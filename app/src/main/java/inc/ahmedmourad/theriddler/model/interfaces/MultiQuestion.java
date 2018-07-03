package inc.ahmedmourad.theriddler.model.interfaces;

public interface MultiQuestion<RA> extends Question<RA> {

	void setWrongAnswers(final String... wrongAnswers);

	String[] getWrongAnswers();
}
