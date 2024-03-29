package com.bookshop01.main;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.common.base.BaseController;
import com.bookshop01.goods.service.GoodsService;
import com.bookshop01.goods.vo.GoodsVO;

@Controller("mainController")
@EnableAspectJAutoProxy //Spring의 <aop:aspectj-autoproxy> XML 요소에 있는 기능과 유사한 AspectJ의 @Aspect 주석으로 표시된 구성 요소 처리 지원을 활성화합니다. 다음과 같이 @Configuration 클래스에서 사용됩니다. 주석처리시 AOP적용 안됨
public class MainController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	private GoodsService goodsService;

	@RequestMapping(value= "/main/main.do" ,method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session;
		ModelAndView mav=new ModelAndView();
		String viewName=(String)request.getAttribute("viewName");
		logger.info("main.do 요청시 뷰네임" + viewName);
		
		mav.setViewName(viewName);
		
		session=request.getSession();
		logger.info("세션에 side_menu라는 키값으로 admin_mode, my_page, user라는 문자열 값 설정, 각 값에 따라 side.jsp에서 메뉴를 다르게 설정함");
		
		session.setAttribute("side_menu", "user");		
		logger.info("서비스 객체가 제품 목록을 가져옴");
		
		
		logger.info("여기는 goodsService.listGoods()");
		Map<String,List<GoodsVO>> goodsMap=goodsService.listGoods();
		
		
		
		logger.info("전체 목록(베스트셀러, 신간, 스테디셀러을 모델에 저장 후 (main.jsp)View로 보냄");
		mav.addObject("goodsMap", goodsMap);
		return mav;
	}
}
