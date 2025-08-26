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

/* loaded from: classes3.dex */
public final class DesksTransitionObserver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DesksOrganizer desksOrganizer;
    public final DesktopUserRepositories desktopUserRepositories;
    public final Map deskTransitions = new LinkedHashMap();
    public final Map activeDeskTransitions = new LinkedHashMap();

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
        String strConcat = "%s: ".concat(str);
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesksTransitionObserver", objArr);
        ProtoLog.d(shellProtoLogGroup, strConcat, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }

    public final void addPendingTransition(DeskTransition deskTransition) {
        if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
            Collection linkedHashSet = (Set) ((LinkedHashMap) this.deskTransitions).get(deskTransition.getToken());
            if (linkedHashSet == null) {
                linkedHashSet = new LinkedHashSet();
            }
            linkedHashSet.add(deskTransition);
            this.deskTransitions.put(deskTransition.getToken(), linkedHashSet);
            logD("Added pending desk transition: %s", deskTransition);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleDeactivateDeskTransition(TransitionInfo transitionInfo, DeskTransition.DeactivateDesk deactivateDesk) {
        List changes;
        int i;
        Object next;
        int i2;
        Integer deskIdForTask;
        logD("handleDeactivateDeskTransition: %s", deactivateDesk);
        int i3 = deactivateDesk.userId;
        DesktopUserRepositories desktopUserRepositories = this.desktopUserRepositories;
        DesktopRepository profile = i3 != -1 ? desktopUserRepositories.getProfile(i3) : desktopUserRepositories.getCurrent();
        if (transitionInfo == null || (changes = transitionInfo.getChanges()) == null) {
            changes = EmptyList.INSTANCE;
        }
        Iterator it = changes.iterator();
        boolean z = false;
        while (true) {
            boolean zHasNext = it.hasNext();
            i = deactivateDesk.deskId;
            if (!zHasNext) {
                break;
            }
            TransitionInfo.Change change = (TransitionInfo.Change) it.next();
            change.getClass();
            RootTaskDesksOrganizer rootTaskDesksOrganizer = (RootTaskDesksOrganizer) this.desksOrganizer;
            SparseArray sparseArray = rootTaskDesksOrganizer.deskRootsByDeskId;
            ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
            if (sparseArray.contains(taskInfo != null ? taskInfo.taskId : -1)) {
                ActivityManager.RunningTaskInfo taskInfo2 = change.getTaskInfo();
                if ((taskInfo2 != null ? taskInfo2.taskId : -1) != i) {
                }
                z = true;
            } else {
                Iterator it2 = ((LinkedHashMap) rootTaskDesksOrganizer.deskMinimizationRootsByDeskId).values().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it2.next();
                    int i4 = ((RootTaskDesksOrganizer.DeskMinimizationRoot) next).taskInfo.taskId;
                    ActivityManager.RunningTaskInfo taskInfo3 = change.getTaskInfo();
                    if (i4 == (taskInfo3 != null ? taskInfo3.taskId : -1)) {
                        break;
                    }
                }
                RootTaskDesksOrganizer.DeskMinimizationRoot deskMinimizationRoot = (RootTaskDesksOrganizer.DeskMinimizationRoot) next;
                if (deskMinimizationRoot == null || deskMinimizationRoot.deskId != i) {
                    ActivityManager.RunningTaskInfo taskInfo4 = change.getTaskInfo();
                    if (taskInfo4 != null && (deskIdForTask = profile.getDeskIdForTask((i2 = taskInfo4.taskId))) != null && deskIdForTask.intValue() == i && rootTaskDesksOrganizer.getDeskAtEnd(change) == null) {
                        profile.removeTaskFromDesk(i, i2);
                    }
                } else {
                    z = true;
                }
            }
        }
        if (!z) {
            logD("Deactivating desk without transition change", new Object[0]);
        }
        profile.setDeskInactive(i);
    }
}
