package com.android.wm.shell.desktopmode.persistence;

import com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepositories;
import com.android.wm.shell.desktopmode.persistence.DesktopRepositoryState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class DesktopPersistentRepository$removeDesktop$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $desktopId;
    final /* synthetic */ int $userId;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DesktopPersistentRepository$removeDesktop$2(int i, int i2, Continuation continuation) {
        super(2, continuation);
        this.$userId = i;
        this.$desktopId = i2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DesktopPersistentRepository$removeDesktop$2 desktopPersistentRepository$removeDesktop$2 = new DesktopPersistentRepository$removeDesktop$2(this.$userId, this.$desktopId, continuation);
        desktopPersistentRepository$removeDesktop$2.L$0 = obj;
        return desktopPersistentRepository$removeDesktop$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DesktopPersistentRepository$removeDesktop$2) create((DesktopPersistentRepositories) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        DesktopPersistentRepositories desktopPersistentRepositories = (DesktopPersistentRepositories) this.L$0;
        DesktopRepositoryState desktopRepoByUserOrDefault = desktopPersistentRepositories.getDesktopRepoByUserOrDefault(this.$userId, DesktopRepositoryState.getDefaultInstance());
        DesktopPersistentRepositories.Builder builder = (DesktopPersistentRepositories.Builder) desktopPersistentRepositories.toBuilder();
        int i = this.$userId;
        DesktopRepositoryState.Builder builder2 = (DesktopRepositoryState.Builder) desktopRepoByUserOrDefault.toBuilder();
        int i2 = this.$desktopId;
        builder2.copyOnWrite();
        DesktopRepositoryState.m3242$$Nest$mgetMutableDesktopMap((DesktopRepositoryState) builder2.instance).remove(Integer.valueOf(i2));
        DesktopRepositoryState desktopRepositoryState = (DesktopRepositoryState) builder2.build();
        builder.copyOnWrite();
        DesktopPersistentRepositories.m3240$$Nest$mgetMutableDesktopRepoByUserMap((DesktopPersistentRepositories) builder.instance).put(Integer.valueOf(i), desktopRepositoryState);
        return builder.build();
    }
}
