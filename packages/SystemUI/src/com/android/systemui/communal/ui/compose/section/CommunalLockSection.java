package com.android.systemui.communal.ui.compose.section;

import android.view.WindowManager;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.log.LogBuffer;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
