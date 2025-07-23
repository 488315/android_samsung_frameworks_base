package android.telephony;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.carrier.CarrierIdentifier;
import com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.Flags;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.UnaryOperator;

@SystemApi
/* loaded from: classes4.dex */
public final class CarrierRestrictionRules implements Parcelable {
    public static final int CARRIER_RESTRICTION_DEFAULT_ALLOWED = 1;
    public static final int CARRIER_RESTRICTION_DEFAULT_NOT_ALLOWED = 0;
    public static final Parcelable.Creator<CarrierRestrictionRules> CREATOR = new Parcelable.Creator<CarrierRestrictionRules>() { // from class: android.telephony.CarrierRestrictionRules.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CarrierRestrictionRules createFromParcel(Parcel parcel) {
            return new CarrierRestrictionRules(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CarrierRestrictionRules[] newArray(int i) {
            return new CarrierRestrictionRules[i];
        }
    };
    public static final int MULTISIM_POLICY_ACTIVE_SERVICE_ON_ANY_SLOT_TO_UNBLOCK_OTHER_SLOTS = 6;
    public static final int MULTISIM_POLICY_ACTIVE_SERVICE_ON_SLOT_1_TO_UNBLOCK_OTHER_SLOTS = 5;
    public static final int MULTISIM_POLICY_ALL_SIMS_MUST_BE_VALID = 7;
    public static final int MULTISIM_POLICY_APPLY_TO_ALL_SLOTS = 2;
    public static final int MULTISIM_POLICY_APPLY_TO_ONLY_SLOT_1 = 3;
    public static final int MULTISIM_POLICY_NONE = 0;
    public static final int MULTISIM_POLICY_ONE_VALID_SIM_MUST_BE_PRESENT = 1;
    public static final int MULTISIM_POLICY_SLOT_POLICY_OTHER = 8;
    public static final int MULTISIM_POLICY_VALID_SIM_MUST_PRESENT_ON_SLOT_1 = 4;
    private static final char WILD_CHARACTER = '?';
    private List<CarrierInfo> mAllowedCarrierInfo;
    private List<CarrierIdentifier> mAllowedCarriers;
    private int mCarrierRestrictionDefault;
    private int mCarrierRestrictionStatus;
    private List<CarrierInfo> mExcludedCarrierInfo;
    private List<CarrierIdentifier> mExcludedCarriers;
    private int mMultiSimPolicy;
    private boolean mUseCarrierLockInfo;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CarrierRestrictionDefault {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MultiSimPolicy {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private CarrierRestrictionRules() {
        this.mAllowedCarriers = new ArrayList();
        this.mExcludedCarriers = new ArrayList();
        this.mAllowedCarrierInfo = new ArrayList();
        this.mExcludedCarrierInfo = new ArrayList();
        this.mCarrierRestrictionDefault = 0;
        this.mMultiSimPolicy = 0;
        this.mCarrierRestrictionStatus = 0;
        this.mUseCarrierLockInfo = false;
    }

    private CarrierRestrictionRules(Parcel parcel) {
        this.mAllowedCarriers = new ArrayList();
        this.mExcludedCarriers = new ArrayList();
        this.mAllowedCarrierInfo = new ArrayList();
        this.mExcludedCarrierInfo = new ArrayList();
        parcel.readTypedList(this.mAllowedCarriers, CarrierIdentifier.CREATOR);
        parcel.readTypedList(this.mExcludedCarriers, CarrierIdentifier.CREATOR);
        this.mCarrierRestrictionDefault = parcel.readInt();
        this.mMultiSimPolicy = parcel.readInt();
        this.mCarrierRestrictionStatus = parcel.readInt();
        if (Flags.carrierRestrictionRulesEnhancement()) {
            parcel.readTypedList(this.mAllowedCarrierInfo, CarrierInfo.CREATOR);
            parcel.readTypedList(this.mExcludedCarrierInfo, CarrierInfo.CREATOR);
            this.mUseCarrierLockInfo = parcel.readBoolean();
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean isAllCarriersAllowed() {
        if (Flags.carrierRestrictionStatus() && this.mCarrierRestrictionStatus == 1) {
            return true;
        }
        return (Flags.carrierRestrictionRulesEnhancement() && this.mUseCarrierLockInfo) ? this.mAllowedCarrierInfo.isEmpty() && this.mExcludedCarrierInfo.isEmpty() && this.mCarrierRestrictionDefault == 1 : this.mAllowedCarriers.isEmpty() && this.mExcludedCarriers.isEmpty() && this.mCarrierRestrictionDefault == 1;
    }

    public List<CarrierIdentifier> getAllowedCarriers() {
        return this.mAllowedCarriers;
    }

    public List<CarrierIdentifier> getExcludedCarriers() {
        return this.mExcludedCarriers;
    }

    public List<CarrierInfo> getExcludedCarriersInfoList() {
        return this.mExcludedCarrierInfo;
    }

    public List<CarrierInfo> getAllowedCarriersInfoList() {
        return this.mAllowedCarrierInfo;
    }

    public int getDefaultCarrierRestriction() {
        return this.mCarrierRestrictionDefault;
    }

    public int getMultiSimPolicy() {
        return this.mMultiSimPolicy;
    }

    public List<Boolean> areCarrierIdentifiersAllowed(List<CarrierIdentifier> list) {
        ArrayList arrayList = new ArrayList(list.size());
        int i = 0;
        while (true) {
            if (i >= list.size()) {
                break;
            }
            boolean isCarrierIdInList = isCarrierIdInList(list.get(i), this.mAllowedCarriers);
            boolean isCarrierIdInList2 = isCarrierIdInList(list.get(i), this.mExcludedCarriers);
            if (this.mCarrierRestrictionDefault == 0) {
                arrayList.add(Boolean.valueOf(isCarrierIdInList && !isCarrierIdInList2));
            } else {
                if (isCarrierIdInList2 && !isCarrierIdInList) {
                    r4 = false;
                }
                arrayList.add(Boolean.valueOf(r4));
            }
            i++;
        }
        if (this.mMultiSimPolicy == 1) {
            Iterator it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (((Boolean) it.next()).booleanValue()) {
                    arrayList.replaceAll(new UnaryOperator() { // from class: android.telephony.CarrierRestrictionRules$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return CarrierRestrictionRules.lambda$areCarrierIdentifiersAllowed$0((Boolean) obj);
                        }
                    });
                    break;
                }
            }
        }
        return arrayList;
    }

    static /* synthetic */ Boolean lambda$areCarrierIdentifiersAllowed$0(Boolean bool) {
        return true;
    }

    private static boolean isCarrierIdInList(CarrierIdentifier carrierIdentifier, List<CarrierIdentifier> list) {
        for (CarrierIdentifier carrierIdentifier2 : list) {
            if (patternMatch(carrierIdentifier.getMcc(), carrierIdentifier2.getMcc()) && patternMatch(carrierIdentifier.getMnc(), carrierIdentifier2.getMnc())) {
                String convertNullToEmpty = convertNullToEmpty(carrierIdentifier2.getSpn());
                String convertNullToEmpty2 = convertNullToEmpty(carrierIdentifier.getSpn());
                if (convertNullToEmpty.isEmpty() || patternMatch(convertNullToEmpty2, convertNullToEmpty)) {
                    String convertNullToEmpty3 = convertNullToEmpty(carrierIdentifier2.getImsi());
                    String convertNullToEmpty4 = convertNullToEmpty(carrierIdentifier.getImsi());
                    if (patternMatch(convertNullToEmpty4.substring(0, Math.min(convertNullToEmpty4.length(), convertNullToEmpty3.length())), convertNullToEmpty3)) {
                        String convertNullToEmpty5 = convertNullToEmpty(carrierIdentifier2.getGid1());
                        String convertNullToEmpty6 = convertNullToEmpty(carrierIdentifier.getGid1());
                        if (patternMatch(convertNullToEmpty6.substring(0, Math.min(convertNullToEmpty6.length(), convertNullToEmpty5.length())), convertNullToEmpty5)) {
                            String convertNullToEmpty7 = convertNullToEmpty(carrierIdentifier2.getGid2());
                            String convertNullToEmpty8 = convertNullToEmpty(carrierIdentifier.getGid2());
                            if (patternMatch(convertNullToEmpty8.substring(0, Math.min(convertNullToEmpty8.length(), convertNullToEmpty7.length())), convertNullToEmpty7)) {
                                return true;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        return false;
    }

    private static String convertNullToEmpty(String str) {
        return Objects.toString(str, "");
    }

    private static boolean patternMatch(String str, String str2) {
        if (str.length() != str2.length()) {
            return false;
        }
        String lowerCase = str.toLowerCase(Locale.ROOT);
        String lowerCase2 = str2.toLowerCase(Locale.ROOT);
        for (int i = 0; i < lowerCase2.length(); i++) {
            if (lowerCase2.charAt(i) != lowerCase.charAt(i) && lowerCase2.charAt(i) != '?') {
                return false;
            }
        }
        return true;
    }

    public int getCarrierRestrictionStatus() {
        return this.mCarrierRestrictionStatus;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mAllowedCarriers);
        parcel.writeTypedList(this.mExcludedCarriers);
        parcel.writeInt(this.mCarrierRestrictionDefault);
        parcel.writeInt(this.mMultiSimPolicy);
        parcel.writeInt(this.mCarrierRestrictionStatus);
        if (Flags.carrierRestrictionRulesEnhancement()) {
            parcel.writeTypedList(this.mAllowedCarrierInfo);
            parcel.writeTypedList(this.mExcludedCarrierInfo);
            parcel.writeBoolean(this.mUseCarrierLockInfo);
        }
    }

    public String toString() {
        return "CarrierRestrictionRules(allowed:" + this.mAllowedCarriers + ", excluded:" + this.mExcludedCarriers + ", default:" + this.mCarrierRestrictionDefault + ", MultiSim policy:" + this.mMultiSimPolicy + getCarrierInfoList() + ", mIsCarrierLockInfoSupported = " + this.mUseCarrierLockInfo + getCarrierRestrictionStatusToLog() + NavigationBarInflaterView.KEY_CODE_END;
    }

    private String getCarrierInfoList() {
        if (Flags.carrierRestrictionRulesEnhancement()) {
            return ",  allowedCarrierInfoList:" + this.mAllowedCarrierInfo + ", excludedCarrierInfoList:" + this.mExcludedCarrierInfo;
        }
        return "";
    }

    private String getCarrierRestrictionStatusToLog() {
        if (Build.isDebuggable()) {
            return ", CarrierRestrictionStatus = " + this.mCarrierRestrictionStatus;
        }
        return "";
    }

    public static final class Builder {
        private final CarrierRestrictionRules mRules = new CarrierRestrictionRules();

        public CarrierRestrictionRules build() {
            return this.mRules;
        }

        public Builder setAllCarriersAllowed() {
            this.mRules.mAllowedCarriers.clear();
            this.mRules.mExcludedCarriers.clear();
            this.mRules.mCarrierRestrictionDefault = 1;
            if (Flags.carrierRestrictionRulesEnhancement()) {
                this.mRules.mCarrierRestrictionStatus = 1;
                this.mRules.mAllowedCarrierInfo.clear();
                this.mRules.mExcludedCarrierInfo.clear();
                this.mRules.mUseCarrierLockInfo = false;
            }
            return this;
        }

        public Builder setAllowedCarriers(List<CarrierIdentifier> list) {
            this.mRules.mAllowedCarriers = new ArrayList(list);
            return this;
        }

        public Builder setExcludedCarriers(List<CarrierIdentifier> list) {
            this.mRules.mExcludedCarriers = new ArrayList(list);
            return this;
        }

        public Builder setDefaultCarrierRestriction(int i) {
            this.mRules.mCarrierRestrictionDefault = i;
            return this;
        }

        public Builder setMultiSimPolicy(int i) {
            this.mRules.mMultiSimPolicy = i;
            return this;
        }

        public Builder setCarrierRestrictionStatus(int i) {
            this.mRules.mCarrierRestrictionStatus = i;
            return this;
        }

        public Builder setAllowedCarrierInfo(List<CarrierInfo> list) {
            this.mRules.mAllowedCarrierInfo = new ArrayList(list);
            return this;
        }

        public Builder setExcludedCarrierInfo(List<CarrierInfo> list) {
            this.mRules.mExcludedCarrierInfo = new ArrayList(list);
            return this;
        }

        public Builder setCarrierLockInfoFeature(boolean z) {
            this.mRules.mUseCarrierLockInfo = z;
            return this;
        }
    }
}
