package com.android.systemui.doze;

import android.os.Handler;
import android.os.PowerManager;
import com.android.systemui.aod.AODTouchModeManager;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.wakelock.WakeLock;
import dagger.internal.DoubleCheck;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DozeScreenState_Factory implements Provider {
    public final Provider authControllerProvider;
    public final Provider dozeLogProvider;
    public final Provider dozeScreenBrightnessProvider;
    public final Provider handlerProvider;
    public final Provider hostProvider;
    public final Provider mAODTouchModeManagerProvider;
    public final Provider mPluginAODManagerLazyProvider;
    public final Provider mPowerManagerProvider;
    public final Provider mSubScreenManagerProvider;
    public final Provider parametersProvider;
    public final Provider serviceProvider;
    public final Provider udfpsControllerProvider;
    public final Provider wakeLockProvider;

    public DozeScreenState_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9, Provider provider10, Provider provider11, Provider provider12, Provider provider13) {
        this.serviceProvider = provider;
        this.handlerProvider = provider2;
        this.hostProvider = provider3;
        this.parametersProvider = provider4;
        this.wakeLockProvider = provider5;
        this.authControllerProvider = provider6;
        this.udfpsControllerProvider = provider7;
        this.dozeLogProvider = provider8;
        this.dozeScreenBrightnessProvider = provider9;
        this.mPluginAODManagerLazyProvider = provider10;
        this.mAODTouchModeManagerProvider = provider11;
        this.mPowerManagerProvider = provider12;
        this.mSubScreenManagerProvider = provider13;
    }

    public static DozeScreenState newInstance(DozeMachine.Service service, Handler handler, DozeHost dozeHost, DozeParameters dozeParameters, WakeLock wakeLock, AuthController authController, Provider provider, DozeLog dozeLog, DozeScreenBrightness dozeScreenBrightness) {
        return new DozeScreenState(service, handler, dozeHost, dozeParameters, wakeLock, authController, provider, dozeLog, dozeScreenBrightness);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        DozeScreenState newInstance = newInstance((DozeMachine.Service) this.serviceProvider.get(), (Handler) this.handlerProvider.get(), (DozeHost) this.hostProvider.get(), (DozeParameters) this.parametersProvider.get(), (WakeLock) this.wakeLockProvider.get(), (AuthController) this.authControllerProvider.get(), this.udfpsControllerProvider, (DozeLog) this.dozeLogProvider.get(), (DozeScreenBrightness) this.dozeScreenBrightnessProvider.get());
        newInstance.mPluginAODManagerLazy = DoubleCheck.lazy(this.mPluginAODManagerLazyProvider);
        newInstance.mAODTouchModeManager = (AODTouchModeManager) this.mAODTouchModeManagerProvider.get();
        newInstance.mPowerManager = (PowerManager) this.mPowerManagerProvider.get();
        newInstance.mSubScreenManager = (SubScreenManager) this.mSubScreenManagerProvider.get();
        return newInstance;
    }
}
