package com.android.systemui.qp.qs.tiles;

import android.content.Context;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SBlueLightFilterSWTile implements CommandQueue.Callbacks {
    private final SettingsHelper settingsHelper;

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

    public SBlueLightFilterSWTile(Context context, SettingsHelper settingsHelper) {
        this.settingsHelper = settingsHelper;
    }
}
