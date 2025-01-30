package com.android.systemui.biometrics.p003ui.viewmodel;

import com.samsung.android.nexus.video.VideoPlayer;
import java.util.List;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel", m277f = "CredentialViewModel.kt", m278l = {119, 119}, m279m = "checkCredential")
/* loaded from: classes.dex */
final class CredentialViewModel$checkCredential$2 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CredentialViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CredentialViewModel$checkCredential$2(CredentialViewModel credentialViewModel, Continuation<? super CredentialViewModel$checkCredential$2> continuation) {
        super(continuation);
        this.this$0 = credentialViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return this.this$0.checkCredential((List) null, (CredentialHeaderViewModel) null, this);
    }
}
