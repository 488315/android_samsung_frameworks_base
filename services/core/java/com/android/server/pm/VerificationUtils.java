package com.android.server.pm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Trace;
import android.os.UserHandle;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class VerificationUtils {
    public static void broadcastPackageVerified(int i, Uri uri, int i2, String str, int i3, UserHandle userHandle, Context context) {
        Intent intent = new Intent("android.intent.action.PACKAGE_VERIFIED");
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.addFlags(1);
        intent.putExtra("android.content.pm.extra.VERIFICATION_ID", i);
        intent.putExtra("android.content.pm.extra.VERIFICATION_RESULT", i2);
        if (str != null) {
            intent.putExtra("android.content.pm.extra.VERIFICATION_ROOT_HASH", str);
        }
        intent.putExtra("android.content.pm.extra.DATA_LOADER_TYPE", i3);
        context.sendBroadcastAsUser(intent, userHandle, "android.permission.PACKAGE_VERIFICATION_AGENT");
    }

    public static void processVerificationResponse(int i, PackageVerificationState packageVerificationState, int i2, String str, PackageManagerService packageManagerService) {
        if (packageVerificationState.isVerificationComplete()) {
            VerifyingSession verifyingSession = packageVerificationState.mVerifyingSession;
            Uri fromFile = verifyingSession != null ? Uri.fromFile(verifyingSession.mOriginInfo.mResolvedFile) : null;
            boolean z = true;
            if (!((packageVerificationState.mRequiredVerificationComplete && packageVerificationState.mRequiredVerificationPassed) ? packageVerificationState.mSufficientVerificationComplete ? packageVerificationState.mSufficientVerificationPassed : true : false)) {
                i2 = -1;
            }
            int i3 = i2;
            if (packageManagerService != null && verifyingSession != null) {
                broadcastPackageVerified(i, fromFile, i3, null, verifyingSession.mDataLoaderType, verifyingSession.mUser, packageManagerService.mContext);
            }
            if (!packageVerificationState.mRequiredVerificationComplete || !packageVerificationState.mRequiredVerificationPassed) {
                z = false;
            } else if (packageVerificationState.mSufficientVerificationComplete) {
                z = packageVerificationState.mSufficientVerificationPassed;
            }
            if (z) {
                Slog.i("PackageManager", "Continuing with installation of " + fromFile);
            } else {
                String str2 = str + " for " + fromFile;
                Slog.i("PackageManager", str2);
                if (verifyingSession != null) {
                    verifyingSession.setReturnCode(-22, str2);
                }
            }
            if (packageManagerService != null && packageVerificationState.areAllVerificationsComplete()) {
                packageManagerService.mPendingVerification.remove(i);
            }
            Trace.asyncTraceEnd(262144L, "verification", i);
            if (verifyingSession != null) {
                verifyingSession.mWaitForVerificationToComplete = false;
                verifyingSession.handleReturnCode();
            }
        }
    }

    public static void processVerificationResponse(int i, PackageVerificationState packageVerificationState, PackageVerificationResponse packageVerificationResponse, PackageManagerService packageManagerService) {
        int i2 = packageVerificationResponse.callerUid;
        int i3 = packageVerificationResponse.code;
        packageVerificationState.setVerifierResponse(i2, i3);
        processVerificationResponse(i, packageVerificationState, i3, "Install not allowed", packageManagerService);
    }

    public static void processVerificationResponseOnTimeout(int i, PackageVerificationState packageVerificationState, PackageVerificationResponse packageVerificationResponse, PackageManagerService packageManagerService) {
        int i2 = packageVerificationResponse.callerUid;
        boolean z = packageVerificationState.mRequiredVerifierUids.get(i2, false);
        int i3 = packageVerificationResponse.code;
        if (z) {
            packageVerificationState.mSufficientVerifierUids.clear();
            if (packageVerificationState.mUnrespondedRequiredVerifierUids.get(i2, false)) {
                packageVerificationState.setVerifierResponse(i2, i3);
            }
        }
        processVerificationResponse(i, packageVerificationState, i3, "Verification timed out", packageManagerService);
    }
}
