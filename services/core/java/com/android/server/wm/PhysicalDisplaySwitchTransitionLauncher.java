package com.android.server.wm;

import android.content.Context;

public final class PhysicalDisplaySwitchTransitionLauncher {
    public final ActivityTaskManagerService mAtmService;
    public final Context mContext;
    public final DisplayContent mDisplayContent;
    public Transition mTransition;
    public final TransitionController mTransitionController;
    public boolean mShouldRequestTransitionOnDisplaySwitch = false;
    public DeviceStateController.DeviceState mDeviceState =
            DeviceStateController.DeviceState.UNKNOWN;

    public PhysicalDisplaySwitchTransitionLauncher(
            DisplayContent displayContent,
            ActivityTaskManagerService activityTaskManagerService,
            Context context,
            TransitionController transitionController) {
        this.mDisplayContent = displayContent;
        this.mAtmService = activityTaskManagerService;
        this.mContext = context;
        this.mTransitionController = transitionController;
    }
}
