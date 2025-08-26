package com.android.internal.util.dump;

import android.content.ComponentName;

/* loaded from: classes4.dex */
public class DumpUtils {
    public static void writeStringIfNotNull(DualDumpOutputStream dualDumpOutputStream, String str, long j, String str2) {
        if (str2 != null) {
            dualDumpOutputStream.write(str, j, str2);
        }
    }

    public static void writeComponentName(DualDumpOutputStream dualDumpOutputStream, String str, long j, ComponentName componentName) {
        long jStart = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("package_name", 1138166333441L, componentName.getPackageName());
        dualDumpOutputStream.write("class_name", 1138166333442L, componentName.getClassName());
        dualDumpOutputStream.end(jStart);
    }
}
