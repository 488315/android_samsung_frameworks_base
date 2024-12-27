package com.android.server.app;

import android.app.ActivityManager;
import android.app.IActivityTaskManager;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.LruCache;
import android.util.Slog;

public final class GameTaskInfoProvider {
    public final IActivityTaskManager mActivityTaskManager;
    public final GameClassifierImpl mGameClassifier;
    public final UserHandle mUserHandle;
    public final Object mLock = new Object();
    public final LruCache mGameTaskInfoCache = new LruCache(50);

    public GameTaskInfoProvider(
            UserHandle userHandle,
            IActivityTaskManager iActivityTaskManager,
            GameClassifierImpl gameClassifierImpl) {
        this.mUserHandle = userHandle;
        this.mActivityTaskManager = iActivityTaskManager;
        this.mGameClassifier = gameClassifierImpl;
    }

    public final GameTaskInfo generateGameInfo(int i, ComponentName componentName) {
        GameClassifierImpl gameClassifierImpl = this.mGameClassifier;
        String packageName = componentName.getPackageName();
        UserHandle userHandle = this.mUserHandle;
        gameClassifierImpl.getClass();
        boolean z = false;
        try {
            if (gameClassifierImpl.mPackageManager.getApplicationInfoAsUser(
                                    packageName, 0, userHandle.getIdentifier())
                            .category
                    == 0) {
                z = true;
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        GameTaskInfo gameTaskInfo = new GameTaskInfo(i, componentName, z);
        synchronized (this.mLock) {
            this.mGameTaskInfoCache.put(Integer.valueOf(i), gameTaskInfo);
        }
        return gameTaskInfo;
    }

    public final ActivityManager.RunningTaskInfo getRunningTaskInfo(int i) {
        try {
            for (ActivityManager.RunningTaskInfo runningTaskInfo :
                    this.mActivityTaskManager.getTasks(Integer.MAX_VALUE, false, false, -1)) {
                if (runningTaskInfo.taskId == i) {
                    return runningTaskInfo;
                }
            }
            return null;
        } catch (RemoteException unused) {
            Slog.w("GameTaskInfoProvider", "Failed to fetch running tasks");
            return null;
        }
    }
}
