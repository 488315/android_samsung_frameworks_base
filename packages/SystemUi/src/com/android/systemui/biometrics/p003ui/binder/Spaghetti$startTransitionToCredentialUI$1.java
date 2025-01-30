package com.android.systemui.biometrics.p003ui.binder;

import com.android.systemui.biometrics.AuthBiometricView;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.ui.binder.Spaghetti$startTransitionToCredentialUI$1", m277f = "BiometricViewBinder.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class Spaghetti$startTransitionToCredentialUI$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ Spaghetti this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Spaghetti$startTransitionToCredentialUI$1(Spaghetti spaghetti, Continuation<? super Spaghetti$startTransitionToCredentialUI$1> continuation) {
        super(2, continuation);
        this.this$0 = spaghetti;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new Spaghetti$startTransitionToCredentialUI$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((Spaghetti$startTransitionToCredentialUI$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.viewModel._forceLargeSize.setValue(Boolean.TRUE);
        AuthBiometricView.Callback callback = this.this$0.legacyCallback;
        if (callback != null) {
            callback.onAction(6);
        }
        return Unit.INSTANCE;
    }
}
