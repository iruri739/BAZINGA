package com.accenture.huaweigroup.module.mapper;

import com.accenture.huaweigroup.module.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    User getUserByName(String name);

    User getUserById(int id);

    void insert(User user);

    //未添加更新方法 2019-9-2 22:12:59
    void update(User user);

}
