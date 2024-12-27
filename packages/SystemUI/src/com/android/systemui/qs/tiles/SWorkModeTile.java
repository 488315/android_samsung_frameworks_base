package com.android.systemui.qs.tiles;

import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.statusbar.phone.ManagedProfileController;
import com.android.systemui.statusbar.phone.ManagedProfileControllerImpl;
import java.util.function.Supplier;

public final class SWorkModeTile extends SQSTileImpl implements ManagedProfileController.Callback {
    public final QSTile.Icon mIcon;
    public final ManagedProfileController mProfileController;

    public SWorkModeTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, ManagedProfileController managedProfileController) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIcon = QSTileImpl.ResourceIcon.get(R.drawable.sec_stat_sys_managed_profile_status);
        this.mProfileController = managedProfileController;
        managedProfileController.observe(((QSTileImpl) this).mLifecycle, this);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("android.settings.MANAGED_PROFILE_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 257;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getResources().getString("SystemUi.QS_WORK_PROFILE_LABEL", new Supplier() { // from class: com.android.systemui.qs.tiles.SWorkModeTile$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return SWorkModeTile.this.mContext.getString(R.string.quick_settings_work_mode_label);
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(Expandable expandable) {
        ((ManagedProfileControllerImpl) this.mProfileController).setWorkModeEnabled(!((QSTile.BooleanState) this.mState).value);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        if (!isAvailable()) {
            onManagedProfileRemoved();
        }
        if (obj instanceof Boolean) {
            booleanState.value = ((Boolean) obj).booleanValue();
        } else {
            booleanState.value = ((ManagedProfileControllerImpl) this.mProfileController).isWorkModeEnabled();
        }
        booleanState.icon = this.mIcon;
        CharSequence tileLabel = getTileLabel();
        booleanState.label = tileLabel;
        booleanState.contentDescription = tileLabel;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        booleanState.state = booleanState.value ? 2 : 1;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        if (((ManagedProfileControllerImpl) this.mProfileController).hasActiveProfile()) {
            if (!this.mHost.shouldBeHiddenByKnox(this.mTileSpec)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.statusbar.phone.ManagedProfileController.Callback
    public final void onManagedProfileChanged() {
        ManagedProfileControllerImpl managedProfileControllerImpl = (ManagedProfileControllerImpl) this.mProfileController;
        if (managedProfileControllerImpl.hasActiveProfile()) {
            refreshState(Boolean.valueOf(managedProfileControllerImpl.isWorkModeEnabled()));
        } else {
            onManagedProfileRemoved();
        }
    }

    @Override // com.android.systemui.statusbar.phone.ManagedProfileController.Callback
    public final void onManagedProfileRemoved() {
        Log.d("SWorkModeTile", "onManagedProfileRemoved");
        this.mHost.removeTile(this.mTileSpec);
    }
}
