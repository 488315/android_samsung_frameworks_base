package com.android.systemui.qs.composefragment.viewmodel;

import com.android.systemui.plugins.statusbar.StatusBarStateController;
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

/* loaded from: classes2.dex */
final class QSFragmentComposeViewModel$statusBarState$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ QSFragmentComposeViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSFragmentComposeViewModel$statusBarState$2(QSFragmentComposeViewModel qSFragmentComposeViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = qSFragmentComposeViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        QSFragmentComposeViewModel$statusBarState$2 qSFragmentComposeViewModel$statusBarState$2 = new QSFragmentComposeViewModel$statusBarState$2(this.this$0, continuation);
        qSFragmentComposeViewModel$statusBarState$2.L$0 = obj;
        return qSFragmentComposeViewModel$statusBarState$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QSFragmentComposeViewModel$statusBarState$2) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.plugins.statusbar.StatusBarStateController$StateListener, com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$statusBarState$2$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new StatusBarStateController.StateListener() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$statusBarState$2$callback$1
                @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
                public final void onStateChanged(int i2) {
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Integer.valueOf(i2));
                }

                @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
                public final void onUpcomingStateChanged(int i2) {
                    if (i2 == 1) {
                        ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Integer.valueOf(i2));
                    }
                }
            };
            this.this$0.sysuiStatusBarStateController.addCallback(r1);
            final QSFragmentComposeViewModel qSFragmentComposeViewModel = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$statusBarState$2$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    qSFragmentComposeViewModel.sysuiStatusBarStateController.removeCallback(r1);
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
