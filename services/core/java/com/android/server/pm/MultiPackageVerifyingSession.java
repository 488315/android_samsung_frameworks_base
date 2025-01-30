package com.android.server.pm;

import android.content.pm.IPackageInstallObserver2;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Slog;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes3.dex */
public final class MultiPackageVerifyingSession {
    public final List mChildVerifyingSessions;
    public final IPackageInstallObserver2 mObserver;
    public final UserHandle mUser;
    public final Set mVerificationState;

    public MultiPackageVerifyingSession(VerifyingSession verifyingSession, List list) {
        this.mUser = verifyingSession.getUser();
        if (list.size() == 0) {
            throw PackageManagerException.ofInternalError("No child sessions found!", -21);
        }
        this.mChildVerifyingSessions = list;
        for (int i = 0; i < list.size(); i++) {
            ((VerifyingSession) list.get(i)).mParentVerifyingSession = this;
        }
        this.mVerificationState = new ArraySet(this.mChildVerifyingSessions.size());
        this.mObserver = verifyingSession.mObserver;
    }

    public void start() {
        Trace.asyncTraceEnd(262144L, "queueVerify", System.identityHashCode(this));
        Trace.traceBegin(262144L, "startVerify");
        Iterator it = this.mChildVerifyingSessions.iterator();
        while (it.hasNext()) {
            ((VerifyingSession) it.next()).handleStartVerify();
        }
        Iterator it2 = this.mChildVerifyingSessions.iterator();
        while (it2.hasNext()) {
            ((VerifyingSession) it2.next()).handleReturnCode();
        }
        Trace.traceEnd(262144L);
    }

    public void trySendVerificationCompleteNotification(VerifyingSession verifyingSession) {
        int i;
        String str;
        this.mVerificationState.add(verifyingSession);
        if (this.mVerificationState.size() != this.mChildVerifyingSessions.size()) {
            return;
        }
        Iterator it = this.mVerificationState.iterator();
        while (true) {
            i = 1;
            if (!it.hasNext()) {
                str = null;
                break;
            }
            VerifyingSession verifyingSession2 = (VerifyingSession) it.next();
            int ret = verifyingSession2.getRet();
            if (ret != 1) {
                str = verifyingSession2.getErrorMessage();
                i = ret;
                break;
            }
        }
        try {
            this.mObserver.onPackageInstalled((String) null, i, str, new Bundle());
        } catch (RemoteException unused) {
            Slog.i("PackageManager", "Observer no longer exists.");
        }
    }

    public String toString() {
        return "MultiPackageVerifyingSession{" + Integer.toHexString(System.identityHashCode(this)) + "}";
    }
}
