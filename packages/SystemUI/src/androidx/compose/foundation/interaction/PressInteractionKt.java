package androidx.compose.foundation.interaction;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class PressInteractionKt {
    public static final MutableState collectIsPressedAsState(InteractionSource interactionSource, Composer composer, int i) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.interaction.collectIsPressedAsState (PressInteraction.kt:80)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (objRememberedValue == composer$Companion$Empty$1) {
            objRememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        MutableState mutableState = (MutableState) objRememberedValue;
        boolean z = (((i & 14) ^ 6) > 4 && composerImpl.changed(interactionSource)) || (i & 6) == 4;
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (z || objRememberedValue2 == composer$Companion$Empty$1) {
            objRememberedValue2 = new PressInteractionKt$collectIsPressedAsState$1$1(interactionSource, mutableState, null);
            composerImpl.updateRememberedValue(objRememberedValue2);
        }
        EffectsKt.LaunchedEffect(composerImpl, interactionSource, (Function2) objRememberedValue2);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return mutableState;
    }
}
