package com.android.systemui.util.composable.kairos;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import com.android.systemui.kairos.KairosNetwork;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
public final class ActivatedKairosSpecKt {
    public static final <T> void ActivatedKairosSpec(Function1 function1, KairosNetwork kairosNetwork, final Function3 function3, Composer composer, final int i) {
        int i2;
        Object activatedKairosSpecKt$ActivatedKairosSpec$1$1;
        final Function1 function12;
        final KairosNetwork kairosNetwork2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(893325741);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(function1) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(kairosNetwork) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function3) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            function12 = function1;
            kairosNetwork2 = kairosNetwork;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.util.composable.kairos.ActivatedKairosSpec (ActivatedKairosSpec.kt:42)");
            }
            Object obj = new Object();
            composerImpl.startReplaceGroup(315701921);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(obj);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            MutableState mutableState = (MutableState) objRememberedValue;
            composerImpl.end(false);
            Unit unit = Unit.INSTANCE;
            composerImpl.startReplaceGroup(315704457);
            boolean zChangedInstance = ((i2 & 14) == 4) | composerImpl.changedInstance(kairosNetwork) | composerImpl.changedInstance(obj);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
                function12 = function1;
                kairosNetwork2 = kairosNetwork;
                activatedKairosSpecKt$ActivatedKairosSpec$1$1 = new ActivatedKairosSpecKt$ActivatedKairosSpec$1$1(kairosNetwork2, function12, mutableState, obj, null);
                composerImpl.updateRememberedValue(activatedKairosSpecKt$ActivatedKairosSpec$1$1);
            } else {
                function12 = function1;
                activatedKairosSpecKt$ActivatedKairosSpec$1$1 = objRememberedValue2;
                kairosNetwork2 = kairosNetwork;
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) activatedKairosSpecKt$ActivatedKairosSpec$1$1);
            Object value = mutableState.getValue();
            composerImpl.startReplaceGroup(315711821);
            if (value != obj) {
                function3.invoke(value, composerImpl, Integer.valueOf((i2 >> 3) & 112));
            }
            composerImpl.end(false);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.util.composable.kairos.ActivatedKairosSpecKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    int iIntValue = ((Integer) obj3).intValue();
                    Function3 function32 = function3;
                    int i3 = i;
                    return ActivatedKairosSpecKt.ActivatedKairosSpec$lambda$5(function12, kairosNetwork2, function32, i3, (Composer) obj2, iIntValue);
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit ActivatedKairosSpec$lambda$5(Function1 function1, KairosNetwork kairosNetwork, Function3 function3, int i, Composer composer, int i2) {
        ActivatedKairosSpec(function1, kairosNetwork, function3, composer, RecomposeScopeImplKt.updateChangedFlags(i | 1));
        return Unit.INSTANCE;
    }
}
