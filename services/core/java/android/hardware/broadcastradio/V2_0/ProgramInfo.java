package android.hardware.broadcastradio.V2_0;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ProgramInfo {
    public int infoFlags;
    public final ProgramSelector selector = new ProgramSelector();
    public final ProgramIdentifier logicallyTunedTo = new ProgramIdentifier();
    public final ProgramIdentifier physicallyTunedTo = new ProgramIdentifier();
    public final ArrayList relatedContent = new ArrayList();
    public int signalQuality = 0;
    public final ArrayList metadata = new ArrayList();
    public final ArrayList vendorInfo = new ArrayList();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != ProgramInfo.class) {
            return false;
        }
        ProgramInfo programInfo = (ProgramInfo) obj;
        return HidlSupport.deepEquals(this.selector, programInfo.selector)
                && HidlSupport.deepEquals(this.logicallyTunedTo, programInfo.logicallyTunedTo)
                && HidlSupport.deepEquals(this.physicallyTunedTo, programInfo.physicallyTunedTo)
                && HidlSupport.deepEquals(this.relatedContent, programInfo.relatedContent)
                && HidlSupport.deepEquals(
                        Integer.valueOf(this.infoFlags), Integer.valueOf(programInfo.infoFlags))
                && this.signalQuality == programInfo.signalQuality
                && HidlSupport.deepEquals(this.metadata, programInfo.metadata)
                && HidlSupport.deepEquals(this.vendorInfo, programInfo.vendorInfo);
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(this.selector)),
                Integer.valueOf(HidlSupport.deepHashCode(this.logicallyTunedTo)),
                Integer.valueOf(HidlSupport.deepHashCode(this.physicallyTunedTo)),
                Integer.valueOf(HidlSupport.deepHashCode(this.relatedContent)),
                AudioConfig$$ExternalSyntheticOutline0.m(this.infoFlags),
                AudioConfig$$ExternalSyntheticOutline0.m(this.signalQuality),
                Integer.valueOf(HidlSupport.deepHashCode(this.metadata)),
                Integer.valueOf(HidlSupport.deepHashCode(this.vendorInfo)));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.selector.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.logicallyTunedTo.readEmbeddedFromParcel(hwBlob, 32 + j);
        this.physicallyTunedTo.readEmbeddedFromParcel(hwBlob, 48 + j);
        int int32 = hwBlob.getInt32(72 + j);
        HwBlob readEmbeddedBuffer =
                hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j + 64, true);
        this.relatedContent.clear();
        int i = 0;
        for (int i2 = 0; i2 < int32; i2++) {
            ProgramIdentifier programIdentifier = new ProgramIdentifier();
            programIdentifier.readEmbeddedFromParcel(readEmbeddedBuffer, i2 * 16);
            this.relatedContent.add(programIdentifier);
        }
        this.infoFlags = hwBlob.getInt32(80 + j);
        this.signalQuality = hwBlob.getInt32(84 + j);
        int int322 = hwBlob.getInt32(96 + j);
        HwBlob readEmbeddedBuffer2 =
                hwParcel.readEmbeddedBuffer(int322 * 32, hwBlob.handle(), j + 88, true);
        this.metadata.clear();
        int i3 = 0;
        while (i3 < int322) {
            Metadata metadata = new Metadata();
            metadata.key = i;
            metadata.intValue = 0L;
            metadata.stringValue = new String();
            long j2 = i3 * 32;
            metadata.key = readEmbeddedBuffer2.getInt32(j2);
            metadata.intValue = readEmbeddedBuffer2.getInt64(8 + j2);
            long j3 = j2 + 16;
            metadata.stringValue = readEmbeddedBuffer2.getString(j3);
            hwParcel.readEmbeddedBuffer(
                    r1.getBytes().length + 1, readEmbeddedBuffer2.handle(), j3, false);
            this.metadata.add(metadata);
            i3++;
            i = 0;
        }
        int int323 = hwBlob.getInt32(112 + j);
        HwBlob readEmbeddedBuffer3 =
                hwParcel.readEmbeddedBuffer(int323 * 32, hwBlob.handle(), j + 104, true);
        this.vendorInfo.clear();
        for (int i4 = 0; i4 < int323; i4++) {
            VendorKeyValue vendorKeyValue = new VendorKeyValue();
            vendorKeyValue.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer3, i4 * 32);
            this.vendorInfo.add(vendorKeyValue);
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.selector = ");
        sb.append(this.selector);
        sb.append(", .logicallyTunedTo = ");
        sb.append(this.logicallyTunedTo);
        sb.append(", .physicallyTunedTo = ");
        sb.append(this.physicallyTunedTo);
        sb.append(", .relatedContent = ");
        sb.append(this.relatedContent);
        sb.append(", .infoFlags = ");
        int i = this.infoFlags;
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("LIVE");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("MUTED");
            i2 |= 2;
        }
        if ((i & 4) == 4) {
            arrayList.add("TRAFFIC_PROGRAM");
            i2 |= 4;
        }
        if ((i & 8) == 8) {
            arrayList.add("TRAFFIC_ANNOUNCEMENT");
            i2 |= 8;
        }
        if ((i & 16) == 16) {
            arrayList.add("TUNED");
            i2 |= 16;
        }
        if ((i & 32) == 32) {
            arrayList.add("STEREO");
            i2 |= 32;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        sb.append(String.join(" | ", arrayList));
        sb.append(", .signalQuality = ");
        sb.append(this.signalQuality);
        sb.append(", .metadata = ");
        sb.append(this.metadata);
        sb.append(", .vendorInfo = ");
        sb.append(this.vendorInfo);
        sb.append("}");
        return sb.toString();
    }
}
