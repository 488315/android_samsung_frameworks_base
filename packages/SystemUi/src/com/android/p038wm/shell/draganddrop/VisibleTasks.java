package com.android.p038wm.shell.draganddrop;

import android.app.ActivityManager;
import com.samsung.android.multiwindow.MultiWindowManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VisibleTasks {
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

    public final List getVisibleTasks() {
        List visibleTasks = MultiWindowManager.getInstance().getVisibleTasks();
        visibleTasks.removeIf(new Predicate() { // from class: com.android.wm.shell.draganddrop.VisibleTasks$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                VisibleTasks visibleTasks2 = VisibleTasks.this;
                visibleTasks2.getClass();
                return ((ActivityManager.RunningTaskInfo) obj).displayId != visibleTasks2.mDisplayId;
            }
        });
        return visibleTasks;
    }

    public final void update() {
        HashMap hashMap = (HashMap) this.mMap;
        hashMap.clear();
        for (ActivityManager.RunningTaskInfo runningTaskInfo : getVisibleTasks()) {
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
                List list = (List) hashMap.get(Integer.valueOf(i));
                if (list == null) {
                    list = new ArrayList();
                    hashMap.put(Integer.valueOf(i), list);
                }
                list.add(runningTaskInfo);
            }
        }
    }
}
