package com.android.systemui.mediaprojection.appselector.data;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.recents.RecentTasks;
import com.android.p038wm.shell.recents.RecentTasksController;
import com.android.p038wm.shell.util.GroupedRecentTaskInfo;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.mediaprojection.appselector.data.ShellRecentTaskListProvider$loadRecentTasks$2", m277f = "RecentTaskListProvider.kt", m278l = {51}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class ShellRecentTaskListProvider$loadRecentTasks$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ShellRecentTaskListProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShellRecentTaskListProvider$loadRecentTasks$2(ShellRecentTaskListProvider shellRecentTaskListProvider, Continuation<? super ShellRecentTaskListProvider$loadRecentTasks$2> continuation) {
        super(2, continuation);
        this.this$0 = shellRecentTaskListProvider;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShellRecentTaskListProvider$loadRecentTasks$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShellRecentTaskListProvider$loadRecentTasks$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x005c, code lost:
    
        if (r10 != null) goto L17;
     */
    /* JADX WARN: Type inference failed for: r4v0, types: [com.android.systemui.mediaprojection.appselector.data.ShellRecentTaskListProvider$getTasks$2$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        Iterable iterable;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            RecentTasks recentTasks = (RecentTasks) this.this$0.recents$delegate.getValue();
            if (recentTasks != null) {
                ShellRecentTaskListProvider shellRecentTaskListProvider = this.this$0;
                this.label = 1;
                shellRecentTaskListProvider.getClass();
                final SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this));
                final int userId = ((UserTrackerImpl) shellRecentTaskListProvider.userTracker).getUserId();
                final ?? r4 = new Consumer() { // from class: com.android.systemui.mediaprojection.appselector.data.ShellRecentTaskListProvider$getTasks$2$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        Continuation continuation = safeContinuation;
                        int i2 = Result.$r8$clinit;
                        continuation.resumeWith((List) obj2);
                    }
                };
                final RecentTasksController.RecentTasksImpl recentTasksImpl = (RecentTasksController.RecentTasksImpl) recentTasks;
                ShellExecutor shellExecutor = RecentTasksController.this.mMainExecutor;
                final Executor executor = shellRecentTaskListProvider.backgroundExecutor;
                ((HandlerExecutor) shellExecutor).execute(new Runnable() { // from class: com.android.wm.shell.recents.RecentTasksController$RecentTasksImpl$$ExternalSyntheticLambda1
                    public final /* synthetic */ int f$1 = Integer.MAX_VALUE;
                    public final /* synthetic */ int f$2 = 2;

                    @Override // java.lang.Runnable
                    public final void run() {
                        RecentTasksController.RecentTasksImpl recentTasksImpl2 = RecentTasksController.RecentTasksImpl.this;
                        int i2 = this.f$1;
                        int i3 = this.f$2;
                        int i4 = userId;
                        Executor executor2 = executor;
                        final Consumer consumer = r4;
                        final ArrayList<GroupedRecentTaskInfo> recentTasks2 = RecentTasksController.this.getRecentTasks(i2, i3, i4);
                        executor2.execute(new Runnable() { // from class: com.android.wm.shell.recents.RecentTasksController$RecentTasksImpl$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                consumer.accept(recentTasks2);
                            }
                        });
                    }
                });
                obj = safeContinuation.getOrThrow();
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            iterable = EmptyList.INSTANCE;
            ArrayList arrayList = new ArrayList();
            Iterator it = iterable.iterator();
            while (true) {
                ActivityManager.RecentTaskInfo recentTaskInfo = null;
                if (!it.hasNext()) {
                    break;
                }
                ActivityManager.RecentTaskInfo[] recentTaskInfoArr = new ActivityManager.RecentTaskInfo[2];
                ActivityManager.RecentTaskInfo[] recentTaskInfoArr2 = ((GroupedRecentTaskInfo) it.next()).mTasks;
                recentTaskInfoArr[0] = recentTaskInfoArr2[0];
                if (recentTaskInfoArr2.length > 1) {
                    recentTaskInfo = recentTaskInfoArr2[1];
                }
                recentTaskInfoArr[1] = recentTaskInfo;
                CollectionsKt__MutableCollectionsKt.addAll(ArraysKt___ArraysKt.filterNotNull(recentTaskInfoArr), arrayList);
            }
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ActivityManager.RecentTaskInfo recentTaskInfo2 = (ActivityManager.RecentTaskInfo) it2.next();
                int i2 = recentTaskInfo2.taskId;
                int i3 = recentTaskInfo2.userId;
                ComponentName componentName = recentTaskInfo2.topActivity;
                Intent intent = recentTaskInfo2.baseIntent;
                ComponentName component = intent != null ? intent.getComponent() : null;
                ActivityManager.TaskDescription taskDescription = recentTaskInfo2.taskDescription;
                arrayList2.add(new RecentTask(i2, i3, componentName, component, taskDescription != null ? new Integer(taskDescription.getBackgroundColor()) : null));
            }
            return arrayList2;
        }
        if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        iterable = (List) obj;
    }
}
