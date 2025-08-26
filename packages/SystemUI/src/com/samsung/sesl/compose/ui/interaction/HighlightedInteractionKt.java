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

/* loaded from: classes4.dex */
public abstract class HighlightedInteractionKt {
    public static final MutableState collectIsHighlightedAsState(InteractionSource interactionSource, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(675434129);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.ui.interaction.collectIsHighlightedAsState (HighlightedInteraction.kt:20)");
        }
        composerImpl.startReplaceGroup(1293186410);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (objRememberedValue == composer$Companion$Empty$1) {
            objRememberedValue = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        MutableState mutableState = (MutableState) objRememberedValue;
        composerImpl.end(false);
        composerImpl.startReplaceGroup(1293189161);
        boolean z = (((i & 14) ^ 6) > 4 && composerImpl.changed(interactionSource)) || (i & 6) == 4;
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (z || objRememberedValue2 == composer$Companion$Empty$1) {
            objRememberedValue2 = new HighlightedInteractionKt$collectIsHighlightedAsState$1$1(interactionSource, mutableState, null);
            composerImpl.updateRememberedValue(objRememberedValue2);
        }
        composerImpl.end(false);
        EffectsKt.LaunchedEffect(composerImpl, interactionSource, (Function2) objRememberedValue2);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return mutableState;
    }
}
