package com.android.server.wm;

import android.os.RemoteCallbackList;
import android.util.IntArray;
import android.view.IDisplayWindowListener;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DisplayWindowListenerController {
    public final RemoteCallbackList mDisplayListeners = new RemoteCallbackList();
    public final WindowManagerService mService;

    public DisplayWindowListenerController(WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
    }

    public final int[] registerListener(IDisplayWindowListener iDisplayWindowListener) {
        int[] array;
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mDisplayListeners.register(iDisplayWindowListener);
                final IntArray intArray = new IntArray();
                this.mService.mAtmService.mRootWindowContainer.forAllDisplays(
                        new Consumer() { // from class:
                                         // com.android.server.wm.DisplayWindowListenerController$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                intArray.add(((DisplayContent) obj).mDisplayId);
                            }
                        });
                array = intArray.toArray();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        return array;
    }
}
