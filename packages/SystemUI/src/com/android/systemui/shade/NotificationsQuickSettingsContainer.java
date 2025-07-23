package com.android.systemui.shade;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.R;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.shade.TouchLogger;
import com.android.systemui.statusbar.notification.AboveShelfObserver;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationsQuickSettingsContainer extends ConstraintLayout implements FragmentHostManager.FragmentListener, AboveShelfObserver.HasViewAboveShelfChangedListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Rect mBoundingBoxRect;
    public NotificationsQSContainerController$onViewAttached$2 mConfigurationChangedListener;
    public Consumer mInsetsChangedListener;
    public View mQSContainer;
    public Consumer mQSFragmentAttachedListener;
    public QS mQs;
    public View mQsFrame;
    public View mStackScroller;
    public final Rect mUpperRect;

    public NotificationsQuickSettingsContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInsetsChangedListener = new NotificationsQuickSettingsContainer$$ExternalSyntheticLambda0(0);
        this.mQSFragmentAttachedListener = new NotificationsQuickSettingsContainer$$ExternalSyntheticLambda0(1);
        this.mUpperRect = new Rect();
        this.mBoundingBoxRect = new Rect();
        setOptimizationLevel(getOptimizationLevel() | 64);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        TouchLogger.Companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, "NotificationsQuickSettingsContainer", dispatchTouchEvent);
        return dispatchTouchEvent;
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        this.mInsetsChangedListener.accept(windowInsets);
        return windowInsets;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        NotificationsQSContainerController$onViewAttached$2 notificationsQSContainerController$onViewAttached$2 = this.mConfigurationChangedListener;
        if (notificationsQSContainerController$onViewAttached$2 != null) {
            notificationsQSContainerController$onViewAttached$2.accept(configuration);
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mQsFrame = findViewById(R.id.qs_frame);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
    public final void onFragmentViewCreated(Fragment fragment) {
        QS qs = (QS) fragment;
        this.mQs = qs;
        this.mQSFragmentAttachedListener.accept(qs);
        View findViewById = this.mQs.getView().findViewById(R.id.quick_settings_container);
        this.mQSContainer = findViewById;
        if (findViewById != null) {
            findViewById.setPadding(findViewById.getPaddingLeft(), this.mQSContainer.getPaddingTop(), this.mQSContainer.getPaddingRight(), 0);
        }
    }
}
