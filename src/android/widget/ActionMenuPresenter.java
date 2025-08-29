package android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.ActionProvider;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ActionMenuView;
import android.widget.FrameLayout;
import com.android.internal.R;
import com.android.internal.view.ActionBarPolicy;
import com.android.internal.view.menu.ActionMenuItemView;
import com.android.internal.view.menu.BaseMenuPresenter;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuItemImpl;
import com.android.internal.view.menu.MenuPopupHelper;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.view.menu.MenuView;
import com.android.internal.view.menu.ShowableListMenu;
import com.android.internal.view.menu.SubMenuBuilder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes5.dex */
public class ActionMenuPresenter extends BaseMenuPresenter implements ActionProvider.SubUiVisibilityListener {
    private static final boolean ACTIONBAR_ANIMATIONS_ENABLED = false;
    private static final float ACTION_MENU_WIDTH_LIMIT = 0.7f;
    private static final int ITEM_ANIMATION_DURATION = 150;
    private static final String TAG = "ActionMenuPresenter";
    private final SparseBooleanArray mActionButtonGroups;
    private ActionButtonSubmenu mActionButtonPopup;
    private int mActionItemWidthLimit;
    private View.OnAttachStateChangeListener mAttachStateChangeListener;
    private boolean mExpandedActionViewsExclusive;
    private boolean mIsThemeDeviceDefaultFamily;
    private ViewTreeObserver.OnPreDrawListener mItemAnimationPreDrawListener;
    private int mMaxItems;
    private boolean mMaxItemsSet;
    private int mMinCellSize;
    private int mNavigationBarHeight;
    int mOpenSubMenuId;
    private OverflowMenuButton mOverflowButton;
    private OverflowPopup mOverflowPopup;
    private Drawable mPendingOverflowIcon;
    private boolean mPendingOverflowIconSet;
    private ActionMenuPopupCallback mPopupCallback;
    final PopupPresenterCallback mPopupPresenterCallback;
    private SparseArray<MenuItemLayoutInfo> mPostLayoutItems;
    private OpenOverflowRunnable mPostedOpenRunnable;
    private SparseArray<MenuItemLayoutInfo> mPreLayoutItems;
    private boolean mReserveOverflow;
    private boolean mReserveOverflowSet;
    private List<ItemAnimationInfo> mRunningItemAnimations;
    private View mSemOverflowButton;
    private boolean mStrictWidthLimit;
    private CharSequence mTooltipText;
    private boolean mUseTextItemMode;
    private int mWidthLimit;
    private boolean mWidthLimitSet;

    public ActionMenuPresenter(Context context) {
        super(context, R.layout.action_menu_layout, R.layout.action_menu_item_layout);
        this.mActionButtonGroups = new SparseBooleanArray();
        this.mPopupPresenterCallback = new PopupPresenterCallback();
        this.mPreLayoutItems = new SparseArray<>();
        this.mPostLayoutItems = new SparseArray<>();
        this.mRunningItemAnimations = new ArrayList();
        this.mItemAnimationPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: android.widget.ActionMenuPresenter.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                ActionMenuPresenter.this.computeMenuItemAnimationInfo(false);
                ((View) ActionMenuPresenter.this.mMenuView).getViewTreeObserver().removeOnPreDrawListener(this);
                ActionMenuPresenter.this.runItemAnimations();
                return true;
            }
        };
        this.mAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: android.widget.ActionMenuPresenter.2
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                ((View) ActionMenuPresenter.this.mMenuView).getViewTreeObserver().removeOnPreDrawListener(ActionMenuPresenter.this.mItemAnimationPreDrawListener);
                ActionMenuPresenter.this.mPreLayoutItems.clear();
                ActionMenuPresenter.this.mPostLayoutItems.clear();
            }
        };
        boolean z = false;
        this.mNavigationBarHeight = 0;
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, true);
        boolean z2 = typedValue.data != 0;
        this.mIsThemeDeviceDefaultFamily = z2;
        if (z2 && context.getResources().getBoolean(R.bool.tw_action_bar_text_item_mode)) {
            z = true;
        }
        this.mUseTextItemMode = z;
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter, com.android.internal.view.menu.MenuPresenter
    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        super.initForMenu(context, menuBuilder);
        if (this.mIsThemeDeviceDefaultFamily) {
            super.setMenuLayoutResources(R.layout.sem_action_menu_layout, R.layout.sem_action_menu_item_layout);
        }
        Resources resources = context.getResources();
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(context);
        if (!this.mReserveOverflowSet) {
            this.mReserveOverflow = actionBarPolicy.showsOverflowMenuButton();
        }
        if (!this.mWidthLimitSet) {
            if (this.mIsThemeDeviceDefaultFamily) {
                this.mWidthLimit = (int) (resources.getDisplayMetrics().widthPixels * ACTION_MENU_WIDTH_LIMIT);
            } else {
                this.mWidthLimit = actionBarPolicy.getEmbeddedMenuWidthLimit();
            }
        }
        if (!this.mMaxItemsSet) {
            this.mMaxItems = actionBarPolicy.getMaxActionButtons();
        }
        int measuredWidth = this.mWidthLimit;
        if (this.mReserveOverflow) {
            if (this.mSemOverflowButton == null) {
                if (this.mIsThemeDeviceDefaultFamily) {
                    this.mSemOverflowButton = new SemOverflowMenuButtonContainer(this.mSystemContext);
                } else {
                    OverflowMenuButton overflowMenuButton = new OverflowMenuButton(this.mSystemContext);
                    this.mOverflowButton = overflowMenuButton;
                    this.mSemOverflowButton = overflowMenuButton;
                    if (this.mPendingOverflowIconSet) {
                        overflowMenuButton.lambda$setImageURIAsync$2(this.mPendingOverflowIcon);
                        this.mPendingOverflowIcon = null;
                        this.mPendingOverflowIconSet = false;
                    }
                }
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                this.mSemOverflowButton.measure(iMakeMeasureSpec, iMakeMeasureSpec);
            }
            measuredWidth -= this.mSemOverflowButton.getMeasuredWidth();
        } else {
            this.mOverflowButton = null;
            this.mSemOverflowButton = null;
        }
        this.mActionItemWidthLimit = measuredWidth;
        this.mMinCellSize = (int) (resources.getDisplayMetrics().density * 56.0f);
    }

    public void onConfigurationChanged(Configuration configuration) {
        View view;
        if (this.mSemOverflowButton != null) {
            TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(null, R.styleable.View, 16843510, 0);
            this.mSemOverflowButton.setMinimumHeight(typedArrayObtainStyledAttributes.getDimensionPixelSize(37, -1));
            View view2 = this.mSemOverflowButton;
            if ((view2 instanceof SemOverflowMenuButtonContainer) && view2.getParent() == null) {
                this.mSemOverflowButton.dispatchConfigurationChanged(configuration);
            }
            typedArrayObtainStyledAttributes.recycle();
        }
        if (!this.mMaxItemsSet) {
            this.mMaxItems = ActionBarPolicy.get(this.mContext).getMaxActionButtons();
        }
        if (!this.mWidthLimitSet) {
            if (this.mIsThemeDeviceDefaultFamily) {
                this.mWidthLimit = (int) (this.mContext.getResources().getDisplayMetrics().widthPixels * ACTION_MENU_WIDTH_LIMIT);
            } else {
                this.mWidthLimit = this.mContext.getResources().getDisplayMetrics().widthPixels / 2;
            }
        }
        if (this.mReserveOverflow && (view = this.mSemOverflowButton) != null) {
            this.mActionItemWidthLimit = this.mWidthLimit - view.getMeasuredWidth();
        } else {
            this.mActionItemWidthLimit = this.mWidthLimit;
        }
        if (this.mMenu != null) {
            this.mMenu.onItemsChanged(true);
        }
    }

    public void setWidthLimit(int i, boolean z) {
        this.mWidthLimit = i;
        this.mStrictWidthLimit = z;
        this.mWidthLimitSet = true;
    }

    public void setReserveOverflow(boolean z) {
        this.mReserveOverflow = z;
        this.mReserveOverflowSet = true;
    }

    public void setItemLimit(int i) {
        this.mMaxItems = i;
        this.mMaxItemsSet = true;
    }

    public void setExpandedActionViewsExclusive(boolean z) {
        this.mExpandedActionViewsExclusive = z;
    }

    public void setOverflowIcon(Drawable drawable) {
        View view = this.mSemOverflowButton;
        if (view != null) {
            if (this.mIsThemeDeviceDefaultFamily) {
                ((SemOverflowMenuButtonContainer) view).setImageDrawable(drawable);
                return;
            } else {
                ((OverflowMenuButton) view).lambda$setImageURIAsync$2(drawable);
                return;
            }
        }
        this.mPendingOverflowIconSet = true;
        this.mPendingOverflowIcon = drawable;
    }

    public Drawable getOverflowIcon() {
        View view = this.mSemOverflowButton;
        if (view != null) {
            if (this.mIsThemeDeviceDefaultFamily) {
                return ((SemOverflowMenuButtonContainer) view).getDrawable();
            }
            return ((OverflowMenuButton) view).getDrawable();
        }
        if (this.mPendingOverflowIconSet) {
            return this.mPendingOverflowIcon;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.internal.view.menu.BaseMenuPresenter, com.android.internal.view.menu.MenuPresenter
    public MenuView getMenuView(ViewGroup viewGroup) {
        Object obj = this.mMenuView;
        MenuView menuView = super.getMenuView(viewGroup);
        if (obj != menuView) {
            ((ActionMenuView) menuView).setPresenter(this);
            if (obj != null) {
                ((View) obj).removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
            }
            ((View) menuView).addOnAttachStateChangeListener(this.mAttachStateChangeListener);
        }
        return menuView;
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    public View getItemView(MenuItemImpl menuItemImpl, View view, ViewGroup viewGroup) {
        View actionView = menuItemImpl.getActionView();
        if (actionView == null || menuItemImpl.hasCollapsibleActionView()) {
            actionView = super.getItemView(menuItemImpl, view, viewGroup);
        }
        actionView.setVisibility(menuItemImpl.isActionViewExpanded() ? 8 : 0);
        ActionMenuView actionMenuView = (ActionMenuView) viewGroup;
        ViewGroup.LayoutParams layoutParams = actionView.getLayoutParams();
        if (!actionMenuView.checkLayoutParams(layoutParams)) {
            actionView.setLayoutParams(actionMenuView.generateLayoutParams(layoutParams));
        }
        return actionView;
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    public void bindItemView(MenuItemImpl menuItemImpl, MenuView.ItemView itemView) {
        itemView.initialize(menuItemImpl, 0);
        ActionMenuItemView actionMenuItemView = (ActionMenuItemView) itemView;
        actionMenuItemView.setItemInvoker((ActionMenuView) this.mMenuView);
        if (this.mPopupCallback == null) {
            this.mPopupCallback = new ActionMenuPopupCallback();
        }
        actionMenuItemView.setPopupCallback(this.mPopupCallback);
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    public boolean shouldIncludeItem(int i, MenuItemImpl menuItemImpl) {
        return menuItemImpl.isActionButton();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void computeMenuItemAnimationInfo(boolean z) {
        ViewGroup viewGroup = (ViewGroup) this.mMenuView;
        int childCount = viewGroup.getChildCount();
        SparseArray<MenuItemLayoutInfo> sparseArray = z ? this.mPreLayoutItems : this.mPostLayoutItems;
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            int id = childAt.getId();
            if (id > 0 && childAt.getWidth() != 0 && childAt.getHeight() != 0) {
                sparseArray.put(id, new MenuItemLayoutInfo(childAt, z));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runItemAnimations() {
        ObjectAnimator objectAnimatorOfPropertyValuesHolder;
        int i = 0;
        while (true) {
            float alpha = 1.0f;
            if (i >= this.mPreLayoutItems.size()) {
                break;
            }
            int iKeyAt = this.mPreLayoutItems.keyAt(i);
            final MenuItemLayoutInfo menuItemLayoutInfo = this.mPreLayoutItems.get(iKeyAt);
            int iIndexOfKey = this.mPostLayoutItems.indexOfKey(iKeyAt);
            if (iIndexOfKey >= 0) {
                MenuItemLayoutInfo menuItemLayoutInfoValueAt = this.mPostLayoutItems.valueAt(iIndexOfKey);
                PropertyValuesHolder propertyValuesHolderOfFloat = menuItemLayoutInfo.left != menuItemLayoutInfoValueAt.left ? PropertyValuesHolder.ofFloat(View.TRANSLATION_X, menuItemLayoutInfo.left - menuItemLayoutInfoValueAt.left, 0.0f) : null;
                PropertyValuesHolder propertyValuesHolderOfFloat2 = menuItemLayoutInfo.top != menuItemLayoutInfoValueAt.top ? PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, menuItemLayoutInfo.top - menuItemLayoutInfoValueAt.top, 0.0f) : null;
                if (propertyValuesHolderOfFloat != null || propertyValuesHolderOfFloat2 != null) {
                    for (int i2 = 0; i2 < this.mRunningItemAnimations.size(); i2++) {
                        ItemAnimationInfo itemAnimationInfo = this.mRunningItemAnimations.get(i2);
                        if (itemAnimationInfo.id == iKeyAt && itemAnimationInfo.animType == 0) {
                            itemAnimationInfo.animator.cancel();
                        }
                    }
                    if (propertyValuesHolderOfFloat == null) {
                        objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(menuItemLayoutInfoValueAt.view, propertyValuesHolderOfFloat2);
                    } else if (propertyValuesHolderOfFloat2 != null) {
                        objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(menuItemLayoutInfoValueAt.view, propertyValuesHolderOfFloat, propertyValuesHolderOfFloat2);
                    } else {
                        objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(menuItemLayoutInfoValueAt.view, propertyValuesHolderOfFloat);
                    }
                    objectAnimatorOfPropertyValuesHolder.setDuration(150L);
                    objectAnimatorOfPropertyValuesHolder.start();
                    this.mRunningItemAnimations.add(new ItemAnimationInfo(iKeyAt, menuItemLayoutInfoValueAt, objectAnimatorOfPropertyValuesHolder, 0));
                    objectAnimatorOfPropertyValuesHolder.addListener(new AnimatorListenerAdapter() { // from class: android.widget.ActionMenuPresenter.3
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            for (int i3 = 0; i3 < ActionMenuPresenter.this.mRunningItemAnimations.size(); i3++) {
                                if (((ItemAnimationInfo) ActionMenuPresenter.this.mRunningItemAnimations.get(i3)).animator == animator) {
                                    ActionMenuPresenter.this.mRunningItemAnimations.remove(i3);
                                    return;
                                }
                            }
                        }
                    });
                }
                this.mPostLayoutItems.remove(iKeyAt);
            } else {
                for (int i3 = 0; i3 < this.mRunningItemAnimations.size(); i3++) {
                    ItemAnimationInfo itemAnimationInfo2 = this.mRunningItemAnimations.get(i3);
                    if (itemAnimationInfo2.id == iKeyAt && itemAnimationInfo2.animType == 1) {
                        alpha = itemAnimationInfo2.menuItemLayoutInfo.view.getAlpha();
                        itemAnimationInfo2.animator.cancel();
                    }
                }
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(menuItemLayoutInfo.view, View.ALPHA, alpha, 0.0f);
                ((ViewGroup) this.mMenuView).getOverlay().add(menuItemLayoutInfo.view);
                objectAnimatorOfFloat.setDuration(150L);
                objectAnimatorOfFloat.start();
                this.mRunningItemAnimations.add(new ItemAnimationInfo(iKeyAt, menuItemLayoutInfo, objectAnimatorOfFloat, 2));
                objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: android.widget.ActionMenuPresenter.4
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        int i4 = 0;
                        while (true) {
                            if (i4 >= ActionMenuPresenter.this.mRunningItemAnimations.size()) {
                                break;
                            }
                            if (((ItemAnimationInfo) ActionMenuPresenter.this.mRunningItemAnimations.get(i4)).animator == animator) {
                                ActionMenuPresenter.this.mRunningItemAnimations.remove(i4);
                                break;
                            }
                            i4++;
                        }
                        ((ViewGroup) ActionMenuPresenter.this.mMenuView).getOverlay().remove(menuItemLayoutInfo.view);
                    }
                });
            }
            i++;
        }
        for (int i4 = 0; i4 < this.mPostLayoutItems.size(); i4++) {
            int iKeyAt2 = this.mPostLayoutItems.keyAt(i4);
            int iIndexOfKey2 = this.mPostLayoutItems.indexOfKey(iKeyAt2);
            if (iIndexOfKey2 >= 0) {
                MenuItemLayoutInfo menuItemLayoutInfoValueAt2 = this.mPostLayoutItems.valueAt(iIndexOfKey2);
                float alpha2 = 0.0f;
                for (int i5 = 0; i5 < this.mRunningItemAnimations.size(); i5++) {
                    ItemAnimationInfo itemAnimationInfo3 = this.mRunningItemAnimations.get(i5);
                    if (itemAnimationInfo3.id == iKeyAt2 && itemAnimationInfo3.animType == 2) {
                        alpha2 = itemAnimationInfo3.menuItemLayoutInfo.view.getAlpha();
                        itemAnimationInfo3.animator.cancel();
                    }
                }
                ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(menuItemLayoutInfoValueAt2.view, View.ALPHA, alpha2, 1.0f);
                objectAnimatorOfFloat2.start();
                objectAnimatorOfFloat2.setDuration(150L);
                this.mRunningItemAnimations.add(new ItemAnimationInfo(iKeyAt2, menuItemLayoutInfoValueAt2, objectAnimatorOfFloat2, 1));
                objectAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: android.widget.ActionMenuPresenter.5
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        for (int i6 = 0; i6 < ActionMenuPresenter.this.mRunningItemAnimations.size(); i6++) {
                            if (((ItemAnimationInfo) ActionMenuPresenter.this.mRunningItemAnimations.get(i6)).animator == animator) {
                                ActionMenuPresenter.this.mRunningItemAnimations.remove(i6);
                                return;
                            }
                        }
                    }
                });
            }
        }
        this.mPreLayoutItems.clear();
        this.mPostLayoutItems.clear();
    }

    private void setupItemAnimations() {
        computeMenuItemAnimationInfo(true);
        ((View) this.mMenuView).getViewTreeObserver().addOnPreDrawListener(this.mItemAnimationPreDrawListener);
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter, com.android.internal.view.menu.MenuPresenter
    public void updateMenuView(boolean z) {
        View view;
        if (this.mMenuView == null) {
            Log.e(TAG, "ActionMenuPresenter::updateMenuView() mMenuView is null");
            return;
        }
        super.updateMenuView(z);
        ((View) this.mMenuView).requestLayout();
        boolean z2 = false;
        if (this.mMenu != null) {
            ArrayList<MenuItemImpl> actionItems = this.mMenu.getActionItems();
            int size = actionItems.size();
            for (int i = 0; i < size; i++) {
                ActionProvider actionProvider = actionItems.get(i).getActionProvider();
                if (actionProvider != null) {
                    actionProvider.setSubUiVisibilityListener(this);
                }
            }
        }
        ArrayList<MenuItemImpl> nonActionItems = this.mMenu != null ? this.mMenu.getNonActionItems() : null;
        if (this.mReserveOverflow && nonActionItems != null) {
            int size2 = nonActionItems.size();
            if (size2 == 1) {
                z2 = !nonActionItems.get(0).isActionViewExpanded();
            } else if (size2 > 0) {
                z2 = true;
            }
        }
        if (z2) {
            if (this.mSemOverflowButton == null) {
                if (this.mIsThemeDeviceDefaultFamily) {
                    this.mSemOverflowButton = new SemOverflowMenuButtonContainer(this.mSystemContext);
                } else {
                    OverflowMenuButton overflowMenuButton = new OverflowMenuButton(this.mSystemContext);
                    this.mOverflowButton = overflowMenuButton;
                    this.mSemOverflowButton = overflowMenuButton;
                }
            }
            ActionMenuView actionMenuView = (ActionMenuView) this.mMenuView;
            ViewRootImpl viewRootImpl = actionMenuView.getViewRootImpl();
            if (viewRootImpl != null && (view = viewRootImpl.getView()) != null) {
                this.mSemOverflowButton.setLayoutDirection(view.getLayoutDirection());
            }
            ViewGroup viewGroup = (ViewGroup) this.mSemOverflowButton.getParent();
            if (viewGroup != this.mMenuView) {
                if (viewGroup != null) {
                    viewGroup.removeView(this.mSemOverflowButton);
                }
                actionMenuView.addView(this.mSemOverflowButton, actionMenuView.generateOverflowButtonLayoutParams());
            }
        } else {
            View view2 = this.mSemOverflowButton;
            if (view2 != null && view2.getParent() == this.mMenuView) {
                ((ViewGroup) this.mMenuView).removeView(this.mSemOverflowButton);
                if (isOverflowMenuShowing()) {
                    hideOverflowMenu();
                }
            }
        }
        if (this.mSemOverflowButton instanceof SemOverflowMenuButtonContainer) {
            ((SemOverflowMenuButtonContainer) this.mSemOverflowButton).invalidateBadgeText();
        }
        View view3 = this.mSemOverflowButton;
        if (view3 == null || (view3.getVisibility() != 0 && isOverflowMenuShowing())) {
            hideOverflowMenu();
        }
        ((ActionMenuView) this.mMenuView).setOverflowReserved(this.mReserveOverflow);
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter
    public boolean filterLeftoverView(ViewGroup viewGroup, int i) {
        if (viewGroup.getChildAt(i) == this.mSemOverflowButton) {
            return false;
        }
        return super.filterLeftoverView(viewGroup, i);
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter, com.android.internal.view.menu.MenuPresenter
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        boolean z = false;
        if (subMenuBuilder == null || !subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        SubMenuBuilder subMenuBuilder2 = subMenuBuilder;
        while (subMenuBuilder2.getParentMenu() != this.mMenu) {
            subMenuBuilder2 = (SubMenuBuilder) subMenuBuilder2.getParentMenu();
        }
        View viewFindViewForItem = findViewForItem(subMenuBuilder2.getItem());
        if (viewFindViewForItem == null) {
            return false;
        }
        this.mOpenSubMenuId = subMenuBuilder.getItem().getItemId();
        int size = subMenuBuilder.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            MenuItem item = subMenuBuilder.getItem(i);
            if (item.isVisible() && item.getIcon() != null) {
                z = true;
                break;
            }
            i++;
        }
        ActionButtonSubmenu actionButtonSubmenu = new ActionButtonSubmenu(this.mContext, subMenuBuilder, viewFindViewForItem);
        this.mActionButtonPopup = actionButtonSubmenu;
        actionButtonSubmenu.setForceShowIcon(z);
        this.mActionButtonPopup.show();
        super.onSubMenuSelected(subMenuBuilder);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private View findViewForItem(MenuItem menuItem) {
        ViewGroup viewGroup = (ViewGroup) this.mMenuView;
        if (viewGroup == null) {
            return null;
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if ((childAt instanceof MenuView.ItemView) && ((MenuView.ItemView) childAt).getItemData() == menuItem) {
                return childAt;
            }
        }
        return null;
    }

    public boolean showOverflowMenu() {
        if (!this.mReserveOverflow || isOverflowMenuShowing() || this.mMenu == null || this.mMenuView == null || this.mPostedOpenRunnable != null || this.mMenu.getNonActionItems().isEmpty()) {
            return false;
        }
        this.mPostedOpenRunnable = new OpenOverflowRunnable(new OverflowPopup(this.mContext, this.mMenu, this.mSemOverflowButton, true));
        ((View) this.mMenuView).post(this.mPostedOpenRunnable);
        super.onSubMenuSelected(null);
        return true;
    }

    public boolean hideOverflowMenu() {
        if (this.mPostedOpenRunnable != null && this.mMenuView != null) {
            ((View) this.mMenuView).removeCallbacks(this.mPostedOpenRunnable);
            this.mPostedOpenRunnable = null;
            return true;
        }
        OverflowPopup overflowPopup = this.mOverflowPopup;
        if (overflowPopup == null) {
            return false;
        }
        overflowPopup.dismiss();
        return true;
    }

    public boolean dismissPopupMenus() {
        return hideSubMenus() | hideOverflowMenu();
    }

    public boolean hideSubMenus() {
        ActionButtonSubmenu actionButtonSubmenu = this.mActionButtonPopup;
        if (actionButtonSubmenu == null) {
            return false;
        }
        actionButtonSubmenu.dismiss();
        return true;
    }

    public boolean isOverflowMenuShowing() {
        OverflowPopup overflowPopup = this.mOverflowPopup;
        return overflowPopup != null && overflowPopup.isShowing();
    }

    public boolean isOverflowMenuShowPending() {
        return this.mPostedOpenRunnable != null || isOverflowMenuShowing();
    }

    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter, com.android.internal.view.menu.MenuPresenter
    public boolean flagActionItems() {
        ArrayList<MenuItemImpl> visibleItems;
        int size;
        int i;
        int iMeasureChildForCells;
        boolean z;
        int i2;
        ActionMenuPresenter actionMenuPresenter = this;
        View view = null;
        boolean z2 = false;
        if (actionMenuPresenter.mMenu != null) {
            visibleItems = actionMenuPresenter.mMenu.getVisibleItems();
            size = visibleItems.size();
        } else {
            visibleItems = null;
            size = 0;
        }
        int i3 = actionMenuPresenter.mMaxItems;
        int i4 = actionMenuPresenter.mActionItemWidthLimit;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        ViewGroup viewGroup = (ViewGroup) actionMenuPresenter.mMenuView;
        boolean z3 = false;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < size; i7++) {
            MenuItemImpl menuItemImpl = visibleItems.get(i7);
            if (menuItemImpl.requiresActionButton()) {
                i5++;
            } else if (menuItemImpl.requestsActionButton()) {
                i6++;
            } else {
                z3 = true;
            }
            if (actionMenuPresenter.mExpandedActionViewsExclusive && menuItemImpl.isActionViewExpanded()) {
                i3 = 0;
            }
        }
        if (actionMenuPresenter.mReserveOverflow && (z3 || i6 + i5 > i3)) {
            i3--;
        }
        int i8 = i3 - i5;
        SparseBooleanArray sparseBooleanArray = actionMenuPresenter.mActionButtonGroups;
        sparseBooleanArray.clear();
        if (actionMenuPresenter.mStrictWidthLimit) {
            int i9 = actionMenuPresenter.mMinCellSize;
            iMeasureChildForCells = i4 / i9;
            i = i9 + ((i4 % i9) / iMeasureChildForCells);
        } else {
            i = 0;
            iMeasureChildForCells = 0;
        }
        int i10 = 0;
        int i11 = 0;
        while (i10 < size) {
            MenuItemImpl menuItemImpl2 = visibleItems.get(i10);
            if (menuItemImpl2.requiresActionButton()) {
                View itemView = actionMenuPresenter.getItemView(menuItemImpl2, view, viewGroup);
                if (actionMenuPresenter.mStrictWidthLimit) {
                    iMeasureChildForCells -= ActionMenuView.measureChildForCells(itemView, i, iMeasureChildForCells, iMakeMeasureSpec, 0);
                } else {
                    itemView.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                }
                int measuredWidth = itemView.getMeasuredWidth();
                i4 -= measuredWidth;
                if (i11 == 0) {
                    i11 = measuredWidth;
                }
                int groupId = menuItemImpl2.getGroupId();
                if (groupId != 0) {
                    sparseBooleanArray.put(groupId, true);
                }
                menuItemImpl2.setIsActionButton(true);
            } else {
                menuItemImpl2.setIsActionButton(false);
            }
            i10++;
            view = null;
        }
        int i12 = 0;
        while (i12 < size) {
            MenuItemImpl menuItemImpl3 = visibleItems.get(i12);
            if (menuItemImpl3.requestsActionButton()) {
                int groupId2 = menuItemImpl3.getGroupId();
                boolean z4 = sparseBooleanArray.get(groupId2);
                boolean z5 = (i8 > 0 || z4) && i4 > 0 && (!actionMenuPresenter.mStrictWidthLimit || iMeasureChildForCells > 0);
                boolean z6 = z5;
                i2 = size;
                if (z5) {
                    View itemView2 = actionMenuPresenter.getItemView(menuItemImpl3, null, viewGroup);
                    if (actionMenuPresenter.mStrictWidthLimit) {
                        int iMeasureChildForCells2 = ActionMenuView.measureChildForCells(itemView2, i, iMeasureChildForCells, iMakeMeasureSpec, 0);
                        iMeasureChildForCells -= iMeasureChildForCells2;
                        if (iMeasureChildForCells2 == 0) {
                            z6 = false;
                        }
                    } else {
                        itemView2.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                    }
                    boolean z7 = z6;
                    int measuredWidth2 = itemView2.getMeasuredWidth();
                    i4 -= measuredWidth2;
                    if (i11 == 0) {
                        i11 = measuredWidth2;
                    }
                    z5 = z7 & (!actionMenuPresenter.mStrictWidthLimit ? !actionMenuPresenter.mIsThemeDeviceDefaultFamily ? i4 + i11 > 0 : i4 >= 0 : i4 < 0);
                }
                if (z5 && groupId2 != 0) {
                    sparseBooleanArray.put(groupId2, true);
                } else if (z4) {
                    sparseBooleanArray.put(groupId2, false);
                    for (int i13 = 0; i13 < i12; i13++) {
                        MenuItemImpl menuItemImpl4 = visibleItems.get(i13);
                        if (menuItemImpl4.getGroupId() == groupId2) {
                            if (menuItemImpl4.isActionButton()) {
                                i8++;
                            }
                            menuItemImpl4.setIsActionButton(false);
                        }
                    }
                }
                z = false;
                if (z5) {
                    i8--;
                }
                menuItemImpl3.setIsActionButton(z5);
            } else {
                z = z2;
                i2 = size;
            }
            i12++;
            z2 = z;
            size = i2;
            actionMenuPresenter = this;
        }
        return true;
    }

    @Override // com.android.internal.view.menu.BaseMenuPresenter, com.android.internal.view.menu.MenuPresenter
    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        dismissPopupMenus();
        super.onCloseMenu(menuBuilder, z);
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.openSubMenuId = this.mOpenSubMenuId;
        return savedState;
    }

    @Override // com.android.internal.view.menu.MenuPresenter
    public void onRestoreInstanceState(Parcelable parcelable) {
        MenuItem menuItemFindItem;
        SavedState savedState = (SavedState) parcelable;
        if (savedState.openSubMenuId <= 0 || (menuItemFindItem = this.mMenu.findItem(savedState.openSubMenuId)) == null) {
            return;
        }
        onSubMenuSelected((SubMenuBuilder) menuItemFindItem.getSubMenu());
    }

    @Override // android.view.ActionProvider.SubUiVisibilityListener
    public void onSubUiVisibilityChanged(boolean z) {
        if (z) {
            super.onSubMenuSelected(null);
        } else if (this.mMenu != null) {
            this.mMenu.close(false);
        }
    }

    public void setMenuView(ActionMenuView actionMenuView) {
        if (this.mMenuView != null) {
            ((View) this.mMenuView).removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
        }
        this.mMenuView = actionMenuView;
        actionMenuView.initialize(this.mMenu);
        actionMenuView.addOnAttachStateChangeListener(this.mAttachStateChangeListener);
    }

    private static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.ActionMenuPresenter.SavedState.1
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
        public int openSubMenuId;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        SavedState() {
        }

        SavedState(Parcel parcel) {
            this.openSubMenuId = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.openSubMenuId);
        }
    }

    private class OverflowMenuButton extends ImageButton implements ActionMenuView.ActionMenuChildView {
        @Override // android.widget.ActionMenuView.ActionMenuChildView
        public boolean needsDividerAfter() {
            return false;
        }

        @Override // android.widget.ActionMenuView.ActionMenuChildView
        public boolean needsDividerBefore() {
            return false;
        }

        public OverflowMenuButton(Context context) {
            super(context, null, 16843510);
            setClickable(true);
            setFocusable(true);
            setVisibility(0);
            setEnabled(true);
        }

        @Override // android.view.View
        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            playSoundEffect(0);
            ActionMenuPresenter.this.showOverflowMenu();
            return true;
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
            accessibilityNodeInfo.setCanOpenPopup(true);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.widget.ImageView, android.view.View
        public boolean setFrame(int i, int i2, int i3, int i4) {
            boolean frame = super.setFrame(i, i2, i3, i4);
            if (!ActionMenuPresenter.this.mIsThemeDeviceDefaultFamily) {
                Drawable drawable = getDrawable();
                Drawable background = getBackground();
                if (drawable != null && background != null) {
                    int width = getWidth();
                    int height = getHeight();
                    int iMax = Math.max(width, height) / 2;
                    int paddingLeft = (width + (getPaddingLeft() - getPaddingRight())) / 2;
                    int paddingTop = (height + (getPaddingTop() - getPaddingBottom())) / 2;
                    background.setHotspotBounds(paddingLeft - iMax, paddingTop - iMax, paddingLeft + iMax, paddingTop + iMax);
                }
            }
            return frame;
        }
    }

    class SemOverflowMenuButtonContainer extends FrameLayout implements ActionMenuView.ActionMenuChildView {
        private static final int BADGE_LIMIT_NUMBER = 99;
        private float mBadgeAdditionalWidth;
        private float mBadgeDefaultWidth;
        private TextView mBadgeView;
        private SemOverflowMenuButton mButtonView;
        private NumberFormat mNumberFormat;

        @Override // android.widget.ActionMenuView.ActionMenuChildView
        public boolean needsDividerAfter() {
            return false;
        }

        @Override // android.widget.ActionMenuView.ActionMenuChildView
        public boolean needsDividerBefore() {
            return false;
        }

        public SemOverflowMenuButtonContainer(Context context) {
            super(context);
            this.mNumberFormat = NumberFormat.getInstance(Locale.getDefault());
            SemOverflowMenuButton semTextOverflowMenuButton = ActionMenuPresenter.this.mUseTextItemMode ? ActionMenuPresenter.this.new SemTextOverflowMenuButton(context) : ActionMenuPresenter.this.new SemImageOverflowMenuButton(context);
            this.mButtonView = semTextOverflowMenuButton;
            addView(semTextOverflowMenuButton, new FrameLayout.LayoutParams(-2, -2));
            TextView textView = (TextView) ActionMenuPresenter.this.mInflater.inflate(R.layout.sem_action_menu_item_badge, (ViewGroup) this, false);
            this.mBadgeView = textView;
            addView(textView);
            this.mBadgeAdditionalWidth = getResources().getDimensionPixelSize(R.dimen.sem_badge_additional_width);
            this.mBadgeDefaultWidth = getResources().getDimension(R.dimen.sem_badge_default_width);
        }

        @Override // android.view.View
        protected void onConfigurationChanged(Configuration configuration) {
            int i;
            super.onConfigurationChanged(configuration);
            TextView textView = this.mBadgeView;
            if (textView == null || TextUtils.isEmpty(textView.getText())) {
                i = 0;
            } else {
                this.mBadgeView.setTextSize(0, (int) getResources().getDimension(R.dimen.sem_menu_item_badge_text_size));
                this.mBadgeDefaultWidth = getResources().getDimension(R.dimen.sem_badge_default_width);
                this.mBadgeAdditionalWidth = getResources().getDimensionPixelSize(R.dimen.sem_badge_additional_width);
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mBadgeView.getLayoutParams();
                marginLayoutParams.width = (int) (this.mBadgeDefaultWidth + (this.mBadgeView.getText().length() * this.mBadgeAdditionalWidth));
                marginLayoutParams.height = (int) getResources().getDimension(R.dimen.sem_menu_item_badge_size);
                int dimension = (int) getResources().getDimension(R.dimen.sem_menu_item_badge_right_margin);
                if (1 == getLayoutDirection()) {
                    marginLayoutParams.rightMargin = dimension;
                } else {
                    marginLayoutParams.leftMargin = dimension;
                }
                this.mBadgeView.setLayoutParams(marginLayoutParams);
                i = marginLayoutParams.width + dimension;
            }
            ActionMenuView.LayoutParams layoutParams = (ActionMenuView.LayoutParams) getLayoutParams();
            if (layoutParams == null || this.mButtonView == null) {
                return;
            }
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, R.styleable.View, 16843510, 0);
            int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(36, -1);
            typedArrayObtainStyledAttributes.recycle();
            if (i > dimensionPixelSize) {
                layoutParams.width = i;
            } else {
                layoutParams.width = dimensionPixelSize;
            }
            setLayoutParams(layoutParams);
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x004d  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        void invalidateBadgeText() {
            String str;
            ActionMenuView actionMenuView = (ActionMenuView) ActionMenuPresenter.this.mMenuView;
            int iSemGetSumOfDigitsInBadges = actionMenuView.semGetSumOfDigitsInBadges();
            if (iSemGetSumOfDigitsInBadges == 0) {
                this.mBadgeView.lambda$setTextAsync$0((CharSequence) null);
                this.mBadgeView.setVisibility(8);
                return;
            }
            String overflowBadgeText = actionMenuView.getOverflowBadgeText();
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mBadgeView.getLayoutParams();
            if (overflowBadgeText != null) {
                str = "";
                if (overflowBadgeText.equals("")) {
                    if (iSemGetSumOfDigitsInBadges > 99) {
                        iSemGetSumOfDigitsInBadges = 99;
                    }
                    str = this.mNumberFormat.format(iSemGetSumOfDigitsInBadges);
                    marginLayoutParams.height = (int) (this.mBadgeDefaultWidth + this.mBadgeAdditionalWidth);
                    marginLayoutParams.width = (int) (this.mBadgeDefaultWidth + (str.length() * this.mBadgeAdditionalWidth));
                    marginLayoutParams.setMarginEnd((int) getResources().getDimension(R.dimen.sem_menu_item_number_badge_end_margin));
                    marginLayoutParams.topMargin = (int) getResources().getDimension(R.dimen.sem_menu_item_number_badge_top_margin);
                } else {
                    marginLayoutParams.height = (int) getResources().getDimension(R.dimen.sem_menu_item_badge_size);
                    marginLayoutParams.width = (int) getResources().getDimension(R.dimen.sem_menu_item_badge_size);
                }
            }
            this.mBadgeView.setLayoutParams(marginLayoutParams);
            this.mBadgeView.lambda$setTextAsync$0(str);
            this.mBadgeView.setVisibility(0);
        }

        void setImageDrawable(Drawable drawable) {
            this.mButtonView.setImageDrawable(drawable);
        }

        Drawable getDrawable() {
            return this.mButtonView.getDrawable();
        }
    }

    private abstract class SemOverflowMenuButton extends TextView {
        abstract Drawable getDrawable();

        @Override // android.widget.TextView, android.view.View
        public void jumpDrawablesToCurrentState() {
        }

        abstract void setImageDrawable(Drawable drawable);

        public SemOverflowMenuButton(ActionMenuPresenter actionMenuPresenter, Context context) {
            super(context, null, 16843510);
            setClickable(true);
            setFocusable(true);
            setVisibility(0);
            setEnabled(true);
            semSetButtonShapeEnabled(true);
        }

        @Override // android.widget.TextView, android.view.View
        protected void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, R.styleable.View, 16843510, 0);
            setContentDescription(typedArrayObtainStyledAttributes.getText(44));
            typedArrayObtainStyledAttributes.recycle();
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName("android.widget.Button");
            accessibilityNodeInfo.setCanOpenPopup(true);
        }
    }

    private class SemImageOverflowMenuButton extends SemOverflowMenuButton {
        public SemImageOverflowMenuButton(Context context) {
            super(ActionMenuPresenter.this, context);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.ImageView, 16843510, 0);
            Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
            if (drawable != null) {
                setImageDrawable(drawable);
            }
            typedArrayObtainStyledAttributes.recycle();
            setLongClickable(true);
            ActionMenuPresenter.this.mTooltipText = getTooltipText();
        }

        @Override // android.widget.ActionMenuPresenter.SemOverflowMenuButton, android.widget.TextView, android.view.View
        protected void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, R.styleable.View, 16843510, 0);
            setMinimumHeight(typedArrayObtainStyledAttributes.getDimensionPixelSize(37, -1));
            typedArrayObtainStyledAttributes.recycle();
            TypedArray typedArrayObtainStyledAttributes2 = getContext().obtainStyledAttributes(null, R.styleable.ImageView, 16843510, 0);
            Drawable drawable = typedArrayObtainStyledAttributes2.getDrawable(0);
            if (drawable != null) {
                setImageDrawable(drawable);
            }
            typedArrayObtainStyledAttributes2.recycle();
        }

        @Override // android.widget.ActionMenuPresenter.SemOverflowMenuButton
        void setImageDrawable(Drawable drawable) {
            setCompoundDrawablesWithIntrinsicBounds(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
        }

        @Override // android.widget.ActionMenuPresenter.SemOverflowMenuButton
        Drawable getDrawable() {
            return getCompoundDrawables()[0];
        }

        @Override // android.widget.TextView, android.view.View
        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            playSoundEffect(0);
            if (ActionMenuPresenter.this.showOverflowMenu() && isHovered() && getTooltip() != null) {
                setTooltipNull(true);
            }
            return true;
        }

        @Override // android.view.View
        public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            if (action != 9) {
                if (action == 10) {
                    setTooltipNull(true);
                }
            } else if (!ActionMenuPresenter.this.isOverflowMenuShowPending()) {
                setTooltipText(ActionMenuPresenter.this.mTooltipText);
                setTooltipNull(false);
            }
            setTooltipOffset();
            return super.dispatchGenericMotionEvent(motionEvent);
        }

        @Override // android.widget.TextView, android.view.View
        public boolean performLongClick() {
            setTooltipOffset();
            return super.performLongClick();
        }

        protected void setTooltipOffset() {
            int navigationBarHeight;
            Context context = getContext();
            Resources resources = context.getResources();
            int[] iArr = new int[2];
            getLocationOnScreen(iArr);
            int width = getWidth();
            int height = getHeight();
            int paddingStart = getPaddingStart();
            int paddingEnd = getPaddingEnd();
            int[] iArr2 = new int[2];
            getLocationInWindow(iArr2);
            Rect rect = new Rect();
            getWindowVisibleDisplayFrame(rect);
            Display defaultDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            defaultDisplay.getRealMetrics(displayMetrics);
            View view = ActionMenuPresenter.this.mMenuView != null ? (View) ((View) ActionMenuPresenter.this.mMenuView).getParent() : null;
            int i = (!(view instanceof Toolbar) || view.getWidth() >= rect.right - rect.left) ? 0 : (iArr[0] - iArr2[0]) - rect.left;
            int i2 = iArr2[1] + height;
            if (getLayoutDirection() == 0) {
                navigationBarHeight = (((rect.right - rect.left) - (iArr2[0] + width)) + (((width - paddingStart) - paddingEnd) / 2)) - i;
                if (checkNaviBarForLandscape()) {
                    navigationBarHeight += (int) ((getNavigationBarHeight() / resources.getDisplayMetrics().density) * displayMetrics.density);
                }
            } else {
                navigationBarHeight = iArr2[0] + paddingStart + ((paddingEnd - paddingStart) / 2);
            }
            setTooltipPosition(navigationBarHeight, i2);
        }

        private boolean checkNaviBarForLandscape() {
            Context context = getContext();
            Resources resources = context.getResources();
            Rect rect = new Rect();
            getWindowVisibleDisplayFrame(rect);
            Point point = new Point();
            Display defaultDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            defaultDisplay.getRealSize(point);
            int rotation = defaultDisplay.getRotation();
            int dimension = (int) resources.getDimension(R.dimen.navigation_bar_height);
            if (rotation == 1 && rect.right + dimension >= point.x) {
                setNavigationBarHeight(point.x - rect.right);
                return true;
            }
            if (rotation != 3 || rect.left > dimension) {
                return false;
            }
            setNavigationBarHeight(rect.left);
            return true;
        }

        private void setNavigationBarHeight(int i) {
            ActionMenuPresenter.this.mNavigationBarHeight = i;
        }

        private int getNavigationBarHeight() {
            return ActionMenuPresenter.this.mNavigationBarHeight;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.widget.TextView, android.view.View
        public boolean setFrame(int i, int i2, int i3, int i4) {
            boolean frame = super.setFrame(i, i2, i3, i4);
            Drawable drawable = getDrawable();
            Drawable background = getBackground();
            if (drawable != null && background != null) {
                int width = getWidth();
                int height = getHeight();
                int paddingLeft = (getPaddingLeft() - getPaddingRight()) / 2;
                background.setHotspotBounds(paddingLeft, 0, width + paddingLeft, height);
            }
            return frame;
        }
    }

    private class SemTextOverflowMenuButton extends SemOverflowMenuButton {
        private static final float MAX_FONT_SCALE = 1.2f;
        private float mCurrentFontScale;
        private float mDefaultTextSize;

        @Override // android.widget.ActionMenuPresenter.SemOverflowMenuButton
        Drawable getDrawable() {
            return null;
        }

        @Override // android.widget.ActionMenuPresenter.SemOverflowMenuButton
        void setImageDrawable(Drawable drawable) {
        }

        public SemTextOverflowMenuButton(Context context) throws Resources.NotFoundException {
            super(ActionMenuPresenter.this, context);
            this.mCurrentFontScale = 1.0f;
            TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(null, R.styleable.Theme, 0, 0);
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(187, 0);
            setTextAppearance(resourceId);
            typedArrayObtainStyledAttributes.recycle();
            lambda$setTextAsync$0(context.getResources().getString(R.string.more_item_label));
            TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(resourceId, R.styleable.TextAppearance);
            TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes2.peekValue(0);
            typedArrayObtainStyledAttributes2.recycle();
            if (typedValuePeekValue != null) {
                this.mDefaultTextSize = TypedValue.complexToFloat(typedValuePeekValue.data);
                float f = context.getResources().getConfiguration().fontScale;
                this.mCurrentFontScale = f;
                if (f > 1.2f) {
                    this.mCurrentFontScale = 1.2f;
                }
                setTextSize(1, this.mDefaultTextSize * this.mCurrentFontScale);
            }
        }

        @Override // android.widget.ActionMenuPresenter.SemOverflowMenuButton, android.widget.TextView, android.view.View
        protected void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, R.styleable.View, 16843510, 0);
            setMinimumHeight(typedArrayObtainStyledAttributes.getDimensionPixelSize(37, -1));
            typedArrayObtainStyledAttributes.recycle();
            if (ActionMenuPresenter.this.mIsThemeDeviceDefaultFamily && configuration != null && configuration.fontScale != this.mCurrentFontScale) {
                float f = configuration.fontScale;
                this.mCurrentFontScale = f;
                if (f > 1.2f) {
                    this.mCurrentFontScale = 1.2f;
                }
                setTextSize(1, this.mDefaultTextSize * this.mCurrentFontScale);
            }
            lambda$setTextAsync$0(getContext().getResources().getString(R.string.more_item_label));
        }

        @Override // android.widget.TextView, android.view.View
        public boolean performClick() {
            if (super.performClick()) {
                return true;
            }
            playSoundEffect(0);
            ActionMenuPresenter.this.showOverflowMenu();
            return true;
        }

        @Override // android.widget.TextView, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
        }
    }

    public class OverflowTextMenuButton extends SemTextOverflowMenuButton {
        @Override // android.widget.ActionMenuPresenter.SemOverflowMenuButton, android.widget.TextView, android.view.View
        public /* bridge */ /* synthetic */ void jumpDrawablesToCurrentState() {
            super.jumpDrawablesToCurrentState();
        }

        @Override // android.widget.ActionMenuPresenter.SemOverflowMenuButton, android.view.View
        public /* bridge */ /* synthetic */ void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        }

        @Override // android.widget.ActionMenuPresenter.SemTextOverflowMenuButton, android.widget.TextView, android.view.View
        public /* bridge */ /* synthetic */ boolean performClick() {
            return super.performClick();
        }

        public OverflowTextMenuButton(ActionMenuPresenter actionMenuPresenter, Context context) {
            super(context);
        }
    }

    private class OverflowPopup extends MenuPopupHelper {
        public OverflowPopup(Context context, MenuBuilder menuBuilder, View view, boolean z) {
            super(context, menuBuilder, view, z, 16843844);
            setGravity(Gravity.END);
            setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
        }

        @Override // com.android.internal.view.menu.MenuPopupHelper
        protected void onDismiss() {
            if (ActionMenuPresenter.this.mMenu != null) {
                ActionMenuPresenter.this.mMenu.close();
            }
            ActionMenuPresenter.this.mOverflowPopup = null;
            super.onDismiss();
        }
    }

    private class ActionButtonSubmenu extends MenuPopupHelper {
        public ActionButtonSubmenu(Context context, SubMenuBuilder subMenuBuilder, View view) {
            super(context, subMenuBuilder, view, false, 16843844);
            if (!((MenuItemImpl) subMenuBuilder.getItem()).isActionButton()) {
                setAnchorView(ActionMenuPresenter.this.mSemOverflowButton == null ? (View) ActionMenuPresenter.this.mMenuView : ActionMenuPresenter.this.mSemOverflowButton);
            }
            if (ActionMenuPresenter.this.mIsThemeDeviceDefaultFamily) {
                setGravity(Gravity.END);
            }
            setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
        }

        @Override // com.android.internal.view.menu.MenuPopupHelper
        protected void onDismiss() {
            ActionMenuPresenter.this.mActionButtonPopup = null;
            ActionMenuPresenter.this.mOpenSubMenuId = 0;
            super.onDismiss();
        }
    }

    private class PopupPresenterCallback implements MenuPresenter.Callback {
        private PopupPresenterCallback() {
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            if (menuBuilder == null) {
                return false;
            }
            ActionMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder) menuBuilder).getItem().getItemId();
            MenuPresenter.Callback callback = ActionMenuPresenter.this.getCallback();
            if (callback != null) {
                return callback.onOpenSubMenu(menuBuilder);
            }
            return false;
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            if (menuBuilder instanceof SubMenuBuilder) {
                menuBuilder.getRootMenu().close(false);
            }
            MenuPresenter.Callback callback = ActionMenuPresenter.this.getCallback();
            if (callback != null) {
                callback.onCloseMenu(menuBuilder, z);
            }
        }
    }

    private class OpenOverflowRunnable implements Runnable {
        private OverflowPopup mPopup;

        public OpenOverflowRunnable(OverflowPopup overflowPopup) {
            this.mPopup = overflowPopup;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (ActionMenuPresenter.this.mMenu != null) {
                ActionMenuPresenter.this.mMenu.changeMenuMode();
            }
            View view = (View) ActionMenuPresenter.this.mMenuView;
            if (ActionMenuPresenter.this.mIsThemeDeviceDefaultFamily) {
                if (view != null && view.getWindowToken() != null && this.mPopup.tryShow(0, 0)) {
                    ActionMenuPresenter.this.mOverflowPopup = this.mPopup;
                }
            } else if (view != null && view.getWindowToken() != null && this.mPopup.tryShow()) {
                ActionMenuPresenter.this.mOverflowPopup = this.mPopup;
            }
            ActionMenuPresenter.this.mPostedOpenRunnable = null;
        }
    }

    private class ActionMenuPopupCallback extends ActionMenuItemView.PopupCallback {
        private ActionMenuPopupCallback() {
        }

        @Override // com.android.internal.view.menu.ActionMenuItemView.PopupCallback
        public ShowableListMenu getPopup() {
            if (ActionMenuPresenter.this.mActionButtonPopup != null) {
                return ActionMenuPresenter.this.mActionButtonPopup.getPopup();
            }
            return null;
        }
    }

    private static class MenuItemLayoutInfo {
        int left;
        int top;
        View view;

        MenuItemLayoutInfo(View view, boolean z) {
            this.left = view.getLeft();
            this.top = view.getTop();
            if (z) {
                this.left = (int) (this.left + view.getTranslationX());
                this.top = (int) (this.top + view.getTranslationY());
            }
            this.view = view;
        }
    }

    private static class ItemAnimationInfo {
        static final int FADE_IN = 1;
        static final int FADE_OUT = 2;
        static final int MOVE = 0;
        int animType;
        Animator animator;
        int id;
        MenuItemLayoutInfo menuItemLayoutInfo;

        ItemAnimationInfo(int i, MenuItemLayoutInfo menuItemLayoutInfo, Animator animator, int i2) {
            this.id = i;
            this.menuItemLayoutInfo = menuItemLayoutInfo;
            this.animator = animator;
            this.animType = i2;
        }
    }
}
