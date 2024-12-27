package com.android.server.wm;

import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import android.view.IRotationWatcher;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RotationWatcherController {
    public volatile boolean mHasProposedRotationListeners;
    public final WindowManagerService mService;
    public final ArrayList mDisplayRotationWatchers = new ArrayList();
    public final ArrayList mProposedRotationListeners = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DisplayRotationWatcher extends RotationWatcher {
        public final int mDisplayId;

        public DisplayRotationWatcher(
                WindowManagerService windowManagerService,
                IRotationWatcher iRotationWatcher,
                int i) {
            super(windowManagerService, iRotationWatcher);
            this.mDisplayId = i;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProposedRotationListener extends RotationWatcher {
        public final IBinder mToken;

        public ProposedRotationListener(
                WindowManagerService windowManagerService,
                IRotationWatcher iRotationWatcher,
                IBinder iBinder) {
            super(windowManagerService, iRotationWatcher);
            this.mToken = iBinder;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class RotationWatcher implements IBinder.DeathRecipient {
        public final int mOwnerUid = Binder.getCallingUid();
        public final IRotationWatcher mWatcher;
        public final WindowManagerService mWms;

        public RotationWatcher(
                WindowManagerService windowManagerService, IRotationWatcher iRotationWatcher) {
            this.mWms = windowManagerService;
            this.mWatcher = iRotationWatcher;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            this.mWms.removeRotationWatcher(this.mWatcher);
        }
    }

    public RotationWatcherController(WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
    }

    public static boolean unregister(IRotationWatcher iRotationWatcher, ArrayList arrayList) {
        IBinder asBinder = iRotationWatcher.asBinder();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            RotationWatcher rotationWatcher = (RotationWatcher) arrayList.get(size);
            if (asBinder == rotationWatcher.mWatcher.asBinder()) {
                arrayList.remove(size);
                rotationWatcher.mWatcher.asBinder().unlinkToDeath(rotationWatcher, 0);
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v12, types: [com.android.server.wm.WindowContainer] */
    public final void dispatchProposedRotation(int i, DisplayContent displayContent) {
        for (int size = this.mProposedRotationListeners.size() - 1; size >= 0; size--) {
            ProposedRotationListener proposedRotationListener =
                    (ProposedRotationListener) this.mProposedRotationListeners.get(size);
            IBinder iBinder = proposedRotationListener.mToken;
            ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
            if (forTokenLocked == null) {
                WindowContextListenerController.WindowContextListenerImpl
                        windowContextListenerImpl =
                                (WindowContextListenerController.WindowContextListenerImpl)
                                        this.mService.mWindowContextListenerController.mListeners
                                                .get(iBinder);
                forTokenLocked =
                        windowContextListenerImpl != null
                                ? windowContextListenerImpl.mContainer
                                : null;
            }
            if (forTokenLocked == null) {
                this.mProposedRotationListeners.remove(size);
                this.mHasProposedRotationListeners = !this.mProposedRotationListeners.isEmpty();
                proposedRotationListener
                        .mWatcher
                        .asBinder()
                        .unlinkToDeath(proposedRotationListener, 0);
            } else if (forTokenLocked.mDisplayContent == displayContent) {
                try {
                    proposedRotationListener.mWatcher.onRotationChanged(i);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    public final void registerDisplayRotationWatcher(IRotationWatcher iRotationWatcher, int i) {
        IBinder asBinder = iRotationWatcher.asBinder();
        for (int size = this.mDisplayRotationWatchers.size() - 1; size >= 0; size--) {
            if (asBinder
                    == ((DisplayRotationWatcher) this.mDisplayRotationWatchers.get(size))
                            .mWatcher.asBinder()) {
                throw new IllegalArgumentException("Registering existed rotation watcher");
            }
        }
        DisplayRotationWatcher displayRotationWatcher =
                new DisplayRotationWatcher(this.mService, iRotationWatcher, i);
        ArrayList arrayList = this.mDisplayRotationWatchers;
        try {
            asBinder.linkToDeath(displayRotationWatcher, 0);
            arrayList.add(displayRotationWatcher);
        } catch (RemoteException unused) {
        }
    }

    public final void registerProposedRotationListener(
            IBinder iBinder, IRotationWatcher iRotationWatcher) {
        IBinder asBinder = iRotationWatcher.asBinder();
        for (int size = this.mProposedRotationListeners.size() - 1; size >= 0; size--) {
            ProposedRotationListener proposedRotationListener =
                    (ProposedRotationListener) this.mProposedRotationListeners.get(size);
            if (iBinder == proposedRotationListener.mToken
                    || asBinder == proposedRotationListener.mWatcher.asBinder()) {
                Slog.w(
                        "WindowManager",
                        "Register rotation listener to a registered token, uid="
                                + Binder.getCallingUid());
                return;
            }
        }
        ProposedRotationListener proposedRotationListener2 =
                new ProposedRotationListener(this.mService, iRotationWatcher, iBinder);
        ArrayList arrayList = this.mProposedRotationListeners;
        try {
            asBinder.linkToDeath(proposedRotationListener2, 0);
            arrayList.add(proposedRotationListener2);
        } catch (RemoteException unused) {
        }
        this.mHasProposedRotationListeners = !this.mProposedRotationListeners.isEmpty();
    }
}
