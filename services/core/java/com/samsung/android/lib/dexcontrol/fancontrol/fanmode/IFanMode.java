package com.samsung.android.lib.dexcontrol.fancontrol.fanmode;

import com.samsung.android.lib.dexcontrol.fancontrol.DexFanControlManager;

/* loaded from: classes2.dex */
public interface IFanMode {
    void destroy();

    DexFanControlManager.FAN_LEVEL getCurrentFanLevel();

    void onChangedDexMode();

    void onChangedFanHoldingRequestCount(int i);

    void onChangedSystemRequestStatus(boolean z);
}
