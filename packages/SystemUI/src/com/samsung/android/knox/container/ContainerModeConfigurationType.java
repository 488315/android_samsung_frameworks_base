package com.samsung.android.knox.container;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;

/* loaded from: classes4.dex */
public class ContainerModeConfigurationType extends KnoxConfigurationType {
    public static final Parcelable.Creator<ContainerModeConfigurationType> CREATOR = new Parcelable.Creator<ContainerModeConfigurationType>() { // from class: com.samsung.android.knox.container.ContainerModeConfigurationType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContainerModeConfigurationType createFromParcel(Parcel parcel) {
            return new ContainerModeConfigurationType(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContainerModeConfigurationType[] newArray(int i) {
            Log.d(ContainerModeConfigurationType.TAG, "ContainerModeConfigurationType[] array to be created");
            return new ContainerModeConfigurationType[i];
        }
    };
    public static final String TAG = "ContainerModeConfigurationType";
    public boolean mAllowClearAllNotification;
    public boolean mAllowHomeKey;
    public boolean mAllowSettingsChanges;
    public boolean mAllowStatusBarExpansion;
    public boolean mHideSystemBar;
    public boolean mWipeRecentTasks;

    public ContainerModeConfigurationType() {
        this.mAllowSettingsChanges = true;
        this.mAllowStatusBarExpansion = true;
        this.mAllowHomeKey = true;
        this.mAllowClearAllNotification = false;
        this.mHideSystemBar = false;
        this.mWipeRecentTasks = false;
    }

    public void allowClearAllNotification(boolean z) {
        this.mAllowClearAllNotification = z;
    }

    public void allowHomeKey(boolean z) {
        this.mAllowHomeKey = z;
    }

    public void allowSettingsChanges(boolean z) {
        this.mAllowSettingsChanges = z;
    }

    public void allowStatusBarExpansion(boolean z) {
        this.mAllowStatusBarExpansion = z;
    }

    @Override // com.samsung.android.knox.container.KnoxConfigurationType, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.samsung.android.knox.container.KnoxConfigurationType
    public void dumpState() {
        Log.d(TAG, "COM config dump START:");
        Log.d(TAG, "mAllowSettingsChanges : " + this.mAllowSettingsChanges);
        Log.d(TAG, "mAllowStatusBarExpansion : " + this.mAllowStatusBarExpansion);
        Log.d(TAG, "mAllowHomeKey : " + this.mAllowHomeKey);
        Log.d(TAG, "mAllowClearAllNotification : " + this.mAllowClearAllNotification);
        Log.d(TAG, "mHideSystemBar : " + this.mHideSystemBar);
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("mWipeRecentTasks : "), this.mWipeRecentTasks, TAG);
        super.dumpState();
        Log.d(TAG, "COM config dump END.");
    }

    public boolean isClearAllNotificationAllowed() {
        return this.mAllowClearAllNotification;
    }

    public boolean isHideSystemBarEnabled() {
        return this.mHideSystemBar;
    }

    public boolean isHomeKeyAllowed() {
        return this.mAllowHomeKey;
    }

    public boolean isSettingChangesAllowed() {
        return this.mAllowSettingsChanges;
    }

    public boolean isStatusBarExpansionAllowed() {
        return this.mAllowStatusBarExpansion;
    }

    public boolean isWipeRecentTasksEnabled() {
        return this.mWipeRecentTasks;
    }

    public void setHideSystemBar(boolean z) {
        this.mHideSystemBar = z;
    }

    public void setWipeRecentTasks(boolean z) {
        this.mWipeRecentTasks = z;
    }

    @Override // com.samsung.android.knox.container.KnoxConfigurationType, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mAllowSettingsChanges ? 1 : 0);
        parcel.writeInt(this.mAllowStatusBarExpansion ? 1 : 0);
        parcel.writeInt(this.mAllowHomeKey ? 1 : 0);
        parcel.writeInt(this.mAllowClearAllNotification ? 1 : 0);
        parcel.writeInt(this.mHideSystemBar ? 1 : 0);
        parcel.writeInt(this.mWipeRecentTasks ? 1 : 0);
    }

    @Override // com.samsung.android.knox.container.KnoxConfigurationType
    public ContainerModeConfigurationType clone(String str) {
        if (str == null || str.isEmpty()) {
            Log.d(TAG, "clone(): name is either null or empty, hence returning null");
            return null;
        }
        ContainerModeConfigurationType containerModeConfigurationType = new ContainerModeConfigurationType();
        cloneConfiguration(containerModeConfigurationType, str);
        containerModeConfigurationType.allowSettingsChanges(this.mAllowSettingsChanges);
        containerModeConfigurationType.allowStatusBarExpansion(this.mAllowStatusBarExpansion);
        containerModeConfigurationType.allowHomeKey(this.mAllowHomeKey);
        containerModeConfigurationType.allowClearAllNotification(this.mAllowClearAllNotification);
        containerModeConfigurationType.setHideSystemBar(this.mHideSystemBar);
        containerModeConfigurationType.setWipeRecentTasks(this.mWipeRecentTasks);
        return containerModeConfigurationType;
    }

    public ContainerModeConfigurationType(Parcel parcel) {
        super(parcel);
        this.mAllowSettingsChanges = true;
        this.mAllowStatusBarExpansion = true;
        this.mAllowHomeKey = true;
        this.mAllowClearAllNotification = false;
        this.mHideSystemBar = false;
        this.mWipeRecentTasks = false;
        this.mAllowSettingsChanges = parcel.readInt() == 1;
        this.mAllowStatusBarExpansion = parcel.readInt() == 1;
        this.mAllowHomeKey = parcel.readInt() == 1;
        this.mAllowClearAllNotification = parcel.readInt() == 1;
        this.mHideSystemBar = parcel.readInt() == 1;
        this.mWipeRecentTasks = parcel.readInt() == 1;
    }
}
