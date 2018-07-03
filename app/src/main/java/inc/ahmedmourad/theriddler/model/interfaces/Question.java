package inc.ahmedmourad.theriddler.model.interfaces;

import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface Question<RA> {

	int TYPE_TEXT = 0;
	int TYPE_MULTI_CHOICE = 1;
	int TYPE_MULTI_ANSWER = 2;

	@IntDef({TYPE_TEXT, TYPE_MULTI_CHOICE, TYPE_MULTI_ANSWER})
	@Retention(RetentionPolicy.SOURCE)
	@Target(ElementType.METHOD)
	@interface AllowedValues {}

	void setValue(String value);

	String getValue();

	void setAnswer(RA answer);

	RA getAnswer();

	@AllowedValues
	int getType();
}
