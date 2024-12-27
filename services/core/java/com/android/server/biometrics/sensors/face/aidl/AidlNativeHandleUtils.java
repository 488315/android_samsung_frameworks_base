package com.android.server.biometrics.sensors.face.aidl;

import android.hardware.common.NativeHandle;
import android.os.ParcelFileDescriptor;

import java.io.FileDescriptor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
}
