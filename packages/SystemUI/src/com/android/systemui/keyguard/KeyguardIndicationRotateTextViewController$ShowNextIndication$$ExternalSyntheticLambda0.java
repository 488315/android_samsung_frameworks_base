package com.android.systemui.keyguard;

import com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardIndicationRotateTextViewController$ShowNextIndication$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ KeyguardIndicationRotateTextViewController.ShowNextIndication f$0;

    @Override // java.lang.Runnable
    public final void run() {
        KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController = this.f$0.this$0;
        keyguardIndicationRotateTextViewController.showIndication(((ArrayList) keyguardIndicationRotateTextViewController.mIndicationQueue).size() == 0 ? -1 : ((Integer) ((ArrayList) keyguardIndicationRotateTextViewController.mIndicationQueue).get(0)).intValue());
    }
}
