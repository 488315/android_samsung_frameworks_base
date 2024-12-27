package com.samsung.android.lib.dexcontrol.fancontrol.fanmode;

import com.samsung.android.lib.dexcontrol.fancontrol.DexFanControlManager;
import com.samsung.android.lib.dexcontrol.utils.SLog;

import java.util.HashMap;
import java.util.Map;

public final class NormalMode implements IFanMode {
    public int mDexModel;
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
        this.mDexModel = 0;
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.fanmode.IFanMode
    public final DexFanControlManager.FAN_LEVEL getCurrentFanLevel() {
        SLog.d("NormalMode", "  " + this.mIDexFanModeControl.mSiopLevel);
        if (this.mFanLevelTable == null) {
            HashMap hashMap = new HashMap();
            this.mFanLevelTable = hashMap;
            int i = this.mDexModel;
            DexFanControlManager.SIOP_LEVEL siop_level = DexFanControlManager.SIOP_LEVEL.PLUS_3;
            DexFanControlManager.SIOP_LEVEL siop_level2 = DexFanControlManager.SIOP_LEVEL.PLUS_2;
            DexFanControlManager.FAN_LEVEL fan_level = DexFanControlManager.FAN_LEVEL.STRONG;
            DexFanControlManager.SIOP_LEVEL siop_level3 = DexFanControlManager.SIOP_LEVEL.PLUS_1;
            DexFanControlManager.SIOP_LEVEL siop_level4 = DexFanControlManager.SIOP_LEVEL.ZERO;
            DexFanControlManager.FAN_LEVEL fan_level2 = DexFanControlManager.FAN_LEVEL.WEAK;
            DexFanControlManager.SIOP_LEVEL siop_level5 = DexFanControlManager.SIOP_LEVEL.MINUS_1;
            DexFanControlManager.SIOP_LEVEL siop_level6 = DexFanControlManager.SIOP_LEVEL.MINUS_2;
            DexFanControlManager.FAN_LEVEL fan_level3 = DexFanControlManager.FAN_LEVEL.STOP;
            DexFanControlManager.SIOP_LEVEL siop_level7 = DexFanControlManager.SIOP_LEVEL.MINUS_3;
            if (i == 40992) {
                hashMap.put(siop_level7, fan_level3);
                ((HashMap) this.mFanLevelTable).put(siop_level6, fan_level3);
                ((HashMap) this.mFanLevelTable).put(siop_level5, fan_level2);
                ((HashMap) this.mFanLevelTable).put(siop_level4, fan_level2);
                ((HashMap) this.mFanLevelTable).put(siop_level3, fan_level);
                ((HashMap) this.mFanLevelTable).put(siop_level2, fan_level);
                ((HashMap) this.mFanLevelTable).put(siop_level, fan_level);
            } else {
                hashMap.put(siop_level7, fan_level3);
                ((HashMap) this.mFanLevelTable).put(siop_level6, fan_level2);
                ((HashMap) this.mFanLevelTable).put(siop_level5, fan_level2);
                Map map = this.mFanLevelTable;
                DexFanControlManager.FAN_LEVEL fan_level4 = DexFanControlManager.FAN_LEVEL.MILD;
                ((HashMap) map).put(siop_level4, fan_level4);
                ((HashMap) this.mFanLevelTable).put(siop_level3, fan_level4);
                ((HashMap) this.mFanLevelTable).put(siop_level2, fan_level);
                ((HashMap) this.mFanLevelTable).put(siop_level, fan_level);
            }
        }
        return (DexFanControlManager.FAN_LEVEL)
                ((HashMap) this.mFanLevelTable).get(this.mIDexFanModeControl.mSiopLevel);
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.fanmode.IFanMode
    public final void onChangedDexMode() {}

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.fanmode.IFanMode
    public final synchronized void onChangedFanHoldingRequestCount(int i) {
        if (i > 0) {
            this.mIDexFanModeControl.setFanMode(DexFanControlManager.FanModeEnum.FanHoldingMode);
        }
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.fanmode.IFanMode
    public final void onChangedSystemRequestStatus(boolean z) {
        if (z) {
            this.mIDexFanModeControl.setFanMode(DexFanControlManager.FanModeEnum.SystemRequestMode);
        }
    }
}
