package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Parcelable;
import androidx.versionedparcelable.VersionedParcel;
import java.nio.charset.Charset;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class IconCompatParcelizer {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static IconCompat read(VersionedParcel versionedParcel) {
        IconCompat iconCompat = new IconCompat();
        iconCompat.mType = versionedParcel.readInt(iconCompat.mType, 1);
        byte[] bArr = iconCompat.mData;
        if (versionedParcel.readField(2)) {
            bArr = versionedParcel.readByteArray();
        }
        iconCompat.mData = bArr;
        iconCompat.mParcelable = versionedParcel.readParcelable(iconCompat.mParcelable, 3);
        iconCompat.mInt1 = versionedParcel.readInt(iconCompat.mInt1, 4);
        iconCompat.mInt2 = versionedParcel.readInt(iconCompat.mInt2, 5);
        iconCompat.mTintList = (ColorStateList) versionedParcel.readParcelable(iconCompat.mTintList, 6);
        iconCompat.mTintModeStr = versionedParcel.readString(7, iconCompat.mTintModeStr);
        iconCompat.mString1 = versionedParcel.readString(8, iconCompat.mString1);
        iconCompat.mTintMode = PorterDuff.Mode.valueOf(iconCompat.mTintModeStr);
        switch (iconCompat.mType) {
            case -1:
                Parcelable parcelable = iconCompat.mParcelable;
                if (parcelable == null) {
                    throw new IllegalArgumentException("Invalid icon");
                }
                iconCompat.mObj1 = parcelable;
                return iconCompat;
            case 0:
            default:
                return iconCompat;
            case 1:
            case 5:
                Parcelable parcelable2 = iconCompat.mParcelable;
                if (parcelable2 != null) {
                    iconCompat.mObj1 = parcelable2;
                } else {
                    byte[] bArr2 = iconCompat.mData;
                    iconCompat.mObj1 = bArr2;
                    iconCompat.mType = 3;
                    iconCompat.mInt1 = 0;
                    iconCompat.mInt2 = bArr2.length;
                }
                return iconCompat;
            case 2:
            case 4:
            case 6:
                String str = new String(iconCompat.mData, Charset.forName("UTF-16"));
                iconCompat.mObj1 = str;
                if (iconCompat.mType == 2 && iconCompat.mString1 == null) {
                    iconCompat.mString1 = str.split(":", -1)[0];
                }
                return iconCompat;
            case 3:
                iconCompat.mObj1 = iconCompat.mData;
                return iconCompat;
        }
    }

    public static void write(IconCompat iconCompat, VersionedParcel versionedParcel) {
        versionedParcel.getClass();
        iconCompat.mTintModeStr = iconCompat.mTintMode.name();
        switch (iconCompat.mType) {
            case -1:
                iconCompat.mParcelable = (Parcelable) iconCompat.mObj1;
                break;
            case 1:
            case 5:
                iconCompat.mParcelable = (Parcelable) iconCompat.mObj1;
                break;
            case 2:
                iconCompat.mData = ((String) iconCompat.mObj1).getBytes(Charset.forName("UTF-16"));
                break;
            case 3:
                iconCompat.mData = (byte[]) iconCompat.mObj1;
                break;
            case 4:
            case 6:
                iconCompat.mData = iconCompat.mObj1.toString().getBytes(Charset.forName("UTF-16"));
                break;
        }
        int i = iconCompat.mType;
        if (-1 != i) {
            versionedParcel.writeInt(i, 1);
        }
        byte[] bArr = iconCompat.mData;
        if (bArr != null) {
            versionedParcel.setOutputField(2);
            versionedParcel.writeByteArray(bArr);
        }
        Parcelable parcelable = iconCompat.mParcelable;
        if (parcelable != null) {
            versionedParcel.writeParcelable(parcelable, 3);
        }
        int i2 = iconCompat.mInt1;
        if (i2 != 0) {
            versionedParcel.writeInt(i2, 4);
        }
        int i3 = iconCompat.mInt2;
        if (i3 != 0) {
            versionedParcel.writeInt(i3, 5);
        }
        ColorStateList colorStateList = iconCompat.mTintList;
        if (colorStateList != null) {
            versionedParcel.writeParcelable(colorStateList, 6);
        }
        String str = iconCompat.mTintModeStr;
        if (str != null) {
            versionedParcel.writeString(7, str);
        }
        String str2 = iconCompat.mString1;
        if (str2 != null) {
            versionedParcel.writeString(8, str2);
        }
    }
}
