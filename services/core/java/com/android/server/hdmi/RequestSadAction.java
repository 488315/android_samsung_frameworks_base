package com.android.server.hdmi;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.hdmi.SetArcTransmissionStateAction.AnonymousClass1;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RequestSadAction extends HdmiCecFeatureAction {
    public final SetArcTransmissionStateAction.AnonymousClass1 mCallback;
    public final List mCecCodecsToQuery;
    public int mQueriedSadCount;
    public final List mSupportedSads;
    public final int mTargetAddress;
    public int mTimeoutRetry;

    public RequestSadAction(
            HdmiCecLocalDevice hdmiCecLocalDevice,
            SetArcTransmissionStateAction.AnonymousClass1 anonymousClass1) {
        super(hdmiCecLocalDevice);
        ArrayList arrayList = new ArrayList();
        this.mCecCodecsToQuery = arrayList;
        this.mSupportedSads = new ArrayList();
        this.mQueriedSadCount = 0;
        this.mTimeoutRetry = 0;
        this.mTargetAddress = 5;
        this.mCallback = anonymousClass1;
        HdmiCecConfig hdmiCecConfig = this.mSource.mService.getHdmiCecConfig();
        if (hdmiCecConfig.getIntValue("query_sad_lpcm") == 1) {
            arrayList.add(1);
        }
        if (hdmiCecConfig.getIntValue("query_sad_dd") == 1) {
            arrayList.add(2);
        }
        if (hdmiCecConfig.getIntValue("query_sad_mpeg1") == 1) {
            arrayList.add(3);
        }
        if (hdmiCecConfig.getIntValue("query_sad_mp3") == 1) {
            arrayList.add(4);
        }
        if (hdmiCecConfig.getIntValue("query_sad_mpeg2") == 1) {
            arrayList.add(5);
        }
        if (hdmiCecConfig.getIntValue("query_sad_aac") == 1) {
            arrayList.add(6);
        }
        if (hdmiCecConfig.getIntValue("query_sad_dts") == 1) {
            arrayList.add(7);
        }
        if (hdmiCecConfig.getIntValue("query_sad_atrac") == 1) {
            arrayList.add(8);
        }
        if (hdmiCecConfig.getIntValue("query_sad_onebitaudio") == 1) {
            arrayList.add(9);
        }
        if (hdmiCecConfig.getIntValue("query_sad_ddp") == 1) {
            arrayList.add(10);
        }
        if (hdmiCecConfig.getIntValue("query_sad_dtshd") == 1) {
            arrayList.add(11);
        }
        if (hdmiCecConfig.getIntValue("query_sad_truehd") == 1) {
            arrayList.add(12);
        }
        if (hdmiCecConfig.getIntValue("query_sad_dst") == 1) {
            arrayList.add(13);
        }
        if (hdmiCecConfig.getIntValue("query_sad_wmapro") == 1) {
            arrayList.add(14);
        }
        if (hdmiCecConfig.getIntValue("query_sad_max") == 1) {
            arrayList.add(15);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        if (this.mState == i && i == 1) {
            int i2 = this.mTimeoutRetry + 1;
            this.mTimeoutRetry = i2;
            if (i2 <= 1) {
                querySad();
            } else {
                wrapUpAndFinish$1();
            }
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        int i;
        if (this.mState == 1 && this.mTargetAddress == hdmiCecMessage.mSource) {
            byte[] bArr = hdmiCecMessage.mParams;
            int i2 = hdmiCecMessage.mOpcode;
            if (i2 == 163) {
                if (bArr != null && bArr.length != 0 && bArr.length % 3 == 0) {
                    for (int i3 = 0; i3 < bArr.length - 2; i3 += 3) {
                        byte b = bArr[i3];
                        if ((b & 128) == 0 && (i = (b & 120) >> 3) > 0 && i <= 15) {
                            ((ArrayList) this.mSupportedSads)
                                    .add(new byte[] {b, bArr[i3 + 1], bArr[i3 + 2]});
                        } else {
                            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("Dropped invalid codec "),
                                    bArr[i3],
                                    ".",
                                    "RequestSadAction");
                        }
                    }
                    this.mQueriedSadCount += 4;
                    this.mTimeoutRetry = 0;
                    querySad();
                }
                return true;
            }
            if (i2 == 0 && (bArr[0] & 255) == 164) {
                int i4 = bArr[1] & 255;
                if (i4 == 0) {
                    wrapUpAndFinish$1();
                    return true;
                }
                if (i4 == 3) {
                    this.mQueriedSadCount += 4;
                    this.mTimeoutRetry = 0;
                    querySad();
                    return true;
                }
            }
        }
        return false;
    }

    public final void querySad() {
        if (this.mQueriedSadCount >= ((ArrayList) this.mCecCodecsToQuery).size()) {
            wrapUpAndFinish$1();
            return;
        }
        List list = this.mCecCodecsToQuery;
        int[] array =
                ((ArrayList) list)
                                .subList(
                                        this.mQueriedSadCount,
                                        Math.min(
                                                ((ArrayList) list).size(),
                                                this.mQueriedSadCount + 4))
                                .stream()
                                .mapToInt(new RequestSadAction$$ExternalSyntheticLambda0())
                                .toArray();
        int sourceAddress = getSourceAddress();
        int min = Math.min(array.length, 4);
        byte[] bArr = new byte[min];
        for (int i = 0; i < min; i++) {
            bArr[i] =
                    (byte)
                            (array[i]
                                    & IDnsResolverUnsolicitedEventListener
                                            .DNS_HEALTH_RESULT_TIMEOUT);
        }
        sendCommand(
                HdmiCecMessage.build(
                        sourceAddress,
                        this.mTargetAddress,
                        FrameworkStatsLog
                                .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_ADMIN_RESTRICTED,
                        bArr));
        this.mState = 1;
        addTimer(1, 2000);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        querySad();
    }

    public final void wrapUpAndFinish$1() {
        List list = this.mSupportedSads;
        SetArcTransmissionStateAction.AnonymousClass1 anonymousClass1 = this.mCallback;
        anonymousClass1.getClass();
        Slog.i("SetArcTransmissionStateAction", "Enabling ARC");
        SetArcTransmissionStateAction setArcTransmissionStateAction =
                SetArcTransmissionStateAction.this;
        HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv =
                (HdmiCecLocalDeviceTv) setArcTransmissionStateAction.mSource;
        hdmiCecLocalDeviceTv.assertRunOnServiceThread();
        HdmiLogger.debug(
                "Set Arc Status[old:%b new:true]",
                Boolean.valueOf(hdmiCecLocalDeviceTv.mArcEstablished));
        hdmiCecLocalDeviceTv.enableAudioReturnChannel$1(true);
        hdmiCecLocalDeviceTv.notifyArcStatusToAudioService(list, true);
        hdmiCecLocalDeviceTv.mArcEstablished = true;
        setArcTransmissionStateAction.mState = 1;
        setArcTransmissionStateAction.addTimer(1, 2000);
        setArcTransmissionStateAction.sendCommand(
                HdmiCecMessage.build(
                        setArcTransmissionStateAction.getSourceAddress(),
                        setArcTransmissionStateAction.mAvrAddress,
                        193),
                setArcTransmissionStateAction.new AnonymousClass1());
        finish(true);
    }
}
