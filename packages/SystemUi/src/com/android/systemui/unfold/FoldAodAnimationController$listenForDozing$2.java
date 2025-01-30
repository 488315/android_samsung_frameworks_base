package com.android.systemui.unfold;

import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.unfold.FoldAodAnimationController$listenForDozing$2", m277f = "FoldAodAnimationController.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_deleteHomeScreenPage}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class FoldAodAnimationController$listenForDozing$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FoldAodAnimationController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FoldAodAnimationController$listenForDozing$2(FoldAodAnimationController foldAodAnimationController, Continuation<? super FoldAodAnimationController$listenForDozing$2> continuation) {
        super(2, continuation);
        this.this$0 = foldAodAnimationController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FoldAodAnimationController$listenForDozing$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FoldAodAnimationController$listenForDozing$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            StateFlow stateFlow = ((KeyguardInteractor) this.this$0.keyguardInteractor.get()).isDozing;
            final FoldAodAnimationController foldAodAnimationController = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.unfold.FoldAodAnimationController$listenForDozing$2.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    FoldAodAnimationController.this.isDozing = ((Boolean) obj2).booleanValue();
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (stateFlow.collect(flowCollector, this) == coroutineSingletons) {
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
