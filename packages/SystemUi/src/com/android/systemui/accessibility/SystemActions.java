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
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.StatusBarWindowCallback;
import com.android.systemui.util.Assert;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import dagger.Lazy;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SystemActions implements CoreStartable {
    public final AccessibilityManager mA11yManager;
    public final Lazy mCentralSurfacesOptionalLazy;
    public final Context mContext;
    public boolean mDismissNotificationShadeActionRegistered;
    public final DisplayTracker mDisplayTracker;
    public Locale mLocale;
    public final NotificationShadeWindowController mNotificationShadeController;
    public final Optional mRecentsOptional;
    public final ShadeController mShadeController;
    public final UserTracker mUserTracker;
    public final SystemActionsBroadcastReceiver mReceiver = new SystemActionsBroadcastReceiver(this, 0);
    public final StatusBarWindowCallback mNotificationShadeCallback = new StatusBarWindowCallback() { // from class: com.android.systemui.accessibility.SystemActions$$ExternalSyntheticLambda1
        @Override // com.android.systemui.statusbar.phone.StatusBarWindowCallback
        public final void onStateChanged(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
            SystemActions.this.registerOrUnregisterDismissNotificationShadeAction(z, z6);
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SystemActionsBroadcastReceiver extends BroadcastReceiver {
        public static final /* synthetic */ int $r8$clinit = 0;

        public /* synthetic */ SystemActionsBroadcastReceiver(SystemActions systemActions, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            final int i;
            final int i2;
            final int i3;
            String action = intent.getAction();
            action.getClass();
            i = 2;
            i2 = 1;
            i3 = 0;
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
                    ((Optional) SystemActions.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.accessibility.SystemActions$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            switch (i2) {
                                case 0:
                                    ((CentralSurfacesImpl) ((CentralSurfaces) obj)).mCommandQueueCallbacks.animateExpandSettingsPanel(null);
                                    break;
                                case 1:
                                    ((CentralSurfacesImpl) ((CentralSurfaces) obj)).mCommandQueueCallbacks.animateExpandNotificationsPanel();
                                    break;
                                default:
                                    ((Recents) obj).toggleRecentApps();
                                    break;
                            }
                        }
                    });
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
                    SystemActions systemActions2 = SystemActions.this;
                    systemActions2.getClass();
                    systemActions2.mRecentsOptional.ifPresent(new Consumer() { // from class: com.android.systemui.accessibility.SystemActions$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            switch (i) {
                                case 0:
                                    ((CentralSurfacesImpl) ((CentralSurfaces) obj)).mCommandQueueCallbacks.animateExpandSettingsPanel(null);
                                    break;
                                case 1:
                                    ((CentralSurfacesImpl) ((CentralSurfaces) obj)).mCommandQueueCallbacks.animateExpandNotificationsPanel();
                                    break;
                                default:
                                    ((Recents) obj).toggleRecentApps();
                                    break;
                            }
                        }
                    });
                    break;
                case "SYSTEM_ACTION_ACCESSIBILITY_BUTTON_MENU":
                    SystemActions systemActions3 = SystemActions.this;
                    systemActions3.getClass();
                    Intent intent2 = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                    intent2.addFlags(268468224);
                    intent2.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                    systemActions3.mContext.startActivityAsUser(intent2, ((UserTrackerImpl) systemActions3.mUserTracker).getUserHandle());
                    break;
                case "SYSTEM_ACTION_DPAD_CENTER":
                    SystemActions.this.sendDownAndUpKeyEvents(23);
                    break;
                case "SYSTEM_ACTION_DPAD_UP":
                    SystemActions.this.sendDownAndUpKeyEvents(19);
                    break;
                case "SYSTEM_ACTION_ACCESSIBILITY_BUTTON":
                    SystemActions systemActions4 = SystemActions.this;
                    AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(systemActions4.mContext);
                    systemActions4.mDisplayTracker.getClass();
                    accessibilityManager.notifyAccessibilityButtonClicked(0);
                    break;
                case "SYSTEM_ACTION_HEADSET_HOOK":
                    SystemActions.this.handleHeadsetHook();
                    break;
                case "SYSTEM_ACTION_TAKE_SCREENSHOT":
                    SystemActions systemActions5 = SystemActions.this;
                    systemActions5.getClass();
                    new ScreenshotHelper(systemActions5.mContext).takeScreenshot(4, new Handler(Looper.getMainLooper()), (Consumer) null);
                    break;
                case "SYSTEM_ACTION_QUICK_SETTINGS":
                    ((Optional) SystemActions.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.accessibility.SystemActions$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            switch (i3) {
                                case 0:
                                    ((CentralSurfacesImpl) ((CentralSurfaces) obj)).mCommandQueueCallbacks.animateExpandSettingsPanel(null);
                                    break;
                                case 1:
                                    ((CentralSurfacesImpl) ((CentralSurfaces) obj)).mCommandQueueCallbacks.animateExpandNotificationsPanel();
                                    break;
                                default:
                                    ((Recents) obj).toggleRecentApps();
                                    break;
                            }
                        }
                    });
                    break;
                case "SYSTEM_ACTION_DPAD_RIGHT":
                    SystemActions.this.sendDownAndUpKeyEvents(22);
                    break;
                case "SYSTEM_ACTION_ACCESSIBILITY_DISMISS_NOTIFICATION_SHADE":
                    ((ShadeControllerImpl) SystemActions.this.mShadeController).animateCollapseShade(0);
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

    public SystemActions(Context context, UserTracker userTracker, NotificationShadeWindowController notificationShadeWindowController, ShadeController shadeController, Lazy lazy, Optional<Recents> optional, DisplayTracker displayTracker) {
        this.mContext = context;
        this.mUserTracker = userTracker;
        this.mShadeController = shadeController;
        this.mRecentsOptional = optional;
        this.mDisplayTracker = displayTracker;
        this.mLocale = context.getResources().getConfiguration().getLocales().get(0);
        this.mA11yManager = (AccessibilityManager) context.getSystemService("accessibility");
        this.mNotificationShadeController = notificationShadeWindowController;
        this.mCentralSurfacesOptionalLazy = lazy;
    }

    public final RemoteAction createRemoteAction(int i, String str) {
        Context context;
        PendingIntent broadcast;
        context = this.mContext;
        Icon createWithResource = Icon.createWithResource(context, R.drawable.ic_info);
        String string = context.getString(i);
        String string2 = context.getString(i);
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

    @Override // com.android.systemui.CoreStartable
    public final void onConfigurationChanged(Configuration configuration) {
        Locale locale = this.mContext.getResources().getConfiguration().getLocales().get(0);
        if (locale.equals(this.mLocale)) {
            return;
        }
        this.mLocale = locale;
        registerActions();
    }

    public final void registerActions() {
        RemoteAction createRemoteAction = createRemoteAction(R.string.auto_data_switch_title, "SYSTEM_ACTION_BACK");
        RemoteAction createRemoteAction2 = createRemoteAction(R.string.autofill_save_notnow, "SYSTEM_ACTION_HOME");
        RemoteAction createRemoteAction3 = createRemoteAction(R.string.autofill_save_type_debit_card, "SYSTEM_ACTION_RECENTS");
        RemoteAction createRemoteAction4 = createRemoteAction(R.string.autofill_save_title_with_2types, "SYSTEM_ACTION_NOTIFICATIONS");
        RemoteAction createRemoteAction5 = createRemoteAction(R.string.autofill_save_type_credit_card, "SYSTEM_ACTION_QUICK_SETTINGS");
        RemoteAction createRemoteAction6 = createRemoteAction(R.string.autofill_save_type_address, "SYSTEM_ACTION_POWER_DIALOG");
        RemoteAction createRemoteAction7 = createRemoteAction(R.string.autofill_save_title, "SYSTEM_ACTION_LOCK_SCREEN");
        RemoteAction createRemoteAction8 = createRemoteAction(R.string.autofill_save_type_email_address, "SYSTEM_ACTION_TAKE_SCREENSHOT");
        RemoteAction createRemoteAction9 = createRemoteAction(R.string.autofill_save_no, "SYSTEM_ACTION_HEADSET_HOOK");
        RemoteAction createRemoteAction10 = createRemoteAction(R.string.autofill_save_never, "SYSTEM_ACTION_ACCESSIBILITY_SHORTCUT");
        RemoteAction createRemoteAction11 = createRemoteAction(R.string.autofill_save_accessibility_title, "SYSTEM_ACTION_DPAD_UP");
        RemoteAction createRemoteAction12 = createRemoteAction(R.string.autofill_picker_accessibility_title, "SYSTEM_ACTION_DPAD_DOWN");
        RemoteAction createRemoteAction13 = createRemoteAction(R.string.autofill_picker_no_suggestions, "SYSTEM_ACTION_DPAD_LEFT");
        RemoteAction createRemoteAction14 = createRemoteAction(R.string.autofill_picker_some_suggestions, "SYSTEM_ACTION_DPAD_RIGHT");
        RemoteAction createRemoteAction15 = createRemoteAction(R.string.autofill_error_cannot_autofill, "SYSTEM_ACTION_DPAD_CENTER");
        AccessibilityManager accessibilityManager = this.mA11yManager;
        accessibilityManager.registerSystemAction(createRemoteAction, 1);
        accessibilityManager.registerSystemAction(createRemoteAction2, 2);
        accessibilityManager.registerSystemAction(createRemoteAction3, 3);
        Lazy lazy = this.mCentralSurfacesOptionalLazy;
        if (((Optional) lazy.get()).isPresent()) {
            accessibilityManager.registerSystemAction(createRemoteAction4, 4);
            accessibilityManager.registerSystemAction(createRemoteAction5, 5);
        }
        accessibilityManager.registerSystemAction(createRemoteAction6, 6);
        accessibilityManager.registerSystemAction(createRemoteAction7, 8);
        accessibilityManager.registerSystemAction(createRemoteAction8, 9);
        accessibilityManager.registerSystemAction(createRemoteAction9, 10);
        accessibilityManager.registerSystemAction(createRemoteAction10, 13);
        accessibilityManager.registerSystemAction(createRemoteAction11, 16);
        accessibilityManager.registerSystemAction(createRemoteAction12, 17);
        accessibilityManager.registerSystemAction(createRemoteAction13, 18);
        accessibilityManager.registerSystemAction(createRemoteAction14, 19);
        accessibilityManager.registerSystemAction(createRemoteAction15, 20);
        Optional optional = (Optional) lazy.get();
        registerOrUnregisterDismissNotificationShadeAction(((CentralSurfacesImpl) ((CentralSurfaces) optional.get())).isKeyguardShowing(), ((Boolean) optional.map(new SystemActions$$ExternalSyntheticLambda0()).orElse(Boolean.FALSE)).booleanValue());
    }

    public final void registerOrUnregisterDismissNotificationShadeAction(boolean z, boolean z2) {
        Assert.isMainThread();
        AccessibilityManager accessibilityManager = this.mA11yManager;
        if (!z2 || z) {
            if (this.mDismissNotificationShadeActionRegistered) {
                accessibilityManager.unregisterSystemAction(15);
                this.mDismissNotificationShadeActionRegistered = false;
                return;
            }
            return;
        }
        if (this.mDismissNotificationShadeActionRegistered) {
            return;
        }
        accessibilityManager.registerSystemAction(createRemoteAction(R.string.autofill_continue_yes, "SYSTEM_ACTION_ACCESSIBILITY_DISMISS_NOTIFICATION_SHADE"), 15);
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
