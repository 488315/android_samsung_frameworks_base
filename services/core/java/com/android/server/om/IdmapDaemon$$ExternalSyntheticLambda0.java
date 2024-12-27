package com.android.server.om;

import android.os.IBinder;
import android.text.TextUtils;
import android.util.Slog;

public final /* synthetic */ class IdmapDaemon$$ExternalSyntheticLambda0
        implements IBinder.DeathRecipient {
    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        Slog.w(
                "OverlayManager",
                TextUtils.formatSimple("service '%s' died", new Object[] {"idmap"}));
    }
}
