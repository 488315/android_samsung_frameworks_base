package com.android.systemui.media;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import com.android.systemui.media.mediaoutput.compose.MediaOutputHostKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ComposableSingletons$MediaOutputViewKt {
    public static final ComposableSingletons$MediaOutputViewKt INSTANCE = new ComposableSingletons$MediaOutputViewKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f50lambda1 = new ComposableLambdaImpl(746142185, false, new Function2() { // from class: com.android.systemui.media.ComposableSingletons$MediaOutputViewKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Composer composer = (Composer) obj;
            if ((((Number) obj2).intValue() & 3) == 2) {
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.getSkipping()) {
                    composerImpl.skipToGroupEnd();
                    return Unit.INSTANCE;
                }
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.media.ComposableSingletons$MediaOutputViewKt.lambda-1.<anonymous> (MediaOutputView.kt:86)");
            }
            MediaOutputHostKt.MediaOutputHost(null, composer, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });
}
