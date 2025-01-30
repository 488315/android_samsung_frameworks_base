package com.android.systemui.navigationbar.interactor;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.SettingsHelper;
import com.samsung.systemui.splugins.navigationbar.ColorSetting;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class InteractorFactory {
    public final Map provider;

    public InteractorFactory(Context context, SettingsHelper settingsHelper, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, LogWrapper logWrapper) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.provider = linkedHashMap;
        linkedHashMap.put(ButtonOrderInteractor.class, new ButtonOrderInteractor(settingsHelper));
        linkedHashMap.put(ButtonPositionInteractor.class, new ButtonPositionInteractor(settingsHelper));
        linkedHashMap.put(ButtonToHideKeyboardInteractor.class, new ButtonToHideKeyboardInteractor(settingsHelper));
        linkedHashMap.put(ColorSetting.class, new ColorSettingImpl(context, settingsHelper));
        linkedHashMap.put(EdgeBackGesturePolicyInteractor.class, new EdgeBackGesturePolicyInteractor(settingsHelper));
        linkedHashMap.put(GestureNavigationSettingsInteractor.class, new GestureNavigationSettingsInteractor(context));
        linkedHashMap.put(OpenThemeInteractor.class, new OpenThemeInteractor(broadcastDispatcher, settingsHelper));
        linkedHashMap.put(UseThemeDefaultInteractor.class, new UseThemeDefaultInteractor(settingsHelper));
        linkedHashMap.put(KnoxStateMonitorInteractor.class, new KnoxStateMonitorInteractor());
        linkedHashMap.put(LegacyDesktopModeInteractor.class, new LegacyDesktopModeInteractor());
        Dependency.DependencyKey dependencyKey = Dependency.NAVBAR_BG_HANDLER;
        linkedHashMap.put(DesktopModeInteractor.class, new DesktopModeInteractor(context, broadcastDispatcher, (Handler) Dependency.get(dependencyKey), settingsHelper));
        linkedHashMap.put(DeviceStateInteractor.class, new DeviceStateInteractor(context, settingsHelper));
        linkedHashMap.put(OneHandModeInteractor.class, new OneHandModeInteractor(settingsHelper));
        linkedHashMap.put(SettingsSoftResetInteractor.class, new SettingsSoftResetInteractor(broadcastDispatcher));
        linkedHashMap.put(RotationLockInteractor.class, new RotationLockInteractor());
        linkedHashMap.put(TaskBarInteractor.class, new TaskBarInteractor(context, broadcastDispatcher, (Handler) Dependency.get(dependencyKey), settingsHelper, logWrapper));
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
