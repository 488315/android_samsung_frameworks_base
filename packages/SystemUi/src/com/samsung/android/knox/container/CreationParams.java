package com.samsung.android.knox.container;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CreationParams implements Parcelable {
    public static final Parcelable.Creator<CreationParams> CREATOR = new Parcelable.Creator<CreationParams>() { // from class: com.samsung.android.knox.container.CreationParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final CreationParams createFromParcel(Parcel parcel) {
            return new CreationParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final CreationParams[] newArray(int i) {
            return new CreationParams[i];
        }

        @Override // android.os.Parcelable.Creator
        public final CreationParams createFromParcel(Parcel parcel) {
            return new CreationParams(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final CreationParams[] newArray(int i) {
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
    public final int describeContents() {
        return 0;
    }

    public final String getAdminPackageName() {
        return this.mAdminPkgName;
    }

    public final String getConfigurationName() {
        return this.mConfigName;
    }

    public final String getPasswordResetToken() {
        return this.mPwdResetToken;
    }

    public final void setAdminPackageName(String str) {
        this.mAdminPkgName = str;
    }

    public final void setConfigurationName(String str) {
        this.mConfigName = str;
    }

    public final void setPasswordResetToken(String str) {
        this.mPwdResetToken = str;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
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
        String readString = parcel.readString();
        this.mConfigName = readString;
        if (readString != null && readString.isEmpty()) {
            this.mConfigName = null;
        }
        String readString2 = parcel.readString();
        this.mAdminPkgName = readString2;
        if (readString2 != null && readString2.isEmpty()) {
            this.mAdminPkgName = null;
        }
        String readString3 = parcel.readString();
        this.mPwdResetToken = readString3;
        if (readString3 == null || !readString3.isEmpty()) {
            return;
        }
        this.mPwdResetToken = null;
    }
}
