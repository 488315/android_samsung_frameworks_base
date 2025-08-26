package com.android.systemui.lifecycle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public abstract class ActivatableKt {
    /* JADX WARN: Removed duplicated region for block: B:14:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Activatable rememberActivated(String str, Function0 function0, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-317888658);
        Unit unit = Unit.INSTANCE;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.lifecycle.rememberActivated (Activatable.kt:80)");
        }
        composerImpl.startReplaceGroup(1312068444);
        boolean zChanged = composerImpl.changed(unit);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion companion = Composer.Companion;
        if (!zChanged) {
            companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                objRememberedValue = (Activatable) function0.invoke();
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        Activatable activatable = (Activatable) objRememberedValue;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(1312070294);
        boolean zChangedInstance = composerImpl.changedInstance(activatable);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (!zChangedInstance) {
            companion.getClass();
            if (objRememberedValue2 == Composer.Companion.Empty) {
                objRememberedValue2 = new ActivatableKt$rememberActivated$1$1(str, activatable, null);
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
        }
        composerImpl.end(false);
        EffectsKt.LaunchedEffect(composerImpl, activatable, (Function2) objRememberedValue2);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return activatable;
    }
}
