package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.shade.LargeScreenHeaderHelper;
import com.android.systemui.util.LargeScreenUtils;

public class QuickStatusBarHeader extends FrameLayout {
    public QuickQSPanel mHeaderQsPanel;

    public QuickStatusBarHeader(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateResources();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mHeaderQsPanel = (QuickQSPanel) findViewById(R.id.quick_qs_panel);
        updateResources();
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getY() > this.mHeaderQsPanel.getTop()) {
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }

    public final void updateResources() {
        boolean shouldUseLargeScreenShadeHeader = LargeScreenUtils.shouldUseLargeScreenShadeHeader(((FrameLayout) this).mContext.getResources());
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = -2;
        setLayoutParams(layoutParams);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mHeaderQsPanel.getLayoutParams();
        if (shouldUseLargeScreenShadeHeader) {
            marginLayoutParams.topMargin = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.qqs_layout_margin_top);
        } else {
            Flags.FEATURE_FLAGS.getClass();
            Context context = ((FrameLayout) this).mContext;
            LargeScreenHeaderHelper.Companion.getClass();
            marginLayoutParams.topMargin = LargeScreenHeaderHelper.Companion.getLargeScreenHeaderHeight(context);
        }
        this.mHeaderQsPanel.setLayoutParams(marginLayoutParams);
    }
}
