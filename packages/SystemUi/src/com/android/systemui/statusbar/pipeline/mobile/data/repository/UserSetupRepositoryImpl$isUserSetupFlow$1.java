package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.UserSetupRepositoryImpl$isUserSetupFlow$1", m277f = "UserSetupRepository.kt", m278l = {67}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class UserSetupRepositoryImpl$isUserSetupFlow$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ UserSetupRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserSetupRepositoryImpl$isUserSetupFlow$1(UserSetupRepositoryImpl userSetupRepositoryImpl, Continuation<? super UserSetupRepositoryImpl$isUserSetupFlow$1> continuation) {
        super(2, continuation);
        this.this$0 = userSetupRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserSetupRepositoryImpl$isUserSetupFlow$1 userSetupRepositoryImpl$isUserSetupFlow$1 = new UserSetupRepositoryImpl$isUserSetupFlow$1(this.this$0, continuation);
        userSetupRepositoryImpl$isUserSetupFlow$1.L$0 = obj;
        return userSetupRepositoryImpl$isUserSetupFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserSetupRepositoryImpl$isUserSetupFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.pipeline.mobile.data.repository.UserSetupRepositoryImpl$isUserSetupFlow$1$callback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.UserSetupRepositoryImpl$isUserSetupFlow$1$callback$1
                @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
                public final void onUserSetupChanged() {
                    ((ChannelCoroutine) ProducerScope.this).mo2872trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            ((DeviceProvisionedControllerImpl) this.this$0.deviceProvisionedController).addCallback(r1);
            final UserSetupRepositoryImpl userSetupRepositoryImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.repository.UserSetupRepositoryImpl$isUserSetupFlow$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((DeviceProvisionedControllerImpl) UserSetupRepositoryImpl.this.deviceProvisionedController).removeCallback(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
