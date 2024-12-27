package android.app.admin;

import android.app.admin.flags.Flags;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/* loaded from: classes.dex */
public final class StringPolicyValue extends PolicyValue<String> {
    public static final Parcelable.Creator<StringPolicyValue> CREATOR =
            new Parcelable.Creator<
                    StringPolicyValue>() { // from class: android.app.admin.StringPolicyValue.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public StringPolicyValue createFromParcel(Parcel source) {
                    return new StringPolicyValue(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public StringPolicyValue[] newArray(int size) {
                    return new StringPolicyValue[size];
                }
            };

    public StringPolicyValue(String value) {
        super(value);
        if (Flags.devicePolicySizeTrackingInternalBugFixEnabled()) {
            PolicySizeVerifier.enforceMaxStringLength(value, "policyValue");
        }
    }

    private StringPolicyValue(Parcel source) {
        super(source.readString());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StringPolicyValue other = (StringPolicyValue) o;
        return Objects.equals(getValue(), other.getValue());
    }

    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "StringPolicyValue { " + getValue() + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getValue());
    }
}
