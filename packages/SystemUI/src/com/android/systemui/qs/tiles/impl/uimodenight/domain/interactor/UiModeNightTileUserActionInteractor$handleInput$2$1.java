package com.android.systemui.qs.tiles.impl.uimodenight.domain.interactor;

import com.android.systemui.qs.tiles.base.interactor.QSTileInput;
import com.android.systemui.qs.tiles.impl.uimodenight.domain.model.UiModeNightTileModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class UiModeNightTileUserActionInteractor$handleInput$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ QSTileInput $input;
    int label;
    final /* synthetic */ UiModeNightTileUserActionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UiModeNightTileUserActionInteractor$handleInput$2$1(UiModeNightTileUserActionInteractor uiModeNightTileUserActionInteractor, QSTileInput qSTileInput, Continuation continuation) {
        super(2, continuation);
        this.this$0 = uiModeNightTileUserActionInteractor;
        this.$input = qSTileInput;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UiModeNightTileUserActionInteractor$handleInput$2$1(this.this$0, this.$input, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UiModeNightTileUserActionInteractor$handleInput$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.this$0.uiModeManager.setNightModeActivated(!((UiModeNightTileModel) this.$input.data).isNightMode));
    }
}
