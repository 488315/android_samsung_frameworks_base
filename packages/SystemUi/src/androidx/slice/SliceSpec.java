package androidx.slice;

import androidx.versionedparcelable.VersionedParcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
