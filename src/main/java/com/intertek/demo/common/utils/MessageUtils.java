package com.intertek.demo.common.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author barry.jt.huang
 */
@Component
public class MessageUtils {
    private static MessageSource messageSource;

    public MessageUtils(MessageSource messageSource){
        MessageUtils.messageSource=messageSource;
    }

    public static String get(String msgKey){
       try {
            return messageSource.getMessage(msgKey,null, LocaleContextHolder.getLocale());
       }catch (Exception e){
            return msgKey;
       }
    }
}
