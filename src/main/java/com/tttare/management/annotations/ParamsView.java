package com.tttare.management.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * ClassName: ParamsView <br/>
 * Description: 方法入参和返回值的打印<br/>
 * date: 2020/11/22 19:31<br/>
 *
 * @author: tttare<br />
 * @since JDK 1.8
 */
@Documented
@Target(ElementType.METHOD)
public @interface ParamsView {

}
