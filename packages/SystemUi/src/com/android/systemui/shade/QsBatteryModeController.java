package com.android.systemui.shade;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QsBatteryModeController {
    public final Context context;
    public float fadeInStartFraction;
    public float fadeOutCompleteFraction;
    public final StatusBarContentInsetsProvider insetsProvider;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        Context context = this.context;
        this.fadeInStartFraction = (context.getResources().getInteger(R.integer.fade_in_start_frame) - 1) / 100.0f;
        this.fadeOutCompleteFraction = (context.getResources().getInteger(R.integer.fade_out_complete_frame) + 1) / 100.0f;
    }
}
