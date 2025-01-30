package com.android.systemui.dreams.touch;

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
import com.android.p038wm.shell.animation.FlingAnimationUtils;
import com.android.systemui.dreams.touch.BouncerSwipeTouchHandler;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import com.android.systemui.dreams.touch.DreamTouchHandler;
import com.android.systemui.dreams.touch.dagger.BouncerSwipeModule$$ExternalSyntheticLambda0;
import com.android.systemui.dreams.touch.scrim.ScrimController;
import com.android.systemui.dreams.touch.scrim.ScrimManager;
import com.android.systemui.dreams.touch.scrim.ScrimManager$$ExternalSyntheticLambda1;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BouncerSwipeTouchHandler implements DreamTouchHandler {
    public boolean mBouncerInitiallyShowing;
    public final float mBouncerZoneScreenPercentage;
    public Boolean mCapture;
    public final Optional mCentralSurfaces;
    public float mCurrentExpansion;
    public ScrimController mCurrentScrimController;
    public Boolean mExpanded;
    public final FlingAnimationUtils mFlingAnimationUtils;
    public final FlingAnimationUtils mFlingAnimationUtilsClosing;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final ScrimManager mScrimManager;
    public DreamTouchHandler.TouchSession mTouchSession;
    public final UiEventLogger mUiEventLogger;
    public final ValueAnimatorCreator mValueAnimatorCreator;
    public VelocityTracker mVelocityTracker;
    public final VelocityTrackerFactory mVelocityTrackerFactory;
    public final C12851 mScrimManagerCallback = new C12851();
    public final C12862 mOnGestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.dreams.touch.BouncerSwipeTouchHandler.2
        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            BouncerSwipeTouchHandler bouncerSwipeTouchHandler = BouncerSwipeTouchHandler.this;
            int i = 1;
            if (bouncerSwipeTouchHandler.mCapture == null) {
                bouncerSwipeTouchHandler.mCapture = Boolean.valueOf(Math.abs(f2) > Math.abs(f));
                BouncerSwipeTouchHandler bouncerSwipeTouchHandler2 = BouncerSwipeTouchHandler.this;
                Optional map = bouncerSwipeTouchHandler2.mCentralSurfaces.map(new BouncerSwipeTouchHandler$$ExternalSyntheticLambda2(i));
                Boolean bool = Boolean.FALSE;
                bouncerSwipeTouchHandler2.mBouncerInitiallyShowing = ((Boolean) map.orElse(bool)).booleanValue();
                if (BouncerSwipeTouchHandler.this.mCapture.booleanValue()) {
                    BouncerSwipeTouchHandler bouncerSwipeTouchHandler3 = BouncerSwipeTouchHandler.this;
                    bouncerSwipeTouchHandler3.mExpanded = bool;
                    bouncerSwipeTouchHandler3.mCurrentScrimController.show();
                }
            }
            if (!BouncerSwipeTouchHandler.this.mCapture.booleanValue()) {
                return false;
            }
            if (!BouncerSwipeTouchHandler.this.mBouncerInitiallyShowing && motionEvent.getY() < motionEvent2.getY()) {
                return true;
            }
            if ((BouncerSwipeTouchHandler.this.mBouncerInitiallyShowing && motionEvent.getY() > motionEvent2.getY()) || !BouncerSwipeTouchHandler.this.mCentralSurfaces.isPresent()) {
                return true;
            }
            float y = motionEvent2.getY() - motionEvent.getY();
            float abs = Math.abs(motionEvent.getY() - motionEvent2.getY()) / ((DreamOverlayTouchMonitor.TouchSessionImpl) BouncerSwipeTouchHandler.this.mTouchSession).mBounds.height();
            BouncerSwipeTouchHandler bouncerSwipeTouchHandler4 = BouncerSwipeTouchHandler.this;
            if (!bouncerSwipeTouchHandler4.mBouncerInitiallyShowing) {
                abs = 1.0f - abs;
            }
            bouncerSwipeTouchHandler4.mCurrentExpansion = abs;
            bouncerSwipeTouchHandler4.mCurrentScrimController.expand(new ShadeExpansionChangeEvent(abs, bouncerSwipeTouchHandler4.mExpanded.booleanValue(), true, y));
            return true;
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.dreams.touch.BouncerSwipeTouchHandler$1 */
    public final class C12851 {
        public C12851() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ValueAnimatorCreator {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface VelocityTrackerFactory {
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.dreams.touch.BouncerSwipeTouchHandler$2] */
    public BouncerSwipeTouchHandler(ScrimManager scrimManager, Optional<CentralSurfaces> optional, NotificationShadeWindowController notificationShadeWindowController, ValueAnimatorCreator valueAnimatorCreator, VelocityTrackerFactory velocityTrackerFactory, FlingAnimationUtils flingAnimationUtils, FlingAnimationUtils flingAnimationUtils2, float f, UiEventLogger uiEventLogger) {
        this.mCentralSurfaces = optional;
        this.mScrimManager = scrimManager;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mBouncerZoneScreenPercentage = f;
        this.mFlingAnimationUtils = flingAnimationUtils;
        this.mFlingAnimationUtilsClosing = flingAnimationUtils2;
        this.mValueAnimatorCreator = valueAnimatorCreator;
        this.mVelocityTrackerFactory = velocityTrackerFactory;
        this.mUiEventLogger = uiEventLogger;
    }

    @Override // com.android.systemui.dreams.touch.DreamTouchHandler
    public final void getTouchInitiationRegion(Rect rect, Region region) {
        int width = rect.width();
        int height = rect.height();
        boolean booleanValue = ((Boolean) this.mCentralSurfaces.map(new BouncerSwipeTouchHandler$$ExternalSyntheticLambda2(0)).orElse(Boolean.FALSE)).booleanValue();
        float f = this.mBouncerZoneScreenPercentage;
        if (booleanValue) {
            region.op(new Rect(0, 0, width, Math.round(height * f)), Region.Op.UNION);
        } else {
            region.op(new Rect(0, Math.round((1.0f - f) * height), width, height), Region.Op.UNION);
        }
    }

    @Override // com.android.systemui.dreams.touch.DreamTouchHandler
    public final void onSessionStart(DreamTouchHandler.TouchSession touchSession) {
        ((BouncerSwipeModule$$ExternalSyntheticLambda0) this.mVelocityTrackerFactory).getClass();
        VelocityTracker obtain = VelocityTracker.obtain();
        this.mVelocityTracker = obtain;
        this.mTouchSession = touchSession;
        obtain.clear();
        ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).setForcePluginOpen(this, true);
        ScrimManager scrimManager = this.mScrimManager;
        scrimManager.getClass();
        scrimManager.mExecutor.execute(new ScrimManager$$ExternalSyntheticLambda1(scrimManager, this.mScrimManagerCallback, 0));
        this.mCurrentScrimController = scrimManager.mCurrentController;
        DreamOverlayTouchMonitor.TouchSessionImpl touchSessionImpl = (DreamOverlayTouchMonitor.TouchSessionImpl) touchSession;
        touchSessionImpl.mCallbacks.add(new BouncerSwipeTouchHandler$$ExternalSyntheticLambda0(this));
        touchSessionImpl.mGestureListeners.add(this.mOnGestureListener);
        touchSessionImpl.mEventListeners.add(new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.dreams.touch.BouncerSwipeTouchHandler$$ExternalSyntheticLambda1
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
                ((DreamOverlayTouchMonitor.TouchSessionImpl) bouncerSwipeTouchHandler.mTouchSession).pop();
                Boolean bool = bouncerSwipeTouchHandler.mCapture;
                if (bool == null || !bool.booleanValue()) {
                    return;
                }
                bouncerSwipeTouchHandler.mVelocityTracker.computeCurrentVelocity(1000);
                float yVelocity = bouncerSwipeTouchHandler.mVelocityTracker.getYVelocity();
                Boolean valueOf = Boolean.valueOf(!(Math.abs((float) Math.hypot((double) bouncerSwipeTouchHandler.mVelocityTracker.getXVelocity(), (double) yVelocity)) >= bouncerSwipeTouchHandler.mFlingAnimationUtils.mMinVelocityPxPerSecond ? yVelocity > 0.0f : bouncerSwipeTouchHandler.mCurrentExpansion > 0.5f));
                bouncerSwipeTouchHandler.mExpanded = valueOf;
                float f = valueOf.booleanValue() ? 0.0f : 1.0f;
                if (!bouncerSwipeTouchHandler.mBouncerInitiallyShowing && f == 0.0f) {
                    bouncerSwipeTouchHandler.mUiEventLogger.log(BouncerSwipeTouchHandler.DreamEvent.DREAM_SWIPED);
                }
                if (bouncerSwipeTouchHandler.mCentralSurfaces.isPresent()) {
                    float height = ((DreamOverlayTouchMonitor.TouchSessionImpl) bouncerSwipeTouchHandler.mTouchSession).mBounds.height();
                    float f2 = bouncerSwipeTouchHandler.mCurrentExpansion;
                    float f3 = height * f2;
                    float f4 = height * f;
                    final float f5 = f4 - f3;
                    ((BouncerSwipeModule$$ExternalSyntheticLambda0) bouncerSwipeTouchHandler.mValueAnimatorCreator).getClass();
                    ValueAnimator ofFloat = ValueAnimator.ofFloat(f2, f);
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.dreams.touch.BouncerSwipeTouchHandler$$ExternalSyntheticLambda3
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            BouncerSwipeTouchHandler bouncerSwipeTouchHandler2 = BouncerSwipeTouchHandler.this;
                            float f6 = f5;
                            bouncerSwipeTouchHandler2.getClass();
                            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                            bouncerSwipeTouchHandler2.mCurrentExpansion = floatValue;
                            bouncerSwipeTouchHandler2.mCurrentScrimController.expand(new ShadeExpansionChangeEvent(floatValue, bouncerSwipeTouchHandler2.mExpanded.booleanValue(), true, f6 * floatValue));
                        }
                    });
                    if (!bouncerSwipeTouchHandler.mBouncerInitiallyShowing && f == 0.0f) {
                        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.dreams.touch.BouncerSwipeTouchHandler.3
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
