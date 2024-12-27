package com.android.systemui.qs;

import android.content.Context;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.log.QuickPanelLogger;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.customize.QSCustomizer;
import com.android.systemui.shade.LargeScreenHeaderHelper;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.TouchLogger;
import com.android.systemui.util.LargeScreenUtils;
import com.android.systemui.util.ViewUtil;
import java.io.PrintWriter;
import kotlin.Lazy;

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
    public boolean mSceneContainerEnabled;
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

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0048, code lost:
    
        if (com.android.systemui.shade.SecPanelSplitHelper.isEnabled == false) goto L19;
     */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void dispatchDraw(android.graphics.Canvas r7) {
        /*
            r6 = this;
            com.android.systemui.qs.SecQSContainerImpl r0 = r6.mSecQSContainerImpl
            r1 = 0
            if (r0 == 0) goto L5e
            float r2 = r6.mQsExpansion
            int r2 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
            r3 = 1
            if (r2 != 0) goto Le
            r2 = r3
            goto Lf
        Le:
            r2 = 0
        Lf:
            float r4 = r6.getTranslationY()
            android.graphics.Path r5 = r0.fancyClippingPath
            boolean r5 = r5.isEmpty()
            r3 = r3 ^ r5
            boolean r5 = com.android.systemui.QpRune.QUICK_TABLET_BG
            if (r5 == 0) goto L1f
            goto L4a
        L1f:
            if (r3 == 0) goto L4e
            boolean r5 = r0.keyguardShowing
            if (r5 != 0) goto L4e
            if (r2 == 0) goto L4e
            kotlin.Lazy r2 = r0.shadeRepository$delegate
            java.lang.Object r2 = r2.getValue()
            com.android.systemui.shade.data.repository.ShadeRepository r2 = (com.android.systemui.shade.data.repository.ShadeRepository) r2
            com.android.systemui.shade.data.repository.ShadeRepositoryImpl r2 = (com.android.systemui.shade.data.repository.ShadeRepositoryImpl) r2
            kotlinx.coroutines.flow.ReadonlyStateFlow r2 = r2.legacyExpandImmediate
            kotlinx.coroutines.flow.StateFlow r2 = r2.$$delegate_0
            java.lang.Object r2 = r2.getValue()
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 != 0) goto L4e
            com.android.systemui.shade.SecPanelSplitHelper$Companion r2 = com.android.systemui.shade.SecPanelSplitHelper.Companion
            r2.getClass()
            boolean r2 = com.android.systemui.shade.SecPanelSplitHelper.isEnabled
            if (r2 != 0) goto L4e
        L4a:
            if (r3 == 0) goto L4e
            r2 = r7
            goto L4f
        L4e:
            r2 = 0
        L4f:
            if (r2 == 0) goto L7a
            float r3 = -r4
            r2.translate(r1, r3)
            android.graphics.Path r0 = r0.fancyClippingPath
            r2.clipPath(r0)
            r2.translate(r1, r4)
            goto L7a
        L5e:
            android.graphics.Path r0 = r6.mFancyClippingPath
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L7a
            float r0 = r6.getTranslationY()
            float r0 = -r0
            r7.translate(r1, r0)
            android.graphics.Path r0 = r6.mFancyClippingPath
            r7.clipOutPath(r0)
            float r0 = r6.getTranslationY()
            r7.translate(r1, r0)
        L7a:
            super.dispatchDraw(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.QSContainerImpl.dispatchDraw(android.graphics.Canvas):void");
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        TouchLogger.Companion.getClass();
        TouchLogger.Companion.logDispatchTouch(motionEvent, QS.TAG, dispatchTouchEvent);
        return dispatchTouchEvent;
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
                if (((SecPanelSplitHelper) lazy.getValue()).isQSState()) {
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
        updateExpansion();
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
        if (secQSContainerImpl != null) {
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
            this.mSecQSContainerImpl.fancyClippingPath.addRoundRect(0.0f, -(this.mShadeHeaderController != null ? r0.header.getHeight() : 0.0f), getWidth(), this.mFancyClippingTop, this.mFancyClippingRadii, Path.Direction.CW);
        } else {
            boolean z = this.mIsFullWidth;
            this.mFancyClippingPath.addRoundRect(z ? -this.mFancyClippingLeftInset : 0, this.mFancyClippingTop, z ? getWidth() + this.mFancyClippingRightInset : getWidth(), this.mFancyClippingBottom, this.mFancyClippingRadii, Path.Direction.CW);
        }
        invalidate();
    }

    public final void updateExpansion() {
        int measuredHeight = getMeasuredHeight();
        if (this.mQSCustomizer.isCustomizing()) {
            this.mQSCustomizer.getHeight();
        } else {
            Math.round(this.mQsExpansion * (measuredHeight - this.mHeader.getHeight()));
            this.mHeader.getHeight();
        }
    }

    public final void updateResources(SecQSPanelController secQSPanelController, SecQuickStatusBarHeaderController secQuickStatusBarHeaderController) {
        SecQSContainerImpl secQSContainerImpl = this.mSecQSContainerImpl;
        if (secQSContainerImpl != null) {
            NonInterceptingScrollView nonInterceptingScrollView = this.mQSPanelContainer;
            Context context = ((FrameLayout) this).mContext;
            ViewGroup.LayoutParams layoutParams = nonInterceptingScrollView != null ? nonInterceptingScrollView.getLayoutParams() : null;
            FrameLayout.LayoutParams layoutParams2 = layoutParams instanceof FrameLayout.LayoutParams ? (FrameLayout.LayoutParams) layoutParams : null;
            if (layoutParams2 == null) {
                return;
            }
            layoutParams2.topMargin = ((SecQSPanelResourcePicker) secQSContainerImpl.resourcePicker$delegate.getValue()).getQsScrollerTopMargin(context);
            return;
        }
        Context context2 = ((FrameLayout) this).mContext;
        int i = QSUtils.$r8$clinit;
        int quickQsOffsetHeight = LargeScreenUtils.shouldUseLargeScreenShadeHeader(context2.getResources()) ? 0 : SystemBarUtils.getQuickQsOffsetHeight(context2);
        if (!LargeScreenUtils.shouldUseLargeScreenShadeHeader(((FrameLayout) this).mContext.getResources())) {
            Flags.FEATURE_FLAGS.getClass();
            Context context3 = ((FrameLayout) this).mContext;
            LargeScreenHeaderHelper.Companion.getClass();
            quickQsOffsetHeight = LargeScreenHeaderHelper.Companion.getLargeScreenHeaderHeight(context3);
        }
        NonInterceptingScrollView nonInterceptingScrollView2 = this.mQSPanelContainer;
        if (nonInterceptingScrollView2 != null) {
            int paddingStart = nonInterceptingScrollView2.getPaddingStart();
            if (this.mSceneContainerEnabled) {
                quickQsOffsetHeight = 0;
            }
            nonInterceptingScrollView2.setPaddingRelative(paddingStart, quickQsOffsetHeight, this.mQSPanelContainer.getPaddingEnd(), this.mQSPanelContainer.getPaddingBottom());
        } else {
            SecQSPanel secQSPanel = this.mQSPanel;
            int paddingStart2 = secQSPanel.getPaddingStart();
            if (this.mSceneContainerEnabled) {
                quickQsOffsetHeight = 0;
            }
            secQSPanel.setPaddingRelative(paddingStart2, quickQsOffsetHeight, this.mQSPanel.getPaddingEnd(), this.mQSPanel.getPaddingBottom());
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
