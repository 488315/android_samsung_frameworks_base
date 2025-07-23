package com.android.systemui.blur.ui.viewmodel;

import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SecCapturedBlurContainerViewModel$shouldBeGone$1 extends SuspendLambda implements Function5 {
    /* synthetic */ float F$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;

    public SecCapturedBlurContainerViewModel$shouldBeGone$1(Continuation continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        float floatValue = ((Number) obj3).floatValue();
        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
        SecCapturedBlurContainerViewModel$shouldBeGone$1 secCapturedBlurContainerViewModel$shouldBeGone$1 = new SecCapturedBlurContainerViewModel$shouldBeGone$1((Continuation) obj5);
        secCapturedBlurContainerViewModel$shouldBeGone$1.Z$0 = booleanValue;
        secCapturedBlurContainerViewModel$shouldBeGone$1.Z$1 = booleanValue2;
        secCapturedBlurContainerViewModel$shouldBeGone$1.F$0 = floatValue;
        secCapturedBlurContainerViewModel$shouldBeGone$1.Z$2 = booleanValue3;
        return secCapturedBlurContainerViewModel$shouldBeGone$1.invokeSuspend(Unit.INSTANCE);
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
        float f = this.F$0;
        boolean z3 = this.Z$2;
        SeslColorSpectrumView$$ExternalSyntheticOutline0.m(f, "SecCapturedBlurContainerViewModel", EmergencyButtonController$$ExternalSyntheticOutline0.m("bouncerShowing = ", " , fullScreenBlurShowing = ", " , maxAlpha = ", z, z2));
        return Boolean.valueOf(!(z || z2 || f != 1.0f) || z3);
    }
}
