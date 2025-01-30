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
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.TransactionPool;
import com.android.wm.shell.splitscreen.SplitBackgroundController;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.samsung.android.multiwindow.IRemoteAppTransitionListener;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SplitBackgroundController implements RootTaskDisplayAreaOrganizer.RootTaskDisplayAreaListener, DisplayController.OnDisplaysChangedListener, SplitScreen.SplitScreenListener {
    public static final boolean DEBUG;
    public float[] mBackgroundColor;
    public SurfaceControl mBackgroundColorLayer;
    public final Context mContext;
    public boolean mIsAttached;
    public boolean mIsDividerVisible;
    public final ShellExecutor mMainExecutor;
    public final C40981 mRemoteAppTransitionListener;
    public boolean mReparentedToTransitionRoot;
    public final StageCoordinator mStageCoordinator;
    public final TransactionPool mTransactionPool;
    public boolean mWallpaperVisible;
    public final Object mLock = new Object();
    public final SplitBackgroundController$$ExternalSyntheticLambda0 mShowAnimDelay = new SplitBackgroundController$$ExternalSyntheticLambda0(this, 0);
    public final SurfaceDelegate mSurfaceDelegate = new SurfaceDelegate();
    public final SurfaceSession mSurfaceSession = new SurfaceSession();
    public ValueAnimator mAnimation = null;
    public boolean mVisible = false;
    public boolean mNightMode = false;

    static {
        DEBUG = CoreRune.SAFE_DEBUG || CoreRune.IS_DEBUG_LEVEL_MID;
    }

    public SplitBackgroundController(Context context, StageCoordinator stageCoordinator, TransactionPool transactionPool, ShellExecutor shellExecutor, DisplayController displayController) {
        C40981 c40981 = new C40981();
        this.mRemoteAppTransitionListener = c40981;
        this.mContext = context;
        this.mStageCoordinator = stageCoordinator;
        this.mTransactionPool = transactionPool;
        this.mMainExecutor = shellExecutor;
        updateBackgroundColor();
        MultiWindowManager.getInstance().registerRemoteAppTransitionListener(c40981);
        ((HandlerExecutor) shellExecutor).execute(new SplitBackgroundController$$ExternalSyntheticLambda0(this, 1));
        displayController.addDisplayWindowListener(this);
    }

    public final boolean canShow() {
        return this.mIsDividerVisible && (this.mWallpaperVisible || CoreRune.MW_MULTI_SPLIT_BACKGROUND);
    }

    public final void detach() {
        if (this.mIsAttached) {
            TransactionPool transactionPool = this.mTransactionPool;
            SurfaceControl.Transaction acquire = transactionPool.acquire();
            acquire.remove(this.mBackgroundColorLayer);
            acquire.apply();
            transactionPool.release(acquire);
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

    public final void reparentToLeash(SurfaceControl surfaceControl, boolean z, SurfaceControl.Transaction transaction) {
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
            SurfaceControl.Transaction acquire = transactionPool.acquire();
            acquire.setLayer(this.mBackgroundColorLayer, -1);
            acquire.reparent(this.mBackgroundColorLayer, surfaceControl);
            acquire.apply();
            transactionPool.release(acquire);
        }
        updateBackgroundVisibility(z2, false);
    }

    public final void updateBackgroundColor() {
        int roundedCornerColor = MultiWindowUtils.getRoundedCornerColor(this.mContext);
        this.mBackgroundColor = new float[]{Color.red(roundedCornerColor) / 255.0f, Color.green(roundedCornerColor) / 255.0f, Color.blue(roundedCornerColor) / 255.0f};
    }

    public final void updateBackgroundLayer(boolean z) {
        if (this.mBackgroundColorLayer == null) {
            return;
        }
        ValueAnimator valueAnimator = this.mAnimation;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        SurfaceDelegate surfaceDelegate = this.mSurfaceDelegate;
        if (z) {
            float f = this.mWallpaperVisible ? 0.9f : 1.0f;
            if (surfaceDelegate.mAlpha != f) {
                surfaceDelegate.mAlpha = f;
                surfaceDelegate.mChanged = true;
            }
            updateBackgroundColor();
            float[] fArr = this.mBackgroundColor;
            if (surfaceDelegate.mColors != fArr) {
                surfaceDelegate.mColors = fArr;
                surfaceDelegate.mChanged = true;
            }
        }
        if (surfaceDelegate.mVisible != z) {
            surfaceDelegate.mVisible = z;
            surfaceDelegate.mChanged = true;
        }
        surfaceDelegate.apply();
    }

    public final void updateBackgroundLayerColor(boolean z) {
        boolean isNightModeActive = this.mContext.getResources().getConfiguration().isNightModeActive();
        if (this.mNightMode != isNightModeActive || z) {
            this.mNightMode = isNightModeActive;
            if (this.mBackgroundColorLayer == null) {
                return;
            }
            updateBackgroundColor();
            float[] fArr = this.mBackgroundColor;
            SurfaceDelegate surfaceDelegate = this.mSurfaceDelegate;
            if (surfaceDelegate.mColors != fArr) {
                surfaceDelegate.mColors = fArr;
                surfaceDelegate.mChanged = true;
            }
            surfaceDelegate.apply();
        }
    }

    public final void updateBackgroundVisibility(boolean z, boolean z2) {
        synchronized (this.mLock) {
            if (((HandlerExecutor) this.mMainExecutor).mHandler.hasCallbacks(this.mShowAnimDelay)) {
                ((HandlerExecutor) this.mMainExecutor).removeCallbacks(this.mShowAnimDelay);
            }
            if (!this.mIsAttached) {
                Slog.e("SplitBackgroundController", "updateBackgroundVisibility: not attached but called. callers=" + Debug.getCallers(Thread.currentThread().getStackTrace().length));
            }
            if (this.mVisible != z) {
                if (DEBUG) {
                    Slog.d("SplitBackgroundController", "updateBackgroundVisibility: visible=" + z + " animate=" + z2 + " Callers=" + Debug.getCallers(Thread.currentThread().getStackTrace().length));
                }
                this.mVisible = z;
                if (z2) {
                    ((HandlerExecutor) this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitBackgroundController$$ExternalSyntheticLambda1
                        public final /* synthetic */ int f$1 = 0;

                        @Override // java.lang.Runnable
                        public final void run() {
                            final float f;
                            float f2;
                            final SplitBackgroundController splitBackgroundController = SplitBackgroundController.this;
                            int i = this.f$1;
                            final boolean z3 = splitBackgroundController.mVisible;
                            if (splitBackgroundController.mBackgroundColorLayer == null) {
                                return;
                            }
                            ValueAnimator valueAnimator = splitBackgroundController.mAnimation;
                            if (valueAnimator != null) {
                                valueAnimator.cancel();
                                splitBackgroundController.updateBackgroundLayer(z3);
                                return;
                            }
                            if (z3) {
                                f2 = splitBackgroundController.mWallpaperVisible ? 0.9f : 1.0f;
                                f = 0.6f;
                            } else {
                                f = splitBackgroundController.mWallpaperVisible ? 0.9f : 1.0f;
                                f2 = 0.0f;
                            }
                            final SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                            ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
                            splitBackgroundController.mAnimation = ofFloat;
                            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.splitscreen.SplitBackgroundController$$ExternalSyntheticLambda2
                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                                    SplitBackgroundController splitBackgroundController2 = SplitBackgroundController.this;
                                    SurfaceControl.Transaction transaction2 = transaction;
                                    splitBackgroundController2.getClass();
                                    float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                                    SplitBackgroundController.SurfaceDelegate surfaceDelegate = splitBackgroundController2.mSurfaceDelegate;
                                    if (surfaceDelegate.mAlpha != floatValue) {
                                        surfaceDelegate.mAlpha = floatValue;
                                        surfaceDelegate.mChanged = true;
                                    }
                                    surfaceDelegate.apply(transaction2);
                                }
                            });
                            splitBackgroundController.mAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.splitscreen.SplitBackgroundController.2
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    if (!z3) {
                                        SurfaceDelegate surfaceDelegate = SplitBackgroundController.this.mSurfaceDelegate;
                                        if (surfaceDelegate.mVisible) {
                                            surfaceDelegate.mVisible = false;
                                            surfaceDelegate.mChanged = true;
                                        }
                                        surfaceDelegate.apply(transaction);
                                    }
                                    transaction.close();
                                    SplitBackgroundController.this.mAnimation = null;
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationStart(Animator animator) {
                                    if (z3) {
                                        SplitBackgroundController splitBackgroundController2 = SplitBackgroundController.this;
                                        SurfaceDelegate surfaceDelegate = splitBackgroundController2.mSurfaceDelegate;
                                        float f3 = f;
                                        if (surfaceDelegate.mAlpha != f3) {
                                            surfaceDelegate.mAlpha = f3;
                                            surfaceDelegate.mChanged = true;
                                        }
                                        splitBackgroundController2.updateBackgroundColor();
                                        SplitBackgroundController splitBackgroundController3 = SplitBackgroundController.this;
                                        SurfaceDelegate surfaceDelegate2 = splitBackgroundController3.mSurfaceDelegate;
                                        float[] fArr = splitBackgroundController3.mBackgroundColor;
                                        if (surfaceDelegate2.mColors != fArr) {
                                            surfaceDelegate2.mColors = fArr;
                                            surfaceDelegate2.mChanged = true;
                                        }
                                        if (!surfaceDelegate2.mVisible) {
                                            surfaceDelegate2.mVisible = true;
                                            surfaceDelegate2.mChanged = true;
                                        }
                                        surfaceDelegate2.apply(transaction);
                                    }
                                }
                            });
                            splitBackgroundController.mAnimation.setDuration(i == 1 ? 200 : 400);
                            splitBackgroundController.mAnimation.start();
                        }
                    });
                } else {
                    ((HandlerExecutor) this.mMainExecutor).execute(new SplitBackgroundController$$ExternalSyntheticLambda0(this, 2));
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SurfaceDelegate {
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
                SurfaceControl.Transaction acquire = splitBackgroundController.mTransactionPool.acquire();
                apply(acquire);
                splitBackgroundController.mTransactionPool.release(acquire);
            }
        }

        public final boolean canApply() {
            if (this.mSurfaceControl == null) {
                if (SplitBackgroundController.DEBUG) {
                    Slog.d("SplitBackgroundController", "surface is not set. apply failed " + Debug.getCallers(Thread.currentThread().getStackTrace().length));
                }
                return false;
            }
            if (this.mChanged) {
                return true;
            }
            if (SplitBackgroundController.DEBUG) {
                Slog.d("SplitBackgroundController", "no changes. cur state: " + this);
            }
            return false;
        }

        public final void setCrop(Rect rect) {
            if (rect != null) {
                Rect rect2 = this.mCropRect;
                if (rect2.equals(rect)) {
                    return;
                }
                rect2.set(rect);
                this.mChanged = true;
                if (SplitBackgroundController.DEBUG) {
                    Slog.d("SplitBackgroundController", "setCrop: " + rect);
                }
            }
        }

        public final String toString() {
            return "sc= " + this.mSurfaceControl + " vis=" + this.mVisible + " color=" + Arrays.toString(this.mColors) + " alpha=" + this.mAlpha;
        }

        public final void apply(SurfaceControl.Transaction transaction) {
            if (canApply()) {
                transaction.setColor(this.mSurfaceControl, this.mColors);
                transaction.setAlpha(this.mSurfaceControl, this.mAlpha);
                transaction.setVisibility(this.mSurfaceControl, this.mVisible);
                transaction.setCrop(this.mSurfaceControl, this.mCropRect);
                transaction.apply();
                this.mChanged = false;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.splitscreen.SplitBackgroundController$1 */
    public final class C40981 extends IRemoteAppTransitionListener.Stub {
        public C40981() {
        }

        public final void onWallpaperVisibilityChanged(final boolean z, final boolean z2) {
            ((HandlerExecutor) SplitBackgroundController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitBackgroundController$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SplitBackgroundController.C40981 c40981 = SplitBackgroundController.C40981.this;
                    boolean z3 = z;
                    boolean z4 = z2;
                    SplitBackgroundController splitBackgroundController = SplitBackgroundController.this;
                    if (splitBackgroundController.mWallpaperVisible == z3) {
                        return;
                    }
                    splitBackgroundController.mWallpaperVisible = z3;
                    if (!splitBackgroundController.canShow() || z4) {
                        SplitBackgroundController.this.updateBackgroundVisibility(false, false);
                        return;
                    }
                    if (!CoreRune.MW_MULTI_SPLIT_BACKGROUND) {
                        SplitBackgroundController.this.updateBackgroundVisibility(true, false);
                        return;
                    }
                    SplitBackgroundController splitBackgroundController2 = SplitBackgroundController.this;
                    float f = splitBackgroundController2.mWallpaperVisible ? 0.9f : 1.0f;
                    if (splitBackgroundController2.mBackgroundColorLayer == null) {
                        return;
                    }
                    SplitBackgroundController.SurfaceDelegate surfaceDelegate = splitBackgroundController2.mSurfaceDelegate;
                    if (surfaceDelegate.mAlpha != f) {
                        surfaceDelegate.mAlpha = f;
                        surfaceDelegate.mChanged = true;
                    }
                    splitBackgroundController2.updateBackgroundColor();
                    float[] fArr = splitBackgroundController2.mBackgroundColor;
                    if (surfaceDelegate.mColors != fArr) {
                        surfaceDelegate.mColors = fArr;
                        surfaceDelegate.mChanged = true;
                    }
                    surfaceDelegate.apply();
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
