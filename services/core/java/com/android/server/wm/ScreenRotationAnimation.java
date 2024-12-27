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
import android.view.animation.Transformation;
import android.window.ScreenCapture;

import com.android.internal.policy.TransitionAnimation;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.DisplayControl;
import com.android.window.flags.Flags;

import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class ScreenRotationAnimation {
    public SurfaceControl mBackColorSurface;
    public final Context mContext;
    public int mCurRotation;
    public final DisplayContent mDisplayContent;
    public float mEndLuma;
    public SurfaceControl mEnterBlackFrameLayer;
    public BlackFrame mEnteringBlackFrame;
    public boolean mFinishAnimReady;
    public long mFinishAnimStartTime;
    public final int mOriginalHeight;
    public final int mOriginalRotation;
    public final int mOriginalWidth;
    public Animation mRotateAlphaAnimation;
    public Animation mRotateEnterAnimation;
    public Animation mRotateExitAnimation;
    public SurfaceControl[] mRoundedCornerOverlay;
    public SurfaceControl mScreenshotLayer;
    public final WindowManagerService mService;
    public final float mStartLuma;
    public boolean mStarted;
    public SurfaceRotationAnimationController mSurfaceRotationAnimationController;
    public final float[] mTmpFloats = new float[9];
    public final Transformation mRotateExitTransformation = new Transformation();
    public final Transformation mRotateEnterTransformation = new Transformation();
    public final Matrix mSnapshotInitialMatrix = new Matrix();

    public final class SurfaceRotationAnimationController {
        public SurfaceAnimator mDisplayAnimator;
        public SurfaceAnimator mEnterBlackFrameAnimator;
        public SurfaceAnimator mRotateScreenAnimator;
        public SurfaceAnimator mScreenshotRotationAnimator;

        public SurfaceRotationAnimationController() {}

        public static WindowAnimationSpec createWindowAnimationSpec(Animation animation) {
            return new WindowAnimationSpec(
                    animation,
                    new Point(0, 0),
                    false,
                    FullScreenMagnificationGestureHandler.MAX_SCALE);
        }

        public final SimpleSurfaceAnimatable.Builder initializeBuilder() {
            SimpleSurfaceAnimatable.Builder builder = new SimpleSurfaceAnimatable.Builder();
            builder.mWidth = -1;
            builder.mHeight = -1;
            builder.mAnimationLeashParent = null;
            builder.mSurfaceControl = null;
            builder.mParentSurfaceControl = null;
            ScreenRotationAnimation screenRotationAnimation = ScreenRotationAnimation.this;
            final DisplayContent displayContent = screenRotationAnimation.mDisplayContent;
            Objects.requireNonNull(displayContent);
            final int i = 0;
            builder.mSyncTransactionSupplier =
                    new Supplier() { // from class:
                                     // com.android.server.wm.ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda1
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            int i2 = i;
                            DisplayContent displayContent2 = displayContent;
                            switch (i2) {
                                case 0:
                                    return displayContent2.getSyncTransaction();
                                case 1:
                                    return displayContent2.getPendingTransaction();
                                default:
                                    return displayContent2.makeOverlay();
                            }
                        }
                    };
            final DisplayContent displayContent2 = screenRotationAnimation.mDisplayContent;
            Objects.requireNonNull(displayContent2);
            final int i2 = 1;
            builder.mPendingTransactionSupplier =
                    new Supplier() { // from class:
                                     // com.android.server.wm.ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda1
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            int i22 = i2;
                            DisplayContent displayContent22 = displayContent2;
                            switch (i22) {
                                case 0:
                                    return displayContent22.getSyncTransaction();
                                case 1:
                                    return displayContent22.getPendingTransaction();
                                default:
                                    return displayContent22.makeOverlay();
                            }
                        }
                    };
            Objects.requireNonNull(displayContent2);
            builder.mCommitTransactionRunnable =
                    new Runnable() { // from class:
                                     // com.android.server.wm.ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            DisplayContent.this.commitPendingTransaction();
                        }
                    };
            Objects.requireNonNull(displayContent2);
            final int i3 = 2;
            builder.mAnimationLeashFactory =
                    new Supplier() { // from class:
                                     // com.android.server.wm.ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda1
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            int i22 = i3;
                            DisplayContent displayContent22 = displayContent2;
                            switch (i22) {
                                case 0:
                                    return displayContent22.getSyncTransaction();
                                case 1:
                                    return displayContent22.getPendingTransaction();
                                default:
                                    return displayContent22.makeOverlay();
                            }
                        }
                    };
            return builder;
        }

        public final boolean isAnimating() {
            SurfaceAnimator surfaceAnimator;
            SurfaceAnimator surfaceAnimator2;
            SurfaceAnimator surfaceAnimator3;
            SurfaceAnimator surfaceAnimator4 = this.mDisplayAnimator;
            return (surfaceAnimator4 != null && surfaceAnimator4.isAnimating())
                    || ((surfaceAnimator = this.mEnterBlackFrameAnimator) != null
                            && surfaceAnimator.isAnimating())
                    || (((surfaceAnimator2 = this.mRotateScreenAnimator) != null
                                    && surfaceAnimator2.isAnimating())
                            || ((surfaceAnimator3 = this.mScreenshotRotationAnimator) != null
                                    && surfaceAnimator3.isAnimating()));
        }

        public final SurfaceAnimator startAnimation(
                SimpleSurfaceAnimatable simpleSurfaceAnimatable,
                WindowAnimationSpec windowAnimationSpec,
                SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
            ScreenRotationAnimation screenRotationAnimation = ScreenRotationAnimation.this;
            SurfaceAnimator surfaceAnimator =
                    new SurfaceAnimator(
                            simpleSurfaceAnimatable,
                            onAnimationFinishedCallback,
                            screenRotationAnimation.mService);
            surfaceAnimator.startAnimation(
                    screenRotationAnimation.mDisplayContent.getPendingTransaction(),
                    new LocalAnimationAdapter(
                            windowAnimationSpec,
                            screenRotationAnimation.mService.mSurfaceAnimationRunner),
                    false,
                    2,
                    null,
                    null,
                    null,
                    null);
            return surfaceAnimator;
        }

        public final void startColorAnimation() {
            boolean z = CoreRune.FW_BLACK_SNAPSHOT_TRANSITION;
            ScreenRotationAnimation screenRotationAnimation = ScreenRotationAnimation.this;
            if (z && screenRotationAnimation.mBackColorSurface == null) {
                return;
            }
            final int integer =
                    screenRotationAnimation
                            .mContext
                            .getResources()
                            .getInteger(R.integer.date_picker_header_max_lines_material);
            WindowManagerService windowManagerService = screenRotationAnimation.mService;
            SurfaceAnimationRunner surfaceAnimationRunner =
                    windowManagerService.mSurfaceAnimationRunner;
            final float[] fArr = new float[3];
            float f = screenRotationAnimation.mStartLuma;
            final int rgb = Color.rgb(f, f, f);
            float f2 = screenRotationAnimation.mEndLuma;
            final int rgb2 = Color.rgb(f2, f2, f2);
            final long currentAnimatorScale =
                    ((long) windowManagerService.getCurrentAnimatorScale()) * integer;
            final ArgbEvaluator argbEvaluator = ArgbEvaluator.getInstance();
            surfaceAnimationRunner.startAnimation(
                    new LocalAnimationAdapter
                            .AnimationSpec() { // from class:
                                               // com.android.server.wm.ScreenRotationAnimation.SurfaceRotationAnimationController.1
                        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
                        public final void apply(
                                SurfaceControl.Transaction transaction,
                                SurfaceControl surfaceControl,
                                long j) {
                            Color valueOf =
                                    Color.valueOf(
                                            ((Integer)
                                                            argbEvaluator.evaluate(
                                                                    getFraction(j),
                                                                    Integer.valueOf(rgb),
                                                                    Integer.valueOf(rgb2)))
                                                    .intValue());
                            float red = valueOf.red();
                            float[] fArr2 = fArr;
                            fArr2[0] = red;
                            fArr2[1] = valueOf.green();
                            fArr2[2] = valueOf.blue();
                            if (surfaceControl.isValid()) {
                                transaction.setColor(surfaceControl, fArr2);
                            }
                        }

                        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
                        public final void dump(PrintWriter printWriter, String str) {
                            StringBuilder m =
                                    Preconditions$$ExternalSyntheticOutline0.m(str, "startLuma=");
                            SurfaceRotationAnimationController surfaceRotationAnimationController =
                                    SurfaceRotationAnimationController.this;
                            m.append(ScreenRotationAnimation.this.mStartLuma);
                            m.append(" endLuma=");
                            m.append(ScreenRotationAnimation.this.mEndLuma);
                            m.append(" durationMs=");
                            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                    m, integer, printWriter);
                        }

                        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
                        public final void dumpDebugInner(ProtoOutputStream protoOutputStream) {
                            long start = protoOutputStream.start(1146756268036L);
                            SurfaceRotationAnimationController surfaceRotationAnimationController =
                                    SurfaceRotationAnimationController.this;
                            protoOutputStream.write(
                                    1108101562369L, ScreenRotationAnimation.this.mStartLuma);
                            protoOutputStream.write(
                                    1108101562370L, ScreenRotationAnimation.this.mEndLuma);
                            protoOutputStream.write(1112396529667L, integer);
                            protoOutputStream.end(start);
                        }

                        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
                        public final long getDuration() {
                            return currentAnimatorScale;
                        }
                    },
                    screenRotationAnimation.mBackColorSurface,
                    screenRotationAnimation.mDisplayContent.getPendingTransaction(),
                    null);
        }

        public final SurfaceAnimator startDisplayRotation() {
            SimpleSurfaceAnimatable.Builder initializeBuilder = initializeBuilder();
            ScreenRotationAnimation screenRotationAnimation = ScreenRotationAnimation.this;
            initializeBuilder.mAnimationLeashParent =
                    screenRotationAnimation.mDisplayContent.getSurfaceControl();
            DisplayContent displayContent = screenRotationAnimation.mDisplayContent;
            initializeBuilder.mSurfaceControl = displayContent.getWindowingLayer();
            initializeBuilder.mParentSurfaceControl = displayContent.getSurfaceControl();
            initializeBuilder.mWidth = displayContent.getSurfaceWidth();
            initializeBuilder.mHeight = displayContent.getSurfaceHeight();
            SurfaceAnimator startAnimation =
                    startAnimation(
                            initializeBuilder.build(),
                            createWindowAnimationSpec(
                                    screenRotationAnimation.mRotateEnterAnimation),
                            new ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda0(
                                    this));
            Rect bounds = displayContent.getBounds();
            displayContent
                    .getPendingTransaction()
                    .setWindowCrop(startAnimation.mLeash, bounds.width(), bounds.height());
            return startAnimation;
        }

        public final SurfaceAnimator startScreenshotAlphaAnimation() {
            SimpleSurfaceAnimatable.Builder initializeBuilder = initializeBuilder();
            ScreenRotationAnimation screenRotationAnimation = ScreenRotationAnimation.this;
            initializeBuilder.mSurfaceControl = screenRotationAnimation.mScreenshotLayer;
            DisplayContent displayContent = screenRotationAnimation.mDisplayContent;
            initializeBuilder.mAnimationLeashParent = displayContent.mOverlayLayer;
            initializeBuilder.mWidth = displayContent.getSurfaceWidth();
            initializeBuilder.mHeight = displayContent.getSurfaceHeight();
            return startAnimation(
                    initializeBuilder.build(),
                    createWindowAnimationSpec(screenRotationAnimation.mRotateAlphaAnimation),
                    new ScreenRotationAnimation$SurfaceRotationAnimationController$$ExternalSyntheticLambda0(
                            this));
        }
    }

    public ScreenRotationAnimation(int i, DisplayContent displayContent) {
        ScreenCapture.ScreenshotHardwareBuffer captureLayers;
        WindowManagerService windowManagerService = displayContent.mWmService;
        this.mService = windowManagerService;
        this.mContext = windowManagerService.mContext;
        this.mDisplayContent = displayContent;
        Rect bounds = displayContent.getBounds();
        int width = bounds.width();
        int height = bounds.height();
        DisplayInfo displayInfo = displayContent.mDisplayInfo;
        int i2 = displayInfo.rotation;
        this.mOriginalRotation = i;
        int deltaRotation = RotationUtils.deltaRotation(i, i2);
        boolean z = deltaRotation == 1 || deltaRotation == 3;
        int i3 = z ? height : width;
        this.mOriginalWidth = i3;
        int i4 = z ? width : height;
        this.mOriginalHeight = i4;
        int i5 = displayInfo.logicalWidth;
        int i6 = displayInfo.logicalHeight;
        boolean z2 = (i5 > i3) == (i6 > i4) && !(i5 == i3 && i6 == i4);
        this.mSurfaceRotationAnimationController = new SurfaceRotationAnimationController();
        boolean z3 =
                displayContent.getWindow(new DisplayContent$$ExternalSyntheticLambda7(3)) != null;
        int i7 = displayContent.mDisplayId;
        SurfaceControl.Transaction transaction =
                (SurfaceControl.Transaction) windowManagerService.mTransactionFactory.get();
        try {
            boolean z4 = z3;
            if (CoreRune.FW_BLACK_SNAPSHOT_TRANSITION && displayContent.isSizeRatioChanging()) {
                SurfaceControl build =
                        displayContent
                                .makeOverlay()
                                .setName("BlackSnapshotLayer")
                                .setOpaque(true)
                                .setColorLayer()
                                .setCallsite("ScreenRotationAnimation")
                                .build();
                this.mScreenshotLayer = build;
                transaction.setMetadata(build, 30, 1);
                transaction.setLayer(this.mScreenshotLayer, 2010000);
                transaction.setAlpha(
                        this.mScreenshotLayer, FullScreenMagnificationGestureHandler.MAX_SCALE);
                transaction.show(this.mScreenshotLayer);
            } else {
                if (z2 && !Flags.deleteCaptureDisplay()) {
                    DisplayAddress.Physical physical = displayInfo.address;
                    if (!(physical instanceof DisplayAddress.Physical)) {
                        Slog.e("WindowManager", "Display does not have a physical address: " + i7);
                        return;
                    }
                    IBinder physicalDisplayToken =
                            DisplayControl.getPhysicalDisplayToken(physical.getPhysicalDisplayId());
                    if (physicalDisplayToken == null) {
                        Slog.e("WindowManager", "Display token is null.");
                        return;
                    } else {
                        setSkipScreenshotForRoundedCornerOverlays(transaction, false);
                        this.mRoundedCornerOverlay = displayContent.findRoundedCornerOverlays();
                        captureLayers =
                                ScreenCapture.captureDisplay(
                                        new ScreenCapture.DisplayCaptureArgs.Builder(
                                                        physicalDisplayToken)
                                                .setSourceCrop(new Rect(0, 0, width, height))
                                                .setAllowProtected(true)
                                                .setCaptureSecureLayers(true)
                                                .setHintForSeamlessTransition(true)
                                                .build());
                    }
                } else if (z2) {
                    setSkipScreenshotForRoundedCornerOverlays(transaction, false);
                    captureLayers =
                            ScreenCapture.captureLayers(
                                    new ScreenCapture.LayerCaptureArgs.Builder(
                                                    displayContent.getSurfaceControl())
                                            .setCaptureSecureLayers(true)
                                            .setAllowProtected(true)
                                            .setSourceCrop(new Rect(0, 0, width, height))
                                            .setHintForSeamlessTransition(true)
                                            .build());
                } else {
                    captureLayers =
                            ScreenCapture.captureLayers(
                                    new ScreenCapture.LayerCaptureArgs.Builder(
                                                    displayContent.getSurfaceControl())
                                            .setCaptureSecureLayers(true)
                                            .setAllowProtected(true)
                                            .setSourceCrop(new Rect(0, 0, width, height))
                                            .setHintForSeamlessTransition(true)
                                            .build());
                }
                if (captureLayers == null) {
                    Slog.w("WindowManager", "Unable to take screenshot of display " + i7);
                    return;
                }
                boolean z5 = captureLayers.containsSecureLayers() ? true : z4;
                this.mBackColorSurface =
                        displayContent
                                .makeChildSurface(null)
                                .setName("BackColorSurface")
                                .setColorLayer()
                                .setCallsite("ScreenRotationAnimation")
                                .build();
                SurfaceControl build2 =
                        displayContent
                                .makeOverlay()
                                .setName("RotationLayer")
                                .setOpaque(true)
                                .setSecure(z5)
                                .setCallsite("ScreenRotationAnimation")
                                .setBLASTLayer()
                                .build();
                this.mScreenshotLayer = build2;
                transaction.setMetadata(build2, 30, 1);
                InputMonitor.setTrustedOverlayInputInfo(
                        this.mScreenshotLayer, transaction, i7, "RotationLayer");
                this.mEnterBlackFrameLayer =
                        displayContent
                                .makeOverlay()
                                .setName("EnterBlackFrameLayer")
                                .setContainerLayer()
                                .setCallsite("ScreenRotationAnimation")
                                .build();
                HardwareBuffer hardwareBuffer = captureLayers.getHardwareBuffer();
                Trace.traceBegin(32L, "ScreenRotationAnimation#getMedianBorderLuma");
                float borderLuma =
                        TransitionAnimation.getBorderLuma(
                                hardwareBuffer, captureLayers.getColorSpace());
                this.mStartLuma = borderLuma;
                Trace.traceEnd(32L);
                transaction.setLayer(this.mScreenshotLayer, 2010000);
                transaction.reparent(this.mBackColorSurface, displayContent.getSurfaceControl());
                transaction.setDimmingEnabled(
                        this.mScreenshotLayer, !captureLayers.containsHdrLayers());
                transaction.setLayer(this.mBackColorSurface, -1);
                transaction.setColor(
                        this.mBackColorSurface, new float[] {borderLuma, borderLuma, borderLuma});
                transaction.setAlpha(this.mBackColorSurface, 1.0f);
                transaction.setBuffer(this.mScreenshotLayer, hardwareBuffer);
                transaction.setColorSpace(this.mScreenshotLayer, captureLayers.getColorSpace());
                transaction.show(this.mScreenshotLayer);
                transaction.show(this.mBackColorSurface);
                hardwareBuffer.close();
                SurfaceControl[] surfaceControlArr = this.mRoundedCornerOverlay;
                if (surfaceControlArr != null) {
                    for (SurfaceControl surfaceControl : surfaceControlArr) {
                        if (surfaceControl.isValid()) {
                            transaction.hide(surfaceControl);
                        }
                    }
                }
            }
        } catch (Surface.OutOfResourcesException e) {
            Slog.w("WindowManager", "Unable to allocate freeze surface", e);
        }
        if (this.mScreenshotLayer != null && z2) {
            displayContent
                    .getPendingTransaction()
                    .setGeometry(
                            this.mScreenshotLayer,
                            new Rect(0, 0, this.mOriginalWidth, this.mOriginalHeight),
                            new Rect(0, 0, i5, i6),
                            0);
        }
        if (ProtoLogImpl_54989576.Cache.WM_SHOW_SURFACE_ALLOC_enabled[2]) {
            ProtoLogImpl_54989576.i(
                    ProtoLogGroup.WM_SHOW_SURFACE_ALLOC,
                    8010999385228654193L,
                    0,
                    null,
                    String.valueOf(this.mScreenshotLayer));
        }
        if (i == i2) {
            setRotation(transaction, i2);
        } else {
            this.mCurRotation = i2;
            this.mSnapshotInitialMatrix.reset();
            setRotationTransform(transaction, this.mSnapshotInitialMatrix);
        }
        transaction.apply();
    }

    public final void kill() {
        SurfaceRotationAnimationController surfaceRotationAnimationController =
                this.mSurfaceRotationAnimationController;
        if (surfaceRotationAnimationController != null) {
            SurfaceAnimator surfaceAnimator =
                    surfaceRotationAnimationController.mEnterBlackFrameAnimator;
            if (surfaceAnimator != null) {
                surfaceAnimator.cancelAnimation();
            }
            SurfaceAnimator surfaceAnimator2 =
                    surfaceRotationAnimationController.mScreenshotRotationAnimator;
            if (surfaceAnimator2 != null) {
                surfaceAnimator2.cancelAnimation();
            }
            SurfaceAnimator surfaceAnimator3 =
                    surfaceRotationAnimationController.mRotateScreenAnimator;
            if (surfaceAnimator3 != null) {
                surfaceAnimator3.cancelAnimation();
            }
            SurfaceAnimator surfaceAnimator4 = surfaceRotationAnimationController.mDisplayAnimator;
            if (surfaceAnimator4 != null) {
                surfaceAnimator4.cancelAnimation();
            }
            ScreenRotationAnimation screenRotationAnimation = ScreenRotationAnimation.this;
            SurfaceControl surfaceControl = screenRotationAnimation.mBackColorSurface;
            if (surfaceControl != null) {
                screenRotationAnimation.mService.mSurfaceAnimationRunner.onAnimationCancelled(
                        surfaceControl);
            }
            this.mSurfaceRotationAnimationController = null;
        }
        SurfaceControl surfaceControl2 = this.mScreenshotLayer;
        if (surfaceControl2 != null) {
            if (ProtoLogImpl_54989576.Cache.WM_SHOW_SURFACE_ALLOC_enabled[2]) {
                ProtoLogImpl_54989576.i(
                        ProtoLogGroup.WM_SHOW_SURFACE_ALLOC,
                        -5825336546511998057L,
                        0,
                        null,
                        String.valueOf(surfaceControl2));
            }
            SurfaceControl.Transaction transaction =
                    (SurfaceControl.Transaction) this.mService.mTransactionFactory.get();
            if (this.mScreenshotLayer.isValid()) {
                transaction.remove(this.mScreenshotLayer);
            }
            this.mScreenshotLayer = null;
            SurfaceControl surfaceControl3 = this.mEnterBlackFrameLayer;
            if (surfaceControl3 != null) {
                if (surfaceControl3.isValid()) {
                    transaction.remove(this.mEnterBlackFrameLayer);
                }
                this.mEnterBlackFrameLayer = null;
            }
            SurfaceControl surfaceControl4 = this.mBackColorSurface;
            if (surfaceControl4 != null) {
                if (surfaceControl4.isValid()) {
                    transaction.remove(this.mBackColorSurface);
                }
                this.mBackColorSurface = null;
            }
            if (this.mRoundedCornerOverlay != null) {
                ScreenRotationAnimation screenRotationAnimation2 =
                        this.mDisplayContent.mScreenRotationAnimation;
                if (screenRotationAnimation2 == null || screenRotationAnimation2 == this) {
                    setSkipScreenshotForRoundedCornerOverlays(transaction, true);
                    for (SurfaceControl surfaceControl5 : this.mRoundedCornerOverlay) {
                        if (surfaceControl5.isValid()) {
                            transaction.show(surfaceControl5);
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

    public final void setRotation(SurfaceControl.Transaction transaction, int i) {
        this.mCurRotation = i;
        int deltaRotation = RotationUtils.deltaRotation(i, this.mOriginalRotation);
        Matrix matrix = this.mSnapshotInitialMatrix;
        if (deltaRotation != 0) {
            int i2 = this.mOriginalHeight;
            if (deltaRotation != 1) {
                int i3 = this.mOriginalWidth;
                if (deltaRotation == 2) {
                    matrix.setRotate(180.0f);
                    matrix.postTranslate(i3, i2);
                } else if (deltaRotation == 3) {
                    matrix.setRotate(270.0f);
                    matrix.postTranslate(FullScreenMagnificationGestureHandler.MAX_SCALE, i3);
                }
            } else {
                matrix.setRotate(90.0f);
                matrix.postTranslate(i2, FullScreenMagnificationGestureHandler.MAX_SCALE);
            }
        } else {
            matrix.reset();
        }
        setRotationTransform(transaction, this.mSnapshotInitialMatrix);
    }

    public final void setRotationTransform(SurfaceControl.Transaction transaction, Matrix matrix) {
        if (this.mScreenshotLayer == null) {
            return;
        }
        float[] fArr = this.mTmpFloats;
        matrix.getValues(fArr);
        transaction.setPosition(this.mScreenshotLayer, fArr[2], fArr[5]);
        transaction.setMatrix(this.mScreenshotLayer, fArr[0], fArr[3], fArr[1], fArr[4]);
        transaction.setAlpha(this.mScreenshotLayer, 1.0f);
        transaction.show(this.mScreenshotLayer);
    }

    public final void setSkipScreenshotForRoundedCornerOverlays(
            final SurfaceControl.Transaction transaction, final boolean z) {
        this.mDisplayContent.forAllWindows(
                new Consumer() { // from class:
                                 // com.android.server.wm.ScreenRotationAnimation$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SurfaceControl.Transaction transaction2 = transaction;
                        boolean z2 = z;
                        WindowState windowState = (WindowState) obj;
                        if (windowState.mToken.mRoundedCornerOverlay
                                && windowState.isVisible()
                                && windowState.mWinAnimator.hasSurface()) {
                            transaction2.setSkipScreenshot(
                                    windowState.mWinAnimator.mSurfaceController.mSurfaceControl,
                                    z2);
                        }
                    }
                },
                false);
        if (z) {
            return;
        }
        transaction.apply(true);
    }
}
