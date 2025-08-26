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

/* loaded from: classes4.dex */
public final class DumpUtils {
    public static final ComponentName[] CRITICAL_SECTION_COMPONENTS = {new ComponentName(AsPackageName.SYSTEMUI, "com.android.systemui.SystemUIService")};
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

    static /* synthetic */ boolean lambda$filterRecord$0(ComponentName.WithComponentName withComponentName) {
        return false;
    }

    private DumpUtils() {
    }

    public static void dumpAsync(Handler handler, final Dump dump, PrintWriter printWriter, final String str, long j) {
        final StringWriter stringWriter = new StringWriter();
        if (handler.runWithScissors(new Runnable() { // from class: com.android.internal.util.DumpUtils.1
            @Override // java.lang.Runnable
            public void run() {
                FastPrintWriter fastPrintWriter = new FastPrintWriter(stringWriter);
                dump.dump(fastPrintWriter, str);
                fastPrintWriter.close();
            }
        }, j)) {
            printWriter.print(stringWriter.toString());
        } else {
            printWriter.println("... timed out");
        }
    }

    private static void logMessage(PrintWriter printWriter, String str) {
        printWriter.println(str);
    }

    public static boolean checkDumpPermission(Context context, String str, PrintWriter printWriter) {
        if (context.checkCallingOrSelfPermission(Manifest.permission.DUMP) == 0) {
            return true;
        }
        logMessage(printWriter, "Permission Denial: can't dump " + str + " from from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " due to missing android.permission.DUMP permission");
        return false;
    }

    public static boolean checkUsageStatsPermission(Context context, String str, PrintWriter printWriter) {
        int callingUid = Binder.getCallingUid();
        if (callingUid == 0 || callingUid == 1000 || callingUid == 1067 || callingUid == 2000) {
            return true;
        }
        if (context.checkCallingOrSelfPermission(Manifest.permission.PACKAGE_USAGE_STATS) != 0) {
            logMessage(printWriter, "Permission Denial: can't dump " + str + " from from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " due to missing android.permission.PACKAGE_USAGE_STATS permission");
            return false;
        }
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        String[] packagesForUid = context.getPackageManager().getPackagesForUid(callingUid);
        if (packagesForUid != null) {
            for (String str2 : packagesForUid) {
                int iNoteOpNoThrow = appOpsManager.noteOpNoThrow(43, callingUid, str2);
                if (iNoteOpNoThrow == 0 || iNoteOpNoThrow == 3) {
                    return true;
                }
            }
        }
        logMessage(printWriter, "Permission Denial: can't dump " + str + " from from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid() + " due to android:get_usage_stats app-op not allowed");
        return false;
    }

    public static boolean checkDumpAndUsageStatsPermission(Context context, String str, PrintWriter printWriter) {
        return checkDumpPermission(context, str, printWriter) && checkUsageStatsPermission(context, str, printWriter);
    }

    public static boolean isPlatformPackage(String str) {
        if (str != null) {
            return str.equals("android") || str.startsWith("android.") || str.startsWith("com.android.");
        }
        return false;
    }

    public static boolean isPlatformPackage(ComponentName componentName) {
        return componentName != null && isPlatformPackage(componentName.getPackageName());
    }

    public static boolean isPlatformPackage(ComponentName.WithComponentName withComponentName) {
        return withComponentName != null && isPlatformPackage(withComponentName.getComponentName());
    }

    private static boolean isSecMediaPackage(String str) {
        if (str != null) {
            return "com.google.android.providers.media.module".equals(str) || "com.samsung.android.providers.media".equals(str) || "com.samsung.android.providers.trash".equals(str) || "com.sec.android.app.myfiles".equals(str) || SemShareConstants.GALLERY_PACKAGE.equals(str);
        }
        return false;
    }

    private static boolean isSecMediaPackage(ComponentName componentName) {
        return componentName != null && isSecMediaPackage(componentName.getPackageName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSecMediaPackage(ComponentName.WithComponentName withComponentName) {
        return withComponentName != null && isSecMediaPackage(withComponentName.getComponentName());
    }

    private static boolean isRoutinePackage(String str) {
        return str != null && AsPackageName.ROUTINE.equals(str);
    }

    private static boolean isRoutinePackage(ComponentName componentName) {
        return componentName != null && isRoutinePackage(componentName.getPackageName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isRoutinePackage(ComponentName.WithComponentName withComponentName) {
        return withComponentName != null && isRoutinePackage(withComponentName.getComponentName());
    }

    public static boolean isNonPlatformPackage(String str) {
        return (str == null || isPlatformPackage(str) || isSecMediaPackage(str) || isRoutinePackage(str)) ? false : true;
    }

    public static boolean isNonPlatformPackage(ComponentName componentName) {
        return (componentName == null || !isNonPlatformPackage(componentName.getPackageName()) || isSecMediaPackage(componentName.getPackageName()) || isRoutinePackage(componentName.getPackageName())) ? false : true;
    }

    public static boolean isNonPlatformPackage(ComponentName.WithComponentName withComponentName) {
        return (withComponentName == null || isPlatformPackage(withComponentName.getComponentName()) || isSecMediaPackage(withComponentName.getComponentName()) || isRoutinePackage(withComponentName.getComponentName())) ? false : true;
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

    public static boolean isPlatformCriticalPackage(ComponentName.WithComponentName withComponentName) {
        return withComponentName != null && isPlatformPackage(withComponentName.getComponentName()) && isCriticalPackage(withComponentName.getComponentName());
    }

    public static boolean isPlatformNonCriticalPackage(ComponentName.WithComponentName withComponentName) {
        return (withComponentName == null || !isPlatformPackage(withComponentName.getComponentName()) || isCriticalPackage(withComponentName.getComponentName())) ? false : true;
    }

    public static <TRec extends ComponentName.WithComponentName> Predicate<TRec> filterRecord(final String str) {
        if (TextUtils.isEmpty(str)) {
            return new Predicate() { // from class: com.android.internal.util.DumpUtils$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DumpUtils.lambda$filterRecord$0((ComponentName.WithComponentName) obj);
                }
            };
        }
        if ("all".equals(str)) {
            return new Predicate() { // from class: com.android.internal.util.DumpUtils$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Objects.nonNull((ComponentName.WithComponentName) obj);
                }
            };
        }
        if ("all-platform".equals(str)) {
            return new Predicate() { // from class: com.android.internal.util.DumpUtils$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DumpUtils.isPlatformPackage((ComponentName.WithComponentName) obj);
                }
            };
        }
        if ("all-non-platform".equals(str)) {
            return new Predicate() { // from class: com.android.internal.util.DumpUtils$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DumpUtils.isNonPlatformPackage((ComponentName.WithComponentName) obj);
                }
            };
        }
        if ("all-platform-critical".equals(str)) {
            return new Predicate() { // from class: com.android.internal.util.DumpUtils$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DumpUtils.isPlatformCriticalPackage((ComponentName.WithComponentName) obj);
                }
            };
        }
        if ("all-platform-non-critical".equals(str)) {
            return new Predicate() { // from class: com.android.internal.util.DumpUtils$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DumpUtils.isPlatformNonCriticalPackage((ComponentName.WithComponentName) obj);
                }
            };
        }
        if ("samsung-media".equals(str)) {
            return new Predicate() { // from class: com.android.internal.util.DumpUtils$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DumpUtils.isSecMediaPackage((ComponentName.WithComponentName) obj);
                }
            };
        }
        if ("routine-dump".equals(str)) {
            return new Predicate() { // from class: com.android.internal.util.DumpUtils$$ExternalSyntheticLambda7
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DumpUtils.isRoutinePackage((ComponentName.WithComponentName) obj);
                }
            };
        }
        final ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(str);
        if (componentNameUnflattenFromString != null) {
            return new Predicate() { // from class: com.android.internal.util.DumpUtils$$ExternalSyntheticLambda8
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return DumpUtils.lambda$filterRecord$1(componentNameUnflattenFromString, (ComponentName.WithComponentName) obj);
                }
            };
        }
        final int intWithBase = ParseUtils.parseIntWithBase(str, 16, -1);
        return new Predicate() { // from class: com.android.internal.util.DumpUtils$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DumpUtils.lambda$filterRecord$2(intWithBase, str, (ComponentName.WithComponentName) obj);
            }
        };
    }

    static /* synthetic */ boolean lambda$filterRecord$1(ComponentName componentName, ComponentName.WithComponentName withComponentName) {
        return withComponentName != null && componentName.equals(withComponentName.getComponentName());
    }

    static /* synthetic */ boolean lambda$filterRecord$2(int i, String str, ComponentName.WithComponentName withComponentName) {
        return (i != -1 && System.identityHashCode(withComponentName) == i) || withComponentName.getComponentName().flattenToString().toLowerCase().contains(str.toLowerCase());
    }

    public static void dumpSparseArray(PrintWriter printWriter, String str, SparseArray<?> sparseArray, String str2) {
        dumpSparseArray(printWriter, str, sparseArray, str2, null, null);
    }

    public static <T> void dumpSparseArrayValues(final PrintWriter printWriter, final String str, SparseArray<T> sparseArray, String str2) {
        dumpSparseArray(printWriter, str, sparseArray, str2, new KeyDumper() { // from class: com.android.internal.util.DumpUtils$$ExternalSyntheticLambda10
            @Override // com.android.internal.util.DumpUtils.KeyDumper
            public final void dump(int i, int i2) {
                PrintWriter printWriter2 = printWriter;
                String str3 = str;
                printWriter2.printf("%s%s", str3, str3);
            }
        }, null);
    }

    public static <T> void dumpSparseArray(PrintWriter printWriter, String str, SparseArray<T> sparseArray, String str2, KeyDumper keyDumper, ValueDumper<T> valueDumper) {
        int size = sparseArray.size();
        if (size == 0) {
            printWriter.print(str);
            printWriter.print("No ");
            printWriter.print(str2);
            printWriter.println(XmlTags.TAG_SESSION);
            return;
        }
        printWriter.print(str);
        printWriter.print(size);
        printWriter.print(' ');
        printWriter.print(str2);
        printWriter.println("(s):");
        String str3 = str + str;
        for (int i = 0; i < size; i++) {
            int iKeyAt = sparseArray.keyAt(i);
            T tValueAt = sparseArray.valueAt(i);
            if (keyDumper != null) {
                keyDumper.dump(i, iKeyAt);
            } else {
                printWriter.print(str3);
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.print(iKeyAt);
                printWriter.print(Session.SUBSESSION_SEPARATION_CHAR);
            }
            if (tValueAt == null) {
                printWriter.print("(null)");
            } else if (valueDumper != null) {
                valueDumper.dump(tValueAt);
            } else {
                printWriter.print(tValueAt);
            }
            printWriter.println();
        }
    }
}
