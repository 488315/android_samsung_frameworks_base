package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ShortcutHelperKt$ShortcutHelper$1 implements Function2 {
    public static final ShortcutHelperKt$ShortcutHelper$1 INSTANCE = new ShortcutHelperKt$ShortcutHelper$1();

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        ((Number) obj2).intValue();
        ComposerImpl composerImpl = (ComposerImpl) ((Composer) obj);
        composerImpl.startReplaceGroup(802821849);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.ShortcutHelper.<anonymous> (ShortcutHelper.kt:139)");
        }
        composerImpl.startReplaceGroup(310118171);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.shouldUseSinglePane (ShortcutHelper.kt:204)");
        }
        boolean hasCompactWindowSize = ShortcutHelperUtilsKt.hasCompactWindowSize(composerImpl);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return Boolean.valueOf(hasCompactWindowSize);
    }
}
