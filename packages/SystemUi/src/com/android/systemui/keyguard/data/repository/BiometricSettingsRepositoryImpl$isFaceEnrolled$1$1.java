package com.android.systemui.keyguard.data.repository;

import com.android.systemui.biometrics.AuthController;
import com.android.systemui.common.coroutine.ChannelExt;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFaceEnrolled$1$1", m277f = "BiometricSettingsRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getFavoriteAppsMaxCount}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class BiometricSettingsRepositoryImpl$isFaceEnrolled$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ AuthController $authController;
    final /* synthetic */ int $selectedUserId;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricSettingsRepositoryImpl$isFaceEnrolled$1$1(AuthController authController, int i, Continuation<? super BiometricSettingsRepositoryImpl$isFaceEnrolled$1$1> continuation) {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.biometrics.AuthController$Callback, com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFaceEnrolled$1$1$callback$1] */
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
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
