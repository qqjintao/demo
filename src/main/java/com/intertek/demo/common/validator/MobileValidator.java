package com.intertek.demo.common.validator;

import com.intertek.demo.common.annotation.IsMobile;
import com.intertek.demo.common.entity.RegexpConstant;
import com.intertek.demo.common.utils.ItsUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验是否为合法的手机号码
 *
 * @author jacksy.qin
 * @date 2019/9/23 14:28
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String> {
    @Override
    public void initialize(IsMobile constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try{
            if(StringUtils.isBlank(s)){
                return true;
            }else{
                String regex = RegexpConstant.MOBILE_REG;
                return ItsUtil.match(regex, s);
            }
        }catch (Exception e){
            return false;
        }
    }
}
