package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1", m277f = "KeyguardBottomAreaViewModel.kt", m278l = {190}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class KeyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ KeyguardBottomAreaViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel) {
        super(3, continuation);
        this.this$0 = keyguardBottomAreaViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1 keyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1 = new KeyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        keyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        keyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return keyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = ((KeyguardBottomAreaViewModel.PreviewMode) this.L$1).isInPreviewMode ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new Float(1.0f)) : FlowKt.distinctUntilChanged(this.this$0.bottomAreaInteractor.alpha);
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons) {
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
