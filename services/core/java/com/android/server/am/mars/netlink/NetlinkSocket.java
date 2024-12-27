package com.android.server.am.mars.netlink;

import android.system.Os;
import android.system.OsConstants;
import android.system.StructTimeval;

import java.io.FileDescriptor;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class NetlinkSocket {
    public static ByteBuffer recvMessage(FileDescriptor fileDescriptor, int i, long j) {
        if (j < 0) {
            throw new IllegalArgumentException("Negative timeouts not permitted");
        }
        Os.setsockoptTimeval(
                fileDescriptor,
                OsConstants.SOL_SOCKET,
                OsConstants.SO_RCVTIMEO,
                StructTimeval.fromMillis(j));
        ByteBuffer allocate = ByteBuffer.allocate(i);
        int read = Os.read(fileDescriptor, allocate);
        allocate.position(0);
        allocate.limit(read);
        allocate.order(ByteOrder.nativeOrder());
        return allocate;
    }
}
