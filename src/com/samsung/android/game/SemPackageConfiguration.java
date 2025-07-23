package com.samsung.android.game;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class SemPackageConfiguration implements Parcelable {
    public static final int CATEGORY_GAME_NORMAL = 1;
    public static final int CATEGORY_NON_GAME_INTERNAL = 3;
    public static final int CATEGORY_NON_GAME_MANAGED = 10;
    public static final int CATEGORY_NON_GAME_NORMAL = 0;
    public static final int CATEGORY_NON_GAME_TUNABLE = 2;
    public static final int CATEGORY_UNDEFINED = -1;
    public static final Parcelable.Creator<SemPackageConfiguration> CREATOR = new Parcelable.Creator<SemPackageConfiguration>() { // from class: com.samsung.android.game.SemPackageConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemPackageConfiguration createFromParcel(Parcel parcel) {
            return new SemPackageConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemPackageConfiguration[] newArray(int i) {
            return new SemPackageConfiguration[i];
        }
    };
    private static final String TAG = "SemPackageConfiguration";
    private int category;
    private int categoryByUser;
    private float dynamicSurfaceScaling;
    private boolean fillBlackSurfaceOnMargins;
    private float optimalAspectRatio;
    private String packageName;
    private String performancePolicyForSsrm;
    private String userId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemPackageConfiguration(String str) {
        this.performancePolicyForSsrm = null;
        this.optimalAspectRatio = 1.7777778f;
        this.dynamicSurfaceScaling = 1.0f;
        this.category = -1;
        this.categoryByUser = -1;
        this.fillBlackSurfaceOnMargins = false;
        this.userId = null;
        this.packageName = str;
    }

    private SemPackageConfiguration(Parcel parcel) {
        this.performancePolicyForSsrm = null;
        this.optimalAspectRatio = 1.7777778f;
        this.dynamicSurfaceScaling = 1.0f;
        this.category = -1;
        this.categoryByUser = -1;
        this.fillBlackSurfaceOnMargins = false;
        this.userId = null;
        this.packageName = parcel.readString();
        this.performancePolicyForSsrm = parcel.readString();
        this.optimalAspectRatio = parcel.readFloat();
        this.dynamicSurfaceScaling = parcel.readFloat();
        this.category = parcel.readInt();
        this.categoryByUser = parcel.readInt();
        this.fillBlackSurfaceOnMargins = parcel.readBoolean();
        this.userId = parcel.readString();
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getPerformancePolicyForSsrm() {
        return this.performancePolicyForSsrm;
    }

    public void setPerformancePolicyForSsrm(String str) {
        if (str != null) {
            str = str.trim();
            if (!str.startsWith("{") || !str.endsWith("}")) {
                GmsLog.e(TAG, "setPerformancePolicyForSsrm(), invalid policy: " + str);
                return;
            }
        }
        this.performancePolicyForSsrm = str;
    }

    public float getOptimalAspectRatio() {
        return this.optimalAspectRatio;
    }

    public void setOptimalAspectRatio(float f) {
        if (f < 0.0f || 5.0f < f) {
            GmsLog.e(TAG, "setOptimalAspectRatio(), invalid optimalAspectRatio: " + f);
            return;
        }
        this.optimalAspectRatio = f;
    }

    public float getDynamicSurfaceScaling() {
        return this.dynamicSurfaceScaling;
    }

    public void setDynamicSurfaceScaling(float f) {
        if (f < 0.0f || 1.0f < f) {
            GmsLog.e(TAG, "setDynamicSurfaceScaling(), invalid dynamicSurfaceScaling: " + f);
            return;
        }
        this.dynamicSurfaceScaling = f;
    }

    public int getCategory() {
        return this.category;
    }

    public void setCategory(int i) {
        if (i < -1 || 10 < i) {
            GmsLog.e(TAG, "setCategory(), invalid category: " + i);
            return;
        }
        this.category = i;
    }

    public int getCategoryByUser() {
        return this.categoryByUser;
    }

    public void setCategoryByUser(int i) {
        if (i < -1 || 10 < i) {
            GmsLog.e(TAG, "setCategoryByUser(), invalid category: " + i);
            return;
        }
        this.categoryByUser = i;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public boolean shouldFillBlackSurfaceOnMargins() {
        return this.fillBlackSurfaceOnMargins;
    }

    public void fillBlackSurfaceOnMargins(boolean z) {
        this.fillBlackSurfaceOnMargins = z;
    }

    public String toString() {
        return "packageName: " + getPackageName() + ", performancePolicyForSsrm: " + getPerformancePolicyForSsrm() + ", optimalAspectRatio: " + getOptimalAspectRatio() + ", dynamicSurfaceScaling: " + getDynamicSurfaceScaling() + ", category: " + getCategory() + ", categoryByUser: " + getCategoryByUser() + ", fillBlackSurfaceOnMargins: " + shouldFillBlackSurfaceOnMargins() + ", userId: " + getUserId();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeString(this.performancePolicyForSsrm);
        parcel.writeFloat(this.optimalAspectRatio);
        parcel.writeFloat(this.dynamicSurfaceScaling);
        parcel.writeInt(this.category);
        parcel.writeInt(this.categoryByUser);
        parcel.writeBoolean(this.fillBlackSurfaceOnMargins);
        parcel.writeString(this.userId);
    }
}
