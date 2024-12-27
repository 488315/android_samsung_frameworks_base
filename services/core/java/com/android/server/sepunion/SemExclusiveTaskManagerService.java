package com.android.server.sepunion;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
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
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.sepunion.ISemExclusiveTaskManager;
import com.samsung.android.view.SemWindowManager;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SemExclusiveTaskManagerService extends ISemExclusiveTaskManager.Stub
        implements AbsSemSystemService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final AnonymousClass2 mExclusiveTaskObserver;
    public final ArrayList mMagnificationShortcut;
    public final HashMap mTaskMap;
    public final HashMap mTaskMapByKey;
    public final HashMap mUserStatus;
    public final AnonymousClass1 mUserSwitchedReceiver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ExclusiveTask {
        public final ArrayList manipulatedList = new ArrayList();
        public String name;

        public ExclusiveTask() {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Task {
        public int actionType;
        public String componentName;
        public final HashSet exclusiveListSet = new HashSet();
        public String name;
        public final TaskSettings setting;

        /* renamed from: -$$Nest$mturnOnOff, reason: not valid java name */
        public static void m868$$Nest$mturnOnOff(Task task, boolean z, UserStatus userStatus) {
            int i = task.actionType;
            TaskSettings taskSettings = task.setting;
            if (i != 1) {
                if (z) {
                    taskSettings.putCurrentSettingsValue(taskSettings.previousValue, userStatus);
                    return;
                } else {
                    taskSettings.putCurrentSettingsValue(0, userStatus);
                    return;
                }
            }
            ComponentName unflattenFromString =
                    ComponentName.unflattenFromString(task.componentName);
            SemExclusiveTaskManagerService semExclusiveTaskManagerService =
                    SemExclusiveTaskManagerService.this;
            semExclusiveTaskManagerService.setAccessibilityServiceState(
                    semExclusiveTaskManagerService.mContext,
                    unflattenFromString,
                    z,
                    userStatus.mUserId);
            userStatus.mIgnoreSettingsList.add(taskSettings.rawKey);
            userStatus.mPreA11yServiceValue =
                    Settings.Secure.getStringForUser(
                            semExclusiveTaskManagerService.mContext.getContentResolver(),
                            "enabled_accessibility_services",
                            userStatus.mUserId);
        }

        public Task() {
            this.setting = SemExclusiveTaskManagerService.this.new TaskSettings();
        }

        public final boolean getSettingsStatus(UserStatus userStatus) {
            if (this.actionType != 1) {
                return this.setting.getCurrentSettingsValue(userStatus) != 0;
            }
            SemExclusiveTaskManagerService semExclusiveTaskManagerService =
                    SemExclusiveTaskManagerService.this;
            return semExclusiveTaskManagerService.isAccessibilityServiceEnabled(
                    semExclusiveTaskManagerService.mContext, this.componentName);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TaskSettings {
        public String key;
        public int previousValue = 1;
        public String rawKey;
        public int tableType;

        public TaskSettings() {}

        public final int getCurrentSettingsValue(UserStatus userStatus) {
            int i = this.tableType;
            SemExclusiveTaskManagerService semExclusiveTaskManagerService =
                    SemExclusiveTaskManagerService.this;
            if (i == 1) {
                return Settings.System.getIntForUser(
                        semExclusiveTaskManagerService.mContext.getContentResolver(),
                        this.key,
                        0,
                        userStatus.mUserId);
            }
            if (i == 2) {
                return Settings.Secure.getIntForUser(
                        semExclusiveTaskManagerService.mContext.getContentResolver(),
                        this.key,
                        0,
                        userStatus.mUserId);
            }
            if (i != 3) {
                return -1;
            }
            return Settings.Global.getInt(
                    semExclusiveTaskManagerService.mContext.getContentResolver(), this.key, 0);
        }

        public final void putCurrentSettingsValue(int i, UserStatus userStatus) {
            if (i == 0) {
                this.previousValue = getCurrentSettingsValue(userStatus);
            }
            int i2 = this.tableType;
            SemExclusiveTaskManagerService semExclusiveTaskManagerService =
                    SemExclusiveTaskManagerService.this;
            if (i2 == 1) {
                Settings.System.putIntForUser(
                        semExclusiveTaskManagerService.mContext.getContentResolver(),
                        this.key,
                        i,
                        userStatus.mUserId);
            } else if (i2 == 2) {
                Settings.Secure.putIntForUser(
                        semExclusiveTaskManagerService.mContext.getContentResolver(),
                        this.key,
                        i,
                        userStatus.mUserId);
            } else if (i2 == 3) {
                Settings.Global.putInt(
                        semExclusiveTaskManagerService.mContext.getContentResolver(), this.key, i);
            }
            if (!this.key.equals("any_screen_enabled")
                    || Settings.Secure.getInt(
                                    semExclusiveTaskManagerService.mContext.getContentResolver(),
                                    "accessibility_corner_action_enabled",
                                    0)
                            != 1) {
                userStatus.mIgnoreSettingsList.add(this.rawKey);
            } else {
                int i3 = SemExclusiveTaskManagerService.$r8$clinit;
                Log.d(
                        "SemExclusiveTaskManagerService",
                        "This case shouldn't be included at ignore list");
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserStatus {
        public HashMap mExclusiveTaskMap;
        public HashSet mIgnoreSettingsList;
        public boolean mIsA11yReset;
        public String mPreA11yServiceValue;
        public int mPreReduceAnimationValue;
        public int mUserId;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Type inference failed for: r5v5, types: [com.android.server.sepunion.SemExclusiveTaskManagerService$1] */
    /* JADX WARN: Type inference failed for: r5v6, types: [com.android.server.sepunion.SemExclusiveTaskManagerService$2] */
    public SemExclusiveTaskManagerService(Context context) {
        char c;
        char c2;
        new Handler();
        this.mUserStatus = new HashMap();
        this.mTaskMap = new HashMap();
        this.mTaskMapByKey = new HashMap();
        this.mMagnificationShortcut = new ArrayList();
        this.mUserSwitchedReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.sepunion.SemExclusiveTaskManagerService.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        int intExtra =
                                intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        int i = SemExclusiveTaskManagerService.$r8$clinit;
                        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                                intExtra,
                                "mUserSwitchedReceiver : ",
                                "SemExclusiveTaskManagerService");
                        SemExclusiveTaskManagerService.this.userSwitch(intExtra);
                    }
                };
        this.mExclusiveTaskObserver =
                new ContentObserver(new Handler()) { // from class:
                    // com.android.server.sepunion.SemExclusiveTaskManagerService.2
                    /* JADX WARN: Code restructure failed: missing block: B:110:0x0309, code lost:

                       r3 = new java.lang.StringBuilder("turn on ");
                       r3.append(r0.name);
                       r3.append(" for exclusive task name : ");
                       com.android.server.VpnManagerService$$ExternalSyntheticOutline0.m(r3, r12.name, "SemExclusiveTaskManagerService");
                       com.android.server.sepunion.SemExclusiveTaskManagerService.Task.m868$$Nest$mturnOnOff(r0, true, r13);
                       r11.executePostProccess(r0, true, r13);
                    */
                    /* JADX WARN: Removed duplicated region for block: B:43:0x0169  */
                    @Override // android.database.ContentObserver
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onChange(boolean r12, android.net.Uri r13, int r14) {
                        /*
                            Method dump skipped, instructions count: 816
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.android.server.sepunion.SemExclusiveTaskManagerService.AnonymousClass2.onChange(boolean,"
                                    + " android.net.Uri, int):void");
                    }
                };
        this.mContext = context;
        Log.d("SemExclusiveTaskManagerService", "SemExclusiveTaskManagerService start");
        Log.d("SemExclusiveTaskManagerService", "init()");
        Log.d("SemExclusiveTaskManagerService", "readExclusiveRelation()");
        try {
            Document parse =
                    DocumentBuilderFactory.newInstance()
                            .newDocumentBuilder()
                            .parse(new File("/system/etc/task_action.xml"));
            if ("task-list".equals(parse.getDocumentElement().getNodeName())
                    && parse.hasChildNodes()) {
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
                    if (item.getNodeType() == 1
                            && "task".equals(item.getNodeName())
                            && item.hasAttributes()) {
                        Task task = new Task();
                        TaskSettings taskSettings = task.setting;
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
                                String nodeValue = item2.getNodeValue();
                                taskSettings.rawKey = nodeValue;
                                String[] split = nodeValue.split("/");
                                String str = split[0];
                                str.getClass();
                                switch (str.hashCode()) {
                                    case -1243020381:
                                        if (str.equals("global")) {
                                            c2 = 0;
                                            break;
                                        }
                                        c2 = 65535;
                                        break;
                                    case -906273929:
                                        if (str.equals("secure")) {
                                            c2 = 1;
                                            break;
                                        }
                                        c2 = 65535;
                                        break;
                                    case -887328209:
                                        if (str.equals("system")) {
                                            c2 = 2;
                                            break;
                                        }
                                        c2 = 65535;
                                        break;
                                    default:
                                        c2 = 65535;
                                        break;
                                }
                                switch (c2) {
                                    case 0:
                                        taskSettings.tableType = 3;
                                        break;
                                    case 1:
                                        taskSettings.tableType = 2;
                                        break;
                                    case 2:
                                        taskSettings.tableType = 1;
                                        break;
                                }
                                taskSettings.key = split[1];
                            } else if (c == 3) {
                                task.exclusiveListSet.addAll(
                                        new ArrayList(
                                                Arrays.asList(
                                                        item2.getNodeValue()
                                                                .replaceAll(" ", "")
                                                                .split(","))));
                            } else if (c == 4) {
                                "1".equals(item2.getNodeValue());
                            }
                        }
                        this.mTaskMap.put(task.name, task);
                        this.mTaskMapByKey.put(taskSettings.rawKey, task);
                    }
                }
            }
        } catch (Exception unused) {
            Log.d("SemExclusiveTaskManagerService", "error during readExclusiveRelation()");
        }
        Iterator it = this.mTaskMap.keySet().iterator();
        while (it.hasNext()) {
            Task task2 = (Task) this.mTaskMap.get((String) it.next());
            Iterator it2 = task2.exclusiveListSet.iterator();
            while (it2.hasNext()) {
                String str2 = (String) it2.next();
                Task task3 = (Task) this.mTaskMap.get(str2);
                if (task3 == null) {
                    DualAppManagerService$$ExternalSyntheticOutline0.m(
                            "### ",
                            str2,
                            " isn't exist in mTaskMap",
                            "SemExclusiveTaskManagerService");
                } else if (!task3.exclusiveListSet.contains(task2.name)) {
                    Log.d(
                            "SemExclusiveTaskManagerService",
                            "### " + task2.name + " doesn't exist in exclusiveListSet of " + str2);
                }
            }
        }
        Task task4 = (Task) this.mTaskMap.get("talkback_se");
        if (task4 != null) {
            task4.componentName =
                    "com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService";
        } else {
            Log.d("SemExclusiveTaskManagerService", "mTaskMap hasn't COMPONENT_NAME_TALKBACK_SE");
        }
        Task task5 = (Task) this.mTaskMap.get("universal_switch");
        if (task5 != null) {
            task5.componentName =
                    "com.samsung.accessibility/.universalswitch.UniversalSwitchService";
        } else {
            Log.d(
                    "SemExclusiveTaskManagerService",
                    "mTaskMap hasn't COMPONENT_NAME_UNIVERSAL_SWITCH");
        }
        Task task6 = (Task) this.mTaskMap.get("talkback");
        if (task6 != null) {
            task6.componentName = "com.google.android.marvin.talkback/.TalkBackService";
        } else {
            Log.d(
                    "SemExclusiveTaskManagerService",
                    "mTaskMap hasn't COMPONENT_NAME_GOOGLE_TALKBACK");
        }
        Task task7 = (Task) this.mTaskMap.get("assistant_menu");
        if (task7 != null) {
            task7.componentName =
                    "com.samsung.accessibility/.assistantmenu.serviceframework.AssistantMenuService";
        } else {
            Log.d(
                    "SemExclusiveTaskManagerService",
                    "mTaskMap hasn't COMPONENT_NAME_ASSISTANT_MENU");
        }
        if (this.mTaskMap.isEmpty()) {
            Log.d("SemExclusiveTaskManagerService", "mTaskMap is empty");
        } else {
            this.mContext
                    .getContentResolver()
                    .unregisterContentObserver(this.mExclusiveTaskObserver);
            Iterator it3 = this.mTaskMapByKey.keySet().iterator();
            while (it3.hasNext()) {
                TaskSettings taskSettings2 =
                        ((Task) this.mTaskMapByKey.get((String) it3.next())).setting;
                ContentResolver contentResolver = this.mContext.getContentResolver();
                AnonymousClass2 anonymousClass2 = this.mExclusiveTaskObserver;
                int i4 = taskSettings2.tableType;
                if (i4 == 1) {
                    contentResolver.registerContentObserver(
                            Settings.System.getUriFor(taskSettings2.key),
                            false,
                            anonymousClass2,
                            -1);
                } else if (i4 == 2) {
                    contentResolver.registerContentObserver(
                            Settings.Secure.getUriFor(taskSettings2.key),
                            false,
                            anonymousClass2,
                            -1);
                } else if (i4 == 3) {
                    contentResolver.registerContentObserver(
                            Settings.Global.getUriFor(taskSettings2.key),
                            false,
                            anonymousClass2,
                            -1);
                }
            }
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.System.getUriFor("accreset_state"),
                            false,
                            this.mExclusiveTaskObserver,
                            -1);
        }
        userSwitch(0);
        this.mContext.registerReceiverAsUser(
                this.mUserSwitchedReceiver,
                UserHandle.ALL,
                new IntentFilter("android.intent.action.USER_SWITCHED"),
                null,
                null);
    }

    public static String getChangedA11yServiceName(Set set) {
        Iterator it = set.iterator();
        while (it.hasNext()) {
            ComponentName componentName = (ComponentName) it.next();
            Log.d(
                    "SemExclusiveTaskManagerService",
                    "name : " + componentName.flattenToShortString());
            if ("com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService"
                    .equals(componentName.flattenToShortString())) {
                Log.d("SemExclusiveTaskManagerService", "talkback se status has changed");
                return "talkback_se";
            }
            if ("com.google.android.marvin.talkback/.TalkBackService"
                    .equals(componentName.flattenToShortString())) {
                Log.d("SemExclusiveTaskManagerService", "talkback status has changed");
                return "talkback";
            }
            if ("com.samsung.accessibility/.universalswitch.UniversalSwitchService"
                    .equals(componentName.flattenToShortString())) {
                Log.d("SemExclusiveTaskManagerService", "universal switch status has changed");
                return "universal_switch";
            }
            if ("com.samsung.accessibility/.assistantmenu.serviceframework.AssistantMenuService"
                    .equals(componentName.flattenToShortString())) {
                Log.d("SemExclusiveTaskManagerService", "assistant menu status has changed");
                return "assistant_menu";
            }
        }
        return null;
    }

    public static void setGrayScale(Context context, boolean z) {
        AccessibilityManager accessibilityManager =
                (AccessibilityManager) context.getSystemService("accessibility");
        if (accessibilityManager != null) {
            if (z) {
                accessibilityManager.semSetMdnieAccessibilityMode(4, true);
            } else {
                accessibilityManager.semSetMdnieAccessibilityMode(1, false);
            }
        }
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(
                "\n"
                    + "##### SemExclusiveTaskManagerService #####\n"
                    + "##### (dumpsys sepunion exclusivetask) #####\n");
        Iterator it = this.mTaskMapByKey.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Task task = (Task) this.mTaskMapByKey.get((String) it.next());
            StringBuilder sb = new StringBuilder("task[");
            sb.append(i);
            sb.append("] : ");
            sb.append(task.name);
            sb.append(", key : ");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    sb, task.setting.rawKey, printWriter);
            i++;
        }
        for (Integer num : this.mUserStatus.keySet()) {
            num.intValue();
            UserStatus userStatus = (UserStatus) this.mUserStatus.get(num);
            StringBuilder m =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("user id : "),
                            userStatus.mUserId,
                            printWriter,
                            "mExclusiveTaskMap size : ");
            m.append(userStatus.mExclusiveTaskMap.size());
            printWriter.println(m.toString());
            Iterator it2 = userStatus.mExclusiveTaskMap.keySet().iterator();
            while (it2.hasNext()) {
                ExclusiveTask exclusiveTask =
                        (ExclusiveTask) userStatus.mExclusiveTaskMap.get((String) it2.next());
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("exclusiveTask name : "),
                        exclusiveTask.name,
                        printWriter);
                Iterator it3 = exclusiveTask.manipulatedList.iterator();
                int i2 = 0;
                while (it3.hasNext()) {
                    printWriter.println(
                            "manipulated task[" + i2 + "] name : " + ((String) it3.next()));
                    i2++;
                }
            }
        }
    }

    public final void executePostProccess(Task task, boolean z, UserStatus userStatus) {
        int i;
        float f;
        Log.d(
                "SemExclusiveTaskManagerService",
                "executePostProccess() name : " + task.name + ", status : " + z);
        String str = task.name;
        str.getClass();
        f = FullScreenMagnificationGestureHandler.MAX_SCALE;
        switch (str) {
            case "remove_animation":
                IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
                if (!z) {
                    f = 1.0f;
                }
                try {
                    windowManagerService.setAnimationScale(0, f);
                    windowManagerService.setAnimationScale(1, f);
                    windowManagerService.setAnimationScale(2, f);
                } catch (RemoteException unused) {
                    Log.d(
                            "SemExclusiveTaskManagerService",
                            "error during calling setAnimationScale");
                }
                if (z) {
                    userStatus.mPreReduceAnimationValue =
                            Settings.System.getIntForUser(
                                    this.mContext.getContentResolver(),
                                    "reduce_animations",
                                    0,
                                    userStatus.mUserId);
                    Settings.System.putIntForUser(
                            this.mContext.getContentResolver(),
                            "reduce_animations",
                            1,
                            userStatus.mUserId);
                    break;
                } else if (userStatus.mPreReduceAnimationValue == 0) {
                    Settings.System.putIntForUser(
                            this.mContext.getContentResolver(),
                            "reduce_animations",
                            0,
                            userStatus.mUserId);
                    break;
                }
                break;
            case "talkback":
            case "talkback_se":
            case "tap_duration":
                updateProfile(task.name);
                break;
            case "one_handed_mode":
                IWindowManager windowManagerService2 =
                        WindowManagerGlobal.getWindowManagerService();
                try {
                    if (!z) {
                        windowManagerService2.removeKeyCustomizationInfo(1106, 8, 3);
                    } else if (Settings.System.getIntForUser(
                                    this.mContext.getContentResolver(),
                                    "one_handed_op_wakeup_type",
                                    0,
                                    userStatus.mUserId)
                            != 0) {
                        Intent intent = new Intent();
                        intent.setComponent(ComponentName.unflattenFromString("onehand/onehand"));
                        windowManagerService2.putKeyCustomizationInfo(
                                new SemWindowManager.KeyCustomizationInfo(8, 1106, 3, 0, intent));
                    }
                } catch (RemoteException unused2) {
                    Log.d(
                            "SemExclusiveTaskManagerService",
                            "Exception occurs when remove OneHandedMode Key");
                }
                Intent intent2 =
                        new Intent("com.samsung.intent.action.ONEHAND_REDUCE_SCREEN_STATUS");
                intent2.putExtra("is_enabled", z);
                this.mContext.sendBroadcast(intent2);
                break;
            case "magnification_shortcut":
                if (z) {
                    for (i = 0; i < this.mMagnificationShortcut.size(); i++) {
                        if (((Integer) this.mMagnificationShortcut.get(i)).intValue() == 4) {
                            Settings.Secure.putIntForUser(
                                    this.mContext.getContentResolver(),
                                    "accessibility_display_magnification_enabled",
                                    1,
                                    userStatus.mUserId);
                        } else {
                            ShortcutUtils.optInValueToSettings(
                                    this.mContext,
                                    ((Integer) this.mMagnificationShortcut.get(i)).intValue(),
                                    "com.android.server.accessibility.MagnificationController");
                        }
                    }
                    this.mMagnificationShortcut.clear();
                    break;
                } else {
                    if (ShortcutUtils.isComponentIdExistingInSettings(
                            this.mContext,
                            1,
                            "com.android.server.accessibility.MagnificationController")) {
                        this.mMagnificationShortcut.add(1);
                        ShortcutUtils.optOutValueFromSettings(
                                this.mContext,
                                1,
                                "com.android.server.accessibility.MagnificationController");
                    }
                    if (ShortcutUtils.isComponentIdExistingInSettings(
                            this.mContext,
                            2,
                            "com.android.server.accessibility.MagnificationController")) {
                        this.mMagnificationShortcut.add(2);
                        ShortcutUtils.optOutValueFromSettings(
                                this.mContext,
                                2,
                                "com.android.server.accessibility.MagnificationController");
                    }
                    if (ShortcutUtils.isComponentIdExistingInSettings(
                            this.mContext,
                            512,
                            "com.android.server.accessibility.MagnificationController")) {
                        this.mMagnificationShortcut.add(512);
                        ShortcutUtils.optOutValueFromSettings(
                                this.mContext,
                                512,
                                "com.android.server.accessibility.MagnificationController");
                    }
                    if (Settings.Secure.getIntForUser(
                                    this.mContext.getContentResolver(),
                                    "accessibility_display_magnification_enabled",
                                    0,
                                    userStatus.mUserId)
                            == 1) {
                        this.mMagnificationShortcut.add(4);
                        Settings.Secure.putIntForUser(
                                this.mContext.getContentResolver(),
                                "accessibility_display_magnification_enabled",
                                0,
                                userStatus.mUserId);
                        break;
                    }
                }
                break;
            case "blue_light_filter":
                try {
                    Intent intent3 = new Intent();
                    intent3.setComponent(
                            new ComponentName(
                                    "com.samsung.android.bluelightfilter",
                                    "com.samsung.android.bluelightfilter.BlueLightFilterService"));
                    intent3.putExtra("BLUE_LIGHT_FILTER_SERVICE_TYPE", z ? 21 : 22);
                    this.mContext.startServiceAsUser(intent3, UserHandle.CURRENT);
                    break;
                } catch (Exception unused3) {
                    Log.d(
                            "SemExclusiveTaskManagerService",
                            "error during starting BlueLightFilterService");
                    return;
                }
            case "mute_all_sound":
                Intent intent4 = new Intent("android.settings.ALL_SOUND_MUTE");
                intent4.putExtra("mute", z ? 1 : 0);
                this.mContext.sendBroadcastAsUser(intent4, UserHandle.ALL);
                updateProfile("mute_all_sound");
                break;
            case "interaction_control":
                Intent intent5 = new Intent("android.intent.action.MAIN");
                intent5.setComponent(
                        ComponentName.unflattenFromString(
                                "com.samsung.accessibility/.interactioncontrol.AccessControlMainService"));
                if (z) {
                    this.mContext.startServiceAsUser(intent5, UserHandle.CURRENT);
                    break;
                } else {
                    this.mContext.stopServiceAsUser(intent5, UserHandle.CURRENT);
                    break;
                }
            case "color_lens":
                AccessibilityManager accessibilityManager =
                        AccessibilityManager.getInstance(this.mContext);
                if (z) {
                    accessibilityManager.semEnableMdnieColorFilter(
                            Settings.Secure.getIntForUser(
                                    this.mContext.getContentResolver(),
                                    "color_lens_type",
                                    0,
                                    userStatus.mUserId),
                            Settings.Secure.getIntForUser(
                                    this.mContext.getContentResolver(),
                                    "color_lens_opacity",
                                    0,
                                    userStatus.mUserId));
                    break;
                } else {
                    accessibilityManager.semDisableMdnieColorFilter();
                    break;
                }
            case "color_blind":
                AccessibilityManager accessibilityManager2 =
                        AccessibilityManager.getInstance(this.mContext);
                if (Settings.Secure.getIntForUser(
                                this.mContext.getContentResolver(),
                                "color_adjustment_type",
                                2,
                                userStatus.mUserId)
                        == 0) {
                    setGrayScale(this.mContext, z);
                    break;
                } else {
                    setGrayScale(this.mContext, false);
                    accessibilityManager2.semSetMdnieColorBlind(
                            z,
                            Settings.Secure.getFloatForUser(
                                    this.mContext.getContentResolver(),
                                    "color_blind_user_parameter",
                                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                                    userStatus.mUserId));
                    break;
                }
        }
    }

    public final void finalize() {}

    public final Set getEnabledServicesFromSettings(Context context, boolean z) {
        String str;
        if (z) {
            str =
                    Settings.Secure.getStringForUser(
                            context.getContentResolver(), "enabled_accessibility_services", -2);
        } else {
            UserStatus userStatus = getUserStatus(ActivityManager.getCurrentUser());
            str = userStatus != null ? userStatus.mPreA11yServiceValue : null;
        }
        if (str == null) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet();
        TextUtils.SimpleStringSplitter simpleStringSplitter =
                new TextUtils.SimpleStringSplitter(':');
        simpleStringSplitter.setString(str);
        while (simpleStringSplitter.hasNext()) {
            ComponentName unflattenFromString =
                    ComponentName.unflattenFromString(simpleStringSplitter.next());
            if (unflattenFromString != null) {
                hashSet.add(unflattenFromString);
            }
        }
        return hashSet;
    }

    public final List getExclusiveTaskList(String str) {
        Log.d("SemExclusiveTaskManagerService", "getExclusiveTaskList for : " + str);
        ArrayList arrayList = new ArrayList();
        Task task = (Task) this.mTaskMap.get(str);
        if (task != null) {
            UserStatus userStatus = getUserStatus(ActivityManager.getCurrentUser());
            Iterator it = task.exclusiveListSet.iterator();
            while (it.hasNext()) {
                Task task2 = (Task) this.mTaskMap.get((String) it.next());
                if (task2 != null && task2.getSettingsStatus(userStatus)) {
                    VpnManagerService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("getExclusiveTaskList add : "),
                            task2.name,
                            "SemExclusiveTaskManagerService");
                    arrayList.add(task2.name);
                }
            }
        }
        return arrayList;
    }

    public final AbsSemSystemService getSemSystemService(String str) {
        return null;
    }

    public final UserStatus getUserStatus(int i) {
        if (this.mUserStatus.containsKey(Integer.valueOf(i))) {
            return (UserStatus) this.mUserStatus.get(Integer.valueOf(i));
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                i, "no user status for ", "SemExclusiveTaskManagerService");
        return null;
    }

    public final boolean isAccessibilityServiceEnabled(
            Context context, ComponentName componentName) {
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

    public final boolean isAccessibilityServiceEnabled(Context context, String str) {
        return isAccessibilityServiceEnabled(context, ComponentName.unflattenFromString(str));
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onBootPhase(int i) {}

    public final void onCleanupUser(int i) {}

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onCreate(Bundle bundle) {
        Log.d("SemExclusiveTaskManagerService", "SemExclusiveTaskManagerService onCreate()");
    }

    public final void onDestroy() {}

    public final void onStart() {
        Log.d("SemExclusiveTaskManagerService", "SemExclusiveTaskManagerService onStart()");
    }

    public final void onStartUser(int i) {}

    public final void onStopUser(int i) {}

    public final void onSwitchUser(int i) {}

    public final void onUnlockUser(int i) {}

    public final void setAccessibilityServiceState(
            Context context, ComponentName componentName, boolean z, int i) {
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
        Settings.Secure.putStringForUser(
                context.getContentResolver(), "enabled_accessibility_services", sb.toString(), i);
        Settings.Secure.putIntForUser(
                context.getContentResolver(),
                "accessibility_enabled",
                !enabledServicesFromSettings.isEmpty() ? 1 : 0,
                i);
    }

    public final void updateProfile(String str) {
        if ("tap_duration".equals(str)) {
            AccessibilityUtils.updateProfile(this.mContext, "tap_duration");
            return;
        }
        if ("mute_all_sound".equals(str)) {
            AccessibilityUtils.updateProfile(
                    this.mContext,
                    "com.android.settings/com.samsung.android.settings.accessibility.shortcut.MuteAllShortcut");
        } else if ("talkback".equals(str)) {
            AccessibilityUtils.updateProfile(
                    this.mContext, "com.google.android.marvin.talkback/.TalkBackService");
        } else if ("talkback_se".equals(str)) {
            AccessibilityUtils.updateProfile(
                    this.mContext,
                    "com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService");
        }
    }

    public final void userSwitch(int i) {
        if (this.mUserStatus.containsKey(Integer.valueOf(i))) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                    i, "already has user status for ", "SemExclusiveTaskManagerService");
            return;
        }
        UserStatus userStatus = new UserStatus();
        userStatus.mUserId = 0;
        userStatus.mIsA11yReset = false;
        userStatus.mExclusiveTaskMap = new HashMap();
        userStatus.mIgnoreSettingsList = new HashSet();
        this.mUserStatus.put(Integer.valueOf(i), userStatus);
        userStatus.mUserId = i;
        userStatus.mPreA11yServiceValue =
                Settings.Secure.getStringForUser(
                        this.mContext.getContentResolver(),
                        "enabled_accessibility_services",
                        userStatus.mUserId);
    }
}
