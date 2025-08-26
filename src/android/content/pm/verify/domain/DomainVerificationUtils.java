package android.content.pm.verify.domain;

import android.os.IBinder;
import android.os.Parcel;
import android.util.ArraySet;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class DomainVerificationUtils {
    private static final int STRINGS_TARGET_BYTE_SIZE = IBinder.getSuggestedMaxIpcSizeBytes() / 2;

    public static void writeHostMap(Parcel parcel, Map<String, ?> map) {
        boolean z;
        int iDataSize = parcel.dataSize();
        Iterator<String> it = map.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            iDataSize += estimatedByteSizeOf(it.next());
            if (iDataSize > STRINGS_TARGET_BYTE_SIZE) {
                z = true;
                break;
            }
        }
        parcel.writeBoolean(z);
        if (!z) {
            parcel.writeMap(map);
            return;
        }
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.writeMap(map);
            parcel.writeBlob(parcelObtain.marshall());
        } finally {
            parcelObtain.recycle();
        }
    }

    public static <T extends Map> T readHostMap(Parcel parcel, T t, ClassLoader classLoader) throws ClassNotFoundException, IOException {
        if (!parcel.readBoolean()) {
            parcel.readMap(t, classLoader);
            return t;
        }
        Parcel parcelObtain = Parcel.obtain();
        try {
            byte[] blob = parcel.readBlob();
            parcelObtain.unmarshall(blob, 0, blob.length);
            parcelObtain.setDataPosition(0);
            parcelObtain.readMap(t, classLoader);
            return t;
        } finally {
            parcelObtain.recycle();
        }
    }

    public static void writeHostSet(Parcel parcel, Set<String> set) {
        boolean z;
        int iDataSize = parcel.dataSize();
        Iterator<String> it = set.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            iDataSize += estimatedByteSizeOf(it.next());
            if (iDataSize > STRINGS_TARGET_BYTE_SIZE) {
                z = true;
                break;
            }
        }
        parcel.writeBoolean(z);
        if (!z) {
            writeSet(parcel, set);
            return;
        }
        Parcel parcelObtain = Parcel.obtain();
        try {
            writeSet(parcelObtain, set);
            parcel.writeBlob(parcelObtain.marshall());
        } finally {
            parcelObtain.recycle();
        }
    }

    public static Set<String> readHostSet(Parcel parcel) {
        if (!parcel.readBoolean()) {
            return readSet(parcel);
        }
        Parcel parcelObtain = Parcel.obtain();
        try {
            byte[] blob = parcel.readBlob();
            parcelObtain.unmarshall(blob, 0, blob.length);
            parcelObtain.setDataPosition(0);
            return readSet(parcelObtain);
        } finally {
            parcelObtain.recycle();
        }
    }

    private static void writeSet(Parcel parcel, Set<String> set) {
        if (set == null) {
            parcel.writeInt(-1);
            return;
        }
        parcel.writeInt(set.size());
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            parcel.writeString(it.next());
        }
    }

    private static Set<String> readSet(Parcel parcel) {
        int i = parcel.readInt();
        if (i == -1) {
            return Collections.EMPTY_SET;
        }
        ArraySet arraySet = new ArraySet(i);
        for (int i2 = 0; i2 < i; i2++) {
            arraySet.add(parcel.readString());
        }
        return arraySet;
    }

    public static int estimatedByteSizeOf(String str) {
        return (str.length() * 2) + 12;
    }
}
