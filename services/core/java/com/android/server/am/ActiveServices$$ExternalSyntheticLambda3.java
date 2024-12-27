package com.android.server.am;

import android.os.SystemClock;

import java.util.ArrayList;

public final /* synthetic */ class ActiveServices$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ActiveServices$$ExternalSyntheticLambda3(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ActiveServices activeServices = (ActiveServices) obj;
                activeServices.getClass();
                long uptimeMillis = SystemClock.uptimeMillis();
                ActivityManagerService activityManagerService = activeServices.mAm;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        activeServices.rescheduleServiceRestartIfPossibleLocked(
                                activeServices.getExtraRestartTimeInBetweenLocked(),
                                activeServices.mAm.mConstants.SERVICE_MIN_RESTART_TIME_BETWEEN,
                                uptimeMillis,
                                "other");
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                return;
            case 1:
                ForegroundServiceDelegation foregroundServiceDelegation =
                        ((ServiceRecord) obj).mFgsDelegation;
                foregroundServiceDelegation.mConnection.onServiceDisconnected(
                        foregroundServiceDelegation.mOptions.getComponentName());
                return;
            default:
                ArrayList arrayList = (ArrayList) obj;
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    ((ServiceRecord) arrayList.get(i2)).cancelNotification();
                }
                return;
        }
    }
}
