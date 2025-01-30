package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.DozeStateModel;
import com.android.systemui.keyguard.shared.model.DozeTransitionModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$isAbleToDream$1", m277f = "KeyguardInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class KeyguardInteractor$isAbleToDream$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public KeyguardInteractor$isAbleToDream$1(Continuation<? super KeyguardInteractor$isAbleToDream$1> continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        KeyguardInteractor$isAbleToDream$1 keyguardInteractor$isAbleToDream$1 = new KeyguardInteractor$isAbleToDream$1((Continuation) obj3);
        keyguardInteractor$isAbleToDream$1.Z$0 = booleanValue;
        keyguardInteractor$isAbleToDream$1.L$0 = (DozeTransitionModel) obj2;
        return keyguardInteractor$isAbleToDream$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        DozeTransitionModel dozeTransitionModel = (DozeTransitionModel) this.L$0;
        boolean z2 = false;
        if (z) {
            DozeStateModel.Companion companion = DozeStateModel.Companion;
            DozeStateModel dozeStateModel = dozeTransitionModel.f300to;
            companion.getClass();
            if (dozeStateModel == DozeStateModel.UNINITIALIZED || dozeStateModel == DozeStateModel.FINISH) {
                z2 = true;
            }
        }
        return Boolean.valueOf(z2);
    }
}
