package com.android.server.wm;

import android.app.ActivityOptions;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;

import com.samsung.android.rune.CoreRune;

import java.util.ArrayList;
import java.util.List;

public final class LaunchParamsController {
    public final LaunchParamsPersister mPersister;
    public final ActivityTaskManagerService mService;
    public final List mModifiers = new ArrayList();
    public final LaunchParams mTmpParams = new LaunchParams();
    public final LaunchParams mTmpCurrent = new LaunchParams();
    public final LaunchParams mTmpResult = new LaunchParams();

    public final class LaunchParams {
        public int mDisplayDeviceType;
        public int mOrientation;
        public TaskDisplayArea mPreferredTaskDisplayArea;
        public int mWindowingMode;
        public final Rect mBounds = new Rect();
        public final FreeformPersistBoundsParams mFreeformPersistBoundsParam =
                new FreeformPersistBoundsParams();
        public final DexPersistBoundsParams mDexPersistBoundsParam = new DexPersistBoundsParams();

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || LaunchParams.class != obj.getClass()) {
                return false;
            }
            LaunchParams launchParams = (LaunchParams) obj;
            if (this.mPreferredTaskDisplayArea != launchParams.mPreferredTaskDisplayArea
                    || this.mWindowingMode != launchParams.mWindowingMode
                    || !this.mFreeformPersistBoundsParam.equals(
                            launchParams.mFreeformPersistBoundsParam)) {
                return false;
            }
            Rect rect = this.mBounds;
            return rect != null ? rect.equals(launchParams.mBounds) : launchParams.mBounds == null;
        }

        public final int hashCode() {
            Rect rect = this.mBounds;
            int hashCode = (rect != null ? rect.hashCode() : 0) * 31;
            TaskDisplayArea taskDisplayArea = this.mPreferredTaskDisplayArea;
            return this.mFreeformPersistBoundsParam.hashCode()
                    + ((((hashCode + (taskDisplayArea != null ? taskDisplayArea.hashCode() : 0))
                                            * 31)
                                    + this.mWindowingMode)
                            * 31);
        }

        public final void reset() {
            this.mBounds.setEmpty();
            this.mPreferredTaskDisplayArea = null;
            this.mWindowingMode = 0;
            this.mOrientation = 0;
            FreeformPersistBoundsParams freeformPersistBoundsParams =
                    this.mFreeformPersistBoundsParam;
            freeformPersistBoundsParams.mFreeformBounds.setEmpty();
            freeformPersistBoundsParams.mDisplayBounds.setEmpty();
            freeformPersistBoundsParams.mRotation = -1;
            DexPersistBoundsParams dexPersistBoundsParams = this.mDexPersistBoundsParam;
            dexPersistBoundsParams.mDexWindowingMode = 0;
            dexPersistBoundsParams.mDexDualBounds.setEmpty();
            dexPersistBoundsParams.mDexStandAloneBounds.setEmpty();
            if (CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY) {
                this.mDisplayDeviceType = -1;
            }
        }

        public final void set(LaunchParams launchParams) {
            this.mBounds.set(launchParams.mBounds);
            this.mPreferredTaskDisplayArea = launchParams.mPreferredTaskDisplayArea;
            this.mWindowingMode = launchParams.mWindowingMode;
            this.mOrientation = launchParams.mOrientation;
            FreeformPersistBoundsParams freeformPersistBoundsParams =
                    this.mFreeformPersistBoundsParam;
            Rect rect = freeformPersistBoundsParams.mFreeformBounds;
            FreeformPersistBoundsParams freeformPersistBoundsParams2 =
                    launchParams.mFreeformPersistBoundsParam;
            rect.set(freeformPersistBoundsParams2.mFreeformBounds);
            freeformPersistBoundsParams.mDisplayBounds.set(
                    freeformPersistBoundsParams2.mDisplayBounds);
            freeformPersistBoundsParams.mRotation = freeformPersistBoundsParams2.mRotation;
            DexPersistBoundsParams dexPersistBoundsParams = this.mDexPersistBoundsParam;
            dexPersistBoundsParams.getClass();
            DexPersistBoundsParams dexPersistBoundsParams2 = launchParams.mDexPersistBoundsParam;
            dexPersistBoundsParams.mDexWindowingMode = dexPersistBoundsParams2.mDexWindowingMode;
            dexPersistBoundsParams.mDexDualBounds.set(dexPersistBoundsParams2.mDexDualBounds);
            dexPersistBoundsParams.mDexStandAloneBounds.set(
                    dexPersistBoundsParams2.mDexStandAloneBounds);
            if (CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY) {
                this.mDisplayDeviceType = launchParams.mDisplayDeviceType;
            }
        }
    }

    public interface LaunchParamsModifier {}

    public LaunchParamsController(
            ActivityTaskManagerService activityTaskManagerService,
            LaunchParamsPersister launchParamsPersister) {
        this.mService = activityTaskManagerService;
        this.mPersister = launchParamsPersister;
    }

    /* JADX WARN: Code restructure failed: missing block: B:307:0x081e, code lost:

       if (r4.info.resizeMode == 10) goto L450;
    */
    /* JADX WARN: Code restructure failed: missing block: B:537:0x0645, code lost:

       if (r4 != 3) goto L348;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0723  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0aa1  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0b04  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0b1a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0d7f  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0bd5  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0d9d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:271:0x0c5c  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0c90  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0b07  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x07ec  */
    /* JADX WARN: Removed duplicated region for block: B:392:0x0a7d  */
    /* JADX WARN: Removed duplicated region for block: B:472:0x0536  */
    /* JADX WARN: Removed duplicated region for block: B:474:0x0538  */
    /* JADX WARN: Type inference failed for: r2v187, types: [com.android.server.wm.ConfigurationContainer, com.android.server.wm.Task] */
    /* JADX WARN: Type inference failed for: r3v123 */
    /* JADX WARN: Type inference failed for: r3v124 */
    /* JADX WARN: Type inference failed for: r3v125 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7, types: [boolean] */
    /* JADX WARN: Type inference failed for: r7v34 */
    /* JADX WARN: Type inference failed for: r7v37 */
    /* JADX WARN: Type inference failed for: r7v38 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void calculate(
            com.android.server.wm.Task r38,
            android.content.pm.ActivityInfo.WindowLayout r39,
            com.android.server.wm.ActivityRecord r40,
            com.android.server.wm.ActivityRecord r41,
            android.app.ActivityOptions r42,
            com.android.server.wm.ActivityStarter.Request r43,
            int r44,
            com.android.server.wm.LaunchParamsController.LaunchParams r45,
            com.android.server.wm.TaskDisplayArea r46) {
        /*
            Method dump skipped, instructions count: 3531
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.LaunchParamsController.calculate(com.android.server.wm.Task,"
                    + " android.content.pm.ActivityInfo$WindowLayout,"
                    + " com.android.server.wm.ActivityRecord, com.android.server.wm.ActivityRecord,"
                    + " android.app.ActivityOptions, com.android.server.wm.ActivityStarter$Request,"
                    + " int, com.android.server.wm.LaunchParamsController$LaunchParams,"
                    + " com.android.server.wm.TaskDisplayArea):void");
    }

    public final boolean layoutTask(
            Task task,
            ActivityInfo.WindowLayout windowLayout,
            ActivityRecord activityRecord,
            ActivityRecord activityRecord2,
            ActivityOptions activityOptions,
            int i) {
        int i2;
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        DisplayContent displayContent =
                activityTaskManagerService.mRootWindowContainer.getDisplayContent(i);
        calculate(
                task,
                windowLayout,
                activityRecord,
                activityRecord2,
                activityOptions,
                null,
                3,
                this.mTmpParams,
                displayContent != null ? displayContent.getDefaultTaskDisplayArea() : null);
        LaunchParams launchParams = this.mTmpParams;
        if (launchParams.mBounds.isEmpty()
                && launchParams.mPreferredTaskDisplayArea == null
                && launchParams.mWindowingMode == 0) {
            return false;
        }
        activityTaskManagerService.deferWindowLayout();
        try {
            if (launchParams.mBounds.isEmpty()) {
                if (launchParams.mWindowingMode != 0
                        && task.isDexMode()
                        && launchParams.mDexPersistBoundsParam.mDexWindowingMode == 1) {
                    if (activityOptions != null) {
                        int windowingMode = task.getWindowingMode();
                        int i3 = launchParams.mWindowingMode;
                        if (windowingMode != i3) {
                            activityOptions.setLaunchWindowingMode(i3);
                        }
                    }
                    task.setBounds(launchParams.mBounds);
                }
                activityTaskManagerService.continueWindowLayout();
                return false;
            }
            if (task.isDexCompatEnabled()
                    && task.isRootTask()
                    && (i2 = launchParams.mWindowingMode) != 0
                    && i2 != task.getWindowingMode()) {
                TaskDisplayArea displayArea = task.getDisplayArea();
                int i4 = launchParams.mWindowingMode;
                if (!displayArea.isValidWindowingMode(i4, activityRecord, task)) {
                    i4 = 0;
                }
                task.setWindowingMode(i4);
            }
            if (task.getRootTask().inMultiWindowMode()) {
                task.setBounds(launchParams.mBounds);
                activityTaskManagerService.continueWindowLayout();
                return true;
            }
            task.setLastNonFullscreenBounds(launchParams.mBounds);
            activityTaskManagerService.continueWindowLayout();
            return false;
        } catch (Throwable th) {
            activityTaskManagerService.continueWindowLayout();
            throw th;
        }
    }
}
