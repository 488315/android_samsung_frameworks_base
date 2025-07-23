package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class StringPolicyValue extends PolicyValue<String> {
    public static final Parcelable.Creator<StringPolicyValue> CREATOR = new Parcelable.Creator<StringPolicyValue>() { // from class: android.app.admin.StringPolicyValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StringPolicyValue createFromParcel(Parcel parcel) {
            return new StringPolicyValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StringPolicyValue[] newArray(int i) {
            return new StringPolicyValue[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public StringPolicyValue(String str) {
        super(str);
        PolicySizeVerifier.enforceMaxStringLength(str, "policyValue");
    }

    private StringPolicyValue(Parcel parcel) {
        super(parcel.readString());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(getValue(), ((StringPolicyValue) obj).getValue());
    }

    public int hashCode() {
        return Objects.hash(getValue());
    }

    public String toString() {
        return "StringPolicyValue { " + getValue() + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getValue());
    }
}
