package com.android.server.desktopmode;

import android.os.Handler;

import com.android.server.ServiceThread;

import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.SemDesktopModeManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BootInitBlocker {
    public final BootInitBlocker$$ExternalSyntheticLambda0 mBlocker =
            new BootInitBlocker$$ExternalSyntheticLambda0();
    public final SemDesktopModeManager mDesktopModeManager;
    public final Handler mHandler;
    public final IStateManager mStateManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.desktopmode.BootInitBlocker$1, reason: invalid class name */
    public final class AnonymousClass1 extends StateManager.StateListener {
        public AnonymousClass1() {}

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public final void onUserChanged(StateManager.InternalState internalState) {
            BootInitBlocker bootInitBlocker = BootInitBlocker.this;
            bootInitBlocker.mHandler.postDelayed(
                    new Runnable() { // from class:
                                     // com.android.server.desktopmode.BootInitBlocker$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            BootInitBlocker.AnonymousClass1 anonymousClass1 =
                                    BootInitBlocker.AnonymousClass1.this;
                            anonymousClass1.getClass();
                            if (DesktopModeFeature.DEBUG) {
                                Log.d("[DMS]BootInitBlocker", "unregisterBlocker");
                            }
                            BootInitBlocker bootInitBlocker2 = BootInitBlocker.this;
                            bootInitBlocker2.mDesktopModeManager.unregisterBlocker(
                                    bootInitBlocker2.mBlocker);
                            ((StateManager) bootInitBlocker2.mStateManager)
                                    .notifyBootInitBlockerRegistered(false);
                        }
                    },
                    3000L);
            ((StateManager) bootInitBlocker.mStateManager).unregisterListener(this);
        }
    }

    public BootInitBlocker(
            ServiceThread serviceThread,
            IStateManager iStateManager,
            SemDesktopModeManager semDesktopModeManager) {
        this.mHandler = new Handler(serviceThread.getLooper());
        this.mStateManager = iStateManager;
        this.mDesktopModeManager = semDesktopModeManager;
    }
}
