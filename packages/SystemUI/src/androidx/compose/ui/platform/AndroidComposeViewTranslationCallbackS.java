package androidx.compose.ui.platform;

import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AndroidComposeViewTranslationCallbackS {
    public static final AndroidComposeViewTranslationCallbackS INSTANCE = new AndroidComposeViewTranslationCallbackS();

    private AndroidComposeViewTranslationCallbackS() {
    }

    public final void clearViewTranslationCallback(View view) {
        view.clearViewTranslationCallback();
    }

    public final void setViewTranslationCallback(View view) {
        view.setViewTranslationCallback(AndroidComposeViewTranslationCallback.INSTANCE);
    }
}
