package com.android.internal.widget;

import android.animation.LayoutTransition;
import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.CollapsibleActionView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ActionMenuPresenter;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.view.menu.ActionMenuItem;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import com.android.internal.view.menu.SubMenuBuilder;

/* loaded from: classes6.dex */
public class ActionBarView extends AbsActionBarView implements DecorToolbar {
    private static final int DEFAULT_CUSTOM_GRAVITY = 8388627;
    public static final int DISPLAY_DEFAULT = 0;
    private static final int DISPLAY_RELAYOUT_MASK = 63;
    private static final String TAG = "ActionBarView";
    private ActionBarContextView mContextView;
    private View mCustomNavView;
    private int mDefaultUpDescription;
    private int mDisplayOptions;
    View mExpandedActionView;
    private final View.OnClickListener mExpandedActionViewUpListener;
    private HomeView mExpandedHomeLayout;
    private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private CharSequence mHomeDescription;
    private int mHomeDescriptionRes;
    private HomeView mHomeLayout;
    private Drawable mIcon;
    private boolean mIncludeTabs;
    private final int mIndeterminateProgressStyle;
    private ProgressBar mIndeterminateProgressView;
    private boolean mIsCollapsible;
    private int mItemPadding;
    private LinearLayout mListNavLayout;
    private Drawable mLogo;
    private ActionMenuItem mLogoNavItem;
    private boolean mMenuPrepared;
    private AdapterView.OnItemSelectedListener mNavItemSelectedListener;
    private int mNavigationMode;
    private MenuBuilder mOptionsMenu;
    private int mProgressBarPadding;
    private final int mProgressStyle;
    private ProgressBar mProgressView;
    private Spinner mSpinner;
    private SpinnerAdapter mSpinnerAdapter;
    private CharSequence mSubtitle;
    private final int mSubtitleStyleRes;
    private TextView mSubtitleView;
    private ScrollingTabContainerView mTabScrollView;
    private Runnable mTabSelector;
    private CharSequence mTitle;
    private LinearLayout mTitleLayout;
    private final int mTitleStyleRes;
    private TextView mTitleView;
    private final View.OnClickListener mUpClickListener;
    private ViewGroup mUpGoerFive;
    private boolean mUserTitle;
    private boolean mWasHomeEnabled;
    Window.Callback mWindowCallback;

    @Override // com.android.internal.widget.DecorToolbar
    public boolean canSplit() {
        return true;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public ViewGroup getViewGroup() {
        return this;
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public ActionBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDisplayOptions = -1;
        this.mDefaultUpDescription = R.string.action_bar_up_description;
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.internal.widget.ActionBarView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MenuItemImpl menuItemImpl = ActionBarView.this.mExpandedMenuPresenter.mCurrentExpandedItem;
                if (menuItemImpl != null) {
                    menuItemImpl.collapseActionView();
                }
            }
        };
        this.mExpandedActionViewUpListener = onClickListener;
        View.OnClickListener onClickListener2 = new View.OnClickListener() { // from class: com.android.internal.widget.ActionBarView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ActionBarView.this.mMenuPrepared) {
                    ActionBarView.this.mWindowCallback.onMenuItemSelected(0, ActionBarView.this.mLogoNavItem);
                }
            }
        };
        this.mUpClickListener = onClickListener2;
        setBackgroundResource(0);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ActionBar, 16843470, 0);
        this.mNavigationMode = obtainStyledAttributes.getInt(7, 0);
        this.mTitle = obtainStyledAttributes.getText(5);
        this.mSubtitle = obtainStyledAttributes.getText(9);
        this.mLogo = obtainStyledAttributes.getDrawable(6);
        this.mIcon = obtainStyledAttributes.getDrawable(0);
        LayoutInflater from = LayoutInflater.from(context);
        int resourceId = obtainStyledAttributes.getResourceId(16, R.layout.action_bar_home);
        ViewGroup viewGroup = (ViewGroup) from.inflate(R.layout.action_bar_up_container, (ViewGroup) this, false);
        this.mUpGoerFive = viewGroup;
        this.mHomeLayout = (HomeView) from.inflate(resourceId, viewGroup, false);
        HomeView homeView = (HomeView) from.inflate(resourceId, this.mUpGoerFive, false);
        this.mExpandedHomeLayout = homeView;
        homeView.setShowUp(true);
        this.mExpandedHomeLayout.setOnClickListener(onClickListener);
        this.mExpandedHomeLayout.setContentDescription(getResources().getText(this.mDefaultUpDescription));
        Drawable background = this.mUpGoerFive.getBackground();
        if (background != null) {
            this.mExpandedHomeLayout.setBackground(background.getConstantState().newDrawable());
        }
        this.mExpandedHomeLayout.setEnabled(true);
        this.mExpandedHomeLayout.setFocusable(true);
        this.mTitleStyleRes = obtainStyledAttributes.getResourceId(11, 0);
        this.mSubtitleStyleRes = obtainStyledAttributes.getResourceId(12, 0);
        this.mProgressStyle = obtainStyledAttributes.getResourceId(1, 0);
        this.mIndeterminateProgressStyle = obtainStyledAttributes.getResourceId(14, 0);
        this.mProgressBarPadding = obtainStyledAttributes.getDimensionPixelOffset(15, 0);
        this.mItemPadding = obtainStyledAttributes.getDimensionPixelOffset(17, 0);
        setDisplayOptions(obtainStyledAttributes.getInt(8, 0));
        int resourceId2 = obtainStyledAttributes.getResourceId(10, 0);
        if (resourceId2 != 0) {
            this.mCustomNavView = from.inflate(resourceId2, (ViewGroup) this, false);
            this.mNavigationMode = 0;
            setDisplayOptions(this.mDisplayOptions | 16);
        }
        this.mContentHeight = obtainStyledAttributes.getLayoutDimension(4, 0);
        obtainStyledAttributes.recycle();
        this.mLogoNavItem = new ActionMenuItem(context, 0, 16908332, 0, 0, this.mTitle);
        this.mUpGoerFive.setOnClickListener(onClickListener2);
        this.mUpGoerFive.setClickable(true);
        this.mUpGoerFive.setFocusable(true);
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
    }

    @Override // com.android.internal.widget.AbsActionBarView, android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mTitleView = null;
        this.mSubtitleView = null;
        LinearLayout linearLayout = this.mTitleLayout;
        if (linearLayout != null) {
            ViewParent parent = linearLayout.getParent();
            ViewGroup viewGroup = this.mUpGoerFive;
            if (parent == viewGroup) {
                viewGroup.removeView(this.mTitleLayout);
            }
        }
        this.mTitleLayout = null;
        if ((this.mDisplayOptions & 8) != 0) {
            initTitle();
        }
        int i = this.mHomeDescriptionRes;
        if (i != 0) {
            setNavigationContentDescription(i);
        }
        ScrollingTabContainerView scrollingTabContainerView = this.mTabScrollView;
        if (scrollingTabContainerView == null || !this.mIncludeTabs) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = scrollingTabContainerView.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = -2;
            layoutParams.height = -1;
        }
        this.mTabScrollView.setAllowCollapse(true);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setWindowCallback(Window.Callback callback) {
        this.mWindowCallback = callback;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mTabSelector);
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.hideOverflowMenu();
            this.mActionMenuPresenter.hideSubMenus();
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void initProgress() {
        ProgressBar progressBar = new ProgressBar(this.mContext, null, 0, this.mProgressStyle);
        this.mProgressView = progressBar;
        progressBar.setId(R.id.progress_horizontal);
        this.mProgressView.setMax(10000);
        this.mProgressView.setVisibility(8);
        addView(this.mProgressView);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void initIndeterminateProgress() {
        ProgressBar progressBar = new ProgressBar(this.mContext, null, 0, this.mIndeterminateProgressStyle);
        this.mIndeterminateProgressView = progressBar;
        progressBar.setId(R.id.progress_circular);
        this.mIndeterminateProgressView.setVisibility(8);
        addView(this.mIndeterminateProgressView);
    }

    @Override // com.android.internal.widget.AbsActionBarView
    public void setSplitToolbar(boolean z) {
        if (this.mSplitActionBar != z) {
            if (this.mMenuView != null) {
                ViewGroup viewGroup = (ViewGroup) this.mMenuView.getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(this.mMenuView);
                }
                if (z) {
                    if (this.mSplitView != null) {
                        this.mSplitView.addView(this.mMenuView);
                    }
                    this.mMenuView.getLayoutParams().width = -1;
                } else {
                    addView(this.mMenuView);
                    this.mMenuView.getLayoutParams().width = -2;
                }
                this.mMenuView.requestLayout();
            }
            if (this.mSplitView != null) {
                this.mSplitView.setVisibility(z ? 0 : 8);
            }
            if (this.mActionMenuPresenter != null) {
                if (!z) {
                    this.mActionMenuPresenter.setExpandedActionViewsExclusive(getResources().getBoolean(R.bool.action_bar_expanded_action_views_exclusive));
                } else {
                    this.mActionMenuPresenter.setExpandedActionViewsExclusive(false);
                    this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
                    this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
                }
            }
            super.setSplitToolbar(z);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean isSplit() {
        return this.mSplitActionBar;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasEmbeddedTabs() {
        return this.mIncludeTabs;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setEmbeddedTabView(ScrollingTabContainerView scrollingTabContainerView) {
        ScrollingTabContainerView scrollingTabContainerView2 = this.mTabScrollView;
        if (scrollingTabContainerView2 != null) {
            removeView(scrollingTabContainerView2);
        }
        this.mTabScrollView = scrollingTabContainerView;
        boolean z = scrollingTabContainerView != null;
        this.mIncludeTabs = z;
        if (z && this.mNavigationMode == 2) {
            addView(scrollingTabContainerView);
            ViewGroup.LayoutParams layoutParams = this.mTabScrollView.getLayoutParams();
            layoutParams.width = -2;
            layoutParams.height = -1;
            scrollingTabContainerView.setAllowCollapse(true);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setMenuPrepared() {
        this.mMenuPrepared = true;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setMenu(Menu menu, MenuPresenter.Callback callback) {
        ActionMenuView actionMenuView;
        ViewGroup viewGroup;
        MenuBuilder menuBuilder = this.mOptionsMenu;
        if (menu == menuBuilder) {
            return;
        }
        if (menuBuilder != null) {
            menuBuilder.removeMenuPresenter(this.mActionMenuPresenter);
            this.mOptionsMenu.removeMenuPresenter(this.mExpandedMenuPresenter);
        }
        MenuBuilder menuBuilder2 = (MenuBuilder) menu;
        this.mOptionsMenu = menuBuilder2;
        if (this.mMenuView != null && (viewGroup = (ViewGroup) this.mMenuView.getParent()) != null) {
            viewGroup.removeView(this.mMenuView);
        }
        if (this.mActionMenuPresenter == null) {
            this.mActionMenuPresenter = new ActionMenuPresenter(this.mContext);
            this.mActionMenuPresenter.setCallback(callback);
            this.mActionMenuPresenter.setId(R.id.action_menu_presenter);
            this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
        }
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
        if (!this.mSplitActionBar) {
            this.mActionMenuPresenter.setExpandedActionViewsExclusive(getResources().getBoolean(R.bool.action_bar_expanded_action_views_exclusive));
            configPresenters(menuBuilder2);
            actionMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
            ViewGroup viewGroup2 = (ViewGroup) actionMenuView.getParent();
            if (viewGroup2 != null && viewGroup2 != this) {
                viewGroup2.removeView(actionMenuView);
            }
            addView(actionMenuView, layoutParams);
        } else {
            this.mActionMenuPresenter.setExpandedActionViewsExclusive(false);
            this.mActionMenuPresenter.setWidthLimit(getContext().getResources().getDisplayMetrics().widthPixels, true);
            this.mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
            layoutParams.width = -1;
            layoutParams.height = -2;
            configPresenters(menuBuilder2);
            actionMenuView = (ActionMenuView) this.mActionMenuPresenter.getMenuView(this);
            if (this.mSplitView != null) {
                ViewGroup viewGroup3 = (ViewGroup) actionMenuView.getParent();
                if (viewGroup3 != null && viewGroup3 != this.mSplitView) {
                    viewGroup3.removeView(actionMenuView);
                }
                actionMenuView.setVisibility(getAnimatedVisibility());
                this.mSplitView.addView(actionMenuView, layoutParams);
            } else {
                actionMenuView.setLayoutParams(layoutParams);
            }
        }
        this.mMenuView = actionMenuView;
    }

    private void configPresenters(MenuBuilder menuBuilder) {
        if (menuBuilder != null) {
            menuBuilder.addMenuPresenter(this.mActionMenuPresenter, this.mPopupContext);
            menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        } else {
            this.mActionMenuPresenter.initForMenu(this.mPopupContext, null);
            this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, null);
            this.mActionMenuPresenter.updateMenuView(true);
            this.mExpandedMenuPresenter.updateMenuView(true);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasExpandedActionView() {
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        return (expandedActionViewMenuPresenter == null || expandedActionViewMenuPresenter.mCurrentExpandedItem == null) ? false : true;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void collapseActionView() {
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        MenuItemImpl menuItemImpl = expandedActionViewMenuPresenter == null ? null : expandedActionViewMenuPresenter.mCurrentExpandedItem;
        if (menuItemImpl != null) {
            menuItemImpl.collapseActionView();
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setCustomView(View view) {
        boolean z = (this.mDisplayOptions & 16) != 0;
        View view2 = this.mCustomNavView;
        if (view2 != null && z) {
            removeView(view2);
        }
        this.mCustomNavView = view;
        if (view == null || !z) {
            return;
        }
        addView(view);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public CharSequence getTitle() {
        return this.mTitle;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setTitle(CharSequence charSequence) {
        this.mUserTitle = true;
        setTitleImpl(charSequence);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setWindowTitle(CharSequence charSequence) {
        if (this.mUserTitle) {
            return;
        }
        setTitleImpl(charSequence);
    }

    private void setTitleImpl(CharSequence charSequence) {
        this.mTitle = charSequence;
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.lambda$setTextAsync$0(charSequence);
            this.mTitleLayout.setVisibility(this.mExpandedActionView == null && (this.mDisplayOptions & 8) != 0 && (!TextUtils.isEmpty(this.mTitle) || !TextUtils.isEmpty(this.mSubtitle)) ? 0 : 8);
        }
        ActionMenuItem actionMenuItem = this.mLogoNavItem;
        if (actionMenuItem != null) {
            actionMenuItem.setTitle(charSequence);
        }
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override // com.android.internal.widget.DecorToolbar
    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setSubtitle(CharSequence charSequence) {
        this.mSubtitle = charSequence;
        TextView textView = this.mSubtitleView;
        if (textView != null) {
            textView.lambda$setTextAsync$0(charSequence);
            this.mSubtitleView.setVisibility(charSequence != null ? 0 : 8);
            this.mTitleLayout.setVisibility(this.mExpandedActionView == null && (this.mDisplayOptions & 8) != 0 && (!TextUtils.isEmpty(this.mTitle) || !TextUtils.isEmpty(this.mSubtitle)) ? 0 : 8);
        }
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setHomeButtonEnabled(boolean z) {
        setHomeButtonEnabled(z, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setHomeButtonEnabled(boolean z, boolean z2) {
        if (z2) {
            this.mWasHomeEnabled = z;
        }
        if (this.mExpandedActionView != null) {
            return;
        }
        this.mUpGoerFive.setEnabled(z);
        this.mUpGoerFive.setFocusable(z);
        updateHomeAccessibility(z);
    }

    private void updateHomeAccessibility(boolean z) {
        if (!z) {
            this.mUpGoerFive.setContentDescription(null);
            this.mUpGoerFive.setImportantForAccessibility(2);
        } else {
            this.mUpGoerFive.setImportantForAccessibility(0);
            this.mUpGoerFive.setContentDescription(buildHomeContentDescription());
        }
    }

    private CharSequence buildHomeContentDescription() {
        CharSequence charSequence = this.mHomeDescription;
        if (charSequence == null) {
            if ((this.mDisplayOptions & 4) != 0) {
                charSequence = this.mContext.getResources().getText(this.mDefaultUpDescription);
            } else {
                charSequence = this.mContext.getResources().getText(R.string.action_bar_home_description);
            }
        }
        CharSequence title = getTitle();
        CharSequence subtitle = getSubtitle();
        if (TextUtils.isEmpty(title)) {
            return charSequence;
        }
        if (!TextUtils.isEmpty(subtitle)) {
            return getResources().getString(R.string.action_bar_home_subtitle_description_format, title, subtitle, charSequence);
        }
        return getResources().getString(R.string.action_bar_home_description_format, title, charSequence);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDisplayOptions(int i) {
        View view;
        int i2 = this.mDisplayOptions;
        int i3 = i2 != -1 ? i ^ i2 : -1;
        this.mDisplayOptions = i;
        if ((i3 & 63) != 0) {
            if ((i3 & 4) != 0) {
                boolean z = (i & 4) != 0;
                this.mHomeLayout.setShowUp(z);
                if (z) {
                    setHomeButtonEnabled(true);
                }
            }
            if ((i3 & 1) != 0) {
                Drawable drawable = this.mLogo;
                boolean z2 = (drawable == null || (i & 1) == 0) ? false : true;
                HomeView homeView = this.mHomeLayout;
                if (!z2) {
                    drawable = this.mIcon;
                }
                homeView.setIcon(drawable);
            }
            if ((i3 & 8) != 0) {
                if ((i & 8) != 0) {
                    initTitle();
                } else {
                    this.mUpGoerFive.removeView(this.mTitleLayout);
                }
            }
            boolean z3 = (i & 2) != 0;
            boolean z4 = !z3 && ((this.mDisplayOptions & 4) != 0);
            this.mHomeLayout.setShowIcon(z3);
            this.mHomeLayout.setVisibility(((z3 || z4) && this.mExpandedActionView == null) ? 0 : 8);
            if ((i3 & 16) != 0 && (view = this.mCustomNavView) != null) {
                if ((i & 16) != 0) {
                    addView(view);
                } else {
                    removeView(view);
                }
            }
            if (this.mTitleLayout != null && (i3 & 32) != 0) {
                if ((i & 32) != 0) {
                    this.mTitleView.setSingleLine(false);
                    this.mTitleView.setMaxLines(2);
                } else {
                    this.mTitleView.setMaxLines(1);
                    this.mTitleView.setSingleLine(true);
                }
            }
            requestLayout();
        } else {
            invalidate();
        }
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setIcon(Drawable drawable) {
        this.mIcon = drawable;
        if (drawable != null && ((this.mDisplayOptions & 1) == 0 || this.mLogo == null)) {
            this.mHomeLayout.setIcon(drawable);
        }
        if (this.mExpandedActionView != null) {
            this.mExpandedHomeLayout.setIcon(this.mIcon.getConstantState().newDrawable(getResources()));
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setIcon(int i) {
        setIcon(i != 0 ? this.mContext.getDrawable(i) : null);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasIcon() {
        return this.mIcon != null;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setLogo(Drawable drawable) {
        this.mLogo = drawable;
        if (drawable == null || (this.mDisplayOptions & 1) == 0) {
            return;
        }
        this.mHomeLayout.setIcon(drawable);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setLogo(int i) {
        setLogo(i != 0 ? this.mContext.getDrawable(i) : null);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean hasLogo() {
        return this.mLogo != null;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationMode(int i) {
        ScrollingTabContainerView scrollingTabContainerView;
        ScrollingTabContainerView scrollingTabContainerView2;
        int i2 = this.mNavigationMode;
        if (i != i2) {
            if (i2 == 1) {
                LinearLayout linearLayout = this.mListNavLayout;
                if (linearLayout != null) {
                    removeView(linearLayout);
                }
            } else if (i2 == 2 && (scrollingTabContainerView2 = this.mTabScrollView) != null && this.mIncludeTabs) {
                removeView(scrollingTabContainerView2);
            }
            if (i == 1) {
                if (this.mSpinner == null) {
                    Spinner spinner = new Spinner(this.mContext, null, 16843479);
                    this.mSpinner = spinner;
                    spinner.setId(R.id.action_bar_spinner);
                    this.mListNavLayout = new LinearLayout(this.mContext, null, 16843508);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
                    layoutParams.gravity = 17;
                    this.mListNavLayout.addView(this.mSpinner, layoutParams);
                }
                SpinnerAdapter adapter = this.mSpinner.getAdapter();
                SpinnerAdapter spinnerAdapter = this.mSpinnerAdapter;
                if (adapter != spinnerAdapter) {
                    this.mSpinner.setAdapter(spinnerAdapter);
                }
                this.mSpinner.setOnItemSelectedListener(this.mNavItemSelectedListener);
                addView(this.mListNavLayout);
            } else if (i == 2 && (scrollingTabContainerView = this.mTabScrollView) != null && this.mIncludeTabs) {
                addView(scrollingTabContainerView);
            }
            this.mNavigationMode = i;
            requestLayout();
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDropdownParams(SpinnerAdapter spinnerAdapter, AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.mSpinnerAdapter = spinnerAdapter;
        this.mNavItemSelectedListener = onItemSelectedListener;
        Spinner spinner = this.mSpinner;
        if (spinner != null) {
            spinner.setAdapter(spinnerAdapter);
            this.mSpinner.setOnItemSelectedListener(onItemSelectedListener);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getDropdownItemCount() {
        SpinnerAdapter spinnerAdapter = this.mSpinnerAdapter;
        if (spinnerAdapter != null) {
            return spinnerAdapter.getCount();
        }
        return 0;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDropdownSelectedPosition(int i) {
        this.mSpinner.setSelection(i);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getDropdownSelectedPosition() {
        return this.mSpinner.getSelectedItemPosition();
    }

    @Override // com.android.internal.widget.DecorToolbar
    public View getCustomView() {
        return this.mCustomNavView;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getNavigationMode() {
        return this.mNavigationMode;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public int getDisplayOptions() {
        return this.mDisplayOptions;
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ActionBar.LayoutParams(DEFAULT_CUSTOM_GRAVITY);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        ViewParent parent;
        super.onFinishInflate();
        this.mUpGoerFive.addView(this.mHomeLayout, 0);
        addView(this.mUpGoerFive);
        View view = this.mCustomNavView;
        if (view == null || (this.mDisplayOptions & 16) == 0 || (parent = view.getParent()) == this) {
            return;
        }
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(this.mCustomNavView);
        }
        addView(this.mCustomNavView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTitle() {
        if (this.mTitleLayout == null) {
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.action_bar_title_item, (ViewGroup) this, false);
            this.mTitleLayout = linearLayout;
            this.mTitleView = (TextView) linearLayout.findViewById(R.id.action_bar_title);
            this.mSubtitleView = (TextView) this.mTitleLayout.findViewById(R.id.action_bar_subtitle);
            int i = this.mTitleStyleRes;
            if (i != 0) {
                this.mTitleView.setTextAppearance(i);
            }
            CharSequence charSequence = this.mTitle;
            if (charSequence != null) {
                this.mTitleView.lambda$setTextAsync$0(charSequence);
            }
            int i2 = this.mSubtitleStyleRes;
            if (i2 != 0) {
                this.mSubtitleView.setTextAppearance(i2);
            }
            CharSequence charSequence2 = this.mSubtitle;
            if (charSequence2 != null) {
                this.mSubtitleView.lambda$setTextAsync$0(charSequence2);
                this.mSubtitleView.setVisibility(0);
            }
        }
        this.mUpGoerFive.addView(this.mTitleLayout);
        if (this.mExpandedActionView != null || (TextUtils.isEmpty(this.mTitle) && TextUtils.isEmpty(this.mSubtitle))) {
            this.mTitleLayout.setVisibility(8);
        } else {
            this.mTitleLayout.setVisibility(0);
        }
    }

    public void setContextView(ActionBarContextView actionBarContextView) {
        this.mContextView = actionBarContextView;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setCollapsible(boolean z) {
        this.mIsCollapsible = z;
    }

    @Override // com.android.internal.widget.DecorToolbar
    public boolean isTitleTruncated() {
        Layout layout;
        TextView textView = this.mTitleView;
        if (textView == null || (layout = textView.getLayout()) == null) {
            return false;
        }
        int lineCount = layout.getLineCount();
        for (int i = 0; i < lineCount; i++) {
            if (layout.getEllipsisCount(i) > 0) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:115:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0260  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x027e  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0277  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x023c  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01b5  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onMeasure(int r19, int r20) {
        /*
            Method dump skipped, instructions count: 746
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.ActionBarView.onMeasure(int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x0167, code lost:
    
        if (r11 < r2) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x0170, code lost:
    
        if (r4 > r2) goto L91;
     */
    /* JADX WARN: Removed duplicated region for block: B:110:0x01ec  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:91:? A[RETURN, SYNTHETIC] */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onLayout(boolean r17, int r18, int r19, int r20, int r21) {
        /*
            Method dump skipped, instructions count: 523
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.ActionBarView.onLayout(boolean, int, int, int, int):void");
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ActionBar.LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams == null ? generateDefaultLayoutParams() : layoutParams;
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = this.mExpandedMenuPresenter;
        if (expandedActionViewMenuPresenter != null && expandedActionViewMenuPresenter.mCurrentExpandedItem != null) {
            savedState.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }
        savedState.isOverflowOpen = isOverflowMenuShowing();
        return savedState;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        MenuBuilder menuBuilder;
        MenuItem findItem;
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.expandedMenuItemId != 0 && this.mExpandedMenuPresenter != null && (menuBuilder = this.mOptionsMenu) != null && (findItem = menuBuilder.findItem(savedState.expandedMenuItemId)) != null) {
            findItem.expandActionView();
        }
        if (savedState.isOverflowOpen) {
            postShowOverflowMenu();
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationIcon(Drawable drawable) {
        this.mHomeLayout.setUpIndicator(drawable);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDefaultNavigationIcon(Drawable drawable) {
        this.mHomeLayout.setDefaultUpIndicator(drawable);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationIcon(int i) {
        this.mHomeLayout.setUpIndicator(i);
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationContentDescription(CharSequence charSequence) {
        this.mHomeDescription = charSequence;
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setNavigationContentDescription(int i) {
        this.mHomeDescriptionRes = i;
        this.mHomeDescription = i != 0 ? getResources().getText(i) : null;
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setDefaultNavigationContentDescription(int i) {
        if (this.mDefaultUpDescription == i) {
            return;
        }
        this.mDefaultUpDescription = i;
        updateHomeAccessibility(this.mUpGoerFive.isEnabled());
    }

    @Override // com.android.internal.widget.DecorToolbar
    public void setMenuCallbacks(MenuPresenter.Callback callback, MenuBuilder.Callback callback2) {
        if (this.mActionMenuPresenter != null) {
            this.mActionMenuPresenter.setCallback(callback);
        }
        MenuBuilder menuBuilder = this.mOptionsMenu;
        if (menuBuilder != null) {
            menuBuilder.setCallback(callback2);
        }
    }

    @Override // com.android.internal.widget.DecorToolbar
    public Menu getMenu() {
        return this.mOptionsMenu;
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.android.internal.widget.ActionBarView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int expandedMenuItemId;
        boolean isOverflowOpen;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.expandedMenuItemId = parcel.readInt();
            this.isOverflowOpen = parcel.readInt() != 0;
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.expandedMenuItemId);
            parcel.writeInt(this.isOverflowOpen ? 1 : 0);
        }
    }

    private static class HomeView extends FrameLayout {
        private static final long DEFAULT_TRANSITION_DURATION = 150;
        private Drawable mDefaultUpIndicator;
        private ImageView mIconView;
        private int mStartOffset;
        private Drawable mUpIndicator;
        private int mUpIndicatorRes;
        private ImageView mUpView;
        private int mUpWidth;

        public HomeView(Context context) {
            this(context, null);
        }

        public HomeView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            LayoutTransition layoutTransition = getLayoutTransition();
            if (layoutTransition != null) {
                layoutTransition.setDuration(DEFAULT_TRANSITION_DURATION);
            }
        }

        public void setShowUp(boolean z) {
            this.mUpView.setVisibility(z ? 0 : 8);
        }

        public void setShowIcon(boolean z) {
            this.mIconView.setVisibility(z ? 0 : 8);
        }

        public void setIcon(Drawable drawable) {
            this.mIconView.lambda$setImageURIAsync$0(drawable);
        }

        public void setUpIndicator(Drawable drawable) {
            this.mUpIndicator = drawable;
            this.mUpIndicatorRes = 0;
            updateUpIndicator();
        }

        public void setDefaultUpIndicator(Drawable drawable) {
            this.mDefaultUpIndicator = drawable;
            updateUpIndicator();
        }

        public void setUpIndicator(int i) {
            this.mUpIndicatorRes = i;
            this.mUpIndicator = null;
            updateUpIndicator();
        }

        private void updateUpIndicator() {
            Drawable drawable = this.mUpIndicator;
            if (drawable != null) {
                this.mUpView.lambda$setImageURIAsync$0(drawable);
            } else if (this.mUpIndicatorRes != 0) {
                this.mUpView.lambda$setImageURIAsync$0(getContext().getDrawable(this.mUpIndicatorRes));
            } else {
                this.mUpView.lambda$setImageURIAsync$0(this.mDefaultUpIndicator);
            }
        }

        @Override // android.view.View
        protected void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            if (this.mUpIndicatorRes != 0) {
                updateUpIndicator();
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
            onPopulateAccessibilityEvent(accessibilityEvent);
            return true;
        }

        @Override // android.view.View
        public void onPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEventInternal(accessibilityEvent);
            CharSequence contentDescription = getContentDescription();
            if (TextUtils.isEmpty(contentDescription)) {
                return;
            }
            accessibilityEvent.getText().add(contentDescription);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchHoverEvent(MotionEvent motionEvent) {
            return onHoverEvent(motionEvent);
        }

        @Override // android.view.View
        protected void onFinishInflate() {
            this.mUpView = (ImageView) findViewById(R.id.up);
            this.mIconView = (ImageView) findViewById(16908332);
            this.mDefaultUpIndicator = this.mUpView.getDrawable();
        }

        public int getStartOffset() {
            if (this.mUpView.getVisibility() == 8) {
                return this.mStartOffset;
            }
            return 0;
        }

        public int getUpWidth() {
            return this.mUpWidth;
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            measureChildWithMargins(this.mUpView, i, 0, i2, 0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mUpView.getLayoutParams();
            int i3 = layoutParams.leftMargin + layoutParams.rightMargin;
            int measuredWidth = this.mUpView.getMeasuredWidth();
            this.mUpWidth = measuredWidth;
            this.mStartOffset = measuredWidth + i3;
            int i4 = this.mUpView.getVisibility() == 8 ? 0 : this.mStartOffset;
            int measuredHeight = layoutParams.topMargin + this.mUpView.getMeasuredHeight() + layoutParams.bottomMargin;
            if (this.mIconView.getVisibility() != 8) {
                measureChildWithMargins(this.mIconView, i, i4, i2, 0);
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mIconView.getLayoutParams();
                i4 += layoutParams2.leftMargin + this.mIconView.getMeasuredWidth() + layoutParams2.rightMargin;
                measuredHeight = Math.max(measuredHeight, layoutParams2.topMargin + this.mIconView.getMeasuredHeight() + layoutParams2.bottomMargin);
            } else if (i3 < 0) {
                i4 -= i3;
            }
            int mode = View.MeasureSpec.getMode(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            int size = View.MeasureSpec.getSize(i);
            int size2 = View.MeasureSpec.getSize(i2);
            if (mode == Integer.MIN_VALUE) {
                i4 = Math.min(i4, size);
            } else if (mode == 1073741824) {
                i4 = size;
            }
            if (mode2 == Integer.MIN_VALUE) {
                measuredHeight = Math.min(measuredHeight, size2);
            } else if (mode2 == 1073741824) {
                measuredHeight = size2;
            }
            setMeasuredDimension(i4, measuredHeight);
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            int i5;
            int i6;
            int i7 = (i4 - i2) / 2;
            boolean isLayoutRtl = isLayoutRtl();
            int width = getWidth();
            int i8 = 0;
            if (this.mUpView.getVisibility() != 8) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mUpView.getLayoutParams();
                int measuredHeight = this.mUpView.getMeasuredHeight();
                int measuredWidth = this.mUpView.getMeasuredWidth();
                int i9 = layoutParams.leftMargin + measuredWidth + layoutParams.rightMargin;
                int i10 = i7 - (measuredHeight / 2);
                int i11 = measuredHeight + i10;
                if (isLayoutRtl) {
                    i8 = width - measuredWidth;
                    i3 -= i9;
                    measuredWidth = width;
                } else {
                    i += i9;
                }
                this.mUpView.layout(i8, i10, measuredWidth, i11);
                i8 = i9;
            }
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mIconView.getLayoutParams();
            int measuredHeight2 = this.mIconView.getMeasuredHeight();
            int measuredWidth2 = this.mIconView.getMeasuredWidth();
            int i12 = (i3 - i) / 2;
            int max = Math.max(layoutParams2.topMargin, i7 - (measuredHeight2 / 2));
            int i13 = measuredHeight2 + max;
            int max2 = Math.max(layoutParams2.getMarginStart(), i12 - (measuredWidth2 / 2));
            if (isLayoutRtl) {
                i6 = (width - i8) - max2;
                i5 = i6 - measuredWidth2;
            } else {
                i5 = i8 + max2;
                i6 = i5 + measuredWidth2;
            }
            this.mIconView.layout(i5, max, i6, i13);
        }
    }

    private class ExpandedActionViewMenuPresenter implements MenuPresenter {
        MenuItemImpl mCurrentExpandedItem;
        MenuBuilder mMenu;

        @Override // com.android.internal.view.menu.MenuPresenter
        public boolean flagActionItems() {
            return false;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public int getId() {
            return 0;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public MenuView getMenuView(ViewGroup viewGroup) {
            return null;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public Parcelable onSaveInstanceState() {
            return null;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
            return false;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void setCallback(MenuPresenter.Callback callback) {
        }

        private ExpandedActionViewMenuPresenter() {
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void initForMenu(Context context, MenuBuilder menuBuilder) {
            MenuItemImpl menuItemImpl;
            MenuBuilder menuBuilder2 = this.mMenu;
            if (menuBuilder2 != null && (menuItemImpl = this.mCurrentExpandedItem) != null) {
                menuBuilder2.collapseItemActionView(menuItemImpl);
            }
            this.mMenu = menuBuilder;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public void updateMenuView(boolean z) {
            if (this.mCurrentExpandedItem != null) {
                MenuBuilder menuBuilder = this.mMenu;
                if (menuBuilder != null) {
                    int size = menuBuilder.size();
                    for (int i = 0; i < size; i++) {
                        if (this.mMenu.getItem(i) == this.mCurrentExpandedItem) {
                            return;
                        }
                    }
                }
                collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
            }
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            ActionBarView.this.mExpandedActionView = menuItemImpl.getActionView();
            ActionBarView.this.mExpandedHomeLayout.setIcon(ActionBarView.this.mIcon.getConstantState().newDrawable(ActionBarView.this.getResources()));
            this.mCurrentExpandedItem = menuItemImpl;
            ViewParent parent = ActionBarView.this.mExpandedActionView.getParent();
            ActionBarView actionBarView = ActionBarView.this;
            if (parent != actionBarView) {
                actionBarView.addView(actionBarView.mExpandedActionView);
            }
            if (ActionBarView.this.mExpandedHomeLayout.getParent() != ActionBarView.this.mUpGoerFive) {
                ActionBarView.this.mUpGoerFive.addView(ActionBarView.this.mExpandedHomeLayout);
            }
            ActionBarView.this.mHomeLayout.setVisibility(8);
            if (ActionBarView.this.mTitleLayout != null) {
                ActionBarView.this.mTitleLayout.setVisibility(8);
            }
            if (ActionBarView.this.mTabScrollView != null) {
                ActionBarView.this.mTabScrollView.setVisibility(8);
            }
            if (ActionBarView.this.mSpinner != null) {
                ActionBarView.this.mSpinner.setVisibility(8);
            }
            if (ActionBarView.this.mCustomNavView != null) {
                ActionBarView.this.mCustomNavView.setVisibility(8);
            }
            ActionBarView.this.setHomeButtonEnabled(false, false);
            ActionBarView.this.requestLayout();
            menuItemImpl.setActionViewExpanded(true);
            if (ActionBarView.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView) ActionBarView.this.mExpandedActionView).onActionViewExpanded();
            }
            return true;
        }

        @Override // com.android.internal.view.menu.MenuPresenter
        public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
            if (ActionBarView.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView) ActionBarView.this.mExpandedActionView).onActionViewCollapsed();
            }
            ActionBarView actionBarView = ActionBarView.this;
            actionBarView.removeView(actionBarView.mExpandedActionView);
            ActionBarView.this.mUpGoerFive.removeView(ActionBarView.this.mExpandedHomeLayout);
            ActionBarView.this.mExpandedActionView = null;
            if ((ActionBarView.this.mDisplayOptions & 2) != 0) {
                ActionBarView.this.mHomeLayout.setVisibility(0);
            }
            if ((ActionBarView.this.mDisplayOptions & 8) != 0) {
                if (ActionBarView.this.mTitleLayout == null) {
                    ActionBarView.this.initTitle();
                } else {
                    ActionBarView.this.mTitleLayout.setVisibility(0);
                }
            }
            if (ActionBarView.this.mTabScrollView != null) {
                ActionBarView.this.mTabScrollView.setVisibility(0);
            }
            if (ActionBarView.this.mSpinner != null) {
                ActionBarView.this.mSpinner.setVisibility(0);
            }
            if (ActionBarView.this.mCustomNavView != null) {
                ActionBarView.this.mCustomNavView.setVisibility(0);
            }
            ActionBarView.this.mExpandedHomeLayout.setIcon(null);
            this.mCurrentExpandedItem = null;
            ActionBarView actionBarView2 = ActionBarView.this;
            actionBarView2.setHomeButtonEnabled(actionBarView2.mWasHomeEnabled);
            ActionBarView.this.requestLayout();
            menuItemImpl.setActionViewExpanded(false);
            return true;
        }
    }
}
