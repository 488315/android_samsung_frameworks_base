package com.android.systemui.scene.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SceneInteractor$isVisible$1 extends SuspendLambda implements Function4 {
    /* synthetic */ int I$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ SceneInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SceneInteractor$isVisible$1(SceneInteractor sceneInteractor, Continuation continuation) {
        super(4, continuation);
        this.this$0 = sceneInteractor;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        int intValue = ((Number) obj3).intValue();
        SceneInteractor$isVisible$1 sceneInteractor$isVisible$1 = new SceneInteractor$isVisible$1(this.this$0, (Continuation) obj4);
        sceneInteractor$isVisible$1.Z$0 = booleanValue;
        sceneInteractor$isVisible$1.Z$1 = booleanValue2;
        sceneInteractor$isVisible$1.I$0 = intValue;
        return sceneInteractor$isVisible$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        int i = this.I$0;
        this.this$0.getClass();
        return Boolean.valueOf(z || z2 || i > 0);
    }
}
