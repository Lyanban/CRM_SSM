package com.lyanba.crm.settings.service;

import com.lyanba.crm.exception.LoginException;
import com.lyanba.crm.settings.domain.User;

/**
 * @className: UserService
 * @description:
 * @author: LyanbA
 * @createDate: 2021/6/19 21:11
 * @todo:
 */
public interface UserService {
    User getUserByLoginActAndLoginPwd(String loginAct, String loginPwd);

    User login(String loginAct, String loginPwd, String ip) throws LoginException;
}
