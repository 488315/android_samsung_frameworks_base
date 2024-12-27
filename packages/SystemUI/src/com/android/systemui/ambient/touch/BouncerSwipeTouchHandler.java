package com.android.systemui.ambient.touch;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.Log;
import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.Flags;
import com.android.systemui.ambient.touch.BouncerSwipeTouchHandler;
import com.android.systemui.ambient.touch.TouchMonitor;
import com.android.systemui.ambient.touch.dagger.BouncerSwipeModule$$ExternalSyntheticLambda0;
import com.android.systemui.ambient.touch.scrim.ScrimController;
import com.android.systemui.ambient.touch.scrim.ScrimManager;
import com.android.systemui.ambient.touch.scrim.ScrimManager$$ExternalSyntheticLambda1;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.wm.shell.animation.FlingAnimationUtils;
import java.util.Optional;

public final class BouncerSwipeTouchHandler implements TouchHandler {
    public final float mBouncerZoneScreenPercentage;
    public Boolean mCapture;
    public final Optional mCentralSurfaces;
    public float mCurrentExpansion;
    public ScrimController mCurrentScrimController;
    public Boolean mExpanded;
    public final FlingAnimationUtils mFlingAnimationUtils;
    public final FlingAnimationUtils mFlingAnimationUtilsClosing;
    public final LockPatternUtils mLockPatternUtils;
    public final float mMinBouncerZoneScreenPercentage;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final ScrimManager mScrimManager;
    public TouchMonitor.TouchSessionImpl mTouchSession;
    public final UiEventLogger mUiEventLogger;
    public final UserTracker mUserTracker;
    public final ValueAnimatorCreator mValueAnimatorCreator;
    public VelocityTracker mVelocityTracker;
    public final VelocityTrackerFactory mVelocityTrackerFactory;
    public final AnonymousClass1 mScrimManagerCallback = new AnonymousClass1();
    public final AnonymousClass2 mOnGestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.ambient.touch.BouncerSwipeTouchHandler.2
        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (BouncerSwipeTouchHandler.this.mCapture == null) {
                Flags.FEATURE_FLAGS.getClass();
                BouncerSwipeTouchHandler.this.mCapture = Boolean.valueOf(Math.abs(f2) > Math.abs(f) && f2 > 0.0f);
                if (BouncerSwipeTouchHandler.this.mCapture.booleanValue()) {
                    BouncerSwipeTouchHandler bouncerSwipeTouchHandler = BouncerSwipeTouchHandler.this;
                    bouncerSwipeTouchHandler.mExpanded = Boolean.FALSE;
                    bouncerSwipeTouchHandler.mCurrentScrimController.show();
                }
            }
            if (!BouncerSwipeTouchHandler.this.mCapture.booleanValue()) {
                return false;
            }
            if (motionEvent.getY() < motionEvent2.getY() || !BouncerSwipeTouchHandler.this.mCentralSurfaces.isPresent()) {
                return true;
            }
            if (motionEvent.getY() > motionEvent2.getY()) {
                BouncerSwipeTouchHandler bouncerSwipeTouchHandler2 = BouncerSwipeTouchHandler.this;
                if (!bouncerSwipeTouchHandler2.mLockPatternUtils.isSecure(((UserTrackerImpl) bouncerSwipeTouchHandler2.mUserTracker).getUserId())) {
                    ((CentralSurfacesImpl) ((CentralSurfaces) BouncerSwipeTouchHandler.this.mCentralSurfaces.get())).awakenDreams();
                    return true;
                }
            }
            motionEvent2.getY();
            motionEvent.getY();
            float abs = Math.abs(motionEvent.getY() - motionEvent2.getY()) / BouncerSwipeTouchHandler.this.mTouchSession.mBounds.height();
            BouncerSwipeTouchHandler bouncerSwipeTouchHandler3 = BouncerSwipeTouchHandler.this;
            float f3 = 1.0f - abs;
            bouncerSwipeTouchHandler3.mCurrentExpansion = f3;
            bouncerSwipeTouchHandler3.mCurrentScrimController.expand(new ShadeExpansionChangeEvent(f3, bouncerSwipeTouchHandler3.mExpanded.booleanValue(), true));
            return true;
        }
    };

    /* renamed from: com.android.systemui.ambient.touch.BouncerSwipeTouchHandler$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    public enum DreamEvent implements UiEventLogger.UiEventEnum {
        DREAM_SWIPED(988),
        DREAM_BOUNCER_FULLY_VISIBLE(1056);

        private final int mId;

        DreamEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public interface ValueAnimatorCreator {
    }

    public interface VelocityTrackerFactory {
    }

    public BouncerSwipeTouchHandler(ScrimManager scrimManager, Optional<CentralSurfaces> optional, NotificationShadeWindowController notificationShadeWindowController, ValueAnimatorCreator valueAnimatorCreator, VelocityTrackerFactory velocityTrackerFactory, LockPatternUtils lockPatternUtils, UserTracker userTracker, FlingAnimationUtils flingAnimationUtils, FlingAnimationUtils flingAnimationUtils2, float f, float f2, UiEventLogger uiEventLogger) {
        this.mCentralSurfaces = optional;
        this.mScrimManager = scrimManager;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mLockPatternUtils = lockPatternUtils;
        this.mUserTracker = userTracker;
        this.mBouncerZoneScreenPercentage = f;
        this.mMinBouncerZoneScreenPercentage = f2;
        this.mFlingAnimationUtils = flingAnimationUtils;
        this.mFlingAnimationUtilsClosing = flingAnimationUtils2;
        this.mValueAnimatorCreator = valueAnimatorCreator;
        this.mVelocityTrackerFactory = velocityTrackerFactory;
        this.mUiEventLogger = uiEventLogger;
    }

    @Override // com.android.systemui.ambient.touch.TouchHandler
    public final void getTouchInitiationRegion(Rect rect, Region region, Rect rect2) {
        int width = rect.width();
        int height = rect.height();
        float f = height;
        int round = Math.round((1.0f - this.mMinBouncerZoneScreenPercentage) * f);
        Rect rect3 = new Rect(0, Math.round((1.0f - this.mBouncerZoneScreenPercentage) * f), width, height);
        if (rect2 != null) {
            rect3.top = Math.max(rect3.top, Math.min(Math.max(0, rect2.bottom), round));
        }
        region.union(rect3);
    }

    @Override // com.android.systemui.ambient.touch.TouchHandler
    public final void onSessionStart(TouchMonitor.TouchSessionImpl touchSessionImpl) {
        ((BouncerSwipeModule$$ExternalSyntheticLambda0) this.mVelocityTrackerFactory).getClass();
        VelocityTracker obtain = VelocityTracker.obtain();
        this.mVelocityTracker = obtain;
        this.mTouchSession = touchSessionImpl;
        obtain.clear();
        Flags.FEATURE_FLAGS.getClass();
        ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).setForcePluginOpen(this, true);
        ScrimManager scrimManager = this.mScrimManager;
        scrimManager.mExecutor.execute(new ScrimManager$$ExternalSyntheticLambda1(scrimManager, this.mScrimManagerCallback, 1));
        this.mCurrentScrimController = scrimManager.mCurrentController;
        touchSessionImpl.mCallbacks.add(new TouchHandler$TouchSession$Callback() { // from class: com.android.systemui.ambient.touch.BouncerSwipeTouchHandler$$ExternalSyntheticLambda0
            @Override // com.android.systemui.ambient.touch.TouchHandler$TouchSession$Callback
            public final void onRemoved() {
                BouncerSwipeTouchHandler bouncerSwipeTouchHandler = BouncerSwipeTouchHandler.this;
                VelocityTracker velocityTracker = bouncerSwipeTouchHandler.mVelocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    bouncerSwipeTouchHandler.mVelocityTracker = null;
                }
                ScrimManager scrimManager2 = bouncerSwipeTouchHandler.mScrimManager;
                scrimManager2.mExecutor.execute(new ScrimManager$$ExternalSyntheticLambda1(scrimManager2, bouncerSwipeTouchHandler.mScrimManagerCallback, 0));
                bouncerSwipeTouchHandler.mCapture = null;
                bouncerSwipeTouchHandler.mTouchSession = null;
                Flags.FEATURE_FLAGS.getClass();
                ((NotificationShadeWindowControllerImpl) bouncerSwipeTouchHandler.mNotificationShadeWindowController).setForcePluginOpen(bouncerSwipeTouchHandler, false);
            }
        });
        touchSessionImpl.mGestureListeners.add(this.mOnGestureListener);
        touchSessionImpl.mEventListeners.add(new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.ambient.touch.BouncerSwipeTouchHandler$$ExternalSyntheticLambda1
            @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
            public final void onInputEvent(InputEvent inputEvent) {
                final BouncerSwipeTouchHandler bouncerSwipeTouchHandler = BouncerSwipeTouchHandler.this;
                bouncerSwipeTouchHandler.getClass();
                if (!(inputEvent instanceof MotionEvent)) {
                    Log.e("BouncerSwipeTouchHandler", "non MotionEvent received:" + inputEvent);
                    return;
                }
                MotionEvent motionEvent = (MotionEvent) inputEvent;
                int action = motionEvent.getAction();
                if (action != 1 && action != 3) {
                    bouncerSwipeTouchHandler.mVelocityTracker.addMovement(motionEvent);
                    return;
                }
                bouncerSwipeTouchHandler.mTouchSession.pop();
                Boolean bool = bouncerSwipeTouchHandler.mCapture;
                if (bool == null || !bool.booleanValue()) {
                    return;
                }
                bouncerSwipeTouchHandler.mVelocityTracker.computeCurrentVelocity(1000);
                float yVelocity = bouncerSwipeTouchHandler.mVelocityTracker.getYVelocity();
                boolean z = !(Math.abs((float) Math.hypot((double) bouncerSwipeTouchHandler.mVelocityTracker.getXVelocity(), (double) yVelocity)) >= bouncerSwipeTouchHandler.mFlingAnimationUtils.mMinVelocityPxPerSecond ? yVelocity > 0.0f : bouncerSwipeTouchHandler.mCurrentExpansion > 0.5f);
                bouncerSwipeTouchHandler.mExpanded = Boolean.valueOf(z);
                float f = z ? 0.0f : 1.0f;
                if (f == 0.0f) {
                    bouncerSwipeTouchHandler.mUiEventLogger.log(BouncerSwipeTouchHandler.DreamEvent.DREAM_SWIPED);
                }
                if (bouncerSwipeTouchHandler.mCentralSurfaces.isPresent() && bouncerSwipeTouchHandler.mLockPatternUtils.isSecure(((UserTrackerImpl) bouncerSwipeTouchHandler.mUserTracker).getUserId())) {
                    float height = bouncerSwipeTouchHandler.mTouchSession.mBounds.height();
                    float f2 = bouncerSwipeTouchHandler.mCurrentExpansion;
                    float f3 = height * f2;
                    float f4 = height * f;
                    ((BouncerSwipeModule$$ExternalSyntheticLambda0) bouncerSwipeTouchHandler.mValueAnimatorCreator).getClass();
                    ValueAnimator ofFloat = ValueAnimator.ofFloat(f2, f);
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.ambient.touch.BouncerSwipeTouchHandler$$ExternalSyntheticLambda2
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            BouncerSwipeTouchHandler bouncerSwipeTouchHandler2 = BouncerSwipeTouchHandler.this;
                            bouncerSwipeTouchHandler2.getClass();
                            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                            bouncerSwipeTouchHandler2.mCurrentExpansion = floatValue;
                            bouncerSwipeTouchHandler2.mCurrentScrimController.expand(new ShadeExpansionChangeEvent(floatValue, bouncerSwipeTouchHandler2.mExpanded.booleanValue(), true));
                        }
                    });
                    if (f == 0.0f) {
                        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.ambient.touch.BouncerSwipeTouchHandler.3
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                BouncerSwipeTouchHandler.this.mUiEventLogger.log(DreamEvent.DREAM_BOUNCER_FULLY_VISIBLE);
                            }
                        });
                    }
                    if (f == 1.0f) {
                        bouncerSwipeTouchHandler.mFlingAnimationUtilsClosing.apply(ofFloat, f3, f4, yVelocity, height);
                    } else {
                        bouncerSwipeTouchHandler.mFlingAnimationUtils.apply(ofFloat, f3, f4, yVelocity, height);
                    }
                    ofFloat.start();
                }
            }
        });
    }
}
