package android.widget;

import android.p009os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import com.android.internal.view.menu.ShowableListMenu;

/* loaded from: classes4.dex */
public abstract class ForwardingListener
    implements View.OnTouchListener, View.OnAttachStateChangeListener {
  private int mActivePointerId;
  private Runnable mDisallowIntercept;
  private boolean mForwarding;
  private final int mLongPressTimeout;
  private final float mScaledTouchSlop;
  private final View mSrc;
  private final int mTapTimeout;
  private Runnable mTriggerLongPress;

  public abstract ShowableListMenu getPopup();

  public ForwardingListener(View src) {
    this.mSrc = src;
    src.setLongClickable(true);
    src.addOnAttachStateChangeListener(this);
    this.mScaledTouchSlop = ViewConfiguration.get(src.getContext()).getScaledTouchSlop();
    int tapTimeout = ViewConfiguration.getTapTimeout();
    this.mTapTimeout = tapTimeout;
    this.mLongPressTimeout = (tapTimeout + ViewConfiguration.getLongPressTimeout()) / 2;
  }

  @Override // android.view.View.OnTouchListener
  public boolean onTouch(View v, MotionEvent event) {
    boolean forwarding;
    boolean wasForwarding = this.mForwarding;
    if (!wasForwarding) {
      forwarding = onTouchObserved(event) && onForwardingStarted();
      if (forwarding) {
        long now = SystemClock.uptimeMillis();
        MotionEvent e = MotionEvent.obtain(now, now, 3, 0.0f, 0.0f, 0);
        this.mSrc.onTouchEvent(e);
        e.recycle();
      }
    } else {
      forwarding = onTouchForwarded(event) || !onForwardingStopped();
    }
    this.mForwarding = forwarding;
    return forwarding || wasForwarding;
  }

  @Override // android.view.View.OnAttachStateChangeListener
  public void onViewAttachedToWindow(View v) {}

  @Override // android.view.View.OnAttachStateChangeListener
  public void onViewDetachedFromWindow(View v) {
    this.mForwarding = false;
    this.mActivePointerId = -1;
    Runnable runnable = this.mDisallowIntercept;
    if (runnable != null) {
      this.mSrc.removeCallbacks(runnable);
    }
  }

  protected boolean onForwardingStarted() {
    ShowableListMenu popup = getPopup();
    if (popup != null && !popup.isShowing()) {
      popup.show();
      return true;
    }
    return true;
  }

  protected boolean onForwardingStopped() {
    ShowableListMenu popup = getPopup();
    if (popup != null && popup.isShowing()) {
      popup.dismiss();
      return true;
    }
    return true;
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  /* JADX WARN: Multi-variable type inference failed */
  private boolean onTouchObserved(MotionEvent motionEvent) {
    View view = this.mSrc;
    if (!view.isEnabled()) {
      return false;
    }
    switch (motionEvent.getActionMasked()) {
      case 0:
        this.mActivePointerId = motionEvent.getPointerId(0);
        Object[] objArr = 0;
        if (this.mDisallowIntercept == null) {
          this.mDisallowIntercept = new DisallowIntercept();
        }
        view.postDelayed(this.mDisallowIntercept, this.mTapTimeout);
        if (this.mTriggerLongPress == null) {
          this.mTriggerLongPress = new TriggerLongPress();
        }
        view.postDelayed(this.mTriggerLongPress, this.mLongPressTimeout);
        return false;
      case 1:
      case 3:
        clearCallbacks();
        return false;
      case 2:
        int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
        if (findPointerIndex >= 0
            && !view.pointInView(
                motionEvent.getX(findPointerIndex),
                motionEvent.getY(findPointerIndex),
                this.mScaledTouchSlop)) {
          clearCallbacks();
          view.getParent().requestDisallowInterceptTouchEvent(true);
          return true;
        }
        return false;
      default:
        return false;
    }
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
    View src = this.mSrc;
    if (!src.isEnabled() || src.isLongClickable() || !onForwardingStarted()) {
      return;
    }
    src.getParent().requestDisallowInterceptTouchEvent(true);
    long now = SystemClock.uptimeMillis();
    MotionEvent e = MotionEvent.obtain(now, now, 3, 0.0f, 0.0f, 0);
    src.onTouchEvent(e);
    e.recycle();
    this.mForwarding = true;
  }

  private boolean onTouchForwarded(MotionEvent srcEvent) {
    DropDownListView dst;
    boolean keepForwarding;
    View src = this.mSrc;
    ShowableListMenu popup = getPopup();
    if (popup == null
        || !popup.isShowing()
        || (dst = (DropDownListView) popup.getListView()) == null
        || !dst.isShown()) {
      return false;
    }
    MotionEvent dstEvent = MotionEvent.obtainNoHistory(srcEvent);
    src.toGlobalMotionEvent(dstEvent);
    dst.toLocalMotionEvent(dstEvent);
    boolean handled = dst.onForwardedEvent(dstEvent, this.mActivePointerId);
    dstEvent.recycle();
    int action = srcEvent.getActionMasked();
    if (action == 1 || action == 3) {
      keepForwarding = false;
    } else {
      keepForwarding = true;
    }
    if (!handled || !keepForwarding) {
      return false;
    }
    return true;
  }

  private class DisallowIntercept implements Runnable {
    private DisallowIntercept() {}

    @Override // java.lang.Runnable
    public void run() {
      ViewParent parent = ForwardingListener.this.mSrc.getParent();
      if (parent != null) {
        parent.requestDisallowInterceptTouchEvent(true);
      }
    }
  }

  private class TriggerLongPress implements Runnable {
    private TriggerLongPress() {}

    @Override // java.lang.Runnable
    public void run() {
      ForwardingListener.this.onLongPress();
    }
  }
}
