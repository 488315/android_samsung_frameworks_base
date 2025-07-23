package android.app;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class BackgroundServiceStartNotAllowedException extends ServiceStartNotAllowedException implements Parcelable {
    public static final Parcelable.Creator<BackgroundServiceStartNotAllowedException> CREATOR = new Parcelable.Creator<BackgroundServiceStartNotAllowedException>() { // from class: android.app.BackgroundServiceStartNotAllowedException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackgroundServiceStartNotAllowedException createFromParcel(Parcel parcel) {
            return new BackgroundServiceStartNotAllowedException(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BackgroundServiceStartNotAllowedException[] newArray(int i) {
            return new BackgroundServiceStartNotAllowedException[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public BackgroundServiceStartNotAllowedException(String str) {
        super(str);
    }

    BackgroundServiceStartNotAllowedException(Parcel parcel) {
        super(parcel.readString());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getMessage());
    }
}
