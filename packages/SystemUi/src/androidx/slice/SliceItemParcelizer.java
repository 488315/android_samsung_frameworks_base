package androidx.slice;

import android.os.Parcelable;
import android.text.Html;
import androidx.core.util.Pair;
import androidx.slice.SliceItemHolder;
import androidx.slice.compat.SliceProviderCompat;
import androidx.versionedparcelable.VersionedParcel;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SliceItemParcelizer {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0092, code lost:
    
        if (r6.equals("bundle") == false) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static SliceItem read(VersionedParcel versionedParcel) {
        Object pair;
        SliceItem sliceItem = new SliceItem();
        char c = 1;
        sliceItem.mHints = (String[]) versionedParcel.readArray(1, sliceItem.mHints);
        sliceItem.mFormat = versionedParcel.readString(2, sliceItem.mFormat);
        sliceItem.mSubType = versionedParcel.readString(3, sliceItem.mSubType);
        SliceItemHolder sliceItemHolder = (SliceItemHolder) versionedParcel.readVersionedParcelable(sliceItem.mHolder, 4);
        sliceItem.mHolder = sliceItemHolder;
        if (sliceItemHolder != null) {
            String str = sliceItem.mFormat;
            SliceProviderCompat.C04942 c04942 = SliceItemHolder.sHandler;
            if (c04942 != null) {
                c04942.handle(sliceItemHolder);
            }
            str.getClass();
            switch (str.hashCode()) {
                case -1422950858:
                    if (str.equals("action")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1377881982:
                    break;
                case 104431:
                    if (str.equals("int")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 3327612:
                    if (str.equals("long")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 3556653:
                    if (str.equals("text")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 100313435:
                    if (str.equals("image")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 100358090:
                    if (str.equals("input")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 109526418:
                    if (str.equals("slice")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    Parcelable parcelable = sliceItemHolder.mParcelable;
                    if (parcelable != null || sliceItemHolder.mVersionedParcelable != null) {
                        if (parcelable == null) {
                            parcelable = null;
                        }
                        pair = new Pair(parcelable, (Slice) sliceItemHolder.mVersionedParcelable);
                        break;
                    } else {
                        pair = null;
                        break;
                    }
                case 1:
                    pair = sliceItemHolder.mBundle;
                    break;
                case 2:
                    pair = Integer.valueOf(sliceItemHolder.mInt);
                    break;
                case 3:
                    pair = Long.valueOf(sliceItemHolder.mLong);
                    break;
                case 4:
                    String str2 = sliceItemHolder.mStr;
                    if (str2 != null && str2.length() != 0) {
                        pair = Html.fromHtml(sliceItemHolder.mStr, 0);
                        break;
                    } else {
                        pair = "";
                        break;
                    }
                    break;
                case 5:
                case 7:
                    pair = sliceItemHolder.mVersionedParcelable;
                    break;
                case 6:
                    pair = sliceItemHolder.mParcelable;
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized format ".concat(str));
            }
            sliceItem.mObj = pair;
            SliceItemHolder sliceItemHolder2 = sliceItem.mHolder;
            SliceItemHolder.SliceItemPool sliceItemPool = sliceItemHolder2.mPool;
            if (sliceItemPool != null) {
                sliceItemHolder2.mParcelable = null;
                sliceItemHolder2.mVersionedParcelable = null;
                sliceItemHolder2.mInt = 0;
                sliceItemHolder2.mLong = 0L;
                sliceItemHolder2.mStr = null;
                sliceItemPool.mCached.add(sliceItemHolder2);
            }
        } else {
            sliceItem.mObj = null;
        }
        sliceItem.mHolder = null;
        return sliceItem;
    }

    public static void write(SliceItem sliceItem, VersionedParcel versionedParcel) {
        versionedParcel.getClass();
        sliceItem.getClass();
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
