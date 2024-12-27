package com.android.server.policy;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;

import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.rune.InputRune;
import com.samsung.android.view.SemWindowManager;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

public final class KeyCustomizationInfoManager {
    public static final int[] ALL_HOT_KEYCODE = {1090, 1091, 1092};
    public final Context mContext;
    public SemWindowManager.KeyCustomizationInfo mGlobalSideKeyDoubleInfo;
    public SemWindowManager.KeyCustomizationInfo mGlobalSideKeyLongInfo;
    public final SparseArray mDownUpMap = new SparseArray();
    public final SparseArray mLongMap = new SparseArray();
    public final SparseArray mDoubleMap = new SparseArray();
    public final SparseArray mTripleMap = new SparseArray();
    public final SparseArray mQuadrupleMap = new SparseArray();
    public final SparseArray mQuintupleMap = new SparseArray();
    public final SparseArray mLastDownUpInfo = new SparseArray();
    public final SparseArray mLastLongInfo = new SparseArray();
    public final SparseArray mLastDoubleInfo = new SparseArray();
    public final SparseArray mLastTripleInfo = new SparseArray();
    public final SparseArray mLastQuadrupleInfo = new SparseArray();
    public final SparseArray mLastQuintupleInfo = new SparseArray();
    public final SparseArray mHotKeyMap = new SparseArray();
    public final Object mLock = new Object();
    public final ArrayList mOwnerPackageList = new ArrayList();
    public int mUserId = 0;
    public final KeyCustomizationInfoXmlUtils mXmlUtils = new KeyCustomizationInfoXmlUtils(this);

    public KeyCustomizationInfoManager(Context context) {
        this.mContext = context;
    }

    public static void dumpKeyCustomizationInfoKeyCodeMap(
            PrintWriter printWriter, SparseArray sparseArray) {
        for (int i = 0; i < sparseArray.size(); i++) {
            SemWindowManager.KeyCustomizationInfo keyCustomizationInfo =
                    (SemWindowManager.KeyCustomizationInfo) sparseArray.valueAt(i);
            if (keyCustomizationInfo != null) {
                printWriter.print("    ");
                printWriter.print("      ");
                printWriter.print(KeyCustomizationManager.idToString(keyCustomizationInfo.id));
                printWriter.print(", keyCode: ");
                printWriter.print(keyCustomizationInfo.keyCode);
                printWriter.print(", ");
                printWriter.print(
                        KeyCustomizationManager.actionToString(keyCustomizationInfo.action));
                int i2 = keyCustomizationInfo.dispatching;
                if (i2 == -1) {
                    printWriter.print(", dispatching: ");
                    printWriter.print(i2);
                }
                int i3 = keyCustomizationInfo.userId;
                if (i3 != -2) {
                    printWriter.print(", userId: ");
                    printWriter.print(i3);
                }
                long j = keyCustomizationInfo.longPressTimeout;
                if (j != 0) {
                    printWriter.print(", longPressTimeout: ");
                    printWriter.print(j);
                }
                long j2 = keyCustomizationInfo.multiPressTimeout;
                if (j2 != 0) {
                    printWriter.print(", multiPressTimeout: ");
                    printWriter.print(j2);
                }
                String str = keyCustomizationInfo.ownerPackage;
                if (!TextUtils.isEmpty(str)) {
                    printWriter.print(", ownerPackage: ");
                    printWriter.println(str);
                    printWriter.print("    ");
                    printWriter.print("        ");
                }
                Intent intent = keyCustomizationInfo.intent;
                if (intent != null) {
                    if (intent.getExtras() != null) {
                        printWriter.print(", showOnKeyguard: ");
                        printWriter.println(
                                keyCustomizationInfo
                                        .intent
                                        .getExtras()
                                        .getBoolean("show_on_keyguard"));
                        printWriter.print("    ");
                        printWriter.print("        ");
                    }
                    printWriter.print(", ");
                    printWriter.println(keyCustomizationInfo.intent);
                } else {
                    printWriter.println(", intent is null.");
                }
            }
        }
    }

    public static SemWindowManager.KeyCustomizationInfo getKodiakPttInfo(int i, String str) {
        Intent intent;
        String str2 =
                i != 1015
                        ? i != 1079 ? null : "com.mcx.intent.action.CRITICAL_COMMUNICATION_SOS_KEY"
                        : "com.mcx.intent.action.CRITICAL_COMMUNICATION_CONTROL_KEY";
        if (TextUtils.isEmpty(str2)) {
            intent = null;
        } else {
            Intent intent2 = new Intent(str2);
            intent2.addFlags(32);
            intent2.setPackage(str);
            intent = intent2;
        }
        if (intent == null) {
            return null;
        }
        return new SemWindowManager.KeyCustomizationInfo(3, 951, i, 2, intent);
    }

    public static SemWindowManager.KeyCustomizationInfo getXCoverTopKeyCustomizationInfo(
            int i, int i2, String str) {
        ComponentName unflattenFromString;
        if (TextUtils.isEmpty(str)
                || (unflattenFromString = ComponentName.unflattenFromString(str)) == null) {
            return null;
        }
        int i3 =
                ("torch/torch".equals(str)
                                || "home/home".equals(str)
                                || "back/back".equals(str)
                                || "quickMessageSender/quickMessageSender".equals(str))
                        ? 0
                        : 1;
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.addFlags(270532608);
        intent.setComponent(unflattenFromString);
        return new SemWindowManager.KeyCustomizationInfo(i, 1103, i2, i3, intent);
    }

    public final void addOwnerPackageList(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Iterator it = this.mOwnerPackageList.iterator();
        while (it.hasNext()) {
            if (str.equals((String) it.next())) {
                return;
            }
        }
        this.mOwnerPackageList.add(str);
    }

    public final boolean checkHomeLongPressInfo() {
        int i;
        SparseArray sparseArray = (SparseArray) getInfoMapLocked(4).get(3);
        boolean z = false;
        if (sparseArray == null) {
            Log.d("KeyCustomizationInfoManager", "There is no home key long press info.");
            return false;
        }
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            SemWindowManager.KeyCustomizationInfo keyCustomizationInfo =
                    (SemWindowManager.KeyCustomizationInfo) sparseArray.valueAt(i2);
            if (keyCustomizationInfo != null
                    && (i = keyCustomizationInfo.id) != 10
                    && i != 30
                    && i != 50) {
                NetworkScoreService$$ExternalSyntheticOutline0.m(
                        i, "checkHomeLongPressInfo, id=", " added", "KeyCustomizationInfoManager");
                arrayList.add(Integer.valueOf(keyCustomizationInfo.id));
            }
        }
        if (arrayList.size() == 0) {
            Log.d("KeyCustomizationInfoManager", "checkHomeLongPressInfo, toRemove size is zero");
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            SemWindowManager.KeyCustomizationInfo keyCustomizationInfo2 =
                    (SemWindowManager.KeyCustomizationInfo) sparseArray.get(num.intValue());
            Log.d(
                    "KeyCustomizationInfoManager",
                    "checkHomeLongPressInfo, remove " + keyCustomizationInfo2);
            if (keyCustomizationInfo2 != null) {
                sparseArray.remove(num.intValue());
                if (num.intValue() == 2003) {
                    String str = keyCustomizationInfo2.ownerPackage;
                    removeOwnerPackageList(str);
                    StringBuilder sb = new StringBuilder("Remove home key long press info, ID(");
                    sb.append(num);
                    sb.append("), ownerPackage=");
                    VpnManagerService$$ExternalSyntheticOutline0.m(
                            sb, str, "KeyCustomizationInfoManager");
                } else {
                    Log.d(
                            "KeyCustomizationInfoManager",
                            "Remove home key long press info, ID(" + num + ")");
                }
                z = true;
            }
        }
        return z;
    }

    public final SemWindowManager.KeyCustomizationInfo get(int i, int i2, int i3, String str) {
        synchronized (this.mLock) {
            try {
                SparseArray sparseArray = (SparseArray) getInfoMapLocked(i2).get(i3);
                SemWindowManager.KeyCustomizationInfo keyCustomizationInfo =
                        sparseArray == null
                                ? null
                                : (SemWindowManager.KeyCustomizationInfo) sparseArray.get(i);
                if (keyCustomizationInfo == null) {
                    return null;
                }
                if (i != 2003
                        || (!TextUtils.isEmpty(str)
                                && str.equals(keyCustomizationInfo.ownerPackage))) {
                    return keyCustomizationInfo;
                }
                return null;
            } finally {
            }
        }
    }

    public final SparseArray getInfoMapLocked(int i) {
        if ((i & 3) != 0) {
            return this.mDownUpMap;
        }
        if ((i & 4) != 0) {
            return this.mLongMap;
        }
        if ((i & 8) != 0) {
            return this.mDoubleMap;
        }
        if ((i & 16) != 0) {
            return this.mTripleMap;
        }
        if ((i & 32) != 0) {
            return this.mQuadrupleMap;
        }
        if ((i & 64) != 0) {
            return this.mQuintupleMap;
        }
        throw new IllegalArgumentException(
                VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Can not find infoMap. which="));
    }

    public final SemWindowManager.KeyCustomizationInfo getLast(int i, int i2) {
        SemWindowManager.KeyCustomizationInfo keyCustomizationInfo;
        synchronized (this.mLock) {
            keyCustomizationInfo =
                    (SemWindowManager.KeyCustomizationInfo) getLastInfoLocked(i).get(i2);
        }
        return keyCustomizationInfo;
    }

    public final SparseArray getLastInfoLocked(int i) {
        if ((i & 3) != 0) {
            return this.mLastDownUpInfo;
        }
        if ((i & 4) != 0) {
            return this.mLastLongInfo;
        }
        if ((i & 8) != 0) {
            return this.mLastDoubleInfo;
        }
        if ((i & 16) != 0) {
            return this.mLastTripleInfo;
        }
        if ((i & 32) != 0) {
            return this.mLastQuadrupleInfo;
        }
        if ((i & 64) != 0) {
            return this.mLastQuintupleInfo;
        }
        throw new IllegalArgumentException(
                VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Can not find lastInfo. which="));
    }

    public final SemWindowManager.KeyCustomizationInfo getQuickLaunchCameraInfoFromSetting(int i) {
        int i2 = Settings.System.getInt(this.mContext.getContentResolver(), "double_tab_launch", 2);
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                i2, "getQuickLaunchCameraInfoFromSetting behavior=", "KeyCustomizationInfoManager");
        if (i2 == 2 || i2 == 0) {
            return null;
        }
        return new SemWindowManager.KeyCustomizationInfo(8, 2001, i, 1);
    }

    public final SemWindowManager.KeyCustomizationInfo getSideKeyDoubleInfoFromGlobalSetting() {
        SideKeyDoublePress.Behavior behavior;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean z =
                Settings.Global.getInt(contentResolver, "function_key_config_doublepress", 1) == 1;
        if (!z) {
            return null;
        }
        int i = Settings.Global.getInt(contentResolver, "function_key_config_doublepress_type", 0);
        String string =
                Settings.Global.getString(contentResolver, "function_key_config_doublepress_value");
        VpnManagerService$$ExternalSyntheticOutline0.m(
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        i,
                        "getSideKeyDoubleInfoFromGlobalSetting enabled=",
                        " type=",
                        " appInfo=",
                        z),
                string,
                "KeyCustomizationInfoManager");
        if (i == 0) {
            behavior =
                    SideKeyDoublePress.getBehavior(
                            "com.sec.android.app.camera/com.sec.android.app.camera.Camera");
        } else if (i == 2) {
            behavior = SideKeyDoublePress.getBehavior(string);
        } else if (i == 3) {
            behavior = SideKeyDoublePress.getBehavior("secureFolder/secureFolder");
        } else if (i != 4) {
            Slog.d("SideKeyDoublePress", "type is not properly.");
            behavior = null;
        } else {
            behavior = SideKeyDoublePress.getBehavior("samsungpay://simplepay/sidekey");
        }
        if (behavior == null) {
            return null;
        }
        return new SemWindowManager.KeyCustomizationInfo(
                8, 1104, 26, behavior.getAction(), behavior.getIntent());
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0043 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.view.SemWindowManager.KeyCustomizationInfo
            getSideKeyLongInfoFromGlobalSetting() {
        /*
            r11 = this;
            android.content.Context r11 = r11.mContext
            android.content.ContentResolver r11 = r11.getContentResolver()
            boolean r0 = com.samsung.android.rune.InputRune.PWM_SIDE_KEY_DIGITAL_ASSISTANT
            r1 = 0
            r2 = 2
            if (r0 == 0) goto Le
            r3 = r2
            goto Lf
        Le:
            r3 = r1
        Lf:
            java.lang.String r4 = "function_key_config_longpress_type"
            int r11 = android.provider.Settings.Global.getInt(r11, r4, r3)
            java.lang.String r3 = "getSideKeyLongInfoFromGlobalSetting sideKeyLongPressType="
            java.lang.String r4 = "KeyCustomizationInfoManager"
            com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0.m(r11, r3, r4)
            r3 = 3
            r4 = 0
            if (r0 == 0) goto L2a
            if (r11 != r2) goto L2a
            java.lang.String r11 = "aiAgentApp/aiAgentApp"
            android.content.ComponentName r11 = android.content.ComponentName.unflattenFromString(r11)
        L28:
            r9 = r3
            goto L41
        L2a:
            if (r11 != 0) goto L34
            java.lang.String r11 = "wakeBixby/wakeBixby"
            android.content.ComponentName r11 = android.content.ComponentName.unflattenFromString(r11)
            goto L28
        L34:
            r0 = 1
            if (r11 != r0) goto L3f
            java.lang.String r11 = "globalAction/globalAction"
            android.content.ComponentName r11 = android.content.ComponentName.unflattenFromString(r11)
            r9 = r1
            goto L41
        L3f:
            r9 = r1
            r11 = r4
        L41:
            if (r11 != 0) goto L44
            return r4
        L44:
            android.content.Intent r10 = new android.content.Intent
            java.lang.String r0 = "android.intent.action.MAIN"
            r10.<init>(r0)
            r10.setComponent(r11)
            com.samsung.android.view.SemWindowManager$KeyCustomizationInfo r11 = new com.samsung.android.view.SemWindowManager$KeyCustomizationInfo
            r7 = 1104(0x450, float:1.547E-42)
            r8 = 26
            r6 = 4
            r5 = r11
            r5.<init>(r6, r7, r8, r9, r10)
            return r11
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.policy.KeyCustomizationInfoManager.getSideKeyLongInfoFromGlobalSetting():com.samsung.android.view.SemWindowManager$KeyCustomizationInfo");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0060, code lost:

       if (r11.equals("com.sprint.sdcplus") == false) goto L14;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.samsung.android.view.SemWindowManager.KeyCustomizationInfo
            getXCoverKeyB2BDeltaInfoFromSetting(int r12) {
        /*
            r11 = this;
            r0 = 1
            r1 = 0
            android.content.Context r11 = r11.mContext
            android.content.ContentResolver r11 = r11.getContentResolver()
            r2 = 1079(0x437, float:1.512E-42)
            r3 = 0
            r4 = 1015(0x3f7, float:1.422E-42)
            if (r12 != r4) goto L14
            java.lang.String r5 = "dedicated_app_xcover_switch"
            java.lang.String r6 = "dedicated_app_xcover"
            goto L1a
        L14:
            if (r12 != r2) goto Lc7
            java.lang.String r5 = "dedicated_app_top_switch"
            java.lang.String r6 = "dedicated_app_top"
        L1a:
            r7 = -2
            int r5 = android.provider.Settings.System.getIntForUser(r11, r5, r1, r7)
            if (r5 != r0) goto Lc7
            java.lang.String r11 = android.provider.Settings.System.getStringForUser(r11, r6, r7)
            boolean r5 = android.text.TextUtils.isEmpty(r11)
            if (r5 == 0) goto L2c
            return r3
        L2c:
            r11.getClass()
            r5 = -1
            int r6 = r11.hashCode()
            switch(r6) {
                case -1849030318: goto L63;
                case -1036550907: goto L5a;
                case -100932730: goto L4f;
                case 1304193381: goto L44;
                case 2030292931: goto L39;
                default: goto L37;
            }
        L37:
            r0 = r5
            goto L6d
        L39:
            java.lang.String r0 = "com.verizon.pushtotalkplus"
            boolean r0 = r11.equals(r0)
            if (r0 != 0) goto L42
            goto L37
        L42:
            r0 = 4
            goto L6d
        L44:
            java.lang.String r0 = "com.att.eptt"
            boolean r0 = r11.equals(r0)
            if (r0 != 0) goto L4d
            goto L37
        L4d:
            r0 = 3
            goto L6d
        L4f:
            java.lang.String r0 = "com.att.firstnet.grey"
            boolean r0 = r11.equals(r0)
            if (r0 != 0) goto L58
            goto L37
        L58:
            r0 = 2
            goto L6d
        L5a:
            java.lang.String r1 = "com.sprint.sdcplus"
            boolean r1 = r11.equals(r1)
            if (r1 != 0) goto L6d
            goto L37
        L63:
            java.lang.String r0 = "com.bell.ptt"
            boolean r0 = r11.equals(r0)
            if (r0 != 0) goto L6c
            goto L37
        L6c:
            r0 = r1
        L6d:
            switch(r0) {
                case 0: goto Lc2;
                case 1: goto Lc2;
                case 2: goto L96;
                case 3: goto Lc2;
                case 4: goto Lc2;
                default: goto L70;
            }
        L70:
            android.content.Intent r10 = new android.content.Intent
            java.lang.String r0 = "com.samsung.android.knox.intent.action.HARD_KEY_REPORT"
            r10.<init>(r0)
            r0 = 16777216(0x1000000, float:2.3509887E-38)
            r10.setFlags(r0)
            java.lang.String r0 = "com.samsung.android.knox.intent.extra.KEY_CODE"
            r10.putExtra(r0, r4)
            boolean r0 = android.text.TextUtils.isEmpty(r11)
            if (r0 != 0) goto L8a
            r10.setPackage(r11)
        L8a:
            com.samsung.android.view.SemWindowManager$KeyCustomizationInfo r11 = new com.samsung.android.view.SemWindowManager$KeyCustomizationInfo
            r6 = 3
            r7 = 951(0x3b7, float:1.333E-42)
            r9 = 2
            r5 = r11
            r8 = r12
            r5.<init>(r6, r7, r8, r9, r10)
            return r11
        L96:
            if (r12 == r4) goto L9f
            if (r12 == r2) goto L9c
            r11 = r3
            goto La1
        L9c:
            java.lang.String r11 = "com.mcx.intent.action.CRITICAL_COMMUNICATION_SOS_KEY"
            goto La1
        L9f:
            java.lang.String r11 = "com.mcx.intent.action.CRITICAL_COMMUNICATION_CONTROL_KEY"
        La1:
            boolean r0 = android.text.TextUtils.isEmpty(r11)
            if (r0 == 0) goto La8
            goto Lc1
        La8:
            android.content.Intent r9 = new android.content.Intent
            r9.<init>(r11)
            java.lang.String r11 = "com.att.firstnet.grey/com.samsung.android.sptt.keyevent.KeyEventService"
            android.content.ComponentName r11 = android.content.ComponentName.unflattenFromString(r11)
            r9.setComponent(r11)
            com.samsung.android.view.SemWindowManager$KeyCustomizationInfo r3 = new com.samsung.android.view.SemWindowManager$KeyCustomizationInfo
            r5 = 3
            r6 = 951(0x3b7, float:1.333E-42)
            r8 = 3
            r4 = r3
            r7 = r12
            r4.<init>(r5, r6, r7, r8, r9)
        Lc1:
            return r3
        Lc2:
            com.samsung.android.view.SemWindowManager$KeyCustomizationInfo r11 = getKodiakPttInfo(r12, r11)
            return r11
        Lc7:
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.policy.KeyCustomizationInfoManager.getXCoverKeyB2BDeltaInfoFromSetting(int):com.samsung.android.view.SemWindowManager$KeyCustomizationInfo");
    }

    public final void init(int i, boolean z) {
        boolean z2;
        synchronized (this.mLock) {
            try {
                long uptimeMillis = SystemClock.uptimeMillis();
                this.mXmlUtils.loadSettingsLocked(i);
                Log.d(
                        "KeyCustomizationInfoManager",
                        "loadSettings duration="
                                + (SystemClock.uptimeMillis() - uptimeMillis)
                                + " version="
                                + this.mXmlUtils.mXmlVersion
                                + " userId="
                                + i
                                + " userSwitching="
                                + z);
                long uptimeMillis2 = SystemClock.uptimeMillis();
                KeyCustomizationInfoXmlUtils.ErrorCode errorCode = this.mXmlUtils.xmlFileErrorCode;
                if (errorCode == KeyCustomizationInfoXmlUtils.ErrorCode.FILE_NOT_FOUND) {
                    initKeyCustomizationInfoLocked(8, 26, true);
                    initKeyCustomizationInfoLocked(4, 26, true);
                    if (InputRune.PWM_ACTIVE_OR_XCOVER_KEY) {
                        initKeyCustomizationInfoLocked(3, 1015, true);
                        if (get(951, 3, 1015, null) == null) {
                            initKeyCustomizationInfoLocked(4, 1015, true);
                        }
                    }
                    if (InputRune.PWM_XCOVER_AND_TOP_KEY) {
                        initKeyCustomizationInfoLocked(3, 1079, true);
                        if (get(951, 3, 1079, null) == null) {
                            initKeyCustomizationInfoLocked(4, 1079, true);
                        }
                    }
                    if (!InputRune.PWM_SIDE_KEY) {
                        initKeyCustomizationInfoLocked(8, 3, true);
                    }
                    KeyCustomizationInfoXmlUtils keyCustomizationInfoXmlUtils = this.mXmlUtils;
                    keyCustomizationInfoXmlUtils.mXmlVersion = 4.1f;
                    if (!z) {
                        keyCustomizationInfoXmlUtils.saveSettingsLocked(i);
                    }
                } else if (errorCode == KeyCustomizationInfoXmlUtils.ErrorCode.SUCCESS) {
                    boolean checkHomeLongPressInfo =
                            InputRune.PWM_HOME_KEY_LONG_PRESS_SEARCLE
                                    ? checkHomeLongPressInfo()
                                    : false;
                    if (!z) {
                        KeyCustomizationInfoXmlUtils keyCustomizationInfoXmlUtils2 = this.mXmlUtils;
                        if (Float.compare(keyCustomizationInfoXmlUtils2.mXmlVersion, 4.1f) == 0) {
                            String str = KeyCustomizationConstants.VOLD_DECRYPT;
                            z2 = false;
                        } else {
                            Log.d(
                                    "KeyCustomizationInfoXmlUtils",
                                    "updateXmlVersion old="
                                            + keyCustomizationInfoXmlUtils2.mXmlVersion
                                            + " new=4.1");
                            keyCustomizationInfoXmlUtils2.mXmlVersion = 4.1f;
                            z2 = true;
                        }
                        checkHomeLongPressInfo |= z2;
                    }
                    if (checkHomeLongPressInfo) {
                        saveSettingsLocked(i);
                    }
                    for (int i2 : KeyCustomizationConstants.SUPPORT_PRESS_TYPE_ALL) {
                        SparseArray infoMapLocked = getInfoMapLocked(i2);
                        for (int i3 = 0; i3 < infoMapLocked.size(); i3++) {
                            updateHigherPriorityInfoLocked(i2, infoMapLocked.keyAt(i3), -1);
                        }
                    }
                } else {
                    Log.e(
                            "KeyCustomizationInfoManager",
                            "Xml file error code was wrong. code="
                                    + this.mXmlUtils.xmlFileErrorCode);
                }
                Log.d(
                        "KeyCustomizationInfoManager",
                        "initKeyCustomizationInfo duration="
                                + (SystemClock.uptimeMillis() - uptimeMillis2));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0037, code lost:

       if (r4 != false) goto L109;
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x007f, code lost:

       if (r4 != false) goto L45;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initKeyCustomizationInfoLocked(int r13, int r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 506
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.policy.KeyCustomizationInfoManager.initKeyCustomizationInfoLocked(int,"
                    + " int, boolean):void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0081, code lost:

       if ("VPP".equals(r6) == false) goto L56;
    */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0092, code lost:

       if ("AIO".equals(r6) == false) goto L56;
    */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00ab, code lost:

       if ("ASR".equals(r6) == false) goto L56;
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00b4, code lost:

       if ("BMC".equals(r6) != false) goto L52;
    */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00b8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00e2 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean initKodiakDedicatedPttApp(
            com.samsung.android.view.SemWindowManager.KeyCustomizationInfo r13, int r14) {
        /*
            Method dump skipped, instructions count: 260
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.policy.KeyCustomizationInfoManager.initKodiakDedicatedPttApp(com.samsung.android.view.SemWindowManager$KeyCustomizationInfo,"
                    + " int):boolean");
    }

    public final void put(SemWindowManager.KeyCustomizationInfo keyCustomizationInfo, boolean z) {
        int size;
        int i;
        if (keyCustomizationInfo == null) {
            return;
        }
        synchronized (this.mLock) {
            try {
                int i2 = keyCustomizationInfo.press;
                int i3 = keyCustomizationInfo.id;
                int i4 = keyCustomizationInfo.keyCode;
                String str = keyCustomizationInfo.ownerPackage;
                SparseArray infoMapLocked = getInfoMapLocked(i2);
                SparseArray sparseArray = (SparseArray) infoMapLocked.get(i4);
                if (sparseArray == null) {
                    sparseArray = new SparseArray();
                    infoMapLocked.put(i4, sparseArray);
                } else if (i3 >= 1000 && (size = sparseArray.size()) > 0) {
                    ArrayList arrayList = new ArrayList();
                    for (int i5 = 0; i5 < size; i5++) {
                        SemWindowManager.KeyCustomizationInfo keyCustomizationInfo2 =
                                (SemWindowManager.KeyCustomizationInfo) sparseArray.valueAt(i5);
                        if (keyCustomizationInfo2 != null
                                && (i = keyCustomizationInfo2.id) >= 1000) {
                            arrayList.add(Integer.valueOf(i));
                        }
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        Integer num = (Integer) it.next();
                        SemWindowManager.KeyCustomizationInfo keyCustomizationInfo3 =
                                (SemWindowManager.KeyCustomizationInfo)
                                        sparseArray.get(num.intValue());
                        if (keyCustomizationInfo3 != null) {
                            String str2 = keyCustomizationInfo3.ownerPackage;
                            sparseArray.remove(num.intValue());
                            if (num.intValue() == 2003) {
                                removeOwnerPackageList(str2);
                            }
                        }
                    }
                }
                sparseArray.put(i3, keyCustomizationInfo);
                updateHigherPriorityInfoLocked(i2, i4, -1);
                if (!z) {
                    saveSettingsLocked();
                }
                if (i3 == 2003) {
                    addOwnerPackageList(str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean remove(int i, int i2, String str, int i3, boolean z) {
        synchronized (this.mLock) {
            try {
                SparseArray sparseArray = (SparseArray) getInfoMapLocked(i2).get(i3);
                if (sparseArray == null) {
                    return false;
                }
                SemWindowManager.KeyCustomizationInfo keyCustomizationInfo =
                        (SemWindowManager.KeyCustomizationInfo) sparseArray.get(i);
                if (keyCustomizationInfo == null) {
                    return false;
                }
                if (i == 2003
                        && !TextUtils.isEmpty(str)
                        && !str.equals(keyCustomizationInfo.ownerPackage)) {
                    Slog.d(
                            "KeyCustomizationInfoManager",
                            "Can not remove data, There is no matched with ownerPackage="
                                    .concat(str));
                    return false;
                }
                sparseArray.remove(i);
                if (i == 2003) {
                    initKeyCustomizationInfoLocked(i2, i3, false);
                    removeOwnerPackageList(str);
                }
                updateHigherPriorityInfoLocked(i2, i3, -1);
                if (!z) {
                    saveSettingsLocked();
                }
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeOwnerPackageList(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        for (int i : KeyCustomizationConstants.SUPPORT_PRESS_TYPE_BASIC) {
            SparseArray infoMapLocked = getInfoMapLocked(i);
            for (int i2 = 0; i2 < infoMapLocked.size(); i2++) {
                SparseArray sparseArray = (SparseArray) infoMapLocked.valueAt(i2);
                if (sparseArray != null) {
                    for (int i3 = 0; i3 < sparseArray.size(); i3++) {
                        SemWindowManager.KeyCustomizationInfo keyCustomizationInfo =
                                (SemWindowManager.KeyCustomizationInfo) sparseArray.valueAt(i3);
                        if (keyCustomizationInfo != null
                                && keyCustomizationInfo.id == 2003
                                && str.equals(keyCustomizationInfo.ownerPackage)) {
                            return;
                        }
                    }
                }
            }
        }
        this.mOwnerPackageList.remove(str);
    }

    public final void saveSettingsLocked() {
        this.mXmlUtils.saveSettingsLocked(this.mUserId);
    }

    public final void saveSettingsLocked(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int currentUser = ActivityManager.getCurrentUser();
            if (this.mUserId != currentUser) {
                Slog.d(
                        "KeyCustomizationInfoManager",
                        "saveSettingsLocked, userId("
                                + this.mUserId
                                + ") is no matched with newId("
                                + currentUser
                                + ")");
                this.mUserId = currentUser;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            this.mXmlUtils.saveSettingsLocked(i);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void updateHigherPriorityInfoLocked(int i, int i2, int i3) {
        int i4;
        if (i3 == -1) {
            SparseArray sparseArray = (SparseArray) getInfoMapLocked(i).get(i2);
            if (sparseArray == null) {
                i3 = -1;
            } else {
                int i5 = 2004;
                for (int i6 = 0; i6 < sparseArray.size(); i6++) {
                    SemWindowManager.KeyCustomizationInfo keyCustomizationInfo =
                            (SemWindowManager.KeyCustomizationInfo) sparseArray.valueAt(i6);
                    if (keyCustomizationInfo != null && i5 > (i4 = keyCustomizationInfo.id)) {
                        i5 = i4;
                    }
                }
                if (i5 == 2004) {
                    i5 = -1;
                }
                i3 = i5;
            }
        }
        getLastInfoLocked(i).remove(i2);
        if (i3 == -1) {
            return;
        }
        SparseArray sparseArray2 = (SparseArray) getInfoMapLocked(i).get(i2);
        SemWindowManager.KeyCustomizationInfo keyCustomizationInfo2 =
                sparseArray2 == null
                        ? null
                        : (SemWindowManager.KeyCustomizationInfo) sparseArray2.get(i3);
        if (keyCustomizationInfo2 == null) {
            return;
        }
        getLastInfoLocked(i).put(i2, keyCustomizationInfo2);
    }
}
