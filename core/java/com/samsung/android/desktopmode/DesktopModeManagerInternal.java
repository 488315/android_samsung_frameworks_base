package com.samsung.android.desktopmode;

import android.content.res.Configuration;
import android.os.Bundle;

/* loaded from: classes6.dex */
public abstract class DesktopModeManagerInternal {
    public static final int MODETOMODE_DUAL_TO_STANDALONE = 1;
    public static final int MODETOMODE_NONE = -1;
    public static final int MODETOMODE_STANDALONE_TO_DUAL = 2;
    public static final int TOUCHPAD_FEATURE_BIT = 1;
    public static final int TOUCHPAD_FEATURE_NONE = 0;
    public static final int TOUCHPAD_FEATURE_SPEN_BIT = 2;

    public abstract int getCurrentUiMode();

    public abstract Bundle getDesktopModeKillPolicy();

    public abstract SemDesktopModeState getDesktopModeState();

    public abstract int getDexHDMIAutoEnterState();

    public abstract int getModeToModeChangeType();

    public abstract int getTouchpadSupportedFeatures();

    public abstract boolean isConfigurationChangedFromDeX(Configuration configuration);

    public abstract boolean isDesktopModeAvailableEx(boolean z, boolean z2);

    public abstract boolean isDesktopModeEnablingOrEnabled();

    public abstract boolean isDesktopModeForPreparing();

    public abstract boolean isDesktopModeForPreparing(int i);

    public abstract boolean isExternalDisplayConnected();

    public abstract boolean isForcedInternalScreenModeEnabled();

    public abstract boolean isLockTaskModeEnabledAndSecured();

    public abstract boolean isModeChangePending();

    public abstract boolean isModeChangePending(int i);

    public abstract void onConfigurationChanged(Configuration configuration);

    public abstract void onDesktopDisplayConfigured(boolean z);

    public abstract void onSecuredAppLaunched(int i, String str);

    public abstract void scheduleUpdateDesktopMode(boolean z);

    public abstract Bundle sendMessage(Bundle bundle);

    public abstract int setDexHDMIAutoEnterState(int i);

    public abstract void startHome();
}
