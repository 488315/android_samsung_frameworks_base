package com.android.systemui.communal.widgets;

import android.content.Context;
import com.android.systemui.plugins.ActivityStarter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class EditWidgetsActivityStarterImpl implements EditWidgetsActivityStarter {
    public final ActivityStarter activityStarter;
    public final Context applicationContext;

    public EditWidgetsActivityStarterImpl(Context context, ActivityStarter activityStarter) {
        this.applicationContext = context;
        this.activityStarter = activityStarter;
    }
}
