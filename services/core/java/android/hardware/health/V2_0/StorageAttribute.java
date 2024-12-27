package android.hardware.health.V2_0;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class StorageAttribute {
    public boolean isInternal = false;
    public boolean isBootDevice = false;
    public String name = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != StorageAttribute.class) {
            return false;
        }
        StorageAttribute storageAttribute = (StorageAttribute) obj;
        return this.isInternal == storageAttribute.isInternal
                && this.isBootDevice == storageAttribute.isBootDevice
                && HidlSupport.deepEquals(this.name, storageAttribute.name);
    }

    public final int hashCode() {
        return Objects.hash(
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.isInternal),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.isBootDevice),
                Integer.valueOf(HidlSupport.deepHashCode(this.name)));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.isInternal = hwBlob.getBool(j);
        this.isBootDevice = hwBlob.getBool(1 + j);
        long j2 = j + 8;
        this.name = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r13.getBytes().length + 1, hwBlob.handle(), j2, false);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.isInternal = ");
        sb.append(this.isInternal);
        sb.append(", .isBootDevice = ");
        sb.append(this.isBootDevice);
        sb.append(", .name = ");
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.name, "}");
    }
}
