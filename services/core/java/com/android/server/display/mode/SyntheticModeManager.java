package com.android.server.display.mode;

import com.android.server.display.feature.DisplayManagerFlags;

import com.samsung.android.rune.CoreRune;

public final class SyntheticModeManager {
    public final boolean mSynthetic60HzModesEnabled;

    public SyntheticModeManager(DisplayManagerFlags displayManagerFlags) {
        this.mSynthetic60HzModesEnabled =
                displayManagerFlags.mSynthetic60hzModes.isEnabled() || CoreRune.FW_VRR_DISCRETE;
    }
}
