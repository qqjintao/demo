package com.intertek.demo.common.validator;

import com.intertek.demo.common.annotation.IsCron;
import org.quartz.CronExpression;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验是否为合法的 Cron表达式
 *
 * @author jacksy.qin
 * @date 2019/9/23 14:24
 */
public class CronValidator implements ConstraintValidator<IsCron, String> {
    @Override
    public void initialize(IsCron isCron) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return CronExpression.isValidExpression(s);
        }catch (Exception e){
            return false;
        }
    }
}
