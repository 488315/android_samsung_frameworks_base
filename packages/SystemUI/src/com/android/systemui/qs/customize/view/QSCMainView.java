package com.android.systemui.qs.customize.view;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.ConfigurationController;

public final class QSCMainView extends LinearLayout {
    public ConfigurationController.ConfigurationListener configChangedCallback;

    public QSCMainView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.qs_customize_main_container, this);
        bringToFront();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        ConfigurationController.ConfigurationListener configurationListener = this.configChangedCallback;
        if (configurationListener != null) {
            configurationListener.onConfigChanged(configuration);
        }
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        View requireViewById = requireViewById(R.id.prime_container);
        if (motionEvent.getPointerCount() <= 1 || requireViewById.getVisibility() == 0) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return true;
    }
}
