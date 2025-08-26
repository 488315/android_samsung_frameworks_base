package android.hardware.radio.voice;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CdmaRedirectingNumberInfoRecord implements Parcelable {
    public static final Parcelable.Creator<CdmaRedirectingNumberInfoRecord> CREATOR = new Parcelable.Creator<CdmaRedirectingNumberInfoRecord>() { // from class: android.hardware.radio.voice.CdmaRedirectingNumberInfoRecord.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaRedirectingNumberInfoRecord createFromParcel(Parcel parcel) {
            CdmaRedirectingNumberInfoRecord cdmaRedirectingNumberInfoRecord = new CdmaRedirectingNumberInfoRecord();
            cdmaRedirectingNumberInfoRecord.readFromParcel(parcel);
            return cdmaRedirectingNumberInfoRecord;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CdmaRedirectingNumberInfoRecord[] newArray(int i) {
            return new CdmaRedirectingNumberInfoRecord[i];
        }
    };

    @Deprecated
    public static final int REDIRECTING_REASON_CALLED_DTE_OUT_OF_ORDER = 9;

    @Deprecated
    public static final int REDIRECTING_REASON_CALL_FORWARDING_BUSY = 1;

    @Deprecated
    public static final int REDIRECTING_REASON_CALL_FORWARDING_BY_THE_CALLED_DTE = 10;

    @Deprecated
    public static final int REDIRECTING_REASON_CALL_FORWARDING_NO_REPLY = 2;

    @Deprecated
    public static final int REDIRECTING_REASON_CALL_FORWARDING_UNCONDITIONAL = 15;

    @Deprecated
    public static final int REDIRECTING_REASON_RESERVED = 16;

    @Deprecated
    public static final int REDIRECTING_REASON_UNKNOWN = 0;

    @Deprecated
    public CdmaNumberInfoRecord redirectingNumber;

    @Deprecated
    public int redirectingReason = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.redirectingNumber, i);
        parcel.writeInt(this.redirectingReason);
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
                this.redirectingNumber = (CdmaNumberInfoRecord) parcel.readTypedObject(CdmaNumberInfoRecord.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.redirectingReason = parcel.readInt();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("redirectingNumber: " + Objects.toString(this.redirectingNumber));
        stringJoiner.add("redirectingReason: " + this.redirectingReason);
        return "CdmaRedirectingNumberInfoRecord" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.redirectingNumber);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
