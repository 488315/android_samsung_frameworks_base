package com.android.server.hdmi;

import android.hardware.hdmi.IHdmiControlCallback;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class OneTouchPlayAction extends HdmiCecFeatureAction {
    static final int STATE_CHECK_STANDBY_PROCESS_STARTED = 2;
    static final int STATE_WAITING_FOR_REPORT_POWER_STATUS = 1;
    public final boolean mIsCec20;
    public int mPowerStatusCounter;
    public HdmiCecLocalDeviceSource mSource;
    public final int mTargetAddress;

    public OneTouchPlayAction(
            HdmiCecLocalDevice hdmiCecLocalDevice,
            int i,
            IHdmiControlCallback iHdmiControlCallback,
            boolean z) {
        super(hdmiCecLocalDevice, iHdmiControlCallback);
        this.mPowerStatusCounter = 0;
        this.mTargetAddress = i;
        this.mIsCec20 = z;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        if (this.mState != i) {
            return;
        }
        if (i != 1) {
            if (i != 2) {
                return;
            }
            Slog.d("OneTouchPlayAction", "Action was not removed, start the action.");
            startAction();
            return;
        }
        int i2 = this.mPowerStatusCounter;
        this.mPowerStatusCounter = i2 + 1;
        if (i2 >= 10) {
            finishWithCallback(1);
        } else {
            sendCommand(HdmiCecMessage.build(getSourceAddress(), this.mTargetAddress, 143));
            addTimer(this.mState, 2000);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        if (this.mState == 1) {
            int i = hdmiCecMessage.mSource;
            int i2 = this.mTargetAddress;
            if (i2 == i && hdmiCecMessage.mOpcode == 144) {
                if (hdmiCecMessage.mParams[0] == 0) {
                    this.mSource.maySendActiveSource(i2);
                    finishWithCallback(0);
                }
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        HdmiCecLocalDeviceSource hdmiCecLocalDeviceSource =
                (HdmiCecLocalDeviceSource) super.mSource;
        this.mSource = hdmiCecLocalDeviceSource;
        if (hdmiCecLocalDeviceSource.mService.mPowerManager.mPowerManager.isInteractive()) {
            startAction();
            return;
        }
        Slog.d(
                "OneTouchPlayAction",
                "PowerManager is not interactive. Delay the action to check if standby started!");
        this.mState = 2;
        addTimer(2, 2000);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00ba  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startAction() {
        /*
            r12 = this;
            java.lang.String r0 = "OneTouchPlayAction"
            java.lang.String r1 = "Start action."
            android.util.Slog.i(r0, r1)
            int r0 = r12.getSourceAddress()
            r1 = 13
            int r2 = r12.mTargetAddress
            com.android.server.hdmi.HdmiCecMessage r0 = com.android.server.hdmi.HdmiCecMessage.build(r0, r2, r1)
            com.android.server.hdmi.HdmiControlService r1 = r12.mService
            r3 = 0
            r1.sendCecCommand(r0, r3)
            r0 = -1
            boolean r4 = r12.mIsCec20
            r5 = 0
            r6 = 1
            if (r4 == 0) goto L36
            com.android.server.hdmi.HdmiCecLocalDeviceSource r7 = r12.mSource
            com.android.server.hdmi.HdmiControlService r7 = r7.mService
            com.android.server.hdmi.HdmiCecNetwork r7 = r7.mHdmiCecNetwork
            android.hardware.hdmi.HdmiDeviceInfo r7 = r7.getCecDeviceInfo(r2)
            if (r7 == 0) goto L31
            int r7 = r7.getDevicePowerStatus()
            goto L32
        L31:
            r7 = r0
        L32:
            if (r7 != 0) goto L36
            r7 = r6
            goto L37
        L36:
            r7 = r5
        L37:
            com.android.server.hdmi.HdmiCecLocalDeviceSource r8 = r12.mSource
            com.android.server.hdmi.HdmiControlService r8 = r8.mService
            com.android.server.hdmi.HdmiCecLocalDevice r9 = r12.mSource
            android.hardware.hdmi.HdmiDeviceInfo r10 = r9.getDeviceInfo()
            int r10 = r10.getPhysicalAddress()
            java.lang.String r11 = "OneTouchPlayAction#broadcastActiveSource()"
            r8.setAndBroadcastActiveSourceFromOneDeviceType(r2, r10, r11)
            com.android.server.hdmi.HdmiCecLocalDeviceSource r8 = r12.mSource
            com.android.server.hdmi.HdmiControlService r8 = r8.mService
            com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem r8 = r8.audioSystem()
            if (r8 == 0) goto L5e
            com.android.server.hdmi.HdmiCecLocalDeviceSource r8 = r12.mSource
            com.android.server.hdmi.HdmiControlService r8 = r8.mService
            com.android.server.hdmi.HdmiCecLocalDeviceAudioSystem r8 = r8.audioSystem()
            r12.mSource = r8
        L5e:
            com.android.server.hdmi.HdmiCecLocalDeviceSource r8 = r12.mSource
            r8.setRoutingPort(r5)
            com.android.server.hdmi.HdmiCecLocalDeviceSource r8 = r12.mSource
            r8.setLocalActivePort(r5)
            com.android.server.hdmi.HdmiCecLocalDeviceSource r8 = r12.mSource
            com.android.server.hdmi.HdmiControlService r8 = r8.mService
            boolean r10 = r8.isAudioSystemDevice()
            if (r10 == 0) goto L73
            goto Laa
        L73:
            com.android.server.hdmi.HdmiCecConfig r8 = r8.getHdmiCecConfig()
            java.lang.String r10 = "power_control_mode"
            java.lang.String r8 = r8.getStringValue(r10)
            java.lang.String r10 = "to_tv_and_audio_system"
            boolean r10 = r8.equals(r10)
            if (r10 != 0) goto L90
            java.lang.String r10 = "broadcast"
            boolean r8 = r8.equals(r10)
            if (r8 == 0) goto Laa
        L90:
            int r8 = r12.getSourceAddress()
            android.hardware.hdmi.HdmiDeviceInfo r9 = r9.getDeviceInfo()
            int r9 = r9.getPhysicalAddress()
            byte[] r9 = com.android.server.hdmi.HdmiCecMessageBuilder.physicalAddressToParam(r9)
            r10 = 5
            r11 = 112(0x70, float:1.57E-43)
            com.android.server.hdmi.HdmiCecMessage r8 = com.android.server.hdmi.HdmiCecMessage.build(r8, r10, r11, r9)
            r1.sendCecCommand(r8, r3)
        Laa:
            r1 = 143(0x8f, float:2.0E-43)
            if (r4 != 0) goto Lba
            int r0 = r12.getSourceAddress()
            com.android.server.hdmi.HdmiCecMessage r0 = com.android.server.hdmi.HdmiCecMessage.build(r0, r2, r1)
            r12.sendCommand(r0)
            goto Le7
        Lba:
            com.android.server.hdmi.HdmiCecLocalDeviceSource r3 = r12.mSource
            com.android.server.hdmi.HdmiControlService r3 = r3.mService
            com.android.server.hdmi.HdmiCecNetwork r3 = r3.mHdmiCecNetwork
            android.hardware.hdmi.HdmiDeviceInfo r3 = r3.getCecDeviceInfo(r2)
            if (r3 == 0) goto Lcb
            int r3 = r3.getDevicePowerStatus()
            goto Lcc
        Lcb:
            r3 = r0
        Lcc:
            if (r3 != r0) goto Lda
            int r0 = r12.getSourceAddress()
            com.android.server.hdmi.HdmiCecMessage r0 = com.android.server.hdmi.HdmiCecMessage.build(r0, r2, r1)
            r12.sendCommand(r0)
            goto Le7
        Lda:
            if (r3 != 0) goto Le7
            if (r7 != 0) goto Le3
            com.android.server.hdmi.HdmiCecLocalDeviceSource r0 = r12.mSource
            r0.maySendActiveSource(r2)
        Le3:
            r12.finishWithCallback(r5)
            return
        Le7:
            r12.mState = r6
            r0 = 2000(0x7d0, float:2.803E-42)
            r12.addTimer(r6, r0)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.hdmi.OneTouchPlayAction.startAction():void");
    }
}
