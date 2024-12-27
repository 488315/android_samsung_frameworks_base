package com.android.server.wm;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class RootWindowContainer$$ExternalSyntheticLambda2
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RootWindowContainer f$0;
    public final /* synthetic */ ArrayList f$1;

    public /* synthetic */ RootWindowContainer$$ExternalSyntheticLambda2(
            RootWindowContainer rootWindowContainer, ArrayList arrayList, int i) {
        this.$r8$classId = i;
        this.f$0 = rootWindowContainer;
        this.f$1 = arrayList;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                RootWindowContainer rootWindowContainer = this.f$0;
                ArrayList arrayList = this.f$1;
                rootWindowContainer.getClass();
                arrayList.add(RootWindowContainer.getRootTaskInfo((Task) obj));
                break;
            case 1:
                RootWindowContainer rootWindowContainer2 = this.f$0;
                ArrayList arrayList2 = this.f$1;
                ActivityRecord activityRecord = (ActivityRecord) obj;
                rootWindowContainer2.getClass();
                Task task = activityRecord.task;
                if (activityRecord.isVisibleRequested()
                        && activityRecord.mStartingData == null
                        && !arrayList2.contains(task)) {
                    if (rootWindowContainer2.mWmService.mFlags.mRespectNonTopVisibleFixedOrientation
                            || activityRecord.occludesParent(false)
                            || !activityRecord.mDisplayContent.hasTopFixedRotationLaunchingApp()
                            || activityRecord.mDisplayContent.isFixedRotationLaunchingApp(
                                    activityRecord)) {
                        activityRecord.showStartingWindow(true);
                        arrayList2.add(task);
                        break;
                    }
                }
                break;
            default:
                RootWindowContainer rootWindowContainer3 = this.f$0;
                ArrayList arrayList3 = this.f$1;
                rootWindowContainer3.getClass();
                arrayList3.add(RootWindowContainer.getRootTaskInfo((Task) obj));
                break;
        }
    }
}
