package android.service.games;

import android.annotation.SystemApi;
import android.app.IGameManagerService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.Objects;
import java.util.function.BiConsumer;

@SystemApi
/* loaded from: classes3.dex */
public class GameService extends Service {
  public static final String ACTION_GAME_SERVICE = "android.service.games.action.GAME_SERVICE";
  public static final String SERVICE_META_DATA = "android.game_service";
  private static final String TAG = "GameService";
  private IGameManagerService mGameManagerService;
  private IGameServiceController mGameServiceController;
  private final IGameService mInterface = new BinderC27161();
  private final IBinder.DeathRecipient mGameManagerServiceDeathRecipient =
      new IBinder
          .DeathRecipient() { // from class:
                              // android.service.games.GameService$$ExternalSyntheticLambda0
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
          GameService.this.lambda$new$0();
        }
      };

  /* renamed from: android.service.games.GameService$1 */
  class BinderC27161 extends IGameService.Stub {
    BinderC27161() {}

    @Override // android.service.games.IGameService
    public void connected(IGameServiceController gameServiceController) {
      Handler.getMain()
          .executeOrSendMessage(
              PooledLambda.obtainMessage(
                  new BiConsumer() { // from class:
                                     // android.service.games.GameService$1$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                      ((GameService) obj).doOnConnected((IGameServiceController) obj2);
                    }
                  },
                  GameService.this,
                  gameServiceController));
    }

    @Override // android.service.games.IGameService
    public void disconnected() {
      Handler.getMain()
          .executeOrSendMessage(
              PooledLambda.obtainMessage(
                  new GameService$$ExternalSyntheticLambda1(), GameService.this));
    }

    @Override // android.service.games.IGameService
    public void gameStarted(GameStartedEvent gameStartedEvent) {
      Handler.getMain()
          .executeOrSendMessage(
              PooledLambda.obtainMessage(
                  new BiConsumer() { // from class:
                                     // android.service.games.GameService$1$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                      ((GameService) obj).onGameStarted((GameStartedEvent) obj2);
                    }
                  },
                  GameService.this,
                  gameStartedEvent));
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0() {
    Log.m102w(TAG, "System service binder died. Shutting down");
    Handler.getMain()
        .executeOrSendMessage(
            PooledLambda.obtainMessage(new GameService$$ExternalSyntheticLambda1(), this));
  }

  @Override // android.app.Service
  public final IBinder onBind(Intent intent) {
    if (ACTION_GAME_SERVICE.equals(intent.getAction())) {
      return this.mInterface.asBinder();
    }
    return null;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void doOnConnected(IGameServiceController gameServiceController) {
    IGameManagerService asInterface =
        IGameManagerService.Stub.asInterface(ServiceManager.getService(Context.GAME_SERVICE));
    this.mGameManagerService = asInterface;
    Objects.requireNonNull(asInterface);
    try {
      this.mGameManagerService.asBinder().linkToDeath(this.mGameManagerServiceDeathRecipient, 0);
    } catch (RemoteException e) {
      Log.m102w(TAG, "Unable to link to death with system service");
    }
    this.mGameServiceController = gameServiceController;
    onConnected();
  }

  public void onConnected() {}

  public void onDisconnected() {}

  public void onGameStarted(GameStartedEvent gameStartedEvent) {}

  public final void createGameSession(int taskId) {
    IGameServiceController iGameServiceController = this.mGameServiceController;
    if (iGameServiceController == null) {
      throw new IllegalStateException("Can not call before connected()");
    }
    try {
      iGameServiceController.createGameSession(taskId);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Request for game session failed", e);
    }
  }
}
