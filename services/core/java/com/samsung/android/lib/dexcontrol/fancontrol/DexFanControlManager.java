package com.samsung.android.lib.dexcontrol.fancontrol;

import com.samsung.android.lib.dexcontrol.fancontrol.fanmode.FanHoldingMode;
import com.samsung.android.lib.dexcontrol.fancontrol.fanmode.IFanMode;
import com.samsung.android.lib.dexcontrol.fancontrol.fanmode.NonDexMode;
import com.samsung.android.lib.dexcontrol.fancontrol.fanmode.NormalMode;
import com.samsung.android.lib.dexcontrol.fancontrol.fanmode.SystemRequestMode;
import com.samsung.android.lib.dexcontrol.utils.SLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

/* loaded from: classes2.dex */
public class DexFanControlManager implements IDexFanControl, IDexFanModeControl {
    public static final String TAG = "DexFanControlManager";
    public int mDexModel;
    public SIOP_LEVEL mSiopLevel;
    public FAN_LEVEL mCurrentFanLevel = null;
    public boolean mIsExistSystemRequest = false;
    public IDexFanControlListener mIDexFanControlListener = null;
    public Timer mTimer = null;
    public Map mFanModeMap = null;
    public IFanMode mCurrentFanMode = null;
    public List mFanHoldingTimerTaskList = null;

    public enum FanModeEnum {
        NonDexMode,
        NoramlMode,
        FanHoldingMode,
        SystemRequestMode
    }

    public enum FAN_LEVEL {
        STOP(0),
        WEAK(1),
        MILD(2),
        STRONG(3);

        private final int mFanLevel;

        FAN_LEVEL(int i) {
            this.mFanLevel = i;
        }

        public int getFanLevel() {
            return this.mFanLevel;
        }
    }

    public enum SIOP_LEVEL {
        MINUS_3(-3),
        MINUS_2(-2),
        MINUS_1(-1),
        ZERO(0),
        PLUS_1(1),
        PLUS_2(2),
        PLUS_3(3);

        private final int mValue;

        SIOP_LEVEL(int i) {
            this.mValue = i;
        }

        public int getValue() {
            return this.mValue;
        }

        public static SIOP_LEVEL fromValue(int i) {
            SIOP_LEVEL siop_level = MINUS_3;
            if (i < siop_level.getValue()) {
                return siop_level;
            }
            SIOP_LEVEL siop_level2 = PLUS_3;
            if (i > siop_level2.getValue()) {
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

    public DexFanControlManager(int i, boolean z, IDexFanControlListener iDexFanControlListener, int i2) {
        this.mSiopLevel = null;
        this.mDexModel = i2;
        setDexFanControlListener(iDexFanControlListener);
        this.mSiopLevel = SIOP_LEVEL.fromValue(i);
        getCurrentFanMode().onChangedDexMode();
        controlFanLevel();
        setSystemRequest(z);
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.IDexFanModeControl
    public SIOP_LEVEL getSiopLevel() {
        return this.mSiopLevel;
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.IDexFanControl
    public void setSiopLevel(int i) {
        this.mSiopLevel = SIOP_LEVEL.fromValue(i);
        controlFanLevel();
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.IDexFanControl
    public void setSystemRequest(boolean z) {
        SLog.m128i(TAG, "setSystemRequest : " + z);
        if (this.mIsExistSystemRequest != z) {
            this.mIsExistSystemRequest = z;
            getCurrentFanMode().onChangedSystemRequestStatus(this.mIsExistSystemRequest);
            controlFanLevel();
        }
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.IDexFanControl
    public void onFanHoldingEvent(String str, int i) {
        if (str != null) {
            if (i <= 0) {
                cancelDexFanHoldingTimerTask(str);
                return;
            }
            if (i > 600000) {
                SLog.m127e(TAG, "Requested Fan Hoding Time is over the max - " + i);
                i = 600000;
            }
            if (getCurrentFanMode() == getFanModeMap().get(FanModeEnum.NoramlMode)) {
                addFanHoldingTimerTask(str, i);
                setFanMode(FanModeEnum.FanHoldingMode);
            } else if (getCurrentFanMode() == getFanModeMap().get(FanModeEnum.FanHoldingMode)) {
                addFanHoldingTimerTask(str, i);
            }
        }
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.IDexFanModeControl
    public boolean isExistSystemRequest() {
        return this.mIsExistSystemRequest;
    }

    public final Timer getFanHoldingTimer() {
        if (this.mTimer == null) {
            this.mTimer = new Timer(true);
        }
        return this.mTimer;
    }

    public final void controlFanLevel() {
        FAN_LEVEL currentFanLevel = getCurrentFanMode().getCurrentFanLevel();
        SLog.m128i(TAG, "controlFanLevel : " + getCurrentFanMode() + " , " + currentFanLevel);
        this.mCurrentFanLevel = currentFanLevel;
        IDexFanControlListener iDexFanControlListener = this.mIDexFanControlListener;
        if (iDexFanControlListener != null) {
            iDexFanControlListener.onFanLevelChanged(currentFanLevel);
        }
    }

    public final void setDexFanControlListener(IDexFanControlListener iDexFanControlListener) {
        this.mIDexFanControlListener = iDexFanControlListener;
    }

    public final Map getFanModeMap() {
        if (this.mFanModeMap == null) {
            HashMap hashMap = new HashMap();
            this.mFanModeMap = hashMap;
            hashMap.put(FanModeEnum.NonDexMode, new NonDexMode(this));
            this.mFanModeMap.put(FanModeEnum.NoramlMode, new NormalMode(this, this.mDexModel));
            this.mFanModeMap.put(FanModeEnum.FanHoldingMode, new FanHoldingMode(this, this.mDexModel));
            this.mFanModeMap.put(FanModeEnum.SystemRequestMode, new SystemRequestMode(this, this.mDexModel));
        }
        return this.mFanModeMap;
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.IDexFanModeControl
    public void setFanMode(FanModeEnum fanModeEnum) {
        SLog.m126d(TAG, "setFanMode " + fanModeEnum);
        this.mCurrentFanMode = (IFanMode) getFanModeMap().get(fanModeEnum);
    }

    public final int getFanHoldingRequestCount() {
        return getFanHoldingTimerTaskList().size();
    }

    public void addFanHoldingTimerTask(String str, int i) {
        String str2 = TAG;
        SLog.m128i(str2, "addFanHoldingTimerTask " + str + "   " + i);
        checkAndUpdateFanHoldingTask(str);
        DexFanHoldingTimerTask dexFanHoldingTimerTask = new DexFanHoldingTimerTask(this, str);
        getFanHoldingTimerTaskList().add(dexFanHoldingTimerTask);
        getCurrentFanMode().onChangedFanHoldingRequestCount(getFanHoldingRequestCount());
        getFanHoldingTimer().schedule(dexFanHoldingTimerTask, (long) i);
        controlFanLevel();
        SLog.m126d(str2, "addFanHoldingTimerTask currentFanHoldingCount : " + getFanHoldingTimerTaskList().size());
    }

    public final void checkAndUpdateFanHoldingTask(String str) {
        List<DexFanHoldingTimerTask> list = this.mFanHoldingTimerTaskList;
        if (list != null) {
            for (DexFanHoldingTimerTask dexFanHoldingTimerTask : list) {
                if (dexFanHoldingTimerTask.getSender().equalsIgnoreCase(str)) {
                    String str2 = TAG;
                    SLog.m128i(str2, "checkAndUpdateFanHoldingTask " + str);
                    dexFanHoldingTimerTask.setUpdate(true);
                    dexFanHoldingTimerTask.cancel();
                    getFanHoldingTimerTaskList().remove(dexFanHoldingTimerTask);
                    SLog.m126d(str2, "checkAndUpdateFanHoldingTask after remove : " + getFanHoldingRequestCount());
                    return;
                }
            }
        }
    }

    public final void cancelFanHoldingTimer() {
        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
        }
        this.mTimer = null;
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.IDexFanModeControl
    public void clearAllFanHoldingTimerTask() {
        if (getFanHoldingRequestCount() > 0) {
            getFanHoldingTimer().cancel();
            cancelFanHoldingTimer();
            this.mFanHoldingTimerTaskList.clear();
        }
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.IDexFanControl
    public void setFanLevelForTest(FAN_LEVEL fan_level) {
        IDexFanControlListener iDexFanControlListener = this.mIDexFanControlListener;
        if (iDexFanControlListener != null) {
            iDexFanControlListener.onFanLevelChanged(fan_level);
        }
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.IDexFanControl
    public void onDexFanLevelUpdated(int i) {
        String str = TAG;
        SLog.m126d(str, "onDexFanLevelUpdated : " + i);
        FAN_LEVEL fan_level = this.mCurrentFanLevel;
        if (fan_level == null || fan_level.getFanLevel() == i) {
            return;
        }
        SLog.m127e(str, "Received Fan Level(" + i + ") is not matched with " + this.mCurrentFanLevel.getFanLevel() + " level.");
    }

    public final IFanMode getCurrentFanMode() {
        if (this.mCurrentFanMode == null) {
            this.mCurrentFanMode = (IFanMode) getFanModeMap().get(FanModeEnum.NoramlMode);
        }
        return this.mCurrentFanMode;
    }

    public final List getFanHoldingTimerTaskList() {
        if (this.mFanHoldingTimerTaskList == null) {
            this.mFanHoldingTimerTaskList = new ArrayList();
        }
        return this.mFanHoldingTimerTaskList;
    }

    public void cancelDexFanHoldingTimerTask(String str) {
        if (this.mFanHoldingTimerTaskList != null) {
            SLog.m126d(TAG, "cancelDexFanHoldingTimerTask enter ");
            for (DexFanHoldingTimerTask dexFanHoldingTimerTask : this.mFanHoldingTimerTaskList) {
                if (dexFanHoldingTimerTask.getSender().equalsIgnoreCase(str)) {
                    SLog.m128i(TAG, "cancelDexFanHoldingTimerTask " + str);
                    dexFanHoldingTimerTask.cancel();
                    return;
                }
            }
        }
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.IDexFanModeControl
    public void onFinishedFanHoldingTimerTask(DexFanHoldingTimerTask dexFanHoldingTimerTask) {
        String str = TAG;
        SLog.m126d(str, "before remove:" + getFanHoldingRequestCount());
        getFanHoldingTimerTaskList().remove(dexFanHoldingTimerTask);
        SLog.m126d(str, "after remove:" + getFanHoldingRequestCount());
        getCurrentFanMode().onChangedFanHoldingRequestCount(getFanHoldingRequestCount());
        controlFanLevel();
    }

    @Override // com.samsung.android.lib.dexcontrol.fancontrol.IDexFanControl
    public void destroy() {
        SLog.m126d(TAG, "destroy");
        clearAllFanHoldingTimerTask();
        this.mFanHoldingTimerTaskList = null;
        this.mSiopLevel = null;
        this.mCurrentFanLevel = null;
        this.mIDexFanControlListener = null;
        this.mCurrentFanMode = null;
        Map map = this.mFanModeMap;
        if (map != null) {
            ((IFanMode) map.get(FanModeEnum.NonDexMode)).destroy();
            ((IFanMode) this.mFanModeMap.get(FanModeEnum.NoramlMode)).destroy();
            ((IFanMode) this.mFanModeMap.get(FanModeEnum.FanHoldingMode)).destroy();
            ((IFanMode) this.mFanModeMap.get(FanModeEnum.SystemRequestMode)).destroy();
            this.mFanModeMap.clear();
            this.mFanModeMap = null;
        }
    }
}
