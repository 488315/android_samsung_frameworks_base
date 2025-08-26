package com.android.systemui.qs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.customize.QSCustomizer;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.LargeScreenHeaderHelper;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.TouchLogger;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.util.LargeScreenUtils;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.ViewUtil;
import java.io.PrintWriter;
import kotlin.Lazy;

/* loaded from: classes2.dex */
public class QSContainerImpl extends FrameLayout implements Dumpable {
    public boolean mClippingEnabled;
    public int mContentHorizontalPadding;
    public int mFancyClippingBottom;
    public int mFancyClippingLeftInset;
    public final Path mFancyClippingPath;
    public final float[] mFancyClippingRadii;
    public int mFancyClippingRightInset;
    public int mFancyClippingTop;
    public SecQuickStatusBarHeader mHeader;
    public int mHorizontalMargins;
    public boolean mIsFullWidth;
    public QSCustomizer mQSCustomizer;
    public SecQSPanel mQSPanel;
    public NonInterceptingScrollView mQSPanelContainer;
    public boolean mQsDisabled;
    public float mQsExpansion;
    public final QuickPanelLogger mQuickPanelLogger;
    public final SecQSContainerImpl mSecQSContainerImpl;
    public ShadeHeaderController mShadeHeaderController;
    public int mTilesPageMargin;

    public QSContainerImpl(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFancyClippingRadii = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        Path path = new Path();
        this.mFancyClippingPath = path;
        this.mContentHorizontalPadding = -1;
        this.mQuickPanelLogger = new QuickPanelLogger("QSContainerI");
        this.mSecQSContainerImpl = new SecQSContainerImpl(path);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x004a  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dispatchDraw(Canvas canvas) {
        Canvas canvas2;
        SecQSContainerImpl secQSContainerImpl = this.mSecQSContainerImpl;
        if (secQSContainerImpl != null) {
            boolean z = this.mQsExpansion == 0.0f;
            float translationY = getTranslationY();
            boolean zIsEmpty = secQSContainerImpl.fancyClippingPath.isEmpty();
            if (zIsEmpty || secQSContainerImpl.keyguardShowing || !z || ((Boolean) ((ShadeRepositoryImpl) ((ShadeRepository) secQSContainerImpl.shadeRepository$delegate.getValue())).legacyExpandImmediate.$$delegate_0.getValue()).booleanValue()) {
                canvas2 = null;
                if (canvas2 != null) {
                    canvas2.translate(0.0f, -translationY);
                    canvas2.clipPath(secQSContainerImpl.fancyClippingPath);
                    canvas2.translate(0.0f, translationY);
                }
            } else {
                SecPanelSplitHelper.Companion.getClass();
                if (!SecPanelSplitHelper.isEnabled && !zIsEmpty) {
                    canvas2 = canvas;
                }
                if (canvas2 != null) {
                }
            }
        } else if (!this.mFancyClippingPath.isEmpty()) {
            canvas.translate(0.0f, -getTranslationY());
            canvas.clipOutPath(this.mFancyClippingPath);
            canvas.translate(0.0f, getTranslationY());
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean zDispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        TouchLogger.Companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, QS.TAG, zDispatchTouchEvent);
        return zDispatchTouchEvent;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(getClass().getSimpleName() + " updateClippingPath: leftInset(" + this.mFancyClippingLeftInset + ") top(" + this.mFancyClippingTop + ") rightInset(" + this.mFancyClippingRightInset + ") bottom(" + this.mFancyClippingBottom + ") mClippingEnabled(" + this.mClippingEnabled + ") mIsFullWidth(" + this.mIsFullWidth + ")");
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
        this.mQSPanel = (SecQSPanel) findViewById(R.id.quick_settings_panel);
        this.mHeader = ViewUtil.getSecQuickStatusBarHeader(this);
        this.mQSCustomizer = (QSCustomizer) findViewById(R.id.qs_customize);
        setImportantForAccessibility(2);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger != null) {
            quickPanelLogger.onInterceptTouchEvent(motionEvent);
        }
        SecQSContainerImpl secQSContainerImpl = this.mSecQSContainerImpl;
        if (secQSContainerImpl != null) {
            SecPanelSplitHelper.Companion.getClass();
            if (SecPanelSplitHelper.isEnabled) {
                Lazy lazy = secQSContainerImpl.panelSplitHepler$delegate;
                if (((SecPanelSplitHelper) lazy.getValue()).isQSState() && !((SecQsUiDisplayModeInteractor) secQSContainerImpl.uiDisplayModeInteractor$delegate.getValue()).isTablet()) {
                    if (motionEvent.getAction() == 0) {
                        secQSContainerImpl.panelSplitIntercepted = false;
                    }
                    if (((SecPanelSplitHelper) lazy.getValue()).onIntercept(motionEvent)) {
                        secQSContainerImpl.panelSplitIntercepted = true;
                        QuickPanelLogger quickPanelLogger2 = this.mQuickPanelLogger;
                        if (quickPanelLogger2 != null) {
                            quickPanelLogger2.onInterceptTouchEvent(motionEvent, "panelSplitHelper.onIntercept()", true);
                        }
                        return true;
                    }
                }
            }
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateClippingPath();
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        QuickPanelLogger quickPanelLogger = this.mQuickPanelLogger;
        if (quickPanelLogger != null) {
            quickPanelLogger.onTouchEvent(motionEvent);
        }
        SecQSContainerImpl secQSContainerImpl = this.mSecQSContainerImpl;
        if (secQSContainerImpl != null && !((SecQsUiDisplayModeInteractor) secQSContainerImpl.uiDisplayModeInteractor$delegate.getValue()).isTablet()) {
            SecPanelSplitHelper.Companion.getClass();
            if (SecPanelSplitHelper.isEnabled) {
                Lazy lazy = secQSContainerImpl.panelSplitHepler$delegate;
                if (!((SecPanelSplitHelper) lazy.getValue()).isShadeState() && secQSContainerImpl.panelSplitIntercepted) {
                    ((SecPanelSplitHelper) lazy.getValue()).handleTouch(motionEvent);
                    QuickPanelLogger quickPanelLogger2 = this.mQuickPanelLogger;
                    if (quickPanelLogger2 != null) {
                        quickPanelLogger2.onTouchEvent(motionEvent, "isPanelSplitEnabled && panelSplitIntercepted", true);
                    }
                    return true;
                }
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final boolean performClick() {
        return true;
    }

    public final void updateClippingPath() {
        this.mFancyClippingPath.reset();
        if (!this.mClippingEnabled) {
            invalidate();
            return;
        }
        if (this.mSecQSContainerImpl != null) {
            this.mSecQSContainerImpl.fancyClippingPath.addRoundRect(0.0f, -(this.mShadeHeaderController != null ? r0.header.getMeasuredHeight() : 0.0f), getWidth(), this.mFancyClippingTop, this.mFancyClippingRadii, Path.Direction.CW);
        } else {
            boolean z = this.mIsFullWidth;
            this.mFancyClippingPath.addRoundRect(z ? -this.mFancyClippingLeftInset : 0, this.mFancyClippingTop, z ? getWidth() + this.mFancyClippingRightInset : getWidth(), this.mFancyClippingBottom, this.mFancyClippingRadii, Path.Direction.CW);
        }
        invalidate();
    }

    public final void updateResources(SecQSPanelController secQSPanelController, SecQuickStatusBarHeaderController secQuickStatusBarHeaderController) {
        SecQSContainerImpl secQSContainerImpl = this.mSecQSContainerImpl;
        if (secQSContainerImpl != null) {
            NonInterceptingScrollView nonInterceptingScrollView = this.mQSPanelContainer;
            Context context = ((FrameLayout) this).mContext;
            ViewGroup.LayoutParams layoutParams = nonInterceptingScrollView != null ? nonInterceptingScrollView.getLayoutParams() : null;
            FrameLayout.LayoutParams layoutParams2 = layoutParams instanceof FrameLayout.LayoutParams ? (FrameLayout.LayoutParams) layoutParams : null;
            if (layoutParams2 != null) {
                layoutParams2.topMargin = ((SecQSPanelResourcePicker) secQSContainerImpl.resourcePicker$delegate.getValue()).getQsScrollerTopMargin(context);
                return;
            }
            return;
        }
        Context context2 = ((FrameLayout) this).mContext;
        int i = QSUtils.$r8$clinit;
        int quickQsOffsetHeight = LargeScreenUtils.shouldUseLargeScreenShadeHeader(context2.getResources()) ? 0 : SystemBarUtils.getQuickQsOffsetHeight(context2);
        if (!LargeScreenUtils.shouldUseLargeScreenShadeHeader(((FrameLayout) this).mContext.getResources())) {
            quickQsOffsetHeight = LargeScreenHeaderHelper.getLargeScreenHeaderHeight(((FrameLayout) this).mContext);
        }
        NonInterceptingScrollView nonInterceptingScrollView2 = this.mQSPanelContainer;
        if (nonInterceptingScrollView2 != null) {
            nonInterceptingScrollView2.setPaddingRelative(nonInterceptingScrollView2.getPaddingStart(), quickQsOffsetHeight, this.mQSPanelContainer.getPaddingEnd(), this.mQSPanelContainer.getPaddingBottom());
        } else {
            SecQSPanel secQSPanel = this.mQSPanel;
            secQSPanel.setPaddingRelative(secQSPanel.getPaddingStart(), quickQsOffsetHeight, this.mQSPanel.getPaddingEnd(), this.mQSPanel.getPaddingBottom());
        }
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.qs_horizontal_margin);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.qs_content_horizontal_padding);
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.qs_tiles_page_horizontal_margin);
        boolean z = (dimensionPixelSize2 == this.mContentHorizontalPadding && dimensionPixelSize == this.mHorizontalMargins && dimensionPixelSize3 == this.mTilesPageMargin) ? false : true;
        this.mContentHorizontalPadding = dimensionPixelSize2;
        this.mHorizontalMargins = dimensionPixelSize;
        this.mTilesPageMargin = dimensionPixelSize3;
        if (z) {
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                View childAt = getChildAt(i2);
                if (childAt != this.mQSCustomizer) {
                    FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) childAt.getLayoutParams();
                    int i3 = this.mHorizontalMargins;
                    layoutParams3.rightMargin = i3;
                    layoutParams3.leftMargin = i3;
                    if (childAt == this.mQSPanelContainer || childAt == this.mQSPanel) {
                        secQSPanelController.getClass();
                        int i4 = SceneContainerFlag.$r8$clinit;
                    } else if (childAt == this.mHeader) {
                        secQuickStatusBarHeaderController.mQuickQSPanelController.getClass();
                    } else {
                        childAt.setPaddingRelative(this.mContentHorizontalPadding, childAt.getPaddingTop(), this.mContentHorizontalPadding, childAt.getPaddingBottom());
                    }
                }
            }
        }
    }
}
