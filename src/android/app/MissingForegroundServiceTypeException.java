package android.app;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class MissingForegroundServiceTypeException extends ForegroundServiceTypeException implements Parcelable {
    public static final Parcelable.Creator<MissingForegroundServiceTypeException> CREATOR = new Parcelable.Creator<MissingForegroundServiceTypeException>() { // from class: android.app.MissingForegroundServiceTypeException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MissingForegroundServiceTypeException createFromParcel(Parcel parcel) {
            return new MissingForegroundServiceTypeException(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MissingForegroundServiceTypeException[] newArray(int i) {
            return new MissingForegroundServiceTypeException[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MissingForegroundServiceTypeException(String str) {
        super(str);
    }

    MissingForegroundServiceTypeException(Parcel parcel) {
        super(parcel.readString());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getMessage());
    }
}
