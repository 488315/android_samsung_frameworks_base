package com.android.server.om;

import android.os.IBinder;
import android.text.TextUtils;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class IdmapDaemon$$ExternalSyntheticLambda0
        implements IBinder.DeathRecipient {
    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        Slog.w(
                "OverlayManager",
                TextUtils.formatSimple("service '%s' died", new Object[] {"idmap"}));
    }
}
