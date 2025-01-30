package com.google.android.material.tabs;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.animation.SeslAnimationUtils;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.util.SeslMisc;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pools$SimplePool;
import androidx.core.util.Pools$SynchronizedPool;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.widget.SeslHorizontalScrollViewReflector;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import com.sec.ims.volte2.data.VolteConstants;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ViewPager.DecorView
/* loaded from: classes2.dex */
public class TabLayout extends HorizontalScrollView {
    public static final Pools$SynchronizedPool tabPool = new Pools$SynchronizedPool(16);
    public AdapterChangeListener adapterChangeListener;
    public ViewPagerOnTabSelectedListener currentVpSelectedListener;
    public final boolean inlineLabel;
    public final Typeface mBoldTypeface;
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
    public final int mSubTabSelectedIndicatorColor;
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
    public boolean setupViewPagerImplicitly;
    public final SlidingTabIndicator slidingTabIndicator;
    public final int tabBackgroundResId;
    public int tabGravity;
    public final ColorStateList tabIconTint;
    public final PorterDuff.Mode tabIconTintMode;
    public final int tabIndicatorAnimationDuration;
    public boolean tabIndicatorFullWidth;
    public int tabIndicatorGravity;
    public TabIndicatorInterpolator tabIndicatorInterpolator;
    public int tabMaxWidth;
    public final int tabPaddingBottom;
    public final int tabPaddingTop;
    public final ColorStateList tabRippleColorStateList;
    public Drawable tabSelectedIndicator;
    public final int tabTextAppearance;
    public ColorStateList tabTextColors;
    public final float tabTextMultiLineSize;
    public final float tabTextSize;
    public final Pools$SimplePool tabViewPool;
    public final ArrayList tabs;
    public ViewPager viewPager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AdapterChangeListener implements ViewPager.OnAdapterChangeListener {
        public boolean autoRefresh;

        public AdapterChangeListener() {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnAdapterChangeListener
        public final void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
            TabLayout tabLayout = TabLayout.this;
            if (tabLayout.viewPager == viewPager) {
                tabLayout.setPagerAdapter(pagerAdapter2, this.autoRefresh);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface BaseOnTabSelectedListener {
        void onTabReselected();

        void onTabSelected(Tab tab);

        void onTabUnselected();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnTabSelectedListener extends BaseOnTabSelectedListener {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PagerAdapterObserver extends DataSetObserver {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SlidingTabIndicator extends LinearLayout {
        public static final /* synthetic */ int $r8$clinit = 0;

        public SlidingTabIndicator(Context context) {
            super(context);
            setWillNotDraw(false);
        }

        @Override // android.view.View
        public final void draw(Canvas canvas) {
            super.draw(canvas);
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
            int i4 = 0;
            boolean z = true;
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
                super.onMeasure(View.MeasureSpec.makeMeasureSpec(size, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), i2);
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

        @Override // android.widget.LinearLayout, android.view.View
        public final void onRtlPropertiesChanged(int i) {
            super.onRtlPropertiesChanged(i);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Tab {
        public CharSequence contentDesc;
        public View customView;
        public Drawable icon;
        public TabLayout parent;
        public CharSequence subText;
        public CharSequence text;
        public TabView view;
        public int position = -1;
        public final int labelVisibilityMode = 1;

        /* renamed from: id */
        public int f463id = -1;

        public final void select() {
            TabLayout tabLayout = this.parent;
            if (tabLayout == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            tabLayout.selectTab(this, true);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
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
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public final void onPageScrolled(float f, int i, int i2) {
            TabLayout tabLayout = (TabLayout) this.tabLayoutRef.get();
            if (tabLayout != null) {
                int i3 = this.scrollState;
                tabLayout.setScrollPosition(f, i, i3 != 2 || this.previousScrollState == 1, (i3 == 2 && this.previousScrollState == 0) ? false : true);
            }
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public final void onPageSelected(int i) {
            TabLayout tabLayout = (TabLayout) this.tabLayoutRef.get();
            if (tabLayout == null || tabLayout.getSelectedTabPosition() == i || i >= tabLayout.getTabCount()) {
                return;
            }
            int i2 = this.scrollState;
            tabLayout.selectTab(tabLayout.getTabAt(i), i2 == 0 || (i2 == 2 && this.previousScrollState == 0));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TabView extends LinearLayout {
        public static final /* synthetic */ int $r8$clinit = 0;
        public Drawable baseBackgroundDrawable;
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
        public RelativeLayout mTabParentView;
        public final ViewOnKeyListenerC43791 mTabViewKeyListener;
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
                ViewCompat.Api16Impl.setBackground(this, drawable2);
            }
            View view = this.mMainTabTouchBackground;
            if (view != null) {
                view.setBackgroundTintList(TabLayout.this.tabRippleColorStateList);
            }
            setGravity(17);
            setOrientation(!TabLayout.this.inlineLabel ? 1 : 0);
            setClickable(true);
            setOnKeyListener(r1);
            if (TabLayout.this.mDepthStyle == 1) {
                int i2 = TabLayout.this.tabPaddingTop;
                int i3 = TabLayout.this.tabPaddingBottom;
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api17Impl.setPaddingRelative(this, 0, i2, 0, i3);
            }
            this.mIconSize = getResources().getDimensionPixelOffset(R.dimen.sesl_tab_icon_size);
        }

        @Override // android.view.ViewGroup, android.view.View
        public final void drawableStateChanged() {
            super.drawableStateChanged();
            int[] drawableState = getDrawableState();
            Drawable drawable = this.baseBackgroundDrawable;
            if (drawable == null || !drawable.isStateful()) {
                return;
            }
            this.baseBackgroundDrawable.setState(drawableState);
        }

        @Override // android.view.View
        public final void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            this.mIconSize = getResources().getDimensionPixelOffset(R.dimen.sesl_tab_icon_size);
        }

        @Override // android.view.View
        public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            AccessibilityNodeInfoCompat wrap = AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo);
            wrap.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, 0, 1, this.tab.position, 1, isSelected()));
            if (isSelected()) {
                wrap.setClickable(false);
                wrap.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
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
                RelativeLayout relativeLayout = this.mTabParentView;
                view2.setRight(relativeLayout != null ? relativeLayout.getWidth() : i3 - i);
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

        /* JADX WARN: Code restructure failed: missing block: B:33:0x00ca, code lost:
        
            if (((r4 / r2.getPaint().getTextSize()) * r2.getLineWidth(0)) > ((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight())) goto L49;
         */
        @Override // android.widget.LinearLayout, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onMeasure(int i, int i2) {
            TextView textView;
            TextView textView2;
            TextView textView3;
            int size = View.MeasureSpec.getSize(i);
            int mode = View.MeasureSpec.getMode(i);
            TabLayout tabLayout = TabLayout.this;
            int i3 = tabLayout.tabMaxWidth;
            int i4 = tabLayout.mode;
            if (i4 != 11 && i4 != 12) {
                int i5 = tabLayout.mRequestedTabWidth;
                if (i5 != -1) {
                    i = View.MeasureSpec.makeMeasureSpec(i5, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                } else if (i3 > 0 && (mode == 0 || size > i3)) {
                    i = View.MeasureSpec.makeMeasureSpec(i3, VideoPlayer.MEDIA_ERROR_SYSTEM);
                }
            } else if (mode == 0) {
                i = View.MeasureSpec.makeMeasureSpec(i3, 0);
            } else if (mode == 1073741824) {
                i = View.MeasureSpec.makeMeasureSpec(size, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
            }
            super.onMeasure(i, i2);
            TextView textView4 = this.textView;
            if (textView4 != null && this.customView == null) {
                TabLayout tabLayout2 = TabLayout.this;
                float f = tabLayout2.tabTextSize;
                TabLayout.access$1700(tabLayout2, textView4, (int) f);
                TabLayout tabLayout3 = TabLayout.this;
                if (tabLayout3.mDepthStyle == 2 && (textView3 = this.mSubTextView) != null) {
                    TabLayout.access$1700(tabLayout3, textView3, tabLayout3.mSubTabTextSize);
                }
                int i6 = this.defaultMaxLines;
                ImageView imageView = this.iconView;
                boolean z = true;
                if (imageView == null || imageView.getVisibility() != 0) {
                    TextView textView5 = this.textView;
                    if (textView5 != null && textView5.getLineCount() > 1) {
                        f = TabLayout.this.tabTextMultiLineSize;
                    }
                } else {
                    i6 = 1;
                }
                float textSize = this.textView.getTextSize();
                int lineCount = this.textView.getLineCount();
                int maxLines = this.textView.getMaxLines();
                if (f != textSize || (maxLines >= 0 && i6 != maxLines)) {
                    if (TabLayout.this.mode == 1 && f > textSize && lineCount == 1) {
                        Layout layout = this.textView.getLayout();
                        if (layout != null) {
                        }
                        z = false;
                    }
                    if (z) {
                        this.textView.setTextSize(0, f);
                        TabLayout.access$1700(TabLayout.this, this.textView, (int) f);
                        TabLayout tabLayout4 = TabLayout.this;
                        if (tabLayout4.mDepthStyle == 2 && (textView2 = this.mSubTextView) != null) {
                            TabLayout.access$1700(tabLayout4, textView2, tabLayout4.mSubTabTextSize);
                        }
                        this.textView.setMaxLines(i6);
                        super.onMeasure(i, i2);
                    }
                }
            }
            if (this.customTextView != null || this.mTabParentView == null || (textView = this.textView) == null || this.tab == null) {
                return;
            }
            TabLayout tabLayout5 = TabLayout.this;
            if (tabLayout5.mode == 0 && tabLayout5.mDepthStyle == 2) {
                if (i3 > 0) {
                    textView.measure(i3, 0);
                } else {
                    textView.measure(0, 0);
                }
                int measuredWidth = this.textView.getMeasuredWidth();
                ViewGroup.LayoutParams layoutParams = this.mTabParentView.getLayoutParams();
                layoutParams.width = (getContext().getResources().getDimensionPixelSize(R.dimen.sesl_tablayout_subtab_side_space) * 2) + measuredWidth;
                this.mTabParentView.setLayoutParams(layoutParams);
                super.onMeasure(View.MeasureSpec.makeMeasureSpec(layoutParams.width, VideoPlayer.MEDIA_ERROR_SYSTEM), i2);
            }
        }

        @Override // android.view.View
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            SeslAbsIndicatorView seslAbsIndicatorView;
            TextView textView;
            if (!isEnabled()) {
                return super.onTouchEvent(motionEvent);
            }
            View view = this.tab.customView;
            if (view != null) {
                return super.onTouchEvent(motionEvent);
            }
            if (motionEvent == null || view != null || this.textView == null) {
                return false;
            }
            int action = motionEvent.getAction() & 255;
            if (action == 0) {
                this.mIsCallPerformClick = false;
                if (this.tab.position != TabLayout.this.getSelectedTabPosition() && (textView = this.textView) != null) {
                    textView.setTypeface(TabLayout.this.mBoldTypeface);
                    TabLayout.startTextColorChangeAnimation(TabLayout.this.getSelectedTabTextColor(), this.textView);
                    SeslAbsIndicatorView seslAbsIndicatorView2 = this.mIndicatorView;
                    if (seslAbsIndicatorView2 != null) {
                        seslAbsIndicatorView2.startPressEffect();
                    }
                    TabLayout tabLayout = TabLayout.this;
                    Tab tabAt = tabLayout.getTabAt(tabLayout.getSelectedTabPosition());
                    if (tabAt != null) {
                        TextView textView2 = tabAt.view.textView;
                        if (textView2 != null) {
                            textView2.setTypeface(TabLayout.this.mNormalTypeface);
                            TabLayout.startTextColorChangeAnimation(TabLayout.this.tabTextColors.getDefaultColor(), tabAt.view.textView);
                        }
                        SeslAbsIndicatorView seslAbsIndicatorView3 = tabAt.view.mIndicatorView;
                        if (seslAbsIndicatorView3 != null) {
                            seslAbsIndicatorView3.onHide();
                        }
                    }
                } else if (this.tab.position == TabLayout.this.getSelectedTabPosition() && (seslAbsIndicatorView = this.mIndicatorView) != null) {
                    seslAbsIndicatorView.startPressEffect();
                }
                showMainTabTouchBackground(0);
            } else if (action == 1) {
                showMainTabTouchBackground(1);
                SeslAbsIndicatorView seslAbsIndicatorView4 = this.mIndicatorView;
                if (seslAbsIndicatorView4 != null) {
                    seslAbsIndicatorView4.startReleaseEffect();
                    this.mIndicatorView.onTouchEvent(motionEvent);
                }
                performClick();
                this.mIsCallPerformClick = true;
            } else if (action == 3) {
                this.textView.setTypeface(TabLayout.this.mNormalTypeface);
                TabLayout.startTextColorChangeAnimation(TabLayout.this.tabTextColors.getDefaultColor(), this.textView);
                SeslAbsIndicatorView seslAbsIndicatorView5 = this.mIndicatorView;
                if (seslAbsIndicatorView5 != null && !seslAbsIndicatorView5.isSelected()) {
                    this.mIndicatorView.onHide();
                }
                TabLayout tabLayout2 = TabLayout.this;
                Tab tabAt2 = tabLayout2.getTabAt(tabLayout2.getSelectedTabPosition());
                if (tabAt2 != null) {
                    TextView textView3 = tabAt2.view.textView;
                    if (textView3 != null) {
                        textView3.setTypeface(TabLayout.this.mBoldTypeface);
                        TabLayout.startTextColorChangeAnimation(TabLayout.this.getSelectedTabTextColor(), tabAt2.view.textView);
                    }
                    SeslAbsIndicatorView seslAbsIndicatorView6 = tabAt2.view.mIndicatorView;
                    if (seslAbsIndicatorView6 != null) {
                        seslAbsIndicatorView6.onShow();
                    }
                }
                if (TabLayout.this.mDepthStyle == 1) {
                    showMainTabTouchBackground(3);
                } else {
                    SeslAbsIndicatorView seslAbsIndicatorView7 = this.mIndicatorView;
                    if (seslAbsIndicatorView7 != null && seslAbsIndicatorView7.isSelected()) {
                        this.mIndicatorView.startReleaseEffect();
                    }
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
                if (isSelected() != z) {
                }
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
                }
                TextView textView2 = this.mSubTextView;
                if (textView2 != null) {
                    textView2.setSelected(z);
                }
            }
        }

        public final void showMainTabTouchBackground(int i) {
            View view = this.mMainTabTouchBackground;
            if (view != null) {
                TabLayout tabLayout = TabLayout.this;
                if (tabLayout.mDepthStyle == 1 && tabLayout.tabBackgroundResId == 0) {
                    view.setAlpha(1.0f);
                    AnimationSet animationSet = new AnimationSet(true);
                    animationSet.setFillAfter(true);
                    if (i == 0) {
                        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                        alphaAnimation.setDuration(100L);
                        alphaAnimation.setFillAfter(true);
                        animationSet.addAnimation(alphaAnimation);
                        ScaleAnimation scaleAnimation = new ScaleAnimation(0.95f, 1.0f, 0.95f, 1.0f, 1, 0.5f, 1, 0.5f);
                        scaleAnimation.setDuration(350L);
                        scaleAnimation.setInterpolator(SeslAnimationUtils.SINE_IN_OUT_80);
                        scaleAnimation.setFillAfter(true);
                        animationSet.addAnimation(scaleAnimation);
                        this.mMainTabTouchBackground.startAnimation(animationSet);
                        return;
                    }
                    if ((i == 1 || i == 3) && this.mMainTabTouchBackground.getAnimation() != null) {
                        if (!this.mMainTabTouchBackground.getAnimation().hasEnded()) {
                            this.mMainTabTouchBackground.getAnimation().setAnimationListener(new Animation.AnimationListener() { // from class: com.google.android.material.tabs.TabLayout.TabView.3
                                @Override // android.view.animation.Animation.AnimationListener
                                public final void onAnimationEnd(Animation animation) {
                                    AnimationSet animationSet2 = new AnimationSet(true);
                                    animationSet2.setFillAfter(true);
                                    AlphaAnimation alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
                                    alphaAnimation2.setDuration(400L);
                                    alphaAnimation2.setFillAfter(true);
                                    animationSet2.addAnimation(alphaAnimation2);
                                    TabView.this.mMainTabTouchBackground.startAnimation(alphaAnimation2);
                                }

                                @Override // android.view.animation.Animation.AnimationListener
                                public final void onAnimationRepeat(Animation animation) {
                                }

                                @Override // android.view.animation.Animation.AnimationListener
                                public final void onAnimationStart(Animation animation) {
                                }
                            });
                            return;
                        }
                        AlphaAnimation alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
                        alphaAnimation2.setDuration(400L);
                        alphaAnimation2.setFillAfter(true);
                        animationSet.addAnimation(alphaAnimation2);
                        this.mMainTabTouchBackground.startAnimation(animationSet);
                    }
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:134:0x02a3, code lost:
        
            if ((r1 != -1 && r1 == r0.position) != false) goto L167;
         */
        /* JADX WARN: Removed duplicated region for block: B:118:0x025e  */
        /* JADX WARN: Removed duplicated region for block: B:121:0x026b  */
        /* JADX WARN: Removed duplicated region for block: B:143:0x024f  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void update() {
            int i;
            boolean z;
            RelativeLayout relativeLayout;
            final ImageView imageView;
            final TextView textView;
            Drawable drawable;
            RelativeLayout relativeLayout2;
            int i2;
            Tab tab = this.tab;
            View view = tab != null ? tab.customView : null;
            if (view != null) {
                ViewParent parent = view.getParent();
                if (parent != this) {
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(view);
                    }
                    addView(view);
                }
                this.customView = view;
                TextView textView2 = this.textView;
                if (textView2 != null) {
                    textView2.setVisibility(8);
                }
                ImageView imageView2 = this.iconView;
                if (imageView2 != null) {
                    imageView2.setVisibility(8);
                    this.iconView.setImageDrawable(null);
                }
                TextView textView3 = this.mSubTextView;
                if (textView3 != null) {
                    textView3.setVisibility(8);
                }
                TextView textView4 = (TextView) view.findViewById(android.R.id.text1);
                this.customTextView = textView4;
                if (textView4 != null) {
                    this.defaultMaxLines = textView4.getMaxLines();
                }
                this.customIconView = (ImageView) view.findViewById(android.R.id.icon);
            } else {
                View view2 = this.customView;
                if (view2 != null) {
                    removeView(view2);
                    this.customView = null;
                }
                this.customTextView = null;
                this.customIconView = null;
            }
            boolean z2 = true;
            if (this.customView != null || this.tab == null) {
                TextView textView5 = this.customTextView;
                if (textView5 != null || this.customIconView != null) {
                    updateTextAndIcon(textView5, this.customIconView);
                }
            } else {
                if (this.mTabParentView == null) {
                    if (TabLayout.this.mDepthStyle == 2) {
                        this.mTabParentView = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.sesl_tabs_sub_tab_layout, (ViewGroup) this, false);
                    } else {
                        RelativeLayout relativeLayout3 = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.sesl_tabs_main_tab_layout, (ViewGroup) this, false);
                        this.mTabParentView = relativeLayout3;
                        View findViewById = relativeLayout3.findViewById(R.id.main_tab_touch_background);
                        this.mMainTabTouchBackground = findViewById;
                        if (findViewById != null && this.tab.icon == null) {
                            Context context = getContext();
                            int i3 = SeslMisc.isLightTheme(getContext()) ? R.drawable.sesl_tablayout_maintab_touch_background_light : R.drawable.sesl_tablayout_maintab_touch_background_dark;
                            Object obj = ContextCompat.sLock;
                            Drawable drawable2 = context.getDrawable(i3);
                            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                            ViewCompat.Api16Impl.setBackground(findViewById, drawable2);
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
                    if (seslAbsIndicatorView != null && (i2 = tabLayout.mSubTabSelectedIndicatorColor) != -1) {
                        seslAbsIndicatorView.onSetSelectedIndicatorColor(i2);
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
                this.textView.setTextAppearance(TabLayout.this.tabTextAppearance);
                if (isSelected()) {
                    this.textView.setTypeface(TabLayout.this.mBoldTypeface);
                } else {
                    this.textView.setTypeface(TabLayout.this.mNormalTypeface);
                }
                TabLayout tabLayout2 = TabLayout.this;
                TabLayout.access$1700(tabLayout2, this.textView, (int) tabLayout2.tabTextSize);
                this.textView.setTextColor(TabLayout.this.tabTextColors);
                if (TabLayout.this.mDepthStyle == 2) {
                    if (this.mSubTextView == null) {
                        this.mSubTextView = (TextView) this.mTabParentView.findViewById(R.id.sub_title);
                    }
                    TextView textView6 = this.mSubTextView;
                    if (textView6 != null) {
                        textView6.setTextAppearance(TabLayout.this.mSubTabSubTextAppearance);
                        this.mSubTextView.setTextColor(TabLayout.this.mSubTabSubTextColors);
                    }
                    TextView textView7 = this.mSubTextView;
                    if (textView7 != null) {
                        TabLayout tabLayout3 = TabLayout.this;
                        TabLayout.access$1700(tabLayout3, textView7, tabLayout3.mSubTabTextSize);
                    }
                }
                if (this.iconView == null && (relativeLayout2 = this.mTabParentView) != null) {
                    this.iconView = (ImageView) relativeLayout2.findViewById(R.id.icon);
                }
                Drawable mutate = (tab == null || (drawable = tab.icon) == null) ? null : drawable.mutate();
                if (mutate != null) {
                    mutate.setTintList(TabLayout.this.tabIconTint);
                    PorterDuff.Mode mode = TabLayout.this.tabIconTintMode;
                    if (mode != null) {
                        mutate.setTintMode(mode);
                    }
                }
                TextView textView8 = this.textView;
                TextView textView9 = this.mSubTextView;
                updateTextAndIcon(textView8, this.iconView);
                if (textView9 != null) {
                    Tab tab2 = this.tab;
                    CharSequence charSequence = tab2 != null ? tab2.subText : null;
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView8.getLayoutParams();
                    if (!TextUtils.isEmpty(charSequence)) {
                        layoutParams.removeRule(13);
                        layoutParams.addRule(2, R.id.center_anchor);
                        textView9.setText(charSequence);
                        if (this.tab.labelVisibilityMode == 1) {
                            textView9.setVisibility(0);
                        } else {
                            textView9.setVisibility(8);
                        }
                        setVisibility(0);
                    } else {
                        layoutParams.addRule(13);
                        layoutParams.removeRule(2);
                        textView9.setVisibility(8);
                        textView9.setText((CharSequence) null);
                    }
                }
                TabLayout tabLayout4 = TabLayout.this;
                if (tabLayout4.mDepthStyle == 2) {
                    r8 = tabLayout4.mode != 0 ? -1 : -2;
                    i = TextUtils.isEmpty(tab != null ? tab.subText : null) ^ true ? TabLayout.this.mSubTabIndicator2ndHeight : TabLayout.this.mSubTabIndicatorHeight;
                    RelativeLayout relativeLayout4 = this.mTabParentView;
                    if (relativeLayout4 != null && relativeLayout4.getHeight() != i) {
                        z = true;
                        relativeLayout = this.mTabParentView;
                        if (relativeLayout == null && relativeLayout.getParent() == null) {
                            addView(this.mTabParentView, r8, i);
                        } else if (z) {
                            removeView(this.mTabParentView);
                            addView(this.mTabParentView, r8, i);
                        }
                        imageView = this.iconView;
                        if (imageView != null) {
                            imageView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.google.android.material.tabs.TabLayout.TabView.2
                                @Override // android.view.View.OnLayoutChangeListener
                                public final void onLayoutChange(View view3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
                                    if (imageView.getVisibility() == 0) {
                                        TabView tabView = TabView.this;
                                        int i12 = TabView.$r8$clinit;
                                        tabView.getClass();
                                    }
                                }
                            });
                        }
                        textView = this.textView;
                        if (textView != null) {
                            textView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.google.android.material.tabs.TabLayout.TabView.2
                                @Override // android.view.View.OnLayoutChangeListener
                                public final void onLayoutChange(View view3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
                                    if (textView.getVisibility() == 0) {
                                        TabView tabView = TabView.this;
                                        int i12 = TabView.$r8$clinit;
                                        tabView.getClass();
                                    }
                                }
                            });
                        }
                    }
                } else if (this.tab.icon != null) {
                    i = -1;
                } else {
                    i = -1;
                    r8 = -1;
                }
                z = false;
                relativeLayout = this.mTabParentView;
                if (relativeLayout == null) {
                }
                if (z) {
                }
                imageView = this.iconView;
                if (imageView != null) {
                }
                textView = this.textView;
                if (textView != null) {
                }
            }
            if (tab != null && !TextUtils.isEmpty(tab.contentDesc)) {
                setContentDescription(tab.contentDesc);
            }
            if (tab != null) {
                TabLayout tabLayout5 = tab.parent;
                if (tabLayout5 == null) {
                    throw new IllegalArgumentException("Tab not attached to a TabLayout");
                }
                int selectedTabPosition = tabLayout5.getSelectedTabPosition();
            }
            z2 = false;
            setSelected(z2);
        }

        public final void updateTextAndIcon(TextView textView, ImageView imageView) {
            int i;
            Drawable drawable;
            Tab tab = this.tab;
            Drawable mutate = (tab == null || (drawable = tab.icon) == null) ? null : drawable.mutate();
            if (mutate != null) {
                mutate.setTintList(TabLayout.this.tabIconTint);
                PorterDuff.Mode mode = TabLayout.this.tabIconTintMode;
                if (mode != null) {
                    mutate.setTintMode(mode);
                }
            }
            Tab tab2 = this.tab;
            CharSequence charSequence = tab2 != null ? tab2.text : null;
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
            boolean z = !TextUtils.isEmpty(charSequence);
            if (textView != null) {
                if (z) {
                    textView.setText(charSequence);
                    if (this.tab.labelVisibilityMode == 1) {
                        textView.setVisibility(0);
                    } else {
                        textView.setVisibility(8);
                    }
                    setVisibility(0);
                } else {
                    textView.setVisibility(8);
                    textView.setText((CharSequence) null);
                }
            }
            if (imageView != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                if (z && imageView.getVisibility() == 0) {
                    i = TabLayout.this.mIconTextGap;
                    if (i == -1) {
                        i = (int) ViewUtils.dpToPx(8, getContext());
                    }
                } else {
                    i = 0;
                }
                if (i != marginLayoutParams.getMarginEnd()) {
                    marginLayoutParams.setMarginEnd(i);
                    marginLayoutParams.bottomMargin = 0;
                    imageView.setLayoutParams(marginLayoutParams);
                    imageView.requestLayout();
                    if (textView != null) {
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
                        layoutParams.addRule(13, 0);
                        layoutParams.addRule(15, 1);
                        layoutParams.addRule(17, R.id.icon);
                        textView.setLayoutParams(layoutParams);
                    }
                }
            }
            Tab tab3 = this.tab;
            setTooltipText(z ? null : tab3 != null ? tab3.contentDesc : null);
        }
    }

    public TabLayout(Context context) {
        this(context, null);
    }

    public static void access$1700(TabLayout tabLayout, TextView textView, int i) {
        float f = tabLayout.getResources().getConfiguration().fontScale;
        if (textView == null || !tabLayout.mIsScaledTextSizeType || f <= 1.3f) {
            return;
        }
        textView.setTextSize(0, (i / f) * 1.3f);
    }

    public static void startTextColorChangeAnimation(int i, TextView textView) {
        if (textView != null) {
            textView.setTextColor(i);
        }
    }

    public final void addOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
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
        while (true) {
            size++;
            if (size >= size2) {
                break;
            } else {
                ((Tab) this.tabs.get(size)).position = size;
            }
        }
        TabView tabView = tab.view;
        tabView.setSelected(false);
        tabView.setActivated(false);
        SlidingTabIndicator slidingTabIndicator = this.slidingTabIndicator;
        int i = tab.position;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
        updateTabViewLayoutParams(layoutParams);
        slidingTabIndicator.addView(tabView, i, layoutParams);
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
            if (ViewCompat.Api19Impl.isLaidOut(this)) {
                SlidingTabIndicator slidingTabIndicator = this.slidingTabIndicator;
                int childCount = slidingTabIndicator.getChildCount();
                boolean z = false;
                int i2 = 0;
                while (true) {
                    if (i2 >= childCount) {
                        break;
                    }
                    if (slidingTabIndicator.getChildAt(i2).getWidth() <= 0) {
                        z = true;
                        break;
                    }
                    i2++;
                }
                if (!z) {
                    int scrollX = getScrollX();
                    int calculateScrollXForTab = calculateScrollXForTab(0.0f, i);
                    if (scrollX != calculateScrollXForTab) {
                        if (this.scrollAnimator == null) {
                            ValueAnimator valueAnimator = new ValueAnimator();
                            this.scrollAnimator = valueAnimator;
                            valueAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
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
        }
        setScrollPosition(0.0f, i, true, true);
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
        return ViewCompat.Api17Impl.getLayoutDirection(this) == 0 ? left + i4 : left - i4;
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

    public final int getSelectedTabPosition() {
        Tab tab = this.selectedTab;
        if (tab != null) {
            return tab.position;
        }
        return -1;
    }

    public final int getSelectedTabTextColor() {
        ColorStateList colorStateList = this.tabTextColors;
        if (colorStateList != null) {
            return colorStateList.getColorForState(new int[]{android.R.attr.state_selected, android.R.attr.state_enabled}, colorStateList.getDefaultColor());
        }
        return -1;
    }

    public final Tab getTabAt(int i) {
        if (i < 0 || i >= getTabCount()) {
            return null;
        }
        return (Tab) this.tabs.get(i);
    }

    public final int getTabCount() {
        return this.tabs.size();
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
        int i2 = tab.f463id;
        if (i2 != -1) {
            tabView.setId(i2);
        }
        return tab;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        TabView tabView;
        super.onAttachedToWindow();
        for (int i = 0; i < getTabCount(); i++) {
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
        for (int i = 0; i < getTabCount(); i++) {
            Tab tabAt = getTabAt(i);
            if (tabAt != null && (tabView = tabAt.view) != null && (view = tabView.mMainTabTouchBackground) != null) {
                view.setAlpha(0.0f);
            }
        }
        updateBadgePosition();
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
    public final void onDraw(Canvas canvas) {
        TabView tabView;
        Drawable drawable;
        for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++) {
            View childAt = this.slidingTabIndicator.getChildAt(i);
            if ((childAt instanceof TabView) && (drawable = (tabView = (TabView) childAt).baseBackgroundDrawable) != null) {
                drawable.setBounds(tabView.getLeft(), tabView.getTop(), tabView.getRight(), tabView.getBottom());
                tabView.baseBackgroundDrawable.draw(canvas);
            }
        }
        super.onDraw(canvas);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, getTabCount(), 1));
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int i = this.mode;
        return (i == 0 || i == 2) && super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateBadgePosition();
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
    
        if (r0 != 12) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0081, code lost:
    
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x008b, code lost:
    
        if (r9.getMeasuredWidth() != getMeasuredWidth()) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0096, code lost:
    
        if (r9.getMeasuredWidth() < getMeasuredWidth()) goto L39;
     */
    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMeasure(int i, int i2) {
        int round = Math.round(ViewUtils.dpToPx(this.mDepthStyle == 2 ? 56 : 60, getContext()));
        int mode = View.MeasureSpec.getMode(i2);
        boolean z = true;
        if (mode != Integer.MIN_VALUE) {
            if (mode == 0) {
                i2 = View.MeasureSpec.makeMeasureSpec(getPaddingBottom() + getPaddingTop() + round, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
            }
        } else if (getChildCount() == 1 && View.MeasureSpec.getSize(i2) >= round) {
            getChildAt(0).setMinimumHeight(round);
        }
        int size = View.MeasureSpec.getSize(i);
        if (View.MeasureSpec.getMode(i) != 0) {
            int i3 = this.requestedTabMaxWidth;
            if (i3 <= 0) {
                i3 = (int) (size - ViewUtils.dpToPx(56, getContext()));
            }
            this.tabMaxWidth = i3;
        }
        super.onMeasure(i, i2);
        if (getChildCount() != 1) {
            return;
        }
        View childAt = getChildAt(0);
        int i4 = this.mode;
        if (i4 != 0) {
            if (i4 != 1) {
                if (i4 != 2) {
                    if (i4 != 11) {
                    }
                    if (z) {
                        childAt.measure(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), HorizontalScrollView.getChildMeasureSpec(i2, getPaddingBottom() + getPaddingTop(), childAt.getLayoutParams().height));
                    }
                    checkOverScreen();
                    if (!this.mIsOverScreen || getChildAt(0).getMeasuredWidth() >= getMeasuredWidth()) {
                        setPaddingRelative(0, 0, 0, 0);
                    } else {
                        setPaddingRelative((getMeasuredWidth() - getChildAt(0).getMeasuredWidth()) / 2, 0, 0, 0);
                    }
                }
            }
        }
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 8) {
            int i = this.mode;
            if (!(i == 0 || i == 2)) {
                return false;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        TabView tabView;
        View view2;
        super.onVisibilityChanged(view, i);
        for (int i2 = 0; i2 < getTabCount(); i2++) {
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
            if (viewPager == null || count <= 0 || (currentItem = viewPager.getCurrentItem()) == getSelectedTabPosition() || currentItem >= getTabCount()) {
                return;
            }
            selectTab(getTabAt(currentItem), true);
        }
    }

    public final void removeAllTabs() {
        for (int childCount = this.slidingTabIndicator.getChildCount() - 1; childCount >= 0; childCount--) {
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
            tab.f463id = -1;
            tab.text = null;
            tab.contentDesc = null;
            tab.position = -1;
            tab.customView = null;
            tab.subText = null;
            tabPool.release(tab);
        }
        this.selectedTab = null;
    }

    public final void selectTab(Tab tab, boolean z) {
        ViewPager viewPager;
        if (tab != null && !tab.view.isEnabled() && (viewPager = this.viewPager) != null) {
            viewPager.setCurrentItem(getSelectedTabPosition());
            return;
        }
        Tab tab2 = this.selectedTab;
        if (tab2 == tab) {
            if (tab2 != null) {
                for (int size = this.selectedListeners.size() - 1; size >= 0; size--) {
                    ((BaseOnTabSelectedListener) this.selectedListeners.get(size)).onTabReselected();
                }
                animateToTab(tab.position);
                return;
            }
            return;
        }
        int i = tab != null ? tab.position : -1;
        if (z) {
            if ((tab2 == null || tab2.position == -1) && i != -1) {
                setScrollPosition(0.0f, i, true, true);
            } else {
                animateToTab(i);
            }
            if (i != -1) {
                setSelectedTabView(i);
            }
        }
        this.selectedTab = tab;
        if (tab2 != null) {
            for (int size2 = this.selectedListeners.size() - 1; size2 >= 0; size2--) {
                ((BaseOnTabSelectedListener) this.selectedListeners.get(size2)).onTabUnselected();
            }
        }
        if (tab != null) {
            for (int size3 = this.selectedListeners.size() - 1; size3 >= 0; size3--) {
                ((BaseOnTabSelectedListener) this.selectedListeners.get(size3)).onTabSelected(tab);
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
                    newTab.subText = ((Tab) this.tabs.get(i)).subText;
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
        MaterialShapeUtils.setElevation(this, f);
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

    public final void setScrollPosition(float f, int i, boolean z, boolean z2) {
        int round = Math.round(i + f);
        if (round < 0 || round >= this.slidingTabIndicator.getChildCount()) {
            return;
        }
        if (z2) {
            SlidingTabIndicator slidingTabIndicator = this.slidingTabIndicator;
            View childAt = slidingTabIndicator.getChildAt(i);
            View childAt2 = slidingTabIndicator.getChildAt(i + 1);
            if (childAt != null && childAt.getWidth() > 0) {
                TabLayout tabLayout = TabLayout.this;
                tabLayout.tabIndicatorInterpolator.updateIndicatorForOffset(tabLayout, childAt, childAt2, f, tabLayout.tabSelectedIndicator);
            } else {
                Drawable drawable = TabLayout.this.tabSelectedIndicator;
                drawable.setBounds(-1, drawable.getBounds().top, -1, TabLayout.this.tabSelectedIndicator.getBounds().bottom);
            }
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postInvalidateOnAnimation(slidingTabIndicator);
        }
        ValueAnimator valueAnimator = this.scrollAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.scrollAnimator.cancel();
        }
        scrollTo(i < 0 ? 0 : calculateScrollXForTab(f, i), 0);
        if (z) {
            setSelectedTabView(round);
        }
    }

    public final void setSelectedTabView(int i) {
        TextView textView;
        TextView textView2;
        int childCount = this.slidingTabIndicator.getChildCount();
        if (i < childCount) {
            int i2 = 0;
            while (true) {
                boolean z = true;
                if (i2 >= childCount) {
                    break;
                }
                View childAt = this.slidingTabIndicator.getChildAt(i2);
                childAt.setSelected(i2 == i);
                if (i2 != i) {
                    z = false;
                }
                childAt.setActivated(z);
                i2++;
            }
            ((Tab) this.tabs.get(i)).view.setSelected(true);
            for (int i3 = 0; i3 < getTabCount(); i3++) {
                TabView tabView = ((Tab) this.tabs.get(i3)).view;
                if (i3 == i) {
                    TextView textView3 = tabView.textView;
                    if (textView3 != null) {
                        startTextColorChangeAnimation(getSelectedTabTextColor(), textView3);
                        tabView.textView.setTypeface(this.mBoldTypeface);
                        tabView.textView.setSelected(true);
                    }
                    if (this.mDepthStyle == 2 && (textView2 = tabView.mSubTextView) != null) {
                        ColorStateList colorStateList = this.mSubTabSubTextColors;
                        startTextColorChangeAnimation(colorStateList != null ? colorStateList.getColorForState(new int[]{android.R.attr.state_selected, android.R.attr.state_enabled}, colorStateList.getDefaultColor()) : -1, textView2);
                        tabView.mSubTextView.setSelected(true);
                    }
                    SeslAbsIndicatorView seslAbsIndicatorView = tabView.mIndicatorView;
                    if (seslAbsIndicatorView != null && seslAbsIndicatorView.getAlpha() != 1.0f) {
                        tabView.mIndicatorView.onShow();
                    }
                } else {
                    SeslAbsIndicatorView seslAbsIndicatorView2 = tabView.mIndicatorView;
                    if (seslAbsIndicatorView2 != null) {
                        seslAbsIndicatorView2.onHide();
                    }
                    TextView textView4 = tabView.textView;
                    if (textView4 != null) {
                        textView4.setTypeface(this.mNormalTypeface);
                        startTextColorChangeAnimation(this.tabTextColors.getDefaultColor(), tabView.textView);
                        tabView.textView.setSelected(false);
                    }
                    if (this.mDepthStyle == 2 && (textView = tabView.mSubTextView) != null) {
                        startTextColorChangeAnimation(this.mSubTabSubTextColors.getDefaultColor(), textView);
                        tabView.mSubTextView.setSelected(false);
                    }
                }
            }
        }
    }

    public final void setupWithViewPager(ViewPager viewPager, boolean z) {
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
            addOnTabSelectedListener(viewPagerOnTabSelectedListener2);
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
            setScrollPosition(0.0f, viewPager.getCurrentItem(), true, true);
        } else {
            this.viewPager = null;
            setPagerAdapter(null, false);
        }
        this.setupViewPagerImplicitly = z;
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return Math.max(0, ((this.slidingTabIndicator.getWidth() - getWidth()) - getPaddingLeft()) - getPaddingRight()) > 0;
    }

    public final void updateBadgePosition() {
        ArrayList arrayList = this.tabs;
        if (arrayList == null || arrayList.size() == 0) {
            return;
        }
        for (int i = 0; i < this.tabs.size(); i++) {
            Tab tab = (Tab) this.tabs.get(i);
            TabView tabView = ((Tab) this.tabs.get(i)).view;
            if (tab != null && tabView != null && tabView.getWidth() > 0) {
                getContext().getResources().getDimensionPixelSize(R.dimen.sesl_tablayout_subtab_n_badge_xoffset);
            }
        }
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
        updateBadgePosition();
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

    /* JADX WARN: Code restructure failed: missing block: B:55:0x031c, code lost:
    
        if (r13 != 12) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0334, code lost:
    
        if (r13 != 2) goto L68;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public TabLayout(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132018828), attributeSet, i);
        int i2;
        ArrayList arrayList = new ArrayList();
        this.tabs = arrayList;
        this.tabMaxWidth = Integer.MAX_VALUE;
        this.selectedListeners = new ArrayList();
        this.tabViewPool = new Pools$SimplePool(12);
        this.mDepthStyle = 1;
        this.mIsScaledTextSizeType = false;
        this.mIconTextGap = -1;
        this.mRequestedTabWidth = -1;
        this.mIsOverScreen = false;
        this.mOverScreenMaxWidth = -1;
        this.mSubTabSelectedIndicatorColor = -1;
        this.mSubTabIndicatorHeight = 1;
        this.mSubTabIndicator2ndHeight = 1;
        Context context2 = getContext();
        setHorizontalScrollBarEnabled(false);
        SlidingTabIndicator slidingTabIndicator = new SlidingTabIndicator(context2);
        this.slidingTabIndicator = slidingTabIndicator;
        super.addView(slidingTabIndicator, 0, new FrameLayout.LayoutParams(-2, -1));
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, R$styleable.TabLayout, i, SeslMisc.isLightTheme(context2) ? 2132018829 : 2132018828);
        if (getBackground() instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) getBackground();
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
            materialShapeDrawable.setFillColor(ColorStateList.valueOf(colorDrawable.getColor()));
            materialShapeDrawable.initializeElevationOverlay(context2);
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            materialShapeDrawable.setElevation(ViewCompat.Api21Impl.getElevation(this));
            ViewCompat.Api16Impl.setBackground(this, materialShapeDrawable);
        }
        Drawable drawable = MaterialResources.getDrawable(context2, obtainStyledAttributes, 8);
        Drawable mutate = (drawable == null ? new GradientDrawable() : drawable).mutate();
        this.tabSelectedIndicator = mutate;
        mutate.setTintList(null);
        int intrinsicHeight = this.tabSelectedIndicator.getIntrinsicHeight();
        Rect bounds = TabLayout.this.tabSelectedIndicator.getBounds();
        TabLayout.this.tabSelectedIndicator.setBounds(bounds.left, 0, bounds.right, intrinsicHeight);
        slidingTabIndicator.requestLayout();
        int color = obtainStyledAttributes.getColor(11, 0);
        updateTabViews(false);
        this.mTabSelectedIndicatorColor = color;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            SeslAbsIndicatorView seslAbsIndicatorView = ((Tab) it.next()).view.mIndicatorView;
            if (seslAbsIndicatorView != null) {
                if (this.mDepthStyle == 2 && (i2 = this.mSubTabSelectedIndicatorColor) != -1) {
                    seslAbsIndicatorView.onSetSelectedIndicatorColor(i2);
                } else {
                    seslAbsIndicatorView.onSetSelectedIndicatorColor(color);
                }
                seslAbsIndicatorView.invalidate();
            }
        }
        SlidingTabIndicator slidingTabIndicator2 = this.slidingTabIndicator;
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(14, -1);
        Rect bounds2 = TabLayout.this.tabSelectedIndicator.getBounds();
        TabLayout.this.tabSelectedIndicator.setBounds(bounds2.left, 0, bounds2.right, dimensionPixelSize);
        slidingTabIndicator2.requestLayout();
        this.mTabSelectedIndicatorColor = obtainStyledAttributes.getColor(11, 0);
        int i3 = obtainStyledAttributes.getInt(13, 0);
        if (this.tabIndicatorGravity != i3) {
            this.tabIndicatorGravity = i3;
            SlidingTabIndicator slidingTabIndicator3 = this.slidingTabIndicator;
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postInvalidateOnAnimation(slidingTabIndicator3);
        }
        int i4 = obtainStyledAttributes.getInt(10, 0);
        if (i4 == 0) {
            this.tabIndicatorInterpolator = new TabIndicatorInterpolator();
        } else if (i4 == 1) {
            this.tabIndicatorInterpolator = new ElasticTabIndicatorInterpolator();
        } else if (i4 == 2) {
            this.tabIndicatorInterpolator = new FadeTabIndicatorInterpolator();
        } else {
            throw new IllegalArgumentException(i4 + " is not a valid TabIndicatorAnimationMode");
        }
        this.tabIndicatorFullWidth = obtainStyledAttributes.getBoolean(12, true);
        SlidingTabIndicator slidingTabIndicator4 = this.slidingTabIndicator;
        int i5 = SlidingTabIndicator.$r8$clinit;
        slidingTabIndicator4.getClass();
        SlidingTabIndicator slidingTabIndicator5 = this.slidingTabIndicator;
        WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.postInvalidateOnAnimation(slidingTabIndicator5);
        int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(19, 0);
        this.tabPaddingBottom = dimensionPixelSize2;
        this.tabPaddingTop = dimensionPixelSize2;
        obtainStyledAttributes.getDimensionPixelSize(22, dimensionPixelSize2);
        this.tabPaddingTop = obtainStyledAttributes.getDimensionPixelSize(23, dimensionPixelSize2);
        obtainStyledAttributes.getDimensionPixelSize(21, dimensionPixelSize2);
        this.tabPaddingBottom = obtainStyledAttributes.getDimensionPixelSize(20, dimensionPixelSize2);
        int resourceId = obtainStyledAttributes.getResourceId(26, 2132018141);
        this.tabTextAppearance = resourceId;
        int[] iArr = androidx.appcompat.R$styleable.TextAppearance;
        TypedArray obtainStyledAttributes2 = context2.obtainStyledAttributes(resourceId, iArr);
        this.tabTextSize = obtainStyledAttributes2.getDimensionPixelSize(0, 0);
        this.mIsScaledTextSizeType = obtainStyledAttributes2.getText(0).toString().contains("sp");
        this.tabTextColors = MaterialResources.getColorStateList(context2, obtainStyledAttributes2, 3);
        Resources resources = getResources();
        this.mMaxTouchSlop = resources.getDisplayMetrics().widthPixels;
        int scaledTouchSlop = ViewConfiguration.get(context2).getScaledTouchSlop();
        this.mDefaultTouchSlop = scaledTouchSlop;
        this.mCurrentTouchSlop = scaledTouchSlop;
        Typeface create = Typeface.create("sec", 0);
        this.mBoldTypeface = Typeface.create(create, VolteConstants.ErrorCode.BUSY_EVERYWHERE, false);
        this.mNormalTypeface = Typeface.create(create, 400, false);
        this.mSubTabIndicatorHeight = resources.getDimensionPixelSize(R.dimen.sesl_tablayout_subtab_indicator_height);
        this.mSubTabIndicator2ndHeight = resources.getDimensionPixelSize(R.dimen.sesl_tablayout_subtab_indicator_2nd_height);
        this.mTabMinSideSpace = resources.getDimensionPixelSize(R.dimen.sesl_tab_min_side_space);
        int resourceId2 = obtainStyledAttributes.getResourceId(1, 2132018143);
        this.mSubTabSubTextAppearance = resourceId2;
        TypedArray obtainStyledAttributes3 = context2.obtainStyledAttributes(resourceId2, iArr);
        try {
            this.mSubTabSubTextColors = MaterialResources.getColorStateList(context2, obtainStyledAttributes3, 3);
            this.mSubTabTextSize = obtainStyledAttributes3.getDimensionPixelSize(0, 0);
            obtainStyledAttributes2.recycle();
            obtainStyledAttributes3.recycle();
            if (obtainStyledAttributes.hasValue(2)) {
                this.mSubTabSubTextColors = MaterialResources.getColorStateList(context2, obtainStyledAttributes, 2);
            }
            if (obtainStyledAttributes.hasValue(0)) {
                this.mSubTabSubTextColors = new ColorStateList(new int[][]{HorizontalScrollView.SELECTED_STATE_SET, HorizontalScrollView.EMPTY_STATE_SET}, new int[]{obtainStyledAttributes.getColor(0, 0), this.mSubTabSubTextColors.getDefaultColor()});
            }
            if (obtainStyledAttributes.hasValue(27)) {
                this.tabTextColors = MaterialResources.getColorStateList(context2, obtainStyledAttributes, 27);
            }
            if (obtainStyledAttributes.hasValue(25)) {
                this.tabTextColors = new ColorStateList(new int[][]{HorizontalScrollView.SELECTED_STATE_SET, HorizontalScrollView.EMPTY_STATE_SET}, new int[]{obtainStyledAttributes.getColor(25, 0), this.tabTextColors.getDefaultColor()});
            }
            this.tabIconTint = MaterialResources.getColorStateList(context2, obtainStyledAttributes, 6);
            this.tabIconTintMode = ViewUtils.parseTintMode(obtainStyledAttributes.getInt(7, -1), null);
            this.tabRippleColorStateList = MaterialResources.getColorStateList(context2, obtainStyledAttributes, 24);
            this.tabIndicatorAnimationDuration = obtainStyledAttributes.getInt(9, 300);
            this.requestedTabMinWidth = obtainStyledAttributes.getDimensionPixelSize(17, -1);
            this.requestedTabMaxWidth = obtainStyledAttributes.getDimensionPixelSize(16, -1);
            this.tabBackgroundResId = obtainStyledAttributes.getResourceId(3, 0);
            obtainStyledAttributes.getDimensionPixelSize(4, 0);
            int i6 = obtainStyledAttributes.getInt(18, 1);
            this.mode = i6;
            int i7 = obtainStyledAttributes.getInt(5, 0);
            this.tabGravity = i7;
            this.mFirstTabGravity = i7;
            this.inlineLabel = obtainStyledAttributes.getBoolean(15, false);
            obtainStyledAttributes.getBoolean(28, false);
            obtainStyledAttributes.recycle();
            this.tabTextMultiLineSize = resources.getDimensionPixelSize(R.dimen.sesl_tab_text_size_2line);
            resources.getDimensionPixelSize(R.dimen.sesl_tab_scrollable_min_width);
            ViewCompat.Api17Impl.setPaddingRelative(this.slidingTabIndicator, 0, 0, 0, 0);
            if (i6 != 0) {
                if (i6 == 1 || i6 == 2) {
                    if (this.tabGravity == 2) {
                        Log.w("TabLayout", "GRAVITY_START is not supported with the current tab mode, GRAVITY_CENTER will be used instead");
                    }
                    this.slidingTabIndicator.setGravity(1);
                } else if (i6 != 11) {
                }
                updateTabViews(true);
            }
            int i8 = this.tabGravity;
            if (i8 == 0) {
                Log.w("TabLayout", "MODE_SCROLLABLE + GRAVITY_FILL is not supported, GRAVITY_START will be used instead");
            } else {
                if (i8 == 1) {
                    this.slidingTabIndicator.setGravity(1);
                }
                updateTabViews(true);
            }
            this.slidingTabIndicator.setGravity(8388611);
            updateTabViews(true);
        } catch (Throwable th) {
            obtainStyledAttributes2.recycle();
            obtainStyledAttributes3.recycle();
            throw th;
        }
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup, android.view.ViewManager
    public final void addView(View view, ViewGroup.LayoutParams layoutParams) {
        addViewInternal(view);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        addViewInternal(view);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ViewPagerOnTabSelectedListener implements OnTabSelectedListener {
        public final ViewPager viewPager;

        public ViewPagerOnTabSelectedListener(ViewPager viewPager) {
            this.viewPager = viewPager;
        }

        @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
        public final void onTabSelected(Tab tab) {
            this.viewPager.setCurrentItem(tab.position);
        }

        @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
        public final void onTabReselected() {
        }

        @Override // com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener
        public final void onTabUnselected() {
        }
    }
}
