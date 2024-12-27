package com.android.server.display.mode;

import com.android.server.display.feature.DisplayManagerFlags;

import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SyntheticModeManager {
    public final boolean mSynthetic60HzModesEnabled;

    public SyntheticModeManager(DisplayManagerFlags displayManagerFlags) {
        this.mSynthetic60HzModesEnabled =
                displayManagerFlags.mSynthetic60hzModes.isEnabled() || CoreRune.FW_VRR_DISCRETE;
    }
}
