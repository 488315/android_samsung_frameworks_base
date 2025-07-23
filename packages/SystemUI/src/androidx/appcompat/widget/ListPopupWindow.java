package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import androidx.appcompat.R$styleable;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.reflect.os.SeslBuildReflector$SeslVersionReflector;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PopupDataSetObserver extends DataSetObserver {
        public PopupDataSetObserver() {
        }

        @Override // android.database.DataSetObserver
        public final void onChanged() {
            if (ListPopupWindow.this.mPopup.isShowing()) {
                ListPopupWindow.this.show();
            }
        }

        @Override // android.database.DataSetObserver
        public final void onInvalidated() {
            ListPopupWindow.this.dismiss();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ResizePopupRunnable implements Runnable {
        public ResizePopupRunnable() {
        }

        @Override // java.lang.Runnable
        public final void run() {
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
    /* JADX WARN: Removed duplicated region for block: B:166:0x0137  */
    @Override // androidx.appcompat.view.menu.ShowableListMenu
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void show() {
        /*
            Method dump skipped, instructions count: 987
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ListPopupWindow.show():void");
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PopupScrollListener implements AbsListView.OnScrollListener {
        public PopupScrollListener() {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public final void onScrollStateChanged(AbsListView absListView, int i) {
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
