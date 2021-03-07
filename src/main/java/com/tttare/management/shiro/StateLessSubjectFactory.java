package com.tttare.management.shiro;

import org.apache.shiro.mgt.DefaultSubjectFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * ClassName: StateLessSubjectFactory <br/>
 * Description: 不使用session的subject工程类<br/>
 * date: 2020/5/24 16:12<br/>
 *
 * @author: tttare<br />
 * @since JDK 1.8
 */
public class StateLessSubjectFactory extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        //不创建session
        context.setSessionCreationEnabled(false);
        //登录之后的subject对象,不再依赖session
        return super.createSubject(context);
    }
}
