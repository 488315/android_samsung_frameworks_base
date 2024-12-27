package com.android.server.wm;

import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.util.SparseIntArray;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class RootWindowContainer$$ExternalSyntheticLambda20
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ RootWindowContainer$$ExternalSyntheticLambda20(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        String flattenToString;
        switch (this.$r8$classId) {
            case 0:
                RootWindowContainer rootWindowContainer = (RootWindowContainer) this.f$0;
                SparseIntArray sparseIntArray = (SparseIntArray) this.f$1;
                WindowState windowState = (WindowState) obj;
                if (!rootWindowContainer.mWmService.mForceRemoves.contains(windowState)) {
                    WindowStateAnimator windowStateAnimator = windowState.mWinAnimator;
                    if (windowStateAnimator.mSurfaceController != null) {
                        int i = windowStateAnimator.mSession.mPid;
                        sparseIntArray.append(i, i);
                        break;
                    }
                }
                break;
            default:
                int[] iArr = (int[]) this.f$0;
                ActivityTaskManager.RootTaskInfo rootTaskInfo =
                        (ActivityTaskManager.RootTaskInfo) this.f$1;
                Task task = (Task) obj;
                int i2 = iArr[0];
                rootTaskInfo.childTaskIds[i2] = task.mTaskId;
                String[] strArr = rootTaskInfo.childTaskNames;
                ComponentName componentName = task.origActivity;
                if (componentName != null) {
                    flattenToString = componentName.flattenToString();
                } else {
                    ComponentName componentName2 = task.realActivity;
                    flattenToString =
                            componentName2 != null
                                    ? componentName2.flattenToString()
                                    : task.getTopNonFinishingActivity(true, true) != null
                                            ? task.getTopNonFinishingActivity(true, true)
                                                    .packageName
                                            : "unknown";
                }
                strArr[i2] = flattenToString;
                rootTaskInfo.childTaskBounds[i2] = task.mAtmService.getTaskBounds(task.mTaskId);
                rootTaskInfo.childTaskUserIds[i2] = task.mUserId;
                iArr[0] = i2 + 1;
                break;
        }
    }
}
