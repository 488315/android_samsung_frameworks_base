package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class CellularIdentifierDisclosure implements Parcelable {
    public static final int CELLULAR_IDENTIFIER_IMEI = 2;
    public static final int CELLULAR_IDENTIFIER_IMSI = 1;
    public static final int CELLULAR_IDENTIFIER_SUCI = 3;
    public static final int CELLULAR_IDENTIFIER_UNKNOWN = 0;
    public static final Parcelable.Creator<CellularIdentifierDisclosure> CREATOR = new Parcelable.Creator<CellularIdentifierDisclosure>() { // from class: android.telephony.CellularIdentifierDisclosure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellularIdentifierDisclosure createFromParcel(Parcel in) {
            return new CellularIdentifierDisclosure(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CellularIdentifierDisclosure[] newArray(int size) {
            return new CellularIdentifierDisclosure[size];
        }
    };
    public static final int NAS_PROTOCOL_MESSAGE_ATTACH_REQUEST = 1;
    public static final int NAS_PROTOCOL_MESSAGE_AUTHENTICATION_AND_CIPHERING_RESPONSE = 6;
    public static final int NAS_PROTOCOL_MESSAGE_CM_REESTABLISHMENT_REQUEST = 9;
    public static final int NAS_PROTOCOL_MESSAGE_CM_SERVICE_REQUEST = 10;
    public static final int NAS_PROTOCOL_MESSAGE_DEREGISTRATION_REQUEST = 8;
    public static final int NAS_PROTOCOL_MESSAGE_DETACH_REQUEST = 3;
    public static final int NAS_PROTOCOL_MESSAGE_IDENTITY_RESPONSE = 2;
    public static final int NAS_PROTOCOL_MESSAGE_IMSI_DETACH_INDICATION = 11;
    public static final int NAS_PROTOCOL_MESSAGE_LOCATION_UPDATE_REQUEST = 5;
    public static final int NAS_PROTOCOL_MESSAGE_REGISTRATION_REQUEST = 7;
    public static final int NAS_PROTOCOL_MESSAGE_TRACKING_AREA_UPDATE_REQUEST = 4;
    public static final int NAS_PROTOCOL_MESSAGE_UNKNOWN = 0;
    private static final String TAG = "CellularIdentifierDisclosure";
    private int mCellularIdentifier;
    private boolean mIsEmergency;
    private int mNasProtocolMessage;
    private String mPlmn;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CellularIdentifier {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NasProtocolMessage {
    }

    public CellularIdentifierDisclosure(int nasProtocolMessage, int cellularIdentifier, String plmn, boolean isEmergency) {
        this.mNasProtocolMessage = nasProtocolMessage;
        this.mCellularIdentifier = cellularIdentifier;
        this.mPlmn = plmn;
        this.mIsEmergency = isEmergency;
    }

    private CellularIdentifierDisclosure(Parcel in) {
        readFromParcel(in);
    }

    public int getNasProtocolMessage() {
        return this.mNasProtocolMessage;
    }

    public int getCellularIdentifier() {
        return this.mCellularIdentifier;
    }

    public String getPlmn() {
        return this.mPlmn;
    }

    public boolean isEmergency() {
        return this.mIsEmergency;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mNasProtocolMessage);
        out.writeInt(this.mCellularIdentifier);
        out.writeBoolean(this.mIsEmergency);
        out.writeString8(this.mPlmn);
    }

    public String toString() {
        return "CellularIdentifierDisclosure:{ mNasProtocolMessage = " + this.mNasProtocolMessage + " mCellularIdentifier = " + this.mCellularIdentifier + " mIsEmergency = " + this.mIsEmergency + " mPlmn = " + this.mPlmn;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CellularIdentifierDisclosure)) {
            return false;
        }
        CellularIdentifierDisclosure that = (CellularIdentifierDisclosure) o;
        return this.mNasProtocolMessage == that.mNasProtocolMessage && this.mCellularIdentifier == that.mCellularIdentifier && this.mIsEmergency == that.mIsEmergency && this.mPlmn.equals(that.mPlmn);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mNasProtocolMessage), Integer.valueOf(this.mCellularIdentifier), Boolean.valueOf(this.mIsEmergency), this.mPlmn);
    }

    private void readFromParcel(Parcel in) {
        this.mNasProtocolMessage = in.readInt();
        this.mCellularIdentifier = in.readInt();
        this.mIsEmergency = in.readBoolean();
        this.mPlmn = in.readString8();
    }
}
