package com.android.systemui.coverlauncher.utils;

import android.content.Context;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class CoverLauncherWidgetUtils {
    public static final Companion Companion = new Companion(null);
    public final Context mContext;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public CoverLauncherWidgetUtils(Context context) {
        this.mContext = context;
    }
}
