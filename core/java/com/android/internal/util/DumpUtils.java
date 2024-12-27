package com.android.internal.util;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.blob.XmlTags;
import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.telecom.Logging.Session;
import android.text.TextUtils;
import android.util.SparseArray;

import com.samsung.android.common.AsPackageName;
import com.samsung.android.share.SemShareConstants;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public final class DumpUtils {
    public static final ComponentName[] CRITICAL_SECTION_COMPONENTS = {
        new ComponentName(AsPackageName.SYSTEMUI, "com.android.systemui.SystemUIService")
    };
    private static final boolean DEBUG = false;
    private static final String TAG = "DumpUtils";

    public interface Dump {
        void dump(PrintWriter printWriter, String str);
    }

    public interface KeyDumper {
        void dump(int i, int i2);
    }

    public interface ValueDumper<T> {
        void dump(T t);
    }

    private DumpUtils() {}

    public static void dumpAsync(
            Handler handler, final Dump dump, PrintWriter pw, final String prefix, long timeout) {
        final StringWriter sw = new StringWriter();
        if (handler.runWithScissors(
                new Runnable() { // from class: com.android.internal.util.DumpUtils.1
                    @Override // java.lang.Runnable
                    public void run() {
                        PrintWriter lpw = new FastPrintWriter(sw);
                        dump.dump(lpw, prefix);
                        lpw.close();
                    }
                },
                timeout)) {
            pw.print(sw.toString());
        } else {
            pw.println("... timed out");
        }
    }

    private static void logMessage(PrintWriter pw, String msg) {
        pw.println(msg);
    }

    public static boolean checkDumpPermission(Context context, String tag, PrintWriter pw) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.DUMP) != 0) {
            logMessage(
                    pw,
                    "Permission Denial: can't dump "
                            + tag
                            + " from from pid="
                            + Binder.getCallingPid()
                            + ", uid="
                            + Binder.getCallingUid()
                            + " due to missing android.permission.DUMP permission");
            return false;
        }
        return true;
    }

    public static boolean checkUsageStatsPermission(Context context, String tag, PrintWriter pw) {
        int uid = Binder.getCallingUid();
        switch (uid) {
            case 0:
            case 1000:
            case 1067:
            case 2000:
                return true;
            default:
                if (context.checkCallingOrSelfPermission(Manifest.permission.PACKAGE_USAGE_STATS)
                        != 0) {
                    logMessage(
                            pw,
                            "Permission Denial: can't dump "
                                    + tag
                                    + " from from pid="
                                    + Binder.getCallingPid()
                                    + ", uid="
                                    + Binder.getCallingUid()
                                    + " due to missing android.permission.PACKAGE_USAGE_STATS"
                                    + " permission");
                    return false;
                }
                AppOpsManager appOps =
                        (AppOpsManager) context.getSystemService(AppOpsManager.class);
                String[] pkgs = context.getPackageManager().getPackagesForUid(uid);
                if (pkgs != null) {
                    for (String pkg : pkgs) {
                        switch (appOps.noteOpNoThrow(43, uid, pkg)) {
                            case 0:
                                return true;
                            case 3:
                                return true;
                            default:
                        }
                    }
                }
                logMessage(
                        pw,
                        "Permission Denial: can't dump "
                                + tag
                                + " from from pid="
                                + Binder.getCallingPid()
                                + ", uid="
                                + Binder.getCallingUid()
                                + " due to android:get_usage_stats app-op not allowed");
                return false;
        }
    }

    public static boolean checkDumpAndUsageStatsPermission(
            Context context, String tag, PrintWriter pw) {
        return checkDumpPermission(context, tag, pw) && checkUsageStatsPermission(context, tag, pw);
    }

    public static boolean isPlatformPackage(String packageName) {
        return packageName != null
                && (packageName.equals("android")
                        || packageName.startsWith("android.")
                        || packageName.startsWith("com.android."));
    }

    public static boolean isPlatformPackage(ComponentName cname) {
        return cname != null && isPlatformPackage(cname.getPackageName());
    }

    public static boolean isPlatformPackage(ComponentName.WithComponentName wcn) {
        return wcn != null && isPlatformPackage(wcn.getComponentName());
    }

    private static boolean isSecMediaPackage(String packageName) {
        return packageName != null
                && ("com.google.android.providers.media.module".equals(packageName)
                        || "com.samsung.android.providers.media".equals(packageName)
                        || "com.samsung.android.providers.trash".equals(packageName)
                        || "com.sec.android.app.myfiles".equals(packageName)
                        || SemShareConstants.GALLERY_PACKAGE.equals(packageName));
    }

    private static boolean isSecMediaPackage(ComponentName cname) {
        return cname != null && isSecMediaPackage(cname.getPackageName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSecMediaPackage(ComponentName.WithComponentName wcn) {
        return wcn != null && isSecMediaPackage(wcn.getComponentName());
    }

    public static boolean isNonPlatformPackage(String packageName) {
        return (packageName == null
                        || isPlatformPackage(packageName)
                        || isSecMediaPackage(packageName))
                ? false
                : true;
    }

    public static boolean isNonPlatformPackage(ComponentName cname) {
        return (cname == null
                        || !isNonPlatformPackage(cname.getPackageName())
                        || isSecMediaPackage(cname.getPackageName()))
                ? false
                : true;
    }

    public static boolean isNonPlatformPackage(ComponentName.WithComponentName wcn) {
        return (wcn == null
                        || isPlatformPackage(wcn.getComponentName())
                        || isSecMediaPackage(wcn.getComponentName()))
                ? false
                : true;
    }

    private static boolean isCriticalPackage(ComponentName cname) {
        if (cname == null) {
            return false;
        }
        for (int i = 0; i < CRITICAL_SECTION_COMPONENTS.length; i++) {
            if (cname.equals(CRITICAL_SECTION_COMPONENTS[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPlatformCriticalPackage(ComponentName.WithComponentName wcn) {
        return wcn != null
                && isPlatformPackage(wcn.getComponentName())
                && isCriticalPackage(wcn.getComponentName());
    }

    public static boolean isPlatformNonCriticalPackage(ComponentName.WithComponentName wcn) {
        return (wcn == null
                        || !isPlatformPackage(wcn.getComponentName())
                        || isCriticalPackage(wcn.getComponentName()))
                ? false
                : true;
    }

    public static <TRec extends ComponentName.WithComponentName> Predicate<TRec> filterRecord(
            final String filterString) {
        if (TextUtils.isEmpty(filterString)) {
            return new Predicate() { // from class:
                                     // com.android.internal.util.DumpUtils$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DumpUtils.lambda$filterRecord$0((ComponentName.WithComponentName) obj);
                }
            };
        }
        if ("all".equals(filterString)) {
            return new Predicate() { // from class:
                                     // com.android.internal.util.DumpUtils$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Objects.nonNull((ComponentName.WithComponentName) obj);
                }
            };
        }
        if ("all-platform".equals(filterString)) {
            return new Predicate() { // from class:
                                     // com.android.internal.util.DumpUtils$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DumpUtils.isPlatformPackage((ComponentName.WithComponentName) obj);
                }
            };
        }
        if ("all-non-platform".equals(filterString)) {
            return new Predicate() { // from class:
                                     // com.android.internal.util.DumpUtils$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DumpUtils.isNonPlatformPackage((ComponentName.WithComponentName) obj);
                }
            };
        }
        if ("all-platform-critical".equals(filterString)) {
            return new Predicate() { // from class:
                                     // com.android.internal.util.DumpUtils$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DumpUtils.isPlatformCriticalPackage(
                            (ComponentName.WithComponentName) obj);
                }
            };
        }
        if ("all-platform-non-critical".equals(filterString)) {
            return new Predicate() { // from class:
                                     // com.android.internal.util.DumpUtils$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DumpUtils.isPlatformNonCriticalPackage(
                            (ComponentName.WithComponentName) obj);
                }
            };
        }
        if ("samsung-media".equals(filterString)) {
            return new Predicate() { // from class:
                                     // com.android.internal.util.DumpUtils$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean isSecMediaPackage;
                    isSecMediaPackage =
                            DumpUtils.isSecMediaPackage((ComponentName.WithComponentName) obj);
                    return isSecMediaPackage;
                }
            };
        }
        final ComponentName filterCname = ComponentName.unflattenFromString(filterString);
        if (filterCname != null) {
            return new Predicate() { // from class:
                                     // com.android.internal.util.DumpUtils$$ExternalSyntheticLambda7
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DumpUtils.lambda$filterRecord$1(
                            ComponentName.this, (ComponentName.WithComponentName) obj);
                }
            };
        }
        final int id = ParseUtils.parseIntWithBase(filterString, 16, -1);
        return new Predicate() { // from class:
                                 // com.android.internal.util.DumpUtils$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DumpUtils.lambda$filterRecord$2(
                        id, filterString, (ComponentName.WithComponentName) obj);
            }
        };
    }

    static /* synthetic */ boolean lambda$filterRecord$0(ComponentName.WithComponentName rec) {
        return false;
    }

    static /* synthetic */ boolean lambda$filterRecord$1(
            ComponentName filterCname, ComponentName.WithComponentName rec) {
        return rec != null && filterCname.equals(rec.getComponentName());
    }

    static /* synthetic */ boolean lambda$filterRecord$2(
            int id, String filterString, ComponentName.WithComponentName rec) {
        ComponentName cn = rec.getComponentName();
        return (id != -1 && System.identityHashCode(rec) == id)
                || cn.flattenToString().toLowerCase().contains(filterString.toLowerCase());
    }

    public static void dumpSparseArray(
            PrintWriter pw, String prefix, SparseArray<?> array, String name) {
        dumpSparseArray(pw, prefix, array, name, null, null);
    }

    public static <T> void dumpSparseArrayValues(
            final PrintWriter pw, final String prefix, SparseArray<T> array, String name) {
        dumpSparseArray(
                pw,
                prefix,
                array,
                name,
                new KeyDumper() { // from class:
                                  // com.android.internal.util.DumpUtils$$ExternalSyntheticLambda9
                    @Override // com.android.internal.util.DumpUtils.KeyDumper
                    public final void dump(int i, int i2) {
                        pw.printf("%s%s", prefix, r1);
                    }
                },
                null);
    }

    public static <T> void dumpSparseArray(
            PrintWriter pw,
            String prefix,
            SparseArray<T> array,
            String name,
            KeyDumper keyDumper,
            ValueDumper<T> valueDumper) {
        int size = array.size();
        if (size == 0) {
            pw.print(prefix);
            pw.print("No ");
            pw.print(name);
            pw.println(XmlTags.TAG_SESSION);
            return;
        }
        pw.print(prefix);
        pw.print(size);
        pw.print(' ');
        pw.print(name);
        pw.println("(s):");
        String prefix2 = prefix + prefix;
        for (int i = 0; i < size; i++) {
            int key = array.keyAt(i);
            T value = array.valueAt(i);
            if (keyDumper != null) {
                keyDumper.dump(i, key);
            } else {
                pw.print(prefix2);
                pw.print(i);
                pw.print(": ");
                pw.print(key);
                pw.print(Session.SUBSESSION_SEPARATION_CHAR);
            }
            if (value == null) {
                pw.print("(null)");
            } else if (valueDumper != null) {
                valueDumper.dump(value);
            } else {
                pw.print(value);
            }
            pw.println();
        }
    }
}
