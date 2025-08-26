package com.android.keyguard;

import android.R;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telecom.TelecomManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.telephony.satellite.SemSatelliteRegistrationStateResult;
import android.telephony.satellite.SemSatelliteServiceState;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.widget.LockPatternUtils;
import com.android.settingslib.WirelessUtils;
import com.android.systemui.CscRune;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.EmergencyDialerConstants;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import com.google.android.msdl.domain.MSDLPlayer;
import com.samsung.android.telecom.SemTelecomManager;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public class EmergencyButtonController extends ViewController {
    public final ActivityTaskManager mActivityTaskManager;
    public final Executor mBackgroundExecutor;
    public boolean mBouncerShowing;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass2 mConfigurationListener;
    public int mCurrentSatellitePhoneId;
    public int mCurrentSimState;
    public EmergencyButtonCallback mEmergencyButtonCallback;
    public final InputMethodManager mImm;
    public final KeyguardUpdateMonitorCallback mInfoCallback;
    public boolean mKeyguardShowing;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final Executor mMainExecutor;
    public final MetricsLogger mMetricsLogger;
    public View mPasswordEntry;
    public final PowerManager mPowerManager;
    public int mSatelliteRegState;
    private SettingsHelper.OnChangedCallback mSatelliteSettingsListener;
    public final Uri[] mSatelliteSettingsValueList;
    public int mSatelliteSubId;
    public final SatelliteTelephonyCallback mSatelliteTelephonyCallback;
    public final SelectedUserInteractor mSelectedUserInteractor;
    private final SettingsHelper.OnChangedCallback mSettingsListener;
    public final Uri[] mSettingsValueList;
    public final ShadeController mShadeController;
    public final TelecomManager mTelecomManager;
    public final TelephonyManager mTelephonyManager;
    public TelephonyManager mTelephonyManagerForSatellite;

    public interface EmergencyButtonCallback {
        void onEmergencyButtonClickedWhenInCall();
    }

    public class Factory {
        public final ActivityTaskManager mActivityTaskManager;
        public final Executor mBackgroundExecutor;
        public final ConfigurationController mConfigurationController;
        public final InputMethodManager mInputMethodManager;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final LockPatternUtils mLockPatternUtils;
        public final MSDLPlayer mMSDLPlayer;
        public final Executor mMainExecutor;
        public final MetricsLogger mMetricsLogger;
        public final PowerManager mPowerManager;
        public final SelectedUserInteractor mSelectedUserInteractor;
        public final ShadeController mShadeController;
        public final TelecomManager mTelecomManager;
        public final TelephonyManager mTelephonyManager;

        public Factory(ConfigurationController configurationController, InputMethodManager inputMethodManager, TelephonyManager telephonyManager, KeyguardUpdateMonitor keyguardUpdateMonitor, PowerManager powerManager, ActivityTaskManager activityTaskManager, ShadeController shadeController, TelecomManager telecomManager, MetricsLogger metricsLogger, LockPatternUtils lockPatternUtils, Executor executor, Executor executor2, SelectedUserInteractor selectedUserInteractor, MSDLPlayer mSDLPlayer) {
            this.mConfigurationController = configurationController;
            this.mInputMethodManager = inputMethodManager;
            this.mTelephonyManager = telephonyManager;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mPowerManager = powerManager;
            this.mActivityTaskManager = activityTaskManager;
            this.mShadeController = shadeController;
            this.mTelecomManager = telecomManager;
            this.mMetricsLogger = metricsLogger;
            this.mLockPatternUtils = lockPatternUtils;
            this.mMainExecutor = executor;
            this.mBackgroundExecutor = executor2;
            this.mSelectedUserInteractor = selectedUserInteractor;
            this.mMSDLPlayer = mSDLPlayer;
        }

        public final EmergencyButtonController create(EmergencyButton emergencyButton) {
            return new EmergencyButtonController(emergencyButton, this.mConfigurationController, this.mInputMethodManager, this.mTelephonyManager, this.mKeyguardUpdateMonitor, this.mPowerManager, this.mActivityTaskManager, this.mShadeController, this.mTelecomManager, this.mMetricsLogger, this.mLockPatternUtils, this.mMainExecutor, this.mBackgroundExecutor, this.mSelectedUserInteractor, this.mMSDLPlayer);
        }
    }

    public class SatelliteTelephonyCallback extends TelephonyCallback implements TelephonyCallback.SemSatelliteStateListener {
        public /* synthetic */ SatelliteTelephonyCallback(EmergencyButtonController emergencyButtonController, int i) {
            this();
        }

        public final void onSemSatelliteServiceStateChanged(SemSatelliteServiceState semSatelliteServiceState) {
            SemSatelliteRegistrationStateResult registrationState = semSatelliteServiceState.getRegistrationState();
            if (registrationState == null) {
                Log.d("EmergencyButton", "onSemSatelliteServiceStateChanged : result is null!");
                return;
            }
            EmergencyButtonController emergencyButtonController = EmergencyButtonController.this;
            int i = emergencyButtonController.mSatelliteRegState;
            emergencyButtonController.mSatelliteRegState = registrationState.getRegState();
            EmergencyButtonController$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "onSemSatelliteServiceStateChanged : mSatelliteRegState changed from [", "] to ["), EmergencyButtonController.this.mSatelliteRegState, "]", "EmergencyButton");
            EmergencyButtonController emergencyButtonController2 = EmergencyButtonController.this;
            if (i != emergencyButtonController2.mSatelliteRegState) {
                emergencyButtonController2.updateEmergencyCallButton();
            }
        }

        private SatelliteTelephonyCallback() {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x00f1, code lost:
    
        if (r0.getVisibility() == 0) goto L52;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void $r8$lambda$W1zTNNSQ2hmvDBkhwJsvlM1sA5k(EmergencyButtonController emergencyButtonController, boolean z, boolean z2) throws NumberFormatException {
        boolean z3;
        boolean zIsOutOfService;
        boolean zIsAirplaneModeOn;
        EmergencyButton emergencyButton = (EmergencyButton) emergencyButtonController.mView;
        boolean zHasSystemFeature = emergencyButtonController.getContext().getPackageManager().hasSystemFeature("android.hardware.telephony");
        emergencyButtonController.mKeyguardUpdateMonitor.isSimPinSecure();
        int i = emergencyButtonController.mCurrentSimState;
        boolean z4 = emergencyButtonController.mBouncerShowing;
        int i2 = emergencyButtonController.mSatelliteRegState;
        emergencyButton.getClass();
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
        if (CscRune.SECURITY_DISABLE_EMERGENCY_CALL_WHEN_OFFLINE) {
            zIsOutOfService = keyguardUpdateMonitor.isOutOfService();
            zIsAirplaneModeOn = WirelessUtils.isAirplaneModeOn(emergencyButton.getContext());
            z3 = i2 == 1 || i2 == 5;
            ActionBarContextView$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("updateEmergencyCallButton isOutOfService = ", " isAirplaneModeOn = ", " isSatelliteRegistered = ", zIsOutOfService, zIsAirplaneModeOn), z3, "EmergencyButton");
        } else {
            z3 = false;
            zIsOutOfService = false;
            zIsAirplaneModeOn = false;
        }
        if ((!CscRune.SECURITY_DISABLE_EMERGENCY_CALL_WHEN_OFFLINE || !zIsOutOfService || z3 || zIsAirplaneModeOn) && zHasSystemFeature) {
            if (!z) {
                if (z2) {
                    Log.d("EmergencyButton", "updateEmergencyCallButton : secure");
                } else if (CscRune.LOCKUI_BOTTOM_USIM_TEXT) {
                    KeyguardUpdateMonitor keyguardUpdateMonitor2 = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
                    if (i == 1 && DeviceState.isNoSimState(emergencyButton.getContext())) {
                        Log.d("EmergencyButton", "SIM_STATE_ABSENT");
                    } else {
                        TelephonyManager telephonyManager = emergencyButton.mTelephonyManager;
                        if (telephonyManager != null && telephonyManager.isVoiceCapable() && keyguardUpdateMonitor2.isEmergencyCallOnly()) {
                            Log.d("EmergencyButton", "EmergencyCallOnly");
                        } else if (i == 5 && CscRune.SECURITY_SKT_USIM_TEXT && !SystemProperties.get("ril.simtype").equals("") && 19 == Integer.valueOf(SystemProperties.get("ril.simtype")).intValue()) {
                            Log.d("EmergencyButton", "SKT Usim unregisterd");
                        } else if (WirelessUtils.isAirplaneModeOn(emergencyButton.getContext())) {
                            Log.d("EmergencyButton", "AirplaneMode On");
                        } else {
                            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Can't match sim state, simState : ", "EmergencyButton");
                        }
                    }
                } else if (z4) {
                }
            }
            emergencyButton.setVisibility(0);
            emergencyButton.setText(z ? R.string.permlab_callCompanionApp : com.android.systemui.R.string.kg_lockscreen_emergency_call_button_text);
            return;
        }
        emergencyButton.setVisibility(8);
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x0089, code lost:
    
        r6 = "";
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void $r8$lambda$e6ceecPxW3wEM4fHjkBzcKaihjE(EmergencyButtonController emergencyButtonController, boolean z) {
        String string;
        View view;
        SelectedUserInteractor selectedUserInteractor = emergencyButtonController.mSelectedUserInteractor;
        if (z) {
            int selectedUserId = selectedUserInteractor.getSelectedUserId();
            Log.d("EmergencyButton", "takeEmergencyCallAction - showInCallScreen(false, " + selectedUserId + ")");
            ((SemTelecomManager) emergencyButtonController.getContext().getSystemService(SemTelecomManager.class)).showInCallScreen(false, UserHandle.of(selectedUserId));
            EmergencyButtonCallback emergencyButtonCallback = emergencyButtonController.mEmergencyButtonCallback;
            if (emergencyButtonCallback != null) {
                emergencyButtonCallback.onEmergencyButtonClickedWhenInCall();
                return;
            }
            return;
        }
        emergencyButtonController.mKeyguardUpdateMonitor.reportEmergencyCallAction();
        if (CscRune.SECURITY_DIRECT_CALL_TO_ECC) {
            try {
                view = emergencyButtonController.mPasswordEntry;
            } catch (Exception unused) {
                string = "";
            }
            if (view instanceof SecPasswordTextView) {
                string = ((SecPasswordTextView) view).mText;
                ((PasswordTextView) view).reset(false, false);
            } else if (view instanceof TextView) {
                string = ((TextView) view).getText().toString();
                ((TextView) emergencyButtonController.mPasswordEntry).setText("");
            } else if (view instanceof EditText) {
                string = ((EditText) view).getText().toString();
                ((EditText) emergencyButtonController.mPasswordEntry).setText("");
            } else {
                string = "";
            }
            if (!string.equals("") && PhoneNumberUtils.isEmergencyNumber(string)) {
                Intent intent = new Intent("android.intent.action.CALL_EMERGENCY");
                intent.setData(Uri.fromParts("tel", string, null));
                intent.setFlags(343932928);
                try {
                    Log.w("EmergencyButton", "callToEmergencyLine");
                    emergencyButtonController.getContext().startActivityAsUser(intent, ActivityOptions.makeCustomAnimation(emergencyButtonController.getContext(), 0, 0).toBundle(), new UserHandle(selectedUserInteractor.getSelectedUserId()));
                    return;
                } catch (ActivityNotFoundException e) {
                    Log.e("EmergencyButton", "Can't find the component " + e);
                    return;
                }
            }
        }
        TelecomManager telecomManager = emergencyButtonController.mTelecomManager;
        if (telecomManager == null) {
            Log.wtf("EmergencyButton", "TelecomManager was null, cannot launch emergency dialer");
            return;
        }
        Intent intentPutExtra = telecomManager.createLaunchEmergencyDialerIntent(null).setFlags(343932928).putExtra(EmergencyDialerConstants.EXTRA_ENTRY_TYPE, 1);
        ActivityOptions activityOptionsMakeCustomAnimation = ActivityOptions.makeCustomAnimation(emergencyButtonController.getContext(), 0, 0);
        Log.d("EmergencyButton", "takeEmergencyCallAction");
        emergencyButtonController.mImm.hideSoftInputFromWindow(((EmergencyButton) emergencyButtonController.mView).getWindowToken(), 0);
        activityOptionsMakeCustomAnimation.setLaunchDisplayId(emergencyButtonController.getContext().getDisplay().getDisplayId());
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isAdminLockEnabled()) {
            intentPutExtra.putExtra("enable_ice_contact_list", false);
            intentPutExtra.putExtra("enable_emergency_medical_info", false);
        }
        emergencyButtonController.getContext().startActivityAsUser(intentPutExtra, activityOptionsMakeCustomAnimation.toBundle(), new UserHandle(selectedUserInteractor.getSelectedUserId()));
    }

    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.keyguard.EmergencyButtonController$2] */
    public EmergencyButtonController(EmergencyButton emergencyButton, ConfigurationController configurationController, InputMethodManager inputMethodManager, TelephonyManager telephonyManager, KeyguardUpdateMonitor keyguardUpdateMonitor, PowerManager powerManager, ActivityTaskManager activityTaskManager, ShadeController shadeController, TelecomManager telecomManager, MetricsLogger metricsLogger, LockPatternUtils lockPatternUtils, Executor executor, Executor executor2, SelectedUserInteractor selectedUserInteractor, MSDLPlayer mSDLPlayer) {
        super(emergencyButton);
        this.mKeyguardShowing = true;
        this.mBouncerShowing = false;
        this.mCurrentSimState = 1;
        this.mSettingsListener = new EmergencyButtonController$$ExternalSyntheticLambda3(this, 1);
        this.mSettingsValueList = new Uri[]{Settings.System.getUriFor(SettingsHelper.INDEX_AIRPLANE_MODE_ON)};
        this.mCurrentSatellitePhoneId = -1;
        this.mSatelliteSubId = -1;
        this.mSatelliteRegState = -1;
        this.mSatelliteSettingsListener = null;
        this.mSatelliteSettingsValueList = new Uri[]{Settings.Global.getUriFor("satellite_mode_enabled")};
        this.mPasswordEntry = null;
        this.mInfoCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.EmergencyButtonController.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
                EmergencyButtonController emergencyButtonController = EmergencyButtonController.this;
                emergencyButtonController.mBouncerShowing = z;
                emergencyButtonController.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardVisibilityChanged(boolean z) {
                EmergencyButtonController emergencyButtonController = EmergencyButtonController.this;
                emergencyButtonController.mKeyguardShowing = z;
                emergencyButtonController.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onOfflineStateChanged() {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onPhoneStateChanged(int i) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onRefreshCarrierInfo(Intent intent) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimStateChanged(int i, int i2, int i3) {
                EmergencyButtonController emergencyButtonController = EmergencyButtonController.this;
                emergencyButtonController.getClass();
                emergencyButtonController.mCurrentSimState = i3;
                emergencyButtonController.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitchComplete(int i) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.EmergencyButtonController.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }
        };
        this.mConfigurationController = configurationController;
        this.mImm = inputMethodManager;
        this.mTelephonyManager = telephonyManager;
        this.mTelephonyManagerForSatellite = telephonyManager.createForSubscriptionId(this.mSatelliteSubId);
        this.mSatelliteTelephonyCallback = new SatelliteTelephonyCallback(this, 0);
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mPowerManager = powerManager;
        this.mActivityTaskManager = activityTaskManager;
        this.mShadeController = shadeController;
        this.mTelecomManager = telecomManager;
        this.mMetricsLogger = metricsLogger;
        this.mMainExecutor = executor;
        this.mBackgroundExecutor = executor2;
        this.mSelectedUserInteractor = selectedUserInteractor;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
        DejankUtils.whitelistIpcs(new EmergencyButtonController$$ExternalSyntheticLambda2(this, 0));
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mKeyguardUpdateMonitor.registerCallback(this.mInfoCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        setEmergencyView(this.mView);
        if (CscRune.SECURITY_EMERGENCY_BUTTON_KOR) {
            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this.mSettingsListener, this.mSettingsValueList);
        }
        if (CscRune.SECURITY_DISABLE_EMERGENCY_CALL_WHEN_OFFLINE) {
            if (this.mSatelliteSettingsListener == null) {
                this.mSatelliteSettingsListener = new EmergencyButtonController$$ExternalSyntheticLambda3(this, 0);
            }
            if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isSatelliteModeEnabled()) {
                registerSatelliteTelephonyCallback();
            }
            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this.mSatelliteSettingsListener, this.mSatelliteSettingsValueList);
        }
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mInfoCallback);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        if (CscRune.SECURITY_EMERGENCY_BUTTON_KOR) {
            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).unregisterCallback(this.mSettingsListener);
        }
        if (CscRune.SECURITY_DISABLE_EMERGENCY_CALL_WHEN_OFFLINE) {
            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).unregisterCallback(this.mSatelliteSettingsListener);
            if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isSatelliteModeEnabled()) {
                unregisterSatelliteTelephonyCallback();
            }
        }
    }

    public final void registerSatelliteTelephonyCallback() {
        int i = -1;
        int i2 = SystemProperties.getInt("ril.tiantong.phone.id", -1);
        this.mCurrentSatellitePhoneId = i2;
        int[] subId = SubscriptionManager.getSubId(i2);
        if (subId == null || subId.length <= 0) {
            Log.e("EmergencyButton", "getSubId: no valid subs");
        } else {
            i = subId[0];
        }
        this.mSatelliteSubId = i;
        StringBuilder sb = new StringBuilder("*** registerTelephonyCallback SatelliteSlotId=");
        sb.append(this.mCurrentSatellitePhoneId);
        sb.append(", SatelliteSubId=");
        RecyclerView$$ExternalSyntheticOutline0.m(this.mSatelliteSubId, "EmergencyButton", sb);
        TelephonyManager telephonyManagerCreateForSubscriptionId = this.mTelephonyManager.createForSubscriptionId(this.mSatelliteSubId);
        this.mTelephonyManagerForSatellite = telephonyManagerCreateForSubscriptionId;
        telephonyManagerCreateForSubscriptionId.registerTelephonyCallback(this.mBackgroundExecutor, this.mSatelliteTelephonyCallback);
    }

    public final void setEmergencyView(View view) {
        EmergencyButton emergencyButton = (EmergencyButton) view;
        this.mView = emergencyButton;
        emergencyButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.EmergencyButtonController$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                EmergencyButtonController emergencyButtonController = this.f$0;
                emergencyButtonController.mMetricsLogger.action(200);
                PowerManager powerManager = emergencyButtonController.mPowerManager;
                if (powerManager != null) {
                    powerManager.userActivity(SystemClock.uptimeMillis(), true);
                }
                emergencyButtonController.mActivityTaskManager.stopSystemLockTaskMode();
                if (!SafeUIState.isSysUiSafeModeEnabled()) {
                    emergencyButtonController.mShadeController.collapseShade(false);
                }
                emergencyButtonController.mBackgroundExecutor.execute(new EmergencyButtonController$$ExternalSyntheticLambda2(emergencyButtonController, 1));
                SystemUIAnalytics.sendEventLog("102", SystemUIAnalytics.EID_EMERGENCY_CALL);
            }
        });
    }

    public final void unregisterSatelliteTelephonyCallback() {
        StringBuilder sb = new StringBuilder("*** unregisterTelephonyCallback SatelliteSlotId=");
        sb.append(this.mCurrentSatellitePhoneId);
        sb.append(", SatelliteSubId=");
        RecyclerView$$ExternalSyntheticOutline0.m(this.mSatelliteSubId, "EmergencyButton", sb);
        this.mTelephonyManagerForSatellite.unregisterTelephonyCallback(this.mSatelliteTelephonyCallback);
        this.mSatelliteRegState = -1;
    }

    public void updateEmergencyCallButton() {
        if (this.mView != 0) {
            TelecomManager telecomManager = this.mTelecomManager;
            boolean z = telecomManager != null && telecomManager.isInCall();
            boolean zIsSecure = this.mKeyguardUpdateMonitor.isSecure();
            if (this.mKeyguardShowing || this.mBouncerShowing) {
                this.mBackgroundExecutor.execute(new EmergencyButtonController$$ExternalSyntheticLambda5(this, z, zIsSecure, 0));
            }
        }
    }
}
