package com.android.systemui.shade;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QsBatteryModeController {
    public final Context context;
    public float fadeInStartFraction;
    public float fadeOutCompleteFraction;
    public final StatusBarContentInsetsProvider insetsProvider;

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

    public QsBatteryModeController(Context context, StatusBarContentInsetsProvider statusBarContentInsetsProvider) {
        this.context = context;
        this.insetsProvider = statusBarContentInsetsProvider;
        updateResources();
    }

    public final void updateResources() {
        this.fadeInStartFraction = (this.context.getResources().getInteger(R.integer.fade_in_start_frame) - 1) / 100.0f;
        this.fadeOutCompleteFraction = (this.context.getResources().getInteger(R.integer.fade_out_complete_frame) + 1) / 100.0f;
    }
}
