package com.android.systemui.blur.ui.viewmodel;

import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

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
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
        float fFloatValue = ((Number) obj3).floatValue();
        boolean zBooleanValue3 = ((Boolean) obj4).booleanValue();
        SecCapturedBlurContainerViewModel$shouldBeGone$1 secCapturedBlurContainerViewModel$shouldBeGone$1 = new SecCapturedBlurContainerViewModel$shouldBeGone$1((Continuation) obj5);
        secCapturedBlurContainerViewModel$shouldBeGone$1.Z$0 = zBooleanValue;
        secCapturedBlurContainerViewModel$shouldBeGone$1.Z$1 = zBooleanValue2;
        secCapturedBlurContainerViewModel$shouldBeGone$1.F$0 = fFloatValue;
        secCapturedBlurContainerViewModel$shouldBeGone$1.Z$2 = zBooleanValue3;
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
