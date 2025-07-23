package com.android.systemui.navigationbar.interactor;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.navigationbar.ColorSetting;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class InteractorFactory {
    public final Executor executor;
    public final Map provider;
    private final SettingsHelper settingsHelper;
    public final UserTracker userTracker;

    public InteractorFactory(Context context, SettingsHelper settingsHelper, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, DisplayManager displayManager, LogWrapper logWrapper, Handler handler, Executor executor) {
        this.settingsHelper = settingsHelper;
        this.executor = executor;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.provider = linkedHashMap;
        linkedHashMap.put(ButtonOrderInteractor.class, new ButtonOrderInteractor(settingsHelper));
        linkedHashMap.put(ButtonPositionInteractor.class, new ButtonPositionInteractor(settingsHelper));
        linkedHashMap.put(ButtonToHideKeyboardInteractor.class, new ButtonToHideKeyboardInteractor(settingsHelper));
        linkedHashMap.put(ColorSetting.class, new ColorSettingImpl(context, settingsHelper));
        linkedHashMap.put(EdgeBackGesturePolicyInteractor.class, new EdgeBackGesturePolicyInteractor(settingsHelper));
        Dependency.DependencyKey dependencyKey = Dependency.NAVBAR_BG_HANDLER;
        linkedHashMap.put(GestureNavigationSettingsInteractor.class, new GestureNavigationSettingsInteractor(context, (Handler) Dependency.sDependency.getDependencyInner(dependencyKey)));
        linkedHashMap.put(OpenThemeInteractor.class, new OpenThemeInteractor(broadcastDispatcher, settingsHelper));
        linkedHashMap.put(UseThemeDefaultInteractor.class, new UseThemeDefaultInteractor(settingsHelper));
        linkedHashMap.put(KeyboardButtonPositionInteractor.class, new KeyboardButtonPositionInteractor(settingsHelper));
        linkedHashMap.put(KnoxStateMonitorInteractor.class, new KnoxStateMonitorInteractor());
        linkedHashMap.put(DesktopModeInteractor.class, new DesktopModeInteractor(context, broadcastDispatcher, executor, (Handler) Dependency.sDependency.getDependencyInner(dependencyKey)));
        linkedHashMap.put(DeviceStateInteractor.class, new DeviceStateInteractor(context, settingsHelper, handler));
        linkedHashMap.put(OneHandModeInteractor.class, new OneHandModeInteractor(settingsHelper));
        linkedHashMap.put(SettingsSoftResetInteractor.class, new SettingsSoftResetInteractor(broadcastDispatcher));
        linkedHashMap.put(RotationLockInteractor.class, new RotationLockInteractor());
        linkedHashMap.put(TaskBarInteractor.class, new TaskBarInteractor(context, broadcastDispatcher, (Handler) Dependency.sDependency.getDependencyInner(dependencyKey), settingsHelper, displayManager, logWrapper));
        linkedHashMap.put(CoverDisplayWidgetInteractor.class, new CoverDisplayWidgetInteractor(context, settingsHelper));
        linkedHashMap.put(PackageRemovedInteractor.class, new PackageRemovedInteractor(broadcastDispatcher, userTracker));
        linkedHashMap.put(NavigationModeInteractor.class, new NavigationModeInteractor(settingsHelper));
    }

    public final Object get(Class cls) {
        Object obj = ((LinkedHashMap) this.provider).get(cls);
        if (obj == null) {
            return null;
        }
        return obj;
    }
}
