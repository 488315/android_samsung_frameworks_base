package com.android.server.hdmi;

import android.util.Slog;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.hdmi.HdmiCecFeatureAction;
import com.android.server.hdmi.HdmiControlService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class OneTouchRecordAction extends HdmiCecFeatureAction {
    public final byte[] mRecordSource;
    public final int mRecorderAddress;

    public OneTouchRecordAction(HdmiCecLocalDevice hdmiCecLocalDevice, int i, byte[] bArr) {
        super(hdmiCecLocalDevice);
        this.mRecorderAddress = i;
        this.mRecordSource = bArr;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        if (this.mState != i) {
            Slog.w("OneTouchRecordAction", ActivityManagerService$$ExternalSyntheticOutline0.m(this.mState, i, ", Actual:", "]", new StringBuilder("Timeout in invalid state:[Expected:")));
        } else {
            ((HdmiCecLocalDeviceTv) this.mSource).announceOneTouchRecordResult(this.mRecorderAddress, 49);
            finish(true);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        int i;
        int i2;
        if (this.mState != 1 || (i2 = this.mRecorderAddress) != (i = hdmiCecMessage.mSource) || hdmiCecMessage.mOpcode != 10 || i != i2) {
            return false;
        }
        byte b = hdmiCecMessage.mParams[0];
        ((HdmiCecLocalDeviceTv) this.mSource).announceOneTouchRecordResult(i2, b);
        StringBuilder sb = new StringBuilder("Got record status:");
        sb.append((int) b);
        sb.append(" from ");
        SystemServiceManager$$ExternalSyntheticOutline0.m(sb, hdmiCecMessage.mSource, "OneTouchRecordAction");
        if (b == 1 || b == 2 || b == 3 || b == 4) {
            this.mState = 2;
            ((HdmiCecFeatureAction.ActionTimerHandler) this.mActionTimer).clearTimerMessage();
        } else {
            finish(true);
        }
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        this.mService.sendCecCommand(HdmiCecMessage.build(getSourceAddress(), this.mRecorderAddress, 9, this.mRecordSource), new HdmiControlService.SendMessageCallback() { // from class: com.android.server.hdmi.OneTouchRecordAction.1
            @Override // com.android.server.hdmi.HdmiControlService.SendMessageCallback
            public final void onSendCompleted(int i) {
                if (i != 0) {
                    OneTouchRecordAction oneTouchRecordAction = OneTouchRecordAction.this;
                    ((HdmiCecLocalDeviceTv) oneTouchRecordAction.mSource).announceOneTouchRecordResult(oneTouchRecordAction.mRecorderAddress, 49);
                    oneTouchRecordAction.finish(true);
                }
            }
        });
        this.mState = 1;
        addTimer(1, 120000);
    }
}
