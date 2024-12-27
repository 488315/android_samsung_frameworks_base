package com.android.systemui.statusbar.notification.logging;

import android.app.Person;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashSet;

public final class NotificationMemoryMeter {
    public static final NotificationMemoryMeter INSTANCE = new NotificationMemoryMeter();

    private NotificationMemoryMeter() {
    }

    public static int computeBitmapUse(Bitmap bitmap, HashSet hashSet) {
        int identityHashCode = System.identityHashCode(bitmap);
        if (hashSet.contains(Integer.valueOf(identityHashCode))) {
            return 0;
        }
        hashSet.add(Integer.valueOf(identityHashCode));
        return bitmap.getAllocationByteCount();
    }

    public static int computeBundleSize(Bundle bundle) {
        Parcel obtain = Parcel.obtain();
        try {
            bundle.writeToParcel(obtain, 0);
            return obtain.dataSize();
        } finally {
            obtain.recycle();
        }
    }

    public static int computeIconUse(Icon icon, HashSet hashSet) {
        Integer valueOf = icon != null ? Integer.valueOf(icon.getType()) : null;
        if (valueOf != null && valueOf.intValue() == 1) {
            return computeBitmapUse(icon.getBitmap(), hashSet);
        }
        if (valueOf != null && valueOf.intValue() == 5) {
            return computeBitmapUse(icon.getBitmap(), hashSet);
        }
        if (valueOf != null && valueOf.intValue() == 3) {
            int identityHashCode = System.identityHashCode(icon.getDataBytes());
            if (!hashSet.contains(Integer.valueOf(identityHashCode))) {
                hashSet.add(Integer.valueOf(identityHashCode));
                return icon.getDataLength();
            }
        }
        return 0;
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
