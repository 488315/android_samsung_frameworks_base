package com.google.android.material.appbar;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ViewStubCompat;
import androidx.core.math.MathUtils;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.reflect.os.SeslBuildReflector$SeslVersionReflector;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.SeslAppBarHelper;
import com.google.android.material.appbar.model.view.AppBarView;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

/* loaded from: classes4.dex */
public class CollapsingToolbarLayout extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CollapsingTextHelper collapsingTextHelper;
    public final boolean collapsingTitleEnabled;
    public final Drawable contentScrim;
    public int currentOffset;
    public boolean drawCollapsingTitle;
    public View dummyView;
    public final int expandedMarginBottom;
    public final int expandedMarginEnd;
    public final int expandedMarginStart;
    public final int expandedMarginTop;
    public int extraMultilineHeight;
    public final boolean extraMultilineHeightEnabled;
    public final boolean forceApplySystemWindowInsetTop;
    public WindowInsetsCompat lastInsets;
    public float mDefaultHeight;
    public final int mExtendTitleAppearance;
    public final TextView mExtendedSubTitle;
    public final TextView mExtendedTitle;
    public final boolean mFadeToolbarTitle;
    public float mHeightProportion;
    public final StackViewGroup mStackViewGroup;
    public final boolean mSubTitleEnabled;
    public final HashMap mSuggestViewHashMap;
    public final boolean mTitleEnabled;
    public final LinearLayout mTitleLayout;
    public final LinearLayout mTitleLayoutParent;
    public final ViewStubCompat mViewStubCompat;
    public OffsetUpdateListener onOffsetChangedListener;
    public boolean refreshToolbar;
    public int scrimAlpha;
    public final long scrimAnimationDuration;
    public final TimeInterpolator scrimAnimationFadeInInterpolator;
    public final TimeInterpolator scrimAnimationFadeOutInterpolator;
    public ValueAnimator scrimAnimator;
    public final int scrimVisibleHeightTrigger;
    public boolean scrimsAreShown;
    public final Drawable statusBarScrim;
    public final Rect tmpRect;
    public ViewGroup toolbar;
    public View toolbarDirectChild;
    public final int toolbarId;
    public int topInsetApplied;

    public class OffsetUpdateListener implements AppBarLayout.OnOffsetChangedListener {
        public OffsetUpdateListener() {
            int i = CollapsingToolbarLayout.$r8$clinit;
            CollapsingToolbarLayout.this.updateDefaultHeight();
        }

        /* JADX WARN: Removed duplicated region for block: B:69:0x011f  */
        @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            AppCompatTextView appCompatTextView;
            CollapsingToolbarLayout collapsingToolbarLayout = CollapsingToolbarLayout.this;
            collapsingToolbarLayout.currentOffset = i;
            FrameLayout frameLayout = collapsingToolbarLayout.mStackViewGroup.rootView;
            int i2 = -i;
            float f = i2;
            frameLayout.setTranslationY(f / 3.0f);
            WindowInsetsCompat windowInsetsCompat = collapsingToolbarLayout.lastInsets;
            int systemWindowInsetTop = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
            int childCount = collapsingToolbarLayout.getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = collapsingToolbarLayout.getChildAt(i3);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                ViewOffsetHelper viewOffsetHelper = CollapsingToolbarLayout.getViewOffsetHelper(childAt);
                ViewGroup viewGroup = collapsingToolbarLayout.toolbar;
                if (viewGroup != null && (childAt instanceof ActionBarContextView)) {
                    if (((ActionBarContextView) childAt).mIsActionModeAccessibilityOn) {
                        viewGroup.setImportantForAccessibility(4);
                    } else {
                        viewGroup.setImportantForAccessibility(1);
                    }
                }
                int i4 = layoutParams.collapseMode;
                if (i4 == 1) {
                    viewOffsetHelper.setTopAndBottomOffset(MathUtils.clamp(i2, 0, ((collapsingToolbarLayout.getHeight() - CollapsingToolbarLayout.getViewOffsetHelper(childAt).layoutTop) - childAt.getHeight()) - ((FrameLayout.LayoutParams) ((LayoutParams) childAt.getLayoutParams())).bottomMargin));
                } else if (i4 == 2) {
                    viewOffsetHelper.setTopAndBottomOffset(Math.round(layoutParams.parallaxMult * f));
                }
            }
            collapsingToolbarLayout.updateScrimVisibility();
            if (collapsingToolbarLayout.statusBarScrim != null && systemWindowInsetTop > 0) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                collapsingToolbarLayout.postInvalidateOnAnimation();
            }
            if (!collapsingToolbarLayout.mTitleEnabled) {
                if (collapsingToolbarLayout.collapsingTitleEnabled) {
                    int height = collapsingToolbarLayout.getHeight();
                    WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                    collapsingToolbarLayout.collapsingTextHelper.setExpansionFraction(Math.abs(i) / ((height - collapsingToolbarLayout.getMinimumHeight()) - systemWindowInsetTop));
                    return;
                }
                return;
            }
            appBarLayout.getWindowVisibleDisplayFrame(new Rect());
            float height2 = collapsingToolbarLayout.getHeight() * 0.143f;
            float fAbs = Math.abs(appBarLayout.getTop());
            float f2 = 0.0f;
            float f3 = 255.0f - ((fAbs - 0.0f) * (100.0f / height2));
            if (f3 < 0.0f) {
                f3 = 0.0f;
            } else if (f3 > 255.0f || (i == 0 && f3 < 255.0f)) {
                f3 = 255.0f;
            }
            float f4 = f3 / 255.0f;
            boolean z = appBarLayout.getBottom() <= ((int) collapsingToolbarLayout.mDefaultHeight) || appBarLayout.lifted;
            frameLayout.setAlpha(z ? 0.0f : f4);
            ViewGroup viewGroup2 = collapsingToolbarLayout.toolbar;
            if (viewGroup2 instanceof Toolbar) {
                Toolbar toolbar = (Toolbar) viewGroup2;
                if (f4 == 1.0f) {
                    toolbar.setTitleAccessibilityEnabled(false);
                } else if (f4 == 0.0f) {
                    toolbar.setTitleAccessibilityEnabled(true);
                }
                if (!z) {
                    float height3 = (fAbs - (collapsingToolbarLayout.getHeight() * 0.35f)) * (150.0f / height2);
                    if (height3 >= 0.0f) {
                        if (height3 <= 255.0f) {
                            f2 = height3;
                        }
                    }
                    int i5 = (int) f2;
                    float f5 = f2 / 255.0f;
                    if (collapsingToolbarLayout.mFadeToolbarTitle) {
                        AppCompatTextView appCompatTextView2 = toolbar.mTitleTextView;
                        if (appCompatTextView2 != null) {
                            appCompatTextView2.setAlpha(f5);
                        }
                        Drawable background = toolbar.getBackground();
                        if (background != null) {
                            background.mutate().setAlpha(i5);
                        }
                    }
                    if (!TextUtils.isEmpty(toolbar.mSubtitleText) || (appCompatTextView = toolbar.mSubtitleTextView) == null) {
                    }
                    appCompatTextView.setAlpha(f5);
                    return;
                }
                toolbar.setTitleAccessibilityEnabled(true);
                f2 = 255.0f;
                int i52 = (int) f2;
                float f52 = f2 / 255.0f;
                if (collapsingToolbarLayout.mFadeToolbarTitle) {
                }
                if (TextUtils.isEmpty(toolbar.mSubtitleText)) {
                }
            }
        }
    }

    public CollapsingToolbarLayout(Context context) {
        this(context, null);
    }

    public static ViewOffsetHelper getViewOffsetHelper(View view) {
        ViewOffsetHelper viewOffsetHelper = (ViewOffsetHelper) view.getTag(R.id.view_offset_helper);
        if (viewOffsetHelper != null) {
            return viewOffsetHelper;
        }
        ViewOffsetHelper viewOffsetHelper2 = new ViewOffsetHelper(view);
        view.setTag(R.id.view_offset_helper, viewOffsetHelper2);
        return viewOffsetHelper2;
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public final void addView(View view, ViewGroup.LayoutParams layoutParams) {
        LayoutParams layoutParams2;
        super.addView(view, layoutParams);
        if (this.mTitleEnabled && (layoutParams2 = (LayoutParams) view.getLayoutParams()) != null && layoutParams2.isTitleCustom) {
            TextView textView = this.mExtendedTitle;
            if (textView != null && textView.getParent() == this.mTitleLayout) {
                this.mExtendedTitle.setVisibility(8);
            }
            TextView textView2 = this.mExtendedSubTitle;
            if (textView2 != null && textView2.getParent() == this.mTitleLayout) {
                this.mExtendedSubTitle.setVisibility(8);
            }
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            this.mTitleLayout.addView(view, layoutParams);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        Drawable drawable;
        super.draw(canvas);
        ensureToolbar();
        if (this.toolbar == null && (drawable = this.contentScrim) != null && this.scrimAlpha > 0) {
            drawable.mutate().setAlpha(this.scrimAlpha);
            this.contentScrim.draw(canvas);
        }
        if (this.collapsingTitleEnabled && this.drawCollapsingTitle) {
            ViewGroup viewGroup = this.toolbar;
            this.collapsingTextHelper.draw(canvas);
        }
        if (this.statusBarScrim == null || this.scrimAlpha <= 0) {
            return;
        }
        WindowInsetsCompat windowInsetsCompat = this.lastInsets;
        int systemWindowInsetTop = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
        if (systemWindowInsetTop > 0) {
            this.statusBarScrim.setBounds(0, -this.currentOffset, getWidth(), systemWindowInsetTop - this.currentOffset);
            this.statusBarScrim.mutate().setAlpha(this.scrimAlpha);
            this.statusBarScrim.draw(canvas);
        }
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j) {
        boolean z;
        View view2;
        Drawable drawable = this.contentScrim;
        if (drawable == null || this.scrimAlpha <= 0 || ((view2 = this.toolbarDirectChild) == null || view2 == this ? view != this.toolbar : view != view2)) {
            z = false;
        } else {
            drawable.setBounds(0, 0, getWidth(), getHeight());
            this.contentScrim.mutate().setAlpha(this.scrimAlpha);
            this.contentScrim.draw(canvas);
            z = true;
        }
        return super.drawChild(canvas, view, j) || z;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        ColorStateList colorStateList;
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.statusBarScrim;
        boolean z = false;
        boolean state = (drawable == null || !drawable.isStateful()) ? false : drawable.setState(drawableState);
        Drawable drawable2 = this.contentScrim;
        if (drawable2 != null && drawable2.isStateful()) {
            state |= drawable2.setState(drawableState);
        }
        CollapsingTextHelper collapsingTextHelper = this.collapsingTextHelper;
        if (collapsingTextHelper != null) {
            collapsingTextHelper.state = drawableState;
            ColorStateList colorStateList2 = collapsingTextHelper.collapsedTextColor;
            if ((colorStateList2 != null && colorStateList2.isStateful()) || ((colorStateList = collapsingTextHelper.expandedTextColor) != null && colorStateList.isStateful())) {
                collapsingTextHelper.recalculate(false);
                z = true;
            }
            state |= z;
        }
        if (state) {
            invalidate();
        }
    }

    public final void ensureToolbar() {
        View view;
        if (this.refreshToolbar) {
            ViewGroup viewGroup = null;
            this.toolbar = null;
            this.toolbarDirectChild = null;
            int i = this.toolbarId;
            if (i != -1) {
                ViewGroup viewGroup2 = (ViewGroup) findViewById(i);
                this.toolbar = viewGroup2;
                if (viewGroup2 != null) {
                    ViewParent parent = viewGroup2.getParent();
                    View view2 = viewGroup2;
                    while (parent != this && parent != null) {
                        if (parent instanceof View) {
                            view2 = (View) parent;
                        }
                        parent = parent.getParent();
                        view2 = view2;
                    }
                    this.toolbarDirectChild = view2;
                }
            }
            if (this.toolbar == null) {
                int childCount = getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = getChildAt(i2);
                    if ((childAt instanceof Toolbar) || (childAt instanceof android.widget.Toolbar)) {
                        viewGroup = (ViewGroup) childAt;
                        break;
                    }
                }
                this.toolbar = viewGroup;
                ViewStubCompat viewStubCompat = this.mViewStubCompat;
                if (viewStubCompat != null) {
                    viewStubCompat.bringToFront();
                    this.mViewStubCompat.invalidate();
                }
            }
            if (!this.collapsingTitleEnabled && (view = this.dummyView) != null) {
                ViewParent parent2 = view.getParent();
                if (parent2 instanceof ViewGroup) {
                    ((ViewGroup) parent2).removeView(this.dummyView);
                }
            }
            if (this.collapsingTitleEnabled && this.toolbar != null) {
                if (this.dummyView == null) {
                    this.dummyView = new View(getContext());
                }
                if (this.dummyView.getParent() == null) {
                    this.toolbar.addView(this.dummyView, -1, -1);
                }
            }
            this.refreshToolbar = false;
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if (parent instanceof AppBarLayout) {
            AppBarLayout appBarLayout = (AppBarLayout) parent;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            setFitsSystemWindows(appBarLayout.getFitsSystemWindows());
            if (this.onOffsetChangedListener == null) {
                this.onOffsetChangedListener = new OffsetUpdateListener();
            }
            appBarLayout.addOnOffsetChangedListener(this.onOffsetChangedListener);
            ViewCompat.Api20Impl.requestApplyInsets(this);
        }
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        if (this.collapsingTitleEnabled) {
            this.collapsingTextHelper.maybeUpdateFontWeightAdjustment(configuration);
        }
        SeslAppBarHelper.Companion companion = SeslAppBarHelper.Companion;
        Context context = getContext();
        companion.getClass();
        this.mHeightProportion = SeslAppBarHelper.Companion.getAppBarProPortion(context);
        updateDefaultHeight();
        updateTitleLayout();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        List list;
        ViewParent parent = getParent();
        OffsetUpdateListener offsetUpdateListener = this.onOffsetChangedListener;
        if (offsetUpdateListener != null && (parent instanceof AppBarLayout) && (list = ((AppBarLayout) parent).listeners) != null) {
            ((ArrayList) list).remove(offsetUpdateListener);
        }
        super.onDetachedFromWindow();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        super.onLayout(z, i, i2, i3, i4);
        WindowInsetsCompat windowInsetsCompat = this.lastInsets;
        if (windowInsetsCompat != null) {
            int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
            int childCount = getChildCount();
            for (int i5 = 0; i5 < childCount; i5++) {
                View childAt = getChildAt(i5);
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (!childAt.getFitsSystemWindows() && childAt.getTop() < systemWindowInsetTop) {
                    childAt.offsetTopAndBottom(systemWindowInsetTop);
                }
            }
        }
        int childCount2 = getChildCount();
        for (int i6 = 0; i6 < childCount2; i6++) {
            ViewOffsetHelper viewOffsetHelper = getViewOffsetHelper(getChildAt(i6));
            viewOffsetHelper.layoutTop = viewOffsetHelper.view.getTop();
            viewOffsetHelper.layoutLeft = viewOffsetHelper.view.getLeft();
        }
        updateTextBounds(false, i, i2, i3, i4);
        updateTitleFromToolbarIfNeeded();
        updateScrimVisibility();
        int childCount3 = getChildCount();
        for (int i7 = 0; i7 < childCount3; i7++) {
            getViewOffsetHelper(getChildAt(i7)).applyOffsets();
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) throws Resources.NotFoundException {
        CollapsingToolbarLayout collapsingToolbarLayout;
        int measuredHeight;
        int measuredHeight2;
        ensureToolbar();
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i2);
        WindowInsetsCompat windowInsetsCompat = this.lastInsets;
        int systemWindowInsetTop = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
        if ((mode == 0 || this.forceApplySystemWindowInsetTop) && systemWindowInsetTop > 0) {
            this.topInsetApplied = systemWindowInsetTop;
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(getMeasuredHeight() + systemWindowInsetTop, 1073741824));
        }
        if (this.extraMultilineHeightEnabled && this.collapsingTitleEnabled && this.collapsingTextHelper.maxLines > 1) {
            updateTitleFromToolbarIfNeeded();
            collapsingToolbarLayout = this;
            collapsingToolbarLayout.updateTextBounds(true, 0, 0, getMeasuredWidth(), getMeasuredHeight());
            CollapsingTextHelper collapsingTextHelper = collapsingToolbarLayout.collapsingTextHelper;
            int i3 = collapsingTextHelper.expandedLineCount;
            if (i3 > 1) {
                TextPaint textPaint = collapsingTextHelper.tmpPaint;
                textPaint.setTextSize(collapsingTextHelper.expandedTextSize);
                textPaint.setTypeface(collapsingTextHelper.expandedTypeface);
                textPaint.setLetterSpacing(collapsingTextHelper.expandedLetterSpacing);
                collapsingToolbarLayout.extraMultilineHeight = (i3 - 1) * Math.round(collapsingTextHelper.tmpPaint.descent() + (-collapsingTextHelper.tmpPaint.ascent()));
                super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(collapsingToolbarLayout.getMeasuredHeight() + collapsingToolbarLayout.extraMultilineHeight, 1073741824));
            }
        } else {
            collapsingToolbarLayout = this;
        }
        ViewGroup viewGroup = collapsingToolbarLayout.toolbar;
        if (viewGroup != null) {
            View view = collapsingToolbarLayout.toolbarDirectChild;
            if (view == null || view == collapsingToolbarLayout) {
                ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                    measuredHeight = viewGroup.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
                } else {
                    measuredHeight = viewGroup.getMeasuredHeight();
                }
                collapsingToolbarLayout.setMinimumHeight(measuredHeight);
                return;
            }
            ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
            if (layoutParams2 instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) layoutParams2;
                measuredHeight2 = view.getMeasuredHeight() + marginLayoutParams2.topMargin + marginLayoutParams2.bottomMargin;
            } else {
                measuredHeight2 = view.getMeasuredHeight();
            }
            collapsingToolbarLayout.setMinimumHeight(measuredHeight2);
        }
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        Drawable drawable = this.contentScrim;
        if (drawable != null) {
            drawable.setBounds(0, 0, i, i2);
        }
    }

    public final void setTitle(CharSequence charSequence) throws Resources.NotFoundException {
        if (this.collapsingTitleEnabled) {
            CollapsingTextHelper collapsingTextHelper = this.collapsingTextHelper;
            if (charSequence == null || !TextUtils.equals(collapsingTextHelper.text, charSequence)) {
                collapsingTextHelper.text = charSequence;
                collapsingTextHelper.textToDraw = null;
                Bitmap bitmap = collapsingTextHelper.expandedTitleTexture;
                if (bitmap != null) {
                    bitmap.recycle();
                    collapsingTextHelper.expandedTitleTexture = null;
                }
                collapsingTextHelper.recalculate(false);
            }
            setContentDescription(this.collapsingTitleEnabled ? this.collapsingTextHelper.text : this.mExtendedTitle.getText());
        } else {
            TextView textView = this.mExtendedTitle;
            if (textView != null) {
                textView.setText(charSequence);
            }
        }
        updateTitleLayout();
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        Drawable drawable = this.statusBarScrim;
        if (drawable != null && drawable.isVisible() != z) {
            this.statusBarScrim.setVisible(z, false);
        }
        Drawable drawable2 = this.contentScrim;
        if (drawable2 == null || drawable2.isVisible() == z) {
            return;
        }
        this.contentScrim.setVisible(z, false);
    }

    public final void updateDefaultHeight() {
        if (!(getParent() instanceof AppBarLayout)) {
            this.mDefaultHeight = getResources().getDimensionPixelSize(R.dimen.sesl_action_bar_height_with_padding);
        } else {
            ((AppBarLayout) getParent()).getClass();
            this.mDefaultHeight = getResources().getDimensionPixelSize(R.dimen.sesl_action_bar_height_with_padding);
        }
    }

    public final void updateScrimVisibility() {
        int iMin;
        int i;
        ViewGroup viewGroup;
        if (this.contentScrim == null && this.statusBarScrim == null) {
            return;
        }
        int height = getHeight() + this.currentOffset;
        int i2 = this.scrimVisibleHeightTrigger;
        if (i2 >= 0) {
            iMin = i2 + this.topInsetApplied + this.extraMultilineHeight;
        } else {
            WindowInsetsCompat windowInsetsCompat = this.lastInsets;
            int systemWindowInsetTop = windowInsetsCompat != null ? windowInsetsCompat.getSystemWindowInsetTop() : 0;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            int minimumHeight = getMinimumHeight();
            iMin = minimumHeight > 0 ? Math.min((minimumHeight * 2) + systemWindowInsetTop, getHeight()) : getHeight() / 3;
        }
        boolean z = height < iMin;
        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
        boolean z2 = isLaidOut() && !isInEditMode();
        if (this.scrimsAreShown != z) {
            if (z2) {
                i = z ? 255 : 0;
                ensureToolbar();
                ValueAnimator valueAnimator = this.scrimAnimator;
                if (valueAnimator == null) {
                    ValueAnimator valueAnimator2 = new ValueAnimator();
                    this.scrimAnimator = valueAnimator2;
                    valueAnimator2.setInterpolator(i > this.scrimAlpha ? this.scrimAnimationFadeInInterpolator : this.scrimAnimationFadeOutInterpolator);
                    this.scrimAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.appbar.CollapsingToolbarLayout.2
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                            ViewGroup viewGroup2;
                            CollapsingToolbarLayout collapsingToolbarLayout = CollapsingToolbarLayout.this;
                            int iIntValue = ((Integer) valueAnimator3.getAnimatedValue()).intValue();
                            if (iIntValue != collapsingToolbarLayout.scrimAlpha) {
                                if (collapsingToolbarLayout.contentScrim != null && (viewGroup2 = collapsingToolbarLayout.toolbar) != null) {
                                    WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                                    viewGroup2.postInvalidateOnAnimation();
                                }
                                collapsingToolbarLayout.scrimAlpha = iIntValue;
                                WeakHashMap weakHashMap4 = ViewCompat.sViewPropertyAnimatorMap;
                                collapsingToolbarLayout.postInvalidateOnAnimation();
                            }
                        }
                    });
                } else if (valueAnimator.isRunning()) {
                    this.scrimAnimator.cancel();
                }
                this.scrimAnimator.setDuration(this.scrimAnimationDuration);
                this.scrimAnimator.setIntValues(this.scrimAlpha, i);
                this.scrimAnimator.start();
            } else {
                i = z ? 255 : 0;
                if (i != this.scrimAlpha) {
                    if (this.contentScrim != null && (viewGroup = this.toolbar) != null) {
                        viewGroup.postInvalidateOnAnimation();
                    }
                    this.scrimAlpha = i;
                    postInvalidateOnAnimation();
                }
            }
            this.scrimsAreShown = z;
        }
    }

    public final void updateTextBounds(boolean z, int i, int i2, int i3, int i4) {
        View view;
        int titleMarginBottom;
        int titleMarginEnd;
        int titleMarginTop;
        if (!this.collapsingTitleEnabled || (view = this.dummyView) == null) {
            return;
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int titleMarginStart = 0;
        boolean z2 = view.isAttachedToWindow() && this.dummyView.getVisibility() == 0;
        this.drawCollapsingTitle = z2;
        if (z2 || z) {
            boolean z3 = getLayoutDirection() == 1;
            View view2 = this.toolbarDirectChild;
            if (view2 == null) {
                view2 = this.toolbar;
            }
            int height = ((getHeight() - getViewOffsetHelper(view2).layoutTop) - view2.getHeight()) - ((FrameLayout.LayoutParams) ((LayoutParams) view2.getLayoutParams())).bottomMargin;
            DescendantOffsetUtils.getDescendantRect(this, this.dummyView, this.tmpRect);
            ViewGroup viewGroup = this.toolbar;
            if (viewGroup instanceof Toolbar) {
                Toolbar toolbar = (Toolbar) viewGroup;
                titleMarginStart = toolbar.mTitleMarginStart;
                titleMarginEnd = toolbar.mTitleMarginEnd;
                titleMarginTop = toolbar.mTitleMarginTop;
                titleMarginBottom = toolbar.mTitleMarginBottom;
            } else if (viewGroup instanceof android.widget.Toolbar) {
                android.widget.Toolbar toolbar2 = (android.widget.Toolbar) viewGroup;
                titleMarginStart = toolbar2.getTitleMarginStart();
                titleMarginEnd = toolbar2.getTitleMarginEnd();
                titleMarginTop = toolbar2.getTitleMarginTop();
                titleMarginBottom = toolbar2.getTitleMarginBottom();
            } else {
                titleMarginBottom = 0;
                titleMarginEnd = 0;
                titleMarginTop = 0;
            }
            CollapsingTextHelper collapsingTextHelper = this.collapsingTextHelper;
            Rect rect = this.tmpRect;
            int i5 = rect.left + (z3 ? titleMarginEnd : titleMarginStart);
            int i6 = rect.top + height + titleMarginTop;
            int i7 = rect.right;
            if (!z3) {
                titleMarginStart = titleMarginEnd;
            }
            int i8 = i7 - titleMarginStart;
            int i9 = (rect.bottom + height) - titleMarginBottom;
            Rect rect2 = collapsingTextHelper.collapsedBounds;
            if (rect2.left != i5 || rect2.top != i6 || rect2.right != i8 || rect2.bottom != i9) {
                rect2.set(i5, i6, i8, i9);
                collapsingTextHelper.boundsChanged = true;
            }
            CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
            int i10 = z3 ? this.expandedMarginEnd : this.expandedMarginStart;
            int i11 = this.tmpRect.top + this.expandedMarginTop;
            int i12 = (i3 - i) - (z3 ? this.expandedMarginStart : this.expandedMarginEnd);
            int i13 = (i4 - i2) - this.expandedMarginBottom;
            Rect rect3 = collapsingTextHelper2.expandedBounds;
            if (rect3.left != i10 || rect3.top != i11 || rect3.right != i12 || rect3.bottom != i13) {
                rect3.set(i10, i11, i12, i13);
                collapsingTextHelper2.boundsChanged = true;
            }
            this.collapsingTextHelper.recalculate(z);
        }
    }

    public final void updateTitleFromToolbarIfNeeded() throws Resources.NotFoundException {
        if (this.toolbar != null && this.collapsingTitleEnabled && TextUtils.isEmpty(this.collapsingTextHelper.text)) {
            ViewGroup viewGroup = this.toolbar;
            setTitle(viewGroup instanceof Toolbar ? ((Toolbar) viewGroup).mTitleText : viewGroup instanceof android.widget.Toolbar ? ((android.widget.Toolbar) viewGroup).getTitle() : null);
        }
    }

    public final void updateTitleLayout() throws Resources.NotFoundException {
        Resources resources = getResources();
        SeslAppBarHelper.Companion companion = SeslAppBarHelper.Companion;
        Context context = getContext();
        companion.getClass();
        this.mHeightProportion = SeslAppBarHelper.Companion.getAppBarProPortion(context);
        if (this.mTitleEnabled) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(this.mExtendTitleAppearance, R$styleable.TextAppearance);
            TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes.peekValue(0);
            if (typedValuePeekValue == null) {
                Log.i("Sesl_CTL", "ExtendTitleAppearance value is null");
                typedArrayObtainStyledAttributes.recycle();
                return;
            }
            float fComplexToFloat = TypedValue.complexToFloat(typedValuePeekValue.data);
            float fMin = Math.min(resources.getConfiguration().fontScale, 1.0f);
            typedArrayObtainStyledAttributes.recycle();
            StringBuilder sb = new StringBuilder("updateTitleLayout : context : ");
            sb.append(getContext());
            sb.append(", textSize : ");
            sb.append(fComplexToFloat);
            sb.append(", fontScale : ");
            sb.append(fMin);
            sb.append(", mSubTitleEnabled : ");
            KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(sb, this.mSubTitleEnabled, "Sesl_CTL");
            if (this.mSubTitleEnabled) {
                this.mExtendedTitle.setTextSize(0, resources.getDimensionPixelSize(R.dimen.sesl_appbar_extended_title_text_size_with_subtitle));
                TextView textView = this.mExtendedSubTitle;
                if (textView != null) {
                    textView.setTextSize(0, resources.getDimensionPixelSize(R.dimen.sesl_appbar_extended_subtitle_text_size));
                }
            } else {
                this.mExtendedTitle.setTextSize(1, fComplexToFloat * fMin);
            }
            if (Math.abs(this.mHeightProportion - 0.3f) >= 1.0E-5f || !this.mSubTitleEnabled) {
                this.mExtendedTitle.setSingleLine(false);
                this.mExtendedTitle.setMaxLines(2);
            } else {
                this.mExtendedTitle.setSingleLine(true);
                this.mExtendedTitle.setMaxLines(1);
            }
            int maxLines = this.mExtendedTitle.getMaxLines();
            if (SeslBuildReflector$SeslVersionReflector.getField_SEM_PLATFORM_INT() >= 120000) {
                if (maxLines > 1) {
                    try {
                        int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
                        int dimensionPixelOffset = identifier > 0 ? getResources().getDimensionPixelOffset(identifier) : 0;
                        if (this.mSubTitleEnabled && dimensionPixelOffset > 0) {
                            dimensionPixelOffset += getResources().getDimensionPixelSize(R.dimen.sesl_action_bar_top_padding);
                        }
                        LinearLayout linearLayout = this.mTitleLayoutParent;
                        linearLayout.setPadding(linearLayout.getPaddingLeft(), linearLayout.getPaddingTop(), linearLayout.getPaddingRight(), dimensionPixelOffset);
                    } catch (Exception e) {
                        Log.e("Sesl_CTL", Log.getStackTraceString(e));
                    }
                } else {
                    this.mExtendedTitle.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
                    this.mExtendedTitle.setAutoSizeTextTypeWithDefaults(0);
                    this.mExtendedTitle.setTextSize(0, resources.getDimensionPixelSize(R.dimen.sesl_appbar_extended_title_text_size_with_subtitle));
                }
            }
        }
        Iterator it = this.mSuggestViewHashMap.values().iterator();
        while (it.hasNext()) {
            ((AppBarView) it.next()).updateResource(getContext());
        }
    }

    @Override // android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.contentScrim || drawable == this.statusBarScrim;
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.collapsingToolbarLayoutStyle);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final FrameLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet, int i) throws Resources.NotFoundException {
        int i2;
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        TextUtils.TruncateAt truncateAt;
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, R.style.Widget_Design_CollapsingToolbar), attributeSet, i);
        this.refreshToolbar = true;
        this.tmpRect = new Rect();
        this.scrimVisibleHeightTrigger = -1;
        this.topInsetApplied = 0;
        this.extraMultilineHeight = 0;
        this.mSuggestViewHashMap = new HashMap();
        this.mFadeToolbarTitle = true;
        Context context2 = getContext();
        TypedArray typedArrayObtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, com.google.android.material.R$styleable.CollapsingToolbarLayout, i, R.style.Widget_Design_CollapsingToolbar, new int[0]);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(24, false);
        this.collapsingTitleEnabled = z;
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(13, true);
        this.mTitleEnabled = z2;
        if (z == z2 && z) {
            this.collapsingTitleEnabled = false;
        }
        CollapsingTextHelper collapsingTextHelper = new CollapsingTextHelper(this);
        this.collapsingTextHelper = collapsingTextHelper;
        if (this.collapsingTitleEnabled) {
            collapsingTextHelper.textSizeInterpolator = AnimationUtils.DECELERATE_INTERPOLATOR;
            collapsingTextHelper.recalculate(false);
            collapsingTextHelper.isRtlTextDirectionHeuristicsEnabled = false;
            int i3 = typedArrayObtainStyledAttributes.getInt(4, 8388691);
            if (collapsingTextHelper.expandedTextGravity != i3) {
                collapsingTextHelper.expandedTextGravity = i3;
                collapsingTextHelper.recalculate(false);
            }
            int i4 = typedArrayObtainStyledAttributes.getInt(0, 8388627);
            if (collapsingTextHelper.collapsedTextGravity != i4) {
                collapsingTextHelper.collapsedTextGravity = i4;
                collapsingTextHelper.recalculate(false);
            }
            int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(5, 0);
            this.expandedMarginBottom = dimensionPixelSize;
            this.expandedMarginEnd = dimensionPixelSize;
            this.expandedMarginTop = dimensionPixelSize;
            this.expandedMarginStart = dimensionPixelSize;
        }
        new ElevationOverlayProvider(context2);
        this.mExtendTitleAppearance = typedArrayObtainStyledAttributes.getResourceId(14, 0);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(12, 0);
        if (typedArrayObtainStyledAttributes.hasValue(10)) {
            this.mExtendTitleAppearance = typedArrayObtainStyledAttributes.getResourceId(10, 0);
        }
        CharSequence text = typedArrayObtainStyledAttributes.getText(21);
        this.mSubTitleEnabled = z2 && !TextUtils.isEmpty(text);
        StackViewGroup stackViewGroup = new StackViewGroup(new FrameLayout(context2));
        this.mStackViewGroup = stackViewGroup;
        FrameLayout frameLayout = stackViewGroup.rootView;
        addView(frameLayout);
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context2).inflate(R.layout.sesl_app_bar, (ViewGroup) frameLayout, false);
        StackViewGroup stackViewGroup2 = this.mStackViewGroup;
        if (viewGroup != null) {
            stackViewGroup2.sceneStack.push((View) viewGroup);
            stackViewGroup2.rootView.addView(viewGroup);
        } else {
            stackViewGroup2.getClass();
        }
        LinearLayout linearLayout = (LinearLayout) viewGroup.findViewById(R.id.collapsing_appbar_title_layout_parent);
        this.mTitleLayoutParent = linearLayout;
        if (linearLayout != null) {
            int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
            int dimensionPixelOffset = identifier > 0 ? getResources().getDimensionPixelOffset(identifier) : 0;
            if (dimensionPixelOffset > 0) {
                LinearLayout linearLayout2 = this.mTitleLayoutParent;
                linearLayout2.setPadding(linearLayout2.getPaddingLeft(), linearLayout2.getPaddingTop(), linearLayout2.getPaddingRight(), dimensionPixelOffset);
            }
        }
        this.mTitleLayout = (LinearLayout) findViewById(R.id.collapsing_appbar_title_layout);
        if (z2) {
            TextView textView = (TextView) findViewById(R.id.collapsing_appbar_extended_title);
            textView.setHyphenationFrequency(1);
            textView.setTextAppearance(context2, this.mExtendTitleAppearance);
            textView.setVisibility(0);
            this.mExtendedTitle = textView;
        }
        if (this.mSubTitleEnabled) {
            if (z2 && !TextUtils.isEmpty(text)) {
                this.mSubTitleEnabled = true;
                if (this.mExtendedSubTitle == null) {
                    TextView textView2 = (TextView) findViewById(R.id.collapsing_appbar_extended_subtitle);
                    textView2.setTextAppearance(getContext(), resourceId);
                    this.mExtendedSubTitle = textView2;
                }
                this.mExtendedSubTitle.setText(text);
                this.mExtendedSubTitle.setVisibility(0);
                TextView textView3 = this.mExtendedTitle;
                if (textView3 != null) {
                    textView3.setTextSize(0, getContext().getResources().getDimensionPixelSize(R.dimen.sesl_appbar_extended_title_text_size_with_subtitle));
                }
            } else {
                this.mSubTitleEnabled = false;
                TextView textView4 = this.mExtendedSubTitle;
                if (textView4 != null) {
                    textView4.setVisibility(8);
                }
            }
            updateTitleLayout();
            requestLayout();
        }
        updateDefaultHeight();
        updateTitleLayout();
        if (typedArrayObtainStyledAttributes.hasValue(8)) {
            this.expandedMarginStart = typedArrayObtainStyledAttributes.getDimensionPixelSize(8, 0);
        }
        if (typedArrayObtainStyledAttributes.hasValue(7)) {
            this.expandedMarginEnd = typedArrayObtainStyledAttributes.getDimensionPixelSize(7, 0);
        }
        if (typedArrayObtainStyledAttributes.hasValue(9)) {
            this.expandedMarginTop = typedArrayObtainStyledAttributes.getDimensionPixelSize(9, 0);
        }
        if (typedArrayObtainStyledAttributes.hasValue(6)) {
            this.expandedMarginBottom = typedArrayObtainStyledAttributes.getDimensionPixelSize(6, 0);
        }
        setTitle(typedArrayObtainStyledAttributes.getText(22));
        if (this.collapsingTitleEnabled) {
            collapsingTextHelper.setExpandedTextAppearance(R.style.TextAppearance_Design_CollapsingToolbar_Expanded);
            collapsingTextHelper.setCollapsedTextAppearance(2132018386);
            if (typedArrayObtainStyledAttributes.hasValue(10)) {
                collapsingTextHelper.setExpandedTextAppearance(typedArrayObtainStyledAttributes.getResourceId(10, 0));
            }
            if (typedArrayObtainStyledAttributes.hasValue(1)) {
                collapsingTextHelper.setCollapsedTextAppearance(typedArrayObtainStyledAttributes.getResourceId(1, 0));
            }
            if (typedArrayObtainStyledAttributes.hasValue(26)) {
                int i5 = typedArrayObtainStyledAttributes.getInt(26, -1);
                if (i5 == 0) {
                    truncateAt = TextUtils.TruncateAt.START;
                } else if (i5 == 1) {
                    truncateAt = TextUtils.TruncateAt.MIDDLE;
                } else if (i5 != 3) {
                    truncateAt = TextUtils.TruncateAt.END;
                } else {
                    truncateAt = TextUtils.TruncateAt.MARQUEE;
                }
                collapsingTextHelper.titleTextEllipsize = truncateAt;
                collapsingTextHelper.recalculate(false);
            }
            if (typedArrayObtainStyledAttributes.hasValue(11) && collapsingTextHelper.expandedTextColor != (colorStateList2 = MaterialResources.getColorStateList(context2, typedArrayObtainStyledAttributes, 11))) {
                collapsingTextHelper.expandedTextColor = colorStateList2;
                collapsingTextHelper.recalculate(false);
            }
            if (typedArrayObtainStyledAttributes.hasValue(2) && collapsingTextHelper.collapsedTextColor != (colorStateList = MaterialResources.getColorStateList(context2, typedArrayObtainStyledAttributes, 2))) {
                collapsingTextHelper.collapsedTextColor = colorStateList;
                collapsingTextHelper.recalculate(false);
            }
        }
        this.scrimVisibleHeightTrigger = typedArrayObtainStyledAttributes.getDimensionPixelSize(19, -1);
        if (typedArrayObtainStyledAttributes.hasValue(17) && (i2 = typedArrayObtainStyledAttributes.getInt(17, 1)) != collapsingTextHelper.maxLines) {
            collapsingTextHelper.maxLines = i2;
            Bitmap bitmap = collapsingTextHelper.expandedTitleTexture;
            if (bitmap != null) {
                bitmap.recycle();
                collapsingTextHelper.expandedTitleTexture = null;
            }
            collapsingTextHelper.recalculate(false);
        }
        if (typedArrayObtainStyledAttributes.hasValue(25)) {
            collapsingTextHelper.positionInterpolator = android.view.animation.AnimationUtils.loadInterpolator(context2, typedArrayObtainStyledAttributes.getResourceId(25, 0));
            collapsingTextHelper.recalculate(false);
        }
        this.scrimAnimationDuration = typedArrayObtainStyledAttributes.getInt(18, VolteConstants.ErrorCode.BUSY_EVERYWHERE);
        this.scrimAnimationFadeInInterpolator = MotionUtils.resolveThemeInterpolator(context2, R.attr.motionEasingStandardInterpolator, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
        this.scrimAnimationFadeOutInterpolator = MotionUtils.resolveThemeInterpolator(context2, R.attr.motionEasingStandardInterpolator, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(3);
        Drawable drawable2 = this.contentScrim;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            Drawable drawableMutate = drawable != null ? drawable.mutate() : null;
            this.contentScrim = drawableMutate;
            if (drawableMutate != null) {
                drawableMutate.setBounds(0, 0, getWidth(), getHeight());
                this.contentScrim.setCallback(this);
                this.contentScrim.setAlpha(this.scrimAlpha);
            }
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            postInvalidateOnAnimation();
        }
        Drawable drawable3 = typedArrayObtainStyledAttributes.getDrawable(20);
        Drawable drawable4 = this.statusBarScrim;
        if (drawable4 != drawable3) {
            if (drawable4 != null) {
                drawable4.setCallback(null);
            }
            Drawable drawableMutate2 = drawable3 != null ? drawable3.mutate() : null;
            this.statusBarScrim = drawableMutate2;
            if (drawableMutate2 != null) {
                if (drawableMutate2.isStateful()) {
                    this.statusBarScrim.setState(getDrawableState());
                }
                Drawable drawable5 = this.statusBarScrim;
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                drawable5.setLayoutDirection(getLayoutDirection());
                this.statusBarScrim.setVisible(getVisibility() == 0, false);
                this.statusBarScrim.setCallback(this);
                this.statusBarScrim.setAlpha(this.scrimAlpha);
            }
            WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
            postInvalidateOnAnimation();
        }
        this.toolbarId = typedArrayObtainStyledAttributes.getResourceId(27, -1);
        this.forceApplySystemWindowInsetTop = typedArrayObtainStyledAttributes.getBoolean(16, false);
        this.extraMultilineHeightEnabled = typedArrayObtainStyledAttributes.getBoolean(15, false);
        typedArrayObtainStyledAttributes.recycle();
        TypedArray typedArrayObtainStyledAttributes2 = getContext().obtainStyledAttributes(R$styleable.AppCompatTheme);
        if (!typedArrayObtainStyledAttributes2.getBoolean(147, false)) {
            LayoutInflater.from(context2).inflate(R.layout.sesl_material_action_mode_view_stub, (ViewGroup) this, true);
            this.mViewStubCompat = (ViewStubCompat) findViewById(R.id.action_mode_bar_stub);
        }
        typedArrayObtainStyledAttributes2.recycle();
        setWillNotDraw(false);
        OnApplyWindowInsetsListener onApplyWindowInsetsListener = new OnApplyWindowInsetsListener() { // from class: com.google.android.material.appbar.CollapsingToolbarLayout.1
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(WindowInsetsCompat windowInsetsCompat, View view) {
                CollapsingToolbarLayout collapsingToolbarLayout = CollapsingToolbarLayout.this;
                collapsingToolbarLayout.getClass();
                WeakHashMap weakHashMap4 = ViewCompat.sViewPropertyAnimatorMap;
                WindowInsetsCompat windowInsetsCompat2 = collapsingToolbarLayout.getFitsSystemWindows() ? windowInsetsCompat : null;
                if (!Objects.equals(collapsingToolbarLayout.lastInsets, windowInsetsCompat2)) {
                    collapsingToolbarLayout.lastInsets = windowInsetsCompat2;
                    collapsingToolbarLayout.requestLayout();
                }
                return windowInsetsCompat.mImpl.consumeSystemWindowInsets();
            }
        };
        WeakHashMap weakHashMap4 = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(this, onApplyWindowInsetsListener);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public class LayoutParams extends FrameLayout.LayoutParams {
        public final int collapseMode;
        public final boolean isTitleCustom;
        public final float parallaxMult;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.collapseMode = 0;
            this.parallaxMult = 0.5f;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.google.android.material.R$styleable.CollapsingToolbarLayout_Layout);
            this.collapseMode = typedArrayObtainStyledAttributes.getInt(1, 0);
            this.parallaxMult = typedArrayObtainStyledAttributes.getFloat(2, 0.5f);
            this.isTitleCustom = typedArrayObtainStyledAttributes.getBoolean(0, false);
            typedArrayObtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.collapseMode = 0;
            this.parallaxMult = 0.5f;
        }

        public LayoutParams(int i, int i2, int i3) {
            super(i, i2, i3);
            this.collapseMode = 0;
            this.parallaxMult = 0.5f;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.collapseMode = 0;
            this.parallaxMult = 0.5f;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.collapseMode = 0;
            this.parallaxMult = 0.5f;
        }

        public LayoutParams(FrameLayout.LayoutParams layoutParams) {
            super(layoutParams);
            this.collapseMode = 0;
            this.parallaxMult = 0.5f;
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((FrameLayout.LayoutParams) layoutParams);
            this.collapseMode = 0;
            this.parallaxMult = 0.5f;
            this.collapseMode = layoutParams.collapseMode;
            this.parallaxMult = layoutParams.parallaxMult;
        }
    }
}
