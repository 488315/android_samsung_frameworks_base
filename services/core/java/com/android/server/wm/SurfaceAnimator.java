package com.android.server.wm;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.SystemClock;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.protolog.common.LogLevel;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Supplier;

public class SurfaceAnimator {
    final Animatable mAnimatable;
    public AnimationAdapter mAnimation;
    public Runnable mAnimationCancelledCallback;
    public boolean mAnimationFinished;
    public boolean mAnimationStartDelayed;
    public int mAnimationType;
    final OnAnimationFinishedCallback mInnerAnimationFinishedCallback;
    SurfaceControl mLeash;
    public final WindowManagerService mService;
    SurfaceFreezer.Snapshot mSnapshot;
    final OnAnimationFinishedCallback mStaticAnimationFinishedCallback;
    public OnAnimationFinishedCallback mSurfaceAnimationFinishedCallback;

    public interface Animatable {
        void commitPendingTransaction();

        default SurfaceControl getAnimationLeash() {
            return null;
        }

        SurfaceControl getAnimationLeashParent();

        SurfaceControl getParentSurfaceControl();

        SurfaceControl getSurfaceControl();

        int getSurfaceHeight();

        int getSurfaceWidth();

        SurfaceControl.Transaction getSyncTransaction();

        SurfaceControl.Builder makeAnimationLeash();

        void onAnimationLeashCreated(
                SurfaceControl.Transaction transaction, SurfaceControl surfaceControl);

        void onAnimationLeashLost(SurfaceControl.Transaction transaction);

        default void onLeashAnimationStarting(
                SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {}

        default boolean shouldDeferAnimationFinish(Runnable runnable) {
            return false;
        }
    }

    public interface OnAnimationFinishedCallback {
        void onAnimationFinished(int i, AnimationAdapter animationAdapter);
    }

    public SurfaceAnimator(
            Animatable animatable,
            final OnAnimationFinishedCallback onAnimationFinishedCallback,
            WindowManagerService windowManagerService) {
        this.mAnimatable = animatable;
        this.mService = windowManagerService;
        this.mStaticAnimationFinishedCallback = onAnimationFinishedCallback;
        this.mInnerAnimationFinishedCallback =
                new OnAnimationFinishedCallback() { // from class:
                                                    // com.android.server.wm.SurfaceAnimator$$ExternalSyntheticLambda0
                    @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
                    public final void onAnimationFinished(
                            final int i, final AnimationAdapter animationAdapter) {
                        final SurfaceAnimator surfaceAnimator = SurfaceAnimator.this;
                        final SurfaceAnimator.OnAnimationFinishedCallback
                                onAnimationFinishedCallback2 = onAnimationFinishedCallback;
                        WindowManagerGlobalLock windowManagerGlobalLock =
                                surfaceAnimator.mService.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                SurfaceAnimator surfaceAnimator2 =
                                        (SurfaceAnimator)
                                                surfaceAnimator.mService.mAnimationTransferMap
                                                        .remove(animationAdapter);
                                if (surfaceAnimator2 != null) {
                                    surfaceAnimator2.mInnerAnimationFinishedCallback
                                            .onAnimationFinished(i, animationAdapter);
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                } else {
                                    if (animationAdapter != surfaceAnimator.mAnimation) {
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                        return;
                                    }
                                    Runnable runnable =
                                            new Runnable() { // from class:
                                                             // com.android.server.wm.SurfaceAnimator$$ExternalSyntheticLambda1
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    SurfaceAnimator surfaceAnimator3 =
                                                            SurfaceAnimator.this;
                                                    AnimationAdapter animationAdapter2 =
                                                            animationAdapter;
                                                    SurfaceAnimator.OnAnimationFinishedCallback
                                                            onAnimationFinishedCallback3 =
                                                                    onAnimationFinishedCallback2;
                                                    int i2 = i;
                                                    if (animationAdapter2
                                                            != surfaceAnimator3.mAnimation) {
                                                        return;
                                                    }
                                                    SurfaceAnimator.OnAnimationFinishedCallback
                                                            onAnimationFinishedCallback4 =
                                                                    surfaceAnimator3
                                                                            .mSurfaceAnimationFinishedCallback;
                                                    surfaceAnimator3.reset(
                                                            surfaceAnimator3.mAnimatable
                                                                    .getSyncTransaction(),
                                                            true);
                                                    if (onAnimationFinishedCallback3 != null) {
                                                        onAnimationFinishedCallback3
                                                                .onAnimationFinished(
                                                                        i2, animationAdapter2);
                                                    }
                                                    if (onAnimationFinishedCallback4 != null) {
                                                        onAnimationFinishedCallback4
                                                                .onAnimationFinished(
                                                                        i2, animationAdapter2);
                                                    }
                                                }
                                            };
                                    if (!surfaceAnimator.mAnimatable.shouldDeferAnimationFinish(
                                                    runnable)
                                            && !animationAdapter.shouldDeferAnimationFinish()) {
                                        runnable.run();
                                    }
                                    surfaceAnimator.mAnimationFinished = true;
                                    WindowManagerService.resetPriorityAfterLockedSection();
                                }
                            } catch (Throwable th) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                throw th;
                            }
                        }
                    }
                };
    }

    public static String animationTypeToString(int i) {
        return i != 0
                ? i != 1
                        ? i != 2
                                ? i != 4
                                        ? i != 8
                                                ? i != 16
                                                        ? i != 32
                                                                ? i != 64
                                                                        ? i != 128
                                                                                ? i != 256
                                                                                        ? (i == 512
                                                                                                        && CoreRune
                                                                                                                .FW_REMOTE_WALLPAPER_ANIM)
                                                                                                ? "remote_wallpaper"
                                                                                                : VibrationParam$1$$ExternalSyntheticOutline0
                                                                                                        .m(
                                                                                                                i,
                                                                                                                "unknown"
                                                                                                                    + " type:")
                                                                                        : "predict_back"
                                                                                : "starting_reveal"
                                                                        : "token_transform"
                                                                : "insets_animation"
                                                        : "window_animation"
                                                : "recents_animation"
                                        : "dimmer"
                                : "screen_rotation"
                        : "app_transition"
                : "none";
    }

    public static SurfaceControl createAnimationLeash(
            Animatable animatable,
            SurfaceControl surfaceControl,
            SurfaceControl.Transaction transaction,
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            boolean z) {
        Slog.i(
                "WindowManager",
                "Reparenting to leash, surface="
                        + surfaceControl
                        + ", leashParent="
                        + animatable.getAnimationLeashParent()
                        + "");
        SurfaceControl.Builder callsite =
                animatable
                        .makeAnimationLeash()
                        .setParent(animatable.getAnimationLeashParent())
                        .setName(
                                surfaceControl
                                        + " - animation-leash of "
                                        + animationTypeToString(i))
                        .setHidden(z)
                        .setEffectLayer()
                        .setCallsite("SurfaceAnimator.createAnimationLeash");
        long uptimeMillis = SystemClock.uptimeMillis();
        SurfaceControl build = callsite.build();
        Slog.d(
                "WindowManager",
                "makeSurface duration="
                        + (SystemClock.uptimeMillis() - uptimeMillis)
                        + " leash="
                        + build);
        transaction.setWindowCrop(build, i2, i3);
        transaction.setPosition(build, (float) i4, (float) i5);
        transaction.show(build);
        transaction.setAlpha(build, z ? FullScreenMagnificationGestureHandler.MAX_SCALE : 1.0f);
        transaction.reparent(surfaceControl, build);
        return build;
    }

    public static boolean removeLeash(
            SurfaceControl.Transaction transaction,
            Animatable animatable,
            SurfaceControl surfaceControl,
            boolean z) {
        SurfaceControl surfaceControl2 = animatable.getSurfaceControl();
        SurfaceControl parentSurfaceControl = animatable.getParentSurfaceControl();
        SurfaceControl animationLeash = animatable.getAnimationLeash();
        boolean z2 = false;
        boolean z3 =
                surfaceControl2 != null
                        && (animationLeash == null || animationLeash.equals(surfaceControl));
        if (z3) {
            Slog.i(
                    "WindowManager",
                    "Reparenting to original parent: "
                            + parentSurfaceControl
                            + ", destroy="
                            + z
                            + ", surface="
                            + surfaceControl2
                            + "");
            if (surfaceControl2.isValid()
                    && parentSurfaceControl != null
                    && parentSurfaceControl.isValid()) {
                transaction.reparent(surfaceControl2, parentSurfaceControl);
                z2 = true;
            }
        }
        if (z) {
            transaction.remove(surfaceControl);
            z2 = true;
        }
        if (!z3) {
            return z2;
        }
        animatable.onAnimationLeashLost(transaction);
        return true;
    }

    public final void cancelAnimation() {
        cancelAnimation(this.mAnimatable.getSyncTransaction(), false, true);
        this.mAnimatable.commitPendingTransaction();
    }

    public final void cancelAnimation(
            SurfaceControl.Transaction transaction, boolean z, boolean z2) {
        if (this.mLeash != null) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "Cancelling animation restarting=", ", leash=", z);
            m.append(this.mLeash);
            m.append("");
            Slog.i("WindowManager", m.toString());
        } else if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ANIM_enabled[2]) {
            ProtoLogImpl_54989576.i(
                    ProtoLogGroup.WM_DEBUG_ANIM,
                    -5370506662233296228L,
                    3,
                    null,
                    Boolean.valueOf(z),
                    String.valueOf(this.mAnimatable));
        }
        SurfaceControl surfaceControl = this.mLeash;
        AnimationAdapter animationAdapter = this.mAnimation;
        int i = this.mAnimationType;
        OnAnimationFinishedCallback onAnimationFinishedCallback =
                this.mSurfaceAnimationFinishedCallback;
        Runnable runnable = this.mAnimationCancelledCallback;
        SurfaceFreezer.Snapshot snapshot = this.mSnapshot;
        reset(transaction, false);
        if (animationAdapter != null) {
            if (!this.mAnimationStartDelayed && z2) {
                animationAdapter.onAnimationCancelled(surfaceControl);
                if (runnable != null) {
                    runnable.run();
                }
            }
            if (!z) {
                OnAnimationFinishedCallback onAnimationFinishedCallback2 =
                        this.mStaticAnimationFinishedCallback;
                if (onAnimationFinishedCallback2 != null) {
                    onAnimationFinishedCallback2.onAnimationFinished(i, animationAdapter);
                }
                if (onAnimationFinishedCallback != null) {
                    onAnimationFinishedCallback.onAnimationFinished(i, animationAdapter);
                }
            }
        }
        if (z2) {
            if (snapshot != null) {
                snapshot.cancelAnimation(transaction, false);
            }
            if (surfaceControl != null) {
                transaction.remove(surfaceControl);
                this.mService.scheduleAnimationLocked();
            }
        }
        if (z) {
            return;
        }
        this.mAnimationStartDelayed = false;
    }

    public final void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        AnimationAdapter animationAdapter = this.mAnimation;
        if (animationAdapter != null) {
            animationAdapter.dumpDebug(protoOutputStream);
        }
        SurfaceControl surfaceControl = this.mLeash;
        if (surfaceControl != null) {
            surfaceControl.dumpDebug(protoOutputStream, 1146756268033L);
        }
        protoOutputStream.write(1133871366146L, this.mAnimationStartDelayed);
        protoOutputStream.end(start);
    }

    public final void endDelayingAnimationStart() {
        AnimationAdapter animationAdapter;
        boolean z = this.mAnimationStartDelayed;
        this.mAnimationStartDelayed = false;
        if (!z || (animationAdapter = this.mAnimation) == null) {
            return;
        }
        animationAdapter.startAnimation(
                this.mLeash,
                this.mAnimatable.getSyncTransaction(),
                this.mAnimationType,
                this.mInnerAnimationFinishedCallback);
        this.mAnimatable.commitPendingTransaction();
    }

    public final boolean hasLeash() {
        return this.mLeash != null;
    }

    public final boolean isAnimating() {
        return this.mAnimation != null;
    }

    public final void reset(SurfaceControl.Transaction transaction, boolean z) {
        WindowManagerService windowManagerService = this.mService;
        windowManagerService.mAnimationTransferMap.remove(this.mAnimation);
        this.mAnimation = null;
        this.mSurfaceAnimationFinishedCallback = null;
        this.mAnimationType = 0;
        SurfaceFreezer.Snapshot snapshot = this.mSnapshot;
        this.mSnapshot = null;
        if (snapshot != null) {
            snapshot.cancelAnimation(transaction, !z);
        }
        SurfaceControl surfaceControl = this.mLeash;
        if (surfaceControl == null) {
            return;
        }
        this.mLeash = null;
        boolean removeLeash = removeLeash(transaction, this.mAnimatable, surfaceControl, z);
        this.mAnimationFinished = false;
        if (removeLeash) {
            windowManagerService.scheduleAnimationLocked();
        }
    }

    public final void startAnimation(
            SurfaceControl.Transaction transaction,
            AnimationAdapter animationAdapter,
            boolean z,
            int i,
            OnAnimationFinishedCallback onAnimationFinishedCallback,
            Runnable runnable,
            AnimationAdapter animationAdapter2,
            SurfaceFreezer surfaceFreezer) {
        SurfaceControl surfaceControl;
        SurfaceFreezer.Snapshot snapshot;
        cancelAnimation(transaction, true, true);
        this.mAnimation = animationAdapter;
        this.mAnimationType = i;
        this.mSurfaceAnimationFinishedCallback = onAnimationFinishedCallback;
        this.mAnimationCancelledCallback = runnable;
        SurfaceControl surfaceControl2 = this.mAnimatable.getSurfaceControl();
        if (surfaceControl2 == null) {
            Slog.w("WindowManager", "Unable to start animation, surface is null or no children.");
            cancelAnimation();
            return;
        }
        if (surfaceFreezer != null) {
            surfaceControl = surfaceFreezer.mLeash;
            surfaceFreezer.mLeash = null;
        } else {
            surfaceControl = null;
        }
        this.mLeash = surfaceControl;
        if (surfaceControl == null) {
            Animatable animatable = this.mAnimatable;
            int surfaceWidth = animatable.getSurfaceWidth();
            int surfaceHeight = this.mAnimatable.getSurfaceHeight();
            Supplier supplier = this.mService.mTransactionFactory;
            snapshot = null;
            SurfaceControl createAnimationLeash =
                    createAnimationLeash(
                            animatable,
                            surfaceControl2,
                            transaction,
                            i,
                            surfaceWidth,
                            surfaceHeight,
                            0,
                            0,
                            z);
            this.mLeash = createAnimationLeash;
            this.mAnimatable.onAnimationLeashCreated(transaction, createAnimationLeash);
        } else {
            snapshot = null;
        }
        this.mAnimatable.onLeashAnimationStarting(transaction, this.mLeash);
        boolean z2 = this.mAnimationStartDelayed;
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_ANIM_enabled;
        if (z2) {
            if (zArr[2]) {
                ProtoLogImpl_54989576.i(
                        ProtoLogGroup.WM_DEBUG_ANIM,
                        -820649637734629482L,
                        0,
                        null,
                        String.valueOf(this.mAnimatable));
                return;
            }
            return;
        }
        this.mAnimation.startAnimation(
                this.mLeash, transaction, i, this.mInnerAnimationFinishedCallback);
        ProtoLogGroup protoLogGroup = ProtoLogGroup.WM_DEBUG_ANIM;
        if (ProtoLogImpl_54989576.isEnabled(protoLogGroup, LogLevel.DEBUG)) {
            StringWriter stringWriter = new StringWriter();
            this.mAnimation.dump(new PrintWriter(stringWriter), "");
            if (zArr[0]) {
                ProtoLogImpl_54989576.d(
                        protoLogGroup,
                        1371702561758591499L,
                        0,
                        null,
                        String.valueOf(this.mAnimatable),
                        String.valueOf(stringWriter));
            }
        }
        if (animationAdapter2 != null) {
            SurfaceFreezer.Snapshot snapshot2 = surfaceFreezer.mSnapshot;
            surfaceFreezer.mSnapshot = snapshot;
            this.mSnapshot = snapshot2;
            if (snapshot2 == null) {
                Slog.e(
                        "WindowManager",
                        "No snapshot target to start animation on for " + this.mAnimatable);
                return;
            }
            snapshot2.cancelAnimation(transaction, true);
            snapshot2.mAnimation = animationAdapter2;
            SurfaceControl surfaceControl3 = snapshot2.mSurfaceControl;
            if (surfaceControl3 == null) {
                snapshot2.cancelAnimation(transaction, false);
            } else {
                animationAdapter2.startAnimation(
                        surfaceControl3,
                        transaction,
                        i,
                        new SurfaceFreezer$Snapshot$$ExternalSyntheticLambda0());
            }
        }
    }
}
