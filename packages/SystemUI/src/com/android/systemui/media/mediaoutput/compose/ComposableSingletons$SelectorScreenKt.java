package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.compose.ext.CharSequenceExtKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ComposableSingletons$SelectorScreenKt {
    public static final ComposableSingletons$SelectorScreenKt INSTANCE = new ComposableSingletons$SelectorScreenKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f76lambda1 = new ComposableLambdaImpl(303712980, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$SelectorScreenKt$lambda-1$1
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
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$SelectorScreenKt.lambda-1.<anonymous> (SelectorScreen.kt:90)");
            }
            SelectorScreenKt.SelectorHeader(CharSequenceExtKt.stringResourceExt(R.string.phone_speaker, composer), composer, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f77lambda2 = new ComposableLambdaImpl(450623193, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$SelectorScreenKt$lambda-2$1
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
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$SelectorScreenKt.lambda-2.<anonymous> (SelectorScreen.kt:100)");
            }
            Dp.Companion companion = Dp.Companion;
            SpacerKt.Spacer(composer, SizeKt.m130height3ABfNKs(Modifier.Companion, 20));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f78lambda3 = new ComposableLambdaImpl(288952338, false, new Function3() { // from class: com.android.systemui.media.mediaoutput.compose.ComposableSingletons$SelectorScreenKt$lambda-3$1
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
                ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ComposableSingletons$SelectorScreenKt.lambda-3.<anonymous> (SelectorScreen.kt:104)");
            }
            SelectorScreenKt.SelectorHeader(StringResources_androidKt.stringResource(R.string.choose_a_device, composer), composer, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });
}
