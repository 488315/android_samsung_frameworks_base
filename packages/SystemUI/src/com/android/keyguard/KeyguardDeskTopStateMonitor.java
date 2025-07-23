package com.android.keyguard;

import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractor;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.wm.shell.desktopmode.DesktopMode;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$addDefaultDisplayDesktopModeChangeListener$1;
import dagger.Lazy;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class KeyguardDeskTopStateMonitor {
    public final Lazy mFaceWidgetManagerLazy;
    public boolean mIsDesktopStandAlone;
    public boolean mIsExternalDesktopWindowing;

    public KeyguardDeskTopStateMonitor(ConnectedDisplayInteractor connectedDisplayInteractor, Lazy lazy, Executor executor, CoroutineScope coroutineScope) {
        this.mFaceWidgetManagerLazy = lazy;
        JavaAdapterKt.collectFlow(coroutineScope, ((ConnectedDisplayInteractorImpl) connectedDisplayInteractor).isExternalDesktopWindowing, new Consumer() { // from class: com.android.keyguard.KeyguardDeskTopStateMonitor$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                KeyguardDeskTopStateMonitor keyguardDeskTopStateMonitor = KeyguardDeskTopStateMonitor.this;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                if (keyguardDeskTopStateMonitor.mIsExternalDesktopWindowing != booleanValue) {
                    PluginKeyguardStatusView pluginKeyguardStatusView = ((PluginFaceWidgetManager) keyguardDeskTopStateMonitor.mFaceWidgetManagerLazy.get()).mFaceWidgetPlugin;
                    if (pluginKeyguardStatusView != null) {
                        pluginKeyguardStatusView.setIsExternalDesktopWindowing(booleanValue);
                    }
                    keyguardDeskTopStateMonitor.mIsExternalDesktopWindowing = booleanValue;
                }
            }
        });
        SystemUIAppComponentFactoryBase.Companion.getClass();
        DesktopMode desktopMode = (DesktopMode) SystemUIAppComponentFactoryBase.systemUIInitializer.getWMComponent().getDesktopMode().orElse(null);
        if (desktopMode != null) {
            DesktopTasksController.DefaultDisplayDesktopModeChangeListener defaultDisplayDesktopModeChangeListener = new DesktopTasksController.DefaultDisplayDesktopModeChangeListener() { // from class: com.android.keyguard.KeyguardDeskTopStateMonitor.1
                @Override // com.android.wm.shell.desktopmode.DesktopTasksController.DefaultDisplayDesktopModeChangeListener
                public final void onDefaultDisplayDesktopModeChanged(boolean z) {
                    EmergencyButtonController$$ExternalSyntheticOutline0.m("onDefaultDisplayDesktopModeChanged", "KeyguardDeskTopStateMonitor", z);
                    KeyguardDeskTopStateMonitor keyguardDeskTopStateMonitor = KeyguardDeskTopStateMonitor.this;
                    PluginKeyguardStatusView pluginKeyguardStatusView = ((PluginFaceWidgetManager) keyguardDeskTopStateMonitor.mFaceWidgetManagerLazy.get()).mFaceWidgetPlugin;
                    if (pluginKeyguardStatusView != null) {
                        pluginKeyguardStatusView.setIsDesktopStandAlone(z);
                    }
                    keyguardDeskTopStateMonitor.mIsDesktopStandAlone = z;
                }
            };
            DesktopTasksController desktopTasksController = DesktopTasksController.this;
            desktopTasksController.mainExecutor.execute(new DesktopTasksController$DesktopModeImpl$addDefaultDisplayDesktopModeChangeListener$1(desktopTasksController, defaultDisplayDesktopModeChangeListener, executor));
        }
    }
}
