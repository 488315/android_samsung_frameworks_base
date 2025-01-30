package com.android.systemui.p016qs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.log.SecTouchLogHelper;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.util.DeviceState;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.io.PrintWriter;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class QSContainerImpl extends FrameLayout implements Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mClippingEnabled;
    public BooleanSupplier mExpandImmediateSupplier;
    public int mFancyClippingBottom;
    public int mFancyClippingLeftInset;
    public final Path mFancyClippingPath;
    public final float[] mFancyClippingRadii;
    public int mFancyClippingRightInset;
    public int mFancyClippingTop;
    public SecQuickStatusBarHeader mHeader;
    public boolean mHeadsUpPinned;
    public BooleanSupplier mImmersiveScrollingSupplier;
    public boolean mIsFullWidth;
    public boolean mKeyguardShowing;
    public DoubleSupplier mMaxExpansionHeightSupplier;
    public DoubleSupplier mMinExpansionHeightSupplier;
    public int mNotificationDividerHeight;
    public final SecQSContainerOpaqueBgHelper mOpaqueBgHelper;
    public SecQuickQSPanel mQQSPanel;
    public NonInterceptingScrollView mQSPanelContainer;
    public int mQSPanelContainerTopMargin;
    public boolean mQsDisabled;
    public float mQsExpansion;
    public final SecQSPanelResourcePicker mResourcePicker;
    public SecQSPanel mSecQSPanel;
    public final SecTouchLogHelper mTouchLogHelper;

    public QSContainerImpl(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFancyClippingRadii = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        this.mFancyClippingPath = new Path();
        this.mTouchLogHelper = new SecTouchLogHelper();
        this.mQSPanelContainerTopMargin = 0;
        this.mHeadsUpPinned = false;
        if (QpRune.QUICK_TABLET_BG) {
            this.mOpaqueBgHelper = new SecQSContainerOpaqueBgHelper(context, new Runnable() { // from class: com.android.systemui.qs.QSContainerImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    QSContainerImpl qSContainerImpl = QSContainerImpl.this;
                    int i = QSContainerImpl.$r8$clinit;
                    qSContainerImpl.updateClippingPath();
                }
            });
        }
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        BooleanSupplier booleanSupplier;
        if (QpRune.QUICK_TABLET_BG ? true : (this.mFancyClippingPath.isEmpty() ^ true) && !this.mKeyguardShowing && this.mQsExpansion == 0.0f && ((booleanSupplier = this.mExpandImmediateSupplier) == null || !booleanSupplier.getAsBoolean())) {
            canvas.translate(0.0f, -getTranslationY());
            canvas.clipPath(this.mFancyClippingPath);
            canvas.translate(0.0f, getTranslationY());
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(getClass().getSimpleName() + " updateClippingPath: leftInset(" + this.mFancyClippingLeftInset + ") top(" + this.mFancyClippingTop + ") rightInset(" + this.mFancyClippingRightInset + ") bottom(" + this.mFancyClippingBottom + ") mClippingEnabled(" + this.mClippingEnabled + ") mIsFullWidth(" + this.mIsFullWidth + ")");
    }

    public final int getContainerHeight() {
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        Context context = ((FrameLayout) this).mContext;
        secQSPanelResourcePicker.getClass();
        int screenHeight = (DeviceState.getScreenHeight(context) - ((ShadeHeaderController) secQSPanelResourcePicker.mShadeHeaderControllerLazy.get()).getViewHeight()) - secQSPanelResourcePicker.getNavBarHeight(context);
        if (!QpRune.QUICK_TABLET_BG) {
            return screenHeight;
        }
        int measuredHeight = this.mSecQSPanel.getMeasuredHeight() + this.mQSPanelContainerTopMargin;
        SecQSPanelResourcePicker secQSPanelResourcePicker2 = this.mResourcePicker;
        Context context2 = ((FrameLayout) this).mContext;
        secQSPanelResourcePicker2.getClass();
        return Math.min(SecQSPanelResourcePicker.getPanelSidePadding(context2) + measuredHeight, DeviceState.getScreenHeight(((FrameLayout) this).mContext) - this.mResourcePicker.getNavBarHeight(((FrameLayout) this).mContext));
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final boolean isTransformedTouchPointInView(float f, float f2, View view, PointF pointF) {
        if (!this.mClippingEnabled || getTranslationY() + f2 <= this.mFancyClippingTop) {
            return super.isTransformedTouchPointInView(f, f2, view, pointF);
        }
        return false;
    }

    @Override // android.view.ViewGroup
    public final void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        super.measureChildWithMargins(view, i, i2, i3, i4);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mQSPanelContainer = (NonInterceptingScrollView) findViewById(R.id.expanded_qs_scroll_view);
        this.mHeader = (SecQuickStatusBarHeader) findViewById(R.id.header);
        ((SecQSDetail) findViewById(R.id.qs_detail)).getClass();
        boolean z = QpRune.QUICK_TABLET_BG;
        if (z) {
            this.mSecQSPanel = (SecQSPanel) findViewById(R.id.quick_settings_panel);
            this.mQQSPanel = (SecQuickQSPanel) findViewById(R.id.quick_qs_panel);
        }
        setImportantForAccessibility(2);
        if (z) {
            SecQSContainerOpaqueBgHelper secQSContainerOpaqueBgHelper = this.mOpaqueBgHelper;
            View findViewById = findViewById(R.id.quick_settings_background);
            secQSContainerOpaqueBgHelper.mBackground = findViewById;
            findViewById.setVisibility(0);
            secQSContainerOpaqueBgHelper.updateBackgroundResources();
        }
        this.mNotificationDividerHeight = getContext().getResources().getDimensionPixelSize(R.dimen.notification_divider_height);
        getContext().getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_background_corner_radius);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        this.mTouchLogHelper.printOnInterceptTouchEventLog(motionEvent, "QSContainerImpl", "");
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateExpansion();
        updateClippingPath();
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(getContainerHeight(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        BooleanSupplier booleanSupplier;
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        Context context = getContext();
        secQSPanelResourcePicker.getClass();
        float qQSPanelSidePadding = SecQSPanelResourcePicker.getQQSPanelSidePadding(context);
        boolean z = (this.mHeadsUpPinned || this.mQsExpansion != 0.0f || !((x > qQSPanelSidePadding ? 1 : (x == qQSPanelSidePadding ? 0 : -1)) > 0 && (x > (((float) getWidth()) - qQSPanelSidePadding) ? 1 : (x == (((float) getWidth()) - qQSPanelSidePadding) ? 0 : -1)) < 0 && (y > 0.0f ? 1 : (y == 0.0f ? 0 : -1)) > 0 && (y > ((float) getBottom()) ? 1 : (y == ((float) getBottom()) ? 0 : -1)) < 0) || (booleanSupplier = this.mImmersiveScrollingSupplier) == null || booleanSupplier.getAsBoolean() || this.mKeyguardShowing) ? false : true;
        this.mTouchLogHelper.printOnTouchEventLog(motionEvent, "QSContainerImpl needToConsumeEvents : " + z, "");
        return z || super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final boolean performClick() {
        return true;
    }

    @Override // android.view.View
    public final void setTranslationY(float f) {
        super.setTranslationY(f);
        if (QpRune.QUICK_TABLET_BG) {
            SecQSContainerOpaqueBgHelper secQSContainerOpaqueBgHelper = this.mOpaqueBgHelper;
            float f2 = this.mQsExpansion;
            float[] fArr = this.mFancyClippingRadii;
            BooleanSupplier booleanSupplier = this.mExpandImmediateSupplier;
            secQSContainerOpaqueBgHelper.updateBackgroundRound(f2, fArr, booleanSupplier != null && booleanSupplier.getAsBoolean());
        }
    }

    public final void updateClippingPath() {
        this.mFancyClippingPath.reset();
        if (!this.mClippingEnabled) {
            invalidate();
            return;
        }
        this.mFancyClippingPath.addRoundRect(0, 0.0f, getWidth(), this.mFancyClippingTop - (QpRune.QUICK_TABLET_BG ? 0 : this.mNotificationDividerHeight), this.mFancyClippingRadii, Path.Direction.CW);
        invalidate();
    }

    public final void updateExpansion() {
        DoubleSupplier doubleSupplier;
        int measuredHeight = getMeasuredHeight();
        boolean z = QpRune.QUICK_TABLET_BG;
        int i = 0;
        int asDouble = z ? (this.mMinExpansionHeightSupplier == null || (doubleSupplier = this.mMaxExpansionHeightSupplier) == null) ? 0 : (int) (this.mMinExpansionHeightSupplier.getAsDouble() + ((doubleSupplier.getAsDouble() - this.mMinExpansionHeightSupplier.getAsDouble()) * this.mQsExpansion)) : Math.round(this.mQsExpansion * (measuredHeight - this.mHeader.getHeight())) + this.mHeader.getHeight();
        setBottom(getTop() + asDouble);
        if (z) {
            BooleanSupplier booleanSupplier = this.mExpandImmediateSupplier;
            boolean z2 = booleanSupplier != null && booleanSupplier.getAsBoolean();
            if (this.mSecQSPanel != null && this.mQQSPanel != null && getBottom() != 0) {
                int containerHeight = getContainerHeight();
                int height = (this.mHeader.getHeight() + this.mSecQSPanel.getHeight()) - this.mQQSPanel.getHeight();
                SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
                Context context = ((FrameLayout) this).mContext;
                secQSPanelResourcePicker.getClass();
                i = Math.min(containerHeight, SecQSPanelResourcePicker.getPanelSidePadding(context) + height);
                if (!z2 && !this.mKeyguardShowing) {
                    i = Math.min(getTop() + asDouble, i);
                }
            }
            this.mOpaqueBgHelper.updateBackgroundRound(this.mQsExpansion, this.mFancyClippingRadii, z2);
            View view = this.mOpaqueBgHelper.mBackground;
            if (view != null) {
                view.setBottom(i);
            }
        }
    }

    public final void updateTabletResources(ShadeHeaderController shadeHeaderController) {
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        Context context = getContext();
        secQSPanelResourcePicker.getClass();
        Resources resources = context.getResources();
        this.mQSPanelContainerTopMargin = (shadeHeaderController.getViewHeight() + (resources.getDimensionPixelSize(R.dimen.sec_qs_buttons_container_margin_bottom) + (resources.getDimensionPixelSize(R.dimen.sec_qs_buttons_container_margin_top) + resources.getDimensionPixelSize(R.dimen.sec_qs_buttons_container_height)))) - getResources().getDimensionPixelSize(R.dimen.shade_header_bottom_margin_tablet);
        ((FrameLayout.LayoutParams) this.mQSPanelContainer.getLayoutParams()).topMargin = this.mQSPanelContainerTopMargin;
        ((FrameLayout.LayoutParams) this.mQSPanelContainer.getLayoutParams()).bottomMargin = getResources().getDimensionPixelSize(R.dimen.qs_panel_bg_bottom_margin);
        this.mOpaqueBgHelper.updateBackgroundResources();
    }
}
