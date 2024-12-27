package android.hardware.weaver.V1_0;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.os.HidlSupport;

import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class WeaverReadResponse {
    public int timeout;
    public ArrayList value;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != WeaverReadResponse.class) {
            return false;
        }
        WeaverReadResponse weaverReadResponse = (WeaverReadResponse) obj;
        return this.timeout == weaverReadResponse.timeout
                && HidlSupport.deepEquals(this.value, weaverReadResponse.value);
    }

    public final int hashCode() {
        return Objects.hash(
                AudioConfig$$ExternalSyntheticOutline0.m(this.timeout),
                Integer.valueOf(HidlSupport.deepHashCode(this.value)));
    }

    public final String toString() {
        return "{.timeout = " + this.timeout + ", .value = " + this.value + "}";
    }
}
