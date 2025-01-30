package com.android.systemui.screenshot;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.screenshot.ScreenshotPolicyImpl", m277f = "ScreenshotPolicyImpl.kt", m278l = {102, 106}, m279m = "findPrimaryContent$suspendImpl")
/* loaded from: classes2.dex */
final class ScreenshotPolicyImpl$findPrimaryContent$1 extends ContinuationImpl {
    int I$0;
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ ScreenshotPolicyImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenshotPolicyImpl$findPrimaryContent$1(ScreenshotPolicyImpl screenshotPolicyImpl, Continuation<? super ScreenshotPolicyImpl$findPrimaryContent$1> continuation) {
        super(continuation);
        this.this$0 = screenshotPolicyImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return ScreenshotPolicyImpl.findPrimaryContent$suspendImpl(this.this$0, 0, this);
    }
}
