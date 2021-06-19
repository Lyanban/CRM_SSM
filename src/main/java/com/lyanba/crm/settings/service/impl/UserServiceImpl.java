package com.lyanba.crm.settings.service.impl;

import com.lyanba.crm.settings.dao.UserDao;
import com.lyanba.crm.settings.domain.User;
import com.lyanba.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: UserServiceImpl
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/19 21:11
 * @todo:
 */
@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserByLoginActAndLoginPwd(String loginAct, String loginPwd) {
        return userDao.getUserByLoginActAndLoginPwd(loginAct, loginPwd);
    }
}
