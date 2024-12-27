package com.android.systemui.keyguard.ui.preview;

import com.android.systemui.Flags;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class PreviewLifecycleObserver$onDestroy$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ KeyguardPreviewRenderer $rendererToDestroy;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PreviewLifecycleObserver$onDestroy$2$1(KeyguardPreviewRenderer keyguardPreviewRenderer, Continuation continuation) {
        super(2, continuation);
        this.$rendererToDestroy = keyguardPreviewRenderer;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PreviewLifecycleObserver$onDestroy$2$1(this.$rendererToDestroy, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PreviewLifecycleObserver$onDestroy$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        KeyguardPreviewRenderer keyguardPreviewRenderer = this.$rendererToDestroy;
        keyguardPreviewRenderer.isDestroyed = true;
        keyguardPreviewRenderer.lockscreenSmartspaceController.disconnect();
        keyguardPreviewRenderer.disposables.dispose();
        Flags.keyguardBottomAreaRefactor();
        return Unit.INSTANCE;
    }
}
