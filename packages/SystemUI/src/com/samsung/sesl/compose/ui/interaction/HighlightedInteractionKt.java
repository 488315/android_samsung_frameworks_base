package com.samsung.sesl.compose.ui.interaction;

import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class HighlightedInteractionKt {
    public static final MutableState collectIsHighlightedAsState(InteractionSource interactionSource, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(675434129);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.ui.interaction.collectIsHighlightedAsState (HighlightedInteraction.kt:20)");
        }
        composerImpl.startReplaceGroup(1293186410);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (rememberedValue == composer$Companion$Empty$1) {
            rememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
            composerImpl.updateRememberedValue(rememberedValue);
        }
        MutableState mutableState = (MutableState) rememberedValue;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(1293189161);
        boolean z = (((i & 14) ^ 6) > 4 && composerImpl.changed(interactionSource)) || (i & 6) == 4;
        Object rememberedValue2 = composerImpl.rememberedValue();
        if (z || rememberedValue2 == composer$Companion$Empty$1) {
            rememberedValue2 = new HighlightedInteractionKt$collectIsHighlightedAsState$1$1(interactionSource, mutableState, null);
            composerImpl.updateRememberedValue(rememberedValue2);
        }
        composerImpl.end(false);
        EffectsKt.LaunchedEffect(composerImpl, interactionSource, (Function2) rememberedValue2);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return mutableState;
    }
}
