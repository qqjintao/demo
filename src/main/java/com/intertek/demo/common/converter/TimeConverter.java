package com.intertek.demo.common.converter;

import com.intertek.demo.common.utils.DateUtil;
import com.wuwenze.poi.convert.WriteConverter;
import com.wuwenze.poi.exception.ExcelKitWriteConverterException;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;

/**
 * @author jacksy.qin
 * @date 2019/9/23 13:57
 */
@Slf4j
public class TimeConverter implements WriteConverter {
    @Override
    public String convert(Object value) {
        if(value == null){
            return "";
        }
        try {
            return DateUtil.formatCSTTime(value.toString(), DateUtil.FULL_TIME_SPLIT_PATTERN);
        }catch (ParseException e){
            String message = "时间转换异常";
            log.error(message, e);
            throw new ExcelKitWriteConverterException(message);
        }
    }
}
