package androidx.core.view;

import android.graphics.Rect;
import android.graphics.Region;
import android.util.ArrayMap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SeslTouchTargetDelegate extends TouchDelegate {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final View mAnchorView;
    public final HashSet mTouchDelegateSet;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public final View mAnchorView;
        public final Queue mQueue = new LinkedList();

        public Builder(View view) {
            this.mAnchorView = view;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CapturedTouchDelegate extends TouchDelegate {
        public final View mView;

        public CapturedTouchDelegate(Rect rect, View view) {
            super(rect, view);
            this.mView = view;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ExtraInsets {
        public static final ExtraInsets NONE = new ExtraInsets(0, 0, 0, 0);
        public final int bottom;
        public final int left;
        public final int right;
        public final int top;

        private ExtraInsets(int i, int i2, int i3, int i4) {
            this.left = i;
            this.top = i2;
            this.right = i3;
            this.bottom = i4;
        }

        /* renamed from: of */
        public static ExtraInsets m33of(int i, int i2, int i3, int i4) {
            return (i == 0 && i2 == 0 && i3 == 0 && i4 == 0) ? NONE : new ExtraInsets(i, i2, i3, i4);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || ExtraInsets.class != obj.getClass()) {
                return false;
            }
            ExtraInsets extraInsets = (ExtraInsets) obj;
            return this.bottom == extraInsets.bottom && this.left == extraInsets.left && this.right == extraInsets.right && this.top == extraInsets.top;
        }

        public final int hashCode() {
            return (((((this.left * 31) + this.top) * 31) + this.right) * 31) + this.bottom;
        }

        public final String toString() {
            return "ExtraInsets{left=" + this.left + ", top=" + this.top + ", right=" + this.right + ", bottom=" + this.bottom + '}';
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class InvalidDelegateViewException extends RuntimeException {
        public InvalidDelegateViewException() {
            super("TouchTargetDelegate's delegateView must be child of anchorView");
        }
    }

    public SeslTouchTargetDelegate(View view) {
        super(new Rect(), view);
        this.mTouchDelegateSet = new HashSet();
        this.mAnchorView = view;
    }

    public static Rect calculateViewBounds(View view, View view2) {
        Rect rect = new Rect(0, 0, view2.getWidth(), view2.getHeight());
        Rect rect2 = new Rect();
        while (!view2.equals(view)) {
            view2.getHitRect(rect2);
            rect.left += rect2.left;
            rect.right += rect2.left;
            rect.top += rect2.top;
            rect.bottom += rect2.top;
            Object parent = view2.getParent();
            if (!(parent instanceof View)) {
                break;
            }
            view2 = (View) parent;
        }
        if (view2.equals(view)) {
            return rect;
        }
        throw new InvalidDelegateViewException();
    }

    public final void addTouchDelegate(View view, ExtraInsets extraInsets) {
        try {
            Rect calculateViewBounds = calculateViewBounds(this.mAnchorView, view);
            if (extraInsets != null) {
                calculateViewBounds.left -= extraInsets.left;
                calculateViewBounds.top -= extraInsets.top;
                calculateViewBounds.right += extraInsets.right;
                calculateViewBounds.bottom += extraInsets.bottom;
            }
            this.mTouchDelegateSet.add(new CapturedTouchDelegate(calculateViewBounds, view));
        } catch (InvalidDelegateViewException e) {
            Log.w("SeslTouchTargetDelegate", "delegateView must be child of anchorView");
            e.printStackTrace();
        }
    }

    @Override // android.view.TouchDelegate
    public final AccessibilityNodeInfo.TouchDelegateInfo getTouchDelegateInfo() {
        Log.i("SeslTouchTargetDelegate", "SeslTouchTargetDelegate does not support accessibility because it cannot support multi-touch delegation with AOSP View");
        ArrayMap arrayMap = new ArrayMap(1);
        arrayMap.put(new Region(), this.mAnchorView);
        return new AccessibilityNodeInfo.TouchDelegateInfo(arrayMap);
    }

    @Override // android.view.TouchDelegate
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        Iterator it = this.mTouchDelegateSet.iterator();
        while (it.hasNext()) {
            CapturedTouchDelegate capturedTouchDelegate = (CapturedTouchDelegate) it.next();
            if (capturedTouchDelegate.mView.getParent() == null) {
                Log.w("SeslTouchTargetDelegate", "delegate view(" + capturedTouchDelegate.mView + ")'s getParent() is null");
            } else if (capturedTouchDelegate.onTouchEvent(motionEvent)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.TouchDelegate
    public final boolean onTouchExplorationHoverEvent(MotionEvent motionEvent) {
        Log.i("SeslTouchTargetDelegate", "SeslTouchTargetDelegate does not support accessibility because it cannot support multi-touch delegation with AOSP View");
        return false;
    }
}
