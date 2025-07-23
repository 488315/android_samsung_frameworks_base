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

    /* JADX WARN: Code restructure failed: missing block: B:35:0x004b, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean equals(java.lang.Object r9) {
        /*
            r8 = this;
            r0 = 1
            if (r8 != r9) goto L4
            return r0
        L4:
            r1 = 0
            if (r9 == 0) goto L61
            java.lang.Class r2 = r8.getClass()
            java.lang.Class r3 = r9.getClass()
            if (r2 == r3) goto L12
            goto L61
        L12:
            android.telephony.euicc.EuiccRulesAuthTable r9 = (android.telephony.euicc.EuiccRulesAuthTable) r9
            android.service.carrier.CarrierIdentifier[][] r2 = r8.mCarrierIds
            int r2 = r2.length
            android.service.carrier.CarrierIdentifier[][] r3 = r9.mCarrierIds
            int r3 = r3.length
            if (r2 == r3) goto L1d
            return r1
        L1d:
            r2 = r1
        L1e:
            android.service.carrier.CarrierIdentifier[][] r3 = r8.mCarrierIds
            int r4 = r3.length
            if (r2 >= r4) goto L4c
            r3 = r3[r2]
            android.service.carrier.CarrierIdentifier[][] r4 = r9.mCarrierIds
            r4 = r4[r2]
            if (r3 == 0) goto L44
            if (r4 == 0) goto L44
            int r5 = r3.length
            int r6 = r4.length
            if (r5 == r6) goto L32
            return r1
        L32:
            r5 = r1
        L33:
            int r6 = r3.length
            if (r5 >= r6) goto L48
            r6 = r3[r5]
            r7 = r4[r5]
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L41
            return r1
        L41:
            int r5 = r5 + 1
            goto L33
        L44:
            if (r3 != 0) goto L4b
            if (r4 != 0) goto L4b
        L48:
            int r2 = r2 + 1
            goto L1e
        L4b:
            return r1
        L4c:
            int[] r2 = r8.mPolicyRules
            int[] r3 = r9.mPolicyRules
            boolean r2 = java.util.Arrays.equals(r2, r3)
            if (r2 == 0) goto L61
            int[] r8 = r8.mPolicyRuleFlags
            int[] r9 = r9.mPolicyRuleFlags
            boolean r8 = java.util.Arrays.equals(r8, r9)
            if (r8 == 0) goto L61
            return r0
        L61:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.telephony.euicc.EuiccRulesAuthTable.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        int hashCode = (Arrays.hashCode(this.mPolicyRules) * 31) + Arrays.hashCode(this.mPolicyRuleFlags);
        int i = 0;
        while (true) {
            CarrierIdentifier[][] carrierIdentifierArr = this.mCarrierIds;
            if (i >= carrierIdentifierArr.length) {
                return hashCode;
            }
            hashCode = (hashCode * 31) + Arrays.hashCode(carrierIdentifierArr[i]);
            i++;
        }
    }

    private EuiccRulesAuthTable(Parcel parcel) {
        int[] createIntArray = parcel.createIntArray();
        this.mPolicyRules = createIntArray;
        int length = createIntArray.length;
        this.mCarrierIds = new CarrierIdentifier[length][];
        for (int i = 0; i < length; i++) {
            this.mCarrierIds[i] = (CarrierIdentifier[]) parcel.createTypedArray(CarrierIdentifier.CREATOR);
        }
        this.mPolicyRuleFlags = parcel.createIntArray();
    }
}
