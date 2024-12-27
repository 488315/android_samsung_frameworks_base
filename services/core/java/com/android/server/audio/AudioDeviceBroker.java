package com.android.server.audio;

import android.app.ActivityManager;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.media.AudioRecordingConfiguration;
import android.media.AudioSystem;
import android.media.BluetoothProfileConnectionInfo;
import android.media.MediaMetrics;
import android.media.audio.Flags;
import android.media.audiopolicy.AudioProductStrategy;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.sysprop.BluetoothProperties;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.PrintWriterPrinter;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioDeviceBroker;
import com.android.server.audio.AudioDeviceInventory;
import com.android.server.audio.AudioService;
import com.android.server.input.KeyboardMetricsCollector;
import com.android.server.utils.EventLogger;
import com.android.server.vibrator.VibrationCustomSettings;
import com.android.server.vibrator.VibratorManagerService;
import com.google.android.collect.Sets;
import com.samsung.android.audio.Rune;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.server.audio.AudioExecutor;
import com.samsung.android.server.audio.DualA2dpVolumeManager;
import com.samsung.android.server.audio.MicModeManager;
import com.samsung.android.server.audio.MultiSoundManager;
import com.samsung.android.server.audio.utils.BtUtils;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AudioDeviceBroker {
    public static final Set MESSAGES_SAR_RCV_CONTROL;
    public static long sLastDeviceConnectMsgTime;
    public AudioDeviceInfo mActiveCommunicationDevice;
    public final AudioService mAudioService;
    public final AudioSystemAdapter mAudioSystem;
    public boolean mBluetoothA2dpSuspendedApplied;
    public boolean mBluetoothA2dpSuspendedExt;
    public boolean mBluetoothA2dpSuspendedInt;
    public boolean mBluetoothLeSuspendedApplied;
    public boolean mBluetoothLeSuspendedExt;
    public boolean mBluetoothLeSuspendedInt;
    public boolean mBluetoothScoOn;
    public boolean mBluetoothScoOnApplied;
    public final PowerManager.WakeLock mBrokerEventWakeLock;
    public BrokerHandler mBrokerHandler;
    public final BtHelper mBtHelper;
    public final Context mContext;
    public boolean mCurRCVBackOffState;
    public final AudioDeviceInventory mDeviceInventory;
    public DualA2dpVolumeManager mDualA2dpManager;
    public String mModeRequesterPackage;
    public AudioDeviceAttributes mPreferredCommunicationDevice;
    public Boolean mReceiverSupported;
    public int mRequestScoCount;
    public int mRequestScoUid;
    public boolean mSarBackoffParam;
    public IBinder mScoCb;
    public final boolean mScoManagedByAudio;
    public final SystemServerAdapter mSystemServer;
    public static final Object sLastDeviceConnectionMsgTimeLock = new Object();
    public static final int[] VALID_COMMUNICATION_DEVICE_TYPES = {2, 7, 3, 22, 1, 4, 23, 26, 11, 27, 5, 9, 19};
    public static final Set MESSAGES_MUTE_MUSIC = new HashSet();
    public int mCommunicationStrategyId = -1;
    public int mAccessibilityStrategyId = -1;
    public final Object mDeviceStateLock = new Object();
    public final AtomicBoolean mBluetoothA2dpEnabled = new AtomicBoolean(false);
    public final Object mSetModeLock = new Object();
    public AudioModeInfo mAudioModeOwner = new AudioModeInfo(0, 0, 0);
    public final Object mCommunicationDeviceLock = new Object();
    public int mCommunicationDeviceUpdateCount = 0;
    public final Object mBluetoothAudioStateLock = new Object();
    public final RemoteCallbackList mCommDevDispatchers = new RemoteCallbackList();
    public int mCurCommunicationPortId = -1;
    public final AtomicBoolean mMusicMuted = new AtomicBoolean(false);
    public final LinkedList mCommunicationRouteClients = new LinkedList();
    public AudioDeviceAttributes mCurrentCallDevice = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AudioModeInfo {
        public final int mMode;
        public final int mPid;
        public final int mUid;

        public AudioModeInfo(int i, int i2, int i3) {
            this.mMode = i;
            this.mPid = i2;
            this.mUid = i3;
        }

        public final String toString() {
            return "AudioModeInfo: mMode=" + AudioSystem.modeToString(this.mMode) + ", mPid=" + this.mPid + ", mUid=" + this.mUid;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BleVolumeInfo {
        public final int mIndex;
        public final int mMaxIndex;
        public final int mStreamType;

        public BleVolumeInfo(int i, int i2, int i3) {
            this.mIndex = i;
            this.mMaxIndex = i2;
            this.mStreamType = i3;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BrokerHandler extends Handler {
        public BrokerHandler() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:258:0x0394  */
        /* JADX WARN: Removed duplicated region for block: B:351:0x04c5  */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r13) {
            /*
                Method dump skipped, instructions count: 1714
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioDeviceBroker.BrokerHandler.handleMessage(android.os.Message):void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BrokerThread extends Thread {
        public BrokerThread() {
            super("AudioDeviceBroker");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Looper.prepare();
            synchronized (AudioDeviceBroker.this) {
                AudioDeviceBroker.this.mBrokerHandler = AudioDeviceBroker.this.new BrokerHandler();
                AudioDeviceBroker.this.notify();
            }
            Looper.loop();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BtDeviceChangedData {
        public final String mEventSource;
        public final BluetoothProfileConnectionInfo mInfo;
        public final BluetoothDevice mNewDevice;
        public final BluetoothDevice mPreviousDevice;

        public BtDeviceChangedData(BluetoothDevice bluetoothDevice, BluetoothDevice bluetoothDevice2, BluetoothProfileConnectionInfo bluetoothProfileConnectionInfo, String str) {
            this.mNewDevice = bluetoothDevice;
            this.mPreviousDevice = bluetoothDevice2;
            this.mInfo = bluetoothProfileConnectionInfo;
            this.mEventSource = str;
        }

        public final String toString() {
            return "BtDeviceChangedData profile=" + BluetoothProfile.getProfileName(this.mInfo.getProfile()) + ", switch device: [" + this.mPreviousDevice + "] -> [" + this.mNewDevice + "]";
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BtDeviceInfo {
        public final int mAudioSystemDevice;
        public final BluetoothDevice mDevice;
        public final String mEventSource;
        public final boolean mIsLeOutput;
        public final int mMusicDevice;
        public final int mProfile;
        public final int mState;
        public final boolean mSupprNoisy;
        public final int mVolume;

        public BtDeviceInfo(BluetoothDevice bluetoothDevice) {
            this.mDevice = bluetoothDevice;
            this.mProfile = 2;
            this.mEventSource = "";
            this.mMusicDevice = 0;
            this.mAudioSystemDevice = 0;
            this.mState = 0;
            this.mSupprNoisy = false;
            this.mVolume = -1;
            this.mIsLeOutput = false;
        }

        public BtDeviceInfo(BtDeviceChangedData btDeviceChangedData, BluetoothDevice bluetoothDevice, int i, int i2) {
            this.mDevice = bluetoothDevice;
            this.mState = i;
            this.mProfile = btDeviceChangedData.mInfo.getProfile();
            this.mSupprNoisy = btDeviceChangedData.mInfo.isSuppressNoisyIntent();
            this.mVolume = btDeviceChangedData.mInfo.getVolume();
            this.mIsLeOutput = btDeviceChangedData.mInfo.isLeOutput();
            this.mEventSource = btDeviceChangedData.mEventSource;
            this.mAudioSystemDevice = i2;
            this.mMusicDevice = 0;
        }

        public BtDeviceInfo(BtDeviceInfo btDeviceInfo) {
            this.mDevice = btDeviceInfo.mDevice;
            this.mState = 0;
            this.mProfile = btDeviceInfo.mProfile;
            this.mSupprNoisy = btDeviceInfo.mSupprNoisy;
            this.mVolume = btDeviceInfo.mVolume;
            this.mIsLeOutput = btDeviceInfo.mIsLeOutput;
            this.mEventSource = btDeviceInfo.mEventSource;
            this.mAudioSystemDevice = btDeviceInfo.mAudioSystemDevice;
            this.mMusicDevice = btDeviceInfo.mMusicDevice;
        }

        public final boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof BtDeviceInfo)) {
                return false;
            }
            BtDeviceInfo btDeviceInfo = (BtDeviceInfo) obj;
            return this.mProfile == btDeviceInfo.mProfile && this.mDevice.equals(btDeviceInfo.mDevice);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.mProfile), this.mDevice);
        }

        public final String toString() {
            return "BtDeviceInfo: device=" + this.mDevice.toString() + " state=" + this.mState + " prof=" + this.mProfile + " supprNoisy=" + this.mSupprNoisy + " volume=" + this.mVolume + " isLeOutput=" + this.mIsLeOutput + " eventSource=" + this.mEventSource + " audioSystemDevice=" + this.mAudioSystemDevice + " musicDevice=" + this.mMusicDevice;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CommunicationDeviceInfo {
        public final IBinder mCb;
        public final AudioDeviceAttributes mDevice;
        public final String mEventSource;
        public final boolean mIsPrivileged;
        public final boolean mOn;
        public final int mScoAudioMode;
        public final int mUid;

        public CommunicationDeviceInfo(IBinder iBinder, int i, AudioDeviceAttributes audioDeviceAttributes, boolean z, int i2, String str, boolean z2) {
            this.mCb = iBinder;
            this.mUid = i;
            this.mDevice = audioDeviceAttributes;
            this.mOn = z;
            this.mScoAudioMode = i2;
            this.mIsPrivileged = z2;
            this.mEventSource = str;
        }

        public final boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CommunicationDeviceInfo)) {
                return false;
            }
            CommunicationDeviceInfo communicationDeviceInfo = (CommunicationDeviceInfo) obj;
            return this.mCb.equals(communicationDeviceInfo.mCb) && this.mUid == communicationDeviceInfo.mUid;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.mCb.hashCode()), Integer.valueOf(this.mUid));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("CommunicationDeviceInfo mCb=");
            sb.append(this.mCb.toString());
            sb.append(" mUid=");
            sb.append(this.mUid);
            sb.append(" mDevice=[");
            AudioDeviceAttributes audioDeviceAttributes = this.mDevice;
            sb.append(audioDeviceAttributes != null ? audioDeviceAttributes.toString() : "null");
            sb.append("] mOn=");
            sb.append(this.mOn);
            sb.append(" mScoAudioMode=");
            sb.append(this.mScoAudioMode);
            sb.append(" mIsPrivileged=");
            sb.append(this.mIsPrivileged);
            sb.append(" mEventSource=");
            sb.append(this.mEventSource);
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CommunicationRouteClient implements IBinder.DeathRecipient {
        public final IBinder mCb;
        public final AudioDeviceAttributes mDevice;
        public final boolean mIsPrivileged;
        public boolean mPlaybackActive;
        public boolean mRecordingActive;
        public final int mUid;

        public CommunicationRouteClient(IBinder iBinder, int i, AudioDeviceAttributes audioDeviceAttributes, boolean z) {
            this.mCb = iBinder;
            this.mUid = i;
            this.mDevice = audioDeviceAttributes;
            this.mIsPrivileged = z;
            this.mPlaybackActive = AudioDeviceBroker.this.mAudioService.isPlaybackActiveForUid(i);
            this.mRecordingActive = AudioDeviceBroker.this.mAudioService.mRecordMonitor.isRecordingActiveForUid(i);
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            AudioDeviceBroker.this.sendLMsgNoDelay(34, 2, this);
        }

        public final boolean isActive() {
            return this.mIsPrivileged || this.mRecordingActive || this.mPlaybackActive;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("[CommunicationRouteClient: mUid: ");
            sb.append(this.mUid);
            sb.append(" mDevice: ");
            sb.append(this.mDevice.toString());
            sb.append(" mIsPrivileged: ");
            sb.append(this.mIsPrivileged);
            sb.append(" mPlaybackActive: ");
            sb.append(this.mPlaybackActive);
            sb.append(" mRecordingActive: ");
            return OptionalBool$$ExternalSyntheticOutline0.m("]", sb, this.mRecordingActive);
        }

        public final void unregisterDeathRecipient() {
            try {
                this.mCb.unlinkToDeath(this, 0);
            } catch (NoSuchElementException unused) {
                Log.w("AS.AudioDeviceBroker", "CommunicationRouteClient could not unlink to " + this.mCb + " binder death");
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:87:0x0108, code lost:
    
        if (r12 != 1) goto L60;
     */
    /* renamed from: -$$Nest$mhandleCustomMessage, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m257$$Nest$mhandleCustomMessage(com.android.server.audio.AudioDeviceBroker r11, android.os.Message r12) {
        /*
            Method dump skipped, instructions count: 372
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioDeviceBroker.m257$$Nest$mhandleCustomMessage(com.android.server.audio.AudioDeviceBroker, android.os.Message):void");
    }

    /* renamed from: -$$Nest$monReceiveBtEvent, reason: not valid java name */
    public static void m258$$Nest$monReceiveBtEvent(AudioDeviceBroker audioDeviceBroker, Intent intent) {
        final BtHelper btHelper = audioDeviceBroker.mBtHelper;
        synchronized (btHelper) {
            String action = intent.getAction();
            Log.i("AS.BtHelper", "onReceiveBtEvent action: " + action + " mScoAudioState: " + btHelper.mScoAudioState);
            if (action.equals("android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED")) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE", BluetoothDevice.class);
                if (bluetoothDevice != null && !btHelper.isProfilePoxyConnected(1)) {
                    EventLogger eventLogger = AudioService.sDeviceLogger;
                    EventLogger.StringEvent stringEvent = new EventLogger.StringEvent("onReceiveBtEvent ACTION_ACTIVE_DEVICE_CHANGED received with null profile proxy for device: " + bluetoothDevice);
                    stringEvent.printLog(0, "AS.BtHelper");
                    eventLogger.enqueue(stringEvent);
                    return;
                }
                btHelper.onSetBtScoActiveDevice(bluetoothDevice);
                AudioExecutor.execute(new Runnable() { // from class: com.android.server.audio.BtHelper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        List<BluetoothDevice> connectedDevices;
                        BtHelper btHelper2 = BtHelper.this;
                        BluetoothHeadset bluetoothHeadset = btHelper2.mBluetoothHeadset;
                        boolean z = false;
                        if (bluetoothHeadset != null && (connectedDevices = bluetoothHeadset.getConnectedDevices()) != null && connectedDevices.size() == 1) {
                            z = BtUtils.isSamsungWatch(connectedDevices.get(0), btHelper2.mBluetoothHeadset);
                        }
                        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("receiveBtEvent updateWatchConnectionState = ", "AS.BtHelper", z);
                        VibratorManagerService vibratorManagerService = (VibratorManagerService) btHelper2.mVibratorManagerInternal.mServiceWeakReference.get();
                        if (vibratorManagerService == null) {
                            return;
                        }
                        VibrationCustomSettings vibrationCustomSettings = vibratorManagerService.mVibrationSettings.mCustomSettings;
                        vibrationCustomSettings.getClass();
                        Log.d("VibratorManagerService", "Update only watch connected: " + z);
                        vibrationCustomSettings.mOnlyWatchConnected = z;
                    }
                });
            } else if (action.equals("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED")) {
                int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1);
                btHelper.onScoAudioStateChanged(intExtra);
                if (intExtra == 12) {
                    int intExtra2 = intent.getIntExtra("com.samsung.bt.hfp.intent.extra.HEADSET_SCO_VOLUME", 7) * 10;
                    btHelper.mDeviceBroker.mAudioService.postSetVolumeIndexOnDevice(6, intExtra2, 32, "BtHelper.receiveBtEvent");
                    Log.i("AS.BtHelper", "SCO volume set to index " + intExtra2 + " by BT intent");
                }
            } else if ("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED".equals(action)) {
                int intExtra3 = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                int intExtra4 = intent.getIntExtra("android.bluetooth.device.extra.DEVICE_TYPE", 0);
                if (intExtra3 == 2 && intExtra4 != 1) {
                    btHelper.onSetBtScoActiveDevice((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE"));
                }
            }
        }
    }

    /* renamed from: -$$Nest$monSendBecomingNoisyIntent, reason: not valid java name */
    public static void m259$$Nest$monSendBecomingNoisyIntent(AudioDeviceBroker audioDeviceBroker, int i) {
        long clearCallingIdentity;
        String[] strArr;
        if (!audioDeviceBroker.mAudioService.isMultiSoundOnInternal() || audioDeviceBroker.mAudioService.getPinDevice() == i) {
            EventLogger eventLogger = AudioService.sDeviceLogger;
            EventLogger.StringEvent stringEvent = new EventLogger.StringEvent("broadcast ACTION_AUDIO_BECOMING_NOISY");
            stringEvent.printLog(0, "AS.AudioDeviceBroker");
            eventLogger.enqueue(stringEvent);
            SystemServerAdapter systemServerAdapter = audioDeviceBroker.mSystemServer;
            if (systemServerAdapter.mContext != null) {
                Intent intent = new Intent("android.media.AUDIO_BECOMING_NOISY_SEC");
                intent.addFlags(67108864);
                intent.addFlags(268435456);
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    systemServerAdapter.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
                } finally {
                }
            }
            Intent intent2 = new Intent("android.media.AUDIO_BECOMING_NOISY");
            if (AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(i))) {
                intent2.putExtra("android.bluetooth.a2dp.extra.DISCONNECT_A2DP", true);
            }
            SystemServerAdapter systemServerAdapter2 = audioDeviceBroker.mSystemServer;
            if (systemServerAdapter2.mContext == null) {
                return;
            }
            intent2.addFlags(67108864);
            intent2.addFlags(268435456);
            clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                systemServerAdapter2.mContext.sendBroadcastAsUser(intent2, UserHandle.ALL);
                return;
            } finally {
            }
        }
        MultiSoundManager multiSoundManager = audioDeviceBroker.mAudioService.mMultiSoundManager;
        multiSoundManager.getClass();
        Log.d("AS.MultiSoundManager", "sendBecomingNoisyIntentToUnpinApps, " + i);
        multiSoundManager.mAudioSystem.getClass();
        if (AudioSystem.getDeviceConnectionState(32768, "0") <= 0 || i == 32768 || !multiSoundManager.isRemoteSubmixAppExist()) {
            int pinDevice = multiSoundManager.getPinDevice(false);
            synchronized (multiSoundManager.mMultiSoundLock) {
                try {
                    Iterator it = new ArrayList(multiSoundManager.mPinAppInfoList.values()).iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            strArr = new String[]{""};
                            break;
                        }
                        MultiSoundManager.MultiSoundItem multiSoundItem = (MultiSoundManager.MultiSoundItem) it.next();
                        if (multiSoundItem.mDevice == pinDevice) {
                            AudioService.AnonymousClass11 anonymousClass11 = multiSoundManager.mInterface;
                            int i2 = multiSoundItem.mUid;
                            anonymousClass11.getClass();
                            int i3 = AudioService.BECOMING_NOISY_DELAY_MS;
                            strArr = AudioService.this.getPackageName(i2);
                        }
                    }
                } finally {
                }
            }
        } else {
            ArrayList arrayList = new ArrayList();
            synchronized (multiSoundManager.mRemoteSubmixApps) {
                try {
                    Iterator it2 = ((HashSet) multiSoundManager.mRemoteSubmixApps).iterator();
                    while (it2.hasNext()) {
                        Integer num = (Integer) it2.next();
                        AudioService.AnonymousClass11 anonymousClass112 = multiSoundManager.mInterface;
                        int intValue = num.intValue();
                        anonymousClass112.getClass();
                        int i4 = AudioService.BECOMING_NOISY_DELAY_MS;
                        arrayList.addAll(Arrays.asList(AudioService.this.getPackageName(intValue)));
                    }
                } finally {
                }
            }
            strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
        }
        Intent intent3 = new Intent();
        intent3.setAction("android.media.AUDIO_BECOMING_NOISY_SEC");
        if (AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(i))) {
            intent3.putExtra("android.bluetooth.a2dp.extra.DISCONNECT_A2DP", true);
        }
        multiSoundManager.sendIntentToSpecificPackage(intent3, strArr);
        intent3.setAction("android.media.AUDIO_BECOMING_NOISY");
        if (intent3.getPackage() != null) {
            intent3.setPackage(null);
        }
        multiSoundManager.sendIntentToSpecificPackage(intent3, strArr);
        EventLogger eventLogger2 = AudioService.sDeviceLogger;
        EventLogger.StringEvent stringEvent2 = new EventLogger.StringEvent("broadcast ACTION_AUDIO_BECOMING_NOISY to unppin apps");
        stringEvent2.printLog(0, "AS.AudioDeviceBroker");
        eventLogger2.enqueue(stringEvent2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0070, code lost:
    
        if (r14[3] != 1) goto L25;
     */
    /* renamed from: -$$Nest$monUpdateCommunicationRouteClient, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m260$$Nest$monUpdateCommunicationRouteClient(com.android.server.audio.AudioDeviceBroker r13, boolean r14, java.lang.String r15) {
        /*
            r0 = 0
            r1 = 1
            r2 = 2
            com.android.server.audio.AudioDeviceBroker$CommunicationRouteClient r3 = r13.topCommunicationRouteClient()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "onUpdateCommunicationRouteClient, crc: "
            r4.<init>(r5)
            r4.append(r3)
            java.lang.String r5 = " wasBtScoRequested: "
            r4.append(r5)
            r4.append(r14)
            java.lang.String r5 = " eventSource: "
            r4.append(r5)
            r4.append(r15)
            java.lang.String r4 = r4.toString()
            java.lang.String r5 = "AS.AudioDeviceBroker"
            android.util.Log.v(r5, r4)
            com.android.server.audio.BtHelper r4 = r13.mBtHelper
            r6 = 7
            if (r3 == 0) goto Lce
            android.media.AudioDeviceAttributes r14 = r3.mDevice
            int r14 = r14.getType()
            if (r14 != r6) goto L91
            android.bluetooth.BluetoothDevice r14 = r4.mBluetoothHeadsetDevice
            if (r14 != 0) goto L3d
            goto L91
        L3d:
            android.bluetooth.BluetoothHeadset r6 = r4.mBluetoothHeadset
            boolean r14 = com.samsung.android.server.audio.utils.BtUtils.isSamsungWatch(r14, r6)
            if (r14 != 0) goto L8d
            android.bluetooth.BluetoothDevice r14 = r4.mBluetoothHeadsetDevice
            if (r14 != 0) goto L4a
            goto L91
        L4a:
            r4 = 518(0x206, float:7.26E-43)
            byte r4 = (byte) r4
            byte r6 = (byte) r2
            byte[] r7 = new byte[r2]     // Catch: java.lang.Exception -> L73
            r7[r0] = r4     // Catch: java.lang.Exception -> L73
            r7[r1] = r6     // Catch: java.lang.Exception -> L73
            byte[] r4 = r14.semGetMetadata(r7)     // Catch: java.lang.Exception -> L73
            r7 = 519(0x207, float:7.27E-43)
            byte r7 = (byte) r7     // Catch: java.lang.Exception -> L73
            byte[] r2 = new byte[r2]     // Catch: java.lang.Exception -> L73
            r2[r0] = r7     // Catch: java.lang.Exception -> L73
            r2[r1] = r6     // Catch: java.lang.Exception -> L73
            byte[] r14 = r14.semGetMetadata(r2)     // Catch: java.lang.Exception -> L73
            if (r4 == 0) goto L91
            if (r14 == 0) goto L91
            r0 = 3
            r2 = r4[r0]     // Catch: java.lang.Exception -> L73
            if (r2 == r1) goto L91
            r14 = r14[r0]     // Catch: java.lang.Exception -> L73
            if (r14 == r1) goto L91
            goto L8d
        L73:
            r14 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "failed to get buds wearing status: "
            r0.<init>(r1)
            java.lang.String r14 = r14.getMessage()
            r0.append(r14)
            java.lang.String r14 = r0.toString()
            java.lang.String r0 = "AS.BtUtils"
            android.util.Log.w(r0, r14)
            goto L91
        L8d:
            r13.updateCommunicationRoute(r15)
            goto Le0
        L91:
            java.lang.Boolean r14 = r13.mReceiverSupported
            boolean r14 = r14.booleanValue()
            if (r14 != 0) goto Lbf
            android.media.AudioDeviceAttributes r14 = r3.mDevice
            if (r14 == 0) goto Lbf
            android.media.AudioDeviceAttributes r0 = r13.mCurrentCallDevice
            if (r0 == 0) goto Lbf
            boolean r14 = r0.equals(r14)
            if (r14 == 0) goto Lbf
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            java.lang.String r0 = "onUpdateCommunicationRouteClient return mCurrentCallDevice = "
            r14.<init>(r0)
            android.media.AudioDeviceAttributes r0 = r13.mCurrentCallDevice
            r14.append(r0)
            java.lang.String r14 = r14.toString()
            android.util.Log.i(r5, r14)
            r13.updateCommunicationRoute(r15)
            goto Le0
        Lbf:
            android.os.IBinder r7 = r3.mCb
            int r8 = r3.mUid
            android.media.AudioDeviceAttributes r9 = r3.mDevice
            boolean r11 = r3.mIsPrivileged
            r10 = -1
            r6 = r13
            r12 = r15
            r6.setCommunicationRouteForClient(r7, r8, r9, r10, r11, r12)
            goto Le0
        Lce:
            boolean r0 = r13.mScoManagedByAudio
            if (r0 != 0) goto Ldd
            boolean r0 = r13.isDeviceRequestedForCommunication(r6)
            if (r0 != 0) goto Ldd
            if (r14 == 0) goto Ldd
            r4.stopBluetoothSco(r15)
        Ldd:
            r13.updateCommunicationRoute(r15)
        Le0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioDeviceBroker.m260$$Nest$monUpdateCommunicationRouteClient(com.android.server.audio.AudioDeviceBroker, boolean, java.lang.String):void");
    }

    static {
        HashSet hashSet = new HashSet();
        MESSAGES_SAR_RCV_CONTROL = hashSet;
        hashSet.add(2);
        hashSet.add(4);
        hashSet.add(5);
        hashSet.add(42);
    }

    public AudioDeviceBroker(Context context, AudioService audioService, AudioSystemAdapter audioSystemAdapter) {
        Boolean bool = Boolean.FALSE;
        this.mReceiverSupported = bool;
        this.mRequestScoUid = 0;
        this.mRequestScoCount = 0;
        this.mScoCb = null;
        this.mCurRCVBackOffState = false;
        this.mSarBackoffParam = false;
        this.mDualA2dpManager = null;
        this.mModeRequesterPackage = "";
        this.mContext = context;
        this.mAudioService = audioService;
        this.mBtHelper = new BtHelper(this, context);
        this.mDeviceInventory = new AudioDeviceInventory(this);
        Objects.requireNonNull(context);
        this.mSystemServer = new SystemServerAdapter(context);
        this.mAudioSystem = audioSystemAdapter;
        this.mScoManagedByAudio = Flags.scoManagedByAudio() && ((Boolean) BluetoothProperties.isScoManagedByAudioEnabled().orElse(bool)).booleanValue();
        this.mBrokerEventWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, "handleAudioDeviceEvent");
        new BrokerThread().start();
        synchronized (this) {
            while (this.mBrokerHandler == null) {
                try {
                    wait();
                } catch (InterruptedException unused) {
                    Log.e("AS.AudioDeviceBroker", "Interruption while waiting on BrokerHandler");
                }
            }
        }
        synchronized (this.mBluetoothAudioStateLock) {
            this.mBluetoothScoOnApplied = false;
            this.mBluetoothA2dpSuspendedApplied = false;
            this.mBluetoothLeSuspendedApplied = false;
            reapplyAudioHalBluetoothState();
        }
        initRoutingStrategyIds();
        this.mPreferredCommunicationDevice = null;
        updateActiveCommunicationDevice();
        SystemServerAdapter systemServerAdapter = this.mSystemServer;
        Context context2 = this.mContext;
        systemServerAdapter.getClass();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_STARTED");
        context2.registerReceiverAsUser(new BroadcastReceiver() { // from class: com.android.server.audio.SystemServerAdapter.1
            public AnonymousClass1() {
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context3, Intent intent) {
                int intExtra;
                UserInfo profileParent;
                if (!"android.intent.action.USER_STARTED".equals(intent.getAction()) || (intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000)) == -10000 || (profileParent = ((UserManager) context3.getSystemService(UserManager.class)).getProfileParent(intExtra)) == null) {
                    return;
                }
                SystemServerAdapter systemServerAdapter2 = SystemServerAdapter.this;
                int i = profileParent.id;
                systemServerAdapter2.getClass();
                Intent registerReceiverAsUser = context3.registerReceiverAsUser(null, UserHandle.of(i), new IntentFilter("android.media.action.HDMI_AUDIO_PLUG"), null, null);
                if (registerReceiverAsUser != null) {
                    ActivityManager.broadcastStickyIntent(registerReceiverAsUser, intExtra);
                }
                SystemServerAdapter systemServerAdapter3 = SystemServerAdapter.this;
                int i2 = profileParent.id;
                systemServerAdapter3.getClass();
                Intent registerReceiverAsUser2 = context3.registerReceiverAsUser(null, UserHandle.of(i2), new IntentFilter("android.intent.action.HEADSET_PLUG"), null, null);
                if (registerReceiverAsUser2 != null) {
                    ActivityManager.broadcastStickyIntent(registerReceiverAsUser2, intExtra);
                }
            }
        }, UserHandle.ALL, intentFilter, null, null);
        this.mBrokerHandler.sendEmptyMessage(2759);
    }

    public static void btMediaMetricRecord(BluetoothDevice bluetoothDevice, String str, BtDeviceChangedData btDeviceChangedData) {
        new MediaMetrics.Item("audio.device.queueOnBluetoothActiveDeviceChanged").set(MediaMetrics.Property.STATE, str).set(MediaMetrics.Property.STATUS, Integer.valueOf(btDeviceChangedData.mInfo.getProfile())).set(MediaMetrics.Property.NAME, TextUtils.emptyIfNull(bluetoothDevice.getName())).record();
    }

    public static BtDeviceInfo createBtDeviceInfo(BtDeviceChangedData btDeviceChangedData, BluetoothDevice bluetoothDevice, int i) {
        int i2;
        int profile = btDeviceChangedData.mInfo.getProfile();
        if (profile == 1) {
            i2 = 16;
        } else if (profile == 2) {
            i2 = 128;
        } else if (profile == 11) {
            i2 = -2147352576;
        } else if (profile == 26) {
            i2 = 536870914;
        } else if (profile == 21) {
            i2 = 134217728;
        } else {
            if (profile != 22) {
                throw new IllegalArgumentException("Invalid profile " + btDeviceChangedData.mInfo.getProfile());
            }
            i2 = btDeviceChangedData.mInfo.isLeOutput() ? 536870912 : -1610612736;
        }
        return new BtDeviceInfo(btDeviceChangedData, bluetoothDevice, i, i2);
    }

    public static List getAvailableCommunicationDevices() {
        ArrayList arrayList = new ArrayList();
        for (AudioDeviceInfo audioDeviceInfo : AudioManager.getDevicesStatic(2)) {
            if (isValidCommunicationDevice(audioDeviceInfo)) {
                arrayList.add(audioDeviceInfo);
            }
        }
        return arrayList;
    }

    public static AudioDeviceInfo getCommunicationDeviceOfType(final int i) {
        return (AudioDeviceInfo) getAvailableCommunicationDevices().stream().filter(new Predicate() { // from class: com.android.server.audio.AudioDeviceBroker$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AudioDeviceInfo) obj).getType() == i;
            }
        }).findFirst().orElse(null);
    }

    public static boolean isMessageHandledUnderWakelock(int i) {
        return i == 2 || i == 29 || i == 31 || i == 35 || i == 49 || i == 6 || i == 7 || i == 10 || i == 11;
    }

    public static boolean isValidCommunicationDevice(AudioDeviceInfo audioDeviceInfo) {
        Objects.requireNonNull(audioDeviceInfo, "device must not be null");
        if (!audioDeviceInfo.isSink()) {
            return false;
        }
        int type = audioDeviceInfo.getType();
        int[] iArr = VALID_COMMUNICATION_DEVICE_TYPES;
        for (int i = 0; i < 13; i++) {
            if (type == iArr[i]) {
                return true;
            }
        }
        return false;
    }

    public final CommunicationRouteClient addCommunicationRouteClient(IBinder iBinder, int i, AudioDeviceAttributes audioDeviceAttributes, boolean z) {
        removeCommunicationRouteClient(iBinder);
        CommunicationRouteClient communicationRouteClient = new CommunicationRouteClient(iBinder, i, audioDeviceAttributes, z);
        try {
            iBinder.linkToDeath(communicationRouteClient, 0);
            this.mCommunicationRouteClients.add(0, communicationRouteClient);
            if (!communicationRouteClient.isActive()) {
                AudioService audioService = this.mAudioService;
                setForceCommunicationClientStateAndDelayedCheck(communicationRouteClient, !audioService.isPlaybackActiveForUid(i), !audioService.mRecordMonitor.isRecordingActiveForUid(i));
            }
            return communicationRouteClient;
        } catch (RemoteException unused) {
            Log.w("AS.AudioDeviceBroker", "CommunicationRouteClient could not link to " + communicationRouteClient.mCb + " binder death");
            return null;
        }
    }

    public final void checkAndResetBtSco() {
        IBinder iBinder;
        if (this.mRequestScoCount <= 4 || (iBinder = this.mScoCb) == null) {
            return;
        }
        CommunicationRouteClient removeCommunicationRouteClient = removeCommunicationRouteClient(iBinder);
        if (removeCommunicationRouteClient != null) {
            EventLogger eventLogger = AudioService.sScoPreventionLogger;
            EventLogger.StringEvent stringEvent = new EventLogger.StringEvent("checkAndResetBtSco remove SCO client of uid = " + removeCommunicationRouteClient.mUid);
            stringEvent.printLog(0, "AS.AudioDeviceBroker");
            eventLogger.enqueue(stringEvent);
        }
        this.mRequestScoUid = 0;
        this.mRequestScoCount = 0;
        this.mScoCb = null;
    }

    public final boolean checkDeviceConnected(int i) {
        boolean checkDeviceConnected;
        synchronized (this.mDeviceStateLock) {
            checkDeviceConnected = this.mDeviceInventory.checkDeviceConnected(i);
        }
        return checkDeviceConnected;
    }

    public final void checkMessagesMuteMusic(int i) {
        boolean messageMutesMusic = messageMutesMusic(i);
        if (!messageMutesMusic) {
            Iterator it = ((HashSet) MESSAGES_MUTE_MUSIC).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                int intValue = ((Integer) it.next()).intValue();
                if (this.mBrokerHandler.hasMessages(intValue) && messageMutesMusic(intValue)) {
                    messageMutesMusic = true;
                    break;
                }
            }
        }
        if (messageMutesMusic != this.mMusicMuted.getAndSet(messageMutesMusic)) {
            this.mAudioService.setMusicMute(messageMutesMusic);
        }
    }

    public final void clearA2dpSuspended(boolean z) {
        Log.v("AS.AudioDeviceBroker", "clearA2dpSuspended, internalOnly: " + z);
        synchronized (this.mBluetoothAudioStateLock) {
            try {
                this.mBluetoothA2dpSuspendedInt = false;
                if (!z) {
                    this.mBluetoothA2dpSuspendedExt = false;
                }
                updateAudioHalBluetoothState();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void clearLeAudioSuspended(boolean z) {
        Log.v("AS.AudioDeviceBroker", "clearLeAudioSuspended, internalOnly: " + z);
        synchronized (this.mBluetoothAudioStateLock) {
            try {
                this.mBluetoothLeSuspendedInt = false;
                if (!z) {
                    this.mBluetoothLeSuspendedExt = false;
                }
                updateAudioHalBluetoothState();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00f8 A[Catch: all -> 0x0090, TryCatch #0 {all -> 0x0090, blocks: (B:14:0x003f, B:18:0x0077, B:20:0x007f, B:21:0x00bb, B:25:0x0093, B:26:0x00de, B:29:0x00e4, B:35:0x00f8, B:36:0x00ee, B:37:0x010a, B:39:0x0122, B:41:0x012a, B:42:0x0161, B:43:0x0164, B:54:0x0157), top: B:13:0x003f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void connectA2dpDevice(android.bluetooth.BluetoothDevice r17, int r18, int r19) {
        /*
            Method dump skipped, instructions count: 379
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.AudioDeviceBroker.connectA2dpDevice(android.bluetooth.BluetoothDevice, int, int):void");
    }

    public final void dump(final PrintWriter printWriter) {
        BluetoothClass bluetoothClass;
        String str;
        if (this.mBrokerHandler != null) {
            printWriter.println("  Message handler (watch for unhandled messages):");
            this.mBrokerHandler.dump(new PrintWriterPrinter(printWriter), "    ");
        } else {
            printWriter.println("Message handler is null");
        }
        AudioDeviceInventory audioDeviceInventory = this.mDeviceInventory;
        audioDeviceInventory.getClass();
        printWriter.println("\n  BECOMING_NOISY_INTENT_DEVICES_SET=");
        AudioDeviceInventory.BECOMING_NOISY_INTENT_DEVICES_SET.forEach(new AudioDeviceInventory$$ExternalSyntheticLambda33(2, printWriter));
        printWriter.println("\n  Preferred devices for strategy:");
        final int i = 0;
        audioDeviceInventory.mPreferredDevices.forEach(new BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda15
            public final /* synthetic */ String f$1 = "  ";

            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                switch (i) {
                    case 0:
                        printWriter.println("  " + this.f$1 + "strategy:" + ((Integer) obj) + " device:" + ((List) obj2));
                        break;
                    case 1:
                        printWriter.println("  " + this.f$1 + "strategy:" + ((Integer) obj) + " device:" + ((List) obj2));
                        break;
                    case 2:
                        printWriter.println("  " + this.f$1 + ((AudioDeviceInventory.DeviceInfo) obj2).toString());
                        break;
                    case 3:
                        printWriter.println("  " + this.f$1 + "capturePreset:" + ((Integer) obj) + " devices:" + ((List) obj2));
                        break;
                    case 4:
                        PrintWriter printWriter2 = printWriter;
                        Pair pair = (Pair) obj;
                        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "strategy: ");
                        m.append(pair.first);
                        m.append(" role:");
                        m.append(pair.second);
                        m.append(" devices:");
                        m.append((List) obj2);
                        printWriter2.println(m.toString());
                        break;
                    case 5:
                        PrintWriter printWriter3 = printWriter;
                        Pair pair2 = (Pair) obj;
                        StringBuilder m2 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "strategy: ");
                        m2.append(pair2.first);
                        m2.append(" role:");
                        m2.append(pair2.second);
                        m2.append(" devices:");
                        m2.append((List) obj2);
                        printWriter3.println(m2.toString());
                        break;
                    case 6:
                        PrintWriter printWriter4 = printWriter;
                        Pair pair3 = (Pair) obj;
                        StringBuilder m3 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "preset: ");
                        m3.append(pair3.first);
                        m3.append(" role:");
                        m3.append(pair3.second);
                        m3.append(" devices:");
                        m3.append((List) obj2);
                        printWriter4.println(m3.toString());
                        break;
                    default:
                        PrintWriter printWriter5 = printWriter;
                        Pair pair4 = (Pair) obj;
                        StringBuilder m4 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "preset: ");
                        m4.append(pair4.first);
                        m4.append(" role:");
                        m4.append(pair4.second);
                        m4.append(" devices:");
                        m4.append((List) obj2);
                        printWriter5.println(m4.toString());
                        break;
                }
            }
        });
        printWriter.println("\n  Non-default devices for strategy:");
        final int i2 = 1;
        audioDeviceInventory.mNonDefaultDevices.forEach(new BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda15
            public final /* synthetic */ String f$1 = "  ";

            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                switch (i2) {
                    case 0:
                        printWriter.println("  " + this.f$1 + "strategy:" + ((Integer) obj) + " device:" + ((List) obj2));
                        break;
                    case 1:
                        printWriter.println("  " + this.f$1 + "strategy:" + ((Integer) obj) + " device:" + ((List) obj2));
                        break;
                    case 2:
                        printWriter.println("  " + this.f$1 + ((AudioDeviceInventory.DeviceInfo) obj2).toString());
                        break;
                    case 3:
                        printWriter.println("  " + this.f$1 + "capturePreset:" + ((Integer) obj) + " devices:" + ((List) obj2));
                        break;
                    case 4:
                        PrintWriter printWriter2 = printWriter;
                        Pair pair = (Pair) obj;
                        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "strategy: ");
                        m.append(pair.first);
                        m.append(" role:");
                        m.append(pair.second);
                        m.append(" devices:");
                        m.append((List) obj2);
                        printWriter2.println(m.toString());
                        break;
                    case 5:
                        PrintWriter printWriter3 = printWriter;
                        Pair pair2 = (Pair) obj;
                        StringBuilder m2 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "strategy: ");
                        m2.append(pair2.first);
                        m2.append(" role:");
                        m2.append(pair2.second);
                        m2.append(" devices:");
                        m2.append((List) obj2);
                        printWriter3.println(m2.toString());
                        break;
                    case 6:
                        PrintWriter printWriter4 = printWriter;
                        Pair pair3 = (Pair) obj;
                        StringBuilder m3 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "preset: ");
                        m3.append(pair3.first);
                        m3.append(" role:");
                        m3.append(pair3.second);
                        m3.append(" devices:");
                        m3.append((List) obj2);
                        printWriter4.println(m3.toString());
                        break;
                    default:
                        PrintWriter printWriter5 = printWriter;
                        Pair pair4 = (Pair) obj;
                        StringBuilder m4 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "preset: ");
                        m4.append(pair4.first);
                        m4.append(" role:");
                        m4.append(pair4.second);
                        m4.append(" devices:");
                        m4.append((List) obj2);
                        printWriter5.println(m4.toString());
                        break;
                }
            }
        });
        printWriter.println("\n  Connected devices:");
        final int i3 = 2;
        audioDeviceInventory.mConnectedDevices.forEach(new BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda15
            public final /* synthetic */ String f$1 = "  ";

            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                switch (i3) {
                    case 0:
                        printWriter.println("  " + this.f$1 + "strategy:" + ((Integer) obj) + " device:" + ((List) obj2));
                        break;
                    case 1:
                        printWriter.println("  " + this.f$1 + "strategy:" + ((Integer) obj) + " device:" + ((List) obj2));
                        break;
                    case 2:
                        printWriter.println("  " + this.f$1 + ((AudioDeviceInventory.DeviceInfo) obj2).toString());
                        break;
                    case 3:
                        printWriter.println("  " + this.f$1 + "capturePreset:" + ((Integer) obj) + " devices:" + ((List) obj2));
                        break;
                    case 4:
                        PrintWriter printWriter2 = printWriter;
                        Pair pair = (Pair) obj;
                        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "strategy: ");
                        m.append(pair.first);
                        m.append(" role:");
                        m.append(pair.second);
                        m.append(" devices:");
                        m.append((List) obj2);
                        printWriter2.println(m.toString());
                        break;
                    case 5:
                        PrintWriter printWriter3 = printWriter;
                        Pair pair2 = (Pair) obj;
                        StringBuilder m2 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "strategy: ");
                        m2.append(pair2.first);
                        m2.append(" role:");
                        m2.append(pair2.second);
                        m2.append(" devices:");
                        m2.append((List) obj2);
                        printWriter3.println(m2.toString());
                        break;
                    case 6:
                        PrintWriter printWriter4 = printWriter;
                        Pair pair3 = (Pair) obj;
                        StringBuilder m3 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "preset: ");
                        m3.append(pair3.first);
                        m3.append(" role:");
                        m3.append(pair3.second);
                        m3.append(" devices:");
                        m3.append((List) obj2);
                        printWriter4.println(m3.toString());
                        break;
                    default:
                        PrintWriter printWriter5 = printWriter;
                        Pair pair4 = (Pair) obj;
                        StringBuilder m4 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "preset: ");
                        m4.append(pair4.first);
                        m4.append(" role:");
                        m4.append(pair4.second);
                        m4.append(" devices:");
                        m4.append((List) obj2);
                        printWriter5.println(m4.toString());
                        break;
                }
            }
        });
        printWriter.println("\n  APM Connected device (A2DP sink only):");
        printWriter.println("\n  mBluetoothDualModeEnabled = " + audioDeviceInventory.mBluetoothDualModeEnabled);
        printWriter.println("\n  Preferred devices for capture preset:");
        final int i4 = 3;
        audioDeviceInventory.mPreferredDevicesForCapturePreset.forEach(new BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda15
            public final /* synthetic */ String f$1 = "  ";

            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                switch (i4) {
                    case 0:
                        printWriter.println("  " + this.f$1 + "strategy:" + ((Integer) obj) + " device:" + ((List) obj2));
                        break;
                    case 1:
                        printWriter.println("  " + this.f$1 + "strategy:" + ((Integer) obj) + " device:" + ((List) obj2));
                        break;
                    case 2:
                        printWriter.println("  " + this.f$1 + ((AudioDeviceInventory.DeviceInfo) obj2).toString());
                        break;
                    case 3:
                        printWriter.println("  " + this.f$1 + "capturePreset:" + ((Integer) obj) + " devices:" + ((List) obj2));
                        break;
                    case 4:
                        PrintWriter printWriter2 = printWriter;
                        Pair pair = (Pair) obj;
                        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "strategy: ");
                        m.append(pair.first);
                        m.append(" role:");
                        m.append(pair.second);
                        m.append(" devices:");
                        m.append((List) obj2);
                        printWriter2.println(m.toString());
                        break;
                    case 5:
                        PrintWriter printWriter3 = printWriter;
                        Pair pair2 = (Pair) obj;
                        StringBuilder m2 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "strategy: ");
                        m2.append(pair2.first);
                        m2.append(" role:");
                        m2.append(pair2.second);
                        m2.append(" devices:");
                        m2.append((List) obj2);
                        printWriter3.println(m2.toString());
                        break;
                    case 6:
                        PrintWriter printWriter4 = printWriter;
                        Pair pair3 = (Pair) obj;
                        StringBuilder m3 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "preset: ");
                        m3.append(pair3.first);
                        m3.append(" role:");
                        m3.append(pair3.second);
                        m3.append(" devices:");
                        m3.append((List) obj2);
                        printWriter4.println(m3.toString());
                        break;
                    default:
                        PrintWriter printWriter5 = printWriter;
                        Pair pair4 = (Pair) obj;
                        StringBuilder m4 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "preset: ");
                        m4.append(pair4.first);
                        m4.append(" role:");
                        m4.append(pair4.second);
                        m4.append(" devices:");
                        m4.append((List) obj2);
                        printWriter5.println(m4.toString());
                        break;
                }
            }
        });
        printWriter.println("\n  Applied devices roles for strategies (from API):");
        final int i5 = 4;
        audioDeviceInventory.mAppliedStrategyRoles.forEach(new BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda15
            public final /* synthetic */ String f$1 = "  ";

            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                switch (i5) {
                    case 0:
                        printWriter.println("  " + this.f$1 + "strategy:" + ((Integer) obj) + " device:" + ((List) obj2));
                        break;
                    case 1:
                        printWriter.println("  " + this.f$1 + "strategy:" + ((Integer) obj) + " device:" + ((List) obj2));
                        break;
                    case 2:
                        printWriter.println("  " + this.f$1 + ((AudioDeviceInventory.DeviceInfo) obj2).toString());
                        break;
                    case 3:
                        printWriter.println("  " + this.f$1 + "capturePreset:" + ((Integer) obj) + " devices:" + ((List) obj2));
                        break;
                    case 4:
                        PrintWriter printWriter2 = printWriter;
                        Pair pair = (Pair) obj;
                        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "strategy: ");
                        m.append(pair.first);
                        m.append(" role:");
                        m.append(pair.second);
                        m.append(" devices:");
                        m.append((List) obj2);
                        printWriter2.println(m.toString());
                        break;
                    case 5:
                        PrintWriter printWriter3 = printWriter;
                        Pair pair2 = (Pair) obj;
                        StringBuilder m2 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "strategy: ");
                        m2.append(pair2.first);
                        m2.append(" role:");
                        m2.append(pair2.second);
                        m2.append(" devices:");
                        m2.append((List) obj2);
                        printWriter3.println(m2.toString());
                        break;
                    case 6:
                        PrintWriter printWriter4 = printWriter;
                        Pair pair3 = (Pair) obj;
                        StringBuilder m3 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "preset: ");
                        m3.append(pair3.first);
                        m3.append(" role:");
                        m3.append(pair3.second);
                        m3.append(" devices:");
                        m3.append((List) obj2);
                        printWriter4.println(m3.toString());
                        break;
                    default:
                        PrintWriter printWriter5 = printWriter;
                        Pair pair4 = (Pair) obj;
                        StringBuilder m4 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "preset: ");
                        m4.append(pair4.first);
                        m4.append(" role:");
                        m4.append(pair4.second);
                        m4.append(" devices:");
                        m4.append((List) obj2);
                        printWriter5.println(m4.toString());
                        break;
                }
            }
        });
        printWriter.println("\n  Applied devices roles for strategies (internal):");
        final int i6 = 5;
        audioDeviceInventory.mAppliedStrategyRolesInt.forEach(new BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda15
            public final /* synthetic */ String f$1 = "  ";

            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                switch (i6) {
                    case 0:
                        printWriter.println("  " + this.f$1 + "strategy:" + ((Integer) obj) + " device:" + ((List) obj2));
                        break;
                    case 1:
                        printWriter.println("  " + this.f$1 + "strategy:" + ((Integer) obj) + " device:" + ((List) obj2));
                        break;
                    case 2:
                        printWriter.println("  " + this.f$1 + ((AudioDeviceInventory.DeviceInfo) obj2).toString());
                        break;
                    case 3:
                        printWriter.println("  " + this.f$1 + "capturePreset:" + ((Integer) obj) + " devices:" + ((List) obj2));
                        break;
                    case 4:
                        PrintWriter printWriter2 = printWriter;
                        Pair pair = (Pair) obj;
                        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "strategy: ");
                        m.append(pair.first);
                        m.append(" role:");
                        m.append(pair.second);
                        m.append(" devices:");
                        m.append((List) obj2);
                        printWriter2.println(m.toString());
                        break;
                    case 5:
                        PrintWriter printWriter3 = printWriter;
                        Pair pair2 = (Pair) obj;
                        StringBuilder m2 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "strategy: ");
                        m2.append(pair2.first);
                        m2.append(" role:");
                        m2.append(pair2.second);
                        m2.append(" devices:");
                        m2.append((List) obj2);
                        printWriter3.println(m2.toString());
                        break;
                    case 6:
                        PrintWriter printWriter4 = printWriter;
                        Pair pair3 = (Pair) obj;
                        StringBuilder m3 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "preset: ");
                        m3.append(pair3.first);
                        m3.append(" role:");
                        m3.append(pair3.second);
                        m3.append(" devices:");
                        m3.append((List) obj2);
                        printWriter4.println(m3.toString());
                        break;
                    default:
                        PrintWriter printWriter5 = printWriter;
                        Pair pair4 = (Pair) obj;
                        StringBuilder m4 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "preset: ");
                        m4.append(pair4.first);
                        m4.append(" role:");
                        m4.append(pair4.second);
                        m4.append(" devices:");
                        m4.append((List) obj2);
                        printWriter5.println(m4.toString());
                        break;
                }
            }
        });
        printWriter.println("\n  Applied devices roles for presets (from API):");
        final int i7 = 6;
        audioDeviceInventory.mAppliedPresetRoles.forEach(new BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda15
            public final /* synthetic */ String f$1 = "  ";

            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                switch (i7) {
                    case 0:
                        printWriter.println("  " + this.f$1 + "strategy:" + ((Integer) obj) + " device:" + ((List) obj2));
                        break;
                    case 1:
                        printWriter.println("  " + this.f$1 + "strategy:" + ((Integer) obj) + " device:" + ((List) obj2));
                        break;
                    case 2:
                        printWriter.println("  " + this.f$1 + ((AudioDeviceInventory.DeviceInfo) obj2).toString());
                        break;
                    case 3:
                        printWriter.println("  " + this.f$1 + "capturePreset:" + ((Integer) obj) + " devices:" + ((List) obj2));
                        break;
                    case 4:
                        PrintWriter printWriter2 = printWriter;
                        Pair pair = (Pair) obj;
                        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "strategy: ");
                        m.append(pair.first);
                        m.append(" role:");
                        m.append(pair.second);
                        m.append(" devices:");
                        m.append((List) obj2);
                        printWriter2.println(m.toString());
                        break;
                    case 5:
                        PrintWriter printWriter3 = printWriter;
                        Pair pair2 = (Pair) obj;
                        StringBuilder m2 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "strategy: ");
                        m2.append(pair2.first);
                        m2.append(" role:");
                        m2.append(pair2.second);
                        m2.append(" devices:");
                        m2.append((List) obj2);
                        printWriter3.println(m2.toString());
                        break;
                    case 6:
                        PrintWriter printWriter4 = printWriter;
                        Pair pair3 = (Pair) obj;
                        StringBuilder m3 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "preset: ");
                        m3.append(pair3.first);
                        m3.append(" role:");
                        m3.append(pair3.second);
                        m3.append(" devices:");
                        m3.append((List) obj2);
                        printWriter4.println(m3.toString());
                        break;
                    default:
                        PrintWriter printWriter5 = printWriter;
                        Pair pair4 = (Pair) obj;
                        StringBuilder m4 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "preset: ");
                        m4.append(pair4.first);
                        m4.append(" role:");
                        m4.append(pair4.second);
                        m4.append(" devices:");
                        m4.append((List) obj2);
                        printWriter5.println(m4.toString());
                        break;
                }
            }
        });
        printWriter.println("\n  Applied devices roles for presets (internal:");
        final int i8 = 7;
        audioDeviceInventory.mAppliedPresetRolesInt.forEach(new BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda15
            public final /* synthetic */ String f$1 = "  ";

            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                switch (i8) {
                    case 0:
                        printWriter.println("  " + this.f$1 + "strategy:" + ((Integer) obj) + " device:" + ((List) obj2));
                        break;
                    case 1:
                        printWriter.println("  " + this.f$1 + "strategy:" + ((Integer) obj) + " device:" + ((List) obj2));
                        break;
                    case 2:
                        printWriter.println("  " + this.f$1 + ((AudioDeviceInventory.DeviceInfo) obj2).toString());
                        break;
                    case 3:
                        printWriter.println("  " + this.f$1 + "capturePreset:" + ((Integer) obj) + " devices:" + ((List) obj2));
                        break;
                    case 4:
                        PrintWriter printWriter2 = printWriter;
                        Pair pair = (Pair) obj;
                        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "strategy: ");
                        m.append(pair.first);
                        m.append(" role:");
                        m.append(pair.second);
                        m.append(" devices:");
                        m.append((List) obj2);
                        printWriter2.println(m.toString());
                        break;
                    case 5:
                        PrintWriter printWriter3 = printWriter;
                        Pair pair2 = (Pair) obj;
                        StringBuilder m2 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "strategy: ");
                        m2.append(pair2.first);
                        m2.append(" role:");
                        m2.append(pair2.second);
                        m2.append(" devices:");
                        m2.append((List) obj2);
                        printWriter3.println(m2.toString());
                        break;
                    case 6:
                        PrintWriter printWriter4 = printWriter;
                        Pair pair3 = (Pair) obj;
                        StringBuilder m3 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "preset: ");
                        m3.append(pair3.first);
                        m3.append(" role:");
                        m3.append(pair3.second);
                        m3.append(" devices:");
                        m3.append((List) obj2);
                        printWriter4.println(m3.toString());
                        break;
                    default:
                        PrintWriter printWriter5 = printWriter;
                        Pair pair4 = (Pair) obj;
                        StringBuilder m4 = DumpUtils$$ExternalSyntheticOutline0.m("  ", this.f$1, "preset: ");
                        m4.append(pair4.first);
                        m4.append(" role:");
                        m4.append(pair4.second);
                        m4.append(" devices:");
                        m4.append((List) obj2);
                        printWriter5.println(m4.toString());
                        break;
                }
            }
        });
        printWriter.println("\ndevices:\n");
        synchronized (audioDeviceInventory.mDeviceInventoryLock) {
            try {
                Iterator it = audioDeviceInventory.mDeviceInventory.values().iterator();
                while (it.hasNext()) {
                    printWriter.println("\t" + ((AdiDeviceState) it.next()) + "\n");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        printWriter.println("\n  Communication route clients:");
        this.mCommunicationRouteClients.forEach(new Consumer() { // from class: com.android.server.audio.AudioDeviceBroker$$ExternalSyntheticLambda0
            public final /* synthetic */ String f$1 = "  ";

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                printWriter.println("  " + this.f$1 + ((AudioDeviceBroker.CommunicationRouteClient) obj).toString());
            }
        });
        printWriter.println("\n  Computed Preferred communication device: " + preferredCommunicationDevice());
        printWriter.println("\n  Applied Preferred communication device: " + this.mPreferredCommunicationDevice);
        StringBuilder sb = new StringBuilder("  Active communication device: ");
        sb.append((Object) (this.mActiveCommunicationDevice == null ? KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG : new AudioDeviceAttributes(this.mActiveCommunicationDevice)));
        printWriter.println(sb.toString());
        StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mCommunicationStrategyId: "), this.mCommunicationStrategyId, printWriter, "  mAccessibilityStrategyId: "), this.mAccessibilityStrategyId, printWriter, "\n  mAudioModeOwner: ");
        m.append(this.mAudioModeOwner);
        printWriter.println(m.toString());
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("\n  mScoManagedByAudio: "), this.mScoManagedByAudio, printWriter);
        BtHelper btHelper = this.mBtHelper;
        btHelper.getClass();
        printWriter.println("\n  mBluetoothHeadset: " + btHelper.mBluetoothHeadset);
        printWriter.println("  mBluetoothHeadsetDevice: " + btHelper.mBluetoothHeadsetDevice);
        BluetoothDevice bluetoothDevice = btHelper.mBluetoothHeadsetDevice;
        if (bluetoothDevice != null && (bluetoothClass = bluetoothDevice.getBluetoothClass()) != null) {
            StringBuilder sb2 = new StringBuilder("  mBluetoothHeadsetDevice.DeviceClass: ");
            int deviceClass = bluetoothClass.getDeviceClass();
            switch (deviceClass) {
                case 1024:
                    str = "AUDIO_VIDEO_UNCATEGORIZED";
                    break;
                case 1028:
                    str = "AUDIO_VIDEO_WEARABLE_HEADSET";
                    break;
                case 1032:
                    str = "AUDIO_VIDEO_HANDSFREE";
                    break;
                case 1036:
                    str = "AUDIO_VIDEO_RESERVED_0x040C";
                    break;
                case 1040:
                    str = "AUDIO_VIDEO_MICROPHONE";
                    break;
                case 1044:
                    str = "AUDIO_VIDEO_LOUDSPEAKER";
                    break;
                case 1048:
                    str = "AUDIO_VIDEO_HEADPHONES";
                    break;
                case 1052:
                    str = "AUDIO_VIDEO_PORTABLE_AUDIO";
                    break;
                case 1056:
                    str = "AUDIO_VIDEO_CAR_AUDIO";
                    break;
                case 1060:
                    str = "AUDIO_VIDEO_SET_TOP_BOX";
                    break;
                case 1064:
                    str = "AUDIO_VIDEO_HIFI_AUDIO";
                    break;
                case 1068:
                    str = "AUDIO_VIDEO_VCR";
                    break;
                case 1072:
                    str = "AUDIO_VIDEO_VIDEO_CAMERA";
                    break;
                case 1076:
                    str = "AUDIO_VIDEO_CAMCORDER";
                    break;
                case 1080:
                    str = "AUDIO_VIDEO_VIDEO_MONITOR";
                    break;
                case 1084:
                    str = "AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER";
                    break;
                case 1088:
                    str = "AUDIO_VIDEO_VIDEO_CONFERENCING";
                    break;
                case 1092:
                    str = "AUDIO_VIDEO_RESERVED_0x0444";
                    break;
                case 1096:
                    str = "AUDIO_VIDEO_VIDEO_GAMING_TOY";
                    break;
                default:
                    str = TextUtils.formatSimple("0x%04x", new Object[]{Integer.valueOf(deviceClass)});
                    break;
            }
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb2, str, printWriter);
        }
        StringBuilder sb3 = new StringBuilder("  mScoAudioState: ");
        int i9 = btHelper.mScoAudioState;
        StringBuilder m2 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, i9 != 0 ? i9 != 1 ? i9 != 2 ? i9 != 3 ? i9 != 5 ? BinaryTransparencyService$$ExternalSyntheticOutline0.m(i9, "SCO_STATE_(", ")") : "SCO_STATE_DEACTIVATING" : "SCO_STATE_ACTIVE_INTERNAL" : "SCO_STATE_ACTIVE_EXTERNAL" : "SCO_STATE_ACTIVATE_REQ" : "SCO_STATE_INACTIVE", "  mScoAudioMode: ", sb3);
        m2.append(BtHelper.scoAudioModeToString(btHelper.mScoAudioMode));
        printWriter.println(m2.toString());
        printWriter.println("\n  mHearingAid: " + btHelper.mHearingAid);
        printWriter.println("\n  mLeAudio: " + btHelper.mLeAudio);
        printWriter.println("  mA2dp: " + btHelper.mA2dp);
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("  mAvrcpAbsVolSupported: "), btHelper.mAvrcpAbsVolSupported, printWriter);
    }

    public final BluetoothA2dp getA2dp() {
        BluetoothA2dp bluetoothA2dp;
        synchronized (this.mDeviceStateLock) {
            try {
                BtHelper btHelper = this.mBtHelper;
                synchronized (btHelper) {
                    bluetoothA2dp = btHelper.mA2dp;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return bluetoothA2dp;
    }

    public final int getA2dpDeviceVolume(BluetoothDevice bluetoothDevice) {
        int intValue;
        DualA2dpVolumeManager dualA2dpVolumeManager = this.mDualA2dpManager;
        synchronized (dualA2dpVolumeManager.mConnectedDevicesVolume) {
            intValue = ((Integer) dualA2dpVolumeManager.mConnectedDevicesVolume.getOrDefault(bluetoothDevice, -1)).intValue();
        }
        return intValue;
    }

    public final String getAddressForDevice(int i) {
        BluetoothHeadset bluetoothHeadset;
        List<BluetoothDevice> connectedDevices;
        BluetoothA2dp a2dp;
        BluetoothDevice activeDevice;
        AudioDeviceInventory audioDeviceInventory = this.mDeviceInventory;
        audioDeviceInventory.getClass();
        if (AudioDeviceInfo.convertInternalDeviceToDeviceType(i) == 8 && (a2dp = audioDeviceInventory.mDeviceBroker.getA2dp()) != null && (activeDevice = a2dp.getActiveDevice()) != null) {
            return activeDevice.getAddress();
        }
        if (AudioDeviceInfo.convertInternalDeviceToDeviceType(i) == 7) {
            AudioDeviceBroker audioDeviceBroker = audioDeviceInventory.mDeviceBroker;
            synchronized (audioDeviceBroker.mDeviceStateLock) {
                BtHelper btHelper = audioDeviceBroker.mBtHelper;
                synchronized (btHelper) {
                    bluetoothHeadset = btHelper.mBluetoothHeadset;
                }
            }
            if (bluetoothHeadset != null && (connectedDevices = bluetoothHeadset.getConnectedDevices()) != null) {
                for (BluetoothDevice bluetoothDevice : connectedDevices) {
                    if (bluetoothHeadset.isAudioConnected(bluetoothDevice)) {
                        return bluetoothDevice.getAddress();
                    }
                }
            }
        }
        if (i == 32768) {
            if (((AudioDeviceInventory.DeviceInfo) audioDeviceInventory.mConnectedDevices.get(AudioDeviceInventory.DeviceInfo.makeDeviceListKey(i, "0"))) != null) {
                return "0";
            }
        }
        AudioDeviceInventory.DeviceInfo deviceInfo = (AudioDeviceInventory.DeviceInfo) audioDeviceInventory.mConnectedDevices.get(AudioDeviceInventory.DeviceInfo.makeDeviceListKey(i, ""));
        return deviceInfo != null ? deviceInfo.mDeviceAddress : "";
    }

    public final AudioDeviceInfo getCommunicationDevice() {
        AudioDeviceInfo communicationDeviceInt;
        synchronized (this.mCommunicationDeviceLock) {
            long currentTimeMillis = System.currentTimeMillis();
            long j = 0;
            while (true) {
                if (this.mCommunicationDeviceUpdateCount <= 0) {
                    break;
                }
                try {
                    this.mCommunicationDeviceLock.wait(3000 - j);
                } catch (InterruptedException unused) {
                    Log.w("AS.AudioDeviceBroker", "Interrupted while waiting for communication device update.");
                }
                j = System.currentTimeMillis() - currentTimeMillis;
                if (j >= 3000) {
                    Log.e("AS.AudioDeviceBroker", "Timeout waiting for communication device update.");
                    break;
                }
            }
        }
        synchronized (this.mDeviceStateLock) {
            communicationDeviceInt = getCommunicationDeviceInt();
        }
        return communicationDeviceInt;
    }

    public final AudioDeviceInfo getCommunicationDeviceInt() {
        updateActiveCommunicationDevice();
        AudioDeviceInfo audioDeviceInfo = this.mActiveCommunicationDevice;
        if (audioDeviceInfo != null && audioDeviceInfo.getType() == 13) {
            audioDeviceInfo = getCommunicationDeviceOfType(2);
        }
        if (audioDeviceInfo != null && isValidCommunicationDevice(audioDeviceInfo)) {
            return audioDeviceInfo;
        }
        AudioDeviceInfo communicationDeviceOfType = getCommunicationDeviceOfType(1);
        if (communicationDeviceOfType != null) {
            return communicationDeviceOfType;
        }
        ArrayList arrayList = (ArrayList) getAvailableCommunicationDevices();
        return !arrayList.isEmpty() ? (AudioDeviceInfo) arrayList.get(0) : communicationDeviceOfType;
    }

    public final CommunicationRouteClient getCommunicationRouteClientForUid(int i) {
        Iterator it = this.mCommunicationRouteClients.iterator();
        while (it.hasNext()) {
            CommunicationRouteClient communicationRouteClient = (CommunicationRouteClient) it.next();
            if (communicationRouteClient.mUid == i) {
                return communicationRouteClient;
            }
        }
        return null;
    }

    public final AudioDeviceAttributes getDefaultCommunicationDevice() {
        AudioModeInfo audioModeInfo = this.mAudioModeOwner;
        if (audioModeInfo.mMode != 3 || audioModeInfo.mUid == 1000) {
            return null;
        }
        AudioDeviceInventory audioDeviceInventory = this.mDeviceInventory;
        audioDeviceInventory.getClass();
        AudioDeviceInventory.DeviceInfo firstConnectedDeviceOfTypes = audioDeviceInventory.getFirstConnectedDeviceOfTypes(Sets.newHashSet(new Integer[]{134217728}));
        if (firstConnectedDeviceOfTypes == null) {
            return null;
        }
        return new AudioDeviceAttributes(firstConnectedDeviceOfTypes.mDeviceType, firstConnectedDeviceOfTypes.mDeviceAddress, firstConnectedDeviceOfTypes.mDeviceName);
    }

    public final Collection getImmutableDeviceInventory() {
        ArrayList arrayList;
        AudioDeviceInventory audioDeviceInventory = this.mDeviceInventory;
        synchronized (audioDeviceInventory.mDeviceInventoryLock) {
            arrayList = new ArrayList(audioDeviceInventory.mDeviceInventory.values());
        }
        return arrayList;
    }

    public final List getLeAudioGroupAddresses(int i) {
        BtHelper btHelper = this.mBtHelper;
        btHelper.getClass();
        ArrayList arrayList = new ArrayList();
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null && btHelper.mLeAudio != null) {
            for (BluetoothDevice bluetoothDevice : defaultAdapter.getActiveDevices(22)) {
                if (bluetoothDevice != null && btHelper.mLeAudio.getGroupId(bluetoothDevice) == i) {
                    arrayList.add(new Pair(bluetoothDevice.getAddress(), bluetoothDevice.getIdentityAddress()));
                }
            }
        }
        return arrayList;
    }

    public final int getPriorityDevice(int i) {
        int i2 = 2;
        AudioDeviceInventory audioDeviceInventory = this.mDeviceInventory;
        int[] iArr = {32768, 128, 8, 4, 67108864, 8192, EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION, 4096, 1024, 2048, 2};
        synchronized (audioDeviceInventory.mDevicesLock) {
            try {
                int i3 = 0;
                int i4 = 0;
                for (Map.Entry entry : audioDeviceInventory.mConnectedDevices.entrySet()) {
                    String str = (String) entry.getKey();
                    int i5 = ((AudioDeviceInventory.DeviceInfo) audioDeviceInventory.mConnectedDevices.get(str)).mDeviceType;
                    if ((Integer.MIN_VALUE & i5) == 0) {
                        i4 |= i5;
                    }
                }
                while (true) {
                    if (i3 >= 11) {
                        break;
                    }
                    int i6 = iArr[i3];
                    if (i != i6 && (i4 & i6) != 0) {
                        i2 = i6;
                        break;
                    }
                    i3++;
                }
            } finally {
            }
        }
        return i2;
    }

    public final boolean handleDeviceConnection(AudioDeviceAttributes audioDeviceAttributes, boolean z, BluetoothDevice bluetoothDevice) {
        boolean handleDeviceConnection;
        synchronized (this.mDeviceStateLock) {
            handleDeviceConnection = this.mDeviceInventory.handleDeviceConnection(audioDeviceAttributes, z, false, bluetoothDevice);
        }
        return handleDeviceConnection;
    }

    public final void handleFmRadioDeviceConnection() {
        synchronized (this.mDeviceStateLock) {
            AudioDeviceInventory audioDeviceInventory = this.mDeviceInventory;
            audioDeviceInventory.getClass();
            audioDeviceInventory.mAudioSystem.setDeviceConnectionState(new AudioDeviceAttributes(-2147475456, "dummy", ""), 1, 0);
        }
    }

    public final void initRoutingStrategyIds() {
        List<AudioProductStrategy> audioProductStrategies = AudioProductStrategy.getAudioProductStrategies();
        this.mCommunicationStrategyId = -1;
        this.mAccessibilityStrategyId = -1;
        for (AudioProductStrategy audioProductStrategy : audioProductStrategies) {
            if (this.mCommunicationStrategyId == -1 && audioProductStrategy.getAudioAttributesForLegacyStreamType(0) != null) {
                this.mCommunicationStrategyId = audioProductStrategy.getId();
            }
            if (this.mAccessibilityStrategyId == -1 && audioProductStrategy.getAudioAttributesForLegacyStreamType(10) != null) {
                this.mAccessibilityStrategyId = audioProductStrategy.getId();
            }
        }
    }

    public final boolean isDeviceActiveForCommunication(int i) {
        AudioDeviceAttributes audioDeviceAttributes;
        AudioDeviceInfo audioDeviceInfo = this.mActiveCommunicationDevice;
        return audioDeviceInfo != null && audioDeviceInfo.getType() == i && (audioDeviceAttributes = this.mPreferredCommunicationDevice) != null && audioDeviceAttributes.getType() == i;
    }

    public final boolean isDeviceConnected(AudioDeviceAttributes audioDeviceAttributes) {
        boolean z;
        synchronized (this.mDeviceStateLock) {
            AudioDeviceInventory audioDeviceInventory = this.mDeviceInventory;
            audioDeviceInventory.getClass();
            String makeDeviceListKey = AudioDeviceInventory.DeviceInfo.makeDeviceListKey(audioDeviceAttributes.getInternalType(), audioDeviceAttributes.getAddress());
            synchronized (audioDeviceInventory.mDevicesLock) {
                z = audioDeviceInventory.mConnectedDevices.get(makeDeviceListKey) != null;
            }
        }
        return z;
    }

    public final boolean isDeviceOnForCommunication(int i) {
        boolean z;
        synchronized (this.mDeviceStateLock) {
            try {
                AudioDeviceAttributes preferredCommunicationDevice = preferredCommunicationDevice();
                z = preferredCommunicationDevice != null && preferredCommunicationDevice.getType() == i;
            } finally {
            }
        }
        return z;
    }

    public final boolean isDeviceRequestedForCommunication(int i) {
        boolean z;
        synchronized (this.mDeviceStateLock) {
            try {
                AudioDeviceAttributes requestedCommunicationDevice = requestedCommunicationDevice();
                z = requestedCommunicationDevice != null && requestedCommunicationDevice.getType() == i;
            } finally {
            }
        }
        return z;
    }

    public final boolean isDualModeActive() {
        BluetoothDevice activeDevice;
        BtHelper btHelper = this.mBtHelper;
        synchronized (btHelper) {
            BluetoothA2dp bluetoothA2dp = btHelper.mA2dp;
            if (bluetoothA2dp == null) {
                activeDevice = null;
            } else {
                activeDevice = bluetoothA2dp.getActiveDevice();
            }
        }
        return activeDevice != null && activeDevice.isLeAudioDualMode();
    }

    public final boolean isInCommunication() {
        AudioService audioService = this.mAudioService;
        return audioService.isInCommunication() || audioService.mMode.get() == 1;
    }

    public final boolean isRemoteVolumeControlSupported() {
        boolean z;
        synchronized (this.mDeviceStateLock) {
            try {
                BtHelper btHelper = this.mBtHelper;
                synchronized (btHelper) {
                    BluetoothDevice bluetoothDevice = btHelper.mBluetoothHeadsetDevice;
                    if (bluetoothDevice != null) {
                        z = bluetoothDevice.getVolumeControlSupport();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public final boolean isSADevice(AdiDeviceState adiDeviceState) {
        return this.mAudioService.mSpatializerHelper.isSADevice(adiDeviceState);
    }

    public final boolean messageMutesMusic(int i) {
        if (i == 0) {
            return false;
        }
        if ((i != 7 && i != 29 && i != 11) || !AudioSystem.isStreamActive(3, 0)) {
            return true;
        }
        Set set = AudioDeviceInventory.DEVICE_OVERRIDE_A2DP_ROUTE_ON_PLUG_SET;
        Set deviceSetForStream = this.mAudioService.getDeviceSetForStream(3);
        Iterator it = ((HashSet) set).iterator();
        while (it.hasNext()) {
            if (deviceSetForStream.contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    public final void muteRingtoneDuringVibration() {
        AudioService audioService = this.mAudioService;
        if (audioService.mMode.get() == 1 && audioService.mRingerMode == 1) {
            Log.i("AS.AudioService", "SPK ringtone volume set to 0 !!!");
            AudioSystem.setStreamVolumeIndexAS(2, 0, 2);
        }
    }

    public final void onCheckCommunicationDeviceRemoval(AudioDeviceAttributes audioDeviceAttributes) {
        Log.v("AS.AudioDeviceBroker", "onCheckCommunicationDeviceRemoval device: " + audioDeviceAttributes.toString());
        Iterator it = this.mCommunicationRouteClients.iterator();
        while (it.hasNext()) {
            CommunicationRouteClient communicationRouteClient = (CommunicationRouteClient) it.next();
            if (audioDeviceAttributes.equals(communicationRouteClient.mDevice)) {
                Log.v("AS.AudioDeviceBroker", "onCheckCommunicationDeviceRemoval removing client: " + communicationRouteClient.toString());
                sendLMsgNoDelay(42, 2, new CommunicationDeviceInfo(communicationRouteClient.mCb, communicationRouteClient.mUid, audioDeviceAttributes, false, -1, "onCheckCommunicationDeviceRemoval", communicationRouteClient.mIsPrivileged));
            }
        }
    }

    public final void onPersistAudioDeviceSettings() {
        String sb;
        AudioDeviceInventory audioDeviceInventory = this.mDeviceInventory;
        synchronized (audioDeviceInventory.mDeviceInventoryLock) {
            try {
                StringBuilder sb2 = new StringBuilder(audioDeviceInventory.mDeviceInventory.size() * 39);
                Iterator it = audioDeviceInventory.mDeviceInventory.values().iterator();
                if (it.hasNext()) {
                    sb2.append(((AdiDeviceState) it.next()).toPersistableString());
                }
                while (it.hasNext()) {
                    sb2.append("|");
                    sb2.append(((AdiDeviceState) it.next()).toPersistableString());
                }
                sb = sb2.toString();
            } catch (Throwable th) {
                throw th;
            }
        }
        Log.v("AS.AudioDeviceBroker", "onPersistAudioDeviceSettings AdiDeviceState: " + sb);
        if (sb.equals(readDeviceSettings())) {
            return;
        }
        if (this.mAudioService.getSettings() == null) {
            Log.e("AS.AudioDeviceBroker", "No settings adapter when saving AdiDeviceState: ".concat(sb));
            return;
        }
        try {
            if (Settings.Secure.putStringForUser(this.mAudioService.mContentResolver, "audio_device_inventory", sb, -2)) {
                return;
            }
            Log.e("AS.AudioDeviceBroker", "error saving AdiDeviceState: " + sb);
        } catch (IllegalArgumentException e) {
            Log.e("AS.AudioDeviceBroker", "error saving AdiDeviceState: ".concat(sb), e);
        }
    }

    public final void onSetCommunicationDeviceForClient(CommunicationDeviceInfo communicationDeviceInfo) {
        Log.v("AS.AudioDeviceBroker", "onSetCommunicationDeviceForClient: " + communicationDeviceInfo);
        if (!communicationDeviceInfo.mOn) {
            CommunicationRouteClient communicationRouteClientForUid = getCommunicationRouteClientForUid(communicationDeviceInfo.mUid);
            if (communicationRouteClientForUid == null) {
                return;
            }
            AudioDeviceAttributes audioDeviceAttributes = communicationDeviceInfo.mDevice;
            if (audioDeviceAttributes != null && !audioDeviceAttributes.equals(communicationRouteClientForUid.mDevice)) {
                return;
            }
        }
        setCommunicationRouteForClient(communicationDeviceInfo.mCb, communicationDeviceInfo.mUid, communicationDeviceInfo.mOn ? communicationDeviceInfo.mDevice : null, communicationDeviceInfo.mScoAudioMode, communicationDeviceInfo.mIsPrivileged, communicationDeviceInfo.mEventSource);
    }

    public final void onSetForceUse(int i, int i2, String str, boolean z) {
        if (i == 1) {
            sendIILMsg(z ? 36 : 13, 1, 0, 0, null, 0);
        }
        AudioService.sForceUseLogger.enqueue(new AudioServiceEvents$ForceUseEvent(i, i2, str));
        new MediaMetrics.Item("audio.forceUse." + AudioSystem.forceUseUsageToString(i)).set(MediaMetrics.Property.EVENT, "onSetForceUse").set(MediaMetrics.Property.FORCE_USE_DUE_TO, str).set(MediaMetrics.Property.FORCE_USE_MODE, AudioSystem.forceUseConfigToString(i2)).record();
        Log.v("AS.AudioDeviceBroker", "onSetForceUse(useCase<" + i + ">, config<" + i2 + ">, fromA2dp<" + z + ">, eventSource<" + str + ">)");
        this.mAudioSystem.setForceUse(i, i2);
    }

    public final void postApplyVolumeOnDevice(int i, int i2, String str) {
        AudioService.sendMsg(this.mAudioService.mAudioHandler, 26, 2, 0, 0, new AudioService.DeviceVolumeUpdate(i, i2, str), 0);
    }

    public final void postCheckCommunicationDeviceRemoval(AudioDeviceAttributes audioDeviceAttributes) {
        int convertInternalDeviceToDeviceType = AudioDeviceInfo.convertInternalDeviceToDeviceType(audioDeviceAttributes.getInternalType());
        int[] iArr = VALID_COMMUNICATION_DEVICE_TYPES;
        for (int i = 0; i < 13; i++) {
            if (convertInternalDeviceToDeviceType == iArr[i]) {
                sendLMsgNoDelay(53, 2, audioDeviceAttributes);
                return;
            }
        }
    }

    public final void postPersistAudioDeviceSettings() {
        sendIILMsg(54, 0, 0, 0, null, 1000);
    }

    public final void postSetLeAudioVolumeIndex(int i, int i2, int i3) {
        sendLMsgNoDelay(46, 0, new BleVolumeInfo(i, i2, i3));
    }

    public final void postSetVolumeIndexForA2dpDevice(String str, boolean z) {
        int mainVolume = this.mDualA2dpManager.getMainVolume();
        if (mainVolume != -1) {
            if (z) {
                AudioSystem.setStreamVolumeIndexAS(3, 0, 128);
            }
            this.mAudioService.postSetVolumeIndexOnDevice(3, mainVolume, 128, str);
            Log.i("AS.AudioDeviceBroker", "update volume " + mainVolume + " from " + str);
        }
    }

    public final void postUpdatedAdiDeviceState(AdiDeviceState adiDeviceState, boolean z) {
        sendILMsgNoDelay(59, z ? 1 : 0, adiDeviceState);
    }

    public final AudioDeviceAttributes preferredCommunicationDevice() {
        boolean z;
        AudioDeviceAttributes audioDeviceAttributes;
        boolean isBluetoothScoOn = this.mBtHelper.isBluetoothScoOn();
        synchronized (this.mBluetoothAudioStateLock) {
            if (isBluetoothScoOn) {
                try {
                    z = this.mBluetoothScoOn;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        if (z) {
            BtHelper btHelper = this.mBtHelper;
            BluetoothDevice bluetoothDevice = btHelper.mBluetoothHeadsetDevice;
            if (bluetoothDevice == null) {
                audioDeviceAttributes = null;
            } else {
                audioDeviceAttributes = (AudioDeviceAttributes) ((HashMap) btHelper.mResolvedScoAudioDevices).get(bluetoothDevice);
                if (audioDeviceAttributes == null) {
                    audioDeviceAttributes = BtHelper.btHeadsetDeviceToAudioDevice(bluetoothDevice);
                }
            }
            if (audioDeviceAttributes != null) {
                return audioDeviceAttributes;
            }
        }
        AudioDeviceAttributes requestedCommunicationDevice = requestedCommunicationDevice();
        if (requestedCommunicationDevice == null || requestedCommunicationDevice.getType() == 7) {
            return null;
        }
        return requestedCommunicationDevice;
    }

    public final void queueOnBluetoothActiveDeviceChanged(BtDeviceChangedData btDeviceChangedData) {
        BluetoothDevice bluetoothDevice;
        BluetoothDevice bluetoothDevice2;
        if (btDeviceChangedData.mInfo.getProfile() == 2 && (bluetoothDevice2 = btDeviceChangedData.mPreviousDevice) != null && bluetoothDevice2.equals(btDeviceChangedData.mNewDevice)) {
            new MediaMetrics.Item("audio.device.queueOnBluetoothActiveDeviceChanged_update").set(MediaMetrics.Property.NAME, TextUtils.emptyIfNull(btDeviceChangedData.mNewDevice.getName())).set(MediaMetrics.Property.STATUS, Integer.valueOf(btDeviceChangedData.mInfo.getProfile())).record();
            synchronized (this.mDeviceStateLock) {
                Log.i("AS.AudioDeviceBroker", "queueOnBluetoothActiveDeviceChanged A2DP config change");
                sendLMsgNoDelay(11, 2, createBtDeviceInfo(btDeviceChangedData, btDeviceChangedData.mNewDevice, 2));
            }
            AudioService.mBtDeviceChangedData = btDeviceChangedData;
            return;
        }
        synchronized (this.mDeviceStateLock) {
            try {
                if (btDeviceChangedData.mInfo.getProfile() == 22 && (bluetoothDevice = btDeviceChangedData.mPreviousDevice) != null && bluetoothDevice.equals(btDeviceChangedData.mNewDevice)) {
                    btMediaMetricRecord(btDeviceChangedData.mNewDevice, "connected", btDeviceChangedData);
                    Log.i("AS.AudioDeviceBroker", "queueOnBluetoothActiveDeviceChanged BLE STATE_CONNECTED_IMPLICIT");
                    sendLMsgNoDelay(45, 2, createBtDeviceInfo(btDeviceChangedData, btDeviceChangedData.mNewDevice, 99));
                    return;
                }
                BluetoothDevice bluetoothDevice3 = btDeviceChangedData.mPreviousDevice;
                if (bluetoothDevice3 != null) {
                    btMediaMetricRecord(bluetoothDevice3, "disconnected", btDeviceChangedData);
                    sendLMsgNoDelay(45, 2, createBtDeviceInfo(btDeviceChangedData, btDeviceChangedData.mPreviousDevice, 0));
                }
                BluetoothDevice bluetoothDevice4 = btDeviceChangedData.mNewDevice;
                if (bluetoothDevice4 != null) {
                    btMediaMetricRecord(bluetoothDevice4, "connected", btDeviceChangedData);
                    sendLMsgNoDelay(45, 2, createBtDeviceInfo(btDeviceChangedData, btDeviceChangedData.mNewDevice, 2));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String readDeviceSettings() {
        AudioService audioService = this.mAudioService;
        SettingsAdapter settings = audioService.getSettings();
        ContentResolver contentResolver = audioService.mContentResolver;
        if (settings != null && contentResolver != null) {
            return Settings.Secure.getStringForUser(contentResolver, "audio_device_inventory", -2);
        }
        Log.e("AS.AudioDeviceBroker", "No settings adapter or content resolver to read device settings", new Exception("readDeviceSettings_NPE"));
        return "";
    }

    public final void reapplyAudioHalBluetoothState() {
        Log.v("AS.AudioDeviceBroker", "reapplyAudioHalBluetoothState() mBluetoothScoOnApplied: " + this.mBluetoothScoOnApplied + ", mBluetoothA2dpSuspendedApplied: " + this.mBluetoothA2dpSuspendedApplied + ", mBluetoothLeSuspendedApplied: " + this.mBluetoothLeSuspendedApplied);
        if (this.mBluetoothScoOnApplied) {
            AudioSystem.setParameters("A2dpSuspended=true");
            AudioSystem.setParameters("LeAudioSuspended=true");
            AudioSystem.setParameters("BT_SCO=on");
            return;
        }
        AudioSystem.setParameters("BT_SCO=off");
        if (this.mBluetoothA2dpSuspendedApplied) {
            AudioSystem.setParameters("A2dpSuspended=true");
        } else {
            AudioSystem.setParameters("A2dpSuspended=false");
        }
        if (this.mBluetoothLeSuspendedApplied) {
            AudioSystem.setParameters("LeAudioSuspended=true");
        } else {
            AudioSystem.setParameters("LeAudioSuspended=false");
        }
    }

    public final CommunicationRouteClient removeCommunicationRouteClient(IBinder iBinder) {
        Iterator it = this.mCommunicationRouteClients.iterator();
        while (it.hasNext()) {
            CommunicationRouteClient communicationRouteClient = (CommunicationRouteClient) it.next();
            if (communicationRouteClient.mCb == iBinder) {
                communicationRouteClient.unregisterDeathRecipient();
                CommunicationRouteClient communicationRouteClientForUid = getCommunicationRouteClientForUid(communicationRouteClient.mUid);
                if (communicationRouteClientForUid != null) {
                    this.mBrokerHandler.removeEqualMessages(56, communicationRouteClientForUid);
                }
                this.mCommunicationRouteClients.remove(communicationRouteClient);
                return communicationRouteClient;
            }
        }
        return null;
    }

    public final AudioDeviceAttributes requestedCommunicationDevice() {
        CommunicationRouteClient communicationRouteClient = topCommunicationRouteClient();
        AudioDeviceAttributes audioDeviceAttributes = communicationRouteClient != null ? communicationRouteClient.mDevice : null;
        Log.v("AS.AudioDeviceBroker", "requestedCommunicationDevice: " + audioDeviceAttributes + " mAudioModeOwner: " + this.mAudioModeOwner.toString());
        return audioDeviceAttributes;
    }

    public final void resetBtScoOnByApp() {
        AudioService audioService = this.mAudioService;
        if (audioService.mBtScoOnByApp) {
            audioService.mBtScoOnByApp = false;
            Log.i("AS.AudioService", "resetBtScoOnByApp");
        }
    }

    public final void sendIILMsg(int i, int i2, int i3, int i4, Object obj, int i5) {
        if (i2 == 0) {
            this.mBrokerHandler.removeMessages(i);
        } else if (i2 == 1 && this.mBrokerHandler.hasMessages(i)) {
            return;
        }
        if (isMessageHandledUnderWakelock(i)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    this.mBrokerEventWakeLock.acquire(5000L);
                } catch (Exception e) {
                    Log.e("AS.AudioDeviceBroker", "Exception acquiring wakelock", e);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        if (((HashSet) MESSAGES_MUTE_MUSIC).contains(Integer.valueOf(i))) {
            checkMessagesMuteMusic(i);
        }
        synchronized (sLastDeviceConnectionMsgTimeLock) {
            long uptimeMillis = SystemClock.uptimeMillis() + i5;
            if (i == 2 || i == 7 || i == 49 || i == 2760 || i == 10 || i == 11) {
                long j = sLastDeviceConnectMsgTime;
                if (j >= uptimeMillis) {
                    uptimeMillis = 30 + j;
                }
                sLastDeviceConnectMsgTime = uptimeMillis;
            }
            BrokerHandler brokerHandler = this.mBrokerHandler;
            brokerHandler.sendMessageAtTime(brokerHandler.obtainMessage(i, i3, i4, obj), uptimeMillis);
        }
    }

    public final void sendILMsgNoDelay(int i, int i2, Object obj) {
        sendIILMsg(i, 2, i2, 0, obj, 0);
    }

    public final void sendIMsgNoDelay(int i, int i2, int i3) {
        sendIILMsg(i, i2, i3, 0, null, 0);
    }

    public final void sendLMsgNoDelay(int i, int i2, Object obj) {
        sendIILMsg(i, i2, 0, 0, obj, 0);
    }

    public final void setA2dpSuspended(String str, boolean z, boolean z2) {
        synchronized (this.mBluetoothAudioStateLock) {
            try {
                Log.v("AS.AudioDeviceBroker", "setA2dpSuspended source: " + str + ", enable: " + z + ", internal: " + z2 + ", mBluetoothA2dpSuspendedInt: " + this.mBluetoothA2dpSuspendedInt + ", mBluetoothA2dpSuspendedExt: " + this.mBluetoothA2dpSuspendedExt);
                if (z2) {
                    this.mBluetoothA2dpSuspendedInt = z;
                } else {
                    this.mBluetoothA2dpSuspendedExt = z;
                }
                updateAudioHalBluetoothState();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setActiveBluetoothDevice(BluetoothDevice bluetoothDevice) {
        synchronized (this.mDeviceStateLock) {
            try {
                if (this.mDualA2dpManager.setActiveDevice(bluetoothDevice)) {
                    postSetVolumeIndexForA2dpDevice("setActiveBluetoothDevice", !updateAvrcpAbsoluteVolumeSupported());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setBluetoothA2dpOnInt(String str, boolean z, boolean z2) {
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("setBluetoothA2dpOn(", ") from u/pid:", z);
        m.append(Binder.getCallingUid());
        m.append("/");
        m.append(Binder.getCallingPid());
        m.append(" src:");
        m.append(str);
        String sb = m.toString();
        this.mBluetoothA2dpEnabled.set(z);
        onSetForceUse(1, z ? 0 : 10, sb, z2);
    }

    public final void setBluetoothScoOn(String str, boolean z) {
        Log.v("AS.AudioDeviceBroker", "setBluetoothScoOn: " + z + " " + str);
        boolean isDeviceRequestedForCommunication = isDeviceRequestedForCommunication(7);
        synchronized (this.mBluetoothAudioStateLock) {
            this.mBluetoothScoOn = z;
            updateAudioHalBluetoothState();
            sendILMsgNoDelay(43, isDeviceRequestedForCommunication ? 1 : 0, str);
            resetBtScoOnByApp();
        }
    }

    public final boolean setCommunicationDevice(IBinder iBinder, int i, AudioDeviceInfo audioDeviceInfo, boolean z, String str) {
        AudioDeviceInfo communicationDeviceOfType = (Rune.SEC_AUDIO_USB_HEADSET_CALL_SUPPORT || audioDeviceInfo == null || audioDeviceInfo.getType() != 22) ? audioDeviceInfo : getCommunicationDeviceOfType(1);
        Log.v("AS.AudioDeviceBroker", "setCommunicationDevice, device: " + communicationDeviceOfType + ", uid: " + i);
        synchronized (this.mDeviceStateLock) {
            if (communicationDeviceOfType == null) {
                try {
                    if (getCommunicationRouteClientForUid(i) == null) {
                        return false;
                    }
                } finally {
                }
            }
            synchronized (this.mCommunicationDeviceLock) {
                try {
                    this.mCommunicationDeviceUpdateCount++;
                    sendLMsgNoDelay(42, 2, new CommunicationDeviceInfo(iBinder, i, communicationDeviceOfType != null ? new AudioDeviceAttributes(communicationDeviceOfType) : null, communicationDeviceOfType != null, -1, str, z));
                } finally {
                }
            }
            return true;
        }
    }

    public final void setCommunicationRouteForClient(IBinder iBinder, int i, AudioDeviceAttributes audioDeviceAttributes, int i2, boolean z, String str) {
        boolean z2;
        AudioDeviceAttributes audioDeviceAttributes2;
        CommunicationRouteClient removeCommunicationRouteClient;
        boolean requestScoState;
        Log.v("AS.AudioDeviceBroker", "setCommunicationRouteForClient: device: " + audioDeviceAttributes + ", eventSource: " + str);
        EventLogger eventLogger = AudioService.sDeviceLogger;
        EventLogger.StringEvent stringEvent = new EventLogger.StringEvent("setCommunicationRouteForClient for uid: " + i + " device: " + audioDeviceAttributes + " isPrivileged: " + z + " from API: " + str);
        stringEvent.printLog(0, "AS.AudioDeviceBroker");
        eventLogger.enqueue(stringEvent);
        boolean isDeviceRequestedForCommunication = isDeviceRequestedForCommunication(7);
        CommunicationRouteClient communicationRouteClientForUid = getCommunicationRouteClientForUid(i);
        if (communicationRouteClientForUid != null) {
            audioDeviceAttributes2 = communicationRouteClientForUid.mDevice;
            z2 = communicationRouteClientForUid.mIsPrivileged;
        } else {
            z2 = false;
            audioDeviceAttributes2 = null;
        }
        if (audioDeviceAttributes != null) {
            if (audioDeviceAttributes.getType() == 7 && this.mBrokerHandler.hasMessages(2764)) {
                this.mBrokerHandler.removeMessages(2764);
                Log.d("AS.AudioDeviceBroker", "MSG_CHECK_ABNORMAL_SCO removeMessages when creating Sco client.");
            }
            removeCommunicationRouteClient = addCommunicationRouteClient(iBinder, i, audioDeviceAttributes, z);
            if (removeCommunicationRouteClient == null) {
                Log.w("AS.AudioDeviceBroker", "setCommunicationRouteForClient: could not add client for uid: " + i + " and device: " + audioDeviceAttributes);
            }
        } else {
            removeCommunicationRouteClient = removeCommunicationRouteClient(iBinder);
        }
        if (removeCommunicationRouteClient == null) {
            return;
        }
        if (audioDeviceAttributes != null && audioDeviceAttributes.getType() == 7 && str.contains("receiveBtEvent")) {
            int i3 = this.mRequestScoUid;
            if (i3 == 0 || i3 != i) {
                this.mRequestScoUid = i;
                this.mRequestScoCount = 0;
                this.mScoCb = iBinder;
            }
            this.mRequestScoCount++;
        } else {
            this.mRequestScoUid = 0;
            this.mRequestScoCount = 0;
            this.mScoCb = null;
        }
        if (!this.mScoManagedByAudio) {
            boolean isDeviceRequestedForCommunication2 = isDeviceRequestedForCommunication(7);
            if (isDeviceRequestedForCommunication2 && (!isDeviceRequestedForCommunication || !isDeviceActiveForCommunication(7))) {
                BtHelper btHelper = this.mBtHelper;
                synchronized (btHelper) {
                    eventLogger.enqueue(new EventLogger.StringEvent(str));
                    requestScoState = btHelper.requestScoState(12, i2);
                }
                if (!requestScoState) {
                    NetworkScoreService$$ExternalSyntheticOutline0.m(i, "setCommunicationRouteForClient: failure to start BT SCO for uid: ", "AS.AudioDeviceBroker");
                    if (audioDeviceAttributes2 != null) {
                        addCommunicationRouteClient(iBinder, i, audioDeviceAttributes2, z2);
                    } else {
                        removeCommunicationRouteClient(iBinder);
                    }
                    sendIMsgNoDelay(3, 2, 0);
                }
            } else if (!isDeviceRequestedForCommunication2 && isDeviceRequestedForCommunication) {
                this.mBtHelper.stopBluetoothSco(str);
            }
        }
        if ((isDeviceRequestedForCommunication(26) || isDeviceRequestedForCommunication(27)) && audioDeviceAttributes != null) {
            int bluetoothContextualVolumeStream = this.mAudioService.getBluetoothContextualVolumeStream();
            int vssVolumeForDevice = this.mAudioService.getVssVolumeForDevice(bluetoothContextualVolumeStream, audioDeviceAttributes.getInternalType());
            int i4 = this.mAudioService.mStreamStates[bluetoothContextualVolumeStream].mIndexMax;
            Log.v("AS.AudioDeviceBroker", "setCommunicationRouteForClient restoring LE Audio device volume lvl.");
            postSetLeAudioVolumeIndex(vssVolumeForDevice, i4, bluetoothContextualVolumeStream);
        }
        updateCommunicationRoute(str);
    }

    public final void setDualA2dpMode(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothA2dp a2dp;
        synchronized (this.mDeviceStateLock) {
            try {
                this.mDualA2dpManager.setDualA2dpMode(bluetoothDevice, z);
            } catch (Throwable th) {
                throw th;
            }
            if (Rune.SEC_AUDIO_BT_VOLUME_MONITOR && (a2dp = getA2dp()) != null) {
                List<BluetoothDevice> connectedDevices = a2dp.getConnectedDevices();
                if (!connectedDevices.isEmpty()) {
                    boolean z2 = true;
                    if (z) {
                        Iterator<BluetoothDevice> it = connectedDevices.iterator();
                        while (it.hasNext()) {
                            if (it.next().semGetAudioType() == 2) {
                                break;
                            }
                        }
                        z2 = false;
                        BtUtils.setBtVolumeMonitor(z2);
                    } else {
                        BluetoothDevice activeDevice = a2dp.getActiveDevice();
                        Iterator<BluetoothDevice> it2 = connectedDevices.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            BluetoothDevice next = it2.next();
                            if (next.equals(activeDevice)) {
                                if (next.semGetAudioType() == 2) {
                                }
                            }
                        }
                        z2 = false;
                        BtUtils.setBtVolumeMonitor(z2);
                    }
                    throw th;
                }
            }
        }
        postSetVolumeIndexForA2dpDevice("setDualA2dpMode", false);
    }

    public final void setForceCommunicationClientStateAndDelayedCheck(CommunicationRouteClient communicationRouteClient, boolean z, boolean z2) {
        if (z) {
            communicationRouteClient.mPlaybackActive = true;
        }
        if (z2) {
            communicationRouteClient.mRecordingActive = true;
        }
        int i = communicationRouteClient.mUid;
        boolean isActive = communicationRouteClient.isActive();
        CommunicationRouteClient communicationRouteClientForUid = getCommunicationRouteClientForUid(i);
        if (communicationRouteClientForUid != null) {
            this.mBrokerHandler.removeEqualMessages(56, communicationRouteClientForUid);
            long uptimeMillis = SystemClock.uptimeMillis() + 6000;
            BrokerHandler brokerHandler = this.mBrokerHandler;
            brokerHandler.sendMessageAtTime(brokerHandler.obtainMessage(56, i, isActive ? 1 : 0, communicationRouteClientForUid), uptimeMillis);
        }
    }

    public final void setForceUse_Async(int i, int i2, String str) {
        sendIILMsg(4, 2, i, i2, str, 0);
    }

    public final void setLeAudioSuspended(String str, boolean z, boolean z2) {
        synchronized (this.mBluetoothAudioStateLock) {
            try {
                Log.v("AS.AudioDeviceBroker", "setLeAudioSuspended source: " + str + ", enable: " + z + ", internal: " + z2 + ", mBluetoothLeSuspendedInt: " + this.mBluetoothA2dpSuspendedInt + ", mBluetoothLeSuspendedExt: " + this.mBluetoothA2dpSuspendedExt);
                if (z2) {
                    this.mBluetoothLeSuspendedInt = z;
                } else {
                    this.mBluetoothLeSuspendedExt = z;
                }
                updateAudioHalBluetoothState();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setSpeakerphoneOn(IBinder iBinder, int i, boolean z, boolean z2, String str) {
        Log.v("AS.AudioDeviceBroker", "setSpeakerphoneOn, on: " + z + " uid: " + i);
        sendLMsgNoDelay(42, 2, new CommunicationDeviceInfo(iBinder, i, new AudioDeviceAttributes(2, ""), z, -1, str, z2));
    }

    public final CommunicationRouteClient topCommunicationRouteClient() {
        Iterator it = this.mCommunicationRouteClients.iterator();
        while (it.hasNext()) {
            CommunicationRouteClient communicationRouteClient = (CommunicationRouteClient) it.next();
            if (communicationRouteClient.mUid == this.mAudioModeOwner.mUid) {
                return communicationRouteClient;
            }
        }
        if (!this.mCommunicationRouteClients.isEmpty() && this.mAudioModeOwner.mPid == 0 && ((CommunicationRouteClient) this.mCommunicationRouteClients.get(0)).isActive()) {
            return (CommunicationRouteClient) this.mCommunicationRouteClients.get(0);
        }
        return null;
    }

    public final void updateActiveCommunicationDevice() {
        AudioDeviceAttributes preferredCommunicationDevice = preferredCommunicationDevice();
        if (preferredCommunicationDevice == null) {
            ArrayList devicesForAttributes = this.mAudioSystem.getDevicesForAttributes(AudioProductStrategy.getAudioAttributesForStrategyWithLegacyStreamType(0), false);
            if (devicesForAttributes.isEmpty()) {
                if (this.mAudioService.mPlatformType == 1) {
                    Log.w("AS.AudioDeviceBroker", "updateActiveCommunicationDevice(): no device for phone strategy");
                }
                this.mActiveCommunicationDevice = null;
                return;
            }
            preferredCommunicationDevice = (AudioDeviceAttributes) devicesForAttributes.get(0);
        }
        this.mActiveCommunicationDevice = AudioManager.getDeviceInfoFromTypeAndAddress(preferredCommunicationDevice.getType(), preferredCommunicationDevice.getAddress());
    }

    public final void updateAudioHalBluetoothState() {
        boolean z = true;
        if (this.mBluetoothScoOn != this.mBluetoothScoOnApplied) {
            Log.v("AS.AudioDeviceBroker", "updateAudioHalBluetoothState() mBluetoothScoOn: " + this.mBluetoothScoOn + ", mBluetoothScoOnApplied: " + this.mBluetoothScoOnApplied);
            if (this.mBluetoothScoOn) {
                if (!this.mBluetoothA2dpSuspendedApplied) {
                    AudioSystem.setParameters("A2dpSuspended=true");
                    this.mBluetoothA2dpSuspendedApplied = true;
                }
                if (!this.mBluetoothLeSuspendedApplied) {
                    AudioSystem.setParameters("LeAudioSuspended=true");
                    this.mBluetoothLeSuspendedApplied = true;
                }
                if (this.mBtHelper.isBluetoothScoOn()) {
                    AudioSystem.setParameters("BT_SCO=on");
                } else {
                    AudioSystem.setParameters("BT_SCO=off");
                }
            } else {
                AudioSystem.setParameters("BT_SCO=off");
            }
            this.mBluetoothScoOnApplied = this.mBluetoothScoOn;
        }
        if (this.mBluetoothScoOnApplied) {
            return;
        }
        if ((this.mBluetoothA2dpSuspendedExt || this.mBluetoothA2dpSuspendedInt) != this.mBluetoothA2dpSuspendedApplied) {
            Log.v("AS.AudioDeviceBroker", "updateAudioHalBluetoothState() mBluetoothA2dpSuspendedExt: " + this.mBluetoothA2dpSuspendedExt + ", mBluetoothA2dpSuspendedInt: " + this.mBluetoothA2dpSuspendedInt + ", mBluetoothA2dpSuspendedApplied: " + this.mBluetoothA2dpSuspendedApplied);
            boolean z2 = this.mBluetoothA2dpSuspendedExt || this.mBluetoothA2dpSuspendedInt;
            this.mBluetoothA2dpSuspendedApplied = z2;
            if (z2) {
                AudioSystem.setParameters("A2dpSuspended=true");
            } else {
                AudioSystem.setParameters("A2dpSuspended=false");
            }
        }
        if ((this.mBluetoothLeSuspendedExt || this.mBluetoothLeSuspendedInt) != this.mBluetoothLeSuspendedApplied) {
            Log.v("AS.AudioDeviceBroker", "updateAudioHalBluetoothState() mBluetoothLeSuspendedExt: " + this.mBluetoothLeSuspendedExt + ", mBluetoothLeSuspendedInt: " + this.mBluetoothLeSuspendedInt + ", mBluetoothLeSuspendedApplied: " + this.mBluetoothLeSuspendedApplied);
            if (!this.mBluetoothLeSuspendedExt && !this.mBluetoothLeSuspendedInt) {
                z = false;
            }
            this.mBluetoothLeSuspendedApplied = z;
            if (z) {
                AudioSystem.setParameters("LeAudioSuspended=true");
            } else {
                AudioSystem.setParameters("LeAudioSuspended=false");
            }
        }
    }

    public final boolean updateAvrcpAbsoluteVolumeSupported() {
        boolean isAvrcpAbsoluteVolumeSupportedForActiveDevice = this.mDualA2dpManager.isAvrcpAbsoluteVolumeSupportedForActiveDevice();
        this.mBtHelper.setAvrcpAbsoluteVolumeSupported(isAvrcpAbsoluteVolumeSupportedForActiveDevice);
        AudioService audioService = this.mAudioService;
        if (audioService.mAvrcpAbsVolSupported != isAvrcpAbsoluteVolumeSupportedForActiveDevice) {
            AudioService.sVolumeLogger.enqueue(new EventLogger.StringEvent("a2dp AVC : " + isAvrcpAbsoluteVolumeSupportedForActiveDevice));
        }
        audioService.mAvrcpAbsVolSupported = isAvrcpAbsoluteVolumeSupportedForActiveDevice;
        return isAvrcpAbsoluteVolumeSupportedForActiveDevice;
    }

    public final void updateBluetoothA2dpDeviceConfigChange(BtDeviceChangedData btDeviceChangedData) {
        synchronized (this.mDeviceStateLock) {
            sendLMsgNoDelay(11, 2, createBtDeviceInfo(btDeviceChangedData, btDeviceChangedData.mNewDevice, 2));
        }
    }

    public final void updateCommunicationRoute(String str) {
        AudioDeviceAttributes audioDeviceAttributes;
        String str2;
        AudioDeviceAttributes preferredCommunicationDevice = preferredCommunicationDevice();
        Log.v("AS.AudioDeviceBroker", "updateCommunicationRoute, preferredCommunicationDevice: " + preferredCommunicationDevice + " eventSource: " + str);
        AudioService.sDeviceLogger.enqueue(new EventLogger.StringEvent("updateCommunicationRoute, preferredCommunicationDevice: " + preferredCommunicationDevice + " eventSource: " + str));
        boolean booleanValue = this.mReceiverSupported.booleanValue();
        AudioDeviceInventory audioDeviceInventory = this.mDeviceInventory;
        if (booleanValue) {
            if (preferredCommunicationDevice == null) {
                AudioDeviceAttributes defaultCommunicationDevice = getDefaultCommunicationDevice();
                if (defaultCommunicationDevice == null || isDeviceRequestedForCommunication(7)) {
                    audioDeviceInventory.clearDevicesRoleForStrategy(this.mCommunicationStrategyId, true);
                    audioDeviceInventory.clearDevicesRoleForStrategy(this.mAccessibilityStrategyId, true);
                } else {
                    Log.i("AS.AudioDeviceBroker", "updateCommunicationRoute set default Device = " + defaultCommunicationDevice.getType());
                    audioDeviceInventory.setDevicesRoleForStrategy(this.mCommunicationStrategyId, true, Arrays.asList(defaultCommunicationDevice));
                    audioDeviceInventory.setDevicesRoleForStrategy(this.mAccessibilityStrategyId, true, Arrays.asList(defaultCommunicationDevice));
                }
                synchronized (audioDeviceInventory.mDevicesLock) {
                    audioDeviceInventory.applyConnectedDevicesRoles_l();
                }
                audioDeviceInventory.reapplyExternalDevicesRoles();
            } else {
                if (this.mAudioModeOwner.mUid == 1000 && (audioDeviceAttributes = this.mPreferredCommunicationDevice) != null && audioDeviceAttributes.getType() == 2 && preferredCommunicationDevice.getType() == 7) {
                    Iterator it = this.mCommunicationRouteClients.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        CommunicationRouteClient communicationRouteClient = (CommunicationRouteClient) it.next();
                        if (communicationRouteClient.mDevice.getType() == 2 && communicationRouteClient.mUid == 1000) {
                            this.mCommunicationRouteClients.remove(communicationRouteClient);
                            Log.i("AS.AudioDeviceBroker", "updateCommunicationRoute removed SPK client");
                            break;
                        }
                    }
                }
                audioDeviceInventory.setDevicesRoleForStrategy(this.mCommunicationStrategyId, true, Arrays.asList(preferredCommunicationDevice));
                audioDeviceInventory.setDevicesRoleForStrategy(this.mAccessibilityStrategyId, true, Arrays.asList(preferredCommunicationDevice));
            }
            sendIILMsg(2763, 0, 0, 0, null, 500);
        } else if (preferredCommunicationDevice == null) {
            this.mCurrentCallDevice = null;
            AudioDeviceAttributes defaultCommunicationDevice2 = getDefaultCommunicationDevice();
            if (defaultCommunicationDevice2 == null || isDeviceRequestedForCommunication(7)) {
                audioDeviceInventory.clearDevicesRoleForStrategy(this.mCommunicationStrategyId, true);
            } else {
                Log.i("AS.AudioDeviceBroker", "updateCommunicationRoute set default Device = " + defaultCommunicationDevice2.getType());
                audioDeviceInventory.setDevicesRoleForStrategy(this.mCommunicationStrategyId, true, Arrays.asList(defaultCommunicationDevice2));
            }
            synchronized (audioDeviceInventory.mDevicesLock) {
                audioDeviceInventory.applyConnectedDevicesRoles_l();
            }
            audioDeviceInventory.reapplyExternalDevicesRoles();
        } else {
            this.mCurrentCallDevice = preferredCommunicationDevice;
            Log.i("AS.AudioDeviceBroker", "updateCommunicationRoute return already mCurrentCallDevice = " + this.mCurrentCallDevice);
            audioDeviceInventory.setDevicesRoleForStrategy(this.mCommunicationStrategyId, true, Arrays.asList(preferredCommunicationDevice));
        }
        boolean isDeviceActiveForCommunication = isDeviceActiveForCommunication(2);
        this.mPreferredCommunicationDevice = preferredCommunicationDevice;
        updateActiveCommunicationDevice();
        boolean z = Rune.SEC_AUDIO_MIC_MODE_FOR_QUICK_PANEL_UI;
        AudioService audioService = this.mAudioService;
        if (z) {
            AudioDeviceAttributes audioDeviceAttributes2 = this.mPreferredCommunicationDevice;
            if (audioDeviceAttributes2 != null) {
                int type = audioDeviceAttributes2.getType();
                MicModeManager micModeManager = audioService.mMicModeManager;
                micModeManager.mCurCallDevice = type;
                Log.i("MicModeManager", "setCommunicationDevice() deviceType: " + type);
                micModeManager.updateAudioDevice();
            } else {
                AudioDeviceInfo audioDeviceInfo = this.mActiveCommunicationDevice;
                if (audioDeviceInfo != null) {
                    int type2 = audioDeviceInfo.getType();
                    MicModeManager micModeManager2 = audioService.mMicModeManager;
                    micModeManager2.mCurCallDevice = type2;
                    Log.i("MicModeManager", "setCommunicationDevice() deviceType: " + type2);
                    micModeManager2.updateAudioDevice();
                }
            }
        }
        if (isDeviceActiveForCommunication != isDeviceActiveForCommunication(2)) {
            try {
                Intent flags = new Intent("android.media.action.SPEAKERPHONE_STATE_CHANGED").setFlags(1073741824);
                if (this.mAudioModeOwner.mPid == 0) {
                    str2 = this.mModeRequesterPackage;
                } else {
                    AudioService.SetModeDeathHandler audioModeOwnerHandler = audioService.getAudioModeOwnerHandler();
                    str2 = audioModeOwnerHandler != null ? audioModeOwnerHandler.mPackage : "";
                }
                flags.putExtra("modeOwner", str2);
                this.mContext.sendBroadcastAsUser(flags, UserHandle.ALL);
            } catch (Exception e) {
                Log.w("AS.AudioDeviceBroker", "failed to broadcast ACTION_SPEAKERPHONE_STATE_CHANGED: " + e);
            }
        }
        AudioService.sendMsg(audioService.mAudioHandler, 25, 2, 0, 0, null, 0);
        AudioDeviceInfo communicationDeviceInt = getCommunicationDeviceInt();
        int id = communicationDeviceInt != null ? communicationDeviceInt.getId() : 0;
        if (id == this.mCurCommunicationPortId) {
            return;
        }
        this.mCurCommunicationPortId = id;
        int beginBroadcast = this.mCommDevDispatchers.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mCommDevDispatchers.getBroadcastItem(i).dispatchCommunicationDeviceChanged(id);
            } catch (RemoteException e2) {
                Log.e("AS.AudioDeviceBroker", "dispatchCommunicationDevice error", e2);
            }
        }
        this.mCommDevDispatchers.finishBroadcast();
    }

    public final void updateCommunicationRouteClientState(CommunicationRouteClient communicationRouteClient, boolean z) {
        boolean isDeviceRequestedForCommunication = isDeviceRequestedForCommunication(7);
        int i = communicationRouteClient.mUid;
        AudioService audioService = this.mAudioService;
        communicationRouteClient.mPlaybackActive = audioService.isPlaybackActiveForUid(i);
        communicationRouteClient.mRecordingActive = audioService.mRecordMonitor.isRecordingActiveForUid(communicationRouteClient.mUid);
        if (z != communicationRouteClient.isActive()) {
            sendILMsgNoDelay(43, isDeviceRequestedForCommunication ? 1 : 0, "updateCommunicationRouteClientState");
        }
    }

    public final void updateCommunicationRouteClientsActivity(List list, List list2) {
        boolean z;
        synchronized (this.mSetModeLock) {
            synchronized (this.mDeviceStateLock) {
                try {
                    Iterator it = this.mCommunicationRouteClients.iterator();
                    while (it.hasNext()) {
                        CommunicationRouteClient communicationRouteClient = (CommunicationRouteClient) it.next();
                        boolean isActive = communicationRouteClient.isActive();
                        if (list != null) {
                            communicationRouteClient.mPlaybackActive = false;
                            Iterator it2 = list.iterator();
                            while (it2.hasNext()) {
                                AudioPlaybackConfiguration audioPlaybackConfiguration = (AudioPlaybackConfiguration) it2.next();
                                if (audioPlaybackConfiguration.getClientUid() == communicationRouteClient.mUid && audioPlaybackConfiguration.isActive()) {
                                    communicationRouteClient.mPlaybackActive = true;
                                    z = true;
                                    break;
                                }
                            }
                        }
                        z = false;
                        if (list2 != null) {
                            communicationRouteClient.mRecordingActive = false;
                            Iterator it3 = list2.iterator();
                            while (true) {
                                if (!it3.hasNext()) {
                                    break;
                                }
                                AudioRecordingConfiguration audioRecordingConfiguration = (AudioRecordingConfiguration) it3.next();
                                if (audioRecordingConfiguration.getClientUid() == communicationRouteClient.mUid && !audioRecordingConfiguration.isClientSilenced()) {
                                    communicationRouteClient.mRecordingActive = true;
                                    z = true;
                                    break;
                                }
                            }
                        }
                        if (z) {
                            CommunicationRouteClient communicationRouteClientForUid = getCommunicationRouteClientForUid(communicationRouteClient.mUid);
                            if (communicationRouteClientForUid != null) {
                                this.mBrokerHandler.removeEqualMessages(56, communicationRouteClientForUid);
                            }
                            updateCommunicationRouteClientState(communicationRouteClient, isActive);
                        } else if (isActive) {
                            setForceCommunicationClientStateAndDelayedCheck(communicationRouteClient, list != null, list2 != null);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void updateDeviceQuickConnection(int i, String str, String str2, boolean z) {
        synchronized (this.mDeviceStateLock) {
            AudioDeviceInventory audioDeviceInventory = this.mDeviceInventory;
            audioDeviceInventory.getClass();
            String makeDeviceListKey = AudioDeviceInventory.DeviceInfo.makeDeviceListKey(i, str);
            AudioDeviceInventory.DeviceInfo deviceInfo = new AudioDeviceInventory.DeviceInfo(i, 0, str2, str, null);
            if (z) {
                audioDeviceInventory.mConnectedDevices.put(makeDeviceListKey, deviceInfo);
            } else {
                audioDeviceInventory.mConnectedDevices.remove(makeDeviceListKey);
            }
        }
    }

    public final void updateIndividualA2dpVolumes(int i) {
        DualA2dpVolumeManager dualA2dpVolumeManager = this.mDualA2dpManager;
        synchronized (dualA2dpVolumeManager.mConnectedDevicesVolume) {
            try {
                dualA2dpVolumeManager.mVolumeLogger.enqueue(new EventLogger.StringEvent("updateVolume:" + i + ",main:" + dualA2dpVolumeManager.mMainVolume));
                if (!dualA2dpVolumeManager.mDualModeEnabled) {
                    BluetoothDevice bluetoothDevice = dualA2dpVolumeManager.mActiveDevice;
                    if (bluetoothDevice != null) {
                        dualA2dpVolumeManager.setVolume(bluetoothDevice, i);
                        dualA2dpVolumeManager.updateMainVolume(i);
                    }
                    return;
                }
                int i2 = i - dualA2dpVolumeManager.mMainVolume;
                dualA2dpVolumeManager.mMainVolume = -1;
                dualA2dpVolumeManager.mMinVolume = dualA2dpVolumeManager.MAX_VOLUME;
                int i3 = 0;
                for (int i4 = 0; i4 < dualA2dpVolumeManager.mConnectedDevicesVolume.size(); i4++) {
                    int intValue = ((Integer) dualA2dpVolumeManager.mConnectedDevicesVolume.valueAt(i4)).intValue();
                    if (intValue == dualA2dpVolumeManager.MAX_VOLUME) {
                        i3++;
                    }
                    int i5 = intValue + i2;
                    dualA2dpVolumeManager.setVolume((BluetoothDevice) dualA2dpVolumeManager.mConnectedDevicesVolume.keyAt(i4), i5);
                    if (dualA2dpVolumeManager.mMainVolume < i5) {
                        dualA2dpVolumeManager.mMainVolume = i5;
                    }
                    if (dualA2dpVolumeManager.mMinVolume > i5) {
                        dualA2dpVolumeManager.mMinVolume = i5;
                    }
                    dualA2dpVolumeManager.mMainVolume = dualA2dpVolumeManager.getValidIndex(dualA2dpVolumeManager.mMainVolume);
                    dualA2dpVolumeManager.mMinVolume = dualA2dpVolumeManager.getValidIndex(dualA2dpVolumeManager.mMinVolume);
                }
                dualA2dpVolumeManager.needVolumeChangedIntent = i3 != dualA2dpVolumeManager.mConnectedDevicesVolume.size();
            } finally {
            }
        }
    }

    public final void updateLeAudioVolume(int i) {
        AudioService audioService = this.mAudioService;
        int activeStreamType = audioService.getActiveStreamType(Integer.MIN_VALUE);
        int i2 = AudioService.mStreamVolumeAlias[activeStreamType];
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, activeStreamType, "updateLeAudioVolume: device = ", "streamType = ", ", streamAlias = ");
        m.append(i2);
        Log.v("AS.AudioService", m.toString());
        int index = audioService.mStreamStates[i2].getIndex(i);
        int i3 = audioService.mStreamStates[i2].mIndexMax;
        audioService.setStreamVolumeInt("AS.AudioService", i2, index, i, true, true);
        audioService.updateStreamVolumeAlias("AS.AudioService", true);
        audioService.setLeAudioVolumeOnModeUpdate(audioService.mMode.get(), i, i2, index, i3);
    }
}
