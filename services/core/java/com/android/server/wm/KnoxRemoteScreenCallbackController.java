package com.android.server.wm;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.window.IScreenRecordingCallback;

import com.samsung.android.knox.remotecontrol.IRemoteInjection;
import com.samsung.android.knox.remotecontrol.IRemoteScreenWatcherCallback;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class KnoxRemoteScreenCallbackController {
    public DisplayContent mRecordedWC;
    public IRemoteInjection mRemoteService;
    public final WindowManagerService mWms;
    public final ArrayMap mCallbacks = new ArrayMap();
    public final ArrayMap mLastInvokedStateByUid = new ArrayMap();
    public boolean mRemoteScreenWatcherCallbackRegistered = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Callback implements IBinder.DeathRecipient {
        public final IScreenRecordingCallback mCallback;
        public final int mUid;

        public Callback(IScreenRecordingCallback iScreenRecordingCallback, int i) {
            this.mCallback = iScreenRecordingCallback;
            this.mUid = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            KnoxRemoteScreenCallbackController.this.unregister(this.mCallback);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RemoteScreenWatcherCallback extends IRemoteScreenWatcherCallback.Stub {
        public RemoteScreenWatcherCallback() {}

        public final void onRemoteScreenStart() {
            KnoxRemoteScreenCallbackController knoxRemoteScreenCallbackController =
                    KnoxRemoteScreenCallbackController.this;
            WindowManagerGlobalLock windowManagerGlobalLock =
                    knoxRemoteScreenCallbackController.mWms.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    knoxRemoteScreenCallbackController.mRecordedWC =
                            knoxRemoteScreenCallbackController.mWms.mRoot.mDefaultDisplay;
                    ArraySet arraySet = new ArraySet();
                    DisplayContent displayContent = knoxRemoteScreenCallbackController.mRecordedWC;
                    if (displayContent != null) {
                        displayContent.forAllActivities(
                                (Consumer)
                                        new KnoxRemoteScreenCallbackController$$ExternalSyntheticLambda2(
                                                knoxRemoteScreenCallbackController, arraySet),
                                true);
                    }
                    knoxRemoteScreenCallbackController.dispatchCallbacks(arraySet, true);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        public final void onRemoteScreenStop() {
            KnoxRemoteScreenCallbackController knoxRemoteScreenCallbackController =
                    KnoxRemoteScreenCallbackController.this;
            WindowManagerGlobalLock windowManagerGlobalLock =
                    knoxRemoteScreenCallbackController.mWms.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ArraySet arraySet = new ArraySet();
                    DisplayContent displayContent = knoxRemoteScreenCallbackController.mRecordedWC;
                    if (displayContent != null) {
                        displayContent.forAllActivities(
                                (Consumer)
                                        new KnoxRemoteScreenCallbackController$$ExternalSyntheticLambda2(
                                                knoxRemoteScreenCallbackController, arraySet),
                                true);
                    }
                    knoxRemoteScreenCallbackController.dispatchCallbacks(arraySet, false);
                    knoxRemoteScreenCallbackController.mRecordedWC = null;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    public KnoxRemoteScreenCallbackController(WindowManagerService windowManagerService) {
        this.mWms = windowManagerService;
    }

    public final void dispatchCallbacks(ArraySet arraySet, final boolean z) {
        if (arraySet.isEmpty()) {
            return;
        }
        for (int i = 0; i < arraySet.size(); i++) {
            this.mLastInvokedStateByUid.put((Integer) arraySet.valueAt(i), Boolean.valueOf(z));
        }
        final ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            if (arraySet.contains(Integer.valueOf(((Callback) this.mCallbacks.valueAt(i2)).mUid))) {
                arrayList.add(((Callback) this.mCallbacks.valueAt(i2)).mCallback);
            }
        }
        this.mWms.mH.post(
                new Runnable() { // from class:
                                 // com.android.server.wm.KnoxRemoteScreenCallbackController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ArrayList arrayList2 = arrayList;
                        boolean z2 = z;
                        for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                            try {
                                ((IScreenRecordingCallback) arrayList2.get(i3))
                                        .onScreenRecordingStateChanged(z2);
                            } catch (RemoteException unused) {
                            }
                        }
                    }
                });
    }

    public final void unregister(IScreenRecordingCallback iScreenRecordingCallback) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mWms.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                IBinder asBinder = iScreenRecordingCallback.asBinder();
                Callback callback = (Callback) this.mCallbacks.remove(asBinder);
                if (callback == null) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                int i = 0;
                asBinder.unlinkToDeath(callback, 0);
                while (true) {
                    if (i >= this.mCallbacks.size()) {
                        this.mLastInvokedStateByUid.remove(Integer.valueOf(callback.mUid));
                        break;
                    } else if (((Callback) this.mCallbacks.valueAt(i)).mUid == callback.mUid) {
                        break;
                    } else {
                        i++;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }
}
