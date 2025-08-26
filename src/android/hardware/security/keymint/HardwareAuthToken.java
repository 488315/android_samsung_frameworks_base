package android.hardware.security.keymint;

import android.hardware.security.secureclock.Timestamp;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class HardwareAuthToken implements Parcelable {
    public static final Parcelable.Creator<HardwareAuthToken> CREATOR = new Parcelable.Creator<HardwareAuthToken>() { // from class: android.hardware.security.keymint.HardwareAuthToken.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HardwareAuthToken createFromParcel(Parcel parcel) {
            HardwareAuthToken hardwareAuthToken = new HardwareAuthToken();
            hardwareAuthToken.readFromParcel(parcel);
            return hardwareAuthToken;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HardwareAuthToken[] newArray(int i) {
            return new HardwareAuthToken[i];
        }
    };
    public byte[] mac;
    public Timestamp timestamp;
    public long challenge = 0;
    public long userId = 0;
    public long authenticatorId = 0;
    public int authenticatorType = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.challenge);
        parcel.writeLong(this.userId);
        parcel.writeLong(this.authenticatorId);
        parcel.writeInt(this.authenticatorType);
        parcel.writeTypedObject(this.timestamp, i);
        parcel.writeByteArray(this.mac);
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
                this.challenge = parcel.readLong();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.userId = parcel.readLong();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.authenticatorId = parcel.readLong();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.authenticatorType = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.timestamp = (Timestamp) parcel.readTypedObject(Timestamp.CREATOR);
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.mac = parcel.createByteArray();
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
        return describeContents(this.timestamp);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
