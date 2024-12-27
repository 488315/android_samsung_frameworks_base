package com.android.server.audio;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioFormat;
import android.media.AudioSystem;
import android.media.INativeSpatializerCallback;
import android.media.ISpatializer;
import android.media.ISpatializerHeadTrackingCallback;
import android.media.MediaMetrics;
import android.media.Spatializer;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.SparseIntArray;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

public final class SpatializerHelper {
    public static final AudioAttributes DEFAULT_ATTRIBUTES;
    public static final AudioFormat DEFAULT_FORMAT;
    public static final AnonymousClass1 SPAT_MODE_FOR_DEVICE_TYPE;
    public static ArrayList sRoutingDevices;
    public final AudioSystemAdapter mASA;
    public final AudioService mAudioService;
    boolean mBinauralEnabledDefault;
    public final AudioDeviceBroker mDeviceBroker;
    public HelperDynamicSensorCallback mDynSensorCallback;
    boolean mHeadTrackingEnabledDefault;
    public SensorManager mSensorManager;
    public ISpatializer mSpat;
    boolean mTransauralEnabledDefault;
    public int mState = 0;
    public boolean mFeatureEnabled = false;
    public int mSpatLevel = 0;
    public int mCapableSpatLevel = 0;
    public boolean mTransauralSupported = false;
    public boolean mBinauralSupported = false;
    public boolean mIsHeadTrackingSupported = false;
    public int[] mSupportedHeadTrackingModes = new int[0];
    public int mActualHeadTrackingMode = -2;
    public int mDesiredHeadTrackingMode = 1;
    public boolean mHeadTrackerAvailable = false;
    public int mDesiredHeadTrackingModeWhenEnabled = 1;
    public int mSpatOutput = 0;
    public final SpatializerHeadTrackingCallback mSpatHeadTrackingCallback = new SpatializerHeadTrackingCallback();
    public final ArrayList mSACapableDeviceTypes = new ArrayList(0);
    public final RemoteCallbackList mStateCallbacks = new RemoteCallbackList();
    public final RemoteCallbackList mHeadTrackingModeCallbacks = new RemoteCallbackList();
    public final RemoteCallbackList mHeadTrackerCallbacks = new RemoteCallbackList();
    public final RemoteCallbackList mHeadPoseCallbacks = new RemoteCallbackList();
    public final RemoteCallbackList mOutputCallbacks = new RemoteCallbackList();
    public boolean mSecHeadTrackerAvailable = false;
    public boolean mGlobalHeadTrackerAvailable = false;

    /* renamed from: com.android.server.audio.SpatializerHelper$1, reason: invalid class name */
    public final class AnonymousClass1 extends SparseIntArray {
    }

    public final class HelperDynamicSensorCallback extends SensorManager.DynamicSensorCallback {
        public HelperDynamicSensorCallback() {
        }

        @Override // android.hardware.SensorManager.DynamicSensorCallback
        public final void onDynamicSensorConnected(Sensor sensor) {
            SpatializerHelper.this.postInitSensors();
        }

        @Override // android.hardware.SensorManager.DynamicSensorCallback
        public final void onDynamicSensorDisconnected(Sensor sensor) {
            SpatializerHelper.this.postInitSensors();
        }
    }

    public final class SpatializerCallback extends INativeSpatializerCallback.Stub {
        public SpatializerCallback() {
        }

        public final void onLevelChanged(byte b) {
            SpatializerHelper spatializerHelper;
            int i;
            SpatializerHelper.loglogi("SpatializerCallback.onLevelChanged level:" + ((int) b));
            synchronized (SpatializerHelper.this) {
                spatializerHelper = SpatializerHelper.this;
                if (b != 0) {
                    i = 1;
                    if (b != 1) {
                        i = 2;
                        if (b != 2) {
                            throw new IllegalArgumentException("Unexpected spatializer level:" + ((int) b));
                        }
                    }
                } else {
                    i = 0;
                }
                spatializerHelper.mSpatLevel = i;
            }
            spatializerHelper.postInitSensors();
        }

        public final void onOutputChanged(int i) {
            SpatializerHelper spatializerHelper;
            int i2;
            SpatializerHelper.loglogi("SpatializerCallback.onOutputChanged output:" + i);
            synchronized (SpatializerHelper.this) {
                spatializerHelper = SpatializerHelper.this;
                i2 = spatializerHelper.mSpatOutput;
                spatializerHelper.mSpatOutput = i;
            }
            if (i2 != i) {
                int beginBroadcast = spatializerHelper.mOutputCallbacks.beginBroadcast();
                for (int i3 = 0; i3 < beginBroadcast; i3++) {
                    try {
                        spatializerHelper.mOutputCallbacks.getBroadcastItem(i3).dispatchSpatializerOutputChanged(i);
                    } catch (RemoteException e) {
                        Log.e("AS.SpatializerHelper", "Error in dispatchOutputUpdate", e);
                    }
                }
                spatializerHelper.mOutputCallbacks.finishBroadcast();
            }
        }
    }

    public final class SpatializerHeadTrackingCallback extends ISpatializerHeadTrackingCallback.Stub {
        public SpatializerHeadTrackingCallback() {
        }

        public final void onHeadToSoundStagePoseUpdated(float[] fArr) {
            if (fArr == null) {
                Log.e("AS.SpatializerHelper", "SpatializerHeadTrackingCallback.onHeadToStagePoseUpdatednull transform");
                return;
            }
            if (fArr.length != 6) {
                Log.e("AS.SpatializerHelper", "SpatializerHeadTrackingCallback.onHeadToStagePoseUpdated invalid transform length" + fArr.length);
                return;
            }
            SpatializerHelper spatializerHelper = SpatializerHelper.this;
            int beginBroadcast = spatializerHelper.mHeadPoseCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    spatializerHelper.mHeadPoseCallbacks.getBroadcastItem(i).dispatchPoseChanged(fArr);
                } catch (RemoteException e) {
                    Log.e("AS.SpatializerHelper", "Error in dispatchPoseChanged", e);
                }
            }
            spatializerHelper.mHeadPoseCallbacks.finishBroadcast();
        }

        public final void onHeadTrackingModeChanged(byte b) {
            int i;
            int i2;
            synchronized (this) {
                SpatializerHelper spatializerHelper = SpatializerHelper.this;
                i = spatializerHelper.mActualHeadTrackingMode;
                spatializerHelper.mActualHeadTrackingMode = SpatializerHelper.headTrackingModeTypeToSpatializerInt(b);
                i2 = SpatializerHelper.this.mActualHeadTrackingMode;
            }
            SpatializerHelper.loglogi("SpatializerHeadTrackingCallback.onHeadTrackingModeChanged mode:" + Spatializer.headtrackingModeToString(i2));
            if (i != i2) {
                SpatializerHelper spatializerHelper2 = SpatializerHelper.this;
                int beginBroadcast = spatializerHelper2.mHeadTrackingModeCallbacks.beginBroadcast();
                for (int i3 = 0; i3 < beginBroadcast; i3++) {
                    try {
                        spatializerHelper2.mHeadTrackingModeCallbacks.getBroadcastItem(i3).dispatchSpatializerActualHeadTrackingModeChanged(i2);
                    } catch (RemoteException e) {
                        Log.e("AS.SpatializerHelper", "Error in dispatchSpatializerActualHeadTrackingModeChanged(" + i2 + ")", e);
                    }
                }
                spatializerHelper2.mHeadTrackingModeCallbacks.finishBroadcast();
            }
        }
    }

    static {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(14);
        anonymousClass1.append(2, 1);
        anonymousClass1.append(24, 1);
        anonymousClass1.append(3, 0);
        anonymousClass1.append(4, 0);
        anonymousClass1.append(8, 0);
        anonymousClass1.append(13, 1);
        anonymousClass1.append(12, 1);
        anonymousClass1.append(11, 1);
        anonymousClass1.append(22, 0);
        anonymousClass1.append(5, 1);
        anonymousClass1.append(6, 1);
        anonymousClass1.append(19, 1);
        anonymousClass1.append(26, 0);
        anonymousClass1.append(27, 1);
        anonymousClass1.append(30, 0);
        SPAT_MODE_FOR_DEVICE_TYPE = anonymousClass1;
        DEFAULT_ATTRIBUTES = new AudioAttributes.Builder().setUsage(1).build();
        DEFAULT_FORMAT = new AudioFormat.Builder().setEncoding(2).setSampleRate(48000).setChannelMask(252).build();
        sRoutingDevices = new ArrayList(0);
    }

    public SpatializerHelper(AudioService audioService, AudioSystemAdapter audioSystemAdapter, AudioDeviceBroker audioDeviceBroker, boolean z, boolean z2, boolean z3) {
        this.mAudioService = audioService;
        this.mASA = audioSystemAdapter;
        this.mDeviceBroker = audioDeviceBroker;
        this.mBinauralEnabledDefault = z;
        this.mTransauralEnabledDefault = z2;
        this.mHeadTrackingEnabledDefault = z3;
    }

    public static int getCanonicalDeviceType(int i, int i2) {
        if (AudioSystem.isBluetoothDevice(i2)) {
            return i;
        }
        int i3 = SPAT_MODE_FOR_DEVICE_TYPE.get(i, Integer.MIN_VALUE);
        if (i3 == 1) {
            return 2;
        }
        return i3 == 0 ? 4 : 0;
    }

    public static int headTrackingModeTypeToSpatializerInt(byte b) {
        if (b == 0) {
            return 0;
        }
        if (b == 1) {
            return -1;
        }
        if (b == 2) {
            return 1;
        }
        if (b == 3) {
            return 2;
        }
        throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(b, "Unexpected head tracking mode:"));
    }

    public static void logDeviceState(AdiDeviceState adiDeviceState, String str) {
        int i;
        synchronized (adiDeviceState) {
            i = adiDeviceState.mDeviceType;
        }
        new MediaMetrics.Item(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("audio.spatializer.device.", AudioSystem.getDeviceName(AudioDeviceInfo.convertDeviceTypeToInternalDevice(i)))).set(MediaMetrics.Property.ADDRESS, adiDeviceState.getDeviceAddress()).set(MediaMetrics.Property.ENABLED, adiDeviceState.isSAEnabled() ? "true" : "false").set(MediaMetrics.Property.EVENT, TextUtils.emptyIfNull(str)).set(MediaMetrics.Property.HAS_HEAD_TRACKER, adiDeviceState.hasHeadTracker() ? "true" : "false").set(MediaMetrics.Property.HEAD_TRACKER_ENABLED, adiDeviceState.isHeadTrackerEnabled() ? "true" : "false").record();
    }

    public static void logloge(String str) {
        AudioService.sSpatialLogger.enqueueAndLog(1, str, "AS.SpatializerHelper");
    }

    public static void loglogi(String str) {
        AudioService.sSpatialLogger.enqueueAndLog(0, str, "AS.SpatializerHelper");
    }

    public static String spatStateString(int i) {
        return i != 0 ? i != 1 ? i != 3 ? i != 4 ? i != 5 ? i != 6 ? "invalid state" : "STATE_DISABLED_AVAILABLE" : "STATE_ENABLED_AVAILABLE" : "STATE_ENABLED_UNAVAILABLE" : "STATE_DISABLED_UNAVAILABLE" : "STATE_NOT_SUPPORTED" : "STATE_UNINITIALIZED";
    }

    public final void addCompatibleAudioDevice(AudioDeviceAttributes audioDeviceAttributes, boolean z, boolean z2) {
        if (isDeviceCompatibleWithSpatializationModes(audioDeviceAttributes)) {
            loglogi("addCompatibleAudioDevice: dev=" + audioDeviceAttributes);
            AdiDeviceState findSACompatibleDeviceStateForAudioDeviceAttributes = findSACompatibleDeviceStateForAudioDeviceAttributes(audioDeviceAttributes);
            AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
            if (findSACompatibleDeviceStateForAudioDeviceAttributes != null) {
                if (z2) {
                    initSAState(findSACompatibleDeviceStateForAudioDeviceAttributes);
                }
                if (!z || findSACompatibleDeviceStateForAudioDeviceAttributes.isSAEnabled()) {
                    findSACompatibleDeviceStateForAudioDeviceAttributes = null;
                } else {
                    findSACompatibleDeviceStateForAudioDeviceAttributes.setSAEnabled(true);
                }
            } else {
                int canonicalDeviceType = getCanonicalDeviceType(audioDeviceAttributes.getType(), audioDeviceAttributes.getInternalType());
                if (canonicalDeviceType == 0) {
                    Log.e("AS.SpatializerHelper", "addCompatibleAudioDevice with incompatible AudioDeviceAttributes " + audioDeviceAttributes);
                    return;
                } else {
                    findSACompatibleDeviceStateForAudioDeviceAttributes = new AdiDeviceState(canonicalDeviceType, audioDeviceAttributes.getInternalType(), audioDeviceAttributes.getAddress());
                    initSAState(findSACompatibleDeviceStateForAudioDeviceAttributes);
                    audioDeviceBroker.mDeviceInventory.addOrUpdateDeviceSAStateInInventory(findSACompatibleDeviceStateForAudioDeviceAttributes, true);
                }
            }
            if (findSACompatibleDeviceStateForAudioDeviceAttributes != null) {
                onRoutingUpdated();
                audioDeviceBroker.postPersistAudioDeviceSettings();
                logDeviceState(findSACompatibleDeviceStateForAudioDeviceAttributes, "addCompatibleAudioDevice");
            }
        }
    }

    public final synchronized void addWirelessDeviceIfNew(AudioDeviceAttributes audioDeviceAttributes) {
        if (isDeviceCompatibleWithSpatializationModes(audioDeviceAttributes)) {
            if (findSACompatibleDeviceStateForAudioDeviceAttributes(audioDeviceAttributes) == null) {
                int canonicalDeviceType = getCanonicalDeviceType(audioDeviceAttributes.getType(), audioDeviceAttributes.getInternalType());
                if (canonicalDeviceType == 0) {
                    Log.e("AS.SpatializerHelper", "addWirelessDeviceIfNew with incompatible AudioDeviceAttributes " + audioDeviceAttributes);
                } else {
                    AdiDeviceState adiDeviceState = new AdiDeviceState(canonicalDeviceType, audioDeviceAttributes.getInternalType(), audioDeviceAttributes.getAddress());
                    initSAState(adiDeviceState);
                    this.mDeviceBroker.mDeviceInventory.addOrUpdateDeviceSAStateInInventory(adiDeviceState, true);
                    this.mDeviceBroker.postPersistAudioDeviceSettings();
                    logDeviceState(adiDeviceState, "addWirelessDeviceIfNew");
                }
            }
        }
    }

    public final synchronized boolean canBeSpatializedOnDevice(AudioAttributes audioAttributes, AudioFormat audioFormat, ArrayList arrayList) {
        if (arrayList.isEmpty()) {
            return false;
        }
        if (!isDeviceCompatibleWithSpatializationModes((AudioDeviceAttributes) arrayList.get(0))) {
            return false;
        }
        return AudioSystem.canBeSpatialized(audioAttributes, audioFormat, (AudioDeviceAttributes[]) arrayList.toArray(new AudioDeviceAttributes[arrayList.size()]));
    }

    public final boolean checkSpatializer(String str) {
        int i = this.mState;
        if (i == 0 || i == 1) {
            return false;
        }
        if ((i != 3 && i != 4 && i != 5 && i != 6) || this.mSpat != null) {
            return true;
        }
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("checkSpatializer(): called from ", str, "(), native spatializer should not be null in state: ");
        m.append(this.mState);
        Log.e("AS.SpatializerHelper", m.toString());
        AudioService.sendMsg(this.mAudioService.mAudioHandler, 50, 0, 0, 0, "AS.AudioService", 0);
        return false;
    }

    public final void createSpat() {
        if (this.mSpat == null) {
            ISpatializer spatializer = AudioSystem.getSpatializer(new SpatializerCallback());
            this.mSpat = spatializer;
            if (spatializer == null) {
                Log.e("AS.SpatializerHelper", "createSpat(): No Spatializer found");
                AudioService.sendMsg(this.mAudioService.mAudioHandler, 50, 0, 0, 0, "AS.AudioService", 0);
                return;
            }
            try {
                if (this.mIsHeadTrackingSupported) {
                    this.mActualHeadTrackingMode = headTrackingModeTypeToSpatializerInt(spatializer.getActualHeadTrackingMode());
                    this.mSpat.registerHeadTrackingCallback(this.mSpatHeadTrackingCallback);
                }
            } catch (RemoteException e) {
                Log.e("AS.SpatializerHelper", "Can't configure head tracking", e);
                this.mState = 1;
                this.mCapableSpatLevel = 0;
                this.mActualHeadTrackingMode = -2;
            }
        }
    }

    public final void dispatchDesiredHeadTrackingMode(int i) {
        int beginBroadcast = this.mHeadTrackingModeCallbacks.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mHeadTrackingModeCallbacks.getBroadcastItem(i2).dispatchSpatializerDesiredHeadTrackingModeChanged(i);
            } catch (RemoteException e) {
                Log.e("AS.SpatializerHelper", "Error in dispatchSpatializerDesiredHeadTrackingModeChanged(" + i + ")", e);
            }
        }
        this.mHeadTrackingModeCallbacks.finishBroadcast();
    }

    public final void dispatchHeadTrackerAvailable(boolean z) {
        int beginBroadcast = this.mHeadTrackerCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mHeadTrackerCallbacks.getBroadcastItem(i).dispatchSpatializerHeadTrackerAvailable(z);
            } catch (RemoteException e) {
                Log.e("AS.SpatializerHelper", "Error in dispatchSpatializerHeadTrackerAvailable(" + z + ")", e);
            }
        }
        this.mHeadTrackerCallbacks.finishBroadcast();
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "SpatializerHelper:", "\tmState:"), this.mState, printWriter, "\tmSpatLevel:"), this.mSpatLevel, printWriter, "\tmCapableSpatLevel:"), this.mCapableSpatLevel, printWriter, "\tmIsHeadTrackingSupported:");
        m.append(this.mIsHeadTrackingSupported);
        printWriter.println(m.toString());
        StringBuilder sb = new StringBuilder();
        for (int i : this.mSupportedHeadTrackingModes) {
            sb.append(Spatializer.headtrackingModeToString(i));
            sb.append(" ");
        }
        printWriter.println("\tsupported head tracking modes:" + ((Object) sb));
        printWriter.println("\tmDesiredHeadTrackingMode:" + Spatializer.headtrackingModeToString(this.mDesiredHeadTrackingMode));
        printWriter.println("\tmActualHeadTrackingMode:" + Spatializer.headtrackingModeToString(this.mActualHeadTrackingMode));
        StringBuilder m2 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("\theadtracker available:"), this.mHeadTrackerAvailable, printWriter, "\tsec headtracker available:"), this.mSecHeadTrackerAvailable, printWriter, "\tglobal headtracker available:"), this.mGlobalHeadTrackerAvailable, printWriter, "\tsupports binaural:");
        m2.append(this.mBinauralSupported);
        m2.append(" / transaural:");
        StringBuilder m3 = BinaryTransparencyService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m(m2, this.mTransauralSupported, printWriter, "\tmSpatOutput:"), this.mSpatOutput, printWriter, "\thas FEATURE_AUDIO_SPATIAL_HEADTRACKING_LOW_LATENCY:");
        m3.append(this.mAudioService.mContext.getPackageManager().hasSystemFeature("android.hardware.audio.spatial.headtracking.low_latency"));
        printWriter.println(m3.toString());
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0096, code lost:
    
        if (r6.mBinauralSupported != false) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized android.util.Pair evaluateState(android.media.AudioDeviceAttributes r7) {
        /*
            r6 = this;
            java.lang.String r0 = "no spatialization device state found for Spatial Audio device:"
            java.lang.String r1 = "no spatialization mode found for device type:"
            java.lang.String r2 = "Device incompatible with Spatial Audio dev:"
            monitor-enter(r6)
            int r3 = r7.getType()     // Catch: java.lang.Throwable -> L33
            java.util.ArrayList r4 = r6.mSACapableDeviceTypes     // Catch: java.lang.Throwable -> L33
            java.lang.Integer r5 = java.lang.Integer.valueOf(r3)     // Catch: java.lang.Throwable -> L33
            boolean r4 = r4.contains(r5)     // Catch: java.lang.Throwable -> L33
            if (r4 != 0) goto L36
            java.lang.String r0 = "AS.SpatializerHelper"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L33
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L33
            r1.append(r7)     // Catch: java.lang.Throwable -> L33
            java.lang.String r7 = r1.toString()     // Catch: java.lang.Throwable -> L33
            android.util.Log.i(r0, r7)     // Catch: java.lang.Throwable -> L33
            android.util.Pair r7 = new android.util.Pair     // Catch: java.lang.Throwable -> L33
            java.lang.Boolean r0 = java.lang.Boolean.FALSE     // Catch: java.lang.Throwable -> L33
            r7.<init>(r0, r0)     // Catch: java.lang.Throwable -> L33
            monitor-exit(r6)
            return r7
        L33:
            r7 = move-exception
            goto Lab
        L36:
            com.android.server.audio.SpatializerHelper$1 r2 = com.android.server.audio.SpatializerHelper.SPAT_MODE_FOR_DEVICE_TYPE     // Catch: java.lang.Throwable -> L33
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            int r2 = r2.get(r3, r4)     // Catch: java.lang.Throwable -> L33
            if (r2 != r4) goto L5a
            java.lang.String r7 = "AS.SpatializerHelper"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L33
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L33
            r0.append(r3)     // Catch: java.lang.Throwable -> L33
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L33
            android.util.Log.e(r7, r0)     // Catch: java.lang.Throwable -> L33
            android.util.Pair r7 = new android.util.Pair     // Catch: java.lang.Throwable -> L33
            java.lang.Boolean r0 = java.lang.Boolean.FALSE     // Catch: java.lang.Throwable -> L33
            r7.<init>(r0, r0)     // Catch: java.lang.Throwable -> L33
            monitor-exit(r6)
            return r7
        L5a:
            com.android.server.audio.AdiDeviceState r1 = r6.findSACompatibleDeviceStateForAudioDeviceAttributes(r7)     // Catch: java.lang.Throwable -> L33
            if (r1 != 0) goto L7a
            java.lang.String r1 = "AS.SpatializerHelper"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L33
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L33
            r2.append(r7)     // Catch: java.lang.Throwable -> L33
            java.lang.String r7 = r2.toString()     // Catch: java.lang.Throwable -> L33
            android.util.Log.i(r1, r7)     // Catch: java.lang.Throwable -> L33
            android.util.Pair r7 = new android.util.Pair     // Catch: java.lang.Throwable -> L33
            java.lang.Boolean r0 = java.lang.Boolean.FALSE     // Catch: java.lang.Throwable -> L33
            r7.<init>(r0, r0)     // Catch: java.lang.Throwable -> L33
            monitor-exit(r6)
            return r7
        L7a:
            boolean r7 = android.media.AudioSystem.isBluetoothDevice(r3)     // Catch: java.lang.Throwable -> L33
            r0 = 1
            if (r7 == 0) goto L98
            int r7 = r1.getAudioDeviceCategory()     // Catch: java.lang.Throwable -> L33
            r3 = 0
            if (r7 == 0) goto L92
            int r7 = r1.getAudioDeviceCategory()     // Catch: java.lang.Throwable -> L33
            r4 = 3
            if (r7 != r4) goto L90
            goto L92
        L90:
            r0 = r3
            goto L98
        L92:
            if (r2 != 0) goto L90
            boolean r7 = r6.mBinauralSupported     // Catch: java.lang.Throwable -> L33
            if (r7 == 0) goto L90
        L98:
            android.util.Pair r7 = new android.util.Pair     // Catch: java.lang.Throwable -> L33
            boolean r1 = r1.isSAEnabled()     // Catch: java.lang.Throwable -> L33
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)     // Catch: java.lang.Throwable -> L33
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch: java.lang.Throwable -> L33
            r7.<init>(r1, r0)     // Catch: java.lang.Throwable -> L33
            monitor-exit(r6)
            return r7
        Lab:
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.SpatializerHelper.evaluateState(android.media.AudioDeviceAttributes):android.util.Pair");
    }

    public final AdiDeviceState findSACompatibleDeviceStateForAudioDeviceAttributes(AudioDeviceAttributes audioDeviceAttributes) {
        int i;
        AudioDeviceBroker audioDeviceBroker = this.mDeviceBroker;
        int canonicalDeviceType = getCanonicalDeviceType(audioDeviceAttributes.getType(), audioDeviceAttributes.getInternalType());
        AudioDeviceInventory audioDeviceInventory = audioDeviceBroker.mDeviceInventory;
        audioDeviceInventory.getClass();
        boolean isBluetoothDevice = AudioSystem.isBluetoothDevice(audioDeviceAttributes.getInternalType());
        synchronized (audioDeviceInventory.mDeviceInventoryLock) {
            try {
                for (AdiDeviceState adiDeviceState : audioDeviceInventory.mDeviceInventory.values()) {
                    synchronized (adiDeviceState) {
                        i = adiDeviceState.mDeviceType;
                    }
                    if (i != canonicalDeviceType || (isBluetoothDevice && !audioDeviceAttributes.getAddress().equals(adiDeviceState.getDeviceAddress()))) {
                    }
                }
                adiDeviceState = null;
            } finally {
            }
        }
        if (adiDeviceState != null && isSADevice(adiDeviceState)) {
            return adiDeviceState;
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x00fe, code lost:
    
        if (r6.getUuid().equals(com.android.server.audio.UuidUtils.STANDALONE_UUID) == false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0107, code lost:
    
        monitor-enter(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x010a, code lost:
    
        if (r14.mIsHeadTrackingSupported != false) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x010c, code lost:
    
        android.util.Log.v("AS.SpatializerHelper", "no headtracking support, setHasHeadTracker always false for " + r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x011d, code lost:
    
        monitor-exit(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x011e, code lost:
    
        r7 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0122, code lost:
    
        r8 = findSACompatibleDeviceStateForAudioDeviceAttributes(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0126, code lost:
    
        if (r8 == null) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x012c, code lost:
    
        if (r8.hasHeadTracker() != false) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x012e, code lost:
    
        r8.setHasHeadTracker(true);
        r14.mDeviceBroker.postPersistAudioDeviceSettings();
        logDeviceState(r8, "setHasHeadTracker");
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x013c, code lost:
    
        r7 = r8.isHeadTrackerEnabled();
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0140, code lost:
    
        monitor-exit(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0142, code lost:
    
        android.util.Log.e("AS.SpatializerHelper", "setHasHeadTracker: device not found for:" + r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0153, code lost:
    
        monitor-exit(r14);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getHeadSensorHandleUpdateTracker() {
        /*
            Method dump skipped, instructions count: 356
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.SpatializerHelper.getHeadSensorHandleUpdateTracker():int");
    }

    public final ArrayList getRoutingDevices(AudioAttributes audioAttributes) {
        ArrayList devicesForAttributes = this.mASA.getDevicesForAttributes(audioAttributes, false);
        Iterator it = devicesForAttributes.iterator();
        while (it.hasNext()) {
            if (((AudioDeviceAttributes) it.next()) == null) {
                return new ArrayList(0);
            }
        }
        return devicesForAttributes;
    }

    public final synchronized void init(boolean z) {
        byte[] supportedLevels;
        loglogi("init effectExpected=" + z);
        if (!z) {
            loglogi("init(): setting state to STATE_NOT_SUPPORTED due to effect not expected");
            this.mState = 1;
            return;
        }
        if (this.mState != 0) {
            String str = "init() called in state " + this.mState;
            logloge(str);
            throw new IllegalStateException(str);
        }
        ISpatializer spatializer = AudioSystem.getSpatializer(new SpatializerCallback());
        if (spatializer == null) {
            loglogi("init(): No Spatializer found");
            this.mState = 1;
            return;
        }
        this.mCapableSpatLevel = 0;
        this.mBinauralSupported = false;
        this.mTransauralSupported = false;
        this.mIsHeadTrackingSupported = false;
        this.mSupportedHeadTrackingModes = new int[0];
        try {
            try {
                supportedLevels = spatializer.getSupportedLevels();
            } catch (RemoteException unused) {
                this.mCapableSpatLevel = 0;
                this.mBinauralSupported = false;
                this.mTransauralSupported = false;
                this.mIsHeadTrackingSupported = false;
                this.mSupportedHeadTrackingModes = new int[0];
            }
            if (supportedLevels != null && supportedLevels.length != 0 && (supportedLevels.length != 1 || supportedLevels[0] != 0)) {
                int length = supportedLevels.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    byte b = supportedLevels[i];
                    loglogi("init(): found support for level: " + ((int) b));
                    if (b == 1) {
                        loglogi("init(): setting capable level to LEVEL_MULTICHANNEL");
                        this.mCapableSpatLevel = b;
                        break;
                    }
                    i++;
                }
                boolean isHeadTrackingSupported = spatializer.isHeadTrackingSupported();
                this.mIsHeadTrackingSupported = isHeadTrackingSupported;
                if (isHeadTrackingSupported) {
                    byte[] supportedHeadTrackingModes = spatializer.getSupportedHeadTrackingModes();
                    ArrayList arrayList = new ArrayList(0);
                    for (byte b2 : supportedHeadTrackingModes) {
                        if (b2 != 0 && b2 != 1) {
                            if (b2 == 2 || b2 == 3) {
                                arrayList.add(Integer.valueOf(headTrackingModeTypeToSpatializerInt(b2)));
                            } else {
                                Log.e("AS.SpatializerHelper", "Unexpected head tracking mode:" + ((int) b2), new IllegalArgumentException("invalid mode"));
                            }
                        }
                    }
                    this.mSupportedHeadTrackingModes = new int[arrayList.size()];
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        this.mSupportedHeadTrackingModes[i2] = ((Integer) arrayList.get(i2)).intValue();
                    }
                    this.mActualHeadTrackingMode = headTrackingModeTypeToSpatializerInt(spatializer.getActualHeadTrackingMode());
                } else {
                    this.mDesiredHeadTrackingModeWhenEnabled = -2;
                    this.mDesiredHeadTrackingMode = -2;
                }
                for (byte b3 : spatializer.getSupportedModes()) {
                    if (b3 == 0) {
                        this.mBinauralSupported = true;
                    } else if (b3 != 1) {
                        logloge("init(): Spatializer reports unknown supported mode:" + ((int) b3));
                    } else {
                        this.mTransauralSupported = true;
                    }
                }
                if (!this.mBinauralSupported && !this.mTransauralSupported) {
                    this.mState = 1;
                    try {
                        spatializer.release();
                    } catch (RemoteException unused2) {
                    }
                    return;
                }
                int i3 = 0;
                while (true) {
                    AnonymousClass1 anonymousClass1 = SPAT_MODE_FOR_DEVICE_TYPE;
                    if (i3 >= anonymousClass1.size()) {
                        break;
                    }
                    int valueAt = anonymousClass1.valueAt(i3);
                    if ((valueAt == 0 && this.mBinauralSupported) || (valueAt == 1 && this.mTransauralSupported)) {
                        this.mSACapableDeviceTypes.add(Integer.valueOf(anonymousClass1.keyAt(i3)));
                    }
                    i3++;
                }
                Iterator it = ((ArrayList) this.mDeviceBroker.getImmutableDeviceInventory()).iterator();
                while (it.hasNext()) {
                    AdiDeviceState adiDeviceState = (AdiDeviceState) it.next();
                    if (isSADevice(adiDeviceState)) {
                        logDeviceState(adiDeviceState, "setSADeviceSettings");
                    }
                }
                addCompatibleAudioDevice(new AudioDeviceAttributes(2, ""), false, false);
                addCompatibleAudioDevice(new AudioDeviceAttributes(8, ""), false, false);
                try {
                    spatializer.release();
                } catch (RemoteException unused3) {
                }
                if (this.mCapableSpatLevel == 0) {
                    this.mState = 1;
                    return;
                } else {
                    this.mState = 3;
                    sRoutingDevices = getRoutingDevices(DEFAULT_ATTRIBUTES);
                    return;
                }
            }
            logloge("init(): found Spatializer is useless");
            this.mState = 1;
            try {
                spatializer.release();
            } catch (RemoteException unused4) {
            }
        } catch (Throwable th) {
            try {
                spatializer.release();
            } catch (RemoteException unused5) {
            }
            throw th;
        }
    }

    public final void initSAState(AdiDeviceState adiDeviceState) {
        int i;
        AnonymousClass1 anonymousClass1 = SPAT_MODE_FOR_DEVICE_TYPE;
        synchronized (adiDeviceState) {
            i = adiDeviceState.mDeviceType;
        }
        int i2 = anonymousClass1.get(i, Integer.MIN_VALUE);
        adiDeviceState.setSAEnabled(i2 == 0 ? this.mBinauralEnabledDefault : i2 == 1 ? this.mTransauralEnabledDefault : false);
        adiDeviceState.setHeadTrackerEnabled(this.mHeadTrackingEnabledDefault);
    }

    public final boolean isDeviceCompatibleWithSpatializationModes(AudioDeviceAttributes audioDeviceAttributes) {
        byte b = (byte) SPAT_MODE_FOR_DEVICE_TYPE.get(audioDeviceAttributes.getType(), -1);
        return (b == 0 && this.mBinauralSupported) || (b == 1 && this.mTransauralSupported);
    }

    public final boolean isSADevice(AdiDeviceState adiDeviceState) {
        int i;
        int i2;
        synchronized (adiDeviceState) {
            i = adiDeviceState.mDeviceType;
        }
        synchronized (adiDeviceState) {
            i2 = adiDeviceState.mDeviceType;
        }
        return i == getCanonicalDeviceType(i2, adiDeviceState.getInternalDeviceType()) && isDeviceCompatibleWithSpatializationModes(adiDeviceState.getAudioDeviceAttributes());
    }

    public final synchronized void onInitSensors() {
        int i;
        int i2;
        HelperDynamicSensorCallback helperDynamicSensorCallback;
        try {
            boolean z = this.mFeatureEnabled && this.mSpatLevel != 0;
            String str = z ? "initializing" : "releasing";
            if (this.mSpat == null) {
                logloge("not " + str + " sensors, null spatializer");
                return;
            }
            if (!this.mIsHeadTrackingSupported) {
                logloge("not " + str + " sensors, spatializer doesn't support headtracking");
                return;
            }
            if (z) {
                if (this.mSensorManager == null) {
                    try {
                        this.mSensorManager = (SensorManager) this.mAudioService.mContext.getSystemService("sensor");
                        HelperDynamicSensorCallback helperDynamicSensorCallback2 = new HelperDynamicSensorCallback();
                        this.mDynSensorCallback = helperDynamicSensorCallback2;
                        this.mSensorManager.registerDynamicSensorCallback(helperDynamicSensorCallback2);
                    } catch (Exception e) {
                        Log.e("AS.SpatializerHelper", "Error with SensorManager, can't initialize sensors", e);
                        this.mSensorManager = null;
                        this.mDynSensorCallback = null;
                        return;
                    }
                }
                i2 = getHeadSensorHandleUpdateTracker();
                loglogi("head tracker sensor handle initialized to " + i2);
                Sensor defaultSensor = this.mSensorManager.getDefaultSensor(11);
                i = defaultSensor != null ? defaultSensor.getHandle() : -1;
                Log.i("AS.SpatializerHelper", "found screen sensor handle initialized to " + i);
            } else {
                SensorManager sensorManager = this.mSensorManager;
                if (sensorManager != null && (helperDynamicSensorCallback = this.mDynSensorCallback) != null) {
                    sensorManager.unregisterDynamicSensorCallback(helperDynamicSensorCallback);
                    this.mSensorManager = null;
                    this.mDynSensorCallback = null;
                }
                i = -1;
                i2 = -1;
            }
            try {
                Log.i("AS.SpatializerHelper", "setScreenSensor:" + i);
                this.mSpat.setScreenSensor(i);
            } catch (Exception e2) {
                Log.e("AS.SpatializerHelper", "Error calling setScreenSensor:" + i, e2);
            }
            try {
                Log.i("AS.SpatializerHelper", "setHeadSensor:" + i2);
                this.mSpat.setHeadSensor(i2);
                if (this.mHeadTrackerAvailable != (i2 != -1)) {
                    boolean z2 = i2 != -1;
                    this.mHeadTrackerAvailable = z2;
                    boolean z3 = this.mSecHeadTrackerAvailable || z2;
                    if (this.mGlobalHeadTrackerAvailable != z3) {
                        this.mGlobalHeadTrackerAvailable = z3;
                        dispatchHeadTrackerAvailable(z3);
                    }
                }
            } catch (Exception e3) {
                Log.e("AS.SpatializerHelper", "Error calling setHeadSensor:" + i2, e3);
            }
            setDesiredHeadTrackingMode(this.mDesiredHeadTrackingMode);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void onRoutingUpdated() {
        boolean z;
        int i = this.mState;
        if (i != 0) {
            ?? r6 = 1;
            if (i != 1) {
                AudioAttributes audioAttributes = DEFAULT_ATTRIBUTES;
                ArrayList routingDevices = getRoutingDevices(audioAttributes);
                sRoutingDevices = routingDevices;
                if (routingDevices.isEmpty()) {
                    logloge("onRoutingUpdated: no device, no Spatial Audio");
                    setDispatchAvailableState(false);
                    return;
                }
                AudioDeviceAttributes audioDeviceAttributes = (AudioDeviceAttributes) sRoutingDevices.get(0);
                if (AudioSystem.isBluetoothDevice(audioDeviceAttributes.getInternalType())) {
                    addWirelessDeviceIfNew(audioDeviceAttributes);
                }
                Pair evaluateState = evaluateState(audioDeviceAttributes);
                if (((Boolean) evaluateState.second).booleanValue()) {
                    z = canBeSpatializedOnDevice(audioAttributes, DEFAULT_FORMAT, sRoutingDevices);
                    loglogi("onRoutingUpdated: can spatialize media 5.1:" + z + " on device:" + audioDeviceAttributes);
                    setDispatchAvailableState(z);
                } else {
                    loglogi("onRoutingUpdated: device:" + audioDeviceAttributes + " not available for Spatial Audio");
                    setDispatchAvailableState(false);
                    z = false;
                }
                if (!this.mFeatureEnabled || !z || !((Boolean) evaluateState.first).booleanValue()) {
                    r6 = 0;
                }
                if (r6 != 0) {
                    loglogi("Enabling Spatial Audio since enabled for media device:" + audioDeviceAttributes);
                } else {
                    loglogi("Disabling Spatial Audio since disabled for media device:" + audioDeviceAttributes);
                }
                if (this.mSpat != null) {
                    loglogi("Setting spatialization level to: " + ((int) r6));
                    try {
                        this.mSpat.setLevel((byte) r6);
                    } catch (RemoteException e) {
                        Log.e("AS.SpatializerHelper", "onRoutingUpdated() Can't set spatializer level", e);
                        AudioService.sendMsg(this.mAudioService.mAudioHandler, 50, 0, 0, 0, "AS.AudioService", 0);
                        return;
                    }
                }
                setDispatchFeatureEnabledState("onRoutingUpdated", r6);
                int i2 = this.mDesiredHeadTrackingMode;
                if (i2 != -2 && i2 != -1) {
                    postInitSensors();
                }
            }
        }
    }

    public final void postInitSensors() {
        AudioService.sendMsg(this.mAudioService.mAudioHandler, 42, 0, 0, 0, "AS.AudioService", 0);
    }

    public final synchronized void refreshDevice(AudioDeviceAttributes audioDeviceAttributes, boolean z) {
        try {
            AdiDeviceState findSACompatibleDeviceStateForAudioDeviceAttributes = findSACompatibleDeviceStateForAudioDeviceAttributes(audioDeviceAttributes);
            if (findSACompatibleDeviceStateForAudioDeviceAttributes != null && (!AudioSystem.isBluetoothDevice(findSACompatibleDeviceStateForAudioDeviceAttributes.getInternalDeviceType()) || findSACompatibleDeviceStateForAudioDeviceAttributes.getAudioDeviceCategory() == 0 || findSACompatibleDeviceStateForAudioDeviceAttributes.getAudioDeviceCategory() == 3)) {
                addCompatibleAudioDevice(audioDeviceAttributes, findSACompatibleDeviceStateForAudioDeviceAttributes.isSAEnabled(), z);
                setHeadTrackerEnabled(findSACompatibleDeviceStateForAudioDeviceAttributes.isHeadTrackerEnabled(), audioDeviceAttributes);
            }
            removeCompatibleAudioDevice(audioDeviceAttributes);
        } catch (Throwable th) {
            throw th;
        }
    }

    public final void releaseSpat() {
        ISpatializer iSpatializer = this.mSpat;
        if (iSpatializer != null) {
            try {
                if (this.mIsHeadTrackingSupported) {
                    iSpatializer.registerHeadTrackingCallback((ISpatializerHeadTrackingCallback) null);
                }
                this.mHeadTrackerAvailable = false;
                this.mSpat.release();
            } catch (RemoteException e) {
                Log.e("AS.SpatializerHelper", "Can't set release spatializer cleanly", e);
            }
            this.mSpat = null;
        }
    }

    public final synchronized void removeCompatibleAudioDevice(AudioDeviceAttributes audioDeviceAttributes) {
        loglogi("removeCompatibleAudioDevice: dev=" + audioDeviceAttributes);
        AdiDeviceState findSACompatibleDeviceStateForAudioDeviceAttributes = findSACompatibleDeviceStateForAudioDeviceAttributes(audioDeviceAttributes);
        if (findSACompatibleDeviceStateForAudioDeviceAttributes != null && findSACompatibleDeviceStateForAudioDeviceAttributes.isSAEnabled()) {
            findSACompatibleDeviceStateForAudioDeviceAttributes.setSAEnabled(false);
            onRoutingUpdated();
            this.mDeviceBroker.postPersistAudioDeviceSettings();
            logDeviceState(findSACompatibleDeviceStateForAudioDeviceAttributes, "removeCompatibleAudioDevice");
        }
    }

    public final synchronized void reset(boolean z) {
        loglogi("Resetting featureEnabled=" + z);
        releaseSpat();
        this.mState = 0;
        this.mSpatLevel = 0;
        this.mActualHeadTrackingMode = -2;
        init(true);
        setSpatializerEnabledInt(z);
    }

    public final synchronized void setDesiredHeadTrackingMode(int i) {
        if (checkSpatializer("setDesiredHeadTrackingMode") && this.mIsHeadTrackingSupported) {
            if (i != -1) {
                this.mDesiredHeadTrackingModeWhenEnabled = i;
            }
            try {
                if (this.mDesiredHeadTrackingMode != i) {
                    this.mDesiredHeadTrackingMode = i;
                    dispatchDesiredHeadTrackingMode(i);
                }
                Log.i("AS.SpatializerHelper", "setDesiredHeadTrackingMode(" + Spatializer.headtrackingModeToString(i) + ")");
                ISpatializer iSpatializer = this.mSpat;
                byte b = 1;
                if (i != -1) {
                    if (i == 0) {
                        b = 0;
                    } else if (i == 1) {
                        b = 2;
                    } else {
                        if (i != 2) {
                            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unexpected head tracking mode:"));
                        }
                        b = 3;
                    }
                }
                iSpatializer.setDesiredHeadTrackingMode(b);
            } catch (RemoteException e) {
                Log.e("AS.SpatializerHelper", "Error calling setDesiredHeadTrackingMode", e);
            }
        }
    }

    public final synchronized void setDispatchAvailableState(boolean z) {
        int i = this.mState;
        if (i == 0 || i == 1) {
            throw new IllegalStateException("Should not update available state in state:" + this.mState);
        }
        if (i == 3) {
            if (!z) {
                loglogi("setDispatchAvailableState(" + z + ") no dispatch: mState:" + spatStateString(this.mState));
                return;
            }
            this.mState = 6;
        } else if (i == 4) {
            if (!z) {
                loglogi("setDispatchAvailableState(" + z + ") no dispatch: mState:" + spatStateString(this.mState));
                return;
            }
            this.mState = 5;
        } else if (i == 5) {
            if (z) {
                loglogi("setDispatchAvailableState(" + z + ") no dispatch: mState:" + spatStateString(this.mState));
                return;
            }
            this.mState = 4;
        } else if (i == 6) {
            if (z) {
                loglogi("setDispatchAvailableState(" + z + ") no dispatch: mState:" + spatStateString(this.mState));
                return;
            }
            this.mState = 3;
        }
        loglogi("setDispatchAvailableState(" + z + ") mState:" + spatStateString(this.mState));
        int beginBroadcast = this.mStateCallbacks.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mStateCallbacks.getBroadcastItem(i2).dispatchSpatializerAvailableChanged(z);
            } catch (RemoteException e) {
                Log.e("AS.SpatializerHelper", "Error in dispatchSpatializerEnabledChanged", e);
            }
        }
        this.mStateCallbacks.finishBroadcast();
    }

    public final synchronized void setDispatchFeatureEnabledState(String str, boolean z) {
        try {
            if (!z) {
                int i = this.mState;
                if (i != 3) {
                    if (i == 4) {
                        this.mState = 3;
                    } else if (i == 5) {
                        this.mState = 6;
                    } else if (i != 6) {
                        throw new IllegalStateException("Invalid mState:" + this.mState + " for enabled false");
                    }
                }
                loglogi("setDispatchFeatureEnabledState(" + z + ") no dispatch: mState:" + spatStateString(this.mState) + " src:" + str);
                return;
            }
            int i2 = this.mState;
            if (i2 == 3) {
                this.mState = 4;
            } else {
                if (i2 == 4 || i2 == 5) {
                    loglogi("setDispatchFeatureEnabledState(" + z + ") no dispatch: mState:" + spatStateString(this.mState) + " src:" + str);
                    return;
                }
                if (i2 != 6) {
                    throw new IllegalStateException("Invalid mState:" + this.mState + " for enabled true");
                }
                this.mState = 5;
            }
            loglogi("setDispatchFeatureEnabledState(" + z + ") mState:" + spatStateString(this.mState));
            int beginBroadcast = this.mStateCallbacks.beginBroadcast();
            for (int i3 = 0; i3 < beginBroadcast; i3++) {
                try {
                    this.mStateCallbacks.getBroadcastItem(i3).dispatchSpatializerEnabledChanged(z);
                } catch (RemoteException e) {
                    Log.e("AS.SpatializerHelper", "Error in dispatchSpatializerEnabledChanged", e);
                }
            }
            this.mStateCallbacks.finishBroadcast();
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void setFeatureEnabled(boolean z) {
        loglogi("setFeatureEnabled(" + z + ") was featureEnabled:" + this.mFeatureEnabled);
        if (this.mFeatureEnabled == z) {
            return;
        }
        this.mFeatureEnabled = z;
        if (z) {
            int i = this.mState;
            if (i == 1) {
                Log.e("AS.SpatializerHelper", "Can't enabled Spatial Audio, unsupported");
            } else {
                if (i == 0) {
                    init(true);
                }
                setSpatializerEnabledInt(true);
            }
        } else {
            setSpatializerEnabledInt(false);
        }
    }

    public final synchronized void setHeadTrackerEnabled(boolean z, AudioDeviceAttributes audioDeviceAttributes) {
        try {
            if (!this.mIsHeadTrackingSupported) {
                Log.v("AS.SpatializerHelper", "no headtracking support, ignoring setHeadTrackerEnabled to " + z + " for " + audioDeviceAttributes);
            }
            AdiDeviceState findSACompatibleDeviceStateForAudioDeviceAttributes = findSACompatibleDeviceStateForAudioDeviceAttributes(audioDeviceAttributes);
            if (findSACompatibleDeviceStateForAudioDeviceAttributes == null) {
                return;
            }
            if (!findSACompatibleDeviceStateForAudioDeviceAttributes.hasHeadTracker()) {
                Log.e("AS.SpatializerHelper", "Called setHeadTrackerEnabled enabled:" + z + " device:" + audioDeviceAttributes + " on a device without headtracker");
                return;
            }
            Log.i("AS.SpatializerHelper", "setHeadTrackerEnabled enabled:" + z + " device:" + audioDeviceAttributes);
            findSACompatibleDeviceStateForAudioDeviceAttributes.setHeadTrackerEnabled(z);
            this.mDeviceBroker.postPersistAudioDeviceSettings();
            logDeviceState(findSACompatibleDeviceStateForAudioDeviceAttributes, "setHeadTrackerEnabled");
            if (sRoutingDevices.isEmpty()) {
                logloge("setHeadTrackerEnabled: no device, bailing");
                return;
            }
            AudioDeviceAttributes audioDeviceAttributes2 = (AudioDeviceAttributes) sRoutingDevices.get(0);
            if (audioDeviceAttributes2.getType() == audioDeviceAttributes.getType() && audioDeviceAttributes2.getAddress().equals(audioDeviceAttributes.getAddress())) {
                setDesiredHeadTrackingMode(z ? this.mDesiredHeadTrackingModeWhenEnabled : -1);
                if (z && !this.mHeadTrackerAvailable) {
                    postInitSensors();
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized void setSpatializerEnabledInt(boolean z) {
        try {
            int i = this.mState;
            if (i != 0) {
                if (i != 1) {
                    if (i != 3) {
                        if (i == 4 || i == 5) {
                            if (!z) {
                                releaseSpat();
                                setDispatchFeatureEnabledState("setSpatializerEnabledInt", false);
                            }
                        } else if (i != 6) {
                        }
                    }
                    if (z) {
                        createSpat();
                        onRoutingUpdated();
                    }
                } else if (z) {
                    Log.e("AS.SpatializerHelper", "Can't enable when unsupported");
                }
            } else if (z) {
                throw new IllegalStateException("Can't enable when uninitialized");
            }
        } finally {
        }
    }
}
