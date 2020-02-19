package com.chenqi.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chenqi.cms.dao.SlideMapper;
import com.chenqi.cms.domain.Slide;
import com.chenqi.cms.service.SlideService;

@Service
public class SlideServiceImpl implements SlideService {

	@Resource
	private SlideMapper slideMapper;
	
	@Override
	public List<Slide> selects() {
		return slideMapper.selects();
	}

}
