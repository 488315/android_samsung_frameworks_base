package com.android.server.wm;

import android.content.ComponentName;
import android.util.Slog;
import android.window.WindowContainerTransaction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppPairController implements IController {
    public final ActivityTaskManagerService mAtm;
    public boolean mShouldAutoPipByAppPair = false;

    public AppPairController(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtm = activityTaskManagerService;
    }

    public final void handleAutoPipIfNeededLocked(
            WindowContainerTransaction windowContainerTransaction) {
        ActivityRecord findEnterPipOnTaskSwitchCandidate;
        TaskDisplayArea defaultTaskDisplayArea =
                this.mAtm.mRootWindowContainer.mDefaultDisplay.getDefaultTaskDisplayArea();
        Task rootTask = defaultTaskDisplayArea.getRootTask(1, 0);
        if (rootTask == null
                || defaultTaskDisplayArea.isSplitScreenModeActivated()
                || (findEnterPipOnTaskSwitchCandidate =
                                Task.findEnterPipOnTaskSwitchCandidate(rootTask))
                        == null
                || findEnterPipOnTaskSwitchCandidate.supportsEnterPipOnTaskSwitch) {
            return;
        }
        String str = findEnterPipOnTaskSwitchCandidate.packageName;
        if (str != null) {
            for (WindowContainerTransaction.HierarchyOp hierarchyOp :
                    windowContainerTransaction.getHierarchyOps()) {
                ComponentName component =
                        hierarchyOp.getActivityIntent() != null
                                ? hierarchyOp.getActivityIntent().getComponent()
                                : null;
                if (component != null && str.equals(component.getPackageName())) {
                    Slog.d(
                            "AppPairController",
                            "handleAutoPipIfNeededLocked: failed, reason=same_package, r="
                                    + findEnterPipOnTaskSwitchCandidate);
                    return;
                }
            }
        }
        Task.enableEnterPipOnTaskSwitch(null, findEnterPipOnTaskSwitchCandidate, null, rootTask);
        Slog.d(
                "AppPairController",
                "handleAutoPipIfNeededLocked: enable autoPip, r="
                        + findEnterPipOnTaskSwitchCandidate);
        this.mShouldAutoPipByAppPair =
                findEnterPipOnTaskSwitchCandidate.supportsEnterPipOnTaskSwitch;
    }

    @Override // com.android.server.wm.IController
    public final void initialize() {}
}
