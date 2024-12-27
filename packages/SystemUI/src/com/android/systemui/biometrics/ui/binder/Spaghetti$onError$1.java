package com.android.systemui.biometrics.ui.binder;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class Spaghetti$onError$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $error;
    int label;
    final /* synthetic */ Spaghetti this$0;

    public Spaghetti$onError$1(Spaghetti spaghetti, String str, Continuation continuation) {
        super(2, continuation);
        this.this$0 = spaghetti;
        this.$error = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new Spaghetti$onError$1(this.this$0, this.$error, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((Spaghetti$onError$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            r12 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r12.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1c
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r13)
            goto L4e
        L10:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L18:
            kotlin.ResultKt.throwOnFailure(r13)
            goto L43
        L1c:
            kotlin.ResultKt.throwOnFailure(r13)
            com.android.systemui.biometrics.ui.binder.Spaghetti r13 = r12.this$0
            com.android.systemui.biometrics.ui.viewmodel.PromptViewModel r4 = r13.viewModel
            java.lang.String r5 = r12.$error
            com.android.systemui.biometrics.shared.model.BiometricModalities r1 = r13.modalities
            android.content.Context r13 = r13.applicationContext
            java.lang.String r6 = com.android.systemui.biometrics.ui.binder.BiometricViewBinderKt.access$asDefaultHelpMessage(r1, r13)
            com.android.systemui.biometrics.ui.binder.Spaghetti r13 = r12.this$0
            com.android.systemui.biometrics.shared.model.BiometricModalities r13 = r13.modalities
            boolean r7 = r13.getHasFingerprint()
            r12.label = r3
            r9 = 0
            r11 = 56
            r8 = 0
            r10 = r12
            java.lang.Object r13 = com.android.systemui.biometrics.ui.viewmodel.PromptViewModel.showTemporaryError$default(r4, r5, r6, r7, r8, r9, r10, r11)
            if (r13 != r0) goto L43
            return r0
        L43:
            r12.label = r2
            r1 = 2000(0x7d0, double:9.88E-321)
            java.lang.Object r13 = kotlinx.coroutines.DelayKt.delay(r1, r12)
            if (r13 != r0) goto L4e
            return r0
        L4e:
            com.android.systemui.biometrics.ui.binder.Spaghetti r12 = r12.this$0
            com.android.systemui.biometrics.ui.binder.Spaghetti$Callback r12 = r12.legacyCallback
            if (r12 == 0) goto L57
            r12.onError()
        L57:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.binder.Spaghetti$onError$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
