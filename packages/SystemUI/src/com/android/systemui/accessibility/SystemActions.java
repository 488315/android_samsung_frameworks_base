package com.android.systemui.accessibility;

import android.R;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.Icon;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.view.IWindowManager;
import android.view.KeyEvent;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.util.ScreenshotHelper;
import com.android.systemui.CoreStartable;
import com.android.systemui.recents.Recents;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.BaseShadeControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.domain.interactor.PanelExpansionInteractor;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.StatusBarWindowCallback;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.Assert;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import dagger.Lazy;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SystemActions implements CoreStartable, ConfigurationController.ConfigurationListener {
    public final AccessibilityManager mA11yManager;
    public final Context mContext;
    public boolean mDismissNotificationShadeActionRegistered;
    public final DisplayTracker mDisplayTracker;
    public final KeyguardStateController mKeyguardStateController;
    public Locale mLocale;
    public final NotificationShadeWindowController mNotificationShadeController;
    public final Lazy mPanelExpansionInteractor;
    public final Optional mRecentsOptional;
    public final ScreenshotHelper mScreenshotHelper;
    public final ShadeController mShadeController;
    public final UserTracker mUserTracker;
    public final SystemActionsBroadcastReceiver mReceiver = new SystemActionsBroadcastReceiver(this, 0);
    public final StatusBarWindowCallback mNotificationShadeCallback = new StatusBarWindowCallback() { // from class: com.android.systemui.accessibility.SystemActions$$ExternalSyntheticLambda0
        @Override // com.android.systemui.statusbar.phone.StatusBarWindowCallback
        public final void onStateChanged(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
            SystemActions.this.registerOrUnregisterDismissNotificationShadeAction();
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SystemActionsBroadcastReceiver extends BroadcastReceiver {
        public static final /* synthetic */ int $r8$clinit = 0;

        public /* synthetic */ SystemActionsBroadcastReceiver(SystemActions systemActions, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            action.getClass();
            switch (action) {
                case "SYSTEM_ACTION_BACK":
                    SystemActions.this.sendDownAndUpKeyEvents(4);
                    break;
                case "SYSTEM_ACTION_HOME":
                    SystemActions.this.sendDownAndUpKeyEvents(3);
                    break;
                case "SYSTEM_ACTION_POWER_DIALOG":
                    SystemActions.this.getClass();
                    try {
                        WindowManagerGlobal.getWindowManagerService().showGlobalActions();
                        break;
                    } catch (RemoteException unused) {
                        Log.e("SystemActions", "failed to display power dialog.");
                        return;
                    }
                case "SYSTEM_ACTION_NOTIFICATIONS":
                    ((BaseShadeControllerImpl) SystemActions.this.mShadeController).animateExpandShade();
                    break;
                case "SYSTEM_ACTION_ACCESSIBILITY_SHORTCUT":
                    SystemActions.this.mA11yManager.performAccessibilityShortcut();
                    break;
                case "SYSTEM_ACTION_LOCK_SCREEN":
                    SystemActions systemActions = SystemActions.this;
                    systemActions.getClass();
                    IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
                    ((PowerManager) systemActions.mContext.getSystemService(PowerManager.class)).goToSleep(SystemClock.uptimeMillis(), 7, 0);
                    try {
                        windowManagerService.lockNow((Bundle) null);
                        break;
                    } catch (RemoteException unused2) {
                        Log.e("SystemActions", "failed to lock screen.");
                        return;
                    }
                case "SYSTEM_ACTION_RECENTS":
                    SystemActions.this.mRecentsOptional.ifPresent(new SystemActions$$ExternalSyntheticLambda1());
                    break;
                case "SYSTEM_ACTION_ACCESSIBILITY_BUTTON_MENU":
                    SystemActions systemActions2 = SystemActions.this;
                    systemActions2.getClass();
                    Intent intent2 = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                    intent2.addFlags(268468224);
                    intent2.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                    systemActions2.mContext.startActivityAsUser(intent2, ((UserTrackerImpl) systemActions2.mUserTracker).getUserHandle());
                    break;
                case "SYSTEM_ACTION_DPAD_CENTER":
                    SystemActions.this.sendDownAndUpKeyEvents(23);
                    break;
                case "SYSTEM_ACTION_DPAD_UP":
                    SystemActions.this.sendDownAndUpKeyEvents(19);
                    break;
                case "SYSTEM_ACTION_ACCESSIBILITY_BUTTON":
                    SystemActions systemActions3 = SystemActions.this;
                    AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(systemActions3.mContext);
                    systemActions3.mDisplayTracker.getClass();
                    accessibilityManager.notifyAccessibilityButtonClicked(0);
                    break;
                case "SYSTEM_ACTION_HEADSET_HOOK":
                    SystemActions.this.handleHeadsetHook();
                    break;
                case "SYSTEM_ACTION_TAKE_SCREENSHOT":
                    SystemActions.this.mScreenshotHelper.takeScreenshot(4, new Handler(Looper.getMainLooper()), (Consumer) null);
                    break;
                case "SYSTEM_ACTION_QUICK_SETTINGS":
                    ((BaseShadeControllerImpl) SystemActions.this.mShadeController).animateExpandQs();
                    break;
                case "SYSTEM_ACTION_DPAD_RIGHT":
                    SystemActions.this.sendDownAndUpKeyEvents(22);
                    break;
                case "SYSTEM_ACTION_ACCESSIBILITY_DISMISS_NOTIFICATION_SHADE":
                    SystemActions.this.mShadeController.animateCollapseShade(0);
                    break;
                case "SYSTEM_ACTION_DPAD_DOWN":
                    SystemActions.this.sendDownAndUpKeyEvents(20);
                    break;
                case "SYSTEM_ACTION_DPAD_LEFT":
                    SystemActions.this.sendDownAndUpKeyEvents(21);
                    break;
            }
        }

        private SystemActionsBroadcastReceiver() {
        }
    }

    public SystemActions(Context context, UserTracker userTracker, NotificationShadeWindowController notificationShadeWindowController, KeyguardStateController keyguardStateController, ShadeController shadeController, Lazy lazy, Optional<Recents> optional, DisplayTracker displayTracker) {
        this.mContext = context;
        this.mUserTracker = userTracker;
        this.mKeyguardStateController = keyguardStateController;
        this.mShadeController = shadeController;
        this.mPanelExpansionInteractor = lazy;
        this.mRecentsOptional = optional;
        this.mDisplayTracker = displayTracker;
        this.mLocale = context.getResources().getConfiguration().getLocales().get(0);
        this.mA11yManager = (AccessibilityManager) context.getSystemService("accessibility");
        this.mNotificationShadeController = notificationShadeWindowController;
        this.mScreenshotHelper = new ScreenshotHelper(context);
    }

    public final RemoteAction createRemoteAction(int i, String str) {
        Context context;
        PendingIntent broadcast;
        Icon createWithResource = Icon.createWithResource(this.mContext, R.drawable.ic_info);
        String string = this.mContext.getString(i);
        String string2 = this.mContext.getString(i);
        context = this.mContext;
        int i2 = SystemActionsBroadcastReceiver.$r8$clinit;
        this.mReceiver.getClass();
        switch (str) {
            case "SYSTEM_ACTION_BACK":
            case "SYSTEM_ACTION_HOME":
            case "SYSTEM_ACTION_POWER_DIALOG":
            case "SYSTEM_ACTION_NOTIFICATIONS":
            case "SYSTEM_ACTION_ACCESSIBILITY_SHORTCUT":
            case "SYSTEM_ACTION_LOCK_SCREEN":
            case "SYSTEM_ACTION_RECENTS":
            case "SYSTEM_ACTION_ACCESSIBILITY_BUTTON_MENU":
            case "SYSTEM_ACTION_DPAD_CENTER":
            case "SYSTEM_ACTION_DPAD_UP":
            case "SYSTEM_ACTION_ACCESSIBILITY_BUTTON":
            case "SYSTEM_ACTION_HEADSET_HOOK":
            case "SYSTEM_ACTION_TAKE_SCREENSHOT":
            case "SYSTEM_ACTION_QUICK_SETTINGS":
            case "SYSTEM_ACTION_DPAD_RIGHT":
            case "SYSTEM_ACTION_ACCESSIBILITY_DISMISS_NOTIFICATION_SHADE":
            case "SYSTEM_ACTION_DPAD_DOWN":
            case "SYSTEM_ACTION_DPAD_LEFT":
                Intent intent = new Intent(str);
                intent.setPackage(context.getPackageName());
                intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                broadcast = PendingIntent.getBroadcast(context, 0, intent, QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY);
                break;
            default:
                broadcast = null;
                break;
        }
        return new RemoteAction(createWithResource, string, string2, broadcast);
    }

    public void handleHeadsetHook() {
        if (AccessibilityUtils.interceptHeadsetHookForActiveCall(this.mContext)) {
            return;
        }
        sendDownAndUpKeyEvents(79);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        Locale locale = this.mContext.getResources().getConfiguration().getLocales().get(0);
        if (locale.equals(this.mLocale)) {
            return;
        }
        this.mLocale = locale;
        registerActions();
    }

    public final void registerActions() {
        RemoteAction createRemoteAction = createRemoteAction(R.string.autofill_save_title, "SYSTEM_ACTION_BACK");
        RemoteAction createRemoteAction2 = createRemoteAction(R.string.autofill_save_type_password, "SYSTEM_ACTION_HOME");
        RemoteAction createRemoteAction3 = createRemoteAction(R.string.autofill_update_title_with_type, "SYSTEM_ACTION_RECENTS");
        RemoteAction createRemoteAction4 = createRemoteAction(R.string.autofill_save_type_username, "SYSTEM_ACTION_NOTIFICATIONS");
        RemoteAction createRemoteAction5 = createRemoteAction(R.string.autofill_update_title_with_3types, "SYSTEM_ACTION_QUICK_SETTINGS");
        RemoteAction createRemoteAction6 = createRemoteAction(R.string.autofill_update_title_with_2types, "SYSTEM_ACTION_POWER_DIALOG");
        RemoteAction createRemoteAction7 = createRemoteAction(R.string.autofill_save_type_payment_card, "SYSTEM_ACTION_LOCK_SCREEN");
        RemoteAction createRemoteAction8 = createRemoteAction(R.string.autofill_update_yes, "SYSTEM_ACTION_TAKE_SCREENSHOT");
        RemoteAction createRemoteAction9 = createRemoteAction(R.string.autofill_save_type_generic_card, "SYSTEM_ACTION_HEADSET_HOOK");
        RemoteAction createRemoteAction10 = createRemoteAction(R.string.autofill_save_type_email_address, "SYSTEM_ACTION_ACCESSIBILITY_SHORTCUT");
        RemoteAction createRemoteAction11 = createRemoteAction(R.string.autofill_save_type_debit_card, "SYSTEM_ACTION_DPAD_UP");
        RemoteAction createRemoteAction12 = createRemoteAction(R.string.autofill_save_title_with_type, "SYSTEM_ACTION_DPAD_DOWN");
        RemoteAction createRemoteAction13 = createRemoteAction(R.string.autofill_save_type_address, "SYSTEM_ACTION_DPAD_LEFT");
        RemoteAction createRemoteAction14 = createRemoteAction(R.string.autofill_save_type_credit_card, "SYSTEM_ACTION_DPAD_RIGHT");
        RemoteAction createRemoteAction15 = createRemoteAction(R.string.autofill_save_title_with_3types, "SYSTEM_ACTION_DPAD_CENTER");
        this.mA11yManager.registerSystemAction(createRemoteAction, 1);
        this.mA11yManager.registerSystemAction(createRemoteAction2, 2);
        this.mA11yManager.registerSystemAction(createRemoteAction3, 3);
        if (this.mShadeController.isShadeEnabled()) {
            this.mA11yManager.registerSystemAction(createRemoteAction4, 4);
            this.mA11yManager.registerSystemAction(createRemoteAction5, 5);
        }
        this.mA11yManager.registerSystemAction(createRemoteAction6, 6);
        this.mA11yManager.registerSystemAction(createRemoteAction7, 8);
        this.mA11yManager.registerSystemAction(createRemoteAction8, 9);
        this.mA11yManager.registerSystemAction(createRemoteAction9, 10);
        this.mA11yManager.registerSystemAction(createRemoteAction10, 13);
        this.mA11yManager.registerSystemAction(createRemoteAction11, 16);
        this.mA11yManager.registerSystemAction(createRemoteAction12, 17);
        this.mA11yManager.registerSystemAction(createRemoteAction13, 18);
        this.mA11yManager.registerSystemAction(createRemoteAction14, 19);
        this.mA11yManager.registerSystemAction(createRemoteAction15, 20);
        registerOrUnregisterDismissNotificationShadeAction();
    }

    public final void registerOrUnregisterDismissNotificationShadeAction() {
        Assert.isMainThread();
        if (!((PanelExpansionInteractor) this.mPanelExpansionInteractor.get()).isPanelExpanded() || ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            if (this.mDismissNotificationShadeActionRegistered) {
                this.mA11yManager.unregisterSystemAction(15);
                this.mDismissNotificationShadeActionRegistered = false;
                return;
            }
            return;
        }
        if (this.mDismissNotificationShadeActionRegistered) {
            return;
        }
        this.mA11yManager.registerSystemAction(createRemoteAction(R.string.autofill_save_title_with_2types, "SYSTEM_ACTION_ACCESSIBILITY_DISMISS_NOTIFICATION_SHADE"), 15);
        this.mDismissNotificationShadeActionRegistered = true;
    }

    public final void sendDownAndUpKeyEvents(int i) {
        long uptimeMillis = SystemClock.uptimeMillis();
        sendKeyEventIdentityCleared(i, 0, uptimeMillis, uptimeMillis);
        sendKeyEventIdentityCleared(i, 1, uptimeMillis, SystemClock.uptimeMillis());
    }

    public final void sendKeyEventIdentityCleared(int i, int i2, long j, long j2) {
        KeyEvent obtain = KeyEvent.obtain(j, j2, i2, i, 0, 0, -1, 0, 8, 257, null);
        ((InputManager) this.mContext.getSystemService(InputManager.class)).injectInputEvent(obtain, 0);
        obtain.recycle();
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        ((NotificationShadeWindowControllerImpl) this.mNotificationShadeController).registerCallback(this.mNotificationShadeCallback);
        Context context = this.mContext;
        int i = SystemActionsBroadcastReceiver.$r8$clinit;
        SystemActionsBroadcastReceiver systemActionsBroadcastReceiver = this.mReceiver;
        systemActionsBroadcastReceiver.getClass();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("SYSTEM_ACTION_BACK");
        intentFilter.addAction("SYSTEM_ACTION_HOME");
        intentFilter.addAction("SYSTEM_ACTION_RECENTS");
        intentFilter.addAction("SYSTEM_ACTION_NOTIFICATIONS");
        intentFilter.addAction("SYSTEM_ACTION_QUICK_SETTINGS");
        intentFilter.addAction("SYSTEM_ACTION_POWER_DIALOG");
        intentFilter.addAction("SYSTEM_ACTION_LOCK_SCREEN");
        intentFilter.addAction("SYSTEM_ACTION_TAKE_SCREENSHOT");
        intentFilter.addAction("SYSTEM_ACTION_HEADSET_HOOK");
        intentFilter.addAction("SYSTEM_ACTION_ACCESSIBILITY_BUTTON");
        intentFilter.addAction("SYSTEM_ACTION_ACCESSIBILITY_BUTTON_MENU");
        intentFilter.addAction("SYSTEM_ACTION_ACCESSIBILITY_SHORTCUT");
        intentFilter.addAction("SYSTEM_ACTION_ACCESSIBILITY_DISMISS_NOTIFICATION_SHADE");
        intentFilter.addAction("SYSTEM_ACTION_DPAD_UP");
        intentFilter.addAction("SYSTEM_ACTION_DPAD_DOWN");
        intentFilter.addAction("SYSTEM_ACTION_DPAD_LEFT");
        intentFilter.addAction("SYSTEM_ACTION_DPAD_RIGHT");
        intentFilter.addAction("SYSTEM_ACTION_DPAD_CENTER");
        context.registerReceiverForAllUsers(systemActionsBroadcastReceiver, intentFilter, "com.android.systemui.permission.SELF", null, 2);
        registerActions();
    }
}
