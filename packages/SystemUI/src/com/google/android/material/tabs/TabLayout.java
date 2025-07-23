package com.google.android.material.tabs;

import android.animation.AnimatorInflater;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.util.SeslMisc;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pools$SimplePool;
import androidx.core.util.Pools$SynchronizedPool;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.widget.SeslHorizontalScrollViewReflector;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.tabs.TabLayout;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@ViewPager.DecorView
/* loaded from: classes4.dex */
public class TabLayout extends HorizontalScrollView {
    public static final Pools$SynchronizedPool tabPool = new Pools$SynchronizedPool(16);
    public AdapterChangeListener adapterChangeListener;
    public ViewPagerOnTabSelectedListener currentVpSelectedListener;
    public final int defaultTabTextAppearance;
    public int indicatorPosition;
    public final boolean inlineLabel;
    public final ColorDrawable mBackgroundColorDrawable;
    public final Typeface mBoldTypeface;
    public final ContentResolver mContentResolver;
    public int mCurrentTouchSlop;
    public final int mDefaultTouchSlop;
    public int mDepthStyle;
    public final int mFirstTabGravity;
    public final int mIconTextGap;
    public boolean mIsOverScreen;
    public final boolean mIsScaledTextSizeType;
    public int mMaxTouchSlop;
    public final Typeface mNormalTypeface;
    public int mOverScreenMaxWidth;
    public final int mRequestedTabWidth;
    public final int mSubTabIndicator2ndHeight;
    public final int mSubTabIndicatorHeight;
    public int mSubTabSelectedIndicatorColor;
    public final int mSubTabSubTextAppearance;
    public final ColorStateList mSubTabSubTextColors;
    public final int mSubTabTextSize;
    public final int mTabMinSideSpace;
    public int mTabSelectedIndicatorColor;
    public final int mode;
    public TabLayoutOnPageChangeListener pageChangeListener;
    public PagerAdapter pagerAdapter;
    public PagerAdapterObserver pagerAdapterObserver;
    public final int requestedTabMaxWidth;
    public final int requestedTabMinWidth;
    public ValueAnimator scrollAnimator;
    public final ArrayList selectedListeners;
    public Tab selectedTab;
    public final int selectedTabTextAppearance;
    public boolean setupViewPagerImplicitly;
    public final SlidingTabIndicator slidingTabIndicator;
    public final int tabBackgroundResId;
    public int tabGravity;
    public final ColorStateList tabIconTint;
    public final PorterDuff.Mode tabIconTintMode;
    public final int tabIndicatorAnimationDuration;
    public final boolean tabIndicatorFullWidth;
    public final int tabIndicatorGravity;
    public final TabIndicatorInterpolator tabIndicatorInterpolator;
    public final TimeInterpolator tabIndicatorTimeInterpolator;
    public int tabMaxWidth;
    public final int tabPaddingBottom;
    public final int tabPaddingTop;
    public final Drawable tabSelectedIndicator;
    public final int tabTextAppearance;
    public ColorStateList tabTextColors;
    public final float tabTextMultiLineSize;
    public final float tabTextSize;
    public final Pools$SimplePool tabViewPool;
    public final ArrayList tabs;
    public ViewPager viewPager;
    public int viewPagerScrollState;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AdapterChangeListener {
        public boolean autoRefresh;

        public AdapterChangeListener() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnTabSelectedListener {
        void onTabSelected(Tab tab);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PagerAdapterObserver extends DataSetObserver {
        public PagerAdapterObserver() {
        }

        @Override // android.database.DataSetObserver
        public final void onChanged() {
            TabLayout.this.populateFromPagerAdapter();
        }

        @Override // android.database.DataSetObserver
        public final void onInvalidated() {
            TabLayout.this.populateFromPagerAdapter();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SlidingTabIndicator extends LinearLayout {
        public SlidingTabIndicator(Context context) {
            super(context);
            setWillNotDraw(false);
        }

        @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
        public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
        }

        @Override // android.widget.LinearLayout, android.view.View
        public final void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            if (View.MeasureSpec.getMode(i) != 1073741824) {
                return;
            }
            TabLayout tabLayout = TabLayout.this;
            int i3 = tabLayout.mode;
            boolean z = true;
            int i4 = 0;
            if (i3 == 11 || i3 == 12) {
                tabLayout.checkOverScreen();
                TabLayout tabLayout2 = TabLayout.this;
                int size = tabLayout2.mIsOverScreen ? tabLayout2.mOverScreenMaxWidth : View.MeasureSpec.getSize(i);
                int childCount = getChildCount();
                int[] iArr = new int[childCount];
                int i5 = 0;
                for (int i6 = 0; i6 < childCount; i6++) {
                    View childAt = getChildAt(i6);
                    if (childAt.getVisibility() == 0) {
                        childAt.measure(View.MeasureSpec.makeMeasureSpec(TabLayout.this.tabMaxWidth, 0), i2);
                        int measuredWidth = (TabLayout.this.mTabMinSideSpace * 2) + childAt.getMeasuredWidth();
                        iArr[i6] = measuredWidth;
                        i5 += measuredWidth;
                    }
                }
                int i7 = size / childCount;
                if (i5 > size) {
                    while (i4 < childCount) {
                        ((LinearLayout.LayoutParams) getChildAt(i4).getLayoutParams()).width = iArr[i4];
                        i4++;
                    }
                } else {
                    if (TabLayout.this.mode == 11) {
                        int i8 = 0;
                        while (true) {
                            if (i8 >= childCount) {
                                z = false;
                                break;
                            } else if (iArr[i8] > i7) {
                                break;
                            } else {
                                i8++;
                            }
                        }
                    }
                    if (z) {
                        int i9 = (size - i5) / childCount;
                        while (i4 < childCount) {
                            ((LinearLayout.LayoutParams) getChildAt(i4).getLayoutParams()).width = iArr[i4] + i9;
                            i4++;
                        }
                    } else {
                        while (i4 < childCount) {
                            ((LinearLayout.LayoutParams) getChildAt(i4).getLayoutParams()).width = i7;
                            i4++;
                        }
                    }
                }
                if (i5 > size) {
                    size = i5;
                }
                super.onMeasure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), i2);
                return;
            }
            if (tabLayout.tabGravity == 1 || i3 == 2 || tabLayout.mFirstTabGravity == 1) {
                int childCount2 = getChildCount();
                TabLayout tabLayout3 = TabLayout.this;
                if (tabLayout3.tabGravity == 0 && tabLayout3.mFirstTabGravity == 1) {
                    for (int i10 = 0; i10 < childCount2; i10++) {
                        View childAt2 = getChildAt(i10);
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt2.getLayoutParams();
                        layoutParams.width = -2;
                        layoutParams.weight = 0.0f;
                        childAt2.measure(View.MeasureSpec.makeMeasureSpec(0, 0), i2);
                    }
                }
                int i11 = 0;
                for (int i12 = 0; i12 < childCount2; i12++) {
                    View childAt3 = getChildAt(i12);
                    if (childAt3.getVisibility() == 0) {
                        i11 = Math.max(i11, childAt3.getMeasuredWidth());
                    }
                }
                if (i11 <= 0) {
                    return;
                }
                if (i11 * childCount2 <= getMeasuredWidth() - (((int) ViewUtils.dpToPx(16, getContext())) * 2)) {
                    boolean z2 = false;
                    while (i4 < childCount2) {
                        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) getChildAt(i4).getLayoutParams();
                        if (layoutParams2.width != i11 || layoutParams2.weight != 0.0f) {
                            layoutParams2.width = i11;
                            layoutParams2.weight = 0.0f;
                            z2 = true;
                        }
                        i4++;
                    }
                    TabLayout tabLayout4 = TabLayout.this;
                    if (tabLayout4.tabGravity == 0 && tabLayout4.mFirstTabGravity == 1) {
                        tabLayout4.tabGravity = 1;
                    }
                    z = z2;
                } else {
                    TabLayout tabLayout5 = TabLayout.this;
                    tabLayout5.tabGravity = 0;
                    tabLayout5.updateTabViews(false);
                }
                if (z) {
                    super.onMeasure(i, i2);
                }
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Tab {
        public CharSequence contentDesc;
        public View customView;
        public Drawable icon;
        public TabLayout parent;
        public int position = -1;
        public CharSequence text;
        public TabView view;

        public final void select() {
            TabLayout tabLayout = this.parent;
            if (tabLayout == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            tabLayout.selectTab(this, true);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
        public int previousScrollState;
        public int scrollState;
        public final WeakReference tabLayoutRef;

        public TabLayoutOnPageChangeListener(TabLayout tabLayout) {
            this.tabLayoutRef = new WeakReference(tabLayout);
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public final void onPageScrollStateChanged(int i) {
            this.previousScrollState = this.scrollState;
            this.scrollState = i;
            TabLayout tabLayout = (TabLayout) this.tabLayoutRef.get();
            if (tabLayout != null) {
                tabLayout.viewPagerScrollState = this.scrollState;
            }
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public final void onPageScrolled(float f, int i) {
            boolean z;
            TabLayout tabLayout = (TabLayout) this.tabLayoutRef.get();
            if (tabLayout != null) {
                int i2 = this.scrollState;
                boolean z2 = true;
                if (i2 != 2 || this.previousScrollState == 1) {
                    z = true;
                } else {
                    z = true;
                    z2 = false;
                }
                if (i2 == 2 && this.previousScrollState == 0) {
                    z = false;
                }
                tabLayout.setScrollPosition(i, f, z2, z, false);
            }
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public final void onPageSelected(int i) {
            TabLayout tabLayout = (TabLayout) this.tabLayoutRef.get();
            if (tabLayout == null || tabLayout.getSelectedTabPosition() == i || i >= tabLayout.tabs.size()) {
                return;
            }
            int i2 = this.scrollState;
            tabLayout.selectTab(tabLayout.getTabAt(i), i2 == 0 || (i2 == 2 && this.previousScrollState == 0));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TabView extends LinearLayout {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final Drawable baseBackgroundDrawable;
        public ImageView customIconView;
        public TextView customTextView;
        public View customView;
        public int defaultMaxLines;
        public ImageView iconView;
        public int mIconSize;
        public SeslAbsIndicatorView mIndicatorView;
        public boolean mIsCallPerformClick;
        public View mMainTabTouchBackground;
        public TextView mSubTextView;
        public ConstraintLayout mTabParentView;
        public final AnonymousClass1 mTabViewKeyListener;
        public Tab tab;
        public TextView textView;

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v0, types: [android.view.View$OnKeyListener, com.google.android.material.tabs.TabLayout$TabView$1] */
        public TabView(Context context) {
            super(context);
            this.defaultMaxLines = 2;
            ?? r1 = new View.OnKeyListener(this) { // from class: com.google.android.material.tabs.TabLayout.TabView.1
                @Override // android.view.View.OnKeyListener
                public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                    return false;
                }
            };
            this.mTabViewKeyListener = r1;
            int i = TabLayout.this.tabBackgroundResId;
            if (i == 0 || TabLayout.this.mDepthStyle == 2) {
                this.baseBackgroundDrawable = null;
            } else {
                Drawable drawable = AppCompatResources.getDrawable(i, context);
                this.baseBackgroundDrawable = drawable;
                if (drawable != null && drawable.isStateful()) {
                    this.baseBackgroundDrawable.setState(getDrawableState());
                }
                Drawable drawable2 = this.baseBackgroundDrawable;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                setBackground(drawable2);
            }
            setGravity(17);
            setOrientation(!TabLayout.this.inlineLabel ? 1 : 0);
            setClickable(true);
            setOnKeyListener(r1);
            if (TabLayout.this.mDepthStyle == 1) {
                int i2 = TabLayout.this.tabPaddingTop;
                int i3 = TabLayout.this.tabPaddingBottom;
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                setPaddingRelative(0, i2, 0, i3);
            }
            this.mIconSize = getResources().getDimensionPixelOffset(R.dimen.sesl_tab_icon_size);
        }

        @Override // android.view.ViewGroup, android.view.View
        public final void drawableStateChanged() {
            super.drawableStateChanged();
        }

        @Override // android.view.View
        public final void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            this.mIconSize = getResources().getDimensionPixelOffset(R.dimen.sesl_tab_icon_size);
        }

        @Override // android.widget.LinearLayout, android.view.View
        public final void onDraw(Canvas canvas) {
            Drawable drawable = this.baseBackgroundDrawable;
            if (drawable != null) {
                View view = this.mMainTabTouchBackground;
                if (view != null) {
                    drawable.setBounds(getPaddingStart() + view.getLeft(), getPaddingTop() + this.mMainTabTouchBackground.getTop(), getPaddingStart() + this.mMainTabTouchBackground.getRight(), getPaddingTop() + this.mMainTabTouchBackground.getBottom());
                } else {
                    drawable.setBounds(getLeft(), getTop(), getRight(), getBottom());
                }
            }
            super.onDraw(canvas);
        }

        @Override // android.view.View
        public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            AccessibilityNodeInfoCompat wrap = AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo);
            wrap.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(isSelected(), 0, 1, this.tab.position, 1));
            if (isSelected()) {
                wrap.setClickable(false);
                wrap.mInfo.removeAction((AccessibilityNodeInfo.AccessibilityAction) AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK.mAction);
            }
            wrap.mInfo.getExtras().putCharSequence("AccessibilityNodeInfo.roleDescription", getResources().getString(R.string.item_view_role_description));
        }

        @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
        public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
            TextView textView;
            super.onLayout(z, i, i2, i3, i4);
            View view = this.mMainTabTouchBackground;
            if (view != null) {
                view.setLeft(0);
                View view2 = this.mMainTabTouchBackground;
                ConstraintLayout constraintLayout = this.mTabParentView;
                view2.setRight(constraintLayout != null ? constraintLayout.getWidth() : i3 - i);
                if (this.mMainTabTouchBackground.getAnimation() != null && this.mMainTabTouchBackground.getAnimation().hasEnded()) {
                    this.mMainTabTouchBackground.setAlpha(0.0f);
                }
            }
            if (this.iconView == null || this.tab.icon == null || (textView = this.textView) == null || this.mIndicatorView == null || this.mTabParentView == null) {
                return;
            }
            int measuredWidth = textView.getMeasuredWidth() + this.mIconSize;
            int i5 = TabLayout.this.mIconTextGap;
            if (i5 != -1) {
                measuredWidth += i5;
            }
            int abs = Math.abs((getWidth() - measuredWidth) / 2);
            if (!ViewUtils.isLayoutRtl(this)) {
                if (this.iconView.getLeft() == this.mTabParentView.getLeft()) {
                    this.textView.offsetLeftAndRight(abs);
                    this.iconView.offsetLeftAndRight(abs);
                    this.mIndicatorView.offsetLeftAndRight(abs);
                    return;
                }
                return;
            }
            int i6 = -abs;
            if (this.iconView.getRight() == this.mTabParentView.getRight()) {
                this.textView.offsetLeftAndRight(i6);
                this.iconView.offsetLeftAndRight(i6);
                this.mIndicatorView.offsetLeftAndRight(i6);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:33:0x00cf, code lost:
        
            if (((r4 / r2.getPaint().getTextSize()) * r2.getLineWidth(0)) > ((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight())) goto L56;
         */
        @Override // android.widget.LinearLayout, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onMeasure(int r11, int r12) {
            /*
                Method dump skipped, instructions count: 327
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.tabs.TabLayout.TabView.onMeasure(int, int):void");
        }

        @Override // android.view.View
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            SeslAbsIndicatorView seslAbsIndicatorView;
            TextView textView;
            if (isEnabled()) {
                TabLayout tabLayout = TabLayout.this;
                Pools$SynchronizedPool pools$SynchronizedPool = TabLayout.tabPool;
                int i = tabLayout.mode;
                if (i != 0 && i != 2) {
                    View view = this.tab.customView;
                    if (view != null) {
                        return super.onTouchEvent(motionEvent);
                    }
                    if (motionEvent == null || view != null || this.textView == null) {
                        return false;
                    }
                    int action = motionEvent.getAction() & 255;
                    float rawX = motionEvent.getRawX();
                    float rawY = motionEvent.getRawY();
                    if (action == 0) {
                        this.mIsCallPerformClick = false;
                        if (this.tab.position != TabLayout.this.getSelectedTabPosition() && (textView = this.textView) != null) {
                            textView.setTypeface(TabLayout.this.mBoldTypeface);
                            TabLayout tabLayout2 = TabLayout.this;
                            TextView textView2 = this.textView;
                            ColorStateList colorStateList = tabLayout2.tabTextColors;
                            int colorForState = colorStateList != null ? colorStateList.getColorForState(new int[]{android.R.attr.state_selected, android.R.attr.state_enabled}, colorStateList.getDefaultColor()) : -1;
                            if (textView2 != null) {
                                textView2.setTextColor(colorForState);
                            }
                            ImageView imageView = this.iconView;
                            if (imageView != null) {
                                imageView.setSelected(true);
                            }
                            SeslAbsIndicatorView seslAbsIndicatorView2 = this.mIndicatorView;
                            if (seslAbsIndicatorView2 != null) {
                                seslAbsIndicatorView2.startPressEffect();
                            }
                            TabLayout tabLayout3 = TabLayout.this;
                            Tab tabAt = tabLayout3.getTabAt(tabLayout3.getSelectedTabPosition());
                            if (tabAt != null) {
                                TextView textView3 = tabAt.view.textView;
                                if (textView3 != null) {
                                    textView3.setTypeface(TabLayout.this.mNormalTypeface);
                                    TabLayout tabLayout4 = TabLayout.this;
                                    TextView textView4 = tabAt.view.textView;
                                    int defaultColor = tabLayout4.tabTextColors.getDefaultColor();
                                    if (textView4 != null) {
                                        textView4.setTextColor(defaultColor);
                                    }
                                }
                                ImageView imageView2 = tabAt.view.iconView;
                                if (imageView2 != null) {
                                    imageView2.setSelected(false);
                                }
                                SeslAbsIndicatorView seslAbsIndicatorView3 = tabAt.view.mIndicatorView;
                                if (seslAbsIndicatorView3 != null) {
                                    seslAbsIndicatorView3.onHide();
                                }
                            }
                        } else if (this.tab.position == TabLayout.this.getSelectedTabPosition() && (seslAbsIndicatorView = this.mIndicatorView) != null) {
                            seslAbsIndicatorView.startPressEffect();
                        }
                    } else if (action != 1) {
                        if (action != 2) {
                            if (action == 3) {
                                TabLayout.access$3800(TabLayout.this, this.textView, this.iconView, this.mIndicatorView);
                            }
                        } else if (!TabLayout.access$3700(TabLayout.this, this, (int) rawX, (int) rawY)) {
                            TabLayout.access$3800(TabLayout.this, this.textView, this.iconView, this.mIndicatorView);
                        }
                    } else if (TabLayout.access$3700(TabLayout.this, this, (int) rawX, (int) rawY)) {
                        SeslAbsIndicatorView seslAbsIndicatorView4 = this.mIndicatorView;
                        if (seslAbsIndicatorView4 != null) {
                            seslAbsIndicatorView4.startReleaseEffect();
                            this.mIndicatorView.onTouchEvent(motionEvent);
                        }
                        performClick();
                        this.mIsCallPerformClick = true;
                    }
                    return super.onTouchEvent(motionEvent);
                }
            }
            return super.onTouchEvent(motionEvent);
        }

        @Override // android.view.View
        public final boolean performClick() {
            if (this.mIsCallPerformClick) {
                this.mIsCallPerformClick = false;
                return true;
            }
            boolean performClick = super.performClick();
            if (this.tab == null) {
                return performClick;
            }
            if (!performClick) {
                playSoundEffect(0);
            }
            this.tab.select();
            return true;
        }

        @Override // android.view.View
        public final void setEnabled(boolean z) {
            super.setEnabled(z);
            View view = this.mMainTabTouchBackground;
            if (view != null) {
                view.setVisibility(z ? 0 : 8);
            }
        }

        @Override // android.view.View
        public final void setSelected(boolean z) {
            if (isEnabled()) {
                isSelected();
                super.setSelected(z);
                TextView textView = this.textView;
                if (textView != null) {
                    textView.setSelected(z);
                }
                ImageView imageView = this.iconView;
                if (imageView != null) {
                    imageView.setSelected(z);
                }
                View view = this.customView;
                if (view != null) {
                    view.setSelected(z);
                }
                SeslAbsIndicatorView seslAbsIndicatorView = this.mIndicatorView;
                if (seslAbsIndicatorView != null) {
                    seslAbsIndicatorView.setSelected(z);
                    if (!TextUtils.isEmpty(null)) {
                        SeslAbsIndicatorView seslAbsIndicatorView2 = this.mIndicatorView;
                        Drawable drawable = getContext().getDrawable(SeslMisc.isLightTheme(getContext()) ? R.drawable.sesl_tablayout_subtab_subtext_indicator_background_light : R.drawable.sesl_tablayout_subtab_subtext_indicator_background_dark);
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        seslAbsIndicatorView2.setBackground(drawable);
                    }
                }
                TextView textView2 = this.mSubTextView;
                if (textView2 != null) {
                    textView2.setSelected(z);
                }
            }
        }

        public final void update() {
            boolean z;
            updateTab();
            Tab tab = this.tab;
            if (tab != null) {
                TabLayout tabLayout = tab.parent;
                if (tabLayout == null) {
                    throw new IllegalArgumentException("Tab not attached to a TabLayout");
                }
                int selectedTabPosition = tabLayout.getSelectedTabPosition();
                if (selectedTabPosition != -1 && selectedTabPosition == tab.position) {
                    z = true;
                    setSelected(z);
                }
            }
            z = false;
            setSelected(z);
        }

        public final void updateTab() {
            int i;
            ConstraintLayout constraintLayout;
            int i2;
            int i3;
            ViewParent parent;
            Tab tab = this.tab;
            View view = tab != null ? tab.customView : null;
            if (view != null) {
                ViewParent parent2 = view.getParent();
                if (parent2 != this) {
                    if (parent2 != null) {
                        ((ViewGroup) parent2).removeView(view);
                    }
                    View view2 = this.customView;
                    if (view2 != null && (parent = view2.getParent()) != null) {
                        ((ViewGroup) parent).removeView(this.customView);
                    }
                    addView(view);
                }
                this.customView = view;
                TextView textView = this.textView;
                if (textView != null) {
                    textView.setVisibility(8);
                }
                ImageView imageView = this.iconView;
                if (imageView != null) {
                    imageView.setVisibility(8);
                    this.iconView.setImageDrawable(null);
                }
                TextView textView2 = this.mSubTextView;
                if (textView2 != null) {
                    textView2.setVisibility(8);
                }
                TextView textView3 = (TextView) view.findViewById(android.R.id.text1);
                this.customTextView = textView3;
                if (textView3 != null) {
                    this.defaultMaxLines = textView3.getMaxLines();
                }
                this.customIconView = (ImageView) view.findViewById(android.R.id.icon);
            } else {
                View view3 = this.customView;
                if (view3 != null) {
                    removeView(view3);
                    this.customView = null;
                }
                this.customTextView = null;
                this.customIconView = null;
            }
            boolean z = false;
            if (this.customView != null || this.tab == null) {
                TextView textView4 = this.customTextView;
                if (textView4 != null || this.customIconView != null) {
                    updateTextAndIcon(textView4, this.customIconView, false);
                }
            } else {
                if (this.mTabParentView == null) {
                    if (TabLayout.this.mDepthStyle == 2) {
                        this.mTabParentView = (ConstraintLayout) LayoutInflater.from(getContext()).inflate(R.layout.sesl_tabs_sub_tab_layout, (ViewGroup) this, false);
                    } else {
                        ConstraintLayout constraintLayout2 = (ConstraintLayout) LayoutInflater.from(getContext()).inflate(R.layout.sesl_tabs_main_tab_layout, (ViewGroup) this, false);
                        this.mTabParentView = constraintLayout2;
                        View findViewById = constraintLayout2.findViewById(R.id.main_tab_touch_background);
                        this.mMainTabTouchBackground = findViewById;
                        if (findViewById != null && this.tab.icon == null) {
                            Drawable drawable = getContext().getDrawable(SeslMisc.isLightTheme(getContext()) ? R.drawable.sesl_tablayout_maintab_touch_background_light : R.drawable.sesl_tablayout_maintab_touch_background_dark);
                            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                            findViewById.setBackground(drawable);
                            this.mMainTabTouchBackground.setAlpha(0.0f);
                        }
                    }
                }
                if (this.mIndicatorView == null) {
                    this.mIndicatorView = (SeslAbsIndicatorView) this.mTabParentView.findViewById(R.id.indicator);
                }
                TabLayout tabLayout = TabLayout.this;
                if (tabLayout.mDepthStyle == 2) {
                    SeslAbsIndicatorView seslAbsIndicatorView = this.mIndicatorView;
                    if (seslAbsIndicatorView != null && (i3 = tabLayout.mSubTabSelectedIndicatorColor) != -1) {
                        seslAbsIndicatorView.onSetSelectedIndicatorColor(i3);
                    }
                } else {
                    SeslAbsIndicatorView seslAbsIndicatorView2 = this.mIndicatorView;
                    if (seslAbsIndicatorView2 != null) {
                        seslAbsIndicatorView2.onSetSelectedIndicatorColor(tabLayout.mTabSelectedIndicatorColor);
                    }
                }
                if (this.textView == null) {
                    this.textView = (TextView) this.mTabParentView.findViewById(R.id.title);
                }
                this.defaultMaxLines = this.textView.getMaxLines();
                this.textView.setTextAppearance(TabLayout.this.defaultTabTextAppearance);
                if (!isSelected() || (i2 = TabLayout.this.selectedTabTextAppearance) == -1) {
                    this.textView.setTextAppearance(TabLayout.this.tabTextAppearance);
                } else {
                    this.textView.setTextAppearance(i2);
                }
                if (isSelected()) {
                    this.textView.setTypeface(TabLayout.this.mBoldTypeface);
                } else {
                    this.textView.setTypeface(TabLayout.this.mNormalTypeface);
                }
                TabLayout tabLayout2 = TabLayout.this;
                TabLayout.access$1900(tabLayout2, this.textView, (int) tabLayout2.tabTextSize);
                this.textView.setTextColor(TabLayout.this.tabTextColors);
                if (TabLayout.this.mDepthStyle == 2) {
                    if (this.mSubTextView == null) {
                        this.mSubTextView = (TextView) this.mTabParentView.findViewById(R.id.sub_title);
                    }
                    TextView textView5 = this.mSubTextView;
                    if (textView5 != null) {
                        textView5.setTextAppearance(TabLayout.this.mSubTabSubTextAppearance);
                        this.mSubTextView.setTextColor(TabLayout.this.mSubTabSubTextColors);
                    }
                    TextView textView6 = this.mSubTextView;
                    if (textView6 != null) {
                        TabLayout tabLayout3 = TabLayout.this;
                        TabLayout.access$1900(tabLayout3, textView6, tabLayout3.mSubTabTextSize);
                    }
                }
                if (this.iconView == null && (constraintLayout = this.mTabParentView) != null) {
                    this.iconView = (ImageView) constraintLayout.findViewById(R.id.icon);
                }
                TextView textView7 = this.textView;
                TextView textView8 = this.mSubTextView;
                updateTextAndIcon(textView7, this.iconView, true);
                if (textView8 != null) {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView7.getLayoutParams();
                    boolean isEmpty = TextUtils.isEmpty(null);
                    layoutParams.topToTop = !isEmpty ? -1 : 0;
                    layoutParams.bottomToBottom = !isEmpty ? -1 : 0;
                    layoutParams.bottomToTop = !isEmpty ? R.id.center_anchor : -1;
                    textView8.setText((CharSequence) null);
                    if (isEmpty) {
                        textView8.setVisibility(8);
                    } else {
                        this.tab.getClass();
                        textView8.setVisibility(0);
                        setVisibility(0);
                    }
                }
                TabLayout tabLayout4 = TabLayout.this;
                if (tabLayout4.mDepthStyle == 2) {
                    r7 = tabLayout4.mode == 0 ? -2 : -1;
                    i = !TextUtils.isEmpty(null) ? TabLayout.this.mSubTabIndicator2ndHeight : TabLayout.this.mSubTabIndicatorHeight;
                    ConstraintLayout constraintLayout3 = this.mTabParentView;
                    if (constraintLayout3 != null && constraintLayout3.getHeight() != i) {
                        z = true;
                    }
                } else if (this.tab.icon != null) {
                    i = -1;
                    r7 = -2;
                } else {
                    i = -1;
                }
                ConstraintLayout constraintLayout4 = this.mTabParentView;
                if (constraintLayout4 != null && constraintLayout4.getParent() == null) {
                    addView(this.mTabParentView, r7, i);
                } else if (z) {
                    removeView(this.mTabParentView);
                    addView(this.mTabParentView, r7, i);
                }
                final ImageView imageView2 = this.iconView;
                if (imageView2 != null) {
                    imageView2.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.google.android.material.tabs.TabLayout.TabView.2
                        @Override // android.view.View.OnLayoutChangeListener
                        public final void onLayoutChange(View view4, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
                            if (imageView2.getVisibility() == 0) {
                                TabView tabView = TabView.this;
                                int i12 = TabView.$r8$clinit;
                                tabView.getClass();
                            }
                        }
                    });
                }
                final TextView textView9 = this.textView;
                if (textView9 != null) {
                    textView9.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.google.android.material.tabs.TabLayout.TabView.2
                        @Override // android.view.View.OnLayoutChangeListener
                        public final void onLayoutChange(View view4, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
                            if (textView9.getVisibility() == 0) {
                                TabView tabView = TabView.this;
                                int i12 = TabView.$r8$clinit;
                                tabView.getClass();
                            }
                        }
                    });
                }
            }
            if (tab == null || TextUtils.isEmpty(tab.contentDesc)) {
                return;
            }
            setContentDescription(tab.contentDesc);
        }

        public final void updateTextAndIcon(TextView textView, ImageView imageView, boolean z) {
            boolean z2;
            Drawable drawable;
            Tab tab = this.tab;
            Drawable mutate = (tab == null || (drawable = tab.icon) == null) ? null : drawable.mutate();
            if (mutate != null) {
                TabLayout tabLayout = TabLayout.this;
                ColorStateList colorStateList = tabLayout.tabIconTint;
                if (colorStateList == null) {
                    mutate.setTintList(tabLayout.tabTextColors);
                } else {
                    mutate.setTintList(colorStateList);
                }
                PorterDuff.Mode mode = TabLayout.this.tabIconTintMode;
                if (mode != null) {
                    mutate.setTintMode(mode);
                }
            }
            Tab tab2 = this.tab;
            CharSequence charSequence = tab2 != null ? tab2.text : null;
            boolean z3 = false;
            if (imageView != null) {
                if (mutate != null) {
                    imageView.setImageDrawable(mutate);
                    imageView.setVisibility(0);
                    setVisibility(0);
                } else {
                    imageView.setVisibility(8);
                    imageView.setImageDrawable(null);
                }
            }
            boolean isEmpty = TextUtils.isEmpty(charSequence);
            if (textView != null) {
                if (isEmpty) {
                    z2 = false;
                } else {
                    this.tab.getClass();
                    z2 = true;
                }
                if (isEmpty) {
                    charSequence = null;
                }
                textView.setText(charSequence);
                textView.setVisibility(z2 ? 0 : 8);
                if (!isEmpty) {
                    setVisibility(0);
                }
                z3 = z2;
            }
            if (z && imageView != null) {
                if (z3 && imageView.getVisibility() == 0 && TabLayout.this.mIconTextGap == -1) {
                    ViewUtils.dpToPx(8, getContext());
                }
            }
            Tab tab3 = this.tab;
            setTooltipText(isEmpty ? tab3 != null ? tab3.contentDesc : null : null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ViewPagerOnTabSelectedListener implements OnTabSelectedListener {
        public final ViewPager viewPager;

        public ViewPagerOnTabSelectedListener(ViewPager viewPager) {
            this.viewPager = viewPager;
        }

        @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
        public final void onTabSelected(Tab tab) {
            this.viewPager.setCurrentItem(tab.position);
        }
    }

    public TabLayout(Context context) {
        this(context, null);
    }

    public static void access$1900(TabLayout tabLayout, TextView textView, int i) {
        float f = tabLayout.getResources().getConfiguration().fontScale;
        if (textView == null || !tabLayout.mIsScaledTextSizeType || f <= 1.3f) {
            return;
        }
        textView.setTextSize(0, (i / f) * 1.3f);
    }

    public static boolean access$3700(TabLayout tabLayout, TabView tabView, int i, int i2) {
        tabLayout.getClass();
        Rect rect = new Rect();
        int[] iArr = new int[2];
        tabView.getDrawingRect(rect);
        tabView.getLocationOnScreen(iArr);
        rect.offset(iArr[0], iArr[1]);
        return rect.contains(i, i2);
    }

    public static void access$3800(TabLayout tabLayout, TextView textView, ImageView imageView, SeslAbsIndicatorView seslAbsIndicatorView) {
        textView.setTypeface(tabLayout.mNormalTypeface);
        textView.setTextColor(tabLayout.tabTextColors.getDefaultColor());
        if (imageView != null) {
            imageView.setSelected(false);
        }
        if (seslAbsIndicatorView != null && !seslAbsIndicatorView.isSelected()) {
            seslAbsIndicatorView.onHide();
        }
        Tab tabAt = tabLayout.getTabAt(tabLayout.getSelectedTabPosition());
        if (tabAt != null) {
            TextView textView2 = tabAt.view.textView;
            if (textView2 != null) {
                textView2.setTypeface(tabLayout.mBoldTypeface);
                TextView textView3 = tabAt.view.textView;
                ColorStateList colorStateList = tabLayout.tabTextColors;
                int colorForState = colorStateList != null ? colorStateList.getColorForState(new int[]{android.R.attr.state_selected, android.R.attr.state_enabled}, colorStateList.getDefaultColor()) : -1;
                if (textView3 != null) {
                    textView3.setTextColor(colorForState);
                }
            }
            ImageView imageView2 = tabAt.view.iconView;
            if (imageView2 != null) {
                imageView2.setSelected(true);
            }
            SeslAbsIndicatorView seslAbsIndicatorView2 = tabAt.view.mIndicatorView;
            if (seslAbsIndicatorView2 != null) {
                seslAbsIndicatorView2.onShow();
            }
        }
        if (tabLayout.mDepthStyle == 1 || seslAbsIndicatorView == null || !seslAbsIndicatorView.isSelected()) {
            return;
        }
        seslAbsIndicatorView.startReleaseEffect();
    }

    public static ColorStateList createColorStateList(int i, int i2) {
        return new ColorStateList(new int[][]{HorizontalScrollView.SELECTED_STATE_SET, HorizontalScrollView.EMPTY_STATE_SET}, new int[]{i2, i});
    }

    public final void addOnTabSelectedListener$1(OnTabSelectedListener onTabSelectedListener) {
        if (this.selectedListeners.contains(onTabSelectedListener)) {
            return;
        }
        this.selectedListeners.add(onTabSelectedListener);
    }

    public final void addTab(Tab tab, boolean z) {
        int size = this.tabs.size();
        if (tab.parent != this) {
            throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
        }
        tab.position = size;
        this.tabs.add(size, tab);
        int size2 = this.tabs.size();
        int i = -1;
        for (int i2 = size + 1; i2 < size2; i2++) {
            if (((Tab) this.tabs.get(i2)).position == this.indicatorPosition) {
                i = i2;
            }
            ((Tab) this.tabs.get(i2)).position = i2;
        }
        this.indicatorPosition = i;
        final TabView tabView = tab.view;
        tabView.setSelected(false);
        tabView.setActivated(false);
        SlidingTabIndicator slidingTabIndicator = this.slidingTabIndicator;
        int i3 = tab.position;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
        updateTabViewLayoutParams(layoutParams);
        slidingTabIndicator.addView(tabView, i3, layoutParams);
        tabView.post(new Runnable() { // from class: com.google.android.material.tabs.TabLayout$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                TabLayout tabLayout = TabLayout.this;
                TabLayout.TabView tabView2 = tabView;
                Pools$SynchronizedPool pools$SynchronizedPool = TabLayout.tabPool;
                tabView2.setStateListAnimator(AnimatorInflater.loadStateListAnimator(tabLayout.getContext(), R.animator.sesl_recoil_button_selector));
                tabView2.getStateListAnimator().jumpToCurrentState();
            }
        });
        if (z) {
            tab.select();
        }
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public final void addView(View view) {
        addViewInternal(view);
    }

    public final void addViewInternal(View view) {
        if (!(view instanceof TabItem)) {
            throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
        }
        TabItem tabItem = (TabItem) view;
        Tab newTab = newTab();
        CharSequence charSequence = tabItem.text;
        if (charSequence != null) {
            if (TextUtils.isEmpty(newTab.contentDesc) && !TextUtils.isEmpty(charSequence)) {
                newTab.view.setContentDescription(charSequence);
            }
            newTab.text = charSequence;
            TabView tabView = newTab.view;
            if (tabView != null) {
                tabView.update();
            }
        }
        Drawable drawable = tabItem.icon;
        if (drawable != null) {
            newTab.icon = drawable;
            TabLayout tabLayout = newTab.parent;
            if (tabLayout.tabGravity == 1 || tabLayout.mode == 2) {
                tabLayout.updateTabViews(true);
            }
            TabView tabView2 = newTab.view;
            if (tabView2 != null) {
                tabView2.update();
            }
        }
        int i = tabItem.customLayout;
        if (i != 0) {
            View inflate = LayoutInflater.from(newTab.view.getContext()).inflate(i, (ViewGroup) newTab.view, false);
            TabView tabView3 = newTab.view;
            if (tabView3.textView != null) {
                tabView3.removeAllViews();
            }
            newTab.customView = inflate;
            TabView tabView4 = newTab.view;
            if (tabView4 != null) {
                tabView4.update();
            }
        }
        if (!TextUtils.isEmpty(tabItem.getContentDescription())) {
            newTab.contentDesc = tabItem.getContentDescription();
            TabView tabView5 = newTab.view;
            if (tabView5 != null) {
                tabView5.update();
            }
        }
        addTab(newTab, this.tabs.isEmpty());
    }

    public final void animateToTab(int i) {
        if (i == -1) {
            return;
        }
        if (getWindowToken() != null) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (isLaidOut()) {
                SlidingTabIndicator slidingTabIndicator = this.slidingTabIndicator;
                int childCount = slidingTabIndicator.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    if (slidingTabIndicator.getChildAt(i2).getWidth() > 0) {
                    }
                }
                int scrollX = getScrollX();
                int calculateScrollXForTab = calculateScrollXForTab(0.0f, i);
                if (scrollX != calculateScrollXForTab) {
                    if (this.scrollAnimator == null) {
                        ValueAnimator valueAnimator = new ValueAnimator();
                        this.scrollAnimator = valueAnimator;
                        valueAnimator.setInterpolator(this.tabIndicatorTimeInterpolator);
                        this.scrollAnimator.setDuration(this.tabIndicatorAnimationDuration);
                        this.scrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.tabs.TabLayout.1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                                TabLayout.this.scrollTo(((Integer) valueAnimator2.getAnimatedValue()).intValue(), 0);
                            }
                        });
                    }
                    this.scrollAnimator.setIntValues(scrollX, calculateScrollXForTab);
                    this.scrollAnimator.start();
                }
                this.slidingTabIndicator.getClass();
                return;
            }
        }
        setScrollPosition(i, 0.0f, true, true, true);
    }

    public final int calculateScrollXForTab(float f, int i) {
        View childAt;
        int i2 = this.mode;
        if ((i2 != 0 && i2 != 2 && i2 != 11 && i2 != 12) || (childAt = this.slidingTabIndicator.getChildAt(i)) == null) {
            return 0;
        }
        int i3 = i + 1;
        View childAt2 = i3 < this.slidingTabIndicator.getChildCount() ? this.slidingTabIndicator.getChildAt(i3) : null;
        int width = childAt.getWidth();
        int width2 = childAt2 != null ? childAt2.getWidth() : 0;
        int left = ((width / 2) + childAt.getLeft()) - (getWidth() / 2);
        int i4 = (int) ((width + width2) * 0.5f * f);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        return getLayoutDirection() == 0 ? left + i4 : left - i4;
    }

    public final void checkOverScreen() {
        int measuredWidth = getMeasuredWidth();
        if (measuredWidth <= ((int) ((getContext().getResources().getDisplayMetrics().densityDpi / 160.0f) * getResources().getInteger(R.integer.sesl_tablayout_over_screen_width_dp)))) {
            this.mIsOverScreen = false;
        } else {
            this.mIsOverScreen = true;
            this.mOverScreenMaxWidth = (int) (getResources().getFloat(R.dimen.sesl_tablayout_over_screen_max_width_rate) * measuredWidth);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return generateDefaultLayoutParams();
    }

    public final int getSelectedTabPosition() {
        Tab tab = this.selectedTab;
        if (tab != null) {
            return tab.position;
        }
        return -1;
    }

    public final Tab getTabAt(int i) {
        if (i < 0 || i >= this.tabs.size()) {
            return null;
        }
        return (Tab) this.tabs.get(i);
    }

    public final Tab newTab() {
        Tab tab = (Tab) tabPool.acquire();
        if (tab == null) {
            tab = new Tab();
        }
        tab.parent = this;
        Pools$SimplePool pools$SimplePool = this.tabViewPool;
        TabView tabView = pools$SimplePool != null ? (TabView) pools$SimplePool.acquire() : null;
        if (tabView == null) {
            tabView = new TabView(getContext());
        }
        View view = tabView.mMainTabTouchBackground;
        if (view != null) {
            view.setAlpha(0.0f);
        }
        ConstraintLayout constraintLayout = tabView.mTabParentView;
        if (constraintLayout != null) {
            constraintLayout.removeView(null);
            tabView.mTabParentView.removeView(null);
        }
        if (tab != tabView.tab) {
            tabView.tab = tab;
            tabView.update();
        }
        tabView.setFocusable(true);
        int i = this.requestedTabMinWidth;
        if (i == -1) {
            i = 0;
        }
        tabView.setMinimumWidth(i);
        if (TextUtils.isEmpty(tab.contentDesc)) {
            tabView.setContentDescription(tab.text);
        } else {
            tabView.setContentDescription(tab.contentDesc);
        }
        tab.view = tabView;
        return tab;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        TabView tabView;
        super.onAttachedToWindow();
        for (int i = 0; i < this.tabs.size(); i++) {
            Tab tabAt = getTabAt(i);
            if (tabAt != null && (tabView = tabAt.view) != null) {
                View view = tabView.mMainTabTouchBackground;
                if (view != null) {
                    view.setAlpha(0.0f);
                }
                if (tabAt.view.mIndicatorView != null) {
                    if (getSelectedTabPosition() == i) {
                        tabAt.view.mIndicatorView.onShow();
                    } else {
                        tabAt.view.mIndicatorView.onHide();
                    }
                }
            }
        }
        MaterialShapeUtils.setParentAbsoluteElevation(this);
        if (this.viewPager == null) {
            ViewParent parent = getParent();
            if (parent instanceof ViewPager) {
                setupWithViewPager((ViewPager) parent, true);
            }
        }
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        TabView tabView;
        View view;
        super.onConfigurationChanged(configuration);
        for (int i = 0; i < this.tabs.size(); i++) {
            Tab tabAt = getTabAt(i);
            if (tabAt != null && (tabView = tabAt.view) != null && (view = tabView.mMainTabTouchBackground) != null) {
                view.setAlpha(0.0f);
            }
        }
        updateTabViews();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.setupViewPagerImplicitly) {
            setupWithViewPager(null, false);
            this.setupViewPagerImplicitly = false;
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, this.tabs.size(), 1));
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int i = this.mode;
        return (i == 0 || i == 2) && super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateTabViews();
        if (z) {
            this.mMaxTouchSlop = Math.max(this.mMaxTouchSlop, i3 - i);
        }
        int i5 = (this.mode == 1 || !(canScrollHorizontally(1) || canScrollHorizontally(-1))) ? this.mMaxTouchSlop : this.mDefaultTouchSlop;
        if (this.mCurrentTouchSlop != i5) {
            Method declaredMethod = SeslBaseReflector.getDeclaredMethod(SeslHorizontalScrollViewReflector.mClass, "hidden_setTouchSlop", Integer.TYPE);
            if (declaredMethod != null) {
                SeslBaseReflector.invoke(this, declaredMethod, Integer.valueOf(i5));
            }
            this.mCurrentTouchSlop = i5;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x007f, code lost:
    
        if (r0 != 12) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x008a, code lost:
    
        if (r9.getMeasuredWidth() != getMeasuredWidth()) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0095, code lost:
    
        if (r9.getMeasuredWidth() < getMeasuredWidth()) goto L39;
     */
    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onMeasure(int r9, int r10) {
        /*
            r8 = this;
            android.content.Context r0 = r8.getContext()
            int r1 = r8.mDepthStyle
            r2 = 56
            r3 = 2
            if (r1 != r3) goto Ld
            r1 = r2
            goto Lf
        Ld:
            r1 = 60
        Lf:
            float r0 = com.google.android.material.internal.ViewUtils.dpToPx(r1, r0)
            int r0 = java.lang.Math.round(r0)
            int r1 = android.view.View.MeasureSpec.getMode(r10)
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = 1073741824(0x40000000, float:2.0)
            r6 = 1
            r7 = 0
            if (r1 == r4) goto L35
            if (r1 == 0) goto L26
            goto L48
        L26:
            int r10 = r8.getPaddingTop()
            int r10 = r10 + r0
            int r0 = r8.getPaddingBottom()
            int r0 = r0 + r10
            int r10 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r5)
            goto L48
        L35:
            int r1 = r8.getChildCount()
            if (r1 != r6) goto L48
            int r1 = android.view.View.MeasureSpec.getSize(r10)
            if (r1 < r0) goto L48
            android.view.View r1 = r8.getChildAt(r7)
            r1.setMinimumHeight(r0)
        L48:
            int r0 = android.view.View.MeasureSpec.getSize(r9)
            int r1 = android.view.View.MeasureSpec.getMode(r9)
            if (r1 == 0) goto L64
            int r1 = r8.requestedTabMaxWidth
            if (r1 <= 0) goto L57
            goto L62
        L57:
            float r0 = (float) r0
            android.content.Context r1 = r8.getContext()
            float r1 = com.google.android.material.internal.ViewUtils.dpToPx(r2, r1)
            float r0 = r0 - r1
            int r1 = (int) r0
        L62:
            r8.tabMaxWidth = r1
        L64:
            super.onMeasure(r9, r10)
            int r9 = r8.getChildCount()
            if (r9 != r6) goto Ldf
            android.view.View r9 = r8.getChildAt(r7)
            int r0 = r8.mode
            if (r0 == 0) goto L8d
            if (r0 == r6) goto L82
            if (r0 == r3) goto L8d
            r1 = 11
            if (r0 == r1) goto L97
            r1 = 12
            if (r0 == r1) goto L97
            goto Lb5
        L82:
            int r0 = r9.getMeasuredWidth()
            int r1 = r8.getMeasuredWidth()
            if (r0 == r1) goto Lb5
            goto L97
        L8d:
            int r0 = r9.getMeasuredWidth()
            int r1 = r8.getMeasuredWidth()
            if (r0 >= r1) goto Lb5
        L97:
            int r0 = r8.getPaddingTop()
            int r1 = r8.getPaddingBottom()
            int r1 = r1 + r0
            android.view.ViewGroup$LayoutParams r0 = r9.getLayoutParams()
            int r0 = r0.height
            int r10 = android.widget.HorizontalScrollView.getChildMeasureSpec(r10, r1, r0)
            int r0 = r8.getMeasuredWidth()
            int r0 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r5)
            r9.measure(r0, r10)
        Lb5:
            r8.checkOverScreen()
            boolean r9 = r8.mIsOverScreen
            if (r9 == 0) goto Ldc
            android.view.View r9 = r8.getChildAt(r7)
            int r9 = r9.getMeasuredWidth()
            int r10 = r8.getMeasuredWidth()
            if (r9 >= r10) goto Ldc
            int r9 = r8.getMeasuredWidth()
            android.view.View r10 = r8.getChildAt(r7)
            int r10 = r10.getMeasuredWidth()
            int r9 = r9 - r10
            int r9 = r9 / r3
            r8.setPaddingRelative(r9, r7, r7, r7)
            return
        Ldc:
            r8.setPaddingRelative(r7, r7, r7, r7)
        Ldf:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.tabs.TabLayout.onMeasure(int, int):void");
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int i;
        if (motionEvent.getActionMasked() != 8 || (i = this.mode) == 0 || i == 2) {
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        TabView tabView;
        View view2;
        super.onVisibilityChanged(view, i);
        for (int i2 = 0; i2 < this.tabs.size(); i2++) {
            Tab tabAt = getTabAt(i2);
            if (tabAt != null && (tabView = tabAt.view) != null && (view2 = tabView.mMainTabTouchBackground) != null) {
                view2.setAlpha(0.0f);
            }
        }
    }

    public final void populateFromPagerAdapter() {
        int currentItem;
        removeAllTabs();
        PagerAdapter pagerAdapter = this.pagerAdapter;
        if (pagerAdapter != null) {
            int count = pagerAdapter.getCount();
            for (int i = 0; i < count; i++) {
                Tab newTab = newTab();
                this.pagerAdapter.getClass();
                if (TextUtils.isEmpty(newTab.contentDesc) && !TextUtils.isEmpty(null)) {
                    newTab.view.setContentDescription(null);
                }
                newTab.text = null;
                TabView tabView = newTab.view;
                if (tabView != null) {
                    tabView.update();
                }
                addTab(newTab, false);
            }
            ViewPager viewPager = this.viewPager;
            if (viewPager == null || count <= 0 || (currentItem = viewPager.getCurrentItem()) == getSelectedTabPosition() || currentItem >= this.tabs.size()) {
                return;
            }
            selectTab(getTabAt(currentItem), true);
        }
    }

    public final void removeAllTabs() {
        int childCount = this.slidingTabIndicator.getChildCount();
        while (true) {
            childCount--;
            if (childCount < 0) {
                break;
            }
            TabView tabView = (TabView) this.slidingTabIndicator.getChildAt(childCount);
            this.slidingTabIndicator.removeViewAt(childCount);
            if (tabView != null) {
                if (tabView.tab != null) {
                    tabView.tab = null;
                    tabView.update();
                }
                tabView.setSelected(false);
                this.tabViewPool.release(tabView);
            }
            requestLayout();
        }
        Iterator it = this.tabs.iterator();
        while (it.hasNext()) {
            Tab tab = (Tab) it.next();
            it.remove();
            tab.parent = null;
            tab.view = null;
            tab.icon = null;
            tab.text = null;
            tab.contentDesc = null;
            tab.position = -1;
            tab.customView = null;
            tabPool.release(tab);
        }
        this.selectedTab = null;
    }

    public final void selectTab(Tab tab, boolean z) {
        TabLayout tabLayout;
        ViewPager viewPager;
        if (tab != null && !tab.view.isEnabled() && (viewPager = this.viewPager) != null) {
            viewPager.setCurrentItem(getSelectedTabPosition());
            return;
        }
        Tab tab2 = this.selectedTab;
        if (tab2 == tab) {
            if (tab2 != null) {
                for (int size = this.selectedListeners.size() - 1; size >= 0; size--) {
                    ((OnTabSelectedListener) this.selectedListeners.get(size)).getClass();
                }
                animateToTab(tab.position);
                return;
            }
            return;
        }
        int i = tab != null ? tab.position : -1;
        if (z) {
            if ((tab2 == null || tab2.position == -1) && i != -1) {
                tabLayout = this;
                tabLayout.setScrollPosition(i, 0.0f, true, true, true);
            } else {
                tabLayout = this;
                tabLayout.animateToTab(i);
            }
            if (i != -1) {
                tabLayout.setSelectedTabView(i);
            }
        } else {
            tabLayout = this;
        }
        tabLayout.selectedTab = tab;
        if (tab2 != null && tab2.parent != null) {
            for (int size2 = tabLayout.selectedListeners.size() - 1; size2 >= 0; size2--) {
                ((OnTabSelectedListener) tabLayout.selectedListeners.get(size2)).getClass();
            }
        }
        if (tab != null) {
            for (int size3 = tabLayout.selectedListeners.size() - 1; size3 >= 0; size3--) {
                ((OnTabSelectedListener) tabLayout.selectedListeners.get(size3)).onTabSelected(tab);
            }
        }
    }

    public final void seslSetSubTabStyle() {
        if (this.mDepthStyle == 1) {
            this.mDepthStyle = 2;
            this.tabTextColors = getResources().getColorStateList(SeslMisc.isLightTheme(getContext()) ? R.color.sesl_tablayout_subtab_text_color_light : R.color.sesl_tablayout_subtab_text_color_dark);
            if (this.tabs.size() > 0) {
                int selectedTabPosition = getSelectedTabPosition();
                ArrayList arrayList = new ArrayList(this.tabs.size());
                for (int i = 0; i < this.tabs.size(); i++) {
                    Tab newTab = newTab();
                    newTab.text = ((Tab) this.tabs.get(i)).text;
                    newTab.icon = ((Tab) this.tabs.get(i)).icon;
                    newTab.customView = ((Tab) this.tabs.get(i)).customView;
                    ((Tab) this.tabs.get(i)).getClass();
                    if (i == selectedTabPosition) {
                        newTab.select();
                    }
                    newTab.view.update();
                    arrayList.add(newTab);
                }
                removeAllTabs();
                int i2 = 0;
                while (i2 < arrayList.size()) {
                    addTab((Tab) arrayList.get(i2), i2 == selectedTabPosition);
                    if (this.tabs.get(i2) != null) {
                        ((Tab) this.tabs.get(i2)).view.update();
                    }
                    i2++;
                }
                arrayList.clear();
            }
        }
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        super.setElevation(f);
        Drawable background = getBackground();
        if (background instanceof MaterialShapeDrawable) {
            ((MaterialShapeDrawable) background).setElevation(f);
        }
    }

    public final void setPagerAdapter(PagerAdapter pagerAdapter, boolean z) {
        PagerAdapterObserver pagerAdapterObserver;
        PagerAdapter pagerAdapter2 = this.pagerAdapter;
        if (pagerAdapter2 != null && (pagerAdapterObserver = this.pagerAdapterObserver) != null) {
            pagerAdapter2.mObservable.unregisterObserver(pagerAdapterObserver);
        }
        this.pagerAdapter = pagerAdapter;
        if (z && pagerAdapter != null) {
            if (this.pagerAdapterObserver == null) {
                this.pagerAdapterObserver = new PagerAdapterObserver();
            }
            pagerAdapter.mObservable.registerObserver(this.pagerAdapterObserver);
        }
        populateFromPagerAdapter();
    }

    public final void setScrollPosition(int i, float f, boolean z, boolean z2, boolean z3) {
        float f2;
        float f3 = i + f;
        int round = Math.round(f3);
        if (round < 0 || round >= this.slidingTabIndicator.getChildCount()) {
            return;
        }
        if (z2) {
            SlidingTabIndicator slidingTabIndicator = this.slidingTabIndicator;
            TabLayout.this.indicatorPosition = Math.round(f3);
            View childAt = slidingTabIndicator.getChildAt(i);
            View childAt2 = slidingTabIndicator.getChildAt(i + 1);
            if (childAt == null || childAt.getWidth() <= 0) {
                f2 = f;
                Drawable drawable = TabLayout.this.tabSelectedIndicator;
                drawable.setBounds(-1, drawable.getBounds().top, -1, TabLayout.this.tabSelectedIndicator.getBounds().bottom);
            } else {
                TabLayout tabLayout = TabLayout.this;
                f2 = f;
                tabLayout.tabIndicatorInterpolator.updateIndicatorForOffset(tabLayout, childAt, childAt2, f2, tabLayout.tabSelectedIndicator);
            }
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            slidingTabIndicator.postInvalidateOnAnimation();
        } else {
            f2 = f;
        }
        ValueAnimator valueAnimator = this.scrollAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.scrollAnimator.cancel();
        }
        int calculateScrollXForTab = calculateScrollXForTab(f2, i);
        int scrollX = getScrollX();
        boolean z4 = (i < getSelectedTabPosition() && calculateScrollXForTab >= scrollX) || (i > getSelectedTabPosition() && calculateScrollXForTab <= scrollX) || i == getSelectedTabPosition();
        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
        if (getLayoutDirection() == 1) {
            z4 = (i < getSelectedTabPosition() && calculateScrollXForTab <= scrollX) || (i > getSelectedTabPosition() && calculateScrollXForTab >= scrollX) || i == getSelectedTabPosition();
        }
        if (z4 || this.viewPagerScrollState == 1 || z3) {
            if (i < 0) {
                calculateScrollXForTab = 0;
            }
            scrollTo(calculateScrollXForTab, 0);
        }
        if (z) {
            setSelectedTabView(round);
        }
    }

    public final void setSelectedTabIndicatorColor(int i) {
        int i2;
        int i3 = 0;
        updateTabViews(false);
        this.mTabSelectedIndicatorColor = i;
        ArrayList arrayList = this.tabs;
        int size = arrayList.size();
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            SeslAbsIndicatorView seslAbsIndicatorView = ((Tab) obj).view.mIndicatorView;
            if (seslAbsIndicatorView != null) {
                if (this.mDepthStyle != 2 || (i2 = this.mSubTabSelectedIndicatorColor) == -1) {
                    seslAbsIndicatorView.onSetSelectedIndicatorColor(i);
                } else {
                    seslAbsIndicatorView.onSetSelectedIndicatorColor(i2);
                }
                seslAbsIndicatorView.invalidate();
            }
        }
    }

    public final void setSelectedTabView(int i) {
        SeslAbsIndicatorView seslAbsIndicatorView;
        int childCount = this.slidingTabIndicator.getChildCount();
        if (i < childCount) {
            int i2 = 0;
            while (i2 < childCount) {
                View childAt = this.slidingTabIndicator.getChildAt(i2);
                if ((i2 != i || childAt.isSelected()) && (i2 == i || !childAt.isSelected())) {
                    childAt.setSelected(i2 == i);
                    childAt.setActivated(i2 == i);
                } else {
                    childAt.setSelected(i2 == i);
                    childAt.setActivated(i2 == i);
                    if (childAt instanceof TabView) {
                        ((TabView) childAt).updateTab();
                    }
                }
                i2++;
            }
            for (int i3 = 0; i3 < this.tabs.size(); i3++) {
                Tab tab = (Tab) this.tabs.get(i3);
                if (tab != null && (seslAbsIndicatorView = tab.view.mIndicatorView) != null) {
                    if (i3 != i) {
                        seslAbsIndicatorView.onHide();
                    } else if (seslAbsIndicatorView.getAlpha() != 1.0f) {
                        seslAbsIndicatorView.onShow();
                    }
                }
            }
        }
    }

    public final void setupWithViewPager(ViewPager viewPager, boolean z) {
        TabLayout tabLayout;
        List list;
        List list2;
        ViewPager viewPager2 = this.viewPager;
        if (viewPager2 != null) {
            TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener = this.pageChangeListener;
            if (tabLayoutOnPageChangeListener != null && (list2 = viewPager2.mOnPageChangeListeners) != null) {
                ((ArrayList) list2).remove(tabLayoutOnPageChangeListener);
            }
            AdapterChangeListener adapterChangeListener = this.adapterChangeListener;
            if (adapterChangeListener != null && (list = this.viewPager.mAdapterChangeListeners) != null) {
                ((ArrayList) list).remove(adapterChangeListener);
            }
        }
        ViewPagerOnTabSelectedListener viewPagerOnTabSelectedListener = this.currentVpSelectedListener;
        if (viewPagerOnTabSelectedListener != null) {
            this.selectedListeners.remove(viewPagerOnTabSelectedListener);
            this.currentVpSelectedListener = null;
        }
        if (viewPager != null) {
            this.viewPager = viewPager;
            if (this.pageChangeListener == null) {
                this.pageChangeListener = new TabLayoutOnPageChangeListener(this);
            }
            TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener2 = this.pageChangeListener;
            tabLayoutOnPageChangeListener2.scrollState = 0;
            tabLayoutOnPageChangeListener2.previousScrollState = 0;
            viewPager.addOnPageChangeListener(tabLayoutOnPageChangeListener2);
            ViewPagerOnTabSelectedListener viewPagerOnTabSelectedListener2 = new ViewPagerOnTabSelectedListener(viewPager);
            this.currentVpSelectedListener = viewPagerOnTabSelectedListener2;
            addOnTabSelectedListener$1(viewPagerOnTabSelectedListener2);
            PagerAdapter adapter = viewPager.getAdapter();
            if (adapter != null) {
                setPagerAdapter(adapter, true);
            }
            if (this.adapterChangeListener == null) {
                this.adapterChangeListener = new AdapterChangeListener();
            }
            AdapterChangeListener adapterChangeListener2 = this.adapterChangeListener;
            adapterChangeListener2.autoRefresh = true;
            if (viewPager.mAdapterChangeListeners == null) {
                viewPager.mAdapterChangeListeners = new ArrayList();
            }
            ((ArrayList) viewPager.mAdapterChangeListeners).add(adapterChangeListener2);
            tabLayout = this;
            tabLayout.setScrollPosition(viewPager.getCurrentItem(), 0.0f, true, true, true);
        } else {
            tabLayout = this;
            tabLayout.viewPager = null;
            tabLayout.setPagerAdapter(null, false);
        }
        tabLayout.setupViewPagerImplicitly = z;
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return Math.max(0, ((this.slidingTabIndicator.getWidth() - getWidth()) - getPaddingLeft()) - getPaddingRight()) > 0;
    }

    public final void updateTabViewLayoutParams(LinearLayout.LayoutParams layoutParams) {
        int i = this.mode;
        if (i == 1 && this.tabGravity == 0) {
            layoutParams.width = 0;
            layoutParams.weight = 1.0f;
        } else if (i == 11 || i == 12) {
            layoutParams.width = -2;
            layoutParams.weight = 0.0f;
        } else {
            layoutParams.width = -2;
            layoutParams.weight = 0.0f;
        }
    }

    public final void updateTabViews(boolean z) {
        for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++) {
            View childAt = this.slidingTabIndicator.getChildAt(i);
            int i2 = this.requestedTabMinWidth;
            if (i2 == -1) {
                i2 = 0;
            }
            childAt.setMinimumWidth(i2);
            updateTabViewLayoutParams((LinearLayout.LayoutParams) childAt.getLayoutParams());
            if (z) {
                childAt.requestLayout();
            }
        }
        updateTabViews();
    }

    public TabLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.tabStyle);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public final void addView(View view, int i) {
        addViewInternal(view);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return generateDefaultLayoutParams();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x032f, code lost:
    
        if (r2 != 12) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0345, code lost:
    
        if (r2 != 2) goto L77;
     */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0368  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0370  */
    /* JADX WARN: Removed duplicated region for block: B:71:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TabLayout(android.content.Context r17, android.util.AttributeSet r18, int r19) {
        /*
            Method dump skipped, instructions count: 916
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.tabs.TabLayout.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup, android.view.ViewManager
    public final void addView(View view, ViewGroup.LayoutParams layoutParams) {
        addViewInternal(view);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        addViewInternal(view);
    }

    public final void updateTabViews() {
        int color;
        if (this.tabs.isEmpty()) {
            return;
        }
        for (int i = 0; i < this.tabs.size(); i++) {
            TabView tabView = ((Tab) this.tabs.get(i)).view;
            TextView textView = tabView.textView;
            if (tabView.getWidth() > 0) {
                getContext().getResources().getDimensionPixelSize(R.dimen.sesl_tablayout_subtab_n_badge_xoffset);
            }
            ColorStateList colorStateList = this.tabTextColors;
            if (this.mDepthStyle == 1 && Settings.System.getInt(this.mContentResolver, SettingsHelper.INDEX_SHOW_BUTTON_BACKGROUND, 0) == 1) {
                ColorDrawable colorDrawable = this.mBackgroundColorDrawable;
                if (colorDrawable != null) {
                    color = colorDrawable.getColor();
                } else {
                    color = getResources().getColor(SeslMisc.isLightTheme(getContext()) ? R.color.sesl_bottom_navigation_background_light : R.color.sesl_bottom_navigation_background_dark, null);
                }
                Drawable drawable = tabView.getResources().getDrawable(R.drawable.sesl_bottom_nav_show_button_shapes_background);
                TextView textView2 = tabView.textView;
                if (textView2 != null) {
                    textView2.setTextColor(color);
                    tabView.textView.setBackground(drawable);
                    tabView.textView.setBackgroundTintList(colorStateList);
                }
                TextView textView3 = tabView.mSubTextView;
                if (textView3 != null) {
                    textView3.setTextColor(color);
                    tabView.mSubTextView.setBackground(drawable);
                    tabView.mSubTextView.setBackgroundTintList(colorStateList);
                }
            }
        }
    }
}
