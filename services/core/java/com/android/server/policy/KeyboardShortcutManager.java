package com.android.server.policy;

import android.app.ActivityOptions;
import android.app.StatusBarManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.policy.PhoneWindow;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class KeyboardShortcutManager {
    public static final int[] SHORT_TYPE_LIST = {0, 1, 2, 3, 4};
    public final Context mContext;
    public final PhoneWindowManagerExt mPolicyExt;
    public final HashMap mShortcutMap = new HashMap();
    public final SparseArray mPreloadBehaviorMap = new SparseArray();
    public boolean mIsTriggeredMeta = false;
    public boolean mIsConsumedMeta = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Behavior {
        public final Context mContext;
        public Intent mIntent;
        public final PhoneWindowManagerExt mPolicyExt;

        public Behavior(Context context, PhoneWindowManagerExt phoneWindowManagerExt) {
            this.mContext = context;
            this.mPolicyExt = phoneWindowManagerExt;
        }

        public boolean preCondition() {
            return this.mPolicyExt.externalKeyboardPolicy();
        }

        public void startTargetApp(int i) {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "launch keyboard shortcut app, displayId=", "KeyboardShortcutManager");
            PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
            phoneWindowManagerExt.getClass();
            PhoneWindowManagerExt.getFillInIntent();
            ActivityOptions makeBasic = ActivityOptions.makeBasic();
            makeBasic.setLaunchDisplayId(i);
            this.mContext.startActivityAsUser(this.mIntent, makeBasic.toBundle(), UserHandle.CURRENT);
            PhoneWindow.sendCloseSystemWindows(phoneWindowManagerExt.mPolicy.mContext, (String) null);
            phoneWindowManagerExt.mPolicy.dismissKeyboardShortcutsMenu();
        }

        public final String toString() {
            return "Behavior=" + getClass().getSimpleName() + " intent=" + this.mIntent;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DefaultBehavior extends Behavior {
        public final /* synthetic */ int $r8$classId;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DefaultBehavior(Context context, PhoneWindowManagerExt phoneWindowManagerExt) {
            super(context, phoneWindowManagerExt);
            this.$r8$classId = 3;
            this.mIntent = BatteryService$$ExternalSyntheticOutline0.m(67108864, "android.settings.SETTINGS");
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ DefaultBehavior(Context context, PhoneWindowManagerExt phoneWindowManagerExt, int i) {
            super(context, phoneWindowManagerExt);
            this.$r8$classId = i;
        }

        @Override // com.android.server.policy.KeyboardShortcutManager.Behavior
        public boolean preCondition() {
            switch (this.$r8$classId) {
                case 2:
                    if (this.mPolicyExt.mPolicy.keyguardOn()) {
                        Log.d("KeyboardShortcutManager", "GameBooster is not launched on keyguard");
                        return true;
                    }
                    try {
                        return true ^ this.mContext.getPackageManager().getApplicationInfo("com.samsung.android.game.gametools", 128).metaData.getString("Feature.External.Action", "").contains("togglemenu");
                    } catch (PackageManager.NameNotFoundException unused) {
                        Log.e("KeyboardShortcutManager", "GameBooster not installed");
                        return false;
                    }
                default:
                    return super.preCondition();
            }
        }

        @Override // com.android.server.policy.KeyboardShortcutManager.Behavior
        public void startTargetApp(int i) {
            switch (this.$r8$classId) {
                case 2:
                    Log.d("KeyboardShortcutManager", "send broadcast game booster toggle menu");
                    this.mContext.sendBroadcastAsUser(this.mIntent, UserHandle.SYSTEM);
                    StatusBarManager statusBarManager = (StatusBarManager) this.mContext.getSystemService("statusbar");
                    if (statusBarManager != null) {
                        statusBarManager.collapsePanels();
                    }
                    this.mPolicyExt.mPolicy.dismissKeyboardShortcutsMenu();
                    break;
                default:
                    super.startTargetApp(i);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SFinderBehavior extends Behavior {
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
        public final boolean preCondition() {
            Intent intent = this.mIntent;
            if (intent != null) {
                List semQueryIntentActivitiesAsUser = this.mContext.getPackageManager().semQueryIntentActivitiesAsUser(intent, 0, UserHandle.myUserId());
                if (semQueryIntentActivitiesAsUser != null && semQueryIntentActivitiesAsUser.size() > 0) {
                    PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
                    ComponentName componentName = phoneWindowManagerExt.mTopActivity;
                    if (componentName != null) {
                        String shortString = componentName.toShortString();
                        for (String str : this.mDeniedActivities) {
                            if (str.equals(shortString)) {
                                Slog.d("KeyboardShortcutManager", "canLaunchAppByExternalKeyboard: It is denied activity");
                                return true;
                            }
                        }
                    }
                    return phoneWindowManagerExt.externalKeyboardPolicy();
                }
            }
            Slog.d("KeyboardShortcutManager", "canLaunchAppByExternalKeyboard : SFinder activity is not available");
            return true;
        }
    }

    public KeyboardShortcutManager(Context context, PhoneWindowManagerExt phoneWindowManagerExt) {
        this.mContext = context;
        this.mPolicyExt = phoneWindowManagerExt;
    }

    public static String getSaLoggerEventId(int i) {
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

    public static String typeToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? Integer.toString(i) : "META_Z" : "META_U" : "META_G" : "META_F" : "DEFAULT";
    }

    public final Behavior getBehavior(int i) {
        PhoneWindowManagerExt phoneWindowManagerExt = this.mPolicyExt;
        if (i == 1) {
            return new SFinderBehavior(this.mContext, phoneWindowManagerExt);
        }
        if (i == 2) {
            DefaultBehavior defaultBehavior = new DefaultBehavior(this.mContext, phoneWindowManagerExt, 2);
            Intent intent = new Intent("com.samsung.android.game.gametools.action.togglemenu");
            intent.setPackage("com.samsung.android.game.gametools");
            intent.putExtra("package", "window-g");
            defaultBehavior.mIntent = intent;
            return defaultBehavior;
        }
        if (i != 3) {
            if (i == 4) {
                return new DefaultBehavior(this.mContext, phoneWindowManagerExt);
            }
            DefaultBehavior defaultBehavior2 = new DefaultBehavior(this.mContext, phoneWindowManagerExt, 0);
            defaultBehavior2.mIntent = new Intent("android.intent.action.MAIN");
            return defaultBehavior2;
        }
        DefaultBehavior defaultBehavior3 = new DefaultBehavior(this.mContext, phoneWindowManagerExt, 1);
        Intent intent2 = new Intent("com.samsung.accessibility.ACCESSIBILITY_SETTINGS");
        intent2.setFlags(268468224);
        defaultBehavior3.mIntent = intent2;
        return defaultBehavior3;
    }

    public final String getShortcutSettingsValue(int i) {
        return Settings.System.getStringForUser(this.mContext.getContentResolver(), (String) this.mShortcutMap.get(Integer.valueOf(i)), -2);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0089  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean launch(int r6, int r7, java.lang.String r8) {
        /*
            r5 = this;
            android.util.SparseArray r0 = r5.mPreloadBehaviorMap
            java.lang.Object r0 = r0.get(r7)
            com.android.server.policy.KeyboardShortcutManager$Behavior r0 = (com.android.server.policy.KeyboardShortcutManager.Behavior) r0
            if (r0 != 0) goto Le
            com.android.server.policy.KeyboardShortcutManager$Behavior r0 = r5.getBehavior(r7)
        Le:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "launch type="
            r1.<init>(r2)
            java.lang.String r2 = typeToString(r7)
            r1.append(r2)
            java.lang.String r2 = " componentName="
            r1.append(r2)
            r1.append(r8)
            java.lang.String r2 = " "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "KeyboardShortcutManager"
            android.util.Log.d(r2, r1)
            android.content.Intent r1 = r0.mIntent
            r3 = 1
            r4 = 0
            if (r1 != 0) goto L42
            java.lang.String r5 = "Intent is null"
            android.util.Log.d(r2, r5)
        L40:
            r1 = r4
            goto L80
        L42:
            if (r7 == 0) goto L72
            if (r7 == r3) goto L47
            goto L80
        L47:
            com.android.server.policy.PhoneWindowManagerExt r5 = r5.mPolicyExt
            android.content.ComponentName r5 = r5.mTopActivity
            if (r5 != 0) goto L53
            java.lang.String r5 = "META_F, Unknown top activity!"
            android.util.Slog.d(r2, r5)
            goto L40
        L53:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "META_F, top="
            r7.<init>(r8)
            java.lang.String r8 = r5.toShortString()
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            android.util.Slog.d(r2, r7)
            java.lang.String r7 = "componentname"
            java.lang.String r5 = r5.toString()
            r1.putExtra(r7, r5)
            goto L80
        L72:
            boolean r5 = android.text.TextUtils.isEmpty(r8)
            if (r5 == 0) goto L79
            goto L40
        L79:
            android.content.ComponentName r5 = android.content.ComponentName.unflattenFromString(r8)
            r1.setComponent(r5)
        L80:
            r5 = 0
            if (r1 != 0) goto L89
            java.lang.String r6 = "Can not launch app, intent is null"
            android.util.Log.d(r2, r6)
            return r5
        L89:
            boolean r7 = r0.preCondition()
            if (r7 == 0) goto L91
            r3 = r5
            goto L94
        L91:
            r0.startTargetApp(r6)
        L94:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.policy.KeyboardShortcutManager.launch(int, int, java.lang.String):boolean");
    }
}
