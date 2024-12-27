package com.android.server.am;

public final class ProcessCpusetController {
    public static ProcessCpusetController mController;
    public final ActivityManagerService mAm;

    public ProcessCpusetController(ActivityManagerService activityManagerService) {
        this.mAm = activityManagerService;
        activityManagerService.mContext.getPackageName();
    }
}
