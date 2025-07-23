package com.android.systemui.qs.tiles;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.Operator;
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
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.statusbar.policy.LocationControllerImpl;
import com.android.systemui.statusbar.policy.SatelliteModeObserver$SatelliteModeCallback;
import com.android.systemui.statusbar.policy.SatelliteModeObserverHelper;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class LocationTile extends SQSTileImpl {
    public final ActivityStarter mActivityStarter;
    public final LocationController mController;
    public final QSTileImpl.AnimationIcon mDisable;
    public final QSTileImpl.AnimationIcon mEnable;
    public boolean mIsSatelliteModeOn;
    public final KeyguardStateController mKeyguard;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public boolean mListening;
    public final PanelInteractor mPanelInteractor;
    public final AnonymousClass1 mSatelliteModeCallback;
    public final SatelliteModeObserverHelper mSatelliteModeObserverHelper;
    private final SettingsHelper mSettingsHelper;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Callback implements LocationController.LocationChangeCallback, KeyguardStateController.Callback {
        public /* synthetic */ Callback(LocationTile locationTile, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
        public final void onKeyguardShowingChanged() {
            LocationTile.this.refreshState(null);
        }

        @Override // com.android.systemui.statusbar.policy.LocationController.LocationChangeCallback
        public final void onLocationSettingsChanged(boolean z) {
            LocationTile.this.refreshState(null);
        }

        private Callback() {
        }
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.qs.tiles.LocationTile$1] */
    public LocationTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, LocationController locationController, KeyguardStateController keyguardStateController, PanelInteractor panelInteractor, KeyguardUpdateMonitor keyguardUpdateMonitor, SatelliteModeObserverHelper satelliteModeObserverHelper, SettingsHelper settingsHelper) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        QSTileImpl.ResourceIcon.get(R.drawable.ic_location);
        Callback callback = new Callback(this, 0);
        this.mEnable = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_location_on, R.drawable.quick_panel_icon_location_012);
        this.mDisable = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_location_off, R.drawable.quick_panel_icon_location_000);
        this.mListening = false;
        this.mIsSatelliteModeOn = false;
        this.mSatelliteModeCallback = new SatelliteModeObserver$SatelliteModeCallback() { // from class: com.android.systemui.qs.tiles.LocationTile.1
            @Override // com.android.systemui.statusbar.policy.SatelliteModeObserver$SatelliteModeCallback
            public final void onSatelliteModeChanged(boolean z) {
                LocationTile locationTile = LocationTile.this;
                locationTile.mIsSatelliteModeOn = z;
                locationTile.refreshState(null);
            }
        };
        this.mController = locationController;
        this.mKeyguard = keyguardStateController;
        this.mPanelInteractor = panelInteractor;
        this.mActivityStarter = activityStarter;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mSatelliteModeObserverHelper = satelliteModeObserverHelper;
        this.mSettingsHelper = settingsHelper;
        locationController.getClass();
        locationController.observe(((QSTileImpl) this).mLifecycle, callback);
        keyguardStateController.getClass();
        keyguardStateController.observe(((QSTileImpl) this).mLifecycle, callback);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        if (this.mIsSatelliteModeOn) {
            return null;
        }
        return new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 122;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_location_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final Expandable expandable) {
        if (((QSTile.BooleanState) this.mState).state == 0) {
            return;
        }
        EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor;
        boolean z = false;
        if (edmMonitor != null) {
            Context context = edmMonitor.knoxStateMonitor.mContext;
            boolean z2 = edmMonitor.mSettingsChangesAllowed;
            boolean booleanValue = edmMonitor.mLocationProviderAllowed.get("gps") != null ? ((Boolean) edmMonitor.mLocationProviderAllowed.get("gps")).booleanValue() : true;
            boolean booleanValue2 = edmMonitor.mLocationProviderAllowed.get("network") != null ? ((Boolean) edmMonitor.mLocationProviderAllowed.get("network")).booleanValue() : true;
            boolean z3 = edmMonitor.mGPSStateChangeAllowed;
            boolean isLocationProviderEnabled = Settings.Secure.isLocationProviderEnabled(edmMonitor.knoxStateMonitor.mContext.getContentResolver(), "gps");
            boolean z4 = (booleanValue && (isLocationProviderEnabled || z3)) ? false : true;
            boolean z5 = isLocationProviderEnabled && !z3;
            boolean z6 = ((!z4 || booleanValue2) && z2 && z3) ? false : true;
            if (z5) {
                z6 = true;
            }
            if (z6) {
                z = true;
            }
        }
        if (z) {
            showItPolicyToast();
            return;
        }
        if (((KeyguardStateControllerImpl) this.mKeyguard).mShowing) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && this.mSettingsHelper.isLockFunctionsEnabled()) {
                this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.LocationTile$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        LocationTile.this.handleClick(expandable);
                    }
                });
                return;
            }
        }
        boolean z7 = ((QSTile.BooleanState) this.mState).value;
        Log.d(this.TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("handleClick ", z7));
        boolean z8 = Operator.QUICK_IS_DCM_BRANDING;
        LocationController locationController = this.mController;
        if (!z8 || ((LocationControllerImpl) locationController).isLocationEnabled$1()) {
            ((LocationControllerImpl) locationController).setLocationEnabled(!z7);
            return;
        }
        String string = this.mContext.getResources().getString(R.string.qs_location_consent_dialog_title_vzw);
        String string2 = DeviceType.isTablet() ? this.mContext.getResources().getString(R.string.qs_location_consent_dialog_body_message_vzw_tablet) : this.mContext.getResources().getString(R.string.qs_location_consent_dialog_body_message_vzw);
        SystemUIDialog systemUIDialog = new SystemUIDialog(this.mContext);
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
                int i3 = i;
                LocationTile locationTile = this.f$0;
                switch (i3) {
                    case 0:
                        ((LocationControllerImpl) locationTile.mController).setLocationEnabled(true);
                        locationTile.refreshState(null);
                        break;
                    default:
                        locationTile.refreshState(null);
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
                int i3 = i2;
                LocationTile locationTile = this.f$0;
                switch (i3) {
                    case 0:
                        ((LocationControllerImpl) locationTile.mController).setLocationEnabled(true);
                        locationTile.refreshState(null);
                        break;
                    default:
                        locationTile.refreshState(null);
                        break;
                }
            }
        });
        systemUIDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.tiles.LocationTile$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                LocationTile.this.refreshState(null);
            }
        });
        ((PanelInteractorImpl) this.mPanelInteractor).collapsePanels();
        systemUIDialog.show();
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(Expandable expandable) {
        if (this.mIsSatelliteModeOn) {
            return;
        }
        showDetail$1(true);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        AnonymousClass1 anonymousClass1 = this.mSatelliteModeCallback;
        SatelliteModeObserverHelper satelliteModeObserverHelper = this.mSatelliteModeObserverHelper;
        if (z) {
            satelliteModeObserverHelper.addCallback(anonymousClass1);
        } else {
            satelliteModeObserverHelper.removeCallback(anonymousClass1);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        LocationControllerImpl locationControllerImpl = (LocationControllerImpl) this.mController;
        boolean isLocationEnabled$1 = locationControllerImpl.isLocationEnabled$1();
        booleanState.dualTarget = true;
        booleanState.value = isLocationEnabled$1;
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_share_location");
        if (!booleanState.disabledByPolicy) {
            checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_config_location");
        }
        booleanState.icon = booleanState.value ? this.mEnable : this.mDisable;
        booleanState.label = getTileLabel();
        if (!this.mIsSatelliteModeOn) {
            if (!((UserManager) locationControllerImpl.mContext.getSystemService("user")).hasUserRestriction("no_share_location", UserHandle.of(ActivityManager.getCurrentUser()))) {
                booleanState.state = booleanState.value ? 2 : 1;
                return;
            }
        }
        booleanState.state = 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }
}
