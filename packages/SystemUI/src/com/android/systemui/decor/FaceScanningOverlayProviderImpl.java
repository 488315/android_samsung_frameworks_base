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
import com.android.systemui.biometrics.data.repository.FacePropertyRepository;
import com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.ScreenDecorationsLogger;
import com.android.systemui.log.ScreenDecorationsLogger$$ExternalSyntheticLambda0;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import java.util.concurrent.Executor;

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

    /* JADX WARN: Removed duplicated region for block: B:14:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateLayoutParams(FrameLayout.LayoutParams layoutParams, int i) {
        int i2 = -1;
        layoutParams.width = -1;
        layoutParams.height = -1;
        FacePropertyRepositoryImpl facePropertyRepositoryImpl = (FacePropertyRepositoryImpl) this.facePropertyRepository;
        Point point = (Point) facePropertyRepositoryImpl.sensorLocation.$$delegate_0.getValue();
        ScreenDecorationsLogger screenDecorationsLogger = this.logger;
        screenDecorationsLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        ScreenDecorationsLogger$$ExternalSyntheticLambda0 screenDecorationsLogger$$ExternalSyntheticLambda0 = new ScreenDecorationsLogger$$ExternalSyntheticLambda0(4);
        LogBuffer logBuffer = screenDecorationsLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("ScreenDecorationsLog", logLevel, screenDecorationsLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = point != null ? point.y * 2 : 0;
        logMessageImpl.str1 = String.valueOf(point);
        logBuffer.commit(logMessageObtain);
        Point point2 = (Point) facePropertyRepositoryImpl.sensorLocation.$$delegate_0.getValue();
        if (point2 != null) {
            int i3 = point2.y * 2;
            if (i == 0) {
                layoutParams.height = i3;
            } else if (i == 1) {
                layoutParams.width = i3;
            } else if (i != 2) {
                if (i == 3) {
                }
            }
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
