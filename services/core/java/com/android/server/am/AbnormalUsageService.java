package com.android.server.am;

public final class AbnormalUsageService {
    public final ActivityManagerService mAm;

    public AbnormalUsageService(ActivityManagerService activityManagerService) {
        this.mAm = activityManagerService;
    }
}
