package com.android.systemui.statusbar.phone;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.MathUtils;
import android.util.TimeUtils;
import com.android.app.animation.Interpolators;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LightBarTransitionsController implements Dumpable {
    public final DarkIntensityApplier mApplier;
    public final Callback mCallback;
    public final CommandQueue mCommandQueue;
    public float mDarkIntensity;
    public final int mDisplayId;
    public float mDozeAmount;
    public final KeyguardStateController mKeyguardStateController;
    public float mNextDarkIntensity;
    public float mPendingDarkIntensity;
    public final StatusBarStateController mStatusBarStateController;
    public ValueAnimator mTintAnimator;
    public boolean mTintChangePending;
    public boolean mTransitionDeferring;
    public long mTransitionDeferringDuration;
    public long mTransitionDeferringStartTime;
    public boolean mTransitionPending;
    public final RunnableC30701 mTransitionDeferringDoneRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.LightBarTransitionsController.1
        @Override // java.lang.Runnable
        public final void run() {
            LightBarTransitionsController.this.mTransitionDeferring = false;
        }
    };
    public boolean needGrayIcon = false;
    public boolean iconColorChanged = false;
    public final Handler mHandler = new Handler();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Callback implements CommandQueue.Callbacks, StatusBarStateController.StateListener {
        public final WeakReference mSelf;

        public Callback(LightBarTransitionsController lightBarTransitionsController) {
            this.mSelf = new WeakReference(lightBarTransitionsController);
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void appTransitionCancelled(int i) {
            LightBarTransitionsController lightBarTransitionsController = (LightBarTransitionsController) this.mSelf.get();
            if (lightBarTransitionsController == null || lightBarTransitionsController.mDisplayId != i) {
                return;
            }
            if (lightBarTransitionsController.mTransitionPending && lightBarTransitionsController.mTintChangePending) {
                lightBarTransitionsController.mTintChangePending = false;
                lightBarTransitionsController.animateIconTint(lightBarTransitionsController.mPendingDarkIntensity, 0L, lightBarTransitionsController.mApplier.getTintAnimationDuration());
            }
            lightBarTransitionsController.mTransitionPending = false;
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void appTransitionPending(int i, boolean z) {
            LightBarTransitionsController lightBarTransitionsController = (LightBarTransitionsController) this.mSelf.get();
            if (lightBarTransitionsController == null || lightBarTransitionsController.mDisplayId != i) {
                return;
            }
            if (!((KeyguardStateControllerImpl) lightBarTransitionsController.mKeyguardStateController).mKeyguardGoingAway || z) {
                lightBarTransitionsController.mTransitionPending = true;
            }
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void appTransitionStarting(int i, long j, long j2, boolean z) {
            LightBarTransitionsController lightBarTransitionsController = (LightBarTransitionsController) this.mSelf.get();
            if (lightBarTransitionsController == null || lightBarTransitionsController.mDisplayId != i) {
                return;
            }
            if (!((KeyguardStateControllerImpl) lightBarTransitionsController.mKeyguardStateController).mKeyguardGoingAway || z) {
                boolean z2 = lightBarTransitionsController.mTransitionPending;
                if (z2 && lightBarTransitionsController.mTintChangePending) {
                    lightBarTransitionsController.mTintChangePending = false;
                    lightBarTransitionsController.animateIconTint(lightBarTransitionsController.mPendingDarkIntensity, Math.max(0L, j - SystemClock.uptimeMillis()), j2);
                } else if (z2) {
                    lightBarTransitionsController.mTransitionDeferring = true;
                    lightBarTransitionsController.mTransitionDeferringStartTime = j;
                    lightBarTransitionsController.mTransitionDeferringDuration = j2;
                    Handler handler = lightBarTransitionsController.mHandler;
                    RunnableC30701 runnableC30701 = lightBarTransitionsController.mTransitionDeferringDoneRunnable;
                    handler.removeCallbacks(runnableC30701);
                    handler.postAtTime(runnableC30701, j);
                }
                lightBarTransitionsController.mTransitionPending = false;
            }
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onDozeAmountChanged(float f, float f2) {
            LightBarTransitionsController lightBarTransitionsController = (LightBarTransitionsController) this.mSelf.get();
            if (lightBarTransitionsController != null) {
                lightBarTransitionsController.mDozeAmount = f2;
                if (BasicRune.NAVBAR_ENABLED) {
                    return;
                }
                lightBarTransitionsController.mApplier.applyDarkIntensity(MathUtils.lerp(lightBarTransitionsController.mDarkIntensity, 0.0f, f2));
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface DarkIntensityApplier {
        void applyDarkIntensity(float f);

        int getTintAnimationDuration();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        LightBarTransitionsController create(DarkIntensityApplier darkIntensityApplier);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.statusbar.phone.LightBarTransitionsController$1] */
    public LightBarTransitionsController(Context context, DarkIntensityApplier darkIntensityApplier, CommandQueue commandQueue, KeyguardStateController keyguardStateController, StatusBarStateController statusBarStateController) {
        this.mApplier = darkIntensityApplier;
        this.mKeyguardStateController = keyguardStateController;
        this.mStatusBarStateController = statusBarStateController;
        this.mCommandQueue = commandQueue;
        Callback callback = new Callback(this);
        this.mCallback = callback;
        commandQueue.addCallback((CommandQueue.Callbacks) callback);
        statusBarStateController.addCallback(callback);
        this.mDozeAmount = statusBarStateController.getDozeAmount();
        this.mDisplayId = context.getDisplayId();
    }

    public final void animateIconTint(float f, long j, long j2) {
        if (this.mNextDarkIntensity != f || this.iconColorChanged) {
            ValueAnimator valueAnimator = this.mTintAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            this.mNextDarkIntensity = f;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mDarkIntensity, f);
            this.mTintAnimator = ofFloat;
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.statusbar.phone.LightBarTransitionsController$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    LightBarTransitionsController lightBarTransitionsController = LightBarTransitionsController.this;
                    lightBarTransitionsController.getClass();
                    float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                    lightBarTransitionsController.mDarkIntensity = floatValue;
                    boolean z = BasicRune.NAVBAR_LIGHTBAR;
                    LightBarTransitionsController.DarkIntensityApplier darkIntensityApplier = lightBarTransitionsController.mApplier;
                    if (z) {
                        darkIntensityApplier.applyDarkIntensity(floatValue);
                    } else {
                        if (BasicRune.NAVBAR_ENABLED) {
                            return;
                        }
                        darkIntensityApplier.applyDarkIntensity(MathUtils.lerp(floatValue, 0.0f, lightBarTransitionsController.mDozeAmount));
                    }
                }
            });
            this.mTintAnimator.setDuration(j2);
            this.mTintAnimator.setStartDelay(j);
            this.mTintAnimator.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
            this.mTintAnimator.start();
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print("  mTransitionDeferring=");
        printWriter.print(this.mTransitionDeferring);
        if (this.mTransitionDeferring) {
            printWriter.println();
            printWriter.print("   mTransitionDeferringStartTime=");
            printWriter.println(TimeUtils.formatUptime(this.mTransitionDeferringStartTime));
            printWriter.print("   mTransitionDeferringDuration=");
            TimeUtils.formatDuration(this.mTransitionDeferringDuration, printWriter);
            printWriter.println();
        }
        printWriter.print("  mTransitionPending=");
        printWriter.print(this.mTransitionPending);
        printWriter.print(" mTintChangePending=");
        printWriter.println(this.mTintChangePending);
        printWriter.print("  mPendingDarkIntensity=");
        printWriter.print(this.mPendingDarkIntensity);
        printWriter.print(" mDarkIntensity=");
        printWriter.print(this.mDarkIntensity);
        printWriter.print(" mNextDarkIntensity=");
        printWriter.println(this.mNextDarkIntensity);
    }

    public final void setIconsDark(boolean z, boolean z2) {
        DarkIntensityApplier darkIntensityApplier = this.mApplier;
        if (!z2) {
            float f = z ? 1.0f : 0.0f;
            this.mDarkIntensity = f;
            if (BasicRune.NAVBAR_LIGHTBAR) {
                darkIntensityApplier.applyDarkIntensity(f);
            } else if (!BasicRune.NAVBAR_ENABLED) {
                darkIntensityApplier.applyDarkIntensity(MathUtils.lerp(f, 0.0f, this.mDozeAmount));
            }
            this.mNextDarkIntensity = z ? 1.0f : 0.0f;
            return;
        }
        if (!this.mTransitionPending) {
            if (this.mTransitionDeferring) {
                animateIconTint(z ? 1.0f : 0.0f, Math.max(0L, this.mTransitionDeferringStartTime - SystemClock.uptimeMillis()), this.mTransitionDeferringDuration);
                return;
            } else {
                animateIconTint(z ? 1.0f : 0.0f, 0L, darkIntensityApplier.getTintAnimationDuration());
                return;
            }
        }
        float f2 = z ? 1.0f : 0.0f;
        if (this.mTintChangePending && f2 == this.mPendingDarkIntensity) {
            return;
        }
        this.mTintChangePending = true;
        this.mPendingDarkIntensity = f2;
    }
}
