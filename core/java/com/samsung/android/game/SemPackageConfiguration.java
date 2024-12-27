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
    public static final Parcelable.Creator<SemPackageConfiguration> CREATOR =
            new Parcelable.Creator<
                    SemPackageConfiguration>() { // from class:
                                                 // com.samsung.android.game.SemPackageConfiguration.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemPackageConfiguration createFromParcel(Parcel in) {
                    return new SemPackageConfiguration(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemPackageConfiguration[] newArray(int size) {
                    return new SemPackageConfiguration[size];
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

    public SemPackageConfiguration(String packageName) {
        this.performancePolicyForSsrm = null;
        this.optimalAspectRatio = 1.7777778f;
        this.dynamicSurfaceScaling = 1.0f;
        this.category = -1;
        this.categoryByUser = -1;
        this.fillBlackSurfaceOnMargins = false;
        this.userId = null;
        this.packageName = packageName;
    }

    private SemPackageConfiguration(Parcel in) {
        this.performancePolicyForSsrm = null;
        this.optimalAspectRatio = 1.7777778f;
        this.dynamicSurfaceScaling = 1.0f;
        this.category = -1;
        this.categoryByUser = -1;
        this.fillBlackSurfaceOnMargins = false;
        this.userId = null;
        this.packageName = in.readString();
        this.performancePolicyForSsrm = in.readString();
        this.optimalAspectRatio = in.readFloat();
        this.dynamicSurfaceScaling = in.readFloat();
        this.category = in.readInt();
        this.categoryByUser = in.readInt();
        this.fillBlackSurfaceOnMargins = in.readBoolean();
        this.userId = in.readString();
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getPerformancePolicyForSsrm() {
        return this.performancePolicyForSsrm;
    }

    public void setPerformancePolicyForSsrm(String policy) {
        if (policy != null) {
            policy = policy.trim();
            if (!policy.startsWith("{") || !policy.endsWith("}")) {
                GmsLog.e(TAG, "setPerformancePolicyForSsrm(), invalid policy: " + policy);
                return;
            }
        }
        this.performancePolicyForSsrm = policy;
    }

    public float getOptimalAspectRatio() {
        return this.optimalAspectRatio;
    }

    public void setOptimalAspectRatio(float optimalAspectRatio) {
        if (optimalAspectRatio < 0.0f || 5.0f < optimalAspectRatio) {
            GmsLog.e(
                    TAG,
                    "setOptimalAspectRatio(), invalid optimalAspectRatio: " + optimalAspectRatio);
        } else {
            this.optimalAspectRatio = optimalAspectRatio;
        }
    }

    public float getDynamicSurfaceScaling() {
        return this.dynamicSurfaceScaling;
    }

    public void setDynamicSurfaceScaling(float dynamicSurfaceScaling) {
        if (dynamicSurfaceScaling < 0.0f || 1.0f < dynamicSurfaceScaling) {
            GmsLog.e(
                    TAG,
                    "setDynamicSurfaceScaling(), invalid dynamicSurfaceScaling: "
                            + dynamicSurfaceScaling);
        } else {
            this.dynamicSurfaceScaling = dynamicSurfaceScaling;
        }
    }

    public int getCategory() {
        return this.category;
    }

    public void setCategory(int category) {
        if (category < -1 || 10 < category) {
            GmsLog.e(TAG, "setCategory(), invalid category: " + category);
        } else {
            this.category = category;
        }
    }

    public int getCategoryByUser() {
        return this.categoryByUser;
    }

    public void setCategoryByUser(int category) {
        if (category < -1 || 10 < category) {
            GmsLog.e(TAG, "setCategoryByUser(), invalid category: " + category);
        } else {
            this.categoryByUser = category;
        }
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean shouldFillBlackSurfaceOnMargins() {
        return this.fillBlackSurfaceOnMargins;
    }

    public void fillBlackSurfaceOnMargins(boolean fillBlackSurfaceOnMargins) {
        this.fillBlackSurfaceOnMargins = fillBlackSurfaceOnMargins;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("packageName: ").append(getPackageName());
        sb.append(", performancePolicyForSsrm: ").append(getPerformancePolicyForSsrm());
        sb.append(", optimalAspectRatio: ").append(getOptimalAspectRatio());
        sb.append(", dynamicSurfaceScaling: ").append(getDynamicSurfaceScaling());
        sb.append(", category: ").append(getCategory());
        sb.append(", categoryByUser: ").append(getCategoryByUser());
        sb.append(", fillBlackSurfaceOnMargins: ").append(shouldFillBlackSurfaceOnMargins());
        sb.append(", userId: ").append(getUserId());
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageName);
        dest.writeString(this.performancePolicyForSsrm);
        dest.writeFloat(this.optimalAspectRatio);
        dest.writeFloat(this.dynamicSurfaceScaling);
        dest.writeInt(this.category);
        dest.writeInt(this.categoryByUser);
        dest.writeBoolean(this.fillBlackSurfaceOnMargins);
        dest.writeString(this.userId);
    }
}
