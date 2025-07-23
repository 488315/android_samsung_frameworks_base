package com.android.systemui.decor;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.data.repository.FacePropertyRepository;
import com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl;
import com.android.systemui.log.ScreenDecorationsLogger;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.collections.EmptyList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class FaceScanningProviderFactoryImpl implements FaceScanningProviderFactory {
    public final AuthController authController;
    public final Context context;
    public final Display display;
    public final DisplayInfo displayInfo = new DisplayInfo();
    public final FacePropertyRepository facePropertyRepository;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final ScreenDecorationsLogger logger;
    public final Executor mainExecutor;
    public final StatusBarStateController statusBarStateController;

    public FaceScanningProviderFactoryImpl(AuthController authController, Context context, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, Executor executor, ScreenDecorationsLogger screenDecorationsLogger, FacePropertyRepository facePropertyRepository) {
        this.authController = authController;
        this.context = context;
        this.statusBarStateController = statusBarStateController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mainExecutor = executor;
        this.logger = screenDecorationsLogger;
        this.facePropertyRepository = facePropertyRepository;
        this.display = context.getDisplay();
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public final boolean getHasProviders() {
        if (((FacePropertyRepositoryImpl) this.facePropertyRepository).sensorLocation.$$delegate_0.getValue() == null) {
            return false;
        }
        Display display = this.display;
        if (display != null) {
            display.getDisplayInfo(this.displayInfo);
        } else {
            Log.w("FaceScanningProvider", "display is null, can't update displayInfo");
        }
        return DisplayCutout.getFillBuiltInDisplayCutout(this.context.getResources(), this.displayInfo.uniqueId);
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public final List getProviders() {
        if (!getHasProviders()) {
            return EmptyList.INSTANCE;
        }
        ArrayList arrayList = new ArrayList();
        DisplayCutout displayCutout = this.displayInfo.displayCutout;
        if (displayCutout != null) {
            ArrayList arrayList2 = (ArrayList) FaceScanningProviderFactoryKt.getBoundBaseOnCurrentRotation(displayCutout);
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList2.get(i);
                i++;
                int baseOnRotation0 = FaceScanningProviderFactoryKt.baseOnRotation0(((Number) obj).intValue(), this.displayInfo.rotation);
                Executor executor = this.mainExecutor;
                arrayList.add(new FaceScanningOverlayProviderImpl(baseOnRotation0, this.authController, this.statusBarStateController, this.keyguardUpdateMonitor, executor, this.logger, this.facePropertyRepository));
            }
        }
        return arrayList;
    }
}
