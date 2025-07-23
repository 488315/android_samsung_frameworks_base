package android.hardware.keymaster;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class VerificationToken implements Parcelable {
    public static final Parcelable.Creator<VerificationToken> CREATOR = new Parcelable.Creator<VerificationToken>() { // from class: android.hardware.keymaster.VerificationToken.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VerificationToken createFromParcel(Parcel parcel) {
            VerificationToken verificationToken = new VerificationToken();
            verificationToken.readFromParcel(parcel);
            return verificationToken;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VerificationToken[] newArray(int i) {
            return new VerificationToken[i];
        }
    };
    public byte[] mac;
    public Timestamp timestamp;
    public long challenge = 0;
    public int securityLevel = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.challenge);
        parcel.writeTypedObject(this.timestamp, i);
        parcel.writeInt(this.securityLevel);
        parcel.writeByteArray(this.mac);
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
                this.challenge = parcel.readLong();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.timestamp = (Timestamp) parcel.readTypedObject(Timestamp.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.securityLevel = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.mac = parcel.createByteArray();
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
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
        return describeContents(this.timestamp);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
