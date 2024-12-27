package com.android.server.wm;

import com.android.server.wm.utils.OptPropFactory;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.LongSupplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AppCompatOrientationOverrides {
    public final ActivityRecord mActivityRecord;
    public final OptPropFactory.OptProp mAllowDisplayOrientationOverrideOptProp;
    public final OptPropFactory.OptProp mAllowIgnoringOrientationRequestWhenLoopDetectedOptProp;
    public final OptPropFactory.OptProp mAllowOrientationOverrideOptProp;
    public final OptPropFactory.OptProp mIgnoreRequestedOrientationOptProp;
    public final OrientationOverridesState mOrientationOverridesState;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OrientationOverridesState {
        static final int MIN_COUNT_TO_IGNORE_REQUEST_IN_LOOP = 2;
        static final int SET_ORIENTATION_REQUEST_COUNTER_TIMEOUT_MS = 1000;
        LongSupplier mCurrentTimeMillisSupplier;
        public final boolean mIsOverrideAnyOrientationEnabled;
        public final boolean mIsOverrideToNosensorOrientationEnabled;
        public final boolean mIsOverrideToPortraitOrientationEnabled;
        public final boolean mIsOverrideToReverseLandscapeOrientationEnabled;
        public boolean mIsRelaunchingAfterRequestedOrientationChanged;
        public long mTimeMsLastSetOrientationRequest = 0;
        public int mSetOrientationRequestCounter = 0;

        public OrientationOverridesState(
                ActivityRecord activityRecord,
                AppCompatOrientationOverrides$$ExternalSyntheticLambda0
                        appCompatOrientationOverrides$$ExternalSyntheticLambda0) {
            this.mCurrentTimeMillisSupplier =
                    appCompatOrientationOverrides$$ExternalSyntheticLambda0;
            this.mIsOverrideToNosensorOrientationEnabled =
                    activityRecord.info.isChangeEnabled(265451093L);
            this.mIsOverrideToPortraitOrientationEnabled =
                    activityRecord.info.isChangeEnabled(265452344L);
            this.mIsOverrideAnyOrientationEnabled = activityRecord.info.isChangeEnabled(265464455L);
            this.mIsOverrideToReverseLandscapeOrientationEnabled =
                    activityRecord.info.isChangeEnabled(266124927L);
        }
    }

    public AppCompatOrientationOverrides(
            ActivityRecord activityRecord,
            final AppCompatConfiguration appCompatConfiguration,
            OptPropFactory optPropFactory) {
        this.mActivityRecord = activityRecord;
        this.mOrientationOverridesState =
                new OrientationOverridesState(
                        activityRecord,
                        new AppCompatOrientationOverrides$$ExternalSyntheticLambda0());
        Objects.requireNonNull(appCompatConfiguration);
        final int i = 1;
        AppCompatUtils.AnonymousClass1 anonymousClass1 =
                new AppCompatUtils.AnonymousClass1(
                        new BooleanSupplier() { // from class:
                                                // com.android.server.wm.AppCompatOrientationOverrides$$ExternalSyntheticLambda2
                            @Override // java.util.function.BooleanSupplier
                            public final boolean getAsBoolean() {
                                int i2 = i;
                                Object obj = appCompatConfiguration;
                                switch (i2) {
                                    case 0:
                                        ActivityRecord activityRecord2 =
                                                ((AppCompatOrientationOverrides) obj)
                                                        .mActivityRecord;
                                        DisplayContent displayContent =
                                                activityRecord2.mDisplayContent;
                                        if (displayContent != null
                                                && activityRecord2.task != null
                                                && displayContent.getIgnoreOrientationRequest()
                                                && !activityRecord2.task.inMultiWindowMode()) {
                                            DisplayContent displayContent2 =
                                                    activityRecord2.mDisplayContent;
                                            if (displayContent2.mBaseDisplayWidth
                                                    > displayContent2.mBaseDisplayHeight) {
                                                return true;
                                            }
                                        }
                                        return false;
                                    default:
                                        return ((AppCompatConfiguration) obj)
                                                .mIsPolicyForIgnoringRequestedOrientationEnabled;
                                }
                            }
                        });
        this.mIgnoreRequestedOrientationOptProp =
                optPropFactory.create(
                        "android.window.PROPERTY_COMPAT_IGNORE_REQUESTED_ORIENTATION",
                        anonymousClass1);
        this.mAllowIgnoringOrientationRequestWhenLoopDetectedOptProp =
                optPropFactory.create(
                        "android.window.PROPERTY_COMPAT_ALLOW_IGNORING_ORIENTATION_REQUEST_WHEN_LOOP_DETECTED",
                        anonymousClass1);
        this.mAllowOrientationOverrideOptProp =
                optPropFactory.create("android.window.PROPERTY_COMPAT_ALLOW_ORIENTATION_OVERRIDE");
        final int i2 = 0;
        this.mAllowDisplayOrientationOverrideOptProp =
                optPropFactory.create(
                        "android.window.PROPERTY_COMPAT_ALLOW_DISPLAY_ORIENTATION_OVERRIDE",
                        new BooleanSupplier() { // from class:
                                                // com.android.server.wm.AppCompatOrientationOverrides$$ExternalSyntheticLambda2
                            @Override // java.util.function.BooleanSupplier
                            public final boolean getAsBoolean() {
                                int i22 = i2;
                                Object obj = this;
                                switch (i22) {
                                    case 0:
                                        ActivityRecord activityRecord2 =
                                                ((AppCompatOrientationOverrides) obj)
                                                        .mActivityRecord;
                                        DisplayContent displayContent =
                                                activityRecord2.mDisplayContent;
                                        if (displayContent != null
                                                && activityRecord2.task != null
                                                && displayContent.getIgnoreOrientationRequest()
                                                && !activityRecord2.task.inMultiWindowMode()) {
                                            DisplayContent displayContent2 =
                                                    activityRecord2.mDisplayContent;
                                            if (displayContent2.mBaseDisplayWidth
                                                    > displayContent2.mBaseDisplayHeight) {
                                                return true;
                                            }
                                        }
                                        return false;
                                    default:
                                        return ((AppCompatConfiguration) obj)
                                                .mIsPolicyForIgnoringRequestedOrientationEnabled;
                                }
                            }
                        });
    }

    public int getSetOrientationRequestCounter() {
        return this.mOrientationOverridesState.mSetOrientationRequestCounter;
    }

    public final boolean shouldIgnoreOrientationRequestLoop() {
        ActivityRecord activityRecord = this.mActivityRecord;
        if (!this.mAllowIgnoringOrientationRequestWhenLoopDetectedOptProp
                .shouldEnableWithOptInOverrideAndOptOutProperty(
                        activityRecord.info.isChangeEnabled(273509367L))) {
            return false;
        }
        OrientationOverridesState orientationOverridesState = this.mOrientationOverridesState;
        long asLong = orientationOverridesState.mCurrentTimeMillisSupplier.getAsLong();
        if (asLong - orientationOverridesState.mTimeMsLastSetOrientationRequest < 1000) {
            orientationOverridesState.mSetOrientationRequestCounter++;
        } else {
            orientationOverridesState.mSetOrientationRequestCounter = 0;
        }
        orientationOverridesState.mTimeMsLastSetOrientationRequest = asLong;
        return orientationOverridesState.mSetOrientationRequestCounter >= 2
                && !activityRecord.mAppCompatController.mAppCompatAspectRatioPolicy
                        .isLetterboxedForFixedOrientationAndAspectRatio();
    }
}
