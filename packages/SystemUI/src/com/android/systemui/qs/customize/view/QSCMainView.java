package com.android.systemui.qs.customize.view;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.qs.customize.viewcontroller.QSCMainViewController;

/* loaded from: classes2.dex */
public final class QSCMainView extends LinearLayout {
    public QSCMainViewController.AnonymousClass1 configChangedCallback;
    public QSCMainViewController.AnonymousClass2 windowInsetChangeListener;

    public QSCMainView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.qs_customize_main_container, this);
        bringToFront();
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        WindowInsets windowInsetsOnApplyWindowInsets = super.onApplyWindowInsets(windowInsets);
        QSCMainViewController.AnonymousClass2 anonymousClass2 = this.windowInsetChangeListener;
        if (anonymousClass2 != null) {
            anonymousClass2.run();
        }
        windowInsetsOnApplyWindowInsets.getClass();
        return windowInsetsOnApplyWindowInsets;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        QSCMainViewController.AnonymousClass1 anonymousClass1 = this.configChangedCallback;
        if (anonymousClass1 != null) {
            anonymousClass1.onConfigChanged(configuration);
        }
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        View viewRequireViewById = requireViewById(R.id.prime_container);
        if (motionEvent.getPointerCount() <= 1 || viewRequireViewById.getVisibility() == 0) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return true;
    }
}
