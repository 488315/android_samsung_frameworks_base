package androidx.slice;

import androidx.versionedparcelable.VersionedParcelable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SliceSpec implements VersionedParcelable {
    public int mRevision;
    public String mType;

    public SliceSpec() {
        this.mRevision = 1;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof SliceSpec)) {
            return false;
        }
        SliceSpec sliceSpec = (SliceSpec) obj;
        return this.mType.equals(sliceSpec.mType) && this.mRevision == sliceSpec.mRevision;
    }

    public final int hashCode() {
        return this.mType.hashCode() + this.mRevision;
    }

    public final String toString() {
        return String.format("SliceSpec{%s,%d}", this.mType, Integer.valueOf(this.mRevision));
    }

    public SliceSpec(String str, int i) {
        this.mType = str;
        this.mRevision = i;
    }
}
