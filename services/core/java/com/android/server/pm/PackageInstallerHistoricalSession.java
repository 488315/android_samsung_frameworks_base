package com.android.server.pm;

import android.content.pm.PackageInstaller;
import android.content.pm.verify.domain.DomainSet;

import com.android.internal.util.IndentingPrintWriter;

import java.io.CharArrayWriter;
import java.io.File;

public final class PackageInstallerHistoricalSession {
    public final int mBridges;
    public final int[] mChildSessionIds;
    public final float mClientProgress;
    public final boolean mCommitted;
    public final long mCommittedMillis;
    public final long mCreatedMillis;
    public final boolean mDestroyed;
    public final int mFds;
    public final String mFinalMessage;
    public final int mFinalStatus;
    public final InstallSource mInstallSource;
    public final int mInstallerUid;
    public final String mOriginalInstallerPackageName;
    public final int mOriginalInstallerUid;
    public final String mPackageName;
    public final String mParams;
    public final int mParentSessionId;
    public final boolean mPermissionsManuallyAccepted;
    public final String mPreVerifiedDomains;
    public final String mPreapprovalDetails;
    public final boolean mPreapprovalRequested;
    public final float mProgress;
    public final boolean mSealed;
    public final boolean mSessionApplied;
    public final int mSessionErrorCode;
    public final String mSessionErrorMessage;
    public final boolean mSessionFailed;
    public final boolean mSessionReady;
    public final String mStageCid;
    public final File mStageDir;
    public final boolean mStageDirInUse;
    public final long mUpdatedMillis;
    public final int sessionId;
    public final int userId;

    public PackageInstallerHistoricalSession(
            int i,
            int i2,
            int i3,
            String str,
            InstallSource installSource,
            int i4,
            long j,
            long j2,
            long j3,
            File file,
            String str2,
            float f,
            float f2,
            boolean z,
            boolean z2,
            boolean z3,
            boolean z4,
            boolean z5,
            boolean z6,
            int i5,
            int i6,
            int i7,
            String str3,
            PackageInstaller.SessionParams sessionParams,
            int i8,
            int[] iArr,
            boolean z7,
            boolean z8,
            boolean z9,
            int i9,
            String str4,
            PackageInstaller.PreapprovalDetails preapprovalDetails,
            DomainSet domainSet,
            String str5) {
        this.sessionId = i;
        this.userId = i2;
        this.mOriginalInstallerUid = i3;
        this.mOriginalInstallerPackageName = str;
        this.mInstallSource = installSource;
        this.mInstallerUid = i4;
        this.mCreatedMillis = j;
        this.mUpdatedMillis = j2;
        this.mCommittedMillis = j3;
        this.mStageDir = file;
        this.mStageCid = str2;
        this.mClientProgress = f;
        this.mProgress = f2;
        this.mCommitted = z;
        this.mPreapprovalRequested = z2;
        this.mSealed = z3;
        this.mPermissionsManuallyAccepted = z4;
        this.mStageDirInUse = z5;
        this.mDestroyed = z6;
        this.mFds = i5;
        this.mBridges = i6;
        this.mFinalStatus = i7;
        this.mFinalMessage = str3;
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        sessionParams.dump(new IndentingPrintWriter(charArrayWriter, "    "));
        this.mParams = charArrayWriter.toString();
        this.mParentSessionId = i8;
        this.mChildSessionIds = iArr;
        this.mSessionApplied = z7;
        this.mSessionFailed = z8;
        this.mSessionReady = z9;
        this.mSessionErrorCode = i9;
        this.mSessionErrorMessage = str4;
        if (preapprovalDetails != null) {
            this.mPreapprovalDetails = preapprovalDetails.toString();
        } else {
            this.mPreapprovalDetails = null;
        }
        if (domainSet != null) {
            this.mPreVerifiedDomains = String.join(",", domainSet.getDomains());
        } else {
            this.mPreVerifiedDomains = null;
        }
        this.mPackageName =
                preapprovalDetails != null
                        ? preapprovalDetails.getPackageName()
                        : str5 != null ? str5 : sessionParams.appPackageName;
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Session " + this.sessionId + ":");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("userId", Integer.valueOf(this.userId));
        indentingPrintWriter.printPair(
                "mOriginalInstallerUid", Integer.valueOf(this.mOriginalInstallerUid));
        indentingPrintWriter.printPair(
                "mOriginalInstallerPackageName", this.mOriginalInstallerPackageName);
        InstallSource installSource = this.mInstallSource;
        indentingPrintWriter.printPair("installerPackageName", installSource.mInstallerPackageName);
        indentingPrintWriter.printPair(
                "installInitiatingPackageName", installSource.mInitiatingPackageName);
        indentingPrintWriter.printPair(
                "installOriginatingPackageName", installSource.mOriginatingPackageName);
        indentingPrintWriter.printPair("mInstallerUid", Integer.valueOf(this.mInstallerUid));
        indentingPrintWriter.printPair("createdMillis", Long.valueOf(this.mCreatedMillis));
        indentingPrintWriter.printPair("updatedMillis", Long.valueOf(this.mUpdatedMillis));
        indentingPrintWriter.printPair("committedMillis", Long.valueOf(this.mCommittedMillis));
        indentingPrintWriter.printPair("stageDir", this.mStageDir);
        indentingPrintWriter.printPair("stageCid", this.mStageCid);
        indentingPrintWriter.println();
        indentingPrintWriter.print(this.mParams);
        indentingPrintWriter.printPair("mClientProgress", Float.valueOf(this.mClientProgress));
        indentingPrintWriter.printPair("mProgress", Float.valueOf(this.mProgress));
        indentingPrintWriter.printPair("mCommitted", Boolean.valueOf(this.mCommitted));
        indentingPrintWriter.printPair(
                "mPreapprovalRequested", Boolean.valueOf(this.mPreapprovalRequested));
        indentingPrintWriter.printPair("mSealed", Boolean.valueOf(this.mSealed));
        indentingPrintWriter.printPair(
                "mPermissionsManuallyAccepted", Boolean.valueOf(this.mPermissionsManuallyAccepted));
        indentingPrintWriter.printPair("mStageDirInUse", Boolean.valueOf(this.mStageDirInUse));
        indentingPrintWriter.printPair("mDestroyed", Boolean.valueOf(this.mDestroyed));
        indentingPrintWriter.printPair("mFds", Integer.valueOf(this.mFds));
        indentingPrintWriter.printPair("mBridges", Integer.valueOf(this.mBridges));
        indentingPrintWriter.printPair("mFinalStatus", Integer.valueOf(this.mFinalStatus));
        indentingPrintWriter.printPair("mFinalMessage", this.mFinalMessage);
        indentingPrintWriter.printPair("mParentSessionId", Integer.valueOf(this.mParentSessionId));
        indentingPrintWriter.printPair("mChildSessionIds", this.mChildSessionIds);
        indentingPrintWriter.printPair("mSessionApplied", Boolean.valueOf(this.mSessionApplied));
        indentingPrintWriter.printPair("mSessionFailed", Boolean.valueOf(this.mSessionFailed));
        indentingPrintWriter.printPair("mSessionReady", Boolean.valueOf(this.mSessionReady));
        indentingPrintWriter.printPair(
                "mSessionErrorCode", Integer.valueOf(this.mSessionErrorCode));
        indentingPrintWriter.printPair("mSessionErrorMessage", this.mSessionErrorMessage);
        indentingPrintWriter.printPair("mPreapprovalDetails", this.mPreapprovalDetails);
        indentingPrintWriter.printPair("mPreVerifiedDomains", this.mPreVerifiedDomains);
        indentingPrintWriter.printPair("mAppPackageName", this.mPackageName);
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
    }

    public final PackageInstaller.SessionInfo generateInfo() {
        PackageInstaller.SessionInfo sessionInfo = new PackageInstaller.SessionInfo();
        sessionInfo.sessionId = this.sessionId;
        sessionInfo.userId = this.userId;
        InstallSource installSource = this.mInstallSource;
        sessionInfo.installerPackageName = installSource.mInstallerPackageName;
        sessionInfo.installerAttributionTag = installSource.mInstallerAttributionTag;
        sessionInfo.progress = this.mProgress;
        sessionInfo.sealed = this.mSealed;
        sessionInfo.isCommitted = this.mCommitted;
        sessionInfo.isPreapprovalRequested = this.mPreapprovalRequested;
        sessionInfo.parentSessionId = this.mParentSessionId;
        sessionInfo.childSessionIds = this.mChildSessionIds;
        sessionInfo.isSessionApplied = this.mSessionApplied;
        sessionInfo.isSessionReady = this.mSessionReady;
        sessionInfo.isSessionFailed = this.mSessionFailed;
        sessionInfo.setSessionErrorCode(this.mSessionErrorCode, this.mSessionErrorMessage);
        sessionInfo.createdMillis = this.mCreatedMillis;
        sessionInfo.updatedMillis = this.mUpdatedMillis;
        sessionInfo.installerUid = this.mInstallerUid;
        sessionInfo.appPackageName = this.mPackageName;
        return sessionInfo;
    }
}
