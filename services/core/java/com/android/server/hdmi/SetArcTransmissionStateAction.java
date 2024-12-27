package com.android.server.hdmi;

import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SetArcTransmissionStateAction extends HdmiCecFeatureAction {
    public final int mAvrAddress;
    public final boolean mEnabled;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.hdmi.SetArcTransmissionStateAction$1, reason: invalid class name */
    public final class AnonymousClass1 implements HdmiControlService.SendMessageCallback {
        public /* synthetic */ AnonymousClass1() {}

        @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
        public void onSendCompleted(int i) {
            if (i != 1) {
                return;
            }
            SetArcTransmissionStateAction setArcTransmissionStateAction =
                    SetArcTransmissionStateAction.this;
            setArcTransmissionStateAction.disableArc();
            HdmiLogger.debug("Failed to send <Report Arc Initiated>.", new Object[0]);
            setArcTransmissionStateAction.finish(true);
        }
    }

    public SetArcTransmissionStateAction(HdmiCecLocalDevice hdmiCecLocalDevice, int i, boolean z) {
        super(hdmiCecLocalDevice);
        if (!HdmiUtils.verifyAddressType(getSourceAddress(), 0)
                || !HdmiUtils.verifyAddressType(i, 5)) {
            Slog.w("SetArcTransmissionStateAction", "Device type mismatch, stop the action.");
            finish(true);
        }
        this.mAvrAddress = i;
        this.mEnabled = z;
    }

    public final void disableArc() {
        Slog.i("SetArcTransmissionStateAction", "Disabling ARC");
        ((HdmiCecLocalDeviceTv) this.mSource).disableArc();
        sendCommand(
                HdmiCecMessage.build(
                        getSourceAddress(),
                        this.mAvrAddress,
                        FrameworkStatsLog
                                .DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_PROVISIONING_ERROR));
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        int i2 = this.mState;
        if (i2 == i && i2 == 1) {
            finish(true);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        if (this.mState != 1
                || hdmiCecMessage.mOpcode != 0
                || (hdmiCecMessage.mParams[0] & 255) != 193) {
            return false;
        }
        HdmiLogger.debug("Feature aborted for <Report Arc Initiated>", new Object[0]);
        disableArc();
        finish(true);
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        if (!this.mEnabled) {
            disableArc();
            finish(true);
        } else {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1();
            HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
            hdmiCecLocalDevice.addAndStartAction(
                    new RequestSadAction(hdmiCecLocalDevice, anonymousClass1));
        }
    }
}
