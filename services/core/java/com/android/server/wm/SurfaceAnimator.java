package com.android.server.wm;

import android.os.Debug;
import android.os.SystemClock;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.server.display.DisplayPowerController2;
import com.android.server.wm.SurfaceFreezer;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
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

        void onAnimationLeashCreated(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl);

        void onAnimationLeashLost(SurfaceControl.Transaction transaction);

        default void onLeashAnimationStarting(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        }

        default boolean shouldDeferAnimationFinish(Runnable runnable) {
            return false;
        }
    }

    public interface OnAnimationFinishedCallback {
        void onAnimationFinished(int i, AnimationAdapter animationAdapter);
    }

    public SurfaceAnimator(Animatable animatable, OnAnimationFinishedCallback onAnimationFinishedCallback, WindowManagerService windowManagerService) {
        this.mAnimatable = animatable;
        this.mService = windowManagerService;
        this.mStaticAnimationFinishedCallback = onAnimationFinishedCallback;
        this.mInnerAnimationFinishedCallback = getFinishedCallback(onAnimationFinishedCallback);
    }

    public final OnAnimationFinishedCallback getFinishedCallback(final OnAnimationFinishedCallback onAnimationFinishedCallback) {
        return new OnAnimationFinishedCallback() { // from class: com.android.server.wm.SurfaceAnimator$$ExternalSyntheticLambda0
            @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
            public final void onAnimationFinished(int i, AnimationAdapter animationAdapter) {
                SurfaceAnimator.this.lambda$getFinishedCallback$1(onAnimationFinishedCallback, i, animationAdapter);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getFinishedCallback$1(final OnAnimationFinishedCallback onAnimationFinishedCallback, final int i, final AnimationAdapter animationAdapter) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                SurfaceAnimator surfaceAnimator = (SurfaceAnimator) this.mService.mAnimationTransferMap.remove(animationAdapter);
                if (surfaceAnimator != null) {
                    surfaceAnimator.mInnerAnimationFinishedCallback.onAnimationFinished(i, animationAdapter);
                    WindowManagerService.resetPriorityAfterLockedSection();
                } else {
                    if (animationAdapter != this.mAnimation) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    Runnable runnable = new Runnable() { // from class: com.android.server.wm.SurfaceAnimator$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SurfaceAnimator.this.lambda$getFinishedCallback$0(animationAdapter, onAnimationFinishedCallback, i);
                        }
                    };
                    if (!this.mAnimatable.shouldDeferAnimationFinish(runnable) && !animationAdapter.shouldDeferAnimationFinish(runnable)) {
                        runnable.run();
                    }
                    this.mAnimationFinished = true;
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getFinishedCallback$0(AnimationAdapter animationAdapter, OnAnimationFinishedCallback onAnimationFinishedCallback, int i) {
        if (animationAdapter != this.mAnimation) {
            return;
        }
        OnAnimationFinishedCallback onAnimationFinishedCallback2 = this.mSurfaceAnimationFinishedCallback;
        reset(this.mAnimatable.getSyncTransaction(), true);
        if (onAnimationFinishedCallback != null) {
            onAnimationFinishedCallback.onAnimationFinished(i, animationAdapter);
        }
        if (onAnimationFinishedCallback2 != null) {
            onAnimationFinishedCallback2.onAnimationFinished(i, animationAdapter);
        }
    }

    public void startAnimation(SurfaceControl.Transaction transaction, AnimationAdapter animationAdapter, boolean z, int i, OnAnimationFinishedCallback onAnimationFinishedCallback, Runnable runnable, AnimationAdapter animationAdapter2, SurfaceFreezer surfaceFreezer) {
        cancelAnimation(transaction, true, true);
        this.mAnimation = animationAdapter;
        this.mAnimationType = i;
        this.mSurfaceAnimationFinishedCallback = onAnimationFinishedCallback;
        this.mAnimationCancelledCallback = runnable;
        SurfaceControl surfaceControl = this.mAnimatable.getSurfaceControl();
        if (surfaceControl == null) {
            Slog.w(StartingSurfaceController.TAG, "Unable to start animation, surface is null or no children.");
            cancelAnimation();
            return;
        }
        SurfaceControl takeLeashForAnimation = surfaceFreezer != null ? surfaceFreezer.takeLeashForAnimation() : null;
        this.mLeash = takeLeashForAnimation;
        if (takeLeashForAnimation == null) {
            Animatable animatable = this.mAnimatable;
            SurfaceControl createAnimationLeash = createAnimationLeash(animatable, surfaceControl, transaction, i, animatable.getSurfaceWidth(), this.mAnimatable.getSurfaceHeight(), 0, 0, z, this.mService.mTransactionFactory);
            this.mLeash = createAnimationLeash;
            this.mAnimatable.onAnimationLeashCreated(transaction, createAnimationLeash);
        }
        this.mAnimatable.onLeashAnimationStarting(transaction, this.mLeash);
        if (this.mAnimationStartDelayed) {
            if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
                ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_ANIM, 215077284, 0, (String) null, new Object[]{String.valueOf(this.mAnimatable)});
                return;
            }
            return;
        }
        this.mAnimation.startAnimation(this.mLeash, transaction, i, this.mInnerAnimationFinishedCallback);
        if (ProtoLogImpl.isEnabled(ProtoLogGroup.WM_DEBUG_ANIM)) {
            StringWriter stringWriter = new StringWriter();
            this.mAnimation.dump(new PrintWriter(stringWriter), "");
            if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
                ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_ANIM, -1969928125, 0, (String) null, new Object[]{String.valueOf(this.mAnimatable), String.valueOf(stringWriter)});
            }
        }
        if (animationAdapter2 != null) {
            SurfaceFreezer.Snapshot takeSnapshotForAnimation = surfaceFreezer.takeSnapshotForAnimation();
            this.mSnapshot = takeSnapshotForAnimation;
            if (takeSnapshotForAnimation == null) {
                Slog.e(StartingSurfaceController.TAG, "No snapshot target to start animation on for " + this.mAnimatable);
                return;
            }
            takeSnapshotForAnimation.startAnimation(transaction, animationAdapter2, i);
        }
    }

    public void startAnimation(SurfaceControl.Transaction transaction, AnimationAdapter animationAdapter, boolean z, int i) {
        startAnimation(transaction, animationAdapter, z, i, null, null, null, null);
    }

    public void startDelayingAnimationStart() {
        if (isAnimating()) {
            return;
        }
        this.mAnimationStartDelayed = true;
    }

    public void endDelayingAnimationStart() {
        AnimationAdapter animationAdapter;
        boolean z = this.mAnimationStartDelayed;
        this.mAnimationStartDelayed = false;
        if (!z || (animationAdapter = this.mAnimation) == null) {
            return;
        }
        animationAdapter.startAnimation(this.mLeash, this.mAnimatable.getSyncTransaction(), this.mAnimationType, this.mInnerAnimationFinishedCallback);
        this.mAnimatable.commitPendingTransaction();
    }

    public boolean isAnimating() {
        return this.mAnimation != null;
    }

    public int getAnimationType() {
        return this.mAnimationType;
    }

    public AnimationAdapter getAnimation() {
        return this.mAnimation;
    }

    public void cancelAnimation() {
        cancelAnimation(this.mAnimatable.getSyncTransaction(), false, true);
        this.mAnimatable.commitPendingTransaction();
    }

    public void setLayer(SurfaceControl.Transaction transaction, int i) {
        SurfaceControl surfaceControl = this.mLeash;
        if (surfaceControl == null) {
            surfaceControl = this.mAnimatable.getSurfaceControl();
        }
        transaction.setLayer(surfaceControl, i);
    }

    public void setRelativeLayer(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, int i) {
        SurfaceControl surfaceControl2 = this.mLeash;
        if (surfaceControl2 == null) {
            surfaceControl2 = this.mAnimatable.getSurfaceControl();
        }
        transaction.setRelativeLayer(surfaceControl2, surfaceControl, i);
    }

    public boolean hasLeash() {
        return this.mLeash != null;
    }

    public void transferAnimation(SurfaceAnimator surfaceAnimator) {
        String str;
        if (surfaceAnimator.mLeash == null) {
            return;
        }
        SurfaceControl surfaceControl = this.mAnimatable.getSurfaceControl();
        SurfaceControl animationLeashParent = this.mAnimatable.getAnimationLeashParent();
        if (surfaceControl == null || animationLeashParent == null) {
            Slog.w(StartingSurfaceController.TAG, "Unable to transfer animation, surface or parent is null");
            cancelAnimation();
            return;
        }
        if (surfaceAnimator.mAnimationFinished) {
            Slog.w(StartingSurfaceController.TAG, "Unable to transfer animation, because " + surfaceAnimator + " animation is finished");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("transferAnimation, surface=");
        sb.append(surfaceControl);
        sb.append(", parent=");
        sb.append(animationLeashParent);
        if (CoreRune.SAFE_DEBUG) {
            str = ", caller=" + Debug.getCallers(4);
        } else {
            str = "";
        }
        sb.append(str);
        Slog.i(StartingSurfaceController.TAG, sb.toString());
        endDelayingAnimationStart();
        SurfaceControl.Transaction syncTransaction = this.mAnimatable.getSyncTransaction();
        cancelAnimation(syncTransaction, true, true);
        this.mLeash = surfaceAnimator.mLeash;
        this.mAnimation = surfaceAnimator.mAnimation;
        this.mAnimationType = surfaceAnimator.mAnimationType;
        this.mSurfaceAnimationFinishedCallback = surfaceAnimator.mSurfaceAnimationFinishedCallback;
        this.mAnimationCancelledCallback = surfaceAnimator.mAnimationCancelledCallback;
        surfaceAnimator.cancelAnimation(syncTransaction, false, false);
        syncTransaction.reparent(surfaceControl, this.mLeash);
        syncTransaction.reparent(this.mLeash, animationLeashParent);
        this.mAnimatable.onAnimationLeashCreated(syncTransaction, this.mLeash);
        this.mService.mAnimationTransferMap.put(this.mAnimation, this);
    }

    public boolean isAnimationStartDelayed() {
        return this.mAnimationStartDelayed;
    }

    public void cancelAnimation(SurfaceControl.Transaction transaction, boolean z, boolean z2) {
        String str;
        if (this.mLeash != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Cancelling animation restarting=");
            sb.append(z);
            sb.append(", leash=");
            sb.append(this.mLeash);
            if (CoreRune.SAFE_DEBUG) {
                str = ", caller=" + Debug.getCallers(3);
            } else {
                str = "";
            }
            sb.append(str);
            Slog.i(StartingSurfaceController.TAG, sb.toString());
        } else if (ProtoLogCache.WM_DEBUG_ANIM_enabled) {
            ProtoLogImpl.i(ProtoLogGroup.WM_DEBUG_ANIM, 397862437, 3, (String) null, new Object[]{Boolean.valueOf(z), String.valueOf(this.mAnimatable)});
        }
        SurfaceControl surfaceControl = this.mLeash;
        AnimationAdapter animationAdapter = this.mAnimation;
        int i = this.mAnimationType;
        OnAnimationFinishedCallback onAnimationFinishedCallback = this.mSurfaceAnimationFinishedCallback;
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
                OnAnimationFinishedCallback onAnimationFinishedCallback2 = this.mStaticAnimationFinishedCallback;
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

    public final void reset(SurfaceControl.Transaction transaction, boolean z) {
        this.mService.mAnimationTransferMap.remove(this.mAnimation);
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
            this.mService.scheduleAnimationLocked();
        }
    }

    public static boolean removeLeash(SurfaceControl.Transaction transaction, Animatable animatable, SurfaceControl surfaceControl, boolean z) {
        String str;
        SurfaceControl surfaceControl2 = animatable.getSurfaceControl();
        SurfaceControl parentSurfaceControl = animatable.getParentSurfaceControl();
        SurfaceControl animationLeash = animatable.getAnimationLeash();
        boolean z2 = false;
        boolean z3 = surfaceControl2 != null && (animationLeash == null || animationLeash.equals(surfaceControl));
        if (z3) {
            StringBuilder sb = new StringBuilder();
            sb.append("Reparenting to original parent: ");
            sb.append(parentSurfaceControl);
            sb.append(", destroy=");
            sb.append(z);
            sb.append(", surface=");
            sb.append(surfaceControl2);
            if (CoreRune.SAFE_DEBUG) {
                str = ", caller=" + Debug.getCallers(3);
            } else {
                str = "";
            }
            sb.append(str);
            Slog.i(StartingSurfaceController.TAG, sb.toString());
            if (surfaceControl2.isValid() && parentSurfaceControl != null && parentSurfaceControl.isValid()) {
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

    public static SurfaceControl createAnimationLeash(Animatable animatable, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, int i, int i2, int i3, int i4, int i5, boolean z, Supplier supplier) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("Reparenting to leash, surface=");
        sb.append(surfaceControl);
        sb.append(", leashParent=");
        sb.append(animatable.getAnimationLeashParent());
        if (CoreRune.SAFE_DEBUG) {
            str = ", caller=" + Debug.getCallers(3);
        } else {
            str = "";
        }
        sb.append(str);
        Slog.i(StartingSurfaceController.TAG, sb.toString());
        SurfaceControl.Builder callsite = animatable.makeAnimationLeash().setParent(animatable.getAnimationLeashParent()).setName(surfaceControl + " - animation-leash of " + animationTypeToString(i)).setHidden(z).setEffectLayer().setCallsite("SurfaceAnimator.createAnimationLeash");
        long uptimeMillis = SystemClock.uptimeMillis();
        SurfaceControl build = callsite.build();
        Slog.d(StartingSurfaceController.TAG, "makeSurface duration=" + (SystemClock.uptimeMillis() - uptimeMillis) + " leash=" + build);
        transaction.setWindowCrop(build, i2, i3);
        transaction.setPosition(build, (float) i4, (float) i5);
        transaction.show(build);
        transaction.setAlpha(build, z ? DisplayPowerController2.RATE_FROM_DOZE_TO_ON : 1.0f);
        transaction.reparent(surfaceControl, build);
        return build;
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        AnimationAdapter animationAdapter = this.mAnimation;
        if (animationAdapter != null) {
            animationAdapter.dumpDebug(protoOutputStream, 1146756268035L);
        }
        SurfaceControl surfaceControl = this.mLeash;
        if (surfaceControl != null) {
            surfaceControl.dumpDebug(protoOutputStream, 1146756268033L);
        }
        protoOutputStream.write(1133871366146L, this.mAnimationStartDelayed);
        protoOutputStream.end(start);
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.print("mLeash=");
        printWriter.print(this.mLeash);
        printWriter.print(" mAnimationType=" + animationTypeToString(this.mAnimationType));
        printWriter.println(this.mAnimationStartDelayed ? " mAnimationStartDelayed=true" : "");
        printWriter.print(str);
        printWriter.print("Animation: ");
        printWriter.println(this.mAnimation);
        AnimationAdapter animationAdapter = this.mAnimation;
        if (animationAdapter != null) {
            animationAdapter.dump(printWriter, str + "  ");
        }
    }

    public static String animationTypeToString(int i) {
        if (i == 0) {
            return "none";
        }
        if (i == 1) {
            return "app_transition";
        }
        if (i == 2) {
            return "screen_rotation";
        }
        if (i == 4) {
            return "dimmer";
        }
        if (i == 8) {
            return "recents_animation";
        }
        if (i == 16) {
            return "window_animation";
        }
        if (i == 32) {
            return "insets_animation";
        }
        if (i == 64) {
            return "token_transform";
        }
        if (i == 128) {
            return "starting_reveal";
        }
        if (i == 256) {
            return "predict_back";
        }
        if (i == 512 && CoreRune.FW_REMOTE_WALLPAPER_ANIM) {
            return "remote_wallpaper";
        }
        return "unknown type:" + i;
    }
}
