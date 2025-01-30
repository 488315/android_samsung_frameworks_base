package android.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.samsung.android.rune.CoreRune;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public class HandwritingInitiator {
  private final int mHandwritingSlop;
  private final InputMethodManager mImm;
  private State mState;
  private final HandwritingAreaTracker mHandwritingAreasTracker = new HandwritingAreaTracker();
  public WeakReference<View> mConnectedView = null;
  private int mConnectionCount = 0;
  private final RectF mTempRectF = new RectF();
  private final Region mTempRegion = new Region();
  private final Matrix mTempMatrix = new Matrix();
  private WeakReference<View> mCachedHoverTarget = null;
  private boolean mShowHoverIconForConnectedView = true;
  private final long mHandwritingTimeoutInMillis = ViewConfiguration.getLongPressTimeout();

  public HandwritingInitiator(
      ViewConfiguration viewConfiguration, InputMethodManager inputMethodManager) {
    this.mHandwritingSlop = viewConfiguration.getScaledHandwritingSlop();
    this.mImm = inputMethodManager;
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  /* JADX WARN: Removed duplicated region for block: B:61:0x0109  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean onTouchEvent(MotionEvent motionEvent) {
    State state;
    int maskedAction = motionEvent.getActionMasked();
    switch (maskedAction) {
      case 0:
      case 5:
        this.mState = null;
        int actionIndex = motionEvent.getActionIndex();
        int toolType = motionEvent.getToolType(actionIndex);
        int buttonState = motionEvent.getButtonState();
        if ((toolType == 2 || toolType == 4)
            && (!CoreRune.FW_DIRECT_WRITING || buttonState != 32)) {
          this.mState = new State(motionEvent);
          break;
        }
        break;
      case 1:
      case 3:
        state = this.mState;
        if (state != null) {
          state.mShouldInitHandwriting = false;
          if (!this.mState.mHasInitiatedHandwriting
              && !this.mState.mHasPreparedHandwritingDelegation) {
            this.mShowHoverIconForConnectedView = true;
            break;
          }
        }
        break;
      case 2:
        State state2 = this.mState;
        if (state2 != null) {
          if (!state2.mShouldInitHandwriting || this.mState.mExceedHandwritingSlop) {
            if (!this.mState.mHasInitiatedHandwriting
                && !this.mState.mHasPreparedHandwritingDelegation) {
              break;
            }
          } else {
            long timeElapsed = motionEvent.getEventTime() - this.mState.mStylusDownTimeInMillis;
            if (timeElapsed > this.mHandwritingTimeoutInMillis) {
              this.mState.mShouldInitHandwriting = false;
              if (!this.mState.mHasInitiatedHandwriting
                  && !this.mState.mHasPreparedHandwritingDelegation) {
                break;
              }
            } else {
              int pointerIndex = motionEvent.findPointerIndex(this.mState.mStylusPointerId);
              float x = motionEvent.getX(pointerIndex);
              float y = motionEvent.getY(pointerIndex);
              if (largerThanTouchSlop(x, y, this.mState.mStylusDownX, this.mState.mStylusDownY)) {
                this.mState.mExceedHandwritingSlop = true;
                View candidateView =
                    findBestCandidateView(
                        this.mState.mStylusDownX, this.mState.mStylusDownY, false);
                if (candidateView != null) {
                  if (candidateView == getConnectedView()) {
                    if (!candidateView.hasFocus()) {
                      requestFocusWithoutReveal(candidateView);
                    }
                    startHandwriting(candidateView);
                  } else if (candidateView.getHandwritingDelegatorCallback() != null) {
                    String delegatePackageName =
                        candidateView.getAllowedHandwritingDelegatePackageName();
                    if (delegatePackageName == null) {
                      delegatePackageName = candidateView.getContext().getOpPackageName();
                    }
                    this.mImm.prepareStylusHandwritingDelegation(
                        candidateView, delegatePackageName);
                    candidateView.getHandwritingDelegatorCallback().run();
                    this.mState.mHasPreparedHandwritingDelegation = true;
                  } else {
                    this.mState.mPendingConnectedView = new WeakReference(candidateView);
                    requestFocusWithoutReveal(candidateView);
                  }
                }
              }
              if (!this.mState.mHasInitiatedHandwriting
                  && !this.mState.mHasPreparedHandwritingDelegation) {
                break;
              }
            }
          }
        }
        break;
      case 6:
        int pointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
        State state3 = this.mState;
        if (state3 == null || pointerId != state3.mStylusPointerId) {}
        state = this.mState;
        if (state != null) {}
        break;
    }
    return false;
  }

  private View getConnectedView() {
    WeakReference<View> weakReference = this.mConnectedView;
    if (weakReference == null) {
      return null;
    }
    return weakReference.get();
  }

  private void clearConnectedView() {
    this.mConnectedView = null;
    this.mConnectionCount = 0;
  }

  public void onDelegateViewFocused(View view) {
    if (view == getConnectedView() && tryAcceptStylusHandwritingDelegation(view)) {
      this.mShowHoverIconForConnectedView = false;
    }
  }

  public void onInputConnectionCreated(View view) {
    if (!view.isAutoHandwritingEnabled()) {
      clearConnectedView();
      return;
    }
    View connectedView = getConnectedView();
    if (connectedView == view) {
      this.mConnectionCount++;
      return;
    }
    this.mConnectedView = new WeakReference<>(view);
    this.mConnectionCount = 1;
    this.mShowHoverIconForConnectedView = true;
    if (view.isHandwritingDelegate() && tryAcceptStylusHandwritingDelegation(view)) {
      this.mShowHoverIconForConnectedView = false;
      return;
    }
    State state = this.mState;
    if (state != null
        && state.mPendingConnectedView != null
        && this.mState.mPendingConnectedView.get() == view) {
      startHandwriting(view);
    }
  }

  public void onInputConnectionClosed(View view) {
    View connectedView = getConnectedView();
    if (connectedView == null) {
      return;
    }
    if (connectedView == view) {
      int i = this.mConnectionCount - 1;
      this.mConnectionCount = i;
      if (i == 0) {
        clearConnectedView();
        return;
      }
      return;
    }
    clearConnectedView();
  }

  public void startHandwriting(View view) {
    this.mImm.startStylusHandwriting(view);
    this.mState.mHasInitiatedHandwriting = true;
    this.mState.mShouldInitHandwriting = false;
    this.mShowHoverIconForConnectedView = false;
    if (view instanceof TextView) {
      ((TextView) view).hideHint();
    }
  }

  public boolean tryAcceptStylusHandwritingDelegation(View view) {
    String delegatorPackageName = view.getAllowedHandwritingDelegatorPackageName();
    if (delegatorPackageName == null) {
      delegatorPackageName = view.getContext().getOpPackageName();
    }
    if (!this.mImm.acceptStylusHandwritingDelegation(view, delegatorPackageName)) {
      return false;
    }
    State state = this.mState;
    if (state != null) {
      state.mHasInitiatedHandwriting = true;
      this.mState.mShouldInitHandwriting = false;
    }
    if (view instanceof TextView) {
      ((TextView) view).hideHint();
    }
    return true;
  }

  public void updateHandwritingAreasForView(View view) {
    this.mHandwritingAreasTracker.updateHandwritingAreaForView(view);
  }

  private static boolean shouldTriggerStylusHandwritingForView(View view) {
    if (!view.shouldInitiateHandwriting()) {
      return false;
    }
    return view.isStylusHandwritingAvailable();
  }

  public PointerIcon onResolvePointerIcon(Context context, MotionEvent event) {
    View hoverView = findHoverView(event);
    if (hoverView == null) {
      return null;
    }
    if (this.mShowHoverIconForConnectedView) {
      return PointerIcon.getSystemIcon(context, 1022);
    }
    if (hoverView == getConnectedView()) {
      return null;
    }
    this.mShowHoverIconForConnectedView = true;
    return PointerIcon.getSystemIcon(context, 1022);
  }

  private View getCachedHoverTarget() {
    WeakReference<View> weakReference = this.mCachedHoverTarget;
    if (weakReference == null) {
      return null;
    }
    return weakReference.get();
  }

  private View findHoverView(MotionEvent event) {
    if (!event.isStylusPointer() || !event.isHoverEvent()) {
      return null;
    }
    if (event.getActionMasked() == 9 || event.getActionMasked() == 7) {
      float hoverX = event.getX(event.getActionIndex());
      float hoverY = event.getY(event.getActionIndex());
      View cachedHoverTarget = getCachedHoverTarget();
      if (cachedHoverTarget != null) {
        Rect handwritingArea = getViewHandwritingArea(cachedHoverTarget);
        if (isInHandwritingArea(handwritingArea, hoverX, hoverY, cachedHoverTarget, true)
            && shouldTriggerStylusHandwritingForView(cachedHoverTarget)) {
          return cachedHoverTarget;
        }
      }
      View candidateView = findBestCandidateView(hoverX, hoverY, true);
      if (candidateView != null) {
        this.mCachedHoverTarget = new WeakReference<>(candidateView);
        return candidateView;
      }
    }
    this.mCachedHoverTarget = null;
    return null;
  }

  private static void requestFocusWithoutReveal(View view) {
    if (view.getRevealOnFocusHint()) {
      view.setRevealOnFocusHint(false);
      view.requestFocus();
      view.setRevealOnFocusHint(true);
      return;
    }
    view.requestFocus();
  }

  private View findBestCandidateView(float x, float y, boolean isHover) {
    float minDistance = Float.MAX_VALUE;
    View bestCandidate = null;
    View connectedView = getConnectedView();
    if (connectedView != null) {
      Rect handwritingArea = getViewHandwritingArea(connectedView);
      if (isInHandwritingArea(handwritingArea, x, y, connectedView, isHover)
          && shouldTriggerStylusHandwritingForView(connectedView)) {
        float distance = distance(handwritingArea, x, y);
        if (distance == 0.0f) {
          return connectedView;
        }
        bestCandidate = connectedView;
        minDistance = distance;
      }
    }
    List<HandwritableViewInfo> handwritableViewInfos =
        this.mHandwritingAreasTracker.computeViewInfos();
    for (HandwritableViewInfo viewInfo : handwritableViewInfos) {
      View view = viewInfo.getView();
      Rect handwritingArea2 = viewInfo.getHandwritingArea();
      if (isInHandwritingArea(handwritingArea2, x, y, view, isHover)
          && shouldTriggerStylusHandwritingForView(view)) {
        float distance2 = distance(handwritingArea2, x, y);
        if (distance2 == 0.0f) {
          return view;
        }
        if (distance2 < minDistance) {
          minDistance = distance2;
          bestCandidate = view;
        }
      }
    }
    return bestCandidate;
  }

  private static float distance(Rect rect, float x, float y) {
    float xDistance;
    float yDistance;
    if (contains(rect, x, y, 0.0f, 0.0f, 0.0f, 0.0f)) {
      return 0.0f;
    }
    if (x >= rect.left && x < rect.right) {
      xDistance = 0.0f;
    } else if (x < rect.left) {
      xDistance = rect.left - x;
    } else {
      xDistance = x - rect.right;
    }
    if (y >= rect.top && y < rect.bottom) {
      yDistance = 0.0f;
    } else if (y < rect.top) {
      yDistance = rect.top - y;
    } else {
      yDistance = y - rect.bottom;
    }
    return (xDistance * xDistance) + (yDistance * yDistance);
  }

  private static Rect getViewHandwritingArea(View view) {
    ViewParent viewParent = view.getParent();
    if (viewParent != null && view.isAttachedToWindow() && view.isAggregatedVisible()) {
      Rect localHandwritingArea = view.getHandwritingArea();
      Rect globalHandwritingArea = new Rect();
      if (localHandwritingArea != null) {
        globalHandwritingArea.set(localHandwritingArea);
      } else {
        globalHandwritingArea.set(0, 0, view.getWidth(), view.getHeight());
      }
      if (viewParent.getChildVisibleRect(view, globalHandwritingArea, null)) {
        return globalHandwritingArea;
      }
    }
    return null;
  }

  private boolean isInHandwritingArea(
      Rect handwritingArea, float x, float y, View view, boolean isHover) {
    if (handwritingArea == null
        || !contains(
            handwritingArea,
            x,
            y,
            view.getHandwritingBoundsOffsetLeft(),
            view.getHandwritingBoundsOffsetTop(),
            view.getHandwritingBoundsOffsetRight(),
            view.getHandwritingBoundsOffsetBottom())) {
      return false;
    }
    ViewParent parent = view.getParent();
    if (parent == null) {
      return true;
    }
    Region region = this.mTempRegion;
    this.mTempRegion.set(0, 0, view.getWidth(), view.getHeight());
    Matrix matrix = this.mTempMatrix;
    matrix.reset();
    if (!parent.getChildLocalHitRegion(view, region, matrix, isHover)) {
      return false;
    }
    float left = x - view.getHandwritingBoundsOffsetRight();
    float top = y - view.getHandwritingBoundsOffsetBottom();
    float right = Math.max(x + view.getHandwritingBoundsOffsetLeft(), left + 1.0f);
    float bottom = Math.max(y + view.getHandwritingBoundsOffsetTop(), 1.0f + top);
    RectF rectF = this.mTempRectF;
    rectF.set(left, top, right, bottom);
    matrix.mapRect(rectF);
    return region.m20op(
        Math.round(rectF.left),
        Math.round(rectF.top),
        Math.round(rectF.right),
        Math.round(rectF.bottom),
        Region.EnumC0836Op.INTERSECT);
  }

  private static boolean contains(
      Rect rect,
      float x,
      float y,
      float offsetLeft,
      float offsetTop,
      float offsetRight,
      float offsetBottom) {
    return x >= ((float) rect.left) - offsetLeft
        && x < ((float) rect.right) + offsetRight
        && y >= ((float) rect.top) - offsetTop
        && y < ((float) rect.bottom) + offsetBottom;
  }

  private boolean largerThanTouchSlop(float x1, float y1, float x2, float y2) {
    float dx = x1 - x2;
    float dy = y1 - y2;
    float f = (dx * dx) + (dy * dy);
    int i = this.mHandwritingSlop;
    return f > ((float) (i * i));
  }

  private static class State {
    private boolean mExceedHandwritingSlop;
    private boolean mHasInitiatedHandwriting;
    private boolean mHasPreparedHandwritingDelegation;
    private WeakReference<View> mPendingConnectedView;
    private boolean mShouldInitHandwriting;
    private final long mStylusDownTimeInMillis;
    private final float mStylusDownX;
    private final float mStylusDownY;
    private final int mStylusPointerId;

    private State(MotionEvent motionEvent) {
      this.mPendingConnectedView = null;
      int actionIndex = motionEvent.getActionIndex();
      this.mStylusPointerId = motionEvent.getPointerId(actionIndex);
      this.mStylusDownTimeInMillis = motionEvent.getEventTime();
      this.mStylusDownX = motionEvent.getX(actionIndex);
      this.mStylusDownY = motionEvent.getY(actionIndex);
      this.mShouldInitHandwriting = true;
      this.mHasInitiatedHandwriting = false;
      this.mHasPreparedHandwritingDelegation = false;
      this.mExceedHandwritingSlop = false;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static boolean isViewActive(View view) {
    return view != null
        && view.isAttachedToWindow()
        && view.isAggregatedVisible()
        && view.shouldInitiateHandwriting();
  }

  public static class HandwritingAreaTracker {
    private final List<HandwritableViewInfo> mHandwritableViewInfos = new ArrayList();

    public void updateHandwritingAreaForView(View view) {
      Iterator<HandwritableViewInfo> iterator = this.mHandwritableViewInfos.iterator();
      boolean found = false;
      while (iterator.hasNext()) {
        HandwritableViewInfo handwritableViewInfo = iterator.next();
        View curView = handwritableViewInfo.getView();
        if (!HandwritingInitiator.isViewActive(curView)) {
          iterator.remove();
        }
        if (curView == view) {
          found = true;
          handwritableViewInfo.mIsDirty = true;
        }
      }
      if (!found && HandwritingInitiator.isViewActive(view)) {
        this.mHandwritableViewInfos.add(new HandwritableViewInfo(view));
      }
    }

    static /* synthetic */ boolean lambda$computeViewInfos$0(HandwritableViewInfo viewInfo) {
      return !viewInfo.update();
    }

    public List<HandwritableViewInfo> computeViewInfos() {
      this.mHandwritableViewInfos.removeIf(
          new Predicate() { // from class:
                            // android.view.HandwritingInitiator$HandwritingAreaTracker$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              return HandwritingInitiator.HandwritingAreaTracker.lambda$computeViewInfos$0(
                  (HandwritingInitiator.HandwritableViewInfo) obj);
            }
          });
      return this.mHandwritableViewInfos;
    }
  }

  public static class HandwritableViewInfo {
    Rect mHandwritingArea = null;
    public boolean mIsDirty = true;
    final WeakReference<View> mViewRef;

    public HandwritableViewInfo(View view) {
      this.mViewRef = new WeakReference<>(view);
    }

    public View getView() {
      return this.mViewRef.get();
    }

    public Rect getHandwritingArea() {
      return this.mHandwritingArea;
    }

    public boolean update() {
      View view = getView();
      if (!HandwritingInitiator.isViewActive(view)) {
        return false;
      }
      if (!this.mIsDirty) {
        return true;
      }
      Rect handwritingArea = view.getHandwritingArea();
      if (handwritingArea == null) {
        return false;
      }
      ViewParent parent = view.getParent();
      if (parent != null) {
        if (this.mHandwritingArea == null) {
          this.mHandwritingArea = new Rect();
        }
        this.mHandwritingArea.set(handwritingArea);
        if (!parent.getChildVisibleRect(view, this.mHandwritingArea, null)) {
          this.mHandwritingArea = null;
        }
      }
      this.mIsDirty = false;
      return true;
    }
  }
}
