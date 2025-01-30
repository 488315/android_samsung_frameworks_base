package com.android.systemui.p016qs.cinema;

import android.content.Context;
import com.android.systemui.p016qs.QSTileHost;
import com.android.systemui.p016qs.SecQSPanelController;
import com.android.systemui.p016qs.SecQuickQSPanel;
import com.android.systemui.p016qs.SecQuickQSPanelController;
import com.android.systemui.plugins.p013qs.InterfaceC1922QS;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSCinemaProvider {
    public final SecQSPanelController mQSPanelController;
    public final QSTileHost mQSTileHost;
    public final InterfaceC1922QS mQs;
    public int mCurrentOrientation = 0;
    public int mCurrentLayoutDirection = -1;

    public QSCinemaProvider(Context context, InterfaceC1922QS interfaceC1922QS, SecQuickQSPanel secQuickQSPanel, SecQSPanelController secQSPanelController, SecQuickQSPanelController secQuickQSPanelController, QSTileHost qSTileHost) {
        context.getResources().getConfiguration();
        this.mQs = interfaceC1922QS;
        this.mQSPanelController = secQSPanelController;
        this.mQSTileHost = qSTileHost;
    }
}
