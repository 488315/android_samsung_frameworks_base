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
        int readInt = parcel.readInt();
        HashMap hashMap = new HashMap(readInt);
        while (true) {
            int i = readInt - 1;
            if (readInt <= 0) {
                return hashMap;
            }
            hashMap.put(parcel.readString(), parcel.readString());
            readInt = i;
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
        int readInt = parcel.readInt();
        HashMap hashMap = new HashMap(readInt);
        while (true) {
            int i = readInt - 1;
            if (readInt <= 0) {
                return hashMap;
            }
            hashMap.put(parcel.readString(), Integer.valueOf(parcel.readInt()));
            readInt = i;
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
                    Parcel.this.writeTypedObject((Parcelable) obj, 0);
                }
            });
        }
    }

    static <T> Set<T> createSet(Parcel parcel, Parcelable.Creator<T> creator) {
        int readInt = parcel.readInt();
        HashSet hashSet = new HashSet(readInt);
        while (true) {
            int i = readInt - 1;
            if (readInt <= 0) {
                return hashSet;
            }
            hashSet.add(parcel.readTypedObject(creator));
            readInt = i;
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
                    Parcel.this.writeInt(((Integer) Objects.requireNonNull((Integer) obj)).intValue());
                }
            });
        }
    }

    static Set<Integer> createIntSet(Parcel parcel) {
        int readInt = parcel.readInt();
        HashSet hashSet = new HashSet(readInt);
        while (true) {
            int i = readInt - 1;
            if (readInt <= 0) {
                return hashSet;
            }
            hashSet.add(Integer.valueOf(parcel.readInt()));
            readInt = i;
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
