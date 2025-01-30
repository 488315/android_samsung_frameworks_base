package com.android.systemui.keyguard.bouncer.data.repository;

import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.bouncer.data.repository.BouncerMessageRepositoryImpl$biometricAuthMessage$2", m277f = "BouncerMessageRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcut}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class BouncerMessageRepositoryImpl$biometricAuthMessage$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;

    public BouncerMessageRepositoryImpl$biometricAuthMessage$2(Continuation<? super BouncerMessageRepositoryImpl$biometricAuthMessage$2> continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BouncerMessageRepositoryImpl$biometricAuthMessage$2 bouncerMessageRepositoryImpl$biometricAuthMessage$2 = new BouncerMessageRepositoryImpl$biometricAuthMessage$2(continuation);
        bouncerMessageRepositoryImpl$biometricAuthMessage$2.L$0 = obj;
        return bouncerMessageRepositoryImpl$biometricAuthMessage$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BouncerMessageRepositoryImpl$biometricAuthMessage$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            this.label = 1;
            if (flowCollector.emit(null, this) == coroutineSingletons) {
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
