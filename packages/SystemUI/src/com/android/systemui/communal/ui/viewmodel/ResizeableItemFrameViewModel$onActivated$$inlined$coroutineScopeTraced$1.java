package com.android.systemui.communal.ui.viewmodel;

import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ResizeableItemFrameViewModel$onActivated$$inlined$coroutineScopeTraced$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $traceName$inlined;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ResizeableItemFrameViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ResizeableItemFrameViewModel$onActivated$$inlined$coroutineScopeTraced$1(Continuation continuation, String str, ResizeableItemFrameViewModel resizeableItemFrameViewModel) {
        super(2, continuation);
        this.$traceName$inlined = str;
        this.this$0 = resizeableItemFrameViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ResizeableItemFrameViewModel$onActivated$$inlined$coroutineScopeTraced$1 resizeableItemFrameViewModel$onActivated$$inlined$coroutineScopeTraced$1 = new ResizeableItemFrameViewModel$onActivated$$inlined$coroutineScopeTraced$1(continuation, this.$traceName$inlined, this.this$0);
        resizeableItemFrameViewModel$onActivated$$inlined$coroutineScopeTraced$1.L$0 = obj;
        return resizeableItemFrameViewModel$onActivated$$inlined$coroutineScopeTraced$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ResizeableItemFrameViewModel$onActivated$$inlined$coroutineScopeTraced$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            ResizeableItemFrameViewModel resizeableItemFrameViewModel = this.this$0;
            FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(resizeableItemFrameViewModel.gridLayoutInfo, new ResizeableItemFrameViewModel$onActivated$2$1(resizeableItemFrameViewModel, null)), coroutineScope);
            this.label = 1;
            if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
