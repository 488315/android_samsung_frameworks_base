package com.android.server.dreams;

import android.service.dreams.DreamManagerInternal;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
