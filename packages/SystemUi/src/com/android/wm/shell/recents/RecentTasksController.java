package com.android.wm.shell.recents;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.os.RemoteException;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import androidx.core.util.SparseArrayKt$valueIterator$1;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.common.TaskStackListenerCallback;
import com.android.wm.shell.common.TaskStackListenerImpl;
import com.android.wm.shell.desktopmode.DesktopModeStatus;
import com.android.wm.shell.desktopmode.DesktopModeTaskRepository;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.recents.IRecentTasksListener;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.util.GroupedRecentTaskInfo;
import com.android.wm.shell.util.SplitBounds;
import com.samsung.android.rune.CoreRune;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import kotlin.sequences.SequencesKt__SequencesKt;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RecentTasksController implements TaskStackListenerCallback, RemoteCallable, DesktopModeTaskRepository.ActiveTasksListener {
    public final ActivityTaskManager mActivityTaskManager;
    public final Context mContext;
    public final Optional mDesktopModeTaskRepository;
    public final boolean mIsDesktopMode;
    public boolean mIsSplitTaskIdValidationChecked;
    public IRecentTasksListener mListener;
    public final ShellExecutor mMainExecutor;
    public final SparseIntArray mMultiSplitTasks;
    public final GroupedRecentTaskSaveController mSaveController;
    public final ShellCommandHandler mShellCommandHandler;
    public final ShellController mShellController;
    public final SparseIntArray mSplitTasks;
    public final Map mTaskSplitBoundsMap;
    public final TaskStackListenerImpl mTaskStackListener;
    public final RecentTasksImpl mImpl = new RecentTasksImpl(this, 0);
    public RecentsTransitionHandler mTransitionHandler = null;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IRecentTasksImpl extends IRecentTasks$Stub implements ExternalInterfaceBinder {
        public RecentTasksController mController;
        public final SingleInstanceRemoteListener mListener;
        public final BinderC40841 mRecentTasksListener = new BinderC40841();

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        /* renamed from: com.android.wm.shell.recents.RecentTasksController$IRecentTasksImpl$1 */
        public final class BinderC40841 extends IRecentTasksListener.Stub {
            public BinderC40841() {
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onRecentTasksChanged() {
                IRecentTasksImpl.this.mListener.call(new C4081x6ba2c7fb());
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onRunningTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo) {
                IRecentTasksImpl.this.mListener.call(new C4082x6ba2c7fc(runningTaskInfo, 0));
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onRunningTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
                IRecentTasksImpl.this.mListener.call(new C4082x6ba2c7fc(runningTaskInfo, 1));
            }
        }

        public IRecentTasksImpl(RecentTasksController recentTasksController) {
            this.mController = recentTasksController;
            this.mListener = new SingleInstanceRemoteListener(recentTasksController, new RecentTasksController$$ExternalSyntheticLambda3(this, 1), new RecentTasksController$IRecentTasksImpl$$ExternalSyntheticLambda0());
        }

        @Override // com.android.wm.shell.common.ExternalInterfaceBinder
        public final void invalidate() {
            this.mController = null;
            this.mListener.unregister();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RecentTasksImpl implements RecentTasks {
        public /* synthetic */ RecentTasksImpl(RecentTasksController recentTasksController, int i) {
            this();
        }

        private RecentTasksImpl() {
        }
    }

    public RecentTasksController(Context context, ShellInit shellInit, ShellController shellController, ShellCommandHandler shellCommandHandler, TaskStackListenerImpl taskStackListenerImpl, ActivityTaskManager activityTaskManager, Optional<DesktopModeTaskRepository> optional, ShellExecutor shellExecutor) {
        SparseIntArray sparseIntArray = new SparseIntArray();
        this.mSplitTasks = sparseIntArray;
        SparseIntArray sparseIntArray2 = new SparseIntArray();
        this.mMultiSplitTasks = sparseIntArray2;
        HashMap hashMap = new HashMap();
        this.mTaskSplitBoundsMap = hashMap;
        this.mIsSplitTaskIdValidationChecked = false;
        this.mContext = context;
        this.mShellController = shellController;
        this.mShellCommandHandler = shellCommandHandler;
        this.mActivityTaskManager = activityTaskManager;
        this.mIsDesktopMode = context.getPackageManager().hasSystemFeature("android.hardware.type.pc");
        this.mTaskStackListener = taskStackListenerImpl;
        this.mDesktopModeTaskRepository = optional;
        this.mMainExecutor = shellExecutor;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.recents.RecentTasksController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                final RecentTasksController recentTasksController = RecentTasksController.this;
                recentTasksController.getClass();
                recentTasksController.mShellController.addExternalInterface("extra_shell_recent_tasks", new Supplier() { // from class: com.android.wm.shell.recents.RecentTasksController$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        RecentTasksController recentTasksController2 = RecentTasksController.this;
                        recentTasksController2.getClass();
                        return new RecentTasksController.IRecentTasksImpl(recentTasksController2);
                    }
                }, recentTasksController);
                recentTasksController.mShellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.recents.RecentTasksController$$ExternalSyntheticLambda2
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        RecentTasksController recentTasksController2 = RecentTasksController.this;
                        PrintWriter printWriter = (PrintWriter) obj;
                        String str = (String) obj2;
                        recentTasksController2.getClass();
                        String str2 = str + "  ";
                        printWriter.println(str + "RecentTasksController");
                        printWriter.println(str + " mListener=" + recentTasksController2.mListener);
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        sb.append("Tasks:");
                        printWriter.println(sb.toString());
                        ArrayList<GroupedRecentTaskInfo> recentTasks = recentTasksController2.getRecentTasks(Integer.MAX_VALUE, 2, ActivityManager.getCurrentUser());
                        for (int i = 0; i < recentTasks.size(); i++) {
                            StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(str2);
                            m18m.append(recentTasks.get(i));
                            printWriter.println(m18m.toString());
                        }
                        StringBuilder m2m = AbstractC0000x2c234b15.m2m(str2, "SplitMap=");
                        m2m.append(recentTasksController2.mSplitTasks.toString());
                        printWriter.println(m2m.toString());
                        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS) {
                            StringBuilder m2m2 = AbstractC0000x2c234b15.m2m(str2, "MultiSplitMap=");
                            m2m2.append(recentTasksController2.mMultiSplitTasks.toString());
                            printWriter.println(m2m2.toString());
                        }
                        GroupedRecentTaskSaveController groupedRecentTaskSaveController = recentTasksController2.mSaveController;
                        groupedRecentTaskSaveController.getClass();
                        String str3 = str + "  ";
                        printWriter.println(str + "GroupedRecentTaskSaveInfo");
                        printWriter.println(str3 + "Saved Grouped Recents");
                        synchronized (groupedRecentTaskSaveController.mGroupedRecentTaskSaveMap) {
                            Iterator it = ((HashMap) groupedRecentTaskSaveController.mGroupedRecentTaskSaveMap).values().iterator();
                            while (it.hasNext()) {
                                printWriter.println(str3 + ((GroupedRecentTaskSaveInfo) it.next()));
                            }
                        }
                    }
                }, recentTasksController);
                recentTasksController.mTaskStackListener.addListener(recentTasksController);
                recentTasksController.mDesktopModeTaskRepository.ifPresent(new RecentTasksController$$ExternalSyntheticLambda3(recentTasksController, 0));
            }
        }, this);
        GroupedRecentTaskSaveController groupedRecentTaskSaveController = new GroupedRecentTaskSaveController(context);
        this.mSaveController = groupedRecentTaskSaveController;
        groupedRecentTaskSaveController.mHandler.post(new Runnable() { // from class: com.android.wm.shell.recents.GroupedRecentTaskSaveController.1
            public final /* synthetic */ SparseIntArray val$multiSplitTasks;
            public final /* synthetic */ SparseIntArray val$splitTasks;
            public final /* synthetic */ Map val$taskSplitBoundsMap;

            public RunnableC40801(SparseIntArray sparseIntArray3, SparseIntArray sparseIntArray22, Map hashMap2) {
                r2 = sparseIntArray3;
                r3 = sparseIntArray22;
                r4 = hashMap2;
            }

            /* JADX WARN: Code restructure failed: missing block: B:52:0x00aa, code lost:
            
                android.util.Slog.d("GroupedRecentTaskSaveInfo", "skip saved task to load");
             */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void run() {
                int i;
                int i2;
                GroupedRecentTaskSaveController groupedRecentTaskSaveController2 = GroupedRecentTaskSaveController.this;
                SparseIntArray sparseIntArray3 = r2;
                SparseIntArray sparseIntArray4 = r3;
                Map map = r4;
                groupedRecentTaskSaveController2.getClass();
                ArrayList arrayList = new ArrayList();
                StringBuilder sb = new StringBuilder();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(groupedRecentTaskSaveController2.mGroupedRecentSaveFile));
                    while (true) {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            } else {
                                sb.append(readLine);
                            }
                        } finally {
                        }
                    }
                    JSONArray jSONArray = new JSONObject(sb.toString()).getJSONArray("grouped_recent_tasks");
                    int i3 = 0;
                    for (int i4 = 0; i4 < jSONArray.length(); i4++) {
                        GroupedRecentTaskSaveInfo jsonToGroupedRecentTaskSaveInfo = GroupedRecentTaskSaveInfo.jsonToGroupedRecentTaskSaveInfo(jSONArray.getJSONObject(i4));
                        groupedRecentTaskSaveController2.addGroupedRecentTaskSaveInfo(jsonToGroupedRecentTaskSaveInfo);
                        arrayList.add(jsonToGroupedRecentTaskSaveInfo);
                    }
                    if (!arrayList.isEmpty()) {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            GroupedRecentTaskSaveInfo groupedRecentTaskSaveInfo = (GroupedRecentTaskSaveInfo) it.next();
                            int[] iArr = {-1, -1, -1};
                            iArr[i3] = groupedRecentTaskSaveInfo.mLeftTopTaskId;
                            int i5 = 1;
                            iArr[1] = groupedRecentTaskSaveInfo.mRightBottomTaskId;
                            if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS) {
                                iArr[2] = groupedRecentTaskSaveInfo.mCellTaskId;
                            }
                            int i6 = i3;
                            while (true) {
                                if (i6 >= 3) {
                                    i5 = i3;
                                    break;
                                } else if (sparseIntArray3.get(iArr[i6], -1) != -1 || ((i6 < 2 && iArr[i6] == -1) || (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS && sparseIntArray4.get(iArr[i6], -1) != -1))) {
                                    break;
                                } else {
                                    i6++;
                                }
                            }
                            sparseIntArray3.put(groupedRecentTaskSaveInfo.mLeftTopTaskId, groupedRecentTaskSaveInfo.mRightBottomTaskId);
                            sparseIntArray3.put(groupedRecentTaskSaveInfo.mRightBottomTaskId, groupedRecentTaskSaveInfo.mLeftTopTaskId);
                            if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS && (i2 = groupedRecentTaskSaveInfo.mCellTaskId) != -1) {
                                sparseIntArray4.put(groupedRecentTaskSaveInfo.mLeftTopTaskId, i2);
                                sparseIntArray4.put(groupedRecentTaskSaveInfo.mCellTaskId, groupedRecentTaskSaveInfo.mLeftTopTaskId);
                            }
                            SplitBounds splitBounds = CoreRune.MW_MULTI_SPLIT_RECENT_TASKS ? new SplitBounds(groupedRecentTaskSaveInfo.mLeftTopBounds, groupedRecentTaskSaveInfo.mRightBottomBounds, groupedRecentTaskSaveInfo.mCellBounds, groupedRecentTaskSaveInfo.mLeftTopTaskId, groupedRecentTaskSaveInfo.mRightBottomTaskId, groupedRecentTaskSaveInfo.mCellTaskId, groupedRecentTaskSaveInfo.mCellPosition, groupedRecentTaskSaveInfo.mSplitDivision) : new SplitBounds(groupedRecentTaskSaveInfo.mLeftTopBounds, groupedRecentTaskSaveInfo.mRightBottomBounds, groupedRecentTaskSaveInfo.mLeftTopTaskId, groupedRecentTaskSaveInfo.mRightBottomTaskId);
                            map.put(Integer.valueOf(groupedRecentTaskSaveInfo.mLeftTopTaskId), splitBounds);
                            map.put(Integer.valueOf(groupedRecentTaskSaveInfo.mRightBottomTaskId), splitBounds);
                            if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS && (i = groupedRecentTaskSaveInfo.mCellTaskId) != -1) {
                                map.put(Integer.valueOf(i), splitBounds);
                            }
                            i3 = 0;
                        }
                        Slog.d("GroupedRecentTaskSaveInfo", "success to load grouped recent tasks");
                    }
                    bufferedReader.close();
                } catch (IOException | JSONException e) {
                    Slog.e("GroupedRecentTaskSaveInfo", "fail to restore grouped recent tasks" + e);
                }
            }
        });
    }

    public final void addSplitPair(int i, int i2, int i3, SplitBounds splitBounds) {
        if (i == i2) {
            return;
        }
        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS && i3 != -1 && (i == i3 || i2 == i3)) {
            return;
        }
        SparseIntArray sparseIntArray = this.mSplitTasks;
        int i4 = sparseIntArray.get(i, -1);
        Map map = this.mTaskSplitBoundsMap;
        if (i4 == i2) {
            if (((SplitBounds) ((HashMap) map).get(Integer.valueOf(i))).equals(splitBounds)) {
                return;
            }
        }
        removeSplitPair(i);
        removeSplitPair(i2);
        HashMap hashMap = (HashMap) map;
        hashMap.remove(Integer.valueOf(i));
        hashMap.remove(Integer.valueOf(i2));
        boolean z = CoreRune.MW_MULTI_SPLIT_RECENT_TASKS;
        SparseIntArray sparseIntArray2 = this.mMultiSplitTasks;
        if (z) {
            if (i3 != -1) {
                removeSplitPair(i3);
                hashMap.remove(Integer.valueOf(i3));
            } else {
                int i5 = sparseIntArray2.get(i, -1);
                if (i5 == -1) {
                    i5 = sparseIntArray2.get(i2, -1);
                }
                if (i5 != -1) {
                    removeSplitPair(i5);
                    hashMap.remove(Integer.valueOf(i5));
                }
            }
        }
        sparseIntArray.put(i, i2);
        sparseIntArray.put(i2, i);
        hashMap.put(Integer.valueOf(i), splitBounds);
        hashMap.put(Integer.valueOf(i2), splitBounds);
        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS && i3 != -1) {
            sparseIntArray2.put(i, i3);
            sparseIntArray2.put(i3, i);
            hashMap.put(Integer.valueOf(i3), splitBounds);
        }
        notifyRecentTasksChanged();
        if (!splitBounds.leftTopBounds.isEmpty() && !splitBounds.rightBottomBounds.isEmpty()) {
            GroupedRecentTaskSaveInfo groupedRecentTaskSaveInfo = new GroupedRecentTaskSaveInfo(splitBounds);
            GroupedRecentTaskSaveController groupedRecentTaskSaveController = this.mSaveController;
            if (groupedRecentTaskSaveController.addGroupedRecentTaskSaveInfo(groupedRecentTaskSaveInfo)) {
                groupedRecentTaskSaveController.scheduleSaveGroupedRecentTasks();
            }
        }
        if (ShellProtoLogCache.WM_SHELL_RECENT_TASKS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENT_TASKS, 1423767195, 5, null, Long.valueOf(i), Long.valueOf(i2), String.valueOf(splitBounds));
        }
    }

    public final void clearAllSplitTaskIdsInfo() {
        this.mSplitTasks.clear();
        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS) {
            this.mMultiSplitTasks.clear();
        }
        GroupedRecentTaskSaveController groupedRecentTaskSaveController = this.mSaveController;
        synchronized (groupedRecentTaskSaveController.mGroupedRecentTaskSaveMap) {
            ((HashMap) groupedRecentTaskSaveController.mGroupedRecentTaskSaveMap).clear();
        }
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.mContext;
    }

    public ArrayList<GroupedRecentTaskInfo> getRecentTasks(int i, int i2, int i3) {
        int i4;
        boolean z;
        boolean z2;
        List recentTasks = this.mActivityTaskManager.getRecentTasks(i, i2, i3);
        int size = recentTasks.size();
        SparseIntArray sparseIntArray = this.mSplitTasks;
        if (size < i) {
            if (!this.mIsSplitTaskIdValidationChecked) {
                this.mIsSplitTaskIdValidationChecked = true;
                ArrayList arrayList = new ArrayList();
                int[] copyKeys = sparseIntArray.copyKeys();
                if (copyKeys != null) {
                    if (recentTasks.size() == 0) {
                        clearAllSplitTaskIdsInfo();
                    } else {
                        Iterator it = recentTasks.iterator();
                        while (it.hasNext()) {
                            arrayList.add(Integer.valueOf(((ActivityManager.RecentTaskInfo) it.next()).taskId));
                        }
                        for (int i5 : copyKeys) {
                            if (!arrayList.contains(Integer.valueOf(i5))) {
                                clearAllSplitTaskIdsInfo();
                            }
                        }
                        z2 = false;
                    }
                    z2 = true;
                    break;
                }
            } else {
                if (recentTasks.size() == 0 && sparseIntArray.size() != 0) {
                    clearAllSplitTaskIdsInfo();
                    z2 = true;
                    break;
                }
                z2 = false;
            }
            if (z2) {
                Slog.d("RecentTasksController", "init split taskIds for sync with rawList");
                this.mSaveController.scheduleSaveGroupedRecentTasks();
            }
        }
        SparseArray sparseArray = new SparseArray();
        for (int i6 = 0; i6 < recentTasks.size(); i6++) {
            ActivityManager.RecentTaskInfo recentTaskInfo = (ActivityManager.RecentTaskInfo) recentTasks.get(i6);
            sparseArray.put(recentTaskInfo.taskId, recentTaskInfo);
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList<GroupedRecentTaskInfo> arrayList3 = new ArrayList<>();
        for (int i7 = 0; i7 < recentTasks.size(); i7++) {
            ActivityManager.RecentTaskInfo recentTaskInfo2 = (ActivityManager.RecentTaskInfo) recentTasks.get(i7);
            if (sparseArray.contains(recentTaskInfo2.taskId)) {
                if (DesktopModeStatus.IS_PROTO2_ENABLED) {
                    Optional optional = this.mDesktopModeTaskRepository;
                    if (optional.isPresent()) {
                        DesktopModeTaskRepository desktopModeTaskRepository = (DesktopModeTaskRepository) optional.get();
                        int i8 = recentTaskInfo2.taskId;
                        desktopModeTaskRepository.getClass();
                        Iterator it2 = SequencesKt__SequencesKt.asSequence(new SparseArrayKt$valueIterator$1(desktopModeTaskRepository.displayData)).iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                z = false;
                                break;
                            }
                            if (((DesktopModeTaskRepository.DisplayData) it2.next()).activeTasks.contains(Integer.valueOf(i8))) {
                                z = true;
                                break;
                            }
                        }
                        if (z) {
                            arrayList2.add(recentTaskInfo2);
                        }
                    }
                }
                int i9 = sparseIntArray.get(recentTaskInfo2.taskId, -1);
                if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS) {
                    SparseIntArray sparseIntArray2 = this.mMultiSplitTasks;
                    i4 = sparseIntArray2.get(recentTaskInfo2.taskId, -1);
                    if (i9 != -1 && i4 == -1) {
                        i4 = sparseIntArray2.get(i9, -1);
                    } else if (i9 == -1 && i4 != -1) {
                        i9 = sparseIntArray.get(i4, -1);
                    }
                } else {
                    i4 = -1;
                }
                if (i9 == -1 || !sparseArray.contains(i9)) {
                    arrayList3.add(GroupedRecentTaskInfo.forSingleTask(recentTaskInfo2));
                } else {
                    ActivityManager.RecentTaskInfo recentTaskInfo3 = (ActivityManager.RecentTaskInfo) sparseArray.get(i9);
                    sparseArray.remove(i9);
                    Map map = this.mTaskSplitBoundsMap;
                    if (i4 == -1) {
                        arrayList3.add(GroupedRecentTaskInfo.forSplitTasks(recentTaskInfo2, recentTaskInfo3, (SplitBounds) ((HashMap) map).get(Integer.valueOf(i9))));
                    } else if (sparseArray.contains(i4)) {
                        ActivityManager.RecentTaskInfo recentTaskInfo4 = (ActivityManager.RecentTaskInfo) sparseArray.get(i4);
                        sparseArray.remove(i4);
                        arrayList3.add(GroupedRecentTaskInfo.forSplitTasks(recentTaskInfo2, recentTaskInfo3, recentTaskInfo4, (SplitBounds) ((HashMap) map).get(Integer.valueOf(i4))));
                    } else {
                        arrayList3.add(GroupedRecentTaskInfo.forSingleTask(recentTaskInfo2));
                        arrayList3.add(GroupedRecentTaskInfo.forSingleTask(recentTaskInfo3));
                    }
                }
            }
        }
        if (!arrayList2.isEmpty()) {
            arrayList3.add(0, GroupedRecentTaskInfo.forFreeformTasks((ActivityManager.RecentTaskInfo[]) arrayList2.toArray(new ActivityManager.RecentTaskInfo[0])));
        }
        return arrayList3;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mMainExecutor;
    }

    public boolean hasRecentTasksListener() {
        return this.mListener != null;
    }

    public void notifyRecentTasksChanged() {
        if (ShellProtoLogCache.WM_SHELL_RECENT_TASKS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENT_TASKS, -1066960526, 0, null, null);
        }
        IRecentTasksListener iRecentTasksListener = this.mListener;
        if (iRecentTasksListener == null) {
            return;
        }
        try {
            iRecentTasksListener.onRecentTasksChanged();
        } catch (RemoteException e) {
            Slog.w("RecentTasksController", "Failed call notifyRecentTasksChanged", e);
        }
    }

    @Override // com.android.wm.shell.common.TaskStackListenerCallback
    public final void onRecentTaskListUpdated() {
        notifyRecentTasksChanged();
    }

    @Override // com.android.wm.shell.common.TaskStackListenerCallback
    public final void onTaskStackChanged() {
        notifyRecentTasksChanged();
    }

    public void registerRecentTasksListener(IRecentTasksListener iRecentTasksListener) {
        this.mListener = iRecentTasksListener;
    }

    public final void removeMultiSplitPair(int i, int i2) {
        SparseIntArray sparseIntArray = this.mMultiSplitTasks;
        sparseIntArray.delete(i);
        sparseIntArray.delete(i2);
        Map map = this.mTaskSplitBoundsMap;
        ((HashMap) map).remove(Integer.valueOf(i));
        ((HashMap) map).remove(Integer.valueOf(i2));
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void removeSplitPair(int i) {
        int i2;
        GroupedRecentTaskSaveController groupedRecentTaskSaveController;
        int taskIdKey;
        int i3 = this.mSplitTasks.get(i, -1);
        if (i3 == -1) {
            if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS) {
                if (-1 != this.mMultiSplitTasks.get(i, -1)) {
                    int i4 = this.mMultiSplitTasks.get(i, -1);
                    int i5 = this.mSplitTasks.get(i4, -1);
                    this.mSplitTasks.delete(i4);
                    this.mSplitTasks.delete(i5);
                    ((HashMap) this.mTaskSplitBoundsMap).remove(Integer.valueOf(i4));
                    ((HashMap) this.mTaskSplitBoundsMap).remove(Integer.valueOf(i5));
                    removeMultiSplitPair(i, i4);
                    int possibleRemoveTaskIdKey = this.mSaveController.getPossibleRemoveTaskIdKey(i4, i5, i);
                    if (possibleRemoveTaskIdKey != -1) {
                        GroupedRecentTaskSaveController groupedRecentTaskSaveController2 = this.mSaveController;
                        synchronized (groupedRecentTaskSaveController2.mGroupedRecentTaskSaveMap) {
                        }
                        this.mSaveController.scheduleSaveGroupedRecentTasks();
                    }
                    notifyRecentTasksChanged();
                    return;
                }
                return;
            }
            return;
        }
        this.mSplitTasks.delete(i);
        this.mSplitTasks.delete(i3);
        ((HashMap) this.mTaskSplitBoundsMap).remove(Integer.valueOf(i));
        ((HashMap) this.mTaskSplitBoundsMap).remove(Integer.valueOf(i3));
        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS) {
            i2 = this.mMultiSplitTasks.get(i, -1);
            if (i2 != -1) {
                removeMultiSplitPair(i, i2);
            } else {
                i2 = this.mMultiSplitTasks.get(i3, -1);
                if (i2 != -1) {
                    removeMultiSplitPair(i3, i2);
                }
            }
            notifyRecentTasksChanged();
            if (ShellProtoLogCache.WM_SHELL_RECENT_TASKS_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENT_TASKS, 927833074, 5, null, Long.valueOf(i), Long.valueOf(i3));
            }
            if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS || !r2) {
                groupedRecentTaskSaveController = this.mSaveController;
                synchronized (groupedRecentTaskSaveController.mGroupedRecentTaskSaveMap) {
                    int taskIdKey2 = GroupedRecentTaskSaveController.getTaskIdKey(i, i3);
                    if (((HashMap) groupedRecentTaskSaveController.mGroupedRecentTaskSaveMap).containsKey(Integer.valueOf(taskIdKey2))) {
                        taskIdKey = taskIdKey2;
                    } else {
                        taskIdKey = GroupedRecentTaskSaveController.getTaskIdKey(i3, i);
                        if (!((HashMap) groupedRecentTaskSaveController.mGroupedRecentTaskSaveMap).containsKey(Integer.valueOf(taskIdKey))) {
                            taskIdKey = -1;
                        }
                    }
                }
            } else {
                taskIdKey = this.mSaveController.getPossibleRemoveTaskIdKey(i, i3, i2);
            }
            if (taskIdKey != -1) {
                GroupedRecentTaskSaveController groupedRecentTaskSaveController3 = this.mSaveController;
                synchronized (groupedRecentTaskSaveController3.mGroupedRecentTaskSaveMap) {
                }
                this.mSaveController.scheduleSaveGroupedRecentTasks();
                return;
            }
            return;
        }
        i2 = -1;
        r2 = false;
        notifyRecentTasksChanged();
        if (ShellProtoLogCache.WM_SHELL_RECENT_TASKS_enabled) {
        }
        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS) {
        }
        groupedRecentTaskSaveController = this.mSaveController;
        synchronized (groupedRecentTaskSaveController.mGroupedRecentTaskSaveMap) {
        }
    }

    public void unregisterRecentTasksListener() {
        this.mListener = null;
    }
}
