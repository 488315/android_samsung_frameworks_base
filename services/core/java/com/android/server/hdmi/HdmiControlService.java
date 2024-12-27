package com.android.server.hdmi;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.HdmiHotplugEvent;
import android.hardware.hdmi.HdmiPortInfo;
import android.hardware.hdmi.HdmiTimerRecordSources;
import android.hardware.hdmi.IHdmiCecSettingChangeListener;
import android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener;
import android.hardware.hdmi.IHdmiControlCallback;
import android.hardware.hdmi.IHdmiControlService;
import android.hardware.hdmi.IHdmiControlStatusChangeListener;
import android.hardware.hdmi.IHdmiDeviceEventListener;
import android.hardware.hdmi.IHdmiHotplugEventListener;
import android.hardware.hdmi.IHdmiInputChangeListener;
import android.hardware.hdmi.IHdmiMhlVendorCommandListener;
import android.hardware.hdmi.IHdmiRecordListener;
import android.hardware.hdmi.IHdmiSystemAudioModeChangeListener;
import android.hardware.hdmi.IHdmiVendorCommandListener;
import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceVolumeManager;
import android.media.VolumeInfo;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.media.tv.TvInputManager;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.sysprop.HdmiProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;

import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.attention.AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0;
import com.android.server.hdmi.HdmiCecController.AnonymousClass7;
import com.android.server.hdmi.HdmiEarcController.EarcAidlCallback;
import com.android.server.hdmi.HdmiEarcLocalDeviceTx.ReportCapsRunnable;
import com.android.server.location.gnss.hal.GnssNative;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class HdmiControlService extends SystemService {
    public static final List ABSOLUTE_VOLUME_BEHAVIORS;
    static final AudioDeviceAttributes AUDIO_OUTPUT_DEVICE_HDMI;
    static final AudioDeviceAttributes AUDIO_OUTPUT_DEVICE_HDMI_ARC;
    public static final List AVB_AUDIO_OUTPUT_DEVICES;
    public static final List FULL_AND_ABSOLUTE_VOLUME_BEHAVIORS;
    public static final Locale HONG_KONG = new Locale("zh", "HK");
    public static final Locale MACAU = new Locale("zh", "MO");
    public static final List PLAYBACK_AVB_AUDIO_OUTPUT_DEVICES;
    static final AudioAttributes STREAM_MUSIC_ATTRIBUTES;
    public static final List TV_AVB_AUDIO_OUTPUT_DEVICES;
    public static final Map sTerminologyToBibliographicMap;
    public AbsoluteVolumeChangedListener mAbsoluteVolumeChangedListener;
    public int mActivePortId;
    public final HdmiCecLocalDevice.ActiveSource mActiveSource;
    public boolean mAddressAllocated;
    public final HdmiCecAtomWriter mAtomWriter;
    public final Map mAudioDeviceVolumeBehaviors;
    public AudioDeviceVolumeManagerWrapper mAudioDeviceVolumeManager;
    public AudioManagerWrapper mAudioManager;
    public HdmiCecController mCecController;
    public final List mCecLocalDevices;
    public CecMessageBuffer mCecMessageBuffer;
    public int mCecVersion;
    public DeviceConfigWrapper mDeviceConfig;
    public final ArrayList mDeviceEventListenerRecords;
    public DisplayManager mDisplayManager;
    public IHdmiControlCallback mDisplayStatusCallback;
    public HdmiEarcController mEarcController;
    public boolean mEarcEnabled;
    public HdmiEarcLocalDevice mEarcLocalDevice;
    public int mEarcPortId;
    private boolean mEarcSupported;
    public boolean mEarcTxFeatureFlagEnabled;
    public final Handler mHandler;
    public HdmiCecConfig mHdmiCecConfig;
    public HdmiCecNetwork mHdmiCecNetwork;
    public final ArrayMap mHdmiCecSettingChangeListenerRecords;
    public int mHdmiCecVolumeControl;
    public final RemoteCallbackList mHdmiCecVolumeControlFeatureListenerRecords;
    public final HdmiControlBroadcastReceiver mHdmiControlBroadcastReceiver;
    public int mHdmiControlEnabled;
    public final ArrayList mHdmiControlStatusChangeListenerRecords;
    public final ArrayList mHotplugEventListenerRecords;
    public HdmiRecordListenerRecord mInputChangeListenerRecord;
    public Looper mIoLooper;
    public final HandlerThread mIoThread;
    public boolean mIsCecAvailable;
    public final Object mLock;
    public String mMenuLanguage;
    public HdmiMhlControllerStub mMhlController;
    public List mMhlDevices;
    public boolean mMhlInputChangeEnabled;
    public final ArrayList mMhlVendorCommandListenerRecords;
    public boolean mNumericSoundbarVolumeUiOnTvFeatureFlagEnabled;
    public IHdmiControlCallback mOtpCallbackPendingAddressAllocation;
    public PowerManagerWrapper mPowerManager;
    public PowerManagerInternalWrapper mPowerManagerInternal;
    public HdmiCecPowerStatusController mPowerStatusController;
    public boolean mProhibitMode;
    public HdmiRecordListenerRecord mRecordListenerRecord;
    public final SelectRequestBuffer mSelectRequestBuffer;
    public final AnonymousClass1 mServiceThreadExecutor;
    public final AnonymousClass2 mSettingChangeListener;
    public final SettingsObserver mSettingsObserver;
    public boolean mSoundbarModeFeatureFlagEnabled;
    public boolean mStandbyMessageReceived;
    public int mStreamMusicMaxVolume;
    public boolean mSystemAudioActivated;
    public final ArrayList mSystemAudioModeChangeListenerRecords;
    public boolean mTransitionFromArcToEarcTxEnabled;
    public TvInputManager mTvInputManager;
    public final ArrayList mVendorCommandListenerRecords;
    public PowerManagerWrapper.DefaultWakeLockWrapper mWakeLock;
    public boolean mWakeUpMessageReceived;

    /* renamed from: com.android.server.hdmi.HdmiControlService$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ HdmiControlService this$0;

        public /* synthetic */ AnonymousClass2(HdmiControlService hdmiControlService, int i) {
            this.$r8$classId = i;
            this.this$0 = hdmiControlService;
        }

        private final void onChange$com$android$server$hdmi$HdmiControlService$5(String str) {
            boolean z = this.this$0.mHdmiCecConfig.getIntValue("system_audio_control") == 1;
            if (this.this$0.isTvDeviceEnabled()) {
                HdmiCecLocalDeviceTv tv = this.this$0.tv();
                tv.assertRunOnServiceThread();
                synchronized (tv.mLock) {
                    tv.mSystemAudioControlFeatureEnabled = z;
                }
                if (tv.mService.mHdmiCecNetwork.getSafeCecDeviceInfo(5) != null) {
                    tv.changeSystemAudioMode(z, null);
                }
            }
            if (this.this$0.isAudioSystemDevice()) {
                if (this.this$0.audioSystem() == null) {
                    Slog.e(
                            "HdmiControlService",
                            "Audio System device has not registered yet. Can't turn system audio"
                                + " mode on.");
                    return;
                }
                HdmiCecLocalDeviceAudioSystem audioSystem = this.this$0.audioSystem();
                audioSystem.assertRunOnServiceThread();
                synchronized (audioSystem.mLock) {
                    audioSystem.mSystemAudioControlFeatureEnabled = z;
                }
                if (z) {
                    if (audioSystem.hasAction(SystemAudioInitiationActionFromAvr.class)) {
                        Slog.i(
                                "HdmiCecLocalDeviceAudioSystem",
                                "SystemAudioInitiationActionFromAvr is in progress. Restarting.");
                        audioSystem.removeAction(SystemAudioInitiationActionFromAvr.class);
                    }
                    audioSystem.addAndStartAction(
                            new SystemAudioInitiationActionFromAvr(audioSystem));
                }
            }
        }

        public final void onChange(final String str) {
            switch (this.$r8$classId) {
                case 0:
                    int intValue = this.this$0.mHdmiCecConfig.getIntValue("hdmi_cec_enabled");
                    HdmiControlService hdmiControlService = this.this$0;
                    hdmiControlService.assertRunOnServiceThread();
                    synchronized (hdmiControlService.mLock) {
                        hdmiControlService.mHdmiControlEnabled = intValue;
                    }
                    if (intValue != 1) {
                        hdmiControlService.setHdmiCecVolumeControlEnabledInternal(0);
                        hdmiControlService.invokeVendorCommandListenersOnControlStateChanged(
                                1, false);
                        hdmiControlService.runOnServiceThread(
                                new AnonymousClass30(hdmiControlService, 0));
                        hdmiControlService.announceHdmiControlStatusChange(intValue);
                        return;
                    }
                    hdmiControlService.mCecController.enableCec(true);
                    hdmiControlService.mCecController.enableSystemCecControl(true);
                    hdmiControlService.mMhlController.getClass();
                    hdmiControlService.initializeCec(0);
                    hdmiControlService.setHdmiCecVolumeControlEnabledInternal(
                            hdmiControlService
                                    .getHdmiCecConfig()
                                    .getIntValue("volume_control_enabled"));
                    return;
                case 1:
                    HdmiControlService.m577$$Nest$mreportFeatures(this.this$0, false);
                    return;
                case 2:
                    HdmiControlService.m577$$Nest$mreportFeatures(this.this$0, false);
                    return;
                case 3:
                    HdmiControlService.m577$$Nest$mreportFeatures(this.this$0, false);
                    return;
                case 4:
                    HdmiControlService.m577$$Nest$mreportFeatures(this.this$0, false);
                    return;
                case 5:
                    HdmiControlService hdmiControlService2 = this.this$0;
                    if (!hdmiControlService2.isTvDevice()) {
                        hdmiControlService2.setEarcEnabled(
                                hdmiControlService2.mHdmiCecConfig.getIntValue("earc_enabled"));
                        return;
                    }
                    int i = 0;
                    if ((hdmiControlService2.mHdmiCecConfig.getIntValue("earc_enabled") == 1)
                            && hdmiControlService2.mEarcTxFeatureFlagEnabled) {
                        i = 1;
                    }
                    hdmiControlService2.setEarcEnabled(i);
                    return;
                case 6:
                    HdmiControlService hdmiControlService3 = this.this$0;
                    int i2 = 0;
                    if ((hdmiControlService3.mHdmiCecConfig.getIntValue("soundbar_mode") == 1)
                            && hdmiControlService3.mSoundbarModeFeatureFlagEnabled) {
                        i2 = 1;
                    }
                    hdmiControlService3.setSoundbarMode(i2);
                    return;
                case 7:
                    synchronized (this.this$0.mLock) {
                        try {
                            if (this.this$0.mHdmiCecSettingChangeListenerRecords.containsKey(str)) {
                                ((RemoteCallbackList)
                                                this.this$0.mHdmiCecSettingChangeListenerRecords
                                                        .get(str))
                                        .broadcast(
                                                new Consumer() { // from class:
                                                    // com.android.server.hdmi.HdmiControlService$33$$ExternalSyntheticLambda0
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        HdmiControlService.AnonymousClass2
                                                                anonymousClass2 =
                                                                        HdmiControlService
                                                                                .AnonymousClass2
                                                                                .this;
                                                        String str2 = str;
                                                        IHdmiCecSettingChangeListener
                                                                iHdmiCecSettingChangeListener =
                                                                        (IHdmiCecSettingChangeListener)
                                                                                obj;
                                                        anonymousClass2.this$0.getClass();
                                                        try {
                                                            iHdmiCecSettingChangeListener.onChange(
                                                                    str2);
                                                        } catch (RemoteException e) {
                                                            Slog.e(
                                                                    "HdmiControlService",
                                                                    "Failed to report setting"
                                                                        + " change",
                                                                    e);
                                                        }
                                                    }
                                                });
                                return;
                            }
                            return;
                        } finally {
                        }
                    }
                case 8:
                    this.this$0.initializeCec(0);
                    return;
                case 9:
                    boolean z = this.this$0.mHdmiCecConfig.getIntValue("routing_control") == 1;
                    if (this.this$0.isAudioSystemDevice()) {
                        if (this.this$0.audioSystem() == null) {
                            Slog.w(
                                    "HdmiControlService",
                                    "Switch device has not registered yet. Can't turn routing on.");
                            return;
                        }
                        HdmiCecLocalDeviceAudioSystem audioSystem = this.this$0.audioSystem();
                        audioSystem.assertRunOnServiceThread();
                        synchronized (audioSystem.mLock) {
                            audioSystem.mRoutingControlFeatureEnabled = z;
                        }
                        return;
                    }
                    return;
                case 10:
                    onChange$com$android$server$hdmi$HdmiControlService$5(str);
                    return;
                case 11:
                    HdmiControlService hdmiControlService4 = this.this$0;
                    hdmiControlService4.setHdmiCecVolumeControlEnabledInternal(
                            hdmiControlService4
                                    .getHdmiCecConfig()
                                    .getIntValue("volume_control_enabled"));
                    return;
                case 12:
                    HdmiControlService hdmiControlService5 = this.this$0;
                    if (hdmiControlService5.isTvDeviceEnabled()) {
                        HdmiCecController hdmiCecController = hdmiControlService5.mCecController;
                        boolean autoWakeup = hdmiControlService5.tv().getAutoWakeup();
                        hdmiCecController.assertRunOnServiceThread();
                        HdmiLogger.debug("enableWakeupByOtp: %b", Boolean.valueOf(autoWakeup));
                        hdmiCecController.mNativeWrapperImpl.enableWakeupByOtp(autoWakeup);
                        return;
                    }
                    return;
                case 13:
                    HdmiControlService.m577$$Nest$mreportFeatures(this.this$0, true);
                    return;
                default:
                    HdmiControlService.m577$$Nest$mreportFeatures(this.this$0, false);
                    return;
            }
        }
    }

    /* renamed from: com.android.server.hdmi.HdmiControlService$21, reason: invalid class name */
    public final class AnonymousClass21 {
        public final /* synthetic */ ArrayList val$allocatedDevices;
        public final /* synthetic */ ArrayList val$allocatingDevices;
        public final /* synthetic */ int[] val$finished;
        public final /* synthetic */ int val$initiatedBy;
        public final /* synthetic */ HdmiCecLocalDevice val$localDevice;

        public AnonymousClass21(
                HdmiCecLocalDevice hdmiCecLocalDevice,
                ArrayList arrayList,
                ArrayList arrayList2,
                int[] iArr,
                int i) {
            this.val$localDevice = hdmiCecLocalDevice;
            this.val$allocatedDevices = arrayList;
            this.val$allocatingDevices = arrayList2;
            this.val$finished = iArr;
            this.val$initiatedBy = i;
        }
    }

    /* renamed from: com.android.server.hdmi.HdmiControlService$23, reason: invalid class name */
    public final class AnonymousClass23 implements Runnable {
        public final /* synthetic */ int $r8$classId = 0;
        public final /* synthetic */ Object val$listener;
        public final /* synthetic */ Object val$record;

        public AnonymousClass23(
                HdmiControlStatusChangeListenerRecord hdmiControlStatusChangeListenerRecord,
                IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) {
            this.val$record = hdmiControlStatusChangeListenerRecord;
            this.val$listener = iHdmiControlStatusChangeListener;
        }

        public AnonymousClass23(
                HotplugEventListenerRecord hotplugEventListenerRecord,
                IHdmiHotplugEventListener iHdmiHotplugEventListener) {
            this.val$record = hotplugEventListenerRecord;
            this.val$listener = iHdmiHotplugEventListener;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    synchronized (HdmiControlService.this.mLock) {
                        try {
                            if (HdmiControlService.this.mHdmiControlStatusChangeListenerRecords
                                    .contains(
                                            (HdmiControlStatusChangeListenerRecord)
                                                    this.val$record)) {
                                synchronized (HdmiControlService.this.mLock) {
                                    HdmiControlService hdmiControlService = HdmiControlService.this;
                                    IHdmiControlStatusChangeListener
                                            iHdmiControlStatusChangeListener =
                                                    (IHdmiControlStatusChangeListener)
                                                            this.val$listener;
                                    int i = hdmiControlService.mHdmiControlEnabled;
                                    List singletonList =
                                            Collections.singletonList(
                                                    iHdmiControlStatusChangeListener);
                                    if (i == 1) {
                                        hdmiControlService.queryDisplayStatus(
                                                hdmiControlService
                                                .new AnonymousClass26(singletonList, i));
                                    } else {
                                        hdmiControlService.mIsCecAvailable = false;
                                        if (!singletonList.isEmpty()) {
                                            HdmiControlService
                                                    .invokeHdmiControlStatusChangeListenerLocked(
                                                            i,
                                                            singletonList,
                                                            hdmiControlService.mIsCecAvailable);
                                        }
                                    }
                                }
                                return;
                            }
                            return;
                        } finally {
                        }
                    }
                default:
                    synchronized (HdmiControlService.this.mLock) {
                        try {
                            if (HdmiControlService.this.mHotplugEventListenerRecords.contains(
                                    (HotplugEventListenerRecord) this.val$record)) {
                                for (HdmiPortInfo hdmiPortInfo :
                                        HdmiControlService.this.getPortInfo()) {
                                    int id = hdmiPortInfo.getId();
                                    HdmiCecController hdmiCecController =
                                            HdmiControlService.this.mCecController;
                                    int id2 = hdmiPortInfo.getId();
                                    hdmiCecController.assertRunOnServiceThread();
                                    HdmiHotplugEvent hdmiHotplugEvent =
                                            new HdmiHotplugEvent(
                                                    id,
                                                    hdmiCecController.mNativeWrapperImpl
                                                            .nativeIsConnected(id2));
                                    synchronized (HdmiControlService.this.mLock) {
                                        HdmiControlService hdmiControlService2 =
                                                HdmiControlService.this;
                                        IHdmiHotplugEventListener iHdmiHotplugEventListener =
                                                (IHdmiHotplugEventListener) this.val$listener;
                                        hdmiControlService2.getClass();
                                        try {
                                            iHdmiHotplugEventListener.onReceived(hdmiHotplugEvent);
                                        } catch (RemoteException e) {
                                            Slog.e(
                                                    "HdmiControlService",
                                                    "Failed to report hotplug event:"
                                                            + hdmiHotplugEvent.toString(),
                                                    e);
                                        }
                                    }
                                }
                                return;
                            }
                            return;
                        } finally {
                        }
                    }
            }
        }
    }

    /* renamed from: com.android.server.hdmi.HdmiControlService$24, reason: invalid class name */
    public final class AnonymousClass24 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;
        public final /* synthetic */ Object val$listener;

        public /* synthetic */ AnonymousClass24(
                BinderService binderService, IHdmiControlCallback iHdmiControlCallback, int i) {
            this.$r8$classId = i;
            this.this$0 = binderService;
            this.val$listener = iHdmiControlCallback;
        }

        public AnonymousClass24(
                HdmiControlService hdmiControlService,
                IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) {
            this.$r8$classId = 0;
            this.this$0 = hdmiControlService;
            this.val$listener = iHdmiCecVolumeControlFeatureListener;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    synchronized (((HdmiControlService) this.this$0).mLock) {
                        try {
                            ((IHdmiCecVolumeControlFeatureListener) this.val$listener)
                                    .onHdmiCecVolumeControlFeature(
                                            ((HdmiControlService) this.this$0)
                                                    .mHdmiCecVolumeControl);
                        } catch (RemoteException e) {
                            Slog.e(
                                    "HdmiControlService",
                                    "Failed to report HdmiControlVolumeControlStatusChange: "
                                            + ((HdmiControlService) this.this$0)
                                                    .mHdmiCecVolumeControl,
                                    e);
                        }
                    }
                    return;
                case 1:
                    HdmiControlService.this.oneTouchPlay((IHdmiControlCallback) this.val$listener);
                    return;
                default:
                    HdmiControlService.this.queryDisplayStatus(
                            (IHdmiControlCallback) this.val$listener);
                    return;
            }
        }
    }

    /* renamed from: com.android.server.hdmi.HdmiControlService$26, reason: invalid class name */
    public final class AnonymousClass26 extends IHdmiControlCallback.Stub {
        public final /* synthetic */ int val$isEnabled;
        public final /* synthetic */ Collection val$listeners;

        public AnonymousClass26(Collection collection, int i) {
            this.val$listeners = collection;
            this.val$isEnabled = i;
        }

        public final void onComplete(int i) {
            HdmiControlService.this.mIsCecAvailable = i != -1;
            if (this.val$listeners.isEmpty()) {
                return;
            }
            HdmiControlService.invokeHdmiControlStatusChangeListenerLocked(
                    this.val$isEnabled,
                    this.val$listeners,
                    HdmiControlService.this.mIsCecAvailable);
        }
    }

    /* renamed from: com.android.server.hdmi.HdmiControlService$27, reason: invalid class name */
    public final class AnonymousClass27
            implements HdmiCecLocalDevice.PendingActionClearedCallback,
                    HdmiCecLocalDevice.StandbyCompletedCallback {
        public final /* synthetic */ Object val$devices;
        public final /* synthetic */ int val$standbyAction;

        public AnonymousClass27(int i, int[] iArr) {
            this.val$standbyAction = i;
            this.val$devices = iArr;
        }

        public AnonymousClass27(List list, int i) {
            this.val$devices = list;
            this.val$standbyAction = i;
        }

        @Override // com.android.server.hdmi.HdmiCecLocalDevice.PendingActionClearedCallback
        public void onCleared(HdmiCecLocalDevice hdmiCecLocalDevice) {
            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                    new StringBuilder("On standby-action cleared:"),
                    hdmiCecLocalDevice.mDeviceType,
                    "HdmiControlService");
            ((List) this.val$devices).remove(hdmiCecLocalDevice);
            if (((List) this.val$devices).isEmpty()) {
                HdmiControlService.this.onPendingActionsCleared(this.val$standbyAction);
            }
        }
    }

    /* renamed from: com.android.server.hdmi.HdmiControlService$29, reason: invalid class name */
    public final class AnonymousClass29 extends IHdmiControlCallback.Stub {
        public final void onComplete(int i) {
            if (i != 0) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i, "Failed to complete 'one touch play'. result=", "HdmiControlService");
            }
        }
    }

    /* renamed from: com.android.server.hdmi.HdmiControlService$30, reason: invalid class name */
    public final class AnonymousClass30 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ HdmiControlService this$0;

        public /* synthetic */ AnonymousClass30(HdmiControlService hdmiControlService, int i) {
            this.$r8$classId = i;
            this.this$0 = hdmiControlService;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    final HdmiControlService hdmiControlService = this.this$0;
                    hdmiControlService.getClass();
                    hdmiControlService.disableCecLocalDevices(
                            new HdmiCecLocalDevice.PendingActionClearedCallback() { // from class:
                                // com.android.server.hdmi.HdmiControlService.31

                                /* renamed from: com.android.server.hdmi.HdmiControlService$31$1, reason: invalid class name */
                                public final class AnonymousClass1 implements Runnable {
                                    public final /* synthetic */ int $r8$classId;
                                    public final /* synthetic */ Object this$1;

                                    public /* synthetic */ AnonymousClass1(int i, Object obj) {
                                        this.$r8$classId = i;
                                        this.this$1 = obj;
                                    }

                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        switch (this.$r8$classId) {
                                            case 0:
                                                HdmiControlService.this.mCecController.enableCec(
                                                        false);
                                                HdmiControlService.this.mCecController
                                                        .enableSystemCecControl(false);
                                                HdmiControlService.this.mMhlController.getClass();
                                                HdmiControlService.this.clearCecLocalDevices();
                                                break;
                                            case 1:
                                                if (!HdmiControlService.this
                                                        .isAudioSystemDevice()) {
                                                    Slog.e(
                                                            "HdmiControlService",
                                                            "Not an audio system device. Won't set"
                                                                + " system audio mode on");
                                                    break;
                                                } else if (HdmiControlService.this.audioSystem()
                                                        != null) {
                                                    if (!HdmiControlService.this
                                                            .audioSystem()
                                                            .checkSupportAndSetSystemAudioMode(
                                                                    true)) {
                                                        Slog.e(
                                                                "HdmiControlService",
                                                                "System Audio Mode is not"
                                                                    + " supported.");
                                                        break;
                                                    } else {
                                                        HdmiControlService hdmiControlService =
                                                                HdmiControlService.this;
                                                        hdmiControlService.sendCecCommand(
                                                                HdmiCecMessageBuilder
                                                                        .buildCommandWithBooleanParam(
                                                                                hdmiControlService
                                                                                        .audioSystem()
                                                                                        .getDeviceInfo()
                                                                                        .getLogicalAddress(),
                                                                                15,
                                                                                114,
                                                                                true),
                                                                null);
                                                        break;
                                                    }
                                                } else {
                                                    Slog.e(
                                                            "HdmiControlService",
                                                            "Audio System local device is not"
                                                                + " registered");
                                                    break;
                                                }
                                            default:
                                                HdmiControlService.this.toggleAndFollowTvPower();
                                                break;
                                        }
                                    }
                                }

                                @Override // com.android.server.hdmi.HdmiCecLocalDevice.PendingActionClearedCallback
                                public final void onCleared(HdmiCecLocalDevice hdmiCecLocalDevice) {
                                    HdmiControlService hdmiControlService2 =
                                            HdmiControlService.this;
                                    hdmiControlService2.assertRunOnServiceThread();
                                    final HdmiCecController hdmiCecController =
                                            hdmiControlService2.mCecController;
                                    final AnonymousClass1 anonymousClass1 =
                                            new AnonymousClass1(0, this);
                                    hdmiCecController.assertRunOnServiceThread();
                                    hdmiCecController.runOnIoThread(
                                            new Runnable() { // from class:
                                                // com.android.server.hdmi.HdmiCecController.6
                                                public final /* synthetic */ Runnable val$runnable;

                                                public AnonymousClass6(
                                                        final HdmiControlService.AnonymousClass31
                                                                        .AnonymousClass1
                                                                anonymousClass12) {
                                                    r2 = anonymousClass12;
                                                }

                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    HdmiCecController.this.runOnServiceThread(r2);
                                                }
                                            });
                                }
                            });
                    break;
                case 1:
                    HdmiControlService hdmiControlService2 = this.this$0;
                    hdmiControlService2.setEarcEnabledInHal(false, false);
                    hdmiControlService2.clearEarcLocalDevice();
                    break;
                default:
                    this.this$0.startArcAction(true, null);
                    break;
            }
        }
    }

    /* renamed from: com.android.server.hdmi.HdmiControlService$37, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass37 {
        public static final /* synthetic */ int[]
                $SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values;

        static {
            int[] iArr = new int[HdmiProperties.cec_device_types_values.values().length];
            $SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values = iArr;
            try {
                iArr[HdmiProperties.cec_device_types_values.TV.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values[
                                HdmiProperties.cec_device_types_values.RECORDING_DEVICE.ordinal()] =
                        2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values[
                                HdmiProperties.cec_device_types_values.RESERVED.ordinal()] =
                        3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values[
                                HdmiProperties.cec_device_types_values.TUNER.ordinal()] =
                        4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values[
                                HdmiProperties.cec_device_types_values.PLAYBACK_DEVICE.ordinal()] =
                        5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values[
                                HdmiProperties.cec_device_types_values.AUDIO_SYSTEM.ordinal()] =
                        6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values[
                                HdmiProperties.cec_device_types_values.PURE_CEC_SWITCH.ordinal()] =
                        7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$android$sysprop$HdmiProperties$cec_device_types_values[
                                HdmiProperties.cec_device_types_values.VIDEO_PROCESSOR.ordinal()] =
                        8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    class AbsoluteVolumeChangedListener
            implements AudioDeviceVolumeManager.OnAudioDeviceVolumeChangedListener {
        public final HdmiCecLocalDevice mLocalDevice;
        public final HdmiDeviceInfo mSystemAudioDevice;

        public AbsoluteVolumeChangedListener(
                HdmiCecLocalDevice hdmiCecLocalDevice, HdmiDeviceInfo hdmiDeviceInfo) {
            this.mLocalDevice = hdmiCecLocalDevice;
            this.mSystemAudioDevice = hdmiDeviceInfo;
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onAudioDeviceVolumeAdjusted(
                android.media.AudioDeviceAttributes r2,
                android.media.VolumeInfo r3,
                int r4,
                int r5) {
            /*
                r1 = this;
                r2 = -100
                r3 = 2
                r0 = 1
                if (r4 == r2) goto L4c
                r2 = -1
                if (r4 == r2) goto L49
                if (r4 == 0) goto L19
                if (r4 == r0) goto L16
                r2 = 100
                if (r4 == r2) goto L4c
                r2 = 101(0x65, float:1.42E-43)
                if (r4 == r2) goto L4c
                return
            L16:
                r2 = 24
                goto L4e
            L19:
                com.android.server.hdmi.HdmiControlService r2 = com.android.server.hdmi.HdmiControlService.this
                com.android.server.hdmi.HdmiCecLocalDeviceTv r2 = r2.tv()
                if (r2 == 0) goto L48
                com.android.server.hdmi.HdmiControlService r1 = com.android.server.hdmi.HdmiControlService.this
                com.android.server.hdmi.HdmiCecLocalDeviceTv r1 = r1.tv()
                r1.assertRunOnServiceThread()
                java.lang.Class<com.android.server.hdmi.AbsoluteVolumeAudioStatusAction> r2 = com.android.server.hdmi.AbsoluteVolumeAudioStatusAction.class
                java.util.List r1 = r1.getActions(r2)
                java.util.Iterator r1 = r1.iterator()
            L34:
                boolean r2 = r1.hasNext()
                if (r2 == 0) goto L48
                java.lang.Object r2 = r1.next()
                com.android.server.hdmi.AbsoluteVolumeAudioStatusAction r2 = (com.android.server.hdmi.AbsoluteVolumeAudioStatusAction) r2
                int r4 = r2.mState
                if (r4 != r3) goto L34
                r2.sendGiveAudioStatus()
                goto L34
            L48:
                return
            L49:
                r2 = 25
                goto L4e
            L4c:
                r2 = 164(0xa4, float:2.3E-43)
            L4e:
                r4 = 0
                if (r5 == 0) goto L62
                if (r5 == r0) goto L5c
                if (r5 == r3) goto L56
                return
            L56:
                com.android.server.hdmi.HdmiCecLocalDevice r1 = r1.mLocalDevice
                r1.sendVolumeKeyEvent(r2, r4)
                goto L6c
            L5c:
                com.android.server.hdmi.HdmiCecLocalDevice r1 = r1.mLocalDevice
                r1.sendVolumeKeyEvent(r2, r0)
                goto L6c
            L62:
                com.android.server.hdmi.HdmiCecLocalDevice r3 = r1.mLocalDevice
                r3.sendVolumeKeyEvent(r2, r0)
                com.android.server.hdmi.HdmiCecLocalDevice r1 = r1.mLocalDevice
                r1.sendVolumeKeyEvent(r2, r4)
            L6c:
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.hdmi.HdmiControlService.AbsoluteVolumeChangedListener.onAudioDeviceVolumeAdjusted(android.media.AudioDeviceAttributes,"
                        + " android.media.VolumeInfo, int, int):void");
        }

        public final void onAudioDeviceVolumeChanged(
                AudioDeviceAttributes audioDeviceAttributes, final VolumeInfo volumeInfo) {
            final int logicalAddress = this.mLocalDevice.getDeviceInfo().getLogicalAddress();
            if (this.mSystemAudioDevice.getDeviceFeatures().getSetAudioVolumeLevelSupport() == 1) {
                HdmiControlService.this.sendCecCommand(
                        SetAudioVolumeLevelMessage.build(
                                logicalAddress,
                                this.mSystemAudioDevice.getLogicalAddress(),
                                volumeInfo.getVolumeIndex()),
                        new SendMessageCallback() { // from class:
                                                    // com.android.server.hdmi.HdmiControlService$AbsoluteVolumeChangedListener$$ExternalSyntheticLambda0
                            @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                            public final void onSendCompleted(int i) {
                                VolumeInfo volumeInfo2 = volumeInfo;
                                HdmiControlService.AbsoluteVolumeChangedListener
                                        absoluteVolumeChangedListener =
                                                HdmiControlService.AbsoluteVolumeChangedListener
                                                        .this;
                                if (i == 0) {
                                    (HdmiControlService.this.isTvDevice()
                                                    ? HdmiControlService.this.tv()
                                                    : HdmiControlService.this.playback())
                                            .updateAvbVolume(volumeInfo2.getVolumeIndex());
                                } else {
                                    HdmiControlService.this.sendCecCommand(
                                            HdmiCecMessage.build(
                                                    logicalAddress,
                                                    absoluteVolumeChangedListener.mSystemAudioDevice
                                                            .getLogicalAddress(),
                                                    113));
                                }
                            }
                        });
            } else {
                (HdmiControlService.this.isTvDevice()
                                ? HdmiControlService.this.tv()
                                : HdmiControlService.this.playback())
                        .updateAvbVolume(volumeInfo.getVolumeIndex());
                HdmiControlService.this.sendCecCommand(
                        HdmiCecMessage.build(
                                logicalAddress, this.mSystemAudioDevice.getLogicalAddress(), 113),
                        null);
            }
        }
    }

    public final class BinderService extends IHdmiControlService.Stub {

        /* renamed from: com.android.server.hdmi.HdmiControlService$BinderService$1, reason: invalid class name */
        public final class AnonymousClass1 implements Runnable {
            public final /* synthetic */ int $r8$classId;
            public final /* synthetic */ BinderService this$1;
            public final /* synthetic */ Object val$callback;
            public final /* synthetic */ int val$deviceId;

            public AnonymousClass1(BinderService binderService, int i, byte[] bArr) {
                this.$r8$classId = 2;
                this.this$1 = binderService;
                this.val$deviceId = i;
                this.val$callback = bArr;
            }

            public /* synthetic */ AnonymousClass1(
                    BinderService binderService,
                    IHdmiControlCallback iHdmiControlCallback,
                    int i,
                    int i2) {
                this.$r8$classId = i2;
                this.this$1 = binderService;
                this.val$callback = iHdmiControlCallback;
                this.val$deviceId = i;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (this.$r8$classId) {
                    case 0:
                        if (((IHdmiControlCallback) this.val$callback) != null) {
                            HdmiCecLocalDeviceTv tv = HdmiControlService.this.tv();
                            HdmiCecLocalDevicePlayback playback =
                                    HdmiControlService.this.playback();
                            if (tv != null || playback != null) {
                                if (tv == null) {
                                    int i = this.val$deviceId;
                                    IHdmiControlCallback iHdmiControlCallback =
                                            (IHdmiControlCallback) this.val$callback;
                                    playback.assertRunOnServiceThread();
                                    int id = playback.getDeviceInfo().getId();
                                    HdmiControlService hdmiControlService = playback.mService;
                                    if (i != id) {
                                        HdmiDeviceInfo hdmiDeviceInfo =
                                                (HdmiDeviceInfo)
                                                        hdmiControlService.mHdmiCecNetwork
                                                                .mDeviceInfos.get(i);
                                        if (hdmiDeviceInfo != null) {
                                            if (!playback.isAlreadyActiveSource(
                                                    hdmiDeviceInfo,
                                                    hdmiDeviceInfo.getLogicalAddress(),
                                                    iHdmiControlCallback)) {
                                                if (!hdmiControlService.isCecControlEnabled()) {
                                                    playback.setActiveSource(
                                                            hdmiDeviceInfo.getLogicalAddress(),
                                                            hdmiDeviceInfo.getPhysicalAddress(),
                                                            "HdmiCecLocalDevicePlayback#deviceSelect()");
                                                    playback.invokeCallback(
                                                            6, iHdmiControlCallback);
                                                    break;
                                                } else {
                                                    playback.removeAction(
                                                            DeviceSelectActionFromPlayback.class);
                                                    playback.addAndStartAction(
                                                            new DeviceSelectActionFromPlayback(
                                                                    playback,
                                                                    hdmiDeviceInfo,
                                                                    iHdmiControlCallback,
                                                                    playback.getDeviceInfo()
                                                                                            .getCecVersion()
                                                                                    >= 6
                                                                            && hdmiDeviceInfo
                                                                                            .getCecVersion()
                                                                                    >= 6));
                                                    break;
                                                }
                                            }
                                        } else {
                                            playback.invokeCallback(3, iHdmiControlCallback);
                                            break;
                                        }
                                    } else {
                                        hdmiControlService.oneTouchPlay(iHdmiControlCallback);
                                        break;
                                    }
                                } else {
                                    HdmiControlService.this.mMhlController.getClass();
                                    tv.deviceSelect(
                                            this.val$deviceId,
                                            (IHdmiControlCallback) this.val$callback);
                                    break;
                                }
                            } else {
                                HdmiControlService hdmiControlService2 = HdmiControlService.this;
                                if (!hdmiControlService2.mAddressAllocated) {
                                    hdmiControlService2.mSelectRequestBuffer.mRequest =
                                            new SelectRequestBuffer.PortSelectRequest(
                                                    hdmiControlService2,
                                                    this.val$deviceId,
                                                    (IHdmiControlCallback) this.val$callback,
                                                    1);
                                    break;
                                } else if (!hdmiControlService2.isTvDevice()) {
                                    HdmiControlService hdmiControlService3 =
                                            HdmiControlService.this;
                                    IHdmiControlCallback iHdmiControlCallback2 =
                                            (IHdmiControlCallback) this.val$callback;
                                    hdmiControlService3.getClass();
                                    HdmiControlService.invokeCallback(2, iHdmiControlCallback2);
                                    break;
                                } else {
                                    Slog.e("HdmiControlService", "Local tv device not available");
                                    break;
                                }
                            }
                        } else {
                            Slog.e("HdmiControlService", "Callback cannot be null");
                            break;
                        }
                        break;
                    case 1:
                        if (((IHdmiControlCallback) this.val$callback) != null) {
                            HdmiCecLocalDeviceTv tv2 = HdmiControlService.this.tv();
                            if (tv2 == null) {
                                HdmiCecLocalDeviceAudioSystem audioSystem =
                                        HdmiControlService.this.audioSystem();
                                if (audioSystem == null) {
                                    HdmiControlService hdmiControlService4 =
                                            HdmiControlService.this;
                                    if (!hdmiControlService4.mAddressAllocated) {
                                        hdmiControlService4.mSelectRequestBuffer.mRequest =
                                                new SelectRequestBuffer.PortSelectRequest(
                                                        hdmiControlService4,
                                                        this.val$deviceId,
                                                        (IHdmiControlCallback) this.val$callback,
                                                        0);
                                        break;
                                    } else {
                                        Slog.w("HdmiControlService", "Local device not available");
                                        HdmiControlService hdmiControlService5 =
                                                HdmiControlService.this;
                                        IHdmiControlCallback iHdmiControlCallback3 =
                                                (IHdmiControlCallback) this.val$callback;
                                        hdmiControlService5.getClass();
                                        HdmiControlService.invokeCallback(2, iHdmiControlCallback3);
                                        break;
                                    }
                                } else {
                                    audioSystem.doManualPortSwitching(
                                            this.val$deviceId,
                                            (IHdmiControlCallback) this.val$callback);
                                    break;
                                }
                            } else {
                                tv2.doManualPortSwitching(
                                        this.val$deviceId,
                                        (IHdmiControlCallback) this.val$callback);
                                break;
                            }
                        } else {
                            Slog.e("HdmiControlService", "Callback cannot be null");
                            break;
                        }
                    default:
                        if (!HdmiControlService.this.isTvDeviceEnabled()) {
                            Slog.w("HdmiControlService", "TV device is not enabled.");
                            break;
                        } else {
                            HdmiControlService.this
                                    .tv()
                                    .startOneTouchRecord(
                                            this.val$deviceId, (byte[]) this.val$callback);
                            break;
                        }
                }
            }
        }

        /* renamed from: com.android.server.hdmi.HdmiControlService$BinderService$11, reason: invalid class name */
        public final class AnonymousClass11 implements Runnable {
            public final /* synthetic */ int $r8$classId;
            public final /* synthetic */ BinderService this$1;
            public final /* synthetic */ int val$physicalAddress;

            public AnonymousClass11(
                    int i, int i2, int i3, BinderService binderService, byte[] bArr) {
                this.$r8$classId = 2;
                this.this$1 = binderService;
                this.val$physicalAddress = i;
            }

            public /* synthetic */ AnonymousClass11(BinderService binderService, int i, int i2) {
                this.$r8$classId = i2;
                this.this$1 = binderService;
                this.val$physicalAddress = i;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (this.$r8$classId) {
                    case 0:
                        HdmiCecMessage build =
                                HdmiCecMessage.build(
                                        HdmiControlService
                                                .m574$$Nest$mgetRemoteControlSourceAddress(
                                                        HdmiControlService.this),
                                        15,
                                        134,
                                        HdmiCecMessageBuilder.physicalAddressToParam(
                                                this.val$physicalAddress));
                        if (HdmiControlService.this.mHdmiCecNetwork.physicalAddressToPortId(
                                        this.val$physicalAddress)
                                != -1) {
                            if (HdmiControlService.m575$$Nest$mgetSwitchDevice(
                                            HdmiControlService.this)
                                    != null) {
                                HdmiControlService.m575$$Nest$mgetSwitchDevice(
                                                HdmiControlService.this)
                                        .handleSetStreamPath(build);
                            } else {
                                Slog.e(
                                        "HdmiControlService",
                                        "Can't get the correct local device to handle routing.");
                            }
                        }
                        HdmiControlService.this.sendCecCommand(build, null);
                        break;
                    case 1:
                        if (!HdmiControlService.this.isTvDeviceEnabled()) {
                            Slog.w("HdmiControlService", "TV device is not enabled.");
                            break;
                        } else {
                            HdmiCecLocalDeviceTv tv = HdmiControlService.this.tv();
                            int i = this.val$physicalAddress;
                            tv.assertRunOnServiceThread();
                            HdmiControlService hdmiControlService = tv.mService;
                            if (!hdmiControlService.isCecControlEnabled()) {
                                Slog.w(
                                        "HdmiCecLocalDeviceTv",
                                        "Can not stop one touch record. CEC control is disabled.");
                                tv.announceOneTouchRecordResult(i, 51);
                                break;
                            } else if (!tv.checkRecorder(i)) {
                                DeviceIdleController$$ExternalSyntheticOutline0.m(
                                        i, "Invalid recorder address:", "HdmiCecLocalDeviceTv");
                                tv.announceOneTouchRecordResult(i, 49);
                                break;
                            } else {
                                tv.removeAction(OneTouchRecordAction.class);
                                hdmiControlService.sendCecCommand(
                                        HdmiCecMessage.build(
                                                tv.getDeviceInfo().getLogicalAddress(), i, 11),
                                        null);
                                Slog.i(
                                        "HdmiCecLocalDeviceTv",
                                        "Stop [One Touch Record]-Target:" + i);
                                break;
                            }
                        }
                    case 2:
                        if (!HdmiControlService.this.isCecControlEnabled()) {
                            Slog.w("HdmiControlService", "Hdmi control is disabled.");
                            break;
                        } else {
                            HdmiControlService.this.mMhlController.getClass();
                            HeapdumpWatcher$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("Invalid port id:"),
                                    this.val$physicalAddress,
                                    "HdmiControlService");
                            break;
                        }
                    default:
                        if (HdmiControlService.this.mHdmiCecNetwork.getLocalDevice(
                                        this.val$physicalAddress)
                                != null) {
                            if (HdmiControlService.this.audioSystem() != null) {
                                if (!HdmiControlService.this
                                        .audioSystem()
                                        .mService
                                        .isSystemAudioActivated()) {
                                    Slog.w(
                                            "HdmiControlService",
                                            "audio system is not in system audio mode");
                                    break;
                                } else {
                                    HdmiControlService.this.audioSystem().reportAudioStatus(0);
                                    break;
                                }
                            } else {
                                Slog.w("HdmiControlService", "audio system is not available");
                                break;
                            }
                        } else {
                            Slog.w("HdmiControlService", "Local device not available");
                            break;
                        }
                }
            }
        }

        /* renamed from: com.android.server.hdmi.HdmiControlService$BinderService$13, reason: invalid class name */
        public final class AnonymousClass13 implements Runnable {
            public final /* synthetic */ int $r8$classId;
            public final /* synthetic */ BinderService this$1;
            public final /* synthetic */ boolean val$mute;

            public /* synthetic */ AnonymousClass13(BinderService binderService, boolean z, int i) {
                this.$r8$classId = i;
                this.this$1 = binderService;
                this.val$mute = z;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (this.$r8$classId) {
                    case 0:
                        HdmiCecLocalDeviceTv tv = HdmiControlService.this.tv();
                        if (tv == null) {
                            Slog.w("HdmiControlService", "Local tv device not available");
                            return;
                        }
                        boolean z = this.val$mute;
                        tv.assertRunOnServiceThread();
                        if (tv.getAvrDeviceInfo() == null
                                || tv.mService.getHdmiCecVolumeControl() == 0) {
                            return;
                        }
                        HdmiLogger.debug("[A]:Change mute:%b", Boolean.valueOf(z));
                        synchronized (tv.mLock) {
                            try {
                                if (tv.mSystemAudioMute == z) {
                                    HdmiLogger.debug("No need to change mute.", new Object[0]);
                                } else if (tv.isSystemAudioActivated()) {
                                    tv.removeAction(VolumeControlAction.class);
                                    int logicalAddress = tv.getAvrDeviceInfo().getLogicalAddress();
                                    HdmiCecKeycode.KeycodeEntry[] keycodeEntryArr =
                                            HdmiCecKeycode.KEYCODE_ENTRIES;
                                    tv.sendUserControlPressedAndReleased(logicalAddress, 67);
                                } else {
                                    HdmiLogger.debug(
                                            "[A]:System audio is not activated.", new Object[0]);
                                }
                            } finally {
                            }
                        }
                        return;
                    case 1:
                        HdmiCecLocalDeviceTv tv2 = HdmiControlService.this.tv();
                        if (tv2 == null) {
                            Slog.w(
                                    "HdmiControlService",
                                    "Local tv device not available to change arc mode.");
                            return;
                        } else {
                            tv2.startArcAction(this.val$mute, null);
                            return;
                        }
                    default:
                        HdmiControlService hdmiControlService = HdmiControlService.this;
                        boolean z2 = this.val$mute;
                        hdmiControlService.assertRunOnServiceThread();
                        if (hdmiControlService.isPowerOnOrTransient() && z2) {
                            PowerManagerWrapper powerManagerWrapper =
                                    hdmiControlService.mPowerManager;
                            powerManagerWrapper.mPowerManager.goToSleep(
                                    SystemClock.uptimeMillis(), 5, 0);
                            if (hdmiControlService.playback() != null) {
                                hdmiControlService.playback().sendStandby(0);
                                return;
                            }
                            return;
                        }
                        if (!hdmiControlService.isPowerStandbyOrTransient() || z2) {
                            return;
                        }
                        PowerManagerWrapper powerManagerWrapper2 = hdmiControlService.mPowerManager;
                        powerManagerWrapper2.mPowerManager.wakeUp(
                                SystemClock.uptimeMillis(), 8, "android.server.hdmi:WAKE");
                        if (hdmiControlService.playback() != null) {
                            hdmiControlService.oneTouchPlay(new AnonymousClass29());
                            return;
                        }
                        return;
                }
            }
        }

        /* renamed from: com.android.server.hdmi.HdmiControlService$BinderService$19, reason: invalid class name */
        public final class AnonymousClass19 implements Runnable {
            public final /* synthetic */ int $r8$classId;
            public final /* synthetic */ BinderService this$1;
            public final /* synthetic */ byte[] val$recordSource;
            public final /* synthetic */ int val$recorderAddress;
            public final /* synthetic */ int val$sourceType;

            public /* synthetic */ AnonymousClass19(
                    int i, int i2, int i3, BinderService binderService, byte[] bArr) {
                this.$r8$classId = i3;
                this.this$1 = binderService;
                this.val$recorderAddress = i;
                this.val$sourceType = i2;
                this.val$recordSource = bArr;
            }

            @Override // java.lang.Runnable
            public final void run() {
                HdmiCecMessage build;
                switch (this.$r8$classId) {
                    case 0:
                        if (!HdmiControlService.this.isTvDeviceEnabled()) {
                            Slog.w("HdmiControlService", "TV device is not enabled.");
                            break;
                        } else {
                            HdmiCecLocalDeviceTv tv = HdmiControlService.this.tv();
                            int i = this.val$recorderAddress;
                            int i2 = this.val$sourceType;
                            byte[] bArr = this.val$recordSource;
                            tv.assertRunOnServiceThread();
                            if (!tv.mService.isCecControlEnabled()) {
                                Slog.w(
                                        "HdmiCecLocalDeviceTv",
                                        "Can not start one touch record. CEC control is disabled.");
                                tv.announceTimerRecordingResult(i, 3);
                                break;
                            } else if (!tv.checkRecorder(i)) {
                                DeviceIdleController$$ExternalSyntheticOutline0.m(
                                        i, "Invalid recorder address:", "HdmiCecLocalDeviceTv");
                                tv.announceTimerRecordingResult(i, 1);
                                break;
                            } else if (bArr != null
                                    && HdmiTimerRecordSources.checkTimerRecordSource(i2, bArr)) {
                                tv.addAndStartAction(new TimerRecordingAction(tv, i, i2, bArr));
                                StringBuilder sb =
                                        new StringBuilder("Start [Timer Recording]-Target:");
                                ServiceKeeper$$ExternalSyntheticOutline0.m(
                                        i, i2, ", SourceType:", ", RecordSource:", sb);
                                sb.append(Arrays.toString(bArr));
                                Slog.i("HdmiCecLocalDeviceTv", sb.toString());
                                break;
                            } else {
                                Slog.w(
                                        "HdmiCecLocalDeviceTv",
                                        "Invalid record source." + Arrays.toString(bArr));
                                tv.announceTimerRecordingResult(i, 2);
                                break;
                            }
                        }
                        break;
                    default:
                        if (!HdmiControlService.this.isTvDeviceEnabled()) {
                            Slog.w("HdmiControlService", "TV device is not enabled.");
                            break;
                        } else {
                            final HdmiCecLocalDeviceTv tv2 = HdmiControlService.this.tv();
                            final int i3 = this.val$recorderAddress;
                            int i4 = this.val$sourceType;
                            byte[] bArr2 = this.val$recordSource;
                            tv2.assertRunOnServiceThread();
                            HdmiControlService hdmiControlService = tv2.mService;
                            if (!hdmiControlService.isCecControlEnabled()) {
                                Slog.w(
                                        "HdmiCecLocalDeviceTv",
                                        "Can not start one touch record. CEC control is disabled.");
                                tv2.announceClearTimerRecordingResult(i3, 162);
                                break;
                            } else if (!tv2.checkRecorder(i3)) {
                                DeviceIdleController$$ExternalSyntheticOutline0.m(
                                        i3, "Invalid recorder address:", "HdmiCecLocalDeviceTv");
                                tv2.announceClearTimerRecordingResult(i3, 160);
                                break;
                            } else {
                                if (!(bArr2 != null
                                        && HdmiTimerRecordSources.checkTimerRecordSource(
                                                i4, bArr2))) {
                                    Slog.w(
                                            "HdmiCecLocalDeviceTv",
                                            "Invalid record source." + Arrays.toString(bArr2));
                                    tv2.announceClearTimerRecordingResult(i3, 161);
                                    break;
                                } else {
                                    if (i4 == 1) {
                                        build =
                                                HdmiCecMessage.build(
                                                        tv2.getDeviceInfo().getLogicalAddress(),
                                                        i3,
                                                        FrameworkStatsLog
                                                                .DEVICE_POLICY_EVENT__EVENT_ID__PROVISIONING_DPC_SETUP_COMPLETED,
                                                        bArr2);
                                    } else if (i4 == 2) {
                                        build =
                                                HdmiCecMessage.build(
                                                        tv2.getDeviceInfo().getLogicalAddress(),
                                                        i3,
                                                        51,
                                                        bArr2);
                                    } else if (i4 != 3) {
                                        DeviceIdleController$$ExternalSyntheticOutline0.m(
                                                i3, "Invalid source type:", "HdmiCecLocalDeviceTv");
                                        tv2.announceClearTimerRecordingResult(i3, 161);
                                        break;
                                    } else {
                                        build =
                                                HdmiCecMessage.build(
                                                        tv2.getDeviceInfo().getLogicalAddress(),
                                                        i3,
                                                        161,
                                                        bArr2);
                                    }
                                    hdmiControlService.sendCecCommand(
                                            build,
                                            new SendMessageCallback() { // from class:
                                                                        // com.android.server.hdmi.HdmiCecLocalDeviceTv.5
                                                @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                                                public final void onSendCompleted(int i5) {
                                                    if (i5 != 0) {
                                                        HdmiCecLocalDeviceTv.this
                                                                .announceClearTimerRecordingResult(
                                                                        i3, 161);
                                                    }
                                                }
                                            });
                                    break;
                                }
                            }
                        }
                        break;
                }
            }
        }

        /* renamed from: com.android.server.hdmi.HdmiControlService$BinderService$3, reason: invalid class name */
        public final class AnonymousClass3 implements Runnable {
            public final /* synthetic */ int $r8$classId = 1;
            public final /* synthetic */ int val$deviceType;
            public final /* synthetic */ boolean val$isPressed;
            public final /* synthetic */ int val$keyCode;

            public AnonymousClass3(int i, int i2, boolean z) {
                this.val$keyCode = i;
                this.val$deviceType = i2;
                this.val$isPressed = z;
            }

            public AnonymousClass3(int i, boolean z, int i2) {
                this.val$keyCode = i;
                this.val$isPressed = z;
                this.val$deviceType = i2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (this.$r8$classId) {
                    case 0:
                        HdmiControlService.this.mMhlController.getClass();
                        HdmiControlService hdmiControlService = HdmiControlService.this;
                        if (hdmiControlService.mCecController != null) {
                            HdmiCecLocalDevice localDevice =
                                    hdmiControlService.mHdmiCecNetwork.getLocalDevice(
                                            this.val$deviceType);
                            if (localDevice != null) {
                                int i = this.val$keyCode;
                                boolean z = this.val$isPressed;
                                localDevice.assertRunOnServiceThread();
                                if (!(HdmiCecKeycode.androidKeyToCecKey(i) != null)) {
                                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                                            i, "Unsupported key: ", "HdmiCecLocalDevice");
                                    break;
                                } else {
                                    List actions = localDevice.getActions(SendKeyAction.class);
                                    int findKeyReceiverAddress =
                                            localDevice.findKeyReceiverAddress();
                                    if (findKeyReceiverAddress != -1
                                            && findKeyReceiverAddress
                                                    != localDevice.mDeviceInfo
                                                            .getLogicalAddress()) {
                                        if (!actions.isEmpty()) {
                                            ((SendKeyAction) actions.get(0)).processKeyEvent(i, z);
                                            break;
                                        } else if (z) {
                                            localDevice.addAndStartAction(
                                                    new SendKeyAction(
                                                            localDevice,
                                                            findKeyReceiverAddress,
                                                            i));
                                            break;
                                        }
                                    } else {
                                        StringBuilder sb = new StringBuilder("Discard key event: ");
                                        sb.append(i);
                                        sb.append(", pressed:");
                                        sb.append(z);
                                        sb.append(", receiverAddr=");
                                        HeapdumpWatcher$$ExternalSyntheticOutline0.m(
                                                sb, findKeyReceiverAddress, "HdmiCecLocalDevice");
                                        break;
                                    }
                                }
                            } else {
                                Slog.w(
                                        "HdmiControlService",
                                        "Local device not available to send key event.");
                                break;
                            }
                        }
                        break;
                    default:
                        HdmiControlService hdmiControlService2 = HdmiControlService.this;
                        if (hdmiControlService2.mCecController != null) {
                            HdmiCecLocalDevice localDevice2 =
                                    hdmiControlService2.mHdmiCecNetwork.getLocalDevice(
                                            this.val$keyCode);
                            if (localDevice2 != null) {
                                localDevice2.sendVolumeKeyEvent(
                                        this.val$deviceType, this.val$isPressed);
                                break;
                            } else {
                                UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("Local device "),
                                        this.val$keyCode,
                                        " not available to send volume key event.",
                                        "HdmiControlService");
                                break;
                            }
                        } else {
                            Slog.w(
                                    "HdmiControlService",
                                    "CEC controller not available to send volume key event.");
                            break;
                        }
                }
            }
        }

        /* renamed from: com.android.server.hdmi.HdmiControlService$BinderService$9, reason: invalid class name */
        public final class AnonymousClass9 implements Runnable {
            public final /* synthetic */ int $r8$classId;
            public final /* synthetic */ BinderService this$1;
            public final /* synthetic */ int val$logicalAddress;
            public final /* synthetic */ int val$powerStatus;

            public /* synthetic */ AnonymousClass9(
                    BinderService binderService, int i, int i2, int i3) {
                this.$r8$classId = i3;
                this.this$1 = binderService;
                this.val$logicalAddress = i;
                this.val$powerStatus = i2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                switch (this.$r8$classId) {
                    case 0:
                        StringBuilder sb = new StringBuilder("Device ");
                        sb.append(this.val$logicalAddress);
                        sb.append(" power status is ");
                        UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                sb,
                                this.val$powerStatus,
                                " before standby command sent out",
                                "HdmiControlService");
                        HdmiControlService hdmiControlService = HdmiControlService.this;
                        hdmiControlService.sendCecCommand(
                                HdmiCecMessage.build(
                                        HdmiControlService
                                                .m574$$Nest$mgetRemoteControlSourceAddress(
                                                        hdmiControlService),
                                        this.val$logicalAddress,
                                        54),
                                null);
                        break;
                    case 1:
                        StringBuilder sb2 = new StringBuilder("Device ");
                        sb2.append(this.val$logicalAddress);
                        sb2.append(" power status is ");
                        CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0.m(
                                sb2,
                                this.val$powerStatus,
                                " before power on command sent out",
                                "HdmiControlService");
                        if (HdmiControlService.m575$$Nest$mgetSwitchDevice(HdmiControlService.this)
                                == null) {
                            Slog.e(
                                    "HdmiControlService",
                                    "Can't get the correct local device to handle routing.");
                            break;
                        } else {
                            HdmiControlService.m575$$Nest$mgetSwitchDevice(HdmiControlService.this)
                                    .sendUserControlPressedAndReleased(
                                            this.val$logicalAddress, 109);
                            break;
                        }
                    default:
                        HdmiControlService.this.mMhlController.getClass();
                        HdmiCecLocalDevice localDevice =
                                HdmiControlService.this.mHdmiCecNetwork.getLocalDevice(
                                        this.val$powerStatus);
                        if (localDevice == null) {
                            localDevice = HdmiControlService.this.audioSystem();
                        }
                        if (localDevice != null) {
                            localDevice.sendStandby(this.val$logicalAddress);
                            break;
                        } else {
                            Slog.w("HdmiControlService", "Local device not available");
                            break;
                        }
                }
            }
        }

        public BinderService() {}

        public final void addCecSettingChangeListener(
                String str, IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) {
            HdmiControlService.this.enforceAccessPermission();
            HdmiControlService hdmiControlService = HdmiControlService.this;
            synchronized (hdmiControlService.mLock) {
                try {
                    if (!hdmiControlService.mHdmiCecSettingChangeListenerRecords.containsKey(str)) {
                        hdmiControlService.mHdmiCecSettingChangeListenerRecords.put(
                                str, new RemoteCallbackList());
                        HdmiCecConfig hdmiCecConfig = hdmiControlService.mHdmiCecConfig;
                        AnonymousClass2 anonymousClass2 = hdmiControlService.mSettingChangeListener;
                        hdmiCecConfig.getClass();
                        hdmiCecConfig.registerChangeListener(
                                str, anonymousClass2, ConcurrentUtils.DIRECT_EXECUTOR);
                    }
                    ((RemoteCallbackList)
                                    hdmiControlService.mHdmiCecSettingChangeListenerRecords.get(
                                            str))
                            .register(iHdmiCecSettingChangeListener);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void addDeviceEventListener(
                IHdmiDeviceEventListener iHdmiDeviceEventListener) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService hdmiControlService = HdmiControlService.this;
            hdmiControlService.getClass();
            DeviceEventListenerRecord deviceEventListenerRecord =
                    hdmiControlService.new DeviceEventListenerRecord(iHdmiDeviceEventListener);
            try {
                iHdmiDeviceEventListener.asBinder().linkToDeath(deviceEventListenerRecord, 0);
                synchronized (hdmiControlService.mLock) {
                    hdmiControlService.mDeviceEventListenerRecords.add(deviceEventListenerRecord);
                }
            } catch (RemoteException unused) {
                Slog.w("HdmiControlService", "Listener already died");
            }
        }

        public final void addHdmiCecVolumeControlFeatureListener(
                IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.addHdmiCecVolumeControlFeatureListener(
                    iHdmiCecVolumeControlFeatureListener);
        }

        public final void addHdmiControlStatusChangeListener(
                IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.addHdmiControlStatusChangeListener(
                    iHdmiControlStatusChangeListener);
        }

        public final void addHdmiMhlVendorCommandListener(
                IHdmiMhlVendorCommandListener iHdmiMhlVendorCommandListener) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService hdmiControlService = HdmiControlService.this;
            hdmiControlService.getClass();
            HdmiMhlVendorCommandListenerRecord hdmiMhlVendorCommandListenerRecord =
                    hdmiControlService.new HdmiMhlVendorCommandListenerRecord();
            try {
                iHdmiMhlVendorCommandListener
                        .asBinder()
                        .linkToDeath(hdmiMhlVendorCommandListenerRecord, 0);
                synchronized (hdmiControlService.mLock) {
                    hdmiControlService.mMhlVendorCommandListenerRecords.add(
                            hdmiMhlVendorCommandListenerRecord);
                }
            } catch (RemoteException unused) {
                Slog.w("HdmiControlService", "Listener already died.");
            }
        }

        public final void addHotplugEventListener(
                IHdmiHotplugEventListener iHdmiHotplugEventListener) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService hdmiControlService = HdmiControlService.this;
            hdmiControlService.getClass();
            HotplugEventListenerRecord hotplugEventListenerRecord =
                    hdmiControlService.new HotplugEventListenerRecord(iHdmiHotplugEventListener);
            try {
                iHdmiHotplugEventListener.asBinder().linkToDeath(hotplugEventListenerRecord, 0);
                synchronized (hdmiControlService.mLock) {
                    hdmiControlService.mHotplugEventListenerRecords.add(hotplugEventListenerRecord);
                }
                hdmiControlService.runOnServiceThread(
                        hdmiControlService
                        .new AnonymousClass23(
                                hotplugEventListenerRecord, iHdmiHotplugEventListener));
            } catch (RemoteException unused) {
                Slog.w("HdmiControlService", "Listener already died");
            }
        }

        public final void addSystemAudioModeChangeListener(
                IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService hdmiControlService = HdmiControlService.this;
            hdmiControlService.getClass();
            SystemAudioModeChangeListenerRecord systemAudioModeChangeListenerRecord =
                    hdmiControlService
                    .new SystemAudioModeChangeListenerRecord(iHdmiSystemAudioModeChangeListener);
            try {
                iHdmiSystemAudioModeChangeListener
                        .asBinder()
                        .linkToDeath(systemAudioModeChangeListenerRecord, 0);
                synchronized (hdmiControlService.mLock) {
                    hdmiControlService.mSystemAudioModeChangeListenerRecords.add(
                            systemAudioModeChangeListenerRecord);
                }
            } catch (RemoteException unused) {
                Slog.w("HdmiControlService", "Listener already died");
            }
        }

        public final void addVendorCommandListener(
                IHdmiVendorCommandListener iHdmiVendorCommandListener, int i) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.addVendorCommandListener(iHdmiVendorCommandListener, i);
        }

        public final void askRemoteDeviceToBecomeActiveSource(int i) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(new AnonymousClass11(this, i, 0));
        }

        public final boolean canChangeSystemAudioMode() {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiCecLocalDeviceTv tv = HdmiControlService.this.tv();
            return (tv == null || tv.mService.mHdmiCecNetwork.getSafeCecDeviceInfo(5) == null)
                    ? false
                    : true;
        }

        public final void clearTimerRecording(int i, int i2, byte[] bArr) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(new AnonymousClass19(i, i2, 1, this, bArr));
        }

        public final void deviceSelect(int i, IHdmiControlCallback iHdmiControlCallback) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(
                    new AnonymousClass1(this, iHdmiControlCallback, i, 0));
        }

        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            boolean z;
            if (DumpUtils.checkDumpPermission(
                    HdmiControlService.this.getContext(), "HdmiControlService", printWriter)) {
                IndentingPrintWriter indentingPrintWriter =
                        new IndentingPrintWriter(printWriter, "  ");
                synchronized (HdmiControlService.this.mLock) {
                    indentingPrintWriter.println(
                            "mProhibitMode: " + HdmiControlService.this.mProhibitMode);
                }
                indentingPrintWriter.println(
                        "mPowerStatus: "
                                + HdmiControlService.this.mPowerStatusController.mPowerStatus);
                StringBuilder m =
                        AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0.m(
                                new StringBuilder("mIsCecAvailable: "),
                                HdmiControlService.this.mIsCecAvailable,
                                indentingPrintWriter,
                                "mCecVersion: ");
                m.append(HdmiControlService.this.mCecVersion);
                indentingPrintWriter.println(m.toString());
                indentingPrintWriter.println(
                        "mIsAbsoluteVolumeBehaviorEnabled: "
                                + HdmiControlService.this.isAbsoluteVolumeBehaviorEnabled());
                indentingPrintWriter.println("System_settings:");
                indentingPrintWriter.increaseIndent();
                StringBuilder sb = new StringBuilder("mMhlInputChangeEnabled: ");
                HdmiControlService hdmiControlService = HdmiControlService.this;
                synchronized (hdmiControlService.mLock) {
                    z = hdmiControlService.mMhlInputChangeEnabled;
                }
                StringBuilder m2 =
                        AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0.m(
                                sb, z, indentingPrintWriter, "mSystemAudioActivated: ");
                m2.append(HdmiControlService.this.isSystemAudioActivated());
                indentingPrintWriter.println(m2.toString());
                indentingPrintWriter.println(
                        "mHdmiCecVolumeControlEnabled: "
                                + HdmiControlService.this.getHdmiCecVolumeControl());
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("CEC settings:");
                indentingPrintWriter.increaseIndent();
                HdmiCecConfig hdmiCecConfig = HdmiControlService.this.getHdmiCecConfig();
                hdmiCecConfig.getClass();
                ArrayList arrayList = new ArrayList(hdmiCecConfig.mSettings.keySet());
                HashSet hashSet = new HashSet(hdmiCecConfig.getUserSettings());
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    if (hdmiCecConfig.getSetting(str) == null) {
                        throw new IllegalArgumentException(
                                XmlUtils$$ExternalSyntheticOutline0.m(
                                        "Setting '", str, "' does not exist."));
                    }
                    if (hdmiCecConfig.getSetting(str).getValueType().equals("string")) {
                        StringBuilder m3 =
                                Preconditions$$ExternalSyntheticOutline0.m(str, " (string): ");
                        m3.append(hdmiCecConfig.getStringValue(str));
                        m3.append(" (default: ");
                        HdmiCecConfig.Setting setting = hdmiCecConfig.getSetting(str);
                        if (setting == null) {
                            throw new IllegalArgumentException(
                                    XmlUtils$$ExternalSyntheticOutline0.m(
                                            "Setting '", str, "' does not exist."));
                        }
                        if (!setting.getValueType().equals("string")) {
                            throw new IllegalArgumentException(
                                    XmlUtils$$ExternalSyntheticOutline0.m(
                                            "Setting '", str, "' is not a string-type setting."));
                        }
                        m3.append(hdmiCecConfig.getSetting(str).getDefaultValue().mStringValue);
                        m3.append(")");
                        m3.append(hashSet.contains(str) ? " [modifiable]" : "");
                        indentingPrintWriter.println(m3.toString());
                    } else {
                        if (hdmiCecConfig.getSetting(str) == null) {
                            throw new IllegalArgumentException(
                                    XmlUtils$$ExternalSyntheticOutline0.m(
                                            "Setting '", str, "' does not exist."));
                        }
                        if (hdmiCecConfig.getSetting(str).getValueType().equals("int")) {
                            StringBuilder m4 =
                                    Preconditions$$ExternalSyntheticOutline0.m(str, " (int): ");
                            m4.append(hdmiCecConfig.getIntValue(str));
                            m4.append(" (default: ");
                            HdmiCecConfig.Setting setting2 = hdmiCecConfig.getSetting(str);
                            if (setting2 == null) {
                                throw new IllegalArgumentException(
                                        XmlUtils$$ExternalSyntheticOutline0.m(
                                                "Setting '", str, "' does not exist."));
                            }
                            if (!setting2.getValueType().equals("int")) {
                                throw new IllegalArgumentException(
                                        XmlUtils$$ExternalSyntheticOutline0.m(
                                                "Setting '",
                                                str,
                                                "' is not a string-type setting."));
                            }
                            m4.append(
                                    hdmiCecConfig
                                            .getSetting(str)
                                            .getDefaultValue()
                                            .mIntValue
                                            .intValue());
                            m4.append(")");
                            m4.append(hashSet.contains(str) ? " [modifiable]" : "");
                            indentingPrintWriter.println(m4.toString());
                        } else {
                            continue;
                        }
                    }
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("mMhlController: ");
                indentingPrintWriter.increaseIndent();
                HdmiControlService.this.mMhlController.getClass();
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.print("eARC local device: ");
                indentingPrintWriter.increaseIndent();
                HdmiEarcLocalDevice hdmiEarcLocalDevice = HdmiControlService.this.mEarcLocalDevice;
                if (hdmiEarcLocalDevice == null) {
                    indentingPrintWriter.println("None. eARC is either disabled or not available.");
                } else {
                    HdmiEarcLocalDeviceTx hdmiEarcLocalDeviceTx =
                            (HdmiEarcLocalDeviceTx) hdmiEarcLocalDevice;
                    synchronized (hdmiEarcLocalDeviceTx.mLock) {
                        indentingPrintWriter.println(
                                "TX, mEarcStatus: " + hdmiEarcLocalDeviceTx.mEarcStatus);
                    }
                }
                indentingPrintWriter.decreaseIndent();
                HdmiCecNetwork hdmiCecNetwork = HdmiControlService.this.mHdmiCecNetwork;
                hdmiCecNetwork.getClass();
                indentingPrintWriter.println("HDMI CEC Network");
                indentingPrintWriter.increaseIndent();
                List list = hdmiCecNetwork.mPortInfo;
                Map map = HdmiUtils.ADDRESS_TO_TYPE;
                indentingPrintWriter.println("mPortInfo:");
                indentingPrintWriter.increaseIndent();
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    indentingPrintWriter.println(it2.next());
                }
                indentingPrintWriter.decreaseIndent();
                for (int i = 0; i < hdmiCecNetwork.mLocalDevices.size(); i++) {
                    indentingPrintWriter.println(
                            "HdmiCecLocalDevice #" + hdmiCecNetwork.mLocalDevices.keyAt(i) + ":");
                    indentingPrintWriter.increaseIndent();
                    ((HdmiCecLocalDevice) hdmiCecNetwork.mLocalDevices.valueAt(i))
                            .dump(indentingPrintWriter);
                    indentingPrintWriter.println("Active Source history:");
                    indentingPrintWriter.increaseIndent();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Iterator it3 =
                            ((HdmiCecLocalDevice) hdmiCecNetwork.mLocalDevices.valueAt(i))
                                    .mActiveSourceHistory.iterator();
                    while (it3.hasNext()) {
                        ((HdmiCecController.Dumpable) it3.next())
                                .dump(indentingPrintWriter, simpleDateFormat);
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.decreaseIndent();
                }
                List list2 = hdmiCecNetwork.mSafeAllDeviceInfos;
                indentingPrintWriter.println("mDeviceInfos:");
                indentingPrintWriter.increaseIndent();
                Iterator it4 = list2.iterator();
                while (it4.hasNext()) {
                    indentingPrintWriter.println(it4.next());
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.decreaseIndent();
                if (HdmiControlService.this.mCecController != null) {
                    indentingPrintWriter.println("mCecController: ");
                    indentingPrintWriter.increaseIndent();
                    HdmiCecController hdmiCecController = HdmiControlService.this.mCecController;
                    hdmiCecController.getClass();
                    indentingPrintWriter.println("CEC message history:");
                    indentingPrintWriter.increaseIndent();
                    SimpleDateFormat simpleDateFormat2 =
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Iterator it5 = hdmiCecController.mMessageHistory.iterator();
                    while (it5.hasNext()) {
                        ((HdmiCecController.Dumpable) it5.next())
                                .dump(indentingPrintWriter, simpleDateFormat2);
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.decreaseIndent();
                }
            }
        }

        public final HdmiDeviceInfo getActiveSource() {
            HdmiDeviceInfo hdmiDeviceInfo;
            int activePath;
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService hdmiControlService = HdmiControlService.this;
            if (hdmiControlService.playback() != null
                    && hdmiControlService.playback().isActiveSource()) {
                return hdmiControlService.playback().getDeviceInfo();
            }
            HdmiCecLocalDevice.ActiveSource localActiveSource =
                    hdmiControlService.getLocalActiveSource();
            if (HdmiUtils.isValidAddress(localActiveSource.logicalAddress)) {
                hdmiDeviceInfo =
                        hdmiControlService.mHdmiCecNetwork.getSafeCecDeviceInfo(
                                localActiveSource.logicalAddress);
                if (hdmiDeviceInfo == null) {
                    int i = localActiveSource.physicalAddress;
                    return HdmiDeviceInfo.hardwarePort(
                            i, hdmiControlService.mHdmiCecNetwork.physicalAddressToPortId(i));
                }
            } else {
                hdmiDeviceInfo = null;
                if (hdmiControlService.tv() != null
                        && (activePath = hdmiControlService.tv().getActivePath()) != 65535) {
                    Iterator it = hdmiControlService.mHdmiCecNetwork.mSafeAllDeviceInfos.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        HdmiDeviceInfo hdmiDeviceInfo2 = (HdmiDeviceInfo) it.next();
                        if (hdmiDeviceInfo2.getPhysicalAddress() == activePath) {
                            hdmiDeviceInfo = hdmiDeviceInfo2;
                            break;
                        }
                    }
                    if (hdmiDeviceInfo == null) {
                        return HdmiDeviceInfo.hardwarePort(
                                activePath, hdmiControlService.tv().getActivePortId());
                    }
                }
            }
            return hdmiDeviceInfo;
        }

        public final int[] getAllowedCecSettingIntValues(String str) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return HdmiControlService.this.getHdmiCecConfig().getAllowedIntValues(str).stream()
                        .mapToInt(new HdmiControlService$BinderService$$ExternalSyntheticLambda0())
                        .toArray();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final List getAllowedCecSettingStringValues(String str) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return HdmiControlService.this.getHdmiCecConfig().getAllowedStringValues(str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getCecSettingIntValue(String str) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return HdmiControlService.this.getHdmiCecConfig().getIntValue(str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final String getCecSettingStringValue(String str) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return HdmiControlService.this.getHdmiCecConfig().getStringValue(str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final List getDeviceList() {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiCecNetwork hdmiCecNetwork = HdmiControlService.this.mHdmiCecNetwork;
            hdmiCecNetwork.getClass();
            ArrayList arrayList = new ArrayList();
            for (HdmiDeviceInfo hdmiDeviceInfo : hdmiCecNetwork.mSafeAllDeviceInfos) {
                if (!hdmiCecNetwork.isLocalDeviceAddress(hdmiDeviceInfo.getLogicalAddress())) {
                    arrayList.add(hdmiDeviceInfo);
                }
            }
            return arrayList;
        }

        public final List getInputDevices() {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService hdmiControlService = HdmiControlService.this;
            List list = hdmiControlService.mHdmiCecNetwork.mSafeExternalInputs;
            List list2 = hdmiControlService.mMhlDevices;
            Map map = HdmiUtils.ADDRESS_TO_TYPE;
            if (list.isEmpty() && list2.isEmpty()) {
                return Collections.emptyList();
            }
            if (list.isEmpty()) {
                return Collections.unmodifiableList(list2);
            }
            if (list2.isEmpty()) {
                return Collections.unmodifiableList(list);
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(list);
            arrayList.addAll(list2);
            return Collections.unmodifiableList(arrayList);
        }

        public final int getMessageHistorySize() {
            int size;
            HdmiControlService.this.enforceAccessPermission();
            HdmiCecController hdmiCecController = HdmiControlService.this.mCecController;
            if (hdmiCecController == null) {
                return 0;
            }
            synchronized (hdmiCecController.mMessageHistoryLock) {
                size =
                        hdmiCecController.mMessageHistory.size()
                                + hdmiCecController.mMessageHistory.remainingCapacity();
            }
            return size;
        }

        public final int getPhysicalAddress() {
            int physicalAddress;
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            synchronized (HdmiControlService.this.mLock) {
                physicalAddress = HdmiControlService.this.mHdmiCecNetwork.getPhysicalAddress();
            }
            return physicalAddress;
        }

        public final List getPortInfo() {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            return HdmiControlService.this.getPortInfo() == null
                    ? Collections.emptyList()
                    : HdmiControlService.this.getPortInfo();
        }

        public final int[] getSupportedTypes() {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            int size = HdmiControlService.this.mCecLocalDevices.size();
            int[] iArr = new int[size];
            for (int i = 0; i < size; i++) {
                iArr[i] = ((Integer) HdmiControlService.this.mCecLocalDevices.get(i)).intValue();
            }
            return iArr;
        }

        public final boolean getSystemAudioMode() {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiCecLocalDeviceTv tv = HdmiControlService.this.tv();
            HdmiCecLocalDeviceAudioSystem audioSystem = HdmiControlService.this.audioSystem();
            return (tv != null && tv.isSystemAudioActivated())
                    || (audioSystem != null && audioSystem.mService.isSystemAudioActivated());
        }

        public final List getUserCecSettings() {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return HdmiControlService.this.getHdmiCecConfig().getUserSettings();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            new HdmiControlShellCommand(this)
                    .exec(
                            this,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        }

        public final void oneTouchPlay(IHdmiControlCallback iHdmiControlCallback) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    Binder.getCallingPid(),
                    "Process pid: ",
                    " is calling oneTouchPlay.",
                    "HdmiControlService");
            HdmiControlService.this.runOnServiceThread(
                    new AnonymousClass24(this, iHdmiControlCallback, 1));
        }

        public final void portSelect(int i, IHdmiControlCallback iHdmiControlCallback) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(
                    new AnonymousClass1(this, iHdmiControlCallback, i, 1));
        }

        public final void powerOffRemoteDevice(int i, int i2) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(new AnonymousClass9(this, i, i2, 0));
        }

        public final void powerOnRemoteDevice(int i, int i2) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(new AnonymousClass9(this, i, i2, 1));
        }

        public final void queryDisplayStatus(IHdmiControlCallback iHdmiControlCallback) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(
                    new AnonymousClass24(this, iHdmiControlCallback, 2));
        }

        public final void removeCecSettingChangeListener(
                String str, IHdmiCecSettingChangeListener iHdmiCecSettingChangeListener) {
            HdmiControlService.this.enforceAccessPermission();
            HdmiControlService hdmiControlService = HdmiControlService.this;
            synchronized (hdmiControlService.mLock) {
                try {
                    if (hdmiControlService.mHdmiCecSettingChangeListenerRecords.containsKey(str)) {
                        ((RemoteCallbackList)
                                        hdmiControlService.mHdmiCecSettingChangeListenerRecords.get(
                                                str))
                                .unregister(iHdmiCecSettingChangeListener);
                        if (((RemoteCallbackList)
                                                hdmiControlService
                                                        .mHdmiCecSettingChangeListenerRecords.get(
                                                        str))
                                        .getRegisteredCallbackCount()
                                == 0) {
                            hdmiControlService.mHdmiCecSettingChangeListenerRecords.remove(str);
                            hdmiControlService.mHdmiCecConfig.removeChangeListener(
                                    hdmiControlService.mSettingChangeListener, str);
                        }
                    }
                } finally {
                }
            }
        }

        public final void removeHdmiCecVolumeControlFeatureListener(
                IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.removeHdmiControlVolumeControlStatusChangeListener(
                    iHdmiCecVolumeControlFeatureListener);
        }

        public final void removeHdmiControlStatusChangeListener(
                IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService hdmiControlService = HdmiControlService.this;
            synchronized (hdmiControlService.mLock) {
                try {
                    Iterator it =
                            hdmiControlService.mHdmiControlStatusChangeListenerRecords.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        HdmiControlStatusChangeListenerRecord
                                hdmiControlStatusChangeListenerRecord =
                                        (HdmiControlStatusChangeListenerRecord) it.next();
                        if (hdmiControlStatusChangeListenerRecord.mListener.asBinder()
                                == iHdmiControlStatusChangeListener.asBinder()) {
                            iHdmiControlStatusChangeListener
                                    .asBinder()
                                    .unlinkToDeath(hdmiControlStatusChangeListenerRecord, 0);
                            hdmiControlService.mHdmiControlStatusChangeListenerRecords.remove(
                                    hdmiControlStatusChangeListenerRecord);
                            break;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void removeHotplugEventListener(
                IHdmiHotplugEventListener iHdmiHotplugEventListener) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService hdmiControlService = HdmiControlService.this;
            synchronized (hdmiControlService.mLock) {
                try {
                    Iterator it = hdmiControlService.mHotplugEventListenerRecords.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        HotplugEventListenerRecord hotplugEventListenerRecord =
                                (HotplugEventListenerRecord) it.next();
                        if (hotplugEventListenerRecord.mListener.asBinder()
                                == iHdmiHotplugEventListener.asBinder()) {
                            iHdmiHotplugEventListener
                                    .asBinder()
                                    .unlinkToDeath(hotplugEventListenerRecord, 0);
                            hdmiControlService.mHotplugEventListenerRecords.remove(
                                    hotplugEventListenerRecord);
                            break;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void removeSystemAudioModeChangeListener(
                IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService hdmiControlService = HdmiControlService.this;
            synchronized (hdmiControlService.mLock) {
                try {
                    Iterator it =
                            hdmiControlService.mSystemAudioModeChangeListenerRecords.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        SystemAudioModeChangeListenerRecord systemAudioModeChangeListenerRecord =
                                (SystemAudioModeChangeListenerRecord) it.next();
                        if (systemAudioModeChangeListenerRecord.mListener.asBinder()
                                == iHdmiSystemAudioModeChangeListener) {
                            iHdmiSystemAudioModeChangeListener
                                    .asBinder()
                                    .unlinkToDeath(systemAudioModeChangeListenerRecord, 0);
                            hdmiControlService.mSystemAudioModeChangeListenerRecords.remove(
                                    systemAudioModeChangeListenerRecord);
                            break;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void reportAudioStatus(int i, int i2, int i3, boolean z) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(new AnonymousClass11(this, i, 3));
        }

        public final void sendKeyEvent(int i, int i2, boolean z) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(new AnonymousClass3(i2, z, i));
        }

        public final void sendMhlVendorCommand(int i, int i2, int i3, byte[] bArr) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(new AnonymousClass11(i, i2, i3, this, bArr));
        }

        public final void sendStandby(int i, int i2) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(new AnonymousClass9(this, i2, i, 2));
        }

        public final void sendVendorCommand(
                final int i, final int i2, final byte[] bArr, final boolean z) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(
                    new Runnable() { // from class:
                                     // com.android.server.hdmi.HdmiControlService.BinderService.15
                        @Override // java.lang.Runnable
                        public final void run() {
                            HdmiCecLocalDevice localDevice =
                                    HdmiControlService.this.mHdmiCecNetwork.getLocalDevice(i);
                            if (localDevice == null) {
                                Slog.w("HdmiControlService", "Local device not available");
                                return;
                            }
                            if (!z) {
                                HdmiControlService.this.sendCecCommand(
                                        HdmiCecMessage.build(
                                                localDevice.getDeviceInfo().getLogicalAddress(),
                                                i2,
                                                137,
                                                bArr),
                                        null);
                                return;
                            }
                            HdmiControlService hdmiControlService = HdmiControlService.this;
                            int logicalAddress = localDevice.getDeviceInfo().getLogicalAddress();
                            int i3 = i2;
                            int vendorId = HdmiControlService.this.getVendorId();
                            byte[] bArr2 = bArr;
                            byte[] bArr3 = new byte[bArr2.length + 3];
                            bArr3[0] =
                                    (byte)
                                            ((vendorId >> 16)
                                                    & IDnsResolverUnsolicitedEventListener
                                                            .DNS_HEALTH_RESULT_TIMEOUT);
                            bArr3[1] =
                                    (byte)
                                            ((vendorId >> 8)
                                                    & IDnsResolverUnsolicitedEventListener
                                                            .DNS_HEALTH_RESULT_TIMEOUT);
                            bArr3[2] =
                                    (byte)
                                            (vendorId
                                                    & IDnsResolverUnsolicitedEventListener
                                                            .DNS_HEALTH_RESULT_TIMEOUT);
                            System.arraycopy(bArr2, 0, bArr3, 3, bArr2.length);
                            hdmiControlService.sendCecCommand(
                                    HdmiCecMessage.build(logicalAddress, i3, 160, bArr3), null);
                        }
                    });
        }

        public final void sendVolumeKeyEvent(int i, int i2, boolean z) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(new AnonymousClass3(i, i2, z));
        }

        public final void setArcMode(boolean z) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(new AnonymousClass13(this, z, 1));
        }

        public final void setCecSettingIntValue(String str, int i) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                HdmiControlService.this.getHdmiCecConfig().setIntValue(i, str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setCecSettingStringValue(String str, String str2) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                HdmiControlService.this.getHdmiCecConfig().setStringValue(str, str2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setHdmiRecordListener(IHdmiRecordListener iHdmiRecordListener) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService hdmiControlService = HdmiControlService.this;
            synchronized (hdmiControlService.mLock) {
                hdmiControlService.mRecordListenerRecord =
                        hdmiControlService.new HdmiRecordListenerRecord(iHdmiRecordListener);
                try {
                    iHdmiRecordListener
                            .asBinder()
                            .linkToDeath(hdmiControlService.mRecordListenerRecord, 0);
                } catch (RemoteException e) {
                    Slog.w("HdmiControlService", "Listener already died.", e);
                }
            }
        }

        public final void setInputChangeListener(
                IHdmiInputChangeListener iHdmiInputChangeListener) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService hdmiControlService = HdmiControlService.this;
            synchronized (hdmiControlService.mLock) {
                try {
                    hdmiControlService.mInputChangeListenerRecord =
                            hdmiControlService
                            .new HdmiRecordListenerRecord(iHdmiInputChangeListener);
                    try {
                        iHdmiInputChangeListener
                                .asBinder()
                                .linkToDeath(hdmiControlService.mInputChangeListenerRecord, 0);
                    } catch (RemoteException unused) {
                        Slog.w("HdmiControlService", "Listener already died");
                    }
                } finally {
                }
            }
        }

        public final boolean setMessageHistorySize(int i) {
            HdmiControlService.this.enforceAccessPermission();
            HdmiCecController hdmiCecController = HdmiControlService.this.mCecController;
            if (hdmiCecController == null) {
                return false;
            }
            hdmiCecController.getClass();
            if (i < 250) {
                return false;
            }
            ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(i);
            synchronized (hdmiCecController.mMessageHistoryLock) {
                try {
                    if (i < hdmiCecController.mMessageHistory.size()) {
                        for (int i2 = 0; i2 < hdmiCecController.mMessageHistory.size() - i; i2++) {
                            hdmiCecController.mMessageHistory.poll();
                        }
                    }
                    arrayBlockingQueue.addAll(hdmiCecController.mMessageHistory);
                    hdmiCecController.mMessageHistory = arrayBlockingQueue;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return true;
        }

        public final void setProhibitMode(boolean z) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            if (HdmiControlService.this.isTvDevice()) {
                HdmiControlService hdmiControlService = HdmiControlService.this;
                synchronized (hdmiControlService.mLock) {
                    hdmiControlService.mProhibitMode = z;
                }
            }
        }

        public final void setStandbyMode(boolean z) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(new AnonymousClass13(this, z, 2));
        }

        public final void setSystemAudioMode(
                final boolean z, final IHdmiControlCallback iHdmiControlCallback) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(
                    new Runnable() { // from class:
                                     // com.android.server.hdmi.HdmiControlService.BinderService.8
                        @Override // java.lang.Runnable
                        public final void run() {
                            HdmiCecLocalDeviceTv tv = HdmiControlService.this.tv();
                            if (tv != null) {
                                tv.changeSystemAudioMode(z, iHdmiControlCallback);
                                return;
                            }
                            Slog.w("HdmiControlService", "Local tv device not available");
                            HdmiControlService hdmiControlService = HdmiControlService.this;
                            IHdmiControlCallback iHdmiControlCallback2 = iHdmiControlCallback;
                            hdmiControlService.getClass();
                            HdmiControlService.invokeCallback(2, iHdmiControlCallback2);
                        }
                    });
        }

        public final void setSystemAudioModeOnForAudioOnlySource() {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(
                    new AnonymousClass31.AnonymousClass1(1, this));
        }

        public final void setSystemAudioMute(boolean z) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(new AnonymousClass13(this, z, 0));
        }

        public final void setSystemAudioVolume(final int i, final int i2, final int i3) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(
                    new Runnable() { // from class:
                                     // com.android.server.hdmi.HdmiControlService.BinderService.12
                        @Override // java.lang.Runnable
                        public final void run() {
                            HdmiCecLocalDeviceTv tv = HdmiControlService.this.tv();
                            if (tv == null) {
                                Slog.w("HdmiControlService", "Local tv device not available");
                                return;
                            }
                            int i4 = i;
                            int i5 = i2 - i4;
                            int i6 = i3;
                            tv.assertRunOnServiceThread();
                            if (tv.getAvrDeviceInfo() == null
                                    || i5 == 0
                                    || !tv.isSystemAudioActivated()
                                    || tv.mService.getHdmiCecVolumeControl() == 0) {
                                return;
                            }
                            int i7 = ((i4 + i5) * 100) / i6;
                            synchronized (tv.mLock) {
                                try {
                                    int i8 = tv.mSystemAudioVolume;
                                    if (i7 == i8) {
                                        tv.mService.setAudioStatus((i8 * i6) / 100);
                                    } else {
                                        List actions = tv.getActions(VolumeControlAction.class);
                                        if (actions.isEmpty()) {
                                            tv.addAndStartAction(
                                                    new VolumeControlAction(
                                                            tv,
                                                            tv.getAvrDeviceInfo()
                                                                    .getLogicalAddress(),
                                                            i5 > 0));
                                        } else {
                                            VolumeControlAction volumeControlAction =
                                                    (VolumeControlAction) actions.get(0);
                                            boolean z = i5 > 0;
                                            boolean z2 = volumeControlAction.mIsVolumeUp;
                                            if (z2 != z) {
                                                HdmiLogger.debug(
                                                        "Volume Key Status Changed[old:%b new:%b]",
                                                        Boolean.valueOf(z2), Boolean.valueOf(z));
                                                volumeControlAction.sendCommand(
                                                        HdmiCecMessage.build(
                                                                volumeControlAction
                                                                        .getSourceAddress(),
                                                                volumeControlAction.mAvrAddress,
                                                                69));
                                                volumeControlAction.mSentKeyPressed = false;
                                                volumeControlAction.mIsVolumeUp = z;
                                                volumeControlAction.sendVolumeKeyPressed();
                                                ((HdmiCecFeatureAction.ActionTimerHandler)
                                                                volumeControlAction.mActionTimer)
                                                        .clearTimerMessage();
                                                volumeControlAction.addTimer(1, 300);
                                            }
                                            volumeControlAction.mLastKeyUpdateTime =
                                                    System.currentTimeMillis();
                                        }
                                    }
                                } finally {
                                }
                            }
                        }
                    });
        }

        public final boolean shouldHandleTvPowerKey() {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            return HdmiControlService.this.shouldHandleTvPowerKey();
        }

        public final void startOneTouchRecord(int i, byte[] bArr) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(new AnonymousClass1(this, i, bArr));
        }

        public final void startTimerRecording(int i, int i2, byte[] bArr) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(new AnonymousClass19(i, i2, 0, this, bArr));
        }

        public final void stopOneTouchRecord(int i) {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            HdmiControlService.this.runOnServiceThread(new AnonymousClass11(this, i, 1));
        }

        public final void toggleAndFollowTvPower() {
            HdmiControlService.m576$$Nest$minitBinderCall(HdmiControlService.this);
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    Binder.getCallingPid(),
                    "Process pid: ",
                    " is calling toggleAndFollowTvPower.",
                    "HdmiControlService");
            HdmiControlService.this.runOnServiceThread(
                    new AnonymousClass31.AnonymousClass1(2, this));
        }
    }

    public final class DeviceEventListenerRecord implements IBinder.DeathRecipient {
        public final IHdmiDeviceEventListener mListener;

        public DeviceEventListenerRecord(IHdmiDeviceEventListener iHdmiDeviceEventListener) {
            this.mListener = iHdmiDeviceEventListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (HdmiControlService.this.mLock) {
                HdmiControlService.this.mDeviceEventListenerRecords.remove(this);
            }
        }
    }

    public interface DevicePollingCallback {
        void onPollingFinished(List list);
    }

    public final class HdmiControlBroadcastReceiver extends BroadcastReceiver {
        public HdmiControlBroadcastReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            boolean contains;
            HdmiControlService.this.assertRunOnServiceThread();
            contains = SystemProperties.get("sys.shutdown.requested").contains("1");
            String action = intent.getAction();
            action.getClass();
            switch (action) {
                case "android.intent.action.SCREEN_OFF":
                    if (HdmiControlService.this.isPowerOnOrTransient() && !contains) {
                        HdmiControlService.this.onStandby(0);
                        break;
                    }
                    break;
                case "android.intent.action.SCREEN_ON":
                    if (HdmiControlService.this.isPowerStandbyOrTransient()) {
                        HdmiControlService.this.onWakeUp(0);
                        break;
                    }
                    break;
                case "android.intent.action.CONFIGURATION_CHANGED":
                    String localeToMenuLanguage =
                            HdmiControlService.localeToMenuLanguage(Locale.getDefault());
                    if (!HdmiControlService.this.mMenuLanguage.equals(localeToMenuLanguage)) {
                        HdmiControlService hdmiControlService = HdmiControlService.this;
                        hdmiControlService.assertRunOnServiceThread();
                        hdmiControlService.mMenuLanguage = localeToMenuLanguage;
                        if (hdmiControlService.isTvDeviceEnabled()) {
                            hdmiControlService.tv().broadcastMenuLanguage(localeToMenuLanguage);
                            HdmiCecController hdmiCecController = hdmiControlService.mCecController;
                            hdmiCecController.assertRunOnServiceThread();
                            if (HdmiCecController.isLanguage(localeToMenuLanguage)) {
                                hdmiCecController.mNativeWrapperImpl.nativeSetLanguage(
                                        localeToMenuLanguage);
                                break;
                            }
                        }
                    }
                    break;
                case "android.intent.action.ACTION_SHUTDOWN":
                    if (HdmiControlService.this.isPowerOnOrTransient() && !contains) {
                        HdmiControlService.this.onStandby(1);
                        break;
                    }
                    break;
            }
        }
    }

    public final class HdmiControlStatusChangeListenerRecord implements IBinder.DeathRecipient {
        public final IHdmiControlStatusChangeListener mListener;

        public HdmiControlStatusChangeListenerRecord(
                IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) {
            this.mListener = iHdmiControlStatusChangeListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (HdmiControlService.this.mLock) {
                HdmiControlService.this.mHdmiControlStatusChangeListenerRecords.remove(this);
            }
        }

        public final boolean equals(Object obj) {
            if (obj instanceof HdmiControlStatusChangeListenerRecord) {
                return obj == this
                        || ((HdmiControlStatusChangeListenerRecord) obj).mListener
                                == this.mListener;
            }
            return false;
        }

        public final int hashCode() {
            return this.mListener.hashCode();
        }
    }

    public final class HdmiMhlVendorCommandListenerRecord implements IBinder.DeathRecipient {
        public HdmiMhlVendorCommandListenerRecord() {}

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            HdmiControlService.this.mMhlVendorCommandListenerRecords.remove(this);
        }
    }

    public final class HdmiRecordListenerRecord implements IBinder.DeathRecipient {
        public final /* synthetic */ int $r8$classId = 1;
        public final Object mListener;

        public HdmiRecordListenerRecord(IHdmiInputChangeListener iHdmiInputChangeListener) {
            this.mListener = iHdmiInputChangeListener;
        }

        public HdmiRecordListenerRecord(IHdmiRecordListener iHdmiRecordListener) {
            this.mListener = iHdmiRecordListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            switch (this.$r8$classId) {
                case 0:
                    synchronized (HdmiControlService.this.mLock) {
                        HdmiControlService hdmiControlService = HdmiControlService.this;
                        if (hdmiControlService.mRecordListenerRecord == this) {
                            hdmiControlService.mRecordListenerRecord = null;
                        }
                    }
                    return;
                default:
                    synchronized (HdmiControlService.this.mLock) {
                        HdmiControlService hdmiControlService2 = HdmiControlService.this;
                        if (hdmiControlService2.mInputChangeListenerRecord == this) {
                            hdmiControlService2.mInputChangeListenerRecord = null;
                        }
                    }
                    return;
            }
        }
    }

    public final class HotplugEventListenerRecord implements IBinder.DeathRecipient {
        public final IHdmiHotplugEventListener mListener;

        public HotplugEventListenerRecord(IHdmiHotplugEventListener iHdmiHotplugEventListener) {
            this.mListener = iHdmiHotplugEventListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (HdmiControlService.this.mLock) {
                HdmiControlService.this.mHotplugEventListenerRecords.remove(this);
            }
        }

        public final boolean equals(Object obj) {
            if (obj instanceof HotplugEventListenerRecord) {
                return obj == this
                        || ((HotplugEventListenerRecord) obj).mListener == this.mListener;
            }
            return false;
        }

        public final int hashCode() {
            return this.mListener.hashCode();
        }
    }

    public interface SendMessageCallback {
        void onSendCompleted(int i);
    }

    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        /* JADX WARN: Code restructure failed: missing block: B:48:0x002c, code lost:

           if (r7.equals("mhl_input_switching_enabled") == false) goto L4;
        */
        @Override // android.database.ContentObserver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onChange(boolean r6, android.net.Uri r7) {
            /*
                r5 = this;
                r6 = 0
                java.lang.String r7 = r7.getLastPathSegment()
                com.android.server.hdmi.HdmiControlService r0 = com.android.server.hdmi.HdmiControlService.this
                r1 = 1
                boolean r0 = r0.readBooleanSetting(r7, r1)
                r7.getClass()
                r2 = -1
                int r3 = r7.hashCode()
                switch(r3) {
                    case -1543071020: goto L2f;
                    case -1262529811: goto L25;
                    case -885757826: goto L19;
                    default: goto L17;
                }
            L17:
                r1 = r2
                goto L3a
            L19:
                java.lang.String r1 = "mhl_power_charge_enabled"
                boolean r1 = r7.equals(r1)
                if (r1 != 0) goto L23
                goto L17
            L23:
                r1 = 2
                goto L3a
            L25:
                java.lang.String r3 = "mhl_input_switching_enabled"
                boolean r3 = r7.equals(r3)
                if (r3 != 0) goto L3a
                goto L17
            L2f:
                java.lang.String r1 = "device_name"
                boolean r1 = r7.equals(r1)
                if (r1 != 0) goto L39
                goto L17
            L39:
                r1 = r6
            L3a:
                switch(r1) {
                    case 0: goto L59;
                    case 1: goto L48;
                    case 2: goto L3f;
                    default: goto L3d;
                }
            L3d:
                goto Lba
            L3f:
                com.android.server.hdmi.HdmiControlService r5 = com.android.server.hdmi.HdmiControlService.this
                com.android.server.hdmi.HdmiMhlControllerStub r5 = r5.mMhlController
                r5.getClass()
                goto Lba
            L48:
                com.android.server.hdmi.HdmiControlService r5 = com.android.server.hdmi.HdmiControlService.this
                com.android.server.hdmi.HdmiMhlControllerStub r6 = r5.mMhlController
                r6.getClass()
                java.lang.Object r1 = r5.mLock
                monitor-enter(r1)
                r5.mMhlInputChangeEnabled = r0     // Catch: java.lang.Throwable -> L56
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L56
                goto Lba
            L56:
                r5 = move-exception
                monitor-exit(r1)     // Catch: java.lang.Throwable -> L56
                throw r5
            L59:
                com.android.server.hdmi.HdmiControlService r0 = com.android.server.hdmi.HdmiControlService.this
                java.lang.String r1 = android.os.Build.MODEL
                android.content.Context r0 = r0.getContext()
                android.content.ContentResolver r0 = r0.getContentResolver()
                java.lang.String r7 = android.provider.Settings.Global.getString(r0, r7)
                boolean r0 = android.text.TextUtils.isEmpty(r7)
                if (r0 == 0) goto L70
                goto L71
            L70:
                r1 = r7
            L71:
                com.android.server.hdmi.HdmiControlService r5 = com.android.server.hdmi.HdmiControlService.this
                java.util.List r7 = r5.getAllCecLocalDevices()
                java.util.ArrayList r7 = (java.util.ArrayList) r7
                java.util.Iterator r7 = r7.iterator()
            L7d:
                boolean r0 = r7.hasNext()
                if (r0 == 0) goto Lba
                java.lang.Object r0 = r7.next()
                com.android.server.hdmi.HdmiCecLocalDevice r0 = (com.android.server.hdmi.HdmiCecLocalDevice) r0
                android.hardware.hdmi.HdmiDeviceInfo r2 = r0.getDeviceInfo()
                java.lang.String r3 = r2.getDisplayName()
                boolean r3 = r3.equals(r1)
                if (r3 == 0) goto L98
                goto L7d
            L98:
                android.hardware.hdmi.HdmiDeviceInfo$Builder r3 = r2.toBuilder()
                android.hardware.hdmi.HdmiDeviceInfo$Builder r3 = r3.setDisplayName(r1)
                android.hardware.hdmi.HdmiDeviceInfo r3 = r3.build()
                java.lang.Object r4 = r0.mLock
                monitor-enter(r4)
                r0.mDeviceInfo = r3     // Catch: java.lang.Throwable -> Lb7
                monitor-exit(r4)     // Catch: java.lang.Throwable -> Lb7
                int r0 = r2.getLogicalAddress()
                com.android.server.hdmi.HdmiCecMessage r0 = com.android.server.hdmi.HdmiCecMessageBuilder.buildSetOsdNameCommand(r0, r6, r1)
                r2 = 0
                r5.sendCecCommand(r0, r2)
                goto L7d
            Lb7:
                r5 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> Lb7
                throw r5
            Lba:
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.hdmi.HdmiControlService.SettingsObserver.onChange(boolean,"
                        + " android.net.Uri):void");
        }
    }

    public final class SystemAudioModeChangeListenerRecord implements IBinder.DeathRecipient {
        public final IHdmiSystemAudioModeChangeListener mListener;

        public SystemAudioModeChangeListenerRecord(
                IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) {
            this.mListener = iHdmiSystemAudioModeChangeListener;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (HdmiControlService.this.mLock) {
                HdmiControlService.this.mSystemAudioModeChangeListenerRecords.remove(this);
            }
        }
    }

    public final class VendorCommandListenerRecord implements IBinder.DeathRecipient {
        public final IHdmiVendorCommandListener mListener;
        public final int mVendorId;

        public VendorCommandListenerRecord(
                IHdmiVendorCommandListener iHdmiVendorCommandListener, int i) {
            this.mListener = iHdmiVendorCommandListener;
            this.mVendorId = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (HdmiControlService.this.mLock) {
                HdmiControlService.this.mVendorCommandListenerRecords.remove(this);
            }
        }
    }

    /* renamed from: -$$Nest$mgetRemoteControlSourceAddress, reason: not valid java name */
    public static int m574$$Nest$mgetRemoteControlSourceAddress(
            HdmiControlService hdmiControlService) {
        if (hdmiControlService.isAudioSystemDevice()) {
            return hdmiControlService.audioSystem().getDeviceInfo().getLogicalAddress();
        }
        if (hdmiControlService.isPlaybackDevice()) {
            return hdmiControlService.playback().getDeviceInfo().getLogicalAddress();
        }
        return 15;
    }

    /* renamed from: -$$Nest$mgetSwitchDevice, reason: not valid java name */
    public static HdmiCecLocalDeviceSource m575$$Nest$mgetSwitchDevice(
            HdmiControlService hdmiControlService) {
        if (hdmiControlService.isAudioSystemDevice()) {
            return hdmiControlService.audioSystem();
        }
        if (hdmiControlService.isPlaybackDevice()) {
            return hdmiControlService.playback();
        }
        return null;
    }

    /* renamed from: -$$Nest$minitBinderCall, reason: not valid java name */
    public static void m576$$Nest$minitBinderCall(HdmiControlService hdmiControlService) {
        hdmiControlService.enforceAccessPermission();
        Binder.setCallingWorkSourceUid(Binder.getCallingUid());
    }

    /* renamed from: -$$Nest$mreportFeatures, reason: not valid java name */
    public static void m577$$Nest$mreportFeatures(
            HdmiControlService hdmiControlService, boolean z) {
        if (hdmiControlService.getCecVersion() < 6) {
            return;
        }
        if (z) {
            if (hdmiControlService.isTvDeviceEnabled()) {
                hdmiControlService.tv().reportFeatures();
            }
        } else {
            HdmiCecLocalDevice audioSystem =
                    hdmiControlService.isAudioSystemDevice()
                            ? hdmiControlService.audioSystem()
                            : hdmiControlService.playback();
            if (audioSystem != null) {
                audioSystem.reportFeatures();
            }
        }
    }

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("sqi", "alb");
        hashMap.put("hye", "arm");
        hashMap.put("eus", "baq");
        hashMap.put("mya", "bur");
        hashMap.put("ces", "cze");
        hashMap.put("nld", "dut");
        hashMap.put("kat", "geo");
        hashMap.put("deu", "ger");
        hashMap.put("ell", "gre");
        hashMap.put("fra", "fre");
        hashMap.put("isl", "ice");
        hashMap.put("mkd", "mac");
        hashMap.put("mri", "mao");
        hashMap.put("msa", "may");
        hashMap.put("fas", "per");
        hashMap.put("ron", "rum");
        hashMap.put("slk", "slo");
        hashMap.put("bod", "tib");
        hashMap.put("cym", "wel");
        sTerminologyToBibliographicMap = Collections.unmodifiableMap(hashMap);
        AudioDeviceAttributes audioDeviceAttributes = new AudioDeviceAttributes(2, 9, "");
        AUDIO_OUTPUT_DEVICE_HDMI = audioDeviceAttributes;
        AudioDeviceAttributes audioDeviceAttributes2 = new AudioDeviceAttributes(2, 10, "");
        AUDIO_OUTPUT_DEVICE_HDMI_ARC = audioDeviceAttributes2;
        AudioDeviceAttributes audioDeviceAttributes3 = new AudioDeviceAttributes(2, 29, "");
        AVB_AUDIO_OUTPUT_DEVICES =
                List.of(audioDeviceAttributes, audioDeviceAttributes2, audioDeviceAttributes3);
        TV_AVB_AUDIO_OUTPUT_DEVICES = List.of(audioDeviceAttributes2, audioDeviceAttributes3);
        PLAYBACK_AVB_AUDIO_OUTPUT_DEVICES = List.of(audioDeviceAttributes);
        ABSOLUTE_VOLUME_BEHAVIORS = List.of(3, 5);
        FULL_AND_ABSOLUTE_VOLUME_BEHAVIORS = List.of(1, 3, 5);
        STREAM_MUSIC_ATTRIBUTES = new AudioAttributes.Builder().setLegacyStreamType(3).build();
    }

    public HdmiControlService(Context context) {
        super(context);
        this.mServiceThreadExecutor =
                new Executor() { // from class: com.android.server.hdmi.HdmiControlService.1
                    @Override // java.util.concurrent.Executor
                    public final void execute(Runnable runnable) {
                        HdmiControlService.this.runOnServiceThread(runnable);
                    }
                };
        this.mActiveSource = new HdmiCecLocalDevice.ActiveSource();
        this.mSystemAudioActivated = false;
        this.mAudioDeviceVolumeBehaviors = new HashMap();
        this.mIoThread = new HandlerThread("Hdmi Control Io Thread");
        this.mLock = new Object();
        this.mHdmiControlStatusChangeListenerRecords = new ArrayList();
        this.mHdmiCecVolumeControlFeatureListenerRecords = new RemoteCallbackList();
        this.mHotplugEventListenerRecords = new ArrayList();
        this.mDeviceEventListenerRecords = new ArrayList();
        this.mVendorCommandListenerRecords = new ArrayList();
        this.mHdmiCecSettingChangeListenerRecords = new ArrayMap();
        this.mEarcPortId = -1;
        this.mSystemAudioModeChangeListenerRecords = new ArrayList();
        Handler handler = new Handler();
        this.mHandler = handler;
        this.mHdmiControlBroadcastReceiver = new HdmiControlBroadcastReceiver();
        this.mDisplayStatusCallback = null;
        this.mOtpCallbackPendingAddressAllocation = null;
        this.mMenuLanguage = localeToMenuLanguage(Locale.getDefault());
        this.mStandbyMessageReceived = false;
        this.mWakeUpMessageReceived = false;
        this.mSoundbarModeFeatureFlagEnabled = false;
        this.mEarcTxFeatureFlagEnabled = false;
        this.mNumericSoundbarVolumeUiOnTvFeatureFlagEnabled = false;
        this.mTransitionFromArcToEarcTxEnabled = false;
        this.mMhlVendorCommandListenerRecords = new ArrayList();
        this.mAddressAllocated = false;
        this.mIsCecAvailable = false;
        this.mAtomWriter = new HdmiCecAtomWriter();
        this.mSelectRequestBuffer = new SelectRequestBuffer();
        this.mSettingChangeListener = new AnonymousClass2(this, 7);
        this.mCecLocalDevices = readDeviceTypes();
        this.mSettingsObserver = new SettingsObserver(handler);
        this.mHdmiCecConfig = new HdmiCecConfig(context);
        this.mDeviceConfig = new DeviceConfigWrapper();
    }

    public HdmiControlService(
            Context context,
            List list,
            AudioManagerWrapper audioManagerWrapper,
            AudioDeviceVolumeManagerWrapper audioDeviceVolumeManagerWrapper) {
        super(context);
        this.mServiceThreadExecutor =
                new Executor() { // from class: com.android.server.hdmi.HdmiControlService.1
                    @Override // java.util.concurrent.Executor
                    public final void execute(Runnable runnable) {
                        HdmiControlService.this.runOnServiceThread(runnable);
                    }
                };
        this.mActiveSource = new HdmiCecLocalDevice.ActiveSource();
        this.mSystemAudioActivated = false;
        this.mAudioDeviceVolumeBehaviors = new HashMap();
        this.mIoThread = new HandlerThread("Hdmi Control Io Thread");
        this.mLock = new Object();
        this.mHdmiControlStatusChangeListenerRecords = new ArrayList();
        this.mHdmiCecVolumeControlFeatureListenerRecords = new RemoteCallbackList();
        this.mHotplugEventListenerRecords = new ArrayList();
        this.mDeviceEventListenerRecords = new ArrayList();
        this.mVendorCommandListenerRecords = new ArrayList();
        this.mHdmiCecSettingChangeListenerRecords = new ArrayMap();
        this.mEarcPortId = -1;
        this.mSystemAudioModeChangeListenerRecords = new ArrayList();
        Handler handler = new Handler();
        this.mHandler = handler;
        this.mHdmiControlBroadcastReceiver = new HdmiControlBroadcastReceiver();
        this.mDisplayStatusCallback = null;
        this.mOtpCallbackPendingAddressAllocation = null;
        this.mMenuLanguage = localeToMenuLanguage(Locale.getDefault());
        this.mStandbyMessageReceived = false;
        this.mWakeUpMessageReceived = false;
        this.mSoundbarModeFeatureFlagEnabled = false;
        this.mEarcTxFeatureFlagEnabled = false;
        this.mNumericSoundbarVolumeUiOnTvFeatureFlagEnabled = false;
        this.mTransitionFromArcToEarcTxEnabled = false;
        this.mMhlVendorCommandListenerRecords = new ArrayList();
        this.mAddressAllocated = false;
        this.mIsCecAvailable = false;
        this.mAtomWriter = new HdmiCecAtomWriter();
        this.mSelectRequestBuffer = new SelectRequestBuffer();
        this.mSettingChangeListener = new AnonymousClass2(this, 7);
        this.mCecLocalDevices = list;
        this.mSettingsObserver = new SettingsObserver(handler);
        this.mHdmiCecConfig = new HdmiCecConfig(context);
        this.mDeviceConfig = new DeviceConfigWrapper();
        this.mAudioManager = audioManagerWrapper;
        this.mAudioDeviceVolumeManager = audioDeviceVolumeManagerWrapper;
    }

    public static void invokeCallback(int i, IHdmiControlCallback iHdmiControlCallback) {
        if (iHdmiControlCallback == null) {
            return;
        }
        try {
            iHdmiControlCallback.onComplete(i);
        } catch (RemoteException e) {
            Slog.e("HdmiControlService", "Invoking callback failed:" + e);
        }
    }

    public static void invokeHdmiControlStatusChangeListenerLocked(
            int i, Collection collection, boolean z) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            try {
                ((IHdmiControlStatusChangeListener) it.next()).onStatusChange(i, z);
            } catch (RemoteException e) {
                Slog.e(
                        "HdmiControlService",
                        AbstractAccessibilityServiceConnection$$ExternalSyntheticOutline0.m(
                                i,
                                "Failed to report HdmiControlStatusChange: ",
                                " isAvailable: ",
                                z),
                        e);
            }
        }
    }

    public static String localeToMenuLanguage(Locale locale) {
        if (locale.equals(Locale.TAIWAN) || locale.equals(HONG_KONG) || locale.equals(MACAU)) {
            return "chi";
        }
        String iSO3Language = locale.getISO3Language();
        Map map = sTerminologyToBibliographicMap;
        return map.containsKey(iSO3Language) ? (String) map.get(iSO3Language) : iSO3Language;
    }

    public void acquireWakeLock() {
        releaseWakeLock();
        PowerManager.WakeLock newWakeLock =
                this.mPowerManager.mPowerManager.newWakeLock(1, "HdmiControlService");
        this.mWakeLock = new PowerManagerWrapper.DefaultWakeLockWrapper(newWakeLock);
        newWakeLock.acquire(5000L);
    }

    public void addEarcLocalDevice(HdmiEarcLocalDevice hdmiEarcLocalDevice) {
        assertRunOnServiceThread();
        this.mEarcLocalDevice = hdmiEarcLocalDevice;
    }

    public void addHdmiCecVolumeControlFeatureListener(
            IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) {
        this.mHdmiCecVolumeControlFeatureListenerRecords.register(
                iHdmiCecVolumeControlFeatureListener);
        runOnServiceThread(new AnonymousClass24(this, iHdmiCecVolumeControlFeatureListener));
    }

    public void addHdmiControlStatusChangeListener(
            IHdmiControlStatusChangeListener iHdmiControlStatusChangeListener) {
        HdmiControlStatusChangeListenerRecord hdmiControlStatusChangeListenerRecord =
                new HdmiControlStatusChangeListenerRecord(iHdmiControlStatusChangeListener);
        try {
            iHdmiControlStatusChangeListener
                    .asBinder()
                    .linkToDeath(hdmiControlStatusChangeListenerRecord, 0);
            synchronized (this.mLock) {
                this.mHdmiControlStatusChangeListenerRecords.add(
                        hdmiControlStatusChangeListenerRecord);
            }
            runOnServiceThread(
                    new AnonymousClass23(
                            hdmiControlStatusChangeListenerRecord,
                            iHdmiControlStatusChangeListener));
        } catch (RemoteException unused) {
            Slog.w("HdmiControlService", "Listener already died");
        }
    }

    public void addVendorCommandListener(
            IHdmiVendorCommandListener iHdmiVendorCommandListener, int i) {
        VendorCommandListenerRecord vendorCommandListenerRecord =
                new VendorCommandListenerRecord(iHdmiVendorCommandListener, i);
        try {
            iHdmiVendorCommandListener.asBinder().linkToDeath(vendorCommandListenerRecord, 0);
            synchronized (this.mLock) {
                this.mVendorCommandListenerRecords.add(vendorCommandListenerRecord);
            }
        } catch (RemoteException unused) {
            Slog.w("HdmiControlService", "Listener already died");
        }
    }

    public void allocateLogicalAddress(ArrayList arrayList, int i) {
        assertRunOnServiceThread();
        HdmiCecController hdmiCecController = this.mCecController;
        hdmiCecController.assertRunOnServiceThread();
        hdmiCecController.mNativeWrapperImpl.nativeClearLogicalAddress();
        ArrayList arrayList2 = new ArrayList();
        int[] iArr = new int[1];
        this.mAddressAllocated = arrayList.isEmpty();
        this.mSelectRequestBuffer.mRequest = null;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            HdmiCecLocalDevice hdmiCecLocalDevice = (HdmiCecLocalDevice) it.next();
            final HdmiCecController hdmiCecController2 = this.mCecController;
            final int i2 = hdmiCecLocalDevice.mDeviceType;
            final int preferredAddress = hdmiCecLocalDevice.getPreferredAddress();
            final AnonymousClass21 anonymousClass21 =
                    new AnonymousClass21(hdmiCecLocalDevice, arrayList2, arrayList, iArr, i);
            hdmiCecController2.assertRunOnServiceThread();
            hdmiCecController2.mIoHandler.postDelayed(
                    new Runnable() { // from class: com.android.server.hdmi.HdmiCecController.3
                        public final /* synthetic */ HdmiControlService.AnonymousClass21
                                val$callback;
                        public final /* synthetic */ int val$deviceType;
                        public final /* synthetic */ int val$preferredAddress;

                        public AnonymousClass3(
                                final int i22,
                                final int preferredAddress2,
                                final HdmiControlService.AnonymousClass21 anonymousClass212) {
                            r2 = i22;
                            r3 = preferredAddress2;
                            r4 = anonymousClass212;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            int i3;
                            HdmiCecController hdmiCecController3 = HdmiCecController.this;
                            int i4 = r2;
                            int i5 = r3;
                            HdmiControlService.AnonymousClass21 anonymousClass212 = r4;
                            hdmiCecController3.getClass();
                            if (Looper.myLooper() != hdmiCecController3.mIoHandler.getLooper()) {
                                throw new IllegalStateException("Should run on io thread.");
                            }
                            ArrayList arrayList3 = new ArrayList();
                            if (HdmiUtils.isEligibleAddressForDevice(i4, i5)) {
                                arrayList3.add(Integer.valueOf(i5));
                            }
                            for (int i6 = 0; i6 < 16; i6++) {
                                if (!arrayList3.contains(Integer.valueOf(i6))
                                        && HdmiUtils.isEligibleAddressForDevice(i4, i6)) {
                                    int cecVersion = hdmiCecController3.mService.getCecVersion();
                                    if (HdmiUtils.isValidAddress(i6)
                                            && ((i6 != 12 && i6 != 13) || cecVersion >= 6)) {
                                        arrayList3.add(Integer.valueOf(i6));
                                    }
                                }
                            }
                            Iterator it2 = arrayList3.iterator();
                            loop1:
                            while (true) {
                                if (!it2.hasNext()) {
                                    i3 = 15;
                                    break;
                                }
                                Integer num = (Integer) it2.next();
                                for (int i7 = 0; i7 < 3; i7++) {
                                    if (hdmiCecController3.sendPollMessage(
                                            num.intValue(), num.intValue(), 1)) {
                                        break;
                                    }
                                }
                                i3 = num.intValue();
                                break loop1;
                            }
                            HdmiLogger.debug(
                                    "New logical address for device [%d]: [preferred:%d,"
                                        + " assigned:%d]",
                                    Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i3));
                            if (anonymousClass212 != null) {
                                hdmiCecController3.runOnServiceThread(
                                        new Runnable() { // from class:
                                            // com.android.server.hdmi.HdmiCecController.4
                                            public final /* synthetic */ int val$assignedAddress;
                                            public final /* synthetic */ int val$deviceType;

                                            public AnonymousClass4(int i42, int i32) {
                                                r2 = i42;
                                                r3 = i32;
                                            }

                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                HdmiControlService.AnonymousClass21
                                                        anonymousClass213 =
                                                                HdmiControlService.AnonymousClass21
                                                                        .this;
                                                int i8 = r2;
                                                int i9 = r3;
                                                if (i9 == 15) {
                                                    anonymousClass213.getClass();
                                                    Slog.e(
                                                            "HdmiControlService",
                                                            "Failed to allocate"
                                                                + " address:[device_type:"
                                                                    + i8
                                                                    + "]");
                                                    HdmiControlService.this.mHdmiCecNetwork
                                                            .mLocalDevices.remove(i8);
                                                } else {
                                                    HdmiControlService hdmiControlService =
                                                            HdmiControlService.this;
                                                    int cecVersion2 =
                                                            hdmiControlService.getCecVersion();
                                                    String str = Build.MODEL;
                                                    String string =
                                                            Settings.Global.getString(
                                                                    hdmiControlService
                                                                            .getContext()
                                                                            .getContentResolver(),
                                                                    "device_name");
                                                    if (!TextUtils.isEmpty(string)) {
                                                        str = string;
                                                    }
                                                    HdmiDeviceInfo build =
                                                            HdmiDeviceInfo.cecDeviceBuilder()
                                                                    .setLogicalAddress(i9)
                                                                    .setPhysicalAddress(
                                                                            hdmiControlService
                                                                                    .mHdmiCecNetwork
                                                                                    .getPhysicalAddress())
                                                                    .setPortId(
                                                                            hdmiControlService
                                                                                    .mHdmiCecNetwork
                                                                                    .physicalAddressToPortId(
                                                                                            hdmiControlService
                                                                                                    .mHdmiCecNetwork
                                                                                                    .getPhysicalAddress()))
                                                                    .setDeviceType(i8)
                                                                    .setVendorId(
                                                                            hdmiControlService
                                                                                    .getVendorId())
                                                                    .setDisplayName(str)
                                                                    .setDevicePowerStatus(0)
                                                                    .setCecVersion(cecVersion2)
                                                                    .build();
                                                    HdmiCecLocalDevice hdmiCecLocalDevice2 =
                                                            anonymousClass213.val$localDevice;
                                                    synchronized (hdmiCecLocalDevice2.mLock) {
                                                        hdmiCecLocalDevice2.mDeviceInfo = build;
                                                    }
                                                    HdmiControlService.this.mHdmiCecNetwork
                                                            .mLocalDevices.put(
                                                            i8, anonymousClass213.val$localDevice);
                                                    HdmiControlService.this.mHdmiCecNetwork
                                                            .addCecDevice(
                                                                    anonymousClass213
                                                                            .val$localDevice
                                                                            .getDeviceInfo());
                                                    HdmiCecController hdmiCecController4 =
                                                            HdmiControlService.this.mCecController;
                                                    hdmiCecController4.assertRunOnServiceThread();
                                                    if (HdmiUtils.isValidAddress(i9)) {
                                                        hdmiCecController4.mNativeWrapperImpl
                                                                .nativeAddLogicalAddress(i9);
                                                    }
                                                    anonymousClass213.val$allocatedDevices.add(
                                                            anonymousClass213.val$localDevice);
                                                }
                                                int size =
                                                        anonymousClass213.val$allocatingDevices
                                                                .size();
                                                int[] iArr2 = anonymousClass213.val$finished;
                                                int i10 = iArr2[0] + 1;
                                                iArr2[0] = i10;
                                                if (size == i10) {
                                                    int i11 = anonymousClass213.val$initiatedBy;
                                                    if (i11 != 4 && i11 != 5) {
                                                        HdmiControlService hdmiControlService2 =
                                                                HdmiControlService.this;
                                                        int i12 =
                                                                hdmiControlService2
                                                                        .mPowerStatusController
                                                                        .mPowerStatus;
                                                        int i13 = 2;
                                                        boolean z = i12 == 2;
                                                        Handler handler =
                                                                hdmiControlService2.mHandler;
                                                        if (z) {
                                                            handler.post(
                                                                    new HdmiControlService$$ExternalSyntheticLambda1(
                                                                            hdmiControlService2,
                                                                            2));
                                                        } else if (i12 == 3) {
                                                            handler.post(
                                                                    new HdmiControlService$$ExternalSyntheticLambda1(
                                                                            hdmiControlService2,
                                                                            3));
                                                        }
                                                        hdmiControlService2.mWakeUpMessageReceived =
                                                                false;
                                                        if (hdmiControlService2
                                                                .isTvDeviceEnabled()) {
                                                            HdmiCecController hdmiCecController5 =
                                                                    hdmiControlService2
                                                                            .mCecController;
                                                            boolean autoWakeup =
                                                                    hdmiControlService2
                                                                            .tv()
                                                                            .getAutoWakeup();
                                                            hdmiCecController5
                                                                    .assertRunOnServiceThread();
                                                            HdmiLogger.debug(
                                                                    "enableWakeupByOtp: %b",
                                                                    Boolean.valueOf(autoWakeup));
                                                            hdmiCecController5.mNativeWrapperImpl
                                                                    .enableWakeupByOtp(autoWakeup);
                                                        }
                                                        if (i11 == 0) {
                                                            i13 = 1;
                                                        } else if (i11 == 1) {
                                                            i13 = 0;
                                                        } else if (i11 == 2) {
                                                            Iterator it3 =
                                                                    ((ArrayList)
                                                                                    hdmiControlService2
                                                                                            .getAllCecLocalDevices())
                                                                            .iterator();
                                                            while (it3.hasNext()) {
                                                                ((HdmiCecLocalDevice) it3.next())
                                                                        .onInitializeCecComplete(
                                                                                i11);
                                                            }
                                                        } else if (i11 != 3) {
                                                            i13 = -1;
                                                        }
                                                        if (i13 != -1) {
                                                            hdmiControlService2
                                                                    .invokeVendorCommandListenersOnControlStateChanged(
                                                                            i13, true);
                                                            hdmiControlService2
                                                                    .announceHdmiControlStatusChange(
                                                                            1);
                                                        }
                                                    } else if (i11 == 4) {
                                                        HdmiControlService hdmiControlService3 =
                                                                HdmiControlService.this;
                                                        if (hdmiControlService3
                                                                        .mDisplayStatusCallback
                                                                == null) {
                                                            synchronized (
                                                                    hdmiControlService3.mLock) {
                                                                HdmiControlService
                                                                        hdmiControlService4 =
                                                                                HdmiControlService
                                                                                        .this;
                                                                hdmiControlService4
                                                                        .announceHdmiControlStatusChange(
                                                                                hdmiControlService4
                                                                                        .mHdmiControlEnabled);
                                                            }
                                                        }
                                                    }
                                                    HdmiCecNetwork hdmiCecNetwork =
                                                            HdmiControlService.this.mHdmiCecNetwork;
                                                    ArrayList arrayList4 =
                                                            anonymousClass213.val$allocatedDevices;
                                                    hdmiCecNetwork.getClass();
                                                    ArrayList arrayList5 = new ArrayList();
                                                    for (int i14 = 0;
                                                            i14
                                                                    < hdmiCecNetwork.mLocalDevices
                                                                            .size();
                                                            i14++) {
                                                        final int keyAt =
                                                                hdmiCecNetwork.mLocalDevices.keyAt(
                                                                        i14);
                                                        if (arrayList4.stream()
                                                                .noneMatch(
                                                                        new Predicate() { // from
                                                                            // class:
                                                                            // com.android.server.hdmi.HdmiCecNetwork$$ExternalSyntheticLambda0
                                                                            @Override // java.util.function.Predicate
                                                                            public final boolean
                                                                                    test(
                                                                                            Object
                                                                                                    obj) {
                                                                                HdmiCecLocalDevice
                                                                                        hdmiCecLocalDevice3 =
                                                                                                (HdmiCecLocalDevice)
                                                                                                        obj;
                                                                                return hdmiCecLocalDevice3
                                                                                                        .getDeviceInfo()
                                                                                                != null
                                                                                        && hdmiCecLocalDevice3
                                                                                                        .getDeviceInfo()
                                                                                                        .getDeviceType()
                                                                                                == keyAt;
                                                                            }
                                                                        })) {
                                                            arrayList5.add(Integer.valueOf(keyAt));
                                                        }
                                                    }
                                                    Iterator it4 = arrayList5.iterator();
                                                    while (it4.hasNext()) {
                                                        hdmiCecNetwork.mLocalDevices.remove(
                                                                ((Integer) it4.next()).intValue());
                                                    }
                                                    HdmiControlService hdmiControlService5 =
                                                            HdmiControlService.this;
                                                    hdmiControlService5.mAddressAllocated = true;
                                                    hdmiControlService5.notifyAddressAllocated(
                                                            anonymousClass213.val$allocatedDevices,
                                                            anonymousClass213.val$initiatedBy);
                                                    HdmiControlService hdmiControlService6 =
                                                            HdmiControlService.this;
                                                    IHdmiControlCallback iHdmiControlCallback =
                                                            hdmiControlService6
                                                                    .mDisplayStatusCallback;
                                                    if (iHdmiControlCallback != null) {
                                                        hdmiControlService6.queryDisplayStatus(
                                                                iHdmiControlCallback);
                                                        HdmiControlService.this
                                                                        .mDisplayStatusCallback =
                                                                null;
                                                    }
                                                    HdmiControlService hdmiControlService7 =
                                                            HdmiControlService.this;
                                                    IHdmiControlCallback iHdmiControlCallback2 =
                                                            hdmiControlService7
                                                                    .mOtpCallbackPendingAddressAllocation;
                                                    if (iHdmiControlCallback2 != null) {
                                                        hdmiControlService7.oneTouchPlay(
                                                                iHdmiControlCallback2);
                                                        HdmiControlService.this
                                                                        .mOtpCallbackPendingAddressAllocation =
                                                                null;
                                                    }
                                                    final CecMessageBuffer cecMessageBuffer =
                                                            HdmiControlService.this
                                                                    .mCecMessageBuffer;
                                                    Iterator it5 =
                                                            ((ArrayList) cecMessageBuffer.mBuffer)
                                                                    .iterator();
                                                    while (it5.hasNext()) {
                                                        final HdmiCecMessage hdmiCecMessage =
                                                                (HdmiCecMessage) it5.next();
                                                        cecMessageBuffer.mHdmiControlService
                                                                .runOnServiceThread(
                                                                        new Runnable() { // from
                                                                            // class:
                                                                            // com.android.server.hdmi.CecMessageBuffer.1
                                                                            @Override // java.lang.Runnable
                                                                            public final void
                                                                                    run() {
                                                                                CecMessageBuffer
                                                                                        .this
                                                                                        .mHdmiControlService
                                                                                        .handleCecCommand(
                                                                                                hdmiCecMessage);
                                                                            }
                                                                        });
                                                    }
                                                    ((ArrayList) cecMessageBuffer.mBuffer).clear();
                                                }
                                            }
                                        });
                            }
                        }
                    },
                    hdmiCecController2.mLogicalAddressAllocationDelay);
        }
    }

    public final void announceHdmiControlStatusChange(int i) {
        assertRunOnServiceThread();
        synchronized (this.mLock) {
            try {
                ArrayList arrayList =
                        new ArrayList(this.mHdmiControlStatusChangeListenerRecords.size());
                Iterator it = this.mHdmiControlStatusChangeListenerRecords.iterator();
                while (it.hasNext()) {
                    arrayList.add(((HdmiControlStatusChangeListenerRecord) it.next()).mListener);
                }
                if (i == 1) {
                    queryDisplayStatus(new AnonymousClass26(arrayList, i));
                } else {
                    this.mIsCecAvailable = false;
                    if (!arrayList.isEmpty()) {
                        invokeHdmiControlStatusChangeListenerLocked(
                                i, arrayList, this.mIsCecAvailable);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void announceSystemAudioModeChange(boolean z) {
        synchronized (this.mLock) {
            try {
                Iterator it = this.mSystemAudioModeChangeListenerRecords.iterator();
                while (it.hasNext()) {
                    try {
                        ((SystemAudioModeChangeListenerRecord) it.next())
                                .mListener.onStatusChanged(z);
                    } catch (RemoteException e) {
                        Slog.e("HdmiControlService", "Invoking callback failed:" + e);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void assertRunOnServiceThread() {
        if (Looper.myLooper() != this.mHandler.getLooper()) {
            throw new IllegalStateException("Should run on service thread.");
        }
    }

    public final HdmiCecLocalDeviceAudioSystem audioSystem() {
        return (HdmiCecLocalDeviceAudioSystem) this.mHdmiCecNetwork.getLocalDevice(5);
    }

    public final boolean canGoToStandby() {
        Iterator it = ((ArrayList) this.mHdmiCecNetwork.getLocalDeviceList()).iterator();
        while (it.hasNext()) {
            if (!((HdmiCecLocalDevice) it.next()).canGoToStandby()) {
                return false;
            }
        }
        return true;
    }

    public final void checkAndUpdateAbsoluteVolumeBehavior() {
        final HdmiCecLocalDevice playback;
        assertRunOnServiceThread();
        if (this.mAudioManager == null) {
            return;
        }
        if (this.mPowerStatusController != null && isPowerStandbyOrTransient()) {
            switchToFullVolumeBehavior();
            return;
        }
        if (isTvDevice() && tv() != null) {
            playback = tv();
            if (!isSystemAudioActivated()) {
                switchToFullVolumeBehavior();
                return;
            }
        } else if (!isPlaybackDevice() || playback() == null) {
            return;
        } else {
            playback = playback();
        }
        int findAudioReceiverAddress = playback.findAudioReceiverAddress();
        assertRunOnServiceThread();
        HdmiDeviceInfo cecDeviceInfo =
                this.mHdmiCecNetwork.getCecDeviceInfo(findAudioReceiverAddress);
        int deviceVolumeBehavior =
                getDeviceVolumeBehavior(
                        (AudioDeviceAttributes) getAvbCapableAudioOutputDevices().get(0));
        boolean contains =
                FULL_AND_ABSOLUTE_VOLUME_BEHAVIORS.contains(Integer.valueOf(deviceVolumeBehavior));
        if (getHdmiCecVolumeControl() != 1 || !contains) {
            switchToFullVolumeBehavior();
            return;
        }
        if (cecDeviceInfo == null) {
            switchToFullVolumeBehavior();
            return;
        }
        int setAudioVolumeLevelSupport =
                cecDeviceInfo.getDeviceFeatures().getSetAudioVolumeLevelSupport();
        if (setAudioVolumeLevelSupport == 0) {
            if (tv() == null || !this.mNumericSoundbarVolumeUiOnTvFeatureFlagEnabled) {
                switchToFullVolumeBehavior();
                return;
            }
            if (deviceVolumeBehavior != 5) {
                if (deviceVolumeBehavior == 3) {
                    Iterator it = getAvbCapableAudioOutputDevices().iterator();
                    while (it.hasNext()) {
                        ((DefaultAudioManagerWrapper) this.mAudioManager)
                                .mAudioManager.setDeviceVolumeBehavior(
                                        (AudioDeviceAttributes) it.next(), 1);
                    }
                }
                int logicalAddress = cecDeviceInfo.getLogicalAddress();
                playback.assertRunOnServiceThread();
                playback.removeAction(AbsoluteVolumeAudioStatusAction.class);
                playback.addAndStartAction(
                        new AbsoluteVolumeAudioStatusAction(playback, logicalAddress));
                return;
            }
            return;
        }
        if (setAudioVolumeLevelSupport == 1) {
            if (deviceVolumeBehavior != 3) {
                int logicalAddress2 = cecDeviceInfo.getLogicalAddress();
                playback.assertRunOnServiceThread();
                playback.removeAction(AbsoluteVolumeAudioStatusAction.class);
                playback.addAndStartAction(
                        new AbsoluteVolumeAudioStatusAction(playback, logicalAddress2));
                return;
            }
            return;
        }
        if (setAudioVolumeLevelSupport != 2) {
            return;
        }
        if (deviceVolumeBehavior == 3) {
            switchToFullVolumeBehavior();
        }
        int logicalAddress3 = cecDeviceInfo.getLogicalAddress();
        playback.assertRunOnServiceThread();
        HdmiControlService hdmiControlService = playback.mService;
        if (hdmiControlService.getCecVersion() >= 6) {
            hdmiControlService.sendCecCommand(
                    HdmiCecMessage.build(
                            playback.getDeviceInfo().getLogicalAddress(),
                            logicalAddress3,
                            FrameworkStatsLog
                                    .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_MISSING_WORK_APP),
                    null);
        }
        if (playback.getActions(SetAudioVolumeLevelDiscoveryAction.class).stream()
                .noneMatch(new HdmiCecLocalDevice$$ExternalSyntheticLambda0(logicalAddress3, 0))) {
            playback.addAndStartAction(
                    new SetAudioVolumeLevelDiscoveryAction(
                            playback,
                            logicalAddress3,
                            new IHdmiControlCallback
                                    .Stub() { // from class:
                                              // com.android.server.hdmi.HdmiCecLocalDevice.3
                                public AnonymousClass3() {}

                                public final void onComplete(int i) {
                                    if (i == 0) {
                                        HdmiCecLocalDevice.this.mService
                                                .checkAndUpdateAbsoluteVolumeBehavior();
                                    }
                                }
                            }));
        }
    }

    public void clearCecLocalDevices() {
        assertRunOnServiceThread();
        HdmiCecController hdmiCecController = this.mCecController;
        if (hdmiCecController == null) {
            return;
        }
        hdmiCecController.assertRunOnServiceThread();
        hdmiCecController.mNativeWrapperImpl.nativeClearLogicalAddress();
        HdmiCecNetwork hdmiCecNetwork = this.mHdmiCecNetwork;
        hdmiCecNetwork.assertRunOnServiceThread();
        hdmiCecNetwork.mLocalDevices.clear();
    }

    public void clearEarcLocalDevice() {
        assertRunOnServiceThread();
        this.mEarcLocalDevice = null;
    }

    public void disableCecLocalDevices(
            HdmiCecLocalDevice.PendingActionClearedCallback pendingActionClearedCallback) {
        if (this.mCecController != null) {
            Iterator it = ((ArrayList) this.mHdmiCecNetwork.getLocalDeviceList()).iterator();
            while (it.hasNext()) {
                ((HdmiCecLocalDevice) it.next())
                        .disableDevice(this.mStandbyMessageReceived, pendingActionClearedCallback);
            }
        }
        this.mMhlController.getClass();
    }

    public int dispatchMessageToLocalDevice(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        Iterator it = ((ArrayList) this.mHdmiCecNetwork.getLocalDeviceList()).iterator();
        while (it.hasNext()) {
            int dispatchMessage = ((HdmiCecLocalDevice) it.next()).dispatchMessage(hdmiCecMessage);
            if (dispatchMessage != -2 && hdmiCecMessage.mDestination != 15) {
                return dispatchMessage;
            }
        }
        if (hdmiCecMessage.mDestination == 15) {
            return -1;
        }
        HdmiLogger.warning("Unhandled cec command:" + hdmiCecMessage, new Object[0]);
        return -2;
    }

    public final boolean earcBlocksArcConnection() {
        boolean z;
        if (this.mEarcLocalDevice == null) {
            return false;
        }
        synchronized (this.mLock) {
            z = this.mEarcLocalDevice.mEarcStatus != 2;
        }
        return z;
    }

    public final void enableAudioReturnChannel(int i, boolean z) {
        if (!this.mTransitionFromArcToEarcTxEnabled && z && this.mEarcController != null) {
            setEarcEnabledInHal(false, false);
        }
        HdmiCecController hdmiCecController = this.mCecController;
        hdmiCecController.assertRunOnServiceThread();
        hdmiCecController.mNativeWrapperImpl.nativeEnableAudioReturnChannel(i, z);
    }

    public final void enforceAccessPermission() {
        getContext()
                .enforceCallingOrSelfPermission(
                        "android.permission.HDMI_CEC", "HdmiControlService");
    }

    public AbsoluteVolumeChangedListener getAbsoluteVolumeChangedListener() {
        return this.mAbsoluteVolumeChangedListener;
    }

    public final List getAllCecLocalDevices() {
        assertRunOnServiceThread();
        return this.mHdmiCecNetwork.getLocalDeviceList();
    }

    public HdmiCecAtomWriter getAtomWriter() {
        return this.mAtomWriter;
    }

    public final List getAvbCapableAudioOutputDevices() {
        return tv() != null
                ? TV_AVB_AUDIO_OUTPUT_DEVICES
                : playback() != null ? PLAYBACK_AVB_AUDIO_OUTPUT_DEVICES : Collections.emptyList();
    }

    public List getCecDeviceTypes() {
        return HdmiProperties.cec_device_types();
    }

    public final List getCecLocalDeviceTypes() {
        ArrayList arrayList = new ArrayList(this.mCecLocalDevices);
        if (this.mHdmiCecConfig.getIntValue("soundbar_mode") == 1
                && !arrayList.contains(5)
                && isArcSupported()
                && this.mSoundbarModeFeatureFlagEnabled) {
            arrayList.add(5);
        }
        return arrayList;
    }

    public int getCecVersion() {
        return this.mCecVersion;
    }

    public List getDeviceTypes() {
        return HdmiProperties.device_type();
    }

    public final int getDeviceVolumeBehavior(AudioDeviceAttributes audioDeviceAttributes) {
        if (AVB_AUDIO_OUTPUT_DEVICES.contains(audioDeviceAttributes)) {
            synchronized (this.mLock) {
                try {
                    if (this.mAudioDeviceVolumeBehaviors.containsKey(audioDeviceAttributes)) {
                        return ((Integer)
                                        this.mAudioDeviceVolumeBehaviors.get(audioDeviceAttributes))
                                .intValue();
                    }
                } finally {
                }
            }
        }
        return ((DefaultAudioManagerWrapper) this.mAudioManager)
                .mAudioManager.getDeviceVolumeBehavior(audioDeviceAttributes);
    }

    public HdmiEarcLocalDevice getEarcLocalDevice() {
        assertRunOnServiceThread();
        return this.mEarcLocalDevice;
    }

    public HdmiCecConfig getHdmiCecConfig() {
        return this.mHdmiCecConfig;
    }

    public final int getHdmiCecVolumeControl() {
        int i;
        synchronized (this.mLock) {
            i = this.mHdmiCecVolumeControl;
        }
        return i;
    }

    public int getInitialPowerStatus() {
        return 3;
    }

    public Looper getIoLooper() {
        return this.mIoLooper;
    }

    public final HdmiCecLocalDevice.ActiveSource getLocalActiveSource() {
        HdmiCecLocalDevice.ActiveSource activeSource;
        synchronized (this.mLock) {
            activeSource = this.mActiveSource;
        }
        return activeSource;
    }

    public final List getPortInfo() {
        List list;
        synchronized (this.mLock) {
            list = this.mHdmiCecNetwork.mPortInfo;
        }
        return list;
    }

    public final int getVendorId() {
        HdmiCecController hdmiCecController = this.mCecController;
        hdmiCecController.assertRunOnServiceThread();
        return hdmiCecController.mNativeWrapperImpl.nativeGetVendorId();
    }

    public int handleCecCommand(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int i = hdmiCecMessage.mValidationResult;
        int i2 = 3;
        if (i == 3 || i == 5 || !verifyPhysicalAddresses(hdmiCecMessage)) {
            return 3;
        }
        if (i == 0) {
            Iterator it = ((ArrayList) getAllCecLocalDevices()).iterator();
            while (true) {
                boolean hasNext = it.hasNext();
                int i3 = hdmiCecMessage.mSource;
                if (!hasNext) {
                    HdmiCecNetwork hdmiCecNetwork = this.mHdmiCecNetwork;
                    hdmiCecNetwork.assertRunOnServiceThread();
                    if (hdmiCecNetwork.getCecDeviceInfo(i3) == null) {
                        HdmiDeviceInfo.Builder displayName =
                                HdmiDeviceInfo.cecDeviceBuilder()
                                        .setLogicalAddress(i3)
                                        .setDisplayName(
                                                HdmiUtils.isValidAddress(i3)
                                                        ? HdmiUtils.DEFAULT_NAMES[i3]
                                                        : "");
                        switch (i3) {
                            case 0:
                                i2 = 0;
                                break;
                            case 1:
                            case 2:
                            case 9:
                                i2 = 1;
                                break;
                            case 3:
                            case 6:
                            case 7:
                            case 10:
                                break;
                            case 4:
                            case 8:
                            case 11:
                                i2 = 4;
                                break;
                            case 5:
                                i2 = 5;
                                break;
                            default:
                                i2 = 2;
                                break;
                        }
                        hdmiCecNetwork.addCecDevice(displayName.setDeviceType(i2).build());
                    }
                    boolean z = hdmiCecMessage instanceof ReportFeaturesMessage;
                    HdmiControlService hdmiControlService = hdmiCecNetwork.mHdmiControlService;
                    if (z) {
                        ReportFeaturesMessage reportFeaturesMessage =
                                (ReportFeaturesMessage) hdmiCecMessage;
                        hdmiCecNetwork.assertRunOnServiceThread();
                        hdmiCecNetwork.updateCecDevice(
                                hdmiCecNetwork
                                        .getCecDeviceInfo(reportFeaturesMessage.mSource)
                                        .toBuilder()
                                        .setCecVersion(reportFeaturesMessage.mCecVersion)
                                        .updateDeviceFeatures(reportFeaturesMessage.mDeviceFeatures)
                                        .build());
                        hdmiControlService.checkAndUpdateAbsoluteVolumeBehavior();
                    }
                    int i4 = hdmiCecMessage.mOpcode;
                    byte[] bArr = hdmiCecMessage.mParams;
                    if (i4 == 0) {
                        hdmiCecNetwork.assertRunOnServiceThread();
                        if (bArr.length >= 2) {
                            int i5 = bArr[0] & 255;
                            int i6 = bArr[1] & 255;
                            if (i5 == 115) {
                                int i7 = i6 != 0 ? 2 : 0;
                                HdmiDeviceInfo cecDeviceInfo = hdmiCecNetwork.getCecDeviceInfo(i3);
                                hdmiCecNetwork.updateCecDevice(
                                        cecDeviceInfo.toBuilder()
                                                .updateDeviceFeatures(
                                                        cecDeviceInfo
                                                                .getDeviceFeatures()
                                                                .toBuilder()
                                                                .setSetAudioVolumeLevelSupport(i7)
                                                                .build())
                                                .build());
                                hdmiControlService.checkAndUpdateAbsoluteVolumeBehavior();
                            }
                        }
                    } else if (i4 == 71) {
                        hdmiCecNetwork.assertRunOnServiceThread();
                        HdmiDeviceInfo cecDeviceInfo2 = hdmiCecNetwork.getCecDeviceInfo(i3);
                        if (cecDeviceInfo2 == null) {
                            Slog.i(
                                    "HdmiCecNetwork",
                                    "No source device info for <Set Osd Name>." + hdmiCecMessage);
                        } else {
                            try {
                                String str = new String(bArr, "US-ASCII");
                                if (cecDeviceInfo2.getDisplayName() == null
                                        || !cecDeviceInfo2.getDisplayName().equals(str)) {
                                    Slog.d(
                                            "HdmiCecNetwork",
                                            "Updating device OSD name from "
                                                    + cecDeviceInfo2.getDisplayName()
                                                    + " to "
                                                    + str);
                                    hdmiCecNetwork.updateCecDevice(
                                            cecDeviceInfo2.toBuilder().setDisplayName(str).build());
                                } else {
                                    Slog.d(
                                            "HdmiCecNetwork",
                                            "Ignore incoming <Set Osd Name> having same osd name:"
                                                    + hdmiCecMessage);
                                }
                            } catch (UnsupportedEncodingException e) {
                                Slog.e(
                                        "HdmiCecNetwork",
                                        "Invalid <Set Osd Name> request:" + hdmiCecMessage,
                                        e);
                            }
                        }
                    } else if (i4 == 132) {
                        hdmiCecNetwork.assertRunOnServiceThread();
                        int twoBytesToInt = HdmiUtils.twoBytesToInt(bArr);
                        byte b = bArr[2];
                        if (!hdmiCecNetwork.updateCecSwitchInfo(i3, b, twoBytesToInt)) {
                            HdmiDeviceInfo cecDeviceInfo3 = hdmiCecNetwork.getCecDeviceInfo(i3);
                            if (cecDeviceInfo3 == null) {
                                Slog.i(
                                        "HdmiCecNetwork",
                                        "Unknown source device info for <Report Physical Address> "
                                                + hdmiCecMessage);
                            } else {
                                hdmiCecNetwork.updateCecDevice(
                                        cecDeviceInfo3.toBuilder()
                                                .setPhysicalAddress(twoBytesToInt)
                                                .setPortId(
                                                        hdmiCecNetwork.physicalAddressToPortId(
                                                                twoBytesToInt))
                                                .setDeviceType(b)
                                                .build());
                            }
                        }
                    } else if (i4 == 135) {
                        hdmiCecNetwork.assertRunOnServiceThread();
                        int threeBytesToInt = HdmiUtils.threeBytesToInt(bArr);
                        HdmiDeviceInfo cecDeviceInfo4 = hdmiCecNetwork.getCecDeviceInfo(i3);
                        if (cecDeviceInfo4 == null) {
                            Slog.i(
                                    "HdmiCecNetwork",
                                    "Unknown source device info for <Device Vendor ID> "
                                            + hdmiCecMessage);
                        } else {
                            hdmiCecNetwork.updateCecDevice(
                                    cecDeviceInfo4.toBuilder()
                                            .setVendorId(threeBytesToInt)
                                            .build());
                        }
                    } else if (i4 == 144) {
                        hdmiCecNetwork.assertRunOnServiceThread();
                        hdmiCecNetwork.updateDevicePowerStatus(i3, bArr[0] & 255);
                        if (hdmiCecMessage.mDestination == 15) {
                            hdmiCecNetwork.updateDeviceCecVersion(i3, 6);
                        }
                    } else if (i4 == 158) {
                        hdmiCecNetwork.assertRunOnServiceThread();
                        hdmiCecNetwork.updateDeviceCecVersion(i3, Byte.toUnsignedInt(bArr[0]));
                    }
                    int dispatchMessageToLocalDevice = dispatchMessageToLocalDevice(hdmiCecMessage);
                    if (!this.mAddressAllocated) {
                        CecMessageBuffer cecMessageBuffer = this.mCecMessageBuffer;
                        cecMessageBuffer.getClass();
                        if (i4 == 4 || i4 == 13) {
                            if (!cecMessageBuffer.replaceMessageIfBuffered(4, hdmiCecMessage)
                                    && !cecMessageBuffer.replaceMessageIfBuffered(
                                            13, hdmiCecMessage)) {
                                ((ArrayList) cecMessageBuffer.mBuffer).add(hdmiCecMessage);
                            }
                        } else if (i4 != 112) {
                            if (i4 != 128) {
                                if (i4 != 130) {
                                    if (i4 == 134) {
                                        if (!cecMessageBuffer.replaceMessageIfBuffered(
                                                134, hdmiCecMessage)) {
                                            ((ArrayList) cecMessageBuffer.mBuffer)
                                                    .add(hdmiCecMessage);
                                        }
                                    }
                                } else if (!cecMessageBuffer.replaceMessageIfBuffered(
                                        130, hdmiCecMessage)) {
                                    ((ArrayList) cecMessageBuffer.mBuffer).add(hdmiCecMessage);
                                }
                            } else if (!cecMessageBuffer.replaceMessageIfBuffered(
                                    128, hdmiCecMessage)) {
                                ((ArrayList) cecMessageBuffer.mBuffer).add(hdmiCecMessage);
                            }
                        } else if (!cecMessageBuffer.replaceMessageIfBuffered(
                                112, hdmiCecMessage)) {
                            ((ArrayList) cecMessageBuffer.mBuffer).add(hdmiCecMessage);
                        }
                        return -1;
                    }
                    return dispatchMessageToLocalDevice;
                }
                if (i3 == ((HdmiCecLocalDevice) it.next()).getDeviceInfo().getLogicalAddress()
                        && i3 != 15) {
                    HdmiLogger.warning(
                            "Unexpected source: message sent from device itself, " + hdmiCecMessage,
                            new Object[0]);
                    break;
                }
            }
        }
        return -1;
    }

    public void initService() {
        final int i = 3;
        final int i2 = 2;
        final int i3 = 0;
        final int i4 = 1;
        if (this.mIoLooper == null) {
            this.mIoThread.start();
            this.mIoLooper = this.mIoThread.getLooper();
        }
        if (this.mPowerStatusController == null) {
            this.mPowerStatusController = new HdmiCecPowerStatusController(this);
        }
        this.mPowerStatusController.setPowerStatus(getInitialPowerStatus(), true);
        synchronized (this.mLock) {
            this.mProhibitMode = false;
        }
        this.mHdmiControlEnabled = this.mHdmiCecConfig.getIntValue("hdmi_cec_enabled");
        this.mDeviceConfig.getClass();
        this.mSoundbarModeFeatureFlagEnabled =
                DeviceConfig.getBoolean("hdmi_control", "enable_soundbar_mode", true);
        this.mDeviceConfig.getClass();
        this.mEarcTxFeatureFlagEnabled =
                DeviceConfig.getBoolean("hdmi_control", "enable_earc_tx", true);
        this.mDeviceConfig.getClass();
        this.mTransitionFromArcToEarcTxEnabled =
                DeviceConfig.getBoolean("hdmi_control", "transition_arc_to_earc_tx", true);
        this.mDeviceConfig.getClass();
        this.mNumericSoundbarVolumeUiOnTvFeatureFlagEnabled =
                DeviceConfig.getBoolean(
                        "hdmi_control", "enable_numeric_soundbar_volume_ui_on_tv", true);
        synchronized (this.mLock) {
            try {
                this.mEarcEnabled = this.mHdmiCecConfig.getIntValue("earc_enabled") == 1;
                if (isTvDevice()) {
                    this.mEarcEnabled &= this.mEarcTxFeatureFlagEnabled;
                }
            } finally {
            }
        }
        setHdmiCecVolumeControlEnabledInternal(
                getHdmiCecConfig().getIntValue("volume_control_enabled"));
        this.mMhlInputChangeEnabled = readBooleanSetting("mhl_input_switching_enabled", true);
        if (this.mCecMessageBuffer == null) {
            CecMessageBuffer cecMessageBuffer = new CecMessageBuffer();
            cecMessageBuffer.mBuffer = new ArrayList();
            cecMessageBuffer.mHdmiControlService = this;
            this.mCecMessageBuffer = cecMessageBuffer;
        }
        if (this.mCecController == null) {
            HdmiCecAtomWriter atomWriter = getAtomWriter();
            byte[] bArr = HdmiCecController.EMPTY_BODY;
            HdmiCecController createWithNativeWrapper =
                    HdmiCecController.createWithNativeWrapper(
                            this, new HdmiCecController.NativeWrapperImplAidl(), atomWriter);
            if (createWithNativeWrapper == null) {
                HdmiLogger.warning(
                        "Unable to use CEC and HDMI Connection AIDL HALs", new Object[0]);
                createWithNativeWrapper =
                        HdmiCecController.createWithNativeWrapper(
                                this, new HdmiCecController.NativeWrapperImpl11(), atomWriter);
                if (createWithNativeWrapper == null) {
                    HdmiLogger.warning("Unable to use cec@1.1", new Object[0]);
                    createWithNativeWrapper =
                            HdmiCecController.createWithNativeWrapper(
                                    this, new HdmiCecController.NativeWrapperImpl(), atomWriter);
                }
            }
            this.mCecController = createWithNativeWrapper;
        }
        if (this.mCecController == null) {
            Slog.i("HdmiControlService", "Device does not support HDMI-CEC.");
            return;
        }
        if (this.mMhlController == null) {
            this.mMhlController = new HdmiMhlControllerStub();
        }
        this.mMhlController.getClass();
        Slog.i("HdmiControlService", "Device does not support MHL-control.");
        if (this.mEarcController == null) {
            HdmiEarcController.EarcNativeWrapperImpl earcNativeWrapperImpl =
                    new HdmiEarcController.EarcNativeWrapperImpl();
            HdmiEarcController hdmiEarcController =
                    new HdmiEarcController(this, earcNativeWrapperImpl);
            if (earcNativeWrapperImpl.connectToHal()) {
                hdmiEarcController.mControlHandler = new Handler(this.mHandler.getLooper());
                earcNativeWrapperImpl.nativeSetCallback(hdmiEarcController.new EarcAidlCallback());
            } else {
                HdmiLogger.warning("Could not connect to eARC AIDL HAL.", new Object[0]);
                hdmiEarcController = null;
            }
            this.mEarcController = hdmiEarcController;
        }
        if (this.mEarcController == null) {
            Slog.i("HdmiControlService", "Device does not support eARC.");
        }
        this.mHdmiCecNetwork = new HdmiCecNetwork(this, this.mCecController, this.mMhlController);
        if (isCecControlEnabled()) {
            initializeCec(1);
        } else {
            this.mCecController.enableCec(false);
        }
        synchronized (this.mLock) {
            this.mMhlDevices = Collections.emptyList();
        }
        this.mHdmiCecNetwork.initPortInfo();
        List portInfo = getPortInfo();
        synchronized (this.mLock) {
            try {
                this.mEarcSupported = false;
                Iterator it = portInfo.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    HdmiPortInfo hdmiPortInfo = (HdmiPortInfo) it.next();
                    boolean isEarcSupported = hdmiPortInfo.isEarcSupported();
                    if (isEarcSupported && this.mEarcSupported) {
                        Slog.e("HdmiControlService", "HDMI eARC supported on more than 1 port.");
                        this.mEarcSupported = false;
                        this.mEarcPortId = -1;
                        break;
                    } else if (isEarcSupported) {
                        this.mEarcPortId = hdmiPortInfo.getId();
                        this.mEarcSupported = isEarcSupported;
                    }
                }
                this.mEarcSupported &= this.mEarcController != null;
            } finally {
            }
        }
        if (isEarcSupported()) {
            if (isEarcEnabled()) {
                initializeEarc(1);
            } else {
                setEarcEnabledInHal(false, false);
            }
        }
        this.mHdmiCecConfig.registerChangeListener(
                "hdmi_cec_enabled", new AnonymousClass2(this, i3), this.mServiceThreadExecutor);
        this.mHdmiCecConfig.registerChangeListener(
                "hdmi_cec_version", new AnonymousClass2(this, 8), this.mServiceThreadExecutor);
        this.mHdmiCecConfig.registerChangeListener(
                "routing_control", new AnonymousClass2(this, 9), this.mServiceThreadExecutor);
        this.mHdmiCecConfig.registerChangeListener(
                "system_audio_control", new AnonymousClass2(this, 10), this.mServiceThreadExecutor);
        this.mHdmiCecConfig.registerChangeListener(
                "volume_control_enabled",
                new AnonymousClass2(this, 11),
                this.mServiceThreadExecutor);
        this.mHdmiCecConfig.registerChangeListener(
                "tv_wake_on_one_touch_play",
                new AnonymousClass2(this, 12),
                this.mServiceThreadExecutor);
        this.mHdmiCecConfig.registerChangeListener(
                "rc_profile_tv", new AnonymousClass2(this, 13), this.mServiceThreadExecutor);
        this.mHdmiCecConfig.registerChangeListener(
                "rc_profile_source_handles_root_menu",
                new AnonymousClass2(this, 14),
                this.mServiceThreadExecutor);
        this.mHdmiCecConfig.registerChangeListener(
                "rc_profile_source_handles_setup_menu",
                new AnonymousClass2(this, i4),
                this.mServiceThreadExecutor);
        this.mHdmiCecConfig.registerChangeListener(
                "rc_profile_source_handles_contents_menu",
                new AnonymousClass2(this, i2),
                this.mServiceThreadExecutor);
        this.mHdmiCecConfig.registerChangeListener(
                "rc_profile_source_handles_top_menu",
                new AnonymousClass2(this, i),
                this.mServiceThreadExecutor);
        this.mHdmiCecConfig.registerChangeListener(
                "rc_profile_source_handles_media_context_sensitive_menu",
                new AnonymousClass2(this, 4),
                this.mServiceThreadExecutor);
        if (isTvDevice()) {
            DeviceConfigWrapper deviceConfigWrapper = this.mDeviceConfig;
            Executor mainExecutor = getContext().getMainExecutor();
            DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener =
                    new DeviceConfig.OnPropertiesChangedListener(
                            this) { // from class: com.android.server.hdmi.HdmiControlService.14
                        public final /* synthetic */ HdmiControlService this$0;

                        {
                            this.this$0 = this;
                        }

                        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                            switch (i3) {
                                case 0:
                                    this.this$0.mEarcTxFeatureFlagEnabled =
                                            properties.getBoolean("enable_earc_tx", true);
                                    boolean z =
                                            this.this$0.mHdmiCecConfig.getIntValue("earc_enabled")
                                                    == 1;
                                    HdmiControlService hdmiControlService = this.this$0;
                                    hdmiControlService.setEarcEnabled(
                                            (z && hdmiControlService.mEarcTxFeatureFlagEnabled)
                                                    ? 1
                                                    : 0);
                                    break;
                                case 1:
                                    this.this$0.mSoundbarModeFeatureFlagEnabled =
                                            properties.getBoolean("enable_soundbar_mode", true);
                                    boolean z2 =
                                            this.this$0.mHdmiCecConfig.getIntValue("soundbar_mode")
                                                    == 1;
                                    HdmiControlService hdmiControlService2 = this.this$0;
                                    hdmiControlService2.setSoundbarMode(
                                            (z2
                                                            && hdmiControlService2
                                                                    .mSoundbarModeFeatureFlagEnabled)
                                                    ? 1
                                                    : 0);
                                    break;
                                case 2:
                                    this.this$0.mTransitionFromArcToEarcTxEnabled =
                                            properties.getBoolean(
                                                    "transition_arc_to_earc_tx", true);
                                    break;
                                default:
                                    this.this$0.mNumericSoundbarVolumeUiOnTvFeatureFlagEnabled =
                                            properties.getBoolean(
                                                    "enable_numeric_soundbar_volume_ui_on_tv",
                                                    true);
                                    this.this$0.checkAndUpdateAbsoluteVolumeBehavior();
                                    break;
                            }
                        }
                    };
            deviceConfigWrapper.getClass();
            DeviceConfig.addOnPropertiesChangedListener(
                    "hdmi_control", mainExecutor, onPropertiesChangedListener);
        }
        this.mHdmiCecConfig.registerChangeListener(
                "earc_enabled", new AnonymousClass2(this, 5), this.mServiceThreadExecutor);
        DeviceConfigWrapper deviceConfigWrapper2 = this.mDeviceConfig;
        Executor mainExecutor2 = getContext().getMainExecutor();
        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener2 =
                new DeviceConfig.OnPropertiesChangedListener(
                        this) { // from class: com.android.server.hdmi.HdmiControlService.14
                    public final /* synthetic */ HdmiControlService this$0;

                    {
                        this.this$0 = this;
                    }

                    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                        switch (i4) {
                            case 0:
                                this.this$0.mEarcTxFeatureFlagEnabled =
                                        properties.getBoolean("enable_earc_tx", true);
                                boolean z =
                                        this.this$0.mHdmiCecConfig.getIntValue("earc_enabled") == 1;
                                HdmiControlService hdmiControlService = this.this$0;
                                hdmiControlService.setEarcEnabled(
                                        (z && hdmiControlService.mEarcTxFeatureFlagEnabled)
                                                ? 1
                                                : 0);
                                break;
                            case 1:
                                this.this$0.mSoundbarModeFeatureFlagEnabled =
                                        properties.getBoolean("enable_soundbar_mode", true);
                                boolean z2 =
                                        this.this$0.mHdmiCecConfig.getIntValue("soundbar_mode")
                                                == 1;
                                HdmiControlService hdmiControlService2 = this.this$0;
                                hdmiControlService2.setSoundbarMode(
                                        (z2 && hdmiControlService2.mSoundbarModeFeatureFlagEnabled)
                                                ? 1
                                                : 0);
                                break;
                            case 2:
                                this.this$0.mTransitionFromArcToEarcTxEnabled =
                                        properties.getBoolean("transition_arc_to_earc_tx", true);
                                break;
                            default:
                                this.this$0.mNumericSoundbarVolumeUiOnTvFeatureFlagEnabled =
                                        properties.getBoolean(
                                                "enable_numeric_soundbar_volume_ui_on_tv", true);
                                this.this$0.checkAndUpdateAbsoluteVolumeBehavior();
                                break;
                        }
                    }
                };
        deviceConfigWrapper2.getClass();
        DeviceConfig.addOnPropertiesChangedListener(
                "hdmi_control", mainExecutor2, onPropertiesChangedListener2);
        this.mHdmiCecConfig.registerChangeListener(
                "soundbar_mode", new AnonymousClass2(this, 6), this.mServiceThreadExecutor);
        DeviceConfigWrapper deviceConfigWrapper3 = this.mDeviceConfig;
        Executor mainExecutor3 = getContext().getMainExecutor();
        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener3 =
                new DeviceConfig.OnPropertiesChangedListener(
                        this) { // from class: com.android.server.hdmi.HdmiControlService.14
                    public final /* synthetic */ HdmiControlService this$0;

                    {
                        this.this$0 = this;
                    }

                    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                        switch (i2) {
                            case 0:
                                this.this$0.mEarcTxFeatureFlagEnabled =
                                        properties.getBoolean("enable_earc_tx", true);
                                boolean z =
                                        this.this$0.mHdmiCecConfig.getIntValue("earc_enabled") == 1;
                                HdmiControlService hdmiControlService = this.this$0;
                                hdmiControlService.setEarcEnabled(
                                        (z && hdmiControlService.mEarcTxFeatureFlagEnabled)
                                                ? 1
                                                : 0);
                                break;
                            case 1:
                                this.this$0.mSoundbarModeFeatureFlagEnabled =
                                        properties.getBoolean("enable_soundbar_mode", true);
                                boolean z2 =
                                        this.this$0.mHdmiCecConfig.getIntValue("soundbar_mode")
                                                == 1;
                                HdmiControlService hdmiControlService2 = this.this$0;
                                hdmiControlService2.setSoundbarMode(
                                        (z2 && hdmiControlService2.mSoundbarModeFeatureFlagEnabled)
                                                ? 1
                                                : 0);
                                break;
                            case 2:
                                this.this$0.mTransitionFromArcToEarcTxEnabled =
                                        properties.getBoolean("transition_arc_to_earc_tx", true);
                                break;
                            default:
                                this.this$0.mNumericSoundbarVolumeUiOnTvFeatureFlagEnabled =
                                        properties.getBoolean(
                                                "enable_numeric_soundbar_volume_ui_on_tv", true);
                                this.this$0.checkAndUpdateAbsoluteVolumeBehavior();
                                break;
                        }
                    }
                };
        deviceConfigWrapper3.getClass();
        DeviceConfig.addOnPropertiesChangedListener(
                "hdmi_control", mainExecutor3, onPropertiesChangedListener3);
        DeviceConfigWrapper deviceConfigWrapper4 = this.mDeviceConfig;
        Executor mainExecutor4 = getContext().getMainExecutor();
        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener4 =
                new DeviceConfig.OnPropertiesChangedListener(
                        this) { // from class: com.android.server.hdmi.HdmiControlService.14
                    public final /* synthetic */ HdmiControlService this$0;

                    {
                        this.this$0 = this;
                    }

                    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                        switch (i) {
                            case 0:
                                this.this$0.mEarcTxFeatureFlagEnabled =
                                        properties.getBoolean("enable_earc_tx", true);
                                boolean z =
                                        this.this$0.mHdmiCecConfig.getIntValue("earc_enabled") == 1;
                                HdmiControlService hdmiControlService = this.this$0;
                                hdmiControlService.setEarcEnabled(
                                        (z && hdmiControlService.mEarcTxFeatureFlagEnabled)
                                                ? 1
                                                : 0);
                                break;
                            case 1:
                                this.this$0.mSoundbarModeFeatureFlagEnabled =
                                        properties.getBoolean("enable_soundbar_mode", true);
                                boolean z2 =
                                        this.this$0.mHdmiCecConfig.getIntValue("soundbar_mode")
                                                == 1;
                                HdmiControlService hdmiControlService2 = this.this$0;
                                hdmiControlService2.setSoundbarMode(
                                        (z2 && hdmiControlService2.mSoundbarModeFeatureFlagEnabled)
                                                ? 1
                                                : 0);
                                break;
                            case 2:
                                this.this$0.mTransitionFromArcToEarcTxEnabled =
                                        properties.getBoolean("transition_arc_to_earc_tx", true);
                                break;
                            default:
                                this.this$0.mNumericSoundbarVolumeUiOnTvFeatureFlagEnabled =
                                        properties.getBoolean(
                                                "enable_numeric_soundbar_volume_ui_on_tv", true);
                                this.this$0.checkAndUpdateAbsoluteVolumeBehavior();
                                break;
                        }
                    }
                };
        deviceConfigWrapper4.getClass();
        DeviceConfig.addOnPropertiesChangedListener(
                "hdmi_control", mainExecutor4, onPropertiesChangedListener4);
    }

    public final void initializeCec(int i) {
        this.mAddressAllocated = false;
        int intValue = getHdmiCecConfig().getIntValue("hdmi_cec_version");
        HdmiCecController hdmiCecController = this.mCecController;
        hdmiCecController.assertRunOnServiceThread();
        this.mCecVersion =
                Math.max(
                        5,
                        Math.min(
                                intValue, hdmiCecController.mNativeWrapperImpl.nativeGetVersion()));
        this.mCecController.enableSystemCecControl(true);
        HdmiCecController hdmiCecController2 = this.mCecController;
        String str = this.mMenuLanguage;
        hdmiCecController2.assertRunOnServiceThread();
        if (HdmiCecController.isLanguage(str)) {
            hdmiCecController2.mNativeWrapperImpl.nativeSetLanguage(str);
        }
        initializeCecLocalDevices(i);
    }

    public void initializeCecLocalDevices(int i) {
        assertRunOnServiceThread();
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) getCecLocalDeviceTypes()).iterator();
        while (it.hasNext()) {
            int intValue = ((Integer) it.next()).intValue();
            HdmiCecLocalDevice localDevice = this.mHdmiCecNetwork.getLocalDevice(intValue);
            if (localDevice == null) {
                localDevice = HdmiCecLocalDevice.create(this, intValue);
            }
            localDevice.assertRunOnServiceThread();
            localDevice.mPreferredAddress = localDevice.getPreferredAddress();
            HdmiCecLocalDevice.AnonymousClass1 anonymousClass1 = localDevice.mHandler;
            if (anonymousClass1.hasMessages(1)) {
                anonymousClass1.removeMessages(1);
                localDevice.handleDisableDeviceTimeout();
            }
            localDevice.mPendingActionClearedCallback = null;
            arrayList.add(localDevice);
        }
        this.mHdmiCecNetwork.clearDeviceList();
        allocateLogicalAddress(arrayList, i);
    }

    public final void initializeEarc(int i) {
        Slog.i("HdmiControlService", "eARC initialized, reason = " + i);
        initializeEarcLocalDevice(i);
        if (i == 6) {
            setEarcEnabledInHal(true, true);
        } else {
            setEarcEnabledInHal(true, false);
        }
    }

    public void initializeEarcLocalDevice(int i) {
        assertRunOnServiceThread();
        if (this.mEarcLocalDevice == null) {
            HdmiEarcLocalDeviceTx hdmiEarcLocalDeviceTx = new HdmiEarcLocalDeviceTx(this, 0);
            synchronized (hdmiEarcLocalDeviceTx.mLock) {
                hdmiEarcLocalDeviceTx.mEarcStatus = 1;
            }
            hdmiEarcLocalDeviceTx.mReportCapsHandler = new Handler(this.mHandler.getLooper());
            hdmiEarcLocalDeviceTx.mReportCapsRunnable =
                    hdmiEarcLocalDeviceTx.new ReportCapsRunnable();
            this.mEarcLocalDevice = hdmiEarcLocalDeviceTx;
        }
    }

    public final void invokeInputChangeListener(HdmiDeviceInfo hdmiDeviceInfo) {
        synchronized (this.mLock) {
            HdmiRecordListenerRecord hdmiRecordListenerRecord = this.mInputChangeListenerRecord;
            if (hdmiRecordListenerRecord != null) {
                try {
                    ((IHdmiInputChangeListener) hdmiRecordListenerRecord.mListener)
                            .onChanged(hdmiDeviceInfo);
                } catch (RemoteException e) {
                    Slog.w(
                            "HdmiControlService",
                            "Exception thrown by IHdmiInputChangeListener: " + e);
                }
            }
        }
    }

    public final void invokeVendorCommandListenersOnControlStateChanged(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mVendorCommandListenerRecords.isEmpty()) {
                    return;
                }
                Iterator it = this.mVendorCommandListenerRecords.iterator();
                while (it.hasNext()) {
                    try {
                        ((VendorCommandListenerRecord) it.next())
                                .mListener.onControlStateChanged(z, i);
                    } catch (RemoteException e) {
                        Slog.e(
                                "HdmiControlService",
                                "Failed to notify control-state-changed to vendor handler",
                                e);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0045, code lost:

       r3 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0046, code lost:

       android.util.Slog.e("HdmiControlService", "Failed to notify vendor command reception", r3);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean invokeVendorCommandListenersOnReceived(
            int r8, int r9, byte[] r10, boolean r11) {
        /*
            r7 = this;
            java.lang.Object r0 = r7.mLock
            monitor-enter(r0)
            java.util.ArrayList r1 = r7.mVendorCommandListenerRecords     // Catch: java.lang.Throwable -> Le
            boolean r1 = r1.isEmpty()     // Catch: java.lang.Throwable -> Le
            r2 = 0
            if (r1 == 0) goto L10
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Le
            return r2
        Le:
            r7 = move-exception
            goto L50
        L10:
            java.util.ArrayList r7 = r7.mVendorCommandListenerRecords     // Catch: java.lang.Throwable -> Le
            java.util.Iterator r7 = r7.iterator()     // Catch: java.lang.Throwable -> Le
            r1 = r2
        L17:
            boolean r3 = r7.hasNext()     // Catch: java.lang.Throwable -> Le
            if (r3 == 0) goto L4e
            java.lang.Object r3 = r7.next()     // Catch: java.lang.Throwable -> Le
            com.android.server.hdmi.HdmiControlService$VendorCommandListenerRecord r3 = (com.android.server.hdmi.HdmiControlService.VendorCommandListenerRecord) r3     // Catch: java.lang.Throwable -> Le
            r4 = 1
            if (r11 == 0) goto L3e
            r5 = r10[r2]     // Catch: java.lang.Throwable -> Le
            r5 = r5 & 255(0xff, float:3.57E-43)
            int r5 = r5 << 16
            r6 = r10[r4]     // Catch: java.lang.Throwable -> Le
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r6 = r6 << 8
            int r5 = r5 + r6
            r6 = 2
            r6 = r10[r6]     // Catch: java.lang.Throwable -> Le
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r5 = r5 + r6
            int r6 = r3.mVendorId     // Catch: java.lang.Throwable -> Le
            if (r6 == r5) goto L3e
            goto L17
        L3e:
            android.hardware.hdmi.IHdmiVendorCommandListener r3 = r3.mListener     // Catch: java.lang.Throwable -> Le android.os.RemoteException -> L45
            r3.onReceived(r8, r9, r10, r11)     // Catch: java.lang.Throwable -> Le android.os.RemoteException -> L45
            r1 = r4
            goto L17
        L45:
            r3 = move-exception
            java.lang.String r4 = "HdmiControlService"
            java.lang.String r5 = "Failed to notify vendor command reception"
            android.util.Slog.e(r4, r5, r3)     // Catch: java.lang.Throwable -> Le
            goto L17
        L4e:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Le
            return r1
        L50:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Le
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.hdmi.HdmiControlService.invokeVendorCommandListenersOnReceived(int,"
                    + " int, byte[], boolean):boolean");
    }

    public final boolean isAbsoluteVolumeBehaviorEnabled() {
        if (!isTvDevice() && !isPlaybackDevice()) {
            return false;
        }
        Iterator it = getAvbCapableAudioOutputDevices().iterator();
        while (it.hasNext()) {
            if (ABSOLUTE_VOLUME_BEHAVIORS.contains(
                    Integer.valueOf(getDeviceVolumeBehavior((AudioDeviceAttributes) it.next())))) {
                return true;
            }
        }
        return false;
    }

    public boolean isArcSupported() {
        return SystemProperties.getBoolean("persist.sys.hdmi.property_arc_support", true);
    }

    public final boolean isAudioSystemDevice() {
        return this.mCecLocalDevices.contains(5);
    }

    public final boolean isCecControlEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = true;
            if (this.mHdmiControlEnabled != 1) {
                z = false;
            }
        }
        return z;
    }

    public final boolean isEarcEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mEarcEnabled;
        }
        return z;
    }

    public boolean isEarcSupported() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mEarcSupported;
        }
        return z;
    }

    public final boolean isPlaybackDevice() {
        return this.mCecLocalDevices.contains(4);
    }

    public final boolean isPowerOnOrTransient() {
        assertRunOnServiceThread();
        int i = this.mPowerStatusController.mPowerStatus;
        return i == 0 || i == 2;
    }

    public final boolean isPowerStandbyOrTransient() {
        assertRunOnServiceThread();
        int i = this.mPowerStatusController.mPowerStatus;
        return i == 1 || i == 3;
    }

    public final boolean isProhibitMode() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mProhibitMode;
        }
        return z;
    }

    public final boolean isSystemAudioActivated() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSystemAudioActivated;
        }
        return z;
    }

    public final boolean isTvDevice() {
        return this.mCecLocalDevices.contains(0);
    }

    public final boolean isTvDeviceEnabled() {
        return isTvDevice() && tv() != null;
    }

    public void notifyAddressAllocated(ArrayList arrayList, int i) {
        assertRunOnServiceThread();
        if (arrayList == null || arrayList.isEmpty()) {
            Slog.w("HdmiControlService", "No local device to notify.");
            return;
        }
        CecMessageBuffer cecMessageBuffer = this.mCecMessageBuffer;
        cecMessageBuffer.getClass();
        ArrayList arrayList2 = new ArrayList(cecMessageBuffer.mBuffer);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            HdmiCecLocalDevice hdmiCecLocalDevice = (HdmiCecLocalDevice) it.next();
            int logicalAddress = hdmiCecLocalDevice.getDeviceInfo().getLogicalAddress();
            hdmiCecLocalDevice.assertRunOnServiceThread();
            hdmiCecLocalDevice.preprocessBufferedMessages(arrayList2);
            hdmiCecLocalDevice.mPreferredAddress = logicalAddress;
            HdmiDeviceInfo build =
                    hdmiCecLocalDevice.getDeviceInfo().toBuilder()
                            .setDeviceFeatures(hdmiCecLocalDevice.computeDeviceFeatures())
                            .build();
            synchronized (hdmiCecLocalDevice.mLock) {
                hdmiCecLocalDevice.mDeviceInfo = build;
            }
            if (hdmiCecLocalDevice.mService.getCecVersion() >= 6) {
                hdmiCecLocalDevice.reportFeatures();
            }
            hdmiCecLocalDevice.onAddressAllocated(i);
            hdmiCecLocalDevice.setPreferredAddress(logicalAddress);
        }
        if (isTvDeviceEnabled()) {
            HdmiCecLocalDeviceTv tv = tv();
            SelectRequestBuffer selectRequestBuffer = this.mSelectRequestBuffer;
            tv.assertRunOnServiceThread();
            tv.mSelectRequestBuffer = selectRequestBuffer;
        }
    }

    public final void notifyAvbMuteChange(boolean z) {
        if (isAbsoluteVolumeBehaviorEnabled()) {
            Iterator it =
                    ((DefaultAudioManagerWrapper) this.mAudioManager)
                            .mAudioManager
                            .getDevicesForAttributes(STREAM_MUSIC_ATTRIBUTES)
                            .iterator();
            while (it.hasNext()) {
                if (getAvbCapableAudioOutputDevices().contains((AudioDeviceAttributes) it.next())) {
                    ((DefaultAudioManagerWrapper) this.mAudioManager)
                            .mAudioManager.adjustStreamVolume(
                                    3, z ? -100 : 100, isTvDevice() ? 8193 : 8192);
                    return;
                }
            }
        }
    }

    public final void notifyEarcStatusToAudioService(List list, boolean z) {
        AudioDeviceAttributes audioDeviceAttributes =
                new AudioDeviceAttributes(2, 29, "", "", new ArrayList(), list);
        if (!isCecControlEnabled()) {
            setSystemAudioActivated(true);
        }
        ((DefaultAudioManagerWrapper) this.mAudioManager)
                .mAudioManager.setWiredDeviceConnectionState(audioDeviceAttributes, z ? 1 : 0);
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i != 500) {
            if (i == 1000) {
                runOnServiceThread(new HdmiControlService$$ExternalSyntheticLambda1(this, 0));
                return;
            }
            return;
        }
        this.mDisplayManager = (DisplayManager) getContext().getSystemService(DisplayManager.class);
        this.mTvInputManager = (TvInputManager) getContext().getSystemService("tv_input");
        this.mPowerManager = new PowerManagerWrapper(getContext());
        PowerManagerInternalWrapper powerManagerInternalWrapper = new PowerManagerInternalWrapper();
        powerManagerInternalWrapper.mPowerManagerInternal =
                (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        this.mPowerManagerInternal = powerManagerInternalWrapper;
        if (this.mAudioManager == null) {
            this.mAudioManager = new DefaultAudioManagerWrapper(getContext());
        }
        this.mStreamMusicMaxVolume =
                ((DefaultAudioManagerWrapper) this.mAudioManager)
                        .mAudioManager.getStreamMaxVolume(3);
        if (this.mAudioDeviceVolumeManager == null) {
            this.mAudioDeviceVolumeManager =
                    new DefaultAudioDeviceVolumeManagerWrapper(getContext());
        }
        AudioDeviceVolumeManagerWrapper audioDeviceVolumeManagerWrapper =
                this.mAudioDeviceVolumeManager;
        ((DefaultAudioDeviceVolumeManagerWrapper) audioDeviceVolumeManagerWrapper)
                .mAudioDeviceVolumeManager.addOnDeviceVolumeBehaviorChangedListener(
                        this.mServiceThreadExecutor,
                        new AudioDeviceVolumeManager
                                .OnDeviceVolumeBehaviorChangedListener() { // from class:
                                                                           // com.android.server.hdmi.HdmiControlService$$ExternalSyntheticLambda0
                            public final void onDeviceVolumeBehaviorChanged(
                                    AudioDeviceAttributes audioDeviceAttributes, int i2) {
                                HdmiControlService.this.onDeviceVolumeBehaviorChanged(
                                        audioDeviceAttributes, i2);
                            }
                        });
    }

    public void onDeviceVolumeBehaviorChanged(AudioDeviceAttributes audioDeviceAttributes, int i) {
        assertRunOnServiceThread();
        if (AVB_AUDIO_OUTPUT_DEVICES.contains(audioDeviceAttributes)) {
            synchronized (this.mLock) {
                ((HashMap) this.mAudioDeviceVolumeBehaviors)
                        .put(audioDeviceAttributes, Integer.valueOf(i));
            }
            checkAndUpdateAbsoluteVolumeBehavior();
        }
    }

    public void onPendingActionsCleared(int i) {
        assertRunOnServiceThread();
        Slog.v("HdmiControlService", "onPendingActionsCleared");
        AnonymousClass27 anonymousClass27 =
                new AnonymousClass27(((ArrayList) getAllCecLocalDevices()).size(), new int[1]);
        HdmiCecPowerStatusController hdmiCecPowerStatusController = this.mPowerStatusController;
        if (hdmiCecPowerStatusController.mPowerStatus == 3) {
            hdmiCecPowerStatusController.setPowerStatus(1, true);
            Iterator it = ((ArrayList) this.mHdmiCecNetwork.getLocalDeviceList()).iterator();
            while (it.hasNext()) {
                ((HdmiCecLocalDevice) it.next())
                        .onStandby(this.mStandbyMessageReceived, i, anonymousClass27);
            }
        }
        this.mStandbyMessageReceived = false;
    }

    public void onStandby(int i) {
        boolean z;
        if (tv() != null) {
            if (this.mHdmiCecConfig.getIntValue("tv_send_standby_on_sleep") == 1) {
                z = true;
            }
            z = false;
        } else {
            if (playback() != null) {
                z = !this.mHdmiCecConfig.getStringValue("power_control_mode").equals("none");
            }
            z = false;
        }
        if (isCecControlEnabled() && isPowerOnOrTransient() && z) {
            acquireWakeLock();
        }
        this.mWakeUpMessageReceived = false;
        assertRunOnServiceThread();
        this.mPowerStatusController.setPowerStatus(3, false);
        invokeVendorCommandListenersOnControlStateChanged(3, false);
        List allCecLocalDevices = getAllCecLocalDevices();
        if (this.mStandbyMessageReceived || canGoToStandby()) {
            disableCecLocalDevices(new AnonymousClass27(allCecLocalDevices, i));
            checkAndUpdateAbsoluteVolumeBehavior();
        } else {
            this.mPowerStatusController.setPowerStatus(1, true);
            Iterator it = ((ArrayList) allCecLocalDevices).iterator();
            while (it.hasNext()) {
                ((HdmiCecLocalDevice) it.next()).onStandby(this.mStandbyMessageReceived, i, null);
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        initService();
        publishBinderService("hdmi_control", new BinderService());
        if (this.mCecController != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
            intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
            getContext().registerReceiver(this.mHdmiControlBroadcastReceiver, intentFilter);
            ContentResolver contentResolver = getContext().getContentResolver();
            String[] strArr = {
                "mhl_input_switching_enabled", "mhl_power_charge_enabled", "device_name"
            };
            for (int i = 0; i < 3; i++) {
                contentResolver.registerContentObserver(
                        Settings.Global.getUriFor(strArr[i]), false, this.mSettingsObserver, -1);
            }
        }
        this.mMhlController.getClass();
    }

    public void onWakeUp(int i) {
        int i2;
        int i3;
        assertRunOnServiceThread();
        int i4 = 2;
        this.mPowerStatusController.setPowerStatus(2, false);
        if (this.mCecController == null) {
            Slog.i("HdmiControlService", "Device does not support HDMI-CEC.");
        } else if (isCecControlEnabled()) {
            if (i == 0) {
                i3 = this.mWakeUpMessageReceived ? 3 : 2;
            } else {
                if (i != 1) {
                    FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0.m(
                            i, "wakeUpAction ", " not defined.", "HdmiControlService");
                    return;
                }
                i3 = 1;
            }
            initializeCec(i3);
        }
        if (isEarcSupported()) {
            if (isEarcEnabled()) {
                if (i != 0) {
                    if (i != 1) {
                        FileDescriptorWatcher$FileDescriptorLeakWatcher$$ExternalSyntheticOutline0
                                .m(i, "wakeUpAction ", " not defined.", "HdmiControlService");
                        return;
                    }
                    i4 = 1;
                }
                initializeEarc(i4);
            } else {
                setEarcEnabledInHal(false, false);
            }
        }
        if (!isTvDevice()) {
            if (isPlaybackDevice()) {
                HdmiCecAtomWriter atomWriter = getAtomWriter();
                boolean isArcSupported = isArcSupported();
                boolean z = this.mHdmiCecConfig.getIntValue("soundbar_mode") == 1;
                atomWriter.getClass();
                FrameworkStatsLog.write(
                        FrameworkStatsLog.HDMI_SOUNDBAR_MODE_STATUS_REPORTED, isArcSupported, z, 1);
                return;
            }
            return;
        }
        assertRunOnServiceThread();
        if (this.mEarcLocalDevice != null) {
            synchronized (this.mLock) {
                i2 = this.mEarcLocalDevice.mEarcStatus;
            }
        } else {
            i2 = -1;
        }
        HdmiCecAtomWriter atomWriter2 = getAtomWriter();
        boolean isEarcSupported = isEarcSupported();
        boolean isEarcEnabled = isEarcEnabled();
        atomWriter2.getClass();
        HdmiCecAtomWriter.earcStatusChanged(i2, i2, 1, isEarcSupported, isEarcEnabled);
    }

    public void oneTouchPlay(IHdmiControlCallback iHdmiControlCallback) {
        assertRunOnServiceThread();
        if (!this.mAddressAllocated) {
            this.mOtpCallbackPendingAddressAllocation = iHdmiControlCallback;
            Slog.d(
                    "HdmiControlService",
                    "Local device is under address allocation. Save OTP callback for later"
                        + " process.");
            return;
        }
        HdmiCecLocalDeviceSource playback = playback();
        if (playback == null) {
            playback = audioSystem();
        }
        if (playback != null) {
            playback.oneTouchPlay(iHdmiControlCallback);
        } else {
            Slog.w("HdmiControlService", "Local source device not available");
            invokeCallback(2, iHdmiControlCallback);
        }
    }

    public void pauseActiveMediaSessions() {
        Iterator<MediaController> it =
                ((MediaSessionManager) getContext().getSystemService(MediaSessionManager.class))
                        .getActiveSessions(null)
                        .iterator();
        while (it.hasNext()) {
            it.next().getTransportControls().pause();
        }
    }

    public final HdmiCecLocalDevicePlayback playback() {
        return (HdmiCecLocalDevicePlayback) this.mHdmiCecNetwork.getLocalDevice(4);
    }

    public final int portIdToPath(int i) {
        HdmiCecNetwork hdmiCecNetwork = this.mHdmiCecNetwork;
        if (i == 0) {
            return hdmiCecNetwork.getPhysicalAddress();
        }
        HdmiPortInfo portInfo = hdmiCecNetwork.getPortInfo(i);
        if (portInfo != null) {
            return portInfo.getAddress();
        }
        NandswapManager$$ExternalSyntheticOutline0.m(
                i, "Cannot find the port info: ", "HdmiCecNetwork");
        return GnssNative.GNSS_AIDING_TYPE_ALL;
    }

    public final void queryDisplayStatus(IHdmiControlCallback iHdmiControlCallback) {
        DevicePowerStatusAction devicePowerStatusAction;
        assertRunOnServiceThread();
        if (!this.mAddressAllocated) {
            this.mDisplayStatusCallback = iHdmiControlCallback;
            Slog.d(
                    "HdmiControlService",
                    "Local device is under address allocation. Queue display callback for later"
                        + " process.");
            return;
        }
        HdmiCecLocalDeviceSource playback = playback();
        if (playback == null) {
            playback = audioSystem();
        }
        if (playback == null) {
            Slog.w("HdmiControlService", "Local source device not available");
            invokeCallback(-1, iHdmiControlCallback);
            return;
        }
        playback.assertRunOnServiceThread();
        List actions = playback.getActions(DevicePowerStatusAction.class);
        if (!actions.isEmpty()) {
            Slog.i("HdmiCecLocalDeviceSource", "queryDisplayStatus already in progress");
            ((ArrayList) ((DevicePowerStatusAction) actions.get(0)).mCallbacks)
                    .add(iHdmiControlCallback);
            return;
        }
        if (iHdmiControlCallback == null) {
            Slog.e("DevicePowerStatusAction", "Wrong arguments");
            devicePowerStatusAction = null;
        } else {
            devicePowerStatusAction = new DevicePowerStatusAction(playback, iHdmiControlCallback);
        }
        if (devicePowerStatusAction != null) {
            playback.addAndStartAction(devicePowerStatusAction);
        } else {
            Slog.w("HdmiCecLocalDeviceSource", "Cannot initiate queryDisplayStatus");
            playback.invokeCallback(-1, iHdmiControlCallback);
        }
    }

    public boolean readBooleanSetting(String str, boolean z) {
        return Settings.Global.getInt(getContext().getContentResolver(), str, z ? 1 : 0) == 1;
    }

    public boolean readBooleanSystemProperty(String str, boolean z) {
        return SystemProperties.getBoolean(str, z);
    }

    public List readDeviceTypes() {
        List cecDeviceTypes = getCecDeviceTypes();
        if (!cecDeviceTypes.isEmpty()) {
            if (cecDeviceTypes.contains(null)) {
                Slog.w(
                        "HdmiControlService",
                        "Error parsing ro.hdmi.cec_device_types: "
                                + SystemProperties.get("ro.hdmi.cec_device_types"));
            }
            return (List)
                    cecDeviceTypes.stream()
                            .map(new HdmiControlService$$ExternalSyntheticLambda3())
                            .filter(new HdmiControlService$$ExternalSyntheticLambda4())
                            .collect(Collectors.toList());
        }
        List deviceTypes = getDeviceTypes();
        if (deviceTypes.contains(null)) {
            Slog.w(
                    "HdmiControlService",
                    "Error parsing ro.hdmi.device_type: "
                            + SystemProperties.get("ro.hdmi.device_type"));
        }
        return (List)
                deviceTypes.stream()
                        .filter(new HdmiControlService$$ExternalSyntheticLambda4())
                        .collect(Collectors.toList());
    }

    public int readIntSetting(String str, int i) {
        return Settings.Global.getInt(getContext().getContentResolver(), str, i);
    }

    public void releaseWakeLock() {
        PowerManagerWrapper.DefaultWakeLockWrapper defaultWakeLockWrapper = this.mWakeLock;
        if (defaultWakeLockWrapper != null) {
            try {
                if (defaultWakeLockWrapper.mWakeLock.isHeld()) {
                    this.mWakeLock.mWakeLock.release();
                }
            } catch (RuntimeException unused) {
                Slog.w("HdmiControlService", "Exception when releasing wake lock.");
            }
            this.mWakeLock = null;
        }
    }

    public void removeHdmiControlVolumeControlStatusChangeListener(
            IHdmiCecVolumeControlFeatureListener iHdmiCecVolumeControlFeatureListener) {
        this.mHdmiCecVolumeControlFeatureListenerRecords.unregister(
                iHdmiCecVolumeControlFeatureListener);
    }

    public final void runOnServiceThread(Runnable runnable) {
        this.mHandler.post(new WorkSourceUidPreservingRunnable(runnable));
    }

    public void sendBroadcastAsUser(Intent intent) {
        assertRunOnServiceThread();
        getContext().sendBroadcastAsUser(intent, UserHandle.ALL, "android.permission.HDMI_CEC");
    }

    public final void sendCecCommand(HdmiCecMessage hdmiCecMessage) {
        sendCecCommand(hdmiCecMessage, null);
    }

    public final void sendCecCommand(
            final HdmiCecMessage hdmiCecMessage, final SendMessageCallback sendMessageCallback) {
        int i = hdmiCecMessage.mOpcode;
        if (i != 4 && i != 13 && i != 128 && i != 130 && i != 134 && i != 157) {
            sendCecCommandWithoutRetries(hdmiCecMessage, sendMessageCallback);
            return;
        }
        if (isTvDeviceEnabled()) {
            tv().removeAction(RequestActiveSourceAction.class);
        }
        assertRunOnServiceThread();
        final HdmiCecLocalDevice hdmiCecLocalDevice =
                (HdmiCecLocalDevice) ((ArrayList) getAllCecLocalDevices()).get(0);
        if (hdmiCecLocalDevice != null) {
            sendCecCommandWithoutRetries(
                    hdmiCecMessage,
                    new SendMessageCallback() { // from class:
                                                // com.android.server.hdmi.HdmiControlService.22
                        @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                        public final void onSendCompleted(int i2) {
                            if (i2 != 0) {
                                HdmiCecMessage hdmiCecMessage2 = hdmiCecMessage;
                                SendMessageCallback sendMessageCallback2 = sendMessageCallback;
                                HdmiCecLocalDevice hdmiCecLocalDevice2 = HdmiCecLocalDevice.this;
                                hdmiCecLocalDevice2.addAndStartAction(
                                        new ResendCecCommandAction(
                                                hdmiCecLocalDevice2,
                                                hdmiCecMessage2,
                                                sendMessageCallback2));
                            }
                        }
                    });
        }
    }

    public final void sendCecCommandWithoutRetries(
            HdmiCecMessage hdmiCecMessage, SendMessageCallback sendMessageCallback) {
        assertRunOnServiceThread();
        if (hdmiCecMessage.mValidationResult == 0 && verifyPhysicalAddresses(hdmiCecMessage)) {
            HdmiCecController hdmiCecController = this.mCecController;
            hdmiCecController.assertRunOnServiceThread();
            ArrayList arrayList = new ArrayList();
            hdmiCecController.runOnIoThread(
                    hdmiCecController
                    .new AnonymousClass7(hdmiCecMessage, arrayList, sendMessageCallback));
            hdmiCecController.assertRunOnServiceThread();
            hdmiCecController.addEventToHistory(
                    new HdmiCecController.MessageHistoryRecord(false, hdmiCecMessage, arrayList));
            return;
        }
        HdmiLogger.error("Invalid message type:" + hdmiCecMessage, new Object[0]);
        if (sendMessageCallback != null) {
            sendMessageCallback.onSendCompleted(3);
        }
    }

    public final void setActiveSource(int i, int i2, String str) {
        synchronized (this.mLock) {
            HdmiCecLocalDevice.ActiveSource activeSource = this.mActiveSource;
            activeSource.logicalAddress = i;
            activeSource.physicalAddress = i2;
        }
        HdmiCecAtomWriter atomWriter = getAtomWriter();
        int pathRelationship =
                HdmiUtils.pathRelationship(this.mHdmiCecNetwork.getPhysicalAddress(), i2);
        atomWriter.getClass();
        FrameworkStatsLog.write(309, i, i2, pathRelationship);
        Iterator it = ((ArrayList) getAllCecLocalDevices()).iterator();
        while (it.hasNext()) {
            HdmiCecLocalDevice hdmiCecLocalDevice = (HdmiCecLocalDevice) it.next();
            HdmiCecLocalDevice.ActiveSourceHistoryRecord activeSourceHistoryRecord =
                    new HdmiCecLocalDevice.ActiveSourceHistoryRecord(
                            new HdmiCecLocalDevice.ActiveSource(i, i2),
                            i == hdmiCecLocalDevice.getDeviceInfo().getLogicalAddress()
                                    && i2 == this.mHdmiCecNetwork.getPhysicalAddress(),
                            str);
            if (!hdmiCecLocalDevice.mActiveSourceHistory.offer(activeSourceHistoryRecord)) {
                hdmiCecLocalDevice.mActiveSourceHistory.poll();
                hdmiCecLocalDevice.mActiveSourceHistory.offer(activeSourceHistoryRecord);
            }
        }
        runOnServiceThread(new HdmiControlService$$ExternalSyntheticLambda1(this, 1));
    }

    public final void setAndBroadcastActiveSource(int i, int i2, int i3, String str) {
        if (i2 == 4) {
            HdmiCecLocalDevicePlayback playback = playback();
            playback.assertRunOnServiceThread();
            playback.mService.sendBroadcastAsUser(
                    new Intent(
                            "android.hardware.hdmi.action.ON_ACTIVE_SOURCE_RECOVERED_DISMISS_UI"));
            playback.setActiveSource(playback.getDeviceInfo().getLogicalAddress(), i, str);
            playback.wakeUpIfActiveSource();
            playback.maySendActiveSource(i3);
            playback.mDelayedStandbyOnActiveSourceLostHandler.removeCallbacksAndMessages(null);
        }
        if (i2 == 5) {
            HdmiCecLocalDeviceAudioSystem audioSystem = audioSystem();
            if (playback() == null) {
                audioSystem.setActiveSource(
                        audioSystem.getDeviceInfo().getLogicalAddress(), i, str);
                audioSystem.wakeUpIfActiveSource();
                audioSystem.maySendActiveSource(i3);
            }
        }
    }

    public final void setAndBroadcastActiveSourceFromOneDeviceType(int i, int i2, String str) {
        HdmiCecLocalDevicePlayback playback = playback();
        HdmiCecLocalDeviceAudioSystem audioSystem = audioSystem();
        if (playback != null) {
            playback.setActiveSource(playback.getDeviceInfo().getLogicalAddress(), i2, str);
            playback.wakeUpIfActiveSource();
            playback.maySendActiveSource(i);
        } else if (audioSystem != null) {
            audioSystem.setActiveSource(audioSystem.getDeviceInfo().getLogicalAddress(), i2, str);
            audioSystem.wakeUpIfActiveSource();
            audioSystem.maySendActiveSource(i);
        }
    }

    public final void setAudioStatus(int i) {
        if (isTvDeviceEnabled()
                && tv().isSystemAudioActivated()
                && tv().isArcEstablished()
                && getHdmiCecVolumeControl() != 0) {
            DefaultAudioManagerWrapper defaultAudioManagerWrapper =
                    (DefaultAudioManagerWrapper) this.mAudioManager;
            if (defaultAudioManagerWrapper.mAudioManager.isStreamMute(3)) {
                defaultAudioManagerWrapper.mAudioManager.setStreamMute(3, false);
            }
            if (i < 0 || i > 100) {
                return;
            }
            HermesService$3$$ExternalSyntheticOutline0.m(i, "volume: ", "HdmiControlService");
            defaultAudioManagerWrapper.mAudioManager.setStreamVolume(
                    3,
                    i,
                    FrameworkStatsLog.HDMI_CEC_MESSAGE_REPORTED__USER_CONTROL_PRESSED_COMMAND__UP);
        }
    }

    public void setCecController(HdmiCecController hdmiCecController) {
        this.mCecController = hdmiCecController;
    }

    public void setCecMessageBuffer(CecMessageBuffer cecMessageBuffer) {
        this.mCecMessageBuffer = cecMessageBuffer;
    }

    public void setDeviceConfig(DeviceConfigWrapper deviceConfigWrapper) {
        this.mDeviceConfig = deviceConfigWrapper;
    }

    public void setEarcController(HdmiEarcController hdmiEarcController) {
        this.mEarcController = hdmiEarcController;
    }

    public void setEarcEnabled(int i) {
        assertRunOnServiceThread();
        synchronized (this.mLock) {
            try {
                this.mEarcEnabled = i == 1;
                if (!isEarcSupported()) {
                    Slog.i(
                            "HdmiControlService",
                            "Enabled/disabled eARC setting, but the hardware doesn´t support eARC."
                                + " This settings change doesn´t have an effect.");
                } else if (this.mEarcEnabled) {
                    initializeEarc(6);
                } else {
                    runOnServiceThread(new AnonymousClass30(this, 1));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setEarcEnabledInHal(final boolean z, boolean z2) {
        assertRunOnServiceThread();
        if (z2) {
            startArcAction(
                    false,
                    new IHdmiControlCallback
                            .Stub() { // from class: com.android.server.hdmi.HdmiControlService.35
                        public final void onComplete(int i) {
                            if (i != 0) {
                                DeviceIdleController$$ExternalSyntheticOutline0.m(
                                        i,
                                        "ARC termination before enabling eARC in the HAL failed"
                                            + " with result: ",
                                        "HdmiControlService");
                            }
                            HdmiEarcController hdmiEarcController =
                                    HdmiControlService.this.mEarcController;
                            boolean z3 = z;
                            hdmiEarcController.getClass();
                            if (Looper.myLooper()
                                    != hdmiEarcController.mControlHandler.getLooper()) {
                                throw new IllegalStateException("Should run on service thread.");
                            }
                            hdmiEarcController.mEarcNativeWrapperImpl.nativeSetEarcEnabled(z3);
                            HdmiControlService hdmiControlService = HdmiControlService.this;
                            HdmiCecController hdmiCecController = hdmiControlService.mCecController;
                            boolean z4 = z;
                            int i2 = hdmiControlService.mEarcPortId;
                            hdmiCecController.assertRunOnServiceThread();
                            HdmiLogger.debug(
                                    "setHpdSignalType: portId %b, signal %b",
                                    Integer.valueOf(i2), Integer.valueOf(z4 ? 1 : 0));
                            hdmiCecController.mNativeWrapperImpl.nativeSetHpdSignalType(
                                    z4 ? 1 : 0, i2);
                        }
                    });
            return;
        }
        HdmiEarcController hdmiEarcController = this.mEarcController;
        hdmiEarcController.getClass();
        if (Looper.myLooper() != hdmiEarcController.mControlHandler.getLooper()) {
            throw new IllegalStateException("Should run on service thread.");
        }
        hdmiEarcController.mEarcNativeWrapperImpl.nativeSetEarcEnabled(z);
        HdmiCecController hdmiCecController = this.mCecController;
        int i = this.mEarcPortId;
        hdmiCecController.assertRunOnServiceThread();
        HdmiLogger.debug(
                "setHpdSignalType: portId %b, signal %b",
                Integer.valueOf(i), Integer.valueOf(z ? 1 : 0));
        hdmiCecController.mNativeWrapperImpl.nativeSetHpdSignalType(z ? 1 : 0, i);
    }

    public void setEarcSupported(boolean z) {
        synchronized (this.mLock) {
            this.mEarcSupported = z;
        }
    }

    public void setHdmiCecConfig(HdmiCecConfig hdmiCecConfig) {
        this.mHdmiCecConfig = hdmiCecConfig;
    }

    public void setHdmiCecNetwork(HdmiCecNetwork hdmiCecNetwork) {
        this.mHdmiCecNetwork = hdmiCecNetwork;
    }

    public void setHdmiCecVolumeControlEnabledInternal(final int i) {
        this.mHdmiCecVolumeControl = i;
        assertRunOnServiceThread();
        synchronized (this.mLock) {
            this.mHdmiCecVolumeControlFeatureListenerRecords.broadcast(
                    new Consumer() { // from class:
                                     // com.android.server.hdmi.HdmiControlService$$ExternalSyntheticLambda5
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i2 = i;
                            try {
                                ((IHdmiCecVolumeControlFeatureListener) obj)
                                        .onHdmiCecVolumeControlFeature(i2);
                            } catch (RemoteException unused) {
                                NandswapManager$$ExternalSyntheticOutline0.m(
                                        i2,
                                        "Failed to report HdmiControlVolumeControlStatusChange: ",
                                        "HdmiControlService");
                            }
                        }
                    });
        }
        runOnServiceThread(new HdmiControlService$$ExternalSyntheticLambda1(this, 1));
    }

    public void setHdmiMhlController(HdmiMhlControllerStub hdmiMhlControllerStub) {
        this.mMhlController = hdmiMhlControllerStub;
    }

    public void setIoLooper(Looper looper) {
        this.mIoLooper = looper;
    }

    public void setPowerManager(PowerManagerWrapper powerManagerWrapper) {
        this.mPowerManager = powerManagerWrapper;
    }

    public void setPowerManagerInternal(PowerManagerInternalWrapper powerManagerInternalWrapper) {
        this.mPowerManagerInternal = powerManagerInternalWrapper;
    }

    public void setPowerStatus(int i) {
        assertRunOnServiceThread();
        this.mPowerStatusController.setPowerStatus(i, true);
    }

    public void setSoundbarMode(int i) {
        boolean z;
        boolean isArcSupported = isArcSupported();
        HdmiCecLocalDevicePlayback playback = playback();
        HdmiCecLocalDeviceAudioSystem audioSystem = audioSystem();
        HdmiCecAtomWriter atomWriter = getAtomWriter();
        boolean z2 = i == 1;
        atomWriter.getClass();
        FrameworkStatsLog.write(
                FrameworkStatsLog.HDMI_SOUNDBAR_MODE_STATUS_REPORTED, isArcSupported, z2, 2);
        if (playback == null) {
            Slog.w("HdmiControlService", "Device type not compatible to change soundbar mode.");
            return;
        }
        if (!isArcSupported) {
            Slog.w("HdmiControlService", "Device type doesn't support ARC.");
            return;
        }
        if (i != 0 || audioSystem == null) {
            z = false;
        } else {
            z = audioSystem.isArcEnabled();
            if (isSystemAudioActivated()) {
                audioSystem.terminateSystemAudioMode(null);
            }
            if (z) {
                if (audioSystem.hasAction(ArcTerminationActionFromAvr.class)) {
                    audioSystem.removeAction(ArcTerminationActionFromAvr.class);
                }
                audioSystem.addAndStartAction(
                        new ArcTerminationActionFromAvr(
                                audioSystem,
                                new IHdmiControlCallback
                                        .Stub() { // from class:
                                                  // com.android.server.hdmi.HdmiControlService.20
                                    public final void onComplete(int i2) {
                                        HdmiControlService hdmiControlService =
                                                HdmiControlService.this;
                                        hdmiControlService.mAddressAllocated = false;
                                        hdmiControlService.initializeCecLocalDevices(5);
                                    }
                                }));
            }
        }
        if (z) {
            return;
        }
        this.mAddressAllocated = false;
        initializeCecLocalDevices(5);
    }

    public final void setSystemAudioActivated(boolean z) {
        synchronized (this.mLock) {
            this.mSystemAudioActivated = z;
        }
        runOnServiceThread(new HdmiControlService$$ExternalSyntheticLambda1(this, 1));
    }

    public boolean shouldHandleTvPowerKey() {
        if (isTvDevice()
                || getHdmiCecConfig().getStringValue("power_control_mode").equals("none")
                || getHdmiCecConfig().getIntValue("hdmi_cec_enabled") != 1) {
            return false;
        }
        return this.mIsCecAvailable;
    }

    public final void standby() {
        assertRunOnServiceThread();
        if (canGoToStandby()) {
            this.mStandbyMessageReceived = true;
            PowerManagerWrapper powerManagerWrapper = this.mPowerManager;
            powerManagerWrapper.mPowerManager.goToSleep(SystemClock.uptimeMillis(), 5, 0);
        }
    }

    public final void startArcAction(boolean z, AnonymousClass35 anonymousClass35) {
        if (isTvDeviceEnabled()) {
            tv().startArcAction(z, anonymousClass35);
        } else {
            invokeCallback(6, anonymousClass35);
        }
    }

    public final void switchToFullVolumeBehavior() {
        Slog.d("HdmiControlService", "Switching to full volume behavior");
        if (playback() != null) {
            HdmiCecLocalDevicePlayback playback = playback();
            playback.assertRunOnServiceThread();
            playback.removeAction(AbsoluteVolumeAudioStatusAction.class);
        } else if (tv() != null) {
            HdmiCecLocalDeviceTv tv = tv();
            tv.assertRunOnServiceThread();
            tv.removeAction(AbsoluteVolumeAudioStatusAction.class);
        }
        for (AudioDeviceAttributes audioDeviceAttributes : getAvbCapableAudioOutputDevices()) {
            if (ABSOLUTE_VOLUME_BEHAVIORS.contains(
                    Integer.valueOf(getDeviceVolumeBehavior(audioDeviceAttributes)))) {
                ((DefaultAudioManagerWrapper) this.mAudioManager)
                        .mAudioManager.setDeviceVolumeBehavior(audioDeviceAttributes, 1);
            }
        }
    }

    public void toggleAndFollowTvPower() {
        assertRunOnServiceThread();
        HdmiCecLocalDevice playback = playback();
        if (playback == null) {
            playback = audioSystem();
        }
        if (playback == null) {
            Slog.w("HdmiControlService", "Local source device not available");
            return;
        }
        playback.assertRunOnServiceThread();
        HdmiControlService hdmiControlService = playback.mService;
        if (hdmiControlService.mPowerManager.mPowerManager.isInteractive()) {
            hdmiControlService.pauseActiveMediaSessions();
        } else {
            hdmiControlService.wakeUp();
        }
        hdmiControlService.queryDisplayStatus(
                new HdmiCecLocalDeviceSource.AnonymousClass1(0, playback));
    }

    public final HdmiCecLocalDeviceTv tv() {
        return (HdmiCecLocalDeviceTv) this.mHdmiCecNetwork.getLocalDevice(0);
    }

    public final boolean verifyPhysicalAddress(int i, byte[] bArr) {
        if (!isTvDevice()) {
            return true;
        }
        if (bArr.length < i + 2) {
            return false;
        }
        int twoBytesToInt = HdmiUtils.twoBytesToInt(i, bArr);
        return (twoBytesToInt != 65535
                        && twoBytesToInt == this.mHdmiCecNetwork.getPhysicalAddress())
                || this.mHdmiCecNetwork.physicalAddressToPortId(twoBytesToInt) != -1;
    }

    public final boolean verifyPhysicalAddresses(HdmiCecMessage hdmiCecMessage) {
        byte[] bArr = hdmiCecMessage.mParams;
        int i = hdmiCecMessage.mOpcode;
        if (i == 112) {
            return bArr.length == 0 || verifyPhysicalAddress(0, bArr);
        }
        if (i != 132 && i != 134 && i != 157) {
            if (i == 161 || i == 162) {
                if (bArr[7] != 5 || bArr.length - 8 < 2) {
                    return true;
                }
                return verifyPhysicalAddress(8, bArr);
            }
            switch (i) {
                case 128:
                    return verifyPhysicalAddress(0, bArr) && verifyPhysicalAddress(2, bArr);
                case 129:
                case 130:
                    break;
                default:
                    return true;
            }
        }
        return verifyPhysicalAddress(0, bArr);
    }

    public final void wakeUp() {
        assertRunOnServiceThread();
        this.mWakeUpMessageReceived = true;
        PowerManagerWrapper powerManagerWrapper = this.mPowerManager;
        powerManagerWrapper.mPowerManager.wakeUp(
                SystemClock.uptimeMillis(), 8, "android.server.hdmi:WAKE");
    }

    public void writeStringSystemProperty(String str, String str2) {
        SystemProperties.set(str, str2);
    }
}
