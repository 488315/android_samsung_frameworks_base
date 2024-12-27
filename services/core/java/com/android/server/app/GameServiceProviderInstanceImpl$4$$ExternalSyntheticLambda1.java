package com.android.server.app;

import android.util.Slog;

import com.android.server.wm.ActivityRecord;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda11;
import com.android.server.wm.Task;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class GameServiceProviderInstanceImpl$4$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ GameServiceProviderInstanceImpl$4$$ExternalSyntheticLambda1(
            GameServiceProviderInstanceImpl.AnonymousClass4 anonymousClass4, int i) {
        this.f$0 = anonymousClass4;
        this.f$1 = i;
    }

    public /* synthetic */ GameServiceProviderInstanceImpl$4$$ExternalSyntheticLambda1(
            GameServiceProviderInstanceImpl.AnonymousClass6 anonymousClass6, int i) {
        this.f$0 = anonymousClass6;
        this.f$1 = i;
    }

    public /* synthetic */ GameServiceProviderInstanceImpl$4$$ExternalSyntheticLambda1(
            GameServiceProviderInstanceImpl.AnonymousClass7 anonymousClass7, int i) {
        this.f$0 = anonymousClass7;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                GameServiceProviderInstanceImpl.AnonymousClass4 anonymousClass4 =
                        (GameServiceProviderInstanceImpl.AnonymousClass4) this.f$0;
                int i = this.f$1;
                GameServiceProviderInstanceImpl gameServiceProviderInstanceImpl =
                        GameServiceProviderInstanceImpl.this;
                synchronized (gameServiceProviderInstanceImpl.mLock) {
                    try {
                        if (gameServiceProviderInstanceImpl.mGameSessions.containsKey(
                                Integer.valueOf(i))) {
                            GameSessionRecord gameSessionRecord =
                                    (GameSessionRecord)
                                            gameServiceProviderInstanceImpl.mGameSessions.remove(
                                                    Integer.valueOf(i));
                            if (gameSessionRecord != null) {
                                gameServiceProviderInstanceImpl.destroyGameSessionFromRecordLocked(
                                        gameSessionRecord);
                            }
                            return;
                        }
                        return;
                    } finally {
                    }
                }
            case 1:
                GameServiceProviderInstanceImpl.AnonymousClass6 anonymousClass6 =
                        (GameServiceProviderInstanceImpl.AnonymousClass6) this.f$0;
                int i2 = this.f$1;
                GameServiceProviderInstanceImpl gameServiceProviderInstanceImpl2 =
                        GameServiceProviderInstanceImpl.this;
                synchronized (gameServiceProviderInstanceImpl2.mLock) {
                    gameServiceProviderInstanceImpl2.createGameSessionLocked(i2);
                }
                return;
            default:
                GameServiceProviderInstanceImpl.AnonymousClass7 anonymousClass7 =
                        (GameServiceProviderInstanceImpl.AnonymousClass7) this.f$0;
                int i3 = this.f$1;
                GameServiceProviderInstanceImpl gameServiceProviderInstanceImpl3 =
                        GameServiceProviderInstanceImpl.this;
                synchronized (gameServiceProviderInstanceImpl3.mLock) {
                    try {
                        GameSessionRecord gameSessionRecord2 =
                                (GameSessionRecord)
                                        gameServiceProviderInstanceImpl3.mGameSessions.get(
                                                Integer.valueOf(i3));
                        if (gameSessionRecord2 == null) {
                            return;
                        }
                        String packageName = gameSessionRecord2.mRootComponentName.getPackageName();
                        if (packageName == null) {
                            return;
                        }
                        ActivityTaskManagerService.LocalService localService =
                                (ActivityTaskManagerService.LocalService)
                                        gameServiceProviderInstanceImpl3
                                                .mActivityTaskManagerInternal;
                        WindowManagerGlobalLock windowManagerGlobalLock =
                                ActivityTaskManagerService.this.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                Task anyTaskForId =
                                        ActivityTaskManagerService.this.mRootWindowContainer
                                                .anyTaskForId(i3, 0, null, false);
                                if (anyTaskForId == null) {
                                    Slog.w(
                                            "ActivityTaskManager",
                                            "Failed to restart Activity. No task found for id: "
                                                    + i3);
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                } else {
                                    ActivityRecord activity =
                                            anyTaskForId.getActivity(
                                                    new ActivityTaskManagerService$$ExternalSyntheticLambda11(
                                                            packageName, 1));
                                    if (activity == null) {
                                        Slog.w(
                                                "ActivityTaskManager",
                                                "Failed to restart Activity. No Activity found for"
                                                    + " package name: "
                                                        + packageName
                                                        + " in task: "
                                                        + i3);
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                    } else {
                                        activity.restartProcessIfVisible();
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                    }
                                }
                            } catch (Throwable th) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                        return;
                    } finally {
                    }
                }
        }
    }
}
