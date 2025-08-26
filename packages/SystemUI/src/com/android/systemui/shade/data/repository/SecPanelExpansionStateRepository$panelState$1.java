package com.android.systemui.shade.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* loaded from: classes3.dex */
final class SecPanelExpansionStateRepository$panelState$1 extends SuspendLambda implements Function5 {
    /* synthetic */ float F$0;
    /* synthetic */ float F$1;
    /* synthetic */ int I$0;
    /* synthetic */ boolean Z$0;
    int label;

    public SecPanelExpansionStateRepository$panelState$1(Continuation continuation) {
        super(5, continuation);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        float fFloatValue = ((Number) obj).floatValue();
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        float fFloatValue2 = ((Number) obj3).floatValue();
        int iIntValue = ((Number) obj4).intValue();
        SecPanelExpansionStateRepository$panelState$1 secPanelExpansionStateRepository$panelState$1 = new SecPanelExpansionStateRepository$panelState$1((Continuation) obj5);
        secPanelExpansionStateRepository$panelState$1.F$0 = fFloatValue;
        secPanelExpansionStateRepository$panelState$1.Z$0 = zBooleanValue;
        secPanelExpansionStateRepository$panelState$1.F$1 = fFloatValue2;
        secPanelExpansionStateRepository$panelState$1.I$0 = iIntValue;
        return secPanelExpansionStateRepository$panelState$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0029  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        float f = this.F$0;
        boolean z = this.Z$0;
        float f2 = this.F$1;
        int i = this.I$0;
        int i2 = 0;
        if (!z) {
            if (i != 1) {
                if (i != 2) {
                    if (f2 != 0.0f) {
                        i2 = f2 == 1.0f ? 2 : 1;
                    }
                } else if (f2 < 1.0f) {
                }
            } else if (f != 0.0f) {
                if (f == 1.0f) {
                }
            }
        }
        return new Integer(i2);
    }
}
