package com.samsung.android.knox.container;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class ContainerCreationParams implements Parcelable {
    public static final Parcelable.Creator<ContainerCreationParams> CREATOR = new Parcelable.Creator<ContainerCreationParams>() { // from class: com.samsung.android.knox.container.ContainerCreationParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContainerCreationParams createFromParcel(Parcel parcel) {
            return new ContainerCreationParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContainerCreationParams[] newArray(int i) {
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
    public int describeContents() {
        Log.d(TAG, "getRequestId :" + getRequestId());
        Log.d(TAG, "getName :" + getName());
        Log.d(TAG, "getAdminUid :" + getAdminUid());
        return 0;
    }

    public String getAdminParam() {
        return this.mAdminParam;
    }

    public boolean getAdminRemovable() {
        return this.mAdminRemovable;
    }

    public int getAdminUid() {
        return this.mAdminUid;
    }

    public String getBackupPin() {
        return this.mBackupPin;
    }

    public int getBiometricsInfo() {
        return this.mBiometricsInfo;
    }

    public String getConfigurationName() {
        return this.mConfigurationName;
    }

    public KnoxConfigurationType getConfigurationType() {
        return this.mConfigurationType;
    }

    public int getContainerId() {
        return this.mContainerId;
    }

    public int getCreatorUid() {
        return this.mCreatorUid;
    }

    public String getFeatureType() {
        return this.mFeatureType;
    }

    public boolean getFingerprintPlus() {
        return this.mFingerprintPlus;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public boolean getIrisPlus() {
        return this.mIrisPlus;
    }

    public boolean getIsMigrationFlow() {
        return this.isMigrationFlow;
    }

    public int getLockType() {
        return this.mLockType;
    }

    public String getName() {
        return this.mName;
    }

    public HashMap<String, ArrayList<Object>> getPackagePoliciesMap() {
        return this.mPackagePoliciesMap;
    }

    public String getPassword() {
        return this.mPassword;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public int getRequestState() {
        return this.mRequestState;
    }

    public String getResetPasswordKey() {
        return this.mResetPwdKey;
    }

    public boolean getRestoreSelection() {
        return this.mRestoreSelected;
    }

    public void setAdminParam(String str) {
        this.mAdminParam = str;
    }

    public void setAdminRemovable(boolean z) {
        this.mAdminRemovable = z;
    }

    public void setAdminUid(int i) {
        this.mAdminUid = i;
    }

    public void setBackupPin(String str) {
        this.mBackupPin = str;
    }

    public void setBiometricsInfo(int i) {
        this.mBiometricsInfo = i;
    }

    public void setConfigurationName(String str) {
        this.mConfigurationName = str;
    }

    public void setConfigurationType(KnoxConfigurationType knoxConfigurationType) {
        this.mConfigurationType = knoxConfigurationType;
    }

    public void setContainerId(int i) {
        this.mContainerId = i;
    }

    public void setCreatorUid(int i) {
        this.mCreatorUid = i;
    }

    public void setFeatureType(String str) {
        this.mFeatureType = str;
    }

    public void setFingerprintPlus(boolean z) {
        this.mFingerprintPlus = z;
    }

    public void setFlags(int i) {
        this.mFlags = i;
    }

    public void setIrisPlus(boolean z) {
        this.mIrisPlus = z;
    }

    public void setIsMigrationFlow(boolean z) {
        this.isMigrationFlow = z;
    }

    public void setLockType(int i) {
        this.mLockType = i;
    }

    public void setName(String str) {
        this.mName = str;
    }

    public void setPackagePoliciesMap(HashMap<String, ArrayList<Object>> map) {
        this.mPackagePoliciesMap = map;
    }

    public void setPassword(String str) {
        this.mPassword = str;
    }

    public void setRequestId(int i) {
        this.mRequestId = i;
    }

    public void setRequestState(int i) {
        this.mRequestState = i;
    }

    public void setResetPasswordKey(String str) {
        this.mResetPwdKey = str;
    }

    public void setRestoreSelection(boolean z) {
        this.mRestoreSelected = z;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
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
    public ContainerCreationParams m3289clone() {
        ContainerCreationParams containerCreationParams = new ContainerCreationParams();
        containerCreationParams.setRequestId(this.mRequestId);
        containerCreationParams.setName(this.mName);
        containerCreationParams.setLockType(this.mLockType);
        containerCreationParams.setFingerprintPlus(this.mFingerprintPlus);
        containerCreationParams.setIrisPlus(this.mIrisPlus);
        containerCreationParams.setBiometricsInfo(this.mBiometricsInfo);
        containerCreationParams.setBackupPin(this.mBackupPin);
        containerCreationParams.setRestoreSelection(this.mRestoreSelected);
        containerCreationParams.setIsMigrationFlow(this.isMigrationFlow);
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
        String string = parcel.readString();
        this.mName = string;
        if (string != null && string.isEmpty()) {
            this.mName = null;
        }
        String string2 = parcel.readString();
        this.mPassword = string2;
        if (string2 != null && string2.isEmpty()) {
            this.mPassword = null;
        }
        String string3 = parcel.readString();
        this.mBackupPin = string3;
        if (string3 != null && string3.isEmpty()) {
            this.mBackupPin = null;
        }
        String string4 = parcel.readString();
        this.mAdminParam = string4;
        if (string4 != null && string4.isEmpty()) {
            this.mAdminParam = null;
        }
        String string5 = parcel.readString();
        this.mResetPwdKey = string5;
        if (string5 != null && string5.isEmpty()) {
            this.mResetPwdKey = null;
        }
        String string6 = parcel.readString();
        this.mConfigurationName = string6;
        if (string6 != null && string6.isEmpty()) {
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
        this.mConfigurationType = (KnoxConfigurationType) parcel.readParcelable(getClass().getClassLoader());
        this.isMigrationFlow = parcel.readInt() == 1;
        this.mAdminRemovable = parcel.readInt() == 1;
        String string7 = parcel.readString();
        this.mFeatureType = string7;
        if (string7 == null || !string7.isEmpty()) {
            return;
        }
        this.mFeatureType = null;
    }
}
