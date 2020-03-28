package com.chz.component.crud_web;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 *加入到ioc中DefaultErrorAttributes的@ConditionalOnMissingBean生效
 *  BasicErrorController调用自定义的ErrorAttributes中的getErrorAttributes
 *  会对所有的错误都添加上自定义map中的信息
 */
@Component
//采用继承ErrorAttributes的子类DefaultErrorAttributes只要重写getErrorAttributes即可
public class MyErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, includeStackTrace);
        map.put("company","chz");//添加自己定义的标识
        //第二个参数是从哪一个域中取值
        HashMap<String, Object> myMap = (HashMap<String, Object>) webRequest.getAttribute("map",0);
        map.put("myMap",myMap);//map也是一个Object
        return map;
    }
}
