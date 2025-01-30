package com.android.systemui.shade;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.log.SecTouchLogHelper;
import com.android.systemui.plugins.qs.InterfaceC1922QS;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.AboveShelfObserver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NotificationsQuickSettingsContainer extends ConstraintLayout implements FragmentHostManager.FragmentListener, AboveShelfObserver.HasViewAboveShelfChangedListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Rect mBoundingBoxRect;
    public final float[] mClippingRadii;
    public Consumer mConfigurationChangedListener;
    public final ArrayList mDrawingOrderedChildren;
    public final Comparator mIndexComparator;
    public Consumer mInsetsChangedListener;
    public View mKeyguardStatusBar;
    public int mLastQSPaddingBottom;
    public final ArrayList mLayoutDrawingOrder;
    public final Path mPath;
    public View mQSContainer;
    public Consumer mQSFragmentAttachedListener;
    public View mQSFrame;
    public InterfaceC1922QS mQs;
    public View mQsFrame;
    public final RectF mRect;
    public View mStackScroller;
    public StatusBarStateController mStatusBarStateController;
    public View mTabletQSBackground;
    public final SecTouchLogHelper mTouchLogHelper;
    public final Rect mUpperRect;

    public NotificationsQuickSettingsContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDrawingOrderedChildren = new ArrayList();
        this.mLayoutDrawingOrder = new ArrayList();
        this.mIndexComparator = Comparator.comparingInt(new ToIntFunction() { // from class: com.android.systemui.shade.NotificationsQuickSettingsContainer$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return NotificationsQuickSettingsContainer.this.indexOfChild((View) obj);
            }
        });
        this.mInsetsChangedListener = new NotificationsQuickSettingsContainer$$ExternalSyntheticLambda1(0);
        this.mQSFragmentAttachedListener = new NotificationsQuickSettingsContainer$$ExternalSyntheticLambda1(1);
        this.mUpperRect = new Rect();
        this.mBoundingBoxRect = new Rect();
        this.mTouchLogHelper = new SecTouchLogHelper();
        this.mPath = new Path();
        this.mRect = new RectF();
        this.mClippingRadii = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        StatusBarStateController statusBarStateController;
        this.mDrawingOrderedChildren.clear();
        this.mLayoutDrawingOrder.clear();
        if (this.mKeyguardStatusBar.getVisibility() == 0) {
            this.mDrawingOrderedChildren.add(this.mKeyguardStatusBar);
            this.mLayoutDrawingOrder.add(this.mKeyguardStatusBar);
        }
        if (this.mQsFrame.getVisibility() == 0) {
            this.mDrawingOrderedChildren.add(this.mQsFrame);
            this.mLayoutDrawingOrder.add(this.mQsFrame);
        }
        if (this.mStackScroller.getVisibility() == 0) {
            this.mDrawingOrderedChildren.add(this.mStackScroller);
            this.mLayoutDrawingOrder.add(this.mStackScroller);
        }
        this.mLayoutDrawingOrder.sort(this.mIndexComparator);
        if (QpRune.QUICK_TABLET_BG && (statusBarStateController = this.mStatusBarStateController) != null && statusBarStateController.getState() != 1) {
            this.mPath.reset();
            this.mRect.set(this.mQSFrame.getX(), this.mTabletQSBackground.getY(), this.mQSFrame.getX() + this.mTabletQSBackground.getMeasuredWidth(), getMeasuredHeight());
            this.mPath.addRoundRect(this.mRect, this.mClippingRadii, Path.Direction.CCW);
            this.mPath.close();
            canvas.clipPath(this.mPath);
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j) {
        int indexOf = this.mLayoutDrawingOrder.indexOf(view);
        return indexOf >= 0 ? super.drawChild(canvas, (View) this.mDrawingOrderedChildren.get(indexOf), j) : super.drawChild(canvas, view, j);
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        this.mInsetsChangedListener.accept(windowInsets);
        return windowInsets;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Consumer consumer = this.mConfigurationChangedListener;
        if (consumer != null) {
            consumer.accept(configuration);
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mQsFrame = findViewById(R.id.qs_frame);
        this.mStackScroller = findViewById(R.id.notification_stack_scroller);
        this.mKeyguardStatusBar = findViewById(R.id.keyguard_header);
        if (QpRune.QUICK_TABLET_BG) {
            this.mQSFrame = findViewById(R.id.qs_frame);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.fragments.FragmentHostManager.FragmentListener
    public final void onFragmentViewCreated(Fragment fragment) {
        InterfaceC1922QS interfaceC1922QS = (InterfaceC1922QS) fragment;
        this.mQs = interfaceC1922QS;
        this.mQSFragmentAttachedListener.accept(interfaceC1922QS);
        View findViewById = this.mQs.getView().findViewById(R.id.quick_settings_container);
        this.mQSContainer = findViewById;
        int i = this.mLastQSPaddingBottom;
        this.mLastQSPaddingBottom = i;
        if (findViewById != null) {
            findViewById.setPadding(findViewById.getPaddingLeft(), this.mQSContainer.getPaddingTop(), this.mQSContainer.getPaddingRight(), i);
        }
        if (QpRune.QUICK_TABLET_BG) {
            this.mTabletQSBackground = findViewById(R.id.quick_settings_background);
            Arrays.fill(this.mClippingRadii, getContext().getResources().getDimensionPixelSize(R.dimen.keyguard_indication_dls_default_notification_start));
        }
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        this.mTouchLogHelper.printOnInterceptTouchEventLog(motionEvent, "NotificationsQuickSettingsContainer", "");
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        this.mTouchLogHelper.printOnTouchEventLog(motionEvent, "NotificationsQuickSettingsContainer", "");
        return super.onTouchEvent(motionEvent);
    }
}
