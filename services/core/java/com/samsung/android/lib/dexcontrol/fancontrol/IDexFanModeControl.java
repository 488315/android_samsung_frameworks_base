package com.samsung.android.lib.dexcontrol.fancontrol;

import com.samsung.android.lib.dexcontrol.fancontrol.DexFanControlManager;

/* loaded from: classes2.dex */
public interface IDexFanModeControl {
    void clearAllFanHoldingTimerTask();

    DexFanControlManager.SIOP_LEVEL getSiopLevel();

    boolean isExistSystemRequest();

    void onFinishedFanHoldingTimerTask(DexFanHoldingTimerTask dexFanHoldingTimerTask);

    void setFanMode(DexFanControlManager.FanModeEnum fanModeEnum);
}
