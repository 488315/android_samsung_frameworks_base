package com.android.systemui.keyguard;

import android.os.Build;
import android.os.SystemProperties;
import com.android.systemui.util.SafeUIState;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class KeyguardViewMediatorHelperImplKt {
    public static final boolean DEBUG_DISABLE_REMOTE_UNLOCK_ANIMATION;
    public static final boolean IS_SAFE_MODE_ENABLED;

    static {
        boolean z = false;
        if (Build.IS_USERDEBUG && SystemProperties.getBoolean("debug.keyguard.disable_unlock_animation", false)) {
            z = true;
        }
        DEBUG_DISABLE_REMOTE_UNLOCK_ANIMATION = z;
        IS_SAFE_MODE_ENABLED = SafeUIState.isSysUiSafeModeEnabled();
    }
}
