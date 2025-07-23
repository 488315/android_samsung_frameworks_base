package com.android.systemui.keyguard;

import android.os.Build;
import android.os.SystemProperties;
import android.view.RemoteAnimationTarget;
import com.android.systemui.util.SafeUIState;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class KeyguardViewMediatorHelperImplKt {
    public static final boolean DEBUG_DISABLE_REMOTE_UNLOCK_ANIMATION;
    public static final boolean IS_SAFE_MODE_ENABLED;
    public static RemoteAnimationTarget aodAppearWallpaperOpeningTarget;
    public static boolean isLockShownDelay;

    static {
        boolean z = false;
        if (Build.IS_USERDEBUG && SystemProperties.getBoolean("debug.keyguard.disable_unlock_animation", false)) {
            z = true;
        }
        DEBUG_DISABLE_REMOTE_UNLOCK_ANIMATION = z;
        IS_SAFE_MODE_ENABLED = SafeUIState.isSysUiSafeModeEnabled();
    }
}
