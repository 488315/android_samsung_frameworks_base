package com.android.systemui.decor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.FaceScanningOverlay;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.data.repository.FacePropertyRepository;
import com.android.systemui.log.ScreenDecorationsLogger;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class FaceScanningOverlayProviderImpl extends BoundDecorProvider {
    public final int alignedBound;
    public final AuthController authController;
    public final FacePropertyRepository facePropertyRepository;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final ScreenDecorationsLogger logger;
    public final Executor mainExecutor;
    public final StatusBarStateController statusBarStateController;
    public final int viewId = R.id.face_scanning_anim;

    public FaceScanningOverlayProviderImpl(int i, AuthController authController, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, Executor executor, ScreenDecorationsLogger screenDecorationsLogger, FacePropertyRepository facePropertyRepository) {
        this.alignedBound = i;
        this.authController = authController;
        this.statusBarStateController = statusBarStateController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mainExecutor = executor;
        this.logger = screenDecorationsLogger;
        this.facePropertyRepository = facePropertyRepository;
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
    public final View inflateView(Context context, ViewGroup viewGroup, int i, int i2) {
        FaceScanningOverlay faceScanningOverlay = new FaceScanningOverlay(context, this.alignedBound, this.statusBarStateController, this.keyguardUpdateMonitor, this.mainExecutor, this.logger, this.authController);
        faceScanningOverlay.setId(this.viewId);
        faceScanningOverlay.setColor$1(i2);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        updateLayoutParams(layoutParams, i);
        viewGroup.addView(faceScanningOverlay, layoutParams);
        return faceScanningOverlay;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public final void onReloadResAndMeasure(View view, int i, int i2, int i3, String str) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        updateLayoutParams(layoutParams, i2);
        view.setLayoutParams(layoutParams);
        FaceScanningOverlay faceScanningOverlay = view instanceof FaceScanningOverlay ? (FaceScanningOverlay) view : null;
        if (faceScanningOverlay != null) {
            faceScanningOverlay.setColor$1(i3);
            faceScanningOverlay.updateConfiguration(str);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0030, code lost:
    
        if (r6 != 3) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateLayoutParams(android.widget.FrameLayout.LayoutParams r5, int r6) {
        /*
            r4 = this;
            r0 = -1
            r5.width = r0
            r5.height = r0
            com.android.systemui.biometrics.data.repository.FacePropertyRepository r1 = r4.facePropertyRepository
            com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl r1 = (com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl) r1
            kotlinx.coroutines.flow.ReadonlyStateFlow r2 = r1.sensorLocation
            kotlinx.coroutines.flow.StateFlow r2 = r2.$$delegate_0
            java.lang.Object r2 = r2.getValue()
            android.graphics.Point r2 = (android.graphics.Point) r2
            com.android.systemui.log.ScreenDecorationsLogger r4 = r4.logger
            r4.faceSensorLocation(r2)
            kotlinx.coroutines.flow.ReadonlyStateFlow r4 = r1.sensorLocation
            kotlinx.coroutines.flow.StateFlow r4 = r4.$$delegate_0
            java.lang.Object r4 = r4.getValue()
            android.graphics.Point r4 = (android.graphics.Point) r4
            r1 = 3
            r2 = 1
            r3 = 2
            if (r4 == 0) goto L38
            int r4 = r4.y
            int r4 = r4 * r3
            if (r6 == 0) goto L36
            if (r6 == r2) goto L33
            if (r6 == r3) goto L36
            if (r6 == r1) goto L33
            goto L38
        L33:
            r5.width = r4
            goto L38
        L36:
            r5.height = r4
        L38:
            if (r6 == 0) goto L4d
            if (r6 == r2) goto L49
            if (r6 == r3) goto L45
            if (r6 == r1) goto L41
            goto L50
        L41:
            r0 = 8388613(0x800005, float:1.175495E-38)
            goto L50
        L45:
            r0 = 8388693(0x800055, float:1.1755063E-38)
            goto L50
        L49:
            r0 = 8388611(0x800003, float:1.1754948E-38)
            goto L50
        L4d:
            r0 = 8388659(0x800033, float:1.1755015E-38)
        L50:
            r5.gravity = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.decor.FaceScanningOverlayProviderImpl.updateLayoutParams(android.widget.FrameLayout$LayoutParams, int):void");
    }
}
