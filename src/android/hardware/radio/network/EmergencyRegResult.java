package android.hardware.radio.network;

import android.hardware.radio.AccessNetwork$$;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class EmergencyRegResult implements Parcelable {
    public static final Parcelable.Creator<EmergencyRegResult> CREATOR = new Parcelable.Creator<EmergencyRegResult>() { // from class: android.hardware.radio.network.EmergencyRegResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EmergencyRegResult createFromParcel(Parcel parcel) {
            EmergencyRegResult emergencyRegResult = new EmergencyRegResult();
            emergencyRegResult.readFromParcel(parcel);
            return emergencyRegResult;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EmergencyRegResult[] newArray(int i) {
            return new EmergencyRegResult[i];
        }
    };
    public int accessNetwork = 0;
    public int regState = 0;
    public int emcDomain = 0;
    public boolean isVopsSupported = false;
    public boolean isEmcBearerSupported = false;
    public byte nwProvidedEmc = 0;
    public byte nwProvidedEmf = 0;
    public String mcc = "";
    public String mnc = "";

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
        parcel.writeInt(this.accessNetwork);
        parcel.writeInt(this.regState);
        parcel.writeInt(this.emcDomain);
        parcel.writeBoolean(this.isVopsSupported);
        parcel.writeBoolean(this.isEmcBearerSupported);
        parcel.writeByte(this.nwProvidedEmc);
        parcel.writeByte(this.nwProvidedEmf);
        parcel.writeString(this.mcc);
        parcel.writeString(this.mnc);
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
                this.accessNetwork = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.regState = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.emcDomain = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.isVopsSupported = parcel.readBoolean();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.isEmcBearerSupported = parcel.readBoolean();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.nwProvidedEmc = parcel.readByte();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.nwProvidedEmf = parcel.readByte();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.mcc = parcel.readString();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.mnc = parcel.readString();
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
        stringJoiner.add("accessNetwork: " + AccessNetwork$$.toString(this.accessNetwork));
        stringJoiner.add("regState: " + RegState$$.toString(this.regState));
        stringJoiner.add("emcDomain: " + Domain$$.toString(this.emcDomain));
        stringJoiner.add("isVopsSupported: " + this.isVopsSupported);
        stringJoiner.add("isEmcBearerSupported: " + this.isEmcBearerSupported);
        stringJoiner.add("nwProvidedEmc: " + ((int) this.nwProvidedEmc));
        stringJoiner.add("nwProvidedEmf: " + ((int) this.nwProvidedEmf));
        stringJoiner.add("mcc: " + Objects.toString(this.mcc));
        stringJoiner.add("mnc: " + Objects.toString(this.mnc));
        return "EmergencyRegResult" + stringJoiner.toString();
    }
}
