package com.winreal.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.spi.FileTypeDetector;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
public class FileuploadController {
	
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
		
	//	});
		String msg = insertRes + "건 저장되었습니다.";
		rttr.addAttribute("msg", msg);
		
		return "redirect:/file/fileupload";  //루트경로 잡아줘야한다(안잡으면 현재경로부터 감)
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
		log.info("CurrentDate : " + currentDate);
		log.info("경로 : " + uploadPath);
	}
	
}









