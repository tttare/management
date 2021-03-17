package com.tttare.management.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * ClassName: UserLogonRequest <br/>
 * Description: <br/>
 * date: 2020/2/6 19:31<br/>
 *
 * @author: tttare<br />
 * @since JDK 1.8
 */
@Getter
@Setter
public class UserLogonRequest {
    private String email;
    private String emailCode;
    private Boolean haveIcon;
    private String nickName;
    private String password;
    private UploadObject uploadObject;
    private String userName;
    private String birthDate;
    private List<String> location;

}

