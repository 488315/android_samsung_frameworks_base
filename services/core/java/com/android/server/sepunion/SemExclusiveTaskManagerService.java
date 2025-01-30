package com.android.server.sepunion;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.accessibility.util.ShortcutUtils;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.sepunion.ISemExclusiveTaskManager;
import com.samsung.android.view.SemWindowManager;
import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* loaded from: classes3.dex */
public class SemExclusiveTaskManagerService extends ISemExclusiveTaskManager.Stub implements AbsSemSystemService {
    public static final String TAG = SemExclusiveTaskManagerService.class.getSimpleName();
    public Context mContext;
    public final Handler mHandler = new Handler();
    public HashMap mUserStatus = new HashMap();
    public HashMap mTaskMap = new HashMap();
    public HashMap mTaskMapByKey = new HashMap();
    public ArrayList mMagnificationShortcut = new ArrayList();
    public BroadcastReceiver mUserSwitchedReceiver = new BroadcastReceiver() { // from class: com.android.server.sepunion.SemExclusiveTaskManagerService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
            Log.d(SemExclusiveTaskManagerService.TAG, "mUserSwitchedReceiver : " + intExtra);
            SemExclusiveTaskManagerService.this.userSwitch(intExtra);
        }
    };
    public final ContentObserver mExclusiveTaskObserver = new ContentObserver(new Handler()) { // from class: com.android.server.sepunion.SemExclusiveTaskManagerService.2
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri, int i) {
            String substring = uri.toString().substring(19);
            Log.d(SemExclusiveTaskManagerService.TAG, "ExclusiveTaskObserver : key : " + substring + ", user : " + i);
            UserStatus userStatus = SemExclusiveTaskManagerService.this.getUserStatus(i);
            if (userStatus == null) {
                Log.d(SemExclusiveTaskManagerService.TAG, "doesn't have user status for " + i);
                return;
            }
            if (userStatus.mIgnoreSettingsList.contains(substring)) {
                Log.d(SemExclusiveTaskManagerService.TAG, "*** ignore key : " + substring);
                userStatus.mIgnoreSettingsList.remove(substring);
                return;
            }
            if ("system/accreset_state".equals(substring)) {
                userStatus.mIsA11yReset = Settings.System.getIntForUser(SemExclusiveTaskManagerService.this.mContext.getContentResolver(), "accreset_state", 0, i) == 1;
            } else {
                SemExclusiveTaskManagerService.this.proccessExclusiveTask(substring, userStatus);
            }
        }
    };

    public class UserStatus {
        public String mPreA11yServiceValue;
        public int mPreReduceAnimationValue;
        public int mUserId = 0;
        public boolean mIsA11yReset = false;
        public HashMap mExclusiveTaskMap = new HashMap();
        public HashSet mIgnoreSettingsList = new HashSet();
    }

    public void finalize() {
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public void onBootPhase(int i) {
    }

    public final void init() {
        Log.d(TAG, "init()");
        readExclusiveRelation();
        verifyExclusiveRelation();
        proccessExceptionalCases();
        registerExclusiveTaskObserver();
    }

    public final UserStatus getUserStatus(int i) {
        if (!this.mUserStatus.containsKey(Integer.valueOf(i))) {
            Log.d(TAG, "no user status for " + i);
            return null;
        }
        return (UserStatus) this.mUserStatus.get(Integer.valueOf(i));
    }

    public final void userSwitch(int i) {
        if (this.mUserStatus.containsKey(Integer.valueOf(i))) {
            Log.d(TAG, "already has user status for " + i);
            return;
        }
        UserStatus userStatus = new UserStatus();
        this.mUserStatus.put(Integer.valueOf(i), userStatus);
        userStatus.mUserId = i;
        userStatus.mPreA11yServiceValue = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "enabled_accessibility_services", userStatus.mUserId);
    }

    public final boolean verifyExclusiveRelation() {
        Iterator it = this.mTaskMap.keySet().iterator();
        while (it.hasNext()) {
            Task task = (Task) this.mTaskMap.get((String) it.next());
            Iterator it2 = task.exclusiveListSet.iterator();
            while (it2.hasNext()) {
                hasExclusiveRelation(task, (String) it2.next());
            }
        }
        return true;
    }

    public final boolean hasExclusiveRelation(Task task, String str) {
        Task task2 = (Task) this.mTaskMap.get(str);
        if (task2 == null) {
            Log.d(TAG, "### " + str + " isn't exist in mTaskMap");
            return false;
        }
        if (task2.exclusiveListSet.contains(task.getName())) {
            return true;
        }
        Log.d(TAG, "### " + task.getName() + " doesn't exist in exclusiveListSet of " + str);
        return false;
    }

    public final void proccessExceptionalCases() {
        Task task = (Task) this.mTaskMap.get("talkback_se");
        if (task != null) {
            task.componentName = "com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService";
        } else {
            Log.d(TAG, "mTaskMap hasn't COMPONENT_NAME_TALKBACK_SE");
        }
        Task task2 = (Task) this.mTaskMap.get("universal_switch");
        if (task2 != null) {
            task2.componentName = "com.samsung.accessibility/.universalswitch.UniversalSwitchService";
        } else {
            Log.d(TAG, "mTaskMap hasn't COMPONENT_NAME_UNIVERSAL_SWITCH");
        }
        Task task3 = (Task) this.mTaskMap.get("talkback");
        if (task3 != null) {
            task3.componentName = "com.google.android.marvin.talkback/.TalkBackService";
        } else {
            Log.d(TAG, "mTaskMap hasn't COMPONENT_NAME_GOOGLE_TALKBACK");
        }
        Task task4 = (Task) this.mTaskMap.get("assistant_menu");
        if (task4 != null) {
            task4.componentName = "com.samsung.accessibility/.assistantmenu.serviceframework.AssistantMenuService";
        } else {
            Log.d(TAG, "mTaskMap hasn't COMPONENT_NAME_ASSISTANT_MENU");
        }
    }

    public final void registerExclusiveTaskObserver() {
        if (this.mTaskMap.isEmpty()) {
            Log.d(TAG, "mTaskMap is empty");
            return;
        }
        this.mContext.getContentResolver().unregisterContentObserver(this.mExclusiveTaskObserver);
        Iterator it = this.mTaskMapByKey.keySet().iterator();
        while (it.hasNext()) {
            ((Task) this.mTaskMapByKey.get((String) it.next())).setting.registerObserver(this.mContext.getContentResolver(), this.mExclusiveTaskObserver);
        }
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("accreset_state"), false, this.mExclusiveTaskObserver, -1);
    }

    public final void proccessExclusiveTask(String str, UserStatus userStatus) {
        Task task = (Task) this.mTaskMapByKey.get(str);
        if (task == null) {
            Log.d(TAG, "### proccessExclusiveTask() : task is null");
            return;
        }
        if (userStatus.mIsA11yReset && task.getType() != 3) {
            Log.d(TAG, "### ignore changed a11y settings");
            return;
        }
        if (task.getType() == 1) {
            String str2 = TAG;
            Log.d(str2, "enabled_accessibility_service : " + Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "enabled_accessibility_services", userStatus.mUserId));
            Task a11yServiceTask = getA11yServiceTask(userStatus);
            if (a11yServiceTask == null) {
                Log.d(str2, "### no samsung a11y service");
                return;
            }
            task = a11yServiceTask;
        }
        if (task.getSettingsStatus(userStatus)) {
            proccessTurningOnTask(task, userStatus);
        } else {
            proccessTurningOffTask(task, userStatus);
        }
    }

    public final Task getA11yServiceTask(UserStatus userStatus) {
        String changedA11yServiceName;
        Set<ComponentName> enabledServicesFromSettings = getEnabledServicesFromSettings(this.mContext, false);
        Set<ComponentName> enabledServicesFromSettings2 = getEnabledServicesFromSettings(this.mContext, true);
        String str = TAG;
        Log.d(str, "last a11y count : " + enabledServicesFromSettings.size());
        Log.d(str, "current a11y count : " + enabledServicesFromSettings2.size());
        userStatus.mPreA11yServiceValue = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "enabled_accessibility_services", userStatus.mUserId);
        if (enabledServicesFromSettings.size() == enabledServicesFromSettings2.size()) {
            Log.d(str, "a11y count hasn't changed");
            for (ComponentName componentName : enabledServicesFromSettings) {
                Log.d(TAG, "oldServices : " + componentName);
            }
            for (ComponentName componentName2 : enabledServicesFromSettings2) {
                Log.d(TAG, "newServices : " + componentName2);
            }
            return null;
        }
        if (enabledServicesFromSettings2.size() > enabledServicesFromSettings.size()) {
            enabledServicesFromSettings2.removeAll(enabledServicesFromSettings);
            Log.d(str, "new a11y service is on");
            changedA11yServiceName = getChangedA11yServiceName(enabledServicesFromSettings2);
        } else {
            enabledServicesFromSettings.removeAll(enabledServicesFromSettings2);
            Log.d(str, "old a11y service is off");
            changedA11yServiceName = getChangedA11yServiceName(enabledServicesFromSettings);
        }
        if (changedA11yServiceName != null) {
            return (Task) this.mTaskMap.get(changedA11yServiceName);
        }
        return null;
    }

    public final String getChangedA11yServiceName(Set set) {
        Iterator it = set.iterator();
        while (it.hasNext()) {
            ComponentName componentName = (ComponentName) it.next();
            String str = TAG;
            Log.d(str, "name : " + componentName.flattenToShortString());
            if ("com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService".equals(componentName.flattenToShortString())) {
                Log.d(str, "talkback se status has changed");
                return "talkback_se";
            }
            if ("com.google.android.marvin.talkback/.TalkBackService".equals(componentName.flattenToShortString())) {
                Log.d(str, "talkback status has changed");
                return "talkback";
            }
            if ("com.samsung.accessibility/.universalswitch.UniversalSwitchService".equals(componentName.flattenToShortString())) {
                Log.d(str, "universal switch status has changed");
                return "universal_switch";
            }
            if ("com.samsung.accessibility/.assistantmenu.serviceframework.AssistantMenuService".equals(componentName.flattenToShortString())) {
                Log.d(str, "assistant menu status has changed");
                return "assistant_menu";
            }
        }
        return null;
    }

    public final void proccessTurningOnTask(Task task, UserStatus userStatus) {
        Log.d(TAG, "proccessTurningOnTask() : " + task.getName());
        ExclusiveTask exclusiveTask = new ExclusiveTask();
        exclusiveTask.name = task.getName();
        Iterator it = task.exclusiveListSet.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            Task task2 = (Task) this.mTaskMap.get(str);
            if (task2 != null && task2.getSettingsStatus(userStatus)) {
                if (userStatus.mExclusiveTaskMap.containsKey(str)) {
                    String str2 = TAG;
                    Log.d(str2, "*** " + str + " is in mExclusiveTaskMap");
                    ExclusiveTask exclusiveTask2 = (ExclusiveTask) userStatus.mExclusiveTaskMap.get(str);
                    if (exclusiveTask2.manipulatedList.contains(task.getName())) {
                        Log.d(str2, task.getName() + " is in manipulatedList");
                        exclusiveTask2.manipulatedList.remove(task.getName());
                        if (exclusiveTask2.manipulatedList.size() == 0) {
                            userStatus.mExclusiveTaskMap.remove(str);
                        }
                    }
                }
                exclusiveTask.manipulatedList.add(str);
                task2.turnOnOff(false, userStatus);
                executePostProccess(task2, false, userStatus);
                Log.d(TAG, "turn off " + str + " for " + task.getName());
            }
        }
        if (exclusiveTask.manipulatedList.size() > 0) {
            userStatus.mExclusiveTaskMap.put(task.getName(), exclusiveTask);
        }
    }

    public final void updateProfile(String str) {
        if ("tap_duration".equals(str)) {
            AccessibilityUtils.updateProfile(this.mContext, "tap_duration");
            return;
        }
        if ("mute_all_sound".equals(str)) {
            AccessibilityUtils.updateProfile(this.mContext, "com.android.settings/com.samsung.android.settings.accessibility.shortcut.MuteAllShortcut");
        } else if ("talkback".equals(str)) {
            AccessibilityUtils.updateProfile(this.mContext, "com.google.android.marvin.talkback/.TalkBackService");
        } else if ("talkback_se".equals(str)) {
            AccessibilityUtils.updateProfile(this.mContext, "com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService");
        }
    }

    public final void proccessTurningOffTask(Task task, UserStatus userStatus) {
        String str = TAG;
        Log.d(str, "proccessTurningOffTask() : " + task.getName());
        if (userStatus.mExclusiveTaskMap.containsKey(task.getName())) {
            Log.d(str, "*** " + task.getName() + " is in mExclusiveTaskMap");
            Iterator it = ((ExclusiveTask) userStatus.mExclusiveTaskMap.get(task.getName())).manipulatedList.iterator();
            while (it.hasNext()) {
                Task task2 = (Task) this.mTaskMap.get((String) it.next());
                if (task2.canTurnOnFeature(userStatus) || reCheck(task2, userStatus)) {
                    Log.d(TAG, "turn on " + task2.name + " for exclusive task name : " + task.getName());
                    task2.turnOnOff(true, userStatus);
                    executePostProccess(task2, true, userStatus);
                }
            }
            userStatus.mExclusiveTaskMap.remove(task.getName());
        }
    }

    public final boolean reCheck(Task task, UserStatus userStatus) {
        if (!task.getName().equals("one_handed_mode")) {
            return false;
        }
        Log.d(TAG, "reCheck For : " + task.getName());
        Iterator it = task.exclusiveListSet.iterator();
        while (it.hasNext()) {
            Task task2 = (Task) this.mTaskMap.get((String) it.next());
            if (task2.getSettingsStatus(userStatus) && task2.getName().equals("corner_action")) {
                Log.d(TAG, "This is Exceptional case for " + task.getName() + " / " + task2.getName());
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final void executePostProccess(Task task, boolean z, UserStatus userStatus) {
        Log.d(TAG, "executePostProccess() name : " + task.getName() + ", status : " + z);
        String name = task.getName();
        name.hashCode();
        boolean z2 = true;
        char c = 65535;
        switch (name.hashCode()) {
            case -1328727671:
                if (name.equals("remove_animation")) {
                    c = 0;
                    break;
                }
                break;
            case -610139245:
                if (name.equals("talkback")) {
                    c = 1;
                    break;
                }
                break;
            case -586200677:
                if (name.equals("one_handed_mode")) {
                    c = 2;
                    break;
                }
                break;
            case -356556162:
                if (name.equals("talkback_se")) {
                    c = 3;
                    break;
                }
                break;
            case -295477822:
                if (name.equals("magnification_shortcut")) {
                    c = 4;
                    break;
                }
                break;
            case -124654970:
                if (name.equals("blue_light_filter")) {
                    c = 5;
                    break;
                }
                break;
            case 167668299:
                if (name.equals("mute_all_sound")) {
                    c = 6;
                    break;
                }
                break;
            case 659793968:
                if (name.equals("interaction_control")) {
                    c = 7;
                    break;
                }
                break;
            case 1289441370:
                if (name.equals("color_lens")) {
                    c = '\b';
                    break;
                }
                break;
            case 1308945273:
                if (name.equals("color_blind")) {
                    c = '\t';
                    break;
                }
                break;
            case 1441900208:
                if (name.equals("tap_duration")) {
                    c = '\n';
                    break;
                }
                break;
        }
        float f = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        switch (c) {
            case 0:
                IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
                if (!z) {
                    f = 1.0f;
                }
                try {
                    windowManagerService.setAnimationScale(0, f);
                    windowManagerService.setAnimationScale(1, f);
                    windowManagerService.setAnimationScale(2, f);
                } catch (RemoteException unused) {
                    Log.d(TAG, "error during calling setAnimationScale");
                }
                if (z) {
                    userStatus.mPreReduceAnimationValue = Settings.System.getIntForUser(this.mContext.getContentResolver(), "reduce_animations", 0, userStatus.mUserId);
                    Settings.System.putIntForUser(this.mContext.getContentResolver(), "reduce_animations", 1, userStatus.mUserId);
                    break;
                } else if (userStatus.mPreReduceAnimationValue == 0) {
                    Settings.System.putIntForUser(this.mContext.getContentResolver(), "reduce_animations", 0, userStatus.mUserId);
                    break;
                }
                break;
            case 1:
            case 3:
            case '\n':
                updateProfile(task.getName());
                break;
            case 2:
                IWindowManager windowManagerService2 = WindowManagerGlobal.getWindowManagerService();
                try {
                    if (z) {
                        if (Settings.System.getIntForUser(this.mContext.getContentResolver(), "one_handed_op_wakeup_type", 0, userStatus.mUserId) != 0) {
                            z2 = false;
                        }
                        if (!z2) {
                            Intent intent = new Intent();
                            intent.setComponent(ComponentName.unflattenFromString("onehand/onehand"));
                            windowManagerService2.putKeyCustomizationInfo(new SemWindowManager.KeyCustomizationInfo(8, 1106, 3, 0, intent));
                        }
                    } else {
                        windowManagerService2.removeKeyCustomizationInfo(1106, 8, 3);
                    }
                } catch (RemoteException unused2) {
                    Log.d(TAG, "Exception occurs when remove OneHandedMode Key");
                }
                Intent intent2 = new Intent("com.samsung.intent.action.ONEHAND_REDUCE_SCREEN_STATUS");
                intent2.putExtra("is_enabled", z);
                this.mContext.sendBroadcast(intent2);
                break;
            case 4:
                if (z) {
                    for (int i = 0; i < this.mMagnificationShortcut.size(); i++) {
                        if (((Integer) this.mMagnificationShortcut.get(i)).intValue() == 4) {
                            Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "accessibility_display_magnification_enabled", 1, userStatus.mUserId);
                        } else {
                            ShortcutUtils.optInValueToSettings(this.mContext, ((Integer) this.mMagnificationShortcut.get(i)).intValue(), "com.android.server.accessibility.MagnificationController");
                        }
                    }
                    this.mMagnificationShortcut.clear();
                    break;
                } else {
                    if (ShortcutUtils.isComponentIdExistingInSettings(this.mContext, 1, "com.android.server.accessibility.MagnificationController")) {
                        this.mMagnificationShortcut.add(1);
                        ShortcutUtils.optOutValueFromSettings(this.mContext, 1, "com.android.server.accessibility.MagnificationController");
                    }
                    if (ShortcutUtils.isComponentIdExistingInSettings(this.mContext, 2, "com.android.server.accessibility.MagnificationController")) {
                        this.mMagnificationShortcut.add(2);
                        ShortcutUtils.optOutValueFromSettings(this.mContext, 2, "com.android.server.accessibility.MagnificationController");
                    }
                    if (ShortcutUtils.isComponentIdExistingInSettings(this.mContext, 3, "com.android.server.accessibility.MagnificationController")) {
                        this.mMagnificationShortcut.add(3);
                        ShortcutUtils.optOutValueFromSettings(this.mContext, 3, "com.android.server.accessibility.MagnificationController");
                    }
                    if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_display_magnification_enabled", 0, userStatus.mUserId) == 1) {
                        this.mMagnificationShortcut.add(4);
                        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "accessibility_display_magnification_enabled", 0, userStatus.mUserId);
                        break;
                    }
                }
                break;
            case 5:
                try {
                    Intent intent3 = new Intent();
                    intent3.setComponent(new ComponentName("com.samsung.android.bluelightfilter", "com.samsung.android.bluelightfilter.BlueLightFilterService"));
                    intent3.putExtra("BLUE_LIGHT_FILTER_SERVICE_TYPE", z ? 21 : 22);
                    this.mContext.startServiceAsUser(intent3, UserHandle.CURRENT);
                    break;
                } catch (Exception unused3) {
                    Log.d(TAG, "error during starting BlueLightFilterService");
                    return;
                }
            case 6:
                Intent intent4 = new Intent("android.settings.ALL_SOUND_MUTE");
                intent4.putExtra("mute", z ? 1 : 0);
                this.mContext.sendBroadcastAsUser(intent4, UserHandle.ALL);
                updateProfile("mute_all_sound");
                break;
            case 7:
                Intent intent5 = new Intent("android.intent.action.MAIN");
                intent5.setComponent(ComponentName.unflattenFromString("com.samsung.accessibility/.interactioncontrol.AccessControlMainService"));
                if (z) {
                    this.mContext.startServiceAsUser(intent5, UserHandle.CURRENT);
                    break;
                } else {
                    this.mContext.stopServiceAsUser(intent5, UserHandle.CURRENT);
                    break;
                }
            case '\b':
                AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(this.mContext);
                if (z) {
                    accessibilityManager.semEnableMdnieColorFilter(Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "color_lens_type", 0, userStatus.mUserId), Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "color_lens_opacity", 0, userStatus.mUserId));
                    break;
                } else {
                    accessibilityManager.semDisableMdnieColorFilter();
                    break;
                }
            case '\t':
                AccessibilityManager accessibilityManager2 = AccessibilityManager.getInstance(this.mContext);
                if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "color_adjustment_type", 2, userStatus.mUserId) == 0) {
                    setGrayScale(this.mContext, z);
                    break;
                } else {
                    setGrayScale(this.mContext, false);
                    accessibilityManager2.semSetMdnieColorBlind(z, Settings.Secure.getFloatForUser(this.mContext.getContentResolver(), "color_blind_user_parameter", DisplayPowerController2.RATE_FROM_DOZE_TO_ON, userStatus.mUserId));
                    break;
                }
        }
    }

    public static void setGrayScale(Context context, boolean z) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        if (accessibilityManager != null) {
            if (z) {
                accessibilityManager.semSetMdnieAccessibilityMode(4, true);
            } else {
                accessibilityManager.semSetMdnieAccessibilityMode(1, false);
            }
        }
    }

    public class ExclusiveTask {
        public ArrayList manipulatedList;
        public String name;

        public ExclusiveTask() {
            this.manipulatedList = new ArrayList();
        }
    }

    public class Task {
        public int actionType;
        public String componentName;
        public HashSet exclusiveListSet;
        public String name;
        public boolean needNotify;
        public TaskSettings setting;

        public Task() {
            this.setting = new TaskSettings();
            this.exclusiveListSet = new HashSet();
        }

        public final String getName() {
            return this.name;
        }

        public final int getType() {
            return this.actionType;
        }

        public final boolean getSettingsStatus(UserStatus userStatus) {
            if (getType() == 1) {
                SemExclusiveTaskManagerService semExclusiveTaskManagerService = SemExclusiveTaskManagerService.this;
                return semExclusiveTaskManagerService.isAccessibilityServiceEnabled(semExclusiveTaskManagerService.mContext, this.componentName);
            }
            return this.setting.getCurrentSettings(userStatus);
        }

        public final void turnOnOff(boolean z, UserStatus userStatus) {
            if (getType() == 1) {
                ComponentName unflattenFromString = ComponentName.unflattenFromString(this.componentName);
                SemExclusiveTaskManagerService semExclusiveTaskManagerService = SemExclusiveTaskManagerService.this;
                semExclusiveTaskManagerService.setAccessibilityServiceState(semExclusiveTaskManagerService.mContext, unflattenFromString, z, userStatus.mUserId);
                userStatus.mIgnoreSettingsList.add(this.setting.rawKey);
                userStatus.mPreA11yServiceValue = Settings.Secure.getStringForUser(SemExclusiveTaskManagerService.this.mContext.getContentResolver(), "enabled_accessibility_services", userStatus.mUserId);
                return;
            }
            this.setting.turnOnOff(z, userStatus);
        }

        public final boolean canTurnOnFeature(UserStatus userStatus) {
            Log.d(SemExclusiveTaskManagerService.TAG, "canTurnOnFeature() : " + this.name);
            Iterator it = this.exclusiveListSet.iterator();
            while (it.hasNext()) {
                Task task = (Task) SemExclusiveTaskManagerService.this.mTaskMap.get((String) it.next());
                if (task.getSettingsStatus(userStatus)) {
                    Log.d(SemExclusiveTaskManagerService.TAG, "canTurnOnFeature() returns false : " + task.getName() + " is on");
                    return false;
                }
            }
            return true;
        }
    }

    public class TaskSettings {
        public int OFF;

        /* renamed from: ON */
        public int f1723ON;
        public int defaultValue;
        public String key;
        public int previousValue;
        public String rawKey;
        public int tableType;

        public TaskSettings() {
            this.f1723ON = 1;
            this.OFF = 0;
            this.defaultValue = 0;
            this.previousValue = 1;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0038, code lost:
        
            if (r1.equals("global") == false) goto L4;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void setSettingsKey(String str) {
            this.rawKey = str;
            String[] split = str.split("/");
            boolean z = false;
            String str2 = split[0];
            str2.hashCode();
            switch (str2.hashCode()) {
                case -1243020381:
                    break;
                case -906273929:
                    if (str2.equals("secure")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                case -887328209:
                    if (str2.equals("system")) {
                        z = 2;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                    this.tableType = 3;
                    break;
                case true:
                    this.tableType = 2;
                    break;
                case true:
                    this.tableType = 1;
                    break;
            }
            this.key = split[1];
        }

        public void registerObserver(ContentResolver contentResolver, ContentObserver contentObserver) {
            int i = this.tableType;
            if (i == 1) {
                contentResolver.registerContentObserver(Settings.System.getUriFor(this.key), false, contentObserver, -1);
            } else if (i == 2) {
                contentResolver.registerContentObserver(Settings.Secure.getUriFor(this.key), false, contentObserver, -1);
            } else {
                if (i != 3) {
                    return;
                }
                contentResolver.registerContentObserver(Settings.Global.getUriFor(this.key), false, contentObserver, -1);
            }
        }

        public int getCurrentSettingsValue(UserStatus userStatus) {
            int i = this.tableType;
            if (i == 1) {
                return Settings.System.getIntForUser(SemExclusiveTaskManagerService.this.mContext.getContentResolver(), this.key, this.defaultValue, userStatus.mUserId);
            }
            if (i == 2) {
                return Settings.Secure.getIntForUser(SemExclusiveTaskManagerService.this.mContext.getContentResolver(), this.key, this.defaultValue, userStatus.mUserId);
            }
            if (i != 3) {
                return -1;
            }
            return Settings.Global.getInt(SemExclusiveTaskManagerService.this.mContext.getContentResolver(), this.key, this.defaultValue);
        }

        public boolean getCurrentSettings(UserStatus userStatus) {
            return getCurrentSettingsValue(userStatus) != this.OFF;
        }

        public void putCurrentSettingsValue(int i, UserStatus userStatus) {
            if (i == this.OFF) {
                this.previousValue = getCurrentSettingsValue(userStatus);
            }
            int i2 = this.tableType;
            if (i2 == 1) {
                Settings.System.putIntForUser(SemExclusiveTaskManagerService.this.mContext.getContentResolver(), this.key, i, userStatus.mUserId);
            } else if (i2 == 2) {
                Settings.Secure.putIntForUser(SemExclusiveTaskManagerService.this.mContext.getContentResolver(), this.key, i, userStatus.mUserId);
            } else if (i2 == 3) {
                Settings.Global.putInt(SemExclusiveTaskManagerService.this.mContext.getContentResolver(), this.key, i);
            }
            if (isExceptionalCase()) {
                Log.d(SemExclusiveTaskManagerService.TAG, "This case shouldn't be included at ignore list");
            } else {
                userStatus.mIgnoreSettingsList.add(this.rawKey);
            }
        }

        public final boolean isExceptionalCase() {
            return this.key.equals("any_screen_enabled") && Settings.Secure.getInt(SemExclusiveTaskManagerService.this.mContext.getContentResolver(), "accessibility_corner_action_enabled", 0) == 1;
        }

        public void turnOnOff(boolean z, UserStatus userStatus) {
            if (z) {
                putCurrentSettingsValue(this.previousValue, userStatus);
            } else {
                putCurrentSettingsValue(this.OFF, userStatus);
            }
        }
    }

    public final void readExclusiveRelation() {
        char c;
        Log.d(TAG, "readExclusiveRelation()");
        try {
            Document parse = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("/system/etc/task_action.xml"));
            if ("task-list".equals(parse.getDocumentElement().getNodeName()) && parse.hasChildNodes()) {
                NodeList childNodes = parse.getChildNodes();
                NodeList childNodes2 = childNodes.item(0).getChildNodes();
                int i = 1;
                while (true) {
                    if (i >= childNodes.getLength()) {
                        break;
                    }
                    if ("task-list".equals(childNodes.item(i).getNodeName())) {
                        childNodes2 = childNodes.item(i).getChildNodes();
                        break;
                    }
                    i++;
                }
                for (int i2 = 0; i2 < childNodes2.getLength(); i2++) {
                    Node item = childNodes2.item(i2);
                    if (item.getNodeType() == 1 && "task".equals(item.getNodeName()) && item.hasAttributes()) {
                        Task task = new Task();
                        NamedNodeMap attributes = item.getAttributes();
                        for (int i3 = 0; i3 < attributes.getLength(); i3++) {
                            Node item2 = attributes.item(i3);
                            String nodeName = item2.getNodeName();
                            switch (nodeName.hashCode()) {
                                case -1480539265:
                                    if (nodeName.equals("needNotify")) {
                                        c = 4;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 106079:
                                    if (nodeName.equals("key")) {
                                        c = 2;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 3373707:
                                    if (nodeName.equals("name")) {
                                        c = 0;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 3575610:
                                    if (nodeName.equals("type")) {
                                        c = 1;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1686617758:
                                    if (nodeName.equals("exclusive")) {
                                        c = 3;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                default:
                                    c = 65535;
                                    break;
                            }
                            if (c == 0) {
                                task.name = item2.getNodeValue();
                            } else if (c == 1) {
                                task.actionType = Integer.parseInt(item2.getNodeValue());
                            } else if (c == 2) {
                                task.setting.setSettingsKey(item2.getNodeValue());
                            } else if (c == 3) {
                                task.exclusiveListSet.addAll(new ArrayList(Arrays.asList(item2.getNodeValue().replaceAll(" ", "").split(","))));
                            } else if (c == 4) {
                                task.needNotify = "1".equals(item2.getNodeValue());
                            }
                        }
                        this.mTaskMap.put(task.getName(), task);
                        this.mTaskMapByKey.put(task.setting.rawKey, task);
                    }
                }
            }
        } catch (Exception unused) {
            Log.d(TAG, "error during readExclusiveRelation()");
        }
    }

    public List getExclusiveTaskList(String str) {
        Log.d(TAG, "getExclusiveTaskList for : " + str);
        ArrayList arrayList = new ArrayList();
        Task task = (Task) this.mTaskMap.get(str);
        if (task != null) {
            UserStatus userStatus = getUserStatus(ActivityManager.getCurrentUser());
            Iterator it = task.exclusiveListSet.iterator();
            while (it.hasNext()) {
                Task task2 = (Task) this.mTaskMap.get((String) it.next());
                if (task2 != null && task2.getSettingsStatus(userStatus)) {
                    Log.d(TAG, "getExclusiveTaskList add : " + task2.getName());
                    arrayList.add(task2.getName());
                }
            }
        }
        return arrayList;
    }

    public void setAccessibilityServiceState(Context context, ComponentName componentName, boolean z, int i) {
        Set enabledServicesFromSettings = getEnabledServicesFromSettings(context, true);
        if (enabledServicesFromSettings.isEmpty() && z) {
            enabledServicesFromSettings = new ArraySet(1);
        }
        if (z) {
            enabledServicesFromSettings.add(componentName);
        } else if (!enabledServicesFromSettings.isEmpty()) {
            enabledServicesFromSettings.remove(componentName);
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = enabledServicesFromSettings.iterator();
        while (it.hasNext()) {
            sb.append(((ComponentName) it.next()).flattenToString());
            sb.append(':');
        }
        int length = sb.length();
        if (length > 0) {
            sb.deleteCharAt(length - 1);
        }
        Settings.Secure.putStringForUser(context.getContentResolver(), "enabled_accessibility_services", sb.toString(), i);
        Settings.Secure.putIntForUser(context.getContentResolver(), "accessibility_enabled", !enabledServicesFromSettings.isEmpty() ? 1 : 0, i);
    }

    public Set getEnabledServicesFromSettings(Context context, boolean z) {
        String str;
        if (z) {
            str = Settings.Secure.getStringForUser(context.getContentResolver(), "enabled_accessibility_services", -2);
        } else {
            UserStatus userStatus = getUserStatus(ActivityManager.getCurrentUser());
            str = userStatus != null ? userStatus.mPreA11yServiceValue : null;
        }
        if (str == null) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet();
        TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(':');
        simpleStringSplitter.setString(str);
        while (simpleStringSplitter.hasNext()) {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(simpleStringSplitter.next());
            if (unflattenFromString != null) {
                hashSet.add(unflattenFromString);
            }
        }
        return hashSet;
    }

    public boolean isAccessibilityServiceEnabled(Context context, String str) {
        return isAccessibilityServiceEnabled(context, ComponentName.unflattenFromString(str));
    }

    public boolean isAccessibilityServiceEnabled(Context context, ComponentName componentName) {
        Set enabledServicesFromSettings = getEnabledServicesFromSettings(context, true);
        if (enabledServicesFromSettings.isEmpty()) {
            return false;
        }
        Iterator it = enabledServicesFromSettings.iterator();
        while (it.hasNext()) {
            if (((ComponentName) it.next()).equals(componentName)) {
                return true;
            }
        }
        return false;
    }

    public SemExclusiveTaskManagerService(Context context) {
        this.mContext = context;
        Log.d(TAG, "SemExclusiveTaskManagerService start");
        init();
        userSwitch(0);
        this.mContext.registerReceiverAsUser(this.mUserSwitchedReceiver, UserHandle.ALL, new IntentFilter("android.intent.action.USER_SWITCHED"), null, null);
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public void onCreate(Bundle bundle) {
        Log.d(TAG, "SemExclusiveTaskManagerService onCreate()");
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("\n##### SemExclusiveTaskManagerService #####\n##### (dumpsys sepunion exclusivetask) #####\n");
        Iterator it = this.mTaskMapByKey.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Task task = (Task) this.mTaskMapByKey.get((String) it.next());
            printWriter.println("task[" + i + "] : " + task.getName() + ", key : " + task.setting.rawKey);
            i++;
        }
        Iterator it2 = this.mUserStatus.keySet().iterator();
        while (it2.hasNext()) {
            UserStatus userStatus = (UserStatus) this.mUserStatus.get(Integer.valueOf(((Integer) it2.next()).intValue()));
            printWriter.println("user id : " + userStatus.mUserId);
            printWriter.println("mExclusiveTaskMap size : " + userStatus.mExclusiveTaskMap.size());
            Iterator it3 = userStatus.mExclusiveTaskMap.keySet().iterator();
            while (it3.hasNext()) {
                ExclusiveTask exclusiveTask = (ExclusiveTask) userStatus.mExclusiveTaskMap.get((String) it3.next());
                printWriter.println("exclusiveTask name : " + exclusiveTask.name);
                Iterator it4 = exclusiveTask.manipulatedList.iterator();
                int i2 = 0;
                while (it4.hasNext()) {
                    printWriter.println("manipulated task[" + i2 + "] name : " + ((String) it4.next()));
                    i2++;
                }
            }
        }
    }
}
