package com.android.systemui.globalactions.util;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.ActivityStarter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ActivityStarterWrapper {
    public final ActivityStarter mActivityStarter = (ActivityStarter) Dependency.sDependency.getDependencyInner(ActivityStarter.class);

    public ActivityStarterWrapper(Context context) {
    }
}
