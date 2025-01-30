package com.samsung.android.knox.application;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ManagedAppInfo implements Parcelable {
    public static final Parcelable.Creator<ManagedAppInfo> CREATOR = new Parcelable.Creator<ManagedAppInfo>() { // from class: com.samsung.android.knox.application.ManagedAppInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ManagedAppInfo[] newArray(int i) {
            return new ManagedAppInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ManagedAppInfo createFromParcel(Parcel parcel) {
            return new ManagedAppInfo(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final ManagedAppInfo[] newArray(int i) {
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
    public final int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
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

    public final String toString() {
        return "pkg: " + this.mAppPkg + " ,InstallCount: " + this.mAppInstallCount + ", UninstallCount: " + this.mAppUninstallCount + ", mAppDisabled: " + this.mAppDisabled + ", mAppInstallationDisabled: " + this.mAppInstallationDisabled + ", mAppUninstallationDisabled: " + this.mAppUninstallationDisabled;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
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
