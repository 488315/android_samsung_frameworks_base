package com.android.systemui.globalactions.util;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.ActivityStarter;

public final class ActivityStarterWrapper {
    public final ActivityStarter mActivityStarter = (ActivityStarter) Dependency.sDependency.getDependencyInner(ActivityStarter.class);

    public ActivityStarterWrapper(Context context) {
    }
}
