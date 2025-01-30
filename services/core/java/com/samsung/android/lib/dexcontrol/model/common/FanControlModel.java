package com.samsung.android.lib.dexcontrol.model.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.AudioRecordingConfiguration;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.samsung.android.lib.dexcontrol.fancontrol.DexFanControlManager;
import com.samsung.android.lib.dexcontrol.fancontrol.IDexFanControl;
import com.samsung.android.lib.dexcontrol.fancontrol.IDexFanControlListener;
import com.samsung.android.lib.dexcontrol.utils.SLog;
import com.samsung.android.lib.dexcontrol.uvdm.sender.IUvdmSender;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class FanControlModel extends BaseModel {
    public static final String TAG = "FanControlModel";
    public boolean isSiopLevelValid;
    public boolean isTestAppSIOPEnable;
    public AudioManager mAudioManager;
    public final AudioManager.AudioRecordingCallback mAudioRecordingCallback;
    public boolean mAudioSystemRequestEnable;
    public int mDexModel;
    public BroadcastReceiver mDexMonitorIntentReceiver;
    public IDexFanControl mIDexFanControl;
    public final IDexFanControlListener mIDexFanControlListener;
    public boolean mIsAudioRecodingMode;
    public boolean mIsCallMode;
    public boolean mIsScreenOffMode;
    public IUvdmSender mMessageSender;
    public final PhoneStateListener mPhoneStateListener;
    public int mSiopLevel;
    public final boolean mSupportTestApp;
    public TelephonyManager mTelephonyManager;

    public abstract void onFanControlResult(DexFanControlManager.FAN_LEVEL fan_level);

    public FanControlModel(Context context, boolean z, int i) {
        super(context);
        this.mIsCallMode = false;
        this.mIsScreenOffMode = false;
        this.mIsAudioRecodingMode = false;
        this.mIDexFanControl = null;
        this.mAudioManager = null;
        this.mSiopLevel = 0;
        this.mAudioSystemRequestEnable = false;
        this.mDexModel = 0;
        this.mSupportTestApp = true;
        this.mMessageSender = null;
        this.isTestAppSIOPEnable = false;
        this.mIDexFanControlListener = new IDexFanControlListener() { // from class: com.samsung.android.lib.dexcontrol.model.common.FanControlModel.1
            @Override // com.samsung.android.lib.dexcontrol.fancontrol.IDexFanControlListener
            public void onFanLevelChanged(DexFanControlManager.FAN_LEVEL fan_level) {
                SLog.m126d(FanControlModel.TAG, "onFanLevelChanged  - fan level = " + fan_level.toString() + " isSiopLevelValid = " + FanControlModel.this.isSiopLevelValid);
                if (FanControlModel.this.isSiopLevelValid) {
                    FanControlModel.this.onFanControlResult(fan_level);
                }
            }
        };
        this.mPhoneStateListener = new PhoneStateListener() { // from class: com.samsung.android.lib.dexcontrol.model.common.FanControlModel.2
            @Override // android.telephony.PhoneStateListener
            public void onCallStateChanged(int i2, String str) {
                SLog.m128i(FanControlModel.TAG, "onCallStateChanged() : " + i2);
                if (i2 == 1) {
                    FanControlModel.this.mIsCallMode = false;
                } else if (i2 != 2) {
                    FanControlModel.this.mIsCallMode = false;
                } else {
                    FanControlModel.this.mIsCallMode = true;
                }
                if (FanControlModel.this.mIDexFanControl != null) {
                    FanControlModel.this.mIDexFanControl.setSystemRequest(FanControlModel.this.isExistSystemRequest());
                } else {
                    SLog.m127e(FanControlModel.TAG, "IDexFanControl is ull");
                }
            }
        };
        this.mAudioRecordingCallback = new AudioManager.AudioRecordingCallback() { // from class: com.samsung.android.lib.dexcontrol.model.common.FanControlModel.3
            @Override // android.media.AudioManager.AudioRecordingCallback
            public void onRecordingConfigChanged(List list) {
                boolean z2;
                SLog.m128i(FanControlModel.TAG, "onRecordingConfigChanged");
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
                        SLog.m128i(FanControlModel.TAG, "source : " + clientAudioSource);
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
                    if (FanControlModel.this.mIDexFanControl != null) {
                        FanControlModel.this.mIDexFanControl.setSystemRequest(FanControlModel.this.isExistSystemRequest());
                    }
                }
            }
        };
        initFanControlReceiver();
        this.mAudioSystemRequestEnable = z;
        this.mDexModel = i;
        registerAudioRecodingListener();
        if (this.mAudioSystemRequestEnable) {
            registerPhoneStateListener();
        }
    }

    public void creatFanControl() {
        this.isSiopLevelValid = false;
        this.mIDexFanControl = new DexFanControlManager(this.mSiopLevel, isExistSystemRequest(), this.mIDexFanControlListener, this.mDexModel);
    }

    public void updateResponsedFanLevel(int i) {
        SLog.m126d(TAG, "updateResponsedFanLevel " + i);
        this.mIDexFanControl.onDexFanLevelUpdated(i);
    }

    public final void registerPhoneStateListener() {
        TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService("phone");
        this.mTelephonyManager = telephonyManager;
        if (telephonyManager != null) {
            telephonyManager.listen(this.mPhoneStateListener, 32);
        }
    }

    public final void registerAudioRecodingListener() {
        AudioManager audioManager = (AudioManager) getContext().getSystemService("audio");
        this.mAudioManager = audioManager;
        if (audioManager != null) {
            audioManager.registerAudioRecordingCallback(this.mAudioRecordingCallback, null);
        } else {
            SLog.m127e(TAG, "registerAudioRecodingListener - mAudioManager is null");
        }
    }

    public final boolean isExistSystemRequest() {
        return this.mIsCallMode || this.mIsScreenOffMode || this.mIsAudioRecodingMode;
    }

    public final void initFanControlReceiver() {
        this.mDexMonitorIntentReceiver = new BroadcastReceiver() { // from class: com.samsung.android.lib.dexcontrol.model.common.FanControlModel.4
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if ("com.samsung.intent.action.CHECK_SIOP_LEVEL".equals(intent.getAction())) {
                    FanControlModel.this.mSiopLevel = intent.getIntExtra("siop_level_broadcast", -1);
                    SLog.m128i(FanControlModel.TAG, "BroadcastReceiver - SIOP LEVEL : " + FanControlModel.this.mSiopLevel);
                    FanControlModel.this.isSiopLevelValid = true;
                    if (FanControlModel.this.mIDexFanControl == null) {
                        SLog.m127e(FanControlModel.TAG, "onReceive - mIDexFanControl is null");
                        return;
                    } else {
                        if (FanControlModel.this.isTestAppSIOPEnable) {
                            return;
                        }
                        FanControlModel.this.mIDexFanControl.setSiopLevel(FanControlModel.this.mSiopLevel);
                        return;
                    }
                }
                if (FanControlModel.this.mIDexFanControl == null) {
                    SLog.m127e(FanControlModel.TAG, "onReceive - mIDexFanControl is null");
                    return;
                }
                if ("com.samsung.intent.action.CHECK_SIOP_LEVEL_TEMP".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("siop_level_broadcast", -1);
                    FanControlModel fanControlModel = FanControlModel.this;
                    fanControlModel.isTestAppSIOPEnable = fanControlModel.isSiopLevelValid = true;
                    SLog.m126d(FanControlModel.TAG, "SIOP LEVEL FROM TEST APP : " + intExtra + " " + FanControlModel.this.isTestAppSIOPEnable + " " + FanControlModel.this.isSiopLevelValid);
                    FanControlModel.this.mIDexFanControl.setSiopLevel(intExtra);
                    return;
                }
                if ("com.samsung.accessory.action.DEX_FAN_ON_TEMP".equals(intent.getAction())) {
                    FanControlModel.this.mIDexFanControl.setFanLevelForTest(DexFanControlManager.FAN_LEVEL.STRONG);
                    return;
                }
                if ("com.samsung.accessory.action.DEX_FAN_OFF_TEMP".equals(intent.getAction())) {
                    FanControlModel.this.mIDexFanControl.setFanLevelForTest(DexFanControlManager.FAN_LEVEL.STOP);
                    return;
                }
                if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                    FanControlModel.this.mIsScreenOffMode = true;
                    FanControlModel.this.mIDexFanControl.setSystemRequest(FanControlModel.this.isExistSystemRequest());
                    return;
                }
                if ("android.intent.action.USER_PRESENT".equals(intent.getAction())) {
                    FanControlModel.this.mIsScreenOffMode = false;
                    FanControlModel.this.mIDexFanControl.setSystemRequest(FanControlModel.this.isExistSystemRequest());
                    return;
                }
                if ("com.samsung.accessory.action.DEX_FAN_HOLDING".equals(intent.getAction())) {
                    int intExtra2 = intent.getIntExtra("dex_fan_holding_duration", 0);
                    String stringExtra = intent.getStringExtra("dex_fan_holding_sender");
                    if (stringExtra == null && (stringExtra = intent.getPackage()) == null) {
                        stringExtra = "Anonymous";
                    }
                    FanControlModel.this.mIDexFanControl.onFanHoldingEvent(stringExtra, intExtra2);
                    return;
                }
                if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
                    FanControlModel.this.registerAudioRecodingListener();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.intent.action.CHECK_SIOP_LEVEL");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("com.samsung.accessory.action.DEX_FAN_HOLDING");
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction("com.samsung.accessory.action.DEX_FAN_ON_TEMP");
        intentFilter.addAction("com.samsung.accessory.action.DEX_FAN_OFF_TEMP");
        intentFilter.addAction("com.samsung.intent.action.CHECK_SIOP_LEVEL_TEMP");
        getContext().registerReceiver(this.mDexMonitorIntentReceiver, intentFilter);
    }

    @Override // com.samsung.android.lib.dexcontrol.model.common.BaseModel
    public void unregisterReceiver() {
        super.unregisterReceiver();
        getContext().unregisterReceiver(this.mDexMonitorIntentReceiver);
    }

    @Override // com.samsung.android.lib.dexcontrol.model.common.BaseModel
    public void destroy() {
        super.destroy();
        IUvdmSender iUvdmSender = this.mMessageSender;
        if (iUvdmSender != null) {
            iUvdmSender.setResponseListener(null);
            this.mMessageSender.destroy();
        }
        IDexFanControl iDexFanControl = this.mIDexFanControl;
        if (iDexFanControl != null) {
            iDexFanControl.destroy();
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
}
