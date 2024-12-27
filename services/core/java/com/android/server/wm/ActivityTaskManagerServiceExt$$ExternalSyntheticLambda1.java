package com.android.server.wm;

import android.app.WindowConfiguration;
import android.content.ComponentName;
import android.util.Log;
import com.samsung.android.desktopmode.DesktopModeFeature;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ActivityTaskManagerServiceExt$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ActivityTaskManagerServiceExt$$ExternalSyntheticLambda1(ActivityRecord activityRecord, AtomicBoolean atomicBoolean) {
        this.f$0 = activityRecord;
        this.f$1 = atomicBoolean;
    }

    public /* synthetic */ ActivityTaskManagerServiceExt$$ExternalSyntheticLambda1(ActivityTaskManagerServiceExt activityTaskManagerServiceExt, String str) {
        this.f$0 = activityTaskManagerServiceExt;
        this.f$1 = str;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ActivityRecord activityRecord = (ActivityRecord) this.f$0;
                AtomicBoolean atomicBoolean = (AtomicBoolean) this.f$1;
                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                if (WindowConfiguration.inMultiWindowMode(activityRecord2.getWindowingMode()) && activityRecord2.packageName.equals(activityRecord.packageName) && !activityRecord2.equals(activityRecord)) {
                    atomicBoolean.set(true);
                    break;
                }
                break;
            default:
                ActivityTaskManagerServiceExt activityTaskManagerServiceExt = (ActivityTaskManagerServiceExt) this.f$0;
                String str = (String) this.f$1;
                Task task = (Task) obj;
                activityTaskManagerServiceExt.getClass();
                if ((task.isActivityTypeHome() || task.isActivityTypeRecents()) && !task.isRootTask()) {
                    ComponentName componentName = task.realActivity;
                    if (componentName == null || !componentName.getPackageName().equals(str)) {
                        if (DesktopModeFeature.DEBUG) {
                            Log.d("ActivityTaskManagerServiceExt", "clearHomeStack(), removing task=" + task);
                        }
                        activityTaskManagerServiceExt.mService.mTaskSupervisor.removeTask(task, true, true, "ActivityTaskManagerServiceExt#clearHomeStack");
                        break;
                    }
                }
                break;
        }
    }
}
