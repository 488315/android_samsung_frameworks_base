package com.android.systemui.blur.domain.interactor;

import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SecPanelBackgroundCommonInteractor$maxAlpha$1 extends SuspendLambda implements Function4 {
    final /* synthetic */ SecBlurCustomColorInteractor $secBlurCustomColorInteractor;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecPanelBackgroundCommonInteractor$maxAlpha$1(SecBlurCustomColorInteractor secBlurCustomColorInteractor, Continuation continuation) {
        super(4, continuation);
        this.$secBlurCustomColorInteractor = secBlurCustomColorInteractor;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        SecPanelBackgroundCommonInteractor$maxAlpha$1 secPanelBackgroundCommonInteractor$maxAlpha$1 = new SecPanelBackgroundCommonInteractor$maxAlpha$1(this.$secBlurCustomColorInteractor, (Continuation) obj4);
        secPanelBackgroundCommonInteractor$maxAlpha$1.Z$0 = booleanValue;
        secPanelBackgroundCommonInteractor$maxAlpha$1.Z$1 = booleanValue2;
        return secPanelBackgroundCommonInteractor$maxAlpha$1.invokeSuspend(Unit.INSTANCE);
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
        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("blurReduced = ", ", hasCustomColorApplied = ", "SecPanelBackgroundDisplayInteractor", z, z2);
        return new Float(z ? 0.9f : z2 ? ((this.$secBlurCustomColorInteractor.context.getColor(SecBlurCustomColorInteractor.backgroundColorId) >> 24) & 255) / 255.0f : 0.3f);
    }
}
