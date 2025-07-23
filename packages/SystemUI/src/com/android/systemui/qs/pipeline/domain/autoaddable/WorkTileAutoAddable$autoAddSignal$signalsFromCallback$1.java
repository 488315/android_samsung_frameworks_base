package com.android.systemui.qs.pipeline.domain.autoaddable;

import android.content.pm.UserInfo;
import com.android.systemui.qs.pipeline.data.restoreprocessors.WorkTileRestoreProcessor;
import com.android.systemui.qs.pipeline.domain.model.AutoAddSignal;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class WorkTileAutoAddable$autoAddSignal$signalsFromCallback$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $userId;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ WorkTileAutoAddable this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WorkTileAutoAddable$autoAddSignal$signalsFromCallback$1(WorkTileAutoAddable workTileAutoAddable, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = workTileAutoAddable;
        this.$userId = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void invokeSuspend$maybeSend(ProducerScope producerScope, WorkTileAutoAddable workTileAutoAddable, int i, List list) {
        int i2;
        List<UserInfo> list2 = list;
        boolean z = list2 instanceof Collection;
        if (z && list2.isEmpty()) {
            return;
        }
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            if (((UserInfo) it.next()).id == i) {
                if (!z || !list2.isEmpty()) {
                    for (UserInfo userInfo : list2) {
                        if (userInfo.isManagedProfile() && userInfo.isEnabled()) {
                            TileSpec tileSpec = workTileAutoAddable.spec;
                            WorkTileRestoreProcessor workTileRestoreProcessor = workTileAutoAddable.workTileRestoreProcessor;
                            synchronized (workTileRestoreProcessor.lastRestorePosition) {
                                i2 = workTileRestoreProcessor.lastRestorePosition.get(i, -1);
                                workTileRestoreProcessor.lastRestorePosition.delete(i);
                            }
                            ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(new AutoAddSignal.Add(tileSpec, i2));
                            return;
                        }
                    }
                }
                ((ChannelCoroutine) producerScope).mo3456trySendJP2dKIU(new AutoAddSignal.Remove(workTileAutoAddable.spec));
                return;
            }
        }
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WorkTileAutoAddable$autoAddSignal$signalsFromCallback$1 workTileAutoAddable$autoAddSignal$signalsFromCallback$1 = new WorkTileAutoAddable$autoAddSignal$signalsFromCallback$1(this.this$0, this.$userId, continuation);
        workTileAutoAddable$autoAddSignal$signalsFromCallback$1.L$0 = obj;
        return workTileAutoAddable$autoAddSignal$signalsFromCallback$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WorkTileAutoAddable$autoAddSignal$signalsFromCallback$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.pipeline.domain.autoaddable.WorkTileAutoAddable$autoAddSignal$signalsFromCallback$1$callback$1, com.android.systemui.settings.UserTracker$Callback] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final WorkTileAutoAddable workTileAutoAddable = this.this$0;
            final int i2 = this.$userId;
            final ?? r1 = new UserTracker.Callback() { // from class: com.android.systemui.qs.pipeline.domain.autoaddable.WorkTileAutoAddable$autoAddSignal$signalsFromCallback$1$callback$1
                @Override // com.android.systemui.settings.UserTracker.Callback
                public final void onProfilesChanged(List list) {
                    WorkTileAutoAddable$autoAddSignal$signalsFromCallback$1.invokeSuspend$maybeSend(ProducerScope.this, workTileAutoAddable, i2, list);
                }
            };
            ((UserTrackerImpl) this.this$0.userTracker).addCallback(r1, new Executor() { // from class: com.android.systemui.qs.pipeline.domain.autoaddable.WorkTileAutoAddable$autoAddSignal$signalsFromCallback$1.1
                @Override // java.util.concurrent.Executor
                public final void execute(Runnable runnable) {
                    runnable.run();
                }
            });
            WorkTileAutoAddable workTileAutoAddable2 = this.this$0;
            invokeSuspend$maybeSend(producerScope, workTileAutoAddable2, this.$userId, ((UserTrackerImpl) workTileAutoAddable2.userTracker).getUserProfiles());
            final WorkTileAutoAddable workTileAutoAddable3 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.qs.pipeline.domain.autoaddable.WorkTileAutoAddable$autoAddSignal$signalsFromCallback$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((UserTrackerImpl) WorkTileAutoAddable.this.userTracker).removeCallback(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
