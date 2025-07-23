package android.window;

import android.app.ActivityTaskManager;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.util.Singleton;
import android.view.SurfaceControl;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes5.dex */
public class WindowOrganizer {
    private static final Singleton<IWindowOrganizerController> IWindowOrganizerControllerSingleton = new Singleton<IWindowOrganizerController>() { // from class: android.window.WindowOrganizer.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public IWindowOrganizerController create() {
            try {
                return ActivityTaskManager.getService().getWindowOrganizerController();
            } catch (RemoteException unused) {
                return null;
            }
        }
    };
    private static final String TAG = "WindowOrganizer";

    public void applyTransaction(WindowContainerTransaction windowContainerTransaction) {
        try {
            if (windowContainerTransaction.isEmpty()) {
                return;
            }
            getWindowOrganizerController().applyTransaction(windowContainerTransaction);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int applySyncTransaction(WindowContainerTransaction windowContainerTransaction, WindowContainerTransactionCallback windowContainerTransactionCallback) {
        try {
            if (CoreRune.FW_SHELL_TRANSITION_LOG) {
                StringBuilder sb = new StringBuilder("applySyncTransaction, hasCallback=");
                sb.append(windowContainerTransactionCallback != null);
                sb.append(", t=");
                sb.append(windowContainerTransaction);
                sb.append(", caller=");
                sb.append(Debug.getCallers(3));
                Log.i(TAG, sb.toString());
            }
            return getWindowOrganizerController().applySyncTransaction(windowContainerTransaction, windowContainerTransactionCallback.mInterface);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public IBinder startNewTransition(int i, WindowContainerTransaction windowContainerTransaction) {
        try {
            if (CoreRune.FW_SHELL_TRANSITION_LOG) {
                Log.i(TAG, "startNewTransition, type=" + i + ", t=" + windowContainerTransaction + ", caller=" + Debug.getCallers(5));
            }
            return getWindowOrganizerController().startNewTransition(i, windowContainerTransaction);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startTransition(IBinder iBinder, WindowContainerTransaction windowContainerTransaction) {
        try {
            getWindowOrganizerController().startTransition(iBinder, windowContainerTransaction);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void finishTransition(IBinder iBinder, WindowContainerTransaction windowContainerTransaction) {
        try {
            getWindowOrganizerController().finishTransition(iBinder, windowContainerTransaction);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void finishAllTransitions(IBinder iBinder, WindowContainerTransaction windowContainerTransaction, WindowContainerTransaction windowContainerTransaction2) {
        try {
            getWindowOrganizerController().finishAllTransitions(iBinder, windowContainerTransaction, windowContainerTransaction2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerTransitionPlayer(ITransitionPlayer iTransitionPlayer) {
        try {
            getWindowOrganizerController().registerTransitionPlayer(iTransitionPlayer);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterTransitionPlayer(ITransitionPlayer iTransitionPlayer) {
        try {
            getWindowOrganizerController().unregisterTransitionPlayer(iTransitionPlayer);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static ITransitionMetricsReporter getTransitionMetricsReporter() {
        try {
            return getWindowOrganizerController().getTransitionMetricsReporter();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shareTransactionQueue() {
        try {
            IBinder applyToken = getWindowOrganizerController().getApplyToken();
            if (applyToken == null) {
                return false;
            }
            if (CoreRune.FW_SHELL_TRANSITION_LOG) {
                Log.i(TAG, "shareTransactionQueue, caller=" + Debug.getCallers(3));
            }
            SurfaceControl.Transaction.setDefaultApplyToken(applyToken);
            return true;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static IWindowOrganizerController getWindowOrganizerController() {
        return IWindowOrganizerControllerSingleton.get();
    }
}
