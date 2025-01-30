package com.android.systemui.biometrics.ui.viewmodel;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.ui.viewmodel.PromptViewModel", m277f = "PromptViewModel.kt", m278l = {385, 386, 392}, m279m = "needsExplicitConfirmation")
/* loaded from: classes.dex */
final class PromptViewModel$needsExplicitConfirmation$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    boolean Z$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ PromptViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromptViewModel$needsExplicitConfirmation$1(PromptViewModel promptViewModel, Continuation<? super PromptViewModel$needsExplicitConfirmation$1> continuation) {
        super(continuation);
        this.this$0 = promptViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        PromptViewModel promptViewModel = this.this$0;
        int i = PromptViewModel.$r8$clinit;
        return promptViewModel.needsExplicitConfirmation(null, this);
    }
}
