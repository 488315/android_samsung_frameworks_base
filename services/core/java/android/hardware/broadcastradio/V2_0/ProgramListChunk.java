package android.hardware.broadcastradio.V2_0;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.HidlSupport;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ProgramListChunk {
    public boolean complete;
    public ArrayList modified;
    public boolean purge;
    public ArrayList removed;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != ProgramListChunk.class) {
            return false;
        }
        ProgramListChunk programListChunk = (ProgramListChunk) obj;
        return this.purge == programListChunk.purge && this.complete == programListChunk.complete && HidlSupport.deepEquals(this.modified, programListChunk.modified) && HidlSupport.deepEquals(this.removed, programListChunk.removed);
    }

    public final int hashCode() {
        return Objects.hash(AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.purge), AudioOffloadInfo$$ExternalSyntheticOutline0.m(this.complete), Integer.valueOf(HidlSupport.deepHashCode(this.modified)), Integer.valueOf(HidlSupport.deepHashCode(this.removed)));
    }

    public final String toString() {
        return "{.purge = " + this.purge + ", .complete = " + this.complete + ", .modified = " + this.modified + ", .removed = " + this.removed + "}";
    }
}
