package com.android.server.wm;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TransparentPolicy {
    public static final RecentsAnimation$$ExternalSyntheticLambda2
            FIRST_OPAQUE_NOT_FINISHING_ACTIVITY_PREDICATE =
                    new RecentsAnimation$$ExternalSyntheticLambda2(0);
    public final ActivityRecord mActivityRecord;
    public final List mDestroyListeners = new ArrayList();
    public final TransparentPolicy$$ExternalSyntheticLambda0
            mIsTranslucentLetterboxingEnabledSupplier;
    public final TransparentPolicyState mTransparentPolicyState;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TransparentPolicyState {
        public final ActivityRecord mActivityRecord;
        public ActivityRecord mFirstOpaqueActivity;
        public AppCompatDisplayInsets mInheritedAppCompatDisplayInsets;
        public WindowContainerListener mLetterboxConfigListener;
        public int mInheritedOrientation = 0;
        public float mInheritedMinAspectRatio = FullScreenMagnificationGestureHandler.MAX_SCALE;
        public float mInheritedMaxAspectRatio = FullScreenMagnificationGestureHandler.MAX_SCALE;
        public int mInheritedAppCompatState = 0;

        /* renamed from: -$$Nest$mreset, reason: not valid java name */
        public static void m1071$$Nest$mreset(TransparentPolicyState transparentPolicyState) {
            WindowContainerListener windowContainerListener =
                    transparentPolicyState.mLetterboxConfigListener;
            if (windowContainerListener != null) {
                windowContainerListener.onRemoved();
            }
            transparentPolicyState.mLetterboxConfigListener = null;
            transparentPolicyState.mInheritedOrientation = 0;
            transparentPolicyState.mInheritedMinAspectRatio =
                    FullScreenMagnificationGestureHandler.MAX_SCALE;
            transparentPolicyState.mInheritedMaxAspectRatio =
                    FullScreenMagnificationGestureHandler.MAX_SCALE;
            transparentPolicyState.mInheritedAppCompatState = 0;
            transparentPolicyState.mInheritedAppCompatDisplayInsets = null;
            ActivityRecord activityRecord = transparentPolicyState.mFirstOpaqueActivity;
            if (activityRecord != null) {
                ((ArrayList)
                                activityRecord
                                        .mAppCompatController
                                        .mTransparentPolicy
                                        .mDestroyListeners)
                        .remove(
                                transparentPolicyState
                                        .mActivityRecord
                                        .mAppCompatController
                                        .mTransparentPolicy);
            }
            transparentPolicyState.mFirstOpaqueActivity = null;
        }

        public TransparentPolicyState(ActivityRecord activityRecord) {
            this.mActivityRecord = activityRecord;
        }

        public final Optional findOpaqueNotFinishingActivityBelow() {
            return (!isRunning() || this.mActivityRecord.task == null)
                    ? Optional.empty()
                    : Optional.ofNullable(this.mFirstOpaqueActivity);
        }

        public final void inheritFromOpaque(ActivityRecord activityRecord) {
            ActivityRecord activityRecord2 = this.mActivityRecord;
            if (activityRecord2.getMinAspectRatio()
                    != FullScreenMagnificationGestureHandler.MAX_SCALE) {
                this.mInheritedMinAspectRatio = activityRecord.getMinAspectRatio();
            }
            if (activityRecord2.getMaxAspectRatio()
                    != FullScreenMagnificationGestureHandler.MAX_SCALE) {
                this.mInheritedMaxAspectRatio = activityRecord.getMaxAspectRatio();
            }
            this.mInheritedOrientation = activityRecord.getRequestedConfigurationOrientation();
            this.mInheritedAppCompatState = activityRecord.getAppCompatState(false);
            this.mInheritedAppCompatDisplayInsets = activityRecord.getAppCompatDisplayInsets();
        }

        public final boolean isRunning() {
            ActivityRecord activityRecord;
            int overrideOrientation;
            return this.mLetterboxConfigListener != null
                    && ((overrideOrientation =
                                            (activityRecord = this.mActivityRecord)
                                                    .getOverrideOrientation())
                                    == -1
                            || !activityRecord.handlesOrientationChangeFromDescendant(
                                    overrideOrientation));
        }
    }

    public TransparentPolicy(
            ActivityRecord activityRecord, AppCompatConfiguration appCompatConfiguration) {
        this.mActivityRecord = activityRecord;
        Objects.requireNonNull(appCompatConfiguration);
        this.mIsTranslucentLetterboxingEnabledSupplier =
                new TransparentPolicy$$ExternalSyntheticLambda0(appCompatConfiguration);
        this.mTransparentPolicyState = new TransparentPolicyState(activityRecord);
    }

    public final void start() {
        if (this.mIsTranslucentLetterboxingEnabledSupplier.f$0.isTranslucentLetterboxingEnabled()) {
            ActivityRecord activityRecord = this.mActivityRecord;
            if (activityRecord.getParent() == null) {
                return;
            }
            TransparentPolicyState transparentPolicyState = this.mTransparentPolicyState;
            boolean isRunning = transparentPolicyState.isRunning();
            TransparentPolicyState.m1071$$Nest$mreset(transparentPolicyState);
            ActivityRecord activity =
                    activityRecord.task.getActivity(
                            FIRST_OPAQUE_NOT_FINISHING_ACTIVITY_PREDICATE,
                            activityRecord,
                            false,
                            true);
            if (!activityRecord.mPopOverState.mIsActivated
                    && activity != null
                    && !activity.isEmbedded()) {
                AppCompatSizeCompatModePolicy appCompatSizeCompatModePolicy =
                        activityRecord.mAppCompatController.mAppCompatSizeCompatModePolicy;
                if (activityRecord.task != null
                        && !activityRecord.occludesParent(true)
                        && !appCompatSizeCompatModePolicy
                                .hasAppCompatDisplayInsetsWithoutInheritance()) {
                    transparentPolicyState.mFirstOpaqueActivity = activity;
                    List list = activity.mAppCompatController.mTransparentPolicy.mDestroyListeners;
                    ActivityRecord activityRecord2 = transparentPolicyState.mActivityRecord;
                    ((ArrayList) list).add(activityRecord2.mAppCompatController.mTransparentPolicy);
                    transparentPolicyState.inheritFromOpaque(activity);
                    transparentPolicyState.mLetterboxConfigListener =
                            WindowContainer.overrideConfigurationPropagation(
                                    activityRecord2,
                                    transparentPolicyState.mFirstOpaqueActivity,
                                    new TransparentPolicy$TransparentPolicyState$$ExternalSyntheticLambda1(
                                            transparentPolicyState, activityRecord2.getParent()));
                    return;
                }
            }
            if (isRunning) {
                activityRecord.recomputeConfiguration();
            }
        }
    }
}
