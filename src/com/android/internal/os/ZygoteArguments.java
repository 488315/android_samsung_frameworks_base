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

    private void parseArgs(ZygoteCommandBuffer zygoteCommandBuffer, int i) throws IllegalArgumentException, EOFException {
        String strNextArg;
        int i2 = 1;
        int i3 = 0;
        boolean z = false;
        boolean z2 = true;
        while (true) {
            strNextArg = null;
            if (i3 >= i) {
                break;
            }
            String strNextArg2 = zygoteCommandBuffer.nextArg();
            if (strNextArg2.equals("--")) {
                i3++;
                break;
            }
            if (strNextArg2.startsWith("--setuid=")) {
                if (this.mUidSpecified) {
                    throw new IllegalArgumentException("Duplicate arg specified");
                }
                this.mUidSpecified = true;
                this.mUid = Integer.parseInt(getAssignmentValue(strNextArg2));
            } else if (strNextArg2.startsWith("--setgid=")) {
                if (this.mGidSpecified) {
                    throw new IllegalArgumentException("Duplicate arg specified");
                }
                this.mGidSpecified = true;
                this.mGid = Integer.parseInt(getAssignmentValue(strNextArg2));
            } else if (strNextArg2.startsWith("--target-sdk-version=")) {
                if (this.mTargetSdkVersionSpecified) {
                    throw new IllegalArgumentException("Duplicate target-sdk-version specified");
                }
                this.mTargetSdkVersionSpecified = true;
                this.mTargetSdkVersion = Integer.parseInt(getAssignmentValue(strNextArg2));
            } else if (strNextArg2.equals("--runtime-args")) {
                z = true;
            } else if (strNextArg2.startsWith("--runtime-flags=")) {
                this.mRuntimeFlags = Integer.parseInt(getAssignmentValue(strNextArg2));
            } else if (strNextArg2.startsWith("--seinfo=")) {
                if (this.mSeInfoSpecified) {
                    throw new IllegalArgumentException("Duplicate arg specified");
                }
                this.mSeInfoSpecified = true;
                this.mSeInfo = getAssignmentValue(strNextArg2);
            } else if (strNextArg2.startsWith("--capabilities=")) {
                if (this.mCapabilitiesSpecified) {
                    throw new IllegalArgumentException("Duplicate arg specified");
                }
                this.mCapabilitiesSpecified = true;
                String[] strArrSplit = getAssignmentValue(strNextArg2).split(",", 2);
                if (strArrSplit.length == 1) {
                    long jLongValue = Long.decode(strArrSplit[0]).longValue();
                    this.mEffectiveCapabilities = jLongValue;
                    this.mPermittedCapabilities = jLongValue;
                } else {
                    this.mPermittedCapabilities = Long.decode(strArrSplit[0]).longValue();
                    this.mEffectiveCapabilities = Long.decode(strArrSplit[1]).longValue();
                }
            } else if (strNextArg2.startsWith("--rlimit=")) {
                String[] assignmentList = getAssignmentList(strNextArg2);
                if (assignmentList.length != 3) {
                    throw new IllegalArgumentException("--rlimit= should have 3 comma-delimited ints");
                }
                int[] iArr = new int[assignmentList.length];
                for (int i4 = 0; i4 < assignmentList.length; i4++) {
                    iArr[i4] = Integer.parseInt(assignmentList[i4]);
                }
                if (this.mRLimits == null) {
                    this.mRLimits = new ArrayList<>();
                }
                this.mRLimits.add(iArr);
            } else if (strNextArg2.startsWith("--setgroups=")) {
                if (this.mGids != null) {
                    throw new IllegalArgumentException("Duplicate arg specified");
                }
                String[] assignmentList2 = getAssignmentList(strNextArg2);
                this.mGids = new int[assignmentList2.length];
                for (int length = assignmentList2.length - 1; length >= 0; length--) {
                    this.mGids[length] = Integer.parseInt(assignmentList2[length]);
                }
            } else if (strNextArg2.equals("--invoke-with")) {
                if (this.mInvokeWith != null) {
                    throw new IllegalArgumentException("Duplicate arg specified");
                }
                i3++;
                try {
                    this.mInvokeWith = zygoteCommandBuffer.nextArg();
                } catch (IndexOutOfBoundsException unused) {
                    throw new IllegalArgumentException("--invoke-with requires argument");
                }
            } else if (strNextArg2.startsWith("--nice-name=")) {
                if (this.mNiceName != null) {
                    throw new IllegalArgumentException("Duplicate arg specified");
                }
                this.mNiceName = getAssignmentValue(strNextArg2);
            } else if (strNextArg2.equals("--mount-external-default")) {
                this.mMountExternal = 1;
            } else if (strNextArg2.equals("--mount-external-installer")) {
                this.mMountExternal = 2;
            } else if (strNextArg2.equals("--mount-external-pass-through")) {
                this.mMountExternal = 3;
            } else if (strNextArg2.equals("--mount-external-android-writable")) {
                this.mMountExternal = 4;
            } else if (strNextArg2.equals("--query-abi-list")) {
                this.mAbiListQuery = true;
            } else if (strNextArg2.equals("--get-pid")) {
                this.mPidQuery = true;
            } else if (strNextArg2.equals("--boot-completed")) {
                this.mBootCompleted = true;
            } else if (strNextArg2.startsWith("--instruction-set=")) {
                this.mInstructionSet = getAssignmentValue(strNextArg2);
            } else if (strNextArg2.startsWith("--app-data-dir=")) {
                this.mAppDataDir = getAssignmentValue(strNextArg2);
            } else if (strNextArg2.equals("--preload-app")) {
                i3++;
                this.mPreloadApp = zygoteCommandBuffer.nextArg();
            } else {
                if (strNextArg2.equals("--preload-default")) {
                    this.mPreloadDefault = true;
                } else if (strNextArg2.equals("--start-child-zygote")) {
                    this.mStartChildZygote = true;
                } else if (strNextArg2.equals("--set-api-denylist-exemptions")) {
                    this.mApiDenylistExemptions = new String[(i - i3) - 1];
                    i3++;
                    int i5 = 0;
                    while (i3 < i) {
                        this.mApiDenylistExemptions[i5] = zygoteCommandBuffer.nextArg();
                        i3++;
                        i5++;
                    }
                } else if (strNextArg2.startsWith("--hidden-api-log-sampling-rate=")) {
                    String assignmentValue = getAssignmentValue(strNextArg2);
                    try {
                        this.mHiddenApiAccessLogSampleRate = Integer.parseInt(assignmentValue);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid log sampling rate: " + assignmentValue, e);
                    }
                } else if (strNextArg2.startsWith("--hidden-api-statslog-sampling-rate=")) {
                    String assignmentValue2 = getAssignmentValue(strNextArg2);
                    try {
                        this.mHiddenApiAccessStatslogSampleRate = Integer.parseInt(assignmentValue2);
                    } catch (NumberFormatException e2) {
                        throw new IllegalArgumentException("Invalid statslog sampling rate: " + assignmentValue2, e2);
                    }
                } else if (strNextArg2.startsWith("--package-name=")) {
                    if (this.mPackageName != null) {
                        throw new IllegalArgumentException("Duplicate arg specified");
                    }
                    this.mPackageName = getAssignmentValue(strNextArg2);
                } else if (strNextArg2.startsWith("--usap-pool-enabled=")) {
                    this.mUsapPoolStatusSpecified = true;
                    this.mUsapPoolEnabled = Boolean.parseBoolean(getAssignmentValue(strNextArg2));
                } else if (strNextArg2.startsWith(Zygote.START_AS_TOP_APP_ARG)) {
                    this.mIsTopApp = true;
                } else if (strNextArg2.startsWith("--disabled-compat-changes=")) {
                    if (this.mDisabledCompatChanges != null) {
                        throw new IllegalArgumentException("Duplicate arg specified");
                    }
                    String[] assignmentList3 = getAssignmentList(strNextArg2);
                    int length2 = assignmentList3.length;
                    this.mDisabledCompatChanges = new long[length2];
                    for (int i6 = 0; i6 < length2; i6++) {
                        this.mDisabledCompatChanges[i6] = Long.parseLong(assignmentList3[i6]);
                    }
                } else if (strNextArg2.startsWith(Zygote.PKG_DATA_INFO_MAP)) {
                    this.mPkgDataInfoList = getAssignmentList(strNextArg2);
                } else if (strNextArg2.startsWith(Zygote.ALLOWLISTED_DATA_INFO_MAP)) {
                    this.mAllowlistedDataInfoList = getAssignmentList(strNextArg2);
                } else if (strNextArg2.equals(Zygote.BIND_MOUNT_APP_STORAGE_DIRS)) {
                    this.mBindMountAppStorageDirs = true;
                } else if (strNextArg2.equals(Zygote.BIND_MOUNT_APP_DATA_DIRS)) {
                    this.mBindMountAppDataDirs = true;
                } else {
                    if (!strNextArg2.equals(Zygote.BIND_MOUNT_SYSPROP_OVERRIDES)) {
                        strNextArg = strNextArg2;
                        break;
                    }
                    this.mBindMountSyspropOverrides = true;
                }
                z2 = false;
            }
            i3++;
        }
        if (this.mBootCompleted) {
            if (i > i3) {
                throw new IllegalArgumentException("Unexpected arguments after --boot-completed");
            }
        } else if (this.mAbiListQuery || this.mPidQuery) {
            if (i > i3) {
                throw new IllegalArgumentException("Unexpected arguments after --query-abi-list.");
            }
        } else if (this.mPreloadApp != null) {
            if (i > i3) {
                throw new IllegalArgumentException("Unexpected arguments after --preload-app.");
            }
        } else if (z2) {
            if (!z) {
                StringBuilder sb = new StringBuilder("Unexpected argument : ");
                if (strNextArg == null) {
                    strNextArg = zygoteCommandBuffer.nextArg();
                }
                sb.append(strNextArg);
                throw new IllegalArgumentException(sb.toString());
            }
            int i7 = i - i3;
            String[] strArr = new String[i7];
            this.mRemainingArgs = strArr;
            if (strNextArg != null) {
                strArr[0] = strNextArg;
            } else {
                i2 = 0;
            }
            while (i2 < i7) {
                this.mRemainingArgs[i2] = zygoteCommandBuffer.nextArg();
                i2++;
            }
        }
        if (this.mStartChildZygote) {
            for (String str : this.mRemainingArgs) {
                if (str.startsWith(Zygote.CHILD_ZYGOTE_SOCKET_NAME_ARG)) {
                    return;
                }
            }
            throw new IllegalArgumentException("--start-child-zygote specified without --zygote-socket=");
        }
    }

    private static String getAssignmentValue(String str) {
        return str.substring(str.indexOf(61) + 1);
    }

    private static String[] getAssignmentList(String str) {
        return getAssignmentValue(str).split(",");
    }
}
