package com.android.server.wm;

import android.app.ActivityOptions;
import android.os.Handler;
import android.os.IBinder;
import android.util.ArrayMap;
import android.view.RemoteAnimationAdapter;
import android.window.RemoteTransition;
import com.android.server.wm.PendingRemoteAnimationRegistry;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class PendingRemoteAnimationRegistry {
    public final ArrayMap mEntries = new ArrayMap();
    public final Handler mHandler;
    public final WindowManagerGlobalLock mLock;

    public PendingRemoteAnimationRegistry(WindowManagerGlobalLock windowManagerGlobalLock, Handler handler) {
        this.mLock = windowManagerGlobalLock;
        this.mHandler = handler;
    }

    public void addPendingAnimation(String str, RemoteAnimationAdapter remoteAnimationAdapter, IBinder iBinder) {
        addPendingAnimation(str, remoteAnimationAdapter, iBinder, null);
    }

    public void addPendingAnimation(String str, RemoteAnimationAdapter remoteAnimationAdapter, IBinder iBinder, RemoteTransition remoteTransition) {
        ArrayMap arrayMap = this.mEntries;
        if (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_REMOTE) {
            remoteTransition = null;
        }
        arrayMap.put(str, new Entry(str, remoteAnimationAdapter, iBinder, remoteTransition));
    }

    public ActivityOptions overrideOptionsIfNeeded(String str, ActivityOptions activityOptions) {
        Entry entry = (Entry) this.mEntries.get(str);
        if (entry == null) {
            return activityOptions;
        }
        if (activityOptions == null) {
            activityOptions = ActivityOptions.makeRemoteAnimation(entry.adapter);
        } else {
            activityOptions.setRemoteAnimationAdapter(entry.adapter);
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_REMOTE) {
            activityOptions.setRemoteTransition(entry.remoteTransition);
        }
        IBinder iBinder = entry.launchCookie;
        if (iBinder != null) {
            activityOptions.setLaunchCookie(iBinder);
        }
        this.mEntries.remove(str);
        return activityOptions;
    }

    public class Entry {
        public final RemoteAnimationAdapter adapter;
        public final IBinder launchCookie;
        public final String packageName;
        public RemoteTransition remoteTransition;

        public Entry(final String str, RemoteAnimationAdapter remoteAnimationAdapter, IBinder iBinder, RemoteTransition remoteTransition) {
            this.packageName = str;
            this.adapter = remoteAnimationAdapter;
            this.launchCookie = iBinder;
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_REMOTE) {
                this.remoteTransition = remoteTransition;
            }
            PendingRemoteAnimationRegistry.this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.wm.PendingRemoteAnimationRegistry$Entry$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PendingRemoteAnimationRegistry.Entry.this.lambda$new$0(str);
                }
            }, 3000L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(String str) {
            WindowManagerGlobalLock windowManagerGlobalLock = PendingRemoteAnimationRegistry.this.mLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (((Entry) PendingRemoteAnimationRegistry.this.mEntries.get(str)) == this) {
                        PendingRemoteAnimationRegistry.this.mEntries.remove(str);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }
}
