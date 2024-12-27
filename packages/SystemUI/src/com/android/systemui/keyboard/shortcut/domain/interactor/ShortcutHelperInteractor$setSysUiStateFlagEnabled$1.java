package com.android.systemui.keyboard.shortcut.domain.interactor;

import com.android.systemui.model.SysUiState;
import com.samsung.android.desktopsystemui.sharedlib.keyguard.SemWallpaperColorsWrapper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class ShortcutHelperInteractor$setSysUiStateFlagEnabled$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $enabled;
    int label;
    final /* synthetic */ ShortcutHelperInteractor this$0;

    public ShortcutHelperInteractor$setSysUiStateFlagEnabled$1(ShortcutHelperInteractor shortcutHelperInteractor, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = shortcutHelperInteractor;
        this.$enabled = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ShortcutHelperInteractor$setSysUiStateFlagEnabled$1(this.this$0, this.$enabled, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ShortcutHelperInteractor$setSysUiStateFlagEnabled$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SysUiState sysUiState = this.this$0.sysUiState;
        sysUiState.setFlag(SemWallpaperColorsWrapper.LOCKSCREEN_STATUS_BAR, this.$enabled);
        this.this$0.displayTracker.getClass();
        sysUiState.commitUpdate(0);
        return Unit.INSTANCE;
    }
}
