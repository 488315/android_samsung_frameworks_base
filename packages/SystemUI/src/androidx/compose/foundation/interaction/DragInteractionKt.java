package androidx.compose.foundation.interaction;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DragInteractionKt {
    public static final MutableState collectIsDraggedAsState(InteractionSource interactionSource, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.interaction.collectIsDraggedAsState (DragInteraction.kt:77)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        MutableState mutableState = (MutableState) rememberedValue;
        boolean changed = composerImpl.changed(interactionSource);
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (changed || rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 = new DragInteractionKt$collectIsDraggedAsState$1$1(interactionSource, mutableState, null);
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        EffectsKt.LaunchedEffect(composerImpl, interactionSource, (Function2) rememberedValue2);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return mutableState;
    }
}
