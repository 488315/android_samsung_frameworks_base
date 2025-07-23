package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class BooleanPolicyValue extends PolicyValue<Boolean> {
    public static final Parcelable.Creator<BooleanPolicyValue> CREATOR = new Parcelable.Creator<BooleanPolicyValue>() { // from class: android.app.admin.BooleanPolicyValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BooleanPolicyValue createFromParcel(Parcel parcel) {
            return new BooleanPolicyValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BooleanPolicyValue[] newArray(int i) {
            return new BooleanPolicyValue[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BooleanPolicyValue(boolean z) {
        super(Boolean.valueOf(z));
    }

    private BooleanPolicyValue(Parcel parcel) {
        this(parcel.readBoolean());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(getValue(), ((BooleanPolicyValue) obj).getValue());
    }

    public int hashCode() {
        return Objects.hash(getValue());
    }

    public String toString() {
        return "BooleanPolicyValue { mValue= " + getValue() + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(getValue().booleanValue());
    }
}
