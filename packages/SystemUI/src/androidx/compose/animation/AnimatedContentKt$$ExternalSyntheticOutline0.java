package androidx.compose.animation;

import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract /* synthetic */ class AnimatedContentKt$$ExternalSyntheticOutline0 {
    public static void m(int i, ComposerImpl composerImpl, int i2, Function2 function2) {
        composerImpl.updateRememberedValue(Integer.valueOf(i));
        composerImpl.apply(Integer.valueOf(i2), function2);
    }

    public static boolean m(ComposerImpl composerImpl, boolean z, boolean z2) {
        composerImpl.end(z);
        composerImpl.end(z2);
        return ComposerKt.isTraceInProgress();
    }
}
