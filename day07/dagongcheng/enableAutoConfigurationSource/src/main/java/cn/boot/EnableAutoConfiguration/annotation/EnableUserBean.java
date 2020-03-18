package cn.boot.EnableAutoConfiguration.annotation;

import cn.boot.EnableAutoConfiguration.importSelector.MyImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 只要使用这个类，就会把实现了ImportSelector接口的实现类返回的数组注册到Spring Ioc容器中去。
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Import({MyImportSelector.class})
public @interface EnableUserBean {
}
