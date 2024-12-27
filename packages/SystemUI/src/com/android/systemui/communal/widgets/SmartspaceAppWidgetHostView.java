package com.android.systemui.communal.widgets;

import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SmartspaceAppWidgetHostView extends AppWidgetHostView implements LaunchableView {
    public final LaunchableViewDelegate launchableViewDelegate;

    public SmartspaceAppWidgetHostView(Context context) {
        super(context);
        this.launchableViewDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.communal.widgets.SmartspaceAppWidgetHostView$launchableViewDelegate$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                super/*android.appwidget.AppWidgetHostView*/.setVisibility(((Number) obj).intValue());
                return Unit.INSTANCE;
            }
        });
    }

    public final Context getRemoteContextEnsuringCorrectCachedApkPath() {
        return null;
    }

    @Override // android.appwidget.AppWidgetHostView
    public final void setAppWidget(int i, AppWidgetProviderInfo appWidgetProviderInfo) {
        super.setAppWidget(i, appWidgetProviderInfo);
        setPadding(0, 0, 0, 0);
    }

    @Override // com.android.systemui.animation.LaunchableView
    public final void setShouldBlockVisibilityChanges(boolean z) {
        this.launchableViewDelegate.setShouldBlockVisibilityChanges(z);
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        this.launchableViewDelegate.setVisibility(i);
    }
}
