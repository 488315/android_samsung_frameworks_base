package com.android.systemui.keyguard;

import android.os.Build;
import android.os.SystemProperties;
import android.view.RemoteAnimationTarget;
import com.android.systemui.util.SafeUIState;

public abstract class KeyguardViewMediatorHelperImplKt {
    public static final boolean DEBUG_DISABLE_REMOTE_UNLOCK_ANIMATION;
    public static final boolean IS_SAFE_MODE_ENABLED;
    public static RemoteAnimationTarget aodAppearWallpaperOpeningTarget;

    static {
        boolean z = false;
        if (Build.IS_USERDEBUG && SystemProperties.getBoolean("debug.keyguard.disable_unlock_animation", false)) {
            z = true;
        }
        DEBUG_DISABLE_REMOTE_UNLOCK_ANIMATION = z;
        IS_SAFE_MODE_ENABLED = SafeUIState.isSysUiSafeModeEnabled();
    }
}
