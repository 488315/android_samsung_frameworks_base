package com.android.wm.shell.desktopmode.multidesks;

import android.app.ActivityManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.SparseArray;
import android.view.SurfaceControl;
import android.window.DesktopExperienceFlags;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.util.SparseArrayKt$valueIterator$1;
import com.android.internal.protolog.ProtoLog;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$$ExternalSyntheticOutline0;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.LaunchAdjacentController;
import com.android.wm.shell.desktopmode.DesktopDisplayEventHandler$$ExternalSyntheticOutline0;
import com.android.wm.shell.desktopmode.multidesks.DesksOrganizer;
import com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda7;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class RootTaskDesksOrganizer implements DesksOrganizer, ShellTaskOrganizer.TaskListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel;
    public final LaunchAdjacentController launchAdjacentController;
    public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda7 onTaskInfoChangedListener;
    public final RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer;
    public final ShellTaskOrganizer shellTaskOrganizer;
    public final List createDeskRootRequests = new ArrayList();
    public final SparseArray deskRootsByDeskId = new SparseArray();
    public final List createDeskMinimizationRootRequests = new ArrayList();
    public final Map deskMinimizationRootsByDeskId = new LinkedHashMap();
    public final Set removeDeskRootRequests = new LinkedHashSet();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CreateDeskMinimizationRootRequest {
        public final int deskId;
        public final int displayId;

        public CreateDeskMinimizationRootRequest(int i, int i2) {
            this.displayId = i;
            this.deskId = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CreateDeskMinimizationRootRequest)) {
                return false;
            }
            CreateDeskMinimizationRootRequest createDeskMinimizationRootRequest = (CreateDeskMinimizationRootRequest) obj;
            return this.displayId == createDeskMinimizationRootRequest.displayId && this.deskId == createDeskMinimizationRootRequest.deskId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.deskId) + (Integer.hashCode(this.displayId) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("CreateDeskMinimizationRootRequest(displayId=");
            sb.append(this.displayId);
            sb.append(", deskId=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.deskId, ")", sb);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CreateDeskRequest {
        public final int displayId;
        public final DesksOrganizer.OnCreateCallback onCreateCallback;
        public final Integer userId;

        public CreateDeskRequest(int i, Integer num, DesksOrganizer.OnCreateCallback onCreateCallback) {
            this.displayId = i;
            this.userId = num;
            this.onCreateCallback = onCreateCallback;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CreateDeskRequest)) {
                return false;
            }
            CreateDeskRequest createDeskRequest = (CreateDeskRequest) obj;
            return this.displayId == createDeskRequest.displayId && Intrinsics.areEqual(this.userId, createDeskRequest.userId) && Intrinsics.areEqual(this.onCreateCallback, createDeskRequest.onCreateCallback);
        }

        public final int hashCode() {
            int hashCode = Integer.hashCode(this.displayId) * 31;
            Integer num = this.userId;
            return this.onCreateCallback.hashCode() + ((hashCode + (num == null ? 0 : num.hashCode())) * 31);
        }

        public final String toString() {
            return "CreateDeskRequest(displayId=" + this.displayId + ", userId=" + this.userId + ", onCreateCallback=" + this.onCreateCallback + ")";
        }
    }

    static {
        new Companion(null);
    }

    public RootTaskDesksOrganizer(ShellInit shellInit, final ShellCommandHandler shellCommandHandler, ShellTaskOrganizer shellTaskOrganizer, LaunchAdjacentController launchAdjacentController, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer) {
        this.shellTaskOrganizer = shellTaskOrganizer;
        this.launchAdjacentController = launchAdjacentController;
        this.rootTaskDisplayAreaOrganizer = rootTaskDisplayAreaOrganizer;
        if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer.1
                @Override // java.lang.Runnable
                public final void run() {
                    ShellCommandHandler shellCommandHandler2 = ShellCommandHandler.this;
                    final RootTaskDesksOrganizer rootTaskDesksOrganizer = this;
                    shellCommandHandler2.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer.1.1
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            RootTaskDesksOrganizer.this.dump$2((PrintWriter) obj, (String) obj2);
                        }
                    }, this);
                }
            }, this);
        }
    }

    public static void logD$2(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String concat = "%s: ".concat(str);
        SpreadBuilder m = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "RootTaskDesksOrganizer", objArr);
        ProtoLog.d(shellProtoLogGroup, concat, m.list.toArray(new Object[m.list.size()]));
    }

    public static void logE$1(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("%s: ", str);
        SpreadBuilder m2 = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "RootTaskDesksOrganizer", objArr);
        ProtoLog.e(shellProtoLogGroup, m, m2.list.toArray(new Object[m2.list.size()]));
    }

    public static void logV$2(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String m = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("%s: ", str);
        SpreadBuilder m2 = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "RootTaskDesksOrganizer", objArr);
        ProtoLog.v(shellProtoLogGroup, m, m2.list.toArray(new Object[m2.list.size()]));
    }

    public final void activateDesk(WindowContainerTransaction windowContainerTransaction, int i) {
        logV$2("activateDesk %d", Integer.valueOf(i));
        Object obj = this.deskRootsByDeskId.get(i);
        if (obj == null) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Root not found for desk: ").toString());
        }
        windowContainerTransaction.reorder(((DeskRoot) obj).token, true);
        updateLaunchRoot(i, windowContainerTransaction, true);
    }

    public final void addChildToDesk(int i, int i2) {
        SparseArray sparseArray = this.deskRootsByDeskId;
        int size = sparseArray.size();
        for (int i3 = 0; i3 < size; i3++) {
            sparseArray.keyAt(i3);
            DeskRoot deskRoot = (DeskRoot) sparseArray.valueAt(i3);
            if (deskRoot.deskId == i2) {
                deskRoot.children.add(Integer.valueOf(i));
            } else {
                deskRoot.children.remove(Integer.valueOf(i));
            }
        }
        Iterator it = ((LinkedHashMap) this.deskMinimizationRootsByDeskId).values().iterator();
        while (it.hasNext()) {
            ((DeskMinimizationRoot) it.next()).children.remove(Integer.valueOf(i));
        }
    }

    public final void addChildToMinimizationRoot(final int i, final int i2) {
        Map map = this.deskMinimizationRootsByDeskId;
        final Function2 function2 = new Function2() { // from class: com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                RootTaskDesksOrganizer.DeskMinimizationRoot deskMinimizationRoot = (RootTaskDesksOrganizer.DeskMinimizationRoot) obj2;
                int i3 = RootTaskDesksOrganizer.$r8$clinit;
                int i4 = deskMinimizationRoot.deskId;
                int i5 = i2;
                int i6 = i;
                if (i4 == i5) {
                    deskMinimizationRoot.children.add(Integer.valueOf(i6));
                } else {
                    deskMinimizationRoot.children.remove(Integer.valueOf(i6));
                }
                return Unit.INSTANCE;
            }
        };
        ((LinkedHashMap) map).forEach(new BiConsumer() { // from class: com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer$sam$java_util_function_BiConsumer$0
            @Override // java.util.function.BiConsumer
            public final /* synthetic */ void accept(Object obj, Object obj2) {
                Function2.this.invoke(obj, obj2);
            }
        });
        SparseArray sparseArray = this.deskRootsByDeskId;
        int size = sparseArray.size();
        for (int i3 = 0; i3 < size; i3++) {
            sparseArray.keyAt(i3);
            DeskRoot deskRoot = (DeskRoot) sparseArray.valueAt(i3);
            deskRoot.children.remove(Integer.valueOf(i));
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void dump$2(PrintWriter printWriter, String str) {
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  ");
        QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str, "RootTaskDesksOrganizer");
        printWriter.println(m + "launchAdjacentEnabled=" + this.launchAdjacentController.launchAdjacentEnabled);
        printWriter.println(m + "createDeskRootRequests=" + this.createDeskRootRequests);
        printWriter.println(m + "removeDeskRootRequests=" + this.removeDeskRootRequests);
        printWriter.println(m + "numOfDeskRoots=" + this.deskRootsByDeskId.size());
        StringBuilder sb = new StringBuilder();
        sb.append(m);
        CarrierTextController$$ExternalSyntheticOutline0.m(sb, "Desk Roots:", printWriter);
        SparseArray sparseArray = this.deskRootsByDeskId;
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            int keyAt = sparseArray.keyAt(i);
            DeskRoot deskRoot = (DeskRoot) sparseArray.valueAt(i);
            DeskMinimizationRoot deskMinimizationRoot = (DeskMinimizationRoot) ((LinkedHashMap) this.deskMinimizationRootsByDeskId).get(Integer.valueOf(keyAt));
            printWriter.println(m + "  #" + keyAt + " visible=" + deskRoot.taskInfo.isVisible);
            int i2 = deskRoot.taskInfo.displayId;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(m);
            sb2.append("    displayId=");
            sb2.append(i2);
            printWriter.println(sb2.toString());
            printWriter.println(m + "    isLaunchRootRequested=" + deskRoot.isLaunchRootRequested);
            printWriter.println(m + "    children=" + deskRoot.children);
            printWriter.println(m + "    users=" + deskRoot.users);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(m);
            CarrierTextController$$ExternalSyntheticOutline0.m(sb3, "    minimization root:", printWriter);
            printWriter.println(m + "      rootId=" + (deskMinimizationRoot != null ? Integer.valueOf(deskMinimizationRoot.taskInfo.taskId) : null));
            if (deskMinimizationRoot != null) {
                printWriter.println(m + "      children=" + deskMinimizationRoot.children);
            }
        }
    }

    public final DeskRoot firstUnassignedDesk(int i, final int i2) {
        Object obj;
        FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filterNot(SequencesKt___SequencesKt.filterNot(SequencesKt__SequencesKt.asSequence(new SparseArrayKt$valueIterator$1(this.deskRootsByDeskId)), new Function1() { // from class: com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                int i3 = RootTaskDesksOrganizer.$r8$clinit;
                return Boolean.valueOf(((RootTaskDesksOrganizer.DeskRoot) obj2).users.contains(Integer.valueOf(i2)));
            }
        }), new Function1() { // from class: com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                return Boolean.valueOf(RootTaskDesksOrganizer.this.removeDeskRootRequests.contains(Integer.valueOf(((RootTaskDesksOrganizer.DeskRoot) obj2).deskId)));
            }
        }));
        while (true) {
            if (!filteringSequence$iterator$1.hasNext()) {
                obj = null;
                break;
            }
            obj = filteringSequence$iterator$1.next();
            if (((DeskRoot) obj).taskInfo.displayId == i) {
                break;
            }
        }
        return (DeskRoot) obj;
    }

    public final Integer getDeskAtEnd(TransitionInfo.Change change) {
        Object obj;
        ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
        if (taskInfo != null) {
            int i = taskInfo.parentTaskId;
            if (this.deskRootsByDeskId.contains(i)) {
                return Integer.valueOf(i);
            }
            Iterator it = ((LinkedHashMap) this.deskMinimizationRootsByDeskId).values().iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it.next();
                if (((DeskMinimizationRoot) obj).taskInfo.taskId == i) {
                    break;
                }
            }
            DeskMinimizationRoot deskMinimizationRoot = (DeskMinimizationRoot) obj;
            if (deskMinimizationRoot != null) {
                return Integer.valueOf(deskMinimizationRoot.deskId);
            }
        }
        return null;
    }

    public final void minimizeTask(WindowContainerTransaction windowContainerTransaction, int i, ActivityManager.RunningTaskInfo runningTaskInfo) {
        logV$2(ListImplementation$$ExternalSyntheticOutline0.m(runningTaskInfo.taskId, i, "minimizeTask task=", " desk="), new Object[0]);
        Object obj = this.deskRootsByDeskId.get(i);
        if (obj == null) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Root not found for desk: ").toString());
        }
        DeskRoot deskRoot = (DeskRoot) obj;
        Object obj2 = ((LinkedHashMap) this.deskMinimizationRootsByDeskId).get(Integer.valueOf(i));
        if (obj2 == null) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Minimization root not found for desk: ").toString());
        }
        DeskMinimizationRoot deskMinimizationRoot = (DeskMinimizationRoot) obj2;
        int i2 = runningTaskInfo.taskId;
        if (deskMinimizationRoot.children.contains(Integer.valueOf(i2))) {
            logV$2(ListImplementation$$ExternalSyntheticOutline0.m(i2, i, "Task #", " is already minimized in desk #"), new Object[0]);
        } else if (deskRoot.children.contains(Integer.valueOf(i2))) {
            windowContainerTransaction.reparent(runningTaskInfo.token, deskMinimizationRoot.token, true);
        } else {
            logE$1(MutableVectorKt$$ExternalSyntheticOutline0.m(runningTaskInfo.taskId, i, "Attempted to minimize task=", " in desk=", " but it was not a child"), new Object[0]);
        }
    }

    public final void moveTaskToDesk(WindowContainerTransaction windowContainerTransaction, int i, ActivityManager.RunningTaskInfo runningTaskInfo) {
        DeskRoot deskRoot = (DeskRoot) this.deskRootsByDeskId.get(i);
        if (deskRoot != null) {
            windowContainerTransaction.setWindowingMode(runningTaskInfo.token, 0);
            windowContainerTransaction.reparent(runningTaskInfo.token, deskRoot.taskInfo.token, true);
        } else {
            throw new IllegalStateException(("Root not found for desk: " + i).toString());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:71:0x004f, code lost:
    
        if (r6 == false) goto L11;
     */
    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onTaskAppeared(android.app.ActivityManager.RunningTaskInfo r15, android.view.SurfaceControl r16) {
        /*
            Method dump skipped, instructions count: 437
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer.onTaskAppeared(android.app.ActivityManager$RunningTaskInfo, android.view.SurfaceControl):void");
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        ActivityManager.RunningTaskInfo runningTaskInfo2;
        Object obj;
        Object obj2;
        if (this.deskRootsByDeskId.contains(runningTaskInfo.taskId)) {
            int i = runningTaskInfo.taskId;
            SparseArray sparseArray = this.deskRootsByDeskId;
            DeskRoot deskRoot = (DeskRoot) sparseArray.get(i);
            runningTaskInfo2 = runningTaskInfo;
            sparseArray.set(i, new DeskRoot(deskRoot.deskId, runningTaskInfo2, deskRoot.leash, deskRoot.children, deskRoot.users, deskRoot.isLaunchRootRequested));
            logV$2("Desk #" + i + "'s task info changed", new Object[0]);
        } else {
            runningTaskInfo2 = runningTaskInfo;
            Iterator it = ((LinkedHashMap) this.deskMinimizationRootsByDeskId).values().iterator();
            while (true) {
                obj = null;
                if (!it.hasNext()) {
                    obj2 = null;
                    break;
                } else {
                    obj2 = it.next();
                    if (((DeskMinimizationRoot) obj2).taskInfo.taskId == runningTaskInfo2.taskId) {
                        break;
                    }
                }
            }
            DeskMinimizationRoot deskMinimizationRoot = (DeskMinimizationRoot) obj2;
            if (deskMinimizationRoot != null) {
                Map map = this.deskMinimizationRootsByDeskId;
                int i2 = deskMinimizationRoot.deskId;
                map.remove(Integer.valueOf(i2));
                this.deskMinimizationRootsByDeskId.put(Integer.valueOf(i2), new DeskMinimizationRoot(i2, runningTaskInfo2, deskMinimizationRoot.leash, deskMinimizationRoot.children));
                logV$2("Minimization root for desk#" + i2 + " task info changed", new Object[0]);
            } else {
                int i3 = runningTaskInfo2.parentTaskId;
                if (this.deskRootsByDeskId.contains(i3)) {
                    int i4 = runningTaskInfo2.parentTaskId;
                    int i5 = runningTaskInfo2.taskId;
                    logV$2(ListImplementation$$ExternalSyntheticOutline0.m(i5, i4, "onTaskInfoChanged: Task #", " appeared in desk #"), new Object[0]);
                    addChildToDesk(i5, i4);
                } else {
                    Iterator it2 = ((LinkedHashMap) this.deskMinimizationRootsByDeskId).values().iterator();
                    boolean z = false;
                    Object obj3 = null;
                    while (true) {
                        if (it2.hasNext()) {
                            Object next = it2.next();
                            if (((DeskMinimizationRoot) next).taskInfo.taskId == i3) {
                                if (z) {
                                    break;
                                }
                                z = true;
                                obj3 = next;
                            }
                        } else if (z) {
                            obj = obj3;
                        }
                    }
                    DeskMinimizationRoot deskMinimizationRoot2 = (DeskMinimizationRoot) obj;
                    if (deskMinimizationRoot2 != null) {
                        int i6 = runningTaskInfo2.taskId;
                        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i6, "onTaskInfoChanged: Task #", " was minimized in desk #");
                        int i7 = deskMinimizationRoot2.deskId;
                        logV$2(ReorderTile$$ExternalSyntheticOutline0.m(i7, " ", m), new Object[0]);
                        addChildToMinimizationRoot(i6, i7);
                    } else {
                        logE$1(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(runningTaskInfo2.taskId, "onTaskInfoChanged: unknown task: "), new Object[0]);
                    }
                }
            }
        }
        if (!this.deskRootsByDeskId.contains(runningTaskInfo2.taskId)) {
            Collection values = ((LinkedHashMap) this.deskMinimizationRootsByDeskId).values();
            if (!(values instanceof Collection) || !values.isEmpty()) {
                Iterator it3 = values.iterator();
                while (it3.hasNext()) {
                    if (((DeskMinimizationRoot) it3.next()).taskInfo.taskId == runningTaskInfo2.taskId) {
                        break;
                    }
                }
            }
            DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda7 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda7 = this.onTaskInfoChangedListener;
            if (desktopModeWindowDecorViewModel$$ExternalSyntheticLambda7 != null) {
                desktopModeWindowDecorViewModel$$ExternalSyntheticLambda7.mo779invoke(runningTaskInfo2);
            }
        }
        updateLaunchAdjacentController();
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x0063, code lost:
    
        if (r3 == false) goto L11;
     */
    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onTaskVanished(android.app.ActivityManager.RunningTaskInfo r10) {
        /*
            Method dump skipped, instructions count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer.onTaskVanished(android.app.ActivityManager$RunningTaskInfo):void");
    }

    public final void reorderTaskToFront(WindowContainerTransaction windowContainerTransaction, int i, ActivityManager.RunningTaskInfo runningTaskInfo) {
        logV$2(ListImplementation$$ExternalSyntheticOutline0.m(runningTaskInfo.taskId, i, "reorderTaskToFront task=", " desk="), new Object[0]);
        DeskRoot deskRoot = (DeskRoot) this.deskRootsByDeskId.get(i);
        if (deskRoot == null) {
            throw new IllegalStateException(("Root not found for desk: " + i).toString());
        }
        if (deskRoot.children.contains(Integer.valueOf(runningTaskInfo.taskId))) {
            windowContainerTransaction.reorder(runningTaskInfo.token, true, true);
            return;
        }
        Object obj = ((LinkedHashMap) this.deskMinimizationRootsByDeskId).get(Integer.valueOf(i));
        if (obj == null) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Minimization root not found for desk: ").toString());
        }
        if (!((DeskMinimizationRoot) obj).children.contains(Integer.valueOf(runningTaskInfo.taskId))) {
            logE$1(MutableVectorKt$$ExternalSyntheticOutline0.m(runningTaskInfo.taskId, i, "Attempted to reorder task=", " in desk=", " but it was not a child"), new Object[0]);
            return;
        }
        int i2 = runningTaskInfo.taskId;
        logV$2(ListImplementation$$ExternalSyntheticOutline0.m(i2, i, "unminimizeTask task=", " desk="), new Object[0]);
        Object obj2 = this.deskRootsByDeskId.get(i);
        if (obj2 == null) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Root not found for desk: ").toString());
        }
        DeskRoot deskRoot2 = (DeskRoot) obj2;
        Object obj3 = ((LinkedHashMap) this.deskMinimizationRootsByDeskId).get(Integer.valueOf(i));
        if (obj3 == null) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Minimization root not found for desk: ").toString());
        }
        DeskMinimizationRoot deskMinimizationRoot = (DeskMinimizationRoot) obj3;
        if (deskRoot2.children.contains(Integer.valueOf(i2))) {
            logV$2(ListImplementation$$ExternalSyntheticOutline0.m(i2, i, "Task #", " is already unminimized in desk="), new Object[0]);
        } else if (deskMinimizationRoot.children.contains(Integer.valueOf(i2))) {
            windowContainerTransaction.reparent(runningTaskInfo.token, deskRoot2.token, true);
        } else {
            logE$1(MutableVectorKt$$ExternalSyntheticOutline0.m(i2, i, "Attempted to unminimize task=", " in desk=", " but it was not a child"), new Object[0]);
        }
        windowContainerTransaction.reorder(runningTaskInfo.token, true, true);
    }

    public final void updateLaunchAdjacentController() {
        SparseArray sparseArray = this.deskRootsByDeskId;
        int size = sparseArray.size();
        int i = 0;
        while (true) {
            LaunchAdjacentController launchAdjacentController = this.launchAdjacentController;
            if (i >= size) {
                launchAdjacentController.setLaunchAdjacentEnabled(true);
                return;
            }
            sparseArray.keyAt(i);
            if (((DeskRoot) sparseArray.valueAt(i)).taskInfo.isVisible) {
                launchAdjacentController.setLaunchAdjacentEnabled(false);
                return;
            }
            i++;
        }
    }

    public final void updateLaunchRoot(int i, WindowContainerTransaction windowContainerTransaction, boolean z) {
        Object obj = this.deskRootsByDeskId.get(i);
        if (obj == null) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Root not found for desk: ").toString());
        }
        DeskRoot deskRoot = (DeskRoot) obj;
        deskRoot.isLaunchRootRequested = z;
        logD$2("updateLaunchRoot deskId=%d enabled=%b", Integer.valueOf(i), Boolean.valueOf(z));
        if (z) {
            windowContainerTransaction.setLaunchRoot(deskRoot.taskInfo.token, new int[]{5, 0}, new int[]{0, 1});
        } else {
            windowContainerTransaction.setLaunchRoot(deskRoot.taskInfo.token, (int[]) null, (int[]) null);
            windowContainerTransaction.reorder(deskRoot.taskInfo.token, false);
        }
        windowContainerTransaction.setReparentLeafTaskIfRelaunch(deskRoot.taskInfo.token, true ^ z);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DeskMinimizationRoot {
        public final Set children;
        public final int deskId;
        public final SurfaceControl leash;
        public final ActivityManager.RunningTaskInfo taskInfo;
        public final WindowContainerToken token;

        public DeskMinimizationRoot(int i, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Set<Integer> set) {
            this.deskId = i;
            this.taskInfo = runningTaskInfo;
            this.leash = surfaceControl;
            this.children = set;
            this.token = runningTaskInfo.token;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DeskMinimizationRoot)) {
                return false;
            }
            DeskMinimizationRoot deskMinimizationRoot = (DeskMinimizationRoot) obj;
            return this.deskId == deskMinimizationRoot.deskId && Intrinsics.areEqual(this.taskInfo, deskMinimizationRoot.taskInfo) && Intrinsics.areEqual(this.leash, deskMinimizationRoot.leash) && Intrinsics.areEqual(this.children, deskMinimizationRoot.children);
        }

        public final int hashCode() {
            return this.children.hashCode() + ((this.leash.hashCode() + ((this.taskInfo.hashCode() + (Integer.hashCode(this.deskId) * 31)) * 31)) * 31);
        }

        public final String toString() {
            return "DeskMinimizationRoot(deskId=" + this.deskId + ", taskInfo=" + this.taskInfo + ", leash=" + this.leash + ", children=" + this.children + ")";
        }

        public /* synthetic */ DeskMinimizationRoot(int i, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Set set, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, runningTaskInfo, surfaceControl, (i2 & 8) != 0 ? new LinkedHashSet() : set);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DeskRoot {
        public final Set children;
        public final int deskId;
        public boolean isLaunchRootRequested;
        public final SurfaceControl leash;
        public final ActivityManager.RunningTaskInfo taskInfo;
        public final WindowContainerToken token;
        public final Set users;

        public DeskRoot(int i, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Set<Integer> set, Set<Integer> set2, boolean z) {
            this.deskId = i;
            this.taskInfo = runningTaskInfo;
            this.leash = surfaceControl;
            this.children = set;
            this.users = set2;
            this.isLaunchRootRequested = z;
            this.token = runningTaskInfo.token;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DeskRoot)) {
                return false;
            }
            DeskRoot deskRoot = (DeskRoot) obj;
            return this.deskId == deskRoot.deskId && Intrinsics.areEqual(this.taskInfo, deskRoot.taskInfo) && Intrinsics.areEqual(this.leash, deskRoot.leash) && Intrinsics.areEqual(this.children, deskRoot.children) && Intrinsics.areEqual(this.users, deskRoot.users) && this.isLaunchRootRequested == deskRoot.isLaunchRootRequested;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isLaunchRootRequested) + ((this.users.hashCode() + ((this.children.hashCode() + ((this.leash.hashCode() + ((this.taskInfo.hashCode() + (Integer.hashCode(this.deskId) * 31)) * 31)) * 31)) * 31)) * 31);
        }

        public final String toString() {
            return "DeskRoot(deskId=" + this.deskId + ", taskInfo=" + this.taskInfo + ", leash=" + this.leash + ", children=" + this.children + ", users=" + this.users + ", isLaunchRootRequested=" + this.isLaunchRootRequested + ")";
        }

        public /* synthetic */ DeskRoot(int i, ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, Set set, Set set2, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, runningTaskInfo, surfaceControl, (i2 & 8) != 0 ? new LinkedHashSet() : set, (i2 & 16) != 0 ? new LinkedHashSet() : set2, (i2 & 32) != 0 ? false : z);
        }
    }

    public static /* synthetic */ void getDeskMinimizationRootsByDeskId$annotations() {
    }

    public static /* synthetic */ void getDeskRootsByDeskId$annotations() {
    }
}
