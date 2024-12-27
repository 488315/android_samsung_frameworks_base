package android.hardware.health.V2_1;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HealthInfo {
    public android.hardware.health.V2_0.HealthInfo legacy = new android.hardware.health.V2_0.HealthInfo();
    public int batteryCapacityLevel = 0;
    public long batteryChargeTimeToFullNowSeconds = 0;
    public int batteryFullChargeDesignCapacityUah = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != HealthInfo.class) {
            return false;
        }
        HealthInfo healthInfo = (HealthInfo) obj;
        return HidlSupport.deepEquals(this.legacy, healthInfo.legacy) && this.batteryCapacityLevel == healthInfo.batteryCapacityLevel && this.batteryChargeTimeToFullNowSeconds == healthInfo.batteryChargeTimeToFullNowSeconds && this.batteryFullChargeDesignCapacityUah == healthInfo.batteryFullChargeDesignCapacityUah;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.legacy)), AudioConfig$$ExternalSyntheticOutline0.m(this.batteryCapacityLevel), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.batteryChargeTimeToFullNowSeconds))), AudioConfig$$ExternalSyntheticOutline0.m(this.batteryFullChargeDesignCapacityUah));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob) {
        this.legacy.readEmbeddedFromParcel(hwParcel, hwBlob);
        this.batteryCapacityLevel = hwBlob.getInt32(112L);
        this.batteryChargeTimeToFullNowSeconds = hwBlob.getInt64(120L);
        this.batteryFullChargeDesignCapacityUah = hwBlob.getInt32(128L);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.legacy = ");
        sb.append(this.legacy);
        sb.append(", .batteryCapacityLevel = ");
        int i = this.batteryCapacityLevel;
        sb.append(i == -1 ? "UNSUPPORTED" : i == 0 ? "UNKNOWN" : i == 1 ? "CRITICAL" : i == 2 ? "LOW" : i == 3 ? "NORMAL" : i == 4 ? "HIGH" : i == 5 ? "FULL" : AudioChannelMask$$ExternalSyntheticOutline0.m(new StringBuilder("0x"), i));
        sb.append(", .batteryChargeTimeToFullNowSeconds = ");
        sb.append(this.batteryChargeTimeToFullNowSeconds);
        sb.append(", .batteryFullChargeDesignCapacityUah = ");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.batteryFullChargeDesignCapacityUah, sb, "}");
    }
}
