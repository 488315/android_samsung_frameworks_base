package android.content.pm.verify.domain;

import android.os.IBinder;
import android.os.Parcel;
import android.util.ArraySet;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class DomainVerificationUtils {
    private static final int STRINGS_TARGET_BYTE_SIZE = IBinder.getSuggestedMaxIpcSizeBytes() / 2;

    public static void writeHostMap(Parcel parcel, Map<String, ?> map) {
        boolean z;
        int dataSize = parcel.dataSize();
        Iterator<String> it = map.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            dataSize += estimatedByteSizeOf(it.next());
            if (dataSize > STRINGS_TARGET_BYTE_SIZE) {
                z = true;
                break;
            }
        }
        parcel.writeBoolean(z);
        if (!z) {
            parcel.writeMap(map);
            return;
        }
        Parcel obtain = Parcel.obtain();
        try {
            obtain.writeMap(map);
            parcel.writeBlob(obtain.marshall());
        } finally {
            obtain.recycle();
        }
    }

    public static <T extends Map> T readHostMap(Parcel parcel, T t, ClassLoader classLoader) {
        if (!parcel.readBoolean()) {
            parcel.readMap(t, classLoader);
            return t;
        }
        Parcel obtain = Parcel.obtain();
        try {
            byte[] readBlob = parcel.readBlob();
            obtain.unmarshall(readBlob, 0, readBlob.length);
            obtain.setDataPosition(0);
            obtain.readMap(t, classLoader);
            return t;
        } finally {
            obtain.recycle();
        }
    }

    public static void writeHostSet(Parcel parcel, Set<String> set) {
        boolean z;
        int dataSize = parcel.dataSize();
        Iterator<String> it = set.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            dataSize += estimatedByteSizeOf(it.next());
            if (dataSize > STRINGS_TARGET_BYTE_SIZE) {
                z = true;
                break;
            }
        }
        parcel.writeBoolean(z);
        if (!z) {
            writeSet(parcel, set);
            return;
        }
        Parcel obtain = Parcel.obtain();
        try {
            writeSet(obtain, set);
            parcel.writeBlob(obtain.marshall());
        } finally {
            obtain.recycle();
        }
    }

    public static Set<String> readHostSet(Parcel parcel) {
        if (!parcel.readBoolean()) {
            return readSet(parcel);
        }
        Parcel obtain = Parcel.obtain();
        try {
            byte[] readBlob = parcel.readBlob();
            obtain.unmarshall(readBlob, 0, readBlob.length);
            obtain.setDataPosition(0);
            return readSet(obtain);
        } finally {
            obtain.recycle();
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
        int readInt = parcel.readInt();
        if (readInt == -1) {
            return Collections.EMPTY_SET;
        }
        ArraySet arraySet = new ArraySet(readInt);
        for (int i = 0; i < readInt; i++) {
            arraySet.add(parcel.readString());
        }
        return arraySet;
    }

    public static int estimatedByteSizeOf(String str) {
        return (str.length() * 2) + 12;
    }
}
