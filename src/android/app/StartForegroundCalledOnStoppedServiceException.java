package android.app;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class StartForegroundCalledOnStoppedServiceException extends IllegalStateException implements Parcelable {
    public static final Parcelable.Creator<StartForegroundCalledOnStoppedServiceException> CREATOR = new Parcelable.Creator<StartForegroundCalledOnStoppedServiceException>() { // from class: android.app.StartForegroundCalledOnStoppedServiceException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StartForegroundCalledOnStoppedServiceException createFromParcel(Parcel parcel) {
            return new StartForegroundCalledOnStoppedServiceException(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StartForegroundCalledOnStoppedServiceException[] newArray(int i) {
            return new StartForegroundCalledOnStoppedServiceException[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public StartForegroundCalledOnStoppedServiceException(String str) {
        super(str);
    }

    StartForegroundCalledOnStoppedServiceException(Parcel parcel) {
        super(parcel.readString());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getMessage());
    }
}
