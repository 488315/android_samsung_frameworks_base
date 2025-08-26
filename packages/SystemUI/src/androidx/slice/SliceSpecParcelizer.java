package androidx.slice;

import androidx.versionedparcelable.VersionedParcel;

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
