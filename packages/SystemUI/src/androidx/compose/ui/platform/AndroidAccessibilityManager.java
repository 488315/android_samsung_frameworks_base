package androidx.compose.ui.platform;

import android.content.Context;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AndroidAccessibilityManager implements AccessibilityManager {
    public final android.view.accessibility.AccessibilityManager accessibilityManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public AndroidAccessibilityManager(Context context) {
        this.accessibilityManager = (android.view.accessibility.AccessibilityManager) context.getSystemService("accessibility");
    }
}
