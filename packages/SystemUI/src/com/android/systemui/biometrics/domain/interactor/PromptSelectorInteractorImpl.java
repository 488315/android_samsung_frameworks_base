package com.android.systemui.biometrics.domain.interactor;

import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.biometrics.data.repository.FingerprintPropertyRepository;
import com.android.systemui.biometrics.data.repository.FingerprintPropertyRepositoryImpl;
import com.android.systemui.biometrics.data.repository.PromptRepository;
import com.android.systemui.biometrics.data.repository.PromptRepositoryImpl;
import com.android.systemui.biometrics.shared.model.PromptKind;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class PromptSelectorInteractorImpl implements PromptSelectorInteractor {
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 credentialKind;
    public final DisplayStateInteractor displayStateInteractor;
    public final Flow isConfirmationRequired;
    public final Flow isCredentialAllowed;
    public final LockPatternUtils lockPatternUtils;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 prompt;
    public final ReadonlyStateFlow promptKind;
    public final PromptRepository promptRepository;
    public final StateFlow sensorType;

    public PromptSelectorInteractorImpl(FingerprintPropertyRepository fingerprintPropertyRepository, DisplayStateInteractor displayStateInteractor, PromptRepository promptRepository, LockPatternUtils lockPatternUtils) {
        this.displayStateInteractor = displayStateInteractor;
        this.promptRepository = promptRepository;
        this.lockPatternUtils = lockPatternUtils;
        PromptRepositoryImpl promptRepositoryImpl = (PromptRepositoryImpl) promptRepository;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 combine = FlowKt.combine(promptRepositoryImpl.promptInfo, promptRepositoryImpl.challenge, promptRepositoryImpl.userId, promptRepositoryImpl.promptKind, promptRepositoryImpl.opPackageName, new PromptSelectorInteractorImpl$prompt$1(null));
        this.prompt = combine;
        this.promptKind = promptRepositoryImpl.promptKind;
        this.isConfirmationRequired = FlowKt.distinctUntilChanged(promptRepositoryImpl.isConfirmationRequired);
        final ReadonlyStateFlow readonlyStateFlow = promptRepositoryImpl.promptInfo;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4b
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        android.hardware.biometrics.PromptInfo r5 = (android.hardware.biometrics.PromptInfo) r5
                        if (r5 == 0) goto L3b
                        boolean r5 = com.android.systemui.biometrics.Utils.isDeviceCredentialAllowed(r5)
                        goto L3c
                    L3b:
                        r5 = 0
                    L3c:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4b
                        return r1
                    L4b:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.isCredentialAllowed = distinctUntilChanged;
        this.credentialKind = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(combine, distinctUntilChanged, new PromptSelectorInteractorImpl$credentialKind$1(this, null));
        this.sensorType = ((FingerprintPropertyRepositoryImpl) fingerprintPropertyRepository).sensorType;
    }

    public final void resetPrompt(long j) {
        PromptRepositoryImpl promptRepositoryImpl = (PromptRepositoryImpl) this.promptRepository;
        StateFlowImpl stateFlowImpl = promptRepositoryImpl._requestId;
        Long l = (Long) stateFlowImpl.getValue();
        if (l == null || j != l.longValue()) {
            Log.w("PromptRepositoryImpl", "Ignoring unsetPrompt - requestId mismatch");
            return;
        }
        promptRepositoryImpl._promptInfo.setValue(null);
        promptRepositoryImpl._userId.setValue(null);
        stateFlowImpl.setValue(null);
        promptRepositoryImpl._challenge.setValue(null);
        promptRepositoryImpl._promptKind.setValue(PromptKind.None.INSTANCE);
        promptRepositoryImpl._opPackageName.setValue(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setPrompt(android.hardware.biometrics.PromptInfo r4, int r5, long r6, com.android.systemui.biometrics.shared.model.BiometricModalities r8, long r9, java.lang.String r11, boolean r12, boolean r13) {
        /*
            r3 = this;
            kotlinx.coroutines.flow.ReadonlyStateFlow r0 = r3.promptKind
            kotlinx.coroutines.flow.StateFlow r0 = r0.$$delegate_0
            java.lang.Object r0 = r0.getValue()
            com.android.systemui.biometrics.shared.model.PromptKind r0 = (com.android.systemui.biometrics.shared.model.PromptKind) r0
            boolean r0 = r0.isCredential()
            boolean r1 = com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.Flags.customBiometricPrompt()
            if (r1 == 0) goto L38
            com.android.systemui.Flags.constraintBp()
            int r1 = com.android.systemui.biometrics.Utils.$r8$clinit
            int r1 = r4.getAuthenticators()
            r1 = r1 & 255(0xff, float:3.57E-43)
            if (r1 == 0) goto L22
            goto L38
        L22:
            boolean r1 = com.android.systemui.biometrics.Utils.isDeviceCredentialAllowed(r4)
            if (r1 == 0) goto L38
            android.hardware.biometrics.PromptContentView r1 = r4.getContentView()
            if (r1 == 0) goto L38
            boolean r1 = r4.isContentViewMoreOptionsButtonUsed()
            if (r1 != 0) goto L38
            if (r0 != 0) goto L38
            r0 = 1
            goto L39
        L38:
            r0 = 0
        L39:
            com.android.systemui.biometrics.shared.model.PromptKind$None r1 = com.android.systemui.biometrics.shared.model.PromptKind.None.INSTANCE
            r2 = 0
            if (r12 == 0) goto L45
            com.android.internal.widget.LockPatternUtils r8 = r3.lockPatternUtils
            com.android.systemui.biometrics.shared.model.PromptKind r1 = com.android.systemui.biometrics.Utils.getCredentialType(r8, r5)
            goto L8d
        L45:
            int r12 = com.android.systemui.biometrics.Utils.$r8$clinit
            int r12 = r4.getAuthenticators()
            r12 = r12 & 255(0xff, float:3.57E-43)
            if (r12 == 0) goto L50
            goto L52
        L50:
            if (r0 == 0) goto L81
        L52:
            if (r13 == 0) goto L79
            com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor r12 = r3.displayStateInteractor
            com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl r12 = (com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl) r12
            kotlinx.coroutines.flow.ReadonlyStateFlow r12 = r12.isLargeScreen
            kotlinx.coroutines.flow.StateFlow r12 = r12.$$delegate_0
            java.lang.Object r12 = r12.getValue()
            java.lang.Boolean r12 = (java.lang.Boolean) r12
            boolean r12 = r12.booleanValue()
            if (r12 == 0) goto L6b
            com.android.systemui.biometrics.shared.model.PromptKind$Biometric$PaneType r12 = com.android.systemui.biometrics.shared.model.PromptKind.Biometric.PaneType.ONE_PANE_LARGE_SCREEN_LANDSCAPE
            goto L72
        L6b:
            if (r0 == 0) goto L70
            com.android.systemui.biometrics.shared.model.PromptKind$Biometric$PaneType r12 = com.android.systemui.biometrics.shared.model.PromptKind.Biometric.PaneType.ONE_PANE_NO_SENSOR_LANDSCAPE
            goto L72
        L70:
            com.android.systemui.biometrics.shared.model.PromptKind$Biometric$PaneType r12 = com.android.systemui.biometrics.shared.model.PromptKind.Biometric.PaneType.TWO_PANE_LANDSCAPE
        L72:
            com.android.systemui.biometrics.shared.model.PromptKind$Biometric r13 = new com.android.systemui.biometrics.shared.model.PromptKind$Biometric
            r13.<init>(r8, r12)
            r1 = r13
            goto L8d
        L79:
            com.android.systemui.biometrics.shared.model.PromptKind$Biometric r12 = new com.android.systemui.biometrics.shared.model.PromptKind$Biometric
            r13 = 2
            r12.<init>(r8, r2, r13, r2)
            r1 = r12
            goto L8d
        L81:
            boolean r8 = com.android.systemui.biometrics.Utils.isDeviceCredentialAllowed(r4)
            if (r8 == 0) goto L8d
            com.android.internal.widget.LockPatternUtils r8 = r3.lockPatternUtils
            com.android.systemui.biometrics.shared.model.PromptKind r1 = com.android.systemui.biometrics.Utils.getCredentialType(r8, r5)
        L8d:
            java.lang.Long r8 = java.lang.Long.valueOf(r9)
            com.android.systemui.biometrics.data.repository.PromptRepository r3 = r3.promptRepository
            com.android.systemui.biometrics.data.repository.PromptRepositoryImpl r3 = (com.android.systemui.biometrics.data.repository.PromptRepositoryImpl) r3
            kotlinx.coroutines.flow.StateFlowImpl r9 = r3._promptKind
            r9.updateState(r2, r1)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            kotlinx.coroutines.flow.StateFlowImpl r9 = r3._userId
            r9.updateState(r2, r5)
            java.lang.Long r5 = java.lang.Long.valueOf(r6)
            kotlinx.coroutines.flow.StateFlowImpl r6 = r3._requestId
            r6.updateState(r2, r5)
            kotlinx.coroutines.flow.StateFlowImpl r5 = r3._challenge
            r5.updateState(r2, r8)
            kotlinx.coroutines.flow.StateFlowImpl r5 = r3._promptInfo
            r5.updateState(r2, r4)
            kotlinx.coroutines.flow.StateFlowImpl r3 = r3._opPackageName
            r3.updateState(r2, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl.setPrompt(android.hardware.biometrics.PromptInfo, int, long, com.android.systemui.biometrics.shared.model.BiometricModalities, long, java.lang.String, boolean, boolean):void");
    }
}
