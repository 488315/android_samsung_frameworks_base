package com.android.systemui.decor;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.FaceScanningOverlay;
import com.android.systemui.R;
import com.android.systemui.RegionInterceptingFrameLayout;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.data.repository.FacePropertyRepository;
import com.android.systemui.log.ScreenDecorationsLogger;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
    public final View inflateView(Context context, RegionInterceptingFrameLayout regionInterceptingFrameLayout, int i, int i2) {
        Executor executor = this.mainExecutor;
        FaceScanningOverlay faceScanningOverlay = new FaceScanningOverlay(context, this.alignedBound, this.statusBarStateController, this.keyguardUpdateMonitor, executor, this.logger, this.authController);
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

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0056, code lost:
    
        if (r9 != 3) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateLayoutParams(android.widget.FrameLayout.LayoutParams r8, int r9) {
        /*
            r7 = this;
            r0 = -1
            r8.width = r0
            r8.height = r0
            com.android.systemui.biometrics.data.repository.FacePropertyRepository r1 = r7.facePropertyRepository
            com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl r1 = (com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl) r1
            kotlinx.coroutines.flow.ReadonlyStateFlow r2 = r1.sensorLocation
            kotlinx.coroutines.flow.StateFlow r2 = r2.$$delegate_0
            java.lang.Object r2 = r2.getValue()
            android.graphics.Point r2 = (android.graphics.Point) r2
            com.android.systemui.log.ScreenDecorationsLogger r7 = r7.logger
            r7.getClass()
            com.android.systemui.log.core.LogLevel r3 = com.android.systemui.log.core.LogLevel.DEBUG
            com.android.systemui.log.ScreenDecorationsLogger$$ExternalSyntheticLambda0 r4 = new com.android.systemui.log.ScreenDecorationsLogger$$ExternalSyntheticLambda0
            r5 = 4
            r4.<init>(r5)
            r5 = 0
            com.android.systemui.log.LogBuffer r7 = r7.logBuffer
            java.lang.String r6 = "ScreenDecorationsLog"
            com.android.systemui.log.core.LogMessage r3 = r7.obtain(r6, r3, r4, r5)
            r4 = 2
            if (r2 == 0) goto L30
            int r5 = r2.y
            int r5 = r5 * r4
            goto L31
        L30:
            r5 = 0
        L31:
            r6 = r3
            com.android.systemui.log.LogMessageImpl r6 = (com.android.systemui.log.LogMessageImpl) r6
            r6.int1 = r5
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r6.str1 = r2
            r7.commit(r3)
            kotlinx.coroutines.flow.ReadonlyStateFlow r7 = r1.sensorLocation
            kotlinx.coroutines.flow.StateFlow r7 = r7.$$delegate_0
            java.lang.Object r7 = r7.getValue()
            android.graphics.Point r7 = (android.graphics.Point) r7
            r1 = 3
            r2 = 1
            if (r7 == 0) goto L5e
            int r7 = r7.y
            int r7 = r7 * r4
            if (r9 == 0) goto L5c
            if (r9 == r2) goto L59
            if (r9 == r4) goto L5c
            if (r9 == r1) goto L59
            goto L5e
        L59:
            r8.width = r7
            goto L5e
        L5c:
            r8.height = r7
        L5e:
            if (r9 == 0) goto L73
            if (r9 == r2) goto L6f
            if (r9 == r4) goto L6b
            if (r9 == r1) goto L67
            goto L76
        L67:
            r0 = 8388613(0x800005, float:1.175495E-38)
            goto L76
        L6b:
            r0 = 8388693(0x800055, float:1.1755063E-38)
            goto L76
        L6f:
            r0 = 8388611(0x800003, float:1.1754948E-38)
            goto L76
        L73:
            r0 = 8388659(0x800033, float:1.1755015E-38)
        L76:
            r8.gravity = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.decor.FaceScanningOverlayProviderImpl.updateLayoutParams(android.widget.FrameLayout$LayoutParams, int):void");
    }
}
