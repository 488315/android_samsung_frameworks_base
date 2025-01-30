package com.android.server.wm;

import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;
import android.view.IRotationWatcher;
import java.io.PrintWriter;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class RotationWatcherController {
    public volatile boolean mHasProposedRotationListeners;
    public final WindowManagerService mService;
    public final ArrayList mDisplayRotationWatchers = new ArrayList();
    public final ArrayList mProposedRotationListeners = new ArrayList();

    public RotationWatcherController(WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
    }

    public void registerDisplayRotationWatcher(IRotationWatcher iRotationWatcher, int i) {
        IBinder asBinder = iRotationWatcher.asBinder();
        for (int size = this.mDisplayRotationWatchers.size() - 1; size >= 0; size--) {
            if (asBinder == ((DisplayRotationWatcher) this.mDisplayRotationWatchers.get(size)).mWatcher.asBinder()) {
                throw new IllegalArgumentException("Registering existed rotation watcher");
            }
        }
        register(asBinder, new DisplayRotationWatcher(this.mService, iRotationWatcher, i), this.mDisplayRotationWatchers);
    }

    public void registerProposedRotationListener(IRotationWatcher iRotationWatcher, IBinder iBinder) {
        IBinder asBinder = iRotationWatcher.asBinder();
        for (int size = this.mProposedRotationListeners.size() - 1; size >= 0; size--) {
            ProposedRotationListener proposedRotationListener = (ProposedRotationListener) this.mProposedRotationListeners.get(size);
            if (iBinder == proposedRotationListener.mToken || asBinder == proposedRotationListener.mWatcher.asBinder()) {
                Slog.w(StartingSurfaceController.TAG, "Register rotation listener to a registered token, uid=" + Binder.getCallingUid());
                return;
            }
        }
        register(asBinder, new ProposedRotationListener(this.mService, iRotationWatcher, iBinder), this.mProposedRotationListeners);
        this.mHasProposedRotationListeners = !this.mProposedRotationListeners.isEmpty();
    }

    public static void register(IBinder iBinder, RotationWatcher rotationWatcher, ArrayList arrayList) {
        try {
            iBinder.linkToDeath(rotationWatcher, 0);
            arrayList.add(rotationWatcher);
        } catch (RemoteException unused) {
        }
    }

    public static boolean unregister(IRotationWatcher iRotationWatcher, ArrayList arrayList) {
        IBinder asBinder = iRotationWatcher.asBinder();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            RotationWatcher rotationWatcher = (RotationWatcher) arrayList.get(size);
            if (asBinder == rotationWatcher.mWatcher.asBinder()) {
                arrayList.remove(size);
                rotationWatcher.unlinkToDeath();
                return true;
            }
        }
        return false;
    }

    public void removeRotationWatcher(IRotationWatcher iRotationWatcher) {
        if (unregister(iRotationWatcher, this.mProposedRotationListeners)) {
            this.mHasProposedRotationListeners = !this.mProposedRotationListeners.isEmpty();
        } else {
            unregister(iRotationWatcher, this.mDisplayRotationWatchers);
        }
    }

    public void dispatchDisplayRotationChange(int i, int i2) {
        for (int size = this.mDisplayRotationWatchers.size() - 1; size >= 0; size--) {
            DisplayRotationWatcher displayRotationWatcher = (DisplayRotationWatcher) this.mDisplayRotationWatchers.get(size);
            if (displayRotationWatcher.mDisplayId == i) {
                displayRotationWatcher.notifyRotation(i2);
            }
        }
    }

    public void dispatchProposedRotation(DisplayContent displayContent, int i) {
        for (int size = this.mProposedRotationListeners.size() - 1; size >= 0; size--) {
            ProposedRotationListener proposedRotationListener = (ProposedRotationListener) this.mProposedRotationListeners.get(size);
            WindowContainer associatedWindowContainer = getAssociatedWindowContainer(proposedRotationListener.mToken);
            if (associatedWindowContainer != null) {
                if (associatedWindowContainer.mDisplayContent == displayContent) {
                    proposedRotationListener.notifyRotation(i);
                }
            } else {
                this.mProposedRotationListeners.remove(size);
                this.mHasProposedRotationListeners = !this.mProposedRotationListeners.isEmpty();
                proposedRotationListener.unlinkToDeath();
            }
        }
    }

    public boolean hasProposedRotationListeners() {
        return this.mHasProposedRotationListeners;
    }

    public WindowContainer getAssociatedWindowContainer(IBinder iBinder) {
        ActivityRecord forTokenLocked = ActivityRecord.forTokenLocked(iBinder);
        return forTokenLocked != null ? forTokenLocked : this.mService.mWindowContextListenerController.getContainer(iBinder);
    }

    public void dump(PrintWriter printWriter) {
        if (!this.mDisplayRotationWatchers.isEmpty()) {
            printWriter.print("  mDisplayRotationWatchers: [");
            for (int size = this.mDisplayRotationWatchers.size() - 1; size >= 0; size--) {
                printWriter.print(' ');
                DisplayRotationWatcher displayRotationWatcher = (DisplayRotationWatcher) this.mDisplayRotationWatchers.get(size);
                printWriter.print(displayRotationWatcher.mOwnerUid);
                printWriter.print("->");
                printWriter.print(displayRotationWatcher.mDisplayId);
            }
            printWriter.println(']');
        }
        if (this.mProposedRotationListeners.isEmpty()) {
            return;
        }
        printWriter.print("  mProposedRotationListeners: [");
        for (int size2 = this.mProposedRotationListeners.size() - 1; size2 >= 0; size2--) {
            printWriter.print(' ');
            ProposedRotationListener proposedRotationListener = (ProposedRotationListener) this.mProposedRotationListeners.get(size2);
            printWriter.print(proposedRotationListener.mOwnerUid);
            printWriter.print("->");
            printWriter.print(getAssociatedWindowContainer(proposedRotationListener.mToken));
        }
        printWriter.println(']');
    }

    public class DisplayRotationWatcher extends RotationWatcher {
        public final int mDisplayId;

        public DisplayRotationWatcher(WindowManagerService windowManagerService, IRotationWatcher iRotationWatcher, int i) {
            super(windowManagerService, iRotationWatcher);
            this.mDisplayId = i;
        }
    }

    public class ProposedRotationListener extends RotationWatcher {
        public final IBinder mToken;

        public ProposedRotationListener(WindowManagerService windowManagerService, IRotationWatcher iRotationWatcher, IBinder iBinder) {
            super(windowManagerService, iRotationWatcher);
            this.mToken = iBinder;
        }
    }

    public abstract class RotationWatcher implements IBinder.DeathRecipient {
        public final int mOwnerUid = Binder.getCallingUid();
        public final IRotationWatcher mWatcher;
        public final WindowManagerService mWms;

        public RotationWatcher(WindowManagerService windowManagerService, IRotationWatcher iRotationWatcher) {
            this.mWms = windowManagerService;
            this.mWatcher = iRotationWatcher;
        }

        public void notifyRotation(int i) {
            try {
                this.mWatcher.onRotationChanged(i);
            } catch (RemoteException unused) {
            }
        }

        public void unlinkToDeath() {
            this.mWatcher.asBinder().unlinkToDeath(this, 0);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.mWms.removeRotationWatcher(this.mWatcher);
        }
    }
}
