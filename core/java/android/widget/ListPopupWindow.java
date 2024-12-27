package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.android.internal.R;
import com.android.internal.view.menu.ShowableListMenu;

public class ListPopupWindow implements ShowableListMenu {
    private static final boolean DEBUG = false;
    private static final int EXPAND_LIST_TIMEOUT = 250;
    public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
    public static final int INPUT_METHOD_NEEDED = 1;
    public static final int INPUT_METHOD_NOT_NEEDED = 2;
    public static final int MATCH_PARENT = -1;
    public static final int POSITION_PROMPT_ABOVE = 0;
    public static final int POSITION_PROMPT_BELOW = 1;
    private static final String TAG = "ListPopupWindow";
    public static final int WRAP_CONTENT = -2;
    private ListAdapter mAdapter;
    private Context mContext;
    private boolean mDropDownAlwaysVisible;
    private View mDropDownAnchorView;
    private int mDropDownGravity;
    private int mDropDownHeight;
    private int mDropDownHorizontalOffset;
    private DropDownListView mDropDownList;
    private Drawable mDropDownListHighlight;
    private int mDropDownVerticalOffset;
    private boolean mDropDownVerticalOffsetSet;
    private int mDropDownWidth;
    private int mDropDownWindowLayoutType;
    private Rect mEpicenterBounds;
    private boolean mForceIgnoreOutsideTouch;
    private final Handler mHandler;
    private final ListSelectorHider mHideSelector;
    private boolean mIsAnimatedFromAnchor;
    private boolean mIsDeviceDefault;
    private boolean mIsDexMode;
    private AdapterView.OnItemClickListener mItemClickListener;
    private AdapterView.OnItemSelectedListener mItemSelectedListener;
    int mListItemExpandMaximum;
    private boolean mModal;
    private DataSetObserver mObserver;
    private boolean mOverlapAnchor;
    private boolean mOverlapAnchorSet;
    PopupWindow mPopup;
    private int mPromptPosition;
    private View mPromptView;
    private final ResizePopupRunnable mResizePopupRunnable;
    private final PopupScrollListener mScrollListener;
    private Runnable mShowDropDownRunnable;
    private final Rect mTempRect;
    private final PopupTouchInterceptor mTouchInterceptor;

    public ListPopupWindow(Context context) {
        this(context, null, 16843519, 0);
    }

    public ListPopupWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 16843519, 0);
    }

    public ListPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ListPopupWindow(Context context, AttributeSet attributeSet, int i, int i2) {
        boolean z;
        this.mDropDownHeight = -2;
        this.mDropDownWidth = -2;
        this.mDropDownWindowLayoutType = 1002;
        this.mIsAnimatedFromAnchor = true;
        this.mDropDownGravity = 0;
        this.mDropDownAlwaysVisible = false;
        this.mForceIgnoreOutsideTouch = false;
        this.mListItemExpandMaximum = Integer.MAX_VALUE;
        this.mPromptPosition = 0;
        this.mResizePopupRunnable = new ResizePopupRunnable();
        this.mTouchInterceptor = new PopupTouchInterceptor();
        this.mScrollListener = new PopupScrollListener();
        this.mHideSelector = new ListSelectorHider();
        this.mTempRect = new Rect();
        this.mIsDexMode = false;
        this.mIsDeviceDefault = false;
        this.mContext = context;
        this.mHandler = new Handler(context.getMainLooper());
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.ListPopupWindow, i, i2);
        this.mDropDownHorizontalOffset = obtainStyledAttributes.getDimensionPixelOffset(0, 0);
        this.mDropDownVerticalOffset = obtainStyledAttributes.getDimensionPixelOffset(1, 0);
        if (this.mDropDownVerticalOffset != 0) {
            this.mDropDownVerticalOffsetSet = true;
        }
        obtainStyledAttributes.recycle();
        this.mPopup = new PopupWindow(context, attributeSet, i, i2);
        this.mPopup.setInputMethodMode(1);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, false);
        if (typedValue.data != 0) {
            z = true;
        } else {
            z = false;
        }
        this.mIsDeviceDefault = z;
        this.mIsDexMode =
                this.mContext.getResources().getConfiguration().semDesktopModeEnabled == 1;
    }

    public void setAdapter(ListAdapter adapter) {
        if (this.mObserver == null) {
            this.mObserver = new PopupDataSetObserver();
        } else if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver);
        }
        this.mAdapter = adapter;
        if (this.mAdapter != null) {
            adapter.registerDataSetObserver(this.mObserver);
        }
        if (this.mDropDownList != null) {
            this.mDropDownList.setAdapter(this.mAdapter);
        }
    }

    public void setPromptPosition(int position) {
        this.mPromptPosition = position;
    }

    public int getPromptPosition() {
        return this.mPromptPosition;
    }

    public void setModal(boolean modal) {
        this.mModal = modal;
        this.mPopup.setFocusable(modal);
    }

    public boolean isModal() {
        return this.mModal;
    }

    public void setForceIgnoreOutsideTouch(boolean forceIgnoreOutsideTouch) {
        this.mForceIgnoreOutsideTouch = forceIgnoreOutsideTouch;
    }

    public void setDropDownAlwaysVisible(boolean dropDownAlwaysVisible) {
        this.mDropDownAlwaysVisible = dropDownAlwaysVisible;
    }

    public boolean isDropDownAlwaysVisible() {
        return this.mDropDownAlwaysVisible;
    }

    public void setSoftInputMode(int mode) {
        this.mPopup.setSoftInputMode(mode);
    }

    public int getSoftInputMode() {
        return this.mPopup.getSoftInputMode();
    }

    public void setListSelector(Drawable selector) {
        this.mDropDownListHighlight = selector;
    }

    public Drawable getBackground() {
        return this.mPopup.getBackground();
    }

    public void setBackgroundDrawable(Drawable d) {
        this.mPopup.setBackgroundDrawable(d);
    }

    public void setAnimationStyle(int animationStyle) {
        this.mPopup.setAnimationStyle(animationStyle);
    }

    public int getAnimationStyle() {
        return this.mPopup.getAnimationStyle();
    }

    public View getAnchorView() {
        return this.mDropDownAnchorView;
    }

    public void setAnchorView(View anchor) {
        this.mDropDownAnchorView = anchor;
    }

    public int getHorizontalOffset() {
        return this.mDropDownHorizontalOffset;
    }

    public void setHorizontalOffset(int offset) {
        this.mDropDownHorizontalOffset = offset;
    }

    public int getVerticalOffset() {
        if (!this.mDropDownVerticalOffsetSet) {
            return 0;
        }
        return this.mDropDownVerticalOffset;
    }

    public void setVerticalOffset(int offset) {
        this.mDropDownVerticalOffset = offset;
        this.mDropDownVerticalOffsetSet = true;
    }

    public void setEpicenterBounds(Rect bounds) {
        this.mEpicenterBounds = bounds != null ? new Rect(bounds) : null;
    }

    public Rect getEpicenterBounds() {
        if (this.mEpicenterBounds != null) {
            return new Rect(this.mEpicenterBounds);
        }
        return null;
    }

    public void setDropDownGravity(int gravity) {
        this.mDropDownGravity = gravity;
    }

    public int getWidth() {
        return this.mDropDownWidth;
    }

    public void setWidth(int width) {
        if (this.mIsDexMode && width < -2) {
            this.mDropDownWidth = -2;
            Log.d(TAG, "An abnormal value has been passed. width: " + width);
        } else {
            this.mDropDownWidth = width;
        }
    }

    public void setContentWidth(int width) {
        Drawable popupBackground = this.mPopup.getBackground();
        if (popupBackground != null) {
            popupBackground.getPadding(this.mTempRect);
            View anchor = getAnchorView();
            if (anchor != null) {
                Rect displayFrame = new Rect();
                anchor.getWindowVisibleDisplayFrame(displayFrame);
                this.mDropDownWidth =
                        Math.min(
                                this.mTempRect.left + this.mTempRect.right + width,
                                displayFrame.right - displayFrame.left);
                return;
            }
            this.mDropDownWidth = this.mTempRect.left + this.mTempRect.right + width;
            return;
        }
        setWidth(width);
    }

    public int getHeight() {
        return this.mDropDownHeight;
    }

    public void setHeight(int height) {
        if (height < 0 && -2 != height && -1 != height) {
            if (this.mContext.getApplicationInfo().targetSdkVersion < 26) {
                Log.e(
                        TAG,
                        "Negative value "
                                + height
                                + " passed to ListPopupWindow#setHeight produces undefined"
                                + " results");
            } else {
                throw new IllegalArgumentException(
                        "Invalid height. Must be a positive value, MATCH_PARENT, or WRAP_CONTENT.");
            }
        }
        this.mDropDownHeight = height;
    }

    public void setWindowLayoutType(int layoutType) {
        this.mDropDownWindowLayoutType = layoutType;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener clickListener) {
        this.mItemClickListener = clickListener;
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener selectedListener) {
        this.mItemSelectedListener = selectedListener;
    }

    public void setPromptView(View prompt) {
        boolean showing = isShowing();
        if (showing) {
            removePromptView();
        }
        this.mPromptView = prompt;
        if (showing) {
            show();
        }
    }

    public void postShow() {
        this.mHandler.post(this.mShowDropDownRunnable);
    }

    @Override // com.android.internal.view.menu.ShowableListMenu
    public void show() {
        int widthSpec;
        int heightSpec;
        int widthSpec2;
        int heightSpec2;
        int height = buildDropDown();
        boolean noInputMethod = isInputMethodNotNeeded();
        this.mPopup.setAllowScrollingAnchorParent(!noInputMethod);
        this.mPopup.setWindowLayoutType(this.mDropDownWindowLayoutType);
        boolean z = false;
        if (this.mPopup.isShowing()) {
            if (!getAnchorView().isAttachedToWindow()) {
                return;
            }
            if (this.mDropDownWidth == -1) {
                widthSpec2 = -1;
            } else {
                int widthSpec3 = this.mDropDownWidth;
                if (widthSpec3 == -2) {
                    widthSpec2 = getAnchorView().getWidth();
                } else {
                    widthSpec2 = this.mDropDownWidth;
                }
            }
            if (this.mDropDownHeight == -1) {
                heightSpec2 = noInputMethod ? height : -1;
                if (noInputMethod) {
                    this.mPopup.setWidth(this.mDropDownWidth == -1 ? -1 : 0);
                    this.mPopup.setHeight(0);
                } else {
                    this.mPopup.setWidth(this.mDropDownWidth == -1 ? -1 : 0);
                    this.mPopup.setHeight(-1);
                }
            } else {
                heightSpec2 =
                        (this.mDropDownHeight == -2 || height < this.mDropDownHeight)
                                ? height
                                : this.mDropDownHeight;
            }
            PopupWindow popupWindow = this.mPopup;
            if (!this.mForceIgnoreOutsideTouch && !this.mDropDownAlwaysVisible) {
                z = true;
            }
            popupWindow.setOutsideTouchable(z);
            this.mPopup.update(
                    getAnchorView(),
                    this.mDropDownHorizontalOffset,
                    this.mDropDownVerticalOffset,
                    widthSpec2 < 0 ? -1 : widthSpec2,
                    heightSpec2 < 0 ? -1 : heightSpec2);
            this.mPopup.getContentView().restoreDefaultFocus();
            return;
        }
        if (this.mDropDownWidth == -1) {
            widthSpec = -1;
        } else {
            int widthSpec4 = this.mDropDownWidth;
            if (widthSpec4 == -2) {
                widthSpec = getAnchorView().getWidth();
            } else {
                widthSpec = this.mDropDownWidth;
            }
        }
        if (this.mDropDownHeight == -1) {
            heightSpec = -1;
        } else if (this.mDropDownHeight == -2 || height < this.mDropDownHeight) {
            heightSpec = height;
        } else {
            heightSpec = Math.max(1, this.mDropDownHeight);
        }
        setBlurEffect();
        this.mPopup.setWidth(widthSpec);
        this.mPopup.setHeight(heightSpec);
        this.mPopup.setIsClippedToScreen(true);
        PopupWindow popupWindow2 = this.mPopup;
        if (!this.mForceIgnoreOutsideTouch && !this.mDropDownAlwaysVisible) {
            z = true;
        }
        popupWindow2.setOutsideTouchable(z);
        this.mPopup.setTouchInterceptor(this.mTouchInterceptor);
        this.mPopup.setEpicenterBounds(this.mEpicenterBounds);
        if (this.mOverlapAnchorSet) {
            this.mPopup.setOverlapAnchor(this.mOverlapAnchor);
        }
        this.mPopup.showAsDropDown(
                getAnchorView(),
                this.mDropDownHorizontalOffset,
                this.mDropDownVerticalOffset,
                this.mDropDownGravity);
        this.mDropDownList.setSelection(-1);
        this.mPopup.getContentView().restoreDefaultFocus();
        if (!this.mModal || this.mDropDownList.isInTouchMode()) {
            clearListSelection();
        }
        if (!this.mModal) {
            this.mHandler.post(this.mHideSelector);
        }
    }

    @Override // com.android.internal.view.menu.ShowableListMenu
    public void dismiss() {
        this.mPopup.dismiss();
        removePromptView();
        this.mPopup.setContentView(null);
        this.mDropDownList = null;
        this.mHandler.removeCallbacks(this.mResizePopupRunnable);
    }

    public void dismissImmediate() {
        this.mPopup.setExitTransition(null);
        dismiss();
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        this.mPopup.setOnDismissListener(listener);
    }

    private void removePromptView() {
        if (this.mPromptView != null) {
            ViewParent parent = this.mPromptView.getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(this.mPromptView);
            }
        }
    }

    public void setInputMethodMode(int mode) {
        this.mPopup.setInputMethodMode(mode);
    }

    public int getInputMethodMode() {
        return this.mPopup.getInputMethodMode();
    }

    public void setSelection(int position) {
        DropDownListView list = this.mDropDownList;
        if (isShowing() && list != null) {
            list.setListSelectionHidden(false);
            list.setSelection(position);
            if (list.getChoiceMode() != 0) {
                list.setItemChecked(position, true);
            }
        }
    }

    public void clearListSelection() {
        DropDownListView list = this.mDropDownList;
        if (list != null) {
            list.setListSelectionHidden(true);
            list.hideSelector();
            list.requestLayout();
        }
    }

    @Override // com.android.internal.view.menu.ShowableListMenu
    public boolean isShowing() {
        return this.mPopup.isShowing();
    }

    public boolean isInputMethodNotNeeded() {
        return this.mPopup.getInputMethodMode() == 2;
    }

    public boolean performItemClick(int position) {
        if (isShowing()) {
            if (this.mItemClickListener != null) {
                DropDownListView list = this.mDropDownList;
                View child = list.getChildAt(position - list.getFirstVisiblePosition());
                ListAdapter adapter = list.getAdapter();
                this.mItemClickListener.onItemClick(
                        list, child, position, adapter.getItemId(position));
                return true;
            }
            return true;
        }
        return false;
    }

    public Object getSelectedItem() {
        if (!isShowing()) {
            return null;
        }
        return this.mDropDownList.getSelectedItem();
    }

    public int getSelectedItemPosition() {
        if (!isShowing()) {
            return -1;
        }
        return this.mDropDownList.getSelectedItemPosition();
    }

    public long getSelectedItemId() {
        if (!isShowing()) {
            return Long.MIN_VALUE;
        }
        return this.mDropDownList.getSelectedItemId();
    }

    public View getSelectedView() {
        if (!isShowing()) {
            return null;
        }
        return this.mDropDownList.getSelectedView();
    }

    @Override // com.android.internal.view.menu.ShowableListMenu
    public ListView getListView() {
        return this.mDropDownList;
    }

    DropDownListView createDropDownListView(Context context, boolean hijackFocus) {
        return new DropDownListView(context, hijackFocus);
    }

    void setListItemExpandMax(int max) {
        this.mListItemExpandMaximum = max;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isShowing()
                && keyCode != 62
                && (this.mDropDownList.getSelectedItemPosition() >= 0
                        || !KeyEvent.isConfirmKey(keyCode))) {
            int curIndex = this.mDropDownList.getSelectedItemPosition();
            boolean below = !this.mPopup.isAboveAnchor();
            ListAdapter adapter = this.mAdapter;
            int firstItem = Integer.MAX_VALUE;
            int lastItem = Integer.MIN_VALUE;
            if (adapter != null) {
                boolean allEnabled = adapter.areAllItemsEnabled();
                firstItem = allEnabled ? 0 : this.mDropDownList.lookForSelectablePosition(0, true);
                lastItem =
                        allEnabled
                                ? adapter.getCount() - 1
                                : this.mDropDownList.lookForSelectablePosition(
                                        adapter.getCount() - 1, false);
            }
            if ((below && keyCode == 19 && curIndex <= firstItem)
                    || (!below && keyCode == 20 && curIndex >= lastItem)) {
                clearListSelection();
                this.mPopup.setInputMethodMode(1);
                show();
                return true;
            }
            this.mDropDownList.setListSelectionHidden(false);
            boolean consumed = this.mDropDownList.onKeyDown(keyCode, event);
            if (consumed) {
                this.mPopup.setInputMethodMode(2);
                this.mDropDownList.requestFocusFromTouch();
                show();
                switch (keyCode) {
                    case 19:
                    case 20:
                    case 23:
                    case 66:
                    case 160:
                        break;
                }
                return true;
            }
            if (below && keyCode == 20) {
                if (curIndex == lastItem) {
                    return true;
                }
            } else if (!below && keyCode == 19 && curIndex == firstItem) {
                return true;
            }
        }
        return false;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (isShowing() && this.mDropDownList.getSelectedItemPosition() >= 0) {
            boolean consumed = this.mDropDownList.onKeyUp(keyCode, event);
            if (consumed && KeyEvent.isConfirmKey(keyCode)) {
                dismiss();
            }
            return consumed;
        }
        return false;
    }

    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if ((keyCode == 4 || keyCode == 111) && isShowing()) {
            View anchorView = this.mDropDownAnchorView;
            if (event.getAction() == 0 && event.getRepeatCount() == 0) {
                KeyEvent.DispatcherState state = anchorView.getKeyDispatcherState();
                if (state != null) {
                    state.startTracking(event, this);
                }
                return true;
            }
            if (event.getAction() == 1) {
                KeyEvent.DispatcherState state2 = anchorView.getKeyDispatcherState();
                if (state2 != null) {
                    state2.handleUpEvent(event);
                }
                if (event.isTracking() && !event.isCanceled()) {
                    dismiss();
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public View.OnTouchListener createDragToOpenListener(View src) {
        return new ForwardingListener(src) { // from class: android.widget.ListPopupWindow.1
            @Override // android.widget.ForwardingListener
            public ShowableListMenu getPopup() {
                return ListPopupWindow.this;
            }
        };
    }

    private int buildDropDown() {
        int padding;
        int childWidthSpec;
        int widthMode;
        int widthSize;
        int otherHeights = 0;
        if (this.mDropDownList == null) {
            Context context = this.mContext;
            this.mShowDropDownRunnable =
                    new Runnable() { // from class: android.widget.ListPopupWindow.2
                        @Override // java.lang.Runnable
                        public void run() {
                            View view = ListPopupWindow.this.getAnchorView();
                            if (view != null && view.getWindowToken() != null) {
                                ListPopupWindow.this.show();
                            }
                        }
                    };
            this.mDropDownList = createDropDownListView(context, !this.mModal);
            if (this.mDropDownListHighlight != null) {
                this.mDropDownList.setSelector(this.mDropDownListHighlight);
            }
            this.mDropDownList.setAdapter(this.mAdapter);
            this.mDropDownList.setOnItemClickListener(this.mItemClickListener);
            this.mDropDownList.setFocusable(true);
            this.mDropDownList.setFocusableInTouchMode(true);
            this.mDropDownList.setOnItemSelectedListener(
                    new AdapterView
                            .OnItemSelectedListener() { // from class:
                                                        // android.widget.ListPopupWindow.3
                        @Override // android.widget.AdapterView.OnItemSelectedListener
                        public void onItemSelected(
                                AdapterView<?> parent, View view, int position, long id) {
                            DropDownListView dropDownList;
                            if (position != -1
                                    && (dropDownList = ListPopupWindow.this.mDropDownList)
                                            != null) {
                                dropDownList.setListSelectionHidden(false);
                            }
                        }

                        @Override // android.widget.AdapterView.OnItemSelectedListener
                        public void onNothingSelected(AdapterView<?> parent) {}
                    });
            this.mDropDownList.setOnScrollListener(this.mScrollListener);
            if (this.mItemSelectedListener != null) {
                this.mDropDownList.setOnItemSelectedListener(this.mItemSelectedListener);
            }
            ViewGroup dropDownView = this.mDropDownList;
            View hintView = this.mPromptView;
            if (hintView != null) {
                LinearLayout hintContainer = new LinearLayout(context);
                hintContainer.setOrientation(1);
                LinearLayout.LayoutParams hintParams = new LinearLayout.LayoutParams(-1, 0, 1.0f);
                switch (this.mPromptPosition) {
                    case 0:
                        hintContainer.addView(hintView);
                        hintContainer.addView(dropDownView, hintParams);
                        break;
                    case 1:
                        hintContainer.addView(dropDownView, hintParams);
                        hintContainer.addView(hintView);
                        break;
                    default:
                        Log.e(TAG, "Invalid hint position " + this.mPromptPosition);
                        break;
                }
                if (this.mDropDownWidth >= 0) {
                    widthMode = Integer.MIN_VALUE;
                    widthSize = this.mDropDownWidth;
                } else {
                    widthMode = 0;
                    widthSize = 0;
                }
                int widthSpec = View.MeasureSpec.makeMeasureSpec(widthSize, widthMode);
                hintView.measure(widthSpec, 0);
                LinearLayout.LayoutParams hintParams2 =
                        (LinearLayout.LayoutParams) hintView.getLayoutParams();
                dropDownView = hintContainer;
                otherHeights =
                        hintView.getMeasuredHeight()
                                + hintParams2.topMargin
                                + hintParams2.bottomMargin;
            }
            this.mPopup.setContentView(dropDownView);
        } else {
            View view = this.mPromptView;
            if (view != null) {
                LinearLayout.LayoutParams hintParams3 =
                        (LinearLayout.LayoutParams) view.getLayoutParams();
                otherHeights =
                        view.getMeasuredHeight() + hintParams3.topMargin + hintParams3.bottomMargin;
            }
        }
        Drawable background = this.mPopup.getBackground();
        if (background != null) {
            background.getPadding(this.mTempRect);
            padding = this.mTempRect.top + this.mTempRect.bottom;
            if (!this.mDropDownVerticalOffsetSet && !this.mIsDeviceDefault) {
                this.mDropDownVerticalOffset = -this.mTempRect.top;
            }
        } else {
            this.mTempRect.setEmpty();
            padding = 0;
        }
        boolean ignoreBottomDecorations = this.mPopup.getInputMethodMode() == 2;
        int maxHeight =
                this.mPopup.getMaxAvailableHeight(
                        getAnchorView(), this.mDropDownVerticalOffset, ignoreBottomDecorations);
        if (maxHeight < 0) {
            return 0;
        }
        if (this.mDropDownAlwaysVisible || this.mDropDownHeight == -1) {
            int childWidthSpec2 = maxHeight + padding;
            return childWidthSpec2;
        }
        switch (this.mDropDownWidth) {
            case -2:
                childWidthSpec =
                        View.MeasureSpec.makeMeasureSpec(
                                this.mContext.getResources().getDisplayMetrics().widthPixels
                                        - (this.mTempRect.left + this.mTempRect.right),
                                Integer.MIN_VALUE);
                break;
            case -1:
                childWidthSpec =
                        View.MeasureSpec.makeMeasureSpec(
                                this.mContext.getResources().getDisplayMetrics().widthPixels
                                        - (this.mTempRect.left + this.mTempRect.right),
                                1073741824);
                break;
            default:
                childWidthSpec =
                        View.MeasureSpec.makeMeasureSpec(
                                this.mDropDownWidth - (this.mTempRect.left + this.mTempRect.right),
                                1073741824);
                break;
        }
        int listContent =
                this.mDropDownList.measureHeightOfChildren(
                        childWidthSpec, 0, -1, maxHeight - otherHeights, -1);
        if (listContent > 0) {
            int listPadding =
                    this.mDropDownList.getPaddingTop() + this.mDropDownList.getPaddingBottom();
            otherHeights += padding + listPadding;
        }
        int listPadding2 = listContent + otherHeights;
        return listPadding2;
    }

    private void setBlurEffect() {
        int blurBackgroundColorId;
        if (this.mContext != null && this.mIsDeviceDefault) {
            boolean isThemeApplied =
                    Settings.System.getString(
                                    this.mContext.getContentResolver(),
                                    "current_sec_active_themepackage")
                            != null;
            boolean isReduceTransparency =
                    Settings.System.getInt(
                                    this.mContext.getContentResolver(),
                                    "accessibility_reduce_transparency",
                                    0)
                            == 1;
            SemBlurInfo.Builder builder = new SemBlurInfo.Builder(0);
            if (!isThemeApplied
                    && !isReduceTransparency
                    && this.mPopup.semIsAvailableBlurBackground()) {
                TypedValue colorValue = new TypedValue();
                this.mContext
                        .getTheme()
                        .resolveAttribute(R.attr.parentIsDeviceDefaultDark, colorValue, true);
                if (colorValue.data != 0) {
                    blurBackgroundColorId = R.color.sem_popup_menu_blur_background_dark;
                } else {
                    blurBackgroundColorId = R.color.sem_popup_menu_blur_background;
                }
                builder.setRadius(120);
                builder.setBackgroundColor(
                        this.mContext
                                .getResources()
                                .getColor(blurBackgroundColorId, this.mContext.getTheme()));
                builder.setBackgroundCornerRadius(
                        this.mContext
                                .getResources()
                                .getDimensionPixelSize(R.dimen.sem_popup_menu_corner_radius));
                this.mPopup.getContentView().semSetBlurInfo(builder.build());
                if (this.mDropDownList != null) {
                    this.mDropDownList.setOverScrollMode(2);
                }
            }
        }
    }

    public void setOverlapAnchor(boolean overlap) {
        this.mOverlapAnchorSet = true;
        this.mOverlapAnchor = overlap;
    }

    private class PopupDataSetObserver extends DataSetObserver {
        private PopupDataSetObserver() {}

        @Override // android.database.DataSetObserver
        public void onChanged() {
            if (ListPopupWindow.this.isShowing()) {
                ListPopupWindow.this.show();
            }
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            ListPopupWindow.this.dismiss();
        }
    }

    private class ListSelectorHider implements Runnable {
        private ListSelectorHider() {}

        @Override // java.lang.Runnable
        public void run() {
            ListPopupWindow.this.clearListSelection();
        }
    }

    private class ResizePopupRunnable implements Runnable {
        private ResizePopupRunnable() {}

        @Override // java.lang.Runnable
        public void run() {
            if (ListPopupWindow.this.mIsDeviceDefault) {
                if (ListPopupWindow.this.mDropDownList != null
                        && ListPopupWindow.this.mDropDownList.isAttachedToWindow()
                        && ListPopupWindow.this.mDropDownList.getChildCount()
                                <= ListPopupWindow.this.mListItemExpandMaximum) {
                    ListPopupWindow.this.mPopup.setInputMethodMode(2);
                    ListPopupWindow.this.show();
                    return;
                }
                return;
            }
            if (ListPopupWindow.this.mDropDownList != null
                    && ListPopupWindow.this.mDropDownList.isAttachedToWindow()
                    && ListPopupWindow.this.mDropDownList.getCount()
                            > ListPopupWindow.this.mDropDownList.getChildCount()
                    && ListPopupWindow.this.mDropDownList.getChildCount()
                            <= ListPopupWindow.this.mListItemExpandMaximum) {
                ListPopupWindow.this.mPopup.setInputMethodMode(2);
                ListPopupWindow.this.show();
            }
        }
    }

    private class PopupTouchInterceptor implements View.OnTouchListener {
        private PopupTouchInterceptor() {}

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (action == 0
                    && ListPopupWindow.this.mPopup != null
                    && ListPopupWindow.this.mPopup.isShowing()
                    && x >= 0
                    && x < ListPopupWindow.this.mPopup.getWidth()
                    && y >= 0
                    && y < ListPopupWindow.this.mPopup.getHeight()) {
                ListPopupWindow.this.mHandler.postDelayed(
                        ListPopupWindow.this.mResizePopupRunnable, 250L);
                return false;
            }
            if (action == 1) {
                ListPopupWindow.this.mHandler.removeCallbacks(
                        ListPopupWindow.this.mResizePopupRunnable);
                return false;
            }
            return false;
        }
    }

    private class PopupScrollListener implements AbsListView.OnScrollListener {
        private PopupScrollListener() {}

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(
                AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {}

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 1
                    && !ListPopupWindow.this.isInputMethodNotNeeded()
                    && ListPopupWindow.this.mPopup.getContentView() != null) {
                ListPopupWindow.this.mHandler.removeCallbacks(
                        ListPopupWindow.this.mResizePopupRunnable);
                ListPopupWindow.this.mResizePopupRunnable.run();
            }
        }
    }
}
