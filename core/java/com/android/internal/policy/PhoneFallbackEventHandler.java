package com.android.internal.policy;

import android.app.ActivityTaskManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ResolveInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.audio.Enums;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.p009os.FactoryTest;
import android.p009os.RemoteException;
import android.p009os.UserHandle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.FallbackEventHandler;
import android.view.IWindowManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManagerGlobal;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.android.internal.C4337R;
import com.samsung.android.core.CoreSaConstant;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.view.SemWindowManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;

/* loaded from: classes5.dex */
public class PhoneFallbackEventHandler implements FallbackEventHandler {
    private static final String AFTER_KEYGUARD_GONE = "afterKeyguardGone";
    private static final String CLASS_NAME_MESSAGING = "com.android.mms.ui.ConversationComposer";
    private static final String COMPONENT_NAME_CAMERA = "com.sec.android.app.camera/com.sec.android.app.camera.Camera";
    private static final boolean DEBUG = false;
    private static final String EXTRA_IS_QUICK_LAUNCH_MODE = "isQuickLaunchMode";
    private static final String EXTRA_IS_SECURE = "isSecure";
    private static final String EXTRA_LAUNCHER_ACTION = "sec.android.intent.extra.LAUNCHER_ACTION";
    private static final String LAUNCHER_ACTION_ALL_APPS = "com.android.launcher2.ALL_APPS";
    private static final String PACKAGE_NAME_MESSAGING = "com.samsung.android.messaging";
    private static final int RESERVE_BATTERY_MODE_KEY_TOAST = 1;
    private static String TAG = "PhoneFallbackEventHandler";
    AudioManager mAudioManager;
    Context mContext;
    KeyguardManager mKeyguardManager;
    MediaSessionManager mMediaSessionManager;
    SearchManager mSearchManager;
    TelephonyManager mTelephonyManager;
    View mView;
    private InputMethodManager mInputMethodManager = null;
    private int mPressType = -1;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PressType {
        public static final int LONG_PRESS = 1;
        public static final int NONE = -1;
        public static final int SHORT_PRESS = 0;
    }

    public PhoneFallbackEventHandler(Context context) {
        this.mContext = context;
    }

    @Override // android.view.FallbackEventHandler
    public void setView(View v) {
        this.mView = v;
    }

    @Override // android.view.FallbackEventHandler
    public void preDispatchKeyEvent(KeyEvent event) {
        getAudioManager().preDispatchKeyEvent(event, Integer.MIN_VALUE);
    }

    @Override // android.view.FallbackEventHandler
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        if (action == 0) {
            return onKeyDown(keyCode, event);
        }
        return onKeyUp(keyCode, event);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    boolean onKeyDown(int keyCode, KeyEvent event) {
        KeyEvent.DispatcherState dispatcher = this.mView.getKeyDispatcherState();
        switch (keyCode) {
            case 5:
                if (!isNotInstantAppAndKeyguardRestricted(dispatcher)) {
                    if (event.getRepeatCount() == 0) {
                        dispatcher.startTracking(event, this);
                    } else if (event.isLongPress() && dispatcher.isTracking(event)) {
                        dispatcher.performedLongPress(event);
                        if (isUserSetupComplete()) {
                            this.mView.performHapticFeedback(0);
                            Intent intent = new Intent(Intent.ACTION_VOICE_COMMAND);
                            intent.setFlags(268435456);
                            try {
                                this.mContext.startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                startCallActivity();
                            }
                        } else {
                            Log.m98i(TAG, "Not starting call activity because user setup is in progress.");
                        }
                    }
                    return true;
                }
                return false;
            case 24:
            case 25:
            case 164:
                handleVolumeKeyEvent(event);
                return true;
            case 27:
                if (!isNotInstantAppAndKeyguardRestricted(dispatcher)) {
                    if (event.getRepeatCount() == 0) {
                        dispatcher.startTracking(event, this);
                    } else if (event.isLongPress() && dispatcher.isTracking(event)) {
                        dispatcher.performedLongPress(event);
                        if (isUserSetupComplete()) {
                            this.mView.performHapticFeedback(0);
                            Intent intent2 = new Intent(Intent.ACTION_CAMERA_BUTTON, (Uri) null);
                            intent2.addFlags(268435456);
                            intent2.putExtra(Intent.EXTRA_KEY_EVENT, event);
                            this.mContext.sendOrderedBroadcastAsUser(intent2, UserHandle.CURRENT_OR_SELF, null, null, null, 0, null, null);
                        } else {
                            Log.m98i(TAG, "Not dispatching CAMERA long press because user setup is in progress.");
                        }
                    }
                    return true;
                }
                return false;
            case 79:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 126:
            case 127:
            case 130:
            case 222:
                handleMediaKeyEvent(event);
                return true;
            case 84:
                if (!isNotInstantAppAndKeyguardRestricted(dispatcher)) {
                    if (event.getRepeatCount() == 0) {
                        dispatcher.startTracking(event, this);
                    } else if (event.isLongPress() && dispatcher.isTracking(event)) {
                        Configuration config = this.mContext.getResources().getConfiguration();
                        if (config.keyboard == 1 || config.hardKeyboardHidden == 2) {
                            if (isUserSetupComplete()) {
                                Intent intent3 = new Intent(Intent.ACTION_SEARCH_LONG_PRESS);
                                intent3.setFlags(268435456);
                                try {
                                    this.mView.performHapticFeedback(0);
                                    getSearchManager().stopSearch();
                                    this.mContext.startActivity(intent3);
                                    dispatcher.performedLongPress(event);
                                    return true;
                                } catch (ActivityNotFoundException e2) {
                                }
                            } else {
                                Log.m98i(TAG, "Not dispatching SEARCH long press because user setup is in progress.");
                            }
                        }
                    }
                }
                return false;
            case 1002:
                if (dispatcher != null && event.getRepeatCount() <= 0 && !getKeyguardManager().isKeyguardLocked()) {
                    Intent intent4 = new Intent(Intent.ACTION_MAIN);
                    intent4.addCategory(Intent.CATEGORY_HOME);
                    intent4.setFlags(268435456);
                    intent4.putExtra(EXTRA_LAUNCHER_ACTION, LAUNCHER_ACTION_ALL_APPS);
                    try {
                        this.mContext.startActivity(intent4);
                    } catch (ActivityNotFoundException e3) {
                        Log.m103w(TAG, "No activity to launch launcher app list. ", e3);
                    }
                    return true;
                }
                return false;
            case 1006:
                if (dispatcher != null && event.getRepeatCount() <= 0 && !getKeyguardManager().isKeyguardLocked()) {
                    getInputMethodManager().toggleSoftInput(0, 0);
                    return true;
                }
                return false;
            case 1008:
                if (dispatcher != null && event.getRepeatCount() <= 0 && !getKeyguardManager().isKeyguardLocked()) {
                    Intent intent5 = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_EMAIL);
                    intent5.addFlags(268435456);
                    intent5.addFlags(8388608);
                    try {
                        this.mContext.startActivity(intent5);
                    } catch (ActivityNotFoundException e4) {
                        Log.m103w(TAG, "No activity to launch email", e4);
                    }
                    return true;
                }
                return false;
            case 1013:
                if (dispatcher != null && event.getRepeatCount() <= 0 && !getKeyguardManager().isKeyguardLocked()) {
                    Intent intent6 = new Intent();
                    intent6.setClassName(PACKAGE_NAME_MESSAGING, CLASS_NAME_MESSAGING);
                    intent6.addFlags(268435456);
                    intent6.addFlags(8388608);
                    try {
                        this.mContext.startActivity(intent6);
                    } catch (ActivityNotFoundException e5) {
                        Log.m103w(TAG, "No activity to launch mms ConversationComposer.", e5);
                    }
                    return true;
                }
                return false;
            case 1015:
            case 1079:
                if ((CoreRune.FW_ACTIVE_OR_XCOVER_KEY || CoreRune.FW_XCOVER_AND_TOP_KEY) && dispatcher != null && !isFactoryMode() && isUserSetupComplete()) {
                    int repeatCount = event.getRepeatCount();
                    boolean longPress = event.isLongPress();
                    boolean isTracking = dispatcher.isTracking(event);
                    if (CoreRune.SAFE_DEBUG) {
                        Log.m94d(TAG, "onKeyDown, keycode=" + keyCode + " repeatCount=" + repeatCount + " isLongPress=" + longPress + " isTracking=" + isTracking);
                    }
                    if (repeatCount == 0) {
                        dispatcher.startTracking(event, this);
                        this.mPressType = 0;
                    } else if (longPress && isTracking) {
                        dispatcher.performedLongPress(event);
                        this.mView.performHapticFeedback(0);
                        launchUserDefinedApp(1, keyCode);
                    }
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    private boolean isNotInstantAppAndKeyguardRestricted(KeyEvent.DispatcherState dispatcher) {
        return !this.mContext.getPackageManager().isInstantApp() && (getKeyguardManager().inKeyguardRestrictedInputMode() || dispatcher == null);
    }

    boolean onKeyUp(int keyCode, KeyEvent event) {
        if (CoreRune.SAFE_DEBUG) {
            Log.m94d(TAG, "up " + keyCode);
        }
        KeyEvent.DispatcherState dispatcher = this.mView.getKeyDispatcherState();
        if (dispatcher != null) {
            dispatcher.handleUpEvent(event);
        }
        switch (keyCode) {
            case 5:
                if (!isNotInstantAppAndKeyguardRestricted(dispatcher)) {
                    if (event.isTracking() && !event.isCanceled()) {
                        if (isUserSetupComplete()) {
                            startCallActivity();
                            break;
                        } else {
                            Log.m98i(TAG, "Not starting call activity because user setup is in progress.");
                            break;
                        }
                    }
                }
                break;
            case 24:
            case 25:
            case 164:
                if (!event.isCanceled()) {
                    handleVolumeKeyEvent(event);
                    break;
                }
                break;
            case 27:
                if (!isNotInstantAppAndKeyguardRestricted(dispatcher)) {
                    if (event.isTracking() && !event.isCanceled()) {
                        if (isUserSetupComplete()) {
                            launchCamera();
                            break;
                        } else {
                            Log.m98i(TAG, "Not starting camera activity because user setup is in progress.");
                            break;
                        }
                    }
                }
                break;
            case 79:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 126:
            case 127:
            case 130:
            case 222:
                handleMediaKeyEvent(event);
                break;
            case 1015:
            case 1079:
                if (CoreRune.FW_ACTIVE_OR_XCOVER_KEY || CoreRune.FW_XCOVER_AND_TOP_KEY) {
                    if (!isFactoryMode() && isUserSetupComplete()) {
                        if (CoreRune.SAFE_DEBUG) {
                            Log.m94d(TAG, "onKeyUp, keyCode=" + keyCode + " press=" + this.mPressType + " event.isCanceled()=" + event.isCanceled());
                        }
                        if (this.mPressType == 0) {
                            if (!event.isCanceled()) {
                                launchUserDefinedApp(this.mPressType, keyCode);
                            }
                            this.mPressType = -1;
                            break;
                        }
                    }
                }
                break;
        }
        return true;
    }

    void startCallActivity() {
        Intent intent = new Intent(Intent.ACTION_CALL_BUTTON);
        intent.setFlags(268435456);
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.m102w(TAG, "No activity found for android.intent.action.CALL_BUTTON.");
        }
    }

    SearchManager getSearchManager() {
        if (this.mSearchManager == null) {
            this.mSearchManager = (SearchManager) this.mContext.getSystemService("search");
        }
        return this.mSearchManager;
    }

    TelephonyManager getTelephonyManager() {
        if (this.mTelephonyManager == null) {
            this.mTelephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
        }
        return this.mTelephonyManager;
    }

    KeyguardManager getKeyguardManager() {
        if (this.mKeyguardManager == null) {
            this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService(Context.KEYGUARD_SERVICE);
        }
        return this.mKeyguardManager;
    }

    AudioManager getAudioManager() {
        if (this.mAudioManager == null) {
            this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        }
        return this.mAudioManager;
    }

    MediaSessionManager getMediaSessionManager() {
        if (this.mMediaSessionManager == null) {
            this.mMediaSessionManager = (MediaSessionManager) this.mContext.getSystemService(Context.MEDIA_SESSION_SERVICE);
        }
        return this.mMediaSessionManager;
    }

    private void handleVolumeKeyEvent(KeyEvent keyEvent) {
        getMediaSessionManager().dispatchVolumeKeyEventAsSystemService(keyEvent, Integer.MIN_VALUE);
    }

    private void handleMediaKeyEvent(KeyEvent keyEvent) {
        getMediaSessionManager().dispatchMediaKeyEventAsSystemService(keyEvent);
    }

    private boolean isUserSetupComplete() {
        return Settings.Secure.getInt(this.mContext.getContentResolver(), Settings.Secure.USER_SETUP_COMPLETE, 0) != 0;
    }

    private boolean isReserveBatteryMode() {
        ContentResolver resolver = this.mContext.getContentResolver();
        if (CoreRune.FW_SUPPORT_DOWNLOADABLE_RESERVE_BATTERY_MODE) {
            return Settings.Secure.getInt(resolver, "reserve_battery_on", 0) == 1 && Settings.Secure.getInt(resolver, "enable_reserve_max_mode", 0) == 1;
        }
        if (CoreRune.FW_SUPPORT_RESERVE_BATTERY_MODE) {
            return Settings.System.getInt(resolver, "reserve_battery_on", 0) == 1 && Settings.System.getInt(resolver, "enable_reserve_max_mode", 0) == 1;
        }
        return false;
    }

    private static class UndefinedSettingNames {
        static final String ENABLE_RESERVE_MAX_MODE = "enable_reserve_max_mode";
        static final String RESERVE_BATTERY_ON = "reserve_battery_on";
        static final String TOP_KEY_ON_LOCKSCREEN = "xcover_top_key_on_lockscreen";
        static final String XCOVER_KEY_ON_LOCKSCREEN = "active_key_on_lockscreen";

        private UndefinedSettingNames() {
        }
    }

    private InputMethodManager getInputMethodManager() {
        if (this.mInputMethodManager == null) {
            this.mInputMethodManager = (InputMethodManager) this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        return this.mInputMethodManager;
    }

    private void launchCamera() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(ComponentName.unflattenFromString(COMPONENT_NAME_CAMERA));
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.putExtra(EXTRA_IS_SECURE, getKeyguardManager().isKeyguardSecure());
        intent.putExtra(EXTRA_IS_QUICK_LAUNCH_MODE, true);
        intent.setFlags(268435456);
        if (getKeyguardManager().semIsKeyguardShowingAndNotOccluded()) {
            intent.addFlags(32768);
            try {
                ActivityTaskManager.getService().requestWaitingForOccluding(this.mContext.getDisplayId());
            } catch (RemoteException e) {
                Log.m94d(TAG, "Can not requestWaitingForOccluding, " + e);
            }
        } else {
            intent.addFlags(2097152);
        }
        try {
            this.mContext.startActivityAsUser(intent, UserHandle.CURRENT_OR_SELF);
        } catch (ActivityNotFoundException e2) {
            Log.m103w(TAG, "No activity to launch Camera.", e2);
        }
        InputMethodManager inputMethodManager = getInputMethodManager();
        if (inputMethodManager != null) {
            inputMethodManager.forceHideSoftInput();
        }
    }

    private boolean launchUserDefinedApp(int pressType, int keyCode) {
        Log.m94d(TAG, "xcover key press type=" + pressType);
        SemWindowManager.KeyCustomizationInfo info = null;
        try {
            IWindowManager windowManager = WindowManagerGlobal.getWindowManagerService();
            switch (pressType) {
                case 0:
                    info = windowManager.getLastKeyCustomizationInfo(3, keyCode);
                    break;
                case 1:
                    info = windowManager.getLastKeyCustomizationInfo(4, keyCode);
                    break;
                default:
                    return false;
            }
        } catch (RemoteException e) {
            Log.m94d(TAG, "Can not read keyCustomizeEvent" + e);
        }
        if (info == null || info.intent == null) {
            Log.m94d(TAG, "xcover/top key info is empty");
            return false;
        }
        if (info.action != 1) {
            Log.m94d(TAG, "xcover/top key action of info is wrong");
            return false;
        }
        ComponentName componentName = info.intent.getComponent();
        if (componentName == null) {
            Log.m94d(TAG, "xcover/top key componentName is empty");
            return false;
        }
        if (COMPONENT_NAME_CAMERA.equals(componentName.flattenToString())) {
            if (!getKeyguardManager().isKeyguardLocked() || isXCoverKeyOnLockScreen(keyCode)) {
                launchCamera();
            }
            if (CoreRune.FW_KEY_SA_LOGGING) {
                sendSaLogging(pressType, keyCode, CoreSaConstant.VALUE_CAMERA);
            }
            return true;
        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(componentName);
        if (CoreRune.FW_SUPPORT_RESERVE_BATTERY_MODE && isReserveBatteryMode() && showToastIfNeeded(intent, componentName.getPackageName(), 1)) {
            return true;
        }
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(270532608);
        startActivityForXCoverTopKey(intent, keyCode);
        if (CoreRune.FW_KEY_SA_LOGGING) {
            sendSaLogging(pressType, keyCode, componentName.getPackageName());
        }
        return true;
    }

    private boolean showToastIfNeeded(Intent intent, String packageName, int keyToastMode) {
        if (TextUtils.isEmpty(packageName) || intent == null) {
            String toastString = TAG;
            Log.m94d(toastString, "packageName or intent is empty. " + packageName + ", " + intent);
            return false;
        }
        String toastString2 = null;
        if (keyToastMode == 1) {
            toastString2 = getToastString(getApplicationInfo(intent, packageName), C4337R.string.reserve_battery_mode_start_activity_disabled);
        }
        if (TextUtils.isEmpty(toastString2)) {
            return false;
        }
        Toast.makeText(new ContextThemeWrapper(this.mContext, 16974123), toastString2, 0).show();
        return true;
    }

    private String getToastString(ApplicationInfo applicationInfo, int resId) {
        if (applicationInfo == null) {
            return null;
        }
        return String.format(this.mContext.getString(resId), getApplicationLabel(applicationInfo));
    }

    private String getApplicationLabel(ApplicationInfo applicationInfo) {
        return this.mContext.getPackageManager().getApplicationLabel(applicationInfo).toString();
    }

    private ApplicationInfo getApplicationInfo(Intent intent, String packageName) {
        PackageManager packageManager = this.mContext.getPackageManager();
        ResolveInfo rInfo = packageManager.resolveActivity(intent, 0);
        if (rInfo != null && rInfo.activityInfo != null) {
            return null;
        }
        Log.m94d(TAG, "Can not start activity because app is not added in reserveBatteryMode");
        try {
            return packageManager.getApplicationInfo(packageName, 0);
        } catch (Exception e) {
            Log.m94d(TAG, "failed getApplicationInfo, " + e);
            return null;
        }
    }

    private void startActivityForXCoverTopKey(Intent intent, int keyCode) {
        Log.m94d(TAG, "startActivityForXCoverTopKey keyCode=" + keyCode);
        if (getKeyguardManager().isKeyguardLocked()) {
            if (isXCoverKeyOnLockScreen(keyCode)) {
                Intent fillInIntent = new Intent();
                fillInIntent.putExtra(AFTER_KEYGUARD_GONE, true);
                PendingIntent pendingIntent = PendingIntent.getActivityAsUser(this.mContext, 0, intent, Enums.AUDIO_FORMAT_DTS_HD, null, UserHandle.CURRENT_OR_SELF);
                getKeyguardManager().semSetPendingIntentAfterUnlock(pendingIntent, fillInIntent);
                return;
            }
            return;
        }
        try {
            this.mContext.startActivityAsUser(intent, UserHandle.CURRENT_OR_SELF);
        } catch (ActivityNotFoundException e) {
            Log.m103w(TAG, "No activity to launch on XCover Key.", e);
        }
    }

    private boolean isXCoverKeyOnLockScreen(int keyCode) {
        String str;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (keyCode == 1079) {
            str = "xcover_top_key_on_lockscreen";
        } else {
            str = "active_key_on_lockscreen";
        }
        return Settings.System.getIntForUser(contentResolver, str, 0, -3) == 1;
    }

    boolean isFactoryMode() {
        if (CoreRune.IS_FACTORY_BINARY || FactoryTest.isAutomaticTestMode(this.mContext) || FactoryTest.isRunningFactoryApp()) {
            Log.m94d(TAG, "Block launchUserDefinedApp because of Factory binary, test mode or Factory app.");
            return true;
        }
        return false;
    }

    private void sendSaLogging(int pressType, int keyCode, String detail) {
        String eventId = getEventId(pressType, keyCode);
        if (TextUtils.isEmpty(eventId)) {
            return;
        }
        HashMap<String, String> customDimension = new HashMap<>();
        customDimension.put(CoreSaLogger.DETAIL_KEY, detail);
        CoreSaLogger.logForBasic(eventId, customDimension);
    }

    private String getEventId(int press, int keyCode) {
        switch (keyCode) {
            case 1015:
                if (press == 0) {
                    return CoreSaConstant.KEY_XCOVER_SHORT_PRESS;
                }
                if (press == 1) {
                    return CoreSaConstant.KEY_XCOVER_LONG_PRESS;
                }
                return null;
            case 1079:
                if (press == 0) {
                    return CoreSaConstant.KEY_TOP_SHORT_PRESS;
                }
                if (press == 1) {
                    return CoreSaConstant.KEY_TOP_LONG_PRESS;
                }
                return null;
            default:
                return null;
        }
    }
}
