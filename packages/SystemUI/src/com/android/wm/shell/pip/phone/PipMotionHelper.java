package com.android.wm.shell.pip.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Debug;
import android.os.SemSystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.view.SurfaceControl;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.animation.FloatProperties;
import com.android.wm.shell.animation.FloatProperties$Companion$RECT_WIDTH$1;
import com.android.wm.shell.animation.FloatProperties$Companion$RECT_X$1;
import com.android.wm.shell.common.FloatingContentCoordinator;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PhoneSizeSpecSource;
import com.android.wm.shell.common.pip.PipAppOpsListener;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipMenuController;
import com.android.wm.shell.common.pip.PipPerfHintController;
import com.android.wm.shell.common.pip.PipSnapAlgorithm;
import com.android.wm.shell.common.pip.SizeSpecSource;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.pip.PipTransitionState;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import java.util.HashMap;
import java.util.Optional;

/* loaded from: classes3.dex */
public class PipMotionHelper implements PipAppOpsListener.Callback, FloatingContentCoordinator.FloatingContent {
    public final PhysicsAnimator.SpringConfig mCatchUpSpringConfig;
    public final PhysicsAnimator.SpringConfig mConflictResolutionSpringConfig;
    public final Context mContext;
    public final PipEdgePanelSupport mEdgePanelSupport;
    public PhysicsAnimator.FlingConfig mFlingConfigX;
    public PhysicsAnimator.FlingConfig mFlingConfigY;
    public final FloatingContentCoordinator mFloatingContentCoordinator;
    public final PhonePipMenuController mMenuController;
    public final PipBoundsState mPipBoundsState;
    public PipPerfHintController.PipHighPerfSession mPipHighPerfSession;
    public final PipPerfHintController mPipPerfHintController;
    public final PipTaskOrganizer mPipTaskOrganizer;
    public final AnonymousClass1 mPipTransitionCallback;
    public PipMenuView$$ExternalSyntheticLambda0 mPostPipTransitionCallback;
    public final PipMotionHelper$$ExternalSyntheticLambda4 mResizePipUpdateListener;
    public final PhoneSizeSpecSource mSizeSpecSource;
    public final PipSnapAlgorithm mSnapAlgorithm;
    public PhysicsAnimator.FlingConfig mStashConfigX;
    public PhysicsAnimator mTemporaryBoundsPhysicsAnimator;
    public final PipMotionHelper$$ExternalSyntheticLambda0 mUpdateBoundsCallback;
    public final Rect mTmpRect = new Rect();
    public final Rect mFloatingAllowedArea = new Rect();
    public final PhysicsAnimator.SpringConfig mSpringConfig = new PhysicsAnimator.SpringConfig(700.0f, 1.0f);

    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.wm.shell.pip.phone.PipMotionHelper$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.wm.shell.pip.phone.PipMotionHelper$$ExternalSyntheticLambda4] */
    public PipMotionHelper(Context context, ShellExecutor shellExecutor, PipBoundsState pipBoundsState, PipTaskOrganizer pipTaskOrganizer, PhonePipMenuController phonePipMenuController, PipSnapAlgorithm pipSnapAlgorithm, PipTransitionController pipTransitionController, FloatingContentCoordinator floatingContentCoordinator, Optional<PipPerfHintController> optional, SizeSpecSource sizeSpecSource) {
        new PhysicsAnimator.SpringConfig(1500.0f, 1.0f);
        this.mCatchUpSpringConfig = new PhysicsAnimator.SpringConfig(5000.0f, 1.0f);
        this.mConflictResolutionSpringConfig = new PhysicsAnimator.SpringConfig(200.0f, 1.0f);
        this.mUpdateBoundsCallback = new PipMotionHelper$$ExternalSyntheticLambda0(this, 2);
        ?? r0 = new PipTransitionController.PipTransitionCallback() { // from class: com.android.wm.shell.pip.phone.PipMotionHelper.1
            @Override // com.android.wm.shell.pip.PipTransitionController.PipTransitionCallback
            public final void onPipTransitionFinished(int i) {
                PipMotionHelper pipMotionHelper = PipMotionHelper.this;
                PipMenuView$$ExternalSyntheticLambda0 pipMenuView$$ExternalSyntheticLambda0 = pipMotionHelper.mPostPipTransitionCallback;
                if (pipMenuView$$ExternalSyntheticLambda0 != null) {
                    pipMenuView$$ExternalSyntheticLambda0.run();
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
        this.mPipPerfHintController = optional.orElse(null);
        ((HashMap) pipTransitionController.mPipTransitionCallbacks).put(r0, shellExecutor);
        this.mResizePipUpdateListener = new PhysicsAnimator.UpdateListener() { // from class: com.android.wm.shell.pip.phone.PipMotionHelper$$ExternalSyntheticLambda4
            @Override // com.android.wm.shell.shared.animation.PhysicsAnimator.UpdateListener
            public final void onAnimationUpdateForProperty(Object obj) {
                Rect rect = (Rect) obj;
                PipMotionHelper pipMotionHelper = this.f$0;
                PipBoundsState pipBoundsState2 = pipMotionHelper.mPipBoundsState;
                if (pipBoundsState2.mMotionBoundsState.isInMotion()) {
                    pipMotionHelper.setStashDimOverlayAlpha(rect);
                    pipMotionHelper.mPipTaskOrganizer.scheduleUserResizePip(pipBoundsState2.getBounds(), pipBoundsState2.mMotionBoundsState.mBoundsInMotion, 0.0f, null);
                }
            }
        };
        this.mEdgePanelSupport = new PipEdgePanelSupport(context);
        this.mSizeSpecSource = (PhoneSizeSpecSource) sizeSpecSource;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0062  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void adjustPipBoundsForEdge(Rect rect) {
        int iPercentToPixel;
        PipEdgePanelSupport pipEdgePanelSupport = this.mEdgePanelSupport;
        if (Settings.Secure.getIntForUser(pipEdgePanelSupport.mContext.getContentResolver(), "edge_enable", 1, -2) == 1) {
            PipBoundsState pipBoundsState = this.mPipBoundsState;
            int iHeight = pipBoundsState.mPipDisplayLayoutState.getDisplayBounds().height();
            PipDisplayLayoutState pipDisplayLayoutState = pipBoundsState.mPipDisplayLayoutState;
            int iWidth = pipDisplayLayoutState.getDisplayBounds().width();
            int i = Settings.System.getInt(pipEdgePanelSupport.mContext.getContentResolver(), "active_edge_area", 1);
            int edgeHandlePixelSize = pipEdgePanelSupport.getEdgeHandlePixelSize();
            if (this.mContext.getResources().getConfiguration().orientation == 2) {
                MultiWindowUtils.isInSubDisplay(pipEdgePanelSupport.mContext);
                String str = SemSystemProperties.get("ro.build.characteristics");
                if (str == null || !str.contains("tablet")) {
                    iPercentToPixel = 0;
                } else {
                    int edgeHandlePixelSize2 = (int) ((pipEdgePanelSupport.getEdgeHandlePixelSize() / 2.0f) + 0.5f);
                    iPercentToPixel = (pipEdgePanelSupport.percentToPixel(Settings.System.getFloat(pipEdgePanelSupport.mContext.getContentResolver(), "edge_handler_position_percent", 0.0f)) - edgeHandlePixelSize2) + pipEdgePanelSupport.getUpperMostPosition();
                    StringBuffer stringBuffer = new StringBuffer("getEdgeHandleMarginOnTop retY=");
                    stringBuffer.append(iPercentToPixel);
                    stringBuffer.append(" halfHandleSize=");
                    stringBuffer.append(edgeHandlePixelSize2);
                    Log.d("EdgePanelSupport", stringBuffer.toString());
                }
            }
            int i2 = edgeHandlePixelSize + iPercentToPixel;
            if ((i != 1 || rect.left <= pipBoundsState.mMovementBounds.centerX()) && (i != 0 || rect.left > pipBoundsState.mMovementBounds.centerX())) {
                return;
            }
            if ((i == 1 ? new Rect(rect.left, rect.top, iWidth, rect.bottom) : new Rect(0, rect.top, rect.right, rect.bottom)).intersect(i == 1 ? new Rect(iWidth - 1, iPercentToPixel, iWidth, i2) : new Rect(0, iPercentToPixel, 1, i2))) {
                int i3 = pipBoundsState.mPipEdgeMargin;
                if (((i2 / 2) + iPercentToPixel >= (rect.bottom / 2) + rect.top || rect.height() + i2 + i3 >= iHeight) && (iPercentToPixel - rect.height()) - i3 >= pipDisplayLayoutState.getDisplayLayout().mStableInsets.top) {
                    int i4 = iPercentToPixel - i3;
                    rect.set(rect.left, i4 - rect.height(), rect.right, i4);
                } else {
                    int i5 = i2 + i3;
                    rect.set(rect.left, i5, rect.right, rect.height() + i5);
                }
            }
        }
    }

    public final void animateToUnexpandedState(Rect rect, float f, Rect rect2, Rect rect3, boolean z) {
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        if (f < 0.0f) {
            Rect rect4 = new Rect(pipBoundsState.getBounds());
            f = this.mSnapAlgorithm.getSnapFraction(pipBoundsState.mStashedState, rect4, rect3);
        }
        int i = pipBoundsState.mStashedState;
        int i2 = pipBoundsState.mStashOffset;
        Rect displayBounds = pipBoundsState.mPipDisplayLayoutState.getDisplayBounds();
        Rect stashInsets = pipBoundsState.getStashInsets();
        this.mSnapAlgorithm.getClass();
        PipSnapAlgorithm.applySnapFraction(rect, rect2, f, i, i2, displayBounds, stashInsets);
        if (z) {
            movePip(rect, false);
        } else {
            resizeAndAnimatePipUnchecked$1(rect);
        }
    }

    public final void cancelPhysicsAnimation$1() {
        this.mTemporaryBoundsPhysicsAnimator.cancel();
        this.mPipBoundsState.mMotionBoundsState.mAnimatingToBounds.setEmpty();
    }

    @Override // com.android.wm.shell.common.pip.PipAppOpsListener.Callback
    public final void dismissPip() {
        int i = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
        Log.d("PipTaskOrganizer", "[PipMotionHelper] removePip: callers=\n" + Debug.getCallers(5, "    "));
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -6558744023433135048L, 0, "PipMotionHelper", String.valueOf(Debug.getCallers(5, "    ")));
        }
        cancelPhysicsAnimation$1();
        this.mMenuController.hideMenu(2);
        this.mPipTaskOrganizer.removePip();
    }

    public final void expandLeavePip$1(boolean z, boolean z2) {
        int i = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
        StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("[PipMotionHelper] exitPip: skipAnimation=", " callers=\n", z);
        sbM.append(Debug.getCallers(5, "    "));
        Log.d("PipTaskOrganizer", sbM.toString());
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -3479890097261691899L, 0, "PipMotionHelper", String.valueOf(z), String.valueOf(Debug.getCallers(5, "    ")));
        }
        cancelPhysicsAnimation$1();
        this.mMenuController.hideMenu(0);
        this.mPipTaskOrganizer.exitPip(z ? 0 : 300, z2);
    }

    @Override // com.android.wm.shell.common.FloatingContentCoordinator.FloatingContent
    public final Rect getAllowedFloatingBoundsRegion() {
        return this.mFloatingAllowedArea;
    }

    @Override // com.android.wm.shell.common.FloatingContentCoordinator.FloatingContent
    public final Rect getFloatingBoundsOnScreen() {
        return this.mPipBoundsState.getBounds();
    }

    public final void movePip(Rect rect, boolean z) {
        if (!z) {
            this.mFloatingContentCoordinator.onContentMoved(this);
        }
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        cancelPhysicsAnimation$1();
        PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
        if (z) {
            pipBoundsState.mMotionBoundsState.setBoundsInMotion(rect);
            pipTaskOrganizer.scheduleUserResizePip(pipBoundsState.getBounds(), rect, 0.0f, new PipMotionHelper$$ExternalSyntheticLambda0(this, 1));
            setStashDimOverlayAlpha(rect);
            return;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -8148763854361119707L, 0, "PipMotionHelper", String.valueOf(rect), String.valueOf(Debug.getCallers(5, "    ")));
        }
        if (!rect.equals(pipBoundsState.getBounds())) {
            if (pipTaskOrganizer.mToken != null && pipTaskOrganizer.mLeash != null) {
                pipTaskOrganizer.mPipBoundsState.setBounds(rect);
                SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) pipTaskOrganizer.mSurfaceControlTransactionFactory).getTransaction();
                SurfaceControl surfaceControl = pipTaskOrganizer.mLeash;
                PipSurfaceTransactionHelper pipSurfaceTransactionHelper = pipTaskOrganizer.mSurfaceTransactionHelper;
                pipSurfaceTransactionHelper.cropAndPosition(rect, transaction, surfaceControl);
                pipSurfaceTransactionHelper.round(transaction, pipTaskOrganizer.mLeash, PipTransitionState.isInPip(pipTaskOrganizer.mPipTransitionState.mState));
                PipMenuController pipMenuController = pipTaskOrganizer.mPipMenuController;
                if (pipMenuController.isMenuVisible()) {
                    pipMenuController.resizePipMenu(rect, transaction, pipTaskOrganizer.mLeash);
                } else {
                    transaction.apply();
                }
                PipMotionHelper$$ExternalSyntheticLambda0 pipMotionHelper$$ExternalSyntheticLambda0 = this.mUpdateBoundsCallback;
                if (pipMotionHelper$$ExternalSyntheticLambda0 != null) {
                    pipMotionHelper$$ExternalSyntheticLambda0.accept(rect);
                }
            } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
                ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 5014614599801648599L, 0, "PipTaskOrganizer");
            }
        }
        pipBoundsState.setBounds(rect);
    }

    @Override // com.android.wm.shell.common.FloatingContentCoordinator.FloatingContent
    public final void moveToBounds(Rect rect) {
        if (!this.mTemporaryBoundsPhysicsAnimator.isRunning()) {
            PipBoundsState pipBoundsState = this.mPipBoundsState;
            pipBoundsState.mMotionBoundsState.setBoundsInMotion(pipBoundsState.getBounds());
        }
        PhysicsAnimator physicsAnimator = this.mTemporaryBoundsPhysicsAnimator;
        FloatProperties$Companion$RECT_X$1 floatProperties$Companion$RECT_X$1 = FloatProperties.RECT_X;
        float f = rect.left;
        PhysicsAnimator.SpringConfig springConfig = this.mConflictResolutionSpringConfig;
        physicsAnimator.spring(floatProperties$Companion$RECT_X$1, f, 0.0f, springConfig);
        physicsAnimator.spring(FloatProperties.RECT_Y, rect.top, 0.0f, springConfig);
        startBoundsAnimator$1(rect.left, rect.top, null);
    }

    public final void movetoTarget$1(float f, float f2, Runnable runnable, boolean z) {
        float f3;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        if (f == 0.0f) {
            f = pipBoundsState.mMotionBoundsState.mBoundsInMotion.centerX() < pipBoundsState.mPipDisplayLayoutState.getDisplayBounds().centerX() ? -0.001f : 0.001f;
        }
        if (z) {
            this.mTmpRect.set(pipBoundsState.mMotionBoundsState.mBoundsInMotion);
            Rect rect = this.mTmpRect;
            Rect insetBounds = this.mSizeSpecSource.pipDisplayLayoutState.getInsetBounds();
            int i = insetBounds.top;
            int i2 = rect.top;
            if (i > i2) {
                rect.offsetTo(rect.left, i);
            } else {
                int i3 = insetBounds.bottom;
                int i4 = rect.bottom;
                if (i3 < i4) {
                    rect.offsetTo(rect.left, (i2 - i4) + i3);
                }
            }
            adjustPipBoundsForEdge(this.mTmpRect);
            PhysicsAnimator.FlingConfig flingConfig = this.mFlingConfigY;
            float f4 = this.mTmpRect.top;
            flingConfig.min = f4;
            flingConfig.max = f4;
            f3 = 0.0f;
        } else {
            f3 = f2;
        }
        Rect displayBounds = pipBoundsState.mPipDisplayLayoutState.getDisplayBounds();
        Rect rect2 = pipBoundsState.mMotionBoundsState.mBoundsInMotion;
        if (f == 0.0f && rect2.left < displayBounds.left && rect2.right < displayBounds.right) {
            int i5 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
            Log.w("PipTaskOrganizer", "movetoTarget: make velocity as negative");
            f = -1.0f;
        }
        float f5 = f;
        PhysicsAnimator physicsAnimator = this.mTemporaryBoundsPhysicsAnimator;
        FloatProperties$Companion$RECT_WIDTH$1 floatProperties$Companion$RECT_WIDTH$1 = FloatProperties.RECT_WIDTH;
        float fWidth = pipBoundsState.getBounds().width();
        PhysicsAnimator.SpringConfig springConfig = this.mSpringConfig;
        physicsAnimator.spring(floatProperties$Companion$RECT_WIDTH$1, fWidth, 0.0f, springConfig);
        physicsAnimator.spring(FloatProperties.RECT_HEIGHT, pipBoundsState.getBounds().height(), 0.0f, springConfig);
        physicsAnimator.flingThenSpring(FloatProperties.RECT_X, f5, z ? this.mStashConfigX : this.mFlingConfigX, this.mSpringConfig, true);
        physicsAnimator.flingThenSpring(FloatProperties.RECT_Y, f3, this.mFlingConfigY, this.mSpringConfig, false);
        PipDisplayLayoutState pipDisplayLayoutState = pipBoundsState.mPipDisplayLayoutState;
        Rect rect3 = pipDisplayLayoutState.getDisplayLayout().mStableInsets;
        float fWidth2 = z ? (pipBoundsState.mStashOffset - pipBoundsState.getBounds().width()) + rect3.left : pipBoundsState.mMovementBounds.left;
        float f6 = z ? (pipDisplayLayoutState.getDisplayBounds().right - pipBoundsState.mStashOffset) - rect3.right : pipBoundsState.mMovementBounds.right;
        if (f5 >= 0.0f) {
            fWidth2 = f6;
        }
        startBoundsAnimator$1(fWidth2, PhysicsAnimator.estimateFlingEndValue(r3.mBoundsInMotion.top, f3, this.mFlingConfigY), runnable);
    }

    public final void resizeAndAnimatePipUnchecked$1(Rect rect) throws Resources.NotFoundException {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 7199987104712044997L, 0, "PipMotionHelper", String.valueOf(rect), String.valueOf(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend), String.valueOf(Debug.getCallers(5, "    ")));
        }
        this.mPipTaskOrganizer.scheduleAnimateResizePip(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, 8, rect);
        this.mPipBoundsState.mMotionBoundsState.mAnimatingToBounds.set(rect);
    }

    public final void setStashDimOverlayAlpha(Rect rect) {
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        boolean zContains = pipBoundsState.mPipDisplayLayoutState.getDisplayBounds().contains(rect);
        PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
        if (zContains) {
            pipTaskOrganizer.clearStashDimOverlay();
            return;
        }
        int iWidth = (rect.width() / 2) - pipBoundsState.mStashOffset;
        if (rect.right - pipBoundsState.mPipDisplayLayoutState.getDisplayBounds().right >= rect.width() / 2) {
            pipTaskOrganizer.setStashDimOverlayAlpha(((rect.centerX() - pipBoundsState.mPipDisplayLayoutState.getDisplayBounds().right) * 0.65f) / iWidth);
        } else if (pipBoundsState.mPipDisplayLayoutState.getDisplayBounds().left - rect.left >= rect.width() / 2) {
            pipTaskOrganizer.setStashDimOverlayAlpha(((pipBoundsState.mPipDisplayLayoutState.getDisplayBounds().left - rect.centerX()) * 0.65f) / iWidth);
        }
    }

    public final void startBoundsAnimator$1(float f, float f2, Runnable runnable) {
        cancelPhysicsAnimation$1();
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        int i = (int) f;
        int i2 = (int) f2;
        pipBoundsState.mMotionBoundsState.mAnimatingToBounds.set(new Rect(i, i2, pipBoundsState.getBounds().width() + i, pipBoundsState.getBounds().height() + i2));
        if (!this.mTemporaryBoundsPhysicsAnimator.isRunning()) {
            PipPerfHintController pipPerfHintController = this.mPipPerfHintController;
            if (pipPerfHintController != null) {
                this.mPipHighPerfSession = pipPerfHintController.startSession(new PipMotionHelper$$ExternalSyntheticLambda0(this, 0), "startBoundsAnimator");
            }
            PipMotionHelper$$ExternalSyntheticLambda4 pipMotionHelper$$ExternalSyntheticLambda4 = this.mResizePipUpdateListener;
            if (runnable != null) {
                PhysicsAnimator physicsAnimator = this.mTemporaryBoundsPhysicsAnimator;
                physicsAnimator.updateListeners.add(pipMotionHelper$$ExternalSyntheticLambda4);
                physicsAnimator.withEndActions(new Runnable() { // from class: com.android.wm.shell.pip.phone.PipMotionHelper$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        PipMotionHelper pipMotionHelper = this.f$0;
                        pipMotionHelper.getClass();
                        PipBoundsState pipBoundsState2 = pipMotionHelper.mPipBoundsState;
                        boolean zIsEmpty = pipBoundsState2.mMotionBoundsState.mBoundsInMotion.isEmpty();
                        PipBoundsState.MotionBoundsState motionBoundsState = pipBoundsState2.mMotionBoundsState;
                        PipTaskOrganizer pipTaskOrganizer = pipMotionHelper.mPipTaskOrganizer;
                        if (zIsEmpty) {
                            int i3 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
                            Log.w("PipTaskOrganizer", "onBoundsPhysicsAnimationEnd PIP empty, setDefaultBounds");
                            motionBoundsState.setBoundsInMotion(pipTaskOrganizer.mPipBoundsAlgorithm.getDefaultBounds());
                        }
                        pipBoundsState2.setBounds(motionBoundsState.mBoundsInMotion);
                        pipMotionHelper.mFloatingContentCoordinator.onContentMoved(pipMotionHelper);
                        motionBoundsState.mBoundsInMotion.setEmpty();
                        pipTaskOrganizer.scheduleFinishResizePip(pipBoundsState2.getBounds(), 0, null);
                        pipBoundsState2.mMotionBoundsState.mAnimatingToBounds.setEmpty();
                        PipPerfHintController.PipHighPerfSession pipHighPerfSession = pipMotionHelper.mPipHighPerfSession;
                        if (pipHighPerfSession != null) {
                            pipHighPerfSession.close();
                            pipMotionHelper.mPipHighPerfSession = null;
                        }
                    }
                }, runnable);
            } else {
                PhysicsAnimator physicsAnimator2 = this.mTemporaryBoundsPhysicsAnimator;
                physicsAnimator2.updateListeners.add(pipMotionHelper$$ExternalSyntheticLambda4);
                physicsAnimator2.withEndActions(new Runnable() { // from class: com.android.wm.shell.pip.phone.PipMotionHelper$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        PipMotionHelper pipMotionHelper = this.f$0;
                        pipMotionHelper.getClass();
                        PipBoundsState pipBoundsState2 = pipMotionHelper.mPipBoundsState;
                        boolean zIsEmpty = pipBoundsState2.mMotionBoundsState.mBoundsInMotion.isEmpty();
                        PipBoundsState.MotionBoundsState motionBoundsState = pipBoundsState2.mMotionBoundsState;
                        PipTaskOrganizer pipTaskOrganizer = pipMotionHelper.mPipTaskOrganizer;
                        if (zIsEmpty) {
                            int i3 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
                            Log.w("PipTaskOrganizer", "onBoundsPhysicsAnimationEnd PIP empty, setDefaultBounds");
                            motionBoundsState.setBoundsInMotion(pipTaskOrganizer.mPipBoundsAlgorithm.getDefaultBounds());
                        }
                        pipBoundsState2.setBounds(motionBoundsState.mBoundsInMotion);
                        pipMotionHelper.mFloatingContentCoordinator.onContentMoved(pipMotionHelper);
                        motionBoundsState.mBoundsInMotion.setEmpty();
                        pipTaskOrganizer.scheduleFinishResizePip(pipBoundsState2.getBounds(), 0, null);
                        pipBoundsState2.mMotionBoundsState.mAnimatingToBounds.setEmpty();
                        PipPerfHintController.PipHighPerfSession pipHighPerfSession = pipMotionHelper.mPipHighPerfSession;
                        if (pipHighPerfSession != null) {
                            pipHighPerfSession.close();
                            pipMotionHelper.mPipHighPerfSession = null;
                        }
                    }
                });
            }
        }
        this.mTemporaryBoundsPhysicsAnimator.start();
    }

    public final void synchronizePinnedStackBounds() {
        cancelPhysicsAnimation$1();
        this.mPipBoundsState.mMotionBoundsState.mBoundsInMotion.setEmpty();
        if (this.mPipTaskOrganizer.isInPip()) {
            this.mFloatingContentCoordinator.onContentMoved(this);
        }
    }
}
