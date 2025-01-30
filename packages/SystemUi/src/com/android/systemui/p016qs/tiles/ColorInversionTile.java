package com.android.systemui.p016qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.p016qs.QSHost;
import com.android.systemui.p016qs.QsEventLogger;
import com.android.systemui.p016qs.SettingObserver;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.p016qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.settings.SecureSettings;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ColorInversionTile extends QSTileImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final C22331 mSetting;

    /* JADX WARN: Type inference failed for: r7v1, types: [com.android.systemui.qs.tiles.ColorInversionTile$1] */
    public ColorInversionTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, UserTracker userTracker, SecureSettings secureSettings) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mSetting = new SettingObserver(secureSettings, this.mHandler, "accessibility_display_inversion_enabled", ((UserTrackerImpl) userTracker).getUserId()) { // from class: com.android.systemui.qs.tiles.ColorInversionTile.1
            @Override // com.android.systemui.p016qs.SettingObserver
            public final void handleValueChanged(int i, boolean z) {
                ColorInversionTile colorInversionTile = ColorInversionTile.this;
                Integer valueOf = Integer.valueOf(i);
                int i2 = ColorInversionTile.$r8$clinit;
                colorInversionTile.handleRefreshState(valueOf);
            }
        };
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("android.settings.COLOR_INVERSION_SETTINGS");
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final int getMetricsCategory() {
        return 116;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_inversion_label);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0021  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleClick(View view) {
        boolean z;
        EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mEdmMonitor;
        if (edmMonitor != null) {
            Context context = edmMonitor.knoxStateMonitor.mContext;
            if (!edmMonitor.mSettingsChangesAllowed) {
                z = true;
                if (!z) {
                    showItPolicyToast();
                    return;
                } else {
                    setValue(!((QSTile.BooleanState) this.mState).value ? 1 : 0);
                    return;
                }
            }
        }
        z = false;
        if (!z) {
        }
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        setListening(false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0017, code lost:
    
        if ((!r3.mSettingsChangesAllowed) != false) goto L8;
     */
    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleSecondaryClick(View view) {
        boolean z;
        EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).mEdmMonitor;
        if (edmMonitor != null) {
            Context context = edmMonitor.knoxStateMonitor.mContext;
            z = true;
        }
        z = false;
        if (z) {
            showItPolicyToast();
        } else {
            this.mActivityStarter.postStartActivityDismissingKeyguard(getLongClickIntent(), 0);
        }
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        setListening(z);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        boolean z = (obj instanceof Integer ? ((Integer) obj).intValue() : getValue()) != 0;
        booleanState.value = z;
        booleanState.state = z ? 2 : 1;
        booleanState.label = this.mContext.getString(R.string.quick_settings_inversion_label);
        booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_quick_panel_icon_accessibility_color_inversion);
        booleanState.dualTarget = true;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleUserSwitch(int i) {
        C22331 c22331 = this.mSetting;
        c22331.setUserId(i);
        handleRefreshState(Integer.valueOf(c22331.getValue()));
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }
}
