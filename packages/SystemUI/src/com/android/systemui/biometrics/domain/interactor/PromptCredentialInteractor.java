package com.android.systemui.biometrics.domain.interactor;

import android.content.pm.UserInfo;
import android.hardware.biometrics.PromptInfo;
import com.android.internal.widget.LockscreenCredential;
import com.android.systemui.biometrics.data.repository.PromptRepository;
import com.android.systemui.biometrics.data.repository.PromptRepositoryImpl;
import com.android.systemui.biometrics.domain.interactor.CredentialStatus;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import com.android.systemui.biometrics.shared.model.BiometricUserInfo;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public final class PromptCredentialInteractor {
    public final StateFlowImpl _verificationError;
    public final CoroutineDispatcher bgDispatcher;
    public final CredentialInteractor credentialInteractor;
    public final Flow prompt;
    public final PromptCredentialInteractor$special$$inlined$map$1 showTitleOnly;
    public final ReadonlyStateFlow verificationError;

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor$special$$inlined$map$1] */
    public PromptCredentialInteractor(CoroutineDispatcher coroutineDispatcher, PromptRepository promptRepository, CredentialInteractor credentialInteractor) {
        this.bgDispatcher = coroutineDispatcher;
        this.credentialInteractor = credentialInteractor;
        promptRepository.getClass();
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
                        Boolean boolValueOf = Boolean.valueOf(((promptInfo != null ? promptInfo.getContentView() : null) == null || promptInfo.isContentViewMoreOptionsButtonUsed()) ? false : true);
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
        };
        this.prompt = FlowKt.distinctUntilChanged(FlowKt.combine(readonlyStateFlow, promptRepositoryImpl.challenge, promptRepositoryImpl.userId, promptRepositoryImpl.promptKind, new PromptCredentialInteractor$prompt$1(this, null)));
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._verificationError = stateFlowImplMutableStateFlow;
        this.verificationError = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
    }

    public static final BiometricUserInfo access$userInfo(PromptCredentialInteractor promptCredentialInteractor, int i, boolean z) {
        UserInfo profileParent;
        CredentialInteractorImpl credentialInteractorImpl = (CredentialInteractorImpl) promptCredentialInteractor.credentialInteractor;
        int credentialOwnerProfile = credentialInteractorImpl.userManager.getCredentialOwnerProfile(i);
        int credentialOwnerProfile2 = (!z || (profileParent = credentialInteractorImpl.userManager.getProfileParent(i)) == null) ? credentialInteractorImpl.userManager.getCredentialOwnerProfile(i) : profileParent.id;
        return new BiometricUserInfo(i, credentialOwnerProfile, credentialOwnerProfile2);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$verifyCredential(PromptCredentialInteractor promptCredentialInteractor, BiometricPromptRequest.Credential credential, LockscreenCredential lockscreenCredential, ContinuationImpl continuationImpl) {
        PromptCredentialInteractor$verifyCredential$1 promptCredentialInteractor$verifyCredential$1;
        promptCredentialInteractor.getClass();
        if (continuationImpl instanceof PromptCredentialInteractor$verifyCredential$1) {
            promptCredentialInteractor$verifyCredential$1 = (PromptCredentialInteractor$verifyCredential$1) continuationImpl;
            int i = promptCredentialInteractor$verifyCredential$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                promptCredentialInteractor$verifyCredential$1.label = i - Integer.MIN_VALUE;
            } else {
                promptCredentialInteractor$verifyCredential$1 = new PromptCredentialInteractor$verifyCredential$1(promptCredentialInteractor, continuationImpl);
            }
        }
        Object objLastOrNull = promptCredentialInteractor$verifyCredential$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = promptCredentialInteractor$verifyCredential$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objLastOrNull);
            if (lockscreenCredential == null || lockscreenCredential.isNone()) {
                return new CredentialStatus.Fail.Error(null, null, null, 7, null);
            }
            CredentialInteractorImpl credentialInteractorImpl = (CredentialInteractorImpl) promptCredentialInteractor.credentialInteractor;
            credentialInteractorImpl.getClass();
            FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new SafeFlow(new CredentialInteractorImpl$verifyCredential$1(credential, credentialInteractorImpl, lockscreenCredential, null)), new PromptCredentialInteractor$verifyCredential$finalStatus$1(promptCredentialInteractor, null));
            promptCredentialInteractor$verifyCredential$1.label = 1;
            objLastOrNull = FlowKt.lastOrNull(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, promptCredentialInteractor$verifyCredential$1);
            if (objLastOrNull == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objLastOrNull);
        }
        CredentialStatus credentialStatus = (CredentialStatus) objLastOrNull;
        return credentialStatus == null ? new CredentialStatus.Fail.Error(null, null, null, 7, null) : credentialStatus;
    }

    public static Object checkCredential$default(PromptCredentialInteractor promptCredentialInteractor, BiometricPromptRequest.Credential credential, CharSequence charSequence, List list, ContinuationImpl continuationImpl, int i) {
        CharSequence charSequence2 = (i & 2) != 0 ? null : charSequence;
        List list2 = (i & 4) != 0 ? null : list;
        promptCredentialInteractor.getClass();
        return BuildersKt.withContext(promptCredentialInteractor.bgDispatcher, new PromptCredentialInteractor$checkCredential$2(credential, charSequence2, list2, promptCredentialInteractor, null), continuationImpl);
    }
}
