package com.android.systemui.qp.qs.tiles;

import android.content.Context;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SBlueLightFilterSWTile implements CommandQueue.Callbacks {
    private final SettingsHelper settingsHelper;

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

    public SBlueLightFilterSWTile(Context context, SettingsHelper settingsHelper) {
        this.settingsHelper = settingsHelper;
    }
}
