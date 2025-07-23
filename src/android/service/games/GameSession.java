package android.service.games;

import android.annotation.SystemApi;
import android.app.ActivityTaskManager;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.games.GameSession;
import android.service.games.IGameSession;
import android.util.Slog;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.util.function.pooled.PooledLambda;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SystemApi
/* loaded from: classes3.dex */
public abstract class GameSession {
    private static final boolean DEBUG = false;
    private static final String TAG = "GameSession";
    private Context mContext;
    private IGameSessionController mGameSessionController;
    private GameSessionRootView mGameSessionRootView;
    private SurfaceControlViewHost mSurfaceControlViewHost;
    private int mTaskId;
    final IGameSession mInterface = new AnonymousClass1();
    private LifecycleState mLifecycleState = LifecycleState.INITIALIZED;
    private boolean mAreTransientInsetsVisibleDueToGesture = false;

    public enum LifecycleState {
        INITIALIZED,
        CREATED,
        TASK_FOCUSED,
        TASK_UNFOCUSED,
        DESTROYED
    }

    public interface ScreenshotCallback {
        public static final int ERROR_TAKE_SCREENSHOT_INTERNAL_ERROR = 0;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ScreenshotFailureStatus {
        }

        void onFailure(int i);

        void onSuccess();
    }

    public void onCreate() {
    }

    public void onDestroy() {
    }

    public void onGameTaskFocusChanged(boolean z) {
    }

    public void onTransientSystemBarVisibilityFromRevealGestureChanged(boolean z) {
    }

    /* renamed from: android.service.games.GameSession$1, reason: invalid class name */
    class AnonymousClass1 extends IGameSession.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.games.IGameSession
        public void onDestroyed() {
            Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(new Consumer() { // from class: android.service.games.GameSession$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((GameSession) obj).doDestroy();
                }
            }, GameSession.this));
        }

        @Override // android.service.games.IGameSession
        public void onTransientSystemBarVisibilityFromRevealGestureChanged(boolean z) {
            Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.games.GameSession$1$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((GameSession) obj).dispatchTransientSystemBarVisibilityFromRevealGestureChanged(((Boolean) obj2).booleanValue());
                }
            }, GameSession.this, Boolean.valueOf(z)));
        }

        @Override // android.service.games.IGameSession
        public void onTaskFocusChanged(boolean z) {
            Handler.getMain().executeOrSendMessage(PooledLambda.obtainMessage(new BiConsumer() { // from class: android.service.games.GameSession$1$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((GameSession) obj).moveToState((GameSession.LifecycleState) obj2);
                }
            }, GameSession.this, z ? LifecycleState.TASK_FOCUSED : LifecycleState.TASK_UNFOCUSED));
        }
    }

    public void attach(IGameSessionController iGameSessionController, int i, Context context, SurfaceControlViewHost surfaceControlViewHost, int i2, int i3) {
        this.mGameSessionController = iGameSessionController;
        this.mTaskId = i;
        this.mContext = context;
        this.mSurfaceControlViewHost = surfaceControlViewHost;
        GameSessionRootView gameSessionRootView = new GameSessionRootView(context, this.mSurfaceControlViewHost);
        this.mGameSessionRootView = gameSessionRootView;
        surfaceControlViewHost.setView(gameSessionRootView, i2, i3);
    }

    void doCreate() {
        moveToState(LifecycleState.CREATED);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doDestroy() {
        this.mSurfaceControlViewHost.release();
        moveToState(LifecycleState.DESTROYED);
    }

    public void dispatchTransientSystemBarVisibilityFromRevealGestureChanged(boolean z) {
        boolean z2 = this.mAreTransientInsetsVisibleDueToGesture != z;
        this.mAreTransientInsetsVisibleDueToGesture = z;
        if (z2) {
            onTransientSystemBarVisibilityFromRevealGestureChanged(z);
        }
    }

    public void moveToState(LifecycleState lifecycleState) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("moveToState should be used only from the main thread");
        }
        LifecycleState lifecycleState2 = this.mLifecycleState;
        if (lifecycleState2 == lifecycleState) {
            return;
        }
        int ordinal = lifecycleState2.ordinal();
        if (ordinal != 0) {
            if (ordinal != 1) {
                if (ordinal != 2) {
                    if (ordinal != 3) {
                        if (ordinal == 4) {
                            return;
                        }
                    } else if (lifecycleState == LifecycleState.TASK_FOCUSED) {
                        onGameTaskFocusChanged(true);
                    } else if (lifecycleState != LifecycleState.DESTROYED) {
                        return;
                    } else {
                        onDestroy();
                    }
                } else if (lifecycleState == LifecycleState.TASK_UNFOCUSED) {
                    onGameTaskFocusChanged(false);
                } else {
                    if (lifecycleState != LifecycleState.DESTROYED) {
                        return;
                    }
                    onGameTaskFocusChanged(false);
                    onDestroy();
                }
            } else if (lifecycleState == LifecycleState.TASK_FOCUSED) {
                onGameTaskFocusChanged(true);
            } else if (lifecycleState != LifecycleState.DESTROYED) {
                return;
            } else {
                onDestroy();
            }
        } else if (lifecycleState == LifecycleState.CREATED) {
            onCreate();
        } else {
            if (lifecycleState != LifecycleState.DESTROYED) {
                return;
            }
            onCreate();
            onDestroy();
        }
        this.mLifecycleState = lifecycleState;
    }

    public void setTaskOverlayView(View view, ViewGroup.LayoutParams layoutParams) {
        this.mGameSessionRootView.removeAllViews();
        this.mGameSessionRootView.addView(view, layoutParams);
    }

    public final boolean restartGame() {
        try {
            this.mGameSessionController.restartGame(this.mTaskId);
            return true;
        } catch (RemoteException e) {
            Slog.w(TAG, "Failed to restart game", e);
            return false;
        }
    }

    private static final class GameSessionRootView extends FrameLayout {
        private final SurfaceControlViewHost mSurfaceControlViewHost;

        GameSessionRootView(Context context, SurfaceControlViewHost surfaceControlViewHost) {
            super(context);
            this.mSurfaceControlViewHost = surfaceControlViewHost;
        }

        @Override // android.view.View
        protected void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            Rect bounds = configuration.windowConfiguration.getBounds();
            this.mSurfaceControlViewHost.relayout(bounds.width(), bounds.height());
        }
    }

    public void takeScreenshot(Executor executor, final ScreenshotCallback screenshotCallback) {
        if (this.mGameSessionController == null) {
            throw new IllegalStateException("Can not call before onCreate()");
        }
        AndroidFuture whenCompleteAsync = new AndroidFuture().whenCompleteAsync(new BiConsumer() { // from class: android.service.games.GameSession$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                GameSession.this.lambda$takeScreenshot$0(screenshotCallback, (GameScreenshotResult) obj, (Throwable) obj2);
            }
        }, executor);
        try {
            this.mGameSessionController.takeScreenshot(this.mTaskId, whenCompleteAsync);
        } catch (RemoteException e) {
            whenCompleteAsync.completeExceptionally(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleScreenshotResult, reason: merged with bridge method [inline-methods] */
    public void lambda$takeScreenshot$0(ScreenshotCallback screenshotCallback, GameScreenshotResult gameScreenshotResult, Throwable th) {
        if (th != null) {
            Slog.w(TAG, th.getMessage(), th.getCause());
            screenshotCallback.onFailure(0);
            return;
        }
        int status = gameScreenshotResult.getStatus();
        if (status == 0) {
            screenshotCallback.onSuccess();
        } else {
            if (status != 1) {
                return;
            }
            Slog.w(TAG, "Error taking screenshot");
            screenshotCallback.onFailure(0);
        }
    }

    public final void startActivityFromGameSessionForResult(Intent intent, Bundle bundle, Executor executor, final GameSessionActivityCallback gameSessionActivityCallback) {
        Objects.requireNonNull(intent);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(gameSessionActivityCallback);
        Intent createIntent = GameSessionTrampolineActivity.createIntent(intent, bundle, new AndroidFuture().whenCompleteAsync(new BiConsumer() { // from class: android.service.games.GameSession$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                GameSession.lambda$startActivityFromGameSessionForResult$1(GameSessionActivityCallback.this, (GameSessionActivityResult) obj, (Throwable) obj2);
            }
        }, executor));
        createIntent.collectExtraIntentKeys();
        try {
            Instrumentation.checkStartActivityResult(ActivityTaskManager.getService().startActivityFromGameSession(this.mContext.getIApplicationThread(), this.mContext.getPackageName(), TAG, Binder.getCallingPid(), Binder.getCallingUid(), createIntent, this.mTaskId, UserHandle.myUserId()), createIntent);
        } catch (Throwable th) {
            executor.execute(new Runnable() { // from class: android.service.games.GameSession$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    GameSessionActivityCallback.this.onActivityStartFailed(th);
                }
            });
        }
    }

    static /* synthetic */ void lambda$startActivityFromGameSessionForResult$1(GameSessionActivityCallback gameSessionActivityCallback, GameSessionActivityResult gameSessionActivityResult, Throwable th) {
        if (th != null) {
            gameSessionActivityCallback.onActivityStartFailed(th);
        } else {
            gameSessionActivityCallback.onActivityResult(gameSessionActivityResult.getResultCode(), gameSessionActivityResult.getData());
        }
    }
}
