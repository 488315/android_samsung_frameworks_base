package com.android.systemui.shade;

import android.content.Context;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class LargeScreenHeaderHelper {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public final SecQSPanelResourcePicker qsPanelResourcePicker;

    public final class Companion {
        private Companion() {
        }

        public static int getLargeScreenHeaderHeight(Context context) {
            return Math.max(context.getResources().getDimensionPixelSize(R.dimen.large_screen_shade_header_height), SystemBarUtils.getStatusBarHeight(context));
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public LargeScreenHeaderHelper(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker) {
        this.context = context;
        this.qsPanelResourcePicker = secQSPanelResourcePicker;
    }
}
