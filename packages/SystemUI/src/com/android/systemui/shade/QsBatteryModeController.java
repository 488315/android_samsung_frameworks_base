package com.android.systemui.shade;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.statusbar.data.repository.StatusBarContentInsetsProviderStore;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class QsBatteryModeController {
    public final Context context;
    public float fadeInStartFraction;
    public float fadeOutCompleteFraction;
    public final StatusBarContentInsetsProviderStore insetsProviderStore;

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

    public QsBatteryModeController(Context context, StatusBarContentInsetsProviderStore statusBarContentInsetsProviderStore) {
        this.context = context;
        this.insetsProviderStore = statusBarContentInsetsProviderStore;
        updateResources();
    }

    public final void updateResources() {
        this.fadeInStartFraction = (this.context.getResources().getInteger(R.integer.fade_in_start_frame) - 1) / 100.0f;
        this.fadeOutCompleteFraction = (this.context.getResources().getInteger(R.integer.fade_out_complete_frame) + 1) / 100.0f;
    }
}
