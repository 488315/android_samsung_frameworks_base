package com.android.server.am;

import android.os.IBinder;

import com.android.internal.util.function.TriConsumer;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;

import java.lang.ref.WeakReference;
import java.util.HashSet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PendingIntentController$$ExternalSyntheticLambda1
        implements TriConsumer {
    public final void accept(Object obj, Object obj2, Object obj3) {
        HashSet hashSet;
        IBinder iBinder = (IBinder) obj2;
        WeakReference weakReference = (WeakReference) obj3;
        WindowManagerGlobalLock windowManagerGlobalLock =
                ActivityTaskManagerService.this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityRecord isInRootTaskLocked = ActivityRecord.isInRootTaskLocked(iBinder);
                if (isInRootTaskLocked != null
                        && (hashSet = isInRootTaskLocked.pendingResults) != null) {
                    hashSet.remove(weakReference);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }
}
