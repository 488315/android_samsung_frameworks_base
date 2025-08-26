package com.android.wm.shell.splitscreen;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.util.ArraySet;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.splitscreen.ForcedResizableInfoActivityController;
import com.android.wm.shell.splitscreen.SplitScreen;

/* loaded from: classes3.dex */
public final class ForcedResizableInfoActivityController implements SplitScreen.SplitScreenListener {
    public final Context mContext;
    public final ShellExecutor mMainExecutor;
    public final ArraySet mPendingTasks = new ArraySet();
    public final ArraySet mPackagesShownInSession = new ArraySet();
    public final ForcedResizableInfoActivityController$$ExternalSyntheticLambda0 mTimeoutRunnable = new Runnable() { // from class: com.android.wm.shell.splitscreen.ForcedResizableInfoActivityController$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            ForcedResizableInfoActivityController forcedResizableInfoActivityController = this.f$0;
            ((HandlerExecutor) forcedResizableInfoActivityController.mMainExecutor).removeCallbacks(forcedResizableInfoActivityController.mTimeoutRunnable);
            for (int size = forcedResizableInfoActivityController.mPendingTasks.size() - 1; size >= 0; size--) {
                ForcedResizableInfoActivityController.PendingTaskRecord pendingTaskRecord = (ForcedResizableInfoActivityController.PendingTaskRecord) forcedResizableInfoActivityController.mPendingTasks.valueAt(size);
                Intent intent = new Intent(forcedResizableInfoActivityController.mContext, (Class<?>) ForcedResizableInfoActivity.class);
                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                activityOptionsMakeBasic.setLaunchTaskId(pendingTaskRecord.mTaskId);
                activityOptionsMakeBasic.setTaskOverlay(true, true);
                int i = pendingTaskRecord.mReason;
                intent.putExtra("extra_forced_resizeable_reason", i);
                if (i == 3) {
                    intent.addFlags(262144);
                }
                forcedResizableInfoActivityController.mContext.startActivityAsUser(intent, activityOptionsMakeBasic.toBundle(), UserHandle.CURRENT);
            }
            forcedResizableInfoActivityController.mPendingTasks.clear();
        }
    };
    public long mLastShowingTime = 0;

    public class PendingTaskRecord {
        public final int mReason;
        public final int mTaskId;

        public PendingTaskRecord(ForcedResizableInfoActivityController forcedResizableInfoActivityController, int i, int i2) {
            this.mTaskId = i;
            this.mReason = i2;
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.wm.shell.splitscreen.ForcedResizableInfoActivityController$$ExternalSyntheticLambda0] */
    public ForcedResizableInfoActivityController(Context context, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mMainExecutor = shellExecutor;
    }
}
