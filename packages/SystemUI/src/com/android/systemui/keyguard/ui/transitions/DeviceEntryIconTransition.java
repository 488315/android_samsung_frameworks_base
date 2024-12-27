package com.android.systemui.keyguard.ui.transitions;

import kotlinx.coroutines.flow.Flow;

public interface DeviceEntryIconTransition {
    Flow getDeviceEntryParentViewAlpha();
}
