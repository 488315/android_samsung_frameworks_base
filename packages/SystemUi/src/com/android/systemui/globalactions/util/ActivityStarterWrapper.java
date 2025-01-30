package com.android.systemui.globalactions.util;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.ActivityStarter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ActivityStarterWrapper {
    public final ActivityStarter mActivityStarter = (ActivityStarter) Dependency.get(ActivityStarter.class);

    public ActivityStarterWrapper(Context context) {
    }
}
