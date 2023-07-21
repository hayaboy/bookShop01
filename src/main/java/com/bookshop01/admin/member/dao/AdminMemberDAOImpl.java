package com.bookshop01.admin.member.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.bookshop01.admin.member.controller.AdminMemberControllerImpl;
import com.bookshop01.member.vo.MemberVO;

@Repository("adminMemberDao")
public class AdminMemberDAOImpl  implements AdminMemberDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(AdminMemberDAOImpl.class);
	
	
	@Override
	public ArrayList<MemberVO> listMember() throws DataAccessException {
		logger.info("전체 회원 조회");
		ArrayList<MemberVO>  memberList=(ArrayList)sqlSession.selectList("mapper.admin.member.listMembers");
		return memberList;
	}
	
	
	@Autowired
	private SqlSession sqlSession;
	
	
	public ArrayList<MemberVO> listMember(HashMap condMap) throws DataAccessException{
		
		logger.info("회원 조회시 해당 되는 기간관련 정보 객체를 매개변수로 넣어주고 회원 정보 뽑아오는 listMember(HashMap condMap))로 들어옴");
		ArrayList<MemberVO>  memberList=(ArrayList)sqlSession.selectList("mapper.admin.member.listMember",condMap);
		return memberList;
	}
	
	public MemberVO memberDetail(String member_id) throws DataAccessException{
		MemberVO memberBean=(MemberVO)sqlSession.selectOne("mapper.admin.member.memberDetail",member_id);
		return memberBean;
	}
	
	public void modifyMemberInfo(HashMap memberMap) throws DataAccessException{
		sqlSession.update("mapper.admin.member.modifyMemberInfo",memberMap);
	}

	
	
	

}
