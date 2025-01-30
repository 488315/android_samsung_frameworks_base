package com.android.systemui.biometrics.ui.viewmodel;

import android.content.Context;
import com.android.systemui.biometrics.domain.interactor.CredentialStatus;
import com.android.systemui.biometrics.domain.interactor.CredentialStatus$Success$Verified;
import com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CredentialViewModel {
    public final StateFlowImpl _animateContents;
    public final StateFlowImpl _remainingAttempts;
    public final SharedFlowImpl _validatedAttestation;
    public final ReadonlyStateFlow animateContents;
    public final Context applicationContext;
    public final PromptCredentialInteractor credentialInteractor;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 errorMessage;
    public final CredentialViewModel$special$$inlined$map$1 header;
    public final CredentialViewModel$special$$inlined$map$2 inputFlags;
    public final ReadonlyStateFlow remainingAttempts;
    public final CredentialViewModel$special$$inlined$map$3 stealthMode;
    public final ReadonlySharedFlow validatedAttestation;

    public CredentialViewModel(Context context, PromptCredentialInteractor promptCredentialInteractor) {
        this.applicationContext = context;
        this.credentialInteractor = promptCredentialInteractor;
        final Flow flow = promptCredentialInteractor.prompt;
        this.header = new CredentialViewModel$special$$inlined$map$1(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$filterIsInstance$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$filterIsInstance$1$2 */
            public final class C11052 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$filterIsInstance$1$2", m277f = "CredentialViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C11052.this.emit(null, this);
                    }
                }

                public C11052(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i2 = anonymousClass1.label;
                        if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                            anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            i = anonymousClass1.label;
                            if (i != 0) {
                                ResultKt.throwOnFailure(obj2);
                                if (obj instanceof BiometricPromptRequest.Credential) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(continuation);
                    Object obj22 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new C11052(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, this);
        this.inputFlags = new CredentialViewModel$special$$inlined$map$2(flow);
        this.stealthMode = new CredentialViewModel$special$$inlined$map$3(flow);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.TRUE);
        this._animateContents = MutableStateFlow;
        this.animateContents = FlowKt.asStateFlow(MutableStateFlow);
        this.errorMessage = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(promptCredentialInteractor.verificationError, flow, new CredentialViewModel$errorMessage$1(this, null));
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._validatedAttestation = MutableSharedFlow$default;
        this.validatedAttestation = new ReadonlySharedFlow(MutableSharedFlow$default, null);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(new RemainingAttempts(null, null, 3, null));
        this._remainingAttempts = MutableStateFlow2;
        this.remainingAttempts = FlowKt.asStateFlow(MutableStateFlow2);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object checkCredential(CredentialStatus credentialStatus, Continuation continuation) {
        CredentialViewModel$checkCredential$3 credentialViewModel$checkCredential$3;
        int i;
        String str;
        if (continuation instanceof CredentialViewModel$checkCredential$3) {
            credentialViewModel$checkCredential$3 = (CredentialViewModel$checkCredential$3) continuation;
            int i2 = credentialViewModel$checkCredential$3.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                credentialViewModel$checkCredential$3.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = credentialViewModel$checkCredential$3.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = credentialViewModel$checkCredential$3.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    boolean z = credentialStatus instanceof CredentialStatus$Success$Verified;
                    SharedFlowImpl sharedFlowImpl = this._validatedAttestation;
                    if (z) {
                        byte[] bArr = ((CredentialStatus$Success$Verified) credentialStatus).hat;
                        credentialViewModel$checkCredential$3.L$0 = this;
                        credentialViewModel$checkCredential$3.label = 1;
                        if (sharedFlowImpl.emit(bArr, credentialViewModel$checkCredential$3) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        this._remainingAttempts.setValue(new RemainingAttempts(null, null, 3, null));
                    } else if (credentialStatus instanceof CredentialStatus.Fail.Error) {
                        credentialViewModel$checkCredential$3.L$0 = this;
                        credentialViewModel$checkCredential$3.L$1 = credentialStatus;
                        credentialViewModel$checkCredential$3.label = 2;
                        if (sharedFlowImpl.emit(null, credentialViewModel$checkCredential$3) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        StateFlowImpl stateFlowImpl = this._remainingAttempts;
                        CredentialStatus.Fail.Error error = (CredentialStatus.Fail.Error) credentialStatus;
                        Integer num = error.remainingAttempts;
                        str = error.urgentMessage;
                        if (str == null) {
                        }
                        stateFlowImpl.setValue(new RemainingAttempts(num, str));
                    } else if (credentialStatus instanceof CredentialStatus.Fail.Throttled) {
                        credentialViewModel$checkCredential$3.L$0 = this;
                        credentialViewModel$checkCredential$3.label = 3;
                        if (sharedFlowImpl.emit(null, credentialViewModel$checkCredential$3) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        this._remainingAttempts.setValue(new RemainingAttempts(null, null, 3, null));
                    }
                } else if (i == 1) {
                    this = (CredentialViewModel) credentialViewModel$checkCredential$3.L$0;
                    ResultKt.throwOnFailure(obj);
                    this._remainingAttempts.setValue(new RemainingAttempts(null, null, 3, null));
                } else if (i == 2) {
                    credentialStatus = (CredentialStatus) credentialViewModel$checkCredential$3.L$1;
                    this = (CredentialViewModel) credentialViewModel$checkCredential$3.L$0;
                    ResultKt.throwOnFailure(obj);
                    StateFlowImpl stateFlowImpl2 = this._remainingAttempts;
                    CredentialStatus.Fail.Error error2 = (CredentialStatus.Fail.Error) credentialStatus;
                    Integer num2 = error2.remainingAttempts;
                    str = error2.urgentMessage;
                    if (str == null) {
                        str = "";
                    }
                    stateFlowImpl2.setValue(new RemainingAttempts(num2, str));
                } else {
                    if (i != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    this = (CredentialViewModel) credentialViewModel$checkCredential$3.L$0;
                    ResultKt.throwOnFailure(obj);
                    this._remainingAttempts.setValue(new RemainingAttempts(null, null, 3, null));
                }
                return Unit.INSTANCE;
            }
        }
        credentialViewModel$checkCredential$3 = new CredentialViewModel$checkCredential$3(this, continuation);
        Object obj2 = credentialViewModel$checkCredential$3.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = credentialViewModel$checkCredential$3.label;
        if (i != 0) {
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0060 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object checkCredential(CharSequence charSequence, CredentialHeaderViewModel credentialHeaderViewModel, Continuation continuation) {
        CredentialViewModel$checkCredential$1 credentialViewModel$checkCredential$1;
        Object obj;
        CoroutineSingletons coroutineSingletons;
        int i;
        if (continuation instanceof CredentialViewModel$checkCredential$1) {
            credentialViewModel$checkCredential$1 = (CredentialViewModel$checkCredential$1) continuation;
            int i2 = credentialViewModel$checkCredential$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                credentialViewModel$checkCredential$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                obj = credentialViewModel$checkCredential$1.result;
                coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = credentialViewModel$checkCredential$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    PromptCredentialInteractor promptCredentialInteractor = this.credentialInteractor;
                    BiometricPromptRequest.Credential credential = ((BiometricPromptHeaderViewModelImpl) credentialHeaderViewModel).request;
                    credentialViewModel$checkCredential$1.L$0 = this;
                    credentialViewModel$checkCredential$1.label = 1;
                    obj = PromptCredentialInteractor.checkCredential$default(promptCredentialInteractor, credential, charSequence, null, credentialViewModel$checkCredential$1, 4);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    this = (CredentialViewModel) credentialViewModel$checkCredential$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                credentialViewModel$checkCredential$1.L$0 = null;
                credentialViewModel$checkCredential$1.label = 2;
                if (this.checkCredential((CredentialStatus) obj, credentialViewModel$checkCredential$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            }
        }
        credentialViewModel$checkCredential$1 = new CredentialViewModel$checkCredential$1(this, continuation);
        obj = credentialViewModel$checkCredential$1.result;
        coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = credentialViewModel$checkCredential$1.label;
        if (i != 0) {
        }
        credentialViewModel$checkCredential$1.L$0 = null;
        credentialViewModel$checkCredential$1.label = 2;
        if (this.checkCredential((CredentialStatus) obj, credentialViewModel$checkCredential$1) == coroutineSingletons) {
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0060 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object checkCredential(List list, CredentialHeaderViewModel credentialHeaderViewModel, Continuation continuation) {
        CredentialViewModel$checkCredential$2 credentialViewModel$checkCredential$2;
        Object obj;
        CoroutineSingletons coroutineSingletons;
        int i;
        if (continuation instanceof CredentialViewModel$checkCredential$2) {
            credentialViewModel$checkCredential$2 = (CredentialViewModel$checkCredential$2) continuation;
            int i2 = credentialViewModel$checkCredential$2.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                credentialViewModel$checkCredential$2.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                obj = credentialViewModel$checkCredential$2.result;
                coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = credentialViewModel$checkCredential$2.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    PromptCredentialInteractor promptCredentialInteractor = this.credentialInteractor;
                    BiometricPromptRequest.Credential credential = ((BiometricPromptHeaderViewModelImpl) credentialHeaderViewModel).request;
                    credentialViewModel$checkCredential$2.L$0 = this;
                    credentialViewModel$checkCredential$2.label = 1;
                    obj = PromptCredentialInteractor.checkCredential$default(promptCredentialInteractor, credential, null, list, credentialViewModel$checkCredential$2, 2);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    this = (CredentialViewModel) credentialViewModel$checkCredential$2.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                credentialViewModel$checkCredential$2.L$0 = null;
                credentialViewModel$checkCredential$2.label = 2;
                if (this.checkCredential((CredentialStatus) obj, credentialViewModel$checkCredential$2) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            }
        }
        credentialViewModel$checkCredential$2 = new CredentialViewModel$checkCredential$2(this, continuation);
        obj = credentialViewModel$checkCredential$2.result;
        coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = credentialViewModel$checkCredential$2.label;
        if (i != 0) {
        }
        credentialViewModel$checkCredential$2.L$0 = null;
        credentialViewModel$checkCredential$2.label = 2;
        if (this.checkCredential((CredentialStatus) obj, credentialViewModel$checkCredential$2) == coroutineSingletons) {
        }
        return Unit.INSTANCE;
    }
}
