package com.android.systemui.mediaprojection.taskswitcher;

import com.android.systemui.CoreStartable;
import com.android.systemui.Flags;
import dagger.Lazy;

public final class MediaProjectionTaskSwitcherCoreStartable implements CoreStartable {
    public MediaProjectionTaskSwitcherCoreStartable(Lazy lazy) {
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Flags.FEATURE_FLAGS.getClass();
    }
}
