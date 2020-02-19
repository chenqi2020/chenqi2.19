package com.chenqi.cms.service;

import java.util.List;

import com.chenqi.cms.domain.Category;
import com.chenqi.cms.domain.Channel;

public interface ChannelService {

	/**
	 * @Title: selects 
	 * @Description: 所有栏目
	 * @return
	 * @return: List<Channel>
	 */
	List<Channel> selects();
	
	/**
	 * @Title: selectsByChannelId 
	 * @Description: 根据栏目查询分类
	 * @param channerId
	 * @return
	 * @return: List<Category>
	 */
	List<Category> selectsByChannelId(Integer channerId);
}
