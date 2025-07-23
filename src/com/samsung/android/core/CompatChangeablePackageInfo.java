package com.samsung.android.core;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class CompatChangeablePackageInfo implements Parcelable {
    public static final Parcelable.Creator<CompatChangeablePackageInfo> CREATOR = new Parcelable.Creator<CompatChangeablePackageInfo>() { // from class: com.samsung.android.core.CompatChangeablePackageInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompatChangeablePackageInfo createFromParcel(Parcel parcel) {
            return new CompatChangeablePackageInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CompatChangeablePackageInfo[] newArray(int i) {
            return new CompatChangeablePackageInfo[i];
        }
    };
    final boolean mHasGameCategory;
    final boolean mHasLauncherActivity;
    final boolean mIsActivityEmbeddingSplitsEnabled;
    final boolean mIsMinAspectRatioOverrideDisallowed;
    final boolean mIsOrientationOverrideDisallowed;
    final boolean mIsResizeableActivityOverrideDisallowed;
    final String mPackageName;
    final int mUid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private CompatChangeablePackageInfo(String str, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.mPackageName = str == null ? "" : str;
        this.mUid = i;
        this.mHasLauncherActivity = z;
        this.mHasGameCategory = z2;
        this.mIsResizeableActivityOverrideDisallowed = z3;
        this.mIsOrientationOverrideDisallowed = z4;
        this.mIsMinAspectRatioOverrideDisallowed = z5;
        this.mIsActivityEmbeddingSplitsEnabled = z6;
    }

    public CompatChangeablePackageInfo(Parcel parcel) {
        this(parcel.readString(), parcel.readInt(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mUid);
        parcel.writeBoolean(this.mHasLauncherActivity);
        parcel.writeBoolean(this.mHasGameCategory);
        parcel.writeBoolean(this.mIsResizeableActivityOverrideDisallowed);
        parcel.writeBoolean(this.mIsOrientationOverrideDisallowed);
        parcel.writeBoolean(this.mIsMinAspectRatioOverrideDisallowed);
        parcel.writeBoolean(this.mIsActivityEmbeddingSplitsEnabled);
    }

    public static class Builder {
        boolean mHasGameCategory;
        boolean mHasLauncherActivity;
        boolean mIsActivityEmbeddingSplitsEnabled;
        boolean mIsMinAspectRatioOverrideDisallowed;
        boolean mIsOrientationOverrideDisallowed;
        boolean mIsResizeableActivityOverrideDisallowed;
        String mPackageName;
        int mUid = -1;

        public Builder setPackageName(String str) {
            this.mPackageName = str;
            return this;
        }

        public Builder setUid(int i) {
            this.mUid = i;
            return this;
        }

        public Builder setHasLauncherActivity(boolean z) {
            this.mHasLauncherActivity = z;
            return this;
        }

        public Builder setHasGameCategory(boolean z) {
            this.mHasGameCategory = z;
            return this;
        }

        public Builder setIsResizeableActivityOverrideDisallowed(boolean z) {
            this.mIsResizeableActivityOverrideDisallowed = z;
            return this;
        }

        public Builder setIsOrientationOverrideDisallowed(boolean z) {
            this.mIsOrientationOverrideDisallowed = z;
            return this;
        }

        public Builder setIsMinAspectRatioOverrideDisallowed(boolean z) {
            this.mIsMinAspectRatioOverrideDisallowed = z;
            return this;
        }

        public Builder setIsActivityEmbeddingSplitsEnabled(boolean z) {
            this.mIsActivityEmbeddingSplitsEnabled = z;
            return this;
        }

        public CompatChangeablePackageInfo build() {
            return new CompatChangeablePackageInfo(this.mPackageName, this.mUid, this.mHasLauncherActivity, this.mHasGameCategory, this.mIsResizeableActivityOverrideDisallowed, this.mIsOrientationOverrideDisallowed, this.mIsMinAspectRatioOverrideDisallowed, this.mIsActivityEmbeddingSplitsEnabled);
        }
    }
}
