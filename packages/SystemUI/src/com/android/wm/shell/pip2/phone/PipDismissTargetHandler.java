package com.android.wm.shell.pip2.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.SurfaceControl;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import com.android.systemui.R;
import com.android.wm.shell.animation.FloatProperties;
import com.android.wm.shell.animation.FloatProperties$Companion$RECT_Y$1;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import com.android.wm.shell.pip2.phone.PipMotionHelper;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.shared.bubbles.DismissCircleView;
import com.android.wm.shell.shared.bubbles.DismissView;
import com.android.wm.shell.shared.magnetictarget.MagnetizedObject;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipDismissTargetHandler implements ViewTreeObserver.OnPreDrawListener {
    public final Context mContext;
    public int mDismissAreaHeight;
    public final DisplayController mDisplayController;
    public boolean mEnableDismissDragToEdge;
    public MagnetizedObject.MagneticTarget mMagneticTarget;
    public PipMotionHelper.AnonymousClass1 mMagnetizedPip;
    public final ShellExecutor mMainExecutor;
    public final PipMotionHelper mMotionHelper;
    public final PipDisplayLayoutState mPipDisplayLayoutState;
    public final PipUiEventLogger mPipUiEventLogger;
    public int mTargetSize;
    public DismissCircleView mTargetView;
    public DismissView mTargetViewContainer;
    public SurfaceControl mTaskLeash;
    public WindowInsets mWindowInsets;
    public WindowManager mWindowManager;
    public int mWindowManagerDisplayId = -1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.pip2.phone.PipDismissTargetHandler$1, reason: invalid class name */
    public class AnonymousClass1 implements MagnetizedObject.MagnetListener {
        public AnonymousClass1() {
        }

        @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject.MagnetListener
        public final void onReleasedInTarget(MagnetizedObject magnetizedObject) {
            PipDismissTargetHandler pipDismissTargetHandler = PipDismissTargetHandler.this;
            if (pipDismissTargetHandler.mEnableDismissDragToEdge) {
                ((HandlerExecutor) pipDismissTargetHandler.mMainExecutor).executeDelayed(new Runnable() { // from class: com.android.wm.shell.pip2.phone.PipDismissTargetHandler$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PipDismissTargetHandler pipDismissTargetHandler2 = PipDismissTargetHandler.this;
                        PipMotionHelper pipMotionHelper = pipDismissTargetHandler2.mMotionHelper;
                        pipMotionHelper.mDismissalPending = true;
                        PhysicsAnimator physicsAnimator = pipMotionHelper.mTemporaryBoundsPhysicsAnimator;
                        FloatProperties$Companion$RECT_Y$1 floatProperties$Companion$RECT_Y$1 = FloatProperties.RECT_Y;
                        PipBoundsState pipBoundsState = pipMotionHelper.mPipBoundsState;
                        physicsAnimator.spring(floatProperties$Companion$RECT_Y$1, (pipBoundsState.getBounds().height() * 2) + pipBoundsState.mMovementBounds.bottom, 0.0f, pipMotionHelper.mSpringConfig);
                        physicsAnimator.withEndActions(new PipMotionHelper$$ExternalSyntheticLambda2(pipMotionHelper, 1));
                        pipMotionHelper.startBoundsAnimator(pipBoundsState.getBounds().left, pipBoundsState.getBounds().height() + pipBoundsState.getBounds().bottom, null);
                        pipMotionHelper.mDismissalPending = false;
                        if (pipDismissTargetHandler2.mEnableDismissDragToEdge) {
                            pipDismissTargetHandler2.mTargetViewContainer.hide();
                        }
                        pipDismissTargetHandler2.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_DRAG_TO_REMOVE);
                    }
                }, 0L);
            }
        }

        @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject.MagnetListener
        public final void onStuckToTarget(MagnetizedObject magnetizedObject) {
            PipDismissTargetHandler pipDismissTargetHandler = PipDismissTargetHandler.this;
            if (pipDismissTargetHandler.mEnableDismissDragToEdge) {
                pipDismissTargetHandler.showDismissTargetMaybe();
            }
        }

        @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject.MagnetListener
        public final void onUnstuckFromTarget(MagnetizedObject.MagneticTarget magneticTarget, MagnetizedObject magnetizedObject, float f, float f2, boolean z) {
            PipDismissTargetHandler pipDismissTargetHandler = PipDismissTargetHandler.this;
            if (!z) {
                pipDismissTargetHandler.mMotionHelper.mSpringingToTouch = true;
                return;
            }
            pipDismissTargetHandler.mMotionHelper.movetoTarget(f, f2, null, false);
            if (pipDismissTargetHandler.mEnableDismissDragToEdge) {
                pipDismissTargetHandler.mTargetViewContainer.hide();
            }
        }
    }

    public PipDismissTargetHandler(Context context, PipUiEventLogger pipUiEventLogger, PipMotionHelper pipMotionHelper, PipDisplayLayoutState pipDisplayLayoutState, DisplayController displayController, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mPipUiEventLogger = pipUiEventLogger;
        this.mMotionHelper = pipMotionHelper;
        this.mPipDisplayLayoutState = pipDisplayLayoutState;
        this.mDisplayController = displayController;
        this.mMainExecutor = shellExecutor;
    }

    public final void createOrUpdateDismissTarget() {
        int i = this.mPipDisplayLayoutState.mDisplayId;
        if (this.mWindowManager == null || i != this.mWindowManagerDisplayId) {
            this.mWindowManagerDisplayId = i;
            this.mWindowManager = (WindowManager) this.mContext.createWindowContext(this.mDisplayController.mDisplayManager.getDisplay(i), 2024, null).getSystemService(WindowManager.class);
        }
        if (this.mTargetViewContainer.getParent() != null) {
            this.mWindowManager.updateViewLayout(this.mTargetViewContainer, getDismissTargetLayoutParams());
            return;
        }
        this.mTargetViewContainer.animator.cancel();
        this.mTargetViewContainer.setVisibility(4);
        this.mTargetViewContainer.getViewTreeObserver().removeOnPreDrawListener(this);
        this.mWindowManager.addView(this.mTargetViewContainer, getDismissTargetLayoutParams());
    }

    public final WindowManager.LayoutParams getDismissTargetLayoutParams() {
        Point point = new Point();
        this.mWindowManager.getDefaultDisplay().getRealSize(point);
        int min = Math.min(point.y, this.mDismissAreaHeight);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, min, 0, point.y - min, 2024, IKnoxCustomManager.Stub.TRANSACTION_getForceSingleView, -3);
        layoutParams.setTitle("pip-dismiss-overlay");
        layoutParams.privateFlags |= 16;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        return layoutParams;
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public final boolean onPreDraw() {
        this.mTargetViewContainer.getViewTreeObserver().removeOnPreDrawListener(this);
        if (this.mTaskLeash != null) {
            SurfaceControl surfaceControl = this.mTargetViewContainer.getViewRootImpl().getSurfaceControl();
            if (surfaceControl.isValid()) {
                SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                transaction.setRelativeLayer(surfaceControl, this.mTaskLeash, -1);
                transaction.apply();
                return true;
            }
        }
        return true;
    }

    public final void showDismissTargetMaybe() {
        if (this.mEnableDismissDragToEdge) {
            this.mMagneticTarget.updateLocationOnScreen();
            createOrUpdateDismissTarget();
            if (this.mTargetViewContainer.getVisibility() != 0) {
                this.mTargetViewContainer.getViewTreeObserver().addOnPreDrawListener(this);
            }
            this.mTargetViewContainer.show();
        }
    }

    public final void updateMagneticTargetSize() {
        if (this.mTargetView == null) {
            return;
        }
        Resources resources = this.mContext.getResources();
        this.mTargetSize = resources.getDimensionPixelSize(R.dimen.dismiss_circle_size);
        this.mDismissAreaHeight = resources.getDimensionPixelSize(R.dimen.floating_dismiss_gradient_height);
        this.mMagneticTarget.magneticFieldRadiusPx = (int) (1.0f * this.mTargetSize * 1.25f);
    }
}
