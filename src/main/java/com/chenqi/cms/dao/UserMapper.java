package com.chenqi.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chenqi.cms.domain.User;

public interface UserMapper {
	// 删除
    int deleteByPrimaryKey(Integer id);

    // 添加
    int insert(User record);

    // 添加  支持动态sql
    int insertSelective(User record);

    // 回显
    User selectByPrimaryKey(Integer id);
    
    // 修改  支持动态sql
    int updateByPrimaryKeySelective(User record);

    // 修改
    int updateByPrimaryKey(User record);
    
    // 列表
    List<User> selects(@Param("username")String username, Integer page, Integer pageSize);
    
    //按照用户名查询
    User selectByName(@Param("username")String name);
}