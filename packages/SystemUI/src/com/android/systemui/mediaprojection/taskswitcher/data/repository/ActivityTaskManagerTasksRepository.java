package com.android.systemui.mediaprojection.taskswitcher.data.repository;

import android.app.IActivityTaskManager;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ActivityTaskManagerTasksRepository implements TasksRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final IActivityTaskManager activityTaskManager;
    public final CoroutineDispatcher backgroundDispatcher;
    public final ReadonlySharedFlow foregroundTask;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ActivityTaskManagerTasksRepository(IActivityTaskManager iActivityTaskManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.activityTaskManager = iActivityTaskManager;
        this.backgroundDispatcher = coroutineDispatcher;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        ActivityTaskManagerTasksRepository$foregroundTask$1 activityTaskManagerTasksRepository$foregroundTask$1 = new ActivityTaskManagerTasksRepository$foregroundTask$1(this, null);
        conflatedCallbackFlow.getClass();
        Flow conflatedCallbackFlow2 = FlowConflatedKt.conflatedCallbackFlow(activityTaskManagerTasksRepository$foregroundTask$1);
        SharingStarted.Companion.getClass();
        this.foregroundTask = FlowKt.shareIn(conflatedCallbackFlow2, coroutineScope, SharingStarted.Companion.Lazily, 1);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object findRunningTaskFromWindowContainerToken(android.os.IBinder r5, kotlin.coroutines.Continuation r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository$findRunningTaskFromWindowContainerToken$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository$findRunningTaskFromWindowContainerToken$1 r0 = (com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository$findRunningTaskFromWindowContainerToken$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository$findRunningTaskFromWindowContainerToken$1 r0 = new com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository$findRunningTaskFromWindowContainerToken$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L34
            if (r2 != r3) goto L2c
            java.lang.Object r4 = r0.L$0
            r5 = r4
            android.os.IBinder r5 = (android.os.IBinder) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L42
        L2c:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L34:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.L$0 = r5
            r0.label = r3
            java.lang.Object r6 = r4.getRunningTasks(r0)
            if (r6 != r1) goto L42
            return r1
        L42:
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            java.util.Iterator r4 = r6.iterator()
        L48:
            boolean r6 = r4.hasNext()
            if (r6 == 0) goto L62
            java.lang.Object r6 = r4.next()
            r0 = r6
            android.app.ActivityManager$RunningTaskInfo r0 = (android.app.ActivityManager.RunningTaskInfo) r0
            android.window.WindowContainerToken r0 = r0.token
            android.os.IBinder r0 = r0.asBinder()
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r5)
            if (r0 == 0) goto L48
            goto L63
        L62:
            r6 = 0
        L63:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository.findRunningTaskFromWindowContainerToken(android.os.IBinder, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getRunningTasks(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository$getRunningTasks$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository$getRunningTasks$1 r0 = (com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository$getRunningTasks$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository$getRunningTasks$1 r0 = new com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository$getRunningTasks$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r5)
            goto L43
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository$getRunningTasks$2 r5 = new com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository$getRunningTasks$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            kotlinx.coroutines.CoroutineDispatcher r4 = r4.backgroundDispatcher
            java.lang.Object r5 = kotlinx.coroutines.BuildersKt.withContext(r4, r5, r0)
            if (r5 != r1) goto L43
            return r1
        L43:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository.getRunningTasks(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
