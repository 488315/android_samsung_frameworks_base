package com.android.wm.shell.pip.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.animation.LinearInterpolator;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.wm.shell.animation.FloatProperties;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.naturalswitching.NaturalSwitchingDropTargetController;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;

/* loaded from: classes3.dex */
public class PipNaturalSwitchingHandler {
    public static final RectEvaluator RECT_EVALUATOR = new RectEvaluator(new Rect());
    public long mInitTime;
    public SurfaceControl mLeash;
    public final ShellExecutor mMainExecutor;
    public final PhonePipMenuController mMenuController;
    public final Runnable mNaturalSwitchingStartedCallback;
    public final NaturalSwitchingDropTargetController mNsController;
    public final PipBoundsState mPipBoundsState;
    public final PipTaskOrganizer mPipTaskOrganizer;
    public final PipTouchState mPipTouchState;
    public ValueAnimator mScaleDownAnimator;
    public PhysicsAnimator mScaleUpPhysicsAnimator;
    public ActivityManager.RunningTaskInfo mTaskInfo;
    public boolean mWaitingForTaskVanished;
    public int mState = 0;
    public final PipNaturalSwitchingHandler$$ExternalSyntheticLambda0 mTaskVanishedTimeout = new PipNaturalSwitchingHandler$$ExternalSyntheticLambda0(this, 0);
    public int mTaskId = -1;

    public PipNaturalSwitchingHandler(Context context, ShellExecutor shellExecutor, PipTaskOrganizer pipTaskOrganizer, PipBoundsState pipBoundsState, PipTouchState pipTouchState, PhonePipMenuController phonePipMenuController, NaturalSwitchingDropTargetController naturalSwitchingDropTargetController, Runnable runnable) {
        this.mMainExecutor = shellExecutor;
        this.mPipTaskOrganizer = pipTaskOrganizer;
        this.mPipBoundsState = pipBoundsState;
        this.mPipTouchState = pipTouchState;
        this.mMenuController = phonePipMenuController;
        this.mNsController = naturalSwitchingDropTargetController;
        this.mNaturalSwitchingStartedCallback = runnable;
        pipTaskOrganizer.mTaskVanishedCallback = new PipNaturalSwitchingHandler$$ExternalSyntheticLambda1(this);
    }

    public static String stateToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? Integer.toString(i) : "RUNNING" : "INITIALIZING" : PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE;
    }

    public final void clearAllAnimations() {
        if (this.mScaleDownAnimator != null) {
            Log.d("PipNaturalSwitchingHandler", "clearAllAnimations: " + this.mScaleDownAnimator);
            this.mScaleDownAnimator.cancel();
            this.mScaleDownAnimator = null;
        }
        if (this.mScaleUpPhysicsAnimator != null) {
            Log.d("PipNaturalSwitchingHandler", "clearAllAnimations: " + this.mScaleUpPhysicsAnimator);
            this.mScaleUpPhysicsAnimator.cancel();
            this.mScaleUpPhysicsAnimator = null;
        }
    }

    public final void setState(int i) {
        if (this.mState == i) {
            return;
        }
        Log.d("PipNaturalSwitchingHandler", "setState: " + stateToString(this.mState) + " -> " + stateToString(i));
        this.mState = i;
        if (i == 0) {
            Log.d("PipNaturalSwitchingHandler", "onFinishNaturalSwitching: dur=" + (System.currentTimeMillis() - this.mInitTime) + "ms");
            clearAllAnimations();
            updateWaitingForTaskVanished(UniversalCredentialManager.RESET_APPLET_FORM_FACTOR, false);
            this.mInitTime = 0L;
            this.mTaskInfo = null;
            this.mLeash = null;
            this.mTaskId = -1;
            return;
        }
        if (i == 1) {
            this.mInitTime = System.currentTimeMillis();
            return;
        }
        if (i != 2) {
            return;
        }
        this.mNaturalSwitchingStartedCallback.run();
        PhonePipMenuController phonePipMenuController = this.mMenuController;
        if (phonePipMenuController.isMenuVisible()) {
            phonePipMenuController.hideMenu();
        }
        if (this.mScaleDownAnimator != null || this.mScaleUpPhysicsAnimator != null) {
            Log.w("PipNaturalSwitchingHandler", "startEnterAnimation: failed, already animating, " + this);
            return;
        }
        final Rect rect = new Rect(this.mPipBoundsState.getBounds());
        final Rect rect2 = new Rect(rect);
        final Rect rect3 = new Rect(rect);
        rect3.scale(0.96f);
        rect3.offsetTo(((rect.width() - rect3.width()) / 2) + rect.left, ((rect.height() - rect3.height()) / 2) + rect.top);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mScaleDownAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(300L);
        this.mScaleDownAnimator.setInterpolator(new LinearInterpolator());
        final String hexString = Integer.toHexString(this.mScaleDownAnimator.hashCode());
        this.mScaleDownAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.pip.phone.PipNaturalSwitchingHandler$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                PipNaturalSwitchingHandler pipNaturalSwitchingHandler = this.f$0;
                Rect rect4 = rect2;
                Rect rect5 = rect;
                Rect rect6 = rect3;
                RectEvaluator rectEvaluator = PipNaturalSwitchingHandler.RECT_EVALUATOR;
                rect4.set(PipNaturalSwitchingHandler.RECT_EVALUATOR.evaluate(((Float) valueAnimator.getAnimatedValue()).floatValue(), rect5, rect6));
                pipNaturalSwitchingHandler.mPipTaskOrganizer.scheduleUserResizePip(rect5, rect4, 0.0f, null);
            }
        });
        this.mScaleDownAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.pip.phone.PipNaturalSwitchingHandler.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("startEnterAnimation: down-scale finished, "), hexString, "PipNaturalSwitchingHandler");
                PipNaturalSwitchingHandler pipNaturalSwitchingHandler = PipNaturalSwitchingHandler.this;
                pipNaturalSwitchingHandler.mScaleDownAnimator = null;
                PhysicsAnimator physicsAnimator = pipNaturalSwitchingHandler.mScaleUpPhysicsAnimator;
                if (physicsAnimator != null) {
                    physicsAnimator.start();
                }
            }
        });
        PhysicsAnimator.Companion.getClass();
        this.mScaleUpPhysicsAnimator = PhysicsAnimator.Companion.getInstance(rect2);
        PhysicsAnimator.SpringConfig springConfig = new PhysicsAnimator.SpringConfig(220.0f, 0.47f);
        String hexString2 = Integer.toHexString(this.mScaleUpPhysicsAnimator.hashCode());
        PhysicsAnimator physicsAnimator = this.mScaleUpPhysicsAnimator;
        physicsAnimator.spring(FloatProperties.RECT_WIDTH, rect.width(), 0.0f, springConfig);
        physicsAnimator.spring(FloatProperties.RECT_HEIGHT, rect.height(), 0.0f, springConfig);
        physicsAnimator.spring(FloatProperties.RECT_X, rect.left, 0.0f, springConfig);
        physicsAnimator.spring(FloatProperties.RECT_Y, rect.top, 0.0f, springConfig);
        physicsAnimator.updateListeners.add(new PhysicsAnimator.UpdateListener() { // from class: com.android.wm.shell.pip.phone.PipNaturalSwitchingHandler$$ExternalSyntheticLambda3
            @Override // com.android.wm.shell.shared.animation.PhysicsAnimator.UpdateListener
            public final void onAnimationUpdateForProperty(Object obj) {
                Rect rect4 = rect;
                Rect rect5 = rect2;
                PipNaturalSwitchingHandler pipNaturalSwitchingHandler = this.f$0;
                if (pipNaturalSwitchingHandler.mScaleUpPhysicsAnimator != null) {
                    pipNaturalSwitchingHandler.mPipTaskOrganizer.scheduleUserResizePip(rect4, rect5, 0.0f, null);
                }
            }
        });
        physicsAnimator.withEndActions(new PipNaturalSwitchingHandler$$ExternalSyntheticLambda0(hexString2, 1));
        MediaSessions$H$$ExternalSyntheticOutline0.m("startEnterAnimation: down=", hexString, ", up=", hexString2, "PipNaturalSwitchingHandler");
        this.mScaleDownAnimator.start();
    }

    public final String toString() {
        ComponentName componentName;
        StringBuilder sb = new StringBuilder("PipNaturalSwitchingHandler{state=");
        sb.append(stateToString(this.mState));
        sb.append(", pkg=");
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        sb.append((runningTaskInfo == null || (componentName = runningTaskInfo.topActivity) == null) ? SignalSeverity.NONE : componentName.getPackageName());
        sb.append(", leash=");
        sb.append(this.mLeash);
        sb.append("}");
        return sb.toString();
    }

    public final void updateWaitingForTaskVanished(String str, boolean z) {
        if (this.mWaitingForTaskVanished != z) {
            this.mWaitingForTaskVanished = z;
            PipNaturalSwitchingHandler$$ExternalSyntheticLambda0 pipNaturalSwitchingHandler$$ExternalSyntheticLambda0 = this.mTaskVanishedTimeout;
            HandlerExecutor handlerExecutor = (HandlerExecutor) this.mMainExecutor;
            handlerExecutor.removeCallbacks(pipNaturalSwitchingHandler$$ExternalSyntheticLambda0);
            StringBuilder sb = new StringBuilder("setWaitingForTaskVanished: ");
            sb.append(z);
            sb.append(", reason=");
            ExifInterface$$ExternalSyntheticOutline0.m(sb, str, "PipNaturalSwitchingHandler");
            if (z) {
                handlerExecutor.executeDelayed(pipNaturalSwitchingHandler$$ExternalSyntheticLambda0, DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
            } else {
                setState(0);
            }
        }
    }
}
