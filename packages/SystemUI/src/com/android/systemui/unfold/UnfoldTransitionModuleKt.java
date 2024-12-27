package com.android.systemui.unfold;

import android.os.SystemProperties;

public abstract class UnfoldTransitionModuleKt {
    public static final boolean ENABLE_FOLD_TASK_ANIMATIONS = SystemProperties.getBoolean("persist.unfold.enable_fold_tasks_animation", false);
}
