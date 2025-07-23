package com.android.wm.shell.desktopmode;

import com.android.wm.shell.desktopmode.DesktopRepository;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class DesktopRepository$updatePersistentRepository$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ List<DesktopRepository.Desk> $desks;
    Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ DesktopRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DesktopRepository$updatePersistentRepository$1(List<DesktopRepository.Desk> list, DesktopRepository desktopRepository, Continuation continuation) {
        super(2, continuation);
        this.$desks = list;
        this.this$0 = desktopRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DesktopRepository$updatePersistentRepository$1(this.$desks, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DesktopRepository$updatePersistentRepository$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        DesktopRepository desktopRepository;
        Iterator it;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            List<DesktopRepository.Desk> list = this.$desks;
            desktopRepository = this.this$0;
            it = list.iterator();
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            it = (Iterator) this.L$1;
            desktopRepository = (DesktopRepository) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        while (it.hasNext()) {
            DesktopRepository.Desk desk = (DesktopRepository.Desk) it.next();
            this.L$0 = desktopRepository;
            this.L$1 = it;
            this.label = 1;
            if (DesktopRepository.access$updatePersistentRepositoryForDesk(desktopRepository, desk, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Unit.INSTANCE;
    }
}
