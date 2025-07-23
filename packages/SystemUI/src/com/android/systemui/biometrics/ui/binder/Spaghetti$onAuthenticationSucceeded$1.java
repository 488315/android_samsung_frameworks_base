package com.android.systemui.biometrics.ui.binder;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class Spaghetti$onAuthenticationSucceeded$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $modality;
    int label;
    final /* synthetic */ Spaghetti this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Spaghetti$onAuthenticationSucceeded$1(int i, Spaghetti spaghetti, Continuation continuation) {
        super(2, continuation);
        this.$modality = i;
        this.this$0 = spaghetti;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new Spaghetti$onAuthenticationSucceeded$1(this.$modality, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((Spaghetti$onAuthenticationSucceeded$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x006c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x005b  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r9.label
            r2 = 1
            if (r1 == 0) goto L15
            if (r1 != r2) goto Ld
            kotlin.ResultKt.throwOnFailure(r10)
            goto L6d
        Ld:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L15:
            kotlin.ResultKt.throwOnFailure(r10)
            int r10 = r9.$modality
            com.android.systemui.biometrics.shared.model.BiometricModality r4 = com.android.systemui.biometrics.shared.model.BiometricModalityKt.asBiometricModality(r10)
            com.android.systemui.biometrics.ui.binder.Spaghetti r10 = r9.this$0
            r10.getClass()
            com.android.systemui.biometrics.shared.model.BiometricModality r1 = com.android.systemui.biometrics.shared.model.BiometricModality.Face
            if (r4 == r1) goto L28
            goto L48
        L28:
            com.android.systemui.biometrics.shared.model.BiometricModalities r1 = r10.modalities
            boolean r1 = r1.getHasUdfps()
            if (r1 == 0) goto L38
            r10 = 2131952246(0x7f130276, float:1.954093E38)
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            goto L49
        L38:
            com.android.systemui.biometrics.shared.model.BiometricModalities r10 = r10.modalities
            boolean r10 = r10.getHasSfps()
            if (r10 == 0) goto L48
            r10 = 2131952249(0x7f130279, float:1.9540935E38)
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            goto L49
        L48:
            r10 = 0
        L49:
            com.android.systemui.biometrics.ui.binder.Spaghetti r1 = r9.this$0
            com.android.systemui.biometrics.ui.viewmodel.PromptViewModel r3 = r1.viewModel
            if (r10 == 0) goto L5b
            android.content.Context r1 = r1.applicationContext
            int r10 = r10.intValue()
            java.lang.String r10 = r1.getString(r10)
        L59:
            r7 = r10
            goto L5e
        L5b:
            java.lang.String r10 = ""
            goto L59
        L5e:
            r7.getClass()
            r9.label = r2
            r5 = 500(0x1f4, double:2.47E-321)
            r8 = r9
            java.lang.Object r9 = r3.showAuthenticated(r4, r5, r7, r8)
            if (r9 != r0) goto L6d
            return r0
        L6d:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.binder.Spaghetti$onAuthenticationSucceeded$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
