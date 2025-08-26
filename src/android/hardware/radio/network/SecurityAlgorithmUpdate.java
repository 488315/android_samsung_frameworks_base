package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class SecurityAlgorithmUpdate implements Parcelable {
    public static final Parcelable.Creator<SecurityAlgorithmUpdate> CREATOR = new Parcelable.Creator<SecurityAlgorithmUpdate>() { // from class: android.hardware.radio.network.SecurityAlgorithmUpdate.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SecurityAlgorithmUpdate createFromParcel(Parcel parcel) {
            SecurityAlgorithmUpdate securityAlgorithmUpdate = new SecurityAlgorithmUpdate();
            securityAlgorithmUpdate.readFromParcel(parcel);
            return securityAlgorithmUpdate;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SecurityAlgorithmUpdate[] newArray(int i) {
            return new SecurityAlgorithmUpdate[i];
        }
    };
    public int connectionEvent = 0;
    public int encryption = 0;
    public int integrity = 0;
    public boolean isUnprotectedEmergency = false;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.connectionEvent);
        parcel.writeInt(this.encryption);
        parcel.writeInt(this.integrity);
        parcel.writeBoolean(this.isUnprotectedEmergency);
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
                this.connectionEvent = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.encryption = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.integrity = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.isUnprotectedEmergency = parcel.readBoolean();
                            if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("connectionEvent: " + ConnectionEvent$$.toString(this.connectionEvent));
        stringJoiner.add("encryption: " + SecurityAlgorithm$$.toString(this.encryption));
        stringJoiner.add("integrity: " + SecurityAlgorithm$$.toString(this.integrity));
        stringJoiner.add("isUnprotectedEmergency: " + this.isUnprotectedEmergency);
        return "SecurityAlgorithmUpdate" + stringJoiner.toString();
    }
}
