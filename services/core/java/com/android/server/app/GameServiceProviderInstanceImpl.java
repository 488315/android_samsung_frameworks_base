package com.android.server.app;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityTaskManager;
import android.app.IActivityManager;
import android.app.IActivityTaskManager;
import android.app.IProcessObserver;
import android.app.TaskStackListener;
import android.content.ComponentName;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.Rect;
import android.net.Uri;
import android.os.IInterface;
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
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.wm.WindowManagerService;

import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class GameServiceProviderInstanceImpl {
    public final IActivityManager mActivityManager;
    public final ActivityManagerInternal mActivityManagerInternal;
    public final IActivityTaskManager mActivityTaskManager;
    public final ActivityTaskManagerInternal mActivityTaskManagerInternal;
    public final Executor mBackgroundExecutor;
    public final ServiceConnector mGameServiceConnector;
    public final ServiceConnector mGameSessionServiceConnector;
    public final GameTaskInfoProvider mGameTaskInfoProvider;
    public volatile boolean mIsRunning;
    public final ScreenshotHelper mScreenshotHelper;
    public final UserHandle mUserHandle;
    public final WindowManagerInternal mWindowManagerInternal;
    public final WindowManagerService mWindowManagerService;
    public final AnonymousClass1 mGameServiceLifecycleCallbacks =
            new ServiceConnector
                    .ServiceLifecycleCallbacks() { // from class:
                                                   // com.android.server.app.GameServiceProviderInstanceImpl.1
                public final void onConnected(IInterface iInterface) {
                    try {
                        ((IGameService) iInterface)
                                .connected(
                                        GameServiceProviderInstanceImpl.this
                                                .mGameServiceController);
                    } catch (RemoteException e) {
                        Slog.w("GameServiceProviderInstance", "Failed to send connected event", e);
                    }
                }
            };
    public final AnonymousClass2 mGameSessionServiceLifecycleCallbacks = new AnonymousClass2();
    public final AnonymousClass3 mTaskSystemBarsVisibilityListener =
            new WindowManagerInternal.TaskSystemBarsListener() { // from class:
                // com.android.server.app.GameServiceProviderInstanceImpl.3
                @Override // com.android.server.wm.WindowManagerInternal.TaskSystemBarsListener
                public final void onTransientSystemBarsVisibilityChanged(
                        int i, boolean z, boolean z2) {
                    GameSessionRecord gameSessionRecord;
                    IGameSession iGameSession;
                    GameServiceProviderInstanceImpl gameServiceProviderInstanceImpl =
                            GameServiceProviderInstanceImpl.this;
                    gameServiceProviderInstanceImpl.getClass();
                    if (!z || z2) {
                        synchronized (gameServiceProviderInstanceImpl.mLock) {
                            gameSessionRecord =
                                    (GameSessionRecord)
                                            gameServiceProviderInstanceImpl.mGameSessions.get(
                                                    Integer.valueOf(i));
                        }
                        if (gameSessionRecord == null
                                || (iGameSession = gameSessionRecord.mIGameSession) == null) {
                            return;
                        }
                        try {
                            iGameSession.onTransientSystemBarVisibilityFromRevealGestureChanged(z);
                        } catch (RemoteException unused) {
                            DeviceIdleController$$ExternalSyntheticOutline0.m(
                                    i,
                                    "Failed to send transient system bars visibility from reveal"
                                        + " gesture for task: ",
                                    "GameServiceProviderInstance");
                        }
                    }
                }
            };
    public final AnonymousClass4 mTaskStackListener = new AnonymousClass4();
    public final AnonymousClass5 mProcessObserver = new AnonymousClass5();
    public final AnonymousClass6 mGameServiceController = new AnonymousClass6();
    public final AnonymousClass7 mGameSessionController = new AnonymousClass7();
    public final Object mLock = new Object();
    public final ConcurrentHashMap mGameSessions = new ConcurrentHashMap();
    public final ConcurrentHashMap mPidToPackageMap = new ConcurrentHashMap();
    public final ConcurrentHashMap mPackageNameToProcessCountMap = new ConcurrentHashMap();

    /* renamed from: com.android.server.app.GameServiceProviderInstanceImpl$2, reason: invalid class name */
    public final class AnonymousClass2 implements ServiceConnector.ServiceLifecycleCallbacks {
        public AnonymousClass2() {}

        public final void onBinderDied() {
            GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(
                    new Runnable() { // from class:
                                     // com.android.server.app.GameServiceProviderInstanceImpl$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            GameServiceProviderInstanceImpl.AnonymousClass2 anonymousClass2 =
                                    GameServiceProviderInstanceImpl.AnonymousClass2.this;
                            synchronized (GameServiceProviderInstanceImpl.this.mLock) {
                                GameServiceProviderInstanceImpl gameServiceProviderInstanceImpl =
                                        GameServiceProviderInstanceImpl.this;
                                Iterator it =
                                        gameServiceProviderInstanceImpl
                                                .mGameSessions
                                                .values()
                                                .iterator();
                                while (it.hasNext()) {
                                    gameServiceProviderInstanceImpl
                                            .destroyGameSessionFromRecordLocked(
                                                    (GameSessionRecord) it.next());
                                }
                                gameServiceProviderInstanceImpl.mGameSessions.clear();
                            }
                        }
                    });
        }
    }

    /* renamed from: com.android.server.app.GameServiceProviderInstanceImpl$4, reason: invalid class name */
    public final class AnonymousClass4 extends TaskStackListener {
        public AnonymousClass4() {}

        public final void onTaskCreated(int i, ComponentName componentName) {
            if (componentName == null) {
                return;
            }
            GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(
                    new GameServiceProviderInstanceImpl$4$$ExternalSyntheticLambda0(
                            this, i, componentName));
        }

        public final void onTaskFocusChanged(final int i, final boolean z) {
            GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(
                    new Runnable() { // from class:
                                     // com.android.server.app.GameServiceProviderInstanceImpl$4$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            GameServiceProviderInstanceImpl.AnonymousClass4 anonymousClass4 =
                                    GameServiceProviderInstanceImpl.AnonymousClass4.this;
                            int i2 = i;
                            boolean z2 = z;
                            GameServiceProviderInstanceImpl gameServiceProviderInstanceImpl =
                                    GameServiceProviderInstanceImpl.this;
                            synchronized (gameServiceProviderInstanceImpl.mLock) {
                                gameServiceProviderInstanceImpl.onTaskFocusChangedLocked(i2, z2);
                            }
                        }
                    });
        }

        public final void onTaskRemoved(int i) {
            GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(
                    new GameServiceProviderInstanceImpl$4$$ExternalSyntheticLambda1(this, i));
        }
    }

    /* renamed from: com.android.server.app.GameServiceProviderInstanceImpl$5, reason: invalid class name */
    public final class AnonymousClass5 extends IProcessObserver.Stub {
        public AnonymousClass5() {}

        public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(
                    new GameServiceProviderInstanceImpl$5$$ExternalSyntheticLambda0(this, i, 0));
        }

        public final void onForegroundServicesChanged(int i, int i2, int i3) {}

        public final void onProcessDied(int i, int i2) {
            GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(
                    new GameServiceProviderInstanceImpl$5$$ExternalSyntheticLambda0(this, i, 1));
        }

        public final void onProcessStarted(int i, int i2, int i3, String str, String str2) {}
    }

    /* renamed from: com.android.server.app.GameServiceProviderInstanceImpl$6, reason: invalid class name */
    public final class AnonymousClass6 extends IGameServiceController.Stub {
        public AnonymousClass6() {}

        public final void createGameSession(int i) {
            createGameSession_enforcePermission();
            GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(
                    new GameServiceProviderInstanceImpl$4$$ExternalSyntheticLambda1(this, i));
        }
    }

    /* renamed from: com.android.server.app.GameServiceProviderInstanceImpl$7, reason: invalid class name */
    public final class AnonymousClass7 extends IGameSessionController.Stub {
        public AnonymousClass7() {}

        public final void restartGame(int i) {
            restartGame_enforcePermission();
            GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(
                    new GameServiceProviderInstanceImpl$4$$ExternalSyntheticLambda1(this, i));
        }

        public final void takeScreenshot(int i, AndroidFuture androidFuture) {
            takeScreenshot_enforcePermission();
            GameServiceProviderInstanceImpl.this.mBackgroundExecutor.execute(
                    new GameServiceProviderInstanceImpl$4$$ExternalSyntheticLambda0(
                            this, i, androidFuture));
        }
    }

    public GameServiceProviderInstanceImpl(
            UserHandle userHandle,
            Executor executor,
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

    public static void destroyGameSessionDuringAttach(
            int i, CreateGameSessionResult createGameSessionResult) {
        try {
            createGameSessionResult.getGameSession().onDestroyed();
        } catch (RemoteException unused) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i, "Failed to destroy session: ", "GameServiceProviderInstance");
        }
    }

    public final void attachGameSessionLocked(
            int i, CreateGameSessionResult createGameSessionResult) {
        GameSessionRecord gameSessionRecord =
                (GameSessionRecord) this.mGameSessions.get(Integer.valueOf(i));
        if (gameSessionRecord == null) {
            Slog.w(
                    "GameServiceProviderInstance",
                    "No associated game session record. Destroying id: " + i);
            destroyGameSessionDuringAttach(i, createGameSessionResult);
            return;
        }
        if (gameSessionRecord.mState != GameSessionRecord.State.GAME_SESSION_REQUESTED) {
            destroyGameSessionDuringAttach(i, createGameSessionResult);
            return;
        }
        try {
            this.mWindowManagerInternal.addTrustedTaskOverlay(
                    i, createGameSessionResult.getSurfacePackage());
            ConcurrentHashMap concurrentHashMap = this.mGameSessions;
            Integer valueOf = Integer.valueOf(i);
            IGameSession gameSession = createGameSessionResult.getGameSession();
            SurfaceControlViewHost.SurfacePackage surfacePackage =
                    createGameSessionResult.getSurfacePackage();
            Objects.requireNonNull(gameSession);
            concurrentHashMap.put(
                    valueOf,
                    new GameSessionRecord(
                            gameSessionRecord.mTaskId,
                            GameSessionRecord.State.GAME_SESSION_ATTACHED,
                            gameSessionRecord.mRootComponentName,
                            gameSession,
                            surfacePackage));
        } catch (IllegalArgumentException unused) {
            Slog.w(
                    "GameServiceProviderInstance",
                    "Failed to add task overlay. Destroying id: " + i);
            destroyGameSessionDuringAttach(i, createGameSessionResult);
        }
    }

    public final void createGameSessionLocked(final int i) {
        final GameSessionViewHostConfiguration gameSessionViewHostConfiguration;
        if (this.mIsRunning) {
            final GameSessionRecord gameSessionRecord =
                    (GameSessionRecord) this.mGameSessions.get(Integer.valueOf(i));
            if (gameSessionRecord == null) {
                BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                        i,
                        "No existing game session record found for task (id: ",
                        ") creation. Ignoring.",
                        "GameServiceProviderInstance");
                return;
            }
            if (gameSessionRecord.mState != GameSessionRecord.State.NO_GAME_SESSION_REQUESTED) {
                BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                        i,
                        "Existing game session for task (id: ",
                        ") is not awaiting game session request. Ignoring.",
                        "GameServiceProviderInstance");
                return;
            }
            ActivityManager.RunningTaskInfo runningTaskInfo =
                    this.mGameTaskInfoProvider.getRunningTaskInfo(i);
            if (runningTaskInfo == null) {
                gameSessionViewHostConfiguration = null;
            } else {
                Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
                gameSessionViewHostConfiguration =
                        new GameSessionViewHostConfiguration(
                                runningTaskInfo.displayId, bounds.width(), bounds.height());
            }
            if (gameSessionViewHostConfiguration == null) {
                BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                        i,
                        "Failed to create view host configuration for task (id",
                        ") creation. Ignoring.",
                        "GameServiceProviderInstance");
                return;
            }
            this.mGameSessions.put(
                    Integer.valueOf(i),
                    new GameSessionRecord(
                            gameSessionRecord.mTaskId,
                            GameSessionRecord.State.GAME_SESSION_REQUESTED,
                            gameSessionRecord.mRootComponentName,
                            null,
                            null));
            final AndroidFuture whenCompleteAsync =
                    new AndroidFuture()
                            .orTimeout(10000L, TimeUnit.MILLISECONDS)
                            .whenCompleteAsync(
                                    new BiConsumer() { // from class:
                                                       // com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda5
                                        @Override // java.util.function.BiConsumer
                                        public final void accept(Object obj, Object obj2) {
                                            GameServiceProviderInstanceImpl
                                                    gameServiceProviderInstanceImpl =
                                                            GameServiceProviderInstanceImpl.this;
                                            GameSessionRecord gameSessionRecord2 =
                                                    gameSessionRecord;
                                            int i2 = i;
                                            CreateGameSessionResult createGameSessionResult =
                                                    (CreateGameSessionResult) obj;
                                            Throwable th = (Throwable) obj2;
                                            gameServiceProviderInstanceImpl.getClass();
                                            if (th != null || createGameSessionResult == null) {
                                                Slog.w(
                                                        "GameServiceProviderInstance",
                                                        "Failed to create GameSession: "
                                                                + gameSessionRecord2,
                                                        th);
                                                synchronized (
                                                        gameServiceProviderInstanceImpl.mLock) {
                                                    GameSessionRecord gameSessionRecord3 =
                                                            (GameSessionRecord)
                                                                    gameServiceProviderInstanceImpl
                                                                            .mGameSessions.remove(
                                                                            Integer.valueOf(i2));
                                                    if (gameSessionRecord3 != null) {
                                                        gameServiceProviderInstanceImpl
                                                                .destroyGameSessionFromRecordLocked(
                                                                        gameSessionRecord3);
                                                    }
                                                }
                                                return;
                                            }
                                            synchronized (gameServiceProviderInstanceImpl.mLock) {
                                                gameServiceProviderInstanceImpl
                                                        .attachGameSessionLocked(
                                                                i2, createGameSessionResult);
                                            }
                                            IGameSession gameSession =
                                                    createGameSessionResult.getGameSession();
                                            try {
                                                ActivityTaskManager.RootTaskInfo
                                                        focusedRootTaskInfo =
                                                                gameServiceProviderInstanceImpl
                                                                        .mActivityTaskManager
                                                                        .getFocusedRootTaskInfo();
                                                if (focusedRootTaskInfo == null
                                                        || focusedRootTaskInfo.taskId != i2) {
                                                    return;
                                                }
                                                gameSession.onTaskFocusChanged(true);
                                            } catch (RemoteException unused) {
                                                DeviceIdleController$$ExternalSyntheticOutline0.m(
                                                        i2,
                                                        "Failed to set task focused for ID: ",
                                                        "GameServiceProviderInstance");
                                            }
                                        }
                                    },
                                    this.mBackgroundExecutor);
            this.mGameSessionServiceConnector.post(
                    new ServiceConnector
                            .VoidJob() { // from class:
                                         // com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda6
                        public final void runNoResult(Object obj) {
                            GameServiceProviderInstanceImpl gameServiceProviderInstanceImpl =
                                    GameServiceProviderInstanceImpl.this;
                            int i2 = i;
                            GameSessionRecord gameSessionRecord2 = gameSessionRecord;
                            GameSessionViewHostConfiguration gameSessionViewHostConfiguration2 =
                                    gameSessionViewHostConfiguration;
                            AndroidFuture androidFuture = whenCompleteAsync;
                            gameServiceProviderInstanceImpl.getClass();
                            ((IGameSessionService) obj)
                                    .create(
                                            gameServiceProviderInstanceImpl.mGameSessionController,
                                            new CreateGameSessionRequest(
                                                    i2,
                                                    gameSessionRecord2.mRootComponentName
                                                            .getPackageName()),
                                            gameSessionViewHostConfiguration2,
                                            androidFuture);
                        }
                    });
        }
    }

    public final void destroyGameSessionFromRecordLocked(GameSessionRecord gameSessionRecord) {
        SurfaceControlViewHost.SurfacePackage surfacePackage = gameSessionRecord.mSurfacePackage;
        if (surfacePackage != null) {
            try {
                this.mWindowManagerInternal.removeTrustedTaskOverlay(
                        gameSessionRecord.mTaskId, surfacePackage);
            } catch (IllegalArgumentException unused) {
                Slog.i(
                        "GameServiceProviderInstance",
                        "Failed to remove task overlay. This is expected if the task is already"
                            + " destroyed: "
                                + gameSessionRecord);
            }
        }
        IGameSession iGameSession = gameSessionRecord.mIGameSession;
        if (iGameSession != null) {
            try {
                iGameSession.onDestroyed();
            } catch (RemoteException e) {
                Slog.w(
                        "GameServiceProviderInstance",
                        "Failed to destroy session: " + gameSessionRecord,
                        e);
            }
        }
        if (this.mGameSessions.isEmpty()) {
            this.mGameSessionServiceConnector.unbind();
        }
    }

    public final void gameTaskStartedLocked(final GameTaskInfo gameTaskInfo) {
        if (this.mIsRunning) {
            if (((GameSessionRecord) this.mGameSessions.get(Integer.valueOf(gameTaskInfo.mTaskId)))
                    != null) {
                UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Existing game session found for task (id: "),
                        gameTaskInfo.mTaskId,
                        ") creation. Ignoring.",
                        "GameServiceProviderInstance");
                return;
            }
            int i = gameTaskInfo.mTaskId;
            this.mGameSessions.put(
                    Integer.valueOf(i),
                    new GameSessionRecord(
                            i,
                            GameSessionRecord.State.NO_GAME_SESSION_REQUESTED,
                            gameTaskInfo.mComponentName,
                            null,
                            null));
            this.mGameServiceConnector.post(
                    new ServiceConnector
                            .VoidJob() { // from class:
                                         // com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda4
                        public final void runNoResult(Object obj) {
                            GameTaskInfo gameTaskInfo2 = GameTaskInfo.this;
                            ((IGameService) obj)
                                    .gameStarted(
                                            new GameStartedEvent(
                                                    gameTaskInfo2.mTaskId,
                                                    gameTaskInfo2.mComponentName.getPackageName()));
                        }
                    });
        }
    }

    public final void onForegroundActivitiesChangedLocked(int i) {
        if (this.mPidToPackageMap.containsKey(Integer.valueOf(i))) {
            return;
        }
        String packageNameByPid = this.mActivityManagerInternal.getPackageNameByPid(i);
        if (TextUtils.isEmpty(packageNameByPid)) {
            return;
        }
        Iterator it = this.mGameSessions.values().iterator();
        while (it.hasNext()) {
            if (packageNameByPid.equals(
                    ((GameSessionRecord) it.next()).mRootComponentName.getPackageName())) {
                this.mPidToPackageMap.put(Integer.valueOf(i), packageNameByPid);
                int intValue =
                        ((Integer)
                                                this.mPackageNameToProcessCountMap.getOrDefault(
                                                        packageNameByPid, 0))
                                        .intValue()
                                + 1;
                this.mPackageNameToProcessCountMap.put(packageNameByPid, Integer.valueOf(intValue));
                if (intValue > 0) {
                    for (GameSessionRecord gameSessionRecord : this.mGameSessions.values()) {
                        if (gameSessionRecord.mState
                                        == GameSessionRecord.State.GAME_SESSION_ENDED_PROCESS_DEATH
                                && packageNameByPid.equals(
                                        gameSessionRecord.mRootComponentName.getPackageName())) {
                            ConcurrentHashMap concurrentHashMap = this.mGameSessions;
                            int i2 = gameSessionRecord.mTaskId;
                            concurrentHashMap.put(
                                    Integer.valueOf(i2),
                                    new GameSessionRecord(
                                            i2,
                                            GameSessionRecord.State.NO_GAME_SESSION_REQUESTED,
                                            gameSessionRecord.mRootComponentName,
                                            null,
                                            null));
                            createGameSessionLocked(i2);
                        }
                    }
                    return;
                }
                return;
            }
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
            for (GameSessionRecord gameSessionRecord : this.mGameSessions.values()) {
                if (gameSessionRecord.mIGameSession != null
                        && str.equals(gameSessionRecord.mRootComponentName.getPackageName())) {
                    GameTaskInfoProvider gameTaskInfoProvider = this.mGameTaskInfoProvider;
                    int i2 = gameSessionRecord.mTaskId;
                    ActivityManager.RunningTaskInfo runningTaskInfo =
                            gameTaskInfoProvider.getRunningTaskInfo(i2);
                    if (runningTaskInfo == null || !runningTaskInfo.isVisible) {
                        this.mGameSessions.put(
                                Integer.valueOf(i2),
                                new GameSessionRecord(
                                        gameSessionRecord.mTaskId,
                                        GameSessionRecord.State.GAME_SESSION_ENDED_PROCESS_DEATH,
                                        gameSessionRecord.mRootComponentName,
                                        null,
                                        null));
                        destroyGameSessionFromRecordLocked(gameSessionRecord);
                    }
                }
            }
        }
    }

    public final void onTaskFocusChangedLocked(int i, boolean z) {
        GameTaskInfo gameTaskInfo;
        ComponentName componentName;
        GameSessionRecord gameSessionRecord =
                (GameSessionRecord) this.mGameSessions.get(Integer.valueOf(i));
        if (gameSessionRecord != null) {
            IGameSession iGameSession = gameSessionRecord.mIGameSession;
            if (iGameSession == null) {
                return;
            }
            try {
                iGameSession.onTaskFocusChanged(z);
                return;
            } catch (RemoteException unused) {
                Slog.w(
                        "GameServiceProviderInstance",
                        "Failed to notify session of task focus change: " + gameSessionRecord);
                return;
            }
        }
        if (z) {
            GameTaskInfoProvider gameTaskInfoProvider = this.mGameTaskInfoProvider;
            synchronized (gameTaskInfoProvider.mLock) {
                try {
                    gameTaskInfo =
                            (GameTaskInfo)
                                    gameTaskInfoProvider.mGameTaskInfoCache.get(Integer.valueOf(i));
                    if (gameTaskInfo == null) {
                        ActivityManager.RunningTaskInfo runningTaskInfo =
                                gameTaskInfoProvider.getRunningTaskInfo(i);
                        gameTaskInfo =
                                (runningTaskInfo == null
                                                || (componentName = runningTaskInfo.baseActivity)
                                                        == null)
                                        ? null
                                        : gameTaskInfoProvider.generateGameInfo(i, componentName);
                    }
                } finally {
                }
            }
            if (gameTaskInfo == null) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i, "No task info for focused task: ", "GameServiceProviderInstance");
            } else if (gameTaskInfo.mIsGameTask) {
                gameTaskStartedLocked(gameTaskInfo);
            }
        }
    }

    public final void startLocked() {
        if (this.mIsRunning) {
            return;
        }
        this.mIsRunning = true;
        this.mGameServiceConnector.setServiceLifecycleCallbacks(
                this.mGameServiceLifecycleCallbacks);
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
                Slog.w(
                        "GameServiceProviderInstance",
                        "Failed to unregister task stack listener",
                        e2);
            }
            this.mWindowManagerInternal.unregisterTaskSystemBarsListener(
                    this.mTaskSystemBarsVisibilityListener);
            Iterator it = this.mGameSessions.values().iterator();
            while (it.hasNext()) {
                destroyGameSessionFromRecordLocked((GameSessionRecord) it.next());
            }
            this.mGameSessions.clear();
            this.mGameServiceConnector
                    .post(new GameServiceProviderInstanceImpl$$ExternalSyntheticLambda1())
                    .whenComplete(
                            new BiConsumer() { // from class:
                                               // com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda2
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    GameServiceProviderInstanceImpl.this.mGameServiceConnector
                                            .unbind();
                                }
                            });
            this.mGameSessionServiceConnector.unbind();
            this.mGameServiceConnector.setServiceLifecycleCallbacks(
                    (ServiceConnector.ServiceLifecycleCallbacks) null);
            this.mGameSessionServiceConnector.setServiceLifecycleCallbacks(
                    (ServiceConnector.ServiceLifecycleCallbacks) null);
        }
    }

    public void takeScreenshot(final int i, final AndroidFuture androidFuture) {
        synchronized (this.mLock) {
            try {
                final GameSessionRecord gameSessionRecord =
                        (GameSessionRecord) this.mGameSessions.get(Integer.valueOf(i));
                if (gameSessionRecord != null) {
                    SurfaceControlViewHost.SurfacePackage surfacePackage =
                            gameSessionRecord.mSurfacePackage;
                    final SurfaceControl surfaceControl =
                            surfacePackage != null ? surfacePackage.getSurfaceControl() : null;
                    this.mBackgroundExecutor.execute(
                            new Runnable() { // from class:
                                             // com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    GameServiceProviderInstanceImpl
                                            gameServiceProviderInstanceImpl =
                                                    GameServiceProviderInstanceImpl.this;
                                    SurfaceControl surfaceControl2 = surfaceControl;
                                    int i2 = i;
                                    final AndroidFuture androidFuture2 = androidFuture;
                                    GameSessionRecord gameSessionRecord2 = gameSessionRecord;
                                    gameServiceProviderInstanceImpl.getClass();
                                    ScreenCapture.LayerCaptureArgs.Builder builder =
                                            new ScreenCapture.LayerCaptureArgs.Builder(
                                                    (SurfaceControl) null);
                                    if (surfaceControl2 != null) {
                                        builder.setExcludeLayers(
                                                new SurfaceControl[] {surfaceControl2});
                                    }
                                    Bitmap captureTaskBitmap =
                                            gameServiceProviderInstanceImpl.mWindowManagerService
                                                    .captureTaskBitmap(i2, builder);
                                    if (captureTaskBitmap == null) {
                                        Slog.w(
                                                "GameServiceProviderInstance",
                                                "Could not get bitmap for id: " + i2);
                                        androidFuture2.complete(
                                                GameScreenshotResult.createInternalErrorResult());
                                        return;
                                    }
                                    ActivityManager.RunningTaskInfo runningTaskInfo =
                                            gameServiceProviderInstanceImpl.mGameTaskInfoProvider
                                                    .getRunningTaskInfo(i2);
                                    if (runningTaskInfo == null) {
                                        Slog.w(
                                                "GameServiceProviderInstance",
                                                "Could not get running task info for id: " + i2);
                                        androidFuture2.complete(
                                                GameScreenshotResult.createInternalErrorResult());
                                    }
                                    gameServiceProviderInstanceImpl.mScreenshotHelper
                                            .takeScreenshot(
                                                    new ScreenshotRequest.Builder(3, 5)
                                                            .setTopComponent(
                                                                    gameSessionRecord2
                                                                            .mRootComponentName)
                                                            .setTaskId(i2)
                                                            .setUserId(
                                                                    gameServiceProviderInstanceImpl
                                                                            .mUserHandle
                                                                            .getIdentifier())
                                                            .setBitmap(captureTaskBitmap)
                                                            .setBoundsOnScreen(
                                                                    runningTaskInfo.configuration
                                                                            .windowConfiguration
                                                                            .getBounds())
                                                            .setInsets(Insets.NONE)
                                                            .build(),
                                                    BackgroundThread.getHandler(),
                                                    new Consumer() { // from class:
                                                                     // com.android.server.app.GameServiceProviderInstanceImpl$$ExternalSyntheticLambda3
                                                        @Override // java.util.function.Consumer
                                                        public final void accept(Object obj) {
                                                            AndroidFuture androidFuture3 =
                                                                    androidFuture2;
                                                            if (((Uri) obj) == null) {
                                                                androidFuture3.complete(
                                                                        GameScreenshotResult
                                                                                .createInternalErrorResult());
                                                            } else {
                                                                androidFuture3.complete(
                                                                        GameScreenshotResult
                                                                                .createSuccessResult());
                                                            }
                                                        }
                                                    });
                                }
                            });
                } else {
                    Slog.w("GameServiceProviderInstance", "No game session found for id: " + i);
                    androidFuture.complete(GameScreenshotResult.createInternalErrorResult());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
