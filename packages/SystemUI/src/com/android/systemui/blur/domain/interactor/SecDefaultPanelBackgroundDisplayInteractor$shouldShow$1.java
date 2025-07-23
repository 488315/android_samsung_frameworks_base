package com.android.systemui.blur.domain.interactor;

import android.util.Log;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.statusbar.StatusBarState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SecDefaultPanelBackgroundDisplayInteractor$shouldShow$1 extends SuspendLambda implements Function6 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    int label;
    final /* synthetic */ SecDefaultPanelBackgroundDisplayInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecDefaultPanelBackgroundDisplayInteractor$shouldShow$1(SecDefaultPanelBackgroundDisplayInteractor secDefaultPanelBackgroundDisplayInteractor, Continuation continuation) {
        super(6, continuation);
        this.this$0 = secDefaultPanelBackgroundDisplayInteractor;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        int intValue = ((Number) obj3).intValue();
        boolean booleanValue3 = ((Boolean) obj5).booleanValue();
        SecDefaultPanelBackgroundDisplayInteractor$shouldShow$1 secDefaultPanelBackgroundDisplayInteractor$shouldShow$1 = new SecDefaultPanelBackgroundDisplayInteractor$shouldShow$1(this.this$0, (Continuation) obj6);
        secDefaultPanelBackgroundDisplayInteractor$shouldShow$1.Z$0 = booleanValue;
        secDefaultPanelBackgroundDisplayInteractor$shouldShow$1.Z$1 = booleanValue2;
        secDefaultPanelBackgroundDisplayInteractor$shouldShow$1.I$0 = intValue;
        secDefaultPanelBackgroundDisplayInteractor$shouldShow$1.L$0 = (KeyguardState) obj4;
        secDefaultPanelBackgroundDisplayInteractor$shouldShow$1.Z$2 = booleanValue3;
        return secDefaultPanelBackgroundDisplayInteractor$shouldShow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z2 = this.Z$0;
        boolean z3 = this.Z$1;
        int i = this.I$0;
        KeyguardState keyguardState = (KeyguardState) this.L$0;
        boolean z4 = this.Z$2;
        String statusBarState = StatusBarState.toString(i);
        String name = keyguardState.name();
        StringBuilder m = EmergencyButtonController$$ExternalSyntheticOutline0.m("blurReduced = ", " , minimalBatteryUse = ", " , statusBarState = ", z2, z3);
        m.append(statusBarState);
        m.append(", keyguardState = ");
        m.append(name);
        Log.d("SecDefaultPanelBackgroundDisplayInteractor", m.toString());
        if (!z2) {
            SecDefaultPanelBackgroundDisplayInteractor secDefaultPanelBackgroundDisplayInteractor = this.this$0;
            int i2 = SecDefaultPanelBackgroundDisplayInteractor.$r8$clinit;
            secDefaultPanelBackgroundDisplayInteractor.getClass();
            if ((i == 0 || keyguardState == KeyguardState.OCCLUDED || !z3) && !z4) {
                z = false;
                return Boolean.valueOf(z);
            }
        }
        z = true;
        return Boolean.valueOf(z);
    }
}
