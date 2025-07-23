package com.samsung.sesl.compose.foundation;

import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.HoverableKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValuesImpl;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.RectangleShapeKt$RectangleShape$1;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.unit.Dp;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class RecoilKt {
    public static final DynamicProvidableCompositionLocal LocalSeslRecoilConfiguration = CompositionLocalKt.compositionLocalOf$default(new RecoilKt$$ExternalSyntheticLambda0());

    /* renamed from: seslRecoil-3f6hBDE, reason: not valid java name */
    public static final Modifier m3333seslRecoil3f6hBDE(Modifier modifier, MutableInteractionSource mutableInteractionSource, RecoilType recoilType, Shape shape, long j, boolean z, PaddingValuesImpl paddingValuesImpl, Composer composer, int i, int i2) {
        long j2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(71592752);
        if ((i2 & 8) != 0) {
            Color.Companion.getClass();
            j2 = Color.Unspecified;
        } else {
            j2 = j;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.seslRecoil (Recoil.kt:190)");
        }
        float scaleRatio = recoilType.getScaleRatio();
        SeslRecoilDrawStrategy drawStrategy = recoilType.getDrawStrategy();
        composerImpl.startReplaceGroup(2021106952);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.seslRecoil (Recoil.kt:211)");
        }
        if (j2 == 16) {
            SeslFeedbackDefaults.INSTANCE.getClass();
            j2 = SeslFeedbackDefaults.m3335colorsWaAFU9c(composerImpl);
        }
        long j3 = j2;
        Modifier then = FocusableKt.focusable(mutableInteractionSource, HoverableKt.hoverable(mutableInteractionSource, modifier, z), z).then(new SeslTouchableModifierElement(z, mutableInteractionSource, null));
        SeslFeedbackAlpha.Companion.getClass();
        Modifier then2 = then.then(new SeslRecoilModifierElement(mutableInteractionSource, z, scaleRatio, j3, shape, paddingValuesImpl, SeslFeedbackAlpha.Unspecified, drawStrategy, null));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return then2;
    }

    /* renamed from: seslRecoil-fWhpE4E, reason: not valid java name */
    public static final Modifier m3334seslRecoilfWhpE4E(Modifier modifier, boolean z, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(244001007);
        RecoilType recoilType = RecoilType.List;
        RectangleShapeKt$RectangleShape$1 rectangleShapeKt$RectangleShape$1 = RectangleShapeKt.RectangleShape;
        Color.Companion.getClass();
        long j = Color.Unspecified;
        Dp.Companion companion = Dp.Companion;
        PaddingValuesImpl m119PaddingValues0680j_4 = PaddingKt.m119PaddingValues0680j_4(0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.seslRecoil (Recoil.kt:161)");
        }
        composerImpl.startReplaceGroup(753084145);
        Object rememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        if (rememberedValue == Composer.Companion.Empty) {
            rememberedValue = InteractionSourceKt.MutableInteractionSource();
            composerImpl.updateRememberedValue(rememberedValue);
        }
        composerImpl.end(false);
        Modifier m3333seslRecoil3f6hBDE = m3333seslRecoil3f6hBDE(modifier, (MutableInteractionSource) rememberedValue, recoilType, rectangleShapeKt$RectangleShape$1, j, z, m119PaddingValues0680j_4, composerImpl, 48 | ((i << 3) & 458752), 0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return m3333seslRecoil3f6hBDE;
    }
}
