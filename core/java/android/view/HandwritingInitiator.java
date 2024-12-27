package android.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.text.TextUtils;
import android.view.inputmethod.ConnectionlessHandwritingCallback;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.Flags;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public class HandwritingInitiator {
    private final int mHandwritingSlop;
    private final InputMethodManager mImm;
    private State mState;
    private final HandwritingAreaTracker mHandwritingAreasTracker = new HandwritingAreaTracker();
    public WeakReference<View> mConnectedView = null;
    private int mConnectionCount = 0;
    public WeakReference<View> mFocusedView = null;
    private final int[] mTempLocation = new int[2];
    private final Rect mTempRect = new Rect();
    private final RectF mTempRectF = new RectF();
    private final Region mTempRegion = new Region();
    private final Matrix mTempMatrix = new Matrix();
    private WeakReference<View> mCachedHoverTarget = null;
    private boolean mShowHoverIconForConnectedView = true;
    private final boolean mInitiateWithoutConnection = Flags.initiationWithoutInputConnection();
    private final long mHandwritingTimeoutInMillis = ViewConfiguration.getLongPressTimeout();

    public HandwritingInitiator(
            ViewConfiguration viewConfiguration, InputMethodManager inputMethodManager) {
        this.mHandwritingSlop = viewConfiguration.getScaledHandwritingSlop();
        this.mImm = inputMethodManager;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0158  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r13) {
        /*
            Method dump skipped, instructions count: 410
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.view.HandwritingInitiator.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private View getConnectedView() {
        if (this.mConnectedView == null) {
            return null;
        }
        return this.mConnectedView.get();
    }

    private void clearConnectedView() {
        this.mConnectedView = null;
        this.mConnectionCount = 0;
    }

    public void onDelegateViewFocused(View view) {
        if (this.mInitiateWithoutConnection) {
            onEditorFocused(view);
        }
        if (view == getConnectedView()) {
            tryAcceptStylusHandwritingDelegation(view);
        }
    }

    public void onInputConnectionCreated(View view) {
        if (this.mInitiateWithoutConnection && !view.isHandwritingDelegate()) {
            return;
        }
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
        } else if (!this.mInitiateWithoutConnection
                && this.mState != null
                && this.mState.mPendingConnectedView != null
                && this.mState.mPendingConnectedView.get() == view) {
            startHandwriting(view);
        }
    }

    public void onEditorFocused(View view) {
        if (!this.mInitiateWithoutConnection) {
            return;
        }
        if (!view.isAutoHandwritingEnabled()) {
            clearFocusedView(view);
            return;
        }
        View focusedView = getFocusedView();
        if (focusedView == view) {
            return;
        }
        updateFocusedView(view);
        if (this.mState != null
                && this.mState.mPendingFocusedView != null
                && this.mState.mPendingFocusedView.get() == view) {
            startHandwriting(view);
        }
    }

    public void onInputConnectionClosed(View view) {
        View connectedView;
        if ((!this.mInitiateWithoutConnection || view.isHandwritingDelegate())
                && (connectedView = getConnectedView()) != null) {
            if (connectedView == view) {
                this.mConnectionCount--;
                if (this.mConnectionCount == 0) {
                    clearConnectedView();
                    return;
                }
                return;
            }
            clearConnectedView();
        }
    }

    private View getFocusedView() {
        if (this.mFocusedView == null) {
            return null;
        }
        return this.mFocusedView.get();
    }

    public void clearFocusedView(View view) {
        if (view != null && this.mFocusedView != null && this.mFocusedView.get() == view) {
            this.mFocusedView = null;
        }
    }

    public boolean updateFocusedView(View view) {
        if (!view.shouldInitiateHandwriting()) {
            this.mFocusedView = null;
            return false;
        }
        View focusedView = getFocusedView();
        if (focusedView != view) {
            this.mFocusedView = new WeakReference<>(view);
            this.mShowHoverIconForConnectedView = true;
        }
        return true;
    }

    public void startHandwriting(View view) {
        this.mImm.startStylusHandwriting(view);
        this.mState.mHandled = true;
        this.mState.mShouldInitHandwriting = false;
        this.mShowHoverIconForConnectedView = false;
        if (view instanceof TextView) {
            ((TextView) view).hideHint();
        }
    }

    private void prepareDelegation(View view) {
        String delegatePackageName = view.getAllowedHandwritingDelegatePackageName();
        if (delegatePackageName == null) {
            delegatePackageName = view.getContext().getOpPackageName();
        }
        if (this.mImm.isConnectionlessStylusHandwritingAvailable()) {
            view.getViewRootImpl().getView().clearFocus();
            InputMethodManager inputMethodManager = this.mImm;
            CursorAnchorInfo cursorAnchorInfoForConnectionless =
                    getCursorAnchorInfoForConnectionless(view);
            Objects.requireNonNull(view);
            inputMethodManager.startConnectionlessStylusHandwritingForDelegation(
                    view,
                    cursorAnchorInfoForConnectionless,
                    delegatePackageName,
                    new HandwritingInitiator$$ExternalSyntheticLambda0(view),
                    new DelegationCallback(view, delegatePackageName));
            this.mState.mShouldInitHandwriting = false;
        } else {
            this.mImm.prepareStylusHandwritingDelegation(view, delegatePackageName);
            view.getHandwritingDelegatorCallback().run();
        }
        this.mState.mHandled = true;
    }

    public boolean tryAcceptStylusHandwritingDelegation(View view) {
        if (Flags.useZeroJankProxy()) {
            tryAcceptStylusHandwritingDelegationAsync(view);
            return false;
        }
        return tryAcceptStylusHandwritingDelegationInternal(view);
    }

    private boolean tryAcceptStylusHandwritingDelegationInternal(View view) {
        String delegatorPackageName = view.getAllowedHandwritingDelegatorPackageName();
        if (delegatorPackageName == null) {
            delegatorPackageName = view.getContext().getOpPackageName();
        }
        if (this.mImm.acceptStylusHandwritingDelegation(view, delegatorPackageName)) {
            onDelegationAccepted(view);
            return true;
        }
        return false;
    }

    private void tryAcceptStylusHandwritingDelegationAsync(View view) {
        String delegatorPackageName = view.getAllowedHandwritingDelegatorPackageName();
        if (delegatorPackageName == null) {
            delegatorPackageName = view.getContext().getOpPackageName();
        }
        final WeakReference<View> viewRef = new WeakReference<>(view);
        Consumer<Boolean> consumer =
                new Consumer() { // from class:
                                 // android.view.HandwritingInitiator$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        HandwritingInitiator.this
                                .lambda$tryAcceptStylusHandwritingDelegationAsync$0(
                                        viewRef, (Boolean) obj);
                    }
                };
        InputMethodManager inputMethodManager = this.mImm;
        Objects.requireNonNull(view);
        inputMethodManager.acceptStylusHandwritingDelegation(
                view,
                delegatorPackageName,
                new HandwritingInitiator$$ExternalSyntheticLambda0(view),
                consumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$tryAcceptStylusHandwritingDelegationAsync$0(
            WeakReference viewRef, Boolean delegationAccepted) {
        if (delegationAccepted.booleanValue()) {
            onDelegationAccepted((View) viewRef.get());
        }
    }

    private void onDelegationAccepted(View view) {
        if (this.mState != null) {
            this.mState.mHandled = true;
            this.mState.mShouldInitHandwriting = false;
        }
        if (view == null) {
            return;
        }
        if (view instanceof TextView) {
            ((TextView) view).hideHint();
        }
        this.mShowHoverIconForConnectedView = false;
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

    private static boolean shouldShowHandwritingUnavailableMessageForView(View view) {
        return (view instanceof TextView) && !shouldTriggerStylusHandwritingForView(view);
    }

    private static boolean shouldTriggerHandwritingOrShowUnavailableMessageForView(View view) {
        return (view instanceof TextView) || shouldTriggerStylusHandwritingForView(view);
    }

    public PointerIcon onResolvePointerIcon(Context context, MotionEvent event) {
        View hoverView = findHoverView(event);
        if (hoverView == null || !shouldTriggerStylusHandwritingForView(hoverView)) {
            return null;
        }
        if (this.mShowHoverIconForConnectedView) {
            return PointerIcon.getSystemIcon(context, 1022);
        }
        if (hoverView == getConnectedOrFocusedView()) {
            return null;
        }
        this.mShowHoverIconForConnectedView = true;
        return PointerIcon.getSystemIcon(context, 1022);
    }

    private View getConnectedOrFocusedView() {
        if (this.mInitiateWithoutConnection) {
            if (this.mFocusedView == null) {
                return null;
            }
            return this.mFocusedView.get();
        }
        if (this.mConnectedView == null) {
            return null;
        }
        return this.mConnectedView.get();
    }

    private View getCachedHoverTarget() {
        if (this.mCachedHoverTarget == null) {
            return null;
        }
        return this.mCachedHoverTarget.get();
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
                Rect handwritingArea = this.mTempRect;
                if (getViewHandwritingArea(cachedHoverTarget, handwritingArea)
                        && isInHandwritingArea(
                                handwritingArea, hoverX, hoverY, cachedHoverTarget, true)
                        && shouldTriggerStylusHandwritingForView(cachedHoverTarget)) {
                    return cachedHoverTarget;
                }
            }
            View candidateView = findBestCandidateView(hoverX, hoverY, true);
            if (candidateView != null) {
                if (!com.android.text.flags.Flags.handwritingUnsupportedMessage()) {
                    this.mCachedHoverTarget = new WeakReference<>(candidateView);
                }
                return candidateView;
            }
        }
        this.mCachedHoverTarget = null;
        return null;
    }

    private void requestFocusWithoutReveal(View view) {
        if (!com.android.text.flags.Flags.handwritingCursorPosition()
                && (view instanceof EditText)) {
            EditText editText = (EditText) view;
            if (!this.mState.mStylusDownWithinEditorBounds) {
                view.getLocationInWindow(this.mTempLocation);
                int offset =
                        editText.getOffsetForPosition(
                                this.mState.mStylusDownX - this.mTempLocation[0],
                                this.mState.mStylusDownY - this.mTempLocation[1]);
                editText.setSelection(offset);
            }
        }
        if (view.getRevealOnFocusHint()) {
            view.setRevealOnFocusHint(false);
            view.requestFocus();
            view.setRevealOnFocusHint(true);
        } else {
            view.requestFocus();
        }
        if (com.android.text.flags.Flags.handwritingCursorPosition()
                && (view instanceof EditText)) {
            EditText editText2 = (EditText) view;
            view.getLocationInWindow(this.mTempLocation);
            int line =
                    editText2.getLineAtCoordinate(this.mState.mStylusDownY - this.mTempLocation[1]);
            int paragraphEnd =
                    TextUtils.indexOf(
                            (CharSequence) editText2.getText(),
                            '\n',
                            editText2.getLayout().getLineStart(line));
            if (paragraphEnd < 0) {
                paragraphEnd = editText2.getText().length();
            }
            editText2.setSelection(paragraphEnd);
        }
    }

    private View findBestCandidateView(float x, float y, boolean isHover) {
        View connectedOrFocusedView = getConnectedOrFocusedView();
        if (connectedOrFocusedView != null) {
            Rect handwritingArea = this.mTempRect;
            if (getViewHandwritingArea(connectedOrFocusedView, handwritingArea)
                    && isInHandwritingArea(handwritingArea, x, y, connectedOrFocusedView, isHover)
                    && shouldTriggerHandwritingOrShowUnavailableMessageForView(
                            connectedOrFocusedView)) {
                if (!isHover && this.mState != null) {
                    this.mState.mStylusDownWithinEditorBounds =
                            contains(handwritingArea, x, y, 0.0f, 0.0f, 0.0f, 0.0f);
                }
                return connectedOrFocusedView;
            }
        }
        List<HandwritableViewInfo> handwritableViewInfos =
                this.mHandwritingAreasTracker.computeViewInfos();
        float minDistance = Float.MAX_VALUE;
        View bestCandidate = null;
        for (HandwritableViewInfo viewInfo : handwritableViewInfos) {
            View view = viewInfo.getView();
            Rect handwritingArea2 = viewInfo.getHandwritingArea();
            if (isInHandwritingArea(handwritingArea2, x, y, view, isHover)
                    && shouldTriggerHandwritingOrShowUnavailableMessageForView(view)) {
                float distance = distance(handwritingArea2, x, y);
                if (distance == 0.0f) {
                    if (!isHover && this.mState != null) {
                        this.mState.mStylusDownWithinEditorBounds = true;
                    }
                    return view;
                }
                if (distance < minDistance) {
                    minDistance = distance;
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

    private static boolean getViewHandwritingArea(View view, Rect rect) {
        ViewParent viewParent = view.getParent();
        if (viewParent == null || !view.isAttachedToWindow() || !view.isAggregatedVisible()) {
            return false;
        }
        Rect localHandwritingArea = view.getHandwritingArea();
        if (localHandwritingArea == null) {
            rect.set(0, 0, view.getWidth(), view.getHeight());
        } else {
            rect.set(localHandwritingArea);
        }
        return viewParent.getChildVisibleRect(view, rect, null);
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
        return region.op(
                Math.round(rectF.left),
                Math.round(rectF.top),
                Math.round(rectF.right),
                Math.round(rectF.bottom),
                Region.Op.INTERSECT);
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
        return (dx * dx) + (dy * dy) > ((float) (this.mHandwritingSlop * this.mHandwritingSlop));
    }

    private static class State {
        private boolean mExceedHandwritingSlop;
        private boolean mHandled;
        private WeakReference<View> mPendingConnectedView;
        private WeakReference<View> mPendingFocusedView;
        private boolean mShouldInitHandwriting;
        private final long mStylusDownTimeInMillis;
        private boolean mStylusDownWithinEditorBounds;
        private final float mStylusDownX;
        private final float mStylusDownY;
        private final int mStylusPointerId;

        private State(MotionEvent motionEvent) {
            this.mPendingConnectedView = null;
            this.mPendingFocusedView = null;
            int actionIndex = motionEvent.getActionIndex();
            this.mStylusPointerId = motionEvent.getPointerId(actionIndex);
            this.mStylusDownTimeInMillis = motionEvent.getEventTime();
            this.mStylusDownX = motionEvent.getX(actionIndex);
            this.mStylusDownY = motionEvent.getY(actionIndex);
            this.mShouldInitHandwriting = true;
            this.mHandled = false;
            this.mExceedHandwritingSlop = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isViewActive(View view) {
        return view != null
                && view.isAttachedToWindow()
                && view.isAggregatedVisible()
                && view.shouldTrackHandwritingArea();
    }

    private CursorAnchorInfo getCursorAnchorInfoForConnectionless(View view) {
        CursorAnchorInfo.Builder builder = new CursorAnchorInfo.Builder();
        TextView textView = findFirstTextViewDescendent(view);
        if (textView != null) {
            textView.getCursorAnchorInfo(0, builder, this.mTempMatrix);
            if (textView.getSelectionStart() < 0) {
                float bottom = textView.getHeight() - textView.getExtendedPaddingBottom();
                builder.setInsertionMarkerLocation(
                        textView.getCompoundPaddingStart(),
                        textView.getExtendedPaddingTop(),
                        bottom,
                        bottom,
                        0);
            }
        } else {
            this.mTempMatrix.reset();
            view.transformMatrixToGlobal(this.mTempMatrix);
            builder.setMatrix(this.mTempMatrix);
            builder.setInsertionMarkerLocation(
                    view.isLayoutRtl() ? view.getWidth() : 0.0f,
                    0.0f,
                    view.getHeight(),
                    view.getHeight(),
                    0);
        }
        return builder.build();
    }

    private static TextView findFirstTextViewDescendent(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                TextView textView =
                        child instanceof TextView
                                ? (TextView) child
                                : findFirstTextViewDescendent(viewGroup.getChildAt(i));
                if (textView != null
                        && textView.isAggregatedVisible()
                        && (!TextUtils.isEmpty(textView.getText())
                                || !TextUtils.isEmpty(textView.getHint()))) {
                    return textView;
                }
            }
            return null;
        }
        return null;
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
                            return HandwritingInitiator.HandwritingAreaTracker
                                    .lambda$computeViewInfos$0(
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

    private class DelegationCallback implements ConnectionlessHandwritingCallback {
        private final String mDelegatePackageName;
        private final View mView;

        private DelegationCallback(View view, String delegatePackageName) {
            this.mView = view;
            this.mDelegatePackageName = delegatePackageName;
        }

        @Override // android.view.inputmethod.ConnectionlessHandwritingCallback
        public void onResult(CharSequence text) {
            this.mView.getHandwritingDelegatorCallback().run();
        }

        @Override // android.view.inputmethod.ConnectionlessHandwritingCallback
        public void onError(int errorCode) {
            switch (errorCode) {
                case 0:
                    this.mView.getHandwritingDelegatorCallback().run();
                    break;
                case 1:
                    HandwritingInitiator.this.mImm.prepareStylusHandwritingDelegation(
                            this.mView, this.mDelegatePackageName);
                    this.mView.getHandwritingDelegatorCallback().run();
                    break;
            }
        }
    }
}
