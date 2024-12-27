package com.android.systemui.plugins.aod;

import android.view.View;

public interface PluginAODFaceWidgetManager extends PluginAODBaseManager {

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
