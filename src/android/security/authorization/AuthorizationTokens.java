package android.security.authorization;

import android.hardware.security.keymint.HardwareAuthToken;
import android.hardware.security.secureclock.TimeStampToken;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class AuthorizationTokens implements Parcelable {
    public static final Parcelable.Creator<AuthorizationTokens> CREATOR = new Parcelable.Creator<AuthorizationTokens>() { // from class: android.security.authorization.AuthorizationTokens.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthorizationTokens createFromParcel(Parcel parcel) {
            AuthorizationTokens authorizationTokens = new AuthorizationTokens();
            authorizationTokens.readFromParcel(parcel);
            return authorizationTokens;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthorizationTokens[] newArray(int i) {
            return new AuthorizationTokens[i];
        }
    };
    public HardwareAuthToken authToken;
    public TimeStampToken timestampToken;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.authToken, i);
        parcel.writeTypedObject(this.timestampToken, i);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.authToken = (HardwareAuthToken) parcel.readTypedObject(HardwareAuthToken.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.timestampToken = (TimeStampToken) parcel.readTypedObject(TimeStampToken.CREATOR);
                    if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.timestampToken) | describeContents(this.authToken);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
