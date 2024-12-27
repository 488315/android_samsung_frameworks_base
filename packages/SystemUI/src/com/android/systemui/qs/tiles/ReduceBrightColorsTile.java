package com.android.systemui.qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import com.android.internal.logging.MetricsLogger;
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
import com.android.systemui.qs.ReduceBrightColorsController;
import com.android.systemui.qs.SettingObserver;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.settings.SecureSettings;
import java.util.HashSet;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ReduceBrightColorsTile extends QSTileImpl implements ReduceBrightColorsController.Listener {
    public static final HashSet excludeDeviceNameSet;
    public final boolean mIsAvailable;
    public final AnonymousClass1 mSetting;

    static {
        HashSet hashSet = new HashSet();
        excludeDeviceNameSet = hashSet;
        hashSet.add("beyond");
        hashSet.add("d1");
        hashSet.add("d2");
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.qs.tiles.ReduceBrightColorsTile$1] */
    public ReduceBrightColorsTile(boolean z, ReduceBrightColorsController reduceBrightColorsController, QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, UserTracker userTracker, SecureSettings secureSettings) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        reduceBrightColorsController.observe(this.mLifecycle, this);
        this.mIsAvailable = z;
        this.mSetting = new SettingObserver(secureSettings, this.mHandler, SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_BRIGHT_COLORS_ACTIVATED, ((UserTrackerImpl) userTracker).getUserId()) { // from class: com.android.systemui.qs.tiles.ReduceBrightColorsTile.1
            @Override // com.android.systemui.qs.SettingObserver
            public final void handleValueChanged(int i, boolean z2) {
                ReduceBrightColorsTile reduceBrightColorsTile = ReduceBrightColorsTile.this;
                Integer valueOf = Integer.valueOf(i);
                HashSet hashSet = ReduceBrightColorsTile.excludeDeviceNameSet;
                reduceBrightColorsTile.handleRefreshState(valueOf);
            }
        };
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("android.settings.REDUCE_BRIGHT_COLORS_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 5010;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.sec_reduce_bright_colors_feature_name);
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
        boolean z = (obj instanceof Integer ? ((Integer) obj).intValue() : getValue()) != 0;
        booleanState.value = z;
        booleanState.state = z ? 2 : 1;
        booleanState.label = this.mContext.getString(R.string.sec_reduce_bright_colors_feature_name);
        booleanState.dualTarget = true;
        booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_quick_panel_icon_extra_dim);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUserSwitch(int i) {
        handleRefreshState(Integer.valueOf(getValue()));
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        boolean z;
        if (!this.mIsAvailable) {
            return false;
        }
        Resources resources = this.mContext.getResources();
        int identifier = Resources.getSystem().getIdentifier("config_setColorTransformAccelerated", "bool", "android");
        if (SystemProperties.getBoolean("ro.surface_flinger.protected_contents", false)) {
            String str = SystemProperties.get("ro.product.vendor.device");
            if (str != null) {
                Iterator it = excludeDeviceNameSet.iterator();
                while (it.hasNext()) {
                    if (str.startsWith((String) it.next())) {
                    }
                }
            }
            z = true;
            return identifier <= 0 ? false : false;
        }
        z = false;
        return identifier <= 0 ? false : false;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.qs.ReduceBrightColorsController.Listener
    public final void onActivated(boolean z) {
        refreshState(null);
    }
}
