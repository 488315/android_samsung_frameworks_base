package androidx.appcompat.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import androidx.appcompat.R$styleable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.appcompat.widget.LinearLayoutCompat;
import com.android.systemui.R;
import com.samsung.systemui.splugins.volume.VolumePanelValues;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ScrollingTabContainerView extends HorizontalScrollView implements AdapterView.OnItemSelectedListener {
    public boolean mAllowCollapse;
    public int mContentHeight;
    public int mMaxTabWidth;
    public int mSelectedTabIndex;
    public final LinearLayoutCompat mTabLayout;
    public RunnableC00691 mTabSelector;
    public AppCompatSpinner mTabSpinner;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TabAdapter extends BaseAdapter {
        public TabAdapter() {
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return ScrollingTabContainerView.this.mTabLayout.getChildCount();
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return ((TabView) ScrollingTabContainerView.this.mTabLayout.getChildAt(i)).mTab;
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            if (view != null) {
                TabView tabView = (TabView) view;
                tabView.mTab = (ActionBar.Tab) getItem(i);
                tabView.update();
                return view;
            }
            ScrollingTabContainerView scrollingTabContainerView = ScrollingTabContainerView.this;
            ActionBar.Tab tab = (ActionBar.Tab) getItem(i);
            scrollingTabContainerView.getClass();
            TabView tabView2 = scrollingTabContainerView.new TabView(scrollingTabContainerView.getContext(), tab, true);
            tabView2.setBackgroundDrawable(null);
            tabView2.setLayoutParams(new AbsListView.LayoutParams(-1, scrollingTabContainerView.mContentHeight));
            return tabView2;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TabView extends LinearLayout {
        public View mCustomView;
        public AppCompatImageView mIconView;
        public ActionBar.Tab mTab;
        public AppCompatTextView mTextView;

        public TabView(Context context, ActionBar.Tab tab, boolean z) {
            super(context, null, R.attr.actionBarTabStyle);
            int[] iArr = {android.R.attr.background};
            this.mTab = tab;
            TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, null, iArr, R.attr.actionBarTabStyle, 0);
            if (obtainStyledAttributes.hasValue(0)) {
                setBackgroundDrawable(obtainStyledAttributes.getDrawable(0));
            }
            obtainStyledAttributes.recycle();
            if (z) {
                setGravity(8388627);
            }
            update();
        }

        @Override // android.view.View
        public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName("androidx.appcompat.app.ActionBar$Tab");
        }

        @Override // android.view.View
        public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName("androidx.appcompat.app.ActionBar$Tab");
        }

        @Override // android.widget.LinearLayout, android.view.View
        public final void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            if (ScrollingTabContainerView.this.mMaxTabWidth > 0) {
                int measuredWidth = getMeasuredWidth();
                int i3 = ScrollingTabContainerView.this.mMaxTabWidth;
                if (measuredWidth > i3) {
                    super.onMeasure(View.MeasureSpec.makeMeasureSpec(i3, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), i2);
                }
            }
        }

        @Override // android.view.View
        public final void setSelected(boolean z) {
            boolean z2 = isSelected() != z;
            super.setSelected(z);
            if (z2 && z) {
                sendAccessibilityEvent(4);
            }
        }

        public final void update() {
            ActionBar.Tab tab = this.mTab;
            View customView = tab.getCustomView();
            if (customView != null) {
                ViewParent parent = customView.getParent();
                if (parent != this) {
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(customView);
                    }
                    addView(customView);
                }
                this.mCustomView = customView;
                AppCompatTextView appCompatTextView = this.mTextView;
                if (appCompatTextView != null) {
                    appCompatTextView.setVisibility(8);
                }
                AppCompatImageView appCompatImageView = this.mIconView;
                if (appCompatImageView != null) {
                    appCompatImageView.setVisibility(8);
                    this.mIconView.setImageDrawable(null);
                    return;
                }
                return;
            }
            View view = this.mCustomView;
            if (view != null) {
                removeView(view);
                this.mCustomView = null;
            }
            Drawable icon = tab.getIcon();
            CharSequence text = tab.getText();
            if (icon != null) {
                if (this.mIconView == null) {
                    AppCompatImageView appCompatImageView2 = new AppCompatImageView(getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams.gravity = 16;
                    appCompatImageView2.setLayoutParams(layoutParams);
                    addView(appCompatImageView2, 0);
                    this.mIconView = appCompatImageView2;
                }
                this.mIconView.setImageDrawable(icon);
                this.mIconView.setVisibility(0);
            } else {
                AppCompatImageView appCompatImageView3 = this.mIconView;
                if (appCompatImageView3 != null) {
                    appCompatImageView3.setVisibility(8);
                    this.mIconView.setImageDrawable(null);
                }
            }
            boolean z = !TextUtils.isEmpty(text);
            if (z) {
                if (this.mTextView == null) {
                    AppCompatTextView appCompatTextView2 = new AppCompatTextView(getContext(), null, R.attr.actionBarTabTextStyle);
                    appCompatTextView2.setEllipsize(TextUtils.TruncateAt.END);
                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams2.gravity = 16;
                    appCompatTextView2.setLayoutParams(layoutParams2);
                    addView(appCompatTextView2);
                    this.mTextView = appCompatTextView2;
                }
                this.mTextView.setText(text);
                this.mTextView.setVisibility(0);
            } else {
                AppCompatTextView appCompatTextView3 = this.mTextView;
                if (appCompatTextView3 != null) {
                    appCompatTextView3.setVisibility(8);
                    this.mTextView.setText((CharSequence) null);
                }
            }
            AppCompatImageView appCompatImageView4 = this.mIconView;
            if (appCompatImageView4 != null) {
                appCompatImageView4.setContentDescription(tab.getContentDescription());
            }
            setTooltipText(z ? null : tab.getContentDescription());
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class VisibilityAnimListener extends AnimatorListenerAdapter {
        public boolean mCanceled = false;

        public VisibilityAnimListener() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            this.mCanceled = true;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            if (this.mCanceled) {
                return;
            }
            ScrollingTabContainerView.this.getClass();
            ScrollingTabContainerView.this.setVisibility(0);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            ScrollingTabContainerView.this.setVisibility(0);
            this.mCanceled = false;
        }
    }

    static {
        new DecelerateInterpolator();
    }

    public ScrollingTabContainerView(Context context) {
        super(context);
        new VisibilityAnimListener();
        setHorizontalScrollBarEnabled(false);
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(context);
        TypedArray obtainStyledAttributes = actionBarPolicy.mContext.obtainStyledAttributes(null, R$styleable.ActionBar, R.attr.actionBarStyle, 0);
        int layoutDimension = obtainStyledAttributes.getLayoutDimension(13, 0);
        obtainStyledAttributes.recycle();
        this.mContentHeight = layoutDimension;
        requestLayout();
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(getContext(), null, R.attr.actionBarTabBarStyle);
        linearLayoutCompat.mUseLargestChild = true;
        if (linearLayoutCompat.mGravity != 17) {
            linearLayoutCompat.mGravity = 17;
            linearLayoutCompat.requestLayout();
        }
        linearLayoutCompat.setLayoutParams(new LinearLayoutCompat.LayoutParams(-2, -1));
        this.mTabLayout = linearLayoutCompat;
        addView(linearLayoutCompat, new ViewGroup.LayoutParams(-2, -1));
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        RunnableC00691 runnableC00691 = this.mTabSelector;
        if (runnableC00691 != null) {
            post(runnableC00691);
        }
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(getContext());
        TypedArray obtainStyledAttributes = actionBarPolicy.mContext.obtainStyledAttributes(null, R$styleable.ActionBar, R.attr.actionBarStyle, 0);
        int layoutDimension = obtainStyledAttributes.getLayoutDimension(13, 0);
        obtainStyledAttributes.recycle();
        this.mContentHeight = layoutDimension;
        requestLayout();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        RunnableC00691 runnableC00691 = this.mTabSelector;
        if (runnableC00691 != null) {
            removeCallbacks(runnableC00691);
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onItemSelected(AdapterView adapterView, View view, int i, long j) {
        ((TabView) view).mTab.select();
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        boolean z = mode == 1073741824;
        setFillViewport(z);
        int childCount = this.mTabLayout.getChildCount();
        if (childCount <= 1 || !(mode == 1073741824 || mode == Integer.MIN_VALUE)) {
            this.mMaxTabWidth = -1;
        } else {
            if (childCount > 2) {
                this.mMaxTabWidth = (int) (View.MeasureSpec.getSize(i) * 0.4f);
            } else {
                this.mMaxTabWidth = View.MeasureSpec.getSize(i) / 2;
            }
            this.mMaxTabWidth = Math.min(this.mMaxTabWidth, 0);
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mContentHeight, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        if (!z && this.mAllowCollapse) {
            this.mTabLayout.measure(0, makeMeasureSpec);
            if (this.mTabLayout.getMeasuredWidth() > View.MeasureSpec.getSize(i)) {
                AppCompatSpinner appCompatSpinner = this.mTabSpinner;
                if (!(appCompatSpinner != null && appCompatSpinner.getParent() == this)) {
                    if (this.mTabSpinner == null) {
                        AppCompatSpinner appCompatSpinner2 = new AppCompatSpinner(getContext(), null, R.attr.actionDropDownStyle);
                        appCompatSpinner2.setLayoutParams(new LinearLayoutCompat.LayoutParams(-2, -1));
                        appCompatSpinner2.setOnItemSelectedListener(this);
                        this.mTabSpinner = appCompatSpinner2;
                    }
                    removeView(this.mTabLayout);
                    addView(this.mTabSpinner, new ViewGroup.LayoutParams(-2, -1));
                    if (this.mTabSpinner.getAdapter() == null) {
                        this.mTabSpinner.setAdapter((SpinnerAdapter) new TabAdapter());
                    }
                    RunnableC00691 runnableC00691 = this.mTabSelector;
                    if (runnableC00691 != null) {
                        removeCallbacks(runnableC00691);
                        this.mTabSelector = null;
                    }
                    this.mTabSpinner.setSelection(this.mSelectedTabIndex);
                }
            } else {
                performExpand();
            }
        } else {
            performExpand();
        }
        int measuredWidth = getMeasuredWidth();
        super.onMeasure(i, makeMeasureSpec);
        int measuredWidth2 = getMeasuredWidth();
        if (!z || measuredWidth == measuredWidth2) {
            return;
        }
        setTabSelected(this.mSelectedTabIndex);
    }

    public final void performExpand() {
        AppCompatSpinner appCompatSpinner = this.mTabSpinner;
        if (appCompatSpinner != null && appCompatSpinner.getParent() == this) {
            removeView(this.mTabSpinner);
            addView(this.mTabLayout, new ViewGroup.LayoutParams(-2, -1));
            setTabSelected(this.mTabSpinner.getSelectedItemPosition());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v3, types: [androidx.appcompat.widget.ScrollingTabContainerView$1, java.lang.Runnable] */
    public final void setTabSelected(int i) {
        this.mSelectedTabIndex = i;
        int childCount = this.mTabLayout.getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            View childAt = this.mTabLayout.getChildAt(i2);
            boolean z = i2 == i;
            childAt.setSelected(z);
            if (z) {
                final View childAt2 = this.mTabLayout.getChildAt(i);
                RunnableC00691 runnableC00691 = this.mTabSelector;
                if (runnableC00691 != null) {
                    removeCallbacks(runnableC00691);
                }
                ?? r4 = new Runnable() { // from class: androidx.appcompat.widget.ScrollingTabContainerView.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ScrollingTabContainerView.this.smoothScrollTo(childAt2.getLeft() - ((ScrollingTabContainerView.this.getWidth() - childAt2.getWidth()) / 2), 0);
                        ScrollingTabContainerView.this.mTabSelector = null;
                    }
                };
                this.mTabSelector = r4;
                post(r4);
            }
            i2++;
        }
        AppCompatSpinner appCompatSpinner = this.mTabSpinner;
        if (appCompatSpinner == null || i < 0) {
            return;
        }
        appCompatSpinner.setSelection(i);
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onNothingSelected(AdapterView adapterView) {
    }
}
