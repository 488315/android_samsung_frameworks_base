package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final class ComposableSingletons$PhoneScreenKt {
    public static final ComposableSingletons$PhoneScreenKt INSTANCE = new ComposableSingletons$PhoneScreenKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f75lambda1 = new ComposableLambdaImpl(449372587, false, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$PhoneScreenKt$lambda-1$1
        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
        @Override // kotlin.jvm.functions.Function2
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 3) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                } else {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$PhoneScreenKt.lambda-1.<anonymous> (PhoneScreen.kt:45)");
                    }
                    MediaCardKt.MediaCard(null, null, composer, 0, 3);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
            return Unit.INSTANCE;
        }
    });
}
