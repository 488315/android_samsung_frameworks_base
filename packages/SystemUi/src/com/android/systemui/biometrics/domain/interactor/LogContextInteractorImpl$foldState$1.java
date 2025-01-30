package com.android.systemui.biometrics.domain.interactor;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.unfold.updates.DeviceFoldStateProvider;
import com.android.systemui.unfold.updates.FoldStateProvider;
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
@DebugMetadata(m276c = "com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$foldState$1", m277f = "LogContextInteractor.kt", m278l = {143}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class LogContextInteractorImpl$foldState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ LogContextInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LogContextInteractorImpl$foldState$1(LogContextInteractorImpl logContextInteractorImpl, Continuation<? super LogContextInteractorImpl$foldState$1> continuation) {
        super(2, continuation);
        this.this$0 = logContextInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        LogContextInteractorImpl$foldState$1 logContextInteractorImpl$foldState$1 = new LogContextInteractorImpl$foldState$1(this.this$0, continuation);
        logContextInteractorImpl$foldState$1.L$0 = obj;
        return logContextInteractorImpl$foldState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LogContextInteractorImpl$foldState$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$foldState$1$callback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new FoldStateProvider.FoldUpdatesListener() { // from class: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$foldState$1$callback$1
                @Override // com.android.systemui.unfold.updates.FoldStateProvider.FoldUpdatesListener
                public final void onFoldUpdate(int i2) {
                    Integer num = i2 != 2 ? i2 != 3 ? i2 != 4 ? null : 3 : 2 : 1;
                    if (num != null) {
                        ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, ProducerScope.this, num, "ContextRepositoryImpl");
                    }
                }

                @Override // com.android.systemui.unfold.updates.FoldStateProvider.FoldUpdatesListener
                public final void onHingeAngleUpdate(float f) {
                }
            };
            ((DeviceFoldStateProvider) this.this$0.foldProvider).addCallback(r1);
            ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, producerScope, new Integer(0), "ContextRepositoryImpl");
            final LogContextInteractorImpl logContextInteractorImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$foldState$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((DeviceFoldStateProvider) LogContextInteractorImpl.this.foldProvider).removeCallback(r1);
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
