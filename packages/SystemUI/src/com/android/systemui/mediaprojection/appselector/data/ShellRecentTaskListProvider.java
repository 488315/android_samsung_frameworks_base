package com.android.systemui.mediaprojection.appselector.data;

import android.app.TaskInfo;
import android.content.pm.UserInfo;
import android.os.UserManager;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.wm.shell.recents.RecentTasks;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.shared.GroupedTaskInfo;
import com.android.wm.shell.shared.split.SplitBounds;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class ShellRecentTaskListProvider implements RecentTaskListProvider {
    public final Executor backgroundExecutor;
    public final CoroutineDispatcher coroutineDispatcher;
    public final Optional recentTasks;
    public final Lazy recents$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.mediaprojection.appselector.data.ShellRecentTaskListProvider$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (RecentTasks) this.f$0.recentTasks.orElse(null);
        }
    });
    public final UserManager userManager;
    public final UserTracker userTracker;

    /* renamed from: com.android.systemui.mediaprojection.appselector.data.ShellRecentTaskListProvider$loadRecentTasks$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShellRecentTaskListProvider.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x006b  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0078  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x007b  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0082  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x0098  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x00a9  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x00c5  */
        /* JADX WARN: Removed duplicated region for block: B:62:0x013f  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            List<GroupedTaskInfo> list;
            GroupedTaskInfo groupedTaskInfo;
            SplitBounds splitBounds;
            RecentTask recentTask;
            RecentTask recentTask2;
            List arrayList;
            TaskInfo taskInfo2;
            TaskInfo taskInfo1;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                RecentTasks recentTasks = (RecentTasks) ShellRecentTaskListProvider.this.recents$delegate.getValue();
                if (recentTasks != null) {
                    ShellRecentTaskListProvider shellRecentTaskListProvider = ShellRecentTaskListProvider.this;
                    this.label = 1;
                    shellRecentTaskListProvider.getClass();
                    SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this));
                    final int userId = ((UserTrackerImpl) shellRecentTaskListProvider.userTracker).getUserId();
                    final Executor executor = shellRecentTaskListProvider.backgroundExecutor;
                    final ShellRecentTaskListProvider$getTasks$2$1 shellRecentTaskListProvider$getTasks$2$1 = new ShellRecentTaskListProvider$getTasks$2$1(safeContinuation);
                    final RecentTasksController.RecentTasksImpl recentTasksImpl = (RecentTasksController.RecentTasksImpl) recentTasks;
                    RecentTasksController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.recents.RecentTasksController$RecentTasksImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            RecentTasksController.RecentTasksImpl recentTasksImpl2 = recentTasksImpl;
                            executor.execute(new RecentTasksController$RecentTasksImpl$$ExternalSyntheticLambda1(0, shellRecentTaskListProvider$getTasks$2$1, RecentTasksController.this.getRecentTasks(Integer.MAX_VALUE, 2, userId)));
                        }
                    });
                    obj = safeContinuation.getOrThrow();
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
                list = EmptyList.INSTANCE;
                groupedTaskInfo = (GroupedTaskInfo) CollectionsKt___CollectionsKt.firstOrNull(list);
                if (groupedTaskInfo != null) {
                    splitBounds = null;
                } else {
                    if (groupedTaskInfo.mType == 4) {
                        throw new IllegalStateException("No split bounds for a mixed task");
                    }
                    splitBounds = groupedTaskInfo.mSplitBounds;
                }
                GroupedTaskInfo groupedTaskInfo2 = splitBounds == null ? (GroupedTaskInfo) CollectionsKt___CollectionsKt.first(list) : (GroupedTaskInfo) CollectionsKt___CollectionsKt.getOrNull(1, list);
                List listFilterNotNull = ArraysKt___ArraysKt.filterNotNull(new Integer[]{(groupedTaskInfo2 != null || (taskInfo1 = groupedTaskInfo2.getTaskInfo1()) == null) ? null : new Integer(taskInfo1.taskId), (groupedTaskInfo2 != null || (taskInfo2 = groupedTaskInfo2.getTaskInfo2()) == null) ? null : new Integer(taskInfo2.taskId)});
                ShellRecentTaskListProvider shellRecentTaskListProvider2 = ShellRecentTaskListProvider.this;
                ArrayList arrayList2 = new ArrayList();
                for (GroupedTaskInfo groupedTaskInfo3 : list) {
                    int i2 = groupedTaskInfo3.mType;
                    boolean z = false;
                    if (i2 == 3) {
                        recentTask = null;
                    } else {
                        if (i2 == 4) {
                            List list2 = groupedTaskInfo3.mGroupedTasks;
                            arrayList = new ArrayList();
                            Iterator it = list2.iterator();
                            while (it.hasNext()) {
                                CollectionsKt__MutableCollectionsKt.addAll(((GroupedTaskInfo) it.next()).mTasks, arrayList);
                            }
                        } else {
                            arrayList = groupedTaskInfo3.mTasks;
                        }
                        if (!arrayList.isEmpty()) {
                            TaskInfo taskInfo12 = groupedTaskInfo3.getTaskInfo1();
                            boolean z2 = ((ArrayList) listFilterNotNull).contains(new Integer(groupedTaskInfo3.getTaskInfo1().taskId)) && groupedTaskInfo3.getTaskInfo1().isVisible;
                            RecentTask.UserType userTypeAccess$toUserType = ShellRecentTaskListProvider.access$toUserType(shellRecentTaskListProvider2, shellRecentTaskListProvider2.userManager.getUserInfo(groupedTaskInfo3.getTaskInfo1().userId));
                            if (groupedTaskInfo3.mType == 4) {
                                throw new IllegalStateException("No split bounds for a mixed task");
                            }
                            recentTask = new RecentTask(taskInfo12, z2, userTypeAccess$toUserType, groupedTaskInfo3.mSplitBounds);
                        }
                    }
                    if (groupedTaskInfo3.getTaskInfo2() != null) {
                        TaskInfo taskInfo22 = groupedTaskInfo3.getTaskInfo2();
                        taskInfo22.getClass();
                        TaskInfo taskInfo23 = groupedTaskInfo3.getTaskInfo2();
                        taskInfo23.getClass();
                        if (((ArrayList) listFilterNotNull).contains(new Integer(taskInfo23.taskId))) {
                            TaskInfo taskInfo24 = groupedTaskInfo3.getTaskInfo2();
                            taskInfo24.getClass();
                            if (taskInfo24.isVisible) {
                                z = true;
                            }
                        }
                        UserManager userManager = shellRecentTaskListProvider2.userManager;
                        TaskInfo taskInfo25 = groupedTaskInfo3.getTaskInfo2();
                        taskInfo25.getClass();
                        RecentTask.UserType userTypeAccess$toUserType2 = ShellRecentTaskListProvider.access$toUserType(shellRecentTaskListProvider2, userManager.getUserInfo(taskInfo25.userId));
                        if (groupedTaskInfo3.mType == 4) {
                            throw new IllegalStateException("No split bounds for a mixed task");
                        }
                        recentTask2 = new RecentTask(taskInfo22, z, userTypeAccess$toUserType2, groupedTaskInfo3.mSplitBounds);
                    } else {
                        recentTask2 = null;
                    }
                    CollectionsKt__MutableCollectionsKt.addAll(ArraysKt___ArraysKt.filterNotNull(new RecentTask[]{recentTask, recentTask2}), arrayList2);
                }
                return arrayList2;
            }
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            list = (List) obj;
            if (list == null) {
                list = EmptyList.INSTANCE;
            }
            groupedTaskInfo = (GroupedTaskInfo) CollectionsKt___CollectionsKt.firstOrNull(list);
            if (groupedTaskInfo != null) {
            }
            if (splitBounds == null) {
            }
            List listFilterNotNull2 = ArraysKt___ArraysKt.filterNotNull(new Integer[]{(groupedTaskInfo2 != null || (taskInfo1 = groupedTaskInfo2.getTaskInfo1()) == null) ? null : new Integer(taskInfo1.taskId), (groupedTaskInfo2 != null || (taskInfo2 = groupedTaskInfo2.getTaskInfo2()) == null) ? null : new Integer(taskInfo2.taskId)});
            ShellRecentTaskListProvider shellRecentTaskListProvider22 = ShellRecentTaskListProvider.this;
            ArrayList arrayList22 = new ArrayList();
            while (r14.hasNext()) {
            }
            return arrayList22;
        }
    }

    public ShellRecentTaskListProvider(CoroutineDispatcher coroutineDispatcher, Executor executor, Optional<RecentTasks> optional, UserTracker userTracker, UserManager userManager) {
        this.coroutineDispatcher = coroutineDispatcher;
        this.backgroundExecutor = executor;
        this.recentTasks = optional;
        this.userTracker = userTracker;
        this.userManager = userManager;
    }

    public static final RecentTask.UserType access$toUserType(ShellRecentTaskListProvider shellRecentTaskListProvider, UserInfo userInfo) {
        shellRecentTaskListProvider.getClass();
        return userInfo.isCloneProfile() ? RecentTask.UserType.CLONED : userInfo.isManagedProfile() ? RecentTask.UserType.WORK : userInfo.isPrivateProfile() ? RecentTask.UserType.PRIVATE : RecentTask.UserType.STANDARD;
    }

    public final Object loadRecentTasks(Continuation continuation) {
        return BuildersKt.withContext(this.coroutineDispatcher, new AnonymousClass2(null), continuation);
    }
}
