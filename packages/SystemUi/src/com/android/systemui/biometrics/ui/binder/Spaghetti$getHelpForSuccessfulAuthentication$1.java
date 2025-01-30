package com.android.systemui.biometrics.ui.binder;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.ui.binder.Spaghetti", m277f = "BiometricViewBinder.kt", m278l = {459}, m279m = "getHelpForSuccessfulAuthentication")
/* loaded from: classes.dex */
final class Spaghetti$getHelpForSuccessfulAuthentication$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ Spaghetti this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Spaghetti$getHelpForSuccessfulAuthentication$1(Spaghetti spaghetti, Continuation<? super Spaghetti$getHelpForSuccessfulAuthentication$1> continuation) {
        super(continuation);
        this.this$0 = spaghetti;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return Spaghetti.access$getHelpForSuccessfulAuthentication(this.this$0, null, this);
    }
}
