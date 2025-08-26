package com.android.systemui.statusbar.notification.logging;

import android.app.Person;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashSet;

/* loaded from: classes3.dex */
public final class NotificationMemoryMeter {
    public static final NotificationMemoryMeter INSTANCE = new NotificationMemoryMeter();

    private NotificationMemoryMeter() {
    }

    public static int computeBitmapUse(Bitmap bitmap, HashSet hashSet) {
        int iIdentityHashCode = System.identityHashCode(bitmap);
        if (hashSet.contains(Integer.valueOf(iIdentityHashCode))) {
            return 0;
        }
        hashSet.add(Integer.valueOf(iIdentityHashCode));
        return bitmap.getAllocationByteCount();
    }

    public static int computeBundleSize(Bundle bundle) {
        Parcel parcelObtain = Parcel.obtain();
        try {
            bundle.writeToParcel(parcelObtain, 0);
            return parcelObtain.dataSize();
        } finally {
            parcelObtain.recycle();
        }
    }

    public static int computeIconUse(Icon icon, HashSet hashSet) {
        Integer numValueOf = icon != null ? Integer.valueOf(icon.getType()) : null;
        if (numValueOf != null && numValueOf.intValue() == 1) {
            return computeBitmapUse(icon.getBitmap(), hashSet);
        }
        if (numValueOf != null && numValueOf.intValue() == 5) {
            return computeBitmapUse(icon.getBitmap(), hashSet);
        }
        if (numValueOf == null || numValueOf.intValue() != 3) {
            return 0;
        }
        int iIdentityHashCode = System.identityHashCode(icon.getDataBytes());
        if (hashSet.contains(Integer.valueOf(iIdentityHashCode))) {
            return 0;
        }
        hashSet.add(Integer.valueOf(iIdentityHashCode));
        return icon.getDataLength();
    }

    public static int computeParcelableUse(Bundle bundle, String str, HashSet hashSet) {
        Parcelable parcelable = bundle != null ? bundle.getParcelable(str) : null;
        if (parcelable instanceof Bitmap) {
            return computeBitmapUse((Bitmap) parcelable, hashSet);
        }
        if (parcelable instanceof Icon) {
            return computeIconUse((Icon) parcelable, hashSet);
        }
        if (parcelable instanceof Person) {
            return computeIconUse(((Person) parcelable).getIcon(), hashSet);
        }
        return 0;
    }
}
