package com.android.server.app;

import android.util.Slog;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class GameServiceController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ GameServiceController f$0;

    public /* synthetic */ GameServiceController$$ExternalSyntheticLambda0(
            GameServiceController gameServiceController) {
        this.f$0 = gameServiceController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        GameServiceController gameServiceController = this.f$0;
        if (gameServiceController.mHasBootCompleted) {
            synchronized (gameServiceController.mLock) {
                try {
                    GameServiceConfiguration gameServiceConfiguration =
                            gameServiceController.mGameServiceProviderSelector.get(
                                    gameServiceController.mCurrentForegroundUser,
                                    gameServiceController.mGameServiceProviderOverride);
                    String str =
                            gameServiceConfiguration == null
                                    ? null
                                    : gameServiceConfiguration.mPackageName;
                    GameServiceConfiguration.GameServiceComponentConfiguration
                            gameServiceComponentConfiguration =
                                    gameServiceConfiguration == null
                                            ? null
                                            : gameServiceConfiguration
                                                    .mGameServiceComponentConfiguration;
                    gameServiceController.evaluateGameServiceProviderPackageChangedListenerLocked(
                            str);
                    if (!Objects.equals(
                            gameServiceComponentConfiguration,
                            gameServiceController.mActiveGameServiceComponentConfiguration)) {
                        if (gameServiceController.mGameServiceProviderInstance != null) {
                            Slog.i(
                                    "GameServiceController",
                                    "Stopping Game Service provider: "
                                            + gameServiceController
                                                    .mActiveGameServiceComponentConfiguration);
                            GameServiceProviderInstanceImpl gameServiceProviderInstanceImpl =
                                    gameServiceController.mGameServiceProviderInstance;
                            synchronized (gameServiceProviderInstanceImpl.mLock) {
                                gameServiceProviderInstanceImpl.stopLocked();
                            }
                            gameServiceController.mGameServiceProviderInstance = null;
                        }
                        gameServiceController.mActiveGameServiceComponentConfiguration =
                                gameServiceComponentConfiguration;
                        if (gameServiceController.mActiveGameServiceComponentConfiguration
                                == null) {
                            return;
                        }
                        Slog.i(
                                "GameServiceController",
                                "Starting Game Service provider: "
                                        + gameServiceController
                                                .mActiveGameServiceComponentConfiguration);
                        gameServiceController.mGameServiceProviderInstance =
                                gameServiceController.mGameServiceProviderInstanceFactory.create(
                                        gameServiceController
                                                .mActiveGameServiceComponentConfiguration);
                        GameServiceProviderInstanceImpl gameServiceProviderInstanceImpl2 =
                                gameServiceController.mGameServiceProviderInstance;
                        synchronized (gameServiceProviderInstanceImpl2.mLock) {
                            gameServiceProviderInstanceImpl2.startLocked();
                        }
                    }
                } finally {
                }
            }
        }
    }
}
