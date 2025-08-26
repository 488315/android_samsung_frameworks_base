package com.android.systemui.shade;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.R;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.shade.NotificationsQSContainerController;
import com.android.systemui.shade.TouchLogger;
import com.android.systemui.statusbar.notification.AboveShelfObserver;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class NotificationsQuickSettingsContainer extends ConstraintLayout implements FragmentHostManager.FragmentListener, AboveShelfObserver.HasViewAboveShelfChangedListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Rect mBoundingBoxRect;
    public NotificationsQSContainerController.C10362 mConfigurationChangedListener;
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
        boolean zDispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        TouchLogger.Companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, "NotificationsQuickSettingsContainer", zDispatchTouchEvent);
        return zDispatchTouchEvent;
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        this.mInsetsChangedListener.accept(windowInsets);
        return windowInsets;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        NotificationsQSContainerController.C10362 c10362 = this.mConfigurationChangedListener;
        if (c10362 != null) {
            c10362.accept(configuration);
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
        View viewFindViewById = this.mQs.getView().findViewById(R.id.quick_settings_container);
        this.mQSContainer = viewFindViewById;
        if (viewFindViewById != null) {
            viewFindViewById.setPadding(viewFindViewById.getPaddingLeft(), this.mQSContainer.getPaddingTop(), this.mQSContainer.getPaddingRight(), 0);
        }
    }
}
