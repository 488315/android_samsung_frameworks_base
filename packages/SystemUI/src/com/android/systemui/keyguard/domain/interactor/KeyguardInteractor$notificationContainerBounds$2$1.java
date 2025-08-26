package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.common.shared.model.NotificationContainerBounds;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
final class KeyguardInteractor$notificationContainerBounds$2$1 extends SuspendLambda implements Function6 {
    /* synthetic */ int I$0;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    public KeyguardInteractor$notificationContainerBounds$2$1(Continuation continuation) {
        super(6, continuation);
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean zBooleanValue = ((Boolean) obj3).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj4).booleanValue();
        int iIntValue = ((Number) obj5).intValue();
        KeyguardInteractor$notificationContainerBounds$2$1 keyguardInteractor$notificationContainerBounds$2$1 = new KeyguardInteractor$notificationContainerBounds$2$1((Continuation) obj6);
        keyguardInteractor$notificationContainerBounds$2$1.L$0 = (FlowCollector) obj;
        keyguardInteractor$notificationContainerBounds$2$1.L$1 = (NotificationContainerBounds) obj2;
        keyguardInteractor$notificationContainerBounds$2$1.Z$0 = zBooleanValue;
        keyguardInteractor$notificationContainerBounds$2$1.Z$1 = zBooleanValue2;
        keyguardInteractor$notificationContainerBounds$2$1.I$0 = iIntValue;
        return keyguardInteractor$notificationContainerBounds$2$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            NotificationContainerBounds notificationContainerBounds = (NotificationContainerBounds) this.L$1;
            boolean z = this.Z$0;
            boolean z2 = this.Z$1;
            int i2 = this.I$0;
            if (z) {
                return Unit.INSTANCE;
            }
            if (z2) {
                notificationContainerBounds = new NotificationContainerBounds(notificationContainerBounds.top, notificationContainerBounds.bottom - i2, notificationContainerBounds.isAnimated);
            }
            this.L$0 = null;
            this.label = 1;
            if (flowCollector.emit(notificationContainerBounds, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
