package com.android.wm.shell.desktopmode.multidesks;

import android.app.ActivityManager;
import android.util.SparseArray;
import android.window.DesktopExperienceFlags;
import android.window.TransitionInfo;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.desktopmode.DesktopDisplayEventHandler$$ExternalSyntheticOutline0;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.multidesks.DeskTransition;
import com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesksTransitionObserver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DesksOrganizer desksOrganizer;
    public final DesktopUserRepositories desktopUserRepositories;
    public final Map deskTransitions = new LinkedHashMap();
    public final Map activeDeskTransitions = new LinkedHashMap();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public DesksTransitionObserver(DesktopUserRepositories desktopUserRepositories, DesksOrganizer desksOrganizer) {
        this.desktopUserRepositories = desktopUserRepositories;
        this.desksOrganizer = desksOrganizer;
    }

    public static List getToFrontTaskIdsInDesk(TransitionInfo transitionInfo, int i) {
        ActivityManager.RunningTaskInfo taskInfo;
        List changes = transitionInfo.getChanges();
        ArrayList arrayList = new ArrayList();
        for (Object obj : changes) {
            TransitionInfo.Change change = (TransitionInfo.Change) obj;
            if (change.getMode() == 3 && (taskInfo = change.getTaskInfo()) != null && taskInfo.parentTaskId == i) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj2 = arrayList.get(i2);
            i2++;
            ActivityManager.RunningTaskInfo taskInfo2 = ((TransitionInfo.Change) obj2).getTaskInfo();
            taskInfo2.getClass();
            arrayList2.add(Integer.valueOf(taskInfo2.taskId));
        }
        return arrayList2;
    }

    public static void logD(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String concat = "%s: ".concat(str);
        SpreadBuilder m = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesksTransitionObserver", objArr);
        ProtoLog.d(shellProtoLogGroup, concat, m.list.toArray(new Object[m.list.size()]));
    }

    public final void addPendingTransition(DeskTransition deskTransition) {
        if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
            Collection collection = (Set) ((LinkedHashMap) this.deskTransitions).get(deskTransition.getToken());
            if (collection == null) {
                collection = new LinkedHashSet();
            }
            collection.add(deskTransition);
            this.deskTransitions.put(deskTransition.getToken(), collection);
            logD("Added pending desk transition: %s", deskTransition);
        }
    }

    public final void handleDeactivateDeskTransition(TransitionInfo transitionInfo, DeskTransition.DeactivateDesk deactivateDesk) {
        List<TransitionInfo.Change> list;
        Object obj;
        int i;
        Integer deskIdForTask;
        logD("handleDeactivateDeskTransition: %s", deactivateDesk);
        DesktopRepository current = this.desktopUserRepositories.getCurrent();
        if (transitionInfo == null || (list = transitionInfo.getChanges()) == null) {
            list = EmptyList.INSTANCE;
        }
        boolean z = false;
        for (TransitionInfo.Change change : list) {
            change.getClass();
            int i2 = deactivateDesk.deskId;
            RootTaskDesksOrganizer rootTaskDesksOrganizer = (RootTaskDesksOrganizer) this.desksOrganizer;
            SparseArray sparseArray = rootTaskDesksOrganizer.deskRootsByDeskId;
            ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
            if (sparseArray.contains(taskInfo != null ? taskInfo.taskId : -1)) {
                ActivityManager.RunningTaskInfo taskInfo2 = change.getTaskInfo();
                if ((taskInfo2 != null ? taskInfo2.taskId : -1) == i2) {
                    z = true;
                }
            }
            Iterator it = ((LinkedHashMap) rootTaskDesksOrganizer.deskMinimizationRootsByDeskId).values().iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it.next();
                int i3 = ((RootTaskDesksOrganizer.DeskMinimizationRoot) obj).taskInfo.taskId;
                ActivityManager.RunningTaskInfo taskInfo3 = change.getTaskInfo();
                if (i3 == (taskInfo3 != null ? taskInfo3.taskId : -1)) {
                    break;
                }
            }
            RootTaskDesksOrganizer.DeskMinimizationRoot deskMinimizationRoot = (RootTaskDesksOrganizer.DeskMinimizationRoot) obj;
            if (deskMinimizationRoot == null || deskMinimizationRoot.deskId != i2) {
                ActivityManager.RunningTaskInfo taskInfo4 = change.getTaskInfo();
                if (taskInfo4 != null && (deskIdForTask = current.getDeskIdForTask((i = taskInfo4.taskId))) != null) {
                    int intValue = deskIdForTask.intValue();
                    int i4 = deactivateDesk.deskId;
                    if (intValue == i4 && rootTaskDesksOrganizer.getDeskAtEnd(change) == null) {
                        current.removeTaskFromDesk(i4, i);
                    }
                }
            } else {
                z = true;
            }
        }
        if (!z) {
            logD("Deactivating desk without transition change", new Object[0]);
        }
        current.setDeskInactive(deactivateDesk.deskId);
    }
}
