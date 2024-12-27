package android.telephony.euicc;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.carrier.CarrierIdentifier;
import android.text.TextUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

@SystemApi
public final class EuiccRulesAuthTable implements Parcelable {
    public static final Parcelable.Creator<EuiccRulesAuthTable> CREATOR =
            new Parcelable.Creator<
                    EuiccRulesAuthTable>() { // from class:
                                             // android.telephony.euicc.EuiccRulesAuthTable.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public EuiccRulesAuthTable createFromParcel(Parcel source) {
                    return new EuiccRulesAuthTable(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public EuiccRulesAuthTable[] newArray(int size) {
                    return new EuiccRulesAuthTable[size];
                }
            };
    public static final int POLICY_RULE_FLAG_CONSENT_REQUIRED = 1;
    private final CarrierIdentifier[][] mCarrierIds;
    private final int[] mPolicyRuleFlags;
    private final int[] mPolicyRules;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PolicyRuleFlag {}

    public static final class Builder {
        private CarrierIdentifier[][] mCarrierIds;
        private int[] mPolicyRuleFlags;
        private int[] mPolicyRules;
        private int mPosition;

        public Builder(int ruleNum) {
            this.mPolicyRules = new int[ruleNum];
            this.mCarrierIds = new CarrierIdentifier[ruleNum][];
            this.mPolicyRuleFlags = new int[ruleNum];
        }

        public EuiccRulesAuthTable build() {
            if (this.mPosition != this.mPolicyRules.length) {
                throw new IllegalStateException(
                        "Not enough rules are added, expected: "
                                + this.mPolicyRules.length
                                + ", added: "
                                + this.mPosition);
            }
            return new EuiccRulesAuthTable(
                    this.mPolicyRules, this.mCarrierIds, this.mPolicyRuleFlags);
        }

        public Builder add(
                int policyRules, List<CarrierIdentifier> carrierId, int policyRuleFlags) {
            if (this.mPosition >= this.mPolicyRules.length) {
                throw new ArrayIndexOutOfBoundsException(this.mPosition);
            }
            this.mPolicyRules[this.mPosition] = policyRules;
            if (carrierId != null && carrierId.size() > 0) {
                this.mCarrierIds[this.mPosition] =
                        (CarrierIdentifier[])
                                carrierId.toArray(new CarrierIdentifier[carrierId.size()]);
            }
            this.mPolicyRuleFlags[this.mPosition] = policyRuleFlags;
            this.mPosition++;
            return this;
        }
    }

    public static boolean match(String mccRule, String mcc) {
        if (mccRule.length() < mcc.length()) {
            return false;
        }
        for (int i = 0; i < mccRule.length(); i++) {
            if (mccRule.charAt(i) != 'E'
                    && (i >= mcc.length() || mccRule.charAt(i) != mcc.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private EuiccRulesAuthTable(
            int[] policyRules, CarrierIdentifier[][] carrierIds, int[] policyRuleFlags) {
        this.mPolicyRules = policyRules;
        this.mCarrierIds = carrierIds;
        this.mPolicyRuleFlags = policyRuleFlags;
    }

    public int findIndex(int policy, CarrierIdentifier carrierId) {
        CarrierIdentifier[] carrierIds;
        for (int i = 0; i < this.mPolicyRules.length; i++) {
            if ((this.mPolicyRules[i] & policy) != 0
                    && (carrierIds = this.mCarrierIds[i]) != null
                    && carrierIds.length != 0) {
                for (CarrierIdentifier ruleCarrierId : carrierIds) {
                    if (match(ruleCarrierId.getMcc(), carrierId.getMcc())
                            && match(ruleCarrierId.getMnc(), carrierId.getMnc())) {
                        String gid = ruleCarrierId.getGid1();
                        if (TextUtils.isEmpty(gid) || gid.equals(carrierId.getGid1())) {
                            String gid2 = ruleCarrierId.getGid2();
                            if (TextUtils.isEmpty(gid2) || gid2.equals(carrierId.getGid2())) {
                                return i;
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }

    public boolean hasPolicyRuleFlag(int index, int flag) {
        if (index < 0 || index >= this.mPolicyRules.length) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return (this.mPolicyRuleFlags[index] & flag) != 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(this.mPolicyRules);
        for (CarrierIdentifier[] ids : this.mCarrierIds) {
            dest.writeTypedArray(ids, flags);
        }
        dest.writeIntArray(this.mPolicyRuleFlags);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EuiccRulesAuthTable that = (EuiccRulesAuthTable) obj;
        if (this.mCarrierIds.length != that.mCarrierIds.length) {
            return false;
        }
        for (int i = 0; i < this.mCarrierIds.length; i++) {
            CarrierIdentifier[] carrierIds = this.mCarrierIds[i];
            CarrierIdentifier[] thatCarrierIds = that.mCarrierIds[i];
            if (carrierIds != null && thatCarrierIds != null) {
                if (carrierIds.length != thatCarrierIds.length) {
                    return false;
                }
                for (int j = 0; j < carrierIds.length; j++) {
                    if (!carrierIds[j].equals(thatCarrierIds[j])) {
                        return false;
                    }
                }
            } else if (carrierIds != null || thatCarrierIds != null) {
                return false;
            }
        }
        if (Arrays.equals(this.mPolicyRules, that.mPolicyRules)
                && Arrays.equals(this.mPolicyRuleFlags, that.mPolicyRuleFlags)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = Arrays.hashCode(this.mPolicyRules);
        int result2 = (result * 31) + Arrays.hashCode(this.mPolicyRuleFlags);
        for (int i = 0; i < this.mCarrierIds.length; i++) {
            result2 = (result2 * 31) + Arrays.hashCode(this.mCarrierIds[i]);
        }
        return result2;
    }

    private EuiccRulesAuthTable(Parcel source) {
        this.mPolicyRules = source.createIntArray();
        int len = this.mPolicyRules.length;
        this.mCarrierIds = new CarrierIdentifier[len][];
        for (int i = 0; i < len; i++) {
            this.mCarrierIds[i] =
                    (CarrierIdentifier[]) source.createTypedArray(CarrierIdentifier.CREATOR);
        }
        this.mPolicyRuleFlags = source.createIntArray();
    }
}
