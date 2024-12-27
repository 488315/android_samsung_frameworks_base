package com.samsung.android.lib.dexcontrol.fancontrol.fanmode;

import com.samsung.android.lib.dexcontrol.fancontrol.DexFanControlManager;
import com.samsung.android.lib.dexcontrol.utils.SLog;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class NonDexMode implements IFanMode {
    public Map mFanLevelTable;
    public DexFanControlManager mIDexFanModeControl;

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.fanmode.IFanMode
    public final void destroy() {
        Map map = this.mFanLevelTable;
        if (map != null) {
            map.clear();
            this.mFanLevelTable = null;
        }
        this.mIDexFanModeControl = null;
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.fanmode.IFanMode
    public final DexFanControlManager.FAN_LEVEL getCurrentFanLevel() {
        SLog.d("NonDexMode", "  " + this.mIDexFanModeControl.mSiopLevel);
        if (this.mFanLevelTable == null) {
            HashMap hashMap = new HashMap();
            this.mFanLevelTable = hashMap;
            DexFanControlManager.SIOP_LEVEL siop_level = DexFanControlManager.SIOP_LEVEL.MINUS_3;
            DexFanControlManager.FAN_LEVEL fan_level = DexFanControlManager.FAN_LEVEL.STOP;
            hashMap.put(siop_level, fan_level);
            ((HashMap) this.mFanLevelTable).put(DexFanControlManager.SIOP_LEVEL.MINUS_2, fan_level);
            ((HashMap) this.mFanLevelTable).put(DexFanControlManager.SIOP_LEVEL.MINUS_1, fan_level);
            ((HashMap) this.mFanLevelTable).put(DexFanControlManager.SIOP_LEVEL.ZERO, fan_level);
            ((HashMap) this.mFanLevelTable).put(DexFanControlManager.SIOP_LEVEL.PLUS_1, fan_level);
            ((HashMap) this.mFanLevelTable).put(DexFanControlManager.SIOP_LEVEL.PLUS_2, fan_level);
            ((HashMap) this.mFanLevelTable).put(DexFanControlManager.SIOP_LEVEL.PLUS_3, fan_level);
        }
        return (DexFanControlManager.FAN_LEVEL) ((HashMap) this.mFanLevelTable).get(this.mIDexFanModeControl.mSiopLevel);
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.fanmode.IFanMode
    public final void onChangedDexMode() {
        DexFanControlManager dexFanControlManager = this.mIDexFanModeControl;
        if (dexFanControlManager.mIsExistSystemRequest) {
            dexFanControlManager.setFanMode(DexFanControlManager.FanModeEnum.SystemRequestMode);
        } else {
            dexFanControlManager.setFanMode(DexFanControlManager.FanModeEnum.NoramlMode);
        }
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.fanmode.IFanMode
    public final synchronized void onChangedFanHoldingRequestCount(int i) {
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.fanmode.IFanMode
    public final void onChangedSystemRequestStatus(boolean z) {
    }
}
