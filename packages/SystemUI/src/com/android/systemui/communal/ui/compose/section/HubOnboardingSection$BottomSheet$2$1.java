package com.android.systemui.communal.ui.compose.section;

import androidx.compose.runtime.MutableState;
import com.android.systemui.communal.ui.compose.section.HubOnboardingSection;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* loaded from: classes2.dex */
final class HubOnboardingSection$BottomSheet$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableState<Boolean> $show$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HubOnboardingSection$BottomSheet$2$1(MutableState<Boolean> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$show$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new HubOnboardingSection$BottomSheet$2$1(this.$show$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HubOnboardingSection$BottomSheet$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            HubOnboardingSection.Companion.getClass();
            long j = HubOnboardingSection.SHOW_BOTTOMSHEET_DELAY_MS;
            this.label = 1;
            if (DelayKt.m3469delayVtjQ1oo(j, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        MutableState<Boolean> mutableState = this.$show$delegate;
        HubOnboardingSection.Companion companion = HubOnboardingSection.Companion;
        mutableState.setValue(Boolean.TRUE);
        return Unit.INSTANCE;
    }
}
