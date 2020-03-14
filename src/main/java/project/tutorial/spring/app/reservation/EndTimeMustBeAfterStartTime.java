package project.tutorial.spring.app.reservation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = { EndTimeMustBeAfterStartTimeValidator.class })
@Target({ 
	ElementType.TYPE, 
	ElementType.ANNOTATION_TYPE, 
})
@Retention(RetentionPolicy.RUNTIME)
public @interface EndTimeMustBeAfterStartTime {

	String message() default "{SpringTutorialProject.app.reservation.EndTimeMustBeAfterStartTime.message}";

	Class<?>[]groups() default {};
	Class<? extends Payload>[]payload() default {};
	
	@Documented
	@Target({
		ElementType.METHOD, 
		ElementType.FIELD, 
		ElementType.ANNOTATION_TYPE, 
		ElementType.CONSTRUCTOR, 
		ElementType.PARAMETER
	})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface List {
		EndTimeMustBeAfterStartTime[]value();
	}
}
