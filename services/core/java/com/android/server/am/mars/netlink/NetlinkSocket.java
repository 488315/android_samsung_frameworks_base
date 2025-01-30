package com.android.server.am.mars.netlink;

import android.net.util.SocketUtils;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructTimeval;
import java.io.FileDescriptor;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public abstract class NetlinkSocket {
    public static FileDescriptor forProto(int i) {
        FileDescriptor socket = Os.socket(OsConstants.AF_NETLINK, OsConstants.SOCK_DGRAM, i);
        Os.setsockoptInt(socket, OsConstants.SOL_SOCKET, OsConstants.SO_RCVBUF, 65536);
        return socket;
    }

    public static void connectToKernel(FileDescriptor fileDescriptor) {
        Os.connect(fileDescriptor, SocketUtils.makeNetlinkSocketAddress(0, 0));
    }

    public static void checkTimeout(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("Negative timeouts not permitted");
        }
    }

    public static ByteBuffer recvMessage(FileDescriptor fileDescriptor, int i, long j) {
        checkTimeout(j);
        Os.setsockoptTimeval(fileDescriptor, OsConstants.SOL_SOCKET, OsConstants.SO_RCVTIMEO, StructTimeval.fromMillis(j));
        ByteBuffer allocate = ByteBuffer.allocate(i);
        int read = Os.read(fileDescriptor, allocate);
        allocate.position(0);
        allocate.limit(read);
        allocate.order(ByteOrder.nativeOrder());
        return allocate;
    }

    public static int sendMessage(FileDescriptor fileDescriptor, byte[] bArr, int i, int i2, long j) {
        checkTimeout(j);
        Os.setsockoptTimeval(fileDescriptor, OsConstants.SOL_SOCKET, OsConstants.SO_SNDTIMEO, StructTimeval.fromMillis(j));
        return Os.write(fileDescriptor, bArr, i, i2);
    }
}
