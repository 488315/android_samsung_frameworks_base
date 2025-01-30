package com.android.systemui.keyguard.data.repository;

import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFaceEnabledByBiometricsManager$2", m277f = "BiometricSettingsRepository.kt", m278l = {}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$isFaceEnabledByBiometricsManager$2 */
/* loaded from: classes.dex */
public final class C1593x823f4eee extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ BiometricSettingsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1593x823f4eee(BiometricSettingsRepositoryImpl biometricSettingsRepositoryImpl, Continuation<? super C1593x823f4eee> continuation) {
        super(2, continuation);
        this.this$0 = biometricSettingsRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        C1593x823f4eee c1593x823f4eee = new C1593x823f4eee(this.this$0, continuation);
        c1593x823f4eee.L$0 = obj;
        return c1593x823f4eee;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((C1593x823f4eee) create((Pair) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Pair pair = (Pair) this.L$0;
        this.this$0.biometricsEnabledForUser.put(pair.getFirst(), pair.getSecond());
        return Unit.INSTANCE;
    }
}
