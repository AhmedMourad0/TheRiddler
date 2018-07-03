package inc.ahmedmourad.theriddler.model.classes;

import android.support.annotation.NonNull;

import org.parceler.Parcel;

import inc.ahmedmourad.theriddler.model.interfaces.MultiQuestion;
import inc.ahmedmourad.theriddler.model.interfaces.Question;

@Parcel(Parcel.Serialization.BEAN)
public class MultiChoiceQuestion implements MultiQuestion<String> {

	private String value;
	private String answer;
	private String[] wrongAnswers;

	@NonNull
	public static MultiChoiceQuestion create(final String value, final String answer, final String... wrongAnswers) {
		final MultiChoiceQuestion question = new MultiChoiceQuestion();
		question.setValue(value);
		question.setAnswer(answer);
		question.setWrongAnswers(wrongAnswers);
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
	public void setWrongAnswers(final String... wrongAnswers) {
		this.wrongAnswers = wrongAnswers;
	}

	@Override
	public String[] getWrongAnswers() {
		return wrongAnswers;
	}

	@Override
	public int getType() {
		return Question.TYPE_MULTI_CHOICE;
	}
}
