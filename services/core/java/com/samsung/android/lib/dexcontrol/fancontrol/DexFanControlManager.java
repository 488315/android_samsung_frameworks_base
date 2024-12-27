package com.samsung.android.lib.dexcontrol.fancontrol;

import com.samsung.android.lib.dexcontrol.fancontrol.fanmode.FanHoldingMode;
import com.samsung.android.lib.dexcontrol.fancontrol.fanmode.IFanMode;
import com.samsung.android.lib.dexcontrol.fancontrol.fanmode.NonDexMode;
import com.samsung.android.lib.dexcontrol.fancontrol.fanmode.NormalMode;
import com.samsung.android.lib.dexcontrol.fancontrol.fanmode.SystemRequestMode;
import com.samsung.android.lib.dexcontrol.model.common.FanControlModel;
import com.samsung.android.lib.dexcontrol.utils.SLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DexFanControlManager {
    public FAN_LEVEL mCurrentFanLevel;
    public IFanMode mCurrentFanMode;
    public int mDexModel;
    public List mFanHoldingTimerTaskList;
    public Map mFanModeMap;
    public FanControlModel.AnonymousClass1 mIDexFanControlListener;
    public boolean mIsExistSystemRequest;
    public SIOP_LEVEL mSiopLevel;
    public Timer mTimer;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum FAN_LEVEL {
        STOP("STOP"),
        WEAK("WEAK"),
        MILD("MILD"),
        STRONG("STRONG");

        private final int mFanLevel;

        FAN_LEVEL(String str) {
            this.mFanLevel = r2;
        }

        public final int getFanLevel() {
            return this.mFanLevel;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FanModeEnum {
        public static final /* synthetic */ FanModeEnum[] $VALUES;
        public static final FanModeEnum FanHoldingMode;
        public static final FanModeEnum NonDexMode;
        public static final FanModeEnum NoramlMode;
        public static final FanModeEnum SystemRequestMode;

        static {
            FanModeEnum fanModeEnum = new FanModeEnum("NonDexMode", 0);
            NonDexMode = fanModeEnum;
            FanModeEnum fanModeEnum2 = new FanModeEnum("NoramlMode", 1);
            NoramlMode = fanModeEnum2;
            FanModeEnum fanModeEnum3 = new FanModeEnum("FanHoldingMode", 2);
            FanHoldingMode = fanModeEnum3;
            FanModeEnum fanModeEnum4 = new FanModeEnum("SystemRequestMode", 3);
            SystemRequestMode = fanModeEnum4;
            $VALUES = new FanModeEnum[] {fanModeEnum, fanModeEnum2, fanModeEnum3, fanModeEnum4};
        }

        public static FanModeEnum valueOf(String str) {
            return (FanModeEnum) Enum.valueOf(FanModeEnum.class, str);
        }

        public static FanModeEnum[] values() {
            return (FanModeEnum[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum SIOP_LEVEL {
        MINUS_3("MINUS_3"),
        MINUS_2("MINUS_2"),
        MINUS_1("MINUS_1"),
        ZERO("ZERO"),
        PLUS_1("PLUS_1"),
        PLUS_2("PLUS_2"),
        PLUS_3("PLUS_3");

        private final int mValue;

        SIOP_LEVEL(String str) {
            this.mValue = r2;
        }

        public static SIOP_LEVEL fromValue(int i) {
            SIOP_LEVEL siop_level = MINUS_3;
            if (i < siop_level.mValue) {
                return siop_level;
            }
            SIOP_LEVEL siop_level2 = PLUS_3;
            if (i > siop_level2.mValue) {
                return siop_level2;
            }
            for (SIOP_LEVEL siop_level3 : values()) {
                if (siop_level3.mValue == i) {
                    return siop_level3;
                }
            }
            return null;
        }
    }

    public final void addFanHoldingTimerTask(int i, String str) {
        SLog.i("DexFanControlManager", "addFanHoldingTimerTask " + str + "   " + i);
        List list = this.mFanHoldingTimerTaskList;
        if (list != null) {
            Iterator it = ((ArrayList) list).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                DexFanHoldingTimerTask dexFanHoldingTimerTask = (DexFanHoldingTimerTask) it.next();
                if (dexFanHoldingTimerTask.mSender.equalsIgnoreCase(str)) {
                    SLog.i("DexFanControlManager", "checkAndUpdateFanHoldingTask ".concat(str));
                    SLog.i("DexFanHoldingTimerTask", "setUpdate - true");
                    dexFanHoldingTimerTask.mSetUpdate = true;
                    dexFanHoldingTimerTask.cancel();
                    getFanHoldingTimerTaskList().remove(dexFanHoldingTimerTask);
                    SLog.d(
                            "DexFanControlManager",
                            "checkAndUpdateFanHoldingTask after remove : "
                                    + getFanHoldingRequestCount());
                    break;
                }
            }
        }
        DexFanHoldingTimerTask dexFanHoldingTimerTask2 = new DexFanHoldingTimerTask(this, str);
        getFanHoldingTimerTaskList().add(dexFanHoldingTimerTask2);
        getCurrentFanMode().onChangedFanHoldingRequestCount(getFanHoldingRequestCount());
        if (this.mTimer == null) {
            this.mTimer = new Timer(true);
        }
        this.mTimer.schedule(dexFanHoldingTimerTask2, i);
        controlFanLevel();
        SLog.d(
                "DexFanControlManager",
                "addFanHoldingTimerTask currentFanHoldingCount : "
                        + getFanHoldingTimerTaskList().size());
    }

    public final void controlFanLevel() {
        FAN_LEVEL currentFanLevel = getCurrentFanMode().getCurrentFanLevel();
        SLog.i(
                "DexFanControlManager",
                "controlFanLevel : " + getCurrentFanMode() + " , " + currentFanLevel);
        this.mCurrentFanLevel = currentFanLevel;
        FanControlModel.AnonymousClass1 anonymousClass1 = this.mIDexFanControlListener;
        if (anonymousClass1 != null) {
            anonymousClass1.onFanLevelChanged(currentFanLevel);
        }
    }

    public final IFanMode getCurrentFanMode() {
        if (this.mCurrentFanMode == null) {
            this.mCurrentFanMode = (IFanMode) getFanModeMap().get(FanModeEnum.NoramlMode);
        }
        return this.mCurrentFanMode;
    }

    public final int getFanHoldingRequestCount() {
        return getFanHoldingTimerTaskList().size();
    }

    public final List getFanHoldingTimerTaskList() {
        if (this.mFanHoldingTimerTaskList == null) {
            this.mFanHoldingTimerTaskList = new ArrayList();
        }
        return this.mFanHoldingTimerTaskList;
    }

    public final Map getFanModeMap() {
        if (this.mFanModeMap == null) {
            HashMap hashMap = new HashMap();
            this.mFanModeMap = hashMap;
            FanModeEnum fanModeEnum = FanModeEnum.NonDexMode;
            NonDexMode nonDexMode = new NonDexMode();
            nonDexMode.mFanLevelTable = null;
            nonDexMode.mIDexFanModeControl = this;
            hashMap.put(fanModeEnum, nonDexMode);
            Map map = this.mFanModeMap;
            FanModeEnum fanModeEnum2 = FanModeEnum.NoramlMode;
            NormalMode normalMode = new NormalMode();
            normalMode.mFanLevelTable = null;
            normalMode.mIDexFanModeControl = this;
            int i = this.mDexModel;
            normalMode.mDexModel = i;
            map.put(fanModeEnum2, normalMode);
            Map map2 = this.mFanModeMap;
            FanModeEnum fanModeEnum3 = FanModeEnum.FanHoldingMode;
            FanHoldingMode fanHoldingMode = new FanHoldingMode();
            fanHoldingMode.mFanLevelTable = null;
            fanHoldingMode.mIDexFanModeControl = this;
            fanHoldingMode.mDexModel = i;
            map2.put(fanModeEnum3, fanHoldingMode);
            Map map3 = this.mFanModeMap;
            FanModeEnum fanModeEnum4 = FanModeEnum.SystemRequestMode;
            SystemRequestMode systemRequestMode = new SystemRequestMode();
            systemRequestMode.mFanLevelTable = null;
            systemRequestMode.mIDexFanModeControl = this;
            systemRequestMode.mDexModel = i;
            map3.put(fanModeEnum4, systemRequestMode);
        }
        return this.mFanModeMap;
    }

    public final void setFanMode(FanModeEnum fanModeEnum) {
        SLog.d("DexFanControlManager", "setFanMode " + fanModeEnum);
        this.mCurrentFanMode = (IFanMode) getFanModeMap().get(fanModeEnum);
    }

    public final void setSystemRequest(boolean z) {
        SLog.i("DexFanControlManager", "setSystemRequest : " + z);
        if (this.mIsExistSystemRequest != z) {
            this.mIsExistSystemRequest = z;
            getCurrentFanMode().onChangedSystemRequestStatus(this.mIsExistSystemRequest);
            controlFanLevel();
        }
    }
}
