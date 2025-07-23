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

    private ZygoteArguments(ZygoteCommandBuffer zygoteCommandBuffer, int i) throws IllegalArgumentException, EOFException {
        parseArgs(zygoteCommandBuffer, i);
    }

    public static ZygoteArguments getInstance(ZygoteCommandBuffer zygoteCommandBuffer) throws IllegalArgumentException, EOFException {
        int count = zygoteCommandBuffer.getCount();
        if (count == 0) {
            return null;
        }
        return new ZygoteArguments(zygoteCommandBuffer, count);
    }

    /* JADX WARN: Code restructure failed: missing block: B:239:0x0364, code lost:
    
        if (r11.mBootCompleted == false) goto L196;
     */
    /* JADX WARN: Code restructure failed: missing block: B:240:0x0366, code lost:
    
        if (r13 > r2) goto L194;
     */
    /* JADX WARN: Code restructure failed: missing block: B:242:0x0370, code lost:
    
        throw new java.lang.IllegalArgumentException("Unexpected arguments after --boot-completed");
     */
    /* JADX WARN: Code restructure failed: missing block: B:244:0x03c3, code lost:
    
        if (r11.mStartChildZygote == false) goto L231;
     */
    /* JADX WARN: Code restructure failed: missing block: B:245:0x03c5, code lost:
    
        r11 = r11.mRemainingArgs;
        r12 = r11.length;
     */
    /* JADX WARN: Code restructure failed: missing block: B:246:0x03c8, code lost:
    
        if (r0 >= r12) goto L289;
     */
    /* JADX WARN: Code restructure failed: missing block: B:248:0x03d2, code lost:
    
        if (r11[r0].startsWith(com.android.internal.os.Zygote.CHILD_ZYGOTE_SOCKET_NAME_ARG) == false) goto L228;
     */
    /* JADX WARN: Code restructure failed: missing block: B:249:0x03d5, code lost:
    
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:251:0x03d4, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:254:0x03df, code lost:
    
        throw new java.lang.IllegalArgumentException("--start-child-zygote specified without --zygote-socket=");
     */
    /* JADX WARN: Code restructure failed: missing block: B:255:0x03e0, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:257:0x0373, code lost:
    
        if (r11.mAbiListQuery != false) goto L220;
     */
    /* JADX WARN: Code restructure failed: missing block: B:259:0x0377, code lost:
    
        if (r11.mPidQuery == false) goto L201;
     */
    /* JADX WARN: Code restructure failed: missing block: B:261:0x037c, code lost:
    
        if (r11.mPreloadApp == null) goto L207;
     */
    /* JADX WARN: Code restructure failed: missing block: B:262:0x037e, code lost:
    
        if (r13 > r2) goto L205;
     */
    /* JADX WARN: Code restructure failed: missing block: B:264:0x0388, code lost:
    
        throw new java.lang.IllegalArgumentException("Unexpected arguments after --preload-app.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:265:0x0389, code lost:
    
        if (r4 == false) goto L221;
     */
    /* JADX WARN: Code restructure failed: missing block: B:266:0x038b, code lost:
    
        if (r3 != false) goto L214;
     */
    /* JADX WARN: Code restructure failed: missing block: B:267:0x038d, code lost:
    
        r13 = new java.lang.StringBuilder("Unexpected argument : ");
     */
    /* JADX WARN: Code restructure failed: missing block: B:268:0x0396, code lost:
    
        if (r5 != null) goto L212;
     */
    /* JADX WARN: Code restructure failed: missing block: B:269:0x0398, code lost:
    
        r5 = r12.nextArg();
     */
    /* JADX WARN: Code restructure failed: missing block: B:270:0x039c, code lost:
    
        r13.append(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:271:0x03a6, code lost:
    
        throw new java.lang.IllegalArgumentException(r13.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:272:0x03a7, code lost:
    
        r13 = r13 - r2;
        r2 = new java.lang.String[r13];
        r11.mRemainingArgs = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:273:0x03ac, code lost:
    
        if (r5 == null) goto L217;
     */
    /* JADX WARN: Code restructure failed: missing block: B:274:0x03ae, code lost:
    
        r2[0] = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:276:0x03b2, code lost:
    
        if (r1 >= r13) goto L290;
     */
    /* JADX WARN: Code restructure failed: missing block: B:277:0x03b4, code lost:
    
        r11.mRemainingArgs[r1] = r12.nextArg();
        r1 = r1 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:279:0x03b1, code lost:
    
        r1 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:280:0x03bf, code lost:
    
        if (r13 > r2) goto L232;
     */
    /* JADX WARN: Code restructure failed: missing block: B:282:0x03e8, code lost:
    
        throw new java.lang.IllegalArgumentException("Unexpected arguments after --query-abi-list.");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void parseArgs(com.android.internal.os.ZygoteCommandBuffer r12, int r13) throws java.lang.IllegalArgumentException, java.io.EOFException {
        /*
            Method dump skipped, instructions count: 1001
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.os.ZygoteArguments.parseArgs(com.android.internal.os.ZygoteCommandBuffer, int):void");
    }

    private static String getAssignmentValue(String str) {
        return str.substring(str.indexOf(61) + 1);
    }

    private static String[] getAssignmentList(String str) {
        return getAssignmentValue(str).split(",");
    }
}
