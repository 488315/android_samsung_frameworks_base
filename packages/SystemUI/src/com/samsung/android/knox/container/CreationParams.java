package com.samsung.android.knox.container;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class CreationParams implements Parcelable {
    public static final Parcelable.Creator<CreationParams> CREATOR = new Parcelable.Creator<CreationParams>() { // from class: com.samsung.android.knox.container.CreationParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreationParams createFromParcel(Parcel parcel) {
            return new CreationParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreationParams[] newArray(int i) {
            return new CreationParams[i];
        }
    };
    public static final String TAG = "CreationParams";
    public String mAdminPkgName;
    public String mConfigName;
    public String mPwdResetToken;

    public CreationParams() {
        this.mConfigName = null;
        this.mAdminPkgName = null;
        this.mPwdResetToken = null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getAdminPackageName() {
        return this.mAdminPkgName;
    }

    public String getConfigurationName() {
        return this.mConfigName;
    }

    public String getPasswordResetToken() {
        return this.mPwdResetToken;
    }

    public void setAdminPackageName(String str) {
        this.mAdminPkgName = str;
    }

    public void setConfigurationName(String str) {
        this.mConfigName = str;
    }

    public void setPasswordResetToken(String str) {
        this.mPwdResetToken = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        String str = this.mConfigName;
        if (str != null) {
            parcel.writeString(str);
        } else {
            parcel.writeString("");
        }
        String str2 = this.mAdminPkgName;
        if (str2 != null) {
            parcel.writeString(str2);
        } else {
            parcel.writeString("");
        }
        String str3 = this.mPwdResetToken;
        if (str3 != null) {
            parcel.writeString(str3);
        } else {
            parcel.writeString("");
        }
    }

    public CreationParams(Parcel parcel) {
        this.mConfigName = null;
        this.mAdminPkgName = null;
        this.mPwdResetToken = null;
        String string = parcel.readString();
        this.mConfigName = string;
        if (string != null && string.isEmpty()) {
            this.mConfigName = null;
        }
        String string2 = parcel.readString();
        this.mAdminPkgName = string2;
        if (string2 != null && string2.isEmpty()) {
            this.mAdminPkgName = null;
        }
        String string3 = parcel.readString();
        this.mPwdResetToken = string3;
        if (string3 == null || !string3.isEmpty()) {
            return;
        }
        this.mPwdResetToken = null;
    }
}
