package com.android.server.hdmi;

import android.media.AudioDescriptor;
import android.os.Handler;
import android.util.Slog;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class HdmiEarcLocalDeviceTx extends HdmiEarcLocalDevice {
    public static final String[] earcStatusNames = {
        "HDMI_EARC_STATUS_IDLE",
        "HDMI_EARC_STATUS_EARC_PENDING",
        "HDMI_EARC_STATUS_ARC_PENDING",
        "HDMI_EARC_STATUS_EARC_CONNECTED"
    };
    public Handler mReportCapsHandler;
    public ReportCapsRunnable mReportCapsRunnable;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ReportCapsRunnable implements Runnable {
        public ReportCapsRunnable() {}

        @Override // java.lang.Runnable
        public final void run() {
            synchronized (HdmiEarcLocalDeviceTx.this.mLock) {
                try {
                    HdmiEarcLocalDeviceTx hdmiEarcLocalDeviceTx = HdmiEarcLocalDeviceTx.this;
                    if (hdmiEarcLocalDeviceTx.mEarcStatus == 3) {
                        hdmiEarcLocalDeviceTx.mService.notifyEarcStatusToAudioService(
                                new ArrayList(), true);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public static List parseCapabilities(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        if (bArr.length < 4) {
            Slog.i(
                    "HdmiEarcLocalDeviceTx",
                    "Raw eARC capabilities array doesn´t contain any blocks.");
            return arrayList;
        }
        int i = bArr[2];
        if (bArr.length < i) {
            Slog.i(
                    "HdmiEarcLocalDeviceTx",
                    "Raw eARC capabilities array is shorter than the reported payload length.");
            return arrayList;
        }
        int i2 = 3;
        while (i2 < i) {
            int i3 = bArr[i2];
            int i4 = (i3 & 224) >> 5;
            int i5 = i3 & 31;
            if (i5 == 0) {
                break;
            }
            if (i4 == 1) {
                int i6 = i5 % 3;
                if (i6 != 0) {
                    NandswapManager$$ExternalSyntheticOutline0.m(
                            i6,
                            "Invalid length of SAD block: expected a factor of 3 but got ",
                            "HdmiEarcLocalDeviceTx");
                } else {
                    byte[] bArr2 = new byte[i5];
                    System.arraycopy(bArr, i2 + 1, bArr2, 0, i5);
                    int i7 = 0;
                    while (i7 < i5) {
                        int i8 = i7 + 3;
                        arrayList.add(new AudioDescriptor(1, 0, Arrays.copyOfRange(bArr2, i7, i8)));
                        i7 = i8;
                    }
                }
            } else if (i4 == 4) {
                int i9 = i5 + 1;
                byte[] bArr3 = new byte[i9];
                System.arraycopy(bArr, i2, bArr3, 0, i9);
                arrayList.add(new AudioDescriptor(2, 0, bArr3));
            } else if (i4 != 7) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i4, "This tagcode was not handled: ", "HdmiEarcLocalDeviceTx");
            } else if (bArr[i2 + 1] == 17) {
                int i10 = i5 + 1;
                byte[] bArr4 = new byte[i10];
                System.arraycopy(bArr, i2, bArr4, 0, i10);
                arrayList.add(new AudioDescriptor(3, 0, bArr4));
            }
            i2 += i5 + 1;
        }
        return arrayList;
    }
}
