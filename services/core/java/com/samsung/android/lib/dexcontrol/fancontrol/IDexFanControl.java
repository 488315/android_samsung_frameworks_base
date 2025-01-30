package com.samsung.android.lib.dexcontrol.fancontrol;

import com.samsung.android.lib.dexcontrol.fancontrol.DexFanControlManager;

/* loaded from: classes2.dex */
public interface IDexFanControl {
    void destroy();

    void onDexFanLevelUpdated(int i);

    void onFanHoldingEvent(String str, int i);

    void setFanLevelForTest(DexFanControlManager.FAN_LEVEL fan_level);

    void setSiopLevel(int i);

    void setSystemRequest(boolean z);
}
