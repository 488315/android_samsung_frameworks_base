package com.android.systemui;

import android.content.Context;
import android.util.FloatProperty;
import android.util.Log;
import android.util.Property;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.Keyframe;
import androidx.core.animation.KeyframeSet;
import androidx.core.animation.ObjectAnimator;
import androidx.core.animation.PropertyValuesHolder;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.wm.shell.animation.FlingAnimationUtils;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ExpandHelper implements Gefingerpoken {
    public static final AnonymousClass1 VIEW_SCALER_HEIGHT_PROPERTY = new FloatProperty("ViewScalerHeight") { // from class: com.android.systemui.ExpandHelper.1
        @Override // android.util.Property
        public final Float get(Object obj) {
            return Float.valueOf(((ViewScaler) obj).mView.mActualHeight);
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            ViewScaler viewScaler = (ViewScaler) obj;
            viewScaler.mView.setActualHeight((int) f, true);
            ExpandHelper.this.mCurrentHeight = f;
        }
    };
    public final Callback mCallback;
    public final Context mContext;
    public float mCurrentHeight;
    public View mEventSource;
    public boolean mExpanding;
    public final FlingAnimationUtils mFlingAnimationUtils;
    public final int mGravity;
    public float mInitialTouchFocusY;
    public float mInitialTouchSpan;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public float mLastFocusY;
    public float mLastMotionY;
    public float mLastSpanY;
    public float mNaturalHeight;
    public float mOldHeight;
    public boolean mOnlyMovements;
    public final float mPullGestureMinXSpan;
    public ExpandableView mResizedView;
    public ScaleGestureDetector mSGD;
    public final ObjectAnimator mScaleAnimation;
    public final AnonymousClass2 mScaleGestureListener;
    public final ViewScaler mScaler;
    public NotificationStackScrollLayout.AnonymousClass9 mScrollAdapter;
    public final float mSlopMultiplier;
    public int mSmallSize;
    public final int mTouchSlop;
    public VelocityTracker mVelocityTracker;
    public boolean mWatchingForPull;
    public int mExpansionStyle = 0;
    public boolean mEnabled = true;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ViewScaler {
        public ExpandableView mView;

        public ViewScaler() {
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [android.view.ScaleGestureDetector$OnScaleGestureListener, com.android.systemui.ExpandHelper$2] */
    public ExpandHelper(Context context, Callback callback, int i, int i2) {
        ?? r1 = new ScaleGestureDetector.SimpleOnScaleGestureListener() { // from class: com.android.systemui.ExpandHelper.2
            @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
            public final boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                return true;
            }

            @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
            public final boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                ExpandHelper expandHelper = ExpandHelper.this;
                if (!expandHelper.mOnlyMovements) {
                    expandHelper.startExpanding(expandHelper.mResizedView, 4);
                }
                return ExpandHelper.this.mExpanding;
            }

            @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
            public final void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            }
        };
        this.mScaleGestureListener = r1;
        this.mSmallSize = i;
        this.mContext = context;
        this.mCallback = callback;
        ViewScaler viewScaler = new ViewScaler();
        this.mScaler = viewScaler;
        this.mGravity = 48;
        this.mScaleAnimation = ObjectAnimator.ofFloat(viewScaler, VIEW_SCALER_HEIGHT_PROPERTY, 0.0f);
        this.mPullGestureMinXSpan = context.getResources().getDimension(R.dimen.pull_span_min);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mSlopMultiplier = ViewConfiguration.getAmbiguousGestureMultiplier();
        this.mSGD = new ScaleGestureDetector(context, r1);
        this.mFlingAnimationUtils = new FlingAnimationUtils(context.getResources().getDisplayMetrics(), 0.3f);
    }

    public final ExpandableView findView$1(float f, float f2) {
        View view = this.mEventSource;
        Callback callback = this.mCallback;
        if (view == null) {
            NotificationStackScrollLayout.AnonymousClass15 anonymousClass15 = (NotificationStackScrollLayout.AnonymousClass15) callback;
            anonymousClass15.getClass();
            boolean z = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
            return NotificationStackScrollLayout.this.getChildAtPosition(f, f2, true, true);
        }
        view.getLocationOnScreen(new int[2]);
        return NotificationStackScrollLayout.this.getChildAtRawPosition(f + r2[0], f2 + r2[1]);
    }

    public void finishExpanding(boolean z, float f) {
        finishExpanding(f, z, true);
    }

    public ObjectAnimator getScaleAnimation() {
        return this.mScaleAnimation;
    }

    public final boolean isInside(View view, float f, float f2) {
        if (view == null) {
            return false;
        }
        View view2 = this.mEventSource;
        if (view2 != null) {
            view2.getLocationOnScreen(new int[2]);
            f += r3[0];
            f2 += r3[1];
        }
        view.getLocationOnScreen(new int[2]);
        float f3 = f - r4[0];
        float f4 = f2 - r4[1];
        if (f3 <= 0.0f || f4 <= 0.0f) {
            return false;
        }
        return ((f3 > ((float) view.getWidth()) ? 1 : (f3 == ((float) view.getWidth()) ? 0 : -1)) < 0) & ((f4 > ((float) view.getHeight()) ? 1 : (f4 == ((float) view.getHeight()) ? 0 : -1)) < 0);
    }

    public final void maybeRecycleVelocityTracker(MotionEvent motionEvent) {
        if (this.mVelocityTracker != null) {
            if (motionEvent.getActionMasked() == 3 || motionEvent.getActionMasked() == 1) {
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0053, code lost:
    
        if (r0 != 3) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0110, code lost:
    
        if (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout.this.mOwnScrollY == 0) goto L66;
     */
    @Override // com.android.systemui.Gefingerpoken
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onInterceptTouchEvent(android.view.MotionEvent r8) {
        /*
            Method dump skipped, instructions count: 325
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.ExpandHelper.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // com.android.systemui.Gefingerpoken
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        float f;
        if (!this.mEnabled && !this.mExpanding) {
            return false;
        }
        trackVelocity(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        this.mSGD.onTouchEvent(motionEvent);
        int focusX = (int) this.mSGD.getFocusX();
        int focusY = (int) this.mSGD.getFocusY();
        if (this.mOnlyMovements) {
            this.mLastMotionY = motionEvent.getRawY();
            return false;
        }
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    if (this.mWatchingForPull) {
                        float rawY = motionEvent.getRawY() - this.mInitialTouchY;
                        float rawX = motionEvent.getRawX() - this.mInitialTouchX;
                        int classification = motionEvent.getClassification();
                        int i = this.mTouchSlop;
                        if (rawY > (classification == 1 ? i * this.mSlopMultiplier : i) && rawY > Math.abs(rawX)) {
                            this.mWatchingForPull = false;
                            ExpandableView expandableView = this.mResizedView;
                            if (expandableView != null && ((expandableView.getIntrinsicHeight() != expandableView.getMaxContentHeight() || (expandableView.isSummaryWithChildren() && !expandableView.areChildrenExpanded())) && startExpanding(this.mResizedView, 1))) {
                                this.mInitialTouchY = motionEvent.getRawY();
                                this.mLastMotionY = motionEvent.getRawY();
                            }
                        }
                    }
                    boolean z2 = this.mExpanding;
                    if (z2 && (this.mExpansionStyle & 1) != 0) {
                        float rawY2 = (motionEvent.getRawY() - this.mLastMotionY) + this.mCurrentHeight;
                        int i2 = this.mSmallSize;
                        float f2 = i2;
                        if (rawY2 >= f2) {
                            f2 = rawY2;
                        }
                        float f3 = this.mNaturalHeight;
                        if (f2 > f3) {
                            f2 = f3;
                        }
                        boolean z3 = rawY2 > f3;
                        if (rawY2 < i2) {
                            z3 = true;
                        }
                        ViewScaler viewScaler = this.mScaler;
                        viewScaler.mView.setActualHeight((int) f2, true);
                        ExpandHelper.this.mCurrentHeight = f2;
                        this.mLastMotionY = motionEvent.getRawY();
                        Callback callback = this.mCallback;
                        if (z3) {
                            ((NotificationStackScrollLayout.AnonymousClass15) callback).expansionStateChanged(false);
                        } else {
                            ((NotificationStackScrollLayout.AnonymousClass15) callback).expansionStateChanged(true);
                        }
                        return true;
                    }
                    if (z2) {
                        updateExpansion();
                        this.mLastMotionY = motionEvent.getRawY();
                        return true;
                    }
                } else if (actionMasked != 3) {
                    if (actionMasked == 5 || actionMasked == 6) {
                        this.mInitialTouchY = (this.mSGD.getFocusY() - this.mLastFocusY) + this.mInitialTouchY;
                        this.mInitialTouchSpan = (this.mSGD.getCurrentSpan() - this.mLastSpanY) + this.mInitialTouchSpan;
                    }
                }
            }
            boolean z4 = !this.mEnabled || motionEvent.getActionMasked() == 3;
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.computeCurrentVelocity(1000);
                f = this.mVelocityTracker.getYVelocity();
            } else {
                f = 0.0f;
            }
            finishExpanding(z4, f);
            this.mResizedView = null;
        } else {
            NotificationStackScrollLayout.AnonymousClass9 anonymousClass9 = this.mScrollAdapter;
            if (anonymousClass9 != null) {
                if (isInside(NotificationStackScrollLayout.this, focusX, focusY)) {
                    z = true;
                    this.mWatchingForPull = z;
                    this.mResizedView = findView$1(focusX, focusY);
                    this.mInitialTouchX = motionEvent.getRawX();
                    this.mInitialTouchY = motionEvent.getRawY();
                }
            }
            z = false;
            this.mWatchingForPull = z;
            this.mResizedView = findView$1(focusX, focusY);
            this.mInitialTouchX = motionEvent.getRawX();
            this.mInitialTouchY = motionEvent.getRawY();
        }
        this.mLastMotionY = motionEvent.getRawY();
        maybeRecycleVelocityTracker(motionEvent);
        return this.mResizedView != null;
    }

    public boolean startExpanding(ExpandableView expandableView, int i) {
        if (!(expandableView instanceof ExpandableNotificationRow)) {
            return false;
        }
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
        if (expandableNotificationRow.mEntry.isOngoingAcitivty() && expandableNotificationRow.mEntry.isPromotedState()) {
            return false;
        }
        this.mExpansionStyle = i;
        if (this.mExpanding && expandableView == this.mResizedView) {
            return true;
        }
        this.mExpanding = true;
        NotificationStackScrollLayout.AnonymousClass15 anonymousClass15 = (NotificationStackScrollLayout.AnonymousClass15) this.mCallback;
        anonymousClass15.expansionStateChanged(true);
        anonymousClass15.setUserLockedChild(expandableView, true);
        ViewScaler viewScaler = this.mScaler;
        viewScaler.mView = expandableView;
        float f = expandableView.mActualHeight;
        this.mOldHeight = f;
        this.mCurrentHeight = f;
        if (anonymousClass15.canChildBeExpanded(expandableView)) {
            Callback callback = ExpandHelper.this.mCallback;
            ExpandableView expandableView2 = viewScaler.mView;
            ((NotificationStackScrollLayout.AnonymousClass15) callback).getClass();
            this.mNaturalHeight = expandableView2.getMaxContentHeight();
            this.mSmallSize = expandableView.getCollapsedHeight();
        } else {
            this.mNaturalHeight = this.mOldHeight;
        }
        InteractionJankMonitor.getInstance().begin(expandableView, 3);
        return true;
    }

    public final void trackVelocity(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 2) {
                return;
            }
            if (this.mVelocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            }
            this.mVelocityTracker.addMovement(motionEvent);
            return;
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
        this.mVelocityTracker.addMovement(motionEvent);
    }

    public void updateExpansion() {
        float currentSpan = (this.mSGD.getCurrentSpan() - this.mInitialTouchSpan) * 1.0f;
        float focusY = (this.mSGD.getFocusY() - this.mInitialTouchFocusY) * 1.0f * (this.mGravity == 80 ? -1.0f : 1.0f);
        float abs = Math.abs(currentSpan) + Math.abs(focusY) + 1.0f;
        float abs2 = ((Math.abs(currentSpan) * currentSpan) / abs) + ((Math.abs(focusY) * focusY) / abs) + this.mOldHeight;
        float f = this.mSmallSize;
        if (abs2 < f) {
            abs2 = f;
        }
        float f2 = this.mNaturalHeight;
        if (abs2 > f2) {
            abs2 = f2;
        }
        ViewScaler viewScaler = this.mScaler;
        viewScaler.mView.setActualHeight((int) abs2, true);
        ExpandHelper.this.mCurrentHeight = abs2;
        this.mLastFocusY = this.mSGD.getFocusY();
        this.mLastSpanY = this.mSGD.getCurrentSpan();
    }

    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v8 */
    public final void finishExpanding(float f, boolean z, boolean z2) {
        final boolean z3;
        ?? r3;
        int i;
        int i2 = 0;
        int i3 = 1;
        if (this.mExpanding) {
            ViewScaler viewScaler = this.mScaler;
            float f2 = viewScaler.mView.mActualHeight;
            float f3 = this.mOldHeight;
            float f4 = this.mSmallSize;
            final boolean z4 = f3 == f4;
            if (z) {
                z3 = !z4;
            } else {
                z3 = (!z4 ? !(f2 >= f3 || f > 0.0f) : !(f2 > f3 && f >= 0.0f)) | (this.mNaturalHeight == f4);
            }
            ObjectAnimator objectAnimator = this.mScaleAnimation;
            if (objectAnimator.mRunning) {
                objectAnimator.cancel();
            }
            NotificationStackScrollLayout.AnonymousClass15 anonymousClass15 = (NotificationStackScrollLayout.AnonymousClass15) this.mCallback;
            anonymousClass15.expansionStateChanged(false);
            ExpandHelper expandHelper = ExpandHelper.this;
            Callback callback = expandHelper.mCallback;
            ExpandableView expandableView = viewScaler.mView;
            ((NotificationStackScrollLayout.AnonymousClass15) callback).getClass();
            int maxContentHeight = expandableView.getMaxContentHeight();
            if (!z3) {
                maxContentHeight = this.mSmallSize;
            }
            float f5 = maxContentHeight;
            if (f5 != f2 && this.mEnabled && z2) {
                objectAnimator.setFloatValues(f5);
                objectAnimator.initAnimation$1();
                WeakReference weakReference = objectAnimator.mTarget;
                Object obj = weakReference == null ? null : weakReference.get();
                if (obj != null) {
                    int length = objectAnimator.mValues.length;
                    int i4 = 0;
                    while (i4 < length) {
                        PropertyValuesHolder propertyValuesHolder = objectAnimator.mValues[i4];
                        List list = ((KeyframeSet) propertyValuesHolder.mKeyframes).mKeyframes;
                        if (list.isEmpty()) {
                            i = i3;
                        } else {
                            Keyframe keyframe = (Keyframe) list.get(i2);
                            Property property = propertyValuesHolder.mProperty;
                            if (property != null) {
                                keyframe.setValue(property.get(obj));
                            } else {
                                try {
                                    if (propertyValuesHolder.mGetter == null) {
                                        Method method = propertyValuesHolder.setupSetterOrGetter(obj.getClass(), PropertyValuesHolder.sGetterPropertyMap, "get", null);
                                        propertyValuesHolder.mGetter = method;
                                        if (method == null) {
                                        }
                                    }
                                    keyframe.setValue(propertyValuesHolder.mGetter.invoke(obj, null));
                                } catch (IllegalAccessException e) {
                                    Log.e("PropertyValuesHolder", e.toString());
                                } catch (InvocationTargetException e2) {
                                    Log.e("PropertyValuesHolder", e2.toString());
                                }
                            }
                            i = 1;
                        }
                        i4 += i;
                        i3 = i;
                        i2 = 0;
                    }
                }
                final ExpandableView expandableView2 = this.mResizedView;
                objectAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.ExpandHelper.3
                    public boolean mCancelled;

                    @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        this.mCancelled = true;
                    }

                    @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        boolean z5 = this.mCancelled;
                        ExpandHelper expandHelper2 = ExpandHelper.this;
                        if (z5) {
                            Callback callback2 = expandHelper2.mCallback;
                            View view = expandableView2;
                            ((NotificationStackScrollLayout.AnonymousClass15) callback2).getClass();
                            if (view instanceof ExpandableNotificationRow) {
                                ((ExpandableNotificationRow) view).mGroupExpansionChanging = false;
                            }
                        } else {
                            ((NotificationStackScrollLayout.AnonymousClass15) expandHelper2.mCallback).setUserExpandedChild(expandableView2, z3);
                            if (!expandHelper2.mExpanding) {
                                expandHelper2.mScaler.mView = null;
                            }
                        }
                        ((NotificationStackScrollLayout.AnonymousClass15) expandHelper2.mCallback).setUserLockedChild(expandableView2, false);
                        expandHelper2.mScaleAnimation.removeListener(this);
                        if (z4) {
                            InteractionJankMonitor.getInstance().end(3);
                        }
                    }
                });
                float f6 = z3 == ((f > 0.0f ? 1 : (f == 0.0f ? 0 : -1)) >= 0) ? f : 0.0f;
                FlingAnimationUtils flingAnimationUtils = this.mFlingAnimationUtils;
                flingAnimationUtils.getClass();
                FlingAnimationUtils.AnimatorProperties properties = flingAnimationUtils.getProperties(f2, f5, f6, Math.abs(f5 - f2));
                objectAnimator.setDuration(properties.mDuration);
                final Interpolator interpolator = properties.mInterpolator;
                Objects.requireNonNull(interpolator);
                objectAnimator.mInterpolator = new androidx.core.animation.Interpolator() { // from class: com.android.wm.shell.animation.FlingAnimationUtils$AnimatorProperties$$ExternalSyntheticLambda0
                    @Override // androidx.core.animation.Interpolator
                    public final float getInterpolation(float f7) {
                        return interpolator.getInterpolation(f7);
                    }
                };
                objectAnimator.start();
                r3 = 0;
            } else {
                if (f5 != f2) {
                    viewScaler.mView.setActualHeight((int) f5, true);
                    expandHelper.mCurrentHeight = f5;
                }
                anonymousClass15.setUserExpandedChild(this.mResizedView, z3);
                r3 = 0;
                r3 = 0;
                anonymousClass15.setUserLockedChild(this.mResizedView, false);
                viewScaler.mView = null;
                if (z4) {
                    InteractionJankMonitor.getInstance().end(3);
                }
            }
            this.mExpanding = r3;
            this.mExpansionStyle = r3;
        }
    }
}
