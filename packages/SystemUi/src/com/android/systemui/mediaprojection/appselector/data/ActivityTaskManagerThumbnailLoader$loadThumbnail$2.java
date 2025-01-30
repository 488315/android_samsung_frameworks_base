package com.android.systemui.mediaprojection.appselector.data;

import android.app.ActivityTaskManager;
import android.os.RemoteException;
import android.util.Log;
import android.window.TaskSnapshot;
import com.android.systemui.shared.recents.model.ThumbnailData;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.mediaprojection.appselector.data.ActivityTaskManagerThumbnailLoader$loadThumbnail$2", m277f = "RecentTaskThumbnailLoader.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class ActivityTaskManagerThumbnailLoader$loadThumbnail$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $taskId;
    int label;
    final /* synthetic */ ActivityTaskManagerThumbnailLoader this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActivityTaskManagerThumbnailLoader$loadThumbnail$2(ActivityTaskManagerThumbnailLoader activityTaskManagerThumbnailLoader, int i, Continuation<? super ActivityTaskManagerThumbnailLoader$loadThumbnail$2> continuation) {
        super(2, continuation);
        this.this$0 = activityTaskManagerThumbnailLoader;
        this.$taskId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ActivityTaskManagerThumbnailLoader$loadThumbnail$2(this.this$0, this.$taskId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ActivityTaskManagerThumbnailLoader$loadThumbnail$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        TaskSnapshot taskSnapshot;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ActivityManagerWrapper activityManagerWrapper = this.this$0.activityManager;
        int i = this.$taskId;
        activityManagerWrapper.getClass();
        try {
            taskSnapshot = ActivityTaskManager.getService().getTaskSnapshot(i, false, true);
        } catch (RemoteException e) {
            Log.w("ActivityManagerWrapper", "Failed to retrieve task snapshot", e);
            taskSnapshot = null;
        }
        ThumbnailData thumbnailData = taskSnapshot != null ? new ThumbnailData(taskSnapshot) : new ThumbnailData();
        if (thumbnailData.thumbnail == null) {
            return null;
        }
        return thumbnailData;
    }
}
