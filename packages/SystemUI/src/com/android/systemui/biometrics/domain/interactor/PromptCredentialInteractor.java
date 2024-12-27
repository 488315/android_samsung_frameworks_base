package com.android.systemui.biometrics.domain.interactor;

import android.content.pm.UserInfo;
import com.android.systemui.biometrics.data.repository.PromptRepository;
import com.android.systemui.biometrics.data.repository.PromptRepositoryImpl;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import com.android.systemui.biometrics.shared.model.BiometricUserInfo;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class PromptCredentialInteractor {
    public final StateFlowImpl _verificationError;
    public final CoroutineDispatcher bgDispatcher;
    public final CredentialInteractor credentialInteractor;
    public final Flow prompt;
    public final PromptCredentialInteractor$special$$inlined$map$1 showTitleOnly;
    public final ReadonlyStateFlow verificationError;

    public PromptCredentialInteractor(CoroutineDispatcher coroutineDispatcher, PromptRepository promptRepository, CredentialInteractor credentialInteractor) {
        this.bgDispatcher = coroutineDispatcher;
        this.credentialInteractor = credentialInteractor;
        PromptRepositoryImpl promptRepositoryImpl = (PromptRepositoryImpl) promptRepository;
        final ReadonlyStateFlow readonlyStateFlow = promptRepositoryImpl.promptInfo;
        this.showTitleOnly = new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L56
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        android.hardware.biometrics.PromptInfo r5 = (android.hardware.biometrics.PromptInfo) r5
                        if (r5 == 0) goto L3b
                        android.hardware.biometrics.PromptContentView r6 = r5.getContentView()
                        goto L3c
                    L3b:
                        r6 = 0
                    L3c:
                        if (r6 == 0) goto L46
                        boolean r5 = r5.isContentViewMoreOptionsButtonUsed()
                        if (r5 != 0) goto L46
                        r5 = r3
                        goto L47
                    L46:
                        r5 = 0
                    L47:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L56
                        return r1
                    L56:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.prompt = FlowKt.distinctUntilChanged(FlowKt.combine(promptRepositoryImpl.promptInfo, promptRepositoryImpl.challenge, promptRepositoryImpl.userId, promptRepositoryImpl.promptKind, new PromptCredentialInteractor$prompt$1(this, null)));
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._verificationError = MutableStateFlow;
        this.verificationError = FlowKt.asStateFlow(MutableStateFlow);
    }

    public static final BiometricUserInfo access$userInfo(PromptCredentialInteractor promptCredentialInteractor, int i, boolean z) {
        int credentialOwnerProfile;
        CredentialInteractorImpl credentialInteractorImpl = (CredentialInteractorImpl) promptCredentialInteractor.credentialInteractor;
        int credentialOwnerProfile2 = credentialInteractorImpl.userManager.getCredentialOwnerProfile(i);
        if (z) {
            UserInfo profileParent = credentialInteractorImpl.userManager.getProfileParent(i);
            credentialOwnerProfile = profileParent != null ? profileParent.id : credentialInteractorImpl.userManager.getCredentialOwnerProfile(i);
        } else {
            credentialOwnerProfile = credentialInteractorImpl.userManager.getCredentialOwnerProfile(i);
        }
        return new BiometricUserInfo(i, credentialOwnerProfile2, credentialOwnerProfile);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$verifyCredential(com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor r8, com.android.systemui.biometrics.domain.model.BiometricPromptRequest.Credential r9, com.android.internal.widget.LockscreenCredential r10, kotlin.coroutines.Continuation r11) {
        /*
            r8.getClass()
            boolean r0 = r11 instanceof com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor$verifyCredential$1
            if (r0 == 0) goto L16
            r0 = r11
            com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor$verifyCredential$1 r0 = (com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor$verifyCredential$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor$verifyCredential$1 r0 = new com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor$verifyCredential$1
            r0.<init>(r8, r11)
        L1b:
            java.lang.Object r11 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L32
            if (r2 != r3) goto L2a
            kotlin.ResultKt.throwOnFailure(r11)
            goto L63
        L2a:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L32:
            kotlin.ResultKt.throwOnFailure(r11)
            if (r10 == 0) goto L76
            boolean r11 = r10.isNone()
            if (r11 == 0) goto L3e
            goto L76
        L3e:
            com.android.systemui.biometrics.domain.interactor.CredentialInteractor r11 = r8.credentialInteractor
            com.android.systemui.biometrics.domain.interactor.CredentialInteractorImpl r11 = (com.android.systemui.biometrics.domain.interactor.CredentialInteractorImpl) r11
            r11.getClass()
            com.android.systemui.biometrics.domain.interactor.CredentialInteractorImpl$verifyCredential$1 r2 = new com.android.systemui.biometrics.domain.interactor.CredentialInteractorImpl$verifyCredential$1
            r4 = 0
            r2.<init>(r9, r11, r10, r4)
            kotlinx.coroutines.flow.SafeFlow r9 = new kotlinx.coroutines.flow.SafeFlow
            r9.<init>(r2)
            com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor$verifyCredential$finalStatus$1 r10 = new com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor$verifyCredential$finalStatus$1
            r10.<init>(r8, r4)
            kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 r8 = new kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1
            r8.<init>(r9, r10)
            r0.label = r3
            java.lang.Object r11 = kotlinx.coroutines.flow.FlowKt.lastOrNull(r8, r0)
            if (r11 != r1) goto L63
            goto L81
        L63:
            com.android.systemui.biometrics.domain.interactor.CredentialStatus r11 = (com.android.systemui.biometrics.domain.interactor.CredentialStatus) r11
            if (r11 != 0) goto L74
            com.android.systemui.biometrics.domain.interactor.CredentialStatus$Fail$Error r8 = new com.android.systemui.biometrics.domain.interactor.CredentialStatus$Fail$Error
            r4 = 7
            r5 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            r0 = r8
            r0.<init>(r1, r2, r3, r4, r5)
            r1 = r8
            goto L81
        L74:
            r1 = r11
            goto L81
        L76:
            com.android.systemui.biometrics.domain.interactor.CredentialStatus$Fail$Error r1 = new com.android.systemui.biometrics.domain.interactor.CredentialStatus$Fail$Error
            r6 = 7
            r7 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r2 = r1
            r2.<init>(r3, r4, r5, r6, r7)
        L81:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor.access$verifyCredential(com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor, com.android.systemui.biometrics.domain.model.BiometricPromptRequest$Credential, com.android.internal.widget.LockscreenCredential, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static Object checkCredential$default(PromptCredentialInteractor promptCredentialInteractor, BiometricPromptRequest.Credential credential, CharSequence charSequence, List list, ContinuationImpl continuationImpl, int i) {
        CharSequence charSequence2 = (i & 2) != 0 ? null : charSequence;
        List list2 = (i & 4) != 0 ? null : list;
        promptCredentialInteractor.getClass();
        return BuildersKt.withContext(promptCredentialInteractor.bgDispatcher, new PromptCredentialInteractor$checkCredential$2(credential, charSequence2, list2, promptCredentialInteractor, null), continuationImpl);
    }
}
