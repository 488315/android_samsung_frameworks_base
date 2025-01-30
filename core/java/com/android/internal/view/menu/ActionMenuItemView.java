package com.android.internal.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.ForwardingListener;
import android.widget.TextView;
import android.widget.Toolbar;
import com.android.internal.C4337R;

/* loaded from: classes5.dex */
public class ActionMenuItemView extends TextView
    implements MenuView.ItemView,
        View.OnClickListener,
        View.OnLongClickListener,
        ActionMenuView.ActionMenuChildView {
  private static final int MAX_ICON_SIZE = 32;
  private static final String TAG = "ActionMenuItemView";
  private boolean mAllowTextWithIcon;
  private float mDefaultTextSize;
  private boolean mExpandedFormat;
  private ForwardingListener mForwardingListener;
  private Drawable mIcon;
  private boolean mIsChangedRelativePadding;
  private boolean mIsDarkTheme;
  private boolean mIsThemeDeviceDefaultFamily;
  private MenuItemImpl mItemData;
  private MenuBuilder.ItemInvoker mItemInvoker;
  private float mMaxFontScale;
  private int mMaxIconSize;
  private int mMinWidth;
  private int mNavigationBarHeight;
  private PopupCallback mPopupCallback;
  private int mSavedPaddingLeft;
  private CharSequence mTitle;

  public abstract static class PopupCallback {
    public abstract ShowableListMenu getPopup();
  }

  public ActionMenuItemView(Context context) {
    this(context, null);
  }

  public ActionMenuItemView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ActionMenuItemView(Context context, AttributeSet attrs, int defStyleAttr) {
    this(context, attrs, defStyleAttr, 0);
  }

  public ActionMenuItemView(
      Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    this.mIsChangedRelativePadding = false;
    this.mIsDarkTheme = false;
    this.mNavigationBarHeight = 0;
    this.mDefaultTextSize = 0.0f;
    this.mMaxFontScale = 1.3f;
    Resources res = context.getResources();
    this.mAllowTextWithIcon = shouldAllowTextWithIcon();
    TypedArray a =
        context.obtainStyledAttributes(
            attrs, C4337R.styleable.ActionMenuItemView, defStyleAttr, defStyleRes);
    this.mMinWidth = a.getDimensionPixelSize(0, 0);
    a.recycle();
    float density = res.getDisplayMetrics().density;
    this.mMaxIconSize = (int) ((32.0f * density) + 0.5f);
    setOnClickListener(this);
    this.mSavedPaddingLeft = -1;
    setSaveEnabled(false);
    TypedValue outValue = new TypedValue();
    setOnLongClickListener(this);
    context.getTheme().resolveAttribute(C4337R.attr.parentIsDeviceDefault, outValue, true);
    this.mIsThemeDeviceDefaultFamily = outValue.data != 0;
    context.getTheme().resolveAttribute(C4337R.attr.parentIsDeviceDefaultDark, outValue, true);
    this.mIsDarkTheme = outValue.data != 0;
    if (this.mIsThemeDeviceDefaultFamily) {
      TypedArray a2 = context.getTheme().obtainStyledAttributes(null, C4337R.styleable.Theme, 0, 0);
      int actionMeneTextAppearnceId = a2.getResourceId(187, 0);
      a2.recycle();
      TypedArray a3 =
          getContext()
              .obtainStyledAttributes(actionMeneTextAppearnceId, C4337R.styleable.TextAppearance);
      TypedValue outValue2 = a3.peekValue(0);
      a3.recycle();
      if (outValue2 != null) {
        this.mDefaultTextSize = TypedValue.complexToFloat(outValue2.data);
      }
    }
  }

  @Override // android.widget.TextView, android.view.View
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    TypedArray a = getContext().obtainStyledAttributes(null, C4337R.styleable.View, 16843480, 0);
    setMinHeight(a.getDimensionPixelSize(37, -1));
    a.recycle();
    float density = getContext().getResources().getDisplayMetrics().density;
    this.mMaxIconSize = (int) ((32.0f * density) + 0.5f);
    this.mAllowTextWithIcon = shouldAllowTextWithIcon();
    updateTextButtonVisibility();
  }

  @Override // android.widget.TextView, android.view.View
  public CharSequence getAccessibilityClassName() {
    return Button.class.getName();
  }

  private boolean shouldAllowTextWithIcon() {
    Configuration configuration = getContext().getResources().getConfiguration();
    int width = configuration.screenWidthDp;
    int height = configuration.screenHeightDp;
    return width >= 480 || (width >= 640 && height >= 480) || configuration.orientation == 2;
  }

  @Override // android.widget.TextView, android.view.View
  public void setPaddingRelative(int l, int t, int r, int b) {
    this.mSavedPaddingLeft = l;
    this.mIsChangedRelativePadding = true;
    super.setPaddingRelative(l, t, r, b);
  }

  @Override // android.widget.TextView, android.view.View
  public void setPadding(int l, int t, int r, int b) {
    this.mSavedPaddingLeft = l;
    super.setPadding(l, t, r, b);
  }

  @Override // com.android.internal.view.menu.MenuView.ItemView
  public MenuItemImpl getItemData() {
    return this.mItemData;
  }

  @Override // com.android.internal.view.menu.MenuView.ItemView
  public void initialize(MenuItemImpl itemData, int menuType) {
    this.mItemData = itemData;
    setIcon(itemData.getIcon());
    setTitle(itemData.getTitleForItemView(this));
    setId(itemData.getItemId());
    setVisibility(itemData.isVisible() ? 0 : 8);
    setEnabled(itemData.isEnabled());
    if (itemData.hasSubMenu()
        && !this.mIsThemeDeviceDefaultFamily
        && this.mForwardingListener == null) {
      this.mForwardingListener = new ActionMenuItemForwardingListener();
    }
  }

  @Override // android.widget.TextView, android.view.View
  public boolean onTouchEvent(MotionEvent e) {
    ForwardingListener forwardingListener;
    if (this.mItemData.hasSubMenu()
        && (forwardingListener = this.mForwardingListener) != null
        && forwardingListener.onTouch(this, e)) {
      return true;
    }
    return super.onTouchEvent(e);
  }

  @Override // android.view.View.OnClickListener
  public void onClick(View v) {
    ShowableListMenu popup;
    MenuBuilder.ItemInvoker itemInvoker = this.mItemInvoker;
    if (itemInvoker != null) {
      itemInvoker.invokeItem(this.mItemData);
    }
    PopupCallback popupCallback = this.mPopupCallback;
    if (popupCallback != null && (popup = popupCallback.getPopup()) != null && popup.isShowing()) {
      v.setTooltipNull(true);
    }
  }

  public void setItemInvoker(MenuBuilder.ItemInvoker invoker) {
    this.mItemInvoker = invoker;
  }

  public void setPopupCallback(PopupCallback popupCallback) {
    this.mPopupCallback = popupCallback;
  }

  @Override // com.android.internal.view.menu.MenuView.ItemView
  public boolean prefersCondensedTitle() {
    return true;
  }

  @Override // com.android.internal.view.menu.MenuView.ItemView
  public void setCheckable(boolean checkable) {}

  @Override // com.android.internal.view.menu.MenuView.ItemView
  public void setChecked(boolean checked) {}

  public void setExpandedFormat(boolean expandedFormat) {
    if (this.mExpandedFormat != expandedFormat) {
      this.mExpandedFormat = expandedFormat;
      MenuItemImpl menuItemImpl = this.mItemData;
      if (menuItemImpl != null) {
        menuItemImpl.actionFormatChanged();
      }
    }
  }

  private void updateTextButtonVisibility() {
    boolean visible =
        (!TextUtils.isEmpty(this.mTitle))
            & (this.mIcon == null
                || (this.mItemData.showsTextAsAction()
                    && (this.mAllowTextWithIcon || this.mExpandedFormat)));
    if (this.mIsThemeDeviceDefaultFamily && this.mDefaultTextSize > 0.0f) {
      float fontScale = getContext().getResources().getConfiguration().fontScale;
      if (fontScale > this.mMaxFontScale) {
        fontScale = this.mMaxFontScale;
      }
      setTextSize(1, this.mDefaultTextSize * fontScale);
    }
    setText(visible ? this.mTitle : null);
    if (visible) {
      if (this.mIsDarkTheme) {
        setBackgroundResource(C4337R.drawable.sem_action_item_background_text_material_dark);
      } else {
        setBackgroundResource(C4337R.drawable.sem_action_item_background_text_material);
      }
    }
    semSetButtonShapeEnabled(visible);
    CharSequence contentDescription = this.mItemData.getContentDescription();
    if (TextUtils.isEmpty(contentDescription)) {
      setContentDescription(visible ? null : this.mItemData.getTitle());
    } else {
      setContentDescription(contentDescription);
    }
    CharSequence tooltipText = this.mItemData.getTooltipText();
    if (TextUtils.isEmpty(tooltipText)) {
      setTooltipText(visible ? null : this.mItemData.getTitle());
    } else {
      setTooltipText(tooltipText);
    }
  }

  @Override // com.android.internal.view.menu.MenuView.ItemView
  public void setIcon(Drawable icon) {
    this.mIcon = icon;
    if (icon != null) {
      int width = icon.getIntrinsicWidth();
      int height = icon.getIntrinsicHeight();
      int i = this.mMaxIconSize;
      if (width > i) {
        float scale = i / width;
        width = this.mMaxIconSize;
        height = (int) (height * scale);
      }
      if (height > i) {
        float scale2 = i / height;
        height = this.mMaxIconSize;
        width = (int) (width * scale2);
      }
      icon.setBounds(0, 0, width, height);
    }
    if (this.mIsThemeDeviceDefaultFamily && hasText() && isLayoutRtl()) {
      setCompoundDrawables(null, null, icon, null);
    } else {
      setCompoundDrawables(icon, null, null, null);
    }
    updateTextButtonVisibility();
  }

  public boolean hasText() {
    return !TextUtils.isEmpty(getText());
  }

  @Override // com.android.internal.view.menu.MenuView.ItemView
  public void setShortcut(boolean showShortcut, char shortcutKey) {}

  @Override // com.android.internal.view.menu.MenuView.ItemView
  public void setTitle(CharSequence title) {
    this.mTitle = title;
    setContentDescription(title);
    updateTextButtonVisibility();
  }

  @Override // android.view.View
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
    super.onInitializeAccessibilityNodeInfo(info);
    info.setClassName("android.widget.Button");
  }

  @Override // android.view.View
  public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent event) {
    onPopulateAccessibilityEvent(event);
    return true;
  }

  @Override // android.widget.TextView, android.view.View
  public void onPopulateAccessibilityEventInternal(AccessibilityEvent event) {
    super.onPopulateAccessibilityEventInternal(event);
    CharSequence cdesc = getContentDescription();
    if (!TextUtils.isEmpty(cdesc)) {
      event.getText().add(cdesc);
    }
  }

  @Override // android.view.View
  public boolean dispatchHoverEvent(MotionEvent event) {
    return onHoverEvent(event);
  }

  @Override // com.android.internal.view.menu.MenuView.ItemView
  public boolean showsIcon() {
    return true;
  }

  @Override // android.widget.ActionMenuView.ActionMenuChildView
  public boolean needsDividerBefore() {
    return hasText() && this.mItemData.getIcon() == null;
  }

  @Override // android.widget.ActionMenuView.ActionMenuChildView
  public boolean needsDividerAfter() {
    return hasText();
  }

  @Override // android.view.View
  public boolean dispatchGenericMotionEvent(MotionEvent event) {
    setTooltipNull(false);
    setTooltipOffset();
    return super.dispatchGenericMotionEvent(event);
  }

  @Override // android.view.View.OnLongClickListener
  public boolean onLongClick(View v) {
    setTooltipOffset();
    return false;
  }

  protected void setTooltipOffset() {
    int xOffset;
    if (!hasText()) {
      if (this.mItemData.getTooltipText() == null && this.mItemData.getTitle() == null) {
        return;
      }
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
      View menuView = (View) getParent();
      View toolbar = menuView != null ? (View) menuView.getParent() : null;
      if ((toolbar instanceof Toolbar)
          && toolbar.getWidth() < displayFrame.right - displayFrame.left) {
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
  }

  boolean checkNaviBarForLandscape() {
    Context context = getContext();
    Resources res = context.getResources();
    Display display =
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    Rect displayFrame = new Rect();
    getWindowVisibleDisplayFrame(displayFrame);
    Point displaySize = new Point();
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
    this.mNavigationBarHeight = height;
  }

  private int getNavigationBarHeight() {
    return this.mNavigationBarHeight;
  }

  @Override // android.widget.TextView, android.view.View
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int i;
    boolean textVisible = hasText();
    if (textVisible && (i = this.mSavedPaddingLeft) >= 0) {
      super.setPadding(i, getPaddingTop(), getPaddingRight(), getPaddingBottom());
    }
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
    int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
    int oldMeasuredWidth = getMeasuredWidth();
    int targetWidth =
        widthMode == Integer.MIN_VALUE ? Math.min(widthSize, this.mMinWidth) : this.mMinWidth;
    if (widthMode != 1073741824 && this.mMinWidth > 0 && oldMeasuredWidth < targetWidth) {
      super.onMeasure(View.MeasureSpec.makeMeasureSpec(targetWidth, 1073741824), heightMeasureSpec);
    }
    if (!textVisible && this.mIcon != null) {
      int w = getMeasuredWidth();
      int dw = this.mIcon.getBounds().width();
      if (!this.mIsChangedRelativePadding) {
        super.setPadding((w - dw) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
      }
    }
  }

  private class ActionMenuItemForwardingListener extends ForwardingListener {
    public ActionMenuItemForwardingListener() {
      super(ActionMenuItemView.this);
    }

    @Override // android.widget.ForwardingListener
    public ShowableListMenu getPopup() {
      if (ActionMenuItemView.this.mPopupCallback != null) {
        return ActionMenuItemView.this.mPopupCallback.getPopup();
      }
      return null;
    }

    @Override // android.widget.ForwardingListener
    protected boolean onForwardingStarted() {
      ShowableListMenu popup;
      return ActionMenuItemView.this.mItemInvoker != null
          && ActionMenuItemView.this.mItemInvoker.invokeItem(ActionMenuItemView.this.mItemData)
          && (popup = getPopup()) != null
          && popup.isShowing();
    }
  }

  @Override // android.widget.TextView, android.view.View
  public void onRestoreInstanceState(Parcelable state) {
    super.onRestoreInstanceState(null);
  }

  /* JADX INFO: Access modifiers changed from: protected */
  @Override // android.widget.TextView, android.view.View
  public boolean setFrame(int l, int t, int r, int b) {
    boolean changed = super.setFrame(l, t, r, b);
    if (!this.mIsChangedRelativePadding) {
      return changed;
    }
    Drawable bg = getBackground();
    if (this.mIcon != null && bg != null) {
      int width = getWidth();
      int height = getHeight();
      int offsetX = getPaddingLeft() - getPaddingRight();
      int halfOffsetX = offsetX / 2;
      bg.setHotspotBounds(halfOffsetX, 0, halfOffsetX + width, height);
    } else if (bg != null) {
      bg.setHotspotBounds(0, 0, getWidth(), getHeight());
    }
    return changed;
  }
}
