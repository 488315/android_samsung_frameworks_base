package com.android.systemui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
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
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.app.animation.Interpolators;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.power.shared.model.WakeSleepReason;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.notification.shared.NotificationContentAlphaOptimization;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout$$ExternalSyntheticLambda5;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.wm.shell.animation.FlingAnimationUtils;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_X, f);
        if (animatorUpdateListener != null) {
            objectAnimatorOfFloat.addUpdateListener(animatorUpdateListener);
        }
        return objectAnimatorOfFloat;
    }

    public void dismissChild(View view, float f, boolean z) {
        dismissChild(view, f, null, 0L, z, 0L, false);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(final PrintWriter printWriter, String[] strArr) {
        printWriter.append("mTouchedView=").print(this.mTouchedView);
        if (this.mTouchedView instanceof ExpandableNotificationRow) {
            PrintWriter printWriterAppend = printWriter.append(" key=");
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mTouchedView;
            printWriterAppend.println(expandableNotificationRow == null ? "null" : expandableNotificationRow.mLoggingKey);
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
        boolean zCanBubble = expandableNotificationRow.getEntryLegacy().mRanking.canBubble();
        Notification notification2 = expandableNotificationRow.getEntryLegacy().mSbn.getNotification();
        PendingIntent pendingIntent = notification2.contentIntent;
        if (pendingIntent == null) {
            pendingIntent = notification2.fullScreenIntent;
        }
        return (pendingIntent == null || !pendingIntent.isActivity() || zCanBubble) ? false : true;
    }

    public final boolean isDismissGesture(MotionEvent motionEvent) {
        getTranslation(this.mTouchedView);
        if (motionEvent.getActionMasked() != 1 || this.mFalsingManager.isUnlockingDisabled() || isFalseGesture()) {
            return false;
        }
        if (swipedFastEnough() || swipedFarEnough()) {
            return ((NotificationStackScrollLayoutController.AnonymousClass12) this.mCallback).canChildBeDismissed(this.mTouchedView);
        }
        return false;
    }

    public final boolean isFalseGesture() {
        boolean zOnKeyguard = NotificationStackScrollLayoutController.this.mView.onKeyguard();
        FalsingManager falsingManager = this.mFalsingManager;
        if (falsingManager.isClassifierEnabled()) {
            if (!zOnKeyguard || !falsingManager.isFalseTouch(1)) {
                return false;
            }
        } else if (!zOnKeyguard || this.mTouchAboveFalsingThreshold) {
            return false;
        }
        return true;
    }

    public void onChildSnappedBack(float f, View view) {
        NotificationStackScrollLayoutController.AnonymousClass12 anonymousClass12 = (NotificationStackScrollLayoutController.AnonymousClass12) this.mCallback;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
        notificationStackScrollLayout.updateFirstAndLastBackgroundViews();
        notificationStackScrollLayout.mController.mNotificationRoundnessManager.setViewsAffectedBySwipe(null, null, null);
        notificationStackScrollLayout.mShelf.updateAppearance();
        if (view instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
            int i = NotificationBundleUi.$r8$clinit;
            boolean z = expandableNotificationRow.getEntryLegacy().mSbn.getNotification().fullScreenIntent == null;
            if (expandableNotificationRow.mPinnedStatus.isPinned() && !anonymousClass12.canChildBeDismissed(expandableNotificationRow) && z) {
                ((HeadsUpManagerImpl) notificationStackScrollLayoutController.mHeadsUpManager).removeNotification(expandableNotificationRow.getKey(), "onChildSnappedBack", true);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00af  */
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
        AnonymousClass1 anonymousClass1 = this.mPerformLongPress;
        Callback callback = this.mCallback;
        if (action != 0) {
            if (action == 1) {
                boolean z = this.mIsSwiping || this.mLongPressSent || this.mMenuRowIntercepting;
                this.mLongPressSent = false;
                NotificationStackScrollLayoutController.this.mLongPressedView = null;
                this.mMenuRowIntercepting = false;
                resetSwipeStates(false);
                cancelLongPress();
                if (!z) {
                }
            } else if (action != 2) {
                if (action != 3) {
                }
            } else if (this.mTouchedView != null && !this.mLongPressSent) {
                this.mVelocityTracker.addMovement(motionEvent);
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                float f = x - this.mInitialTouchPos;
                float f2 = y - this.mPerpendicularInitialTouchPos;
                if (Math.abs(f) > (motionEvent.getClassification() == 1 ? this.mPagingTouchSlop * this.mSlopMultiplier : this.mPagingTouchSlop) && Math.abs(f) > Math.abs(f2)) {
                    callback.getClass();
                    this.mIsSwiping = true;
                    ExpandableView expandableView2 = this.mTouchedView;
                    NotificationStackScrollLayoutController.AnonymousClass12 anonymousClass12 = (NotificationStackScrollLayoutController.AnonymousClass12) callback;
                    if (expandableView2 instanceof ExpandableNotificationRow) {
                        NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
                        notificationStackScrollLayoutController.mMagneticNotificationRowManager.setMagneticAndRoundableTargets((ExpandableNotificationRow) expandableView2, notificationStackScrollLayoutController.mView, notificationStackScrollLayoutController.mSectionsManager);
                    }
                    anonymousClass12.onBeginDrag(this.mTouchedView);
                    this.mInitialTouchPos = motionEvent.getX();
                    this.mTranslation = getTranslation(this.mTouchedView);
                    cancelLongPress();
                } else if (motionEvent.getClassification() == 2 && handler.hasCallbacks(anonymousClass1)) {
                    cancelLongPress();
                    anonymousClass1.run();
                }
            }
        }
        this.mTouchAboveFalsingThreshold = false;
        this.mIsSwiping = false;
        this.mSnappingChild = false;
        this.mLongPressSent = false;
        this.mAlreadyExecutedDragAndDrop = false;
        NotificationStackScrollLayoutController.AnonymousClass12 anonymousClass122 = (NotificationStackScrollLayoutController.AnonymousClass12) callback;
        NotificationStackScrollLayoutController.this.mLongPressedView = null;
        this.mVelocityTracker.clear();
        cancelLongPress();
        ExpandableView childAtPosition = anonymousClass122.getChildAtPosition(motionEvent);
        this.mTouchedView = childAtPosition;
        if (childAtPosition != null) {
            if (childAtPosition instanceof ExpandableNotificationRow) {
                ((ExpandableNotificationRow) childAtPosition).mSkipRemovalAnim = false;
            }
            onDownUpdate(childAtPosition);
            this.mCanCurrViewBeDimissed = anonymousClass122.canChildBeDismissed(this.mTouchedView);
            this.mVelocityTracker.addMovement(motionEvent);
            this.mInitialTouchPos = motionEvent.getX();
            this.mInitialTouchPosY = motionEvent.getY();
            this.mPerpendicularInitialTouchPos = motionEvent.getY();
            this.mTranslation = getTranslation(this.mTouchedView);
            float rawX = motionEvent.getRawX();
            float[] fArr = this.mDownLocation;
            fArr[0] = rawX;
            fArr[1] = motionEvent.getRawY();
            handler.postDelayed(anonymousClass1, (long) (ViewConfiguration.getLongPressTimeout() * 1.5f));
        }
        return this.mIsSwiping || this.mLongPressSent || this.mMenuRowIntercepting;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouchEvent(MotionEvent motionEvent) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        boolean z = this.mIsSwiping;
        Callback callback = this.mCallback;
        if (!z && !this.mMenuRowIntercepting && !this.mLongPressSent) {
            NotificationStackScrollLayoutController.AnonymousClass12 anonymousClass12 = (NotificationStackScrollLayoutController.AnonymousClass12) callback;
            if (anonymousClass12.getChildAtPosition(motionEvent) == null) {
                cancelLongPress();
                return false;
            }
            this.mTouchedView = anonymousClass12.getChildAtPosition(motionEvent);
            onInterceptTouchEvent(motionEvent);
            return true;
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int action = motionEvent.getAction();
        ExpandableNotificationRow expandableNotificationRow = null;
        if (action == 1) {
            if (this.mTouchedView != null) {
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mDensityScale * 4000.0f);
                float xVelocity = this.mVelocityTracker.getXVelocity();
                ExpandableView expandableView = this.mTouchedView;
                getTranslation(expandableView);
                if (!handleUpEvent(motionEvent, expandableView, xVelocity)) {
                    if (isDismissGesture(motionEvent)) {
                        dismissChild(this.mTouchedView, xVelocity, !swipedFastEnough());
                    } else {
                        Log.d("com.android.systemui.SwipeHelper", this.mTouchedView + " is not isDismissGesture");
                        NotificationStackScrollLayoutController.AnonymousClass12 anonymousClass122 = (NotificationStackScrollLayoutController.AnonymousClass12) callback;
                        anonymousClass122.onMagneticInteractionEnd(xVelocity, this.mTouchedView);
                        anonymousClass122.onDragCancelled(this.mTouchedView);
                        snapChild(this.mTouchedView, 0.0f, xVelocity);
                    }
                    this.mTouchedView = null;
                }
                this.mIsSwiping = false;
                return true;
            }
        } else if (action == 2) {
            if (this.mTouchedView != null) {
                float x = motionEvent.getX() - this.mInitialTouchPos;
                float fAbs = Math.abs(motionEvent.getY() - this.mInitialTouchPosY);
                float fAbs2 = Math.abs(x);
                NotificationStackScrollLayoutController.AnonymousClass12 anonymousClass123 = (NotificationStackScrollLayoutController.AnonymousClass12) callback;
                WakefulnessModel wakefulnessModel = (WakefulnessModel) NotificationStackScrollLayoutController.this.mPowerInteractor.detailedWakefulness.$$delegate_0.getValue();
                ShadeViewController.Companion.getClass();
                if (wakefulnessModel.isAwake()) {
                    WakeSleepReason wakeSleepReason = WakeSleepReason.TAP;
                    WakeSleepReason wakeSleepReason2 = wakefulnessModel.lastWakeReason;
                    float f = (wakeSleepReason2 == wakeSleepReason || wakeSleepReason2 == WakeSleepReason.GESTURE) ? 1.5f : 1.0f;
                    if (fAbs2 >= ((int) (this.mFalsingThreshold * f))) {
                        this.mTouchAboveFalsingThreshold = true;
                    }
                    if (!this.mLongPressSent) {
                        if (!anonymousClass123.canChildBeDismissed(this.mTouchedView)) {
                            float measuredWidth = this.mTouchedView.getMeasuredWidth();
                            float f2 = 0.3f * measuredWidth;
                            if (fAbs2 >= measuredWidth) {
                                x = x > 0.0f ? f2 : -f2;
                            } else {
                                NotificationMenuRowPlugin currentMenuRow = NotificationStackScrollLayoutController.this.mSwipeHelper.getCurrentMenuRow();
                                float fAbs3 = currentMenuRow != null ? Math.abs(currentMenuRow.getMenuSnapTarget()) : 0;
                                if (fAbs2 > fAbs3) {
                                    x = (f2 * ((float) Math.sin(((x - r1) / measuredWidth) * 1.5707963267948966d))) + ((int) (Math.signum(x) * fAbs3));
                                }
                            }
                        }
                        setTranslation(this.mTranslation + x, this.mTouchedView);
                        View view = this.mTouchedView;
                        updateSwipeProgressFromOffset(view, getTranslation(view), this.mCanCurrViewBeDimissed);
                        onMoveUpdate(x);
                        return true;
                    }
                    int classification = motionEvent.getClassification();
                    int i = this.mTouchSlop;
                    if (fAbs2 < (classification == 1 ? i * this.mTouchSlopMultiplier : i)) {
                        int classification2 = motionEvent.getClassification();
                        int i2 = this.mTouchSlop;
                        if (fAbs >= (classification2 == 1 ? i2 * this.mTouchSlopMultiplier : i2)) {
                            if (this.mTouchedView instanceof ExpandableNotificationRow) {
                                Log.d("com.android.systemui.SwipeHelper", "prepare drag and drop CallBack");
                                if (((ExpandableNotificationRow) this.mTouchedView).isInsignificantSummary() && ((ExpandableNotificationRow) this.mTouchedView).isGroupExpanded$1() && ((ArrayList) ((ExpandableNotificationRow) this.mTouchedView).getAttachedChildren()).size() == 1) {
                                    ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) this.mTouchedView;
                                    NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow2.mChildrenContainer;
                                    if (notificationChildrenContainer != null && ((ArrayList) notificationChildrenContainer.mAttachedChildren).size() > 0) {
                                        expandableNotificationRow = (ExpandableNotificationRow) ((ArrayList) expandableNotificationRow2.mChildrenContainer.mAttachedChildren).get(0);
                                    }
                                    if (isAvailableToDragAndDrop(expandableNotificationRow) && !this.mAlreadyExecutedDragAndDrop) {
                                        float x2 = motionEvent.getX();
                                        float y = motionEvent.getY();
                                        if (expandableNotificationRow.mDragController != null) {
                                            expandableNotificationRow.mTargetPoint = new Point((int) x2, (int) y);
                                            expandableNotificationRow.mDragController.startDragAndDrop(expandableNotificationRow);
                                        }
                                        this.mAlreadyExecutedDragAndDrop = true;
                                        return true;
                                    }
                                } else if (isAvailableToDragAndDrop(this.mTouchedView) && !this.mAlreadyExecutedDragAndDrop) {
                                    ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) this.mTouchedView;
                                    float x3 = motionEvent.getX();
                                    float y2 = motionEvent.getY();
                                    if (expandableNotificationRow3.mDragController != null) {
                                        expandableNotificationRow3.mTargetPoint = new Point((int) x3, (int) y2);
                                        expandableNotificationRow3.mDragController.startDragAndDrop(expandableNotificationRow3);
                                    }
                                    this.mAlreadyExecutedDragAndDrop = true;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        } else if (action != 3) {
            if (action == 4) {
            }
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
        final boolean zCanChildBeDismissed = ((NotificationStackScrollLayoutController.AnonymousClass12) this.mCallback).canChildBeDismissed(view);
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
                SwipeHelper swipeHelper = this.f$0;
                float translation = swipeHelper.getTranslation(view2);
                swipeHelper.updateSwipeProgressFromOffset(view2, translation, zCanChildBeDismissed);
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
                SwipeHelper swipeHelper = this.f$0;
                swipeHelper.mSnappingChild = false;
                swipeHelper.mSnapBackDirection = 0.0f;
                if (!z3) {
                    swipeHelper.updateSwipeProgressFromOffset(view2, swipeHelper.getTranslation(view2), zCanChildBeDismissed);
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
        boolean zCanChildBeDismissed = ((NotificationStackScrollLayoutController.AnonymousClass12) this.mCallback).canChildBeDismissed(view);
        setTranslation(0.0f, view);
        updateSwipeProgressFromOffset(view, getTranslation(view), zCanChildBeDismissed);
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
        float fMin = f == 0.0f ? 0.0f : Math.min(Math.max(0.0f, Math.abs(f / view.getMeasuredWidth())), 1.0f);
        this.mCallback.getClass();
        if (z) {
            if (fMin == 0.0f || fMin == 1.0f) {
                view.setLayerType(0, null);
            } else {
                view.setLayerType(2, null);
            }
            updateSwipeProgressAlpha(getSwipeAlpha(fMin), view);
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
        NotificationStackScrollLayoutController.AnonymousClass12 anonymousClass12 = (NotificationStackScrollLayoutController.AnonymousClass12) this.mCallback;
        final boolean zCanChildBeDismissed = anonymousClass12.canChildBeDismissed(view);
        boolean z3 = false;
        boolean z4 = view.getLayoutDirection() == 1;
        if (f == 0.0f && ((getTranslation(view) == 0.0f || z2) && z4)) {
            z3 = true;
        }
        float totalTranslationLength = ((Math.abs(f) <= getEscapeVelocity() || f >= 0.0f) && (getTranslation(view) >= 0.0f || z2) && !z3) ? getTotalTranslationLength(view) : -getTotalTranslationLength(view);
        long jMin = j2 == 0 ? f != 0.0f ? Math.min(400L, (int) ((Math.abs(totalTranslationLength - getTranslation(view)) * 1000.0f) / Math.abs(f))) : 200L : j2;
        view.setLayerType(2, null);
        Animator viewTranslationAnimator = getViewTranslationAnimator(view, totalTranslationLength, new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.SwipeHelper.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SwipeHelper.this.updateSwipeProgressFromOffset(view, ((Float) valueAnimator.getAnimatedValue()).floatValue(), zCanChildBeDismissed);
            }
        });
        anonymousClass12.onMagneticInteractionEnd(f, view);
        if (viewTranslationAnimator == null) {
            onDismissChildWithAnimationFinished();
            return;
        }
        if (z) {
            viewTranslationAnimator.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
            viewTranslationAnimator.setDuration(jMin);
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
                ((NotificationStackScrollLayoutController.AnonymousClass12) SwipeHelper.this.mCallback).onDragCancelled(view);
            }

            /* JADX WARN: Removed duplicated region for block: B:10:0x0042  */
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onAnimationEnd(Animator animator2) {
                boolean z5;
                NotificationChildrenContainer notificationChildrenContainer;
                int notificationChildCount;
                Log.d("com.android.systemui.SwipeHelper", "swiped dismiss anim end : " + view);
                SwipeHelper swipeHelper = SwipeHelper.this;
                View view2 = view;
                swipeHelper.updateSwipeProgressFromOffset(view2, swipeHelper.getTranslation(view2), zCanChildBeDismissed);
                SwipeHelper.this.mDismissPendingMap.remove(view);
                View view3 = view;
                if (view3 instanceof ExpandableNotificationRow) {
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view3;
                    z5 = expandableNotificationRow.mTransientContainer != null || expandableNotificationRow.getParent() == null;
                }
                if (!this.mCancelled || z5) {
                    ((NotificationStackScrollLayoutController.AnonymousClass12) SwipeHelper.this.mCallback).onChildDismissed(view);
                    SwipeHelper swipeHelper2 = SwipeHelper.this;
                    if ((swipeHelper2.mIsSwiping ? swipeHelper2.mTouchedView : null) == view) {
                        swipeHelper2.resetSwipeStates(false);
                    }
                    View view4 = view;
                    if (view4 instanceof ExpandableNotificationRow) {
                        ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) view4;
                        if (expandableNotificationRow2.isInsignificantSummary() && (notificationChildrenContainer = expandableNotificationRow2.mChildrenContainer) != null && (notificationChildCount = notificationChildrenContainer.getNotificationChildCount()) > 0) {
                            for (int i = 0; i < notificationChildCount; i++) {
                                ((NotificationStackScrollLayoutController.AnonymousClass12) SwipeHelper.this.mCallback).onChildDismissed((ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i));
                            }
                        }
                    }
                } else {
                    View view5 = view;
                    if (view5 instanceof ExpandableNotificationRow) {
                        Log.d("com.android.systemui.SwipeHelper", "onAnimationCancel removeFromTransientContainer");
                        ((ExpandableNotificationRow) view5).removeFromTransientContainer();
                    }
                }
                Consumer consumer = notificationStackScrollLayout$$ExternalSyntheticLambda5;
                if (consumer != null) {
                    consumer.accept(Boolean.valueOf(this.mCancelled));
                }
                view.setLayerType(0, null);
                SwipeHelper.this.onDismissChildWithAnimationFinished();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator2) {
                super.onAnimationStart(animator2);
                ((NotificationStackScrollLayoutController.AnonymousClass12) SwipeHelper.this.mCallback).onBeginDrag(view);
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
