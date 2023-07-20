package com.winreal.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.spi.FileTypeDetector;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.winreal.service.FileuploadService;
import com.winreal.vo.FileuploadVO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnails;
/**
 * 매서드의 리턴타입
 * 
 *	String 
 * 	/WEB-INF/views/반환값.jsp 응답페이지 주소
 *  servlet-context.xml에 정의되어 있습니다.
 *  
 *  void
 *  요청주소와 동일한 이름의 jsp를 반환
 *  
 * 파일업로드용 라이브러리 추가
 * commons-fileupload
 * 
 * cos.jar와 달리 파일을 저장하는 로직이 추가되어야 합니다!
 * 
 * 1.라이브러리 추가
 * 2.multipartResolver 빈 등록
 * 3.메서드의 매개변수로 MultipartFile 이용
	 * 
 */


@Controller
@Log4j
public class FileuploadController extends CommonRestController{
	
	@GetMapping("/file/fileupload")
	public void fileupload() {
		
	}
	//ATTACHES_DIR : 파일 첨부를 저장하는 디렉토리의 경로
	private static final String ATTACHES_DIR = "c:\\upload\\";
	//c:\ upload\2023\07\18\파일이름.확장자
	/**
	 * [오류발생 요소들]
	 * 전달된 파일이 없는 경우
	 * enctype="multipart/form-data" 오타가 있을 경우
	 * 설정이 안되어 있는 경우
	 * 		- 라이브러리 추가 (commons-fileupload) pom
	 * 		- 빈객체 생성 (multipartResolver) root
	 * 
	 * [파일전송시 고려사항] (처리해야하는 문제들)
	 * -동일한 이름 파일업로드 > 기존파일 삭제    : uuid로 해결
	 * -이미지 용량큰경우 썸네일 이미지 생성        : Thumbnailator 라이브러리 활용(JDK의 ImageIO는 해상도 이슈가 있음)
	 * -이미지파일과 일반파일을 구분해서 다운
	 * -첨부파일공격 대비 업로드 파일 확장자 제한
	 * @param files
	 * @return
	 */
	
	@PostMapping("/file/fileuploadAction")
	public String fileuploadAction(List<MultipartFile> files
									, int bno
									, RedirectAttributes rttr) {
		
		int insertRes = fileupload(files, bno);

		String msg = insertRes + "건 저장되었습니다.";
		rttr.addAttribute("msg", msg);
		
		return "redirect:/file/fileupload";  //fileupload 이 페이지를 다시 요청! 반면에 아래 메서드는 맵을 그냥 반환
	}
	
	
	@PostMapping("/file/fileuploadActionFetch")
	public @ResponseBody Map<String, Object> fileuploadActionFetch(List<MultipartFile> files
									, int bno) {
		log.info("fileuploadActionFetch");
		int insertRes = fileupload(files, bno);
		log.info("업로드건수 : " + insertRes);
		return responseMap("success", insertRes + "건 저장되었습니다." );
		
	}
	
	@Autowired
	FileuploadService service;
	
	@GetMapping("/file/list/{bno}")
	public @ResponseBody Map<String, Object> fileuploadList(@PathVariable("bno") int bno) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", service.getList(bno));
		return map;
	}
	

	
	
	

	//중복방지용 
	//업로드 날짜를 폴더 이름으로 사용
	// 2023\07\18
	public String getFolder() {
		LocalDate currentDate = LocalDate.now();
		String uploadPath = currentDate.toString().replace("-", File.separator)
									+ File.separator;
	
		log.info("CurrentDate : " + currentDate);
		log.info("경로 : " + uploadPath);
		
		//폴더없으면 생성
		File saveDir = new File(ATTACHES_DIR + uploadPath);
		if(!saveDir.exists()) {
			if(saveDir.mkdirs()) {
				log.info("폴더생성!");
			} else {
				log.info("폴더생성 실패!");
			}
		}
		
		return uploadPath;
		
	}
	
	public static void main(String[] args) {
		
		LocalDate currentDate = LocalDate.now();
		String uploadPath = currentDate.toString().replace("-", File.separator)
								+ File.separator;
		System.out.println("CurrentDate : " + currentDate);
		System.out.println("경로 : " + uploadPath);
	}
	
	
	/**
	 * 첨부파일 저장 및 데이터베이스에 등록
	 * @param files
	 * @param bno
	 * @return
	 */
	public int fileupload(List<MultipartFile> files, int bno) {
		
		int insertRes = 0;
		//forEach를 사용하니까 외부의 변수를 이용할 수 없음 
		//files.forEach(file ->{
		for(MultipartFile file : files) {
			
			//선택된 파일이 없는 경우 다음 파일로 이동
			if(file.isEmpty()) {
				continue;
			}
				//if(!file.isEmpty()) {
				
				log.info("oFileName : " + file.getOriginalFilename());
				log.info("name : " + file.getName());
				log.info("size : " + file.getSize());
				
				//(파일이없을 수도 있기때문에 try-catch로 묶어준다)
				try {
					//UUID
					/**
					 * 소프트웨어 구축에 쓰이는 식별자(중복되지 않는값) 표준
					 * UUID를 이용해 파일이름이 중복되어 파일이 소실되지 않도록 uuid를 붙여서 저장
					 * 
					 * ATTACHES_DIR : 파일 첨부를 저장하는 디렉토리의 경로
					 */
					UUID uuid = UUID.randomUUID();
					String saveFileName =  
							uuid + "_" + file.getOriginalFilename();
					String uploadPath = getFolder();
					//dir 경로추가
					//c:/upload/2023/7/18/   이런식으로
					//년/월/일 폴더안에 내가 올리는 파일을 저장! (경로생성하는 매서드 만들어볼게요. 아래의 getFolder메서드)
					
					File sFile = new File(ATTACHES_DIR 
										+ uploadPath    //경로반환 (2023\07\18\) 
										+ saveFileName);
					
					//file(원본파일)을 sFile(저장할 대상 파일)에 저장  
					file.transferTo(sFile);
					
					
					FileuploadVO vo = new FileuploadVO();

					
					
					//★★★이미지 파일일 경우 썸네일
					// 주어진 파일의 Mime 유형을 확인
					String contentType = Files.probeContentType(sFile.toPath());
					
					// Mime타입을 확인하여 이미지인 경우 썸네일을 생성
					if(contentType != null && contentType.startsWith("image")) {
						vo.setFiletype("I");
						
						//썸네일 생성 경로
						String thumbnail =  ATTACHES_DIR 
											+ uploadPath   
											+ "s_"
											+ saveFileName;
						//썸네일 생성
						//원본파일, 크기, 저장될 파일경로
						Thumbnails.of(sFile).size(100, 100).toFile(thumbnail);
					} else {
						vo.setFiletype("F");
					}
					
					vo.setBno(bno);
					vo.setFileName(file.getOriginalFilename());
					//vo.setFiletype("I");
					//I로 지정해놔서 계속 sql에 I로 나옴. 그래서 완전히 없애버림 (setFiletype은 위에 있는 if문에서 결과도출하기 떄문에)
					vo.setUploadPath(uploadPath);
					vo.setUuid(uuid.toString());
					
					int res = service.insert(vo);
					
					if(res>0) {
						insertRes++;
					}
					
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		return insertRes;
		}
	
	//파일삭제
	@GetMapping("/file/delete/{uuid}/{bno}")
	public @ResponseBody Map<String, Object> delete(
							@PathVariable("uuid") String uuid
							, @PathVariable("bno") int bno){
		int res = service.delete(bno, uuid);
		if(res > 0) {
			return responseDeleteMap(res);
		} else {
			return responseDeleteMap(res);
		}
	}
	
	/**
	 *  [★파일다운로드]
	 * 		컨텐츠타입을 다운로드 받을 수 있는 형식으로 지정하여 
	 * 		브라우저에서 파일을 다운로드할 수 있게 처리
	 * 
	 *  다운로드 받을 수 있는 형식으로 처리 > 한글깨짐에 대한 처리
	 *  헤더 설정을 위해 ResponseEntity타입으로 
	 * @param fileName
	 * @return
	 */
	@GetMapping("/file/download")
	public @ResponseBody ResponseEntity<byte[]> download(String fileName){
		log.info("download file : " + fileName);
		// 헤더지정을 위해 헤더객체 생성 , ★스프링프레임워크http 임포트
		HttpHeaders headers = new HttpHeaders();
		
		//파일이 있는지 없는지 확인위해 파일 객체 생성
		File file = new File(ATTACHES_DIR + fileName);
		System.out.println("file : " + file);
		//파일이 존재하면
		if(file.exists()) {
			// 컨텐츠 타입을 지정
			// APPLICATION_OCTET_STREAM : 이진 파일의 콘텐츠 유형
			headers.add("content-type"
					, MediaType.APPLICATION_OCTET_STREAM.toString());
			
			
				// 컨텐츠에 대한 추가설명 및 파일이름 한글처리
				try {
					headers.add("Content-Disposition"
														//★따옴표수정
								, "attachment; fileName=\"" 
								+ new String(file.getName().getBytes("UTF-8"), "ISO-8859-1") + "\"");
								//+ new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\""); ★수정
					
					//스프링에서 제공해주는 ResponseEntity 객체가 있어요
					return new ResponseEntity<>(
									//오류해결 선택에서 두번째거 선택 / 다운로드 파일 이진으로
									FileCopyUtils.copyToByteArray(file)  
									, headers
									, HttpStatus.OK
							);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return new ResponseEntity<>(
									HttpStatus.INTERNAL_SERVER_ERROR);
				} catch (IOException e) {
					e.printStackTrace();
					return new ResponseEntity<>(
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
		// 파일이 존재하지 않으면	
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		// 처음에 만듦(문장다 작성후 필요없어져서 주석처리) return new ResponseEntity<>(HttpStatus.OK);
		
	}	
	
}










