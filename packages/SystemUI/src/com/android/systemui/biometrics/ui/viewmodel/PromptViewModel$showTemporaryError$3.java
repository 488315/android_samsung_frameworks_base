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
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.StandaloneCoroutine;

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

        /* JADX WARN: Code restructure failed: missing block: B:17:0x0042, code lost:
        
            if (r7.showHelp(r1) == r0) goto L18;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                long j = this.this$0.messageDelay;
                this.label = 1;
                if (DelayKt.delay(j, this) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            if (this.$authenticateAfterError) {
                PromptViewModel.showAuthenticating$default(this.this$0, this.$messageAfterError, 2);
            } else {
                PromptViewModel promptViewModel = this.this$0;
                String str = this.$messageAfterError;
                this.label = 2;
            }
            return Unit.INSTANCE;
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
        boolean zBooleanValue = ((Boolean) this.$suppressIf.invoke(this.this$0._message.getValue(), this.this$0.history)).booleanValue();
        PromptHistoryImpl promptHistoryImpl = this.this$0.history;
        BiometricModality biometricModality = this.$failedModality;
        promptHistoryImpl.getClass();
        if (biometricModality != BiometricModality.None) {
            promptHistoryImpl.failures.add(biometricModality);
        }
        if (zBooleanValue) {
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
