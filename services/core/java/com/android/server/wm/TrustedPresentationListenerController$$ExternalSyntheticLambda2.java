package com.android.server.wm;

import java.util.Optional;

public final /* synthetic */ class TrustedPresentationListenerController$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ TrustedPresentationListenerController$$ExternalSyntheticLambda2(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                TrustedPresentationListenerController trustedPresentationListenerController =
                        (TrustedPresentationListenerController) obj;
                trustedPresentationListenerController.computeTpl(
                        trustedPresentationListenerController.mLastWindowHandles);
                break;
            default:
                TrustedPresentationListenerController.Listeners.ListenerDeathRecipient
                        listenerDeathRecipient =
                                (TrustedPresentationListenerController.Listeners
                                                .ListenerDeathRecipient)
                                        obj;
                listenerDeathRecipient.this$1.mUniqueListeners.remove(
                        listenerDeathRecipient.mListenerBinder);
                listenerDeathRecipient.this$1.removeListeners(
                        listenerDeathRecipient.mListenerBinder, Optional.empty());
                break;
        }
    }
}
