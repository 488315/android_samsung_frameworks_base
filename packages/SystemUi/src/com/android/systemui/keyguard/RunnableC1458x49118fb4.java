package com.android.systemui.keyguard;

import com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController$ShowNextIndication$$ExternalSyntheticLambda0 */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC1458x49118fb4 implements Runnable {
    public final /* synthetic */ KeyguardIndicationRotateTextViewController.ShowNextIndication f$0;

    @Override // java.lang.Runnable
    public final void run() {
        KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController = this.f$0.this$0;
        keyguardIndicationRotateTextViewController.showIndication(keyguardIndicationRotateTextViewController.mIndicationQueue.size() == 0 ? -1 : ((Integer) keyguardIndicationRotateTextViewController.mIndicationQueue.get(0)).intValue());
    }
}
