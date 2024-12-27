package android.graphics;

import android.os.Parcel;
import android.os.Process;
import android.util.ArrayMap;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class LeakyTypefaceStorage {
    private static final Object sLock = new Object();
    private static final ArrayList<Typeface> sStorage = new ArrayList<>();
    private static final ArrayMap<Typeface, Integer> sTypefaceMap = new ArrayMap<>();

    public static void writeTypefaceToParcel(Typeface typeface, Parcel parcel) {
        int id;
        parcel.writeInt(Process.myPid());
        synchronized (sLock) {
            Integer i = sTypefaceMap.get(typeface);
            if (i != null) {
                id = i.intValue();
            } else {
                id = sStorage.size();
                sStorage.add(typeface);
                sTypefaceMap.put(typeface, Integer.valueOf(id));
            }
            parcel.writeInt(id);
        }
    }

    public static Typeface readTypefaceFromParcel(Parcel parcel) {
        Typeface typeface;
        int pid = parcel.readInt();
        int typefaceId = parcel.readInt();
        if (pid != Process.myPid()) {
            return null;
        }
        synchronized (sLock) {
            typeface = sStorage.get(typefaceId);
        }
        return typeface;
    }
}
