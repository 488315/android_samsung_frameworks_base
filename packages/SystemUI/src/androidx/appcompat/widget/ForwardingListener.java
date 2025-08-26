package androidx.appcompat.widget;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import androidx.appcompat.view.menu.ShowableListMenu;

/* loaded from: classes.dex */
public abstract class ForwardingListener implements View.OnTouchListener, View.OnAttachStateChangeListener {
    public int mActivePointerId;
    public DisallowIntercept mDisallowIntercept;
    public boolean mForwarding;
    public final int mLongPressTimeout;
    public final float mScaledTouchSlop;
    public final View mSrc;
    public final int mTapTimeout;
    public final int[] mTmpLocation = new int[2];
    public TriggerLongPress mTriggerLongPress;

    public class DisallowIntercept implements Runnable {
        public DisallowIntercept() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            ViewParent parent = ForwardingListener.this.mSrc.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }
    }

    public class TriggerLongPress implements Runnable {
        public TriggerLongPress() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            ForwardingListener forwardingListener = ForwardingListener.this;
            forwardingListener.clearCallbacks();
            View view = forwardingListener.mSrc;
            if (view.isEnabled() && !view.isLongClickable() && forwardingListener.onForwardingStarted()) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                long jUptimeMillis = SystemClock.uptimeMillis();
                MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                view.onTouchEvent(motionEventObtain);
                motionEventObtain.recycle();
                forwardingListener.mForwarding = true;
            }
        }
    }

    public ForwardingListener(View view) {
        this.mSrc = view;
        view.setLongClickable(true);
        view.addOnAttachStateChangeListener(this);
        this.mScaledTouchSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        int tapTimeout = ViewConfiguration.getTapTimeout();
        this.mTapTimeout = tapTimeout;
        this.mLongPressTimeout = (ViewConfiguration.getLongPressTimeout() + tapTimeout) / 2;
    }

    public final void clearCallbacks() {
        TriggerLongPress triggerLongPress = this.mTriggerLongPress;
        if (triggerLongPress != null) {
            this.mSrc.removeCallbacks(triggerLongPress);
        }
        DisallowIntercept disallowIntercept = this.mDisallowIntercept;
        if (disallowIntercept != null) {
            this.mSrc.removeCallbacks(disallowIntercept);
        }
    }

    public abstract ShowableListMenu getPopup();

    public boolean onForwardingStarted() {
        ShowableListMenu popup = getPopup();
        if (popup == null || popup.isShowing()) {
            return true;
        }
        popup.show();
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x010a  */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        boolean z;
        DropDownListView listView;
        boolean z2 = this.mForwarding;
        if (z2) {
            View view2 = this.mSrc;
            ShowableListMenu popup = getPopup();
            if (popup == null || !popup.isShowing() || (listView = popup.getListView()) == null || !listView.isShown()) {
                ShowableListMenu popup2 = getPopup();
                if (popup2 != null && popup2.isShowing()) {
                    popup2.dismiss();
                }
                z = false;
            } else {
                MotionEvent motionEventObtainNoHistory = MotionEvent.obtainNoHistory(motionEvent);
                view2.getLocationOnScreen(this.mTmpLocation);
                motionEventObtainNoHistory.offsetLocation(r6[0], r6[1]);
                listView.getLocationOnScreen(this.mTmpLocation);
                motionEventObtainNoHistory.offsetLocation(-r3[0], -r3[1]);
                boolean zOnForwardedEvent = listView.onForwardedEvent(motionEventObtainNoHistory, this.mActivePointerId);
                motionEventObtainNoHistory.recycle();
                int actionMasked = motionEvent.getActionMasked();
                boolean z3 = (actionMasked == 1 || actionMasked == 3) ? false : true;
                if (zOnForwardedEvent && z3) {
                    z = true;
                }
            }
        } else {
            View view3 = this.mSrc;
            if (view3.isEnabled()) {
                int actionMasked2 = motionEvent.getActionMasked();
                if (actionMasked2 == 0) {
                    this.mActivePointerId = motionEvent.getPointerId(0);
                    if (this.mDisallowIntercept == null) {
                        this.mDisallowIntercept = new DisallowIntercept();
                    }
                    view3.postDelayed(this.mDisallowIntercept, this.mTapTimeout);
                    if (this.mTriggerLongPress == null) {
                        this.mTriggerLongPress = new TriggerLongPress();
                    }
                    view3.postDelayed(this.mTriggerLongPress, this.mLongPressTimeout);
                } else if (actionMasked2 == 1) {
                    clearCallbacks();
                } else if (actionMasked2 == 2) {
                    int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (iFindPointerIndex >= 0) {
                        float x = motionEvent.getX(iFindPointerIndex);
                        float y = motionEvent.getY(iFindPointerIndex);
                        float f = this.mScaledTouchSlop;
                        float f2 = -f;
                        if (x < f2 || y < f2 || x >= (view3.getRight() - view3.getLeft()) + f || y >= (view3.getBottom() - view3.getTop()) + f) {
                            clearCallbacks();
                            view3.getParent().requestDisallowInterceptTouchEvent(true);
                            if (onForwardingStarted()) {
                                z = true;
                            }
                            if (z) {
                                long jUptimeMillis = SystemClock.uptimeMillis();
                                MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                                this.mSrc.onTouchEvent(motionEventObtain);
                                motionEventObtain.recycle();
                            }
                        }
                    }
                } else if (actionMasked2 == 3) {
                }
                z = false;
                if (z) {
                }
            } else {
                z = false;
                if (z) {
                }
            }
        }
        this.mForwarding = z;
        return z || z2;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        this.mForwarding = false;
        this.mActivePointerId = -1;
        DisallowIntercept disallowIntercept = this.mDisallowIntercept;
        if (disallowIntercept != null) {
            this.mSrc.removeCallbacks(disallowIntercept);
        }
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
    }
}
