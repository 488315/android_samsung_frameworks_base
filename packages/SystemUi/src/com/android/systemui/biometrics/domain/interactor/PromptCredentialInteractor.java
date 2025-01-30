package com.android.systemui.biometrics.domain.interactor;

import com.android.internal.widget.LockscreenCredential;
import com.android.systemui.biometrics.data.repository.PromptRepository;
import com.android.systemui.biometrics.data.repository.PromptRepositoryImpl;
import com.android.systemui.biometrics.domain.interactor.CredentialStatus;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.List;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PromptCredentialInteractor {
    public final StateFlowImpl _verificationError;
    public final CoroutineDispatcher bgDispatcher;
    public final CredentialInteractor credentialInteractor;
    public final Flow prompt;
    public final ReadonlyStateFlow verificationError;

    public PromptCredentialInteractor(CoroutineDispatcher coroutineDispatcher, PromptRepository promptRepository, CredentialInteractor credentialInteractor) {
        this.bgDispatcher = coroutineDispatcher;
        this.credentialInteractor = credentialInteractor;
        PromptRepositoryImpl promptRepositoryImpl = (PromptRepositoryImpl) promptRepository;
        this.prompt = FlowKt.distinctUntilChanged(FlowKt.combine(promptRepositoryImpl.promptInfo, promptRepositoryImpl.challenge, promptRepositoryImpl.userId, promptRepositoryImpl.kind, new PromptCredentialInteractor$prompt$1(this, null)));
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._verificationError = MutableStateFlow;
        this.verificationError = FlowKt.asStateFlow(MutableStateFlow);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$verifyCredential(PromptCredentialInteractor promptCredentialInteractor, BiometricPromptRequest.Credential credential, LockscreenCredential lockscreenCredential, Continuation continuation) {
        PromptCredentialInteractor$verifyCredential$1 promptCredentialInteractor$verifyCredential$1;
        int i;
        promptCredentialInteractor.getClass();
        if (continuation instanceof PromptCredentialInteractor$verifyCredential$1) {
            promptCredentialInteractor$verifyCredential$1 = (PromptCredentialInteractor$verifyCredential$1) continuation;
            int i2 = promptCredentialInteractor$verifyCredential$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                promptCredentialInteractor$verifyCredential$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = promptCredentialInteractor$verifyCredential$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = promptCredentialInteractor$verifyCredential$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    if (lockscreenCredential == null || lockscreenCredential.isNone()) {
                        return new CredentialStatus.Fail.Error(null, null, null, 7, null);
                    }
                    CredentialInteractorImpl credentialInteractorImpl = (CredentialInteractorImpl) promptCredentialInteractor.credentialInteractor;
                    credentialInteractorImpl.getClass();
                    FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new SafeFlow(new CredentialInteractorImpl$verifyCredential$1(credential, credentialInteractorImpl, lockscreenCredential, null)), new PromptCredentialInteractor$verifyCredential$finalStatus$1(promptCredentialInteractor, null));
                    promptCredentialInteractor$verifyCredential$1.label = 1;
                    obj = FlowKt.lastOrNull(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, promptCredentialInteractor$verifyCredential$1);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                CredentialStatus credentialStatus = (CredentialStatus) obj;
                return credentialStatus != null ? new CredentialStatus.Fail.Error(null, null, null, 7, null) : credentialStatus;
            }
        }
        promptCredentialInteractor$verifyCredential$1 = new PromptCredentialInteractor$verifyCredential$1(promptCredentialInteractor, continuation);
        Object obj2 = promptCredentialInteractor$verifyCredential$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = promptCredentialInteractor$verifyCredential$1.label;
        if (i != 0) {
        }
        CredentialStatus credentialStatus2 = (CredentialStatus) obj2;
        if (credentialStatus2 != null) {
        }
    }

    public static Object checkCredential$default(PromptCredentialInteractor promptCredentialInteractor, BiometricPromptRequest.Credential credential, CharSequence charSequence, List list, ContinuationImpl continuationImpl, int i) {
        CharSequence charSequence2 = (i & 2) != 0 ? null : charSequence;
        List list2 = (i & 4) != 0 ? null : list;
        promptCredentialInteractor.getClass();
        return BuildersKt.withContext(promptCredentialInteractor.bgDispatcher, new PromptCredentialInteractor$checkCredential$2(credential, charSequence2, list2, promptCredentialInteractor, null), continuationImpl);
    }
}
