package com.android.wm.shell.desktopmode;

import android.graphics.Region;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;
import androidx.core.util.SparseArrayKt$keyIterator$1;
import androidx.core.util.SparseArrayKt$valueIterator$1;
import com.android.wm.shell.desktopmode.DesktopModeTaskRepository;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.util.KtProtoLog;
import com.android.systemui.model.SysUiState;
import com.android.systemui.wmshell.WMShell;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.FilteringSequence$iterator$1;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DesktopModeTaskRepository {
    public Executor desktopGestureExclusionExecutor;
    public Consumer desktopGestureExclusionListener;
    public final List freeformTasksInZOrder = new ArrayList();
    public final ArraySet activeTasksListeners = new ArraySet();
    public final ArrayMap visibleTasksListeners = new ArrayMap();
    public final SparseArray desktopCorners = new SparseArray();
    public final DesktopModeTaskRepository$displayData$1 displayData = new SparseArray() { // from class: com.android.wm.shell.desktopmode.DesktopModeTaskRepository$displayData$1
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ActiveTasksListener {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DisplayData {
        public final ArraySet activeTasks;
        public final ArraySet visibleTasks;

        /* JADX WARN: Multi-variable type inference failed */
        public DisplayData() {
            this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DisplayData)) {
                return false;
            }
            DisplayData displayData = (DisplayData) obj;
            return Intrinsics.areEqual(this.activeTasks, displayData.activeTasks) && Intrinsics.areEqual(this.visibleTasks, displayData.visibleTasks);
        }

        public final int hashCode() {
            return this.visibleTasks.hashCode() + (this.activeTasks.hashCode() * 31);
        }

        public final String toString() {
            return "DisplayData(activeTasks=" + this.activeTasks + ", visibleTasks=" + this.visibleTasks + ")";
        }

        public DisplayData(ArraySet<Integer> arraySet, ArraySet<Integer> arraySet2) {
            this.activeTasks = arraySet;
            this.visibleTasks = arraySet2;
        }

        public /* synthetic */ DisplayData(ArraySet arraySet, ArraySet arraySet2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? new ArraySet() : arraySet, (i & 2) != 0 ? new ArraySet() : arraySet2);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface VisibleTasksListener {
    }

    public static final Region access$calculateDesktopExclusionRegion(DesktopModeTaskRepository desktopModeTaskRepository) {
        desktopModeTaskRepository.getClass();
        Region region = new Region();
        SparseArrayKt$valueIterator$1 sparseArrayKt$valueIterator$1 = new SparseArrayKt$valueIterator$1(desktopModeTaskRepository.desktopCorners);
        while (sparseArrayKt$valueIterator$1.hasNext()) {
            region.op((Region) sparseArrayKt$valueIterator$1.next(), Region.Op.UNION);
        }
        return region;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean addActiveTask(int i, int i2) {
        ArraySet arraySet;
        DesktopModeTaskRepository$displayData$1 desktopModeTaskRepository$displayData$1 = this.displayData;
        int size = desktopModeTaskRepository$displayData$1.size();
        int i3 = 0;
        while (true) {
            arraySet = this.activeTasksListeners;
            if (i3 >= size) {
                break;
            }
            int keyAt = desktopModeTaskRepository$displayData$1.keyAt(i3);
            DisplayData displayData = (DisplayData) desktopModeTaskRepository$displayData$1.valueAt(i3);
            if (keyAt != i && displayData.activeTasks.remove(Integer.valueOf(i2))) {
                Iterator it = arraySet.iterator();
                while (it.hasNext()) {
                    ((RecentTasksController) ((ActiveTasksListener) it.next())).notifyRecentTasksChanged();
                }
            }
            i3++;
        }
        if (!desktopModeTaskRepository$displayData$1.contains(i)) {
            desktopModeTaskRepository$displayData$1.put(i, new DisplayData(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0));
        }
        DisplayData displayData2 = (DisplayData) desktopModeTaskRepository$displayData$1.get(i);
        boolean add = displayData2.activeTasks.add(Integer.valueOf(i2));
        if (add) {
            KtProtoLog.Companion companion = KtProtoLog.Companion;
            ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
            Object[] objArr = {Integer.valueOf(i2), Integer.valueOf(i)};
            companion.getClass();
            KtProtoLog.Companion.m234d(shellProtoLogGroup, "DesktopTaskRepo: add active task=%d displayId=%d", objArr);
            Iterator it2 = arraySet.iterator();
            while (it2.hasNext()) {
                ((RecentTasksController) ((ActiveTasksListener) it2.next())).notifyRecentTasksChanged();
            }
        }
        return add;
    }

    public final void addOrMoveFreeformTaskToTop(int i) {
        KtProtoLog.Companion companion = KtProtoLog.Companion;
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        Object[] objArr = {Integer.valueOf(i)};
        companion.getClass();
        KtProtoLog.Companion.m234d(shellProtoLogGroup, "DesktopTaskRepo: add or move task to top taskId=%d", objArr);
        ArrayList arrayList = (ArrayList) this.freeformTasksInZOrder;
        if (arrayList.contains(Integer.valueOf(i))) {
            arrayList.remove(Integer.valueOf(i));
        }
        arrayList.add(0, Integer.valueOf(i));
    }

    public final void addVisibleTasksListener(final VisibleTasksListener visibleTasksListener, Executor executor) {
        this.visibleTasksListeners.put(visibleTasksListener, executor);
        SparseArrayKt$keyIterator$1 sparseArrayKt$keyIterator$1 = new SparseArrayKt$keyIterator$1(this.displayData);
        while (sparseArrayKt$keyIterator$1.hasNext()) {
            final int intValue = ((Number) sparseArrayKt$keyIterator$1.next()).intValue();
            final int visibleTaskCount = getVisibleTaskCount(intValue);
            executor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopModeTaskRepository$addVisibleTasksListener$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    DesktopModeTaskRepository.VisibleTasksListener visibleTasksListener2 = DesktopModeTaskRepository.VisibleTasksListener.this;
                    int i = intValue;
                    boolean z = visibleTaskCount > 0;
                    WMShell.C374814 c374814 = (WMShell.C374814) visibleTasksListener2;
                    if (i != 0) {
                        c374814.getClass();
                        return;
                    }
                    WMShell wMShell = c374814.this$0;
                    SysUiState sysUiState = wMShell.mSysUiState;
                    sysUiState.setFlag(67108864L, z);
                    wMShell.mDisplayTracker.getClass();
                    sysUiState.commitUpdate(0);
                }
            });
        }
    }

    public final ArraySet getActiveTasks(int i) {
        DisplayData displayData = (DisplayData) get(i);
        return new ArraySet(displayData != null ? displayData.activeTasks : null);
    }

    public final int getVisibleTaskCount(int i) {
        ArraySet arraySet;
        DisplayData displayData = (DisplayData) get(i);
        if (displayData == null || (arraySet = displayData.visibleTasks) == null) {
            return 0;
        }
        return arraySet.size();
    }

    public final boolean isVisibleTask(int i) {
        Iterator it = SequencesKt__SequencesKt.asSequence(new SparseArrayKt$valueIterator$1(this.displayData)).iterator();
        while (it.hasNext()) {
            if (((DisplayData) it.next()).visibleTasks.contains(Integer.valueOf(i))) {
                return true;
            }
        }
        return false;
    }

    public final void notifyVisibleTaskListeners(final int i, final boolean z) {
        for (Map.Entry entry : this.visibleTasksListeners.entrySet()) {
            final VisibleTasksListener visibleTasksListener = (VisibleTasksListener) entry.getKey();
            ((Executor) entry.getValue()).execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopModeTaskRepository$notifyVisibleTaskListeners$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    DesktopModeTaskRepository.VisibleTasksListener visibleTasksListener2 = DesktopModeTaskRepository.VisibleTasksListener.this;
                    int i2 = i;
                    boolean z2 = z;
                    WMShell.C374814 c374814 = (WMShell.C374814) visibleTasksListener2;
                    if (i2 != 0) {
                        c374814.getClass();
                        return;
                    }
                    WMShell wMShell = c374814.this$0;
                    SysUiState sysUiState = wMShell.mSysUiState;
                    sysUiState.setFlag(67108864L, z2);
                    wMShell.mDisplayTracker.getClass();
                    sysUiState.commitUpdate(0);
                }
            });
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void updateVisibleFreeformTasks(final int i, int i2, boolean z) {
        ArraySet arraySet;
        DesktopModeTaskRepository$displayData$1 desktopModeTaskRepository$displayData$1 = this.displayData;
        if (z) {
            FilteringSequence$iterator$1 filteringSequence$iterator$1 = new FilteringSequence$iterator$1(SequencesKt___SequencesKt.filter(SequencesKt__SequencesKt.asSequence(new SparseArrayKt$keyIterator$1(desktopModeTaskRepository$displayData$1)), new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopModeTaskRepository$updateVisibleFreeformTasks$otherDisplays$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return Boolean.valueOf(((Number) obj).intValue() != i);
                }
            }));
            while (filteringSequence$iterator$1.hasNext()) {
                int intValue = ((Number) filteringSequence$iterator$1.next()).intValue();
                if (((DisplayData) desktopModeTaskRepository$displayData$1.get(intValue)).visibleTasks.remove(Integer.valueOf(i2)) && ((DisplayData) desktopModeTaskRepository$displayData$1.get(intValue)).visibleTasks.isEmpty()) {
                    notifyVisibleTaskListeners(intValue, false);
                }
            }
        }
        int visibleTaskCount = getVisibleTaskCount(i);
        if (z) {
            if (!desktopModeTaskRepository$displayData$1.contains(i)) {
                desktopModeTaskRepository$displayData$1.put(i, new DisplayData(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0));
            }
            ((DisplayData) desktopModeTaskRepository$displayData$1.get(i)).visibleTasks.add(Integer.valueOf(i2));
        } else {
            DisplayData displayData = (DisplayData) desktopModeTaskRepository$displayData$1.get(i);
            if (displayData != null && (arraySet = displayData.visibleTasks) != null) {
                arraySet.remove(Integer.valueOf(i2));
            }
        }
        int visibleTaskCount2 = getVisibleTaskCount(i);
        if (visibleTaskCount != visibleTaskCount2) {
            KtProtoLog.Companion companion = KtProtoLog.Companion;
            ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
            Object[] objArr = {Integer.valueOf(i2), Boolean.valueOf(z), Integer.valueOf(i)};
            companion.getClass();
            KtProtoLog.Companion.m234d(shellProtoLogGroup, "DesktopTaskRepo: update task visibility taskId=%d visible=%b displayId=%d", objArr);
        }
        if (visibleTaskCount != visibleTaskCount2) {
            if (visibleTaskCount == 0 || visibleTaskCount2 == 0) {
                notifyVisibleTaskListeners(i, visibleTaskCount2 > 0);
            }
        }
    }
}
