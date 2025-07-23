package android.app.admin;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class BundlePolicyValue extends PolicyValue<Bundle> {
    public static final Parcelable.Creator<BundlePolicyValue> CREATOR = new Parcelable.Creator<BundlePolicyValue>() { // from class: android.app.admin.BundlePolicyValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BundlePolicyValue createFromParcel(Parcel parcel) {
            return new BundlePolicyValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BundlePolicyValue[] newArray(int i) {
            return new BundlePolicyValue[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BundlePolicyValue(Bundle bundle) {
        super(bundle);
        PolicySizeVerifier.enforceMaxBundleFieldsLength(bundle);
    }

    private BundlePolicyValue(Parcel parcel) {
        this(parcel.readBundle());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(getValue(), ((BundlePolicyValue) obj).getValue());
    }

    public int hashCode() {
        return Objects.hash(getValue());
    }

    public String toString() {
        return "BundlePolicyValue { mValue= " + getValue() + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(getValue());
    }
}
