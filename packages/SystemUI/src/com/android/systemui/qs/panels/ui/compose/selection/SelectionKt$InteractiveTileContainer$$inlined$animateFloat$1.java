package com.android.systemui.qs.panels.ui.compose.selection;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SelectionKt$InteractiveTileContainer$$inlined$animateFloat$1 extends Lambda implements Function3 {
    public static final SelectionKt$InteractiveTileContainer$$inlined$animateFloat$1 INSTANCE = new SelectionKt$InteractiveTileContainer$$inlined$animateFloat$1();

    public SelectionKt$InteractiveTileContainer$$inlined$animateFloat$1() {
        super(3);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ((Number) obj3).intValue();
        ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj2);
        composerImpl.startReplaceGroup(-522164544);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.animation.core.animateFloat.<anonymous> (Transition.kt:1908)");
        }
        SpringSpec spring$default = AnimationSpecKt.spring$default(0.0f, 0.0f, null, 7);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return spring$default;
    }
}
