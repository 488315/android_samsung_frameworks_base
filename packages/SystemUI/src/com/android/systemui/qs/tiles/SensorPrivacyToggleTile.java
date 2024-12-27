package com.android.systemui.qs.tiles;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.safetycenter.SafetyCenterManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;

public abstract class SensorPrivacyToggleTile extends SQSTileImpl implements IndividualSensorPrivacyController.Callback {
    public SystemUIDialog mDialog;
    public final Boolean mIsSafetyCenterEnabled;
    public final KeyguardStateController mKeyguard;
    public final PanelInteractor mPanelInteractor;
    public final IndividualSensorPrivacyController mSensorPrivacyController;

    public abstract class SensorPrivacyToggleDetailAdapter implements DetailAdapter {
        public TextView mDetailSummary;

        public SensorPrivacyToggleDetailAdapter() {
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
            View inflate = LayoutInflater.from(SensorPrivacyToggleTile.this.mContext).inflate(R.layout.sec_qs_detail_text, viewGroup, false);
            TextView textView = (TextView) inflate.findViewById(R.id.message);
            this.mDetailSummary = textView;
            textView.setText(getDetailSummary());
            return inflate;
        }

        public abstract String getDetailSummary();

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final int getMetricsCategory() {
            return 1598;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final Intent getSettingsIntent() {
            return SensorPrivacyToggleTile.this.getLongClickIntent();
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final Boolean getToggleState() {
            return Boolean.valueOf(((QSTile.BooleanState) SensorPrivacyToggleTile.this.mState).value);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final void setToggleState(boolean z) {
            SensorPrivacyToggleTile sensorPrivacyToggleTile = SensorPrivacyToggleTile.this;
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) sensorPrivacyToggleTile.mKeyguard;
            if (keyguardStateControllerImpl.mSecure && keyguardStateControllerImpl.mShowing) {
                sensorPrivacyToggleTile.mActivityStarter.postQSRunnableDismissingKeyguard(new SensorPrivacyToggleTile$$ExternalSyntheticLambda0(this));
                return;
            }
            ((IndividualSensorPrivacyControllerImpl) sensorPrivacyToggleTile.mSensorPrivacyController).setSensorBlocked(1, sensorPrivacyToggleTile.getSensorId(), !((IndividualSensorPrivacyControllerImpl) sensorPrivacyToggleTile.mSensorPrivacyController).isSensorBlocked(sensorPrivacyToggleTile.getSensorId()));
            sensorPrivacyToggleTile.fireToggleStateChanged(z);
            Log.d(sensorPrivacyToggleTile.TAG, "setToggleState:" + z);
            this.mDetailSummary.setText(getDetailSummary());
        }
    }

    public SensorPrivacyToggleTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, IndividualSensorPrivacyController individualSensorPrivacyController, KeyguardStateController keyguardStateController, SafetyCenterManager safetyCenterManager, PanelInteractor panelInteractor) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mSensorPrivacyController = individualSensorPrivacyController;
        this.mKeyguard = keyguardStateController;
        this.mIsSafetyCenterEnabled = Boolean.valueOf(safetyCenterManager.isSafetyCenterEnabled());
        individualSensorPrivacyController.observe(((QSTileImpl) this).mLifecycle, this);
        this.mPanelInteractor = panelInteractor;
    }

    public abstract int getIconRes(boolean z);

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return this.mIsSafetyCenterEnabled.booleanValue() ? new Intent("android.settings.PRIVACY_CONTROLS") : new Intent("android.settings.PRIVACY_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 0;
    }

    public abstract String getRestriction();

    public abstract int getSensorId();

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(Expandable expandable) {
        int sensorId = getSensorId();
        IndividualSensorPrivacyControllerImpl individualSensorPrivacyControllerImpl = (IndividualSensorPrivacyControllerImpl) this.mSensorPrivacyController;
        boolean isSensorBlocked = individualSensorPrivacyControllerImpl.isSensorBlocked(sensorId);
        if (individualSensorPrivacyControllerImpl.mSensorPrivacyManager.requiresAuthentication()) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguard;
            if (keyguardStateControllerImpl.mSecure && keyguardStateControllerImpl.mShowing) {
                this.mActivityStarter.postQSRunnableDismissingKeyguard(new SensorPrivacyToggleTile$$ExternalSyntheticLambda0(this, isSensorBlocked));
                return;
            }
        }
        toggleTileState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        boolean isSensorBlocked = obj == null ? ((IndividualSensorPrivacyControllerImpl) this.mSensorPrivacyController).isSensorBlocked(getSensorId()) : ((Boolean) obj).booleanValue();
        checkIfRestrictionEnforcedByAdminOnly(booleanState, getRestriction());
        booleanState.icon = QSTileImpl.ResourceIcon.get(getIconRes(isSensorBlocked));
        booleanState.state = isSensorBlocked ? 1 : 2;
        booleanState.value = !isSensorBlocked;
        CharSequence tileLabel = getTileLabel();
        booleanState.label = tileLabel;
        booleanState.dualTarget = true;
        booleanState.contentDescription = tileLabel;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
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
        IndividualSensorPrivacyControllerImpl individualSensorPrivacyControllerImpl = (IndividualSensorPrivacyControllerImpl) this.mSensorPrivacyController;
        if (individualSensorPrivacyControllerImpl.isSensorBlocked(sensorId)) {
            individualSensorPrivacyControllerImpl.setSensorBlocked(1, getSensorId(), !individualSensorPrivacyControllerImpl.isSensorBlocked(getSensorId()));
            return;
        }
        if (getSensorId() == 2) {
            str = this.mContext.getString(R.string.block_access_camera_dialog_title);
            str2 = this.mContext.getString(R.string.turn_off_camera_dialog_message);
        } else if (getSensorId() == 1) {
            str = this.mContext.getString(R.string.block_access_microphone_dialog_title);
            str2 = this.mContext.getString(R.string.turn_off_microphone_dialog_message);
        } else {
            str = "";
            str2 = str;
        }
        if (str.equals("")) {
            return;
        }
        final int i = 0;
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener(this) { // from class: com.android.systemui.qs.tiles.SensorPrivacyToggleTile$$ExternalSyntheticLambda1
            public final /* synthetic */ SensorPrivacyToggleTile f$0;

            {
                this.f$0 = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                int i3 = i;
                SensorPrivacyToggleTile sensorPrivacyToggleTile = this.f$0;
                switch (i3) {
                    case 0:
                        int sensorId2 = sensorPrivacyToggleTile.getSensorId();
                        int sensorId3 = sensorPrivacyToggleTile.getSensorId();
                        ((IndividualSensorPrivacyControllerImpl) sensorPrivacyToggleTile.mSensorPrivacyController).setSensorBlocked(1, sensorId2, !r0.isSensorBlocked(sensorId3));
                        String str3 = null;
                        sensorPrivacyToggleTile.refreshState(null);
                        if (sensorPrivacyToggleTile.mDialog != null) {
                            if (sensorPrivacyToggleTile.getSensorId() == 2) {
                                str3 = sensorPrivacyToggleTile.mContext.getString(R.string.sensor_privacy_start_use_camera_blocked_dialog_title);
                            } else if (sensorPrivacyToggleTile.getSensorId() == 1) {
                                str3 = sensorPrivacyToggleTile.mContext.getString(R.string.sensor_privacy_start_use_mic_blocked_dialog_title);
                            }
                            if (str3 != null) {
                                sensorPrivacyToggleTile.mDialog.sendAnnouncementEvent(str3);
                                break;
                            }
                        }
                        break;
                    default:
                        sensorPrivacyToggleTile.refreshState(null);
                        break;
                }
            }
        };
        final int i2 = 1;
        DialogInterface.OnClickListener onClickListener2 = new DialogInterface.OnClickListener(this) { // from class: com.android.systemui.qs.tiles.SensorPrivacyToggleTile$$ExternalSyntheticLambda1
            public final /* synthetic */ SensorPrivacyToggleTile f$0;

            {
                this.f$0 = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i22) {
                int i3 = i2;
                SensorPrivacyToggleTile sensorPrivacyToggleTile = this.f$0;
                switch (i3) {
                    case 0:
                        int sensorId2 = sensorPrivacyToggleTile.getSensorId();
                        int sensorId3 = sensorPrivacyToggleTile.getSensorId();
                        ((IndividualSensorPrivacyControllerImpl) sensorPrivacyToggleTile.mSensorPrivacyController).setSensorBlocked(1, sensorId2, !r0.isSensorBlocked(sensorId3));
                        String str3 = null;
                        sensorPrivacyToggleTile.refreshState(null);
                        if (sensorPrivacyToggleTile.mDialog != null) {
                            if (sensorPrivacyToggleTile.getSensorId() == 2) {
                                str3 = sensorPrivacyToggleTile.mContext.getString(R.string.sensor_privacy_start_use_camera_blocked_dialog_title);
                            } else if (sensorPrivacyToggleTile.getSensorId() == 1) {
                                str3 = sensorPrivacyToggleTile.mContext.getString(R.string.sensor_privacy_start_use_mic_blocked_dialog_title);
                            }
                            if (str3 != null) {
                                sensorPrivacyToggleTile.mDialog.sendAnnouncementEvent(str3);
                                break;
                            }
                        }
                        break;
                    default:
                        sensorPrivacyToggleTile.refreshState(null);
                        break;
                }
            }
        };
        SystemUIDialog systemUIDialog = new SystemUIDialog(this.mContext, R.style.Theme_SystemUI_Dialog_Alert);
        this.mDialog = systemUIDialog;
        systemUIDialog.setTitle(str);
        this.mDialog.setMessage(str2);
        this.mDialog.setPositiveButton(R.string.block_privacy_toggle_dialog_button, onClickListener);
        this.mDialog.setNegativeButton(R.string.qs_sensor_privacy_dialog_cancel, onClickListener2);
        this.mPanelInteractor.collapsePanels();
        this.mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.tiles.SensorPrivacyToggleTile$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                SensorPrivacyToggleTile.this.refreshState(null);
            }
        });
        this.mDialog.show();
    }
}
