package androidx.slice;

import androidx.versionedparcelable.VersionedParcel;
import androidx.versionedparcelable.VersionedParcelable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SliceParcelizer {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v7, types: [java.lang.Object, java.lang.Object[]] */
    public static Slice read(VersionedParcel versionedParcel) {
        Slice slice = new Slice();
        VersionedParcelable versionedParcelable = slice.mSpec;
        if (versionedParcel.readField(1)) {
            versionedParcelable = versionedParcel.readVersionedParcelable();
        }
        slice.mSpec = (SliceSpec) versionedParcelable;
        slice.mItems = (SliceItem[]) versionedParcel.readArray(2, slice.mItems);
        slice.mHints = (String[]) versionedParcel.readArray(3, slice.mHints);
        slice.mUri = versionedParcel.readString(4, slice.mUri);
        for (int length = slice.mItems.length - 1; length >= 0; length--) {
            SliceItem[] sliceItemArr = slice.mItems;
            SliceItem sliceItem = sliceItemArr[length];
            if (sliceItem.mObj == null) {
                if (ArrayUtils.contains(sliceItemArr, sliceItem)) {
                    int length2 = sliceItemArr.length;
                    int i = 0;
                    while (true) {
                        if (i >= length2) {
                            break;
                        }
                        if (!Objects.equals(sliceItemArr[i], sliceItem)) {
                            i++;
                        } else if (length2 == 1) {
                            sliceItemArr = null;
                        } else {
                            ?? r3 = (Object[]) Array.newInstance((Class<?>) SliceItem.class, length2 - 1);
                            System.arraycopy(sliceItemArr, 0, r3, 0, i);
                            System.arraycopy(sliceItemArr, i + 1, r3, i, (length2 - i) - 1);
                            sliceItemArr = r3;
                        }
                    }
                }
                SliceItem[] sliceItemArr2 = sliceItemArr;
                slice.mItems = sliceItemArr2;
                if (sliceItemArr2 == null) {
                    slice.mItems = new SliceItem[0];
                }
            }
        }
        return slice;
    }

    public static void write(Slice slice, VersionedParcel versionedParcel) {
        versionedParcel.getClass();
        slice.getClass();
        SliceSpec sliceSpec = slice.mSpec;
        if (sliceSpec != null) {
            versionedParcel.setOutputField(1);
            versionedParcel.writeVersionedParcelable(sliceSpec);
        }
        if (!Arrays.equals(Slice.NO_ITEMS, slice.mItems)) {
            versionedParcel.writeArray(2, slice.mItems);
        }
        if (!Arrays.equals(Slice.NO_HINTS, slice.mHints)) {
            versionedParcel.writeArray(3, slice.mHints);
        }
        String str = slice.mUri;
        if (str != null) {
            versionedParcel.writeString(4, str);
        }
    }
}
