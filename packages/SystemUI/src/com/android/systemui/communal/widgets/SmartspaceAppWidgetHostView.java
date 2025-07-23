package com.android.systemui.communal.widgets;

import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SmartspaceAppWidgetHostView extends AppWidgetHostView implements LaunchableView {
    public final LaunchableViewDelegate launchableViewDelegate;

    /* renamed from: $r8$lambda$F3-Mh89VSXE3NzNehwPBvfy3TWk, reason: not valid java name */
    public static Unit m1092$r8$lambda$F3Mh89VSXE3NzNehwPBvfy3TWk(SmartspaceAppWidgetHostView smartspaceAppWidgetHostView, int i) {
        super.setVisibility(i);
        return Unit.INSTANCE;
    }

    public SmartspaceAppWidgetHostView(Context context) {
        super(context);
        this.launchableViewDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.communal.widgets.SmartspaceAppWidgetHostView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return SmartspaceAppWidgetHostView.m1092$r8$lambda$F3Mh89VSXE3NzNehwPBvfy3TWk(SmartspaceAppWidgetHostView.this, ((Integer) obj).intValue());
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
