package com.android.systemui.flags;

import android.app.NotificationManager;
import android.content.Context;
import com.android.systemui.flags.FlagDependenciesBase;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class FlagDependenciesNotifier implements FlagDependenciesBase.Handler {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
