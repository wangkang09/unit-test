package com.wangkang.spock;

import java.util.ArrayList;
import java.util.IllformedLocaleException;
import java.util.List;

/**
 * @Description:
 * @Author: wangkang
 * @Date: Created in 14:21 2020/3/28
 * @Modified By:
 */
public class UserInfoController  {
    UserInfoServiceImpl userInfoService;
    public UserInfoController(UserInfoServiceImpl userInfoService) {
        this.userInfoService = userInfoService;
    }
    public Integer getOrderListDemo(Integer req, String site, String name, List<String> names){
        if (req == null) {
            throw new RuntimeException("req 对象是null");
        }
        Integer userDTOResponse;
        try {
            userDTOResponse = userInfoService.getUserInfo(req);
        } catch (IllegalArgumentException e) {
            userDTOResponse = -1;
        } catch (IllformedLocaleException e) {
            userDTOResponse = -2;
        }
        return userDTOResponse;
    }
}
