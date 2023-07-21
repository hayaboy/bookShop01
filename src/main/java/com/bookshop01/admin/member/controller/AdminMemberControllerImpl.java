package com.bookshop01.admin.member.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.admin.member.service.AdminMemberService;
import com.bookshop01.common.base.BaseController;
import com.bookshop01.main.MainController;
import com.bookshop01.member.vo.MemberVO;

@Controller("adminMemberController")
@RequestMapping(value="/admin/member")
public class AdminMemberControllerImpl extends BaseController  implements AdminMemberController{
	
	private static final Logger logger = LoggerFactory.getLogger(AdminMemberControllerImpl.class);
	
	@Autowired
	private AdminMemberService adminMemberService;
	
	@RequestMapping(value="/adminMemberMain.do" ,method={RequestMethod.POST,RequestMethod.GET})
	
	public ModelAndView adminGoodsMain(@RequestParam Map<String, String> dateMap,
			                           HttpServletRequest request, HttpServletResponse response)  throws Exception{
		
		
		logger.info("관리자가 회원관리하는 화면으로 들어옴 ");
		
		String viewName=(String)request.getAttribute("viewName");
		
		logger.info("adminMemberMain.do에서의 뷰네임 : " + viewName);
		ModelAndView mav = new ModelAndView(viewName);

//		String fixedSearchPeriod = dateMap.get("fixedSearchPeriod");
//		logger.info("fixedSearchPeriod(고정된 검색 기간 : " + fixedSearchPeriod);
//		String section = dateMap.get("section");
//		logger.info("section  : " + section);
//		String pageNum = dateMap.get("pageNum");
//		logger.info("pageNum  : " + pageNum);
//		
//		
//		String beginDate=null,endDate=null;
//		
//		String [] tempDate=calcSearchPeriod(fixedSearchPeriod).split(",");
//		logger.info("fixedSearchPeriod에서 가져온 임시 날짜 배열  : " + Arrays.toString(tempDate) );
//		
//		beginDate=tempDate[0];
//		endDate=tempDate[1];
//		logger.info("시작일 : " +beginDate  + " 마지막일 : " + endDate);
//		
//		dateMap.put("beginDate", beginDate);
//		
//		dateMap.put("endDate", endDate);
//		
//		
//		HashMap<String,Object> condMap=new HashMap<String,Object>();
//		if(section== null) {
//			section = "1";
//		}
//		condMap.put("section",section);
//		if(pageNum== null) {
//			pageNum = "1";
//		}
//		condMap.put("pageNum",pageNum);
//		condMap.put("beginDate",beginDate);
//		condMap.put("endDate", endDate);
//		
		
		//기간 정보 없이 모든 회원 정보 조회
		ArrayList<MemberVO> all_member_list=adminMemberService.listMember();
//		ArrayList<MemberVO> member_list=adminMemberService.listMember(condMap);
//		
		// 기간 정보 없는 모든 회원 all_member_list, 기간 정보 있는 모든 회원 member_list
		mav.addObject("all_member_list", all_member_list);
		//mav.addObject("member_list", member_list);
//		
//		String beginDate1[]=beginDate.split("-");
//		String endDate2[]=endDate.split("-");
//		mav.addObject("beginYear",beginDate1[0]);
//		mav.addObject("beginMonth",beginDate1[1]);
//		mav.addObject("beginDay",beginDate1[2]);
//		mav.addObject("endYear",endDate2[0]);
//		mav.addObject("endMonth",endDate2[1]);
//		mav.addObject("endDay",endDate2[2]);
//		
//		mav.addObject("section", section);
//		mav.addObject("pageNum", pageNum);
		return mav;
		
	}
	@RequestMapping(value="/memberDetail.do" ,method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView memberDetail(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		logger.info("adminMemberController의 memberDetail.do 메서드로 들어옴");
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		String member_id=request.getParameter("member_id");
		logger.info("member_id : " + member_id);
		MemberVO member_info=adminMemberService.memberDetail(member_id);
		mav.addObject("member_info",member_info);
		return mav;
	}
	
	@RequestMapping(value="/modifyMemberInfo.do" ,method={RequestMethod.POST,RequestMethod.GET})
	public void modifyMemberInfo(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		HashMap<String,String> memberMap=new HashMap<String,String>();
		String val[]=null;
		PrintWriter pw=response.getWriter();
		String member_id=request.getParameter("member_id");
		logger.info("수정할 id : " + member_id );
		String mod_type=request.getParameter("mod_type");
		logger.info("수정할 mod_type : " + mod_type );
		String value =request.getParameter("value");
		logger.info("수정할 value : " + value );
		
		if(mod_type.equals("member_birth")){
			val=value.split(",");
			memberMap.put("member_birth_y",val[0]);
			memberMap.put("member_birth_m",val[1]);
			memberMap.put("member_birth_d",val[2]);
			memberMap.put("member_birth_gn",val[3]);
		}else if(mod_type.equals("tel")){
			val=value.split(",");
			memberMap.put("tel1",val[0]);
			memberMap.put("tel2",val[1]);
			memberMap.put("tel3",val[2]);
			
		}else if(mod_type.equals("hp")){
			val=value.split(",");
			memberMap.put("hp1",val[0]);
			memberMap.put("hp2",val[1]);
			memberMap.put("hp3",val[2]);
			memberMap.put("smssts_yn", val[3]);
		}else if(mod_type.equals("email")){
			val=value.split(",");
			memberMap.put("email1",val[0]);
			memberMap.put("email2",val[1]);
			memberMap.put("emailsts_yn", val[2]);
		}else if(mod_type.equals("address")){
			val=value.split(",");
			memberMap.put("zipcode",val[0]);
			memberMap.put("roadAddress",val[1]);
			memberMap.put("jibunAddress", val[2]);
			memberMap.put("namujiAddress", val[3]);
		}
		
		memberMap.put("member_id", member_id);
		
		//수정할 정보가 담긴 memberMap객체를 매개변수에 넣어주고 수정
		adminMemberService.modifyMemberInfo(memberMap);
		pw.print("mod_success");
		pw.close();		
		
	}
	
	@RequestMapping(value="/deleteMember.do" ,method={RequestMethod.POST})
	public ModelAndView deleteMember(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		ModelAndView mav = new ModelAndView();
		HashMap<String,String> memberMap=new HashMap<String,String>();
		String member_id=request.getParameter("member_id");
		String del_yn=request.getParameter("del_yn");
		memberMap.put("del_yn", del_yn);
		memberMap.put("member_id", member_id);
		
		adminMemberService.modifyMemberInfo(memberMap);
		mav.setViewName("redirect:/admin/member/adminMemberMain.do");
		return mav;
		
	}
		
}
