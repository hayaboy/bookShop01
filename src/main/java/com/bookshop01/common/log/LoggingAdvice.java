package com.bookshop01.common.log;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect  // Aspect = Pointcut + Advice    //Spring 구성에서 @AspectJ aspect를 사용하려면 @AspectJ aspect를 기반으로 Spring AOP를 구성하기 위한 Spring 지원과 해당 aspect에 의해 조언되는지 여부에 따라 bean을 자동 프록시하도록 설정해야 한다. autoproxying에 의해 우리는 Spring이 bean이 하나 이상의 aspect에 의해 어드바이스되었다고 결정하면 자동으로 해당 bean에 대한 프록시를 생성하여 메소드 호출을 가로채고 어드바이스가 필요에 따라 실행되도록 보장한다는 것을 의미합니다
public class LoggingAdvice {
	private static final Logger logger = LoggerFactory.getLogger(LoggingAdvice.class);
                                                                                                                                         //리턴타입  이 패키지로 시작하는 모든 패키지 선택  클래스 이름이 Impl로 끝나는 클래스만 선택   get으로 시작하는 모든 메서드
	// target 메서드의 파라미터등 정보를 출력합니다.     //포인트컷 표현식 execution 명시자를 이용하며, execution 명시자 안에 포인트 컷 표현식을 기술  전제적인 구조 execution(*     com.multicampus.biz..*Impl.get*(..))  
	@Before("execution(* com.bookshop01.*.service.*.*(..)) or " + "execution(* com.bookshop01.*.dao.*.*(..))")  //리턴타입*   패키지 경로를 저장할 때는 '*','..' 캐릭터 이용
	public void startLog(JoinPoint jp) { // 조인 포인트에서 사용할 수 있는 상태와 이에 대한 정적 정보에 대한 반영 액세스를 제공합니다. 이 정보는 thisJoinPoint라는
											// 특수 형식을 사용하여 조언 본문에서 사용할 수 있습니다. 이 반사 정보의 주요 용도는 응용 프로그램을 추적하고 기록하는 것입니다.
		
		logger.info("--------------------------------------------------------------------------------------------------------------");
		logger.info("target 메서드의 파라미터등 정보를 출력합니다.");
		logger.info("-------------------------------------");
		logger.info("-------------------------------------");
		logger.info("@Before  - 비지니스 메서드 실행 전 동작 시작");

		// 전달되는 모든 파라미터들을 Object의 배열로 가져옵니다.
		logger.info("아래의 내용은 어드바이스를 적용하는 지점을 배열로 받아봄");
		logger.info("1:" + Arrays.toString(jp.getArgs())); // jp : 어드바이스를 적용하는 지점 처음엔 [] 서비스 그 후  [bestseller]의 DAO

		// 해당 Advice의 타입을 알아냅니다.\
		logger.info("아래는 Advice 타입");
		logger.info("2:" + jp.getKind()); // 메서드 타입

		// 실행하는 대상 객체의 메소드에 대한 정보를 알아낼 때 사용합니다.
		logger.info("아래는 실행하는 대상 객체의 메소드에 대한 정보");
		logger.info("3:" + jp.getSignature().getName()); //selectGoodsList 메서드

		// target 객체를 알아낼 때 사용합니다.
		logger.info("아래는 target 객체");
		logger.info("4:" + jp.getTarget().toString());  //타겟 객체 com.bookshop01.goods.dao.GoodsDAOImpl 객체

		// Advice를 행하는 객체를 알아낼 때 사용합니다.
		logger.info("아래는 Advice를 행하는 객체");
		logger.info("5:" + jp.getThis().toString());
		logger.info("@Before  - 비지니스 메서드 실행 전 동작 끝");
		logger.info("--------------------------------------------------------------------------------------------------------------");
	}

	@After("execution(* com.bookshop01.*.service.*.*(..)) or " + "execution(* com.bookshop01.*.*.dao.*.*(..))")
	public void after(JoinPoint jp) {
		logger.info("--------------------------------------------------------------------------------------------------------------");
		logger.info("@After  - 비지니스 메서드 실행 된 후 무조건 실행  - 시작");
		logger.info("-------------------------------------");
		logger.info("-------------------------------------");

		// 전달되는 모든 파라미터들을 Object의 배열로 가져옵니다. 
		logger.info("1:" + Arrays.toString(jp.getArgs()));

		// 해당 Advice의 타입을 알아냅니다. 
		logger.info("2:" + jp.getKind());

		// 실행하는 대상 객체의 메소드에 대한 정보를 알아낼 때 사용합니다.
		logger.info("3:" + jp.getSignature().getName());

		// target 객체를 알아낼 때 사용합니다. 
		logger.info("4:" + jp.getTarget().toString());

		// Advice를 행하는 객체를 알아낼 때 사용합니다 
		logger.info("5:" + jp.getThis().toString());
		
		logger.info("@After  - 비지니스 메서드 실행 된 후 무조건 실행  - 끝");
		logger.info("--------------------------------------------------------------------------------------------------------------");

	}

	// target 메소드의 동작 시간을 측정합니다.
	@Around("execution(* com.bookshop01.*.service.*.*(..)) or " + "execution(* com.bookshop01.*.dao.*.*(..))")
	public Object timeLog(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("@Around는 메서드 호출 자체를 가로채 비지니스 메서드 실행 전후에 처리할 로직을 삽입할 수 있음");
		long startTime = System.currentTimeMillis();
		logger.info(Arrays.toString(pjp.getArgs()));

		// 실제 타겟을 실행하는 부분이다. 이 부분이 없으면 advice가 적용된 메소드가 동작하지않습니다.
		Object result = pjp.proceed(); // proceed는 Exception 보다 상위 Throwable을 처리해야 합니다.

		long endTime = System.currentTimeMillis();
		// target 메소드의 동작 시간을 출력한다.
		logger.info("메소드 동작 시간 측정하여 sql 동작 시간이 오래 걸릴 경우 sql문 점검 및 인덱스 사용할지 여부  검토" +pjp.getSignature().getName() + " : " + (endTime - startTime) + "밀리세컨드"); 
		logger.info("==============================");

		// Around를 사용할 경우 반드시 Object를 리턴해야 합니다.
		return result;
	}

}
