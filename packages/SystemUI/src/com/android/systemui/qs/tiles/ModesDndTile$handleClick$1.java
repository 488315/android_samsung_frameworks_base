package com.android.systemui.qs.tiles;

import com.android.systemui.qs.tiles.impl.modes.domain.interactor.ModesDndTileUserActionInteractor;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ModesDndTile$handleClick$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ModesDndTile this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ModesDndTile$handleClick$1(ModesDndTile modesDndTile, Continuation continuation) {
        super(2, continuation);
        this.this$0 = modesDndTile;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ModesDndTile$handleClick$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ModesDndTile$handleClick$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        ModesDndTileUserActionInteractor modesDndTileUserActionInteractor = this.this$0.userActionInteractor;
        this.label = 1;
        modesDndTileUserActionInteractor.zenModeInteractor.getClass();
        ZenModeInteractor.getDndMode();
        throw null;
    }
}
