package android.hardware.weaver.V1_0;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.os.HidlSupport;

import java.util.ArrayList;
import java.util.Objects;

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
