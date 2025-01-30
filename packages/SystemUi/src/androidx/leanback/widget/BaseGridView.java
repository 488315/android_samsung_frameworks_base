package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import androidx.leanback.R$styleable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class BaseGridView extends RecyclerView {
    public final boolean mHasOverlappingRendering;
    public final int mInitialPrefetchItemCount;
    public GridLayoutManager mLayoutManager;
    public int mPrivateFlag;

    public BaseGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mHasOverlappingRendering = true;
        this.mInitialPrefetchItemCount = 4;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this);
        this.mLayoutManager = gridLayoutManager;
        setLayoutManager(gridLayoutManager);
        this.mPreserveFocusAfterLayout = false;
        setDescendantFocusability(262144);
        this.mHasFixedSize = true;
        setChildrenDrawingOrderEnabled(true);
        setWillNotDraw(true);
        setOverScrollMode(2);
        ((SimpleItemAnimator) this.mItemAnimator).mSupportsChangeAnimations = false;
        ((ArrayList) this.mRecyclerListeners).add(new RecyclerView.RecyclerListener() { // from class: androidx.leanback.widget.BaseGridView.1
            @Override // androidx.recyclerview.widget.RecyclerView.RecyclerListener
            public final void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
                GridLayoutManager gridLayoutManager2 = BaseGridView.this.mLayoutManager;
                gridLayoutManager2.getClass();
                if (viewHolder.getAbsoluteAdapterPosition() != -1) {
                    gridLayoutManager2.mChildrenStates.getClass();
                }
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchGenericFocusedEvent(MotionEvent motionEvent) {
        return super.dispatchGenericFocusedEvent(motionEvent);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final View focusSearch(int i) {
        if (isFocused()) {
            GridLayoutManager gridLayoutManager = this.mLayoutManager;
            View findViewByPosition = gridLayoutManager.findViewByPosition(gridLayoutManager.mFocusPosition);
            if (findViewByPosition != null) {
                return focusSearch(findViewByPosition, i);
            }
        }
        return super.focusSearch(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public final int getChildDrawingOrder(int i, int i2) {
        int indexOfChild;
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        View findViewByPosition = gridLayoutManager.findViewByPosition(gridLayoutManager.mFocusPosition);
        if (findViewByPosition == null || i2 < (indexOfChild = indexOfChild(findViewByPosition))) {
            return i2;
        }
        if (i2 < i - 1) {
            indexOfChild = ((indexOfChild + i) - 1) - i2;
        }
        return indexOfChild;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return this.mHasOverlappingRendering;
    }

    public final void initBaseGridViewAttributes(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.lbBaseGridView);
        boolean z = obtainStyledAttributes.getBoolean(4, false);
        boolean z2 = obtainStyledAttributes.getBoolean(3, false);
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        gridLayoutManager.mFlag = (z ? 2048 : 0) | (gridLayoutManager.mFlag & (-6145)) | (z2 ? 4096 : 0);
        boolean z3 = obtainStyledAttributes.getBoolean(6, true);
        boolean z4 = obtainStyledAttributes.getBoolean(5, true);
        GridLayoutManager gridLayoutManager2 = this.mLayoutManager;
        gridLayoutManager2.mFlag = (z3 ? 8192 : 0) | (gridLayoutManager2.mFlag & (-24577)) | (z4 ? 16384 : 0);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(2, obtainStyledAttributes.getDimensionPixelSize(8, 0));
        if (gridLayoutManager2.mOrientation == 1) {
            gridLayoutManager2.mVerticalSpacing = dimensionPixelSize;
            gridLayoutManager2.mSpacingPrimary = dimensionPixelSize;
        } else {
            gridLayoutManager2.mVerticalSpacing = dimensionPixelSize;
            gridLayoutManager2.mSpacingSecondary = dimensionPixelSize;
        }
        GridLayoutManager gridLayoutManager3 = this.mLayoutManager;
        int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(1, obtainStyledAttributes.getDimensionPixelSize(7, 0));
        if (gridLayoutManager3.mOrientation == 0) {
            gridLayoutManager3.mSpacingPrimary = dimensionPixelSize2;
        } else {
            gridLayoutManager3.mSpacingSecondary = dimensionPixelSize2;
        }
        if (obtainStyledAttributes.hasValue(0)) {
            this.mLayoutManager.mGravity = obtainStyledAttributes.getInt(0, 0);
            requestLayout();
        }
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    public final void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        if (!z) {
            gridLayoutManager.getClass();
            return;
        }
        int i2 = gridLayoutManager.mFocusPosition;
        while (true) {
            View findViewByPosition = gridLayoutManager.findViewByPosition(i2);
            if (findViewByPosition == null) {
                return;
            }
            if (findViewByPosition.getVisibility() == 0 && findViewByPosition.hasFocusable()) {
                findViewByPosition.requestFocus();
                return;
            }
            i2++;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public final boolean onRequestFocusInDescendants(int i, Rect rect) {
        if ((this.mPrivateFlag & 1) == 1) {
            return false;
        }
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        View findViewByPosition = gridLayoutManager.findViewByPosition(gridLayoutManager.mFocusPosition);
        if (findViewByPosition != null) {
            return findViewByPosition.requestFocus(i, rect);
        }
        return false;
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
        int i2;
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        if (gridLayoutManager != null) {
            if (gridLayoutManager.mOrientation == 0) {
                if (i == 1) {
                    i2 = 262144;
                }
                i2 = 0;
            } else {
                if (i == 1) {
                    i2 = 524288;
                }
                i2 = 0;
            }
            int i3 = gridLayoutManager.mFlag;
            if ((786432 & i3) == i2) {
                return;
            }
            gridLayoutManager.mFlag = i2 | (i3 & (-786433)) | 256;
            gridLayoutManager.mWindowAlignment.horizontal.mReversedFlow = i == 1;
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public final void removeView(View view) {
        boolean z = view.hasFocus() && isFocusable();
        if (z) {
            this.mPrivateFlag = 1 | this.mPrivateFlag;
            requestFocus();
        }
        super.removeView(view);
        if (z) {
            this.mPrivateFlag ^= -2;
        }
    }

    @Override // android.view.ViewGroup
    public final void removeViewAt(int i) {
        boolean hasFocus = getChildAt(i).hasFocus();
        if (hasFocus) {
            this.mPrivateFlag |= 1;
            requestFocus();
        }
        super.removeViewAt(i);
        if (hasFocus) {
            this.mPrivateFlag ^= -2;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public final void scrollToPosition(int i) {
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        if ((gridLayoutManager.mFlag & 64) != 0) {
            gridLayoutManager.setSelection$1(i, false);
        } else {
            super.scrollToPosition(i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public final void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager != null) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            this.mLayoutManager = gridLayoutManager;
            gridLayoutManager.mBaseGridView = this;
            gridLayoutManager.mGrid = null;
            super.setLayoutManager(layoutManager);
            return;
        }
        super.setLayoutManager(null);
        GridLayoutManager gridLayoutManager2 = this.mLayoutManager;
        if (gridLayoutManager2 != null) {
            gridLayoutManager2.mBaseGridView = null;
            gridLayoutManager2.mGrid = null;
        }
        this.mLayoutManager = null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public final void smoothScrollBy(int i, int i2) {
        smoothScrollBy(i, i2, false);
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public final void smoothScrollBy$2(int i, int i2) {
        smoothScrollBy(i, i2, false);
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public final void smoothScrollToPosition(int i) {
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        if ((gridLayoutManager.mFlag & 64) != 0) {
            gridLayoutManager.setSelection$1(i, false);
        } else {
            super.smoothScrollToPosition(i);
        }
    }
}
