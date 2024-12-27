package com.android.systemui.qs.tiles.viewmodel;

import com.android.systemui.plugins.qs.QSTile;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class QSTileViewModelAdapter$setListening$1$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ QSTileViewModelAdapter this$0;

    public QSTileViewModelAdapter$setListening$1$2(QSTileViewModelAdapter qSTileViewModelAdapter, Continuation continuation) {
        super(2, continuation);
        this.this$0 = qSTileViewModelAdapter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        QSTileViewModelAdapter$setListening$1$2 qSTileViewModelAdapter$setListening$1$2 = new QSTileViewModelAdapter$setListening$1$2(this.this$0, continuation);
        qSTileViewModelAdapter$setListening$1$2.L$0 = obj;
        return qSTileViewModelAdapter$setListening$1$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QSTileViewModelAdapter$setListening$1$2) create((QSTile.State) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        QSTile.State state = (QSTile.State) this.L$0;
        QSTileViewModelAdapter qSTileViewModelAdapter = this.this$0;
        synchronized (qSTileViewModelAdapter.callbacks) {
            Iterator it = qSTileViewModelAdapter.callbacks.iterator();
            while (it.hasNext()) {
                ((QSTile.Callback) it.next()).onStateChanged(state);
            }
        }
        return Unit.INSTANCE;
    }
}
