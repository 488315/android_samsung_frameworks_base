package com.android.server.wm;

import android.os.Handler;
import android.os.IBinder;
import android.util.ArrayMap;
import android.view.RemoteAnimationAdapter;
import android.window.RemoteTransition;

import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PendingRemoteAnimationRegistry {
    public final ArrayMap mEntries = new ArrayMap();
    public final Handler mHandler;
    public final WindowManagerGlobalLock mLock;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Entry {
        public final RemoteAnimationAdapter adapter;
        public final IBinder launchCookie;
        public final RemoteTransition remoteTransition;

        public Entry(
                final String str,
                RemoteAnimationAdapter remoteAnimationAdapter,
                IBinder iBinder,
                RemoteTransition remoteTransition) {
            this.adapter = remoteAnimationAdapter;
            this.launchCookie = iBinder;
            if (CoreRune.FW_SHELL_TRANSITION_REMOTE) {
                this.remoteTransition = remoteTransition;
            }
            PendingRemoteAnimationRegistry.this.mHandler.postDelayed(
                    new Runnable() { // from class:
                                     // com.android.server.wm.PendingRemoteAnimationRegistry$Entry$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            PendingRemoteAnimationRegistry.Entry entry =
                                    PendingRemoteAnimationRegistry.Entry.this;
                            String str2 = str;
                            WindowManagerGlobalLock windowManagerGlobalLock =
                                    PendingRemoteAnimationRegistry.this.mLock;
                            WindowManagerService.boostPriorityForLockedSection();
                            synchronized (windowManagerGlobalLock) {
                                try {
                                    if (((PendingRemoteAnimationRegistry.Entry)
                                                    PendingRemoteAnimationRegistry.this.mEntries
                                                            .get(str2))
                                            == entry) {
                                        PendingRemoteAnimationRegistry.this.mEntries.remove(str2);
                                    }
                                } catch (Throwable th) {
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                    throw th;
                                }
                            }
                            WindowManagerService.resetPriorityAfterLockedSection();
                        }
                    },
                    3000L);
        }
    }

    public PendingRemoteAnimationRegistry(
            WindowManagerGlobalLock windowManagerGlobalLock, Handler handler) {
        this.mLock = windowManagerGlobalLock;
        this.mHandler = handler;
    }
}
