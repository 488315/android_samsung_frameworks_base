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
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.wm.shell.animation.FlingAnimationUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class ExpandHelper implements Gefingerpoken {
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
    public NotificationStackScrollLayout mEventSource;
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

    public interface Callback {
    }

    public class ViewScaler {
        public ExpandableNotificationRow mView;

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
        NotificationStackScrollLayout notificationStackScrollLayout = this.mEventSource;
        Callback callback = this.mCallback;
        if (notificationStackScrollLayout == null) {
            NotificationStackScrollLayout.AnonymousClass11 anonymousClass11 = (NotificationStackScrollLayout.AnonymousClass11) callback;
            anonymousClass11.getClass();
            boolean z = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
            return NotificationStackScrollLayout.this.getChildAtPosition(f, f2, true, true);
        }
        notificationStackScrollLayout.getLocationOnScreen(new int[2]);
        return NotificationStackScrollLayout.this.getChildAtRawPosition(f + r2[0], f2 + r2[1]);
    }

    public void finishExpanding(boolean z, float f) {
        finishExpanding(f, z, true);
    }

    public ObjectAnimator getScaleAnimation() {
        return this.mScaleAnimation;
    }

    public final boolean isInside(View view, float f, float f2) {
        if (view != null) {
            NotificationStackScrollLayout notificationStackScrollLayout = this.mEventSource;
            if (notificationStackScrollLayout != null) {
                notificationStackScrollLayout.getLocationOnScreen(new int[2]);
                f += r3[0];
                f2 += r3[1];
            }
            view.getLocationOnScreen(new int[2]);
            float f3 = f - r4[0];
            float f4 = f2 - r4[1];
            if (f3 > 0.0f && f4 > 0.0f) {
                if ((f3 < ((float) view.getWidth())) & (f4 < ((float) view.getHeight()))) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void maybeRecycleVelocityTracker(MotionEvent motionEvent) {
        if (this.mVelocityTracker != null) {
            if (motionEvent.getActionMasked() == 3 || motionEvent.getActionMasked() == 1) {
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0112  */
    @Override // com.android.systemui.Gefingerpoken
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        float yVelocity;
        if (!this.mEnabled) {
            return false;
        }
        trackVelocity(motionEvent);
        int action = motionEvent.getAction();
        this.mSGD.onTouchEvent(motionEvent);
        int focusX = (int) this.mSGD.getFocusX();
        float focusY = (int) this.mSGD.getFocusY();
        this.mInitialTouchFocusY = focusY;
        float currentSpan = this.mSGD.getCurrentSpan();
        this.mInitialTouchSpan = currentSpan;
        this.mLastFocusY = this.mInitialTouchFocusY;
        this.mLastSpanY = currentSpan;
        if (this.mExpanding) {
            this.mLastMotionY = motionEvent.getRawY();
            maybeRecycleVelocityTracker(motionEvent);
            return true;
        }
        if (action == 2 && (this.mExpansionStyle & 1) != 0) {
            return true;
        }
        int i = action & 255;
        if (i == 0) {
            NotificationStackScrollLayout.AnonymousClass9 anonymousClass9 = this.mScrollAdapter;
            if (anonymousClass9 != null) {
                if (isInside(NotificationStackScrollLayout.this, focusX, focusY)) {
                    NotificationStackScrollLayout.AnonymousClass9 anonymousClass92 = this.mScrollAdapter;
                    anonymousClass92.getClass();
                    int i2 = SceneContainerFlag.$r8$clinit;
                    boolean z = NotificationStackScrollLayout.this.getOwnScrollY() == 0;
                    this.mWatchingForPull = z;
                    ExpandableView expandableViewFindView$1 = findView$1(focusX, focusY);
                    this.mResizedView = expandableViewFindView$1;
                    if (expandableViewFindView$1 != null && !((NotificationStackScrollLayout.AnonymousClass11) this.mCallback).canChildBeExpanded(expandableViewFindView$1)) {
                        this.mResizedView = null;
                        this.mWatchingForPull = false;
                    }
                    this.mInitialTouchY = motionEvent.getRawY();
                    this.mInitialTouchX = motionEvent.getRawX();
                }
            }
        } else if (i == 1) {
            boolean z2 = motionEvent.getActionMasked() == 3;
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.computeCurrentVelocity(1000);
                yVelocity = this.mVelocityTracker.getYVelocity();
            } else {
                yVelocity = 0.0f;
            }
            finishExpanding(z2, yVelocity);
            this.mResizedView = null;
        } else if (i == 2) {
            float currentSpanX = this.mSGD.getCurrentSpanX();
            if (currentSpanX > this.mPullGestureMinXSpan && currentSpanX > this.mSGD.getCurrentSpanY() && !this.mExpanding) {
                startExpanding(this.mResizedView, 2);
                this.mWatchingForPull = false;
            }
            if (this.mWatchingForPull) {
                float rawY = motionEvent.getRawY() - this.mInitialTouchY;
                float rawX = motionEvent.getRawX() - this.mInitialTouchX;
                int classification = motionEvent.getClassification();
                int i3 = this.mTouchSlop;
                if (rawY > (classification == 1 ? i3 * this.mSlopMultiplier : i3) && rawY > Math.abs(rawX)) {
                    this.mWatchingForPull = false;
                    ExpandableView expandableView = this.mResizedView;
                    if (expandableView != null && ((expandableView.getIntrinsicHeight() != expandableView.getMaxContentHeight() || (expandableView.isSummaryWithChildren() && !expandableView.areChildrenExpanded())) && startExpanding(this.mResizedView, 1))) {
                        this.mLastMotionY = motionEvent.getRawY();
                        this.mInitialTouchY = motionEvent.getRawY();
                    }
                }
            }
        } else if (i == 3) {
        }
        this.mLastMotionY = motionEvent.getRawY();
        maybeRecycleVelocityTracker(motionEvent);
        return this.mExpanding;
    }

    public boolean startExpanding(ExpandableView expandableView, int i) {
        if (!(expandableView instanceof ExpandableNotificationRow)) {
            return false;
        }
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
        if (expandableNotificationRow.mEntry.isOngoingActivity() && expandableNotificationRow.mEntry.isPromotedState() && !expandableNotificationRow.mIsSummaryWithChildren) {
            return false;
        }
        this.mExpansionStyle = i;
        if (this.mExpanding && expandableView == this.mResizedView) {
            return true;
        }
        this.mExpanding = true;
        NotificationStackScrollLayout.AnonymousClass11 anonymousClass11 = (NotificationStackScrollLayout.AnonymousClass11) this.mCallback;
        anonymousClass11.expansionStateChanged(true);
        anonymousClass11.setUserLockedChild(expandableView, true);
        ViewScaler viewScaler = this.mScaler;
        viewScaler.mView = (ExpandableNotificationRow) expandableView;
        float f = expandableView.mActualHeight;
        this.mOldHeight = f;
        this.mCurrentHeight = f;
        if (anonymousClass11.canChildBeExpanded(expandableView)) {
            Callback callback = ExpandHelper.this.mCallback;
            ExpandableNotificationRow expandableNotificationRow2 = viewScaler.mView;
            ((NotificationStackScrollLayout.AnonymousClass11) callback).getClass();
            this.mNaturalHeight = expandableNotificationRow2.getMaxContentHeight();
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
        float fAbs = Math.abs(currentSpan) + Math.abs(focusY) + 1.0f;
        float fAbs2 = ((Math.abs(currentSpan) * currentSpan) / fAbs) + ((Math.abs(focusY) * focusY) / fAbs) + this.mOldHeight;
        float f = this.mSmallSize;
        if (fAbs2 < f) {
            fAbs2 = f;
        }
        float f2 = this.mNaturalHeight;
        if (fAbs2 > f2) {
            fAbs2 = f2;
        }
        ViewScaler viewScaler = this.mScaler;
        viewScaler.mView.setActualHeight((int) fAbs2, true);
        ExpandHelper.this.mCurrentHeight = fAbs2;
        this.mLastFocusY = this.mSGD.getFocusY();
        this.mLastSpanY = this.mSGD.getCurrentSpan();
    }

    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v9 */
    public final void finishExpanding(float f, boolean z, boolean z2) {
        final boolean z3;
        ?? r3;
        boolean z4;
        float f2;
        int i = 0;
        boolean z5 = true;
        if (this.mExpanding) {
            ViewScaler viewScaler = this.mScaler;
            float f3 = viewScaler.mView.mActualHeight;
            float f4 = this.mOldHeight;
            float f5 = this.mSmallSize;
            final boolean z6 = f4 == f5;
            float f6 = 0.0f;
            if (z) {
                z3 = !z6;
            } else {
                z3 = (!z6 ? !(f3 >= f4 || f > 0.0f) : !(f3 > f4 && f >= 0.0f)) | (this.mNaturalHeight == f5);
            }
            ObjectAnimator objectAnimator = this.mScaleAnimation;
            if (objectAnimator.mRunning) {
                objectAnimator.cancel();
            }
            NotificationStackScrollLayout.AnonymousClass11 anonymousClass11 = (NotificationStackScrollLayout.AnonymousClass11) this.mCallback;
            anonymousClass11.expansionStateChanged(false);
            ExpandHelper expandHelper = ExpandHelper.this;
            Callback callback = expandHelper.mCallback;
            ExpandableNotificationRow expandableNotificationRow = viewScaler.mView;
            ((NotificationStackScrollLayout.AnonymousClass11) callback).getClass();
            int maxContentHeight = expandableNotificationRow.getMaxContentHeight();
            if (!z3) {
                maxContentHeight = this.mSmallSize;
            }
            float f7 = maxContentHeight;
            if (f7 != f3 && this.mEnabled && z2) {
                objectAnimator.setFloatValues(f7);
                objectAnimator.initAnimation$1();
                Object target = objectAnimator.getTarget();
                if (target != null) {
                    int length = objectAnimator.mValues.length;
                    int i2 = 0;
                    while (i2 < length) {
                        PropertyValuesHolder propertyValuesHolder = objectAnimator.mValues[i2];
                        List list = ((KeyframeSet) propertyValuesHolder.mKeyframes).mKeyframes;
                        if (list.isEmpty()) {
                            z4 = z5;
                            f2 = f6;
                        } else {
                            Keyframe keyframe = (Keyframe) list.get(i);
                            f2 = f6;
                            Property property = propertyValuesHolder.mProperty;
                            if (property != null) {
                                keyframe.setValue(property.get(target));
                                z4 = z5;
                            } else {
                                try {
                                    if (propertyValuesHolder.mGetter == null) {
                                        z4 = z5;
                                        try {
                                            Method method = propertyValuesHolder.setupSetterOrGetter(target.getClass(), PropertyValuesHolder.sGetterPropertyMap, "get", null);
                                            propertyValuesHolder.mGetter = method;
                                            if (method == null) {
                                            }
                                        } catch (IllegalAccessException e) {
                                            e = e;
                                            Log.e("PropertyValuesHolder", e.toString());
                                            i2++;
                                            f6 = f2;
                                            z5 = z4;
                                            i = 0;
                                        } catch (InvocationTargetException e2) {
                                            e = e2;
                                            Log.e("PropertyValuesHolder", e.toString());
                                            i2++;
                                            f6 = f2;
                                            z5 = z4;
                                            i = 0;
                                        }
                                    } else {
                                        z4 = z5;
                                    }
                                    keyframe.setValue(propertyValuesHolder.mGetter.invoke(target, null));
                                } catch (IllegalAccessException e3) {
                                    e = e3;
                                    z4 = z5;
                                } catch (InvocationTargetException e4) {
                                    e = e4;
                                    z4 = z5;
                                }
                            }
                        }
                        i2++;
                        f6 = f2;
                        z5 = z4;
                        i = 0;
                    }
                }
                boolean z7 = z5;
                float f8 = f6;
                final ExpandableView expandableView = this.mResizedView;
                objectAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.ExpandHelper.3
                    public boolean mCancelled;

                    @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        this.mCancelled = true;
                    }

                    @Override // androidx.core.animation.AnimatorListenerAdapter, androidx.core.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        boolean z8 = this.mCancelled;
                        ExpandHelper expandHelper2 = ExpandHelper.this;
                        if (z8) {
                            Callback callback2 = expandHelper2.mCallback;
                            View view = expandableView;
                            ((NotificationStackScrollLayout.AnonymousClass11) callback2).getClass();
                            if (view instanceof ExpandableNotificationRow) {
                                ((ExpandableNotificationRow) view).mGroupExpansionChanging = false;
                            }
                        } else {
                            ((NotificationStackScrollLayout.AnonymousClass11) expandHelper2.mCallback).setUserExpandedChild(expandableView, z3);
                            if (!expandHelper2.mExpanding) {
                                expandHelper2.mScaler.mView = null;
                            }
                        }
                        ((NotificationStackScrollLayout.AnonymousClass11) expandHelper2.mCallback).setUserLockedChild(expandableView, false);
                        expandHelper2.mScaleAnimation.removeListener(this);
                        if (z6) {
                            InteractionJankMonitor.getInstance().end(3);
                        }
                    }
                });
                float f9 = z3 == ((f > f8 ? 1 : (f == f8 ? 0 : -1)) >= 0 ? z7 : false) ? f : f8;
                FlingAnimationUtils flingAnimationUtils = this.mFlingAnimationUtils;
                flingAnimationUtils.getClass();
                FlingAnimationUtils.AnimatorProperties properties = flingAnimationUtils.getProperties(f3, f7, f9, Math.abs(f7 - f3));
                objectAnimator.setDuration(properties.mDuration);
                final Interpolator interpolator = properties.mInterpolator;
                Objects.requireNonNull(interpolator);
                objectAnimator.mInterpolator = new androidx.core.animation.Interpolator() { // from class: com.android.wm.shell.animation.FlingAnimationUtils$AnimatorProperties$$ExternalSyntheticLambda0
                    @Override // androidx.core.animation.Interpolator
                    public final float getInterpolation(float f10) {
                        return interpolator.getInterpolation(f10);
                    }
                };
                objectAnimator.start();
                r3 = 0;
            } else {
                if (f7 != f3) {
                    viewScaler.mView.setActualHeight((int) f7, true);
                    expandHelper.mCurrentHeight = f7;
                }
                anonymousClass11.setUserExpandedChild(this.mResizedView, z3);
                r3 = 0;
                r3 = 0;
                anonymousClass11.setUserLockedChild(this.mResizedView, false);
                viewScaler.mView = null;
                if (z6) {
                    InteractionJankMonitor.getInstance().end(3);
                }
            }
            this.mExpanding = r3;
            this.mExpansionStyle = r3;
        }
    }
}
