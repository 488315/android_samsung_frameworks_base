package com.android.wm.shell.pip.phone;

import android.content.Context;
import android.graphics.Rect;
import android.os.Debug;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.view.SurfaceControl;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.wm.shell.animation.FloatProperties;
import com.android.wm.shell.animation.FloatProperties$Companion$RECT_WIDTH$1;
import com.android.wm.shell.animation.FloatProperties$Companion$RECT_X$1;
import com.android.wm.shell.animation.PhysicsAnimator;
import com.android.wm.shell.common.FloatingContentCoordinator;
import com.android.wm.shell.pip.PipAppOpsListener;
import com.android.wm.shell.pip.PipBoundsState;
import com.android.wm.shell.pip.PipSnapAlgorithm;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.pip.PipTransitionState;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipMotionHelper implements PipAppOpsListener.Callback, FloatingContentCoordinator.FloatingContent {
    public final PhysicsAnimator.SpringConfig mCatchUpSpringConfig;
    public final PhysicsAnimator.SpringConfig mConflictResolutionSpringConfig;
    public final Context mContext;
    public final PipEdgePanelSupport mEdgePanelSupport;
    public PhysicsAnimator.FlingConfig mFlingConfigX;
    public PhysicsAnimator.FlingConfig mFlingConfigY;
    public final FloatingContentCoordinator mFloatingContentCoordinator;
    public final PhonePipMenuController mMenuController;
    public final PipBoundsState mPipBoundsState;
    public final PipSizeSpecHandler mPipSizeSpecHandler;
    public final PipTaskOrganizer mPipTaskOrganizer;
    public final C40641 mPipTransitionCallback;
    public Runnable mPostPipTransitionCallback;
    public final PipMotionHelper$$ExternalSyntheticLambda1 mResizePipUpdateListener;
    public final PipSnapAlgorithm mSnapAlgorithm;
    public boolean mSpringingToTouch;
    public PhysicsAnimator.FlingConfig mStashConfigX;
    public PhysicsAnimator mTemporaryBoundsPhysicsAnimator;
    public final PipMotionHelper$$ExternalSyntheticLambda0 mUpdateBoundsCallback;
    public final Rect mTmpRect = new Rect();
    public final Rect mFloatingAllowedArea = new Rect();
    public final PhysicsAnimator.SpringConfig mSpringConfig = new PhysicsAnimator.SpringConfig(700.0f, 1.0f);

    /* renamed from: $r8$lambda$VxtrbYZwxPqmSIi-Ho3ifEKvrlc, reason: not valid java name */
    public static void m2754$r8$lambda$VxtrbYZwxPqmSIiHo3ifEKvrlc(PipMotionHelper pipMotionHelper) {
        pipMotionHelper.getClass();
        boolean z = pipMotionHelper.mSpringingToTouch;
        PipBoundsState pipBoundsState = pipMotionHelper.mPipBoundsState;
        if (!z) {
            boolean isEmpty = pipBoundsState.mMotionBoundsState.mBoundsInMotion.isEmpty();
            PipBoundsState.MotionBoundsState motionBoundsState = pipBoundsState.mMotionBoundsState;
            PipTaskOrganizer pipTaskOrganizer = pipMotionHelper.mPipTaskOrganizer;
            if (isEmpty) {
                Log.w("PipTaskOrganizer", "onBoundsPhysicsAnimationEnd PIP empty, setDefaultBounds");
                motionBoundsState.mBoundsInMotion.set(pipTaskOrganizer.mPipBoundsAlgorithm.getDefaultBounds(null, -1.0f));
            }
            pipBoundsState.setBounds(motionBoundsState.mBoundsInMotion);
            motionBoundsState.mBoundsInMotion.setEmpty();
            pipTaskOrganizer.scheduleFinishResizePip(pipMotionHelper.getBounds(), null);
        }
        pipBoundsState.mMotionBoundsState.mAnimatingToBounds.setEmpty();
        pipMotionHelper.mSpringingToTouch = false;
    }

    static {
        SystemProperties.getBoolean("persist.wm.debug.fling_to_dismiss_pip", false);
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.wm.shell.pip.phone.PipMotionHelper$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.wm.shell.pip.phone.PipMotionHelper$$ExternalSyntheticLambda1] */
    public PipMotionHelper(Context context, PipBoundsState pipBoundsState, PipTaskOrganizer pipTaskOrganizer, PhonePipMenuController phonePipMenuController, PipSnapAlgorithm pipSnapAlgorithm, PipTransitionController pipTransitionController, FloatingContentCoordinator floatingContentCoordinator, PipSizeSpecHandler pipSizeSpecHandler) {
        new PhysicsAnimator.SpringConfig(1500.0f, 1.0f);
        this.mCatchUpSpringConfig = new PhysicsAnimator.SpringConfig(5000.0f, 1.0f);
        this.mConflictResolutionSpringConfig = new PhysicsAnimator.SpringConfig(200.0f, 1.0f);
        this.mUpdateBoundsCallback = new PipMotionHelper$$ExternalSyntheticLambda0(this, 1);
        this.mSpringingToTouch = false;
        ?? r0 = new PipTransitionController.PipTransitionCallback() { // from class: com.android.wm.shell.pip.phone.PipMotionHelper.1
            @Override // com.android.wm.shell.pip.PipTransitionController.PipTransitionCallback
            public final void onPipTransitionFinished(int i) {
                PipMotionHelper pipMotionHelper = PipMotionHelper.this;
                Runnable runnable = pipMotionHelper.mPostPipTransitionCallback;
                if (runnable != null) {
                    runnable.run();
                    pipMotionHelper.mPostPipTransitionCallback = null;
                }
            }

            @Override // com.android.wm.shell.pip.PipTransitionController.PipTransitionCallback
            public final void onPipTransitionCanceled(int i) {
            }

            @Override // com.android.wm.shell.pip.PipTransitionController.PipTransitionCallback
            public final void onPipTransitionStarted(int i, Rect rect) {
            }
        };
        this.mPipTransitionCallback = r0;
        this.mContext = context;
        this.mPipTaskOrganizer = pipTaskOrganizer;
        this.mPipBoundsState = pipBoundsState;
        this.mMenuController = phonePipMenuController;
        this.mSnapAlgorithm = pipSnapAlgorithm;
        this.mFloatingContentCoordinator = floatingContentCoordinator;
        ((ArrayList) pipTransitionController.mPipTransitionCallbacks).add(r0);
        this.mResizePipUpdateListener = new PhysicsAnimator.UpdateListener() { // from class: com.android.wm.shell.pip.phone.PipMotionHelper$$ExternalSyntheticLambda1
            @Override // com.android.wm.shell.animation.PhysicsAnimator.UpdateListener
            public final void onAnimationUpdateForProperty(Object obj) {
                Rect rect = (Rect) obj;
                PipMotionHelper pipMotionHelper = PipMotionHelper.this;
                PipBoundsState pipBoundsState2 = pipMotionHelper.mPipBoundsState;
                if (!pipBoundsState2.mMotionBoundsState.mBoundsInMotion.isEmpty()) {
                    pipMotionHelper.setStashDimOverlayAlpha(rect);
                    pipMotionHelper.mPipTaskOrganizer.scheduleUserResizePip(pipMotionHelper.getBounds(), pipBoundsState2.mMotionBoundsState.mBoundsInMotion, 0.0f, null);
                }
            }
        };
        this.mEdgePanelSupport = new PipEdgePanelSupport(context);
        this.mPipSizeSpecHandler = pipSizeSpecHandler;
    }

    public final void adjustPipBoundsForEdge(Rect rect) {
        int upperMostPosition;
        PipEdgePanelSupport pipEdgePanelSupport = this.mEdgePanelSupport;
        if (Settings.Secure.getIntForUser(pipEdgePanelSupport.mContext.getContentResolver(), "edge_enable", 1, -2) == 1) {
            PipBoundsState pipBoundsState = this.mPipBoundsState;
            int height = pipBoundsState.getDisplayBounds().height();
            int width = pipBoundsState.getDisplayBounds().width();
            Context context = pipEdgePanelSupport.mContext;
            int i = Settings.System.getInt(context.getContentResolver(), "active_edge_area", 1);
            int edgeHandlePixelSize = pipEdgePanelSupport.getEdgeHandlePixelSize();
            if (this.mContext.getResources().getConfiguration().orientation == 2) {
                MultiWindowUtils.isInSubDisplay(context);
                String str = SemSystemProperties.get("ro.build.characteristics");
                if (!(str != null && str.contains("tablet"))) {
                    upperMostPosition = 0;
                    int i2 = edgeHandlePixelSize + upperMostPosition;
                    Rect rect2 = pipBoundsState.mMovementBounds;
                    if ((i == 1 || rect.left <= rect2.centerX()) && (i != 0 || rect.left > rect2.centerX())) {
                        return;
                    }
                    if ((i == 1 ? new Rect(rect.left, rect.top, width, rect.bottom) : new Rect(0, rect.top, rect.right, rect.bottom)).intersect(i == 1 ? new Rect(width - 1, upperMostPosition, width, i2) : new Rect(0, upperMostPosition, 1, i2))) {
                        int i3 = pipBoundsState.mPipEdgeMargin;
                        if (((i2 / 2) + upperMostPosition >= (rect.bottom / 2) + rect.top || (rect.height() + i2) + i3 >= height) && (upperMostPosition - rect.height()) - i3 >= pipBoundsState.getDisplayLayout().stableInsets(false).top) {
                            int i4 = upperMostPosition - i3;
                            rect.set(rect.left, i4 - rect.height(), rect.right, i4);
                            return;
                        } else {
                            int i5 = i2 + i3;
                            rect.set(rect.left, i5, rect.right, rect.height() + i5);
                            return;
                        }
                    }
                    return;
                }
            }
            int percentToPixel = pipEdgePanelSupport.percentToPixel(Settings.System.getFloat(context.getContentResolver(), "edge_handler_position_percent", 0.0f));
            int edgeHandlePixelSize2 = (int) ((pipEdgePanelSupport.getEdgeHandlePixelSize() / 2.0f) + 0.5f);
            upperMostPosition = (percentToPixel - edgeHandlePixelSize2) + pipEdgePanelSupport.getUpperMostPosition();
            StringBuffer stringBuffer = new StringBuffer("getEdgeHandleMarginOnTop retY=");
            stringBuffer.append(upperMostPosition);
            stringBuffer.append(" halfHandleSize=");
            stringBuffer.append(edgeHandlePixelSize2);
            Log.d("EdgePanelSupport", stringBuffer.toString());
            int i22 = edgeHandlePixelSize + upperMostPosition;
            Rect rect22 = pipBoundsState.mMovementBounds;
            if (i == 1) {
            }
        }
    }

    public final void animateToOffset(int i, Rect rect) {
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1795696385, 0, "%s: animateToOffset: originalBounds=%s offset=%s callers=\n%s", "PipMotionHelper", String.valueOf(rect), String.valueOf(i), String.valueOf(Debug.getCallers(5, "    ")));
        }
        cancelPhysicsAnimation();
        PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
        PipTransitionState pipTransitionState = pipTaskOrganizer.mPipTransitionState;
        int i2 = pipTransitionState.mState;
        if ((i2 < 3 || i2 == 5) || pipTransitionState.mInSwipePipToHomeTransition) {
            return;
        }
        if (pipTaskOrganizer.mWaitForFixedRotation) {
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1965704743, 0, "%s: skip scheduleOffsetPip, entering pip deferred", "PipTaskOrganizer");
                return;
            }
            return;
        }
        if (i2 == 3) {
            Log.d("PipTaskOrganizer", "skip offset pip mState=ENTERING_PIP");
            return;
        }
        if (pipTaskOrganizer.mTaskInfo != null) {
            Rect rect2 = new Rect(rect);
            rect2.offset(0, i);
            pipTaskOrganizer.animateResizePip(rect, rect2, null, 1, 300, 0.0f);
        } else if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m233w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 677124681, 0, "%s: mTaskInfo is not set", "PipTaskOrganizer");
        }
        Rect rect3 = new Rect(rect);
        rect3.offset(0, i);
        PipMotionHelper$$ExternalSyntheticLambda0 pipMotionHelper$$ExternalSyntheticLambda0 = this.mUpdateBoundsCallback;
        if (pipMotionHelper$$ExternalSyntheticLambda0 != null) {
            pipMotionHelper$$ExternalSyntheticLambda0.accept(rect3);
        }
    }

    public final void animateToUnexpandedState(Rect rect, float f, Rect rect2, Rect rect3, boolean z) {
        PipSnapAlgorithm pipSnapAlgorithm = this.mSnapAlgorithm;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        if (f < 0.0f) {
            f = pipSnapAlgorithm.getSnapFraction(pipBoundsState.mStashedState, new Rect(getBounds()), rect3);
        }
        int i = pipBoundsState.mStashedState;
        int i2 = pipBoundsState.mStashOffset;
        Rect displayBounds = pipBoundsState.getDisplayBounds();
        Rect stashInsets = pipBoundsState.getStashInsets();
        pipSnapAlgorithm.getClass();
        PipSnapAlgorithm.applySnapFraction(rect, rect2, f, i, i2, displayBounds, stashInsets);
        if (z) {
            movePip(rect, false);
        } else {
            resizeAndAnimatePipUnchecked(rect);
        }
    }

    public final void cancelPhysicsAnimation() {
        this.mTemporaryBoundsPhysicsAnimator.cancel();
        this.mPipBoundsState.mMotionBoundsState.mAnimatingToBounds.setEmpty();
        this.mSpringingToTouch = false;
    }

    @Override // com.android.wm.shell.pip.PipAppOpsListener.Callback
    public final void dismissPip() {
        Log.d("PipTaskOrganizer", "[PipMotionHelper] removePip: callers=\n" + Debug.getCallers(5, "    "));
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 674201280, 0, "%s: removePip: callers=\n%s", "PipMotionHelper", String.valueOf(Debug.getCallers(5, "    ")));
        }
        cancelPhysicsAnimation();
        this.mMenuController.hideMenu(2);
        this.mPipTaskOrganizer.removePip();
    }

    public final void expandLeavePip(boolean z, boolean z2) {
        StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("[PipMotionHelper] exitPip: skipAnimation=", z, " callers=\n");
        m49m.append(Debug.getCallers(5, "    "));
        Log.d("PipTaskOrganizer", m49m.toString());
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1577222906, 0, "%s: exitPip: skipAnimation=%s callers=\n%s", "PipMotionHelper", String.valueOf(z), String.valueOf(Debug.getCallers(5, "    ")));
        }
        cancelPhysicsAnimation();
        this.mMenuController.hideMenu(0);
        this.mPipTaskOrganizer.exitPip(z ? 0 : 300, z2);
    }

    @Override // com.android.wm.shell.common.FloatingContentCoordinator.FloatingContent
    public final Rect getAllowedFloatingBoundsRegion() {
        return this.mFloatingAllowedArea;
    }

    public final Rect getBounds() {
        return this.mPipBoundsState.getBounds();
    }

    @Override // com.android.wm.shell.common.FloatingContentCoordinator.FloatingContent
    public final Rect getFloatingBoundsOnScreen() {
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        return !pipBoundsState.mMotionBoundsState.mAnimatingToBounds.isEmpty() ? pipBoundsState.mMotionBoundsState.mAnimatingToBounds : getBounds();
    }

    public final void movePip(Rect rect, boolean z) {
        if (!z) {
            this.mFloatingContentCoordinator.onContentMoved(this);
        }
        if (this.mSpringingToTouch) {
            PhysicsAnimator physicsAnimator = this.mTemporaryBoundsPhysicsAnimator;
            FloatProperties$Companion$RECT_WIDTH$1 floatProperties$Companion$RECT_WIDTH$1 = FloatProperties.RECT_WIDTH;
            float width = getBounds().width();
            PhysicsAnimator.SpringConfig springConfig = this.mCatchUpSpringConfig;
            physicsAnimator.spring(floatProperties$Companion$RECT_WIDTH$1, width, 0.0f, springConfig);
            physicsAnimator.spring(FloatProperties.RECT_HEIGHT, getBounds().height(), 0.0f, springConfig);
            physicsAnimator.spring(FloatProperties.RECT_X, rect.left, 0.0f, springConfig);
            physicsAnimator.spring(FloatProperties.RECT_Y, rect.top, 0.0f, springConfig);
            startBoundsAnimator(rect.left, rect.top, null);
            return;
        }
        cancelPhysicsAnimation();
        int i = 0;
        PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        if (z) {
            pipBoundsState.mMotionBoundsState.mBoundsInMotion.set(rect);
            pipTaskOrganizer.scheduleUserResizePip(getBounds(), rect, 0.0f, new PipMotionHelper$$ExternalSyntheticLambda0(this, i));
            setStashDimOverlayAlpha(rect);
            return;
        }
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -536438549, 0, "%s: resizePipUnchecked: toBounds=%s callers=\n%s", "PipMotionHelper", String.valueOf(rect), String.valueOf(Debug.getCallers(5, "    ")));
        }
        if (!rect.equals(getBounds())) {
            if (pipTaskOrganizer.mToken != null && pipTaskOrganizer.mLeash != null) {
                pipTaskOrganizer.mPipBoundsState.setBounds(rect);
                SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) pipTaskOrganizer.mSurfaceControlTransactionFactory).getTransaction();
                SurfaceControl surfaceControl = pipTaskOrganizer.mLeash;
                PipSurfaceTransactionHelper pipSurfaceTransactionHelper = pipTaskOrganizer.mSurfaceTransactionHelper;
                pipSurfaceTransactionHelper.crop(rect, transaction, surfaceControl);
                pipSurfaceTransactionHelper.round(pipTaskOrganizer.mLeash, pipTaskOrganizer.mPipTransitionState.isInPip(), transaction);
                if (pipTaskOrganizer.shouldSyncPipTransactionWithMenu()) {
                    pipTaskOrganizer.mPipMenuController.resizePipMenu(rect, transaction, pipTaskOrganizer.mLeash);
                } else {
                    transaction.apply();
                }
                PipMotionHelper$$ExternalSyntheticLambda0 pipMotionHelper$$ExternalSyntheticLambda0 = this.mUpdateBoundsCallback;
                if (pipMotionHelper$$ExternalSyntheticLambda0 != null) {
                    pipMotionHelper$$ExternalSyntheticLambda0.accept(rect);
                }
            } else if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m233w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1332460476, 0, "%s: Abort animation, invalid leash", "PipTaskOrganizer");
            }
        }
        pipBoundsState.setBounds(rect);
    }

    @Override // com.android.wm.shell.common.FloatingContentCoordinator.FloatingContent
    public final void moveToBounds(Rect rect) {
        if (!this.mTemporaryBoundsPhysicsAnimator.isRunning()) {
            this.mPipBoundsState.mMotionBoundsState.mBoundsInMotion.set(getBounds());
        }
        PhysicsAnimator physicsAnimator = this.mTemporaryBoundsPhysicsAnimator;
        FloatProperties$Companion$RECT_X$1 floatProperties$Companion$RECT_X$1 = FloatProperties.RECT_X;
        float f = rect.left;
        PhysicsAnimator.SpringConfig springConfig = this.mConflictResolutionSpringConfig;
        physicsAnimator.spring(floatProperties$Companion$RECT_X$1, f, 0.0f, springConfig);
        physicsAnimator.spring(FloatProperties.RECT_Y, rect.top, 0.0f, springConfig);
        startBoundsAnimator(rect.left, rect.top, null);
    }

    public final void movetoTarget(float f, float f2, PipTouchHandler$$ExternalSyntheticLambda1 pipTouchHandler$$ExternalSyntheticLambda1, boolean z) {
        float f3;
        float f4;
        this.mSpringingToTouch = false;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        if (z) {
            Rect rect = pipBoundsState.mMotionBoundsState.mBoundsInMotion;
            Rect rect2 = this.mTmpRect;
            rect2.set(rect);
            Rect insetBounds = this.mPipSizeSpecHandler.getInsetBounds();
            int i = insetBounds.top;
            int i2 = rect2.top;
            if (i > i2) {
                rect2.offsetTo(rect2.left, i);
            } else {
                int i3 = insetBounds.bottom;
                int i4 = rect2.bottom;
                if (i3 < i4) {
                    rect2.offsetTo(rect2.left, (i2 - i4) + i3);
                }
            }
            adjustPipBoundsForEdge(rect2);
            PhysicsAnimator.FlingConfig flingConfig = this.mFlingConfigY;
            float f5 = rect2.top;
            flingConfig.min = f5;
            flingConfig.max = f5;
            f3 = 0.0f;
        } else {
            f3 = f2;
        }
        Rect displayBounds = pipBoundsState.getDisplayBounds();
        Rect rect3 = pipBoundsState.mMotionBoundsState.mBoundsInMotion;
        if (f != 0.0f || rect3.left >= displayBounds.left || rect3.right >= displayBounds.right) {
            f4 = f;
        } else {
            Log.w("PipTaskOrganizer", "movetoTarget: make velocity as negative");
            f4 = -1.0f;
        }
        PhysicsAnimator physicsAnimator = this.mTemporaryBoundsPhysicsAnimator;
        FloatProperties$Companion$RECT_WIDTH$1 floatProperties$Companion$RECT_WIDTH$1 = FloatProperties.RECT_WIDTH;
        float width = getBounds().width();
        PhysicsAnimator.SpringConfig springConfig = this.mSpringConfig;
        physicsAnimator.spring(floatProperties$Companion$RECT_WIDTH$1, width, 0.0f, springConfig);
        physicsAnimator.spring(FloatProperties.RECT_HEIGHT, getBounds().height(), 0.0f, springConfig);
        physicsAnimator.flingThenSpring(FloatProperties.RECT_X, f4, z ? this.mStashConfigX : this.mFlingConfigX, this.mSpringConfig, true);
        physicsAnimator.flingThenSpring(FloatProperties.RECT_Y, f3, this.mFlingConfigY, springConfig, false);
        pipBoundsState.getDisplayLayout().stableInsets(false);
        Rect rect4 = pipBoundsState.mMovementBounds;
        float width2 = z ? (pipBoundsState.mStashOffset - pipBoundsState.getBounds().width()) + pipBoundsState.getStashInsets().left : rect4.left;
        float f6 = z ? (pipBoundsState.getDisplayBounds().right - pipBoundsState.mStashOffset) - pipBoundsState.getStashInsets().right : rect4.right;
        if (f4 >= 0.0f) {
            width2 = f6;
        }
        startBoundsAnimator(width2, PhysicsAnimator.estimateFlingEndValue(r12.mBoundsInMotion.top, f3, this.mFlingConfigY), pipTouchHandler$$ExternalSyntheticLambda1);
    }

    public final void resizeAndAnimatePipUnchecked(Rect rect) {
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1986961096, 0, "%s: resizeAndAnimatePipUnchecked: toBounds=%s duration=%s callers=\n%s", "PipMotionHelper", String.valueOf(rect), String.valueOf(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend), String.valueOf(Debug.getCallers(5, "    ")));
        }
        this.mPipTaskOrganizer.scheduleAnimateResizePip(rect, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, 8);
        this.mPipBoundsState.mMotionBoundsState.mAnimatingToBounds.set(rect);
        this.mFloatingContentCoordinator.onContentMoved(this);
    }

    public final void setStashDimOverlayAlpha(Rect rect) {
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        boolean contains = pipBoundsState.getDisplayBounds().contains(rect);
        PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
        if (contains) {
            pipTaskOrganizer.clearStashDimOverlay();
            return;
        }
        int width = (rect.width() / 2) - pipBoundsState.mStashOffset;
        if (rect.right - pipBoundsState.getDisplayBounds().right >= rect.width() / 2) {
            pipTaskOrganizer.setStashDimOverlayAlpha(((rect.centerX() - pipBoundsState.getDisplayBounds().right) * 0.65f) / width);
        } else if (pipBoundsState.getDisplayBounds().left - rect.left >= rect.width() / 2) {
            pipTaskOrganizer.setStashDimOverlayAlpha(((pipBoundsState.getDisplayBounds().left - rect.centerX()) * 0.65f) / width);
        }
    }

    public final void startBoundsAnimator(float f, float f2, PipTouchHandler$$ExternalSyntheticLambda1 pipTouchHandler$$ExternalSyntheticLambda1) {
        if (!this.mSpringingToTouch) {
            cancelPhysicsAnimation();
        }
        int i = (int) f;
        int i2 = (int) f2;
        this.mPipBoundsState.mMotionBoundsState.mAnimatingToBounds.set(new Rect(i, i2, getBounds().width() + i, getBounds().height() + i2));
        this.mFloatingContentCoordinator.onContentMoved(this);
        if (!this.mTemporaryBoundsPhysicsAnimator.isRunning()) {
            PipMotionHelper$$ExternalSyntheticLambda1 pipMotionHelper$$ExternalSyntheticLambda1 = this.mResizePipUpdateListener;
            if (pipTouchHandler$$ExternalSyntheticLambda1 != null) {
                PhysicsAnimator physicsAnimator = this.mTemporaryBoundsPhysicsAnimator;
                physicsAnimator.updateListeners.add(pipMotionHelper$$ExternalSyntheticLambda1);
                final int i3 = 0;
                physicsAnimator.withEndActions(new Runnable(this) { // from class: com.android.wm.shell.pip.phone.PipMotionHelper$$ExternalSyntheticLambda2
                    public final /* synthetic */ PipMotionHelper f$0;

                    {
                        this.f$0 = this;
                    }

                    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i3) {
                        }
                        PipMotionHelper.m2754$r8$lambda$VxtrbYZwxPqmSIiHo3ifEKvrlc(this.f$0);
                    }
                }, pipTouchHandler$$ExternalSyntheticLambda1);
            } else {
                PhysicsAnimator physicsAnimator2 = this.mTemporaryBoundsPhysicsAnimator;
                physicsAnimator2.updateListeners.add(pipMotionHelper$$ExternalSyntheticLambda1);
                final int i4 = 1;
                physicsAnimator2.withEndActions(new Runnable(this) { // from class: com.android.wm.shell.pip.phone.PipMotionHelper$$ExternalSyntheticLambda2
                    public final /* synthetic */ PipMotionHelper f$0;

                    {
                        this.f$0 = this;
                    }

                    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i4) {
                        }
                        PipMotionHelper.m2754$r8$lambda$VxtrbYZwxPqmSIiHo3ifEKvrlc(this.f$0);
                    }
                });
            }
        }
        this.mTemporaryBoundsPhysicsAnimator.start();
    }

    public final void synchronizePinnedStackBounds() {
        cancelPhysicsAnimation();
        this.mPipBoundsState.mMotionBoundsState.mBoundsInMotion.setEmpty();
        if (this.mPipTaskOrganizer.isInPip()) {
            this.mFloatingContentCoordinator.onContentMoved(this);
        }
    }
}
