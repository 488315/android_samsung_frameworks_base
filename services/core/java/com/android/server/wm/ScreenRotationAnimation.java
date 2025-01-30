package com.android.server.wm;

import android.R;
import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.IBinder;
import android.os.Trace;
import android.util.RotationUtils;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.DisplayAddress;
import android.view.DisplayInfo;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.window.ScreenCapture;
import com.android.internal.policy.TransitionAnimation;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl;
import com.android.server.display.DisplayControl;
import com.android.server.display.DisplayPowerController2;
import com.android.server.wm.LocalAnimationAdapter;
import com.android.server.wm.SimpleSurfaceAnimatable;
import com.android.server.wm.SurfaceAnimator;
import com.android.server.wm.utils.CoordinateTransforms;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class ScreenRotationAnimation {
    public boolean mAnimRunning;
    public SurfaceControl mBackColorSurface;
    public final Context mContext;
    public int mCurRotation;
    public final DisplayContent mDisplayContent;
    public float mEndLuma;
    public SurfaceControl mEnterBlackFrameLayer;
    public BlackFrame mEnteringBlackFrame;
    public boolean mFinishAnimReady;
    public long mFinishAnimStartTime;
    public boolean mHasBlackSnapshot;
    public final int mOriginalHeight;
    public final int mOriginalRotation;
    public final int mOriginalWidth;
    public Animation mRotateAlphaAnimation;
    public Animation mRotateEnterAnimation;
    public Animation mRotateExitAnimation;
    public SurfaceControl[] mRoundedCornerOverlay;
    public SurfaceControl mScreenshotLayer;
    public final WindowManagerService mService;
    public float mStartLuma;
    public boolean mStarted;
    public SurfaceRotationAnimationController mSurfaceRotationAnimationController;
    public final float[] mTmpFloats = new float[9];
    public final Transformation mRotateExitTransformation = new Transformation();
    public final Transformation mRotateEnterTransformation = new Transformation();
    public final Matrix mSnapshotInitialMatrix = new Matrix();

    public ScreenRotationAnimation(DisplayContent displayContent, int i) {
        int i2;
        boolean z;
        ScreenCapture.ScreenshotHardwareBuffer captureLayers;
        boolean z2;
        WindowContainer windowContainer;
        WindowManagerService windowManagerService = displayContent.mWmService;
        this.mService = windowManagerService;
        this.mContext = windowManagerService.mContext;
        this.mDisplayContent = displayContent;
        Rect bounds = displayContent.getBounds();
        int width = bounds.width();
        int height = bounds.height();
        DisplayInfo displayInfo = displayContent.getDisplayInfo();
        int i3 = displayInfo.rotation;
        if (CoreRune.FW_BLACK_SNAPSHOT_TRANSITION) {
            boolean shouldHasBlackSnapshot = shouldHasBlackSnapshot();
            this.mHasBlackSnapshot = shouldHasBlackSnapshot;
            if (CoreRune.SAFE_DEBUG && shouldHasBlackSnapshot) {
                Slog.d(StartingSurfaceController.TAG, "BlackScreenshot is triggered, isScreenTurnedOn=" + windowManagerService.mExt.mPolicyExt.isScreenTurnedOn());
            }
        }
        this.mOriginalRotation = i;
        int deltaRotation = RotationUtils.deltaRotation(i, i3);
        boolean z3 = deltaRotation == 1 || deltaRotation == 3;
        int i4 = z3 ? height : width;
        this.mOriginalWidth = i4;
        int i5 = z3 ? width : height;
        this.mOriginalHeight = i5;
        int i6 = displayInfo.logicalWidth;
        int i7 = displayInfo.logicalHeight;
        boolean z4 = (i6 > i4) == (i7 > i5) && !(i6 == i4 && i7 == i5);
        this.mSurfaceRotationAnimationController = new SurfaceRotationAnimationController();
        boolean hasSecureWindowOnScreen = displayContent.hasSecureWindowOnScreen();
        int displayId = displayContent.getDisplayId();
        SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) windowManagerService.mTransactionFactory.get();
        try {
            if (CoreRune.FW_BLACK_SNAPSHOT_TRANSITION && this.mHasBlackSnapshot) {
                SurfaceControl build = displayContent.makeOverlay().setName("BlackSnapshotLayer").setOpaque(true).setColorLayer().setCallsite("ScreenRotationAnimation").build();
                this.mScreenshotLayer = build;
                transaction.setLayer(build, 2010000);
                transaction.setAlpha(this.mScreenshotLayer, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                transaction.show(this.mScreenshotLayer);
            } else {
                if (z4) {
                    DisplayAddress.Physical physical = displayInfo.address;
                    if (!(physical instanceof DisplayAddress.Physical)) {
                        Slog.e(StartingSurfaceController.TAG, "Display does not have a physical address: " + displayId);
                        return;
                    }
                    IBinder physicalDisplayToken = DisplayControl.getPhysicalDisplayToken(physical.getPhysicalDisplayId());
                    if (physicalDisplayToken == null) {
                        Slog.e(StartingSurfaceController.TAG, "Display token is null.");
                        return;
                    }
                    setSkipScreenshotForRoundedCornerOverlays(false, transaction);
                    this.mRoundedCornerOverlay = displayContent.findRoundedCornerOverlays();
                    z = hasSecureWindowOnScreen;
                    captureLayers = ScreenCapture.captureDisplay(new ScreenCapture.DisplayCaptureArgs.Builder(physicalDisplayToken).setSourceCrop(new Rect(0, 0, width, height)).setAllowProtected(true).setCaptureSecureLayers(true).setHintForSeamlessTransition(true).build());
                } else {
                    z = hasSecureWindowOnScreen;
                    captureLayers = ScreenCapture.captureLayers(new ScreenCapture.LayerCaptureArgs.Builder(displayContent.getSurfaceControl()).setCaptureSecureLayers(true).setAllowProtected(true).setSourceCrop(new Rect(0, 0, width, height)).setHintForSeamlessTransition(true).build());
                }
                if (captureLayers == null) {
                    Slog.w(StartingSurfaceController.TAG, "Unable to take screenshot of display " + displayId);
                    return;
                }
                if (captureLayers.containsSecureLayers()) {
                    windowContainer = null;
                    z2 = true;
                } else {
                    z2 = z;
                    windowContainer = null;
                }
                this.mBackColorSurface = displayContent.makeChildSurface(windowContainer).setName("BackColorSurface").setColorLayer().setCallsite("ScreenRotationAnimation").build();
                SurfaceControl build2 = displayContent.makeOverlay().setName("RotationLayer").setOpaque(true).setSecure(z2).setCallsite("ScreenRotationAnimation").setBLASTLayer().build();
                this.mScreenshotLayer = build2;
                InputMonitor.setTrustedOverlayInputInfo(build2, transaction, displayId, "RotationLayer");
                this.mEnterBlackFrameLayer = displayContent.makeOverlay().setName("EnterBlackFrameLayer").setContainerLayer().setCallsite("ScreenRotationAnimation").build();
                HardwareBuffer hardwareBuffer = captureLayers.getHardwareBuffer();
                Trace.traceBegin(32L, "ScreenRotationAnimation#getMedianBorderLuma");
                this.mStartLuma = TransitionAnimation.getBorderLuma(hardwareBuffer, captureLayers.getColorSpace());
                Trace.traceEnd(32L);
                transaction.setLayer(this.mScreenshotLayer, 2010000);
                transaction.reparent(this.mBackColorSurface, displayContent.getSurfaceControl());
                transaction.setDimmingEnabled(this.mScreenshotLayer, !captureLayers.containsHdrLayers());
                transaction.setLayer(this.mBackColorSurface, -1);
                SurfaceControl surfaceControl = this.mBackColorSurface;
                float f = this.mStartLuma;
                transaction.setColor(surfaceControl, new float[]{f, f, f});
                transaction.setAlpha(this.mBackColorSurface, 1.0f);
                transaction.setBuffer(this.mScreenshotLayer, hardwareBuffer);
                transaction.setColorSpace(this.mScreenshotLayer, captureLayers.getColorSpace());
                transaction.show(this.mScreenshotLayer);
                transaction.show(this.mBackColorSurface);
                hardwareBuffer.close();
                SurfaceControl[] surfaceControlArr = this.mRoundedCornerOverlay;
                if (surfaceControlArr != null) {
                    for (SurfaceControl surfaceControl2 : surfaceControlArr) {
                        if (surfaceControl2.isValid()) {
                            transaction.hide(surfaceControl2);
                        }
                    }
                }
            }
        } catch (Surface.OutOfResourcesException e) {
            Slog.w(StartingSurfaceController.TAG, "Unable to allocate freeze surface", e);
        }
        if (this.mScreenshotLayer == null || !z4) {
            i2 = 0;
        } else {
            i2 = 0;
            displayContent.getPendingTransaction().setGeometry(this.mScreenshotLayer, new Rect(0, 0, this.mOriginalWidth, this.mOriginalHeight), new Rect(0, 0, i6, i7), 0);
        }
        if (ProtoLogCache.WM_SHOW_SURFACE_ALLOC_enabled) {
            ProtoLogImpl.i(ProtoLogGroup.WM_SHOW_SURFACE_ALLOC, 10608884, i2, (String) null, new Object[]{String.valueOf(this.mScreenshotLayer)});
        }
        if (i == i3) {
            setRotation(transaction, i3);
        } else {
            this.mCurRotation = i3;
            this.mSnapshotInitialMatrix.reset();
            setRotationTransform(transaction, this.mSnapshotInitialMatrix);
        }
        transaction.apply();
    }

    public void setSkipScreenshotForRoundedCornerOverlays(final boolean z, final SurfaceControl.Transaction transaction) {
        this.mDisplayContent.forAllWindows(new Consumer() { // from class: com.android.server.wm.ScreenRotationAnimation$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ScreenRotationAnimation.lambda$setSkipScreenshotForRoundedCornerOverlays$0(transaction, z, (WindowState) obj);
            }
        }, false);
        if (z) {
            return;
        }
        transaction.apply(true);
    }

    public static /* synthetic */ void lambda$setSkipScreenshotForRoundedCornerOverlays$0(SurfaceControl.Transaction transaction, boolean z, WindowState windowState) {
        if (windowState.mToken.mRoundedCornerOverlay && windowState.isVisible() && windowState.mWinAnimator.hasSurface()) {
            transaction.setSkipScreenshot(windowState.mWinAnimator.mSurfaceController.mSurfaceControl, z);
        }
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1133871366145L, this.mStarted);
        protoOutputStream.write(1133871366146L, this.mAnimRunning);
        protoOutputStream.end(start);
    }

    public boolean hasScreenshot() {
        return this.mScreenshotLayer != null;
    }

    public final void setRotationTransform(SurfaceControl.Transaction transaction, Matrix matrix) {
        if (this.mScreenshotLayer == null) {
            return;
        }
        matrix.getValues(this.mTmpFloats);
        float[] fArr = this.mTmpFloats;
        transaction.setPosition(this.mScreenshotLayer, fArr[2], fArr[5]);
        SurfaceControl surfaceControl = this.mScreenshotLayer;
        float[] fArr2 = this.mTmpFloats;
        transaction.setMatrix(surfaceControl, fArr2[0], fArr2[3], fArr2[1], fArr2[4]);
        transaction.setAlpha(this.mScreenshotLayer, 1.0f);
        transaction.show(this.mScreenshotLayer);
    }

    public void printTo(String str, PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("mSurface=");
        printWriter.print(this.mScreenshotLayer);
        printWriter.print(str);
        printWriter.print("mEnteringBlackFrame=");
        printWriter.println(this.mEnteringBlackFrame);
        BlackFrame blackFrame = this.mEnteringBlackFrame;
        if (blackFrame != null) {
            blackFrame.printTo(str + "  ", printWriter);
        }
        printWriter.print(str);
        printWriter.print("mCurRotation=");
        printWriter.print(this.mCurRotation);
        printWriter.print(" mOriginalRotation=");
        printWriter.println(this.mOriginalRotation);
        printWriter.print(str);
        printWriter.print("mOriginalWidth=");
        printWriter.print(this.mOriginalWidth);
        printWriter.print(" mOriginalHeight=");
        printWriter.println(this.mOriginalHeight);
        printWriter.print(str);
        printWriter.print("mStarted=");
        printWriter.print(this.mStarted);
        printWriter.print(" mAnimRunning=");
        printWriter.print(this.mAnimRunning);
        printWriter.print(" mFinishAnimReady=");
        printWriter.print(this.mFinishAnimReady);
        printWriter.print(" mFinishAnimStartTime=");
        printWriter.println(this.mFinishAnimStartTime);
        printWriter.print(str);
        printWriter.print("mRotateExitAnimation=");
        printWriter.print(this.mRotateExitAnimation);
        printWriter.print(" ");
        this.mRotateExitTransformation.printShortString(printWriter);
        printWriter.println();
        printWriter.print(str);
        printWriter.print("mRotateEnterAnimation=");
        printWriter.print(this.mRotateEnterAnimation);
        printWriter.print(" ");
        this.mRotateEnterTransformation.printShortString(printWriter);
        printWriter.println();
        printWriter.print(str);
        printWriter.print("mSnapshotInitialMatrix=");
        this.mSnapshotInitialMatrix.dump(printWriter);
        printWriter.println();
    }

    public void setRotation(SurfaceControl.Transaction transaction, int i) {
        this.mCurRotation = i;
        CoordinateTransforms.computeRotationMatrix(RotationUtils.deltaRotation(i, this.mOriginalRotation), this.mOriginalWidth, this.mOriginalHeight, this.mSnapshotInitialMatrix);
        setRotationTransform(transaction, this.mSnapshotInitialMatrix);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x017e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startAnimation(SurfaceControl.Transaction transaction, long j, float f, int i, int i2, int i3, int i4) {
        boolean z;
        if (this.mScreenshotLayer == null) {
            return false;
        }
        if (this.mStarted) {
            return true;
        }
        this.mStarted = true;
        int deltaRotation = RotationUtils.deltaRotation(this.mCurRotation, this.mOriginalRotation);
        if (CoreRune.FW_BLACK_SNAPSHOT_TRANSITION && this.mHasBlackSnapshot) {
            deltaRotation = 0;
        }
        if (i3 != 0 && i4 != 0) {
            this.mRotateExitAnimation = AnimationUtils.loadAnimation(this.mContext, i3);
            this.mRotateEnterAnimation = AnimationUtils.loadAnimation(this.mContext, i4);
            if (!CoreRune.FW_BLACK_SNAPSHOT_TRANSITION || !this.mHasBlackSnapshot) {
                this.mRotateAlphaAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.ft_avd_tooverflow_rectangle_path_2_animation);
                z = true;
                if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                    ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_ORIENTATION, -177040661, 0, (String) null, new Object[]{String.valueOf(z), String.valueOf(Surface.rotationToString(this.mCurRotation)), String.valueOf(Surface.rotationToString(this.mOriginalRotation))});
                }
                this.mRotateExitAnimation.initialize(i, i2, this.mOriginalWidth, this.mOriginalHeight);
                this.mRotateExitAnimation.restrictDuration(j);
                this.mRotateExitAnimation.scaleCurrentDuration(f);
                this.mRotateEnterAnimation.initialize(i, i2, this.mOriginalWidth, this.mOriginalHeight);
                this.mRotateEnterAnimation.restrictDuration(j);
                this.mRotateEnterAnimation.scaleCurrentDuration(f);
                this.mAnimRunning = false;
                this.mFinishAnimReady = false;
                this.mFinishAnimStartTime = -1L;
                if (z) {
                    this.mRotateAlphaAnimation.restrictDuration(j);
                    this.mRotateAlphaAnimation.scaleCurrentDuration(f);
                }
                if (z && this.mEnteringBlackFrame == null) {
                    try {
                        this.mEnteringBlackFrame = new BlackFrame(this.mService.mTransactionFactory, transaction, new Rect(-i, -i2, i * 2, i2 * 2), new Rect(0, 0, i, i2), 2010000, this.mDisplayContent, false, this.mEnterBlackFrameLayer);
                    } catch (Surface.OutOfResourcesException e) {
                        Slog.w(StartingSurfaceController.TAG, "Unable to allocate black surface", e);
                    }
                }
                if (!z) {
                    this.mSurfaceRotationAnimationController.startCustomAnimation();
                } else {
                    this.mSurfaceRotationAnimationController.startScreenRotationAnimation();
                }
                return true;
            }
        } else if (deltaRotation == 0) {
            this.mRotateExitAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.ft_avd_tooverflow_rectangle_2_pivot_animation);
            this.mRotateEnterAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.task_fragment_clear_top_close_enter);
        } else if (deltaRotation == 1) {
            this.mRotateExitAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.ic_bluetooth_transient_animation_2);
            this.mRotateEnterAnimation = AnimationUtils.loadAnimation(this.mContext, CoreRune.FW_CUSTOM_BASIC_ANIM ? R.anim.voice_layer_exit : R.anim.ic_bluetooth_transient_animation_1);
        } else if (deltaRotation == 2) {
            this.mRotateExitAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.ft_avd_tooverflow_rectangle_3_pivot_animation);
            this.mRotateEnterAnimation = AnimationUtils.loadAnimation(this.mContext, CoreRune.FW_CUSTOM_BASIC_ANIM ? R.anim.voice_activity_open_exit : R.anim.ft_avd_tooverflow_rectangle_3_animation);
        } else if (deltaRotation == 3) {
            this.mRotateExitAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.ic_bluetooth_transient_animation_0);
            this.mRotateEnterAnimation = AnimationUtils.loadAnimation(this.mContext, CoreRune.FW_CUSTOM_BASIC_ANIM ? R.anim.voice_layer_enter : R.anim.grow_fade_in_from_bottom);
        }
        z = false;
        if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
        }
        this.mRotateExitAnimation.initialize(i, i2, this.mOriginalWidth, this.mOriginalHeight);
        this.mRotateExitAnimation.restrictDuration(j);
        this.mRotateExitAnimation.scaleCurrentDuration(f);
        this.mRotateEnterAnimation.initialize(i, i2, this.mOriginalWidth, this.mOriginalHeight);
        this.mRotateEnterAnimation.restrictDuration(j);
        this.mRotateEnterAnimation.scaleCurrentDuration(f);
        this.mAnimRunning = false;
        this.mFinishAnimReady = false;
        this.mFinishAnimStartTime = -1L;
        if (z) {
        }
        if (z) {
            this.mEnteringBlackFrame = new BlackFrame(this.mService.mTransactionFactory, transaction, new Rect(-i, -i2, i * 2, i2 * 2), new Rect(0, 0, i, i2), 2010000, this.mDisplayContent, false, this.mEnterBlackFrameLayer);
        }
        if (!z) {
        }
        return true;
    }

    public boolean dismiss(SurfaceControl.Transaction transaction, long j, float f, int i, int i2, int i3, int i4) {
        if (this.mScreenshotLayer == null) {
            return false;
        }
        if (!this.mStarted) {
            this.mEndLuma = TransitionAnimation.getBorderLuma(this.mDisplayContent.getWindowingLayer(), i, i2);
            startAnimation(transaction, j, f, i, i2, i3, i4);
        }
        if (!this.mStarted) {
            return false;
        }
        this.mFinishAnimReady = true;
        return true;
    }

    public void kill() {
        SurfaceRotationAnimationController surfaceRotationAnimationController = this.mSurfaceRotationAnimationController;
        if (surfaceRotationAnimationController != null) {
            surfaceRotationAnimationController.cancel();
            this.mSurfaceRotationAnimationController = null;
        }
        SurfaceControl surfaceControl = this.mScreenshotLayer;
        if (surfaceControl != null) {
            if (ProtoLogCache.WM_SHOW_SURFACE_ALLOC_enabled) {
                ProtoLogImpl.i(ProtoLogGroup.WM_SHOW_SURFACE_ALLOC, 1089714158, 0, (String) null, new Object[]{String.valueOf(surfaceControl)});
            }
            SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.mService.mTransactionFactory.get();
            if (this.mScreenshotLayer.isValid()) {
                transaction.remove(this.mScreenshotLayer);
            }
            this.mScreenshotLayer = null;
            SurfaceControl surfaceControl2 = this.mEnterBlackFrameLayer;
            if (surfaceControl2 != null) {
                if (surfaceControl2.isValid()) {
                    transaction.remove(this.mEnterBlackFrameLayer);
                }
                this.mEnterBlackFrameLayer = null;
            }
            SurfaceControl surfaceControl3 = this.mBackColorSurface;
            if (surfaceControl3 != null) {
                if (surfaceControl3.isValid()) {
                    transaction.remove(this.mBackColorSurface);
                }
                this.mBackColorSurface = null;
            }
            if (this.mRoundedCornerOverlay != null) {
                if (this.mDisplayContent.getRotationAnimation() == null || this.mDisplayContent.getRotationAnimation() == this) {
                    setSkipScreenshotForRoundedCornerOverlays(true, transaction);
                    for (SurfaceControl surfaceControl4 : this.mRoundedCornerOverlay) {
                        if (surfaceControl4.isValid()) {
                            transaction.show(surfaceControl4);
                        }
                    }
                }
                this.mRoundedCornerOverlay = null;
            }
            transaction.apply();
        }
        BlackFrame blackFrame = this.mEnteringBlackFrame;
        if (blackFrame != null) {
            blackFrame.kill();
            this.mEnteringBlackFrame = null;
        }
        Animation animation = this.mRotateExitAnimation;
        if (animation != null) {
            animation.cancel();
            this.mRotateExitAnimation = null;
        }
        Animation animation2 = this.mRotateEnterAnimation;
        if (animation2 != null) {
            animation2.cancel();
            this.mRotateEnterAnimation = null;
        }
        Animation animation3 = this.mRotateAlphaAnimation;
        if (animation3 != null) {
            animation3.cancel();
            this.mRotateAlphaAnimation = null;
        }
    }

    public boolean isAnimating() {
        SurfaceRotationAnimationController surfaceRotationAnimationController = this.mSurfaceRotationAnimationController;
        return surfaceRotationAnimationController != null && surfaceRotationAnimationController.isAnimating();
    }

    public class SurfaceRotationAnimationController {
        public SurfaceAnimator mDisplayAnimator;
        public SurfaceAnimator mEnterBlackFrameAnimator;
        public SurfaceAnimator mRotateScreenAnimator;
        public SurfaceAnimator mScreenshotRotationAnimator;

        public SurfaceRotationAnimationController() {
        }

        public void startCustomAnimation() {
            try {
                ScreenRotationAnimation.this.mService.mSurfaceAnimationRunner.deferStartingAnimations();
                this.mRotateScreenAnimator = startScreenshotAlphaAnimation();
                this.mDisplayAnimator = startDisplayRotation();
                if (ScreenRotationAnimation.this.mEnteringBlackFrame != null) {
                    this.mEnterBlackFrameAnimator = startEnterBlackFrameAnimation();
                }
            } finally {
                ScreenRotationAnimation.this.mService.mSurfaceAnimationRunner.continueStartingAnimations();
            }
        }

        public void startScreenRotationAnimation() {
            try {
                ScreenRotationAnimation.this.mService.mSurfaceAnimationRunner.deferStartingAnimations();
                this.mDisplayAnimator = startDisplayRotation();
                this.mScreenshotRotationAnimator = startScreenshotRotationAnimation();
                startColorAnimation();
            } finally {
                ScreenRotationAnimation.this.mService.mSurfaceAnimationRunner.continueStartingAnimations();
            }
        }

        public final SimpleSurfaceAnimatable.Builder initializeBuilder() {
            SimpleSurfaceAnimatable.Builder builder = new SimpleSurfaceAnimatable.Builder();
            final DisplayContent displayContent = ScreenRotationAnimation.this.mDisplayContent;
            Objects.requireNonNull(displayContent);
            SimpleSurfaceAnimatable.Builder syncTransactionSupplier = builder.setSyncTransactionSupplier(new Supplier() { // from class: com.android.server.wm.ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return DisplayContent.this.getSyncTransaction();
                }
            });
            final DisplayContent displayContent2 = ScreenRotationAnimation.this.mDisplayContent;
            Objects.requireNonNull(displayContent2);
            SimpleSurfaceAnimatable.Builder pendingTransactionSupplier = syncTransactionSupplier.setPendingTransactionSupplier(new Supplier() { // from class: com.android.server.wm.ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    return DisplayContent.this.getPendingTransaction();
                }
            });
            final DisplayContent displayContent3 = ScreenRotationAnimation.this.mDisplayContent;
            Objects.requireNonNull(displayContent3);
            SimpleSurfaceAnimatable.Builder commitTransactionRunnable = pendingTransactionSupplier.setCommitTransactionRunnable(new Runnable() { // from class: com.android.server.wm.ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayContent.this.commitPendingTransaction();
                }
            });
            final DisplayContent displayContent4 = ScreenRotationAnimation.this.mDisplayContent;
            Objects.requireNonNull(displayContent4);
            return commitTransactionRunnable.setAnimationLeashSupplier(new Supplier() { // from class: com.android.server.wm.ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final Object get() {
                    return DisplayContent.this.makeOverlay();
                }
            });
        }

        public final SurfaceAnimator startDisplayRotation() {
            SurfaceAnimator startAnimation = startAnimation(initializeBuilder().setAnimationLeashParent(ScreenRotationAnimation.this.mDisplayContent.getSurfaceControl()).setSurfaceControl(ScreenRotationAnimation.this.mDisplayContent.getWindowingLayer()).setParentSurfaceControl(ScreenRotationAnimation.this.mDisplayContent.getSurfaceControl()).setWidth(ScreenRotationAnimation.this.mDisplayContent.getSurfaceWidth()).setHeight(ScreenRotationAnimation.this.mDisplayContent.getSurfaceHeight()).build(), createWindowAnimationSpec(ScreenRotationAnimation.this.mRotateEnterAnimation), new C2967x5a652b8e(this));
            Rect bounds = ScreenRotationAnimation.this.mDisplayContent.getBounds();
            ScreenRotationAnimation.this.mDisplayContent.getPendingTransaction().setWindowCrop(startAnimation.mLeash, bounds.width(), bounds.height());
            return startAnimation;
        }

        public final SurfaceAnimator startScreenshotAlphaAnimation() {
            return startAnimation(initializeBuilder().setSurfaceControl(ScreenRotationAnimation.this.mScreenshotLayer).setAnimationLeashParent(ScreenRotationAnimation.this.mDisplayContent.getOverlayLayer()).setWidth(ScreenRotationAnimation.this.mDisplayContent.getSurfaceWidth()).setHeight(ScreenRotationAnimation.this.mDisplayContent.getSurfaceHeight()).build(), createWindowAnimationSpec(ScreenRotationAnimation.this.mRotateAlphaAnimation), new C2967x5a652b8e(this));
        }

        public final SurfaceAnimator startEnterBlackFrameAnimation() {
            return startAnimation(initializeBuilder().setSurfaceControl(ScreenRotationAnimation.this.mEnterBlackFrameLayer).setAnimationLeashParent(ScreenRotationAnimation.this.mDisplayContent.getOverlayLayer()).build(), createWindowAnimationSpec(ScreenRotationAnimation.this.mRotateEnterAnimation), new C2967x5a652b8e(this));
        }

        public final SurfaceAnimator startScreenshotRotationAnimation() {
            return startAnimation(initializeBuilder().setSurfaceControl(ScreenRotationAnimation.this.mScreenshotLayer).setAnimationLeashParent(ScreenRotationAnimation.this.mDisplayContent.getOverlayLayer()).build(), createWindowAnimationSpec(ScreenRotationAnimation.this.mRotateExitAnimation), new C2967x5a652b8e(this));
        }

        public final void startColorAnimation() {
            if (CoreRune.FW_BLACK_SNAPSHOT_TRANSITION && ScreenRotationAnimation.this.mBackColorSurface == null) {
                return;
            }
            final int integer = ScreenRotationAnimation.this.mContext.getResources().getInteger(R.integer.config_screenshotChordKeyTimeout);
            SurfaceAnimationRunner surfaceAnimationRunner = ScreenRotationAnimation.this.mService.mSurfaceAnimationRunner;
            final float[] fArr = new float[3];
            final int rgb = Color.rgb(ScreenRotationAnimation.this.mStartLuma, ScreenRotationAnimation.this.mStartLuma, ScreenRotationAnimation.this.mStartLuma);
            final int rgb2 = Color.rgb(ScreenRotationAnimation.this.mEndLuma, ScreenRotationAnimation.this.mEndLuma, ScreenRotationAnimation.this.mEndLuma);
            final long currentAnimatorScale = integer * ((long) ScreenRotationAnimation.this.mService.getCurrentAnimatorScale());
            final ArgbEvaluator argbEvaluator = ArgbEvaluator.getInstance();
            surfaceAnimationRunner.startAnimation(new LocalAnimationAdapter.AnimationSpec() { // from class: com.android.server.wm.ScreenRotationAnimation.SurfaceRotationAnimationController.1
                @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
                public long getDuration() {
                    return currentAnimatorScale;
                }

                @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
                public void apply(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, long j) {
                    Color valueOf = Color.valueOf(((Integer) argbEvaluator.evaluate(getFraction(j), Integer.valueOf(rgb), Integer.valueOf(rgb2))).intValue());
                    fArr[0] = valueOf.red();
                    fArr[1] = valueOf.green();
                    fArr[2] = valueOf.blue();
                    if (surfaceControl.isValid()) {
                        transaction.setColor(surfaceControl, fArr);
                    }
                }

                @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
                public void dump(PrintWriter printWriter, String str) {
                    printWriter.println(str + "startLuma=" + ScreenRotationAnimation.this.mStartLuma + " endLuma=" + ScreenRotationAnimation.this.mEndLuma + " durationMs=" + integer);
                }

                @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
                public void dumpDebugInner(ProtoOutputStream protoOutputStream) {
                    long start = protoOutputStream.start(1146756268036L);
                    protoOutputStream.write(1108101562369L, ScreenRotationAnimation.this.mStartLuma);
                    protoOutputStream.write(1108101562370L, ScreenRotationAnimation.this.mEndLuma);
                    protoOutputStream.write(1112396529667L, integer);
                    protoOutputStream.end(start);
                }
            }, ScreenRotationAnimation.this.mBackColorSurface, ScreenRotationAnimation.this.mDisplayContent.getPendingTransaction(), null);
        }

        public final WindowAnimationSpec createWindowAnimationSpec(Animation animation) {
            return new WindowAnimationSpec(animation, new Point(0, 0), false, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        }

        public final SurfaceAnimator startAnimation(SurfaceAnimator.Animatable animatable, LocalAnimationAdapter.AnimationSpec animationSpec, SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
            SurfaceAnimator surfaceAnimator = new SurfaceAnimator(animatable, onAnimationFinishedCallback, ScreenRotationAnimation.this.mService);
            surfaceAnimator.startAnimation(ScreenRotationAnimation.this.mDisplayContent.getPendingTransaction(), new LocalAnimationAdapter(animationSpec, ScreenRotationAnimation.this.mService.mSurfaceAnimationRunner), false, 2);
            return surfaceAnimator;
        }

        public final void onAnimationEnd(int i, AnimationAdapter animationAdapter) {
            WindowManagerGlobalLock windowManagerGlobalLock = ScreenRotationAnimation.this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    if (isAnimating()) {
                        if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                            long j = i;
                            SurfaceAnimator surfaceAnimator = this.mDisplayAnimator;
                            String valueOf = String.valueOf(surfaceAnimator != null ? Boolean.valueOf(surfaceAnimator.isAnimating()) : null);
                            SurfaceAnimator surfaceAnimator2 = this.mEnterBlackFrameAnimator;
                            String valueOf2 = String.valueOf(surfaceAnimator2 != null ? Boolean.valueOf(surfaceAnimator2.isAnimating()) : null);
                            SurfaceAnimator surfaceAnimator3 = this.mRotateScreenAnimator;
                            String valueOf3 = String.valueOf(surfaceAnimator3 != null ? Boolean.valueOf(surfaceAnimator3.isAnimating()) : null);
                            SurfaceAnimator surfaceAnimator4 = this.mScreenshotRotationAnimator;
                            ProtoLogImpl.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, 1346895820, 1, (String) null, new Object[]{Long.valueOf(j), valueOf, valueOf2, valueOf3, String.valueOf(surfaceAnimator4 != null ? Boolean.valueOf(surfaceAnimator4.isAnimating()) : null)});
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    if (ProtoLogCache.WM_DEBUG_ORIENTATION_enabled) {
                        ProtoLogImpl.d(ProtoLogGroup.WM_DEBUG_ORIENTATION, 200829729, 0, (String) null, (Object[]) null);
                    }
                    this.mEnterBlackFrameAnimator = null;
                    this.mScreenshotRotationAnimator = null;
                    this.mRotateScreenAnimator = null;
                    ScreenRotationAnimation.this.mService.mAnimator.mBulkUpdateParams |= 1;
                    ScreenRotationAnimation rotationAnimation = ScreenRotationAnimation.this.mDisplayContent.getRotationAnimation();
                    ScreenRotationAnimation screenRotationAnimation = ScreenRotationAnimation.this;
                    if (rotationAnimation == screenRotationAnimation) {
                        screenRotationAnimation.mDisplayContent.setRotationAnimation(null);
                        if (ScreenRotationAnimation.this.mDisplayContent.mDisplayRotationCompatPolicy != null) {
                            ScreenRotationAnimation.this.mDisplayContent.mDisplayRotationCompatPolicy.onScreenRotationAnimationFinished();
                        }
                    } else {
                        screenRotationAnimation.kill();
                    }
                    ScreenRotationAnimation.this.mService.updateRotation(false, false);
                    WindowManagerService.resetPriorityAfterLockedSection();
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }

        public void cancel() {
            SurfaceAnimator surfaceAnimator = this.mEnterBlackFrameAnimator;
            if (surfaceAnimator != null) {
                surfaceAnimator.cancelAnimation();
            }
            SurfaceAnimator surfaceAnimator2 = this.mScreenshotRotationAnimator;
            if (surfaceAnimator2 != null) {
                surfaceAnimator2.cancelAnimation();
            }
            SurfaceAnimator surfaceAnimator3 = this.mRotateScreenAnimator;
            if (surfaceAnimator3 != null) {
                surfaceAnimator3.cancelAnimation();
            }
            SurfaceAnimator surfaceAnimator4 = this.mDisplayAnimator;
            if (surfaceAnimator4 != null) {
                surfaceAnimator4.cancelAnimation();
            }
            if (ScreenRotationAnimation.this.mBackColorSurface != null) {
                ScreenRotationAnimation.this.mService.mSurfaceAnimationRunner.onAnimationCancelled(ScreenRotationAnimation.this.mBackColorSurface);
            }
        }

        public boolean isAnimating() {
            SurfaceAnimator surfaceAnimator;
            SurfaceAnimator surfaceAnimator2;
            SurfaceAnimator surfaceAnimator3;
            SurfaceAnimator surfaceAnimator4 = this.mDisplayAnimator;
            return (surfaceAnimator4 != null && surfaceAnimator4.isAnimating()) || ((surfaceAnimator = this.mEnterBlackFrameAnimator) != null && surfaceAnimator.isAnimating()) || (((surfaceAnimator2 = this.mRotateScreenAnimator) != null && surfaceAnimator2.isAnimating()) || ((surfaceAnimator3 = this.mScreenshotRotationAnimator) != null && surfaceAnimator3.isAnimating()));
        }
    }

    public final boolean shouldHasBlackSnapshot() {
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent.mHasBlackSnapshot) {
            return true;
        }
        if (displayContent.getDisplayId() == 2 && this.mService.mAtmService.mDexController.getDexModeLocked() == 2) {
            return false;
        }
        DisplayContent displayContent2 = this.mDisplayContent;
        if (displayContent2.isDefaultDisplay) {
            return !this.mService.mExt.mPolicyExt.isScreenTurnedOn();
        }
        return displayContent2.getDisplay().getState() != 2;
    }
}
