package android.app;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class InvalidForegroundServiceTypeException extends ForegroundServiceTypeException implements Parcelable {
    public static final Parcelable.Creator<InvalidForegroundServiceTypeException> CREATOR = new Parcelable.Creator<InvalidForegroundServiceTypeException>() { // from class: android.app.InvalidForegroundServiceTypeException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InvalidForegroundServiceTypeException createFromParcel(Parcel parcel) {
            return new InvalidForegroundServiceTypeException(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InvalidForegroundServiceTypeException[] newArray(int i) {
            return new InvalidForegroundServiceTypeException[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public InvalidForegroundServiceTypeException(String str) {
        super(str);
    }

    InvalidForegroundServiceTypeException(Parcel parcel) {
        super(parcel.readString());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getMessage());
    }
}
