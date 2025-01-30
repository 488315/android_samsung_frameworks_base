package com.android.systemui.security.data.repository;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.security.data.repository.SecurityRepositoryImpl$security$1", m277f = "SecurityRepository.kt", m278l = {45}, m279m = "invokeSuspend$updateState")
/* loaded from: classes2.dex */
final class SecurityRepositoryImpl$security$1$updateState$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    int label;
    /* synthetic */ Object result;

    public SecurityRepositoryImpl$security$1$updateState$1(Continuation<? super SecurityRepositoryImpl$security$1$updateState$1> continuation) {
        super(continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return SecurityRepositoryImpl$security$1.invokeSuspend$updateState(null, null, this);
    }
}
