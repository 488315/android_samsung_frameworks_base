package com.android.systemui.communal.widgets;

import android.content.Context;
import com.android.systemui.plugins.ActivityStarter;

/* loaded from: classes2.dex */
public final class EditWidgetsActivityStarterImpl implements EditWidgetsActivityStarter {
    public final ActivityStarter activityStarter;
    public final Context applicationContext;

    public EditWidgetsActivityStarterImpl(Context context, ActivityStarter activityStarter) {
        this.applicationContext = context;
        this.activityStarter = activityStarter;
    }
}
