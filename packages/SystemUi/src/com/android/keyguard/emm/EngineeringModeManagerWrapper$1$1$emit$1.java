package com.android.keyguard.emm;

import com.android.keyguard.emm.EngineeringModeManagerWrapper;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.keyguard.emm.EngineeringModeManagerWrapper$1$1", m277f = "EngineeringModeManagerWrapper.kt", m278l = {56}, m279m = "emit")
/* loaded from: classes.dex */
final class EngineeringModeManagerWrapper$1$1$emit$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ EngineeringModeManagerWrapper.C08601.AnonymousClass1 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EngineeringModeManagerWrapper$1$1$emit$1(EngineeringModeManagerWrapper.C08601.AnonymousClass1 anonymousClass1, Continuation<? super EngineeringModeManagerWrapper$1$1$emit$1> continuation) {
        super(continuation);
        this.this$0 = anonymousClass1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return this.this$0.emit(this);
    }
}
