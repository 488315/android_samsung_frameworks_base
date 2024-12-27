package com.samsung.android.lib.dexcontrol.model.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.AudioRecordingConfiguration;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.samsung.android.lib.dexcontrol.fancontrol.DexFanControlManager;
import com.samsung.android.lib.dexcontrol.fancontrol.DexFanHoldingTimerTask;
import com.samsung.android.lib.dexcontrol.fancontrol.fanmode.IFanMode;
import com.samsung.android.lib.dexcontrol.utils.SLog;
import com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmSendExecutor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public abstract class FanControlModel extends BaseModel {
    public boolean isSiopLevelValid;
    public boolean isTestAppSIOPEnable;
    public AudioManager mAudioManager;
    public final AnonymousClass3 mAudioRecordingCallback;
    public final int mDexModel;
    public final AnonymousClass4 mDexMonitorIntentReceiver;
    public DexFanControlManager mIDexFanControl;
    public final AnonymousClass1 mIDexFanControlListener;
    public boolean mIsAudioRecodingMode;
    public boolean mIsCallMode;
    public boolean mIsScreenOffMode;
    public UvdmSendExecutor mMessageSender;
    public final AnonymousClass2 mPhoneStateListener;
    public int mSiopLevel;
    public final TelephonyManager mTelephonyManager;

    /* renamed from: com.samsung.android.lib.dexcontrol.model.common.FanControlModel$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }

        public final void onFanLevelChanged(DexFanControlManager.FAN_LEVEL fan_level) {
            StringBuilder sb = new StringBuilder("onFanLevelChanged  - fan level = ");
            sb.append(fan_level.toString());
            sb.append(" isSiopLevelValid = ");
            FanControlModel fanControlModel = FanControlModel.this;
            sb.append(fanControlModel.isSiopLevelValid);
            SLog.d("FanControlModel", sb.toString());
            if (fanControlModel.isSiopLevelValid) {
                fanControlModel.onFanControlResult(fan_level);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.media.AudioManager$AudioRecordingCallback, com.samsung.android.lib.dexcontrol.model.common.FanControlModel$3] */
    /* JADX WARN: Type inference failed for: r2v0, types: [com.samsung.android.lib.dexcontrol.model.common.FanControlModel$4] */
    /* JADX WARN: Type inference failed for: r8v3, types: [android.telephony.PhoneStateListener, com.samsung.android.lib.dexcontrol.model.common.FanControlModel$2] */
    public FanControlModel(Context context, int i, boolean z) {
        super(context);
        this.mIsCallMode = false;
        this.mIsScreenOffMode = false;
        this.mIsAudioRecodingMode = false;
        this.mIDexFanControl = null;
        this.mAudioManager = null;
        this.mSiopLevel = 0;
        this.mDexModel = 0;
        this.mMessageSender = null;
        this.isTestAppSIOPEnable = false;
        this.mIDexFanControlListener = new AnonymousClass1();
        ?? r8 = new PhoneStateListener() { // from class: com.samsung.android.lib.dexcontrol.model.common.FanControlModel.2
            @Override // android.telephony.PhoneStateListener
            public final void onCallStateChanged(int i2, String str) {
                SLog.i("FanControlModel", "onCallStateChanged() : " + i2);
                if (i2 == 1) {
                    FanControlModel.this.mIsCallMode = false;
                } else if (i2 != 2) {
                    FanControlModel.this.mIsCallMode = false;
                } else {
                    FanControlModel.this.mIsCallMode = true;
                }
                FanControlModel fanControlModel = FanControlModel.this;
                DexFanControlManager dexFanControlManager = fanControlModel.mIDexFanControl;
                if (dexFanControlManager != null) {
                    dexFanControlManager.setSystemRequest(fanControlModel.isExistSystemRequest());
                } else {
                    SLog.e("FanControlModel", "IDexFanControl is ull");
                }
            }
        };
        this.mPhoneStateListener = r8;
        ?? r1 = new AudioManager.AudioRecordingCallback() { // from class: com.samsung.android.lib.dexcontrol.model.common.FanControlModel.3
            @Override // android.media.AudioManager.AudioRecordingCallback
            public final void onRecordingConfigChanged(List list) {
                boolean z2;
                SLog.i("FanControlModel", "onRecordingConfigChanged");
                if (list != null) {
                    int size = list.size();
                    boolean z3 = false;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= size) {
                            z2 = false;
                            break;
                        }
                        int clientAudioSource = ((AudioRecordingConfiguration) list.get(i2)).getClientAudioSource();
                        SLog.i("FanControlModel", "source : " + clientAudioSource);
                        if (clientAudioSource == 7) {
                            z2 = true;
                            break;
                        }
                        i2++;
                    }
                    FanControlModel fanControlModel = FanControlModel.this;
                    if (size > 0 && !z2) {
                        z3 = true;
                    }
                    fanControlModel.mIsAudioRecodingMode = z3;
                    DexFanControlManager dexFanControlManager = fanControlModel.mIDexFanControl;
                    if (dexFanControlManager != null) {
                        dexFanControlManager.setSystemRequest(fanControlModel.isExistSystemRequest());
                    }
                }
            }
        };
        this.mAudioRecordingCallback = r1;
        this.mDexMonitorIntentReceiver = new BroadcastReceiver() { // from class: com.samsung.android.lib.dexcontrol.model.common.FanControlModel.4
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("com.samsung.intent.action.CHECK_SIOP_LEVEL".equals(intent.getAction())) {
                    FanControlModel.this.mSiopLevel = intent.getIntExtra("siop_level_broadcast", -1);
                    SLog.i("FanControlModel", "BroadcastReceiver - SIOP LEVEL : " + FanControlModel.this.mSiopLevel);
                    FanControlModel fanControlModel = FanControlModel.this;
                    fanControlModel.isSiopLevelValid = true;
                    DexFanControlManager dexFanControlManager = fanControlModel.mIDexFanControl;
                    if (dexFanControlManager == null) {
                        SLog.e("FanControlModel", "onReceive - mIDexFanControl is null");
                        return;
                    } else {
                        if (fanControlModel.isTestAppSIOPEnable) {
                            return;
                        }
                        dexFanControlManager.mSiopLevel = DexFanControlManager.SIOP_LEVEL.fromValue(fanControlModel.mSiopLevel);
                        dexFanControlManager.controlFanLevel();
                        return;
                    }
                }
                if (FanControlModel.this.mIDexFanControl == null) {
                    SLog.e("FanControlModel", "onReceive - mIDexFanControl is null");
                    return;
                }
                if ("com.samsung.intent.action.CHECK_SIOP_LEVEL_TEMP".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("siop_level_broadcast", -1);
                    FanControlModel fanControlModel2 = FanControlModel.this;
                    fanControlModel2.isSiopLevelValid = true;
                    fanControlModel2.isTestAppSIOPEnable = true;
                    StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(intExtra, "SIOP LEVEL FROM TEST APP : ", " ");
                    m.append(FanControlModel.this.isTestAppSIOPEnable);
                    m.append(" ");
                    m.append(FanControlModel.this.isSiopLevelValid);
                    SLog.d("FanControlModel", m.toString());
                    DexFanControlManager dexFanControlManager2 = FanControlModel.this.mIDexFanControl;
                    dexFanControlManager2.getClass();
                    dexFanControlManager2.mSiopLevel = DexFanControlManager.SIOP_LEVEL.fromValue(intExtra);
                    dexFanControlManager2.controlFanLevel();
                    return;
                }
                if ("com.samsung.accessory.action.DEX_FAN_ON_TEMP".equals(intent.getAction())) {
                    DexFanControlManager dexFanControlManager3 = FanControlModel.this.mIDexFanControl;
                    DexFanControlManager.FAN_LEVEL fan_level = DexFanControlManager.FAN_LEVEL.STRONG;
                    AnonymousClass1 anonymousClass1 = dexFanControlManager3.mIDexFanControlListener;
                    if (anonymousClass1 != null) {
                        anonymousClass1.onFanLevelChanged(fan_level);
                        return;
                    }
                    return;
                }
                if ("com.samsung.accessory.action.DEX_FAN_OFF_TEMP".equals(intent.getAction())) {
                    DexFanControlManager dexFanControlManager4 = FanControlModel.this.mIDexFanControl;
                    DexFanControlManager.FAN_LEVEL fan_level2 = DexFanControlManager.FAN_LEVEL.STOP;
                    AnonymousClass1 anonymousClass12 = dexFanControlManager4.mIDexFanControlListener;
                    if (anonymousClass12 != null) {
                        anonymousClass12.onFanLevelChanged(fan_level2);
                        return;
                    }
                    return;
                }
                if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                    FanControlModel fanControlModel3 = FanControlModel.this;
                    fanControlModel3.mIsScreenOffMode = true;
                    fanControlModel3.mIDexFanControl.setSystemRequest(fanControlModel3.isExistSystemRequest());
                    return;
                }
                if ("android.intent.action.USER_PRESENT".equals(intent.getAction())) {
                    FanControlModel fanControlModel4 = FanControlModel.this;
                    fanControlModel4.mIsScreenOffMode = false;
                    fanControlModel4.mIDexFanControl.setSystemRequest(fanControlModel4.isExistSystemRequest());
                    return;
                }
                if (!"com.samsung.accessory.action.DEX_FAN_HOLDING".equals(intent.getAction())) {
                    if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                        FanControlModel fanControlModel5 = FanControlModel.this;
                        AudioManager audioManager = (AudioManager) fanControlModel5.mContext.getSystemService("audio");
                        fanControlModel5.mAudioManager = audioManager;
                        if (audioManager != null) {
                            audioManager.registerAudioRecordingCallback(fanControlModel5.mAudioRecordingCallback, null);
                            return;
                        } else {
                            SLog.e("FanControlModel", "registerAudioRecodingListener - mAudioManager is null");
                            return;
                        }
                    }
                    return;
                }
                int intExtra2 = intent.getIntExtra("dex_fan_holding_duration", 0);
                String stringExtra = intent.getStringExtra("dex_fan_holding_sender");
                if (stringExtra == null && (stringExtra = intent.getPackage()) == null) {
                    stringExtra = "Anonymous";
                }
                DexFanControlManager dexFanControlManager5 = FanControlModel.this.mIDexFanControl;
                dexFanControlManager5.getClass();
                if (intExtra2 <= 0) {
                    if (dexFanControlManager5.mFanHoldingTimerTaskList != null) {
                        SLog.d("DexFanControlManager", "cancelDexFanHoldingTimerTask enter ");
                        Iterator it = ((ArrayList) dexFanControlManager5.mFanHoldingTimerTaskList).iterator();
                        while (it.hasNext()) {
                            DexFanHoldingTimerTask dexFanHoldingTimerTask = (DexFanHoldingTimerTask) it.next();
                            if (dexFanHoldingTimerTask.mSender.equalsIgnoreCase(stringExtra)) {
                                SLog.i("DexFanControlManager", "cancelDexFanHoldingTimerTask ".concat(stringExtra));
                                dexFanHoldingTimerTask.cancel();
                                return;
                            }
                        }
                        return;
                    }
                    return;
                }
                if (intExtra2 > 600000) {
                    SLog.e("DexFanControlManager", "Requested Fan Hoding Time is over the max - " + intExtra2);
                    intExtra2 = 600000;
                }
                IFanMode currentFanMode = dexFanControlManager5.getCurrentFanMode();
                Object obj = dexFanControlManager5.getFanModeMap().get(DexFanControlManager.FanModeEnum.NoramlMode);
                DexFanControlManager.FanModeEnum fanModeEnum = DexFanControlManager.FanModeEnum.FanHoldingMode;
                if (currentFanMode == obj) {
                    dexFanControlManager5.addFanHoldingTimerTask(intExtra2, stringExtra);
                    dexFanControlManager5.setFanMode(fanModeEnum);
                } else if (dexFanControlManager5.getCurrentFanMode() == dexFanControlManager5.getFanModeMap().get(fanModeEnum)) {
                    dexFanControlManager5.addFanHoldingTimerTask(intExtra2, stringExtra);
                }
            }
        };
        IntentFilter m = VcnManagementService$$ExternalSyntheticOutline0.m("com.samsung.intent.action.CHECK_SIOP_LEVEL", "android.intent.action.SCREEN_OFF", "android.intent.action.USER_PRESENT", "com.samsung.accessory.action.DEX_FAN_HOLDING", "android.intent.action.BOOT_COMPLETED");
        m.addAction("com.samsung.accessory.action.DEX_FAN_ON_TEMP");
        m.addAction("com.samsung.accessory.action.DEX_FAN_OFF_TEMP");
        m.addAction("com.samsung.intent.action.CHECK_SIOP_LEVEL_TEMP");
        SLog.i("FanControlModel", "Register with Context.RECEIVER_EXPORTED");
        this.mContext.registerReceiver(this.mDexMonitorIntentReceiver, m, 2);
        this.mDexModel = i;
        AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
        this.mAudioManager = audioManager;
        if (audioManager != 0) {
            audioManager.registerAudioRecordingCallback(r1, null);
        } else {
            SLog.e("FanControlModel", "registerAudioRecodingListener - mAudioManager is null");
        }
        if (z) {
            TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
            this.mTelephonyManager = telephonyManager;
            if (telephonyManager != 0) {
                telephonyManager.listen(r8, 32);
            }
        }
    }

    public final void creatFanControl() {
        this.isSiopLevelValid = false;
        int i = this.mSiopLevel;
        boolean isExistSystemRequest = isExistSystemRequest();
        DexFanControlManager dexFanControlManager = new DexFanControlManager();
        dexFanControlManager.mSiopLevel = null;
        dexFanControlManager.mCurrentFanLevel = null;
        dexFanControlManager.mIsExistSystemRequest = false;
        dexFanControlManager.mTimer = null;
        dexFanControlManager.mFanModeMap = null;
        dexFanControlManager.mCurrentFanMode = null;
        dexFanControlManager.mFanHoldingTimerTaskList = null;
        dexFanControlManager.mDexModel = this.mDexModel;
        dexFanControlManager.mIDexFanControlListener = this.mIDexFanControlListener;
        dexFanControlManager.mSiopLevel = DexFanControlManager.SIOP_LEVEL.fromValue(i);
        dexFanControlManager.getCurrentFanMode().onChangedDexMode();
        dexFanControlManager.controlFanLevel();
        dexFanControlManager.setSystemRequest(isExistSystemRequest);
        this.mIDexFanControl = dexFanControlManager;
    }

    public void destroy() {
        unregisterReceiver();
        this.mContext = null;
        UvdmSendExecutor uvdmSendExecutor = this.mMessageSender;
        if (uvdmSendExecutor != null) {
            uvdmSendExecutor.mListener = null;
            uvdmSendExecutor.destroy();
        }
        DexFanControlManager dexFanControlManager = this.mIDexFanControl;
        if (dexFanControlManager != null) {
            SLog.d("DexFanControlManager", "destroy");
            if (dexFanControlManager.getFanHoldingRequestCount() > 0) {
                if (dexFanControlManager.mTimer == null) {
                    dexFanControlManager.mTimer = new Timer(true);
                }
                dexFanControlManager.mTimer.cancel();
                Timer timer = dexFanControlManager.mTimer;
                if (timer != null) {
                    timer.cancel();
                }
                dexFanControlManager.mTimer = null;
                ((ArrayList) dexFanControlManager.mFanHoldingTimerTaskList).clear();
            }
            dexFanControlManager.mFanHoldingTimerTaskList = null;
            dexFanControlManager.mSiopLevel = null;
            dexFanControlManager.mCurrentFanLevel = null;
            dexFanControlManager.mIDexFanControlListener = null;
            dexFanControlManager.mCurrentFanMode = null;
            Map map = dexFanControlManager.mFanModeMap;
            if (map != null) {
                ((IFanMode) ((HashMap) map).get(DexFanControlManager.FanModeEnum.NonDexMode)).destroy();
                ((IFanMode) ((HashMap) dexFanControlManager.mFanModeMap).get(DexFanControlManager.FanModeEnum.NoramlMode)).destroy();
                ((IFanMode) ((HashMap) dexFanControlManager.mFanModeMap).get(DexFanControlManager.FanModeEnum.FanHoldingMode)).destroy();
                ((IFanMode) ((HashMap) dexFanControlManager.mFanModeMap).get(DexFanControlManager.FanModeEnum.SystemRequestMode)).destroy();
                ((HashMap) dexFanControlManager.mFanModeMap).clear();
                dexFanControlManager.mFanModeMap = null;
            }
        }
        AudioManager audioManager = this.mAudioManager;
        if (audioManager != null) {
            audioManager.unregisterAudioRecordingCallback(this.mAudioRecordingCallback);
            this.mAudioManager = null;
        }
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager != null) {
            telephonyManager.listen(this.mPhoneStateListener, 0);
        }
    }

    public final boolean isExistSystemRequest() {
        return this.mIsCallMode || this.mIsScreenOffMode || this.mIsAudioRecodingMode;
    }

    public abstract void onFanControlResult(DexFanControlManager.FAN_LEVEL fan_level);

    public abstract void unregisterReceiver();
}
