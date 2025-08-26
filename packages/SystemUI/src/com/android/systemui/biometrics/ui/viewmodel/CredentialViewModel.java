package com.android.systemui.biometrics.ui.viewmodel;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.biometrics.domain.interactor.CredentialStatus;
import com.android.systemui.biometrics.domain.interactor.CredentialStatus$Success$Verified;
import com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
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

    /* renamed from: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$checkCredential$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
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
            return CredentialViewModel.this.checkCredential((CharSequence) null, (CredentialHeaderViewModel) null, this);
        }
    }

    /* renamed from: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$checkCredential$2, reason: invalid class name */
    final class AnonymousClass2 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass2(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CredentialViewModel.this.checkCredential((List) null, (CredentialHeaderViewModel) null, this);
        }
    }

    /* renamed from: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$checkCredential$3, reason: invalid class name */
    final class AnonymousClass3 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass3(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CredentialViewModel.this.checkCredential(null, this);
        }
    }

    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$1] */
    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$2] */
    public CredentialViewModel(Context context, PromptCredentialInteractor promptCredentialInteractor) {
        this.applicationContext = context;
        this.credentialInteractor = promptCredentialInteractor;
        final Flow flow = promptCredentialInteractor.prompt;
        this.header = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$filterIsInstance$1

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
                        if (obj instanceof BiometricPromptRequest.Credential) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, promptCredentialInteractor.showTitleOnly, new CredentialViewModel$header$1(this, null));
        final Flow flow2 = promptCredentialInteractor.prompt;
        this.inputFlags = new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$1

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
                        Integer num = ((BiometricPromptRequest.Credential) obj) instanceof BiometricPromptRequest.Credential.Pin ? new Integer(18) : null;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.inputBoxContentDescription = new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel$special$$inlined$map$2

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
                        BiometricPromptRequest.Credential credential = (BiometricPromptRequest.Credential) obj;
                        Integer num = credential instanceof BiometricPromptRequest.Credential.Pin ? new Integer(R.string.keyguard_accessibility_pin_area) : credential instanceof BiometricPromptRequest.Credential.Password ? new Integer(R.string.keyguard_accessibility_password) : null;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.stealthMode = new CredentialViewModel$special$$inlined$map$3(flow2);
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.TRUE);
        this._animateContents = stateFlowImplMutableStateFlow;
        this.animateContents = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.errorMessage = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(promptCredentialInteractor.verificationError, flow2, new CredentialViewModel$errorMessage$1(this, null));
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._validatedAttestation = sharedFlowImplMutableSharedFlow$default;
        this.validatedAttestation = FlowKt.asSharedFlow(sharedFlowImplMutableSharedFlow$default);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(new RemainingAttempts(null, null, 3, null));
        this._remainingAttempts = stateFlowImplMutableStateFlow2;
        this.remainingAttempts = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0065, code lost:
    
        if (r2.emit(r8, r0) == r1) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0081, code lost:
    
        if (r2.emit(null, r0) == r1) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00a5, code lost:
    
        if (r2.emit(null, r0) == r1) goto L37;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object checkCredential(CredentialStatus credentialStatus, ContinuationImpl continuationImpl) {
        AnonymousClass3 anonymousClass3;
        if (continuationImpl instanceof AnonymousClass3) {
            anonymousClass3 = (AnonymousClass3) continuationImpl;
            int i = anonymousClass3.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass3.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass3 = new AnonymousClass3(continuationImpl);
            }
        }
        Object obj = anonymousClass3.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass3.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            boolean z = credentialStatus instanceof CredentialStatus$Success$Verified;
            SharedFlowImpl sharedFlowImpl = this._validatedAttestation;
            if (z) {
                byte[] bArr = ((CredentialStatus$Success$Verified) credentialStatus).hat;
                anonymousClass3.L$0 = this;
                anonymousClass3.label = 1;
            } else if (credentialStatus instanceof CredentialStatus.Fail.Error) {
                anonymousClass3.L$0 = this;
                anonymousClass3.L$1 = credentialStatus;
                anonymousClass3.label = 2;
            } else {
                if (!(credentialStatus instanceof CredentialStatus.Fail.Throttled)) {
                    throw new NoWhenBranchMatchedException();
                }
                anonymousClass3.L$0 = this;
                anonymousClass3.label = 3;
            }
            return coroutineSingletons;
        }
        if (i2 == 1) {
            this = (CredentialViewModel) anonymousClass3.L$0;
            ResultKt.throwOnFailure(obj);
            this._remainingAttempts.updateState(null, new RemainingAttempts(null, null, 3, null));
        } else if (i2 == 2) {
            credentialStatus = (CredentialStatus) anonymousClass3.L$1;
            this = (CredentialViewModel) anonymousClass3.L$0;
            ResultKt.throwOnFailure(obj);
            StateFlowImpl stateFlowImpl = this._remainingAttempts;
            CredentialStatus.Fail.Error error = (CredentialStatus.Fail.Error) credentialStatus;
            Integer num = error.remainingAttempts;
            String str = error.urgentMessage;
            if (str == null) {
                str = "";
            }
            stateFlowImpl.updateState(null, new RemainingAttempts(num, str));
        } else {
            if (i2 != 3) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (CredentialViewModel) anonymousClass3.L$0;
            ResultKt.throwOnFailure(obj);
            this._remainingAttempts.updateState(null, new RemainingAttempts(null, null, 3, null));
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x005f, code lost:
    
        if (r8.checkCredential((com.android.systemui.biometrics.domain.interactor.CredentialStatus) r11, r5) == r0) goto L22;
     */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object checkCredential(CharSequence charSequence, CredentialHeaderViewModel credentialHeaderViewModel, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        AnonymousClass1 anonymousClass12 = anonymousClass1;
        Object objCheckCredential$default = anonymousClass12.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass12.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objCheckCredential$default);
            BiometricPromptRequest.Credential credential = ((BiometricPromptHeaderViewModelImpl) credentialHeaderViewModel).request;
            anonymousClass12.L$0 = this;
            anonymousClass12.label = 1;
            objCheckCredential$default = PromptCredentialInteractor.checkCredential$default(this.credentialInteractor, credential, charSequence, null, anonymousClass12, 4);
            if (objCheckCredential$default != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objCheckCredential$default);
            return Unit.INSTANCE;
        }
        this = (CredentialViewModel) anonymousClass12.L$0;
        ResultKt.throwOnFailure(objCheckCredential$default);
        anonymousClass12.L$0 = null;
        anonymousClass12.label = 2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x005f, code lost:
    
        if (r8.checkCredential((com.android.systemui.biometrics.domain.interactor.CredentialStatus) r11, r5) == r0) goto L22;
     */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object checkCredential(List list, CredentialHeaderViewModel credentialHeaderViewModel, ContinuationImpl continuationImpl) {
        AnonymousClass2 anonymousClass2;
        if (continuationImpl instanceof AnonymousClass2) {
            anonymousClass2 = (AnonymousClass2) continuationImpl;
            int i = anonymousClass2.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass2.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass2 = new AnonymousClass2(continuationImpl);
            }
        }
        AnonymousClass2 anonymousClass22 = anonymousClass2;
        Object objCheckCredential$default = anonymousClass22.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass22.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objCheckCredential$default);
            BiometricPromptRequest.Credential credential = ((BiometricPromptHeaderViewModelImpl) credentialHeaderViewModel).request;
            anonymousClass22.L$0 = this;
            anonymousClass22.label = 1;
            objCheckCredential$default = PromptCredentialInteractor.checkCredential$default(this.credentialInteractor, credential, null, list, anonymousClass22, 2);
            if (objCheckCredential$default != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objCheckCredential$default);
            return Unit.INSTANCE;
        }
        this = (CredentialViewModel) anonymousClass22.L$0;
        ResultKt.throwOnFailure(objCheckCredential$default);
        anonymousClass22.L$0 = null;
        anonymousClass22.label = 2;
    }
}
