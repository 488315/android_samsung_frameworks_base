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

/* loaded from: classes2.dex */
public final class ComposableSingletons$SettingHomeKt {
    public static final ComposableSingletons$SettingHomeKt INSTANCE = new ComposableSingletons$SettingHomeKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f79lambda1 = new ComposableLambdaImpl(-1455183949, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$SettingHomeKt$lambda-1$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x001e  */
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
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$SettingHomeKt.lambda-1.<anonymous> (SettingHome.kt:147)");
                    }
                    Dp.Companion companion = Dp.Companion;
                    SpacerKt.Spacer(composer, SizeKt.m131height3ABfNKs(Modifier.Companion, 10));
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });
}
