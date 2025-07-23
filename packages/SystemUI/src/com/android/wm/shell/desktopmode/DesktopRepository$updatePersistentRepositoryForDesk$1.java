package com.android.wm.shell.desktopmode;

import com.android.wm.shell.desktopmode.DesktopRepository;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class DesktopRepository$updatePersistentRepositoryForDesk$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ DesktopRepository.Desk $desk;
    int label;
    final /* synthetic */ DesktopRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DesktopRepository$updatePersistentRepositoryForDesk$1(DesktopRepository desktopRepository, DesktopRepository.Desk desk, Continuation continuation) {
        super(2, continuation);
        this.this$0 = desktopRepository;
        this.$desk = desk;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DesktopRepository$updatePersistentRepositoryForDesk$1(this.this$0, this.$desk, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DesktopRepository$updatePersistentRepositoryForDesk$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DesktopRepository desktopRepository = this.this$0;
            DesktopRepository.Desk desk = this.$desk;
            this.label = 1;
            if (DesktopRepository.access$updatePersistentRepositoryForDesk(desktopRepository, desk, this) == coroutineSingletons) {
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
