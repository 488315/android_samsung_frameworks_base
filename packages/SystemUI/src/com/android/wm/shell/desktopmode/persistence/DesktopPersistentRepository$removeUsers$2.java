package com.android.wm.shell.desktopmode.persistence;

import com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepositories;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class DesktopPersistentRepository$removeUsers$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ List<Integer> $uids;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DesktopPersistentRepository$removeUsers$2(List<Integer> list, Continuation continuation) {
        super(2, continuation);
        this.$uids = list;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DesktopPersistentRepository$removeUsers$2 desktopPersistentRepository$removeUsers$2 = new DesktopPersistentRepository$removeUsers$2(this.$uids, continuation);
        desktopPersistentRepository$removeUsers$2.L$0 = obj;
        return desktopPersistentRepository$removeUsers$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DesktopPersistentRepository$removeUsers$2) create((DesktopPersistentRepositories) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DesktopPersistentRepositories.Builder builder = (DesktopPersistentRepositories.Builder) ((DesktopPersistentRepositories) this.L$0).toBuilder();
        Iterator<T> it = this.$uids.iterator();
        while (it.hasNext()) {
            int intValue = ((Number) it.next()).intValue();
            builder.copyOnWrite();
            DesktopPersistentRepositories.m3240$$Nest$mgetMutableDesktopRepoByUserMap((DesktopPersistentRepositories) builder.instance).remove(Integer.valueOf(intValue));
        }
        return builder.build();
    }
}
