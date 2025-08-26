package com.android.internal.os;

import android.os.Process;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructCapUserData;
import android.system.StructCapUserHeader;
import android.util.Slog;
import android.util.TimingsTraceLog;
import dalvik.system.VMRuntime;
import java.io.DataOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import libcore.io.IoUtils;

/* loaded from: classes5.dex */
public class WrapperInit {
    private static final String TAG = "AndroidRuntime";

    private WrapperInit() {
    }

    public static void main(String[] strArr) throws Throwable {
        int i = Integer.parseInt(strArr[0], 10);
        int i2 = Integer.parseInt(strArr[1], 10);
        if (i != 0) {
            FileDescriptor fileDescriptor = new FileDescriptor();
            try {
                try {
                    fileDescriptor.setInt$(i);
                    DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(fileDescriptor));
                    dataOutputStream.writeInt(Process.myPid());
                    dataOutputStream.close();
                } catch (IOException e) {
                    Slog.d(TAG, "Could not write pid of wrapped process to Zygote pipe.", e);
                }
            } finally {
                IoUtils.closeQuietly(fileDescriptor);
            }
        }
        ZygoteInit.preload(new TimingsTraceLog("WrapperInitTiming", 16384L));
        int length = strArr.length - 2;
        String[] strArr2 = new String[length];
        System.arraycopy(strArr, 2, strArr2, 0, length);
        wrapperInit(i2, strArr2).run();
    }

    public static void execApplication(String str, String str2, int i, String str3, FileDescriptor fileDescriptor, String[] strArr) {
        String str4;
        StringBuilder sb = new StringBuilder(str);
        if (VMRuntime.is64BitInstructionSet(str3)) {
            str4 = "/system/bin/app_process64";
        } else {
            str4 = "/system/bin/app_process32";
        }
        sb.append(' ');
        sb.append(str4);
        sb.append(" -Xcompiler-option --generate-mini-debug-info /system/bin --application");
        if (str2 != null) {
            sb.append(" '--nice-name=");
            sb.append(str2);
            sb.append("'");
        }
        sb.append(" com.android.internal.os.WrapperInit ");
        sb.append(fileDescriptor != null ? fileDescriptor.getInt$() : 0);
        sb.append(' ');
        sb.append(i);
        Zygote.appendQuotedShellArgs(sb, strArr);
        preserveCapabilities();
        Zygote.execShell(sb.toString());
    }

    private static Runnable wrapperInit(int i, String[] strArr) {
        ClassLoader classLoaderCreatePathClassLoader;
        if (strArr == null || strArr.length <= 2 || !strArr[0].equals("-cp")) {
            classLoaderCreatePathClassLoader = null;
        } else {
            classLoaderCreatePathClassLoader = ZygoteInit.createPathClassLoader(strArr[1], i);
            Thread.currentThread().setContextClassLoader(classLoaderCreatePathClassLoader);
            String[] strArr2 = new String[strArr.length - 2];
            System.arraycopy(strArr, 2, strArr2, 0, strArr.length - 2);
            strArr = strArr2;
        }
        Zygote.nativePreApplicationInit();
        return RuntimeInit.applicationInit(i, null, strArr, classLoaderCreatePathClassLoader);
    }

    private static void preserveCapabilities() throws ErrnoException {
        StructCapUserHeader structCapUserHeader = new StructCapUserHeader(OsConstants._LINUX_CAPABILITY_VERSION_3, 0);
        try {
            StructCapUserData[] structCapUserDataArrCapget = Os.capget(structCapUserHeader);
            if (structCapUserDataArrCapget[0].permitted != structCapUserDataArrCapget[0].inheritable || structCapUserDataArrCapget[1].permitted != structCapUserDataArrCapget[1].inheritable) {
                structCapUserDataArrCapget[0] = new StructCapUserData(structCapUserDataArrCapget[0].effective, structCapUserDataArrCapget[0].permitted, structCapUserDataArrCapget[0].permitted);
                structCapUserDataArrCapget[1] = new StructCapUserData(structCapUserDataArrCapget[1].effective, structCapUserDataArrCapget[1].permitted, structCapUserDataArrCapget[1].permitted);
                try {
                    Os.capset(structCapUserHeader, structCapUserDataArrCapget);
                } catch (ErrnoException e) {
                    Slog.e(TAG, "RuntimeInit: Failed capset", e);
                    return;
                }
            }
            for (int i = 0; i < 64; i++) {
                if ((structCapUserDataArrCapget[OsConstants.CAP_TO_INDEX(i)].inheritable & OsConstants.CAP_TO_MASK(i)) != 0) {
                    try {
                        Os.prctl(OsConstants.PR_CAP_AMBIENT, OsConstants.PR_CAP_AMBIENT_RAISE, i, 0L, 0L);
                    } catch (ErrnoException e2) {
                        Slog.e(TAG, "RuntimeInit: Failed to raise ambient capability " + i, e2);
                    }
                }
            }
        } catch (ErrnoException e3) {
            Slog.e(TAG, "RuntimeInit: Failed capget", e3);
        }
    }
}
