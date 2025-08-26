package com.android.systemui.globalactions.util;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.ActivityStarter;

/* loaded from: classes2.dex */
public class ActivityStarterWrapper {
    public final ActivityStarter mActivityStarter = (ActivityStarter) Dependency.sDependency.getDependencyInner(ActivityStarter.class);

    public ActivityStarterWrapper(Context context) {
    }
}
