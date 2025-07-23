package androidx.compose.ui.platform;

import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AndroidComposeViewForceDarkModeQ {
    public static final AndroidComposeViewForceDarkModeQ INSTANCE = new AndroidComposeViewForceDarkModeQ();

    private AndroidComposeViewForceDarkModeQ() {
    }

    public final void disallowForceDark(View view) {
        view.setForceDarkAllowed(false);
    }
}
