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
import kotlin.ResultKt;
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
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3Combine = FlowKt.combine(promptRepositoryImpl.promptInfo, promptRepositoryImpl.challenge, promptRepositoryImpl.userId, promptRepositoryImpl.promptKind, promptRepositoryImpl.opPackageName, new PromptSelectorInteractorImpl$prompt$1(this, null));
        this.prompt = flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3Combine;
        this.promptKind = promptRepositoryImpl.promptKind;
        this.isConfirmationRequired = FlowKt.distinctUntilChanged(promptRepositoryImpl.isConfirmationRequired);
        final ReadonlyStateFlow readonlyStateFlow = promptRepositoryImpl.promptInfo;
        Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        PromptInfo promptInfo = (PromptInfo) obj;
                        Boolean boolValueOf = Boolean.valueOf(promptInfo != null ? Utils.isDeviceCredentialAllowed(promptInfo) : false);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.isCredentialAllowed = flowDistinctUntilChanged;
        this.credentialKind = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3Combine, flowDistinctUntilChanged, new PromptSelectorInteractorImpl$credentialKind$1(this, null));
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
        boolean zIsCredential = ((PromptKind) this.promptKind.$$delegate_0.getValue()).isCredential();
        int i2 = Utils.$r8$clinit;
        boolean z3 = ((promptInfo.getAuthenticators() & 255) != 0 || !Utils.isDeviceCredentialAllowed(promptInfo) || promptInfo.getContentView() == null || promptInfo.isContentViewMoreOptionsButtonUsed() || zIsCredential) ? false : true;
        Object biometric = PromptKind.None.INSTANCE;
        if (z) {
            biometric = Utils.getCredentialType(this.lockPatternUtils, credentialOwnerProfile);
        } else if ((promptInfo.getAuthenticators() & 255) == 0 && !z3) {
            if (Utils.isDeviceCredentialAllowed(promptInfo)) {
                biometric = Utils.getCredentialType(this.lockPatternUtils, credentialOwnerProfile);
            }
        } else if (z2) {
            biometric = new PromptKind.Biometric(biometricModalities, ((Boolean) ((DisplayStateInteractorImpl) this.displayStateInteractor).isLargeScreen.$$delegate_0.getValue()).booleanValue() ? PromptKind.Biometric.PaneType.ONE_PANE_LARGE_SCREEN_LANDSCAPE : z3 ? PromptKind.Biometric.PaneType.ONE_PANE_NO_SENSOR_LANDSCAPE : PromptKind.Biometric.PaneType.TWO_PANE_LANDSCAPE);
        } else {
            biometric = new PromptKind.Biometric(biometricModalities, null, 2, null);
        }
        Long lValueOf = Long.valueOf(j2);
        PromptRepositoryImpl promptRepositoryImpl = (PromptRepositoryImpl) this.promptRepository;
        promptRepositoryImpl._promptKind.setValue(biometric);
        promptRepositoryImpl._userId.updateState(null, Integer.valueOf(i));
        promptRepositoryImpl._requestId.updateState(null, Long.valueOf(j));
        promptRepositoryImpl._challenge.updateState(null, lValueOf);
        promptRepositoryImpl._promptInfo.updateState(null, promptInfo);
        promptRepositoryImpl._opPackageName.setValue(str);
    }
}
