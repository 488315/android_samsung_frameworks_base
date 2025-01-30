package com.android.systemui.biometrics.ui.viewmodel;

import android.util.Log;
import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractor;
import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl;
import com.android.systemui.biometrics.domain.model.BiometricModalities;
import com.android.systemui.biometrics.domain.model.BiometricModality;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import com.android.systemui.biometrics.ui.viewmodel.PromptMessage;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PromptViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _canTryAgainNow;
    public final StateFlowImpl _fingerprintStartMode;
    public final StateFlowImpl _forceLargeSize;
    public final StateFlowImpl _forceMediumSize;
    public final StateFlowImpl _isAuthenticated;
    public final StateFlowImpl _isAuthenticating;
    public final StateFlowImpl _legacyState;
    public final StateFlowImpl _message;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 credentialKind;
    public final Flow description;
    public final ReadonlyStateFlow fingerprintStartMode;
    public final PromptSelectorInteractor interactor;
    public final ReadonlyStateFlow isAuthenticated;
    public final ReadonlyStateFlow isAuthenticating;
    public final Flow isCancelButtonVisible;
    public final Flow isConfirmButtonVisible;
    public final Flow isConfirmationRequested;
    public final Flow isCredentialButtonVisible;
    public final Flow isIndicatorMessageVisible;
    public final Flow isNegativeButtonVisible;
    public final PromptViewModel$special$$inlined$map$3 isRetrySupported;
    public final Flow isTryAgainButtonVisible;
    public final ReadonlyStateFlow legacyState;
    public final ReadonlyStateFlow message;
    public Job messageJob;
    public final Flow modalities;
    public final PromptViewModel$special$$inlined$map$2 negativeButtonText;
    public final Flow size;
    public final Flow subtitle;
    public final Flow title;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r10v1, types: [com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$3, kotlinx.coroutines.flow.Flow] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$2] */
    public PromptViewModel(PromptSelectorInteractor promptSelectorInteractor) {
        this.interactor = promptSelectorInteractor;
        PromptSelectorInteractorImpl promptSelectorInteractorImpl = (PromptSelectorInteractorImpl) promptSelectorInteractor;
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 = promptSelectorInteractorImpl.prompt;
        final Flow distinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$1$2 */
            public final class C11092 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$1$2", m277f = "PromptViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C11092.this.emit(null, this);
                    }
                }

                public C11092(FlowCollector flowCollector) {
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
                    BiometricModalities biometricModalities;
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
                                BiometricPromptRequest.Biometric biometric = (BiometricPromptRequest.Biometric) obj;
                                if (biometric == null || (biometricModalities = biometric.modalities) == null) {
                                    biometricModalities = new BiometricModalities(null, null, 3, null);
                                }
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(biometricModalities, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
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
                Object collect = Flow.this.collect(new C11092(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.modalities = distinctUntilChanged;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(1);
        this._legacyState = MutableStateFlow;
        this.legacyState = FlowKt.asStateFlow(MutableStateFlow);
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._isAuthenticating = MutableStateFlow2;
        this.isAuthenticating = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(new PromptAuthState(false, null, false, 0L, 14, null));
        this._isAuthenticated = MutableStateFlow3;
        ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(MutableStateFlow3);
        this.isAuthenticated = asStateFlow;
        this.isConfirmationRequested = promptSelectorInteractorImpl.isConfirmationRequested;
        this.credentialKind = promptSelectorInteractorImpl.credentialKind;
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$22 = promptSelectorInteractorImpl.prompt;
        this.negativeButtonText = new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$2$2 */
            public final class C11102 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$2$2", m277f = "PromptViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C11102.this.emit(null, this);
                    }
                }

                public C11102(FlowCollector flowCollector) {
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
                    String str;
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
                                BiometricPromptRequest.Biometric biometric = (BiometricPromptRequest.Biometric) obj;
                                if (biometric == null || (str = biometric.negativeButtonText) == null) {
                                    str = "";
                                }
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(str, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
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
                Object collect = Flow.this.collect(new C11102(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(PromptMessage.Empty.INSTANCE);
        this._message = MutableStateFlow4;
        ReadonlyStateFlow asStateFlow2 = FlowKt.asStateFlow(MutableStateFlow4);
        this.message = asStateFlow2;
        ?? r10 = new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$3

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$3$2 */
            public final class C11112 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$3$2", m277f = "PromptViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C11112.this.emit(null, this);
                    }
                }

                public C11112(FlowCollector flowCollector) {
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
                                Boolean valueOf = Boolean.valueOf(((BiometricModalities) obj).faceProperties != null);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
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
                Object collect = Flow.this.collect(new C11112(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.isRetrySupported = r10;
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(FingerprintStartMode.Pending);
        this._fingerprintStartMode = MutableStateFlow5;
        ReadonlyStateFlow asStateFlow3 = FlowKt.asStateFlow(MutableStateFlow5);
        this.fingerprintStartMode = asStateFlow3;
        StateFlowImpl MutableStateFlow6 = StateFlowKt.MutableStateFlow(bool);
        this._forceLargeSize = MutableStateFlow6;
        StateFlowImpl MutableStateFlow7 = StateFlowKt.MutableStateFlow(bool);
        this._forceMediumSize = MutableStateFlow7;
        Flow distinctUntilChanged2 = FlowKt.distinctUntilChanged(FlowKt.combine(MutableStateFlow6, MutableStateFlow7, distinctUntilChanged, promptSelectorInteractorImpl.isConfirmationRequested, asStateFlow3, new PromptViewModel$size$1(null)));
        this.size = distinctUntilChanged2;
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$23 = promptSelectorInteractorImpl.prompt;
        this.title = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$4

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$4$2 */
            public final class C11122 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$4$2", m277f = "PromptViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$4$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C11122.this.emit(null, this);
                    }
                }

                public C11122(FlowCollector flowCollector) {
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
                    String str;
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
                                BiometricPromptRequest.Biometric biometric = (BiometricPromptRequest.Biometric) obj;
                                if (biometric == null || (str = biometric.title) == null) {
                                    str = "";
                                }
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(str, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
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
                Object collect = Flow.this.collect(new C11122(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$24 = promptSelectorInteractorImpl.prompt;
        this.subtitle = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$5

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$5$2 */
            public final class C11132 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$5$2", m277f = "PromptViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$5$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C11132.this.emit(null, this);
                    }
                }

                public C11132(FlowCollector flowCollector) {
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
                    String str;
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
                                BiometricPromptRequest.Biometric biometric = (BiometricPromptRequest.Biometric) obj;
                                if (biometric == null || (str = biometric.subtitle) == null) {
                                    str = "";
                                }
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(str, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
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
                Object collect = Flow.this.collect(new C11132(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$25 = promptSelectorInteractorImpl.prompt;
        this.description = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$6

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$6$2 */
            public final class C11142 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$6$2", m277f = "PromptViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                /* renamed from: com.android.systemui.biometrics.ui.viewmodel.PromptViewModel$special$$inlined$map$6$2$1, reason: invalid class name */
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C11142.this.emit(null, this);
                    }
                }

                public C11142(FlowCollector flowCollector) {
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
                    String str;
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
                                BiometricPromptRequest.Biometric biometric = (BiometricPromptRequest.Biometric) obj;
                                if (biometric == null || (str = biometric.description) == null) {
                                    str = "";
                                }
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(str, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
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
                Object collect = Flow.this.collect(new C11142(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.isIndicatorMessageVisible = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(distinctUntilChanged2, asStateFlow2, new PromptViewModel$isIndicatorMessageVisible$1(null)));
        Flow distinctUntilChanged3 = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(distinctUntilChanged2, asStateFlow, new PromptViewModel$isConfirmButtonVisible$1(null)));
        this.isConfirmButtonVisible = distinctUntilChanged3;
        Flow distinctUntilChanged4 = FlowKt.distinctUntilChanged(FlowKt.combine(distinctUntilChanged2, asStateFlow, promptSelectorInteractorImpl.isCredentialAllowed, new PromptViewModel$isNegativeButtonVisible$1(null)));
        this.isNegativeButtonVisible = distinctUntilChanged4;
        this.isCancelButtonVisible = FlowKt.distinctUntilChanged(FlowKt.combine(distinctUntilChanged2, asStateFlow, distinctUntilChanged4, distinctUntilChanged3, new PromptViewModel$isCancelButtonVisible$1(null)));
        StateFlowImpl MutableStateFlow8 = StateFlowKt.MutableStateFlow(bool);
        this._canTryAgainNow = MutableStateFlow8;
        this.isTryAgainButtonVisible = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.distinctUntilChanged(FlowKt.combine(MutableStateFlow8, distinctUntilChanged2, asStateFlow, r10, new PromptViewModel$canTryAgainNow$1(null))), distinctUntilChanged, new PromptViewModel$isTryAgainButtonVisible$1(null)));
        this.isCredentialButtonVisible = FlowKt.distinctUntilChanged(FlowKt.combine(distinctUntilChanged2, asStateFlow, promptSelectorInteractorImpl.isCredentialAllowed, new PromptViewModel$isCredentialButtonVisible$1(null)));
    }

    public static void showAuthenticating$default(PromptViewModel promptViewModel, String str, boolean z, int i) {
        if ((i & 1) != 0) {
            str = "";
        }
        if ((i & 2) != 0) {
            z = false;
        }
        StateFlowImpl stateFlowImpl = promptViewModel._isAuthenticated;
        if (((PromptAuthState) stateFlowImpl.getValue()).isAuthenticated) {
            Log.w("PromptViewModel", "Cannot show authenticating after authenticated");
            return;
        }
        promptViewModel._isAuthenticating.setValue(Boolean.TRUE);
        stateFlowImpl.setValue(new PromptAuthState(false, null, false, 0L, 14, null));
        promptViewModel._message.setValue(StringsKt__StringsJVMKt.isBlank(str) ? PromptMessage.Empty.INSTANCE : new PromptMessage.Help(str));
        promptViewModel._legacyState.setValue(2);
        if (z) {
            promptViewModel._canTryAgainNow.setValue(Boolean.FALSE);
        }
        Job job = promptViewModel.messageJob;
        if (job != null) {
            job.cancel(null);
        }
        promptViewModel.messageJob = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x007e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object needsExplicitConfirmation(BiometricModality biometricModality, Continuation continuation) {
        PromptViewModel$needsExplicitConfirmation$1 promptViewModel$needsExplicitConfirmation$1;
        CoroutineSingletons coroutineSingletons;
        int i;
        Object first;
        PromptViewModel promptViewModel;
        BiometricModalities biometricModalities;
        boolean booleanValue;
        boolean z;
        if (continuation instanceof PromptViewModel$needsExplicitConfirmation$1) {
            promptViewModel$needsExplicitConfirmation$1 = (PromptViewModel$needsExplicitConfirmation$1) continuation;
            int i2 = promptViewModel$needsExplicitConfirmation$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                promptViewModel$needsExplicitConfirmation$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = promptViewModel$needsExplicitConfirmation$1.result;
                coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = promptViewModel$needsExplicitConfirmation$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    promptViewModel$needsExplicitConfirmation$1.L$0 = this;
                    promptViewModel$needsExplicitConfirmation$1.L$1 = biometricModality;
                    promptViewModel$needsExplicitConfirmation$1.label = 1;
                    obj = FlowKt.first(this.modalities, promptViewModel$needsExplicitConfirmation$1);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        if (i != 2) {
                            if (i != 3) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            z = promptViewModel$needsExplicitConfirmation$1.Z$0;
                            ResultKt.throwOnFailure(obj);
                            return Boolean.valueOf(obj == FingerprintStartMode.Pending || z);
                        }
                        biometricModalities = (BiometricModalities) promptViewModel$needsExplicitConfirmation$1.L$2;
                        biometricModality = (BiometricModality) promptViewModel$needsExplicitConfirmation$1.L$1;
                        promptViewModel = (PromptViewModel) promptViewModel$needsExplicitConfirmation$1.L$0;
                        ResultKt.throwOnFailure(obj);
                        booleanValue = ((Boolean) obj).booleanValue();
                        if (biometricModalities.getHasFaceAndFingerprint() || biometricModality != BiometricModality.Face) {
                            if (biometricModalities.faceProperties == null) {
                                if (!(biometricModalities.fingerprintProperties != null)) {
                                    r3 = true;
                                }
                            }
                            return !r3 ? Boolean.valueOf(booleanValue) : Boolean.FALSE;
                        }
                        ReadonlyStateFlow readonlyStateFlow = promptViewModel.fingerprintStartMode;
                        promptViewModel$needsExplicitConfirmation$1.L$0 = null;
                        promptViewModel$needsExplicitConfirmation$1.L$1 = null;
                        promptViewModel$needsExplicitConfirmation$1.L$2 = null;
                        promptViewModel$needsExplicitConfirmation$1.Z$0 = booleanValue;
                        promptViewModel$needsExplicitConfirmation$1.label = 3;
                        Object first2 = FlowKt.first(readonlyStateFlow, promptViewModel$needsExplicitConfirmation$1);
                        if (first2 == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        obj = first2;
                        z = booleanValue;
                        return Boolean.valueOf(obj == FingerprintStartMode.Pending || z);
                    }
                    biometricModality = (BiometricModality) promptViewModel$needsExplicitConfirmation$1.L$1;
                    this = (PromptViewModel) promptViewModel$needsExplicitConfirmation$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                BiometricModalities biometricModalities2 = (BiometricModalities) obj;
                Flow flow = ((PromptSelectorInteractorImpl) this.interactor).isConfirmationRequested;
                promptViewModel$needsExplicitConfirmation$1.L$0 = this;
                promptViewModel$needsExplicitConfirmation$1.L$1 = biometricModality;
                promptViewModel$needsExplicitConfirmation$1.L$2 = biometricModalities2;
                promptViewModel$needsExplicitConfirmation$1.label = 2;
                first = FlowKt.first(flow, promptViewModel$needsExplicitConfirmation$1);
                if (first != coroutineSingletons) {
                    return coroutineSingletons;
                }
                promptViewModel = this;
                biometricModalities = biometricModalities2;
                obj = first;
                booleanValue = ((Boolean) obj).booleanValue();
                if (biometricModalities.getHasFaceAndFingerprint()) {
                }
                if (biometricModalities.faceProperties == null) {
                }
                if (!r3) {
                }
            }
        }
        promptViewModel$needsExplicitConfirmation$1 = new PromptViewModel$needsExplicitConfirmation$1(this, continuation);
        Object obj2 = promptViewModel$needsExplicitConfirmation$1.result;
        coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = promptViewModel$needsExplicitConfirmation$1.label;
        if (i != 0) {
        }
        BiometricModalities biometricModalities22 = (BiometricModalities) obj2;
        Flow flow2 = ((PromptSelectorInteractorImpl) this.interactor).isConfirmationRequested;
        promptViewModel$needsExplicitConfirmation$1.L$0 = this;
        promptViewModel$needsExplicitConfirmation$1.L$1 = biometricModality;
        promptViewModel$needsExplicitConfirmation$1.L$2 = biometricModalities22;
        promptViewModel$needsExplicitConfirmation$1.label = 2;
        first = FlowKt.first(flow2, promptViewModel$needsExplicitConfirmation$1);
        if (first != coroutineSingletons) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object showAuthenticated(BiometricModality biometricModality, long j, String str, Continuation continuation) {
        PromptViewModel$showAuthenticated$1 promptViewModel$showAuthenticated$1;
        int i;
        String str2;
        long j2;
        Object needsExplicitConfirmation;
        Job job;
        PromptViewModel promptViewModel = this;
        BiometricModality biometricModality2 = biometricModality;
        if (continuation instanceof PromptViewModel$showAuthenticated$1) {
            promptViewModel$showAuthenticated$1 = (PromptViewModel$showAuthenticated$1) continuation;
            int i2 = promptViewModel$showAuthenticated$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                promptViewModel$showAuthenticated$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = promptViewModel$showAuthenticated$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = promptViewModel$showAuthenticated$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    if (((PromptAuthState) promptViewModel._isAuthenticated.getValue()).isAuthenticated) {
                        Log.w("PromptViewModel", "Cannot show authenticated after authenticated");
                        return Unit.INSTANCE;
                    }
                    promptViewModel._isAuthenticating.setValue(Boolean.FALSE);
                    promptViewModel$showAuthenticated$1.L$0 = promptViewModel;
                    promptViewModel$showAuthenticated$1.L$1 = biometricModality2;
                    str2 = str;
                    promptViewModel$showAuthenticated$1.L$2 = str2;
                    j2 = j;
                    promptViewModel$showAuthenticated$1.J$0 = j2;
                    promptViewModel$showAuthenticated$1.label = 1;
                    needsExplicitConfirmation = promptViewModel.needsExplicitConfirmation(biometricModality2, promptViewModel$showAuthenticated$1);
                    if (needsExplicitConfirmation == coroutineSingletons) {
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
                    long j3 = promptViewModel$showAuthenticated$1.J$0;
                    String str3 = (String) promptViewModel$showAuthenticated$1.L$2;
                    BiometricModality biometricModality3 = (BiometricModality) promptViewModel$showAuthenticated$1.L$1;
                    PromptViewModel promptViewModel2 = (PromptViewModel) promptViewModel$showAuthenticated$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    needsExplicitConfirmation = obj;
                    str2 = str3;
                    biometricModality2 = biometricModality3;
                    promptViewModel = promptViewModel2;
                    j2 = j3;
                }
                boolean booleanValue = ((Boolean) needsExplicitConfirmation).booleanValue();
                promptViewModel._isAuthenticated.setValue(new PromptAuthState(true, biometricModality2, booleanValue, j2));
                promptViewModel._message.setValue(PromptMessage.Empty.INSTANCE);
                promptViewModel._legacyState.setValue(new Integer(!booleanValue ? 5 : 6));
                job = promptViewModel.messageJob;
                if (job != null) {
                    job.cancel(null);
                }
                promptViewModel.messageJob = null;
                if (!StringsKt__StringsJVMKt.isBlank(str2)) {
                    return Unit.INSTANCE;
                }
                promptViewModel$showAuthenticated$1.L$0 = null;
                promptViewModel$showAuthenticated$1.L$1 = null;
                promptViewModel$showAuthenticated$1.L$2 = null;
                promptViewModel$showAuthenticated$1.label = 2;
                if (promptViewModel.showHelp(str2) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            }
        }
        promptViewModel$showAuthenticated$1 = new PromptViewModel$showAuthenticated$1(promptViewModel, continuation);
        Object obj2 = promptViewModel$showAuthenticated$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = promptViewModel$showAuthenticated$1.label;
        if (i != 0) {
        }
        boolean booleanValue2 = ((Boolean) needsExplicitConfirmation).booleanValue();
        promptViewModel._isAuthenticated.setValue(new PromptAuthState(true, biometricModality2, booleanValue2, j2));
        promptViewModel._message.setValue(PromptMessage.Empty.INSTANCE);
        promptViewModel._legacyState.setValue(new Integer(!booleanValue2 ? 5 : 6));
        job = promptViewModel.messageJob;
        if (job != null) {
        }
        promptViewModel.messageJob = null;
        if (!StringsKt__StringsJVMKt.isBlank(str2)) {
        }
    }

    public final Unit showHelp(String str) {
        StateFlowImpl stateFlowImpl = this._isAuthenticated;
        boolean z = ((PromptAuthState) stateFlowImpl.getValue()).isAuthenticated;
        if (!z) {
            this._isAuthenticating.setValue(Boolean.FALSE);
            stateFlowImpl.setValue(new PromptAuthState(false, null, false, 0L, 14, null));
        }
        this._message.setValue(StringsKt__StringsJVMKt.isBlank(str) ^ true ? new PromptMessage.Help(str) : PromptMessage.Empty.INSTANCE);
        this._forceMediumSize.setValue(Boolean.TRUE);
        this._legacyState.setValue(new Integer(z ? 5 : 3));
        Job job = this.messageJob;
        if (job != null) {
            job.cancel(null);
        }
        this.messageJob = null;
        return Unit.INSTANCE;
    }

    public final Object showTemporaryError(String str, String str2, boolean z, boolean z2, BiometricModality biometricModality, Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new PromptViewModel$showTemporaryError$2(this, z2, biometricModality, str, z, str2, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }

    public final Object showTemporaryHelp(String str, String str2, Continuation continuation) {
        Object coroutineScope = CoroutineScopeKt.coroutineScope(new PromptViewModel$showTemporaryHelp$2(this, str, str2, null), continuation);
        return coroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? coroutineScope : Unit.INSTANCE;
    }
}
