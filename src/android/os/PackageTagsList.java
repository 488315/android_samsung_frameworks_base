package android.os;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.PackageTagsList;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.ArraySet;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final class PackageTagsList implements Parcelable {
    public static final Parcelable.Creator<PackageTagsList> CREATOR = new Parcelable.Creator<PackageTagsList>() { // from class: android.os.PackageTagsList.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackageTagsList createFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            ArrayMap arrayMap = new ArrayMap(readInt);
            int i = 0;
            while (true) {
                if (i < readInt) {
                    arrayMap.append(parcel.readString8(), parcel.readArraySet(null));
                    i++;
                } else {
                    return new PackageTagsList(arrayMap);
                }
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackageTagsList[] newArray(int i) {
            return new PackageTagsList[i];
        }
    };
    private final ArrayMap<String, ArraySet<String>> mPackageTags;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private PackageTagsList(ArrayMap<String, ArraySet<String>> arrayMap) {
        this.mPackageTags = (ArrayMap) Objects.requireNonNull(arrayMap);
    }

    public boolean isEmpty() {
        return this.mPackageTags.isEmpty();
    }

    public boolean includes(String str) {
        return this.mPackageTags.containsKey(str);
    }

    public boolean includesTag(String str) {
        int size = this.mPackageTags.size();
        for (int i = 0; i < size; i++) {
            if (this.mPackageTags.valueAt(i).contains(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsAll(String str) {
        ArraySet<String> arraySet = this.mPackageTags.get(str);
        return arraySet != null && arraySet.isEmpty();
    }

    public boolean contains(String str, String str2) {
        ArraySet<String> arraySet = this.mPackageTags.get(str);
        if (arraySet == null) {
            return false;
        }
        if (arraySet.isEmpty()) {
            return true;
        }
        return arraySet.contains(str2);
    }

    public boolean contains(PackageTagsList packageTagsList) {
        int size = packageTagsList.mPackageTags.size();
        if (size > this.mPackageTags.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            ArraySet<String> arraySet = this.mPackageTags.get(packageTagsList.mPackageTags.keyAt(i));
            if (arraySet == null) {
                return false;
            }
            if (!arraySet.isEmpty()) {
                ArraySet<String> valueAt = packageTagsList.mPackageTags.valueAt(i);
                if (valueAt.isEmpty() || !arraySet.containsAll(valueAt)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Deprecated
    public Collection<String> getPackages() {
        return new ArrayList(this.mPackageTags.keySet());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int size = this.mPackageTags.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeString8(this.mPackageTags.keyAt(i2));
            parcel.writeArraySet(this.mPackageTags.valueAt(i2));
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PackageTagsList) {
            return this.mPackageTags.equals(((PackageTagsList) obj).mPackageTags);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mPackageTags);
    }

    public String toString() {
        return this.mPackageTags.toString();
    }

    public void dump(PrintWriter printWriter) {
        int size = this.mPackageTags.size();
        for (int i = 0; i < size; i++) {
            String keyAt = this.mPackageTags.keyAt(i);
            printWriter.print(keyAt);
            printWriter.print(NavigationBarInflaterView.SIZE_MOD_START);
            int size2 = this.mPackageTags.valueAt(i).size();
            if (size2 == 0) {
                printWriter.print("*");
            } else {
                for (int i2 = 0; i2 < size2; i2++) {
                    String valueAt = this.mPackageTags.valueAt(i).valueAt(i2);
                    if (i2 > 0) {
                        printWriter.print(", ");
                    }
                    if (valueAt != null && valueAt.startsWith(keyAt)) {
                        printWriter.print(valueAt.substring(keyAt.length()));
                    } else {
                        printWriter.print(valueAt);
                    }
                }
            }
            printWriter.println(NavigationBarInflaterView.SIZE_MOD_END);
        }
    }

    public static final class Builder {
        private final ArrayMap<String, ArraySet<String>> mPackageTags;

        public Builder() {
            this.mPackageTags = new ArrayMap<>();
        }

        public Builder(int i) {
            this.mPackageTags = new ArrayMap<>(i);
        }

        static /* synthetic */ ArraySet lambda$add$0(String str) {
            return new ArraySet();
        }

        public Builder add(String str) {
            this.mPackageTags.computeIfAbsent(str, new Function() { // from class: android.os.PackageTagsList$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return PackageTagsList.Builder.lambda$add$0((String) obj);
                }
            }).clear();
            return this;
        }

        public Builder add(String str, String str2) {
            ArraySet<String> arraySet = this.mPackageTags.get(str);
            if (arraySet == null) {
                ArraySet<String> arraySet2 = new ArraySet<>(1);
                arraySet2.add(str2);
                this.mPackageTags.put(str, arraySet2);
                return this;
            }
            if (!arraySet.isEmpty()) {
                arraySet.add(str2);
            }
            return this;
        }

        public Builder add(String str, Collection<String> collection) {
            if (!collection.isEmpty()) {
                ArraySet<String> arraySet = this.mPackageTags.get(str);
                if (arraySet == null) {
                    this.mPackageTags.put(str, new ArraySet<>(collection));
                    return this;
                }
                if (!arraySet.isEmpty()) {
                    arraySet.addAll(collection);
                }
            }
            return this;
        }

        public Builder add(PackageTagsList packageTagsList) {
            return add(packageTagsList.mPackageTags);
        }

        public Builder add(Map<String, ? extends Set<String>> map) {
            this.mPackageTags.ensureCapacity(map.size());
            for (Map.Entry<String, ? extends Set<String>> entry : map.entrySet()) {
                Set<String> value = entry.getValue();
                if (value.isEmpty()) {
                    add(entry.getKey());
                } else {
                    add(entry.getKey(), value);
                }
            }
            return this;
        }

        public Builder remove(String str) {
            this.mPackageTags.remove(str);
            return this;
        }

        public Builder remove(String str, String str2) {
            ArraySet<String> arraySet = this.mPackageTags.get(str);
            if (arraySet != null && arraySet.remove(str2) && arraySet.isEmpty()) {
                this.mPackageTags.remove(str);
            }
            return this;
        }

        public Builder remove(String str, Collection<String> collection) {
            ArraySet<String> arraySet;
            if (!collection.isEmpty() && (arraySet = this.mPackageTags.get(str)) != null && arraySet.removeAll(collection) && arraySet.isEmpty()) {
                this.mPackageTags.remove(str);
            }
            return this;
        }

        public Builder remove(PackageTagsList packageTagsList) {
            return remove(packageTagsList.mPackageTags);
        }

        public Builder remove(Map<String, ? extends Set<String>> map) {
            for (Map.Entry<String, ? extends Set<String>> entry : map.entrySet()) {
                Set<String> value = entry.getValue();
                if (value.isEmpty()) {
                    remove(entry.getKey());
                } else {
                    remove(entry.getKey(), value);
                }
            }
            return this;
        }

        public Builder clear() {
            this.mPackageTags.clear();
            return this;
        }

        public PackageTagsList build() {
            return new PackageTagsList(copy(this.mPackageTags));
        }

        private static ArrayMap<String, ArraySet<String>> copy(ArrayMap<String, ArraySet<String>> arrayMap) {
            int size = arrayMap.size();
            ArrayMap<String, ArraySet<String>> arrayMap2 = new ArrayMap<>(size);
            for (int i = 0; i < size; i++) {
                arrayMap2.append(arrayMap.keyAt(i), new ArraySet<>((ArraySet) Objects.requireNonNull(arrayMap.valueAt(i))));
            }
            return arrayMap2;
        }
    }
}
