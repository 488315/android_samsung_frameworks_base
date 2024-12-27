package com.android.server.wm;

import android.app.ActivityOptions;
import android.content.Intent;
import android.util.Slog;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DexController$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DexController$$ExternalSyntheticLambda4(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Intent intent;
        switch (this.$r8$classId) {
            case 0:
                DexController dexController = (DexController) this.f$0;
                DisplayContent displayContent = (DisplayContent) this.f$1;
                Task task = (Task) obj;
                dexController.getClass();
                if (!task.isActivityTypeHome()
                        && !task.isActivityTypeRecents()
                        && !task.mCreatedByOrganizer) {
                    task.getRootTask().reparent(displayContent.getDefaultTaskDisplayArea(), false);
                    if (task.inFreeformWindowingMode()) {
                        dexController.mAtm.mTaskSupervisor.mLaunchParamsController.layoutTask(
                                task, null, null, null, null, -1);
                        break;
                    }
                }
                break;
            case 1:
                DexController dexController2 = (DexController) this.f$0;
                DisplayContent displayContent2 = (DisplayContent) this.f$1;
                Task task2 = (Task) obj;
                dexController2.getClass();
                ActivityRecord rootActivity = task2.getRootActivity(true, false);
                if (rootActivity != null
                        && rootActivity.isActivityTypeStandardOrUndefined()
                        && task2.isActivityTypeStandardOrUndefined()) {
                    if (!task2.inRecents
                            || (((intent = task2.intent) != null
                                            && (intent.getFlags() & 8388608) != 0)
                                    || dexController2.getNonStartableActivityInDexMode(task2)
                                            != null)) {
                        dexController2.mH.post(
                                new DexController$$ExternalSyntheticLambda5(dexController2, task2));
                        break;
                    } else {
                        ActivityOptions makeBasic = ActivityOptions.makeBasic();
                        makeBasic.setLaunchWindowingMode(5);
                        if (displayContent2.isDexMode()) {
                            task2.updateDexCompatMode(null, null, false);
                        }
                        dexController2.mAtm.mTaskSupervisor.mLaunchParamsController.layoutTask(
                                task2, null, null, null, makeBasic, -1);
                        if (!displayContent2.getBounds().equals(task2.getBounds())) {
                            if (task2.getRequestedOverrideWindowingMode() != 5) {
                                task2.setWindowingMode(5);
                                break;
                            }
                        } else {
                            task2.setWindowingMode(1);
                            Slog.d(
                                    "DexController",
                                    "moveTaskToFreeformLocked: has fullscreen dex persistent bounds"
                                        + " task= "
                                            + task2);
                            break;
                        }
                    }
                }
                break;
            default:
                DexController.FindTaskResult findTaskResult =
                        (DexController.FindTaskResult) this.f$0;
                AtomicInteger atomicInteger = (AtomicInteger) this.f$1;
                ActivityRecord activityRecord = (ActivityRecord) obj;
                findTaskResult.getClass();
                if (findTaskResult.mProcessName.equals(activityRecord.processName)
                        && activityRecord.getUid() == findTaskResult.mUid) {
                    atomicInteger.set(findTaskResult.mTask.getChildCount());
                    break;
                }
                break;
        }
    }
}
