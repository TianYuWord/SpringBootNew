package cn.boot.validatorpro.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone,String> {

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$"
    );

    @Override
    public void initialize(Phone constraintAnnotation) {

    }

    /**
     * 校验的实际逻辑 ， （校验是否失败）
     * @param s
     * @param constraintValidatorContext
     * @return  为true则失败
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        if( s == null || s.length() == 0) {
            return true;
        }

        Matcher m = PHONE_PATTERN.matcher(s);

        return m.matches();
    }
}
