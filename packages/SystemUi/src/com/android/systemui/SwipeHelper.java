package com.android.systemui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Property;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.PathInterpolator;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.app.animation.Interpolators;
import com.android.p038wm.shell.animation.FlingAnimationUtils;
import com.android.p038wm.shell.animation.PhysicsAnimator;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import java.io.PrintWriter;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SwipeHelper implements Gefingerpoken, Dumpable {
    public boolean mAlreadyExecutedDragAndDrop;
    public final Callback mCallback;
    public boolean mCanCurrViewBeDimissed;
    public float mDensityScale;
    public final boolean mFadeDependingOnAmountSwiped;
    public final FalsingManager mFalsingManager;
    public final int mFalsingThreshold;
    public final FeatureFlags mFeatureFlags;
    public final FlingAnimationUtils mFlingAnimationUtils;
    public float mInitialTouchPos;
    public float mInitialTouchPosY;
    public boolean mIsSwiping;
    public boolean mLongPressSent;
    public boolean mMenuRowIntercepting;
    public float mPagingTouchSlop;
    public float mPerpendicularInitialTouchPos;
    public final float mSlopMultiplier;
    public boolean mSnappingChild;
    public boolean mTouchAboveFalsingThreshold;
    public final int mTouchSlop;
    public ExpandableView mTouchedView;
    public final float mMaxSwipeProgress = 1.0f;
    public final PhysicsAnimator.SpringConfig mSnapBackSpringConfig = new PhysicsAnimator.SpringConfig(200.0f, 0.75f);
    public float mTranslation = 0.0f;
    public final float[] mDownLocation = new float[2];
    public final RunnableC09851 mPerformLongPress = new RunnableC09851();
    public final ArrayMap mDismissPendingMap = new ArrayMap();
    public final Handler mHandler = new Handler();
    public final VelocityTracker mVelocityTracker = VelocityTracker.obtain();
    public final float mTouchSlopMultiplier = ViewConfiguration.getAmbiguousGestureMultiplier();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.SwipeHelper$1 */
    public final class RunnableC09851 implements Runnable {
        public final int[] mViewOffset = new int[2];

        public RunnableC09851() {
        }

        @Override // java.lang.Runnable
        public final void run() {
            SwipeHelper swipeHelper = SwipeHelper.this;
            ExpandableView expandableView = swipeHelper.mTouchedView;
            if (expandableView == null || swipeHelper.mLongPressSent) {
                return;
            }
            swipeHelper.mLongPressSent = true;
            if (expandableView instanceof ExpandableNotificationRow) {
                expandableView.getLocationOnScreen(this.mViewOffset);
                SwipeHelper swipeHelper2 = SwipeHelper.this;
                float[] fArr = swipeHelper2.mDownLocation;
                int i = (int) fArr[0];
                int[] iArr = this.mViewOffset;
                int i2 = i - iArr[0];
                int i3 = ((int) fArr[1]) - iArr[1];
                swipeHelper2.mTouchedView.sendAccessibilityEvent(2);
                ((ExpandableNotificationRow) SwipeHelper.this.mTouchedView).doLongClickCallback(i2, i3);
                SwipeHelper swipeHelper3 = SwipeHelper.this;
                if (swipeHelper3.isAvailableToDragAndDrop(swipeHelper3.mTouchedView)) {
                    SwipeHelper swipeHelper4 = SwipeHelper.this;
                    NotificationStackScrollLayoutController.this.mLongPressedView = swipeHelper4.mTouchedView;
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
    }

    public SwipeHelper(Callback callback, Resources resources, ViewConfiguration viewConfiguration, FalsingManager falsingManager, FeatureFlags featureFlags) {
        this.mCallback = callback;
        this.mPagingTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        this.mSlopMultiplier = viewConfiguration.getScaledAmbiguousGestureMultiplier();
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        ViewConfiguration.getLongPressTimeout();
        this.mDensityScale = resources.getDisplayMetrics().density;
        this.mFalsingThreshold = resources.getDimensionPixelSize(R.dimen.swipe_helper_falsing_threshold);
        this.mFadeDependingOnAmountSwiped = resources.getBoolean(R.bool.config_fadeDependingOnAmountSwiped);
        this.mFalsingManager = falsingManager;
        this.mFeatureFlags = featureFlags;
        this.mFlingAnimationUtils = new FlingAnimationUtils(resources.getDisplayMetrics(), 400 / 1000.0f);
    }

    public final void cancelLongPress() {
        this.mHandler.removeCallbacks(this.mPerformLongPress);
    }

    public Animator createTranslationAnimation(View view, float f, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_X, f);
        if (animatorUpdateListener != null) {
            ofFloat.addUpdateListener(animatorUpdateListener);
        }
        return ofFloat;
    }

    public void dismissChild(View view, float f, boolean z) {
        dismissChild(view, f, null, 0L, z, 0L, false);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(final PrintWriter printWriter, String[] strArr) {
        printWriter.append("mTouchedView=").print(this.mTouchedView);
        if (this.mTouchedView instanceof ExpandableNotificationRow) {
            PrintWriter append = printWriter.append(" key=");
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mTouchedView;
            append.println(expandableNotificationRow == null ? "null" : NotificationUtils.logKey(expandableNotificationRow.mEntry));
        } else {
            printWriter.println();
        }
        printWriter.append("mIsSwiping=").println(this.mIsSwiping);
        printWriter.append("mSnappingChild=").println(this.mSnappingChild);
        printWriter.append("mLongPressSent=").println(this.mLongPressSent);
        printWriter.append("mInitialTouchPos=").println(this.mInitialTouchPos);
        printWriter.append("mTranslation=").println(this.mTranslation);
        printWriter.append("mCanCurrViewBeDimissed=").println(this.mCanCurrViewBeDimissed);
        printWriter.append("mMenuRowIntercepting=").println(this.mMenuRowIntercepting);
        printWriter.append("mDisableHwLayers=").println(false);
        PrintWriter append2 = printWriter.append("mDismissPendingMap: ");
        ArrayMap arrayMap = this.mDismissPendingMap;
        append2.println(arrayMap.size());
        if (arrayMap.isEmpty()) {
            return;
        }
        arrayMap.forEach(new BiConsumer() { // from class: com.android.systemui.SwipeHelper$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PrintWriter printWriter2 = printWriter;
                printWriter2.append((CharSequence) "  ").print((View) obj);
                printWriter2.append((CharSequence) ": ").println((Animator) obj2);
            }
        });
    }

    public float getEscapeVelocity() {
        return 500.0f * this.mDensityScale;
    }

    public float getMinDismissVelocity() {
        return getEscapeVelocity();
    }

    public float getSwipeAlpha(float f) {
        return this.mFadeDependingOnAmountSwiped ? Math.max(1.0f - f, 0.0f) : 1.0f - Math.max(0.0f, Math.min(1.0f, f / 0.6f));
    }

    public float getTotalTranslationLength(View view) {
        return view.getMeasuredWidth();
    }

    public float getTranslation(View view) {
        return view.getTranslationX();
    }

    public Animator getViewTranslationAnimator(View view, float f, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        PhysicsAnimator.getInstance(view).cancel();
        return view instanceof ExpandableNotificationRow ? ((ExpandableNotificationRow) view).getTranslateViewAnimator(f, animatorUpdateListener) : createTranslationAnimation(view, f, animatorUpdateListener);
    }

    public boolean handleUpEvent(float f, MotionEvent motionEvent, View view) {
        return false;
    }

    public final boolean isAvailableToDragAndDrop(View view) {
        if (!((FeatureFlagsRelease) this.mFeatureFlags).isEnabled(Flags.NOTIFICATION_DRAG_TO_CONTENTS) || !(view instanceof ExpandableNotificationRow)) {
            return false;
        }
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
        boolean canBubble = expandableNotificationRow.mEntry.canBubble();
        Notification notification2 = expandableNotificationRow.mEntry.mSbn.getNotification();
        PendingIntent pendingIntent = notification2.contentIntent;
        if (pendingIntent == null) {
            pendingIntent = notification2.fullScreenIntent;
        }
        return (pendingIntent == null || canBubble) ? false : true;
    }

    public final boolean isDismissGesture(MotionEvent motionEvent) {
        getTranslation(this.mTouchedView);
        if (motionEvent.getActionMasked() == 1 && !this.mFalsingManager.isUnlockingDisabled() && !isFalseGesture() && (swipedFastEnough() || swipedFarEnough())) {
            if (((NotificationStackScrollLayoutController.C29679) this.mCallback).canChildBeDismissed(this.mTouchedView)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isFalseGesture() {
        boolean onKeyguard = NotificationStackScrollLayoutController.this.mView.onKeyguard();
        FalsingManager falsingManager = this.mFalsingManager;
        if (falsingManager.isClassifierEnabled()) {
            if (onKeyguard && falsingManager.isFalseTouch(1)) {
                return true;
            }
        } else if (onKeyguard && !this.mTouchAboveFalsingThreshold) {
            return true;
        }
        return false;
    }

    public void onChildSnappedBack(View view, float f) {
        NotificationStackScrollLayoutController.C29679 c29679 = (NotificationStackScrollLayoutController.C29679) this.mCallback;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.updateFirstAndLastBackgroundViews();
        notificationStackScrollLayout.mController.mNotificationRoundnessManager.getClass();
        notificationStackScrollLayout.mShelf.updateAppearance();
        if (view instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            if (expandableNotificationRow.mIsPinned && !c29679.canChildBeDismissed(expandableNotificationRow) && expandableNotificationRow.mEntry.mSbn.getNotification().fullScreenIntent == null) {
                notificationStackScrollLayoutController.mHeadsUpManager.removeNotification(expandableNotificationRow.mEntry.mSbn.getKey(), true);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002a, code lost:
    
        if (r0 != 3) goto L48;
     */
    @Override // com.android.systemui.Gefingerpoken
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        NotificationMenuRowPlugin notificationMenuRowPlugin;
        ExpandableView expandableView = this.mTouchedView;
        if ((expandableView instanceof ExpandableNotificationRow) && (notificationMenuRowPlugin = ((ExpandableNotificationRow) expandableView).mMenuRow) != null) {
            this.mMenuRowIntercepting = notificationMenuRowPlugin.onInterceptTouchEvent(expandableView, motionEvent);
        }
        int action = motionEvent.getAction();
        Handler handler = this.mHandler;
        RunnableC09851 runnableC09851 = this.mPerformLongPress;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        Callback callback = this.mCallback;
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    if (this.mTouchedView != null && !this.mLongPressSent) {
                        velocityTracker.addMovement(motionEvent);
                        float x = motionEvent.getX();
                        float y = motionEvent.getY();
                        float f = x - this.mInitialTouchPos;
                        float f2 = y - this.mPerpendicularInitialTouchPos;
                        if (Math.abs(f) > (motionEvent.getClassification() == 1 ? this.mPagingTouchSlop * this.mSlopMultiplier : this.mPagingTouchSlop) && Math.abs(f) > Math.abs(f2)) {
                            callback.getClass();
                            this.mIsSwiping = true;
                            ((NotificationStackScrollLayoutController.C29679) callback).onBeginDrag(this.mTouchedView);
                            this.mInitialTouchPos = motionEvent.getX();
                            this.mTranslation = getTranslation(this.mTouchedView);
                            cancelLongPress();
                        } else if (motionEvent.getClassification() == 2 && handler.hasCallbacks(runnableC09851)) {
                            cancelLongPress();
                            runnableC09851.run();
                        }
                    }
                }
            }
            boolean z = this.mIsSwiping || this.mLongPressSent || this.mMenuRowIntercepting;
            this.mLongPressSent = false;
            NotificationStackScrollLayoutController.this.mLongPressedView = null;
            this.mMenuRowIntercepting = false;
            resetSwipeStates(false);
            cancelLongPress();
            if (z) {
                return true;
            }
        } else {
            this.mTouchAboveFalsingThreshold = false;
            this.mIsSwiping = false;
            this.mSnappingChild = false;
            this.mLongPressSent = false;
            this.mAlreadyExecutedDragAndDrop = false;
            NotificationStackScrollLayoutController.C29679 c29679 = (NotificationStackScrollLayoutController.C29679) callback;
            NotificationStackScrollLayoutController.this.mLongPressedView = null;
            velocityTracker.clear();
            cancelLongPress();
            ExpandableView childAtPosition = c29679.getChildAtPosition(motionEvent);
            this.mTouchedView = childAtPosition;
            if (childAtPosition != null) {
                onDownUpdate(childAtPosition);
                this.mCanCurrViewBeDimissed = c29679.canChildBeDismissed(this.mTouchedView);
                velocityTracker.addMovement(motionEvent);
                this.mInitialTouchPos = motionEvent.getX();
                this.mInitialTouchPosY = motionEvent.getY();
                this.mPerpendicularInitialTouchPos = motionEvent.getY();
                this.mTranslation = getTranslation(this.mTouchedView);
                float rawX = motionEvent.getRawX();
                float[] fArr = this.mDownLocation;
                fArr[0] = rawX;
                fArr[1] = motionEvent.getRawY();
                handler.postDelayed(runnableC09851, (long) (ViewConfiguration.getLongPressTimeout() * 1.5f));
            }
        }
        return this.mIsSwiping || this.mLongPressSent || this.mMenuRowIntercepting;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x003b, code lost:
    
        if (r4 != 4) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0098, code lost:
    
        if (r4 >= (r11.getClassification() == 1 ? r2 * r1 : r2)) goto L46;
     */
    @Override // com.android.systemui.Gefingerpoken
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z = this.mIsSwiping;
        Callback callback = this.mCallback;
        if (!z && !this.mMenuRowIntercepting && !this.mLongPressSent) {
            NotificationStackScrollLayoutController.C29679 c29679 = (NotificationStackScrollLayoutController.C29679) callback;
            if (c29679.getChildAtPosition(motionEvent) == null) {
                cancelLongPress();
                return false;
            }
            this.mTouchedView = c29679.getChildAtPosition(motionEvent);
            onInterceptTouchEvent(motionEvent);
            return true;
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        velocityTracker.addMovement(motionEvent);
        int action = motionEvent.getAction();
        if (action != 1) {
            if (action != 2) {
                if (action != 3) {
                }
            }
            if (this.mTouchedView != null) {
                float x = motionEvent.getX() - this.mInitialTouchPos;
                float abs = Math.abs(motionEvent.getY() - this.mInitialTouchPosY);
                float abs2 = Math.abs(x);
                NotificationStackScrollLayoutController.C29679 c296792 = (NotificationStackScrollLayoutController.C29679) callback;
                if (abs2 >= ((int) (this.mFalsingThreshold * (((CentralSurfacesImpl) NotificationStackScrollLayoutController.this.mCentralSurfaces).mWakeUpComingFromTouch ? 1.5f : 1.0f)))) {
                    this.mTouchAboveFalsingThreshold = true;
                }
                if (this.mLongPressSent) {
                    int classification = motionEvent.getClassification();
                    float f = this.mTouchSlopMultiplier;
                    int i = this.mTouchSlop;
                    if (abs2 < (classification == 1 ? i * f : i)) {
                    }
                    if (this.mTouchedView instanceof ExpandableNotificationRow) {
                        Log.d("com.android.systemui.SwipeHelper", "prepare drag and drop CallBack");
                        if (isAvailableToDragAndDrop(this.mTouchedView) && !this.mAlreadyExecutedDragAndDrop) {
                            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mTouchedView;
                            float x2 = motionEvent.getX();
                            float y = motionEvent.getY();
                            if (expandableNotificationRow.mDragController != null) {
                                expandableNotificationRow.mTargetPoint = new Point((int) x2, (int) y);
                                expandableNotificationRow.mDragController.startDragAndDrop(expandableNotificationRow);
                            }
                            this.mAlreadyExecutedDragAndDrop = true;
                        }
                    }
                } else {
                    if (!c296792.canChildBeDismissed(this.mTouchedView)) {
                        float measuredWidth = this.mTouchedView.getMeasuredWidth();
                        float f2 = 0.3f * measuredWidth;
                        if (abs2 >= measuredWidth) {
                            x = x > 0.0f ? f2 : -f2;
                        } else {
                            NotificationMenuRowPlugin currentMenuRow = NotificationStackScrollLayoutController.this.mSwipeHelper.getCurrentMenuRow();
                            float abs3 = currentMenuRow != null ? Math.abs(currentMenuRow.getMenuSnapTarget()) : 0;
                            if (abs2 > abs3) {
                                x = (f2 * ((float) Math.sin(((x - r1) / measuredWidth) * 1.5707963267948966d))) + ((int) (Math.signum(x) * abs3));
                            }
                        }
                    }
                    setTranslation(this.mTouchedView, this.mTranslation + x);
                    ExpandableView expandableView = this.mTouchedView;
                    updateSwipeProgressFromOffset(expandableView, getTranslation(expandableView), this.mCanCurrViewBeDimissed);
                    onMoveUpdate(x);
                }
            }
            return true;
        }
        if (this.mTouchedView != null) {
            velocityTracker.computeCurrentVelocity(1000, this.mDensityScale * 4000.0f);
            float xVelocity = velocityTracker.getXVelocity();
            ExpandableView expandableView2 = this.mTouchedView;
            getTranslation(expandableView2);
            if (!handleUpEvent(xVelocity, motionEvent, expandableView2)) {
                if (isDismissGesture(motionEvent)) {
                    dismissChild(this.mTouchedView, xVelocity, !swipedFastEnough());
                } else {
                    Log.d("com.android.systemui.SwipeHelper", this.mTouchedView + " is not isDismissGesture");
                    NotificationStackScrollLayoutController.this.mFalsingCollector.getClass();
                    snapChild(this.mTouchedView, 0.0f, xVelocity);
                }
                this.mTouchedView = null;
            }
            this.mIsSwiping = false;
        }
        return true;
    }

    public final void resetSwipeStates(boolean z) {
        Animator animator;
        ExpandableView expandableView = this.mTouchedView;
        boolean z2 = this.mSnappingChild;
        boolean z3 = this.mIsSwiping;
        this.mTouchedView = null;
        this.mIsSwiping = false;
        boolean z4 = z | z3;
        if (z4) {
            this.mSnappingChild = false;
        }
        if (expandableView == null) {
            return;
        }
        boolean z5 = z4 && z2;
        if (z5) {
            if ((expandableView instanceof ExpandableNotificationRow) && (animator = ((ExpandableNotificationRow) expandableView).mTranslateAnim) != null) {
                animator.cancel();
            }
            PhysicsAnimator.getInstance(expandableView).cancel();
        }
        if (z4) {
            snapChildIfNeeded(expandableView, 0.0f, false);
        }
        if (z3 || z5) {
            onChildSnappedBack(expandableView, 0.0f);
        }
    }

    public void setTranslation(View view, float f) {
        if (view != null) {
            view.setTranslationX(f);
        }
    }

    public void snapChild(final View view, final float f, float f2) {
        PhysicsAnimator physicsAnimator;
        Animator animator;
        final boolean canChildBeDismissed = ((NotificationStackScrollLayoutController.C29679) this.mCallback).canChildBeDismissed(view);
        boolean z = view instanceof ExpandableNotificationRow;
        if (z && (animator = ((ExpandableNotificationRow) view).mTranslateAnim) != null) {
            animator.cancel();
        }
        PhysicsAnimator.getInstance(view).cancel();
        PhysicsAnimator.SpringConfig springConfig = this.mSnapBackSpringConfig;
        if (z) {
            physicsAnimator = PhysicsAnimator.getInstance((ExpandableNotificationRow) view);
            ExpandableNotificationRow.C28652 c28652 = ExpandableNotificationRow.TRANSLATE_CONTENT;
            physicsAnimator.spring(new FloatPropertyCompat.C01991(c28652.getName(), c28652), f, f2, springConfig);
        } else {
            physicsAnimator = PhysicsAnimator.getInstance(view);
            physicsAnimator.spring(DynamicAnimation.TRANSLATION_X, f, f2, springConfig);
        }
        physicsAnimator.updateListeners.add(new PhysicsAnimator.UpdateListener() { // from class: com.android.systemui.SwipeHelper$$ExternalSyntheticLambda0
            @Override // com.android.wm.shell.animation.PhysicsAnimator.UpdateListener
            public final void onAnimationUpdateForProperty(Object obj) {
                View view2 = (View) obj;
                SwipeHelper swipeHelper = SwipeHelper.this;
                swipeHelper.updateSwipeProgressFromOffset(view2, swipeHelper.getTranslation(view2), canChildBeDismissed);
            }
        });
        physicsAnimator.endListeners.add(new PhysicsAnimator.EndListener() { // from class: com.android.systemui.SwipeHelper$$ExternalSyntheticLambda1
            @Override // com.android.wm.shell.animation.PhysicsAnimator.EndListener
            public final void onAnimationEnd(Object obj, FloatPropertyCompat floatPropertyCompat, boolean z2, boolean z3, float f3, float f4) {
                SwipeHelper swipeHelper = SwipeHelper.this;
                swipeHelper.mSnappingChild = false;
                View view2 = view;
                if (!z3) {
                    swipeHelper.updateSwipeProgressFromOffset(view2, swipeHelper.getTranslation(view2), canChildBeDismissed);
                    if ((swipeHelper.mIsSwiping ? swipeHelper.mTouchedView : null) == view2) {
                        swipeHelper.resetSwipeStates(false);
                    }
                    if (view2 == swipeHelper.mTouchedView && !swipeHelper.mIsSwiping) {
                        swipeHelper.mTouchedView = null;
                    }
                }
                swipeHelper.onChildSnappedBack(view2, f);
            }
        });
        this.mSnappingChild = true;
        physicsAnimator.start();
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0029  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void snapChildIfNeeded(View view, float f, boolean z) {
        boolean z2;
        if ((this.mIsSwiping && this.mTouchedView == view) || this.mSnappingChild) {
            return;
        }
        Animator animator = (Animator) this.mDismissPendingMap.get(view);
        if (animator != null) {
            animator.cancel();
        } else if (getTranslation(view) == 0.0f) {
            z2 = false;
            if (z2) {
                return;
            }
            if (z) {
                snapChild(view, f, 0.0f);
                return;
            }
            boolean canChildBeDismissed = ((NotificationStackScrollLayoutController.C29679) this.mCallback).canChildBeDismissed(view);
            setTranslation(view, 0.0f);
            updateSwipeProgressFromOffset(view, getTranslation(view), canChildBeDismissed);
            return;
        }
        z2 = true;
        if (z2) {
        }
    }

    public boolean swipedFarEnough() {
        return Math.abs(getTranslation(this.mTouchedView)) > ((float) this.mTouchedView.getMeasuredWidth()) * 0.6f;
    }

    public boolean swipedFastEnough() {
        float xVelocity = this.mVelocityTracker.getXVelocity();
        float translation = getTranslation(this.mTouchedView);
        if (Math.abs(xVelocity) > getEscapeVelocity()) {
            return ((xVelocity > 0.0f ? 1 : (xVelocity == 0.0f ? 0 : -1)) > 0) == ((translation > 0.0f ? 1 : (translation == 0.0f ? 0 : -1)) > 0);
        }
        return false;
    }

    public void updateSwipeProgressAlpha(View view, float f) {
        view.setAlpha(f);
    }

    public final void updateSwipeProgressFromOffset(View view, float f, boolean z) {
        float min = Math.min(Math.max(0.0f, Math.abs(f / view.getMeasuredWidth())), this.mMaxSwipeProgress);
        this.mCallback.getClass();
        if (z) {
            if (min == 0.0f || min == 1.0f) {
                view.setLayerType(0, null);
            } else {
                view.setLayerType(2, null);
            }
            updateSwipeProgressAlpha(view, getSwipeAlpha(min));
        }
        Trace.beginSection("SwipeHelper.invalidateGlobalRegion");
        RectF rectF = new RectF(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        while (view.getParent() != null && (view.getParent() instanceof View)) {
            view = (View) view.getParent();
            view.getMatrix().mapRect(rectF);
            view.invalidate((int) Math.floor(rectF.left), (int) Math.floor(rectF.top), (int) Math.ceil(rectF.right), (int) Math.ceil(rectF.bottom));
        }
        Trace.endSection();
    }

    public final void dismissChild(final View view, float f, final NotificationStackScrollLayout$$ExternalSyntheticLambda1 notificationStackScrollLayout$$ExternalSyntheticLambda1, long j, boolean z, long j2, boolean z2) {
        final boolean canChildBeDismissed = ((NotificationStackScrollLayoutController.C29679) this.mCallback).canChildBeDismissed(view);
        boolean z3 = true;
        boolean z4 = f == 0.0f && (getTranslation(view) == 0.0f || z2) && (view.getLayoutDirection() == 1);
        if ((Math.abs(f) <= getEscapeVelocity() || f >= 0.0f) && (getTranslation(view) >= 0.0f || z2)) {
            z3 = false;
        }
        float totalTranslationLength = (z3 || z4) ? -getTotalTranslationLength(view) : getTotalTranslationLength(view);
        long min = j2 == 0 ? f != 0.0f ? Math.min(400L, (int) ((Math.abs(totalTranslationLength - getTranslation(view)) * 1000.0f) / Math.abs(f))) : 200L : j2;
        view.setLayerType(2, null);
        Animator viewTranslationAnimator = getViewTranslationAnimator(view, totalTranslationLength, new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.SwipeHelper.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SwipeHelper.this.updateSwipeProgressFromOffset(view, ((Float) valueAnimator.getAnimatedValue()).floatValue(), canChildBeDismissed);
            }
        });
        if (viewTranslationAnimator == null) {
            onDismissChildWithAnimationFinished();
            return;
        }
        if (z) {
            viewTranslationAnimator.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
            viewTranslationAnimator.setDuration(min);
        } else {
            float translation = getTranslation(view);
            float measuredWidth = view.getMeasuredWidth();
            FlingAnimationUtils flingAnimationUtils = this.mFlingAnimationUtils;
            float pow = (float) (Math.pow(Math.abs(r5) / measuredWidth, 0.5d) * flingAnimationUtils.mMaxLengthSeconds);
            float abs = Math.abs(totalTranslationLength - translation);
            float abs2 = Math.abs(f);
            float f2 = flingAnimationUtils.mMinVelocityPxPerSecond;
            float max = Math.max(0.0f, Math.min(1.0f, (abs2 - f2) / (flingAnimationUtils.mHighVelocityPxPerSecond - f2)));
            float f3 = (max * 0.5f) + ((1.0f - max) * 0.4f);
            PathInterpolator pathInterpolator = new PathInterpolator(0.0f, 0.0f, 0.5f, f3);
            float f4 = ((f3 / 0.5f) * abs) / abs2;
            FlingAnimationUtils.AnimatorProperties animatorProperties = flingAnimationUtils.mAnimatorProperties;
            if (f4 <= pow) {
                animatorProperties.mInterpolator = pathInterpolator;
                pow = f4;
            } else if (abs2 >= f2) {
                animatorProperties.mInterpolator = new FlingAnimationUtils.InterpolatorInterpolator(new FlingAnimationUtils.VelocityInterpolator(pow, abs2, abs, 0), pathInterpolator, com.android.p038wm.shell.animation.Interpolators.LINEAR_OUT_SLOW_IN);
            } else {
                animatorProperties.mInterpolator = com.android.p038wm.shell.animation.Interpolators.FAST_OUT_LINEAR_IN;
            }
            animatorProperties.getClass();
            viewTranslationAnimator = viewTranslationAnimator;
            viewTranslationAnimator.setDuration((long) (pow * 1000.0f));
            viewTranslationAnimator.setInterpolator(animatorProperties.mInterpolator);
        }
        if (j > 0) {
            viewTranslationAnimator.setStartDelay(j);
        }
        viewTranslationAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.SwipeHelper.3
            public boolean mCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                this.mCancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                Log.d("com.android.systemui.SwipeHelper", "swiped dismiss anim end : " + view);
                SwipeHelper swipeHelper = SwipeHelper.this;
                View view2 = view;
                swipeHelper.updateSwipeProgressFromOffset(view2, swipeHelper.getTranslation(view2), canChildBeDismissed);
                SwipeHelper.this.mDismissPendingMap.remove(view);
                View view3 = view;
                if (view3 instanceof ExpandableNotificationRow) {
                    ((ExpandableNotificationRow) view3).getClass();
                }
                if (this.mCancelled) {
                    View view4 = view;
                    if (view4 instanceof ExpandableNotificationRow) {
                        Log.d("com.android.systemui.SwipeHelper", "onAnimationCancel removeFromTransientContainer");
                        ((ExpandableNotificationRow) view4).removeFromTransientContainer();
                    }
                } else {
                    Callback callback = SwipeHelper.this.mCallback;
                    View view5 = view;
                    NotificationStackScrollLayoutController.C29679 c29679 = (NotificationStackScrollLayoutController.C29679) callback;
                    c29679.getClass();
                    if (view5 instanceof ActivatableNotificationView) {
                        ActivatableNotificationView activatableNotificationView = (ActivatableNotificationView) view5;
                        if (!activatableNotificationView.mDismissed) {
                            c29679.handleChildViewDismissed(view5);
                        }
                        activatableNotificationView.removeFromTransientContainer();
                        if (activatableNotificationView instanceof ExpandableNotificationRow) {
                            ((ExpandableNotificationRow) activatableNotificationView).removeChildrenWithKeepInParent();
                        }
                    }
                    SwipeHelper swipeHelper2 = SwipeHelper.this;
                    if ((swipeHelper2.mIsSwiping ? swipeHelper2.mTouchedView : null) == view) {
                        swipeHelper2.resetSwipeStates(false);
                    }
                }
                Consumer consumer = notificationStackScrollLayout$$ExternalSyntheticLambda1;
                if (consumer != null) {
                    consumer.accept(Boolean.valueOf(this.mCancelled));
                }
                SwipeHelper.this.getClass();
                view.setLayerType(0, null);
                SwipeHelper.this.onDismissChildWithAnimationFinished();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                ((NotificationStackScrollLayoutController.C29679) SwipeHelper.this.mCallback).onBeginDrag(view);
            }
        });
        prepareDismissAnimation(viewTranslationAnimator, view);
        this.mDismissPendingMap.put(view, viewTranslationAnimator);
        viewTranslationAnimator.start();
    }

    public void onDownUpdate(ExpandableView expandableView) {
    }

    public void onMoveUpdate(float f) {
    }

    public void onDismissChildWithAnimationFinished() {
    }

    public void prepareDismissAnimation(Animator animator, View view) {
    }
}
