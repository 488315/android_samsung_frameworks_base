package android.view;

import android.content.Context;
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

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
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
                    int findPointerIndex = motionEvent.findPointerIndex(this.mState.mStylusPointerId);
                    if (largerThanTouchSlop(motionEvent.getX(findPointerIndex), motionEvent.getY(findPointerIndex), this.mState.mStylusDownX, this.mState.mStylusDownY)) {
                        this.mState.mExceedHandwritingSlop = true;
                        View findBestCandidateView = findBestCandidateView(this.mState.mStylusDownX, this.mState.mStylusDownY, false);
                        if (findBestCandidateView != null && findBestCandidateView.isEnabled()) {
                            boolean hasFocus = findBestCandidateView.hasFocus();
                            if (!findBestCandidateView.isStylusHandwritingAvailable()) {
                                this.mState.mShouldInitHandwriting = false;
                                return false;
                            }
                            if (shouldShowHandwritingUnavailableMessageForView(findBestCandidateView)) {
                                if (!CoreRune.DIRECT_WRITING) {
                                    Toast.makeText(findBestCandidateView.getContext(), ((findBestCandidateView instanceof TextView) && ((TextView) findBestCandidateView).isAnyPasswordInputType()) ? R.string.error_handwriting_unsupported_password : R.string.error_handwriting_unsupported, 0).show();
                                }
                                if (!findBestCandidateView.hasFocus()) {
                                    requestFocusWithoutReveal(findBestCandidateView);
                                }
                                if (!com.android.text.flags.Flags.handwritingUnsupportedShowSoftInputFix() || ((findBestCandidateView instanceof TextView) && ((TextView) findBestCandidateView).getShowSoftInputOnFocus())) {
                                    this.mImm.showSoftInput(findBestCandidateView, 0);
                                }
                                this.mState.mHandled = true;
                                this.mState.mShouldInitHandwriting = false;
                                motionEvent.setAction((motionEvent.getAction() & 65280) | 3);
                                findBestCandidateView.getRootView().dispatchTouchEvent(motionEvent);
                            } else if (findBestCandidateView == getConnectedOrFocusedView()) {
                                if (!hasFocus) {
                                    requestFocusWithoutReveal(findBestCandidateView);
                                }
                                startHandwriting(findBestCandidateView);
                            } else if (findBestCandidateView.getHandwritingDelegatorCallback() != null) {
                                prepareDelegation(findBestCandidateView);
                            } else if (!this.mInitiateWithoutConnection) {
                                this.mState.mPendingConnectedView = new WeakReference(findBestCandidateView);
                                if (!hasFocus) {
                                    requestFocusWithoutReveal(findBestCandidateView);
                                }
                            } else if (!hasFocus) {
                                this.mState.mPendingFocusedView = new WeakReference(findBestCandidateView);
                                requestFocusWithoutReveal(findBestCandidateView);
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
                        return false;
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
        this.mState = null;
        int buttonState = motionEvent.getButtonState();
        if (motionEvent.isStylusPointer() && (!CoreRune.DIRECT_WRITING || buttonState != 32)) {
            this.mState = new State(motionEvent);
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

    private void prepareDelegation(View view) {
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
                HandwritingInitiator.this.lambda$tryAcceptStylusHandwritingDelegationAsync$0(weakReference, (Boolean) obj);
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
        View findHoverView = findHoverView(motionEvent);
        if (findHoverView != null && shouldTriggerStylusHandwritingForView(findHoverView)) {
            if (this.mShowHoverIconForConnectedView) {
                return PointerIcon.getSystemIcon(context, 1022);
            }
            if (findHoverView != getConnectedOrFocusedView()) {
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

    /* JADX WARN: Removed duplicated region for block: B:23:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.view.View findHoverView(android.view.MotionEvent r9) {
        /*
            r8 = this;
            boolean r0 = r9.isStylusPointer()
            r1 = 0
            if (r0 == 0) goto L65
            boolean r0 = r9.isHoverEvent()
            if (r0 != 0) goto Le
            goto L65
        Le:
            int r0 = r9.getActionMasked()
            r2 = 9
            if (r0 == r2) goto L20
            int r0 = r9.getActionMasked()
            r2 = 7
            if (r0 != r2) goto L1e
            goto L20
        L1e:
            r2 = r8
            goto L63
        L20:
            int r0 = r9.getActionIndex()
            float r4 = r9.getX(r0)
            int r0 = r9.getActionIndex()
            float r5 = r9.getY(r0)
            android.view.View r6 = r8.getCachedHoverTarget()
            if (r6 == 0) goto L4d
            android.graphics.Rect r3 = r8.mTempRect
            boolean r9 = getViewHandwritingArea(r6, r3)
            if (r9 == 0) goto L4d
            r7 = 1
            r2 = r8
            boolean r8 = r2.isInHandwritingArea(r3, r4, r5, r6, r7)
            if (r8 == 0) goto L4e
            boolean r8 = shouldTriggerStylusHandwritingForView(r6)
            if (r8 == 0) goto L4e
            return r6
        L4d:
            r2 = r8
        L4e:
            r8 = 1
            android.view.View r8 = r2.findBestCandidateView(r4, r5, r8)
            if (r8 == 0) goto L63
            boolean r9 = com.android.text.flags.Flags.handwritingUnsupportedMessage()
            if (r9 != 0) goto L62
            java.lang.ref.WeakReference r9 = new java.lang.ref.WeakReference
            r9.<init>(r8)
            r2.mCachedHoverTarget = r9
        L62:
            return r8
        L63:
            r2.mCachedHoverTarget = r1
        L65:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.HandwritingInitiator.findHoverView(android.view.MotionEvent):android.view.View");
    }

    private void requestFocusWithoutReveal(View view) {
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
            int indexOf = TextUtils.indexOf((CharSequence) editText2.getText(), '\n', editText2.getLayout().getLineStart(editText2.getLineAtCoordinate(this.mState.mStylusDownY - this.mTempLocation[1])));
            if (indexOf < 0) {
                indexOf = editText2.getText().length();
            }
            editText2.setSelection(indexOf);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x004b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.view.View findBestCandidateView(float r13, float r14, boolean r15) {
        /*
            r12 = this;
            android.view.View r4 = r12.getConnectedOrFocusedView()
            if (r4 == 0) goto L33
            android.graphics.Rect r1 = r12.mTempRect
            boolean r0 = getViewHandwritingArea(r4, r1)
            if (r0 == 0) goto L33
            r0 = r12
            r2 = r13
            r3 = r14
            r5 = r15
            boolean r12 = r0.isInHandwritingArea(r1, r2, r3, r4, r5)
            if (r12 == 0) goto L37
            boolean r12 = shouldTriggerHandwritingOrShowUnavailableMessageForView(r4)
            if (r12 == 0) goto L37
            if (r5 != 0) goto L32
            android.view.HandwritingInitiator$State r12 = r0.mState
            if (r12 == 0) goto L32
            r10 = 0
            r11 = 0
            r8 = 0
            r9 = 0
            r5 = r1
            r6 = r2
            r7 = r3
            boolean r13 = contains(r5, r6, r7, r8, r9, r10, r11)
            android.view.HandwritingInitiator.State.m5663$$Nest$fputmStylusDownWithinEditorBounds(r12, r13)
        L32:
            return r4
        L33:
            r0 = r12
            r2 = r13
            r3 = r14
            r5 = r15
        L37:
            android.view.HandwritingInitiator$HandwritingAreaTracker r12 = r0.mHandwritingAreasTracker
            java.util.List r12 = r12.computeViewInfos()
            java.util.Iterator r12 = r12.iterator()
            r13 = 2139095039(0x7f7fffff, float:3.4028235E38)
            r14 = 0
        L45:
            boolean r15 = r12.hasNext()
            if (r15 == 0) goto L86
            java.lang.Object r15 = r12.next()
            android.view.HandwritingInitiator$HandwritableViewInfo r15 = (android.view.HandwritingInitiator.HandwritableViewInfo) r15
            android.view.View r9 = r15.getView()
            android.graphics.Rect r6 = r15.getHandwritingArea()
            r7 = r2
            r8 = r3
            r10 = r5
            r5 = r0
            boolean r15 = r5.isInHandwritingArea(r6, r7, r8, r9, r10)
            r5 = r10
            if (r15 == 0) goto L45
            boolean r15 = shouldTriggerHandwritingOrShowUnavailableMessageForView(r9)
            if (r15 != 0) goto L6b
            goto L45
        L6b:
            float r15 = distance(r6, r2, r3)
            r1 = 0
            int r1 = (r15 > r1 ? 1 : (r15 == r1 ? 0 : -1))
            if (r1 != 0) goto L7f
            if (r5 != 0) goto L7e
            android.view.HandwritingInitiator$State r12 = r0.mState
            if (r12 == 0) goto L7e
            r13 = 1
            android.view.HandwritingInitiator.State.m5663$$Nest$fputmStylusDownWithinEditorBounds(r12, r13)
        L7e:
            return r9
        L7f:
            int r1 = (r15 > r13 ? 1 : (r15 == r13 ? 0 : -1))
            if (r1 >= 0) goto L45
            r13 = r15
            r14 = r9
            goto L45
        L86:
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.HandwritingInitiator.findBestCandidateView(float, float, boolean):android.view.View");
    }

    private static float distance(Rect rect, float f, float f2) {
        float f3;
        boolean contains = contains(rect, f, f2, 0.0f, 0.0f, 0.0f, 0.0f);
        float f4 = 0.0f;
        if (contains) {
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
        float max = Math.max(view.getHandwritingBoundsOffsetLeft() + f, handwritingBoundsOffsetRight + 1.0f);
        float max2 = Math.max(view.getHandwritingBoundsOffsetTop() + f2, 1.0f + handwritingBoundsOffsetBottom);
        RectF rectF = this.mTempRectF;
        rectF.set(handwritingBoundsOffsetRight, handwritingBoundsOffsetBottom, max, max2);
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
        TextView findFirstTextViewDescendent = findFirstTextViewDescendent(view);
        if (findFirstTextViewDescendent != null) {
            findFirstTextViewDescendent.getCursorAnchorInfo(0, builder, this.mTempMatrix);
            if (findFirstTextViewDescendent.getSelectionStart() < 0) {
                float height = findFirstTextViewDescendent.getHeight() - findFirstTextViewDescendent.getExtendedPaddingBottom();
                builder.setInsertionMarkerLocation(findFirstTextViewDescendent.getCompoundPaddingStart(), findFirstTextViewDescendent.getExtendedPaddingTop(), height, height, 0);
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
            TextView findFirstTextViewDescendent = childAt instanceof TextView ? (TextView) childAt : findFirstTextViewDescendent(viewGroup.getChildAt(i));
            if (findFirstTextViewDescendent != null && findFirstTextViewDescendent.isAggregatedVisible() && (!TextUtils.isEmpty(findFirstTextViewDescendent.getText()) || !TextUtils.isEmpty(findFirstTextViewDescendent.getHint()))) {
                return findFirstTextViewDescendent;
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
