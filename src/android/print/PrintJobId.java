package android.print;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.UUID;

/* loaded from: classes3.dex */
public final class PrintJobId implements Parcelable {
    public static final Parcelable.Creator<PrintJobId> CREATOR = new Parcelable.Creator<PrintJobId>() { // from class: android.print.PrintJobId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrintJobId createFromParcel(Parcel parcel) {
            return new PrintJobId((String) Preconditions.checkNotNull(parcel.readString()));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PrintJobId[] newArray(int i) {
            return new PrintJobId[i];
        }
    };
    private final String mValue;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PrintJobId() {
        this(UUID.randomUUID().toString());
    }

    public PrintJobId(String str) {
        this.mValue = str;
    }

    public int hashCode() {
        return 31 + this.mValue.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.mValue.equals(((PrintJobId) obj).mValue);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mValue);
    }

    public String flattenToString() {
        return this.mValue;
    }

    public static PrintJobId unflattenFromString(String str) {
        return new PrintJobId(str);
    }
}
