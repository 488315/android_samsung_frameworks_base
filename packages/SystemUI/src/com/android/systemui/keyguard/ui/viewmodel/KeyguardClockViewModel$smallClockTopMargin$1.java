package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.shared.model.ShadeMode;
import com.android.systemui.statusbar.ui.SystemBarUtilsProxyImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class KeyguardClockViewModel$smallClockTopMargin$1 extends SuspendLambda implements Function3 {
    int label;
    final /* synthetic */ KeyguardClockViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardClockViewModel$smallClockTopMargin$1(KeyguardClockViewModel keyguardClockViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = keyguardClockViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new KeyguardClockViewModel$smallClockTopMargin$1(this.this$0, (Continuation) obj3).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        int i;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        KeyguardClockViewModel keyguardClockViewModel = this.this$0;
        int statusBarHeaderHeightKeyguard = ((SystemBarUtilsProxyImpl) keyguardClockViewModel.systemBarUtils).getStatusBarHeaderHeightKeyguard();
        if (Intrinsics.areEqual(((ShadeInteractorImpl) keyguardClockViewModel.shadeInteractor).baseShadeInteractor.getShadeMode().getValue(), ShadeMode.Split.INSTANCE)) {
            i = keyguardClockViewModel.resources.getDimensionPixelSize(R.dimen.keyguard_split_shade_top_margin);
            Flags.composeLockscreen();
        } else {
            int dimensionPixelSize = keyguardClockViewModel.resources.getDimensionPixelSize(R.dimen.keyguard_clock_top_margin);
            Flags.composeLockscreen();
            i = dimensionPixelSize + statusBarHeaderHeightKeyguard;
        }
        return new Integer(i);
    }
}
