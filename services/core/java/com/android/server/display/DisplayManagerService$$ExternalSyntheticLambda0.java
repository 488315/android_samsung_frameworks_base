package com.android.server.display;

import android.view.Display;

import java.util.function.Consumer;

public final /* synthetic */ class DisplayManagerService$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Display.Mode f$0;

    public /* synthetic */ DisplayManagerService$$ExternalSyntheticLambda0(
            int i, Display.Mode mode) {
        this.$r8$classId = i;
        this.f$0 = mode;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Display.Mode mode = this.f$0;
        DisplayDevice displayDevice = (DisplayDevice) obj;
        switch (i) {
            case 0:
                displayDevice.setUserPreferredDisplayModeLocked(mode);
                break;
            default:
                displayDevice.setUserPreferredDisplayModeLocked(mode);
                break;
        }
    }
}
