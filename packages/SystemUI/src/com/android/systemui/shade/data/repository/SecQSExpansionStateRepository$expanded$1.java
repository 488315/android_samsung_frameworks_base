package com.android.systemui.shade.data.repository;

import android.util.Log;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class SecQSExpansionStateRepository$expanded$1 extends SuspendLambda implements Function5 {
    /* synthetic */ float F$0;
    /* synthetic */ float F$1;
    /* synthetic */ int I$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ SecQSExpansionStateRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecQSExpansionStateRepository$expanded$1(SecQSExpansionStateRepository secQSExpansionStateRepository, Continuation continuation) {
        super(5, continuation);
        this.this$0 = secQSExpansionStateRepository;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        int intValue = ((Number) obj2).intValue();
        float floatValue = ((Number) obj3).floatValue();
        float floatValue2 = ((Number) obj4).floatValue();
        SecQSExpansionStateRepository$expanded$1 secQSExpansionStateRepository$expanded$1 = new SecQSExpansionStateRepository$expanded$1(this.this$0, (Continuation) obj5);
        secQSExpansionStateRepository$expanded$1.Z$0 = booleanValue;
        secQSExpansionStateRepository$expanded$1.I$0 = intValue;
        secQSExpansionStateRepository$expanded$1.F$0 = floatValue;
        secQSExpansionStateRepository$expanded$1.F$1 = floatValue2;
        return secQSExpansionStateRepository$expanded$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        int i = this.I$0;
        float f = this.F$0;
        float f2 = this.F$1;
        StringBuilder m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("pte: ", i, " | pts: ", z, " | lsse: ");
        m.append(f);
        m.append(" | qe: ");
        m.append(f2);
        Log.d("SecQSExpansionStateRepository", m.toString());
        boolean z2 = false;
        if (!z ? f2 > 0.0f : !(i != 0 || (f <= 0.0f && f2 <= 0.0f))) {
            z2 = true;
        }
        Boolean valueOf = Boolean.valueOf(z2);
        SecQSExpansionStateRepository secQSExpansionStateRepository = this.this$0;
        boolean z3 = secQSExpansionStateRepository.debugExpanded;
        if (z3 == z2) {
            valueOf = null;
        }
        if (valueOf != null) {
            boolean booleanValue = valueOf.booleanValue();
            Log.d("SecQSExpansionStateRepository", secQSExpansionStateRepository.debugExpanded + " => " + booleanValue + ": pte: " + z + " | pts: " + i + " | lsse: " + f + " | qe: " + f2);
            secQSExpansionStateRepository.debugExpanded = booleanValue;
            z3 = valueOf.booleanValue();
        }
        return Boolean.valueOf(z3);
    }
}
