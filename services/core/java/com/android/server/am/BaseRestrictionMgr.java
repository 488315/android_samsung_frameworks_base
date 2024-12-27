package com.android.server.am;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Slog;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.database.MARsVersionManager;

import java.util.ArrayList;
import java.util.List;

public final class BaseRestrictionMgr {
    public Context mContext = null;
    public final ArrayList mRestrictActivityTheme =
            new ArrayList() { // from class: com.android.server.am.BaseRestrictionMgr.1
                {
                    add(Integer.valueOf(R.style.Theme.Translucent.NoTitleBar));
                }
            };

    public abstract class BaseRestrictionMgrHolder {
        public static final BaseRestrictionMgr INSTANCE = new BaseRestrictionMgr();
    }

    public final Intent getLaunchIntentForPackage(String str, int i) {
        PackageManager packageManager = this.mContext.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.INFO");
        intent.setPackage(str);
        List queryIntentActivitiesAsUser = packageManager.queryIntentActivitiesAsUser(intent, 0, i);
        if (queryIntentActivitiesAsUser == null || queryIntentActivitiesAsUser.isEmpty()) {
            intent.removeCategory("android.intent.category.INFO");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setPackage(str);
            queryIntentActivitiesAsUser = packageManager.queryIntentActivitiesAsUser(intent, 0, i);
        }
        if (queryIntentActivitiesAsUser == null || queryIntentActivitiesAsUser.isEmpty()) {
            return null;
        }
        Intent intent2 = new Intent(intent);
        intent2.setFlags(268435456);
        intent2.setClassName(
                ((ResolveInfo) queryIntentActivitiesAsUser.get(0)).activityInfo.packageName,
                ((ResolveInfo) queryIntentActivitiesAsUser.get(0)).activityInfo.name);
        return intent2;
    }

    public final boolean isBlockAssociatedActivity(ActivityInfo activityInfo) {
        boolean z = MARsPolicyManager.MARs_ENABLE;
        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
        if (MARsPolicyManager.isChinaModel
                && activityInfo != null
                && activityInfo.taskAffinity != null
                && this.mRestrictActivityTheme.contains(Integer.valueOf(activityInfo.theme))) {
            String[][] strArr = MARsVersionManager.mMARsSettingsInfoDefault;
            MARsVersionManager mARsVersionManager =
                    MARsVersionManager.MARsVersionManagerHolder.INSTANCE;
            if (mARsVersionManager.isAdjustRestrictionMatch(
                    27, activityInfo.taskAffinity, null, null)) {
                return true;
            }
            String[] split = activityInfo.taskAffinity.split(":");
            if (split.length <= 1) {
                Slog.e(
                        "BaseRestrictionMgr",
                        "Failed to analyze taskAffinity: [" + activityInfo.taskAffinity + "]");
                return false;
            }
            if (mARsVersionManager.isAdjustRestrictionMatch(27, split[1], null, null)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isLauncherableApp(int i, String str) {
        if (!"com.baidu.searchbox_samsung".equals(str) && !"com.bst.floatingmsgproxy".equals(str)) {
            try {
                if (getLaunchIntentForPackage(str, i) == null) {
                    boolean z = MARsPolicyManager.MARs_ENABLE;
                    if (MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.isMARsTarget(i, str)) {
                        if (MARsDebugConfig.DEBUG_MARs) {
                            Slog.d(
                                    "BaseRestrictionMgr",
                                    "AutoRun Policy isLauncherableApp -- Not launcherable 3rd party"
                                        + " package:"
                                            + str);
                        }
                        return true;
                    }
                    if (!MARsDebugConfig.DEBUG_MARs) {
                        return false;
                    }
                    Slog.d(
                            "BaseRestrictionMgr",
                            "AutoRun Policy isLauncherableApp -- Not launcherable system package:"
                                    + str);
                    return false;
                }
            } catch (Exception e) {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        e, "isLaucherableApp exception=", "BaseRestrictionMgr");
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x021d, code lost:

       if (com.android.server.am.mars.database.MARsVersionManager.MARsVersionManagerHolder.INSTANCE.isAdjustRestrictionMatch(2, r7, r17, r0) != false) goto L135;
    */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x00c7, code lost:

       if (com.android.server.am.mars.database.MARsVersionManager.MARsVersionManagerHolder.INSTANCE.isAdjustRestrictionMatch(1, r7, r17, r12) != false) goto L63;
    */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0288  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x02b4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:147:0x02db  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x02e9  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0330  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int isPolicyBlockedPackage(
            java.lang.String r17,
            int r18,
            android.content.ComponentName r19,
            java.lang.String r20,
            android.content.Intent r21,
            int r22,
            int r23) {
        /*
            Method dump skipped, instructions count: 831
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.BaseRestrictionMgr.isPolicyBlockedPackage(java.lang.String,"
                    + " int, android.content.ComponentName, java.lang.String,"
                    + " android.content.Intent, int, int):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:298:0x037c, code lost:

       if (isPolicyBlockedPackage(r24, r25, r23, r26, r27, r12, r28) > 0) goto L250;
    */
    /* JADX WARN: Removed duplicated region for block: B:146:0x024d A[Catch: all -> 0x0090, TryCatch #0 {all -> 0x0090, blocks: (B:33:0x0076, B:35:0x007c, B:37:0x0082, B:39:0x008a, B:40:0x0093, B:42:0x009c, B:45:0x00aa, B:47:0x00b0, B:49:0x00ba, B:51:0x00c9, B:52:0x00c2, B:57:0x00cb, B:60:0x00dd, B:62:0x00e3, B:64:0x00eb, B:67:0x00f8, B:69:0x00fe, B:71:0x0108, B:73:0x011b, B:74:0x0112, B:79:0x0120, B:81:0x0130, B:84:0x0138, B:86:0x0141, B:88:0x0152, B:93:0x015b, B:96:0x0163, B:98:0x016c, B:100:0x017d, B:103:0x0182, B:106:0x0188, B:109:0x0190, B:111:0x0199, B:113:0x01aa, B:116:0x01af, B:118:0x01b7, B:120:0x01bb, B:122:0x01c6, B:124:0x01d2, B:129:0x01dc, B:144:0x0248, B:146:0x024d, B:149:0x0256, B:151:0x025b, B:152:0x025d, B:155:0x0260, B:161:0x0267, B:162:0x0268, B:164:0x026b, B:166:0x0273, B:168:0x0277, B:173:0x0289, B:176:0x028e, B:178:0x0294, B:180:0x0298, B:183:0x02a9, B:185:0x02b1, B:188:0x02c7, B:193:0x04e2, B:196:0x04f2, B:197:0x04f9, B:199:0x04ff, B:202:0x0507, B:204:0x0510, B:206:0x0516, B:207:0x0530, B:210:0x0569, B:212:0x0572, B:214:0x0583, B:220:0x058c, B:222:0x0590, B:223:0x0597, B:226:0x059d, B:227:0x05d5, B:279:0x02ff, B:281:0x0308, B:283:0x031a, B:285:0x0323, B:287:0x0327, B:290:0x034e, B:292:0x0352, B:295:0x0365, B:297:0x0369, B:305:0x0385, B:308:0x0397, B:310:0x039b, B:314:0x03aa, B:316:0x03b0, B:320:0x03c7, B:323:0x03ce, B:325:0x03d4, B:327:0x03d8, B:333:0x03eb, B:335:0x03f0, B:339:0x0400, B:341:0x0405, B:344:0x040f, B:346:0x0419, B:351:0x042b, B:354:0x0432, B:356:0x043b, B:368:0x045f, B:370:0x0468, B:374:0x046e, B:376:0x0477, B:378:0x047f, B:382:0x0488, B:384:0x048c, B:388:0x04a8, B:390:0x04ac, B:393:0x04be, B:395:0x04c2, B:406:0x0206, B:410:0x021a, B:414:0x022f, B:416:0x023f, B:154:0x025e), top: B:32:0x0076, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x025b A[Catch: all -> 0x0090, TryCatch #0 {all -> 0x0090, blocks: (B:33:0x0076, B:35:0x007c, B:37:0x0082, B:39:0x008a, B:40:0x0093, B:42:0x009c, B:45:0x00aa, B:47:0x00b0, B:49:0x00ba, B:51:0x00c9, B:52:0x00c2, B:57:0x00cb, B:60:0x00dd, B:62:0x00e3, B:64:0x00eb, B:67:0x00f8, B:69:0x00fe, B:71:0x0108, B:73:0x011b, B:74:0x0112, B:79:0x0120, B:81:0x0130, B:84:0x0138, B:86:0x0141, B:88:0x0152, B:93:0x015b, B:96:0x0163, B:98:0x016c, B:100:0x017d, B:103:0x0182, B:106:0x0188, B:109:0x0190, B:111:0x0199, B:113:0x01aa, B:116:0x01af, B:118:0x01b7, B:120:0x01bb, B:122:0x01c6, B:124:0x01d2, B:129:0x01dc, B:144:0x0248, B:146:0x024d, B:149:0x0256, B:151:0x025b, B:152:0x025d, B:155:0x0260, B:161:0x0267, B:162:0x0268, B:164:0x026b, B:166:0x0273, B:168:0x0277, B:173:0x0289, B:176:0x028e, B:178:0x0294, B:180:0x0298, B:183:0x02a9, B:185:0x02b1, B:188:0x02c7, B:193:0x04e2, B:196:0x04f2, B:197:0x04f9, B:199:0x04ff, B:202:0x0507, B:204:0x0510, B:206:0x0516, B:207:0x0530, B:210:0x0569, B:212:0x0572, B:214:0x0583, B:220:0x058c, B:222:0x0590, B:223:0x0597, B:226:0x059d, B:227:0x05d5, B:279:0x02ff, B:281:0x0308, B:283:0x031a, B:285:0x0323, B:287:0x0327, B:290:0x034e, B:292:0x0352, B:295:0x0365, B:297:0x0369, B:305:0x0385, B:308:0x0397, B:310:0x039b, B:314:0x03aa, B:316:0x03b0, B:320:0x03c7, B:323:0x03ce, B:325:0x03d4, B:327:0x03d8, B:333:0x03eb, B:335:0x03f0, B:339:0x0400, B:341:0x0405, B:344:0x040f, B:346:0x0419, B:351:0x042b, B:354:0x0432, B:356:0x043b, B:368:0x045f, B:370:0x0468, B:374:0x046e, B:376:0x0477, B:378:0x047f, B:382:0x0488, B:384:0x048c, B:388:0x04a8, B:390:0x04ac, B:393:0x04be, B:395:0x04c2, B:406:0x0206, B:410:0x021a, B:414:0x022f, B:416:0x023f, B:154:0x025e), top: B:32:0x0076, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:164:0x026b A[Catch: all -> 0x0090, TryCatch #0 {all -> 0x0090, blocks: (B:33:0x0076, B:35:0x007c, B:37:0x0082, B:39:0x008a, B:40:0x0093, B:42:0x009c, B:45:0x00aa, B:47:0x00b0, B:49:0x00ba, B:51:0x00c9, B:52:0x00c2, B:57:0x00cb, B:60:0x00dd, B:62:0x00e3, B:64:0x00eb, B:67:0x00f8, B:69:0x00fe, B:71:0x0108, B:73:0x011b, B:74:0x0112, B:79:0x0120, B:81:0x0130, B:84:0x0138, B:86:0x0141, B:88:0x0152, B:93:0x015b, B:96:0x0163, B:98:0x016c, B:100:0x017d, B:103:0x0182, B:106:0x0188, B:109:0x0190, B:111:0x0199, B:113:0x01aa, B:116:0x01af, B:118:0x01b7, B:120:0x01bb, B:122:0x01c6, B:124:0x01d2, B:129:0x01dc, B:144:0x0248, B:146:0x024d, B:149:0x0256, B:151:0x025b, B:152:0x025d, B:155:0x0260, B:161:0x0267, B:162:0x0268, B:164:0x026b, B:166:0x0273, B:168:0x0277, B:173:0x0289, B:176:0x028e, B:178:0x0294, B:180:0x0298, B:183:0x02a9, B:185:0x02b1, B:188:0x02c7, B:193:0x04e2, B:196:0x04f2, B:197:0x04f9, B:199:0x04ff, B:202:0x0507, B:204:0x0510, B:206:0x0516, B:207:0x0530, B:210:0x0569, B:212:0x0572, B:214:0x0583, B:220:0x058c, B:222:0x0590, B:223:0x0597, B:226:0x059d, B:227:0x05d5, B:279:0x02ff, B:281:0x0308, B:283:0x031a, B:285:0x0323, B:287:0x0327, B:290:0x034e, B:292:0x0352, B:295:0x0365, B:297:0x0369, B:305:0x0385, B:308:0x0397, B:310:0x039b, B:314:0x03aa, B:316:0x03b0, B:320:0x03c7, B:323:0x03ce, B:325:0x03d4, B:327:0x03d8, B:333:0x03eb, B:335:0x03f0, B:339:0x0400, B:341:0x0405, B:344:0x040f, B:346:0x0419, B:351:0x042b, B:354:0x0432, B:356:0x043b, B:368:0x045f, B:370:0x0468, B:374:0x046e, B:376:0x0477, B:378:0x047f, B:382:0x0488, B:384:0x048c, B:388:0x04a8, B:390:0x04ac, B:393:0x04be, B:395:0x04c2, B:406:0x0206, B:410:0x021a, B:414:0x022f, B:416:0x023f, B:154:0x025e), top: B:32:0x0076, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:192:0x04db  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x04f0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:199:0x04ff A[Catch: all -> 0x0090, TryCatch #0 {all -> 0x0090, blocks: (B:33:0x0076, B:35:0x007c, B:37:0x0082, B:39:0x008a, B:40:0x0093, B:42:0x009c, B:45:0x00aa, B:47:0x00b0, B:49:0x00ba, B:51:0x00c9, B:52:0x00c2, B:57:0x00cb, B:60:0x00dd, B:62:0x00e3, B:64:0x00eb, B:67:0x00f8, B:69:0x00fe, B:71:0x0108, B:73:0x011b, B:74:0x0112, B:79:0x0120, B:81:0x0130, B:84:0x0138, B:86:0x0141, B:88:0x0152, B:93:0x015b, B:96:0x0163, B:98:0x016c, B:100:0x017d, B:103:0x0182, B:106:0x0188, B:109:0x0190, B:111:0x0199, B:113:0x01aa, B:116:0x01af, B:118:0x01b7, B:120:0x01bb, B:122:0x01c6, B:124:0x01d2, B:129:0x01dc, B:144:0x0248, B:146:0x024d, B:149:0x0256, B:151:0x025b, B:152:0x025d, B:155:0x0260, B:161:0x0267, B:162:0x0268, B:164:0x026b, B:166:0x0273, B:168:0x0277, B:173:0x0289, B:176:0x028e, B:178:0x0294, B:180:0x0298, B:183:0x02a9, B:185:0x02b1, B:188:0x02c7, B:193:0x04e2, B:196:0x04f2, B:197:0x04f9, B:199:0x04ff, B:202:0x0507, B:204:0x0510, B:206:0x0516, B:207:0x0530, B:210:0x0569, B:212:0x0572, B:214:0x0583, B:220:0x058c, B:222:0x0590, B:223:0x0597, B:226:0x059d, B:227:0x05d5, B:279:0x02ff, B:281:0x0308, B:283:0x031a, B:285:0x0323, B:287:0x0327, B:290:0x034e, B:292:0x0352, B:295:0x0365, B:297:0x0369, B:305:0x0385, B:308:0x0397, B:310:0x039b, B:314:0x03aa, B:316:0x03b0, B:320:0x03c7, B:323:0x03ce, B:325:0x03d4, B:327:0x03d8, B:333:0x03eb, B:335:0x03f0, B:339:0x0400, B:341:0x0405, B:344:0x040f, B:346:0x0419, B:351:0x042b, B:354:0x0432, B:356:0x043b, B:368:0x045f, B:370:0x0468, B:374:0x046e, B:376:0x0477, B:378:0x047f, B:382:0x0488, B:384:0x048c, B:388:0x04a8, B:390:0x04ac, B:393:0x04be, B:395:0x04c2, B:406:0x0206, B:410:0x021a, B:414:0x022f, B:416:0x023f, B:154:0x025e), top: B:32:0x0076, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:218:0x0588  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x059b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:230:0x05d9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:271:0x04df  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x0352 A[Catch: all -> 0x0090, TryCatch #0 {all -> 0x0090, blocks: (B:33:0x0076, B:35:0x007c, B:37:0x0082, B:39:0x008a, B:40:0x0093, B:42:0x009c, B:45:0x00aa, B:47:0x00b0, B:49:0x00ba, B:51:0x00c9, B:52:0x00c2, B:57:0x00cb, B:60:0x00dd, B:62:0x00e3, B:64:0x00eb, B:67:0x00f8, B:69:0x00fe, B:71:0x0108, B:73:0x011b, B:74:0x0112, B:79:0x0120, B:81:0x0130, B:84:0x0138, B:86:0x0141, B:88:0x0152, B:93:0x015b, B:96:0x0163, B:98:0x016c, B:100:0x017d, B:103:0x0182, B:106:0x0188, B:109:0x0190, B:111:0x0199, B:113:0x01aa, B:116:0x01af, B:118:0x01b7, B:120:0x01bb, B:122:0x01c6, B:124:0x01d2, B:129:0x01dc, B:144:0x0248, B:146:0x024d, B:149:0x0256, B:151:0x025b, B:152:0x025d, B:155:0x0260, B:161:0x0267, B:162:0x0268, B:164:0x026b, B:166:0x0273, B:168:0x0277, B:173:0x0289, B:176:0x028e, B:178:0x0294, B:180:0x0298, B:183:0x02a9, B:185:0x02b1, B:188:0x02c7, B:193:0x04e2, B:196:0x04f2, B:197:0x04f9, B:199:0x04ff, B:202:0x0507, B:204:0x0510, B:206:0x0516, B:207:0x0530, B:210:0x0569, B:212:0x0572, B:214:0x0583, B:220:0x058c, B:222:0x0590, B:223:0x0597, B:226:0x059d, B:227:0x05d5, B:279:0x02ff, B:281:0x0308, B:283:0x031a, B:285:0x0323, B:287:0x0327, B:290:0x034e, B:292:0x0352, B:295:0x0365, B:297:0x0369, B:305:0x0385, B:308:0x0397, B:310:0x039b, B:314:0x03aa, B:316:0x03b0, B:320:0x03c7, B:323:0x03ce, B:325:0x03d4, B:327:0x03d8, B:333:0x03eb, B:335:0x03f0, B:339:0x0400, B:341:0x0405, B:344:0x040f, B:346:0x0419, B:351:0x042b, B:354:0x0432, B:356:0x043b, B:368:0x045f, B:370:0x0468, B:374:0x046e, B:376:0x0477, B:378:0x047f, B:382:0x0488, B:384:0x048c, B:388:0x04a8, B:390:0x04ac, B:393:0x04be, B:395:0x04c2, B:406:0x0206, B:410:0x021a, B:414:0x022f, B:416:0x023f, B:154:0x025e), top: B:32:0x0076, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:297:0x0369 A[Catch: all -> 0x0090, TryCatch #0 {all -> 0x0090, blocks: (B:33:0x0076, B:35:0x007c, B:37:0x0082, B:39:0x008a, B:40:0x0093, B:42:0x009c, B:45:0x00aa, B:47:0x00b0, B:49:0x00ba, B:51:0x00c9, B:52:0x00c2, B:57:0x00cb, B:60:0x00dd, B:62:0x00e3, B:64:0x00eb, B:67:0x00f8, B:69:0x00fe, B:71:0x0108, B:73:0x011b, B:74:0x0112, B:79:0x0120, B:81:0x0130, B:84:0x0138, B:86:0x0141, B:88:0x0152, B:93:0x015b, B:96:0x0163, B:98:0x016c, B:100:0x017d, B:103:0x0182, B:106:0x0188, B:109:0x0190, B:111:0x0199, B:113:0x01aa, B:116:0x01af, B:118:0x01b7, B:120:0x01bb, B:122:0x01c6, B:124:0x01d2, B:129:0x01dc, B:144:0x0248, B:146:0x024d, B:149:0x0256, B:151:0x025b, B:152:0x025d, B:155:0x0260, B:161:0x0267, B:162:0x0268, B:164:0x026b, B:166:0x0273, B:168:0x0277, B:173:0x0289, B:176:0x028e, B:178:0x0294, B:180:0x0298, B:183:0x02a9, B:185:0x02b1, B:188:0x02c7, B:193:0x04e2, B:196:0x04f2, B:197:0x04f9, B:199:0x04ff, B:202:0x0507, B:204:0x0510, B:206:0x0516, B:207:0x0530, B:210:0x0569, B:212:0x0572, B:214:0x0583, B:220:0x058c, B:222:0x0590, B:223:0x0597, B:226:0x059d, B:227:0x05d5, B:279:0x02ff, B:281:0x0308, B:283:0x031a, B:285:0x0323, B:287:0x0327, B:290:0x034e, B:292:0x0352, B:295:0x0365, B:297:0x0369, B:305:0x0385, B:308:0x0397, B:310:0x039b, B:314:0x03aa, B:316:0x03b0, B:320:0x03c7, B:323:0x03ce, B:325:0x03d4, B:327:0x03d8, B:333:0x03eb, B:335:0x03f0, B:339:0x0400, B:341:0x0405, B:344:0x040f, B:346:0x0419, B:351:0x042b, B:354:0x0432, B:356:0x043b, B:368:0x045f, B:370:0x0468, B:374:0x046e, B:376:0x0477, B:378:0x047f, B:382:0x0488, B:384:0x048c, B:388:0x04a8, B:390:0x04ac, B:393:0x04be, B:395:0x04c2, B:406:0x0206, B:410:0x021a, B:414:0x022f, B:416:0x023f, B:154:0x025e), top: B:32:0x0076, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:300:0x0382  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x0362  */
    /* JADX WARN: Removed duplicated region for block: B:402:0x04e6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isRestrictedPackage(
            android.content.ComponentName r23,
            java.lang.String r24,
            int r25,
            java.lang.String r26,
            android.content.Intent r27,
            int r28,
            boolean r29,
            android.content.pm.ActivityInfo r30,
            java.lang.String r31,
            int r32,
            int r33) {
        /*
            Method dump skipped, instructions count: 1675
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.BaseRestrictionMgr.isRestrictedPackage(android.content.ComponentName,"
                    + " java.lang.String, int, java.lang.String, android.content.Intent, int,"
                    + " boolean, android.content.pm.ActivityInfo, java.lang.String, int,"
                    + " int):boolean");
    }
}
