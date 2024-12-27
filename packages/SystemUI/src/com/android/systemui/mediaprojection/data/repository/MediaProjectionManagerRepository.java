package com.android.systemui.mediaprojection.data.repository;

import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import com.android.systemui.mediaprojection.MediaProjectionServiceHelper;
import com.android.systemui.mediaprojection.data.model.MediaProjectionState;
import com.android.systemui.mediaprojection.taskswitcher.data.repository.TasksRepository;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaProjectionManagerRepository implements MediaProjectionRepository {
    public final CoroutineDispatcher backgroundDispatcher;
    public final Handler handler;
    public final MediaProjectionManager mediaProjectionManager;
    public final MediaProjectionServiceHelper mediaProjectionServiceHelper;
    public final ReadonlyStateFlow mediaProjectionState;
    public final TasksRepository tasksRepository;

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

    public MediaProjectionManagerRepository(MediaProjectionManager mediaProjectionManager, Handler handler, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, TasksRepository tasksRepository, MediaProjectionServiceHelper mediaProjectionServiceHelper) {
        this.mediaProjectionManager = mediaProjectionManager;
        this.handler = handler;
        this.backgroundDispatcher = coroutineDispatcher;
        this.tasksRepository = tasksRepository;
        Flow conflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new MediaProjectionManagerRepository$mediaProjectionState$1(this, null));
        SharingStarted.Companion.getClass();
        this.mediaProjectionState = FlowKt.stateIn(conflatedCallbackFlow, coroutineScope, SharingStarted.Companion.Lazily, MediaProjectionState.NotProjecting.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$stateForSession(com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository r4, android.media.projection.MediaProjectionInfo r5, android.view.ContentRecordingSession r6, kotlin.coroutines.Continuation r7) {
        /*
            r4.getClass()
            boolean r0 = r7 instanceof com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$stateForSession$1
            if (r0 == 0) goto L16
            r0 = r7
            com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$stateForSession$1 r0 = (com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$stateForSession$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$stateForSession$1 r0 = new com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$stateForSession$1
            r0.<init>(r4, r7)
        L1b:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r4 = r0.L$0
            java.lang.String r4 = (java.lang.String) r4
            kotlin.ResultKt.throwOnFailure(r7)
            goto L65
        L2e:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L36:
            kotlin.ResultKt.throwOnFailure(r7)
            if (r6 != 0) goto L3e
            com.android.systemui.mediaprojection.data.model.MediaProjectionState$NotProjecting r1 = com.android.systemui.mediaprojection.data.model.MediaProjectionState.NotProjecting.INSTANCE
            goto L8f
        L3e:
            java.lang.String r5 = r5.getPackageName()
            int r7 = r6.getContentToRecord()
            if (r7 == 0) goto L87
            android.os.IBinder r7 = r6.getTokenToRecord()
            if (r7 != 0) goto L4f
            goto L87
        L4f:
            android.os.IBinder r6 = r6.getTokenToRecord()
            if (r6 == 0) goto L7b
            r0.L$0 = r5
            r0.label = r3
            com.android.systemui.mediaprojection.taskswitcher.data.repository.TasksRepository r4 = r4.tasksRepository
            com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository r4 = (com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository) r4
            java.lang.Object r7 = r4.findRunningTaskFromWindowContainerToken(r6, r0)
            if (r7 != r1) goto L64
            goto L8f
        L64:
            r4 = r5
        L65:
            android.app.ActivityManager$RunningTaskInfo r7 = (android.app.ActivityManager.RunningTaskInfo) r7
            if (r7 != 0) goto L72
            com.android.systemui.mediaprojection.data.model.MediaProjectionState$Projecting$EntireScreen r1 = new com.android.systemui.mediaprojection.data.model.MediaProjectionState$Projecting$EntireScreen
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
            r1.<init>(r4)
            goto L8f
        L72:
            com.android.systemui.mediaprojection.data.model.MediaProjectionState$Projecting$SingleTask r1 = new com.android.systemui.mediaprojection.data.model.MediaProjectionState$Projecting$SingleTask
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
            r1.<init>(r4, r7)
            goto L8f
        L7b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "Required value was null."
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L87:
            com.android.systemui.mediaprojection.data.model.MediaProjectionState$Projecting$EntireScreen r1 = new com.android.systemui.mediaprojection.data.model.MediaProjectionState$Projecting$EntireScreen
            kotlin.jvm.internal.Intrinsics.checkNotNull(r5)
            r1.<init>(r5)
        L8f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository.access$stateForSession(com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository, android.media.projection.MediaProjectionInfo, android.view.ContentRecordingSession, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object stopProjecting(Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.backgroundDispatcher, new MediaProjectionManagerRepository$stopProjecting$2(this, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }
}
