package com.samsung.android.knox.container;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class ContainerModeConfigurationType extends KnoxConfigurationType {
    public static final Parcelable.Creator<ContainerModeConfigurationType> CREATOR = new Parcelable.Creator<ContainerModeConfigurationType>() { // from class: com.samsung.android.knox.container.ContainerModeConfigurationType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ContainerModeConfigurationType createFromParcel(Parcel parcel) {
            return new ContainerModeConfigurationType(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final ContainerModeConfigurationType createFromParcel(Parcel parcel) {
            return new ContainerModeConfigurationType(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final ContainerModeConfigurationType[] newArray(int i) {
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

    public final void allowClearAllNotification(boolean z) {
        this.mAllowClearAllNotification = z;
    }

    public final void allowHomeKey(boolean z) {
        this.mAllowHomeKey = z;
    }

    public final void allowSettingsChanges(boolean z) {
        this.mAllowSettingsChanges = z;
    }

    public final void allowStatusBarExpansion(boolean z) {
        this.mAllowStatusBarExpansion = z;
    }

    @Override // com.samsung.android.knox.container.KnoxConfigurationType, android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // com.samsung.android.knox.container.KnoxConfigurationType
    public final void dumpState() {
        Log.d(TAG, "COM config dump START:");
        Log.d(TAG, "mAllowSettingsChanges : " + this.mAllowSettingsChanges);
        Log.d(TAG, "mAllowStatusBarExpansion : " + this.mAllowStatusBarExpansion);
        Log.d(TAG, "mAllowHomeKey : " + this.mAllowHomeKey);
        Log.d(TAG, "mAllowClearAllNotification : " + this.mAllowClearAllNotification);
        Log.d(TAG, "mHideSystemBar : " + this.mHideSystemBar);
        Log.d(TAG, "mWipeRecentTasks : " + this.mWipeRecentTasks);
        Log.d(TAG, "COM config dump END.");
    }

    public final boolean isClearAllNotificationAllowed() {
        return this.mAllowClearAllNotification;
    }

    public final boolean isHideSystemBarEnabled() {
        return this.mHideSystemBar;
    }

    public final boolean isHomeKeyAllowed() {
        return this.mAllowHomeKey;
    }

    public final boolean isSettingChangesAllowed() {
        return this.mAllowSettingsChanges;
    }

    public final boolean isStatusBarExpansionAllowed() {
        return this.mAllowStatusBarExpansion;
    }

    public final boolean isWipeRecentTasksEnabled() {
        return this.mWipeRecentTasks;
    }

    public final void setHideSystemBar(boolean z) {
        this.mHideSystemBar = z;
    }

    public final void setWipeRecentTasks(boolean z) {
        this.mWipeRecentTasks = z;
    }

    @Override // com.samsung.android.knox.container.KnoxConfigurationType, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mAllowSettingsChanges ? 1 : 0);
        parcel.writeInt(this.mAllowStatusBarExpansion ? 1 : 0);
        parcel.writeInt(this.mAllowHomeKey ? 1 : 0);
        parcel.writeInt(this.mAllowClearAllNotification ? 1 : 0);
        parcel.writeInt(this.mHideSystemBar ? 1 : 0);
        parcel.writeInt(this.mWipeRecentTasks ? 1 : 0);
    }

    @Override // com.samsung.android.knox.container.KnoxConfigurationType
    public final ContainerModeConfigurationType clone(String str) {
        if (str == null || str.isEmpty()) {
            Log.d(TAG, "clone(): name is either null or empty, hence returning null");
            return null;
        }
        ContainerModeConfigurationType containerModeConfigurationType = new ContainerModeConfigurationType();
        cloneConfiguration(containerModeConfigurationType, str);
        containerModeConfigurationType.mAllowSettingsChanges = this.mAllowSettingsChanges;
        containerModeConfigurationType.mAllowStatusBarExpansion = this.mAllowStatusBarExpansion;
        containerModeConfigurationType.mAllowHomeKey = this.mAllowHomeKey;
        containerModeConfigurationType.mAllowClearAllNotification = this.mAllowClearAllNotification;
        containerModeConfigurationType.mHideSystemBar = this.mHideSystemBar;
        containerModeConfigurationType.mWipeRecentTasks = this.mWipeRecentTasks;
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
