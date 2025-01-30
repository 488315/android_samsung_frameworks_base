package com.android.systemui.dreams;

import com.android.systemui.dreams.DreamOverlayAnimationsController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1$1$invokeSuspend$$inlined$flatMapLatest$1", m277f = "DreamOverlayAnimationsController.kt", m278l = {190}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1$1$invokeSuspend$$inlined$flatMapLatest$1 */
/* loaded from: classes.dex */
public final class C1265xc4797fdb extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ DreamOverlayAnimationsController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1265xc4797fdb(Continuation continuation, DreamOverlayAnimationsController dreamOverlayAnimationsController) {
        super(3, continuation);
        this.this$0 = dreamOverlayAnimationsController;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        C1265xc4797fdb c1265xc4797fdb = new C1265xc4797fdb((Continuation) obj3, this.this$0);
        c1265xc4797fdb.L$0 = (FlowCollector) obj;
        c1265xc4797fdb.L$1 = obj2;
        return c1265xc4797fdb.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 dreamOverlayTranslationY = this.this$0.transitionViewModel.dreamOverlayTranslationY(((DreamOverlayAnimationsController.ConfigurationBasedDimensions) this.L$1).translationYPx);
            this.label = 1;
            if (FlowKt.emitAll(this, dreamOverlayTranslationY, flowCollector) == coroutineSingletons) {
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
