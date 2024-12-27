package com.android.server.wm;

import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.FactoryTest;

import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.packagefeature.PackageFeatureUserChange;

import java.io.PrintWriter;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MultiTaskingAppCompatOrientationPolicy {
    public static final boolean SUPPORTS_MULTI_STAR =
            CoreRune.MT_APP_COMPAT_LANDSCAPE_VIEW_FOR_PORTRAIT_APPS;
    public final MultiTaskingAppCompatOrientationOverrides mOrientationOverrides;
    public boolean mShouldIgnoreLandscapeRequestDueToMultiStar;
    public final Rect mTmpPrevBounds = new Rect();
    public final int mRotationCompatPolicy = 2;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.wm.MultiTaskingAppCompatOrientationPolicy$1, reason: invalid class name */
    public final class AnonymousClass1
            implements MultiTaskingAppCompatController.OverridesObserver {
        public AnonymousClass1() {}

        @Override // com.android.server.wm.MultiTaskingAppCompatController.OverridesObserver
        public final void onDumpInTask(Task task, PrintWriter printWriter, String str) {
            if (task.mRespectOrientationRequestOverride == -1) {
                return;
            }
            printWriter.print(str);
            printWriter.print("mRespectOrientationRequestOverride=");
            printWriter.print("0x" + task.mRespectOrientationRequestOverride);
            printWriter.println();
        }

        @Override // com.android.server.wm.MultiTaskingAppCompatController.OverridesObserver
        public final void onOverridesChangedIfNeededInTask(
                int i, Task task, String str, boolean z) {
            int i2 = task.mRespectOrientationRequestOverride;
            int respectOrientationRequest =
                    MultiTaskingAppCompatOrientationPolicy.this.mOrientationOverrides
                            .getRespectOrientationRequest(i, str);
            task.mRespectOrientationRequestOverride = respectOrientationRequest;
            if (!z || i2 == respectOrientationRequest) {
                return;
            }
            task.forAllActivities(
                    new Consumer() { // from class:
                                     // com.android.server.wm.MultiTaskingAppCompatOrientationPolicy$1$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            MultiTaskingAppCompatOrientationPolicy.AnonymousClass1 anonymousClass1 =
                                    MultiTaskingAppCompatOrientationPolicy.AnonymousClass1.this;
                            ActivityRecord activityRecord = (ActivityRecord) obj;
                            anonymousClass1.getClass();
                            MultiTaskingAppCompatOrientationPolicy.this
                                    .requestActivityBoundsChangedTransitionIfNeeded(
                                            activityRecord,
                                            new MultiTaskingAppCompatOrientationPolicy$$ExternalSyntheticLambda0(
                                                    1, activityRecord));
                        }
                    });
        }

        @Override // com.android.server.wm.MultiTaskingAppCompatController.OverridesObserver
        public final void resetUserOverrides(int i, int i2) {
            MultiTaskingAppCompatOrientationOverrides multiTaskingAppCompatOrientationOverrides =
                    MultiTaskingAppCompatOrientationPolicy.this.mOrientationOverrides;
            synchronized (multiTaskingAppCompatOrientationOverrides) {
                PackageFeatureUserChange packageFeatureUserChange =
                        multiTaskingAppCompatOrientationOverrides.mUserOverride;
                int i3 = packageFeatureUserChange.mIdentityFlag;
                if ((i2 & i3) == i3) {
                    packageFeatureUserChange.reset(i);
                }
            }
        }
    }

    public MultiTaskingAppCompatOrientationPolicy(
            MultiTaskingAppCompatOrientationOverrides multiTaskingAppCompatOrientationOverrides) {
        this.mOrientationOverrides = multiTaskingAppCompatOrientationOverrides;
    }

    public final int getRespectOrientationRequestIfAllowed(int i, ActivityRecord activityRecord) {
        DisplayContent displayContent;
        if (activityRecord == null
                || activityRecord.task == null
                || (displayContent = activityRecord.mDisplayContent) == null
                || !displayContent.mSetIgnoreOrientationRequestOverride) {
            return -1;
        }
        if ((SUPPORTS_MULTI_STAR && this.mShouldIgnoreLandscapeRequestDueToMultiStar)
                ? false
                : ActivityInfo.isFixedOrientationLandscape(i)) {
            return -1;
        }
        if (!(CoreRune.IS_FACTORY_BINARY || FactoryTest.isRunningFactoryApp())
                && activityRecord.getTaskFragment() != null
                && activityRecord.getTaskFragment().getWindowingMode() == 1
                && MultiTaskingAppCompatController.inAllowedWindowingMode(activityRecord)) {
            return activityRecord.task.mRespectOrientationRequestOverride;
        }
        return -1;
    }

    public final void requestActivityBoundsChangedTransitionIfNeeded(
            ActivityRecord activityRecord, Runnable runnable) {
        if (CoreRune.MT_APP_COMPAT_CONFIGURATION) {
            boolean isPresetLetterboxed =
                    MultiTaskingAppCompatConfiguration.isPresetLetterboxed(activityRecord);
            this.mTmpPrevBounds.set(activityRecord.getBounds());
            runnable.run();
            if (this.mTmpPrevBounds.equals(activityRecord.getBounds())) {
                return;
            }
            if (isPresetLetterboxed
                    || MultiTaskingAppCompatConfiguration.isPresetLetterboxed(activityRecord)) {
                ChangeTransitionController changeTransitionController =
                        activityRecord.mAtmService.mChangeTransitController;
                Rect rect = this.mTmpPrevBounds;
                changeTransitionController.getClass();
                Task task = activityRecord.task;
                if (task == null || task.isChangeTransitionBlockedByCommonPolicy()) {
                    return;
                }
                Transition.ChangeInfo findCollectingChangeInfo =
                        changeTransitionController.findCollectingChangeInfo(activityRecord);
                if (findCollectingChangeInfo == null
                        || findCollectingChangeInfo.mChangeLeash == null) {
                    Transition.ChangeInfo findCollectingChangeInfo2 =
                            changeTransitionController.findCollectingChangeInfo(task);
                    if (findCollectingChangeInfo2 == null
                            || !findCollectingChangeInfo2.hasChanged()) {
                        Transition createTransition =
                                !changeTransitionController.mTransitionController.isCollecting()
                                        ? changeTransitionController.mTransitionController
                                                .createTransition(6, 0)
                                        : null;
                        changeTransitionController.mTransitionController.collect(activityRecord);
                        changeTransitionController.updateChangeInfo(activityRecord, 5, 0, rect, 0);
                        changeTransitionController.mTransitionController.collectVisibleChange(
                                activityRecord);
                        if (createTransition != null) {
                            changeTransitionController.mTransitionController.requestStartTransition(
                                    createTransition, task, null, null);
                            createTransition.setReady(task, true);
                        }
                    }
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x011d, code lost:

       if (r13.isResizeable(true) == false) goto L28;
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0111, code lost:

       if (r12 != 4) goto L36;
    */
    /* JADX WARN: Removed duplicated region for block: B:106:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:108:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0119  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldCreateAppCompatDisplayInsetsForRotationCompat(
            com.android.server.wm.ActivityRecord r13) {
        /*
            Method dump skipped, instructions count: 304
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.MultiTaskingAppCompatOrientationPolicy.shouldCreateAppCompatDisplayInsetsForRotationCompat(com.android.server.wm.ActivityRecord):boolean");
    }

    public final boolean shouldIgnoreOrientationRequest(int i, WindowContainer windowContainer) {
        int respectOrientationRequestIfAllowed =
                getRespectOrientationRequestIfAllowed(
                        i, windowContainer != null ? windowContainer.asActivityRecord() : null);
        if (respectOrientationRequestIfAllowed != -1) {
            boolean z = MultiTaskingAppCompatOrientationOverrides.SUPPORTS_DEFAULT_ENABLED;
            if (respectOrientationRequestIfAllowed != 0
                    && respectOrientationRequestIfAllowed != 32) {
                return true;
            }
        }
        return false;
    }
}
