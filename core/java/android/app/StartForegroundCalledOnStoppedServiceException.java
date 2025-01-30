package android.app;

import android.p009os.Parcel;
import android.p009os.Parcelable;

/* loaded from: classes.dex */
public final class StartForegroundCalledOnStoppedServiceException extends IllegalStateException implements Parcelable {
    public static final Parcelable.Creator<StartForegroundCalledOnStoppedServiceException> CREATOR = new Parcelable.Creator<StartForegroundCalledOnStoppedServiceException>() { // from class: android.app.StartForegroundCalledOnStoppedServiceException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StartForegroundCalledOnStoppedServiceException createFromParcel(Parcel source) {
            return new StartForegroundCalledOnStoppedServiceException(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StartForegroundCalledOnStoppedServiceException[] newArray(int size) {
            return new StartForegroundCalledOnStoppedServiceException[size];
        }
    };

    public StartForegroundCalledOnStoppedServiceException(String message) {
        super(message);
    }

    StartForegroundCalledOnStoppedServiceException(Parcel source) {
        super(source.readString());
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getMessage());
    }
}
