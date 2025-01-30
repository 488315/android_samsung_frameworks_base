package com.android.server.am;

import android.R;
import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.INetd;
import android.os.RemoteException;
import android.os.UserHandle;
import android.p005os.IInstalld;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.database.MARsComponentTracker;
import com.android.server.am.mars.database.MARsVersionManager;
import com.android.server.am.mars.filter.filter.AccessibilityAppFilter;
import com.android.server.am.mars.filter.filter.DefaultAppFilter;
import com.android.server.am.mars.util.UidStateMgr;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class BaseRestrictionMgr {
    public static String TAG = "BaseRestrictionMgr";
    public Context mContext;
    public final ArrayList mRestrictActivityTheme;

    public abstract class BaseRestrictionMgrHolder {
        public static final BaseRestrictionMgr INSTANCE = new BaseRestrictionMgr();
    }

    public final boolean isBlockAssociatedActivity(ActivityInfo activityInfo) {
        if (MARsPolicyManager.getInstance().checkIsChinaModel() && activityInfo != null && activityInfo.taskAffinity != null && this.mRestrictActivityTheme.contains(Integer.valueOf(activityInfo.theme))) {
            if (MARsVersionManager.getInstance().isAdjustRestrictionMatch(27, activityInfo.taskAffinity, null, null)) {
                return true;
            }
            String[] split = activityInfo.taskAffinity.split(XmlUtils.STRING_ARRAY_SEPARATOR);
            if (split.length <= 1) {
                Slog.e(TAG, "Failed to analyze taskAffinity: [" + activityInfo.taskAffinity + "]");
                return false;
            }
            if (MARsVersionManager.getInstance().isAdjustRestrictionMatch(27, split[1], null, null)) {
                return true;
            }
        }
        return false;
    }

    public BaseRestrictionMgr() {
        this.mContext = null;
        this.mRestrictActivityTheme = new ArrayList() { // from class: com.android.server.am.BaseRestrictionMgr.1
            {
                add(Integer.valueOf(R.style.Theme.Translucent.NoTitleBar));
            }
        };
    }

    public static BaseRestrictionMgr getInstance() {
        return BaseRestrictionMgrHolder.INSTANCE;
    }

    public final void setContext(Context context) {
        this.mContext = context;
    }

    public void init(Context context) {
        setContext(context);
    }

    public final int getRestrictionsByCurrentLevel(int i, boolean z) {
        switch (i) {
            case 2:
                if (MARsPolicyManager.getInstance().isChinaPolicyEnabled()) {
                    if (!MARsPolicyManager.getInstance().getScreenOnState() || !z) {
                    }
                }
                break;
            case 3:
                if (!MARsPolicyManager.getInstance().isChinaPolicyEnabled()) {
                }
                break;
            case 4:
                if (!MARsPolicyManager.getInstance().isChinaPolicyEnabled()) {
                }
                break;
        }
        return 1344014472;
    }

    public boolean isRestrictedPackage(ComponentName componentName, String str, int i, String str2, Intent intent, int i2, String str3, int i3, int i4) {
        return isRestrictedPackage(componentName, str, i, str2, intent, null, i2, false, false, null, str3, i3, i4);
    }

    public boolean isRestrictedPackage(ComponentName componentName, String str, int i, String str2, Intent intent, int i2, ActivityInfo activityInfo, String str3, int i3, int i4) {
        return isRestrictedPackage(componentName, str, i, str2, intent, null, i2, false, false, activityInfo, str3, i3, i4);
    }

    public boolean isRestrictedPackage(ComponentName componentName, String str, int i, String str2, Intent intent, String str3, int i2, boolean z, boolean z2, String str4, int i3, int i4) {
        return isRestrictedPackage(componentName, str, i, str2, intent, str3, i2, z, z2, null, str4, i3, i4);
    }

    /* JADX WARN: Code restructure failed: missing block: B:376:0x0526, code lost:
    
        if (isPolicyBlockedPackage(r27, r28, r26, r29, r30, r20, r32) > 0) goto L328;
     */
    /* JADX WARN: Removed duplicated region for block: B:260:0x038b A[Catch: all -> 0x06a2, TryCatch #0 {, blocks: (B:17:0x0047, B:19:0x0051, B:21:0x0059, B:23:0x0065, B:24:0x0078, B:26:0x0085, B:29:0x009a, B:31:0x00a0, B:33:0x00aa, B:35:0x00b9, B:36:0x00b2, B:41:0x00bc, B:44:0x00d0, B:46:0x00d8, B:48:0x00e4, B:51:0x00f8, B:53:0x00fe, B:55:0x010a, B:57:0x011d, B:58:0x0113, B:64:0x0121, B:66:0x013c, B:69:0x0148, B:70:0x014f, B:72:0x0156, B:73:0x015c, B:76:0x0170, B:78:0x0179, B:79:0x018c, B:84:0x0194, B:87:0x01a0, B:88:0x01a7, B:90:0x01ae, B:91:0x01b4, B:94:0x01c8, B:97:0x01cc, B:100:0x01d4, B:103:0x01e0, B:104:0x01e7, B:106:0x01ee, B:107:0x01f4, B:110:0x0208, B:113:0x020c, B:116:0x021c, B:118:0x0222, B:120:0x0232, B:123:0x0242, B:128:0x024b, B:131:0x0255, B:133:0x025b, B:136:0x0261, B:138:0x0266, B:141:0x0272, B:143:0x0277, B:146:0x0283, B:148:0x0288, B:151:0x0294, B:153:0x0299, B:157:0x02a7, B:159:0x02af, B:166:0x0534, B:169:0x0548, B:170:0x0555, B:172:0x055f, B:175:0x056b, B:177:0x0574, B:178:0x0589, B:181:0x05c3, B:182:0x05ca, B:184:0x05d1, B:185:0x05d8, B:188:0x05ec, B:190:0x0582, B:194:0x05f4, B:196:0x05fa, B:197:0x0603, B:200:0x0609, B:201:0x0641, B:222:0x02c2, B:224:0x02c6, B:228:0x02d9, B:230:0x02dd, B:234:0x02e5, B:236:0x02e9, B:238:0x02ef, B:240:0x02f7, B:242:0x0306, B:246:0x033b, B:248:0x0344, B:251:0x034e, B:253:0x0357, B:255:0x035b, B:258:0x0387, B:260:0x038b, B:264:0x039a, B:266:0x039e, B:269:0x03ae, B:271:0x03b2, B:283:0x03ca, B:286:0x03dd, B:289:0x03e3, B:291:0x03e7, B:295:0x03fa, B:297:0x03fe, B:301:0x0406, B:303:0x040c, B:307:0x0424, B:311:0x042e, B:313:0x043a, B:317:0x0446, B:319:0x044b, B:323:0x0453, B:325:0x0458, B:329:0x0464, B:331:0x0469, B:333:0x0473, B:337:0x047b, B:339:0x0480, B:350:0x04a4, B:352:0x04ae, B:356:0x04bd, B:358:0x04c9, B:362:0x04ea, B:364:0x04ee, B:368:0x04fa, B:370:0x04fe, B:373:0x050e, B:375:0x0512), top: B:16:0x0047 }] */
    /* JADX WARN: Removed duplicated region for block: B:266:0x039e A[Catch: all -> 0x06a2, TryCatch #0 {, blocks: (B:17:0x0047, B:19:0x0051, B:21:0x0059, B:23:0x0065, B:24:0x0078, B:26:0x0085, B:29:0x009a, B:31:0x00a0, B:33:0x00aa, B:35:0x00b9, B:36:0x00b2, B:41:0x00bc, B:44:0x00d0, B:46:0x00d8, B:48:0x00e4, B:51:0x00f8, B:53:0x00fe, B:55:0x010a, B:57:0x011d, B:58:0x0113, B:64:0x0121, B:66:0x013c, B:69:0x0148, B:70:0x014f, B:72:0x0156, B:73:0x015c, B:76:0x0170, B:78:0x0179, B:79:0x018c, B:84:0x0194, B:87:0x01a0, B:88:0x01a7, B:90:0x01ae, B:91:0x01b4, B:94:0x01c8, B:97:0x01cc, B:100:0x01d4, B:103:0x01e0, B:104:0x01e7, B:106:0x01ee, B:107:0x01f4, B:110:0x0208, B:113:0x020c, B:116:0x021c, B:118:0x0222, B:120:0x0232, B:123:0x0242, B:128:0x024b, B:131:0x0255, B:133:0x025b, B:136:0x0261, B:138:0x0266, B:141:0x0272, B:143:0x0277, B:146:0x0283, B:148:0x0288, B:151:0x0294, B:153:0x0299, B:157:0x02a7, B:159:0x02af, B:166:0x0534, B:169:0x0548, B:170:0x0555, B:172:0x055f, B:175:0x056b, B:177:0x0574, B:178:0x0589, B:181:0x05c3, B:182:0x05ca, B:184:0x05d1, B:185:0x05d8, B:188:0x05ec, B:190:0x0582, B:194:0x05f4, B:196:0x05fa, B:197:0x0603, B:200:0x0609, B:201:0x0641, B:222:0x02c2, B:224:0x02c6, B:228:0x02d9, B:230:0x02dd, B:234:0x02e5, B:236:0x02e9, B:238:0x02ef, B:240:0x02f7, B:242:0x0306, B:246:0x033b, B:248:0x0344, B:251:0x034e, B:253:0x0357, B:255:0x035b, B:258:0x0387, B:260:0x038b, B:264:0x039a, B:266:0x039e, B:269:0x03ae, B:271:0x03b2, B:283:0x03ca, B:286:0x03dd, B:289:0x03e3, B:291:0x03e7, B:295:0x03fa, B:297:0x03fe, B:301:0x0406, B:303:0x040c, B:307:0x0424, B:311:0x042e, B:313:0x043a, B:317:0x0446, B:319:0x044b, B:323:0x0453, B:325:0x0458, B:329:0x0464, B:331:0x0469, B:333:0x0473, B:337:0x047b, B:339:0x0480, B:350:0x04a4, B:352:0x04ae, B:356:0x04bd, B:358:0x04c9, B:362:0x04ea, B:364:0x04ee, B:368:0x04fa, B:370:0x04fe, B:373:0x050e, B:375:0x0512), top: B:16:0x0047 }] */
    /* JADX WARN: Removed duplicated region for block: B:271:0x03b2 A[Catch: all -> 0x06a2, TryCatch #0 {, blocks: (B:17:0x0047, B:19:0x0051, B:21:0x0059, B:23:0x0065, B:24:0x0078, B:26:0x0085, B:29:0x009a, B:31:0x00a0, B:33:0x00aa, B:35:0x00b9, B:36:0x00b2, B:41:0x00bc, B:44:0x00d0, B:46:0x00d8, B:48:0x00e4, B:51:0x00f8, B:53:0x00fe, B:55:0x010a, B:57:0x011d, B:58:0x0113, B:64:0x0121, B:66:0x013c, B:69:0x0148, B:70:0x014f, B:72:0x0156, B:73:0x015c, B:76:0x0170, B:78:0x0179, B:79:0x018c, B:84:0x0194, B:87:0x01a0, B:88:0x01a7, B:90:0x01ae, B:91:0x01b4, B:94:0x01c8, B:97:0x01cc, B:100:0x01d4, B:103:0x01e0, B:104:0x01e7, B:106:0x01ee, B:107:0x01f4, B:110:0x0208, B:113:0x020c, B:116:0x021c, B:118:0x0222, B:120:0x0232, B:123:0x0242, B:128:0x024b, B:131:0x0255, B:133:0x025b, B:136:0x0261, B:138:0x0266, B:141:0x0272, B:143:0x0277, B:146:0x0283, B:148:0x0288, B:151:0x0294, B:153:0x0299, B:157:0x02a7, B:159:0x02af, B:166:0x0534, B:169:0x0548, B:170:0x0555, B:172:0x055f, B:175:0x056b, B:177:0x0574, B:178:0x0589, B:181:0x05c3, B:182:0x05ca, B:184:0x05d1, B:185:0x05d8, B:188:0x05ec, B:190:0x0582, B:194:0x05f4, B:196:0x05fa, B:197:0x0603, B:200:0x0609, B:201:0x0641, B:222:0x02c2, B:224:0x02c6, B:228:0x02d9, B:230:0x02dd, B:234:0x02e5, B:236:0x02e9, B:238:0x02ef, B:240:0x02f7, B:242:0x0306, B:246:0x033b, B:248:0x0344, B:251:0x034e, B:253:0x0357, B:255:0x035b, B:258:0x0387, B:260:0x038b, B:264:0x039a, B:266:0x039e, B:269:0x03ae, B:271:0x03b2, B:283:0x03ca, B:286:0x03dd, B:289:0x03e3, B:291:0x03e7, B:295:0x03fa, B:297:0x03fe, B:301:0x0406, B:303:0x040c, B:307:0x0424, B:311:0x042e, B:313:0x043a, B:317:0x0446, B:319:0x044b, B:323:0x0453, B:325:0x0458, B:329:0x0464, B:331:0x0469, B:333:0x0473, B:337:0x047b, B:339:0x0480, B:350:0x04a4, B:352:0x04ae, B:356:0x04bd, B:358:0x04c9, B:362:0x04ea, B:364:0x04ee, B:368:0x04fa, B:370:0x04fe, B:373:0x050e, B:375:0x0512), top: B:16:0x0047 }] */
    /* JADX WARN: Removed duplicated region for block: B:279:0x03ac  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x0398  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isRestrictedPackage(ComponentName componentName, String str, int i, String str2, Intent intent, String str3, int i2, boolean z, boolean z2, ActivityInfo activityInfo, String str4, int i3, int i4) {
        int i5;
        int i6;
        boolean z3;
        boolean z4;
        FreecessPkgStatus freecessPkgStatus;
        int i7;
        int i8;
        int i9;
        MARsPackageInfo mARsPackageInfo;
        int i10;
        int i11;
        MARsPackageInfo mARsPackageInfo2;
        int i12;
        int i13;
        boolean z5;
        boolean z6;
        int i14;
        MARsPackageInfo mARsPackageInfo3;
        boolean z7;
        boolean z8;
        boolean z9;
        int i15;
        boolean z10;
        boolean z11;
        boolean z12;
        boolean z13;
        int i16;
        boolean z14;
        boolean z15;
        SparseArray sparseArray;
        SparseArray sparseArray2;
        String packageName = componentName.getPackageName();
        if (MARsPolicyManager.isIntentProhibited(intent, str)) {
            return true;
        }
        if ("com.google.android.projection.gearhead".equals(str) && "bindService".equals(str2)) {
            MARsPolicyManager.getInstance().onAppUsed(packageName, i2);
        }
        if (intent != null && "android.intent.action.MEDIA_BUTTON".equals(intent.getAction())) {
            MARsPolicyManager.getInstance().onAppUsedForSpecificCase(packageName, i2);
        }
        synchronized (MARsPolicyManager.MARsLock) {
            MARsPackageInfo mARsPackageInfo4 = (!MARsComponentTracker.getInstance().getEnabled() || MARsPolicyManager.getInstance().mMARsTargetPackages == null || MARsPolicyManager.getInstance().mMARsTargetPackages.size() == 0) ? null : MARsPolicyManager.getInstance().getMARsPackageInfo(MARsPolicyManager.getInstance().mMARsTargetPackages, packageName, i2);
            int i17 = -1;
            if (!FreecessController.getInstance().getFreecessEnabled() || (sparseArray2 = (SparseArray) FreecessController.getInstance().mFreezedPackages.getUserIdMap().get(packageName)) == null) {
                i5 = -1;
                i6 = -1;
                z3 = false;
                z4 = false;
                freecessPkgStatus = null;
            } else {
                freecessPkgStatus = null;
                for (int i18 = 0; i18 < sparseArray2.size(); i18++) {
                    freecessPkgStatus = (FreecessPkgStatus) sparseArray2.valueAt(i18);
                    if (freecessPkgStatus.name == null) {
                        Slog.e(TAG, "Abnomal case in isRestrictedPackage package name is null");
                    } else {
                        i6 = freecessPkgStatus.userId;
                        if (i2 != i6 && i2 != -1) {
                        }
                        i5 = freecessPkgStatus.uid;
                        z3 = true;
                        break;
                    }
                }
                i5 = -1;
                i6 = -1;
                z3 = false;
                z4 = z3;
            }
            if (!z3 && MARsPolicyManager.getInstance().mMARsRestrictedPackages != null && MARsPolicyManager.getInstance().mMARsRestrictedPackages.size() != 0 && (sparseArray = (SparseArray) MARsPolicyManager.getInstance().mMARsRestrictedPackages.getMap().get(packageName)) != null) {
                for (int i19 = 0; i19 < sparseArray.size(); i19++) {
                    MARsPackageInfo mARsPackageInfo5 = (MARsPackageInfo) sparseArray.valueAt(i19);
                    if (mARsPackageInfo5.getName() == null) {
                        Slog.e(TAG, "Abnomal case in isRestrictedPackage package name is null");
                        i17 = -1;
                    } else {
                        if (i2 != mARsPackageInfo5.getUserId()) {
                            i17 = -1;
                            if (i2 == -1) {
                            }
                        } else {
                            i17 = -1;
                        }
                        int userId = mARsPackageInfo5.getUserId();
                        int uid = mARsPackageInfo5.getUid();
                        i9 = !mARsPackageInfo5.getFASEnabled() ? 1 : 0;
                        i7 = uid;
                        mARsPackageInfo = mARsPackageInfo5;
                        z3 = true;
                        i8 = userId;
                        break;
                    }
                }
            }
            i7 = i5;
            i8 = i6;
            i9 = i17;
            mARsPackageInfo = null;
            if (!z3) {
                if (MARsComponentTracker.getInstance().getEnabled()) {
                    MARsComponentTracker.getInstance().sendCTInfo(mARsPackageInfo4 != null ? mARsPackageInfo4.getCurLevel() : i17, i4, i3, packageName, str, str2, intent != null ? intent.getAction() : "", System.currentTimeMillis(), str4);
                }
                if ("provider".equals(str2)) {
                    FreecessController.getInstance().protectFreezePackage(packageName, i8, str2, 1000L);
                }
                return false;
            }
            if (z && mARsPackageInfo != null && !z4) {
                if (MARsComponentTracker.getInstance().getEnabled()) {
                    MARsComponentTracker.getInstance().sendCTInfo(mARsPackageInfo4 != null ? mARsPackageInfo4.getCurLevel() : i17, i4, i3, packageName, str, str2, intent != null ? intent.getAction() : "", System.currentTimeMillis(), str4);
                }
                return false;
            }
            if (mARsPackageInfo != null && mARsPackageInfo.getAppliedPolicy() == null && freecessPkgStatus == null) {
                if (MARsComponentTracker.getInstance().getEnabled()) {
                    MARsComponentTracker.getInstance().sendCTInfo(mARsPackageInfo4 != null ? mARsPackageInfo4.getCurLevel() : i17, i4, i3, packageName, str, str2, intent != null ? intent.getAction() : "", System.currentTimeMillis(), str4);
                }
                return false;
            }
            int autorunForFreezedPackage = z4 ? MARsPolicyManager.getInstance().getAutorunForFreezedPackage(packageName, i8) : i9;
            int i20 = (mARsPackageInfo == null || mARsPackageInfo.getAppliedPolicy() == null) ? z4 ? 4 : 0 : mARsPackageInfo.getAppliedPolicy().num;
            if (mARsPackageInfo != null) {
                i10 = mARsPackageInfo.getCurLevel();
            } else {
                i10 = (z4 && autorunForFreezedPackage == 0) ? 2 : 1;
            }
            if (freecessPkgStatus != null && freecessPkgStatus.isFreezedByCalm) {
                i10 = 8;
            }
            int restrictionsByCurrentLevel = getRestrictionsByCurrentLevel(i10, z4 && freecessPkgStatus != null && freecessPkgStatus.freezedRecord.isLcdOnFreezed);
            if (i20 == 4 && (restrictionsByCurrentLevel & 0) != 0) {
                z3 = false;
            }
            if ((33554432 & restrictionsByCurrentLevel) != 0 && !MARsPolicyManager.getInstance().getScreenOnState()) {
                z3 = false;
            }
            if ((67108864 & restrictionsByCurrentLevel) != 0 && MARsPolicyManager.getInstance().getScreenOnState()) {
                z3 = false;
            }
            if ((134217728 & restrictionsByCurrentLevel) != 0 && !MARsPolicyManager.getInstance().getCarModeOnState()) {
                z3 = false;
            }
            if ((268435456 & restrictionsByCurrentLevel) != 0 && MARsPolicyManager.getInstance().getCarModeOnState()) {
                z3 = false;
            }
            if (z3) {
                if ("activity".equals(str2)) {
                    if ((restrictionsByCurrentLevel & 1) != 0) {
                        i11 = i20;
                        mARsPackageInfo2 = mARsPackageInfo;
                        i12 = i8;
                        z6 = false;
                        z9 = false;
                    } else {
                        if ((restrictionsByCurrentLevel & 2) != 0) {
                            z15 = MARsPolicyManager.getInstance().isForegroundPackage(str, i);
                            z8 = true;
                        } else {
                            z15 = false;
                            z8 = false;
                        }
                        if ((restrictionsByCurrentLevel & 4) != 0) {
                            if (isSystemPackage(str, i)) {
                                z15 = true;
                            }
                            z8 = true;
                        }
                        if ((restrictionsByCurrentLevel & 8) == 0 || isSelfIntent(packageName, str) || !(isBlockAssociatedActivity(activityInfo) || MARsVersionManager.getInstance().isAdjustRestrictionMatch(8, componentName.getClassName(), null, null))) {
                            z9 = z15;
                            i11 = i20;
                            mARsPackageInfo2 = mARsPackageInfo;
                            i12 = i8;
                            z6 = false;
                            i13 = -1;
                        } else {
                            Slog.d(TAG, "Block activity: " + activityInfo.toString() + " --- isBlockAssociatedActivity which is not started by itself.");
                            z9 = z15;
                            i11 = i20;
                            mARsPackageInfo2 = mARsPackageInfo;
                            i12 = i8;
                            z6 = false;
                        }
                    }
                    z8 = true;
                    i13 = -1;
                } else {
                    if (!"startService".equals(str2) && !"bindService".equals(str2)) {
                        if ("provider".equals(str2)) {
                            if ((restrictionsByCurrentLevel & 256) != 0) {
                                i11 = i20;
                                mARsPackageInfo2 = mARsPackageInfo;
                                i12 = i8;
                                i13 = -1;
                                i16 = restrictionsByCurrentLevel;
                                if (isPolicyBlockedPackage(str, i, componentName, str2, intent, i7, i11) > 0) {
                                    z14 = true;
                                    if ((i16 & 512) == 0) {
                                        if (MARsPolicyManager.getInstance().isForegroundPackage(str, i)) {
                                            z14 = true;
                                        }
                                        z8 = true;
                                    } else {
                                        z8 = false;
                                    }
                                    if ((i16 & 1024) != 0) {
                                        z9 = z14;
                                    } else if (isSystemPackage(str, i)) {
                                        z8 = true;
                                        z9 = true;
                                    } else {
                                        z9 = z14;
                                        z8 = true;
                                    }
                                    if ((i16 & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0) {
                                        if (isPolicyBlockedPackage(str, i, componentName, str2, intent, i7, i11) > 0) {
                                            z9 = true;
                                        }
                                        z6 = false;
                                        z8 = true;
                                    }
                                    z6 = false;
                                }
                            } else {
                                i11 = i20;
                                mARsPackageInfo2 = mARsPackageInfo;
                                i12 = i8;
                                i13 = -1;
                                i16 = restrictionsByCurrentLevel;
                            }
                            z14 = false;
                            if ((i16 & 512) == 0) {
                            }
                            if ((i16 & 1024) != 0) {
                            }
                            if ((i16 & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0) {
                            }
                            z6 = false;
                        } else {
                            int i21 = i20;
                            mARsPackageInfo2 = mARsPackageInfo;
                            i12 = i8;
                            i13 = -1;
                            if (INetd.IF_FLAG_BROADCAST.equals(str2)) {
                                if (intent != null && (restrictionsByCurrentLevel & IInstalld.FLAG_USE_QUOTA) == 0) {
                                    if ((restrictionsByCurrentLevel & IInstalld.FLAG_FORCE) != 0) {
                                        z10 = MARsPolicyManager.getInstance().isForegroundPackage(str, i);
                                        z11 = true;
                                    } else {
                                        z10 = false;
                                        z11 = false;
                                    }
                                    if ((restrictionsByCurrentLevel & 16384) != 0) {
                                        if (isSystemPackage(str, i)) {
                                            z10 = true;
                                        }
                                        z11 = true;
                                    }
                                    if ((32768 & restrictionsByCurrentLevel) != 0) {
                                        if ("com.google.android.c2dm.intent.RECEIVE".equalsIgnoreCase(intent.getAction())) {
                                            z6 = true;
                                            z13 = true;
                                        } else {
                                            z13 = z10;
                                            z6 = false;
                                        }
                                        z12 = true;
                                    } else {
                                        z12 = z11;
                                        z13 = z10;
                                        z6 = false;
                                    }
                                    if ((131072 & restrictionsByCurrentLevel) != 0) {
                                        if (i21 == 1 && "com.google.android.c2dm.intent.RECEIVE".equalsIgnoreCase(intent.getAction()) && "foreground".equals(str3)) {
                                            z13 = true;
                                        }
                                        z12 = true;
                                    }
                                    if ((524288 & restrictionsByCurrentLevel) != 0) {
                                        if (isSelfIntent(packageName, str)) {
                                            z13 = true;
                                        }
                                        z12 = true;
                                    }
                                    if ((1048576 & restrictionsByCurrentLevel) != 0) {
                                        if (isEssentialIntent(packageName, str, intent.getAction())) {
                                            z13 = true;
                                        }
                                        z12 = true;
                                    }
                                    if ((262144 & restrictionsByCurrentLevel) != 0) {
                                        if (MARsPolicyManager.getInstance().getScreenOnState() && isCurrentLauncherApp(str)) {
                                            z13 = true;
                                        }
                                        z12 = true;
                                    }
                                    if ((1073741824 & restrictionsByCurrentLevel) == 0) {
                                        z9 = z13;
                                        z8 = z12;
                                        i11 = i21;
                                    } else if (isSpageApp(str)) {
                                        i11 = i21;
                                        z8 = true;
                                        z9 = true;
                                    } else {
                                        z9 = z13;
                                        i11 = i21;
                                        z8 = true;
                                    }
                                }
                                i15 = i21;
                                i11 = i15;
                                z6 = false;
                                z9 = false;
                                z8 = true;
                            } else {
                                i15 = i21;
                                if (!"backup".equals(str2) || (16777216 & restrictionsByCurrentLevel) == 0) {
                                    i11 = i15;
                                    z6 = false;
                                    z8 = false;
                                    z9 = false;
                                } else {
                                    i11 = i15;
                                    z6 = false;
                                    z9 = false;
                                    z8 = true;
                                }
                            }
                        }
                    }
                    mARsPackageInfo2 = mARsPackageInfo;
                    i12 = i8;
                    i13 = -1;
                    int i22 = i20;
                    if ((restrictionsByCurrentLevel & 16) != 0) {
                        i11 = i22;
                        z7 = isPolicyBlockedPackage(str, i, componentName, str2, intent, i7, i22) > 0;
                        z8 = true;
                    } else {
                        i11 = i22;
                        z7 = false;
                        z8 = false;
                    }
                    if ((restrictionsByCurrentLevel & 32) != 0) {
                        if (MARsPolicyManager.getInstance().isForegroundPackage(str, i)) {
                            z7 = true;
                        }
                        z8 = true;
                    }
                    if ((restrictionsByCurrentLevel & 64) == 0) {
                        z9 = z7;
                    } else if (isSystemPackage(str, i)) {
                        z8 = true;
                        z9 = true;
                    } else {
                        z9 = z7;
                        z8 = true;
                    }
                    if ((restrictionsByCurrentLevel & 128) != 0) {
                    }
                    z6 = false;
                }
                if (!z8) {
                    z9 = true;
                }
                z5 = !z9;
            } else {
                i11 = i20;
                mARsPackageInfo2 = mARsPackageInfo;
                i12 = i8;
                i13 = -1;
                z5 = z3;
                z6 = false;
            }
            if (z5 || !z6) {
                i14 = i12;
            } else {
                i14 = i12;
                MARsPolicyManager.getInstance().cancelPolicy(packageName, 1, i14);
            }
            if (FreecessController.getInstance().getFreecessEnabled() && FreecessController.getInstance().isFreezedPackage(packageName, i14) && !z5) {
                if ("provider".equals(str2)) {
                    FreecessController.getInstance().protectFreezePackage(packageName, i14, str2, 1000L);
                } else {
                    FreecessController.getInstance().unFreezePackage(packageName, i14, str2);
                }
                Slog.d(TAG, "Package: " + packageName + ", userid: " + i14 + ", hostingType: " + str2 + " is allowed by freecess, caller is: " + str);
                if (MARsComponentTracker.getInstance().getEnabled()) {
                    MARsComponentTracker.getInstance().sendCTInfo(mARsPackageInfo4 != null ? mARsPackageInfo4.getCurLevel() : i13, i4, i3, packageName, str, str2, intent != null ? intent.getAction() : "", System.currentTimeMillis(), str4);
                }
                return false;
            }
            if (!z5 && (mARsPackageInfo3 = mARsPackageInfo2) != null && !mARsPackageInfo3.isSCPMTarget()) {
                MARsPolicyManager.getInstance().levelChange(IInstalld.FLAG_USE_QUOTA, mARsPackageInfo3);
            }
            if (MARsDebugConfig.DEBUG_MARs && z5) {
                Slog.d(TAG, "Package: " + packageName + ", userid: " + i14 + ", hostingType: " + str2 + " is Restricted by policy: " + i11 + " caller is: " + str);
            }
            if (!MARsPolicyManager.getInstance().getCarModeOnState() && MARsPolicyManager.App_StartUp_History && this.mContext.getUserId() == i14) {
                MARsComponentTracker.getInstance().trackComponent(z5, str2, str, packageName, autorunForFreezedPackage, i7, z4);
            }
            if (MARsComponentTracker.getInstance().getEnabled() && !z5) {
                MARsComponentTracker.getInstance().sendCTInfo(mARsPackageInfo4 != null ? mARsPackageInfo4.getCurLevel() : i13, i4, i3, packageName, str, str2, intent != null ? intent.getAction() : "", System.currentTimeMillis(), str4);
            }
            return z5;
        }
    }

    public Intent getLaunchIntentForPackage(String str, int i) {
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
        intent2.setClassName(((ResolveInfo) queryIntentActivitiesAsUser.get(0)).activityInfo.packageName, ((ResolveInfo) queryIntentActivitiesAsUser.get(0)).activityInfo.name);
        return intent2;
    }

    public final int isPolicyBlockedPackage(String str, int i, ComponentName componentName, String str2, Intent intent, int i2, int i3) {
        String packageName = componentName.getPackageName();
        int userId = UserHandle.getUserId(i2);
        if (str == null) {
            if (MARsDebugConfig.DEBUG_MARs) {
                Slog.d(TAG, "Blocked by policy:" + i3 + " -- Caller is null!!");
            }
            return -1;
        }
        if (str.equals(packageName) && !FreecessController.getInstance().isCalmModeOnoff()) {
            return 1;
        }
        if (i3 != 4 && !FreecessController.getInstance().isCalmModeOnoff()) {
            if (MARsPolicyManager.getInstance().checkIsChinaModel() && !UidStateMgr.getInstance().isUidGone(i2)) {
                return 1;
            }
            if (!MARsPolicyManager.getInstance().checkIsChinaModel() && UidStateMgr.getInstance().isUidRunning(i2)) {
                return 1;
            }
        }
        if ((("startService".equals(str2) || "bindService".equals(str2)) && isShouldSkipCaseForPolicy(str, i, packageName, intent)) || "com.sec.android.app.samsungapps".equals(packageName)) {
            return 1;
        }
        if (isLauncherableApp(packageName, userId) && isLauncherableApp(str, i)) {
            if (!isSamsungService(str, i)) {
                if (MARsDebugConfig.DEBUG_MARs) {
                    Slog.d(TAG, "is Blocked by Policy:" + i3 + " -- Caller is not samsung!!");
                }
                return -1;
            }
            if (FreecessController.getInstance().isCalmModeOnoff()) {
                if (MARsDebugConfig.DEBUG_MARs) {
                    Slog.d(TAG, "is Blocked by Policy:" + i3 + " -- isCalmMode!!");
                }
                return -1;
            }
        }
        if (isShouldBlockCase(str, packageName, intent)) {
            if (MARsDebugConfig.DEBUG_MARs) {
                Slog.d(TAG, "Blocked by policy:" + i3 + " -- should Block cases!!");
            }
            return -1;
        }
        if (!MARsPolicyManager.getInstance().isChinaPolicyEnabled() || !"bindService".equals(str2)) {
            return 2;
        }
        if (i3 != 4 && isJobSchedulerPackage(str, componentName, userId)) {
            if (MARsDebugConfig.DEBUG_MARs) {
                Slog.d(TAG, "Blocked by policy:" + i3 + " -- JobSchedulerPackage!!");
            }
            return -1;
        }
        if (isSyncManagerPackage(str, componentName, intent)) {
            if (MARsDebugConfig.DEBUG_MARs) {
                Slog.d(TAG, "Blocked by policy:" + i3 + " -- SyncManagerPackage!!");
            }
            return -1;
        }
        if (!isBindNotificationListenerPackage(str, componentName, userId)) {
            return 2;
        }
        if (MARsDebugConfig.DEBUG_MARs) {
            Slog.d(TAG, "Blocked by policy:" + i3 + " -- isBindNotificationListenerPackage!!");
        }
        return -1;
    }

    public final boolean isEssentialIntent(String str, String str2, String str3) {
        return MARsVersionManager.getInstance().isAdjustRestrictionMatch(3, str, str2, str3);
    }

    public final boolean isSelfIntent(String str, String str2) {
        if (str == null || !str.equals(str2)) {
            return false;
        }
        if (!MARsDebugConfig.DEBUG_MARs) {
            return true;
        }
        Slog.v(TAG, "isSelfIntent :" + str);
        return true;
    }

    public final boolean isCurrentLauncherApp(String str) {
        if (str == null || !str.equals(DefaultAppFilter.getInstance().getDefaultHomePackage())) {
            return false;
        }
        Slog.d(TAG, "Call from Current Launcher app :" + str);
        return true;
    }

    public final boolean isSpageApp(String str) {
        if (str == null || !str.equals("com.samsung.android.app.spage")) {
            return false;
        }
        Slog.d(TAG, "Call from spage app :" + str);
        return true;
    }

    public final boolean isShouldSkipCaseForPolicy(String str, int i, String str2, Intent intent) {
        String action = (intent == null || intent.getAction() == null) ? null : intent.getAction();
        if ("android".equals(str) && action == null && AccessibilityAppFilter.getInstance().isEnabledAccessibilityApp(str2)) {
            Slog.d(TAG, "isShouldSkipCase: Enable AccessibilityService callee = " + str2);
            return true;
        }
        if (MARsPolicyManager.getInstance().isChinaPolicyEnabled() && MARsVersionManager.getInstance().isAdjustRestrictionMatch(1, str2, str, action)) {
            return true;
        }
        if (!MARsPolicyManager.getInstance().isChinaPolicyEnabled() || !MARsVersionManager.getInstance().isAdjustRestrictionMatch(7, str2, str, action) || !MARsPolicyManager.getInstance().isForegroundPackage(str, i)) {
            return false;
        }
        Slog.d(TAG, "isShouldSkipCase: Foreground caller and callee = " + str2);
        return true;
    }

    public final boolean isSamsungService(String str, int i) {
        if ("system".equals(str) || str.startsWith("com.sec.") || str.startsWith("com.samsung.")) {
            if (MARsDebugConfig.DEBUG_MARs) {
                Slog.d(TAG, "isSamsungService -- SamsungService:" + str);
            }
            return true;
        }
        if ("com.baidu.BaiduMap".equals(str) || "com.baidu.searchbox_samsung".equals(str) || "com.baidu.netdisk_ss".equals(str) || !isSystemPackage(str, i)) {
            return false;
        }
        if (MARsDebugConfig.DEBUG_MARs) {
            Slog.d(TAG, "isSamsungService -- SystemPackage:" + str);
        }
        return true;
    }

    public final boolean isLauncherableApp(String str, int i) {
        if (!"com.baidu.searchbox_samsung".equals(str) && !"com.bst.floatingmsgproxy".equals(str)) {
            try {
                if (getLaunchIntentForPackage(str, i) == null) {
                    if (MARsPolicyManager.getInstance().isMARsTarget(str, i)) {
                        if (MARsDebugConfig.DEBUG_MARs) {
                            Slog.d(TAG, "AutoRun Policy isLauncherableApp -- Not launcherable 3rd party package:" + str);
                        }
                        return true;
                    }
                    if (!MARsDebugConfig.DEBUG_MARs) {
                        return false;
                    }
                    Slog.d(TAG, "AutoRun Policy isLauncherableApp -- Not launcherable system package:" + str);
                    return false;
                }
            } catch (Exception e) {
                Slog.e(TAG, "isLaucherableApp exception=" + e);
            }
        }
        return true;
    }

    public final boolean isShouldBlockCase(String str, String str2, Intent intent) {
        String action = (intent == null || intent.getAction() == null) ? null : intent.getAction();
        if ("android".equals(str) && "android.accounts.AccountAuthenticator".equals(action)) {
            if (intent.getIntExtra("binderCallingUid", 1000) != 1000) {
                Slog.d(TAG, "isShouldBlockCase: block AccountAuthenticator");
                return true;
            }
            Slog.d(TAG, "isShouldBlockCase: not block AccountAuthenticator");
        }
        return MARsPolicyManager.getInstance().isChinaPolicyEnabled() && MARsVersionManager.getInstance().isAdjustRestrictionMatch(2, str2, str, action);
    }

    public final boolean isJobSchedulerPackage(String str, ComponentName componentName, int i) {
        IPackageManager packageManager;
        boolean z = false;
        if (str == null || (packageManager = AppGlobals.getPackageManager()) == null) {
            return false;
        }
        try {
            ServiceInfo serviceInfo = packageManager.getServiceInfo(componentName, 0L, i);
            if (serviceInfo == null || !"android.permission.BIND_JOB_SERVICE".equals(serviceInfo.permission) || !"android".equals(str)) {
                return false;
            }
            z = true;
            Slog.d(TAG, "AutoRun Policy isJobSchedulerPackage -- package = " + componentName.getPackageName());
            return true;
        } catch (RemoteException e) {
            Slog.e(TAG, "isJobSchedulerPackage exception=" + e);
            return z;
        }
    }

    public final boolean isSyncManagerPackage(String str, ComponentName componentName, Intent intent) {
        boolean z = false;
        if (str == null) {
            return false;
        }
        try {
            if (intent.getAction() == null || !"android".equals(str) || !"android.content.SyncAdapter".equals(intent.getAction())) {
                return false;
            }
            z = true;
            Slog.d(TAG, "AutoRun Policy isSyncManagerPackage -- package = " + componentName.getPackageName());
            return true;
        } catch (Exception e) {
            Slog.e(TAG, "isSyncManagerPackage exception=" + e);
            return z;
        }
    }

    public final boolean isBindNotificationListenerPackage(String str, ComponentName componentName, int i) {
        IPackageManager packageManager;
        boolean z = false;
        if (str == null || (packageManager = AppGlobals.getPackageManager()) == null) {
            return false;
        }
        try {
            ServiceInfo serviceInfo = packageManager.getServiceInfo(componentName, 0L, i);
            if (serviceInfo == null || !"android.permission.BIND_NOTIFICATION_LISTENER_SERVICE".equals(serviceInfo.permission) || !"android".equals(str)) {
                return false;
            }
            z = true;
            Slog.d(TAG, "AutoRun Policy isBindNotificationListenerPackage -- package = " + componentName.getPackageName());
            return true;
        } catch (RemoteException e) {
            Slog.e(TAG, "isBindNotificationListenerPackage exception=" + e);
            return z;
        }
    }

    public final boolean isSystemPackage(String str, int i) {
        try {
            ApplicationInfo applicationInfoAsUser = this.mContext.getPackageManager().getApplicationInfoAsUser(str, 0, i);
            if (applicationInfoAsUser == null || (applicationInfoAsUser.flags & 1) == 0) {
                return false;
            }
            return this.mContext.getPackageManager().checkSignatures(str, "android") >= 0;
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e(TAG, "isSystemPackage exception=" + e);
            return false;
        }
    }
}
