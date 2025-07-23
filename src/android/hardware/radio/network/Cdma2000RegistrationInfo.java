package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class Cdma2000RegistrationInfo implements Parcelable {
    public static final Parcelable.Creator<Cdma2000RegistrationInfo> CREATOR = new Parcelable.Creator<Cdma2000RegistrationInfo>() { // from class: android.hardware.radio.network.Cdma2000RegistrationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Cdma2000RegistrationInfo createFromParcel(Parcel parcel) {
            Cdma2000RegistrationInfo cdma2000RegistrationInfo = new Cdma2000RegistrationInfo();
            cdma2000RegistrationInfo.readFromParcel(parcel);
            return cdma2000RegistrationInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Cdma2000RegistrationInfo[] newArray(int i) {
            return new Cdma2000RegistrationInfo[i];
        }
    };

    @Deprecated
    public static final int PRL_INDICATOR_IN_PRL = 1;

    @Deprecated
    public static final int PRL_INDICATOR_NOT_IN_PRL = 0;

    @Deprecated
    public static final int PRL_INDICATOR_NOT_REGISTERED = -1;

    @Deprecated
    public boolean cssSupported = false;

    @Deprecated
    public int roamingIndicator = 0;

    @Deprecated
    public int systemIsInPrl = 0;

    @Deprecated
    public int defaultRoamingIndicator = 0;

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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeBoolean(this.cssSupported);
        parcel.writeInt(this.roamingIndicator);
        parcel.writeInt(this.systemIsInPrl);
        parcel.writeInt(this.defaultRoamingIndicator);
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
                this.cssSupported = parcel.readBoolean();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.roamingIndicator = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.systemIsInPrl = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.defaultRoamingIndicator = parcel.readInt();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("cssSupported: " + this.cssSupported);
        stringJoiner.add("roamingIndicator: " + this.roamingIndicator);
        stringJoiner.add("systemIsInPrl: " + this.systemIsInPrl);
        stringJoiner.add("defaultRoamingIndicator: " + this.defaultRoamingIndicator);
        return "Cdma2000RegistrationInfo" + stringJoiner.toString();
    }
}
