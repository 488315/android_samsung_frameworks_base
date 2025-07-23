package androidx.compose.ui.platform;

import android.view.View;
import android.view.translation.ViewTranslationCallback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AndroidComposeViewTranslationCallback implements ViewTranslationCallback {
    public static final AndroidComposeViewTranslationCallback INSTANCE = new AndroidComposeViewTranslationCallback();

    private AndroidComposeViewTranslationCallback() {
    }

    @Override // android.view.translation.ViewTranslationCallback
    public final boolean onClearTranslation(View view) {
        ((AndroidComposeView) view).contentCaptureManager.onClearTranslation$ui_release();
        return true;
    }

    @Override // android.view.translation.ViewTranslationCallback
    public final boolean onHideTranslation(View view) {
        ((AndroidComposeView) view).contentCaptureManager.onHideTranslation$ui_release();
        return true;
    }

    @Override // android.view.translation.ViewTranslationCallback
    public final boolean onShowTranslation(View view) {
        ((AndroidComposeView) view).contentCaptureManager.onShowTranslation$ui_release();
        return true;
    }
}
