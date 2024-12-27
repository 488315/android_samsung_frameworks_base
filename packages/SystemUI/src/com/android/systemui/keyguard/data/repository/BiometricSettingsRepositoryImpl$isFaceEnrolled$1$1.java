package com.android.systemui.keyguard.data.repository;

import com.android.systemui.biometrics.AuthController;
import com.android.systemui.common.coroutine.ChannelExt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class BiometricSettingsRepositoryImpl$isFaceEnrolled$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ AuthController $authController;
    final /* synthetic */ int $selectedUserId;
    private /* synthetic */ Object L$0;
    int label;

    public BiometricSettingsRepositoryImpl$isFaceEnrolled$1$1(AuthController authController, int i, Continuation continuation) {
        super(2, continuation);
        this.$authController = authController;
        this.$selectedUserId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BiometricSettingsRepositoryImpl$isFaceEnrolled$1$1 biometricSettingsRepositoryImpl$isFaceEnrolled$1$1 = new BiometricSettingsRepositoryImpl$isFaceEnrolled$1$1(this.$authController, this.$selectedUserId, continuation);
        biometricSettingsRepositoryImpl$isFaceEnrolled$1$1.L$0 = obj;
        return biometricSettingsRepositoryImpl$isFaceEnrolled$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BiometricSettingsRepositoryImpl$isFaceEnrolled$1$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final AuthController authController = this.$authController;
            final int i2 = this.$selectedUserId;
            final ?? r1 = new AuthController.Callback() { // from class: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFaceEnrolled$1$1$callback$1
                @Override // com.android.systemui.biometrics.AuthController.Callback
                public final void onEnrollmentsChanged(BiometricType biometricType, int i3, boolean z) {
                    if (biometricType == BiometricType.FACE) {
                        ChannelExt channelExt = ChannelExt.INSTANCE;
                        AuthController authController2 = authController;
                        Boolean valueOf = Boolean.valueOf(authController2.mFaceProps == null ? false : authController2.mFaceEnrolledForUser.get(i2));
                        channelExt.getClass();
                        ChannelExt.trySendWithFailureLogging(ProducerScope.this, valueOf, "BiometricsRepositoryImpl", "Face enrollment changed");
                    }
                }
            };
            this.$authController.addCallback(r1);
            ChannelExt channelExt = ChannelExt.INSTANCE;
            AuthController authController2 = this.$authController;
            Boolean valueOf = Boolean.valueOf(authController2.mFaceProps == null ? false : authController2.mFaceEnrolledForUser.get(this.$selectedUserId));
            channelExt.getClass();
            ChannelExt.trySendWithFailureLogging(producerScope, valueOf, "BiometricsRepositoryImpl", "Initial value of face auth enrollment");
            final AuthController authController3 = this.$authController;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFaceEnrolled$1$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    AuthController.this.removeCallback(r1);
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
