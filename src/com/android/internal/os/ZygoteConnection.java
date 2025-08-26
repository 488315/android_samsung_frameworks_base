package com.android.internal.os;

import android.content.pm.ApplicationInfo;
import android.net.Credentials;
import android.net.LocalSocket;
import android.os.Parcel;
import android.os.Process;
import android.os.Trace;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructPollfd;
import android.util.Log;
import dalvik.system.VMRuntime;
import dalvik.system.ZygoteHooks;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

/* loaded from: classes5.dex */
class ZygoteConnection {
    private static final String TAG = "Zygote";
    private final String abiList;
    private boolean isEof;
    private final LocalSocket mSocket;
    private final DataOutputStream mSocketOutStream;
    private final Credentials peer;

    protected boolean canPreloadApp() {
        return false;
    }

    ZygoteConnection(LocalSocket localSocket, String str) throws IOException, ErrnoException {
        this.mSocket = localSocket;
        this.abiList = str;
        this.mSocketOutStream = new DataOutputStream(localSocket.getOutputStream());
        localSocket.setSoTimeout(1000);
        try {
            Credentials peerCredentials = localSocket.getPeerCredentials();
            this.peer = peerCredentials;
            if (peerCredentials.getUid() != 1000) {
                throw new ZygoteSecurityException("Only system UID is allowed to connect to Zygote.");
            }
            this.isEof = false;
        } catch (IOException e) {
            Log.e(TAG, "Cannot read peer credentials", e);
            throw e;
        }
    }

    FileDescriptor getFileDescriptor() {
        return this.mSocket.getFileDescriptor();
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x01b2, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x01b3, code lost:
    
        r4 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x01b6, code lost:
    
        libcore.io.IoUtils.closeQuietly(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x01b9, code lost:
    
        handleParentProc(r2, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x01bc, code lost:
    
        libcore.io.IoUtils.closeQuietly((java.io.FileDescriptor) null);
        libcore.io.IoUtils.closeQuietly(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x01c2, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x01c5, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x01c6, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x01c7, code lost:
    
        r5 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x01ca, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x01cb, code lost:
    
        libcore.io.IoUtils.closeQuietly(r5);
        libcore.io.IoUtils.closeQuietly(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x01d1, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x01fe, code lost:
    
        throw new com.android.internal.os.ZygoteSecurityException("Client may not specify capabilities: permitted=0x" + java.lang.Long.toHexString(r3.mPermittedCapabilities) + ", effective=0x" + java.lang.Long.toHexString(r3.mEffectiveCapabilities));
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x01ff, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0206, code lost:
    
        if (r3.mUsapPoolStatusSpecified == false) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x020e, code lost:
    
        return handleUsapPoolStatusChange(r28, r3.mUsapPoolEnabled);
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x0211, code lost:
    
        if (r3.mApiDenylistExemptions == null) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x0219, code lost:
    
        return handleApiDenylistExemptions(r28, r3.mApiDenylistExemptions);
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x021c, code lost:
    
        if (r3.mHiddenApiAccessLogSampleRate != (-1)) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0220, code lost:
    
        if (r3.mHiddenApiAccessStatslogSampleRate == (-1)) goto L127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x022a, code lost:
    
        throw new java.lang.AssertionError("Shouldn't get here");
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x0233, code lost:
    
        return handleHiddenApiAccessLogSampleRate(r28, r3.mHiddenApiAccessLogSampleRate, r3.mHiddenApiAccessStatslogSampleRate);
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0153, code lost:
    
        r4 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x016d, code lost:
    
        r5 = r9;
        r2 = com.android.internal.os.Zygote.forkAndSpecialize(r3.mUid, r3.mGid, r3.mGids, r3.mRuntimeFlags, r10, r3.mMountExternal, r3.mSeInfo, r3.mNiceName, r14, r15, r3.mStartChildZygote, r3.mInstructionSet, r3.mAppDataDir, r3.mIsTopApp, r3.mPkgDataInfoList, r3.mAllowlistedDataInfoList, r3.mBindMountAppDataDirs, r3.mBindMountAppStorageDirs, r3.mBindMountSyspropOverrides);
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0197, code lost:
    
        if (r2 != 0) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0199, code lost:
    
        r28.setForkChild();
        r28.closeServerSocket();
        libcore.io.IoUtils.closeQuietly(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x01a2, code lost:
    
        r0 = handleChildProc(r3, r5, r3.mStartChildZygote);
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x01a8, code lost:
    
        libcore.io.IoUtils.closeQuietly(r5);
        libcore.io.IoUtils.closeQuietly((java.io.FileDescriptor) null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x01ae, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x01b1, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    Runnable processCommand(ZygoteServer zygoteServer, boolean z) throws Throwable {
        ZygoteCommandBuffer zygoteCommandBuffer;
        Throwable th;
        FileDescriptor fileDescriptor;
        FileDescriptor fileDescriptor2;
        int[] iArr;
        ZygoteCommandBuffer zygoteCommandBuffer2 = new ZygoteCommandBuffer(this.mSocket);
        while (true) {
            try {
                try {
                    try {
                        ZygoteArguments zygoteArguments = ZygoteArguments.getInstance(zygoteCommandBuffer2);
                        if (zygoteArguments != null) {
                            if (!zygoteArguments.mBootCompleted) {
                                if (!zygoteArguments.mAbiListQuery) {
                                    if (!zygoteArguments.mPidQuery) {
                                        if (zygoteArguments.mUsapPoolStatusSpecified || zygoteArguments.mApiDenylistExemptions != null || zygoteArguments.mHiddenApiAccessLogSampleRate != -1 || zygoteArguments.mHiddenApiAccessStatslogSampleRate != -1) {
                                            break;
                                        }
                                        if (!zygoteArguments.mPreloadDefault) {
                                            if (canPreloadApp()) {
                                                try {
                                                    if (zygoteArguments.mPreloadApp != null) {
                                                        byte[] bArrDecode = Base64.getDecoder().decode(zygoteArguments.mPreloadApp);
                                                        Parcel parcelObtain = Parcel.obtain();
                                                        parcelObtain.unmarshall(bArrDecode, 0, bArrDecode.length);
                                                        parcelObtain.setDataPosition(0);
                                                        ApplicationInfo applicationInfoCreateFromParcel = ApplicationInfo.CREATOR.createFromParcel(parcelObtain);
                                                        parcelObtain.recycle();
                                                        if (applicationInfoCreateFromParcel == null) {
                                                            throw new IllegalArgumentException("Failed to deserialize --preload-app");
                                                        }
                                                        handlePreloadApp(applicationInfoCreateFromParcel);
                                                        zygoteCommandBuffer2.close();
                                                        return null;
                                                    }
                                                } catch (Throwable th2) {
                                                    th = th2;
                                                    zygoteCommandBuffer = zygoteCommandBuffer2;
                                                    try {
                                                        zygoteCommandBuffer.close();
                                                        throw th;
                                                    } catch (Throwable th3) {
                                                        th.addSuppressed(th3);
                                                        throw th;
                                                    }
                                                }
                                            }
                                            if (zygoteArguments.mPermittedCapabilities != 0 || zygoteArguments.mEffectiveCapabilities != 0) {
                                                break;
                                            }
                                            Zygote.applyUidSecurityPolicy(zygoteArguments, this.peer);
                                            Zygote.applyInvokeWithSecurityPolicy(zygoteArguments, this.peer);
                                            Zygote.applyDebuggerSystemProperty(zygoteArguments);
                                            Zygote.applyInvokeWithSystemProperty(zygoteArguments);
                                            int[][] iArr2 = zygoteArguments.mRLimits != null ? (int[][]) zygoteArguments.mRLimits.toArray(Zygote.INT_ARRAY_2D) : null;
                                            if (zygoteArguments.mInvokeWith != null) {
                                                try {
                                                    FileDescriptor[] fileDescriptorArrPipe2 = Os.pipe2(OsConstants.O_CLOEXEC);
                                                    fileDescriptor = fileDescriptorArrPipe2[1];
                                                    fileDescriptor2 = fileDescriptorArrPipe2[0];
                                                    Os.fcntlInt(fileDescriptor, OsConstants.F_SETFD, 0);
                                                    iArr = new int[]{fileDescriptor.getInt$(), fileDescriptor2.getInt$()};
                                                } catch (ErrnoException e) {
                                                    throw new IllegalStateException("Unable to set up pipe for invoke-with", e);
                                                }
                                            } else {
                                                fileDescriptor2 = null;
                                                fileDescriptor = null;
                                                iArr = null;
                                            }
                                            int[] iArr3 = {-1, -1};
                                            FileDescriptor fileDescriptor3 = this.mSocket.getFileDescriptor();
                                            if (fileDescriptor3 != null) {
                                                iArr3[0] = fileDescriptor3.getInt$();
                                            }
                                            FileDescriptor zygoteSocketFileDescriptor = zygoteServer.getZygoteSocketFileDescriptor();
                                            if (zygoteSocketFileDescriptor != null) {
                                                iArr3[1] = zygoteSocketFileDescriptor.getInt$();
                                            }
                                            if (zygoteArguments.mInvokeWith != null || zygoteArguments.mStartChildZygote || !z || this.peer.getUid() != 1000) {
                                                break;
                                            }
                                            ZygoteHooks.preFork();
                                            Runnable runnableForkSimpleApps = Zygote.forkSimpleApps(zygoteCommandBuffer2, zygoteServer.getZygoteSocketFileDescriptor(), this.peer.getUid(), Zygote.minChildUid(this.peer), zygoteArguments.mNiceName);
                                            if (runnableForkSimpleApps != null) {
                                                zygoteServer.setForkChild();
                                                zygoteCommandBuffer2.close();
                                                return runnableForkSimpleApps;
                                            }
                                            ZygoteHooks.postForkCommon();
                                        } else {
                                            handlePreload();
                                            zygoteCommandBuffer2.close();
                                            return null;
                                        }
                                    } else {
                                        handlePidQuery();
                                        zygoteCommandBuffer2.close();
                                        return null;
                                    }
                                } else {
                                    handleAbiListQuery();
                                    zygoteCommandBuffer2.close();
                                    return null;
                                }
                            } else {
                                handleBootCompleted();
                                zygoteCommandBuffer2.close();
                                return null;
                            }
                        } else {
                            this.isEof = true;
                            zygoteCommandBuffer2.close();
                            return null;
                        }
                    } catch (IOException e2) {
                        throw new IllegalStateException("IOException on command socket", e2);
                    }
                } catch (Throwable th4) {
                    th = th4;
                    th = th;
                    zygoteCommandBuffer.close();
                    throw th;
                }
            } catch (Throwable th5) {
                th = th5;
                zygoteCommandBuffer = zygoteCommandBuffer2;
                th = th;
                zygoteCommandBuffer.close();
                throw th;
            }
        }
    }

    private void handleAbiListQuery() throws IOException {
        try {
            byte[] bytes = this.abiList.getBytes(StandardCharsets.US_ASCII);
            this.mSocketOutStream.writeInt(bytes.length);
            this.mSocketOutStream.write(bytes);
        } catch (IOException e) {
            throw new IllegalStateException("Error writing to command socket", e);
        }
    }

    private void handlePidQuery() throws IOException {
        try {
            byte[] bytes = String.valueOf(Process.myPid()).getBytes(StandardCharsets.US_ASCII);
            this.mSocketOutStream.writeInt(bytes.length);
            this.mSocketOutStream.write(bytes);
        } catch (IOException e) {
            throw new IllegalStateException("Error writing to command socket", e);
        }
    }

    private void handleBootCompleted() throws IOException {
        try {
            this.mSocketOutStream.writeInt(0);
            VMRuntime.bootCompleted();
        } catch (IOException e) {
            throw new IllegalStateException("Error writing to command socket", e);
        }
    }

    private void handlePreload() throws IOException {
        try {
            if (isPreloadComplete()) {
                this.mSocketOutStream.writeInt(1);
            } else {
                preload();
                this.mSocketOutStream.writeInt(0);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Error writing to command socket", e);
        }
    }

    private Runnable stateChangeWithUsapPoolReset(ZygoteServer zygoteServer, Runnable runnable) throws IOException {
        try {
            if (zygoteServer.isUsapPoolEnabled()) {
                Log.i(TAG, "Emptying USAP Pool due to state change.");
                Zygote.emptyUsapPool();
            }
            runnable.run();
            if (zygoteServer.isUsapPoolEnabled()) {
                Runnable runnableFillUsapPool = zygoteServer.fillUsapPool(new int[]{this.mSocket.getFileDescriptor().getInt$()}, false);
                if (runnableFillUsapPool != null) {
                    zygoteServer.setForkChild();
                    return runnableFillUsapPool;
                }
                Log.i(TAG, "Finished refilling USAP Pool after state change.");
            }
            this.mSocketOutStream.writeInt(0);
            return null;
        } catch (IOException e) {
            throw new IllegalStateException("Error writing to command socket", e);
        }
    }

    private Runnable handleApiDenylistExemptions(ZygoteServer zygoteServer, final String[] strArr) {
        return stateChangeWithUsapPoolReset(zygoteServer, new Runnable() { // from class: com.android.internal.os.ZygoteConnection$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ZygoteInit.setApiDenylistExemptions(strArr);
            }
        });
    }

    private Runnable handleUsapPoolStatusChange(ZygoteServer zygoteServer, boolean z) throws IOException {
        try {
            Runnable usapPoolStatus = zygoteServer.setUsapPoolStatus(z, this.mSocket);
            if (usapPoolStatus == null) {
                this.mSocketOutStream.writeInt(0);
                return usapPoolStatus;
            }
            zygoteServer.setForkChild();
            return usapPoolStatus;
        } catch (IOException e) {
            throw new IllegalStateException("Error writing to command socket", e);
        }
    }

    private Runnable handleHiddenApiAccessLogSampleRate(ZygoteServer zygoteServer, final int i, final int i2) {
        return stateChangeWithUsapPoolReset(zygoteServer, new Runnable() { // from class: com.android.internal.os.ZygoteConnection$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ZygoteConnection.lambda$handleHiddenApiAccessLogSampleRate$1(i, i2);
            }
        });
    }

    static /* synthetic */ void lambda$handleHiddenApiAccessLogSampleRate$1(int i, int i2) {
        ZygoteInit.setHiddenApiAccessLogSampleRate(Math.max(i, i2));
        StatsdHiddenApiUsageLogger.setHiddenApiAccessLogSampleRates(i, i2);
        ZygoteInit.setHiddenApiUsageLogger(StatsdHiddenApiUsageLogger.getInstance());
    }

    protected void preload() {
        ZygoteInit.lazyPreload();
    }

    protected boolean isPreloadComplete() {
        return ZygoteInit.isPreloadComplete();
    }

    protected DataOutputStream getSocketOutputStream() {
        return this.mSocketOutStream;
    }

    protected void handlePreloadApp(ApplicationInfo applicationInfo) {
        throw new RuntimeException("Zygote does not support app preloading");
    }

    void closeSocket() {
        try {
            this.mSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Exception while closing command socket in parent", e);
        }
    }

    boolean isClosedByPeer() {
        return this.isEof;
    }

    private Runnable handleChildProc(ZygoteArguments zygoteArguments, FileDescriptor fileDescriptor, boolean z) {
        closeSocket();
        Zygote.setAppProcessName(zygoteArguments, TAG);
        Trace.traceEnd(64L);
        if (zygoteArguments.mInvokeWith != null) {
            WrapperInit.execApplication(zygoteArguments.mInvokeWith, zygoteArguments.mNiceName, zygoteArguments.mTargetSdkVersion, VMRuntime.getCurrentInstructionSet(), fileDescriptor, zygoteArguments.mRemainingArgs);
            throw new IllegalStateException("WrapperInit.execApplication unexpectedly returned");
        }
        if (!z) {
            return ZygoteInit.zygoteInit(zygoteArguments.mTargetSdkVersion, zygoteArguments.mDisabledCompatChanges, zygoteArguments.mRemainingArgs, null);
        }
        return ZygoteInit.childZygoteInit(zygoteArguments.mRemainingArgs);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00eb A[PHI: r16
      0x00eb: PHI (r16v1 short) = (r16v0 short), (r16v5 short), (r16v5 short) binds: [B:52:0x00e9, B:44:0x00ad, B:51:0x00cf] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void handleParentProc(int i, FileDescriptor fileDescriptor) throws IOException, ErrnoException {
        short s;
        boolean z;
        int i2;
        int i3;
        int i4;
        int i5 = i;
        if (i5 > 0) {
            setChildPgid(i);
        }
        short s2 = 0;
        try {
            if (fileDescriptor != null && i5 > 0) {
                try {
                    StructPollfd[] structPollfdArr = {new StructPollfd()};
                    int i6 = 4;
                    byte[] bArr = new byte[4];
                    long jNanoTime = System.nanoTime();
                    int i7 = 0;
                    int iConvert = 20000;
                    while (i7 < i6 && iConvert > 0) {
                        structPollfdArr[s2].fd = fileDescriptor;
                        structPollfdArr[s2].events = (short) OsConstants.POLLIN;
                        structPollfdArr[s2].revents = s2;
                        structPollfdArr[s2].userData = null;
                        int iPoll = Os.poll(structPollfdArr, iConvert);
                        s = s2;
                        try {
                            iConvert = 20000 - ((int) TimeUnit.MILLISECONDS.convert(System.nanoTime() - jNanoTime, TimeUnit.NANOSECONDS));
                            if (iPoll > 0) {
                                if ((structPollfdArr[s].revents & OsConstants.POLLIN) == 0) {
                                    i4 = 4;
                                    break;
                                }
                                int i8 = Os.read(fileDescriptor, bArr, i7, 1);
                                if (i8 < 0) {
                                    throw new RuntimeException("Some error");
                                }
                                i7 += i8;
                            } else if (iPoll == 0) {
                                Log.w(TAG, "Timed out waiting for child.");
                            }
                            s2 = s;
                            i6 = 4;
                        } catch (Exception e) {
                            e = e;
                            i2 = -1;
                            Log.w(TAG, "Error reading pid from wrapped process, child may have died", e);
                            i3 = i2;
                            if (i3 > 0) {
                                z = s;
                            }
                            this.mSocketOutStream.writeInt(i5);
                            this.mSocketOutStream.writeBoolean(z);
                            return;
                        }
                    }
                    s = s2;
                    i4 = i6;
                    i3 = i7 == i4 ? new DataInputStream(new ByteArrayInputStream(bArr)).readInt() : -1;
                    if (i3 == -1) {
                        try {
                            Log.w(TAG, "Error reading pid from wrapped process, child may have died");
                        } catch (Exception e2) {
                            e = e2;
                            i2 = i3;
                            Log.w(TAG, "Error reading pid from wrapped process, child may have died", e);
                            i3 = i2;
                            if (i3 > 0) {
                            }
                            this.mSocketOutStream.writeInt(i5);
                            this.mSocketOutStream.writeBoolean(z);
                            return;
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    s = s2;
                }
                if (i3 > 0) {
                    int parentPid = i3;
                    while (parentPid > 0 && parentPid != i5) {
                        parentPid = Process.getParentPid(parentPid);
                    }
                    if (parentPid > 0) {
                        Log.i(TAG, "Wrapped process has pid " + i3);
                        i5 = i3;
                        z = 1;
                    } else {
                        Log.w(TAG, "Wrapped process reported a pid that is not a child of the process that we forked: childPid=" + i5 + " innerPid=" + i3);
                    }
                }
                this.mSocketOutStream.writeInt(i5);
                this.mSocketOutStream.writeBoolean(z);
                return;
            }
            s = 0;
            this.mSocketOutStream.writeInt(i5);
            this.mSocketOutStream.writeBoolean(z);
            return;
        } catch (IOException e4) {
            throw new IllegalStateException("Error writing to command socket", e4);
        }
        z = s;
    }

    private void setChildPgid(int i) {
        try {
            Os.setpgid(i, Os.getpgid(this.peer.getPid()));
        } catch (ErrnoException unused) {
            Log.i(TAG, "Zygote: setpgid failed. This is normal if peer is not in our session");
        }
    }
}
