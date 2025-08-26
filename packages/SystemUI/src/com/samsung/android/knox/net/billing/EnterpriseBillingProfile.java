package com.samsung.android.knox.net.billing;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class EnterpriseBillingProfile implements Parcelable {
    public static Parcelable.Creator<EnterpriseBillingProfile> CREATOR = new Parcelable.Creator<EnterpriseBillingProfile>() { // from class: com.samsung.android.knox.net.billing.EnterpriseBillingProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnterpriseBillingProfile createFromParcel(Parcel parcel) {
            EnterpriseBillingProfile enterpriseBillingProfile = new EnterpriseBillingProfile(parcel.readString());
            enterpriseBillingProfile.addApnsToProfile(parcel.readArrayList(EnterpriseBillingProfile.class.getClassLoader()));
            return enterpriseBillingProfile;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnterpriseBillingProfile[] newArray(int i) {
            return new EnterpriseBillingProfile[i];
        }
    };
    public static final String VALID_REGEX_FOR_PROFILE_NAME = "[a-zA-Z_0-9]+";
    public final List<EnterpriseApn> apns;
    public final String profileName;

    public EnterpriseBillingProfile(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException();
        }
        this.profileName = str;
        this.apns = new ArrayList();
    }

    public void addApnToProfile(EnterpriseApn enterpriseApn) {
        if (enterpriseApn != null) {
            this.apns.add(enterpriseApn);
        }
    }

    public void addApnsToProfile(List<EnterpriseApn> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        this.apns.addAll(list);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public List<EnterpriseApn> getApnsFromProfile() {
        return this.apns;
    }

    public String getProfileName() {
        return this.profileName;
    }

    public boolean isProfileNameValid() {
        if (TextUtils.isEmpty(this.profileName)) {
            return false;
        }
        return this.profileName.matches(VALID_REGEX_FOR_PROFILE_NAME);
    }

    public boolean isProfileValid() {
        if (this.apns == null) {
            return false;
        }
        boolean z = !TextUtils.isEmpty(this.profileName);
        for (EnterpriseApn enterpriseApn : this.apns) {
            if (TextUtils.isEmpty(enterpriseApn.apn) || TextUtils.isEmpty(enterpriseApn.mcc) || TextUtils.isEmpty(enterpriseApn.mnc)) {
                return false;
            }
        }
        return z;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.profileName);
        parcel.writeList(this.apns);
    }
}
