package com.samsung.android.knox.container;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ContainerCreationParams implements Parcelable {
    public static final Parcelable.Creator<ContainerCreationParams> CREATOR = new Parcelable.Creator<ContainerCreationParams>() { // from class: com.samsung.android.knox.container.ContainerCreationParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ContainerCreationParams createFromParcel(Parcel parcel) {
            return new ContainerCreationParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ContainerCreationParams[] newArray(int i) {
            return new ContainerCreationParams[i];
        }

        @Override // android.os.Parcelable.Creator
        public final ContainerCreationParams createFromParcel(Parcel parcel) {
            return new ContainerCreationParams(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final ContainerCreationParams[] newArray(int i) {
            return new ContainerCreationParams[i];
        }
    };
    public static final int LOCK_TYPE_FINGER_PRINT = 1;
    public static final int LOCK_TYPE_IRIS = 9;
    public static final int LOCK_TYPE_PASSWORD = 0;
    public static final int LOCK_TYPE_PATTERN = 3;
    public static final int LOCK_TYPE_PIN = 2;
    public static final int STATE_SETUP_POST_CREATE = 1;
    public static final int STATE_SETUP_PROGRESS = 0;
    public static final int STATE_WIZARD_EXIT_CLEAN = 2;
    public static final String TAG = "ContainerCreationParams";
    public boolean isMigrationFlow;
    public String mAdminParam;
    public boolean mAdminRemovable;
    public int mAdminUid;
    public String mBackupPin;
    public int mBiometricsInfo;
    public String mConfigurationName;
    public KnoxConfigurationType mConfigurationType;
    public int mContainerId;
    public int mCreatorUid;
    public String mFeatureType;
    public boolean mFingerprintPlus;
    public int mFlags;
    public boolean mIrisPlus;
    public int mLockType;
    public String mName;
    public HashMap<String, ArrayList<Object>> mPackagePoliciesMap;
    public String mPassword;
    public int mRequestId;
    public int mRequestState;
    public String mResetPwdKey;
    public boolean mRestoreSelected;

    public ContainerCreationParams() {
        this.mName = null;
        this.mPassword = null;
        this.mBackupPin = null;
        this.mAdminParam = null;
        this.mRequestState = 0;
        this.mRequestId = -1;
        this.mFlags = 0;
        this.mLockType = 0;
        this.mAdminUid = 0;
        this.mAdminRemovable = true;
        this.mCreatorUid = 0;
        this.mContainerId = 0;
        this.mFingerprintPlus = false;
        this.mRestoreSelected = false;
        this.mIrisPlus = false;
        this.mBiometricsInfo = 0;
        this.mConfigurationType = null;
        this.isMigrationFlow = false;
        this.mFeatureType = null;
        this.mConfigurationName = null;
        this.mPackagePoliciesMap = new HashMap<>();
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        Log.d(TAG, "getRequestId :" + this.mRequestId);
        Log.d(TAG, "getName :" + this.mName);
        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("getAdminUid :"), this.mAdminUid, TAG);
        return 0;
    }

    public final String getAdminParam() {
        return this.mAdminParam;
    }

    public final boolean getAdminRemovable() {
        return this.mAdminRemovable;
    }

    public final int getAdminUid() {
        return this.mAdminUid;
    }

    public final String getBackupPin() {
        return this.mBackupPin;
    }

    public final int getBiometricsInfo() {
        return this.mBiometricsInfo;
    }

    public final String getConfigurationName() {
        return this.mConfigurationName;
    }

    public final KnoxConfigurationType getConfigurationType() {
        return this.mConfigurationType;
    }

    public final int getContainerId() {
        return this.mContainerId;
    }

    public final int getCreatorUid() {
        return this.mCreatorUid;
    }

    public final String getFeatureType() {
        return this.mFeatureType;
    }

    public final boolean getFingerprintPlus() {
        return this.mFingerprintPlus;
    }

    public final int getFlags() {
        return this.mFlags;
    }

    public final boolean getIrisPlus() {
        return this.mIrisPlus;
    }

    public final boolean getIsMigrationFlow() {
        return this.isMigrationFlow;
    }

    public final int getLockType() {
        return this.mLockType;
    }

    public final String getName() {
        return this.mName;
    }

    public final HashMap<String, ArrayList<Object>> getPackagePoliciesMap() {
        return this.mPackagePoliciesMap;
    }

    public final String getPassword() {
        return this.mPassword;
    }

    public final int getRequestId() {
        return this.mRequestId;
    }

    public final int getRequestState() {
        return this.mRequestState;
    }

    public final String getResetPasswordKey() {
        return this.mResetPwdKey;
    }

    public final boolean getRestoreSelection() {
        return this.mRestoreSelected;
    }

    public final void setAdminParam(String str) {
        this.mAdminParam = str;
    }

    public final void setAdminRemovable(boolean z) {
        this.mAdminRemovable = z;
    }

    public final void setAdminUid(int i) {
        this.mAdminUid = i;
    }

    public final void setBackupPin(String str) {
        this.mBackupPin = str;
    }

    public final void setBiometricsInfo(int i) {
        this.mBiometricsInfo = i;
    }

    public final void setConfigurationName(String str) {
        this.mConfigurationName = str;
    }

    public final void setConfigurationType(KnoxConfigurationType knoxConfigurationType) {
        this.mConfigurationType = knoxConfigurationType;
    }

    public final void setContainerId(int i) {
        this.mContainerId = i;
    }

    public final void setCreatorUid(int i) {
        this.mCreatorUid = i;
    }

    public final void setFeatureType(String str) {
        this.mFeatureType = str;
    }

    public final void setFingerprintPlus(boolean z) {
        this.mFingerprintPlus = z;
    }

    public final void setFlags(int i) {
        this.mFlags = i;
    }

    public final void setIrisPlus(boolean z) {
        this.mIrisPlus = z;
    }

    public final void setIsMigrationFlow(boolean z) {
        this.isMigrationFlow = z;
    }

    public final void setLockType(int i) {
        this.mLockType = i;
    }

    public final void setName(String str) {
        this.mName = str;
    }

    public final void setPackagePoliciesMap(HashMap<String, ArrayList<Object>> hashMap) {
        this.mPackagePoliciesMap = hashMap;
    }

    public final void setPassword(String str) {
        this.mPassword = str;
    }

    public final void setRequestId(int i) {
        this.mRequestId = i;
    }

    public final void setRequestState(int i) {
        this.mRequestState = i;
    }

    public final void setResetPasswordKey(String str) {
        this.mResetPwdKey = str;
    }

    public final void setRestoreSelection(boolean z) {
        this.mRestoreSelected = z;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        String str = this.mName;
        if (str != null) {
            parcel.writeString(str);
        } else {
            parcel.writeString("");
        }
        String str2 = this.mPassword;
        if (str2 != null) {
            parcel.writeString(str2);
        } else {
            parcel.writeString("");
        }
        String str3 = this.mBackupPin;
        if (str3 != null) {
            parcel.writeString(str3);
        } else {
            parcel.writeString("");
        }
        String str4 = this.mAdminParam;
        if (str4 != null) {
            parcel.writeString(str4);
        } else {
            parcel.writeString("");
        }
        String str5 = this.mResetPwdKey;
        if (str5 != null) {
            parcel.writeString(str5);
        } else {
            parcel.writeString("");
        }
        String str6 = this.mConfigurationName;
        if (str6 != null) {
            parcel.writeString(str6);
        } else {
            parcel.writeString("");
        }
        parcel.writeInt(this.mRequestId);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mLockType);
        parcel.writeInt(this.mAdminUid);
        parcel.writeInt(this.mCreatorUid);
        parcel.writeInt(this.mContainerId);
        parcel.writeInt(this.mFingerprintPlus ? 1 : 0);
        parcel.writeInt(this.mRestoreSelected ? 1 : 0);
        parcel.writeInt(this.mIrisPlus ? 1 : 0);
        parcel.writeInt(this.mBiometricsInfo);
        parcel.writeParcelable(this.mConfigurationType, i);
        parcel.writeInt(this.isMigrationFlow ? 1 : 0);
        parcel.writeInt(this.mAdminRemovable ? 1 : 0);
        String str7 = this.mFeatureType;
        if (str7 != null) {
            parcel.writeString(str7);
        } else {
            parcel.writeString("");
        }
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public final ContainerCreationParams m2773clone() {
        ContainerCreationParams containerCreationParams = new ContainerCreationParams();
        containerCreationParams.mRequestId = this.mRequestId;
        containerCreationParams.mName = this.mName;
        containerCreationParams.mLockType = this.mLockType;
        containerCreationParams.mFingerprintPlus = this.mFingerprintPlus;
        containerCreationParams.mIrisPlus = this.mIrisPlus;
        containerCreationParams.mBiometricsInfo = this.mBiometricsInfo;
        containerCreationParams.mBackupPin = this.mBackupPin;
        containerCreationParams.mRestoreSelected = this.mRestoreSelected;
        containerCreationParams.isMigrationFlow = this.isMigrationFlow;
        return containerCreationParams;
    }

    public ContainerCreationParams(Parcel parcel) {
        this.mName = null;
        this.mPassword = null;
        this.mBackupPin = null;
        this.mAdminParam = null;
        this.mRequestState = 0;
        this.mRequestId = -1;
        this.mFlags = 0;
        this.mLockType = 0;
        this.mAdminUid = 0;
        this.mAdminRemovable = true;
        this.mCreatorUid = 0;
        this.mContainerId = 0;
        this.mFingerprintPlus = false;
        this.mRestoreSelected = false;
        this.mIrisPlus = false;
        this.mBiometricsInfo = 0;
        this.mConfigurationType = null;
        this.isMigrationFlow = false;
        this.mFeatureType = null;
        this.mConfigurationName = null;
        this.mPackagePoliciesMap = new HashMap<>();
        String readString = parcel.readString();
        this.mName = readString;
        if (readString != null && readString.isEmpty()) {
            this.mName = null;
        }
        String readString2 = parcel.readString();
        this.mPassword = readString2;
        if (readString2 != null && readString2.isEmpty()) {
            this.mPassword = null;
        }
        String readString3 = parcel.readString();
        this.mBackupPin = readString3;
        if (readString3 != null && readString3.isEmpty()) {
            this.mBackupPin = null;
        }
        String readString4 = parcel.readString();
        this.mAdminParam = readString4;
        if (readString4 != null && readString4.isEmpty()) {
            this.mAdminParam = null;
        }
        String readString5 = parcel.readString();
        this.mResetPwdKey = readString5;
        if (readString5 != null && readString5.isEmpty()) {
            this.mResetPwdKey = null;
        }
        String readString6 = parcel.readString();
        this.mConfigurationName = readString6;
        if (readString6 != null && readString6.isEmpty()) {
            this.mConfigurationName = null;
        }
        this.mRequestId = parcel.readInt();
        this.mFlags = parcel.readInt();
        this.mLockType = parcel.readInt();
        this.mAdminUid = parcel.readInt();
        this.mCreatorUid = parcel.readInt();
        this.mContainerId = parcel.readInt();
        this.mFingerprintPlus = parcel.readInt() == 1;
        this.mRestoreSelected = parcel.readInt() == 1;
        this.mIrisPlus = parcel.readInt() == 1;
        this.mBiometricsInfo = parcel.readInt();
        this.mConfigurationType = (KnoxConfigurationType) parcel.readParcelable(ContainerCreationParams.class.getClassLoader());
        this.isMigrationFlow = parcel.readInt() == 1;
        this.mAdminRemovable = parcel.readInt() == 1;
        String readString7 = parcel.readString();
        this.mFeatureType = readString7;
        if (readString7 == null || !readString7.isEmpty()) {
            return;
        }
        this.mFeatureType = null;
    }
}
