package com.android.server.app;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityTaskManager;
import android.app.IActivityManager;
import android.app.IActivityTaskManager;
import android.app.IProcessObserver;
import android.app.TaskStackListener;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.Rect;
import android.net.Uri;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.games.CreateGameSessionRequest;
import android.service.games.CreateGameSessionResult;
import android.service.games.GameScreenshotResult;
import android.service.games.GameSessionViewHostConfiguration;
import android.service.games.GameStartedEvent;
import android.service.games.IGameService;
import android.service.games.IGameServiceController;
import android.service.games.IGameSession;
import android.service.games.IGameSessionController;
import android.service.games.IGameSessionService;
import android.text.TextUtils;
import android.util.Slog;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.window.ScreenCapture;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.infra.ServiceConnector;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ScreenshotHelper;
import com.android.internal.util.ScreenshotRequest;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.wm.WindowManagerService;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public final class GameServiceProviderInstanceImpl implements GameServiceProviderInstance {
  public final IActivityManager mActivityManager;
  public final ActivityManagerInternal mActivityManagerInternal;
  public final IActivityTaskManager mActivityTaskManager;
  public final ActivityTaskManagerInternal mActivityTaskManagerInternal;
  public final Executor mBackgroundExecutor;
  public final Context mContext;
  public final ServiceConnector mGameServiceConnector;
  public final ServiceConnector mGameSessionServiceConnector;
  public final GameTaskInfoProvider mGameTaskInfoProvider;
  public volatile boolean mIsRunning;
  public final ScreenshotHelper mScreenshotHelper;
  public final UserHandle mUserHandle;
  public final WindowManagerInternal mWindowManagerInternal;
  public final WindowManagerService mWindowManagerService;
  public final ServiceConnector.ServiceLifecycleCallbacks mGameServiceLifecycleCallbacks =
      new ServiceConnector.ServiceLifecycleCallbacks() { // from class:
        // com.android.server.app.GameServiceProviderInstanceImpl.1
        public void onConnected(IGameService iGameService) {
          try {
            iGameService.connected(GameServiceProviderInstanceImpl.this.mGameServiceController);
          } catch (RemoteException e) {
            Slog.w("GameServiceProviderInstance", "Failed to send connected event", e);
          }
        }
      };
  public final ServiceConnector.ServiceLifecycleCallbacks mGameSessionServiceLifecycleCallbacks =
      new C06752();
  public final WindowManagerInternal.TaskSystemBarsListener mTaskSystemBarsVisibilityListener =
      new WindowManagerInternal.TaskSystemBarsListener() { // from class:
        // com.android.server.app.GameServiceProviderInstanceImpl.3
        @Override // com.android.server.wm.WindowManagerInternal.TaskSystemBarsListener
        public void onTransientSystemBarsVisibilityChanged(int i, boolean z, boolean z2) {
          GameServiceProviderInstanceImpl.this.onTransientSystemBarsVisibilityChanged(i, z, z2);
        }
      };
  public final TaskStackListener mTaskStackListener = new TaskStackListenerC06774();
  public final IProcessObserver mProcessObserver = new IProcessObserverStubC06785();
  public final IGameServiceController mGameServiceController = new C06796();
  public final IGameSessionController mGameSessionController = new C06807();
  public final Object mLock = new Object();
  public final ConcurrentHashMap mGameSessions = new ConcurrentHashMap();
  public final ConcurrentHashMap mPidToPackageMap = new ConcurrentHashMap();
  public final ConcurrentHashMap mPackageNameToProcessCountMap = new ConcurrentHashMap();

  /* renamed from: com.android.server.app.GameServiceProviderInstanceImpl$2 */
  public class C06752 implements ServiceConnector.ServiceLifecycleCallbacks {
    public C06752() {}

    public void onBinderDied() {
      GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(
          new Runnable() { // from class:
            // com.android.server.app.GameServiceProviderInstanceImpl$2$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              GameServiceProviderInstanceImpl.C06752.this.lambda$onBinderDied$0();
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBinderDied$0() {
      synchronized (GameServiceProviderInstanceImpl.this.mLock) {
        GameServiceProviderInstanceImpl.this.destroyAndClearAllGameSessionsLocked();
      }
    }
  }

  /* renamed from: com.android.server.app.GameServiceProviderInstanceImpl$4 */
  public class TaskStackListenerC06774 extends TaskStackListener {
    public TaskStackListenerC06774() {}

    public void onTaskCreated(final int i, final ComponentName componentName) {
      if (componentName == null) {
        return;
      }
      GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(
          new Runnable() { // from class:
            // com.android.server.app.GameServiceProviderInstanceImpl$4$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              GameServiceProviderInstanceImpl.TaskStackListenerC06774.this.lambda$onTaskCreated$0(
                  i, componentName);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTaskCreated$0(int i, ComponentName componentName) {
      GameServiceProviderInstanceImpl.this.onTaskCreated(i, componentName);
    }

    public void onTaskRemoved(final int i) {
      GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(
          new Runnable() { // from class:
            // com.android.server.app.GameServiceProviderInstanceImpl$4$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              GameServiceProviderInstanceImpl.TaskStackListenerC06774.this.lambda$onTaskRemoved$1(
                  i);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTaskRemoved$1(int i) {
      GameServiceProviderInstanceImpl.this.onTaskRemoved(i);
    }

    public void onTaskFocusChanged(final int i, final boolean z) {
      GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(
          new Runnable() { // from class:
            // com.android.server.app.GameServiceProviderInstanceImpl$4$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              GameServiceProviderInstanceImpl.TaskStackListenerC06774.this
                  .lambda$onTaskFocusChanged$2(i, z);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTaskFocusChanged$2(int i, boolean z) {
      GameServiceProviderInstanceImpl.this.onTaskFocusChanged(i, z);
    }
  }

  /* renamed from: com.android.server.app.GameServiceProviderInstanceImpl$5 */
  public class IProcessObserverStubC06785 extends IProcessObserver.Stub {
    public void onForegroundServicesChanged(int i, int i2, int i3) {}

    public IProcessObserverStubC06785() {}

    public void onForegroundActivitiesChanged(final int i, int i2, boolean z) {
      GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(
          new Runnable() { // from class:
            // com.android.server.app.GameServiceProviderInstanceImpl$5$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              GameServiceProviderInstanceImpl.IProcessObserverStubC06785.this
                  .lambda$onForegroundActivitiesChanged$0(i);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onForegroundActivitiesChanged$0(int i) {
      GameServiceProviderInstanceImpl.this.onForegroundActivitiesChanged(i);
    }

    public void onProcessDied(final int i, int i2) {
      GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(
          new Runnable() { // from class:
            // com.android.server.app.GameServiceProviderInstanceImpl$5$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              GameServiceProviderInstanceImpl.IProcessObserverStubC06785.this
                  .lambda$onProcessDied$1(i);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onProcessDied$1(int i) {
      GameServiceProviderInstanceImpl.this.onProcessDied(i);
    }
  }

  /* renamed from: com.android.server.app.GameServiceProviderInstanceImpl$6 */
  public class C06796 extends IGameServiceController.Stub {
    public C06796() {}

    public void createGameSession(final int i) {
      super.createGameSession_enforcePermission();
      GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(
          new Runnable() { // from class:
            // com.android.server.app.GameServiceProviderInstanceImpl$6$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              GameServiceProviderInstanceImpl.C06796.this.lambda$createGameSession$0(i);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createGameSession$0(int i) {
      GameServiceProviderInstanceImpl.this.createGameSession(i);
    }
  }

  /* renamed from: com.android.server.app.GameServiceProviderInstanceImpl$7 */
  public class C06807 extends IGameSessionController.Stub {
    public C06807() {}

    public void takeScreenshot(final int i, final AndroidFuture androidFuture) {
      super.takeScreenshot_enforcePermission();
      GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(
          new Runnable() { // from class:
            // com.android.server.app.GameServiceProviderInstanceImpl$7$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              GameServiceProviderInstanceImpl.C06807.this.lambda$takeScreenshot$0(i, androidFuture);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$takeScreenshot$0(int i, AndroidFuture androidFuture) {
      GameServiceProviderInstanceImpl.this.takeScreenshot(i, androidFuture);
    }

    public void restartGame(final int i) {
      super.restartGame_enforcePermission();
      GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(
          new Runnable() { // from class:
            // com.android.server.app.GameServiceProviderInstanceImpl$7$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              GameServiceProviderInstanceImpl.C06807.this.lambda$restartGame$1(i);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$restartGame$1(int i) {
      GameServiceProviderInstanceImpl.this.restartGame(i);
    }
  }

  public GameServiceProviderInstanceImpl(
      UserHandle userHandle,
      Executor executor,
      Context context,
      GameTaskInfoProvider gameTaskInfoProvider,
      IActivityManager iActivityManager,
      ActivityManagerInternal activityManagerInternal,
      IActivityTaskManager iActivityTaskManager,
      WindowManagerService windowManagerService,
      WindowManagerInternal windowManagerInternal,
      ActivityTaskManagerInternal activityTaskManagerInternal,
      ServiceConnector serviceConnector,
      ServiceConnector serviceConnector2,
      ScreenshotHelper screenshotHelper) {
    this.mUserHandle = userHandle;
    this.mBackgroundExecutor = executor;
    this.mContext = context;
    this.mGameTaskInfoProvider = gameTaskInfoProvider;
    this.mActivityManager = iActivityManager;
    this.mActivityManagerInternal = activityManagerInternal;
    this.mActivityTaskManager = iActivityTaskManager;
    this.mWindowManagerService = windowManagerService;
    this.mWindowManagerInternal = windowManagerInternal;
    this.mActivityTaskManagerInternal = activityTaskManagerInternal;
    this.mGameServiceConnector = serviceConnector;
    this.mGameSessionServiceConnector = serviceConnector2;
    this.mScreenshotHelper = screenshotHelper;
  }

  @Override // com.android.server.app.GameServiceProviderInstance
  public void start() {
    synchronized (this.mLock) {
      startLocked();
    }
  }

  @Override // com.android.server.app.GameServiceProviderInstance
  public void stop() {
    synchronized (this.mLock) {
      stopLocked();
    }
  }

  public final void startLocked() {
    if (this.mIsRunning) {
      return;
    }
    this.mIsRunning = true;
    this.mGameServiceConnector.setServiceLifecycleCallbacks(this.mGameServiceLifecycleCallbacks);
    this.mGameSessionServiceConnector.setServiceLifecycleCallbacks(
        this.mGameSessionServiceLifecycleCallbacks);
    this.mGameServiceConnector.connect();
    try {
      this.mActivityTaskManager.registerTaskStackListener(this.mTaskStackListener);
    } catch (RemoteException e) {
      Slog.w("GameServiceProviderInstance", "Failed to register task stack listener", e);
    }
    try {
      this.mActivityManager.registerProcessObserver(this.mProcessObserver);
    } catch (RemoteException e2) {
      Slog.w("GameServiceProviderInstance", "Failed to register process observer", e2);
    }
    this.mWindowManagerInternal.registerTaskSystemBarsListener(
        this.mTaskSystemBarsVisibilityListener);
  }

  public final void stopLocked() {
    if (this.mIsRunning) {
      this.mIsRunning = false;
      try {
        this.mActivityManager.unregisterProcessObserver(this.mProcessObserver);
      } catch (RemoteException e) {
        Slog.w("GameServiceProviderInstance", "Failed to unregister process observer", e);
      }
      try {
        this.mActivityTaskManager.unregisterTaskStackListener(this.mTaskStackListener);
      } catch (RemoteException e2) {
        Slog.w("GameServiceProviderInstance", "Failed to unregister task stack listener", e2);
      }
      this.mWindowManagerInternal.unregisterTaskSystemBarsListener(
          this.mTaskSystemBarsVisibilityListener);
      destroyAndClearAllGameSessionsLocked();
      this.mGameServiceConnector
          .post(
              new ServiceConnector.VoidJob() { // from class:
                // com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda0
                public final void runNoResult(Object obj) {
                  ((IGameService) obj).disconnected();
                }
              })
          .whenComplete(
              new BiConsumer() { // from class:
                // com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                  GameServiceProviderInstanceImpl.this.lambda$stopLocked$0(
                      (Void) obj, (Throwable) obj2);
                }
              });
      this.mGameSessionServiceConnector.unbind();
      this.mGameServiceConnector.setServiceLifecycleCallbacks(
          (ServiceConnector.ServiceLifecycleCallbacks) null);
      this.mGameSessionServiceConnector.setServiceLifecycleCallbacks(
          (ServiceConnector.ServiceLifecycleCallbacks) null);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$stopLocked$0(Void r1, Throwable th) {
    this.mGameServiceConnector.unbind();
  }

  public final void onTaskCreated(int i, ComponentName componentName) {
    GameTaskInfo gameTaskInfo = this.mGameTaskInfoProvider.get(i, componentName);
    if (gameTaskInfo.mIsGameTask) {
      synchronized (this.mLock) {
        gameTaskStartedLocked(gameTaskInfo);
      }
    }
  }

  public final void onTaskFocusChanged(int i, boolean z) {
    synchronized (this.mLock) {
      onTaskFocusChangedLocked(i, z);
    }
  }

  public final void onTaskFocusChangedLocked(int i, boolean z) {
    GameSessionRecord gameSessionRecord =
        (GameSessionRecord) this.mGameSessions.get(Integer.valueOf(i));
    if (gameSessionRecord == null) {
      if (z) {
        maybeCreateGameSessionForFocusedTaskLocked(i);
      }
    } else {
      if (gameSessionRecord.getGameSession() == null) {
        return;
      }
      try {
        gameSessionRecord.getGameSession().onTaskFocusChanged(z);
      } catch (RemoteException unused) {
        Slog.w(
            "GameServiceProviderInstance",
            "Failed to notify session of task focus change: " + gameSessionRecord);
      }
    }
  }

  public final void maybeCreateGameSessionForFocusedTaskLocked(int i) {
    GameTaskInfo gameTaskInfo = this.mGameTaskInfoProvider.get(i);
    if (gameTaskInfo == null) {
      Slog.w("GameServiceProviderInstance", "No task info for focused task: " + i);
      return;
    }
    if (gameTaskInfo.mIsGameTask) {
      gameTaskStartedLocked(gameTaskInfo);
    }
  }

  public final void gameTaskStartedLocked(final GameTaskInfo gameTaskInfo) {
    if (this.mIsRunning) {
      if (((GameSessionRecord) this.mGameSessions.get(Integer.valueOf(gameTaskInfo.mTaskId)))
          != null) {
        Slog.w(
            "GameServiceProviderInstance",
            "Existing game session found for task (id: "
                + gameTaskInfo.mTaskId
                + ") creation. Ignoring.");
        return;
      }
      this.mGameSessions.put(
          Integer.valueOf(gameTaskInfo.mTaskId),
          GameSessionRecord.awaitingGameSessionRequest(
              gameTaskInfo.mTaskId, gameTaskInfo.mComponentName));
      this.mGameServiceConnector.post(
          new ServiceConnector.VoidJob() { // from class:
            // com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda5
            public final void runNoResult(Object obj) {
              GameServiceProviderInstanceImpl.lambda$gameTaskStartedLocked$1(
                  GameTaskInfo.this, (IGameService) obj);
            }
          });
    }
  }

  public static /* synthetic */ void lambda$gameTaskStartedLocked$1(
      GameTaskInfo gameTaskInfo, IGameService iGameService) {
    iGameService.gameStarted(
        new GameStartedEvent(gameTaskInfo.mTaskId, gameTaskInfo.mComponentName.getPackageName()));
  }

  public final void onTaskRemoved(int i) {
    synchronized (this.mLock) {
      if (this.mGameSessions.containsKey(Integer.valueOf(i))) {
        removeAndDestroyGameSessionIfNecessaryLocked(i);
      }
    }
  }

  public final void onTransientSystemBarsVisibilityChanged(int i, boolean z, boolean z2) {
    GameSessionRecord gameSessionRecord;
    IGameSession gameSession;
    if (!z || z2) {
      synchronized (this.mLock) {
        gameSessionRecord = (GameSessionRecord) this.mGameSessions.get(Integer.valueOf(i));
      }
      if (gameSessionRecord == null || (gameSession = gameSessionRecord.getGameSession()) == null) {
        return;
      }
      try {
        gameSession.onTransientSystemBarVisibilityFromRevealGestureChanged(z);
      } catch (RemoteException unused) {
        Slog.w(
            "GameServiceProviderInstance",
            "Failed to send transient system bars visibility from reveal gesture for task: " + i);
      }
    }
  }

  public final void createGameSession(int i) {
    synchronized (this.mLock) {
      createGameSessionLocked(i);
    }
  }

  public final void createGameSessionLocked(final int i) {
    if (this.mIsRunning) {
      final GameSessionRecord gameSessionRecord =
          (GameSessionRecord) this.mGameSessions.get(Integer.valueOf(i));
      if (gameSessionRecord == null) {
        Slog.w(
            "GameServiceProviderInstance",
            "No existing game session record found for task (id: " + i + ") creation. Ignoring.");
        return;
      }
      if (!gameSessionRecord.isAwaitingGameSessionRequest()) {
        Slog.w(
            "GameServiceProviderInstance",
            "Existing game session for task (id: "
                + i
                + ") is not awaiting game session request. Ignoring.");
        return;
      }
      final GameSessionViewHostConfiguration createViewHostConfigurationForTask =
          createViewHostConfigurationForTask(i);
      if (createViewHostConfigurationForTask == null) {
        Slog.w(
            "GameServiceProviderInstance",
            "Failed to create view host configuration for task (id" + i + ") creation. Ignoring.");
        return;
      }
      this.mGameSessions.put(Integer.valueOf(i), gameSessionRecord.withGameSessionRequested());
      final AndroidFuture whenCompleteAsync =
          new AndroidFuture()
              .orTimeout(10000L, TimeUnit.MILLISECONDS)
              .whenCompleteAsync(
                  new BiConsumer() { // from class:
                    // com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda3
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                      GameServiceProviderInstanceImpl.this.lambda$createGameSessionLocked$2(
                          gameSessionRecord, i, (CreateGameSessionResult) obj, (Throwable) obj2);
                    }
                  },
                  this.mBackgroundExecutor);
      this.mGameSessionServiceConnector.post(
          new ServiceConnector.VoidJob() { // from class:
            // com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda4
            public final void runNoResult(Object obj) {
              GameServiceProviderInstanceImpl.this.lambda$createGameSessionLocked$3(
                  i,
                  gameSessionRecord,
                  createViewHostConfigurationForTask,
                  whenCompleteAsync,
                  (IGameSessionService) obj);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$createGameSessionLocked$2(
      GameSessionRecord gameSessionRecord,
      int i,
      CreateGameSessionResult createGameSessionResult,
      Throwable th) {
    if (th != null || createGameSessionResult == null) {
      Slog.w(
          "GameServiceProviderInstance", "Failed to create GameSession: " + gameSessionRecord, th);
      synchronized (this.mLock) {
        removeAndDestroyGameSessionIfNecessaryLocked(i);
      }
      return;
    }
    synchronized (this.mLock) {
      attachGameSessionLocked(i, createGameSessionResult);
    }
    setGameSessionFocusedIfNecessary(i, createGameSessionResult.getGameSession());
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$createGameSessionLocked$3(
      int i,
      GameSessionRecord gameSessionRecord,
      GameSessionViewHostConfiguration gameSessionViewHostConfiguration,
      AndroidFuture androidFuture,
      IGameSessionService iGameSessionService) {
    iGameSessionService.create(
        this.mGameSessionController,
        new CreateGameSessionRequest(i, gameSessionRecord.getComponentName().getPackageName()),
        gameSessionViewHostConfiguration,
        androidFuture);
  }

  public final void setGameSessionFocusedIfNecessary(int i, IGameSession iGameSession) {
    try {
      ActivityTaskManager.RootTaskInfo focusedRootTaskInfo =
          this.mActivityTaskManager.getFocusedRootTaskInfo();
      if (focusedRootTaskInfo == null || focusedRootTaskInfo.taskId != i) {
        return;
      }
      iGameSession.onTaskFocusChanged(true);
    } catch (RemoteException unused) {
      Slog.w("GameServiceProviderInstance", "Failed to set task focused for ID: " + i);
    }
  }

  public final void attachGameSessionLocked(
      int i, CreateGameSessionResult createGameSessionResult) {
    GameSessionRecord gameSessionRecord =
        (GameSessionRecord) this.mGameSessions.get(Integer.valueOf(i));
    if (gameSessionRecord == null) {
      Slog.w(
          "GameServiceProviderInstance", "No associated game session record. Destroying id: " + i);
      destroyGameSessionDuringAttach(i, createGameSessionResult);
      return;
    }
    if (!gameSessionRecord.isGameSessionRequested()) {
      destroyGameSessionDuringAttach(i, createGameSessionResult);
      return;
    }
    try {
      this.mWindowManagerInternal.addTrustedTaskOverlay(
          i, createGameSessionResult.getSurfacePackage());
      this.mGameSessions.put(
          Integer.valueOf(i),
          gameSessionRecord.withGameSession(
              createGameSessionResult.getGameSession(),
              createGameSessionResult.getSurfacePackage()));
    } catch (IllegalArgumentException unused) {
      Slog.w("GameServiceProviderInstance", "Failed to add task overlay. Destroying id: " + i);
      destroyGameSessionDuringAttach(i, createGameSessionResult);
    }
  }

  public final void destroyAndClearAllGameSessionsLocked() {
    Iterator it = this.mGameSessions.values().iterator();
    while (it.hasNext()) {
      destroyGameSessionFromRecordLocked((GameSessionRecord) it.next());
    }
    this.mGameSessions.clear();
  }

  public final void destroyGameSessionDuringAttach(
      int i, CreateGameSessionResult createGameSessionResult) {
    try {
      createGameSessionResult.getGameSession().onDestroyed();
    } catch (RemoteException unused) {
      Slog.w("GameServiceProviderInstance", "Failed to destroy session: " + i);
    }
  }

  public final void removeAndDestroyGameSessionIfNecessaryLocked(int i) {
    GameSessionRecord gameSessionRecord =
        (GameSessionRecord) this.mGameSessions.remove(Integer.valueOf(i));
    if (gameSessionRecord == null) {
      return;
    }
    destroyGameSessionFromRecordLocked(gameSessionRecord);
  }

  public final void destroyGameSessionFromRecordLocked(GameSessionRecord gameSessionRecord) {
    SurfaceControlViewHost.SurfacePackage surfacePackage = gameSessionRecord.getSurfacePackage();
    if (surfacePackage != null) {
      try {
        this.mWindowManagerInternal.removeTrustedTaskOverlay(
            gameSessionRecord.getTaskId(), surfacePackage);
      } catch (IllegalArgumentException unused) {
        Slog.i(
            "GameServiceProviderInstance",
            "Failed to remove task overlay. This is expected if the task is already destroyed: "
                + gameSessionRecord);
      }
    }
    IGameSession gameSession = gameSessionRecord.getGameSession();
    if (gameSession != null) {
      try {
        gameSession.onDestroyed();
      } catch (RemoteException e) {
        Slog.w("GameServiceProviderInstance", "Failed to destroy session: " + gameSessionRecord, e);
      }
    }
    if (this.mGameSessions.isEmpty()) {
      this.mGameSessionServiceConnector.unbind();
    }
  }

  public final void onForegroundActivitiesChanged(int i) {
    synchronized (this.mLock) {
      onForegroundActivitiesChangedLocked(i);
    }
  }

  public final void onForegroundActivitiesChangedLocked(int i) {
    if (this.mPidToPackageMap.containsKey(Integer.valueOf(i))) {
      return;
    }
    String packageNameByPid = this.mActivityManagerInternal.getPackageNameByPid(i);
    if (!TextUtils.isEmpty(packageNameByPid)
        && gameSessionExistsForPackageNameLocked(packageNameByPid)) {
      this.mPidToPackageMap.put(Integer.valueOf(i), packageNameByPid);
      int intValue =
          ((Integer) this.mPackageNameToProcessCountMap.getOrDefault(packageNameByPid, 0))
                  .intValue()
              + 1;
      this.mPackageNameToProcessCountMap.put(packageNameByPid, Integer.valueOf(intValue));
      if (intValue > 0) {
        recreateEndedGameSessionsLocked(packageNameByPid);
      }
    }
  }

  public final void recreateEndedGameSessionsLocked(String str) {
    for (GameSessionRecord gameSessionRecord : this.mGameSessions.values()) {
      if (gameSessionRecord.isGameSessionEndedForProcessDeath()
          && str.equals(gameSessionRecord.getComponentName().getPackageName())) {
        int taskId = gameSessionRecord.getTaskId();
        this.mGameSessions.put(
            Integer.valueOf(taskId),
            GameSessionRecord.awaitingGameSessionRequest(
                taskId, gameSessionRecord.getComponentName()));
        createGameSessionLocked(gameSessionRecord.getTaskId());
      }
    }
  }

  public final void onProcessDied(int i) {
    synchronized (this.mLock) {
      onProcessDiedLocked(i);
    }
  }

  public final void onProcessDiedLocked(int i) {
    String str = (String) this.mPidToPackageMap.remove(Integer.valueOf(i));
    if (str == null) {
      return;
    }
    Integer num = (Integer) this.mPackageNameToProcessCountMap.get(str);
    if (num == null) {
      Slog.w(
          "GameServiceProviderInstance",
          "onProcessDiedLocked(): Missing process count for package");
      return;
    }
    int intValue = num.intValue() - 1;
    this.mPackageNameToProcessCountMap.put(str, Integer.valueOf(intValue));
    if (intValue <= 0) {
      endGameSessionsForPackageLocked(str);
    }
  }

  public final void endGameSessionsForPackageLocked(String str) {
    ActivityManager.RunningTaskInfo runningTaskInfo;
    for (GameSessionRecord gameSessionRecord : this.mGameSessions.values()) {
      if (gameSessionRecord.getGameSession() != null
          && str.equals(gameSessionRecord.getComponentName().getPackageName())
          && ((runningTaskInfo =
                      this.mGameTaskInfoProvider.getRunningTaskInfo(gameSessionRecord.getTaskId()))
                  == null
              || !runningTaskInfo.isVisible)) {
        this.mGameSessions.put(
            Integer.valueOf(gameSessionRecord.getTaskId()),
            gameSessionRecord.withGameSessionEndedOnProcessDeath());
        destroyGameSessionFromRecordLocked(gameSessionRecord);
      }
    }
  }

  public final boolean gameSessionExistsForPackageNameLocked(String str) {
    Iterator it = this.mGameSessions.values().iterator();
    while (it.hasNext()) {
      if (str.equals(((GameSessionRecord) it.next()).getComponentName().getPackageName())) {
        return true;
      }
    }
    return false;
  }

  public final GameSessionViewHostConfiguration createViewHostConfigurationForTask(int i) {
    ActivityManager.RunningTaskInfo runningTaskInfo =
        this.mGameTaskInfoProvider.getRunningTaskInfo(i);
    if (runningTaskInfo == null) {
      return null;
    }
    Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
    return new GameSessionViewHostConfiguration(
        runningTaskInfo.displayId, bounds.width(), bounds.height());
  }

  public void takeScreenshot(final int i, final AndroidFuture androidFuture) {
    synchronized (this.mLock) {
      final GameSessionRecord gameSessionRecord =
          (GameSessionRecord) this.mGameSessions.get(Integer.valueOf(i));
      if (gameSessionRecord == null) {
        Slog.w("GameServiceProviderInstance", "No game session found for id: " + i);
        androidFuture.complete(GameScreenshotResult.createInternalErrorResult());
        return;
      }
      SurfaceControlViewHost.SurfacePackage surfacePackage = gameSessionRecord.getSurfacePackage();
      final SurfaceControl surfaceControl =
          surfacePackage != null ? surfacePackage.getSurfaceControl() : null;
      this.mBackgroundExecutor.execute(
          new Runnable() { // from class:
            // com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              GameServiceProviderInstanceImpl.this.lambda$takeScreenshot$5(
                  surfaceControl, i, androidFuture, gameSessionRecord);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$takeScreenshot$5(
      SurfaceControl surfaceControl,
      int i,
      final AndroidFuture androidFuture,
      GameSessionRecord gameSessionRecord) {
    ScreenCapture.LayerCaptureArgs.Builder builder =
        new ScreenCapture.LayerCaptureArgs.Builder((SurfaceControl) null);
    if (surfaceControl != null) {
      builder.setExcludeLayers(new SurfaceControl[] {surfaceControl});
    }
    Bitmap captureTaskBitmap = this.mWindowManagerService.captureTaskBitmap(i, builder);
    if (captureTaskBitmap == null) {
      Slog.w("GameServiceProviderInstance", "Could not get bitmap for id: " + i);
      androidFuture.complete(GameScreenshotResult.createInternalErrorResult());
      return;
    }
    ActivityManager.RunningTaskInfo runningTaskInfo =
        this.mGameTaskInfoProvider.getRunningTaskInfo(i);
    if (runningTaskInfo == null) {
      Slog.w("GameServiceProviderInstance", "Could not get running task info for id: " + i);
      androidFuture.complete(GameScreenshotResult.createInternalErrorResult());
    }
    Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
    this.mScreenshotHelper.takeScreenshot(
        new ScreenshotRequest.Builder(3, 5)
            .setTopComponent(gameSessionRecord.getComponentName())
            .setTaskId(i)
            .setUserId(this.mUserHandle.getIdentifier())
            .setBitmap(captureTaskBitmap)
            .setBoundsOnScreen(bounds)
            .setInsets(Insets.NONE)
            .build(),
        BackgroundThread.getHandler(),
        new Consumer() { // from class:
          // com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda6
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            GameServiceProviderInstanceImpl.lambda$takeScreenshot$4(androidFuture, (Uri) obj);
          }
        });
  }

  public static /* synthetic */ void lambda$takeScreenshot$4(AndroidFuture androidFuture, Uri uri) {
    if (uri == null) {
      androidFuture.complete(GameScreenshotResult.createInternalErrorResult());
    } else {
      androidFuture.complete(GameScreenshotResult.createSuccessResult());
    }
  }

  public final void restartGame(int i) {
    synchronized (this.mLock) {
      GameSessionRecord gameSessionRecord =
          (GameSessionRecord) this.mGameSessions.get(Integer.valueOf(i));
      if (gameSessionRecord == null) {
        return;
      }
      String packageName = gameSessionRecord.getComponentName().getPackageName();
      if (packageName == null) {
        return;
      }
      this.mActivityTaskManagerInternal.restartTaskActivityProcessIfVisible(i, packageName);
    }
  }
}
