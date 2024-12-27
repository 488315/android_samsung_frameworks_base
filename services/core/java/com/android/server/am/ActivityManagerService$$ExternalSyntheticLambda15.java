package com.android.server.am;

import android.os.DropBoxManager;

import java.util.ArrayList;

public final /* synthetic */ class ActivityManagerService$$ExternalSyntheticLambda15
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ ActivityManagerService$$ExternalSyntheticLambda15(
            DropBoxManager dropBoxManager, String str, String str2) {
        this.f$0 = dropBoxManager;
        this.f$1 = str;
        this.f$2 = str2;
    }

    public /* synthetic */ ActivityManagerService$$ExternalSyntheticLambda15(
            ActivityManagerService activityManagerService, ArrayList arrayList, String str) {
        this.f$0 = activityManagerService;
        this.f$2 = arrayList;
        this.f$1 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((DropBoxManager) this.f$0).addText(this.f$1, (String) this.f$2);
                return;
            default:
                ActivityManagerService activityManagerService = (ActivityManagerService) this.f$0;
                ArrayList arrayList = (ArrayList) this.f$2;
                String str = this.f$1;
                activityManagerService.getClass();
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        int size = arrayList.size();
                        for (int i = 0; i < size; i++) {
                            ((ProcessRecord) arrayList.get(i))
                                    .killLocked(13, 12, str, str, true, true);
                        }
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
                return;
        }
    }
}
