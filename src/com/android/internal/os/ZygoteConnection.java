package com.android.internal.os;

import android.content.pm.ApplicationInfo;
import android.net.Credentials;
import android.net.LocalSocket;
import android.os.Process;
import android.os.Trace;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Log;
import dalvik.system.VMRuntime;
import java.io.DataOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

    ZygoteConnection(LocalSocket localSocket, String str) throws IOException {
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

    /* JADX WARN: Code restructure failed: missing block: B:112:0x01fe, code lost:
    
        throw new com.android.internal.os.ZygoteSecurityException("Client may not specify capabilities: permitted=0x" + java.lang.Long.toHexString(r3.mPermittedCapabilities) + ", effective=0x" + java.lang.Long.toHexString(r3.mEffectiveCapabilities));
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0206, code lost:
    
        if (r3.mUsapPoolStatusSpecified == false) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x020e, code lost:
    
        return handleUsapPoolStatusChange(r28, r3.mUsapPoolEnabled);
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0211, code lost:
    
        if (r3.mApiDenylistExemptions == null) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x0219, code lost:
    
        return handleApiDenylistExemptions(r28, r3.mApiDenylistExemptions);
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x021c, code lost:
    
        if (r3.mHiddenApiAccessLogSampleRate != (-1)) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x0220, code lost:
    
        if (r3.mHiddenApiAccessStatslogSampleRate == (-1)) goto L127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x022a, code lost:
    
        throw new java.lang.AssertionError("Shouldn't get here");
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x0233, code lost:
    
        return handleHiddenApiAccessLogSampleRate(r28, r3.mHiddenApiAccessLogSampleRate, r3.mHiddenApiAccessStatslogSampleRate);
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x016d, code lost:
    
        r5 = r9;
        r2 = com.android.internal.os.Zygote.forkAndSpecialize(r3.mUid, r3.mGid, r3.mGids, r3.mRuntimeFlags, r10, r3.mMountExternal, r3.mSeInfo, r3.mNiceName, r14, r15, r3.mStartChildZygote, r3.mInstructionSet, r3.mAppDataDir, r3.mIsTopApp, r3.mPkgDataInfoList, r3.mAllowlistedDataInfoList, r3.mBindMountAppDataDirs, r3.mBindMountAppStorageDirs, r3.mBindMountSyspropOverrides);
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0197, code lost:
    
        if (r2 != 0) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0199, code lost:
    
        r28.setForkChild();
        r28.closeServerSocket();
        libcore.io.IoUtils.closeQuietly(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x01a2, code lost:
    
        r0 = handleChildProc(r3, r5, r3.mStartChildZygote);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x01a8, code lost:
    
        libcore.io.IoUtils.closeQuietly(r5);
        libcore.io.IoUtils.closeQuietly((java.io.FileDescriptor) null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x01ae, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x01b1, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01b2, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01b3, code lost:
    
        r4 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x01cb, code lost:
    
        libcore.io.IoUtils.closeQuietly(r5);
        libcore.io.IoUtils.closeQuietly(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01d1, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01b6, code lost:
    
        libcore.io.IoUtils.closeQuietly(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01b9, code lost:
    
        handleParentProc(r2, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01bc, code lost:
    
        libcore.io.IoUtils.closeQuietly((java.io.FileDescriptor) null);
        libcore.io.IoUtils.closeQuietly(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01c2, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01c5, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01c6, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01c7, code lost:
    
        r5 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x01ca, code lost:
    
        r0 = th;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    java.lang.Runnable processCommand(com.android.internal.os.ZygoteServer r28, boolean r29) {
        /*
            Method dump skipped, instructions count: 591
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.ZygoteConnection.processCommand(com.android.internal.os.ZygoteServer, boolean):java.lang.Runnable");
    }

    private void handleAbiListQuery() {
        try {
            byte[] bytes = this.abiList.getBytes(StandardCharsets.US_ASCII);
            this.mSocketOutStream.writeInt(bytes.length);
            this.mSocketOutStream.write(bytes);
        } catch (IOException e) {
            throw new IllegalStateException("Error writing to command socket", e);
        }
    }

    private void handlePidQuery() {
        try {
            byte[] bytes = String.valueOf(Process.myPid()).getBytes(StandardCharsets.US_ASCII);
            this.mSocketOutStream.writeInt(bytes.length);
            this.mSocketOutStream.write(bytes);
        } catch (IOException e) {
            throw new IllegalStateException("Error writing to command socket", e);
        }
    }

    private void handleBootCompleted() {
        try {
            this.mSocketOutStream.writeInt(0);
            VMRuntime.bootCompleted();
        } catch (IOException e) {
            throw new IllegalStateException("Error writing to command socket", e);
        }
    }

    private void handlePreload() {
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

    private Runnable stateChangeWithUsapPoolReset(ZygoteServer zygoteServer, Runnable runnable) {
        try {
            if (zygoteServer.isUsapPoolEnabled()) {
                Log.i(TAG, "Emptying USAP Pool due to state change.");
                Zygote.emptyUsapPool();
            }
            runnable.run();
            if (zygoteServer.isUsapPoolEnabled()) {
                Runnable fillUsapPool = zygoteServer.fillUsapPool(new int[]{this.mSocket.getFileDescriptor().getInt$()}, false);
                if (fillUsapPool != null) {
                    zygoteServer.setForkChild();
                    return fillUsapPool;
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

    private Runnable handleUsapPoolStatusChange(ZygoteServer zygoteServer, boolean z) {
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
    /* JADX WARN: Removed duplicated region for block: B:36:0x00af  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void handleParentProc(int r20, java.io.FileDescriptor r21) {
        /*
            Method dump skipped, instructions count: 257
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.ZygoteConnection.handleParentProc(int, java.io.FileDescriptor):void");
    }

    private void setChildPgid(int i) {
        try {
            Os.setpgid(i, Os.getpgid(this.peer.getPid()));
        } catch (ErrnoException unused) {
            Log.i(TAG, "Zygote: setpgid failed. This is normal if peer is not in our session");
        }
    }
}
