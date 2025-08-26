package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.deviceentry.data.repository.DeviceEntryRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
final class DeviceEntryInteractor$isLockscreenEnabled$2$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ DeviceEntryInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryInteractor$isLockscreenEnabled$2$1(DeviceEntryInteractor deviceEntryInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceEntryInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DeviceEntryInteractor$isLockscreenEnabled$2$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryInteractor$isLockscreenEnabled$2$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DeviceEntryInteractor deviceEntryInteractor = this.this$0;
            this.label = 1;
            Object objIsLockscreenEnabled = ((DeviceEntryRepositoryImpl) deviceEntryInteractor.repository).isLockscreenEnabled(this);
            if (objIsLockscreenEnabled != coroutineSingletons) {
                objIsLockscreenEnabled = Unit.INSTANCE;
            }
            if (objIsLockscreenEnabled == coroutineSingletons) {
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
