package com.android.server.hdmi;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.hdmi.DeviceFeatures;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.HdmiPortInfo;
import android.hardware.hdmi.IHdmiControlCallback;
import android.media.AudioDeviceInfo;
import android.media.tv.TvContract;
import android.media.tv.TvInputInfo;
import android.media.tv.TvInputManager;
import android.os.SystemProperties;
import android.sysprop.HdmiProperties;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.attention.AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0;
import com.android.server.location.gnss.hal.GnssNative;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class HdmiCecLocalDeviceAudioSystem extends HdmiCecLocalDeviceSource {
    public static final HashMap AUDIO_CODECS_MAP;
    public boolean mArcEstablished;
    public final boolean mArcIntentUsed;
    public final DelayedMessageBuffer mDelayedMessageBuffer;
    public final HashMap mPortIdToTvInputs;
    public boolean mSystemAudioControlFeatureEnabled;
    public final AnonymousClass1 mTvInputCallback;
    public final HashMap mTvInputsToDeviceInfo;
    public Boolean mTvSystemAudioModeSupport;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem$2, reason: invalid class name */
    public final class AnonymousClass2
            implements HdmiControlService.SendMessageCallback, TvSystemAudioModeSupportedCallback {
        public final /* synthetic */ Object val$callback;

        public /* synthetic */ AnonymousClass2(Object obj) {
            this.val$callback = obj;
        }

        @Override // com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.TvSystemAudioModeSupportedCallback
        public void onResult(boolean z) {
            HdmiCecLocalDeviceAudioSystem hdmiCecLocalDeviceAudioSystem =
                    HdmiCecLocalDeviceAudioSystem.this;
            if (z) {
                hdmiCecLocalDeviceAudioSystem.setSystemAudioMode(true);
                hdmiCecLocalDeviceAudioSystem.mService.sendCecCommand(
                        HdmiCecMessageBuilder.buildCommandWithBooleanParam(
                                hdmiCecLocalDeviceAudioSystem.getDeviceInfo().getLogicalAddress(),
                                15,
                                114,
                                true),
                        null);
            } else {
                HdmiControlService hdmiControlService = hdmiCecLocalDeviceAudioSystem.mService;
                hdmiControlService.assertRunOnServiceThread();
                hdmiControlService.mCecController.maySendFeatureAbortCommand(
                        4, (HdmiCecMessage) this.val$callback);
            }
        }

        @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
        public void onSendCompleted(int i) {
            HdmiCecLocalDeviceAudioSystem.this.invokeStandbyCompletedCallback(
                    (HdmiCecLocalDevice.StandbyCompletedCallback) this.val$callback);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface TvSystemAudioModeSupportedCallback {
        void onResult(boolean z);
    }

    /* renamed from: -$$Nest$maddOrUpdateTvInput, reason: not valid java name */
    public static void m570$$Nest$maddOrUpdateTvInput(
            HdmiCecLocalDeviceAudioSystem hdmiCecLocalDeviceAudioSystem, String str) {
        hdmiCecLocalDeviceAudioSystem.assertRunOnServiceThread();
        synchronized (hdmiCecLocalDeviceAudioSystem.mLock) {
            try {
                TvInputInfo tvInputInfo =
                        hdmiCecLocalDeviceAudioSystem.mService.mTvInputManager.getTvInputInfo(str);
                if (tvInputInfo == null) {
                    return;
                }
                HdmiDeviceInfo hdmiDeviceInfo = tvInputInfo.getHdmiDeviceInfo();
                if (hdmiDeviceInfo == null) {
                    return;
                }
                hdmiCecLocalDeviceAudioSystem.mPortIdToTvInputs.put(
                        Integer.valueOf(hdmiDeviceInfo.getPortId()), str);
                hdmiCecLocalDeviceAudioSystem.mTvInputsToDeviceInfo.put(str, hdmiDeviceInfo);
                if (hdmiDeviceInfo.isCecDevice()) {
                    int logicalAddress = hdmiDeviceInfo.getLogicalAddress();
                    hdmiCecLocalDeviceAudioSystem.assertRunOnServiceThread();
                    hdmiCecLocalDeviceAudioSystem.mDelayedMessageBuffer.processActiveSource(
                            logicalAddress);
                }
            } finally {
            }
        }
    }

    static {
        HashMap hashMap = new HashMap();
        hashMap.put(0, List.of(1));
        hashMap.put(1, List.of(3, 2, 4, 21, 22));
        hashMap.put(2, List.of(5));
        hashMap.put(3, List.of(11));
        hashMap.put(5, List.of(12));
        hashMap.put(4, List.of(9));
        hashMap.put(6, List.of(10));
        hashMap.put(7, List.of(7));
        hashMap.put(10, List.of(6, 18));
        hashMap.put(11, List.of(8));
        hashMap.put(12, List.of(14, 19));
        AUDIO_CODECS_MAP = hashMap;
    }

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem$1] */
    public HdmiCecLocalDeviceAudioSystem(HdmiControlService hdmiControlService) {
        super(hdmiControlService, 5);
        this.mTvSystemAudioModeSupport = null;
        this.mArcEstablished = false;
        this.mArcIntentUsed = ((String) HdmiProperties.arc_port().orElse("0")).contains("tvinput");
        this.mPortIdToTvInputs = new HashMap();
        this.mTvInputsToDeviceInfo = new HashMap();
        this.mDelayedMessageBuffer = new DelayedMessageBuffer(this);
        this.mTvInputCallback =
                new TvInputManager
                        .TvInputCallback() { // from class:
                                             // com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.1
                    @Override // android.media.tv.TvInputManager.TvInputCallback
                    public final void onInputAdded(String str) {
                        HdmiCecLocalDeviceAudioSystem.m570$$Nest$maddOrUpdateTvInput(
                                HdmiCecLocalDeviceAudioSystem.this, str);
                    }

                    @Override // android.media.tv.TvInputManager.TvInputCallback
                    public final void onInputRemoved(String str) {
                        HdmiCecLocalDeviceAudioSystem hdmiCecLocalDeviceAudioSystem =
                                HdmiCecLocalDeviceAudioSystem.this;
                        hdmiCecLocalDeviceAudioSystem.assertRunOnServiceThread();
                        synchronized (hdmiCecLocalDeviceAudioSystem.mLock) {
                            try {
                                if (hdmiCecLocalDeviceAudioSystem.mTvInputsToDeviceInfo.get(str)
                                        == null) {
                                    return;
                                }
                                hdmiCecLocalDeviceAudioSystem.mPortIdToTvInputs.remove(
                                        Integer.valueOf(
                                                ((HdmiDeviceInfo)
                                                                hdmiCecLocalDeviceAudioSystem
                                                                        .mTvInputsToDeviceInfo.get(
                                                                        str))
                                                        .getPortId()));
                                hdmiCecLocalDeviceAudioSystem.mTvInputsToDeviceInfo.remove(str);
                            } finally {
                            }
                        }
                    }

                    @Override // android.media.tv.TvInputManager.TvInputCallback
                    public final void onInputUpdated(String str) {
                        HdmiCecLocalDeviceAudioSystem.m570$$Nest$maddOrUpdateTvInput(
                                HdmiCecLocalDeviceAudioSystem.this, str);
                    }
                };
        this.mRoutingControlFeatureEnabled =
                hdmiControlService.getHdmiCecConfig().getIntValue("routing_control") == 1;
        this.mSystemAudioControlFeatureEnabled =
                hdmiControlService.getHdmiCecConfig().getIntValue("system_audio_control") == 1;
        this.mStandbyHandler = new HdmiCecStandbyModeHandler(hdmiControlService, this);
    }

    public static byte[] getShortAudioDescriptorBytes(ArrayList arrayList) {
        byte[] bArr = new byte[arrayList.size() * 3];
        Iterator it = arrayList.iterator();
        int i = 0;
        while (it.hasNext()) {
            System.arraycopy((byte[]) it.next(), 0, bArr, i, 3);
            i += 3;
        }
        return bArr;
    }

    public final boolean checkSupportAndSetSystemAudioMode(boolean z) {
        if (!isSystemAudioControlFeatureEnabled()) {
            HdmiLogger.debug(
                    AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Cannot turn "),
                            z ? "on" : "off",
                            "system audio mode because the System Audio Control feature is"
                                + " disabled."),
                    new Object[0]);
            return false;
        }
        HdmiControlService hdmiControlService = this.mService;
        HdmiLogger.debug(
                "System Audio Mode change[old:%b new:%b]",
                Boolean.valueOf(hdmiControlService.isSystemAudioActivated()), Boolean.valueOf(z));
        if (z) {
            hdmiControlService.wakeUp();
        }
        setSystemAudioMode(z);
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final DeviceFeatures computeDeviceFeatures() {
        return DeviceFeatures.NO_FEATURES_SUPPORTED.toBuilder()
                .setArcRxSupport(
                        SystemProperties.getBoolean("persist.sys.hdmi.property_arc_support", true)
                                ? 1
                                : 0)
                .build();
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource,
              // com.android.server.hdmi.HdmiCecLocalDevice
    public final void disableDevice(
            boolean z,
            HdmiCecLocalDevice.PendingActionClearedCallback pendingActionClearedCallback) {
        removeAction(ArcInitiationActionFromAvr.class);
        boolean isArcEnabled = isArcEnabled();
        HdmiControlService hdmiControlService = this.mService;
        if (isArcEnabled
                && hdmiControlService.readBooleanSystemProperty(
                        "persist.sys.hdmi.property_arc_support", true)) {
            addAndStartAction(new ArcTerminationActionFromAvr(this));
        }
        super.disableDevice(z, pendingActionClearedCallback);
        assertRunOnServiceThread();
        TvInputManager tvInputManager = hdmiControlService.mTvInputManager;
        if (tvInputManager != null) {
            tvInputManager.unregisterCallback(this.mTvInputCallback);
        }
        assertRunOnServiceThread();
        Iterator it = this.mActions.iterator();
        while (it.hasNext()) {
            ((HdmiCecFeatureAction) it.next()).finish(false);
        }
        this.mActions.clear();
        checkIfPendingActionsCleared();
    }

    public final void doManualPortSwitching(int i, IHdmiControlCallback iHdmiControlCallback) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        if (hdmiControlService.mHdmiCecNetwork.getPortInfo(i) == null) {
            invokeCallback(3, iHdmiControlCallback);
            return;
        }
        if (i == getLocalActivePort()) {
            invokeCallback(0, iHdmiControlCallback);
            return;
        }
        if (!hdmiControlService.isCecControlEnabled()) {
            setRoutingPort(i);
            setLocalActivePort(i);
            invokeCallback(6, iHdmiControlCallback);
            return;
        }
        int portIdToPath =
                getRoutingPort() != 0
                        ? hdmiControlService.portIdToPath(getRoutingPort())
                        : getDeviceInfo().getPhysicalAddress();
        int portIdToPath2 = hdmiControlService.portIdToPath(i);
        if (portIdToPath == portIdToPath2) {
            return;
        }
        setRoutingPort(i);
        setLocalActivePort(i);
        hdmiControlService.sendCecCommand(
                HdmiCecMessageBuilder.buildRoutingChange(
                        getDeviceInfo().getLogicalAddress(), portIdToPath, portIdToPath2),
                null);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("HdmiCecLocalDeviceAudioSystem:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("isRoutingFeatureEnabled " + isRoutingControlFeatureEnabled());
        StringBuilder m =
                AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0.m(
                        new StringBuilder("mSystemAudioControlFeatureEnabled: "),
                        this.mSystemAudioControlFeatureEnabled,
                        indentingPrintWriter,
                        "mTvSystemAudioModeSupport: ");
        m.append(this.mTvSystemAudioModeSupport);
        indentingPrintWriter.println(m.toString());
        StringBuilder m2 =
                AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0.m(
                        AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0.m(
                                new StringBuilder("mArcEstablished: "),
                                this.mArcEstablished,
                                indentingPrintWriter,
                                "mArcIntentUsed: "),
                        this.mArcIntentUsed,
                        indentingPrintWriter,
                        "mRoutingPort: ");
        m2.append(getRoutingPort());
        indentingPrintWriter.println(m2.toString());
        indentingPrintWriter.println("mLocalActivePort: " + getLocalActivePort());
        HdmiUtils.dumpMap(indentingPrintWriter, "mPortIdToTvInputs:", this.mPortIdToTvInputs);
        HdmiUtils.dumpMap(
                indentingPrintWriter, "mTvInputsToDeviceInfo:", this.mTvInputsToDeviceInfo);
        indentingPrintWriter.decreaseIndent();
        super.dump(indentingPrintWriter);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int findKeyReceiverAddress() {
        HdmiControlService hdmiControlService = this.mService;
        if (HdmiUtils.isValidAddress(hdmiControlService.getLocalActiveSource().logicalAddress)) {
            return hdmiControlService.getLocalActiveSource().logicalAddress;
        }
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int getPreferredAddress() {
        assertRunOnServiceThread();
        return SystemProperties.getInt("persist.sys.hdmi.addr.audiosystem", 15);
    }

    public byte[] getSupportedShortAudioDescriptor(AudioDeviceInfo audioDeviceInfo, int i) {
        int i2;
        byte b;
        byte[] bArr = new byte[3];
        int[] encodings = audioDeviceInfo.getEncodings();
        HashMap hashMap = AUDIO_CODECS_MAP;
        if (hashMap.containsKey(Integer.valueOf(i)) && encodings.length != 0) {
            List list = (List) hashMap.get(Integer.valueOf(i));
            for (int i3 : encodings) {
                if (list.contains(Integer.valueOf(i3))) {
                    int[] channelCounts = audioDeviceInfo.getChannelCounts();
                    if (channelCounts.length == 0
                            || (i2 = channelCounts[channelCounts.length - 1]) > 8) {
                        i2 = 8;
                    }
                    bArr[0] = (byte) (((byte) (i2 - 1)) | (i << 3));
                    int i4 = 192;
                    ArrayList arrayList =
                            new ArrayList(
                                    Arrays.asList(
                                            32,
                                            44,
                                            48,
                                            88,
                                            96,
                                            Integer.valueOf(
                                                    FrameworkStatsLog
                                                            .DEVICE_POLICY_EVENT__EVENT_ID__DOCSUI_PICK_RESULT),
                                            192));
                    int[] sampleRates = audioDeviceInfo.getSampleRates();
                    if (sampleRates.length == 0) {
                        Slog.e("HdmiCecLocalDeviceAudioSystem", "Device supports arbitrary rates");
                        b = Byte.MAX_VALUE;
                    } else {
                        byte b2 = 0;
                        for (int i5 : sampleRates) {
                            if (arrayList.contains(Integer.valueOf(i5))) {
                                b2 = (byte) (b2 | (1 << arrayList.indexOf(Integer.valueOf(i5))));
                            }
                        }
                        b = b2;
                    }
                    bArr[1] = b;
                    switch (i) {
                        case 1:
                            if (i3 == 2) {
                                bArr[2] = 1;
                            } else if (i3 == 21) {
                                bArr[2] = 4;
                            } else {
                                bArr[2] = 0;
                            }
                            return bArr;
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                            int[] sampleRates2 = audioDeviceInfo.getSampleRates();
                            if (sampleRates2.length != 0) {
                                int i6 = 0;
                                for (int i7 : sampleRates2) {
                                    if (i6 < i7) {
                                        i6 = i7;
                                    }
                                }
                                i4 = i6;
                            }
                            bArr[2] = (byte) (i4 / 8);
                            return bArr;
                        case 8:
                        case 9:
                        default:
                            return null;
                        case 10:
                        case 11:
                        case 12:
                            bArr[2] = 0;
                            return bArr;
                    }
                }
            }
        }
        return null;
    }

    public byte[] getSupportedShortAudioDescriptors(AudioDeviceInfo audioDeviceInfo, int[] iArr) {
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int i : iArr) {
            byte[] supportedShortAudioDescriptor =
                    getSupportedShortAudioDescriptor(audioDeviceInfo, i);
            if (supportedShortAudioDescriptor != null) {
                if (supportedShortAudioDescriptor.length == 3) {
                    arrayList.add(supportedShortAudioDescriptor);
                } else {
                    HdmiLogger.warning(
                            "Dropping Short Audio Descriptor with length %d for requested codec %x",
                            Integer.valueOf(supportedShortAudioDescriptor.length),
                            Integer.valueOf(i));
                }
            }
        }
        return getShortAudioDescriptorBytes(arrayList);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource,
              // com.android.server.hdmi.HdmiCecLocalDevice
    public final void handleActiveSource(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int i = hdmiCecMessage.mSource;
        int twoBytesToInt = HdmiUtils.twoBytesToInt(hdmiCecMessage.mParams);
        HdmiControlService hdmiControlService = this.mService;
        if (HdmiUtils.getLocalPortFromPhysicalAddress(
                        twoBytesToInt, hdmiControlService.mHdmiCecNetwork.getPhysicalAddress())
                == -1) {
            super.handleActiveSource(hdmiCecMessage);
            return;
        }
        HdmiDeviceInfo cecDeviceInfo = hdmiControlService.mHdmiCecNetwork.getCecDeviceInfo(i);
        DelayedMessageBuffer delayedMessageBuffer = this.mDelayedMessageBuffer;
        if (cecDeviceInfo == null) {
            HdmiLogger.debug("Device info %X not found; buffering the command", Integer.valueOf(i));
            delayedMessageBuffer.add(hdmiCecMessage);
        } else {
            if (!isInputReady(cecDeviceInfo.getPortId())) {
                HdmiLogger.debug(
                        "Input not ready for device: %X; buffering the command",
                        Integer.valueOf(cecDeviceInfo.getId()));
                delayedMessageBuffer.add(hdmiCecMessage);
                return;
            }
            Iterator it = delayedMessageBuffer.mBuffer.iterator();
            while (it.hasNext()) {
                if (((HdmiCecMessage) it.next()).mOpcode == 130) {
                    it.remove();
                }
            }
            super.handleActiveSource(hdmiCecMessage);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleGiveAudioStatus(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        if (!isSystemAudioControlFeatureEnabled() || this.mService.getHdmiCecVolumeControl() != 1) {
            return 4;
        }
        reportAudioStatus(hdmiCecMessage.mSource);
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleGiveSystemAudioModeStatus(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        boolean isSystemAudioActivated = hdmiControlService.isSystemAudioActivated();
        if (!isSystemAudioActivated
                && hdmiCecMessage.mSource == 0
                && hasAction(SystemAudioInitiationActionFromAvr.class)) {
            isSystemAudioActivated = true;
        }
        hdmiControlService.sendCecCommand(
                HdmiCecMessageBuilder.buildCommandWithBooleanParam(
                        getDeviceInfo().getLogicalAddress(),
                        hdmiCecMessage.mSource,
                        126,
                        isSystemAudioActivated));
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleInitiateArc(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        HdmiLogger.debug("HdmiCecLocalDeviceAudioSystemStub handleInitiateArc", new Object[0]);
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleReportArcInitiate() {
        assertRunOnServiceThread();
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleReportArcTermination() {
        assertRunOnServiceThread();
        processArcTermination();
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleRequestArcInitiate() {
        assertRunOnServiceThread();
        removeAction(ArcInitiationActionFromAvr.class);
        if (!this.mService.readBooleanSystemProperty(
                "persist.sys.hdmi.property_arc_support", true)) {
            return 0;
        }
        if (isDirectConnectToTv()) {
            addAndStartAction(new ArcInitiationActionFromAvr(this));
            return -1;
        }
        HdmiLogger.debug("AVR device is not directly connected with TV", new Object[0]);
        return 1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleRequestArcTermination() {
        assertRunOnServiceThread();
        if (!SystemProperties.getBoolean("persist.sys.hdmi.property_arc_support", true)) {
            return 0;
        }
        if (!isArcEnabled()) {
            HdmiLogger.debug("ARC is not established between TV and AVR device", new Object[0]);
            return 1;
        }
        if (getActions(ArcTerminationActionFromAvr.class).isEmpty()
                || ((ArrayList)
                                ((ArcTerminationActionFromAvr)
                                                getActions(ArcTerminationActionFromAvr.class)
                                                        .get(0))
                                        .mCallbacks)
                        .isEmpty()) {
            removeAction(ArcTerminationActionFromAvr.class);
            addAndStartAction(new ArcTerminationActionFromAvr(this));
            return -1;
        }
        IHdmiControlCallback iHdmiControlCallback =
                (IHdmiControlCallback)
                        ((ArrayList)
                                        ((ArcTerminationActionFromAvr)
                                                        getActions(
                                                                        ArcTerminationActionFromAvr
                                                                                .class)
                                                                .get(0))
                                                .mCallbacks)
                                .get(0);
        removeAction(ArcTerminationActionFromAvr.class);
        addAndStartAction(new ArcTerminationActionFromAvr(this, iHdmiControlCallback));
        return -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x017d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x017f  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0123  */
    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int handleRequestShortAudioDescriptor(com.android.server.hdmi.HdmiCecMessage r13) {
        /*
            Method dump skipped, instructions count: 412
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.handleRequestShortAudioDescriptor(com.android.server.hdmi.HdmiCecMessage):int");
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource
    public final void handleRoutingChangeAndInformation(int i, HdmiCecMessage hdmiCecMessage) {
        HdmiControlService hdmiControlService = this.mService;
        int physicalAddressToPortId = hdmiControlService.mHdmiCecNetwork.physicalAddressToPortId(i);
        if (physicalAddressToPortId > 0) {
            return;
        }
        if (physicalAddressToPortId < 0 && hdmiControlService.isSystemAudioActivated()) {
            routeToInputFromPortId(17);
            return;
        }
        if (physicalAddressToPortId == 0) {
            if (getRoutingPort() == 0 && hdmiControlService.isPlaybackDevice()) {
                routeToInputFromPortId(0);
                hdmiControlService.setAndBroadcastActiveSourceFromOneDeviceType(
                        hdmiCecMessage.mSource,
                        hdmiControlService.mHdmiCecNetwork.getPhysicalAddress(),
                        "HdmiCecLocalDeviceAudioSystem#handleRoutingChangeAndInformationForSwitch()");
                return;
            }
            int portIdToPath = hdmiControlService.portIdToPath(getRoutingPort());
            if (portIdToPath == hdmiControlService.mHdmiCecNetwork.getPhysicalAddress()) {
                HdmiLogger.debug(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(
                                portIdToPath,
                                "Current device can't assign valid physical addressto devices under"
                                    + " it any more. It's physical address is "),
                        new Object[0]);
            } else {
                hdmiControlService.sendCecCommand(
                        HdmiCecMessage.build(
                                getDeviceInfo().getLogicalAddress(),
                                15,
                                129,
                                HdmiCecMessageBuilder.physicalAddressToParam(portIdToPath)));
                routeToInputFromPortId(getRoutingPort());
            }
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleSetSystemAudioMode(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        return !checkSupportAndSetSystemAudioMode(
                        HdmiUtils.parseCommandParamSystemAudioStatus(hdmiCecMessage))
                ? 4
                : -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleSystemAudioModeRequest(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        boolean z = hdmiCecMessage.mParams.length != 0;
        int i = hdmiCecMessage.mSource;
        HdmiDeviceInfo hdmiDeviceInfo = null;
        HdmiControlService hdmiControlService = this.mService;
        if (i == 0) {
            this.mTvSystemAudioModeSupport = Boolean.TRUE;
        } else if (z) {
            if (!isSystemAudioControlFeatureEnabled()) {
                HdmiLogger.debug(
                        "Cannot turn onsystem audio mode because the System Audio Control feature"
                            + " is disabled.",
                        new Object[0]);
                return 4;
            }
            hdmiControlService.wakeUp();
            if (hdmiControlService.mHdmiCecNetwork.physicalAddressToPortId(
                            hdmiControlService.getLocalActiveSource().physicalAddress)
                    != -1) {
                setSystemAudioMode(true);
                hdmiControlService.sendCecCommand(
                        HdmiCecMessageBuilder.buildCommandWithBooleanParam(
                                getDeviceInfo().getLogicalAddress(), 15, 114, true),
                        null);
            } else {
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(hdmiCecMessage);
                Boolean bool = this.mTvSystemAudioModeSupport;
                if (bool == null) {
                    DetectTvSystemAudioModeSupportAction detectTvSystemAudioModeSupportAction =
                            new DetectTvSystemAudioModeSupportAction(this);
                    detectTvSystemAudioModeSupportAction.mSendSetSystemAudioModeRetryCount = 0;
                    detectTvSystemAudioModeSupportAction.mCallback = anonymousClass2;
                    addAndStartAction(detectTvSystemAudioModeSupportAction);
                } else {
                    anonymousClass2.onResult(bool.booleanValue());
                }
            }
            return -1;
        }
        if (!checkSupportAndSetSystemAudioMode(z)) {
            return 4;
        }
        hdmiControlService.sendCecCommand(
                HdmiCecMessageBuilder.buildCommandWithBooleanParam(
                        getDeviceInfo().getLogicalAddress(), 15, 114, z),
                null);
        if (z) {
            int twoBytesToInt = HdmiUtils.twoBytesToInt(hdmiCecMessage.mParams);
            if (HdmiUtils.getLocalPortFromPhysicalAddress(
                            twoBytesToInt, getDeviceInfo().getPhysicalAddress())
                    != -1) {
                return -1;
            }
            Iterator it = hdmiControlService.mHdmiCecNetwork.mSafeAllDeviceInfos.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                HdmiDeviceInfo hdmiDeviceInfo2 = (HdmiDeviceInfo) it.next();
                if (hdmiDeviceInfo2.getPhysicalAddress() == twoBytesToInt) {
                    hdmiDeviceInfo = hdmiDeviceInfo2;
                    break;
                }
            }
            if (hdmiDeviceInfo == null) {
                switchInputOnReceivingNewActivePath(twoBytesToInt);
            }
        }
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleSystemAudioModeStatus(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        return !checkSupportAndSetSystemAudioMode(
                        HdmiUtils.parseCommandParamSystemAudioStatus(hdmiCecMessage))
                ? 4
                : -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public /* bridge */ /* synthetic */ void invokeStandbyCompletedCallback(
            HdmiCecLocalDevice.StandbyCompletedCallback standbyCompletedCallback) {
        super.invokeStandbyCompletedCallback(standbyCompletedCallback);
    }

    public boolean isArcEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mArcEstablished;
        }
        return z;
    }

    public final boolean isDirectConnectToTv() {
        int physicalAddress = this.mService.mHdmiCecNetwork.getPhysicalAddress();
        return (61440 & physicalAddress) == physicalAddress;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final boolean isInputReady(int i) {
        assertRunOnServiceThread();
        return ((HdmiDeviceInfo)
                        this.mTvInputsToDeviceInfo.get(
                                (String) this.mPortIdToTvInputs.get(Integer.valueOf(i))))
                != null;
    }

    public final boolean isSystemAudioControlFeatureEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSystemAudioControlFeatureEnabled;
        }
        return z;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void onAddressAllocated(int i) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        if (i == 0) {
            hdmiControlService.setAndBroadcastActiveSource(
                    hdmiControlService.mHdmiCecNetwork.getPhysicalAddress(),
                    getDeviceInfo().getDeviceType(),
                    15,
                    "HdmiCecLocalDeviceAudioSystem#onAddressAllocated()");
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
        TvInputManager tvInputManager = hdmiControlService.mTvInputManager;
        if (tvInputManager != null) {
            tvInputManager.registerCallback(this.mTvInputCallback, hdmiControlService.mHandler);
        }
        removeAction(ArcTerminationActionFromAvr.class);
        if (SystemProperties.getBoolean("persist.sys.hdmi.property_arc_support", true)
                && isDirectConnectToTv()
                && !isArcEnabled()) {
            removeAction(ArcInitiationActionFromAvr.class);
            addAndStartAction(new ArcInitiationActionFromAvr(this));
        }
        if (hdmiControlService.mDisplayManager.getDisplay(0).getState() != 1) {
            systemAudioControlOnPowerOn(
                    SystemProperties.getInt("persist.sys.hdmi.system_audio_control_on_power_on", 0),
                    SystemProperties.getBoolean(
                            "persist.sys.hdmi.last_system_audio_control", true));
        }
        hdmiControlService.mHdmiCecNetwork.clearDeviceList();
        assertRunOnServiceThread();
        HdmiCecLocalDevicePlayback playback = hdmiControlService.playback();
        if (playback == null
                || (!playback.hasAction(DeviceDiscoveryAction.class)
                        && !playback.hasAction(HotplugDetectionAction.class))) {
            if (hasAction(DeviceDiscoveryAction.class)) {
                Slog.i(
                        "HdmiCecLocalDeviceAudioSystem",
                        "Device Discovery Action is in progress. Restarting.");
                removeAction(DeviceDiscoveryAction.class);
            }
            addAndStartAction(
                    new DeviceDiscoveryAction(
                            this,
                            new DeviceDiscoveryAction
                                    .DeviceDiscoveryCallback() { // from class:
                                                                 // com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem.4
                                @Override // com.android.server.hdmi.DeviceDiscoveryAction.DeviceDiscoveryCallback
                                public final void onDeviceDiscoveryDone(List list) {
                                    Iterator it = ((ArrayList) list).iterator();
                                    while (it.hasNext()) {
                                        HdmiCecLocalDeviceAudioSystem.this.mService.mHdmiCecNetwork
                                                .addCecDevice((HdmiDeviceInfo) it.next());
                                    }
                                }
                            }));
        }
        startQueuedActions();
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void onHotplug(int i, boolean z) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        HdmiPortInfo portInfo = hdmiControlService.mHdmiCecNetwork.getPortInfo(i);
        if (portInfo == null || portInfo.getType() != 1) {
            if (z || this.mPortIdToTvInputs.get(Integer.valueOf(i)) == null) {
                return;
            }
            HdmiDeviceInfo hdmiDeviceInfo =
                    (HdmiDeviceInfo)
                            this.mTvInputsToDeviceInfo.get(
                                    (String) this.mPortIdToTvInputs.get(Integer.valueOf(i)));
            if (hdmiDeviceInfo == null) {
                return;
            }
            hdmiControlService.mHdmiCecNetwork.removeCecDevice(
                    this, hdmiDeviceInfo.getLogicalAddress());
            return;
        }
        this.mCecMessageCache.mCache.clear();
        if (z) {
            return;
        }
        if (hdmiControlService.isSystemAudioActivated()) {
            this.mTvSystemAudioModeSupport = null;
            checkSupportAndSetSystemAudioMode(false);
        }
        if (isArcEnabled()) {
            setArcStatus(false);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void onStandby(
            boolean z, int i, HdmiControlService.AnonymousClass27 anonymousClass27) {
        assertRunOnServiceThread();
        this.mService.setActiveSource(
                -1, GnssNative.GNSS_AIDING_TYPE_ALL, "HdmiCecLocalDeviceAudioSystem#onStandby()");
        this.mTvSystemAudioModeSupport = null;
        synchronized (this.mLock) {
            try {
                HdmiControlService hdmiControlService = this.mService;
                hdmiControlService.writeStringSystemProperty(
                        "persist.sys.hdmi.last_system_audio_control",
                        hdmiControlService.isSystemAudioActivated() ? "true" : "false");
            } catch (Throwable th) {
                throw th;
            }
        }
        terminateSystemAudioMode(anonymousClass27);
    }

    public final void processArcTermination() {
        setArcStatus(false);
        if (getLocalActivePort() == 17) {
            routeToInputFromPortId(getRoutingPort());
        }
    }

    public final void reportAudioStatus(int i) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        if (hdmiControlService.getHdmiCecVolumeControl() == 0) {
            return;
        }
        int streamVolume =
                ((DefaultAudioManagerWrapper) hdmiControlService.mAudioManager)
                        .mAudioManager.getStreamVolume(3);
        boolean isStreamMute =
                ((DefaultAudioManagerWrapper) hdmiControlService.mAudioManager)
                        .mAudioManager.isStreamMute(3);
        int streamMaxVolume =
                ((DefaultAudioManagerWrapper) hdmiControlService.mAudioManager)
                        .mAudioManager.getStreamMaxVolume(3);
        int i2 = (streamVolume * 100) / streamMaxVolume;
        HdmiLogger.debug(
                "Reporting volume %d (%d-%d) as CEC volume %d",
                Integer.valueOf(streamVolume),
                Integer.valueOf(
                        ((DefaultAudioManagerWrapper) hdmiControlService.mAudioManager)
                                .mAudioManager.getStreamMinVolume(3)),
                Integer.valueOf(streamMaxVolume),
                Integer.valueOf(i2));
        hdmiControlService.sendCecCommand(
                HdmiCecMessage.build(
                        getDeviceInfo().getLogicalAddress(),
                        i,
                        122,
                        new byte[] {
                            (byte)
                                    (((byte) (isStreamMute ? 128 : 0))
                                            | (((byte) i2) & Byte.MAX_VALUE))
                        }),
                null);
    }

    public final void routeToInputFromPortId(int i) {
        if (!isRoutingControlFeatureEnabled()) {
            HdmiLogger.debug("Routing Control Feature is not enabled.", new Object[0]);
            return;
        }
        if (this.mArcIntentUsed) {
            if (i < 0 || i >= 21) {
                HdmiLogger.debug("Invalid port number for Tv Input switching.", new Object[0]);
                return;
            }
            HdmiControlService hdmiControlService = this.mService;
            hdmiControlService.wakeUp();
            if (getLocalActivePort() == i && i != 17) {
                HdmiLogger.debug(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                i, "Not switching to the same port ", " except for arc"),
                        new Object[0]);
                return;
            }
            if (i == 0 && hdmiControlService.isPlaybackDevice()) {
                try {
                    hdmiControlService
                            .getContext()
                            .startActivity(
                                    new Intent("android.intent.action.MAIN")
                                            .addCategory("android.intent.category.HOME")
                                            .setFlags(872480768));
                } catch (ActivityNotFoundException e) {
                    Slog.e(
                            "HdmiCecLocalDeviceAudioSystem",
                            "Can't find activity to switch to HOME",
                            e);
                }
            } else if (i == 17) {
                switchToTvInput((String) HdmiProperties.arc_port().orElse("0"));
                setLocalActivePort(i);
                return;
            } else {
                String str = (String) this.mPortIdToTvInputs.get(Integer.valueOf(i));
                if (str == null) {
                    HdmiLogger.debug("Port number does not match any Tv Input.", new Object[0]);
                    return;
                }
                switchToTvInput(str);
            }
            setLocalActivePort(i);
            setRoutingPort(i);
        }
    }

    public final void setArcStatus(boolean z) {
        assertRunOnServiceThread();
        HdmiLogger.debug(
                "Set Arc Status[old:%b new:%b]",
                Boolean.valueOf(this.mArcEstablished), Boolean.valueOf(z));
        assertRunOnServiceThread();
        int parseInt = Integer.parseInt((String) HdmiProperties.arc_port().orElse("0"));
        HdmiControlService hdmiControlService = this.mService;
        hdmiControlService.enableAudioReturnChannel(parseInt, z);
        ((DefaultAudioManagerWrapper) hdmiControlService.mAudioManager)
                .mAudioManager.setWiredDeviceConnectionState(-2013265920, z ? 1 : 0, "", "");
        this.mArcEstablished = z;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void setPreferredAddress(int i) {
        assertRunOnServiceThread();
        this.mService.writeStringSystemProperty(
                "persist.sys.hdmi.addr.audiosystem", String.valueOf(i));
    }

    public final void setSystemAudioMode(boolean z) {
        this.mService.mHdmiCecNetwork.physicalAddressToPortId(
                this.mService.getLocalActiveSource().physicalAddress);
        boolean z2 = this.mService.getHdmiCecConfig().getIntValue("system_audio_mode_muting") == 1;
        if (((DefaultAudioManagerWrapper) this.mService.mAudioManager).mAudioManager.isStreamMute(3)
                        == z
                && (z2 || z)) {
            ((DefaultAudioManagerWrapper) this.mService.mAudioManager)
                    .mAudioManager.adjustStreamVolume(3, z ? 100 : -100, 0);
        }
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
            } catch (Throwable th) {
                throw th;
            }
        }
        if (this.mArcIntentUsed && !z2 && !z && getLocalActivePort() == 17) {
            routeToInputFromPortId(getRoutingPort());
        }
        if (SystemProperties.getBoolean("persist.sys.hdmi.property_arc_support", true)
                && isDirectConnectToTv()
                && this.mService.isSystemAudioActivated()
                && !hasAction(ArcInitiationActionFromAvr.class)) {
            addAndStartAction(new ArcInitiationActionFromAvr(this));
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource
    public final void switchInputOnReceivingNewActivePath(int i) {
        HdmiControlService hdmiControlService = this.mService;
        int physicalAddressToPortId = hdmiControlService.mHdmiCecNetwork.physicalAddressToPortId(i);
        if (hdmiControlService.isSystemAudioActivated() && physicalAddressToPortId < 0) {
            routeToInputFromPortId(17);
        } else {
            if (!this.mIsSwitchDevice || physicalAddressToPortId < 0) {
                return;
            }
            routeToInputFromPortId(physicalAddressToPortId);
        }
    }

    public final void switchToTvInput(String str) {
        try {
            this.mService
                    .getContext()
                    .startActivity(
                            new Intent(
                                            "android.intent.action.VIEW",
                                            TvContract.buildChannelUriForPassthroughInput(str))
                                    .addFlags(268435456));
        } catch (ActivityNotFoundException e) {
            Slog.e("HdmiCecLocalDeviceAudioSystem", "Can't find activity to switch to " + str, e);
        }
    }

    public void systemAudioControlOnPowerOn(int i, boolean z) {
        if (i == 0 || (i == 1 && z && isSystemAudioControlFeatureEnabled())) {
            if (hasAction(SystemAudioInitiationActionFromAvr.class)) {
                Slog.i(
                        "HdmiCecLocalDeviceAudioSystem",
                        "SystemAudioInitiationActionFromAvr is in progress. Restarting.");
                removeAction(SystemAudioInitiationActionFromAvr.class);
            }
            addAndStartAction(new SystemAudioInitiationActionFromAvr(this));
        }
    }

    public final void terminateSystemAudioMode(
            HdmiControlService.AnonymousClass27 anonymousClass27) {
        removeAction(SystemAudioInitiationActionFromAvr.class);
        HdmiControlService hdmiControlService = this.mService;
        if (!hdmiControlService.isSystemAudioActivated()) {
            invokeStandbyCompletedCallback(anonymousClass27);
        } else if (checkSupportAndSetSystemAudioMode(false)) {
            hdmiControlService.sendCecCommand(
                    HdmiCecMessageBuilder.buildCommandWithBooleanParam(
                            getDeviceInfo().getLogicalAddress(), 15, 114, false),
                    new AnonymousClass2(anonymousClass27));
        }
    }
}
