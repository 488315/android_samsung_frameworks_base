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
import com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$onViewAttached$1;
import com.android.systemui.qs.customize.viewcontroller.QSCMainViewController$onViewAttached$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSCMainView extends LinearLayout {
    public QSCMainViewController$onViewAttached$1 configChangedCallback;
    public QSCMainViewController$onViewAttached$2 windowInsetChangeListener;

    public QSCMainView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.qs_customize_main_container, this);
        bringToFront();
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        WindowInsets onApplyWindowInsets = super.onApplyWindowInsets(windowInsets);
        QSCMainViewController$onViewAttached$2 qSCMainViewController$onViewAttached$2 = this.windowInsetChangeListener;
        if (qSCMainViewController$onViewAttached$2 != null) {
            qSCMainViewController$onViewAttached$2.run();
        }
        onApplyWindowInsets.getClass();
        return onApplyWindowInsets;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        QSCMainViewController$onViewAttached$1 qSCMainViewController$onViewAttached$1 = this.configChangedCallback;
        if (qSCMainViewController$onViewAttached$1 != null) {
            qSCMainViewController$onViewAttached$1.onConfigChanged(configuration);
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
