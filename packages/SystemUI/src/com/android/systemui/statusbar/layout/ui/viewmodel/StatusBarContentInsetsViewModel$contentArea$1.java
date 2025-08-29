package com.android.systemui.statusbar.layout.ui.viewmodel;

import com.android.systemui.statusbar.layout.StatusBarContentInsetsChangedListener;
import com.android.systemui.statusbar.layout.StatusBarContentInsetsProviderImpl;
import com.android.systemui.util.leak.RotationUtils;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class StatusBarContentInsetsViewModel$contentArea$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ StatusBarContentInsetsViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StatusBarContentInsetsViewModel$contentArea$1(StatusBarContentInsetsViewModel statusBarContentInsetsViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = statusBarContentInsetsViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        StatusBarContentInsetsViewModel$contentArea$1 statusBarContentInsetsViewModel$contentArea$1 = new StatusBarContentInsetsViewModel$contentArea$1(this.this$0, continuation);
        statusBarContentInsetsViewModel$contentArea$1.L$0 = obj;
        return statusBarContentInsetsViewModel$contentArea$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((StatusBarContentInsetsViewModel$contentArea$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.layout.ui.viewmodel.StatusBarContentInsetsViewModel$contentArea$1$listener$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final StatusBarContentInsetsViewModel statusBarContentInsetsViewModel = this.this$0;
            final ?? r1 = new StatusBarContentInsetsChangedListener() { // from class: com.android.systemui.statusbar.layout.ui.viewmodel.StatusBarContentInsetsViewModel$contentArea$1$listener$1
                @Override // com.android.systemui.statusbar.layout.StatusBarContentInsetsChangedListener
                public final void onStatusBarContentInsetsChanged() {
                    StatusBarContentInsetsProviderImpl statusBarContentInsetsProviderImpl = (StatusBarContentInsetsProviderImpl) statusBarContentInsetsViewModel.statusBarContentInsetsProvider;
                    ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(statusBarContentInsetsProviderImpl.getStatusBarContentAreaForRotation(RotationUtils.getExactRotation(statusBarContentInsetsProviderImpl.context)));
                }
            };
            ((StatusBarContentInsetsProviderImpl) this.this$0.statusBarContentInsetsProvider).addCallback(r1);
            final StatusBarContentInsetsViewModel statusBarContentInsetsViewModel2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.layout.ui.viewmodel.StatusBarContentInsetsViewModel$contentArea$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((StatusBarContentInsetsProviderImpl) statusBarContentInsetsViewModel2.statusBarContentInsetsProvider).removeCallback(r1);
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
