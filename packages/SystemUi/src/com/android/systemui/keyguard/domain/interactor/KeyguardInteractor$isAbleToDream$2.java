package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.WakefulnessModel;
import com.android.systemui.keyguard.shared.model.WakefulnessState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$isAbleToDream$2", m277f = "KeyguardInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class KeyguardInteractor$isAbleToDream$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public KeyguardInteractor$isAbleToDream$2(Continuation<? super KeyguardInteractor$isAbleToDream$2> continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        KeyguardInteractor$isAbleToDream$2 keyguardInteractor$isAbleToDream$2 = new KeyguardInteractor$isAbleToDream$2((Continuation) obj3);
        keyguardInteractor$isAbleToDream$2.Z$0 = booleanValue;
        keyguardInteractor$isAbleToDream$2.L$0 = (WakefulnessModel) obj2;
        return keyguardInteractor$isAbleToDream$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        WakefulnessModel wakefulnessModel = (WakefulnessModel) this.L$0;
        boolean z2 = false;
        if (z) {
            wakefulnessModel.getClass();
            WakefulnessState wakefulnessState = WakefulnessState.STARTING_TO_WAKE;
            WakefulnessState wakefulnessState2 = wakefulnessModel.state;
            if ((wakefulnessState2 == wakefulnessState) || wakefulnessState2 == WakefulnessState.AWAKE) {
                z2 = true;
            }
        }
        return Boolean.valueOf(z2);
    }
}
