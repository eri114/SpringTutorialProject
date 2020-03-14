package project.tutorial.spring.app.reservation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 任意のLocalTimeが30分単位であることを示すインターフェース
 */

@Documented
@Constraint(validatedBy = {ThirtyMinuteUnitValidator.class })
@Target({ 
	ElementType.METHOD, 
	ElementType.FIELD, 
	ElementType.ANNOTATION_TYPE, 
	ElementType.CONSTRUCTOR, 
	ElementType.PARAMETER 
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ThirtyMinuteUnit {
	
	String message() default "{SpringTutorialProject.app.reservation.ThirtyMinuteUnit.message}";
	
	// 下記の２行は、バリデーションを実装する際の必須定義
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
		ThirtyMinuteUnit[]value();
	}
}
