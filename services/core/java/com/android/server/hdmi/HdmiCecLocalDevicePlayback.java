package com.android.server.hdmi;

import android.R;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.IHdmiControlCallback;
import android.os.Binder;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.sysprop.HdmiProperties;
import android.util.Slog;

import com.android.internal.app.LocalePicker;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public final class HdmiCecLocalDevicePlayback extends HdmiCecLocalDeviceSource {
    static final long STANDBY_AFTER_ACTIVE_SOURCE_LOST_DELAY_MS = 30000;
    static final long STANDBY_AFTER_HOTPLUG_OUT_DELAY_MS = 30000;
    public Handler mDelayedPopupOnActiveSourceLostHandler;
    public Handler mDelayedStandbyHandler;
    public Handler mDelayedStandbyOnActiveSourceLostHandler;
    protected HdmiProperties.playback_device_action_on_routing_control_values
            mPlaybackDeviceActionOnRoutingControl;
    public ActiveWakeLock mWakeLock;

    /* renamed from: com.android.server.hdmi.HdmiCecLocalDevicePlayback$1, reason: invalid class name */
    public final class AnonymousClass1
            implements HdmiControlService.SendMessageCallback,
                    DeviceDiscoveryAction.DeviceDiscoveryCallback {
        public /* synthetic */ AnonymousClass1() {}

        @Override // com.android.server.hdmi.DeviceDiscoveryAction.DeviceDiscoveryCallback
        public void onDeviceDiscoveryDone(List list) {
            HdmiCecLocalDevicePlayback hdmiCecLocalDevicePlayback;
            Iterator it = ((ArrayList) list).iterator();
            while (true) {
                boolean hasNext = it.hasNext();
                hdmiCecLocalDevicePlayback = HdmiCecLocalDevicePlayback.this;
                if (!hasNext) {
                    break;
                }
                hdmiCecLocalDevicePlayback.mService.mHdmiCecNetwork.addCecDevice(
                        (HdmiDeviceInfo) it.next());
            }
            Iterator it2 =
                    ((ArrayList) hdmiCecLocalDevicePlayback.mService.getAllCecLocalDevices())
                            .iterator();
            while (it2.hasNext()) {
                hdmiCecLocalDevicePlayback.mService.mHdmiCecNetwork.addCecDevice(
                        ((HdmiCecLocalDevice) it2.next()).getDeviceInfo());
            }
            if (hdmiCecLocalDevicePlayback.getActions(HotplugDetectionAction.class).isEmpty()) {
                hdmiCecLocalDevicePlayback.addAndStartAction(
                        new HotplugDetectionAction(hdmiCecLocalDevicePlayback));
            }
        }

        @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
        public void onSendCompleted(int i) {
            if (i == 1) {
                HdmiLogger.debug(
                        "AVR did not respond to <Give System Audio Mode Status>", new Object[0]);
                HdmiCecLocalDevicePlayback.this.mService.setSystemAudioActivated(false);
            }
        }
    }

    /* renamed from: com.android.server.hdmi.HdmiCecLocalDevicePlayback$3, reason: invalid class name */
    public final class AnonymousClass3
            implements HdmiControlService.SendMessageCallback, ActiveWakeLock {
        public final Object val$callback;

        public AnonymousClass3() {
            PowerManager.WakeLock newWakeLock =
                    HdmiCecLocalDevicePlayback.this.mService.mPowerManager.mPowerManager
                            .newWakeLock(1, "HdmiCecLocalDevicePlayback");
            this.val$callback = new PowerManagerWrapper.DefaultWakeLockWrapper(newWakeLock);
            newWakeLock.setReferenceCounted(false);
        }

        public AnonymousClass3(HdmiControlService.AnonymousClass27 anonymousClass27) {
            this.val$callback = anonymousClass27;
        }

        @Override // com.android.server.hdmi.HdmiCecLocalDevicePlayback.ActiveWakeLock
        public void acquire() {
            ((PowerManagerWrapper.DefaultWakeLockWrapper) this.val$callback).mWakeLock.acquire();
            HdmiLogger.debug(
                    "active source: %b. Wake lock acquired",
                    Boolean.valueOf(HdmiCecLocalDevicePlayback.this.isActiveSource()));
        }

        @Override // com.android.server.hdmi.HdmiCecLocalDevicePlayback.ActiveWakeLock
        public boolean isHeld() {
            return ((PowerManagerWrapper.DefaultWakeLockWrapper) this.val$callback)
                    .mWakeLock.isHeld();
        }

        @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
        public void onSendCompleted(int i) {
            HdmiCecLocalDevicePlayback.this.invokeStandbyCompletedCallback(
                    (HdmiCecLocalDevice.StandbyCompletedCallback) this.val$callback);
        }

        @Override // com.android.server.hdmi.HdmiCecLocalDevicePlayback.ActiveWakeLock
        public void release() {
            ((PowerManagerWrapper.DefaultWakeLockWrapper) this.val$callback).mWakeLock.release();
            HdmiLogger.debug("Wake lock released", new Object[0]);
        }
    }

    /* renamed from: com.android.server.hdmi.HdmiCecLocalDevicePlayback$4, reason: invalid class name */
    public final class AnonymousClass4 extends IHdmiControlCallback.Stub {
        public final void onComplete(int i) {
            if (i != 0) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i,
                        "Failed to complete One Touch Play. result=",
                        "HdmiCecLocalDevicePlayback");
            }
        }
    }

    /* renamed from: com.android.server.hdmi.HdmiCecLocalDevicePlayback$5, reason: invalid class name */
    public final class AnonymousClass5 implements ActiveWakeLock {
        @Override // com.android.server.hdmi.HdmiCecLocalDevicePlayback.ActiveWakeLock
        public final void acquire() {}

        @Override // com.android.server.hdmi.HdmiCecLocalDevicePlayback.ActiveWakeLock
        public final boolean isHeld() {
            return false;
        }

        @Override // com.android.server.hdmi.HdmiCecLocalDevicePlayback.ActiveWakeLock
        public final void release() {}
    }

    /* renamed from: com.android.server.hdmi.HdmiCecLocalDevicePlayback$6, reason: invalid class name */
    public final class AnonymousClass6 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ HdmiCecLocalDevicePlayback this$0;

        public /* synthetic */ AnonymousClass6(
                HdmiCecLocalDevicePlayback hdmiCecLocalDevicePlayback, int i) {
            this.$r8$classId = i;
            this.this$0 = hdmiCecLocalDevicePlayback;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    if (!this.this$0.isActiveSource()) {
                        this.this$0.startHdmiCecActiveSourceLostActivity();
                        this.this$0.mDelayedStandbyOnActiveSourceLostHandler
                                .removeCallbacksAndMessages(null);
                        HdmiCecLocalDevicePlayback hdmiCecLocalDevicePlayback = this.this$0;
                        hdmiCecLocalDevicePlayback.mDelayedStandbyOnActiveSourceLostHandler
                                .postDelayed(
                                        new AnonymousClass6(hdmiCecLocalDevicePlayback, 1), 30000L);
                        break;
                    }
                    break;
                case 1:
                    if (!this.this$0.mService.mPowerManagerInternal.mPowerManagerInternal
                            .wasDeviceIdleFor(30000L)) {
                        HdmiControlService hdmiControlService = this.this$0.mService;
                        hdmiControlService.setAndBroadcastActiveSource(
                                hdmiControlService.mHdmiCecNetwork.getPhysicalAddress(),
                                this.this$0.getDeviceInfo().getDeviceType(),
                                0,
                                "DelayedActiveSourceLostStandbyRunnable");
                        break;
                    } else {
                        this.this$0.mService.standby();
                        break;
                    }
                default:
                    if (!this.this$0.mService.mPowerManagerInternal.mPowerManagerInternal
                            .wasDeviceIdleFor(30000L)) {
                        HdmiCecLocalDevicePlayback hdmiCecLocalDevicePlayback2 = this.this$0;
                        hdmiCecLocalDevicePlayback2.mDelayedStandbyHandler.postDelayed(
                                new AnonymousClass6(hdmiCecLocalDevicePlayback2, 2), 30000L);
                        break;
                    } else {
                        this.this$0.mService.standby();
                        break;
                    }
            }
        }
    }

    /* renamed from: com.android.server.hdmi.HdmiCecLocalDevicePlayback$7, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass7 {
        public static final /* synthetic */ int[]
                $SwitchMap$android$sysprop$HdmiProperties$playback_device_action_on_routing_control_values;

        static {
            int[] iArr =
                    new int
                            [HdmiProperties.playback_device_action_on_routing_control_values
                                    .values()
                                    .length];
            $SwitchMap$android$sysprop$HdmiProperties$playback_device_action_on_routing_control_values =
                    iArr;
            try {
                iArr[
                                HdmiProperties.playback_device_action_on_routing_control_values
                                        .WAKE_UP_AND_SEND_ACTIVE_SOURCE.ordinal()] =
                        1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$sysprop$HdmiProperties$playback_device_action_on_routing_control_values[
                                HdmiProperties.playback_device_action_on_routing_control_values
                                        .WAKE_UP_ONLY.ordinal()] =
                        2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$sysprop$HdmiProperties$playback_device_action_on_routing_control_values[
                                HdmiProperties.playback_device_action_on_routing_control_values.NONE
                                        .ordinal()] =
                        3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public interface ActiveWakeLock {
        void acquire();

        boolean isHeld();

        void release();
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final boolean canGoToStandby() {
        return !getWakeLock().isHeld();
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource,
              // com.android.server.hdmi.HdmiCecLocalDevice
    public final void disableDevice(
            boolean z,
            HdmiCecLocalDevice.PendingActionClearedCallback pendingActionClearedCallback) {
        assertRunOnServiceThread();
        removeAction(DeviceDiscoveryAction.class);
        removeAction(HotplugDetectionAction.class);
        removeAction(NewDeviceAction.class);
        super.disableDevice(z, pendingActionClearedCallback);
        assertRunOnServiceThread();
        this.mService.mHdmiCecNetwork.clearDeviceList();
        checkIfPendingActionsCleared();
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        super.dump(indentingPrintWriter);
        indentingPrintWriter.println("isActiveSource(): " + isActiveSource());
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int findAudioReceiverAddress() {
        return this.mService.isSystemAudioActivated() ? 5 : 0;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int findKeyReceiverAddress() {
        return 0;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int getPreferredAddress() {
        assertRunOnServiceThread();
        return SystemProperties.getInt("persist.sys.hdmi.addr.playback", 15);
    }

    public final ActiveWakeLock getWakeLock() {
        assertRunOnServiceThread();
        if (this.mWakeLock == null) {
            if (SystemProperties.getBoolean("persist.sys.hdmi.keep_awake", true)) {
                this.mWakeLock = new AnonymousClass3();
            } else {
                this.mWakeLock = new AnonymousClass5();
                HdmiLogger.debug("No wakelock is used to keep the display on.", new Object[0]);
            }
        }
        return this.mWakeLock;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource,
              // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleRoutingChange(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        handleRoutingChangeAndInformation(
                HdmiUtils.twoBytesToInt(2, hdmiCecMessage.mParams), hdmiCecMessage);
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource
    public final void handleRoutingChangeAndInformation(int i, HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        if (HdmiUtils.isInActiveRoutingPath(
                        hdmiControlService.mHdmiCecNetwork.getPhysicalAddress(), i)
                && i != 0
                && isActiveSource()) {
            return;
        }
        if (i != hdmiControlService.mHdmiCecNetwork.getPhysicalAddress()) {
            assertRunOnServiceThread();
            setActiveSource(
                    -1, i, "HdmiCecLocalDevicePlayback#handleRoutingChangeAndInformation()");
            return;
        }
        if (!isActiveSource()) {
            assertRunOnServiceThread();
            setActiveSource(
                    -1, i, "HdmiCecLocalDevicePlayback#handleRoutingChangeAndInformation()");
        }
        assertRunOnServiceThread();
        hdmiControlService.sendBroadcastAsUser(
                new Intent("android.hardware.hdmi.action.ON_ACTIVE_SOURCE_RECOVERED_DISMISS_UI"));
        int i2 =
                AnonymousClass7
                        .$SwitchMap$android$sysprop$HdmiProperties$playback_device_action_on_routing_control_values[
                        this.mPlaybackDeviceActionOnRoutingControl.ordinal()];
        if (i2 == 1) {
            hdmiControlService.setAndBroadcastActiveSource(
                    i,
                    getDeviceInfo().getDeviceType(),
                    hdmiCecMessage.mSource,
                    "HdmiCecLocalDevicePlayback#handleRoutingChangeAndInformation()");
        } else {
            if (i2 != 2) {
                return;
            }
            hdmiControlService.wakeUp();
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource,
              // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleRoutingInformation(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int twoBytesToInt = HdmiUtils.twoBytesToInt(hdmiCecMessage.mParams);
        HdmiDeviceInfo cecDeviceInfo =
                this.mService.mHdmiCecNetwork.getCecDeviceInfo(hdmiCecMessage.mSource);
        if (cecDeviceInfo == null
                || cecDeviceInfo.getLogicalAddress() == 0
                || cecDeviceInfo.getPhysicalAddress() != twoBytesToInt) {
            handleRoutingChangeAndInformation(twoBytesToInt, hdmiCecMessage);
            return -1;
        }
        Slog.d(
                "HdmiCecLocalDevicePlayback",
                "<Routing Information> is ignored, it is pointing to the same physical address as"
                    + " the message sender");
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleSetMenuLanguage(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        if (hdmiControlService.getHdmiCecConfig().getIntValue("set_menu_language") == 0) {
            return 0;
        }
        try {
            String str = new String(hdmiCecMessage.mParams, 0, 3, "US-ASCII");
            String localeToMenuLanguage =
                    HdmiControlService.localeToMenuLanguage(
                            hdmiControlService
                                    .getContext()
                                    .getResources()
                                    .getConfiguration()
                                    .locale);
            HdmiLogger.debug(
                    "handleSetMenuLanguage " + str + " cur:" + localeToMenuLanguage, new Object[0]);
            if (localeToMenuLanguage.equals(str)) {
                return -1;
            }
            for (LocalePicker.LocaleInfo localeInfo :
                    LocalePicker.getAllAssetLocales(hdmiControlService.getContext(), false)) {
                if (HdmiControlService.localeToMenuLanguage(localeInfo.getLocale()).equals(str)) {
                    startSetMenuLanguageActivity(localeInfo.getLocale());
                    return -1;
                }
            }
            Slog.w("HdmiCecLocalDevicePlayback", "Can't handle <Set Menu Language> of " + str);
            return 3;
        } catch (UnsupportedEncodingException e) {
            Slog.w("HdmiCecLocalDevicePlayback", "Can't handle <Set Menu Language>", e);
            return 3;
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleSetSystemAudioMode(HdmiCecMessage hdmiCecMessage) {
        boolean parseCommandParamSystemAudioStatus;
        if (hdmiCecMessage.mDestination == 15 && hdmiCecMessage.mSource == 5) {
            HdmiControlService hdmiControlService = this.mService;
            if (hdmiControlService.audioSystem() == null
                    && hdmiControlService.isSystemAudioActivated()
                            != (parseCommandParamSystemAudioStatus =
                                    HdmiUtils.parseCommandParamSystemAudioStatus(hdmiCecMessage))) {
                hdmiControlService.setSystemAudioActivated(parseCommandParamSystemAudioStatus);
            }
        }
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleSystemAudioModeStatus(HdmiCecMessage hdmiCecMessage) {
        if (hdmiCecMessage.mDestination != getDeviceInfo().getLogicalAddress()
                || hdmiCecMessage.mSource != 5) {
            return -1;
        }
        boolean parseCommandParamSystemAudioStatus =
                HdmiUtils.parseCommandParamSystemAudioStatus(hdmiCecMessage);
        HdmiControlService hdmiControlService = this.mService;
        if (hdmiControlService.isSystemAudioActivated() == parseCommandParamSystemAudioStatus) {
            return -1;
        }
        hdmiControlService.setSystemAudioActivated(parseCommandParamSystemAudioStatus);
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleUserControlPressed(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        wakeUpIfActiveSource();
        return super.handleUserControlPressed(hdmiCecMessage);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public /* bridge */ /* synthetic */ void invokeStandbyCompletedCallback(
            HdmiCecLocalDevice.StandbyCompletedCallback standbyCompletedCallback) {
        super.invokeStandbyCompletedCallback(standbyCompletedCallback);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource
    public final void onActiveSourceLost() {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        hdmiControlService.pauseActiveMediaSessions();
        String stringValue =
                hdmiControlService
                        .getHdmiCecConfig()
                        .getStringValue("power_state_change_on_active_source_lost");
        stringValue.getClass();
        if (stringValue.equals("standby_now")) {
            Handler handler = this.mDelayedPopupOnActiveSourceLostHandler;
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(new AnonymousClass6(this, 0), 5000L);
        }
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
                    "HdmiCecLocalDevicePlayback#onAddressAllocated()");
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
        buildAndSendSetOsdName(0);
        if (hdmiControlService.audioSystem() == null) {
            hdmiControlService.sendCecCommand(
                    HdmiCecMessage.build(getDeviceInfo().getLogicalAddress(), 5, 125),
                    new AnonymousClass1());
        }
        assertRunOnServiceThread();
        assertRunOnServiceThread();
        hdmiControlService.mHdmiCecNetwork.clearDeviceList();
        if (hasAction(DeviceDiscoveryAction.class)) {
            Slog.i(
                    "HdmiCecLocalDevicePlayback",
                    "Device Discovery Action is in progress. Restarting.");
            removeAction(DeviceDiscoveryAction.class);
        }
        addAndStartAction(new DeviceDiscoveryAction(this, new AnonymousClass1()));
        startQueuedActions();
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void onHotplug(int i, boolean z) {
        assertRunOnServiceThread();
        this.mCecMessageCache.mCache.clear();
        Handler handler = this.mDelayedStandbyHandler;
        if (z) {
            handler.removeCallbacksAndMessages(null);
            return;
        }
        getWakeLock().release();
        HdmiCecNetwork hdmiCecNetwork = this.mService.mHdmiCecNetwork;
        hdmiCecNetwork.removeCecSwitches(i);
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < hdmiCecNetwork.mDeviceInfos.size(); i2++) {
            int keyAt = hdmiCecNetwork.mDeviceInfos.keyAt(i2);
            int physicalAddressToPortId =
                    hdmiCecNetwork.physicalAddressToPortId(
                            ((HdmiDeviceInfo) hdmiCecNetwork.mDeviceInfos.get(keyAt))
                                    .getPhysicalAddress());
            if (physicalAddressToPortId == i || physicalAddressToPortId == -1) {
                arrayList.add(Integer.valueOf(keyAt));
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            hdmiCecNetwork.removeDeviceInfo(((Integer) it.next()).intValue());
        }
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new AnonymousClass6(this, 2), 30000L);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void onInitializeCecComplete(int i) {
        if (i == 2
                && !this.mService
                        .getHdmiCecConfig()
                        .getStringValue("power_control_mode")
                        .equals("none")) {
            oneTouchPlay(new AnonymousClass4());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0098, code lost:

       if (r9.equals(android.net.INetd.IF_FLAG_BROADCAST) == false) goto L21;
    */
    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onStandby(
            boolean r8, int r9, com.android.server.hdmi.HdmiControlService.AnonymousClass27 r10) {
        /*
            r7 = this;
            r0 = 1
            r1 = 0
            r2 = -1
            r7.assertRunOnServiceThread()
            com.android.server.hdmi.HdmiControlService r3 = r7.mService
            boolean r4 = r3.isCecControlEnabled()
            if (r4 != 0) goto L12
            r7.invokeStandbyCompletedCallback(r10)
            return
        L12:
            boolean r4 = r7.isActiveSource()
            java.lang.String r5 = "HdmiCecLocalDevicePlayback#onStandby()"
            r6 = 65535(0xffff, float:9.1834E-41)
            r3.setActiveSource(r2, r6, r5)
            if (r4 != 0) goto L24
            r7.invokeStandbyCompletedCallback(r10)
            return
        L24:
            com.android.server.hdmi.HdmiCecLocalDevicePlayback$3 r4 = new com.android.server.hdmi.HdmiCecLocalDevicePlayback$3
            r4.<init>(r10)
            r10 = 157(0x9d, float:2.2E-43)
            if (r8 == 0) goto L47
            android.hardware.hdmi.HdmiDeviceInfo r7 = r7.getDeviceInfo()
            int r7 = r7.getLogicalAddress()
            com.android.server.hdmi.HdmiCecNetwork r8 = r3.mHdmiCecNetwork
            int r8 = r8.getPhysicalAddress()
            byte[] r8 = com.android.server.hdmi.HdmiCecMessageBuilder.physicalAddressToParam(r8)
            com.android.server.hdmi.HdmiCecMessage r7 = com.android.server.hdmi.HdmiCecMessage.build(r7, r1, r10, r8)
            r3.sendCecCommand(r7, r4)
            return
        L47:
            r8 = 15
            r5 = 54
            if (r9 == 0) goto L62
            if (r9 == r0) goto L51
            goto L104
        L51:
            android.hardware.hdmi.HdmiDeviceInfo r7 = r7.getDeviceInfo()
            int r7 = r7.getLogicalAddress()
            com.android.server.hdmi.HdmiCecMessage r7 = com.android.server.hdmi.HdmiCecMessage.build(r7, r8, r5)
            r3.sendCecCommand(r7, r4)
            goto L104
        L62:
            com.android.server.hdmi.HdmiCecConfig r9 = r3.getHdmiCecConfig()
            java.lang.String r6 = "power_control_mode"
            java.lang.String r9 = r9.getStringValue(r6)
            r9.getClass()
            int r6 = r9.hashCode()
            switch(r6) {
                case -1744153479: goto L9b;
                case -1618876223: goto L91;
                case 3387192: goto L85;
                case 110530246: goto L79;
                default: goto L77;
            }
        L77:
            r0 = r2
            goto La6
        L79:
            java.lang.String r0 = "to_tv"
            boolean r9 = r9.equals(r0)
            if (r9 != 0) goto L83
            goto L77
        L83:
            r0 = 3
            goto La6
        L85:
            java.lang.String r0 = "none"
            boolean r9 = r9.equals(r0)
            if (r9 != 0) goto L8f
            goto L77
        L8f:
            r0 = 2
            goto La6
        L91:
            java.lang.String r6 = "broadcast"
            boolean r9 = r9.equals(r6)
            if (r9 != 0) goto La6
            goto L77
        L9b:
            java.lang.String r0 = "to_tv_and_audio_system"
            boolean r9 = r9.equals(r0)
            if (r9 != 0) goto La5
            goto L77
        La5:
            r0 = r1
        La6:
            switch(r0) {
                case 0: goto Le4;
                case 1: goto Ld4;
                case 2: goto Lba;
                case 3: goto Laa;
                default: goto La9;
            }
        La9:
            goto L104
        Laa:
            android.hardware.hdmi.HdmiDeviceInfo r7 = r7.getDeviceInfo()
            int r7 = r7.getLogicalAddress()
            com.android.server.hdmi.HdmiCecMessage r7 = com.android.server.hdmi.HdmiCecMessage.build(r7, r1, r5)
            r3.sendCecCommand(r7, r4)
            goto L104
        Lba:
            android.hardware.hdmi.HdmiDeviceInfo r7 = r7.getDeviceInfo()
            int r7 = r7.getLogicalAddress()
            com.android.server.hdmi.HdmiCecNetwork r8 = r3.mHdmiCecNetwork
            int r8 = r8.getPhysicalAddress()
            byte[] r8 = com.android.server.hdmi.HdmiCecMessageBuilder.physicalAddressToParam(r8)
            com.android.server.hdmi.HdmiCecMessage r7 = com.android.server.hdmi.HdmiCecMessage.build(r7, r1, r10, r8)
            r3.sendCecCommand(r7, r4)
            goto L104
        Ld4:
            android.hardware.hdmi.HdmiDeviceInfo r7 = r7.getDeviceInfo()
            int r7 = r7.getLogicalAddress()
            com.android.server.hdmi.HdmiCecMessage r7 = com.android.server.hdmi.HdmiCecMessage.build(r7, r8, r5)
            r3.sendCecCommand(r7, r4)
            goto L104
        Le4:
            android.hardware.hdmi.HdmiDeviceInfo r8 = r7.getDeviceInfo()
            int r8 = r8.getLogicalAddress()
            com.android.server.hdmi.HdmiCecMessage r8 = com.android.server.hdmi.HdmiCecMessage.build(r8, r1, r5)
            r9 = 0
            r3.sendCecCommand(r8, r9)
            android.hardware.hdmi.HdmiDeviceInfo r7 = r7.getDeviceInfo()
            int r7 = r7.getLogicalAddress()
            r8 = 5
            com.android.server.hdmi.HdmiCecMessage r7 = com.android.server.hdmi.HdmiCecMessage.build(r7, r8, r5)
            r3.sendCecCommand(r7, r4)
        L104:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.hdmi.HdmiCecLocalDevicePlayback.onStandby(boolean, int,"
                    + " com.android.server.hdmi.HdmiControlService$27):void");
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void preprocessBufferedMessages(List list) {
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            int i = ((HdmiCecMessage) it.next()).mOpcode;
            if (i == 128 || i == 134 || i == 130) {
                removeAction(ActiveSourceAction.class);
                removeAction(OneTouchPlayAction.class);
                return;
            }
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDeviceSource,
              // com.android.server.hdmi.HdmiCecLocalDevice
    public void setActiveSource(int i, int i2, String str) {
        assertRunOnServiceThread();
        super.setActiveSource(i, i2, str);
        if (isActiveSource()) {
            getWakeLock().acquire();
        } else {
            getWakeLock().release();
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void setPreferredAddress(int i) {
        assertRunOnServiceThread();
        this.mService.writeStringSystemProperty(
                "persist.sys.hdmi.addr.playback", String.valueOf(i));
    }

    public void startHdmiCecActiveSourceLostActivity() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Context context = this.mService.getContext();
                Intent intent = new Intent();
                intent.setComponent(
                        ComponentName.unflattenFromString(
                                context.getResources()
                                        .getString(
                                                R.string.dynamic_mode_notification_channel_name)));
                intent.addFlags(268435456);
                context.startActivityAsUser(intent, context.getUser());
            } catch (ActivityNotFoundException unused) {
                Slog.e(
                        "HdmiCecLocalDevicePlayback",
                        "Unable to start HdmiCecActiveSourceLostActivity");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void startSetMenuLanguageActivity(Locale locale) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Context context = this.mService.getContext();
                Intent intent = new Intent();
                intent.putExtra("android.hardware.hdmi.extra.LOCALE", locale.toLanguageTag());
                intent.setComponent(
                        ComponentName.unflattenFromString(
                                context.getResources()
                                        .getString(R.string.dynamic_mode_notification_summary)));
                intent.addFlags(268435456);
                context.startActivityAsUser(intent, context.getUser());
            } catch (ActivityNotFoundException unused) {
                Slog.e(
                        "HdmiCecLocalDevicePlayback",
                        "unable to start HdmiCecSetMenuLanguageActivity");
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
