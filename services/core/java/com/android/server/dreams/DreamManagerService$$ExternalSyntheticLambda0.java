package com.android.server.dreams;

import android.service.dreams.DreamManagerInternal;

import java.util.function.Consumer;

public final /* synthetic */ class DreamManagerService$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DreamManagerInternal.DreamManagerStateListener dreamManagerStateListener =
                (DreamManagerInternal.DreamManagerStateListener) obj;
        switch (this.$r8$classId) {
            case 0:
                dreamManagerStateListener.onDreamingStarted();
                break;
            default:
                dreamManagerStateListener.onDreamingStopped();
                break;
        }
    }
}
