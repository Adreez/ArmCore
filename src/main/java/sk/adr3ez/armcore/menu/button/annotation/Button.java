package sk.adr3ez.armcore.menu.button.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Button {
	
	int row() default 1;
	int value() default 0;
	SnapPosition snapPosition() default SnapPosition.LEFT;
	
	enum SnapPosition {
		
		LEFT, RIGHT, CENTER
		
	}
	
}
