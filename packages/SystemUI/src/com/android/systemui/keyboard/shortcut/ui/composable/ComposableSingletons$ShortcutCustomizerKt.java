package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ComposableSingletons$ShortcutCustomizerKt {
    public static final ComposableSingletons$ShortcutCustomizerKt INSTANCE = new ComposableSingletons$ShortcutCustomizerKt();

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f43lambda1 = new ComposableLambdaImpl(-995813706, false, new Function2() { // from class: com.android.systemui.keyboard.shortcut.ui.composable.ComposableSingletons$ShortcutCustomizerKt$lambda-1$1
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
                ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ComposableSingletons$ShortcutCustomizerKt.lambda-1.<anonymous> (ShortcutCustomizer.kt:311)");
            }
            ShortcutCustomizerKt.PressKeyPrompt(0, composer);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return Unit.INSTANCE;
        }
    });
}
