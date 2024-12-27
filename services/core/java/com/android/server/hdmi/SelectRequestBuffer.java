package com.android.server.hdmi;

import android.hardware.hdmi.IHdmiControlCallback;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class SelectRequestBuffer {
    public static final AnonymousClass1 EMPTY_BUFFER = new AnonymousClass1();
    public PortSelectRequest mRequest;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.hdmi.SelectRequestBuffer$1, reason: invalid class name */
    public final class AnonymousClass1 extends SelectRequestBuffer {
        @Override // com.android.server.hdmi.SelectRequestBuffer
        public final void process() {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PortSelectRequest {
        public final /* synthetic */ int $r8$classId;
        public final IHdmiControlCallback mCallback;
        public final int mId;
        public final HdmiControlService mService;

        public PortSelectRequest(
                HdmiControlService hdmiControlService,
                int i,
                IHdmiControlCallback iHdmiControlCallback,
                int i2) {
            this.$r8$classId = i2;
            this.mService = hdmiControlService;
            this.mId = i;
            this.mCallback = iHdmiControlCallback;
        }

        public final boolean isLocalDeviceReady() {
            if (this.mService.tv() != null) {
                return true;
            }
            Slog.e("SelectRequestBuffer", "Local tv device not available");
            try {
                IHdmiControlCallback iHdmiControlCallback = this.mCallback;
                if (iHdmiControlCallback == null) {
                    return false;
                }
                iHdmiControlCallback.onComplete(2);
                return false;
            } catch (RemoteException e) {
                Slog.e("SelectRequestBuffer", "Invoking callback failed:" + e);
                return false;
            }
        }
    }

    public void process() {
        PortSelectRequest portSelectRequest = this.mRequest;
        if (portSelectRequest != null) {
            switch (portSelectRequest.$r8$classId) {
                case 0:
                    if (portSelectRequest.isLocalDeviceReady()) {
                        StringBuilder sb = new StringBuilder("calling delayed portSelect id:");
                        int i = portSelectRequest.mId;
                        GmsAlarmManager$$ExternalSyntheticOutline0.m(sb, i, "SelectRequestBuffer");
                        HdmiControlService hdmiControlService = portSelectRequest.mService;
                        HdmiCecLocalDeviceTv tv = hdmiControlService.tv();
                        if (tv == null) {
                            HdmiCecLocalDeviceAudioSystem audioSystem =
                                    hdmiControlService.audioSystem();
                            if (audioSystem != null) {
                                audioSystem.doManualPortSwitching(i, portSelectRequest.mCallback);
                                break;
                            }
                        } else {
                            tv.doManualPortSwitching(i, portSelectRequest.mCallback);
                            break;
                        }
                    }
                    break;
                default:
                    if (portSelectRequest.isLocalDeviceReady()) {
                        StringBuilder sb2 = new StringBuilder("calling delayed deviceSelect id:");
                        int i2 = portSelectRequest.mId;
                        GmsAlarmManager$$ExternalSyntheticOutline0.m(
                                sb2, i2, "SelectRequestBuffer");
                        portSelectRequest
                                .mService
                                .tv()
                                .deviceSelect(i2, portSelectRequest.mCallback);
                        break;
                    }
                    break;
            }
            this.mRequest = null;
        }
    }
}
