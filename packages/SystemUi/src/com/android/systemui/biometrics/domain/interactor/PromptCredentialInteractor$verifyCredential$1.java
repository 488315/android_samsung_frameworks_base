package com.android.systemui.biometrics.domain.interactor;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.domain.interactor.PromptCredentialInteractor", m277f = "PromptCredentialInteractor.kt", m278l = {180}, m279m = "verifyCredential")
/* loaded from: classes.dex */
final class PromptCredentialInteractor$verifyCredential$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ PromptCredentialInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptCredentialInteractor$verifyCredential$1(PromptCredentialInteractor promptCredentialInteractor, Continuation<? super PromptCredentialInteractor$verifyCredential$1> continuation) {
        super(continuation);
        this.this$0 = promptCredentialInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return PromptCredentialInteractor.access$verifyCredential(this.this$0, null, null, this);
    }
}
