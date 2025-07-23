package android.view.textclassifier;

import android.app.RemoteAction;
import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public final class ExtrasUtils {
    private static final String ACTIONS_INTENTS = "actions-intents";
    private static final String ACTION_INTENT = "action-intent";
    private static final String ENTITY_TYPE = "entity-type";
    private static final String FOREIGN_LANGUAGE = "foreign-language";
    private static final String MODEL_NAME = "model-name";
    private static final String SCORE = "score";

    private ExtrasUtils() {
    }

    public static Bundle getForeignLanguageExtra(TextClassification textClassification) {
        if (textClassification == null) {
            return null;
        }
        return textClassification.getExtras().getBundle(FOREIGN_LANGUAGE);
    }

    public static Intent getActionIntent(Bundle bundle) {
        return (Intent) bundle.getParcelable(ACTION_INTENT, Intent.class);
    }

    public static ArrayList<Intent> getActionsIntents(TextClassification textClassification) {
        if (textClassification == null) {
            return null;
        }
        return textClassification.getExtras().getParcelableArrayList(ACTIONS_INTENTS, Intent.class);
    }

    private static RemoteAction findAction(TextClassification textClassification, String str) {
        ArrayList<Intent> actionsIntents;
        if (textClassification != null && str != null && (actionsIntents = getActionsIntents(textClassification)) != null) {
            int size = actionsIntents.size();
            for (int i = 0; i < size; i++) {
                Intent intent = actionsIntents.get(i);
                if (intent != null && str.equals(intent.getAction())) {
                    return textClassification.getActions().get(i);
                }
            }
        }
        return null;
    }

    public static RemoteAction findTranslateAction(TextClassification textClassification) {
        return findAction(textClassification, Intent.ACTION_TRANSLATE);
    }

    public static String getEntityType(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        return bundle.getString(ENTITY_TYPE);
    }

    public static float getScore(Bundle bundle) {
        if (bundle == null) {
            return -1.0f;
        }
        return bundle.getFloat("score", -1.0f);
    }

    public static String getModelName(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        return bundle.getString(MODEL_NAME);
    }
}
