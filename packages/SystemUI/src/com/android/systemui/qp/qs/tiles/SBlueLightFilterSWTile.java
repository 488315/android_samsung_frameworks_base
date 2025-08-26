package com.android.systemui.qp.qs.tiles;

import android.content.Context;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.util.SettingsHelper;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class SBlueLightFilterSWTile implements CommandQueue.Callbacks {
    public static final /* synthetic */ int $r8$clinit = 0;
    private final SettingsHelper settingsHelper;

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
        LazyKt__LazyJVMKt.lazy(new SBlueLightFilterSWTile$$ExternalSyntheticLambda0());
        this.settingsHelper = settingsHelper;
    }
}
