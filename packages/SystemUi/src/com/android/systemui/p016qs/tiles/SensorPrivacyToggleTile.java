package com.android.systemui.p016qs.tiles;

import android.content.Context;
import android.content.DialogInterface;
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
import com.android.systemui.p016qs.QSHost;
import com.android.systemui.p016qs.QsEventLogger;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.p016qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.p016qs.tileimpl.QSTileImpl;
import com.android.systemui.p016qs.tileimpl.SQSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p013qs.DetailAdapter;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class SensorPrivacyToggleTile extends SQSTileImpl implements IndividualSensorPrivacyController.Callback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final KeyguardStateController mKeyguard;
    public final PanelInteractor mPanelInteractor;
    public final IndividualSensorPrivacyController mSensorPrivacyController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class SensorPrivacyToggleDetailAdapter implements DetailAdapter {
        public TextView mDetailSummary;

        public SensorPrivacyToggleDetailAdapter() {
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
            int i = SensorPrivacyToggleTile.$r8$clinit;
            View inflate = LayoutInflater.from(SensorPrivacyToggleTile.this.mContext).inflate(R.layout.sec_qs_detail_text, viewGroup, false);
            TextView textView = (TextView) inflate.findViewById(R.id.message);
            this.mDetailSummary = textView;
            textView.setText(getDetailSummary());
            return inflate;
        }

        public abstract String getDetailSummary();

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final int getMetricsCategory() {
            return 1598;
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final Intent getSettingsIntent() {
            return SensorPrivacyToggleTile.this.getLongClickIntent();
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final Boolean getToggleState() {
            int i = SensorPrivacyToggleTile.$r8$clinit;
            return Boolean.valueOf(((QSTile.BooleanState) SensorPrivacyToggleTile.this.mState).value);
        }

        @Override // com.android.systemui.plugins.p013qs.DetailAdapter
        public final void setToggleState(boolean z) {
            SensorPrivacyToggleTile sensorPrivacyToggleTile = SensorPrivacyToggleTile.this;
            KeyguardStateController keyguardStateController = sensorPrivacyToggleTile.mKeyguard;
            if (((KeyguardStateControllerImpl) keyguardStateController).mSecure && ((KeyguardStateControllerImpl) keyguardStateController).mShowing) {
                sensorPrivacyToggleTile.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.SensorPrivacyToggleTile$SensorPrivacyToggleDetailAdapter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SensorPrivacyToggleTile sensorPrivacyToggleTile2 = SensorPrivacyToggleTile.this;
                        ((IndividualSensorPrivacyControllerImpl) sensorPrivacyToggleTile2.mSensorPrivacyController).setSensorBlocked(1, sensorPrivacyToggleTile2.getSensorId(), !((IndividualSensorPrivacyControllerImpl) sensorPrivacyToggleTile2.mSensorPrivacyController).isSensorBlocked(sensorPrivacyToggleTile2.getSensorId()));
                    }
                });
                return;
            }
            ((IndividualSensorPrivacyControllerImpl) sensorPrivacyToggleTile.mSensorPrivacyController).setSensorBlocked(1, sensorPrivacyToggleTile.getSensorId(), !((IndividualSensorPrivacyControllerImpl) sensorPrivacyToggleTile.mSensorPrivacyController).isSensorBlocked(sensorPrivacyToggleTile.getSensorId()));
            sensorPrivacyToggleTile.fireToggleStateChanged(z);
            Log.d("SensorPrivacyToggleTile", "setToggleState:" + z);
            this.mDetailSummary.setText(getDetailSummary());
        }
    }

    public SensorPrivacyToggleTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, IndividualSensorPrivacyController individualSensorPrivacyController, KeyguardStateController keyguardStateController, PanelInteractor panelInteractor) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mSensorPrivacyController = individualSensorPrivacyController;
        this.mKeyguard = keyguardStateController;
        individualSensorPrivacyController.observe(((QSTileImpl) this).mLifecycle, this);
        this.mPanelInteractor = panelInteractor;
    }

    public abstract int getIconRes();

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("android.settings.PRIVACY_SETTINGS");
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final int getMetricsCategory() {
        return 0;
    }

    public abstract String getRestriction();

    public abstract int getSensorId();

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleClick(View view) {
        int sensorId = getSensorId();
        IndividualSensorPrivacyControllerImpl individualSensorPrivacyControllerImpl = (IndividualSensorPrivacyControllerImpl) this.mSensorPrivacyController;
        final boolean isSensorBlocked = individualSensorPrivacyControllerImpl.isSensorBlocked(sensorId);
        if (individualSensorPrivacyControllerImpl.mSensorPrivacyManager.requiresAuthentication()) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguard;
            if (keyguardStateControllerImpl.mSecure && keyguardStateControllerImpl.mShowing) {
                this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable(isSensorBlocked) { // from class: com.android.systemui.qs.tiles.SensorPrivacyToggleTile$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SensorPrivacyToggleTile.this.toggleTileState();
                    }
                });
                return;
            }
        }
        toggleTileState();
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        boolean isSensorBlocked = obj == null ? ((IndividualSensorPrivacyControllerImpl) this.mSensorPrivacyController).isSensorBlocked(getSensorId()) : ((Boolean) obj).booleanValue();
        checkIfRestrictionEnforcedByAdminOnly(booleanState, getRestriction());
        booleanState.icon = QSTileImpl.ResourceIcon.get(getIconRes());
        booleanState.state = isSensorBlocked ? 1 : 2;
        booleanState.value = !isSensorBlocked;
        booleanState.label = getTileLabel();
        booleanState.dualTarget = true;
        booleanState.contentDescription = ((Object) booleanState.label) + "," + this.mContext.getString(booleanState.value ? R.string.accessibility_desc_on : R.string.accessibility_desc_off);
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.statusbar.policy.IndividualSensorPrivacyController.Callback
    public final void onSensorBlockedChanged(int i, boolean z) {
        if (i == getSensorId()) {
            refreshState(Boolean.valueOf(z));
        }
    }

    public final void toggleTileState() {
        String str;
        String str2;
        int sensorId = getSensorId();
        IndividualSensorPrivacyController individualSensorPrivacyController = this.mSensorPrivacyController;
        final int i = 1;
        if (((IndividualSensorPrivacyControllerImpl) individualSensorPrivacyController).isSensorBlocked(sensorId)) {
            IndividualSensorPrivacyControllerImpl individualSensorPrivacyControllerImpl = (IndividualSensorPrivacyControllerImpl) individualSensorPrivacyController;
            ((IndividualSensorPrivacyControllerImpl) individualSensorPrivacyController).setSensorBlocked(1, getSensorId(), !individualSensorPrivacyControllerImpl.isSensorBlocked(getSensorId()));
            return;
        }
        int sensorId2 = getSensorId();
        Context context = this.mContext;
        if (sensorId2 == 2) {
            str = context.getString(R.string.block_access_camera_dialog_title);
            str2 = context.getString(R.string.turn_off_camera_dialog_message);
        } else if (getSensorId() == 1) {
            str = context.getString(R.string.block_access_microphone_dialog_title);
            str2 = context.getString(R.string.turn_off_microphone_dialog_message);
        } else {
            str = "";
            str2 = str;
        }
        if (str.equals("")) {
            return;
        }
        final int i2 = 0;
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(this) { // from class: com.android.systemui.qs.tiles.SensorPrivacyToggleTile$$ExternalSyntheticLambda1
            public final /* synthetic */ SensorPrivacyToggleTile f$0;

            {
                this.f$0 = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i3) {
                switch (i2) {
                    case 0:
                        SensorPrivacyToggleTile sensorPrivacyToggleTile = this.f$0;
                        int sensorId3 = sensorPrivacyToggleTile.getSensorId();
                        int sensorId4 = sensorPrivacyToggleTile.getSensorId();
                        ((IndividualSensorPrivacyControllerImpl) sensorPrivacyToggleTile.mSensorPrivacyController).setSensorBlocked(1, sensorId3, !r1.isSensorBlocked(sensorId4));
                        sensorPrivacyToggleTile.refreshState(null);
                        break;
                    default:
                        this.f$0.refreshState(null);
                        break;
                }
            }
        };
        DialogInterface.OnClickListener onClickListener2 = new DialogInterface.OnClickListener(this) { // from class: com.android.systemui.qs.tiles.SensorPrivacyToggleTile$$ExternalSyntheticLambda1
            public final /* synthetic */ SensorPrivacyToggleTile f$0;

            {
                this.f$0 = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i3) {
                switch (i) {
                    case 0:
                        SensorPrivacyToggleTile sensorPrivacyToggleTile = this.f$0;
                        int sensorId3 = sensorPrivacyToggleTile.getSensorId();
                        int sensorId4 = sensorPrivacyToggleTile.getSensorId();
                        ((IndividualSensorPrivacyControllerImpl) sensorPrivacyToggleTile.mSensorPrivacyController).setSensorBlocked(1, sensorId3, !r1.isSensorBlocked(sensorId4));
                        sensorPrivacyToggleTile.refreshState(null);
                        break;
                    default:
                        this.f$0.refreshState(null);
                        break;
                }
            }
        };
        SystemUIDialog systemUIDialog = new SystemUIDialog(context, 2132018528);
        systemUIDialog.setTitle(str);
        systemUIDialog.setMessage(str2);
        systemUIDialog.setPositiveButton(R.string.block_privacy_toggle_dialog_button, onClickListener);
        systemUIDialog.setNegativeButton(R.string.qs_sensor_privacy_dialog_cancel, onClickListener2);
        this.mPanelInteractor.collapsePanels();
        systemUIDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.tiles.SensorPrivacyToggleTile$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                SensorPrivacyToggleTile.this.refreshState(null);
            }
        });
        systemUIDialog.show();
    }
}
