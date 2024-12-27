package com.android.server.hdmi;

import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.IHdmiControlCallback;
import android.net.INetd;
import android.sysprop.HdmiProperties;
import android.util.Slog;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.hdmi.HdmiCecLocalDevice;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class HdmiCecLocalDeviceSource extends HdmiCecLocalDevice {
    public final boolean mIsSwitchDevice;
    public int mLocalActivePort;
    public boolean mRoutingControlFeatureEnabled;
    public int mRoutingPort;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.hdmi.HdmiCecLocalDeviceSource$1, reason: invalid class name */
    public final class AnonymousClass1 extends IHdmiControlCallback.Stub {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass1(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        public final void onComplete(int i) {
            switch (this.$r8$classId) {
                case 0:
                    if (i != -1) {
                        if (i != 0 && i != 2) {
                            if (i == 1 || i == 3) {
                                Slog.i("HdmiCecLocalDeviceSource", "TV power toggle: turning on TV");
                                ((HdmiCecLocalDeviceSource) this.this$0).oneTouchPlay(new AnonymousClass1(1, this));
                                break;
                            }
                        } else {
                            Slog.i("HdmiCecLocalDeviceSource", "TV power toggle: turning off TV");
                            ((HdmiCecLocalDeviceSource) this.this$0).sendStandby(0);
                            ((HdmiCecLocalDeviceSource) this.this$0).mService.standby();
                            break;
                        }
                    } else {
                        Slog.i("HdmiCecLocalDeviceSource", "TV power toggle: TV power status unknown");
                        ((HdmiCecLocalDeviceSource) this.this$0).sendUserControlPressedAndReleased(0, 107);
                        break;
                    }
                    break;
                default:
                    if (i != 0) {
                        DeviceIdleController$$ExternalSyntheticOutline0.m(i, "Failed to complete One Touch Play. result=", "HdmiCecLocalDeviceSource");
                        ((HdmiCecLocalDeviceSource) ((AnonymousClass1) this.this$0).this$0).sendUserControlPressedAndReleased(0, 107);
                        break;
                    }
                    break;
            }
        }
    }

    public HdmiCecLocalDeviceSource(HdmiControlService hdmiControlService, int i) {
        super(hdmiControlService, i);
        this.mIsSwitchDevice = ((Boolean) HdmiProperties.is_switch().orElse(Boolean.FALSE)).booleanValue();
        this.mRoutingPort = 0;
        this.mLocalActivePort = 0;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public void disableDevice(boolean z, HdmiCecLocalDevice.PendingActionClearedCallback pendingActionClearedCallback) {
        removeAction(OneTouchPlayAction.class);
        removeAction(DevicePowerStatusAction.class);
        super.disableDevice(z, pendingActionClearedCallback);
    }

    public final int getLocalActivePort() {
        int i;
        synchronized (this.mLock) {
            i = this.mLocalActivePort;
        }
        return i;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final List getRcFeatures() {
        ArrayList arrayList = new ArrayList();
        HdmiCecConfig hdmiCecConfig = this.mService.getHdmiCecConfig();
        if (hdmiCecConfig.getIntValue("rc_profile_source_handles_root_menu") == 1) {
            arrayList.add(4);
        }
        if (hdmiCecConfig.getIntValue("rc_profile_source_handles_setup_menu") == 1) {
            arrayList.add(3);
        }
        if (hdmiCecConfig.getIntValue("rc_profile_source_handles_contents_menu") == 1) {
            arrayList.add(2);
        }
        if (hdmiCecConfig.getIntValue("rc_profile_source_handles_top_menu") == 1) {
            arrayList.add(1);
        }
        if (hdmiCecConfig.getIntValue("rc_profile_source_handles_media_context_sensitive_menu") == 1) {
            arrayList.add(0);
        }
        return arrayList;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int getRcProfile() {
        return 1;
    }

    public final int getRoutingPort() {
        int i;
        synchronized (this.mLock) {
            i = this.mRoutingPort;
        }
        return i;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public void handleActiveSource(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int i = hdmiCecMessage.mSource;
        int twoBytesToInt = HdmiUtils.twoBytesToInt(hdmiCecMessage.mParams);
        HdmiCecLocalDevice.ActiveSource activeSource = new HdmiCecLocalDevice.ActiveSource(i, twoBytesToInt);
        if (!this.mService.getLocalActiveSource().equals(activeSource)) {
            setActiveSource(activeSource.logicalAddress, activeSource.physicalAddress, "HdmiCecLocalDeviceSource#handleActiveSource()");
        }
        if (isRoutingControlFeatureEnabled()) {
            switchInputOnReceivingNewActivePath(twoBytesToInt);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void handleRequestActiveSource(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        maySendActiveSource(hdmiCecMessage.mSource);
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public int handleRoutingChange(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int twoBytesToInt = HdmiUtils.twoBytesToInt(2, hdmiCecMessage.mParams);
        if (twoBytesToInt != this.mService.mHdmiCecNetwork.getPhysicalAddress() || !isActiveSource()) {
            assertRunOnServiceThread();
            setActiveSource(-1, twoBytesToInt, "HdmiCecLocalDeviceSource#handleRoutingChange()");
        }
        if (!isRoutingControlFeatureEnabled()) {
            return 4;
        }
        handleRoutingChangeAndInformation(twoBytesToInt, hdmiCecMessage);
        return -1;
    }

    public abstract void handleRoutingChangeAndInformation(int i, HdmiCecMessage hdmiCecMessage);

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public int handleRoutingInformation(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int twoBytesToInt = HdmiUtils.twoBytesToInt(hdmiCecMessage.mParams);
        if (twoBytesToInt != this.mService.mHdmiCecNetwork.getPhysicalAddress() || !isActiveSource()) {
            assertRunOnServiceThread();
            setActiveSource(-1, twoBytesToInt, "HdmiCecLocalDeviceSource#handleRoutingInformation()");
        }
        if (!isRoutingControlFeatureEnabled()) {
            return 4;
        }
        handleRoutingChangeAndInformation(twoBytesToInt, hdmiCecMessage);
        return -1;
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final int handleSetStreamPath(HdmiCecMessage hdmiCecMessage) {
        assertRunOnServiceThread();
        int twoBytesToInt = HdmiUtils.twoBytesToInt(hdmiCecMessage.mParams);
        HdmiControlService hdmiControlService = this.mService;
        if (twoBytesToInt == hdmiControlService.mHdmiCecNetwork.getPhysicalAddress() && hdmiControlService.isPlaybackDevice()) {
            hdmiControlService.setAndBroadcastActiveSource(twoBytesToInt, getDeviceInfo().getDeviceType(), hdmiCecMessage.mSource, "HdmiCecLocalDeviceSource#handleSetStreamPath()");
        } else if (twoBytesToInt != hdmiControlService.mHdmiCecNetwork.getPhysicalAddress() || !isActiveSource()) {
            assertRunOnServiceThread();
            setActiveSource(-1, twoBytesToInt, "HdmiCecLocalDeviceSource#handleSetStreamPath()");
        }
        switchInputOnReceivingNewActivePath(twoBytesToInt);
        return -1;
    }

    public final boolean isActiveSource() {
        if (getDeviceInfo() == null) {
            return false;
        }
        HdmiCecLocalDevice.ActiveSource localActiveSource = this.mService.getLocalActiveSource();
        return localActiveSource.logicalAddress == getDeviceInfo().getLogicalAddress() && localActiveSource.physicalAddress == getDeviceInfo().getPhysicalAddress();
    }

    public final boolean isRoutingControlFeatureEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mRoutingControlFeatureEnabled;
        }
        return z;
    }

    public final void maySendActiveSource(int i) {
        if (isActiveSource()) {
            addAndStartAction(new ActiveSourceAction(this, i));
        }
    }

    public void onActiveSourceLost() {
    }

    public final void oneTouchPlay(IHdmiControlCallback iHdmiControlCallback) {
        OneTouchPlayAction oneTouchPlayAction;
        boolean z;
        assertRunOnServiceThread();
        List actions = getActions(OneTouchPlayAction.class);
        if (!actions.isEmpty()) {
            Slog.i("HdmiCecLocalDeviceSource", "oneTouchPlay already in progress");
            ((ArrayList) ((OneTouchPlayAction) actions.get(0)).mCallbacks).add(iHdmiControlCallback);
            return;
        }
        if (iHdmiControlCallback == null) {
            Slog.e("OneTouchPlayAction", "Wrong arguments");
            oneTouchPlayAction = null;
        } else {
            if (getDeviceInfo().getCecVersion() >= 6) {
                HdmiDeviceInfo cecDeviceInfo = this.mService.mHdmiCecNetwork.getCecDeviceInfo(0);
                if ((cecDeviceInfo != null ? cecDeviceInfo.getCecVersion() : 5) >= 6) {
                    z = true;
                    oneTouchPlayAction = new OneTouchPlayAction(this, 0, iHdmiControlCallback, z);
                }
            }
            z = false;
            oneTouchPlayAction = new OneTouchPlayAction(this, 0, iHdmiControlCallback, z);
        }
        if (oneTouchPlayAction != null) {
            addAndStartAction(oneTouchPlayAction);
        } else {
            Slog.w("HdmiCecLocalDeviceSource", "Cannot initiate oneTouchPlay");
            invokeCallback(5, iHdmiControlCallback);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public final void sendStandby(int i) {
        assertRunOnServiceThread();
        HdmiControlService hdmiControlService = this.mService;
        String stringValue = hdmiControlService.getHdmiCecConfig().getStringValue("power_control_mode");
        if (stringValue.equals(INetd.IF_FLAG_BROADCAST)) {
            hdmiControlService.sendCecCommand(HdmiCecMessage.build(getDeviceInfo().getLogicalAddress(), 15, 54), null);
            return;
        }
        hdmiControlService.sendCecCommand(HdmiCecMessage.build(getDeviceInfo().getLogicalAddress(), 0, 54), null);
        if (stringValue.equals("to_tv_and_audio_system")) {
            hdmiControlService.sendCecCommand(HdmiCecMessage.build(getDeviceInfo().getLogicalAddress(), 5, 54), null);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecLocalDevice
    public void setActiveSource(int i, int i2, String str) {
        boolean isActiveSource = isActiveSource();
        super.setActiveSource(i, i2, str);
        if (!isActiveSource || isActiveSource()) {
            return;
        }
        onActiveSourceLost();
    }

    public final void setLocalActivePort(int i) {
        synchronized (this.mLock) {
            this.mLocalActivePort = i;
        }
    }

    public void setRoutingPort(int i) {
        synchronized (this.mLock) {
            this.mRoutingPort = i;
        }
    }

    public void switchInputOnReceivingNewActivePath(int i) {
    }

    public final void wakeUpIfActiveSource() {
        if (isActiveSource()) {
            this.mService.wakeUp();
        }
    }
}
