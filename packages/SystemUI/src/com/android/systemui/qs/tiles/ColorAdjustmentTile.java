package com.android.systemui.qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.SettingObserver;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.WindowUtils;
import com.android.systemui.util.settings.SystemSettings;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ColorAdjustmentTile extends QSTileImpl {
    public final ActivityStarter mActivityStarter;
    public final AnonymousClass1 mSetting;

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.qs.tiles.ColorAdjustmentTile$1] */
    public ColorAdjustmentTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, Resources resources, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, UserTracker userTracker, SystemSettings systemSettings) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mActivityStarter = activityStarter;
        this.mSetting = new SettingObserver(systemSettings, this.mHandler, SettingsHelper.INDEX_COLOR_ADJUSTMENT, ((UserTrackerImpl) userTracker).getUserId()) { // from class: com.android.systemui.qs.tiles.ColorAdjustmentTile.1
            @Override // com.android.systemui.qs.SettingObserver
            public final void handleValueChanged(int i, boolean z) {
                ColorAdjustmentTile.this.handleRefreshState(Integer.valueOf(i));
            }
        };
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("com.samsung.accessibility.COLOR_ADJUSTMENT");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 5011;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_color_adjustment_title);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(Expandable expandable) {
        EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor;
        if (edmMonitor != null) {
            Context context = edmMonitor.knoxStateMonitor.mContext;
            if (!edmMonitor.mSettingsChangesAllowed) {
                showItPolicyToast();
                return;
            }
        }
        setValue(!((QSTile.BooleanState) this.mState).value ? 1 : 0);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        setListening(false);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(Expandable expandable) {
        EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor;
        if (edmMonitor != null) {
            Context context = edmMonitor.knoxStateMonitor.mContext;
            if (!edmMonitor.mSettingsChangesAllowed) {
                showItPolicyToast();
                return;
            }
        }
        this.mActivityStarter.postStartActivityDismissingKeyguard(getLongClickIntent(), 0);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        setListening(z);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        int i = 0;
        boolean z = (obj instanceof Integer ? ((Integer) obj).intValue() : getValue()) != 0;
        booleanState.value = z;
        booleanState.state = z ? 2 : 1;
        booleanState.dualTarget = true;
        booleanState.label = this.mContext.getString(R.string.quick_settings_color_adjustment_title);
        booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_quick_panel_icon_accessibility_color_adjustment);
        Context context = this.mContext;
        boolean z2 = booleanState.value;
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        if (accessibilityManager == null) {
            return;
        }
        int i2 = Settings.Secure.getInt(context.getContentResolver(), "color_adjustment_type", 2);
        if (i2 == 0) {
            AccessibilityManager accessibilityManager2 = (AccessibilityManager) context.getSystemService("accessibility");
            if (accessibilityManager2 != null) {
                if (z2) {
                    accessibilityManager2.semSetMdnieAccessibilityMode(4, true);
                    return;
                } else {
                    accessibilityManager2.semSetMdnieAccessibilityMode(1, false);
                    return;
                }
            }
            return;
        }
        AccessibilityManager accessibilityManager3 = (AccessibilityManager) context.getSystemService("accessibility");
        if (accessibilityManager3 != null) {
            accessibilityManager3.semSetMdnieAccessibilityMode(1, false);
        }
        if (i2 < 1 || i2 > 4) {
            Log.d(this.TAG, "getColorAdjustmentIntensity - wrong type entered.");
        } else if (i2 == 4) {
            i = (int) (Settings.Secure.getFloat(context.getContentResolver(), "color_blind_user_parameter", 0.0f) * 10.0f);
        } else {
            String string = Settings.Secure.getString(context.getContentResolver(), "predefined_color_blind_intensity");
            if (!TextUtils.isEmpty(string)) {
                i = Integer.parseInt(string.split(",")[i2 - 1]);
            }
        }
        accessibilityManager.semSetMdnieColorBlind(z2, i / 10.0f);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUserSwitch(int i) {
        handleRefreshState(Integer.valueOf(getValue()));
        handleRefreshState(null);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        return WindowUtils.isLcdMdnieSupported() && SystemProperties.getInt("ro.product.first_api_level", 0) < 33 && !WindowUtils.isDesktopDualModeMonitorDisplay(this.mContext);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }
}
