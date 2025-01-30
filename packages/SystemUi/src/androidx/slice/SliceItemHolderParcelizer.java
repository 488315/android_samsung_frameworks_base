package androidx.slice;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.slice.SliceItemHolder;
import androidx.versionedparcelable.VersionedParcel;
import androidx.versionedparcelable.VersionedParcelable;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SliceItemHolderParcelizer {
    private static SliceItemHolder.SliceItemPool sBuilder = new SliceItemHolder.SliceItemPool();

    public static SliceItemHolder read(VersionedParcel versionedParcel) {
        SliceItemHolder.SliceItemPool sliceItemPool = sBuilder;
        ArrayList arrayList = sliceItemPool.mCached;
        SliceItemHolder sliceItemHolder = arrayList.size() > 0 ? (SliceItemHolder) arrayList.remove(arrayList.size() - 1) : new SliceItemHolder(sliceItemPool);
        sliceItemHolder.mVersionedParcelable = versionedParcel.readVersionedParcelable(sliceItemHolder.mVersionedParcelable, 1);
        sliceItemHolder.mParcelable = versionedParcel.readParcelable(sliceItemHolder.mParcelable, 2);
        sliceItemHolder.mStr = versionedParcel.readString(3, sliceItemHolder.mStr);
        sliceItemHolder.mInt = versionedParcel.readInt(sliceItemHolder.mInt, 4);
        long j = sliceItemHolder.mLong;
        if (versionedParcel.readField(5)) {
            j = versionedParcel.readLong();
        }
        sliceItemHolder.mLong = j;
        Bundle bundle = sliceItemHolder.mBundle;
        if (versionedParcel.readField(6)) {
            bundle = versionedParcel.readBundle();
        }
        sliceItemHolder.mBundle = bundle;
        return sliceItemHolder;
    }

    public static void write(SliceItemHolder sliceItemHolder, VersionedParcel versionedParcel) {
        versionedParcel.getClass();
        VersionedParcelable versionedParcelable = sliceItemHolder.mVersionedParcelable;
        if (versionedParcelable != null) {
            versionedParcel.setOutputField(1);
            versionedParcel.writeVersionedParcelable(versionedParcelable);
        }
        Parcelable parcelable = sliceItemHolder.mParcelable;
        if (parcelable != null) {
            versionedParcel.writeParcelable(parcelable, 2);
        }
        String str = sliceItemHolder.mStr;
        if (str != null) {
            versionedParcel.writeString(3, str);
        }
        int i = sliceItemHolder.mInt;
        if (i != 0) {
            versionedParcel.writeInt(i, 4);
        }
        long j = sliceItemHolder.mLong;
        if (0 != j) {
            versionedParcel.setOutputField(5);
            versionedParcel.writeLong(j);
        }
        Bundle bundle = sliceItemHolder.mBundle;
        if (bundle != null) {
            versionedParcel.setOutputField(6);
            versionedParcel.writeBundle(bundle);
        }
    }
}
