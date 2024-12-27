package android.hardware.tv.cec.V1_0;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class HdmiPortInfo {
    public boolean arcSupported;
    public boolean cecSupported;
    public short physicalAddress;
    public int portId;
    public int type;

    public static final ArrayList readVectorFromParcel(HwParcel hwParcel) {
        ArrayList arrayList = new ArrayList();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer =
                hwParcel.readEmbeddedBuffer(int32 * 12, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            HdmiPortInfo hdmiPortInfo = new HdmiPortInfo();
            hdmiPortInfo.type = 0;
            hdmiPortInfo.portId = 0;
            hdmiPortInfo.cecSupported = false;
            hdmiPortInfo.arcSupported = false;
            hdmiPortInfo.physicalAddress = (short) 0;
            long j = i * 12;
            hdmiPortInfo.type = readEmbeddedBuffer.getInt32(j);
            hdmiPortInfo.portId = readEmbeddedBuffer.getInt32(4 + j);
            hdmiPortInfo.cecSupported = readEmbeddedBuffer.getBool(j + 8);
            hdmiPortInfo.arcSupported = readEmbeddedBuffer.getBool(9 + j);
            hdmiPortInfo.physicalAddress = readEmbeddedBuffer.getInt16(j + 10);
            arrayList.add(hdmiPortInfo);
        }
        return arrayList;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != HdmiPortInfo.class) {
            return false;
        }
        HdmiPortInfo hdmiPortInfo = (HdmiPortInfo) obj;
        return this.type == hdmiPortInfo.type
                && this.portId == hdmiPortInfo.portId
                && this.cecSupported == hdmiPortInfo.cecSupported
                && this.arcSupported == hdmiPortInfo.arcSupported
                && this.physicalAddress == hdmiPortInfo.physicalAddress;
    }

    public final int hashCode() {
        return Objects.hash(
                AudioConfig$$ExternalSyntheticOutline0.m(this.type),
                AudioConfig$$ExternalSyntheticOutline0.m(this.portId),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.cecSupported),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.arcSupported),
                Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.physicalAddress))));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.type = ");
        int i = this.type;
        sb.append(
                i == 0
                        ? "INPUT"
                        : i == 1
                                ? "OUTPUT"
                                : AudioChannelMask$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("0x"), i));
        sb.append(", .portId = ");
        sb.append(this.portId);
        sb.append(", .cecSupported = ");
        sb.append(this.cecSupported);
        sb.append(", .arcSupported = ");
        sb.append(this.arcSupported);
        sb.append(", .physicalAddress = ");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.physicalAddress, sb, "}");
    }
}
