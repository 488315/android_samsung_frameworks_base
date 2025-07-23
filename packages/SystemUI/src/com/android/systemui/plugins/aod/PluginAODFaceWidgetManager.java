package com.android.systemui.plugins.aod;

import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface PluginAODFaceWidgetManager extends PluginAODBaseManager {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
