package com.android.server.hdmi;

import android.content.Intent;
import android.hardware.hdmi.DeviceFeatures;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.HdmiPortInfo;
import android.hardware.hdmi.HdmiRecordSources;
import android.hardware.hdmi.IHdmiControlCallback;
import android.hardware.hdmi.IHdmiRecordListener;
import android.media.AudioDeviceAttributes;
import android.media.tv.TvInputInfo;
import android.media.tv.TvInputManager;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseBooleanArray;

import com.android.internal.util.IndentingPrintWriter;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.attention.AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0;
import com.android.server.location.gnss.hal.GnssNative;

import libcore.util.EmptyArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class HdmiCecLocalDeviceTv extends HdmiCecLocalDevice {
    public boolean mArcEstablished;
    public final SparseBooleanArray mArcFeatureEnabled;
    public final DelayedMessageBuffer mDelayedMessageBuffer;
    public int mPrevPortId;
    public final HdmiCecLocalDeviceTv$$ExternalSyntheticLambda0 mResetSkipRoutingControlRunnable;
    public SelectRequestBuffer mSelectRequestBuffer;
    public boolean mSkipRoutingControl;
    public final Handler mSkipRoutingControlHandler;
    public boolean mSystemAudioControlFeatureEnabled;
    public boolean mSystemAudioMute;
    public int mSystemAudioVolume;
    public final AnonymousClass1 mTvInputCallback;
    public final HashMap mTvInputs;

    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.server.hdmi.HdmiCecLocalDeviceTv$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.server.hdmi.HdmiCecLocalDeviceTv$1] */
    public HdmiCecLocalDeviceTv(HdmiControlService hdmiControlService) {
        super(hdmiControlService, 0);
        this.mArcEstablished = false;
        this.mArcFeatureEnabled = new SparseBooleanArray();
        this.mSystemAudioVolume = -1;
        this.mSystemAudioMute = false;
        this.mResetSkipRoutingControlRunnable =
                new Runnable() { // from class:
                                 // com.android.server.hdmi.HdmiCecLocalDeviceTv$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        HdmiCecLocalDeviceTv.this.mSkipRoutingControl = false;
                    }
                };
        this.mDelayedMessageBuffer = new DelayedMessageBuffer(this);
        this.mTvInputCallback =
                new TvInputManager
                        .TvInputCallback() { // from class:
                                             // com.android.server.hdmi.HdmiCecLocalDeviceTv.1
                    @Override // android.media.tv.TvInputManager.TvInputCallback
                    public final void onInputAdded(String str) {
                        HdmiDeviceInfo hdmiDeviceInfo;
                        TvInputInfo tvInputInfo =
                                HdmiCecLocalDeviceTv.this.mService.mTvInputManager.getTvInputInfo(
                                        str);
                        if (tvInputInfo == null
                                || (hdmiDeviceInfo = tvInputInfo.getHdmiDeviceInfo()) == null) {
                            return;
                        }
                        HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv = HdmiCecLocalDeviceTv.this;
                        int id = hdmiDeviceInfo.getId();
                        hdmiCecLocalDeviceTv.assertRunOnServiceThread();
                        hdmiCecLocalDeviceTv.mTvInputs.put(str, Integer.valueOf(id));
                        if (hdmiDeviceInfo.isCecDevice()) {
                            HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv2 = HdmiCecLocalDeviceTv.this;
                            int logicalAddress = hdmiDeviceInfo.getLogicalAddress();
                            hdmiCecLocalDeviceTv2.assertRunOnServiceThread();
                            hdmiCecLocalDeviceTv2.mDelayedMessageBuffer.processActiveSource(
                                    logicalAddress);
                        }
                    }

                    @Override // android.media.tv.TvInputManager.TvInputCallback
                    public final void onInputRemoved(String str) {
                        HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv = HdmiCecLocalDeviceTv.this;
                        hdmiCecLocalDeviceTv.assertRunOnServiceThread();
                        hdmiCecLocalDeviceTv.mTvInputs.remove(str);
                    }
                };
        this.mTvInputs = new HashMap();
        this.mPrevPortId = -1;
        this.mSystemAudioControlFeatureEnabled =
                hdmiControlService.getHdmiCecConfig().getIntValue("system_audio_control") == 1;
        this.mStandbyHandler = new HdmiCecStandbyModeHandler(hdmiControlService, this);
        this.mSkipRoutingControlHandler = new Handler(hdmiControlService.mHandler.getLooper());
    }

    public static boolean isTailOfActivePath(int i, int i2) {
        if (i2 == 0) {
            return false;
        }
        for (int i3 = 12; i3 >= 0; i3 -= 4) {
            int i4 = (i2 >> i3) & 15;
            if (i4 == 0) {
                return true;
            }
            if (((i >> i3) & 15) != i4) {
                return false;
            }
        }
        return false;
    }

    public final void announceClearTimerRecordingResult(int i, int i2) {
        HdmiControlService hdmiControlService = this.mService;
        synchronized (hdmiControlService.mLock) {
            HdmiControlService.HdmiRecordListenerRecord hdmiRecordListenerRecord =
                    hdmiControlService.mRecordListenerRecord;
            if (hdmiRecordListenerRecord != null) {
                try {
                    ((IHdmiRecordListener) hdmiRecordListenerRecord.mListener)
                            .onClearTimerRecordingResult(i, i2);
                } catch (RemoteException e) {
                    Slog.w("HdmiControlService", "Failed to call onClearTimerRecordingResult.", e);
                }
            }
        }
    }

    public final void announceOneTouchRecordResult(int i, int i2) {
        HdmiControlService hdmiControlService = this.mService;
        synchronized (hdmiControlService.mLock) {
            HdmiControlService.HdmiRecordListenerRecord hdmiRecordListenerRecord =
                    hdmiControlService.mRecordListenerRecord;
            if (hdmiRecordListenerRecord != null) {
                try {
                    ((IHdmiRecordListener) hdmiRecordListenerRecord.mListener)
                            .onOneTouchRecordResult(i, i2);
                } catch (RemoteException e) {
                    Slog.w("HdmiControlService", "Failed to call onOneTouchRecordResult.", e);
                }
            }
        }
    }

    public final void announceTimerRecordingResult(int i, int i2) {
        HdmiControlService hdmiControlService = this.mService;
        synchronized (hdmiControlService.mLock) {
            HdmiControlService.HdmiRecordListenerRecord hdmiRecordListenerRecord =
                    hdmiControlService.mRecordListenerRecord;
            if (hdmiRecordListenerRecord != null) {
                try {
                    ((IHdmiRecordListener) hdmiRecordListenerRecord.mListener)
                            .onTimerRecordingResult(i, i2);
                } catch (RemoteException e) {
                    Slog.w("HdmiControlService", "Failed to call onTimerRecordingResult.", e);
                }
            }
        }
    }

    public final boolean broadcastMenuLanguage(String str) {
        HdmiCecMessage build;
        assertRunOnServiceThread();
        int logicalAddress = getDeviceInfo().getLogicalAddress();
        if (str.length() != 3) {
            build = null;
        } else {
            String lowerCase = str.toLowerCase();
            build =
                    HdmiCecMessage.build(
                            logicalAddress,
                            15,
                            50,
                            new byte[] {
                                (byte) (lowerCase.charAt(0) & 255),
                                (byte) (lowerCase.charAt(1) & 255),
                                (byte) (lowerCase.charAt(2) & 255)
                            });
        }
        if (build == null) {
            return false;
        }
        this.mService.sendCecCommand(build);
        return true;
    }

    public final boolean canStartArcUpdateAction(int i, boolean z) {
        HdmiDeviceInfo avrDeviceInfo = getAvrDeviceInfo();
        if (avrDeviceInfo == null
                || i != avrDeviceInfo.getLogicalAddress()
                || !isConnectedToArcPort(avrDeviceInfo.getPhysicalAddress())) {
            return false;
        }
        if (!z) {
            return true;
        }
        int portId = avrDeviceInfo.getPortId();
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        hdmiControlService.assertRunOnServiceThread();
        HdmiCecController hdmiCecController = hdmiControlService.mCecController;
        hdmiCecController.assertRunOnServiceThread();
        if (!hdmiCecController.mNativeWrapperImpl.nativeIsConnected(portId)) {
            return false;
        }
        int portId2 = avrDeviceInfo.getPortId();
        assertRunOnServiceThread();
        if (!this.mArcFeatureEnabled.get(portId2)) {
            return false;
        }
        int physicalAddress = avrDeviceInfo.getPhysicalAddress();
        return (61440 & physicalAddress) == physicalAddress;
    }

    public final void changeSystemAudioMode(boolean z, IHdmiControlCallback iHdmiControlCallback) {
        assertRunOnServiceThread();
        if (!this.mService.isCecControlEnabled() || hasAction(DeviceDiscoveryAction.class)) {
            setSystemAudioMode$1(false);
            invokeCallback(6, iHdmiControlCallback);
            return;
        }
        HdmiDeviceInfo avrDeviceInfo = getAvrDeviceInfo();
        if (avrDeviceInfo != null) {
            addAndStartAction(
                    new SystemAudioActionFromTv(
                            this, avrDeviceInfo.getLogicalAddress(), iHdmiControlCallback, z));
        } else {
            setSystemAudioMode$1(false);
            invokeCallback(3, iHdmiControlCallback);
        }
    }

    public final boolean checkRecorder(int i) {
        return this.mService.mHdmiCecNetwork.getCecDeviceInfo(i) != null
                && HdmiUtils.isEligibleAddressForDevice(1, i);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final DeviceFeatures computeDeviceFeatures() {
        int i;
        Iterator it = this.mService.getPortInfo().iterator();
        while (true) {
            if (!it.hasNext()) {
                i = 0;
                break;
            }
            int id = ((HdmiPortInfo) it.next()).getId();
            assertRunOnServiceThread();
            if (this.mArcFeatureEnabled.get(id)) {
                i = 1;
                break;
            }
        }
        return DeviceFeatures.NO_FEATURES_SUPPORTED.toBuilder()
                .setRecordTvScreenSupport(1)
                .setArcTxSupport(i)
                .setSetAudioVolumeLevelSupport(1)
                .build();
    }

    public final void deviceSelect(int i, IHdmiControlCallback iHdmiControlCallback) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        HdmiDeviceInfo hdmiDeviceInfo =
                (HdmiDeviceInfo) hdmiControlService.mHdmiCecNetwork.mDeviceInfos.get(i);
        if (hdmiDeviceInfo == null) {
            invokeCallback(3, iHdmiControlCallback);
            return;
        }
        int logicalAddress = hdmiDeviceInfo.getLogicalAddress();
        if (isAlreadyActiveSource(hdmiDeviceInfo, logicalAddress, iHdmiControlCallback)) {
            return;
        }
        removeAction(RequestActiveSourceAction.class);
        boolean z = false;
        if (logicalAddress != 0) {
            if (!hdmiControlService.isCecControlEnabled()) {
                setActiveSource(
                        hdmiDeviceInfo.getLogicalAddress(),
                        hdmiDeviceInfo.getPhysicalAddress(),
                        "HdmiCecLocalDeviceTv#deviceSelect()");
                invokeCallback(6, iHdmiControlCallback);
                return;
            }
            removeAction(DeviceSelectActionFromTv.class);
            if (getDeviceInfo().getCecVersion() >= 6 && hdmiDeviceInfo.getCecVersion() >= 6) {
                z = true;
            }
            addAndStartAction(
                    new DeviceSelectActionFromTv(this, hdmiDeviceInfo, iHdmiControlCallback, z));
            return;
        }
        assertRunOnServiceThread();
        if (hdmiControlService.isCecControlEnabled()
                && hdmiControlService.getLocalActiveSource().logicalAddress
                        != getDeviceInfo().getLogicalAddress()) {
            int logicalAddress2 = getDeviceInfo().getLogicalAddress();
            int physicalAddress = hdmiControlService.mHdmiCecNetwork.getPhysicalAddress();
            assertRunOnServiceThread();
            updateActiveSource(
                    new HdmiCecLocalDevice.ActiveSource(logicalAddress2, physicalAddress),
                    "HdmiCecLocalDeviceTv#handleSelectInternalSource()");
            if (this.mSkipRoutingControl) {
                this.mSkipRoutingControl = false;
            } else {
                hdmiControlService.sendCecCommand(
                        HdmiCecMessageBuilder.buildActiveSource(
                                getDeviceInfo().getLogicalAddress(),
                                hdmiControlService.mHdmiCecNetwork.getPhysicalAddress()),
                        null);
            }
        }
        setActiveSource(
                logicalAddress,
                hdmiControlService.mHdmiCecNetwork.getPhysicalAddress(),
                "HdmiCecLocalDeviceTv#deviceSelect()");
        setActivePath(hdmiControlService.mHdmiCecNetwork.getPhysicalAddress());
        invokeCallback(0, iHdmiControlCallback);
    }

    public final void disableArc() {
        assertRunOnServiceThread();
        HdmiLogger.debug("Set Arc Status[old:%b new:false]", Boolean.valueOf(this.mArcEstablished));
        enableAudioReturnChannel$1(false);
        notifyArcStatusToAudioService(new ArrayList(), false);
        this.mArcEstablished = false;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void disableDevice(
            boolean z,
            HdmiCecLocalDevice.PendingActionClearedCallback pendingActionClearedCallback) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        TvInputManager tvInputManager = hdmiControlService.mTvInputManager;
        if (tvInputManager != null) {
            tvInputManager.unregisterCallback(this.mTvInputCallback);
        }
        removeAction(DeviceDiscoveryAction.class);
        removeAction(HotplugDetectionAction.class);
        removeAction(PowerStatusMonitorAction.class);
        removeAction(OneTouchRecordAction.class);
        removeAction(TimerRecordingAction.class);
        removeAction(NewDeviceAction.class);
        removeAction(RequestActiveSourceAction.class);
        if (z || !hdmiControlService.isEarcEnabled()) {
            assertRunOnServiceThread();
            if (getAvrDeviceInfo() != null) {
                removeAction(SystemAudioActionFromAvr.class);
                removeAction(SystemAudioActionFromTv.class);
                removeAction(SystemAudioAutoInitiationAction.class);
                removeAction(VolumeControlAction.class);
                if (!hdmiControlService.isCecControlEnabled()) {
                    setSystemAudioMode$1(false);
                }
            }
        }
        assertRunOnServiceThread();
        HdmiDeviceInfo avrDeviceInfo = getAvrDeviceInfo();
        if (avrDeviceInfo != null) {
            removeAction(RequestArcTerminationAction.class);
            removeAction(RequestArcInitiationAction.class);
            removeAction(SetArcTransmissionStateAction.class);
            if (!hasAction(RequestArcTerminationAction.class) && isArcEstablished()) {
                addAndStartAction(
                        new RequestArcTerminationAction(
                                this, avrDeviceInfo.getLogicalAddress(), null));
            }
            for (HdmiPortInfo hdmiPortInfo : hdmiControlService.getPortInfo()) {
                int id = hdmiPortInfo.getId();
                assertRunOnServiceThread();
                if (this.mArcFeatureEnabled.get(id)) {
                    hdmiControlService.enableAudioReturnChannel(hdmiPortInfo.getId(), false);
                }
            }
        }
        super.disableDevice(z, pendingActionClearedCallback);
        assertRunOnServiceThread();
        hdmiControlService.mHdmiCecNetwork.clearDeviceList();
        hdmiControlService.getLocalActiveSource().invalidate();
        setActivePath(GnssNative.GNSS_AIDING_TYPE_ALL);
        checkIfPendingActionsCleared();
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public int dispatchMessage(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        hdmiControlService.assertRunOnServiceThread();
        if (hdmiControlService.mPowerStatusController.mPowerStatus == 1
                && !hdmiControlService.mWakeUpMessageReceived) {
            HdmiCecStandbyModeHandler hdmiCecStandbyModeHandler = this.mStandbyHandler;
            HdmiCecStandbyModeHandler.CecMessageHandler cecMessageHandler =
                    (HdmiCecStandbyModeHandler.CecMessageHandler)
                            hdmiCecStandbyModeHandler.mCecMessageHandlers.get(
                                    hdmiCecMessage.mOpcode);
            if (cecMessageHandler != null
                    ? cecMessageHandler.handle(hdmiCecMessage)
                    : hdmiCecStandbyModeHandler.mDefaultHandler.handle(hdmiCecMessage)) {
                return -1;
            }
        }
        return onMessage(hdmiCecMessage);
    }

    public final void doManualPortSwitching(int i, IHdmiControlCallback iHdmiControlCallback) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        if (!(hdmiControlService.mHdmiCecNetwork.getPortInfo(i) != null)) {
            invokeCallback(6, iHdmiControlCallback);
            return;
        }
        if (i == getActivePortId()) {
            invokeCallback(0, iHdmiControlCallback);
            return;
        }
        hdmiControlService.getLocalActiveSource().invalidate();
        if (!hdmiControlService.isCecControlEnabled()) {
            setActivePath(hdmiControlService.portIdToPath(i));
            invokeCallback(6, iHdmiControlCallback);
            return;
        }
        int physicalAddress =
                (getActivePortId() == -1 || getActivePortId() == 0)
                        ? getDeviceInfo().getPhysicalAddress()
                        : hdmiControlService.portIdToPath(getActivePortId());
        setActivePath(physicalAddress);
        if (this.mSkipRoutingControl) {
            this.mSkipRoutingControl = false;
        } else {
            startRoutingControl(
                    physicalAddress, hdmiControlService.portIdToPath(i), iHdmiControlCallback);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        super.dump(indentingPrintWriter);
        StringBuilder m =
                AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0.m(
                        new StringBuilder("mArcEstablished: "),
                        this.mArcEstablished,
                        indentingPrintWriter,
                        "mArcFeatureEnabled: ");
        m.append(this.mArcFeatureEnabled);
        indentingPrintWriter.println(m.toString());
        StringBuilder m2 =
                AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0.m(
                        AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0.m(
                                AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("mSystemAudioMute: "),
                                        this.mSystemAudioMute,
                                        indentingPrintWriter,
                                        "mSystemAudioControlFeatureEnabled: "),
                                this.mSystemAudioControlFeatureEnabled,
                                indentingPrintWriter,
                                "mSkipRoutingControl: "),
                        this.mSkipRoutingControl,
                        indentingPrintWriter,
                        "mPrevPortId: ");
        m2.append(this.mPrevPortId);
        indentingPrintWriter.println(m2.toString());
    }

    public final void enableAudioReturnChannel$1(boolean z) {
        assertRunOnServiceThread();
        HdmiDeviceInfo avrDeviceInfo = getAvrDeviceInfo();
        if (avrDeviceInfo == null || avrDeviceInfo.getPortId() == -1) {
            return;
        }
        this.mService.enableAudioReturnChannel(avrDeviceInfo.getPortId(), z);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int findAudioReceiverAddress() {
        return 5;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int findKeyReceiverAddress() {
        HdmiDeviceInfo hdmiDeviceInfo;
        HdmiControlService hdmiControlService = this.mService;
        if (HdmiUtils.isValidAddress(hdmiControlService.getLocalActiveSource().logicalAddress)) {
            return hdmiControlService.getLocalActiveSource().logicalAddress;
        }
        HdmiCecNetwork hdmiCecNetwork = hdmiControlService.mHdmiCecNetwork;
        int activePath = getActivePath();
        hdmiCecNetwork.assertRunOnServiceThread();
        Iterator it = ((ArrayList) hdmiCecNetwork.getDeviceInfoList()).iterator();
        while (true) {
            if (!it.hasNext()) {
                hdmiDeviceInfo = null;
                break;
            }
            hdmiDeviceInfo = (HdmiDeviceInfo) it.next();
            if (hdmiDeviceInfo.getPhysicalAddress() == activePath) {
                break;
            }
        }
        if (hdmiDeviceInfo != null) {
            return hdmiDeviceInfo.getLogicalAddress();
        }
        return -1;
    }

    public final boolean getAutoWakeup() {
        assertRunOnServiceThread();
        return this.mService.getHdmiCecConfig().getIntValue("tv_wake_on_one_touch_play") == 1;
    }

    public final HdmiDeviceInfo getAvrDeviceInfo() {
        assertRunOnServiceThread();
        return this.mService.mHdmiCecNetwork.getCecDeviceInfo(5);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int getPreferredAddress() {
        return 0;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final List getRcFeatures() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                Integer.valueOf(this.mService.getHdmiCecConfig().getIntValue("rc_profile_tv")));
        return arrayList;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int getRcProfile() {
        return 0;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void handleActiveSource(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int i = hdmiCecMessage.mSource;
        int twoBytesToInt = HdmiUtils.twoBytesToInt(hdmiCecMessage.mParams);
        HdmiControlService hdmiControlService = this.mService;
        HdmiDeviceInfo cecDeviceInfo = hdmiControlService.mHdmiCecNetwork.getCecDeviceInfo(i);
        DelayedMessageBuffer delayedMessageBuffer = this.mDelayedMessageBuffer;
        if (cecDeviceInfo == null) {
            if (handleNewDeviceAtTheTailOfActivePath(twoBytesToInt)) {
                return;
            }
            HdmiLogger.debug("Device info %X not found; buffering the command", Integer.valueOf(i));
            delayedMessageBuffer.add(hdmiCecMessage);
            return;
        }
        if (!isInputReady(cecDeviceInfo.getId()) && cecDeviceInfo.getDeviceType() != 5) {
            HdmiLogger.debug(
                    "Input not ready for device: %X; buffering the command",
                    Integer.valueOf(cecDeviceInfo.getId()));
            delayedMessageBuffer.add(hdmiCecMessage);
            return;
        }
        hdmiControlService.mHdmiCecNetwork.updateDevicePowerStatus(i, 0);
        HdmiCecLocalDevice.ActiveSource activeSource =
                new HdmiCecLocalDevice.ActiveSource(i, twoBytesToInt);
        int deviceType = cecDeviceInfo.getDeviceType();
        int i2 = activeSource.logicalAddress;
        hdmiControlService.assertRunOnServiceThread();
        if (hdmiControlService.mHdmiCecNetwork.getCecDeviceInfo(i2) == null) {
            Iterator it = getActions(NewDeviceAction.class).iterator();
            while (true) {
                if (!it.hasNext()) {
                    addAndStartAction(
                            new NewDeviceAction(
                                    this,
                                    activeSource.logicalAddress,
                                    activeSource.physicalAddress,
                                    deviceType));
                    break;
                }
                NewDeviceAction newDeviceAction = (NewDeviceAction) it.next();
                newDeviceAction.getClass();
                if (newDeviceAction.mDeviceLogicalAddress == activeSource.logicalAddress
                        && newDeviceAction.mDevicePhysicalAddress == activeSource.physicalAddress) {
                    break;
                }
            }
        }
        if (hdmiControlService.isProhibitMode()) {
            HdmiCecLocalDevice.ActiveSource localActiveSource =
                    hdmiControlService.getLocalActiveSource();
            if (localActiveSource.logicalAddress != getDeviceInfo().getLogicalAddress()) {
                startRoutingControl(
                        activeSource.physicalAddress, localActiveSource.physicalAddress, null);
                return;
            } else {
                hdmiControlService.sendCecCommand(
                        HdmiCecMessageBuilder.buildActiveSource(
                                localActiveSource.logicalAddress,
                                localActiveSource.physicalAddress),
                        null);
                updateActiveSource(localActiveSource, "ActiveSourceHandler");
                return;
            }
        }
        HdmiCecLocalDevice.ActiveSource localActiveSource2 =
                hdmiControlService.getLocalActiveSource();
        int i3 = localActiveSource2.logicalAddress;
        int i4 = localActiveSource2.physicalAddress;
        updateActiveSource(activeSource, "ActiveSourceHandler");
        if (activeSource.logicalAddress != i3 || activeSource.physicalAddress != i4) {
            setPrevPortId(getActivePortId());
        }
        updateActiveInput(activeSource.physicalAddress, true);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleGetMenuLanguage(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        hdmiControlService.assertRunOnServiceThread();
        if (broadcastMenuLanguage(hdmiControlService.mMenuLanguage)) {
            return -1;
        }
        Slog.w(
                "HdmiCecLocalDeviceTv",
                "Failed to respond to <Get Menu Language>: " + hdmiCecMessage.toString());
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleImageViewOn(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        handleTextViewOn();
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleInactiveSource(HdmiCecMessage hdmiCecMessage) {
        int i;
        assertRunOnServiceThread();
        if (this.mService.getLocalActiveSource().logicalAddress != hdmiCecMessage.mSource
                || this.mService.isProhibitMode()) {
            return -1;
        }
        synchronized (this.mLock) {
            i = this.mPrevPortId;
        }
        if (i != -1) {
            HdmiDeviceInfo cecDeviceInfo =
                    this.mService.mHdmiCecNetwork.getCecDeviceInfo(hdmiCecMessage.mSource);
            if (cecDeviceInfo == null) {
                return -1;
            }
            HdmiControlService hdmiControlService = this.mService;
            if (hdmiControlService.mHdmiCecNetwork.physicalAddressToPortId(
                            cecDeviceInfo.getPhysicalAddress())
                    == i) {
                return -1;
            }
            doManualPortSwitching(i, null);
            setPrevPortId(-1);
        } else {
            this.mService.getLocalActiveSource().invalidate();
            setActivePath(GnssNative.GNSS_AIDING_TYPE_ALL);
            this.mService.invokeInputChangeListener(HdmiDeviceInfo.INACTIVE_DEVICE);
        }
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleInitiateArc(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        if (hdmiControlService.earcBlocksArcConnection()) {
            Slog.i(
                    "HdmiCecLocalDeviceTv",
                    "ARC connection blocked because eARC connection is established or being"
                        + " established.");
            return 1;
        }
        if (canStartArcUpdateAction(hdmiCecMessage.mSource, true)) {
            addAndStartAction(
                    new SetArcTransmissionStateAction(this, hdmiCecMessage.mSource, true));
            return -1;
        }
        HdmiDeviceInfo avrDeviceInfo = getAvrDeviceInfo();
        if (avrDeviceInfo == null) {
            this.mDelayedMessageBuffer.add(hdmiCecMessage);
            return -1;
        }
        if (isConnectedToArcPort(avrDeviceInfo.getPhysicalAddress())) {
            return 4;
        }
        assertRunOnServiceThread();
        hdmiControlService.assertRunOnServiceThread();
        Intent intent = new Intent("android.hardware.hdmi.action.OSD_MESSAGE");
        intent.putExtra("android.hardware.hdmi.extra.MESSAGE_ID", 1);
        hdmiControlService.sendBroadcastAsUser(intent);
        return 4;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleMenuStatus() {
        return -1;
    }

    public final boolean handleNewDeviceAtTheTailOfActivePath(int i) {
        if (!isTailOfActivePath(i, getActivePath())) {
            return false;
        }
        int portIdToPath = this.mService.portIdToPath(getActivePortId());
        setActivePath(portIdToPath);
        startRoutingControl(getActivePath(), portIdToPath, null);
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleRecordStatus() {
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleRecordTvScreen(HdmiCecMessage hdmiCecMessage) {
        byte[] oneTouchRecordSource;
        List actions = getActions(OneTouchRecordAction.class);
        if (!actions.isEmpty()) {
            int i = ((OneTouchRecordAction) actions.get(0)).mRecorderAddress;
            int i2 = hdmiCecMessage.mSource;
            if (i == i2) {
                return 2;
            }
            announceOneTouchRecordResult(i2, 48);
            return 2;
        }
        int i3 = hdmiCecMessage.mSource;
        HdmiControlService hdmiControlService = this.mService;
        synchronized (hdmiControlService.mLock) {
            try {
                HdmiControlService.HdmiRecordListenerRecord hdmiRecordListenerRecord =
                        hdmiControlService.mRecordListenerRecord;
                if (hdmiRecordListenerRecord != null) {
                    try {
                        oneTouchRecordSource =
                                ((IHdmiRecordListener) hdmiRecordListenerRecord.mListener)
                                        .getOneTouchRecordSource(i3);
                    } catch (RemoteException e) {
                        Slog.w("HdmiControlService", "Failed to start record.", e);
                    }
                }
                oneTouchRecordSource = EmptyArray.BYTE;
            } finally {
            }
        }
        return startOneTouchRecord(i3, oneTouchRecordSource);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleReportAudioStatus(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (this.mService.getHdmiCecVolumeControl() == 0) {
            return 4;
        }
        Map map = HdmiUtils.ADDRESS_TO_TYPE;
        setAudioStatus(
                HdmiUtils.getAudioStatusVolume(hdmiCecMessage),
                (hdmiCecMessage.mParams[0] & 128) == 128);
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void handleReportPhysicalAddress(HdmiCecMessage hdmiCecMessage) {
        super.handleReportPhysicalAddress(hdmiCecMessage);
        byte[] bArr = hdmiCecMessage.mParams;
        int twoBytesToInt = HdmiUtils.twoBytesToInt(bArr);
        byte b = bArr[2];
        HdmiCecNetwork hdmiCecNetwork = this.mService.mHdmiCecNetwork;
        hdmiCecNetwork.assertRunOnServiceThread();
        int i = hdmiCecMessage.mSource;
        HdmiDeviceInfo cecDeviceInfo = hdmiCecNetwork.getCecDeviceInfo(i);
        if (cecDeviceInfo == null || cecDeviceInfo.getPhysicalAddress() != twoBytesToInt) {
            handleNewDeviceAtTheTailOfActivePath(twoBytesToInt);
        }
        for (NewDeviceAction newDeviceAction : getActions(NewDeviceAction.class)) {
            newDeviceAction.getClass();
            if (newDeviceAction.mDeviceLogicalAddress == i
                    && newDeviceAction.mDevicePhysicalAddress == twoBytesToInt) {
                return;
            }
        }
        addAndStartAction(new NewDeviceAction(this, i, twoBytesToInt, b));
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void handleRequestActiveSource(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int logicalAddress = getDeviceInfo().getLogicalAddress();
        HdmiControlService hdmiControlService = this.mService;
        if (logicalAddress == hdmiControlService.getLocalActiveSource().logicalAddress) {
            hdmiControlService.sendCecCommand(
                    HdmiCecMessageBuilder.buildActiveSource(
                            getDeviceInfo().getLogicalAddress(), getActivePath()),
                    null);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleRoutingChange(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        byte[] bArr = hdmiCecMessage.mParams;
        int twoBytesToInt = HdmiUtils.twoBytesToInt(bArr);
        int activePath = getActivePath();
        int i = 0;
        while (true) {
            if (i > 12) {
                break;
            }
            if (((twoBytesToInt >> i) & 15) != 0) {
                twoBytesToInt &= 65520 << i;
                break;
            }
            i += 4;
        }
        if (!(twoBytesToInt == 0
                ? true
                : HdmiUtils.isInActiveRoutingPath(activePath, twoBytesToInt))) {
            return -1;
        }
        this.mService.getLocalActiveSource().invalidate();
        removeAction(RoutingControlAction.class);
        addAndStartAction(new RoutingControlAction(this, HdmiUtils.twoBytesToInt(2, bArr), null));
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleSetAudioVolumeLevel(
            SetAudioVolumeLevelMessage setAudioVolumeLevelMessage) {
        HdmiControlService hdmiControlService = this.mService;
        if (hdmiControlService.isSystemAudioActivated()) {
            return 1;
        }
        int i = setAudioVolumeLevelMessage.mAudioVolumeLevel;
        if (i < 0 || i > 100) {
            return -1;
        }
        AudioManagerWrapper audioManagerWrapper = hdmiControlService.mAudioManager;
        ((DefaultAudioManagerWrapper) audioManagerWrapper)
                .mAudioManager.setStreamVolume(
                        3, (i * hdmiControlService.mStreamMusicMaxVolume) / 100, 0);
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleSetSystemAudioMode(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        boolean parseCommandParamSystemAudioStatus =
                HdmiUtils.parseCommandParamSystemAudioStatus(hdmiCecMessage);
        if (isMessageForSystemAudio(hdmiCecMessage)) {
            if (parseCommandParamSystemAudioStatus && !isSystemAudioControlFeatureEnabled()) {
                HdmiLogger.debug(
                        "Ignoring <Set System Audio Mode> message because the System Audio Control"
                            + " feature is disabled: %s",
                        hdmiCecMessage);
                return 4;
            }
        } else {
            if (getAvrDeviceInfo() != null) {
                HdmiLogger.warning(
                        "Invalid <Set System Audio Mode> message:" + hdmiCecMessage, new Object[0]);
                return 4;
            }
            this.mDelayedMessageBuffer.add(hdmiCecMessage);
        }
        removeAction(SystemAudioAutoInitiationAction.class);
        SystemAudioActionFromAvr systemAudioActionFromAvr =
                new SystemAudioActionFromAvr(
                        this, hdmiCecMessage.mSource, null, parseCommandParamSystemAudioStatus);
        if (!HdmiUtils.verifyAddressType(systemAudioActionFromAvr.getSourceAddress(), 0)) {
            Slog.w("SystemAudioActionFromAvr", "Device type mismatch, stop the action.");
            systemAudioActionFromAvr.finish(true);
        }
        addAndStartAction(systemAudioActionFromAvr);
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleStandby(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        if (hdmiControlService.getLocalActiveSource().logicalAddress == hdmiCecMessage.mSource) {
            return super.handleStandby(hdmiCecMessage);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder(
                        "<Standby> was not sent by the current active source, ignoring. Current"
                            + " active source has logical address "),
                hdmiControlService.getLocalActiveSource().logicalAddress,
                "HdmiCecLocalDeviceTv");
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleSystemAudioModeStatus(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (!isMessageForSystemAudio(hdmiCecMessage)) {
            HdmiLogger.warning(
                    "Invalid <System Audio Mode Status> message:" + hdmiCecMessage, new Object[0]);
            return -1;
        }
        boolean isSystemAudioControlFeatureEnabled = isSystemAudioControlFeatureEnabled();
        boolean parseCommandParamSystemAudioStatus =
                HdmiUtils.parseCommandParamSystemAudioStatus(hdmiCecMessage);
        HdmiDeviceInfo avrDeviceInfo = getAvrDeviceInfo();
        if (avrDeviceInfo == null) {
            setSystemAudioMode$1(false);
        } else if (parseCommandParamSystemAudioStatus != isSystemAudioControlFeatureEnabled) {
            addAndStartAction(
                    new SystemAudioActionFromTv(
                            this,
                            avrDeviceInfo.getLogicalAddress(),
                            null,
                            isSystemAudioControlFeatureEnabled));
        } else {
            setSystemAudioMode$1(isSystemAudioControlFeatureEnabled);
        }
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleTerminateArc(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (this.mService.isPowerStandbyOrTransient()) {
            disableArc();
            return -1;
        }
        addAndStartAction(new SetArcTransmissionStateAction(this, hdmiCecMessage.mSource, false));
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleTextViewOn() {
        assertRunOnServiceThread();
        if (!getAutoWakeup()) {
            return -1;
        }
        this.mService.wakeUp();
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleTimerClearedStatus(HdmiCecMessage hdmiCecMessage) {
        announceTimerRecordingResult(hdmiCecMessage.mSource, hdmiCecMessage.mParams[0] & 255);
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleTimerStatus() {
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public /* bridge */ /* synthetic */ void invokeStandbyCompletedCallback(
            HdmiCecLocalDevice.StandbyCompletedCallback standbyCompletedCallback) {
        super.invokeStandbyCompletedCallback(standbyCompletedCallback);
    }

    public final boolean isArcEstablished() {
        assertRunOnServiceThread();
        if (this.mArcEstablished) {
            for (int i = 0; i < this.mArcFeatureEnabled.size(); i++) {
                if (this.mArcFeatureEnabled.valueAt(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final boolean isInputReady(int i) {
        assertRunOnServiceThread();
        return this.mTvInputs.containsValue(Integer.valueOf(i));
    }

    public final boolean isMessageForSystemAudio(HdmiCecMessage hdmiCecMessage) {
        int i;
        return this.mService.isCecControlEnabled()
                && hdmiCecMessage.mSource == 5
                && ((i = hdmiCecMessage.mDestination) == 0 || i == 15)
                && getAvrDeviceInfo() != null;
    }

    public final boolean isSystemAudioActivated() {
        HdmiControlService hdmiControlService = this.mService;
        if (hdmiControlService.mHdmiCecNetwork.getSafeCecDeviceInfo(5) != null) {
            return hdmiControlService.isSystemAudioActivated();
        }
        return false;
    }

    public final boolean isSystemAudioControlFeatureEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSystemAudioControlFeatureEnabled;
        }
        return z;
    }

    public final void notifyArcStatusToAudioService(List list, boolean z) {
        ((DefaultAudioManagerWrapper) this.mService.mAudioManager)
                .mAudioManager.setWiredDeviceConnectionState(
                        new AudioDeviceAttributes(
                                2,
                                10,
                                "",
                                "",
                                new ArrayList(),
                                (List)
                                        list.stream()
                                                .map(
                                                        new HdmiCecLocalDeviceTv$$ExternalSyntheticLambda1())
                                                .collect(Collectors.toList())),
                        z ? 1 : 0);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void onAddressAllocated(int i) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        for (HdmiPortInfo hdmiPortInfo : hdmiControlService.getPortInfo()) {
            this.mArcFeatureEnabled.put(hdmiPortInfo.getId(), hdmiPortInfo.isArcSupported());
        }
        TvInputManager tvInputManager = hdmiControlService.mTvInputManager;
        if (tvInputManager != null) {
            tvInputManager.registerCallback(this.mTvInputCallback, hdmiControlService.mHandler);
        }
        hdmiControlService.sendCecCommand(
                HdmiCecMessageBuilder.buildReportPhysicalAddressCommand(
                        getDeviceInfo().getLogicalAddress(),
                        hdmiControlService.mHdmiCecNetwork.getPhysicalAddress(),
                        this.mDeviceType),
                null);
        hdmiControlService.sendCecCommand(
                HdmiCecMessageBuilder.buildDeviceVendorIdCommand(
                        getDeviceInfo().getLogicalAddress(), hdmiControlService.getVendorId()),
                null);
        HdmiCecNetwork hdmiCecNetwork = hdmiControlService.mHdmiCecNetwork;
        hdmiCecNetwork.mCecSwitches.add(Integer.valueOf(hdmiCecNetwork.getPhysicalAddress()));
        this.mTvInputs.clear();
        this.mSkipRoutingControl = i == 3;
        Handler handler = this.mSkipRoutingControlHandler;
        HdmiCecLocalDeviceTv$$ExternalSyntheticLambda0
                hdmiCecLocalDeviceTv$$ExternalSyntheticLambda0 =
                        this.mResetSkipRoutingControlRunnable;
        handler.removeCallbacks(hdmiCecLocalDeviceTv$$ExternalSyntheticLambda0);
        if (this.mSkipRoutingControl) {
            handler.postDelayed(hdmiCecLocalDeviceTv$$ExternalSyntheticLambda0, 2000L);
        }
        boolean z = (i == 0 || i == 1) ? false : true;
        assertRunOnServiceThread();
        int activePortId = getActivePortId();
        DelayedMessageBuffer delayedMessageBuffer = this.mDelayedMessageBuffer;
        if (activePortId == -1 || getActivePortId() == 0) {
            int physicalAddress = hdmiControlService.mHdmiCecNetwork.getPhysicalAddress();
            setActivePath(physicalAddress);
            if (!z) {
                Iterator it = delayedMessageBuffer.mBuffer.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        hdmiControlService.sendCecCommand(
                                HdmiCecMessageBuilder.buildActiveSource(
                                        getDeviceInfo().getLogicalAddress(), physicalAddress),
                                null);
                        int logicalAddress = getDeviceInfo().getLogicalAddress();
                        assertRunOnServiceThread();
                        updateActiveSource(
                                new HdmiCecLocalDevice.ActiveSource(
                                        logicalAddress, physicalAddress),
                                "HdmiCecLocalDeviceTv#launchRoutingControl()");
                        break;
                    }
                    if (((HdmiCecMessage) it.next()).mOpcode == 130) {
                        break;
                    }
                }
            }
        } else if (!z && !hdmiControlService.isProhibitMode()) {
            int portIdToPath = hdmiControlService.portIdToPath(getActivePortId());
            setActivePath(portIdToPath);
            startRoutingControl(getActivePath(), portIdToPath, null);
        }
        assertRunOnServiceThread();
        SelectRequestBuffer.AnonymousClass1 anonymousClass1 = SelectRequestBuffer.EMPTY_BUFFER;
        assertRunOnServiceThread();
        this.mSelectRequestBuffer = anonymousClass1;
        assertRunOnServiceThread();
        addAndStartAction(
                new DeviceDiscoveryAction(
                        this,
                        new DeviceDiscoveryAction
                                .DeviceDiscoveryCallback() { // from class:
                                                             // com.android.server.hdmi.HdmiCecLocalDeviceTv.3
                            @Override // com.android.server.hdmi.DeviceDiscoveryAction.DeviceDiscoveryCallback
                            public final void onDeviceDiscoveryDone(List list) {
                                HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv;
                                Iterator it2 = ((ArrayList) list).iterator();
                                while (true) {
                                    boolean hasNext = it2.hasNext();
                                    hdmiCecLocalDeviceTv = HdmiCecLocalDeviceTv.this;
                                    if (!hasNext) {
                                        break;
                                    }
                                    hdmiCecLocalDeviceTv.mService.mHdmiCecNetwork.addCecDevice(
                                            (HdmiDeviceInfo) it2.next());
                                }
                                hdmiCecLocalDeviceTv.mSelectRequestBuffer.process();
                                hdmiCecLocalDeviceTv.assertRunOnServiceThread();
                                SelectRequestBuffer.AnonymousClass1 anonymousClass12 =
                                        SelectRequestBuffer.EMPTY_BUFFER;
                                hdmiCecLocalDeviceTv.assertRunOnServiceThread();
                                hdmiCecLocalDeviceTv.mSelectRequestBuffer = anonymousClass12;
                                if (hdmiCecLocalDeviceTv
                                        .getActions(HotplugDetectionAction.class)
                                        .isEmpty()) {
                                    hdmiCecLocalDeviceTv.addAndStartAction(
                                            new HotplugDetectionAction(hdmiCecLocalDeviceTv));
                                }
                                if (hdmiCecLocalDeviceTv
                                        .getActions(PowerStatusMonitorAction.class)
                                        .isEmpty()) {
                                    hdmiCecLocalDeviceTv.addAndStartAction(
                                            new PowerStatusMonitorAction(hdmiCecLocalDeviceTv));
                                }
                                HdmiDeviceInfo avrDeviceInfo =
                                        hdmiCecLocalDeviceTv.getAvrDeviceInfo();
                                if (avrDeviceInfo != null) {
                                    hdmiCecLocalDeviceTv.onNewAvrAdded(avrDeviceInfo);
                                } else {
                                    hdmiCecLocalDeviceTv.setSystemAudioMode$1(false);
                                }
                            }
                        }));
        startQueuedActions();
        Iterator it2 = delayedMessageBuffer.mBuffer.iterator();
        while (it2.hasNext()) {
            if (((HdmiCecMessage) it2.next()).mOpcode == 130) {
                return;
            }
        }
        if (hasAction(RequestActiveSourceAction.class)) {
            Slog.i("HdmiCecLocalDeviceTv", "RequestActiveSourceAction is in progress. Restarting.");
            removeAction(RequestActiveSourceAction.class);
        }
        RequestActiveSourceAction requestActiveSourceAction =
                new RequestActiveSourceAction(
                        this,
                        new IHdmiControlCallback
                                .Stub() { // from class:
                                          // com.android.server.hdmi.HdmiCecLocalDeviceTv.2
                            public final void onComplete(int i2) {
                                if (HdmiUtils.isValidAddress(
                                                HdmiCecLocalDeviceTv.this.mService
                                                        .getLocalActiveSource()
                                                        .logicalAddress)
                                        || i2 == 0) {
                                    return;
                                }
                                HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv =
                                        HdmiCecLocalDeviceTv.this;
                                hdmiCecLocalDeviceTv.mService.sendCecCommand(
                                        HdmiCecMessageBuilder.buildActiveSource(
                                                hdmiCecLocalDeviceTv
                                                        .getDeviceInfo()
                                                        .getLogicalAddress(),
                                                HdmiCecLocalDeviceTv.this
                                                        .getDeviceInfo()
                                                        .getPhysicalAddress()),
                                        null);
                                HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv2 =
                                        HdmiCecLocalDeviceTv.this;
                                int logicalAddress2 =
                                        hdmiCecLocalDeviceTv2.getDeviceInfo().getLogicalAddress();
                                int physicalAddress2 =
                                        HdmiCecLocalDeviceTv.this
                                                .getDeviceInfo()
                                                .getPhysicalAddress();
                                hdmiCecLocalDeviceTv2.assertRunOnServiceThread();
                                hdmiCecLocalDeviceTv2.updateActiveSource(
                                        new HdmiCecLocalDevice.ActiveSource(
                                                logicalAddress2, physicalAddress2),
                                        "RequestActiveSourceAction#finishWithCallback()");
                            }
                        });
        requestActiveSourceAction.mSendRetryCount = 0;
        addAndStartAction(requestActiveSourceAction);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void onHotplug(int i, boolean z) {
        HdmiDeviceInfo avrDeviceInfo;
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        if (!z) {
            hdmiControlService.mHdmiCecNetwork.removeCecSwitches(i);
        }
        if ((!hdmiControlService.isEarcEnabled() || !hdmiControlService.isEarcSupported())
                && (avrDeviceInfo = getAvrDeviceInfo()) != null
                && i == avrDeviceInfo.getPortId()
                && isConnectedToArcPort(avrDeviceInfo.getPhysicalAddress())) {
            HdmiLogger.debug("Port ID:%d, 5v=%b", Integer.valueOf(i), Boolean.valueOf(z));
            if (!z) {
                enableAudioReturnChannel$1(false);
            } else if (this.mArcEstablished) {
                enableAudioReturnChannel$1(true);
            }
        }
        List actions = getActions(HotplugDetectionAction.class);
        if (actions.isEmpty()) {
            return;
        }
        HotplugDetectionAction hotplugDetectionAction = (HotplugDetectionAction) actions.get(0);
        ((HdmiCecFeatureAction.ActionTimerHandler) hotplugDetectionAction.mActionTimer)
                .clearTimerMessage();
        hotplugDetectionAction.mTimeoutCount = 0;
        hotplugDetectionAction.mState = 1;
        hotplugDetectionAction.pollAllDevices();
        hotplugDetectionAction.addTimer(
                hotplugDetectionAction.mState, hotplugDetectionAction.mIsTvDevice ? 5000 : 60000);
    }

    public final void onNewAvrAdded(HdmiDeviceInfo hdmiDeviceInfo) {
        assertRunOnServiceThread();
        addAndStartAction(
                new SystemAudioAutoInitiationAction(this, hdmiDeviceInfo.getLogicalAddress()));
        int physicalAddress = hdmiDeviceInfo.getPhysicalAddress();
        if (!((61440 & physicalAddress) == physicalAddress)) {
            startArcAction(false, null);
            return;
        }
        int portId = hdmiDeviceInfo.getPortId();
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        hdmiControlService.assertRunOnServiceThread();
        HdmiCecController hdmiCecController = hdmiControlService.mCecController;
        hdmiCecController.assertRunOnServiceThread();
        if (hdmiCecController.mNativeWrapperImpl.nativeIsConnected(portId)) {
            int portId2 = hdmiDeviceInfo.getPortId();
            assertRunOnServiceThread();
            if (!this.mArcFeatureEnabled.get(portId2)
                    || hasAction(SetArcTransmissionStateAction.class)) {
                return;
            }
            startArcAction(true, null);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void onStandby(
            boolean z, int i, final HdmiControlService.AnonymousClass27 anonymousClass27) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        if (!hdmiControlService.isCecControlEnabled()) {
            invokeStandbyCompletedCallback(anonymousClass27);
            return;
        }
        boolean z2 =
                hdmiControlService.getHdmiCecConfig().getIntValue("tv_send_standby_on_sleep") == 1;
        if (z || !z2) {
            invokeStandbyCompletedCallback(anonymousClass27);
        } else {
            hdmiControlService.sendCecCommand(
                    HdmiCecMessage.build(getDeviceInfo().getLogicalAddress(), 15, 54),
                    new HdmiControlService
                            .SendMessageCallback() { // from class:
                                                     // com.android.server.hdmi.HdmiCecLocalDeviceTv.4
                        @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                        public final void onSendCompleted(int i2) {
                            HdmiCecLocalDeviceTv.this.invokeStandbyCompletedCallback(
                                    anonymousClass27);
                        }
                    });
        }
    }

    public final void processDelayedMessages(int i) {
        assertRunOnServiceThread();
        DelayedMessageBuffer delayedMessageBuffer = this.mDelayedMessageBuffer;
        delayedMessageBuffer.getClass();
        ArrayList arrayList = new ArrayList(delayedMessageBuffer.mBuffer);
        delayedMessageBuffer.mBuffer.clear();
        HdmiLogger.debug("Checking message for address:" + i, new Object[0]);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            HdmiCecMessage hdmiCecMessage = (HdmiCecMessage) it.next();
            if (hdmiCecMessage.mSource != i) {
                delayedMessageBuffer.mBuffer.add(hdmiCecMessage);
            } else {
                int i2 = hdmiCecMessage.mOpcode;
                HdmiCecLocalDevice hdmiCecLocalDevice = delayedMessageBuffer.mDevice;
                if (i2 != 130
                        || hdmiCecLocalDevice.isInputReady(HdmiDeviceInfo.idForCecDevice(i))) {
                    hdmiCecLocalDevice.onMessage(hdmiCecMessage);
                    HdmiLogger.debug("Processing message:" + hdmiCecMessage, new Object[0]);
                } else {
                    delayedMessageBuffer.mBuffer.add(hdmiCecMessage);
                }
            }
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void sendStandby(int i) {
        HdmiControlService hdmiControlService = this.mService;
        HdmiDeviceInfo hdmiDeviceInfo =
                (HdmiDeviceInfo) hdmiControlService.mHdmiCecNetwork.mDeviceInfos.get(i);
        if (hdmiDeviceInfo == null) {
            return;
        }
        hdmiControlService.sendCecCommand(
                HdmiCecMessage.build(
                        getDeviceInfo().getLogicalAddress(),
                        hdmiDeviceInfo.getLogicalAddress(),
                        54),
                null);
    }

    public final void setAudioStatus(int i, boolean z) {
        if (!isSystemAudioActivated() || this.mService.getHdmiCecVolumeControl() == 0) {
            return;
        }
        synchronized (this.mLock) {
            this.mSystemAudioMute = z;
            this.mSystemAudioVolume = i;
            if (z) {
                i = 101;
            }
            assertRunOnServiceThread();
            HdmiControlService hdmiControlService = this.mService;
            hdmiControlService.assertRunOnServiceThread();
            Intent intent = new Intent("android.hardware.hdmi.action.OSD_MESSAGE");
            intent.putExtra("android.hardware.hdmi.extra.MESSAGE_ID", 2);
            intent.putExtra("android.hardware.hdmi.extra.MESSAGE_EXTRA_PARAM1", i);
            hdmiControlService.sendBroadcastAsUser(intent);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void setPreferredAddress(int i) {
        Slog.w("HdmiCecLocalDeviceTv", "Preferred addres will not be stored for TV");
    }

    public final void setPrevPortId(int i) {
        synchronized (this.mLock) {
            this.mPrevPortId = i;
        }
    }

    public final void setSystemAudioMode$1(boolean z) {
        if (!isSystemAudioControlFeatureEnabled() && z) {
            HdmiLogger.debug(
                    "Cannot turn on system audio mode because the System Audio Control feature is"
                        + " disabled.",
                    new Object[0]);
            return;
        }
        HdmiLogger.debug(
                "System Audio Mode change[old:%b new:%b]",
                Boolean.valueOf(this.mService.isSystemAudioActivated()), Boolean.valueOf(z));
        HdmiLogger.debug(
                "[A]UpdateSystemAudio mode[on=%b] output=[%X]",
                Boolean.valueOf(z),
                Integer.valueOf(
                        ((DefaultAudioManagerWrapper) this.mService.mAudioManager)
                                .mAudioManager.setHdmiSystemAudioSupported(z)));
        synchronized (this.mLock) {
            try {
                if (this.mService.isSystemAudioActivated() != z) {
                    this.mService.setSystemAudioActivated(z);
                    this.mService.announceSystemAudioModeChange(z);
                }
                if (z && !this.mArcEstablished) {
                    startArcAction(true, null);
                } else if (!z) {
                    startArcAction(false, null);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startArcAction(
            boolean z, HdmiControlService.AnonymousClass35 anonymousClass35) {
        assertRunOnServiceThread();
        HdmiDeviceInfo avrDeviceInfo = getAvrDeviceInfo();
        if (avrDeviceInfo == null) {
            Slog.w("HdmiCecLocalDeviceTv", "Failed to start arc action; No AVR device.");
            invokeCallback(3, anonymousClass35);
            return;
        }
        boolean canStartArcUpdateAction =
                canStartArcUpdateAction(avrDeviceInfo.getLogicalAddress(), z);
        HdmiControlService hdmiControlService = this.mService;
        if (!canStartArcUpdateAction) {
            Slog.w(
                    "HdmiCecLocalDeviceTv",
                    "Failed to start arc action; ARC configuration check failed.");
            if (z && !isConnectedToArcPort(avrDeviceInfo.getPhysicalAddress())) {
                assertRunOnServiceThread();
                hdmiControlService.assertRunOnServiceThread();
                Intent intent = new Intent("android.hardware.hdmi.action.OSD_MESSAGE");
                intent.putExtra("android.hardware.hdmi.extra.MESSAGE_ID", 1);
                hdmiControlService.sendBroadcastAsUser(intent);
            }
            invokeCallback(6, anonymousClass35);
            return;
        }
        if (z && hdmiControlService.earcBlocksArcConnection()) {
            Slog.i(
                    "HdmiCecLocalDeviceTv",
                    "ARC connection blocked because eARC connection is established or being"
                        + " established.");
            invokeCallback(6, anonymousClass35);
            return;
        }
        if (z) {
            removeAction(RequestArcTerminationAction.class);
            if (hasAction(RequestArcInitiationAction.class)) {
                ((ArrayList)
                                ((RequestArcInitiationAction)
                                                getActions(RequestArcInitiationAction.class).get(0))
                                        .mCallbacks)
                        .add(anonymousClass35);
                return;
            } else {
                addAndStartAction(
                        new RequestArcInitiationAction(
                                this, avrDeviceInfo.getLogicalAddress(), anonymousClass35));
                return;
            }
        }
        removeAction(RequestArcInitiationAction.class);
        if (hasAction(RequestArcTerminationAction.class)) {
            ((ArrayList)
                            ((RequestArcTerminationAction)
                                            getActions(RequestArcTerminationAction.class).get(0))
                                    .mCallbacks)
                    .add(anonymousClass35);
        } else {
            addAndStartAction(
                    new RequestArcTerminationAction(
                            this, avrDeviceInfo.getLogicalAddress(), anonymousClass35));
        }
    }

    public final int startOneTouchRecord(int i, byte[] bArr) {
        assertRunOnServiceThread();
        if (!this.mService.isCecControlEnabled()) {
            Slog.w(
                    "HdmiCecLocalDeviceTv",
                    "Can not start one touch record. CEC control is disabled.");
            announceOneTouchRecordResult(i, 51);
            return 1;
        }
        if (!checkRecorder(i)) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i, "Invalid recorder address:", "HdmiCecLocalDeviceTv");
            announceOneTouchRecordResult(i, 49);
            return 1;
        }
        if (bArr == null || !HdmiRecordSources.checkRecordSource(bArr)) {
            Slog.w("HdmiCecLocalDeviceTv", "Invalid record source." + Arrays.toString(bArr));
            announceOneTouchRecordResult(i, 50);
            return 2;
        }
        addAndStartAction(new OneTouchRecordAction(this, i, bArr));
        Slog.i(
                "HdmiCecLocalDeviceTv",
                "Start new [One Touch Record]-Target:"
                        + i
                        + ", recordSource:"
                        + Arrays.toString(bArr));
        return -1;
    }

    public final void startRoutingControl(
            int i, int i2, IHdmiControlCallback iHdmiControlCallback) {
        assertRunOnServiceThread();
        if (i == i2) {
            return;
        }
        this.mService.sendCecCommand(
                HdmiCecMessageBuilder.buildRoutingChange(
                        getDeviceInfo().getLogicalAddress(), i, i2));
        removeAction(RoutingControlAction.class);
        addAndStartAction(new RoutingControlAction(this, i2, iHdmiControlCallback));
    }

    public final void updateActiveInput(int i, boolean z) {
        assertRunOnServiceThread();
        setActivePath(i);
        if (z) {
            HdmiControlService hdmiControlService = this.mService;
            HdmiDeviceInfo cecDeviceInfo =
                    hdmiControlService.mHdmiCecNetwork.getCecDeviceInfo(
                            hdmiControlService.getLocalActiveSource().logicalAddress);
            if (cecDeviceInfo == null) {
                getActivePortId();
                hdmiControlService.assertRunOnServiceThread();
                hdmiControlService.mMhlController.getClass();
                cecDeviceInfo = HdmiDeviceInfo.hardwarePort(i, getActivePortId());
            }
            hdmiControlService.invokeInputChangeListener(cecDeviceInfo);
        }
    }

    public final void updateActiveSource(HdmiCecLocalDevice.ActiveSource activeSource, String str) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        if (hdmiControlService.getLocalActiveSource().equals(activeSource)) {
            return;
        }
        setActiveSource(activeSource.logicalAddress, activeSource.physicalAddress, str);
        int i = activeSource.logicalAddress;
        if (hdmiControlService.mHdmiCecNetwork.getCecDeviceInfo(i) == null
                || i == getDeviceInfo().getLogicalAddress()) {
            return;
        }
        if (hdmiControlService.mHdmiCecNetwork.physicalAddressToPortId(activeSource.physicalAddress)
                == getActivePortId()) {
            setPrevPortId(getActivePortId());
        }
    }
}
