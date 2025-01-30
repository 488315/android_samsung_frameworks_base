package com.android.server.utils;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;

/* loaded from: classes3.dex */
public abstract class PriorityDump {
    public static void dump(PriorityDumper priorityDumper, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i = 0;
        if (strArr == null) {
            priorityDumper.dump(fileDescriptor, printWriter, strArr, false);
            return;
        }
        String[] strArr2 = new String[strArr.length];
        int i2 = 0;
        int i3 = 0;
        boolean z = false;
        while (i < strArr.length) {
            if (strArr[i].equals("--proto")) {
                z = true;
            } else if (strArr[i].equals("--dump-priority")) {
                int i4 = i + 1;
                if (i4 < strArr.length) {
                    i3 = getPriorityType(strArr[i4]);
                    i = i4;
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
        if (i3 == 1) {
            priorityDumper.dumpCritical(fileDescriptor, printWriter, strArr2, z);
            return;
        }
        if (i3 == 2) {
            priorityDumper.dumpHigh(fileDescriptor, printWriter, strArr2, z);
        } else if (i3 == 3) {
            priorityDumper.dumpNormal(fileDescriptor, printWriter, strArr2, z);
        } else {
            priorityDumper.dump(fileDescriptor, printWriter, strArr2, z);
        }
    }

    public static int getPriorityType(String str) {
        str.hashCode();
        switch (str) {
            case "NORMAL":
                return 3;
            case "CRITICAL":
                return 1;
            case "HIGH":
                return 2;
            default:
                return 0;
        }
    }

    public interface PriorityDumper {
        default void dumpCritical(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, boolean z) {
        }

        default void dumpHigh(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, boolean z) {
        }

        default void dumpNormal(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, boolean z) {
        }

        default void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, boolean z) {
            dumpCritical(fileDescriptor, printWriter, strArr, z);
            dumpHigh(fileDescriptor, printWriter, strArr, z);
            dumpNormal(fileDescriptor, printWriter, strArr, z);
        }
    }
}
