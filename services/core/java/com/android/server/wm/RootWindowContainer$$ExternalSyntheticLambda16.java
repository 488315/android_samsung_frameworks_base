package com.android.server.wm;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class RootWindowContainer$$ExternalSyntheticLambda16
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RootWindowContainer f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ RootWindowContainer$$ExternalSyntheticLambda16(
            RootWindowContainer rootWindowContainer, String str, int i) {
        this.$r8$classId = i;
        this.f$0 = rootWindowContainer;
        this.f$1 = str;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                RootWindowContainer rootWindowContainer = this.f$0;
                String str = this.f$1;
                ActivityRecord activityRecord = (ActivityRecord) obj;
                rootWindowContainer.getClass();
                if ((activityRecord.info.flags & 256) == 0) {
                    boolean z = false;
                    if (activityRecord.isActivityTypeAssistant() && str != "assist") {
                        z = rootWindowContainer.mWmService.mAssistantOnTopOfDream;
                    }
                    if (!z) {}
                }
                activityRecord.finishIfPossible(str, true);
                break;
            case 1:
                RootWindowContainer rootWindowContainer2 = this.f$0;
                String str2 = this.f$1;
                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                rootWindowContainer2.getClass();
                if ((activityRecord2.info.flags & 256) == 0) {
                    boolean z2 = false;
                    if (activityRecord2.isActivityTypeAssistant() && str2 != "assist") {
                        z2 = rootWindowContainer2.mWmService.mAssistantOnTopOfDream;
                    }
                    if (!z2) {}
                }
                activityRecord2.finishIfPossible(str2, true);
                break;
            default:
                RootWindowContainer rootWindowContainer3 = this.f$0;
                String str3 = this.f$1;
                TaskDisplayArea taskDisplayArea = (TaskDisplayArea) obj;
                rootWindowContainer3.getClass();
                if (taskDisplayArea.topRunningActivity(false) == null) {
                    WindowManagerService windowManagerService = rootWindowContainer3.mWmService;
                    rootWindowContainer3.startHomeOnTaskDisplayArea(
                            windowManagerService.mUmInternal.getUserAssignedToDisplay(
                                    taskDisplayArea.mDisplayContent.mDisplayId),
                            str3,
                            taskDisplayArea,
                            false,
                            false);
                    break;
                }
                break;
        }
    }
}
