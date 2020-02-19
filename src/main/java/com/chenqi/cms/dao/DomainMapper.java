package com.chenqi.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chenqi.cms.domain.Domain;
import com.chenqi.cms.vo.ComplainVO;

public interface DomainMapper {

	//查询
	List<Domain> list(ComplainVO complainVO);
	//添加
	boolean add(Domain domain);
	//删除
	void dele(@Param("did")Integer did);
	
}
