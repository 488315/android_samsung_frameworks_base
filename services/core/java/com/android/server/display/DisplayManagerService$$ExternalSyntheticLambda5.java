package com.android.server.display;

import java.util.function.Consumer;

public final /* synthetic */ class DisplayManagerService$$ExternalSyntheticLambda5
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((Runnable) obj).run();
                break;
            default:
                ((DisplayDevice) obj).onOverlayChangedLocked();
                break;
        }
    }
}
