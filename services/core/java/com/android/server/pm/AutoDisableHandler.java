package com.android.server.pm;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

import com.android.server.am.MARsPackageInfo;
import com.android.server.am.MARsPolicyManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AutoDisableHandler {
    public ADHandler mAutoDisableHandler;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ADHandler extends Handler {
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Bundle data;
            if (message.what == 1 && (data = message.getData()) != null) {
                String string = data.getString("packageName", null);
                int i = data.getInt("userId", -1);
                boolean z = false;
                if (data.getBoolean("isGoogleChanged", false)) {
                    boolean z2 = MARsPolicyManager.MARs_ENABLE;
                    MARsPolicyManager mARsPolicyManager =
                            MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                    mARsPolicyManager.getClass();
                    synchronized (MARsPolicyManager.MARsLock) {
                        MARsPackageInfo mARsPackageInfo =
                                MARsPolicyManager.getMARsPackageInfo(
                                        mARsPolicyManager.mMARsTargetPackages, string, i);
                        if (mARsPackageInfo != null) {
                            mARsPackageInfo.curLevel = 0;
                            mARsPackageInfo.isDisabled = false;
                        }
                    }
                    return;
                }
                boolean z3 = MARsPolicyManager.MARs_ENABLE;
                MARsPolicyManager mARsPolicyManager2 =
                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                mARsPolicyManager2.getClass();
                synchronized (MARsPolicyManager.MARsLock) {
                    MARsPackageInfo mARsPackageInfo2 =
                            MARsPolicyManager.getMARsPackageInfo(
                                    mARsPolicyManager2.mMARsRestrictedPackages, string, i);
                    if (mARsPackageInfo2 != null
                            && MARsPolicyManager.isDisabledByUser(mARsPackageInfo2.disableReason)) {
                        mARsPackageInfo2.disableReason = 0;
                        if (!MARsPolicyManager.isChinaPolicyEnabled()) {
                            mARsPackageInfo2.fasType = 1;
                            mARsPackageInfo2.state = 1;
                            z = true;
                        }
                    }
                }
                mARsPolicyManager2.changeAutoDisabledAppState(i, string, true ^ z);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AutoDisableThread extends Thread {
        public final int mPriority;

        public AutoDisableThread() {
            super("AutoDisableThread");
            this.mPriority = 0;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Process.setThreadPriority(this.mPriority);
            Looper.prepare();
            AutoDisableHandler.this.mAutoDisableHandler = new ADHandler();
            Looper.loop();
        }
    }

    public AutoDisableHandler() {
        new AutoDisableThread().start();
    }
}
