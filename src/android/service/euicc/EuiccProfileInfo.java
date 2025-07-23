package android.service.euicc;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.carrier.CarrierIdentifier;
import android.telephony.SubscriptionInfo;
import android.telephony.UiccAccessRule;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class EuiccProfileInfo implements Parcelable {
    public static final Parcelable.Creator<EuiccProfileInfo> CREATOR = new Parcelable.Creator<EuiccProfileInfo>() { // from class: android.service.euicc.EuiccProfileInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EuiccProfileInfo createFromParcel(Parcel parcel) {
            return new EuiccProfileInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EuiccProfileInfo[] newArray(int i) {
            return new EuiccProfileInfo[i];
        }
    };
    public static final int POLICY_RULE_DELETE_AFTER_DISABLING = 4;
    public static final int POLICY_RULE_DO_NOT_DELETE = 2;
    public static final int POLICY_RULE_DO_NOT_DISABLE = 1;
    public static final int PROFILE_CLASS_OPERATIONAL = 2;
    public static final int PROFILE_CLASS_PROVISIONING = 1;
    public static final int PROFILE_CLASS_TESTING = 0;
    public static final int PROFILE_CLASS_UNSET = -1;
    public static final int PROFILE_STATE_DISABLED = 0;
    public static final int PROFILE_STATE_ENABLED = 1;
    public static final int PROFILE_STATE_UNSET = -1;
    private final UiccAccessRule[] mAccessRules;
    private final CarrierIdentifier mCarrierIdentifier;
    private final String mIccid;
    private final String mNickname;
    private final int mPolicyRules;
    private final int mProfileClass;
    private final String mProfileName;
    private final String mServiceProviderName;
    private final int mState;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PolicyRule {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProfileClass {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProfileState {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public EuiccProfileInfo(String str, UiccAccessRule[] uiccAccessRuleArr, String str2) {
        if (!TextUtils.isDigitsOnly(str)) {
            throw new IllegalArgumentException("iccid contains invalid characters: " + str);
        }
        this.mIccid = str;
        this.mAccessRules = uiccAccessRuleArr;
        this.mNickname = str2;
        this.mServiceProviderName = null;
        this.mProfileName = null;
        this.mProfileClass = -1;
        this.mState = -1;
        this.mCarrierIdentifier = null;
        this.mPolicyRules = 0;
    }

    private EuiccProfileInfo(Parcel parcel) {
        this.mIccid = parcel.readString();
        this.mNickname = parcel.readString();
        this.mServiceProviderName = parcel.readString();
        this.mProfileName = parcel.readString();
        this.mProfileClass = parcel.readInt();
        this.mState = parcel.readInt();
        if (parcel.readByte() == 1) {
            this.mCarrierIdentifier = CarrierIdentifier.CREATOR.createFromParcel(parcel);
        } else {
            this.mCarrierIdentifier = null;
        }
        this.mPolicyRules = parcel.readInt();
        this.mAccessRules = (UiccAccessRule[]) parcel.createTypedArray(UiccAccessRule.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mIccid);
        parcel.writeString(this.mNickname);
        parcel.writeString(this.mServiceProviderName);
        parcel.writeString(this.mProfileName);
        parcel.writeInt(this.mProfileClass);
        parcel.writeInt(this.mState);
        if (this.mCarrierIdentifier != null) {
            parcel.writeByte((byte) 1);
            this.mCarrierIdentifier.writeToParcel(parcel, i);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeInt(this.mPolicyRules);
        parcel.writeTypedArray(this.mAccessRules, i);
    }

    public static final class Builder {
        private List<UiccAccessRule> mAccessRules;
        private CarrierIdentifier mCarrierIdentifier;
        private String mIccid;
        private String mNickname;
        private int mPolicyRules;
        private int mProfileClass;
        private String mProfileName;
        private String mServiceProviderName;
        private int mState;

        public Builder(String str) {
            if (!TextUtils.isDigitsOnly(str)) {
                throw new IllegalArgumentException("iccid contains invalid characters: " + str);
            }
            this.mIccid = str;
        }

        public Builder(EuiccProfileInfo euiccProfileInfo) {
            List<UiccAccessRule> asList;
            this.mIccid = euiccProfileInfo.mIccid;
            this.mNickname = euiccProfileInfo.mNickname;
            this.mServiceProviderName = euiccProfileInfo.mServiceProviderName;
            this.mProfileName = euiccProfileInfo.mProfileName;
            this.mProfileClass = euiccProfileInfo.mProfileClass;
            this.mState = euiccProfileInfo.mState;
            this.mCarrierIdentifier = euiccProfileInfo.mCarrierIdentifier;
            this.mPolicyRules = euiccProfileInfo.mPolicyRules;
            if (euiccProfileInfo.mAccessRules == null) {
                asList = Collections.EMPTY_LIST;
            } else {
                asList = Arrays.asList(euiccProfileInfo.mAccessRules);
            }
            this.mAccessRules = asList;
        }

        public EuiccProfileInfo build() {
            if (this.mIccid == null) {
                throw new IllegalStateException("ICCID must be set for a profile.");
            }
            return new EuiccProfileInfo(this.mIccid, this.mNickname, this.mServiceProviderName, this.mProfileName, this.mProfileClass, this.mState, this.mCarrierIdentifier, this.mPolicyRules, this.mAccessRules);
        }

        public Builder setIccid(String str) {
            if (!TextUtils.isDigitsOnly(str)) {
                throw new IllegalArgumentException("iccid contains invalid characters: " + str);
            }
            this.mIccid = str;
            return this;
        }

        public Builder setNickname(String str) {
            this.mNickname = str;
            return this;
        }

        public Builder setServiceProviderName(String str) {
            this.mServiceProviderName = str;
            return this;
        }

        public Builder setProfileName(String str) {
            this.mProfileName = str;
            return this;
        }

        public Builder setProfileClass(int i) {
            this.mProfileClass = i;
            return this;
        }

        public Builder setState(int i) {
            this.mState = i;
            return this;
        }

        public Builder setCarrierIdentifier(CarrierIdentifier carrierIdentifier) {
            this.mCarrierIdentifier = carrierIdentifier;
            return this;
        }

        public Builder setPolicyRules(int i) {
            this.mPolicyRules = i;
            return this;
        }

        public Builder setUiccAccessRule(List<UiccAccessRule> list) {
            this.mAccessRules = list;
            return this;
        }
    }

    private EuiccProfileInfo(String str, String str2, String str3, String str4, int i, int i2, CarrierIdentifier carrierIdentifier, int i3, List<UiccAccessRule> list) {
        this.mIccid = str;
        this.mNickname = str2;
        this.mServiceProviderName = str3;
        this.mProfileName = str4;
        this.mProfileClass = i;
        this.mState = i2;
        this.mCarrierIdentifier = carrierIdentifier;
        this.mPolicyRules = i3;
        if (list != null && list.size() > 0) {
            this.mAccessRules = (UiccAccessRule[]) list.toArray(new UiccAccessRule[list.size()]);
        } else {
            this.mAccessRules = null;
        }
    }

    public String getIccid() {
        return this.mIccid;
    }

    public List<UiccAccessRule> getUiccAccessRules() {
        UiccAccessRule[] uiccAccessRuleArr = this.mAccessRules;
        if (uiccAccessRuleArr == null) {
            return null;
        }
        return Arrays.asList(uiccAccessRuleArr);
    }

    public String getNickname() {
        return this.mNickname;
    }

    public String getServiceProviderName() {
        return this.mServiceProviderName;
    }

    public String getProfileName() {
        return this.mProfileName;
    }

    public int getProfileClass() {
        return this.mProfileClass;
    }

    public int getState() {
        return this.mState;
    }

    public CarrierIdentifier getCarrierIdentifier() {
        return this.mCarrierIdentifier;
    }

    public int getPolicyRules() {
        return this.mPolicyRules;
    }

    public boolean hasPolicyRules() {
        return this.mPolicyRules != 0;
    }

    public boolean hasPolicyRule(int i) {
        return (this.mPolicyRules & i) != 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            EuiccProfileInfo euiccProfileInfo = (EuiccProfileInfo) obj;
            if (Objects.equals(this.mIccid, euiccProfileInfo.mIccid) && Objects.equals(this.mNickname, euiccProfileInfo.mNickname) && Objects.equals(this.mServiceProviderName, euiccProfileInfo.mServiceProviderName) && Objects.equals(this.mProfileName, euiccProfileInfo.mProfileName) && this.mProfileClass == euiccProfileInfo.mProfileClass && this.mState == euiccProfileInfo.mState && Objects.equals(this.mCarrierIdentifier, euiccProfileInfo.mCarrierIdentifier) && this.mPolicyRules == euiccProfileInfo.mPolicyRules && Arrays.equals(this.mAccessRules, euiccProfileInfo.mAccessRules)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((((Objects.hashCode(this.mIccid) + 31) * 31) + Objects.hashCode(this.mNickname)) * 31) + Objects.hashCode(this.mServiceProviderName)) * 31) + Objects.hashCode(this.mProfileName)) * 31) + this.mProfileClass) * 31) + this.mState) * 31) + Objects.hashCode(this.mCarrierIdentifier)) * 31) + this.mPolicyRules) * 31) + Arrays.hashCode(this.mAccessRules);
    }

    public String toString() {
        return "EuiccProfileInfo (nickname=" + this.mNickname + ", serviceProviderName=" + this.mServiceProviderName + ", profileName=" + this.mProfileName + ", profileClass=" + this.mProfileClass + ", state=" + this.mState + ", CarrierIdentifier=" + this.mCarrierIdentifier + ", policyRules=" + this.mPolicyRules + ", accessRules=" + Arrays.toString(this.mAccessRules) + ", iccid=" + SubscriptionInfo.getPrintableId(this.mIccid) + NavigationBarInflaterView.KEY_CODE_END;
    }
}
