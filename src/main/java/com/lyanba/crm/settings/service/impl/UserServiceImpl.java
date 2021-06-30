package com.lyanba.crm.settings.service.impl;

import com.lyanba.crm.exception.LoginException;
import com.lyanba.crm.settings.dao.UserDao;
import com.lyanba.crm.settings.domain.User;
import com.lyanba.crm.settings.service.UserService;
import com.lyanba.crm.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public User getUserByLoginActAndLoginPwd(String loginAct, String loginPwd, String ip) throws LoginException {
        User user = userDao.getUserByLoginActAndLoginPwd(loginAct, loginPwd);
        if (null == user) {
            throw new LoginException("账号密码不匹配");
        }
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(currentTime) < 0) {
            throw new LoginException("账号已失效");
        }
        if ("0".equals(user.getLockState())) {
            throw new LoginException("账号被锁定");
        }
        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)) {
            throw new LoginException("IP地址受限");
        }
        return user;
    }

    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }

    @Override
    public User getUserById(String id) {
        return userDao.getUserById(id);
    }
}
