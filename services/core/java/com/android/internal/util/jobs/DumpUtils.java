package com.android.internal.util.jobs;

import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.text.TextUtils;
import android.util.SparseArray;

import com.samsung.android.knoxguard.service.utils.Constants;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DumpUtils {
    public static final ComponentName[] CRITICAL_SECTION_COMPONENTS = {
        new ComponentName(Constants.SYSTEMUI_PACKAGE_NAME, "com.android.systemui.SystemUIService")
    };
    private static final boolean DEBUG = false;
    private static final String TAG = "DumpUtils";

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Dump {
        void dump(PrintWriter printWriter, String str);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface KeyDumper {
        void dump(int i, int i2);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ValueDumper {
        void dump(Object obj);
    }

    private DumpUtils() {}

    public static boolean checkDumpAndUsageStatsPermission(
            Context context, String str, PrintWriter printWriter) {
        return checkDumpPermission(context, str, printWriter)
                && checkUsageStatsPermission(context, str, printWriter);
    }

    public static boolean checkDumpPermission(
            Context context, String str, PrintWriter printWriter) {
        if (context.checkCallingOrSelfPermission("android.permission.DUMP") == 0) {
            return true;
        }
        StringBuilder m =
                DumpUtils$$ExternalSyntheticOutline0.m(
                        "Permission Denial: can't dump ", str, " from from pid=");
        m.append(Binder.getCallingPid());
        m.append(", uid=");
        m.append(Binder.getCallingUid());
        m.append(" due to missing android.permission.DUMP permission");
        logMessage(printWriter, m.toString());
        return false;
    }

    public static boolean checkUsageStatsPermission(
            Context context, String str, PrintWriter printWriter) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 0 || callingUid == 1000 || callingUid == 1067 || callingUid == 2000) {
            return true;
        }
        if (context.checkCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS") != 0) {
            StringBuilder m =
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "Permission Denial: can't dump ", str, " from from pid=");
            m.append(Binder.getCallingPid());
            m.append(", uid=");
            m.append(Binder.getCallingUid());
            m.append(" due to missing android.permission.PACKAGE_USAGE_STATS permission");
            logMessage(printWriter, m.toString());
            return false;
        }
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        String[] packagesForUid = context.getPackageManager().getPackagesForUid(callingUid);
        if (packagesForUid != null) {
            for (String str2 : packagesForUid) {
                int noteOpNoThrow = appOpsManager.noteOpNoThrow(43, callingUid, str2);
                if (noteOpNoThrow == 0 || noteOpNoThrow == 3) {
                    return true;
                }
            }
        }
        StringBuilder m2 =
                DumpUtils$$ExternalSyntheticOutline0.m(
                        "Permission Denial: can't dump ", str, " from from pid=");
        m2.append(Binder.getCallingPid());
        m2.append(", uid=");
        m2.append(Binder.getCallingUid());
        m2.append(" due to android:get_usage_stats app-op not allowed");
        logMessage(printWriter, m2.toString());
        return false;
    }

    public static void dumpAsync(
            Handler handler, final Dump dump, PrintWriter printWriter, final String str, long j) {
        final StringWriter stringWriter = new StringWriter();
        if (handler.runWithScissors(
                new Runnable() { // from class: com.android.internal.util.jobs.DumpUtils.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        FastPrintWriter fastPrintWriter = new FastPrintWriter(stringWriter);
                        dump.dump(fastPrintWriter, str);
                        fastPrintWriter.close();
                    }
                },
                j)) {
            printWriter.print(stringWriter.toString());
        } else {
            printWriter.println("... timed out");
        }
    }

    public static void dumpSparseArray(
            PrintWriter printWriter, String str, SparseArray sparseArray, String str2) {
        dumpSparseArray(printWriter, str, sparseArray, str2, null, null);
    }

    public static void dumpSparseArray(
            PrintWriter printWriter,
            String str,
            SparseArray sparseArray,
            String str2,
            KeyDumper keyDumper,
            ValueDumper valueDumper) {
        int size = sparseArray.size();
        if (size == 0) {
            printWriter.print(str);
            printWriter.print("No ");
            printWriter.print(str2);
            printWriter.println("s");
            return;
        }
        printWriter.print(str);
        printWriter.print(size);
        printWriter.print(' ');
        printWriter.print(str2);
        printWriter.println("(s):");
        String str3 = str + str;
        for (int i = 0; i < size; i++) {
            int keyAt = sparseArray.keyAt(i);
            Object valueAt = sparseArray.valueAt(i);
            if (keyDumper != null) {
                keyDumper.dump(i, keyAt);
            } else {
                printWriter.print(str3);
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.print(keyAt);
                printWriter.print("->");
            }
            if (valueAt == null) {
                printWriter.print("(null)");
            } else if (valueDumper != null) {
                valueDumper.dump(valueAt);
            } else {
                printWriter.print(valueAt);
            }
            printWriter.println();
        }
    }

    public static void dumpSparseArrayValues(
            final PrintWriter printWriter, final String str, SparseArray sparseArray, String str2) {
        dumpSparseArray(
                printWriter,
                str,
                sparseArray,
                str2,
                new KeyDumper() { // from class:
                                  // com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticLambda1
                    @Override // com.android.internal.util.jobs.DumpUtils.KeyDumper
                    public final void dump(int i, int i2) {
                        DumpUtils.lambda$dumpSparseArrayValues$3(printWriter, str, i, i2);
                    }
                },
                null);
    }

    public static Predicate filterRecord(final String str) {
        if (TextUtils.isEmpty(str)) {
            final int i = 0;
            return new Predicate() { // from class:
                                     // com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$filterRecord$0;
                    boolean isSecMediaPackage;
                    ComponentName.WithComponentName withComponentName =
                            (ComponentName.WithComponentName) obj;
                    switch (i) {
                        case 0:
                            lambda$filterRecord$0 =
                                    DumpUtils.lambda$filterRecord$0(withComponentName);
                            return lambda$filterRecord$0;
                        case 1:
                            return Objects.nonNull(withComponentName);
                        case 2:
                            return DumpUtils.isPlatformPackage(withComponentName);
                        case 3:
                            return DumpUtils.isNonPlatformPackage(withComponentName);
                        case 4:
                            return DumpUtils.isPlatformCriticalPackage(withComponentName);
                        case 5:
                            return DumpUtils.isPlatformNonCriticalPackage(withComponentName);
                        default:
                            isSecMediaPackage = DumpUtils.isSecMediaPackage(withComponentName);
                            return isSecMediaPackage;
                    }
                }
            };
        }
        if ("all".equals(str)) {
            final int i2 = 1;
            return new Predicate() { // from class:
                                     // com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$filterRecord$0;
                    boolean isSecMediaPackage;
                    ComponentName.WithComponentName withComponentName =
                            (ComponentName.WithComponentName) obj;
                    switch (i2) {
                        case 0:
                            lambda$filterRecord$0 =
                                    DumpUtils.lambda$filterRecord$0(withComponentName);
                            return lambda$filterRecord$0;
                        case 1:
                            return Objects.nonNull(withComponentName);
                        case 2:
                            return DumpUtils.isPlatformPackage(withComponentName);
                        case 3:
                            return DumpUtils.isNonPlatformPackage(withComponentName);
                        case 4:
                            return DumpUtils.isPlatformCriticalPackage(withComponentName);
                        case 5:
                            return DumpUtils.isPlatformNonCriticalPackage(withComponentName);
                        default:
                            isSecMediaPackage = DumpUtils.isSecMediaPackage(withComponentName);
                            return isSecMediaPackage;
                    }
                }
            };
        }
        if ("all-platform".equals(str)) {
            final int i3 = 2;
            return new Predicate() { // from class:
                                     // com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$filterRecord$0;
                    boolean isSecMediaPackage;
                    ComponentName.WithComponentName withComponentName =
                            (ComponentName.WithComponentName) obj;
                    switch (i3) {
                        case 0:
                            lambda$filterRecord$0 =
                                    DumpUtils.lambda$filterRecord$0(withComponentName);
                            return lambda$filterRecord$0;
                        case 1:
                            return Objects.nonNull(withComponentName);
                        case 2:
                            return DumpUtils.isPlatformPackage(withComponentName);
                        case 3:
                            return DumpUtils.isNonPlatformPackage(withComponentName);
                        case 4:
                            return DumpUtils.isPlatformCriticalPackage(withComponentName);
                        case 5:
                            return DumpUtils.isPlatformNonCriticalPackage(withComponentName);
                        default:
                            isSecMediaPackage = DumpUtils.isSecMediaPackage(withComponentName);
                            return isSecMediaPackage;
                    }
                }
            };
        }
        if ("all-non-platform".equals(str)) {
            final int i4 = 3;
            return new Predicate() { // from class:
                                     // com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$filterRecord$0;
                    boolean isSecMediaPackage;
                    ComponentName.WithComponentName withComponentName =
                            (ComponentName.WithComponentName) obj;
                    switch (i4) {
                        case 0:
                            lambda$filterRecord$0 =
                                    DumpUtils.lambda$filterRecord$0(withComponentName);
                            return lambda$filterRecord$0;
                        case 1:
                            return Objects.nonNull(withComponentName);
                        case 2:
                            return DumpUtils.isPlatformPackage(withComponentName);
                        case 3:
                            return DumpUtils.isNonPlatformPackage(withComponentName);
                        case 4:
                            return DumpUtils.isPlatformCriticalPackage(withComponentName);
                        case 5:
                            return DumpUtils.isPlatformNonCriticalPackage(withComponentName);
                        default:
                            isSecMediaPackage = DumpUtils.isSecMediaPackage(withComponentName);
                            return isSecMediaPackage;
                    }
                }
            };
        }
        if ("all-platform-critical".equals(str)) {
            final int i5 = 4;
            return new Predicate() { // from class:
                                     // com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$filterRecord$0;
                    boolean isSecMediaPackage;
                    ComponentName.WithComponentName withComponentName =
                            (ComponentName.WithComponentName) obj;
                    switch (i5) {
                        case 0:
                            lambda$filterRecord$0 =
                                    DumpUtils.lambda$filterRecord$0(withComponentName);
                            return lambda$filterRecord$0;
                        case 1:
                            return Objects.nonNull(withComponentName);
                        case 2:
                            return DumpUtils.isPlatformPackage(withComponentName);
                        case 3:
                            return DumpUtils.isNonPlatformPackage(withComponentName);
                        case 4:
                            return DumpUtils.isPlatformCriticalPackage(withComponentName);
                        case 5:
                            return DumpUtils.isPlatformNonCriticalPackage(withComponentName);
                        default:
                            isSecMediaPackage = DumpUtils.isSecMediaPackage(withComponentName);
                            return isSecMediaPackage;
                    }
                }
            };
        }
        if ("all-platform-non-critical".equals(str)) {
            final int i6 = 5;
            return new Predicate() { // from class:
                                     // com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$filterRecord$0;
                    boolean isSecMediaPackage;
                    ComponentName.WithComponentName withComponentName =
                            (ComponentName.WithComponentName) obj;
                    switch (i6) {
                        case 0:
                            lambda$filterRecord$0 =
                                    DumpUtils.lambda$filterRecord$0(withComponentName);
                            return lambda$filterRecord$0;
                        case 1:
                            return Objects.nonNull(withComponentName);
                        case 2:
                            return DumpUtils.isPlatformPackage(withComponentName);
                        case 3:
                            return DumpUtils.isNonPlatformPackage(withComponentName);
                        case 4:
                            return DumpUtils.isPlatformCriticalPackage(withComponentName);
                        case 5:
                            return DumpUtils.isPlatformNonCriticalPackage(withComponentName);
                        default:
                            isSecMediaPackage = DumpUtils.isSecMediaPackage(withComponentName);
                            return isSecMediaPackage;
                    }
                }
            };
        }
        if ("samsung-media".equals(str)) {
            final int i7 = 6;
            return new Predicate() { // from class:
                                     // com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$filterRecord$0;
                    boolean isSecMediaPackage;
                    ComponentName.WithComponentName withComponentName =
                            (ComponentName.WithComponentName) obj;
                    switch (i7) {
                        case 0:
                            lambda$filterRecord$0 =
                                    DumpUtils.lambda$filterRecord$0(withComponentName);
                            return lambda$filterRecord$0;
                        case 1:
                            return Objects.nonNull(withComponentName);
                        case 2:
                            return DumpUtils.isPlatformPackage(withComponentName);
                        case 3:
                            return DumpUtils.isNonPlatformPackage(withComponentName);
                        case 4:
                            return DumpUtils.isPlatformCriticalPackage(withComponentName);
                        case 5:
                            return DumpUtils.isPlatformNonCriticalPackage(withComponentName);
                        default:
                            isSecMediaPackage = DumpUtils.isSecMediaPackage(withComponentName);
                            return isSecMediaPackage;
                    }
                }
            };
        }
        final ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        if (unflattenFromString != null) {
            return new Predicate() { // from class:
                                     // com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticLambda9
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$filterRecord$1;
                    lambda$filterRecord$1 =
                            DumpUtils.lambda$filterRecord$1(
                                    unflattenFromString, (ComponentName.WithComponentName) obj);
                    return lambda$filterRecord$1;
                }
            };
        }
        final int parseIntWithBase = ParseUtils.parseIntWithBase(str, 16, -1);
        return new Predicate() { // from class:
                                 // com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$filterRecord$2;
                lambda$filterRecord$2 =
                        DumpUtils.lambda$filterRecord$2(
                                parseIntWithBase, str, (ComponentName.WithComponentName) obj);
                return lambda$filterRecord$2;
            }
        };
    }

    private static boolean isCriticalPackage(ComponentName componentName) {
        if (componentName == null) {
            return false;
        }
        int i = 0;
        while (true) {
            ComponentName[] componentNameArr = CRITICAL_SECTION_COMPONENTS;
            if (i >= componentNameArr.length) {
                return false;
            }
            if (componentName.equals(componentNameArr[i])) {
                return true;
            }
            i++;
        }
    }

    public static boolean isNonPlatformPackage(ComponentName.WithComponentName withComponentName) {
        return (withComponentName == null
                        || isPlatformPackage(withComponentName.getComponentName())
                        || isSecMediaPackage(withComponentName.getComponentName()))
                ? false
                : true;
    }

    public static boolean isNonPlatformPackage(ComponentName componentName) {
        return (componentName == null
                        || !isNonPlatformPackage(componentName.getPackageName())
                        || isSecMediaPackage(componentName.getPackageName()))
                ? false
                : true;
    }

    public static boolean isNonPlatformPackage(String str) {
        return (str == null || isPlatformPackage(str) || isSecMediaPackage(str)) ? false : true;
    }

    public static boolean isPlatformCriticalPackage(
            ComponentName.WithComponentName withComponentName) {
        return withComponentName != null
                && isPlatformPackage(withComponentName.getComponentName())
                && isCriticalPackage(withComponentName.getComponentName());
    }

    public static boolean isPlatformNonCriticalPackage(
            ComponentName.WithComponentName withComponentName) {
        return (withComponentName == null
                        || !isPlatformPackage(withComponentName.getComponentName())
                        || isCriticalPackage(withComponentName.getComponentName()))
                ? false
                : true;
    }

    public static boolean isPlatformPackage(ComponentName.WithComponentName withComponentName) {
        return withComponentName != null && isPlatformPackage(withComponentName.getComponentName());
    }

    public static boolean isPlatformPackage(ComponentName componentName) {
        return componentName != null && isPlatformPackage(componentName.getPackageName());
    }

    public static boolean isPlatformPackage(String str) {
        return str != null
                && (str.equals("android")
                        || str.startsWith("android.")
                        || str.startsWith("com.android."));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSecMediaPackage(ComponentName.WithComponentName withComponentName) {
        return withComponentName != null && isSecMediaPackage(withComponentName.getComponentName());
    }

    private static boolean isSecMediaPackage(ComponentName componentName) {
        return componentName != null && isSecMediaPackage(componentName.getPackageName());
    }

    private static boolean isSecMediaPackage(String str) {
        return str != null
                && ("com.google.android.providers.media.module".equals(str)
                        || "com.samsung.android.providers.media".equals(str)
                        || "com.samsung.android.providers.trash".equals(str)
                        || "com.sec.android.app.myfiles".equals(str)
                        || "com.sec.android.gallery3d".equals(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpSparseArrayValues$3(
            PrintWriter printWriter, String str, int i, int i2) {
        printWriter.printf("%s%s", str, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$filterRecord$0(
            ComponentName.WithComponentName withComponentName) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$filterRecord$1(
            ComponentName componentName, ComponentName.WithComponentName withComponentName) {
        return withComponentName != null
                && componentName.equals(withComponentName.getComponentName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$filterRecord$2(
            int i, String str, ComponentName.WithComponentName withComponentName) {
        return (i != -1 && System.identityHashCode(withComponentName) == i)
                || withComponentName
                        .getComponentName()
                        .flattenToString()
                        .toLowerCase()
                        .contains(str.toLowerCase());
    }

    private static void logMessage(PrintWriter printWriter, String str) {
        printWriter.println(str);
    }
}
