package com.android.systemui.screenshot.sep;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.screenshot.sep.SemImageCaptureImpl", m277f = "SemImageCaptureImpl.kt", m278l = {168}, m279m = "semCaptureTask")
/* loaded from: classes2.dex */
final class SemImageCaptureImpl$semCaptureTask$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ SemImageCaptureImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SemImageCaptureImpl$semCaptureTask$1(SemImageCaptureImpl semImageCaptureImpl, Continuation<? super SemImageCaptureImpl$semCaptureTask$1> continuation) {
        super(continuation);
        this.this$0 = semImageCaptureImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return this.this$0.semCaptureTask(0, this);
    }
}
