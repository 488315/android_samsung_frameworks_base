package com.android.systemui.biometrics.domain.interactor;

import com.android.internal.widget.LockscreenCredential;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class CredentialInteractorImpl$verifyCredential$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ LockscreenCredential $credential;
    final /* synthetic */ BiometricPromptRequest.Credential $request;
    int I$0;
    long J$0;
    long J$1;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CredentialInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CredentialInteractorImpl$verifyCredential$1(BiometricPromptRequest.Credential credential, CredentialInteractorImpl credentialInteractorImpl, LockscreenCredential lockscreenCredential, Continuation continuation) {
        super(2, continuation);
        this.$request = credential;
        this.this$0 = credentialInteractorImpl;
        this.$credential = lockscreenCredential;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CredentialInteractorImpl$verifyCredential$1 credentialInteractorImpl$verifyCredential$1 = new CredentialInteractorImpl$verifyCredential$1(this.$request, this.this$0, this.$credential, continuation);
        credentialInteractorImpl$verifyCredential$1.L$0 = obj;
        return credentialInteractorImpl$verifyCredential$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CredentialInteractorImpl$verifyCredential$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:114:0x0271, code lost:
    
        if (r2.emit(r11, r16) == r1) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0274, code lost:
    
        r1 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x0289, code lost:
    
        if (r2.emit(r8, r16) == r1) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x00f8, code lost:
    
        if (r2.emit(r10, r16) == r1) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0108, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(r8, r16) == r1) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0122, code lost:
    
        if (r2.emit(r10, r16) == r1) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x009f, code lost:
    
        if (r2.emit(r4, r16) == r1) goto L122;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x010e  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0108 -> B:11:0x010c). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r17) {
        /*
            Method dump skipped, instructions count: 680
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.domain.interactor.CredentialInteractorImpl$verifyCredential$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
