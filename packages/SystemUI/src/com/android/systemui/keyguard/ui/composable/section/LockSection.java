package com.android.systemui.keyguard.ui.composable.section;

import android.view.WindowManager;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.log.LogBuffer;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LockSection {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope applicationScope;
    public final AuthController authController;
    public final Lazy deviceEntryBackgroundViewModel;
    public final Lazy deviceEntryForegroundViewModel;
    public final Lazy deviceEntryIconViewModel;
    public final Lazy falsingManager;
    public final FeatureFlagsClassic featureFlags;
    public final LogBuffer logBuffer;
    public final CoroutineDispatcher mainDispatcher;
    public final Lazy msdlPlayer;
    public final Lazy vibratorHelper;
    public final WindowManager windowManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public LockSection(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, WindowManager windowManager, AuthController authController, FeatureFlagsClassic featureFlagsClassic, Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, Lazy lazy6, LogBuffer logBuffer) {
        this.applicationScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.windowManager = windowManager;
        this.authController = authController;
        this.featureFlags = featureFlagsClassic;
        this.deviceEntryIconViewModel = lazy;
        this.deviceEntryForegroundViewModel = lazy2;
        this.deviceEntryBackgroundViewModel = lazy3;
        this.falsingManager = lazy4;
        this.vibratorHelper = lazy5;
        this.msdlPlayer = lazy6;
        this.logBuffer = logBuffer;
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x0095, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00c5, code lost:
    
        if (r7 == androidx.compose.runtime.Composer.Companion.Empty) goto L53;
     */
    /* renamed from: LockIcon-BAq54LU, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m2603LockIconBAq54LU(final com.android.compose.animation.scene.ContentScope r12, final androidx.compose.ui.graphics.Color r13, androidx.compose.ui.Modifier r14, androidx.compose.runtime.Composer r15, final int r16, final int r17) {
        /*
            Method dump skipped, instructions count: 253
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.composable.section.LockSection.m2603LockIconBAq54LU(com.android.compose.animation.scene.ContentScope, androidx.compose.ui.graphics.Color, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int, int):void");
    }
}
