package com.android.systemui.accessibility;

import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.systemui.R;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class MagnificationConnectionImpl$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MagnificationConnectionImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ MagnificationConnectionImpl$$ExternalSyntheticLambda3(MagnificationConnectionImpl magnificationConnectionImpl, int i, boolean z, int i2) {
        this.$r8$classId = i2;
        this.f$0 = magnificationConnectionImpl;
        this.f$1 = i;
        this.f$2 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                MagnificationConnectionImpl magnificationConnectionImpl = this.f$0;
                int i = this.f$1;
                boolean z = this.f$2;
                final FullscreenMagnificationController fullscreenMagnificationController = (FullscreenMagnificationController) magnificationConnectionImpl.mMagnification.mFullscreenMagnificationControllerSupplier.get(i);
                if (fullscreenMagnificationController != null && fullscreenMagnificationController.mFullscreenMagnificationActivated != z) {
                    fullscreenMagnificationController.mFullscreenMagnificationActivated = z;
                    if (!z) {
                        if (fullscreenMagnificationController.mHandler.hasCallbacks(fullscreenMagnificationController.mShowBorderRunnable)) {
                            fullscreenMagnificationController.mHandler.removeCallbacks(fullscreenMagnificationController.mShowBorderRunnable);
                        }
                        fullscreenMagnificationController.mContext.unregisterComponentCallbacks(fullscreenMagnificationController);
                        fullscreenMagnificationController.mShowHideBorderAnimator.reverse();
                        break;
                    } else {
                        fullscreenMagnificationController.onConfigurationChanged(fullscreenMagnificationController.mContext.getResources().getConfiguration());
                        fullscreenMagnificationController.mContext.registerComponentCallbacks(fullscreenMagnificationController);
                        if (fullscreenMagnificationController.mSurfaceControlViewHost == null) {
                            View inflate = LayoutInflater.from(fullscreenMagnificationController.mContext).inflate(R.layout.fullscreen_magnification_border, (ViewGroup) null);
                            fullscreenMagnificationController.mFullscreenBorder = inflate;
                            inflate.setAlpha(0.0f);
                            ((GradientDrawable) fullscreenMagnificationController.mFullscreenBorder.getBackground()).setCornerRadius(ScreenDecorationsUtils.getWindowCornerRadius(fullscreenMagnificationController.mContext));
                            fullscreenMagnificationController.mShowHideBorderAnimator.setTarget(fullscreenMagnificationController.mFullscreenBorder);
                            SurfaceControlViewHost surfaceControlViewHost = (SurfaceControlViewHost) fullscreenMagnificationController.mScvhSupplier.get();
                            fullscreenMagnificationController.mSurfaceControlViewHost = surfaceControlViewHost;
                            View view = fullscreenMagnificationController.mFullscreenBorder;
                            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams((fullscreenMagnificationController.mBorderOffset * 2) + fullscreenMagnificationController.mWindowBounds.width(), (fullscreenMagnificationController.mBorderOffset * 2) + fullscreenMagnificationController.mWindowBounds.height(), 2032, 40, -2);
                            layoutParams.setTrustedOverlay();
                            surfaceControlViewHost.setView(view, layoutParams);
                            fullscreenMagnificationController.mBorderSurfaceControl = fullscreenMagnificationController.mSurfaceControlViewHost.getSurfacePackage().getSurfaceControl();
                            try {
                                fullscreenMagnificationController.mIWindowManager.watchRotation(fullscreenMagnificationController.mRotationWatcher, 0);
                            } catch (Exception e) {
                                Log.w("FullscreenMagnificationController", "Failed to register rotation watcher", e);
                            }
                        }
                        SurfaceControl.Transaction addTransactionCommittedListener = fullscreenMagnificationController.mTransaction.addTransactionCommittedListener(fullscreenMagnificationController.mExecutor, new SurfaceControl.TransactionCommittedListener() { // from class: com.android.systemui.accessibility.FullscreenMagnificationController$$ExternalSyntheticLambda2
                            @Override // android.view.SurfaceControl.TransactionCommittedListener
                            public final void onTransactionCommitted() {
                                FullscreenMagnificationController fullscreenMagnificationController2 = FullscreenMagnificationController.this;
                                if (fullscreenMagnificationController2.mShowHideBorderAnimator.isRunning()) {
                                    fullscreenMagnificationController2.mShowHideBorderAnimator.reverse();
                                } else {
                                    fullscreenMagnificationController2.mShowHideBorderAnimator.start();
                                }
                            }
                        });
                        SurfaceControl surfaceControl = fullscreenMagnificationController.mBorderSurfaceControl;
                        float f = -fullscreenMagnificationController.mBorderOffset;
                        addTransactionCommittedListener.setPosition(surfaceControl, f, f).setLayer(fullscreenMagnificationController.mBorderSurfaceControl, Integer.MAX_VALUE).show(fullscreenMagnificationController.mBorderSurfaceControl).apply();
                        fullscreenMagnificationController.mAccessibilityManager.attachAccessibilityOverlayToDisplay(fullscreenMagnificationController.mDisplayId, fullscreenMagnificationController.mBorderSurfaceControl);
                        if (fullscreenMagnificationController.mFullscreenBorder != null) {
                            fullscreenMagnificationController.mSurfaceControlViewHost.getRootSurfaceControl().setTouchableRegion(FullscreenMagnificationController.sEmptyRegion);
                            break;
                        }
                    }
                }
                break;
            default:
                MagnificationConnectionImpl magnificationConnectionImpl2 = this.f$0;
                int i2 = this.f$1;
                boolean z2 = this.f$2;
                WindowMagnificationController windowMagnificationController = (WindowMagnificationController) magnificationConnectionImpl2.mMagnification.mWindowMagnificationControllerSupplier.get(i2);
                if (windowMagnificationController != null && z2) {
                    if (windowMagnificationController.mCursorView == null) {
                        windowMagnificationController.mCursorView = (ImageView) windowMagnificationController.mMirrorView.findViewById(R.id.focus_point);
                    }
                    ImageView imageView = windowMagnificationController.mCursorView;
                    if (imageView != null) {
                        if (!z2) {
                            imageView.setVisibility(8);
                            break;
                        } else {
                            imageView.setVisibility(0);
                            break;
                        }
                    }
                }
                break;
        }
    }
}
