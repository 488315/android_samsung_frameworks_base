package com.android.systemui.flags;

import android.app.NotificationManager;
import android.content.Context;
import com.android.systemui.flags.FlagDependenciesBase;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class FlagDependenciesNotifier implements FlagDependenciesBase.Handler {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public FlagDependenciesNotifier(Context context, NotificationManager notificationManager) {
    }
}
