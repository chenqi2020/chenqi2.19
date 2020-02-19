package com.chenqi.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chenqi.cms.dao.CategoryMapper;
import com.chenqi.cms.dao.ChannelMapper;
import com.chenqi.cms.domain.Category;
import com.chenqi.cms.domain.Channel;
import com.chenqi.cms.service.ChannelService;

@Service
public class ChannelServiceImpl implements ChannelService {

	@Resource
	private ChannelMapper channelMapper;
	@Resource
	private CategoryMapper categoryMapper;
	
	@Override
	public List<Channel> selects() {
		return channelMapper.selects();
	}

	@Override
	public List<Category> selectsByChannelId(Integer channerId) {
		return categoryMapper.selectsByChannelId(channerId);
	}

}
