package com.android.systemui.keyguard.data.repository;

import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback;
import com.android.systemui.common.coroutine.ChannelExt;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Pair;
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
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFaceEnabledByBiometricsManager$1", m277f = "BiometricSettingsRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_setForceSingleView}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFaceEnabledByBiometricsManager$1 */
/* loaded from: classes.dex */
public final class C1591x823f4eed extends SuspendLambda implements Function2 {
    final /* synthetic */ BiometricManager $biometricManager;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1591x823f4eed(BiometricManager biometricManager, Continuation<? super C1591x823f4eed> continuation) {
        super(2, continuation);
        this.$biometricManager = biometricManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        C1591x823f4eed c1591x823f4eed = new C1591x823f4eed(this.$biometricManager, continuation);
        c1591x823f4eed.L$0 = obj;
        return c1591x823f4eed;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((C1591x823f4eed) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback = new IBiometricEnabledOnKeyguardCallback.Stub() { // from class: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFaceEnabledByBiometricsManager$1$callback$1
                public final void onChanged(boolean z, int i2) {
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    ProducerScope producerScope2 = ProducerScope.this;
                    Pair pair = new Pair(Integer.valueOf(i2), Boolean.valueOf(z));
                    channelExt.getClass();
                    ChannelExt.trySendWithFailureLogging(producerScope2, pair, "BiometricsRepositoryImpl", "biometricsEnabled state changed");
                }
            };
            BiometricManager biometricManager = this.$biometricManager;
            if (biometricManager != null) {
                biometricManager.registerEnabledOnKeyguardCallback(iBiometricEnabledOnKeyguardCallback);
            }
            AnonymousClass1 anonymousClass1 = new Function0() { // from class: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFaceEnabledByBiometricsManager$1.1
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, anonymousClass1, this) == coroutineSingletons) {
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
