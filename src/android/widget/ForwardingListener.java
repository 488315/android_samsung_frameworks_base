package android.widget;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import com.android.internal.view.menu.ShowableListMenu;

/* loaded from: classes5.dex */
public abstract class ForwardingListener implements View.OnTouchListener, View.OnAttachStateChangeListener {
    private int mActivePointerId;
    private Runnable mDisallowIntercept;
    private boolean mForwarding;
    private final int mLongPressTimeout;
    private final float mScaledTouchSlop;
    private final View mSrc;
    private final int mTapTimeout;
    private Runnable mTriggerLongPress;

    public abstract ShowableListMenu getPopup();

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view) {
    }

    public ForwardingListener(View view) {
        this.mSrc = view;
        view.setLongClickable(true);
        view.addOnAttachStateChangeListener(this);
        this.mScaledTouchSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        int tapTimeout = ViewConfiguration.getTapTimeout();
        this.mTapTimeout = tapTimeout;
        this.mLongPressTimeout = (tapTimeout + ViewConfiguration.getLongPressTimeout()) / 2;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean z;
        boolean z2 = this.mForwarding;
        if (z2) {
            z = onTouchForwarded(motionEvent) || !onForwardingStopped();
        } else {
            z = onTouchObserved(motionEvent) && onForwardingStarted();
            if (z) {
                long uptimeMillis = SystemClock.uptimeMillis();
                MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                this.mSrc.onTouchEvent(obtain);
                obtain.recycle();
            }
        }
        this.mForwarding = z;
        return z || z2;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view) {
        this.mForwarding = false;
        this.mActivePointerId = -1;
        Runnable runnable = this.mDisallowIntercept;
        if (runnable != null) {
            this.mSrc.removeCallbacks(runnable);
        }
    }

    protected boolean onForwardingStarted() {
        ShowableListMenu popup = getPopup();
        if (popup == null || popup.isShowing()) {
            return true;
        }
        popup.show();
        return true;
    }

    protected boolean onForwardingStopped() {
        ShowableListMenu popup = getPopup();
        if (popup == null || !popup.isShowing()) {
            return true;
        }
        popup.dismiss();
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0017, code lost:
    
        if (r1 != 3) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean onTouchObserved(android.view.MotionEvent r6) {
        /*
            r5 = this;
            android.view.View r0 = r5.mSrc
            boolean r1 = r0.isEnabled()
            r2 = 0
            if (r1 != 0) goto La
            return r2
        La:
            int r1 = r6.getActionMasked()
            if (r1 == 0) goto L41
            r3 = 1
            if (r1 == r3) goto L3d
            r4 = 2
            if (r1 == r4) goto L1a
            r6 = 3
            if (r1 == r6) goto L3d
            goto L6e
        L1a:
            int r1 = r5.mActivePointerId
            int r1 = r6.findPointerIndex(r1)
            if (r1 < 0) goto L6e
            float r4 = r6.getX(r1)
            float r6 = r6.getY(r1)
            float r1 = r5.mScaledTouchSlop
            boolean r6 = r0.pointInView(r4, r6, r1)
            if (r6 != 0) goto L6e
            r5.clearCallbacks()
            android.view.ViewParent r5 = r0.getParent()
            r5.requestDisallowInterceptTouchEvent(r3)
            return r3
        L3d:
            r5.clearCallbacks()
            goto L6e
        L41:
            int r6 = r6.getPointerId(r2)
            r5.mActivePointerId = r6
            java.lang.Runnable r6 = r5.mDisallowIntercept
            r1 = 0
            if (r6 != 0) goto L53
            android.widget.ForwardingListener$DisallowIntercept r6 = new android.widget.ForwardingListener$DisallowIntercept
            r6.<init>()
            r5.mDisallowIntercept = r6
        L53:
            java.lang.Runnable r6 = r5.mDisallowIntercept
            int r3 = r5.mTapTimeout
            long r3 = (long) r3
            r0.postDelayed(r6, r3)
            java.lang.Runnable r6 = r5.mTriggerLongPress
            if (r6 != 0) goto L66
            android.widget.ForwardingListener$TriggerLongPress r6 = new android.widget.ForwardingListener$TriggerLongPress
            r6.<init>()
            r5.mTriggerLongPress = r6
        L66:
            java.lang.Runnable r6 = r5.mTriggerLongPress
            int r5 = r5.mLongPressTimeout
            long r3 = (long) r5
            r0.postDelayed(r6, r3)
        L6e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.ForwardingListener.onTouchObserved(android.view.MotionEvent):boolean");
    }

    private void clearCallbacks() {
        Runnable runnable = this.mTriggerLongPress;
        if (runnable != null) {
            this.mSrc.removeCallbacks(runnable);
        }
        Runnable runnable2 = this.mDisallowIntercept;
        if (runnable2 != null) {
            this.mSrc.removeCallbacks(runnable2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLongPress() {
        clearCallbacks();
        View view = this.mSrc;
        if (view.isEnabled() && !view.isLongClickable() && onForwardingStarted()) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            long uptimeMillis = SystemClock.uptimeMillis();
            MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
            view.onTouchEvent(obtain);
            obtain.recycle();
            this.mForwarding = true;
        }
    }

    private boolean onTouchForwarded(MotionEvent motionEvent) {
        DropDownListView dropDownListView;
        View view = this.mSrc;
        ShowableListMenu popup = getPopup();
        if (popup != null && popup.isShowing() && (dropDownListView = (DropDownListView) popup.getListView()) != null && dropDownListView.isShown()) {
            MotionEvent obtainNoHistory = MotionEvent.obtainNoHistory(motionEvent);
            view.toGlobalMotionEvent(obtainNoHistory);
            dropDownListView.toLocalMotionEvent(obtainNoHistory);
            boolean onForwardedEvent = dropDownListView.onForwardedEvent(obtainNoHistory, this.mActivePointerId);
            obtainNoHistory.recycle();
            int actionMasked = motionEvent.getActionMasked();
            boolean z = (actionMasked == 1 || actionMasked == 3) ? false : true;
            if (onForwardedEvent && z) {
                return true;
            }
        }
        return false;
    }

    private class DisallowIntercept implements Runnable {
        private DisallowIntercept() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ViewParent parent = ForwardingListener.this.mSrc.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }
    }

    private class TriggerLongPress implements Runnable {
        private TriggerLongPress() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ForwardingListener.this.onLongPress();
        }
    }
}
