package com.android.systemui.volume.dialog;

import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialog$onCreate$1$invokeSuspend$$inlined$coroutineScopeTraced$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $traceName$inlined;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ VolumeDialog this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VolumeDialog$onCreate$1$invokeSuspend$$inlined$coroutineScopeTraced$1(Continuation continuation, String str, VolumeDialog volumeDialog) {
        super(2, continuation);
        this.$traceName$inlined = str;
        this.this$0 = volumeDialog;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        VolumeDialog$onCreate$1$invokeSuspend$$inlined$coroutineScopeTraced$1 volumeDialog$onCreate$1$invokeSuspend$$inlined$coroutineScopeTraced$1 = new VolumeDialog$onCreate$1$invokeSuspend$$inlined$coroutineScopeTraced$1(continuation, this.$traceName$inlined, this.this$0);
        volumeDialog$onCreate$1$invokeSuspend$$inlined$coroutineScopeTraced$1.L$0 = obj;
        return volumeDialog$onCreate$1$invokeSuspend$$inlined$coroutineScopeTraced$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VolumeDialog$onCreate$1$invokeSuspend$$inlined$coroutineScopeTraced$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            ((DaggerReferenceGlobalRootComponent.VolumeDialogComponentImpl) this.this$0.componentFactory.create(coroutineScope)).volumeDialogViewBinder().bind(coroutineScope, this.this$0);
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
