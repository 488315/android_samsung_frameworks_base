package android.security.authenticationpolicy;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class DisableSecureLockDeviceParams implements Parcelable {
    public static final Parcelable.Creator<DisableSecureLockDeviceParams> CREATOR = new Parcelable.Creator<DisableSecureLockDeviceParams>() { // from class: android.security.authenticationpolicy.DisableSecureLockDeviceParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisableSecureLockDeviceParams createFromParcel(Parcel parcel) {
            return new DisableSecureLockDeviceParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisableSecureLockDeviceParams[] newArray(int i) {
            return new DisableSecureLockDeviceParams[i];
        }
    };
    private final CharSequence mMessage;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DisableSecureLockDeviceParams(CharSequence charSequence) {
        this.mMessage = charSequence;
    }

    private DisableSecureLockDeviceParams(Parcel parcel) {
        this.mMessage = (CharSequence) Objects.requireNonNull(parcel.readCharSequence());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeCharSequence(this.mMessage);
    }
}
