package android.app;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class InvalidForegroundServiceTypeException extends ForegroundServiceTypeException
        implements Parcelable {
    public static final Parcelable.Creator<InvalidForegroundServiceTypeException> CREATOR =
            new Parcelable.Creator<InvalidForegroundServiceTypeException>() { // from class:
                // android.app.InvalidForegroundServiceTypeException.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public InvalidForegroundServiceTypeException createFromParcel(Parcel source) {
                    return new InvalidForegroundServiceTypeException(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public InvalidForegroundServiceTypeException[] newArray(int size) {
                    return new InvalidForegroundServiceTypeException[size];
                }
            };

    public InvalidForegroundServiceTypeException(String message) {
        super(message);
    }

    InvalidForegroundServiceTypeException(Parcel source) {
        super(source.readString());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getMessage());
    }
}
