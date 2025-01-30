package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.accessibility.fontscaling.FontScalingDialog;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SystemSettings;
import com.android.systemui.util.time.SystemClock;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FontScalingTile extends QSTileImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DelayableExecutor backgroundDelayableExecutor;
    public final DialogLaunchAnimator dialogLaunchAnimator;
    public final FeatureFlags featureFlags;
    public final QSTile.Icon icon;
    public final Handler mainHandler;
    public final SecureSettings secureSettings;
    public final SystemClock systemClock;
    public final SystemSettings systemSettings;

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

    public FontScalingTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, DialogLaunchAnimator dialogLaunchAnimator, SystemSettings systemSettings, SecureSettings secureSettings, SystemClock systemClock, FeatureFlags featureFlags, DelayableExecutor delayableExecutor) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mainHandler = handler;
        this.dialogLaunchAnimator = dialogLaunchAnimator;
        this.systemSettings = systemSettings;
        this.secureSettings = secureSettings;
        this.systemClock = systemClock;
        this.featureFlags = featureFlags;
        this.backgroundDelayableExecutor = delayableExecutor;
        this.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_font_scaling);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("android.settings.TEXT_READING_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_font_scaling_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final View view) {
        this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.FontScalingTile$handleClick$1
            @Override // java.lang.Runnable
            public final void run() {
                FontScalingTile fontScalingTile = FontScalingTile.this;
                int i = FontScalingTile.$r8$clinit;
                FontScalingDialog fontScalingDialog = new FontScalingDialog(fontScalingTile.mContext, fontScalingTile.systemSettings, fontScalingTile.secureSettings, fontScalingTile.systemClock, fontScalingTile.mainHandler, fontScalingTile.backgroundDelayableExecutor);
                View view2 = view;
                if (view2 != null) {
                    DialogLaunchAnimator.showFromView$default(FontScalingTile.this.dialogLaunchAnimator, fontScalingDialog, view2, new DialogCuj(58, "font_scaling"), false, 8);
                } else {
                    fontScalingDialog.show();
                }
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        if (state != null) {
            state.label = this.mContext.getString(R.string.quick_settings_font_scaling_label);
        }
        if (state == null) {
            return;
        }
        state.icon = this.icon;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final boolean isAvailable() {
        return ((FeatureFlagsRelease) this.featureFlags).isEnabled(Flags.ENABLE_FONT_SCALING_TILE);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.State();
    }
}
