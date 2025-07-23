package android.service.games;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.IBinder;
import android.service.games.IGameSessionService;
import android.view.Display;
import android.view.SurfaceControlViewHost;
import android.window.InputTransferToken;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.function.QuintConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public abstract class GameSessionService extends Service {
    public static final String ACTION_GAME_SESSION_SERVICE = "android.service.games.action.GAME_SESSION_SERVICE";
    private DisplayManager mDisplayManager;
    private final IGameSessionService mInterface = new AnonymousClass1();

    public abstract GameSession onNewSession(CreateGameSessionRequest createGameSessionRequest);

    /* renamed from: android.service.games.GameSessionService$1, reason: invalid class name */
    class AnonymousClass1 extends IGameSessionService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.games.IGameSessionService
        public void create(IGameSessionController iGameSessionController, CreateGameSessionRequest createGameSessionRequest, GameSessionViewHostConfiguration gameSessionViewHostConfiguration, AndroidFuture androidFuture) {
            Handler.getMain().post(PooledLambda.obtainRunnable(new QuintConsumer() { // from class: android.service.games.GameSessionService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuintConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                    ((GameSessionService) obj).doCreate((IGameSessionController) obj2, (CreateGameSessionRequest) obj3, (GameSessionViewHostConfiguration) obj4, (AndroidFuture) obj5);
                }
            }, GameSessionService.this, iGameSessionController, createGameSessionRequest, gameSessionViewHostConfiguration, androidFuture));
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mDisplayManager = (DisplayManager) getSystemService(DisplayManager.class);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (intent != null && ACTION_GAME_SESSION_SERVICE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doCreate(IGameSessionController iGameSessionController, CreateGameSessionRequest createGameSessionRequest, GameSessionViewHostConfiguration gameSessionViewHostConfiguration, AndroidFuture<CreateGameSessionResult> androidFuture) {
        GameSession onNewSession = onNewSession(createGameSessionRequest);
        Objects.requireNonNull(onNewSession);
        Display display = this.mDisplayManager.getDisplay(gameSessionViewHostConfiguration.mDisplayId);
        if (display == null) {
            androidFuture.completeExceptionally(new IllegalStateException("No display found for id: " + gameSessionViewHostConfiguration.mDisplayId));
        } else {
            Context createWindowContext = createWindowContext(display, 2038, null);
            SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(createWindowContext, display, new InputTransferToken(), "GameSessionService");
            onNewSession.attach(iGameSessionController, createGameSessionRequest.getTaskId(), createWindowContext, surfaceControlViewHost, gameSessionViewHostConfiguration.mWidthPx, gameSessionViewHostConfiguration.mHeightPx);
            androidFuture.complete(new CreateGameSessionResult(onNewSession.mInterface, surfaceControlViewHost.getSurfacePackage()));
            onNewSession.doCreate();
        }
    }
}
