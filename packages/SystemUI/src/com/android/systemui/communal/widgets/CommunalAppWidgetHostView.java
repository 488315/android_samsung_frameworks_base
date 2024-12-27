package com.android.systemui.communal.widgets;

import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.graphics.Outline;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.core.os.BuildCompat;
import com.android.systemui.R;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public final class CommunalAppWidgetHostView extends AppWidgetHostView implements LaunchableView {
    public final CommunalAppWidgetHostView$cornerRadiusEnforcementOutline$1 cornerRadiusEnforcementOutline;
    public final float enforcedCornerRadius;
    public final Rect enforcedRectangle;
    public final LaunchableViewDelegate launchableViewDelegate;

    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.systemui.communal.widgets.CommunalAppWidgetHostView$cornerRadiusEnforcementOutline$1] */
    public CommunalAppWidgetHostView(Context context) {
        super(context);
        this.launchableViewDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.communal.widgets.CommunalAppWidgetHostView$launchableViewDelegate$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                super/*android.appwidget.AppWidgetHostView*/.setVisibility(((Number) obj).intValue());
                return Unit.INSTANCE;
            }
        });
        RoundedCornerEnforcement.INSTANCE.getClass();
        int i = BuildCompat.$r8$clinit;
        this.enforcedCornerRadius = Math.min(context.getResources().getDimension(R.dimen.communal_enforced_rounded_corner_max_radius), context.getResources().getDimension(android.R.dimen.system_app_widget_background_radius));
        this.enforcedRectangle = new Rect();
        this.cornerRadiusEnforcementOutline = new ViewOutlineProvider() { // from class: com.android.systemui.communal.widgets.CommunalAppWidgetHostView$cornerRadiusEnforcementOutline$1
            @Override // android.view.ViewOutlineProvider
            public final void getOutline(View view, Outline outline) {
                if (!CommunalAppWidgetHostView.this.enforcedRectangle.isEmpty()) {
                    CommunalAppWidgetHostView communalAppWidgetHostView = CommunalAppWidgetHostView.this;
                    float f = communalAppWidgetHostView.enforcedCornerRadius;
                    if (f > 0.0f) {
                        outline.setRoundRect(communalAppWidgetHostView.enforcedRectangle, f);
                        return;
                    }
                }
                outline.setEmpty();
            }
        };
    }

    @Override // android.appwidget.AppWidgetHostView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.enforcedCornerRadius <= 0.0f) {
            setOutlineProvider(ViewOutlineProvider.BACKGROUND);
            setClipToOutline(false);
            return;
        }
        RoundedCornerEnforcement.INSTANCE.getClass();
        ArrayList arrayList = new ArrayList();
        RoundedCornerEnforcement.accumulateViewsWithId(this, arrayList);
        View findUndefinedBackground = arrayList.size() == 1 ? (View) arrayList.get(0) : getChildCount() > 0 ? RoundedCornerEnforcement.findUndefinedBackground(getChildAt(0)) : this;
        if (findUndefinedBackground == null || (findUndefinedBackground.getId() == R.id.background && findUndefinedBackground.getClipToOutline())) {
            setOutlineProvider(ViewOutlineProvider.BACKGROUND);
            setClipToOutline(false);
            return;
        }
        Rect rect = this.enforcedRectangle;
        rect.left = 0;
        rect.right = findUndefinedBackground.getWidth();
        rect.top = 0;
        rect.bottom = findUndefinedBackground.getHeight();
        while (findUndefinedBackground != this) {
            rect.offset(findUndefinedBackground.getLeft(), findUndefinedBackground.getTop());
            findUndefinedBackground = (View) findUndefinedBackground.getParent();
        }
        setOutlineProvider(this.cornerRadiusEnforcementOutline);
        setClipToOutline(true);
        invalidateOutline();
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
