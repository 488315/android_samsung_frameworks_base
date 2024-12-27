package com.android.systemui.deviceentry.domain.interactor;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus;
import com.android.systemui.scene.shared.model.Scenes;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

final class DeviceEntryInteractor$isDeviceEntered$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DeviceEntryInteractor this$0;

    /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$isDeviceEntered$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((DeviceUnlockStatus) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boolean.valueOf(((DeviceUnlockStatus) this.L$0).isUnlocked);
        }
    }

    public DeviceEntryInteractor$isDeviceEntered$2(DeviceEntryInteractor deviceEntryInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = deviceEntryInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceEntryInteractor$isDeviceEntered$2 deviceEntryInteractor$isDeviceEntered$2 = new DeviceEntryInteractor$isDeviceEntered$2(this.this$0, continuation);
        deviceEntryInteractor$isDeviceEntered$2.L$0 = obj;
        return deviceEntryInteractor$isDeviceEntered$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceEntryInteractor$isDeviceEntered$2) create((SceneKey) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        boolean z = true;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (Intrinsics.areEqual((SceneKey) this.L$0, Scenes.Gone)) {
                ReadonlyStateFlow readonlyStateFlow = this.this$0.deviceUnlockedInteractor.deviceUnlockStatus;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(null);
                this.label = 1;
                if (FlowKt.first(readonlyStateFlow, anonymousClass1, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                z = false;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Boolean.valueOf(z);
    }
}
