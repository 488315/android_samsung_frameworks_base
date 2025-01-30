package com.android.systemui.power;

import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.Annotation;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.util.Slog;
import android.view.View;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.fuelgauge.BatterySaverUtils;
import com.android.systemui.R;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.power.PowerUI;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.util.settings.GlobalSettings;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import dagger.Lazy;
import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PowerNotificationWarnings implements PowerUI.WarningsUI {
    public static final boolean DEBUG = PowerUI.DEBUG;
    public static final String[] SHOWING_STRINGS = {"SHOWING_NOTHING", "SHOWING_WARNING", "SHOWING_SAVER", "SHOWING_INVALID_CHARGER", "SHOWING_AUTO_SAVER_SUGGESTION"};
    public final ActivityStarter mActivityStarter;
    public final Lazy mBatteryControllerLazy;
    public int mBatteryLevel;
    public final BroadcastSender mBroadcastSender;
    public int mBucket;
    public final Context mContext;
    public BatteryStateSnapshot mCurrentBatterySnapshot;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public SystemUIDialog mHighTempDialog;
    public boolean mHighTempWarning;
    public boolean mInvalidCharger;
    public final KeyguardManager mKeyguard;
    public final NotificationManager mNoMan;
    public final Intent mOpenBatterySaverSettings;
    public final Intent mOpenBatterySettings;
    public final PowerManager mPowerMan;
    public SystemUIDialog mSaverConfirmation;
    public boolean mShowAutoSaverSuggestion;
    public int mShowing;
    public SystemUIDialog mThermalShutdownDialog;
    public final UiEventLogger mUiEventLogger;
    SystemUIDialog mUsbHighTempDialog;
    public final boolean mUseExtraSaverConfirmation;
    public final UserTracker mUserTracker;
    public long mWarningTriggerTimeMs;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.power.PowerNotificationWarnings$1 */
    public final class DialogInterfaceOnClickListenerC19311 implements DialogInterface.OnClickListener {
        public final /* synthetic */ String val$url;

        public DialogInterfaceOnClickListenerC19311(String str) {
            this.val$url = str;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            PowerNotificationWarnings.this.mActivityStarter.startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse(this.val$url)).setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE), true, (ActivityStarter.Callback) new PowerNotificationWarnings$$ExternalSyntheticLambda3(this, 1));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.power.PowerNotificationWarnings$2 */
    public final class DialogInterfaceOnClickListenerC19322 implements DialogInterface.OnClickListener {
        public final /* synthetic */ String val$url;

        public DialogInterfaceOnClickListenerC19322(String str) {
            this.val$url = str;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            PowerNotificationWarnings.this.mActivityStarter.startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse(this.val$url)).setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE), true, (ActivityStarter.Callback) new PowerNotificationWarnings$$ExternalSyntheticLambda3(this, 2));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Receiver extends BroadcastReceiver {
        public /* synthetic */ Receiver(PowerNotificationWarnings powerNotificationWarnings, int i) {
            this();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v40, types: [android.app.AlertDialog, android.app.Dialog, com.android.systemui.statusbar.phone.SystemUIDialog] */
        /* JADX WARN: Type inference failed for: r3v13, types: [android.text.SpannableStringBuilder] */
        /* JADX WARN: Type inference failed for: r3v14, types: [java.lang.CharSequence] */
        /* JADX WARN: Type inference failed for: r3v29, types: [java.lang.CharSequence] */
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            ?? spannableStringBuilder;
            String action = intent.getAction();
            Slog.i("PowerUI.Notification", "Received " + action);
            if (action.equals("PNW.batterySaverSettings")) {
                PowerNotificationWarnings powerNotificationWarnings = PowerNotificationWarnings.this;
                BatteryWarningEvents$LowBatteryWarningEvent batteryWarningEvents$LowBatteryWarningEvent = BatteryWarningEvents$LowBatteryWarningEvent.LOW_BATTERY_NOTIFICATION_SETTINGS;
                boolean z = PowerNotificationWarnings.DEBUG;
                powerNotificationWarnings.logEvent(batteryWarningEvents$LowBatteryWarningEvent);
                PowerNotificationWarnings.this.updateNotification();
                PowerNotificationWarnings powerNotificationWarnings2 = PowerNotificationWarnings.this;
                powerNotificationWarnings2.mContext.startActivityAsUser(powerNotificationWarnings2.mOpenBatterySaverSettings, ((UserTrackerImpl) powerNotificationWarnings2.mUserTracker).getUserHandle());
                return;
            }
            if (action.equals("PNW.startSaver")) {
                PowerNotificationWarnings powerNotificationWarnings3 = PowerNotificationWarnings.this;
                BatteryWarningEvents$LowBatteryWarningEvent batteryWarningEvents$LowBatteryWarningEvent2 = BatteryWarningEvents$LowBatteryWarningEvent.LOW_BATTERY_NOTIFICATION_TURN_ON;
                boolean z2 = PowerNotificationWarnings.DEBUG;
                powerNotificationWarnings3.logEvent(batteryWarningEvents$LowBatteryWarningEvent2);
                BatterySaverUtils.setPowerSaveMode(5, PowerNotificationWarnings.this.mContext, true, true);
                PowerNotificationWarnings.this.updateNotification();
                return;
            }
            if (action.equals("PNW.startSaverConfirmation")) {
                PowerNotificationWarnings powerNotificationWarnings4 = PowerNotificationWarnings.this;
                boolean z3 = PowerNotificationWarnings.DEBUG;
                powerNotificationWarnings4.updateNotification();
                final PowerNotificationWarnings powerNotificationWarnings5 = PowerNotificationWarnings.this;
                Bundle extras = intent.getExtras();
                if (powerNotificationWarnings5.mSaverConfirmation != null || powerNotificationWarnings5.mUseExtraSaverConfirmation) {
                    return;
                }
                Context context2 = powerNotificationWarnings5.mContext;
                ?? systemUIDialog = new SystemUIDialog(context2);
                boolean z4 = extras.getBoolean("extra_confirm_only");
                final int i = extras.getInt("extra_power_save_mode_trigger", 0);
                final int i2 = extras.getInt("extra_power_save_mode_trigger_level", 0);
                String charSequence = context2.getText(R.string.help_uri_battery_saver_learn_more_link_target).toString();
                if (TextUtils.isEmpty(charSequence)) {
                    spannableStringBuilder = context2.getText(R.string.battery_low_intro);
                } else {
                    SpannableString spannableString = new SpannableString(context2.getText(android.R.string.config_defaultCredentialManagerAutofillService));
                    spannableStringBuilder = new SpannableStringBuilder(spannableString);
                    for (Annotation annotation : (Annotation[]) spannableString.getSpans(0, spannableString.length(), Annotation.class)) {
                        if ("url".equals(annotation.getValue())) {
                            int spanStart = spannableString.getSpanStart(annotation);
                            int spanEnd = spannableString.getSpanEnd(annotation);
                            URLSpan uRLSpan = new URLSpan(charSequence) { // from class: com.android.systemui.power.PowerNotificationWarnings.3
                                @Override // android.text.style.URLSpan, android.text.style.ClickableSpan
                                public final void onClick(View view) {
                                    SystemUIDialog systemUIDialog2 = PowerNotificationWarnings.this.mSaverConfirmation;
                                    if (systemUIDialog2 != null) {
                                        systemUIDialog2.dismiss();
                                    }
                                    PowerNotificationWarnings.this.mBroadcastSender.closeSystemDialogs();
                                    Uri parse = Uri.parse(getURL());
                                    Context context3 = view.getContext();
                                    Intent flags = new Intent("android.intent.action.VIEW", parse).setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                                    try {
                                        context3.startActivity(flags);
                                    } catch (ActivityNotFoundException unused) {
                                        Log.w("PowerUI.Notification", "Activity was not found for intent, " + flags.toString());
                                    }
                                }

                                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                                public final void updateDrawState(TextPaint textPaint) {
                                    super.updateDrawState(textPaint);
                                    textPaint.setUnderlineText(false);
                                }
                            };
                            spannableStringBuilder.setSpan(uRLSpan, spanStart, spanEnd, spannableString.getSpanFlags(uRLSpan));
                        }
                    }
                }
                systemUIDialog.setMessage(spannableStringBuilder);
                if (Objects.equals(Locale.getDefault().getLanguage(), Locale.ENGLISH.getLanguage())) {
                    systemUIDialog.setMessageHyphenationFrequency(0);
                }
                systemUIDialog.setMessageMovementMethod(LinkMovementMethod.getInstance());
                if (z4) {
                    systemUIDialog.setTitle(R.string.battery_saver_confirmation_title_generic);
                    systemUIDialog.setPositiveButton(android.R.string.face_acquired_too_left, new DialogInterface.OnClickListener() { // from class: com.android.systemui.power.PowerNotificationWarnings$$ExternalSyntheticLambda4
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i3) {
                            PowerNotificationWarnings powerNotificationWarnings6 = PowerNotificationWarnings.this;
                            int i4 = i;
                            int i5 = i2;
                            ContentResolver contentResolver = powerNotificationWarnings6.mContext.getContentResolver();
                            Settings.Global.putInt(contentResolver, "automatic_power_save_mode", i4);
                            Settings.Global.putInt(contentResolver, "low_power_trigger_level", i5);
                            UserTrackerImpl userTrackerImpl = (UserTrackerImpl) powerNotificationWarnings6.mUserTracker;
                            Settings.Secure.putIntForUser(contentResolver, "low_power_warning_acknowledged", 1, userTrackerImpl.getUserId());
                            Settings.Secure.putIntForUser(contentResolver, "extra_low_power_warning_acknowledged", 1, userTrackerImpl.getUserId());
                        }
                    });
                } else {
                    systemUIDialog.setTitle(R.string.battery_saver_confirmation_title);
                    systemUIDialog.setPositiveButton(R.string.battery_saver_confirmation_ok, new PowerNotificationWarnings$$ExternalSyntheticLambda1(powerNotificationWarnings5, 2));
                    systemUIDialog.setNegativeButton(android.R.string.cancel, new PowerNotificationWarnings$$ExternalSyntheticLambda1(powerNotificationWarnings5, 3));
                }
                SystemUIDialog.setShowForAllUsers(systemUIDialog);
                systemUIDialog.setOnDismissListener(new PowerNotificationWarnings$$ExternalSyntheticLambda2(powerNotificationWarnings5, 2));
                Lazy lazy = powerNotificationWarnings5.mBatteryControllerLazy;
                WeakReference weakReference = (WeakReference) ((BatteryControllerImpl) ((BatteryController) lazy.get())).mPowerSaverStartView.get();
                if (weakReference == null || weakReference.get() == null || !((View) weakReference.get()).isAggregatedVisible()) {
                    systemUIDialog.show();
                } else {
                    View view = (View) weakReference.get();
                    DialogCuj dialogCuj = new DialogCuj(58, "start_power_saver");
                    DialogLaunchAnimator dialogLaunchAnimator = powerNotificationWarnings5.mDialogLaunchAnimator;
                    dialogLaunchAnimator.getClass();
                    DialogLaunchAnimator.showFromView$default(dialogLaunchAnimator, systemUIDialog, view, dialogCuj, false, 8);
                }
                powerNotificationWarnings5.logEvent(BatteryWarningEvents$LowBatteryWarningEvent.SAVER_CONFIRM_DIALOG);
                powerNotificationWarnings5.mSaverConfirmation = systemUIDialog;
                ((BatteryControllerImpl) ((BatteryController) lazy.get())).mPowerSaverStartView.set(null);
                return;
            }
            if (action.equals("PNW.dismissedWarning")) {
                PowerNotificationWarnings powerNotificationWarnings6 = PowerNotificationWarnings.this;
                BatteryWarningEvents$LowBatteryWarningEvent batteryWarningEvents$LowBatteryWarningEvent3 = BatteryWarningEvents$LowBatteryWarningEvent.LOW_BATTERY_NOTIFICATION_CANCEL;
                boolean z5 = PowerNotificationWarnings.DEBUG;
                powerNotificationWarnings6.logEvent(batteryWarningEvents$LowBatteryWarningEvent3);
                PowerNotificationWarnings powerNotificationWarnings7 = PowerNotificationWarnings.this;
                if (PowerNotificationWarnings.DEBUG) {
                    Slog.d("PowerUI.Notification", "dismissing low battery warning: level=" + powerNotificationWarnings7.mBatteryLevel);
                }
                powerNotificationWarnings7.updateNotification();
                return;
            }
            if ("PNW.clickedTempWarning".equals(action)) {
                PowerNotificationWarnings powerNotificationWarnings8 = PowerNotificationWarnings.this;
                boolean z6 = PowerNotificationWarnings.DEBUG;
                powerNotificationWarnings8.dismissHighTemperatureWarningInternal();
                PowerNotificationWarnings powerNotificationWarnings9 = PowerNotificationWarnings.this;
                if (powerNotificationWarnings9.mHighTempDialog != null) {
                    return;
                }
                Context context3 = powerNotificationWarnings9.mContext;
                SystemUIDialog systemUIDialog2 = new SystemUIDialog(context3);
                systemUIDialog2.setIconAttribute(android.R.attr.alertDialogIcon);
                systemUIDialog2.setTitle(R.string.high_temp_title);
                systemUIDialog2.setMessage(R.string.high_temp_dialog_message);
                systemUIDialog2.setPositiveButton(android.R.string.ok, null);
                SystemUIDialog.setShowForAllUsers(systemUIDialog2);
                systemUIDialog2.setOnDismissListener(new PowerNotificationWarnings$$ExternalSyntheticLambda2(powerNotificationWarnings9, 3));
                String string = context3.getString(R.string.high_temp_dialog_help_url);
                if (!string.isEmpty()) {
                    systemUIDialog2.setNeutralButton(R.string.high_temp_dialog_help_text, powerNotificationWarnings9.new DialogInterfaceOnClickListenerC19311(string));
                }
                systemUIDialog2.show();
                powerNotificationWarnings9.mHighTempDialog = systemUIDialog2;
                return;
            }
            if ("PNW.dismissedTempWarning".equals(action)) {
                PowerNotificationWarnings powerNotificationWarnings10 = PowerNotificationWarnings.this;
                boolean z7 = PowerNotificationWarnings.DEBUG;
                powerNotificationWarnings10.dismissHighTemperatureWarningInternal();
                return;
            }
            if ("PNW.clickedThermalShutdownWarning".equals(action)) {
                PowerNotificationWarnings.this.dismissThermalShutdownWarning();
                PowerNotificationWarnings powerNotificationWarnings11 = PowerNotificationWarnings.this;
                if (powerNotificationWarnings11.mThermalShutdownDialog != null) {
                    return;
                }
                Context context4 = powerNotificationWarnings11.mContext;
                SystemUIDialog systemUIDialog3 = new SystemUIDialog(context4);
                systemUIDialog3.setIconAttribute(android.R.attr.alertDialogIcon);
                systemUIDialog3.setTitle(R.string.thermal_shutdown_title);
                systemUIDialog3.setMessage(R.string.thermal_shutdown_dialog_message);
                systemUIDialog3.setPositiveButton(android.R.string.ok, null);
                SystemUIDialog.setShowForAllUsers(systemUIDialog3);
                systemUIDialog3.setOnDismissListener(new PowerNotificationWarnings$$ExternalSyntheticLambda2(powerNotificationWarnings11, 1));
                String string2 = context4.getString(R.string.thermal_shutdown_dialog_help_url);
                if (!string2.isEmpty()) {
                    systemUIDialog3.setNeutralButton(R.string.thermal_shutdown_dialog_help_text, powerNotificationWarnings11.new DialogInterfaceOnClickListenerC19322(string2));
                }
                systemUIDialog3.show();
                powerNotificationWarnings11.mThermalShutdownDialog = systemUIDialog3;
                return;
            }
            if ("PNW.dismissedThermalShutdownWarning".equals(action)) {
                PowerNotificationWarnings.this.dismissThermalShutdownWarning();
                return;
            }
            if ("PNW.autoSaverSuggestion".equals(action)) {
                PowerNotificationWarnings powerNotificationWarnings12 = PowerNotificationWarnings.this;
                powerNotificationWarnings12.mShowAutoSaverSuggestion = true;
                powerNotificationWarnings12.updateNotification();
                return;
            }
            if ("PNW.dismissAutoSaverSuggestion".equals(action)) {
                PowerNotificationWarnings powerNotificationWarnings13 = PowerNotificationWarnings.this;
                powerNotificationWarnings13.mShowAutoSaverSuggestion = false;
                powerNotificationWarnings13.updateNotification();
                return;
            }
            if (!"PNW.enableAutoSaver".equals(action)) {
                if ("PNW.autoSaverNoThanks".equals(action)) {
                    PowerNotificationWarnings powerNotificationWarnings14 = PowerNotificationWarnings.this;
                    powerNotificationWarnings14.mShowAutoSaverSuggestion = false;
                    powerNotificationWarnings14.updateNotification();
                    Settings.Secure.putInt(context.getContentResolver(), "suppress_auto_battery_saver_suggestion", 1);
                    return;
                }
                return;
            }
            PowerNotificationWarnings powerNotificationWarnings15 = PowerNotificationWarnings.this;
            powerNotificationWarnings15.mShowAutoSaverSuggestion = false;
            powerNotificationWarnings15.updateNotification();
            PowerNotificationWarnings powerNotificationWarnings16 = PowerNotificationWarnings.this;
            powerNotificationWarnings16.getClass();
            Intent intent2 = new Intent("com.android.settings.BATTERY_SAVER_SCHEDULE_SETTINGS");
            intent2.setFlags(268468224);
            powerNotificationWarnings16.mActivityStarter.startActivity(intent2, true);
        }

        private Receiver() {
        }
    }

    static {
        new AudioAttributes.Builder().setContentType(4).setUsage(13).build();
    }

    public PowerNotificationWarnings(Context context, ActivityStarter activityStarter, BroadcastSender broadcastSender, Lazy lazy, DialogLaunchAnimator dialogLaunchAnimator, UiEventLogger uiEventLogger, GlobalSettings globalSettings, UserTracker userTracker) {
        Receiver receiver = new Receiver(this, 0);
        this.mOpenBatterySettings = new Intent("android.intent.action.POWER_USAGE_SUMMARY").setFlags(1551892480);
        this.mOpenBatterySaverSettings = new Intent("android.settings.BATTERY_SAVER_SETTINGS").setFlags(1551892480);
        this.mContext = context;
        this.mNoMan = (NotificationManager) context.getSystemService(NotificationManager.class);
        this.mPowerMan = (PowerManager) context.getSystemService("power");
        this.mKeyguard = (KeyguardManager) context.getSystemService(KeyguardManager.class);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("PNW.batterySaverSettings");
        intentFilter.addAction("PNW.startSaver");
        intentFilter.addAction("PNW.dismissedWarning");
        intentFilter.addAction("PNW.clickedTempWarning");
        intentFilter.addAction("PNW.dismissedTempWarning");
        intentFilter.addAction("PNW.clickedThermalShutdownWarning");
        intentFilter.addAction("PNW.dismissedThermalShutdownWarning");
        intentFilter.addAction("PNW.startSaverConfirmation");
        intentFilter.addAction("PNW.autoSaverSuggestion");
        intentFilter.addAction("PNW.enableAutoSaver");
        intentFilter.addAction("PNW.autoSaverNoThanks");
        intentFilter.addAction("PNW.dismissAutoSaverSuggestion");
        PowerNotificationWarnings powerNotificationWarnings = PowerNotificationWarnings.this;
        powerNotificationWarnings.mContext.registerReceiverAsUser(receiver, UserHandle.ALL, intentFilter, "android.permission.DEVICE_POWER", powerNotificationWarnings.mHandler, 2);
        this.mActivityStarter = activityStarter;
        this.mBroadcastSender = broadcastSender;
        this.mBatteryControllerLazy = lazy;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        this.mUiEventLogger = uiEventLogger;
        this.mUserTracker = userTracker;
        this.mUseExtraSaverConfirmation = context.getResources().getBoolean(R.bool.config_extra_battery_saver_confirmation);
    }

    public final void dismissHighTemperatureWarningInternal() {
        this.mNoMan.cancelAsUser("high_temp", 4, UserHandle.ALL);
        this.mHighTempWarning = false;
    }

    public void dismissThermalShutdownWarning() {
        this.mNoMan.cancelAsUser("high_temp", 39, UserHandle.ALL);
    }

    public Dialog getSaverConfirmationDialog() {
        return this.mSaverConfirmation;
    }

    public final void logEvent(BatteryWarningEvents$LowBatteryWarningEvent batteryWarningEvents$LowBatteryWarningEvent) {
        UiEventLogger uiEventLogger = this.mUiEventLogger;
        if (uiEventLogger != null) {
            uiEventLogger.log(batteryWarningEvents$LowBatteryWarningEvent);
        }
    }

    public final PendingIntent pendingBroadcast(String str) {
        Intent intent = new Intent(str);
        Context context = this.mContext;
        return PendingIntent.getBroadcastAsUser(context, 0, intent.setPackage(context.getPackageName()).setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE), QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY, UserHandle.CURRENT);
    }

    public final void updateNotification() {
        if (DEBUG) {
            Slog.d("PowerUI.Notification", "updateNotification mWarning=false mPlaySound=false mInvalidCharger=" + this.mInvalidCharger);
        }
        boolean z = this.mInvalidCharger;
        Context context = this.mContext;
        NotificationManager notificationManager = this.mNoMan;
        if (z) {
            Notification.Builder color = new Notification.Builder(context, "ALR").setSmallIcon(R.drawable.ic_power_low).setWhen(0L).setShowWhen(false).setOngoing(true).setContentTitle(context.getString(R.string.invalid_charger_title)).setContentText(context.getString(R.string.invalid_charger_text)).setColor(context.getColor(android.R.color.system_notification_accent_color));
            SystemUIApplication.overrideNotificationAppName(context, color, false);
            Notification build = color.build();
            notificationManager.cancelAsUser("low_battery", 3, UserHandle.ALL);
            notificationManager.notifyAsUser("low_battery", 2, build, UserHandle.ALL);
            this.mShowing = 3;
            return;
        }
        if (!this.mShowAutoSaverSuggestion) {
            notificationManager.cancelAsUser("low_battery", 2, UserHandle.ALL);
            notificationManager.cancelAsUser("low_battery", 3, UserHandle.ALL);
            notificationManager.cancelAsUser("auto_saver", 49, UserHandle.ALL);
            this.mShowing = 0;
            return;
        }
        if (this.mShowing != 4) {
            String string = context.getString(R.string.auto_saver_text);
            Notification.Builder contentText = new Notification.Builder(context, "HNT").setSmallIcon(R.drawable.ic_power_saver).setWhen(0L).setShowWhen(false).setContentTitle(context.getString(R.string.auto_saver_title)).setStyle(new Notification.BigTextStyle().bigText(string)).setContentText(string);
            contentText.setContentIntent(pendingBroadcast("PNW.enableAutoSaver"));
            contentText.setDeleteIntent(pendingBroadcast("PNW.dismissAutoSaverSuggestion"));
            contentText.addAction(0, context.getString(R.string.no_auto_saver_action), pendingBroadcast("PNW.autoSaverNoThanks"));
            SystemUIApplication.overrideNotificationAppName(context, contentText, false);
            notificationManager.notifyAsUser("auto_saver", 49, contentText.build(), UserHandle.ALL);
        }
        this.mShowing = 4;
    }
}
