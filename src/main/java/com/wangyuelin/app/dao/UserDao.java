package com.wangyuelin.app.dao;

import com.wangyuelin.app.bean.Usert;
import com.wangyuelin.app.config.mybatis.baseMapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao  extends BaseMapper<Usert> {

    @Select("select * from stu where id=#{id}")
    Usert getUserById(@Param("id") int id);



}
