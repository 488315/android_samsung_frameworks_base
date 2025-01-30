package com.android.systemui.plugins.aod;

import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface PluginAODFaceWidgetManager extends PluginAODBaseManager {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
