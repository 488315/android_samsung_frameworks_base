package android.hardware.broadcastradio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.ArrayList;
import java.util.Objects;

public final class ProgramSelector {
    public final ProgramIdentifier primaryId = new ProgramIdentifier();
    public final ArrayList secondaryIds = new ArrayList();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != ProgramSelector.class) {
            return false;
        }
        ProgramSelector programSelector = (ProgramSelector) obj;
        return HidlSupport.deepEquals(this.primaryId, programSelector.primaryId)
                && HidlSupport.deepEquals(this.secondaryIds, programSelector.secondaryIds);
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(this.primaryId)),
                Integer.valueOf(HidlSupport.deepHashCode(this.secondaryIds)));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.primaryId.readEmbeddedFromParcel(hwBlob, j);
        long j2 = j + 16;
        int int32 = hwBlob.getInt32(j + 24);
        HwBlob readEmbeddedBuffer =
                hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j2, true);
        this.secondaryIds.clear();
        for (int i = 0; i < int32; i++) {
            ProgramIdentifier programIdentifier = new ProgramIdentifier();
            programIdentifier.readEmbeddedFromParcel(readEmbeddedBuffer, i * 16);
            this.secondaryIds.add(programIdentifier);
        }
    }

    public final String toString() {
        return "{.primaryId = " + this.primaryId + ", .secondaryIds = " + this.secondaryIds + "}";
    }
}
