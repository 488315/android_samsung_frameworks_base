package android.service.dreams;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.WindowManager;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public abstract class DreamOverlayService extends Service {
  private static final boolean DEBUG = false;
  private static final String TAG = "DreamOverlayService";
  private OverlayClient mCurrentClient;
  private IDreamOverlay mDreamOverlay =
      new IDreamOverlay.Stub() { // from class: android.service.dreams.DreamOverlayService.1
        @Override // android.service.dreams.IDreamOverlay
        public void getClient(IDreamOverlayClientCallback callback) {
          try {
            callback.onDreamOverlayClient(new OverlayClient(DreamOverlayService.this));
          } catch (RemoteException e) {
            Log.m97e(DreamOverlayService.TAG, "could not send client to callback", e);
          }
        }
      };
  private Executor mExecutor;

  public abstract void onStartDream(WindowManager.LayoutParams layoutParams);

  /* JADX INFO: Access modifiers changed from: private */
  static class OverlayClient extends IDreamOverlayClient.Stub {
    private ComponentName mDreamComponent;
    IDreamOverlayCallback mDreamOverlayCallback;
    private final DreamOverlayService mService;
    private boolean mShowComplications;

    OverlayClient(DreamOverlayService service) {
      this.mService = service;
    }

    @Override // android.service.dreams.IDreamOverlayClient
    public void startDream(
        WindowManager.LayoutParams params,
        IDreamOverlayCallback callback,
        String dreamComponent,
        boolean shouldShowComplications)
        throws RemoteException {
      this.mDreamComponent = ComponentName.unflattenFromString(dreamComponent);
      this.mShowComplications = shouldShowComplications;
      this.mDreamOverlayCallback = callback;
      this.mService.startDream(this, params);
    }

    @Override // android.service.dreams.IDreamOverlayClient
    public void wakeUp() {
      this.mService.wakeUp(this);
    }

    @Override // android.service.dreams.IDreamOverlayClient
    public void endDream() {
      this.mService.endDream(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onExitRequested() {
      try {
        this.mDreamOverlayCallback.onExitRequested();
      } catch (RemoteException e) {
        Log.m96e(DreamOverlayService.TAG, "Could not request exit:" + e);
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldShowComplications() {
      return this.mShowComplications;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ComponentName getComponent() {
      return this.mDreamComponent;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void startDream(final OverlayClient client, final WindowManager.LayoutParams params) {
    this.mExecutor.execute(
        new Runnable() { // from class:
                         // android.service.dreams.DreamOverlayService$$ExternalSyntheticLambda2
          @Override // java.lang.Runnable
          public final void run() {
            DreamOverlayService.this.lambda$startDream$0(client, params);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$startDream$0(
      OverlayClient client, WindowManager.LayoutParams params) {
    lambda$endDream$1(this.mCurrentClient);
    this.mCurrentClient = client;
    onStartDream(params);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void endDream(final OverlayClient client) {
    this.mExecutor.execute(
        new Runnable() { // from class:
                         // android.service.dreams.DreamOverlayService$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            DreamOverlayService.this.lambda$endDream$1(client);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  /* renamed from: endDreamInternal, reason: merged with bridge method [inline-methods] */
  public void lambda$endDream$1(OverlayClient client) {
    if (client == null || client != this.mCurrentClient) {
      return;
    }
    onEndDream();
    this.mCurrentClient = null;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void wakeUp(final OverlayClient client) {
    this.mExecutor.execute(
        new Runnable() { // from class:
                         // android.service.dreams.DreamOverlayService$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            DreamOverlayService.this.lambda$wakeUp$2(client);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$wakeUp$2(OverlayClient client) {
    if (this.mCurrentClient != client) {
      return;
    }
    onWakeUp();
  }

  public DreamOverlayService() {}

  public DreamOverlayService(Executor executor) {
    this.mExecutor = executor;
  }

  @Override // android.app.Service
  public void onCreate() {
    super.onCreate();
    if (this.mExecutor == null) {
      this.mExecutor = getMainExecutor();
    }
  }

  @Override // android.app.Service
  public final IBinder onBind(Intent intent) {
    return this.mDreamOverlay.asBinder();
  }

  public void onWakeUp() {}

  public void onEndDream() {}

  public final void requestExit() {
    OverlayClient overlayClient = this.mCurrentClient;
    if (overlayClient == null) {
      throw new IllegalStateException("requested exit with no dream present");
    }
    overlayClient.onExitRequested();
  }

  public final boolean shouldShowComplications() {
    OverlayClient overlayClient = this.mCurrentClient;
    if (overlayClient == null) {
      throw new IllegalStateException("requested if should show complication when no dream active");
    }
    return overlayClient.shouldShowComplications();
  }

  public final ComponentName getDreamComponent() {
    OverlayClient overlayClient = this.mCurrentClient;
    if (overlayClient == null) {
      throw new IllegalStateException("requested dream component when no dream active");
    }
    return overlayClient.getComponent();
  }
}
