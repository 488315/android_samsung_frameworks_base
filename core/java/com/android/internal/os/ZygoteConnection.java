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

class ZygoteConnection {
    private static final String TAG = "Zygote";
    private final String abiList;
    private boolean isEof;
    private final LocalSocket mSocket;
    private final DataOutputStream mSocketOutStream;
    private final Credentials peer;

    ZygoteConnection(LocalSocket socket, String abiList) throws IOException {
        this.mSocket = socket;
        this.abiList = abiList;
        this.mSocketOutStream = new DataOutputStream(socket.getOutputStream());
        this.mSocket.setSoTimeout(1000);
        try {
            this.peer = this.mSocket.getPeerCredentials();
            if (this.peer.getUid() != 1000) {
                throw new ZygoteSecurityException(
                        "Only system UID is allowed to connect to Zygote.");
            }
            this.isEof = false;
        } catch (IOException ex) {
            Log.e(TAG, "Cannot read peer credentials", ex);
            throw ex;
        }
    }

    FileDescriptor getFileDescriptor() {
        return this.mSocket.getFileDescriptor();
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x01e8, code lost:

       r0 = th;
    */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x01e9, code lost:

       r6 = r34;
    */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x01ee, code lost:

       r0 = th;
    */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x01ef, code lost:

       r6 = r34;
       r7 = r35;
    */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x020f, code lost:

       r0 = th;
    */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x021c, code lost:

       r0 = th;
    */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x0220, code lost:

       r0 = th;
    */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0254, code lost:

       throw new com.android.internal.os.ZygoteSecurityException("Client may not specify capabilities: permitted=0x" + java.lang.Long.toHexString(r0.mPermittedCapabilities) + ", effective=0x" + java.lang.Long.toHexString(r0.mEffectiveCapabilities));
    */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x0264, code lost:

       r33.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0269, code lost:

       if (r0.mUsapPoolStatusSpecified == false) goto L143;
    */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x0273, code lost:

       return r2.handleUsapPoolStatusChange(r38, r0.mUsapPoolEnabled);
    */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x0278, code lost:

       if (r0.mApiDenylistExemptions == null) goto L147;
    */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x0280, code lost:

       return r2.handleApiDenylistExemptions(r38, r0.mApiDenylistExemptions);
    */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0283, code lost:

       if (r0.mHiddenApiAccessLogSampleRate != (-1)) goto L154;
    */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x0287, code lost:

       if (r0.mHiddenApiAccessStatslogSampleRate == (-1)) goto L152;
    */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x0291, code lost:

       throw new java.lang.AssertionError("Shouldn't get here");
    */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x029a, code lost:

       return r2.handleHiddenApiAccessLogSampleRate(r38, r0.mHiddenApiAccessLogSampleRate, r0.mHiddenApiAccessStatslogSampleRate);
    */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x0261, code lost:

       r2 = r37;
       r33 = r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0192, code lost:

       r33 = r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0194, code lost:

       r3 = r0.mAppDataDir;
    */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0196, code lost:

       r1 = r0.mIsTopApp;
       r34 = r6;
       r6 = r0.mPkgDataInfoList;
       r35 = r7;
       r7 = r0.mAllowlistedDataInfoList;
       r7 = r0.mBindMountAppDataDirs;
       r7 = r0.mBindMountAppStorageDirs;
       r7 = r0.mBindMountSyspropOverrides;
       r0 = com.android.internal.os.Zygote.forkAndSpecialize(r11, r12, r13, r14, r8, r0, r15, r5, r9, r30, r10, r2, r3, r1, r6, r7, r7, r7, r7);
    */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x01c8, code lost:

       if (r0 != 0) goto L115;
    */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01f6, code lost:

       r6 = r34;
    */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01fa, code lost:

       libcore.io.IoUtils.closeQuietly((java.io.FileDescriptor) r6);
    */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0200, code lost:

       handleParentProc(r0, r35);
    */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0204, code lost:

       libcore.io.IoUtils.closeQuietly((java.io.FileDescriptor) null);
       libcore.io.IoUtils.closeQuietly(r35);
    */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x020a, code lost:

       r33.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x020e, code lost:

       return null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0211, code lost:

       r0 = th;
    */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0212, code lost:

       r7 = r35;
    */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0214, code lost:

       libcore.io.IoUtils.closeQuietly(r6);
       libcore.io.IoUtils.closeQuietly(r7);
    */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x021b, code lost:

       throw r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01ca, code lost:

       r38.setForkChild();
       r38.closeServerSocket();
       libcore.io.IoUtils.closeQuietly(r35);
    */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x01d3, code lost:

       r7 = 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x01da, code lost:

       r0 = handleChildProc(r0, r34, r0.mStartChildZygote);
    */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x01de, code lost:

       libcore.io.IoUtils.closeQuietly(r34);
       libcore.io.IoUtils.closeQuietly((java.io.FileDescriptor) null);
    */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x01e4, code lost:

       r33.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x01e7, code lost:

       return r0;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.lang.String[]] */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r6v8, types: [java.io.FileDescriptor] */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v6, types: [boolean] */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    java.lang.Runnable processCommand(com.android.internal.os.ZygoteServer r38, boolean r39) {
        /*
            Method dump skipped, instructions count: 707
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.os.ZygoteConnection.processCommand(com.android.internal.os.ZygoteServer,"
                    + " boolean):java.lang.Runnable");
    }

    private void handleAbiListQuery() {
        try {
            byte[] abiListBytes = this.abiList.getBytes(StandardCharsets.US_ASCII);
            this.mSocketOutStream.writeInt(abiListBytes.length);
            this.mSocketOutStream.write(abiListBytes);
        } catch (IOException ioe) {
            throw new IllegalStateException("Error writing to command socket", ioe);
        }
    }

    private void handlePidQuery() {
        try {
            String pidString = String.valueOf(Process.myPid());
            byte[] pidStringBytes = pidString.getBytes(StandardCharsets.US_ASCII);
            this.mSocketOutStream.writeInt(pidStringBytes.length);
            this.mSocketOutStream.write(pidStringBytes);
        } catch (IOException ioe) {
            throw new IllegalStateException("Error writing to command socket", ioe);
        }
    }

    private void handleBootCompleted() {
        try {
            this.mSocketOutStream.writeInt(0);
            VMRuntime.bootCompleted();
        } catch (IOException ioe) {
            throw new IllegalStateException("Error writing to command socket", ioe);
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
        } catch (IOException ioe) {
            throw new IllegalStateException("Error writing to command socket", ioe);
        }
    }

    private Runnable stateChangeWithUsapPoolReset(
            ZygoteServer zygoteServer, Runnable stateChangeCode) {
        try {
            if (zygoteServer.isUsapPoolEnabled()) {
                Log.i(TAG, "Emptying USAP Pool due to state change.");
                Zygote.emptyUsapPool();
            }
            stateChangeCode.run();
            if (zygoteServer.isUsapPoolEnabled()) {
                Runnable fpResult =
                        zygoteServer.fillUsapPool(
                                new int[] {this.mSocket.getFileDescriptor().getInt$()}, false);
                if (fpResult != null) {
                    zygoteServer.setForkChild();
                    return fpResult;
                }
                Log.i(TAG, "Finished refilling USAP Pool after state change.");
            }
            this.mSocketOutStream.writeInt(0);
            return null;
        } catch (IOException ioe) {
            throw new IllegalStateException("Error writing to command socket", ioe);
        }
    }

    private Runnable handleApiDenylistExemptions(
            ZygoteServer zygoteServer, final String[] exemptions) {
        return stateChangeWithUsapPoolReset(
                zygoteServer,
                new Runnable() { // from class:
                                 // com.android.internal.os.ZygoteConnection$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ZygoteInit.setApiDenylistExemptions(exemptions);
                    }
                });
    }

    private Runnable handleUsapPoolStatusChange(ZygoteServer zygoteServer, boolean newStatus) {
        try {
            Runnable fpResult = zygoteServer.setUsapPoolStatus(newStatus, this.mSocket);
            if (fpResult == null) {
                this.mSocketOutStream.writeInt(0);
            } else {
                zygoteServer.setForkChild();
            }
            return fpResult;
        } catch (IOException ioe) {
            throw new IllegalStateException("Error writing to command socket", ioe);
        }
    }

    private Runnable handleHiddenApiAccessLogSampleRate(
            ZygoteServer zygoteServer, final int samplingRate, final int statsdSamplingRate) {
        return stateChangeWithUsapPoolReset(
                zygoteServer,
                new Runnable() { // from class:
                                 // com.android.internal.os.ZygoteConnection$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ZygoteConnection.lambda$handleHiddenApiAccessLogSampleRate$1(
                                samplingRate, statsdSamplingRate);
                    }
                });
    }

    static /* synthetic */ void lambda$handleHiddenApiAccessLogSampleRate$1(
            int samplingRate, int statsdSamplingRate) {
        int maxSamplingRate = Math.max(samplingRate, statsdSamplingRate);
        ZygoteInit.setHiddenApiAccessLogSampleRate(maxSamplingRate);
        StatsdHiddenApiUsageLogger.setHiddenApiAccessLogSampleRates(
                samplingRate, statsdSamplingRate);
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

    protected void handlePreloadPackage(
            String packagePath, String libsPath, String libFileName, String cacheKey) {
        throw new RuntimeException("Zygote does not support package preloading");
    }

    protected boolean canPreloadApp() {
        return false;
    }

    protected void handlePreloadApp(ApplicationInfo aInfo) {
        throw new RuntimeException("Zygote does not support app preloading");
    }

    void closeSocket() {
        try {
            this.mSocket.close();
        } catch (IOException ex) {
            Log.e(TAG, "Exception while closing command socket in parent", ex);
        }
    }

    boolean isClosedByPeer() {
        return this.isEof;
    }

    private Runnable handleChildProc(
            ZygoteArguments parsedArgs, FileDescriptor pipeFd, boolean isZygote) {
        closeSocket();
        Zygote.setAppProcessName(parsedArgs, TAG);
        Trace.traceEnd(64L);
        if (parsedArgs.mInvokeWith != null) {
            WrapperInit.execApplication(
                    parsedArgs.mInvokeWith,
                    parsedArgs.mNiceName,
                    parsedArgs.mTargetSdkVersion,
                    VMRuntime.getCurrentInstructionSet(),
                    pipeFd,
                    parsedArgs.mRemainingArgs);
            throw new IllegalStateException("WrapperInit.execApplication unexpectedly returned");
        }
        if (!isZygote) {
            return ZygoteInit.zygoteInit(
                    parsedArgs.mTargetSdkVersion,
                    parsedArgs.mDisabledCompatChanges,
                    parsedArgs.mRemainingArgs,
                    null);
        }
        return ZygoteInit.childZygoteInit(parsedArgs.mRemainingArgs);
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void handleParentProc(int r22, java.io.FileDescriptor r23) {
        /*
            Method dump skipped, instructions count: 289
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.internal.os.ZygoteConnection.handleParentProc(int,"
                    + " java.io.FileDescriptor):void");
    }

    private void setChildPgid(int pid) {
        try {
            Os.setpgid(pid, Os.getpgid(this.peer.getPid()));
        } catch (ErrnoException e) {
            Log.i(TAG, "Zygote: setpgid failed. This is normal if peer is not in our session");
        }
    }
}
