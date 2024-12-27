package android.app;

import android.os.Parcel;
import android.os.Parcelable;

public final class BackgroundServiceStartNotAllowedException extends ServiceStartNotAllowedException
        implements Parcelable {
    public static final Parcelable.Creator<BackgroundServiceStartNotAllowedException> CREATOR =
            new Parcelable.Creator<BackgroundServiceStartNotAllowedException>() { // from class:
                // android.app.BackgroundServiceStartNotAllowedException.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public BackgroundServiceStartNotAllowedException createFromParcel(Parcel source) {
                    return new BackgroundServiceStartNotAllowedException(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public BackgroundServiceStartNotAllowedException[] newArray(int size) {
                    return new BackgroundServiceStartNotAllowedException[size];
                }
            };

    public BackgroundServiceStartNotAllowedException(String message) {
        super(message);
    }

    BackgroundServiceStartNotAllowedException(Parcel source) {
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
