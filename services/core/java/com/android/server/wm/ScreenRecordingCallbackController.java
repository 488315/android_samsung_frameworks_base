package com.android.server.wm;

import android.app.ActivityOptions;
import android.media.projection.IMediaProjectionManager;
import android.media.projection.IMediaProjectionWatcherCallback;
import android.media.projection.MediaProjectionInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.view.ContentRecordingSession;
import android.window.IScreenRecordingCallback;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ScreenRecordingCallbackController {
    public WindowContainer mRecordedWC;
    public final WindowManagerService mWms;
    public final ArrayMap mCallbacks = new ArrayMap();
    public final ArrayMap mLastInvokedStateByUid = new ArrayMap();
    public boolean mWatcherCallbackRegistered = false;

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
            ScreenRecordingCallbackController.this.unregister(this.mCallback);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MediaProjectionWatcherCallback extends IMediaProjectionWatcherCallback.Stub {
        public MediaProjectionWatcherCallback() {}

        public final void onRecordingSessionSet(
                MediaProjectionInfo mediaProjectionInfo,
                ContentRecordingSession contentRecordingSession) {}

        public final void onStart(MediaProjectionInfo mediaProjectionInfo) {
            ScreenRecordingCallbackController screenRecordingCallbackController =
                    ScreenRecordingCallbackController.this;
            WindowManagerGlobalLock windowManagerGlobalLock =
                    screenRecordingCallbackController.mWms.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityOptions.LaunchCookie launchCookie =
                            mediaProjectionInfo.getLaunchCookie();
                    WindowManagerService windowManagerService =
                            screenRecordingCallbackController.mWms;
                    if (launchCookie == null) {
                        screenRecordingCallbackController.mRecordedWC =
                                windowManagerService.mRoot.mDefaultDisplay;
                    } else {
                        screenRecordingCallbackController.mRecordedWC =
                                windowManagerService.mRoot.getActivity(
                                                new ScreenRecordingCallbackController$$ExternalSyntheticLambda1(
                                                        mediaProjectionInfo))
                                        .task;
                    }
                    ArraySet arraySet = new ArraySet();
                    WindowContainer windowContainer = screenRecordingCallbackController.mRecordedWC;
                    if (windowContainer != null) {
                        windowContainer.forAllActivities(
                                (Consumer)
                                        new ScreenRecordingCallbackController$$ExternalSyntheticLambda3(
                                                screenRecordingCallbackController, arraySet),
                                true);
                    }
                    screenRecordingCallbackController.dispatchCallbacks(arraySet, true);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        public final void onStop(MediaProjectionInfo mediaProjectionInfo) {
            ScreenRecordingCallbackController screenRecordingCallbackController =
                    ScreenRecordingCallbackController.this;
            WindowManagerGlobalLock windowManagerGlobalLock =
                    screenRecordingCallbackController.mWms.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ArraySet arraySet = new ArraySet();
                    WindowContainer windowContainer = screenRecordingCallbackController.mRecordedWC;
                    if (windowContainer != null) {
                        windowContainer.forAllActivities(
                                (Consumer)
                                        new ScreenRecordingCallbackController$$ExternalSyntheticLambda3(
                                                screenRecordingCallbackController, arraySet),
                                true);
                    }
                    screenRecordingCallbackController.dispatchCallbacks(arraySet, false);
                    screenRecordingCallbackController.mRecordedWC = null;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    public ScreenRecordingCallbackController(WindowManagerService windowManagerService) {
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
                                 // com.android.server.wm.ScreenRecordingCallbackController$$ExternalSyntheticLambda2
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

    public final void dump(PrintWriter printWriter) {
        printWriter.format("ScreenRecordingCallbackController:\n", new Object[0]);
        printWriter.format("  Registered callbacks:\n", new Object[0]);
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            printWriter.format(
                    "    callback=%s uid=%s\n",
                    this.mCallbacks.keyAt(i),
                    Integer.valueOf(((Callback) this.mCallbacks.valueAt(i)).mUid));
        }
        printWriter.format("  Last invoked states:\n", new Object[0]);
        for (int i2 = 0; i2 < this.mLastInvokedStateByUid.size(); i2++) {
            printWriter.format(
                    "    uid=%s isVisibleInScreenRecording=%s\n",
                    this.mLastInvokedStateByUid.keyAt(i2), this.mLastInvokedStateByUid.valueAt(i2));
        }
    }

    public final void ensureMediaProjectionWatcherCallbackRegistered() {
        if (this.mWatcherCallbackRegistered) {
            return;
        }
        IMediaProjectionManager asInterface =
                IMediaProjectionManager.Stub.asInterface(
                        ServiceManager.getService("media_projection"));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        MediaProjectionInfo mediaProjectionInfo = null;
        try {
            try {
                mediaProjectionInfo = asInterface.addCallback(new MediaProjectionWatcherCallback());
                this.mWatcherCallbackRegistered = true;
            } catch (RemoteException unused) {
                if (ProtoLogImpl_54989576.Cache.WM_ERROR_enabled[4]) {
                    ProtoLogImpl_54989576.e(
                            ProtoLogGroup.WM_ERROR,
                            4666728330189027178L,
                            0,
                            "Failed to register MediaProjectionWatcherCallback",
                            null);
                }
            }
            if (mediaProjectionInfo != null) {
                ActivityOptions.LaunchCookie launchCookie = mediaProjectionInfo.getLaunchCookie();
                WindowManagerService windowManagerService = this.mWms;
                if (launchCookie == null) {
                    this.mRecordedWC = windowManagerService.mRoot.mDefaultDisplay;
                } else {
                    this.mRecordedWC =
                            windowManagerService.mRoot.getActivity(
                                            new ScreenRecordingCallbackController$$ExternalSyntheticLambda1(
                                                    mediaProjectionInfo))
                                    .task;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
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
