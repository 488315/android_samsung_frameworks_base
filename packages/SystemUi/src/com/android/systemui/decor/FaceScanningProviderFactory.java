package com.android.systemui.decor;

import android.content.Context;
import android.os.FactoryTest;
import android.util.Log;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.log.ScreenDecorationsLogger;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.util.DeviceType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.collections.EmptyList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FaceScanningProviderFactory extends DecorProviderFactory {
    public final AuthController authController;
    public final Context context;
    public final Display display;
    public final DisplayInfo displayInfo = new DisplayInfo();
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final ScreenDecorationsLogger logger;
    public final Executor mainExecutor;
    public final StatusBarStateController statusBarStateController;

    public FaceScanningProviderFactory(AuthController authController, Context context, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, Executor executor, ScreenDecorationsLogger screenDecorationsLogger) {
        this.authController = authController;
        this.context = context;
        this.statusBarStateController = statusBarStateController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mainExecutor = executor;
        this.logger = screenDecorationsLogger;
        this.display = context.getDisplay();
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public final boolean getHasProviders() {
        if (this.authController.mFaceSensorLocation == null) {
            return false;
        }
        DisplayInfo displayInfo = this.displayInfo;
        Display display = this.display;
        if (display != null) {
            display.getDisplayInfo(displayInfo);
        } else {
            Log.w("FaceScanningProvider", "display is null, can't update displayInfo");
        }
        int i = DeviceType.supportTablet;
        return !FactoryTest.isFactoryBinary() && DisplayCutout.getFillBuiltInDisplayCutout(this.context.getResources(), displayInfo.uniqueId);
    }

    @Override // com.android.systemui.decor.DecorProviderFactory
    public final List getProviders() {
        if (!getHasProviders()) {
            return EmptyList.INSTANCE;
        }
        ArrayList arrayList = new ArrayList();
        DisplayInfo displayInfo = this.displayInfo;
        DisplayCutout displayCutout = displayInfo.displayCutout;
        if (displayCutout != null) {
            Iterator it = ((ArrayList) FaceScanningProviderFactoryKt.getBoundBaseOnCurrentRotation(displayCutout)).iterator();
            while (it.hasNext()) {
                arrayList.add(new FaceScanningOverlayProviderImpl(FaceScanningProviderFactoryKt.baseOnRotation0(((Number) it.next()).intValue(), displayInfo.rotation), this.authController, this.statusBarStateController, this.keyguardUpdateMonitor, this.mainExecutor, this.logger));
            }
        }
        return arrayList;
    }
}
