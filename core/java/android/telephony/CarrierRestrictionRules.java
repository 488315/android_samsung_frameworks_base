package android.telephony;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.carrier.CarrierIdentifier;
import com.android.internal.telephony.flags.Flags;
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
        public CarrierRestrictionRules createFromParcel(Parcel in) {
            return new CarrierRestrictionRules(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CarrierRestrictionRules[] newArray(int size) {
            return new CarrierRestrictionRules[size];
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

    private CarrierRestrictionRules(Parcel in) {
        this.mAllowedCarriers = new ArrayList();
        this.mExcludedCarriers = new ArrayList();
        this.mAllowedCarrierInfo = new ArrayList();
        this.mExcludedCarrierInfo = new ArrayList();
        in.readTypedList(this.mAllowedCarriers, CarrierIdentifier.CREATOR);
        in.readTypedList(this.mExcludedCarriers, CarrierIdentifier.CREATOR);
        this.mCarrierRestrictionDefault = in.readInt();
        this.mMultiSimPolicy = in.readInt();
        this.mCarrierRestrictionStatus = in.readInt();
        if (Flags.carrierRestrictionRulesEnhancement()) {
            in.readTypedList(this.mAllowedCarrierInfo, CarrierInfo.CREATOR);
            in.readTypedList(this.mExcludedCarrierInfo, CarrierInfo.CREATOR);
            this.mUseCarrierLockInfo = in.readBoolean();
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

    public List<Boolean> areCarrierIdentifiersAllowed(List<CarrierIdentifier> carrierIds) {
        ArrayList<Boolean> result = new ArrayList<>(carrierIds.size());
        int i = 0;
        while (true) {
            if (i >= carrierIds.size()) {
                break;
            }
            boolean inAllowedList = isCarrierIdInList(carrierIds.get(i), this.mAllowedCarriers);
            boolean inExcludedList = isCarrierIdInList(carrierIds.get(i), this.mExcludedCarriers);
            if (this.mCarrierRestrictionDefault == 0) {
                result.add(Boolean.valueOf(inAllowedList && !inExcludedList));
            } else {
                if (inExcludedList && !inAllowedList) {
                    r3 = false;
                }
                result.add(Boolean.valueOf(r3));
            }
            i++;
        }
        int i2 = this.mMultiSimPolicy;
        if (i2 == 1) {
            Iterator<Boolean> it = result.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                boolean b = it.next().booleanValue();
                if (b) {
                    result.replaceAll(new UnaryOperator() { // from class: android.telephony.CarrierRestrictionRules$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return CarrierRestrictionRules.lambda$areCarrierIdentifiersAllowed$0((Boolean) obj);
                        }
                    });
                    break;
                }
            }
        }
        return result;
    }

    static /* synthetic */ Boolean lambda$areCarrierIdentifiersAllowed$0(Boolean x) {
        return true;
    }

    private static boolean isCarrierIdInList(CarrierIdentifier id, List<CarrierIdentifier> list) {
        for (CarrierIdentifier listItem : list) {
            if (patternMatch(id.getMcc(), listItem.getMcc()) && patternMatch(id.getMnc(), listItem.getMnc())) {
                String listItemValue = convertNullToEmpty(listItem.getSpn());
                String idValue = convertNullToEmpty(id.getSpn());
                if (listItemValue.isEmpty() || patternMatch(idValue, listItemValue)) {
                    String listItemValue2 = convertNullToEmpty(listItem.getImsi());
                    String idValue2 = convertNullToEmpty(id.getImsi());
                    if (patternMatch(idValue2.substring(0, Math.min(idValue2.length(), listItemValue2.length())), listItemValue2)) {
                        String listItemValue3 = convertNullToEmpty(listItem.getGid1());
                        String idValue3 = convertNullToEmpty(id.getGid1());
                        if (patternMatch(idValue3.substring(0, Math.min(idValue3.length(), listItemValue3.length())), listItemValue3)) {
                            String listItemValue4 = convertNullToEmpty(listItem.getGid2());
                            String idValue4 = convertNullToEmpty(id.getGid2());
                            if (patternMatch(idValue4.substring(0, Math.min(idValue4.length(), listItemValue4.length())), listItemValue4)) {
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

    private static String convertNullToEmpty(String value) {
        return Objects.toString(value, "");
    }

    private static boolean patternMatch(String str, String pattern) {
        if (str.length() != pattern.length()) {
            return false;
        }
        String lowerCaseStr = str.toLowerCase(Locale.ROOT);
        String lowerCasePattern = pattern.toLowerCase(Locale.ROOT);
        for (int i = 0; i < lowerCasePattern.length(); i++) {
            if (lowerCasePattern.charAt(i) != lowerCaseStr.charAt(i) && lowerCasePattern.charAt(i) != '?') {
                return false;
            }
        }
        return true;
    }

    public int getCarrierRestrictionStatus() {
        return this.mCarrierRestrictionStatus;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeTypedList(this.mAllowedCarriers);
        out.writeTypedList(this.mExcludedCarriers);
        out.writeInt(this.mCarrierRestrictionDefault);
        out.writeInt(this.mMultiSimPolicy);
        out.writeInt(this.mCarrierRestrictionStatus);
        if (Flags.carrierRestrictionRulesEnhancement()) {
            out.writeTypedList(this.mAllowedCarrierInfo);
            out.writeTypedList(this.mExcludedCarrierInfo);
            out.writeBoolean(this.mUseCarrierLockInfo);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "CarrierRestrictionRules(allowed:" + this.mAllowedCarriers + ", excluded:" + this.mExcludedCarriers + ", default:" + this.mCarrierRestrictionDefault + ", MultiSim policy:" + this.mMultiSimPolicy + getCarrierInfoList() + "  mIsCarrierLockInfoSupported = " + this.mUseCarrierLockInfo + NavigationBarInflaterView.KEY_CODE_END;
    }

    private String getCarrierInfoList() {
        if (Flags.carrierRestrictionRulesEnhancement()) {
            return ",  allowedCarrierInfoList:" + this.mAllowedCarrierInfo + ", excludedCarrierInfoList:" + this.mExcludedCarrierInfo;
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

        public Builder setAllowedCarriers(List<CarrierIdentifier> allowedCarriers) {
            this.mRules.mAllowedCarriers = new ArrayList(allowedCarriers);
            return this;
        }

        public Builder setExcludedCarriers(List<CarrierIdentifier> excludedCarriers) {
            this.mRules.mExcludedCarriers = new ArrayList(excludedCarriers);
            return this;
        }

        public Builder setDefaultCarrierRestriction(int carrierRestrictionDefault) {
            this.mRules.mCarrierRestrictionDefault = carrierRestrictionDefault;
            return this;
        }

        public Builder setMultiSimPolicy(int multiSimPolicy) {
            this.mRules.mMultiSimPolicy = multiSimPolicy;
            return this;
        }

        public Builder setCarrierRestrictionStatus(int carrierRestrictionStatus) {
            this.mRules.mCarrierRestrictionStatus = carrierRestrictionStatus;
            return this;
        }

        public Builder setAllowedCarrierInfo(List<CarrierInfo> allowedCarrierInfo) {
            this.mRules.mAllowedCarrierInfo = new ArrayList(allowedCarrierInfo);
            return this;
        }

        public Builder setExcludedCarrierInfo(List<CarrierInfo> excludedCarrierInfo) {
            this.mRules.mExcludedCarrierInfo = new ArrayList(excludedCarrierInfo);
            return this;
        }

        public Builder setCarrierLockInfoFeature(boolean carrierLockInfoSupported) {
            this.mRules.mUseCarrierLockInfo = carrierLockInfoSupported;
            return this;
        }
    }
}
