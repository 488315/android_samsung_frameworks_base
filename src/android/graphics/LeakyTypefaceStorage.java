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
        int i;
        parcel.writeInt(Process.myPid());
        synchronized (sLock) {
            ArrayMap<Typeface, Integer> arrayMap = sTypefaceMap;
            Integer num = arrayMap.get(typeface);
            if (num != null) {
                i = num.intValue();
            } else {
                ArrayList<Typeface> arrayList = sStorage;
                int size = arrayList.size();
                arrayList.add(typeface);
                arrayMap.put(typeface, Integer.valueOf(size));
                i = size;
            }
            parcel.writeInt(i);
        }
    }

    public static Typeface readTypefaceFromParcel(Parcel parcel) {
        Typeface typeface;
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        if (readInt != Process.myPid()) {
            return null;
        }
        synchronized (sLock) {
            typeface = sStorage.get(readInt2);
        }
        return typeface;
    }
}
