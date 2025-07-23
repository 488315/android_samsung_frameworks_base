package com.android.systemui.mediaprojection.appselector;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Deferred;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class MediaProjectionAppSelectorController$refreshForegroundTaskThumbnails$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ List<RecentTask> $tasks;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MediaProjectionAppSelectorController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaProjectionAppSelectorController$refreshForegroundTaskThumbnails$2(List<RecentTask> list, MediaProjectionAppSelectorController mediaProjectionAppSelectorController, Continuation continuation) {
        super(2, continuation);
        this.$tasks = list;
        this.this$0 = mediaProjectionAppSelectorController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaProjectionAppSelectorController$refreshForegroundTaskThumbnails$2 mediaProjectionAppSelectorController$refreshForegroundTaskThumbnails$2 = new MediaProjectionAppSelectorController$refreshForegroundTaskThumbnails$2(this.$tasks, this.this$0, continuation);
        mediaProjectionAppSelectorController$refreshForegroundTaskThumbnails$2.L$0 = obj;
        return mediaProjectionAppSelectorController$refreshForegroundTaskThumbnails$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaProjectionAppSelectorController$refreshForegroundTaskThumbnails$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Iterator it;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            List<RecentTask> list = this.$tasks;
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : list) {
                if (((RecentTask) obj2).isForegroundTask) {
                    arrayList.add(obj2);
                }
            }
            MediaProjectionAppSelectorController mediaProjectionAppSelectorController = this.this$0;
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj3 = arrayList.get(i2);
                i2++;
                arrayList2.add(CoroutineTracingKt.asyncTraced$default(coroutineScope, null, null, new MediaProjectionAppSelectorController$refreshForegroundTaskThumbnails$2$thumbnails$2$1(mediaProjectionAppSelectorController, (RecentTask) obj3, null), 7));
            }
            it = arrayList2.iterator();
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            it = (Iterator) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        while (it.hasNext()) {
            Deferred deferred = (Deferred) it.next();
            this.L$0 = it;
            this.label = 1;
            if (deferred.await(this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Unit.INSTANCE;
    }
}
