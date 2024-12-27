package android.app.admin;

import android.app.admin.flags.Flags;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes.dex */
public final class PackageSetPolicyValue extends PolicyValue<Set<String>> {
    public static final Parcelable.Creator<PackageSetPolicyValue> CREATOR = new Parcelable.Creator<PackageSetPolicyValue>() { // from class: android.app.admin.PackageSetPolicyValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackageSetPolicyValue createFromParcel(Parcel source) {
            return new PackageSetPolicyValue(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PackageSetPolicyValue[] newArray(int size) {
            return new PackageSetPolicyValue[size];
        }
    };

    public PackageSetPolicyValue(Set<String> value) {
        super(value);
        if (Flags.devicePolicySizeTrackingInternalBugFixEnabled()) {
            for (String packageName : value) {
                PolicySizeVerifier.enforceMaxPackageNameLength(packageName);
            }
        }
    }

    public PackageSetPolicyValue(Parcel source) {
        this(readValues(source));
    }

    private static Set<String> readValues(Parcel source) {
        Set<String> values = new HashSet<>();
        int size = source.readInt();
        for (int i = 0; i < size; i++) {
            values.add(source.readString());
        }
        return values;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PackageSetPolicyValue other = (PackageSetPolicyValue) o;
        return Objects.equals(getValue(), other.getValue());
    }

    public int hashCode() {
        return Objects.hash(getValue());
    }

    public String toString() {
        return "PackageNameSetPolicyValue { " + getValue() + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getValue().size());
        for (String entry : getValue()) {
            dest.writeString(entry);
        }
    }
}
