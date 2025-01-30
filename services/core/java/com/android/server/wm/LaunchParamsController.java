package com.android.server.wm;

import android.app.ActivityOptions;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import com.android.server.wm.ActivityStarter;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class LaunchParamsController {
    public final LaunchParamsPersister mPersister;
    public final ActivityTaskManagerService mService;
    public final List mModifiers = new ArrayList();
    public final LaunchParams mTmpParams = new LaunchParams();
    public final LaunchParams mTmpCurrent = new LaunchParams();
    public final LaunchParams mTmpResult = new LaunchParams();

    public interface LaunchParamsModifier {
        int onCalculate(Task task, ActivityInfo.WindowLayout windowLayout, ActivityRecord activityRecord, ActivityRecord activityRecord2, ActivityOptions activityOptions, ActivityStarter.Request request, int i, LaunchParams launchParams, LaunchParams launchParams2);
    }

    public LaunchParamsController(ActivityTaskManagerService activityTaskManagerService, LaunchParamsPersister launchParamsPersister) {
        this.mService = activityTaskManagerService;
        this.mPersister = launchParamsPersister;
    }

    public void registerDefaultModifiers(ActivityTaskSupervisor activityTaskSupervisor) {
        registerModifier(new MultiTaskingTaskLaunchParamsModifier(activityTaskSupervisor));
    }

    public void calculate(Task task, ActivityInfo.WindowLayout windowLayout, ActivityRecord activityRecord, ActivityRecord activityRecord2, ActivityOptions activityOptions, ActivityStarter.Request request, int i, LaunchParams launchParams) {
        calculate(task, windowLayout, activityRecord, activityRecord2, activityOptions, request, i, launchParams, null);
    }

    public void calculate(Task task, ActivityInfo.WindowLayout windowLayout, ActivityRecord activityRecord, ActivityRecord activityRecord2, ActivityOptions activityOptions, ActivityStarter.Request request, int i, LaunchParams launchParams, TaskDisplayArea taskDisplayArea) {
        TaskDisplayArea taskDisplayArea2;
        TaskDisplayArea taskDisplayArea3;
        launchParams.reset();
        if (task != null || activityRecord != null) {
            if (i > 0) {
                int launchWindowingMode = activityOptions != null ? activityOptions.getLaunchWindowingMode() : 0;
                if (launchWindowingMode == 0 && taskDisplayArea != null) {
                    launchWindowingMode = taskDisplayArea.getWindowingMode();
                }
                if (launchWindowingMode == 0 && task != null && task.isAttached()) {
                    launchWindowingMode = task.getWindowingMode();
                }
                if ((task != null && task.isDexMode() && taskDisplayArea == null) || launchWindowingMode == 5) {
                    this.mPersister.getLaunchParams(task, activityRecord, launchParams);
                    if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY && (taskDisplayArea3 = launchParams.mPreferredTaskDisplayArea) != null && taskDisplayArea3.isNewDexMode()) {
                        int launchWindowingMode2 = activityOptions != null ? activityOptions.getLaunchWindowingMode() : 0;
                        if (launchWindowingMode2 == 5) {
                            DexPersistBoundsParams dexPersistBoundsParams = launchParams.mDexPersistBoundsParam;
                            if (dexPersistBoundsParams.mDexWindowingMode == 1) {
                                dexPersistBoundsParams.mDexWindowingMode = 5;
                                if (task != null && task.getDisplayContent() != null) {
                                    this.mPersister.saveTask(task, task.getDisplayContent());
                                }
                            }
                        }
                        if (task != null && task.inSplitScreenWindowingMode() && launchWindowingMode2 != 5 && launchParams.mWindowingMode == 5) {
                            launchParams.mWindowingMode = 0;
                        }
                    }
                } else if ((CoreRune.MT_NEW_DEX_PERSIST_BOUNDS && task != null && task.isNewDexMode() && taskDisplayArea == null) || launchWindowingMode == 5) {
                    this.mPersister.getLaunchParams(task, activityRecord, launchParams);
                    if (CoreRune.MT_NEW_DEX_LAUNCH_POLICY && (taskDisplayArea2 = launchParams.mPreferredTaskDisplayArea) != null && taskDisplayArea2.isNewDexMode()) {
                        int launchWindowingMode3 = activityOptions != null ? activityOptions.getLaunchWindowingMode() : 0;
                        if (launchWindowingMode3 == 5) {
                            NewDexPersistBoundsParams newDexPersistBoundsParams = launchParams.mNewDexPersistBoundsParam;
                            if (newDexPersistBoundsParams.mNewDexWindowingMode == 1) {
                                newDexPersistBoundsParams.mNewDexWindowingMode = 5;
                                if (task != null && task.getDisplayContent() != null) {
                                    this.mPersister.saveTask(task, task.getDisplayContent());
                                }
                            }
                        }
                        if (task != null && task.inSplitScreenWindowingMode() && launchWindowingMode3 != 5 && launchParams.mWindowingMode == 5) {
                            launchParams.mWindowingMode = 0;
                        }
                    }
                }
            } else {
                this.mPersister.getLaunchParams(task, activityRecord, launchParams);
            }
        }
        for (int size = this.mModifiers.size() - 1; size >= 0; size--) {
            this.mTmpCurrent.set(launchParams);
            this.mTmpResult.reset();
            int onCalculate = ((LaunchParamsModifier) this.mModifiers.get(size)).onCalculate(task, windowLayout, activityRecord, activityRecord2, activityOptions, request, i, this.mTmpCurrent, this.mTmpResult);
            if (onCalculate == 1) {
                launchParams.set(this.mTmpResult);
                return;
            } else {
                if (onCalculate == 2) {
                    launchParams.set(this.mTmpResult);
                }
            }
        }
        if (activityRecord != null && activityRecord.requestedVrComponent != null) {
            launchParams.mPreferredTaskDisplayArea = this.mService.mRootWindowContainer.getDefaultTaskDisplayArea();
            return;
        }
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        int i2 = activityTaskManagerService.mVr2dDisplayId;
        if (i2 != -1) {
            launchParams.mPreferredTaskDisplayArea = activityTaskManagerService.mRootWindowContainer.getDisplayContent(i2).getDefaultTaskDisplayArea();
        }
    }

    public boolean layoutTask(Task task, ActivityInfo.WindowLayout windowLayout) {
        return layoutTask(task, windowLayout, null, null, null);
    }

    public boolean layoutTask(Task task, ActivityInfo.WindowLayout windowLayout, ActivityRecord activityRecord, ActivityRecord activityRecord2, ActivityOptions activityOptions) {
        return layoutTask(task, windowLayout, activityRecord, activityRecord2, activityOptions, -1);
    }

    public boolean layoutTask(Task task, ActivityInfo.WindowLayout windowLayout, ActivityRecord activityRecord, ActivityRecord activityRecord2, ActivityOptions activityOptions, int i) {
        DisplayContent displayContent = this.mService.mRootWindowContainer.getDisplayContent(i);
        calculate(task, windowLayout, activityRecord, activityRecord2, activityOptions, null, 3, this.mTmpParams, displayContent != null ? displayContent.getDefaultTaskDisplayArea() : null);
        if (this.mTmpParams.isEmpty()) {
            return false;
        }
        this.mService.deferWindowLayout();
        try {
            if (this.mTmpParams.mBounds.isEmpty()) {
                if (this.mTmpParams.hasWindowingMode() && task.isDexMode() && this.mTmpParams.mDexPersistBoundsParam.mDexWindowingMode == 1) {
                    if (activityOptions != null) {
                        int windowingMode = task.getWindowingMode();
                        int i2 = this.mTmpParams.mWindowingMode;
                        if (windowingMode != i2) {
                            activityOptions.setLaunchWindowingMode(i2);
                        }
                    }
                    task.setBounds(this.mTmpParams.mBounds);
                }
                if (CoreRune.MT_NEW_DEX_PERSIST_BOUNDS && this.mTmpParams.hasWindowingMode() && task.isNewDexMode()) {
                    LaunchParams launchParams = this.mTmpParams;
                    if (launchParams.mNewDexPersistBoundsParam.mNewDexWindowingMode == 1) {
                        task.setBounds(launchParams.mBounds);
                    }
                }
            } else {
                if (this.mTmpParams.hasWindowingMode() && task.isDexCompatEnabled() && task.isRootTask() && this.mTmpParams.mWindowingMode != task.getWindowingMode()) {
                    task.setWindowingMode(task.getDisplayArea().validateWindowingMode(this.mTmpParams.mWindowingMode, activityRecord, task));
                }
                if (task.getRootTask().inMultiWindowMode()) {
                    task.setBounds(this.mTmpParams.mBounds);
                    return true;
                }
                task.setLastNonFullscreenBounds(this.mTmpParams.mBounds);
            }
            return false;
        } finally {
            this.mService.continueWindowLayout();
        }
    }

    public void registerModifier(LaunchParamsModifier launchParamsModifier) {
        if (this.mModifiers.contains(launchParamsModifier)) {
            return;
        }
        this.mModifiers.add(launchParamsModifier);
    }

    public class LaunchParams {
        public int mDisplayDeviceType;
        public int mOrientation;
        public TaskDisplayArea mPreferredTaskDisplayArea;
        public int mWindowingMode;
        public final Rect mBounds = new Rect();
        public final FreeformPersistBoundsParams mFreeformPersistBoundsParam = new FreeformPersistBoundsParams();
        public final DexPersistBoundsParams mDexPersistBoundsParam = new DexPersistBoundsParams();
        public final NewDexPersistBoundsParams mNewDexPersistBoundsParam = new NewDexPersistBoundsParams();

        public void reset() {
            this.mBounds.setEmpty();
            this.mPreferredTaskDisplayArea = null;
            this.mWindowingMode = 0;
            this.mOrientation = 0;
            this.mFreeformPersistBoundsParam.reset();
            this.mDexPersistBoundsParam.reset();
            if (CoreRune.MT_NEW_DEX_PERSIST_BOUNDS) {
                this.mNewDexPersistBoundsParam.reset();
            }
            if (CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY) {
                this.mDisplayDeviceType = -1;
            }
        }

        public void set(LaunchParams launchParams) {
            this.mBounds.set(launchParams.mBounds);
            this.mPreferredTaskDisplayArea = launchParams.mPreferredTaskDisplayArea;
            this.mWindowingMode = launchParams.mWindowingMode;
            this.mOrientation = launchParams.mOrientation;
            this.mFreeformPersistBoundsParam.set(launchParams.mFreeformPersistBoundsParam);
            this.mDexPersistBoundsParam.set(launchParams.mDexPersistBoundsParam);
            if (CoreRune.MT_NEW_DEX_PERSIST_BOUNDS) {
                this.mNewDexPersistBoundsParam.set(launchParams.mNewDexPersistBoundsParam);
            }
            if (CoreRune.MW_MULTI_SPLIT_FREEFORM_FOLDING_POLICY) {
                this.mDisplayDeviceType = launchParams.mDisplayDeviceType;
            }
        }

        public boolean isEmpty() {
            return this.mBounds.isEmpty() && this.mPreferredTaskDisplayArea == null && this.mWindowingMode == 0;
        }

        public boolean hasWindowingMode() {
            return this.mWindowingMode != 0;
        }

        public boolean hasPreferredTaskDisplayArea() {
            return this.mPreferredTaskDisplayArea != null;
        }

        public boolean hasValidFreeformPersistBounds() {
            return this.mFreeformPersistBoundsParam.isValid();
        }

        public boolean hasValidDexPersistBounds() {
            return this.mDexPersistBoundsParam.isValid();
        }

        public boolean hasValidNewDexPersistBounds() {
            return this.mNewDexPersistBoundsParam.isValid();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            LaunchParams launchParams = (LaunchParams) obj;
            if (this.mPreferredTaskDisplayArea != launchParams.mPreferredTaskDisplayArea || this.mWindowingMode != launchParams.mWindowingMode || !this.mFreeformPersistBoundsParam.equals(launchParams.mFreeformPersistBoundsParam)) {
                return false;
            }
            Rect rect = this.mBounds;
            return rect != null ? rect.equals(launchParams.mBounds) : launchParams.mBounds == null;
        }

        public int hashCode() {
            Rect rect = this.mBounds;
            int hashCode = (rect != null ? rect.hashCode() : 0) * 31;
            TaskDisplayArea taskDisplayArea = this.mPreferredTaskDisplayArea;
            return ((((hashCode + (taskDisplayArea != null ? taskDisplayArea.hashCode() : 0)) * 31) + this.mWindowingMode) * 31) + this.mFreeformPersistBoundsParam.hashCode();
        }
    }
}
