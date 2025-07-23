package com.android.internal.policy;

import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.FactoryTest;
import android.os.RemoteException;
import android.os.UserHandle;
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
import com.android.internal.R;
import com.samsung.android.core.CoreSaConstant;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.InputRune;
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

    private boolean isReserveBatteryMode() {
        return false;
    }

    public PhoneFallbackEventHandler(Context context) {
        this.mContext = context;
    }

    @Override // android.view.FallbackEventHandler
    public void setView(View view) {
        this.mView = view;
    }

    @Override // android.view.FallbackEventHandler
    public void preDispatchKeyEvent(KeyEvent keyEvent) {
        getAudioManager().preDispatchKeyEvent(keyEvent, Integer.MIN_VALUE);
    }

    @Override // android.view.FallbackEventHandler
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int action = keyEvent.getAction();
        int keyCode = keyEvent.getKeyCode();
        if (action == 0) {
            return onKeyDown(keyCode, keyEvent);
        }
        return onKeyUp(keyCode, keyEvent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    boolean onKeyDown(int i, KeyEvent keyEvent) {
        KeyEvent.DispatcherState keyDispatcherState = this.mView.getKeyDispatcherState();
        if (i != 24 && i != 25) {
            if (i != 126 && i != 127) {
                switch (i) {
                    case 5:
                        if (!isNotInstantAppAndKeyguardRestricted(keyDispatcherState)) {
                            if (keyEvent.getRepeatCount() == 0) {
                                keyDispatcherState.startTracking(keyEvent, this);
                                break;
                            } else if (keyEvent.isLongPress() && keyDispatcherState.isTracking(keyEvent)) {
                                keyDispatcherState.performedLongPress(keyEvent);
                                if (isUserSetupComplete()) {
                                    this.mView.performHapticFeedback(0);
                                    Intent intent = new Intent(Intent.ACTION_VOICE_COMMAND);
                                    intent.setFlags(268435456);
                                    try {
                                        this.mContext.startActivity(intent);
                                        break;
                                    } catch (ActivityNotFoundException unused) {
                                        startCallActivity();
                                        break;
                                    }
                                } else {
                                    Log.i(TAG, "Not starting call activity because user setup is in progress.");
                                    break;
                                }
                            }
                        }
                        break;
                    case 27:
                        if (!isNotInstantAppAndKeyguardRestricted(keyDispatcherState)) {
                            if (keyEvent.getRepeatCount() == 0) {
                                keyDispatcherState.startTracking(keyEvent, this);
                                break;
                            } else if (keyEvent.isLongPress() && keyDispatcherState.isTracking(keyEvent)) {
                                keyDispatcherState.performedLongPress(keyEvent);
                                if (isUserSetupComplete()) {
                                    this.mView.performHapticFeedback(0);
                                    Intent intent2 = new Intent(Intent.ACTION_CAMERA_BUTTON, (Uri) null);
                                    intent2.addFlags(268435456);
                                    intent2.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
                                    this.mContext.sendOrderedBroadcastAsUser(intent2, UserHandle.CURRENT_OR_SELF, null, null, null, 0, null, null);
                                    break;
                                } else {
                                    Log.i(TAG, "Not dispatching CAMERA long press because user setup is in progress.");
                                    break;
                                }
                            }
                        }
                        break;
                    case 79:
                    case 130:
                    case 222:
                        break;
                    case 164:
                        break;
                    case 1002:
                        if (keyDispatcherState != null && keyEvent.getRepeatCount() <= 0 && !getKeyguardManager().isKeyguardLocked()) {
                            Intent intent3 = new Intent(Intent.ACTION_MAIN);
                            intent3.addCategory(Intent.CATEGORY_HOME);
                            intent3.setFlags(268435456);
                            intent3.putExtra(EXTRA_LAUNCHER_ACTION, LAUNCHER_ACTION_ALL_APPS);
                            try {
                                this.mContext.startActivity(intent3);
                                break;
                            } catch (ActivityNotFoundException e) {
                                Log.w(TAG, "No activity to launch launcher app list. ", e);
                                break;
                            }
                        }
                        break;
                    case 1006:
                        if (keyDispatcherState != null && keyEvent.getRepeatCount() <= 0 && !getKeyguardManager().isKeyguardLocked()) {
                            getInputMethodManager().toggleSoftInput(0, 0);
                            break;
                        }
                        break;
                    case 1008:
                        if (keyDispatcherState != null && keyEvent.getRepeatCount() <= 0 && !getKeyguardManager().isKeyguardLocked()) {
                            Intent makeMainSelectorActivity = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_EMAIL);
                            makeMainSelectorActivity.addFlags(268435456);
                            makeMainSelectorActivity.addFlags(8388608);
                            try {
                                this.mContext.startActivity(makeMainSelectorActivity);
                                break;
                            } catch (ActivityNotFoundException e2) {
                                Log.w(TAG, "No activity to launch email", e2);
                                break;
                            }
                        }
                        break;
                    case 1013:
                        if (keyDispatcherState != null && keyEvent.getRepeatCount() <= 0 && !getKeyguardManager().isKeyguardLocked()) {
                            Intent intent4 = new Intent();
                            intent4.setClassName(PACKAGE_NAME_MESSAGING, CLASS_NAME_MESSAGING);
                            intent4.addFlags(268435456);
                            intent4.addFlags(8388608);
                            try {
                                this.mContext.startActivity(intent4);
                                break;
                            } catch (ActivityNotFoundException e3) {
                                Log.w(TAG, "No activity to launch mms ConversationComposer.", e3);
                                break;
                            }
                        }
                        break;
                    case 1015:
                    case 1079:
                        if ((InputRune.PWM_ACTIVE_OR_XCOVER_KEY || InputRune.PWM_XCOVER_AND_TOP_KEY) && keyDispatcherState != null && !isFactoryMode() && isUserSetupComplete()) {
                            int repeatCount = keyEvent.getRepeatCount();
                            boolean isLongPress = keyEvent.isLongPress();
                            boolean isTracking = keyDispatcherState.isTracking(keyEvent);
                            if (InputRune.SAFE_DEBUG) {
                                Log.d(TAG, "onKeyDown, keycode=" + i + " repeatCount=" + repeatCount + " isLongPress=" + isLongPress + " isTracking=" + isTracking);
                            }
                            if (repeatCount != 0) {
                                if (isLongPress && isTracking) {
                                    keyDispatcherState.performedLongPress(keyEvent);
                                    this.mView.performHapticFeedback(0);
                                    launchUserDefinedApp(1, i);
                                    break;
                                }
                            } else {
                                keyDispatcherState.startTracking(keyEvent, this);
                                this.mPressType = 0;
                                break;
                            }
                        }
                        break;
                    default:
                        switch (i) {
                            case 84:
                                if (!isNotInstantAppAndKeyguardRestricted(keyDispatcherState)) {
                                    if (keyEvent.getRepeatCount() == 0) {
                                        keyDispatcherState.startTracking(keyEvent, this);
                                    } else if (keyEvent.isLongPress() && keyDispatcherState.isTracking(keyEvent)) {
                                        Configuration configuration = this.mContext.getResources().getConfiguration();
                                        if (configuration.keyboard == 1 || configuration.hardKeyboardHidden == 2) {
                                            if (isUserSetupComplete()) {
                                                Intent intent5 = new Intent(Intent.ACTION_SEARCH_LONG_PRESS);
                                                intent5.setFlags(268435456);
                                                try {
                                                    this.mView.performHapticFeedback(0);
                                                    getSearchManager().stopSearch();
                                                    this.mContext.startActivity(intent5);
                                                    keyDispatcherState.performedLongPress(keyEvent);
                                                    break;
                                                } catch (ActivityNotFoundException unused2) {
                                                    return false;
                                                }
                                            } else {
                                                Log.i(TAG, "Not dispatching SEARCH long press because user setup is in progress.");
                                            }
                                        }
                                    }
                                }
                                break;
                        }
                }
                return true;
            }
            handleMediaKeyEvent(keyEvent);
            return true;
        }
        handleVolumeKeyEvent(keyEvent);
        return true;
        return true;
        return true;
        return true;
        return true;
    }

    private boolean isNotInstantAppAndKeyguardRestricted(KeyEvent.DispatcherState dispatcherState) {
        if (this.mContext.getPackageManager().isInstantApp()) {
            return false;
        }
        return getKeyguardManager().inKeyguardRestrictedInputMode() || dispatcherState == null;
    }

    boolean onKeyUp(int i, KeyEvent keyEvent) {
        KeyEvent.DispatcherState keyDispatcherState = this.mView.getKeyDispatcherState();
        if (keyDispatcherState != null) {
            keyDispatcherState.handleUpEvent(keyEvent);
        }
        if (i == 5) {
            if (isNotInstantAppAndKeyguardRestricted(keyDispatcherState)) {
                return false;
            }
            if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                if (isUserSetupComplete()) {
                    startCallActivity();
                } else {
                    Log.i(TAG, "Not starting call activity because user setup is in progress.");
                }
            }
            return true;
        }
        if (i != 27) {
            if (i != 79 && i != 130) {
                if (i != 164) {
                    if (i != 222) {
                        if (i == 1015 || i == 1079) {
                            if (InputRune.PWM_ACTIVE_OR_XCOVER_KEY || InputRune.PWM_XCOVER_AND_TOP_KEY) {
                                if (isFactoryMode() || !isUserSetupComplete()) {
                                    return false;
                                }
                                if (InputRune.SAFE_DEBUG) {
                                    Log.d(TAG, "onKeyUp, keyCode=" + i + " press=" + this.mPressType + " event.isCanceled()=" + keyEvent.isCanceled());
                                }
                                if (this.mPressType == 0) {
                                    if (!keyEvent.isCanceled()) {
                                        launchUserDefinedApp(this.mPressType, i);
                                    }
                                    this.mPressType = -1;
                                }
                            }
                            return true;
                        }
                        if (i != 24 && i != 25) {
                            if (i != 126 && i != 127) {
                                switch (i) {
                                    case 85:
                                    case 86:
                                    case 87:
                                    case 88:
                                    case 89:
                                    case 90:
                                        break;
                                    default:
                                        return false;
                                }
                            }
                        }
                    }
                }
                if (!keyEvent.isCanceled()) {
                    handleVolumeKeyEvent(keyEvent);
                }
                return true;
            }
            handleMediaKeyEvent(keyEvent);
            return true;
        }
        if (isNotInstantAppAndKeyguardRestricted(keyDispatcherState)) {
            return false;
        }
        if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
            if (isUserSetupComplete()) {
                launchCamera();
            } else {
                Log.i(TAG, "Not starting camera activity because user setup is in progress.");
            }
        }
        return true;
    }

    void startCallActivity() {
        Intent intent = new Intent(Intent.ACTION_CALL_BUTTON);
        intent.setFlags(268435456);
        try {
            this.mContext.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Log.w(TAG, "No activity found for android.intent.action.CALL_BUTTON.");
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
        } else {
            intent.addFlags(2097152);
        }
        try {
            this.mContext.startActivityAsUser(intent, UserHandle.CURRENT_OR_SELF);
        } catch (ActivityNotFoundException e) {
            Log.w(TAG, "No activity to launch Camera.", e);
        }
        InputMethodManager inputMethodManager = getInputMethodManager();
        if (inputMethodManager != null) {
            inputMethodManager.forceHideSoftInput();
        }
    }

    private boolean launchUserDefinedApp(int i, int i2) {
        SemWindowManager.KeyCustomizationInfo keyCustomizationInfo;
        Log.d(TAG, "xcover key press type=" + i);
        try {
            IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
            if (i == 0) {
                keyCustomizationInfo = windowManagerService.getLastKeyCustomizationInfo(3, i2);
            } else {
                if (i != 1) {
                    return false;
                }
                keyCustomizationInfo = windowManagerService.getLastKeyCustomizationInfo(4, i2);
            }
        } catch (RemoteException e) {
            Log.d(TAG, "Can not read keyCustomizeEvent" + e);
            keyCustomizationInfo = null;
        }
        if (keyCustomizationInfo == null || keyCustomizationInfo.intent == null) {
            Log.d(TAG, "xcover/top key info is empty");
            return false;
        }
        if (keyCustomizationInfo.action != 1) {
            Log.d(TAG, "xcover/top key action of info is wrong");
            return false;
        }
        ComponentName component = keyCustomizationInfo.intent.getComponent();
        if (component == null) {
            Log.d(TAG, "xcover/top key componentName is empty");
            return false;
        }
        if (COMPONENT_NAME_CAMERA.equals(component.flattenToString())) {
            if (!getKeyguardManager().isKeyguardLocked() || isXCoverKeyOnLockScreen(i2)) {
                launchCamera();
            }
            if (InputRune.PWM_KEY_SA_LOGGING) {
                sendSaLogging(i, i2, CoreSaConstant.VALUE_CAMERA);
            }
            return true;
        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(component);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(270532608);
        startActivityForXCoverTopKey(intent, i2);
        if (InputRune.PWM_KEY_SA_LOGGING) {
            sendSaLogging(i, i2, component.getPackageName());
        }
        return true;
    }

    private boolean showToastIfNeeded(Intent intent, String str, int i) {
        if (TextUtils.isEmpty(str) || intent == null) {
            Log.d(TAG, "packageName or intent is empty. " + str + ", " + intent);
            return false;
        }
        String toastString = i == 1 ? getToastString(getApplicationInfo(intent, str), R.string.reserve_battery_mode_start_activity_disabled) : null;
        if (TextUtils.isEmpty(toastString)) {
            return false;
        }
        Toast.makeText(new ContextThemeWrapper(this.mContext, 16974123), toastString, 0).show();
        return true;
    }

    private String getToastString(ApplicationInfo applicationInfo, int i) {
        if (applicationInfo == null) {
            return null;
        }
        return String.format(this.mContext.getString(i), getApplicationLabel(applicationInfo));
    }

    private String getApplicationLabel(ApplicationInfo applicationInfo) {
        return this.mContext.getPackageManager().getApplicationLabel(applicationInfo).toString();
    }

    private ApplicationInfo getApplicationInfo(Intent intent, String str) {
        PackageManager packageManager = this.mContext.getPackageManager();
        ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 0);
        if (resolveActivity != null && resolveActivity.activityInfo != null) {
            return null;
        }
        Log.d(TAG, "Can not start activity because app is not added in reserveBatteryMode");
        try {
            return packageManager.getApplicationInfo(str, 0);
        } catch (Exception e) {
            Log.d(TAG, "failed getApplicationInfo, " + e);
            return null;
        }
    }

    private void startActivityForXCoverTopKey(Intent intent, int i) {
        Log.d(TAG, "startActivityForXCoverTopKey keyCode=" + i);
        if (getKeyguardManager().isKeyguardLocked()) {
            if (isXCoverKeyOnLockScreen(i)) {
                Intent intent2 = new Intent();
                intent2.putExtra(AFTER_KEYGUARD_GONE, true);
                getKeyguardManager().semSetPendingIntentAfterUnlock(PendingIntent.getActivityAsUser(this.mContext, 0, intent, 201326592, null, UserHandle.CURRENT_OR_SELF), intent2);
                return;
            }
            return;
        }
        try {
            this.mContext.startActivityAsUser(intent, UserHandle.CURRENT_OR_SELF);
        } catch (ActivityNotFoundException e) {
            Log.w(TAG, "No activity to launch on XCover Key.", e);
        }
    }

    private boolean isXCoverKeyOnLockScreen(int i) {
        String str;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (i == 1079) {
            str = "xcover_top_key_on_lockscreen";
        } else {
            str = "active_key_on_lockscreen";
        }
        return Settings.System.getIntForUser(contentResolver, str, 0, -3) == 1;
    }

    private boolean isFactoryMode() {
        if (!InputRune.PWM_KEY_FACTORY_MODE_POLICY && !FactoryTest.isRunningFactoryApp() && !FactoryTest.isAutomaticTestMode(this.mContext)) {
            return false;
        }
        Log.d(TAG, "Block launchUserDefinedApp because of Factory binary, test mode or Factory app.");
        return true;
    }

    private void sendSaLogging(int i, int i2, String str) {
        String eventId = getEventId(i, i2);
        if (TextUtils.isEmpty(eventId)) {
            return;
        }
        HashMap hashMap = new HashMap();
        hashMap.put(CoreSaLogger.DETAIL_KEY, str);
        CoreSaLogger.logForBasic(eventId, (HashMap<String, String>) hashMap);
    }

    private String getEventId(int i, int i2) {
        if (i2 == 1015) {
            if (i == 0) {
                return CoreSaConstant.KEY_XCOVER_SHORT_PRESS;
            }
            if (i == 1) {
                return CoreSaConstant.KEY_XCOVER_LONG_PRESS;
            }
            return null;
        }
        if (i2 != 1079) {
            return null;
        }
        if (i == 0) {
            return CoreSaConstant.KEY_TOP_SHORT_PRESS;
        }
        if (i == 1) {
            return CoreSaConstant.KEY_TOP_LONG_PRESS;
        }
        return null;
    }
}
