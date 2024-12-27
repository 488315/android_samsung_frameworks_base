package com.android.internal.os;

import java.io.EOFException;
import java.util.ArrayList;

/* loaded from: classes5.dex */
class ZygoteArguments {
    boolean mAbiListQuery;
    String[] mAllowlistedDataInfoList;
    String[] mApiDenylistExemptions;
    String mAppDataDir;
    boolean mBindMountAppDataDirs;
    boolean mBindMountAppStorageDirs;
    boolean mBindMountSyspropOverrides;
    boolean mBootCompleted;
    private boolean mCapabilitiesSpecified;
    long mEffectiveCapabilities;
    boolean mGidSpecified;
    int[] mGids;
    String mInstructionSet;
    String mInvokeWith;
    boolean mIsTopApp;
    String mNiceName;
    String mPackageName;
    long mPermittedCapabilities;
    boolean mPidQuery;
    String[] mPkgDataInfoList;
    String mPreloadApp;
    boolean mPreloadDefault;
    String mPreloadPackage;
    String mPreloadPackageCacheKey;
    String mPreloadPackageLibFileName;
    String mPreloadPackageLibs;
    ArrayList<int[]> mRLimits;
    String[] mRemainingArgs;
    int mRuntimeFlags;
    String mSeInfo;
    private boolean mSeInfoSpecified;
    boolean mStartChildZygote;
    int mTargetSdkVersion;
    private boolean mTargetSdkVersionSpecified;
    boolean mUidSpecified;
    boolean mUsapPoolEnabled;
    int mUid = 0;
    int mGid = 0;
    int mMountExternal = 0;
    boolean mUsapPoolStatusSpecified = false;
    int mHiddenApiAccessLogSampleRate = -1;
    int mHiddenApiAccessStatslogSampleRate = -1;
    long[] mDisabledCompatChanges = null;

    private ZygoteArguments(ZygoteCommandBuffer args, int argCount) throws IllegalArgumentException, EOFException {
        parseArgs(args, argCount);
    }

    public static ZygoteArguments getInstance(ZygoteCommandBuffer args) throws IllegalArgumentException, EOFException {
        int argCount = args.getCount();
        if (argCount == 0) {
            return null;
        }
        return new ZygoteArguments(args, argCount);
    }

    /* JADX WARN: Code restructure failed: missing block: B:250:0x03a3, code lost:
    
        if (r12.mBootCompleted == false) goto L203;
     */
    /* JADX WARN: Code restructure failed: missing block: B:251:0x03a5, code lost:
    
        if (r14 > r1) goto L201;
     */
    /* JADX WARN: Code restructure failed: missing block: B:253:0x03b0, code lost:
    
        throw new java.lang.IllegalArgumentException("Unexpected arguments after --boot-completed");
     */
    /* JADX WARN: Code restructure failed: missing block: B:255:0x041f, code lost:
    
        if (r12.mStartChildZygote == false) goto L247;
     */
    /* JADX WARN: Code restructure failed: missing block: B:256:0x0421, code lost:
    
        r5 = false;
        r6 = r12.mRemainingArgs;
        r7 = r6.length;
     */
    /* JADX WARN: Code restructure failed: missing block: B:257:0x0425, code lost:
    
        if (r4 >= r7) goto L309;
     */
    /* JADX WARN: Code restructure failed: missing block: B:259:0x042f, code lost:
    
        if (r6[r4].startsWith(com.android.internal.os.Zygote.CHILD_ZYGOTE_SOCKET_NAME_ARG) == false) goto L242;
     */
    /* JADX WARN: Code restructure failed: missing block: B:260:0x0433, code lost:
    
        r4 = r4 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:262:0x0431, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:263:0x0436, code lost:
    
        if (r5 == false) goto L245;
     */
    /* JADX WARN: Code restructure failed: missing block: B:265:0x0440, code lost:
    
        throw new java.lang.IllegalArgumentException("--start-child-zygote specified without --zygote-socket=");
     */
    /* JADX WARN: Code restructure failed: missing block: B:266:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:268:0x0441, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:270:0x03b3, code lost:
    
        if (r12.mAbiListQuery != false) goto L234;
     */
    /* JADX WARN: Code restructure failed: missing block: B:272:0x03b7, code lost:
    
        if (r12.mPidQuery == false) goto L208;
     */
    /* JADX WARN: Code restructure failed: missing block: B:274:0x03bc, code lost:
    
        if (r12.mPreloadPackage == null) goto L214;
     */
    /* JADX WARN: Code restructure failed: missing block: B:275:0x03be, code lost:
    
        if (r14 > r1) goto L212;
     */
    /* JADX WARN: Code restructure failed: missing block: B:277:0x03c8, code lost:
    
        throw new java.lang.IllegalArgumentException("Unexpected arguments after --preload-package.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:279:0x03cb, code lost:
    
        if (r12.mPreloadApp == null) goto L220;
     */
    /* JADX WARN: Code restructure failed: missing block: B:280:0x03cd, code lost:
    
        if (r14 > r1) goto L218;
     */
    /* JADX WARN: Code restructure failed: missing block: B:282:0x03d7, code lost:
    
        throw new java.lang.IllegalArgumentException("Unexpected arguments after --preload-app.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:283:0x03d8, code lost:
    
        if (r3 == false) goto L235;
     */
    /* JADX WARN: Code restructure failed: missing block: B:284:0x03da, code lost:
    
        if (r2 != false) goto L228;
     */
    /* JADX WARN: Code restructure failed: missing block: B:285:0x03dc, code lost:
    
        r5 = new java.lang.StringBuilder().append("Unexpected argument : ");
     */
    /* JADX WARN: Code restructure failed: missing block: B:286:0x03e9, code lost:
    
        if (r0 != null) goto L225;
     */
    /* JADX WARN: Code restructure failed: missing block: B:287:0x03eb, code lost:
    
        r6 = r13.nextArg();
     */
    /* JADX WARN: Code restructure failed: missing block: B:289:0x03fc, code lost:
    
        throw new java.lang.IllegalArgumentException(r5.append(r6).toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:290:0x03f0, code lost:
    
        r6 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:291:0x03fd, code lost:
    
        r12.mRemainingArgs = new java.lang.String[r14 - r1];
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:292:0x0404, code lost:
    
        if (r0 == null) goto L312;
     */
    /* JADX WARN: Code restructure failed: missing block: B:293:0x0406, code lost:
    
        r12.mRemainingArgs[0] = r0;
        r5 = 0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:296:0x040e, code lost:
    
        if (r5 >= (r14 - r1)) goto L311;
     */
    /* JADX WARN: Code restructure failed: missing block: B:297:0x0410, code lost:
    
        r12.mRemainingArgs[r5] = r13.nextArg();
        r5 = r5 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:299:0x041b, code lost:
    
        if (r14 > r1) goto L248;
     */
    /* JADX WARN: Code restructure failed: missing block: B:301:0x0449, code lost:
    
        throw new java.lang.IllegalArgumentException("Unexpected arguments after --query-abi-list.");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void parseArgs(com.android.internal.os.ZygoteCommandBuffer r13, int r14) throws java.lang.IllegalArgumentException, java.io.EOFException {
        /*
            Method dump skipped, instructions count: 1098
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.ZygoteArguments.parseArgs(com.android.internal.os.ZygoteCommandBuffer, int):void");
    }

    private static String getAssignmentValue(String arg) {
        return arg.substring(arg.indexOf(61) + 1);
    }

    private static String[] getAssignmentList(String arg) {
        return getAssignmentValue(arg).split(",");
    }
}
