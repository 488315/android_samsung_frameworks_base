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
        public void create(IGameSessionController gameSessionController, CreateGameSessionRequest createGameSessionRequest, GameSessionViewHostConfiguration gameSessionViewHostConfiguration, AndroidFuture gameSessionFuture) {
            Handler.getMain().post(PooledLambda.obtainRunnable(new QuintConsumer() { // from class: android.service.games.GameSessionService$1$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.QuintConsumer
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                    ((GameSessionService) obj).doCreate((IGameSessionController) obj2, (CreateGameSessionRequest) obj3, (GameSessionViewHostConfiguration) obj4, (AndroidFuture) obj5);
                }
            }, GameSessionService.this, gameSessionController, createGameSessionRequest, gameSessionViewHostConfiguration, gameSessionFuture));
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mDisplayManager = (DisplayManager) getSystemService(DisplayManager.class);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (intent == null || !ACTION_GAME_SESSION_SERVICE.equals(intent.getAction())) {
            return null;
        }
        return this.mInterface.asBinder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doCreate(IGameSessionController gameSessionController, CreateGameSessionRequest createGameSessionRequest, GameSessionViewHostConfiguration gameSessionViewHostConfiguration, AndroidFuture<CreateGameSessionResult> createGameSessionResultFuture) {
        GameSession gameSession = onNewSession(createGameSessionRequest);
        Objects.requireNonNull(gameSession);
        Display display = this.mDisplayManager.getDisplay(gameSessionViewHostConfiguration.mDisplayId);
        if (display == null) {
            createGameSessionResultFuture.completeExceptionally(new IllegalStateException("No display found for id: " + gameSessionViewHostConfiguration.mDisplayId));
            return;
        }
        Context windowContext = createWindowContext(display, 2038, null);
        SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(windowContext, display, new InputTransferToken(), "GameSessionService");
        gameSession.attach(gameSessionController, createGameSessionRequest.getTaskId(), windowContext, surfaceControlViewHost, gameSessionViewHostConfiguration.mWidthPx, gameSessionViewHostConfiguration.mHeightPx);
        CreateGameSessionResult createGameSessionResult = new CreateGameSessionResult(gameSession.mInterface, surfaceControlViewHost.getSurfacePackage());
        createGameSessionResultFuture.complete(createGameSessionResult);
        gameSession.doCreate();
    }
}
