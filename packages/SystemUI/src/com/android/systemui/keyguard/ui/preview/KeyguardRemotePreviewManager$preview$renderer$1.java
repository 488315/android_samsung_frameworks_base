package com.android.systemui.keyguard.ui.preview;

import android.os.Bundle;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class KeyguardRemotePreviewManager$preview$renderer$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Bundle $request;
    int label;
    final /* synthetic */ KeyguardRemotePreviewManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRemotePreviewManager$preview$renderer$1(KeyguardRemotePreviewManager keyguardRemotePreviewManager, Bundle bundle, Continuation continuation) {
        super(2, continuation);
        this.this$0 = keyguardRemotePreviewManager;
        this.$request = bundle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardRemotePreviewManager$preview$renderer$1(this.this$0, this.$request, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardRemotePreviewManager$preview$renderer$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.this$0.previewRendererFactory.create(this.$request);
    }
}
