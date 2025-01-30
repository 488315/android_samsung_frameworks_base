package com.android.systemui.qs.tiles;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.ManagedProfileController;
import com.android.systemui.statusbar.phone.ManagedProfileControllerImpl;
import com.android.systemui.util.DeviceType;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SWorkModeTile extends SQSTileImpl implements ManagedProfileController.Callback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final WorkModeDetailAdapter mDetailAdapter;
    public final QSTile.Icon mIcon;
    public final ManagedProfileController mProfileController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WorkModeDetailAdapter implements DetailAdapter {
        public TextView mSummary;

        public /* synthetic */ WorkModeDetailAdapter(SWorkModeTile sWorkModeTile, int i) {
            this();
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
            int i = SWorkModeTile.$r8$clinit;
            SWorkModeTile sWorkModeTile = SWorkModeTile.this;
            View inflate = LayoutInflater.from(sWorkModeTile.mContext).inflate(R.layout.sec_qs_detail_text, viewGroup, false);
            this.mSummary = (TextView) inflate.findViewById(R.id.message);
            this.mSummary.setText(DeviceType.isTablet() ? sWorkModeTile.mContext.getString(R.string.quick_settings_workmode_detail_summary_tablet_work) : sWorkModeTile.mContext.getString(R.string.quick_settings_workmode_detail_summary_work));
            return inflate;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final int getMetricsCategory() {
            return 257;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final Intent getSettingsIntent() {
            return SWorkModeTile.this.getLongClickIntent();
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final CharSequence getTitle() {
            int i = SWorkModeTile.$r8$clinit;
            return SWorkModeTile.this.mContext.getString(R.string.quick_settings_work_mode_label);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final Boolean getToggleState() {
            int i = SWorkModeTile.$r8$clinit;
            return Boolean.valueOf(((QSTile.BooleanState) SWorkModeTile.this.mState).value);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final void setToggleState(boolean z) {
            ((ManagedProfileControllerImpl) SWorkModeTile.this.mProfileController).setWorkModeEnabled(z);
        }

        private WorkModeDetailAdapter() {
        }
    }

    public SWorkModeTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, ManagedProfileController managedProfileController) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIcon = QSTileImpl.ResourceIcon.get(R.drawable.sec_stat_sys_managed_profile_status);
        this.mDetailAdapter = new WorkModeDetailAdapter(this, 0);
        this.mProfileController = managedProfileController;
        managedProfileController.observe(((QSTileImpl) this).mLifecycle, this);
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final DetailAdapter getDetailAdapter() {
        return this.mDetailAdapter;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("android.settings.MANAGED_PROFILE_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 257;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final CharSequence getTileLabel() {
        return ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getResources().getString("SystemUi.QS_WORK_PROFILE_LABEL", new Supplier() { // from class: com.android.systemui.qs.tiles.SWorkModeTile$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return SWorkModeTile.this.mContext.getString(R.string.quick_settings_work_mode_label);
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(View view) {
        ((ManagedProfileControllerImpl) this.mProfileController).setWorkModeEnabled(!((QSTile.BooleanState) this.mState).value);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        if (!isAvailable()) {
            onManagedProfileRemoved();
        }
        if (booleanState.slash == null) {
            booleanState.slash = new QSTile.SlashState();
        }
        if (obj instanceof Boolean) {
            booleanState.value = ((Boolean) obj).booleanValue();
        } else {
            booleanState.value = ((ManagedProfileControllerImpl) this.mProfileController).isWorkModeEnabled();
        }
        booleanState.icon = this.mIcon;
        if (booleanState.value) {
            booleanState.slash.isSlashed = false;
        } else {
            booleanState.slash.isSlashed = true;
        }
        CharSequence tileLabel = getTileLabel();
        booleanState.label = tileLabel;
        booleanState.contentDescription = tileLabel;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        booleanState.state = booleanState.value ? 2 : 1;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
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
