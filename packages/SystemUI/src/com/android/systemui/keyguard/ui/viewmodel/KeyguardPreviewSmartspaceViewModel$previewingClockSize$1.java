package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.shared.model.ClockSizeSetting;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardPreviewSmartspaceViewModel$previewingClockSize$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public KeyguardPreviewSmartspaceViewModel$previewingClockSize$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardPreviewSmartspaceViewModel$previewingClockSize$1 keyguardPreviewSmartspaceViewModel$previewingClockSize$1 = new KeyguardPreviewSmartspaceViewModel$previewingClockSize$1((Continuation) obj3);
        keyguardPreviewSmartspaceViewModel$previewingClockSize$1.L$0 = (ClockSizeSetting) obj;
        keyguardPreviewSmartspaceViewModel$previewingClockSize$1.L$1 = (ClockSizeSetting) obj2;
        return keyguardPreviewSmartspaceViewModel$previewingClockSize$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ClockSizeSetting clockSizeSetting = (ClockSizeSetting) this.L$0;
        return clockSizeSetting == null ? (ClockSizeSetting) this.L$1 : clockSizeSetting;
    }
}
