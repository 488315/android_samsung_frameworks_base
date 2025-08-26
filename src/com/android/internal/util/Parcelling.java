package com.android.internal.util;

import android.os.Parcel;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public interface Parcelling<T> {
    void parcel(T t, Parcel parcel, int i);

    T unparcel(Parcel parcel);

    public static class Cache {
        private static ArrayMap<Class, Parcelling> sCache = new ArrayMap<>();

        private Cache() {
        }

        public static <P extends Parcelling<?>> P get(Class<P> cls) {
            return (P) sCache.get(cls);
        }

        public static <P extends Parcelling<?>> P put(P p) {
            sCache.put(p.getClass(), p);
            return p;
        }

        public static <P extends Parcelling<?>> P getOrCreate(Class<P> cls) {
            P p = (P) get(cls);
            if (p != null) {
                return p;
            }
            try {
                return (P) put(cls.newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public interface BuiltIn {

        public static class ForInternedString implements Parcelling<String> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(String str, Parcel parcel, int i) {
                parcel.writeString(str);
            }

            @Override // com.android.internal.util.Parcelling
            public String unparcel(Parcel parcel) {
                return TextUtils.safeIntern(parcel.readString());
            }
        }

        public static class ForInternedStringArray implements Parcelling<String[]> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(String[] strArr, Parcel parcel, int i) {
                parcel.writeStringArray(strArr);
            }

            @Override // com.android.internal.util.Parcelling
            public String[] unparcel(Parcel parcel) {
                String[] stringArray = parcel.readStringArray();
                if (stringArray != null) {
                    int size = ArrayUtils.size(stringArray);
                    for (int i = 0; i < size; i++) {
                        stringArray[i] = TextUtils.safeIntern(stringArray[i]);
                    }
                }
                return stringArray;
            }
        }

        public static class ForInternedStringList implements Parcelling<List<String>> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(List<String> list, Parcel parcel, int i) {
                parcel.writeStringList(list);
            }

            @Override // com.android.internal.util.Parcelling
            public List<String> unparcel(Parcel parcel) {
                ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                if (arrayListCreateStringArrayList != null) {
                    int size = arrayListCreateStringArrayList.size();
                    for (int i = 0; i < size; i++) {
                        arrayListCreateStringArrayList.set(i, arrayListCreateStringArrayList.get(i).intern());
                    }
                }
                return CollectionUtils.emptyIfNull(arrayListCreateStringArrayList);
            }
        }

        public static class ForInternedStringValueMap implements Parcelling<Map<String, String>> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(Map<String, String> map, Parcel parcel, int i) {
                parcel.writeMap(map);
            }

            @Override // com.android.internal.util.Parcelling
            public Map<String, String> unparcel(Parcel parcel) throws ClassNotFoundException, IOException {
                ArrayMap arrayMap = new ArrayMap();
                parcel.readMap(arrayMap, String.class.getClassLoader());
                for (int i = 0; i < arrayMap.size(); i++) {
                    arrayMap.setValueAt(i, TextUtils.safeIntern((String) arrayMap.valueAt(i)));
                }
                return arrayMap;
            }
        }

        public static class ForStringSet implements Parcelling<Set<String>> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(Set<String> set, Parcel parcel, int i) {
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

            @Override // com.android.internal.util.Parcelling
            public Set<String> unparcel(Parcel parcel) {
                int i = parcel.readInt();
                if (i < 0) {
                    return Collections.EMPTY_SET;
                }
                ArraySet arraySet = new ArraySet();
                for (int i2 = 0; i2 < i; i2++) {
                    arraySet.add(parcel.readString());
                }
                return arraySet;
            }
        }

        public static class ForInternedStringSet implements Parcelling<Set<String>> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(Set<String> set, Parcel parcel, int i) {
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

            @Override // com.android.internal.util.Parcelling
            public Set<String> unparcel(Parcel parcel) {
                int i = parcel.readInt();
                if (i < 0) {
                    return Collections.EMPTY_SET;
                }
                ArraySet arraySet = new ArraySet();
                for (int i2 = 0; i2 < i; i2++) {
                    arraySet.add(TextUtils.safeIntern(parcel.readString()));
                }
                return arraySet;
            }
        }

        public static class ForInternedStringArraySet implements Parcelling<ArraySet<String>> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(ArraySet<String> arraySet, Parcel parcel, int i) {
                if (arraySet == null) {
                    parcel.writeInt(-1);
                    return;
                }
                parcel.writeInt(arraySet.size());
                Iterator<String> it = arraySet.iterator();
                while (it.hasNext()) {
                    parcel.writeString(it.next());
                }
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.android.internal.util.Parcelling
            public ArraySet<String> unparcel(Parcel parcel) {
                int i = parcel.readInt();
                if (i < 0) {
                    return null;
                }
                ArraySet<String> arraySet = new ArraySet<>();
                for (int i2 = 0; i2 < i; i2++) {
                    arraySet.add(TextUtils.safeIntern(parcel.readString()));
                }
                return arraySet;
            }
        }

        public static class ForBoolean implements Parcelling<Boolean> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(Boolean bool, Parcel parcel, int i) {
                if (bool == null) {
                    parcel.writeInt(1);
                } else if (!bool.booleanValue()) {
                    parcel.writeInt(0);
                } else {
                    parcel.writeInt(-1);
                }
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.android.internal.util.Parcelling
            public Boolean unparcel(Parcel parcel) {
                int i = parcel.readInt();
                if (i == -1) {
                    return Boolean.TRUE;
                }
                if (i == 0) {
                    return Boolean.FALSE;
                }
                if (i == 1) {
                    return null;
                }
                throw new IllegalStateException("Malformed Parcel reading Boolean: " + parcel);
            }
        }

        public static class ForPattern implements Parcelling<Pattern> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(Pattern pattern, Parcel parcel, int i) {
                parcel.writeString(pattern == null ? null : pattern.pattern());
            }

            @Override // com.android.internal.util.Parcelling
            public Pattern unparcel(Parcel parcel) {
                String string = parcel.readString();
                if (string == null) {
                    return null;
                }
                return Pattern.compile(string);
            }
        }

        public static class ForUUID implements Parcelling<UUID> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(UUID uuid, Parcel parcel, int i) {
                parcel.writeString(uuid == null ? null : uuid.toString());
            }

            @Override // com.android.internal.util.Parcelling
            public UUID unparcel(Parcel parcel) {
                String string = parcel.readString();
                if (string == null) {
                    return null;
                }
                return UUID.fromString(string);
            }
        }

        public static class ForInstant implements Parcelling<Instant> {
            @Override // com.android.internal.util.Parcelling
            public void parcel(Instant instant, Parcel parcel, int i) {
                parcel.writeLong(instant == null ? Long.MIN_VALUE : instant.getEpochSecond());
                parcel.writeInt(instant == null ? Integer.MIN_VALUE : instant.getNano());
            }

            @Override // com.android.internal.util.Parcelling
            public Instant unparcel(Parcel parcel) {
                long j = parcel.readLong();
                int i = parcel.readInt();
                if (j == Long.MIN_VALUE) {
                    return null;
                }
                return Instant.ofEpochSecond(j, i);
            }
        }
    }
}
