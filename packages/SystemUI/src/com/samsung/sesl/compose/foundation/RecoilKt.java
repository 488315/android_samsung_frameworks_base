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
import com.samsung.sesl.compose.foundation.shape.SeslRoundedCornerShape;
import kotlin.NoWhenBranchMatchedException;

/* loaded from: classes4.dex */
public abstract class RecoilKt {
    public static final DynamicProvidableCompositionLocal LocalSeslRecoilConfiguration = CompositionLocalKt.compositionLocalOf$default(new RecoilKt$$ExternalSyntheticLambda0());

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[RecoilType.values().length];
            try {
                iArr[RecoilType.Card.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[RecoilType.List.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[RecoilType.Button.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[RecoilType.IconButton.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* renamed from: seslRecoil-3f6hBDE, reason: not valid java name */
    public static final Modifier m3350seslRecoil3f6hBDE(Modifier modifier, MutableInteractionSource mutableInteractionSource, SeslRecoilPreset seslRecoilPreset, Shape shape, long j, boolean z, PaddingValuesImpl paddingValuesImpl, Composer composer, int i, int i2) {
        long jM3353colorsWaAFU9c;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1179904992);
        if ((i2 & 8) != 0) {
            Color.Companion.getClass();
            jM3353colorsWaAFU9c = Color.Unspecified;
        } else {
            jM3353colorsWaAFU9c = j;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.seslRecoil (Recoil.kt:283)");
        }
        SeslRecoilParameter parameter$sesl8_compose_core_release = seslRecoilPreset.getParameter$sesl8_compose_core_release();
        composerImpl.startReplaceGroup(510261066);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.seslRecoil (Recoil.kt:314)");
        }
        float f = parameter$sesl8_compose_core_release.scaleRatio;
        composerImpl.startReplaceGroup(2021106952);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.seslRecoil (Recoil.kt:336)");
        }
        if (jM3353colorsWaAFU9c == 16) {
            SeslFeedbackDefaults.INSTANCE.getClass();
            jM3353colorsWaAFU9c = SeslFeedbackDefaults.m3353colorsWaAFU9c(composerImpl);
        }
        long j2 = jM3353colorsWaAFU9c;
        Modifier modifierFocusable = FocusableKt.focusable(mutableInteractionSource, HoverableKt.hoverable(mutableInteractionSource, modifier.then(new SeslTouchableModifierElement(z, mutableInteractionSource, new SeslTouchableKt$$ExternalSyntheticLambda0())), z), z);
        SeslFeedbackAlpha.Companion.getClass();
        Modifier modifierThen = modifierFocusable.then(new SeslRecoilModifierElement(mutableInteractionSource, z, f, j2, shape, paddingValuesImpl, SeslFeedbackAlpha.Unspecified, parameter$sesl8_compose_core_release.drawStrategy, null));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return modifierThen;
    }

    /* renamed from: seslRecoil-fWhpE4E, reason: not valid java name */
    public static final Modifier m3351seslRecoilfWhpE4E(Modifier modifier, boolean z, Composer composer, int i) {
        SeslRecoilPreset seslRecoilPreset;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(244001007);
        RecoilType recoilType = RecoilType.List;
        RectangleShapeKt$RectangleShape$1 rectangleShapeKt$RectangleShape$1 = RectangleShapeKt.RectangleShape;
        Color.Companion.getClass();
        long j = Color.Unspecified;
        Dp.Companion companion = Dp.Companion;
        PaddingValuesImpl paddingValuesImplM120PaddingValues0680j_4 = PaddingKt.m120PaddingValues0680j_4(0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.seslRecoil (Recoil.kt:213)");
        }
        composerImpl.startReplaceGroup(753146929);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        if (objRememberedValue == Composer.Companion.Empty) {
            objRememberedValue = InteractionSourceKt.MutableInteractionSource();
            composerImpl.updateRememberedValue(objRememberedValue);
        }
        MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) objRememberedValue;
        composerImpl.end(false);
        int i2 = ((i << 3) & 458752) | 384;
        composerImpl.startReplaceGroup(-1089489946);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.seslRecoil (Recoil.kt:247)");
        }
        int i3 = WhenMappings.$EnumSwitchMapping$0[recoilType.ordinal()];
        if (i3 == 1) {
            seslRecoilPreset = SeslRecoilPreset.Card;
        } else if (i3 == 2) {
            seslRecoilPreset = SeslRecoilPreset.List;
        } else if (i3 == 3) {
            seslRecoilPreset = SeslRecoilPreset.Button;
        } else {
            if (i3 != 4) {
                throw new NoWhenBranchMatchedException();
            }
            seslRecoilPreset = SeslRecoilPreset.IconButton;
        }
        Modifier modifierM3350seslRecoil3f6hBDE = m3350seslRecoil3f6hBDE(modifier, mutableInteractionSource, seslRecoilPreset, rectangleShapeKt$RectangleShape$1, j, z, paddingValuesImplM120PaddingValues0680j_4, composerImpl, 48 | (i2 & 458752), 0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return modifierM3350seslRecoil3f6hBDE;
    }

    /* renamed from: seslRecoilIndication-bw27NRU$default, reason: not valid java name */
    public static SeslRecoilNodeFactory m3352seslRecoilIndicationbw27NRU$default(SeslRecoilPreset seslRecoilPreset, SeslRoundedCornerShape seslRoundedCornerShape, int i) {
        if ((i & 1) != 0) {
            seslRecoilPreset = SeslRecoilPreset.Button;
        }
        Color.Companion.getClass();
        long j = Color.Unspecified;
        Shape shape = seslRoundedCornerShape;
        if ((i & 4) != 0) {
            shape = RectangleShapeKt.RectangleShape;
        }
        SeslRecoilParameter parameter$sesl8_compose_core_release = seslRecoilPreset.getParameter$sesl8_compose_core_release();
        return new SeslRecoilNodeFactory(j, parameter$sesl8_compose_core_release.scaleRatio, shape, parameter$sesl8_compose_core_release.drawStrategy, null);
    }
}
