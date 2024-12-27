package com.android.systemui.flags;

import android.app.NotificationManager;
import android.content.Context;
import com.android.systemui.flags.FlagDependenciesBase;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class FlagDependenciesNotifier implements FlagDependenciesBase.Handler {

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public FlagDependenciesNotifier(Context context, NotificationManager notificationManager) {
    }
}
