package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$2", m277f = "KeyguardQuickAffordanceInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class KeyguardQuickAffordanceInteractor$quickAffordance$2 extends SuspendLambda implements Function6 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    /* synthetic */ boolean Z$3;
    int label;

    public KeyguardQuickAffordanceInteractor$quickAffordance$2(Continuation<? super KeyguardQuickAffordanceInteractor$quickAffordance$2> continuation) {
        super(6, continuation);
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        boolean booleanValue3 = ((Boolean) obj4).booleanValue();
        boolean booleanValue4 = ((Boolean) obj5).booleanValue();
        KeyguardQuickAffordanceInteractor$quickAffordance$2 keyguardQuickAffordanceInteractor$quickAffordance$2 = new KeyguardQuickAffordanceInteractor$quickAffordance$2((Continuation) obj6);
        keyguardQuickAffordanceInteractor$quickAffordance$2.L$0 = (KeyguardQuickAffordanceModel) obj;
        keyguardQuickAffordanceInteractor$quickAffordance$2.Z$0 = booleanValue;
        keyguardQuickAffordanceInteractor$quickAffordance$2.Z$1 = booleanValue2;
        keyguardQuickAffordanceInteractor$quickAffordance$2.Z$2 = booleanValue3;
        keyguardQuickAffordanceInteractor$quickAffordance$2.Z$3 = booleanValue4;
        return keyguardQuickAffordanceInteractor$quickAffordance$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return (this.Z$0 || !this.Z$1 || this.Z$2 || this.Z$3) ? KeyguardQuickAffordanceModel.Hidden.INSTANCE : (KeyguardQuickAffordanceModel) this.L$0;
    }
}
