package com.android.internal.os;

import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.os.SystemClock;
import android.os.Trace;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructPollfd;
import android.util.Log;
import android.util.Slog;
import dalvik.system.ZygoteHooks;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.ToIntFunction;

/* loaded from: classes5.dex */
class ZygoteServer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int INVALID_TIMESTAMP = -1;
    public static final String TAG = "ZygoteServer";
    private boolean mCloseSocketFd;
    private boolean mIsFirstPropertyCheck;
    private boolean mIsForkChild;
    private long mLastPropCheckTimestamp;
    private boolean mUsapPoolEnabled;
    private final FileDescriptor mUsapPoolEventFD;
    private UsapPoolRefillAction mUsapPoolRefillAction;
    private int mUsapPoolRefillDelayMs;
    private int mUsapPoolRefillThreshold;
    private long mUsapPoolRefillTriggerTimestamp;
    private int mUsapPoolSizeMax;
    private int mUsapPoolSizeMin;
    private final LocalServerSocket mUsapPoolSocket;
    private final boolean mUsapPoolSupported;
    private LocalServerSocket mZygoteSocket;

    private enum UsapPoolRefillAction {
        DELAYED,
        IMMEDIATE,
        NONE
    }

    ZygoteServer() {
        this.mUsapPoolEnabled = false;
        this.mUsapPoolSizeMax = 0;
        this.mUsapPoolSizeMin = 0;
        this.mUsapPoolRefillThreshold = 0;
        this.mUsapPoolRefillDelayMs = -1;
        this.mIsFirstPropertyCheck = true;
        this.mLastPropCheckTimestamp = 0L;
        this.mUsapPoolEventFD = null;
        this.mZygoteSocket = null;
        this.mUsapPoolSocket = null;
        this.mUsapPoolSupported = false;
    }

    ZygoteServer(boolean z) {
        this.mUsapPoolEnabled = false;
        this.mUsapPoolSizeMax = 0;
        this.mUsapPoolSizeMin = 0;
        this.mUsapPoolRefillThreshold = 0;
        this.mUsapPoolRefillDelayMs = -1;
        this.mIsFirstPropertyCheck = true;
        this.mLastPropCheckTimestamp = 0L;
        this.mUsapPoolEventFD = Zygote.getUsapPoolEventFD();
        if (z) {
            this.mZygoteSocket = Zygote.createManagedSocketFromInitSocket(Zygote.PRIMARY_SOCKET_NAME);
            this.mUsapPoolSocket = Zygote.createManagedSocketFromInitSocket(Zygote.USAP_POOL_PRIMARY_SOCKET_NAME);
        } else {
            this.mZygoteSocket = Zygote.createManagedSocketFromInitSocket(Zygote.SECONDARY_SOCKET_NAME);
            this.mUsapPoolSocket = Zygote.createManagedSocketFromInitSocket(Zygote.USAP_POOL_SECONDARY_SOCKET_NAME);
        }
        this.mUsapPoolSupported = true;
        fetchUsapPoolPolicyProps();
    }

    void setForkChild() {
        this.mIsForkChild = true;
    }

    public boolean isUsapPoolEnabled() {
        return this.mUsapPoolEnabled;
    }

    void registerServerSocketAtAbstractName(String str) {
        if (this.mZygoteSocket == null) {
            try {
                this.mZygoteSocket = new LocalServerSocket(str);
                this.mCloseSocketFd = false;
            } catch (IOException e) {
                throw new RuntimeException("Error binding to abstract socket '" + str + "'", e);
            }
        }
    }

    private ZygoteConnection acceptCommandPeer(String str) {
        try {
            return createNewConnection(this.mZygoteSocket.accept(), str);
        } catch (IOException e) {
            throw new RuntimeException("IOException during accept()", e);
        }
    }

    protected ZygoteConnection createNewConnection(LocalSocket localSocket, String str) throws IOException {
        return new ZygoteConnection(localSocket, str);
    }

    void closeServerSocket() throws ErrnoException {
        try {
            LocalServerSocket localServerSocket = this.mZygoteSocket;
            if (localServerSocket != null) {
                FileDescriptor fileDescriptor = localServerSocket.getFileDescriptor();
                this.mZygoteSocket.close();
                if (fileDescriptor != null && this.mCloseSocketFd) {
                    Os.close(fileDescriptor);
                }
            }
        } catch (ErrnoException e) {
            Log.e(TAG, "Zygote:  error closing descriptor", e);
        } catch (IOException e2) {
            Log.e(TAG, "Zygote:  error closing sockets", e2);
        }
        this.mZygoteSocket = null;
    }

    FileDescriptor getZygoteSocketFileDescriptor() {
        return this.mZygoteSocket.getFileDescriptor();
    }

    private void fetchUsapPoolPolicyProps() {
        if (this.mUsapPoolSupported) {
            this.mUsapPoolSizeMax = Integer.min(ZygoteConfig.getInt(ZygoteConfig.USAP_POOL_SIZE_MAX, 3), 100);
            this.mUsapPoolSizeMin = Integer.max(ZygoteConfig.getInt(ZygoteConfig.USAP_POOL_SIZE_MIN, 1), 1);
            this.mUsapPoolRefillThreshold = Integer.min(ZygoteConfig.getInt(ZygoteConfig.USAP_POOL_REFILL_THRESHOLD, 1), this.mUsapPoolSizeMax);
            this.mUsapPoolRefillDelayMs = ZygoteConfig.getInt(ZygoteConfig.USAP_POOL_REFILL_DELAY_MS, 3000);
            if (this.mUsapPoolSizeMin >= this.mUsapPoolSizeMax) {
                Log.w(TAG, "The max size of the USAP pool must be greater than the minimum size.  Restoring default values.");
                this.mUsapPoolSizeMax = 3;
                this.mUsapPoolSizeMin = 1;
                this.mUsapPoolRefillThreshold = 3 / 2;
            }
        }
    }

    private void fetchUsapPoolPolicyPropsWithMinInterval() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (this.mIsFirstPropertyCheck || jElapsedRealtime - this.mLastPropCheckTimestamp >= 60000) {
            this.mIsFirstPropertyCheck = false;
            this.mLastPropCheckTimestamp = jElapsedRealtime;
            fetchUsapPoolPolicyProps();
        }
    }

    private void fetchUsapPoolPolicyPropsIfUnfetched() {
        if (this.mIsFirstPropertyCheck) {
            this.mIsFirstPropertyCheck = false;
            fetchUsapPoolPolicyProps();
        }
    }

    Runnable fillUsapPool(int[] iArr, boolean z) {
        int i;
        Runnable runnableForkUsap;
        Trace.traceBegin(64L, "Zygote:FillUsapPool");
        fetchUsapPoolPolicyPropsIfUnfetched();
        int usapPoolCount = Zygote.getUsapPoolCount();
        if (z) {
            i = this.mUsapPoolSizeMin - usapPoolCount;
            Log.i(Zygote.PRIMARY_SOCKET_NAME, "Priority USAP Pool refill. New USAPs: " + i);
        } else {
            i = this.mUsapPoolSizeMax - usapPoolCount;
            Log.i(Zygote.PRIMARY_SOCKET_NAME, "Delayed USAP Pool refill. New USAPs: " + i);
        }
        ZygoteHooks.preFork();
        do {
            i--;
            if (i >= 0) {
                runnableForkUsap = Zygote.forkUsap(this.mUsapPoolSocket, iArr, z);
            } else {
                ZygoteHooks.postForkCommon();
                resetUsapRefillState();
                Trace.traceEnd(64L);
                return null;
            }
        } while (runnableForkUsap == null);
        return runnableForkUsap;
    }

    Runnable setUsapPoolStatus(boolean z, LocalSocket localSocket) {
        if (!this.mUsapPoolSupported) {
            Log.w(TAG, "Attempting to enable a USAP pool for a Zygote that doesn't support it.");
            return null;
        }
        if (this.mUsapPoolEnabled == z) {
            return null;
        }
        Log.i(TAG, "USAP Pool status change: ".concat(z ? "ENABLED" : "DISABLED"));
        this.mUsapPoolEnabled = z;
        if (z) {
            return fillUsapPool(new int[]{localSocket.getFileDescriptor().getInt$()}, false);
        }
        Zygote.emptyUsapPool();
        return null;
    }

    private void resetUsapRefillState() {
        this.mUsapPoolRefillAction = UsapPoolRefillAction.NONE;
        this.mUsapPoolRefillTriggerTimestamp = -1L;
    }

    /* JADX WARN: Finally extract failed */
    Runnable runSelectLoop(String str) throws IOException, ErrnoException {
        StructPollfd[] structPollfdArr;
        int[] usapPipeFDs;
        int i;
        int i2;
        int i3;
        int i4;
        boolean z;
        int i5;
        boolean z2;
        ZygoteConnection zygoteConnection;
        Runnable runnableProcessCommand;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList.add(this.mZygoteSocket.getFileDescriptor());
        int[] iArr = null;
        arrayList2.add(null);
        this.mUsapPoolRefillTriggerTimestamp = -1L;
        while (true) {
            fetchUsapPoolPolicyPropsWithMinInterval();
            this.mUsapPoolRefillAction = UsapPoolRefillAction.NONE;
            if (this.mUsapPoolEnabled) {
                usapPipeFDs = Zygote.getUsapPipeFDs();
                structPollfdArr = new StructPollfd[arrayList.size() + 1 + usapPipeFDs.length];
            } else {
                structPollfdArr = new StructPollfd[arrayList.size()];
                usapPipeFDs = iArr;
            }
            Iterator it = arrayList.iterator();
            int i6 = 0;
            while (it.hasNext()) {
                FileDescriptor fileDescriptor = (FileDescriptor) it.next();
                StructPollfd structPollfd = new StructPollfd();
                structPollfdArr[i6] = structPollfd;
                structPollfd.fd = fileDescriptor;
                structPollfdArr[i6].events = (short) OsConstants.POLLIN;
                i6++;
            }
            if (this.mUsapPoolEnabled) {
                StructPollfd structPollfd2 = new StructPollfd();
                structPollfdArr[i6] = structPollfd2;
                structPollfd2.fd = this.mUsapPoolEventFD;
                structPollfdArr[i6].events = (short) OsConstants.POLLIN;
                i = i6 + 1;
                for (int i7 : usapPipeFDs) {
                    FileDescriptor fileDescriptor2 = new FileDescriptor();
                    fileDescriptor2.setInt$(i7);
                    StructPollfd structPollfd3 = new StructPollfd();
                    structPollfdArr[i] = structPollfd3;
                    structPollfd3.fd = fileDescriptor2;
                    structPollfdArr[i].events = (short) OsConstants.POLLIN;
                    i++;
                }
            } else {
                i = i6;
            }
            if (this.mUsapPoolRefillTriggerTimestamp == -1) {
                i4 = i6;
                i3 = -1;
                i2 = -1;
            } else {
                i2 = -1;
                long jCurrentTimeMillis = System.currentTimeMillis() - this.mUsapPoolRefillTriggerTimestamp;
                i3 = this.mUsapPoolRefillDelayMs;
                i4 = i6;
                if (jCurrentTimeMillis >= i3) {
                    this.mUsapPoolRefillTriggerTimestamp = -1L;
                    this.mUsapPoolRefillAction = UsapPoolRefillAction.DELAYED;
                    i3 = 0;
                } else if (jCurrentTimeMillis > 0) {
                    i3 = (int) (i3 - jCurrentTimeMillis);
                }
            }
            try {
                if (Os.poll(structPollfdArr, i3) == 0) {
                    this.mUsapPoolRefillTriggerTimestamp = -1L;
                    this.mUsapPoolRefillAction = UsapPoolRefillAction.DELAYED;
                    z = false;
                } else {
                    boolean z3 = false;
                    while (true) {
                        i += i2;
                        if (i >= 0) {
                            if ((structPollfdArr[i].revents & OsConstants.POLLIN) == 0) {
                                i5 = i4;
                            } else if (i == 0) {
                                ZygoteConnection zygoteConnectionAcceptCommandPeer = acceptCommandPeer(str);
                                arrayList2.add(zygoteConnectionAcceptCommandPeer);
                                arrayList.add(zygoteConnectionAcceptCommandPeer.getFileDescriptor());
                                i5 = i4;
                            } else {
                                i5 = i4;
                                if (i < i5) {
                                    try {
                                        try {
                                            zygoteConnection = (ZygoteConnection) arrayList2.get(i);
                                            runnableProcessCommand = zygoteConnection.processCommand(this, !isUsapPoolEnabled() && ZygoteHooks.isIndefiniteThreadSuspensionSafe());
                                        } catch (Exception e) {
                                            if (!this.mIsForkChild) {
                                                Slog.e(TAG, "Exception executing zygote command: ", e);
                                                ((ZygoteConnection) arrayList2.remove(i)).closeSocket();
                                                arrayList.remove(i);
                                                z2 = false;
                                            } else {
                                                Log.e(TAG, "Caught post-fork exception in child process.", e);
                                                throw e;
                                            }
                                        }
                                        if (this.mIsForkChild) {
                                            if (runnableProcessCommand == null) {
                                                throw new IllegalStateException("command == null");
                                            }
                                            this.mIsForkChild = false;
                                            return runnableProcessCommand;
                                        }
                                        if (runnableProcessCommand != null) {
                                            throw new IllegalStateException("command != null");
                                        }
                                        if (zygoteConnection.isClosedByPeer()) {
                                            zygoteConnection.closeSocket();
                                            arrayList2.remove(i);
                                            arrayList.remove(i);
                                        }
                                        z2 = false;
                                        this.mIsForkChild = z2;
                                    } catch (Throwable th) {
                                        this.mIsForkChild = false;
                                        throw th;
                                    }
                                } else {
                                    try {
                                        byte[] bArr = new byte[8];
                                        int i8 = Os.read(structPollfdArr[i].fd, bArr, 0, 8);
                                        if (i8 != 8) {
                                            Log.e(TAG, "Incomplete read from USAP management FD of size " + i8);
                                        } else {
                                            long j = new DataInputStream(new ByteArrayInputStream(bArr)).readLong();
                                            if (i > i5) {
                                                Zygote.removeUsapTableEntry((int) j);
                                            }
                                            z3 = true;
                                        }
                                    } catch (Exception e2) {
                                        if (i == i5) {
                                            Log.e(TAG, "Failed to read from USAP pool event FD: " + e2.getMessage());
                                        } else {
                                            Log.e(TAG, "Failed to read from USAP reporting pipe: " + e2.getMessage());
                                        }
                                    }
                                }
                            }
                            i4 = i5;
                        } else {
                            z = false;
                            if (z3) {
                                int usapPoolCount = Zygote.getUsapPoolCount();
                                if (usapPoolCount < this.mUsapPoolSizeMin) {
                                    this.mUsapPoolRefillAction = UsapPoolRefillAction.IMMEDIATE;
                                } else if (this.mUsapPoolSizeMax - usapPoolCount >= this.mUsapPoolRefillThreshold) {
                                    this.mUsapPoolRefillTriggerTimestamp = System.currentTimeMillis();
                                }
                            }
                        }
                    }
                }
                if (this.mUsapPoolRefillAction != UsapPoolRefillAction.NONE) {
                    int[] array = arrayList.subList(1, arrayList.size()).stream().mapToInt(new ToIntFunction() { // from class: com.android.internal.os.ZygoteServer$$ExternalSyntheticLambda0
                        @Override // java.util.function.ToIntFunction
                        public final int applyAsInt(Object obj) {
                            return ((FileDescriptor) obj).getInt$();
                        }
                    }).toArray();
                    boolean z4 = this.mUsapPoolRefillAction != UsapPoolRefillAction.IMMEDIATE ? z : true;
                    Runnable runnableFillUsapPool = fillUsapPool(array, z4);
                    if (runnableFillUsapPool != null) {
                        return runnableFillUsapPool;
                    }
                    if (z4) {
                        this.mUsapPoolRefillTriggerTimestamp = System.currentTimeMillis();
                    }
                }
                iArr = null;
            } catch (ErrnoException e3) {
                throw new RuntimeException("poll failed", e3);
            }
        }
    }
}
