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
import com.android.internal.C4337R;
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

/* loaded from: classes4.dex */
public class ActionMenuPresenter extends BaseMenuPresenter
    implements ActionProvider.SubUiVisibilityListener {
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
    super(context, C4337R.layout.action_menu_layout, C4337R.layout.action_menu_item_layout);
    this.mActionButtonGroups = new SparseBooleanArray();
    this.mPopupPresenterCallback = new PopupPresenterCallback();
    this.mPreLayoutItems = new SparseArray<>();
    this.mPostLayoutItems = new SparseArray<>();
    this.mRunningItemAnimations = new ArrayList();
    this.mItemAnimationPreDrawListener =
        new ViewTreeObserver
            .OnPreDrawListener() { // from class: android.widget.ActionMenuPresenter.1
          @Override // android.view.ViewTreeObserver.OnPreDrawListener
          public boolean onPreDraw() {
            ActionMenuPresenter.this.computeMenuItemAnimationInfo(false);
            ((View) ActionMenuPresenter.this.mMenuView)
                .getViewTreeObserver()
                .removeOnPreDrawListener(this);
            ActionMenuPresenter.this.runItemAnimations();
            return true;
          }
        };
    this.mAttachStateChangeListener =
        new View.OnAttachStateChangeListener() { // from class: android.widget.ActionMenuPresenter.2
          @Override // android.view.View.OnAttachStateChangeListener
          public void onViewAttachedToWindow(View v) {}

          @Override // android.view.View.OnAttachStateChangeListener
          public void onViewDetachedFromWindow(View v) {
            ((View) ActionMenuPresenter.this.mMenuView)
                .getViewTreeObserver()
                .removeOnPreDrawListener(ActionMenuPresenter.this.mItemAnimationPreDrawListener);
            ActionMenuPresenter.this.mPreLayoutItems.clear();
            ActionMenuPresenter.this.mPostLayoutItems.clear();
          }
        };
    boolean z = false;
    this.mNavigationBarHeight = 0;
    TypedValue themeValue = new TypedValue();
    context.getTheme().resolveAttribute(C4337R.attr.parentIsDeviceDefault, themeValue, true);
    boolean z2 = themeValue.data != 0;
    this.mIsThemeDeviceDefaultFamily = z2;
    if (z2 && context.getResources().getBoolean(C4337R.bool.tw_action_bar_text_item_mode)) {
      z = true;
    }
    this.mUseTextItemMode = z;
  }

  @Override // com.android.internal.view.menu.BaseMenuPresenter,
            // com.android.internal.view.menu.MenuPresenter
  public void initForMenu(Context context, MenuBuilder menu) {
    super.initForMenu(context, menu);
    if (this.mIsThemeDeviceDefaultFamily) {
      super.setMenuLayoutResources(
          C4337R.layout.sem_action_menu_layout, C4337R.layout.sem_action_menu_item_layout);
    }
    Resources res = context.getResources();
    ActionBarPolicy abp = ActionBarPolicy.get(context);
    if (!this.mReserveOverflowSet) {
      this.mReserveOverflow = abp.showsOverflowMenuButton();
    }
    if (!this.mWidthLimitSet) {
      if (this.mIsThemeDeviceDefaultFamily) {
        this.mWidthLimit = (int) (res.getDisplayMetrics().widthPixels * ACTION_MENU_WIDTH_LIMIT);
      } else {
        this.mWidthLimit = abp.getEmbeddedMenuWidthLimit();
      }
    }
    if (!this.mMaxItemsSet) {
      this.mMaxItems = abp.getMaxActionButtons();
    }
    int width = this.mWidthLimit;
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
        int spec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mSemOverflowButton.measure(spec, spec);
      }
      width -= this.mSemOverflowButton.getMeasuredWidth();
    } else {
      this.mOverflowButton = null;
      this.mSemOverflowButton = null;
    }
    this.mActionItemWidthLimit = width;
    this.mMinCellSize = (int) (res.getDisplayMetrics().density * 56.0f);
  }

  public void onConfigurationChanged(Configuration newConfig) {
    View view;
    if (this.mSemOverflowButton != null) {
      TypedArray a = this.mContext.obtainStyledAttributes(null, C4337R.styleable.View, 16843510, 0);
      this.mSemOverflowButton.setMinimumHeight(a.getDimensionPixelSize(37, -1));
      View view2 = this.mSemOverflowButton;
      if ((view2 instanceof SemOverflowMenuButtonContainer) && view2.getParent() == null) {
        this.mSemOverflowButton.dispatchConfigurationChanged(newConfig);
      }
      a.recycle();
    }
    if (!this.mMaxItemsSet) {
      this.mMaxItems = ActionBarPolicy.get(this.mContext).getMaxActionButtons();
    }
    if (!this.mWidthLimitSet) {
      if (this.mIsThemeDeviceDefaultFamily) {
        this.mWidthLimit =
            (int)
                (this.mContext.getResources().getDisplayMetrics().widthPixels
                    * ACTION_MENU_WIDTH_LIMIT);
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

  public void setWidthLimit(int width, boolean strict) {
    this.mWidthLimit = width;
    this.mStrictWidthLimit = strict;
    this.mWidthLimitSet = true;
  }

  public void setReserveOverflow(boolean reserveOverflow) {
    this.mReserveOverflow = reserveOverflow;
    this.mReserveOverflowSet = true;
  }

  public void setItemLimit(int itemCount) {
    this.mMaxItems = itemCount;
    this.mMaxItemsSet = true;
  }

  public void setExpandedActionViewsExclusive(boolean isExclusive) {
    this.mExpandedActionViewsExclusive = isExclusive;
  }

  public void setOverflowIcon(Drawable icon) {
    View view = this.mSemOverflowButton;
    if (view != null) {
      if (this.mIsThemeDeviceDefaultFamily) {
        ((SemOverflowMenuButtonContainer) view).setImageDrawable(icon);
        return;
      } else {
        ((OverflowMenuButton) view).lambda$setImageURIAsync$2(icon);
        return;
      }
    }
    this.mPendingOverflowIconSet = true;
    this.mPendingOverflowIcon = icon;
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
  @Override // com.android.internal.view.menu.BaseMenuPresenter,
            // com.android.internal.view.menu.MenuPresenter
  public MenuView getMenuView(ViewGroup root) {
    Object obj = this.mMenuView;
    MenuView menuView = super.getMenuView(root);
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
  public View getItemView(MenuItemImpl item, View convertView, ViewGroup parent) {
    View actionView = item.getActionView();
    if (actionView == null || item.hasCollapsibleActionView()) {
      actionView = super.getItemView(item, convertView, parent);
    }
    actionView.setVisibility(item.isActionViewExpanded() ? 8 : 0);
    ActionMenuView menuParent = (ActionMenuView) parent;
    ViewGroup.LayoutParams lp = actionView.getLayoutParams();
    if (!menuParent.checkLayoutParams(lp)) {
      actionView.setLayoutParams(menuParent.generateLayoutParams(lp));
    }
    return actionView;
  }

  @Override // com.android.internal.view.menu.BaseMenuPresenter
  public void bindItemView(MenuItemImpl item, MenuView.ItemView itemView) {
    itemView.initialize(item, 0);
    ActionMenuView menuView = (ActionMenuView) this.mMenuView;
    ActionMenuItemView actionItemView = (ActionMenuItemView) itemView;
    actionItemView.setItemInvoker(menuView);
    if (this.mPopupCallback == null) {
      this.mPopupCallback = new ActionMenuPopupCallback();
    }
    actionItemView.setPopupCallback(this.mPopupCallback);
  }

  @Override // com.android.internal.view.menu.BaseMenuPresenter
  public boolean shouldIncludeItem(int childIndex, MenuItemImpl item) {
    return item.isActionButton();
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void computeMenuItemAnimationInfo(boolean preLayout) {
    ViewGroup menuView = (ViewGroup) this.mMenuView;
    int count = menuView.getChildCount();
    SparseArray items = preLayout ? this.mPreLayoutItems : this.mPostLayoutItems;
    for (int i = 0; i < count; i++) {
      View child = menuView.getChildAt(i);
      int id = child.getId();
      if (id > 0 && child.getWidth() != 0 && child.getHeight() != 0) {
        MenuItemLayoutInfo info = new MenuItemLayoutInfo(child, preLayout);
        items.put(id, info);
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void runItemAnimations() {
    ObjectAnimator anim;
    for (int i = 0; i < this.mPreLayoutItems.size(); i++) {
      int id = this.mPreLayoutItems.keyAt(i);
      final MenuItemLayoutInfo menuItemLayoutInfoPre = this.mPreLayoutItems.get(id);
      int postLayoutIndex = this.mPostLayoutItems.indexOfKey(id);
      if (postLayoutIndex >= 0) {
        MenuItemLayoutInfo menuItemLayoutInfoPost = this.mPostLayoutItems.valueAt(postLayoutIndex);
        PropertyValuesHolder pvhX = null;
        PropertyValuesHolder pvhY = null;
        if (menuItemLayoutInfoPre.left != menuItemLayoutInfoPost.left) {
          pvhX =
              PropertyValuesHolder.ofFloat(
                  View.TRANSLATION_X,
                  menuItemLayoutInfoPre.left - menuItemLayoutInfoPost.left,
                  0.0f);
        }
        if (menuItemLayoutInfoPre.top != menuItemLayoutInfoPost.top) {
          pvhY =
              PropertyValuesHolder.ofFloat(
                  View.TRANSLATION_Y, menuItemLayoutInfoPre.top - menuItemLayoutInfoPost.top, 0.0f);
        }
        if (pvhX != null || pvhY != null) {
          for (int j = 0; j < this.mRunningItemAnimations.size(); j++) {
            ItemAnimationInfo oldInfo = this.mRunningItemAnimations.get(j);
            if (oldInfo.f598id == id && oldInfo.animType == 0) {
              oldInfo.animator.cancel();
            }
          }
          if (pvhX != null) {
            if (pvhY != null) {
              anim = ObjectAnimator.ofPropertyValuesHolder(menuItemLayoutInfoPost.view, pvhX, pvhY);
            } else {
              anim = ObjectAnimator.ofPropertyValuesHolder(menuItemLayoutInfoPost.view, pvhX);
            }
          } else {
            anim = ObjectAnimator.ofPropertyValuesHolder(menuItemLayoutInfoPost.view, pvhY);
          }
          anim.setDuration(150L);
          anim.start();
          ItemAnimationInfo info = new ItemAnimationInfo(id, menuItemLayoutInfoPost, anim, 0);
          this.mRunningItemAnimations.add(info);
          anim.addListener(
              new AnimatorListenerAdapter() { // from class: android.widget.ActionMenuPresenter.3
                @Override // android.animation.AnimatorListenerAdapter,
                          // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                  for (int j2 = 0;
                      j2 < ActionMenuPresenter.this.mRunningItemAnimations.size();
                      j2++) {
                    if (((ItemAnimationInfo)
                                ActionMenuPresenter.this.mRunningItemAnimations.get(j2))
                            .animator
                        == animation) {
                      ActionMenuPresenter.this.mRunningItemAnimations.remove(j2);
                      return;
                    }
                  }
                }
              });
        }
        this.mPostLayoutItems.remove(id);
      } else {
        float oldAlpha = 1.0f;
        for (int j2 = 0; j2 < this.mRunningItemAnimations.size(); j2++) {
          ItemAnimationInfo oldInfo2 = this.mRunningItemAnimations.get(j2);
          if (oldInfo2.f598id == id && oldInfo2.animType == 1) {
            oldAlpha = oldInfo2.menuItemLayoutInfo.view.getAlpha();
            oldInfo2.animator.cancel();
          }
        }
        ObjectAnimator anim2 =
            ObjectAnimator.ofFloat(menuItemLayoutInfoPre.view, View.ALPHA, oldAlpha, 0.0f);
        ((ViewGroup) this.mMenuView).getOverlay().add(menuItemLayoutInfoPre.view);
        anim2.setDuration(150L);
        anim2.start();
        ItemAnimationInfo info2 = new ItemAnimationInfo(id, menuItemLayoutInfoPre, anim2, 2);
        this.mRunningItemAnimations.add(info2);
        anim2.addListener(
            new AnimatorListenerAdapter() { // from class: android.widget.ActionMenuPresenter.4
              @Override // android.animation.AnimatorListenerAdapter,
                        // android.animation.Animator.AnimatorListener
              public void onAnimationEnd(Animator animation) {
                int j3 = 0;
                while (true) {
                  if (j3 >= ActionMenuPresenter.this.mRunningItemAnimations.size()) {
                    break;
                  }
                  if (((ItemAnimationInfo) ActionMenuPresenter.this.mRunningItemAnimations.get(j3))
                          .animator
                      != animation) {
                    j3++;
                  } else {
                    ActionMenuPresenter.this.mRunningItemAnimations.remove(j3);
                    break;
                  }
                }
                ((ViewGroup) ActionMenuPresenter.this.mMenuView)
                    .getOverlay()
                    .remove(menuItemLayoutInfoPre.view);
              }
            });
      }
    }
    for (int i2 = 0; i2 < this.mPostLayoutItems.size(); i2++) {
      int id2 = this.mPostLayoutItems.keyAt(i2);
      int postLayoutIndex2 = this.mPostLayoutItems.indexOfKey(id2);
      if (postLayoutIndex2 >= 0) {
        MenuItemLayoutInfo menuItemLayoutInfo = this.mPostLayoutItems.valueAt(postLayoutIndex2);
        float oldAlpha2 = 0.0f;
        for (int j3 = 0; j3 < this.mRunningItemAnimations.size(); j3++) {
          ItemAnimationInfo oldInfo3 = this.mRunningItemAnimations.get(j3);
          if (oldInfo3.f598id == id2 && oldInfo3.animType == 2) {
            oldAlpha2 = oldInfo3.menuItemLayoutInfo.view.getAlpha();
            oldInfo3.animator.cancel();
          }
        }
        ObjectAnimator anim3 =
            ObjectAnimator.ofFloat(menuItemLayoutInfo.view, View.ALPHA, oldAlpha2, 1.0f);
        anim3.start();
        anim3.setDuration(150L);
        ItemAnimationInfo info3 = new ItemAnimationInfo(id2, menuItemLayoutInfo, anim3, 1);
        this.mRunningItemAnimations.add(info3);
        anim3.addListener(
            new AnimatorListenerAdapter() { // from class: android.widget.ActionMenuPresenter.5
              @Override // android.animation.AnimatorListenerAdapter,
                        // android.animation.Animator.AnimatorListener
              public void onAnimationEnd(Animator animation) {
                for (int j4 = 0;
                    j4 < ActionMenuPresenter.this.mRunningItemAnimations.size();
                    j4++) {
                  if (((ItemAnimationInfo) ActionMenuPresenter.this.mRunningItemAnimations.get(j4))
                          .animator
                      == animation) {
                    ActionMenuPresenter.this.mRunningItemAnimations.remove(j4);
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
    ((View) this.mMenuView)
        .getViewTreeObserver()
        .addOnPreDrawListener(this.mItemAnimationPreDrawListener);
  }

  @Override // com.android.internal.view.menu.BaseMenuPresenter,
            // com.android.internal.view.menu.MenuPresenter
  public void updateMenuView(boolean cleared) {
    View view;
    if (this.mMenuView == null) {
      Log.m96e(TAG, "ActionMenuPresenter::updateMenuView() mMenuView is null");
      return;
    }
    super.updateMenuView(cleared);
    ((View) this.mMenuView).requestLayout();
    if (this.mMenu != null) {
      ArrayList<MenuItemImpl> actionItems = this.mMenu.getActionItems();
      int count = actionItems.size();
      for (int i = 0; i < count; i++) {
        ActionProvider provider = actionItems.get(i).getActionProvider();
        if (provider != null) {
          provider.setSubUiVisibilityListener(this);
        }
      }
    }
    ArrayList<MenuItemImpl> nonActionItems =
        this.mMenu != null ? this.mMenu.getNonActionItems() : null;
    boolean hasOverflow = false;
    if (this.mReserveOverflow && nonActionItems != null) {
      int count2 = nonActionItems.size();
      if (count2 == 1) {
        hasOverflow = !nonActionItems.get(0).isActionViewExpanded();
      } else {
        hasOverflow = count2 > 0;
      }
    }
    if (hasOverflow) {
      if (this.mSemOverflowButton == null) {
        if (this.mIsThemeDeviceDefaultFamily) {
          this.mSemOverflowButton = new SemOverflowMenuButtonContainer(this.mSystemContext);
        } else {
          OverflowMenuButton overflowMenuButton = new OverflowMenuButton(this.mSystemContext);
          this.mOverflowButton = overflowMenuButton;
          this.mSemOverflowButton = overflowMenuButton;
        }
      }
      ActionMenuView menuView = (ActionMenuView) this.mMenuView;
      ViewRootImpl viewRootImpl = menuView.getViewRootImpl();
      if (viewRootImpl != null && (view = viewRootImpl.getView()) != null) {
        this.mSemOverflowButton.setLayoutDirection(view.getLayoutDirection());
      }
      ViewGroup parent = (ViewGroup) this.mSemOverflowButton.getParent();
      if (parent != this.mMenuView) {
        if (parent != null) {
          parent.removeView(this.mSemOverflowButton);
        }
        menuView.addView(this.mSemOverflowButton, menuView.generateOverflowButtonLayoutParams());
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
  public boolean filterLeftoverView(ViewGroup parent, int childIndex) {
    if (parent.getChildAt(childIndex) == this.mSemOverflowButton) {
      return false;
    }
    return super.filterLeftoverView(parent, childIndex);
  }

  @Override // com.android.internal.view.menu.BaseMenuPresenter,
            // com.android.internal.view.menu.MenuPresenter
  public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
    if (subMenu == null || !subMenu.hasVisibleItems()) {
      return false;
    }
    SubMenuBuilder topSubMenu = subMenu;
    while (topSubMenu.getParentMenu() != this.mMenu) {
      topSubMenu = (SubMenuBuilder) topSubMenu.getParentMenu();
    }
    View anchor = findViewForItem(topSubMenu.getItem());
    if (anchor == null) {
      return false;
    }
    this.mOpenSubMenuId = subMenu.getItem().getItemId();
    boolean preserveIconSpacing = false;
    int count = subMenu.size();
    int i = 0;
    while (true) {
      if (i >= count) {
        break;
      }
      MenuItem childItem = subMenu.getItem(i);
      if (!childItem.isVisible() || childItem.getIcon() == null) {
        i++;
      } else {
        preserveIconSpacing = true;
        break;
      }
    }
    ActionButtonSubmenu actionButtonSubmenu =
        new ActionButtonSubmenu(this.mContext, subMenu, anchor);
    this.mActionButtonPopup = actionButtonSubmenu;
    actionButtonSubmenu.setForceShowIcon(preserveIconSpacing);
    this.mActionButtonPopup.show();
    super.onSubMenuSelected(subMenu);
    return true;
  }

  /* JADX WARN: Multi-variable type inference failed */
  private View findViewForItem(MenuItem item) {
    ViewGroup parent = (ViewGroup) this.mMenuView;
    if (parent == null) {
      return null;
    }
    int count = parent.getChildCount();
    for (int i = 0; i < count; i++) {
      View childAt = parent.getChildAt(i);
      if ((childAt instanceof MenuView.ItemView)
          && ((MenuView.ItemView) childAt).getItemData() == item) {
        return childAt;
      }
    }
    return null;
  }

  public boolean showOverflowMenu() {
    if (this.mReserveOverflow
        && !isOverflowMenuShowing()
        && this.mMenu != null
        && this.mMenuView != null
        && this.mPostedOpenRunnable == null
        && !this.mMenu.getNonActionItems().isEmpty()) {
      OverflowPopup popup =
          new OverflowPopup(this.mContext, this.mMenu, this.mSemOverflowButton, true);
      this.mPostedOpenRunnable = new OpenOverflowRunnable(popup);
      ((View) this.mMenuView).post(this.mPostedOpenRunnable);
      super.onSubMenuSelected(null);
      return true;
    }
    return false;
  }

  public boolean hideOverflowMenu() {
    if (this.mPostedOpenRunnable != null && this.mMenuView != null) {
      ((View) this.mMenuView).removeCallbacks(this.mPostedOpenRunnable);
      this.mPostedOpenRunnable = null;
      return true;
    }
    MenuPopupHelper popup = this.mOverflowPopup;
    if (popup != null) {
      popup.dismiss();
      return true;
    }
    return false;
  }

  public boolean dismissPopupMenus() {
    boolean result = hideOverflowMenu();
    return result | hideSubMenus();
  }

  public boolean hideSubMenus() {
    ActionButtonSubmenu actionButtonSubmenu = this.mActionButtonPopup;
    if (actionButtonSubmenu != null) {
      actionButtonSubmenu.dismiss();
      return true;
    }
    return false;
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

  /* JADX WARN: Removed duplicated region for block: B:114:0x0148  */
  /* JADX WARN: Removed duplicated region for block: B:63:0x00fd  */
  /* JADX WARN: Removed duplicated region for block: B:80:0x0185  */
  /* JADX WARN: Removed duplicated region for block: B:85:0x015a  */
  /* JADX WARN: Removed duplicated region for block: B:99:0x0182  */
  @Override // com.android.internal.view.menu.BaseMenuPresenter,
            // com.android.internal.view.menu.MenuPresenter
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean flagActionItems() {
    ArrayList<MenuItemImpl> visibleItems;
    int itemsSize;
    int itemsSize2;
    int querySpec;
    int requestedItems;
    boolean isAction;
    boolean isAction2;
    int maxActions;
    int requiredItems;
    int widthLimit;
    boolean z;
    ActionMenuPresenter actionMenuPresenter = this;
    if (actionMenuPresenter.mMenu != null) {
      visibleItems = actionMenuPresenter.mMenu.getVisibleItems();
      itemsSize = visibleItems.size();
    } else {
      visibleItems = null;
      itemsSize = 0;
    }
    int maxActions2 = actionMenuPresenter.mMaxItems;
    int widthLimit2 = actionMenuPresenter.mActionItemWidthLimit;
    int querySpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
    ViewGroup parent = (ViewGroup) actionMenuPresenter.mMenuView;
    int requiredItems2 = 0;
    int requestedItems2 = 0;
    int firstActionWidth = 0;
    boolean hasOverflow = false;
    for (int i = 0; i < itemsSize; i++) {
      MenuItemImpl item = visibleItems.get(i);
      if (item.requiresActionButton()) {
        requiredItems2++;
      } else if (item.requestsActionButton()) {
        requestedItems2++;
      } else {
        hasOverflow = true;
      }
      if (actionMenuPresenter.mExpandedActionViewsExclusive && item.isActionViewExpanded()) {
        maxActions2 = 0;
      }
    }
    if (actionMenuPresenter.mReserveOverflow
        && (hasOverflow || requiredItems2 + requestedItems2 > maxActions2)) {
      maxActions2--;
    }
    int maxActions3 = maxActions2 - requiredItems2;
    SparseBooleanArray seenGroups = actionMenuPresenter.mActionButtonGroups;
    seenGroups.clear();
    int cellSize = 0;
    int cellsRemaining = 0;
    if (actionMenuPresenter.mStrictWidthLimit) {
      int i2 = actionMenuPresenter.mMinCellSize;
      cellsRemaining = widthLimit2 / i2;
      int cellSizeRemaining = widthLimit2 % i2;
      cellSize = i2 + (cellSizeRemaining / cellsRemaining);
    }
    int i3 = 0;
    while (i3 < itemsSize) {
      MenuItemImpl item2 = visibleItems.get(i3);
      if (item2.requiresActionButton()) {
        maxActions = maxActions3;
        View v = actionMenuPresenter.getItemView(item2, null, parent);
        requiredItems = requiredItems2;
        if (actionMenuPresenter.mStrictWidthLimit) {
          cellsRemaining -=
              ActionMenuView.measureChildForCells(v, cellSize, cellsRemaining, querySpec2, 0);
        } else {
          v.measure(querySpec2, querySpec2);
        }
        int measuredWidth = v.getMeasuredWidth();
        int widthLimit3 = widthLimit2 - measuredWidth;
        if (firstActionWidth == 0) {
          firstActionWidth = measuredWidth;
        }
        int groupId = item2.getGroupId();
        if (groupId == 0) {
          widthLimit = widthLimit3;
          z = true;
        } else {
          widthLimit = widthLimit3;
          z = true;
          seenGroups.put(groupId, true);
        }
        item2.setIsActionButton(z);
        widthLimit2 = widthLimit;
      } else {
        maxActions = maxActions3;
        requiredItems = requiredItems2;
        item2.setIsActionButton(false);
      }
      i3++;
      maxActions3 = maxActions;
      requiredItems2 = requiredItems;
    }
    int maxActions4 = maxActions3;
    int i4 = 0;
    while (i4 < itemsSize) {
      MenuItemImpl item3 = visibleItems.get(i4);
      if (!item3.requestsActionButton()) {
        itemsSize2 = itemsSize;
        querySpec = querySpec2;
        requestedItems = requestedItems2;
      } else {
        int groupId2 = item3.getGroupId();
        boolean inGroup = seenGroups.get(groupId2);
        if (maxActions4 <= 0 && !inGroup) {
          itemsSize2 = itemsSize;
        } else if (widthLimit2 > 0) {
          itemsSize2 = itemsSize;
          if (!actionMenuPresenter.mStrictWidthLimit || cellsRemaining > 0) {
            isAction = true;
            if (isAction) {
              querySpec = querySpec2;
              requestedItems = requestedItems2;
            } else {
              boolean isAction3 = isAction;
              requestedItems = requestedItems2;
              View v2 = actionMenuPresenter.getItemView(item3, null, parent);
              if (actionMenuPresenter.mStrictWidthLimit) {
                int cells =
                    ActionMenuView.measureChildForCells(
                        v2, cellSize, cellsRemaining, querySpec2, 0);
                cellsRemaining -= cells;
                if (cells != 0) {
                  isAction2 = isAction3;
                } else {
                  isAction2 = false;
                }
              } else {
                v2.measure(querySpec2, querySpec2);
                isAction2 = isAction3;
              }
              int measuredWidth2 = v2.getMeasuredWidth();
              widthLimit2 -= measuredWidth2;
              if (firstActionWidth == 0) {
                firstActionWidth = measuredWidth2;
              }
              querySpec = querySpec2;
              if (actionMenuPresenter.mStrictWidthLimit) {
                isAction = isAction2 & (widthLimit2 >= 0);
              } else if (actionMenuPresenter.mIsThemeDeviceDefaultFamily) {
                isAction = isAction2 & (widthLimit2 >= 0);
              } else {
                isAction = isAction2 & (widthLimit2 + firstActionWidth > 0);
              }
            }
            if (!isAction && groupId2 != 0) {
              seenGroups.put(groupId2, true);
            } else if (!inGroup) {
              seenGroups.put(groupId2, false);
              for (int j = 0; j < i4; j++) {
                MenuItemImpl areYouMyGroupie = visibleItems.get(j);
                if (areYouMyGroupie.getGroupId() == groupId2) {
                  if (areYouMyGroupie.isActionButton()) {
                    maxActions4++;
                  }
                  areYouMyGroupie.setIsActionButton(false);
                }
              }
            }
            if (isAction) {
              maxActions4--;
            }
            item3.setIsActionButton(isAction);
          }
        } else {
          itemsSize2 = itemsSize;
        }
        isAction = false;
        if (isAction) {}
        if (!isAction) {}
        if (!inGroup) {}
        if (isAction) {}
        item3.setIsActionButton(isAction);
      }
      i4++;
      actionMenuPresenter = this;
      requestedItems2 = requestedItems;
      itemsSize = itemsSize2;
      querySpec2 = querySpec;
    }
    return true;
  }

  @Override // com.android.internal.view.menu.BaseMenuPresenter,
            // com.android.internal.view.menu.MenuPresenter
  public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
    dismissPopupMenus();
    super.onCloseMenu(menu, allMenusAreClosing);
  }

  @Override // com.android.internal.view.menu.MenuPresenter
  public Parcelable onSaveInstanceState() {
    SavedState state = new SavedState();
    state.openSubMenuId = this.mOpenSubMenuId;
    return state;
  }

  @Override // com.android.internal.view.menu.MenuPresenter
  public void onRestoreInstanceState(Parcelable state) {
    MenuItem item;
    SavedState saved = (SavedState) state;
    if (saved.openSubMenuId > 0 && (item = this.mMenu.findItem(saved.openSubMenuId)) != null) {
      SubMenuBuilder subMenu = (SubMenuBuilder) item.getSubMenu();
      onSubMenuSelected(subMenu);
    }
  }

  @Override // android.view.ActionProvider.SubUiVisibilityListener
  public void onSubUiVisibilityChanged(boolean isVisible) {
    if (isVisible) {
      super.onSubMenuSelected(null);
    } else if (this.mMenu != null) {
      this.mMenu.close(false);
    }
  }

  public void setMenuView(ActionMenuView menuView) {
    if (this.mMenuView != null) {
      ((View) this.mMenuView).removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
    }
    this.mMenuView = menuView;
    menuView.initialize(this.mMenu);
    menuView.addOnAttachStateChangeListener(this.mAttachStateChangeListener);
  }

  private static class SavedState implements Parcelable {
    public static final Parcelable.Creator<SavedState> CREATOR =
        new Parcelable.Creator<
            SavedState>() { // from class: android.widget.ActionMenuPresenter.SavedState.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public SavedState createFromParcel(Parcel in) {
            return new SavedState(in);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public SavedState[] newArray(int size) {
            return new SavedState[size];
          }
        };
    public int openSubMenuId;

    SavedState() {}

    SavedState(Parcel in) {
      this.openSubMenuId = in.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
      return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.openSubMenuId);
    }
  }

  private class OverflowMenuButton extends ImageButton
      implements ActionMenuView.ActionMenuChildView {
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

    @Override // android.widget.ActionMenuView.ActionMenuChildView
    public boolean needsDividerBefore() {
      return false;
    }

    @Override // android.widget.ActionMenuView.ActionMenuChildView
    public boolean needsDividerAfter() {
      return false;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
      super.onInitializeAccessibilityNodeInfoInternal(info);
      info.setCanOpenPopup(true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.ImageView, android.view.View
    public boolean setFrame(int l, int t, int r, int b) {
      boolean changed = super.setFrame(l, t, r, b);
      if (ActionMenuPresenter.this.mIsThemeDeviceDefaultFamily) {
        return changed;
      }
      Drawable d = getDrawable();
      Drawable bg = getBackground();
      if (d != null && bg != null) {
        int width = getWidth();
        int height = getHeight();
        int halfEdge = Math.max(width, height) / 2;
        int offsetX = getPaddingLeft() - getPaddingRight();
        int offsetY = getPaddingTop() - getPaddingBottom();
        int centerX = (width + offsetX) / 2;
        int centerY = (height + offsetY) / 2;
        bg.setHotspotBounds(
            centerX - halfEdge, centerY - halfEdge, centerX + halfEdge, centerY + halfEdge);
      }
      return changed;
    }
  }

  class SemOverflowMenuButtonContainer extends FrameLayout
      implements ActionMenuView.ActionMenuChildView {
    private static final int BADGE_LIMIT_NUMBER = 99;
    private float mBadgeAdditionalWidth;
    private float mBadgeDefaultWidth;
    private TextView mBadgeView;
    private SemOverflowMenuButton mButtonView;
    private NumberFormat mNumberFormat;

    public SemOverflowMenuButtonContainer(Context context) {
      super(context);
      this.mNumberFormat = NumberFormat.getInstance(Locale.getDefault());
      SemOverflowMenuButton semTextOverflowMenuButton =
          ActionMenuPresenter.this.mUseTextItemMode
              ? ActionMenuPresenter.this.new SemTextOverflowMenuButton(context)
              : ActionMenuPresenter.this.new SemImageOverflowMenuButton(context);
      this.mButtonView = semTextOverflowMenuButton;
      addView(semTextOverflowMenuButton, new FrameLayout.LayoutParams(-2, -2));
      TextView textView =
          (TextView)
              ActionMenuPresenter.this.mInflater.inflate(
                  C4337R.layout.sem_action_menu_item_badge, (ViewGroup) this, false);
      this.mBadgeView = textView;
      addView(textView);
      this.mBadgeAdditionalWidth =
          getResources().getDimensionPixelSize(C4337R.dimen.sem_badge_additional_width);
      this.mBadgeDefaultWidth = getResources().getDimension(C4337R.dimen.sem_badge_default_width);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
      super.onConfigurationChanged(newConfig);
      int mWidth = 0;
      TextView textView = this.mBadgeView;
      if (textView != null && !TextUtils.isEmpty(textView.getText())) {
        this.mBadgeView.setTextSize(
            0, (int) getResources().getDimension(C4337R.dimen.sem_menu_item_badge_text_size));
        this.mBadgeDefaultWidth = getResources().getDimension(C4337R.dimen.sem_badge_default_width);
        this.mBadgeAdditionalWidth =
            getResources().getDimensionPixelSize(C4337R.dimen.sem_badge_additional_width);
        ViewGroup.MarginLayoutParams lp =
            (ViewGroup.MarginLayoutParams) this.mBadgeView.getLayoutParams();
        lp.width =
            (int)
                (this.mBadgeDefaultWidth
                    + (this.mBadgeView.getText().length() * this.mBadgeAdditionalWidth));
        lp.height = (int) getResources().getDimension(C4337R.dimen.sem_menu_item_badge_size);
        int mMargin =
            (int) getResources().getDimension(C4337R.dimen.sem_menu_item_badge_right_margin);
        if (1 == getLayoutDirection()) {
          lp.rightMargin = mMargin;
        } else {
          lp.leftMargin = mMargin;
        }
        this.mBadgeView.setLayoutParams(lp);
        mWidth = lp.width + mMargin;
      }
      ActionMenuView.LayoutParams mLayoutParams = (ActionMenuView.LayoutParams) getLayoutParams();
      if (mLayoutParams != null && this.mButtonView != null) {
        TypedArray a =
            getContext().obtainStyledAttributes(null, C4337R.styleable.View, 16843510, 0);
        int mOverflowButtonMinWidth = a.getDimensionPixelSize(36, -1);
        a.recycle();
        if (mWidth > mOverflowButtonMinWidth) {
          mLayoutParams.width = mWidth;
        } else {
          mLayoutParams.width = mOverflowButtonMinWidth;
        }
        setLayoutParams(mLayoutParams);
      }
    }

    @Override // android.widget.ActionMenuView.ActionMenuChildView
    public boolean needsDividerBefore() {
      return false;
    }

    @Override // android.widget.ActionMenuView.ActionMenuChildView
    public boolean needsDividerAfter() {
      return false;
    }

    void invalidateBadgeText() {
      String badgeText;
      ActionMenuView menuView = (ActionMenuView) ActionMenuPresenter.this.mMenuView;
      int badgeCount = menuView.semGetSumOfDigitsInBadges();
      if (badgeCount == 0) {
        this.mBadgeView.setText((CharSequence) null);
        this.mBadgeView.setVisibility(8);
        return;
      }
      String badgeText2 = menuView.getOverflowBadgeText();
      ViewGroup.MarginLayoutParams lp =
          (ViewGroup.MarginLayoutParams) this.mBadgeView.getLayoutParams();
      if (badgeText2 == null || badgeText2.equals("")) {
        int badgeNumber = badgeCount <= 99 ? badgeCount : 99;
        badgeText = this.mNumberFormat.format(badgeNumber);
        lp.height = (int) (this.mBadgeDefaultWidth + this.mBadgeAdditionalWidth);
        lp.width =
            (int) (this.mBadgeDefaultWidth + (badgeText.length() * this.mBadgeAdditionalWidth));
        lp.setMarginEnd(
            (int) getResources().getDimension(C4337R.dimen.sem_menu_item_number_badge_end_margin));
        lp.topMargin =
            (int) getResources().getDimension(C4337R.dimen.sem_menu_item_number_badge_top_margin);
      } else {
        badgeText = "";
        lp.height = (int) getResources().getDimension(C4337R.dimen.sem_menu_item_badge_size);
        lp.width = (int) getResources().getDimension(C4337R.dimen.sem_menu_item_badge_size);
      }
      this.mBadgeView.setLayoutParams(lp);
      this.mBadgeView.setText(badgeText);
      this.mBadgeView.setVisibility(0);
    }

    void setImageDrawable(Drawable icon) {
      this.mButtonView.setImageDrawable(icon);
    }

    Drawable getDrawable() {
      return this.mButtonView.getDrawable();
    }
  }

  private abstract class SemOverflowMenuButton extends TextView {
    abstract Drawable getDrawable();

    abstract void setImageDrawable(Drawable drawable);

    public SemOverflowMenuButton(Context context) {
      super(context, null, 16843510);
      setClickable(true);
      setFocusable(true);
      setVisibility(0);
      setEnabled(true);
      semSetButtonShapeEnabled(true);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
      super.onConfigurationChanged(newConfig);
      TypedArray a = getContext().obtainStyledAttributes(null, C4337R.styleable.View, 16843510, 0);
      setContentDescription(a.getText(44));
      a.recycle();
    }

    @Override // android.widget.TextView, android.view.View
    public void jumpDrawablesToCurrentState() {}

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
      super.onInitializeAccessibilityNodeInfo(info);
      info.setClassName("android.widget.Button");
      info.setCanOpenPopup(true);
    }
  }

  private class SemImageOverflowMenuButton extends SemOverflowMenuButton {
    public SemImageOverflowMenuButton(Context context) {
      super(context);
      TypedArray a = context.obtainStyledAttributes(null, C4337R.styleable.ImageView, 16843510, 0);
      Drawable d = a.getDrawable(0);
      if (d != null) {
        setImageDrawable(d);
      }
      a.recycle();
      setLongClickable(true);
      ActionMenuPresenter.this.mTooltipText = getTooltipText();
    }

    @Override // android.widget.ActionMenuPresenter.SemOverflowMenuButton, android.widget.TextView,
              // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
      super.onConfigurationChanged(newConfig);
      TypedArray a = getContext().obtainStyledAttributes(null, C4337R.styleable.View, 16843510, 0);
      setMinimumHeight(a.getDimensionPixelSize(37, -1));
      a.recycle();
      TypedArray a2 =
          getContext().obtainStyledAttributes(null, C4337R.styleable.ImageView, 16843510, 0);
      Drawable d = a2.getDrawable(0);
      if (d != null) {
        setImageDrawable(d);
      }
      a2.recycle();
    }

    @Override // android.widget.ActionMenuPresenter.SemOverflowMenuButton
    void setImageDrawable(Drawable icon) {
      setCompoundDrawablesWithIntrinsicBounds(
          icon, (Drawable) null, (Drawable) null, (Drawable) null);
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
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
      switch (event.getAction()) {
        case 9:
          if (!ActionMenuPresenter.this.isOverflowMenuShowPending()) {
            setTooltipText(ActionMenuPresenter.this.mTooltipText);
            setTooltipNull(false);
            break;
          }
          break;
        case 10:
          setTooltipNull(true);
          break;
      }
      setTooltipOffset();
      return super.dispatchGenericMotionEvent(event);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean performLongClick() {
      setTooltipOffset();
      return super.performLongClick();
    }

    protected void setTooltipOffset() {
      int xOffset;
      Context context = getContext();
      Resources res = context.getResources();
      int[] screenPos = new int[2];
      getLocationOnScreen(screenPos);
      int width = getWidth();
      int height = getHeight();
      int paddingStart = getPaddingStart();
      int paddingEnd = getPaddingEnd();
      int[] windowPos = new int[2];
      getLocationInWindow(windowPos);
      Rect displayFrame = new Rect();
      getWindowVisibleDisplayFrame(displayFrame);
      Display display =
          ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
      DisplayMetrics realDisplayMetrics = new DisplayMetrics();
      display.getRealMetrics(realDisplayMetrics);
      int diff = 0;
      View toolBar =
          ActionMenuPresenter.this.mMenuView != null
              ? (View) ((View) ActionMenuPresenter.this.mMenuView).getParent()
              : null;
      if ((toolBar instanceof Toolbar)
          && toolBar.getWidth() < displayFrame.right - displayFrame.left) {
        diff = (screenPos[0] - windowPos[0]) - displayFrame.left;
      }
      int yOffset = windowPos[1] + height;
      if (getLayoutDirection() == 0) {
        xOffset =
            (((displayFrame.right - displayFrame.left) - (windowPos[0] + width))
                    + (((width - paddingStart) - paddingEnd) / 2))
                - diff;
        if (checkNaviBarForLandscape()) {
          float navigationBarHeight = getNavigationBarHeight();
          float density = res.getDisplayMetrics().density;
          float realDensity = realDisplayMetrics.density;
          xOffset += (int) ((navigationBarHeight / density) * realDensity);
        }
      } else {
        xOffset = ((paddingEnd - paddingStart) / 2) + windowPos[0] + paddingStart;
      }
      setTooltipPosition(xOffset, yOffset);
    }

    private boolean checkNaviBarForLandscape() {
      Context context = getContext();
      Resources res = context.getResources();
      Rect displayFrame = new Rect();
      getWindowVisibleDisplayFrame(displayFrame);
      Point displaySize = new Point();
      Display display =
          ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
      display.getRealSize(displaySize);
      int rotate = display.getRotation();
      int navigationBarHeight = (int) res.getDimension(C4337R.dimen.navigation_bar_height);
      if (rotate == 1 && displayFrame.right + navigationBarHeight >= displaySize.f85x) {
        setNavigationBarHeight(displaySize.f85x - displayFrame.right);
        return true;
      }
      if (rotate == 3 && displayFrame.left <= navigationBarHeight) {
        setNavigationBarHeight(displayFrame.left);
        return true;
      }
      return false;
    }

    private void setNavigationBarHeight(int height) {
      ActionMenuPresenter.this.mNavigationBarHeight = height;
    }

    private int getNavigationBarHeight() {
      return ActionMenuPresenter.this.mNavigationBarHeight;
    }

    @Override // android.widget.TextView, android.view.View
    protected boolean setFrame(int l, int t, int r, int b) {
      boolean changed = super.setFrame(l, t, r, b);
      Drawable d = getDrawable();
      Drawable bg = getBackground();
      if (d != null && bg != null) {
        int width = getWidth();
        int height = getHeight();
        int offsetX = getPaddingLeft() - getPaddingRight();
        int halfOffsetX = offsetX / 2;
        bg.setHotspotBounds(halfOffsetX, 0, halfOffsetX + width, height);
      }
      return changed;
    }
  }

  private class SemTextOverflowMenuButton extends SemOverflowMenuButton {
    private static final float MAX_FONT_SCALE = 1.2f;
    private float mCurrentFontScale;
    private float mDefaultTextSize;

    public SemTextOverflowMenuButton(Context context) {
      super(context);
      this.mCurrentFontScale = 1.0f;
      TypedArray a = context.getTheme().obtainStyledAttributes(null, C4337R.styleable.Theme, 0, 0);
      int textAppearnceId = a.getResourceId(187, 0);
      setTextAppearance(textAppearnceId);
      a.recycle();
      setText(context.getResources().getString(C4337R.string.more_item_label));
      TypedArray a2 =
          context.obtainStyledAttributes(textAppearnceId, C4337R.styleable.TextAppearance);
      TypedValue value = a2.peekValue(0);
      a2.recycle();
      if (value != null) {
        this.mDefaultTextSize = TypedValue.complexToFloat(value.data);
        float f = context.getResources().getConfiguration().fontScale;
        this.mCurrentFontScale = f;
        if (f > 1.2f) {
          this.mCurrentFontScale = 1.2f;
        }
        setTextSize(1, this.mDefaultTextSize * this.mCurrentFontScale);
      }
    }

    @Override // android.widget.ActionMenuPresenter.SemOverflowMenuButton, android.widget.TextView,
              // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
      super.onConfigurationChanged(newConfig);
      TypedArray a = getContext().obtainStyledAttributes(null, C4337R.styleable.View, 16843510, 0);
      setMinimumHeight(a.getDimensionPixelSize(37, -1));
      a.recycle();
      if (ActionMenuPresenter.this.mIsThemeDeviceDefaultFamily
          && newConfig != null
          && newConfig.fontScale != this.mCurrentFontScale) {
        float f = newConfig.fontScale;
        this.mCurrentFontScale = f;
        if (f > 1.2f) {
          this.mCurrentFontScale = 1.2f;
        }
        setTextSize(1, this.mDefaultTextSize * this.mCurrentFontScale);
      }
      setText(getContext().getResources().getString(C4337R.string.more_item_label));
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

    @Override // android.widget.ActionMenuPresenter.SemOverflowMenuButton
    void setImageDrawable(Drawable icon) {}

    @Override // android.widget.ActionMenuPresenter.SemOverflowMenuButton
    Drawable getDrawable() {
      return null;
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
  }

  public class OverflowTextMenuButton extends SemTextOverflowMenuButton {
    @Override // android.widget.ActionMenuPresenter.SemOverflowMenuButton, android.widget.TextView,
              // android.view.View
    public /* bridge */ /* synthetic */ void jumpDrawablesToCurrentState() {
      super.jumpDrawablesToCurrentState();
    }

    @Override // android.widget.ActionMenuPresenter.SemOverflowMenuButton, android.view.View
    public /* bridge */ /* synthetic */ void onInitializeAccessibilityNodeInfo(
        AccessibilityNodeInfo accessibilityNodeInfo) {
      super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
    }

    @Override // android.widget.ActionMenuPresenter.SemTextOverflowMenuButton,
              // android.widget.TextView, android.view.View
    public /* bridge */ /* synthetic */ boolean performClick() {
      return super.performClick();
    }

    public OverflowTextMenuButton(Context context) {
      super(context);
    }
  }

  private class OverflowPopup extends MenuPopupHelper {
    public OverflowPopup(Context context, MenuBuilder menu, View anchorView, boolean overflowOnly) {
      super(context, menu, anchorView, overflowOnly, 16843844);
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
    public ActionButtonSubmenu(Context context, SubMenuBuilder subMenu, View anchorView) {
      super(context, subMenu, anchorView, false, 16843844);
      MenuItemImpl item = (MenuItemImpl) subMenu.getItem();
      if (!item.isActionButton()) {
        setAnchorView(
            ActionMenuPresenter.this.mSemOverflowButton == null
                ? (View) ActionMenuPresenter.this.mMenuView
                : ActionMenuPresenter.this.mSemOverflowButton);
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
    private PopupPresenterCallback() {}

    @Override // com.android.internal.view.menu.MenuPresenter.Callback
    public boolean onOpenSubMenu(MenuBuilder subMenu) {
      if (subMenu == null) {
        return false;
      }
      ActionMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder) subMenu).getItem().getItemId();
      MenuPresenter.Callback cb = ActionMenuPresenter.this.getCallback();
      if (cb != null) {
        return cb.onOpenSubMenu(subMenu);
      }
      return false;
    }

    @Override // com.android.internal.view.menu.MenuPresenter.Callback
    public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
      if (menu instanceof SubMenuBuilder) {
        menu.getRootMenu().close(false);
      }
      MenuPresenter.Callback cb = ActionMenuPresenter.this.getCallback();
      if (cb != null) {
        cb.onCloseMenu(menu, allMenusAreClosing);
      }
    }
  }

  private class OpenOverflowRunnable implements Runnable {
    private OverflowPopup mPopup;

    public OpenOverflowRunnable(OverflowPopup popup) {
      this.mPopup = popup;
    }

    @Override // java.lang.Runnable
    public void run() {
      if (ActionMenuPresenter.this.mMenu != null) {
        ActionMenuPresenter.this.mMenu.changeMenuMode();
      }
      View menuView = (View) ActionMenuPresenter.this.mMenuView;
      if (ActionMenuPresenter.this.mIsThemeDeviceDefaultFamily) {
        if (menuView != null && menuView.getWindowToken() != null && this.mPopup.tryShow(0, 0)) {
          ActionMenuPresenter.this.mOverflowPopup = this.mPopup;
        }
      } else if (menuView != null && menuView.getWindowToken() != null && this.mPopup.tryShow()) {
        ActionMenuPresenter.this.mOverflowPopup = this.mPopup;
      }
      ActionMenuPresenter.this.mPostedOpenRunnable = null;
    }
  }

  private class ActionMenuPopupCallback extends ActionMenuItemView.PopupCallback {
    private ActionMenuPopupCallback() {}

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

    MenuItemLayoutInfo(View view, boolean preLayout) {
      this.left = view.getLeft();
      this.top = view.getTop();
      if (preLayout) {
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

    /* renamed from: id */
    int f598id;
    MenuItemLayoutInfo menuItemLayoutInfo;

    ItemAnimationInfo(int id, MenuItemLayoutInfo info, Animator anim, int animType) {
      this.f598id = id;
      this.menuItemLayoutInfo = info;
      this.animator = anim;
      this.animType = animType;
    }
  }
}
