package com.android.systemui.qs.tiles.impl.saver.domain.interactor;

import com.android.systemui.statusbar.policy.DataSaverControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class DataSaverTileUserActionInteractor$handleInput$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $wasEnabled;
    int label;
    final /* synthetic */ DataSaverTileUserActionInteractor this$0;

    public DataSaverTileUserActionInteractor$handleInput$2$1(DataSaverTileUserActionInteractor dataSaverTileUserActionInteractor, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = dataSaverTileUserActionInteractor;
        this.$wasEnabled = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DataSaverTileUserActionInteractor$handleInput$2$1(this.this$0, this.$wasEnabled, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DataSaverTileUserActionInteractor$handleInput$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ((DataSaverControllerImpl) this.this$0.dataSaverController).setDataSaverEnabled(!this.$wasEnabled);
        return Unit.INSTANCE;
    }
}
