package com.winreal.aop;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.winreal.service.LogService;
import com.winreal.vo.LogVO;

import lombok.extern.log4j.Log4j;

/**
 * AOP(Aspect-Oriented Programming)
 * 관점지향 프로그래밍
 * 핵심비지니스 로직과 [부가적인 관심사]를 분리하여 개발하는 방법론(누구의 관점인가에 따라 관심사가 다름)
 * 
 *  코드의 중복을 줄이고 유지보수성을 향상시킬 수 있습니다.
 *  
 *  [부가적인 관심사]
 *  	로깅, 보안, 트랜잭션 관리 등
 *  	애플리케이션에서 공통적으로 처리해야 하는 기능
 *  	오류발생시 데이터베이스에 저장
 *  
 * Aspect
 * 		부가적인 관심사를 모듈화한 단위
 * 		(Advice를 그룹화)Cross Concern : 횡단관심사
 * 		주 업무로직 이외의 부가적인 기능을 의미
 * 
 * Advice
 * 		부가적인 관심사
 *  
 * Pointcut
 * 		부가기능이 적용되는 지점 (언제어디서 적용될 것인지 지정(메서드 실행전, 후, 감싸기)
 * 
 * Target
 * 		핵심 기능을 구현한 객체, 적용되는 주요업무
 * 		(Core Concern: 핵심관심사)
 * 
 * Proxy
 * 		Target + Advice
 * @author user
 *
 */

@Aspect
@Log4j
@Component  //자바빈으로 등록위해 컴포넌트 어노테이션 
public class LogAdvice {

	/**
	 * 문서참고!(part5.aop.pdf)
	 * 포인트컷 : 언제(실행 전, 후, 중) 어디에(어떤 메서드) 적용할지 기술
	 * 
	 * [Before]
	 * 	타겟 객체의 메서드가 실행되기 전에 호출되는 어드바이스
	 *  JoinPoint를 통해 파라미터 정보 참조 가능
	 *  (JoinPoint라는 객체를 매개변수로 받아서 사용 가능)
	 */
	//첫 괄호 ""안에 포인트 컷 지정 ()안에는 접근제한자, 패키지포함한 클래스 이름  ..(매개변수)
	@Before("execution(* com.winreal.service.Board*.*(..))")
	public void logBefore() {
		log.info("=============================");
	}
	
	/**
	 * joinPoint
	 * 		타겟에 대한 정보와 상태를 담고 있는 객체로 매개변수로 받아서 사용
	 * 		파라메더에 필수는 아님
	 * @param joinPoint
	 */
	//★오류추적시 도움
	@Before("execution(* com.winreal.service.Reply*.*(..))")
	public void logBeforeParams(JoinPoint joinPoint) {
		log.info("===========AOP====================");
		log.info("Param : " + Arrays.toString(joinPoint.getArgs()));  //오브젝트 배열을 반환하고 있음 > 출력하려면 Arrays.toString()
		log.info("Target : " + joinPoint.getTarget());
		log.info("Method : " + joinPoint.getSignature().getName());
	}
	
	/**
	 * Around
	 * 		타겟의 메서드가 호출되기 이전 시점과 이후시점에 모두 처리해야할 필요가 있는 부가기능 정의 (주업무로직을 감싼다.)
	 * 		주업무 로직을 실행하기 위해 joinPoint의 하위 클래스인 
	 * 		ProceedingJoinPoint 타입의 파라미터를 필수적으로 선언해야 함 (반환타입 관리)
	 * 		타겟메서드의 실행결과를 반환하기 위해서 선언은 필수!
	 * @return pjp
	 */
	//★ 시간구하기
	//어라운드는 타겟을 직접 실행해야한다 (감싸고 처리하기때문에 )
	//어라운드의 첫번째 매서드가 ProceedingJoinPoint 들어와야 한다!(약속)
	/*
	@Around("execution(* com.winreal.service.Board*.*(..))")  //포인트컷. 어디다가 실행할지
	public Object logTime(ProceedingJoinPoint pjp) {
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Object res = "";
		//주 업무로직 실행 (타켓 메서드의 실행시점을 정할 수 있다)
		try {
			res = pjp.proceed();   //반드시 res에 담아서 넘겨줘야한다
		} catch (Throwable e) {
			// TODO: handle exception
		}
		
		stopWatch.stop();
		
		//어디에 했는지 알기위해 pjp로부터 얻어옵니다.
		log.info("==========================");
		log.info(pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName());
		log.info("수행시간 : " + stopWatch.getTotalTimeMillis() + "(ms)초");
		log.info("==========================");
		
		return res;
	}
	*/
	
	
	//로그를 남기는 객체를 자동주입받아야 한다
	@Autowired
	LogService logService;

	/**
	 * AfterThrowing
	 * 		타겟 메서드 실행 중 예외가 발생한 뒤에 실행할 부가기능
	 * 		오류가 발생한 내용을 테이블에 등록
	 * @param joinPoint
	 * @param exception
	 */
	@AfterThrowing(pointcut="execution(* com.winreal.service.*.*(..))", throwing="exception")
	public void logException(JoinPoint joinPoint, Exception exception) {
		// 예외가 발생시 예외 내용을 테이블에 저장하는 코드
		
		try {
			LogVO vo = new LogVO();
			
			
			vo.setClassName(joinPoint.getTarget().getClass().getName());
			vo.setMethodName(joinPoint.getSignature().getName());
			vo.setParams(Arrays.toString(joinPoint.getArgs())); //배열이니까 
			vo.setErrmsg(exception.getMessage());
			
			logService.insert(vo);			
			
			log.info("=========== 로그테이블 저장");
		} catch (Exception e) {
			log.info("=========== 로그테이블 저장중 예외발생");
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
}















