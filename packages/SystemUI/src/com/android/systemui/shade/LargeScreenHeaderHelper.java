package com.android.systemui.shade;

import android.content.Context;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class LargeScreenHeaderHelper {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public final SecQSPanelResourcePicker qsPanelResourcePicker;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
