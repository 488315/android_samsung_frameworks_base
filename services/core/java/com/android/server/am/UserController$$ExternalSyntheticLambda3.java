package com.android.server.am;

import android.content.pm.UserInfo;
import android.content.pm.UserProperties;
import android.os.Handler;
import android.os.IProgressListener;
import android.util.ArraySet;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class UserController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UserController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ UserController$$ExternalSyntheticLambda3(
            UserController userController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = userController;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                UserController userController = this.f$0;
                userController.finishUserBoot((UserState) this.f$1, null);
                userController.startProfiles();
                ArraySet arraySet = new ArraySet();
                List users = userController.mInjector.getUserManager().getUsers(true, true, true);
                int i = 0;
                while (true) {
                    ArrayList arrayList = (ArrayList) users;
                    if (i >= arrayList.size()) {
                        synchronized (userController.mLock) {
                            userController.stopExcessRunningUsersLU(
                                    userController.mMaxRunningUsers, arraySet);
                        }
                        return;
                    } else {
                        int i2 = ((UserInfo) arrayList.get(i)).id;
                        UserProperties userProperties =
                                userController
                                        .mInjector
                                        .getUserManagerInternal()
                                        .getUserProperties(i2);
                        if (userProperties != null && userProperties.getAlwaysVisible()) {
                            arraySet.add(Integer.valueOf(i2));
                        }
                        i++;
                    }
                }
                break;
            case 1:
                this.f$0.mInjector.getUserManager().makeInitialized(((UserInfo) this.f$1).id);
                return;
            case 2:
                UserController userController2 = this.f$0;
                Pair pair = (Pair) this.f$1;
                userController2.getClass();
                int i3 = ((UserInfo) pair.second).id;
                userController2.mHandler.removeMessages(120);
                Handler handler = userController2.mHandler;
                handler.sendMessage(handler.obtainMessage(120, i3, 0));
                return;
            default:
                UserController userController3 = this.f$0;
                UserController.PendingUserStart pendingUserStart =
                        (UserController.PendingUserStart) this.f$1;
                userController3.getClass();
                int i4 = pendingUserStart.userId;
                IProgressListener iProgressListener = pendingUserStart.unlockListener;
                userController3.checkCallingHasOneOfThosePermissions(
                        "startUser", "android.permission.INTERACT_ACROSS_USERS_FULL");
                userController3.startUserNoChecks(
                        i4, 0, pendingUserStart.userStartMode, iProgressListener);
                return;
        }
    }
}
