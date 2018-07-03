package inc.ahmedmourad.theriddler.view.controllers.bases;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bluelinelabs.conductor.Controller;

public abstract class QuestionController extends Controller {

	protected QuestionController(@Nullable Bundle args) {
		super(args);
	}

	public abstract boolean isAnswerCorrect();
}
