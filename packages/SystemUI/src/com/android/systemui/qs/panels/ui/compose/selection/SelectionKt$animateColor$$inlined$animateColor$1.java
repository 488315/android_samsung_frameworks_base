package com.android.systemui.qs.panels.ui.compose.selection;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Lambda;

/* loaded from: classes2.dex */
public final class SelectionKt$animateColor$$inlined$animateColor$1 extends Lambda implements Function3 {
    public static final SelectionKt$animateColor$$inlined$animateColor$1 INSTANCE = new SelectionKt$animateColor$$inlined$animateColor$1();

    public SelectionKt$animateColor$$inlined$animateColor$1() {
        super(3);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ((Number) obj3).intValue();
        ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
        composerImpl.startReplaceGroup(-1457805428);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.animateColor.<anonymous> (Transition.kt:62)");
        }
        SpringSpec springSpecSpring$default = AnimationSpecKt.spring$default(0.0f, 0.0f, null, 7);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return springSpecSpring$default;
    }
}
