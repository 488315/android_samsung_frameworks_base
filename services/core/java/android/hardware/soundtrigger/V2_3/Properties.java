package android.hardware.soundtrigger.V2_3;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.hardware.soundtrigger.V2_0.ISoundTriggerHw;
import android.os.HidlSupport;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class Properties {
    public int audioCapabilities;
    public ISoundTriggerHw.Properties base = new ISoundTriggerHw.Properties();
    public String supportedModelArch = new String();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != Properties.class) {
            return false;
        }
        Properties properties = (Properties) obj;
        return HidlSupport.deepEquals(this.base, properties.base) && HidlSupport.deepEquals(this.supportedModelArch, properties.supportedModelArch) && HidlSupport.deepEquals(Integer.valueOf(this.audioCapabilities), Integer.valueOf(properties.audioCapabilities));
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(this.supportedModelArch)), AudioConfig$$ExternalSyntheticOutline0.m(this.audioCapabilities));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .supportedModelArch = " + this.supportedModelArch + ", .audioCapabilities = " + AudioCapabilities.dumpBitfield(this.audioCapabilities) + "}";
    }
}
