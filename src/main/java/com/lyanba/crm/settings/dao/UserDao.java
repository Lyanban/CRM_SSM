package com.lyanba.crm.settings.dao;

import com.lyanba.crm.settings.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @className: UserDao
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/19 21:12
 */
public interface UserDao {
    User getUserByLoginActAndLoginPwd(@Param("loginAct") String loginAct, @Param("loginPwd") String loginPwd);

    User login(Map<String, String> map);
}
