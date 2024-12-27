package com.android.server.hdmi;

import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;

import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class TimerRecordingAction extends HdmiCecFeatureAction {
    public final byte[] mRecordSource;
    public final int mRecorderAddress;
    public final int mSourceType;

    public TimerRecordingAction(
            HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv, int i, int i2, byte[] bArr) {
        super(hdmiCecLocalDeviceTv);
        this.mRecorderAddress = i;
        this.mSourceType = i2;
        this.mRecordSource = bArr;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        if (this.mState != i) {
            Slog.w(
                    "TimerRecordingAction",
                    ActivityManagerService$$ExternalSyntheticOutline0.m(
                            this.mState,
                            i,
                            ", Actual:",
                            "]",
                            new StringBuilder("Timeout in invalid state:[Expected:")));
        } else {
            ((HdmiCecLocalDeviceTv) this.mSource)
                    .announceTimerRecordingResult(this.mRecorderAddress, 1);
            finish(true);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        if (this.mState != 1) {
            return false;
        }
        int i = hdmiCecMessage.mSource;
        int i2 = this.mRecorderAddress;
        if (i != i2) {
            return false;
        }
        HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
        byte[] bArr = hdmiCecMessage.mParams;
        int i3 = hdmiCecMessage.mOpcode;
        if (i3 == 0) {
            int i4 = bArr[0] & 255;
            if (i4 != 52 && i4 != 151 && i4 != 162) {
                return false;
            }
            Slog.i(
                    "TimerRecordingAction",
                    "[Feature Abort] for " + i4 + " reason:" + (bArr[1] & 255));
            ((HdmiCecLocalDeviceTv) hdmiCecLocalDevice).announceTimerRecordingResult(i2, 1);
            finish(true);
            return true;
        }
        if (i3 != 53) {
            return false;
        }
        if (bArr.length == 1 || bArr.length == 3) {
            HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv = (HdmiCecLocalDeviceTv) hdmiCecLocalDevice;
            if (bArr.length > 4) {
                throw new IllegalArgumentException("Invalid data size:" + Arrays.toString(bArr));
            }
            int i5 = 0;
            for (int i6 = 0; i6 < bArr.length; i6++) {
                i5 |= (bArr[i6] & 255) << ((3 - i6) * 8);
            }
            hdmiCecLocalDeviceTv.announceTimerRecordingResult(i2, i5);
            Slog.i("TimerRecordingAction", "Received [Timer Status Data]:" + Arrays.toString(bArr));
        } else {
            Slog.w("TimerRecordingAction", "Invalid [Timer Status Data]:" + Arrays.toString(bArr));
        }
        finish(true);
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        HdmiCecMessage build;
        byte[] bArr = this.mRecordSource;
        int i = this.mRecorderAddress;
        int i2 = this.mSourceType;
        if (i2 == 1) {
            build =
                    HdmiCecMessage.build(
                            getSourceAddress(),
                            i,
                            FrameworkStatsLog
                                    .DEVICE_POLICY_EVENT__EVENT_ID__BIND_CROSS_PROFILE_SERVICE,
                            bArr);
        } else if (i2 == 2) {
            build = HdmiCecMessage.build(getSourceAddress(), i, 52, bArr);
        } else {
            if (i2 != 3) {
                ((HdmiCecLocalDeviceTv) this.mSource).announceTimerRecordingResult(i, 2);
                finish(true);
                return;
            }
            build = HdmiCecMessage.build(getSourceAddress(), i, 162, bArr);
        }
        this.mService.sendCecCommand(
                build,
                new HdmiControlService
                        .SendMessageCallback() { // from class:
                                                 // com.android.server.hdmi.TimerRecordingAction.1
                    @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
                    public final void onSendCompleted(int i3) {
                        TimerRecordingAction timerRecordingAction = TimerRecordingAction.this;
                        if (i3 != 0) {
                            ((HdmiCecLocalDeviceTv) timerRecordingAction.mSource)
                                    .announceTimerRecordingResult(
                                            timerRecordingAction.mRecorderAddress, 1);
                            timerRecordingAction.finish(true);
                        } else {
                            timerRecordingAction.mState = 1;
                            timerRecordingAction.addTimer(1, 120000);
                        }
                    }
                });
    }
}
