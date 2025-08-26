package android.view;

import android.graphics.Rect;
import android.graphics.Region;
import android.util.ArrayMap;
import android.view.accessibility.AccessibilityNodeInfo;

/* loaded from: classes4.dex */
public class TouchDelegate {
    public static final int ABOVE = 1;
    public static final int BELOW = 2;
    public static final int TO_LEFT = 4;
    public static final int TO_RIGHT = 8;
    private Rect mBounds;
    private boolean mDelegateTargeted;
    private View mDelegateView;
    private int mSlop;
    private Rect mSlopBounds;
    private AccessibilityNodeInfo.TouchDelegateInfo mTouchDelegateInfo;

    public TouchDelegate(Rect rect, View view) {
        this.mBounds = rect;
        this.mSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        Rect rect2 = new Rect(rect);
        this.mSlopBounds = rect2;
        int i = this.mSlop;
        rect2.inset(-i, -i);
        this.mDelegateView = view;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean zContains;
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            zContains = this.mBounds.contains(x, y);
            this.mDelegateTargeted = zContains;
        } else if (actionMasked == 1 || actionMasked == 2) {
            boolean z = this.mDelegateTargeted;
            zContains = z ? this.mSlopBounds.contains(x, y) : true;
            zContains = z;
        } else if (actionMasked == 3) {
            zContains = this.mDelegateTargeted;
            this.mDelegateTargeted = false;
        } else if (actionMasked != 5 && actionMasked != 6) {
            zContains = false;
        }
        if (!zContains) {
            return false;
        }
        if (zContains) {
            motionEvent.setLocation(this.mDelegateView.getWidth() / 2, this.mDelegateView.getHeight() / 2);
        } else {
            float f = -(this.mSlop * 2);
            motionEvent.setLocation(f, f);
        }
        return this.mDelegateView.dispatchTouchEvent(motionEvent);
    }

    public boolean onTouchExplorationHoverEvent(MotionEvent motionEvent) {
        if (this.mBounds == null) {
            return false;
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        boolean zContains = this.mBounds.contains(x, y);
        int actionMasked = motionEvent.getActionMasked();
        boolean z = true;
        if (actionMasked != 7) {
            if (actionMasked == 9) {
                this.mDelegateTargeted = zContains;
            } else if (actionMasked == 10) {
                this.mDelegateTargeted = true;
            }
        } else if (zContains) {
            this.mDelegateTargeted = true;
        } else if (this.mDelegateTargeted && !this.mSlopBounds.contains(x, y)) {
            z = false;
        }
        if (!this.mDelegateTargeted) {
            return false;
        }
        if (z) {
            motionEvent.setLocation(this.mDelegateView.getWidth() / 2, this.mDelegateView.getHeight() / 2);
        } else {
            this.mDelegateTargeted = false;
        }
        return this.mDelegateView.dispatchHoverEvent(motionEvent);
    }

    public AccessibilityNodeInfo.TouchDelegateInfo getTouchDelegateInfo() {
        if (this.mTouchDelegateInfo == null) {
            ArrayMap arrayMap = new ArrayMap(1);
            Rect rect = this.mBounds;
            if (rect == null) {
                rect = new Rect();
            }
            arrayMap.put(new Region(rect), this.mDelegateView);
            this.mTouchDelegateInfo = new AccessibilityNodeInfo.TouchDelegateInfo(arrayMap);
        }
        return this.mTouchDelegateInfo;
    }
}
