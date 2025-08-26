package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.DozeStateModel;
import com.android.systemui.keyguard.shared.model.DozeTransitionModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes2.dex */
public final class KeyguardInteractor$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ KeyguardInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardInteractor$special$$inlined$flatMapLatest$2(Continuation continuation, KeyguardInteractor keyguardInteractor) {
        super(3, continuation);
        this.this$0 = keyguardInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardInteractor$special$$inlined$flatMapLatest$2 keyguardInteractor$special$$inlined$flatMapLatest$2 = new KeyguardInteractor$special$$inlined$flatMapLatest$2((Continuation) obj3, this.this$0);
        keyguardInteractor$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        keyguardInteractor$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return keyguardInteractor$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x004e, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(500, r5) == r0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0064, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.emitAll(r1, r3, r5) != r0) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0066, code lost:
    
        return r0;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector = (FlowCollector) this.L$0;
            DozeTransitionModel dozeTransitionModel = (DozeTransitionModel) this.L$1;
            DozeStateModel.Companion companion = DozeStateModel.Companion;
            DozeStateModel dozeStateModel = dozeTransitionModel.to;
            companion.getClass();
            if (dozeStateModel == DozeStateModel.UNINITIALIZED || dozeStateModel == DozeStateModel.FINISH) {
                this.L$0 = flowCollector;
                this.label = 1;
            } else {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
                this.L$0 = null;
                this.label = 2;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = FlowKt.debounce(this.this$0.isDreaming, 50L);
        this.L$0 = null;
        this.label = 2;
    }
}
