package com.android.systemui.plugins.aod;

import android.view.View;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface PluginAODFaceWidgetManager extends PluginAODBaseManager {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
        int getClockSidePadding();

        int getClockType();

        String getCurrentPageKey();

        int getMinTopMargin();

        View getPage(String str);

        int getPluginLockClockGravity();

        int getPluginLockTopMargin();

        boolean hasMultiplePages();

        void requestAODState(boolean z, boolean z2);

        void setPage(String str);

        void setPageTransformer(boolean z);
    }

    void onClockPageTransitionEnded();
}
