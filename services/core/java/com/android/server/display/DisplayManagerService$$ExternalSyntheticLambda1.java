package com.android.server.display;

public final /* synthetic */ class DisplayManagerService$$ExternalSyntheticLambda1 {
    public final /* synthetic */ DisplayManagerService f$0;

    public /* synthetic */ DisplayManagerService$$ExternalSyntheticLambda1(
            DisplayManagerService displayManagerService) {
        this.f$0 = displayManagerService;
    }

    public final DisplayDeviceConfig getDisplayDeviceConfig(int i) {
        DisplayManagerService displayManagerService = this.f$0;
        synchronized (displayManagerService.mSyncRoot) {
            try {
                DisplayDevice deviceForDisplayLocked =
                        displayManagerService.getDeviceForDisplayLocked(i);
                if (deviceForDisplayLocked == null) {
                    return null;
                }
                return deviceForDisplayLocked.getDisplayDeviceConfig();
            } finally {
            }
        }
    }
}
