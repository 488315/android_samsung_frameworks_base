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
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda10;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.wm.shell.animation.FlingAnimationUtils;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import java.io.PrintWriter;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    public final PhysicsAnimator.SpringConfig mSnapBackSpringConfig = new PhysicsAnimator.SpringConfig(200.0f, 0.75f);
    public float mTranslation = 0.0f;
    public final float[] mDownLocation = new float[2];
    public final AnonymousClass1 mPerformLongPress = new AnonymousClass1();
    public final ArrayMap mDismissPendingMap = new ArrayMap();
    public final Handler mHandler = new Handler();
    public final VelocityTracker mVelocityTracker = VelocityTracker.obtain();
    public final float mTouchSlopMultiplier = ViewConfiguration.getAmbiguousGestureMultiplier();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.SwipeHelper$1, reason: invalid class name */
    public final class AnonymousClass1 implements Runnable {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public boolean handleUpEvent(MotionEvent motionEvent, View view, float f) {
        return false;
    }

    public final boolean isAvailableToDragAndDrop(View view) {
        if (!((FeatureFlagsClassicRelease) this.mFeatureFlags).isEnabled(com.android.systemui.flags.Flags.NOTIFICATION_DRAG_TO_CONTENTS) || !(view instanceof ExpandableNotificationRow)) {
            return false;
        }
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
        boolean canBubble = expandableNotificationRow.mEntry.mRanking.canBubble();
        Notification notification2 = expandableNotificationRow.mEntry.mSbn.getNotification();
        PendingIntent pendingIntent = notification2.contentIntent;
        if (pendingIntent == null) {
            pendingIntent = notification2.fullScreenIntent;
        }
        return (pendingIntent == null || !pendingIntent.isActivity() || canBubble) ? false : true;
    }

    public final boolean isDismissGesture(MotionEvent motionEvent) {
        getTranslation(this.mTouchedView);
        if (motionEvent.getActionMasked() == 1 && !this.mFalsingManager.isUnlockingDisabled() && !isFalseGesture() && (swipedFastEnough() || swipedFarEnough())) {
            if (((NotificationStackScrollLayoutController.AnonymousClass11) this.mCallback).canChildBeDismissed(this.mTouchedView)) {
                return true;
            }
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
            if (expandableNotificationRow.mIsPinned && !anonymousClass11.canChildBeDismissed(expandableNotificationRow) && expandableNotificationRow.mEntry.mSbn.getNotification().fullScreenIntent == null) {
                ((BaseHeadsUpManager) notificationStackScrollLayoutController.mHeadsUpManager).removeNotification$1(expandableNotificationRow.mEntry.mSbn.getKey(), true);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0028, code lost:
    
        if (r0 != 3) goto L48;
     */
    @Override // com.android.systemui.Gefingerpoken
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r11) {
        /*
            Method dump skipped, instructions count: 306
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.SwipeHelper.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x003c, code lost:
    
        if (r0 != 4) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00b6, code lost:
    
        if (r7 >= (r0 == 1 ? r2 * r11.mTouchSlopMultiplier : r2)) goto L50;
     */
    /* JADX WARN: Removed duplicated region for block: B:34:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x015f  */
    @Override // com.android.systemui.Gefingerpoken
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onTouchEvent(android.view.MotionEvent r12) {
        /*
            Method dump skipped, instructions count: 539
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
        if ((view instanceof ExpandableNotificationRow) && (animator = ((ExpandableNotificationRow) view).mTranslateAnim) != null) {
            animator.cancel();
        }
        PhysicsAnimator.Companion companion2 = PhysicsAnimator.Companion;
        companion2.getClass();
        PhysicsAnimator.Companion.getInstance(view).cancel();
        boolean z = view instanceof ExpandableNotificationRow;
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
                swipeHelper.updateSwipeProgressFromOffset(view2, swipeHelper.getTranslation(view2), canChildBeDismissed);
            }
        });
        companion.endListeners.add(new PhysicsAnimator.EndListener() { // from class: com.android.systemui.SwipeHelper$$ExternalSyntheticLambda2
            @Override // com.android.wm.shell.shared.animation.PhysicsAnimator.EndListener
            public final void onAnimationEnd(Object obj, FloatPropertyCompat floatPropertyCompat, boolean z2, boolean z3, float f3, float f4) {
                View view2 = view;
                SwipeHelper swipeHelper = SwipeHelper.this;
                swipeHelper.mSnappingChild = false;
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
            return ((xVelocity > 0.0f ? 1 : (xVelocity == 0.0f ? 0 : -1)) > 0) == ((translation > 0.0f ? 1 : (translation == 0.0f ? 0 : -1)) > 0);
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

    public final void dismissChild(final View view, float f, final NotificationStackScrollLayout$$ExternalSyntheticLambda10 notificationStackScrollLayout$$ExternalSyntheticLambda10, long j, boolean z, long j2, boolean z2) {
        final boolean canChildBeDismissed = ((NotificationStackScrollLayoutController.AnonymousClass11) this.mCallback).canChildBeDismissed(view);
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
        if (viewTranslationAnimator == null) {
            onDismissChildWithAnimationFinished();
            return;
        }
        if (z) {
            viewTranslationAnimator.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
            viewTranslationAnimator.setDuration(min);
        } else {
            this.mFlingAnimationUtils.applyDismissing(viewTranslationAnimator, getTranslation(view), totalTranslationLength, f, view.getMeasuredWidth());
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

            /* JADX WARN: Removed duplicated region for block: B:16:0x0096  */
            /* JADX WARN: Removed duplicated region for block: B:22:0x006b  */
            /* JADX WARN: Removed duplicated region for block: B:30:0x0089  */
            /* JADX WARN: Removed duplicated region for block: B:32:0x008f  */
            /* JADX WARN: Removed duplicated region for block: B:33:0x008c  */
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onAnimationEnd(android.animation.Animator r6) {
                /*
                    r5 = this;
                    java.lang.StringBuilder r6 = new java.lang.StringBuilder
                    java.lang.String r0 = "swiped dismiss anim end : "
                    r6.<init>(r0)
                    android.view.View r0 = r2
                    r6.append(r0)
                    java.lang.String r6 = r6.toString()
                    java.lang.String r0 = "com.android.systemui.SwipeHelper"
                    android.util.Log.d(r0, r6)
                    com.android.systemui.SwipeHelper r6 = com.android.systemui.SwipeHelper.this
                    android.view.View r1 = r2
                    boolean r2 = r3
                    float r3 = r6.getTranslation(r1)
                    r6.updateSwipeProgressFromOffset(r1, r3, r2)
                    com.android.systemui.SwipeHelper r6 = com.android.systemui.SwipeHelper.this
                    android.util.ArrayMap r6 = r6.mDismissPendingMap
                    android.view.View r1 = r2
                    r6.remove(r1)
                    android.view.View r6 = r2
                    boolean r1 = r6 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
                    r2 = 0
                    if (r1 == 0) goto L42
                    com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r6 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r6
                    android.view.ViewGroup r1 = r6.mTransientContainer
                    if (r1 != 0) goto L40
                    android.view.ViewParent r6 = r6.getParent()
                    if (r6 == 0) goto L40
                    goto L42
                L40:
                    r6 = 1
                    goto L43
                L42:
                    r6 = r2
                L43:
                    boolean r1 = r5.mCancelled
                    r3 = 0
                    if (r1 == 0) goto L5c
                    if (r6 == 0) goto L4b
                    goto L5c
                L4b:
                    android.view.View r6 = r2
                    boolean r1 = r6 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
                    if (r1 == 0) goto L92
                    com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r6 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r6
                    java.lang.String r1 = "onAnimationCancel removeFromTransientContainer"
                    android.util.Log.d(r0, r1)
                    r6.removeFromTransientContainer()
                    goto L92
                L5c:
                    com.android.systemui.SwipeHelper r6 = com.android.systemui.SwipeHelper.this
                    com.android.systemui.SwipeHelper$Callback r6 = r6.mCallback
                    android.view.View r0 = r2
                    com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$11 r6 = (com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController.AnonymousClass11) r6
                    r6.getClass()
                    boolean r1 = r0 instanceof com.android.systemui.statusbar.notification.row.ActivatableNotificationView
                    if (r1 == 0) goto L81
                    r1 = r0
                    com.android.systemui.statusbar.notification.row.ActivatableNotificationView r1 = (com.android.systemui.statusbar.notification.row.ActivatableNotificationView) r1
                    boolean r4 = r1.mDismissed
                    if (r4 != 0) goto L75
                    r6.handleChildViewDismissed(r0)
                L75:
                    r1.removeFromTransientContainer()
                    boolean r6 = r1 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
                    if (r6 == 0) goto L81
                    com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r1 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r1
                    r1.removeChildrenWithKeepInParent()
                L81:
                    com.android.systemui.SwipeHelper r6 = com.android.systemui.SwipeHelper.this
                    android.view.View r0 = r2
                    boolean r1 = r6.mIsSwiping
                    if (r1 == 0) goto L8c
                    com.android.systemui.statusbar.notification.row.ExpandableView r1 = r6.mTouchedView
                    goto L8d
                L8c:
                    r1 = r3
                L8d:
                    if (r1 != r0) goto L92
                    r6.resetSwipeStates(r2)
                L92:
                    java.util.function.Consumer r6 = r4
                    if (r6 == 0) goto L9f
                    boolean r0 = r5.mCancelled
                    java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
                    r6.accept(r0)
                L9f:
                    com.android.systemui.SwipeHelper r6 = com.android.systemui.SwipeHelper.this
                    r6.getClass()
                    android.view.View r6 = r2
                    r6.setLayerType(r2, r3)
                    com.android.systemui.SwipeHelper r5 = com.android.systemui.SwipeHelper.this
                    r5.onDismissChildWithAnimationFinished()
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.SwipeHelper.AnonymousClass3.onAnimationEnd(android.animation.Animator):void");
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                ((NotificationStackScrollLayoutController.AnonymousClass11) SwipeHelper.this.mCallback).onBeginDrag(view);
            }
        });
        prepareDismissAnimation(view, viewTranslationAnimator);
        this.mDismissPendingMap.put(view, viewTranslationAnimator);
        viewTranslationAnimator.start();
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
