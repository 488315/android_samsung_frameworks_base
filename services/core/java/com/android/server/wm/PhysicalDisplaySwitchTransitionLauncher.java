package com.android.server.wm;

import android.content.Context;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
