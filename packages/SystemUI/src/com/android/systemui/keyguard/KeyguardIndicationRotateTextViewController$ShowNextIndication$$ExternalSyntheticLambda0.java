package com.android.systemui.keyguard;

import com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardIndicationRotateTextViewController$ShowNextIndication$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ KeyguardIndicationRotateTextViewController.ShowNextIndication f$0;

    @Override // java.lang.Runnable
    public final void run() {
        KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController = this.f$0.this$0;
        keyguardIndicationRotateTextViewController.showIndication(((ArrayList) keyguardIndicationRotateTextViewController.mIndicationQueue).size() == 0 ? -1 : ((Integer) ((ArrayList) keyguardIndicationRotateTextViewController.mIndicationQueue).get(0)).intValue());
    }
}
