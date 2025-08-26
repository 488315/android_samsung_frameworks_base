package android.hardware.radio.network;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class EutranRegistrationInfo implements Parcelable {
    public static final Parcelable.Creator<EutranRegistrationInfo> CREATOR = new Parcelable.Creator<EutranRegistrationInfo>() { // from class: android.hardware.radio.network.EutranRegistrationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EutranRegistrationInfo createFromParcel(Parcel parcel) {
            EutranRegistrationInfo eutranRegistrationInfo = new EutranRegistrationInfo();
            eutranRegistrationInfo.readFromParcel(parcel);
            return eutranRegistrationInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EutranRegistrationInfo[] newArray(int i) {
            return new EutranRegistrationInfo[i];
        }
    };
    public static final int EXTRA_CSFB_NOT_PREFERRED = 1;
    public static final int EXTRA_SMS_ONLY = 2;
    public LteVopsInfo lteVopsInfo;
    public NrIndicators nrIndicators;
    public byte lteAttachResultType = 0;
    public int extraInfo = 0;

    public @interface AttachResultType {
        public static final byte COMBINED = 2;
        public static final byte EPS_ONLY = 1;
        public static final byte NONE = 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.lteVopsInfo, i);
        parcel.writeTypedObject(this.nrIndicators, i);
        parcel.writeByte(this.lteAttachResultType);
        parcel.writeInt(this.extraInfo);
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
                this.lteVopsInfo = (LteVopsInfo) parcel.readTypedObject(LteVopsInfo.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.nrIndicators = (NrIndicators) parcel.readTypedObject(NrIndicators.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.lteAttachResultType = parcel.readByte();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.extraInfo = parcel.readInt();
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
        stringJoiner.add("lteVopsInfo: " + Objects.toString(this.lteVopsInfo));
        stringJoiner.add("nrIndicators: " + Objects.toString(this.nrIndicators));
        stringJoiner.add("lteAttachResultType: " + ((int) this.lteAttachResultType));
        stringJoiner.add("extraInfo: " + this.extraInfo);
        return "EutranRegistrationInfo" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.nrIndicators) | describeContents(this.lteVopsInfo);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
