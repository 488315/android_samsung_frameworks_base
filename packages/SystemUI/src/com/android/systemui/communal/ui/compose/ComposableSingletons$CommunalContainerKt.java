package com.android.systemui.communal.ui.compose;

import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ComposableSingletons$CommunalContainerKt {
    public static final ComposableSingletons$CommunalContainerKt INSTANCE = new ComposableSingletons$CommunalContainerKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f28lambda1 = new ComposableLambdaImpl(1663018362, false, new Function3() { // from class: com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalContainerKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            Composer composer = (Composer) obj2;
            if ((((Number) obj3).intValue() & 17) == 16) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.ComposableSingletons$CommunalContainerKt.lambda-1.<anonymous> (CommunalContainer.kt:253)");
            }
            BoxKt.Box(SizeKt.fillMaxSize(Modifier.Companion, 1.0f), composer, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });
}
