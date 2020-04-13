package com.wangkang.spock;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: wangkang
 * @Date: Created in 14:19 2020/3/28
 * @Modified By:
 */
@Data
public class UserInfoRequest implements Serializable {
    private String name;
    private String address;
    private Long id;
    private List<String> names;

    private String userInfo;
}
