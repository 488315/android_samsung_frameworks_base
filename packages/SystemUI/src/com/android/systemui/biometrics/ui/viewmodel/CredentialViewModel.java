package com.android.systemui.biometrics.ui.viewmodel;

import android.content.Context;
import com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CredentialViewModel {
    public final StateFlowImpl _animateContents;
    public final StateFlowImpl _remainingAttempts;
    public final SharedFlowImpl _validatedAttestation;
    public final ReadonlyStateFlow animateContents;
    public final Context applicationContext;
    public final PromptCredentialInteractor credentialInteractor;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 errorMessage;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 header;
    public final CredentialViewModel$special$$inlined$map$2 inputBoxContentDescription;
    public final CredentialViewModel$special$$inlined$map$1 inputFlags;
    public final ReadonlyStateFlow remainingAttempts;
    public final CredentialViewModel$special$$inlined$map$3 stealthMode;
    public final ReadonlySharedFlow validatedAttestation;

    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$1] */
    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$2] */
    public CredentialViewModel(Context context, PromptCredentialInteractor promptCredentialInteractor) {
        this.applicationContext = context;
        this.credentialInteractor = promptCredentialInteractor;
        final Flow flow = promptCredentialInteractor.prompt;
        this.header = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$filterIsInstance$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$filterIsInstance$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$filterIsInstance$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$filterIsInstance$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$filterIsInstance$1$2$1 r0 = (com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$filterIsInstance$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$filterIsInstance$1$2$1 r0 = new com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$filterIsInstance$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L41
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        boolean r6 = r5 instanceof com.android.systemui.biometrics.domain.model.BiometricPromptRequest.Credential
                        if (r6 == 0) goto L41
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$filterIsInstance$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, promptCredentialInteractor.showTitleOnly, new CredentialViewModel$header$1(this, null));
        final Flow flow2 = promptCredentialInteractor.prompt;
        this.inputFlags = new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.biometrics.domain.model.BiometricPromptRequest$Credential r5 = (com.android.systemui.biometrics.domain.model.BiometricPromptRequest.Credential) r5
                        boolean r5 = r5 instanceof com.android.systemui.biometrics.domain.model.BiometricPromptRequest.Credential.Pin
                        if (r5 == 0) goto L40
                        java.lang.Integer r5 = new java.lang.Integer
                        r6 = 18
                        r5.<init>(r6)
                        goto L41
                    L40:
                        r5 = 0
                    L41:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.inputBoxContentDescription = new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$2$2$1 r0 = (com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$2$2$1 r0 = new com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.biometrics.domain.model.BiometricPromptRequest$Credential r5 = (com.android.systemui.biometrics.domain.model.BiometricPromptRequest.Credential) r5
                        boolean r6 = r5 instanceof com.android.systemui.biometrics.domain.model.BiometricPromptRequest.Credential.Pin
                        if (r6 == 0) goto L41
                        java.lang.Integer r5 = new java.lang.Integer
                        r6 = 2131953933(0x7f13090d, float:1.954435E38)
                        r5.<init>(r6)
                        goto L4f
                    L41:
                        boolean r5 = r5 instanceof com.android.systemui.biometrics.domain.model.BiometricPromptRequest.Credential.Password
                        if (r5 == 0) goto L4e
                        java.lang.Integer r5 = new java.lang.Integer
                        r6 = 2131953929(0x7f130909, float:1.9544343E38)
                        r5.<init>(r6)
                        goto L4f
                    L4e:
                        r5 = 0
                    L4f:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L5a
                        return r1
                    L5a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.stealthMode = new CredentialViewModel$special$$inlined$map$3(flow2);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.TRUE);
        this._animateContents = MutableStateFlow;
        this.animateContents = FlowKt.asStateFlow(MutableStateFlow);
        this.errorMessage = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(promptCredentialInteractor.verificationError, flow2, new CredentialViewModel$errorMessage$1(this, null));
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._validatedAttestation = MutableSharedFlow$default;
        this.validatedAttestation = FlowKt.asSharedFlow(MutableSharedFlow$default);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(new RemainingAttempts(null, null, 3, null));
        this._remainingAttempts = MutableStateFlow2;
        this.remainingAttempts = FlowKt.asStateFlow(MutableStateFlow2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0065, code lost:
    
        if (r2.emit(r8, r0) == r1) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0081, code lost:
    
        if (r2.emit(null, r0) == r1) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00a5, code lost:
    
        if (r2.emit(null, r0) == r1) goto L37;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object checkCredential(com.android.systemui.biometrics.domain.interactor.CredentialStatus r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$checkCredential$3
            if (r0 == 0) goto L13
            r0 = r9
            com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$checkCredential$3 r0 = (com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$checkCredential$3) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$checkCredential$3 r0 = new com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$checkCredential$3
            r0.<init>(r7, r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            r5 = 3
            r6 = 0
            if (r2 == 0) goto L50
            if (r2 == r4) goto L48
            if (r2 == r3) goto L3b
            if (r2 != r5) goto L33
            java.lang.Object r7 = r0.L$0
            com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel r7 = (com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel) r7
            kotlin.ResultKt.throwOnFailure(r9)
            goto La8
        L33:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L3b:
            java.lang.Object r7 = r0.L$1
            r8 = r7
            com.android.systemui.biometrics.domain.interactor.CredentialStatus r8 = (com.android.systemui.biometrics.domain.interactor.CredentialStatus) r8
            java.lang.Object r7 = r0.L$0
            com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel r7 = (com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel) r7
            kotlin.ResultKt.throwOnFailure(r9)
            goto L84
        L48:
            java.lang.Object r7 = r0.L$0
            com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel r7 = (com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel) r7
            kotlin.ResultKt.throwOnFailure(r9)
            goto L68
        L50:
            kotlin.ResultKt.throwOnFailure(r9)
            boolean r9 = r8 instanceof com.android.systemui.biometrics.domain.interactor.CredentialStatus$Success$Verified
            kotlinx.coroutines.flow.SharedFlowImpl r2 = r7._validatedAttestation
            if (r9 == 0) goto L73
            com.android.systemui.biometrics.domain.interactor.CredentialStatus$Success$Verified r8 = (com.android.systemui.biometrics.domain.interactor.CredentialStatus$Success$Verified) r8
            byte[] r8 = r8.hat
            r0.L$0 = r7
            r0.label = r4
            java.lang.Object r8 = r2.emit(r8, r0)
            if (r8 != r1) goto L68
            goto La7
        L68:
            kotlinx.coroutines.flow.StateFlowImpl r7 = r7._remainingAttempts
            com.android.systemui.biometrics.ui.viewmodel.RemainingAttempts r8 = new com.android.systemui.biometrics.ui.viewmodel.RemainingAttempts
            r8.<init>(r6, r6, r5, r6)
            r7.updateState(r6, r8)
            goto Lb2
        L73:
            boolean r9 = r8 instanceof com.android.systemui.biometrics.domain.interactor.CredentialStatus.Fail.Error
            if (r9 == 0) goto L99
            r0.L$0 = r7
            r0.L$1 = r8
            r0.label = r3
            java.lang.Object r9 = r2.emit(r6, r0)
            if (r9 != r1) goto L84
            goto La7
        L84:
            kotlinx.coroutines.flow.StateFlowImpl r7 = r7._remainingAttempts
            com.android.systemui.biometrics.ui.viewmodel.RemainingAttempts r9 = new com.android.systemui.biometrics.ui.viewmodel.RemainingAttempts
            com.android.systemui.biometrics.domain.interactor.CredentialStatus$Fail$Error r8 = (com.android.systemui.biometrics.domain.interactor.CredentialStatus.Fail.Error) r8
            java.lang.Integer r0 = r8.remainingAttempts
            java.lang.String r8 = r8.urgentMessage
            if (r8 != 0) goto L92
            java.lang.String r8 = ""
        L92:
            r9.<init>(r0, r8)
            r7.updateState(r6, r9)
            goto Lb2
        L99:
            boolean r8 = r8 instanceof com.android.systemui.biometrics.domain.interactor.CredentialStatus.Fail.Throttled
            if (r8 == 0) goto Lb5
            r0.L$0 = r7
            r0.label = r5
            java.lang.Object r8 = r2.emit(r6, r0)
            if (r8 != r1) goto La8
        La7:
            return r1
        La8:
            kotlinx.coroutines.flow.StateFlowImpl r7 = r7._remainingAttempts
            com.android.systemui.biometrics.ui.viewmodel.RemainingAttempts r8 = new com.android.systemui.biometrics.ui.viewmodel.RemainingAttempts
            r8.<init>(r6, r6, r5, r6)
            r7.updateState(r6, r8)
        Lb2:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        Lb5:
            kotlin.NoWhenBranchMatchedException r7 = new kotlin.NoWhenBranchMatchedException
            r7.<init>()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel.checkCredential(com.android.systemui.biometrics.domain.interactor.CredentialStatus, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x005f, code lost:
    
        if (r8.checkCredential((com.android.systemui.biometrics.domain.interactor.CredentialStatus) r11, r5) != r0) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0061, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0051, code lost:
    
        if (r11 == r0) goto L22;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object checkCredential(java.lang.CharSequence r9, com.android.systemui.biometrics.ui.viewmodel.CredentialHeaderViewModel r10, kotlin.coroutines.jvm.internal.ContinuationImpl r11) {
        /*
            r8 = this;
            boolean r0 = r11 instanceof com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$checkCredential$1
            if (r0 == 0) goto L14
            r0 = r11
            com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$checkCredential$1 r0 = (com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$checkCredential$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L14
            int r1 = r1 - r2
            r0.label = r1
        L12:
            r5 = r0
            goto L1a
        L14:
            com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$checkCredential$1 r0 = new com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$checkCredential$1
            r0.<init>(r8, r11)
            goto L12
        L1a:
            java.lang.Object r11 = r5.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            r7 = 2
            r2 = 1
            if (r1 == 0) goto L3c
            if (r1 == r2) goto L34
            if (r1 != r7) goto L2c
            kotlin.ResultKt.throwOnFailure(r11)
            goto L62
        L2c:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L34:
            java.lang.Object r8 = r5.L$0
            com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel r8 = (com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel) r8
            kotlin.ResultKt.throwOnFailure(r11)
            goto L54
        L3c:
            kotlin.ResultKt.throwOnFailure(r11)
            com.android.systemui.biometrics.ui.viewmodel.BiometricPromptHeaderViewModelImpl r10 = (com.android.systemui.biometrics.ui.viewmodel.BiometricPromptHeaderViewModelImpl) r10
            com.android.systemui.biometrics.domain.model.BiometricPromptRequest$Credential r10 = r10.request
            r5.L$0 = r8
            r5.label = r2
            r4 = 0
            r6 = 4
            com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor r1 = r8.credentialInteractor
            r3 = r9
            r2 = r10
            java.lang.Object r11 = com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor.checkCredential$default(r1, r2, r3, r4, r5, r6)
            if (r11 != r0) goto L54
            goto L61
        L54:
            com.android.systemui.biometrics.domain.interactor.CredentialStatus r11 = (com.android.systemui.biometrics.domain.interactor.CredentialStatus) r11
            r9 = 0
            r5.L$0 = r9
            r5.label = r7
            java.lang.Object r8 = r8.checkCredential(r11, r5)
            if (r8 != r0) goto L62
        L61:
            return r0
        L62:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel.checkCredential(java.lang.CharSequence, com.android.systemui.biometrics.ui.viewmodel.CredentialHeaderViewModel, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x005f, code lost:
    
        if (r8.checkCredential((com.android.systemui.biometrics.domain.interactor.CredentialStatus) r11, r5) != r0) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0061, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0051, code lost:
    
        if (r11 == r0) goto L22;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object checkCredential(java.util.List r9, com.android.systemui.biometrics.ui.viewmodel.CredentialHeaderViewModel r10, kotlin.coroutines.jvm.internal.ContinuationImpl r11) {
        /*
            r8 = this;
            boolean r0 = r11 instanceof com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$checkCredential$2
            if (r0 == 0) goto L14
            r0 = r11
            com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$checkCredential$2 r0 = (com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$checkCredential$2) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L14
            int r1 = r1 - r2
            r0.label = r1
        L12:
            r5 = r0
            goto L1a
        L14:
            com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$checkCredential$2 r0 = new com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$checkCredential$2
            r0.<init>(r8, r11)
            goto L12
        L1a:
            java.lang.Object r11 = r5.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            r7 = 2
            r2 = 1
            if (r1 == 0) goto L3c
            if (r1 == r2) goto L34
            if (r1 != r7) goto L2c
            kotlin.ResultKt.throwOnFailure(r11)
            goto L62
        L2c:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L34:
            java.lang.Object r8 = r5.L$0
            com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel r8 = (com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel) r8
            kotlin.ResultKt.throwOnFailure(r11)
            goto L54
        L3c:
            kotlin.ResultKt.throwOnFailure(r11)
            com.android.systemui.biometrics.ui.viewmodel.BiometricPromptHeaderViewModelImpl r10 = (com.android.systemui.biometrics.ui.viewmodel.BiometricPromptHeaderViewModelImpl) r10
            com.android.systemui.biometrics.domain.model.BiometricPromptRequest$Credential r10 = r10.request
            r5.L$0 = r8
            r5.label = r2
            r3 = 0
            r6 = 2
            com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor r1 = r8.credentialInteractor
            r4 = r9
            r2 = r10
            java.lang.Object r11 = com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor.checkCredential$default(r1, r2, r3, r4, r5, r6)
            if (r11 != r0) goto L54
            goto L61
        L54:
            com.android.systemui.biometrics.domain.interactor.CredentialStatus r11 = (com.android.systemui.biometrics.domain.interactor.CredentialStatus) r11
            r9 = 0
            r5.L$0 = r9
            r5.label = r7
            java.lang.Object r8 = r8.checkCredential(r11, r5)
            if (r8 != r0) goto L62
        L61:
            return r0
        L62:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel.checkCredential(java.util.List, com.android.systemui.biometrics.ui.viewmodel.CredentialHeaderViewModel, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
