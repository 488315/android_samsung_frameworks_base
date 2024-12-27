package com.android.server.am;

import android.content.IIntentReceiver;
import android.content.Intent;
import android.os.Binder;
import android.util.EventLog;
import com.android.server.SystemServiceManager;
import com.android.server.am.UserController;
import com.android.server.pm.UserJourneyLogger;
import com.android.server.utils.TimingsTraceAndSlog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class UserController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UserController f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ UserState f$2;
    public final /* synthetic */ boolean f$3;

    public /* synthetic */ UserController$$ExternalSyntheticLambda1(UserController userController, int i, UserState userState, boolean z, int i2) {
        this.$r8$classId = i2;
        this.f$0 = userController;
        this.f$1 = i;
        this.f$2 = userState;
        this.f$3 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                UserController userController = this.f$0;
                userController.mHandler.post(new UserController$$ExternalSyntheticLambda1(userController, this.f$1, this.f$2, this.f$3, 1));
                return;
            case 1:
                UserController userController2 = this.f$0;
                int i = this.f$1;
                UserState userState = this.f$2;
                boolean z = this.f$3;
                userController2.getClass();
                TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
                timingsTraceAndSlog.traceBegin("finishUserStopping-" + i + "-[stopUser]");
                EventLog.writeEvent(30073, i);
                synchronized (userController2.mLock) {
                    try {
                        if (userState.state != 4) {
                            UserJourneyLogger.UserJourneySession logUserJourneyFinishWithError = userController2.mInjector.getUserManager().mUserJourneyLogger.logUserJourneyFinishWithError(-1, userController2.getUserInfo(i), 5, 3);
                            if (logUserJourneyFinishWithError != null) {
                                userController2.mHandler.removeMessages(200, logUserJourneyFinishWithError);
                            } else {
                                userController2.mInjector.getUserManager().mUserJourneyLogger.logUserJourneyFinishWithError(-1, userController2.getUserInfo(i), 5, 0);
                            }
                        } else {
                            userState.setState(5);
                            TimingsTraceAndSlog timingsTraceAndSlog2 = new TimingsTraceAndSlog();
                            timingsTraceAndSlog2.traceBegin("setUserState-STATE_SHUTDOWN-" + i + "-[stopUser]");
                            userController2.mInjector.getUserManagerInternal().setUserState(i, userState.state);
                            timingsTraceAndSlog2.traceEnd();
                            userController2.mInjector.mService.mBatteryStatsService.noteEvent(16391, Integer.toString(i), i);
                            SystemServiceManager systemServiceManager = userController2.mInjector.mService.mSystemServiceManager;
                            systemServiceManager.getClass();
                            EventLog.writeEvent(30086, i);
                            systemServiceManager.onUser(i, "Stop");
                            UserController$$ExternalSyntheticLambda1 userController$$ExternalSyntheticLambda1 = new UserController$$ExternalSyntheticLambda1(userController2, i, userState, z, 2);
                            if (userController2.mInjector.getUserManager().isPreCreated(i)) {
                                userController$$ExternalSyntheticLambda1.run();
                            } else {
                                Intent intent = new Intent("android.intent.action.ACTION_SHUTDOWN");
                                IIntentReceiver anonymousClass5 = new UserController.AnonymousClass5(i, userController$$ExternalSyntheticLambda1, (byte) 0);
                                UserController.asyncTraceBegin(i, "broadcast-ACTION_SHUTDOWN-" + i + "-[stopUser]");
                                userController2.mInjector.broadcastIntent(intent, anonymousClass5, null, null, ActivityManagerService.MY_PID, Binder.getCallingUid(), Binder.getCallingPid(), i);
                            }
                        }
                    } finally {
                    }
                }
                timingsTraceAndSlog.traceEnd();
                return;
            case 2:
                UserController userController3 = this.f$0;
                userController3.mHandler.post(new UserController$$ExternalSyntheticLambda1(userController3, this.f$1, this.f$2, this.f$3, 3));
                return;
            default:
                UserController userController4 = this.f$0;
                int i2 = this.f$1;
                UserState userState2 = this.f$2;
                boolean z2 = this.f$3;
                userController4.getClass();
                TimingsTraceAndSlog timingsTraceAndSlog3 = new TimingsTraceAndSlog();
                timingsTraceAndSlog3.traceBegin("finishUserStopped-" + i2 + "-[stopUser]");
                userController4.finishUserStopped(userState2, z2);
                timingsTraceAndSlog3.traceEnd();
                return;
        }
    }
}
