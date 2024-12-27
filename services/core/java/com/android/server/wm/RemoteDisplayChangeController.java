package com.android.server.wm;

import android.os.RemoteException;
import android.os.Trace;
import android.util.Slog;
import android.view.IDisplayChangeWindowCallback;
import android.window.DisplayAreaInfo;
import android.window.WindowContainerTransaction;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RemoteDisplayChangeController {
    public final DisplayContent mDisplayContent;
    public final WindowManagerService mService;
    public final RemoteDisplayChangeController$$ExternalSyntheticLambda0 mTimeoutRunnable =
            new Runnable() { // from class:
                             // com.android.server.wm.RemoteDisplayChangeController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RemoteDisplayChangeController remoteDisplayChangeController =
                            RemoteDisplayChangeController.this;
                    remoteDisplayChangeController.getClass();
                    Slog.e(
                            "RemoteDisplayChangeController",
                            "RemoteDisplayChange timed-out, UI might get messed-up after this.");
                    WindowManagerGlobalLock windowManagerGlobalLock =
                            remoteDisplayChangeController.mService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        for (int i = 0;
                                i < ((ArrayList) remoteDisplayChangeController.mCallbacks).size();
                                i++) {
                            try {
                                RemoteDisplayChangeController.ContinueRemoteDisplayChangeCallback
                                        continueRemoteDisplayChangeCallback =
                                                (RemoteDisplayChangeController
                                                                .ContinueRemoteDisplayChangeCallback)
                                                        ((ArrayList)
                                                                        remoteDisplayChangeController
                                                                                .mCallbacks)
                                                                .get(i);
                                if (i
                                        == ((ArrayList) remoteDisplayChangeController.mCallbacks)
                                                        .size()
                                                - 1) {
                                    ((ArrayList) remoteDisplayChangeController.mCallbacks).clear();
                                }
                                continueRemoteDisplayChangeCallback.onContinueRemoteDisplayChange(
                                        null);
                                if (Trace.isTagEnabled(32L)) {
                                    Trace.endAsyncSection(
                                            "RemoteDisplayChange",
                                            continueRemoteDisplayChangeCallback.hashCode());
                                }
                            } catch (Throwable th) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                        DisplayContent displayContent =
                                remoteDisplayChangeController.mDisplayContent;
                        if (displayContent.mWaitingForConfig) {
                            displayContent.sendNewConfiguration();
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            };
    public final List mCallbacks = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.wm.RemoteDisplayChangeController$1, reason: invalid class name */
    public final class AnonymousClass1 extends IDisplayChangeWindowCallback.Stub {
        public final /* synthetic */ ContinueRemoteDisplayChangeCallback val$callback;

        public AnonymousClass1(
                ContinueRemoteDisplayChangeCallback continueRemoteDisplayChangeCallback) {
            this.val$callback = continueRemoteDisplayChangeCallback;
        }

        public final void continueDisplayChange(
                final WindowContainerTransaction windowContainerTransaction) {
            WindowManagerGlobalLock windowManagerGlobalLock =
                    RemoteDisplayChangeController.this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (!((ArrayList) RemoteDisplayChangeController.this.mCallbacks)
                            .contains(this.val$callback)) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    WindowManagerService.H h = RemoteDisplayChangeController.this.mService.mH;
                    final ContinueRemoteDisplayChangeCallback continueRemoteDisplayChangeCallback =
                            this.val$callback;
                    h.post(
                            new Runnable() { // from class:
                                             // com.android.server.wm.RemoteDisplayChangeController$1$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    RemoteDisplayChangeController.AnonymousClass1 anonymousClass1 =
                                            RemoteDisplayChangeController.AnonymousClass1.this;
                                    RemoteDisplayChangeController.this.continueDisplayChange(
                                            continueRemoteDisplayChangeCallback,
                                            windowContainerTransaction);
                                }
                            });
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ContinueRemoteDisplayChangeCallback {
        void onContinueRemoteDisplayChange(WindowContainerTransaction windowContainerTransaction);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.wm.RemoteDisplayChangeController$$ExternalSyntheticLambda0] */
    public RemoteDisplayChangeController(DisplayContent displayContent) {
        this.mService = displayContent.mWmService;
        this.mDisplayContent = displayContent;
    }

    public void continueDisplayChange(
            ContinueRemoteDisplayChangeCallback continueRemoteDisplayChangeCallback,
            WindowContainerTransaction windowContainerTransaction) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int indexOf =
                        ((ArrayList) this.mCallbacks).indexOf(continueRemoteDisplayChangeCallback);
                if (indexOf < 0) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                for (int i = 0; i < indexOf; i++) {
                    ContinueRemoteDisplayChangeCallback continueRemoteDisplayChangeCallback2 =
                            (ContinueRemoteDisplayChangeCallback)
                                    ((ArrayList) this.mCallbacks).get(i);
                    continueRemoteDisplayChangeCallback2.onContinueRemoteDisplayChange(null);
                    if (Trace.isTagEnabled(32L)) {
                        Trace.endAsyncSection(
                                "RemoteDisplayChange",
                                continueRemoteDisplayChangeCallback2.hashCode());
                    }
                }
                ((ArrayList) this.mCallbacks).subList(0, indexOf + 1).clear();
                boolean isEmpty = ((ArrayList) this.mCallbacks).isEmpty();
                if (isEmpty) {
                    this.mService.mH.removeCallbacks(this.mTimeoutRunnable);
                }
                continueRemoteDisplayChangeCallback.onContinueRemoteDisplayChange(
                        windowContainerTransaction);
                if (isEmpty) {
                    DisplayContent displayContent = this.mDisplayContent;
                    if (displayContent.mWaitingForConfig) {
                        displayContent.sendNewConfiguration();
                    }
                }
                if (Trace.isTagEnabled(32L)) {
                    Trace.endAsyncSection(
                            "RemoteDisplayChange", continueRemoteDisplayChangeCallback.hashCode());
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    public final boolean isWaitingForRemoteDisplayChange() {
        return !((ArrayList) this.mCallbacks).isEmpty();
    }

    public final boolean performRemoteDisplayChange(
            int i,
            int i2,
            DisplayAreaInfo displayAreaInfo,
            ContinueRemoteDisplayChangeCallback continueRemoteDisplayChangeCallback) {
        RemoteDisplayChangeController$$ExternalSyntheticLambda0
                remoteDisplayChangeController$$ExternalSyntheticLambda0 = this.mTimeoutRunnable;
        WindowManagerService windowManagerService = this.mService;
        if (windowManagerService.mDisplayChangeController == null) {
            return false;
        }
        ((ArrayList) this.mCallbacks).add(continueRemoteDisplayChangeCallback);
        if (Trace.isTagEnabled(32L)) {
            Trace.beginAsyncSection(
                    "RemoteDisplayChange", continueRemoteDisplayChangeCallback.hashCode());
        }
        if (displayAreaInfo != null
                && ProtoLogImpl_54989576.Cache.WM_DEBUG_CONFIGURATION_enabled[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_CONFIGURATION,
                    1736084564226683342L,
                    85,
                    null,
                    Long.valueOf(i),
                    Long.valueOf(
                            displayAreaInfo
                                    .configuration
                                    .windowConfiguration
                                    .getMaxBounds()
                                    .width()),
                    Long.valueOf(
                            displayAreaInfo
                                    .configuration
                                    .windowConfiguration
                                    .getMaxBounds()
                                    .height()),
                    Long.valueOf(i2));
        }
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(continueRemoteDisplayChangeCallback);
        try {
            windowManagerService.mH.removeCallbacks(
                    remoteDisplayChangeController$$ExternalSyntheticLambda0);
            windowManagerService.mH.postDelayed(
                    remoteDisplayChangeController$$ExternalSyntheticLambda0, 800L);
            windowManagerService.mDisplayChangeController.onDisplayChange(
                    this.mDisplayContent.mDisplayId, i, i2, displayAreaInfo, anonymousClass1);
            return true;
        } catch (RemoteException e) {
            Slog.e(
                    "RemoteDisplayChangeController",
                    "Exception while dispatching remote display-change",
                    e);
            ((ArrayList) this.mCallbacks).remove(continueRemoteDisplayChangeCallback);
            return false;
        }
    }
}
