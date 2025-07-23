package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class LongPolicyValue extends PolicyValue<Long> {
    public static final Parcelable.Creator<LongPolicyValue> CREATOR = new Parcelable.Creator<LongPolicyValue>() { // from class: android.app.admin.LongPolicyValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LongPolicyValue createFromParcel(Parcel parcel) {
            return new LongPolicyValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LongPolicyValue[] newArray(int i) {
            return new LongPolicyValue[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LongPolicyValue(long j) {
        super(Long.valueOf(j));
    }

    private LongPolicyValue(Parcel parcel) {
        this(parcel.readLong());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(getValue(), ((LongPolicyValue) obj).getValue());
    }

    public int hashCode() {
        return Objects.hash(getValue());
    }

    public String toString() {
        return "LongPolicyValue { mValue= " + getValue() + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(getValue().longValue());
    }
}
