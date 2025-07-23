package com.android.systemui.statusbar.pipeline.shared.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.scene.shared.model.Scenes;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class HomeStatusBarViewModelImpl$isHomeStatusBarAllowedByScene$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public HomeStatusBarViewModelImpl$isHomeStatusBarAllowedByScene$1(Continuation continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        HomeStatusBarViewModelImpl$isHomeStatusBarAllowedByScene$1 homeStatusBarViewModelImpl$isHomeStatusBarAllowedByScene$1 = new HomeStatusBarViewModelImpl$isHomeStatusBarAllowedByScene$1((Continuation) obj4);
        homeStatusBarViewModelImpl$isHomeStatusBarAllowedByScene$1.L$0 = (SceneKey) obj;
        homeStatusBarViewModelImpl$isHomeStatusBarAllowedByScene$1.Z$0 = booleanValue;
        homeStatusBarViewModelImpl$isHomeStatusBarAllowedByScene$1.Z$1 = booleanValue2;
        return homeStatusBarViewModelImpl$isHomeStatusBarAllowedByScene$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf((Intrinsics.areEqual((SceneKey) this.L$0, Scenes.Gone) && !this.Z$0) || this.Z$1);
    }
}
