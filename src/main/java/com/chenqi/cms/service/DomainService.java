package com.chenqi.cms.service;



import org.apache.ibatis.annotations.Param;

import com.chenqi.cms.domain.Domain;
import com.chenqi.cms.vo.ComplainVO;
import com.github.pagehelper.PageInfo;

public interface DomainService {

	//添加
	boolean add(Domain domain);
	
	//查询
	PageInfo<Domain> list(Integer page,Integer pageSize,ComplainVO complainVO);
	
	//删除
	void dele(@Param("did")Integer did);
}
