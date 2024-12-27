package com.android.server.wm;

import android.app.admin.DevicePolicyCache;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.TokenWatcher;

import com.android.server.pm.UserManagerInternal;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.policy.WindowManagerPolicy;
import com.android.server.utils.UserTokenWatcher;
import com.android.server.utils.UserTokenWatcher.InnerTokenWatcher;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class KeyguardDisableHandler {
    public final UserTokenWatcher mAppTokenWatcher;
    public int mCurrentUser = 0;
    public final Injector mInjector;
    public final UserTokenWatcher mSystemTokenWatcher;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.wm.KeyguardDisableHandler$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.wm.KeyguardDisableHandler$2, reason: invalid class name */
    public final class AnonymousClass2 implements Injector {
        public final /* synthetic */ WindowManagerPolicy val$policy;
        public final /* synthetic */ UserManagerInternal val$userManager;

        public AnonymousClass2(
                WindowManagerPolicy windowManagerPolicy, UserManagerInternal userManagerInternal) {
            this.val$policy = windowManagerPolicy;
            this.val$userManager = userManagerInternal;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Injector {}

    public KeyguardDisableHandler(Injector injector, Handler handler) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mInjector = injector;
        this.mAppTokenWatcher = new UserTokenWatcher(anonymousClass1, handler);
        this.mSystemTokenWatcher = new UserTokenWatcher(anonymousClass1, handler);
    }

    public final void disableKeyguard(int i, IBinder iBinder, String str, int i2) {
        UserTokenWatcher userTokenWatcher;
        if (Process.isApplicationUid(i)) {
            userTokenWatcher = this.mAppTokenWatcher;
        } else {
            if (i != 1000 || !(iBinder instanceof LockTaskController.LockTaskToken)) {
                throw new UnsupportedOperationException("Only apps can use the KeyguardLock API");
            }
            userTokenWatcher = this.mSystemTokenWatcher;
        }
        int profileParentId =
                ((AnonymousClass2) this.mInjector).val$userManager.getProfileParentId(i2);
        synchronized (userTokenWatcher.mWatchers) {
            try {
                TokenWatcher tokenWatcher =
                        (TokenWatcher) userTokenWatcher.mWatchers.get(profileParentId);
                if (tokenWatcher == null) {
                    tokenWatcher =
                            userTokenWatcher
                            .new InnerTokenWatcher(
                                    profileParentId,
                                    userTokenWatcher.mHandler,
                                    userTokenWatcher.mTag);
                    userTokenWatcher.mWatchers.put(profileParentId, tokenWatcher);
                }
                tokenWatcher.acquire(iBinder, str);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void updateKeyguardEnabled(int i) {
        synchronized (this) {
            updateKeyguardEnabledLocked(i);
        }
    }

    public final void updateKeyguardEnabledLocked(int i) {
        int i2 = this.mCurrentUser;
        if (i2 == i || i == -1) {
            Injector injector = this.mInjector;
            AnonymousClass2 anonymousClass2 = (AnonymousClass2) injector;
            anonymousClass2.getClass();
            boolean z = false;
            boolean z2 = DevicePolicyCache.getInstance().getPasswordQuality(i2) != 0;
            boolean z3 =
                    (z2
                                    || ((PhoneWindowManager) anonymousClass2.val$policy)
                                            .isKeyguardSecure(this.mCurrentUser))
                            ? false
                            : true;
            boolean z4 = !z2;
            if ((z3 && this.mAppTokenWatcher.isAcquired(i2))
                    || (z4 && this.mSystemTokenWatcher.isAcquired(i2))) {
                z = true;
            }
            ((PhoneWindowManager) ((AnonymousClass2) injector).val$policy).enableKeyguard(!z);
        }
    }
}
