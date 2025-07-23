package com.samsung.android.knox.application;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ManagedAppInfo implements Parcelable {
    public static final Parcelable.Creator<ManagedAppInfo> CREATOR = new Parcelable.Creator<ManagedAppInfo>() { // from class: com.samsung.android.knox.application.ManagedAppInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ManagedAppInfo createFromParcel(Parcel parcel) {
            return new ManagedAppInfo(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ManagedAppInfo[] newArray(int i) {
            return new ManagedAppInfo[i];
        }
    };
    public int mAppDisabled;
    public int mAppInstallCount;
    public int mAppInstallationDisabled;
    public String mAppPkg;
    public int mAppUninstallCount;
    public int mAppUninstallationDisabled;
    public int mControlStateDisableCause;
    public String mControlStateDisableCauseMetadata;
    public int mIsEnterpriseApp;
    public int mIsInstallSourceMDM;

    public /* synthetic */ ManagedAppInfo(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.mAppPkg = parcel.readString();
        this.mAppInstallCount = parcel.readInt();
        this.mAppUninstallCount = parcel.readInt();
        this.mAppDisabled = parcel.readInt();
        this.mAppInstallationDisabled = parcel.readInt();
        this.mAppUninstallationDisabled = parcel.readInt();
        this.mControlStateDisableCause = parcel.readInt();
        this.mControlStateDisableCauseMetadata = parcel.readString();
        this.mIsEnterpriseApp = parcel.readInt();
        this.mIsInstallSourceMDM = parcel.readInt();
    }

    public String toString() {
        return "pkg: " + this.mAppPkg + " ,InstallCount: " + this.mAppInstallCount + ", UninstallCount: " + this.mAppUninstallCount + ", mAppDisabled: " + this.mAppDisabled + ", mAppInstallationDisabled: " + this.mAppInstallationDisabled + ", mAppUninstallationDisabled: " + this.mAppUninstallationDisabled;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mAppPkg);
        parcel.writeInt(this.mAppInstallCount);
        parcel.writeInt(this.mAppUninstallCount);
        parcel.writeInt(this.mAppDisabled);
        parcel.writeInt(this.mAppInstallationDisabled);
        parcel.writeInt(this.mAppUninstallationDisabled);
        parcel.writeInt(this.mControlStateDisableCause);
        parcel.writeString(this.mControlStateDisableCauseMetadata);
        parcel.writeInt(this.mIsEnterpriseApp);
        parcel.writeInt(this.mIsInstallSourceMDM);
    }

    public ManagedAppInfo() {
        this.mAppPkg = null;
        this.mAppInstallCount = -1;
        this.mAppUninstallCount = -1;
        this.mAppDisabled = -1;
        this.mAppInstallationDisabled = -1;
        this.mAppUninstallationDisabled = -1;
        this.mControlStateDisableCause = 0;
        this.mControlStateDisableCauseMetadata = null;
        this.mIsEnterpriseApp = -1;
        this.mIsInstallSourceMDM = -1;
    }

    private ManagedAppInfo(Parcel parcel) {
        this.mAppPkg = null;
        this.mAppInstallCount = -1;
        this.mAppUninstallCount = -1;
        this.mAppDisabled = -1;
        this.mAppInstallationDisabled = -1;
        this.mAppUninstallationDisabled = -1;
        this.mControlStateDisableCause = 0;
        this.mControlStateDisableCauseMetadata = null;
        this.mIsEnterpriseApp = -1;
        this.mIsInstallSourceMDM = -1;
        readFromParcel(parcel);
    }
}
