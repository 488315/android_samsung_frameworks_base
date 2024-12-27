package com.android.server.wm;

import com.android.server.wm.TrustedPresentationListenerController;
import java.util.Optional;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class TrustedPresentationListenerController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ TrustedPresentationListenerController$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                TrustedPresentationListenerController trustedPresentationListenerController = (TrustedPresentationListenerController) obj;
                trustedPresentationListenerController.computeTpl(trustedPresentationListenerController.mLastWindowHandles);
                break;
            default:
                TrustedPresentationListenerController.Listeners.ListenerDeathRecipient listenerDeathRecipient = (TrustedPresentationListenerController.Listeners.ListenerDeathRecipient) obj;
                listenerDeathRecipient.this$1.mUniqueListeners.remove(listenerDeathRecipient.mListenerBinder);
                listenerDeathRecipient.this$1.removeListeners(listenerDeathRecipient.mListenerBinder, Optional.empty());
                break;
        }
    }
}
