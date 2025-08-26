package com.android.systemui.util.composable.kairos;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import com.android.systemui.KairosActivatable;
import com.android.systemui.kairos.KairosNetwork;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final class RememberKairosActivatableKt {
    /* JADX WARN: Removed duplicated region for block: B:20:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0074  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final <T extends KairosActivatable> T rememberKairosActivatable(KairosNetwork kairosNetwork, Object obj, Function0 function0, Composer composer, int i, int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1141740364);
        if ((i2 & 2) != 0) {
            obj = Unit.INSTANCE;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.util.composable.kairos.rememberKairosActivatable (RememberKairosActivatable.kt:31)");
        }
        composerImpl.startReplaceGroup(-991251959);
        boolean zChanged = composerImpl.changed(obj) | ((((i & 896) ^ 384) > 256 && composerImpl.changed(function0)) || (i & 384) == 256);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion companion = Composer.Companion;
        if (!zChanged) {
            companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = (KairosActivatable) function0.invoke();
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        T t = (T) objRememberedValue;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(-991249329);
        boolean zChangedInstance = composerImpl.changedInstance(kairosNetwork) | composerImpl.changedInstance(t);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (!zChangedInstance) {
            companion.getClass();
            if (objRememberedValue2 == Composer.Companion.Empty) {
                objRememberedValue2 = new RememberKairosActivatableKt$rememberKairosActivatable$1$1(kairosNetwork, t, null);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
        }
        composerImpl.end(false);
        EffectsKt.LaunchedEffect(t, kairosNetwork, (Function2) objRememberedValue2, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return t;
    }
}
