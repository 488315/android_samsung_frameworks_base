package android.window;

import android.app.ActivityTaskManager;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.util.Singleton;
import android.view.RemoteAnimationAdapter;
import android.view.SurfaceControl;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes4.dex */
public class WindowOrganizer {
  private static final Singleton<IWindowOrganizerController> IWindowOrganizerControllerSingleton =
      new Singleton<IWindowOrganizerController>() { // from class: android.window.WindowOrganizer.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.util.Singleton
        public IWindowOrganizerController create() {
          try {
            return ActivityTaskManager.getService().getWindowOrganizerController();
          } catch (RemoteException e) {
            return null;
          }
        }
      };
  private static final String TAG = "WindowOrganizer";

  public void applyTransaction(WindowContainerTransaction t) {
    try {
      if (!t.isEmpty()) {
        getWindowOrganizerController().applyTransaction(t);
      }
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public int applySyncTransaction(
      WindowContainerTransaction t, WindowContainerTransactionCallback callback) {
    try {
      if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
        Log.m98i(
            TAG,
            "applySyncTransaction, hasCallback="
                + (callback != null)
                + ", t="
                + t
                + ", caller="
                + Debug.getCallers(3));
      }
      return getWindowOrganizerController().applySyncTransaction(t, callback.mInterface);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public IBinder startNewTransition(int type, WindowContainerTransaction t) {
    try {
      if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
        Log.m98i(
            TAG,
            "startNewTransition, type=" + type + ", t=" + t + ", caller=" + Debug.getCallers(5));
      }
      return getWindowOrganizerController().startNewTransition(type, t);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void startTransition(IBinder transitionToken, WindowContainerTransaction t) {
    try {
      getWindowOrganizerController().startTransition(transitionToken, t);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public int finishTransition(
      IBinder transitionToken,
      WindowContainerTransaction t,
      WindowContainerTransactionCallback callback) {
    try {
      return getWindowOrganizerController()
          .finishTransition(transitionToken, t, callback != null ? callback.mInterface : null);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public int startLegacyTransition(
      int type,
      RemoteAnimationAdapter adapter,
      WindowContainerTransactionCallback syncCallback,
      WindowContainerTransaction t) {
    try {
      return getWindowOrganizerController()
          .startLegacyTransition(type, adapter, syncCallback.mInterface, t);
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  public void registerTransitionPlayer(ITransitionPlayer player) {
    try {
      getWindowOrganizerController().registerTransitionPlayer(player);
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
      IBinder wmApplyToken = getWindowOrganizerController().getApplyToken();
      if (wmApplyToken == null) {
        return false;
      }
      if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LOG) {
        Log.m98i(TAG, "shareTransactionQueue, caller=" + Debug.getCallers(3));
      }
      SurfaceControl.Transaction.setDefaultApplyToken(wmApplyToken);
      return true;
    } catch (RemoteException e) {
      throw e.rethrowFromSystemServer();
    }
  }

  static IWindowOrganizerController getWindowOrganizerController() {
    return IWindowOrganizerControllerSingleton.get();
  }
}
