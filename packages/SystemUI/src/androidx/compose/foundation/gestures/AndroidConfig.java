package androidx.compose.foundation.gestures;

import android.view.ViewConfiguration;

/* loaded from: classes.dex */
final class AndroidConfig implements ScrollConfig {
    public final ViewConfiguration viewConfiguration;

    public AndroidConfig(ViewConfiguration viewConfiguration) {
        this.viewConfiguration = viewConfiguration;
    }
}
