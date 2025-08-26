package com.android.systemui.blur.domain.interactor;

import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.QpRune;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* loaded from: classes.dex */
final class SecPanelBackgroundCommonInteractor$maxAlpha$1 extends SuspendLambda implements Function4 {
    final /* synthetic */ SecBlurCustomColorInteractor $secBlurCustomColorInteractor;
    final /* synthetic */ SecQsUiDisplayModeInteractor $secQsUiDisplayModeInteractor;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecPanelBackgroundCommonInteractor$maxAlpha$1(SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor, SecBlurCustomColorInteractor secBlurCustomColorInteractor, Continuation continuation) {
        super(4, continuation);
        this.$secQsUiDisplayModeInteractor = secQsUiDisplayModeInteractor;
        this.$secBlurCustomColorInteractor = secBlurCustomColorInteractor;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
        SecPanelBackgroundCommonInteractor$maxAlpha$1 secPanelBackgroundCommonInteractor$maxAlpha$1 = new SecPanelBackgroundCommonInteractor$maxAlpha$1(this.$secQsUiDisplayModeInteractor, this.$secBlurCustomColorInteractor, (Continuation) obj4);
        secPanelBackgroundCommonInteractor$maxAlpha$1.Z$0 = zBooleanValue;
        secPanelBackgroundCommonInteractor$maxAlpha$1.Z$1 = zBooleanValue2;
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
        return new Float((QpRune.QUICK_PANEL_BLUR_MASSIVE && this.$secQsUiDisplayModeInteractor.isTablet()) ? 0.95f : z ? 0.9f : z2 ? ((this.$secBlurCustomColorInteractor.context.getColor(SecBlurCustomColorInteractor.backgroundColorId) >> 24) & 255) / 255.0f : 0.3f);
    }
}
