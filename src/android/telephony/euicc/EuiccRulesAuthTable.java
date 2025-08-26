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
/* loaded from: classes4.dex */
public final class EuiccRulesAuthTable implements Parcelable {
    public static final Parcelable.Creator<EuiccRulesAuthTable> CREATOR = new Parcelable.Creator<EuiccRulesAuthTable>() { // from class: android.telephony.euicc.EuiccRulesAuthTable.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EuiccRulesAuthTable createFromParcel(Parcel parcel) {
            return new EuiccRulesAuthTable(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EuiccRulesAuthTable[] newArray(int i) {
            return new EuiccRulesAuthTable[i];
        }
    };
    public static final int POLICY_RULE_FLAG_CONSENT_REQUIRED = 1;
    private final CarrierIdentifier[][] mCarrierIds;
    private final int[] mPolicyRuleFlags;
    private final int[] mPolicyRules;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PolicyRuleFlag {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private CarrierIdentifier[][] mCarrierIds;
        private int[] mPolicyRuleFlags;
        private int[] mPolicyRules;
        private int mPosition;

        public Builder(int i) {
            this.mPolicyRules = new int[i];
            this.mCarrierIds = new CarrierIdentifier[i][];
            this.mPolicyRuleFlags = new int[i];
        }

        public EuiccRulesAuthTable build() {
            if (this.mPosition != this.mPolicyRules.length) {
                throw new IllegalStateException("Not enough rules are added, expected: " + this.mPolicyRules.length + ", added: " + this.mPosition);
            }
            return new EuiccRulesAuthTable(this.mPolicyRules, this.mCarrierIds, this.mPolicyRuleFlags);
        }

        public Builder add(int i, List<CarrierIdentifier> list, int i2) {
            int i3 = this.mPosition;
            int[] iArr = this.mPolicyRules;
            if (i3 >= iArr.length) {
                throw new ArrayIndexOutOfBoundsException(this.mPosition);
            }
            iArr[i3] = i;
            if (list != null && list.size() > 0) {
                this.mCarrierIds[this.mPosition] = (CarrierIdentifier[]) list.toArray(new CarrierIdentifier[list.size()]);
            }
            int[] iArr2 = this.mPolicyRuleFlags;
            int i4 = this.mPosition;
            iArr2[i4] = i2;
            this.mPosition = i4 + 1;
            return this;
        }
    }

    public static boolean match(String str, String str2) {
        if (str.length() < str2.length()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != 'E' && (i >= str2.length() || str.charAt(i) != str2.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private EuiccRulesAuthTable(int[] iArr, CarrierIdentifier[][] carrierIdentifierArr, int[] iArr2) {
        this.mPolicyRules = iArr;
        this.mCarrierIds = carrierIdentifierArr;
        this.mPolicyRuleFlags = iArr2;
    }

    public int findIndex(int i, CarrierIdentifier carrierIdentifier) {
        CarrierIdentifier[] carrierIdentifierArr;
        int i2 = 0;
        loop0: while (true) {
            int[] iArr = this.mPolicyRules;
            if (i2 >= iArr.length) {
                return -1;
            }
            if ((iArr[i2] & i) != 0 && (carrierIdentifierArr = this.mCarrierIds[i2]) != null && carrierIdentifierArr.length != 0) {
                for (CarrierIdentifier carrierIdentifier2 : carrierIdentifierArr) {
                    if (match(carrierIdentifier2.getMcc(), carrierIdentifier.getMcc()) && match(carrierIdentifier2.getMnc(), carrierIdentifier.getMnc())) {
                        String gid1 = carrierIdentifier2.getGid1();
                        if (TextUtils.isEmpty(gid1) || gid1.equals(carrierIdentifier.getGid1())) {
                            String gid2 = carrierIdentifier2.getGid2();
                            if (TextUtils.isEmpty(gid2) || gid2.equals(carrierIdentifier.getGid2())) {
                                break loop0;
                            }
                        }
                    }
                }
            }
            i2++;
        }
        return i2;
    }

    public boolean hasPolicyRuleFlag(int i, int i2) {
        if (i < 0 || i >= this.mPolicyRules.length) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return (this.mPolicyRuleFlags[i] & i2) != 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(this.mPolicyRules);
        for (CarrierIdentifier[] carrierIdentifierArr : this.mCarrierIds) {
            parcel.writeTypedArray(carrierIdentifierArr, i);
        }
        parcel.writeIntArray(this.mPolicyRuleFlags);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            EuiccRulesAuthTable euiccRulesAuthTable = (EuiccRulesAuthTable) obj;
            if (this.mCarrierIds.length != euiccRulesAuthTable.mCarrierIds.length) {
                return false;
            }
            int i = 0;
            while (true) {
                CarrierIdentifier[][] carrierIdentifierArr = this.mCarrierIds;
                if (i < carrierIdentifierArr.length) {
                    CarrierIdentifier[] carrierIdentifierArr2 = carrierIdentifierArr[i];
                    CarrierIdentifier[] carrierIdentifierArr3 = euiccRulesAuthTable.mCarrierIds[i];
                    if (carrierIdentifierArr2 == null || carrierIdentifierArr3 == null) {
                        if (carrierIdentifierArr2 != null || carrierIdentifierArr3 != null) {
                            break;
                        }
                    } else {
                        if (carrierIdentifierArr2.length != carrierIdentifierArr3.length) {
                            return false;
                        }
                        for (int i2 = 0; i2 < carrierIdentifierArr2.length; i2++) {
                            if (!carrierIdentifierArr2[i2].equals(carrierIdentifierArr3[i2])) {
                                return false;
                            }
                        }
                    }
                    i++;
                } else if (Arrays.equals(this.mPolicyRules, euiccRulesAuthTable.mPolicyRules) && Arrays.equals(this.mPolicyRuleFlags, euiccRulesAuthTable.mPolicyRuleFlags)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public int hashCode() {
        int iHashCode = (Arrays.hashCode(this.mPolicyRules) * 31) + Arrays.hashCode(this.mPolicyRuleFlags);
        int i = 0;
        while (true) {
            CarrierIdentifier[][] carrierIdentifierArr = this.mCarrierIds;
            if (i >= carrierIdentifierArr.length) {
                return iHashCode;
            }
            iHashCode = (iHashCode * 31) + Arrays.hashCode(carrierIdentifierArr[i]);
            i++;
        }
    }

    private EuiccRulesAuthTable(Parcel parcel) {
        int[] iArrCreateIntArray = parcel.createIntArray();
        this.mPolicyRules = iArrCreateIntArray;
        int length = iArrCreateIntArray.length;
        this.mCarrierIds = new CarrierIdentifier[length][];
        for (int i = 0; i < length; i++) {
            this.mCarrierIds[i] = (CarrierIdentifier[]) parcel.createTypedArray(CarrierIdentifier.CREATOR);
        }
        this.mPolicyRuleFlags = parcel.createIntArray();
    }
}
