package com.android.systemui.communal.data.repository;

import android.content.ComponentName;
import android.os.UserHandle;
import com.android.systemui.communal.widgets.WidgetConfigurator;
import java.util.Map;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface CommunalWidgetRepository {
    void abortRestoreWidgets();

    void addWidget(ComponentName componentName, UserHandle userHandle, Integer num, WidgetConfigurator widgetConfigurator);

    void deleteWidget(int i);

    Flow getCommunalWidgets();

    void resizeWidget(Map map, int i, int i2);

    void restoreWidgets(Map map);

    void updateWidgetOrder(Map map);
}
