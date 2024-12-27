package com.android.systemui.communal.widgets;

import android.content.Context;
import com.android.systemui.plugins.ActivityStarter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class EditWidgetsActivityStarterImpl implements EditWidgetsActivityStarter {
    public final ActivityStarter activityStarter;
    public final Context applicationContext;

    public EditWidgetsActivityStarterImpl(Context context, ActivityStarter activityStarter) {
        this.applicationContext = context;
        this.activityStarter = activityStarter;
    }
}
