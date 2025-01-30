package com.android.server.wm;

import android.content.ComponentName;
import android.util.Slog;
import android.window.WindowContainerTransaction;

/* loaded from: classes3.dex */
public class AppPairController implements IController {
    public final ActivityTaskManagerService mAtm;
    public boolean mShouldAutoPipByAppPair = false;

    @Override // com.android.server.wm.IController
    public void initialize() {
    }

    public AppPairController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
    }

    public void handleAutoPipIfNeededLocked(WindowContainerTransaction windowContainerTransaction) {
        ActivityRecord findEnterPipOnTaskSwitchCandidate;
        TaskDisplayArea defaultTaskDisplayArea = this.mAtm.mRootWindowContainer.getDefaultTaskDisplayArea();
        Task topRootTaskInWindowingMode = defaultTaskDisplayArea.getTopRootTaskInWindowingMode(1);
        if (topRootTaskInWindowingMode == null || defaultTaskDisplayArea.isSplitScreenModeActivated() || (findEnterPipOnTaskSwitchCandidate = Task.findEnterPipOnTaskSwitchCandidate(topRootTaskInWindowingMode)) == null || findEnterPipOnTaskSwitchCandidate.supportsEnterPipOnTaskSwitch) {
            return;
        }
        if (hasSamePackageInStartIntentsLocked(windowContainerTransaction, findEnterPipOnTaskSwitchCandidate.packageName)) {
            Slog.d("AppPairController", "handleAutoPipIfNeededLocked: failed, reason=same_package, r=" + findEnterPipOnTaskSwitchCandidate);
            return;
        }
        Task.enableEnterPipOnTaskSwitch(findEnterPipOnTaskSwitchCandidate, topRootTaskInWindowingMode, null, null);
        Slog.d("AppPairController", "handleAutoPipIfNeededLocked: enable autoPip, r=" + findEnterPipOnTaskSwitchCandidate);
        setShouldAutoPipByAppPair(findEnterPipOnTaskSwitchCandidate.supportsEnterPipOnTaskSwitch);
    }

    public final boolean hasSamePackageInStartIntentsLocked(WindowContainerTransaction windowContainerTransaction, String str) {
        if (str == null) {
            return false;
        }
        for (WindowContainerTransaction.HierarchyOp hierarchyOp : windowContainerTransaction.getHierarchyOps()) {
            ComponentName component = hierarchyOp.getActivityIntent() != null ? hierarchyOp.getActivityIntent().getComponent() : null;
            if (component != null && str.equals(component.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public boolean shouldAutoPipByAppPair() {
        return this.mShouldAutoPipByAppPair;
    }

    public void setShouldAutoPipByAppPair(boolean z) {
        this.mShouldAutoPipByAppPair = z;
    }
}
