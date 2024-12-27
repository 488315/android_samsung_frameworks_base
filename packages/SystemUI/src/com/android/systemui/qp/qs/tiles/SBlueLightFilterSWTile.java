package com.android.systemui.qp.qs.tiles;

import android.content.Context;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SBlueLightFilterSWTile implements CommandQueue.Callbacks {
    private final SettingsHelper settingsHelper;

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
