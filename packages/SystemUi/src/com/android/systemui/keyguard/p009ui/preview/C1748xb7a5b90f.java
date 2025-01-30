package com.android.systemui.keyguard.p009ui.preview;

import com.android.systemui.keyguard.p009ui.preview.KeyguardRemotePreviewManager;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.ui.preview.KeyguardRemotePreviewManager$PreviewLifecycleObserver$onDestroy$1", m277f = "KeyguardRemotePreviewManager.kt", m278l = {}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.keyguard.ui.preview.KeyguardRemotePreviewManager$PreviewLifecycleObserver$onDestroy$1 */
/* loaded from: classes.dex */
final class C1748xb7a5b90f extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyguardRemotePreviewManager.PreviewLifecycleObserver this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1748xb7a5b90f(KeyguardRemotePreviewManager.PreviewLifecycleObserver previewLifecycleObserver, Continuation<? super C1748xb7a5b90f> continuation) {
        super(2, continuation);
        this.this$0 = previewLifecycleObserver;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new C1748xb7a5b90f(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((C1748xb7a5b90f) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        KeyguardPreviewRenderer keyguardPreviewRenderer = this.this$0.renderer;
        keyguardPreviewRenderer.isDestroyed = true;
        keyguardPreviewRenderer.lockscreenSmartspaceController.disconnect();
        Iterator it = keyguardPreviewRenderer.disposables.iterator();
        while (it.hasNext()) {
            ((DisposableHandle) it.next()).dispose();
        }
        return Unit.INSTANCE;
    }
}
