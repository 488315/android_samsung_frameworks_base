package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class PackageSetPolicyValue extends PolicyValue<Set<String>> {
    public static final Parcelable.Creator<PackageSetPolicyValue> CREATOR = new Parcelable.Creator<PackageSetPolicyValue>() { // from class: android.app.admin.PackageSetPolicyValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackageSetPolicyValue createFromParcel(Parcel parcel) {
            return new PackageSetPolicyValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackageSetPolicyValue[] newArray(int i) {
            return new PackageSetPolicyValue[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PackageSetPolicyValue(Set<String> set) {
        super(set);
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            PolicySizeVerifier.enforceMaxPackageNameLength(it.next());
        }
    }

    public PackageSetPolicyValue(Parcel parcel) {
        this(readValues(parcel));
    }

    private static Set<String> readValues(Parcel parcel) {
        HashSet hashSet = new HashSet();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            hashSet.add(parcel.readString());
        }
        return hashSet;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(getValue(), ((PackageSetPolicyValue) obj).getValue());
    }

    public int hashCode() {
        return Objects.hash(getValue());
    }

    public String toString() {
        return "PackageNameSetPolicyValue { " + getValue() + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getValue().size());
        Iterator<String> it = getValue().iterator();
        while (it.hasNext()) {
            parcel.writeString(it.next());
        }
    }
}
