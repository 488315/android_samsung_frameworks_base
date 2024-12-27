package com.android.server.utils;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class PriorityDump {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface PriorityDumper {
        default void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, boolean z) {
            dumpCritical(fileDescriptor, printWriter, strArr, z);
            dumpHigh(fileDescriptor, printWriter, strArr, z);
            dumpNormal(fileDescriptor, printWriter, strArr, z);
        }

        default void dumpCritical(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, boolean z) {
        }

        default void dumpHigh(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, boolean z) {
        }

        default void dumpNormal(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, boolean z) {
        }
    }

    public static void dump(PriorityDumper priorityDumper, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (strArr == null) {
            priorityDumper.dump(fileDescriptor, printWriter, strArr, false);
            return;
        }
        String[] strArr2 = new String[strArr.length];
        int i = 0;
        int i2 = 0;
        char c = 0;
        boolean z = false;
        while (i < strArr.length) {
            if (strArr[i].equals("--proto")) {
                z = true;
            } else if (strArr[i].equals("--dump-priority")) {
                int i3 = i + 1;
                if (i3 < strArr.length) {
                    String str = strArr[i3];
                    str.getClass();
                    switch (str) {
                        case "NORMAL":
                            c = 3;
                            break;
                        case "CRITICAL":
                            c = 1;
                            break;
                        case "HIGH":
                            c = 2;
                            break;
                        default:
                            c = 0;
                            break;
                    }
                    i = i3;
                }
            } else {
                strArr2[i2] = strArr[i];
                i2++;
            }
            i++;
        }
        if (i2 < strArr.length) {
            strArr2 = (String[]) Arrays.copyOf(strArr2, i2);
        }
        if (c == 1) {
            priorityDumper.dumpCritical(fileDescriptor, printWriter, strArr2, z);
            return;
        }
        if (c == 2) {
            priorityDumper.dumpHigh(fileDescriptor, printWriter, strArr2, z);
        } else if (c != 3) {
            priorityDumper.dump(fileDescriptor, printWriter, strArr2, z);
        } else {
            priorityDumper.dumpNormal(fileDescriptor, printWriter, strArr2, z);
        }
    }
}
