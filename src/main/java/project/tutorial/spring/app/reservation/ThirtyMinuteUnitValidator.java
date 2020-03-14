package project.tutorial.spring.app.reservation;

import java.time.LocalTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ThirtyMinuteUnitValidator implements ConstraintValidator<ThirtyMinuteUnit, LocalTime> {
	
	@Override
	public void initialize(ThirtyMinuteUnit constraintAnnotation) {
		
	}
	
	@Override
	public boolean isValid(LocalTime value, ConstraintValidatorContext context) {
		
		// 入力値がnullの場合、ここではチェックせずに他のルール(@NotNullなど)に移譲
		if(value == null) {
			return true;
		}
		return value.getMinute() % 30 == 0;
	}

}
