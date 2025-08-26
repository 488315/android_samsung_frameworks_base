package com.android.systemui.inputdevice.tutorial.domain.interactor;

import com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository;
import java.io.PrintWriter;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class TutorialSchedulerInteractor$TutorialCommand$execute$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ PrintWriter $pw;
    int label;
    final /* synthetic */ TutorialSchedulerInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TutorialSchedulerInteractor$TutorialCommand$execute$1(TutorialSchedulerInteractor tutorialSchedulerInteractor, PrintWriter printWriter, Continuation continuation) {
        super(2, continuation);
        this.this$0 = tutorialSchedulerInteractor;
        this.$pw = printWriter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TutorialSchedulerInteractor$TutorialCommand$execute$1(this.this$0, this.$pw, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TutorialSchedulerInteractor$TutorialCommand$execute$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            TutorialSchedulerRepository tutorialSchedulerRepository = this.this$0.repo;
            this.label = 1;
            if (tutorialSchedulerRepository.clear(this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        this.$pw.println("Tutorial scheduler reset");
        return Unit.INSTANCE;
    }
}
