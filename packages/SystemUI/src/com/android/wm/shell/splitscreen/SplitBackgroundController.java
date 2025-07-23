package com.android.wm.shell.splitscreen;

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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                SurfaceControl.Transaction acquire = splitBackgroundController.mTransactionPool.acquire();
                if (canApply()) {
                    acquire.setColor(this.mSurfaceControl, this.mColors);
                    acquire.setAlpha(this.mSurfaceControl, this.mAlpha);
                    acquire.setVisibility(this.mSurfaceControl, this.mVisible);
                    acquire.setCrop(this.mSurfaceControl, this.mCropRect);
                    acquire.apply();
                    this.mChanged = false;
                }
                splitBackgroundController.mTransactionPool.release(acquire);
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
                SplitBackgroundController splitBackgroundController = SplitBackgroundController.this;
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
            SurfaceControl.Transaction acquire = transactionPool.acquire();
            acquire.setLayer(this.mBackgroundColorLayer, -1);
            acquire.reparent(this.mBackgroundColorLayer, surfaceControl);
            acquire.apply();
            transactionPool.release(acquire);
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
        boolean isNightModeActive = this.mContext.getResources().getConfiguration().isNightModeActive();
        if (this.mNightMode != isNightModeActive || z) {
            this.mNightMode = isNightModeActive;
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

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0055, code lost:
    
        if (java.util.Arrays.equals(r9.mSurfaceDelegate.mColors, r9.mColors) != false) goto L53;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateVisibility(final boolean r10, final boolean r11) {
        /*
            Method dump skipped, instructions count: 218
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.splitscreen.SplitBackgroundController.updateVisibility(boolean, boolean):void");
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.splitscreen.SplitBackgroundController$1, reason: invalid class name */
    public class AnonymousClass1 extends IRemoteAppTransitionListener.Stub {
        public AnonymousClass1() {
        }

        public final void onWallpaperVisibilityChanged(final boolean z, final boolean z2) {
            SplitBackgroundController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitBackgroundController$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SplitBackgroundController.AnonymousClass1 anonymousClass1 = SplitBackgroundController.AnonymousClass1.this;
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
