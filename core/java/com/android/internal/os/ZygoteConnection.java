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

  ZygoteConnection(LocalSocket socket, String abiList) throws IOException {
    this.mSocket = socket;
    this.abiList = abiList;
    this.mSocketOutStream = new DataOutputStream(socket.getOutputStream());
    socket.setSoTimeout(1000);
    try {
      Credentials peerCredentials = socket.getPeerCredentials();
      this.peer = peerCredentials;
      if (peerCredentials.getUid() != 1000) {
        throw new ZygoteSecurityException("Only system UID is allowed to connect to Zygote.");
      }
      this.isEof = false;
    } catch (IOException ex) {
      Log.m97e(TAG, "Cannot read peer credentials", ex);
      throw ex;
    }
  }

  FileDescriptor getFileDescriptor() {
    return this.mSocket.getFileDescriptor();
  }

  /* JADX WARN: Code restructure failed: missing block: B:100:0x01e7, code lost:

     r0 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:101:0x01e8, code lost:

     r6 = r33;
  */
  /* JADX WARN: Code restructure failed: missing block: B:103:0x01ed, code lost:

     r0 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:104:0x01ee, code lost:

     r6 = r33;
     r7 = r34;
  */
  /* JADX WARN: Code restructure failed: missing block: B:105:0x020e, code lost:

     r0 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:107:0x021b, code lost:

     r0 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:119:0x021f, code lost:

     r0 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:136:0x0253, code lost:

     throw new com.android.internal.os.ZygoteSecurityException("Client may not specify capabilities: permitted=0x" + java.lang.Long.toHexString(r0.mPermittedCapabilities) + ", effective=0x" + java.lang.Long.toHexString(r0.mEffectiveCapabilities));
  */
  /* JADX WARN: Code restructure failed: missing block: B:160:0x0263, code lost:

     r32.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:161:0x0268, code lost:

     if (r0.mUsapPoolStatusSpecified == false) goto L143;
  */
  /* JADX WARN: Code restructure failed: missing block: B:163:0x0272, code lost:

     return r2.handleUsapPoolStatusChange(r37, r0.mUsapPoolEnabled);
  */
  /* JADX WARN: Code restructure failed: missing block: B:165:0x0277, code lost:

     if (r0.mApiDenylistExemptions == null) goto L147;
  */
  /* JADX WARN: Code restructure failed: missing block: B:167:0x027f, code lost:

     return r2.handleApiDenylistExemptions(r37, r0.mApiDenylistExemptions);
  */
  /* JADX WARN: Code restructure failed: missing block: B:169:0x0282, code lost:

     if (r0.mHiddenApiAccessLogSampleRate != (-1)) goto L154;
  */
  /* JADX WARN: Code restructure failed: missing block: B:171:0x0286, code lost:

     if (r0.mHiddenApiAccessStatslogSampleRate == (-1)) goto L152;
  */
  /* JADX WARN: Code restructure failed: missing block: B:173:0x0290, code lost:

     throw new java.lang.AssertionError("Shouldn't get here");
  */
  /* JADX WARN: Code restructure failed: missing block: B:175:0x0299, code lost:

     return r2.handleHiddenApiAccessLogSampleRate(r37, r0.mHiddenApiAccessLogSampleRate, r0.mHiddenApiAccessStatslogSampleRate);
  */
  /* JADX WARN: Code restructure failed: missing block: B:177:0x0260, code lost:

     r2 = r36;
     r32 = r0;
  */
  /* JADX WARN: Code restructure failed: missing block: B:68:0x0195, code lost:

     r32 = r0;
  */
  /* JADX WARN: Code restructure failed: missing block: B:70:0x0197, code lost:

     r3 = r0.mAppDataDir;
  */
  /* JADX WARN: Code restructure failed: missing block: B:72:0x0199, code lost:

     r1 = r0.mIsTopApp;
     r33 = r6;
     r6 = r0.mPkgDataInfoList;
     r34 = r7;
     r7 = r0.mAllowlistedDataInfoList;
     r7 = r0.mBindMountAppDataDirs;
     r7 = r0.mBindMountAppStorageDirs;
     r0 = com.android.internal.os.Zygote.forkAndSpecialize(r11, r12, r13, r14, r8, r0, r15, r5, r9, r29, r10, r2, r3, r1, r6, r7, r7, r7);
  */
  /* JADX WARN: Code restructure failed: missing block: B:75:0x01c7, code lost:

     if (r0 != 0) goto L115;
  */
  /* JADX WARN: Code restructure failed: missing block: B:76:0x01f5, code lost:

     r6 = r33;
  */
  /* JADX WARN: Code restructure failed: missing block: B:78:0x01f9, code lost:

     libcore.io.IoUtils.closeQuietly((java.io.FileDescriptor) r6);
  */
  /* JADX WARN: Code restructure failed: missing block: B:80:0x01ff, code lost:

     handleParentProc(r0, r34);
  */
  /* JADX WARN: Code restructure failed: missing block: B:81:0x0203, code lost:

     libcore.io.IoUtils.closeQuietly((java.io.FileDescriptor) null);
     libcore.io.IoUtils.closeQuietly(r34);
  */
  /* JADX WARN: Code restructure failed: missing block: B:82:0x0209, code lost:

     r32.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:83:0x020d, code lost:

     return null;
  */
  /* JADX WARN: Code restructure failed: missing block: B:85:0x0210, code lost:

     r0 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:86:0x0211, code lost:

     r7 = r34;
  */
  /* JADX WARN: Code restructure failed: missing block: B:87:0x0213, code lost:

     libcore.io.IoUtils.closeQuietly(r6);
     libcore.io.IoUtils.closeQuietly(r7);
  */
  /* JADX WARN: Code restructure failed: missing block: B:88:0x021a, code lost:

     throw r0;
  */
  /* JADX WARN: Code restructure failed: missing block: B:90:0x01c9, code lost:

     r37.setForkChild();
     r37.closeServerSocket();
     libcore.io.IoUtils.closeQuietly(r34);
  */
  /* JADX WARN: Code restructure failed: missing block: B:91:0x01d2, code lost:

     r7 = 0;
  */
  /* JADX WARN: Code restructure failed: missing block: B:95:0x01d9, code lost:

     r0 = handleChildProc(r0, r33, r0.mStartChildZygote);
  */
  /* JADX WARN: Code restructure failed: missing block: B:96:0x01dd, code lost:

     libcore.io.IoUtils.closeQuietly(r33);
     libcore.io.IoUtils.closeQuietly((java.io.FileDescriptor) null);
  */
  /* JADX WARN: Code restructure failed: missing block: B:97:0x01e3, code lost:

     r32.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:98:0x01e6, code lost:

     return r0;
  */
  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Type inference failed for: r6v3, types: [java.lang.String[]] */
  /* JADX WARN: Type inference failed for: r6v5 */
  /* JADX WARN: Type inference failed for: r6v6 */
  /* JADX WARN: Type inference failed for: r6v8, types: [java.io.FileDescriptor] */
  /* JADX WARN: Type inference failed for: r7v5, types: [boolean] */
  /* JADX WARN: Type inference failed for: r7v7 */
  /* JADX WARN: Type inference failed for: r7v8 */
  /* JADX WARN: Type inference failed for: r7v9 */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  Runnable processCommand(ZygoteServer zygoteServer, boolean multipleOK) {
    ZygoteCommandBuffer argBuffer;
    IOException iOException;
    int[] fdsToIgnore;
    ZygoteCommandBuffer argBuffer2 = new ZygoteCommandBuffer(this.mSocket);
    while (true) {
      try {
        try {
          ZygoteArguments parsedArgs = ZygoteArguments.getInstance(argBuffer2);
          if (parsedArgs != null) {
            FileDescriptor childPipeFd = null;
            FileDescriptor serverPipeFd = null;
            if (!parsedArgs.mBootCompleted) {
              if (!parsedArgs.mAbiListQuery) {
                if (!parsedArgs.mPidQuery) {
                  if (parsedArgs.mUsapPoolStatusSpecified) {
                    break;
                  }
                  try {
                    if (parsedArgs.mApiDenylistExemptions != null
                        || parsedArgs.mHiddenApiAccessLogSampleRate != -1) {
                      break;
                    }
                    if (parsedArgs.mHiddenApiAccessStatslogSampleRate != -1) {
                      ZygoteConnection zygoteConnection = this;
                      ZygoteCommandBuffer argBuffer3 = argBuffer2;
                      break;
                    }
                    if (parsedArgs.mPreloadDefault) {
                      handlePreload();
                      argBuffer2.close();
                      return null;
                    }
                    if (parsedArgs.mPreloadPackage != null) {
                      handlePreloadPackage(
                          parsedArgs.mPreloadPackage,
                          parsedArgs.mPreloadPackageLibs,
                          parsedArgs.mPreloadPackageLibFileName,
                          parsedArgs.mPreloadPackageCacheKey);
                      argBuffer2.close();
                      return null;
                    }
                    if (canPreloadApp()) {
                      try {
                        if (parsedArgs.mPreloadApp != null) {
                          byte[] rawParcelData = Base64.getDecoder().decode(parsedArgs.mPreloadApp);
                          Parcel appInfoParcel = Parcel.obtain();
                          appInfoParcel.unmarshall(rawParcelData, 0, rawParcelData.length);
                          appInfoParcel.setDataPosition(0);
                          ApplicationInfo appInfo =
                              ApplicationInfo.CREATOR.createFromParcel(appInfoParcel);
                          appInfoParcel.recycle();
                          if (appInfo == null) {
                            throw new IllegalArgumentException(
                                "Failed to deserialize --preload-app");
                          }
                          handlePreloadApp(appInfo);
                          argBuffer2.close();
                          return null;
                        }
                      } catch (Throwable th) {
                        argBuffer = argBuffer2;
                        iOException = th;
                        try {
                          argBuffer.close();
                          throw iOException;
                        } catch (Throwable th2) {
                          iOException.addSuppressed(th2);
                          throw iOException;
                        }
                      }
                    }
                    try {
                      if (parsedArgs.mPermittedCapabilities != 0
                          || parsedArgs.mEffectiveCapabilities != 0) {
                        break;
                      }
                      Zygote.applyUidSecurityPolicy(parsedArgs, this.peer);
                      Zygote.applyInvokeWithSecurityPolicy(parsedArgs, this.peer);
                      Zygote.applyDebuggerSystemProperty(parsedArgs);
                      Zygote.applyInvokeWithSystemProperty(parsedArgs);
                      int[][] rlimits =
                          parsedArgs.mRLimits != null
                              ? (int[][]) parsedArgs.mRLimits.toArray(Zygote.INT_ARRAY_2D)
                              : null;
                      if (parsedArgs.mInvokeWith != null) {
                        try {
                          FileDescriptor[] pipeFds = Os.pipe2(OsConstants.O_CLOEXEC);
                          childPipeFd = pipeFds[1];
                          serverPipeFd = pipeFds[0];
                          Os.fcntlInt(childPipeFd, OsConstants.F_SETFD, 0);
                          int[] fdsToIgnore2 = {childPipeFd.getInt$(), serverPipeFd.getInt$()};
                          fdsToIgnore = fdsToIgnore2;
                        } catch (ErrnoException errnoEx) {
                          throw new IllegalStateException(
                              "Unable to set up pipe for invoke-with", errnoEx);
                        }
                      } else {
                        fdsToIgnore = null;
                      }
                      int[] fdsToClose = {-1, -1};
                      FileDescriptor fd = this.mSocket.getFileDescriptor();
                      if (fd != null) {
                        fdsToClose[0] = fd.getInt$();
                      }
                      FileDescriptor zygoteFd = zygoteServer.getZygoteSocketFileDescriptor();
                      if (zygoteFd != null) {
                        fdsToClose[1] = zygoteFd.getInt$();
                      }
                      if (parsedArgs.mInvokeWith != null
                          || parsedArgs.mStartChildZygote
                          || !multipleOK
                          || this.peer.getUid() != 1000) {
                        break;
                      }
                      ZygoteHooks.preFork();
                      Runnable result =
                          Zygote.forkSimpleApps(
                              argBuffer2,
                              zygoteServer.getZygoteSocketFileDescriptor(),
                              this.peer.getUid(),
                              Zygote.minChildUid(this.peer),
                              parsedArgs.mNiceName);
                      if (result != null) {
                        zygoteServer.setForkChild();
                        argBuffer2.close();
                        return result;
                      }
                      ZygoteHooks.postForkCommon();
                    } catch (Throwable th3) {
                      ex = th3;
                    }
                  } catch (Throwable th4) {
                    th = th4;
                    argBuffer = argBuffer2;
                    iOException = th;
                    argBuffer.close();
                    throw iOException;
                  }
                } else {
                  handlePidQuery();
                  argBuffer2.close();
                  return null;
                }
              } else {
                handleAbiListQuery();
                argBuffer2.close();
                return null;
              }
            } else {
              handleBootCompleted();
              argBuffer2.close();
              return null;
            }
          } else {
            this.isEof = true;
            argBuffer2.close();
            return null;
          }
        } catch (IOException ex) {
          argBuffer = argBuffer2;
          try {
            throw new IllegalStateException("IOException on command socket", ex);
          } catch (Throwable th5) {
            ex = th5;
            iOException = ex;
            argBuffer.close();
            throw iOException;
          }
        }
      } catch (Throwable th6) {
        th = th6;
        argBuffer = argBuffer2;
      }
    }
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
        Log.m98i(TAG, "Emptying USAP Pool due to state change.");
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
        Log.m98i(TAG, "Finished refilling USAP Pool after state change.");
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
                         // com.android.internal.os.ZygoteConnection$$ExternalSyntheticLambda0
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
                         // com.android.internal.os.ZygoteConnection$$ExternalSyntheticLambda1
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
    StatsdHiddenApiUsageLogger.setHiddenApiAccessLogSampleRates(samplingRate, statsdSamplingRate);
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
      Log.m97e(TAG, "Exception while closing command socket in parent", ex);
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
  */
  private void handleParentProc(int pid, FileDescriptor pipeFd) {
    boolean usingWrapper;
    boolean usingWrapper2;
    int innerPid;
    char c;
    boolean z;
    int pid2 = pid;
    if (pid2 > 0) {
      setChildPgid(pid);
    }
    boolean usingWrapper3 = false;
    try {
      if (pipeFd == null || pid2 <= 0) {
        usingWrapper = false;
      } else {
        int innerPid2 = -1;
        int BYTES_REQUIRED = 4;
        try {
          char c2 = 0;
          StructPollfd[] fds = {new StructPollfd()};
          byte[] data = new byte[4];
          int remainingSleepTime = 20000;
          int dataIndex = 0;
          long startTime = System.nanoTime();
          while (dataIndex < data.length && remainingSleepTime > 0) {
            fds[c2].fd = pipeFd;
            fds[c2].events = (short) OsConstants.POLLIN;
            fds[0].revents = (short) 0;
            fds[0].userData = null;
            int res = Os.poll(fds, remainingSleepTime);
            long endTime = System.nanoTime();
            usingWrapper = usingWrapper3;
            innerPid = innerPid2;
            int BYTES_REQUIRED2 = BYTES_REQUIRED;
            try {
              int elapsedTimeMs =
                  (int) TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);
              remainingSleepTime = 20000 - elapsedTimeMs;
              if (res > 0) {
                c = 0;
                if ((fds[0].revents & OsConstants.POLLIN) == 0) {
                  break;
                }
                z = true;
                int readBytes = Os.read(pipeFd, data, dataIndex, 1);
                if (readBytes < 0) {
                  throw new RuntimeException("Some error");
                }
                dataIndex += readBytes;
              } else {
                c = 0;
                z = true;
                if (res == 0) {
                  Log.m102w(TAG, "Timed out waiting for child.");
                }
              }
              c2 = c;
              usingWrapper3 = usingWrapper;
              innerPid2 = innerPid;
              BYTES_REQUIRED = BYTES_REQUIRED2;
            } catch (Exception e) {
              ex = e;
              innerPid2 = innerPid;
              Log.m103w(TAG, "Error reading pid from wrapped process, child may have died", ex);
              if (innerPid2 > 0) {}
              usingWrapper2 = usingWrapper;
              this.mSocketOutStream.writeInt(pid2);
              this.mSocketOutStream.writeBoolean(usingWrapper2);
              return;
            }
          }
          usingWrapper = usingWrapper3;
          innerPid = innerPid2;
          int BYTES_REQUIRED3 = data.length;
          if (dataIndex != BYTES_REQUIRED3) {
            innerPid2 = innerPid;
          } else {
            DataInputStream is = new DataInputStream(new ByteArrayInputStream(data));
            innerPid2 = is.readInt();
          }
          if (innerPid2 == -1) {
            try {
              Log.m102w(TAG, "Error reading pid from wrapped process, child may have died");
            } catch (Exception e2) {
              ex = e2;
              Log.m103w(TAG, "Error reading pid from wrapped process, child may have died", ex);
              if (innerPid2 > 0) {}
              usingWrapper2 = usingWrapper;
              this.mSocketOutStream.writeInt(pid2);
              this.mSocketOutStream.writeBoolean(usingWrapper2);
              return;
            }
          }
        } catch (Exception e3) {
          ex = e3;
          usingWrapper = usingWrapper3;
        }
        if (innerPid2 > 0) {
          int parentPid = innerPid2;
          while (parentPid > 0 && parentPid != pid2) {
            parentPid = Process.getParentPid(parentPid);
          }
          if (parentPid > 0) {
            Log.m98i(TAG, "Wrapped process has pid " + innerPid2);
            pid2 = innerPid2;
            usingWrapper2 = true;
            this.mSocketOutStream.writeInt(pid2);
            this.mSocketOutStream.writeBoolean(usingWrapper2);
            return;
          }
          Log.m102w(
              TAG,
              "Wrapped process reported a pid that is not a child of the process that we forked:"
                  + " childPid="
                  + pid2
                  + " innerPid="
                  + innerPid2);
        }
      }
      this.mSocketOutStream.writeInt(pid2);
      this.mSocketOutStream.writeBoolean(usingWrapper2);
      return;
    } catch (IOException ex) {
      throw new IllegalStateException("Error writing to command socket", ex);
    }
    usingWrapper2 = usingWrapper;
  }

  private void setChildPgid(int pid) {
    try {
      Os.setpgid(pid, Os.getpgid(this.peer.getPid()));
    } catch (ErrnoException e) {
      Log.m98i(TAG, "Zygote: setpgid failed. This is normal if peer is not in our session");
    }
  }
}
