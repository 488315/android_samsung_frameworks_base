package com.android.server.am;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.MessageQueue;
import android.util.Slog;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LmkdConnection {
    public final ByteBuffer mInputBuf;
    public final DataInputStream mInputData;
    public final ProcessList.AnonymousClass1 mListener;
    public final MessageQueue mMsgQueue;
    public ByteBuffer mReplyBuf;
    public final Object mReplyBufLock;
    public final Object mLmkdSocketLock = new Object();
    public LocalSocket mLmkdSocket = null;
    public OutputStream mLmkdOutputStream = null;
    public InputStream mLmkdInputStream = null;

    public LmkdConnection(MessageQueue messageQueue, ProcessList.AnonymousClass1 anonymousClass1) {
        ByteBuffer allocate = ByteBuffer.allocate(222);
        this.mInputBuf = allocate;
        this.mInputData = new DataInputStream(new ByteArrayInputStream(allocate.array()));
        this.mReplyBufLock = new Object();
        this.mReplyBuf = null;
        this.mMsgQueue = messageQueue;
        this.mListener = anonymousClass1;
    }

    public static LocalSocket openSocket() {
        try {
            LocalSocket localSocket = new LocalSocket(3);
            localSocket.connect(
                    new LocalSocketAddress("lmkd", LocalSocketAddress.Namespace.RESERVED));
            return localSocket;
        } catch (IOException e) {
            Slog.e("ActivityManager", "Connection failed: " + e.toString());
            return null;
        }
    }

    public final boolean write(ByteBuffer byteBuffer) {
        synchronized (this.mLmkdSocketLock) {
            try {
                try {
                    this.mLmkdOutputStream.write(byteBuffer.array(), 0, byteBuffer.position());
                } catch (IOException unused) {
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }
}
