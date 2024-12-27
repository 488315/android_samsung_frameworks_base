package com.android.systemui.mediaprojection.taskswitcher;

import com.android.systemui.CoreStartable;
import com.android.systemui.Flags;
import dagger.Lazy;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaProjectionTaskSwitcherCoreStartable implements CoreStartable {
    public MediaProjectionTaskSwitcherCoreStartable(Lazy lazy) {
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Flags.FEATURE_FLAGS.getClass();
    }
}
