package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ComposableSingletons$SpotifyCastSettingScreenKt {
    public static final ComposableSingletons$SpotifyCastSettingScreenKt INSTANCE = new ComposableSingletons$SpotifyCastSettingScreenKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f80lambda1 = new ComposableLambdaImpl(-1692530238, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$SpotifyCastSettingScreenKt$lambda-1$1
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
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$SpotifyCastSettingScreenKt.lambda-1.<anonymous> (SpotifyCastSettingScreen.kt:87)");
            }
            Dp.Companion companion = Dp.Companion;
            SpacerKt.Spacer(composer, SizeKt.m130height3ABfNKs(Modifier.Companion, 10));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });
}
