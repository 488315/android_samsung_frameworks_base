package com.android.server.knox.dar.ddar.nativedaemon;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

import com.android.server.knox.dar.ddar.DDLog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class DualDarDaemonConnector implements Runnable, Handler.Callback {
    public final INativeDaemonConnectorCallbacks mCallback;
    public InputStream mInputStream;
    public OutputStream mOutputStream;
    public boolean mIsListening = true;
    public final Object mDaemonLock = new Object();
    public LocalSocket mSocket = null;
    public final ResponseQueue mResponseQueue = new ResponseQueue();
    public final AtomicInteger mSequenceNumber = new AtomicInteger(0);

    public final class ResponseQueue {
        public final LinkedList mPendingCmds = new LinkedList();
        public final int mMaxCount = 10;

        public final class PendingCmd {
            public int availableResponseCount;
            public final String cmd;
            public final int cmdNum;
            public final BlockingQueue responses = new ArrayBlockingQueue(10);

            public PendingCmd(int i, String str) {
                this.cmdNum = i;
                this.cmd = str;
            }
        }

        public final void add(int i, NativeDaemonEvent nativeDaemonEvent) {
            PendingCmd pendingCmd;
            synchronized (this.mPendingCmds) {
                try {
                    Iterator it = this.mPendingCmds.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            pendingCmd = null;
                            break;
                        } else {
                            pendingCmd = (PendingCmd) it.next();
                            if (pendingCmd.cmdNum == i) {
                                break;
                            }
                        }
                    }
                    if (pendingCmd == null) {
                        while (this.mPendingCmds.size() >= this.mMaxCount) {
                            DDLog.e(
                                    "DualDarDaemonConnector",
                                    "more buffered than allowed: "
                                            + this.mPendingCmds.size()
                                            + " >= "
                                            + this.mMaxCount,
                                    new Object[0]);
                            PendingCmd pendingCmd2 = (PendingCmd) this.mPendingCmds.remove();
                            DDLog.e(
                                    "DualDarDaemonConnector",
                                    "Removing request: "
                                            + pendingCmd2.cmd
                                            + " ("
                                            + pendingCmd2.cmdNum
                                            + ")",
                                    new Object[0]);
                        }
                        pendingCmd = new PendingCmd(i, null);
                        this.mPendingCmds.add(pendingCmd);
                    }
                    int i2 = pendingCmd.availableResponseCount + 1;
                    pendingCmd.availableResponseCount = i2;
                    if (i2 == 0) {
                        this.mPendingCmds.remove(pendingCmd);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            try {
                ((ArrayBlockingQueue) pendingCmd.responses).put(nativeDaemonEvent);
            } catch (InterruptedException unused) {
            }
        }

        public final NativeDaemonEvent remove(int i, String str) {
            NativeDaemonEvent nativeDaemonEvent;
            PendingCmd pendingCmd;
            synchronized (this.mPendingCmds) {
                try {
                    Iterator it = this.mPendingCmds.iterator();
                    while (true) {
                        nativeDaemonEvent = null;
                        if (!it.hasNext()) {
                            pendingCmd = null;
                            break;
                        }
                        pendingCmd = (PendingCmd) it.next();
                        if (pendingCmd.cmdNum == i) {
                            break;
                        }
                    }
                    if (pendingCmd == null) {
                        pendingCmd = new PendingCmd(i, str);
                        this.mPendingCmds.add(pendingCmd);
                    }
                    int i2 = pendingCmd.availableResponseCount - 1;
                    pendingCmd.availableResponseCount = i2;
                    if (i2 == 0) {
                        this.mPendingCmds.remove(pendingCmd);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            try {
                nativeDaemonEvent =
                        (NativeDaemonEvent)
                                ((ArrayBlockingQueue) pendingCmd.responses)
                                        .poll(60000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException unused) {
            }
            if (nativeDaemonEvent == null) {
                DDLog.e("DualDarDaemonConnector", "Timeout waiting for response", new Object[0]);
            }
            return nativeDaemonEvent;
        }
    }

    public DualDarDaemonConnector(INativeDaemonConnectorCallbacks iNativeDaemonConnectorCallbacks) {
        this.mCallback = iNativeDaemonConnectorCallbacks;
    }

    public static void makeCommand(
            StringBuilder sb, int i, String str, String str2, Object... objArr) {
        if (str2.indexOf(0) >= 0) {
            throw new IllegalArgumentException("Unexpected command: ".concat(str2));
        }
        if (str2.indexOf(32) >= 0) {
            throw new IllegalArgumentException("Arguments must be separate from command");
        }
        sb.append(i);
        sb.append(' ');
        sb.append(str);
        sb.append(' ');
        sb.append(str2);
        for (Object obj : objArr) {
            String valueOf = String.valueOf(obj);
            if (valueOf.indexOf(0) >= 0) {
                throw new IllegalArgumentException("Unexpected argument: ".concat(valueOf));
            }
            sb.append(' ');
            boolean z = valueOf.indexOf(32) >= 0;
            if (z) {
                sb.append('\"');
            }
            int length = valueOf.length();
            for (int i2 = 0; i2 < length; i2++) {
                char charAt = valueOf.charAt(i2);
                if (charAt == '\"') {
                    sb.append("\\\"");
                } else if (charAt == '\\') {
                    sb.append("\\\\");
                } else {
                    sb.append(charAt);
                }
            }
            if (z) {
                sb.append('\"');
            }
        }
        sb.append((char) 0);
    }

    public final synchronized NativeDaemonEvent executeSync(
            String str, String str2, Object... objArr) {
        NativeDaemonEvent remove;
        int i;
        StringBuilder sb = new StringBuilder(800);
        int incrementAndGet = this.mSequenceNumber.incrementAndGet();
        makeCommand(sb, incrementAndGet, str, str2, objArr);
        String substring = sb.substring(0);
        synchronized (this.mDaemonLock) {
            if (this.mOutputStream == null) {
                DDLog.e(
                        "DualDarDaemonConnector",
                        "Missing Output stream - cannot write commands!",
                        new Object[0]);
            } else {
                try {
                    byte[] bytes = substring.getBytes(StandardCharsets.UTF_8);
                    this.mOutputStream.write(bytes);
                    Arrays.fill(bytes, (byte) 0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            while (true) {
                DDLog.d(
                        "DualDarDaemonConnector",
                        "Command Sent : sequence Number "
                                + incrementAndGet
                                + "task is "
                                + str
                                + " Command is "
                                + str2,
                        new Object[0]);
                remove = this.mResponseQueue.remove(incrementAndGet, str2);
                if (remove == null) {
                    DDLog.e(
                            "DualDarDaemonConnector",
                            "timed-out waiting for response to " + str2,
                            new Object[0]);
                    break;
                }
                if (!remove.isClassContinue()) {
                    break;
                }
            }
            if (remove != null && (i = remove.mCode) >= 500 && i < 600) {
                DDLog.e(
                        "DualDarDaemonConnector",
                        "event = null or isClassClientError = true",
                        new Object[0]);
            }
            sb.delete(0, sb.length());
        }
        return remove;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        String str = (String) message.obj;
        try {
            INativeDaemonConnectorCallbacks iNativeDaemonConnectorCallbacks = this.mCallback;
            NativeDaemonEvent.unescapeArgs(str);
            iNativeDaemonConnectorCallbacks.getClass();
            DDLog.v("DualDarDaemonConnector", "Unhandled event '" + str + "'", new Object[0]);
            return true;
        } catch (Exception e) {
            DDLog.e("DualDarDaemonConnector", "Error handling '" + str + "': " + e, new Object[0]);
            return true;
        }
    }

    public final void listenToSocket() {
        int i;
        int read;
        try {
            try {
                byte[] bArr = new byte[4096];
                loop0:
                while (true) {
                    i = 0;
                    while (true) {
                        read = this.mInputStream.read(bArr, i, 4096 - i);
                        if (read < 0) {
                            break loop0;
                        }
                        int i2 = read + i;
                        int i3 = 0;
                        for (int i4 = 0; i4 < i2; i4++) {
                            if (bArr[i4] == 0) {
                                String str = new String(bArr, i3, i4 - i3, StandardCharsets.UTF_8);
                                DDLog.v("DualDarDaemonConnector", "rawEvent " + str, new Object[0]);
                                try {
                                    NativeDaemonEvent parseRawEvent =
                                            NativeDaemonEvent.parseRawEvent(str);
                                    int i5 = parseRawEvent.mCode;
                                    if (i5 < 600 || i5 >= 700) {
                                        this.mResponseQueue.add(
                                                parseRawEvent.mCmdNumber, parseRawEvent);
                                    }
                                } catch (IllegalArgumentException e) {
                                    DDLog.e(
                                            "DualDarDaemonConnector",
                                            "Problem parsing message " + e,
                                            new Object[0]);
                                }
                                i3 = i4 + 1;
                            }
                        }
                        if (i3 == 0) {
                            DDLog.v("DualDarDaemonConnector", "RCV incomplete", new Object[0]);
                        }
                        if (i3 != i2) {
                            i = 4096 - i3;
                            System.arraycopy(bArr, i3, bArr, 0, i);
                        }
                    }
                }
                DDLog.e(
                        "DualDarDaemonConnector",
                        "got " + read + " reading with start = " + i,
                        new Object[0]);
                for (int i6 = 0; i6 < 4096; i6++) {
                    bArr[i6] = 0;
                }
                synchronized (this.mDaemonLock) {
                    if (this.mOutputStream != null) {
                        try {
                            DDLog.e(
                                    "DualDarDaemonConnector",
                                    "closing stream for " + this.mSocket,
                                    new Object[0]);
                            this.mOutputStream.close();
                        } catch (IOException e2) {
                            DDLog.e(
                                    "DualDarDaemonConnector",
                                    "Failed closing output stream: " + e2,
                                    new Object[0]);
                        }
                        this.mOutputStream = null;
                    }
                }
                try {
                    LocalSocket localSocket = this.mSocket;
                    if (localSocket != null) {
                        localSocket.close();
                    }
                } catch (IOException e3) {
                    DDLog.e(
                            "DualDarDaemonConnector",
                            "Failed closing socket: " + e3,
                            new Object[0]);
                }
            } catch (IOException e4) {
                e4.printStackTrace();
                throw e4;
            }
        } catch (Throwable th) {
            synchronized (this.mDaemonLock) {
                if (this.mOutputStream != null) {
                    try {
                        DDLog.e(
                                "DualDarDaemonConnector",
                                "closing stream for " + this.mSocket,
                                new Object[0]);
                        this.mOutputStream.close();
                    } catch (IOException e5) {
                        DDLog.e(
                                "DualDarDaemonConnector",
                                "Failed closing output stream: " + e5,
                                new Object[0]);
                    }
                    this.mOutputStream = null;
                }
                try {
                    LocalSocket localSocket2 = this.mSocket;
                    if (localSocket2 == null) {
                        throw th;
                    }
                    localSocket2.close();
                    throw th;
                } catch (IOException e6) {
                    DDLog.e(
                            "DualDarDaemonConnector",
                            "Failed closing socket: " + e6,
                            new Object[0]);
                    throw th;
                }
            }
        }
    }

    public final void openSocketLocked() {
        try {
            LocalSocketAddress localSocketAddress =
                    new LocalSocketAddress("ddar", LocalSocketAddress.Namespace.RESERVED);
            this.mInputStream = null;
            DDLog.d("DualDarDaemonConnector", "Creating socket", new Object[0]);
            this.mSocket = new LocalSocket();
            DDLog.d("DualDarDaemonConnector", "Connecting socket", new Object[0]);
            this.mSocket.connect(localSocketAddress);
            this.mOutputStream = this.mSocket.getOutputStream();
            this.mInputStream = this.mSocket.getInputStream();
            DualDARDaemonProxy dualDARDaemonProxy = (DualDARDaemonProxy) this.mCallback;
            dualDARDaemonProxy.getClass();
            try {
                DDLog.d("DualDARDaemonProxy", "onDaemonConnected()", new Object[0]);
                synchronized (dualDARDaemonProxy.mLock) {
                    dualDARDaemonProxy.mLock.notify();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            DDLog.d("DualDarDaemonConnector", "DualDarDaemon connected !", new Object[0]);
        } catch (IOException e2) {
            DDLog.e(
                    "DualDarDaemonConnector",
                    "Caught an exception opening the socket: " + e2,
                    new Object[0]);
            DDLog.d("DualDarDaemonConnector", "Closing socket", new Object[0]);
            try {
                OutputStream outputStream = this.mOutputStream;
                if (outputStream != null) {
                    outputStream.close();
                    this.mOutputStream = null;
                }
            } catch (IOException e3) {
                DDLog.e(
                        "DualDarDaemonConnector",
                        "Failed closing output stream: " + e3,
                        new Object[0]);
            }
            try {
                LocalSocket localSocket = this.mSocket;
                if (localSocket != null) {
                    localSocket.close();
                    this.mSocket = null;
                }
            } catch (IOException e4) {
                DDLog.e("DualDarDaemonConnector", "Failed closing socket: " + e4, new Object[0]);
            }
            this.mCallback.getClass();
            throw e2;
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        while (this.mIsListening) {
            synchronized (this) {
                try {
                    openSocketLocked();
                } catch (Exception unused) {
                    SystemClock.sleep(100L);
                }
            }
            try {
                listenToSocket();
            } catch (Exception e) {
                DDLog.e(
                        "DualDarDaemonConnector",
                        "Error connecting to DualDAR daemon in NativeDaemonConnector: " + e,
                        new Object[0]);
                SystemClock.sleep(100L);
            }
        }
    }
}
