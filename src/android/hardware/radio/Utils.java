package android.hardware.radio;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
final class Utils {
    private static final String TAG = "BroadcastRadio.utils";

    Utils() {
    }

    static void writeStringMap(Parcel parcel, Map<String, String> map) {
        if (map == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(map.size());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            parcel.writeString(entry.getKey());
            parcel.writeString(entry.getValue());
        }
    }

    static Map<String, String> readStringMap(Parcel parcel) {
        int i = parcel.readInt();
        HashMap map = new HashMap(i);
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return map;
            }
            map.put(parcel.readString(), parcel.readString());
            i = i2;
        }
    }

    static void writeStringIntMap(Parcel parcel, Map<String, Integer> map) {
        if (map == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(map.size());
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            parcel.writeString(entry.getKey());
            parcel.writeInt(entry.getValue().intValue());
        }
    }

    static Map<String, Integer> readStringIntMap(Parcel parcel) {
        int i = parcel.readInt();
        HashMap map = new HashMap(i);
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return map;
            }
            map.put(parcel.readString(), Integer.valueOf(parcel.readInt()));
            i = i2;
        }
    }

    static <T extends Parcelable> void writeSet(final Parcel parcel, Set<T> set) {
        if (set == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(set.size());
            set.stream().forEach(new Consumer() { // from class: android.hardware.radio.Utils$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    parcel.writeTypedObject((Parcelable) obj, 0);
                }
            });
        }
    }

    static <T> Set<T> createSet(Parcel parcel, Parcelable.Creator<T> creator) {
        int i = parcel.readInt();
        HashSet hashSet = new HashSet(i);
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return hashSet;
            }
            hashSet.add(parcel.readTypedObject(creator));
            i = i2;
        }
    }

    static void writeIntSet(final Parcel parcel, Set<Integer> set) {
        if (set == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(set.size());
            set.stream().forEach(new Consumer() { // from class: android.hardware.radio.Utils$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    parcel.writeInt(((Integer) Objects.requireNonNull((Integer) obj)).intValue());
                }
            });
        }
    }

    static Set<Integer> createIntSet(Parcel parcel) {
        int i = parcel.readInt();
        HashSet hashSet = new HashSet(i);
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return hashSet;
            }
            hashSet.add(Integer.valueOf(parcel.readInt()));
            i = i2;
        }
    }

    static <T extends Parcelable> void writeTypedCollection(Parcel parcel, Collection<T> collection) {
        ArrayList arrayList;
        if (collection == null) {
            arrayList = null;
        } else if (collection instanceof ArrayList) {
            arrayList = (ArrayList) collection;
        } else {
            arrayList = new ArrayList(collection);
        }
        parcel.writeTypedList(arrayList);
    }

    static void close(ICloseHandle iCloseHandle) {
        try {
            iCloseHandle.close();
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }
}
