package com.android.systemui.flags;

import android.app.NotificationManager;
import android.content.Context;
import com.android.systemui.flags.FlagDependenciesBase;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class FlagDependenciesNotifier implements FlagDependenciesBase.Handler {

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
