package com.android.systemui.qs.panels.ui.compose.selection;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.VisibilityThresholdsKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.Size;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Lambda;

/* loaded from: classes2.dex */
public final class SelectionKt$animateSize$$inlined$animateSize$1 extends Lambda implements Function3 {
    public static final SelectionKt$animateSize$$inlined$animateSize$1 INSTANCE = new SelectionKt$animateSize$$inlined$animateSize$1();

    public SelectionKt$animateSize$$inlined$animateSize$1() {
        super(3);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ((Number) obj3).intValue();
        ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
        composerImpl.startReplaceGroup(-1607152761);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.core.animateSize.<anonymous> (Transition.kt:1998)");
        }
        Size.Companion companion = Size.Companion;
        Rect rect = VisibilityThresholdsKt.RectVisibilityThreshold;
        SpringSpec springSpecSpring$default = AnimationSpecKt.spring$default(0.0f, 0.0f, Size.m415boximpl((Float.floatToRawIntBits(0.5f) & 4294967295L) | (Float.floatToRawIntBits(0.5f) << 32)), 3);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return springSpecSpring$default;
    }
}
