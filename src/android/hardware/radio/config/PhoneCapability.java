package android.hardware.radio.config;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class PhoneCapability implements Parcelable {
    public static final Parcelable.Creator<PhoneCapability> CREATOR = new Parcelable.Creator<PhoneCapability>() { // from class: android.hardware.radio.config.PhoneCapability.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhoneCapability createFromParcel(Parcel parcel) {
            PhoneCapability phoneCapability = new PhoneCapability();
            phoneCapability.readFromParcel(parcel);
            return phoneCapability;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhoneCapability[] newArray(int i) {
            return new PhoneCapability[i];
        }
    };
    public static final byte UNKNOWN = -1;
    public byte[] logicalModemIds;
    public byte maxActiveData = 0;
    public byte maxActiveInternetData = 0;
    public boolean isInternetLingeringSupported = false;
    public byte maxActiveVoice = -1;

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
        parcel.writeByte(this.maxActiveData);
        parcel.writeByte(this.maxActiveInternetData);
        parcel.writeBoolean(this.isInternetLingeringSupported);
        parcel.writeByteArray(this.logicalModemIds);
        parcel.writeByte(this.maxActiveVoice);
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
                this.maxActiveData = parcel.readByte();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.maxActiveInternetData = parcel.readByte();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.isInternetLingeringSupported = parcel.readBoolean();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.logicalModemIds = parcel.createByteArray();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.maxActiveVoice = parcel.readByte();
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
        stringJoiner.add("maxActiveData: " + ((int) this.maxActiveData));
        stringJoiner.add("maxActiveInternetData: " + ((int) this.maxActiveInternetData));
        stringJoiner.add("isInternetLingeringSupported: " + this.isInternetLingeringSupported);
        stringJoiner.add("logicalModemIds: " + Arrays.toString(this.logicalModemIds));
        stringJoiner.add("maxActiveVoice: " + ((int) this.maxActiveVoice));
        return "PhoneCapability" + stringJoiner.toString();
    }
}
