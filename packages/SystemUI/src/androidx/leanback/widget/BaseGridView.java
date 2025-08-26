package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class BaseGridView extends RecyclerView {
    public final boolean mHasOverlappingRendering;
    public final int mInitialPrefetchItemCount;
    public GridLayoutManager mLayoutManager;
    public int mPrivateFlag;

    /* renamed from: androidx.leanback.widget.BaseGridView$1, reason: invalid class name */
    public class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

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
        this.mItemAnimator.mSupportsChangeAnimations = false;
        ((ArrayList) this.mRecyclerListeners).add(new AnonymousClass1());
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchGenericFocusedEvent(MotionEvent motionEvent) {
        return super.dispatchGenericFocusedEvent(motionEvent);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.View
    public final View focusSearch(int i) {
        if (isFocused()) {
            GridLayoutManager gridLayoutManager = this.mLayoutManager;
            View viewFindViewByPosition = gridLayoutManager.findViewByPosition(gridLayoutManager.mFocusPosition);
            if (viewFindViewByPosition != null) {
                return focusSearch(viewFindViewByPosition, i);
            }
        }
        return super.focusSearch(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public final int getChildDrawingOrder(int i, int i2) {
        int iIndexOfChild;
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        View viewFindViewByPosition = gridLayoutManager.findViewByPosition(gridLayoutManager.mFocusPosition);
        return (viewFindViewByPosition != null && i2 >= (iIndexOfChild = indexOfChild(viewFindViewByPosition))) ? i2 < i + (-1) ? ((iIndexOfChild + i) - 1) - i2 : iIndexOfChild : i2;
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return this.mHasOverlappingRendering;
    }

    public final void initBaseGridViewAttributes(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.lbBaseGridView);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(4, false);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(3, false);
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        gridLayoutManager.mFlag = (z ? 2048 : 0) | (gridLayoutManager.mFlag & (-6145)) | (z2 ? 4096 : 0);
        boolean z3 = typedArrayObtainStyledAttributes.getBoolean(6, true);
        boolean z4 = typedArrayObtainStyledAttributes.getBoolean(5, true);
        GridLayoutManager gridLayoutManager2 = this.mLayoutManager;
        gridLayoutManager2.mFlag = (z3 ? 8192 : 0) | (gridLayoutManager2.mFlag & (-24577)) | (z4 ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 0);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(2, typedArrayObtainStyledAttributes.getDimensionPixelSize(8, 0));
        if (gridLayoutManager2.mOrientation == 1) {
            gridLayoutManager2.mVerticalSpacing = dimensionPixelSize;
            gridLayoutManager2.mSpacingPrimary = dimensionPixelSize;
        } else {
            gridLayoutManager2.mVerticalSpacing = dimensionPixelSize;
            gridLayoutManager2.mSpacingSecondary = dimensionPixelSize;
        }
        GridLayoutManager gridLayoutManager3 = this.mLayoutManager;
        int dimensionPixelSize2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(1, typedArrayObtainStyledAttributes.getDimensionPixelSize(7, 0));
        if (gridLayoutManager3.mOrientation == 0) {
            gridLayoutManager3.mSpacingPrimary = dimensionPixelSize2;
        } else {
            gridLayoutManager3.mSpacingSecondary = dimensionPixelSize2;
        }
        if (typedArrayObtainStyledAttributes.hasValue(0)) {
            this.mLayoutManager.mGravity = typedArrayObtainStyledAttributes.getInt(0, 0);
            requestLayout();
        }
        typedArrayObtainStyledAttributes.recycle();
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
            View viewFindViewByPosition = gridLayoutManager.findViewByPosition(i2);
            if (viewFindViewByPosition == null) {
                return;
            }
            if (viewFindViewByPosition.getVisibility() == 0 && viewFindViewByPosition.hasFocusable()) {
                viewFindViewByPosition.requestFocus();
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
        View viewFindViewByPosition = gridLayoutManager.findViewByPosition(gridLayoutManager.mFocusPosition);
        if (viewFindViewByPosition != null) {
            return viewFindViewByPosition.requestFocus(i, rect);
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x000f  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onRtlPropertiesChanged(int i) {
        int i2;
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        if (gridLayoutManager != null) {
            if (gridLayoutManager.mOrientation == 0) {
                i2 = i == 1 ? 262144 : 0;
            } else if (i == 1) {
                i2 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
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
        boolean zHasFocus = getChildAt(i).hasFocus();
        if (zHasFocus) {
            this.mPrivateFlag |= 1;
            requestFocus();
        }
        super.removeViewAt(i);
        if (zHasFocus) {
            this.mPrivateFlag ^= -2;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public final void scrollToPosition(int i) {
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        if ((gridLayoutManager.mFlag & 64) != 0) {
            gridLayoutManager.setSelection(i, false);
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
    public final void smoothScrollBy$1(int i, int i2) {
        smoothScrollBy(i, i2, false);
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public final void smoothScrollToPosition(int i) {
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        if ((gridLayoutManager.mFlag & 64) != 0) {
            gridLayoutManager.setSelection(i, false);
        } else {
            super.smoothScrollToPosition(i);
        }
    }
}
