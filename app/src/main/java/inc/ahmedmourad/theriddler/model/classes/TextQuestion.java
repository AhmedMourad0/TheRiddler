package inc.ahmedmourad.theriddler.model.classes;

import android.support.annotation.NonNull;

import org.parceler.Parcel;

import inc.ahmedmourad.theriddler.model.interfaces.Question;

@Parcel(Parcel.Serialization.BEAN)
public class TextQuestion implements Question<String> {

	private String value;
	private String answer;

	@NonNull
	public static TextQuestion create(final String value, final String answer) {
		final TextQuestion question = new TextQuestion();
		question.setValue(value);
		question.setAnswer(answer);
		return question;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setAnswer(final String answer) {
		this.answer = answer;
	}

	@Override
	public String getAnswer() {
		return answer;
	}

	@Override
	public int getType() {
		return Question.TYPE_TEXT;
	}
}
