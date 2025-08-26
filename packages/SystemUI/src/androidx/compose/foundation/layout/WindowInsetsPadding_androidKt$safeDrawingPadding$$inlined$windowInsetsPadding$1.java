package androidx.compose.foundation.layout;

import androidx.compose.foundation.layout.WindowInsetsHolder;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Lambda;

/* loaded from: classes.dex */
public final class WindowInsetsPadding_androidKt$safeDrawingPadding$$inlined$windowInsetsPadding$1 extends Lambda implements Function3 {
    public WindowInsetsPadding_androidKt$safeDrawingPadding$$inlined$windowInsetsPadding$1() {
        super(3);
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0038  */
    @Override // kotlin.jvm.functions.Function3
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ((Number) obj3).intValue();
        ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
        composerImpl.startReplaceGroup(359872873);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.layout.windowInsetsPadding.<anonymous> (WindowInsetsPadding.android.kt:240)");
        }
        WindowInsetsHolder.Companion.getClass();
        WindowInsetsHolder windowInsetsHolderCurrent = WindowInsetsHolder.Companion.current(composerImpl);
        boolean zChanged = composerImpl.changed(windowInsetsHolderCurrent);
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new InsetsPaddingModifier(windowInsetsHolderCurrent.safeDrawing);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        InsetsPaddingModifier insetsPaddingModifier = (InsetsPaddingModifier) objRememberedValue;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return insetsPaddingModifier;
    }
}
