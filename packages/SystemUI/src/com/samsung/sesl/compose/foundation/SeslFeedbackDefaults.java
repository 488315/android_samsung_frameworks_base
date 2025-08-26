package com.samsung.sesl.compose.foundation;

import androidx.compose.animation.core.CubicBezierEasing;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import com.samsung.sesl.compose.component.tokens.SeslCommonColorSchemeKeyTokens;
import com.samsung.sesl.compose.foundation.theme.BasicColorSchemeKt;

/* loaded from: classes4.dex */
public final class SeslFeedbackDefaults {
    public static final SeslFeedbackDefaults INSTANCE = new SeslFeedbackDefaults();
    public static final CubicBezierEasing FEEDBACK_ANIMATION_DOWN_EASING = new CubicBezierEasing(0.0f, 0.0f, 1.0f, 1.0f);
    public static final CubicBezierEasing FEEDBACK_ANIMATION_UP_EASING = new CubicBezierEasing(0.17f, 0.17f, 0.67f, 1.0f);
    public static final SeslFeedbackAlpha feedbackAlpha = new SeslFeedbackAlpha(0.0f, 0.8f, 0.6f, 1.0f);

    private SeslFeedbackDefaults() {
    }

    /* renamed from: colors-WaAFU9c, reason: not valid java name */
    public static long m3353colorsWaAFU9c(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(970554861);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.foundation.SeslFeedbackDefaults.colors (Feedback.kt:298)");
        }
        long color = BasicColorSchemeKt.toColor(SeslCommonColorSchemeKeyTokens.Ripple, composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return color;
    }
}
