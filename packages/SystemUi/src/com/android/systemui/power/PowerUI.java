package com.android.systemui.power;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.ForegroundServiceStartNotAllowedException;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.hardware.display.IDisplayManager;
import android.hardware.scontext.SContextEvent;
import android.hardware.scontext.SContextListener;
import android.hardware.scontext.SContextManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.IThermalEventListener;
import android.os.IThermalService;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Temperature;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Slog;
import android.view.IWindowManager;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0790xf8f53ce8;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.Utils;
import com.android.settingslib.fuelgauge.Estimate;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.BasicRune;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.PowerUiRune;
import com.android.systemui.R;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.ChargerAnimationView;
import com.android.systemui.power.PowerUI;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.volume.Events;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.hardware.SemBatteryUtils;
import com.samsung.android.knox.custom.SettingsManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;
import com.samsung.android.view.SemWindowManager;
import com.sec.ims.IMSParameter;
import com.sec.ims.settings.ImsProfile;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Future;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PowerUI implements CoreStartable, CommandQueue.Callbacks, ChargerAnimationView.ChargerAnimationListener, WirelessMisalignListener {
    public static final boolean DEBUG = Log.isLoggable("PowerUI", 3);
    public PowerManager.WakeLock mBatteryHealthInterruptionPartialWakeLock;
    public PowerManager.WakeLock mBatteryHealthInterruptionScreenDimWakeLock;
    public int mBatteryProtectionThreshold;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Lazy mCentralSurfacesOptionalLazy;
    public ChargerAnimationView mChargerAnimationView;
    public WindowManager.LayoutParams mChargerAnimationWindowLp;
    public WindowManager mChargerAnimationWindowManager;
    public String mChargingStartTime;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    BatteryStateSnapshot mCurrentBatteryStateSnapshot;
    public boolean mEnableSkinTemperatureWarning;
    public boolean mEnableUsbTemperatureAlarm;
    public final EnhancedEstimates mEnhancedEstimates;
    BatteryStateSnapshot mLastBatteryStateSnapshot;
    public Future mLastShowWarningTask;
    public int mLowBatteryAlertCloseLevel;
    boolean mLowWarningShownThisChargeCycle;
    public int mLtcHighSocThreshold;
    public int mLtcReleaseThreshold;
    public InattentiveSleepWarningView mOverlayView;
    public final Lazy mPluginAODManagerLazy;
    public final PowerManager mPowerManager;
    public final SecPowerNotificationWarnings mSecPowerNotificationWarnings;
    boolean mSevereWarningShownThisChargeCycle;
    public IThermalEventListener mSkinThermalEventListener;
    public String mSleepChargingEvent;
    public int mSuperFastCharger;
    IThermalService mThermalService;
    public IThermalEventListener mUsbThermalEventListener;
    public final UserTracker mUserTracker;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final WarningsUI mWarnings;
    public WirelessMisalignView mWirelessMisalignView;
    public PowerManager.WakeLock mWirelessMisalignWakeLock;
    public WindowManager.LayoutParams mWirelessMisalignWindowLp;
    public WindowManager mWirelessMisalignWindowManager;
    public final Handler mHandler = new Handler();
    final Receiver mReceiver = new Receiver();
    public final Configuration mLastConfiguration = new Configuration();
    public int mPlugType = -1;
    public int mInvalidCharger = 0;
    public final int[] mLowBatteryReminderLevels = new int[2];
    public long mScreenOffTime = -1;
    int mBatteryLevel = 100;
    int mBatteryStatus = 1;
    public final C19341 mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.power.PowerUI.1
        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedGoingToSleep() {
            PowerUI.this.mScreenOffTime = SystemClock.elapsedRealtime();
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedWakingUp() {
            PowerUI.this.mScreenOffTime = -1L;
        }
    };
    public final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.power.PowerUI.2
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            Slog.i("PowerUI", "onUserChanged : " + i);
            PowerUI powerUI = PowerUI.this;
            ((PowerNotificationWarnings) powerUI.mWarnings).updateNotification();
            powerUI.mSecPowerNotificationWarnings.updateNotification();
            if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION && powerUI.mProtectBatteryValue == 4 && i != 0) {
                Slog.i("PowerUI", "start battery protection InitService at user " + i);
                Intent intent = new Intent();
                intent.setPackage(PowerUiConstants.DC_PACKAGE_NAME);
                intent.setAction("com.samsung.android.sm.service.action.ACTION_BATTERY_PROTECTION_INIT_SERVICE");
                context.startService(intent);
            }
        }
    };
    public boolean mIsRunningLowBatteryTask = false;
    public boolean mIsRunningStopPowerSoundTask = false;
    public int mBatterySwellingMode = 0;
    public boolean mBatteryHighVoltageCharger = false;
    public boolean mFullyConnected = true;
    public boolean mBatterySlowCharger = false;
    public boolean mIsChangedStringAfterCharging = false;
    public int mBatteryChargingType = 0;
    public int mBatteryOnline = -1;
    public boolean mIsChargerAnimationPlaying = false;
    public boolean mIsWirelessMisalignTask = false;
    public SContextManager mSContextManager = null;
    public boolean mIsMotionDetectionSupported = false;
    public boolean mIsSContextEnabled = false;
    public boolean mIsSContextListenerRegistered = false;
    public boolean mIsDeviceMoving = true;
    public boolean mWirelessFodState = false;
    public boolean mBatteryWaterConnector = false;
    public boolean mIsHiccupState = false;
    public boolean mTemperatureHiccupState = false;
    public boolean mDismissBatteryHealthInterruptionWarning = false;
    public int mBatteryHealth = 1;
    public boolean mIsShutdownTaskDelayed = false;
    public int mBatteryOverheatLevel = 0;
    public int mCallState = 0;
    public boolean mBootCompleted = false;
    public int mBatteryCurrentEvent = 0;
    public boolean mIsProtectingBatteryCutOffSettingEnabled = false;
    public int mProtectBatteryValue = -1;
    public boolean mIsAfterAdaptiveProtection = false;
    public int mBatteryMiscEvent = 0;
    public int mTurnOffPsmLevel = -1;
    public final RunnableC19467 mLowBatteryWarningTask = new Runnable() { // from class: com.android.systemui.power.PowerUI.7
        @Override // java.lang.Runnable
        public final void run() {
            Log.d("PowerUI", "mLowBatteryWarningTask");
            PowerUI powerUI = PowerUI.this;
            powerUI.mIsRunningLowBatteryTask = false;
            powerUI.mSecPowerNotificationWarnings.showLowBatteryWarning(true);
        }
    };
    public final C19478 mScreenOnOffCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.power.PowerUI.8
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onFinishedGoingToSleep(int i) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            PowerUI powerUI = PowerUI.this;
            powerUI.mScreenOffTime = elapsedRealtime;
            SecPowerNotificationWarnings secPowerNotificationWarnings = powerUI.mSecPowerNotificationWarnings;
            secPowerNotificationWarnings.getClass();
            Log.d("SecPowerUI.Notification", "dismissUnintentionallyLcdOnNotice");
            secPowerNotificationWarnings.dismissUnintentionalLcdOnWindow();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onStartedWakingUp() {
            PowerUI.this.mScreenOffTime = -1L;
        }
    };
    public final RunnableC19489 mOverheatShutdownWarningTask = new Runnable() { // from class: com.android.systemui.power.PowerUI.9
        @Override // java.lang.Runnable
        public final void run() {
            PowerUI powerUI = PowerUI.this;
            if (powerUI.mCallState != 0) {
                Log.d("PowerUI", "Battery overheat but on call, so delayed power off");
                PowerUI.this.mIsShutdownTaskDelayed = true;
                return;
            }
            SecPowerNotificationWarnings secPowerNotificationWarnings = powerUI.mSecPowerNotificationWarnings;
            secPowerNotificationWarnings.getClass();
            Log.d("SecPowerUI.Notification", "runOverheatShutdownTask - Delay time = 10000");
            secPowerNotificationWarnings.mHandler.postDelayed(secPowerNotificationWarnings.mOverheatShutdownTask, 10000);
            PowerUI.this.mSecPowerNotificationWarnings.showWillOverheatShutdownWarning();
        }
    };
    public final C193510 mPhoneStateListener = new PhoneStateListener() { // from class: com.android.systemui.power.PowerUI.10
        @Override // android.telephony.PhoneStateListener
        public final void onCallStateChanged(int i, String str) {
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(AbstractC0000x2c234b15.m1m("mPhoneStateListener onCallStateChanged(): state= ", i, " mIsShutdownTaskDelayed = "), PowerUI.this.mIsShutdownTaskDelayed, "PowerUI");
            PowerUI powerUI = PowerUI.this;
            powerUI.mCallState = i;
            if (i == 0 && powerUI.mIsShutdownTaskDelayed) {
                powerUI.mIsShutdownTaskDelayed = false;
                if (2 == powerUI.mBatteryOverheatLevel) {
                    SecPowerNotificationWarnings secPowerNotificationWarnings = powerUI.mSecPowerNotificationWarnings;
                    secPowerNotificationWarnings.getClass();
                    Log.d("SecPowerUI.Notification", "runOverheatShutdownTask - Delay time = 10000");
                    secPowerNotificationWarnings.mHandler.postDelayed(secPowerNotificationWarnings.mOverheatShutdownTask, 10000);
                    PowerUI.this.mSecPowerNotificationWarnings.showWillOverheatShutdownWarning();
                }
            }
            if (i == 0) {
                PowerUI.this.mSecPowerNotificationWarnings.mIsInCall = false;
            } else {
                PowerUI.this.mSecPowerNotificationWarnings.mIsInCall = true;
            }
        }
    };
    public final RunnableC193611 mAfterChargingNoticeTask = new Runnable() { // from class: com.android.systemui.power.PowerUI.11
        @Override // java.lang.Runnable
        public final void run() {
            PowerUI powerUI = PowerUI.this;
            powerUI.mIsChangedStringAfterCharging = true;
            int i = powerUI.mBatteryChargingType;
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    i = 11;
                    break;
                case 6:
                case 7:
                    i = 10;
                    break;
            }
            powerUI.mBatteryChargingType = i;
            SecPowerNotificationWarnings secPowerNotificationWarnings = powerUI.mSecPowerNotificationWarnings;
            int i2 = powerUI.mSuperFastCharger;
            secPowerNotificationWarnings.mOldChargingType = secPowerNotificationWarnings.mChargingType;
            secPowerNotificationWarnings.mChargingType = i;
            secPowerNotificationWarnings.mSuperFastCharger = i2;
            secPowerNotificationWarnings.showChargingNotice();
        }
    };
    public final RunnableC193712 mStopPowerSoundTask = new Runnable() { // from class: com.android.systemui.power.PowerUI.12
        @Override // java.lang.Runnable
        public final void run() {
            Log.d("PowerUI", "mStopPowerSoundTask");
            PowerUI powerUI = PowerUI.this;
            powerUI.mIsRunningStopPowerSoundTask = false;
            powerUI.mSecPowerNotificationWarnings.stopPowerSound(1);
        }
    };
    public final C193813 mSContextListener = new SContextListener() { // from class: com.android.systemui.power.PowerUI.13
        public final void onSContextChanged(SContextEvent sContextEvent) {
            if (sContextEvent.scontext.getType() == 46) {
                int action = sContextEvent.getWirelessChargingDetectionContext().getAction();
                if (action == 0) {
                    Log.d("PowerUI", "SContextListener - No Move");
                    PowerUI.this.mIsDeviceMoving = false;
                    return;
                }
                if (action != 1) {
                    return;
                }
                Log.d("PowerUI", "SContextListener - Move");
                PowerUI powerUI = PowerUI.this;
                powerUI.mIsDeviceMoving = true;
                if (powerUI.mIsSContextListenerRegistered) {
                    if (powerUI.mPlugType == 4 && powerUI.mBatteryStatus == 2) {
                        return;
                    }
                    Log.d("PowerUI", "Unregister SContextListener - From Listener");
                    PowerUI powerUI2 = PowerUI.this;
                    powerUI2.mSContextManager.unregisterListener(powerUI2.mSContextListener, 46);
                    PowerUI.this.mIsSContextListenerRegistered = false;
                }
            }
        }
    };
    public final RunnableC193914 mWirelessMisalignTimeoutTask = new Runnable() { // from class: com.android.systemui.power.PowerUI.14
        @Override // java.lang.Runnable
        public final void run() {
            if (PowerUI.this.mIsWirelessMisalignTask) {
                Log.d("PowerUI", "mWirelessMisalignTask");
                PowerUI.this.removeChargerView();
                PowerUI.this.removeMisalignView();
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    final class Receiver extends BroadcastReceiver {
        public boolean mHasReceivedBattery = false;

        public Receiver() {
        }

        /* JADX WARN: Removed duplicated region for block: B:101:0x03ed  */
        /* JADX WARN: Removed duplicated region for block: B:104:0x03f6  */
        /* JADX WARN: Removed duplicated region for block: B:107:0x0409  */
        /* JADX WARN: Removed duplicated region for block: B:110:0x0412  */
        /* JADX WARN: Removed duplicated region for block: B:112:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:113:0x03fc  */
        /* JADX WARN: Removed duplicated region for block: B:116:0x03b7  */
        /* JADX WARN: Removed duplicated region for block: B:119:0x034f  */
        /* JADX WARN: Removed duplicated region for block: B:123:0x02fd  */
        /* JADX WARN: Removed duplicated region for block: B:126:0x02dd  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x0154  */
        /* JADX WARN: Removed duplicated region for block: B:40:0x016c  */
        /* JADX WARN: Removed duplicated region for block: B:55:0x01a2  */
        /* JADX WARN: Removed duplicated region for block: B:58:0x02d6  */
        /* JADX WARN: Removed duplicated region for block: B:61:0x02f7  */
        /* JADX WARN: Removed duplicated region for block: B:70:0x0329  */
        /* JADX WARN: Removed duplicated region for block: B:78:0x035f  */
        /* JADX WARN: Removed duplicated region for block: B:83:0x0396  */
        /* JADX WARN: Removed duplicated region for block: B:86:0x03b5  */
        /* JADX WARN: Removed duplicated region for block: B:89:0x03bf  */
        /* JADX WARN: Removed duplicated region for block: B:92:0x03ca  */
        /* JADX WARN: Removed duplicated region for block: B:95:0x03d6  */
        /* JADX WARN: Removed duplicated region for block: B:98:0x03e4  */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onReceive(Context context, Intent intent) {
            int i;
            boolean z;
            boolean z2;
            int i2;
            int i3;
            int i4;
            Future future;
            String action = intent.getAction();
            if (!"android.intent.action.BATTERY_CHANGED".equals(action)) {
                if ("android.intent.action.LOCALE_CHANGED".equals(action)) {
                    PowerUI powerUI = PowerUI.this;
                    SecPowerNotificationWarnings secPowerNotificationWarnings = powerUI.mSecPowerNotificationWarnings;
                    if (secPowerNotificationWarnings.mWarning) {
                        secPowerNotificationWarnings.updateNotification();
                        Log.d("PowerUI", "Language is changed so notify LowBatteryNotification again");
                    }
                    if (powerUI.mBatteryChargingType > 0 && powerUI.mBatterySwellingMode == 0) {
                        secPowerNotificationWarnings.mDoNotShowChargingNotice = false;
                        secPowerNotificationWarnings.mChargingType = 0;
                        secPowerNotificationWarnings.mOldChargingType = 0;
                        secPowerNotificationWarnings.mChargingTime = 0L;
                        secPowerNotificationWarnings.mShowChargingNotice = false;
                        secPowerNotificationWarnings.dismissSlowByChargerConnectionInfoPopUp();
                        Log.d("SecPowerUI.Notification", "dismissChargingNotification()");
                        secPowerNotificationWarnings.cancelNotification(2);
                        Log.d("PowerUI", "Language is changed so notify ChargingNotification again");
                        secPowerNotificationWarnings.showChargingNotice();
                    }
                    if (PowerUiRune.INCOMPATIBLE_CHARGER_CHECK && powerUI.mBatteryOnline == 0) {
                        AlertDialog alertDialog = secPowerNotificationWarnings.mIncompatibleChargerDialog;
                        if (alertDialog != null) {
                            alertDialog.dismiss();
                        }
                        Log.d("SecPowerUI.Notification", "dismissing incompatible charger notification");
                        secPowerNotificationWarnings.cancelNotification(3);
                        Log.d("PowerUI", "Language is changed so notify incompatible charger again");
                        secPowerNotificationWarnings.showIncompatibleChargerNotice();
                    }
                    if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION && PowerUtils.isSleepChargingOn(powerUI.mContext)) {
                        powerUI.checkAdaptiveProtectionNotification(powerUI.mSleepChargingEvent, powerUI.mChargingStartTime);
                    }
                    if (PowerUiRune.BATTERY_PROTECTION_NOTIFICATION) {
                        powerUI.checkBatteryProtectionNotification();
                        return;
                    }
                    return;
                }
                if (action.equals("com.samsung.server.BatteryService.action.SEC_BATTERY_EVENT")) {
                    PowerUI powerUI2 = PowerUI.this;
                    boolean z3 = powerUI2.mWirelessFodState;
                    powerUI2.mWirelessFodState = (intent.getIntExtra("misc_event", 0) & 256) == 256;
                    ActionBarContextView$$ExternalSyntheticOutline0.m9m(RowView$$ExternalSyntheticOutline0.m49m("SUPPORT_WIRELESS_CHARGER_FOD_POPUP - oldWirelessFodState : ", z3, ", mWirelessFodState : "), powerUI2.mWirelessFodState, "PowerUI");
                    boolean z4 = powerUI2.mWirelessFodState;
                    if (z3 != z4 && z4) {
                        SecPowerNotificationWarnings secPowerNotificationWarnings2 = powerUI2.mSecPowerNotificationWarnings;
                        secPowerNotificationWarnings2.getClass();
                        Log.d("SecPowerUI.Notification", "showWirelessFodAlertDialog");
                        if (secPowerNotificationWarnings2.mWirelessFodAlertDialog == null) {
                            AlertDialog popupDialog = secPowerNotificationWarnings2.getPopupDialog(11);
                            secPowerNotificationWarnings2.mWirelessFodAlertDialog = popupDialog;
                            if (popupDialog != null) {
                                popupDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.14
                                    public DialogInterfaceOnDismissListenerC195414() {
                                    }

                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        SecPowerNotificationWarnings.this.mWirelessFodAlertDialog = null;
                                    }
                                });
                                secPowerNotificationWarnings2.mWirelessFodAlertDialog.show();
                            }
                        }
                    }
                    PowerUI powerUI3 = PowerUI.this;
                    boolean z5 = powerUI3.mBatteryWaterConnector;
                    boolean z6 = powerUI3.mIsHiccupState;
                    powerUI3.mBatteryWaterConnector = (intent.getIntExtra("misc_event", 0) & 1) == 1;
                    boolean z7 = (intent.getIntExtra("misc_event", 0) & 32) == 32;
                    powerUI3.mIsHiccupState = z7;
                    SecPowerNotificationWarnings secPowerNotificationWarnings3 = powerUI3.mSecPowerNotificationWarnings;
                    secPowerNotificationWarnings3.mIsHiccupState = z7;
                    StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("SUPPORT_WATER_PROTECTION_POPUP - oldBatteryWaterConnector : ", z5, ", mBatteryWaterConnector : ");
                    KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m49m, powerUI3.mBatteryWaterConnector, ", oldHiccupState : ", z6, ", mIsHiccupState : ");
                    ActionBarContextView$$ExternalSyntheticOutline0.m9m(m49m, powerUI3.mIsHiccupState, "PowerUI");
                    boolean z8 = powerUI3.mIsHiccupState;
                    Context context2 = powerUI3.mContext;
                    if (z6 == z8) {
                        boolean z9 = powerUI3.mBatteryWaterConnector;
                        if (z5 != z9) {
                            if (z9) {
                                secPowerNotificationWarnings3.showWaterProtectionAlertDialog(z9);
                                Log.i("PowerUI", "showWaterProtectionAlertDialog by mBatteryWaterConnector : show and sending intent ACTION_USB_DAMAGE_PROTECTION_POPUP_SHOW");
                                context2.sendBroadcast(new Intent("com.samsung.systemui.power.action.USB_DAMAGE_POPUP_SHOW"));
                            } else {
                                ActionBarContextView$$ExternalSyntheticOutline0.m9m(RowView$$ExternalSyntheticOutline0.m49m("dismiss WaterProtectionAlertDialog - isWaterDetected = ", z9, " mIsWaterDetected = "), secPowerNotificationWarnings3.mIsWaterDetected, "SecPowerUI.Notification");
                                secPowerNotificationWarnings3.mIsWaterDetected = z9;
                                secPowerNotificationWarnings3.mHandler.removeCallbacks(secPowerNotificationWarnings3.mWaterProtectionAlertTask);
                                PowerManager.WakeLock wakeLock = secPowerNotificationWarnings3.mWaterProtectionPartialWakeLock;
                                if (wakeLock != null) {
                                    wakeLock.release();
                                    secPowerNotificationWarnings3.mWaterProtectionPartialWakeLock = null;
                                }
                                AlertDialog alertDialog2 = secPowerNotificationWarnings3.mWaterProtectionAlertDialog;
                                if (alertDialog2 != null) {
                                    alertDialog2.dismiss();
                                }
                            }
                        }
                    } else if (z8) {
                        secPowerNotificationWarnings3.showWaterProtectionAlertDialog(powerUI3.mBatteryWaterConnector);
                        Log.i("PowerUI", "showWaterProtectionAlertDialog by hiccup state : show and sending intent ACTION_USB_DAMAGE_PROTECTION_POPUP_SHOW");
                        context2.sendBroadcast(new Intent("com.samsung.systemui.power.action.USB_DAMAGE_POPUP_SHOW"));
                    }
                    PowerUI powerUI4 = PowerUI.this;
                    boolean z10 = powerUI4.mTemperatureHiccupState;
                    powerUI4.mTemperatureHiccupState = (intent.getIntExtra("misc_event", 0) & 8192) == 8192;
                    ActionBarContextView$$ExternalSyntheticOutline0.m9m(RowView$$ExternalSyntheticOutline0.m49m("USB damage detection - oldTemperatureHiccupState : ", z10, ", mTemperatureHiccupState : "), powerUI4.mTemperatureHiccupState, "PowerUI");
                    boolean z11 = powerUI4.mTemperatureHiccupState;
                    SecPowerNotificationWarnings secPowerNotificationWarnings4 = powerUI4.mSecPowerNotificationWarnings;
                    secPowerNotificationWarnings4.mIsTemperatureHiccupState = z11;
                    if (z10 == z11 || !z11) {
                        return;
                    }
                    secPowerNotificationWarnings4.showUsbDamageProtectionAlertDialog();
                    powerUI4.mContext.sendBroadcast(new Intent("com.samsung.systemui.power.action.USB_DAMAGE_POPUP_SHOW"));
                    return;
                }
                if (action.equals("com.samsung.intent.action.KSO_SHOW_POPUP")) {
                    SecPowerNotificationWarnings secPowerNotificationWarnings5 = PowerUI.this.mSecPowerNotificationWarnings;
                    secPowerNotificationWarnings5.getClass();
                    boolean z12 = ((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.get(KeyguardStateController.class))).mShowing;
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("showUnintentionallyLcdOnNotice() - isLock ? ", z12, "SecPowerUI.Notification");
                    synchronized (secPowerNotificationWarnings5) {
                        if (secPowerNotificationWarnings5.mIsUnintentionalPopupShowing) {
                            return;
                        }
                        Log.d("SecPowerUI.Notification", "showUnintentionalLcdOnWindow");
                        if (z12) {
                            secPowerNotificationWarnings5.mPowerManager.userActivity(SystemClock.uptimeMillis(), 2, 0);
                        }
                        if (secPowerNotificationWarnings5.mUnintentionalLCDOnWindowLp == null) {
                            secPowerNotificationWarnings5.mUnintentionalLCDOnWindowLp = new WindowManager.LayoutParams(-1, -1, 2021, 256, -3);
                        }
                        boolean z13 = LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY && SemWindowManager.getInstance().isFolded();
                        if (z13) {
                            secPowerNotificationWarnings5.mUnintentionalLCDOnWindowLp.layoutInDisplayCutoutMode = 3;
                        } else {
                            WindowManager.LayoutParams layoutParams = secPowerNotificationWarnings5.mUnintentionalLCDOnWindowLp;
                            layoutParams.screenOrientation = 1;
                            layoutParams.layoutInDisplayCutoutMode = 1;
                        }
                        secPowerNotificationWarnings5.mUnintentionalLCDOnWindowLp.setFitInsetsSides(0);
                        WindowManager.LayoutParams layoutParams2 = secPowerNotificationWarnings5.mUnintentionalLCDOnWindowLp;
                        layoutParams2.inputFeatures |= 2;
                        if (!z12) {
                            layoutParams2.semSetScreenTimeout(10000L);
                            secPowerNotificationWarnings5.mUnintentionalLCDOnWindowLp.semSetScreenDimDuration(0L);
                        }
                        secPowerNotificationWarnings5.mUnintentionalLCDOnWindowLp.setTitle("UnintentionalLcdOn");
                        if (secPowerNotificationWarnings5.mWindowManager == null || secPowerNotificationWarnings5.mUnintentionalLcdOnWindow == null) {
                            Log.d("SecPowerUI.Notification", "showUnintentionalLcdOnWindow addview - isCover = " + z13);
                            if (z13) {
                                Context subDisplayContext = PowerUtils.getSubDisplayContext(secPowerNotificationWarnings5.mContext);
                                secPowerNotificationWarnings5.mWindowManager = (WindowManager) subDisplayContext.getSystemService("window");
                                secPowerNotificationWarnings5.mUnintentionalLcdOnWindow = (UnintentionalLcdOnView) View.inflate(subDisplayContext, R.layout.sc_unintentional_lcd_on_window, null);
                            } else {
                                secPowerNotificationWarnings5.mWindowManager = (WindowManager) secPowerNotificationWarnings5.mContext.getSystemService("window");
                                secPowerNotificationWarnings5.mUnintentionalLcdOnWindow = (UnintentionalLcdOnView) View.inflate(secPowerNotificationWarnings5.mContext, R.layout.unintentional_lcd_on_window, null);
                            }
                            secPowerNotificationWarnings5.mUnintentionalLcdOnWindow.setCoverState(z13);
                            secPowerNotificationWarnings5.mWindowManager.addView(secPowerNotificationWarnings5.mUnintentionalLcdOnWindow, secPowerNotificationWarnings5.mUnintentionalLCDOnWindowLp);
                            secPowerNotificationWarnings5.mUnintentionalLcdOnWindow.mTouchListener = secPowerNotificationWarnings5;
                        }
                        secPowerNotificationWarnings5.mIsUnintentionalPopupShowing = true;
                        return;
                    }
                }
                if (action.equals("com.samsung.intent.action.KSO_CLOSE_POPUP")) {
                    SecPowerNotificationWarnings secPowerNotificationWarnings6 = PowerUI.this.mSecPowerNotificationWarnings;
                    secPowerNotificationWarnings6.getClass();
                    Log.d("SecPowerUI.Notification", "dismissUnintentionallyLcdOnNotice");
                    secPowerNotificationWarnings6.dismissUnintentionalLcdOnWindow();
                    return;
                }
                if (action.equals("com.samsung.systemui.power.action.WATER_ALERT_SOUND_TEST")) {
                    SecPowerNotificationWarnings secPowerNotificationWarnings7 = PowerUI.this.mSecPowerNotificationWarnings;
                    if (secPowerNotificationWarnings7.mIsInCall) {
                        return;
                    }
                    secPowerNotificationWarnings7.playPowerSound(1600);
                    return;
                }
                if (action.equals("com.samsung.CHECK_COOLDOWN_LEVEL")) {
                    Log.d("PowerUI", "PowerUI check cool down level");
                    PowerUI powerUI5 = PowerUI.this;
                    int i5 = powerUI5.mBatteryOverheatLevel;
                    powerUI5.mBatteryOverheatLevel = intent.getIntExtra("battery_overheat_level", 0);
                    RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("Battery overheat Level = "), powerUI5.mBatteryOverheatLevel, "PowerUI");
                    int i6 = powerUI5.mBatteryOverheatLevel;
                    if (i5 != i6) {
                        RunnableC19489 runnableC19489 = powerUI5.mOverheatShutdownWarningTask;
                        Handler handler = powerUI5.mHandler;
                        SecPowerNotificationWarnings secPowerNotificationWarnings8 = powerUI5.mSecPowerNotificationWarnings;
                        if (2 == i6) {
                            secPowerNotificationWarnings8.getClass();
                            Log.d("SecPowerUI.Notification", "showOverheatWarning()");
                            secPowerNotificationWarnings8.showNotification(6);
                            handler.postDelayed(runnableC19489, 30000L);
                        } else if (2 > i6 && 2 <= i5) {
                            secPowerNotificationWarnings8.getClass();
                            Log.d("SecPowerUI.Notification", "dismissWillOverheatShutdownWarning");
                            secPowerNotificationWarnings8.mHandler.removeCallbacks(secPowerNotificationWarnings8.mOverheatShutdownTask);
                            AlertDialog alertDialog3 = secPowerNotificationWarnings8.mWillOverheatShutdownWarningDialog;
                            if (alertDialog3 != null) {
                                alertDialog3.dismiss();
                            }
                            handler.removeCallbacks(runnableC19489);
                            Log.d("PowerUI", "Battery overheat level recovered from shutdown");
                        }
                        int i7 = powerUI5.mBatteryOverheatLevel;
                        if (i7 != 0) {
                            if (1 == i7) {
                                secPowerNotificationWarnings8.getClass();
                                Log.d("SecPowerUI.Notification", "showOverheatWarning()");
                                secPowerNotificationWarnings8.showNotification(6);
                                return;
                            }
                            return;
                        }
                        secPowerNotificationWarnings8.getClass();
                        Log.d("SecPowerUI.Notification", "dismissOverheatWarning()");
                        AlertDialog alertDialog4 = secPowerNotificationWarnings8.mOverheatNoticeDialog;
                        if (alertDialog4 != null) {
                            alertDialog4.dismiss();
                        }
                        secPowerNotificationWarnings8.cancelNotification(6);
                        return;
                    }
                    return;
                }
                if (action.equals("android.intent.action.BOOT_COMPLETED") || action.equals("com.sec.android.intent.action.SAFEMODE_ENABLE")) {
                    Log.d("PowerUI", "PowerUI received : ".concat(action));
                    PowerUI powerUI6 = PowerUI.this;
                    if (!powerUI6.mBootCompleted) {
                        powerUI6.checkOverheatShutdownHappened();
                    }
                    try {
                        boolean isSafeModeEnabled = IWindowManager.Stub.asInterface(ServiceManager.getService("window")).isSafeModeEnabled();
                        Log.d("PowerUI", "safe mode case : " + isSafeModeEnabled);
                        if (isSafeModeEnabled && !ImsProfile.PDN_EMERGENCY.equals(SystemProperties.get("persist.sys.emergency_reset"))) {
                            PowerUI.this.mSecPowerNotificationWarnings.showSafeModeNotice();
                        }
                    } catch (Exception e) {
                        AbstractC0790xf8f53ce8.m80m(e, new StringBuilder("SAFEMODE Exception occurs! "), "PowerUI");
                    }
                    if (action.equals("android.intent.action.BOOT_COMPLETED")) {
                        PowerUI.this.mBootCompleted = true;
                        return;
                    }
                    return;
                }
                if (!action.equals("com.samsung.android.sm.IGNORE_RUT_TIPS_NOTI") && !action.equals("com.samsung.android.sm.CLEAR_TIPS_NOTI") && !action.equals("android.intent.action.tips.noti.confirmed")) {
                    if (action.equals("android.intent.action.TIMEZONE_CHANGED") || action.equals("android.intent.action.TIME_SET")) {
                        Slog.w("PowerUI", "Time is changed, so we need to init LTC time");
                        PowerUI powerUI7 = PowerUI.this;
                        boolean z14 = PowerUI.DEBUG;
                        powerUI7.clearScheduling();
                        return;
                    }
                    if (!action.equals("com.samsung.server.BatteryService.action.ACTION_SLEEP_CHARGING")) {
                        Slog.w("PowerUI", "unknown intent: " + intent);
                        return;
                    } else {
                        PowerUI.this.mSleepChargingEvent = intent.getStringExtra("sleep_charging_event");
                        PowerUI.this.mChargingStartTime = intent.getStringExtra("sleep_charging_finish_time");
                        PowerUI powerUI8 = PowerUI.this;
                        powerUI8.checkAdaptiveProtectionNotification(powerUI8.mSleepChargingEvent, powerUI8.mChargingStartTime);
                        return;
                    }
                }
                Slog.i("PowerUI", "get TEST action : ".concat(action));
                if (PowerUiRune.TIPS_NOTIFICATION) {
                    if (action.equals("com.samsung.android.sm.IGNORE_RUT_TIPS_NOTI")) {
                        SharedPreferences sharedPreferences = PowerUI.this.mContext.getSharedPreferences("com.android.systemui.power_tips_notification", 0);
                        if (sharedPreferences != null) {
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putBoolean("ignoreRUT", true);
                            edit.commit();
                            return;
                        }
                        return;
                    }
                    if (action.equals("com.samsung.android.sm.CLEAR_TIPS_NOTI")) {
                        SharedPreferences sharedPreferences2 = PowerUI.this.mContext.getSharedPreferences("com.android.systemui.power_tips_notification", 0);
                        if (sharedPreferences2 != null) {
                            SharedPreferences.Editor edit2 = sharedPreferences2.edit();
                            edit2.putBoolean("tipsNotiConfirmed", false);
                            edit2.putBoolean("ignoreRUT", false);
                            edit2.putInt("tipsNotiRegisteredCount", 0);
                            edit2.putLong("tipsNotiLastTime", 0L);
                            edit2.commit();
                            return;
                        }
                        return;
                    }
                    if (action.equals("android.intent.action.tips.noti.confirmed")) {
                        String stringExtra = intent.getStringExtra("tips_action_confirmed_id");
                        SharedPreferences sharedPreferences3 = PowerUI.this.mContext.getSharedPreferences("com.android.systemui.power_tips_notification", 0);
                        if (sharedPreferences3 == null || stringExtra == null || !stringExtra.contains(String.valueOf(120999))) {
                            return;
                        }
                        Log.i("PowerUI", "120999 was clicked, so we set preference !!");
                        SharedPreferences.Editor edit3 = sharedPreferences3.edit();
                        edit3.putBoolean("tipsNotiConfirmed", true);
                        edit3.commit();
                        return;
                    }
                    return;
                }
                return;
            }
            this.mHasReceivedBattery = true;
            PowerUI powerUI9 = PowerUI.this;
            int i8 = powerUI9.mBatteryLevel;
            powerUI9.mBatteryLevel = intent.getIntExtra(ActionResults.RESULT_SET_VOLUME_SUCCESS, 100);
            PowerUI powerUI10 = PowerUI.this;
            int i9 = powerUI10.mBatteryStatus;
            powerUI10.mBatteryStatus = intent.getIntExtra(IMSParameter.CALL.STATUS, 1);
            PowerUI powerUI11 = PowerUI.this;
            int i10 = powerUI11.mPlugType;
            powerUI11.mPlugType = intent.getIntExtra("plugged", 1);
            PowerUI powerUI12 = PowerUI.this;
            if (i10 != powerUI12.mPlugType) {
                powerUI12.mHandler.removeCallbacks(powerUI12.mAfterChargingNoticeTask);
                PowerUI.this.mIsChangedStringAfterCharging = false;
            }
            PowerUI powerUI13 = PowerUI.this;
            int i11 = powerUI13.mInvalidCharger;
            powerUI13.mInvalidCharger = intent.getIntExtra("invalid_charger", 0);
            PowerUI powerUI14 = PowerUI.this;
            powerUI14.mLastBatteryStateSnapshot = powerUI14.mCurrentBatteryStateSnapshot;
            final boolean z15 = powerUI14.mPlugType != 0;
            boolean z16 = i10 != 0;
            int findBatteryLevelBucket = powerUI14.findBatteryLevelBucket(i8);
            PowerUI powerUI15 = PowerUI.this;
            final int findBatteryLevelBucket2 = powerUI15.findBatteryLevelBucket(powerUI15.mBatteryLevel);
            PowerUI powerUI16 = PowerUI.this;
            int i12 = powerUI16.mBatteryOnline;
            boolean z17 = powerUI16.mFullyConnected;
            powerUI16.mBatteryOnline = intent.getIntExtra("online", 1);
            PowerUI.this.mBatteryHighVoltageCharger = intent.getBooleanExtra("hv_charger", false);
            PowerUI powerUI17 = PowerUI.this;
            if (powerUI17.mBootCompleted) {
                powerUI17.mFullyConnected = (intent.getIntExtra("misc_event", 0) & 4) == 0;
                PowerUI.this.mBatterySlowCharger = intent.getIntExtra("charge_type", 0) == 2;
            }
            StringBuilder sb = new StringBuilder("mBootCompleted = ");
            sb.append(PowerUI.this.mBootCompleted);
            sb.append(" |  mFullyConnected = ");
            NotificationListener$$ExternalSyntheticOutline0.m123m(sb, PowerUI.this.mFullyConnected, "PowerUI");
            PowerUI powerUI18 = PowerUI.this;
            int i13 = powerUI18.mBatteryHealth;
            int i14 = powerUI18.mBatteryMiscEvent;
            powerUI18.mBatteryMiscEvent = intent.getIntExtra("misc_event", 0);
            int intExtra = intent.getIntExtra("health", 1);
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("BATTERY_HEALTH_CHECK extraHealth=", intExtra, " mBatteryMiscEvent=");
            m1m.append(PowerUI.this.mBatteryMiscEvent);
            Slog.i("PowerUI", m1m.toString());
            if (intExtra == 3 && (PowerUI.this.mBatteryMiscEvent & QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING) != 0) {
                intExtra = 8;
            }
            PowerUI powerUI19 = PowerUI.this;
            powerUI19.mBatteryHealth = intExtra;
            int i15 = powerUI19.mBatterySwellingMode;
            boolean z18 = PowerUiRune.BATTERY_SWELLING_NOTICE;
            if (z18) {
                int intExtra2 = intent.getIntExtra("current_event", 0);
                if ((intExtra2 & 16) != 0) {
                    PowerUI.this.mBatterySwellingMode = 1;
                } else if ((intExtra2 & 32) != 0) {
                    PowerUI.this.mBatterySwellingMode = 2;
                } else {
                    i = 0;
                    PowerUI.this.mBatterySwellingMode = 0;
                    PowerUI.this.mSuperFastCharger = intent.getIntExtra("charger_type", i);
                    PowerUI powerUI20 = PowerUI.this;
                    int i16 = powerUI20.mBatteryCurrentEvent;
                    z = PowerUiRune.HV_CHARGER_ENABLE_POPUP;
                    if (z) {
                        powerUI20.mBatteryCurrentEvent = intent.getIntExtra("current_event", i);
                    }
                    PowerUI powerUI21 = PowerUI.this;
                    SecPowerNotificationWarnings secPowerNotificationWarnings9 = powerUI21.mSecPowerNotificationWarnings;
                    int i17 = powerUI21.mBatteryStatus;
                    int i18 = powerUI21.mBatteryHealth;
                    secPowerNotificationWarnings9.mBatteryStatus = i17;
                    secPowerNotificationWarnings9.mBatteryHealth = i18;
                    if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION) {
                        int i19 = powerUI21.mPlugType;
                        if (i19 == 0 || (i19 > 0 && i17 == 5)) {
                            powerUI21.mIsAfterAdaptiveProtection = false;
                            powerUI21.dismissAdaptiveProtectionNotification();
                        } else if (i8 == 100 && i8 != powerUI21.mBatteryLevel && PowerUtils.isSleepChargingOn(powerUI21.mContext)) {
                            Slog.i("PowerUI", "show again AdaptiveProtection Notification");
                            PowerUI powerUI22 = PowerUI.this;
                            powerUI22.checkAdaptiveProtectionNotification(powerUI22.mSleepChargingEvent, powerUI22.mChargingStartTime);
                        }
                    }
                    z2 = PowerUI.DEBUG;
                    if (z2) {
                        Slog.d("PowerUI", "buckets   ....." + PowerUI.this.mLowBatteryAlertCloseLevel + " .. " + PowerUI.this.mLowBatteryReminderLevels[0] + " .. " + PowerUI.this.mLowBatteryReminderLevels[1]);
                        StringBuilder sb2 = new StringBuilder("level          ");
                        sb2.append(i8);
                        sb2.append(" --> ");
                        sb2.append(PowerUI.this.mBatteryLevel);
                        Slog.d("PowerUI", sb2.toString());
                        Slog.d("PowerUI", "status         " + i9 + " --> " + PowerUI.this.mBatteryStatus);
                        Slog.d("PowerUI", "plugType       " + i10 + " --> " + PowerUI.this.mPlugType);
                        Slog.d("PowerUI", "invalidCharger " + i11 + " --> " + PowerUI.this.mInvalidCharger);
                        Slog.d("PowerUI", "bucket         " + findBatteryLevelBucket + " --> " + findBatteryLevelBucket2);
                        Slog.d("PowerUI", "plugged        " + z16 + " --> " + z15);
                        Slog.d("PowerUI", "current_Event  " + i16 + " ---> " + PowerUI.this.mBatteryCurrentEvent);
                        Slog.d("PowerUI", "health  " + i13 + " ---> " + PowerUI.this.mBatteryHealth);
                    }
                    PowerUI powerUI23 = PowerUI.this;
                    SecPowerNotificationWarnings secPowerNotificationWarnings10 = powerUI23.mSecPowerNotificationWarnings;
                    secPowerNotificationWarnings10.mBatteryLevel = powerUI23.mBatteryLevel;
                    if (findBatteryLevelBucket2 < 0) {
                        i2 = findBatteryLevelBucket;
                        i3 = i8;
                        secPowerNotificationWarnings10.mWarningTriggerTimeMs = 0L;
                    } else {
                        i2 = findBatteryLevelBucket;
                        i3 = i8;
                        if (findBatteryLevelBucket2 < secPowerNotificationWarnings10.mBucket) {
                            secPowerNotificationWarnings10.mWarningTriggerTimeMs = System.currentTimeMillis();
                        }
                    }
                    secPowerNotificationWarnings10.mBucket = findBatteryLevelBucket2;
                    PowerUI powerUI24 = PowerUI.this;
                    PowerNotificationWarnings powerNotificationWarnings = (PowerNotificationWarnings) powerUI24.mWarnings;
                    powerNotificationWarnings.mBatteryLevel = powerUI24.mBatteryLevel;
                    if (findBatteryLevelBucket2 < 0) {
                        i4 = i3;
                        powerNotificationWarnings.mWarningTriggerTimeMs = 0L;
                    } else {
                        i4 = i3;
                        if (findBatteryLevelBucket2 < powerNotificationWarnings.mBucket) {
                            powerNotificationWarnings.mWarningTriggerTimeMs = System.currentTimeMillis();
                        }
                    }
                    powerNotificationWarnings.mBucket = findBatteryLevelBucket2;
                    if (i11 != 0 && PowerUI.this.mInvalidCharger != 0) {
                        Slog.d("PowerUI", "showing invalid charger warning");
                        PowerNotificationWarnings powerNotificationWarnings2 = (PowerNotificationWarnings) PowerUI.this.mWarnings;
                        powerNotificationWarnings2.mInvalidCharger = true;
                        powerNotificationWarnings2.updateNotification();
                        return;
                    }
                    if (i11 != 0) {
                        PowerUI powerUI25 = PowerUI.this;
                        if (powerUI25.mInvalidCharger == 0) {
                            PowerNotificationWarnings powerNotificationWarnings3 = (PowerNotificationWarnings) powerUI25.mWarnings;
                            if (powerNotificationWarnings3.mInvalidCharger) {
                                Slog.i("PowerUI.Notification", "dismissing invalid charger notification");
                            }
                            powerNotificationWarnings3.mInvalidCharger = false;
                            powerNotificationWarnings3.updateNotification();
                            future = PowerUI.this.mLastShowWarningTask;
                            if (future != null) {
                                future.cancel(true);
                                if (z2) {
                                    Slog.d("PowerUI", "cancelled task");
                                }
                            }
                            PowerUI.this.mLastShowWarningTask = ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.systemui.power.PowerUI$Receiver$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    PowerUI.Receiver receiver = PowerUI.Receiver.this;
                                    boolean z19 = z15;
                                    int i20 = findBatteryLevelBucket2;
                                    PowerUI powerUI26 = PowerUI.this;
                                    powerUI26.mEnhancedEstimates.getClass();
                                    boolean isPowerSaveMode = powerUI26.mPowerManager.isPowerSaveMode();
                                    boolean z20 = PowerUI.DEBUG;
                                    if (z20) {
                                        Slog.d("PowerUI", "evaluating which notification to show");
                                    }
                                    if (z20) {
                                        Slog.d("PowerUI", "using standard");
                                    }
                                    int i21 = powerUI26.mBatteryLevel;
                                    int i22 = powerUI26.mBatteryStatus;
                                    int[] iArr = powerUI26.mLowBatteryReminderLevels;
                                    boolean z21 = true;
                                    BatteryStateSnapshot batteryStateSnapshot = new BatteryStateSnapshot(i21, isPowerSaveMode, z19, i20, i22, iArr[1], iArr[0]);
                                    powerUI26.mCurrentBatteryStateSnapshot = batteryStateSnapshot;
                                    ((PowerNotificationWarnings) powerUI26.mWarnings).mCurrentBatterySnapshot = batteryStateSnapshot;
                                    SecPowerNotificationWarnings secPowerNotificationWarnings11 = powerUI26.mSecPowerNotificationWarnings;
                                    secPowerNotificationWarnings11.getClass();
                                    BatteryStateSnapshot batteryStateSnapshot2 = powerUI26.mCurrentBatteryStateSnapshot;
                                    BatteryStateSnapshot batteryStateSnapshot3 = powerUI26.mLastBatteryStateSnapshot;
                                    int i23 = batteryStateSnapshot2.bucket;
                                    int i24 = batteryStateSnapshot3.bucket;
                                    boolean z22 = batteryStateSnapshot3.plugged;
                                    boolean z23 = i23 != i24 || z22;
                                    boolean z24 = batteryStateSnapshot2.plugged;
                                    boolean z25 = !z24 && (i23 < i24 || z22) && i23 < 0 && batteryStateSnapshot2.batteryStatus != 1;
                                    Handler handler2 = powerUI26.mHandler;
                                    PowerUI.RunnableC19467 runnableC19467 = powerUI26.mLowBatteryWarningTask;
                                    if (z25) {
                                        if (i23 == i24 || !secPowerNotificationWarnings11.mWarning) {
                                            secPowerNotificationWarnings11.showLowBatteryWarning(z23);
                                            return;
                                        }
                                        secPowerNotificationWarnings11.dismissLowBatteryWarning();
                                        powerUI26.mIsRunningLowBatteryTask = true;
                                        handler2.postDelayed(runnableC19467, 500L);
                                        return;
                                    }
                                    if (!z24 && (i23 <= i24 || i23 <= 0)) {
                                        z21 = false;
                                    }
                                    if (z21) {
                                        if (powerUI26.mIsRunningLowBatteryTask) {
                                            handler2.removeCallbacks(runnableC19467);
                                            powerUI26.mIsRunningLowBatteryTask = false;
                                        }
                                        secPowerNotificationWarnings11.dismissLowBatteryWarning();
                                        return;
                                    }
                                    if (!secPowerNotificationWarnings11.mWarning || batteryStateSnapshot2.batteryLevel == batteryStateSnapshot3.batteryLevel) {
                                        return;
                                    }
                                    secPowerNotificationWarnings11.updateNotification();
                                }
                            });
                            PowerUI.m1601$$Nest$mcheckConnectedChargerStatus(PowerUI.this, i10);
                            PowerUI.m1610$$Nest$mplayChargerConnectionAnimation(i10, i9, PowerUI.this, z17);
                            PowerUI.m1611$$Nest$mplayChargerConnectionSound(i10, i9, PowerUI.this, z17);
                            PowerUI.m1609$$Nest$mcheckWirelessMisalign(PowerUI.this, i14);
                            PowerUI.m1613$$Nest$mshowChargingNotice(PowerUI.this, i10, i9, i13);
                            if (z18) {
                                PowerUI.m1600$$Nest$mcheckBatterySwellingStatus(PowerUI.this, i15, i9);
                            }
                            TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("mBatteryMiscEvent = "), PowerUI.this.mBatteryMiscEvent, "PowerUI");
                            PowerUI powerUI26 = PowerUI.this;
                            PowerUI.m1598$$Nest$mcheckBatteryHealthInterruptionStatus(i13, powerUI26, (powerUI26.mBatteryMiscEvent & 16384) != 0);
                            if (PowerUiRune.INCOMPATIBLE_CHARGER_CHECK) {
                                PowerUI.m1604$$Nest$mcheckIncompatibleChargerConnection(PowerUI.this, i12);
                            }
                            if (PowerUiRune.FULL_BATTERY_CHECK) {
                                PowerUI.m1602$$Nest$mcheckFullBatteryStatus(PowerUI.this, i9);
                            }
                            PowerUI.m1612$$Nest$msendLowBatteryDumpIfNeeded(PowerUI.this, i4, i2, findBatteryLevelBucket2);
                            if (z) {
                                PowerUI.m1603$$Nest$mcheckHVchargerEnableConnection(PowerUI.this, i16);
                            }
                            PowerUI.m1597$$Nest$mcheckAbnormalChargingPad(PowerUI.this, i14);
                            if (PowerUiRune.TIPS_NOTIFICATION) {
                                PowerUI.m1605$$Nest$mcheckTipsNotification(PowerUI.this, i4);
                            }
                            if (PowerUiRune.CHN_SMART_MANAGER) {
                                PowerUI.m1606$$Nest$mcheckTurnOffPsmNotification(PowerUI.this, i4);
                            }
                            if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE) {
                                PowerUI.m1608$$Nest$mcheckTurnOnProtectBatteryByLongTermCharge(PowerUI.this);
                            } else if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_TA) {
                                PowerUI.m1607$$Nest$mcheckTurnOnProtectBatteryByLongTa(PowerUI.this);
                            }
                            if (PowerUiRune.BATTERY_PROTECTION_NOTIFICATION) {
                                PowerUI.this.checkBatteryProtectionNotification();
                            }
                            if (PowerUiRune.BATTERY_PROTECTION_TIPS_NOTIFICATION) {
                                PowerUI.m1599$$Nest$mcheckBatteryProtectionTipsNotification(PowerUI.this, i10);
                                return;
                            }
                            return;
                        }
                    }
                    if (((PowerNotificationWarnings) PowerUI.this.mWarnings).mInvalidCharger) {
                        if (z2) {
                            Slog.d("PowerUI", "Bad Charger");
                            return;
                        }
                        return;
                    }
                    future = PowerUI.this.mLastShowWarningTask;
                    if (future != null) {
                    }
                    PowerUI.this.mLastShowWarningTask = ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.systemui.power.PowerUI$Receiver$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            PowerUI.Receiver receiver = PowerUI.Receiver.this;
                            boolean z19 = z15;
                            int i20 = findBatteryLevelBucket2;
                            PowerUI powerUI262 = PowerUI.this;
                            powerUI262.mEnhancedEstimates.getClass();
                            boolean isPowerSaveMode = powerUI262.mPowerManager.isPowerSaveMode();
                            boolean z20 = PowerUI.DEBUG;
                            if (z20) {
                                Slog.d("PowerUI", "evaluating which notification to show");
                            }
                            if (z20) {
                                Slog.d("PowerUI", "using standard");
                            }
                            int i21 = powerUI262.mBatteryLevel;
                            int i22 = powerUI262.mBatteryStatus;
                            int[] iArr = powerUI262.mLowBatteryReminderLevels;
                            boolean z21 = true;
                            BatteryStateSnapshot batteryStateSnapshot = new BatteryStateSnapshot(i21, isPowerSaveMode, z19, i20, i22, iArr[1], iArr[0]);
                            powerUI262.mCurrentBatteryStateSnapshot = batteryStateSnapshot;
                            ((PowerNotificationWarnings) powerUI262.mWarnings).mCurrentBatterySnapshot = batteryStateSnapshot;
                            SecPowerNotificationWarnings secPowerNotificationWarnings11 = powerUI262.mSecPowerNotificationWarnings;
                            secPowerNotificationWarnings11.getClass();
                            BatteryStateSnapshot batteryStateSnapshot2 = powerUI262.mCurrentBatteryStateSnapshot;
                            BatteryStateSnapshot batteryStateSnapshot3 = powerUI262.mLastBatteryStateSnapshot;
                            int i23 = batteryStateSnapshot2.bucket;
                            int i24 = batteryStateSnapshot3.bucket;
                            boolean z22 = batteryStateSnapshot3.plugged;
                            boolean z23 = i23 != i24 || z22;
                            boolean z24 = batteryStateSnapshot2.plugged;
                            boolean z25 = !z24 && (i23 < i24 || z22) && i23 < 0 && batteryStateSnapshot2.batteryStatus != 1;
                            Handler handler2 = powerUI262.mHandler;
                            PowerUI.RunnableC19467 runnableC19467 = powerUI262.mLowBatteryWarningTask;
                            if (z25) {
                                if (i23 == i24 || !secPowerNotificationWarnings11.mWarning) {
                                    secPowerNotificationWarnings11.showLowBatteryWarning(z23);
                                    return;
                                }
                                secPowerNotificationWarnings11.dismissLowBatteryWarning();
                                powerUI262.mIsRunningLowBatteryTask = true;
                                handler2.postDelayed(runnableC19467, 500L);
                                return;
                            }
                            if (!z24 && (i23 <= i24 || i23 <= 0)) {
                                z21 = false;
                            }
                            if (z21) {
                                if (powerUI262.mIsRunningLowBatteryTask) {
                                    handler2.removeCallbacks(runnableC19467);
                                    powerUI262.mIsRunningLowBatteryTask = false;
                                }
                                secPowerNotificationWarnings11.dismissLowBatteryWarning();
                                return;
                            }
                            if (!secPowerNotificationWarnings11.mWarning || batteryStateSnapshot2.batteryLevel == batteryStateSnapshot3.batteryLevel) {
                                return;
                            }
                            secPowerNotificationWarnings11.updateNotification();
                        }
                    });
                    PowerUI.m1601$$Nest$mcheckConnectedChargerStatus(PowerUI.this, i10);
                    PowerUI.m1610$$Nest$mplayChargerConnectionAnimation(i10, i9, PowerUI.this, z17);
                    PowerUI.m1611$$Nest$mplayChargerConnectionSound(i10, i9, PowerUI.this, z17);
                    PowerUI.m1609$$Nest$mcheckWirelessMisalign(PowerUI.this, i14);
                    PowerUI.m1613$$Nest$mshowChargingNotice(PowerUI.this, i10, i9, i13);
                    if (z18) {
                    }
                    TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("mBatteryMiscEvent = "), PowerUI.this.mBatteryMiscEvent, "PowerUI");
                    PowerUI powerUI262 = PowerUI.this;
                    PowerUI.m1598$$Nest$mcheckBatteryHealthInterruptionStatus(i13, powerUI262, (powerUI262.mBatteryMiscEvent & 16384) != 0);
                    if (PowerUiRune.INCOMPATIBLE_CHARGER_CHECK) {
                    }
                    if (PowerUiRune.FULL_BATTERY_CHECK) {
                    }
                    PowerUI.m1612$$Nest$msendLowBatteryDumpIfNeeded(PowerUI.this, i4, i2, findBatteryLevelBucket2);
                    if (z) {
                    }
                    PowerUI.m1597$$Nest$mcheckAbnormalChargingPad(PowerUI.this, i14);
                    if (PowerUiRune.TIPS_NOTIFICATION) {
                    }
                    if (PowerUiRune.CHN_SMART_MANAGER) {
                    }
                    if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE) {
                    }
                    if (PowerUiRune.BATTERY_PROTECTION_NOTIFICATION) {
                    }
                    if (PowerUiRune.BATTERY_PROTECTION_TIPS_NOTIFICATION) {
                    }
                }
            }
            i = 0;
            PowerUI.this.mSuperFastCharger = intent.getIntExtra("charger_type", i);
            PowerUI powerUI202 = PowerUI.this;
            int i162 = powerUI202.mBatteryCurrentEvent;
            z = PowerUiRune.HV_CHARGER_ENABLE_POPUP;
            if (z) {
            }
            PowerUI powerUI212 = PowerUI.this;
            SecPowerNotificationWarnings secPowerNotificationWarnings92 = powerUI212.mSecPowerNotificationWarnings;
            int i172 = powerUI212.mBatteryStatus;
            int i182 = powerUI212.mBatteryHealth;
            secPowerNotificationWarnings92.mBatteryStatus = i172;
            secPowerNotificationWarnings92.mBatteryHealth = i182;
            if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION) {
            }
            z2 = PowerUI.DEBUG;
            if (z2) {
            }
            PowerUI powerUI232 = PowerUI.this;
            SecPowerNotificationWarnings secPowerNotificationWarnings102 = powerUI232.mSecPowerNotificationWarnings;
            secPowerNotificationWarnings102.mBatteryLevel = powerUI232.mBatteryLevel;
            if (findBatteryLevelBucket2 < 0) {
            }
            secPowerNotificationWarnings102.mBucket = findBatteryLevelBucket2;
            PowerUI powerUI242 = PowerUI.this;
            PowerNotificationWarnings powerNotificationWarnings4 = (PowerNotificationWarnings) powerUI242.mWarnings;
            powerNotificationWarnings4.mBatteryLevel = powerUI242.mBatteryLevel;
            if (findBatteryLevelBucket2 < 0) {
            }
            powerNotificationWarnings4.mBucket = findBatteryLevelBucket2;
            if (i11 != 0) {
            }
            if (i11 != 0) {
            }
            if (((PowerNotificationWarnings) PowerUI.this.mWarnings).mInvalidCharger) {
            }
            future = PowerUI.this.mLastShowWarningTask;
            if (future != null) {
            }
            PowerUI.this.mLastShowWarningTask = ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.systemui.power.PowerUI$Receiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PowerUI.Receiver receiver = PowerUI.Receiver.this;
                    boolean z19 = z15;
                    int i20 = findBatteryLevelBucket2;
                    PowerUI powerUI2622 = PowerUI.this;
                    powerUI2622.mEnhancedEstimates.getClass();
                    boolean isPowerSaveMode = powerUI2622.mPowerManager.isPowerSaveMode();
                    boolean z20 = PowerUI.DEBUG;
                    if (z20) {
                        Slog.d("PowerUI", "evaluating which notification to show");
                    }
                    if (z20) {
                        Slog.d("PowerUI", "using standard");
                    }
                    int i21 = powerUI2622.mBatteryLevel;
                    int i22 = powerUI2622.mBatteryStatus;
                    int[] iArr = powerUI2622.mLowBatteryReminderLevels;
                    boolean z21 = true;
                    BatteryStateSnapshot batteryStateSnapshot = new BatteryStateSnapshot(i21, isPowerSaveMode, z19, i20, i22, iArr[1], iArr[0]);
                    powerUI2622.mCurrentBatteryStateSnapshot = batteryStateSnapshot;
                    ((PowerNotificationWarnings) powerUI2622.mWarnings).mCurrentBatterySnapshot = batteryStateSnapshot;
                    SecPowerNotificationWarnings secPowerNotificationWarnings11 = powerUI2622.mSecPowerNotificationWarnings;
                    secPowerNotificationWarnings11.getClass();
                    BatteryStateSnapshot batteryStateSnapshot2 = powerUI2622.mCurrentBatteryStateSnapshot;
                    BatteryStateSnapshot batteryStateSnapshot3 = powerUI2622.mLastBatteryStateSnapshot;
                    int i23 = batteryStateSnapshot2.bucket;
                    int i24 = batteryStateSnapshot3.bucket;
                    boolean z22 = batteryStateSnapshot3.plugged;
                    boolean z23 = i23 != i24 || z22;
                    boolean z24 = batteryStateSnapshot2.plugged;
                    boolean z25 = !z24 && (i23 < i24 || z22) && i23 < 0 && batteryStateSnapshot2.batteryStatus != 1;
                    Handler handler2 = powerUI2622.mHandler;
                    PowerUI.RunnableC19467 runnableC19467 = powerUI2622.mLowBatteryWarningTask;
                    if (z25) {
                        if (i23 == i24 || !secPowerNotificationWarnings11.mWarning) {
                            secPowerNotificationWarnings11.showLowBatteryWarning(z23);
                            return;
                        }
                        secPowerNotificationWarnings11.dismissLowBatteryWarning();
                        powerUI2622.mIsRunningLowBatteryTask = true;
                        handler2.postDelayed(runnableC19467, 500L);
                        return;
                    }
                    if (!z24 && (i23 <= i24 || i23 <= 0)) {
                        z21 = false;
                    }
                    if (z21) {
                        if (powerUI2622.mIsRunningLowBatteryTask) {
                            handler2.removeCallbacks(runnableC19467);
                            powerUI2622.mIsRunningLowBatteryTask = false;
                        }
                        secPowerNotificationWarnings11.dismissLowBatteryWarning();
                        return;
                    }
                    if (!secPowerNotificationWarnings11.mWarning || batteryStateSnapshot2.batteryLevel == batteryStateSnapshot3.batteryLevel) {
                        return;
                    }
                    secPowerNotificationWarnings11.updateNotification();
                }
            });
            PowerUI.m1601$$Nest$mcheckConnectedChargerStatus(PowerUI.this, i10);
            PowerUI.m1610$$Nest$mplayChargerConnectionAnimation(i10, i9, PowerUI.this, z17);
            PowerUI.m1611$$Nest$mplayChargerConnectionSound(i10, i9, PowerUI.this, z17);
            PowerUI.m1609$$Nest$mcheckWirelessMisalign(PowerUI.this, i14);
            PowerUI.m1613$$Nest$mshowChargingNotice(PowerUI.this, i10, i9, i13);
            if (z18) {
            }
            TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("mBatteryMiscEvent = "), PowerUI.this.mBatteryMiscEvent, "PowerUI");
            PowerUI powerUI2622 = PowerUI.this;
            PowerUI.m1598$$Nest$mcheckBatteryHealthInterruptionStatus(i13, powerUI2622, (powerUI2622.mBatteryMiscEvent & 16384) != 0);
            if (PowerUiRune.INCOMPATIBLE_CHARGER_CHECK) {
            }
            if (PowerUiRune.FULL_BATTERY_CHECK) {
            }
            PowerUI.m1612$$Nest$msendLowBatteryDumpIfNeeded(PowerUI.this, i4, i2, findBatteryLevelBucket2);
            if (z) {
            }
            PowerUI.m1597$$Nest$mcheckAbnormalChargingPad(PowerUI.this, i14);
            if (PowerUiRune.TIPS_NOTIFICATION) {
            }
            if (PowerUiRune.CHN_SMART_MANAGER) {
            }
            if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE) {
            }
            if (PowerUiRune.BATTERY_PROTECTION_NOTIFICATION) {
            }
            if (PowerUiRune.BATTERY_PROTECTION_TIPS_NOTIFICATION) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    final class SkinThermalEventListener extends IThermalEventListener.Stub {
        public SkinThermalEventListener() {
        }

        public final void notifyThrottling(Temperature temperature) {
            int status = temperature.getStatus();
            if (status < 5) {
                PowerNotificationWarnings powerNotificationWarnings = (PowerNotificationWarnings) PowerUI.this.mWarnings;
                if (powerNotificationWarnings.mHighTempWarning) {
                    powerNotificationWarnings.dismissHighTemperatureWarningInternal();
                    return;
                }
                return;
            }
            if (((Boolean) ((Optional) PowerUI.this.mCentralSurfacesOptionalLazy.get()).map(new PowerUI$SkinThermalEventListener$$ExternalSyntheticLambda0()).orElse(Boolean.FALSE)).booleanValue()) {
                return;
            }
            PowerNotificationWarnings powerNotificationWarnings2 = (PowerNotificationWarnings) PowerUI.this.mWarnings;
            if (!powerNotificationWarnings2.mHighTempWarning) {
                powerNotificationWarnings2.mHighTempWarning = true;
                Context context = powerNotificationWarnings2.mContext;
                String string = context.getString(R.string.high_temp_notif_message);
                Notification.Builder color = new Notification.Builder(context, "ALR").setSmallIcon(R.drawable.ic_device_thermostat_24).setWhen(0L).setShowWhen(false).setContentTitle(context.getString(R.string.high_temp_title)).setContentText(string).setStyle(new Notification.BigTextStyle().bigText(string)).setVisibility(1).setContentIntent(powerNotificationWarnings2.pendingBroadcast("PNW.clickedTempWarning")).setDeleteIntent(powerNotificationWarnings2.pendingBroadcast("PNW.dismissedTempWarning")).setColor(Utils.getColorAttrDefaultColor(android.R.attr.colorError, context, 0));
                SystemUIApplication.overrideNotificationAppName(context, color, false);
                powerNotificationWarnings2.mNoMan.notifyAsUser("high_temp", 4, color.build(), UserHandle.ALL);
            }
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("SkinThermalEventListener: notifyThrottling was called , current skin status = ", status, ", temperature = ");
            m1m.append(temperature.getValue());
            Slog.d("PowerUI", m1m.toString());
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    final class UsbThermalEventListener extends IThermalEventListener.Stub {
        public UsbThermalEventListener() {
        }

        public final void notifyThrottling(Temperature temperature) {
            int status = temperature.getStatus();
            if (status >= 5) {
                final PowerNotificationWarnings powerNotificationWarnings = (PowerNotificationWarnings) PowerUI.this.mWarnings;
                powerNotificationWarnings.mHandler.post(new Runnable() { // from class: com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PowerNotificationWarnings powerNotificationWarnings2 = PowerNotificationWarnings.this;
                        if (powerNotificationWarnings2.mUsbHighTempDialog != null) {
                            return;
                        }
                        Context context = powerNotificationWarnings2.mContext;
                        SystemUIDialog systemUIDialog = new SystemUIDialog(context, 2132018528);
                        systemUIDialog.setCancelable(false);
                        systemUIDialog.setIconAttribute(android.R.attr.alertDialogIcon);
                        systemUIDialog.setTitle(R.string.high_temp_alarm_title);
                        SystemUIDialog.setShowForAllUsers(systemUIDialog);
                        systemUIDialog.setMessage(context.getString(R.string.high_temp_alarm_notify_message, ""));
                        systemUIDialog.setPositiveButton(android.R.string.ok, new PowerNotificationWarnings$$ExternalSyntheticLambda1(powerNotificationWarnings2, 0));
                        systemUIDialog.setNegativeButton(R.string.high_temp_alarm_help_care_steps, new PowerNotificationWarnings$$ExternalSyntheticLambda1(powerNotificationWarnings2, 1));
                        systemUIDialog.setOnDismissListener(new PowerNotificationWarnings$$ExternalSyntheticLambda2(powerNotificationWarnings2, 0));
                        systemUIDialog.getWindow().addFlags(2097280);
                        systemUIDialog.show();
                        powerNotificationWarnings2.mUsbHighTempDialog = systemUIDialog;
                        Events.writeEvent(19, 3, Boolean.valueOf(powerNotificationWarnings2.mKeyguard.isKeyguardLocked()));
                    }
                });
                Slog.d("PowerUI", "UsbThermalEventListener: notifyThrottling was called , current usb port status = " + status + ", temperature = " + temperature.getValue());
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface WarningsUI {
    }

    /* renamed from: -$$Nest$mcheckAbnormalChargingPad, reason: not valid java name */
    public static void m1597$$Nest$mcheckAbnormalChargingPad(PowerUI powerUI, int i) {
        int i2 = i & QuickStepContract.SYSUI_STATE_DEVICE_DOZING;
        SecPowerNotificationWarnings secPowerNotificationWarnings = powerUI.mSecPowerNotificationWarnings;
        if (i2 == 0 && (powerUI.mBatteryMiscEvent & QuickStepContract.SYSUI_STATE_DEVICE_DOZING) != 0) {
            secPowerNotificationWarnings.getClass();
            Log.i("SecPowerUI.Notification", "showAbnormalPadNotification");
            secPowerNotificationWarnings.showNotification(8);
        } else {
            if (i2 == 0 || (powerUI.mBatteryMiscEvent & QuickStepContract.SYSUI_STATE_DEVICE_DOZING) != 0) {
                return;
            }
            secPowerNotificationWarnings.getClass();
            Log.i("SecPowerUI.Notification", "dismissAbnormalPadNotification");
            secPowerNotificationWarnings.cancelNotification(8);
        }
    }

    /* renamed from: -$$Nest$mcheckBatteryHealthInterruptionStatus, reason: not valid java name */
    public static void m1598$$Nest$mcheckBatteryHealthInterruptionStatus(int i, PowerUI powerUI, boolean z) {
        int i2;
        int i3 = powerUI.mBatteryStatus;
        PowerManager powerManager = powerUI.mPowerManager;
        SecPowerNotificationWarnings secPowerNotificationWarnings = powerUI.mSecPowerNotificationWarnings;
        if (4 == i3 && (3 == (i2 = powerUI.mBatteryHealth) || 7 == i2 || 6 == i2)) {
            if (6 == i2 && z) {
                Log.i("PowerUI", "This status is not charging && false present but direct mode , so we do nothing !!");
                return;
            }
            Log.i("PowerUI", "Unhealth state");
            if (powerUI.mBatteryHealthInterruptionScreenDimWakeLock == null) {
                PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(268435462, "PowerUI");
                powerUI.mBatteryHealthInterruptionScreenDimWakeLock = newWakeLock;
                if (PowerUiRune.KEEP_DIMMING_AT_BATTERY_HEALTH_INTERRUPTION && powerUI.mBatteryHealth == 6) {
                    newWakeLock.acquire();
                } else {
                    newWakeLock.acquire(60000L);
                }
                if (powerUI.mBatteryHealth == 6) {
                    if (powerUI.mBatteryHealthInterruptionPartialWakeLock == null) {
                        powerUI.mBatteryHealthInterruptionPartialWakeLock = powerManager.newWakeLock(1, "PowerUI");
                    }
                    powerUI.mBatteryHealthInterruptionPartialWakeLock.acquire();
                }
                secPowerNotificationWarnings.showBatteryHealthInterruptionWarning();
            } else if (8 == i) {
                secPowerNotificationWarnings.showBatteryHealthInterruptionWarning();
            }
        } else if (4 == i3 && 8 == powerUI.mBatteryHealth) {
            PowerManager.WakeLock wakeLock = powerUI.mBatteryHealthInterruptionScreenDimWakeLock;
            if (wakeLock == null) {
                PowerManager.WakeLock newWakeLock2 = powerManager.newWakeLock(268435462, "PowerUI");
                powerUI.mBatteryHealthInterruptionScreenDimWakeLock = newWakeLock2;
                if (PowerUiRune.KEEP_DIMMING_AT_BATTERY_HEALTH_INTERRUPTION) {
                    newWakeLock2.acquire();
                } else {
                    newWakeLock2.acquire(60000L);
                }
                if (powerUI.mBatteryHealthInterruptionPartialWakeLock == null) {
                    powerUI.mBatteryHealthInterruptionPartialWakeLock = powerManager.newWakeLock(1, "PowerUI");
                }
                powerUI.mBatteryHealthInterruptionPartialWakeLock.acquire();
                secPowerNotificationWarnings.showBatteryHealthInterruptionWarning();
            } else if (3 == i) {
                if (!PowerUiRune.KEEP_DIMMING_AT_BATTERY_HEALTH_INTERRUPTION) {
                    wakeLock.acquire(60000L);
                }
                secPowerNotificationWarnings.showBatteryHealthInterruptionWarning();
            }
        } else if (powerUI.mBatteryHealthInterruptionScreenDimWakeLock != null) {
            if (4 == powerUI.mPlugType && 3 == i3 && 3 == powerUI.mBatteryHealth) {
                powerUI.mDismissBatteryHealthInterruptionWarning = true;
            } else {
                Handler handler = secPowerNotificationWarnings.mHandler;
                handler.removeCallbacks(secPowerNotificationWarnings.mBatteryHealthInterruptionTask);
                handler.removeCallbacks(secPowerNotificationWarnings.mTemperatureLimitAlertTask);
                Log.d("SecPowerUI.Notification", "dismissBatteryHealthInterruptionNotification()");
                secPowerNotificationWarnings.cancelNotification(5);
                Log.d("SecPowerUI.Notification", "dismissBatteryHealthInterruptionPopUp()");
                AlertDialog alertDialog = secPowerNotificationWarnings.mBatteryHealthInterruptionDialog;
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                powerUI.mDismissBatteryHealthInterruptionWarning = false;
            }
            if (PowerUiRune.KEEP_DIMMING_AT_BATTERY_HEALTH_INTERRUPTION && powerUI.mBatteryHealthInterruptionScreenDimWakeLock.isHeld()) {
                powerUI.mBatteryHealthInterruptionScreenDimWakeLock.release();
            }
            PowerManager.WakeLock wakeLock2 = powerUI.mBatteryHealthInterruptionPartialWakeLock;
            if (wakeLock2 != null && wakeLock2.isHeld()) {
                powerUI.mBatteryHealthInterruptionPartialWakeLock.release();
            }
            powerUI.mBatteryHealthInterruptionScreenDimWakeLock = null;
        }
        if (powerUI.mDismissBatteryHealthInterruptionWarning && 2 == powerUI.mBatteryStatus) {
            Handler handler2 = secPowerNotificationWarnings.mHandler;
            handler2.removeCallbacks(secPowerNotificationWarnings.mBatteryHealthInterruptionTask);
            handler2.removeCallbacks(secPowerNotificationWarnings.mTemperatureLimitAlertTask);
            Log.d("SecPowerUI.Notification", "dismissBatteryHealthInterruptionNotification()");
            secPowerNotificationWarnings.cancelNotification(5);
            Log.d("SecPowerUI.Notification", "dismissBatteryHealthInterruptionPopUp()");
            AlertDialog alertDialog2 = secPowerNotificationWarnings.mBatteryHealthInterruptionDialog;
            if (alertDialog2 != null) {
                alertDialog2.dismiss();
            }
            powerUI.mDismissBatteryHealthInterruptionWarning = false;
        }
        int i4 = powerUI.mBatteryHealth;
        if (i != i4) {
            if (5 == i || 5 == i4 || 9 == i || 9 == i4) {
                SettingsManager settingsManager = SettingsManager.getInstance();
                if (settingsManager != null && !settingsManager.getScreenWakeupOnPowerState()) {
                    Log.d("PowerUI", "Knox Customization: shouldWakeUp: not waking when battery health is changed");
                } else {
                    Log.d("PowerUI", "Overvoltage/Undervoltage status is changed so turn on the screen.");
                    powerManager.wakeUp(SystemClock.uptimeMillis(), powerUI.mContext.getOpPackageName());
                }
            }
        }
    }

    /* renamed from: -$$Nest$mcheckBatteryProtectionTipsNotification, reason: not valid java name */
    public static void m1599$$Nest$mcheckBatteryProtectionTipsNotification(PowerUI powerUI, int i) {
        Context context = powerUI.mContext;
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.android.systemui.power_tips_notification", 0);
            boolean z = sharedPreferences.getBoolean("tipsNotiFirstTime", true);
            Slog.d("PowerUI", "checkBatteryProtectionTipsNotification, first : " + z);
            if (i == 0) {
                int i2 = powerUI.mPlugType;
                if ((i2 == 1 || i2 == 4) && z) {
                    Intent intent = new Intent();
                    intent.setClassName("com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
                    intent.putExtra("tips_extras", 8);
                    intent.putExtra("tips_extras2", "BATT_0003");
                    intent.putExtra("tips_extras3", context.getString(R.string.battery_protection_tips_noti_content));
                    intent.putExtra("tips_extras4", context.getString(R.string.battery_protection_tips_noti_title));
                    Slog.d("PowerUI", "checkBatteryProtectionTipsNotification, show Battery protection tip");
                    if (context.startForegroundService(intent) != null) {
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.putBoolean("tipsNotiFirstTime", false);
                        edit.commit();
                    }
                }
            }
        } catch (ForegroundServiceStartNotAllowedException | SecurityException e) {
            Log.e("PowerUI", "Exception occur", e);
        }
    }

    /* renamed from: -$$Nest$mcheckBatterySwellingStatus, reason: not valid java name */
    public static void m1600$$Nest$mcheckBatterySwellingStatus(PowerUI powerUI, int i, int i2) {
        powerUI.getClass();
        StringBuilder sb = new StringBuilder("Battery swelling mode - priorBatterySwellingMode = ");
        sb.append(i);
        sb.append(" mBatterySwellingMode = ");
        sb.append(powerUI.mBatterySwellingMode);
        sb.append(" mBatteryStatus = ");
        RecyclerView$$ExternalSyntheticOutline0.m46m(sb, powerUI.mBatteryStatus, "PowerUI");
        int i3 = powerUI.mBatterySwellingMode;
        if (i == i3 && i2 == powerUI.mBatteryStatus) {
            return;
        }
        SecPowerNotificationWarnings secPowerNotificationWarnings = powerUI.mSecPowerNotificationWarnings;
        if (i3 <= 0 || powerUI.mBatteryStatus != 2) {
            secPowerNotificationWarnings.getClass();
            Log.d("SecPowerUI.Notification", "dismissBatterySwellingNotice()");
            secPowerNotificationWarnings.cancelNotification(4);
            Log.d("SecPowerUI.Notification", "dismissBatterySwellingPopup()");
            AlertDialog alertDialog = secPowerNotificationWarnings.mSwellingDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
                return;
            }
            return;
        }
        secPowerNotificationWarnings.getClass();
        if (i3 == 1) {
            Log.d("SecPowerUI.Notification", "showBatterySwellingNotice()");
            secPowerNotificationWarnings.showNotification(4);
        } else {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("Not battery low swelling mode, (ignore high swelling mode) so return ", i3, "SecPowerUI.Notification");
        }
        if (powerUI.mBatterySwellingMode == 1) {
            Log.d("SecPowerUI.Notification", "showBatterySwellingPopup for low temp");
            Log.d("SecPowerUI.Notification", "showBatterySwellingLowTempPopup()");
            if (secPowerNotificationWarnings.mSwellingDialog == null) {
                AlertDialog popupDialog = secPowerNotificationWarnings.getPopupDialog(3);
                secPowerNotificationWarnings.mSwellingDialog = popupDialog;
                if (popupDialog != null) {
                    popupDialog.setOnDismissListener(new SecPowerNotificationWarnings$$ExternalSyntheticLambda0(secPowerNotificationWarnings, 1));
                    secPowerNotificationWarnings.mSwellingDialog.show();
                    secPowerNotificationWarnings.turnOnScreen();
                }
            }
        } else {
            Log.d("SecPowerUI.Notification", "Neither battery swelling mode nor low temp, so no popup is shown");
        }
        secPowerNotificationWarnings.mDoNotShowChargingNotice = false;
        secPowerNotificationWarnings.mChargingType = 0;
        secPowerNotificationWarnings.mOldChargingType = 0;
        secPowerNotificationWarnings.mChargingTime = 0L;
        secPowerNotificationWarnings.mShowChargingNotice = false;
        secPowerNotificationWarnings.dismissSlowByChargerConnectionInfoPopUp();
        Log.d("SecPowerUI.Notification", "dismissChargingNotification()");
        secPowerNotificationWarnings.cancelNotification(2);
        powerUI.mHandler.removeCallbacks(powerUI.mAfterChargingNoticeTask);
        powerUI.mIsChangedStringAfterCharging = false;
    }

    /* renamed from: -$$Nest$mcheckConnectedChargerStatus, reason: not valid java name */
    public static void m1601$$Nest$mcheckConnectedChargerStatus(PowerUI powerUI, int i) {
        if (2 == powerUI.mBatteryStatus) {
            int i2 = powerUI.mPlugType;
            if (1 == i2 || 2 == i2) {
                if (PowerUiRune.SPECIFIC_POWER_REQUEST_BY_VZW) {
                    if (powerUI.mBatterySlowCharger) {
                        powerUI.mBatteryChargingType = 8;
                    } else if (powerUI.mFullyConnected) {
                        int i3 = powerUI.mSuperFastCharger;
                        if (i3 == 3) {
                            powerUI.mBatteryChargingType = 3;
                        } else if (i3 == 4) {
                            powerUI.mBatteryChargingType = 4;
                        } else if (powerUI.mBatteryHighVoltageCharger) {
                            powerUI.mBatteryChargingType = 2;
                        } else {
                            powerUI.mBatteryChargingType = 1;
                        }
                    } else {
                        powerUI.mBatteryChargingType = 9;
                    }
                } else if (!powerUI.mFullyConnected) {
                    powerUI.mBatteryChargingType = 9;
                } else if (powerUI.mBatterySlowCharger) {
                    powerUI.mBatteryChargingType = 8;
                } else {
                    int i4 = powerUI.mSuperFastCharger;
                    if (i4 == 3) {
                        powerUI.mBatteryChargingType = 3;
                    } else if (i4 == 4) {
                        powerUI.mBatteryChargingType = 4;
                    } else if (powerUI.mBatteryHighVoltageCharger) {
                        powerUI.mBatteryChargingType = 2;
                    } else {
                        powerUI.mBatteryChargingType = 1;
                    }
                }
            } else if (4 != i2) {
                powerUI.mBatteryChargingType = 0;
            } else if (100 == powerUI.mBatteryOnline) {
                powerUI.mBatteryChargingType = 7;
            } else {
                powerUI.mBatteryChargingType = 6;
            }
            if (i2 == i && powerUI.mIsChangedStringAfterCharging) {
                int i5 = powerUI.mBatteryChargingType;
                switch (i5) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        i5 = 11;
                        break;
                    case 6:
                    case 7:
                        i5 = 10;
                        break;
                }
                powerUI.mBatteryChargingType = i5;
            }
            if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION && powerUI.mIsAfterAdaptiveProtection) {
                powerUI.mBatteryChargingType = 12;
            }
        } else {
            powerUI.mBatteryChargingType = 0;
        }
        int i6 = powerUI.mBatteryChargingType;
        int i7 = powerUI.mSuperFastCharger;
        SecPowerNotificationWarnings secPowerNotificationWarnings = powerUI.mSecPowerNotificationWarnings;
        secPowerNotificationWarnings.mOldChargingType = secPowerNotificationWarnings.mChargingType;
        secPowerNotificationWarnings.mChargingType = i6;
        secPowerNotificationWarnings.mSuperFastCharger = i7;
    }

    /* renamed from: -$$Nest$mcheckFullBatteryStatus, reason: not valid java name */
    public static void m1602$$Nest$mcheckFullBatteryStatus(PowerUI powerUI, int i) {
        int i2 = powerUI.mBatteryStatus;
        if (i != i2) {
            if (5 != i2 || i == 2) {
                if (!(powerUI.mBatteryLevel >= powerUI.mBatteryProtectionThreshold && powerUI.mIsProtectingBatteryCutOffSettingEnabled)) {
                    return;
                }
            }
            if (powerUI.mPowerManager.isInteractive()) {
                return;
            }
            Lazy lazy = powerUI.mPluginAODManagerLazy;
            ((PluginAODManager) lazy.get()).chargingAnimStarted(true);
            ((PluginAODManager) lazy.get()).chargingAnimStarted(false);
        }
    }

    /* renamed from: -$$Nest$mcheckHVchargerEnableConnection, reason: not valid java name */
    public static void m1603$$Nest$mcheckHVchargerEnableConnection(PowerUI powerUI, int i) {
        int i2 = powerUI.mBatteryCurrentEvent & QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY;
        SecPowerNotificationWarnings secPowerNotificationWarnings = powerUI.mSecPowerNotificationWarnings;
        if (i2 == 0 || (i & QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY) != 0) {
            if ((i & QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY) == 0 || i2 != 0) {
                return;
            }
            secPowerNotificationWarnings.getClass();
            Log.d("SecPowerUI.Notification", "dismissHVchargerEnableAlertDialog()");
            AlertDialog alertDialog = secPowerNotificationWarnings.mHVchargerEnablePopupDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
                Log.d("SecPowerUI.Notification", "afcDisableChargerDialog is dimissed");
                return;
            }
            return;
        }
        if (PowerUtils.isViewCoverClosed()) {
            return;
        }
        secPowerNotificationWarnings.getClass();
        Log.d("SecPowerUI.Notification", "showHVchargerEnableAlertDialog()");
        if (secPowerNotificationWarnings.mHVchargerEnablePopupDialog == null) {
            AlertDialog popupDialog = secPowerNotificationWarnings.getPopupDialog(14);
            secPowerNotificationWarnings.mHVchargerEnablePopupDialog = popupDialog;
            if (popupDialog == null) {
                return;
            }
            popupDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.22
                public DialogInterfaceOnDismissListenerC196222() {
                }

                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    SecPowerNotificationWarnings.this.mHVchargerEnablePopupDialog = null;
                }
            });
            secPowerNotificationWarnings.turnOnScreen();
            secPowerNotificationWarnings.mHVchargerEnablePopupDialog.show();
        }
    }

    /* renamed from: -$$Nest$mcheckIncompatibleChargerConnection, reason: not valid java name */
    public static void m1604$$Nest$mcheckIncompatibleChargerConnection(PowerUI powerUI, int i) {
        int i2 = powerUI.mBatteryOnline;
        SecPowerNotificationWarnings secPowerNotificationWarnings = powerUI.mSecPowerNotificationWarnings;
        if (i2 == 0 && i != 0) {
            secPowerNotificationWarnings.showIncompatibleChargerNotice();
            return;
        }
        if (i != 0 || i2 == 0) {
            return;
        }
        AlertDialog alertDialog = secPowerNotificationWarnings.mIncompatibleChargerDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        Log.d("SecPowerUI.Notification", "dismissing incompatible charger notification");
        secPowerNotificationWarnings.cancelNotification(3);
    }

    /* renamed from: -$$Nest$mcheckTipsNotification, reason: not valid java name */
    public static void m1605$$Nest$mcheckTipsNotification(PowerUI powerUI, int i) {
        int i2;
        if (powerUI.mPlugType != 0 || i <= 30 || powerUI.mBatteryLevel > 30) {
            return;
        }
        Context context = powerUI.mContext;
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.android.systemui.power_tips_notification", 0);
        if (sharedPreferences != null) {
            boolean z = sharedPreferences.getBoolean("tipsNotiConfirmed", false);
            Slog.d("PowerUI", "checkTipsNotificaiton confirmed : " + z);
            if (z) {
                Slog.e("PowerUI", "User confirmed, so we do not register tip notification!!");
                return;
            }
            int i3 = sharedPreferences.getInt("tipsNotiRegisteredCount", 0);
            int i4 = Settings.Secure.getInt(context.getContentResolver(), "refresh_rate_mode", 0);
            Slog.d("PowerUI", "checkTipsNotificaiton notiCount : " + i3 + "  refreshRate : " + i4);
            if (i3 >= 3 || i4 <= 0) {
                Slog.e("PowerUI", SeslRecyclerViewFastScroller$$ExternalSyntheticOutline0.m47m("Noti count = ", i3, " refresh rate settings = ", i4, " , so we do not register tip notification!!"));
                return;
            }
            long j = sharedPreferences.getLong("tipsNotiLastTime", 0L);
            long currentTimeMillis = System.currentTimeMillis();
            StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("lastNotifiedTime = ", j, "  currentTime = ");
            m17m.append(currentTimeMillis);
            Slog.d("PowerUI", m17m.toString());
            if (currentTimeMillis - j <= 604800000) {
                Slog.e("PowerUI", "last tip notification has been registered within 1 week, so we ignore this case!!");
                return;
            }
            boolean z2 = sharedPreferences.getBoolean("ignoreRUT", false);
            Slog.d("PowerUI", "ignore_rut = " + z2);
            if (z2) {
                powerUI.showTipsNotification();
                return;
            }
            if (Settings.Global.getInt(context.getContentResolver(), "low_power", 0) == 1) {
                i2 = -1;
            } else {
                long batteryRemainingUsageTime = SemBatteryUtils.getBatteryRemainingUsageTime(context, 39);
                if (batteryRemainingUsageTime < 0) {
                    batteryRemainingUsageTime = SemBatteryUtils.getBatteryRemainingUsageTime(context, 5);
                }
                i2 = (int) batteryRemainingUsageTime;
            }
            Slog.d("PowerUI", "remaining time : " + i2);
            if (i2 <= 0 || i2 >= 780) {
                return;
            }
            powerUI.showTipsNotification();
        }
    }

    /* renamed from: -$$Nest$mcheckTurnOffPsmNotification, reason: not valid java name */
    public static void m1606$$Nest$mcheckTurnOffPsmNotification(PowerUI powerUI, int i) {
        int i2 = powerUI.mTurnOffPsmLevel;
        Context context = powerUI.mContext;
        if (i2 == -1) {
            final ContentResolver contentResolver = context.getContentResolver();
            powerUI.mTurnOffPsmLevel = Settings.Global.getInt(contentResolver, "turn_off_psm_trigger_level", 50);
            contentResolver.registerContentObserver(Settings.Global.getUriFor("turn_off_psm_trigger_level"), false, new ContentObserver(powerUI.mHandler) { // from class: com.android.systemui.power.PowerUI.15
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    PowerUI.this.mTurnOffPsmLevel = Settings.Global.getInt(contentResolver, "turn_off_psm_trigger_level", 50);
                }
            }, -1);
        }
        int i3 = powerUI.mTurnOffPsmLevel;
        if (i >= i3 || powerUI.mBatteryLevel < i3 || Settings.Global.getInt(context.getContentResolver(), "low_power", 0) != 1) {
            return;
        }
        Intent intent = new Intent("com.samsung.android.sm.ACTION_TURN_OFF_PSM_NOTI");
        intent.setComponent(TipsConfig.TURN_OFF_PSM_COMPONENT_NAME);
        context.sendBroadcast(intent);
    }

    /* renamed from: -$$Nest$mcheckTurnOnProtectBatteryByLongTa, reason: not valid java name */
    public static void m1607$$Nest$mcheckTurnOnProtectBatteryByLongTa(PowerUI powerUI) {
        if (powerUI.mPlugType != 0 && !powerUI.mIsProtectingBatteryCutOffSettingEnabled) {
            powerUI.startScheduling();
            return;
        }
        Context context = powerUI.mContext;
        boolean z = context.getSharedPreferences("com.android.systemui.power_auto_on_protect_battery", 0).getBoolean("auto_on_protect_battery_timer_started", false);
        int i = Settings.Global.getInt(context.getContentResolver(), "auto_on_protect_battery", -1);
        Log.i("PowerUI", "checkShouldTurnOffProtectBattery : " + i + ", Scheduling start ? : " + z);
        if (!z && powerUI.mPlugType == 0 && i == 1) {
            PowerUtils.sendIntentToDc(context, "com.samsung.android.sm.action.TURN_OFF_PROTECT_BATTERY_BY_LONG_TERM_TA");
        }
        powerUI.clearScheduling();
    }

    /* renamed from: -$$Nest$mcheckTurnOnProtectBatteryByLongTermCharge, reason: not valid java name */
    public static void m1608$$Nest$mcheckTurnOnProtectBatteryByLongTermCharge(PowerUI powerUI) {
        if (powerUI.mBatteryLevel >= powerUI.mLtcHighSocThreshold && powerUI.mProtectBatteryValue != 2) {
            powerUI.startScheduling();
            return;
        }
        Context context = powerUI.mContext;
        boolean z = context.getSharedPreferences("com.android.systemui.power_auto_on_protect_battery", 0).getBoolean("auto_on_protect_battery_timer_started", false);
        Log.i("PowerUI", "checkRestoreProtectBattery pb value : " + powerUI.mProtectBatteryValue + ", Scheduling start ? : " + z);
        if (!z && powerUI.mBatteryLevel < powerUI.mLtcReleaseThreshold && powerUI.mProtectBatteryValue == 2) {
            PowerUtils.sendIntentToDc(context, "com.samsung.android.sm.action.TURN_OFF_PROTECT_BATTERY_BY_LONG_TERM_TA");
        } else if (!z && powerUI.mBatteryLevel < powerUI.mLtcHighSocThreshold && Settings.Global.getInt(context.getContentResolver(), "key_ltc_state", 0) == 1) {
            PowerUtils.sendIntentToDc(context, "com.samsung.android.sm.action.TURN_OFF_SOFT_NOTI_BY_LONG_TERM_TA");
        }
        powerUI.clearScheduling();
    }

    /* renamed from: -$$Nest$mcheckWirelessMisalign, reason: not valid java name */
    public static void m1609$$Nest$mcheckWirelessMisalign(PowerUI powerUI, int i) {
        powerUI.getClass();
        boolean z = (i & QuickStepContract.SYSUI_STATE_BACK_DISABLED) == 4194304;
        boolean z2 = (powerUI.mBatteryMiscEvent & QuickStepContract.SYSUI_STATE_BACK_DISABLED) == 4194304;
        Log.i("PowerUI", "oldMisalign : " + z + ", curMisalign : " + z2);
        if (powerUI.mPlugType <= 0) {
            powerUI.removeMisalignView();
            return;
        }
        if (!z && z2) {
            Log.i("PowerUI", "old not misalign but now misalign");
            if (PowerUtils.isViewCoverClosed()) {
                return;
            }
            powerUI.removeChargerView();
            powerUI.removeMisalignView();
            powerUI.setWirelessMisalignView(0);
            powerUI.mIsWirelessMisalignTask = true;
            powerUI.mHandler.postDelayed(powerUI.mWirelessMisalignTimeoutTask, 30000L);
            return;
        }
        if (!z || z2) {
            if (z && z2) {
                return;
            }
            powerUI.removeMisalignView();
            return;
        }
        Log.i("PowerUI", "old misalign but now not misalign");
        powerUI.removeChargerView();
        powerUI.removeMisalignView();
        powerUI.setWirelessMisalignView(1);
    }

    /* JADX WARN: Removed duplicated region for block: B:140:0x01e1  */
    /* JADX WARN: Removed duplicated region for block: B:161:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01d1  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x029a  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x00b0  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:82:0x02ff -> B:78:0x0306). Please report as a decompilation issue!!! */
    /* renamed from: -$$Nest$mplayChargerConnectionAnimation, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m1610$$Nest$mplayChargerConnectionAnimation(int i, int i2, PowerUI powerUI, boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        final ChargerAnimationView chargerAnimationView;
        ObjectAnimator duration;
        if (powerUI.mIsChargerAnimationPlaying) {
            Log.w("PowerUI", "Skip charging animation - already playing");
        } else {
            if (powerUI.mBatteryLevel >= powerUI.mBatteryProtectionThreshold && powerUI.mIsProtectingBatteryCutOffSettingEnabled) {
                Log.w("PowerUI", "Skip charging animation - by protect battery cutoff");
            } else if (powerUI.skipAnimByPlugStatus(i, i2, z)) {
                Log.w("PowerUI", "Skip charging animation - by plug status");
            } else {
                boolean isCoverClosed = ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).isCoverClosed();
                CoverState coverState = ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).getCoverState();
                int type = coverState != null ? coverState.getType() : 2;
                if (!isCoverClosed || type == 8) {
                    z2 = false;
                } else {
                    IconCompat$$ExternalSyntheticOutline0.m30m("View Cover is covered and closed, so don't play charging animation but turn on AOD, cover type : ", type, "PowerUI");
                    if ((type == 15 || type == 16 || type == 17) && !powerUI.mPowerManager.isInteractive()) {
                        Log.w("PowerUI", "Supported view cover && cover closed, so we should call PluginAODManager");
                        Lazy lazy = powerUI.mPluginAODManagerLazy;
                        ((PluginAODManager) lazy.get()).chargingAnimStarted(true);
                        ((PluginAODManager) lazy.get()).chargingAnimStarted(false);
                    }
                    z2 = true;
                }
                if (z2) {
                    Log.w("PowerUI", "Skip charging animation - by cover state");
                } else if (powerUI.skipAnimByMotionDetected()) {
                    Log.w("PowerUI", "Skip charging animation - by motion detected");
                } else {
                    if (!powerUI.mIsAfterAdaptiveProtection) {
                        z3 = true;
                        if (z3) {
                            return;
                        }
                        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("Charger connected, charger : "), powerUI.mSuperFastCharger, "PowerUI");
                        if (powerUI.mChargerAnimationWindowLp == null) {
                            WindowManager.LayoutParams layoutParam = getLayoutParam("PowerUI.ChargerAnimationViewLp");
                            powerUI.mChargerAnimationWindowLp = layoutParam;
                            layoutParam.type = 2021;
                            layoutParam.flags = layoutParam.flags | 8 | 16;
                        }
                        boolean isFolded = SemWindowManager.getInstance().isFolded();
                        if (powerUI.mChargerAnimationWindowManager == null) {
                            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("folder state : ", isFolded, "PowerUI");
                            if (BasicRune.BASIC_FOLDABLE_TYPE_FLIP && isFolded) {
                                Context subDisplayContext = PowerUtils.getSubDisplayContext(powerUI.mContext);
                                if (PowerUiRune.COVER_DISPLAY_LARGE_SCREEN) {
                                    powerUI.mChargerAnimationWindowLp.layoutInDisplayCutoutMode = 3;
                                }
                                powerUI.mChargerAnimationWindowManager = (WindowManager) subDisplayContext.getSystemService("window");
                                if (SystemProperties.get("ro.product.vendor.name").toLowerCase().contains("bloom")) {
                                    powerUI.mChargerAnimationView = (ChargerAnimationView) View.inflate(subDisplayContext, R.layout.battery_charger_animation_bloom, null);
                                } else {
                                    powerUI.mChargerAnimationView = (ChargerAnimationView) View.inflate(subDisplayContext, R.layout.battery_charger_animation, null);
                                }
                            } else {
                                powerUI.mChargerAnimationWindowManager = (WindowManager) powerUI.mContext.getSystemService("window");
                                powerUI.mChargerAnimationView = (ChargerAnimationView) View.inflate(powerUI.mContext, R.layout.battery_charger_animation, null);
                            }
                        }
                        powerUI.mChargerAnimationWindowManager.addView(powerUI.mChargerAnimationView, powerUI.mChargerAnimationWindowLp);
                        ChargerAnimationView chargerAnimationView2 = powerUI.mChargerAnimationView;
                        chargerAnimationView2.getClass();
                        chargerAnimationView2.mAnimationPlaying = false;
                        chargerAnimationView2.setVisibility(8);
                        ChargerAnimationView chargerAnimationView3 = powerUI.mChargerAnimationView;
                        chargerAnimationView3.mAnimationListener = powerUI;
                        boolean isFolded2 = SemWindowManager.getInstance().isFolded();
                        boolean isInteractive = chargerAnimationView3.mPowerManager.isInteractive();
                        boolean z5 = BasicRune.BASIC_FOLDABLE_TYPE_FLIP;
                        chargerAnimationView3.mIsSubscreenOff = (z5 && isFolded2) && ((DisplayManager) chargerAnimationView3.mContext.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN")[1].getState() == 1;
                        int i3 = ((StatusBarStateControllerImpl) ((SysuiStatusBarStateController) Dependency.get(StatusBarStateController.class))).mState;
                        if (!(i3 == 1 || i3 == 2)) {
                            if (!(z5 && isFolded2)) {
                                z4 = false;
                                chargerAnimationView3.mNeedFullScreenBlur = z4;
                                if (isInteractive || chargerAnimationView3.mPowerManager.isScreenCurtainEnabled()) {
                                    chargerAnimationView3.mStartedInDoze = true;
                                }
                                if (!chargerAnimationView3.mIsSubscreenOff) {
                                    chargerAnimationView3.mBackGroundView.setBackgroundColor(chargerAnimationView3.mContext.getColor(R.color.charger_anim_normal_bg_color_lock_screen_off));
                                } else if (chargerAnimationView3.mStartedInDoze) {
                                    chargerAnimationView3.mBackGroundView.setBackgroundColor(chargerAnimationView3.mContext.getColor(R.color.charger_anim_normal_bg_color_lock));
                                } else if (PowerUiRune.WINDOW_BLUR_SUPPORTED && PowerUiRune.GPU_BLUR_SUPPORTED) {
                                    SemBlurInfo.Builder colorCurvePreset = new SemBlurInfo.Builder(0).setColorCurvePreset(11);
                                    if (chargerAnimationView3.mNeedFullScreenBlur) {
                                        chargerAnimationView3.mBackGroundView.setBackgroundColor(chargerAnimationView3.mContext.getColor(R.color.charger_anim_blur_bg_color_lock));
                                        colorCurvePreset.setBackgroundColor(chargerAnimationView3.mContext.getColor(R.color.charger_anim_blur_bg_color_lock));
                                        chargerAnimationView3.mCircleBackgroundView.semSetBlurInfo(null);
                                        chargerAnimationView3.mBackGroundView.semSetBlurInfo(colorCurvePreset.build());
                                    } else {
                                        colorCurvePreset.setBackgroundColor(chargerAnimationView3.mContext.getColor(R.color.charger_anim_blur_bg_color));
                                        chargerAnimationView3.mBackGroundView.semSetBlurInfo(null);
                                        chargerAnimationView3.mCircleBackgroundView.semSetBlurInfo(colorCurvePreset.setBackgroundCornerRadius(chargerAnimationView3.mContext.getResources().getDimension(R.dimen.charger_anim_blur_corner_radius)).build());
                                    }
                                } else if (chargerAnimationView3.mNeedFullScreenBlur) {
                                    chargerAnimationView3.mBackGroundView.setBackgroundColor(chargerAnimationView3.mContext.getColor(R.color.charger_anim_normal_bg_color_lock));
                                } else {
                                    chargerAnimationView3.mCircleBackgroundView.setBackgroundResource(R.drawable.charging_app_screen_background);
                                }
                                powerUI.mChargerAnimationView.mPluginAODManager = (PluginAODManager) powerUI.mPluginAODManagerLazy.get();
                                chargerAnimationView = powerUI.mChargerAnimationView;
                                int i4 = powerUI.mBatteryLevel;
                                chargerAnimationView.mSuperFastChargingType = powerUI.mSuperFastCharger;
                                chargerAnimationView.mCurrentBatteryLevel = i4;
                                if (chargerAnimationView.mAnimationPlaying) {
                                    chargerAnimationView.mAnimationPlaying = true;
                                    chargerAnimationView.setVisibility(0);
                                    if (chargerAnimationView.mStartedInDoze) {
                                        Log.i("PowerUI.ChargerAnimationView", "startChargerAnimation : Lcd OFF -> so call chargingAnimStarted(true)");
                                        chargerAnimationView.mPluginAODManager.chargingAnimStarted(true);
                                        synchronized (chargerAnimationView) {
                                            if (chargerAnimationView.mDozeChargingPartialWakelock == null) {
                                                PowerManager.WakeLock newWakeLock = chargerAnimationView.mPowerManager.newWakeLock(128, "PowerUI.ChargerAnimationView");
                                                chargerAnimationView.mDozeChargingPartialWakelock = newWakeLock;
                                                newWakeLock.acquire(4000L);
                                            }
                                        }
                                        if (chargerAnimationView.mDisplayManager == null) {
                                            chargerAnimationView.mDisplayManager = IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
                                        }
                                        try {
                                            Log.i("PowerUI.ChargerAnimationView", "startChargerAnimation : setDisplayStateLimit(Display.STATE_ON)");
                                            IDisplayManager iDisplayManager = chargerAnimationView.mDisplayManager;
                                            if (iDisplayManager != null) {
                                                iDisplayManager.setDisplayStateLimit(chargerAnimationView.mDisplayStateLock, 2);
                                                chargerAnimationView.mRefreshRateToken = chargerAnimationView.mDisplayManager.acquirePassiveModeToken(chargerAnimationView.mToken, "PowerUI");
                                            } else {
                                                Log.e("PowerUI.ChargerAnimationView", "startChargerAnimation : mDisplayManager is null!! ERROR case");
                                            }
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    int i5 = chargerAnimationView.mSuperFastChargingType;
                                    String str = i5 != 3 ? i5 != 4 ? i5 != 5 ? "charging_l1" : "charging_l4" : "charging_l3" : "charging_l2";
                                    int i6 = ((StatusBarStateControllerImpl) ((SysuiStatusBarStateController) Dependency.get(StatusBarStateController.class))).mState;
                                    if (i6 == 1 || i6 == 2) {
                                        str = str.concat("_lock");
                                    }
                                    String m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, ".json");
                                    KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m70m("Animation applied : ", m14m, "PowerUI.ChargerAnimationView");
                                    chargerAnimationView.mChargerAnimationView.setAnimation(m14m);
                                    if (chargerAnimationView.mIsSubscreenOff) {
                                        chargerAnimationView.mFadeInAnimation = ObjectAnimator.ofFloat(chargerAnimationView.mChargerContainer, "alpha", 0.0f, 1.0f).setDuration(chargerAnimationView.mNeedFullScreenBlur ? 400L : 200L);
                                        duration = ObjectAnimator.ofFloat(chargerAnimationView.mChargerContainer, "alpha", 1.0f, 0.0f).setDuration(chargerAnimationView.mNeedFullScreenBlur ? 400L : 100L);
                                    } else {
                                        chargerAnimationView.mFadeInAnimation = ObjectAnimator.ofFloat(chargerAnimationView, "alpha", 0.0f, 1.0f).setDuration(chargerAnimationView.mNeedFullScreenBlur ? 400L : 200L);
                                        duration = ObjectAnimator.ofFloat(chargerAnimationView, "alpha", 1.0f, 0.0f).setDuration(chargerAnimationView.mNeedFullScreenBlur ? 400L : 100L);
                                    }
                                    duration.setStartDelay(chargerAnimationView.mNeedFullScreenBlur ? 1600L : 1350L);
                                    duration.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.power.ChargerAnimationView.1
                                        @Override // android.animation.Animator.AnimatorListener
                                        public final void onAnimationCancel(Animator animator) {
                                            Log.i("PowerUI.ChargerAnimationView", "Animation Cancel");
                                            ChargerAnimationView chargerAnimationView4 = ChargerAnimationView.this;
                                            if (chargerAnimationView4.mStartedInDoze) {
                                                ChargerAnimationView.m1596$$Nest$mreleaseDisplayStateLimit(chargerAnimationView4);
                                                ChargerAnimationView chargerAnimationView5 = ChargerAnimationView.this;
                                                synchronized (chargerAnimationView5) {
                                                    PowerManager.WakeLock wakeLock = chargerAnimationView5.mDozeChargingPartialWakelock;
                                                    if (wakeLock != null && wakeLock.isHeld()) {
                                                        chargerAnimationView5.mDozeChargingPartialWakelock.release();
                                                        chargerAnimationView5.mDozeChargingPartialWakelock = null;
                                                    }
                                                }
                                                Log.i("PowerUI.ChargerAnimationView", "setChargerAnimation onAnimationCancel : Now the AOD plugin called true, so call chargingAnimStarted(false)");
                                                ChargerAnimationView chargerAnimationView6 = ChargerAnimationView.this;
                                                chargerAnimationView6.mStartedInDoze = false;
                                                chargerAnimationView6.mPluginAODManager.chargingAnimStarted(false);
                                            }
                                        }

                                        @Override // android.animation.Animator.AnimatorListener
                                        public final void onAnimationEnd(Animator animator) {
                                            Log.i("PowerUI.ChargerAnimationView", "Animation Ended");
                                            ChargerAnimationView chargerAnimationView4 = ChargerAnimationView.this;
                                            chargerAnimationView4.getClass();
                                            chargerAnimationView4.mAnimationPlaying = false;
                                            chargerAnimationView4.setVisibility(8);
                                            PowerUI powerUI2 = (PowerUI) ChargerAnimationView.this.mAnimationListener;
                                            powerUI2.getClass();
                                            Log.i("PowerUI", "onChargerAnimationEnd");
                                            powerUI2.removeChargerView();
                                            if (powerUI2.mBatteryChargingType != 0 && powerUI2.mBatterySwellingMode != 1) {
                                                powerUI2.mSecPowerNotificationWarnings.showChargingNotice();
                                            }
                                            ChargerAnimationView chargerAnimationView5 = ChargerAnimationView.this;
                                            if (chargerAnimationView5.mStartedInDoze) {
                                                ChargerAnimationView.m1596$$Nest$mreleaseDisplayStateLimit(chargerAnimationView5);
                                                ChargerAnimationView chargerAnimationView6 = ChargerAnimationView.this;
                                                synchronized (chargerAnimationView6) {
                                                    PowerManager.WakeLock wakeLock = chargerAnimationView6.mDozeChargingPartialWakelock;
                                                    if (wakeLock != null && wakeLock.isHeld()) {
                                                        chargerAnimationView6.mDozeChargingPartialWakelock.release();
                                                        chargerAnimationView6.mDozeChargingPartialWakelock = null;
                                                    }
                                                }
                                                Log.i("PowerUI.ChargerAnimationView", "setChargerAnimation onAnimationEnd : Now the AOD plugin called true, so call chargingAnimStarted(false)");
                                                ChargerAnimationView chargerAnimationView7 = ChargerAnimationView.this;
                                                chargerAnimationView7.mStartedInDoze = false;
                                                chargerAnimationView7.mPluginAODManager.chargingAnimStarted(false);
                                            }
                                        }

                                        @Override // android.animation.Animator.AnimatorListener
                                        public final void onAnimationRepeat(Animator animator) {
                                        }

                                        @Override // android.animation.Animator.AnimatorListener
                                        public final void onAnimationStart(Animator animator) {
                                        }
                                    });
                                    chargerAnimationView.mAlphaAnimatorSet.play(duration).after(chargerAnimationView.mFadeInAnimation);
                                    if (chargerAnimationView.mNeedFullScreenBlur) {
                                        chargerAnimationView.mBackGroundView.setVisibility(0);
                                        chargerAnimationView.mCircleBackgroundView.setVisibility(8);
                                    } else {
                                        chargerAnimationView.mBackGroundView.setVisibility(8);
                                        chargerAnimationView.mCircleBackgroundView.setVisibility(0);
                                    }
                                    ImageView imageView = chargerAnimationView.mChargingIconView;
                                    Context context = chargerAnimationView.mContext;
                                    int i7 = chargerAnimationView.mSuperFastChargingType;
                                    imageView.setImageDrawable(context.getDrawable((i7 == 3 || i7 == 4 || i7 == 5) ? R.drawable.ic_icon_superfast : R.drawable.ic_icon_charging));
                                    PathInterpolator pathInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
                                    PathInterpolator pathInterpolator2 = new PathInterpolator(0.33f, 0.0f, 0.4f, 1.0f);
                                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(chargerAnimationView.mChargingIconView, "scaleX", 0.5f, 1.05f);
                                    ofFloat.setDuration(233L);
                                    ofFloat.setStartDelay(chargerAnimationView.mNeedFullScreenBlur ? 100L : 0L);
                                    ofFloat.setInterpolator(pathInterpolator2);
                                    ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(chargerAnimationView.mChargingIconView, "scaleY", 0.5f, 1.05f);
                                    ofFloat2.setDuration(233L);
                                    ofFloat2.setInterpolator(pathInterpolator2);
                                    ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(chargerAnimationView.mChargingIconView, "scaleX", 1.05f, 0.96f);
                                    ofFloat3.setDuration(267L);
                                    ofFloat3.setInterpolator(pathInterpolator);
                                    ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(chargerAnimationView.mChargingIconView, "scaleY", 1.05f, 0.96f);
                                    ofFloat4.setDuration(267L);
                                    ofFloat4.setInterpolator(pathInterpolator);
                                    ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(chargerAnimationView.mChargingIconView, "scaleX", 0.96f, 1.0f);
                                    ofFloat5.setDuration(183L);
                                    ofFloat5.setInterpolator(pathInterpolator2);
                                    ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(chargerAnimationView.mChargingIconView, "scaleY", 0.96f, 1.0f);
                                    ofFloat6.setDuration(183L);
                                    ofFloat6.setInterpolator(pathInterpolator2);
                                    chargerAnimationView.mAlphaAnimatorSet.play(chargerAnimationView.mFadeInAnimation).with(ofFloat);
                                    chargerAnimationView.mAlphaAnimatorSet.play(ofFloat).with(ofFloat2).before(ofFloat3);
                                    chargerAnimationView.mAlphaAnimatorSet.play(ofFloat3).with(ofFloat4).before(ofFloat5);
                                    chargerAnimationView.mAlphaAnimatorSet.play(ofFloat5).with(ofFloat6);
                                    chargerAnimationView.mChargerAnimationView.playAnimation();
                                    chargerAnimationView.mAlphaAnimatorSet.start();
                                    chargerAnimationView.setBatteryLevelText();
                                    Log.d("PowerUI.ChargerAnimationView", "Animation Started");
                                } else {
                                    Log.d("PowerUI.ChargerAnimationView", "Animation is playing, return");
                                }
                                powerUI.mIsChargerAnimationPlaying = true;
                                if (powerUI.mIsMotionDetectionSupported || powerUI.mIsDeviceMoving || 4 == powerUI.mPlugType) {
                                    return;
                                }
                                Log.i("PowerUI", "Current charging plug is not wireless but mIsDeviceMovign is not still released. We should release mIsDeviceMoving to true !!");
                                powerUI.mIsDeviceMoving = true;
                                return;
                            }
                        }
                        z4 = true;
                        chargerAnimationView3.mNeedFullScreenBlur = z4;
                        if (isInteractive) {
                        }
                        chargerAnimationView3.mStartedInDoze = true;
                        if (!chargerAnimationView3.mIsSubscreenOff) {
                        }
                        powerUI.mChargerAnimationView.mPluginAODManager = (PluginAODManager) powerUI.mPluginAODManagerLazy.get();
                        chargerAnimationView = powerUI.mChargerAnimationView;
                        int i42 = powerUI.mBatteryLevel;
                        chargerAnimationView.mSuperFastChargingType = powerUI.mSuperFastCharger;
                        chargerAnimationView.mCurrentBatteryLevel = i42;
                        if (chargerAnimationView.mAnimationPlaying) {
                        }
                        powerUI.mIsChargerAnimationPlaying = true;
                        if (powerUI.mIsMotionDetectionSupported) {
                            return;
                        } else {
                            return;
                        }
                    }
                    Log.w("PowerUI", "Skip charging animation - After adaptive protection");
                }
            }
        }
        z3 = false;
        if (z3) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /* renamed from: -$$Nest$mplayChargerConnectionSound, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m1611$$Nest$mplayChargerConnectionSound(int i, int i2, PowerUI powerUI, boolean z) {
        boolean z2;
        powerUI.getClass();
        StringBuilder sb = new StringBuilder("priorPlugType = ");
        sb.append(i);
        sb.append(" mPlugType = ");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, powerUI.mPlugType, " priorBatteryStatus = ", i2, " mBatteryStatus = ");
        RecyclerView$$ExternalSyntheticOutline0.m46m(sb, powerUI.mBatteryStatus, "PowerUI");
        if (powerUI.mBatteryLevel >= powerUI.mBatteryProtectionThreshold && powerUI.mIsProtectingBatteryCutOffSettingEnabled) {
            Log.w("PowerUI", "Skip charging sound - by protect battery cutoff");
        } else if (powerUI.skipAnimByPlugStatus(i, i2, z)) {
            Log.w("PowerUI", "Skip charging sound - by plug status");
        } else if (powerUI.skipAnimByMotionDetected()) {
            Log.w("PowerUI", "Skip charging sound - by motion detected");
        } else {
            if (!powerUI.mIsAfterAdaptiveProtection) {
                z2 = true;
                if (z2) {
                    return;
                }
                boolean z3 = powerUI.mIsRunningStopPowerSoundTask;
                Handler handler = powerUI.mHandler;
                RunnableC193712 runnableC193712 = powerUI.mStopPowerSoundTask;
                if (z3) {
                    handler.removeCallbacks(runnableC193712);
                    powerUI.mIsRunningStopPowerSoundTask = false;
                }
                powerUI.mSecPowerNotificationWarnings.playPowerSound(1);
                handler.postDelayed(runnableC193712, 3000L);
                powerUI.mIsRunningStopPowerSoundTask = true;
                return;
            }
            Log.w("PowerUI", "Skip charging sound - After adaptive protection");
        }
        z2 = false;
        if (z2) {
        }
    }

    /* renamed from: -$$Nest$msendLowBatteryDumpIfNeeded, reason: not valid java name */
    public static void m1612$$Nest$msendLowBatteryDumpIfNeeded(PowerUI powerUI, int i, int i2, int i3) {
        Context context = powerUI.mContext;
        if (1 == Settings.System.getInt(context.getContentResolver(), "LOW_BATTERY_DUMP", 0)) {
            int i4 = powerUI.mBatteryLevel;
            if ((((i - i4 >= 10 || i4 - i >= 10) && -1 != i) || (i3 < i2 && -2 == i3)) && powerUI.mBootCompleted) {
                Log.d("PowerUI", "Low battery dump");
                Intent intent = new Intent("com.samsung.systemui.power.action.LOW_BATTERY_DUMP");
                intent.addFlags(16777216);
                context.sendBroadcast(intent);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:70:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* renamed from: -$$Nest$mshowChargingNotice, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m1613$$Nest$mshowChargingNotice(PowerUI powerUI, int i, int i2, int i3) {
        String str;
        int i4;
        int i5 = powerUI.mBatteryStatus;
        RunnableC193611 runnableC193611 = powerUI.mAfterChargingNoticeTask;
        Handler handler = powerUI.mHandler;
        C193813 c193813 = powerUI.mSContextListener;
        SecPowerNotificationWarnings secPowerNotificationWarnings = powerUI.mSecPowerNotificationWarnings;
        boolean z = true;
        if (2 == i5) {
            int i6 = powerUI.mPlugType;
            if (1 == i6 || 2 == i6) {
                if (4 == i) {
                    z = true;
                    secPowerNotificationWarnings.showChargingTypeSwitchedNotice(true);
                    if (powerUI.mBatteryChargingType != 0 && powerUI.mBatterySwellingMode != z) {
                        secPowerNotificationWarnings.showChargingNotice();
                        if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION) {
                            if (powerUI.mIsChangedStringAfterCharging) {
                                handler.removeCallbacks(runnableC193611);
                            }
                            handler.postDelayed(runnableC193611, 5000L);
                        } else {
                            if (powerUI.mIsChangedStringAfterCharging || powerUI.mIsAfterAdaptiveProtection) {
                                handler.removeCallbacks(runnableC193611);
                            }
                            if (!powerUI.mIsAfterAdaptiveProtection) {
                                handler.postDelayed(runnableC193611, 5000L);
                            }
                        }
                    }
                    str = "PowerUI";
                }
                z = true;
                if (powerUI.mBatteryChargingType != 0) {
                    secPowerNotificationWarnings.showChargingNotice();
                    if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION) {
                    }
                }
                str = "PowerUI";
            } else if (4 == i6) {
                if (1 == i || 2 == i) {
                    secPowerNotificationWarnings.showChargingTypeSwitchedNotice(false);
                }
                if (powerUI.mIsMotionDetectionSupported && powerUI.mIsSContextEnabled && !powerUI.mIsSContextListenerRegistered) {
                    Log.d("PowerUI", "Register SContextListener");
                    powerUI.mSContextManager.registerListener(c193813, 46);
                    powerUI.mIsSContextListenerRegistered = true;
                }
                if (!powerUI.mWirelessFodState && 4 != i) {
                    secPowerNotificationWarnings.getClass();
                    Log.d("SecPowerUI.Notification", "dismissWirelessFodAlertDialog");
                    AlertDialog alertDialog = secPowerNotificationWarnings.mWirelessFodAlertDialog;
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                }
                if (powerUI.mBatteryChargingType != 0) {
                }
                str = "PowerUI";
            } else {
                secPowerNotificationWarnings.mDoNotShowChargingNotice = false;
                secPowerNotificationWarnings.mChargingType = 0;
                secPowerNotificationWarnings.mOldChargingType = 0;
                secPowerNotificationWarnings.mChargingTime = 0L;
                secPowerNotificationWarnings.mShowChargingNotice = false;
                secPowerNotificationWarnings.dismissSlowByChargerConnectionInfoPopUp();
                Log.d("SecPowerUI.Notification", "dismissChargingNotification()");
                secPowerNotificationWarnings.cancelNotification(2);
                handler.removeCallbacks(runnableC193611);
                powerUI.mIsChangedStringAfterCharging = false;
                z = true;
                if (powerUI.mBatteryChargingType != 0) {
                }
                str = "PowerUI";
            }
            if (i4 == i && powerUI.mPlugType == 0) {
                if (i2 != i4 || i3 != 3) {
                    z = false;
                }
                if (!z && !powerUI.mIsDeviceMoving) {
                    Log.e(str, "Wireless charger has been disconnected but this is no move case, so we do nothing !!");
                    return;
                }
                if (secPowerNotificationWarnings.mFTAMode) {
                    Log.d("SecPowerUI.Notification", "FTA Mode is ON so don't show Wireless charging disconnect warning");
                    return;
                }
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("showWirelessChargerDisconnectToast() - byHighTemp = ", z, "SecPowerUI.Notification");
                if (z) {
                    boolean isTablet = DeviceType.isTablet();
                    Context context = secPowerNotificationWarnings.mContext;
                    Toast.makeText(context, isTablet ? context.getString(R.string.f772x8049c074) : context.getString(R.string.battery_wireless_charging_disconnect_by_high_temperature_text), 0).show();
                    return;
                }
                return;
            }
            return;
        }
        secPowerNotificationWarnings.mDoNotShowChargingNotice = false;
        secPowerNotificationWarnings.mChargingType = 0;
        secPowerNotificationWarnings.mOldChargingType = 0;
        str = "PowerUI";
        secPowerNotificationWarnings.mChargingTime = 0L;
        secPowerNotificationWarnings.mShowChargingNotice = false;
        secPowerNotificationWarnings.dismissSlowByChargerConnectionInfoPopUp();
        Log.d("SecPowerUI.Notification", "dismissChargingNotification()");
        secPowerNotificationWarnings.cancelNotification(2);
        handler.removeCallbacks(runnableC193611);
        powerUI.mIsChangedStringAfterCharging = false;
        if (powerUI.mIsMotionDetectionSupported && powerUI.mIsSContextListenerRegistered && powerUI.mIsDeviceMoving) {
            i4 = 4;
            if (4 == i) {
                Log.d(str, "Unregister SContextListener - From Check charging type");
                powerUI.mSContextManager.unregisterListener(c193813, 46);
                powerUI.mIsSContextListenerRegistered = false;
            }
            if (i4 == i) {
                return;
            } else {
                return;
            }
        }
        i4 = 4;
        if (i4 == i) {
        }
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.systemui.power.PowerUI$13] */
    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.power.PowerUI$14] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.power.PowerUI$7] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.systemui.power.PowerUI$8] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.systemui.power.PowerUI$9] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.systemui.power.PowerUI$10] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.power.PowerUI$11] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.systemui.power.PowerUI$12] */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.power.PowerUI$1] */
    public PowerUI(Context context, BroadcastDispatcher broadcastDispatcher, CommandQueue commandQueue, Lazy lazy, WarningsUI warningsUI, EnhancedEstimates enhancedEstimates, WakefulnessLifecycle wakefulnessLifecycle, PowerManager powerManager, UserTracker userTracker, Lazy lazy2) {
        this.mContext = context;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mCommandQueue = commandQueue;
        this.mCentralSurfacesOptionalLazy = lazy;
        this.mWarnings = warningsUI;
        this.mEnhancedEstimates = enhancedEstimates;
        this.mPowerManager = powerManager;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mUserTracker = userTracker;
        this.mSecPowerNotificationWarnings = new SecPowerNotificationWarnings(context);
        this.mPluginAODManagerLazy = lazy2;
    }

    public static WindowManager.LayoutParams getLayoutParam(String str) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2, UcmAgentService.ERROR_APDU_CREATION, -3);
        if (LsRune.LOCKUI_AOD_PACKAGE_AVAILABLE) {
            layoutParams.semAddExtensionFlags(262144);
        }
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTrustedOverlay();
        layoutParams.setTitle(str);
        return layoutParams;
    }

    public final void checkAdaptiveProtectionNotification(String str, String str2) {
        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("checkAdaptiveProtectionNotification : ", str, ", lev : ");
        m4m.append(this.mBatteryLevel);
        Slog.i("PowerUI", m4m.toString());
        boolean equalsIgnoreCase = "on".equalsIgnoreCase(str);
        SecPowerNotificationWarnings secPowerNotificationWarnings = this.mSecPowerNotificationWarnings;
        Context context = this.mContext;
        if (equalsIgnoreCase && this.mProtectBatteryValue == 4) {
            if (this.mBatteryLevel != 100) {
                Slog.i("PowerUI", "show AdaptiveProtectionNotification");
                secPowerNotificationWarnings.showAdaptiveProtectionNotification(str2);
            }
            Settings.Global.putInt(context.getContentResolver(), "key_sleep_charging", 1);
            return;
        }
        if ("update".equalsIgnoreCase(str) && this.mProtectBatteryValue == 4) {
            if (this.mBatteryLevel != 100) {
                Slog.i("PowerUI", "update AdaptiveProtectionNotification");
                secPowerNotificationWarnings.showAdaptiveProtectionNotification(str2);
            }
            Settings.Global.putInt(context.getContentResolver(), "key_sleep_charging", 2);
            return;
        }
        if (!"off".equalsIgnoreCase(str)) {
            Slog.i("PowerUI", "dismiss AdaptiveProtectionNotification");
            dismissAdaptiveProtectionNotification();
        } else {
            Slog.i("PowerUI", "off AdaptiveProtectionNotification");
            dismissAdaptiveProtectionNotification();
            this.mIsAfterAdaptiveProtection = true;
        }
    }

    public final void checkBatteryProtectionNotification() {
        int i;
        StringBuilder sb = new StringBuilder("checkBatteryProtectionNotification : ");
        sb.append(this.mBatteryStatus);
        sb.append(", pb value : ");
        sb.append(this.mProtectBatteryValue);
        sb.append(", plugType : ");
        sb.append(this.mPlugType);
        sb.append(", level : ");
        TooltipPopup$$ExternalSyntheticOutline0.m13m(sb, this.mBatteryLevel, "PowerUI");
        Context context = this.mContext;
        boolean isSleepChargingOn = PowerUtils.isSleepChargingOn(context);
        SecPowerNotificationWarnings secPowerNotificationWarnings = this.mSecPowerNotificationWarnings;
        if (isSleepChargingOn || (i = this.mProtectBatteryValue) == 0 || this.mPlugType == 0 || this.mBatteryStatus == 2 || this.mBatteryHealth != 2 || this.mBatteryOverheatLevel != 0 || (this.mBatteryLevel == 100 && (i == 3 || i == 4))) {
            secPowerNotificationWarnings.cancelNotification(9);
            return;
        }
        if (PowerUtils.isSleepChargingOn(context) || this.mBatteryStatus < 4 || this.mBatteryHealth != 2 || this.mBatteryOverheatLevel != 0) {
            return;
        }
        int i2 = this.mProtectBatteryValue;
        if (i2 != 2 && i2 != 1) {
            if (this.mBatteryLevel == 100) {
                return;
            }
            if (i2 != 3 && i2 != 4) {
                return;
            }
        }
        secPowerNotificationWarnings.showNotification(9);
    }

    public final void checkOverheatShutdownHappened() {
        NotificationListener$$ExternalSyntheticOutline0.m123m(new StringBuilder("checkOverheatShutdownHappened, boot completed : "), this.mBootCompleted, "PowerUI");
        Context context = this.mContext;
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.android.systemui.power_overheat_shutdown_happened", 0);
        if (sharedPreferences != null) {
            if (!sharedPreferences.getBoolean("OverheatShutdownHappened", false)) {
                Log.d("PowerUI", "Not an overheat shutdown case");
                return;
            }
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean("OverheatShutdownHappened", false);
            edit.commit();
            context.sendBroadcast(new Intent("com.android.systemui.power.action.ACTION_CLEAR_SHUTDOWN"));
            SecPowerNotificationWarnings secPowerNotificationWarnings = this.mSecPowerNotificationWarnings;
            if (1 == Settings.System.getInt(secPowerNotificationWarnings.mContext.getContentResolver(), "SHOULD_SHUT_DOWN", 0)) {
                Log.d("SecPowerUI.Notification", "don't show Overheat shutdown notice while Shutdown is ON");
                return;
            }
            if (secPowerNotificationWarnings.mWillOverheatShutdownWarningDialog != null) {
                Log.d("SecPowerUI.Notification", "don't show Overheat shutdown notice while Over heat shutdown warning");
                return;
            }
            Log.d("SecPowerUI.Notification", "showOverheatShutdownHappenedNotice()");
            Log.d("SecPowerUI.Notification", "showOverheatShutdownHappenedPopUp()");
            if (secPowerNotificationWarnings.mOverheatShutdownHappenedDialog == null) {
                AlertDialog popupDialog = secPowerNotificationWarnings.getPopupDialog(9);
                secPowerNotificationWarnings.mOverheatShutdownHappenedDialog = popupDialog;
                if (popupDialog == null) {
                    return;
                }
                popupDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.10
                    public DialogInterfaceOnDismissListenerC195010() {
                    }

                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        SecPowerNotificationWarnings.this.mOverheatShutdownHappenedDialog = null;
                    }
                });
                secPowerNotificationWarnings.mOverheatShutdownHappenedDialog.show();
                secPowerNotificationWarnings.mOverheatShutdownHappenedDialog.getButton(-1).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.power.SecPowerNotificationWarnings.11
                    public ViewOnClickListenerC195111() {
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SecPowerNotificationWarnings secPowerNotificationWarnings2 = SecPowerNotificationWarnings.this;
                        secPowerNotificationWarnings2.getClass();
                        Log.d("SecPowerUI.Notification", "dismissOverheatShutdownHappenedPopUp()");
                        AlertDialog alertDialog = secPowerNotificationWarnings2.mOverheatShutdownHappenedDialog;
                        if (alertDialog != null) {
                            alertDialog.dismiss();
                        }
                    }
                });
                secPowerNotificationWarnings.turnOnScreen();
            }
        }
    }

    public final void clearScheduling() {
        Log.i("PowerUI", "Clear time");
        boolean z = PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE;
        Context context = this.mContext;
        if (z) {
            Settings.Global.putLong(context.getContentResolver(), "ltc_highsoc_exceed_time", 0L);
        } else if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_TA) {
            Settings.Global.putString(context.getContentResolver(), "charger_connected_time", "");
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.android.systemui.power_auto_on_protect_battery", 0);
        if (sharedPreferences != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean("auto_on_protect_battery_timer_started", false);
            edit.commit();
        }
    }

    public final void dismissAdaptiveProtectionNotification() {
        Log.i("PowerUI", "dismissAdaptiveProtectionNotification");
        SecPowerNotificationWarnings secPowerNotificationWarnings = this.mSecPowerNotificationWarnings;
        secPowerNotificationWarnings.cancelNotification(10);
        secPowerNotificationWarnings.mOptimizationChargingFinishTime = "";
        Settings.Global.putInt(this.mContext.getContentResolver(), "key_sleep_charging", 0);
        this.mChargingStartTime = "";
        this.mSleepChargingEvent = "off";
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void dismissInattentiveSleepWarning(boolean z) {
        InattentiveSleepWarningView inattentiveSleepWarningView = this.mOverlayView;
        if (inattentiveSleepWarningView == null || inattentiveSleepWarningView.getParent() == null) {
            return;
        }
        inattentiveSleepWarningView.mDismissing = true;
        if (!z) {
            inattentiveSleepWarningView.setVisibility(4);
            inattentiveSleepWarningView.mWindowManager.removeView(inattentiveSleepWarningView);
        } else {
            final Animator animator = inattentiveSleepWarningView.mFadeOutAnimator;
            Objects.requireNonNull(animator);
            inattentiveSleepWarningView.postOnAnimation(new Runnable() { // from class: com.android.systemui.power.InattentiveSleepWarningView$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    animator.start();
                }
            });
        }
    }

    public synchronized void doSkinThermalEventListenerRegistration() {
        boolean z;
        boolean z2 = this.mEnableSkinTemperatureWarning;
        boolean z3 = true;
        boolean z4 = Settings.Global.getInt(this.mContext.getContentResolver(), "show_temperature_warning", this.mContext.getResources().getInteger(R.integer.config_showTemperatureWarning)) != 0;
        this.mEnableSkinTemperatureWarning = z4;
        if (z4 != z2) {
            try {
                if (this.mSkinThermalEventListener == null) {
                    this.mSkinThermalEventListener = new SkinThermalEventListener();
                }
                if (this.mThermalService == null) {
                    this.mThermalService = IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
                }
                z = this.mEnableSkinTemperatureWarning ? this.mThermalService.registerThermalEventListenerWithType(this.mSkinThermalEventListener, 3) : this.mThermalService.unregisterThermalEventListener(this.mSkinThermalEventListener);
            } catch (RemoteException e) {
                Slog.e("PowerUI", "Exception while (un)registering skin thermal event listener.", e);
                z = false;
            }
            if (!z) {
                if (this.mEnableSkinTemperatureWarning) {
                    z3 = false;
                }
                this.mEnableSkinTemperatureWarning = z3;
                Slog.e("PowerUI", "Failed to register or unregister skin thermal event listener.");
            }
        }
    }

    public synchronized void doUsbThermalEventListenerRegistration() {
        boolean z;
        boolean z2 = this.mEnableUsbTemperatureAlarm;
        boolean z3 = true;
        boolean z4 = Settings.Global.getInt(this.mContext.getContentResolver(), "show_usb_temperature_alarm", this.mContext.getResources().getInteger(R.integer.config_showUsbPortAlarm)) != 0;
        this.mEnableUsbTemperatureAlarm = z4;
        if (z4 != z2) {
            try {
                if (this.mUsbThermalEventListener == null) {
                    this.mUsbThermalEventListener = new UsbThermalEventListener();
                }
                if (this.mThermalService == null) {
                    this.mThermalService = IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
                }
                z = this.mEnableUsbTemperatureAlarm ? this.mThermalService.registerThermalEventListenerWithType(this.mUsbThermalEventListener, 4) : this.mThermalService.unregisterThermalEventListener(this.mUsbThermalEventListener);
            } catch (RemoteException e) {
                Slog.e("PowerUI", "Exception while (un)registering usb thermal event listener.", e);
                z = false;
            }
            if (!z) {
                if (this.mEnableUsbTemperatureAlarm) {
                    z3 = false;
                }
                this.mEnableUsbTemperatureAlarm = z3;
                Slog.e("PowerUI", "Failed to register or unregister usb thermal event listener.");
            }
        }
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print("mLowBatteryAlertCloseLevel=");
        printWriter.println(this.mLowBatteryAlertCloseLevel);
        printWriter.print("mLowBatteryReminderLevels=");
        printWriter.println(Arrays.toString(this.mLowBatteryReminderLevels));
        printWriter.print("mBatteryLevel=");
        printWriter.println(Integer.toString(this.mBatteryLevel));
        printWriter.print("mBatteryStatus=");
        printWriter.println(Integer.toString(this.mBatteryStatus));
        printWriter.print("mPlugType=");
        printWriter.println(Integer.toString(this.mPlugType));
        printWriter.print("mInvalidCharger=");
        printWriter.println(Integer.toString(this.mInvalidCharger));
        printWriter.print("mScreenOffTime=");
        printWriter.print(this.mScreenOffTime);
        if (this.mScreenOffTime >= 0) {
            printWriter.print(" (");
            printWriter.print(SystemClock.elapsedRealtime() - this.mScreenOffTime);
            printWriter.print(" ago)");
        }
        printWriter.println();
        printWriter.print("soundTimeout=");
        printWriter.println(Settings.Global.getInt(this.mContext.getContentResolver(), "low_battery_sound_timeout", 0));
        printWriter.print("bucket: ");
        printWriter.println(Integer.toString(findBatteryLevelBucket(this.mBatteryLevel)));
        printWriter.print("mEnableSkinTemperatureWarning=");
        printWriter.println(this.mEnableSkinTemperatureWarning);
        printWriter.print("mEnableUsbTemperatureAlarm=");
        printWriter.println(this.mEnableUsbTemperatureAlarm);
        final SecPowerNotificationWarnings secPowerNotificationWarnings = this.mSecPowerNotificationWarnings;
        secPowerNotificationWarnings.getClass();
        if (!PowerUiRune.CHN_SMART_MANAGER) {
            HandlerWrapper handlerWrapper = new HandlerWrapper();
            secPowerNotificationWarnings.mHandlerWrapper = handlerWrapper;
            handlerWrapper.mWorker.post(new Runnable() { // from class: com.android.systemui.power.SecPowerNotificationWarnings$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SecPowerNotificationWarnings secPowerNotificationWarnings2 = SecPowerNotificationWarnings.this;
                    secPowerNotificationWarnings2.getClass();
                    Log.i("SecPowerUI.Notification", "dumpsAdditionalBatteryInfo call DC service in worker thread");
                    Intent intent = new Intent();
                    intent.setPackage(PowerUiConstants.DC_PACKAGE_NAME);
                    intent.setAction("com.samsung.android.sm.DUMP");
                    Context context = secPowerNotificationWarnings2.mContext;
                    try {
                        if (context.getPackageManager().queryIntentServices(intent, 0).isEmpty()) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("dump", (Integer) 1);
                            Log.i("SecPowerUI.Notification", "update dc dump provider");
                            context.getContentResolver().update(PowerUiConstants.SMART_MGR_VERIFY_FORCED_APP_STANDBY_URI, contentValues, null, null);
                        } else {
                            Log.i("SecPowerUI.Notification", "start dc dump service");
                            context.startService(intent);
                            Log.w("SecPowerUI.Notification", "quitBgThread");
                            secPowerNotificationWarnings2.mHandlerWrapper.mWorkerThread.quitSafely();
                            secPowerNotificationWarnings2.mHandlerWrapper = null;
                        }
                    } catch (Error | Exception e) {
                        Log.w("SecPowerUI.Notification", "err", e);
                    }
                }
            });
        }
        PowerNotificationWarnings powerNotificationWarnings = (PowerNotificationWarnings) this.mWarnings;
        powerNotificationWarnings.getClass();
        printWriter.print("mWarning=");
        printWriter.println(false);
        printWriter.print("mPlaySound=");
        printWriter.println(false);
        printWriter.print("mInvalidCharger=");
        printWriter.println(powerNotificationWarnings.mInvalidCharger);
        printWriter.print("mShowing=");
        printWriter.println(PowerNotificationWarnings.SHOWING_STRINGS[powerNotificationWarnings.mShowing]);
        printWriter.print("mSaverConfirmation=");
        printWriter.println(powerNotificationWarnings.mSaverConfirmation != null ? "not null" : null);
        printWriter.print("mSaverEnabledConfirmation=");
        printWriter.print("mHighTempWarning=");
        printWriter.println(powerNotificationWarnings.mHighTempWarning);
        printWriter.print("mHighTempDialog=");
        printWriter.println(powerNotificationWarnings.mHighTempDialog != null ? "not null" : null);
        printWriter.print("mThermalShutdownDialog=");
        printWriter.println(powerNotificationWarnings.mThermalShutdownDialog != null ? "not null" : null);
        printWriter.print("mUsbHighTempDialog=");
        printWriter.println(powerNotificationWarnings.mUsbHighTempDialog == null ? null : "not null");
    }

    public final int findBatteryLevelBucket(int i) {
        if (i >= this.mLowBatteryAlertCloseLevel) {
            return 1;
        }
        int[] iArr = this.mLowBatteryReminderLevels;
        if (i > iArr[0]) {
            return 0;
        }
        for (int length = iArr.length - 1; length >= 0; length--) {
            if (i <= iArr[length]) {
                return (-1) - length;
            }
        }
        throw new RuntimeException("not possible!");
    }

    public void maybeShowHybridWarning(BatteryStateSnapshot batteryStateSnapshot, BatteryStateSnapshot batteryStateSnapshot2) {
        int i = batteryStateSnapshot.batteryLevel;
        boolean z = DEBUG;
        if (i >= 30) {
            this.mLowWarningShownThisChargeCycle = false;
            this.mSevereWarningShownThisChargeCycle = false;
            if (z) {
                Slog.d("PowerUI", "Charge cycle reset! Can show warnings again");
            }
        }
        boolean z2 = batteryStateSnapshot.bucket != batteryStateSnapshot2.bucket || batteryStateSnapshot2.plugged;
        boolean shouldShowHybridWarning = shouldShowHybridWarning(batteryStateSnapshot);
        SecPowerNotificationWarnings secPowerNotificationWarnings = this.mSecPowerNotificationWarnings;
        if (!shouldShowHybridWarning) {
            if (shouldDismissHybridWarning(batteryStateSnapshot)) {
                if (z) {
                    Slog.d("PowerUI", "Dismissing warning");
                }
                secPowerNotificationWarnings.dismissLowBatteryWarning();
                return;
            } else {
                if (z) {
                    Slog.d("PowerUI", "Updating warning");
                }
                secPowerNotificationWarnings.dismissLowBatteryWarning();
                return;
            }
        }
        secPowerNotificationWarnings.showLowBatteryWarning(z2);
        if (batteryStateSnapshot.batteryLevel > batteryStateSnapshot.severeLevelThreshold) {
            Slog.d("PowerUI", "Low warning marked as shown this cycle");
            this.mLowWarningShownThisChargeCycle = true;
            return;
        }
        this.mSevereWarningShownThisChargeCycle = true;
        this.mLowWarningShownThisChargeCycle = true;
        if (z) {
            Slog.d("PowerUI", "Severe warning marked as shown this cycle");
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void onConfigurationChanged(Configuration configuration) {
        if ((this.mLastConfiguration.updateFrom(configuration) & 3) != 0) {
            this.mHandler.post(new Runnable() { // from class: com.android.systemui.power.PowerUI$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PowerUI powerUI = PowerUI.this;
                    powerUI.doSkinThermalEventListenerRegistration();
                    powerUI.doUsbThermalEventListenerRegistration();
                }
            });
        }
    }

    public Estimate refreshEstimateIfNeeded() {
        BatteryStateSnapshot batteryStateSnapshot = this.mLastBatteryStateSnapshot;
        if (batteryStateSnapshot != null && batteryStateSnapshot.timeRemainingMillis != -1 && this.mBatteryLevel == batteryStateSnapshot.batteryLevel) {
            BatteryStateSnapshot batteryStateSnapshot2 = this.mLastBatteryStateSnapshot;
            return new Estimate(batteryStateSnapshot2.timeRemainingMillis, batteryStateSnapshot2.isBasedOnUsage, batteryStateSnapshot2.averageTimeToDischargeMillis);
        }
        ((EnhancedEstimatesImpl) this.mEnhancedEstimates).getClass();
        Estimate estimate = new Estimate(-1L, false, -1L);
        if (DEBUG) {
            Slog.d("PowerUI", "updated estimate: " + estimate.estimateMillis);
        }
        return estimate;
    }

    public final void removeChargerView() {
        WindowManager windowManager;
        if (this.mIsChargerAnimationPlaying) {
            if (!this.mPowerManager.isInteractive()) {
                Log.i("PowerUI", "onChargerAnimationEnd : Lcd OFF -> so call chargingAnimStarted(false)");
                ((PluginAODManager) this.mPluginAODManagerLazy.get()).chargingAnimStarted(false);
            }
            ChargerAnimationView chargerAnimationView = this.mChargerAnimationView;
            if (chargerAnimationView != null && (windowManager = this.mChargerAnimationWindowManager) != null) {
                windowManager.removeView(chargerAnimationView);
                this.mChargerAnimationView = null;
            }
            this.mChargerAnimationWindowManager = null;
            this.mIsChargerAnimationPlaying = false;
        }
    }

    public final void removeMisalignView() {
        WindowManager windowManager;
        PowerManager.WakeLock wakeLock = this.mWirelessMisalignWakeLock;
        if (wakeLock != null && wakeLock.isHeld()) {
            this.mWirelessMisalignWakeLock.release();
            this.mWirelessMisalignWakeLock = null;
        }
        WirelessMisalignView wirelessMisalignView = this.mWirelessMisalignView;
        if (wirelessMisalignView != null && (windowManager = this.mWirelessMisalignWindowManager) != null) {
            windowManager.removeView(wirelessMisalignView);
            this.mWirelessMisalignView = null;
        }
        this.mWirelessMisalignWindowManager = null;
        this.mIsWirelessMisalignTask = false;
        this.mHandler.removeCallbacks(this.mWirelessMisalignTimeoutTask);
    }

    public final void setWirelessMisalignView(int i) {
        if (this.mWirelessMisalignWindowLp == null) {
            WindowManager.LayoutParams layoutParam = getLayoutParam("PowerUI.WirelessMisalignViewLp");
            this.mWirelessMisalignWindowLp = layoutParam;
            layoutParam.type = 2009;
        }
        boolean isFolded = SemWindowManager.getInstance().isFolded();
        WindowManager windowManager = this.mWirelessMisalignWindowManager;
        Context context = this.mContext;
        if (windowManager == null) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("folder state : ", isFolded, "PowerUI");
            boolean z = BasicRune.BASIC_FOLDABLE_TYPE_FLIP;
            if (z && isFolded) {
                Context subDisplayContext = PowerUtils.getSubDisplayContext(context);
                if (PowerUiRune.COVER_DISPLAY_LARGE_SCREEN) {
                    this.mWirelessMisalignWindowLp.layoutInDisplayCutoutMode = 3;
                }
                this.mWirelessMisalignWindowManager = (WindowManager) subDisplayContext.getSystemService("window");
                this.mWirelessMisalignView = (WirelessMisalignView) View.inflate(subDisplayContext, R.layout.battery_misalign_subdisplay_layout, null);
            } else {
                this.mWirelessMisalignWindowManager = (WindowManager) context.getSystemService("window");
                if (isFolded && BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
                    this.mWirelessMisalignView = (WirelessMisalignView) View.inflate(context, R.layout.battery_misalign_normal_layout, null);
                } else if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
                    this.mWirelessMisalignView = (WirelessMisalignView) View.inflate(context, R.layout.battery_misalign_fold_layout, null);
                } else if (z) {
                    this.mWirelessMisalignView = (WirelessMisalignView) View.inflate(context, R.layout.battery_misalign_flip_layout, null);
                } else {
                    this.mWirelessMisalignView = (WirelessMisalignView) View.inflate(context, R.layout.battery_misalign_normal_layout, null);
                }
            }
        }
        this.mWirelessMisalignWindowManager.addView(this.mWirelessMisalignView, this.mWirelessMisalignWindowLp);
        WirelessMisalignView wirelessMisalignView = this.mWirelessMisalignView;
        wirelessMisalignView.mListener = this;
        if (i == 0) {
            wirelessMisalignView.mTextContainerLayout.setVisibility(0);
            wirelessMisalignView.mButton.setVisibility(0);
            wirelessMisalignView.mCenterImageView.setImageResource(R.drawable.overlay_center_alignment);
        } else if (i == 1) {
            wirelessMisalignView.mTextContainerLayout.setVisibility(8);
            wirelessMisalignView.mButton.setVisibility(8);
            wirelessMisalignView.mCenterImageView.setImageResource(R.drawable.overlay_center_alignment_ok);
        }
        PowerManager powerManager = this.mPowerManager;
        if (powerManager != null) {
            Log.i("PowerUI", "turn on screen - misalign view");
            powerManager.wakeUp(SystemClock.uptimeMillis(), context.getOpPackageName());
            if (this.mWirelessMisalignWakeLock == null) {
                this.mWirelessMisalignWakeLock = powerManager.newWakeLock(268435462, "PowerUI");
            }
            this.mWirelessMisalignWakeLock.acquire(30000L);
        }
        this.mWirelessMisalignView.setWirelessMisalignViewVisibility(0);
    }

    public boolean shouldDismissHybridWarning(BatteryStateSnapshot batteryStateSnapshot) {
        return batteryStateSnapshot.plugged || batteryStateSnapshot.batteryLevel > batteryStateSnapshot.lowLevelThreshold;
    }

    public boolean shouldDismissLowBatteryWarning(BatteryStateSnapshot batteryStateSnapshot, BatteryStateSnapshot batteryStateSnapshot2) {
        if (!batteryStateSnapshot.isPowerSaver && !batteryStateSnapshot.plugged) {
            int i = batteryStateSnapshot2.bucket;
            int i2 = batteryStateSnapshot.bucket;
            if (i2 <= i || i2 <= 0) {
                return false;
            }
        }
        return true;
    }

    public boolean shouldShowHybridWarning(BatteryStateSnapshot batteryStateSnapshot) {
        boolean z = batteryStateSnapshot.plugged;
        int i = batteryStateSnapshot.batteryStatus;
        if (z || i == 1) {
            StringBuilder sb = new StringBuilder("can't show warning due to - plugged: ");
            sb.append(batteryStateSnapshot.plugged);
            sb.append(" status unknown: ");
            sb.append(i == 1);
            Slog.d("PowerUI", sb.toString());
            return false;
        }
        boolean z2 = this.mLowWarningShownThisChargeCycle;
        int i2 = batteryStateSnapshot.batteryLevel;
        boolean z3 = (!z2 && !batteryStateSnapshot.isPowerSaver && i2 <= batteryStateSnapshot.lowLevelThreshold) || (!this.mSevereWarningShownThisChargeCycle && i2 <= batteryStateSnapshot.severeLevelThreshold);
        if (DEBUG) {
            StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("Enhanced trigger is: ", z3, "\nwith battery snapshot: mLowWarningShownThisChargeCycle: ");
            m49m.append(this.mLowWarningShownThisChargeCycle);
            m49m.append(" mSevereWarningShownThisChargeCycle: ");
            m49m.append(this.mSevereWarningShownThisChargeCycle);
            m49m.append("\n");
            m49m.append(batteryStateSnapshot.toString());
            Slog.d("PowerUI", m49m.toString());
        }
        return z3;
    }

    public boolean shouldShowLowBatteryWarning(BatteryStateSnapshot batteryStateSnapshot, BatteryStateSnapshot batteryStateSnapshot2) {
        if (!batteryStateSnapshot.plugged && !batteryStateSnapshot.isPowerSaver) {
            int i = batteryStateSnapshot2.bucket;
            int i2 = batteryStateSnapshot.bucket;
            if ((i2 < i || batteryStateSnapshot2.plugged) && i2 < 0 && batteryStateSnapshot.batteryStatus != 1) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
    public final void showInattentiveSleepWarning() {
        if (this.mOverlayView == null) {
            this.mOverlayView = new InattentiveSleepWarningView(this.mContext);
        }
        InattentiveSleepWarningView inattentiveSleepWarningView = this.mOverlayView;
        if (inattentiveSleepWarningView.getParent() != null) {
            if (inattentiveSleepWarningView.mFadeOutAnimator.isStarted()) {
                inattentiveSleepWarningView.mFadeOutAnimator.cancel();
                return;
            }
            return;
        }
        inattentiveSleepWarningView.setAlpha(1.0f);
        inattentiveSleepWarningView.setVisibility(0);
        WindowManager windowManager = inattentiveSleepWarningView.mWindowManager;
        IBinder iBinder = inattentiveSleepWarningView.mWindowToken;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2006, 256, -3);
        layoutParams.privateFlags |= 16;
        layoutParams.setTitle("InattentiveSleepWarning");
        layoutParams.token = iBinder;
        windowManager.addView(inattentiveSleepWarningView, layoutParams);
        inattentiveSleepWarningView.announceForAccessibility(inattentiveSleepWarningView.getContext().getString(R.string.inattentive_sleep_warning_message));
    }

    public final void showTipsNotification() {
        Intent intent = new Intent();
        Intent intent2 = new Intent();
        intent2.setClassName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$HighRefreshRatesSettingsActivity");
        intent2.setFlags(268468224);
        Intent intent3 = new Intent("com.samsung.android.sm.TIPS_DELETED");
        intent3.setClassName("com.android.systemui", "com.android.systemui.power.TipsNotificationService");
        intent.setClassName("com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
        intent.putExtra("tips_extras", 9);
        intent.putExtra("tips_id", String.valueOf(120999));
        Context context = this.mContext;
        intent.putExtra("tips_app_name", context.getPackageName());
        intent.putExtra("tips_title", context.getString(R.string.motion_smoothness_tips_noti_title));
        intent.putExtra("tips_text", context.getString(R.string.motion_smoothness_tips_noti_content));
        intent.putExtra("tips_noti_category", "CATEGORY_RECOMMENDATION");
        intent.putExtra("tips_action", intent2);
        intent.putExtra("tips_delete_action", intent3);
        intent.putExtra("tips_delete_action_type", 1);
        intent.putExtra("tips_condition", 1);
        intent.putExtra("tips_noti_skip_add_action", 1);
        Slog.d("PowerUI", "showTipsNotification - ALL condition is OK, so show tips notification !!");
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.android.systemui.power_tips_notification", 0);
        if (sharedPreferences != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putLong("tipsNotiLastTime", System.currentTimeMillis());
            edit.commit();
        }
        context.startForegroundService(intent);
    }

    public final boolean skipAnimByMotionDetected() {
        if (!this.mIsMotionDetectionSupported || this.mIsDeviceMoving || 4 != this.mPlugType) {
            return false;
        }
        Log.w("PowerUI", "Charger connected but device had no move detection");
        if (!this.mPowerManager.isInteractive()) {
            Log.w("PowerUI", "Charger connected but device had no move detection and screen off => trigger AOD");
            Lazy lazy = this.mPluginAODManagerLazy;
            ((PluginAODManager) lazy.get()).chargingAnimStarted(true);
            ((PluginAODManager) lazy.get()).chargingAnimStarted(false);
        }
        return true;
    }

    public final boolean skipAnimByPlugStatus(int i, int i2, boolean z) {
        int i3;
        int i4;
        if (i == -1 || (i3 = this.mPlugType) == 0 || 2 != (i4 = this.mBatteryStatus) || !this.mFullyConnected || ((i2 == 5 || i2 == i4) && i == i3 && z)) {
            Log.w("PowerUI", "Plug reason");
            return true;
        }
        if (this.mBatteryOnline == 99) {
            Log.w("PowerUI", "AFC retry case");
            return true;
        }
        if ((i3 != 1 || i != 2) && (i3 != 2 || i != 1)) {
            return false;
        }
        Log.w("PowerUI", "Only cable charger type changed");
        return true;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        int i;
        Intent registerReceiver;
        PowerManager powerManager = this.mPowerManager;
        this.mScreenOffTime = powerManager.isScreenOn() ? -1L : SystemClock.elapsedRealtime();
        Context context = this.mContext;
        this.mLastConfiguration.setTo(context.getResources().getConfiguration());
        Handler handler = this.mHandler;
        ContentObserver contentObserver = new ContentObserver(handler) { // from class: com.android.systemui.power.PowerUI.3
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                PowerUI.this.updateBatteryWarningLevels();
            }
        };
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.registerContentObserver(Settings.Global.getUriFor("low_power_trigger_level"), false, contentObserver, -1);
        updateBatteryWarningLevels();
        Receiver receiver = this.mReceiver;
        receiver.getClass();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("com.samsung.server.BatteryService.action.SEC_BATTERY_EVENT");
        intentFilter.addAction("com.samsung.systemui.power.action.WATER_ALERT_SOUND_TEST");
        intentFilter.addAction("com.samsung.intent.action.KSO_SHOW_POPUP");
        intentFilter.addAction("com.samsung.intent.action.KSO_CLOSE_POPUP");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.samsung.CHECK_COOLDOWN_LEVEL");
        intentFilter2.addAction("com.sec.android.intent.action.SAFEMODE_ENABLE");
        PowerUI powerUI = PowerUI.this;
        powerUI.mContext.registerReceiver(receiver, intentFilter2, "com.samsung.android.permission.SSRM_NOTIFICATION_PERMISSION", powerUI.mHandler, 2);
        if (PowerUiRune.TIPS_NOTIFICATION) {
            intentFilter.addAction("com.samsung.android.sm.IGNORE_RUT_TIPS_NOTI");
            intentFilter.addAction("com.samsung.android.sm.CLEAR_TIPS_NOTI");
            intentFilter.addAction("android.intent.action.tips.noti.confirmed");
        }
        if (PowerUiRune.INIT_LTC_TIME_CHANGED) {
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            intentFilter.addAction("android.intent.action.TIME_SET");
        }
        if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION) {
            intentFilter.addAction("com.samsung.server.BatteryService.action.ACTION_SLEEP_CHARGING");
        }
        PowerUI powerUI2 = PowerUI.this;
        powerUI2.mBroadcastDispatcher.registerReceiverWithHandler(receiver, intentFilter, powerUI2.mHandler);
        if (!receiver.mHasReceivedBattery && (registerReceiver = PowerUI.this.mContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"), 2)) != null) {
            receiver.onReceive(PowerUI.this.mContext, registerReceiver);
        }
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserChangedCallback, context.getMainExecutor());
        this.mWakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        int i2 = context.getSharedPreferences("powerui_prefs", 0).getInt("boot_count", -1);
        try {
            i = Settings.Global.getInt(context.getContentResolver(), "boot_count");
        } catch (Settings.SettingNotFoundException unused) {
            Slog.e("PowerUI", "Failed to read system boot count from Settings.Global.BOOT_COUNT");
            i = -1;
        }
        if (i > i2) {
            context.getSharedPreferences("powerui_prefs", 0).edit().putInt("boot_count", i).apply();
            if (powerManager.getLastShutdownReason() == 4) {
                PowerNotificationWarnings powerNotificationWarnings = (PowerNotificationWarnings) this.mWarnings;
                Context context2 = powerNotificationWarnings.mContext;
                String string = context2.getString(R.string.thermal_shutdown_message);
                Notification.Builder color = new Notification.Builder(context2, "ALR").setSmallIcon(R.drawable.ic_device_thermostat_24).setWhen(0L).setShowWhen(false).setContentTitle(context2.getString(R.string.thermal_shutdown_title)).setContentText(string).setStyle(new Notification.BigTextStyle().bigText(string)).setVisibility(1).setContentIntent(powerNotificationWarnings.pendingBroadcast("PNW.clickedThermalShutdownWarning")).setDeleteIntent(powerNotificationWarnings.pendingBroadcast("PNW.dismissedThermalShutdownWarning")).setColor(Utils.getColorAttrDefaultColor(android.R.attr.colorError, context2, 0));
                SystemUIApplication.overrideNotificationAppName(context2, color, false);
                powerNotificationWarnings.mNoMan.notifyAsUser("high_temp", 39, color.build(), UserHandle.ALL);
            }
        }
        contentResolver.registerContentObserver(Settings.Global.getUriFor("show_temperature_warning"), false, new ContentObserver(handler) { // from class: com.android.systemui.power.PowerUI.4
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                PowerUI.this.doSkinThermalEventListenerRegistration();
            }
        });
        contentResolver.registerContentObserver(Settings.Global.getUriFor("show_usb_temperature_alarm"), false, new ContentObserver(handler) { // from class: com.android.systemui.power.PowerUI.5
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                PowerUI.this.doUsbThermalEventListenerRegistration();
            }
        });
        doSkinThermalEventListenerRegistration();
        doUsbThermalEventListenerRegistration();
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        boolean equals = "1".equals(SystemProperties.get("sys.boot_completed"));
        this.mBootCompleted = equals;
        if (equals) {
            checkOverheatShutdownHappened();
            if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE) {
                int protectBatteryValue = PowerUtils.getProtectBatteryValue(context);
                int i3 = Settings.Global.getInt(context.getContentResolver(), "prev_protect_battery_ltc", -1);
                if (protectBatteryValue == 2) {
                    Settings.Global.putInt(context.getContentResolver(), "protect_battery", i3);
                    Settings.Global.putInt(context.getContentResolver(), "prev_protect_battery_ltc", -1);
                }
                clearScheduling();
            }
            if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION) {
                dismissAdaptiveProtectionNotification();
                this.mIsAfterAdaptiveProtection = false;
            }
        }
        if (PowerUiRune.PROTECT_BATTERY_CUTOFF) {
            this.mBatteryProtectionThreshold = Settings.Global.getInt(context.getContentResolver(), "battery_protection_threshold", Settings.Global.BATTERY_PROTECTION_THRESHOLD_DEFAULT_VALUE);
            contentResolver.registerContentObserver(Settings.Global.getUriFor("protect_battery"), false, new ContentObserver(handler) { // from class: com.android.systemui.power.PowerUI.6
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    PowerUI powerUI3 = PowerUI.this;
                    int i4 = powerUI3.mProtectBatteryValue;
                    powerUI3.mProtectBatteryValue = PowerUtils.getProtectBatteryValue(powerUI3.mContext);
                    PowerUI powerUI4 = PowerUI.this;
                    int i5 = powerUI4.mProtectBatteryValue;
                    boolean z2 = true;
                    powerUI4.mIsProtectingBatteryCutOffSettingEnabled = i5 == 1 || i5 == 2;
                    if (PowerUiRune.BATTERY_PROTECTION_NOTIFICATION) {
                        powerUI4.mSecPowerNotificationWarnings.cancelNotification(9);
                        PowerUI powerUI5 = PowerUI.this;
                        int i6 = powerUI5.mProtectBatteryValue;
                        if ((i4 != 4 || i6 != 3) && ((i4 != 3 || i6 != 4) && ((i4 != 0 || i6 != 3) && (i4 != 0 || i6 != 4)))) {
                            z2 = false;
                        }
                        if (z2) {
                            powerUI5.checkBatteryProtectionNotification();
                        }
                    }
                    if (PowerUiRune.ADAPTIVE_PROTECTION_NOTIFICATION && i4 == 4) {
                        PowerUI powerUI6 = PowerUI.this;
                        if (powerUI6.mProtectBatteryValue != 4) {
                            powerUI6.dismissAdaptiveProtectionNotification();
                            PowerUI.this.mIsAfterAdaptiveProtection = false;
                        }
                    }
                    if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE) {
                        PowerUI.m1608$$Nest$mcheckTurnOnProtectBatteryByLongTermCharge(PowerUI.this);
                    } else if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_TA) {
                        PowerUI.m1607$$Nest$mcheckTurnOnProtectBatteryByLongTa(PowerUI.this);
                    }
                }
            }, -1);
            int protectBatteryValue2 = PowerUtils.getProtectBatteryValue(context);
            this.mProtectBatteryValue = protectBatteryValue2;
            this.mIsProtectingBatteryCutOffSettingEnabled = protectBatteryValue2 == 1 || protectBatteryValue2 == 2;
            if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE) {
                this.mLtcHighSocThreshold = Settings.Global.getInt(context.getContentResolver(), "ltc_highsoc_threshold", 95);
                this.mLtcReleaseThreshold = Settings.Global.getInt(context.getContentResolver(), "ltc_release_threshold", 75);
                Log.i("PowerUI", "enabled level : " + this.mLtcHighSocThreshold + ", clear level : " + this.mLtcReleaseThreshold + ", duration : " + Settings.Global.getInt(context.getContentResolver(), "ltc_highsoc_duration", 10080));
            }
        }
        this.mSecPowerNotificationWarnings.restoreScreenTimeOutIfNeeded();
        if (context.getPackageManager().hasSystemFeature("com.sec.feature.sensorhub")) {
            Log.d("PowerUI", "start : hasSystemFeature(com.sec.feature.sensorhub)");
            this.mIsMotionDetectionSupported = true;
            SContextManager sContextManager = (SContextManager) context.getSystemService("scontext");
            this.mSContextManager = sContextManager;
            if (sContextManager != null) {
                this.mIsSContextEnabled = sContextManager.isAvailableService(46);
                ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("start : (mSContextManager != null - mIsSContextEnabled = "), this.mIsSContextEnabled, "PowerUI");
            }
        }
        ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).registerCallback(this.mScreenOnOffCallback);
        this.mBatteryHealthInterruptionPartialWakeLock = powerManager.newWakeLock(1, "PowerUI");
        ((TelephonyManager) context.getSystemService("phone")).listen(this.mPhoneStateListener, 32);
    }

    public final void startScheduling() {
        Context context = this.mContext;
        if (context.getSharedPreferences("com.android.systemui.power_auto_on_protect_battery", 0).getBoolean("auto_on_protect_battery_timer_started", false)) {
            return;
        }
        Log.i("PowerUI", "Meet soc conditions, start scheduling");
        if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE) {
            Settings.Global.putLong(context.getContentResolver(), "ltc_highsoc_exceed_time", System.currentTimeMillis());
        } else if (PowerUiRune.TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_TA) {
            Settings.Global.putString(context.getContentResolver(), "charger_connected_time", String.valueOf(System.currentTimeMillis()));
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.android.systemui.power_auto_on_protect_battery", 0);
        if (sharedPreferences != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean("auto_on_protect_battery_timer_started", true);
            edit.commit();
        }
    }

    public final void updateBatteryWarningLevels() {
        Context context = this.mContext;
        int integer = context.getResources().getInteger(android.R.integer.config_defaultNotificationLedOn);
        int integer2 = context.getResources().getInteger(android.R.integer.config_minMillisBetweenInputUserActivityEvents);
        if (integer2 < integer) {
            integer2 = integer;
        }
        int[] iArr = this.mLowBatteryReminderLevels;
        iArr[0] = integer2;
        iArr[1] = integer;
        this.mLowBatteryAlertCloseLevel = context.getResources().getInteger(android.R.integer.config_minDreamOverlayDurationMs) + integer2;
    }
}
