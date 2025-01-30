package com.android.p038wm.shell.bubbles;

import android.os.Bundle;
import com.android.p038wm.shell.bubbles.Bubbles;
import com.android.p038wm.shell.common.SingleInstanceRemoteListener;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubbleController$$ExternalSyntheticLambda4 implements Bubbles.BubbleExpandListener, SingleInstanceRemoteListener.RemoteCall {
    public final /* synthetic */ Object f$0;

    @Override // com.android.wm.shell.common.SingleInstanceRemoteListener.RemoteCall
    public final void accept(Object obj) {
        ((IBubblesListener$Stub$Proxy) ((IBubblesListener) obj)).onBubbleStateChange((Bundle) this.f$0);
    }

    @Override // com.android.wm.shell.bubbles.Bubbles.BubbleExpandListener
    public final void onBubbleExpandChanged(String str, boolean z) {
        Bubbles.BubbleExpandListener bubbleExpandListener = (Bubbles.BubbleExpandListener) this.f$0;
        if (bubbleExpandListener != null) {
            bubbleExpandListener.onBubbleExpandChanged(str, z);
        }
    }
}
