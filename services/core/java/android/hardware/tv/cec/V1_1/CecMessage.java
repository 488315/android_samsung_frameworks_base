package android.hardware.tv.cec.V1_1;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.os.HidlSupport;

import java.util.ArrayList;
import java.util.Objects;

public final class CecMessage {
    public ArrayList body;
    public int destination;
    public int initiator;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CecMessage.class) {
            return false;
        }
        CecMessage cecMessage = (CecMessage) obj;
        return this.initiator == cecMessage.initiator
                && this.destination == cecMessage.destination
                && HidlSupport.deepEquals(this.body, cecMessage.body);
    }

    public final int hashCode() {
        return Objects.hash(
                AudioConfig$$ExternalSyntheticOutline0.m(this.initiator),
                AudioConfig$$ExternalSyntheticOutline0.m(this.destination),
                Integer.valueOf(HidlSupport.deepHashCode(this.body)));
    }

    public final String toString() {
        return "{.initiator = "
                + CecLogicalAddress.toString(this.initiator)
                + ", .destination = "
                + CecLogicalAddress.toString(this.destination)
                + ", .body = "
                + this.body
                + "}";
    }
}
