package com.android.wm.shell.splitscreen;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Debug;
import android.util.Slog;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.splitscreen.SplitBackgroundController;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.samsung.android.multiwindow.IRemoteAppTransitionListener;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class SplitBackgroundController implements RootTaskDisplayAreaOrganizer.RootTaskDisplayAreaListener, DisplayController.OnDisplaysChangedListener, SplitScreen.SplitScreenListener {
    public static final boolean DEBUG = CoreRune.IS_DEBUG_LEVEL_MID;
    public float mAlpha;
    public SurfaceControl mBackgroundColorLayer;
    public float[] mColors;
    public final Context mContext;
    public boolean mHiddenWhileRecentsTransition;
    public boolean mIsAttached;
    public boolean mIsDividerVisible;
    public final ShellExecutor mMainExecutor;
    public final AnonymousClass1 mRemoteAppTransitionListener;
    public boolean mReparentedToTransitionRoot;
    public final StageCoordinator mStageCoordinator;
    public final TransactionPool mTransactionPool;
    public boolean mWallpaperVisible;
    public final Object mLock = new Object();
    public float mOneshotStartAlpha = -1.0f;
    public final SurfaceDelegate mSurfaceDelegate = new SurfaceDelegate();
    public final SurfaceSession mSurfaceSession = new SurfaceSession();
    public ValueAnimator mAnimation = null;
    public boolean mVisible = false;
    public boolean mNightMode = false;

    public class SurfaceDelegate {
        public float mAlpha;
        public boolean mChanged;
        public SurfaceControl mSurfaceControl;
        public float[] mColors = new float[3];
        public boolean mVisible = false;
        public final Rect mCropRect = new Rect();

        public SurfaceDelegate() {
        }

        public final void apply() {
            if (canApply()) {
                SplitBackgroundController splitBackgroundController = SplitBackgroundController.this;
                SurfaceControl.Transaction transactionAcquire = splitBackgroundController.mTransactionPool.acquire();
                if (canApply()) {
                    transactionAcquire.setColor(this.mSurfaceControl, this.mColors);
                    transactionAcquire.setAlpha(this.mSurfaceControl, this.mAlpha);
                    transactionAcquire.setVisibility(this.mSurfaceControl, this.mVisible);
                    transactionAcquire.setCrop(this.mSurfaceControl, this.mCropRect);
                    transactionAcquire.apply();
                    this.mChanged = false;
                }
                splitBackgroundController.mTransactionPool.release(transactionAcquire);
            }
        }

        public final boolean canApply() {
            if (this.mSurfaceControl == null) {
                if (SplitBackgroundController.DEBUG) {
                    Slog.d("SplitBackgroundController", "surface is not set. apply failed " + Debug.getCallers(Thread.currentThread().getStackTrace().length));
                    return false;
                }
            } else {
                if (this.mChanged) {
                    return true;
                }
                if (SplitBackgroundController.DEBUG) {
                    Slog.d("SplitBackgroundController", "no changes. cur state: " + this);
                }
            }
            return false;
        }

        public final void setCrop(Rect rect) {
            if (rect == null || this.mCropRect.equals(rect)) {
                return;
            }
            this.mCropRect.set(rect);
            this.mChanged = true;
            if (SplitBackgroundController.DEBUG) {
                Slog.d("SplitBackgroundController", "setCrop: " + rect);
            }
        }

        public final String toString() {
            return "sc= " + this.mSurfaceControl + " vis=" + this.mVisible + " color=" + Arrays.toString(this.mColors) + " alpha=" + this.mAlpha;
        }
    }

    public SplitBackgroundController(Context context, StageCoordinator stageCoordinator, TransactionPool transactionPool, ShellExecutor shellExecutor, DisplayController displayController) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mRemoteAppTransitionListener = anonymousClass1;
        this.mContext = context;
        this.mStageCoordinator = stageCoordinator;
        this.mTransactionPool = transactionPool;
        this.mMainExecutor = shellExecutor;
        updateColor();
        MultiWindowManager.getInstance().registerRemoteAppTransitionListener(anonymousClass1);
        shellExecutor.execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitBackgroundController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SplitBackgroundController splitBackgroundController = this.f$0;
                boolean z = SplitBackgroundController.DEBUG;
                splitBackgroundController.updateBackgroundLayerColor(true);
            }
        });
        displayController.addDisplayWindowListener(this, -1);
    }

    public final boolean canShow() {
        if (!this.mIsDividerVisible || this.mHiddenWhileRecentsTransition) {
            return false;
        }
        return this.mWallpaperVisible || CoreRune.MW_MULTI_SPLIT_BACKGROUND;
    }

    public final void detach() {
        if (this.mIsAttached) {
            TransactionPool transactionPool = this.mTransactionPool;
            SurfaceControl.Transaction transactionAcquire = transactionPool.acquire();
            transactionAcquire.remove(this.mBackgroundColorLayer);
            transactionAcquire.apply();
            transactionPool.release(transactionAcquire);
            this.mBackgroundColorLayer = null;
            this.mSurfaceDelegate.mSurfaceControl = null;
            this.mIsAttached = false;
        }
    }

    public final Rect getDisplayBounds() {
        StageCoordinator stageCoordinator = this.mStageCoordinator;
        DisplayLayout displayLayout = stageCoordinator.mDisplayController.getDisplayLayout(stageCoordinator.mContext.getDisplayId());
        if (displayLayout == null) {
            Slog.w("SplitBackgroundController", "getDisplayBounds: cannot find display");
            return null;
        }
        Rect rect = new Rect();
        rect.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
        return rect;
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
        if (i == 0) {
            updateBackgroundLayerColor(false);
            Rect displayBounds = getDisplayBounds();
            SurfaceDelegate surfaceDelegate = this.mSurfaceDelegate;
            surfaceDelegate.setCrop(displayBounds);
            surfaceDelegate.apply();
        }
    }

    public final void onRecentsInSplitAnimationFinish(boolean z) {
        if (this.mHiddenWhileRecentsTransition) {
            this.mHiddenWhileRecentsTransition = false;
            if (z && canShow()) {
                updateVisibility(true, true);
            }
        }
    }

    public final void reparentToLeash(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, boolean z) {
        if (surfaceControl == null || !surfaceControl.isValid()) {
            Slog.e("SplitBackgroundController", "reparentToLeash: failed, invalid leash=" + surfaceControl + ", callers=" + Debug.getCallers(5));
            return;
        }
        if (!this.mIsAttached) {
            Slog.e("SplitBackgroundController", "reparentToLeash: failed, non-attached state, callers=" + Debug.getCallers(5));
            return;
        }
        boolean z2 = canShow() || z;
        this.mReparentedToTransitionRoot = z;
        Slog.d("SplitBackgroundController", "reparentToLeash: leash=" + surfaceControl + ", isTransitionRoot=" + z + ", vis=" + z2 + ", callers=" + Debug.getCallers(3));
        if (transaction != null) {
            transaction.setLayer(this.mBackgroundColorLayer, -1);
            transaction.reparent(this.mBackgroundColorLayer, surfaceControl);
        } else {
            TransactionPool transactionPool = this.mTransactionPool;
            SurfaceControl.Transaction transactionAcquire = transactionPool.acquire();
            transactionAcquire.setLayer(this.mBackgroundColorLayer, -1);
            transactionAcquire.reparent(this.mBackgroundColorLayer, surfaceControl);
            transactionAcquire.apply();
            transactionPool.release(transactionAcquire);
        }
        updateVisibility(z2, false);
    }

    public final void setSplitsVisible(boolean z, boolean z2) {
        this.mIsDividerVisible = z;
        if (canShow()) {
            if (z2) {
                updateVisibility(true, true);
                return;
            } else {
                updateVisibility(true, false);
                return;
            }
        }
        if (z2) {
            updateVisibility(false, true);
        } else {
            updateVisibility(false, false);
        }
    }

    public final void updateBackgroundLayerColor(boolean z) {
        boolean zIsNightModeActive = this.mContext.getResources().getConfiguration().isNightModeActive();
        if (this.mNightMode != zIsNightModeActive || z) {
            this.mNightMode = zIsNightModeActive;
            if (this.mBackgroundColorLayer == null) {
                return;
            }
            updateColor();
            updateVisibility(this.mVisible, false);
        }
    }

    public final void updateColor() {
        int roundedCornerColor = MultiWindowUtils.getRoundedCornerColor(this.mContext);
        this.mColors = new float[]{Color.red(roundedCornerColor) / 255.0f, Color.green(roundedCornerColor) / 255.0f, Color.blue(roundedCornerColor) / 255.0f};
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0057 A[Catch: all -> 0x002c, TryCatch #0 {all -> 0x002c, blocks: (B:4:0x0009, B:6:0x000d, B:9:0x0030, B:11:0x003a, B:13:0x003e, B:20:0x004b, B:53:0x00d6, B:22:0x0057, B:24:0x005b, B:25:0x0089, B:29:0x0090, B:40:0x00a9, B:44:0x00b3, B:47:0x00bc, B:52:0x00c5, B:33:0x009a, B:35:0x009f, B:39:0x00a7), top: B:57:0x0009 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateVisibility(final boolean z, final boolean z2) {
        final float f;
        synchronized (this.mLock) {
            try {
                if (!this.mIsAttached) {
                    Slog.e("SplitBackgroundController", "updateBackgroundVisibility: not attached but called. callers=" + Debug.getCallers(Thread.currentThread().getStackTrace().length));
                }
                if (this.mVisible == z) {
                    if (this.mAlpha != (z ? this.mWallpaperVisible ? 0.9f : 1.0f : 0.0f) || !Arrays.equals(this.mSurfaceDelegate.mColors, this.mColors)) {
                        if (DEBUG) {
                            Slog.d("SplitBackgroundController", "updateBackgroundVisibility: visible=" + z + " animate=" + z2 + " Callers=" + Debug.getCallers(Thread.currentThread().getStackTrace().length));
                        }
                        boolean z3 = this.mVisible != z;
                        SurfaceDelegate surfaceDelegate = this.mSurfaceDelegate;
                        float f2 = z ? z3 ? 0.6f : surfaceDelegate.mAlpha : z3 ? this.mWallpaperVisible ? 0.9f : 1.0f : surfaceDelegate.mAlpha;
                        float f3 = this.mOneshotStartAlpha;
                        if (f3 < 0.0f || f3 > 1.0f) {
                            f = f2;
                        } else {
                            this.mOneshotStartAlpha = -1.0f;
                            f = f3;
                        }
                        final float f4 = z ? this.mWallpaperVisible ? 0.9f : 1.0f : 0.0f;
                        this.mVisible = z;
                        this.mAlpha = f4;
                        this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitBackgroundController$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                float animatedFraction;
                                final SplitBackgroundController splitBackgroundController = this.f$0;
                                boolean z4 = z2;
                                final boolean z5 = z;
                                final float fFloatValue = f;
                                float f5 = f4;
                                if (!z4 && splitBackgroundController.mAnimation == null) {
                                    if (splitBackgroundController.mBackgroundColorLayer == null) {
                                        return;
                                    }
                                    SplitBackgroundController.SurfaceDelegate surfaceDelegate2 = splitBackgroundController.mSurfaceDelegate;
                                    if (surfaceDelegate2.mVisible != z5) {
                                        surfaceDelegate2.mVisible = z5;
                                        surfaceDelegate2.mChanged = true;
                                    }
                                    float[] fArr = splitBackgroundController.mColors;
                                    if (surfaceDelegate2.mColors != fArr) {
                                        surfaceDelegate2.mColors = fArr;
                                        surfaceDelegate2.mChanged = true;
                                    }
                                    float f6 = splitBackgroundController.mAlpha;
                                    if (surfaceDelegate2.mAlpha != f6) {
                                        surfaceDelegate2.mAlpha = f6;
                                        surfaceDelegate2.mChanged = true;
                                    }
                                    surfaceDelegate2.apply();
                                    return;
                                }
                                final float[] fArr2 = splitBackgroundController.mColors;
                                if (splitBackgroundController.mBackgroundColorLayer == null) {
                                    return;
                                }
                                ValueAnimator valueAnimator = splitBackgroundController.mAnimation;
                                if (valueAnimator != null) {
                                    fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                    animatedFraction = splitBackgroundController.mAnimation.getAnimatedFraction();
                                    splitBackgroundController.mAnimation.cancel();
                                } else {
                                    animatedFraction = 0.0f;
                                }
                                if (fFloatValue != f5) {
                                    ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(fFloatValue, f5);
                                    splitBackgroundController.mAnimation = valueAnimatorOfFloat;
                                    valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.splitscreen.SplitBackgroundController$$ExternalSyntheticLambda2
                                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                                            SplitBackgroundController splitBackgroundController2 = splitBackgroundController;
                                            boolean z6 = SplitBackgroundController.DEBUG;
                                            splitBackgroundController2.getClass();
                                            float fFloatValue2 = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                                            SplitBackgroundController.SurfaceDelegate surfaceDelegate3 = splitBackgroundController2.mSurfaceDelegate;
                                            if (surfaceDelegate3.mAlpha != fFloatValue2) {
                                                surfaceDelegate3.mAlpha = fFloatValue2;
                                                surfaceDelegate3.mChanged = true;
                                            }
                                            surfaceDelegate3.apply();
                                        }
                                    });
                                    splitBackgroundController.mAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.splitscreen.SplitBackgroundController.2
                                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                        public final void onAnimationEnd(Animator animator) {
                                            if (!z5) {
                                                SurfaceDelegate surfaceDelegate3 = SplitBackgroundController.this.mSurfaceDelegate;
                                                if (surfaceDelegate3.mVisible) {
                                                    surfaceDelegate3.mVisible = false;
                                                    surfaceDelegate3.mChanged = true;
                                                }
                                                surfaceDelegate3.apply();
                                            }
                                            SplitBackgroundController.this.mAnimation = null;
                                        }

                                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                        public final void onAnimationStart(Animator animator) {
                                            if (z5) {
                                                SurfaceDelegate surfaceDelegate3 = SplitBackgroundController.this.mSurfaceDelegate;
                                                float f7 = fFloatValue;
                                                if (surfaceDelegate3.mAlpha != f7) {
                                                    surfaceDelegate3.mAlpha = f7;
                                                    surfaceDelegate3.mChanged = true;
                                                }
                                                float[] fArr3 = fArr2;
                                                if (surfaceDelegate3.mColors != fArr3) {
                                                    surfaceDelegate3.mColors = fArr3;
                                                    surfaceDelegate3.mChanged = true;
                                                }
                                                if (!surfaceDelegate3.mVisible) {
                                                    surfaceDelegate3.mVisible = true;
                                                    surfaceDelegate3.mChanged = true;
                                                }
                                                surfaceDelegate3.apply();
                                            }
                                        }
                                    });
                                    splitBackgroundController.mAnimation.setDuration(animatedFraction <= 0.0f ? 400L : animatedFraction >= 0.75f ? 100L : (long) ((1.0f - animatedFraction) * 400.0f));
                                    splitBackgroundController.mAnimation.start();
                                    return;
                                }
                                if (splitBackgroundController.mBackgroundColorLayer == null) {
                                    return;
                                }
                                SplitBackgroundController.SurfaceDelegate surfaceDelegate3 = splitBackgroundController.mSurfaceDelegate;
                                if (surfaceDelegate3.mVisible != z5) {
                                    surfaceDelegate3.mVisible = z5;
                                    surfaceDelegate3.mChanged = true;
                                }
                                float[] fArr3 = splitBackgroundController.mColors;
                                if (surfaceDelegate3.mColors != fArr3) {
                                    surfaceDelegate3.mColors = fArr3;
                                    surfaceDelegate3.mChanged = true;
                                }
                                float f7 = splitBackgroundController.mAlpha;
                                if (surfaceDelegate3.mAlpha != f7) {
                                    surfaceDelegate3.mAlpha = f7;
                                    surfaceDelegate3.mChanged = true;
                                }
                                surfaceDelegate3.apply();
                            }
                        });
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: com.android.wm.shell.splitscreen.SplitBackgroundController$1, reason: invalid class name */
    public class AnonymousClass1 extends IRemoteAppTransitionListener.Stub {
        public AnonymousClass1() {
        }

        public final void onWallpaperVisibilityChanged(final boolean z, final boolean z2) {
            SplitBackgroundController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitBackgroundController$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SplitBackgroundController.AnonymousClass1 anonymousClass1 = this.f$0;
                    boolean z3 = z;
                    boolean z4 = z2;
                    SplitBackgroundController splitBackgroundController = SplitBackgroundController.this;
                    if (splitBackgroundController.mWallpaperVisible == z3) {
                        return;
                    }
                    splitBackgroundController.mWallpaperVisible = z3;
                    if (!splitBackgroundController.canShow() || z4) {
                        SplitBackgroundController.this.updateVisibility(false, true);
                    } else {
                        SplitBackgroundController.this.updateVisibility(true, true);
                    }
                }
            });
        }

        public final void onFinishRecentsAnimation(boolean z) {
        }

        public final void onStartHomeAnimation(boolean z) {
        }

        public final void onStartRecentsAnimation(boolean z) {
        }
    }
}
