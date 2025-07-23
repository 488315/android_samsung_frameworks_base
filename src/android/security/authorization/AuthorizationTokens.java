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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.authToken, i);
        parcel.writeTypedObject(this.timestampToken, i);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.authToken = (HardwareAuthToken) parcel.readTypedObject(HardwareAuthToken.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.timestampToken = (TimeStampToken) parcel.readTypedObject(TimeStampToken.CREATOR);
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
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
