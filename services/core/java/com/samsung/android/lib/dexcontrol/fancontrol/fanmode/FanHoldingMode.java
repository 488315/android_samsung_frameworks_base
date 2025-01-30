package com.samsung.android.lib.dexcontrol.fancontrol.fanmode;

import com.samsung.android.lib.dexcontrol.fancontrol.DexFanControlManager;
import com.samsung.android.lib.dexcontrol.fancontrol.IDexFanModeControl;
import com.samsung.android.lib.dexcontrol.utils.SLog;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class FanHoldingMode implements IFanMode {
    public int mDexModel;
    public Map mFanLevelTable = null;
    public IDexFanModeControl mIDexFanModeControl;

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.fanmode.IFanMode
    public void onChangedDexMode() {
    }

    public final Map getFanLevelTable() {
        if (this.mFanLevelTable == null) {
            HashMap hashMap = new HashMap();
            this.mFanLevelTable = hashMap;
            if (this.mDexModel == 40992) {
                DexFanControlManager.SIOP_LEVEL siop_level = DexFanControlManager.SIOP_LEVEL.MINUS_3;
                DexFanControlManager.FAN_LEVEL fan_level = DexFanControlManager.FAN_LEVEL.STOP;
                hashMap.put(siop_level, fan_level);
                this.mFanLevelTable.put(DexFanControlManager.SIOP_LEVEL.MINUS_2, fan_level);
                this.mFanLevelTable.put(DexFanControlManager.SIOP_LEVEL.MINUS_1, fan_level);
                this.mFanLevelTable.put(DexFanControlManager.SIOP_LEVEL.ZERO, fan_level);
                this.mFanLevelTable.put(DexFanControlManager.SIOP_LEVEL.PLUS_1, fan_level);
                this.mFanLevelTable.put(DexFanControlManager.SIOP_LEVEL.PLUS_2, DexFanControlManager.FAN_LEVEL.WEAK);
                this.mFanLevelTable.put(DexFanControlManager.SIOP_LEVEL.PLUS_3, DexFanControlManager.FAN_LEVEL.STRONG);
            } else {
                DexFanControlManager.SIOP_LEVEL siop_level2 = DexFanControlManager.SIOP_LEVEL.MINUS_3;
                DexFanControlManager.FAN_LEVEL fan_level2 = DexFanControlManager.FAN_LEVEL.STOP;
                hashMap.put(siop_level2, fan_level2);
                this.mFanLevelTable.put(DexFanControlManager.SIOP_LEVEL.MINUS_2, fan_level2);
                this.mFanLevelTable.put(DexFanControlManager.SIOP_LEVEL.MINUS_1, fan_level2);
                this.mFanLevelTable.put(DexFanControlManager.SIOP_LEVEL.ZERO, fan_level2);
                this.mFanLevelTable.put(DexFanControlManager.SIOP_LEVEL.PLUS_1, DexFanControlManager.FAN_LEVEL.WEAK);
                this.mFanLevelTable.put(DexFanControlManager.SIOP_LEVEL.PLUS_2, DexFanControlManager.FAN_LEVEL.MILD);
                this.mFanLevelTable.put(DexFanControlManager.SIOP_LEVEL.PLUS_3, DexFanControlManager.FAN_LEVEL.STRONG);
            }
        }
        return this.mFanLevelTable;
    }

    public FanHoldingMode(IDexFanModeControl iDexFanModeControl, int i) {
        this.mIDexFanModeControl = iDexFanModeControl;
        this.mDexModel = i;
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.fanmode.IFanMode
    public DexFanControlManager.FAN_LEVEL getCurrentFanLevel() {
        SLog.m126d("DexTestApp : FanHolding", "curFanLevel" + this.mIDexFanModeControl.getSiopLevel());
        return (DexFanControlManager.FAN_LEVEL) getFanLevelTable().get(this.mIDexFanModeControl.getSiopLevel());
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.fanmode.IFanMode
    public void onChangedSystemRequestStatus(boolean z) {
        if (z) {
            this.mIDexFanModeControl.clearAllFanHoldingTimerTask();
            this.mIDexFanModeControl.setFanMode(DexFanControlManager.FanModeEnum.SystemRequestMode);
        }
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.fanmode.IFanMode
    public synchronized void onChangedFanHoldingRequestCount(int i) {
        if (i <= 0) {
            this.mIDexFanModeControl.setFanMode(DexFanControlManager.FanModeEnum.NoramlMode);
        }
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.fanmode.IFanMode
    public void destroy() {
        Map map = this.mFanLevelTable;
        if (map != null) {
            map.clear();
            this.mFanLevelTable = null;
        }
        this.mIDexFanModeControl = null;
        this.mDexModel = 0;
    }
}
