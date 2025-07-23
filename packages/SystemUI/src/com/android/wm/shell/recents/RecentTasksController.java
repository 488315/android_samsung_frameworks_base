package com.android.wm.shell.recents;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.KeyguardManager;
import android.app.TaskInfo;
import android.content.Context;
import android.os.IInterface;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.SparseIntArray;
import android.window.DesktopModeFlags;
import android.window.WindowContainerToken;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.common.TaskStackListenerCallback;
import com.android.wm.shell.common.TaskStackListenerImpl;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.DesktopWallpaperActivity;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.IRecentTasksListener;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.recents.TaskStackTransitionObserver;
import com.android.wm.shell.shared.GroupedTaskInfo;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.shared.split.SplitBounds;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.sysui.UserChangeListener;
import com.samsung.android.rune.CoreRune;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class RecentTasksController implements TaskStackListenerCallback, RemoteCallable, DesktopRepository.ActiveTasksListener, TaskStackTransitionObserver.TaskStackTransitionObserverListener, UserChangeListener {
    public final ActivityTaskManager mActivityTaskManager;
    public final Context mContext;
    public final DesktopState mDesktopState;
    public final Optional mDesktopUserRepositories;
    public boolean mIsSplitTaskIdValidationChecked;
    public IRecentTasksListener mListener;
    public final ShellExecutor mMainExecutor;
    public final SparseIntArray mMultiSplitTasks;
    public final boolean mPcFeatureEnabled;
    public final RecentsShellCommandHandler mRecentsShellCommandHandler;
    public final GroupedRecentTaskSaveController mSaveController;
    public final ShellCommandHandler mShellCommandHandler;
    public final ShellController mShellController;
    public final SparseIntArray mSplitTasks;
    public final Map mTaskSplitBoundsMap;
    public final TaskStackListenerImpl mTaskStackListener;
    public final TaskStackTransitionObserver mTaskStackTransitionObserver;
    public final Map mTmpDesks;
    public final Map mTmpRemaining;
    public int mUserId;
    public final List mVisibleTasks;
    public final Map mVisibleTasksMap;
    public final RecentTasksImpl mImpl = new RecentTasksImpl(this, 0);
    public RecentsTransitionHandler mTransitionHandler = null;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class IRecentTasksImpl extends IRecentTasks$Stub implements ExternalInterfaceBinder {
        public static final /* synthetic */ int $r8$clinit = 0;
        public RecentTasksController mController;
        public final SingleInstanceRemoteListener mListener;
        public final AnonymousClass1 mRecentTasksListener = new AnonymousClass1();

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.wm.shell.recents.RecentTasksController$IRecentTasksImpl$1, reason: invalid class name */
        public class AnonymousClass1 extends IRecentTasksListener.Stub {
            public static final /* synthetic */ int $r8$clinit = 0;

            public AnonymousClass1() {
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onRecentTasksChanged() {
                IRecentTasksImpl.this.mListener.call(new RecentTasksController$IRecentTasksImpl$1$$ExternalSyntheticLambda0());
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onRunningTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo) {
                IInterface iInterface = IRecentTasksImpl.this.mListener.mListener;
                if (iInterface == null) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                    return;
                }
                try {
                    ((IRecentTasksListener) iInterface).onRunningTaskAppeared(runningTaskInfo);
                } catch (RemoteException e) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                }
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onRunningTaskChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
                IInterface iInterface = IRecentTasksImpl.this.mListener.mListener;
                if (iInterface == null) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                    return;
                }
                try {
                    ((IRecentTasksListener) iInterface).onRunningTaskChanged(runningTaskInfo);
                } catch (RemoteException e) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                }
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onRunningTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
                IInterface iInterface = IRecentTasksImpl.this.mListener.mListener;
                if (iInterface == null) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                    return;
                }
                try {
                    ((IRecentTasksListener) iInterface).onRunningTaskVanished(runningTaskInfo);
                } catch (RemoteException e) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                }
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
                IInterface iInterface = IRecentTasksImpl.this.mListener.mListener;
                if (iInterface == null) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                    return;
                }
                try {
                    ((IRecentTasksListener) iInterface).onTaskInfoChanged(runningTaskInfo);
                } catch (RemoteException e) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                }
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onTaskMovedToFront(GroupedTaskInfo groupedTaskInfo) {
                IInterface iInterface = IRecentTasksImpl.this.mListener.mListener;
                if (iInterface == null) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                    return;
                }
                try {
                    ((IRecentTasksListener) iInterface).onTaskMovedToFront(groupedTaskInfo);
                } catch (RemoteException e) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                }
            }

            @Override // com.android.wm.shell.recents.IRecentTasksListener
            public final void onVisibleTasksChanged(GroupedTaskInfo[] groupedTaskInfoArr) {
                IInterface iInterface = IRecentTasksImpl.this.mListener.mListener;
                if (iInterface == null) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                    return;
                }
                try {
                    ((IRecentTasksListener) iInterface).onVisibleTasksChanged(groupedTaskInfoArr);
                } catch (RemoteException e) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                }
            }
        }

        public IRecentTasksImpl(RecentTasksController recentTasksController) {
            this.mController = recentTasksController;
            this.mListener = new SingleInstanceRemoteListener(recentTasksController, new RecentTasksController$IRecentTasksImpl$$ExternalSyntheticLambda3(this, 0), new RecentTasksController$IRecentTasksImpl$$ExternalSyntheticLambda4());
        }

        @Override // com.android.wm.shell.common.ExternalInterfaceBinder
        public final void invalidate() {
            this.mController = null;
            this.mListener.unregister();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RecentTasksImpl implements RecentTasks {
        public /* synthetic */ RecentTasksImpl(RecentTasksController recentTasksController, int i) {
            this();
        }

        private RecentTasksImpl() {
        }
    }

    public RecentTasksController(Context context, ShellInit shellInit, ShellController shellController, ShellCommandHandler shellCommandHandler, TaskStackListenerImpl taskStackListenerImpl, ActivityTaskManager activityTaskManager, Optional<DesktopUserRepositories> optional, TaskStackTransitionObserver taskStackTransitionObserver, ShellExecutor shellExecutor, DesktopState desktopState) {
        SparseIntArray sparseIntArray = new SparseIntArray();
        this.mSplitTasks = sparseIntArray;
        SparseIntArray sparseIntArray2 = new SparseIntArray();
        this.mMultiSplitTasks = sparseIntArray2;
        HashMap hashMap = new HashMap();
        this.mTaskSplitBoundsMap = hashMap;
        this.mIsSplitTaskIdValidationChecked = false;
        this.mVisibleTasks = new ArrayList();
        this.mVisibleTasksMap = new HashMap();
        this.mTmpRemaining = new HashMap();
        this.mTmpDesks = new HashMap();
        this.mContext = context;
        this.mShellController = shellController;
        this.mShellCommandHandler = shellCommandHandler;
        this.mActivityTaskManager = activityTaskManager;
        this.mPcFeatureEnabled = context.getPackageManager().hasSystemFeature("android.hardware.type.pc");
        this.mTaskStackListener = taskStackListenerImpl;
        this.mDesktopUserRepositories = optional;
        this.mTaskStackTransitionObserver = taskStackTransitionObserver;
        this.mMainExecutor = shellExecutor;
        this.mRecentsShellCommandHandler = new RecentsShellCommandHandler(this);
        this.mDesktopState = desktopState;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.recents.RecentTasksController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                final RecentTasksController recentTasksController = RecentTasksController.this;
                recentTasksController.mShellController.addExternalInterface("com.android.wm.shell.recents.IRecentTasks", new Supplier() { // from class: com.android.wm.shell.recents.RecentTasksController$$ExternalSyntheticLambda4
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        RecentTasksController recentTasksController2 = RecentTasksController.this;
                        recentTasksController2.getClass();
                        return new RecentTasksController.IRecentTasksImpl(recentTasksController2);
                    }
                }, recentTasksController);
                BiConsumer biConsumer = new BiConsumer() { // from class: com.android.wm.shell.recents.RecentTasksController$$ExternalSyntheticLambda5
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
                        ArrayList<GroupedTaskInfo> recentTasks = recentTasksController2.getRecentTasks(Integer.MAX_VALUE, 2, ActivityManager.getCurrentUser());
                        for (int i = 0; i < recentTasks.size(); i++) {
                            StringBuilder m = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str2);
                            m.append(recentTasks.get(i));
                            printWriter.println(m.toString());
                        }
                        StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str2, "SplitMap=");
                        m2.append(recentTasksController2.mSplitTasks.toString());
                        printWriter.println(m2.toString());
                        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS) {
                            StringBuilder m3 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str2, "MultiSplitMap=");
                            m3.append(recentTasksController2.mMultiSplitTasks.toString());
                            printWriter.println(m3.toString());
                        }
                        GroupedRecentTaskSaveController groupedRecentTaskSaveController = recentTasksController2.mSaveController;
                        groupedRecentTaskSaveController.getClass();
                        String str3 = str + "  ";
                        printWriter.println(str + "GroupedRecentTaskSaveInfo");
                        printWriter.println(str3 + "Saved Grouped Recents");
                        synchronized (groupedRecentTaskSaveController.mGroupedRecentTaskSaveMap) {
                            try {
                                Iterator it = ((HashMap) groupedRecentTaskSaveController.mGroupedRecentTaskSaveMap).values().iterator();
                                while (it.hasNext()) {
                                    printWriter.println(str3 + ((GroupedRecentTaskSaveInfo) it.next()));
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                };
                ShellCommandHandler shellCommandHandler2 = recentTasksController.mShellCommandHandler;
                shellCommandHandler2.addDumpCallback(biConsumer, recentTasksController);
                shellCommandHandler2.addCommandCallback("recents", recentTasksController.mRecentsShellCommandHandler, recentTasksController);
                recentTasksController.mUserId = ActivityManager.getCurrentUser();
                recentTasksController.mDesktopUserRepositories.ifPresent(new RecentTasksController$$ExternalSyntheticLambda6(recentTasksController, 0));
                recentTasksController.mTaskStackListener.addListener(recentTasksController);
                ArrayMap arrayMap = recentTasksController.mTaskStackTransitionObserver.taskStackTransitionObserverListeners;
                ShellExecutor shellExecutor2 = recentTasksController.mMainExecutor;
                arrayMap.put(recentTasksController, shellExecutor2);
                ((KeyguardManager) recentTasksController.mContext.getSystemService(KeyguardManager.class)).addKeyguardLockedStateListener(shellExecutor2, new KeyguardManager.KeyguardLockedStateListener() { // from class: com.android.wm.shell.recents.RecentTasksController$$ExternalSyntheticLambda7
                    @Override // android.app.KeyguardManager.KeyguardLockedStateListener
                    public final void onKeyguardLockedStateChanged(boolean z) {
                        RecentTasksController.this.notifyRecentTasksChanged();
                    }
                });
            }
        }, this);
        GroupedRecentTaskSaveController groupedRecentTaskSaveController = new GroupedRecentTaskSaveController(context);
        this.mSaveController = groupedRecentTaskSaveController;
        groupedRecentTaskSaveController.mHandler.post(new Runnable() { // from class: com.android.wm.shell.recents.GroupedRecentTaskSaveController.1
            public final /* synthetic */ SparseIntArray val$multiSplitTasks;
            public final /* synthetic */ SparseIntArray val$splitTasks;
            public final /* synthetic */ Map val$taskSplitBoundsMap;

            public AnonymousClass1(SparseIntArray sparseIntArray3, SparseIntArray sparseIntArray22, Map hashMap2) {
                r2 = sparseIntArray3;
                r3 = sparseIntArray22;
                r4 = hashMap2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i;
                SplitBounds splitBounds;
                int i2;
                int i3;
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
                    int i4 = 0;
                    for (int i5 = 0; i5 < jSONArray.length(); i5++) {
                        GroupedRecentTaskSaveInfo jsonToGroupedRecentTaskSaveInfo = GroupedRecentTaskSaveInfo.jsonToGroupedRecentTaskSaveInfo(jSONArray.getJSONObject(i5));
                        groupedRecentTaskSaveController2.addGroupedRecentTaskSaveInfo(jsonToGroupedRecentTaskSaveInfo);
                        arrayList.add(jsonToGroupedRecentTaskSaveInfo);
                    }
                    if (!arrayList.isEmpty()) {
                        int size = arrayList.size();
                        int i6 = 0;
                        while (i6 < size) {
                            Object obj = arrayList.get(i6);
                            i6++;
                            GroupedRecentTaskSaveInfo groupedRecentTaskSaveInfo = (GroupedRecentTaskSaveInfo) obj;
                            int[] iArr = new int[3];
                            iArr[i4] = -1;
                            iArr[1] = -1;
                            iArr[2] = -1;
                            iArr[i4] = groupedRecentTaskSaveInfo.mLeftTopTaskId;
                            iArr[1] = groupedRecentTaskSaveInfo.mRightBottomTaskId;
                            if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS) {
                                iArr[2] = groupedRecentTaskSaveInfo.mCellTaskId;
                            }
                            for (int i7 = i4; i7 < 3; i7++) {
                                if (sparseIntArray3.get(iArr[i7], -1) == -1 && ((i7 >= 2 || iArr[i7] != -1) && (!CoreRune.MW_MULTI_SPLIT_RECENT_TASKS || sparseIntArray4.get(iArr[i7], -1) == -1))) {
                                }
                                Slog.d("GroupedRecentTaskSaveInfo", "skip saved task to load");
                                break;
                            }
                            sparseIntArray3.put(groupedRecentTaskSaveInfo.mLeftTopTaskId, groupedRecentTaskSaveInfo.mRightBottomTaskId);
                            sparseIntArray3.put(groupedRecentTaskSaveInfo.mRightBottomTaskId, groupedRecentTaskSaveInfo.mLeftTopTaskId);
                            boolean z = CoreRune.MW_MULTI_SPLIT_RECENT_TASKS;
                            if (z && (i3 = groupedRecentTaskSaveInfo.mCellTaskId) != -1) {
                                sparseIntArray4.put(groupedRecentTaskSaveInfo.mLeftTopTaskId, i3);
                                sparseIntArray4.put(groupedRecentTaskSaveInfo.mCellTaskId, groupedRecentTaskSaveInfo.mLeftTopTaskId);
                            }
                            if (z) {
                                i = size;
                                splitBounds = new SplitBounds(groupedRecentTaskSaveInfo.mLeftTopBounds, groupedRecentTaskSaveInfo.mRightBottomBounds, groupedRecentTaskSaveInfo.mCellBounds, groupedRecentTaskSaveInfo.mLeftTopTaskId, groupedRecentTaskSaveInfo.mRightBottomTaskId, 8, groupedRecentTaskSaveInfo.mCellTaskId, groupedRecentTaskSaveInfo.mCellPosition, groupedRecentTaskSaveInfo.mSplitDivision, groupedRecentTaskSaveInfo.mParallelMultiSplit);
                            } else {
                                i = size;
                                splitBounds = new SplitBounds(groupedRecentTaskSaveInfo.mLeftTopBounds, groupedRecentTaskSaveInfo.mRightBottomBounds, groupedRecentTaskSaveInfo.mLeftTopTaskId, groupedRecentTaskSaveInfo.mRightBottomTaskId, 8);
                            }
                            map.put(Integer.valueOf(groupedRecentTaskSaveInfo.mLeftTopTaskId), splitBounds);
                            map.put(Integer.valueOf(groupedRecentTaskSaveInfo.mRightBottomTaskId), splitBounds);
                            if (z && (i2 = groupedRecentTaskSaveInfo.mCellTaskId) != -1) {
                                map.put(Integer.valueOf(i2), splitBounds);
                            }
                            size = i;
                            i4 = 0;
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

    public static boolean excludeTaskFromGeneratedList(TaskInfo taskInfo) {
        if (taskInfo.getWindowingMode() == 2) {
            return true;
        }
        DesktopWallpaperActivity.Companion.getClass();
        return DesktopWallpaperActivity.Companion.isWallpaperTask(taskInfo);
    }

    public final boolean addSplitPair(int i, int i2, int i3, SplitBounds splitBounds) {
        if (i == i2) {
            return false;
        }
        boolean z = CoreRune.MW_MULTI_SPLIT_RECENT_TASKS;
        if (z && i3 != -1 && (i == i3 || i2 == i3)) {
            return false;
        }
        if (this.mSplitTasks.get(i, -1) == i2) {
            if (((SplitBounds) ((HashMap) this.mTaskSplitBoundsMap).get(Integer.valueOf(i))).equals(splitBounds)) {
                return false;
            }
        }
        removeSplitPair(i);
        removeSplitPair(i2);
        ((HashMap) this.mTaskSplitBoundsMap).remove(Integer.valueOf(i));
        ((HashMap) this.mTaskSplitBoundsMap).remove(Integer.valueOf(i2));
        if (z) {
            if (i3 != -1) {
                removeSplitPair(i3);
                ((HashMap) this.mTaskSplitBoundsMap).remove(Integer.valueOf(i3));
            } else {
                int i4 = this.mMultiSplitTasks.get(i, -1);
                if (i4 == -1) {
                    i4 = this.mMultiSplitTasks.get(i2, -1);
                }
                if (i4 != -1) {
                    removeSplitPair(i4);
                    ((HashMap) this.mTaskSplitBoundsMap).remove(Integer.valueOf(i4));
                }
            }
        }
        this.mSplitTasks.put(i, i2);
        this.mSplitTasks.put(i2, i);
        ((HashMap) this.mTaskSplitBoundsMap).put(Integer.valueOf(i), splitBounds);
        ((HashMap) this.mTaskSplitBoundsMap).put(Integer.valueOf(i2), splitBounds);
        if (z && i3 != -1) {
            this.mMultiSplitTasks.put(i, i3);
            this.mMultiSplitTasks.put(i3, i);
            ((HashMap) this.mTaskSplitBoundsMap).put(Integer.valueOf(i3), splitBounds);
        }
        notifyRecentTasksChanged();
        if (!splitBounds.leftTopBounds.isEmpty() && !splitBounds.rightBottomBounds.isEmpty()) {
            GroupedRecentTaskSaveInfo groupedRecentTaskSaveInfo = new GroupedRecentTaskSaveInfo(splitBounds);
            GroupedRecentTaskSaveController groupedRecentTaskSaveController = this.mSaveController;
            if (groupedRecentTaskSaveController.addGroupedRecentTaskSaveInfo(groupedRecentTaskSaveInfo)) {
                groupedRecentTaskSaveController.scheduleSaveGroupedRecentTasks();
            }
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 2920484107003245482L, 5, Long.valueOf(i), Long.valueOf(i2), String.valueOf(splitBounds));
        }
        return true;
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

    public final ActivityManager.RecentTaskInfo findTaskInBackground(int i) {
        List recentTasks = this.mActivityTaskManager.getRecentTasks(Integer.MAX_VALUE, 2, ActivityManager.getCurrentUser());
        for (int i2 = 0; i2 < recentTasks.size(); i2++) {
            ActivityManager.RecentTaskInfo recentTaskInfo = (ActivityManager.RecentTaskInfo) recentTasks.get(i2);
            if (!recentTaskInfo.isVisible && i == recentTaskInfo.taskId) {
                return recentTaskInfo;
            }
        }
        return null;
    }

    public <T extends TaskInfo> ArrayList<GroupedTaskInfo> generateList(List<T> list, String str) {
        return generateList(0, list);
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.mContext;
    }

    public final Desk getOrCreateDesk(int i) {
        Desk desk = (Desk) ((HashMap) this.mTmpDesks).get(Integer.valueOf(i));
        if (desk == null) {
            int displayForDesk = ((DesktopUserRepositories) this.mDesktopUserRepositories.get()).getCurrent().desktopData.getDisplayForDesk(i);
            DesktopRepository.Desk desk2 = ((DesktopUserRepositories) this.mDesktopUserRepositories.get()).getCurrent().desktopData.getDesk(i);
            desk = new Desk(i, displayForDesk, desk2 != null ? desk2.usedDesk : -1);
            ((HashMap) this.mTmpDesks).put(Integer.valueOf(i), desk);
        }
        return desk;
    }

    public ArrayList<GroupedTaskInfo> getRecentTasks(int i, int i2, int i3) {
        return generateList(i2, this.mActivityTaskManager.getRecentTasks(i, i2, i3));
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mMainExecutor;
    }

    public final SplitBounds getSplitBoundsForTaskId(int i) {
        if (i == -1) {
            return null;
        }
        SplitBounds splitBounds = (SplitBounds) ((HashMap) this.mTaskSplitBoundsMap).get(Integer.valueOf(i));
        if (splitBounds != null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 2099540702148572685L, 21, Long.valueOf(i), Long.valueOf(splitBounds.leftTopTaskId), Long.valueOf(splitBounds.rightBottomTaskId));
                return splitBounds;
            }
        } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -1069799039355308561L, 1, Long.valueOf(i));
        }
        return splitBounds;
    }

    public final ActivityManager.RunningTaskInfo getTopRunningTask(WindowContainerToken windowContainerToken) {
        List tasks = this.mActivityTaskManager.getTasks(2, false);
        for (int i = 0; i < tasks.size(); i++) {
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) tasks.get(i);
            if (!runningTaskInfo.token.equals(windowContainerToken)) {
                return runningTaskInfo;
            }
        }
        return null;
    }

    public boolean hasRecentTasksListener() {
        return this.mListener != null;
    }

    public final void initializeDesksMap(boolean z) {
        ((HashMap) this.mTmpDesks).clear();
        if (((DesktopStateImpl) this.mDesktopState).canEnterDesktopMode && this.mDesktopUserRepositories.isPresent()) {
            if (!z) {
                getOrCreateDesk(-1);
                return;
            }
            Iterator it = ((DesktopUserRepositories) this.mDesktopUserRepositories.get()).getCurrent().getAllDeskIds().iterator();
            while (it.hasNext()) {
                getOrCreateDesk(((Integer) it.next()).intValue());
            }
        }
    }

    public void notifyRecentTasksChanged() {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENT_TASKS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENT_TASKS, -1654962621925607965L, 0, null);
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

    @Override // com.android.wm.shell.desktopmode.DesktopRepository.ActiveTasksListener
    public final void onActiveTasksChanged(int i) {
        notifyRecentTasksChanged();
    }

    @Override // com.android.wm.shell.common.TaskStackListenerCallback
    public final void onLockTaskModeChanged() {
        notifyRecentTasksChanged();
    }

    @Override // com.android.wm.shell.common.TaskStackListenerCallback
    public final void onRecentTaskListUpdated() {
        notifyRecentTasksChanged();
    }

    @Override // com.android.wm.shell.common.TaskStackListenerCallback
    public final void onRecentTaskRemovedForAddTask(final int i) {
        this.mDesktopUserRepositories.ifPresent(new Consumer() { // from class: com.android.wm.shell.recents.RecentTasksController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((DesktopUserRepositories) obj).getCurrent().removeTask(-1, i);
            }
        });
    }

    @Override // com.android.wm.shell.common.TaskStackListenerCallback
    public final void onTaskStackChanged() {
        notifyRecentTasksChanged();
    }

    @Override // com.android.wm.shell.sysui.UserChangeListener
    public final void onUserChanged(int i, Context context) {
        if (this.mDesktopUserRepositories.isEmpty()) {
            return;
        }
        DesktopRepository profile = ((DesktopUserRepositories) this.mDesktopUserRepositories.get()).getProfile(this.mUserId);
        this.mUserId = i;
        DesktopRepository profile2 = ((DesktopUserRepositories) this.mDesktopUserRepositories.get()).getProfile(i);
        if (profile.userId == profile2.userId) {
            return;
        }
        profile.activeTasksListeners.remove(this);
        profile2.activeTasksListeners.add(this);
    }

    public void registerRecentTasksListener(IRecentTasksListener iRecentTasksListener) {
        this.mListener = iRecentTasksListener;
    }

    public final void removeMultiSplitPair(int i, int i2) {
        this.mMultiSplitTasks.delete(i);
        this.mMultiSplitTasks.delete(i2);
        ((HashMap) this.mTaskSplitBoundsMap).remove(Integer.valueOf(i));
        ((HashMap) this.mTaskSplitBoundsMap).remove(Integer.valueOf(i2));
    }

    public final void removeSplitPair(int i) {
        int i2;
        int taskIdKey;
        int i3 = this.mSplitTasks.get(i, -1);
        if (i3 == -1) {
            if (!CoreRune.MW_MULTI_SPLIT_RECENT_TASKS || -1 == this.mMultiSplitTasks.get(i, -1)) {
                return;
            }
            int i4 = this.mMultiSplitTasks.get(i, -1);
            int i5 = this.mSplitTasks.get(i4, -1);
            this.mSplitTasks.delete(i4);
            this.mSplitTasks.delete(i5);
            ((HashMap) this.mTaskSplitBoundsMap).remove(Integer.valueOf(i4));
            ((HashMap) this.mTaskSplitBoundsMap).remove(Integer.valueOf(i5));
            removeMultiSplitPair(i, i4);
            int possibleRemoveTaskIdKey = this.mSaveController.getPossibleRemoveTaskIdKey(i4, i5, i);
            if (possibleRemoveTaskIdKey != -1) {
                this.mSaveController.removeGruopedRecentTaskSaveInfo(possibleRemoveTaskIdKey);
                this.mSaveController.scheduleSaveGroupedRecentTasks();
            }
            notifyRecentTasksChanged();
            return;
        }
        this.mSplitTasks.delete(i);
        this.mSplitTasks.delete(i3);
        ((HashMap) this.mTaskSplitBoundsMap).remove(Integer.valueOf(i));
        ((HashMap) this.mTaskSplitBoundsMap).remove(Integer.valueOf(i3));
        boolean z = CoreRune.MW_MULTI_SPLIT_RECENT_TASKS;
        boolean z2 = false;
        if (z) {
            i2 = this.mMultiSplitTasks.get(i, -1);
            if (i2 != -1) {
                removeMultiSplitPair(i, i2);
            } else {
                i2 = this.mMultiSplitTasks.get(i3, -1);
                if (i2 != -1) {
                    removeMultiSplitPair(i3, i2);
                }
            }
            z2 = true;
        } else {
            i2 = -1;
        }
        notifyRecentTasksChanged();
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, -2481655932528676172L, 5, Long.valueOf(i), Long.valueOf(i3));
        }
        if (z && z2) {
            taskIdKey = this.mSaveController.getPossibleRemoveTaskIdKey(i, i3, i2);
        } else {
            GroupedRecentTaskSaveController groupedRecentTaskSaveController = this.mSaveController;
            synchronized (groupedRecentTaskSaveController.mGroupedRecentTaskSaveMap) {
                try {
                    int taskIdKey2 = GroupedRecentTaskSaveController.getTaskIdKey(i, i3);
                    if (((HashMap) groupedRecentTaskSaveController.mGroupedRecentTaskSaveMap).containsKey(Integer.valueOf(taskIdKey2))) {
                        taskIdKey = taskIdKey2;
                    } else {
                        taskIdKey = GroupedRecentTaskSaveController.getTaskIdKey(i3, i);
                        if (!((HashMap) groupedRecentTaskSaveController.mGroupedRecentTaskSaveMap).containsKey(Integer.valueOf(taskIdKey))) {
                            taskIdKey = -1;
                        }
                    }
                } finally {
                }
            }
        }
        if (taskIdKey != -1) {
            this.mSaveController.removeGruopedRecentTaskSaveInfo(taskIdKey);
            this.mSaveController.scheduleSaveGroupedRecentTasks();
        }
    }

    public final boolean shouldEnableRunningTasksForDesktopMode() {
        if (this.mPcFeatureEnabled) {
            return true;
        }
        return ((DesktopStateImpl) this.mDesktopState).canEnterDesktopMode && DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_TASKBAR_RUNNING_APPS.isTrue();
    }

    public void unregisterRecentTasksListener() {
        this.mListener = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:127:0x02f8  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x010e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.ArrayList generateList(int r17, java.util.List r18) {
        /*
            Method dump skipped, instructions count: 798
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.recents.RecentTasksController.generateList(int, java.util.List):java.util.ArrayList");
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Desk {
        public final int mDeskId;
        public final ArrayList mDeskTasks;
        public final int mDisplayId;
        public final Set mMinimizedDeskTasks;
        public final int mUsedDesk;

        public Desk(int i, int i2) {
            this.mDeskTasks = new ArrayList();
            this.mMinimizedDeskTasks = new HashSet();
            this.mUsedDesk = -1;
            this.mDeskId = i;
            this.mDisplayId = i2;
        }

        public Desk(int i, int i2, int i3) {
            this.mDeskTasks = new ArrayList();
            this.mMinimizedDeskTasks = new HashSet();
            this.mDeskId = i;
            this.mDisplayId = i2;
            this.mUsedDesk = i3;
        }
    }
}
