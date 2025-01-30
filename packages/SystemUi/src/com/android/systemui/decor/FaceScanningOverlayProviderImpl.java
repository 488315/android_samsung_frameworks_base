package com.android.systemui.decor;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.widget.FrameLayout;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.FaceScanningOverlay;
import com.android.systemui.R;
import com.android.systemui.RegionInterceptingFrameLayout;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.log.ScreenDecorationsLogger;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FaceScanningOverlayProviderImpl extends BoundDecorProvider {
    public final int alignedBound;
    public final AuthController authController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final ScreenDecorationsLogger logger;
    public final Executor mainExecutor;
    public final StatusBarStateController statusBarStateController;
    public final int viewId = R.id.face_scanning_anim;

    public FaceScanningOverlayProviderImpl(int i, AuthController authController, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, Executor executor, ScreenDecorationsLogger screenDecorationsLogger) {
        this.alignedBound = i;
        this.authController = authController;
        this.statusBarStateController = statusBarStateController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mainExecutor = executor;
        this.logger = screenDecorationsLogger;
    }

    @Override // com.android.systemui.decor.BoundDecorProvider
    public final int getAlignedBound() {
        return this.alignedBound;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public final int getViewId() {
        return this.viewId;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public final View inflateView(Context context, RegionInterceptingFrameLayout regionInterceptingFrameLayout, int i, int i2) {
        FaceScanningOverlay faceScanningOverlay = new FaceScanningOverlay(context, this.alignedBound, this.statusBarStateController, this.keyguardUpdateMonitor, this.mainExecutor, this.logger, this.authController);
        faceScanningOverlay.setId(this.viewId);
        faceScanningOverlay.setColor(i2);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        updateLayoutParams(layoutParams, i);
        regionInterceptingFrameLayout.addView(faceScanningOverlay, layoutParams);
        return faceScanningOverlay;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public final void onReloadResAndMeasure(View view, int i, int i2, int i3, String str) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        updateLayoutParams(layoutParams, i2);
        view.setLayoutParams(layoutParams);
        FaceScanningOverlay faceScanningOverlay = view instanceof FaceScanningOverlay ? (FaceScanningOverlay) view : null;
        if (faceScanningOverlay != null) {
            faceScanningOverlay.setColor(i3);
            faceScanningOverlay.updateConfiguration(str);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x001e, code lost:
    
        if (r6 != 3) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateLayoutParams(FrameLayout.LayoutParams layoutParams, int i) {
        int i2 = -1;
        layoutParams.width = -1;
        layoutParams.height = -1;
        AuthController authController = this.authController;
        this.logger.faceSensorLocation(authController.mFaceSensorLocation);
        Point point = authController.mFaceSensorLocation;
        if (point != null) {
            int i3 = point.y * 2;
            if (i != 0) {
                if (i != 1) {
                    if (i != 2) {
                    }
                }
                layoutParams.width = i3;
            }
            layoutParams.height = i3;
        }
        if (i == 0) {
            i2 = 8388659;
        } else if (i == 1) {
            i2 = 8388611;
        } else if (i == 2) {
            i2 = 8388693;
        } else if (i == 3) {
            i2 = 8388613;
        }
        layoutParams.gravity = i2;
    }
}
