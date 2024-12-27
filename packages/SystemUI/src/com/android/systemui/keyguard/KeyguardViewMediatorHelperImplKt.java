package com.android.systemui.keyguard;

import android.os.Build;
import android.os.SystemProperties;
import android.view.RemoteAnimationTarget;
import com.android.systemui.util.SafeUIState;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
