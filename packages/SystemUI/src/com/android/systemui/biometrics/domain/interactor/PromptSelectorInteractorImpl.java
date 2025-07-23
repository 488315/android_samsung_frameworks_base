package com.android.systemui.biometrics.domain.interactor;

import android.hardware.biometrics.PromptInfo;
import android.util.Log;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.biometrics.Utils;
import com.android.systemui.biometrics.data.repository.FingerprintPropertyRepository;
import com.android.systemui.biometrics.data.repository.FingerprintPropertyRepositoryImpl;
import com.android.systemui.biometrics.data.repository.PromptRepository;
import com.android.systemui.biometrics.data.repository.PromptRepositoryImpl;
import com.android.systemui.biometrics.shared.model.BiometricModalities;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PromptSelectorInteractorImpl implements PromptSelectorInteractor {
    public final CredentialInteractor credentialInteractor;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 credentialKind;
    public final DisplayStateInteractor displayStateInteractor;
    public final StateFlow fingerprintSensorType;
    public final Flow isConfirmationRequired;
    public final Flow isCredentialAllowed;
    public final LockPatternUtils lockPatternUtils;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 prompt;
    public final ReadonlyStateFlow promptKind;
    public final PromptRepository promptRepository;

    public PromptSelectorInteractorImpl(FingerprintPropertyRepository fingerprintPropertyRepository, DisplayStateInteractor displayStateInteractor, CredentialInteractor credentialInteractor, PromptRepository promptRepository, LockPatternUtils lockPatternUtils) {
        this.displayStateInteractor = displayStateInteractor;
        this.credentialInteractor = credentialInteractor;
        this.promptRepository = promptRepository;
        this.lockPatternUtils = lockPatternUtils;
        PromptRepositoryImpl promptRepositoryImpl = (PromptRepositoryImpl) promptRepository;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 combine = FlowKt.combine(promptRepositoryImpl.promptInfo, promptRepositoryImpl.challenge, promptRepositoryImpl.userId, promptRepositoryImpl.promptKind, promptRepositoryImpl.opPackageName, new PromptSelectorInteractorImpl$prompt$1(this, null));
        this.prompt = combine;
        this.promptKind = promptRepositoryImpl.promptKind;
        this.isConfirmationRequired = FlowKt.distinctUntilChanged(promptRepositoryImpl.isConfirmationRequired);
        final ReadonlyStateFlow readonlyStateFlow = promptRepositoryImpl.promptInfo;
        Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
        this.fingerprintSensorType = ((FingerprintPropertyRepositoryImpl) fingerprintPropertyRepository).sensorType;
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

    public final void setPrompt(PromptInfo promptInfo, int i, long j, BiometricModalities biometricModalities, long j2, String str, boolean z, boolean z2) {
        int credentialOwnerProfile = ((CredentialInteractorImpl) this.credentialInteractor).userManager.getCredentialOwnerProfile(i);
        boolean isCredential = ((PromptKind) this.promptKind.$$delegate_0.getValue()).isCredential();
        int i2 = Utils.$r8$clinit;
        boolean z3 = ((promptInfo.getAuthenticators() & 255) != 0 || !Utils.isDeviceCredentialAllowed(promptInfo) || promptInfo.getContentView() == null || promptInfo.isContentViewMoreOptionsButtonUsed() || isCredential) ? false : true;
        Object obj = PromptKind.None.INSTANCE;
        if (z) {
            obj = Utils.getCredentialType(this.lockPatternUtils, credentialOwnerProfile);
        } else if ((promptInfo.getAuthenticators() & 255) == 0 && !z3) {
            if (Utils.isDeviceCredentialAllowed(promptInfo)) {
                obj = Utils.getCredentialType(this.lockPatternUtils, credentialOwnerProfile);
            }
        } else if (z2) {
            obj = new PromptKind.Biometric(biometricModalities, ((Boolean) ((DisplayStateInteractorImpl) this.displayStateInteractor).isLargeScreen.$$delegate_0.getValue()).booleanValue() ? PromptKind.Biometric.PaneType.ONE_PANE_LARGE_SCREEN_LANDSCAPE : z3 ? PromptKind.Biometric.PaneType.ONE_PANE_NO_SENSOR_LANDSCAPE : PromptKind.Biometric.PaneType.TWO_PANE_LANDSCAPE);
        } else {
            obj = new PromptKind.Biometric(biometricModalities, null, 2, null);
        }
        Long valueOf = Long.valueOf(j2);
        PromptRepositoryImpl promptRepositoryImpl = (PromptRepositoryImpl) this.promptRepository;
        promptRepositoryImpl._promptKind.setValue(obj);
        promptRepositoryImpl._userId.updateState(null, Integer.valueOf(i));
        promptRepositoryImpl._requestId.updateState(null, Long.valueOf(j));
        promptRepositoryImpl._challenge.updateState(null, valueOf);
        promptRepositoryImpl._promptInfo.updateState(null, promptInfo);
        promptRepositoryImpl._opPackageName.setValue(str);
    }
}
