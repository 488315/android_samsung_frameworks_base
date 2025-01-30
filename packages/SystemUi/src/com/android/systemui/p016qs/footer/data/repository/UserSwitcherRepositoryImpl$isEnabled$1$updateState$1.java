package com.android.systemui.p016qs.footer.data.repository;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl$isEnabled$1", m277f = "UserSwitcherRepository.kt", m278l = {72}, m279m = "invokeSuspend$updateState")
/* loaded from: classes2.dex */
final class UserSwitcherRepositoryImpl$isEnabled$1$updateState$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;

    public UserSwitcherRepositoryImpl$isEnabled$1$updateState$1(Continuation<? super UserSwitcherRepositoryImpl$isEnabled$1$updateState$1> continuation) {
        super(continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return UserSwitcherRepositoryImpl$isEnabled$1.invokeSuspend$updateState(null, null, this);
    }
}
