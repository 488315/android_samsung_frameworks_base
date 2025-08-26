package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass;
import androidx.compose.material3.windowsizeclass.WindowSizeClass;
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import com.android.compose.windowsizeclass.WindowSizeClassKt;

/* loaded from: classes2.dex */
public abstract class ShortcutHelperUtilsKt {
    /* JADX WARN: Removed duplicated region for block: B:10:0x0035  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final boolean hasCompactWindowSize(Composer composer) {
        boolean z;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(392261908);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.keyboard.shortcut.ui.composable.hasCompactWindowSize (ShortcutHelperUtils.kt:33)");
        }
        StaticProvidableCompositionLocal staticProvidableCompositionLocal = WindowSizeClassKt.LocalWindowSizeClass;
        int i = ((WindowSizeClass) composerImpl.consume(staticProvidableCompositionLocal)).widthSizeClass;
        WindowWidthSizeClass.Companion.getClass();
        if (i == 0) {
            z = true;
        } else {
            int i2 = ((WindowSizeClass) composerImpl.consume(staticProvidableCompositionLocal)).heightSizeClass;
            WindowHeightSizeClass.Companion.getClass();
            if (i2 != 0) {
                z = false;
            }
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return z;
    }
}
