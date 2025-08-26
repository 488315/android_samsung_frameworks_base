package com.android.wm.shell.draganddrop;

import android.app.ActivityManager;
import com.samsung.android.multiwindow.MultiWindowManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes3.dex */
public class VisibleTasks {
    public final int mDisplayId;
    public final Map mMap = new HashMap();

    public VisibleTasks(int i) {
        this.mDisplayId = i;
    }

    public final List getFullscreenTasks() {
        return new ArrayList((Collection) ((HashMap) this.mMap).getOrDefault(0, new ArrayList()));
    }

    public final List getTasksException(final int i) {
        return (List) ((HashMap) this.mMap).entrySet().stream().filter(new Predicate() { // from class: com.android.wm.shell.draganddrop.VisibleTasks$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((Integer) ((Map.Entry) obj).getKey()).intValue() != i;
            }
        }).flatMap(new VisibleTasks$$ExternalSyntheticLambda2()).collect(Collectors.toList());
    }

    public final void update() {
        ((HashMap) this.mMap).clear();
        List<ActivityManager.RunningTaskInfo> visibleTasks = MultiWindowManager.getInstance().getVisibleTasks();
        visibleTasks.removeIf(new VisibleTasks$$ExternalSyntheticLambda0(this));
        for (ActivityManager.RunningTaskInfo runningTaskInfo : visibleTasks) {
            int stagePosition = runningTaskInfo.configuration.windowConfiguration.getStagePosition();
            int windowingMode = runningTaskInfo.getWindowingMode();
            int i = 5;
            if (windowingMode != 5) {
                i = 1;
                if (windowingMode == 1) {
                    i = 0;
                } else if (stagePosition != 8) {
                    i = stagePosition != 16 ? stagePosition != 32 ? stagePosition != 64 ? -1 : 4 : 3 : 2;
                }
            }
            if (i != -1) {
                List arrayList = (List) ((HashMap) this.mMap).get(Integer.valueOf(i));
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    ((HashMap) this.mMap).put(Integer.valueOf(i), arrayList);
                }
                arrayList.add(runningTaskInfo);
            }
        }
    }
}
