package com.android.systemui.user.domain.interactor;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.user.domain.interactor.UserInteractor", m277f = "UserInteractor.kt", m278l = {621}, m279m = "onBroadcastReceived")
/* loaded from: classes2.dex */
final class UserInteractor$onBroadcastReceived$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ UserInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserInteractor$onBroadcastReceived$1(UserInteractor userInteractor, Continuation<? super UserInteractor$onBroadcastReceived$1> continuation) {
        super(continuation);
        this.this$0 = userInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return UserInteractor.access$onBroadcastReceived(this.this$0, null, null, this);
    }
}
