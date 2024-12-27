package com.android.server.wm;

import android.annotation.SystemApi;
import android.app.ActivityOptions;
import android.app.TaskInfo;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes2.dex */
public interface ActivityInterceptorCallback {
    public static final int DREAM_MANAGER_ORDERED_ID = 4;
    public static final int FROZEN_PACKAGE_ORDERED_ID = 6;
    public static final int MAINLINE_FIRST_ORDERED_ID = 1000;
    public static final int MAINLINE_LAST_ORDERED_ID = 1001;
    public static final int MAINLINE_SDK_SANDBOX_ORDER_ID = 1001;
    public static final int PERMISSION_POLICY_ORDERED_ID = 1;
    public static final int PRODUCT_ORDERED_ID = 5;
    public static final int SYSTEM_FIRST_ORDERED_ID = 0;
    public static final int SYSTEM_LAST_ORDERED_ID = 6;
    public static final int VIRTUAL_DEVICE_SERVICE_ORDERED_ID = 3;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    @SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
    public final class ActivityInterceptResult {
        public final ActivityOptions mActivityOptions;
        public final boolean mActivityResolved;
        public final Intent mIntent;

        public ActivityInterceptResult(Intent intent, ActivityOptions activityOptions) {
            this(intent, activityOptions, false);
        }

        public ActivityInterceptResult(Intent intent, ActivityOptions activityOptions, boolean z) {
            this.mIntent = intent;
            this.mActivityOptions = activityOptions;
            this.mActivityResolved = z;
        }

        public ActivityOptions getActivityOptions() {
            return this.mActivityOptions;
        }

        public Intent getIntent() {
            return this.mIntent;
        }

        public boolean isActivityResolved() {
            return this.mActivityResolved;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    @SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
    public final class ActivityInterceptorInfo {
        public final ActivityInfo mActivityInfo;
        public final String mCallingFeatureId;
        public final String mCallingPackage;
        public final int mCallingPid;
        public final int mCallingUid;
        public final ActivityOptions mCheckedOptions;
        public final Runnable mClearOptionsAnimation;
        public final Intent mIntent;
        public final int mRealCallingPid;
        public final int mRealCallingUid;
        public final ResolveInfo mResolveInfo;
        public final String mResolvedType;
        public final int mUserId;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Builder {
            public final ActivityInfo mActivityInfo;
            public final int mCallingPid;
            public final int mCallingUid;
            public final Intent mIntent;
            public final int mRealCallingPid;
            public final int mRealCallingUid;
            public final ResolveInfo mResolveInfo;
            public String mResolvedType;
            public final int mUserId;
            public String mCallingPackage = null;
            public String mCallingFeatureId = null;
            public ActivityOptions mCheckedOptions = null;
            public Runnable mClearOptionsAnimation = null;

            public Builder(
                    int i,
                    int i2,
                    int i3,
                    int i4,
                    int i5,
                    Intent intent,
                    ResolveInfo resolveInfo,
                    ActivityInfo activityInfo) {
                this.mCallingUid = i;
                this.mCallingPid = i2;
                this.mRealCallingUid = i3;
                this.mRealCallingPid = i4;
                this.mUserId = i5;
                this.mIntent = intent;
                this.mResolveInfo = resolveInfo;
                this.mActivityInfo = activityInfo;
            }

            public ActivityInterceptorInfo build() {
                return new ActivityInterceptorInfo(this);
            }

            public Builder setCallingFeatureId(String str) {
                this.mCallingFeatureId = str;
                return this;
            }

            public Builder setCallingPackage(String str) {
                this.mCallingPackage = str;
                return this;
            }

            public Builder setCheckedOptions(ActivityOptions activityOptions) {
                this.mCheckedOptions = activityOptions;
                return this;
            }

            public Builder setClearOptionsAnimationRunnable(Runnable runnable) {
                this.mClearOptionsAnimation = runnable;
                return this;
            }

            public Builder setResolvedType(String str) {
                this.mResolvedType = str;
                return this;
            }
        }

        public ActivityInterceptorInfo(Builder builder) {
            this.mCallingUid = builder.mCallingUid;
            this.mCallingPid = builder.mCallingPid;
            this.mRealCallingUid = builder.mRealCallingUid;
            this.mRealCallingPid = builder.mRealCallingPid;
            this.mUserId = builder.mUserId;
            this.mIntent = builder.mIntent;
            this.mResolveInfo = builder.mResolveInfo;
            this.mActivityInfo = builder.mActivityInfo;
            this.mResolvedType = builder.mResolvedType;
            this.mCallingPackage = builder.mCallingPackage;
            this.mCallingFeatureId = builder.mCallingFeatureId;
            this.mCheckedOptions = builder.mCheckedOptions;
            this.mClearOptionsAnimation = builder.mClearOptionsAnimation;
        }

        public ActivityInfo getActivityInfo() {
            return this.mActivityInfo;
        }

        public String getCallingFeatureId() {
            return this.mCallingFeatureId;
        }

        public String getCallingPackage() {
            return this.mCallingPackage;
        }

        public int getCallingPid() {
            return this.mCallingPid;
        }

        public int getCallingUid() {
            return this.mCallingUid;
        }

        public ActivityOptions getCheckedOptions() {
            return this.mCheckedOptions;
        }

        public Runnable getClearOptionsAnimationRunnable() {
            return this.mClearOptionsAnimation;
        }

        public Intent getIntent() {
            return this.mIntent;
        }

        public int getRealCallingPid() {
            return this.mRealCallingPid;
        }

        public int getRealCallingUid() {
            return this.mRealCallingUid;
        }

        public ResolveInfo getResolveInfo() {
            return this.mResolveInfo;
        }

        public String getResolvedType() {
            return this.mResolvedType;
        }

        public int getUserId() {
            return this.mUserId;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    @Retention(RetentionPolicy.SOURCE)
    public @interface OrderedId {}

    static boolean isValidMainlineOrderId(int i) {
        return i >= 1000 && i <= 1001;
    }

    static boolean isValidOrderId(int i) {
        return isValidMainlineOrderId(i) || (i >= 0 && i <= 6);
    }

    default void onActivityLaunched(
            TaskInfo taskInfo,
            ActivityInfo activityInfo,
            ActivityInterceptorInfo activityInterceptorInfo) {}

    ActivityInterceptResult onInterceptActivityLaunch(
            ActivityInterceptorInfo activityInterceptorInfo);
}
