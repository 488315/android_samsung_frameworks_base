package com.android.internal.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.IntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;

/* loaded from: classes6.dex */
public abstract class ExploreByTouchHelper extends View.AccessibilityDelegate {
    public static final int HOST_ID = -1;
    public static final int INVALID_ID = Integer.MIN_VALUE;
    private final Context mContext;
    private int mFocusedVirtualViewId = Integer.MIN_VALUE;
    private int mHoveredVirtualViewId = Integer.MIN_VALUE;
    private final AccessibilityManager mManager;
    private ExploreByTouchNodeProvider mNodeProvider;
    private IntArray mTempArray;
    private int[] mTempGlobalRect;
    private Rect mTempParentRect;
    private Rect mTempScreenRect;
    private Rect mTempVisibleRect;
    private final View mView;
    private static final String DEFAULT_CLASS_NAME = View.class.getName();
    private static final Rect INVALID_PARENT_BOUNDS = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

    protected abstract int getVirtualViewAt(float f, float f2);

    protected abstract void getVisibleVirtualViews(IntArray intArray);

    protected abstract boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle);

    protected void onPopulateEventForHost(AccessibilityEvent accessibilityEvent) {
    }

    protected abstract void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent);

    protected void onPopulateNodeForHost(AccessibilityNodeInfo accessibilityNodeInfo) {
    }

    protected abstract void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfo accessibilityNodeInfo);

    public ExploreByTouchHelper(View view) {
        if (view == null) {
            throw new IllegalArgumentException("View may not be null");
        }
        this.mView = view;
        Context context = view.getContext();
        this.mContext = context;
        this.mManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    @Override // android.view.View.AccessibilityDelegate
    public AccessibilityNodeProvider getAccessibilityNodeProvider(View view) {
        if (this.mNodeProvider == null) {
            this.mNodeProvider = new ExploreByTouchNodeProvider();
        }
        return this.mNodeProvider;
    }

    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        if (this.mManager.isEnabled() && this.mManager.isTouchExplorationEnabled()) {
            int action = motionEvent.getAction();
            if (action == 7 || action == 9) {
                int virtualViewAt = getVirtualViewAt(motionEvent.getX(), motionEvent.getY());
                updateHoveredVirtualView(virtualViewAt);
                if (virtualViewAt != Integer.MIN_VALUE) {
                    return true;
                }
            } else {
                if (action != 10 || this.mHoveredVirtualViewId == Integer.MIN_VALUE) {
                    return false;
                }
                updateHoveredVirtualView(Integer.MIN_VALUE);
                return true;
            }
        }
        return false;
    }

    public boolean sendEventForVirtualView(int i, int i2) {
        ViewParent parent;
        if (i == Integer.MIN_VALUE || !this.mManager.isEnabled() || (parent = this.mView.getParent()) == null) {
            return false;
        }
        return parent.requestSendAccessibilityEvent(this.mView, createEvent(i, i2));
    }

    public void invalidateRoot() {
        invalidateVirtualView(-1, 1);
    }

    public void invalidateVirtualView(int i) {
        invalidateVirtualView(i, 0);
    }

    public void invalidateVirtualView(int i, int i2) {
        ViewParent parent;
        if (i == Integer.MIN_VALUE || !this.mManager.isEnabled() || (parent = this.mView.getParent()) == null) {
            return;
        }
        AccessibilityEvent createEvent = createEvent(i, 2048);
        createEvent.setContentChangeTypes(i2);
        parent.requestSendAccessibilityEvent(this.mView, createEvent);
    }

    public int getFocusedVirtualView() {
        return this.mFocusedVirtualViewId;
    }

    private void updateHoveredVirtualView(int i) {
        int i2 = this.mHoveredVirtualViewId;
        if (i2 == i) {
            return;
        }
        this.mHoveredVirtualViewId = i;
        sendEventForVirtualView(i, 128);
        sendEventForVirtualView(i2, 256);
    }

    private AccessibilityEvent createEvent(int i, int i2) {
        if (i == -1) {
            return createEventForHost(i2);
        }
        return createEventForChild(i, i2);
    }

    private AccessibilityEvent createEventForHost(int i) {
        AccessibilityEvent obtain = AccessibilityEvent.obtain(i);
        this.mView.onInitializeAccessibilityEvent(obtain);
        onPopulateEventForHost(obtain);
        return obtain;
    }

    private AccessibilityEvent createEventForChild(int i, int i2) {
        AccessibilityEvent obtain = AccessibilityEvent.obtain(i2);
        obtain.setEnabled(true);
        obtain.setClassName(DEFAULT_CLASS_NAME);
        onPopulateEventForVirtualView(i, obtain);
        if (obtain.getText().isEmpty() && obtain.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
        }
        obtain.setPackageName(this.mView.getContext().getPackageName());
        obtain.setSource(this.mView, i);
        return obtain;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AccessibilityNodeInfo createNode(int i) {
        if (i == -1) {
            return createNodeForHost();
        }
        return createNodeForChild(i);
    }

    private AccessibilityNodeInfo createNodeForHost() {
        AccessibilityNodeInfo obtain = AccessibilityNodeInfo.obtain(this.mView);
        this.mView.onInitializeAccessibilityNodeInfo(obtain);
        int childCount = obtain.getChildCount();
        onPopulateNodeForHost(obtain);
        IntArray intArray = this.mTempArray;
        if (intArray == null) {
            this.mTempArray = new IntArray();
        } else {
            intArray.clear();
        }
        IntArray intArray2 = this.mTempArray;
        getVisibleVirtualViews(intArray2);
        if (childCount > 0 && intArray2.size() > 0) {
            throw new RuntimeException("Views cannot have both real and virtual children");
        }
        int size = intArray2.size();
        for (int i = 0; i < size; i++) {
            obtain.addChild(this.mView, intArray2.get(i));
        }
        return obtain;
    }

    private AccessibilityNodeInfo createNodeForChild(int i) {
        ensureTempRects();
        Rect rect = this.mTempParentRect;
        int[] iArr = this.mTempGlobalRect;
        Rect rect2 = this.mTempScreenRect;
        AccessibilityNodeInfo obtain = AccessibilityNodeInfo.obtain();
        obtain.setEnabled(true);
        obtain.setClassName(DEFAULT_CLASS_NAME);
        Rect rect3 = INVALID_PARENT_BOUNDS;
        obtain.setBoundsInParent(rect3);
        onPopulateNodeForVirtualView(i, obtain);
        if (obtain.getText() == null && obtain.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        obtain.getBoundsInParent(rect);
        if (rect.equals(rect3)) {
            throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
        }
        int actions = obtain.getActions();
        if ((actions & 64) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        if ((actions & 128) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        obtain.setPackageName(this.mView.getContext().getPackageName());
        obtain.setSource(this.mView, i);
        obtain.setParent(this.mView);
        if (this.mFocusedVirtualViewId == i) {
            obtain.setAccessibilityFocused(true);
            obtain.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS);
        } else {
            obtain.setAccessibilityFocused(false);
            obtain.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_ACCESSIBILITY_FOCUS);
        }
        if (intersectVisibleToUser(rect)) {
            obtain.setVisibleToUser(true);
            obtain.setBoundsInParent(rect);
        }
        this.mView.getLocationOnScreen(iArr);
        int i2 = iArr[0];
        int i3 = iArr[1];
        rect2.set(rect);
        rect2.offset(i2, i3);
        obtain.setBoundsInScreen(rect2);
        return obtain;
    }

    private void ensureTempRects() {
        this.mTempGlobalRect = new int[2];
        this.mTempParentRect = new Rect();
        this.mTempScreenRect = new Rect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean performAction(int i, int i2, Bundle bundle) {
        if (i == -1) {
            return performActionForHost(i2, bundle);
        }
        return performActionForChild(i, i2, bundle);
    }

    private boolean performActionForHost(int i, Bundle bundle) {
        return this.mView.performAccessibilityAction(i, bundle);
    }

    private boolean performActionForChild(int i, int i2, Bundle bundle) {
        if (i2 == 64 || i2 == 128) {
            return manageFocusForChild(i, i2);
        }
        return onPerformActionForVirtualView(i, i2, bundle);
    }

    private boolean manageFocusForChild(int i, int i2) {
        if (i2 == 64) {
            return requestAccessibilityFocus(i);
        }
        if (i2 != 128) {
            return false;
        }
        return clearAccessibilityFocus(i);
    }

    private boolean intersectVisibleToUser(Rect rect) {
        if (rect == null || rect.isEmpty() || this.mView.getWindowVisibility() != 0) {
            return false;
        }
        Object parent = this.mView.getParent();
        while (parent instanceof View) {
            View view = (View) parent;
            if (view.getAlpha() <= 0.0f || view.getVisibility() != 0) {
                return false;
            }
            parent = view.getParent();
        }
        if (parent == null) {
            return false;
        }
        if (this.mTempVisibleRect == null) {
            this.mTempVisibleRect = new Rect();
        }
        Rect rect2 = this.mTempVisibleRect;
        if (this.mView.getLocalVisibleRect(rect2)) {
            return rect.intersect(rect2);
        }
        return false;
    }

    private boolean isAccessibilityFocused(int i) {
        return this.mFocusedVirtualViewId == i;
    }

    private boolean requestAccessibilityFocus(int i) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.mContext.getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (!this.mManager.isEnabled() || !accessibilityManager.isTouchExplorationEnabled() || isAccessibilityFocused(i)) {
            return false;
        }
        int i2 = this.mFocusedVirtualViewId;
        if (i2 != Integer.MIN_VALUE) {
            sendEventForVirtualView(i2, 65536);
        }
        this.mFocusedVirtualViewId = i;
        this.mView.invalidate();
        sendEventForVirtualView(i, 32768);
        return true;
    }

    private boolean clearAccessibilityFocus(int i) {
        if (!isAccessibilityFocused(i)) {
            return false;
        }
        this.mFocusedVirtualViewId = Integer.MIN_VALUE;
        this.mView.invalidate();
        sendEventForVirtualView(i, 65536);
        return true;
    }

    private class ExploreByTouchNodeProvider extends AccessibilityNodeProvider {
        private ExploreByTouchNodeProvider() {
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
            return ExploreByTouchHelper.this.createNode(i);
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public boolean performAction(int i, int i2, Bundle bundle) {
            return ExploreByTouchHelper.this.performAction(i, i2, bundle);
        }
    }
}
