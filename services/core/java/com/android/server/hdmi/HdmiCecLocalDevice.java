package com.android.server.hdmi;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.hdmi.DeviceFeatures;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.HdmiPortInfo;
import android.hardware.hdmi.IHdmiControlCallback;
import android.hardware.input.InputManagerGlobal;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.sysprop.HdmiProperties;
import android.util.Slog;
import android.util.SparseArray;
import android.view.KeyEvent;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.location.gnss.hal.GnssNative;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public abstract class HdmiCecLocalDevice extends HdmiLocalDevice {
    final ArrayList mActions;
    public int mActiveRoutingPath;
    public final ArrayBlockingQueue mActiveSourceHistory;
    public final HdmiCecMessageCache mCecMessageCache;
    public HdmiDeviceInfo mDeviceInfo;
    public final AnonymousClass1 mHandler;
    public int mLastKeyRepeatCount;
    public int mLastKeycode;
    public AnonymousClass4 mPendingActionClearedCallback;
    public int mPreferredAddress;
    public HdmiCecStandbyModeHandler mStandbyHandler;

    /* renamed from: com.android.server.hdmi.HdmiCecLocalDevice$4, reason: invalid class name */
    public final class AnonymousClass4 implements PendingActionClearedCallback {
        public final /* synthetic */ PendingActionClearedCallback val$originalCallback;

        public AnonymousClass4(PendingActionClearedCallback pendingActionClearedCallback) {
            this.val$originalCallback = pendingActionClearedCallback;
        }

        @Override // com.android.server.hdmi.HdmiCecLocalDevice.PendingActionClearedCallback
        public final void onCleared(HdmiCecLocalDevice hdmiCecLocalDevice) {
            removeMessages(1);
            this.val$originalCallback.onCleared(hdmiCecLocalDevice);
        }
    }

    public final class ActiveSource {
        public int logicalAddress;
        public int physicalAddress;

        public ActiveSource() {
            invalidate();
        }

        public ActiveSource(int i, int i2) {
            this.logicalAddress = i;
            this.physicalAddress = i2;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof ActiveSource)) {
                return false;
            }
            ActiveSource activeSource = (ActiveSource) obj;
            return activeSource.logicalAddress == this.logicalAddress
                    && activeSource.physicalAddress == this.physicalAddress;
        }

        public final int hashCode() {
            return (this.logicalAddress * 29) + this.physicalAddress;
        }

        public final void invalidate() {
            this.logicalAddress = -1;
            this.physicalAddress = GnssNative.GNSS_AIDING_TYPE_ALL;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("(");
            int i = this.logicalAddress;
            sb.append(i == -1 ? "invalid" : String.format("0x%02x", Integer.valueOf(i)));
            int i2 = this.physicalAddress;
            return BootReceiver$$ExternalSyntheticOutline0.m(
                    sb,
                    ", ",
                    i2 != 65535 ? String.format("0x%04x", Integer.valueOf(i2)) : "invalid",
                    ")");
        }
    }

    public final class ActiveSourceHistoryRecord extends HdmiCecController.Dumpable {
        public final ActiveSource mActiveSource;
        public final String mCaller;
        public final boolean mIsActiveSource;

        public ActiveSourceHistoryRecord(ActiveSource activeSource, boolean z, String str) {
            this.mActiveSource = activeSource;
            this.mIsActiveSource = z;
            this.mCaller = str;
        }

        @Override // com.android.server.hdmi.HdmiCecController.Dumpable
        public final void dump(
                IndentingPrintWriter indentingPrintWriter, SimpleDateFormat simpleDateFormat) {
            indentingPrintWriter.print("time=");
            indentingPrintWriter.print(simpleDateFormat.format(new Date(this.mTime)));
            indentingPrintWriter.print(" active source=");
            indentingPrintWriter.print(this.mActiveSource);
            indentingPrintWriter.print(" isActiveSource=");
            indentingPrintWriter.print(this.mIsActiveSource);
            indentingPrintWriter.print(" from=");
            indentingPrintWriter.println(this.mCaller);
        }
    }

    public interface PendingActionClearedCallback {
        void onCleared(HdmiCecLocalDevice hdmiCecLocalDevice);
    }

    public interface StandbyCompletedCallback {}

    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.server.hdmi.HdmiCecLocalDevice$1] */
    public HdmiCecLocalDevice(HdmiControlService hdmiControlService, int i) {
        super(hdmiControlService, i);
        this.mLastKeycode = -1;
        this.mLastKeyRepeatCount = 0;
        this.mActiveSourceHistory = new ArrayBlockingQueue(10);
        this.mCecMessageCache = new HdmiCecMessageCache();
        this.mActions = new ArrayList();
        this.mHandler =
                new Handler() { // from class: com.android.server.hdmi.HdmiCecLocalDevice.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        int i2 = message.what;
                        HdmiCecLocalDevice hdmiCecLocalDevice = HdmiCecLocalDevice.this;
                        if (i2 == 1) {
                            hdmiCecLocalDevice.handleDisableDeviceTimeout();
                        } else {
                            if (i2 != 2) {
                                return;
                            }
                            hdmiCecLocalDevice.handleUserControlReleased();
                        }
                    }
                };
    }

    public static HdmiCecLocalDevice create(HdmiControlService hdmiControlService, int i) {
        if (i == 0) {
            return new HdmiCecLocalDeviceTv(hdmiControlService);
        }
        if (i != 4) {
            if (i != 5) {
                return null;
            }
            return new HdmiCecLocalDeviceAudioSystem(hdmiControlService);
        }
        HdmiCecLocalDevicePlayback hdmiCecLocalDevicePlayback =
                new HdmiCecLocalDevicePlayback(hdmiControlService, 4);
        hdmiCecLocalDevicePlayback.mPlaybackDeviceActionOnRoutingControl =
                (HdmiProperties.playback_device_action_on_routing_control_values)
                        HdmiProperties.playback_device_action_on_routing_control()
                                .orElse(
                                        HdmiProperties
                                                .playback_device_action_on_routing_control_values
                                                .NONE);
        hdmiCecLocalDevicePlayback.mDelayedStandbyHandler =
                new Handler(hdmiControlService.mHandler.getLooper());
        Handler handler = hdmiControlService.mHandler;
        hdmiCecLocalDevicePlayback.mDelayedStandbyOnActiveSourceLostHandler =
                new Handler(handler.getLooper());
        hdmiCecLocalDevicePlayback.mDelayedPopupOnActiveSourceLostHandler =
                new Handler(handler.getLooper());
        hdmiCecLocalDevicePlayback.mStandbyHandler =
                new HdmiCecStandbyModeHandler(hdmiControlService, hdmiCecLocalDevicePlayback);
        return hdmiCecLocalDevicePlayback;
    }

    public static void injectKeyEvent(int i, int i2, int i3, long j) {
        KeyEvent obtain = KeyEvent.obtain(j, j, i, i2, i3, 0, -1, 0, 8, 33554433, null);
        InputManagerGlobal.getInstance().injectInputEvent(obtain, 0);
        obtain.recycle();
    }

    public static boolean isPowerOffOrToggleCommand(HdmiCecMessage hdmiCecMessage) {
        byte[] bArr = hdmiCecMessage.mParams;
        if (hdmiCecMessage.mOpcode != 68) {
            return false;
        }
        byte b = bArr[0];
        return b == 108 || b == 107;
    }

    public static boolean isPowerOnOrToggleCommand(HdmiCecMessage hdmiCecMessage) {
        byte[] bArr = hdmiCecMessage.mParams;
        if (hdmiCecMessage.mOpcode != 68) {
            return false;
        }
        byte b = bArr[0];
        return b == 64 || b == 109 || b == 107;
    }

    public final void addAndStartAction(HdmiCecFeatureAction hdmiCecFeatureAction) {
        assertRunOnServiceThread();
        this.mActions.add(hdmiCecFeatureAction);
        HdmiControlService hdmiControlService = this.mService;
        hdmiControlService.assertRunOnServiceThread();
        if (hdmiControlService.mPowerStatusController.mPowerStatus != 1
                && hdmiControlService.mAddressAllocated) {
            hdmiCecFeatureAction.start();
            return;
        }
        if (hdmiCecFeatureAction.getClass() != ResendCecCommandAction.class) {
            Slog.i(
                    "HdmiCecLocalDevice",
                    "Not ready to start action. Queued for deferred start:" + hdmiCecFeatureAction);
        } else {
            Slog.i(
                    "HdmiCecLocalDevice",
                    "Not ready to start ResendCecCommandAction. This action is cancelled.");
            assertRunOnServiceThread();
            hdmiCecFeatureAction.finish(false);
            this.mActions.remove(hdmiCecFeatureAction);
            checkIfPendingActionsCleared();
        }
    }

    public final void assertRunOnServiceThread() {
        if (Looper.myLooper() != this.mService.mHandler.getLooper()) {
            throw new IllegalStateException("Should run on service thread.");
        }
    }

    public final void buildAndSendSetOsdName(int i) {
        final HdmiCecMessage buildSetOsdNameCommand =
                HdmiCecMessageBuilder.buildSetOsdNameCommand(
                        this.mDeviceInfo.getLogicalAddress(), i, this.mDeviceInfo.getDisplayName());
        if (buildSetOsdNameCommand != null) {
            this.mService.sendCecCommand(
                    buildSetOsdNameCommand,
                    new HdmiControlService
                            .SendMessageCallback() { // from class:
                                                     // com.android.server.hdmi.HdmiCecLocalDevice.2
                        @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                        public final void onSendCompleted(int i2) {
                            if (i2 != 0) {
                                HdmiLogger.debug(
                                        "Failed to send cec command " + HdmiCecMessage.this,
                                        new Object[0]);
                            }
                        }
                    });
        } else {
            Slog.w(
                    "HdmiCecLocalDevice",
                    "Failed to build <Get Osd Name>:" + this.mDeviceInfo.getDisplayName());
        }
    }

    public boolean canGoToStandby() {
        return true;
    }

    public final void checkIfPendingActionsCleared() {
        AnonymousClass4 anonymousClass4;
        if (!this.mActions.isEmpty()
                || (anonymousClass4 = this.mPendingActionClearedCallback) == null) {
            return;
        }
        this.mPendingActionClearedCallback = null;
        anonymousClass4.onCleared(this);
    }

    public DeviceFeatures computeDeviceFeatures() {
        return DeviceFeatures.NO_FEATURES_SUPPORTED;
    }

    public void disableDevice(
            boolean z, PendingActionClearedCallback pendingActionClearedCallback) {
        removeAction(SetAudioVolumeLevelDiscoveryAction.class);
        removeAction(ActiveSourceAction.class);
        removeAction(ResendCecCommandAction.class);
        this.mPendingActionClearedCallback = new AnonymousClass4(pendingActionClearedCallback);
        AnonymousClass1 anonymousClass1 = this.mHandler;
        anonymousClass1.sendMessageDelayed(Message.obtain(anonymousClass1, 1), 5000L);
    }

    public int dispatchMessage(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int i = hdmiCecMessage.mDestination;
        if (i != this.mDeviceInfo.getLogicalAddress() && i != 15) {
            return -2;
        }
        HdmiControlService hdmiControlService = this.mService;
        hdmiControlService.assertRunOnServiceThread();
        int i2 = hdmiControlService.mPowerStatusController.mPowerStatus;
        int i3 = hdmiCecMessage.mOpcode;
        if (i2 == 1 && !hdmiControlService.mWakeUpMessageReceived) {
            HdmiCecStandbyModeHandler hdmiCecStandbyModeHandler = this.mStandbyHandler;
            HdmiCecStandbyModeHandler.CecMessageHandler cecMessageHandler =
                    (HdmiCecStandbyModeHandler.CecMessageHandler)
                            hdmiCecStandbyModeHandler.mCecMessageHandlers.get(i3);
            if (cecMessageHandler != null
                    ? cecMessageHandler.handle(hdmiCecMessage)
                    : hdmiCecStandbyModeHandler.mDefaultHandler.handle(hdmiCecMessage)) {
                return -1;
            }
        }
        HdmiCecMessageCache hdmiCecMessageCache = this.mCecMessageCache;
        hdmiCecMessageCache.getClass();
        if (HdmiCecMessageCache.CACHEABLE_OPCODES.contains(Integer.valueOf(i3))) {
            SparseArray sparseArray = hdmiCecMessageCache.mCache;
            int i4 = hdmiCecMessage.mSource;
            SparseArray sparseArray2 = (SparseArray) sparseArray.get(i4);
            if (sparseArray2 == null) {
                sparseArray2 = new SparseArray();
                hdmiCecMessageCache.mCache.put(i4, sparseArray2);
            }
            sparseArray2.put(i3, hdmiCecMessage);
        }
        return onMessage(hdmiCecMessage);
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("mDeviceType: " + this.mDeviceType);
        indentingPrintWriter.println("mPreferredAddress: " + this.mPreferredAddress);
        indentingPrintWriter.println("mDeviceInfo: " + this.mDeviceInfo);
        indentingPrintWriter.println("mActiveSource: " + this.mService.getLocalActiveSource());
        indentingPrintWriter.println(
                String.format(
                        "mActiveRoutingPath: 0x%04x", Integer.valueOf(this.mActiveRoutingPath)));
    }

    public int findAudioReceiverAddress() {
        Slog.w("HdmiCecLocalDevice", "findAudioReceiverAddress is not implemented");
        return -1;
    }

    public abstract int findKeyReceiverAddress();

    public List getActions(Class cls) {
        assertRunOnServiceThread();
        List emptyList = Collections.emptyList();
        Iterator it = this.mActions.iterator();
        while (it.hasNext()) {
            HdmiCecFeatureAction hdmiCecFeatureAction = (HdmiCecFeatureAction) it.next();
            if (hdmiCecFeatureAction.getClass().equals(cls)) {
                if (emptyList.isEmpty()) {
                    emptyList = new ArrayList();
                }
                emptyList.add(hdmiCecFeatureAction);
            }
        }
        return emptyList;
    }

    public final int getActivePath() {
        int i;
        synchronized (this.mLock) {
            i = this.mActiveRoutingPath;
        }
        return i;
    }

    public final int getActivePortId() {
        int physicalAddressToPortId;
        synchronized (this.mLock) {
            HdmiControlService hdmiControlService = this.mService;
            physicalAddressToPortId =
                    hdmiControlService.mHdmiCecNetwork.physicalAddressToPortId(
                            this.mActiveRoutingPath);
        }
        return physicalAddressToPortId;
    }

    public final HdmiDeviceInfo getDeviceInfo() {
        HdmiDeviceInfo hdmiDeviceInfo;
        synchronized (this.mLock) {
            hdmiDeviceInfo = this.mDeviceInfo;
        }
        return hdmiDeviceInfo;
    }

    public abstract int getPreferredAddress();

    public abstract List getRcFeatures();

    public abstract int getRcProfile();

    public abstract void handleActiveSource(HdmiCecMessage hdmiCecMessage);

    public final void handleDisableDeviceTimeout() {
        assertRunOnServiceThread();
        Iterator it = this.mActions.iterator();
        while (it.hasNext()) {
            ((HdmiCecFeatureAction) it.next()).finish(false);
            it.remove();
        }
        AnonymousClass4 anonymousClass4 = this.mPendingActionClearedCallback;
        if (anonymousClass4 != null) {
            anonymousClass4.onCleared(this);
        }
    }

    public int handleGetMenuLanguage(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        Slog.w(
                "HdmiCecLocalDevice",
                "Only TV can handle <Get Menu Language>:" + hdmiCecMessage.toString());
        return -2;
    }

    public int handleGiveAudioStatus(HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    public int handleGiveSystemAudioModeStatus(HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    public int handleImageViewOn(HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    public int handleInactiveSource(HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    public int handleInitiateArc(HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    public int handleMenuStatus() {
        return -2;
    }

    public int handleRecordStatus() {
        return -2;
    }

    public int handleRecordTvScreen(HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    public int handleReportArcInitiate() {
        return -2;
    }

    public int handleReportArcTermination() {
        return -2;
    }

    public int handleReportAudioStatus(HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    public void handleReportPhysicalAddress(HdmiCecMessage hdmiCecMessage) {
        int i = hdmiCecMessage.mSource;
        if (hasAction(DeviceDiscoveryAction.class)) {
            Slog.i(
                    "HdmiCecLocalDevice",
                    "Ignored while Device Discovery Action is in progress: " + hdmiCecMessage);
            return;
        }
        HdmiControlService hdmiControlService = this.mService;
        HdmiDeviceInfo cecDeviceInfo = hdmiControlService.mHdmiCecNetwork.getCecDeviceInfo(i);
        if (hdmiControlService.isTvDevice() || cecDeviceInfo == null) {
            return;
        }
        if (cecDeviceInfo
                .getDisplayName()
                .equals(HdmiUtils.isValidAddress(i) ? HdmiUtils.DEFAULT_NAMES[i] : "")) {
            hdmiControlService.sendCecCommand(
                    HdmiCecMessage.build(this.mDeviceInfo.getLogicalAddress(), i, 70), null);
        }
    }

    public abstract void handleRequestActiveSource(HdmiCecMessage hdmiCecMessage);

    public int handleRequestArcInitiate() {
        return -2;
    }

    public int handleRequestArcTermination() {
        return -2;
    }

    public int handleRequestShortAudioDescriptor(HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    public abstract int handleRoutingChange(HdmiCecMessage hdmiCecMessage);

    public int handleRoutingInformation(HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    public int handleSetAudioVolumeLevel(SetAudioVolumeLevelMessage setAudioVolumeLevelMessage) {
        return -2;
    }

    public int handleSetMenuLanguage(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        Slog.w(
                "HdmiCecLocalDevice",
                "Only Playback device can handle <Set Menu Language>:" + hdmiCecMessage.toString());
        return -2;
    }

    public int handleSetStreamPath(HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    public abstract int handleSetSystemAudioMode(HdmiCecMessage hdmiCecMessage);

    public int handleStandby(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        if (!hdmiControlService.isCecControlEnabled()
                || hdmiControlService.isProhibitMode()
                || !hdmiControlService.isPowerOnOrTransient()) {
            return 1;
        }
        hdmiControlService.standby();
        return -1;
    }

    public int handleSystemAudioModeRequest(HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    public abstract int handleSystemAudioModeStatus(HdmiCecMessage hdmiCecMessage);

    public int handleTerminateArc(HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    public int handleTextViewOn() {
        return -2;
    }

    public int handleTimerClearedStatus(HdmiCecMessage hdmiCecMessage) {
        return -2;
    }

    public int handleTimerStatus() {
        return -2;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0090 A[LOOP:0: B:36:0x006a->B:42:0x0090, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0096 A[EDGE_INSN: B:43:0x0096->B:44:0x0096 BREAK  A[LOOP:0: B:36:0x006a->B:42:0x0090], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00b8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int handleUserControlPressed(com.android.server.hdmi.HdmiCecMessage r17) {
        /*
            Method dump skipped, instructions count: 224
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.hdmi.HdmiCecLocalDevice.handleUserControlPressed(com.android.server.hdmi.HdmiCecMessage):int");
    }

    public final void handleUserControlReleased() {
        assertRunOnServiceThread();
        removeMessages(2);
        this.mLastKeyRepeatCount = 0;
        if (this.mLastKeycode != -1) {
            injectKeyEvent(1, this.mLastKeycode, 0, SystemClock.uptimeMillis());
            this.mLastKeycode = -1;
        }
    }

    public final boolean hasAction(Class cls) {
        assertRunOnServiceThread();
        Iterator it = this.mActions.iterator();
        while (it.hasNext()) {
            if (((HdmiCecFeatureAction) it.next()).getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    public final void invokeCallback(int i, IHdmiControlCallback iHdmiControlCallback) {
        assertRunOnServiceThread();
        if (iHdmiControlCallback == null) {
            return;
        }
        try {
            iHdmiControlCallback.onComplete(i);
        } catch (RemoteException e) {
            Slog.e("HdmiCecLocalDevice", "Invoking callback failed:" + e);
        }
    }

    public void invokeStandbyCompletedCallback(StandbyCompletedCallback standbyCompletedCallback) {
        assertRunOnServiceThread();
        if (standbyCompletedCallback == null) {
            return;
        }
        HdmiControlService.AnonymousClass27 anonymousClass27 =
                (HdmiControlService.AnonymousClass27) standbyCompletedCallback;
        int[] iArr = (int[]) anonymousClass27.val$devices;
        int i = iArr[0] + 1;
        iArr[0] = i;
        if (anonymousClass27.val$standbyAction < i) {
            return;
        }
        HdmiControlService hdmiControlService = HdmiControlService.this;
        hdmiControlService.releaseWakeLock();
        if (hdmiControlService.isAudioSystemDevice()) {
            return;
        }
        hdmiControlService.assertRunOnServiceThread();
        if (hdmiControlService.mPowerStatusController.mPowerStatus == 1) {
            hdmiControlService.mCecController.enableSystemCecControl(false);
            hdmiControlService.mMhlController.getClass();
        }
    }

    public boolean isAlreadyActiveSource(
            HdmiDeviceInfo hdmiDeviceInfo, int i, IHdmiControlCallback iHdmiControlCallback) {
        ActiveSource localActiveSource = this.mService.getLocalActiveSource();
        if (hdmiDeviceInfo.getDevicePowerStatus() != 0
                || !HdmiUtils.isValidAddress(localActiveSource.logicalAddress)
                || i != localActiveSource.logicalAddress) {
            return false;
        }
        invokeCallback(0, iHdmiControlCallback);
        return true;
    }

    public final boolean isConnectedToArcPort(int i) {
        assertRunOnServiceThread();
        HdmiCecNetwork hdmiCecNetwork = this.mService.mHdmiCecNetwork;
        int physicalAddressToPortId = hdmiCecNetwork.physicalAddressToPortId(i);
        if (physicalAddressToPortId == -1 || physicalAddressToPortId == 0) {
            return false;
        }
        return ((HdmiPortInfo) hdmiCecNetwork.mPortInfoMap.mArray.get(physicalAddressToPortId))
                .isArcSupported();
    }

    public boolean isInputReady(int i) {
        return true;
    }

    public abstract void onAddressAllocated(int i);

    public abstract void onHotplug(int i, boolean z);

    public void onInitializeCecComplete(int i) {}

    public final int onMessage(HdmiCecMessage hdmiCecMessage) {
        boolean z;
        assertRunOnServiceThread();
        assertRunOnServiceThread();
        Iterator it = new ArrayList(this.mActions).iterator();
        loop0:
        while (true) {
            z = false;
            while (it.hasNext()) {
                boolean processCommand =
                        ((HdmiCecFeatureAction) it.next()).processCommand(hdmiCecMessage);
                if (z || processCommand) {
                    z = true;
                }
            }
        }
        if (z) {
            return -1;
        }
        if (hdmiCecMessage instanceof SetAudioVolumeLevelMessage) {
            return handleSetAudioVolumeLevel((SetAudioVolumeLevelMessage) hdmiCecMessage);
        }
        int i = hdmiCecMessage.mOpcode;
        if (i == 4) {
            return handleImageViewOn(hdmiCecMessage);
        }
        if (i == 10) {
            return handleRecordStatus();
        }
        if (i == 13) {
            return handleTextViewOn();
        }
        if (i == 15) {
            return handleRecordTvScreen(hdmiCecMessage);
        }
        if (i == 50) {
            return handleSetMenuLanguage(hdmiCecMessage);
        }
        if (i == 122) {
            return handleReportAudioStatus(hdmiCecMessage);
        }
        HdmiControlService hdmiControlService = this.mService;
        byte[] bArr = hdmiCecMessage.mParams;
        int i2 = hdmiCecMessage.mDestination;
        int i3 = hdmiCecMessage.mSource;
        if (i == 137) {
            return !hdmiControlService.invokeVendorCommandListenersOnReceived(i3, i2, bArr, false)
                    ? 4
                    : -1;
        }
        if (i == 53) {
            return handleTimerStatus();
        }
        if (i == 54) {
            return handleStandby(hdmiCecMessage);
        }
        if (i == 125) {
            return handleGiveSystemAudioModeStatus(hdmiCecMessage);
        }
        if (i == 126) {
            return handleSystemAudioModeStatus(hdmiCecMessage);
        }
        if (i == 164) {
            return handleRequestShortAudioDescriptor(hdmiCecMessage);
        }
        if (i == 165) {
            if (hdmiControlService.getCecVersion() < 6) {
                return 0;
            }
            reportFeatures();
            return -1;
        }
        switch (i) {
            case 67:
                return handleTimerClearedStatus(hdmiCecMessage);
            case 68:
                return handleUserControlPressed(hdmiCecMessage);
            case 69:
                handleUserControlReleased();
                return -1;
            case 70:
                assertRunOnServiceThread();
                buildAndSendSetOsdName(i3);
                return -1;
            case 71:
                return -1;
            default:
                switch (i) {
                    case 112:
                        return handleSystemAudioModeRequest(hdmiCecMessage);
                    case 113:
                        return handleGiveAudioStatus(hdmiCecMessage);
                    case 114:
                        return handleSetSystemAudioMode(hdmiCecMessage);
                    default:
                        switch (i) {
                            case 128:
                                return handleRoutingChange(hdmiCecMessage);
                            case 129:
                                return handleRoutingInformation(hdmiCecMessage);
                            case 130:
                                handleActiveSource(hdmiCecMessage);
                                return -1;
                            case 131:
                                assertRunOnServiceThread();
                                int physicalAddress =
                                        hdmiControlService.mHdmiCecNetwork.getPhysicalAddress();
                                if (physicalAddress == 65535) {
                                    hdmiControlService.assertRunOnServiceThread();
                                    hdmiControlService.mCecController.maySendFeatureAbortCommand(
                                            5, hdmiCecMessage);
                                } else {
                                    hdmiControlService.sendCecCommand(
                                            HdmiCecMessageBuilder.buildReportPhysicalAddressCommand(
                                                    this.mDeviceInfo.getLogicalAddress(),
                                                    physicalAddress,
                                                    this.mDeviceType),
                                            null);
                                }
                                return -1;
                            case 132:
                                handleReportPhysicalAddress(hdmiCecMessage);
                                return -1;
                            case 133:
                                handleRequestActiveSource(hdmiCecMessage);
                                return -1;
                            case 134:
                                return handleSetStreamPath(hdmiCecMessage);
                            default:
                                switch (i) {
                                    case 140:
                                        assertRunOnServiceThread();
                                        int vendorId = hdmiControlService.getVendorId();
                                        if (vendorId == 1) {
                                            hdmiControlService.assertRunOnServiceThread();
                                            hdmiControlService.mCecController
                                                    .maySendFeatureAbortCommand(5, hdmiCecMessage);
                                        } else {
                                            hdmiControlService.sendCecCommand(
                                                    HdmiCecMessageBuilder
                                                            .buildDeviceVendorIdCommand(
                                                                    this.mDeviceInfo
                                                                            .getLogicalAddress(),
                                                                    vendorId),
                                                    null);
                                        }
                                        return -1;
                                    case 141:
                                        hdmiControlService.sendCecCommand(
                                                HdmiCecMessage.build(
                                                        this.mDeviceInfo.getLogicalAddress(),
                                                        i3,
                                                        142,
                                                        new byte[] {(byte) 0}),
                                                null);
                                        return -1;
                                    case 142:
                                        return handleMenuStatus();
                                    case 143:
                                        int logicalAddress = this.mDeviceInfo.getLogicalAddress();
                                        hdmiControlService.assertRunOnServiceThread();
                                        hdmiControlService.sendCecCommand(
                                                HdmiCecMessage.build(
                                                        logicalAddress,
                                                        i3,
                                                        144,
                                                        new byte[] {
                                                            (byte)
                                                                    (hdmiControlService
                                                                                    .mPowerStatusController
                                                                                    .mPowerStatus
                                                                            & IDnsResolverUnsolicitedEventListener
                                                                                    .DNS_HEALTH_RESULT_TIMEOUT)
                                                        }),
                                                null);
                                        return -1;
                                    case 144:
                                        return -1;
                                    case 145:
                                        return handleGetMenuLanguage(hdmiCecMessage);
                                    default:
                                        switch (i) {
                                            case FrameworkStatsLog
                                                    .DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_EMPTY_STATE_WORK_APPS_DISABLED /* 157 */:
                                                return handleInactiveSource(hdmiCecMessage);
                                            case FrameworkStatsLog
                                                    .DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_EMPTY_STATE_NO_SHARING_TO_PERSONAL /* 158 */:
                                                assertRunOnServiceThread();
                                                return -1;
                                            case FrameworkStatsLog
                                                    .DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_EMPTY_STATE_NO_SHARING_TO_WORK /* 159 */:
                                                assertRunOnServiceThread();
                                                hdmiControlService.sendCecCommand(
                                                        HdmiCecMessage.build(
                                                                i2,
                                                                i3,
                                                                FrameworkStatsLog
                                                                        .DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_EMPTY_STATE_NO_SHARING_TO_PERSONAL,
                                                                new byte[] {
                                                                    (byte)
                                                                            (hdmiControlService
                                                                                            .getCecVersion()
                                                                                    & IDnsResolverUnsolicitedEventListener
                                                                                            .DNS_HEALTH_RESULT_TIMEOUT)
                                                                }),
                                                        null);
                                                return -1;
                                            case 160:
                                                HdmiUtils.threeBytesToInt(bArr);
                                                if (i2 != 15 && i3 != 15) {
                                                    return !hdmiControlService
                                                                    .invokeVendorCommandListenersOnReceived(
                                                                            i3, i2, bArr, true)
                                                            ? 4
                                                            : -1;
                                                }
                                                Slog.v(
                                                        "HdmiCecLocalDevice",
                                                        "Wrong broadcast vendor command. Ignoring");
                                                return -1;
                                            default:
                                                switch (i) {
                                                    case 192:
                                                        return handleInitiateArc(hdmiCecMessage);
                                                    case 193:
                                                        return handleReportArcInitiate();
                                                    case FrameworkStatsLog
                                                            .DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_PROVISIONING_ERROR /* 194 */:
                                                        return handleReportArcTermination();
                                                    case 195:
                                                        return handleRequestArcInitiate();
                                                    case 196:
                                                        return handleRequestArcTermination();
                                                    case 197:
                                                        return handleTerminateArc(hdmiCecMessage);
                                                    default:
                                                        return -2;
                                                }
                                        }
                                }
                        }
                }
        }
    }

    public abstract void onStandby(
            boolean z, int i, HdmiControlService.AnonymousClass27 anonymousClass27);

    public void preprocessBufferedMessages(List list) {}

    public final void removeAction(Class cls) {
        assertRunOnServiceThread();
        removeActionExcept(cls, null);
    }

    public final void removeActionExcept(Class cls, HdmiCecFeatureAction hdmiCecFeatureAction) {
        assertRunOnServiceThread();
        Iterator it = this.mActions.iterator();
        while (it.hasNext()) {
            HdmiCecFeatureAction hdmiCecFeatureAction2 = (HdmiCecFeatureAction) it.next();
            if (hdmiCecFeatureAction2 != hdmiCecFeatureAction
                    && hdmiCecFeatureAction2.getClass().equals(cls)) {
                hdmiCecFeatureAction2.finish(false);
                it.remove();
            }
        }
        checkIfPendingActionsCleared();
    }

    public final void reportFeatures() {
        int logicalAddress;
        int i;
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) this.mService.getAllCecLocalDevices()).iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(((HdmiCecLocalDevice) it.next()).mDeviceType));
        }
        int rcProfile = getRcProfile();
        List rcFeatures = getRcFeatures();
        HdmiDeviceInfo build =
                getDeviceInfo().toBuilder().setDeviceFeatures(computeDeviceFeatures()).build();
        synchronized (this.mLock) {
            this.mDeviceInfo = build;
        }
        DeviceFeatures deviceFeatures = getDeviceInfo().getDeviceFeatures();
        synchronized (this.mLock) {
            logicalAddress = this.mDeviceInfo.getLogicalAddress();
        }
        HdmiControlService hdmiControlService = this.mService;
        int cecVersion = hdmiControlService.getCecVersion();
        int i2 = ReportFeaturesMessage.$r8$clinit;
        byte b =
                (byte)
                        (cecVersion
                                & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
        Iterator it2 = arrayList.iterator();
        byte b2 = 0;
        while (it2.hasNext()) {
            int intValue = ((Integer) it2.next()).intValue();
            if (intValue == 0) {
                i = 7;
            } else if (intValue != 1) {
                i = 5;
                if (intValue == 3) {
                    continue;
                } else if (intValue == 4) {
                    i = 4;
                } else if (intValue == 5) {
                    i = 3;
                } else {
                    if (intValue != 6) {
                        throw new IllegalArgumentException(
                                VibrationParam$1$$ExternalSyntheticOutline0.m(
                                        intValue, "Unhandled device type: "));
                    }
                    i = 2;
                }
            } else {
                i = 6;
            }
            b2 = (byte) (((byte) (1 << i)) | b2);
        }
        byte b3 = (byte) (rcProfile << 6);
        if (rcProfile == 1) {
            Iterator it3 = ((ArrayList) rcFeatures).iterator();
            while (it3.hasNext()) {
                b3 = (byte) (b3 | ((byte) (1 << ((Integer) it3.next()).intValue())));
            }
        } else {
            b3 =
                    (byte)
                            (b3
                                    | ((byte)
                                            (((Integer) ((ArrayList) rcFeatures).get(0)).intValue()
                                                    & GnssNative.GNSS_AIDING_TYPE_ALL)));
        }
        byte[] bArr = {b, b2, b3};
        byte[] operand = deviceFeatures.toOperand();
        byte[] copyOf = Arrays.copyOf(bArr, operand.length + 3);
        System.arraycopy(operand, 0, copyOf, 3, operand.length);
        int validateAddress =
                HdmiCecMessageValidator.validateAddress(logicalAddress, 15, 32767, 32768);
        hdmiControlService.sendCecCommand(
                validateAddress != 0
                        ? new HdmiCecMessage(
                                logicalAddress,
                                15,
                                FrameworkStatsLog
                                        .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_MISSING_PERSONAL_APP,
                                validateAddress,
                                copyOf)
                        : new ReportFeaturesMessage(
                                logicalAddress, 15, copyOf, cecVersion, deviceFeatures),
                null);
    }

    public abstract void sendStandby(int i);

    public final void sendUserControlPressedAndReleased(int i, int i2) {
        HdmiCecMessage build =
                HdmiCecMessage.build(
                        this.mDeviceInfo.getLogicalAddress(),
                        i,
                        68,
                        new byte[] {
                            (byte)
                                    (i2
                                            & IDnsResolverUnsolicitedEventListener
                                                    .DNS_HEALTH_RESULT_TIMEOUT)
                        });
        HdmiControlService hdmiControlService = this.mService;
        hdmiControlService.sendCecCommand(build, null);
        hdmiControlService.sendCecCommand(
                HdmiCecMessage.build(this.mDeviceInfo.getLogicalAddress(), i, 69), null);
    }

    public final void sendVolumeKeyEvent(int i, boolean z) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        if (hdmiControlService.getHdmiCecVolumeControl() == 0) {
            return;
        }
        if (!HdmiCecKeycode.isVolumeKeycode(i)) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i, "Not a volume key: ", "HdmiCecLocalDevice");
            return;
        }
        List actions = getActions(SendKeyAction.class);
        int findAudioReceiverAddress = findAudioReceiverAddress();
        if (findAudioReceiverAddress == -1
                || hdmiControlService.getAllCecLocalDevices().stream()
                        .anyMatch(
                                new HdmiCecLocalDevice$$ExternalSyntheticLambda0(
                                        findAudioReceiverAddress, 1))) {
            StringBuilder sb = new StringBuilder("Discard volume key event: ");
            sb.append(i);
            sb.append(", pressed:");
            sb.append(z);
            sb.append(", receiverAddr=");
            HeapdumpWatcher$$ExternalSyntheticOutline0.m(
                    sb, findAudioReceiverAddress, "HdmiCecLocalDevice");
            return;
        }
        if (!actions.isEmpty()) {
            ((SendKeyAction) actions.get(0)).processKeyEvent(i, z);
        } else if (z) {
            addAndStartAction(new SendKeyAction(this, findAudioReceiverAddress, i));
        }
    }

    public final void setActivePath(int i) {
        synchronized (this.mLock) {
            this.mActiveRoutingPath = i;
        }
        HdmiControlService hdmiControlService = this.mService;
        assertRunOnServiceThread();
        int physicalAddressToPortId = this.mService.mHdmiCecNetwork.physicalAddressToPortId(i);
        hdmiControlService.assertRunOnServiceThread();
        hdmiControlService.mActivePortId = physicalAddressToPortId;
        hdmiControlService.assertRunOnServiceThread();
    }

    public void setActiveSource(int i, int i2, String str) {
        HdmiControlService hdmiControlService = this.mService;
        hdmiControlService.setActiveSource(i, i2, str);
        hdmiControlService.assertRunOnServiceThread();
    }

    public abstract void setPreferredAddress(int i);

    public final void startQueuedActions() {
        assertRunOnServiceThread();
        Iterator it = new ArrayList(this.mActions).iterator();
        while (it.hasNext()) {
            HdmiCecFeatureAction hdmiCecFeatureAction = (HdmiCecFeatureAction) it.next();
            if (hdmiCecFeatureAction.mState == 0) {
                Slog.i("HdmiCecLocalDevice", "Starting queued action:" + hdmiCecFeatureAction);
                hdmiCecFeatureAction.start();
            }
        }
    }

    public final void updateAvbVolume(int i) {
        assertRunOnServiceThread();
        for (AbsoluteVolumeAudioStatusAction absoluteVolumeAudioStatusAction :
                getActions(AbsoluteVolumeAudioStatusAction.class)) {
            absoluteVolumeAudioStatusAction.mLastAudioStatus =
                    new AudioStatus(i, absoluteVolumeAudioStatusAction.mLastAudioStatus.mMute);
        }
    }
}
