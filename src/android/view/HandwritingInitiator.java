package android.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.text.TextUtils;
import android.view.HandwritingInitiator;
import android.view.inputmethod.ConnectionlessHandwritingCallback;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.Flags;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.R;
import com.samsung.android.rune.CoreRune;
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

    public HandwritingInitiator(ViewConfiguration viewConfiguration, InputMethodManager inputMethodManager) {
        this.mHandwritingSlop = viewConfiguration.getScaledHandwritingSlop();
        this.mImm = inputMethodManager;
    }

    /* JADX WARN: Removed duplicated region for block: B:88:0x0172  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mState = null;
            int buttonState = motionEvent.getButtonState();
            if (motionEvent.isStylusPointer() && (!CoreRune.DIRECT_WRITING || buttonState != 32)) {
                this.mState = new State(motionEvent);
            }
        } else {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    State state = this.mState;
                    if (state == null) {
                        return false;
                    }
                    if (!state.mShouldInitHandwriting || this.mState.mExceedHandwritingSlop) {
                        return this.mState.mHandled;
                    }
                    if (motionEvent.getEventTime() - this.mState.mStylusDownTimeInMillis > this.mHandwritingTimeoutInMillis) {
                        this.mState.mShouldInitHandwriting = false;
                        return this.mState.mHandled;
                    }
                    int iFindPointerIndex = motionEvent.findPointerIndex(this.mState.mStylusPointerId);
                    if (largerThanTouchSlop(motionEvent.getX(iFindPointerIndex), motionEvent.getY(iFindPointerIndex), this.mState.mStylusDownX, this.mState.mStylusDownY)) {
                        this.mState.mExceedHandwritingSlop = true;
                        View viewFindBestCandidateView = findBestCandidateView(this.mState.mStylusDownX, this.mState.mStylusDownY, false);
                        if (viewFindBestCandidateView != null && viewFindBestCandidateView.isEnabled()) {
                            boolean zHasFocus = viewFindBestCandidateView.hasFocus();
                            if (!viewFindBestCandidateView.isStylusHandwritingAvailable()) {
                                this.mState.mShouldInitHandwriting = false;
                                return false;
                            }
                            if (shouldShowHandwritingUnavailableMessageForView(viewFindBestCandidateView)) {
                                if (!CoreRune.DIRECT_WRITING) {
                                    Toast.makeText(viewFindBestCandidateView.getContext(), ((viewFindBestCandidateView instanceof TextView) && ((TextView) viewFindBestCandidateView).isAnyPasswordInputType()) ? R.string.error_handwriting_unsupported_password : R.string.error_handwriting_unsupported, 0).show();
                                }
                                if (!viewFindBestCandidateView.hasFocus()) {
                                    requestFocusWithoutReveal(viewFindBestCandidateView);
                                }
                                if (!com.android.text.flags.Flags.handwritingUnsupportedShowSoftInputFix() || ((viewFindBestCandidateView instanceof TextView) && ((TextView) viewFindBestCandidateView).getShowSoftInputOnFocus())) {
                                    this.mImm.showSoftInput(viewFindBestCandidateView, 0);
                                }
                                this.mState.mHandled = true;
                                this.mState.mShouldInitHandwriting = false;
                                motionEvent.setAction((motionEvent.getAction() & 65280) | 3);
                                viewFindBestCandidateView.getRootView().dispatchTouchEvent(motionEvent);
                            } else if (viewFindBestCandidateView == getConnectedOrFocusedView()) {
                                if (!zHasFocus) {
                                    requestFocusWithoutReveal(viewFindBestCandidateView);
                                }
                                startHandwriting(viewFindBestCandidateView);
                            } else if (viewFindBestCandidateView.getHandwritingDelegatorCallback() != null) {
                                prepareDelegation(viewFindBestCandidateView);
                            } else if (!this.mInitiateWithoutConnection) {
                                this.mState.mPendingConnectedView = new WeakReference(viewFindBestCandidateView);
                                if (!zHasFocus) {
                                    requestFocusWithoutReveal(viewFindBestCandidateView);
                                }
                            } else if (!zHasFocus) {
                                this.mState.mPendingFocusedView = new WeakReference(viewFindBestCandidateView);
                                requestFocusWithoutReveal(viewFindBestCandidateView);
                            }
                        }
                    }
                    return this.mState.mHandled;
                }
                if (actionMasked != 3) {
                    if (actionMasked != 5) {
                        if (actionMasked == 6) {
                            int pointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
                            State state2 = this.mState;
                            if (state2 == null || pointerId != state2.mStylusPointerId) {
                                return false;
                            }
                        }
                    }
                }
            }
            State state3 = this.mState;
            if (state3 != null) {
                state3.mShouldInitHandwriting = false;
                if (!this.mState.mHandled) {
                    this.mShowHoverIconForConnectedView = true;
                }
            }
            return false;
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
        if (this.mInitiateWithoutConnection) {
            onEditorFocused(view);
        }
        if (view == getConnectedView()) {
            tryAcceptStylusHandwritingDelegation(view);
        }
    }

    public void onInputConnectionCreated(View view) {
        State state;
        if (!this.mInitiateWithoutConnection || view.isHandwritingDelegate()) {
            if (!view.isAutoHandwritingEnabled()) {
                clearConnectedView();
                return;
            }
            if (getConnectedView() == view) {
                this.mConnectionCount++;
                return;
            }
            this.mConnectedView = new WeakReference<>(view);
            this.mConnectionCount = 1;
            this.mShowHoverIconForConnectedView = true;
            if (view.isHandwritingDelegate() && tryAcceptStylusHandwritingDelegation(view)) {
                this.mShowHoverIconForConnectedView = false;
            } else {
                if (this.mInitiateWithoutConnection || (state = this.mState) == null || state.mPendingConnectedView == null || this.mState.mPendingConnectedView.get() != view) {
                    return;
                }
                startHandwriting(view);
            }
        }
    }

    public void onEditorFocused(View view) {
        if (this.mInitiateWithoutConnection) {
            View focusedView = getFocusedView();
            if (!com.android.text.flags.Flags.handwritingTrackDisabled() && !view.isAutoHandwritingEnabled()) {
                clearFocusedView(focusedView);
                return;
            }
            if (focusedView == view) {
                return;
            }
            updateFocusedView(view);
            State state = this.mState;
            if (state == null || state.mPendingFocusedView == null || this.mState.mPendingFocusedView.get() != view) {
                return;
            }
            if (!com.android.text.flags.Flags.handwritingTrackDisabled() || view.isAutoHandwritingEnabled()) {
                startHandwriting(view);
            }
        }
    }

    public void onInputConnectionClosed(View view) {
        View connectedView;
        if ((!this.mInitiateWithoutConnection || view.isHandwritingDelegate()) && (connectedView = getConnectedView()) != null) {
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
    }

    private View getFocusedView() {
        WeakReference<View> weakReference = this.mFocusedView;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    public void clearFocusedView(View view) {
        WeakReference<View> weakReference;
        if (view == null || (weakReference = this.mFocusedView) == null || weakReference.get() != view) {
            return;
        }
        this.mFocusedView = null;
    }

    public boolean updateFocusedView(View view) {
        if (!com.android.text.flags.Flags.handwritingTrackDisabled() && !view.shouldInitiateHandwriting()) {
            this.mFocusedView = null;
            return false;
        }
        if (getFocusedView() != view) {
            this.mFocusedView = new WeakReference<>(view);
            if (!com.android.text.flags.Flags.handwritingTrackDisabled() || view.shouldInitiateHandwriting()) {
                this.mShowHoverIconForConnectedView = true;
            }
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

    private void prepareDelegation(View view) throws Resources.NotFoundException {
        String allowedHandwritingDelegatePackageName = view.getAllowedHandwritingDelegatePackageName();
        if (allowedHandwritingDelegatePackageName == null) {
            allowedHandwritingDelegatePackageName = view.getContext().getOpPackageName();
        }
        String str = allowedHandwritingDelegatePackageName;
        if (this.mImm.isConnectionlessStylusHandwritingAvailable()) {
            view.getViewRootImpl().getView().clearFocus();
            InputMethodManager inputMethodManager = this.mImm;
            CursorAnchorInfo cursorAnchorInfoForConnectionless = getCursorAnchorInfoForConnectionless(view);
            Objects.requireNonNull(view);
            inputMethodManager.startConnectionlessStylusHandwritingForDelegation(view, cursorAnchorInfoForConnectionless, str, new HandwritingInitiator$$ExternalSyntheticLambda0(view), new DelegationCallback(view, str));
            this.mState.mShouldInitHandwriting = false;
        } else {
            this.mImm.prepareStylusHandwritingDelegation(view, str);
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
        String allowedHandwritingDelegatorPackageName = view.getAllowedHandwritingDelegatorPackageName();
        if (allowedHandwritingDelegatorPackageName == null) {
            allowedHandwritingDelegatorPackageName = view.getContext().getOpPackageName();
        }
        if (!this.mImm.acceptStylusHandwritingDelegation(view, allowedHandwritingDelegatorPackageName)) {
            return false;
        }
        onDelegationAccepted(view);
        return true;
    }

    private void tryAcceptStylusHandwritingDelegationAsync(View view) {
        String allowedHandwritingDelegatorPackageName = view.getAllowedHandwritingDelegatorPackageName();
        if (allowedHandwritingDelegatorPackageName == null) {
            allowedHandwritingDelegatorPackageName = view.getContext().getOpPackageName();
        }
        final WeakReference weakReference = new WeakReference(view);
        Consumer<Boolean> consumer = new Consumer() { // from class: android.view.HandwritingInitiator$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$tryAcceptStylusHandwritingDelegationAsync$0(weakReference, (Boolean) obj);
            }
        };
        InputMethodManager inputMethodManager = this.mImm;
        Objects.requireNonNull(view);
        inputMethodManager.acceptStylusHandwritingDelegation(view, allowedHandwritingDelegatorPackageName, new HandwritingInitiator$$ExternalSyntheticLambda0(view), consumer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$tryAcceptStylusHandwritingDelegationAsync$0(WeakReference weakReference, Boolean bool) {
        if (bool.booleanValue()) {
            onDelegationAccepted((View) weakReference.get());
        }
    }

    private void onDelegationAccepted(View view) {
        State state = this.mState;
        if (state != null) {
            state.mHandled = true;
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
        if (view.shouldInitiateHandwriting()) {
            return view.isStylusHandwritingAvailable();
        }
        return false;
    }

    private static boolean shouldShowHandwritingUnavailableMessageForView(View view) {
        return (view instanceof TextView) && !shouldTriggerStylusHandwritingForView(view);
    }

    private static boolean shouldTriggerHandwritingOrShowUnavailableMessageForView(View view) {
        return (view instanceof TextView) || shouldTriggerStylusHandwritingForView(view);
    }

    public PointerIcon onResolvePointerIcon(Context context, MotionEvent motionEvent) {
        View viewFindHoverView = findHoverView(motionEvent);
        if (viewFindHoverView != null && shouldTriggerStylusHandwritingForView(viewFindHoverView)) {
            if (this.mShowHoverIconForConnectedView) {
                return PointerIcon.getSystemIcon(context, 1022);
            }
            if (viewFindHoverView != getConnectedOrFocusedView()) {
                this.mShowHoverIconForConnectedView = true;
                return PointerIcon.getSystemIcon(context, 1022);
            }
        }
        return null;
    }

    private View getConnectedOrFocusedView() {
        if (this.mInitiateWithoutConnection) {
            WeakReference<View> weakReference = this.mFocusedView;
            if (weakReference == null) {
                return null;
            }
            return weakReference.get();
        }
        WeakReference<View> weakReference2 = this.mConnectedView;
        if (weakReference2 == null) {
            return null;
        }
        return weakReference2.get();
    }

    private View getCachedHoverTarget() {
        WeakReference<View> weakReference = this.mCachedHoverTarget;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private View findHoverView(MotionEvent motionEvent) {
        HandwritingInitiator handwritingInitiator;
        if (motionEvent.isStylusPointer() && motionEvent.isHoverEvent()) {
            if (motionEvent.getActionMasked() == 9 || motionEvent.getActionMasked() == 7) {
                float x = motionEvent.getX(motionEvent.getActionIndex());
                float y = motionEvent.getY(motionEvent.getActionIndex());
                View cachedHoverTarget = getCachedHoverTarget();
                if (cachedHoverTarget != null) {
                    Rect rect = this.mTempRect;
                    if (getViewHandwritingArea(cachedHoverTarget, rect)) {
                        handwritingInitiator = this;
                        if (handwritingInitiator.isInHandwritingArea(rect, x, y, cachedHoverTarget, true) && shouldTriggerStylusHandwritingForView(cachedHoverTarget)) {
                            return cachedHoverTarget;
                        }
                    } else {
                        handwritingInitiator = this;
                    }
                    View viewFindBestCandidateView = handwritingInitiator.findBestCandidateView(x, y, true);
                    if (viewFindBestCandidateView != null) {
                        if (!com.android.text.flags.Flags.handwritingUnsupportedMessage()) {
                            handwritingInitiator.mCachedHoverTarget = new WeakReference<>(viewFindBestCandidateView);
                        }
                        return viewFindBestCandidateView;
                    }
                }
            } else {
                handwritingInitiator = this;
            }
            handwritingInitiator.mCachedHoverTarget = null;
        }
        return null;
    }

    private void requestFocusWithoutReveal(View view) throws Resources.NotFoundException {
        if (!com.android.text.flags.Flags.handwritingCursorPosition() && (view instanceof EditText)) {
            EditText editText = (EditText) view;
            if (!this.mState.mStylusDownWithinEditorBounds) {
                view.getLocationInWindow(this.mTempLocation);
                editText.setSelection(editText.getOffsetForPosition(this.mState.mStylusDownX - this.mTempLocation[0], this.mState.mStylusDownY - this.mTempLocation[1]));
            }
        }
        if (view.getRevealOnFocusHint()) {
            view.setRevealOnFocusHint(false);
            view.requestFocus();
            view.setRevealOnFocusHint(true);
        } else {
            view.requestFocus();
        }
        if (com.android.text.flags.Flags.handwritingCursorPosition() && (view instanceof EditText)) {
            EditText editText2 = (EditText) view;
            view.getLocationInWindow(this.mTempLocation);
            int iIndexOf = TextUtils.indexOf((CharSequence) editText2.getText(), '\n', editText2.getLayout().getLineStart(editText2.getLineAtCoordinate(this.mState.mStylusDownY - this.mTempLocation[1])));
            if (iIndexOf < 0) {
                iIndexOf = editText2.getText().length();
            }
            editText2.setSelection(iIndexOf);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private View findBestCandidateView(float f, float f2, boolean z) {
        HandwritingInitiator handwritingInitiator;
        float f3;
        float f4;
        boolean z2;
        State state;
        State state2;
        View connectedOrFocusedView = getConnectedOrFocusedView();
        if (connectedOrFocusedView != null) {
            Rect rect = this.mTempRect;
            if (getViewHandwritingArea(connectedOrFocusedView, rect)) {
                handwritingInitiator = this;
                f3 = f;
                f4 = f2;
                z2 = z;
                if (handwritingInitiator.isInHandwritingArea(rect, f3, f4, connectedOrFocusedView, z2) && shouldTriggerHandwritingOrShowUnavailableMessageForView(connectedOrFocusedView)) {
                    if (!z2 && (state2 = handwritingInitiator.mState) != null) {
                        state2.mStylusDownWithinEditorBounds = contains(rect, f3, f4, 0.0f, 0.0f, 0.0f, 0.0f);
                    }
                    return connectedOrFocusedView;
                }
            } else {
                handwritingInitiator = this;
                f3 = f;
                f4 = f2;
                z2 = z;
            }
        }
        float f5 = Float.MAX_VALUE;
        View view = null;
        for (HandwritableViewInfo handwritableViewInfo : handwritingInitiator.mHandwritingAreasTracker.computeViewInfos()) {
            View view2 = handwritableViewInfo.getView();
            Rect handwritingArea = handwritableViewInfo.getHandwritingArea();
            boolean z3 = z2;
            z2 = z3;
            if (handwritingInitiator.isInHandwritingArea(handwritingArea, f3, f4, view2, z3) && shouldTriggerHandwritingOrShowUnavailableMessageForView(view2)) {
                float fDistance = distance(handwritingArea, f3, f4);
                if (fDistance == 0.0f) {
                    if (!z2 && (state = handwritingInitiator.mState) != null) {
                        state.mStylusDownWithinEditorBounds = true;
                    }
                    return view2;
                }
                if (fDistance < f5) {
                    f5 = fDistance;
                    view = view2;
                }
            }
        }
        return view;
    }

    private static float distance(Rect rect, float f, float f2) {
        float f3;
        boolean zContains = contains(rect, f, f2, 0.0f, 0.0f, 0.0f, 0.0f);
        float f4 = 0.0f;
        if (zContains) {
            return 0.0f;
        }
        if (f >= rect.left && f < rect.right) {
            f3 = 0.0f;
        } else if (f < rect.left) {
            f3 = rect.left - f;
        } else {
            f3 = f - rect.right;
        }
        if (f2 < rect.top || f2 >= rect.bottom) {
            if (f2 < rect.top) {
                f4 = rect.top - f2;
            } else {
                f4 = f2 - rect.bottom;
            }
        }
        return (f3 * f3) + (f4 * f4);
    }

    private static boolean getViewHandwritingArea(View view, Rect rect) {
        ViewParent parent = view.getParent();
        if (parent == null || !view.isAttachedToWindow() || !view.isAggregatedVisible()) {
            return false;
        }
        Rect handwritingArea = view.getHandwritingArea();
        if (handwritingArea != null) {
            rect.set(handwritingArea);
        } else {
            rect.set(0, 0, view.getWidth(), view.getHeight());
        }
        return parent.getChildVisibleRect(view, rect, null);
    }

    private boolean isInHandwritingArea(Rect rect, float f, float f2, View view, boolean z) {
        if (rect == null || !contains(rect, f, f2, view.getHandwritingBoundsOffsetLeft(), view.getHandwritingBoundsOffsetTop(), view.getHandwritingBoundsOffsetRight(), view.getHandwritingBoundsOffsetBottom())) {
            return false;
        }
        ViewParent parent = view.getParent();
        if (parent == null) {
            return true;
        }
        Region region = this.mTempRegion;
        region.set(0, 0, view.getWidth(), view.getHeight());
        Matrix matrix = this.mTempMatrix;
        matrix.reset();
        if (!parent.getChildLocalHitRegion(view, region, matrix, z)) {
            return false;
        }
        float handwritingBoundsOffsetRight = f - view.getHandwritingBoundsOffsetRight();
        float handwritingBoundsOffsetBottom = f2 - view.getHandwritingBoundsOffsetBottom();
        float fMax = Math.max(view.getHandwritingBoundsOffsetLeft() + f, handwritingBoundsOffsetRight + 1.0f);
        float fMax2 = Math.max(view.getHandwritingBoundsOffsetTop() + f2, 1.0f + handwritingBoundsOffsetBottom);
        RectF rectF = this.mTempRectF;
        rectF.set(handwritingBoundsOffsetRight, handwritingBoundsOffsetBottom, fMax, fMax2);
        matrix.mapRect(rectF);
        return region.op(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom), Region.Op.INTERSECT);
    }

    private static boolean contains(Rect rect, float f, float f2, float f3, float f4, float f5, float f6) {
        return f >= ((float) rect.left) - f3 && f < ((float) rect.right) + f5 && f2 >= ((float) rect.top) - f4 && f2 < ((float) rect.bottom) + f6;
    }

    private boolean largerThanTouchSlop(float f, float f2, float f3, float f4) {
        float f5 = f - f3;
        float f6 = f2 - f4;
        float f7 = (f5 * f5) + (f6 * f6);
        int i = this.mHandwritingSlop;
        return f7 > ((float) (i * i));
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
        return view != null && view.isAttachedToWindow() && view.isAggregatedVisible() && view.shouldTrackHandwritingArea();
    }

    private CursorAnchorInfo getCursorAnchorInfoForConnectionless(View view) {
        CursorAnchorInfo.Builder builder = new CursorAnchorInfo.Builder();
        TextView textViewFindFirstTextViewDescendent = findFirstTextViewDescendent(view);
        if (textViewFindFirstTextViewDescendent != null) {
            textViewFindFirstTextViewDescendent.getCursorAnchorInfo(0, builder, this.mTempMatrix);
            if (textViewFindFirstTextViewDescendent.getSelectionStart() < 0) {
                float height = textViewFindFirstTextViewDescendent.getHeight() - textViewFindFirstTextViewDescendent.getExtendedPaddingBottom();
                builder.setInsertionMarkerLocation(textViewFindFirstTextViewDescendent.getCompoundPaddingStart(), textViewFindFirstTextViewDescendent.getExtendedPaddingTop(), height, height, 0);
            }
        } else {
            this.mTempMatrix.reset();
            view.transformMatrixToGlobal(this.mTempMatrix);
            builder.setMatrix(this.mTempMatrix);
            builder.setInsertionMarkerLocation(view.isLayoutRtl() ? view.getWidth() : 0.0f, 0.0f, view.getHeight(), view.getHeight(), 0);
        }
        return builder.build();
    }

    private static TextView findFirstTextViewDescendent(View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            TextView textViewFindFirstTextViewDescendent = childAt instanceof TextView ? (TextView) childAt : findFirstTextViewDescendent(viewGroup.getChildAt(i));
            if (textViewFindFirstTextViewDescendent != null && textViewFindFirstTextViewDescendent.isAggregatedVisible() && (!TextUtils.isEmpty(textViewFindFirstTextViewDescendent.getText()) || !TextUtils.isEmpty(textViewFindFirstTextViewDescendent.getHint()))) {
                return textViewFindFirstTextViewDescendent;
            }
        }
        return null;
    }

    public static class HandwritingAreaTracker {
        private final List<HandwritableViewInfo> mHandwritableViewInfos = new ArrayList();

        public void updateHandwritingAreaForView(View view) {
            Iterator<HandwritableViewInfo> it = this.mHandwritableViewInfos.iterator();
            boolean z = false;
            while (it.hasNext()) {
                HandwritableViewInfo next = it.next();
                View view2 = next.getView();
                if (!HandwritingInitiator.isViewActive(view2)) {
                    it.remove();
                }
                if (view2 == view) {
                    z = true;
                    next.mIsDirty = true;
                }
            }
            if (z || !HandwritingInitiator.isViewActive(view)) {
                return;
            }
            this.mHandwritableViewInfos.add(new HandwritableViewInfo(view));
        }

        static /* synthetic */ boolean lambda$computeViewInfos$0(HandwritableViewInfo handwritableViewInfo) {
            return !handwritableViewInfo.update();
        }

        public List<HandwritableViewInfo> computeViewInfos() {
            this.mHandwritableViewInfos.removeIf(new Predicate() { // from class: android.view.HandwritingInitiator$HandwritingAreaTracker$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return HandwritingInitiator.HandwritingAreaTracker.lambda$computeViewInfos$0((HandwritingInitiator.HandwritableViewInfo) obj);
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

        private DelegationCallback(View view, String str) {
            this.mView = view;
            this.mDelegatePackageName = str;
        }

        @Override // android.view.inputmethod.ConnectionlessHandwritingCallback
        public void onResult(CharSequence charSequence) {
            this.mView.getHandwritingDelegatorCallback().run();
        }

        @Override // android.view.inputmethod.ConnectionlessHandwritingCallback
        public void onError(int i) {
            if (i == 0) {
                this.mView.getHandwritingDelegatorCallback().run();
            } else {
                if (i != 1) {
                    return;
                }
                HandwritingInitiator.this.mImm.prepareStylusHandwritingDelegation(this.mView, this.mDelegatePackageName);
                this.mView.getHandwritingDelegatorCallback().run();
            }
        }
    }
}
