package android.security.authenticationpolicy;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class EnableSecureLockDeviceParams implements Parcelable {
    public static final Parcelable.Creator<EnableSecureLockDeviceParams> CREATOR = new Parcelable.Creator<EnableSecureLockDeviceParams>() { // from class: android.security.authenticationpolicy.EnableSecureLockDeviceParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnableSecureLockDeviceParams createFromParcel(Parcel parcel) {
            return new EnableSecureLockDeviceParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnableSecureLockDeviceParams[] newArray(int i) {
            return new EnableSecureLockDeviceParams[i];
        }
    };
    private final CharSequence mMessage;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public EnableSecureLockDeviceParams(CharSequence charSequence) {
        this.mMessage = charSequence;
    }

    private EnableSecureLockDeviceParams(Parcel parcel) {
        this.mMessage = (CharSequence) Objects.requireNonNull(parcel.readCharSequence());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeCharSequence(this.mMessage);
    }
}
