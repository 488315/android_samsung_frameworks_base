package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.ArraySet;
import android.view.SurfaceControl;
import android.window.DesktopModeFlags;
import android.window.TransitionInfo;
import android.window.WindowContainerTransaction;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.multidesks.DesksOrganizer;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopConfig;
import com.android.wm.shell.shared.desktopmode.DesktopConfigImpl;
import com.android.wm.shell.sysui.UserChangeListener;
import com.android.wm.shell.transition.Transitions;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;

/* loaded from: classes3.dex */
public final class DesktopTasksLimiter {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final DesktopTasksLimiter$deskChangeListener$1 deskChangeListener;
    public final DesksOrganizer desksOrganizer;
    public final DesktopConfig desktopConfig;
    public DesktopTasksController desktopTasksController;
    public final DesktopUserRepositories desktopUserRepositories;
    public final DisplayController displayController;
    public Integer maxTasksLimit;
    public final MinimizeTransitionObserver minimizeTransitionObserver;
    public final ArrayList minimizingList;
    public final ShellTaskOrganizer shellTaskOrganizer;
    public int userId;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class LeftoverMinimizedTasksRemover implements DesktopRepository.ActiveTasksListener, UserChangeListener {
        public LeftoverMinimizedTasksRemover() {
        }

        @Override // com.android.wm.shell.desktopmode.DesktopRepository.ActiveTasksListener
        public final void onActiveTasksChanged(int i) {
            if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_BACK_NAVIGATION.isTrue()) {
                return;
            }
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            DesktopTasksLimiter desktopTasksLimiter = DesktopTasksLimiter.this;
            DesktopRepository current = desktopTasksLimiter.desktopUserRepositories.getCurrent();
            boolean zIsEmpty = ((ArrayList) current.getExpandedTasksOrdered(i)).isEmpty();
            ShellTaskOrganizer shellTaskOrganizer = desktopTasksLimiter.shellTaskOrganizer;
            if (zIsEmpty) {
                DesktopRepository.Desk activeDesk = current.desktopData.getActiveDesk(i);
                ArraySet arraySet = new ArraySet(activeDesk != null ? activeDesk.minimizedTasks : null);
                if (!arraySet.isEmpty()) {
                    DesktopTasksLimiter.logV("Removing leftover minimized tasks: %s", arraySet);
                    Iterator it = arraySet.iterator();
                    while (it.hasNext()) {
                        Integer num = (Integer) it.next();
                        num.getClass();
                        ActivityManager.RunningTaskInfo runningTaskInfo = shellTaskOrganizer.getRunningTaskInfo(num.intValue());
                        if (runningTaskInfo != null) {
                            windowContainerTransaction.removeTask(runningTaskInfo.token);
                        }
                    }
                }
            }
            shellTaskOrganizer.applyTransaction(windowContainerTransaction);
        }

        @Override // com.android.wm.shell.sysui.UserChangeListener
        public final void onUserChanged(int i, Context context) {
            DesktopTasksLimiter desktopTasksLimiter = DesktopTasksLimiter.this;
            desktopTasksLimiter.desktopUserRepositories.getProfile(desktopTasksLimiter.userId).activeTasksListeners.remove(this);
            desktopTasksLimiter.userId = i;
            desktopTasksLimiter.desktopUserRepositories.getProfile(i).activeTasksListeners.add(this);
        }
    }

    public final class MinimizeTransitionObserver implements Transitions.TransitionObserver {
        public final Map pendingTransitionTokensAndTasks = new LinkedHashMap();
        public final Map pendingTransitionMinimizeAllTokensAndTasks = new LinkedHashMap();
        public final Map activeTransitionTokensAndTasks = new LinkedHashMap();
        public final Map pendingUnminimizeTransitionTokensAndTasks = new LinkedHashMap();
        public final Map activeUnminimizeTransitionTokensAndTasks = new LinkedHashMap();

        public MinimizeTransitionObserver() {
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
        public final void onTransitionFinished(IBinder iBinder, boolean z) {
            this.pendingTransitionTokensAndTasks.remove(iBinder);
            this.activeUnminimizeTransitionTokensAndTasks.remove(iBinder);
            this.pendingUnminimizeTransitionTokensAndTasks.remove(iBinder);
            this.pendingTransitionMinimizeAllTokensAndTasks.remove(iBinder);
        }

        @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
        public final void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
            this.activeTransitionTokensAndTasks.remove(iBinder);
            TaskDetails taskDetails = (TaskDetails) this.pendingTransitionTokensAndTasks.remove(iBinder);
            if (taskDetails != null) {
                this.pendingTransitionTokensAndTasks.put(iBinder2, taskDetails);
            }
            this.activeUnminimizeTransitionTokensAndTasks.remove(iBinder);
            TaskDetails taskDetails2 = (TaskDetails) this.pendingUnminimizeTransitionTokensAndTasks.remove(iBinder);
            if (taskDetails2 != null) {
                this.pendingUnminimizeTransitionTokensAndTasks.put(iBinder2, taskDetails2);
            }
            TaskDetails taskDetails3 = (TaskDetails) this.pendingTransitionMinimizeAllTokensAndTasks.remove(iBinder);
            if (taskDetails3 != null) {
                this.pendingTransitionMinimizeAllTokensAndTasks.put(iBinder2, taskDetails3);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:42:0x00b0  */
        @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
            Set set;
            Object next;
            Object next2;
            Object next3;
            Object next4;
            Object next5;
            DesktopTasksLimiter desktopTasksLimiter = DesktopTasksLimiter.this;
            DesktopRepository current = desktopTasksLimiter.desktopUserRepositories.getCurrent();
            TaskDetails taskDetails = (TaskDetails) this.pendingTransitionTokensAndTasks.remove(iBinder);
            DesktopUserRepositories desktopUserRepositories = desktopTasksLimiter.desktopUserRepositories;
            if (taskDetails != null) {
                int i = taskDetails.taskId;
                if (current.isActiveTask(i)) {
                    Iterator it = transitionInfo.getChanges().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            next4 = null;
                            break;
                        }
                        next4 = it.next();
                        ActivityManager.RunningTaskInfo taskInfo = ((TransitionInfo.Change) next4).getTaskInfo();
                        if (taskInfo != null && taskInfo.taskId == i) {
                            break;
                        }
                    }
                    TransitionInfo.Change change = (TransitionInfo.Change) next4;
                    if (change == null ? !desktopUserRepositories.getCurrent().isVisibleTask(i) : change.getMode() == 4) {
                        taskDetails.transitionInfo = transitionInfo;
                        this.activeTransitionTokensAndTasks.put(iBinder, taskDetails);
                        Iterator it2 = transitionInfo.getChanges().iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                next5 = null;
                                break;
                            }
                            next5 = it2.next();
                            ActivityManager.RunningTaskInfo taskInfo2 = ((TransitionInfo.Change) next5).getTaskInfo();
                            if (taskInfo2 != null && taskInfo2.taskId == i) {
                                break;
                            }
                        }
                        TransitionInfo.Change change2 = (TransitionInfo.Change) next5;
                        current.boundsBeforeMinimizeByTaskId.set(i, new Rect(change2 != null ? change2.getStartAbsBounds() : null));
                        DesktopTasksLimiter.access$minimizeTask(desktopTasksLimiter, taskDetails.displayId, i);
                    } else {
                        DesktopTasksLimiter.logV("task %d is not reordered to back nor invis", Integer.valueOf(i));
                        if (desktopTasksLimiter.minimizingList.contains(Integer.valueOf(i))) {
                            Iterator it3 = transitionInfo.getChanges().iterator();
                            while (true) {
                                if (!it3.hasNext()) {
                                    next3 = null;
                                    break;
                                }
                                next3 = it3.next();
                                ActivityManager.RunningTaskInfo taskInfo3 = ((TransitionInfo.Change) next3).getTaskInfo();
                                if (taskInfo3 != null && taskInfo3.taskId == i) {
                                    break;
                                }
                            }
                            TransitionInfo.Change change3 = (TransitionInfo.Change) next3;
                            current.boundsBeforeMinimizeByTaskId.set(i, new Rect(change3 != null ? change3.getStartAbsBounds() : null));
                            DesktopTasksLimiter.logV("task %d is saved into repository for minimizing", Integer.valueOf(i));
                        }
                    }
                }
            }
            TaskDetails taskDetails2 = (TaskDetails) this.pendingUnminimizeTransitionTokensAndTasks.remove(iBinder);
            if (taskDetails2 != null) {
                this.activeUnminimizeTransitionTokensAndTasks.put(iBinder, taskDetails2);
            }
            TaskDetails taskDetails3 = (TaskDetails) this.pendingTransitionMinimizeAllTokensAndTasks.remove(iBinder);
            if (taskDetails3 == null || (set = taskDetails3.taskIds) == null) {
                return;
            }
            Iterator it4 = set.iterator();
            while (it4.hasNext()) {
                int iIntValue = ((Number) it4.next()).intValue();
                Iterator it5 = transitionInfo.getChanges().iterator();
                while (true) {
                    if (!it5.hasNext()) {
                        next2 = null;
                        break;
                    }
                    next2 = it5.next();
                    ActivityManager.RunningTaskInfo taskInfo4 = ((TransitionInfo.Change) next2).getTaskInfo();
                    if (taskInfo4 != null && taskInfo4.taskId == iIntValue) {
                        break;
                    }
                }
                TransitionInfo.Change change4 = (TransitionInfo.Change) next2;
                if (!(change4 == null ? !desktopUserRepositories.getCurrent().isVisibleTask(iIntValue) : change4.getMode() == 4)) {
                    DesktopTasksLimiter.logV("task %d is not reordered to back nor invis", Integer.valueOf(iIntValue));
                }
            }
            taskDetails3.transitionInfo = transitionInfo;
            this.activeTransitionTokensAndTasks.put(iBinder, taskDetails3);
            Iterator it6 = taskDetails3.taskIds.iterator();
            while (it6.hasNext()) {
                int iIntValue2 = ((Number) it6.next()).intValue();
                Iterator it7 = transitionInfo.getChanges().iterator();
                while (true) {
                    if (!it7.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it7.next();
                    ActivityManager.RunningTaskInfo taskInfo5 = ((TransitionInfo.Change) next).getTaskInfo();
                    if (taskInfo5 != null && taskInfo5.taskId == iIntValue2) {
                        break;
                    }
                }
                TransitionInfo.Change change5 = (TransitionInfo.Change) next;
                current.boundsBeforeMinimizeByTaskId.set(iIntValue2, new Rect(change5 != null ? change5.getStartAbsBounds() : null));
                DesktopTasksLimiter.access$minimizeTask(desktopTasksLimiter, taskDetails3.displayId, iIntValue2);
            }
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r5v2, types: [com.android.wm.shell.desktopmode.DesktopTasksLimiter$deskChangeListener$1, java.lang.Object] */
    public DesktopTasksLimiter(Transitions transitions, DesktopUserRepositories desktopUserRepositories, ShellTaskOrganizer shellTaskOrganizer, DesksOrganizer desksOrganizer, Integer num, InteractionJankMonitor interactionJankMonitor, Context context, final Handler handler, DisplayController displayController, DesktopConfig desktopConfig, ShellExecutor shellExecutor) throws Resources.NotFoundException, Settings.SettingNotFoundException {
        int iIntValue;
        this.desktopUserRepositories = desktopUserRepositories;
        this.shellTaskOrganizer = shellTaskOrganizer;
        this.desksOrganizer = desksOrganizer;
        this.maxTasksLimit = num;
        this.context = context;
        this.displayController = displayController;
        this.desktopConfig = desktopConfig;
        MinimizeTransitionObserver minimizeTransitionObserver = new MinimizeTransitionObserver();
        this.minimizeTransitionObserver = minimizeTransitionObserver;
        LeftoverMinimizedTasksRemover leftoverMinimizedTasksRemover = new LeftoverMinimizedTasksRemover();
        this.minimizingList = new ArrayList();
        ?? r5 = new DesktopRepository.DeskChangeListener() { // from class: com.android.wm.shell.desktopmode.DesktopTasksLimiter$deskChangeListener$1
            @Override // com.android.wm.shell.desktopmode.DesktopRepository.DeskChangeListener
            public final void onActiveDeskChanged(int i, int i2, int i3) {
                DesktopTasksLimiter desktopTasksLimiter = this.this$0;
                if (i2 != -1) {
                    DesktopTasksLimiter.logV("onActiveDeskChanged activeDeskId=%s minimizingList=%s", Integer.valueOf(i2), desktopTasksLimiter.minimizingList);
                    ArrayList arrayList = desktopTasksLimiter.minimizingList;
                    int size = arrayList.size();
                    int i4 = 0;
                    while (i4 < size) {
                        Object obj = arrayList.get(i4);
                        i4++;
                        DesktopTasksLimiter.access$minimizeTask(desktopTasksLimiter, i, ((Number) obj).intValue());
                    }
                }
                desktopTasksLimiter.minimizingList.clear();
            }

            @Override // com.android.wm.shell.desktopmode.DesktopRepository.DeskChangeListener
            public final void onCanCreateDesksChanged(boolean z) {
            }

            @Override // com.android.wm.shell.desktopmode.DesktopRepository.DeskChangeListener
            public final void onDeskAdded(int i, int i2) {
            }

            @Override // com.android.wm.shell.desktopmode.DesktopRepository.DeskChangeListener
            public final void onDeskRemoved(int i, int i2) {
            }
        };
        this.deskChangeListener = r5;
        Integer num2 = this.maxTasksLimit;
        if (num2 != null && (iIntValue = num2.intValue()) <= 0) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(iIntValue, "DesktopTasksLimiter: maxTasksLimit should be greater than 0. Current value: ", ".").toString());
        }
        transitions.registerObserver(minimizeTransitionObserver);
        this.userId = ActivityManager.getCurrentUser();
        desktopUserRepositories.getCurrent().activeTasksListeners.add(leftoverMinimizedTasksRemover);
        context.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("max_desktop_windowing_active_tasks"), false, new ContentObserver(handler) { // from class: com.android.wm.shell.desktopmode.DesktopTasksLimiter$registerContentObserver$settingsObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) throws Resources.NotFoundException, Settings.SettingNotFoundException {
                super.onChange(z, uri);
                DesktopTasksLimiter desktopTasksLimiter = this.this$0;
                int i = DesktopTasksLimiter.$r8$clinit;
                desktopTasksLimiter.updateMaxTasksLimit();
            }
        });
        updateMaxTasksLimit();
        desktopUserRepositories.getCurrent().deskChangeListeners.put(r5, shellExecutor);
        Integer num3 = this.maxTasksLimit;
        if (num3 != null) {
            logV("Starting limiter with a maximum of %d tasks", num3);
        } else {
            logV("Starting limiter without the task limit", new Object[0]);
        }
    }

    public static final void access$minimizeTask(DesktopTasksLimiter desktopTasksLimiter, int i, int i2) {
        Object[] objArr = {Integer.valueOf(i2), Integer.valueOf(i)};
        desktopTasksLimiter.getClass();
        logV("Minimize taskId=%d, displayId=%d", objArr);
        desktopTasksLimiter.desktopUserRepositories.getCurrent().minimizeTask(i, i2);
    }

    public static List createOrderedTaskListWithGivenTaskInFront(List list, Integer num) {
        if (num == null) {
            return list;
        }
        List listSingletonList = Collections.singletonList(num);
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (((Number) obj).intValue() != num.intValue()) {
                arrayList.add(obj);
            }
        }
        return CollectionsKt___CollectionsKt.plus((Iterable) arrayList, (Collection) listSingletonList);
    }

    public static List getTaskIdsToMinimize$default(DesktopTasksLimiter desktopTasksLimiter, List list, Integer num) {
        desktopTasksLimiter.getClass();
        List listCreateOrderedTaskListWithGivenTaskInFront = createOrderedTaskListWithGivenTaskInFront(list, num);
        int size = listCreateOrderedTaskListWithGivenTaskInFront.size();
        Integer num2 = desktopTasksLimiter.maxTasksLimit;
        if (size <= (num2 != null ? num2.intValue() : 5)) {
            logV("No need to minimize; tasks below limit", new Object[0]);
            return null;
        }
        int size2 = listCreateOrderedTaskListWithGivenTaskInFront.size();
        Integer num3 = desktopTasksLimiter.maxTasksLimit;
        List listTakeLast = CollectionsKt___CollectionsKt.takeLast(size2 - (num3 != null ? num3.intValue() : 5), listCreateOrderedTaskListWithGivenTaskInFront);
        logV("getTaskIdsToMinimize %s", listTakeLast.toString());
        return listTakeLast;
    }

    public static void logV(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strConcat = "%s: ".concat(str);
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopTasksLimiter", objArr);
        ProtoLog.v(shellProtoLogGroup, strConcat, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }

    public final void addPendingMinimizeChanges(IBinder iBinder, int i, List list, DesktopModeEventLogger.Companion.MinimizeReason minimizeReason) {
        int size = list.size();
        MinimizeTransitionObserver minimizeTransitionObserver = this.minimizeTransitionObserver;
        if (size > 1) {
            minimizeTransitionObserver.pendingTransitionMinimizeAllTokensAndTasks.put(iBinder, new TaskDetails(i, -1, CollectionsKt___CollectionsKt.toSet(list), null, minimizeReason, null, 32, null));
        } else {
            Integer num = (Integer) CollectionsKt___CollectionsKt.firstOrNull(list);
            if (num != null) {
                minimizeTransitionObserver.pendingTransitionTokensAndTasks.put(iBinder, new TaskDetails(i, num.intValue(), null, null, minimizeReason, null, 36, null));
            }
        }
        DesktopRepository current = this.desktopUserRepositories.getCurrent();
        Integer num2 = (Integer) CollectionsKt___CollectionsKt.firstOrNull(list);
        Integer deskIdForTask = num2 != null ? current.getDeskIdForTask(num2.intValue()) : null;
        if (deskIdForTask != null) {
            current.isDeskActive(deskIdForTask.intValue());
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (!this.minimizingList.contains(Integer.valueOf(((Number) obj).intValue()))) {
                arrayList.add(obj);
            }
        }
        this.minimizingList.addAll(arrayList);
        arrayList.isEmpty();
    }

    public final Integer getTaskIdToMinimize(List list, Integer num, boolean z) {
        List listCreateOrderedTaskListWithGivenTaskInFront = createOrderedTaskListWithGivenTaskInFront(list, num);
        int size = listCreateOrderedTaskListWithGivenTaskInFront.size() + (z ? 1 : 0);
        Integer num2 = this.maxTasksLimit;
        if (size > (num2 != null ? num2.intValue() : Integer.MAX_VALUE)) {
            return (Integer) CollectionsKt___CollectionsKt.last(listCreateOrderedTaskListWithGivenTaskInFront);
        }
        logV("No need to minimize; tasks below limit", new Object[0]);
        return null;
    }

    public final Transitions.TransitionObserver getTransitionObserver() {
        return this.minimizeTransitionObserver;
    }

    public final void updateMaxTasksLimit() throws Resources.NotFoundException, Settings.SettingNotFoundException {
        int i;
        Collection collection;
        int i2;
        try {
            i = Settings.Secure.getInt(this.context.getContentResolver(), "max_desktop_windowing_active_tasks");
        } catch (Settings.SettingNotFoundException unused) {
            i = 0;
        }
        if (i > 0) {
            Integer num = this.maxTasksLimit;
            if (num != null && i == num.intValue()) {
                return;
            }
            int iMin = Math.min(i, 15);
            this.maxTasksLimit = Integer.valueOf(iMin);
            DesktopConfigImpl desktopConfigImpl = (DesktopConfigImpl) this.desktopConfig;
            if (desktopConfigImpl.maxTaskLimit != iMin && iMin > 0) {
                desktopConfigImpl.maxTaskLimit = iMin;
            }
            DesktopRepository current = this.desktopUserRepositories.getCurrent();
            Iterator it = current.getAllDeskIds().iterator();
            while (it.hasNext()) {
                int iIntValue = ((Number) it.next()).intValue();
                if (current.isDeskActive(iIntValue)) {
                    DesktopRepository.Desk desk = current.desktopData.getDesk(iIntValue);
                    if (desk == null || (collection = desk.visibleTasks) == null) {
                        collection = EmptyList.INSTANCE;
                    }
                    List taskIdsToMinimize$default = getTaskIdsToMinimize$default(this, CollectionsKt___CollectionsKt.reversed(new ArrayList(collection)), null);
                    if (taskIdsToMinimize$default != null) {
                        ActivityManager.RunningTaskInfo runningTaskInfo = this.shellTaskOrganizer.getRunningTaskInfo(((Number) CollectionsKt___CollectionsKt.first(taskIdsToMinimize$default)).intValue());
                        if (runningTaskInfo != null && (i2 = runningTaskInfo.displayId) != -1) {
                            DesktopTasksController desktopTasksController = this.desktopTasksController;
                            (desktopTasksController != null ? desktopTasksController : null).minimizeTasks(taskIdsToMinimize$default, iIntValue, i2);
                        }
                    }
                }
            }
        }
    }

    public final class TaskDetails {
        public final int displayId;
        public final DesktopModeEventLogger.Companion.MinimizeReason minimizeReason;
        public final int taskId;
        public final Set taskIds;
        public TransitionInfo transitionInfo;
        public final DesktopModeEventLogger.Companion.UnminimizeReason unminimizeReason;

        public TaskDetails(int i, int i2, Set<Integer> set, TransitionInfo transitionInfo, DesktopModeEventLogger.Companion.MinimizeReason minimizeReason, DesktopModeEventLogger.Companion.UnminimizeReason unminimizeReason) {
            this.displayId = i;
            this.taskId = i2;
            this.taskIds = set;
            this.transitionInfo = transitionInfo;
            this.minimizeReason = minimizeReason;
            this.unminimizeReason = unminimizeReason;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TaskDetails)) {
                return false;
            }
            TaskDetails taskDetails = (TaskDetails) obj;
            return this.displayId == taskDetails.displayId && this.taskId == taskDetails.taskId && Intrinsics.areEqual(this.taskIds, taskDetails.taskIds) && Intrinsics.areEqual(this.transitionInfo, taskDetails.transitionInfo) && this.minimizeReason == taskDetails.minimizeReason && this.unminimizeReason == taskDetails.unminimizeReason;
        }

        public final int hashCode() {
            int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.taskId, Integer.hashCode(this.displayId) * 31, 31);
            Set set = this.taskIds;
            int iHashCode = (iM + (set == null ? 0 : set.hashCode())) * 31;
            TransitionInfo transitionInfo = this.transitionInfo;
            int iHashCode2 = (iHashCode + (transitionInfo == null ? 0 : transitionInfo.hashCode())) * 31;
            DesktopModeEventLogger.Companion.MinimizeReason minimizeReason = this.minimizeReason;
            int iHashCode3 = (iHashCode2 + (minimizeReason == null ? 0 : minimizeReason.hashCode())) * 31;
            DesktopModeEventLogger.Companion.UnminimizeReason unminimizeReason = this.unminimizeReason;
            return iHashCode3 + (unminimizeReason != null ? unminimizeReason.hashCode() : 0);
        }

        public final String toString() {
            return "TaskDetails(displayId=" + this.displayId + ", taskId=" + this.taskId + ", taskIds=" + this.taskIds + ", transitionInfo=" + this.transitionInfo + ", minimizeReason=" + this.minimizeReason + ", unminimizeReason=" + this.unminimizeReason + ")";
        }

        public /* synthetic */ TaskDetails(int i, int i2, Set set, TransitionInfo transitionInfo, DesktopModeEventLogger.Companion.MinimizeReason minimizeReason, DesktopModeEventLogger.Companion.UnminimizeReason unminimizeReason, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2, (i3 & 4) != 0 ? null : set, (i3 & 8) != 0 ? null : transitionInfo, (i3 & 16) != 0 ? null : minimizeReason, (i3 & 32) != 0 ? null : unminimizeReason);
        }
    }

    public static /* synthetic */ void getLeftoverMinimizedTasksRemover$annotations() {
    }
}
