package com.android.systemui.biometrics.domain.interactor;

import com.android.internal.widget.LockPatternView;
import com.android.internal.widget.LockscreenCredential;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class PromptCredentialInteractor$checkCredential$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ List<LockPatternView.Cell> $pattern;
    final /* synthetic */ BiometricPromptRequest.Credential $request;
    final /* synthetic */ CharSequence $text;
    Object L$0;
    int label;
    final /* synthetic */ PromptCredentialInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptCredentialInteractor$checkCredential$2(BiometricPromptRequest.Credential credential, CharSequence charSequence, List<LockPatternView.Cell> list, PromptCredentialInteractor promptCredentialInteractor, Continuation continuation) {
        super(2, continuation);
        this.$request = credential;
        this.$text = charSequence;
        this.$pattern = list;
        this.this$0 = promptCredentialInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PromptCredentialInteractor$checkCredential$2(this.$request, this.$text, this.$pattern, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((PromptCredentialInteractor$checkCredential$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v12, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r6v7, types: [java.lang.CharSequence] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        AutoCloseable createPattern;
        Throwable th;
        AutoCloseable autoCloseable;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            BiometricPromptRequest.Credential credential = this.$request;
            if (credential instanceof BiometricPromptRequest.Credential.Pin) {
                ?? r6 = this.$text;
                createPattern = LockscreenCredential.createPinOrNone(r6 != 0 ? r6 : "");
            } else if (credential instanceof BiometricPromptRequest.Credential.Password) {
                ?? r62 = this.$text;
                createPattern = LockscreenCredential.createPasswordOrNone(r62 != 0 ? r62 : "");
            } else {
                if (!(credential instanceof BiometricPromptRequest.Credential.Pattern)) {
                    throw new NoWhenBranchMatchedException();
                }
                List list = this.$pattern;
                if (list == null) {
                    list = EmptyList.INSTANCE;
                }
                createPattern = LockscreenCredential.createPattern(list);
            }
            PromptCredentialInteractor promptCredentialInteractor = this.this$0;
            BiometricPromptRequest.Credential credential2 = this.$request;
            try {
                this.L$0 = createPattern;
                this.label = 1;
                Object access$verifyCredential = PromptCredentialInteractor.access$verifyCredential(promptCredentialInteractor, credential2, createPattern, this);
                if (access$verifyCredential == coroutineSingletons) {
                    return coroutineSingletons;
                }
                AutoCloseable autoCloseable2 = createPattern;
                obj = access$verifyCredential;
                autoCloseable = autoCloseable2;
            } catch (Throwable th2) {
                AutoCloseable autoCloseable3 = createPattern;
                th = th2;
                autoCloseable = autoCloseable3;
                throw th;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            autoCloseable = (AutoCloseable) this.L$0;
            try {
                ResultKt.throwOnFailure(obj);
            } catch (Throwable th3) {
                th = th3;
                try {
                    throw th;
                } catch (Throwable th4) {
                    AutoCloseableKt.closeFinally(autoCloseable, th);
                    throw th4;
                }
            }
        }
        CredentialStatus credentialStatus = (CredentialStatus) obj;
        AutoCloseableKt.closeFinally(autoCloseable, null);
        return credentialStatus;
    }
}
