package com.android.systemui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.res.Resources;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.Property;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.app.animation.Interpolators;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.notification.shared.NotificationContentAlphaOptimization;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda5;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.wm.shell.animation.FlingAnimationUtils;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import java.io.PrintWriter;
import java.util.function.BiConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SwipeHelper implements Gefingerpoken, Dumpable {
    public boolean mAlreadyExecutedDragAndDrop;
    public final Callback mCallback;
    public boolean mCanCurrViewBeDimissed;
    public float mDensityScale;
    public final boolean mFadeDependingOnAmountSwiped;
    public final FalsingManager mFalsingManager;
    public int mFalsingThreshold;
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
    public final PhysicsAnimator.SpringConfig mSnapBackSpringConfig;
    public boolean mSnappingChild;
    public boolean mTouchAboveFalsingThreshold;
    public final int mTouchSlop;
    public ExpandableView mTouchedView;
    public float mTranslation = 0.0f;
    public final float[] mDownLocation = new float[2];
    public final AnonymousClass1 mPerformLongPress = new AnonymousClass1();
    public final ArrayMap mDismissPendingMap = new ArrayMap();
    public float mSnapBackDirection = 0.0f;
    public final Handler mHandler = new Handler();
    public final VelocityTracker mVelocityTracker = VelocityTracker.obtain();
    public final float mTouchSlopMultiplier = ViewConfiguration.getAmbiguousGestureMultiplier();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.SwipeHelper$1, reason: invalid class name */
    public class AnonymousClass1 implements Runnable {
        public final int[] mViewOffset = new int[2];

        public AnonymousClass1() {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
    }

    public SwipeHelper(Callback callback, Resources resources, ViewConfiguration viewConfiguration, FalsingManager falsingManager, FeatureFlags featureFlags) {
        this.mCallback = callback;
        this.mPagingTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        this.mSlopMultiplier = viewConfiguration.getScaledAmbiguousGestureMultiplier();
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        ViewConfiguration.getLongPressTimeout();
        float f = resources.getDisplayMetrics().density;
        this.mDensityScale = f;
        NotificationStackScrollLayoutController.this.mMagneticNotificationRowManager.onDensityChange(f);
        this.mFalsingThreshold = resources.getDimensionPixelSize(R.dimen.swipe_helper_falsing_threshold);
        this.mFadeDependingOnAmountSwiped = resources.getBoolean(R.bool.config_fadeDependingOnAmountSwiped);
        this.mFalsingManager = falsingManager;
        this.mFeatureFlags = featureFlags;
        this.mSnapBackSpringConfig = new PhysicsAnimator.SpringConfig(200.0f, 0.75f);
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
            append.println(expandableNotificationRow == null ? "null" : expandableNotificationRow.mLoggingKey);
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
        printWriter.append("mDismissPendingMap: ").println(this.mDismissPendingMap.size());
        if (this.mDismissPendingMap.isEmpty()) {
            return;
        }
        this.mDismissPendingMap.forEach(new BiConsumer() { // from class: com.android.systemui.SwipeHelper$$ExternalSyntheticLambda0
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
        PhysicsAnimator.Companion.getClass();
        PhysicsAnimator.Companion.getInstance(view).cancel();
        return view instanceof ExpandableNotificationRow ? ((ExpandableNotificationRow) view).getTranslateViewAnimator(f, animatorUpdateListener) : createTranslationAnimation(view, f, animatorUpdateListener);
    }

    public boolean handleUpEvent(MotionEvent motionEvent, ExpandableView expandableView, float f) {
        return false;
    }

    public final boolean isAvailableToDragAndDrop(ExpandableView expandableView) {
        if (!((FeatureFlagsClassicRelease) this.mFeatureFlags).isEnabled(Flags.NOTIFICATION_DRAG_TO_CONTENTS) || !(expandableView instanceof ExpandableNotificationRow)) {
            return false;
        }
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
        int i = NotificationBundleUi.$r8$clinit;
        boolean canBubble = expandableNotificationRow.getEntryLegacy().mRanking.canBubble();
        Notification notification2 = expandableNotificationRow.getEntryLegacy().mSbn.getNotification();
        PendingIntent pendingIntent = notification2.contentIntent;
        if (pendingIntent == null) {
            pendingIntent = notification2.fullScreenIntent;
        }
        return (pendingIntent == null || !pendingIntent.isActivity() || canBubble) ? false : true;
    }

    public final boolean isDismissGesture(MotionEvent motionEvent) {
        getTranslation(this.mTouchedView);
        if (motionEvent.getActionMasked() != 1 || this.mFalsingManager.isUnlockingDisabled() || isFalseGesture()) {
            return false;
        }
        if (swipedFastEnough() || swipedFarEnough()) {
            return ((NotificationStackScrollLayoutController.AnonymousClass11) this.mCallback).canChildBeDismissed(this.mTouchedView);
        }
        return false;
    }

    public final boolean isFalseGesture() {
        boolean onKeyguard = NotificationStackScrollLayoutController.this.mView.onKeyguard();
        FalsingManager falsingManager = this.mFalsingManager;
        if (falsingManager.isClassifierEnabled()) {
            if (!onKeyguard || !falsingManager.isFalseTouch(1)) {
                return false;
            }
        } else if (!onKeyguard || this.mTouchAboveFalsingThreshold) {
            return false;
        }
        return true;
    }

    public void onChildSnappedBack(float f, View view) {
        NotificationStackScrollLayoutController.AnonymousClass11 anonymousClass11 = (NotificationStackScrollLayoutController.AnonymousClass11) this.mCallback;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.updateFirstAndLastBackgroundViews();
        notificationStackScrollLayout.mController.mNotificationRoundnessManager.setViewsAffectedBySwipe(null, null, null);
        notificationStackScrollLayout.mShelf.updateAppearance();
        if (view instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            int i = NotificationBundleUi.$r8$clinit;
            boolean z = expandableNotificationRow.getEntryLegacy().mSbn.getNotification().fullScreenIntent == null;
            if (expandableNotificationRow.mPinnedStatus.isPinned() && !anonymousClass11.canChildBeDismissed(expandableNotificationRow) && z) {
                ((HeadsUpManagerImpl) notificationStackScrollLayoutController.mHeadsUpManager).removeNotification(expandableNotificationRow.getKey(), "onChildSnappedBack", true);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0028, code lost:
    
        if (r0 != 3) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00cf, code lost:
    
        if (r11 != false) goto L62;
     */
    @Override // com.android.systemui.Gefingerpoken
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r11) {
        /*
            Method dump skipped, instructions count: 335
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.SwipeHelper.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x003c, code lost:
    
        if (r0 != 4) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00b6, code lost:
    
        if (r7 >= (r0 == 1 ? r2 * r11.mTouchSlopMultiplier : r2)) goto L50;
     */
    /* JADX WARN: Removed duplicated region for block: B:34:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x015b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onTouchEvent(android.view.MotionEvent r12) {
        /*
            Method dump skipped, instructions count: 544
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.SwipeHelper.onTouchEvent(android.view.MotionEvent):boolean");
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
            PhysicsAnimator.Companion.getClass();
            PhysicsAnimator.Companion.getInstance(expandableView).cancel();
        }
        if (z4) {
            snapChildIfNeeded(expandableView, 0.0f, false);
        }
        if (z3 || z5) {
            onChildSnappedBack(0.0f, expandableView);
        }
    }

    public void setTranslation(float f, View view) {
        if (view != null) {
            view.setTranslationX(f);
        }
    }

    public void snapChild(final View view, final float f, float f2) {
        PhysicsAnimator companion;
        Animator animator;
        final boolean canChildBeDismissed = ((NotificationStackScrollLayoutController.AnonymousClass11) this.mCallback).canChildBeDismissed(view);
        this.mSnapBackDirection = getTranslation(view) - f;
        boolean z = view instanceof ExpandableNotificationRow;
        if (z && (animator = ((ExpandableNotificationRow) view).mTranslateAnim) != null) {
            animator.cancel();
        }
        PhysicsAnimator.Companion companion2 = PhysicsAnimator.Companion;
        companion2.getClass();
        PhysicsAnimator.Companion.getInstance(view).cancel();
        PhysicsAnimator.SpringConfig springConfig = this.mSnapBackSpringConfig;
        if (z) {
            companion2.getClass();
            companion = PhysicsAnimator.Companion.getInstance((ExpandableNotificationRow) view);
            ExpandableNotificationRow.AnonymousClass2 anonymousClass2 = ExpandableNotificationRow.TRANSLATE_CONTENT;
            companion.spring(new FloatPropertyCompat.AnonymousClass1(anonymousClass2.getName(), anonymousClass2), f, f2, springConfig);
        } else {
            companion2.getClass();
            companion = PhysicsAnimator.Companion.getInstance(view);
            companion.spring(DynamicAnimation.TRANSLATION_X, f, f2, springConfig);
        }
        companion.updateListeners.add(new PhysicsAnimator.UpdateListener() { // from class: com.android.systemui.SwipeHelper$$ExternalSyntheticLambda1
            @Override // com.android.wm.shell.shared.animation.PhysicsAnimator.UpdateListener
            public final void onAnimationUpdateForProperty(Object obj) {
                View view2 = (View) obj;
                SwipeHelper swipeHelper = SwipeHelper.this;
                float translation = swipeHelper.getTranslation(view2);
                swipeHelper.updateSwipeProgressFromOffset(view2, translation, canChildBeDismissed);
                float f3 = swipeHelper.mSnapBackDirection;
                float f4 = f;
                if ((f3 <= 0.0f || translation >= f4) && (f3 >= 0.0f || translation <= f4)) {
                    return;
                }
                swipeHelper.mCallback.getClass();
                swipeHelper.mSnapBackDirection = 0.0f;
            }
        });
        companion.endListeners.add(new PhysicsAnimator.EndListener() { // from class: com.android.systemui.SwipeHelper$$ExternalSyntheticLambda2
            @Override // com.android.wm.shell.shared.animation.PhysicsAnimator.EndListener
            public final void onAnimationEnd(Object obj, FloatPropertyCompat floatPropertyCompat, boolean z2, boolean z3, float f3, float f4) {
                View view2 = view;
                SwipeHelper swipeHelper = SwipeHelper.this;
                swipeHelper.mSnappingChild = false;
                swipeHelper.mSnapBackDirection = 0.0f;
                if (!z3) {
                    swipeHelper.updateSwipeProgressFromOffset(view2, swipeHelper.getTranslation(view2), canChildBeDismissed);
                    if ((swipeHelper.mIsSwiping ? swipeHelper.mTouchedView : null) == view2) {
                        swipeHelper.resetSwipeStates(false);
                    }
                    if (view2 == swipeHelper.mTouchedView && !swipeHelper.mIsSwiping) {
                        swipeHelper.mTouchedView = null;
                    }
                }
                swipeHelper.onChildSnappedBack(f, view2);
            }
        });
        this.mSnappingChild = true;
        companion.start();
    }

    public final void snapChildIfNeeded(View view, float f, boolean z) {
        if ((this.mIsSwiping && this.mTouchedView == view) || this.mSnappingChild) {
            return;
        }
        Animator animator = (Animator) this.mDismissPendingMap.get(view);
        if (animator != null) {
            animator.cancel();
        } else if (getTranslation(view) == 0.0f) {
            return;
        }
        if (z) {
            snapChild(view, f, 0.0f);
            return;
        }
        boolean canChildBeDismissed = ((NotificationStackScrollLayoutController.AnonymousClass11) this.mCallback).canChildBeDismissed(view);
        setTranslation(0.0f, view);
        updateSwipeProgressFromOffset(view, getTranslation(view), canChildBeDismissed);
    }

    public boolean swipedFarEnough() {
        return Math.abs(getTranslation(this.mTouchedView)) > ((float) this.mTouchedView.getMeasuredWidth()) * 0.6f;
    }

    public boolean swipedFastEnough() {
        float xVelocity = this.mVelocityTracker.getXVelocity();
        float translation = getTranslation(this.mTouchedView);
        if (Math.abs(xVelocity) > getEscapeVelocity()) {
            if ((xVelocity > 0.0f) == (translation > 0.0f)) {
                return true;
            }
        }
        return false;
    }

    public void updateSwipeProgressAlpha(float f, View view) {
        view.setAlpha(f);
    }

    public final void updateSwipeProgressFromOffset(View view, float f, boolean z) {
        float min = f == 0.0f ? 0.0f : Math.min(Math.max(0.0f, Math.abs(f / view.getMeasuredWidth())), 1.0f);
        this.mCallback.getClass();
        if (z) {
            if (min == 0.0f || min == 1.0f) {
                view.setLayerType(0, null);
            } else {
                view.setLayerType(2, null);
            }
            updateSwipeProgressAlpha(getSwipeAlpha(min), view);
        } else {
            int i = NotificationContentAlphaOptimization.$r8$clinit;
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

    public final void dismissChild(final View view, float f, final NotificationStackScrollLayout$$ExternalSyntheticLambda5 notificationStackScrollLayout$$ExternalSyntheticLambda5, long j, boolean z, long j2, boolean z2) {
        Animator animator;
        NotificationStackScrollLayoutController.AnonymousClass11 anonymousClass11 = (NotificationStackScrollLayoutController.AnonymousClass11) this.mCallback;
        final boolean canChildBeDismissed = anonymousClass11.canChildBeDismissed(view);
        boolean z3 = false;
        boolean z4 = view.getLayoutDirection() == 1;
        if (f == 0.0f && ((getTranslation(view) == 0.0f || z2) && z4)) {
            z3 = true;
        }
        float totalTranslationLength = ((Math.abs(f) <= getEscapeVelocity() || f >= 0.0f) && (getTranslation(view) >= 0.0f || z2) && !z3) ? getTotalTranslationLength(view) : -getTotalTranslationLength(view);
        long min = j2 == 0 ? f != 0.0f ? Math.min(400L, (int) ((Math.abs(totalTranslationLength - getTranslation(view)) * 1000.0f) / Math.abs(f))) : 200L : j2;
        view.setLayerType(2, null);
        Animator viewTranslationAnimator = getViewTranslationAnimator(view, totalTranslationLength, new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.SwipeHelper.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SwipeHelper.this.updateSwipeProgressFromOffset(view, ((Float) valueAnimator.getAnimatedValue()).floatValue(), canChildBeDismissed);
            }
        });
        anonymousClass11.onMagneticInteractionEnd(f, view);
        if (viewTranslationAnimator == null) {
            onDismissChildWithAnimationFinished();
            return;
        }
        if (z) {
            viewTranslationAnimator.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
            viewTranslationAnimator.setDuration(min);
            animator = viewTranslationAnimator;
        } else {
            animator = viewTranslationAnimator;
            this.mFlingAnimationUtils.applyDismissing(animator, getTranslation(view), totalTranslationLength, f, view.getMeasuredWidth());
        }
        if (j > 0) {
            animator.setStartDelay(j);
        }
        animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.SwipeHelper.3
            public boolean mCancelled;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator2) {
                this.mCancelled = true;
                ((NotificationStackScrollLayoutController.AnonymousClass11) SwipeHelper.this.mCallback).onDragCancelled(view);
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x00ad  */
            /* JADX WARN: Removed duplicated region for block: B:22:0x006f  */
            /* JADX WARN: Removed duplicated region for block: B:24:0x0075  */
            /* JADX WARN: Removed duplicated region for block: B:27:0x007e  */
            /* JADX WARN: Removed duplicated region for block: B:37:0x0072  */
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onAnimationEnd(android.animation.Animator r7) {
                /*
                    r6 = this;
                    java.lang.StringBuilder r7 = new java.lang.StringBuilder
                    java.lang.String r0 = "swiped dismiss anim end : "
                    r7.<init>(r0)
                    android.view.View r0 = r2
                    r7.append(r0)
                    java.lang.String r7 = r7.toString()
                    java.lang.String r0 = "com.android.systemui.SwipeHelper"
                    android.util.Log.d(r0, r7)
                    com.android.systemui.SwipeHelper r7 = com.android.systemui.SwipeHelper.this
                    android.view.View r1 = r2
                    boolean r2 = r3
                    float r3 = r7.getTranslation(r1)
                    r7.updateSwipeProgressFromOffset(r1, r3, r2)
                    com.android.systemui.SwipeHelper r7 = com.android.systemui.SwipeHelper.this
                    android.util.ArrayMap r7 = r7.mDismissPendingMap
                    android.view.View r1 = r2
                    r7.remove(r1)
                    android.view.View r7 = r2
                    boolean r1 = r7 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
                    r2 = 0
                    if (r1 == 0) goto L42
                    com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r7 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r7
                    android.view.ViewGroup r1 = r7.mTransientContainer
                    if (r1 != 0) goto L40
                    android.view.ViewParent r7 = r7.getParent()
                    if (r7 == 0) goto L40
                    goto L42
                L40:
                    r7 = 1
                    goto L43
                L42:
                    r7 = r2
                L43:
                    boolean r1 = r6.mCancelled
                    r3 = 0
                    if (r1 == 0) goto L5c
                    if (r7 == 0) goto L4b
                    goto L5c
                L4b:
                    android.view.View r7 = r2
                    boolean r1 = r7 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
                    if (r1 == 0) goto La9
                    com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r7 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r7
                    java.lang.String r1 = "onAnimationCancel removeFromTransientContainer"
                    android.util.Log.d(r0, r1)
                    r7.removeFromTransientContainer()
                    goto La9
                L5c:
                    com.android.systemui.SwipeHelper r7 = com.android.systemui.SwipeHelper.this
                    com.android.systemui.SwipeHelper$Callback r7 = r7.mCallback
                    android.view.View r0 = r2
                    com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$11 r7 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.AnonymousClass11) r7
                    r7.onChildDismissed(r0)
                    com.android.systemui.SwipeHelper r7 = com.android.systemui.SwipeHelper.this
                    android.view.View r0 = r2
                    boolean r1 = r7.mIsSwiping
                    if (r1 == 0) goto L72
                    com.android.systemui.statusbar.notification.row.ExpandableView r1 = r7.mTouchedView
                    goto L73
                L72:
                    r1 = r3
                L73:
                    if (r1 != r0) goto L78
                    r7.resetSwipeStates(r2)
                L78:
                    android.view.View r7 = r2
                    boolean r0 = r7 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
                    if (r0 == 0) goto La9
                    com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r7 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r7
                    boolean r0 = r7.isInsignificantSummary()
                    if (r0 == 0) goto La9
                    com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer r7 = r7.mChildrenContainer
                    if (r7 == 0) goto La9
                    int r0 = r7.getNotificationChildCount()
                    if (r0 <= 0) goto La9
                    r1 = r2
                L91:
                    if (r1 >= r0) goto La9
                    java.util.List r4 = r7.mAttachedChildren
                    java.util.ArrayList r4 = (java.util.ArrayList) r4
                    java.lang.Object r4 = r4.get(r1)
                    com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r4 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r4
                    com.android.systemui.SwipeHelper r5 = com.android.systemui.SwipeHelper.this
                    com.android.systemui.SwipeHelper$Callback r5 = r5.mCallback
                    com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$11 r5 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.AnonymousClass11) r5
                    r5.onChildDismissed(r4)
                    int r1 = r1 + 1
                    goto L91
                La9:
                    java.util.function.Consumer r7 = r4
                    if (r7 == 0) goto Lb6
                    boolean r0 = r6.mCancelled
                    java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
                    r7.accept(r0)
                Lb6:
                    android.view.View r7 = r2
                    r7.setLayerType(r2, r3)
                    com.android.systemui.SwipeHelper r6 = com.android.systemui.SwipeHelper.this
                    r6.onDismissChildWithAnimationFinished()
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.SwipeHelper.AnonymousClass3.onAnimationEnd(android.animation.Animator):void");
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator2) {
                super.onAnimationStart(animator2);
                ((NotificationStackScrollLayoutController.AnonymousClass11) SwipeHelper.this.mCallback).onBeginDrag(view);
            }
        });
        prepareDismissAnimation(view, animator);
        this.mDismissPendingMap.put(view, animator);
        animator.start();
    }

    public void onDismissChildWithAnimationFinished() {
    }

    public void onDownUpdate(ExpandableView expandableView) {
    }

    public void onMoveUpdate(float f) {
    }

    public void prepareDismissAnimation(View view, Animator animator) {
    }
}
