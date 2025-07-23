package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.AccessNetworkConstants;
import android.text.TextUtils;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class EmergencyRegistrationResult implements Parcelable {
    public static final Parcelable.Creator<EmergencyRegistrationResult> CREATOR = new Parcelable.Creator<EmergencyRegistrationResult>() { // from class: android.telephony.EmergencyRegistrationResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EmergencyRegistrationResult createFromParcel(Parcel parcel) {
            return new EmergencyRegistrationResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EmergencyRegistrationResult[] newArray(int i) {
            return new EmergencyRegistrationResult[i];
        }
    };
    private int mAccessNetworkType;
    private String mCountryIso;
    private int mDomain;
    private boolean mIsEmcBearerSupported;
    private boolean mIsVopsSupported;
    private String mMcc;
    private String mMnc;
    private int mNwProvidedEmc;
    private int mNwProvidedEmf;
    private int mRegState;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public EmergencyRegistrationResult(int i, int i2, int i3, boolean z, boolean z2, int i4, int i5, String str, String str2, String str3) {
        this.mAccessNetworkType = i;
        this.mRegState = i2;
        this.mDomain = i3;
        this.mIsVopsSupported = z;
        this.mIsEmcBearerSupported = z2;
        this.mNwProvidedEmc = i4;
        this.mNwProvidedEmf = i5;
        this.mMcc = str;
        this.mMnc = str2;
        this.mCountryIso = str3;
    }

    public EmergencyRegistrationResult(EmergencyRegistrationResult emergencyRegistrationResult) {
        this.mAccessNetworkType = emergencyRegistrationResult.mAccessNetworkType;
        this.mRegState = emergencyRegistrationResult.mRegState;
        this.mDomain = emergencyRegistrationResult.mDomain;
        this.mIsVopsSupported = emergencyRegistrationResult.mIsVopsSupported;
        this.mIsEmcBearerSupported = emergencyRegistrationResult.mIsEmcBearerSupported;
        this.mNwProvidedEmc = emergencyRegistrationResult.mNwProvidedEmc;
        this.mNwProvidedEmf = emergencyRegistrationResult.mNwProvidedEmf;
        this.mMcc = emergencyRegistrationResult.mMcc;
        this.mMnc = emergencyRegistrationResult.mMnc;
        this.mCountryIso = emergencyRegistrationResult.mCountryIso;
    }

    private EmergencyRegistrationResult(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getAccessNetwork() {
        return this.mAccessNetworkType;
    }

    public int getRegState() {
        return this.mRegState;
    }

    public int getDomain() {
        return this.mDomain;
    }

    public boolean isVopsSupported() {
        return this.mIsVopsSupported;
    }

    public boolean isEmcBearerSupported() {
        return this.mIsEmcBearerSupported;
    }

    public int getNwProvidedEmc() {
        return this.mNwProvidedEmc;
    }

    public int getNwProvidedEmf() {
        return this.mNwProvidedEmf;
    }

    public String getMcc() {
        return this.mMcc;
    }

    public String getMnc() {
        return this.mMnc;
    }

    public String getCountryIso() {
        return this.mCountryIso;
    }

    public String toString() {
        return "{ accessNetwork=" + AccessNetworkConstants.AccessNetworkType.toString(this.mAccessNetworkType) + ", regState=" + NetworkRegistrationInfo.registrationStateToString(this.mRegState) + ", domain=" + NetworkRegistrationInfo.domainToString(this.mDomain) + ", vops=" + this.mIsVopsSupported + ", emcBearer=" + this.mIsEmcBearerSupported + ", emc=" + this.mNwProvidedEmc + ", emf=" + this.mNwProvidedEmf + ", mcc=" + this.mMcc + ", mnc=" + this.mMnc + ", iso=" + this.mCountryIso + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            EmergencyRegistrationResult emergencyRegistrationResult = (EmergencyRegistrationResult) obj;
            if (this.mAccessNetworkType == emergencyRegistrationResult.mAccessNetworkType && this.mRegState == emergencyRegistrationResult.mRegState && this.mDomain == emergencyRegistrationResult.mDomain && this.mIsVopsSupported == emergencyRegistrationResult.mIsVopsSupported && this.mIsEmcBearerSupported == emergencyRegistrationResult.mIsEmcBearerSupported && this.mNwProvidedEmc == emergencyRegistrationResult.mNwProvidedEmc && this.mNwProvidedEmf == emergencyRegistrationResult.mNwProvidedEmf && TextUtils.equals(this.mMcc, emergencyRegistrationResult.mMcc) && TextUtils.equals(this.mMnc, emergencyRegistrationResult.mMnc) && TextUtils.equals(this.mCountryIso, emergencyRegistrationResult.mCountryIso)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mAccessNetworkType), Integer.valueOf(this.mRegState), Integer.valueOf(this.mDomain), Boolean.valueOf(this.mIsVopsSupported), Boolean.valueOf(this.mIsEmcBearerSupported), Integer.valueOf(this.mNwProvidedEmc), Integer.valueOf(this.mNwProvidedEmf), this.mMcc, this.mMnc, this.mCountryIso);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mAccessNetworkType);
        parcel.writeInt(this.mRegState);
        parcel.writeInt(this.mDomain);
        parcel.writeBoolean(this.mIsVopsSupported);
        parcel.writeBoolean(this.mIsEmcBearerSupported);
        parcel.writeInt(this.mNwProvidedEmc);
        parcel.writeInt(this.mNwProvidedEmf);
        parcel.writeString8(this.mMcc);
        parcel.writeString8(this.mMnc);
        parcel.writeString8(this.mCountryIso);
    }

    private void readFromParcel(Parcel parcel) {
        this.mAccessNetworkType = parcel.readInt();
        this.mRegState = parcel.readInt();
        this.mDomain = parcel.readInt();
        this.mIsVopsSupported = parcel.readBoolean();
        this.mIsEmcBearerSupported = parcel.readBoolean();
        this.mNwProvidedEmc = parcel.readInt();
        this.mNwProvidedEmf = parcel.readInt();
        this.mMcc = parcel.readString8();
        this.mMnc = parcel.readString8();
        this.mCountryIso = parcel.readString8();
    }
}
