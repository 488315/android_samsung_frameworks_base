package com.android.systemui.unfold;

import android.os.SystemProperties;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class UnfoldTransitionModuleKt {
    public static final boolean ENABLE_FOLD_TASK_ANIMATIONS = SystemProperties.getBoolean("persist.unfold.enable_fold_tasks_animation", false);
}
