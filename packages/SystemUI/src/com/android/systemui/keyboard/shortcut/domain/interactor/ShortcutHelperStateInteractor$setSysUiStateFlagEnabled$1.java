package com.android.systemui.keyboard.shortcut.domain.interactor;

import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class ShortcutHelperStateInteractor$setSysUiStateFlagEnabled$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $enabled;
    int label;
    final /* synthetic */ ShortcutHelperStateInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShortcutHelperStateInteractor$setSysUiStateFlagEnabled$1(ShortcutHelperStateInteractor shortcutHelperStateInteractor, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shortcutHelperStateInteractor;
        this.$enabled = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShortcutHelperStateInteractor$setSysUiStateFlagEnabled$1(this.this$0, this.$enabled, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShortcutHelperStateInteractor$setSysUiStateFlagEnabled$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SysUiState flag = this.this$0.sysUiState.setFlag(4294967296L, this.$enabled);
        this.this$0.displayTracker.getClass();
        ((SysUiStateImpl) flag).commitUpdate();
        return Unit.INSTANCE;
    }
}
