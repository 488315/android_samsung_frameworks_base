package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.graphics.drawable.DrawableWrapperCompat;
import androidx.core.widget.ListViewAutoScrollHelper;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.widget.SeslAdapterViewReflector;
import com.android.systemui.R;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.lang.reflect.Field;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class DropDownListView extends ListView {
    public boolean mDrawsInPressedState;
    public final boolean mHijackFocus;
    public boolean mListSelectionHidden;
    public int mMotionPosition;
    public ResolveHoverRunnable mResolveHoverRunnable;
    public ListViewAutoScrollHelper mScrollHelper;
    public int mSelectionBottomPadding;
    public int mSelectionLeftPadding;
    public int mSelectionRightPadding;
    public int mSelectionTopPadding;
    public GateKeeperDrawable mSelector;
    public final Rect mSelectorRect;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class GateKeeperDrawable extends DrawableWrapperCompat {
        public boolean mEnabled;

        public GateKeeperDrawable(Drawable drawable) {
            super(drawable);
            this.mEnabled = true;
        }

        @Override // androidx.appcompat.graphics.drawable.DrawableWrapperCompat, android.graphics.drawable.Drawable
        public final void draw(Canvas canvas) {
            if (this.mEnabled) {
                super.draw(canvas);
            }
        }

        @Override // androidx.appcompat.graphics.drawable.DrawableWrapperCompat, android.graphics.drawable.Drawable
        public final void setHotspot(float f, float f2) {
            if (this.mEnabled) {
                super.setHotspot(f, f2);
            }
        }

        @Override // androidx.appcompat.graphics.drawable.DrawableWrapperCompat, android.graphics.drawable.Drawable
        public final void setHotspotBounds(int i, int i2, int i3, int i4) {
            if (this.mEnabled) {
                super.setHotspotBounds(i, i2, i3, i4);
            }
        }

        @Override // androidx.appcompat.graphics.drawable.DrawableWrapperCompat, android.graphics.drawable.Drawable
        public final boolean setState(int[] iArr) {
            if (this.mEnabled) {
                return super.setState(iArr);
            }
            return false;
        }

        @Override // androidx.appcompat.graphics.drawable.DrawableWrapperCompat, android.graphics.drawable.Drawable
        public final boolean setVisible(boolean z, boolean z2) {
            if (this.mEnabled) {
                return super.setVisible(z, z2);
            }
            return false;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ResolveHoverRunnable implements Runnable {
        public ResolveHoverRunnable() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            DropDownListView dropDownListView = DropDownListView.this;
            dropDownListView.mResolveHoverRunnable = null;
            dropDownListView.drawableStateChanged();
        }
    }

    public DropDownListView(Context context, boolean z) {
        super(context, null, R.attr.dropDownListViewStyle);
        this.mSelectorRect = new Rect();
        this.mSelectionLeftPadding = 0;
        this.mSelectionTopPadding = 0;
        this.mSelectionRightPadding = 0;
        this.mSelectionBottomPadding = 0;
        this.mHijackFocus = z;
        setCacheColorHint(0);
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        Drawable selector;
        if (!this.mSelectorRect.isEmpty() && (selector = getSelector()) != null) {
            selector.setBounds(this.mSelectorRect);
            selector.draw(canvas);
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        if (this.mResolveHoverRunnable != null) {
            return;
        }
        super.drawableStateChanged();
        GateKeeperDrawable gateKeeperDrawable = this.mSelector;
        if (gateKeeperDrawable != null) {
            gateKeeperDrawable.mEnabled = true;
        }
        Drawable selector = getSelector();
        if (selector != null && this.mDrawsInPressedState && isPressed()) {
            selector.setState(getDrawableState());
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean hasFocus() {
        return this.mHijackFocus || super.hasFocus();
    }

    @Override // android.view.View
    public final boolean hasWindowFocus() {
        return this.mHijackFocus || super.hasWindowFocus();
    }

    @Override // android.view.View
    public final boolean isFocused() {
        return this.mHijackFocus || super.isFocused();
    }

    @Override // android.view.View
    public final boolean isInTouchMode() {
        return (this.mHijackFocus && this.mListSelectionHidden) || super.isInTouchMode();
    }

    public final int measureHeightOfChildrenCompat(int i, int i2) {
        int listPaddingTop = getListPaddingTop();
        int listPaddingBottom = getListPaddingBottom();
        int dividerHeight = getDividerHeight();
        Drawable divider = getDivider();
        ListAdapter adapter = getAdapter();
        int i3 = listPaddingTop + listPaddingBottom;
        if (adapter == null) {
            return i3;
        }
        if (dividerHeight <= 0 || divider == null) {
            dividerHeight = 0;
        }
        int count = adapter.getCount();
        int i4 = 0;
        View view = null;
        for (int i5 = 0; i5 < count; i5++) {
            int itemViewType = adapter.getItemViewType(i5);
            if (itemViewType != i4) {
                view = null;
                i4 = itemViewType;
            }
            view = adapter.getView(i5, view, this);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = generateDefaultLayoutParams();
                view.setLayoutParams(layoutParams);
            }
            int i6 = layoutParams.height;
            view.measure(i, i6 > 0 ? View.MeasureSpec.makeMeasureSpec(i6, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) : View.MeasureSpec.makeMeasureSpec(0, 0));
            view.forceLayout();
            if (i5 > 0) {
                i3 += dividerHeight;
            }
            i3 += view.getMeasuredHeight();
            if (i3 >= i2) {
                return i2;
            }
        }
        return i3;
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        this.mResolveHoverRunnable = null;
        super.onDetachedFromWindow();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x011a A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onForwardedEvent(MotionEvent motionEvent, int i) {
        boolean z;
        View childAt;
        View childAt2;
        int actionMasked = motionEvent.getActionMasked();
        boolean z2 = false;
        if (actionMasked == 1) {
            z = false;
        } else {
            if (actionMasked != 2) {
                if (actionMasked != 3) {
                    z = true;
                    if (z || z2) {
                        this.mDrawsInPressedState = false;
                        setPressed(false);
                        drawableStateChanged();
                        childAt2 = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
                        if (childAt2 != null) {
                            childAt2.setPressed(false);
                        }
                    }
                    if (z) {
                        ListViewAutoScrollHelper listViewAutoScrollHelper = this.mScrollHelper;
                        if (listViewAutoScrollHelper != null) {
                            if (listViewAutoScrollHelper.mEnabled) {
                                listViewAutoScrollHelper.requestStop();
                            }
                            listViewAutoScrollHelper.mEnabled = false;
                        }
                    } else {
                        if (this.mScrollHelper == null) {
                            this.mScrollHelper = new ListViewAutoScrollHelper(this);
                        }
                        ListViewAutoScrollHelper listViewAutoScrollHelper2 = this.mScrollHelper;
                        boolean z3 = listViewAutoScrollHelper2.mEnabled;
                        listViewAutoScrollHelper2.mEnabled = true;
                        listViewAutoScrollHelper2.onTouch(this, motionEvent);
                    }
                    return z;
                }
                z = false;
                if (z) {
                }
                this.mDrawsInPressedState = false;
                setPressed(false);
                drawableStateChanged();
                childAt2 = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
                if (childAt2 != null) {
                }
                if (z) {
                }
                return z;
            }
            z = true;
        }
        int findPointerIndex = motionEvent.findPointerIndex(i);
        if (findPointerIndex >= 0) {
            int x = (int) motionEvent.getX(findPointerIndex);
            int y = (int) motionEvent.getY(findPointerIndex);
            int pointToPosition = pointToPosition(x, y);
            if (pointToPosition == -1) {
                z2 = true;
            } else {
                View childAt3 = getChildAt(pointToPosition - getFirstVisiblePosition());
                float f = x;
                float f2 = y;
                this.mDrawsInPressedState = true;
                drawableHotspotChanged(f, f2);
                if (!isPressed()) {
                    setPressed(true);
                }
                layoutChildren();
                int i2 = this.mMotionPosition;
                if (i2 != -1 && (childAt = getChildAt(i2 - getFirstVisiblePosition())) != null && childAt != childAt3 && childAt.isPressed()) {
                    childAt.setPressed(false);
                }
                this.mMotionPosition = pointToPosition;
                childAt3.drawableHotspotChanged(f - childAt3.getLeft(), f2 - childAt3.getTop());
                if (!childAt3.isPressed()) {
                    childAt3.setPressed(true);
                }
                Drawable selector = getSelector();
                boolean z4 = (selector == null || pointToPosition == -1) ? false : true;
                if (z4) {
                    selector.setVisible(false, false);
                }
                Rect rect = this.mSelectorRect;
                rect.set(childAt3.getLeft(), childAt3.getTop(), childAt3.getRight(), childAt3.getBottom());
                rect.left -= this.mSelectionLeftPadding;
                rect.top -= this.mSelectionTopPadding;
                rect.right += this.mSelectionRightPadding;
                rect.bottom += this.mSelectionBottomPadding;
                boolean isSelectedChildViewEnabled = isSelectedChildViewEnabled();
                if (childAt3.isEnabled() != isSelectedChildViewEnabled) {
                    setSelectedChildViewEnabled(!isSelectedChildViewEnabled);
                    if (pointToPosition != -1) {
                        refreshDrawableState();
                    }
                }
                if (z4) {
                    Rect rect2 = this.mSelectorRect;
                    float exactCenterX = rect2.exactCenterX();
                    float exactCenterY = rect2.exactCenterY();
                    selector.setVisible(getVisibility() == 0, false);
                    selector.setHotspot(exactCenterX, exactCenterY);
                }
                Drawable selector2 = getSelector();
                if (selector2 != null && pointToPosition != -1) {
                    selector2.setHotspot(f, f2);
                }
                GateKeeperDrawable gateKeeperDrawable = this.mSelector;
                if (gateKeeperDrawable != null) {
                    gateKeeperDrawable.mEnabled = false;
                }
                refreshDrawableState();
                if (actionMasked == 1) {
                    performItemClick(childAt3, pointToPosition, getItemIdAtPosition(pointToPosition));
                }
                z = true;
                z2 = false;
            }
            if (z) {
            }
            this.mDrawsInPressedState = false;
            setPressed(false);
            drawableStateChanged();
            childAt2 = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
            if (childAt2 != null) {
            }
            if (z) {
            }
            return z;
        }
        z = false;
        if (z) {
        }
        this.mDrawsInPressedState = false;
        setPressed(false);
        drawableStateChanged();
        childAt2 = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
        if (childAt2 != null) {
        }
        if (z) {
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0064  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onHoverEvent(MotionEvent motionEvent) {
        int i;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 10 && this.mResolveHoverRunnable == null) {
            ResolveHoverRunnable resolveHoverRunnable = new ResolveHoverRunnable();
            this.mResolveHoverRunnable = resolveHoverRunnable;
            DropDownListView.this.post(resolveHoverRunnable);
        }
        boolean onHoverEvent = super.onHoverEvent(motionEvent);
        if (actionMasked == 9 || actionMasked == 7) {
            int pointToPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
            Field declaredField = SeslBaseReflector.getDeclaredField(SeslAdapterViewReflector.mClass, "mSelectedPosition");
            if (declaredField != null) {
                Object obj = SeslBaseReflector.get(declaredField, this);
                if (obj instanceof Integer) {
                    i = ((Integer) obj).intValue();
                    if (pointToPosition != -1 && pointToPosition != i) {
                        if (getChildAt(pointToPosition - getFirstVisiblePosition()).isEnabled()) {
                            requestFocus();
                            if (!isHovered()) {
                                setHovered(true);
                            }
                        }
                        drawableStateChanged();
                    }
                }
            }
            i = -1;
            if (pointToPosition != -1) {
                if (getChildAt(pointToPosition - getFirstVisiblePosition()).isEnabled()) {
                }
                drawableStateChanged();
            }
        } else {
            setSelection(-1);
        }
        return onHoverEvent;
    }

    @Override // android.widget.AbsListView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.mMotionPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
        }
        ResolveHoverRunnable resolveHoverRunnable = this.mResolveHoverRunnable;
        if (resolveHoverRunnable != null) {
            DropDownListView dropDownListView = DropDownListView.this;
            dropDownListView.mResolveHoverRunnable = null;
            dropDownListView.removeCallbacks(resolveHoverRunnable);
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.widget.AbsListView
    public final void setSelector(Drawable drawable) {
        GateKeeperDrawable gateKeeperDrawable = drawable != null ? new GateKeeperDrawable(drawable) : null;
        this.mSelector = gateKeeperDrawable;
        super.setSelector(gateKeeperDrawable);
        Rect rect = new Rect();
        if (drawable != null) {
            drawable.getPadding(rect);
        }
        this.mSelectionLeftPadding = rect.left;
        this.mSelectionTopPadding = rect.top;
        this.mSelectionRightPadding = rect.right;
        this.mSelectionBottomPadding = rect.bottom;
    }
}
