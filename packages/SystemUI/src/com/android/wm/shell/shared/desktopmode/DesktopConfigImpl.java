package com.android.wm.shell.shared.desktopmode;

import android.R;
import android.content.Context;
import android.os.SystemProperties;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopConfigImpl implements DesktopConfig {
    public static final int DESKTOP_DENSITY_OVERRIDE;
    public static final boolean DESKTOP_DENSITY_OVERRIDE_ENABLED;
    public static final boolean USE_WINDOW_SHADOWS;
    public static final boolean USE_WINDOW_SHADOWS_FOCUSED_WINDOW;
    public final Context context;
    public final int desktopDensityOverride;
    public final DesktopState desktopState;
    public final boolean isVeiledResizeEnabled;
    public final int maxDeskLimit;
    public int maxTaskLimit;
    public final boolean useAppToWebBuildTimeGenericLinks;
    public final boolean useDesktopOverrideDensity;
    public final boolean useRoundedCorners;
    public final int windowDecorPreWarmSize;

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
        USE_WINDOW_SHADOWS = SystemProperties.getBoolean("persist.wm.debug.desktop_use_window_shadows", true);
        USE_WINDOW_SHADOWS_FOCUSED_WINDOW = SystemProperties.getBoolean("persist.wm.debug.desktop_use_window_shadows_focused_window", false);
        DESKTOP_DENSITY_OVERRIDE_ENABLED = SystemProperties.getBoolean("persist.wm.debug.desktop_mode_density_enabled", false);
        DESKTOP_DENSITY_OVERRIDE = SystemProperties.getInt("persist.wm.debug.desktop_mode_density", IKnoxCustomManager.Stub.TRANSACTION_registerSystemUiCallback);
    }

    public DesktopConfigImpl(Context context, DesktopState desktopState) {
        int i;
        this.context = context;
        this.desktopState = desktopState;
        this.useDesktopOverrideDensity = DESKTOP_DENSITY_OVERRIDE_ENABLED && (i = DESKTOP_DENSITY_OVERRIDE) >= 100 && i <= 1000;
        this.windowDecorPreWarmSize = SystemProperties.getInt("persist.wm.debug.desktop_window_decor_pre_warm_size", 2);
        this.isVeiledResizeEnabled = SystemProperties.getBoolean("persist.wm.debug.desktop_veiled_resizing", true);
        this.useAppToWebBuildTimeGenericLinks = SystemProperties.getBoolean("persist.wm.debug.use_app_to_web_build_time_generic_links", true);
        this.useRoundedCorners = SystemProperties.getBoolean("persist.wm.debug.desktop_use_rounded_corners", true);
        this.maxTaskLimit = SystemProperties.getInt("persist.wm.debug.desktop_max_task_limit", context.getResources().getInteger(R.integer.config_previousVibrationsDumpSizeLimit));
        this.maxDeskLimit = SystemProperties.getInt("persist.wm.debug.desktop_max_desk_limit", context.getResources().getInteger(R.integer.config_progressTimeoutFallbackHome));
        this.desktopDensityOverride = SystemProperties.getInt("persist.wm.debug.desktop_mode_density", IKnoxCustomManager.Stub.TRANSACTION_registerSystemUiCallback);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DesktopConfigImpl(Context context) {
        this(context, new DesktopStateImpl(context));
        DesktopState.Companion.getClass();
    }
}
