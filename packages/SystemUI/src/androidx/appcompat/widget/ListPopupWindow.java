package androidx.appcompat.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import androidx.appcompat.R$styleable;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.core.view.SemBlurCompat;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.os.SeslBuildReflector$SeslVersionReflector;
import androidx.reflect.view.SeslSemWindowManagerReflector;
import androidx.reflect.view.SeslViewRuneReflector;
import androidx.reflect.widget.SeslPopupWindowReflector;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class ListPopupWindow implements ShowableListMenu {
    public static final boolean ONEUI_5_1_1;
    public ListAdapter mAdapter;
    public final Context mContext;
    public View mDropDownAnchorView;
    public int mDropDownGravity;
    public int mDropDownHeight;
    public int mDropDownHorizontalOffset;
    public DropDownListView mDropDownList;
    public int mDropDownVerticalOffset;
    public boolean mDropDownVerticalOffsetSet;
    public int mDropDownWidth;
    public final int mDropDownWindowLayoutType;
    public Rect mEpicenterBounds;
    public final Handler mHandler;
    public final ListSelectorHider mHideSelector;
    public boolean mIsOverflowPopup;
    public AdapterView.OnItemClickListener mItemClickListener;
    public AdapterView.OnItemSelectedListener mItemSelectedListener;
    public final int mListItemExpandMaximum;
    public boolean mModal;
    public PopupDataSetObserver mObserver;
    public boolean mOverlapAnchor;
    public boolean mOverlapAnchorSet;
    public final AppCompatPopupWindow mPopup;
    public final ResizePopupRunnable mResizePopupRunnable;
    public final PopupScrollListener mScrollListener;
    public final Rect mTempRect;
    public final PopupTouchInterceptor mTouchInterceptor;

    public class ListSelectorHider implements Runnable {
        public ListSelectorHider() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            DropDownListView dropDownListView = ListPopupWindow.this.mDropDownList;
            if (dropDownListView != null) {
                dropDownListView.mListSelectionHidden = true;
                dropDownListView.requestLayout();
            }
        }
    }

    public class PopupDataSetObserver extends DataSetObserver {
        public PopupDataSetObserver() {
        }

        @Override // android.database.DataSetObserver
        public final void onChanged() throws Resources.NotFoundException {
            if (ListPopupWindow.this.mPopup.isShowing()) {
                ListPopupWindow.this.show();
            }
        }

        @Override // android.database.DataSetObserver
        public final void onInvalidated() {
            ListPopupWindow.this.dismiss();
        }
    }

    public class PopupTouchInterceptor implements View.OnTouchListener {
        public PopupTouchInterceptor() {
        }

        @Override // android.view.View.OnTouchListener
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            AppCompatPopupWindow appCompatPopupWindow;
            int action = motionEvent.getAction();
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (action == 0 && (appCompatPopupWindow = ListPopupWindow.this.mPopup) != null && appCompatPopupWindow.isShowing() && x >= 0 && x < ListPopupWindow.this.mPopup.getWidth() && y >= 0 && y < ListPopupWindow.this.mPopup.getHeight()) {
                ListPopupWindow listPopupWindow = ListPopupWindow.this;
                listPopupWindow.mHandler.postDelayed(listPopupWindow.mResizePopupRunnable, 250L);
                return false;
            }
            if (action != 1) {
                return false;
            }
            ListPopupWindow listPopupWindow2 = ListPopupWindow.this;
            listPopupWindow2.mHandler.removeCallbacks(listPopupWindow2.mResizePopupRunnable);
            return false;
        }
    }

    public class ResizePopupRunnable implements Runnable {
        public ResizePopupRunnable() {
        }

        @Override // java.lang.Runnable
        public final void run() throws Resources.NotFoundException {
            DropDownListView dropDownListView = ListPopupWindow.this.mDropDownList;
            if (dropDownListView == null || !dropDownListView.isAttachedToWindow() || ListPopupWindow.this.mDropDownList.getCount() <= ListPopupWindow.this.mDropDownList.getChildCount()) {
                return;
            }
            int childCount = ListPopupWindow.this.mDropDownList.getChildCount();
            ListPopupWindow listPopupWindow = ListPopupWindow.this;
            if (childCount <= listPopupWindow.mListItemExpandMaximum) {
                listPopupWindow.mPopup.setInputMethodMode(2);
                ListPopupWindow.this.show();
            }
        }
    }

    static {
        ONEUI_5_1_1 = SeslBuildReflector$SeslVersionReflector.getField_SEM_PLATFORM_INT() >= 140500;
    }

    public ListPopupWindow(Context context) {
        this(context, null, R.attr.listPopupWindowStyle);
    }

    public DropDownListView createDropDownListView(Context context, boolean z) {
        return new DropDownListView(context, z);
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public final void dismiss() {
        AppCompatPopupWindow appCompatPopupWindow = this.mPopup;
        appCompatPopupWindow.dismiss();
        appCompatPopupWindow.setContentView(null);
        this.mDropDownList = null;
        this.mHandler.removeCallbacks(this.mResizePopupRunnable);
    }

    public final Drawable getBackground() {
        return this.mPopup.getBackground();
    }

    public final int getHorizontalOffset() {
        return this.mDropDownHorizontalOffset;
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public final DropDownListView getListView() {
        return this.mDropDownList;
    }

    public final int getVerticalOffset() {
        if (this.mDropDownVerticalOffsetSet) {
            return this.mDropDownVerticalOffset;
        }
        return 0;
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public final boolean isShowing() {
        return this.mPopup.isShowing();
    }

    public void setAdapter(ListAdapter listAdapter) {
        PopupDataSetObserver popupDataSetObserver = this.mObserver;
        if (popupDataSetObserver == null) {
            this.mObserver = new PopupDataSetObserver();
        } else {
            ListAdapter listAdapter2 = this.mAdapter;
            if (listAdapter2 != null) {
                listAdapter2.unregisterDataSetObserver(popupDataSetObserver);
            }
        }
        this.mAdapter = listAdapter;
        if (listAdapter != null) {
            listAdapter.registerDataSetObserver(this.mObserver);
        }
        DropDownListView dropDownListView = this.mDropDownList;
        if (dropDownListView != null) {
            dropDownListView.setAdapter(this.mAdapter);
        }
    }

    public final void setBackgroundDrawable(Drawable drawable) {
        this.mPopup.setBackgroundDrawable(drawable);
    }

    public final void setContentWidth(int i) {
        Drawable background = this.mPopup.getBackground();
        if (background == null) {
            this.mDropDownWidth = i;
            return;
        }
        background.getPadding(this.mTempRect);
        Rect rect = this.mTempRect;
        this.mDropDownWidth = rect.left + rect.right + i;
    }

    public final void setHorizontalOffset(int i) {
        this.mDropDownHorizontalOffset = i;
    }

    public final void setVerticalOffset(int i) {
        this.mDropDownVerticalOffset = i;
        this.mDropDownVerticalOffsetSet = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01d1 A[ADDED_TO_REGION] */
    @Override // androidx.appcompat.view.menu.ShowableListMenu
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void show() throws Resources.NotFoundException {
        int i;
        int i2;
        int i3;
        int iMakeMeasureSpec;
        int paddingBottom;
        Context context;
        boolean blurEffectPreset;
        DropDownListView dropDownListView;
        DropDownListView dropDownListView2;
        Activity activity;
        int i4;
        int dimensionPixelSize;
        int i5;
        DropDownListView dropDownListView3 = this.mDropDownList;
        AppCompatPopupWindow appCompatPopupWindow = this.mPopup;
        if (dropDownListView3 == null) {
            Context context2 = this.mContext;
            new Runnable() { // from class: androidx.appcompat.widget.ListPopupWindow.2
                @Override // java.lang.Runnable
                public final void run() throws Resources.NotFoundException {
                    View view = ListPopupWindow.this.mDropDownAnchorView;
                    if (view == null || view.getWindowToken() == null) {
                        return;
                    }
                    ListPopupWindow.this.show();
                }
            };
            DropDownListView dropDownListViewCreateDropDownListView = createDropDownListView(context2, !this.mModal);
            this.mDropDownList = dropDownListViewCreateDropDownListView;
            dropDownListViewCreateDropDownListView.setAdapter(this.mAdapter);
            this.mDropDownList.setOnItemClickListener(this.mItemClickListener);
            this.mDropDownList.setFocusable(true);
            this.mDropDownList.setFocusableInTouchMode(true);
            this.mDropDownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: androidx.appcompat.widget.ListPopupWindow.3
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public final void onItemSelected(AdapterView adapterView, View view, int i6, long j) {
                    DropDownListView dropDownListView4;
                    if (i6 == -1 || (dropDownListView4 = ListPopupWindow.this.mDropDownList) == null) {
                        return;
                    }
                    dropDownListView4.mListSelectionHidden = false;
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public final void onNothingSelected(AdapterView adapterView) {
                }
            });
            this.mDropDownList.setOnScrollListener(this.mScrollListener);
            AdapterView.OnItemSelectedListener onItemSelectedListener = this.mItemSelectedListener;
            if (onItemSelectedListener != null) {
                this.mDropDownList.setOnItemSelectedListener(onItemSelectedListener);
            }
            appCompatPopupWindow.setContentView(this.mDropDownList);
        }
        Drawable background = appCompatPopupWindow.getBackground();
        if (background != null) {
            background.getPadding(this.mTempRect);
            Rect rect = this.mTempRect;
            i = rect.top + rect.bottom;
        } else {
            this.mTempRect.setEmpty();
            i = 0;
        }
        boolean z = appCompatPopupWindow.getInputMethodMode() == 2;
        View view = this.mDropDownAnchorView;
        int maxAvailableHeight = appCompatPopupWindow.getMaxAvailableHeight(view, this.mDropDownVerticalOffset, z);
        if (ONEUI_5_1_1 || !this.mIsOverflowPopup) {
            i2 = 1;
            i3 = 0;
        } else {
            Point point = new Point();
            DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService("display");
            if (displayManager == null) {
                Log.w("ListPopupWindow", "displayManager is null, can not update height");
            } else {
                Display display = displayManager.getDisplay(0);
                if (display == null) {
                    Log.w("ListPopupWindow", "display is null, can not update height");
                } else if (SeslSemWindowManagerReflector.isTableMode()) {
                    Context baseContext = this.mContext;
                    while (true) {
                        if (!(baseContext instanceof ContextWrapper)) {
                            activity = null;
                            break;
                        } else {
                            if (baseContext instanceof Activity) {
                                activity = (Activity) baseContext;
                                break;
                            }
                            baseContext = ((ContextWrapper) baseContext).getBaseContext();
                        }
                    }
                    if (activity == null || !activity.isInMultiWindowMode()) {
                        int[] iArr = new int[2];
                        view.getLocationOnScreen(iArr);
                        display.getRealSize(point);
                        if (SeslViewRuneReflector.supportFoldableDualDisplay()) {
                            if (this.mContext.getResources().getConfiguration().orientation == 2) {
                                int i6 = point.y;
                                int i7 = point.x;
                                i4 = i6 > i7 ? i7 / 2 : i6 / 2;
                            } else {
                                i4 = 0;
                            }
                            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i4, "center = ", " , anchor top = ");
                            sbM.append(iArr[1]);
                            Log.e("ListPopupWindow", sbM.toString());
                            if (i4 == 0) {
                                int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.sesl_menu_popup_top_margin);
                                int dimensionPixelSize3 = this.mContext.getResources().getDimensionPixelSize(R.dimen.sesl_menu_popup_bottom_margin);
                                int i8 = iArr[1];
                                if (i4 > i8) {
                                    i5 = ((i4 - i8) - dimensionPixelSize2) - dimensionPixelSize3;
                                    i2 = 1;
                                    i3 = 0;
                                } else {
                                    WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
                                    if (windowManager != null) {
                                        Insets insets = windowManager.getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.systemBars());
                                        dimensionPixelSize = insets.bottom;
                                        i3 = 0;
                                        i2 = 1;
                                        Log.d("ListPopupWindow", "systemBar insets = " + insets);
                                    } else {
                                        i2 = 1;
                                        i3 = 0;
                                        int identifier = this.mContext.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
                                        dimensionPixelSize = identifier > 0 ? this.mContext.getResources().getDimensionPixelSize(identifier) : 0;
                                    }
                                    ListPopupWindow$$ExternalSyntheticOutline0.m(dimensionPixelSize, "navigationBarHeight = ", "ListPopupWindow");
                                    int i9 = iArr[i2];
                                    int i10 = i9 - i4;
                                    i5 = i10 > (i4 - dimensionPixelSize) / 2 ? (i10 - dimensionPixelSize2) - dimensionPixelSize3 : (((point.y - i9) - dimensionPixelSize2) - dimensionPixelSize3) - dimensionPixelSize;
                                }
                            }
                            if (i5 > 0 && i5 < maxAvailableHeight) {
                                maxAvailableHeight = i5;
                            }
                        } else {
                            if (SeslViewRuneReflector.supportFoldableNoSubDisplay() && this.mContext.getResources().getConfiguration().orientation == 1) {
                                int i11 = point.y;
                                int i12 = point.x;
                                i4 = i11 > i12 ? i11 / 2 : i12 / 2;
                            }
                            StringBuilder sbM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i4, "center = ", " , anchor top = ");
                            sbM2.append(iArr[1]);
                            Log.e("ListPopupWindow", sbM2.toString());
                            if (i4 == 0) {
                            }
                            if (i5 > 0) {
                                maxAvailableHeight = i5;
                            }
                        }
                    }
                }
            }
            i2 = 1;
            i3 = 0;
            i5 = -2;
            if (i5 > 0) {
            }
        }
        if (this.mDropDownHeight == -1) {
            paddingBottom = maxAvailableHeight + i;
        } else {
            int i13 = this.mDropDownWidth;
            if (i13 == -2) {
                int i14 = this.mContext.getResources().getDisplayMetrics().widthPixels;
                Rect rect2 = this.mTempRect;
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i14 - (rect2.left + rect2.right), Integer.MIN_VALUE);
            } else if (i13 != -1) {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i13, 1073741824);
            } else {
                int i15 = this.mContext.getResources().getDisplayMetrics().widthPixels;
                Rect rect3 = this.mTempRect;
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i15 - (rect3.left + rect3.right), 1073741824);
            }
            int iMeasureHeightOfChildrenCompat = this.mDropDownList.measureHeightOfChildrenCompat(iMakeMeasureSpec, maxAvailableHeight);
            paddingBottom = iMeasureHeightOfChildrenCompat + (iMeasureHeightOfChildrenCompat > 0 ? this.mDropDownList.getPaddingBottom() + this.mDropDownList.getPaddingTop() + i : i3);
        }
        int i16 = this.mPopup.getInputMethodMode() == 2 ? i2 : i3;
        appCompatPopupWindow.setWindowLayoutType(this.mDropDownWindowLayoutType);
        boolean z2 = i16 ^ 1;
        Class cls = SeslPopupWindowReflector.mClass;
        Class[] clsArr = new Class[i2];
        clsArr[i3] = Boolean.TYPE;
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(cls, "setAllowScrollingAnchorParent", clsArr);
        if (declaredMethod != null) {
            SeslBaseReflector.invoke(appCompatPopupWindow, declaredMethod, Boolean.valueOf(z2));
        }
        if (appCompatPopupWindow.isShowing()) {
            if (this.mDropDownAnchorView.isAttachedToWindow()) {
                int width = this.mDropDownWidth;
                if (width == -1) {
                    width = -1;
                } else if (width == -2) {
                    width = this.mDropDownAnchorView.getWidth();
                }
                int i17 = this.mDropDownHeight;
                if (i17 == -1) {
                    if (i16 == 0) {
                        paddingBottom = -1;
                    }
                    if (i16 != 0) {
                        appCompatPopupWindow.setWidth(this.mDropDownWidth == -1 ? -1 : i3);
                        appCompatPopupWindow.setHeight(i3);
                    } else {
                        appCompatPopupWindow.setWidth(this.mDropDownWidth == -1 ? -1 : 0);
                        appCompatPopupWindow.setHeight(-1);
                    }
                } else if (i17 != -2) {
                    paddingBottom = i17;
                }
                appCompatPopupWindow.setOutsideTouchable(true);
                this.mPopup.update(this.mDropDownAnchorView, this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, width < 0 ? -1 : width, paddingBottom < 0 ? -1 : paddingBottom);
                return;
            }
            return;
        }
        int width2 = this.mDropDownWidth;
        if (width2 == -1) {
            width2 = -1;
        } else if (width2 == -2) {
            width2 = this.mDropDownAnchorView.getWidth();
        }
        int i18 = this.mDropDownHeight;
        if (i18 == -1) {
            paddingBottom = -1;
        } else if (i18 != -2) {
            paddingBottom = i18;
        }
        View contentView = appCompatPopupWindow.getContentView();
        if (contentView == null || (context = this.mContext) == null || appCompatPopupWindow.mIsReplacedPoupBackground) {
            blurEffectPreset = false;
        } else {
            boolean zIsLightTheme = SeslMisc.isLightTheme(context);
            blurEffectPreset = SemBlurCompat.setBlurEffectPreset(contentView, zIsLightTheme ? 110 : 125, zIsLightTheme ? null : Integer.valueOf(context.getResources().getColor(R.color.sesl_popup_menu_blur_background_dark, context.getTheme())), Float.valueOf(context.getResources().getDimension(R.dimen.sesl_menu_popup_corner_radius)));
        }
        if (blurEffectPreset && (dropDownListView2 = this.mDropDownList) != null) {
            dropDownListView2.setOverScrollMode(2);
        }
        if ((Settings.System.getString(this.mContext.getContentResolver(), SettingsHelper.INDEX_CURRENT_SEC_ACTIVE_THEMEPACKAGE) != null) && !appCompatPopupWindow.mIsReplacedPoupBackground) {
            Drawable background2 = appCompatPopupWindow.getBackground();
            if (background2 instanceof LayerDrawable) {
                Drawable drawable = ((LayerDrawable) background2).getDrawable(0);
                if (drawable instanceof GradientDrawable) {
                    ((GradientDrawable) drawable).setStroke(this.mContext.getResources().getDimensionPixelSize(R.dimen.sesl_menu_popup_stroke_width), this.mContext.getResources().getColor(R.color.sesl_menu_popup_background_stroke_color, this.mContext.getTheme()));
                }
            }
        }
        appCompatPopupWindow.setWidth(width2);
        appCompatPopupWindow.setHeight(paddingBottom);
        appCompatPopupWindow.setIsClippedToScreen(true);
        appCompatPopupWindow.setOutsideTouchable(true);
        appCompatPopupWindow.setTouchInterceptor(this.mTouchInterceptor);
        if (this.mOverlapAnchorSet) {
            appCompatPopupWindow.setOverlapAnchor(this.mOverlapAnchor);
        }
        appCompatPopupWindow.setEpicenterBounds(this.mEpicenterBounds);
        appCompatPopupWindow.showAsDropDown(this.mDropDownAnchorView, this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, this.mDropDownGravity);
        this.mDropDownList.setSelection(-1);
        if ((!this.mModal || this.mDropDownList.isInTouchMode()) && (dropDownListView = this.mDropDownList) != null) {
            dropDownListView.mListSelectionHidden = true;
            dropDownListView.requestLayout();
        }
        if (this.mModal) {
            return;
        }
        this.mHandler.post(this.mHideSelector);
    }

    public ListPopupWindow(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.listPopupWindowStyle);
    }

    public ListPopupWindow(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ListPopupWindow(Context context, AttributeSet attributeSet, int i, int i2) {
        this.mDropDownHeight = -2;
        this.mDropDownWidth = -2;
        this.mDropDownWindowLayoutType = 1002;
        this.mDropDownGravity = 0;
        this.mListItemExpandMaximum = Integer.MAX_VALUE;
        this.mResizePopupRunnable = new ResizePopupRunnable();
        this.mTouchInterceptor = new PopupTouchInterceptor();
        this.mScrollListener = new PopupScrollListener();
        this.mHideSelector = new ListSelectorHider();
        this.mTempRect = new Rect();
        this.mContext = context;
        this.mHandler = new Handler(context.getMainLooper());
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ListPopupWindow, i, i2);
        this.mDropDownHorizontalOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(0, 0);
        int dimensionPixelOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(1, 0);
        this.mDropDownVerticalOffset = dimensionPixelOffset;
        if (dimensionPixelOffset != 0) {
            this.mDropDownVerticalOffsetSet = true;
        }
        typedArrayObtainStyledAttributes.recycle();
        AppCompatPopupWindow appCompatPopupWindow = new AppCompatPopupWindow(context, attributeSet, i, i2);
        this.mPopup = appCompatPopupWindow;
        appCompatPopupWindow.setInputMethodMode(1);
    }

    public class PopupScrollListener implements AbsListView.OnScrollListener {
        public PopupScrollListener() {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public final void onScrollStateChanged(AbsListView absListView, int i) throws Resources.NotFoundException {
            if (i != 1 || ListPopupWindow.this.mPopup.getInputMethodMode() == 2 || ListPopupWindow.this.mPopup.getContentView() == null) {
                return;
            }
            ListPopupWindow listPopupWindow = ListPopupWindow.this;
            listPopupWindow.mHandler.removeCallbacks(listPopupWindow.mResizePopupRunnable);
            ListPopupWindow.this.mResizePopupRunnable.run();
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public final void onScroll(AbsListView absListView, int i, int i2, int i3) {
        }
    }
}
