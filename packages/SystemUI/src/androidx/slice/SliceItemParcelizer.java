package androidx.slice;

import androidx.versionedparcelable.VersionedParcel;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SliceItemParcelizer {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0078, code lost:
    
        if (r7.equals("text") == false) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static androidx.slice.SliceItem read(androidx.versionedparcelable.VersionedParcel r10) {
        /*
            Method dump skipped, instructions count: 334
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.slice.SliceItemParcelizer.read(androidx.versionedparcelable.VersionedParcel):androidx.slice.SliceItem");
    }

    public static void write(SliceItem sliceItem, VersionedParcel versionedParcel) {
        versionedParcel.getClass();
        sliceItem.mHolder = new SliceItemHolder(sliceItem.mFormat, sliceItem.mObj, false);
        if (!Arrays.equals(Slice.NO_HINTS, sliceItem.mHints)) {
            versionedParcel.writeArray(1, sliceItem.mHints);
        }
        if (!"text".equals(sliceItem.mFormat)) {
            versionedParcel.writeString(2, sliceItem.mFormat);
        }
        String str = sliceItem.mSubType;
        if (str != null) {
            versionedParcel.writeString(3, str);
        }
        SliceItemHolder sliceItemHolder = sliceItem.mHolder;
        versionedParcel.setOutputField(4);
        versionedParcel.writeVersionedParcelable(sliceItemHolder);
    }
}
