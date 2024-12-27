package com.android.server.app;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.service.games.IGameService;
import android.service.games.IGameSessionService;

import com.android.internal.infra.ServiceConnector;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ScreenshotHelper;
import com.android.server.LocalServices;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.wm.WindowManagerService;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GameServiceProviderInstanceFactoryImpl {
    public final Context mContext;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class GameServiceConnector extends ServiceConnector.Impl {
        public final long getAutoDisconnectTimeoutMs() {
            return 0L;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class GameSessionServiceConnector extends ServiceConnector.Impl {
        public final long getAutoDisconnectTimeoutMs() {
            return 0L;
        }
    }

    public GameServiceProviderInstanceFactoryImpl(Context context) {
        this.mContext = context;
    }

    public final GameServiceProviderInstanceImpl create(
            GameServiceConfiguration.GameServiceComponentConfiguration
                    gameServiceComponentConfiguration) {
        UserHandle userHandle = gameServiceComponentConfiguration.mUserHandle;
        IActivityTaskManager service = ActivityTaskManager.getService();
        final int i = 0;
        final int i2 = 1;
        return new GameServiceProviderInstanceImpl(
                userHandle,
                BackgroundThread.getExecutor(),
                new GameTaskInfoProvider(
                        userHandle,
                        service,
                        new GameClassifierImpl(this.mContext.getPackageManager())),
                ActivityManager.getService(),
                (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class),
                service,
                (WindowManagerService) ServiceManager.getService("window"),
                (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class),
                (ActivityTaskManagerInternal)
                        LocalServices.getService(ActivityTaskManagerInternal.class),
                new GameServiceConnector(
                        this.mContext,
                        new Intent("android.service.games.action.GAME_SERVICE")
                                .setComponent(
                                        gameServiceComponentConfiguration
                                                .mGameServiceComponentName),
                        1048576,
                        gameServiceComponentConfiguration.mUserHandle.getIdentifier(),
                        new Function() { // from class:
                                         // com.android.server.app.GameServiceProviderInstanceFactoryImpl$GameServiceConnector$$ExternalSyntheticLambda0
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                IBinder iBinder = (IBinder) obj;
                                switch (i) {
                                    case 0:
                                        return IGameService.Stub.asInterface(iBinder);
                                    default:
                                        return IGameSessionService.Stub.asInterface(iBinder);
                                }
                            }
                        }),
                new GameSessionServiceConnector(
                        this.mContext,
                        new Intent("android.service.games.action.GAME_SESSION_SERVICE")
                                .setComponent(
                                        gameServiceComponentConfiguration
                                                .mGameSessionServiceComponentName),
                        135790592,
                        gameServiceComponentConfiguration.mUserHandle.getIdentifier(),
                        new Function() { // from class:
                                         // com.android.server.app.GameServiceProviderInstanceFactoryImpl$GameServiceConnector$$ExternalSyntheticLambda0
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                IBinder iBinder = (IBinder) obj;
                                switch (i2) {
                                    case 0:
                                        return IGameService.Stub.asInterface(iBinder);
                                    default:
                                        return IGameSessionService.Stub.asInterface(iBinder);
                                }
                            }
                        }),
                new ScreenshotHelper(this.mContext));
    }
}
