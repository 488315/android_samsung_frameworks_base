package com.android.server.app;

import android.content.ComponentName;
import android.util.Slog;

import com.android.internal.infra.AndroidFuture;

public final /* synthetic */ class GameServiceProviderInstanceImpl$4$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ GameServiceProviderInstanceImpl$4$$ExternalSyntheticLambda0(
            GameServiceProviderInstanceImpl.AnonymousClass4 anonymousClass4,
            int i,
            ComponentName componentName) {
        this.f$0 = anonymousClass4;
        this.f$1 = i;
        this.f$2 = componentName;
    }

    public /* synthetic */ GameServiceProviderInstanceImpl$4$$ExternalSyntheticLambda0(
            GameServiceProviderInstanceImpl.AnonymousClass7 anonymousClass7,
            int i,
            AndroidFuture androidFuture) {
        this.f$0 = anonymousClass7;
        this.f$1 = i;
        this.f$2 = androidFuture;
    }

    @Override // java.lang.Runnable
    public final void run() {
        GameTaskInfo gameTaskInfo;
        switch (this.$r8$classId) {
            case 0:
                GameServiceProviderInstanceImpl.AnonymousClass4 anonymousClass4 =
                        (GameServiceProviderInstanceImpl.AnonymousClass4) this.f$0;
                int i = this.f$1;
                ComponentName componentName = (ComponentName) this.f$2;
                GameServiceProviderInstanceImpl gameServiceProviderInstanceImpl =
                        anonymousClass4.this$0;
                GameTaskInfoProvider gameTaskInfoProvider =
                        gameServiceProviderInstanceImpl.mGameTaskInfoProvider;
                synchronized (gameTaskInfoProvider.mLock) {
                    try {
                        gameTaskInfo =
                                (GameTaskInfo)
                                        gameTaskInfoProvider.mGameTaskInfoCache.get(
                                                Integer.valueOf(i));
                        if (gameTaskInfo != null) {
                            if (gameTaskInfo.mComponentName.equals(componentName)) {
                                Slog.w(
                                        "GameTaskInfoProvider",
                                        "Found cached task info for taskId "
                                                + i
                                                + " but cached component name "
                                                + gameTaskInfo.mComponentName
                                                + " does not match "
                                                + componentName);
                            }
                        }
                        gameTaskInfo = gameTaskInfoProvider.generateGameInfo(i, componentName);
                    } finally {
                    }
                }
                if (gameTaskInfo.mIsGameTask) {
                    synchronized (gameServiceProviderInstanceImpl.mLock) {
                        gameServiceProviderInstanceImpl.gameTaskStartedLocked(gameTaskInfo);
                    }
                    return;
                }
                return;
            default:
                ((GameServiceProviderInstanceImpl.AnonymousClass7) this.f$0)
                        .this$0.takeScreenshot(this.f$1, (AndroidFuture) this.f$2);
                return;
        }
    }
}
