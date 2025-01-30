package com.android.systemui.p016qs.tiles;

import android.os.Handler;
import android.os.Looper;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.DejankUtils;
import com.android.systemui.R;
import com.android.systemui.p016qs.QSHost;
import com.android.systemui.p016qs.QsEventLogger;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.p016qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.p016qs.tiles.SensorPrivacyToggleTile;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p013qs.DetailAdapter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CameraToggleTile extends SensorPrivacyToggleTile {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CameraToggleDetailAdapter mDetailAdapter;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CameraToggleDetailAdapter extends SensorPrivacyToggleTile.SensorPrivacyToggleDetailAdapter {
        public CameraToggleDetailAdapter() {
            super();
        }

        @Override // com.android.systemui.qs.tiles.SensorPrivacyToggleTile.SensorPrivacyToggleDetailAdapter
        public final String getDetailSummary() {
            int i = CameraToggleTile.$r8$clinit;
            return CameraToggleTile.this.mContext.getString(R.string.qs_camera_toggle_detail_summary);
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final CharSequence getTitle() {
            int i = CameraToggleTile.$r8$clinit;
            return CameraToggleTile.this.mContext.getString(R.string.qs_camera_toggle_detail_title);
        }
    }

    public CameraToggleTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, MetricsLogger metricsLogger, FalsingManager falsingManager, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, IndividualSensorPrivacyController individualSensorPrivacyController, KeyguardStateController keyguardStateController, PanelInteractor panelInteractor) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger, individualSensorPrivacyController, keyguardStateController, panelInteractor);
        this.mDetailAdapter = new CameraToggleDetailAdapter();
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final DetailAdapter getDetailAdapter() {
        return this.mDetailAdapter;
    }

    @Override // com.android.systemui.p016qs.tiles.SensorPrivacyToggleTile
    public final int getIconRes() {
        return R.drawable.quick_panel_icon_cameraaccess;
    }

    @Override // com.android.systemui.p016qs.tiles.SensorPrivacyToggleTile
    public final String getRestriction() {
        return "disallow_camera_toggle";
    }

    @Override // com.android.systemui.p016qs.tiles.SensorPrivacyToggleTile
    public final int getSensorId() {
        return 2;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.sec_quick_settings_camera_label);
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final boolean isAvailable() {
        return ((IndividualSensorPrivacyControllerImpl) this.mSensorPrivacyController).mSensorPrivacyManager.supportsSensorToggle(2) && ((Boolean) DejankUtils.whitelistIpcs(new CameraToggleTile$$ExternalSyntheticLambda0())).booleanValue();
    }
}
