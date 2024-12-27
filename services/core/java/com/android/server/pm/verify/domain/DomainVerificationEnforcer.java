package com.android.server.pm.verify.domain;

import android.content.Context;
import android.os.Binder;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.pm.DomainVerificationConnection;
import com.android.server.pm.verify.domain.proxy.DomainVerificationProxy;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DomainVerificationEnforcer {
    public DomainVerificationConnection mCallback;
    public final Context mContext;

    public DomainVerificationEnforcer(Context context) {
        this.mContext = context;
    }

    public static void assertInternal(int i) {
        if (i != 0 && i != 1000 && i != 2000) {
            throw new SecurityException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i, "Caller ", " is not allowed to change internal state"));
        }
    }

    public final void assertApprovedQuerent(
            int i, DomainVerificationProxy domainVerificationProxy) {
        if (i == 0 || i == 1000 || i == 2000) {
            return;
        }
        if (domainVerificationProxy.isCallerVerifier(i)) {
            this.mContext.enforcePermission(
                    "android.permission.QUERY_ALL_PACKAGES",
                    Binder.getCallingPid(),
                    i,
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i, "Caller ", " does not hold android.permission.QUERY_ALL_PACKAGES"));
        } else {
            this.mContext.enforcePermission(
                    "android.permission.DUMP",
                    Binder.getCallingPid(),
                    i,
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i, "Caller ", " is not allowed to query domain verification state"));
        }
    }

    public final boolean assertApprovedUserSelector(int i, int i2, int i3, String str) {
        if (i2 != i3) {
            this.mContext.enforcePermission(
                    "android.permission.INTERACT_ACROSS_USERS",
                    Binder.getCallingPid(),
                    i,
                    "Caller is not allowed to edit other users");
        }
        this.mContext.enforcePermission(
                "android.permission.UPDATE_DOMAIN_VERIFICATION_USER_SELECTION",
                Binder.getCallingPid(),
                i,
                "Caller is not allowed to edit user selections");
        if (!this.mCallback.mUmInternal.exists(i2)) {
            throw new SecurityException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i2, "User ", " does not exist"));
        }
        if (!this.mCallback.mUmInternal.exists(i3)) {
            throw new SecurityException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i3, "User ", " does not exist"));
        }
        if (str == null) {
            return true;
        }
        return !this.mCallback.mPm.snapshotComputer().filterAppAccess(i, i3, str, true);
    }

    public final void assertApprovedVerifier(
            int i, DomainVerificationProxy domainVerificationProxy) {
        if (i != 0 && i != 1000 && i != 2000) {
            int callingPid = Binder.getCallingPid();
            boolean z = false;
            if (this.mContext.checkPermission(
                            "android.permission.DOMAIN_VERIFICATION_AGENT", callingPid, i)
                    != 0) {
                r0 =
                        this.mContext.checkPermission(
                                        "android.permission.INTENT_FILTER_VERIFICATION_AGENT",
                                        callingPid,
                                        i)
                                == 0;
                if (!r0) {
                    throw new SecurityException(
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    i,
                                    "Caller ",
                                    " does not hold android.permission.DOMAIN_VERIFICATION_AGENT"));
                }
                z = r0;
            }
            if (!z) {
                this.mContext.enforcePermission(
                        "android.permission.QUERY_ALL_PACKAGES",
                        callingPid,
                        i,
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                i,
                                "Caller ",
                                " does not hold android.permission.QUERY_ALL_PACKAGES"));
            }
            r0 = domainVerificationProxy.isCallerVerifier(i);
        }
        if (!r0) {
            throw new SecurityException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i, "Caller ", " is not the approved domain verification agent"));
        }
    }
}
