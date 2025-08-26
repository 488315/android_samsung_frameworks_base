package com.android.systemui.volume.panel.component.anc.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
public final class ComposableSingletons$AncButtonComponentKt {
    public static final ComposableSingletons$AncButtonComponentKt INSTANCE = new ComposableSingletons$AncButtonComponentKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f112lambda1 = new ComposableLambdaImpl(-1473503589, false, new Function3() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.ComposableSingletons$AncButtonComponentKt$lambda-1$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x001d  */
        @Override // kotlin.jvm.functions.Function3
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 17) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.anc.ui.composable.ComposableSingletons$AncButtonComponentKt.lambda-1.<anonymous> (AncButtonComponent.kt:103)");
                    }
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });
}
