package androidx.appcompat.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
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
import androidx.core.view.ViewCompat;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.p001os.SeslBuildReflector$SeslVersionReflector;
import androidx.reflect.provider.SeslSettingsReflector$SeslSystemReflector;
import androidx.reflect.view.SeslSemWindowManagerReflector;
import androidx.reflect.view.SeslViewReflector;
import androidx.reflect.view.SeslViewRuneReflector;
import androidx.reflect.widget.SeslPopupWindowReflector;
import com.android.systemui.R;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ListPopupWindow implements ShowableListMenu {
    public static final boolean ONEUI_5_1_1;
    public ListAdapter mAdapter;
    public final Context mContext;
    public View mDropDownAnchorView;
    public int mDropDownGravity;
    public final int mDropDownHeight;
    public int mDropDownHorizontalOffset;
    public DropDownListView mDropDownList;
    public int mDropDownVerticalOffset;
    public boolean mDropDownVerticalOffsetSet;
    public int mDropDownWidth;
    public final int mDropDownWindowLayoutType;
    public Rect mEpicenterBounds;
    public boolean mForceShowUpper;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ListSelectorHider implements Runnable {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PopupDataSetObserver extends DataSetObserver {
        public PopupDataSetObserver() {
        }

        @Override // android.database.DataSetObserver
        public final void onChanged() {
            if (ListPopupWindow.this.isShowing()) {
                ListPopupWindow.this.show();
            }
        }

        @Override // android.database.DataSetObserver
        public final void onInvalidated() {
            ListPopupWindow.this.dismiss();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PopupTouchInterceptor implements View.OnTouchListener {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ResizePopupRunnable implements Runnable {
        public ResizePopupRunnable() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            DropDownListView dropDownListView = ListPopupWindow.this.mDropDownList;
            if (dropDownListView != null) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (!ViewCompat.Api19Impl.isAttachedToWindow(dropDownListView) || ListPopupWindow.this.mDropDownList.getCount() <= ListPopupWindow.this.mDropDownList.getChildCount()) {
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
        Rect rect = this.mTempRect;
        background.getPadding(rect);
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
    /* JADX WARN: Removed duplicated region for block: B:159:0x0359  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0381  */
    @Override // androidx.appcompat.view.menu.ShowableListMenu
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void show() {
        int i;
        int paddingBottom;
        int i2;
        int i3;
        DropDownListView dropDownListView;
        Object obj;
        Object obj2;
        boolean z;
        boolean z2;
        Constructor constructor;
        Activity activity;
        int dimensionPixelSize;
        int i4;
        DropDownListView dropDownListView2 = this.mDropDownList;
        Context context = this.mContext;
        AppCompatPopupWindow appCompatPopupWindow = this.mPopup;
        if (dropDownListView2 == null) {
            new Runnable() { // from class: androidx.appcompat.widget.ListPopupWindow.2
                @Override // java.lang.Runnable
                public final void run() {
                    View view = ListPopupWindow.this.mDropDownAnchorView;
                    if (view == null || view.getWindowToken() == null) {
                        return;
                    }
                    ListPopupWindow.this.show();
                }
            };
            DropDownListView createDropDownListView = createDropDownListView(context, !this.mModal);
            this.mDropDownList = createDropDownListView;
            createDropDownListView.setAdapter(this.mAdapter);
            this.mDropDownList.setOnItemClickListener(this.mItemClickListener);
            this.mDropDownList.setFocusable(true);
            this.mDropDownList.setFocusableInTouchMode(true);
            this.mDropDownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: androidx.appcompat.widget.ListPopupWindow.3
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public final void onItemSelected(AdapterView adapterView, View view, int i5, long j) {
                    DropDownListView dropDownListView3;
                    if (i5 == -1 || (dropDownListView3 = ListPopupWindow.this.mDropDownList) == null) {
                        return;
                    }
                    dropDownListView3.mListSelectionHidden = false;
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
        int i5 = 0;
        Rect rect = this.mTempRect;
        if (background != null) {
            background.getPadding(rect);
            i = rect.top + rect.bottom;
        } else {
            rect.setEmpty();
            i = 0;
        }
        boolean z3 = appCompatPopupWindow.getInputMethodMode() == 2;
        View view = this.mDropDownAnchorView;
        int maxAvailableHeight = appCompatPopupWindow.getMaxAvailableHeight(view, this.mDropDownVerticalOffset, z3);
        if (!ONEUI_5_1_1 && this.mIsOverflowPopup) {
            Point point = new Point();
            DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
            if (displayManager == null) {
                Log.w("ListPopupWindow", "displayManager is null, can not update height");
            } else {
                Display display = displayManager.getDisplay(0);
                if (display == null) {
                    Log.w("ListPopupWindow", "display is null, can not update height");
                } else if (SeslSemWindowManagerReflector.isTableMode()) {
                    Context context2 = context;
                    while (true) {
                        if (!(context2 instanceof ContextWrapper)) {
                            activity = null;
                            break;
                        } else {
                            if (context2 instanceof Activity) {
                                activity = (Activity) context2;
                                break;
                            }
                            context2 = ((ContextWrapper) context2).getBaseContext();
                        }
                    }
                    if (activity == null || !activity.isInMultiWindowMode()) {
                        int[] iArr = new int[2];
                        view.getLocationOnScreen(iArr);
                        display.getRealSize(point);
                        if (SeslViewRuneReflector.supportFoldableDualDisplay()) {
                            if (context.getResources().getConfiguration().orientation == 2) {
                                int i6 = point.y;
                                int i7 = point.x;
                                i5 = i6 > i7 ? i7 / 2 : i6 / 2;
                            }
                        } else if (SeslViewRuneReflector.supportFoldableNoSubDisplay() && context.getResources().getConfiguration().orientation == 1) {
                            int i8 = point.y;
                            int i9 = point.x;
                            i5 = i8 > i9 ? i8 / 2 : i9 / 2;
                        }
                        StringBuilder m1m = AbstractC0000x2c234b15.m1m("center = ", i5, " , anchor top = ");
                        m1m.append(iArr[1]);
                        Log.e("ListPopupWindow", m1m.toString());
                        if (i5 != 0) {
                            int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.sesl_menu_popup_top_margin);
                            int dimensionPixelSize3 = context.getResources().getDimensionPixelSize(R.dimen.sesl_menu_popup_bottom_margin);
                            int i10 = iArr[1];
                            if (i5 > i10) {
                                i4 = ((i5 - i10) - dimensionPixelSize2) - dimensionPixelSize3;
                            } else {
                                WindowManager windowManager = (WindowManager) context.getSystemService("window");
                                if (windowManager != null) {
                                    Insets insets = windowManager.getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.systemBars());
                                    dimensionPixelSize = insets.bottom;
                                    Log.d("ListPopupWindow", "systemBar insets = " + insets);
                                } else {
                                    int identifier = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
                                    dimensionPixelSize = identifier > 0 ? context.getResources().getDimensionPixelSize(identifier) : 0;
                                }
                                ListPopupWindow$$ExternalSyntheticOutline0.m10m("navigationBarHeight = ", dimensionPixelSize, "ListPopupWindow");
                                int i11 = iArr[1];
                                int i12 = i11 - i5;
                                i4 = i12 > (i5 - dimensionPixelSize) / 2 ? (i12 - dimensionPixelSize2) - dimensionPixelSize3 : (((point.y - i11) - dimensionPixelSize2) - dimensionPixelSize3) - dimensionPixelSize;
                            }
                            if (i4 > 0 && i4 < maxAvailableHeight) {
                                maxAvailableHeight = i4;
                            }
                        }
                    }
                }
            }
            i4 = -2;
            if (i4 > 0) {
                maxAvailableHeight = i4;
            }
        }
        int i13 = this.mDropDownHeight;
        if (i13 == -1) {
            paddingBottom = maxAvailableHeight + i;
        } else {
            int i14 = this.mDropDownWidth;
            int measureHeightOfChildrenCompat = this.mDropDownList.measureHeightOfChildrenCompat(i14 != -2 ? i14 != -1 ? View.MeasureSpec.makeMeasureSpec(i14, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) : View.MeasureSpec.makeMeasureSpec(context.getResources().getDisplayMetrics().widthPixels - (rect.left + rect.right), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) : View.MeasureSpec.makeMeasureSpec(context.getResources().getDisplayMetrics().widthPixels - (rect.left + rect.right), VideoPlayer.MEDIA_ERROR_SYSTEM), maxAvailableHeight + 0);
            paddingBottom = measureHeightOfChildrenCompat + (measureHeightOfChildrenCompat > 0 ? this.mDropDownList.getPaddingBottom() + this.mDropDownList.getPaddingTop() + i + 0 : 0);
        }
        boolean z4 = appCompatPopupWindow.getInputMethodMode() == 2;
        appCompatPopupWindow.setWindowLayoutType(this.mDropDownWindowLayoutType);
        boolean z5 = !z4;
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(SeslPopupWindowReflector.mClass, "setAllowScrollingAnchorParent", Boolean.TYPE);
        if (declaredMethod != null) {
            SeslBaseReflector.invoke(appCompatPopupWindow, declaredMethod, Boolean.valueOf(z5));
        }
        if (appCompatPopupWindow.isShowing()) {
            View view2 = this.mDropDownAnchorView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api19Impl.isAttachedToWindow(view2)) {
                int i15 = this.mDropDownWidth;
                if (i15 == -1) {
                    i15 = -1;
                } else if (i15 == -2) {
                    i15 = this.mDropDownAnchorView.getWidth();
                }
                if (i13 == -1) {
                    i13 = z4 ? paddingBottom : -1;
                    if (z4) {
                        appCompatPopupWindow.setWidth(this.mDropDownWidth == -1 ? -1 : 0);
                        appCompatPopupWindow.setHeight(0);
                    } else {
                        appCompatPopupWindow.setWidth(this.mDropDownWidth == -1 ? -1 : 0);
                        appCompatPopupWindow.setHeight(-1);
                    }
                } else if (i13 == -2) {
                    i13 = paddingBottom;
                }
                appCompatPopupWindow.setOutsideTouchable(true);
                int i16 = this.mDropDownVerticalOffset;
                if (this.mForceShowUpper) {
                    i16 -= paddingBottom;
                    if (!this.mOverlapAnchor) {
                        i16 -= this.mDropDownAnchorView.getHeight();
                    }
                }
                this.mPopup.update(this.mDropDownAnchorView, this.mDropDownHorizontalOffset, i16, i15 < 0 ? -1 : i15, i13 < 0 ? -1 : i13);
                return;
            }
            return;
        }
        int i17 = this.mDropDownWidth;
        if (i17 == -1) {
            i3 = -2;
            i2 = -1;
        } else {
            if (i17 == -2) {
                i17 = this.mDropDownAnchorView.getWidth();
            }
            i2 = i17;
            i3 = -2;
        }
        if (i13 == -1) {
            i13 = -1;
        } else if (i13 == i3) {
            i13 = paddingBottom;
        }
        if (appCompatPopupWindow.getContentView() != null && context != null) {
            if (!(Settings.System.getString(context.getContentResolver(), "current_sec_active_themepackage") != null)) {
                Method declaredMethod2 = SeslBaseReflector.getDeclaredMethod(SeslSettingsReflector$SeslSystemReflector.mClass, "hidden_SEM_ACCESSIBILITY_REDUCE_TRANSPARENCY", new Class[0]);
                if (declaredMethod2 != null) {
                    obj = null;
                    obj2 = SeslBaseReflector.invoke(null, declaredMethod2, new Object[0]);
                } else {
                    obj = null;
                    obj2 = null;
                }
                String str = obj2 instanceof String ? (String) obj2 : "not_supported";
                if (str.equals("not_supported")) {
                    z2 = true;
                } else {
                    z = true;
                    if (Settings.System.getInt(context.getContentResolver(), str, 0) == 1) {
                        z2 = true;
                        if (!z && (z2 ^ appCompatPopupWindow.mIsReplacedPoupBackground)) {
                            try {
                                constructor = Class.forName("android.view.SemBlurInfo$Builder").getDeclaredConstructor(Integer.TYPE);
                            } catch (ClassNotFoundException | NoSuchMethodException e) {
                                Log.e("SeslBaseReflector", "failed to get reflection - " + e);
                                constructor = obj;
                            }
                            if (constructor != 0) {
                                try {
                                    obj = constructor.newInstance(0);
                                } catch (IllegalAccessException e2) {
                                    Log.e("SeslSemBlurInfoReflector", "semCreateBlurBuilder IllegalAccessException", e2);
                                } catch (InstantiationException e3) {
                                    Log.e("SeslSemBlurInfoReflector", "semCreateBlurBuilder InstantiationException", e3);
                                } catch (InvocationTargetException e4) {
                                    Log.e("SeslSemBlurInfoReflector", "semCreateBlurBuilder InvocationTargetException", e4);
                                }
                            }
                            if (obj != null) {
                                Class cls = Integer.TYPE;
                                Method declaredMethod3 = SeslBaseReflector.getDeclaredMethod("android.view.SemBlurInfo$Builder", "hidden_setRadius", cls);
                                if (declaredMethod3 != null) {
                                    declaredMethod3.setAccessible(true);
                                    SeslBaseReflector.invoke(obj, declaredMethod3, 120);
                                }
                                int color = context.getResources().getColor(SeslMisc.isLightTheme(context) ? R.color.sesl_popup_menu_blur_background : R.color.sesl_popup_menu_blur_background_dark, context.getTheme());
                                Method declaredMethod4 = SeslBaseReflector.getDeclaredMethod("android.view.SemBlurInfo$Builder", "hidden_setBackgroundColor", cls);
                                if (declaredMethod4 != null) {
                                    declaredMethod4.setAccessible(true);
                                    SeslBaseReflector.invoke(obj, declaredMethod4, Integer.valueOf(color));
                                }
                                float dimensionPixelSize4 = context.getResources().getDimensionPixelSize(R.dimen.sesl_menu_popup_corner_radius);
                                Method declaredMethod5 = SeslBaseReflector.getDeclaredMethod("android.view.SemBlurInfo$Builder", "hidden_setBackgroundCornerRadius", Float.TYPE);
                                if (declaredMethod5 != null) {
                                    declaredMethod5.setAccessible(true);
                                    SeslBaseReflector.invoke(obj, declaredMethod5, Float.valueOf(dimensionPixelSize4));
                                }
                                View contentView = appCompatPopupWindow.getContentView();
                                Method declaredMethod6 = SeslBaseReflector.getDeclaredMethod("android.view.SemBlurInfo$Builder", "hidden_build", new Class[0]);
                                if (declaredMethod6 != null) {
                                    declaredMethod6.setAccessible(true);
                                    Object invoke = SeslBaseReflector.invoke(obj, declaredMethod6, new Object[0]);
                                    Class cls2 = SeslViewReflector.mClass;
                                    try {
                                        Method declaredMethod7 = SeslBaseReflector.getDeclaredMethod(SeslViewReflector.mClass, "hidden_semSetBlurInfo", Class.forName("android.view.SemBlurInfo"));
                                        if (declaredMethod7 != null) {
                                            SeslBaseReflector.invoke(contentView, declaredMethod7, invoke);
                                        }
                                    } catch (ClassNotFoundException e5) {
                                        Log.e("SeslViewReflector", "semSetBlurInfo ClassNotFoundException", e5);
                                    }
                                }
                                DropDownListView dropDownListView3 = this.mDropDownList;
                                if (dropDownListView3 != null) {
                                    dropDownListView3.setOverScrollMode(2);
                                }
                            }
                        }
                    } else {
                        z2 = true;
                    }
                }
                z = false;
                if (!z) {
                    constructor = Class.forName("android.view.SemBlurInfo$Builder").getDeclaredConstructor(Integer.TYPE);
                    if (constructor != 0) {
                    }
                    if (obj != null) {
                    }
                }
            }
        }
        appCompatPopupWindow.setWidth(i2);
        appCompatPopupWindow.setHeight(i13);
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
        this.mForceShowUpper = false;
        this.mContext = context;
        this.mHandler = new Handler(context.getMainLooper());
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ListPopupWindow, i, i2);
        this.mDropDownHorizontalOffset = obtainStyledAttributes.getDimensionPixelOffset(0, 0);
        int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(1, 0);
        this.mDropDownVerticalOffset = dimensionPixelOffset;
        if (dimensionPixelOffset != 0) {
            this.mDropDownVerticalOffsetSet = true;
        }
        obtainStyledAttributes.recycle();
        AppCompatPopupWindow appCompatPopupWindow = new AppCompatPopupWindow(context, attributeSet, i, i2);
        this.mPopup = appCompatPopupWindow;
        appCompatPopupWindow.setInputMethodMode(1);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PopupScrollListener implements AbsListView.OnScrollListener {
        public PopupScrollListener() {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public final void onScrollStateChanged(AbsListView absListView, int i) {
            if (i == 1) {
                if ((ListPopupWindow.this.mPopup.getInputMethodMode() == 2) || ListPopupWindow.this.mPopup.getContentView() == null) {
                    return;
                }
                ListPopupWindow listPopupWindow = ListPopupWindow.this;
                listPopupWindow.mHandler.removeCallbacks(listPopupWindow.mResizePopupRunnable);
                ListPopupWindow.this.mResizePopupRunnable.run();
            }
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public final void onScroll(AbsListView absListView, int i, int i2, int i3) {
        }
    }
}
