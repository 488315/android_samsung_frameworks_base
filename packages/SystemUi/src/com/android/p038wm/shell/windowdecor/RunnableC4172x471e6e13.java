package com.android.p038wm.shell.windowdecor;

import android.content.Intent;
import android.os.UserHandle;
import com.android.p038wm.shell.windowdecor.MultitaskingWindowDecorViewModel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.windowdecor.MultitaskingWindowDecorViewModel$CaptionTouchEventListener$$ExternalSyntheticLambda0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC4172x471e6e13 implements Runnable {
    public final /* synthetic */ MultitaskingWindowDecorViewModel.CaptionTouchEventListener f$0;
    public final /* synthetic */ Intent f$1;

    public /* synthetic */ RunnableC4172x471e6e13(MultitaskingWindowDecorViewModel.CaptionTouchEventListener captionTouchEventListener, Intent intent) {
        this.f$0 = captionTouchEventListener;
        this.f$1 = intent;
    }

    @Override // java.lang.Runnable
    public final void run() {
        MultitaskingWindowDecorViewModel.CaptionTouchEventListener captionTouchEventListener = this.f$0;
        captionTouchEventListener.this$0.mContext.startServiceAsUser(this.f$1, UserHandle.CURRENT);
    }
}
