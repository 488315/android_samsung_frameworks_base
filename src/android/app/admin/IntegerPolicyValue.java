package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class IntegerPolicyValue extends PolicyValue<Integer> {
    public static final Parcelable.Creator<IntegerPolicyValue> CREATOR = new Parcelable.Creator<IntegerPolicyValue>() { // from class: android.app.admin.IntegerPolicyValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IntegerPolicyValue createFromParcel(Parcel parcel) {
            return new IntegerPolicyValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IntegerPolicyValue[] newArray(int i) {
            return new IntegerPolicyValue[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public IntegerPolicyValue(int i) {
        super(Integer.valueOf(i));
    }

    private IntegerPolicyValue(Parcel parcel) {
        this(parcel.readInt());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(getValue(), ((IntegerPolicyValue) obj).getValue());
    }

    public int hashCode() {
        return Objects.hash(getValue());
    }

    public String toString() {
        return "IntegerPolicyValue { mValue= " + getValue() + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getValue().intValue());
    }
}
