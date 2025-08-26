package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.runtime.State;
import com.android.systemui.qs.panels.ui.compose.BounceableInfo;
import com.android.systemui.qs.panels.ui.viewmodel.BounceableTileViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class TileKt$Tile$2$3$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ State<BounceableInfo> $currentBounceableInfo$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TileKt$Tile$2$3$1$1$1(State<BounceableInfo> state, Continuation continuation) {
        super(2, continuation);
        this.$currentBounceableInfo$delegate = state;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TileKt$Tile$2$3$1$1$1(this.$currentBounceableInfo$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TileKt$Tile$2$3$1$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            BounceableTileViewModel bounceableTileViewModel = ((BounceableInfo) this.$currentBounceableInfo$delegate.getValue()).bounceable;
            this.label = 1;
            if (bounceableTileViewModel.animateBounce(this) == coroutineSingletons) {
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
