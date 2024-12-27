package android.hardware.health.V2_0;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.HidlSupport;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class StorageInfo {
    public StorageAttribute attr;
    public short eol;
    public short lifetimeA;
    public short lifetimeB;
    public String version;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != StorageInfo.class) {
            return false;
        }
        StorageInfo storageInfo = (StorageInfo) obj;
        return HidlSupport.deepEquals(this.attr, storageInfo.attr) && this.eol == storageInfo.eol && this.lifetimeA == storageInfo.lifetimeA && this.lifetimeB == storageInfo.lifetimeB && HidlSupport.deepEquals(this.version, storageInfo.version);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.attr)), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.eol))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.lifetimeA))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.lifetimeB))), Integer.valueOf(HidlSupport.deepHashCode(this.version)));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.attr = ");
        sb.append(this.attr);
        sb.append(", .eol = ");
        sb.append((int) this.eol);
        sb.append(", .lifetimeA = ");
        sb.append((int) this.lifetimeA);
        sb.append(", .lifetimeB = ");
        sb.append((int) this.lifetimeB);
        sb.append(", .version = ");
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.version, "}");
    }
}
