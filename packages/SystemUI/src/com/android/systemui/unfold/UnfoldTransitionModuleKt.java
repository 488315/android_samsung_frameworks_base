package com.android.systemui.unfold;

import android.os.SystemProperties;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class UnfoldTransitionModuleKt {
    public static final boolean ENABLE_FOLD_TASK_ANIMATIONS = SystemProperties.getBoolean("persist.unfold.enable_fold_tasks_animation", false);
}
