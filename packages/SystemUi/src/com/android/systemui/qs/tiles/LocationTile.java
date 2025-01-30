package com.android.systemui.qs.tiles;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.os.UserManager;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.Operator;
import com.android.systemui.R;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.statusbar.policy.LocationControllerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LocationTile extends SQSTileImpl {
    public final ActivityStarter mActivityStarter;
    public final LocationController mController;
    public final QSTileImpl.AnimationIcon mDisable;
    public final QSTileImpl.AnimationIcon mEnable;
    public final KeyguardStateController mKeyguard;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final PanelInteractor mPanelInteractor;
    public final SettingsHelper mSettingsHelper;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Callback implements LocationController.LocationChangeCallback, KeyguardStateController.Callback {
        public /* synthetic */ Callback(LocationTile locationTile, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public final void onKeyguardShowingChanged() {
            LocationTile.this.refreshState(null);
        }

        @Override // com.android.systemui.statusbar.policy.LocationController.LocationChangeCallback
        public final void onLocationSettingsChanged() {
            LocationTile.this.refreshState(null);
        }

        private Callback() {
        }
    }

    public LocationTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, LocationController locationController, KeyguardStateController keyguardStateController, PanelInteractor panelInteractor, KeyguardUpdateMonitor keyguardUpdateMonitor, SettingsHelper settingsHelper) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        QSTileImpl.ResourceIcon.get(R.drawable.ic_location);
        Callback callback = new Callback(this, 0);
        this.mEnable = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_location_on, R.drawable.quick_panel_icon_location_012);
        this.mDisable = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_location_off, R.drawable.quick_panel_icon_location_000);
        this.mController = locationController;
        this.mKeyguard = keyguardStateController;
        this.mPanelInteractor = panelInteractor;
        this.mActivityStarter = activityStarter;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mSettingsHelper = settingsHelper;
        locationController.getClass();
        locationController.observe(((QSTileImpl) this).mLifecycle, callback);
        keyguardStateController.getClass();
        keyguardStateController.observe(((QSTileImpl) this).mLifecycle, callback);
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final DetailAdapter getDetailAdapter() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 122;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getResources().getString(R.string.qs_location_detail_default_title);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final View view) {
        if (((QSTile.BooleanState) this.mState).state == 0) {
            return;
        }
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isLocationTileBlocked()) {
            showItPolicyToast();
            return;
        }
        if (((KeyguardStateControllerImpl) this.mKeyguard).mShowing) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && this.mSettingsHelper.isLockFunctionsEnabled()) {
                this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.LocationTile$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        LocationTile.this.handleClick(view);
                    }
                });
                return;
            }
        }
        boolean z = ((QSTile.BooleanState) this.mState).value;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("handleClick ", z, "LocationTile");
        boolean z2 = Operator.QUICK_IS_DCM_BRANDING;
        LocationController locationController = this.mController;
        if (z2 && !((LocationControllerImpl) locationController).isLocationEnabled()) {
            showLocationConsentDialog();
        } else {
            ((LocationControllerImpl) locationController).setLocationEnabled(!z);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(View view) {
        showDetail(true);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        if (booleanState.slash == null) {
            booleanState.slash = new QSTile.SlashState();
        }
        LocationControllerImpl locationControllerImpl = (LocationControllerImpl) this.mController;
        boolean isLocationEnabled = locationControllerImpl.isLocationEnabled();
        booleanState.dualTarget = true;
        booleanState.value = isLocationEnabled;
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_share_location");
        if (!booleanState.disabledByPolicy) {
            checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_config_location");
        }
        booleanState.icon = booleanState.value ? this.mEnable : this.mDisable;
        booleanState.label = getTileLabel();
        booleanState.state = booleanState.value ? 2 : 1;
        if (((UserManager) locationControllerImpl.mContext.getSystemService("user")).hasUserRestriction("no_share_location", UserHandle.of(ActivityManager.getCurrentUser()))) {
            booleanState.state = 0;
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    public final void showLocationConsentDialog() {
        Context context = this.mContext;
        String string = context.getResources().getString(R.string.qs_location_consent_dialog_title_vzw);
        String string2 = DeviceType.isTablet() ? context.getResources().getString(R.string.qs_location_consent_dialog_body_message_vzw_tablet) : context.getResources().getString(R.string.qs_location_consent_dialog_body_message_vzw);
        SystemUIDialog systemUIDialog = new SystemUIDialog(context);
        systemUIDialog.setTitle(string);
        systemUIDialog.setMessage(string2);
        final int i = 0;
        systemUIDialog.setPositiveButton(R.string.agree, new DialogInterface.OnClickListener(this) { // from class: com.android.systemui.qs.tiles.LocationTile$$ExternalSyntheticLambda1
            public final /* synthetic */ LocationTile f$0;

            {
                this.f$0 = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                switch (i) {
                    case 0:
                        LocationTile locationTile = this.f$0;
                        ((LocationControllerImpl) locationTile.mController).setLocationEnabled(true);
                        locationTile.refreshState(null);
                        break;
                    default:
                        this.f$0.refreshState(null);
                        break;
                }
            }
        });
        final int i2 = 1;
        systemUIDialog.setNegativeButton(R.string.disagree, new DialogInterface.OnClickListener(this) { // from class: com.android.systemui.qs.tiles.LocationTile$$ExternalSyntheticLambda1
            public final /* synthetic */ LocationTile f$0;

            {
                this.f$0 = this;
            }

            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i22) {
                switch (i2) {
                    case 0:
                        LocationTile locationTile = this.f$0;
                        ((LocationControllerImpl) locationTile.mController).setLocationEnabled(true);
                        locationTile.refreshState(null);
                        break;
                    default:
                        this.f$0.refreshState(null);
                        break;
                }
            }
        });
        systemUIDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.tiles.LocationTile$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                LocationTile.this.refreshState(null);
            }
        });
        this.mPanelInteractor.collapsePanels();
        systemUIDialog.show();
    }
}
