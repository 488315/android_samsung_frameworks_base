package com.android.systemui.communal.ui.compose.section;

import android.view.WindowManager;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.log.LogBuffer;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class CommunalLockSection {
    public final CoroutineScope applicationScope;
    public final AuthController authController;
    public final Lazy falsingManager;
    public final FeatureFlagsClassic featureFlags;
    public final LogBuffer logBuffer;
    public final Lazy vibratorHelper;
    public final Lazy viewModel;
    public final WindowManager windowManager;

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

    public CommunalLockSection(CoroutineScope coroutineScope, WindowManager windowManager, AuthController authController, Lazy lazy, Lazy lazy2, Lazy lazy3, FeatureFlagsClassic featureFlagsClassic, LogBuffer logBuffer) {
        this.applicationScope = coroutineScope;
        this.windowManager = windowManager;
        this.authController = authController;
        this.viewModel = lazy;
        this.falsingManager = lazy2;
        this.vibratorHelper = lazy3;
        this.featureFlags = featureFlagsClassic;
        this.logBuffer = logBuffer;
    }
}
