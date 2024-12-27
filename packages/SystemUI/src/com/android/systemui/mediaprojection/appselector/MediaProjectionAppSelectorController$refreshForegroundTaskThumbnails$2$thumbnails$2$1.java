package com.android.systemui.mediaprojection.appselector;

import com.android.systemui.mediaprojection.appselector.data.ActivityTaskManagerThumbnailLoader;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.mediaprojection.appselector.data.RecentTaskThumbnailLoader;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class MediaProjectionAppSelectorController$refreshForegroundTaskThumbnails$2$thumbnails$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ RecentTask $it;
    int label;
    final /* synthetic */ MediaProjectionAppSelectorController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaProjectionAppSelectorController$refreshForegroundTaskThumbnails$2$thumbnails$2$1(MediaProjectionAppSelectorController mediaProjectionAppSelectorController, RecentTask recentTask, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaProjectionAppSelectorController;
        this.$it = recentTask;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaProjectionAppSelectorController$refreshForegroundTaskThumbnails$2$thumbnails$2$1(this.this$0, this.$it, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaProjectionAppSelectorController$refreshForegroundTaskThumbnails$2$thumbnails$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            RecentTaskThumbnailLoader recentTaskThumbnailLoader = this.this$0.thumbnailLoader;
            int i2 = this.$it.taskId;
            this.label = 1;
            obj = ((ActivityTaskManagerThumbnailLoader) recentTaskThumbnailLoader).captureThumbnail(i2, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return obj;
    }
}
