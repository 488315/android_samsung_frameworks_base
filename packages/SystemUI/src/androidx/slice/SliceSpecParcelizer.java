package androidx.slice;

import androidx.versionedparcelable.VersionedParcel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SliceSpecParcelizer {
    public static SliceSpec read(VersionedParcel versionedParcel) {
        SliceSpec sliceSpec = new SliceSpec();
        sliceSpec.mType = versionedParcel.readString(1, sliceSpec.mType);
        sliceSpec.mRevision = versionedParcel.readInt(sliceSpec.mRevision, 2);
        return sliceSpec;
    }

    public static void write(SliceSpec sliceSpec, VersionedParcel versionedParcel) {
        versionedParcel.getClass();
        versionedParcel.writeString(1, sliceSpec.mType);
        int i = sliceSpec.mRevision;
        if (1 != i) {
            versionedParcel.writeInt(i, 2);
        }
    }
}
