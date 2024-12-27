package com.android.server.tv;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.HdmiHotplugEvent;
import android.hardware.hdmi.IHdmiDeviceEventListener;
import android.hardware.hdmi.IHdmiHotplugEventListener;
import android.hardware.hdmi.IHdmiSystemAudioModeChangeListener;
import android.media.AudioDevicePort;
import android.media.AudioManager;
import android.media.AudioPatch;
import android.media.AudioPort;
import android.media.AudioSystem;
import android.media.tv.ITvInputHardware;
import android.media.tv.ITvInputHardwareCallback;
import android.media.tv.ITvInputService;
import android.media.tv.TvInputHardwareInfo;
import android.media.tv.TvInputInfo;
import android.media.tv.TvStreamConfig;
import android.media.tv.tunerresourcemanager.ResourceClientProfile;
import android.media.tv.tunerresourcemanager.TunerResourceManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.Surface;
import com.android.internal.os.SomeArgs;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.tv.TvInputHal;
import com.android.server.tv.TvInputManagerService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final class TvInputHardwareManager implements TvInputHal.Callback {
    public final AudioManager mAudioManager;
    public final SparseArray mConnections;
    public final Context mContext;
    public int mCurrentIndex;
    public int mCurrentMaxIndex;
    public final TvInputHal mHal;
    public final ListenerHandler mHandler;
    public final SparseArray mHardwareInputIdMap;
    public final List mHardwareList;
    public final HdmiDeviceEventListener mHdmiDeviceEventListener;
    public final List mHdmiDeviceList;
    public final HdmiHotplugEventListener mHdmiHotplugEventListener;
    public final SparseArray mHdmiInputIdMap;
    public final Map mHdmiParentInputMap;
    public final SparseBooleanArray mHdmiStateMap;
    public final HdmiSystemAudioModeChangeListener mHdmiSystemAudioModeChangeListener;
    public final Map mInputMap;
    public final TvInputManagerService.HardwareListener mListener;
    public final Object mLock;
    public final List mPendingHdmiDeviceEvents;
    public final List mPendingTvinputInfoEvents;
    public final AnonymousClass1 mVolumeReceiver;

    public final class Connection implements IBinder.DeathRecipient {
        public ITvInputHardwareCallback mCallback;
        public TvInputHardwareInfo mHardwareInfo;
        public TvInputInfo mInfo;
        public Runnable mOnFirstFrameCaptured;
        public TvInputHardwareImpl mHardware = null;
        public TvStreamConfig[] mConfigs = null;
        public Integer mCallingUid = null;
        public Integer mResolvedUserId = null;
        public ResourceClientProfile mResourceClientProfile = null;
        public boolean mIsCableConnectionStatusUpdated = false;

        /* renamed from: -$$Nest$mgetInputStateLocked, reason: not valid java name */
        public static int m977$$Nest$mgetInputStateLocked(Connection connection) {
            int cableConnectionStatus;
            if ((connection.getConfigsLengthLocked() <= 0 || connection.mIsCableConnectionStatusUpdated) && (cableConnectionStatus = connection.mHardwareInfo.getCableConnectionStatus()) != 1) {
                return cableConnectionStatus != 2 ? 1 : 2;
            }
            return 0;
        }

        public Connection(TvInputHardwareInfo tvInputHardwareInfo) {
            this.mHardwareInfo = tvInputHardwareInfo;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (TvInputHardwareManager.this.mLock) {
                resetLocked(null, null, null, null, null, null);
            }
        }

        public final int getConfigsLengthLocked() {
            TvStreamConfig[] tvStreamConfigArr = this.mConfigs;
            if (tvStreamConfigArr == null) {
                return 0;
            }
            return tvStreamConfigArr.length;
        }

        public final void resetLocked(TvInputHardwareImpl tvInputHardwareImpl, ITvInputHardwareCallback iTvInputHardwareCallback, TvInputInfo tvInputInfo, Integer num, Integer num2, ResourceClientProfile resourceClientProfile) {
            if (this.mHardware != null) {
                try {
                    this.mCallback.onReleased();
                } catch (RemoteException e) {
                    Slog.e("TvInputHardwareManager", "error in Connection::resetLocked", e);
                }
                this.mHardware.release();
            }
            this.mHardware = tvInputHardwareImpl;
            this.mCallback = iTvInputHardwareCallback;
            this.mInfo = tvInputInfo;
            this.mCallingUid = num;
            this.mResolvedUserId = num2;
            this.mOnFirstFrameCaptured = null;
            this.mResourceClientProfile = resourceClientProfile;
            if (tvInputHardwareImpl == null || iTvInputHardwareCallback == null) {
                return;
            }
            try {
                iTvInputHardwareCallback.onStreamConfigChanged(this.mConfigs);
            } catch (RemoteException e2) {
                Slog.e("TvInputHardwareManager", "error in Connection::resetLocked", e2);
            }
        }

        public final String toString() {
            return "Connection{ mHardwareInfo: " + this.mHardwareInfo + ", mInfo: " + this.mInfo + ", mCallback: " + this.mCallback + ", mHardware: " + this.mHardware + ", mConfigs: " + Arrays.toString(this.mConfigs) + ", mCallingUid: " + this.mCallingUid + ", mResolvedUserId: " + this.mResolvedUserId + ", mResourceClientProfile: " + this.mResourceClientProfile + " }";
        }

        public final boolean updateCableConnectionStatusLocked(int i) {
            if (i != 0 || this.mIsCableConnectionStatusUpdated) {
                this.mIsCableConnectionStatusUpdated = true;
                this.mHardwareInfo = this.mHardwareInfo.toBuilder().cableConnectionStatus(i).build();
            }
            return this.mIsCableConnectionStatusUpdated;
        }
    }

    public final class HdmiDeviceEventListener extends IHdmiDeviceEventListener.Stub {
        public HdmiDeviceEventListener() {
        }

        public final HdmiDeviceInfo findHdmiDeviceInfo(int i) {
            Iterator it = ((ArrayList) TvInputHardwareManager.this.mHdmiDeviceList).iterator();
            while (it.hasNext()) {
                HdmiDeviceInfo hdmiDeviceInfo = (HdmiDeviceInfo) it.next();
                if (hdmiDeviceInfo.getId() == i) {
                    return hdmiDeviceInfo;
                }
            }
            return null;
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onStatusChanged(android.hardware.hdmi.HdmiDeviceInfo r7, int r8) {
            /*
                Method dump skipped, instructions count: 220
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.tv.TvInputHardwareManager.HdmiDeviceEventListener.onStatusChanged(android.hardware.hdmi.HdmiDeviceInfo, int):void");
        }
    }

    public final class HdmiHotplugEventListener extends IHdmiHotplugEventListener.Stub {
        public HdmiHotplugEventListener() {
        }

        public final void onReceived(HdmiHotplugEvent hdmiHotplugEvent) {
            synchronized (TvInputHardwareManager.this.mLock) {
                try {
                    TvInputHardwareManager.this.mHdmiStateMap.put(hdmiHotplugEvent.getPort(), hdmiHotplugEvent.isConnected());
                    TvInputHardwareInfo findHardwareInfoForHdmiPortLocked = TvInputHardwareManager.this.findHardwareInfoForHdmiPortLocked(hdmiHotplugEvent.getPort());
                    if (findHardwareInfoForHdmiPortLocked == null) {
                        return;
                    }
                    String str = (String) TvInputHardwareManager.this.mHardwareInputIdMap.get(findHardwareInfoForHdmiPortLocked.getDeviceId());
                    if (str == null) {
                        return;
                    }
                    TvInputHardwareManager.this.mHandler.obtainMessage(1, !hdmiHotplugEvent.isConnected() ? 1 : 0, 0, str).sendToTarget();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class HdmiSystemAudioModeChangeListener extends IHdmiSystemAudioModeChangeListener.Stub {
        public HdmiSystemAudioModeChangeListener() {
        }

        public final void onStatusChanged(boolean z) {
            synchronized (TvInputHardwareManager.this.mLock) {
                for (int i = 0; i < TvInputHardwareManager.this.mConnections.size(); i++) {
                    try {
                        TvInputHardwareImpl tvInputHardwareImpl = ((Connection) TvInputHardwareManager.this.mConnections.valueAt(i)).mHardware;
                        if (tvInputHardwareImpl != null) {
                            synchronized (tvInputHardwareImpl.mImplLock) {
                                tvInputHardwareImpl.updateAudioConfigLocked();
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    public final class ListenerHandler extends Handler {
        public ListenerHandler() {
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            String str;
            TvInputManagerService.UserState orCreateUserStateLocked;
            TvInputManagerService.TvInputState tvInputState;
            switch (message.what) {
                case 1:
                    TvInputHardwareManager.this.mListener.onStateChanged(message.arg1, (String) message.obj);
                    return;
                case 2:
                    TvInputHardwareManager.this.mListener.onHardwareDeviceAdded((TvInputHardwareInfo) message.obj);
                    return;
                case 3:
                    TvInputHardwareManager.this.mListener.onHardwareDeviceRemoved((TvInputHardwareInfo) message.obj);
                    return;
                case 4:
                    TvInputHardwareManager.this.mListener.onHdmiDeviceAdded((HdmiDeviceInfo) message.obj);
                    return;
                case 5:
                    TvInputHardwareManager.this.mListener.onHdmiDeviceRemoved((HdmiDeviceInfo) message.obj);
                    return;
                case 6:
                    HdmiDeviceInfo hdmiDeviceInfo = (HdmiDeviceInfo) message.obj;
                    synchronized (TvInputHardwareManager.this.mLock) {
                        str = (String) TvInputHardwareManager.this.mHdmiInputIdMap.get(hdmiDeviceInfo.getId());
                    }
                    if (str == null) {
                        Slog.w("TvInputHardwareManager", "Could not resolve input ID matching the device info; ignoring.");
                        return;
                    }
                    TvInputManagerService.HardwareListener hardwareListener = TvInputHardwareManager.this.mListener;
                    synchronized (TvInputManagerService.this.mLock) {
                        try {
                            int devicePowerStatus = hdmiDeviceInfo.getDevicePowerStatus();
                            Integer num = devicePowerStatus != 0 ? (devicePowerStatus == 1 || devicePowerStatus == 2 || devicePowerStatus == 3) ? 1 : null : 0;
                            if (num != null) {
                                TvInputManagerService.m985$$Nest$msetStateLocked(TvInputManagerService.this, str, num.intValue(), TvInputManagerService.this.mCurrentUserId);
                            }
                            TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                        } catch (RemoteException e) {
                            Slog.e("TvInputManagerService", "error in notifyHdmiDeviceUpdated", e);
                        } finally {
                        }
                        for (TvInputManagerService.ServiceState serviceState : ((HashMap) tvInputManagerService.getOrCreateUserStateLocked(tvInputManagerService.mCurrentUserId).serviceStateMap).values()) {
                            if (serviceState.isHardware) {
                                TvInputManagerService tvInputManagerService2 = TvInputManagerService.this;
                                tvInputManagerService2.bindService(serviceState, tvInputManagerService2.mCurrentUserId);
                                ITvInputService iTvInputService = serviceState.service;
                                if (iTvInputService != null) {
                                    iTvInputService.notifyHdmiDeviceUpdated(hdmiDeviceInfo);
                                } else {
                                    ((ArrayList) serviceState.hdmiDeviceUpdatedBuffer).add(hdmiDeviceInfo);
                                }
                            }
                        }
                        TvInputManagerService tvInputManagerService3 = TvInputManagerService.this;
                        tvInputManagerService3.updateHardwareServiceConnectionDelayed(tvInputManagerService3.mCurrentUserId);
                    }
                    return;
                case 7:
                    int i = message.arg1;
                    int i2 = message.arg2;
                    Connection connection = (Connection) message.obj;
                    int configsLengthLocked = connection.getConfigsLengthLocked();
                    int m977$$Nest$mgetInputStateLocked = Connection.m977$$Nest$mgetInputStateLocked(connection);
                    String str2 = (String) TvInputHardwareManager.this.mHardwareInputIdMap.get(i);
                    if (str2 != null) {
                        synchronized (TvInputHardwareManager.this.mLock) {
                            try {
                                if (!connection.updateCableConnectionStatusLocked(i2)) {
                                    if ((configsLengthLocked == 0) != (connection.getConfigsLengthLocked() == 0)) {
                                        TvInputHardwareManager.this.mHandler.obtainMessage(1, Connection.m977$$Nest$mgetInputStateLocked(connection), 0, str2).sendToTarget();
                                    }
                                } else if (m977$$Nest$mgetInputStateLocked != Connection.m977$$Nest$mgetInputStateLocked(connection)) {
                                    TvInputHardwareManager.this.mHandler.obtainMessage(1, Connection.m977$$Nest$mgetInputStateLocked(connection), 0, str2).sendToTarget();
                                }
                            } finally {
                            }
                        }
                        return;
                    }
                    return;
                case 8:
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    String str3 = (String) someArgs.arg1;
                    Bundle bundle = (Bundle) someArgs.arg2;
                    int i3 = message.arg1;
                    TvInputManagerService.HardwareListener hardwareListener2 = TvInputHardwareManager.this.mListener;
                    synchronized (TvInputManagerService.this.mLock) {
                        try {
                            TvInputManagerService tvInputManagerService4 = TvInputManagerService.this;
                            orCreateUserStateLocked = tvInputManagerService4.getOrCreateUserStateLocked(tvInputManagerService4.mCurrentUserId);
                            tvInputState = (TvInputManagerService.TvInputState) orCreateUserStateLocked.inputMap.get(str3);
                        } catch (RemoteException e2) {
                            Slog.e("TvInputManagerService", "error in onTvMessage", e2);
                        } finally {
                        }
                        if (tvInputState == null) {
                            Slog.e("TvInputManagerService", "failed to send TV message - unknown input id " + str3);
                        } else {
                            Iterator it = ((ArrayList) ((TvInputManagerService.ServiceState) ((HashMap) orCreateUserStateLocked.serviceStateMap).get(tvInputState.info.getComponent())).sessionTokens).iterator();
                            while (it.hasNext()) {
                                TvInputManagerService.SessionState sessionStateLocked = TvInputManagerService.this.getSessionStateLocked(Binder.getCallingUid(), (IBinder) it.next(), TvInputManagerService.this.mCurrentUserId);
                                if (!sessionStateLocked.isRecordingSession && sessionStateLocked.hardwareSessionToken != null) {
                                    sessionStateLocked.session.notifyTvMessage(i3, bundle);
                                }
                            }
                        }
                    }
                    someArgs.recycle();
                    return;
                default:
                    Slog.w("TvInputHardwareManager", "Unhandled message: " + message);
                    return;
            }
        }
    }

    public final class TvInputHardwareImpl extends ITvInputHardware.Stub {
        public TvStreamConfig mActiveConfig;
        public final AnonymousClass1 mAudioListener;
        public AudioPatch mAudioPatch;
        public List mAudioSink;
        public AudioDevicePort mAudioSource;
        public float mCommittedVolume;
        public int mDesiredChannelMask;
        public int mDesiredFormat;
        public int mDesiredSamplingRate;
        public final Object mImplLock;
        public final TvInputHardwareInfo mInfo;
        public String mOverrideAudioAddress;
        public int mOverrideAudioType;
        public boolean mReleased;
        public float mSourceVolume;

        /* renamed from: -$$Nest$mstartCapture, reason: not valid java name */
        public static boolean m978$$Nest$mstartCapture(TvInputHardwareImpl tvInputHardwareImpl, Surface surface, TvStreamConfig tvStreamConfig) {
            boolean z;
            synchronized (tvInputHardwareImpl.mImplLock) {
                try {
                    if (!tvInputHardwareImpl.mReleased) {
                        if (surface != null && tvStreamConfig != null) {
                            if (tvStreamConfig.getType() == 2) {
                                z = TvInputHardwareManager.this.mHal.addOrUpdateStream(tvInputHardwareImpl.mInfo.getDeviceId(), surface, tvStreamConfig) == 0;
                            }
                        }
                    }
                } finally {
                }
            }
            return z;
        }

        public TvInputHardwareImpl(TvInputHardwareInfo tvInputHardwareInfo) {
            Object obj = new Object();
            this.mImplLock = obj;
            ?? r1 = new AudioManager.OnAudioPortUpdateListener() { // from class: com.android.server.tv.TvInputHardwareManager.TvInputHardwareImpl.1
                public final void onAudioPatchListUpdate(AudioPatch[] audioPatchArr) {
                }

                public final void onAudioPortListUpdate(AudioPort[] audioPortArr) {
                    synchronized (TvInputHardwareImpl.this.mImplLock) {
                        TvInputHardwareImpl.this.updateAudioConfigLocked();
                    }
                }

                public final void onServiceDied() {
                    synchronized (TvInputHardwareImpl.this.mImplLock) {
                        try {
                            TvInputHardwareImpl tvInputHardwareImpl = TvInputHardwareImpl.this;
                            tvInputHardwareImpl.mAudioSource = null;
                            ((ArrayList) tvInputHardwareImpl.mAudioSink).clear();
                            TvInputHardwareImpl tvInputHardwareImpl2 = TvInputHardwareImpl.this;
                            AudioPatch audioPatch = tvInputHardwareImpl2.mAudioPatch;
                            if (audioPatch != null) {
                                AudioManager audioManager = TvInputHardwareManager.this.mAudioManager;
                                AudioManager.releaseAudioPatch(audioPatch);
                                TvInputHardwareImpl.this.mAudioPatch = null;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            };
            this.mAudioListener = r1;
            this.mReleased = false;
            this.mOverrideAudioType = 0;
            this.mOverrideAudioAddress = "";
            this.mAudioSink = new ArrayList();
            this.mAudioPatch = null;
            this.mCommittedVolume = -1.0f;
            this.mSourceVolume = FullScreenMagnificationGestureHandler.MAX_SCALE;
            this.mActiveConfig = null;
            this.mDesiredSamplingRate = 0;
            this.mDesiredChannelMask = 1;
            this.mDesiredFormat = 1;
            this.mInfo = tvInputHardwareInfo;
            TvInputHardwareManager.this.mAudioManager.registerAudioPortUpdateListener(r1);
            if (tvInputHardwareInfo.getAudioType() != 0) {
                synchronized (obj) {
                    this.mAudioSource = findAudioDevicePort(tvInputHardwareInfo.getAudioType(), tvInputHardwareInfo.getAudioAddress());
                    findAudioSinkFromAudioPolicy(this.mAudioSink);
                }
            }
        }

        public final AudioDevicePort findAudioDevicePort(int i, String str) {
            if (i == 0) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            AudioManager audioManager = TvInputHardwareManager.this.mAudioManager;
            if (AudioManager.listAudioDevicePorts(arrayList) != 0) {
                return null;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                AudioDevicePort audioDevicePort = (AudioDevicePort) it.next();
                if (audioDevicePort.type() == i && audioDevicePort.address().equals(str)) {
                    return audioDevicePort;
                }
            }
            return null;
        }

        public final void findAudioSinkFromAudioPolicy(List list) {
            list.clear();
            ArrayList arrayList = new ArrayList();
            AudioManager audioManager = TvInputHardwareManager.this.mAudioManager;
            if (AudioManager.listAudioDevicePorts(arrayList) != 0) {
                return;
            }
            int devicesForStream = TvInputHardwareManager.this.mAudioManager.getDevicesForStream(3);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                AudioDevicePort audioDevicePort = (AudioDevicePort) it.next();
                if ((audioDevicePort.type() & devicesForStream) != 0 && !AudioSystem.isInputDevice(audioDevicePort.type())) {
                    list.add(audioDevicePort);
                }
            }
        }

        public final void overrideAudioSink(int i, String str, int i2, int i3, int i4) {
            synchronized (this.mImplLock) {
                this.mOverrideAudioType = i;
                this.mOverrideAudioAddress = str;
                this.mDesiredSamplingRate = i2;
                this.mDesiredChannelMask = i3;
                this.mDesiredFormat = i4;
                updateAudioConfigLocked();
            }
        }

        public final void release() {
            synchronized (this.mImplLock) {
                try {
                    TvInputHardwareManager.this.mAudioManager.unregisterAudioPortUpdateListener(this.mAudioListener);
                    AudioPatch audioPatch = this.mAudioPatch;
                    if (audioPatch != null) {
                        AudioManager audioManager = TvInputHardwareManager.this.mAudioManager;
                        AudioManager.releaseAudioPatch(audioPatch);
                        this.mAudioPatch = null;
                    }
                    this.mReleased = true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setStreamVolume(float f) {
            synchronized (this.mImplLock) {
                try {
                    if (this.mReleased) {
                        throw new IllegalStateException("Device already released.");
                    }
                    this.mSourceVolume = f;
                    updateAudioConfigLocked();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean setSurface(Surface surface, TvStreamConfig tvStreamConfig) {
            int i;
            int i2;
            synchronized (this.mImplLock) {
                try {
                    if (this.mReleased) {
                        throw new IllegalStateException("Device already released.");
                    }
                    boolean z = true;
                    if (surface == null) {
                        if (this.mActiveConfig == null) {
                            return true;
                        }
                        i2 = TvInputHardwareManager.this.mHal.removeStream(this.mInfo.getDeviceId(), this.mActiveConfig);
                        this.mActiveConfig = null;
                    } else {
                        if (tvStreamConfig == null) {
                            return false;
                        }
                        TvStreamConfig tvStreamConfig2 = this.mActiveConfig;
                        if (tvStreamConfig2 == null || tvStreamConfig.equals(tvStreamConfig2)) {
                            i = 0;
                        } else {
                            i = TvInputHardwareManager.this.mHal.removeStream(this.mInfo.getDeviceId(), this.mActiveConfig);
                            if (i != 0) {
                                this.mActiveConfig = null;
                            }
                        }
                        if (i == 0) {
                            i2 = TvInputHardwareManager.this.mHal.addOrUpdateStream(this.mInfo.getDeviceId(), surface, tvStreamConfig);
                            if (i2 == 0) {
                                this.mActiveConfig = tvStreamConfig;
                            }
                        } else {
                            i2 = i;
                        }
                    }
                    updateAudioConfigLocked();
                    if (i2 != 0) {
                        z = false;
                    }
                    return z;
                } finally {
                }
            }
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void updateAudioConfigLocked() {
            /*
                Method dump skipped, instructions count: 624
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.tv.TvInputHardwareManager.TvInputHardwareImpl.updateAudioConfigLocked():void");
        }
    }

    /* renamed from: -$$Nest$smintArrayContains, reason: not valid java name */
    public static boolean m976$$Nest$smintArrayContains(int i, int[] iArr) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public TvInputHardwareManager(Context context, TvInputManagerService.HardwareListener hardwareListener) {
        TvInputHal tvInputHal = new TvInputHal(this);
        this.mHal = tvInputHal;
        this.mLock = new Object();
        this.mConnections = new SparseArray();
        this.mHardwareList = new ArrayList();
        this.mHdmiDeviceList = new ArrayList();
        this.mHardwareInputIdMap = new SparseArray();
        this.mHdmiInputIdMap = new SparseArray();
        this.mInputMap = new ArrayMap();
        this.mHdmiParentInputMap = new ArrayMap();
        this.mHdmiHotplugEventListener = new HdmiHotplugEventListener();
        this.mHdmiDeviceEventListener = new HdmiDeviceEventListener();
        this.mHdmiSystemAudioModeChangeListener = new HdmiSystemAudioModeChangeListener();
        this.mVolumeReceiver = new BroadcastReceiver() { // from class: com.android.server.tv.TvInputHardwareManager.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                int intExtra;
                TvInputHardwareManager tvInputHardwareManager = TvInputHardwareManager.this;
                tvInputHardwareManager.getClass();
                String action = intent.getAction();
                action.getClass();
                if (action.equals("android.media.VOLUME_CHANGED_ACTION")) {
                    if (intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) != 3 || (intExtra = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0)) == tvInputHardwareManager.mCurrentIndex) {
                        return;
                    } else {
                        tvInputHardwareManager.mCurrentIndex = intExtra;
                    }
                } else if (!action.equals("android.media.STREAM_MUTE_CHANGED_ACTION")) {
                    Slog.w("TvInputHardwareManager", "Unrecognized intent: " + intent);
                    return;
                } else if (intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) != 3) {
                    return;
                }
                synchronized (tvInputHardwareManager.mLock) {
                    for (int i = 0; i < tvInputHardwareManager.mConnections.size(); i++) {
                        try {
                            TvInputHardwareImpl tvInputHardwareImpl = ((Connection) tvInputHardwareManager.mConnections.valueAt(i)).mHardware;
                            if (tvInputHardwareImpl != null) {
                                synchronized (tvInputHardwareImpl.mImplLock) {
                                    tvInputHardwareImpl.updateAudioConfigLocked();
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
        };
        this.mCurrentIndex = 0;
        this.mCurrentMaxIndex = 0;
        this.mHdmiStateMap = new SparseBooleanArray();
        this.mPendingHdmiDeviceEvents = new ArrayList();
        this.mPendingTvinputInfoEvents = new ArrayList();
        this.mHandler = new ListenerHandler();
        this.mContext = context;
        this.mListener = hardwareListener;
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        tvInputHal.init();
    }

    public static int indexOfEqualValue(SparseArray sparseArray, Object obj) {
        for (int i = 0; i < sparseArray.size(); i++) {
            if (sparseArray.valueAt(i).equals(obj)) {
                return i;
            }
        }
        return -1;
    }

    public final TvInputHardwareImpl acquireHardware(int i, ITvInputHardwareCallback iTvInputHardwareCallback, TvInputInfo tvInputInfo, int i2, int i3, String str, int i4) {
        iTvInputHardwareCallback.getClass();
        TunerResourceManager tunerResourceManager = (TunerResourceManager) this.mContext.getSystemService("tv_tuner_resource_mgr");
        synchronized (this.mLock) {
            try {
                Connection connection = (Connection) this.mConnections.get(i);
                if (connection == null) {
                    Slog.e("TvInputHardwareManager", "Invalid deviceId : " + i);
                    return null;
                }
                ResourceClientProfile resourceClientProfile = new ResourceClientProfile();
                resourceClientProfile.tvInputSessionId = str;
                resourceClientProfile.useCase = i4;
                ResourceClientProfile resourceClientProfile2 = connection.mResourceClientProfile;
                if (resourceClientProfile2 != null && tunerResourceManager != null && !tunerResourceManager.isHigherPriority(resourceClientProfile, resourceClientProfile2)) {
                    Slog.d("TvInputHardwareManager", "Acquiring does not show higher priority than the current holder. Device id:" + i);
                    return null;
                }
                TvInputHardwareImpl tvInputHardwareImpl = new TvInputHardwareImpl(connection.mHardwareInfo);
                try {
                    iTvInputHardwareCallback.asBinder().linkToDeath(connection, 0);
                    connection.resetLocked(tvInputHardwareImpl, iTvInputHardwareCallback, tvInputInfo, Integer.valueOf(i2), Integer.valueOf(i3), resourceClientProfile);
                    return connection.mHardware;
                } catch (RemoteException unused) {
                    tvInputHardwareImpl.release();
                    return null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addHardwareInput(int i, TvInputInfo tvInputInfo) {
        String str;
        synchronized (this.mLock) {
            try {
                String str2 = (String) this.mHardwareInputIdMap.get(i);
                if (str2 != null) {
                    Slog.w("TvInputHardwareManager", "Trying to override previous registration: old = " + ((ArrayMap) this.mInputMap).get(str2) + ":" + i + ", new = " + tvInputInfo + ":" + i);
                }
                this.mHardwareInputIdMap.put(i, tvInputInfo.getId());
                ((ArrayMap) this.mInputMap).put(tvInputInfo.getId(), tvInputInfo);
                Iterator it = ((ArrayList) this.mPendingTvinputInfoEvents).iterator();
                while (it.hasNext()) {
                    Message message = (Message) it.next();
                    if (((String) this.mHardwareInputIdMap.get(message.arg1)) != null) {
                        message.sendToTarget();
                        it.remove();
                    }
                }
                Slog.d("TvInputHardwareManager", "deviceId =" + i + ", tvinputinfo = " + tvInputInfo);
                for (int i2 = 0; i2 < this.mHdmiStateMap.size(); i2++) {
                    TvInputHardwareInfo findHardwareInfoForHdmiPortLocked = findHardwareInfoForHdmiPortLocked(this.mHdmiStateMap.keyAt(i2));
                    if (findHardwareInfoForHdmiPortLocked != null && (str = (String) this.mHardwareInputIdMap.get(findHardwareInfoForHdmiPortLocked.getDeviceId())) != null && str.equals(tvInputInfo.getId())) {
                        this.mHandler.obtainMessage(1, !this.mHdmiStateMap.valueAt(i2) ? 1 : 0, 0, str).sendToTarget();
                        return;
                    }
                }
                Connection connection = (Connection) this.mConnections.get(i);
                if (connection != null) {
                    this.mHandler.obtainMessage(1, Connection.m977$$Nest$mgetInputStateLocked(connection), 0, tvInputInfo.getId()).sendToTarget();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addHdmiInput(int i, TvInputInfo tvInputInfo) {
        if (tvInputInfo.getType() != 1007) {
            throw new IllegalArgumentException("info (" + tvInputInfo + ") has non-HDMI type.");
        }
        synchronized (this.mLock) {
            try {
                String parentId = tvInputInfo.getParentId();
                if (indexOfEqualValue(this.mHardwareInputIdMap, parentId) < 0) {
                    throw new IllegalArgumentException("info (" + tvInputInfo + ") has invalid parentId.");
                }
                String str = (String) this.mHdmiInputIdMap.get(i);
                if (str != null) {
                    Slog.w("TvInputHardwareManager", "Trying to override previous registration: old = " + ((ArrayMap) this.mInputMap).get(str) + ":" + i + ", new = " + tvInputInfo + ":" + i);
                }
                this.mHdmiInputIdMap.put(i, tvInputInfo.getId());
                ((ArrayMap) this.mInputMap).put(tvInputInfo.getId(), tvInputInfo);
                if (!((ArrayMap) this.mHdmiParentInputMap).containsKey(parentId)) {
                    ((ArrayMap) this.mHdmiParentInputMap).put(parentId, new ArrayList());
                }
                ((List) ((ArrayMap) this.mHdmiParentInputMap).get(parentId)).add(tvInputInfo.getId());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void buildHardwareListLocked() {
        ((ArrayList) this.mHardwareList).clear();
        for (int i = 0; i < this.mConnections.size(); i++) {
            ((ArrayList) this.mHardwareList).add(((Connection) this.mConnections.valueAt(i)).mHardwareInfo);
        }
    }

    public final boolean captureFrame(String str, Surface surface, final TvStreamConfig tvStreamConfig) {
        synchronized (this.mLock) {
            try {
                int findDeviceIdForInputIdLocked = findDeviceIdForInputIdLocked(str);
                if (findDeviceIdForInputIdLocked < 0) {
                    Slog.e("TvInputHardwareManager", "Invalid inputId : " + str);
                    return false;
                }
                Connection connection = (Connection) this.mConnections.get(findDeviceIdForInputIdLocked);
                final TvInputHardwareImpl tvInputHardwareImpl = connection.mHardware;
                if (tvInputHardwareImpl == null) {
                    return false;
                }
                Runnable runnable = connection.mOnFirstFrameCaptured;
                if (runnable != null) {
                    runnable.run();
                    connection.mOnFirstFrameCaptured = null;
                }
                boolean m978$$Nest$mstartCapture = TvInputHardwareImpl.m978$$Nest$mstartCapture(tvInputHardwareImpl, surface, tvStreamConfig);
                if (m978$$Nest$mstartCapture) {
                    connection.mOnFirstFrameCaptured = new Runnable() { // from class: com.android.server.tv.TvInputHardwareManager.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvInputHardwareImpl tvInputHardwareImpl2 = TvInputHardwareImpl.this;
                            TvStreamConfig tvStreamConfig2 = tvStreamConfig;
                            synchronized (tvInputHardwareImpl2.mImplLock) {
                                try {
                                    if (tvInputHardwareImpl2.mReleased) {
                                        return;
                                    }
                                    if (tvStreamConfig2 == null) {
                                        return;
                                    }
                                    TvInputHardwareManager.this.mHal.removeStream(tvInputHardwareImpl2.mInfo.getDeviceId(), tvStreamConfig2);
                                } finally {
                                }
                            }
                        }
                    };
                }
                return m978$$Nest$mstartCapture;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int findDeviceIdForInputIdLocked(String str) {
        TvInputInfo tvInputInfo;
        for (int i = 0; i < this.mConnections.size(); i++) {
            int keyAt = this.mConnections.keyAt(i);
            Connection connection = (Connection) this.mConnections.get(keyAt);
            if (connection != null && (tvInputInfo = connection.mInfo) != null && tvInputInfo.getId().equals(str)) {
                return keyAt;
            }
        }
        return -1;
    }

    public final TvInputHardwareInfo findHardwareInfoForHdmiPortLocked(int i) {
        Iterator it = ((ArrayList) this.mHardwareList).iterator();
        while (it.hasNext()) {
            TvInputHardwareInfo tvInputHardwareInfo = (TvInputHardwareInfo) it.next();
            if (tvInputHardwareInfo.getType() == 9 && tvInputHardwareInfo.getHdmiPortId() == i) {
                return tvInputHardwareInfo;
            }
        }
        return null;
    }

    public final List getAvailableTvStreamConfigList(String str) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            try {
                int findDeviceIdForInputIdLocked = findDeviceIdForInputIdLocked(str);
                if (findDeviceIdForInputIdLocked < 0) {
                    Slog.e("TvInputHardwareManager", "Invalid inputId : " + str);
                    return arrayList;
                }
                for (TvStreamConfig tvStreamConfig : ((Connection) this.mConnections.get(findDeviceIdForInputIdLocked)).mConfigs) {
                    if (tvStreamConfig.getType() == 2) {
                        arrayList.add(tvStreamConfig);
                    }
                }
                return arrayList;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onDeviceAvailable(TvInputHardwareInfo tvInputHardwareInfo, TvStreamConfig[] tvStreamConfigArr) {
        synchronized (this.mLock) {
            Connection connection = new Connection(tvInputHardwareInfo);
            connection.mConfigs = tvStreamConfigArr;
            connection.updateCableConnectionStatusLocked(tvInputHardwareInfo.getCableConnectionStatus());
            this.mConnections.put(tvInputHardwareInfo.getDeviceId(), connection);
            buildHardwareListLocked();
            this.mHandler.obtainMessage(2, 0, 0, tvInputHardwareInfo).sendToTarget();
            if (tvInputHardwareInfo.getType() == 9) {
                Iterator it = ((ArrayList) this.mPendingHdmiDeviceEvents).iterator();
                while (it.hasNext()) {
                    Message message = (Message) it.next();
                    if (findHardwareInfoForHdmiPortLocked(((HdmiDeviceInfo) message.obj).getPortId()) != null) {
                        message.sendToTarget();
                        it.remove();
                    }
                }
            }
        }
    }

    public final void onDeviceUnavailable(int i) {
        synchronized (this.mLock) {
            try {
                Connection connection = (Connection) this.mConnections.get(i);
                if (connection == null) {
                    Slog.e("TvInputHardwareManager", "onDeviceUnavailable: Cannot find a connection with " + i);
                    return;
                }
                connection.resetLocked(null, null, null, null, null, null);
                this.mConnections.remove(i);
                buildHardwareListLocked();
                TvInputHardwareInfo tvInputHardwareInfo = connection.mHardwareInfo;
                if (tvInputHardwareInfo.getType() == 9) {
                    Iterator it = ((ArrayList) this.mHdmiDeviceList).iterator();
                    while (it.hasNext()) {
                        HdmiDeviceInfo hdmiDeviceInfo = (HdmiDeviceInfo) it.next();
                        if (hdmiDeviceInfo.getPortId() == tvInputHardwareInfo.getHdmiPortId()) {
                            this.mHandler.obtainMessage(5, 0, 0, hdmiDeviceInfo).sendToTarget();
                            it.remove();
                        }
                    }
                }
                this.mHandler.obtainMessage(3, 0, 0, tvInputHardwareInfo).sendToTarget();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onStreamConfigurationChanged(final int i, TvStreamConfig[] tvStreamConfigArr, int i2) {
        synchronized (this.mLock) {
            try {
                Connection connection = (Connection) this.mConnections.get(i);
                if (connection == null) {
                    Slog.e("TvInputHardwareManager", "StreamConfigurationChanged: Cannot find a connection with " + i);
                    return;
                }
                int configsLengthLocked = connection.getConfigsLengthLocked();
                int m977$$Nest$mgetInputStateLocked = Connection.m977$$Nest$mgetInputStateLocked(connection);
                connection.mConfigs = tvStreamConfigArr;
                String str = (String) this.mHardwareInputIdMap.get(i);
                if (str == null) {
                    Message obtainMessage = this.mHandler.obtainMessage(7, i, i2, connection);
                    ((ArrayList) this.mPendingTvinputInfoEvents).removeIf(new Predicate() { // from class: com.android.server.tv.TvInputHardwareManager$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return ((Message) obj).arg1 == i;
                        }
                    });
                    ((ArrayList) this.mPendingTvinputInfoEvents).add(obtainMessage);
                } else if (!connection.updateCableConnectionStatusLocked(i2)) {
                    if ((configsLengthLocked == 0) != (connection.getConfigsLengthLocked() == 0)) {
                        this.mHandler.obtainMessage(1, Connection.m977$$Nest$mgetInputStateLocked(connection), 0, str).sendToTarget();
                    }
                } else if (m977$$Nest$mgetInputStateLocked != Connection.m977$$Nest$mgetInputStateLocked(connection)) {
                    this.mHandler.obtainMessage(1, Connection.m977$$Nest$mgetInputStateLocked(connection), 0, str).sendToTarget();
                }
                ITvInputHardwareCallback iTvInputHardwareCallback = connection.mCallback;
                if (iTvInputHardwareCallback != null) {
                    try {
                        iTvInputHardwareCallback.onStreamConfigChanged(tvStreamConfigArr);
                    } catch (RemoteException e) {
                        Slog.e("TvInputHardwareManager", "error in onStreamConfigurationChanged", e);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void releaseHardware(int i, ITvInputHardware iTvInputHardware, int i2, int i3) {
        synchronized (this.mLock) {
            try {
                Connection connection = (Connection) this.mConnections.get(i);
                if (connection == null) {
                    Slog.e("TvInputHardwareManager", "Invalid deviceId : " + i);
                    return;
                }
                if (connection.mHardware == iTvInputHardware) {
                    Integer num = connection.mCallingUid;
                    Integer num2 = connection.mResolvedUserId;
                    if (num != null && num2 != null && num.intValue() == i2 && num2.intValue() == i3) {
                        ITvInputHardwareCallback iTvInputHardwareCallback = connection.mCallback;
                        if (iTvInputHardwareCallback != null) {
                            iTvInputHardwareCallback.asBinder().unlinkToDeath(connection, 0);
                        }
                        connection.resetLocked(null, null, null, null, null, null);
                    }
                }
            } finally {
            }
        }
    }

    public final void setTvMessageEnabled(int i, String str, boolean z) {
        synchronized (this.mLock) {
            try {
                int findDeviceIdForInputIdLocked = findDeviceIdForInputIdLocked(str);
                if (findDeviceIdForInputIdLocked < 0) {
                    Slog.e("TvInputHardwareManager", "Invalid inputId : " + str);
                    return;
                }
                boolean z2 = true;
                for (TvStreamConfig tvStreamConfig : ((Connection) this.mConnections.get(findDeviceIdForInputIdLocked)).mConfigs) {
                    z2 = z2 && this.mHal.setTvMessageEnabled(findDeviceIdForInputIdLocked, tvStreamConfig, i, z) == 0;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
