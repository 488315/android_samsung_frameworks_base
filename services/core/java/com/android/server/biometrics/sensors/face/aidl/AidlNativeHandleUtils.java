package com.android.server.biometrics.sensors.face.aidl;

import android.hardware.common.NativeHandle;
import android.os.ParcelFileDescriptor;
import java.io.FileDescriptor;

/* loaded from: classes.dex */
public abstract class AidlNativeHandleUtils {
    public static NativeHandle dup(android.os.NativeHandle nativeHandle) {
        if (nativeHandle == null) {
            return null;
        }
        NativeHandle nativeHandle2 = new NativeHandle();
        FileDescriptor[] fileDescriptors = nativeHandle.getFileDescriptors();
        nativeHandle2.ints = (int[]) nativeHandle.getInts().clone();
        nativeHandle2.fds = new ParcelFileDescriptor[fileDescriptors.length];
        for (int i = 0; i < fileDescriptors.length; i++) {
            nativeHandle2.fds[i] = ParcelFileDescriptor.dup(fileDescriptors[i]);
        }
        return nativeHandle2;
    }

    public static void close(NativeHandle nativeHandle) {
        if (nativeHandle != null) {
            for (ParcelFileDescriptor parcelFileDescriptor : nativeHandle.fds) {
                if (parcelFileDescriptor != null) {
                    parcelFileDescriptor.close();
                }
            }
        }
    }
}
