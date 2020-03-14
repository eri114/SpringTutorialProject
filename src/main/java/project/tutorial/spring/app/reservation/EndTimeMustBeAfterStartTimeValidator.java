package project.tutorial.spring.app.reservation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EndTimeMustBeAfterStartTimeValidator 
		implements ConstraintValidator<EndTimeMustBeAfterStartTime, ReservationForm> {
	private String message;

	@Override
	public void initialize(EndTimeMustBeAfterStartTime constraintAnnotation) {
		message = constraintAnnotation.message();
	}
	
	@Override
	public boolean isValid(ReservationForm value, ConstraintValidatorContext context) {

		// 入力値がnullの場合、ここではチェックせずに他のルール(@NotNullなど)に移譲
		if(value.getStartTime() == null || value.getEndTime() == null) {
			return true;
		}
		boolean isEndTimeMustBeAfterStartTime = value.getEndTime()
				.isAfter(value.getStartTime());
		if (!isEndTimeMustBeAfterStartTime) {
			
			// デフォルトのメッセージの出し方を無効にし、EndTimeプロパティに対してエラーメッセージを設定。
			// 画面でエラーメッセージを表示する際、フィールド右横に表示したいときに必要な処理。
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message)
				.addPropertyNode("endTime").addConstraintViolation();
		}
		return isEndTimeMustBeAfterStartTime;
	}
}
