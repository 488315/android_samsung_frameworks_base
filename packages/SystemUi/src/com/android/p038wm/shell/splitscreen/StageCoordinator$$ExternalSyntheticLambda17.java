package com.android.p038wm.shell.splitscreen;

import android.util.ArrayMap;
import android.util.Slog;
import com.android.p038wm.shell.recents.RecentTasksController;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda17 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda17(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ArrayMap arrayMap = (ArrayMap) this.f$0;
                RecentTasksController recentTasksController = (RecentTasksController) obj;
                int size = arrayMap.keySet().size();
                while (true) {
                    size--;
                    if (size < 0) {
                        break;
                    } else {
                        recentTasksController.removeSplitPair(((Integer) arrayMap.keyAt(size)).intValue());
                    }
                }
            default:
                RecentTasksController recentTasksController2 = (RecentTasksController) obj;
                StageCoordinator stageCoordinator = StageCoordinator.this;
                int topVisibleChildTaskId = stageCoordinator.mMainStage.getTopVisibleChildTaskId();
                boolean z = false;
                if (topVisibleChildTaskId != -1) {
                    if (!(-1 != recentTasksController2.mSplitTasks.get(topVisibleChildTaskId, -1))) {
                        z = true;
                    }
                }
                if (z) {
                    Slog.d("StageCoordinator", "update pair by onSplitPairUpdateRequested");
                    stageCoordinator.mShouldUpdateRecents = true;
                    stageCoordinator.updateRecentTasksSplitPair();
                    break;
                }
                break;
        }
    }
}
