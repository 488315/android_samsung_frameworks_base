package com.android.systemui.biometrics.ui.viewmodel;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.biometrics.shared.model.BiometricModality;
import com.android.systemui.biometrics.ui.viewmodel.PromptMessage;
import com.android.systemui.biometrics.ui.viewmodel.PromptViewModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PromptViewModel$showTemporaryError$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $authenticateAfterError;
    final /* synthetic */ BiometricModality $failedModality;
    final /* synthetic */ boolean $hapticFeedback;
    final /* synthetic */ String $message;
    final /* synthetic */ String $messageAfterError;
    final /* synthetic */ Function2 $suppressIf;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ PromptViewModel this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$showTemporaryError$3$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $authenticateAfterError;
        final /* synthetic */ String $messageAfterError;
        int label;
        final /* synthetic */ PromptViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(PromptViewModel promptViewModel, boolean z, String str, Continuation continuation) {
            super(2, continuation);
            this.this$0 = promptViewModel;
            this.$authenticateAfterError = z;
            this.$messageAfterError = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$authenticateAfterError, this.$messageAfterError, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x0042, code lost:
        
            if (r7.showHelp(r1) == r0) goto L18;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0044, code lost:
        
            return r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x0029, code lost:
        
            if (kotlinx.coroutines.DelayKt.delay(r4, r6) == r0) goto L18;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r7) {
            /*
                r6 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r6.label
                r2 = 1
                r3 = 2
                if (r1 == 0) goto L1c
                if (r1 == r2) goto L18
                if (r1 != r3) goto L10
                kotlin.ResultKt.throwOnFailure(r7)
                goto L45
            L10:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r7)
                throw r6
            L18:
                kotlin.ResultKt.throwOnFailure(r7)
                goto L2c
            L1c:
                kotlin.ResultKt.throwOnFailure(r7)
                com.android.systemui.biometrics.ui.viewmodel.PromptViewModel r7 = r6.this$0
                long r4 = r7.messageDelay
                r6.label = r2
                java.lang.Object r7 = kotlinx.coroutines.DelayKt.delay(r4, r6)
                if (r7 != r0) goto L2c
                goto L44
            L2c:
                boolean r7 = r6.$authenticateAfterError
                if (r7 == 0) goto L38
                com.android.systemui.biometrics.ui.viewmodel.PromptViewModel r7 = r6.this$0
                java.lang.String r6 = r6.$messageAfterError
                com.android.systemui.biometrics.ui.viewmodel.PromptViewModel.showAuthenticating$default(r7, r6, r3)
                goto L45
            L38:
                com.android.systemui.biometrics.ui.viewmodel.PromptViewModel r7 = r6.this$0
                java.lang.String r1 = r6.$messageAfterError
                r6.label = r3
                kotlin.Unit r6 = r7.showHelp(r1)
                if (r6 != r0) goto L45
            L44:
                return r0
            L45:
                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$showTemporaryError$3.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptViewModel$showTemporaryError$3(PromptViewModel promptViewModel, boolean z, BiometricModality biometricModality, Function2 function2, String str, boolean z2, String str2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = promptViewModel;
        this.$hapticFeedback = z;
        this.$failedModality = biometricModality;
        this.$suppressIf = function2;
        this.$message = str;
        this.$authenticateAfterError = z2;
        this.$messageAfterError = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        PromptViewModel$showTemporaryError$3 promptViewModel$showTemporaryError$3 = new PromptViewModel$showTemporaryError$3(this.this$0, this.$hapticFeedback, this.$failedModality, this.$suppressIf, this.$message, this.$authenticateAfterError, this.$messageAfterError, continuation);
        promptViewModel$showTemporaryError$3.L$0 = obj;
        return promptViewModel$showTemporaryError$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PromptViewModel$showTemporaryError$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        if (((PromptAuthState) this.this$0._isAuthenticated.getValue()).isAuthenticated) {
            if (((PromptAuthState) this.this$0._isAuthenticated.getValue()).needsUserConfirmation && this.$hapticFeedback) {
                PromptViewModel promptViewModel = this.this$0;
                promptViewModel.getClass();
                promptViewModel._hapticsToPlay.updateState(null, new PromptViewModel.HapticsToPlay.HapticConstant(10005, null));
            }
            return Unit.INSTANCE;
        }
        this.this$0._canTryAgainNow.updateState(null, Boolean.valueOf(this.$failedModality == BiometricModality.Face));
        boolean booleanValue = ((Boolean) this.$suppressIf.invoke(this.this$0._message.getValue(), this.this$0.history)).booleanValue();
        PromptHistoryImpl promptHistoryImpl = this.this$0.history;
        BiometricModality biometricModality = this.$failedModality;
        promptHistoryImpl.getClass();
        if (biometricModality != BiometricModality.None) {
            promptHistoryImpl.failures.add(biometricModality);
        }
        if (booleanValue) {
            return Unit.INSTANCE;
        }
        this.this$0._isAuthenticating.updateState(null, Boolean.FALSE);
        this.this$0._isAuthenticated.updateState(null, new PromptAuthState(false, null, false, 0L, 14, null));
        this.this$0._forceMediumSize.updateState(null, Boolean.TRUE);
        this.this$0._message.updateState(null, new PromptMessage.Error(this.$message));
        if (this.$hapticFeedback) {
            PromptViewModel promptViewModel2 = this.this$0;
            promptViewModel2.getClass();
            promptViewModel2._hapticsToPlay.updateState(null, new PromptViewModel.HapticsToPlay.HapticConstant(10005, null));
        }
        StandaloneCoroutine standaloneCoroutine = this.this$0.messageJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        PromptViewModel promptViewModel3 = this.this$0;
        promptViewModel3.messageJob = CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(promptViewModel3, this.$authenticateAfterError, this.$messageAfterError, null), 7);
        return Unit.INSTANCE;
    }
}
