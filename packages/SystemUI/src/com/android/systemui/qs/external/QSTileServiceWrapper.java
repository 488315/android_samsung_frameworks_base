package com.android.systemui.qs.external;

import android.service.quicksettings.IQSTileService;

public final class QSTileServiceWrapper {
    public final IQSTileService mService;

    public QSTileServiceWrapper(IQSTileService iQSTileService) {
        this.mService = iQSTileService;
    }
}
