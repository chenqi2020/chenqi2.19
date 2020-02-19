package com.chenqi.cms.dao;

import java.util.List;

import com.chenqi.cms.domain.Complain;
import com.chenqi.cms.vo.ComplainVO;

public interface ComplainMapper {
	
	int insert(Complain complain);
	
	//查询举报
	List<Complain> selects(ComplainVO complainVO);

}
