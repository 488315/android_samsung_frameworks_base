package androidx.compose.foundation;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.runtime.saveable.SaverKt$Saver$1;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public abstract class ScrollKt {
    /* JADX WARN: Removed duplicated region for block: B:9:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final ScrollState rememberScrollState(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.rememberScrollState (Scroll.kt:68)");
        }
        final int i = 0;
        Object[] objArr = new Object[0];
        ScrollState.Companion.getClass();
        SaverKt$Saver$1 saverKt$Saver$1 = ScrollState.Saver;
        boolean zChanged = ((ComposerImpl) composer).changed(0);
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = new Function0() { // from class: androidx.compose.foundation.ScrollKt$rememberScrollState$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new ScrollState(i);
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        ScrollState scrollState = (ScrollState) RememberSaveableKt.rememberSaveable(objArr, saverKt$Saver$1, null, (Function0) objRememberedValue, composerImpl, 0, 4);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return scrollState;
    }
}
