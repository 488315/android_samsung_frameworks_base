package com.android.systemui.communal.widgets;

import android.content.Context;
import com.android.systemui.plugins.ActivityStarter;

public final class EditWidgetsActivityStarterImpl implements EditWidgetsActivityStarter {
    public final ActivityStarter activityStarter;
    public final Context applicationContext;

    public EditWidgetsActivityStarterImpl(Context context, ActivityStarter activityStarter) {
        this.applicationContext = context;
        this.activityStarter = activityStarter;
    }
}
