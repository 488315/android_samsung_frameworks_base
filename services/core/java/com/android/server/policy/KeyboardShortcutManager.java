package com.android.server.policy;

import android.app.ActivityOptions;
import android.app.StatusBarManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.KeyEvent;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.rune.CoreRune;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes3.dex */
public class KeyboardShortcutManager {
    public static final int[] SHORT_TYPE_LIST = {0, 1, 2, 3, 4};
    public final Context mContext;
    public final PhoneWindowManagerExt mPolicyExt;
    public final HashMap mShortcutMap = new HashMap();
    public final SparseArray mPreloadBehaviorMap = new SparseArray();
    public boolean mIsTriggeredMeta = false;
    public boolean mIsConsumedMeta = false;

    public String getSaLoggerEventId(int i) {
        if (i == 41) {
            return "PKBD0062";
        }
        if (i == 44) {
            return "PKBD0064";
        }
        if (i == 54) {
            return "PKBD0021";
        }
        if (i == 46) {
            return "PKBD0015";
        }
        if (i == 47) {
            return "PKBD0065";
        }
        switch (i) {
            case 29:
                return "PKBD0056";
            case 30:
                return "PKBD0057";
            case 31:
                return "PKBD0058";
            case 32:
                return "PKBD0008";
            case 33:
                return "PKBD0059";
            case 34:
                return "PKBD0009";
            default:
                switch (i) {
                    case 36:
                        return "PKBD0060";
                    case 37:
                        return "PKBD0061";
                    case 38:
                        return "PKBD0011";
                    case 39:
                        return "PKBD0012";
                    default:
                        return null;
                }
        }
    }

    public String getShortcutSettingsValue(int i) {
        return Settings.System.getStringForUser(this.mContext.getContentResolver(), (String) this.mShortcutMap.get(Integer.valueOf(i)), -2);
    }

    public KeyboardShortcutManager(Context context, PhoneWindowManagerExt phoneWindowManagerExt) {
        this.mContext = context;
        this.mPolicyExt = phoneWindowManagerExt;
    }

    public void init() {
        Slog.d("KeyboardShortcutManager", "init()");
        preloadBehavior();
        preloadSettings();
    }

    public final void preloadBehavior() {
        for (int i : SHORT_TYPE_LIST) {
            this.mPreloadBehaviorMap.put(i, getBehavior(i));
        }
    }

    public final void preloadSettings() {
        this.mShortcutMap.put(29, "app_shortcuts_command_a");
        this.mShortcutMap.put(30, "app_shortcuts_command_b");
        this.mShortcutMap.put(31, "app_shortcuts_command_c");
        this.mShortcutMap.put(32, "app_shortcuts_command_d");
        this.mShortcutMap.put(33, "app_shortcuts_command_e");
        this.mShortcutMap.put(34, "app_shortcuts_command_f");
        this.mShortcutMap.put(36, "app_shortcuts_command_h");
        this.mShortcutMap.put(37, "app_shortcuts_command_i");
        this.mShortcutMap.put(38, "app_shortcuts_command_j");
        this.mShortcutMap.put(39, "app_shortcuts_command_k");
        this.mShortcutMap.put(41, "app_shortcuts_command_m");
        this.mShortcutMap.put(44, "app_shortcuts_command_p");
        this.mShortcutMap.put(46, "app_shortcuts_command_r");
        this.mShortcutMap.put(47, "app_shortcuts_command_s");
        this.mShortcutMap.put(54, "app_shortcuts_command_z");
    }

    public boolean interceptKeyBeforeDispatching(KeyEvent keyEvent, boolean z, int i) {
        int keyCode = keyEvent.getKeyCode();
        int i2 = 1;
        boolean z2 = keyEvent.getAction() == 0;
        int repeatCount = keyEvent.getRepeatCount();
        if (!isSupportShortcut(keyCode)) {
            if (z2 && repeatCount == 0) {
                reset();
            }
            return false;
        }
        int modifiers = keyEvent.getModifiers();
        if (z && KeyEvent.metaStateHasModifiers(modifiers, 65536)) {
            this.mIsTriggeredMeta = true;
            Log.d("KeyboardShortcutManager", "interceptKeyTi, triggered");
        } else if (!this.mIsTriggeredMeta) {
            return false;
        }
        if (repeatCount != 0) {
            return this.mIsConsumedMeta;
        }
        if (!z2) {
            if (KeyCustomizationConstants.isSafeDebugInput()) {
                Log.d("KeyboardShortcutManager", "interceptKeyTi, up, triggered=" + this.mIsTriggeredMeta + " consumed=" + this.mIsConsumedMeta);
            }
            this.mIsTriggeredMeta = false;
            if (!this.mIsConsumedMeta) {
                return false;
            }
            this.mIsConsumedMeta = false;
            return true;
        }
        String shortcutSettingsValue = getShortcutSettingsValue(keyCode);
        if (TextUtils.isEmpty(shortcutSettingsValue)) {
            if (KeyCustomizationConstants.isSafeDebugInput()) {
                Log.d("KeyboardShortcutManager", "shortcutSetting(" + keyCode + ") is empty");
            }
            return false;
        }
        if ("None".equals(shortcutSettingsValue)) {
            if (KeyCustomizationConstants.isSafeDebugInput()) {
                Log.d("KeyboardShortcutManager", "shortcutSetting(" + keyCode + ") is none");
            }
            return true;
        }
        if (!shortcutSettingsValue.contains("android.intent.category.")) {
            if (shortcutSettingsValue.contains("android.app.role.")) {
                i2 = 2;
            } else if ("com.sec.android.app.launcher/com.sec.android.app.launcher.search.SearchActivity".equals(shortcutSettingsValue)) {
                i2 = 3;
            } else if ("com.android.settings".equals(shortcutSettingsValue) && keyCode == 37) {
                i2 = 4;
            } else {
                i2 = shortcutSettingsValue.contains(KnoxCustomManagerService.LAUNCHER_PACKAGE) ? 5 : 0;
            }
        }
        return launchCustomizationShortcut(i, i2, shortcutSettingsValue, keyCode);
    }

    public abstract class Behavior {
        public Context mContext;
        public Intent mIntent;
        public PhoneWindowManagerExt mPolicyExt;

        public Behavior(Context context, PhoneWindowManagerExt phoneWindowManagerExt) {
            this.mContext = context;
            this.mPolicyExt = phoneWindowManagerExt;
        }

        public Intent getIntent() {
            return this.mIntent;
        }

        public boolean launch(int i) {
            if (preCondition()) {
                return false;
            }
            startTargetApp(i);
            return true;
        }

        public boolean preCondition() {
            return this.mPolicyExt.externalKeyboardPolicy();
        }

        public void startTargetApp(int i) {
            Log.d("KeyboardShortcutManager", "launch keyboard shortcut app, displayId=" + i);
            Intent fillInIntent = this.mPolicyExt.getFillInIntent();
            if (this.mPolicyExt.showCoverToast(fillInIntent, this.mIntent)) {
                PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
                phoneWindowManagerExt.setPendingIntentAfterUnlock(phoneWindowManagerExt.getPendingIntentActivityAsUser(this.mIntent, UserHandle.CURRENT), fillInIntent);
                return;
            }
            ActivityOptions makeBasic = ActivityOptions.makeBasic();
            makeBasic.setLaunchDisplayId(i);
            this.mContext.startActivityAsUser(this.mIntent, makeBasic.toBundle(), UserHandle.CURRENT);
            this.mPolicyExt.mPolicy.sendCloseSystemWindows();
            this.mPolicyExt.mPolicy.dismissKeyboardShortcutsMenu();
        }

        public String toString() {
            return "Behavior=" + getClass().getSimpleName() + " intent=" + this.mIntent;
        }
    }

    public boolean launch(int i, int i2) {
        return launch(i, i2, null);
    }

    public boolean launch(int i, String str) {
        return launch(i, 0, str);
    }

    public final boolean launch(int i, int i2, String str) {
        Behavior preloadBehaviorMap = getPreloadBehaviorMap(i2);
        Log.d("KeyboardShortcutManager", "launch type=" + typeToString(i2) + " componentName=" + str + " " + preloadBehaviorMap);
        if (getIntent(i2, preloadBehaviorMap.getIntent(), str) == null) {
            Log.d("KeyboardShortcutManager", "Can not launch app, intent is null");
            return false;
        }
        return preloadBehaviorMap.launch(i);
    }

    public boolean launchCustomizationShortcut(int i, int i2, String str, int i3) {
        if (i2 == 1) {
            Log.v("KeyboardShortcutManager", "launch type=category shortcutSetting=" + str);
            return false;
        }
        if (i2 == 2) {
            Log.v("KeyboardShortcutManager", "launch type=role shortcutSetting=" + str);
            return false;
        }
        if (i2 == 4) {
            Log.v("KeyboardShortcutManager", "launch type=settings shortcutSetting=" + str + " keyCode=" + i3);
            return false;
        }
        if (i2 == 5) {
            Log.v("KeyboardShortcutManager", "launch type=HOME shortcutSetting=" + str + " keyCode=" + i3);
            this.mPolicyExt.shortPressOnHome(i);
            return true;
        }
        Behavior behavior = getBehavior(i2, str);
        Log.d("KeyboardShortcutManager", "launch type=" + typeToString(i2) + " shortcutSetting= " + str + " " + behavior);
        if (!behavior.launch(i)) {
            return false;
        }
        if (CoreRune.FW_KEY_SA_LOGGING) {
            HashMap hashMap = new HashMap();
            hashMap.put("det", str);
            String saLoggerEventId = getSaLoggerEventId(i3);
            if (!TextUtils.isEmpty(saLoggerEventId)) {
                CoreSaLogger.logForBasic(saLoggerEventId, hashMap);
            }
        }
        this.mIsConsumedMeta = true;
        Log.d("KeyboardShortcutManager", "consumed");
        return true;
    }

    public final Behavior getPreloadBehaviorMap(int i) {
        Behavior behavior = (Behavior) this.mPreloadBehaviorMap.get(i);
        return behavior == null ? getBehavior(i) : behavior;
    }

    public final Intent getIntent(int i, Intent intent, String str) {
        if (intent == null) {
            Log.d("KeyboardShortcutManager", "Intent is null");
            return null;
        }
        if (i != 0) {
            if (i == 1) {
                ComponentName topActivity = this.mPolicyExt.getTopActivity();
                if (topActivity == null) {
                    Slog.d("KeyboardShortcutManager", "META_F, Unknown top activity!");
                    return null;
                }
                Slog.d("KeyboardShortcutManager", "META_F, top=" + topActivity.toShortString());
                intent.putExtra("componentname", topActivity.toString());
            }
        } else {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            intent.setComponent(ComponentName.unflattenFromString(str));
        }
        return intent;
    }

    public class DefaultBehavior extends Behavior {
        public DefaultBehavior(Context context, PhoneWindowManagerExt phoneWindowManagerExt) {
            super(context, phoneWindowManagerExt);
            this.mIntent = new Intent("android.intent.action.MAIN");
        }

        public DefaultBehavior(Context context, PhoneWindowManagerExt phoneWindowManagerExt, String str) {
            super(context, phoneWindowManagerExt);
            this.mIntent = this.mContext.getPackageManager().getLaunchIntentForPackage(str);
        }
    }

    public class SFinderBehavior extends Behavior {
        public final String[] mDeniedActivities;

        public SFinderBehavior(Context context, PhoneWindowManagerExt phoneWindowManagerExt) {
            super(context, phoneWindowManagerExt);
            this.mDeniedActivities = new String[]{"{com.android.phone/com.android.phone.UsimDownload}"};
            Intent intent = new Intent();
            intent.setComponent(ComponentName.unflattenFromString("com.sec.android.app.launcher/com.sec.android.app.launcher.search.SearchActivity"));
            intent.putExtra("callername", 0);
            this.mIntent = intent;
        }

        @Override // com.android.server.policy.KeyboardShortcutManager.Behavior
        public boolean preCondition() {
            if (!isActivityAvailable(this.mIntent)) {
                Slog.d("KeyboardShortcutManager", "canLaunchAppByExternalKeyboard : SFinder activity is not available");
                return true;
            }
            if (isDeniedActivity()) {
                Slog.d("KeyboardShortcutManager", "canLaunchAppByExternalKeyboard: It is denied activity");
                return true;
            }
            return super.preCondition();
        }

        public boolean isActivityAvailable(Intent intent) {
            List semQueryIntentActivitiesAsUser;
            return (intent == null || (semQueryIntentActivitiesAsUser = this.mContext.getPackageManager().semQueryIntentActivitiesAsUser(intent, 0, UserHandle.myUserId())) == null || semQueryIntentActivitiesAsUser.size() <= 0) ? false : true;
        }

        public boolean isDeniedActivity() {
            ComponentName topActivity = this.mPolicyExt.getTopActivity();
            if (topActivity == null) {
                return false;
            }
            String shortString = topActivity.toShortString();
            for (String str : this.mDeniedActivities) {
                if (str.equals(shortString)) {
                    return true;
                }
            }
            return false;
        }
    }

    public class SettingsBehavior extends Behavior {
        public SettingsBehavior(Context context, PhoneWindowManagerExt phoneWindowManagerExt) {
            super(context, phoneWindowManagerExt);
            Intent intent = new Intent("android.settings.SETTINGS");
            intent.addFlags(67108864);
            this.mIntent = intent;
        }
    }

    public class A11ySettingsBehavior extends Behavior {
        public A11ySettingsBehavior(Context context, PhoneWindowManagerExt phoneWindowManagerExt) {
            super(context, phoneWindowManagerExt);
            Intent intent = new Intent("com.samsung.accessibility.ACCESSIBILITY_SETTINGS");
            intent.setFlags(268468224);
            this.mIntent = intent;
        }
    }

    public class GameBoosterToggleMenuBehavior extends Behavior {
        public GameBoosterToggleMenuBehavior(Context context, PhoneWindowManagerExt phoneWindowManagerExt) {
            super(context, phoneWindowManagerExt);
            Intent intent = new Intent("com.samsung.android.game.gametools.action.togglemenu");
            intent.setPackage("com.samsung.android.game.gametools");
            intent.putExtra("package", "window-g");
            this.mIntent = intent;
        }

        @Override // com.android.server.policy.KeyboardShortcutManager.Behavior
        public boolean preCondition() {
            if (this.mPolicyExt.mPolicy.isKeyguardLocked()) {
                Log.d("KeyboardShortcutManager", "GameBooster is not launched on keyguard");
                return true;
            }
            try {
                return !this.mContext.getPackageManager().getApplicationInfo("com.samsung.android.game.gametools", 128).metaData.getString("Feature.External.Action", "").contains("togglemenu");
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e("KeyboardShortcutManager", "GameBooster not installed");
                return false;
            }
        }

        @Override // com.android.server.policy.KeyboardShortcutManager.Behavior
        public void startTargetApp(int i) {
            Log.d("KeyboardShortcutManager", "send broadcast game booster toggle menu");
            this.mContext.sendBroadcastAsUser(this.mIntent, UserHandle.SYSTEM);
            StatusBarManager statusBarManager = (StatusBarManager) this.mContext.getSystemService("statusbar");
            if (statusBarManager != null) {
                statusBarManager.collapsePanels();
            }
            this.mPolicyExt.mPolicy.dismissKeyboardShortcutsMenu();
        }
    }

    public final boolean isSupportShortcut(int i) {
        return this.mShortcutMap.get(Integer.valueOf(i)) != null;
    }

    public final void reset() {
        this.mIsTriggeredMeta = false;
        this.mIsConsumedMeta = false;
    }

    public final Behavior getBehavior(int i) {
        if (i == 1) {
            return new SFinderBehavior(this.mContext, this.mPolicyExt);
        }
        if (i == 2) {
            return new GameBoosterToggleMenuBehavior(this.mContext, this.mPolicyExt);
        }
        if (i == 3) {
            return new A11ySettingsBehavior(this.mContext, this.mPolicyExt);
        }
        if (i == 4) {
            return new SettingsBehavior(this.mContext, this.mPolicyExt);
        }
        return new DefaultBehavior(this.mContext, this.mPolicyExt);
    }

    public final Behavior getBehavior(int i, String str) {
        if (i == 3) {
            return new SFinderBehavior(this.mContext, this.mPolicyExt);
        }
        if (str.equals("com.android.settings")) {
            return new SettingsBehavior(this.mContext, this.mPolicyExt);
        }
        return new DefaultBehavior(this.mContext, this.mPolicyExt, str);
    }

    public final String typeToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? Integer.toString(i) : "META_Z" : "META_U" : "META_G" : "META_F" : "DEFAULT";
    }
}
