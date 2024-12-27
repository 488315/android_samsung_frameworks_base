package com.android.server.display;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
