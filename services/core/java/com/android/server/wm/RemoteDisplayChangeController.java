package com.android.server.wm;

import android.os.RemoteException;
import android.util.Slog;
import android.view.IDisplayChangeWindowCallback;
import android.window.DisplayAreaInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.server.wm.RemoteDisplayChangeController;
import com.android.server.wm.WindowManagerService;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class RemoteDisplayChangeController {
    public final DisplayContent mDisplayContent;
    public final WindowManagerService mService;
    public final Runnable mTimeoutRunnable = new Runnable() { // from class: com.android.server.wm.RemoteDisplayChangeController$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            RemoteDisplayChangeController.this.onContinueTimedOut();
        }
    };
    public final List mCallbacks = new ArrayList();

    public interface ContinueRemoteDisplayChangeCallback {
        void onContinueRemoteDisplayChange(WindowContainerTransaction windowContainerTransaction);
    }

    public RemoteDisplayChangeController(DisplayContent displayContent) {
        this.mService = displayContent.mWmService;
        this.mDisplayContent = displayContent;
    }

    public boolean isWaitingForRemoteDisplayChange() {
        return !this.mCallbacks.isEmpty();
    }

    public boolean performRemoteDisplayChange(int i, int i2, DisplayAreaInfo displayAreaInfo, ContinueRemoteDisplayChangeCallback continueRemoteDisplayChangeCallback) {
        if (this.mService.mDisplayChangeController == null) {
            return false;
        }
        this.mCallbacks.add(continueRemoteDisplayChangeCallback);
        if (displayAreaInfo != null && ProtoLogCache.WM_DEBUG_CONFIGURATION_enabled) {
            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_CONFIGURATION, 1393721079, 85, (String) null, new Object[]{Long.valueOf(i), Long.valueOf(displayAreaInfo.configuration.windowConfiguration.getMaxBounds().width()), Long.valueOf(displayAreaInfo.configuration.windowConfiguration.getMaxBounds().height()), Long.valueOf(i2)});
        }
        IDisplayChangeWindowCallback createCallback = createCallback(continueRemoteDisplayChangeCallback);
        try {
            this.mService.f1749mH.removeCallbacks(this.mTimeoutRunnable);
            this.mService.f1749mH.postDelayed(this.mTimeoutRunnable, 800L);
            this.mService.mDisplayChangeController.onDisplayChange(this.mDisplayContent.mDisplayId, i, i2, displayAreaInfo, createCallback);
            return true;
        } catch (RemoteException e) {
            Slog.e("RemoteDisplayChangeController", "Exception while dispatching remote display-change", e);
            this.mCallbacks.remove(continueRemoteDisplayChangeCallback);
            return false;
        }
    }

    public final void onContinueTimedOut() {
        Slog.e("RemoteDisplayChangeController", "RemoteDisplayChange timed-out, UI might get messed-up after this.");
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            for (int i = 0; i < this.mCallbacks.size(); i++) {
                try {
                    ((ContinueRemoteDisplayChangeCallback) this.mCallbacks.get(i)).onContinueRemoteDisplayChange(null);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            this.mCallbacks.clear();
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        onCompleted();
    }

    public final void onCompleted() {
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent.mWaitingForConfig) {
            displayContent.sendNewConfiguration();
        }
    }

    public final void continueDisplayChange(ContinueRemoteDisplayChangeCallback continueRemoteDisplayChangeCallback, WindowContainerTransaction windowContainerTransaction) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                int indexOf = this.mCallbacks.indexOf(continueRemoteDisplayChangeCallback);
                if (indexOf < 0) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                for (int i = 0; i < indexOf; i++) {
                    ((ContinueRemoteDisplayChangeCallback) this.mCallbacks.get(i)).onContinueRemoteDisplayChange(null);
                }
                this.mCallbacks.subList(0, indexOf + 1).clear();
                boolean isEmpty = this.mCallbacks.isEmpty();
                if (isEmpty) {
                    this.mService.f1749mH.removeCallbacks(this.mTimeoutRunnable);
                }
                continueRemoteDisplayChangeCallback.onContinueRemoteDisplayChange(windowContainerTransaction);
                if (isEmpty) {
                    onCompleted();
                }
                WindowManagerService.resetPriorityAfterLockedSection();
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* renamed from: com.android.server.wm.RemoteDisplayChangeController$1 */
    public class C29641 extends IDisplayChangeWindowCallback.Stub {
        public final /* synthetic */ ContinueRemoteDisplayChangeCallback val$callback;

        public C29641(ContinueRemoteDisplayChangeCallback continueRemoteDisplayChangeCallback) {
            this.val$callback = continueRemoteDisplayChangeCallback;
        }

        public void continueDisplayChange(final WindowContainerTransaction windowContainerTransaction) {
            WindowManagerGlobalLock windowManagerGlobalLock = RemoteDisplayChangeController.this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (!RemoteDisplayChangeController.this.mCallbacks.contains(this.val$callback)) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    WindowManagerService.HandlerC2998H handlerC2998H = RemoteDisplayChangeController.this.mService.f1749mH;
                    final ContinueRemoteDisplayChangeCallback continueRemoteDisplayChangeCallback = this.val$callback;
                    handlerC2998H.post(new Runnable() { // from class: com.android.server.wm.RemoteDisplayChangeController$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            RemoteDisplayChangeController.C29641.this.lambda$continueDisplayChange$0(continueRemoteDisplayChangeCallback, windowContainerTransaction);
                        }
                    });
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$continueDisplayChange$0(ContinueRemoteDisplayChangeCallback continueRemoteDisplayChangeCallback, WindowContainerTransaction windowContainerTransaction) {
            RemoteDisplayChangeController.this.continueDisplayChange(continueRemoteDisplayChangeCallback, windowContainerTransaction);
        }
    }

    public final IDisplayChangeWindowCallback createCallback(ContinueRemoteDisplayChangeCallback continueRemoteDisplayChangeCallback) {
        return new C29641(continueRemoteDisplayChangeCallback);
    }
}
