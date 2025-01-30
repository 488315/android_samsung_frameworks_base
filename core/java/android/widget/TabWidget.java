package android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import com.android.internal.C4337R;
import com.samsung.android.widget.SemTabDotLineView;

@Deprecated
/* loaded from: classes4.dex */
public class TabWidget extends LinearLayout implements View.OnFocusChangeListener {
  private static final int FONT_WEIGHT_REGULAR = 400;
  private static final int FONT_WEIGHT_SEMIBOLD = 600;
  private final Rect mBounds;
  private float mDefaultTextSize;
  private boolean mDrawBottomStrips;
  private int[] mImposedTabWidths;
  private int mImposedTabsHeight;
  private boolean mIsThemeDeviceDefaultFamily;
  private Drawable mLeftStrip;
  private float mMaxFontScale;
  private Drawable mRightStrip;
  private int mSelectedTab;
  private OnTabSelectionChanged mSelectionChangedListener;
  private Typeface mSemRegularFont;
  private Typeface mSemSemiBoldFont;
  private boolean mStripMoved;
  private ColorStateList mTabTextColorStateList;

  interface OnTabSelectionChanged {
    void onTabSelectionChanged(int i, boolean z);
  }

  public TabWidget(Context context) {
    this(context, null);
  }

  public TabWidget(Context context, AttributeSet attrs) {
    this(context, attrs, 16842883);
  }

  public TabWidget(Context context, AttributeSet attrs, int defStyleAttr) {
    this(context, attrs, defStyleAttr, 0);
  }

  public TabWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    this.mBounds = new Rect();
    this.mSelectedTab = -1;
    this.mDrawBottomStrips = true;
    this.mImposedTabsHeight = -1;
    this.mDefaultTextSize = 14.0f;
    this.mMaxFontScale = 1.3f;
    TypedArray a =
        context.obtainStyledAttributes(
            attrs, C4337R.styleable.TabWidget, defStyleAttr, defStyleRes);
    saveAttributeDataForStyleable(
        context, C4337R.styleable.TabWidget, attrs, a, defStyleAttr, defStyleRes);
    this.mDrawBottomStrips = a.getBoolean(3, this.mDrawBottomStrips);
    boolean isTargetSdkDonutOrLower = context.getApplicationInfo().targetSdkVersion <= 4;
    boolean hasExplicitLeft = a.hasValueOrEmpty(1);
    if (hasExplicitLeft) {
      this.mLeftStrip = a.getDrawable(1);
    } else if (isTargetSdkDonutOrLower) {
      this.mLeftStrip = context.getDrawable(C4337R.drawable.tab_bottom_left_v4);
    } else {
      this.mLeftStrip = context.getDrawable(C4337R.drawable.tab_bottom_left);
    }
    boolean hasExplicitRight = a.hasValueOrEmpty(2);
    if (hasExplicitRight) {
      this.mRightStrip = a.getDrawable(2);
    } else if (isTargetSdkDonutOrLower) {
      this.mRightStrip = context.getDrawable(C4337R.drawable.tab_bottom_right_v4);
    } else {
      this.mRightStrip = context.getDrawable(C4337R.drawable.tab_bottom_right);
    }
    a.recycle();
    setChildrenDrawingOrderEnabled(true);
    TypedValue outValue = new TypedValue();
    context.getTheme().resolveAttribute(C4337R.attr.parentIsDeviceDefault, outValue, true);
    boolean z = outValue.data != 0;
    this.mIsThemeDeviceDefaultFamily = z;
    if (z) {
      TypedArray typedArray =
          context.getTheme().obtainStyledAttributes(null, C4337R.styleable.Theme, 0, 0);
      int tabTextAppearnceId = typedArray.getResourceId(143, 0);
      typedArray.recycle();
      TypedArray typedArray2 =
          getContext().obtainStyledAttributes(tabTextAppearnceId, C4337R.styleable.TextAppearance);
      TypedValue outValue2 = typedArray2.peekValue(0);
      typedArray2.recycle();
      if (outValue2 != null) {
        this.mDefaultTextSize = TypedValue.complexToFloat(outValue2.data);
      }
      Typeface tf = Typeface.create("sec", 0);
      this.mSemRegularFont = Typeface.create(tf, 400, false);
      Typeface tf2 = Typeface.create("sec", 0);
      this.mSemSemiBoldFont = Typeface.create(tf2, 600, false);
    }
  }

  @Override // android.widget.LinearLayout, android.view.View
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (this.mIsThemeDeviceDefaultFamily) {
      float fontScale = getContext().getResources().getConfiguration().fontScale;
      if (fontScale > this.mMaxFontScale) {
        fontScale = this.mMaxFontScale;
      }
      int count = getTabCount();
      for (int i = 0; i < count; i++) {
        View child = getChildTabViewAt(i);
        if (child != null) {
          View vTextView = child.findViewById(16908310);
          if (vTextView instanceof TextView) {
            TextView textView = (TextView) vTextView;
            textView.setTextSize(1, this.mDefaultTextSize * fontScale);
          }
        }
      }
    }
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  @Override // android.view.View
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    this.mStripMoved = true;
    super.onSizeChanged(w, h, oldw, oldh);
  }

  @Override // android.view.ViewGroup
  protected int getChildDrawingOrder(int childCount, int i) {
    int i2 = this.mSelectedTab;
    if (i2 == -1) {
      return i;
    }
    if (i == childCount - 1) {
      return i2;
    }
    if (i >= i2) {
      return i + 1;
    }
    return i;
  }

  @Override // android.widget.LinearLayout
  void measureChildBeforeLayout(
      View child,
      int childIndex,
      int widthMeasureSpec,
      int totalWidth,
      int heightMeasureSpec,
      int totalHeight) {
    if (!isMeasureWithLargestChildEnabled() && this.mImposedTabsHeight >= 0) {
      widthMeasureSpec =
          View.MeasureSpec.makeMeasureSpec(
              this.mImposedTabWidths[childIndex] + totalWidth, 1073741824);
      heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mImposedTabsHeight, 1073741824);
    }
    super.measureChildBeforeLayout(
        child, childIndex, widthMeasureSpec, totalWidth, heightMeasureSpec, totalHeight);
  }

  @Override // android.widget.LinearLayout
  void measureHorizontal(int widthMeasureSpec, int heightMeasureSpec) {
    if (View.MeasureSpec.getMode(widthMeasureSpec) == 0) {
      super.measureHorizontal(widthMeasureSpec, heightMeasureSpec);
      return;
    }
    int width = View.MeasureSpec.getSize(widthMeasureSpec);
    int unspecifiedWidth = View.MeasureSpec.makeSafeMeasureSpec(width, 0);
    this.mImposedTabsHeight = -1;
    super.measureHorizontal(unspecifiedWidth, heightMeasureSpec);
    int extraWidth = getMeasuredWidth() - width;
    if (extraWidth > 0) {
      int count = getChildCount();
      int childCount = 0;
      for (int i = 0; i < count; i++) {
        if (getChildAt(i).getVisibility() != 8) {
          childCount++;
        }
      }
      if (childCount > 0) {
        int[] iArr = this.mImposedTabWidths;
        if (iArr == null || iArr.length != count) {
          this.mImposedTabWidths = new int[count];
        }
        for (int i2 = 0; i2 < count; i2++) {
          View child = getChildAt(i2);
          if (child.getVisibility() != 8) {
            int childWidth = child.getMeasuredWidth();
            int delta = extraWidth / childCount;
            int newWidth = Math.max(0, childWidth - delta);
            this.mImposedTabWidths[i2] = newWidth;
            extraWidth -= childWidth - newWidth;
            childCount--;
            this.mImposedTabsHeight = Math.max(this.mImposedTabsHeight, child.getMeasuredHeight());
          }
        }
      }
    }
    super.measureHorizontal(widthMeasureSpec, heightMeasureSpec);
  }

  public View getChildTabViewAt(int index) {
    return getChildAt(index);
  }

  public int getTabCount() {
    return getChildCount();
  }

  @Override // android.widget.LinearLayout
  public void setDividerDrawable(Drawable drawable) {
    super.setDividerDrawable(drawable);
  }

  public void setDividerDrawable(int resId) {
    setDividerDrawable(this.mContext.getDrawable(resId));
  }

  public void setLeftStripDrawable(Drawable drawable) {
    this.mLeftStrip = drawable;
    requestLayout();
    invalidate();
  }

  public void setLeftStripDrawable(int resId) {
    setLeftStripDrawable(this.mContext.getDrawable(resId));
  }

  public Drawable getLeftStripDrawable() {
    return this.mLeftStrip;
  }

  public void setRightStripDrawable(Drawable drawable) {
    this.mRightStrip = drawable;
    requestLayout();
    invalidate();
  }

  public void setRightStripDrawable(int resId) {
    setRightStripDrawable(this.mContext.getDrawable(resId));
  }

  public Drawable getRightStripDrawable() {
    return this.mRightStrip;
  }

  public void setStripEnabled(boolean stripEnabled) {
    this.mDrawBottomStrips = stripEnabled;
    invalidate();
  }

  public boolean isStripEnabled() {
    return this.mDrawBottomStrips;
  }

  @Override // android.view.ViewGroup, android.view.ViewParent
  public void childDrawableStateChanged(View child) {
    if (getTabCount() > 0 && child == getChildTabViewAt(this.mSelectedTab)) {
      invalidate();
    }
    super.childDrawableStateChanged(child);
  }

  @Override // android.view.ViewGroup, android.view.View
  public void dispatchDraw(Canvas canvas) {
    super.dispatchDraw(canvas);
    if (getTabCount() == 0 || !this.mDrawBottomStrips) {
      return;
    }
    View selectedChild = getChildTabViewAt(this.mSelectedTab);
    Drawable leftStrip = this.mLeftStrip;
    Drawable rightStrip = this.mRightStrip;
    if (leftStrip != null) {
      leftStrip.setState(selectedChild.getDrawableState());
    }
    if (rightStrip != null) {
      rightStrip.setState(selectedChild.getDrawableState());
    }
    if (this.mStripMoved) {
      Rect bounds = this.mBounds;
      bounds.left = selectedChild.getLeft();
      bounds.right = selectedChild.getRight();
      int myHeight = getHeight();
      if (leftStrip != null) {
        leftStrip.setBounds(
            Math.min(0, bounds.left - leftStrip.getIntrinsicWidth()),
            myHeight - leftStrip.getIntrinsicHeight(),
            bounds.left,
            myHeight);
      }
      if (rightStrip != null) {
        rightStrip.setBounds(
            bounds.right,
            myHeight - rightStrip.getIntrinsicHeight(),
            Math.max(getWidth(), bounds.right + rightStrip.getIntrinsicWidth()),
            myHeight);
      }
      this.mStripMoved = false;
    }
    if (leftStrip != null) {
      leftStrip.draw(canvas);
    }
    if (rightStrip != null) {
      rightStrip.draw(canvas);
    }
  }

  public void setCurrentTab(int index) {
    int i;
    View tabView;
    View oldTabView;
    if (index < 0 || index >= getTabCount() || index == (i = this.mSelectedTab)) {
      return;
    }
    if (i != -1) {
      getChildTabViewAt(i).setSelected(false);
      if (this.mIsThemeDeviceDefaultFamily
          && (oldTabView = getChildTabViewAt(this.mSelectedTab)) != null) {
        TextView oldTabTextView = (TextView) oldTabView.findViewById(16908310);
        if (oldTabTextView != null) {
          oldTabTextView.setTextColor(this.mTabTextColorStateList);
          oldTabTextView.setTypeface(this.mSemRegularFont);
        }
        SemTabDotLineView oldSemTabDotLineView =
            (SemTabDotLineView) oldTabView.findViewById(C4337R.id.sem_tab_indicator);
        if (oldSemTabDotLineView != null) {
          oldSemTabDotLineView.setDrawState(false);
        }
      }
    }
    this.mSelectedTab = index;
    getChildTabViewAt(index).setSelected(true);
    this.mStripMoved = true;
    if (this.mIsThemeDeviceDefaultFamily
        && (tabView = getChildTabViewAt(this.mSelectedTab)) != null) {
      TextView tabTextView = (TextView) tabView.findViewById(16908310);
      if (tabTextView != null) {
        tabTextView.setTextColor(this.mTabTextColorStateList);
        tabTextView.setTypeface(this.mSemSemiBoldFont);
      }
      SemTabDotLineView semTabDotLineView =
          (SemTabDotLineView) tabView.findViewById(C4337R.id.sem_tab_indicator);
      if (semTabDotLineView != null) {
        semTabDotLineView.setDrawState(true);
      }
    }
  }

  @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
  public CharSequence getAccessibilityClassName() {
    return TabWidget.class.getName();
  }

  @Override // android.view.View
  public void onInitializeAccessibilityEventInternal(AccessibilityEvent event) {
    super.onInitializeAccessibilityEventInternal(event);
    event.setItemCount(getTabCount());
    event.setCurrentItemIndex(this.mSelectedTab);
  }

  public void focusCurrentTab(int index) {
    int oldTab = this.mSelectedTab;
    setCurrentTab(index);
    if (oldTab != index) {
      getChildTabViewAt(index).requestFocus();
    }
  }

  @Override // android.view.View
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);
    int count = getTabCount();
    for (int i = 0; i < count; i++) {
      View child = getChildTabViewAt(i);
      child.setEnabled(enabled);
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  @Override // android.view.ViewGroup
  public void addView(View view) {
    if (view.getLayoutParams() == null) {
      LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -1, 1.0f);
      layoutParams.setMargins(0, 0, 0, 0);
      view.setLayoutParams(layoutParams);
    }
    view.setFocusable(true);
    view.setClickable(true);
    if (view.getPointerIcon() == null) {
      view.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1002));
    }
    super.addView(view);
    Object[] objArr = 0;
    view.setOnClickListener(new TabClickListener(getTabCount() - 1));
    if (this.mIsThemeDeviceDefaultFamily) {
      view.setOnTouchListener(new SemTabTouchListener(getTabCount() - 1));
      TextView textView = (TextView) view.findViewById(16908310);
      if (textView != null) {
        this.mTabTextColorStateList = textView.getTextColors();
      }
    }
  }

  @Override // android.view.ViewGroup
  public void removeAllViews() {
    super.removeAllViews();
    this.mSelectedTab = -1;
  }

  @Override // android.view.ViewGroup, android.view.View
  public PointerIcon onResolvePointerIcon(MotionEvent event, int pointerIndex) {
    if (!isEnabled()) {
      return null;
    }
    return super.onResolvePointerIcon(event, pointerIndex);
  }

  void setTabSelectionListener(OnTabSelectionChanged listener) {
    this.mSelectionChangedListener = listener;
  }

  @Override // android.view.View.OnFocusChangeListener
  public void onFocusChange(View v, boolean hasFocus) {}

  private class TabClickListener implements View.OnClickListener {
    private final int mTabIndex;

    private TabClickListener(int tabIndex) {
      this.mTabIndex = tabIndex;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
      TabWidget.this.mSelectionChangedListener.onTabSelectionChanged(this.mTabIndex, true);
    }
  }

  private class SemTabTouchListener implements View.OnTouchListener {
    private final int mTabIndex;

    private SemTabTouchListener(int tabIndex) {
      this.mTabIndex = tabIndex;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00b7, code lost:

       return false;
    */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouch(View view, MotionEvent motionEvent) {
      TabWidget tabWidget;
      View oldView;
      if (TabWidget.this.mSelectedTab != this.mTabIndex) {
        int action = motionEvent.getAction();
        SemTabDotLineView semTabDotLineView =
            (SemTabDotLineView) view.findViewById(C4337R.id.sem_tab_indicator);
        TextView tabText = (TextView) view.findViewById(16908310);
        if (semTabDotLineView != null
            && tabText != null
            && (oldView = (tabWidget = TabWidget.this).getChildTabViewAt(tabWidget.mSelectedTab))
                != null) {
          SemTabDotLineView oldDotView =
              (SemTabDotLineView) oldView.findViewById(C4337R.id.sem_tab_indicator);
          TextView oldTextView = (TextView) oldView.findViewById(16908310);
          if (oldDotView != null && oldTextView != null) {
            switch (action) {
              case 0:
                oldDotView.setDrawState(false);
                TabWidget tabWidget2 = TabWidget.this;
                oldTextView.setTextColor(
                    tabWidget2.getNotSelectedColor(tabWidget2.mTabTextColorStateList));
                oldTextView.setTypeface(TabWidget.this.mSemRegularFont);
                TabWidget tabWidget3 = TabWidget.this;
                tabText.setTextColor(
                    tabWidget3.getSelectedColor(tabWidget3.mTabTextColorStateList));
                tabText.setTypeface(TabWidget.this.mSemSemiBoldFont);
                tabText.setSelected(false);
                break;
              case 1:
              case 2:
                if (!view.isPressed()) {
                  oldDotView.setSelected(true);
                  oldDotView.mDrawDot = true;
                  oldTextView.setTextColor(TabWidget.this.mTabTextColorStateList);
                  oldTextView.setTypeface(TabWidget.this.mSemSemiBoldFont);
                  TabWidget tabWidget4 = TabWidget.this;
                  tabText.setTextColor(
                      tabWidget4.getNotSelectedColor(tabWidget4.mTabTextColorStateList));
                  tabText.setTypeface(TabWidget.this.mSemRegularFont);
                  tabText.setSelected(false);
                  break;
                }
                break;
            }
          } else {
            return false;
          }
        } else {
          return false;
        }
      } else {
        return false;
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public int getSelectedColor(ColorStateList colorStateList) {
    if (colorStateList != null) {
      return colorStateList.getColorForState(
          new int[] {16842913, 16842910}, colorStateList.getDefaultColor());
    }
    return -1;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public int getNotSelectedColor(ColorStateList colorStateList) {
    if (colorStateList != null) {
      return colorStateList.getColorForState(
          new int[] {-16842913, -16842908}, colorStateList.getDefaultColor());
    }
    return -1;
  }
}
