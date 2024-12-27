package com.android.systemui.plugins.qs;

public interface QSContainerController {

    public final class DefaultImpls {
        public static void setCustomizerShowing(QSContainerController qSContainerController, boolean z) {
            qSContainerController.setCustomizerShowing(z, 0L);
        }
    }

    void setCustomizerAnimating(boolean z);

    void setCustomizerShowing(boolean z);

    void setCustomizerShowing(boolean z, long j);

    void setDetailShowing(boolean z);
}
