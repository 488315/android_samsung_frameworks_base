package android.app;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class ForegroundServiceStartNotAllowedException extends ServiceStartNotAllowedException implements Parcelable {
    public static final Parcelable.Creator<ForegroundServiceStartNotAllowedException> CREATOR = new Parcelable.Creator<ForegroundServiceStartNotAllowedException>() { // from class: android.app.ForegroundServiceStartNotAllowedException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ForegroundServiceStartNotAllowedException createFromParcel(Parcel parcel) {
            return new ForegroundServiceStartNotAllowedException(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ForegroundServiceStartNotAllowedException[] newArray(int i) {
            return new ForegroundServiceStartNotAllowedException[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ForegroundServiceStartNotAllowedException(String str) {
        super(str);
    }

    ForegroundServiceStartNotAllowedException(Parcel parcel) {
        super(parcel.readString());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getMessage());
    }
}
