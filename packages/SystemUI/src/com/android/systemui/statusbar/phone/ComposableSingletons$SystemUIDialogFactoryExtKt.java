package com.android.systemui.statusbar.phone;

import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Dp;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ComposableSingletons$SystemUIDialogFactoryExtKt {
    public static final ComposableSingletons$SystemUIDialogFactoryExtKt INSTANCE = new ComposableSingletons$SystemUIDialogFactoryExtKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f108lambda1 = new ComposableLambdaImpl(-1370905715, false, new Function2() { // from class: com.android.systemui.statusbar.phone.ComposableSingletons$SystemUIDialogFactoryExtKt$lambda-1$1
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
                ComposerKt.traceEventStart("com.android.systemui.statusbar.phone.ComposableSingletons$SystemUIDialogFactoryExtKt.lambda-1.<anonymous> (SystemUIDialogFactoryExt.kt:297)");
            }
            Dp.Companion companion = Dp.Companion;
            BoxKt.Box(SizeKt.m140sizeVpY3zN4(Modifier.Companion, 32, 4), composer, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });
}
