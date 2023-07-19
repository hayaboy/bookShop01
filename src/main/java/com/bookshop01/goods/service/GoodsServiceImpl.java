package com.bookshop01.goods.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookshop01.goods.dao.GoodsDAO;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import com.bookshop01.main.MainController;

@Service("goodsService")
@Transactional(propagation=Propagation.REQUIRED)
public class GoodsServiceImpl implements GoodsService{
	
	private static final Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);
	
	@Autowired
	private GoodsDAO goodsDAO;
	
	public Map<String,List<GoodsVO>> listGoods() throws Exception {
		
		logger.info("여기는 GoodsServiceImpl의 listGoods()");
		
		
		// 보여줄 상품 정보를 goodsMap 객체에 저장
		Map<String,List<GoodsVO>> goodsMap=new HashMap<String,List<GoodsVO>>();
		
		//베스트셀러 상품 목록
		List<GoodsVO> goodsList=goodsDAO.selectGoodsList("bestseller");
		logger.info("베스트셀러 상품 목록을 goodsMap 객체에 저장");
		goodsMap.put("bestseller",goodsList);
		
		//신간 상품 목록
		goodsList=goodsDAO.selectGoodsList("newbook");
		logger.info("신간 상품 목록을 goodsMap 객체에 저장");
		goodsMap.put("newbook",goodsList);
		
		
		//스테디셀러 상품 목록
		goodsList=goodsDAO.selectGoodsList("steadyseller");
		logger.info("스테디셀러 상품 목록을 goodsMap 객체에 저장");
		goodsMap.put("steadyseller",goodsList);
		return goodsMap;
	}
	
	public Map goodsDetail(String _goods_id) throws Exception {
		Map goodsMap=new HashMap();
		GoodsVO goodsVO = goodsDAO.selectGoodsDetail(_goods_id);
		goodsMap.put("goodsVO", goodsVO);
		List<ImageFileVO> imageList =goodsDAO.selectGoodsDetailImage(_goods_id);
		goodsMap.put("imageList", imageList);
		return goodsMap;
	}
	
	public List<String> keywordSearch(String keyword) throws Exception {
		List<String> list=goodsDAO.selectKeywordSearch(keyword);
		return list;
	}
	
	public List<GoodsVO> searchGoods(String searchWord) throws Exception{
		List goodsList=goodsDAO.selectGoodsBySearchWord(searchWord);
		return goodsList;
	}
	
	
}
