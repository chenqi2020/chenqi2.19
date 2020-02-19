package com.chenqi.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenqi.cms.dao.DomainMapper;
import com.chenqi.cms.domain.Domain;
import com.chenqi.cms.util.CMSException;
import com.chenqi.cms.vo.ComplainVO;
import com.chenqi.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class DomainServiceimpl implements DomainService{
	@Autowired
	private DomainMapper domainMapper;
	
	@Override
	public PageInfo<Domain> list(Integer page, Integer pageSize,ComplainVO complainVO) {
		
		PageHelper.startPage(page, pageSize);
		List<Domain> list = domainMapper.list(complainVO);
		return new PageInfo<Domain>(list);
	}

	@Override
	public boolean add(Domain domain) {
		
		try {
			
			boolean b = StringUtil.isHttpUrl(domain.getUrl());
			//判断url是否合格
			if(!b) {
				throw new CMSException("url 不合法");
			}
			domainMapper.add(domain);
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("举报失败");
		}
		
		
		
	}

	@Override
	public void dele(Integer did) {
		// TODO Auto-generated method stub
		domainMapper.dele(did);
	}

	
}
