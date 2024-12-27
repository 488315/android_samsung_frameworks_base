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
        public EmergencyRegistrationResult createFromParcel(Parcel in) {
            return new EmergencyRegistrationResult(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EmergencyRegistrationResult[] newArray(int size) {
            return new EmergencyRegistrationResult[size];
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

    public EmergencyRegistrationResult(int accessNetwork, int regState, int domain, boolean isVopsSupported, boolean isEmcBearerSupported, int emc, int emf, String mcc, String mnc, String iso) {
        this.mAccessNetworkType = accessNetwork;
        this.mRegState = regState;
        this.mDomain = domain;
        this.mIsVopsSupported = isVopsSupported;
        this.mIsEmcBearerSupported = isEmcBearerSupported;
        this.mNwProvidedEmc = emc;
        this.mNwProvidedEmf = emf;
        this.mMcc = mcc;
        this.mMnc = mnc;
        this.mCountryIso = iso;
    }

    public EmergencyRegistrationResult(EmergencyRegistrationResult s) {
        this.mAccessNetworkType = s.mAccessNetworkType;
        this.mRegState = s.mRegState;
        this.mDomain = s.mDomain;
        this.mIsVopsSupported = s.mIsVopsSupported;
        this.mIsEmcBearerSupported = s.mIsEmcBearerSupported;
        this.mNwProvidedEmc = s.mNwProvidedEmc;
        this.mNwProvidedEmf = s.mNwProvidedEmf;
        this.mMcc = s.mMcc;
        this.mMnc = s.mMnc;
        this.mCountryIso = s.mCountryIso;
    }

    private EmergencyRegistrationResult(Parcel in) {
        readFromParcel(in);
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmergencyRegistrationResult that = (EmergencyRegistrationResult) o;
        if (this.mAccessNetworkType == that.mAccessNetworkType && this.mRegState == that.mRegState && this.mDomain == that.mDomain && this.mIsVopsSupported == that.mIsVopsSupported && this.mIsEmcBearerSupported == that.mIsEmcBearerSupported && this.mNwProvidedEmc == that.mNwProvidedEmc && this.mNwProvidedEmf == that.mNwProvidedEmf && TextUtils.equals(this.mMcc, that.mMcc) && TextUtils.equals(this.mMnc, that.mMnc) && TextUtils.equals(this.mCountryIso, that.mCountryIso)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mAccessNetworkType), Integer.valueOf(this.mRegState), Integer.valueOf(this.mDomain), Boolean.valueOf(this.mIsVopsSupported), Boolean.valueOf(this.mIsEmcBearerSupported), Integer.valueOf(this.mNwProvidedEmc), Integer.valueOf(this.mNwProvidedEmf), this.mMcc, this.mMnc, this.mCountryIso);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mAccessNetworkType);
        out.writeInt(this.mRegState);
        out.writeInt(this.mDomain);
        out.writeBoolean(this.mIsVopsSupported);
        out.writeBoolean(this.mIsEmcBearerSupported);
        out.writeInt(this.mNwProvidedEmc);
        out.writeInt(this.mNwProvidedEmf);
        out.writeString8(this.mMcc);
        out.writeString8(this.mMnc);
        out.writeString8(this.mCountryIso);
    }

    private void readFromParcel(Parcel in) {
        this.mAccessNetworkType = in.readInt();
        this.mRegState = in.readInt();
        this.mDomain = in.readInt();
        this.mIsVopsSupported = in.readBoolean();
        this.mIsEmcBearerSupported = in.readBoolean();
        this.mNwProvidedEmc = in.readInt();
        this.mNwProvidedEmf = in.readInt();
        this.mMcc = in.readString8();
        this.mMnc = in.readString8();
        this.mCountryIso = in.readString8();
    }
}
