package com.android.server.pm;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.AuxiliaryResolveInfo;
import android.content.pm.InstantAppRequest;
import android.content.pm.InstantAppResolveInfo;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.android.internal.logging.MetricsLogger;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class InstantAppResolver {
    public static final boolean DEBUG_INSTANT = Build.IS_DEBUGGABLE;
    public static MetricsLogger sMetricsLogger;

    /* renamed from: com.android.server.pm.InstantAppResolver$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public final /* synthetic */ Computer val$computer;
        public final /* synthetic */ Context val$context;
        public final /* synthetic */ ActivityInfo val$instantAppInstaller;
        public final /* synthetic */ Intent val$origIntent;
        public final /* synthetic */ InstantAppRequest val$requestObj;
        public final /* synthetic */ Intent val$sanitizedIntent;
        public final /* synthetic */ String val$token;
        public final /* synthetic */ UserManagerService val$userManager;

        public AnonymousClass1(
                Computer computer,
                UserManagerService userManagerService,
                Intent intent,
                String str,
                InstantAppRequest instantAppRequest,
                Intent intent2,
                ActivityInfo activityInfo,
                Context context) {
            this.val$computer = computer;
            this.val$userManager = userManagerService;
            this.val$origIntent = intent;
            this.val$token = str;
            this.val$requestObj = instantAppRequest;
            this.val$sanitizedIntent = intent2;
            this.val$instantAppInstaller = activityInfo;
            this.val$context = context;
        }
    }

    public static Intent buildEphemeralInstallerIntent(
            Intent intent,
            Intent intent2,
            Intent intent3,
            String str,
            String str2,
            Bundle bundle,
            String str3,
            int i,
            ComponentName componentName,
            String str4,
            boolean z,
            List list) {
        Intent intent4;
        int flags = intent.getFlags();
        Intent intent5 = new Intent();
        intent5.setFlags(flags | 1082130432);
        if (str4 != null) {
            intent5.putExtra("android.intent.extra.INSTANT_APP_TOKEN", str4);
        }
        if (intent.getData() != null) {
            intent5.putExtra(
                    "android.intent.extra.INSTANT_APP_HOSTNAME", intent.getData().getHost());
        }
        intent5.putExtra("android.intent.extra.INSTANT_APP_ACTION", intent.getAction());
        intent5.putExtra("android.intent.extra.INTENT", intent2);
        if (z) {
            intent5.setAction("android.intent.action.RESOLVE_INSTANT_APP_PACKAGE");
        } else {
            ActivityOptions pendingIntentCreatorBackgroundActivityStartMode =
                    ActivityOptions.makeBasic()
                            .setPendingIntentCreatorBackgroundActivityStartMode(1);
            if (intent3 != null || componentName != null) {
                if (componentName != null) {
                    try {
                        intent4 = new Intent();
                        intent4.setComponent(componentName);
                        if (list != null && list.size() == 1) {
                            intent4.putExtra(
                                    "android.intent.extra.SPLIT_NAME",
                                    ((AuxiliaryResolveInfo.AuxiliaryFilter) list.get(0)).splitName);
                        }
                        intent4.putExtra("android.intent.extra.INTENT", intent);
                    } catch (RemoteException unused) {
                    }
                } else {
                    intent4 = intent3;
                }
                intent5.putExtra(
                        "android.intent.extra.INSTANT_APP_FAILURE",
                        new IntentSender(
                                ActivityManager.getService()
                                        .getIntentSenderWithFeature(
                                                2,
                                                str,
                                                str2,
                                                (IBinder) null,
                                                (String) null,
                                                1,
                                                new Intent[] {intent4},
                                                new String[] {str3},
                                                1409286144,
                                                pendingIntentCreatorBackgroundActivityStartMode
                                                        .toBundle(),
                                                i)));
            }
            Intent intent6 = new Intent(intent);
            intent6.setLaunchToken(str4);
            try {
                intent5.putExtra(
                        "android.intent.extra.INSTANT_APP_SUCCESS",
                        new IntentSender(
                                ActivityManager.getService()
                                        .getIntentSenderWithFeature(
                                                2,
                                                str,
                                                str2,
                                                (IBinder) null,
                                                (String) null,
                                                0,
                                                new Intent[] {intent6},
                                                new String[] {str3},
                                                1409286144,
                                                pendingIntentCreatorBackgroundActivityStartMode
                                                        .toBundle(),
                                                i)));
            } catch (RemoteException unused2) {
            }
            if (bundle != null) {
                intent5.putExtra("android.intent.extra.VERIFICATION_BUNDLE", bundle);
            }
            intent5.putExtra("android.intent.extra.CALLING_PACKAGE", str);
            if (list != null) {
                Bundle[] bundleArr = new Bundle[list.size()];
                int size = list.size();
                for (int i2 = 0; i2 < size; i2++) {
                    Bundle bundle2 = new Bundle();
                    AuxiliaryResolveInfo.AuxiliaryFilter auxiliaryFilter =
                            (AuxiliaryResolveInfo.AuxiliaryFilter) list.get(i2);
                    InstantAppResolveInfo instantAppResolveInfo = auxiliaryFilter.resolveInfo;
                    bundle2.putBoolean(
                            "android.intent.extra.UNKNOWN_INSTANT_APP",
                            instantAppResolveInfo != null
                                    && instantAppResolveInfo.shouldLetInstallerDecide());
                    bundle2.putString(
                            "android.intent.extra.PACKAGE_NAME", auxiliaryFilter.packageName);
                    bundle2.putString("android.intent.extra.SPLIT_NAME", auxiliaryFilter.splitName);
                    bundle2.putLong(
                            "android.intent.extra.LONG_VERSION_CODE", auxiliaryFilter.versionCode);
                    bundle2.putBundle(
                            "android.intent.extra.INSTANT_APP_EXTRAS", auxiliaryFilter.extras);
                    bundleArr[i2] = bundle2;
                    if (i2 == 0) {
                        intent5.putExtras(bundle2);
                        intent5.putExtra(
                                "android.intent.extra.VERSION_CODE",
                                (int) auxiliaryFilter.versionCode);
                    }
                }
                intent5.putExtra("android.intent.extra.INSTANT_APP_BUNDLES", bundleArr);
            }
            intent5.setAction("android.intent.action.INSTALL_INSTANT_APP_PACKAGE");
        }
        return intent5;
    }

    public static Intent createFailureIntent(Intent intent, String str) {
        Intent intent2 = new Intent(intent);
        intent2.setFlags(intent2.getFlags() | Integer.MIN_VALUE);
        intent2.setFlags(intent2.getFlags() & (-2049));
        intent2.setLaunchToken(str);
        return intent2;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.pm.AuxiliaryResolveInfo filterInstantAppIntent(
            com.android.server.pm.Computer r27,
            com.android.server.pm.UserManagerService r28,
            java.util.List r29,
            android.content.Intent r30,
            java.lang.String r31,
            int r32,
            java.lang.String r33,
            java.lang.String r34,
            int[] r35) {
        /*
            Method dump skipped, instructions count: 549
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.InstantAppResolver.filterInstantAppIntent(com.android.server.pm.Computer,"
                    + " com.android.server.pm.UserManagerService, java.util.List,"
                    + " android.content.Intent, java.lang.String, int, java.lang.String,"
                    + " java.lang.String, int[]):android.content.pm.AuxiliaryResolveInfo");
    }

    public static void logMetrics(int i, int i2, long j, String str) {
        LogMaker addTaggedData =
                new LogMaker(i)
                        .setType(4)
                        .addTaggedData(901, new Long(System.currentTimeMillis() - j))
                        .addTaggedData(903, str)
                        .addTaggedData(902, new Integer(i2));
        if (sMetricsLogger == null) {
            sMetricsLogger = new MetricsLogger();
        }
        sMetricsLogger.write(addTaggedData);
    }

    public static InstantAppResolveInfo.InstantAppDigest parseDigest(Intent intent) {
        return (intent.getData() == null || TextUtils.isEmpty(intent.getData().getHost()))
                ? InstantAppResolveInfo.InstantAppDigest.UNDEFINED
                : new InstantAppResolveInfo.InstantAppDigest(intent.getData().getHost(), 5);
    }

    public static Intent sanitizeIntent(Intent intent) {
        Intent intent2 = new Intent(intent.getAction());
        Set<String> categories = intent.getCategories();
        if (categories != null) {
            Iterator<String> it = categories.iterator();
            while (it.hasNext()) {
                intent2.addCategory(it.next());
            }
        }
        intent2.setDataAndType(
                intent.getData() == null ? null : Uri.fromParts(intent.getScheme(), "", ""),
                intent.getType());
        intent2.addFlags(intent.getFlags());
        intent2.setPackage(intent.getPackage());
        return intent2;
    }
}
