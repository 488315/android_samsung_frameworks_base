package com.android.systemui.biometrics.ui.viewmodel;

import com.samsung.android.nexus.video.VideoPlayer;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.ui.viewmodel.PromptViewModel", m277f = "PromptViewModel.kt", m278l = {365, VolteConstants.ErrorCode.ALTERNATIVE_SERVICES}, m279m = "showAuthenticated")
/* loaded from: classes.dex */
final class PromptViewModel$showAuthenticated$1 extends ContinuationImpl {
    long J$0;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ PromptViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptViewModel$showAuthenticated$1(PromptViewModel promptViewModel, Continuation<? super PromptViewModel$showAuthenticated$1> continuation) {
        super(continuation);
        this.this$0 = promptViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return this.this$0.showAuthenticated(null, 0L, null, this);
    }
}
