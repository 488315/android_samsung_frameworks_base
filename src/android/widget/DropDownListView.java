package android.widget;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.widget.AutoScrollHelper;

/* loaded from: classes5.dex */
public class DropDownListView extends ListView {
    private boolean mDrawsInPressedState;
    private boolean mHijackFocus;
    boolean mIsAutoCompleteTextPopup;
    private boolean mListSelectionHidden;
    private ResolveHoverRunnable mResolveHoverRunnable;
    private AutoScrollHelper.AbsListViewAutoScroller mScrollHelper;

    public DropDownListView(Context context, boolean z) {
        this(context, z, 16842861);
    }

    public DropDownListView(Context context, boolean z, int i) {
        super(context, null, i);
        this.mIsAutoCompleteTextPopup = false;
        this.mHijackFocus = z;
        setCacheColorHint(0);
    }

    @Override // android.widget.AbsListView
    boolean shouldShowSelector() {
        return isHovered() || super.shouldShowSelector();
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        ResolveHoverRunnable resolveHoverRunnable = this.mResolveHoverRunnable;
        if (resolveHoverRunnable != null) {
            resolveHoverRunnable.cancel();
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 10 && this.mResolveHoverRunnable == null) {
            ResolveHoverRunnable resolveHoverRunnable = new ResolveHoverRunnable();
            this.mResolveHoverRunnable = resolveHoverRunnable;
            resolveHoverRunnable.post();
        }
        boolean onHoverEvent = super.onHoverEvent(motionEvent);
        if (actionMasked == 9 || actionMasked == 7) {
            int pointToPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
            if (pointToPosition != -1 && pointToPosition != this.mSelectedPosition) {
                View childAt = getChildAt(pointToPosition - getFirstVisiblePosition());
                if (childAt.isEnabled()) {
                    requestFocus();
                    AccessibilityManager accessibilityManager = (AccessibilityManager) this.mContext.getSystemService(Context.ACCESSIBILITY_SERVICE);
                    if (!(accessibilityManager != null ? accessibilityManager.semIsScreenReaderEnabled() : false)) {
                        if (!isHovered()) {
                            setHovered(true);
                        }
                        positionSelector(pointToPosition, childAt);
                        setSelectedPositionInt(pointToPosition);
                        setNextSelectedPositionInt(pointToPosition);
                    }
                }
                updateSelectorState();
            }
        } else if (!super.shouldShowSelector()) {
            setSelectedPositionInt(-1);
            setNextSelectedPositionInt(-1);
            return onHoverEvent;
        }
        return onHoverEvent;
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        if (this.mResolveHoverRunnable == null) {
            super.drawableStateChanged();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x000c, code lost:
    
        if (r0 != 3) goto L8;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x004f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x006c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onForwardedEvent(android.view.MotionEvent r9, int r10) {
        /*
            r8 = this;
            int r0 = r9.getActionMasked()
            r1 = 1
            r2 = 0
            if (r0 == r1) goto L16
            r3 = 2
            if (r0 == r3) goto L14
            r10 = 3
            if (r0 == r10) goto L11
        Le:
            r3 = r1
        Lf:
            r10 = r2
            goto L4d
        L11:
            r10 = r2
            r3 = r10
            goto L4d
        L14:
            r3 = r1
            goto L17
        L16:
            r3 = r2
        L17:
            int r10 = r9.findPointerIndex(r10)
            if (r10 >= 0) goto L1e
            goto L11
        L1e:
            float r4 = r9.getX(r10)
            int r4 = (int) r4
            float r10 = r9.getY(r10)
            int r10 = (int) r10
            if (r10 >= 0) goto L2b
            goto Lf
        L2b:
            int r5 = r8.pointToPosition(r4, r10)
            r6 = -1
            if (r5 != r6) goto L34
            r10 = r1
            goto L4d
        L34:
            int r3 = r8.getFirstVisiblePosition()
            int r3 = r5 - r3
            android.view.View r3 = r8.getChildAt(r3)
            float r4 = (float) r4
            float r10 = (float) r10
            r8.setPressedItem(r3, r5, r4, r10)
            if (r0 != r1) goto Le
            long r6 = r8.getItemIdAtPosition(r5)
            r8.performItemClick(r3, r5, r6)
            goto Le
        L4d:
            if (r3 == 0) goto L51
            if (r10 == 0) goto L54
        L51:
            r8.clearPressedItem()
        L54:
            if (r3 == 0) goto L6c
            com.android.internal.widget.AutoScrollHelper$AbsListViewAutoScroller r10 = r8.mScrollHelper
            if (r10 != 0) goto L61
            com.android.internal.widget.AutoScrollHelper$AbsListViewAutoScroller r10 = new com.android.internal.widget.AutoScrollHelper$AbsListViewAutoScroller
            r10.<init>(r8)
            r8.mScrollHelper = r10
        L61:
            com.android.internal.widget.AutoScrollHelper$AbsListViewAutoScroller r10 = r8.mScrollHelper
            r10.setEnabled(r1)
            com.android.internal.widget.AutoScrollHelper$AbsListViewAutoScroller r10 = r8.mScrollHelper
            r10.onTouch(r8, r9)
            return r3
        L6c:
            com.android.internal.widget.AutoScrollHelper$AbsListViewAutoScroller r8 = r8.mScrollHelper
            if (r8 == 0) goto L73
            r8.setEnabled(r2)
        L73:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.DropDownListView.onForwardedEvent(android.view.MotionEvent, int):boolean");
    }

    public void setListSelectionHidden(boolean z) {
        this.mListSelectionHidden = z;
    }

    private void clearPressedItem() {
        this.mDrawsInPressedState = false;
        setPressed(false);
        updateSelectorState();
        View childAt = getChildAt(this.mMotionPosition - this.mFirstPosition);
        if (childAt != null) {
            childAt.setPressed(false);
        }
    }

    private void setPressedItem(View view, int i, float f, float f2) {
        this.mDrawsInPressedState = true;
        drawableHotspotChanged(f, f2);
        if (!isPressed()) {
            setPressed(true);
        }
        if (this.mDataChanged) {
            layoutChildren();
        }
        View childAt = getChildAt(this.mMotionPosition - this.mFirstPosition);
        if (childAt != null && childAt != view && childAt.isPressed()) {
            childAt.setPressed(false);
        }
        this.mMotionPosition = i;
        view.drawableHotspotChanged(f - view.getLeft(), f2 - view.getTop());
        if (!view.isPressed()) {
            view.setPressed(true);
        }
        setSelectedPositionInt(i);
        positionSelectorLikeTouch(i, view, f, f2);
        refreshDrawableState();
    }

    @Override // android.widget.AbsListView
    boolean touchModeDrawsInPressedState() {
        return this.mDrawsInPressedState || super.touchModeDrawsInPressedState();
    }

    @Override // android.widget.AbsListView
    View obtainView(int i, boolean[] zArr) {
        View obtainView = super.obtainView(i, zArr);
        if (obtainView instanceof TextView) {
            ((TextView) obtainView).setHorizontallyScrolling(true);
        }
        return obtainView;
    }

    @Override // android.view.View
    public boolean isInTouchMode() {
        return (this.mHijackFocus && this.mListSelectionHidden) || super.isInTouchMode();
    }

    @Override // android.view.View
    public boolean hasWindowFocus() {
        return this.mHijackFocus || super.hasWindowFocus();
    }

    @Override // android.view.View
    public boolean isFocused() {
        return this.mHijackFocus || super.isFocused();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean hasFocus() {
        return this.mHijackFocus || super.hasFocus();
    }

    private class ResolveHoverRunnable implements Runnable {
        private ResolveHoverRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            DropDownListView.this.mResolveHoverRunnable = null;
            DropDownListView.this.drawableStateChanged();
        }

        public void cancel() {
            DropDownListView.this.mResolveHoverRunnable = null;
            DropDownListView.this.removeCallbacks(this);
        }

        public void post() {
            DropDownListView.this.post(this);
        }
    }
}
