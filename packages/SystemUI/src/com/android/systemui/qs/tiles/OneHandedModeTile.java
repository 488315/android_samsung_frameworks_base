package com.android.systemui.qs.tiles;

import android.R;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.UserSettingObserver;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.settings.SecureSettings;
import com.android.wm.shell.onehanded.OneHanded;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class OneHandedModeTile extends QSTileImpl {
    public final QSTile.Icon mIcon;
    public final AnonymousClass1 mSetting;

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.qs.tiles.OneHandedModeTile$1] */
    public OneHandedModeTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, UserTracker userTracker, SecureSettings secureSettings) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIcon = QSTileImpl.ResourceIcon.get(R.drawable.ic_storage_48dp);
        this.mSetting = new UserSettingObserver(secureSettings, this.mHandler, "one_handed_mode_enabled", ((UserTrackerImpl) userTracker).getUserId()) { // from class: com.android.systemui.qs.tiles.OneHandedModeTile.1
            @Override // com.android.systemui.qs.UserSettingObserver
            public final void handleValueChanged(int i) {
                OneHandedModeTile.this.handleRefreshState(Integer.valueOf(i));
            }
        };
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("android.settings.action.ONE_HANDED_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(com.android.systemui.R.string.quick_settings_onehanded_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(Expandable expandable) {
        int i = !((QSTile.BooleanState) this.mState).value ? 1 : 0;
        AnonymousClass1 anonymousClass1 = this.mSetting;
        anonymousClass1.mSettingsProxy.putIntForUser(anonymousClass1.mSettingName, i, anonymousClass1.mUserId);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        setListening(false);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        setListening(z);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        booleanState.value = (obj instanceof Integer ? ((Integer) obj).intValue() : getValue()) != 0;
        String string = this.mContext.getString(com.android.systemui.R.string.quick_settings_onehanded_label);
        booleanState.label = string;
        booleanState.icon = this.mIcon;
        booleanState.state = booleanState.value ? 2 : 1;
        booleanState.contentDescription = string;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUserSwitch(int i) {
        AnonymousClass1 anonymousClass1 = this.mSetting;
        anonymousClass1.setUserId(i);
        handleRefreshState(Integer.valueOf(anonymousClass1.getValue()));
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        return isSupportOneHandedMode();
    }

    public boolean isSupportOneHandedMode() {
        return OneHanded.sIsSupportOneHandedMode;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }
}
