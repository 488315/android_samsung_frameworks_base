package com.android.server.policy.keyguard;

import android.app.ActivityManager;
import android.content.Context;
import android.os.RemoteException;
import android.util.Slog;

import com.android.internal.policy.IKeyguardService;
import com.android.internal.policy.IKeyguardStateCallback;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.policy.PhoneWindowManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class KeyguardStateMonitor extends IKeyguardStateCallback.Stub {
    public final PhoneWindowManager.AnonymousClass1 mCallback;
    public final LockPatternUtils mLockPatternUtils;
    public volatile boolean mIsShowing = true;
    public volatile boolean mSimSecure = true;
    public volatile boolean mInputRestricted = true;
    public volatile boolean mTrusted = false;
    public int mCurrentUserId = ActivityManager.getCurrentUser();

    public KeyguardStateMonitor(
            Context context,
            IKeyguardService iKeyguardService,
            PhoneWindowManager.AnonymousClass1 anonymousClass1) {
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mCallback = anonymousClass1;
        try {
            iKeyguardService.addStateMonitorCallback(this);
        } catch (RemoteException e) {
            Slog.w("KeyguardStateMonitor", "Remote Exception", e);
        }
    }

    public final void onInputRestrictedStateChanged(boolean z) {
        this.mInputRestricted = z;
    }

    public final void onShowingStateChanged(boolean z, int i) {
        if (i != this.mCurrentUserId) {
            return;
        }
        this.mIsShowing = z;
        this.mCallback.onShowingChanged();
    }

    public final void onSimSecureStateChanged(boolean z) {
        this.mSimSecure = z;
    }

    public final void onTrustedChanged(boolean z) {
        this.mTrusted = z;
        this.mCallback.onTrustedChanged();
    }
}
